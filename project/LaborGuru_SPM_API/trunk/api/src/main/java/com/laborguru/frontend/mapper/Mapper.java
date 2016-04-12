package com.laborguru.frontend.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
public abstract class Mapper {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm");

    String formatDate(Date date) {
        return simpleDateFormat.format(date);
    }

    String formatHour(Date date) {
        return simpleHourFormat.format(date);
    }
}
