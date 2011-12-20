package com.jens.typequest.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jens.typequest.TypeQuestConstants;
import com.jens.typequest.model.Battle;
import com.jens.typequest.model.Button;
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
	
	private static ContentLoader instance;

	private ContentLoader(){

	}
	
	public static ContentLoader getInstance(){
		if(instance == null){
			instance = new ContentLoader();			
		}
		return instance;
	}
	
//	static List<EnemyBlueprint> enemyBlueprints = new ArrayList<EnemyBlueprint>();
	
	 Map<String, ContentFrameBlueprint> contentBlueprintMap = new HashMap<String, ContentFrameBlueprint>();
	 Map<String, EnemyBlueprint> enemyBlueprints = new HashMap<String, EnemyBlueprint>();
	

	public  void init() {
		Gson gson = new Gson();
		try {
		
			BufferedReader reader = new BufferedReader(new FileReader(contentFolder + "enemies.json"));
			while (true) {
				String enemyData = reader.readLine();
				if(enemyData == null || enemyData.length() == 0){
					break;
				}
				EnemyBlueprint print = gson.fromJson(enemyData, EnemyBlueprint.class);
				enemyBlueprints.put(print.getId(), print);
			}
			
			reader = new BufferedReader(new FileReader(contentFolder + "contentframes.json"));
			while (true) {
				String contentData = reader.readLine();
				if(contentData == null || contentData.length() == 0){
					break;
				}
				System.out.println(contentData);
				ContentFrameBlueprint print = gson.fromJson(contentData, ContentFrameBlueprint.class);
				contentBlueprintMap.put(print.getId(), print);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  StringBuffer readAll(String string) throws Exception {
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

	public  EnemyEntity getEnemy(int level, int nr, boolean boss) {
		
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
	
	private  EnemyEntity createEnemy(EnemyBlueprint enemyBlueprint, int nr, int level) {

		// randomize a new position
		int rand = RandomUtil.nextInt(200);
		Image image = ImageProvider.getImage(enemyBlueprint.getImagePath());
		rand += image.getHeight();

		return new EnemyEntity(enemyBlueprint.getId(), new Vector2f(1024 + 100 * nr, 800 - rand), image, enemyBlueprint, level);
	}

	public  Battle getBattle(int difficulty) {
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

	public  String loadJsonString(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/content/json/" + fileName + ".json"));
			return reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public  ContentFrame getContentFrame(String id){
		
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
			
			contentFrame.getButtons().add(new Button(TypeQuestConstants.CLOSE_PLAYER_STATS, new Vector2f(0, 300), new Vector2f(200, 80), ImageProvider.getImage("button-battle")));
		} else if(id.equals("battleComplete")){
			contentFrame.getEntities().addAll(
					Arrays.asList(new GraphicalEntity[]{
				new TextEntity("You completed the level!",  new Vector2f(300,220), Color.darkGray),
				new TextEntity("Gained gold: " + state.getBattle().getGainedGold(), new Vector2f(300,250), Color.darkGray),
				new TextEntity("Gained xp: " + state.getBattle().getGainedXp(), new Vector2f(300,280), Color.darkGray),
				new TextEntity("You should get back to town and celebrate!", new Vector2f(300,310), Color.darkGray)
					}));
			contentFrame.getButtons().add(new Button(TypeQuestConstants.ENTER_TOWN_BUTTON_ID, new Vector2f(0, 600), new Vector2f(200, 80), ImageProvider.getImage("button-battle")));
		} else if(id.equals("town")){
			contentFrame.getButtons().add(new Button(TypeQuestConstants.ENTER_BATTLE_BUTTON_ID, new Vector2f(0, 720), new Vector2f(200, 80), ImageProvider.getImage("button-battle")));
			contentFrame.getButtons().add(new Button(TypeQuestConstants.SHOW_PLAYER_STATS, new Vector2f(200, 720), new Vector2f(200, 80), ImageProvider.getImage("button-stats")));
		} else if(id.equals("mainMenu")){
			contentFrame.getButtons().add(new Button(TypeQuestConstants.ENTER_TOWN_BUTTON_ID, new Vector2f(0, 520), new Vector2f(200, 80), ImageProvider.getImage("button-battle")));
		} else if(id.equals("battle")){
			contentFrame.getButtons().add(new Button(TypeQuestConstants.ENTER_TOWN_BUTTON_ID, new Vector2f(0, 550), new Vector2f(200, 80), ImageProvider.getImage("button-battle")));
		}
		
		
		return contentFrame;
	}

	public  Blueprint getBlueprint(String name, Class<ContentFrame> clazz) {
		if (clazz.equals(ContentFrame.class)) {
			return contentBlueprintMap.get(name);
		}
		throw new RuntimeException("No blueprint for '" + name + "', classtype = " + clazz);
	}
}
