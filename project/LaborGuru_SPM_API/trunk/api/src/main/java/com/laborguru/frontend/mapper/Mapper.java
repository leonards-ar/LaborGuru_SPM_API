package com.laborguru.frontend.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
public abstract class Mapper {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    String format(Date date) {
        return simpleDateFormat.format(date);
    }
}
