package com.valentin4311.rpg;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;

public class GameRenderer
{
	private final HashMap<String, Image> textureList = new HashMap<String, Image>();
	
	private UnicodeFont gameFont;
	
	private int renderTime = 0;
	
	@SuppressWarnings("unchecked")
	public GameRenderer()
	{
		loadImage("tileset");
		loadImage("message_box");
		
		loadImage("npc/character");
		loadImage("npc/girl_0");
		loadImage("npc/boy_0");
		
		try
		{
			gameFont = new UnicodeFont("./assets/font.ttf", 8, false, false);
			getGameFont().addAsciiGlyphs();
			getGameFont().addGlyphs(400, 600);
			getGameFont().getEffects().add(new ColorEffect());
			getGameFont().loadGlyphs();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void loadImage(String url)
	{
		try
		{
			Image img;
			img = new Image("./assets/graphics/"+url+".png");
			img.setFilter(Image.FILTER_NEAREST);
			textureList.put(url, img);
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	public void onTick(GameContainer arg0, Graphics arg1)
	{
		setRenderTime(getRenderTime() + 1);
		
		//Décalage du monde en fonction du joueur plus le centrage (64)
		arg1.translate((float)-RPG.thePlayer.getRenderX() + 64, (float)-RPG.thePlayer.getRenderY() + 64);
		for(int x = 0;x < RPG.currentMap.getMapWidth();x++)
		{
			for(int y = 0;y < RPG.currentMap.getMapHeight();y++)
			{
				drawTile(x, y, getTileAnimation(RPG.currentMap.getRenderMap()[x][y] - 1));
			}
		}
		for(Event ev : RPG.currentMap.getEventList())
		{
			if(ev instanceof NPC)
			{
				NPC npc = (NPC)ev;
				getTexture("npc/"+npc.getTextureName()).draw((int)npc.getRenderX(), (int)npc.getRenderY() - 4, (int)npc.getRenderX() + 16, (int)npc.getRenderY() + 12, npc.getSpriteID() * 16, npc.getDirection() * 16, npc.getSpriteID() * 16 + 16, npc.getDirection() * 16 + 16);
			}
		}
		getTexture("npc/"+RPG.thePlayer.getTextureName()).draw((int)RPG.thePlayer.getRenderX(), (int)RPG.thePlayer.getRenderY() - 4, (int)RPG.thePlayer.getRenderX() + 16, (int)RPG.thePlayer.getRenderY() + 12, RPG.thePlayer.getSpriteID() * 16, RPG.thePlayer.getDirection() * 16, RPG.thePlayer.getSpriteID() * 16 + 16, RPG.thePlayer.getDirection() * 16 + 16);
		
		//Vue normale
		arg1.translate((float)RPG.thePlayer.getRenderX() - 64, (float)RPG.thePlayer.getRenderY() - 64);
		
		Gui currentGui = RPG.getCurrentGui();
		if(currentGui != null)
			currentGui.drawGui(this);
		//Couleur
		arg1.setColor(new Color(0.75F, 0.3F, 0.25F));
		Rectangle rect = new Rectangle(0, 0, 160, 144);
		arg1.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
		arg1.fill(rect);
	}
	public void drawTile(int x, int y, int id)
	{
		int tX = id % 38;
		int tY = id / 38;
		getTexture("tileset").draw(x*16, y*16, x*16+16, y*16+16, tX*16, tY*16, tX*16+16, tY*16+16);
	}
	public int getTileAnimation(int tileID)
	{
		if(tileID == 11 || tileID == 49)
		{
			int timeStep = (getRenderTime() / 400) % 3;
			
			switch(timeStep)
			{
				case 0: return tileID;
				case 1: return tileID + 14;
				case 2: return tileID + 15;
				default: return tileID;
			}
		}
		else if(tileID == 8 || tileID == 9 || tileID == 10 || tileID == 46 || tileID == 47 || tileID == 48 || tileID == 84 || tileID == 85 || tileID == 86)
		{
			int timeStep = (getRenderTime() / 200) % 6;
			
			switch(timeStep)
			{
				case 0: return tileID;
				case 1: return tileID + 19;
				case 2: return tileID + 22;
				case 3: return tileID + 25;
				case 4: return tileID + 22;
				case 5: return tileID + 19;
				default: return tileID;
			}
		}
		else
		{
			return tileID;
		}
	}
	public void drawBox(int x1, int y1, int x2, int y2)
	{
		//Remplissage
		getTexture("message_box").draw(x1, y1, x2, y2, 8, 8, 16, 16);
		//Coins
		getTexture("message_box").draw(x1, y1, x1 + 8, y1 + 8, 0, 0, 8, 8);
		getTexture("message_box").draw(x2 - 8, y1, x2, y1 + 8, 16, 0, 24, 8);
		getTexture("message_box").draw(x1, y2 - 8, x1 + 8, y2, 0, 16, 8, 24);
		getTexture("message_box").draw(x2 - 8, y2 - 8, x2, y2, 16, 16, 24, 24);
		//Bordures
		getTexture("message_box").draw(x1, y1 + 8, x1 + 8, y2 - 8, 0, 8, 8, 16);
		getTexture("message_box").draw(x2 - 8, y1 + 8, x2, y2 - 8, 16, 8, 24, 16);
		getTexture("message_box").draw(x1 + 8, y1, x2 - 8, y1 + 8, 8, 0, 16, 8);
		getTexture("message_box").draw(x1 + 8, y2 - 8, x2 - 8, y2, 8, 16, 16, 24);
	}
	public Image getTexture(String url)
	{
		return textureList.get(url);
	}
	public UnicodeFont getGameFont()
	{
		return gameFont;
	}
	public int getRenderTime()
	{
		return renderTime;
	}
	public void setRenderTime(int renderTime)
	{
		this.renderTime = renderTime;
	}
}
