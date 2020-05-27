package com.example.tute04mad;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tute04mad.Database.DBHandler;

import java.util.ArrayList;
import java.util.List;



public class Login extends AppCompatActivity implements View.OnClickListener{
    Button signIn, add, delete, update, selectall;
    TextView uName, pwd;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new DBHandler(this);
        signIn = (Button)findViewById(R.id.btnSign);
        add = (Button)findViewById(R.id.btnAdd);
        delete = (Button)findViewById(R.id.btnDelete);
        update = (Button)findViewById(R.id.btnUpdate);
        selectall = (Button)findViewById(R.id.btnSelectAll);

        uName = (TextView)findViewById(R.id.txtUserName);
        pwd = (TextView)findViewById(R.id.txtPwd);

        signIn.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        selectall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSign:  loginUser();
                break;

            case R.id.btnAdd:   addUser();
                break;

            case R.id.btnDelete: deleteUser();
                break;

            case R.id.btnUpdate: updateUser();
                break;

            case R.id.btnSelectAll: showData(dbHandler.readAllInfo());
                break;
        }
    }

    private void updateUser() {
        String uname = uName.getText().toString();
        String pswd = pwd.getText().toString();
        if(!uname.equals("") && !pswd.equals("")){
            if(dbHandler.updateInfo(uname, pswd)) {
                Toast t = Toast.makeText(getApplicationContext(), "User Updated! ", Toast.LENGTH_LONG);
                t.show();
            }
            else{
                Toast t = Toast.makeText(getApplicationContext(), "User can't be Updated! ", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }

    private void deleteUser() {
        String uname = uName.getText().toString();
        if(!uname.equals("")){
            dbHandler.deleteInfo(uname);
        }
    }

    private void showData(List list) {
        //Log.i(c.getString(1), c.getString(2));
        for(int i = 0; i < list.size() ; i++){
            Log.i("name", list.get(i).toString());
        }
    }

    private void loginUser(){
        String uname = uName.getText().toString();
        String pswd = pwd.getText().toString();
        if(!uname.equals("")) {
            if( dbHandler.readInfo(uname, pswd)){
                Intent secondActivity = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(secondActivity);
            }
            else {
                Toast t = Toast.makeText(getApplicationContext(), "User is not existing!", Toast.LENGTH_LONG);
                t.show();
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Please input a user name", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void addUser(){
        String uname = uName.getText().toString();
        String pswd = pwd.getText().toString();
        if(!uname.equals("") && !pswd.equals("")){
            if(dbHandler.addInfo(uName.getText().toString(), pwd.getText().toString())){
                Toast t = Toast.makeText(getApplicationContext(), "Inserted a new user!", Toast.LENGTH_LONG);
                t.show();
            }
            else
            {
                Toast t = Toast.makeText(getApplicationContext(), "Can't insert the user!", Toast.LENGTH_LONG);
                t.show();
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
