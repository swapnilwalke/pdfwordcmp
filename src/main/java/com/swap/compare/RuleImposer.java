package com.swap.compare;

import java.util.Iterator;

import com.swap.pdf.PdfTextObject;
import com.swap.word.WordTextObject;

public class RuleImposer {

	WordTextObject wordTextObject;
	Iterator<PdfTextObject> pdfFileIterator;

	public RuleImposer(WordTextObject wordTextObject, Iterator<PdfTextObject> pdfFileIterator) {
		this.wordTextObject = wordTextObject;
		this.pdfFileIterator = pdfFileIterator;
	}
	
	

}
