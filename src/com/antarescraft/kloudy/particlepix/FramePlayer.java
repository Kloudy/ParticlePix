package com.antarescraft.kloudy.particlepix;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.particlepix.events.PlayerJoin;
import com.antarescraft.kloudy.particlepix.protocol.ParticlePacket;

public class FramePlayer extends BukkitRunnable
{	
	public ParticleDisplay particleDisplay;
	private int frame;
	
	public FramePlayer(ParticleDisplay particleDisplay)
	{
		this.particleDisplay = particleDisplay;
		frame = 0;
		
		init();
	}
	
	private void init()//cancels old and add this new FramePlayer to the Hashtable of FramePlayers
	{
		FramePlayer framePlayer = ParticlePix.FramePlayers.get(particleDisplay.getName());
		if(framePlayer != null)
		{
			framePlayer.cancel();
			ParticlePix.FramePlayers.remove(particleDisplay.getName());
		}
		
		ParticlePix.FramePlayers.put(particleDisplay.getName(), this);
	}
	
	public void start()
	{
		this.runTaskTimer(ParticlePix.plugin, 0, ParticlePix.configValues.getFramerate());
	}

	@Override
	public void run() 
	{	
		try
		{
			if(frame >= particleDisplay.getParticleImage().getFrameCount())
			{				
				frame = 0;
			}
				
			List<Player> players = Bukkit.getWorld(particleDisplay.getLocation().getWorld().getUID()).getPlayers();
			
			for(Player player : players)
			{
				if(player.getLocation().distance(particleDisplay.getLocation()) <= ParticlePix.configValues.getRenderDistance()
						&& !PlayerJoin.justJoinedPlayers.containsKey(player.getUniqueId()) && !ParticlePix.PlayerNoShow.containsKey(player.getUniqueId()))
				{	
					try
					{
						int ping = ParticlePix.getPlayerPing(player);

						if(ping < ParticlePix.configValues.getMaxPing())//don't send packets to a player who's ping exceeds the allowed maximum
						{
							ParticlePacket.sendParticlePackets(player, particleDisplay, frame);
						}
					}
					catch(Exception e)
					{
						ParticlePacket.sendParticlePackets(player, particleDisplay, frame);
					}		
				}
			}

			frame += 1;
		}
		catch(Exception e)
		{
			ParticlePix.FramePlayers.remove(particleDisplay.getName());
			this.cancel();
		}
	}
}