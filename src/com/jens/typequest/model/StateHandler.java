package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.ImageProvider;
import com.jens.typequest.Main;
import com.jens.typequest.TypeQuestConstants;

public class StateHandler {
	
	private List<ClickableEntity> clickEntities = new ArrayList<ClickableEntity>();
	
	public enum Mode {
		MAIN_MENU, TOWN, BATTLE;
	}
	
	Mode currentMode = Mode.MAIN_MENU;

	public Mode getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
		clickEntities.clear();
		switch (currentMode) {
		case BATTLE:
			addClickEntry(new ClickableEntity(TypeQuestConstants.ENTER_TOWN_BUTTON_ID, new Vector2f(0, 720), new Vector2f(200, 80), ImageProvider.getImage("button-town")));
			break;
		case TOWN:
			addClickEntry(new ClickableEntity(TypeQuestConstants.ENTER_BATTLE_BUTTON_ID, new Vector2f(0, 720), new Vector2f(200, 80), ImageProvider.getImage("button-battle")));
			break;
		case MAIN_MENU:
			addClickEntry(new ClickableEntity(TypeQuestConstants.ENTER_TOWN_BUTTON_ID, new Vector2f(0, 720), new Vector2f(200, 80), ImageProvider.getImage("button-town")));
			break;
		
		}
	}

	public List<ClickableEntity> getClickEntities() {
		return clickEntities;
	}

	public void setClickEntities(List<ClickableEntity> clickEntities) {
		this.clickEntities = clickEntities;
	}
	
	public void addClickEntry(ClickableEntity entity){
		clickEntities.add(entity);
	}

	public void isClicked(ClickableEntity entity) {
		if(entity.id.equals(TypeQuestConstants.ENTER_BATTLE_BUTTON_ID)){
			setCurrentMode(Mode.BATTLE);
		} else if(entity.id.equals(TypeQuestConstants.ENTER_TOWN_BUTTON_ID)){
			setCurrentMode(Mode.TOWN);
		}
	}
}
