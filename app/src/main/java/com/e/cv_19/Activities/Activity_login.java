package com.e.cv_19.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerLogin;
import com.e.cv_19.R;


public class Activity_login extends AppCompatActivity {

    private EditText campo_email;
    private EditText campo_password;
    private ControllerLogin Controller = new ControllerLogin(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        campo_email = findViewById(R.id.Editviewemail);
        campo_password = findViewById(R.id.editTextpassword);
        Controller.Auto_Login();
    }


    @Override
    public void onStart() {
        super.onStart();
    }




    public void Login_is_clicked(View view) {



        String username=campo_email.getText().toString();
        String password=campo_password.getText().toString();


        if (!Controller.isValidMail(username)) {
            Toast.makeText(getApplicationContext(), "Inserire una mail valida", Toast.LENGTH_SHORT).show();
        } else if (!Controller.isValidPassword(password)) {
            Toast.makeText(getApplicationContext(), "La password deve avere almeno 4 caratteri", Toast.LENGTH_SHORT).show();
        } else {
            Controller.Effettua_login(username,password);
        }


    }

    public void Registrati_is_clicked(View view) {
        Controller.Mostra_activity_registrazione();
    }

    public void Recupera_is_clicked(View view) {
        Controller.Mostra_activity_recupero_password();
    }



}
