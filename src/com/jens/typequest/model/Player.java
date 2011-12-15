package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private int wallHealth = 100;
	int targetNr = 0;
	List<EnemyEntity> possibleTargets = new ArrayList<EnemyEntity>();
	EnemyEntity target = null;
	
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
		
		possibleTargets = StateHandler.getInstance().battle.currentEnemies;
		
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
}
