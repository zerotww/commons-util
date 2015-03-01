package com.nriet.util;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class ActiveMQManage {

	/**
	 * hello
	 * 
	 * @param url
	 * @param type
	 * @param brokerName
	 * @param destinationType
	 * @param destinationName
	 */
	public static void purge(String url, String type, String brokerName, String destinationType, String destinationName) {
		try {
			JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
			JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL);
			MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
			ObjectName objectName = new ObjectName(String.format(
					"org.apache.activemq:type=%s,brokerName=%s,destinatinType=%s,destinatinName=%s", type, brokerName,
					destinationType, destinationName));
			mBeanServerConnection.invoke(objectName, "purge", null, null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "service:jmx:rmi://10.124.31.191:1097/jndi/rmi://10.124.31.191:1098/jmxrmi";
		try {
			JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
			JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);
			MBeanServerConnection beanServerConnection = connector.getMBeanServerConnection();
			ObjectName objectName = new ObjectName(
					"org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=String");
			// ObjectInstance objectInstance =
			// beanServerConnection.getObjectInstance(objectName);
			beanServerConnection.invoke(objectName, "purge", null, null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
	}
}
