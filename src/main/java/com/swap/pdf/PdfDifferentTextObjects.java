package com.swap.pdf;

public class PdfDifferentTextObjects {
	String font;
	float fontSize;
	String letterString;

	public PdfDifferentTextObjects(String font, float fontSize, String letterString, boolean checkflag) {
		super();
		this.font = font;
		this.fontSize = fontSize;
		this.letterString = letterString;
	}

	public PdfDifferentTextObjects() {
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public String getLetterString() {
		return letterString;
	}

	public void setLetterString(String ltrString) {
		letterString = ltrString;
	}

	@Override
	public String toString() {
		return "DifferentTextObjects [font=" + font + ", fontSize=" + fontSize + ", LetterString=" + letterString + "]";
	}
}
