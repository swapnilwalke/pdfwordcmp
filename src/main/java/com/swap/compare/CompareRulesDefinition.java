package com.swap.compare;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.swap.pdf.PdfExtractor;
import com.swap.pdf.PdfTextObject;

public abstract class CompareRulesDefinition {
	private PdfTextObject applyRulesOnThisObject;
	private static final Log LOG = LogFactory.getLog(PdfExtractor.class);

	public CompareRulesDefinition(PdfTextObject applyRulesOnThisObject) {
		super();
		this.applyRulesOnThisObject = applyRulesOnThisObject;
	}

	public PdfTextObject getApplyRulesOnThisObject() {
		return applyRulesOnThisObject;
	}

	public boolean applySpaceCheckRule(CompareRules rules, PdfTextObject textObject) {
		if (rules.equals(CompareRules.LINE_SPACE_CHECK)) {
			char[] letterString = textObject.getLetterstring().toCharArray();
			for (int i = 0; i < letterString.length; i++) {
				if (letterString[i] == ' ' && i < letterString.length - 1 && i > 0) {
					if (letterString[i + 1] != ' ') {
						LOG.info(CompareRules.LINE_SPACE_CHECK.toString() + ": Passed ");
						return true;
					} else {
						if (letterString[i - 1] != '.') {
							LOG.error(CompareRules.LINE_SPACE_CHECK.toString() + ": Breached");
							return false;
						}
					}
				}
			}
		}

		return false;
	}

}
