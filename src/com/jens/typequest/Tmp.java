package com.jens.typequest;

import java.util.Arrays;

import com.google.gson.Gson;
import com.jens.typequest.model.EnemyBlueprint;

public class Tmp {
	
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		EnemyBlueprint enemyBlueprint = new EnemyBlueprint("1", 0.1, 1.5, 100L, Arrays.asList(new String[]{"Ra ra oh la la"}), "enemy", 1, 9, 10);
		String json = gson.toJson(enemyBlueprint);
		System.out.println(json);
		EnemyBlueprint fromJson = gson.fromJson(json, EnemyBlueprint.class);
		System.out.println(fromJson.getAttackSpeed());
	}
}
