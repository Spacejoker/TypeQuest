package com.jens.typequest.ui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.model.Button;
import com.jens.typequest.model.GraphicalEntity;

public class ContentFrame {

	String id = "";
	Image background = null;
	List<GraphicalEntity> entities = new ArrayList<GraphicalEntity>();
	List<Button> buttons = new ArrayList<Button>();
	Vector2f position;
	
	public ContentFrame() {
	}

	public ContentFrame(String id, Image background, List<GraphicalEntity> entities, Vector2f position) {
		super();
		this.id = id;
		this.background = background;
		this.entities = entities;
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Image getBackground() {
		return background;
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	public List<GraphicalEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<GraphicalEntity> entities) {
		this.entities = entities;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}	
}
