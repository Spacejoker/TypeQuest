package com.jens.typequest.loaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.model.Battle;
import com.jens.typequest.model.EnemyBlueprint;
import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.Wave;

public class ContentLoader {

	static List<EnemyBlueprint> enemyBlueprints = new ArrayList<EnemyBlueprint>();
	
	public static EnemyEntity getEnemy(String id){
		
		if(enemyBlueprints.size() == 0){
			loadBlueprints();
		}
		
		System.out.println("Looking for id: " + id);
		for (int i = 0; i < enemyBlueprints.size(); i++) {
			System.out.println("enemy has id: "+ enemyBlueprints.get(i).getId());
			if(enemyBlueprints.get(i).getId().equals(id)){
				return createEnemy(enemyBlueprints.get(i));
			}
		}
//		enemies.add(new EnemyEntity("apa", new Vector2f(800, 500), new Image("src/content/image/enemy.png"), new Image("src/content/image/enemy_marked.png"), EnemyType.OZZY));
//		enemies.add(new EnemyEntity("apa", new Vector2f(680, 450), new Image("src/content/image/enemy.png"), new Image("src/content/image/enemy_marked.png"), EnemyType.OZZY));
//		enemies.add(new EnemyEntity("apa", new Vector2f(300, 660), new Image("src/content/image/enemy.png"), new Image("src/content/image/enemy_marked.png"), EnemyType.OZZY));
		System.out.println("Problem detected");
		return null;
	}

	private static void loadBlueprints() {
		enemyBlueprints.add(new EnemyBlueprint("1", 0.1, 1.5, 100L, Arrays.asList(new String[]{"Ra ra oh la la"}), "enemy"));
	}

	private static EnemyEntity createEnemy(EnemyBlueprint enemyBlueprint) {
		
		//randomize a new position
		int rand = RandomUtil.nextInt(200);
		Image image = ImageProvider.getImage(enemyBlueprint.getImagePath());
		rand += image.getHeight();
		
		
		
		return new EnemyEntity(enemyBlueprint.getId(),new Vector2f(1024+RandomUtil.nextInt(300), 800 - rand) , image, enemyBlueprint);
	}

	public static Battle getBattle(String nextBattleId) {
		Battle battle = new Battle();
		
		Wave w = new Wave();
		w.add(getEnemy("1"));
		w.add(getEnemy("1"));
		w.add(getEnemy("1"));
		
		battle.getWaves().add(w);
		
		w = new Wave();
		w.add(getEnemy("1"));
		w.add(getEnemy("1"));
		w.add(getEnemy("1"));
		
		battle.getWaves().add(w);
		
		return battle;
	}
}
