package com.valentin4311.rpg;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

public class GameRemote implements InputProviderListener
{
	private Input input;
	
	private Command buttonA = new BasicCommand("A_Button");
	private Command buttonB = new BasicCommand("B_Button");
	private Command buttonStart = new BasicCommand("Start_Button");
	
	private boolean keyCoolDown = false;
	
	public GameRemote(Input input)
	{
		this.input = input;
		InputProvider inputProvider = new InputProvider(input);
		inputProvider.bindCommand(new KeyControl(Input.KEY_Z), buttonA);
		inputProvider.bindCommand(new KeyControl(Input.KEY_A), buttonB);
		inputProvider.bindCommand(new KeyControl(Input.KEY_ENTER), buttonStart);
		inputProvider.addListener(this);
	}
	public void tickRemote()
	{
		if(keyCoolDown)
		{
			try
			{
				Thread.sleep(200);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(!keyCoolDown)
		{
			if(input.isKeyDown(Keyboard.KEY_DOWN))
			{
				if(RPG.getCurrentGui() != null)
				{
					RPG.getCurrentGui().handleDown(this);
				}
				else
				{
					RPG.thePlayer.move(0);
				}		
			}
			else if(input.isKeyDown(Keyboard.KEY_RIGHT))
			{
				if(RPG.getCurrentGui() != null)
				{
					RPG.getCurrentGui().handleRight(this);
				}
				else
				{
					RPG.thePlayer.move(1);
				}
			}
			else if(input.isKeyDown(Keyboard.KEY_UP))
			{
				if(RPG.getCurrentGui() != null)
				{
					RPG.getCurrentGui().handleUp(this);
				}
				else
				{
					RPG.thePlayer.move(2);
				}
			}
			else if(input.isKeyDown(Keyboard.KEY_LEFT))
			{
				if(RPG.getCurrentGui() != null)
				{
					RPG.getCurrentGui().handleLeft(this);
				}
				else
				{
					RPG.thePlayer.move(3);
				}
			}
		}
		else
		{
			keyCoolDown = false;
		}
	}
	@Override
	public void controlPressed(Command arg0)
	{
		if(arg0 == this.buttonA && !RPG.thePlayer.isMoving())
		{
			if(RPG.getCurrentGui() != null)
			{
				RPG.getCurrentGui().handleA(this);
			}
			else
			{
				int nextX = RPG.thePlayer.getDirection() == 1 ? RPG.thePlayer.getTileX() + 1 : RPG.thePlayer.getDirection() == 3 ? RPG.thePlayer.getTileX() - 1 : RPG.thePlayer.getTileX();
				int nextY = RPG.thePlayer.getDirection() == 0 ? RPG.thePlayer.getTileY() + 1 : RPG.thePlayer.getDirection() == 2 ? RPG.thePlayer.getTileY() - 1 : RPG.thePlayer.getTileY();
				Event ev = RPG.currentMap.getEventHere(nextX, nextY);
				if(ev != null)ev.interact((Player)RPG.thePlayer);
			}
		}
		else if(arg0 == this.buttonB && !RPG.thePlayer.isMoving())
		{
			if(RPG.getCurrentGui() != null)
			{
				RPG.getCurrentGui().handleB(this);
			}
			else
			{
				int nextX = RPG.thePlayer.getDirection() == 1 ? RPG.thePlayer.getTileX() + 1 : RPG.thePlayer.getDirection() == 3 ? RPG.thePlayer.getTileX() - 1 : RPG.thePlayer.getTileX();
				int nextY = RPG.thePlayer.getDirection() == 0 ? RPG.thePlayer.getTileY() + 1 : RPG.thePlayer.getDirection() == 2 ? RPG.thePlayer.getTileY() - 1 : RPG.thePlayer.getTileY();
				Event ev = RPG.currentMap.getEventHere(nextX, nextY);
				if(ev != null)ev.interact((Player)RPG.thePlayer);
			}
		}
		else if(arg0 == this.buttonStart && !RPG.thePlayer.isMoving())
		{
			if(RPG.getCurrentGui() instanceof GuiMenu)
			{
				RPG.setCurrentGui(null);
			}
			else if(RPG.getCurrentGui() == null)
			{
				RPG.setCurrentGui(new GuiMenu());
			}
		}
	}
	@Override
	public void controlReleased(Command arg0) {}
	
	public boolean isKeyCoolDown()
	{
		return keyCoolDown;
	}
	public void setKeyCoolDown(boolean keyCoolDown)
	{
		this.keyCoolDown = keyCoolDown;
	}
}
