package com.jens.typequest.ui;

import java.util.List;

import org.newdawn.slick.Image;

import com.jens.typequest.model.ClickableEntity;
import com.jens.typequest.model.GraphicalEntity;

public class ContentFrame {

	Image background = null;
	List<GraphicalEntity> entities = null;
	int x = 0;
	int y = 0;
	
	public ContentFrame(Image bg, int x, int y, List<GraphicalEntity> entities) {
		this.background = bg;
		this.entities = entities;
		this.x = x;
		this.y = y;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
