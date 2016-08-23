package com.antarescraft.kloudy.particlepix.protocol;

import org.bukkit.entity.Player;

import com.antarescraft.kloudy.particlepix.DisplayDirection;
import com.antarescraft.kloudy.particlepix.ParticleDisplay;
import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

public class ParticlePacket 
{
	public static void sendParticlePackets(Player player, ParticleDisplay particleDisplay, int frameIndex)
	{
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		ParticleImage particleImage = particleDisplay.getParticleImage();
		
		//int leftOutParticles = (int)(BaseImageProcessor.PARTICLES_PER_FRAME - Math.round(BaseImageProcessor.PARTICLES_PER_FRAME * density));
		//int particleSkipInterval = (int) (BaseImageProcessor.PARTICLES_PER_FRAME / leftOutParticles);
		
		//int skipCount = particleSkipInterval;
		
		for(int y = 0; y < particleImage.getHeight(); y++)
		{
			for(int x = 0; x < particleImage.getWidth(); x++)
			{
				WrapperPlayServerWorldParticles packet = new WrapperPlayServerWorldParticles();
				packet.setParticleType(Particle.REDSTONE);
				packet.setLongDistance(true);
				packet.setParticleData((float)1);
				
				float xLoc = 0;
				float yLoc = 0;
				float zLoc = 0;
				
				if(particleDisplay.getDirection() == DisplayDirection.NORTH)
				{
					xLoc = (float) (particleDisplay.getLocation().getX() + (-0.15 * x));
					yLoc = (float) (particleDisplay.getLocation().getY() + (0.15 * y));
					zLoc = (float) particleDisplay.getLocation().getZ();
				}
				else if(particleDisplay.getDirection() == DisplayDirection.SOUTH)
				{
					xLoc = (float) (particleDisplay.getLocation().getX() + (0.15 * x));
					yLoc = (float) (particleDisplay.getLocation().getY() + (0.15 * y));
					zLoc = (float) particleDisplay.getLocation().getZ();
				}
				else if(particleDisplay.getDirection() == DisplayDirection.EAST)
				{
					xLoc = (float) (particleDisplay.getLocation().getX());
					yLoc = (float) (particleDisplay.getLocation().getY() + (0.15 * y));
					zLoc = (float) (particleDisplay.getLocation().getZ() + (-0.15 * x));
				}
				else if(particleDisplay.getDirection() == DisplayDirection.WEST)
				{
					xLoc = (float) (particleDisplay.getLocation().getX());
					yLoc = (float) (particleDisplay.getLocation().getY() + (0.15 * y));
					zLoc = (float) (particleDisplay.getLocation().getZ() + (0.15 * x));
				}

				packet.setX(xLoc);
				packet.setY(yLoc);
				packet.setZ(zLoc);
				
				int rgb = particleImage.getPixel(frameIndex, particleImage.getHeight() - y - 1, x);
				int mask = 0xFF;
				int red = (rgb >> 16) & mask;
				int green = (rgb >> 8) & mask;
				int blue = rgb & mask;
				int alpha = (rgb >> 24) & mask;
				
				if(red == 0)
				{
					packet.setOffsetX((1/255F));
				}
				else
				{
					packet.setOffsetX((red/255F));
				}
				
				if(green == 0)
				{
					packet.setOffsetY(1/255F);
				}
				else
				{
					packet.setOffsetY(green/255F);
				}
				
				if(blue == 0)
				{
					packet.setOffsetZ(1/255F);
				}
				else
				{
					packet.setOffsetZ(blue/255F);
				}

				try
				{
					if(alpha != 0)
					{
						manager.sendServerPacket(player, packet.getHandle());
					}
					//else
					//{
					//	skipCount = particleSkipInterval;
					//}
				}
				catch(Exception e)
				{
					if(ParticlePix.configValues.getDebugMode())
					{
						e.printStackTrace();
					}
				}
				
				//skipCount -= 1;
			}
		}
	}
}
