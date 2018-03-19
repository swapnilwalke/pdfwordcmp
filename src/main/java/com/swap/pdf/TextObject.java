package com.swap.pdf;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.swap.pdf.DifferentTextObjects;

public class TextObject {
	float startX;
	float endX;
	float yPos;
	float fontSize;
	String font;
	String letterstring;
	List<DifferentTextObjects> subTextObjects;
	private static final Log LOG = LogFactory.getLog(TextObject.class);

	public TextObject(float startX, float endX, float ypos, float fontSize, String font, String letterstring,
			List<DifferentTextObjects> subTextObjects) {
		this.startX = startX;
		this.endX = endX;
		this.yPos = ypos;
		this.fontSize = fontSize;
		this.font = font;
		this.letterstring = letterstring;
		this.subTextObjects = subTextObjects;
	}

	public List<DifferentTextObjects> getSubTextObjects() {
		return subTextObjects;
	}

	public void setSubTextObjects(List<DifferentTextObjects> subTextObjects) {
		this.subTextObjects = subTextObjects;
	}

	public TextObject() {
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

	public void setFontSize(float fntSize) {
		fontSize = fntSize;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String fntName) {
		font = fntName;
	}

	public String getLetterstring() {
		return letterstring;
	}

	public void setLetterstring(String string) {
		letterstring = string;

	}

	@Override
	public String toString() {
		LOG.info(letterstring + ":  [startX=" + startX + ", endX=" + endX + ", yPos=" + yPos + ", fontSize=" + fontSize
				+ ", font=" + font + "]");

		return letterstring + ":  [startX=" + startX + ", endX=" + endX + ", yPos=" + yPos + ", fontSize=" + fontSize
				+ ", font=" + font + "]";

	}

}
