package com.aleksus.handtohand1.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.aleksus.handtohand1.DefaultCallback;
import com.aleksus.handtohand1.Defaults;
import com.aleksus.handtohand1.R;
import com.aleksus.handtohand1.SocialCallback;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{
    private TextView restoreLink;
    private EditText identityField, passwordField;
    Button registerButton;
    Button loginButton;
    private CheckBox rememberLoginBox;
    private Button facebookButton;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login);

        initUI();

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );
        Backendless.UserService.isValidLogin( new DefaultCallback<Boolean>( this ) {
            @Override
            public void handleResponse( Boolean isValidLogin )
            {
                if( isValidLogin && Backendless.UserService.CurrentUser() == null )
                {
                    String currentUserId = Backendless.UserService.loggedInUser();

                    if( !currentUserId.equals( "" ) )
                    {
                        Backendless.UserService.findById( currentUserId, new DefaultCallback<BackendlessUser>( LoginActivity.this, "Заходим..." )
                        {
                            @Override
                            public void handleResponse( BackendlessUser currentUser )
                            {
                                super.handleResponse( currentUser );
                                Backendless.UserService.setCurrentUser( currentUser );
                                startActivity( new Intent( getBaseContext(), ProfileActivity.class ) );
                                finish();
                            }
                        } );
                    }
                }

                super.handleResponse( isValidLogin );
            }
        });
    }

    private void initUI()
    {
        registerButton = (Button) findViewById( R.id.registerButton );
        restoreLink = (TextView) findViewById( R.id.restoreLink );
        identityField = (EditText) findViewById( R.id.identityField );
        passwordField = (EditText) findViewById( R.id.passwordField );
        loginButton = (Button) findViewById( R.id.loginButton );
        rememberLoginBox = (CheckBox) findViewById( R.id.rememberLoginBox );
        facebookButton = (Button) findViewById( R.id.loginFacebookButton );

        String tempString = getResources().getString( R.string.register_text );
        SpannableString underlinedContent = new SpannableString( tempString );
        underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
        registerButton.setText( underlinedContent );
        tempString = getResources().getString( R.string.restore_link );
        underlinedContent = new SpannableString( tempString );
        underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
        restoreLink.setText( underlinedContent );

        loginButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onLoginButtonClicked();
            }
        } );

        registerButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onRegisterButtonClicked();
            }
        } );

        restoreLink.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onRestoreLinkClicked();
            }
        } );

        facebookButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onLoginWithFacebookButtonClicked();
            }
        } );
    }

    public void onLoginButtonClicked()
    {
        String identity = identityField.getText().toString();
        String password = passwordField.getText().toString();
        boolean rememberLogin = rememberLoginBox.isChecked();

        Backendless.UserService.login( identity, password, new DefaultCallback<BackendlessUser>( LoginActivity.this )
        {
            public void handleResponse( BackendlessUser backendlessUser )
            {
                super.handleResponse( backendlessUser );
                startActivity( new Intent( LoginActivity.this, ProfileActivity.class ) );
            }
        }, rememberLogin );
    }

    public void onRegisterButtonClicked()
    {
        startActivity( new Intent( this, RegisterActivity.class ) );
    }

    public void onRestoreLinkClicked()
    {
        startActivity( new Intent( this, RestorePasswordActivity.class ) );
    }

    public void onLoginWithFacebookButtonClicked()
    {
        Map<String, String> facebookFieldsMapping = new HashMap<>();
        facebookFieldsMapping.put( "name", "name" );
        facebookFieldsMapping.put( "gender", "gender" );
        facebookFieldsMapping.put( "email", "email" );

        List<String> facebookPermissions = new ArrayList<>();
        facebookPermissions.add( "email" );

        Backendless.UserService.loginWithFacebook( LoginActivity.this, null, facebookFieldsMapping, facebookPermissions, new SocialCallback<BackendlessUser>( LoginActivity.this )
        {
            @Override
            public void handleResponse( BackendlessUser backendlessUser )
            {
                startActivity( new Intent( getBaseContext(), ProfileActivity.class ) );
                finish();
            }
        } );
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.loginButton:
//                String identity = identityField.getText().toString();
//                String password = passwordField.getText().toString();
//                boolean rememberLogin = rememberLoginBox.isChecked();
//
//                Backendless.UserService.activity_login( identity, password, new DefaultCallback<BackendlessUser>( LoginActivity.this )
//                {
//                    public void handleResponse( BackendlessUser backendlessUser )
//                    {
//                        super.handleResponse( backendlessUser );
//                        startActivity( new Intent( LoginActivity.this, ProfileActivity.class ) );
//                    }
//                }, rememberLogin );
//                break;
//            case R.id.registerButton:
//                startActivity( new Intent( this, RegisterActivity.class ) );
//                break;
//            case R.id.restoreLink:
//                startActivity( new Intent( this, RestorePasswordActivity.class ) );
//                break;
//            case R.id.loginFacebookButton:
//                Map<String, String> facebookFieldsMapping = new HashMap<>();
//                facebookFieldsMapping.put( "name", "name" );
//                facebookFieldsMapping.put( "gender", "gender" );
//                facebookFieldsMapping.put( "email", "email" );
//
//                List<String> facebookPermissions = new ArrayList<>();
//                facebookPermissions.add( "email" );
//
//                Backendless.UserService.loginWithFacebook( LoginActivity.this, null, facebookFieldsMapping, facebookPermissions, new SocialCallback<BackendlessUser>( LoginActivity.this )
//                {
//                    @Override
//                    public void handleResponse( BackendlessUser backendlessUser )
//                    {
//                        startActivity( new Intent( getBaseContext(), ProfileActivity.class ) );
//                        finish();
//                    }
//                } );
//                break;
//            default:
//                break;
//        }
//    }
}