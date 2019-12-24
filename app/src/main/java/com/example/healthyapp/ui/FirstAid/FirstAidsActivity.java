package com.example.healthyapp.ui.FirstAid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.healthyapp.R;
import com.example.healthyapp.pojo.FirstAid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class FirstAidsActivity extends AppCompatActivity {

    RecyclerView recycler;
    FirstAidAdupter firstAidAdupter;

    EditText edit_aidSearch;

    FirebaseFirestore firebaseFirestore;

    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aids);


        Paper.init(this);
        lang= Paper.book().read("language","en");
        setLocale(lang);
      //  loadLocale();

        recycler=findViewById(R.id.recycler);
        edit_aidSearch=findViewById(R.id.edit_aidSearch);

        firstAidAdupter=new FirstAidAdupter(this);
        firstAidAdupter.notifyDataSetChanged();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(firstAidAdupter);

        FirebaseApp.initializeApp(this);

        firebaseFirestore=FirebaseFirestore.getInstance();

        showDate("");

        edit_aidSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showDate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void showDate(final String i){
        firebaseFirestore.collection("FirstAids").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<FirstAid> FirstAids=new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //  Log.d(TAG, document.getId() + " => " + document.getData());
                        if (i.equals("")){
                            FirstAids.add(document.toObject(FirstAid.class));
                            firstAidAdupter.setitemsList(FirstAids);

                        }else if (!i.isEmpty()){
                            if (lang.equals("ar")&&document.toObject(FirstAid.class).getAr_name().startsWith(i)){
                                FirstAids.add(document.toObject(FirstAid.class));
                                firstAidAdupter.setitemsList(FirstAids);

                            }else if(lang.equals("en")&&document.toObject(FirstAid.class).getEn_name().toLowerCase().startsWith(i.toLowerCase())){
                                FirstAids.add(document.toObject(FirstAid.class));
                                firstAidAdupter.setitemsList(FirstAids);

                            }
                        }

                    }
                    firstAidAdupter.setitemsList(FirstAids);
                } else {

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
