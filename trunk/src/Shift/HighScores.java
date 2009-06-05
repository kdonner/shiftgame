package Shift;

import java.util.ArrayList;
import java.util.Collections;

public class HighScores implements java.io.Serializable
{
	private static final int MAX_SIZE = 100;
	private static final String NO_TIME = "--:--:---";
	private static final int MAX_RESULTS = 10;
	private static final long serialVersionUID = 3627550956646622212L;
	
	class Score implements java.io.Serializable, Comparable<Score>
	{
		private static final long serialVersionUID = 3627550956646622212L;
		long time; // in milliseconds
		String format; // time formatted
		String name;
		
		public Score(long time, String format, String name)
		{
			this.time = time;
			this.format = format;
			this.name = name;
		}
		
		public int compareTo(Score other)
		{
			return ((Long)this.time).compareTo(other.time);
		}
	}
	
	ArrayList<Score> scores;
	
	public HighScores()
	{
		scores = new ArrayList<Score>();
	}
	
	public void addScore(long time, String format, String name)
	{
		scores.add(new Score(time, format, name));
		Collections.sort(scores);
		if(scores.size() > MAX_SIZE)
		{
			scores.remove(scores.size()-1);
		}
	}
	
	public ArrayList<String> getTopScores()
	{
		ArrayList<String> topScores = new ArrayList<String>();
		for(int i = 0; i < scores.size() && i < MAX_RESULTS; i++)
		{
			topScores.add(scores.get(i).name + ": " + scores.get(i).format);
		}
		for(int i = 0; i < MAX_RESULTS - scores.size(); i++)
		{
			topScores.add(NO_TIME);
		}
		return topScores;
	}
	
	public String formatScores()
	{
		Constants.clearStringBuilder();
		for(int i = 0; i < scores.size() && i < MAX_RESULTS; i++)
		{
			Constants.strBuild.append(scores.get(i).name);
			Constants.strBuild.append(": ");
			Constants.strBuild.append(scores.get(i).format);
			Constants.strBuild.append("\n");
		}
		for(int i = 0; i < MAX_RESULTS - scores.size(); i++)
		{
			Constants.strBuild.append(NO_TIME);
			Constants.strBuild.append("\n");
		}
		return Constants.strBuild.toString();
	}
	
	
}
