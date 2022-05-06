package com.example.roomtestdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class}, version=1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {


    //initialise instance of Database

    private static RoomDB database;

    //initialise database name
    private static String DATABASE_NAME ="database";


    public synchronized static RoomDB getInstance(Context context){

        if (database==null)
        {
            database= Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  database;
    }

    public abstract MainDao mainDao();
}
