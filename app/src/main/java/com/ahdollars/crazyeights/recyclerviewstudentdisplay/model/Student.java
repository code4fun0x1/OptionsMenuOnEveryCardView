package com.ahdollars.crazyeights.recyclerviewstudentdisplay.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Shashank on 29-08-2016.
 */
public class Student{

    public  Bitmap photo;
    public String name;
    public String gender;
    public String course;
    public String photoName;
    public static ArrayList<Student> student=new ArrayList<>();



    public Student(String gender, String course, String name, Bitmap photo,String photoName) {
        this.gender = gender;
        this.course = course;
        this.name = name;
        this.photo = photo;
        this.photoName=photoName;

    }

}
