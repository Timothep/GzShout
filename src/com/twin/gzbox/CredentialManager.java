package com.twin.gzbox;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

public class CredentialManager {

	private final static String PREF_NAME = "credentials";
	private final static String AUTH_COOKIES = "cookies";
	
	private Context context;
	private List<String> cookies;
	private String username;
	
	
	public CredentialManager(Context context) {
		this.context = context;
	}
	
	public boolean isUserLoggedIn() {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return prefs.getString(AUTH_COOKIES, null) != null;
	}
	
	public void signIn(String username, String password) {
		
	}
	
	
	
}
