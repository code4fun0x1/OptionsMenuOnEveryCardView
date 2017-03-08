package com.ahdollars.crazyeights.recyclerviewstudentdisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.ahdollars.crazyeights.recyclerviewstudentdisplay.model.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Shashank on 01-10-2016.
 */

public class StoreStudent extends AsyncTask<Student,Void,Void> {

    Context context;

    public StoreStudent(Context context){
        this.context=context;
    }

    public static final String FILE_NAME="Details.txt";

    @Override
    protected Void doInBackground(Student... params) {
        Student s=params[0];
        File x=new File(Environment.getExternalStorageDirectory(),FILE_NAME);
        if(!x.exists()){
            try {
                x.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            StringBuilder student=new StringBuilder();
            student.append(s.gender);
            student.append("~");
            student.append(s.course);
            student.append("~");
            student.append(s.name);




            student.append("~");
            student.append(s.photoName);
            student.append("~");
            student.append("\n");



            FileOutputStream fout=new FileOutputStream(x,true);
            fout.write(student.toString().getBytes());

            File photoFile=new File(Environment.getExternalStorageDirectory(),s.photoName);
            FileOutputStream f=new FileOutputStream(photoFile,false);
            s.photo.compress(Bitmap.CompressFormat.JPEG,100,f);
            f.flush();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
