package com.jens.typequest;

public interface TypeQuestConstants {

	public static int WALL_X = 150;
	public static float TEXT_BOX_X = 350;
	public static float TEXT_BOX_Y = 80;
	public static float TEXT_X = TEXT_BOX_X + 120;
	public static float TEXT_Y = TEXT_BOX_Y + 20;
	public static float PIC_X = TEXT_BOX_X + 10;
	
	//IDs
	public static String ENTER_BATTLE_BUTTON_ID = "enter-battle-button";
	public static String ENTER_TOWN_BUTTON_ID = "enter-town-button";
	public static String ENTER_MAIN_MENU_BUTTON_ID = "enter-mainmenu-button";
	public static String SHOW_PLAYER_STATS = "playerStats";
	
	
	// ACTIONS
	public static String ACTION_ENTER_TOWN = "action-enter-town";
	public static Object ACTION_ENTER_BATTLE = "action-enter-battle";
	public static Object ACTION_SHOW_PLAYER_STATS = "action-show-player-stats";
	public static Object ACTION_HIDE_PLAYER_STATS = "action-hide-player-stats";
	public static Object ACTION_SAVE_PLAYER = "action-save-player";
	public static Object ACTION_LOAD_PLAYER = "action-load-player";
	
	public static String ACTION_PLAYER_UPGRADE_ONE = "action-player-upgrade-one";
	public static String ACTION_PLAYER_UPGRADE_TWO = "action-player-upgrade-two";
	public static String ACTION_PLAYER_UPGRADE_THREE = "action-player-upgrade-three";
	public static String ACTION_PLAYER_UPGRADE_FOUR = "action-player-upgrade-four";
	public static String ACTION_PLAYER_UPGRADE_FIVE = "action-player-upgrade-five";
	
	
	//Skill ids
	public static int PLAYER_UPGRADE_ONE = 0;
	public static int PLAYER_UPGRADE_TWO = 1;
	public static int PLAYER_UPGRADE_THREE = 2;
	public static int PLAYER_UPGRADE_FOUR = 3;
	public static int PLAYER_UPGRADE_FIVE = 4;
	
}
