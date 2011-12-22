package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Battle {

	List<EnemyEntity> currentEnemies = new ArrayList<EnemyEntity>();
	Stack<Wave> waves = new Stack<Wave>();
	boolean completed = false;

	int gainedXp = 0;
	int gainedGold = 0;
	
	HealthBar healthBar = new HealthBar();

	double currentHp = 1;
	double maxHp = 1;
	double maxshield = 100;
	double curshield = 100;
	double reg = 20;

	long lastHit = 0;
	
	public Battle() {
		
	}
	
	public void addGold(int gold) {
		gainedGold += gold;
	}

	public void addXp(int xp) {
		gainedXp += xp;
	}

	public int getGainedXp() {
		return gainedXp;
	}

	public int getGainedGold() {
		return gainedGold;
	}

	public void addWave(Wave w) {
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

	public void dmgWall(double dmg) {
		lastHit = System.currentTimeMillis();
		double shielddmg = Math.min(dmg, curshield);
		curshield -= shielddmg;
		currentHp -= dmg - shielddmg;
		updateHealthBar();
	}

	public void updateHealthBar() {
		healthBar.setShieldPercentage(curshield/maxshield*100);
		healthBar.setPercentage(currentHp/maxHp*100);
		healthBar.updateMe();
	}

	public double getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(double currentHp) {
		this.currentHp = currentHp;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}

	public void setGainedXp(int gainedXp) {
		this.gainedXp = gainedXp;
	}

	public void setGainedGold(int gainedGold) {
		this.gainedGold = gainedGold;
	}

	public HealthBar getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(HealthBar healthBar) {
		this.healthBar = healthBar;
	}

	public double getMaxshield() {
		return maxshield;
	}

	public void setMaxshield(double maxshield) {
		this.maxshield = maxshield;
	}

	public double getCurshield() {
		return curshield;
	}

	public void setCurshield(double curshield) {
		this.curshield = curshield;
	}

	public double getReg() {
		return reg;
	}

	public void setReg(double reg) {
		this.reg = reg;
	}

	public void addShield(int value) {
		curshield = Math.min(curshield + value, maxshield); 
	}

	public long getLastHit() {
		return lastHit;
	}

	public void setLastHit(long lastHit) {
		this.lastHit = lastHit;
	}

	public boolean isShieldDamaged() {
		return curshield < maxshield;
	}

	public String getHpString() {
		return pad(6,((int)currentHp) + "") + "/" + pad(6,((int)maxHp) + "");
	}
	
	public String getShieldString() {
		return pad(6,((int)curshield) + "") + "/" + pad(6,((int)maxshield) + "");
	}
	
	private String pad(int lens, String s) {
		
		;
		while (s.length() < lens) {
			s = " " + s;
		}
		return s;
	}

	public String getRemainingWawes() {
		if(waves.size() > 1){
			return waves.size() + " waves left";
		} else if(waves.size() == 1){
			return "One wave left";
		}
		return "Battle complete";
	}
}
