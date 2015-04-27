package com.valentin4311.rpg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Music;

public class Map 
{
	private final Random random = new Random();
	private final String mapName;
	private Music mapMusic;
	
	private int mapWidth;
	private int mapHeight;
	
	private int[][] renderMap;
	private int[][] collisionMap;

	private final ArrayList<Event> eventList = new ArrayList<Event>();
	
	public static boolean worldFreezed = false;

	public Map(String mapName)
	{
		this.mapName = mapName;
		RPG.currentMap = this;
		try
		{
			String mapDirectory = mapName.split("/")[mapName.split("/").length - 1];
			BufferedReader mapReader = new BufferedReader(new FileReader("./map/"+mapName+"/"+mapDirectory+".map"));
			mapWidth = Integer.valueOf(mapReader.readLine().split("=")[1]);
			mapHeight = Integer.valueOf(mapReader.readLine().split("=")[1]);
			String musicName = mapReader.readLine().split("=")[1];
			mapMusic = new Music("./assets/audio/music/"+musicName+".ogg");
			renderMap = new int[mapWidth][mapHeight];
			collisionMap = new int[mapWidth][mapHeight];
			
			String currentLine;
			int currentRow = 0;
			int arrayID = 0;
			
			while((currentLine = mapReader.readLine()) != null)
			{
				if(currentLine.equals("[render]"))
				{
					arrayID = 0;
					currentRow = 0;
				}
				else if(currentLine.equals("[collision]"))
				{
					arrayID = 1;
					currentRow = 0;
				}
				else if(currentLine.equals("[event]"))
				{
					arrayID = 2;
					currentRow = 0;
				}
				if(currentLine.contains(","))
				{
					String[] data = currentLine.split(",");
					for(int i = 0;i < data.length;i++)
					{
						switch (arrayID)
						{
							case 0:renderMap[i][currentRow] = Integer.valueOf(data[i]);break;
							case 1:collisionMap[i][currentRow] = Integer.valueOf(data[i]);break;
							case 2:
							{
								int eventID = Integer.valueOf(data[i]);
								if(eventID > 0)
								{
									try
									{
										BufferedReader npcReader = new BufferedReader(new FileReader("./map/"+RPG.currentMap.getMapName()+"/event-"+eventID+"-init.event"));
										String eventType = npcReader.readLine().split("=")[1];
										if(eventType.equalsIgnoreCase("event"))
										{
											eventList.add(new Event(eventID, i, currentRow));
										}
										else if(eventType.equalsIgnoreCase("npc"))
										{
											eventList.add(new NPC(eventID, npcReader.readLine().split("=")[1], npcReader.readLine().split("=")[1].equalsIgnoreCase("true"), i, currentRow));
										}
										npcReader.close();
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
								break;
							}
						}
					}
					currentRow++;
				}
			}
			if(!musicName.equals(RPG.backgroundMusic))
			{
				RPG.backgroundMusic = musicName;
				mapMusic.loop();
			}
			mapReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void tickMap(int delta)
	{
		if(!worldFreezed)
		{
			for(Event ev : eventList)
			{
				if(ev instanceof NPC)
				((NPC)ev).tickNPC(delta);
			}
		}
		RPG.thePlayer.tickNPC(delta);
	}
	public Event getEventHere(int x, int y)
	{
		for(Event ev : eventList)
		{
			if(ev.getTileX() == x && ev.getTileY() == y)
			{
				return ev;
			}
		}
		return null;
	}
	public boolean isBlocked(NPC npc, int x, int y)
	{
		if(x < 0 || y < 0 || x > mapWidth || y > mapHeight)return true;
		else if(collisionMap[x][y] != 0)
		{
			return true;
		}
		else
		{
			for(Event ev : eventList)
			{
				if(npc instanceof Player && ev instanceof NPC && ev.getTileX() == x && ev.getTileY() == y)
				{
					return true;
				}
				else if(!(npc instanceof Player) && ev != npc && ((ev.getTileX() == x && ev.getTileY() == y) || (RPG.thePlayer.getTileX() == x && RPG.thePlayer.getTileY() == y)))
				{
					return true;
				}
			}
		}
		return false;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public void setMapWidth(int mapWidth)
	{
		this.mapWidth = mapWidth;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
	public void setMapHeight(int mapHeight)
	{
		this.mapHeight = mapHeight;
	}
	public int[][] getRenderMap()
	{
		return renderMap;
	}
	public void setRenderMap(int[][] renderMap)
	{
		this.renderMap = renderMap;
	}
	public int[][] getCollisionMap()
	{
		return collisionMap;
	}
	public void setCollisionMap(int[][] collisionMap)
	{
		this.collisionMap = collisionMap;
	}
	public ArrayList<Event> getEventList()
	{
		return eventList;
	}
	public String getMapName()
	{
		return mapName;
	}
	public Random getRandom() 
	{
		return random;
	}
	public Music getMapMusic()
	{
		return mapMusic;
	}
}
