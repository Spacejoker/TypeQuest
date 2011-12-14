package com.jens.typequest.model;

import org.newdawn.slick.geom.Vector2f;

public class Entity {

	String id;
	Vector2f position;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public void update(int delta) {
			
	}
	
}
