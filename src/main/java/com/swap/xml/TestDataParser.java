package com.swap.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;

import jdk.internal.org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class TestDataParser {

	private String filepath;
	private File xmlFile;
	private Pattern pattern;
	private Matcher matcher;

	public TestDataParser(String filePath) {

		this.filepath = filePath;
		pattern = Pattern.compile("([^\\s]+(\\.(?i)(xml|XML))$)");
		matcher = pattern.matcher(filepath);
		if (matcher.matches()) {
			xmlFile = new File(filepath);
		} else {
			new TestDataParser();
		}

	}

	private TestDataParser() {

	}

	public Map<String, String> getdataFromTestFile() throws org.xml.sax.SAXException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			dBuilder = docFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE
						&& nodeList.item(i).getNodeName().equalsIgnoreCase("data")) {
					NodeList dataNodeList = nodeList.item(i).getChildNodes();
					for (int x = 0; x < dataNodeList.getLength(); x++) {
						dataMap.put(dataNodeList.item(x).getNodeName(), dataNodeList.item(x).getTextContent());

					}
				}
			}

		} catch (ParserConfigurationException  e) {
			e.printStackTrace();
		}catch( IOException e) {
			e.printStackTrace();
		}
		return dataMap;
	}
}