package com.jens.typequest.model.blueprint;

import org.newdawn.slick.geom.Vector2f;

/**
 * Json-able entity for a button, 
 * 
 * @author jensa
 */
public class ButtonBlueprint implements Blueprint {

	String id = "";
	String imagePath = "";
	Vector2f position = null;
	String actionId = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public ButtonBlueprint(String id, String imagePath, Vector2f position, String actionId) {
		super();
		this.id = id;
		this.imagePath = imagePath;
		this.position = position;
		this.actionId = actionId;
	}
	
}
