package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

	private int wallHealth = 100;
	int targetNr = 0;
	List<EnemyEntity> possibleTargets = new ArrayList<EnemyEntity>();
	EnemyEntity target = null;
	int gold;
	int xp = 0;
	int level = 1;
	int nextLevel = 10;
	
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
		this.xp = xp;
		while(this.xp >= nextLevel){
			level ++;
			nextLevel += level*1.5*10;
		}
	}

	public int getLevel() {
		return level;
	}

}
