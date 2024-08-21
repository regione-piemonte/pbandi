/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaDati {

	/* valida lunghezza variabile di stringhe in base ad una regex passata come parametro */
	public static boolean isValidAlphanumericString(String value, String regexFormat, int maxLength) {
	    if (value == null || regexFormat == null) {
	        return false;
	    }
	    String regex = String.format(regexFormat, maxLength);
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(value);
	    return matcher.matches();
	}


	/* valida data nel formato: yyyy-MM-dd */
	public static boolean isValidDate(Date date) {
		try {
			LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}
