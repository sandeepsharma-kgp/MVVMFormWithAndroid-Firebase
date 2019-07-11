package com.example.apliaieducation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detail = viewModel.getDetails();
        detail.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    EducationDetails educationDetails = dataSnapshot.child(FirebaseAuth.getInstance().getUid()).getValue(EducationDetails.class);
                    binding.degreeTypeSpinner.setSelection(Integer.parseInt(viewModel.degreeType.getValue()));
                    viewModel.institute.setValue(educationDetails.getInstituteName());
                    binding.perTypeSpinner.setSelection(Integer.parseInt(viewModel.perType.getValue()));
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
        viewModel = ViewModelProviders.of(this).get(EducationViewModel.class);
        viewModel.init(context);
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

        binding.perTypeSpinner.setAdapter(viewModel.getPerTypeAdapter);
        binding.perTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.perType.postValue(Integer.toString(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.degreeTypeSpinner.setAdapter(viewModel.getDegreeAdapter);

        binding.degreeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viewModel.degreeType.setValue(Integer.toString(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.updateDetailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateDetails();
            }
        });

    }

    private void updateDetails() {
        viewModel.save();
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
