package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jens.typequest.loaders.RandomUtil;

/**
 * Blueprints for an enemy - what picture/stats does it have etc?
 */
public class EnemyBlueprint {
	
	private String id;
	private double speed = 0.1;
	private double dmg;
	private long attackSpeed; // ms between attacks
	private List<String> texts = new ArrayList<String>();
	private String imagePath;
	int mingold;
	int diffgold;
	int xp;
	
	
	public EnemyBlueprint(String id, double speed, double dmg, long attackSpeed, List<String> texts, String imagePath, int mingold, int diffgold, int xp) {
		super();
		this.id = id;
		this.speed = speed;
		this.dmg = dmg;
		this.attackSpeed = attackSpeed;
		this.texts = texts;
		this.imagePath = imagePath;
		this.mingold = mingold;
		this.diffgold = diffgold;
		this.xp = xp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDmg() {
		return dmg;
	}
	public void setDmg(double dmg) {
		this.dmg = dmg;
	}
	public long getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(long attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	public List<String> getTexts() {
		return texts;
	}
	public void setTexts(List<String> texts) {
		this.texts = texts;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getText() {
		if(texts.size() == 0){
			return "Hello world!";
		}
		return texts.get(RandomUtil.nextInt(texts.size()));
	}
	public int getMingold() {
		return mingold;
	}
	public void setMingold(int mingold) {
		this.mingold = mingold;
	}
	public int getDiffgold() {
		return diffgold;
	}
	public void setDiffgold(int diffgold) {
		this.diffgold = diffgold;
	}
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	
}
