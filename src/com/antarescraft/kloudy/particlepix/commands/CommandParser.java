package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandParser implements CommandExecutor
{
	private String baseCommand, packagePath, commandErrorMessage;
	public ArrayList<String> CommandHelpStrings;
	
	public CommandParser(String baseCommand, String packagePath, String commandErrorMessage)
	{
		this.baseCommand = baseCommand;
		this.packagePath = packagePath;
		this.commandErrorMessage = commandErrorMessage;
		
		CommandHelpStrings = new ArrayList<String>();
		
		BaseCommand base = new BaseCommand();
		DeletedisplayCommand deleteDisplay = new DeletedisplayCommand();
		DeletealldisplaysCommand deleteAllDisplays = new DeletealldisplaysCommand();
		DeleteimageCommand deleteImage = new DeleteimageCommand();
		DisplayCommand display = new DisplayCommand();
		DisplaylistCommand displayList = new DisplaylistCommand();
		ImagelistCommand imageList = new ImagelistCommand();
		ImageCommand image = new ImageCommand();
		StartCommand start = new StartCommand();
		ToggledisplayCommand toggle = new ToggledisplayCommand();
		ToggledoffplayersCommand toggledOff = new ToggledoffplayersCommand();
		
		CommandHelpStrings.add(base.commandUsage());
		CommandHelpStrings.add(deleteDisplay.commandUsage());
		CommandHelpStrings.add(deleteAllDisplays.commandUsage());
		CommandHelpStrings.add(deleteImage.commandUsage());
		CommandHelpStrings.add(display.commandUsage());
		CommandHelpStrings.add(displayList.commandUsage());
		CommandHelpStrings.add(imageList.commandUsage());
		CommandHelpStrings.add(image.commandUsage());
		CommandHelpStrings.add(start.commandUsage());
		CommandHelpStrings.add(toggle.commandUsage());
		CommandHelpStrings.add(toggledOff.commandUsage());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase(baseCommand) || alias(cmd, label))
		{
			if(args.length == 0)
			{
				BaseCommand baseCommand = new BaseCommand();
				baseCommand.call(sender, null);
			}
			else
			{
				try
				{
					//Command Parser works based on a naming convention
					//Convention: <SubCommand>Command.java - Must implement Callable
					//ex. userinput: /test help would invoke the 'call' function in HelpCommand.java
					//Only the first character must be capitalized in the class name
					//Bad: SpawnEntityCommand.java		Good: SpawnentityCommand.java
					
					String className = args[0].toLowerCase();
					className = className.substring(0, 1).toUpperCase() + className.substring(1);
					className += "Command";
					
					Callable callableCommand = (Callable) Class.forName(packagePath + "." + className).newInstance();
					
					ArrayList<String> dataArguments = new ArrayList<String>();
					for(int i = 1; i < args.length; i++)//sub command
					{
						dataArguments.add(args[i]);
					}
					
					//check args length
					if(callableCommand.getArgsLength().compareArgsLength(dataArguments.size()))
					{
						callableCommand.call(sender, dataArguments);
					}
					else
					{
						sender.sendMessage(ChatColor.RED + commandErrorMessage + " Usage: \n" + callableCommand.commandUsage());
					}
				}
				catch (ClassNotFoundException | SecurityException | IllegalAccessException | 
						IllegalArgumentException | InstantiationException e) 
				{
					sender.sendMessage(ChatColor.RED + commandErrorMessage);
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	private boolean alias(Command cmd, String label)
	{
		boolean hasAlias = false;
		
		for(String alias : cmd.getAliases())
		{
			if(alias.equalsIgnoreCase(label))
			{
				hasAlias = true;
				break;
			}
		}
		
		return hasAlias;
	}
}
