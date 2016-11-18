package jnu.mcl.scheduler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.UserModel;
import jnu.mcl.scheduler.service.UserService;
import jnu.mcl.scheduler.util.SharedPreferenceUtil;

public class LoginActivity extends AppCompatActivity {

    private UserService userService = UserService.getInstance();
    private UserModel user = null;

    private EditText idEditText, nicknameEditText;
    private Button loginButton, signupButton;

    private String id, nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idEditText = (EditText) findViewById(R.id.idEditText);
        nicknameEditText = (EditText) findViewById(R.id.nicknameEditText);

        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                id = idEditText.getText().toString();
                nickname = nicknameEditText.getText().toString();
                user = userService.getUser(id, nickname);
                if (user == null) {
                    Toast.makeText(LoginActivity.this, "ID, nickname을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferenceUtil.putSharedPreference(LoginActivity.this, "id", id);
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                }catch(IOException e){}
            }
        });

        signupButton.setOnClickListener (new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "ㅎㅇㅎㅇㅎㅇ", Toast.LENGTH_SHORT).show();
                id = idEditText.getText().toString();
                nickname = nicknameEditText.getText().toString();
                try {
                    if (userService.getUser(id) == null) {
                        userService.addUser(id, nickname);
                        String message = "가입 성공, ID : " + id + "," + "nickname : " + nickname;
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "ID 중복입니다.", Toast.LENGTH_SHORT).show();
                    }
                }catch(IOException e){}
            }
        });

    }
}