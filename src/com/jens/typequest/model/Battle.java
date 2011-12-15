package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Battle {

	List<EnemyEntity> currentEnemies = new ArrayList<EnemyEntity>();
	Stack<Wave> waves = new Stack<Wave>();
	boolean completed = false;

	public void addWave(Wave w){
		waves.add(w);
	}
	
	public boolean nextWave() {
		if (waves.size() == 0) {
			return false;
		}
		Wave pop = waves.pop();
		currentEnemies.addAll(pop.getWaveEnemies());
		return true;
	}

	public List<EnemyEntity> getCurrentEnemies() {
		return currentEnemies;
	}

	public void setCurrentEnemies(List<EnemyEntity> currentEnemies) {
		this.currentEnemies = currentEnemies;
	}

	public Stack<Wave> getWaves() {
		return waves;
	}

	public void setWaves(Stack<Wave> waves) {
		this.waves = waves;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
