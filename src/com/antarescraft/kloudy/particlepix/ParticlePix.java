package com.antarescraft.kloudy.particlepix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.antarescraft.kloudy.particlepix.commands.CommandParser;
import com.antarescraft.kloudy.particlepix.events.PlayerJoin;
import com.antarescraft.kloudy.particlepix.events.PlayerQuit;
import com.antarescraft.kloudy.particlepix.imageprocessing.ImageLauncher;
import com.antarescraft.kloudy.particlepix.util.*;

public class ParticlePix extends JavaPlugin
{
	public static JavaPlugin plugin;
	public static ConfigValues configValues;
	public static Hashtable<String, ParticleDisplay> ParticleDisplays;
	public static Hashtable<String, ParticleImage> ParticleImages;
	public static Hashtable<String, FramePlayer> FramePlayers;
	public static Hashtable<UUID, String> PlayerNoShow;
	public static ResourceManager ResourceManager;
	public static PluginDescriptionFile PluginDescription;
	public static CommandParser CommandParser;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		ParticleImages = new Hashtable<String, ParticleImage>();
		FramePlayers = new Hashtable<String, FramePlayer>();
		ParticleDisplays = new Hashtable<String, ParticleDisplay>();
		PlayerNoShow = new Hashtable<UUID, String>();
		configValues = new ConfigValues(getConfig());
		ResourceManager = new ResourceManager();
		PluginDescription = getDescription();
		
		saveDefaultConfig();
		
		String baseCommand = "particlepix";
		String commandPackagePath = "com.antarescraft.kloudy.particlepix.commands";
		String commandErrorMsg = "Invalid command or parameters.";
		
		CommandParser = new CommandParser(baseCommand, commandPackagePath, commandErrorMsg);
		
		getCommand("particlepix").setExecutor(CommandParser);
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		
		IOManager.initFileStructure();
		IOManager.loadDisplays();
		IOManager.loadToggledPlayers();
		
		ResourceManager.loadResources();
		
		for(ParticleDisplay particleDisplay : ParticleDisplays.values())
		{
			if(particleDisplay.getIsRunning())
			{
				ImageLauncher.start(particleDisplay);
			}
		}
	}
	
	@Override
	public void onDisable()
	{
		IOManager.saveDisplays();
		IOManager.saveToggledPlayers();
	}

	public static boolean imageExists(String name)
	{
		ArrayList<String> imageNamesFromFile = IOManager.getImageNames();
		
		boolean exists = false;
		for(String fileName : imageNamesFromFile)
		{
			if(fileName.equals(name))
			{
				exists = true;
				break;
			}
		}
		
		return exists;
	}
	
	public static BlockFace yawToFace(float yaw) 
	{
		BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
        return axis[Math.round(yaw / 90f) & 0x3];
    }
	
	public static int getPlayerPing(Player player) throws Exception {
        int ping = 0;

        /* first, get the craftPlayer class, for it we use the Class.forName, which will search the class with the given name */
        Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit."+getServerVersion()+"entity.CraftPlayer");

        /* now, convert the player to craftplayer using reflection, this is the same thing as doing ((CraftPlayer)player) */
        Object converted = craftPlayer.cast(player);

        /* using the converted class which sould be a CraftPlayer instance, try to find the getHandle method */
        Method handle = converted.getClass().getMethod("getHandle");

        /* now we just have to call this method, is the same thing as doing the ((CraftPlayer)player).getHandle(); */
        Object entityPlayer = handle.invoke(converted);
        
        /* at this point we should already have the EntityPlayer instance, now we just have to find the 'ping' field */
        Field pingField = entityPlayer.getClass().getField("ping");
        
        /* now that we already have the ping field, just get its value from the entityPlayer object and we are good */
        ping = pingField.getInt(entityPlayer);
        
        return ping;
    }

    /**
     * Return the server package name, it is used on reflection.
     *
     * @return it might be 1.4.5., v1.4.5., v1_4_5., v1_4_R1. or something in this pattern;
     */
    public static String getServerVersion() {

        /* compile a simple pattern to match any kind of server version, with or without the safe-guard */
        Pattern brand = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
        String version = null;

        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        String version0 = pkg.substring(pkg.lastIndexOf('.') + 1);

        /* if the pattern does not match, it means that the server does not have the save-guard (libigot or old versions) */
        if (!brand.matcher(version0).matches()) {
            version0 = "";
        }

        version = version0;

        return !"".equals(version) ? version + "." : "";
    }
}
