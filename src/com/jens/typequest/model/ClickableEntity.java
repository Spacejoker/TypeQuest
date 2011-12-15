package com.jens.typequest.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class ClickableEntity extends GraphicalEntity {

	private Vector2f size;

	public ClickableEntity(String id, Vector2f position, Vector2f size, Image image) {
		super(id, position, image);
		this.size = size;
	}

	public boolean isIn(float x, float y){
		return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
	}
	
}
