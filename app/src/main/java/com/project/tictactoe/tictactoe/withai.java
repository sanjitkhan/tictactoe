package com.project.tictactoe.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class withai extends Activity implements View.OnClickListener {
    private Button aib0,aib1,aib2,aib3,aib4,aib5,aib6,aib7,aib8,ub,ab,ta,mm,nameb;
    Button[] buttonArray_ai = new Button[9];
    private TextView winloss,unametv,uscoretv,aiscoretv,ainametv;
    String uname;
    private int forkflag=0,flag=2,nextflag=0,blackflag=0;
    int p1score, p2score; //user=p1score, ai=p2score
    String p1s="", p2s="";
    private int player_ai=1,moves_ai=0,ss;
    List<Integer> usermoves = new ArrayList<Integer>();
    List<Integer> aimoves = new ArrayList<Integer>();
    private int[] box_number_ai={0,0,0,0,0,0,0,0,0};
    private int[][] win_sit={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withai);
        aib0=(Button) findViewById(R.id.ai1);
        aib1=(Button) findViewById(R.id.ai2);
        aib2=(Button) findViewById(R.id.ai3);
        aib3=(Button) findViewById(R.id.ai4);
        aib4=(Button) findViewById(R.id.ai5);
        aib5=(Button) findViewById(R.id.ai6);
        aib6=(Button) findViewById(R.id.ai7);
        aib7=(Button) findViewById(R.id.ai8);
        aib8=(Button) findViewById(R.id.ai9);
        buttonArray_ai[0] = aib0;
        buttonArray_ai[1] = aib1;
        buttonArray_ai[2] = aib2;
        buttonArray_ai[3] = aib3;
        buttonArray_ai[4] = aib4;
        buttonArray_ai[5] = aib5;
        buttonArray_ai[6] = aib6;
        buttonArray_ai[7] = aib7;
        buttonArray_ai[8] = aib8;
        buttonArray_ai[0].setOnClickListener(this);
        buttonArray_ai[1].setOnClickListener(this);
        buttonArray_ai[2].setOnClickListener(this);
        buttonArray_ai[3].setOnClickListener(this);
        buttonArray_ai[4].setOnClickListener(this);
        buttonArray_ai[5].setOnClickListener(this);
        buttonArray_ai[6].setOnClickListener(this);
        buttonArray_ai[7].setOnClickListener(this);
        buttonArray_ai[8].setOnClickListener(this);
        ss = buttonArray_ai[0].getId();
        //Log.e("button1", Integer.toString(ss));
        unametv=(TextView) findViewById(R.id.unameid);
        ainametv=(TextView) findViewById(R.id.ainame);
        uscoretv=(TextView) findViewById(R.id.uscoreid);
        aiscoretv=(TextView) findViewById(R.id.aiscoreid);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String p1sc = extras.getString("uscore");
            uscoretv.setText(p1sc);
            p1score=Integer.valueOf(p1sc);
            String p2sc = extras.getString("aiscore");
            aiscoretv.setText(p2sc);
            p2score=Integer.valueOf(p2sc);
            String nameflag = extras.getString("namecheck");
            if (nameflag.equals("yes")) { //show dialog
                final Dialog dial = new Dialog(this);
                dial.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dial.setContentView(R.layout.names_ai);
                dial.show();
                nameb = (Button) dial.findViewById(R.id.submit);
                nameb.setOnClickListener(new View.OnClickListener() { //user will play first
                    @Override
                    public void onClick(View v) {
                        EditText p1nameget = (EditText) dial.findViewById(R.id.inputp1);
                        if (p1nameget.getText().toString().trim().equals("")) {
                            p1nameget.setError("Player 1 name is required!");
                        } else {
                            uname = p1nameget.getText().toString();
                            unametv.setText(uname);
                            ainametv.setText("Computer");
                            dial.dismiss();
                            nextflag=1;
                        }
                    }
                });
            }
            else {
                uname = extras.getString("uname");
                unametv.setText(uname);
                ainametv.setText("Computer");
                nextflag=1;
            }
        }
        //if(nextflag==1) {
            final Dialog d = new Dialog(this);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.firstmove);
            d.show();

            ub=(Button) d.findViewById(R.id.userbutton);
            ub.setOnClickListener(new View.OnClickListener() { //user will play first

                @Override
                public void onClick(View v) {
                    flag=2;
                    d.dismiss();
                }
            });
            ab=(Button) d.findViewById(R.id.aibutton);
            ab.setOnClickListener(new View.OnClickListener() { //computer will play first

                @Override
                public void onClick(View v) {
                    flag = 1;
                    d.dismiss();
                    aifirstmove();
                }
            });
        //}
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
        Button b = (Button) v;
        if (v.isClickable()) {
            placemove(b);
            //aichance
            forkflag=0;
            if (aiwincheck(aimoves) == 0) { //check_ai for win move
                if (aiwincheck(usermoves) == 0) { //block opponent's win move
                    forkflag=1;
                    if (fork(aimoves, 1) == 0) {
                        if (fork(usermoves, 2) == 0) {
                            if (box_number_ai[4] == 0) //first center
                                placemove(buttonArray_ai[4]);
                            else if (box_number_ai[0] == 0) //then corner
                                placemove(buttonArray_ai[0]);
                            else if (box_number_ai[2] == 0)
                                placemove(buttonArray_ai[2]);
                            else if (box_number_ai[6] == 0)
                                placemove(buttonArray_ai[6]);
                            else if (box_number_ai[8] == 0)
                                placemove(buttonArray_ai[8]);
                            else if (box_number_ai[1] == 0) //then side
                                placemove(buttonArray_ai[1]);
                            else if (box_number_ai[3] == 0)
                                placemove(buttonArray_ai[3]);
                            else if (box_number_ai[5] == 0)
                                placemove(buttonArray_ai[5]);
                            else if (box_number_ai[7] == 0)
                                placemove(buttonArray_ai[7]);
                        }
                    }
                }
            }
        }
    }

    public void win_color_ai (Button x) {
        if(player_ai==1)
            x.setBackgroundResource(R.drawable.back_x_win);
        else
            x.setBackgroundResource(R.drawable.back_o_win);
    }

    public void placemove(Button ba) {
        int s = ba.getId() - ss; //box number
        if(player_ai==1) {
            if(flag==1) {
                new withai().addtoarr(aimoves, s);
            }
            else
                new withai().addtoarr(usermoves, s);
            ba.setBackgroundResource(R.drawable.back_x);
            box_number_ai[s]=1;
            if(moves_ai>=2)
                wincheck_ai(s);
            player_ai=2;
            if(moves_ai==4 && blackflag!=1) { //game is drawn
                gameover(0);
            }
        }
        else {
            if(flag==2) {
                new withai().addtoarr(aimoves, s);
            }
            else
                new withai().addtoarr(usermoves, s);
            ba.setBackgroundResource(R.drawable.back_o);
            box_number_ai[s]=2;
            moves_ai++;
            if(moves_ai>2)
                wincheck_ai(s);
            player_ai=1;
        }
        ba.setClickable(false);
    }

    public void aifirstmove() { //a random first move for the computer from among the 3 logical situations: corner, center and side
        int rmove = (int) (Math.random()*3);
        int rflag=0;
        if(rmove==0) {//corner
            do {
                int nmove = (int) (Math.random() * 4);
                if (nmove == 0) {
                    if (box_number_ai[0] == 0) {
                        placemove(buttonArray_ai[0]);
                        rflag = 1;
                    }
                } else if (nmove == 1) {
                    if (box_number_ai[2] == 0) {
                        placemove(buttonArray_ai[2]);
                        rflag = 1;
                    }
                } else if (nmove == 2) {
                    if (box_number_ai[6] == 0) {
                        placemove(buttonArray_ai[6]);
                        rflag = 1;
                    }
                } else {
                    if (box_number_ai[8] == 0) {
                        placemove(buttonArray_ai[8]);
                        rflag = 1;
                    }
                }
            }while(rflag==0);
        }
        else if (rmove == 1) { //center
            if (box_number_ai[4] == 0)
                placemove(buttonArray_ai[4]);
            else
                aifirstmove();
        }
        else { //side
            do {
                int nmove = (int) (Math.random() * 4);
                if (nmove == 0) {
                    if (box_number_ai[1] == 0) {
                        placemove(buttonArray_ai[1]);
                        rflag = 1;
                    }
                } else if (nmove == 1){
                    if (box_number_ai[3] == 0) {
                        placemove(buttonArray_ai[3]);
                        rflag = 1;
                    }
                } else if (nmove == 2){
                    if (box_number_ai[5] == 0) {
                        placemove(buttonArray_ai[5]);
                        rflag = 1;
                    }
                } else {
                    if (box_number_ai[7] == 0) {
                        placemove(buttonArray_ai[7]);
                        rflag = 1;
                    }
                }
            }while(rflag==0);
        }
    }

    public int aiwincheck(List<Integer> movesarr) {
        int k,i,j,size,next_move,sits=0;
        size=movesarr.size()-1;
        for(k=0;k<size;k++) {
            for(i=0;i<=7;i++) {
                for(j=0;j<2;j++) {
                    if(movesarr.get(k)==win_sit[i][j]) { //might be in a win situation
                        for(int l=j+1;l<=2;l++) {//checking if the other box of the win situation is matched
                            if(movesarr.get(k+1) == win_sit[i][l]) {
                                if(l==1 && j==0) {
                                    next_move = win_sit[i][2];
                                    if (box_number_ai[next_move] == 0) { //win situation matched
                                        if(forkflag==0) {
                                            placemove(buttonArray_ai[next_move]);
                                            return 1;
                                        } else {
                                            sits++;
                                        }
                                    }
                                } else if (l==2 && j==0) {
                                    next_move = win_sit[i][1];
                                    if (box_number_ai[next_move] == 0) { //win situation matched
                                        if (forkflag == 0) {
                                            placemove(buttonArray_ai[next_move]);
                                            return 1;
                                        } else {
                                            sits++;
                                        }
                                    }
                                } else if (l==2 && j==1) {
                                    next_move = win_sit[i][0];
                                    if (box_number_ai[next_move] == 0) { //win situation matched
                                        if(forkflag==0) {
                                            placemove(buttonArray_ai[next_move]);
                                            return 1;
                                        } else {
                                            sits++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return sits;
    }

    public int fork(List<Integer> movesarr, int p) {
        int emp[] = new int[9];
        int c=-1,pl;
        if(p==2) {
            if(player_ai==1)
                pl=2;
            else
                pl=1;
        }
        else
            pl=player_ai;
        for(int i=0;i<=8;i++) { //get remaining boxes
            if (box_number_ai[i] == 0)
                emp[++c]=i;
        }
        while(c>-1) { //checking remaining boxes
            box_number_ai[emp[c]]=pl; //testing for box_number_ai[c]
            addtoarr(movesarr,emp[c]);
            if(aiwincheck(movesarr)>1) {//fork situation available
                box_number_ai[emp[c]]=0;
                movesarr.remove(new Integer(emp[c]));
                placemove(buttonArray_ai[emp[c]]);
                return 1;
            }
            else { //fork situation unavailable. So, remove allocated values
                box_number_ai[emp[c]]=0;
                movesarr.remove(new Integer(emp[c]));
            }
            c--;
        }
        return 0; //fork situation unavailable
    }

    public void addtoarr(List<Integer> arr, int x) {
        int i;
        int size=arr.size();
        int zflag=0;
        for(i=0;i<size;i++) {
            if(x<arr.get(i)) {
                arr.add(i,x);
                zflag=1;
                break;
            }
        }

        if(zflag==0)
            arr.add(x);}

    public void wincheck_ai (int move) {
        int winflag = 0;
        switch (move) {
            case 0: {
                if (box_number_ai[1] == player_ai && box_number_ai[2] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[1]);
                    win_color_ai(buttonArray_ai[2]);
                    winflag = 1;
                }
                if (box_number_ai[3] == player_ai && box_number_ai[6] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[3]);
                    win_color_ai(buttonArray_ai[6]);
                    winflag = 1;
                }
                if (box_number_ai[4] == player_ai && box_number_ai[8] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                break;
            }
            case 1: {
                if (box_number_ai[0] == player_ai && box_number_ai[2] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[1]);
                    win_color_ai(buttonArray_ai[2]);
                    winflag = 1;
                }
                if (box_number_ai[4] == player_ai && box_number_ai[7] == player_ai) {
                    win_color_ai(buttonArray_ai[1]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[7]);
                    winflag = 1;
                }
                break;
            }
            case 2: {
                if (box_number_ai[0] == player_ai && box_number_ai[1] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[1]);
                    win_color_ai(buttonArray_ai[2]);
                    winflag = 1;
                }
                if (box_number_ai[5] == player_ai && box_number_ai[8] == player_ai) {
                    win_color_ai(buttonArray_ai[2]);
                    win_color_ai(buttonArray_ai[5]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                if (box_number_ai[4] == player_ai && box_number_ai[6] == player_ai) {
                    win_color_ai(buttonArray_ai[2]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[6]);
                    winflag = 1;
                }
                break;
            }
            case 3: {
                if (box_number_ai[0] == player_ai && box_number_ai[6] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[3]);
                    win_color_ai(buttonArray_ai[6]);
                    winflag = 1;
                }
                if (box_number_ai[4] == player_ai && box_number_ai[5] == player_ai) {
                    win_color_ai(buttonArray_ai[3]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[5]);
                    winflag = 1;
                }
                break;
            }
            case 4: {
                if (box_number_ai[1] == player_ai && box_number_ai[7] == player_ai) {
                    win_color_ai(buttonArray_ai[1]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[7]);
                    winflag = 1;
                }
                if (box_number_ai[0] == player_ai && box_number_ai[8] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                if (box_number_ai[3] == player_ai && box_number_ai[5] == player_ai) {
                    win_color_ai(buttonArray_ai[3]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[5]);
                    winflag = 1;
                }
                if (box_number_ai[2] == player_ai && box_number_ai[6] == player_ai) {
                    win_color_ai(buttonArray_ai[2]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[6]);
                    winflag = 1;
                }
                break;
            }
            case 5: {
                if (box_number_ai[2] == player_ai && box_number_ai[8] == player_ai) {
                    win_color_ai(buttonArray_ai[2]);
                    win_color_ai(buttonArray_ai[5]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                if (box_number_ai[3] == player_ai && box_number_ai[4] == player_ai) {
                    win_color_ai(buttonArray_ai[3]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[5]);
                    winflag = 1;
                }
                break;
            }
            case 6: {
                if (box_number_ai[0] == player_ai && box_number_ai[3] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[3]);
                    win_color_ai(buttonArray_ai[6]);
                    winflag = 1;
                }
                if (box_number_ai[2] == player_ai && box_number_ai[4] == player_ai) {
                    win_color_ai(buttonArray_ai[2]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[6]);
                    winflag = 1;
                }
                if (box_number_ai[7] == player_ai && box_number_ai[8] == player_ai) {
                    win_color_ai(buttonArray_ai[6]);
                    win_color_ai(buttonArray_ai[7]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                break;
            }
            case 7: {
                if (box_number_ai[1] == player_ai && box_number_ai[4] == player_ai) {
                    win_color_ai(buttonArray_ai[1]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[7]);
                    winflag = 1;
                }
                if (box_number_ai[6] == player_ai && box_number_ai[8] == player_ai) {
                    win_color_ai(buttonArray_ai[6]);
                    win_color_ai(buttonArray_ai[7]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                break;
            }
            case 8: {
                if (box_number_ai[6] == player_ai && box_number_ai[7] == player_ai) {
                    win_color_ai(buttonArray_ai[6]);
                    win_color_ai(buttonArray_ai[7]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                if (box_number_ai[2] == player_ai && box_number_ai[5] == player_ai) {
                    win_color_ai(buttonArray_ai[2]);
                    win_color_ai(buttonArray_ai[5]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                if (box_number_ai[0] == player_ai && box_number_ai[4] == player_ai) {
                    win_color_ai(buttonArray_ai[0]);
                    win_color_ai(buttonArray_ai[4]);
                    win_color_ai(buttonArray_ai[8]);
                    winflag = 1;
                }
                break;
            }
        }
        //game over
        if (winflag == 1) {
            blackflag=1;
            gameover(1);
        }
    }

    public void gameover(int s) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gameover);
        winloss=(TextView) dialog.findViewById(R.id.text_wonlost);
        ta=(Button) dialog.findViewById(R.id.tryagain_button);
        mm=(Button) dialog.findViewById(R.id.mainmenu_button);
        for(int i=0;i<=8;i++) {
            if (box_number_ai[i] == 0)
                box_number_ai[i] = 3;
        }
        if(s==0) { //draw
            winloss.setText("It is a Draw!!");
            ta.setText("Try Again");
            p1s=Integer.toString(p1score);
            p2s=Integer.toString(p2score);
        }
        else if((player_ai==1 && flag==1)||(player_ai==2 && flag==2)) { //ai won
            winloss.setText("You Lost :(");
            ta.setText("Try Again");
            p2score++;
            p1s=Integer.toString(p1score);
            p2s=Integer.toString(p2score);

        }
        else { //user won
            winloss.setText("You Won!!");
            ta.setText("Play Again");
            p1score++;
            p1s=Integer.toString(p1score);
            p2s=Integer.toString(p2score);
        }

        dialog.show();

        ta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =  new Intent (withai.this, withai.class);
                intent.putExtra("uscore",p1s);
                intent.putExtra("aiscore",p2s);
                intent.putExtra("namecheck","no");
                intent.putExtra("uname",uname);
                startActivity(intent);
                finish();
            }
        });

        mm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(withai.this, mainActivity.class));
                finish();
            }
        });
    }
}