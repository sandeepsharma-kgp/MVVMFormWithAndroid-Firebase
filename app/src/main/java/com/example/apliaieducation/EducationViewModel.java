package com.example.apliaieducation;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;

public class EducationViewModel extends ViewModel {

    public EducationRepository educationRepository;
    public MutableLiveData<String> institute = new MutableLiveData<>();
    public MutableLiveData<String> degreeType = new MutableLiveData<>();
    public MutableLiveData<String> perType = new MutableLiveData<>();
    public MutableLiveData<String> percentage = new MutableLiveData<>();
    public MutableLiveData<String> fieldOfStudy = new MutableLiveData<>();
    public MutableLiveData<String> fromDate = new MutableLiveData<>();
    public MutableLiveData<String> toDate = new MutableLiveData<>();
    public DataSnapshot educationDetailsLiveData;
    private Context context;

    public void init(Context context){
        this.context = context;
        educationRepository = new EducationRepository(context);
    }
    public void save(EducationDetails educationDetails){
        educationRepository.save(educationDetails);
    }

    public LiveData<DataSnapshot> getDetails(){
        return educationRepository.getData();
    }
}
