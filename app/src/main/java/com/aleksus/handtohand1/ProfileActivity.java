package com.aleksus.handtohand1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.exceptions.BackendlessFault;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_search;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.profile);

//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText("Добро пожаловать в " + name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(ProfileActivity.this, getString(R.string.action_settings), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                Toast.makeText(ProfileActivity.this, getString(R.string.action_about), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_exit:
                Backendless.UserService.logout( new DefaultCallback<Void>( this )
                {
                    @Override
                    public void handleResponse( Void response )
                    {
                        super.handleResponse( response );
                        startActivity( new Intent( ProfileActivity.this, LoginActivity.class ) );
                        finish();
                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        if( fault.getCode().equals( "3023" ) ) // Unable to logout: not logged in (session expired, etc.)
                            handleResponse( null );
                        else
                            super.handleFault( fault );
                    }
                } );
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        startActivity( new Intent( this, SearchActivity.class ) );
    }
}
