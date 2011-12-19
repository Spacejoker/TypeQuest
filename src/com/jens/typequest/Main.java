package com.jens.typequest;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

import com.jens.typequest.model.ClickableEntity;
import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.GraphicalEntity;
import com.jens.typequest.model.Player;
import com.jens.typequest.model.StateHandler;
import com.jens.typequest.model.StateHandler.Mode;
import com.jens.typequest.model.TextEntity;
import com.jens.typequest.ui.BroadCaster;
import com.jens.typequest.ui.ContentFrame;
import com.jens.typequest.ui.Message;
import com.jens.typequest.ui.TextProcessor;
import com.jens.typequest.ui.UserCommandReader;

public class Main extends BasicGame {

	StateHandler currentState = StateHandler.getInstance();	
	public static final String IMAGE_FOLDER = "src/content/image/";

	TextProcessor textProcessor = new TextProcessor();
	UserCommandReader commandReader = new UserCommandReader();
	public static TrueTypeFont font = null;
	
	Color doneLetterColor = Color.green;
	Color undoneLetterColor = Color.white;
	
	Image battleBg = null;
	Image mainMenuBg = null;
	Image townBg = null;
	Image textbox = null;
	Image targetImage = null;
	Image battleCompleteBg = null;
	Image playerStatsBg = null;
	
	Player player = null;

	public Main() {
		super("Type Quest");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		player = StateHandler.getInstance().getPlayer();
		container.getInput().addKeyListener(textProcessor);
		container.getInput().addKeyListener(commandReader);
		container.getInput().addMouseListener(commandReader);
		
		font = new TrueTypeFont(new Font("Courier new", Font.BOLD, 24), false);
		currentState.setFont(font);
		// create a few test enemies:
		
		//load some bg-graphics
		battleBg = new Image(IMAGE_FOLDER + "fortress.png");
		mainMenuBg = new Image(IMAGE_FOLDER + "mainmenu.png");
		townBg = new Image(IMAGE_FOLDER + "town.png");
		textbox = new Image(IMAGE_FOLDER + "textbox.png");
		targetImage = new Image(IMAGE_FOLDER + "target.png");
		battleCompleteBg = new Image(IMAGE_FOLDER + "battlecomplete.png");
		
		currentState.setCurrentMode(Mode.MAIN_MENU);
	}

	int charWidth = 14;
	List<Message> combatLog = new ArrayList<Message>();

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		synchronized (this.getClass()) {
			switch (currentState.getCurrentMode()) {
			case BATTLE:
				battleBg.draw();
				drawWritingArea();
				drawEnemies();
				drawCombatLog();
				
				break;

			case MAIN_MENU:
				mainMenuBg.draw();
				displayMainMenu();
				break;
				
			case TOWN:
				townBg.draw();
				showTown();
				break;
			
			default:
				break;
			}
		}
		if(currentState.getContentFrame() != null){
			ContentFrame contentFrame = currentState.getContentFrame();
			contentFrame.getBackground().draw(contentFrame.getX(), contentFrame.getY());
			
			for (GraphicalEntity entity : currentState.getContentFrame().getEntities()) {
				if(entity instanceof TextEntity){
					((TextEntity) entity).getFont().drawString(entity.getPosition().getX(), entity.getPosition().getY(), ((TextEntity) entity).getText(), ((TextEntity) entity).getColor());
				} else {
					entity.getImage().draw(entity.getPosition().x, entity.getPosition().y);
				}
			}
		}
		
		for (ClickableEntity entity : currentState.getClickEntities()) {
			entity.getImage().draw(entity.getPosition().x, entity.getPosition().y);
		}
		
		if(currentState.getShowPlayerStats()){
			
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		synchronized (this.getClass()) {
			switch (currentState.getCurrentMode()) {
			case BATTLE:
				// The actual combat - writing!
				handleWriting();

				// Set up the currently selected enemy
				handleEnemies(delta);

				// Broadcast any messages in the broadcaster que and then clear the que
				handleMessageLog();
				
				//show score screen if the battle is over
				break;
			case MAIN_MENU:
//				displayMainMenu();
				handleMainMenu(container);
			case TOWN:
//				showTown();
				handleTown(container);
			default:
				break;
			}
		}
		commandReader.handleQueuedCommands(currentState);
	}

	private void handleTown(GameContainer container) {
		
	}

	private void handleMainMenu(GameContainer container) {
		
	}

	private void showTown() {
		
	}

	private void displayMainMenu() {
		
	}

	private void handleWriting() {
		if (player.getTarget() == null) {
			return;
		}
		String written = textProcessor.popCharacterQue().toLowerCase();
		String targetString = player.getTarget().getTextToWrite().toLowerCase();
		int correctLetters = player.getTarget().getLettersTyped();
		if (written.length() > 0) {

			for (int i = 0; i < written.length(); i++) {
				if (written.charAt(i) == targetString.charAt(correctLetters)) {
					correctLetters++;
					if (correctLetters == targetString.length()) {
						// string is done!
						player.getTarget().kill();
						player.setTarget(null);

						correctLetters = 0;
					}
					while (targetString.charAt(correctLetters) == ' ') {
						correctLetters++;
					}
				}
			}
		}

		if (player.getTarget() != null) {
			player.getTarget().setLettersTyped(correctLetters);
		}
	}

	private void handleEnemies(int delta) {
		
		if(currentState.getBattle().getCurrentEnemies().size() == 0 && !currentState.getBattle().isCompleted()){
			boolean nextWave = currentState.getBattle().nextWave();
			if(!nextWave){
				currentState.getPlayer().addXp(currentState.getBattle().getGainedXp());
				currentState.getPlayer().modGold(currentState.getBattle().getGainedGold());
				currentState.getBattle().setCompleted(true);

				ContentFrame contentFrame = new ContentFrame(battleCompleteBg, 200, 100, Arrays.asList(new GraphicalEntity[]{
						new TextEntity("You completed the level!", font, new Vector2f(300,220), Color.darkGray),
						new TextEntity("Gained gold: " + currentState.getBattle().getGainedGold(), font, new Vector2f(300,250), Color.darkGray),
						new TextEntity("Gained xp: " + currentState.getBattle().getGainedXp(), font, new Vector2f(300,280), Color.darkGray),
						new TextEntity("You should get back to town and celebrate!", font, new Vector2f(300,310), Color.darkGray)
				}));
				
				currentState.setContentFrame(contentFrame);
			} else {
//				font.drawString(200, 500, "Next wave approaches", Color.orange);
			}
		}
		
		for (Iterator<EnemyEntity> iterator = currentState.getBattle().getCurrentEnemies().iterator(); iterator.hasNext();) {
			EnemyEntity enemy = iterator.next();
			if (enemy.getIsDead()) {
				currentState.getBattle().addGold(enemy.getGold());
				currentState.getBattle().addXp(enemy.getXp());
				iterator.remove();
			}
			if (enemy.equals(player.getTarget())) {
				enemy.setMarked(true);
			} else {
				enemy.setMarked(false);
			}
			enemy.update(delta);
		}
	}

	private void handleMessageLog() {
		List<Message> messages = BroadCaster.getInstance().getMessages();
		for (Message message : messages) {
			combatLog.add(0, message);
		}
		messages.clear();
		for (int i = 0; i < combatLog.size(); i++) {
			if (combatLog.get(i).age() > 2000) {
				combatLog.remove(i);
				i--;
			}
		}
	}

	private void drawWritingArea() {
		if (player.getTarget() != null) {
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
		Collections.sort(currentState.getBattle().getCurrentEnemies(), new Comparator<EnemyEntity>() {
			@Override
			public int compare(EnemyEntity o1, EnemyEntity o2) {
				return (int) (o1.getPosition().getY()+o1.getImage().getHeight() - o2.getPosition().getY()-o2.getImage().getHeight());
			}
		});
		
		for (EnemyEntity entity : currentState.getBattle().getCurrentEnemies()) {
			entity.getImage().draw(entity.getPosition().x, entity.getPosition().y);
			if(entity.isMarked()){
				targetImage.draw(entity.getPosition().x+entity.getImage().getWidth()/2-targetImage.getWidth()/2, entity.getPosition().y+entity.getImage().getHeight()/2-targetImage.getHeight()/2);
			}
		}
	}

	private void drawCombatLog() {
		// display messages in the combat log
		for (int i = 0; i < Math.min(combatLog.size(), 5); i++) {
			Message message = combatLog.get(i);
			font.drawString(20, 600 + i * 20, message.getContent(), message.getColor());
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
