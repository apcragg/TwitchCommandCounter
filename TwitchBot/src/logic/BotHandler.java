package logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BotHandler
{
	private final String commands[] = { "start", "left", "right", "up", "down", "a", "b", "anarchy", "democracy"};
	private Map<String, Integer> com;
	
	public BotHandler()
	{
		com = new HashMap<String, Integer>();
		
		for(String s : commands)
			com.put(s, 0);
	}
	
	public void processMessage(String message)
	{
		message = message.toLowerCase();
		
		if(!com.containsKey(message))
			com.put(message, 1);
		else
			com.put(message, com.get(message) + 1);
	}	
	
	public String getTop()
	{
		int high = 0;
		String message = "default";
		
		Iterator<Map.Entry<String, Integer>> i = com.entrySet().iterator();
		
		while(i.hasNext())
		{
			Map.Entry<String, Integer> entry = i.next();
			int j = entry.getValue();

			if(j >= high)
			{
				high = j;
				message = entry.getKey();
			}
		}
		
		return message + " " + high;
	}
	
	public String[] getCommands()
	{
		return commands;
	}
	
	public Map<String, Integer> getCommandCount()
	{
		return com;
	}
}
