package com.example.artemij.ppplus;

import android.media.Image;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Artemij on 06.11.2016.
 */
public class Student implements Serializable {
    private long ID;
    private String name;
    private String nickName;
    private int plusAmount;
    private String imageUri;

    //CONSTRUCTORS:
    public Student() {
        ID = 0;
        name = "";
        nickName = "";
        plusAmount = 0;
        imageUri = "";
    }
    public Student(Student builder) {
        ID = builder.getID();
        name = builder.getName();
        nickName = builder.getNickName();
        plusAmount = builder.getPlusAmount();
        imageUri = builder.getStringImageUri();
    }

    //SETTERS:
    public void setName(String _name) {
        name = _name;
    }

    public void setNickName(String _name) {
        nickName = _name;
    }

    public void setPlusAmount(int pA) {
        plusAmount = pA;
    }

    public void setUri(String imgUri) {
        imageUri = imgUri;
    }

    public void setID(long id) {
        ID = ID;
    }

    //GETTERS:
    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public long getID() {
        return ID;
    }

    public int getPlusAmount() {
        return plusAmount;
    }
    public String getStringImageUri() {
        return imageUri;
    }

    public Uri getStudentImageUri() {
        Uri myUri = Uri.parse(imageUri);
        return myUri;
    }
    public String getStringPlusAmount() {
        return new String("" + plusAmount);
    }

    //ADDITIONS:
    public void update(Student newUp) {
        name = newUp.getName();
        nickName = newUp.getNickName();
        plusAmount = newUp.getPlusAmount();
        imageUri = newUp.getStringImageUri();
    }

    public void addPlus() {
        plusAmount = plusAmount + 1;
    }
    public void substractPlus() {
        plusAmount = plusAmount - 1;
    }

    //BUILDER REALIZATION:
    public static Builder newBuilder() {
        return new Student().new Builder();
    }

    public class Builder {

        //Constructor:
        private Builder() {
            //private builder
        }

        //Setters:
        public Builder setID(long ID) {

            Student.this.ID = ID;
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

        //for building class:
        public Student build() {

            return Student.this;
        }

    }

}
