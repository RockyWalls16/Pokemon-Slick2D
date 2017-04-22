package com.valentin4311.rpg;

import org.newdawn.slick.Color;

public class GuiSave extends Gui
{
	private static GuiMessage message;
	private GuiMenu backMenu;
	
	public GuiSave(GuiMenu menu)
	{
		backMenu = menu;
		message = new GuiMessage(new Message("Vous vous sauvegarder la partie ?<awnser>Oui;Non"), null);
		message.setSide(true);
	}
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
		backMenu.drawGui(renderer);
		
		renderer.drawBox(32, 0, 160, 80);
		renderer.getGameFont().drawString(40, 16, "Joueur " + CommonVariable.getVariableValue("playerName").toString().substring(0, Math.min(7, CommonVariable.getVariableValue("playerName").toString().length())), Color.black);
		renderer.getGameFont().drawString(40, 32, "Badges", Color.black);
		renderer.getGameFont().drawString(40, 48, "Pokedex", Color.black);
		renderer.getGameFont().drawString(144, 48, "0", Color.black);
		renderer.getGameFont().drawString(40, 64, "Temps", Color.black);
		renderer.getGameFont().drawString(152 - TimeUtil.getPlayTime().length() * 8, 64, TimeUtil.getPlayTime(), Color.black);
		
		message.drawGui(renderer);
	}
	@Override
	public void handleA(GameRemote remote)
	{
		if(message.getDisplayedMessage().getCurrentMessage().equals("Sauvegarde termine !"))
		{
			RPG.setCurrentGui(null);
			return;
		}
		
		message.handleA(remote);
		
		if(CommonVariable.getVariableValue("awnser") != null)
		{
			if(CommonVariable.getVariableValue("awnser").toString().equals("Oui"))
			{
				RPG.saveGame();
				message = new GuiMessage(new Message(new String[]{"Sauvegarde termine !"}), null);
			}
			else
			{
				RPG.setCurrentGui(null);
			}
		}
	}
	@Override
	public void handleB(GameRemote remote)
	{
		message.handleB(remote);
	}
	@Override
	public void handleUp(GameRemote remote)
	{
		message.handleUp(remote);
	}
	@Override
	public void handleDown(GameRemote remote)
	{
		message.handleDown(remote);
	}
	@Override
	public void handleLeft(GameRemote remote){}
	
	@Override
	public void handleRight(GameRemote remote){}
}
