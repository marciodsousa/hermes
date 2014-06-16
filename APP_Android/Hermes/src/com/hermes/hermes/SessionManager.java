package com.hermes.hermes;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
	// Shared pref file name
	private static final String PREF_NAME = "HermesPref";
	 
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	     
	// Userid (make variable public to access from outside)
	public static final String KEY_USERID = "idUser";
	
	// Username (make variable public to access from outside)
	public static final String KEY_USERNAME = "username";
	
	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";
	
	// Licence code (make variable public to access from outside)
	public static final String KEY_CODLIC = "codLicence";
	 
//	// Email address (make variable public to access from outside)
//	public static final String KEY_EMAIL = "email";
//	    
//	// User password (make variable public to access from outside)
//	public static final String KEY_PASSWORD = "password";
//	
//	// User password SALT (make variable public to access from outside)
//	public static final String KEY_PASSWORDSLT = "passwordSalt";
//	
//	// User status (make variable public to access from outside)
//	public static final String KEY_STATUS = "estado";
//
//	// Phone Serial Number (make variable public to access from outside)
//	public static final String KEY_SERIAL = "numSerieEquip";
//	    
//	// CompanyID (make variable public to access from outside)
//	public static final String KEY_COMPANYID = "idEmpresa";
//	    
//	// UserTypeID (make variable public to access from outside)
//	public static final String KEY_USERTYPE = "idTipoUtilizador";
     
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void createLoginSession(String id, String username, String name, String codLic ){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing userid in pref
        editor.putString(KEY_USERID, id);
        
        // Storing username in pref
        editor.putString(KEY_USERNAME, username);
        
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        
        // Storing licence code in pref
        editor.putString(KEY_CODLIC, codLic);
         
//        // Storing email in pref
//        editor.putString(KEY_EMAIL, email);
//        
//        // Storing password in pref
//        editor.putString(KEY_PASSWORD, password);
//        
//        // Storing passwordSalt in pref
//        editor.putString(KEY_PASSWORDSLT, passSalt);
//         
//        // Storing status in pref
//        editor.putString(KEY_STATUS, status);
//        
//        // Storing phone SerialNumber in pref
//        editor.putString(KEY_SERIAL, serial);
//        
//        // Storing user typeid in pref
//        editor.putString(KEY_COMPANYID, usrtype);
//         
//        // Storing companyid in pref
//        editor.putString(KEY_USERTYPE, companyid);
         
        // commit changes
        editor.commit();
    }   
     
    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            _context.startActivity(i);
        }
         
    }
     
     
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        
        // user name
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        
        // user name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
         
        // user email id
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        
        // licence code
        user.put(KEY_CODLIC, pref.getString(KEY_CODLIC, null));
        
//        // user name
//        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
//        
//        // user name
//        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
//         
//        // user email id
//        user.put(KEY_PASSWORDSLT, pref.getString(KEY_PASSWORDSLT, null));
//        
//        // user name
//        user.put(KEY_STATUS, pref.getString(KEY_STATUS, null));
//        
//        // user name
//        user.put(KEY_SERIAL, pref.getString(KEY_SERIAL, null));
//         
//        // user email id
//        user.put(KEY_COMPANYID, pref.getString(KEY_COMPANYID, null));
//        
//        // user email id
//        user.put(KEY_USERTYPE, pref.getString(KEY_USERTYPE, null));
         
        // return user
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