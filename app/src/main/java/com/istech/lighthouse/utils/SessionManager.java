package com.istech.lighthouse.utils;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.istech.lighthouse.model.login.LoginModel;

import io.reactivex.disposables.CompositeDisposable;
public class SessionManager {

    private CompositeDisposable disposable = new CompositeDisposable();
    private SharedPreferences pref, isFirstPref;
    private SharedPreferences.Editor editor, isFirstEdidtor;
    String PREF_NAME = "My_Pref";
    private String isFirst = "isFirst";
    private String isSeen = "isSeen";
    private String USER = "USER";
    private String LatLng = "LatLng";
    String IS_LOGIN = "IS_LOGIN";
    String RAZOR_PAY_KEY = "RAZORPAYkEY";

    public SessionManager(Context context) {
        this.pref = context.getSharedPreferences( PREF_NAME, MODE_PRIVATE );
        this.isFirstPref = context.getSharedPreferences( isFirst, MODE_PRIVATE );
        this.editor = this.pref.edit();
        this.isFirstEdidtor = this.isFirstPref.edit();

    }

    public void saveRazorPayKey(String value) {
        editor.putString(RAZOR_PAY_KEY, value).apply();
    }

    public void deleteRazorPayKey() {
        editor.remove(RAZOR_PAY_KEY).apply();
    }

    public String getRazorPayKey() {
        return pref.getString(RAZOR_PAY_KEY, "rzp_test_wwow1QUddYXzwr");
    }



    public void saveLogin(Boolean value) {
        editor.putBoolean( IS_LOGIN, value );
        editor.apply();
    }

    public Boolean getLogin() {
        return pref.getBoolean( IS_LOGIN, false );
    }

    public void saveUser(LoginModel user) {
        editor.putString( USER, new Gson().toJson( user ) );
        editor.apply();
    }

    public LoginModel getUser() {
        String userString = pref.getString( USER, "" );
        if (!userString.isEmpty()) {
            return new Gson().fromJson( userString, LoginModel.class );
        }
        return null;
    }

    public static String checkEmptyOrNull(String str) {
        if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
            return "";
        } else return str + " ";
    }

    public void saveFirst(Boolean value) {
        isFirstEdidtor.putBoolean( isFirst, value );
        isFirstEdidtor.apply();
    }

    public Boolean getFirst() {
        return isFirstPref.getBoolean( isFirst, false );
    }

    public void clear() {
        try {
            editor.clear().apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveLogin( false );

    }
}
