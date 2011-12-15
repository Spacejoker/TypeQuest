package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;

public class Wave {
	List<EnemyEntity> waveEnemies = new ArrayList<EnemyEntity>();

	public void add(EnemyEntity e){
		waveEnemies.add(e);
	}
	
	public List<EnemyEntity> getWaveEnemies() {
		return waveEnemies;
	}

	public void setWaveEnemies(List<EnemyEntity> waveEnemies) {
		this.waveEnemies = waveEnemies;
	}
}
