package com.happiest.BookingService.utility;

import java.util.ResourceBundle;

public class Rbundle {
    public static String getKey(String name) {
        ResourceBundle rs = ResourceBundle.getBundle("constant");
                return rs.getString(name);
    }
}
