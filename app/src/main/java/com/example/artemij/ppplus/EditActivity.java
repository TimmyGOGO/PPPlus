package com.example.artemij.ppplus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    ImageView img;

    static final int GALLERY_REQUEST = 1;

    String type;
    Student studEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Typeface myTF = Typeface.createFromAsset(getAssets(), "HelveticaNeueCyr-Light.otf");
        setUpEnvironmentViews(myTF);

        Intent i = getIntent();
        type = i.getStringExtra("TypeCall");
        studEdit = new Student();

        if (type == "NEW") {
            setUpNewEnvironment();
        } else if (type == "EDIT") {
            studEdit = (Student) i.getSerializableExtra("studentObject");
            setUpEditEnvironment();
        }


    }

    private void setUpEnvironmentViews(Typeface myTF) {
        //общая настройка элементов интерфейса:
        img = (ImageView) findViewById(R.id.imageViewEdit);
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
        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int plusAmount;
                try {
                    plusAmount = Integer.parseInt(edPlus.getText().toString());
                } catch (Exception e) {
                    plusAmount = 0;
                }

                studEdit.describeStudent(
                        edName.getText().toString(),
                        edNick.getText().toString(),
                        plusAmount,
                        studEdit.getStudentImageUri()
                );

                Intent intent1 = new Intent();
                intent1.putExtra("TypeCall", type);
                intent1.putExtra("studentObject", studEdit);
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });
        bCANCEL = (Button) findViewById(R.id.btnCancel);
        bCANCEL.setTypeface(myTF);
        loadSD = (Button) findViewById(R.id.btnLoad1);
        loadSD.setTypeface(myTF);
        loadSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
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

    private void setUpEditEnvironment() {
        edNick.setText(studEdit.getNickName());
        edName.setText(studEdit.getName());
        edPlus.setText(studEdit.getStringPlusAmount());
        sNick.setChecked(true);
        sNick.setChecked(true);
        sNick.setChecked(true);

        try {
            Bitmap galleryPic = MediaStore.Images.Media.getBitmap(getContentResolver(), studEdit.getStudentImageUri());
            img.setImageBitmap(galleryPic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setUpNewEnvironment() {
        edNick.setText("");
        edName.setText("");
        edPlus.setText("");
        sNick.setChecked(false);
        sNick.setChecked(false);
        sNick.setChecked(false);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        Bitmap galleryPic = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        img.setImageBitmap(galleryPic);
                        //запомнили адрес:
                        studEdit.setUri(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

        }
    }
}
