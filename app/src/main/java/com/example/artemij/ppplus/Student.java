package com.example.artemij.ppplus;

import android.media.Image;
import android.net.Uri;

/**
 * Created by Artemij on 06.11.2016.
 */
public class Student {
    private String name;
    private String nickName;
    private int plusAmount;
    private Uri imageUri;

    public Student() {
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


}
