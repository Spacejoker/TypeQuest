package com.jens.typequest.model.blueprint;

import org.newdawn.slick.geom.Vector2f;

/**
 * Json-able entity for just an image or something
 * 
 * @author jensa
 */
public class GraphicalEntityBlueprint implements Blueprint {

	String imagePath = "";
	Vector2f position = null;
	
	public GraphicalEntityBlueprint(String imagePath, Vector2f position) {
		super();
		this.imagePath = imagePath;
		this.position = position;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
}
