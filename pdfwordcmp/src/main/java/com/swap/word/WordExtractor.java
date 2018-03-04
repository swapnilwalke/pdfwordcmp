package com.swap.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import com.swap.pdf.TextObject;

public class WordExtractor extends PDFTextStripper {

	private static final Log LOG = LogFactory.getLog(WordExtractor.class);
	private static List<TextObject> listOfWordsTextObjects = new ArrayList<>();
	private File pdfFile;
	private PDDocument pdoc;

	public WordExtractor() throws IOException {

	}

	public WordExtractor(File pdfFile) throws IOException {
		super();
		this.pdfFile = pdfFile;
		this.pdoc = PDDocument.load(pdfFile);
		PDFTextStripper stripper = new WordExtractor();
		stripper.setSortByPosition(true);
		stripper.writeText(pdoc, new OutputStreamWriter(new ByteArrayOutputStream()));
		LOG.info("Constructing PDFstripper with pdf file passed to it");
	}

	public List<TextObject> getListOfWordsTextObjects() {

		LOG.info("Returns iportant list of all data from stripped pdf");

		return listOfWordsTextObjects;
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
		if (str.length()!=1) {
			TextObject txtObject = new TextObject(textPositions.get(0).getX(),
					textPositions.get(textPositions.size() - 1).getX(), textPositions.get(0).getY(),
					textPositions.get(0).getFontSizeInPt(), textPositions.get(0).getFont().getName(), str);
			txtObject.toString();
			listOfWordsTextObjects.add(txtObject);
		}
	}
}
