package com.antarescraft.kloudy.particlepix;

import java.io.Serializable;

public class ParticleImage implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private int[][][] colors;
	
	public ParticleImage(int[][][] colors, String name)
	{
		this.colors = colors;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getFrameCount()
	{
		int frameCount = -1;
		if(colors != null)
		{
			frameCount = colors.length;
		}
		
		return frameCount;
	}
	
	public int getHeight()
	{
		int height = -1;
		if(colors[0] != null)
		{
			height = colors[0].length;
		}
		
		return height;
	}
	
	public int getWidth()
	{
		int width = -1;
		if(colors[0] != null && colors[0][0] != null)
		{
			width = colors[0][0].length;
		}
		
		return width;
	}
	
	public int getPixel(int frameIndex, int x, int y)
	{
		return colors[frameIndex][x][y];
	}
}