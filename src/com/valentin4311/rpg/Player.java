package com.valentin4311.rpg;

public class Player extends NPC
{
	public static long startTime = 0;
	
	public Player(int x, int y)
	{
		super(-1, "character", false, x, y);
		moveSpeed = 0.06D;
		
		startTime = System.currentTimeMillis();
		
		CommonVariable.setVariableValue("playerName", System.getProperty("user.name"));
		CommonVariable.setVariableValue("money", 0);
	}
	public void changeMap(String newMap, int x, int y)
	{
		new Map(newMap);
		RPG.thePlayer.setTileX(x);
		RPG.thePlayer.setTileY(y);
		RPG.thePlayer.setMovingStep(0);
		RPG.thePlayer.setMoving(false);
		RPG.thePlayer.setRenderX(x * 16);
		RPG.thePlayer.setRenderY(y * 16);
	}
}
