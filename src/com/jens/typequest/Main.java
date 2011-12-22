package com.jens.typequest;

import java.awt.Font;
import java.util.ArrayList;
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
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.loaders.ContentLoader;
import com.jens.typequest.model.Button;
import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.GraphicalEntity;
import com.jens.typequest.model.HealthBar;
import com.jens.typequest.model.Player;
import com.jens.typequest.model.StateHandler;
import com.jens.typequest.model.StateHandler.Mode;
import com.jens.typequest.model.TextEntity;
import com.jens.typequest.ui.BroadCaster;
import com.jens.typequest.ui.ContentFrame;
import com.jens.typequest.ui.Message;
import com.jens.typequest.ui.TextProcessor;
import com.jens.typequest.ui.UserCommandReader;
import com.jens.typequest.util.GraphUtil;

public class Main extends BasicGame {

	StateHandler state = StateHandler.getInstance();
	public static final String IMAGE_FOLDER = "src/content/image/";

	TextProcessor textProcessor = new TextProcessor();
	UserCommandReader commandReader = new UserCommandReader();
	public static TrueTypeFont font = null;

	Color doneLetterColor = Color.green;
	Color undoneLetterColor = Color.white;

	private Image textbox = null;
	private Image targetImage = null;

	int charWidth = 14;
	List<Message> combatLog = new ArrayList<Message>();

	public Main() {
		super("Type Quest");
	}

	@Override
	public void init(GameContainer container) throws SlickException {

		container.getInput().addKeyListener(textProcessor);
		container.getInput().addKeyListener(commandReader);
		container.getInput().addMouseListener(commandReader);

		font = new TrueTypeFont(new Font("Courier new", Font.BOLD, 24), false);
		state.setFont(font);

		ContentLoader.getInstance().init();

		// load some bg-graphics, move to content loader
		textbox = new Image(IMAGE_FOLDER + "textbox.png");
		targetImage = new Image(IMAGE_FOLDER + "target.png");

		state.setCurrentMode(Mode.MAIN_MENU);
	}

	/**
	 * Framework method - render
	 */
	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		synchronized (this.getClass()) {
			if (state.getBackgroundFrame() != null) {
				drawFrame(state.getBackgroundFrame());
			}
			if (state.getContentFrame() != null) { // a menu of some kind is open
				drawFrame(state.getContentFrame());
			}
			// some special stuff for the battle mode:
			if (state.getCurrentMode().equals(Mode.BATTLE)) {
				drawWritingArea();
				drawEnemies();
				drawCombatLog();
				
				//health bar:
				if(state.getBattle().getHealthBar().getShieldPart() != null){
					graphics.fill(state.getBattle().getHealthBar().getShieldPart(), GraphUtil.getColorAsGradient(Color.blue));
				}
				if(state.getBattle().getHealthBar().getRedPart() != null){
					graphics.fill(state.getBattle().getHealthBar().getRedPart(), GraphUtil.getColorAsGradient(Color.red));
				}
				state.getBattle().getHealthBar().getBg().draw();
				
				
			}
		}
	}

	/**
	 * Framework method - update logics
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		synchronized (this.getClass()) {
			if (state.getCurrentMode().equals(Mode.BATTLE)) {
				// The actual combat - writing!
				handleWriting();
				// Set up the currently selected enemy
				handleEnemies(delta);
				// Broadcast any messages in the broadcaster que and then clear the que
				handleMessageLog();
				state.timePassed(delta);
			}
			commandReader.handleQueuedCommands(state);
		}
	}

	/**
	 * Draws a frame - can be just a background or menu
	 * 
	 * @param contentFrame
	 */
	private void drawFrame(ContentFrame contentFrame) {
		contentFrame.getBackground().draw(contentFrame.getPosition().getX(), contentFrame.getPosition().getY());

		for (GraphicalEntity entity : contentFrame.getEntities()) {
			if (entity instanceof TextEntity) {
				font.drawString(entity.getPosition().getX(), entity.getPosition().getY(), ((TextEntity) entity).getText(), ((TextEntity) entity).getColor());
			} else {
				entity.getImage().draw(entity.getPosition().x, entity.getPosition().y);
			}
		}

		for (Button entity : contentFrame.getButtons()) {
			entity.getImage().draw(entity.getPosition().x, entity.getPosition().y);
		}
	}

	/**
	 * Handle the writing
	 */
	private void handleWriting() {
		if (state.getPlayer().getTarget() == null) {
			return;
		}
		String written = textProcessor.popCharacterQue().toLowerCase();
		String targetString = state.getPlayer().getTarget().getTextToWrite().toLowerCase();
		int correctLetters = state.getPlayer().getTarget().getLettersTyped();
		if (written.length() > 0) {
			for (int i = 0; i < written.length(); i++) {
				if (written.charAt(i) == targetString.charAt(correctLetters)) {
					correctLetters++;
					if (correctLetters == targetString.length()) {
						// string is done!
						state.getPlayer().getTarget().kill();
						state.getPlayer().setTarget(null);

						correctLetters = 0;
					}
					while (targetString.charAt(correctLetters) == ' ') {
						correctLetters++;
					}
				}
			}
		}

		if (state.getPlayer().getTarget() != null) {
			state.getPlayer().getTarget().setLettersTyped(correctLetters);
		}
	}

	private void handleEnemies(int delta) {

		if (state.getBattle().getCurrentHp() <= 0) {
			state.setCurrentMode(Mode.DEFEAT);
		} else {
			if (state.getBattle().getCurrentEnemies().size() == 0 && !state.getBattle().isCompleted()) {
				boolean nextWave = state.getBattle().nextWave();
				if (!nextWave) {
					state.getPlayer().addXp(state.getBattle().getGainedXp());
					state.getPlayer().modGold(state.getBattle().getGainedGold());
					state.getBattle().setCompleted(true);

					ContentFrame contentFrame = ContentLoader.getInstance().getContentFrame("battleComplete");
					state.setContentFrame(contentFrame);
				} else {
					// go to victory scene
				}
			}

			for (Iterator<EnemyEntity> iterator = state.getBattle().getCurrentEnemies().iterator(); iterator.hasNext();) {
				EnemyEntity enemy = iterator.next();
				if (enemy.getIsDead()) {
					state.getBattle().addGold(enemy.getGold());
					state.getBattle().addXp(enemy.getXp());
					iterator.remove();
				}
				if (enemy.equals(state.getPlayer().getTarget())) {
					enemy.setMarked(true);
				} else {
					enemy.setMarked(false);
				}
				enemy.update(delta);
			}
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
		if (state.getPlayer().getTarget() != null) {
			EnemyEntity target = state.getPlayer().getTarget();

			textbox.draw(TypeQuestConstants.TEXT_BOX_X, TypeQuestConstants.TEXT_BOX_Y);

			String targetString = state.getPlayer().getTarget().getTextToWrite();
			int correctLetters = state.getPlayer().getTarget().getLettersTyped();

			String doneString = targetString.substring(0, correctLetters);
			String undoneString = targetString.substring(correctLetters);

			target.getSmallImage().draw(TypeQuestConstants.PIC_X, TypeQuestConstants.TEXT_Y);

			// write charsPerLine chars per line:
			int charsPerLine = 20;

			int totWritten = 0;
			for (int i = 0; i <= targetString.length() / charsPerLine + 1; i++) {
				int yoffset = 20;
				for (int j = 0; j < charsPerLine; j++) {
					if (j > 0 && (j % charsPerLine == 0) || j + totWritten >= targetString.length()) {
						break;
					}
					Color c = undoneLetterColor;
					if (totWritten + j < correctLetters) {
						c = doneLetterColor;
					}
					font.drawString(TypeQuestConstants.TEXT_X + charWidth * j, TypeQuestConstants.TEXT_Y + i * yoffset, "" + targetString.charAt(totWritten + j), c);
				}
				totWritten += charsPerLine;
			}
		}
	}

	private void drawEnemies() {
		Collections.sort(state.getBattle().getCurrentEnemies(), new Comparator<EnemyEntity>() {
			@Override
			public int compare(EnemyEntity o1, EnemyEntity o2) {
				return (int) (o1.getPosition().getY() + o1.getImage().getHeight() - o2.getPosition().getY() - o2.getImage().getHeight());
			}
		});

		for (EnemyEntity entity : state.getBattle().getCurrentEnemies()) {
			entity.getImage().draw(entity.getPosition().x, entity.getPosition().y);
			if (entity.isMarked()) {
				targetImage.draw(entity.getPosition().x + entity.getImage().getWidth() / 2 - targetImage.getWidth() / 2, entity.getPosition().y + entity.getImage().getHeight() / 2 - targetImage.getHeight() / 2);
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
			app.setDisplayMode(1024, 720, false);
			app.setShowFPS(false);
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
