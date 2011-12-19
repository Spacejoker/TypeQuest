package com.jens.typequest.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.Gson;
import com.jens.typequest.model.Battle;
import com.jens.typequest.model.EnemyBlueprint;
import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.Player;
import com.jens.typequest.model.StateHandler;
import com.jens.typequest.model.Wave;

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
		
		if(appropriateBlueprints.size() == 0){
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

		return new EnemyEntity(enemyBlueprint.getId(), new Vector2f(1024 + 100*nr, 800 - rand), image, enemyBlueprint, level);
	}

	public static Battle getBattle(int difficulty) {
		Battle battle = new Battle();
		//typical map is 10 levels
		
		StateHandler handler = StateHandler.getInstance();
		Player player = handler.getPlayer();
		
		// and a boss at the end
		Wave w = new Wave();
		w.add(getEnemy(1, 0, true));
		battle.addWave(w);
		
		// level should be difficulty +- 2
		for (int i = 0; i < 1; i++) {
			int cnt = 1 + RandomUtil.nextInt(3);
			w = new Wave();
			for (int j = 0; j < cnt; j++) {
				int level = RandomUtil.nextInt(5) - 2 + difficulty;
				level = Math.max(level,0);
				
				w.add(getEnemy(level, j, false));
			}
			battle.addWave(w);
		}

		return battle;
	}
}
