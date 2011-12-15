package com.jens.typequest.ui;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.jens.typequest.model.StateHandler;

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
	public void keyPressed(int arg0, char character) {
		if (arg0 == Input.KEY_TAB) {
			System.out.println("TAB");
			StateHandler.getInstance().getPlayer().cycleEnemy();
		} else {
			characterQue.append(character);
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
	}
}
