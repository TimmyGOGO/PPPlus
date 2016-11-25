package com.example.artemij.ppplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Artemij on 25.11.2016.
 */
public class DBStudentHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "PStudentsDB";

    private static final String TABLE_STUDENTS = "students";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NICKNAME = "nickName";
    private static final String KEY_PLUS_AMOUNT = "plusAmount";
    private static final String KEY_IMAGE_URI = "imageUri";

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_NICKNAME, KEY_PLUS_AMOUNT, KEY_IMAGE_URI};

    public DBStudentHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + " ( " +
                KEY_ID + " LONG PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_NICKNAME + " TEXT, " +
                KEY_PLUS_AMOUNT + " INTEGER, " +
                KEY_IMAGE_URI + " TEXT) ";

        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);

        this.onCreate(db);
    }

    //основные методы для работы:
    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        //KEY_ID - не добавляем, он у нас автоматически инкрементируется
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_NICKNAME, student.getNickName());
        values.put(KEY_PLUS_AMOUNT, student.getPlusAmount());
        values.put(KEY_IMAGE_URI, student.getStringImageUri());

        long id = db.insert(TABLE_STUDENTS, null, values);
        db.close();

        return id;
    }

    public Student getStudent(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        //извлекаем запись с нашим id:
        Cursor cursor = db.query(
                TABLE_STUDENTS, COLUMNS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if (cursor != null) cursor.moveToFirst();

        Student newStudent = Student.newBuilder()
                .setID(cursor.getLong(0))
                .setName(cursor.getString(1))
                .setNickName(cursor.getString(2))
                .setPlusAmount(cursor.getInt(3))
                .setUri(cursor.getString(4))
                .build();

        return newStudent;
    }

    //возвращаем полный список студентов:
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<Student>();

        String query = "SELECT * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Student student = null;
        if (cursor.moveToFirst()) {
            do {
                student = Student.newBuilder()
                        .setID(cursor.getLong(0))
                        .setName(cursor.getString(1))
                        .setNickName(cursor.getString(2))
                        .setPlusAmount(cursor.getInt(3))
                        .setUri(cursor.getString(4))
                        .build();

                students.add(student);
            } while (cursor.moveToNext());
        }

        return students;
    }

    //обновим студента:
    public long updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_NICKNAME, student.getNickName());
        values.put(KEY_PLUS_AMOUNT, student.getPlusAmount());
        values.put(KEY_IMAGE_URI, student.getStringImageUri());

        long i = db.update(TABLE_STUDENTS,
                values,
                KEY_ID,
                new String[]{String.valueOf(student.getID())});

        db.close();
        return i;
    }

    //удалим студента:
    public void deleteStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long delCount = db.delete(TABLE_STUDENTS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //изменим только количество плюсов:
    public long plusStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLUS_AMOUNT, student.getPlusAmount());

        long i = db.update(TABLE_STUDENTS,
                values,
                KEY_ID,
                new String[]{String.valueOf(student.getID())});

        db.close();
        return i;
    }

}

