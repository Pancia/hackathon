package com.turingsarmy.hackathon.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.turingsarmy.hackathon.server.AsyncJsonRequestManager;
import com.turingsarmy.hackathon.server.HackMap;
import com.turingsarmy.hackathon.server.MyFutureTask;
import com.turingsarmy.hackathon.R;

import java.util.Arrays;
import java.util.Random;

public class PlayGameActivityMM extends ActionBarActivity {

    private static final String TAG = PlayGameActivityMM.class.getSimpleName();
    private int[] guess = new int[3];
    private int[] solution = new int[3];

    private Button red0;
    private Button blu0;
    private Button grn0;
    private Button red1;
    private Button blu1;
    private Button grn1;
    private Button red2;
    private Button blu2;
    private Button grn2;
    private Button red3;
    private Button blu3;
    private Button grn3;
    private Button red4;
    private Button blu4;
    private Button grn4;
    private Button red5;
    private Button blu5;
    private Button grn5;
    private Button red6;
    private Button blu6;
    private Button grn6;
    private Button red7;
    private Button blu7;
    private Button grn7;
    private Button red8;
    private Button blu8;
    private Button grn8;
    private Button red9;
    private Button blu9;
    private Button grn9;

    private TextView ges0;
    private TextView ges1;
    private TextView ges2;
    private TextView ges3;
    private TextView ges4;
    private TextView ges5;
    private TextView ges6;
    private TextView ges7;
    private TextView ges8;
    private TextView ges9;

    private String textSub0 = "SUBMIT";
    private String textSub1 = "SUBMIT";
    private String textSub2 = "SUBMIT";
    private String textSub3 = "SUBMIT";
    private String textSub4 = "SUBMIT";
    private String textSub5 = "SUBMIT";
    private String textSub6 = "SUBMIT";
    private String textSub7 = "SUBMIT";
    private String textSub8 = "SUBMIT";
    private String textSub9 = "SUBMIT";

    private String red = "#ff000a";
    private String blu = "#1100ff";
    private String grn = "#1dc319";

    private String rTxt = "#ffff8c6b";
    private String bTxt = "#0081ff";
    private String gTxt = "#36ff00";

    private String redT = "Ω";
    private String grnT = "β";
    private String bluT = "Σ";

    private String home = "Eight";

    private int currentRow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);

        final int seed = (int) System.currentTimeMillis();

        Random random = new Random(seed);
        int rand1, rand2, rand3;

        rand1 = random.nextInt()%3;
        rand2 = random.nextInt()%3;
        rand3 = random.nextInt()%3;

        while (rand1<0){
            rand1 = random.nextInt()%3;
        }

        while (rand2<0){
            rand2 = random.nextInt()%3;
        }

        while (rand3<0){
            rand3 = random.nextInt()%3;
        }

        solution[0] = rand1;
        solution[1] = rand2;
        solution[2] = rand3;

        red0 = (Button) findViewById(R.id.activity_mm_red0);
        blu0 = (Button) findViewById(R.id.activity_mm_blu0);
        grn0 = (Button) findViewById(R.id.activity_mm_grn0);

        red1 = (Button) findViewById(R.id.activity_mm_red1);
        blu1 = (Button) findViewById(R.id.activity_mm_blu1);
        grn1 = (Button) findViewById(R.id.activity_mm_grn1);

        red2 = (Button) findViewById(R.id.activity_mm_red2);
        blu2 = (Button) findViewById(R.id.activity_mm_blu2);
        grn2 = (Button) findViewById(R.id.activity_mm_grn2);

        red3 = (Button) findViewById(R.id.activity_mm_red3);
        blu3 = (Button) findViewById(R.id.activity_mm_blu3);
        grn3 = (Button) findViewById(R.id.activity_mm_grn3);

        red4 = (Button) findViewById(R.id.activity_mm_red4);
        blu4 = (Button) findViewById(R.id.activity_mm_blu4);
        grn4 = (Button) findViewById(R.id.activity_mm_grn4);

        red5 = (Button) findViewById(R.id.activity_mm_red5);
        blu5 = (Button) findViewById(R.id.activity_mm_blu5);
        grn5 = (Button) findViewById(R.id.activity_mm_grn5);

        red6 = (Button) findViewById(R.id.activity_mm_red6);
        blu6 = (Button) findViewById(R.id.activity_mm_blu6);
        grn6 = (Button) findViewById(R.id.activity_mm_grn6);

        red7 = (Button) findViewById(R.id.activity_mm_red7);
        blu7 = (Button) findViewById(R.id.activity_mm_blu7);
        grn7 = (Button) findViewById(R.id.activity_mm_grn7);

        red8 = (Button) findViewById(R.id.activity_mm_red8);
        blu8 = (Button) findViewById(R.id.activity_mm_blu8);
        grn8 = (Button) findViewById(R.id.activity_mm_grn8);

        red9 = (Button) findViewById(R.id.activity_mm_red9);
        blu9 = (Button) findViewById(R.id.activity_mm_blu9);
        grn9 = (Button) findViewById(R.id.activity_mm_grn9);

        ges0 = (TextView) findViewById(R.id.activity_mm_ges0);
        ges1 = (TextView) findViewById(R.id.activity_mm_ges1);
        ges2 = (TextView) findViewById(R.id.activity_mm_ges2);
        ges3 = (TextView) findViewById(R.id.activity_mm_ges3);
        ges4 = (TextView) findViewById(R.id.activity_mm_ges4);
        ges5 = (TextView) findViewById(R.id.activity_mm_ges5);
        ges6 = (TextView) findViewById(R.id.activity_mm_ges6);
        ges7 = (TextView) findViewById(R.id.activity_mm_ges7);
        ges8 = (TextView) findViewById(R.id.activity_mm_ges8);
        ges9 = (TextView) findViewById(R.id.activity_mm_ges9);

        ges0.setText(textSub0);
        ges1.setText(textSub1);
        ges2.setText(textSub2);
        ges3.setText(textSub3);
        ges4.setText(textSub4);
        ges5.setText(textSub5);
        ges6.setText(textSub6);
        ges7.setText(textSub7);
        ges8.setText(textSub8);
        ges9.setText(textSub9);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListenerForRow(currentRow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.activitymm_item__howtoplay) {
            createToast("Click squares to change the color. Try to guess the secret code. The output is in no specific order.", Toast.LENGTH_LONG);
            createToast("C - correct color and position \n W - correct color, wrong position\n Nothing - both color and position is wrong.", Toast.LENGTH_LONG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOnClickListenerForRow(int row) {
        Log.w(TAG, "row: "+String.valueOf(row));
        if(row==0){
            buttonChange(red0);
            buttonChange(grn0);
            buttonChange(blu0);
            submitChange(red0, grn0, blu0, ges0);
        }
        else if(row==1){
            buttonChange(red1);
            buttonChange(grn1);
            buttonChange(blu1);
            submitChange(red1, grn1, blu1, ges1);
        }
        else if(row==2){
            buttonChange(red2);
            buttonChange(grn2);
            buttonChange(blu2);
            submitChange(red2, grn2, blu2, ges2);
        }
        else if(row==3){
            buttonChange(red3);
            buttonChange(grn3);
            buttonChange(blu3);
            submitChange(red3, grn3, blu3, ges3);
        }
        else if(row==4){
            buttonChange(red4);
            buttonChange(grn4);
            buttonChange(blu4);
            submitChange(red4, grn4, blu4, ges4);
        }
        else if(row==5){
            buttonChange(red5);
            buttonChange(grn5);
            buttonChange(blu5);
            submitChange(red5, grn5, blu5, ges5);
        }
        else if(row==6){
            buttonChange(red6);
            buttonChange(grn6);
            buttonChange(blu6);
            submitChange(red6, grn6, blu6, ges6);
        }
        else if(row==7){
            buttonChange(red7);
            buttonChange(grn7);
            buttonChange(blu7);
            submitChange(red7, grn7, blu7, ges7);
        }
        else if(row==8){
            buttonChange(red8);
            buttonChange(grn8);
            buttonChange(blu8);
            submitChange(red8, grn8, blu8, ges8);
        }
        else if(row==9){
            buttonChange(red9);
            buttonChange(grn9);
            buttonChange(blu9);
            submitChange(red9, grn9, blu9, ges9);
        }
    }

    public void buttonChange(final Button b){
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(b.getText().equals(redT)){
                    b.setText(grnT);
                    b.setTextColor(Color.parseColor(gTxt));
                    b.setBackgroundColor(Color.parseColor(grn));
                }
                else if(b.getText().equals(grnT)){
                    b.setText(bluT);
                    b.setTextColor(Color.parseColor(bTxt));
                    b.setBackgroundColor(Color.parseColor(blu));
                }
                else{
                    b.setText(redT);
                    b.setTextColor(Color.parseColor(rTxt));
                    b.setBackgroundColor(Color.parseColor(red));
                }
            }
        });
    }

    public void submitChange(final Button red, final Button blu, final Button grn, final TextView tv){
        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = 0;
                addGuessEntry(red, i++);
                addGuessEntry(blu, i++);
                addGuessEntry(grn, i);
                checkAns(tv);
                setOnClickListenerForRow(++currentRow);
            }
        });
    }

    public void addGuessEntry(final Button b, int index){
        Log.w(TAG, String.valueOf(index));
        Log.w(TAG, Arrays.toString(guess));
        if(redT.equals(b.getText())){
            guess[index] = 0;
        }
        else if(grnT.equals(b.getText())){
            guess[index] = 1;
        }
        else{
            guess[index] = 2;
        }
    }

    public void checkAns(final TextView tv){
        int numC = 0;
        int numW = 0;
        int [] solutionCopy = new int [3];
        int [] guessCopy = new int[3];
        guessCopy[0] = guess[0];
        guessCopy[1] = guess[1];
        guessCopy[2] = guess[2];
        solutionCopy[0] = solution[0];
        solutionCopy[1] = solution[1];
        solutionCopy[2] = solution[2];

        for(int i = 0; i < guessCopy.length; i++){
            if (solutionCopy[i] == guessCopy[i]){
                solutionCopy[i] = 100;
                guessCopy[i] = 100;
                numC++;
            }
        }

        for (int i = 0; i < guessCopy.length; i++){
            for(int j = 0; j < solutionCopy.length; j++){
                if(solutionCopy[j] == guessCopy[i] && guessCopy[i]<100){
                    if(i == j){
                        numC++;
                    }
                    else{
                        numW++;
                    }
                solutionCopy[j]=100;
                break;
                }
            }
        }

        String out = "";
        if(numC == solution.length){
            Toast.makeText(getApplicationContext(), "Good job! You won resources for your college!", Toast.LENGTH_SHORT).show();
            tryToUpdateCollegeRes();
            Intent myIntent = new Intent(PlayGameActivityMM.this, MenuActivity.class);
            PlayGameActivityMM.this.startActivity(myIntent);

        }
        else{
            while(numC>0){
                numC--;
                out+="C ";
            }
            while(numW>0){
                numW--;
                out+="W ";
            }

            tv.setText(out);
        }
    }

    private void tryToUpdateCollegeRes() {
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(PlayGameActivityMM.this);
        man.setAction(AsyncJsonRequestManager.Actions.UPDATECOLLEGEINFO);
        man.setRequestBody(new HackMap().setCollege(home));
        man.setCallback(new MyFutureTask() {
            @Override
            public void onCompleted(Exception e, JsonObject json) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                int status = json.get("response").getAsJsonObject().get("status").getAsInt();
                if (status != 0) {
                    createToast(json.get("response").getAsJsonObject().get("message").getAsString());
                }
            }
        }).execute();

    }

    private void createToast(final String string){
        PlayGameActivityMM.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PlayGameActivityMM.this, string, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void createToast(final String string, final int length){
        PlayGameActivityMM.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PlayGameActivityMM.this, string, length).show();

            }
        });
    }
}
