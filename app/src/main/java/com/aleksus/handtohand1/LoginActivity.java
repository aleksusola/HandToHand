package com.aleksus.handtohand1;

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

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{
    private TextView registerLink, restoreLink;
    private EditText identityField, passwordField;
    Button loginButton;
    private CheckBox rememberLoginBox;
    private Button facebookButton;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login );

        initUI();

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( this, Defaults.APPLICATION_ID, Defaults.API_KEY );

        Backendless.UserService.isValidLogin( new DefaultCallback<Boolean>( this )
        {
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
        registerLink = (TextView) findViewById( R.id.registerLink );
        restoreLink = (TextView) findViewById( R.id.restoreLink );
        identityField = (EditText) findViewById( R.id.identityField );
        passwordField = (EditText) findViewById( R.id.passwordField );
        loginButton = (Button) findViewById( R.id.loginButton );
        rememberLoginBox = (CheckBox) findViewById( R.id.rememberLoginBox );
        facebookButton = (Button) findViewById( R.id.loginFacebookButton );

        String tempString = getResources().getString( R.string.register_text );
        SpannableString underlinedContent = new SpannableString( tempString );
        underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
        registerLink.setText( underlinedContent );
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

        registerLink.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onRegisterLinkClicked();
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

    public void onRegisterLinkClicked()
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
}