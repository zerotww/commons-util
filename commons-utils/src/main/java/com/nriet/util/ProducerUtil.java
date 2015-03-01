package com.nriet.util;

import java.io.IOException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ProducerUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProducerUtil.class);

	private static ConnectionFactory factory;

	private static Connection connection;

	private static Session session;

	private static Destination destination;

	static {
		Properties properties = null;
		try {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("mq.properties"));
			String brokerURI = properties.getProperty("sender.brokerURI");
			factory = new ActiveMQConnectionFactory(brokerURI);
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			String destURI = properties.getProperty("sender.destURI");
			destination = session.createQueue(destURI);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建消费�?
	 * 
	 * @return
	 */
	public static MessageProducer createProducer() {
		MessageProducer producer = null;
		try {
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			logger.error("createProducer()", e); //$NON-NLS-1$

			e.printStackTrace();
		}
		return producer;
	}

	/**
	 * 发�?消息,若是出现异常则尝试关闭该生产�?可用于连续发�?
	 * 
	 * @param producer
	 * @param msg
	 */
	public static void sendMsg(MessageProducer producer, String msg) {
		if (producer != null) {
			TextMessage message;
			try {
				message = session.createTextMessage(msg);
				producer.send(message);
			} catch (JMSException e) {
				logger.error("SendMsg(String)", e); //$NON-NLS-1$
				e.printStackTrace();
				closeProducer(producer);
			}
		} else {
			logger.info("Producer is null");
		}
	}

	/**
	 * 关闭生产�?
	 * 
	 * @param producer
	 */
	public static void closeProducer(MessageProducer producer) {
		if (producer != null)
			try {
				producer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 发�?单条消息,使用的MQ配置从mq.properties文件�?
	 * 
	 * @param msg
	 */
	public static void sendMsg(String msg) {
		MessageProducer producer = createProducer();
		sendMsg(producer, msg);
		closeProducer(producer);
	}

	public static void main(String[] args) {
		sendMsg("hello");
	}

}
