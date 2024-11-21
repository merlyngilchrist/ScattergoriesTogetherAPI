package com.example.ScattergoriesTogetherAPI.model;

import java.util.ArrayList;

public class Lobby {

    private ArrayList<User> users;
    private String lobbyCode;

    public Lobby() {}
    public Lobby(String lobbyCode, ArrayList<User> users) {
        this.lobbyCode = lobbyCode;
        this.users = users;
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
    public void removeUser(User user) {
        this.users.remove(user);
    }
}
