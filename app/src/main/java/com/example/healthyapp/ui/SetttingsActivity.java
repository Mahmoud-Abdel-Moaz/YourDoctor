package com.example.healthyapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.healthyapp.R;

import java.util.Locale;

import io.paperdb.Paper;

public class SetttingsActivity extends AppCompatActivity {

    Spinner spinner;
    String lang;

    TextView txt_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setttings);

       // loadLocale();

        Paper.init(this);
      lang= Paper.book().read("language");
          if (lang==null){
            Paper.book().write("language","en");
            lang="en";
        }

        txt_lang=findViewById(R.id.txt_lang);


        spinner=findViewById(R.id.spinner);




        ArrayAdapter adapter=ArrayAdapter.createFromResource(
                this,
                R.array.spinner_array,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)

            {
                if (lang.equals("ar")){
                    if (position==1){
                        Paper.book().write("language","en");
                    }else if(position==0){
                        Paper.book().write("language","ar");
                    }
                }else {
                    if (position==0){
                        Paper.book().write("language","en");
                    }else if(position==1){
                        Paper.book().write("language","ar");
                    }
                }

                updateView((String)Paper.book().read("language"));

               /* String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Add new category"))
                {
                    // do your stuff
                }*/
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



    }

    private void updateView(String language){
        String  l= Paper.book().read("language");
        if(!l.equals(lang)){
            Context context=LocaleHelper.setLocale(this,language);
            Resources resources=context.getResources();
            startActivity(new Intent(SetttingsActivity.this,MainActivity.class));
            finish();
            //    startActivity(getIntent());
        }

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
