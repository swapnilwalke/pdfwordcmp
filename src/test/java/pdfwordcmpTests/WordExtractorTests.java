package pdfwordcmpTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;

import com.swap.word.WordExtractor;
import com.swap.word.WordTextObject;
import com.swap.compare.CheckStatus;
import com.swap.compare.PdfWordCompare;
import com.swap.pdf.PdfExtractor;
import com.swap.pdf.PdfTextObject;

public class WordExtractorTests {
	private static final Log LOG = LogFactory.getLog(PdfExtractorTests.class);
	File wordFile = new File("./Resources/mockup.pdf");

	@Ignore
	@Test
	public void TestPdfExtractorCreation() throws IOException {
		WordExtractor wordextractor = new WordExtractor(wordFile);
		assertNotNull("Successfully create PdfExtractor", wordextractor.getPdfFile() != null);
	}

	@Ignore
	@Test
	public void testIfWriteStringIsWorkingForWordFile() throws IOException {
		// File pdfFile = new File("./Resources/mockup.pdf");
		WordExtractor wordExtractor = new WordExtractor(wordFile);
		List<WordTextObject> lst = wordExtractor.getListOfWordsTextObjects();
		WordTextObject textObject = lst.get(0);
		assertEquals("textObject create " + textObject.getFontSize() + " :" + textObject.getMockupString(),
				textObject.getMockupString(), lst.get(0).getMockupString());
	}

	@Ignore
	@Test
	public void getWordAndPdfLists() throws IOException {
		// File wordFile = new File("./Resources/mockup.pdf");
		WordExtractor wordextractor = new WordExtractor(wordFile);
		List<WordTextObject> words = wordextractor.getListOfWordsTextObjects();
		assertNotNull(words);
		File pdfFile = new File("./Resources/letter.pdf");
		PdfExtractor pdfextractor = new PdfExtractor(pdfFile);
		List<PdfTextObject> pdf = pdfextractor.getListOfLetterTextObjects();
		assertNotNull(pdf);
		PdfWordCompare compare = new PdfWordCompare(pdfextractor, wordextractor);
		compare.processWordBeforeCompare();
		assertEquals(wordextractor.getListOfWordsTextObjects().get(0).getY(),
				pdfextractor.getListOfLetterTextObjects().get(0).getY(), 0.0);
	}

	@Ignore
	@Test
	public void testRegularExpressionsToExtractsFieldsFromMockup() {
		PdfTextObject test = new PdfTextObject(0, 0, 0, 0, null,
				"<<Betty1>> <bought some <<butter>>  <<but>> the  <<butterwasso555bitter>>", null,
				CheckStatus.UNCHECKED);
		PdfWordCompare compare = new PdfWordCompare();
		String[] testList = test.getLetterstring().split("\\s");

		for (int i = 0; i < testList.length; i++) {
			if (compare.isWordDocFields(testList[i])) {
				System.out.println(testList[i]);
			}
		}
		assertTrue(compare.isWordDocFields(test.getLetterstring()));
	}

	@Ignore
	@Test
	public void testRegularExpressionsToExtractsVariablesFromMockup() {
		PdfTextObject test = new PdfTextObject(0, 0, 0, 0, null,
				"<<@@Betty1>> <bought some <<butter>> <<but>> the  <<@@butterwasso555bitter>>", null,
				CheckStatus.UNCHECKED);
		PdfWordCompare compare = new PdfWordCompare();

		assertTrue(compare.isWordDocVariables(test.getLetterstring()));

	}

	@Ignore
	@Test
	public void testExtraCharacterStrinpper() {
		String testString = "<<StripMeOfExtraString>>.|";
		PdfWordCompare compare = new PdfWordCompare();
		System.out.println(compare.stripExtraCharactersFromField(testString));
		System.out.println(compare.getFieldNameWithoutMetaCharacters(testString));
	}

	@Test
	public void testIndexPositionOfAField() throws IOException {
		WordTextObject test = new WordTextObject(0, 0, 0, 0, null,
				"<<@@Betty1>> <bought some <<butter>> <<but>> the  <<@@butterwasso555bitter>>", null,
				CheckStatus.UNCHECKED);
		WordExtractor wordextractor = new WordExtractor();
		List<String> tempFieldList	=	wordextractor.findFieldStartAndEndIndexAndAddToList(test);
		List<String> tempVariableList	=	wordextractor.findVariableStartAndEndIndexAndAddToList(test);
		System.out.println("char is "+test.getMockupString().charAt(0)+"and "+test.getMockupString().charAt(11));
		
		assertNotNull(tempFieldList);
		
		for(String s : tempFieldList) {
			System.out.println(s);
		}
		
		assertNotNull(tempVariableList);
		
		for(String s: tempVariableList) {
			System.out.println(s);
		}
		
	}

}
