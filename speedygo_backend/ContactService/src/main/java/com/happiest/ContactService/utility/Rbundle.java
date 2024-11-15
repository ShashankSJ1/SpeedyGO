package com.happiest.ContactService.utility;

import java.util.ResourceBundle;

public class Rbundle {
    public static String getKey(String name) {
        ResourceBundle rs = ResourceBundle.getBundle("constant");
                return rs.getString(name);
    }
}
