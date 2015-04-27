package com.valentin4311.rpg;

public class Item
{
	public static Item[] itemList = new Item[256];
	
	public static Item potion = new Item(0, "Potion", 200);
	public static Item superPotion = new Item(1, "Super potion", 600);
	public static Item hyperPotion = new Item(2, "Hyper potion", 1200);
	public static Item pokeball = new Item(3, "Pokeball", 600);
	public static Item superball = new Item(4, "Superball", 1000);
	public static Item hyperball = new Item(5, "Hyperball", 1500);
	
	private final int itemID;
	private final String itemName;
	private final int moneyValue;
	
	public Item(int id, String name, int money)
	{
		itemID = id;
		itemName = name;
		moneyValue = money;
		itemList[id] = this;
	}
	public Item(int id, String name)
	{
		this(id, name, 100);
	}
	public int getItemID()
	{
		return itemID;
	}
	public String getItemName()
	{
		return itemName;
	}
	public int getMoneyValue()
	{
		return moneyValue;
	}
}
