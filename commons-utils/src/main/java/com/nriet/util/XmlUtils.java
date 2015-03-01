package com.nriet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class XmlUtils {
	// 文档
	private static Document doc = null;
	// 根节点
	private static Element root = null;

	private static String seperator = ",";

	public static void init(String xmlContent) {
		// SAXReader reader = new SAXReader();
		try {
			doc = DocumentHelper.parseText(xmlContent);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void init(InputStream is) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		init(sb.toString());
	}

	public static void init(File file) {
		try {
			init(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void setSeperator(String seperator) {
		XmlUtils.seperator = seperator;
	}

	// /////////////////////////////////////////////////////////////
	/**
	 * 获取任意节点
	 * 
	 * @param nodeName
	 * @return
	 */
	public static Node getNode(String nodeName) {
		return root.selectSingleNode("//" + nodeName);
	}

	@SuppressWarnings("unchecked")
	public static List<Node> getNodes(String nodeName) {
		return root.selectNodes("//" + nodeName);
	}

	// /////////////////////////////////////////////////////////////
	/**
	 * 获取唯一节点的文本值
	 * 
	 * @param node
	 * @return
	 */
	public static String getNodeString(Node node) {
		return node.getText();
	}

	public static int getNodeInt(Node node) {
		return Integer.parseInt(node.getText());
	}

	public static float getNodeFloat(Node node) {
		return Float.parseFloat(node.getText());
	}

	public static double getNodeDouble(Node node) {
		return Double.parseDouble(node.getText());
	}

	public static Boolean getNodeBoolean(Node node) {
		return Boolean.parseBoolean(node.getText());
	}

	// /////////////////////////////////////////////////////////////

	public static String getAttrString(Node node, String attr) {
		return node.valueOf("@" + attr);
	}

	public static int getAttrInt(Node node, String attr) {
		return Integer.parseInt(getAttrString(node, attr));
	}

	public static float getAttrFloat(Node node, String attr) {
		return Float.parseFloat(getAttrString(node, attr));
	}

	public static double getAttrDouble(Node node, String attr) {
		return Double.parseDouble(getAttrString(node, attr));
	}

	public static boolean getAttrBoolean(Node node, String attr) {
		return Boolean.parseBoolean(getAttrString(node, attr));
	}

	// /////////////////////////////////////////////////////////////
	public static String getNodeString(String nodeName) {
		return getNode(nodeName).getText();
	}

	public static int getNodeInt(String nodeName) {
		return Integer.parseInt(getNodeString(nodeName));
	}

	public static float getNodeFloat(String nodeName) {
		return Float.parseFloat(getNodeString(nodeName));
	}

	public static double getNodeDouble(String nodeName) {
		return Double.parseDouble(getNodeString(nodeName));
	}

	public static boolean getNodeBoolean(String nodeName) {
		return Boolean.parseBoolean(getNodeString(nodeName));
	}

	// /////////////////////////////////////////////////////////////
	public static String getAttrString(String nodeName, String attr) {
		return getNode(nodeName).valueOf("@" + attr);
	}

	public static int getAttrInt(String nodeName, String attr) {
		return Integer.parseInt(getAttrString(nodeName, attr));
	}

	public static float getAttrFloat(String nodeName, String attr) {
		return Float.parseFloat(getAttrString(nodeName, attr));
	}

	public static double getAttrDouble(String nodeName, String attr) {
		return Double.parseDouble(getAttrString(nodeName, attr));
	}

	public static boolean getAttrBoolean(String nodeName, String attr) {
		return Boolean.parseBoolean(getAttrString(nodeName, attr));
	}

	// /////////////////////////////////////////////////////////////
	/**
	 * 获取多个节点的文本值
	 * 
	 * @param nodeName
	 * @return
	 */
	public static List<String> getNodeStringList(String nodeName) {
		List<Node> nodes = getNodes(nodeName);
		List<String> result = new ArrayList<String>(nodes.size());
		for (Node node : nodes) {
			result.add(node.getText());
		}
		return result;
	}

	public static List<Integer> getNodeIntList(String nodeName) {
		List<Node> nodes = getNodes(nodeName);
		List<Integer> result = new ArrayList<Integer>(nodes.size());
		for (Node node : nodes) {
			result.add(Integer.parseInt(node.getText()));
		}
		return result;
	}

	public static List<Float> getNodeFloatList(String nodeName) {
		List<Node> nodes = getNodes(nodeName);
		List<Float> result = new ArrayList<Float>(nodes.size());
		for (Node node : nodes) {
			result.add(Float.parseFloat(node.getText()));
		}
		return result;
	}

	public static List<Double> getNodeDoubleList(String nodeName) {
		List<Node> nodes = getNodes(nodeName);
		List<Double> result = new ArrayList<Double>(nodes.size());
		for (Node node : nodes) {
			result.add(Double.parseDouble(node.getText()));
		}
		return result;
	}

	public static List<Boolean> getNodeBooleanList(String nodeName) {
		List<Node> nodes = getNodes(nodeName);
		List<Boolean> result = new ArrayList<Boolean>(nodes.size());
		for (Node node : nodes) {
			result.add(Boolean.parseBoolean(node.getText()));
		}
		return result;
	}

	// /////////////////////////////////////////////////////////////
	/**
	 * 获取多个节点的属性值
	 * 
	 * @param nodeName
	 * @param attr
	 * @return
	 */
	public static List<String> getAttrStringList(String nodeName, String attr) {
		List<Node> nodes = getNodes(nodeName);
		List<String> result = new ArrayList<String>(nodes.size());
		for (Node node : nodes) {
			result.add(getAttrString(node, attr));
		}
		return result;
	}

	public static List<Integer> getAttrIntList(String nodeName, String attr) {
		List<Node> nodes = getNodes(nodeName);
		List<Integer> result = new ArrayList<Integer>(nodes.size());
		for (Node node : nodes) {
			result.add(getAttrInt(node, attr));
		}
		return result;
	}

	public static List<Float> getAttrFloatList(String nodeName, String attr) {
		List<Node> nodes = getNodes(nodeName);
		List<Float> result = new ArrayList<Float>(nodes.size());
		for (Node node : nodes) {
			result.add(getAttrFloat(node, attr));
		}
		return result;
	}

	public static List<Double> getAttrDoubleList(String nodeName, String attr) {
		List<Node> nodes = getNodes(nodeName);
		List<Double> result = new ArrayList<Double>(nodes.size());
		for (Node node : nodes) {
			result.add(getAttrDouble(node, attr));
		}
		return result;
	}

	public static List<Boolean> getAttrBooleanList(String nodeName, String attr) {
		List<Node> nodes = getNodes(nodeName);
		List<Boolean> result = new ArrayList<Boolean>(nodes.size());
		for (Node node : nodes) {
			result.add(getAttrBoolean(node, attr));
		}
		return result;
	}

}
