package com.ahdollars.crazyeights.recyclerviewstudentdisplay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ahdollars.crazyeights.recyclerviewstudentdisplay.model.Student;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    TextView noOfStudent;
    RadioGroup courseSelect,genderSelect;
    RadioButton male,female,pandora,java,jango;
    Boolean gender=true;
    String course="";
    Button submit,btnView;
    String name,address,mobno,email,college;
    ImageView ivPic;
    Bitmap bitmap=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initialize();
        genderSelect.check(R.id.rb_male);
        courseSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i){
                    case R.id.rb_pandora:
                        course="ANDROID";
                        radioGroup.check(i);
                        break;
                    case R.id.rb_java:
                        course="JAVA";
                        radioGroup.check(i);
                        break;
                    case R.id.rb_jango:
                        course="PYTHON";
                        radioGroup.check(i);
                        break;

                }
            }
        });

        genderSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i){
                    case R.id.rb_male:
                        gender=true;
                        radioGroup.check(i);
                        break;
                    case R.id.rb_female:
                        gender=false;
                        radioGroup.check(i);
                        break;

                }
            }
        });

        submit.setOnClickListener(this);

        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,555);
            }
        });

    }



    private void initialize() {

        etName=(EditText)findViewById(R.id.et_name);

        courseSelect=(RadioGroup)findViewById(R.id.rg_course_select);
        genderSelect=(RadioGroup)findViewById(R.id.rg_gender);

        male=(RadioButton)findViewById(R.id.rb_male);
        female=(RadioButton)findViewById(R.id.rb_female);
        java=(RadioButton)findViewById(R.id.rb_java);
        pandora=(RadioButton)findViewById(R.id.rb_pandora);
        jango=(RadioButton)findViewById(R.id.rb_jango);
        btnView=(Button)findViewById(R.id.btn_view);
        submit=(Button)findViewById(R.id.submit);
        ivPic=(ImageView)findViewById(R.id.iv_pic);
  //noOfStudent=(TextView)findViewById(R.id.tv_student_list);
        btnView.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.submit){

            Boolean error=false;
            if(etName.getText().toString().trim().equals("")){
                error=true;
                etName.setHint("Enter Name");
                etName.setHintTextColor(getResources().getColor(R.color.error));
            }else{
                etName.setTextColor(getResources().getColor(R.color.normal));
                name=etName.getText().toString();
            }


            if(bitmap==null){
                error=true;
            }


            if(course=="")
                error=true;

            if(!error){
         /*   Intent i=new Intent(RegistrationActivity.this,DisplayActivity.class);
            i.putExtra("name",name);
            i.putExtra("email",email);
            i.putExtra("mobno",mobno);
            i.putExtra("addr",address);
            i.putExtra("college",college);
            i.putExtra("course",course);
            i.putExtra("gender",gender);
            i.putExtra("pic",bitmap);


            startActivity(i);
            */
                String g;
                if(gender){
                    g="MALE";
                }else{
                    g="FEMALE";
                }
                //THIS ID THE OLD PROJECT IN WHICH WE STORE IN STATIC ARRAYLIST

              //  Student.student.add(new Student(g,course,name,bitmap));
             //   Toast.makeText(getApplicationContext(),"ADDED",Toast.LENGTH_SHORT).show();
               // noOfStudent.setText("No. Of Registered User  "+Student.student.size());


                new StoreStudent(this).execute(new Student(g,course,name,bitmap,System.currentTimeMillis()+".jpg"));
            }

        }else{

            Intent i=new Intent(RegistrationActivity.this,DisplayActivity.class);

            startActivity(i);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            Bundle extra = data.getExtras();
            bitmap = (Bitmap) extra.get("data");
            ivPic.setImageBitmap(bitmap);
        }
    }
}
