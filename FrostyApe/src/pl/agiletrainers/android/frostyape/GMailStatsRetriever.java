package pl.agiletrainers.android.frostyape;
import android.content.*;
import android.accounts.*;
import android.database.*;
import android.net.*;
import android.text.format.*;


public class GMailStatsRetriever {

   public GMailStatsRetriever() {}

   final String ACCOUNT_TYPE_GOOGLE = "com.google";
   
   public ConversationsStatistic retrieve(Context context) {
	
	   AccountManager accountManager = AccountManager.get(context);
	   final Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE_GOOGLE);
	   String accountName = accounts[0].name;
	   //logOnTextView("Account: " + accountName);



	   Cursor labelsCursor = null;
	  
	   String labelsUriString = "content://" + "com.google.android.gm/" + accountName + "/labels";
	   Uri labelsUri = Uri.parse(labelsUriString);

	   ContentResolver contentResolver = context.getContentResolver();
	   labelsCursor = contentResolver.query(labelsUri, null, null, null, null);
	   //logOnTextView("labelsCursor: " + labelsCursor);

	   final int canonicalNameIndex = labelsCursor.getColumnIndexOrThrow("canonicalName");
	   final int numConversationsIndex = labelsCursor.getColumnIndexOrThrow("numConversations");
	   final int numUnreadConversationsIndex = labelsCursor.getColumnIndexOrThrow("numUnreadConversations");
	
	   while (labelsCursor.moveToNext()) {
		   String labelName = labelsCursor.getString(canonicalNameIndex);
		   if (labelName.equals("^i")) {
			   int numConversations = labelsCursor.getInt(numConversationsIndex);
			   int numUnreadConversations = labelsCursor.getInt(numUnreadConversationsIndex);
		       return new ConversationsStatistic(numConversations, numUnreadConversations);
		   }
	   }
	
	   return null;
   }
   
   
}
