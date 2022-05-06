package com.example.roomtestdb;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    //Insert to DB
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //Delete single User
    @Delete
    void delete(MainData mainData);

    //dDelete All Users
    @Delete
    void reset(List<MainData> mainData);

    //Update User
    @Query("UPDATE table_name SET usename = :suserName WHERE ID = :sID")
    void update(int sID, String suserName);

    //get all Users
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}
