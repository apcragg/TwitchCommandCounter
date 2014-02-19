package gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import logic.Component;
import main.TwitchBot;

@SuppressWarnings("serial")
public class MainFrame extends JFrame 
{
	private List<Component> components;
	
	private String name;
	private TwitchBot bot;
	private int width, height;
	private double delay; //in seconds;

	public MainFrame(String name, int width, int height, TwitchBot bot)
	{
		super(name);
		
		this.components = new ArrayList<Component>();
		
		this.name = name;
		this.width = width;
		this.height = height;
		this.bot = bot;
		this.delay = 1f;

		getContentPane().setPreferredSize(new Dimension(width, height));
	
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setAlwaysOnTop(true);
		setResizable(true);
		setFocusable(true);
		setLocationRelativeTo(null);
		setLayout(null);	
		
		components.add(new MainPanel(this));
		
		for(Component component : components)
			add((java.awt.Component) component);

		setVisible(true);
		pack();
	}
	
	public void loop()
	{
		long startTime = System.nanoTime();
		long currentTime = startTime;
		long elapsedTime = 0;
		
		while(true)
		{
			currentTime = System.nanoTime();
			elapsedTime = currentTime - startTime;
			
			if(elapsedTime / 1E9d > getDelay())
			{
				startTime = System.nanoTime();
				
				for(Component component : components)
					component.update();
			}
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String newName)
	{
		name = newName;

		this.setName(newName);
	}

	public TwitchBot getBot()
	{
		return bot;
	}

	public void setBot(TwitchBot bot)
	{
		this.bot = bot;
	}
	
	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void setDelay(double d)
	{
		this.delay = d;
	}
	
	public double getDelay()
	{
		return delay;
	}

	@Override
	/**
	 * Overridden from JFrame. Exits the bot from the server and performs cleanup before exiting
	 * the program.
	 */
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);

		if (e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			switch (getDefaultCloseOperation())
			{
				case HIDE_ON_CLOSE:
					setVisible(false);
					break;
				case DISPOSE_ON_CLOSE:
					bot.exit();
					dispose();
					break;
				case DO_NOTHING_ON_CLOSE:
				default:
					break;
				case EXIT_ON_CLOSE:
					System.out.println("UHM");
					bot.exit();
					System.exit(0);
					break;
			}
		}
	}
	
	public void exitButton()
	{
		processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
