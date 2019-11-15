package cmsc420.meeshquest.part1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cmsc420.xml.XmlUtility;

/**
 * Skeleton implementation of MeeshQuest, Part 1, CMSC 420-0201, Fall 2019.
 * This does the following: (1) opens the input/output files;
 * (2) validates and parses the xml input;
 * (3) iterates through the input command nodes, but doesn't do anything
 * (4) prints the results.
 */
public class MeeshQuest {
//
// --------------------------------------------------------------------------------------------
//  Uncomment these to read from standard input and output (USE THESE FOR YOUR FINAL SUBMISSION)
	private static final boolean USE_STD_IO = true; 
	private static String inputFileName = "";
	private static String outputFileName = "";
// --------------------------------------------------------------------------------------------
//  Uncomment these to read from a file (USE THESE FOR YOUR TESTING ONLY)
//	private static final boolean USE_STD_IO = false;
//	private static String inputFileName = "test/mytest-input-7.xml";
//	private static String outputFileName = "test/mytest-output-6.xml";
// --------------------------------------------------------------------------------------------
	private final static Commands commands = new Commands();
	public static void main(String[] args) {

		// configure to read from file rather than standard input/output
		if (!USE_STD_IO) {
			try {
				System.setIn(new FileInputStream(inputFileName));
				System.setOut(new PrintStream(outputFileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		// results will be stored here
		Document resultsDoc = null;
		try {
			// generate XML document for results
			resultsDoc = XmlUtility.getDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return;
		}
		

		try {
			// validate and parse XML input
			Document input = XmlUtility.validateNoNamespace(System.in);
			
			commands.setResults(resultsDoc);
			// get input document root node
			Element rootNode = input.getDocumentElement();
			// get list of all nodes in document
			final NodeList nl = rootNode.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				// process only commands (ignore comments)
				if (nl.item(i).getNodeType() == Document.ELEMENT_NODE) {
					// get next command to process
					Element command = (Element) nl.item(i); // (ignore warning - just a skeleton)

					// ---------------------------------------
					// TODO: Add your command processing here
					// ---------------------------------------

					String name = command.getNodeName();
					if (name.equals("createCity")) {
						commands.pcreatecity(command);
					}
					else if (name.equals("listCities")) {
						commands.plistcities(command);
					}
					else if (name.equals("deleteCity")) {
						commands.pdeletecity(command);
					}
					else if (name.equals("printBinarySearchTree")) {
						commands.printbst(command);
					}
					else if (name.equals("printSGTree")) {
						commands.printsgt(command);
					}
					else if (name.equals("clearAll")) {
						commands.clearall(command);
					}
					
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {

			// ---------------------------------------
			// TODO: Add fatal-error processing here
			// ---------------------------------------
			Element fe = resultsDoc.createElement("fatalError");
			resultsDoc.appendChild(fe);
		} finally {
			try {
				// print the contents of your results document
				XmlUtility.print(resultsDoc);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}
	}
}
