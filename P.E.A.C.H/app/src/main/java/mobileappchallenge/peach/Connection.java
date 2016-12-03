package mobileappchallenge.peach;

/**
 * Created by jjaparicio on 4/10/16.
 */

import android.content.Context;


import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Connection extends SQLiteAssetHelper {



    private static final String DATABASE_NAME = "PEACH22.db";


    private static final int DATABASE_VERSION = 1;

    public Connection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}