package com.example.artemij.ppplus;

import android.media.Image;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Artemij on 06.11.2016.
 */
public class Student implements Serializable {
    private int position;
    private String name;
    private String nickName;
    private int plusAmount;
    private Uri imageUri;

    public Student() {
        position = 0;
        name = "";
        nickName = "";
        plusAmount = 0;
        imageUri = null;
    }

    public Student(String _name, String _nickName, int pA, Uri img) {
        name = _name;
        nickName = _nickName;
        plusAmount = pA;
        imageUri = img;
    }

    public void describeStudent(String _name, String _nickName, int pA, Uri img) {
        name = _name;
        nickName = _nickName;
        plusAmount = pA;
        imageUri = img;
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public int getPosition() {
        return position;
    }

    public Uri getStudentImageUri() {
        return imageUri;
    }

    public int getPlusAmount() {
        return plusAmount;
    }

    public String getStringPlusAmount() {
        return new String("" + plusAmount);
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public void addPlus() {
        plusAmount = plusAmount + 1;
    }

    public void substractPlus() {
        plusAmount = plusAmount - 1;
    }

    public void setUri(Uri imgUri) {
        imageUri = imgUri;
    }


}
