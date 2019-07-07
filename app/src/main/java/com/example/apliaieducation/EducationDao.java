package com.example.apliaieducation;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EducationDao {
    @Insert
    void insert(EducationDetails educationDetails);

    @Update
    void update(EducationDetails educationDetails);

    @Delete
    void delete(EducationDetails educationDetails);

    @Query("SELECT * FROM EducationDetails")
    LiveData<List<EducationDetails>> getDetail();
}
