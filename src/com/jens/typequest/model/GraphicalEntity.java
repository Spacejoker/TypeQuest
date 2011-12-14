package com.jens.typequest.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class GraphicalEntity extends Entity {

	Image image;
	boolean visible = true;

	public GraphicalEntity(String id, Vector2f position, Image image) {
		this.position = position;
		this.id = id;
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
