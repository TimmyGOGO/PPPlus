package com.example.artemij.ppplus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        Typeface myTypeFace = Typeface.createFromAsset(ctx.getAssets(), "HelveticaNeueCyr-Light.otf");

        Log.d("myLogs", "Новый элемент: " + temp.getName() + " " + temp.getNickName() + " " + temp.getPlusAmount());

        //заполняем View в пункте списка данными из Информации о цвете:
        ((TextView) view.findViewById(R.id.textNumber)).setTypeface(myTypeFace);
        ((TextView) view.findViewById(R.id.textNumber)).setText(temp.getPlusAmount());
        ((TextView) view.findViewById(R.id.textNick)).setTypeface(myTypeFace);
        ((TextView) view.findViewById(R.id.textNick)).setText(temp.getNickName());
        ((TextView) view.findViewById(R.id.textFullname)).setTypeface(myTypeFace);
        ((TextView) view.findViewById(R.id.textFullname)).setText(temp.getName());

        Bitmap galleryPic = null;
        CircleImageView img = (CircleImageView) view.findViewById(R.id.student_image);
        try {
            galleryPic = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), temp.getStudentImageUri());
            img.setImageBitmap(galleryPic);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
