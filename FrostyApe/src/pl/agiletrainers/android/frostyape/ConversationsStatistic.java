package pl.agiletrainers.android.frostyape;
import android.text.format.Time;

public class ConversationsStatistic
{

	private Time time;
	private int numConversations;
	private int numUnreadConversations;
    
	public ConversationsStatistic(Time time, int numConversations, int numUnreadConversations) {
		setTime(time);
		setNumConversations(numConversations);
		setNumUnreadConversations(numUnreadConversations);
	}

	public ConversationsStatistic(long milis, int numConversations, int numUnreadConversations) {
		this(null, numConversations, numUnreadConversations);
		Time time = new Time();
		time.set(milis);
		setTime(time);
	}
	
	public ConversationsStatistic(int numConversations, int numUnreadConversations) {
		this(null, numConversations, numUnreadConversations);
		Time time = new Time();
		time.setToNow();
		setTime(time);
	}

	public int getNumUnreadConversations() {
		return numUnreadConversations;
	}

	public void setNumUnreadConversations(int numUnreadConversations) {
		this.numUnreadConversations = numUnreadConversations;
	}
	
	public int getNumConversations() {
		return numConversations;
	}
	
	public void setNumConversations(int numConversations) {
		this.numConversations = numConversations;
	}

	public void setTime(Time time) {
		this.time = time;
	}
	
	public long getTimeMilis() {
		return time.toMillis(false);
	}
	
	public Time getTime() {
		return time;
	}

}
