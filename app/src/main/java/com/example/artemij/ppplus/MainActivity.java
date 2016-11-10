package com.example.artemij.ppplus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

//import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView image;
    Button singleButton;
    ListView studList;

    StudAdapter specAdapter;
    ArrayList<Student> listS = new ArrayList<>();

    static final int GALLERY_REQUEST = 1;
    static final int CM_PLUS_ID = 0;
    static final int CM_EDIT_ID = 1;
    static final int CM_DELETE_ID = 2;

    final String LOG_TAG = "myLogs";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "HelveticaNeueCyr-Light.otf");

        Log.d(LOG_TAG, "Сообщение");
        Log.d(LOG_TAG, "Список пуст: " + "none" + " " + "none" + " " + 0);
        specAdapter = new StudAdapter(this, listS);

        studList = (ListView) findViewById(R.id.listView);
        studList.setAdapter(specAdapter);
        registerForContextMenu(studList);

        singleButton = (Button) findViewById(R.id.button);
        singleButton.setTypeface(myTypeFace);
        singleButton.setText("Добавить");

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap galleryPic = null;

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    Student newS = new Student("Агакерим Агададашев", "Милый зам.старосты", 1, selectedImage);
                    Log.d("myLogs", "Новый элемент: " + newS.getName() + " " + newS.getNickName() + " " + newS.getPlusAmount());
                    listS.add(newS);
                    specAdapter.notifyDataSetChanged();
                }
        }
    }

    //Про контекстное меню:
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_PLUS_ID, 0, "Плюсанем");
        menu.add(0, CM_EDIT_ID, 1, "Подправим");
        menu.add(0, CM_DELETE_ID, 2, "Удалим");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // получаем инфу о пункте списка
        final AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == CM_PLUS_ID) {
            //прибавляем значение:
            (listS.get(acmi.position)).addPlus();
            specAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Элементу в позиции" + acmi.position + "прибавлен плюс");

            return true;
        } else if (item.getItemId() == CM_EDIT_ID) {
            //запускаем вторую активность:


        } else if (item.getItemId() == CM_DELETE_ID) {
            //удаляем ненужный элемент списка:
            listS.remove(acmi.position);
            specAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Элемент в позиции" + acmi.position + "удален");
        }

        return super.onContextItemSelected(item);
    }

}
