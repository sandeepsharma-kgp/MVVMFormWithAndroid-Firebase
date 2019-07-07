package com.example.apliaieducation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "EducationDetails")
public class EducationDetails implements Parcelable {

    @NonNull
    @PrimaryKey
    private String id;
    private String degreeType;
    private String fieldOfStudy;
    private String fromDate;
    private String toDate;
    private String instituteName;
    private String percentage;
    private String perType;

    public EducationDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    protected EducationDetails(Parcel in) {
        this.id = in.readString();
        this.degreeType = in.readString();
        this.fieldOfStudy = in.readString();
        this.fromDate = in.readString();
        this.toDate = in.readString();
        this.instituteName = in.readString();
        this.percentage = in.readString();
        this.perType = in.readString();
    }

    public static final Creator<EducationDetails> CREATOR = new Creator<EducationDetails>() {
        @Override
        public EducationDetails createFromParcel(Parcel in) {
            return new EducationDetails(in);
        }

        @Override
        public EducationDetails[] newArray(int size) {
            return new EducationDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.degreeType);
        parcel.writeString(this.fieldOfStudy);
        parcel.writeString(this.fromDate);
        parcel.writeString(this.toDate);
        parcel.writeString(this.instituteName);
        parcel.writeString(this.percentage);
        parcel.writeString(this.perType);
    }
}
