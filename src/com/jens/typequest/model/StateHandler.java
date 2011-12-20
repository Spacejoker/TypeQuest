package com.jens.typequest.model;

import java.util.List;

import org.newdawn.slick.TrueTypeFont;

import com.jens.typequest.TypeQuestConstants;
import com.jens.typequest.loaders.ContentLoader;
import com.jens.typequest.ui.ContentFrame;

public class StateHandler {
	
	String nextBattleId = "1";
	Battle battle;
	Player player = new Player();
	
	TrueTypeFont font = null;
	
	private ContentLoader loader = ContentLoader.getInstance();
	private ContentFrame contentFrame = null;
	private ContentFrame backgroundFrame = null;
	
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
		MAIN_MENU, TOWN, BATTLE, BATTLE_COMPLETE, DEFEAT;
	}
	
	Mode currentMode = null;

	public Mode getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
		backgroundFrame = null;

		switch (currentMode) {
		case BATTLE:
			backgroundFrame = loader.getContentFrame("battle");
			battle = loader.getBattle(1);
			break;
		case TOWN:
			backgroundFrame = loader.getContentFrame("town");
			break;
		case MAIN_MENU:
			backgroundFrame = loader.getContentFrame("mainMenu");
			break;
		case BATTLE_COMPLETE:
			contentFrame = loader.getContentFrame("battleComplete");
			break;
		case DEFEAT:
			contentFrame = loader.getContentFrame("defeat");
			break;
		}
	}

	public List<Button> getClickEntities() {
		if(contentFrame != null){
			return contentFrame.getButtons();
		}
		return backgroundFrame.getButtons();
	}
	
	/**
	 * Handle clicked events:
	 * @param entity
	 */
	public void isClicked(Button entity) {
		
		String action = entity.getAction();
		
		if(action.equals(TypeQuestConstants.ACTION_ENTER_TOWN)){
			setCurrentMode(Mode.TOWN);
			contentFrame = null;
		} else if(action.equals(TypeQuestConstants.ACTION_ENTER_BATTLE)){
			setCurrentMode(Mode.BATTLE);
			contentFrame = null;
		} else if(action.equals(TypeQuestConstants.ACTION_SHOW_PLAYER_STATS)){
			contentFrame = loader.getContentFrame(TypeQuestConstants.SHOW_PLAYER_STATS);
		} else if(action.equals(TypeQuestConstants.ACTION_HIDE_PLAYER_STATS)){
			contentFrame = null; // hide it
		}
	}
	
	public ContentFrame getContentFrame() {
		return contentFrame;
	}

	public void setContentFrame(ContentFrame contentFrame) {
		this.contentFrame = contentFrame;
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

	public ContentFrame getBackgroundFrame() {
		return backgroundFrame;
	}

	public void setBackgroundFrame(ContentFrame backgroundFrame) {
		this.backgroundFrame = backgroundFrame;
	}
}
