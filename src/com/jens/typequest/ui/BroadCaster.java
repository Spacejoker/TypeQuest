package com.jens.typequest.ui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

public class BroadCaster {

	List<Message> messagesOnQue = new ArrayList<Message>();

	private static BroadCaster instance;

	private BroadCaster() {
	}

	public static BroadCaster getInstance() {
		if(instance == null){
			instance = new BroadCaster();
		}
		return instance;
	}

	public void writeMessage(String content, Color color) {
		messagesOnQue.add(new Message(content, color));
	}

	public List<Message> getMessages() {
		return messagesOnQue;
	}
}
