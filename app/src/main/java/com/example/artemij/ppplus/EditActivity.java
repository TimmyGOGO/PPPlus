package com.example.artemij.ppplus;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    EditText edNick;
    EditText edName;
    EditText edPlus;
    Switch sNick;
    Switch sName;
    Switch sPlus;
    Button loadSD;
    Button loadNET;
    Button bOK;
    Button bCANCEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Typeface myTF = Typeface.createFromAsset(getAssets(), "HelveticaNeueCyr-Light.otf");
        setUpEnvironmentViews(myTF);

        Intent i = getIntent();
        String type = i.getStringExtra("TypeCall");


        Student info = (Student) i.getSerializableExtra("studentObject");

        //загружаем элементы



    }

    private void setUpEnvironmentViews(Typeface myTF) {
        //общая настройка элементов интерфейса:
        //поля редактирования:
        edNick = (EditText) findViewById(R.id.editNick);
        edNick.setTypeface(myTF);
        edName = (EditText) findViewById(R.id.editName);
        edName.setTypeface(myTF);
        edPlus = (EditText) findViewById(R.id.editPlus);
        edPlus.setTypeface(myTF);
        //кнопки:
        bOK = (Button) findViewById(R.id.btnSave);
        bOK.setTypeface(myTF);
        bCANCEL = (Button) findViewById(R.id.btnCancel);
        bCANCEL.setTypeface(myTF);
        loadSD = (Button) findViewById(R.id.btnLoad1);
        loadSD.setTypeface(myTF);
        loadNET = (Button) findViewById(R.id.btnLoad2);
        loadNET.setTypeface(myTF);
        //переключатели:
        sNick = (Switch) findViewById(R.id.switchNick);
        sNick.setTypeface(myTF);
        sNick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edNick.setEnabled(isChecked);
            }
        });
        sName = (Switch) findViewById(R.id.switchName);
        sName.setTypeface(myTF);
        sName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edName.setEnabled(isChecked);
            }
        });
        sPlus = (Switch) findViewById(R.id.switchPlus);
        sPlus.setTypeface(myTF);
        sPlus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edPlus.setEnabled(isChecked);
            }
        });
    }

    private void setUpEditEnvironment(Student st) {

    }

    private void setUpNewEnvironment() {

    }

}
