package com.jens.typequest.model;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.TextGenerator;
import com.jens.typequest.TypeQuestConstants;
import com.jens.typequest.ui.BroadCaster;

public class EnemyEntity extends GraphicalEntity {

	Image markedImage = null;
	@Override
	public Image getImage() {
		return marked ? markedImage : image;
	}
	
	public Image getSmallImage(){
		return image;
	}
	public enum EnemyType {
		OZZY(1);
		int id;

		private EnemyType(int id) {
			this.id = id;
		}
	}

	double speed = 0.1;
	double dmg;
	long attackSpeed;
	String textToWrite ="";
	int lettersTyped = 0;
	
	EnemyType type;

	public EnemyEntity(String id, Vector2f position, Image image, Image markedImage, EnemyType type) {
		super(id, position, image);
		this.type = type;
		this.markedImage = markedImage;
		switch (type) {
		case OZZY:
			speed = 0.1d;
			dmg = 1;
			attackSpeed = 1000L;
			textToWrite = TextGenerator.generateText(this);
			break;

		default:
			break;
		}
	}
	public EnemyType getType() {
		return type;
	}
	public void setType(EnemyType type) {
		this.type = type;
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
					Player.getInstance().setWallHealth((int) (Player.getInstance().getWallHealth() - dmg)); // bleh
					BroadCaster.getInstance().writeMessage(type.toString() + " hits the wall for " + dmg + " damage.", Color.orange);
				}
			}
		}
		switch (type) {
		case OZZY:
			break;

		default:
			break;
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
//		System.out.println("textToWrite: " + textToWrite);
		return textToWrite;
	}

	public void setTextToWrite(String textToWrite) {
		this.textToWrite = textToWrite;
	}

	public int getLettersTyped() {
//		System.out.println("letters typed: " + lettersTyped);
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
	
	
	
}
