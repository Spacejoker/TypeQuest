package com.jens.typequest.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.jens.typequest.loaders.ImageProvider;

public class HealthBar {

	double percentage = 100;
	Image characterImage = null;

	Vector2f position = new Vector2f(120, 120);

	double mindegree = 0;
	double maxdegree = 7*210.0/360.0;

	Polygon healthPart = new Polygon();
	Polygon deathPart = new Polygon();

	Curve c = new Curve(new Vector2f(10, 10), new Vector2f(100, 10), new Vector2f(10, 100), new Vector2f(100, 100));
	Image bg = null;
	public HealthBar() {
		setPercentage(100);
		updateHealthBar();
		bg = ImageProvider.getImage("healthbar-bg");
	}

	public void updateHealthBar() {
		double mod = 1.0/60.0;
		double rad = 100;

		healthPart = new Polygon();
		
		if(percentage > 0){
			double start = (maxdegree -  mindegree) * (100-percentage) / 100.0 + mindegree;
			
			for (double degree = start; degree >= start; degree += mod) {
				if (degree < maxdegree) {
					System.out.println(degree);
					float x = (float) (Math.cos(degree) * rad + position.x);
					float y = (float) (Math.sin(degree) * rad*-1 + position.y);
					healthPart.addPoint(x, y);
				} else {
					mod = Math.abs(mod)*-1;
					rad = 80;
				}
			}
		} else {
			//empty life - dead
			healthPart = null;
		}
	}

	public Polygon getRedPart() {
		return healthPart;
	}

	public void setP(Polygon p) {
		this.healthPart = p;
	}

	public Image getCharacterImage() {
		return characterImage;
	}

	public void setCharacterImage(Image characterImage) {
		this.characterImage = characterImage;
	}

	public double getMindegree() {
		return mindegree;
	}

	public void setMindegree(double mindegree) {
		this.mindegree = mindegree;
	}

	public double getMaxdegree() {
		return maxdegree;
	}

	public void setMaxdegree(double maxdegree) {
		this.maxdegree = maxdegree;
	}

	public Curve getC() {
		return c;
	}

	public void setC(Curve c) {
		this.c = c;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Polygon getHealthPart() {
		return healthPart;
	}

	public void setHealthPart(Polygon healthPart) {
		this.healthPart = healthPart;
	}

	public Polygon getDeathPart() {
		return deathPart;
	}

	public void setDeathPart(Polygon deathPart) {
		this.deathPart = deathPart;
	}

	public Image getBg() {
		return bg;
	}

	public void setBg(Image bg) {
		this.bg = bg;
	}

	
}
