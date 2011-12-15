package com.jens.typequest.ui;

import org.newdawn.slick.Color;
//fades down in 2 seconds
public class Message {
	private String content;
	private Color color;
	private long created;
	public Message(String content, Color color) {
		super();
		this.content = content;
		this.color = color;
		created = System.currentTimeMillis();
	}
	public long age() {
		return System.currentTimeMillis() - created;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Color getColor() {
		float x = System.currentTimeMillis() - created;
		return color.darker(x/2000.0f);
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	
}
