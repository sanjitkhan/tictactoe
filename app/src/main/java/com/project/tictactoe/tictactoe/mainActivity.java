package com.project.tictactoe.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class mainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newgamevshuman(View view) {
        Intent intent = new Intent(this, newalgo1.class);
        intent.putExtra("p1","0");
        intent.putExtra("p2","0");
        intent.putExtra("namecheck","yes");
        startActivity(intent);
    }

    public void newgamevsai(View view) {
        Intent intent = new Intent(this, withai.class);
        intent.putExtra("uscore","0");
        intent.putExtra("aiscore","0");
        intent.putExtra("namecheck","yes");
        startActivity(intent);
    }

    public void timetoleave(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void multiplayer(View view) {
        Intent intent = new Intent(this,multiplayer.class);
        startActivity(intent);
    }
}
