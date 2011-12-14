package com.jens.typequest;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.InputAdapter;

import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.EnemyEntity.EnemyType;
import com.jens.typequest.model.GraphicalEntity;
import com.jens.typequest.model.Player;
import com.jens.typequest.ui.BroadCaster;
import com.jens.typequest.ui.Message;

public class Main extends BasicGame {

	private static final String IMAGE_FOLDER = "src/content/image/";
	InputAdapter inputAdapter;
	TextProcessor textProcessor = new TextProcessor();
	TrueTypeFont font = null;
	Color doneLetterColor = Color.green;
	Color undoneLetterColor = Color.white;
	Image bg = null;
	Image textbox = null;
	Player player = null;
	
	List<EnemyEntity> enemies = new ArrayList<EnemyEntity>();
	GraphicalEntity graphicalEntity = null;
	public Main() {
		super("Type Quest");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		player = Player.getInstance();
		player.setPossibleTargets(enemies);
		inputAdapter = new InputAdapter();
		container.getInput().addKeyListener(textProcessor);
		font = new TrueTypeFont(new Font("Courier new", Font.BOLD, 24), false);
		
		//create a test enemy:
		enemies.add(new EnemyEntity("apa", new Vector2f(800, 500), new Image("src/content/image/enemy.png"), new Image("src/content/image/enemy_marked.png"), EnemyType.OZZY));
		enemies.add(new EnemyEntity("apa", new Vector2f(680, 450), new Image("src/content/image/enemy.png"), new Image("src/content/image/enemy_marked.png"),  EnemyType.OZZY));
		enemies.add(new EnemyEntity("apa", new Vector2f(300, 660), new Image("src/content/image/enemy.png"), new Image("src/content/image/enemy_marked.png"), EnemyType.OZZY));
		bg = new Image(IMAGE_FOLDER + "fortress.png");
		textbox = new Image(IMAGE_FOLDER + "textbox.png");
	}

	int charWidth = 14;
	List<Message> combatLog = new ArrayList<Message>();

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		synchronized (this.getClass()) {
			bg.draw();
			drawWritingArea();

			drawEnemies();
			
			drawCombatLog();
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		synchronized (this.getClass()) {
			//The actual combat - writing!
			handleWriting();
			
			//Set up the currently selected enemy
			selectEnemy(delta);
			
			//Broadcast any messages in the broadcaster que and then clear the que
			handleMessageLog();
		}
	}

	private void handleWriting() {
		if(player.getTarget() == null){
			return;
		}
		String written = textProcessor.popCharacterQue().toLowerCase();
		String targetString = player.getTarget().getTextToWrite().toLowerCase();
		int correctLetters = player.getTarget().getLettersTyped();
		if (written.length() > 0) {
			
			for (int i = 0; i < written.length(); i++) {
				if(written.charAt(i) == targetString.charAt(correctLetters) ){
					correctLetters ++;
					if(correctLetters == targetString.length()){
						//string is done!
						player.getTarget().kill();
						player.setTarget(null);
						
						correctLetters = 0;
					}
					while(targetString.charAt(correctLetters) == ' '){
						correctLetters ++;
					}
				}
			}
		}
		
		if(player.getTarget() != null){
			player.getTarget().setLettersTyped(correctLetters);
		}
	}

	private void selectEnemy(int delta) {
		for (Iterator<EnemyEntity> iterator = enemies.iterator(); iterator.hasNext();) {
			EnemyEntity entity = iterator.next();
			if(entity.getIsDead()){
				iterator.remove();
			}
			if(entity.equals(player.getTarget())){
				entity.setMarked(true);
			} else {
				entity.setMarked(false);
			}
			entity.update(delta);
		}
	}

	private void handleMessageLog() {
		List<Message> messages = BroadCaster.getInstance().getMessages();
		for (Message message : messages) {
			combatLog.add(0,message);
		}
		messages.clear();
		for (int i = 0; i < combatLog.size(); i++) {
			if(combatLog.get(i).age() > 2000){
				combatLog.remove(i);
				i --;
			}
		}
	}

	private void drawWritingArea() {
		if(player.getTarget() != null){
			EnemyEntity target = player.getTarget();
			
			textbox.draw(TypeQuestConstants.TEXT_BOX_X, TypeQuestConstants.TEXT_BOX_Y);
			
			String targetString = player.getTarget().getTextToWrite();
			int correctLetters = player.getTarget().getLettersTyped();
			
			String doneString = targetString.substring(0, correctLetters);
			String undoneString = targetString.substring(correctLetters);
			
			target.getSmallImage().draw(TypeQuestConstants.PIC_X, TypeQuestConstants.TEXT_Y);
			font.drawString(TypeQuestConstants.TEXT_X, TypeQuestConstants.TEXT_Y, doneString, doneLetterColor);
			font.drawString(TypeQuestConstants.TEXT_X + doneString.length() * charWidth, TypeQuestConstants.TEXT_Y, undoneString, undoneLetterColor);
		}
	}

	private void drawEnemies() {
		for (EnemyEntity entity : enemies) {
			entity.getImage().draw(entity.getPosition().x, entity.getPosition().y);
		}
	}

	private void drawCombatLog() {
		//display messages in the combat log
		for (int i = 0; i < Math.min(combatLog.size(),5); i++) {
			Message message = combatLog.get(i);
			font.drawString(20, 600 + i*20, message.getContent(), message.getColor());
		}
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Main());
			app.setDisplayMode(1024, 800, false);
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
