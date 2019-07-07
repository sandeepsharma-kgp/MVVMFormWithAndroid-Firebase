package com.example.apliaieducation;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = EducationDetails.class, version = 1, exportSchema = false)
public abstract class EducationDetailDatabase extends RoomDatabase {

    private static EducationDetailDatabase EducationDetailsDbInstance;

    public abstract EducationDao educationDao();

    public static synchronized EducationDetailDatabase getInstance(Context context){
        if(EducationDetailsDbInstance == null) {
            EducationDetailsDbInstance = Room.databaseBuilder(context.getApplicationContext(),
                    EducationDetailDatabase.class, "education_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return EducationDetailsDbInstance;
    }
}
