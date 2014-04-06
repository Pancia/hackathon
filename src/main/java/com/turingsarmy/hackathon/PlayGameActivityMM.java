package com.turingsarmy.hackathon;

import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayGameActivityMM extends ActionBarActivity {

    private int [] guess;
    private int [] solution = {(int) Math.random()%3, (int) Math.random()%3, (int) Math.random()%3};

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

    private String red = (String) red0.getTag();
    private String blu = (String) blu0.getTag();
    private String grn = (String) grn0.getTag();

    private String rTxt = "#ff2d00";
    private String bTxt = "#0081ff";
    private String gTxt = "#36ff00";

    private String redT = "Ω";
    private String bluT = "β";
    private String grnT = "Σ";

    private Tag rTag = (Tag) red0.getTag();
    private Tag bTag = (Tag) blu0.getTag();
    private Tag gTag = (Tag) grn0.getTag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);

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

        int i = 0;
        while(i < 10){
            if(i==0){
                buttonChange(red0);
                buttonChange(grn0);
                buttonChange(blu0);
                i++;
            }
            else if(i==1){
                buttonChange(red1);
                buttonChange(grn1);
                buttonChange(blu1);
                i++;
            }
            else if(i==2){
                buttonChange(red2);
                buttonChange(grn2);
                buttonChange(blu2);
                i++;
            }
            else if(i==3){
                buttonChange(red3);
                buttonChange(grn3);
                buttonChange(blu3);
                i++;
            }
            else if(i==4){
                buttonChange(red4);
                buttonChange(grn4);
                buttonChange(blu4);
                i++;
            }
            else if(i==5){
                buttonChange(red5);
                buttonChange(grn5);
                buttonChange(blu5);
                i++;
            }
            else if(i==6){
                buttonChange(red6);
                buttonChange(grn6);
                buttonChange(blu6);
                i++;
            }
            else if(i==7){
                buttonChange(red7);
                buttonChange(grn7);
                buttonChange(blu7);
                i++;
            }
            else if(i==8){
                buttonChange(red8);
                buttonChange(grn8);
                buttonChange(blu8);
                i++;
            }
            else if(i==9){
                buttonChange(red9);
                buttonChange(grn9);
                buttonChange(blu9);
                i++;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonChange(final Button b){
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(b.getTag().equals(rTag)){
                    b.setText(grnT);
                    b.setTextColor(Integer.parseInt(gTxt));
                    b.setBackgroundColor(Integer.parseInt(grn));
                    b.setTag(gTag);
                }
                else if(b.getTag().equals(gTag)){
                    b.setText(bluT);
                    b.setTextColor(Integer.parseInt(bTxt));
                    b.setBackgroundColor(Integer.parseInt(blu));
                    b.setTag(bTag);
                }
                else{
                    b.setText(redT);
                    b.setTextColor(Integer.parseInt(rTxt));
                    b.setBackgroundColor(Integer.parseInt(red));
                    b.setTag(rTag);
                }
            }
        });
    }

}
