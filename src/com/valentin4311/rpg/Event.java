package com.valentin4311.rpg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Event
{
	private final int id;
	private int tileX;
	private int tileY;
	
	private final ArrayList<String> commandList = new ArrayList<String>();
	private int currentCommand = -1;
	
	public Event(int id, int x, int y)
	{
		this.id = id;
		tileX = x;
		tileY = y;
		if(!(this instanceof Player))
		{
			RPG.currentMap.getEventList().add(this);
		
			try
			{
				BufferedReader eventReader = new BufferedReader(new FileReader("./map/"+RPG.currentMap.getMapName()+"/event-"+id+"-interact.event"));
				String currentLine = null;
				
				while((currentLine = eventReader.readLine()) != null)
				{
					if(!currentLine.isEmpty())getCommandList().add(currentLine.replace("\t", ""));
				}
				eventReader.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void interact(Player player)
	{
		if(!Map.worldFreezed)
		{
			currentCommand++;
			if(!CommandHandler.handleCommand(this, getCommandList().get(currentCommand)))
			interact(player);
		}
	}
	public int getId()
	{
		return id;
	}
	public int getTileX()
	{
		return tileX;
	}
	public void setTileX(int tileX)
	{
		this.tileX = tileX;
	}
	public int getTileY()
	{
		return tileY;
	}
	public void setTileY(int tileY)
	{
		this.tileY = tileY;
	}
	public int getCurrentCommand()
	{
		return currentCommand;
	}
	public void setCurrentCommand(int currentCommand)
	{
		this.currentCommand = currentCommand;
	}
	public ArrayList<String> getCommandList()
	{
		return commandList;
	}
}
