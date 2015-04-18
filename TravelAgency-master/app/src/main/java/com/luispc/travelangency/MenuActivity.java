package com.luispc.travelangency;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void pasarActividadInfo(View v){

        Intent act = new Intent(this, my.class);
        startActivity(act);
    }

    // Initialices the items on the upper action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    // Method to handle Clicks on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get the event source id
        int id = item.getItemId();

        // Handle presses on the action bar items
        switch (id) {
            case R.id.action_login:
                Intent loginAct = new Intent(MenuActivity.this,GmailAuth.class);
                startActivity(loginAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
