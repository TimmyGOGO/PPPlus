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
    private String imageUri;

    public Student() {
        position = 0;
        name = "";
        nickName = "";
        plusAmount = 0;
        imageUri = null;
    }

    public Student(Student builder) {
        position = builder.getPosition();
        name = builder.getName();
        nickName = builder.getNickName();
        plusAmount = builder.getPlusAmount();
        imageUri = builder.getStringImageUri();
    }


    public void setPosition(int pos) {
        position = pos;
    }

    public int getPosition() {
        return position;
    }

    public String getStringImageUri() {
        return imageUri;
    }

    public Uri getStudentImageUri() {
        Uri myUri = Uri.parse(imageUri);
        return myUri;
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

    public void setUri(String imgUri) {
        imageUri = imgUri;
    }

    public static Builder newBuilder() {
        return new Student().new Builder();
    }

    public class Builder {

        private Builder() {
            //private builder
        }

        public Builder setPosition(int pos) {

            Student.this.position = pos;
            return this;
        }

        public Builder setUri(String imgUri) {

            Student.this.imageUri = imgUri;
            return this;
        }

        public Builder setName(String _name) {

            Student.this.name = _name;
            return this;
        }

        public Builder setNickName(String _name) {

            Student.this.nickName = _name;
            return this;
        }

        public Builder setPlusAmount(int pA) {

            Student.this.plusAmount = pA;
            return this;
        }

        public Student build() {

            return Student.this;
        }

    }

}
