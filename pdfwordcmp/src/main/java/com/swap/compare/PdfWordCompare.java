package com.swap.compare;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.swap.pdf.PdfExtractor;
import com.swap.pdf.TextObject;
import com.swap.word.WordExtractor;

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

	public void processWordBeforeCompare() {

		if (!word.getListOfWordsTextObjects().isEmpty() && !pdf.getListOfLetterTextObjects().isEmpty()) {
			adjustWordByYAxis();
			adjustWordByXAxis();
		} else {
			LOG.error("pdf and word extracted data cannot be empty, no comparison if one of them is empty");
		}
	}

	private void adjustWordByXAxis() {
		float xAxisdiff = 0;
		if (word.getListOfWordsTextObjects().get(0).getStartX() >= pdf.getListOfLetterTextObjects().get(0)
				.getStartX()) {
			xAxisdiff = word.getListOfWordsTextObjects().get(0).getStartX()
					- pdf.getListOfLetterTextObjects().get(0).getStartX();
			for (TextObject wordTextObject : word.getListOfWordsTextObjects()) {
				wordTextObject.setStartX(wordTextObject.getStartX() - xAxisdiff);
				wordTextObject.setEndX(wordTextObject.getEndX() - xAxisdiff);
			}
		} else {
			for (TextObject wordTextObject : word.getListOfWordsTextObjects()) {
				wordTextObject.setStartX(wordTextObject.getStartX() + xAxisdiff);
				wordTextObject.setEndX(wordTextObject.getEndX() + xAxisdiff);
			}
		}

	}

	private void adjustWordByYAxis() {
		float yAxisdiff = 0;
		if (word.getListOfWordsTextObjects().get(0).getY() >= pdf.getListOfLetterTextObjects().get(0).getY()) {
			yAxisdiff = word.getListOfWordsTextObjects().get(0).getY() - pdf.getListOfLetterTextObjects().get(0).getY();
			for (TextObject wordTextObject : word.getListOfWordsTextObjects()) {
				wordTextObject.setY(wordTextObject.getY() - yAxisdiff);
			}
		} else {
			for (TextObject wordTextObject : word.getListOfWordsTextObjects()) {
				wordTextObject.setY(wordTextObject.getY() + yAxisdiff);
			}
		}

	}

}
