package com.valentin4311.rpg;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler
{
	private static final ArrayList<Integer> blockList = new ArrayList<Integer>();
	private static int currentBlockID = -1;
	private static int fakeBlockID = -1;

	public static boolean handleCommand(Event ev, String cmd)
	{
		try
		{
			String[] separator = cmd.split(">>>");
			
			String command = separator[0];
			String argument = separator[1];
			
			Pattern pattern = Pattern.compile("\\[(.*?)\\]");
			Matcher matcher = pattern.matcher(argument);
			
			while(matcher.find())
			{
				String variableName = matcher.group(1);
			    Object variableValue = CommonVariable.getVariableValue(variableName);
			    if(variableValue != null)
			    {
			    	argument = argument.replace("["+variableName+"]", variableValue.toString());
			    }
			}
			
			if(command.equals("print") && canExecuteCommand("print"))
			{
				RPG.setCurrentGui(new GuiMessage(new Message(argument.split("<next>")), ev));
			}
			else if(command.equals("ask") && canExecuteCommand("ask"))
			{
				RPG.setCurrentGui(new GuiMessage(new Message(argument), ev));
			}
			else if(command.equals("warp") && canExecuteCommand("warp"))
			{
				String[] coords = argument.split(",");
				ev.setCurrentCommand(-1);
				blockList.clear();
				currentBlockID = -1;
				RPG.thePlayer.changeMap(coords[0], Integer.valueOf(coords[1]), Integer.valueOf(coords[2]));
				return true;
			}
			else if(command.equals("set") && canExecuteCommand("set"))
			{
				String[] scheme = argument.split(",", 2);
				
				Object variableValue = CommonVariable.getVariableValue(scheme[0]);
			    if(variableValue != null)
			    {
			    	if(variableValue instanceof String)
			    	{
			    		CommonVariable.setVariableValue(scheme[0], scheme[1]);
			    	}
			    	else if(variableValue instanceof Boolean)
			    	{
			    		CommonVariable.setVariableValue(scheme[0], scheme[1].equalsIgnoreCase("true"));
			    	}
			    	else if(variableValue instanceof Integer)
			    	{
			    		CommonVariable.setVariableValue(scheme[0], Integer.valueOf(scheme[1]));
			    	}
			    	else if(variableValue instanceof Double)
			    	{
			    		CommonVariable.setVariableValue(scheme[0], Double.valueOf(scheme[1]));
			    	}
			    }
			}
			else if(command.equals("add") && canExecuteCommand("add"))
			{
				String[] scheme = argument.split(",", 2);
				
				Object variableValue = CommonVariable.getVariableValue(scheme[0]);
			    if(variableValue != null)
			    {
			    	if(variableValue instanceof Integer)
			    	{
			    		CommonVariable.setVariableValue(scheme[0], Integer.valueOf(scheme[1]) + (int)variableValue);
			    	}
			    	else if(variableValue instanceof Double)
			    	{
			    		CommonVariable.setVariableValue(scheme[0], Double.valueOf(scheme[1]) + (double)variableValue);
			    	}
			    }
			}
			else if(command.equals("end") && canExecuteCommand("end"))
			{
				ev.setCurrentCommand(-1);
				blockList.clear();
				currentBlockID = -1;
				return true;
			}
			else if(command.equals("if"))
			{
				if(canExecuteCommand("if"))
				{
					blockList.add(isConditionOK(argument) ? 1 : 0);
					currentBlockID++;
				}
				fakeBlockID++;
			}
			else if(command.equals("elseif") && canExecuteCommand("elseif"))
			{
				if(blockList.get(currentBlockID) == 1)
					blockList.set(currentBlockID, -1);
					
				if(blockList.get(currentBlockID) == 0 && isConditionOK(argument))
				{
					blockList.set(currentBlockID, 1);
				}
				else if(blockList.get(currentBlockID) == 1)
				{
					blockList.set(currentBlockID, 0);
				}
			}
			else if(command.equals("else") && canExecuteCommand("else"))
			{
				if(blockList.get(currentBlockID) == 0 && blockList.get(currentBlockID) != -1)
				{
					blockList.set(currentBlockID, 1);
				}
				else
				{
					blockList.set(currentBlockID, 0);
				}
			}
			else if(command.equals("endif"))
			{
				if(canExecuteCommand("endif"))
				{
					blockList.remove(currentBlockID);
					currentBlockID--;
				}
				fakeBlockID--;
			}
			return false;
		}
		catch(Exception e)
		{
			System.out.println("Error at command : "+cmd);
			e.printStackTrace();
			return true;
		}
	}
	public static boolean canExecuteCommand(String cmd)
	{
		if(cmd.equals("else") || cmd.equals("elseif") || cmd.equals("endif"))
		{
			return fakeBlockID == currentBlockID;
		}
		boolean isAllowed = true;
		for(int i : blockList)
		{
			if(i != 1)
			{
				isAllowed = false;
				break;
			}
		}
		if(isAllowed || currentBlockID == -1)
		{
			return true;
		}
		return false;
	}
	public static boolean isConditionOK(String compiledText)
	{
		String[] conditionScheme = compiledText.split(" ");
		
		switch(conditionScheme[1])
		{
			case "=": return conditionScheme[0].equals(conditionScheme[2]);
			case "!=": return !conditionScheme[0].equals(conditionScheme[2]);
			case "<": return conditionScheme[0].compareTo(conditionScheme[2]) < 0;
			case ">": return conditionScheme[0].compareTo(conditionScheme[2]) > 0;
			case "<=": return conditionScheme[0].compareTo(conditionScheme[2]) <= 0;
			case ">=": return conditionScheme[0].compareTo(conditionScheme[2]) >= 0;
			default: return false;
		}
	}
}
