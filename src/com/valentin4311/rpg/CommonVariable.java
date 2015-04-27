package com.valentin4311.rpg;

import java.util.HashMap;

public class CommonVariable
{
	private static final HashMap<String, Object> varList = new HashMap<String, Object>();
	
	public static void setVariableValue(String name, Object newValue)
	{
		if(varList.containsKey(name))
		{
			varList.replace(name, newValue);
		}
		else
		{
			varList.put(name, newValue);
		}
	}
	public static Object getVariableValue(String name)
	{
		return varList.get(name);
	}
}
