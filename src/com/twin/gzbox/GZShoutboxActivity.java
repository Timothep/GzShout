package com.twin.gzbox;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

public class GZShoutboxActivity extends Activity {
    	  
	private ListView shoutListView ;  
	private ShoutListAdapter listAdapter ;  
	private ShoutStore messageStore;
	private AvatarCache avatarCache;
	private CredentialManager credManager;
	private ImageButton refreshButton;
	private ProgressBar progressBar;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // no title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
                
        // intialization
        credManager = new CredentialManager(this);
        avatarCache = new AvatarCache(this);
        messageStore = new ShoutStore(avatarCache);        
        shoutListView = (ListView) findViewById(R.id.lvMessages);      
        refreshButton = (ImageButton) findViewById(R.id.ibtnRefresh);
        progressBar = (ProgressBar) findViewById(R.id.progressBar); 
        
        listAdapter = new ShoutListAdapter(this, avatarCache); 
		shoutListView.setAdapter(listAdapter);	
    }
    
    public void onRefreshClicked(View v) {
    	 new ShoutUpdateTask().execute();    	 
    }
    
    public void onWriteClicked(View v) {
    	if(credManager.isUserLoggedIn()) {
    		
    	}else {
    		showSignInDialog();
    	}
    }
    
    public void onBubbleClicked(View v) {
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.geekzone.fr"));
    	startActivity(browserIntent);
    }
    
    
    /**********************/
    /*      PRIVATE       */
    /**********************/
    
	private void showSignInDialog() {
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);		     
		    LayoutInflater inflater = getLayoutInflater();
		    
		    // pass null as the parent view because it's going in the dialog layout
		    builder.setView(inflater.inflate(R.layout.signindialog, null))		    
		           .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
		               @Override
		               public void onClick(DialogInterface dialog, int id) {
		                   // sign in the user ...
		               }
		           })
		           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		                   // just let the dialog disappear
		               }
		           });      
		    builder.create().show();
	}
    
    /********************************/
    /*         TASKS                */
    /********************************/
    
    private class ShoutUpdateTask extends AsyncTask<Void, Void, List<Shout>> {
    	
    	private String errorMessage;
    	
    	@Override
    	protected void onPreExecute() {
    		refreshButton.setVisibility(View.INVISIBLE);
	    	progressBar.setVisibility(View.VISIBLE);
    	}
    	
		@Override
		protected List<Shout> doInBackground(Void... params) {
	    	
			List<Shout> result;
	        try {
				messageStore.updateMessages();
				result = messageStore.getShoutList();								
			} catch (Exception e) {
				result = null;
				errorMessage = e.getMessage(); 				
			}
			return result;
		} 
		
		@Override
    	protected void onPostExecute(List<Shout> list) {			   
	    	
	    	if(list != null) {
	    		listAdapter.updateShoutList(list);
	    		shoutListView.smoothScrollToPosition(0);
	    	}else {	    		
	    		new AlertDialog.Builder(GZShoutboxActivity.this)
	    		.setTitle("Shoutbox update failure ")
	    		.setMessage("Could not load GZ shoutbox content.\n\n" + errorMessage)
	    		.show();
	    	}
	    	
	    	refreshButton.setVisibility(View.VISIBLE);
	    	progressBar.setVisibility(View.INVISIBLE);
    	}
    }


}