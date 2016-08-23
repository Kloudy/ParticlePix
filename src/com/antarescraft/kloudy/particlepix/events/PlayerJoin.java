package com.antarescraft.kloudy.particlepix.events;

import java.util.Hashtable;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.particlepix.ParticlePix;

public class PlayerJoin implements Listener
{
	public static Hashtable<UUID, Player> justJoinedPlayers;
	
	public PlayerJoin()
	{
		justJoinedPlayers = new Hashtable<UUID, Player>();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		justJoinedPlayers.put(player.getUniqueId(), player);
		
		BukkitRunnable removePlayer = new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if(justJoinedPlayers.containsKey(player.getUniqueId()))
				{
					justJoinedPlayers.remove(player.getUniqueId());
				}
			}
		};
		removePlayer.runTaskLater(ParticlePix.plugin, 200);//removes player after delay
	}
}
