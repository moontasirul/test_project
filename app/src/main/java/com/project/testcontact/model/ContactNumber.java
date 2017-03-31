package com.project.testcontact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user123 on 1/9/2017.
 */

public class ContactNumber implements Serializable{
    private int id;
    String title;
    String number;


    public ContactNumber(){

    }

    public ContactNumber(String title, int number) {
        this.title = title;
        number = number;
    }

    public ContactNumber(int id, String title, String number){
        this.id = id;
        this.title = title;
        this.number = number;
    }


    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "ContactNumber{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", number='" + number + '\'' +

                '}';
    }

}
