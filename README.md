## ActiveMQ External Plugin template
This repo is a template for developing your ActiveMQ external plugin. 

Its main purpose is to define the bare minimum dependencies and steps to add it to your ActiveMQ installation.

This simple plugin intercepts text messages sent and print out the number of characters to stdout.


### Step 1. Build the plugin jar

```shell
mvn package
```

### Step 2. Move the packaged jar to the activemq lib
- Download ActiveMQ 6.x [here](https://activemq.apache.org/components/classic/download/)
- Unzip the tarball or zip file you just downloaded. For example, `unzip ~/Downloads/apache-activemq-6.1.5-bin.zip`
- Copy the built jar in the `lib` sub-folder of the installation. For example`cp ./target/activemq-plugin-1.0-SNAPSHOT.jar ~/Downloads/apache-activemq-6.1.5/lib/`
- Configure the plugin in the <plugins> tag

### Step 3. Start the ActiveMQ broker
```shell
$ cd ~/Downloads/apache-activemq-6.1.5/
$ ./bin/activemq console 
```

Try sending a a few messages. You will notice it print out `Sent: ...` on the terminal
