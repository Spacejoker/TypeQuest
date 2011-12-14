package com.jens.typequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.jens.typequest.model.EnemyEntity;
import com.jens.typequest.model.Player;

/**
 * This class is retarded, but I cannot find a smarter way to do it with thie Input-class
 * 
 * @author Jensa
 */
public class TextProcessor implements KeyListener {

	StringBuilder characterQue = new StringBuilder();

	public String popCharacterQue() {
		String string = characterQue.toString();
		characterQue = new StringBuilder();
		return string;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		if (arg0 == Input.KEY_TAB) {
			Player.getInstance().cycleEnemy();
		} else {
			System.out.println("Got char: " + arg1);
			characterQue.append(arg1);
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
	}
}
