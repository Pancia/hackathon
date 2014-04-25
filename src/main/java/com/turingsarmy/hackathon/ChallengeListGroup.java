package com.turingsarmy.hackathon;
import java.util.ArrayList;
/**
 * Created by cameron on 4/6/14.
 */
public class ChallengeListGroup {

    private String Name;
    private ArrayList<ChallengeListChild> Items;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public ArrayList<ChallengeListChild> getItems() {
        return Items;
    }
    public void setItems(ArrayList<ChallengeListChild> Items) {
        this.Items = Items;
    }
}
