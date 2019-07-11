package com.example.apliaieducation;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class EducationViewModel extends ViewModel {

    public EducationRepository educationRepository;
    public MutableLiveData<String> institute = new MutableLiveData<>();
    public MutableLiveData<String> degreeType = new MutableLiveData<>();
    public MutableLiveData<String> perType = new MutableLiveData<>();
    public MutableLiveData<String> percentage = new MutableLiveData<>();
    public MutableLiveData<String> fieldOfStudy = new MutableLiveData<>();
    public MutableLiveData<String> fromDate = new MutableLiveData<>();
    public MutableLiveData<String> toDate = new MutableLiveData<>();
    public SpinnerAdapter getDegreeAdapter;
    public SpinnerAdapter getPerTypeAdapter;
    private Context context;
    public ArrayList<String> educationList, perList;


    public void init(Context context){
        this.context = context;
        educationRepository = new EducationRepository(context);
        educationList = new ArrayList<>();
        educationList.add("SECONDARY EDUCATION");
        educationList.add("SENIOR SECONDARY");
        educationList.add("BACHELORS");
        educationList.add("MASTERS");
        educationList.add("DOCTORATE");
        getDegreeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, educationList);

        perList = new ArrayList<String>();
        perList.add("PERCENTAGE");
        perList.add("CGPA");
        perList.add("SGPA");

        getPerTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, perList);

    }
    public void save(){
        EducationDetails educationDetails = new EducationDetails();
        educationDetails.setDegreeType(degreeType.getValue());
        educationDetails.setInstituteName(institute.getValue());
        educationDetails.setFieldOfStudy(fieldOfStudy.getValue());
        educationDetails.setFromDate(fromDate.getValue());
        educationDetails.setToDate(toDate.getValue());
        educationDetails.setPercentage(percentage.getValue());
        educationDetails.setPerType(perType.getValue());
        educationRepository.save(educationDetails);
    }

    public LiveData<DataSnapshot> getDetails(){
        return educationRepository.getData();
    }
}
