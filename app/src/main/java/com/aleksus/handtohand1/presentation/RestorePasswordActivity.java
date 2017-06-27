
package com.aleksus.handtohand1.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aleksus.handtohand1.DefaultCallback;
import com.aleksus.handtohand1.R;
import com.backendless.Backendless;

public class RestorePasswordActivity extends Activity
{
  private Button restorePasswordButton;
  private EditText loginField;

  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_restore_password);

    initUI();
  }

  private void initUI()
  {
    restorePasswordButton = (Button) findViewById( R.id.restorePasswordButton );
    loginField = (EditText) findViewById( R.id.loginField );

    restorePasswordButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRestorePasswordButtonClicked();
      }
    } );
  }

  public void onRestorePasswordButtonClicked()
  {
    String login = loginField.getText().toString();
    Backendless.UserService.restorePassword( login, new DefaultCallback<Void>( this )
    {
      @Override
      public void handleResponse( Void response )
      {
        super.handleResponse( response );
        startActivity( new Intent( RestorePasswordActivity.this, PasswordRecoveryActivity.class ) );
        finish();
      }
    } );
  }
}
                