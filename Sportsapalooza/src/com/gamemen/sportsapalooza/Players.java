package com.gamemen.sportsapalooza;

import android.graphics.Color;

public class Players {
	static int score = 0;
	static int dudesAvailable = 3;
	private static Color leftColor;
	private static Color rightColor;
	
	public enum PlayerID {
		LEFT,
		RIGHT,
	}
	
	public static void setColor(Color color, PlayerID id) {
		switch(id){
		case LEFT:
			leftColor = color;
			break;
		case RIGHT:
			rightColor = color;
			break;
		}
	}
}
