package com.jens.typequest;
import java.util.Random;

import com.jens.typequest.model.EnemyEntity;

@Deprecated
public class TextGenerator {

	static String[] content = new String[]{"You know that I want you",
			"And you know that I need you",
			"I want it bad",
			"Your bad romance",
			"Rah rah ah ah ah"};
	
	static Random r = new Random(System.currentTimeMillis());
	
	public static String generateText(EnemyEntity entity){
		return content[r.nextInt(content.length)];
	}
}
