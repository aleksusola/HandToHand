package com.aleksus.handtohand1.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleksus.handtohand1.DefaultCallback;
import com.aleksus.handtohand1.MyAdapter;
import com.aleksus.handtohand1.R;
import com.aleksus.handtohand1.RecyclerItem;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<RecyclerItem> listItems;
    private int c;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile);
//        BackendlessUser user = Backendless.UserService.CurrentUser();
//        if( user != null ) {
//            String name = (String) user.getProperty( "name" );
//            TextView textView = (TextView) findViewById(R.id.textView1);
//            textView.setText("Добро пожаловать, " + name);
//            String email = (String) user.getProperty( "email" );
//            TextView textView1 = (TextView) findViewById(R.id.textView3);
//            textView1.setText("Электронная почта: " + email);
//            Double phone = (Double) user.getProperty( "phone" );
//            TextView textView2 = (TextView) findViewById(R.id.textView4);
//            textView2.setText("Телефон: +" + phone);
//        }
//        else {
//            Toast.makeText( ProfileActivity.this,
//                    "User hasn't been logged",
//                    Toast.LENGTH_SHORT ).show();
//        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        Backendless.Persistence.of( "ads_users" ).find( new AsyncCallback<List<Map>>(){
            @Override
            public void handleResponse(final List<Map> foundAds ) {


                Backendless.Data.of( "ads_users" ).getObjectCount( new AsyncCallback<Integer>() {
                @Override
                public void handleResponse( Integer cnt ) {

                    for (int i = 0; i<cnt; i++) {
                        listItems.add(new RecyclerItem( foundAds.get(i).get( "name" ).toString(), "Автор: " + foundAds.get(i).get("ownerId").toString(), "Цена: " + foundAds.get(i).get( "price" ).toString() ));
//                        listItems.add(new RecyclerItem( foundAds.get(i).get( "name" ).toString(), "Автор: " + Backendless.UserService.findById(foundAds.get(i).get("ownerId").toString()).getProperty("name").toString(), "Цена: " + foundAds.get(i).get( "price" ).toString() ));
                    }
                    //Set adapter
                    adapter = new MyAdapter(listItems, ProfileActivity.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void handleFault( BackendlessFault backendlessFault )
                {
                    Log.i( "MYAPP", "error - " + backendlessFault.getMessage() );
                }
            } );
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i( "MYAPP", "error");
            }
        });

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
                Backendless.UserService.logout( new DefaultCallback<Void>( this ) {
                    @Override
                    public void handleResponse( Void response ) {
                        super.handleResponse( response );
                        startActivity( new Intent( ProfileActivity.this, LoginActivity.class ) );
                        finish();
                    }

                    @Override
                    public void handleFault( BackendlessFault fault ) {
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
}
