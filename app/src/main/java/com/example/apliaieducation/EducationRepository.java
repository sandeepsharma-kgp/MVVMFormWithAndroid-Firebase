package com.example.apliaieducation;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class EducationRepository {

    private LiveData<DataSnapshot> dataSnapshot = null;
    private EducationDao educationDetailDao;
    private final FirebaseQueryLiveData liveData;

    public EducationRepository(Context context) {
        EducationDetailDatabase db = EducationDetailDatabase.getInstance(context);
        educationDetailDao = db.educationDao();
        FirebaseUtil.openFbReference("Education", (Activity) context);
        liveData = new FirebaseQueryLiveData(FirebaseUtil.databaseReference);
    }

    public LiveData<DataSnapshot> getData(){
        return liveData;
    }

    public void save(EducationDetails educationDetails) {
        FirebaseUtil.databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(educationDetails);
    }
}
