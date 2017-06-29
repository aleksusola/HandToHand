package com.aleksus.handtohand1.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.aleksus.handtohand1.DefaultCallback;
import com.aleksus.handtohand1.Defaults;
import com.aleksus.handtohand1.R;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );
        LinearLayout linearAnim = (LinearLayout) findViewById(R.id.linearAnim);
        Animation anim = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.combo);
        linearAnim.startAnimation(anim);
        Backendless.UserService.isValidLogin( new DefaultCallback<Boolean>( this ) {
            @Override
            public void handleResponse( Boolean isValidLogin ) {
                if( isValidLogin && Backendless.UserService.CurrentUser() == null ) {
                    String currentUserId = Backendless.UserService.loggedInUser();

                    if( !currentUserId.equals( "" ) ) {

                        Backendless.UserService.findById( currentUserId, new DefaultCallback<BackendlessUser>( SplashScreenActivity.this, "Заходим..." ) {

                            @Override
                            public void handleResponse( BackendlessUser currentUser ) {

                                super.handleResponse( currentUser );
                                Backendless.UserService.setCurrentUser( currentUser );
                                Thread thread = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            TimeUnit.MILLISECONDS.sleep(3000);
                                            startActivity( new Intent( getBaseContext(), ProfileActivity.class ) );
                                            finish();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                thread.start();
                            }
                        } );
                    }

                }

                super.handleResponse( isValidLogin );
            }
        });
//        LinearLayout linearAnim = (LinearLayout) findViewById(R.id.linearAnim);
//        Animation anim = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.combo);
//        linearAnim.startAnimation(anim);
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(3000);
//                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        thread.start();
    }
}