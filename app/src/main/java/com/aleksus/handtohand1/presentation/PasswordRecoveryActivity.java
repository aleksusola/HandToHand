
package com.aleksus.handtohand1.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aleksus.handtohand1.R;

public class PasswordRecoveryActivity extends AppCompatActivity {
    private Button loginButton;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_password_recovery_requested);
        initUI();
    }

    private void initUI() {
        loginButton = (Button) findViewById( R.id.loginButton );
        loginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                onLoginButtonClicked();
            }
        } );
    }

    public void onLoginButtonClicked() {
        startActivity( new Intent( this, LoginActivity.class ) );
        finish();
    }
}
                