package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jens.typequest.model.StateHandler.Mode;
import com.jens.typequest.model.blueprint.PlayerBlueprint;

public class Player {

	private int wallHealth = 1000;
	
	int targetNr = 0;
	List<EnemyEntity> possibleTargets = new ArrayList<EnemyEntity>();
	EnemyEntity target = null;
	int gold;
	int xp = 0;
	int level = 1;
	int nextLevel = 10;
	int shieldRegenSpeed = 20;
	int shieldIdleTime = 2000;
	
	int[] skillLevels = new int[10]; // 10 siklls hard wired to begin with, all start at 0

	public Player(PlayerBlueprint player){
		this.gold = player.getGold();
		this.xp = player.getXp();
		this.level = player.getLevel();
		this.nextLevel = player.getNextLevel();
		this.skillLevels = player.getSkillLevels();
	}
	
	public Player() {
	}
	
	public EnemyEntity getTarget() {
		return target;
	}

	public void setTarget(EnemyEntity target) {
		this.target = target;
	}

	public int getWallHealth() {
		return wallHealth;
	}

	public void setWallHealth(int wallHealth) {
		this.wallHealth = wallHealth;
	}

	public void cycleEnemy() {
		if(!StateHandler.getInstance().getCurrentMode().equals(Mode.BATTLE)){
			return;
		}
		
		possibleTargets.clear();
		possibleTargets.addAll(StateHandler.getInstance().battle.currentEnemies);
		for (Iterator<EnemyEntity> iterator = possibleTargets.iterator(); iterator.hasNext();) {
			EnemyEntity entity = iterator.next();
			if(entity.getPosition().getX() > 1024){
				iterator.remove();
			}
		}
	
		if(possibleTargets.size() == 0){
			target = null;
			return;
		}
		
		if(target != null){
			targetNr ++;
		}
		
		target = possibleTargets.get(targetNr % possibleTargets.size());
	}



	public List<EnemyEntity> getPossibleTargets() {
		return possibleTargets;
	}



	public void setPossibleTargets(List<EnemyEntity> possibleTargets) {
		this.possibleTargets = possibleTargets;
	}

	public int getTargetNr() {
		return targetNr;
	}

	public void setTargetNr(int targetNr) {
		this.targetNr = targetNr;
	}

	public int getGold() {
		return gold;
	}

	public void modGold(int gold) {
		this.gold += gold;
	}

	public int getXp() {
		return xp;
	}

	public void addXp(int xp) {
		this.xp += xp;
		while(this.xp >= nextLevel){
			level ++;
			nextLevel += level*1.5*10;
		}
	}
	
	public int getUnspentSkillpoints(){
		int tot = 0;
		for (int i = 0; i < skillLevels.length; i++) {
			tot += skillLevels[i];
		}
		return level - tot;
	}

	public int getLevel() {
		return level;
	}

	public int getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(int nextLevel) {
		this.nextLevel = nextLevel;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int[] getSkillLevels() {
		return skillLevels;
	}

	public void setSkillLevels(int[] skillLevels) {
		this.skillLevels = skillLevels;
	}

	public int getShieldRegenSpeed() {
		return shieldRegenSpeed;
	}

	public void setShieldRegenSpeed(int shieldRegenSpeed) {
		this.shieldRegenSpeed = shieldRegenSpeed;
	}

	public int getShieldIdleTime() {
		return shieldIdleTime;
	}

	public void setShieldIdleTime(int shieldIdleTime) {
		this.shieldIdleTime = shieldIdleTime;
	}
	
	public void upgradeSkill(int i) {
		System.out.println("Trying to upgrade skill nr: " + i);
		if(getUnspentSkillpoints() > 0){
			System.out.println("Upgrading skill nr " + i);
			skillLevels[i] ++;
		}
	}
}
