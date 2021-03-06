package com.hermes.hermes.service;

import android.app.IntentService;
import android.content.Intent;

import com.hermes.hermes.controller.ClienteController;

public class ClientImportService extends IntentService {
	// Used to write to the system log from this class.
    public static final String LOG_TAG = "CompanyImportService";

    // Defines and instantiates an object for handling status updates.
    private BroadcastNotifier mBroadcaster = new BroadcastNotifier(this);
    
    private ClienteController c;

    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public ClientImportService() {
        super("CompanyImportService");
    }

    /**
     * In an IntentService, onHandleIntent is run on a background thread.  As it
     * runs, it broadcasts its current status using the LocalBroadcastManager.
     * @param workIntent The Intent that starts the IntentService. This Intent contains the
     * URL of the web site from which the RSS parser gets data.
     */
    @Override
    protected void onHandleIntent(Intent workIntent) {
    	 c = new ClienteController(getApplicationContext());

        try {
        	// Broadcasts an Intent indicating that processing has started.
            mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_STARTED);
            
        	if (!c.ImportClientes())
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
