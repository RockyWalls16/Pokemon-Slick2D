package com.valentin4311.rpg;

public class TimeUtil
{
	public static String getPlayTime()
	{
		long playTimeInSecond = (System.currentTimeMillis() - Player.startTime) / 1000;
		
		String playTimeInHours = String.valueOf((playTimeInSecond / 216000));

		String playTimeInMinutes = String.valueOf((playTimeInSecond / 60));
		if(playTimeInMinutes.length() == 1)
			playTimeInMinutes = "0" + playTimeInMinutes;
		
		return playTimeInHours + ":" + playTimeInMinutes; 
	}
}
