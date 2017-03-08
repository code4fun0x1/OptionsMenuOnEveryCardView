package com.ahdollars.crazyeights.recyclerviewstudentdisplay;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahdollars.crazyeights.recyclerviewstudentdisplay.model.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayActivity extends Activity {

    public  ArrayList<Student> student=new ArrayList<>();
    public static final String FILE_NAME="Details.txt";
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        AsyncTask<Void,Void,Void> loader=new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                File f=new File(Environment.getExternalStorageDirectory(),FILE_NAME);
                try {
                    //when data comes to myprogram then it is InputStream
                    //when data leaves myprogram the it is outputStream

                    FileReader freader=new FileReader(f);
                    BufferedReader br=new BufferedReader(freader);
                    StringBuilder tstudent=new StringBuilder();
                    String temp=null;
                    while((temp=br.readLine())!=null){
                        String x[]=temp.split("~");
                        student.add(new Student(x[0],x[1],x[2],null,x[3]));
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                rv.setAdapter(new StudentAdapter());
            }
        };

        loader.execute();

        rv=(RecyclerView)findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }



    public class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
            oneView=itemView;
            photo=(ImageView)itemView.findViewById(R.id.iv_photo);
            name=(TextView)itemView.findViewById(R.id.tv_name);
            course=(TextView)itemView.findViewById(R.id.tv_course);
            gender=(TextView)itemView.findViewById(R.id.tv_gender);
            overflow=(ImageView)itemView.findViewById(R.id.overFlow);
        }
        View oneView;
        ImageView photo,overflow;
        TextView name,gender,course;

    }


    public class StudentAdapter extends RecyclerView.Adapter<Holder>{



        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=getLayoutInflater();
            View v=inflater.inflate(R.layout.list_student,parent,false);
            Holder holder=new Holder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {




          /*  holder.gender.setText("SEX: "+Student.student.get(position).gender);
            holder.name.setText(Student.student.get(position).name);
            holder.course.setText("COURSE: "+Student.student.get(position).course);
            holder.photo.setImageBitmap(Student.student.get(position).photo);
            */
            holder.gender.setText("SEX: "+student.get(position).gender);
            holder.name.setText(student.get(position).name);
            holder.course.setText("COURSE: "+student.get(position).course);


            AsyncTask<String,Void,Bitmap> x=new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                 //   Toast.makeText(getApplicationContext(),params[0],Toast.LENGTH_SHORT).show();
                   return BitmapFactory.decodeFile(String.valueOf(new File(Environment.getExternalStorageDirectory(),params[0])));
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    holder.photo.setImageBitmap(bitmap);
                }
            };
            x.execute(student.get(position).photoName);


            holder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup=new PopupMenu(v.getContext(),v);
                    MenuInflater inflater=popup.getMenuInflater();
                    inflater.inflate(R.menu.mymenu,popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            return false;
                        }
                    });
                    popup.show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return student.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }


}
