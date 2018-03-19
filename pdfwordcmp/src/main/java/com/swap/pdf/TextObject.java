package com.swap.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TextObject {
	float startX;
	float endX;
	float yPos;
	float fontSize;
	String font;
	String letterstring;
	private static final Log LOG = LogFactory.getLog(TextObject.class);

	public TextObject(float startX, float endX, float ypos, float fontSize, String font, String letterstring) {
		super();
		this.startX = startX;
		this.endX = endX;
		this.yPos = ypos;
		this.fontSize = fontSize;
		this.font = font;
		this.letterstring = letterstring;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public void setEndX(float endX) {
		this.endX = endX;
	}

	public void setY(float y) {
		yPos = y;
	}

	public float getStartX() {
		return startX;
	}

	public float getEndX() {
		return endX;
	}

	public float getY() {
		return yPos;
	}

	public float getFontSize() {
		return fontSize;
	}

	public String getFont() {
		return font;
	}

	public String getLetterstring() {
		return letterstring;
	}

	@Override
	public String toString() {
		LOG.info(letterstring + ":  [startX=" + startX + ", endX=" + endX + ", yPos=" + yPos + ", fontSize=" + fontSize
				+ ", font=" + font + "]");

		return letterstring + ":  [startX=" + startX + ", endX=" + endX + ", yPos=" + yPos + ", fontSize=" + fontSize
				+ ", font=" + font + "]";

	}

}
