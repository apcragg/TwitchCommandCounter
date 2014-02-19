package main;

import java.io.IOException;
import java.rmi.ConnectException;

import logic.BotHandler;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class TwitchBot extends PircBot
{
	private String currentMessage;
	private BotHandler handler;
	
	public TwitchBot(String name, BotHandler handler)
	{
		this.currentMessage = "default";
		this.setName(name);
		this.setHandler(handler);
		setVerbose(true);
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		System.out.println("Message: " + message);
		
		handler.processMessage(message);
		
		setCurrentMessage(handler.getTop());
	}
	
	public void exit()
	{
		setVerbose(true);
		quitServer("Because reasons.");
		dispose();
	}
	
	public void connectToServer(String server, int port, String key)
	{
		try
		{
			connect(server, port, key);
		}
		catch (NickAlreadyInUseException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (IrcException e)
		{
			e.printStackTrace();
		}
		
		//setVerbose(false);
	}
	
	public boolean testConnection()
	{
		boolean isConnected = isConnected();
		
		if(isConnected)	System.out.println("Connected!");
		else System.out.println("Failed to connect...");
		
		return isConnected;
	}

	public String getCurrentMessage()
	{
		return currentMessage;
	}

	public void setCurrentMessage(String currentMessage)
	{
		this.currentMessage = currentMessage;
	}

	public BotHandler getHandler()
	{
		return handler;
	}

	public void setHandler(BotHandler handler)
	{
		this.handler = handler;
	}
}