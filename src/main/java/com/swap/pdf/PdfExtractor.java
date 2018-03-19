package com.swap.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PdfExtractor extends PDFTextStripper {
	private static final Log LOG = LogFactory.getLog(PdfExtractor.class);
	private static List<TextObject> listOfLetterTextObjects = new LinkedList<TextObject>();
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

	}

	public List<TextObject> getListOfLetterTextObjects() {

		return listOfLetterTextObjects;
	}

	public File getPdfFile() {
		LOG.info("File Name : " + pdfFile.getName());
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
		String flagFontName = checkForMostUsedFontNameInString(textPositions);
		float flagFontSize = checkForMostUsedFontSizeInString(textPositions);

		List<DifferentTextObjects> differentTextObjects = new ArrayList<DifferentTextObjects>();
		DifferentTextObjects tempTextObject = new DifferentTextObjects();
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < textPositions.size(); i++) {

			if (textPositions.get(i).getFontSizeInPt() != flagFontSize
					|| textPositions.get(i).getFont().getName() != flagFontName) {

				if (tempTextObject.getFont() == null || tempTextObject.getFontSize() == 0) {
					tempTextObject.setFont(textPositions.get(i).getFont().getName());
					tempTextObject.setFontSize(textPositions.get(i).getFontSizeInPt());

				}

				if (tempTextObject.getFont() == textPositions.get(i).getFont().getName()
						&& tempTextObject.getFontSize() == textPositions.get(i).getFontSizeInPt()) {

					temp.append(textPositions.get(i).getUnicode());

				}

			}
			if (temp.length() > 0 && str.contains(temp)) {

				tempTextObject.setLetterString(temp.toString());
			}

		}
		differentTextObjects.add(tempTextObject);

		TextObject txtObject = new TextObject(textPositions.get(0).getX(),
				textPositions.get(textPositions.size() - 1).getX(), textPositions.get(0).getY(),
				textPositions.get(0).getFontSizeInPt(), textPositions.get(0).getFont().getName(), str,
				differentTextObjects);
		txtObject.toString();
		listOfLetterTextObjects.add(txtObject);

	}
	private float checkForMostUsedFontSizeInString(List<TextPosition> textPositions) {
		HashMap<Float, Integer> fontCount = new HashMap<Float, Integer>();
		for (int i = 0; i < textPositions.size(); i++) {
			float key = textPositions.get(i).getFontSizeInPt();
			if (fontCount.containsKey(key)) {
				fontCount.put(key, fontCount.get(key) + 1);
			} else {
				fontCount.put(key, 1);
			}
		}
		Map.Entry<Float, Integer> maxEntry = null;
		for (Map.Entry<Float, Integer> entry : fontCount.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}

		return maxEntry != null ? maxEntry.getKey() : null;
	}

	private String checkForMostUsedFontNameInString(List<TextPosition> textPositions) {
		HashMap<String, Integer> fontCount = new HashMap<String, Integer>();
		for (int i = 0; i < textPositions.size(); i++) {
			String key = textPositions.get(i).getFont().getName();
			if (fontCount.containsKey(key)) {
				fontCount.put(key, fontCount.get(key) + 1);
			} else {
				fontCount.put(key, 1);
			}
		}
		Map.Entry<String, Integer> maxEntry = null;
		for (Map.Entry<String, Integer> entry : fontCount.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}

		return maxEntry != null ? maxEntry.getKey() : null;
	}


}
