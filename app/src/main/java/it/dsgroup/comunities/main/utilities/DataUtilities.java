package it.dsgroup.comunities.main.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by utente9.academy on 11/12/2017.
 */

public class DataUtilities {
    public DataUtilities() {
    }


    public static Date convertStringToDate(String data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convrtedDate = new Date();
        try {
            convrtedDate = dateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convrtedDate;
    }

    public static String convertDateToString (Date data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(data);
    }
}
