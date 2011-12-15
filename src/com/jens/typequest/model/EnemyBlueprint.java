package com.jens.typequest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	public EnemyBlueprint(String id, double speed, double dmg, long attackSpeed, List<String> texts, String imagePath) {
		super();
		this.id = id;
		this.speed = speed;
		this.dmg = dmg;
		this.attackSpeed = attackSpeed;
		this.texts = texts;
		this.imagePath = imagePath;
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
		return texts.get(new  Random(System.currentTimeMillis()).nextInt(texts.size()));
	}
}
