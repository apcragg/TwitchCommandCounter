package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import logic.Component;
import main.TwitchBot;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Component
{
	private final MainFrame root;
	private int width, height;
	
	private JTextField field1;
	private JTextField field2;
	private List<JTextField> fields;
	private List<JTextArea> areas;
	
	public MainPanel(final MainFrame root)
	{
		super(null);
		this.root = root;
		this.width = root.getWidth();
		this.height = root.getHeight();
		this.fields = new ArrayList<JTextField>();
		this.areas = new ArrayList<JTextArea>();
		
		setSize(width, height);
			
		JButton exit = new JButton("Exit App");
		exit.setSize(new Dimension(100, 33));
		exit.setLocation(20, 20);
		
		exit.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				((MainFrame) root).exitButton();
			}
		});
		
		JButton update = new JButton("Update");
		update.setSize(new Dimension(100, 33));
		update.setLocation(130, 20);
		
		update.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				update();
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		final double[] choices = {.1d, .25d, .5d, 1d, 2d, 5d, 10d, 30d};
		
		for(int i = 0; i < 8; i++)
		{
			JRadioButton button = new JRadioButton();
			
			if(i == 0) button.setSelected(true);
			final double d = choices[i];
			
			button.setAction(new AbstractAction()		
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					root.setDelay(d);
				}			
			});
				
			button.setText(choices[i] + " seconds");
			button.setBackground(new Color(.85f, .85f, .9f));		
			button.setBounds(320, 85 + (i * 30), button.getPreferredSize().width, button.getPreferredSize().height);					
			
			group.add(button);
			add(button);
		}	
	
		add(exit);
		add(update);
		
		field1 = new JTextField("default", 20);
		field1.setBounds(20, 60, (int) field1.getPreferredSize().getWidth(), (int) field1.getPreferredSize().getHeight());
		field1.setBackground(new Color(.85f, .85f, .9f));
		
		field2 = new JTextField("  Update Delay");
		field2.setBounds(320, 60, (int) field2.getPreferredSize().getWidth() + 10, (int) field2.getPreferredSize().getHeight());
		field2.setBackground(new Color(.85f, .85f, .9f));
		field2.setEditable(false);
		field2.setBorder(BorderFactory.createEmptyBorder());
		
		for(int i = 0; i < root.getBot().getHandler().getCommands().length; i++)
		{
			JTextField field = new JTextField(15);
			field.setBounds(110, 85 + (25 * i), (int) field.getPreferredSize().getWidth(), (int) field.getPreferredSize().getHeight());
			field.setBackground(new Color(.85f, .85f, .9f));
			
			fields.add(field);
			
			JTextField label = new JTextField(root.getBot().getHandler().getCommands()[i], 16);
			label.setBounds(20, 85 + (25 * i), 80, (int) field.getPreferredSize().getHeight());
			label.setBackground(new Color(.85f, .85f, .9f));
			
			add(field);
			add(label);
		}
		
		add(field1);
		add(field2);
		root.setVisible(true);
	}
	
	public void update()
	{		
		TwitchBot bot = root.getBot();
		field1.setText(bot.getCurrentMessage());
		
		for(int i = 0; i <bot.getHandler().getCommands().length; i++)
		{
			int value = bot.getHandler().getCommandCount().get(bot.getHandler().getCommands()[i]);
			fields.get(i).setText(Integer.toString(value));
			fields.get(i).setBackground(logic.Util.lerp(Color.GREEN, Color.red, value / (float) bot.getHandler().getTotalVotes()));
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
