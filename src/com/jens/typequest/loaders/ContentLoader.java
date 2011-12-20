package com.jens.typequest.loaders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jens.typequest.model.Battle;
import com.jens.typequest.model.Button;
import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.GraphicalEntity;
import com.jens.typequest.model.Player;
import com.jens.typequest.model.StateHandler;
import com.jens.typequest.model.TextEntity;
import com.jens.typequest.model.Wave;
import com.jens.typequest.model.blueprint.Blueprint;
import com.jens.typequest.model.blueprint.ButtonBlueprint;
import com.jens.typequest.model.blueprint.ContentFrameBlueprint;
import com.jens.typequest.model.blueprint.EnemyBlueprint;
import com.jens.typequest.model.blueprint.PlayerBlueprint;
import com.jens.typequest.ui.ContentFrame;

public class ContentLoader {

	static String contentFolder = "src/content/json/";

	private static ContentLoader instance;

	private ContentLoader() {

	}

	public static ContentLoader getInstance() {
		if (instance == null) {
			instance = new ContentLoader();
		}
		return instance;
	}

	Map<String, ContentFrameBlueprint> contentBlueprintMap = new HashMap<String, ContentFrameBlueprint>();
	Map<String, EnemyBlueprint> enemyBlueprints = new HashMap<String, EnemyBlueprint>();
	Map<String, ButtonBlueprint> buttonBlueprintMap = new HashMap<String, ButtonBlueprint>();

	/**
	 * load alot of the view content and save as blueprints in maps .] (one eyed smiley)
	 */
	public void init() {
		Gson gson = new Gson();
		try {

			BufferedReader reader = new BufferedReader(new FileReader(contentFolder + "enemies.json"));
			while (true) {
				String enemyData = reader.readLine();
				if (enemyData == null || enemyData.length() == 0) {
					break;
				}
				EnemyBlueprint print = gson.fromJson(enemyData, EnemyBlueprint.class);
				enemyBlueprints.put(print.getId(), print);
			}

			reader = new BufferedReader(new FileReader(contentFolder + "contentframes.json"));
			while (true) {
				String contentData = reader.readLine();
				if (contentData == null || contentData.length() == 0) {
					break;
				}
				ContentFrameBlueprint print = gson.fromJson(contentData, ContentFrameBlueprint.class);
				contentBlueprintMap.put(print.getId(), print);
			}

			reader = new BufferedReader(new FileReader(contentFolder + "buttons.json"));
			while (true) {
				String contentData = reader.readLine();
				if (contentData == null || contentData.length() == 0) {
					break;
				}
				ButtonBlueprint print = gson.fromJson(contentData, ButtonBlueprint.class);
				buttonBlueprintMap.put(print.getId(), print);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Player loadPlayer(){
		try {
			PlayerBlueprint blueprint = new Gson().fromJson(readAll(contentFolder + "player.json").toString(), PlayerBlueprint.class);
			return new Player(blueprint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void savePlayer(Player player){
		PlayerBlueprint playerBlueprint = new PlayerBlueprint(player);
		String json = new Gson().toJson(player);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(contentFolder + "player.json"));
			writer.write(json);
			writer.close();
		} catch (Exception  e) {
			System.out.println("Probelm problem");
			e.printStackTrace();
		}
	}

	private StringBuffer readAll(String string) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(string));
		StringBuffer buf = new StringBuffer();
		while (true) {
			String readLine = reader.readLine();
			if (readLine == null) {
				return buf;
			}
			buf.append(readLine);

		}
	}

	public EnemyEntity getEnemy(int level, int nr, boolean boss) {

		List<EnemyBlueprint> appropriateBlueprints = new ArrayList<EnemyBlueprint>();

		for (String key : enemyBlueprints.keySet()) {
			EnemyBlueprint enemy = enemyBlueprints.get(key);

			if (enemy.isBoss() == boss && enemy.getMinlevel() <= level && enemy.getMaxlevel() >= level) {
				appropriateBlueprints.add(enemy);
			}
		}

		if (appropriateBlueprints.size() == 0) {
			appropriateBlueprints.addAll(enemyBlueprints.values());
		}

		return createEnemy(appropriateBlueprints.get(RandomUtil.nextInt(appropriateBlueprints.size())), nr, level);
	}

	private EnemyEntity createEnemy(EnemyBlueprint enemyBlueprint, int nr, int level) {

		// randomize a new position
		int rand = RandomUtil.nextInt(200);
		Image image = ImageProvider.getImage(enemyBlueprint.getImagePath());
		rand += image.getHeight();

		return new EnemyEntity(enemyBlueprint.getId(), new Vector2f(1024 + 100 * nr, 800 - rand), image, enemyBlueprint, level);
	}

	public Battle getBattle(int difficulty) {
		StateHandler state = StateHandler.getInstance();

		Battle battle = new Battle();
		battle.setMaxHp(state.getPlayer().getWallHealth());
		battle.setCurrentHp(state.getPlayer().getWallHealth());

		// test level
		Wave w = new Wave();
		w.add(getEnemy(1, 0, true));
		battle.addWave(w);

		return battle;
	}

	public String loadJsonString(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/content/json/" + fileName + ".json"));
			return reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public ContentFrame getContentFrame(String id) {

		StateHandler state = StateHandler.getInstance();
		ContentFrameBlueprint blueprint = (ContentFrameBlueprint) getBlueprint(id, ContentFrame.class);

		ContentFrame contentFrame = new ContentFrame();

		contentFrame.setBackground(ImageProvider.getImage(blueprint.getBackgroundImage()));
		contentFrame.setPosition(blueprint.getPosition());
		for (String buttonBp : blueprint.getButtonBlueprints()) {
			contentFrame.getButtons().add(getButtonFromBlueprint(buttonBp));
		}
		
		//texts hard wired per content frame, dont know what to do about this that makes sense
		if (id.equals("playerStats")) {
			contentFrame.getEntities().addAll(
					Arrays.asList(
							new TextEntity("Current level: " + state.getPlayer().getLevel(), new Vector2f(400, 250), Color.darkGray), 
							new TextEntity("Xp current (next level): " + state.getPlayer().getXp() + "(" + state.getPlayer().getNextLevel() + ")", new Vector2f(400, 280), Color.darkGray), 
							new TextEntity("Gold: " + state.getPlayer().getGold(), new Vector2f(400, 310), Color.darkGray)));
		} else if (id.equals("battleComplete")) {
			contentFrame.getEntities().addAll(
					Arrays.asList(new GraphicalEntity[] { 
							new TextEntity("You completed the level!", new Vector2f(300, 220), Color.darkGray), 
							new TextEntity("Gained gold: " + state.getBattle().getGainedGold(), new Vector2f(300, 250), Color.darkGray),
							new TextEntity("Gained xp: " + state.getBattle().getGainedXp(), new Vector2f(300, 280), Color.darkGray), 
							new TextEntity("You should get back to town and celebrate!", new Vector2f(300, 310), Color.darkGray) }));
		} else if (id.equals("defeat")) {
			contentFrame.getEntities().addAll(
					Arrays.asList(
							new GraphicalEntity[] { new TextEntity("Your wall is destroyed and you flee!", new Vector2f(300, 220), Color.darkGray), 
							new TextEntity("Maybe you should try an easier level?", new Vector2f(300, 250), Color.darkGray)}));
		}

		return contentFrame;
	}

	private Button getButtonFromBlueprint(String name) {
		ButtonBlueprint buttonBp = buttonBlueprintMap.get(name);
		return new Button(buttonBp.getId(), buttonBp.getPosition(), ImageProvider.getImage(buttonBp.getImagePath()), buttonBp.getActionId());
	}

	public Blueprint getBlueprint(String name, Class<ContentFrame> clazz) {
		if (clazz.equals(ContentFrame.class)) {
			return contentBlueprintMap.get(name);
		}
		throw new RuntimeException("No blueprint for '" + name + "', classtype = " + clazz);
	}
}
