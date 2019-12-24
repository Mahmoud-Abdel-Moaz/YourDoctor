package com.example.healthyapp.ui.Medicine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.healthyapp.PharmacyMapsActivity;
import com.example.healthyapp.R;
import com.example.healthyapp.pojo.Medicine;

import java.util.Locale;

import io.paperdb.Paper;

public class MedicineDetailsActivity extends AppCompatActivity {


    TextView txtName;

    TextView txtDescription;

    TextView txtInteractions;

    TextView txtDosage;

    TextView txtSideEffects;

    TextView txtContraindications;

    TextView txtUses;

    TextView txtAlternatives;

    TextView txtResources;

    Button butOrder;

    Intent intent;
    String MedicineName, lang;
    FirebaseFirestore firestore;
    DocumentReference ducRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);


        Paper.init(this);
        lang = Paper.book().read("language","en");
        setLocale(lang);
        intent = getIntent();
        MedicineName = intent.getStringExtra("name");


        txtName = findViewById(R.id.txt_name);
        txtDescription = findViewById(R.id.txt_description);
        txtInteractions = findViewById(R.id.txt_interactions);
        txtDosage = findViewById(R.id.txt_dosage);
        txtSideEffects = findViewById(R.id.txt_sideEffects);
        txtContraindications = findViewById(R.id.txt_contraindications);
        txtUses = findViewById(R.id.txt_uses);
        txtAlternatives = findViewById(R.id.txt_alternatives);
        txtResources = findViewById(R.id.txt_resource);

        butOrder = findViewById(R.id.but_order);


        firestore = FirebaseFirestore.getInstance();

        ducRef = firestore.collection("Medicines").document(MedicineName);
        ducRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Medicine medicine = task.getResult().toObject(Medicine.class);
                    if (lang.equals("ar")) {
                        txtName.setText(medicine.getAr_name());
                        txtDescription.setText(medicine.getAr_description());
                        txtDosage.setText(medicine.getAr_dosage());
                        txtContraindications.setText(medicine.getAr_contraindications());
                        txtUses.setText(medicine.getAr_uses());
                        txtInteractions.setText(medicine.getAr_interactions());
                        txtSideEffects.setText(medicine.getAr_sideEffects());
                        txtAlternatives.setText(medicine.getAr_alternatives());
                        txtResources.setText(medicine.getAr_resource());
                    } else {
                        txtName.setText(medicine.getEn_name());
                        txtDescription.setText(medicine.getEn_description());
                        txtDosage.setText(medicine.getEn_dosage());
                        txtContraindications.setText(medicine.getEn_contraindications());
                        txtUses.setText(medicine.getEn_uses());
                        txtInteractions.setText(medicine.getEn_interactions());
                        txtSideEffects.setText(medicine.getEn_sideEffects());
                        txtAlternatives.setText(medicine.getEn_alternatives());
                        txtResources.setText(medicine.getEn_resource());
                    }
                }
            }
        });

        butOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicineDetailsActivity.this, PharmacyMapsActivity.class));
            }
        });


    }
    private void setLocale(String lang){
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("My_Lang","en");
        setLocale(language);
    }
}
