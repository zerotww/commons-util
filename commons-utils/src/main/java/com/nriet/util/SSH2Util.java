package com.nriet.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class SSH2Util {

	private static Connection connection;

	public static Connection getConnection(String ip, String username,
			String password) {
		if (connection == null) {
			connection = new Connection(ip);
			try {
				connection.connect();
				if (connection.authenticateWithPassword(username, password))
					return connection;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (connection == null) {
			System.out.println("cannot connect to " + ip + " with username: "
					+ username + " and password: " + password);
		}
		return connection;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null)
			connection.close();
	}

	public static String execCommand(Connection connection, String command) {
		Session session = null;
		InputStream stdout = null;
		String result = null;
		try {
			session = connection.openSession();
			session.execCommand(command);
			stdout = session.getStdout();
			result = IOUtils.toString(stdout);
			String error = IOUtils.toString(session.getStderr());
			if (error != null && !error.trim().isEmpty()) {
				System.err.println(error);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(stdout);
			closeSession(session);
		}
		return result;
	}

	public static String execCommand(Connection connection, String... commands) {
		Session session = null;
		InputStream stdout = null;
		String result = null;
		try {
			session = connection.openSession();
			StringBuffer sb = new StringBuffer();
			for (String command : commands) {
				sb.append(command + "\n");
			}
			session.execCommand(sb.toString().trim());
			stdout = session.getStdout();
			result = IOUtils.toString(stdout);
			String error = IOUtils.toString(session.getStderr());
			if (error != null && !error.trim().isEmpty()) {
				System.err.println(error);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(stdout);
			closeSession(session);
		}
		return result;
	}

	public static void closeSession(Session session) {
		if (session != null)
			session.close();
	}

}
