package com.jens.typequest.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Button extends GraphicalEntity {

	private Vector2f size;
	String action = "";
	public Button(String id, Vector2f position, Image image, String action) {
		super(id, position, image);
		this.size = new Vector2f(image.getWidth(), image.getHeight());
		this.action = action;
	}
	
	public boolean isIn(float x, float y){
		return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
	}

	public Vector2f getSize() {
		return size;
	}

	public String getAction() {
		return action;
	}
	
}
