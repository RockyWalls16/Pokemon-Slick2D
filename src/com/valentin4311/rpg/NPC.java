package com.valentin4311.rpg;

public class NPC extends Event
{
	private double renderX;
	private double renderY;
	
	private int direction = 0;//0: bottom, 1: right, 2: top, 3: left
	private boolean isMoving = false;
	private boolean secondStep = false;
	private int movingStep = 0;
	
	private String textureName;
	private boolean canMove = false;
	
	private int livingTick = 0;
	protected double moveSpeed;
	
	public NPC(int id, String tn, boolean canMove, int x, int y)
	{
		super(id, x, y);
		this.textureName = tn;
		this.canMove = canMove;
		renderX = x * 16;
		renderY = y * 16;
		moveSpeed = 0.02D;
	}
	public void tickNPC(int delta)
	{
		livingTick++;
		if(canMove)
		{
			if(livingTick % 70 == 0 && RPG.currentMap.getRandom().nextInt(60) == 0)
			{
				if(RPG.currentMap.getRandom().nextBoolean())
				{
					direction = RPG.currentMap.getRandom().nextInt(4);
				}
				else
				{
					move(RPG.currentMap.getRandom().nextInt(4));
				}
			}
		}
		if(isMoving && !Map.worldFreezed)
		{
			if(direction == 0)
			{
				renderY += moveSpeed * delta;
				if(renderY >= getTileY() * 16)
				{
					renderY = getTileY() * 16;
					setMovingStep(0);
					isMoving = false;
					if(this instanceof Player)
					{
						Event ev = RPG.currentMap.getEventHere(getTileX(), getTileY());
						if(ev != null)ev.interact((Player)this);
					}
				}
				else if(renderY >= getTileY() * 16 - 8)setMovingStep(2);
			}
			else if(direction == 1)
			{
				renderX += moveSpeed * delta;
				if(renderX >= getTileX() * 16)
				{
					renderX = getTileX() * 16;
					setMovingStep(0);
					isMoving = false;
					if(this instanceof Player)
					{
						Event ev = RPG.currentMap.getEventHere(getTileX(), getTileY());
						if(ev != null)ev.interact((Player)this);
					}
				}
				else if(renderX >= getTileX() * 16 - 8)setMovingStep(2);
			}
			else if(direction == 2)
			{
				renderY -= moveSpeed * delta;
				if(renderY <= getTileY() * 16)
				{
					renderY = getTileY() * 16;
					setMovingStep(0);
					isMoving = false;
					if(this instanceof Player)
					{
						Event ev = RPG.currentMap.getEventHere(getTileX(), getTileY());
						if(ev != null)ev.interact((Player)this);
					}
				}
				else if(renderY <= getTileY() * 16 + 8)setMovingStep(2);
			}
			else if(direction == 3)
			{
				renderX -= moveSpeed * delta;
				if(renderX <= getTileX() * 16)
				{
					renderX = getTileX() * 16;
					setMovingStep(0);
					isMoving = false;
					if(this instanceof Player)
					{
						Event ev = RPG.currentMap.getEventHere(getTileX(), getTileY());
						if(ev != null)ev.interact((Player)this);
					}
				}
				else if(renderX <= getTileX() * 16 + 8)setMovingStep(2);
			}
		}
	}
	public void interact(Player player)
	{
		direction = player.getDirection() + 2 & 3;
		super.interact(player);
	}
	public void move(int dir)
	{
		if(isMoving || Map.worldFreezed)return;
		direction = dir;
		int nextX = direction == 1 ? getTileX() + 1 : direction == 3 ? getTileX() - 1 : getTileX();
		int nextY = direction == 0 ? getTileY() + 1 : direction == 2 ? getTileY() - 1 : getTileY();
		
		if(!RPG.currentMap.isBlocked(this, nextX, nextY) && !RPG.currentMap.isBlocked(this, getTileX(), getTileY()))
		{
			secondStep = !secondStep;
			isMoving = true;
			setMovingStep(1);
			setTileX(nextX);
			setTileY(nextY);
		}
	}
	public double getRenderX()
	{
		return renderX;
	}
	public void setRenderX(int renderX)
	{
		this.renderX = renderX;
	}
	public double getRenderY()
	{
		return renderY;
	}
	public void setRenderY(int renderY)
	{
		this.renderY = renderY;
	}
	public int getDirection()
	{
		return direction;
	}
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	public boolean isMoving()
	{
		return isMoving;
	}
	public void setMoving(boolean isMoving)
	{
		this.isMoving = isMoving;
	}
	public int getMovingStep()
	{
		return movingStep;
	}
	public void setMovingStep(int movingStep)
	{
		this.movingStep = movingStep;
	}
	public int getSpriteID()
	{
		if(!secondStep)
		{
			switch(getMovingStep())
			{
				case 0: return 0;
				case 1: return 0;
				case 2: return 1;
				default: return 0;
			}
		}
		else
		{
			switch(getMovingStep())
			{
				case 0: return 2;
				case 1: return 2;
				case 2: return 3;
				default: return 0;
			}
		}
	}
	public String getTextureName()
	{
		return textureName;
	}
}
