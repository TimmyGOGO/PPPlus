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
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    Button singleButton;
    ListView studList;

    StudAdapter specAdapter;
    ArrayList<Student> listS = new ArrayList<>();

    static final int CM_PLUS_ID = 0;
    static final int CM_EDIT_ID = 1;
    static final int CM_DELETE_ID = 2;

    final String LOG_TAG = "myLogs";
    static final int CALL_EDIT_ACTIVITY = 1;

    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "HelveticaNeueCyr-Light.otf");

        specAdapter = new StudAdapter(this, listS);
        String[] hNames = {"Плюсы", "Фото", "Имя"};
        header = createHeader(hNames, myTypeFace);


        studList = (ListView) findViewById(R.id.listView);
        studList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        studList.addHeaderView(header);
        studList.setAdapter(specAdapter);
        studList.setFocusable(false);
        studList.setClickable(true);
        registerForContextMenu(studList);

        singleButton = (Button) findViewById(R.id.button);
        singleButton.setTypeface(myTypeFace);
        singleButton.setText("Добавить");

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("TypeCall", "NEW");
                startActivityForResult(intent, CALL_EDIT_ACTIVITY);
                Toast.makeText(getApplicationContext(), "Выполнен переход в новую активность", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private View createHeader(String[] p, Typeface tf) {
        View v = getLayoutInflater().inflate(R.layout.header_layout, null);
        ((TextView) v.findViewById(R.id.texth1)).setText(p[0]);
        ((TextView) v.findViewById(R.id.texth1)).setTypeface(tf);
        ((TextView) v.findViewById(R.id.texth2)).setText(p[1]);
        ((TextView) v.findViewById(R.id.texth2)).setTypeface(tf);
        ((TextView) v.findViewById(R.id.texth3)).setText(p[2]);
        ((TextView) v.findViewById(R.id.texth3)).setTypeface(tf);
        return v;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CALL_EDIT_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    String type = data.getStringExtra("TypeCall");
                    NStudent object = (NStudent) data.getParcelableExtra("studentObject");
                    Student newStud = new Student(object.getChap());

                    Log.d(LOG_TAG, "Ник=" + newStud.getNickName() + " Имя=" + newStud.getName() + " Плюсы= " + newStud.getPlusAmount() + " | Type= |" + type + "|");

                    if (type.equals("NEW")) {
                        listS.add(newStud);
                        int pos = listS.indexOf(newStud);
                        (listS.get(pos)).setPosition(pos);
                        Log.d(LOG_TAG, "Добавили новый элемент");
                    } else if (type.equals("EDIT")) {
                        listS.remove(newStud.getPosition());
                        listS.add(newStud.getPosition(), newStud);
                        Log.d(LOG_TAG, "Изменили элемент");
                    }
                    Log.d(LOG_TAG, "Размер списка = " + listS.size());
                    specAdapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Ну, добавили =)", Toast.LENGTH_SHORT).show();

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Передумали=)", Toast.LENGTH_SHORT).show();
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

        //если добавили header-ы к списку, то  придется вычитать их из позиции:
        int offset = studList.getHeaderViewsCount();

        if (item.getItemId() == CM_PLUS_ID) {
            //прибавляем значение:
            (listS.get(acmi.position - offset)).addPlus();
            specAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Элементу в позиции " + (acmi.position - offset) + " прибавлен плюс");

            return true;
        } else if (item.getItemId() == CM_EDIT_ID) {
            //запускаем вторую активность:
            final Student temp = listS.get(acmi.position - offset);
            NStudent dataToSend = new NStudent(temp);

            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("TypeCall", "EDIT");
            intent.putExtra("studentObject", dataToSend);
            startActivityForResult(intent, CALL_EDIT_ACTIVITY);

            Toast.makeText(this, "Выполнен переход в новую активность", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == CM_DELETE_ID) {
            //удаляем ненужный элемент списка:
            listS.remove(acmi.position - offset);
            specAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Элемент в позиции " + (acmi.position - offset) + " удален");
        }

        return super.onContextItemSelected(item);
    }

}
