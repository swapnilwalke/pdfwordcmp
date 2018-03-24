package pdfwordcmpTests;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.swap.compare.PdfWordCompare;
import com.swap.word.WordExtractor;
import com.swap.word.WordTextObject;
import com.swap.xml.TestDataParser;

public class TestDataXmlTests {
	File wordFile = new File("./Resources/mockup.pdf");

	@Test
	public void checkandmatchfieldfromMockup() throws IOException, SAXException {
		WordExtractor wordExtractor = new WordExtractor(wordFile);
		TestDataParser parser = new TestDataParser("./Resources/Test.xml");
		PdfWordCompare compare = new PdfWordCompare();
		Map<String, String> map = parser.getdataFromTestFile();
		List<WordTextObject> lst = wordExtractor.getListOfWordsTextObjects();
		for (WordTextObject word : lst) {
			String[] strarr = word.getLetterstring().split("\\s");
			for (int i = 0; i < strarr.length; i++) {
				if (compare.isWordDocFields(strarr[i])) {
					for (Map.Entry<String, String> entry : map.entrySet()) {
						if (entry.getKey() == strarr[i]) {
							System.out.println(entry.getKey() + " : " + entry.getValue());
						}
					}
				}

			}
		}

	}

	@Test
	public void printTextXml() throws SAXException {
		TestDataParser parser = new TestDataParser("./Resources/Test.xml");
		Map<String, String> map = parser.getdataFromTestFile();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

}
