package com.example.artemij.ppplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Created by Artemij on 06.11.2016.
 */
public class StudAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;

    //пулл cтудентов:
    ArrayList<Student> objects;

    StudAdapter(Context context, ArrayList<Student> data) {
        ctx = context;
        objects = data;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //обработка нажатия:
    /*
    public interface ViewClickListener {
        void onItemClicked(int position);
    }

    private ViewClickListener mViewClickListener;

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
    */

    //ПЕРЕОПРЕДЕЛЕННЫЕ МЕТОДЫ ДЛЯ АДАПТЕРА:

    //кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    //элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    //id по позиции
    @Override

    public long getItemId(int position) {
        return position;
    }

    //пункт списка:
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //используем созданные, но неиспользованные view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_student, parent, false);
        }

        //получить стуента по позиции:
        Student temp = getStudent(position);

        //заполняем View в пункте списка данными из Информации о цвете:


        //для реализации фишек:
        view.setLongClickable(true);
        view.setClickable(true);


        return view;
    }
    //ДОП.МЕТОДЫ:

    //студент по позиции:
    Student getStudent(int position) {
        return ((Student) getItem(position));
    }


    //обработчик для чек-бокса:
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //а здесь идет синхронизация с корзиной:

        }
    };


}
