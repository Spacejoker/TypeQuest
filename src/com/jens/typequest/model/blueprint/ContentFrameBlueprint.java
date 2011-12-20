package com.jens.typequest.model.blueprint;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

/**
 * Json-able blueprint for a content frame with content and position
 * 
 * @author jensa
 */
public class ContentFrameBlueprint implements Blueprint {

	String id ="";
	String backgroundImage = "";
	List<String> buttonBlueprints = new ArrayList<String>();
	List<String> images = new ArrayList<String>();
	Vector2f position = null;
	
	public ContentFrameBlueprint() { }

	public ContentFrameBlueprint(String id, String backgroundImage, List<String> buttonBlueprints, List<String> images, Vector2f position) {
		super();
		this.id = id;
		this.backgroundImage = backgroundImage;
		this.buttonBlueprints = buttonBlueprints;
		this.images = images;
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public List<String> getButtonBlueprints() {
		return buttonBlueprints;
	}

	public void setButtonBlueprints(List<String> buttonBlueprints) {
		this.buttonBlueprints = buttonBlueprints;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
	

}
