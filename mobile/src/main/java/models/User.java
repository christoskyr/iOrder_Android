package models;

import java.lang.reflect.TypeVariable;

/**
 * Created by iOrder on 12/10/2014.
 */
public class User {

    private String name;
    private String email;
    private String password;
    private String password_confirmation;

    public User() {}

    public User(String name){ this.name = name; }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getRepassword(){
        return this.password_confirmation;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRepassword(String repassword){
        this.password_confirmation = repassword;
    }



}
