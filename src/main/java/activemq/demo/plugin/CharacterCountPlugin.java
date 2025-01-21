package activemq.demo.plugin;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.ActiveMQTextMessage;

public class CharacterCountPlugin implements BrokerPlugin {
    
    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        return new CharacterCountBroker(broker);
    }
    
    private static class CharacterCountBroker extends BrokerFilter {
        
        public CharacterCountBroker(Broker next) {
            super(next);
            System.out.println("Initializing CharacterCountBroker");
        }
        
        @Override
        public void send(ProducerBrokerExchange producerExchange, Message messageSend) 
            throws Exception {
            
            if (messageSend instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage textMessage = (ActiveMQTextMessage) messageSend;
                String text = textMessage.getText();
                
                if (text != null) {
                    int charCount = text.length();
                    System.out.println("Sent: " + charCount);
                }
            }
            
            super.send(producerExchange, messageSend);
        }
    }
} 