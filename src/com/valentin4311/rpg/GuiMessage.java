package com.valentin4311.rpg;

import org.newdawn.slick.Color;

public class GuiMessage extends Gui
{
	private Message displayedMessage;
	private Event currentEvent;
	
	private boolean side = false;
	
	public GuiMessage(Message displayedMessage, Event event)
	{
		this.displayedMessage = displayedMessage;
		currentEvent = event;
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
		renderer.drawBox(0, 96, 160, 145);
		displayedMessage.tickMessage();
		renderer.getGameFont().drawString(8, 113, displayedMessage.getFirstLine().substring(0, Math.min(displayedMessage.getFirstLine().length(), displayedMessage.getLetterIterator())), Color.black);
		int letterAmount = Math.min(displayedMessage.getSecondLine().length(), displayedMessage.getLetterIterator() - displayedMessage.getFirstLine().length());
		if(letterAmount < 0)letterAmount = 0;
		renderer.getGameFont().drawString(8, 129, displayedMessage.getSecondLine().substring(0, letterAmount), Color.black);
		
		if(displayedMessage.isMessageDisplayed() && !displayedMessage.isLastMessage() && renderer.getRenderTime() / 20 % 50 < 25)
		{
			renderer.getTexture("message_box").draw(144, 129, 151, 134, 24, 0, 31, 5);
		}
		if(displayedMessage.isQuestion() && displayedMessage.isMessageDisplayed() && !displayedMessage.isMessageTooBig())
		{
			int beginY = 96 - displayedMessage.getAwnsers().length * 19 - 1;
			int beginX = 160 - displayedMessage.getMaxLenght() * 9 - 24;
			int sizeX = 160;
			
			if(side)
			{
				beginX = 0;
				sizeX = displayedMessage.getMaxLenght() * 9 + 24;
			}
			
			renderer.drawBox(beginX, beginY, sizeX, 96);
			
			for(int i = 0;i < displayedMessage.getAwnsers().length;i++)
			{
				renderer.getGameFont().drawString(beginX + 16, beginY + i * 16 + 9, displayedMessage.getAwnsers()[i].replace("_", " "), Color.black);
			}
			renderer.getTexture("message_box").draw(beginX + 8, beginY + displayedMessage.getChoice() * 16 + 8, beginX + 13, beginY + displayedMessage.getChoice() * 16 + 15, 24, 5, 29, 12);
		}
	}
	@Override
	public void handleA(GameRemote remote)
	{
		if(!displayedMessage.isMessageDisplayed())return;
		
		if(!displayedMessage.isLastMessage())
		{
			if(!displayedMessage.isMessageTooBig())
			{
				displayedMessage.increaseMessageIterator();
			}
			else
			{
				displayedMessage.nextLine();
			}
		}
		else
		{
			if(displayedMessage.isQuestion())
				CommonVariable.setVariableValue("awnser", displayedMessage.getAwnsers()[displayedMessage.getChoice()]);
			
			if(RPG.getCurrentGui() == this)
				RPG.setCurrentGui(null);
			
			if(currentEvent != null)
			{
				currentEvent.interact(RPG.thePlayer);
			}
		}
	}
	@Override
	public void handleB(GameRemote remote) 
	{
		handleA(remote);
	}
	@Override
	public void handleUp(GameRemote remote)
	{
		if(displayedMessage.isQuestion())
		{
			if(displayedMessage.getChoice() > 0)
			{
				displayedMessage.setChoice(displayedMessage.getChoice()-1);
				remote.setKeyCoolDown(true);
			}
		}
	}
	@Override
	public void handleDown(GameRemote remote)
	{
		if(displayedMessage.isQuestion())
		{
			if(displayedMessage.getChoice() < displayedMessage.getAwnsers().length - 1)
			{
				displayedMessage.setChoice(displayedMessage.getChoice()+1);
				remote.setKeyCoolDown(true);
			}
		}
	}
	@Override
	public void handleLeft(GameRemote remote){}
	
	@Override
	public void handleRight(GameRemote remote){}
	
	public boolean isSide()
	{
		return side;
	}
	public void setSide(boolean side)
	{
		this.side = side;
	}
	public Message getDisplayedMessage()
	{
		return displayedMessage;
	}
}
