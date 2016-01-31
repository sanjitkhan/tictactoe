package com.project.tictactoe.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class  newalgo1 extends Activity implements View.OnClickListener {
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,taa,mmm,nameb;
    private TextView losswin,p1nametv,p2nametv,p1sctv,p2sctv;
    Button[] buttonArray = new Button[9];
    private int player=1, p1_chance=0, p2_chance=0,blackflag=0;
    private int box_number[]={0,0,0,0,0,0,0,0,0};
    private String p1name, p2name;
    private int p1score, p2score,ss;
    String p1s="", p2s="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newalgo1);
        p1nametv = (TextView) findViewById(R.id.p1name);
        p1sctv = (TextView) findViewById(R.id.p1score);
        p2nametv = (TextView) findViewById(R.id.p2name);
        p2sctv = (TextView) findViewById(R.id.p2score);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        buttonArray[0] = b1;
        buttonArray[1] = b2;
        buttonArray[2] = b3;
        buttonArray[3] = b4;
        buttonArray[4] = b5;
        buttonArray[5] = b6;
        buttonArray[6] = b7;
        buttonArray[7] = b8;
        buttonArray[8] = b9;
        buttonArray[0].setOnClickListener(this);
        buttonArray[1].setOnClickListener(this);
        buttonArray[2].setOnClickListener(this);
        buttonArray[3].setOnClickListener(this);
        buttonArray[4].setOnClickListener(this);
        buttonArray[5].setOnClickListener(this);
        buttonArray[6].setOnClickListener(this);
        buttonArray[7].setOnClickListener(this);
        buttonArray[8].setOnClickListener(this);
        ss = buttonArray[0].getId();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String p1sc = extras.getString("p1");
            p1score = Integer.valueOf(p1sc);
            p1sctv.setText(p1sc);
            String p2sc = extras.getString("p2");
            p2score = Integer.valueOf(p2sc);
            p2sctv.setText(p2sc);
            String nameflag = extras.getString("namecheck");
            if (nameflag.equals("yes")) { //show dialog
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.names);
                dialog.show();
                nameb = (Button) dialog.findViewById(R.id.submit);
                nameb.setOnClickListener(new View.OnClickListener() { //user will play first
                    @Override
                    public void onClick(View v) {
                        EditText p1nameget = (EditText) dialog.findViewById(R.id.inputp1);
                        EditText p2nameget = (EditText) dialog.findViewById(R.id.inputp2);
                        if (p1nameget.getText().toString().trim().equals("")) {
                            p1nameget.setError("Player 1 name is required!");
                        } else if (p2nameget.getText().toString().trim().equals("")) {
                            p2nameget.setError("Player 2 name is required!");
                        } else {
                            p1name = p1nameget.getText().toString();
                            p1nametv.setText(p1name);
                            p2name = p2nameget.getText().toString();
                            p2nametv.setText(p2name);
                            dialog.dismiss();
                        }
                    }
                });
            }
            else {
                p1name = extras.getString("p1name");
                p2name = extras.getString("p2name");
                p1nametv.setText(p1name);
                p2nametv.setText(p2name);
            }
        }
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

    @Override
    public void onClick(View v) {
        int winflag;
        int s = v.getId() - ss; //box number
        if(player==1 && v.isClickable()){
            v.setBackgroundResource(R.drawable.back_x);
            p1_chance++;
            box_number[s]=1;
            v.setClickable(false);
            if(p1_chance>2) {
                wincheck(1, s);
            }
            if(p1_chance==5 && blackflag!=1)
                gameover(0);
            player=2;
        }
        else if(player==2 && v.isClickable()) {
            v.setBackgroundResource(R.drawable.back_o);
            p2_chance++;
            box_number[s]=2;
            v.setClickable(false);
            if(p2_chance>2)
                wincheck(2,s);
            player=1;
        }
    }

    public void wincheck (int player, int move) {
        int winflag=0;
        switch(move) {
            case 0: {
                if(box_number[1] == player && box_number[2] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[1]);
                    win_color(buttonArray[2]);
                    winflag=1;
                }
                if(box_number[3] == player && box_number[6] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[3]);
                    win_color(buttonArray[6]);
                    winflag=1;
                }
                if(box_number[4] == player && box_number[8] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                break;
            }
            case 1: {
                if(box_number[0] == player && box_number[2] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[1]);
                    win_color(buttonArray[2]);
                    winflag=1;
                }
                if(box_number[4] == player && box_number[7] == player) {
                    win_color(buttonArray[1]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[7]);
                    winflag=1;
                }
                break;
            }
            case 2: {
                if(box_number[0] == player && box_number[1] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[1]);
                    win_color(buttonArray[2]);
                    winflag=1;
                }
                if(box_number[5] == player && box_number[8] == player) {
                    win_color(buttonArray[2]);
                    win_color(buttonArray[5]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                if(box_number[4] == player && box_number[6] == player) {
                    win_color(buttonArray[2]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[6]);
                    winflag=1;
                }
                break;
            }
            case 3: {
                if(box_number[0] == player && box_number[6] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[3]);
                    win_color(buttonArray[6]);
                    winflag=1;
                }
                if(box_number[4] == player && box_number[5] == player) {
                    win_color(buttonArray[3]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[5]);
                    winflag=1;
                }
                break;
            }
            case 4: {
                if(box_number[1] == player && box_number[7] == player) {
                    win_color(buttonArray[1]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[7]);
                    winflag=1;
                }
                if(box_number[0] == player && box_number[8] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                if(box_number[3] == player && box_number[5] == player) {
                    win_color(buttonArray[3]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[5]);
                    winflag=1;
                }
                if(box_number[2] == player && box_number[6] == player) {
                    win_color(buttonArray[2]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[6]);
                    winflag=1;
                }
                break;
            }
            case 5: {
                if(box_number[2] == player && box_number[8] == player) {
                    win_color(buttonArray[2]);
                    win_color(buttonArray[5]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                if(box_number[3] == player && box_number[4] == player) {
                    win_color(buttonArray[3]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[5]);
                    winflag=1;
                }
                break;
            }
            case 6: {
                if(box_number[0] == player && box_number[3] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[3]);
                    win_color(buttonArray[6]);
                    winflag=1;
                }
                if(box_number[2] == player && box_number[4] == player) {
                    win_color(buttonArray[2]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[6]);
                    winflag=1;
                }
                if(box_number[7] == player && box_number[8] == player) {
                    win_color(buttonArray[6]);
                    win_color(buttonArray[7]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                break;
            }
            case 7: {
                if(box_number[1] == player && box_number[4] == player) {
                    win_color(buttonArray[1]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[7]);
                    winflag=1;
                }
                if(box_number[6] == player && box_number[8] == player) {
                    win_color(buttonArray[6]);
                    win_color(buttonArray[7]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                break;
            }
            case 8: {
                if(box_number[6] == player && box_number[7] == player) {
                    win_color(buttonArray[6]);
                    win_color(buttonArray[7]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                if(box_number[2] == player && box_number[5] == player) {
                    win_color(buttonArray[2]);
                    win_color(buttonArray[5]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                if(box_number[0] == player && box_number[4] == player) {
                    win_color(buttonArray[0]);
                    win_color(buttonArray[4]);
                    win_color(buttonArray[8]);
                    winflag=1;
                }
                break;
            }
        }
        if(winflag==1) {
            gameover(1);
            blackflag = 1;
        }
    }

    public void win_color (Button x) {
        if(player==1)
            x.setBackgroundResource(R.drawable.back_x_win);
        else
            x.setBackgroundResource(R.drawable.back_o_win);
    }

    public void gameover(int s) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gameover);
        losswin=(TextView) dialog.findViewById(R.id.text_wonlost);
        taa=(Button) dialog.findViewById(R.id.tryagain_button);
        mmm=(Button) dialog.findViewById(R.id.mainmenu_button);
        if(s==0) { //draw
            losswin.setText("It is a Draw!!");
            taa.setText("Play Again");
            p1s=Integer.toString(p1score);
            p2s=Integer.toString(p2score);
        }
        else if(player==1) { //Player 1 won
            String str = p1name + " wins!!";
            losswin.setText(str);
            taa.setText("Play Again");
            p1score++;
            p1s=Integer.toString(p1score);
            p2s=Integer.toString(p2score);
        }
        else { //Player 2 won
            String str = p2name + " wins!!";
            losswin.setText(str);
            taa.setText("Play Again");
            p2score++;
            p1s=Integer.toString(p1score);
            p2s=Integer.toString(p2score);
        }
        p1sctv.setText(p1s);
        p2sctv.setText(p2s);

        dialog.show();

        taa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =  new Intent (newalgo1.this, newalgo1.class);
                intent.putExtra("p1", p1s);
                intent.putExtra("p2",p2s);
                intent.putExtra("namecheck","no");
                intent.putExtra("p1name",p1name);
                intent.putExtra("p2name",p2name);
                startActivity(intent);
                finish();
            }
        });

        mmm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(newalgo1.this, mainActivity.class));
                finish();
            }
        });
    }
}

