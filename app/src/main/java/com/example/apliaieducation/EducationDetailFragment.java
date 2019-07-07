package com.example.apliaieducation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.apliaieducation.databinding.EducationDetailFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class EducationDetailFragment extends Fragment {
    private Button updateDetailsButton;

    private EducationViewModel viewModel;
    private EducationDetailFragmentBinding binding;
    private Context context;
    private LiveData<DataSnapshot> detail;
    private ArrayList<String> perList;
    private ArrayList<String> educationList;
    private Spinner perTypeSpinner;
    private Spinner degreeSpinner;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(EducationViewModel.class);
        viewModel.init(context);
        detail = viewModel.getDetails();
        detail.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    EducationDetails educationDetails = dataSnapshot.child(FirebaseAuth.getInstance().getUid()).getValue(EducationDetails.class);
                    viewModel.degreeType.setValue(educationDetails.getDegreeType());
                    degreeSpinner.setSelection(Integer.parseInt(viewModel.degreeType.getValue()));
                    viewModel.institute.setValue(educationDetails.getInstituteName());
                    viewModel.perType.setValue(educationDetails.getPerType());
                    perTypeSpinner.setSelection(Integer.parseInt(viewModel.perType.getValue()));
                    viewModel.percentage.setValue(educationDetails.getPercentage());
                    viewModel.fieldOfStudy.setValue(educationDetails.getFieldOfStudy());
                    viewModel.fromDate.setValue(educationDetails.getFromDate());
                    viewModel.toDate.setValue(educationDetails.getToDate());
                }
            }
        });

        binding.setEducationviewmodel(viewModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.education_detail_fragment, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDatePickerDialog();

        perList = new ArrayList<String>();
        perList.add("PERCENTAGE");
        perList.add("CGPA");
        perList.add("SGPA");

        perTypeSpinner = view.findViewById(R.id.perTypeSpinner);
        final ArrayAdapter<String> perTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, perList);
        perTypeSpinner.setAdapter(perTypeAdapter);


        perTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viewModel.perType.postValue(Integer.toString(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        educationList = new ArrayList<>();
        educationList.add("SECONDARY EDUCATION");
        educationList.add("SENIOR SECONDARY");
        educationList.add("BACHELORS");
        educationList.add("MASTERS");
        educationList.add("DOCTORATE");

        degreeSpinner = view.findViewById(R.id.degreeTypeSpinner);
        ArrayAdapter<String> degreeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, educationList);
        degreeSpinner.setAdapter(degreeAdapter);

        degreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viewModel.degreeType.setValue(Integer.toString(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateDetailsButton = view.findViewById(R.id.updateDetails_button);

        updateDetailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateDetails();
            }
        });

    }

    private void updateDetails() {
        EducationDetails educationDetails = new EducationDetails();
        educationDetails.setDegreeType(viewModel.degreeType.getValue());
        educationDetails.setInstituteName(viewModel.institute.getValue());
        educationDetails.setFieldOfStudy(viewModel.fieldOfStudy.getValue());
        educationDetails.setFromDate(viewModel.fromDate.getValue());
        educationDetails.setToDate(viewModel.toDate.getValue());
        educationDetails.setPercentage(viewModel.percentage.getValue());
        educationDetails.setPerType(viewModel.perType.getValue());
        viewModel.save(educationDetails);
        Toast.makeText(context, "DETAILS UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
    }

    private void showDatePickerDialog() {
        TextView fromDatePickerView = null;
        TextView toDatePickerView = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            fromDatePickerView = Objects.requireNonNull(getView()).findViewById(R.id.from);
            toDatePickerView = Objects.requireNonNull(getView()).findViewById(R.id.to);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        if (view.getId() == R.id.from) {
                            viewModel.fromDate.setValue(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[month] + " " + year);
                        } else {
                            viewModel.toDate.setValue(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[month] + " " + year);
                        }
                    }
                };

                Calendar now = Calendar.getInstance();
                int year = now.get(java.util.Calendar.YEAR);
                int month = now.get(java.util.Calendar.MONTH);
                int day = now.get(java.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), onDateSetListener, year, month, day);
                }
                datePickerDialog.setTitle("Please select date.");
                datePickerDialog.show();
            }
        };

        fromDatePickerView.setOnClickListener(clickListener);
        toDatePickerView.setOnClickListener(clickListener);
    }

}
