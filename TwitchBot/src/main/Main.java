package main;

import gui.MainFrame;

import java.util.Scanner;

public class Main
{
	public static void main(String vmArgs[])
	{		
		TwitchBot bot = new TwitchBot("apcragg", new BotHandler());	
		
		final MainFrame frame = new MainFrame("Twitch Listener", 480, 360, bot);
				
		bot.connectToServer("199.9.252.26", 6667, "oauth:l2as7dnczuhpxurm0ltlmz0thydsxwp");	
		bot.joinChannel("#twitchplayspokemon");
		bot.testConnection();

		Scanner scanner = new Scanner(System.in);
		
		Thread thread = new Thread(new Runnable()
		{		
			@Override
			public void run()
			{
				frame.loop();
			}
		});
		
		thread.run();
		
		while (!scanner.nextLine().contains("quit"));
		
		scanner.close();		
		bot.exit();		
		frame.dispose();
	}
}
