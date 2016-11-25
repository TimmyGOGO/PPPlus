package com.example.artemij.ppplus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Artemij on 20.11.2016.
 */
public class NStudent implements Parcelable {
    //данные для передачи пакета:
    //(возьмем сразу класс с данными)
    private Student chap;

    //и добавим методы для извлечения и добавления:
    public Student getChap() {
        return chap;
    }

    public void setChap(Student chap) {
        this.chap = chap;
    }

    // We can also include child Parcelable objects. Assume MySubParcel is such a Parcelable:
    //.....

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(chap.getName());
        out.writeString(chap.getNickName());
        out.writeInt(chap.getPlusAmount());
        out.writeInt(chap.getID());
        out.writeString(chap.getStringImageUri());
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private NStudent(Parcel in) {
        chap = new Student();
        chap.setName(in.readString());
        chap.setNickName(in.readString());
        chap.setPlusAmount(in.readInt());
        chap.setID(in.readInt());
        chap.setUri(in.readString());

    }

    public NStudent() {
        chap = new Student();
        // Normal actions performed by class, since this is still a normal object!
    }

    public NStudent(Student s) {
        chap = s;
        // Normal actions performed by class, since this is still a normal object!
    }

    // In the vast majority of cases you can simply return 0 for this.
    // There are cases where you need to use the constant `CONTENTS_FILE_DESCRIPTOR`
    // But this is out of scope of this tutorial
    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<NStudent> CREATOR
            = new Parcelable.Creator<NStudent>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public NStudent createFromParcel(Parcel in) {
            return new NStudent(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public NStudent[] newArray(int size) {
            return new NStudent[size];
        }
    };

}
