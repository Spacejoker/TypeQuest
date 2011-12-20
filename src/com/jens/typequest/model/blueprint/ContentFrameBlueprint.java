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

	String backgroundImage = "";
	List<ButtonBlueprint> buttonBlueprints = new ArrayList<ButtonBlueprint>();
	List<GraphicalEntityBlueprint> images = new ArrayList<GraphicalEntityBlueprint>();
	Vector2f position = null;
	
	public ContentFrameBlueprint() { }
	
	public ContentFrameBlueprint(String backgroundImage, List<ButtonBlueprint> buttonBlueprints, List<GraphicalEntityBlueprint> images, Vector2f position) {
		super();
		this.backgroundImage = backgroundImage;
		this.buttonBlueprints = buttonBlueprints;
		this.images = images;
		this.position = position;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public List<ButtonBlueprint> getButtonBlueprints() {
		return buttonBlueprints;
	}
	public void setButtonBlueprints(List<ButtonBlueprint> buttonBlueprints) {
		this.buttonBlueprints = buttonBlueprints;
	}
	public List<GraphicalEntityBlueprint> getImages() {
		return images;
	}
	public void setImages(List<GraphicalEntityBlueprint> images) {
		this.images = images;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
}
