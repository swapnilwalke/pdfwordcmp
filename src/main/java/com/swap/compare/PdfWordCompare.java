package com.swap.compare;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.swap.pdf.PdfExtractor;
import com.swap.pdf.PdfTextObject;
import com.swap.word.WordExtractor;
import com.swap.word.WordTextObject;

public class PdfWordCompare {
	private static final Log LOG = LogFactory.getLog(PdfWordCompare.class);
	private PdfExtractor pdf;
	private WordExtractor word;

	public PdfWordCompare(PdfExtractor pdf, WordExtractor word) {
		if (pdf != null && word != null) {
			this.pdf = pdf;
			this.word = word;
		}
	}

	public PdfWordCompare() {
	}

	public void processWordBeforeCompare() {

		if (!word.getListOfWordsTextObjects().isEmpty() && !pdf.getListOfLetterTextObjects().isEmpty()) {
			adjustWordByYAxis();
			adjustWordByXAxis();
		} else {
			LOG.error("pdf and word extracted data cannot be empty, no comparison if one of them is empty");
		}
	}

	public void compareMyDocs() {
		if (!word.getListOfWordsTextObjects().isEmpty() && !pdf.getListOfLetterTextObjects().isEmpty()) {
			Iterator<WordTextObject> wordIterator = word.getListOfWordsTextObjects().listIterator();
			Iterator<PdfTextObject> pdfIterator = pdf.getListOfLetterTextObjects().listIterator();
			while (wordIterator.hasNext()) {
				String[] wordList = wordIterator.next().getLetterstring().split("\\s");
				for (int i = 0; i < wordList.length; i++) {

				}
			}
		}

	}

	public boolean isWordDocFields(String word) {

		Pattern pattern = Pattern.compile("\\<{2}+\\w+\\>{2}+");

		Matcher matcher = pattern.matcher(word);

		return matcher.find() ? true : false;
	}

	public boolean isWordDocVariables(String word) {

		Pattern pattern = Pattern.compile("\\<{2}+@{2}+\\w+\\>{2}+");

		Matcher matcher = pattern.matcher(word);

		return matcher.find() ? true : false;
	}

	private void adjustWordByXAxis() {
		float wordMargin = word.findXMargin(word.getListOfWordsTextObjects());
		float pdfMargin = pdf.findXMargin(pdf.getListOfLetterTextObjects());
		float xAxisdiff = wordMargin - pdfMargin;
		for (WordTextObject wordTextObject : word.getListOfWordsTextObjects()) {
			wordTextObject.setStartX(wordTextObject.getStartX() - xAxisdiff);
			wordTextObject.setEndX(wordTextObject.getEndX() - xAxisdiff);
		}

	}

	private void adjustWordByYAxis() {
		float yAxisdiff = word.getListOfWordsTextObjects().get(0).getY()
				- pdf.getListOfLetterTextObjects().get(0).getY();
		for (WordTextObject wordTextObject : word.getListOfWordsTextObjects()) {
			wordTextObject.setY(wordTextObject.getY() - yAxisdiff);
		}

	}

}
