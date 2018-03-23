package pdfwordcmpTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.swap.compare.PdfWordCompare;
import com.swap.pdf.PdfExtractor;
import com.swap.pdf.PdfTextObject;

public class PdfExtractorTests {
	private static final Log LOG = LogFactory.getLog(PdfExtractorTests.class);
	//ClassLoader classloader	=	new PdfExtractorTests().getClass().getClassLoader();
	File pdfFile = new File("./Resources/letter.pdf");
	@Test
	public void TestPdfExtractorCreation() throws IOException {		
		PdfExtractor pdfextractor = new PdfExtractor(pdfFile);
		assertNotNull("Successfully create PdfExtractor", pdfextractor.getPdfFile() != null);
	}

	@Test
	public void TestIfWriteStringIsWorking() throws IOException {
		//File pdfFile = new File("./Resources/letter.pdf");
		PdfExtractor pdfextractor = new PdfExtractor(pdfFile);
		List<PdfTextObject> lst = pdfextractor.getListOfLetterTextObjects();
		PdfTextObject textObject = lst.get(0);
		assertEquals("textObject create " + textObject.getFontSize() + " :" + textObject.getLetterstring(),
				textObject.getLetterstring(), lst.get(0).getLetterstring());
	}

}
