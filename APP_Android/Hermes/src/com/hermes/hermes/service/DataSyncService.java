package com.hermes.hermes.service;

import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;

import com.hermes.hermes.*;

public class DataSyncService extends IntentService {
	// Used to write to the system log from this class.
    public static final String LOG_TAG = "DataSyncService";

    // Defines and instantiates an object for handling status updates.
    private BroadcastNotifier mBroadcaster = new BroadcastNotifier(this);
    
    private DataController c;
    private SessionManager session;

    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public DataSyncService() {
        super("DataSyncService");
    }

    /**
     * In an IntentService, onHandleIntent is run on a background thread.  As it
     * runs, it broadcasts its current status using the LocalBroadcastManager.
     * @param workIntent The Intent that starts the IntentService. This Intent contains the
     * URL of the web site from which the RSS parser gets data.
     */
    @Override
    protected void onHandleIntent(Intent workIntent) {
    	 c = new DataController(getApplicationContext());
    	 session = SessionManager.getInstance(getApplicationContext());

        /*
         * A block that tries to connect to the Picasa featured picture URL passed as the "data"
         * value in the incoming Intent. The block throws exceptions (see the end of the block).
         */
        try {
        	// Broadcasts an Intent indicating that processing has started.
            mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_STARTED);
            
        	if (!c.syncAllData(session))
        	{
        		// Reports that the feed retrieval is complete.
        		mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_FAILED);              
        	}
        	else{
        		// Reports that the feed retrieval has failed.
        		mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE);
        	}

        // Handles possible exceptions
        } catch (Exception ex) {
            ex.printStackTrace();
            mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_FAILED);
        } 
    }

}
