/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class ModelClint implements Serializable {
    private String str_Image_url;
    private String str_Clint_name;
    private String str_Clint_Address;
    private String str_Clint_PhoneNumber;
    @Exclude
    private String str_id;

    public ModelClint() {
    }

    public ModelClint(String str_Image_url, String str_Clint_name,
                      String str_Clint_Address, String str_Clint_PhoneNumber, String str_id) {
        this.str_Image_url = str_Image_url;
        this.str_Clint_name = str_Clint_name;
        this.str_Clint_Address = str_Clint_Address;
        this.str_Clint_PhoneNumber = str_Clint_PhoneNumber;
        this.str_id = str_id;
    }

    public String getStr_Image_url() {
        return str_Image_url;
    }

    public void setStr_Image_url(String str_Image_url) {
        this.str_Image_url = str_Image_url;
    }

    public String getStr_Clint_name() {
        return str_Clint_name;
    }

    public void setStr_Clint_name(String str_Clint_name) {
        this.str_Clint_name = str_Clint_name;
    }

    public String getStr_Clint_Address() {
        return str_Clint_Address;
    }

    public void setStr_Clint_Address(String str_Clint_Address) {
        this.str_Clint_Address = str_Clint_Address;
    }

    public String getStr_Clint_PhoneNumber() {
        return str_Clint_PhoneNumber;
    }

    public void setStr_Clint_PhoneNumber(String str_Clint_PhoneNumber) {
        this.str_Clint_PhoneNumber = str_Clint_PhoneNumber;
    }

    public String getStr_id() {
        return str_id;
    }

    public void setStr_id(String str_id) {
        this.str_id = str_id;
    }
}
