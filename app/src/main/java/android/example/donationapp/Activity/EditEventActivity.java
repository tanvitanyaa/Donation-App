package android.example.donationapp.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.example.donationapp.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Locale;

public class EditEventActivity extends AppCompatActivity {

    TextInputLayout eventTitleBox, descriptionBox, timeBox, dateBox, addressBox, contactBox, emailBox;
    TextInputEditText eventTitleEdit, eventdescriptionEdit, eventAddressEdit, eventContactEdit, eventEmailEdit;
    TextView timeEdit, dateEdit;
    Button save;
    ImageView backbutton, imageedit;
    String title , description, time, date , address ,email , contact;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    DocumentReference documentReference ;
    StorageReference storageReference;
    int hour;
    int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        eventTitleBox = findViewById(R.id.editEvent_edit_box);
        descriptionBox = findViewById(R.id.editEvent_description_box);
        timeBox = findViewById(R.id.editEvent_time_box);
        dateBox = findViewById(R.id.editEvent_date_box);
        addressBox = findViewById(R.id.editEvent_address_box);
        contactBox = findViewById(R.id.editEvent_contact_box);
        emailBox = findViewById(R.id.editEvent_email_box);

        eventTitleEdit = findViewById(R.id.editEvent_edit);
        eventdescriptionEdit = findViewById(R.id.editEvent_description_edit);
        eventAddressEdit = findViewById(R.id.editEvent_address_edit);
        eventContactEdit = findViewById(R.id.editEvent_contact_edit);
        eventEmailEdit = findViewById(R.id.editEvent_email_edit);

        timeEdit = findViewById(R.id.editEvent_time_edit);
        dateEdit = findViewById(R.id.editEvent_date_edit);
        save = findViewById(R.id.editEvent_save);
        backbutton = findViewById(R.id.editEvent_back);
        imageedit = findViewById(R.id.editEvent_profile_pic_add);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        documentReference = firebaseFirestore.collection("Events").document(currentUser.getUid());
        storageReference = firebaseStorage.getReference().child("images").child(currentUser.getUid());

        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfmonth) {
                        dateEdit.setText(dayOfmonth+ " / "+ (month+1)+ " /"+ year);

                    }
                },year, month, day);
                datePickerDialog.show();
            }


        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    title = eventTitleEdit.getText().toString();
                    description = eventdescriptionEdit.getText().toString();
                    time = timeEdit.getText().toString();
                    address = dateEdit.getText().toString();
                    contact = eventContactEdit.getText().toString();
                    email = eventEmailEdit.getText().toString();


                if(title.isEmpty())
                {
                    eventTitleEdit.setError("Enter Event Title");
                }

                if(description.isEmpty())
                {
                    eventdescriptionEdit.setError("Enter Event Description");
                }

                if(time.isEmpty())
                {
                    timeEdit.setError("Select Time");
                }

                if(date.isEmpty())
                {
                    dateEdit.setError("Select Date");
                }

                if(address.isEmpty())
                {
                    eventAddressEdit.setError("Enter Event Address");
                }

                if(contact.isEmpty())
                {
                    eventContactEdit.setError("Enter Contact Number");
                }

                if(email.isEmpty())
                {
                    eventEmailEdit.setError("Enter Email");
                }

//                documentReference.collection("random").



            }

        });

    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {

                hour = hourofDay;
                minutes = minute;
                timeEdit.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditEventActivity.this, onTimeSetListener, hour, minutes, true);

        timePickerDialog.show();
    }

}