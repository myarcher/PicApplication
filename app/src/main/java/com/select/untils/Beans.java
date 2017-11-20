package com.select.untils;

import java.io.Serializable;

/**
 * Created by jyjin on 2017/10/31.
 */

public class Beans implements Serializable {
    public  String name;
    public float values;

    public Beans(String name, float values) {
        this.name = name;
        this.values = values;
    }
}
