package com.jens.typequest.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.Gson;
import com.jens.typequest.model.Battle;
import com.jens.typequest.model.EnemyBlueprint;
import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.Wave;

public class ContentLoader {

	static String contentFolder = "src/content/json/";

	static List<EnemyBlueprint> enemyBlueprints = new ArrayList<EnemyBlueprint>();

	public static EnemyEntity getEnemy(String id, int nr) {

		if (enemyBlueprints.size() == 0) {
			loadBlueprints();
		}

		System.out.println("Looking for id: " + id);
		for (int i = 0; i < enemyBlueprints.size(); i++) {
			System.out.println("enemy has id: " + enemyBlueprints.get(i).getId());
			if (enemyBlueprints.get(i).getId().equals(id)) {
				return createEnemy(enemyBlueprints.get(i), nr);
			}
		}
		return null;
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
				// ;
				// enemyBlueprints.add(new EnemyBlueprint("1", 0.1, 1.5, 100L, Arrays.asList(new String[]{"Ra ra oh la la"}), "enemy"));
				enemyBlueprints.add(gson.fromJson(readLine, EnemyBlueprint.class));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static EnemyEntity createEnemy(EnemyBlueprint enemyBlueprint, int nr) {

		// randomize a new position
		int rand = RandomUtil.nextInt(200);
		Image image = ImageProvider.getImage(enemyBlueprint.getImagePath());
		rand += image.getHeight();

		return new EnemyEntity(enemyBlueprint.getId(), new Vector2f(1024 + 100*nr, 800 - rand), image, enemyBlueprint);
	}

	public static Battle getBattle(String nextBattleId) {
		Battle battle = new Battle();

		Wave w = new Wave();
		w.add(getEnemy("2", 0));
		w.add(getEnemy("2", 1));
		w.add(getEnemy("2", 2));
		w.add(getEnemy("2", 3));
		w.add(getEnemy("2", 4));
		w.add(getEnemy("2", 5));
		w.add(getEnemy("2", 6));
		w.add(getEnemy("2", 7));
		w.add(getEnemy("2", 8));
		w.add(getEnemy("2", 9));
		
		battle.getWaves().add(w);

		
		w = new Wave();
		w.add(getEnemy("1", 0));
		w.add(getEnemy("2", 1));
		w.add(getEnemy("1", 2));

		battle.getWaves().add(w);

		w = new Wave();
		w.add(getEnemy("2", 1));
		w.add(getEnemy("1", 2));
		
		battle.getWaves().add(w);

		return battle;
	}
}
