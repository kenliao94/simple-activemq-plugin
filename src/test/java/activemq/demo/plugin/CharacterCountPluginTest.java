package activemq.demo.plugin;

import static org.mockito.Mockito.*;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CharacterCountPluginTest {

    @Mock
    private Broker mockBroker;

    @Mock
    private ProducerBrokerExchange mockProducerExchange;

    private CharacterCountPlugin plugin;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        plugin = new CharacterCountPlugin();
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testInstallPlugin() throws Exception {
        Broker result = plugin.installPlugin(mockBroker);
        assertNotNull(result);
        assertTrue(result.toString().contains("CharacterCountBroker"));
    }

    @Test
    public void testSendWithTextMessage() throws Exception {
        // Arrange
        Broker broker = plugin.installPlugin(mockBroker);
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("Hello World");

        // Act
        broker.send(mockProducerExchange, message);

        // Assert
        verify(mockBroker).send(mockProducerExchange, message);
        assertTrue(outputStreamCaptor.toString().contains("Sent: 11"));
    }

    @Test
    public void testSendWithNullText() throws Exception {
        // Arrange
        Broker broker = plugin.installPlugin(mockBroker);
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(null);

        // Act
        broker.send(mockProducerExchange, message);

        // Assert
        verify(mockBroker).send(mockProducerExchange, message);
        assertFalse(outputStreamCaptor.toString().contains("Sent:"));
    }

    @Test
    public void testSendWithEmptyText() throws Exception {
        // Arrange
        Broker broker = plugin.installPlugin(mockBroker);
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("");

        // Act
        broker.send(mockProducerExchange, message);

        // Assert
        verify(mockBroker).send(mockProducerExchange, message);
        assertTrue(outputStreamCaptor.toString().contains("Sent: 0"));
    }
}