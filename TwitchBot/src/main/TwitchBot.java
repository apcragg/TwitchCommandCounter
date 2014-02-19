package main;

import java.io.IOException;
import java.rmi.ConnectException;

import logic.BotHandler;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class TwitchBot extends PircBot
{
	private String currentMessage, currentServer, currentKey, currentChannel;
	private BotHandler handler;
	private int currentPort;
	
	public TwitchBot(String name, BotHandler handler)
	{
		this.currentMessage = "default";
		this.setName(name);
		this.setHandler(handler);
		setVerbose(true);
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		handler.processMessage(message);
		
		System.out.println(message);
		
		setCurrentMessage(handler.getTop());
	}
	
	public void exit()
	{
		setVerbose(true);
		quitServer("Because reasons.");
		dispose();
	}
	
	public void connectToChannel(String channel, boolean attemptReconnect)
	{
		setCurrentChannel(channel);
		joinChannel(channel);
		
		if(attemptReconnect) verifyConnection();
	}
	
	public void connectToServer(String server, int port, String key)
	{
		setCurrentServer(server);
		setCurrentPort(port);
		setCurrentKey(key);
		
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
		
		setVerbose(false);
	}
	
	public void verifyConnection()
	{
		boolean go = true;
		
		while(go)
		{
			//Waits a period before determining if it should try to reconnect.
			//Hackish implemenation.
			try { Thread.sleep(4000); }
			catch (Exception e) { e.printStackTrace(); }
			
			if(currentMessage.equals("default"))
			{		
				System.out.println("Disconnecting");
				disconnect();	
				
				//hackishly waits for the bot to disconnect from the IRC server
				//before attempting a reconnect.
				try { Thread.sleep(250); }
				catch (Exception e) { e.printStackTrace(); }
				
				System.out.println("Attempting to reconnect. . .");
				
				//Attempts to reconnect.
				connectToServer(getCurrentServer(), getCurrentPort(), getCurrentKey());			
				joinChannel(getCurrentChannel());
			}
			else 
			{
				System.out.println("Succesfully connected!");
				go = false;
			}
		}
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

	public String getCurrentServer()
	{
		return currentServer;
	}

	public void setCurrentServer(String currentServer)
	{
		this.currentServer = currentServer;
	}

	public String getCurrentKey()
	{
		return currentKey;
	}

	public void setCurrentKey(String currentKey)
	{
		this.currentKey = currentKey;
	}

	public int getCurrentPort()
	{
		return currentPort;
	}

	public void setCurrentPort(int currentPort)
	{
		this.currentPort = currentPort;
	}

	public String getCurrentChannel()
	{
		return currentChannel;
	}

	public void setCurrentChannel(String currentChannel)
	{
		this.currentChannel = currentChannel;
	}
}