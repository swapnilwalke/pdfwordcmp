package com.swap.word;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.swap.compare.CheckStatus;

public class WordTextObject {
	private float startX;
	private float endX;
	private float yPos;
	private float fontSize;
	private String font;
	private String letterstring;
	private List<WordDifferentTextObjects> subTextObjects;
	private CheckStatus status;
	private static final Log LOG = LogFactory.getLog(WordTextObject.class);

	public WordTextObject(float startX, float endX, float ypos, float fontSize, String font, String letterstring,
			List<WordDifferentTextObjects> differentTextObjects, CheckStatus status) {
		this.startX = startX;
		this.endX = endX;
		this.yPos = ypos;
		this.fontSize = fontSize;
		this.font = font;
		this.letterstring = letterstring;
		this.subTextObjects = differentTextObjects;
		this.setStatus(status);
	}

	public List<WordDifferentTextObjects> getSubTextObjects() {
		return subTextObjects;
	}

	public void setSubTextObjects(List<WordDifferentTextObjects> subTextObjects) {
		this.subTextObjects = subTextObjects;
	}

	public WordTextObject() {
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

	public CheckStatus getStatus() {
		return status;
	}

	public void setStatus(CheckStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		LOG.info(letterstring + ":  [startX=" + startX + ", endX=" + endX + ", yPos=" + yPos + ", fontSize=" + fontSize
				+ ", font=" + font + "]");

		return letterstring + ":  [startX=" + startX + ", endX=" + endX + ", yPos=" + yPos + ", fontSize=" + fontSize
				+ ", font=" + font + "]";

	}

}
