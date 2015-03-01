package org.tww.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsExample {

	public static void produceMessage() {
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				"failover:(tcp://10.124.31.191:61616)");
		try {
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("DEST");
			MessageProducer producer = session.createProducer(destination);

			Message textMessage = session.createTextMessage("msg");
			producer.send(textMessage);
			producer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				"failover:(tcp://10.124.31.191:61616)");
		try {
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("DEST");
			MessageConsumer consumer = session.createConsumer(destination);
			Message message = consumer.receive();
			System.out.println(message);
			consumer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
