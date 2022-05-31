package com.triangle.atm;

public class dbref {

    String username,usernumber,userpin;


    public dbref() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getUserpin() {
        return userpin;
    }

    public void setUserpin(String userpin) {
        this.userpin = userpin;
    }

    public dbref(String username, String usernumber, String userpin) {
        this.username = username;
        this.usernumber = usernumber;
        this.userpin = userpin;
    }
}
