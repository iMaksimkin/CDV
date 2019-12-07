package util;

import java.sql.Date;

public class DateUtil {
    public static Date convertStringToSqlDate(String stringDate){
        try{
            return Date.valueOf(stringDate);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new IllegalArgumentException("I cant " + stringDate + " convert to sql.Date." + " Format must be yyyy-mm-dd");
        }
    }
}
