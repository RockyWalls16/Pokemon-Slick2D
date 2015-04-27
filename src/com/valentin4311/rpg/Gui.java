package com.valentin4311.rpg;

public abstract class Gui
{
	public abstract void openGui();
	
	public abstract void closeGui();
	
	public abstract void drawGui(GameRenderer renderer);
	
	public abstract void handleA(GameRemote remote);
	
	public abstract void handleB(GameRemote remote);
	
	public abstract void handleUp(GameRemote remote);
	
	public abstract void handleDown(GameRemote remote);
	
	public abstract void handleLeft(GameRemote remote);
	
	public abstract void handleRight(GameRemote remote);
}
