package com.example.healthyapp.ui.Diseases;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.ui.Medicine.MedicineDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.healthyapp.R;
import com.example.healthyapp.pojo.Disease;

import java.util.Locale;

import io.paperdb.Paper;

public class DiseaseDetailsActivity extends AppCompatActivity {


    Intent intent;
    String MedicineName,lang;
    FirebaseFirestore firestore;
    DocumentReference ducRef;

    TextView txt_name,txt_description,txt_symptoms,txt_causes,txt_treatment,txt_resource;
    String treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);

        Paper.init(this);
        lang= Paper.book().read("language","en");
        setLocale(lang);
        intent=getIntent();

        txt_name=findViewById(R.id.txt_name);
        txt_description=findViewById(R.id.txt_description);
        txt_symptoms=findViewById(R.id.txt_symptoms);
        txt_causes=findViewById(R.id.txt_causes);
        txt_treatment=findViewById(R.id.txt_treatment);
        txt_resource=findViewById(R.id.txt_resource);
        //get clicked disease name
        MedicineName=intent.getStringExtra("name");

        firestore=FirebaseFirestore.getInstance();
        treatment="empty";

        txt_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(DiseaseDetailsActivity.this, treatment, Toast.LENGTH_SHORT).show();
                if (!treatment.equals("empty")){
                    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                    try {
                        DocumentReference docIdRef = rootRef.collection("Medicines").document(treatment);
                        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Log.d(TAG, "Document exists!");0;
                                        Intent i=new Intent(DiseaseDetailsActivity.this, MedicineDetailsActivity.class);
                                        i.putExtra("name",treatment);
                                        startActivity(i);
                                    } else {
                                        // Log.d(TAG, "Document does not exist!");
                                        //   Toast.makeText(DiseaseDetailsActivity.this, "Document does not exist!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //  Log.d(TAG, "Failed with: ", task.getException());
                                }
                            }
                        });
                    }catch (Exception e){

                    }

                }
            }
        });

        ducRef=firestore.collection("Diseases").document(MedicineName);
        ducRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    Disease disease=task.getResult().toObject(Disease.class);
                    treatment=disease.getEn_treatment();
                    if (lang.equals("ar")){
                        txt_name.setText(disease.getAr_name());
                        txt_description.setText(disease.getAr_description());
                        txt_symptoms.setText(disease.getAr_symptoms());
                        txt_causes.setText(disease.getAr_reasons());
                        txt_treatment.setText(disease.getAr_treatment());
                        txt_resource.setText(disease.getAr_resource());
                    }else {
                        txt_name.setText(disease.getEn_name());
                        txt_description.setText(disease.getEn_description());
                        txt_symptoms.setText(disease.getEn_symptoms());
                        txt_causes.setText(disease.getEn_reasons());
                        txt_treatment.setText(disease.getEn_treatment());
                        txt_resource.setText(disease.getEn_resource());

                    }
                }
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
