/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class DateConverter {
	
	private static Logger logger = Logger.getLogger(DateConverter.class);
	
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    // Converte una stringa in un oggetto Date
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Converte un oggetto Date in una stringa
    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }
    
    // converte data tipo Date restituendo HH:mm:ss tutti a zero
    public static Date convertDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(date);
        try {
            return formatter.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = inputFormat.parse(dateString);
            date = outputFormat.parse(outputFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
    
}
