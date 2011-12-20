package com.jens.typequest.model.blueprint;

import com.jens.typequest.model.Player;

/**
 * Blueprint for saving, no runtime stuff, only persistent data such as xp, level etc.
 * 
 * @author Jensa
 */
public class PlayerBlueprint {

	int gold, xp, level, nextLevel;
	int[] skillLevels = new int[10];

	public PlayerBlueprint(Player player) {
		this.gold = player.getGold();
		this.xp = player.getXp();
		this.level = player.getLevel();
		this.nextLevel = player.getNextLevel();
		this.skillLevels = player.getSkillLevels();
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(int nextLevel) {
		this.nextLevel = nextLevel;
	}

	public int[] getSkillLevels() {
		return skillLevels;
	}

	public void setSkillLevels(int[] skillLevels) {
		this.skillLevels = skillLevels;
	}
}
