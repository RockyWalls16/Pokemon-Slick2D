package com.valentin4311.rpg;

public class Message
{
	private int letterIterator = 0;
	private int messageIterator = 0;
	private String[] totalMessage;
	private double timePassed = 0;
	
	private String[] awnsers;
	private boolean isQuestion = false;
	private int choice = 0;
	
	public Message(String[] messages)
	{
		totalMessage = messages;
	}
	public Message(String question)
	{
		CommonVariable.setVariableValue("awnser", null);
		totalMessage = new String[]{question.split("<awnser>")[0]};
		awnsers = question.split("<awnser>")[1].split(";");
		isQuestion = true;
	}
	public void tickMessage()
	{
		timePassed += 3.5 / 100 * RPG.delta;//Original is 1.5
		if(timePassed >= 1)
		{
			timePassed = 0;
			increaseLetterIterator();
		}
	}
	public String getFirstLine()
	{
		String[] words = getCurrentMessage().split(" ");
		String result = "";
		
		for(String word : words)
		{
			if(result.length() + word.length() > 17)
			{
				return result;
			}
			else
			{
				result += word + " ";
			}
		}
		return result;
	}
	public String getSecondLine()
	{
		String[] words = getCurrentMessage().split(" ");
		String result = "";
		
		for(String word : words)
		{
			if(result.length() - getFirstLine().length() + word.length() > 17)
			{
				return result.substring(getFirstLine().length(), result.length());
			}
			else
			{
				result += word + " ";
			}
		}
		return result.substring(getFirstLine().length(), result.length());
	}
	public void nextLine()
	{
		letterIterator -= getFirstLine().length();
		totalMessage[messageIterator] = getCurrentMessage().substring(getFirstLine().length());
	}
	public String getCurrentMessage()
	{
		return totalMessage[messageIterator];
	}
	public boolean isLastMessage()
	{
		return messageIterator == totalMessage.length - 1 && !isMessageTooBig();
	}
	public boolean isMessageTooBig()
	{
		String[] words = getCurrentMessage().split(" ");
		String result = "";
		
		for(String word : words)
		{
			if(result.length() - getFirstLine().length() + word.length() > 17)
			{
				return true;
			}
			else
			{
				result += word + " ";
			}
		}
		return false;
	}
	public int getMaxLenght()
	{
		int max = 0;
		for(String str : awnsers)
		{
			if(str.length() > max)
				max = str.length();
		}
		return max;
	}
	//ACS
	public void increaseMessageIterator()
	{
		this.messageIterator++;
		resetLetterIterator();
	}
	public int getLetterIterator()
	{
		return letterIterator;
	}
	public void increaseLetterIterator()
	{
		if(letterIterator < getFirstLine().length() + getSecondLine().length())
		{
			letterIterator++;
		}
	}
	public void resetLetterIterator()
	{
		letterIterator = 0;
		timePassed = 0;
	}
	public boolean isMessageDisplayed()
	{
		return !(getLetterIterator() < getFirstLine().length() + getSecondLine().length());
	}
	public boolean isQuestion()
	{
		return isQuestion;
	}
	public void setQuestion(boolean isQuestion)
	{
		this.isQuestion = isQuestion;
	}
	public int getChoice()
	{
		return choice;
	}
	public void setChoice(int choice)
	{
		this.choice = choice;
	}
	public String[] getAwnsers()
	{
		return awnsers;
	}
	public void setAwnsers(String[] awnsers)
	{
		this.awnsers = awnsers;
	}
}
