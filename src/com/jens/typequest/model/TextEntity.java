package com.jens.typequest.model;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class TextEntity extends GraphicalEntity {

	Color color;
	private String text;
	
	public TextEntity(String text, Vector2f pos, Color color) {
		super("", pos, null);
		this.color = color;
		this.text = text;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
