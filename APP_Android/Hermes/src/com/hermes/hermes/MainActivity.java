package com.hermes.hermes;

import com.hermes.hermes.service.*;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    /**
     * Options menu used to populate ActionBar.
     */
    private Menu mOptionsMenu;
    
    private SyncStateReceiver mSyncStateReceiver;
    
    private Context mActivityContext;
    
 // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityContext = getApplicationContext();
        
        // Session Manager
        session = new SessionManager(getApplicationContext()); 
        
        //check if user is Logged in. If not, redirect to Login Activity
        boolean isLogged = session.checkLogin();
        
        if (!isLogged)
        {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            getApplicationContext().startActivity(i);
        }
        

        
        
        
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        /*
         * Creates an intent filter for DownloadStateReceiver that intercepts broadcast Intents
         */
        
        
        // The filter's action is BROADCAST_ACTION
        IntentFilter statusIntentFilter = new IntentFilter(
                Constants.BROADCAST_ACTION);
        
        // Sets the filter's category to DEFAULT
        statusIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        
        // Instantiates a new DownloadStateReceiver
        mSyncStateReceiver = new SyncStateReceiver();
        
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
        		mSyncStateReceiver,
                statusIntentFilter);
        
    }

    private boolean userAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        
        
        switch (position) {
        case 0:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, GuidesFragment.newInstance(position + 1))
            .commit();
            break;
        case 1:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, ProductsFragment.newInstance(position + 1))
            .commit();
            break;
        case 2:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, ClientsFragment.newInstance(position + 1))
            .commit();
            break;
        case 3:
        	fragmentManager.beginTransaction()
            .replace(R.id.container, PlacesFragment.newInstance(position + 1))
            .commit();
            break;
            
        }       	
            
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	mOptionsMenu = menu;
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	
        }
        
        switch (item.getItemId()) {
        // If the user clicks the "Refresh" button.
	        case R.id.action_settings:
	        	
	            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
	            // Closing all the Activities
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	             
	            // Add new Flag to start new Activity
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             
	            // Staring Login Activity
	            getApplicationContext().startActivity(i);
	            return true;
	            
	        case R.id.menu_sync:

	        	Intent mServiceIntent = new Intent(this, DataSyncService.class);
	        	mServiceIntent.setClass(mActivityContext, DataSyncService.class);
	        	// Starts the IntentService
	        	this.startService(mServiceIntent);
	        	
	            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
/*    *//**
     * Set the state of the Refresh button. If a sync is active, turn on the ProgressBar widget.
     * Otherwise, turn it off.
     *
     * @param refreshing True if an active sync is occuring, false otherwise
     *//*
    public void setRefreshActionButtonState(boolean refreshing) {
        if (mOptionsMenu == null) {
            return;
        }

        final MenuItem refreshItem = mOptionsMenu.findItem(R.id.menu_refresh);
        if (refreshItem != null) {
            if (refreshing) {
            	refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
            } else {
                refreshItem.setActionView(null);
            }
        }
    }*/
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This class uses the BroadcastReceiver framework to detect and handle status messages from
     * the service that downloads URLs.
     */
    private class SyncStateReceiver extends BroadcastReceiver {
    	
    	
        private SyncStateReceiver() {
            
            // prevents instantiation by other packages.
        }
        /**
         *
         * This method is called by the system when a broadcast Intent is matched by this class'
         * intent filters
         *
         * @param context An Android context
         * @param intent The incoming broadcast Intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
        	final MenuItem refreshItem = mOptionsMenu.findItem(R.id.menu_sync);
        	
            /*
             * Gets the status from the Intent's extended data, and chooses the appropriate action
             */
            switch (intent.getIntExtra(Constants.EXTENDED_DATA_STATUS,
                    Constants.STATE_ACTION_COMPLETE)) {
                
                // Logs "started" state
                case Constants.STATE_ACTION_STARTED:
                	refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                    break;
                // Stops the loading object when sync is complete
                case Constants.STATE_ACTION_COMPLETE:
                	refreshItem.setActionView(null);
                    break;
                 // Starts displaying data when the sync process has failed
                case Constants.STATE_ACTION_FAILED:
                	refreshItem.setActionView(null);
                	Toast.makeText(getApplicationContext(), R.string.error_sync, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }
    
}
