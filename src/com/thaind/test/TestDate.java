package com.thaind.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    public static void main(String[] args) {
        String date = "16-11-2000";
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
        try{
            Date date2 = sdf.parse(date);
            System.out.println(sdf.format(date2));   
            System.out.println(date2.getTime()); 
            System.out.println(java.sql.Date.valueOf("2000-11-16"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
}
