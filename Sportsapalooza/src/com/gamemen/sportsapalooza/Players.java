package com.gamemen.sportsapalooza;

import android.graphics.Color;

public class Players {
	private static int rightScore = 0;
	private static int leftScore = 0;
	private static int rightDudes = 3;
	private static int leftDudes = 3;
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
	
	public static Color getColor(PlayerID id) {
		switch(id){
		default:
			return null;
		case LEFT:
			return leftColor;
		case RIGHT:
			return rightColor;
		}
	}
	
	public static void score(PlayerID id) {
		switch(id){
		case LEFT:
			leftScore++;
		case RIGHT:              //I dunno how much it'll actually increment
			rightScore++;
		}
	}
	
	public static void dudeUsed(PlayerID id) {
		switch(id){
		case LEFT:
			leftDudes--;
		case RIGHT:           //I can't decide if these are better than having two separate player classes
			rightDudes--;
		}
	}
	
	public static void dudeReturned(PlayerID id) {
		switch(id){
		case LEFT:
			leftDudes++;
		case RIGHT:
			rightDudes++;
		}
	}
}