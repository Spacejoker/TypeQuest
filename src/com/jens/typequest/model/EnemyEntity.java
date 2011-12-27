package com.jens.typequest.model;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.TypeQuestConstants;
import com.jens.typequest.loaders.ImageProvider;
import com.jens.typequest.loaders.RandomUtil;
import com.jens.typequest.model.blueprint.EnemyBlueprint;
import com.jens.typequest.ui.BroadCaster;

public class EnemyEntity extends GraphicalEntity {

	public Image getSmallImage(){
		return portraitImage;
	}
	
	public Image getImage() {
		return walkAnimation.getCurrentFrame();
	};
	
	Animation walkAnimation;
	Animation attackAnimation;
	double speed = 0.1;
	double dmg;
	long attackSpeed;
	String textToWrite ="";
	int lettersTyped = 0;
	String name;
	int xp;
	int gold;
	int level;
	Image portraitImage;
	
	public EnemyEntity(String id, Vector2f position, Image image, EnemyBlueprint blueprint, int level) {
		super(id, position, image);
		speed = blueprint.getSpeed();
		dmg = blueprint.getDmg()*1.2*level;
		attackSpeed = (long) (blueprint.getAttackSpeed()/(1.1*level));
		textToWrite = blueprint.getText();
		gold = blueprint.getMingold() + RandomUtil.nextInt(blueprint.getDiffgold());
		xp = (int) (blueprint.getXp()*1.5*level);
		
		this.level = level;
		this.portraitImage = ImageProvider.getImage(blueprint.getPortraitImage());
		walkAnimation = new Animation(ImageProvider.getImages(blueprint.getWalkAnimation()), 350);
		walkAnimation = new Animation(ImageProvider.getImages(blueprint.getAttackAnimation()), 350);
	}
	
	public long becomesActive = 0;
	public long lastDmg = 0;
	boolean marked = false;
	
	@Override
	public void update(int delta) {
		
		if(System.currentTimeMillis() >= becomesActive){
			if(position.x > TypeQuestConstants.WALL_X){
				position.x -= speed*delta;
			} else { //hit the wall!
				if(System.currentTimeMillis() -lastDmg > attackSpeed){
					lastDmg = System.currentTimeMillis();
					BroadCaster.getInstance().writeMessage(name + " hits the wall for " + dmg + " damage.", Color.orange);
					Battle battle = StateHandler.getInstance().getBattle();
					battle.dmgWall(dmg);
				}
			}
		}
	}
	
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

	public Animation getWalkAnimation() {
		return walkAnimation;
	}

	public void setWalkAnimation(Animation walkAnimation) {
		this.walkAnimation = walkAnimation;
	}
	
}
