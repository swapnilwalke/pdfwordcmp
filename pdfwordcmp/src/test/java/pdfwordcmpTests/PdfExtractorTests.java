package pdfwordcmpTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

import com.swap.pdf.PdfExtractor;
import com.swap.pdf.TextObject;

public class PdfExtractorTests {

	@Test
	public void TestPdfExtractorCreation() throws IOException {
		File pdfFile = new File("./Resources/letter.pdf");
		PdfExtractor pdfextractor = new PdfExtractor(pdfFile);
		assertNotNull("Successfully create PdfExtractor", pdfextractor.getPdfFile() != null);
	}

	@Test
	public void TestIfWriteStringIsWorking() throws IOException {
		File pdfFile = new File("./Resources/letter.pdf");
		PdfExtractor pdfextractor = new PdfExtractor(pdfFile);
		List<TextObject> lst = pdfextractor.getListOfLetterTextObjects();
		TextObject textObject = lst.get(0);
		assertEquals("textObject create " + textObject.getFontSize() + " :" + textObject.getLetterstring(),
				textObject.getLetterstring(), lst.get(0).getLetterstring());
	}

}
