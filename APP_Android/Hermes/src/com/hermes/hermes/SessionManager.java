package com.hermes.hermes;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
	 
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	     
	// Userid (make variable public to access from outside)
	public static final String KEY_USERID = "idUser";
	
	// server address (make variable public to access from outside)
	public static final String KEY_SERVER = "serverAddress";
	
	// Licence code (make variable public to access from outside)
	public static final String KEY_CODLIC = "codLicence";
	 
     
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void createLoginSession(String id, String server, String codLic ){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing userid in pref
        editor.putString(KEY_USERID, id);
        
        // Storing server address in pref
        editor.putString(KEY_SERVER, server);
        
        // Storing licence code in pref
        editor.putString(KEY_CODLIC, codLic);
   
         
        // commit changes
        editor.commit();
    }   
     
    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean checkLogin(){
        // Check login status
    	boolean ret=true;
        if(!this.isLoggedIn())
        	ret = false;
         
         return ret;
    }
     
     
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        
        // user id
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        
        // server address
        user.put(KEY_SERVER, pref.getString(KEY_SERVER, null));
        
        // licence code
        user.put(KEY_CODLIC, pref.getString(KEY_CODLIC, null));
        
        return user;
    }
     
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        _context.startActivity(i);
    }
     
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}