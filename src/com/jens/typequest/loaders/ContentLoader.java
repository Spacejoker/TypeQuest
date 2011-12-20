package com.jens.typequest.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.Gson;
import com.jens.typequest.model.Battle;
import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.GraphicalEntity;
import com.jens.typequest.model.Player;
import com.jens.typequest.model.StateHandler;
import com.jens.typequest.model.TextEntity;
import com.jens.typequest.model.Wave;
import com.jens.typequest.model.blueprint.Blueprint;
import com.jens.typequest.model.blueprint.ContentFrameBlueprint;
import com.jens.typequest.model.blueprint.EnemyBlueprint;
import com.jens.typequest.ui.ContentFrame;

public class ContentLoader {

	static String contentFolder = "src/content/json/";

	static List<EnemyBlueprint> enemyBlueprints = new ArrayList<EnemyBlueprint>();

	public static EnemyEntity getEnemy(int level, int nr, boolean boss) {

		if (enemyBlueprints.size() == 0) {
			loadBlueprints();
		}

		List<EnemyBlueprint> appropriateBlueprints = new ArrayList<EnemyBlueprint>();

		for (int i = 0; i < enemyBlueprints.size(); i++) {
			if (enemyBlueprints.get(i).isBoss() == boss && enemyBlueprints.get(i).getMinlevel() <= level && enemyBlueprints.get(i).getMaxlevel() >= level) {
				appropriateBlueprints.add(enemyBlueprints.get(i));
			}
		}

		if (appropriateBlueprints.size() == 0) {
			appropriateBlueprints.addAll(enemyBlueprints);
		}

		return createEnemy(appropriateBlueprints.get(RandomUtil.nextInt(appropriateBlueprints.size())), nr, level);
	}

	private static void loadBlueprints() {

		Gson gson = new Gson();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(contentFolder + "enemies.json"));
			while (true) {

				String readLine = reader.readLine();
				if (readLine == null) {
					return;
				}

				enemyBlueprints.add(gson.fromJson(readLine, EnemyBlueprint.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static EnemyEntity createEnemy(EnemyBlueprint enemyBlueprint, int nr, int level) {

		// randomize a new position
		int rand = RandomUtil.nextInt(200);
		Image image = ImageProvider.getImage(enemyBlueprint.getImagePath());
		rand += image.getHeight();

		return new EnemyEntity(enemyBlueprint.getId(), new Vector2f(1024 + 100 * nr, 800 - rand), image, enemyBlueprint, level);
	}

	public static Battle getBattle(int difficulty) {
		Battle battle = new Battle();
		// typical map is 10 levels

		StateHandler handler = StateHandler.getInstance();
		Player player = handler.getPlayer();

		// and a boss at the end
		Wave w = new Wave();
		w.add(getEnemy(1, 0, true));
		battle.addWave(w);

		// level should be difficulty +- 2
//		for (int i = 0; i < 1; i++) {
//			int cnt = 1 + RandomUtil.nextInt(3);
//			w = new Wave();
//			for (int j = 0; j < cnt; j++) {
//				int level = RandomUtil.nextInt(5) - 2 + difficulty;
//				level = Math.max(level, 0);
//
//				w.add(getEnemy(level, j, false));
//			}
//			battle.addWave(w);
//		}

		return battle;
	}

	public static String loadJsonString(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/content/json/" + fileName + ".json"));
			return reader.readLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public static ContentFrame getContentFrame(String id){
		
		StateHandler state = StateHandler.getInstance();
		ContentFrameBlueprint blueprint = (ContentFrameBlueprint) getBlueprint(id, ContentFrame.class);

		ContentFrame contentFrame = new ContentFrame();
		
		contentFrame.setBackground(ImageProvider.getImage(blueprint.getBackgroundImage()));
		contentFrame.setPosition(blueprint.getPosition());
		
		if(id.equals("playerStats")){
			contentFrame.getEntities().addAll(Arrays.asList(
					new TextEntity("Current level: " + state.getPlayer().getLevel(), new Vector2f(400,250), Color.darkGray),
					new TextEntity("Xp current (next level): " + state.getPlayer().getXp() + "(" + state.getPlayer().getLevel()+ ")", new Vector2f(400,280), Color.darkGray),
					new TextEntity("Gold: " + state.getPlayer().getGold(), new Vector2f(400,310), Color.darkGray)));
		} else if(id.equals("battleComplete")){
			contentFrame.getEntities().addAll(
					Arrays.asList(new GraphicalEntity[]{
				new TextEntity("You completed the level!",  new Vector2f(300,220), Color.darkGray),
				new TextEntity("Gained gold: " + state.getBattle().getGainedGold(), new Vector2f(300,250), Color.darkGray),
				new TextEntity("Gained xp: " + state.getBattle().getGainedXp(), new Vector2f(300,280), Color.darkGray),
				new TextEntity("You should get back to town and celebrate!", new Vector2f(300,310), Color.darkGray)
					}));
		}
		
		return contentFrame;
	}

	public static Blueprint getBlueprint(String name, Class clazz) {
		if (clazz.equals(ContentFrame.class)) {
			if ("playerStats".equals(name)) {
				ContentFrameBlueprint playerStatsBlueprint = new ContentFrameBlueprint();
				playerStatsBlueprint.setBackgroundImage("playerstatsbg");
				playerStatsBlueprint.setPosition(new Vector2f(100, 100));
				return playerStatsBlueprint;
			}
			if ("battleComplete".equals(name)) {
				ContentFrameBlueprint playerStatsBlueprint = new ContentFrameBlueprint();
				playerStatsBlueprint.setBackgroundImage("battlecomplete");
				playerStatsBlueprint.setPosition(new Vector2f(200, 100));
				return playerStatsBlueprint;
			}
		}
		return null;
	}
}
