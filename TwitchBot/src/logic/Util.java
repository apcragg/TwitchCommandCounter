package logic;

import java.awt.Color;

public class Util
{
	/**
	 * Returns a linearly interpolated color between the two inputed colors based on the time factor.
	 * @param a1 First color
	 * @param a2 Second Color
	 * @param timeFactor Must be between 0.0 - 1.0
	 * @return The interpolated color
	 */
	public static Color lerp(Color a1, Color a2, float timeFactor)
	{
		if(timeFactor > 1.0f || timeFactor < 0.0f) 
		{
			timeFactor = 0.0f;
			throw new IllegalStateException("Cannot process a timestep outside the range 0.0 - 1.0. Value was: " + timeFactor); 		
		}
		
		int r = (int) (a1.getRed() + ((a2.getRed() - a1.getRed()) * timeFactor));
		int g = (int) (a1.getGreen() + ((a2.getGreen() - a1.getGreen()) * timeFactor));
		int b = (int) (a1.getBlue() + ((a2.getBlue() - a1.getBlue()) * timeFactor));
		
		return new Color(r,g,b);
	}
	
	//Test method. Should throw an illegal step exception but continue running.
	public static void test()
	{
		lerp(Color.black, Color.blue, -.5f);
	}
}
