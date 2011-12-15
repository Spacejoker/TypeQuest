package com.jens.typequest.loaders;

import java.util.Random;

public class RandomUtil {

	static Random r = new Random(System.currentTimeMillis());
	
	public static int nextInt(int i) {
		return r.nextInt(i);
	}

	
}
