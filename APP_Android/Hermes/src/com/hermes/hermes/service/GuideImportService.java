package com.hermes.hermes.service;

import android.app.IntentService;
import android.content.Intent;

import com.hermes.hermes.controller.GuiaTransporteController;

public class GuideImportService extends IntentService {
	// Used to write to the system log from this class.
    public static final String LOG_TAG = "GuideImportService";

    // Defines and instantiates an object for handling status updates.
    private BroadcastNotifier mBroadcaster = new BroadcastNotifier(this);
    
    private GuiaTransporteController c;

    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public GuideImportService() {
        super("GuideImportService");
    }

    /**
     * In an IntentService, onHandleIntent is run on a background thread.  As it
     * runs, it broadcasts its current status using the LocalBroadcastManager.
     * @param workIntent The Intent that starts the IntentService. This Intent contains the
     * URL of the web site from which the RSS parser gets data.
     */
    @Override
    protected void onHandleIntent(Intent workIntent) {
    	 c = new GuiaTransporteController(getApplicationContext());

        try {
        	// Broadcasts an Intent indicating that processing has started.
            mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_STARTED);
            
        	if (!c.ImportGuias())
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
