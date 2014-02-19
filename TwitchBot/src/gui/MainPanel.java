package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Component;
import main.TwitchBot;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Component
{
	private final MainFrame root;
	private int width, height;
	
	private JTextField feidl1;
	private List<JTextField> feilds;
	
	public MainPanel(final MainFrame root)
	{
		super(null);
		this.root = root;
		this.width = root.getWidth();
		this.height = root.getHeight();
		this.feilds = new ArrayList<JTextField>();
		
		setSize(width, height);
			
		JButton button = new JButton("Exit App");
		button.setSize(new Dimension(100, 33));
		button.setLocation(20, 20);
		
		button.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				((MainFrame) root).exitButton();
			}
		});
		
		add(button);
		
		feidl1 = new JTextField("default", 25);
		feidl1.setBounds(20, 60, (int) feidl1.getPreferredSize().getWidth(), (int) feidl1.getPreferredSize().getHeight());
		feidl1.setBackground(new Color(.85f, .85f, .9f));
		
		for(int i = 0; i < root.getBot().getHandler().getCommands().length; i++)
		{
			JTextField feild = new JTextField();
			feild.setBounds(20, 85 + (25 * i), (int) feidl1.getPreferredSize().getWidth(), (int) feidl1.getPreferredSize().getHeight());
			feild.setBackground(new Color(.85f, .85f, .9f));
			
			feilds.add(feild);
			
			add(feild);
		}
		
		add(feidl1);
		root.setVisible(true);
	}
	
	public void update()
	{
		TwitchBot bot = root.getBot();
		feidl1.setText(bot.getCurrentMessage());
		
		for(int i = 0; i <bot.getHandler().getCommands().length; i++)
		{
			feilds.get(i).setText(bot.getHandler().getCommands()[i] + " " + bot.getHandler().getCommandCount().get(bot.getHandler().getCommands()[i]));
		}
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
}
