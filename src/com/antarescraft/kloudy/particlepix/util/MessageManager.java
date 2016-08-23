package com.antarescraft.kloudy.particlepix.util;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager 
{
	public static final int ELEMENTS_PER_PAGE = 10;
	
	public static void error(CommandSender sender, String message)
	{
		if(sender != null)
		{
			sender.sendMessage(ChatColor.RED + message);
		}
	}
	
	public static void success(CommandSender sender, String message)
	{
		if(sender != null)
		{
			sender.sendMessage(ChatColor.GREEN + message);
		}	
	}
	
	public static void info(CommandSender sender, String message)
	{
		if(sender != null)
		{
			sender.sendMessage(ChatColor.GOLD + message);
		}
	}
	
	public static void pageList(CommandSender sender, ArrayList<String> elements, String listTitle)
	{
		pageList(sender, elements, "0", listTitle);
	}
	
	public static void pageList(CommandSender sender, ArrayList<String> elements, String pageString, String listTitle)
	{
		int page = 0;
		try
		{
			page = Integer.parseInt(pageString) -1;
			
			if(page < 0)
			{
				page = 0;
			}
		}
		catch(NumberFormatException e)
		{
			pageList(sender, elements, listTitle);
		}
		
		int totalPages = 0;
		
		if(elements.size() % 10 > 0)
		{
			totalPages = (elements.size() / 10) + 1;
		}
		else
		{
			totalPages = (elements.size() / 10);
		}
		
		String message = ChatColor.GOLD + "===============================\n";
		message += String.format("%s List - Page: %d Total Pages: %d\n" + ChatColor.AQUA, listTitle, page+1, totalPages);
		
		for(int i = page * 10; i <= ((page*10) + 10)-1; i++)
		{
			if(i >= elements.size())
			{
				break;
			}
			else
			{
				message +=  elements.get(i).toString() + "\n";
			}
		}
		
		message += ChatColor.GOLD + "===============================";
		
		sender.sendMessage(message);
	}
}