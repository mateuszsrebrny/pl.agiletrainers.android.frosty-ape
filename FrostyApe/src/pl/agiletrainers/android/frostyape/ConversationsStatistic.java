package pl.agiletrainers.android.frostyape;
import android.text.format.*;

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

	public void setNumUnreadConversations(int numUnreadConversations) {
		this.numUnreadConversations = numUnreadConversations;
	}

	public void setNumConversations(int numConversations) {
		this.numConversations = numConversations;
	}

	public void setTime(Time time) {
		this.time = time;
	}

}
