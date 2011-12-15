package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.TypeQuestConstants;
import com.jens.typequest.loaders.ContentLoader;
import com.jens.typequest.loaders.ImageProvider;

public class StateHandler {
	
	private List<ClickableEntity> clickEntities = new ArrayList<ClickableEntity>();
	String nextBattleId = "1";
	Battle battle;
	Player player = new Player();
	
	private StateHandler() {
	}
	
	static StateHandler instance = null;
	
	public static StateHandler getInstance(){
		if(instance == null){
			instance = new StateHandler();
		}
		return instance;
	}
	
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
			battle = ContentLoader.getBattle(nextBattleId);
		} else if(entity.id.equals(TypeQuestConstants.ENTER_TOWN_BUTTON_ID)){
			setCurrentMode(Mode.TOWN);
		}
	}

	public String getNextBattleId() {
		return nextBattleId;
	}

	public void setNextBattleId(String nextBattleId) {
		this.nextBattleId = nextBattleId;
	}

	public Battle getBattle() {
		return battle;
	}

	public void setBattle(Battle battle) {
		this.battle = battle;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
