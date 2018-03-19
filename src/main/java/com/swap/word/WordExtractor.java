package com.swap.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import com.swap.pdf.DifferentTextObjects;
import com.swap.pdf.TextObject;

public class WordExtractor extends PDFTextStripper {

	private static final Log LOG = LogFactory.getLog(WordExtractor.class);
	private static List<TextObject> listOfWordsTextObjects = new LinkedList<TextObject>();
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

		return listOfWordsTextObjects;
	}

	public File getPdfFile() {
		return pdfFile;
	}

	/**
	 * @param list
	 * @return decided what is the X margin of the document.
	 */
	public float findXMargin(List<TextObject> list) {
		ListIterator<TextObject> listIterator = list.listIterator();
		Map<Float, Integer> map = new HashMap<Float, Integer>();
		if (!list.isEmpty()) {
			while (listIterator.hasNext()) {

				float key = listIterator.next().getStartX();

				if (map.containsKey(key)) {

					map.put(key, map.get(key) + 1);
				} else {
					map.put(key, 1);
				}
			}

		}

		Map.Entry<Float, Integer> maxEntry = null;

		for (Map.Entry<Float, Integer> entry : map.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}

		return maxEntry != null ? maxEntry.getKey() : 0;
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
			if (textPositions.get(i).getUnicode() == " ") {
				temp.setLength(0);
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
		listOfWordsTextObjects.add(txtObject);

	}

	/**
	 * @param textPositions
	 * @return return Most Used Font Size This will help reducing time parsing
	 *         String based on Font Size
	 */
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
