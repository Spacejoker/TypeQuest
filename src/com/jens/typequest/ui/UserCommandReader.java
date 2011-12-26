package com.jens.typequest.ui;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.model.Button;
import com.jens.typequest.model.StateHandler;
import com.jens.typequest.model.StateHandler.Mode;

public class UserCommandReader implements KeyListener, MouseListener {

	public static final char CLOSE_DIALOGUE = ' ';
	
	StringBuilder characterQue = new StringBuilder();
	LinkedList<Integer> queuedCommands = new LinkedList<Integer>();
	Queue<Character> queueCommandKeys = new LinkedList<Character>();
	LinkedList<Vector2f> queuedClicks = new LinkedList<Vector2f>();
	
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
		queuedCommands.offer(arg0);
		queueCommandKeys.offer(character);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
	}

	public void handleQueuedCommands(StateHandler stateHandler) throws SlickException {
		out:while (queuedClicks.size()> 0) {
			Vector2f poll = queuedClicks.poll();
			for (int i = 0; i < stateHandler.getClickEntities().size(); i++) {
				Button entity = stateHandler.getClickEntities().get(i);
				if(entity.isIn(poll.x, poll.y)){
					System.out.println("in "  + entity.getImage().getResourceReference());
					
					stateHandler.isClicked(entity);
					break out;
				}
			}
		}
		queuedClicks.clear();
		
		while(!queueCommandKeys.isEmpty()) {
			Character c = queueCommandKeys.poll();
			if(c.equals(CLOSE_DIALOGUE) && stateHandler.getCurrentMode().equals(Mode.BATTLE) && stateHandler.getBattle().isCompleted()) {
				stateHandler.enterTown();
			}
		}
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int arg0, int x, int y) {
		if(arg0 == 0){
			queuedClicks.offer(new Vector2f(x,y));
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
