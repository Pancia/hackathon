package com.turingsarmy.hackathon;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class ChallengesActivity extends Activity {

    /** Called when the activity is first created. */
    private ExpandListAdapter ExpAdapter;
    private ArrayList<ChallengeListGroup> ExpListItems;
    private ExpandableListView ExpandList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challengesres);
        ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
    }

    public ArrayList<ChallengeListGroup> SetStandardGroups() {
        ArrayList<ChallengeListGroup> list = new ArrayList<ChallengeListGroup>();
        ArrayList<ChallengeListChild> list2 = new ArrayList<ChallengeListChild>();
        ChallengeListGroup gru1 = new ChallengeListGroup();
        gru1.setName("First Steps");
        ChallengeListChild ch1_1 = new ChallengeListChild();
        ch1_1.setName("Journey to College Eight");
        ch1_1.setTag(null);
        list2.add(ch1_1);
        ChallengeListChild ch1_2 = new ChallengeListChild();
        ch1_2.setName("Win a battle at College Eight");
        ch1_2.setTag(null);
        list2.add(ch1_2);


        gru1.setItems(list2);
        list2 = new ArrayList<ChallengeListChild>();

        ChallengeListGroup gru2 = new ChallengeListGroup();
        gru2.setName("Campus Tour");

        ChallengeListChild ch2_1 = new ChallengeListChild();
        ch2_1.setName("Visit Oakes College");
        ch2_1.setTag(null);
        list2.add(ch2_1);

        ChallengeListChild ch2_2 = new ChallengeListChild();
        ch2_2.setName("Visit College Eight");
        ch2_2.setTag(null);
        list2.add(ch2_2);

        ChallengeListChild ch2_3 = new ChallengeListChild();
        ch2_3.setName("Visit Porter College");
        ch2_3.setTag(null);
        list2.add(ch2_3);

        ChallengeListChild ch2_4 = new ChallengeListChild();
        ch2_4.setName("Visit Kresge College");
        ch2_4.setTag(null);
        list2.add(ch2_4);

        ChallengeListChild ch2_5 = new ChallengeListChild();
        ch2_5.setName("Visit College Nine");
        ch2_5.setTag(null);
        list2.add(ch2_5);

        ChallengeListChild ch2_6 = new ChallengeListChild();
        ch2_6.setName("Visit College Ten");
        ch2_6.setTag(null);
        list2.add(ch2_6);

        ChallengeListChild ch2_7 = new ChallengeListChild();
        ch2_7.setName("Visit Crown College");
        ch2_7.setTag(null);
        list2.add(ch2_7);

        ChallengeListChild ch2_8 = new ChallengeListChild();
        ch2_8.setName("Visit Merrill College");
        ch2_8.setTag(null);
        list2.add(ch2_8);

        ChallengeListChild ch2_9 = new ChallengeListChild();
        ch2_9.setName("Visit Stevenson College");
        ch2_9.setTag(null);
        list2.add(ch2_9);

        ChallengeListChild ch2_10 = new ChallengeListChild();
        ch2_10.setName("Visit Cowell College");
        ch2_10.setTag(null);
        list2.add(ch2_10);

        gru2.setItems(list2);
        list2 = new ArrayList<ChallengeListChild>();

        ChallengeListGroup gru3 = new ChallengeListGroup();
        gru3.setName("Paint The Town Red");
        ChallengeListChild ch3_1 = new ChallengeListChild();
        ch3_1.setName("Win a battle at The Boardwalk");
        ch3_1.setTag(null);
        list2.add(ch3_1);
        ChallengeListChild ch3_2 = new ChallengeListChild();
        ch3_2.setName("Win a battle at Downtown");
        ch3_2.setTag(null);
        list2.add(ch3_2);
        gru3.setItems(list2);

        list.add(gru1);
        list.add(gru2);
        list.add(gru3);

        return list;
    }

}