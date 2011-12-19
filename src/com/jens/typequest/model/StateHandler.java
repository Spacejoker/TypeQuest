package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.Main;
import com.jens.typequest.TypeQuestConstants;
import com.jens.typequest.loaders.ContentLoader;
import com.jens.typequest.loaders.ImageProvider;
import com.jens.typequest.ui.ContentFrame;

public class StateHandler {
	
	private List<ClickableEntity> clickEntities = new ArrayList<ClickableEntity>();
	String nextBattleId = "1";
	Battle battle;
	Player player = new Player();
	boolean showPlayerStats = false;
	TrueTypeFont font = null;
	
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
		hidePopups();
		clickEntities.clear();
		switch (currentMode) {
		case BATTLE:
			addClickEntry(new ClickableEntity(TypeQuestConstants.ENTER_TOWN_BUTTON_ID, new Vector2f(0, 720), new Vector2f(200, 80), ImageProvider.getImage("button-town")));
			battle = ContentLoader.getBattle(1);
			break;
		case TOWN:
			addClickEntry(new ClickableEntity(TypeQuestConstants.ENTER_BATTLE_BUTTON_ID, new Vector2f(0, 720), new Vector2f(200, 80), ImageProvider.getImage("button-battle")));
			addClickEntry(new ClickableEntity(TypeQuestConstants.SHOW_PLAYER_STATS, new Vector2f(200, 720), new Vector2f(200, 80), ImageProvider.getImage("button-stats")));
			break;
		case MAIN_MENU:
			addClickEntry(new ClickableEntity(TypeQuestConstants.ENTER_TOWN_BUTTON_ID, new Vector2f(0, 720), new Vector2f(200, 80), ImageProvider.getImage("button-town")));
			break;
		
		}
	}

	/**
	 * Hide popus when switching mode
	 */
	private void hidePopups() {
		showPlayerStats = false;
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

	ContentFrame contentFrame = null;
	
	public void isClicked(ClickableEntity entity) {
		if(entity.id.equals(TypeQuestConstants.ENTER_BATTLE_BUTTON_ID)){
			setCurrentMode(Mode.BATTLE);
			contentFrame = null;
		} else if(entity.id.equals(TypeQuestConstants.ENTER_TOWN_BUTTON_ID)){
			setCurrentMode(Mode.TOWN);
			contentFrame = null;
		} else if(entity.id.equals(TypeQuestConstants.SHOW_PLAYER_STATS)){
			toggleShowPlayerStats();
			if(getShowPlayerStats()){
				Image playerStatsBg = ImageProvider.getImage("playerstatsbg");
//				playerStatsBg.draw(100,100);
				List entities = Arrays.asList(new TextEntity("Current level: " + getPlayer().getLevel(), font, new Vector2f(400,250), Color.darkGray),
						new TextEntity("Xp current (next level): " + getPlayer().getXp() + "(" + getPlayer().getLevel()+ ")", font, new Vector2f(400,280), Color.darkGray),
						new TextEntity("Gold: " + getPlayer().getGold(), font, new Vector2f(400,310), Color.darkGray) );
				contentFrame = new ContentFrame(playerStatsBg, 100,100, entities);
			} else {
				contentFrame = null;
			}
		}
	}
	
	public ContentFrame getContentFrame() {
		return contentFrame;
	}

	public void setContentFrame(ContentFrame contentFrame) {
		this.contentFrame = contentFrame;
	}

	private void toggleShowPlayerStats() {
		showPlayerStats = !showPlayerStats;
	}

	public boolean getShowPlayerStats() {
		return showPlayerStats;
	}

	public void setShowPlayerStats(boolean showPlayerStats) {
		this.showPlayerStats = showPlayerStats;
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

	public TrueTypeFont getFont() {
		return font;
	}

	public void setFont(TrueTypeFont font) {
		this.font = font;
	}
}
