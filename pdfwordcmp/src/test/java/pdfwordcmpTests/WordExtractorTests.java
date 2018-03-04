package pdfwordcmpTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

import com.swap.word.WordExtractor;
import com.swap.compare.PdfWordCompare;
import com.swap.pdf.PdfExtractor;
import com.swap.pdf.TextObject;

public class WordExtractorTests {

	@Test
	public void TestPdfExtractorCreation() throws IOException {
		File pdfFile = new File("./Resources/mockup.pdf");
		WordExtractor wordextractor = new WordExtractor(pdfFile);
		assertNotNull("Successfully create PdfExtractor", wordextractor.getPdfFile() != null);
	}

	@Test
	public void testIfWriteStringIsWorkingForWordFile() throws IOException {
		File pdfFile = new File("./Resources/mockup.pdf");
		WordExtractor wordExtractor = new WordExtractor(pdfFile);
		List<TextObject> lst = wordExtractor.getListOfWordsTextObjects();
		TextObject textObject = lst.get(0);		
		assertEquals("textObject create " + textObject.getFontSize() + " :" + textObject.getLetterstring(),
				textObject.getLetterstring(), lst.get(0).getLetterstring());
	}

	@Test
	public void getWordAndPdfLists() throws IOException {
		File wordFile = new File("./Resources/mockup.pdf");
		WordExtractor wordextractor = new WordExtractor(wordFile);
		File pdfFile = new File("./Resources/letter.pdf");
		PdfExtractor pdfextractor = new PdfExtractor(pdfFile);
		PdfWordCompare compare	=	new PdfWordCompare(pdfextractor, wordextractor);
		compare.processWordBeforeCompare();		
		assertEquals(wordextractor.getListOfWordsTextObjects().get(0).getY(),
				pdfextractor.getListOfLetterTextObjects().get(0).getY(),0.0);
	}

}
