package com.swap.compare;

import java.util.Iterator;
import java.util.Stack;
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

		compare();
		markpdf();
	}

	private void markpdf() {
		// TODO Auto-generated method stub

	}

	private void compare() {
		if (!word.getListOfWordsTextObjects().isEmpty() && !pdf.getListOfLetterTextObjects().isEmpty()) {

			Iterator<WordTextObject> wordFileIterator = word.getListOfWordsTextObjects().listIterator();

			Iterator<PdfTextObject> pdfFileIterator = pdf.getListOfLetterTextObjects().listIterator();

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

	public String stripExtraCharactersFromField(String field) {

		if (isWordDocFields(field) && field.charAt(field.length() - 1) == '>') {
			return field;
		} else {
			field = field.substring(0, field.length() - 1);
			return stripExtraCharactersFromField(field);
		}
	}
	
	public String getFieldNameWithoutMetaCharacters(String field) {
		String fieldWithoutMetaCharacters	=	null;
		
		if(isWordDocFields(field)) {
			fieldWithoutMetaCharacters	=	stripExtraCharactersFromField(field);
			
		fieldWithoutMetaCharacters=fieldWithoutMetaCharacters.replaceAll(">", "").replaceAll("<","");	
		}
		
		return fieldWithoutMetaCharacters;
	}

	public String stripMetaCharactersFromFieldAndVariable(String fieldOrVariable) {

		for (int i = 0; i < fieldOrVariable.length() - 1; i++) {
			// fieldOrVariable.charAt(i)
		}

		return null;
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
