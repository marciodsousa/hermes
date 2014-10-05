package com.hermes.hermes;

import java.util.HashMap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.hermes.hermes.Model.*;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mServer;
	private String mUsername;
	private String mPassword;


	// UI references.
	private EditText mServerView;
	private EditText mUsernameView;
	private EditText mPasswordView;

	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	// Session reference
	private SessionManager session;

	private int loginStatus = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mServerView = (EditText) findViewById(R.id.server);
		mServerView.setText(mServer);
		
		mUsernameView = (EditText) findViewById(R.id.username);
		mUsernameView.setText(mUsername);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUsernameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mServer = mServerView.getText().toString();
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid server Address.
		if (TextUtils.isEmpty(mServer)) {
			mServerView.setError(getString(R.string.error_field_required));
			focusView = mServerView;
			cancel = true;
		} else if (mServer.contains(" ")) {
			mServerView.setError(getString(R.string.error_invalid_server));
			focusView = mServerView;
			cancel = true;
		}
		
		// Check for a valid username.
		if (TextUtils.isEmpty(mUsername)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (mUsername.contains(" ")) {
			mUsernameView.setError(getString(R.string.error_invalid_username));
			focusView = mUsernameView;
			cancel = true;
		}
				
		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 6) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			mServer = "http://wvm100.dei.isep.ipp.pt/hermesclientWS";
			mUsername = "admin";
			mPassword = "hermesadmin";
			
			
			session = SessionManager.getInstance(getApplicationContext());
			
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

			// TODO: attempt authentication against a network service.

			DataController c = new DataController(getApplicationContext());
			
			//calls registerDevice method which returns the licence code
			String codLic = c.registerDevice(mServer, telephonyManager.getDeviceId());
			
			// Server Doesnt Exist
			if (codLic.compareTo("404")==0)
			{
				loginStatus = -1;
				return false;
			}
			
			// Users number is maxxed out
			if (codLic.compareTo("409")==0)
			{
				loginStatus = -4;
				return false;
			}	
			
			// test other errors
			if (codLic.compareTo("500")==0 || codLic.compareTo("400")==0)
			{
				loginStatus = -5;
				return false;
			}	
			
			//calls login method which returns userID
			int usrId = c.loginServer(mServer, mUsername, mPassword);

			// if user does not exist
			if (usrId == 0) {
				loginStatus = -2;
				return false;
				// if password was correct
			} else if (usrId == -1) {
				loginStatus = -3;
				return false;
			}
			
			session.createLoginSession(usrId+"", mServer, codLic);
			
			//call method to fetch data from server before finishing activity
			c.syncAllData();
			
			loginStatus = 0;
			return true;
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			DataController c = new DataController(getApplicationContext());
			
			showProgress(false);

			if (success) {
				finish();
			} else {
				switch (loginStatus) {
		            case -1:  
		            	mServerView.setError(getString(R.string.error_invalid_server));
		            	mServerView.requestFocus();
	            			break;
	            			
		            case -2:  
	            		mUsernameView.setError(getString(R.string.error_invalid_username));
	            		mUsernameView.requestFocus();
		                    break;
	            			
		            case -3:  
						mPasswordView.setError(getString(R.string.error_incorrect_password));
						mPasswordView.requestFocus();
						    break;
					
		            case -4:  
		            	mUsernameView.setError(getString(R.string.error_max_users_reached));
		            	mUsernameView.requestFocus();
		                    break;
		                    
		            case -5:  
		            	mUsernameView.setError(getString(R.string.error_error_registering_device));
		            	mUsernameView.requestFocus();
		                    break;
				}
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
