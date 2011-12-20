package com.jens.typequest.model;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.TypeQuestConstants;
import com.jens.typequest.loaders.RandomUtil;
import com.jens.typequest.model.blueprint.EnemyBlueprint;
import com.jens.typequest.ui.BroadCaster;

public class EnemyEntity extends GraphicalEntity {

	public Image getSmallImage(){
		return image;
	}
	
	double speed = 0.1;
	double dmg;
	long attackSpeed;
	String textToWrite ="";
	int lettersTyped = 0;
	String name;
	int xp;
	int gold;
	int level;
	
	public EnemyEntity(String id, Vector2f position, Image image, EnemyBlueprint blueprint, int level) {
		super(id, position, image);
		speed = blueprint.getSpeed();
		dmg = blueprint.getDmg()*1.2*level;
		attackSpeed = (long) (blueprint.getAttackSpeed()/(1.1*level));
		textToWrite = blueprint.getText();
		gold = blueprint.getMingold() + RandomUtil.nextInt(blueprint.getDiffgold());
		xp = (int) (blueprint.getXp()*1.5*level);
		this.level = level;
	}
	
	public long becomesActive = 0;
	public long lastDmg = 0;
	
	@Override
	public void update(int delta) {
		
		if(System.currentTimeMillis() >= becomesActive){
			if(position.x > TypeQuestConstants.WALL_X){
				position.x -= speed*delta;
			} else {
				//hit the wall!
				while(System.currentTimeMillis() -lastDmg > attackSpeed){
					lastDmg = System.currentTimeMillis();
//					Player.getInstance().setWallHealth((int) (Player.getInstance().getWallHealth() - dmg)); // bleh
					BroadCaster.getInstance().writeMessage(name + " hits the wall for " + dmg + " damage.", Color.orange);
				}
			}
		}
	
	}
	boolean marked = false;
	public void setMarked(boolean b) {
		marked = b;
	}
	public boolean isMarked() {
		return marked;
	}

	public String getTextToWrite() {
		return textToWrite;
	}

	public void setTextToWrite(String textToWrite) {
		this.textToWrite = textToWrite;
	}

	public int getLettersTyped() {
		return lettersTyped;
	}

	public void setLettersTyped(int lettersTyped) {
		this.lettersTyped = lettersTyped;
	}

	boolean isDead = false;
	public void kill() {
		isDead = true;
	}

	public boolean getIsDead() {
		return isDead;
	}
	public int getXp() {
		return xp;
	}
	public int getGold() {
		return gold;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
