package com.valentin4311.rpg;

import org.newdawn.slick.Color;

public class GuiMenu extends Gui
{
	public static int cursorPos = 0;
	
	@Override
	public void openGui()
	{
		Map.worldFreezed = true;
	}
	@Override
	public void closeGui()
	{
		Map.worldFreezed = false;
	}
	@Override
	public void drawGui(GameRenderer renderer)
	{
		renderer.drawBox(80, 0, 160, 128);
		renderer.getGameFont().drawString(96, 16, "Pokedex", Color.black);
		renderer.getGameFont().drawString(96, 32, "Pokemon", Color.black);
		renderer.getGameFont().drawString(96, 48, "Sac", Color.black);
		renderer.getGameFont().drawString(96, 64, ((String)CommonVariable.getVariableValue("playerName")).substring(0, 7), Color.black);
		renderer.getGameFont().drawString(96, 80, "Sauver", Color.black);
		renderer.getGameFont().drawString(96, 96, "Options", Color.black);
		renderer.getGameFont().drawString(96, 112, "Retour", Color.black);
		renderer.getTexture("message_box").draw(89, 16 + cursorPos * 16, 94, 23 + cursorPos * 16, 24, 5, 29, 12);
	}
	@Override
	public void handleA(GameRemote remote)
	{
		switch(cursorPos)
		{
			case 0:
			{
				RPG.setCurrentGui(new GuiMessage(new Message(new String[]{"Ce n'est pas encore fait !"}), null));
				break;
			}
			case 1:
			{
				RPG.setCurrentGui(new GuiMessage(new Message(new String[]{"Ce n'est pas encore fait !"}), null));
				break;
			}
			case 2:
			{
				RPG.setCurrentGui(new GuiMessage(new Message(new String[]{"Ce n'est pas encore fait !"}), null));
				break;
			}
			case 3:
			{
				RPG.setCurrentGui(new GuiMessage(new Message(new String[]{"Ce n'est pas encore fait !"}), null));
				break;
			}
			case 4:
			{
				RPG.setCurrentGui(new GuiSave(this));
				break;
			}
			case 5:
			{
				RPG.setCurrentGui(new GuiMessage(new Message(new String[]{"Ce n'est pas encore fait !"}), null));
				break;
			}
			case 6:
			{
				RPG.setCurrentGui(null);
				break;
			}
		}
	}
	@Override
	public void handleB(GameRemote remote) 
	{
		RPG.setCurrentGui(null);
	}
	@Override
	public void handleUp(GameRemote remote)
	{
		if(cursorPos > 0)
		{
			cursorPos--;
			remote.setKeyCoolDown(true);
		}
	}
	@Override
	public void handleDown(GameRemote remote)
	{
		if(cursorPos < 6)
		{
			cursorPos++;
			remote.setKeyCoolDown(true);
		}
	}
	@Override
	public void handleLeft(GameRemote remote){}
	
	@Override
	public void handleRight(GameRemote remote){}
}
