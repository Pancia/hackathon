package com.turingsarmy.hackathon;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HackMap {
    private static final String USERNAME    = "username";
    private static final String PASSWORD    = "password";
    private static final String GAMEMODE    = "gamemode";
    private static final String COLLEGE     = "college";
    private static final String MOVE        = "move";
    private static final String EMAIL       = "email";

    HashMap<String, String> myMap = new HashMap<String, String>();

    public Set<Map.Entry<String,String>> entrySet() {
        return myMap.entrySet();
    }

    public boolean isEmpty() {
        return myMap.isEmpty();
    }

    public HackMap setUsername(String username) {
        myMap.put(USERNAME, username);
        return this;
    }

    public HackMap setGamemode(String gamemode) {
        myMap.put(GAMEMODE, gamemode);
        return this;
    }

    public HackMap setMove(String move) {
        myMap.put(MOVE, move);
        return this;
    }

    public HackMap setCollege(String currentCollege) {
        myMap.put(COLLEGE, currentCollege);
        return this;
    }

    public HackMap setPassword(String password) {
        myMap.put(PASSWORD, password);
        return this;
    }

    public HackMap setEmail(String email) {
        myMap.put(EMAIL, email);
        return this;
    }
}
