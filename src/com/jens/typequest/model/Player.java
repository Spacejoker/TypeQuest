package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Making Player a singleton to make it easy to access - no plans for multiplayer anyway.
 * @author Jensa
 *
 */
public class Player {

	private static Player instance = null;

	private int wallHealth = 100;
	int targetNr = 0;
	List<EnemyEntity> possibleTargets = new ArrayList<EnemyEntity>();
	EnemyEntity target = null;
	
	private Player() {
	}

	public static Player getInstance() {
		if (instance == null) {
			instance = new Player();
		}
		return instance;
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
