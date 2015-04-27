package com.valentin4311.rpg;

import java.io.FileOutputStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

public class RPG extends BasicGame
{
	private GameRemote gameRemote;
	public static GameRenderer gameRenderer;
	private static Gui currentGui = null;
	
	public static Map currentMap;
	public static String backgroundMusic;
	
	public static Player thePlayer;
	
	public static int delta = 0;
	
	private AppGameContainer container;
	
	public RPG() throws SlickException
	{
		super("Pokemon");
		container = new AppGameContainer(new ScalableGame(this, 160, 144), 480, 432, false);
		container.setDisplayMode(480, 432, false);
		container.setShowFPS(false);
		container.start();
	}
	public static void main(String[] args)
	{
		try
		{
			new RPG();
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException
	{
		gameRenderer.onTick(arg0, arg1);
	}
	@Override
	public void init(GameContainer arg0) throws SlickException
	{
		gameRenderer = new GameRenderer();
		gameRemote = new GameRemote(container.getInput());
		
		new Map("pallet_town");
		thePlayer = new Player(8, 8);
	}
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException
	{
		gameRemote.tickRemote();
		currentMap.tickMap(arg1);
		delta = arg1;
	}
	public static Gui getCurrentGui()
	{
		return currentGui;
	}
	public static void setCurrentGui(Gui currentGui)
	{
		if(getCurrentGui() != null)
			getCurrentGui().closeGui();
		
		RPG.currentGui = currentGui;
		
		if(getCurrentGui() != null)
			getCurrentGui().openGui();
	}
	public static void saveGame()
	{
		System.out.println("Saving Game...");
		
		try
		{
			FileOutputStream outputStream = new FileOutputStream("./saves/save.gsv");
			
			outputStream.write("filestart".getBytes());
			outputStream.write(TimeUtil.getPlayTime().getBytes());
			outputStream.write(thePlayer.getTileX());
			outputStream.write(thePlayer.getTileY());
			outputStream.write(thePlayer.getDirection());
			outputStream.write((int)CommonVariable.getVariableValue("money"));
			//#A revoir
			outputStream.write("endoffile".getBytes());
			
			outputStream.close();
			
			System.out.println("Save done !");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
