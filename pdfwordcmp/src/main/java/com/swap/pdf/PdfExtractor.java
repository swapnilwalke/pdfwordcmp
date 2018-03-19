package com.swap.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PdfExtractor extends PDFTextStripper {
	private static final Log LOG = LogFactory.getLog(PdfExtractor.class);
	private static List<TextObject> listOfLetterTextObjects = new LinkedList<>();
	private File pdfFile;
	private PDDocument pdoc;

	public PdfExtractor() throws IOException {

	}

	public PdfExtractor(File pdfFile) throws IOException {
		super();
		this.pdfFile = pdfFile;
		this.pdoc = PDDocument.load(pdfFile);
		PDFTextStripper stripper = new PdfExtractor();
		stripper.setSortByPosition(true);
		stripper.writeText(pdoc, new OutputStreamWriter(new ByteArrayOutputStream()));
		LOG.info("Constructing PDFstripper with pdf file passed to it");
	}

	public List<TextObject> getListOfLetterTextObjects() {

		LOG.info("Returns iportant list of all data from stripped pdf");

		return listOfLetterTextObjects;
	}

	public File getPdfFile() {
		return pdfFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.pdfbox.text.PDFTextStripper#writeString(java.lang.String,
	 * java.util.List)
	 * 
	 * @param str overridden method parses doc string by string
	 * 
	 * @param textPosition gives informative data.
	 */
	@Override
	public void writeString(String str, List<TextPosition> textPositions) {

		TextObject txtObject = new TextObject(textPositions.get(0).getX(),
				textPositions.get(textPositions.size() - 1).getX(), textPositions.get(0).getY(),
				textPositions.get(0).getFontSizeInPt(), textPositions.get(0).getFont().getName(), str);
		txtObject.toString();
		listOfLetterTextObjects.add(txtObject);
	}
	
}
