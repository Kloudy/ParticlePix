package com.antarescraft.kloudy.particlepix.commands;

public enum ArgsLength 
{
	LESS_THAN,
	LESS_THAN_OR_EQUAL_TO,
	EQUALS,
	GREATER_THAN,
	GREATER_THAN_OR_EQUAL_TO;
	
	private int numArgs;
	
	public void setNumArgs(int argsLength)
	{
		this.numArgs = argsLength;
	}
	
	public boolean compareArgsLength(int argsLength)
	{
		boolean result = false;
		
		switch(this)
		{
			case LESS_THAN:
				if(argsLength < numArgs)
				{
					result = true;
				}
				break;
			case LESS_THAN_OR_EQUAL_TO:
				if(argsLength <= numArgs)
				{
					result = true;
				}
				break;
			case EQUALS:
				if(argsLength == numArgs)
				{
					result = true;
				}
				break;
			case GREATER_THAN:
				if(argsLength > numArgs)
				{
					result = true;
				}
				break;
			case GREATER_THAN_OR_EQUAL_TO:
				if(argsLength >= numArgs)
				{
					result = true;
				}
				break;
		}
		
		return result;
	}
}
