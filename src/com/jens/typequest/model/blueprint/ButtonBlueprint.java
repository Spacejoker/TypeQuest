package com.jens.typequest.model.blueprint;

import org.newdawn.slick.geom.Vector2f;

/**
 * Json-able entity for a button, 
 * 
 * @author jensa
 */
public class ButtonBlueprint implements Blueprint {

	String imagePath = "";
	Vector2f position = null;
	String actionId = "";

	public ButtonBlueprint(String imagePath, Vector2f position, String actionId) {
		super();
		this.imagePath = imagePath;
		this.position = position;
		this.actionId = actionId;
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
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
}
