package com.example.artemij.ppplus;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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

    //база данных:
    DBStudentHelper dbStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //шрифт:
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "HelveticaNeueCyr-Light.otf");

        //загружаем базу:
        dbStudent = new DBStudentHelper(getApplicationContext());

        //настройка списка:
        //получаем список студентов из нашей базы:
        listS = dbStudent.getAllStudents();
        specAdapter = new StudAdapter(this, listS);

        //заголовок:
        String[] hNames = {"Плюсы", "Фото", "Имя"};
        header = createHeader(hNames, myTypeFace);

        //сам список:
        studList = (ListView) findViewById(R.id.listView);
        studList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        studList.addHeaderView(header);
        studList.setAdapter(specAdapter);
        studList.setFocusable(false);
        studList.setClickable(true);
        registerForContextMenu(studList);


        //кнопки:
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

    //функция для нахождения элемента списка по ID:
    private Student findStudentByID(ArrayList<Student> slist, long id) {
        for (Student s : slist) {
            if (s.getID() == id) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CALL_EDIT_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    String type = data.getStringExtra("TypeCall");
                    long studID = data.getLongExtra("StudentID", -1);

                    Log.d(LOG_TAG, "ID Студента = " + studID + " | Type= |" + type + "|");

                    if (studID != -1) {
                        if (type.equals("NEW")) {
                            //вынимаем из базы нового добавленного студента
                            Student s = dbStudent.getStudent(studID);
                            Log.d(LOG_TAG, "Ник=" + s.getNickName() + " Имя=" + s.getName() + " Плюсы= " + s.getPlusAmount() + " | ID= |" + s.getID() + "|");
                            listS.add(dbStudent.getStudent(studID));

                        } else if (type.equals("EDIT")) {
                            //находим студента по ID и обновляем его, получая информацию из базы:
                            findStudentByID(listS, studID).update(dbStudent.getStudent(studID));
                        }
                    }

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
        final int offset = studList.getHeaderViewsCount();

        if (item.getItemId() == CM_PLUS_ID) {

            //прибавим значение:
            (listS.get(acmi.position - offset)).addPlus();
            //обновим элемент в базе:
            dbStudent.plusStudent(listS.get(acmi.position - offset));

            specAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Элементу в позиции " + (acmi.position - offset) + " прибавлен плюс");

            return true;
        } else if (item.getItemId() == CM_EDIT_ID) { //переходим на EditActivity
            //запускаем вторую активность:
            final Student temp = listS.get(acmi.position - offset);
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("TypeCall", "EDIT");
            intent.putExtra("StudentID", temp.getID());
            startActivityForResult(intent, CALL_EDIT_ACTIVITY);

            Toast.makeText(this, "Выполнен переход в новую активность", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == CM_DELETE_ID) {
            //диалоговое окно:
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Вы исключаете студента!")
                    .setMessage("Вы уверены?")
                    .setIcon(R.drawable.warning)
                    .setCancelable(false)
                    .setPositiveButton("Уверены",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long studID = listS.get(acmi.position - offset).getID();
                                    //удалим из базы:
                                    dbStudent.deleteStudent(studID);
                                    //удаляем ненужный элемент из списка:
                                    listS.remove(acmi.position - offset);
                                    specAdapter.notifyDataSetChanged();
                                    Log.d(LOG_TAG, "Элемент в позиции " + (acmi.position - offset) + " удален");
                                }
                            })
                    .setNegativeButton("Погорячились",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

        }

        return super.onContextItemSelected(item);
    }

}
