/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * DOCUMENT ME!
 *
 * @author apiccirilli
 */
public class DataFilter {

   public static final String NULL_STRING_INDICATOR = "";
   public static final String EMPTY_STRING_INDICATOR = "";
   public static final String NULL_INTEGER_INDICATOR = "";
   public static final String EMPTY_INTEGER_INDICATOR = "0";
   public static final String NULL_LONG_INDICATOR = "";
   public static final String EMPTY_LONG_INDICATOR = "0";
   public static final String TRUE_BOOLEAN_INDICATOR = "SI";
   public static final String FALSE_BOOLEAN_INDICATOR = "NO";

   public static final String DATE_FORMAT = "dd/MM/yyyy";
   public static Locale locale = Locale.ITALIAN;

   
   public static String removeNull(Object value) {

      String valueToReturn = EMPTY_STRING_INDICATOR;

      if(value instanceof String) {
         valueToReturn = removeNullString((String) value);
      } 
      else if(value instanceof Integer) {
         valueToReturn = removeNullInteger((Integer) value);
      } 
      else if(value instanceof Date) {
         valueToReturn = removeNullDate((Date) value);
      } 
      else if(value instanceof Boolean) {
         valueToReturn = removeNullBoolean((Boolean) value);
      } 
      else if(value instanceof BigDecimal) {
         valueToReturn = removeNullBigDecimal((BigDecimal) value);
      } 
      else if(value instanceof Long) {
         valueToReturn = removeNullLong((Long) value);
      }

      return valueToReturn;
   }

   /**
    * Metodo che trasforma il null in NULL_STRING_INDICATOR.
    */
   public static String removeNullString(String value) {

      if(value == null) {
         value = NULL_STRING_INDICATOR;
      }

      return value;
   }


   public static String removeNullInteger(Integer value) {

      String valueToReturn;

      if(value == null) {
         valueToReturn = NULL_INTEGER_INDICATOR;
      } 
      else {
         try {
            valueToReturn = "" + value.intValue();
         } catch(NumberFormatException nfe) {
            nfe.printStackTrace();
            valueToReturn = NULL_INTEGER_INDICATOR;
         }
      }

      return valueToReturn;
   }


   public static String removeNullBigDecimal(BigDecimal value) {

      String valueToReturn;

      if(value == null) {
         valueToReturn = NULL_LONG_INDICATOR;
      } 
      else {
         try {
            valueToReturn = "" + value.longValue();
         } 
         catch(NumberFormatException nfe) {
            nfe.printStackTrace();
            valueToReturn = NULL_LONG_INDICATOR;
         }
      }

      return valueToReturn;
   }


   public static String removeNullLong(Long value) {

      String valueToReturn;

      if(value == null) {
         valueToReturn = NULL_LONG_INDICATOR;
      } 
      else {
         try {
            valueToReturn = "" + value.longValue();
         } 
         catch(NumberFormatException nfe) {
            nfe.printStackTrace();
            valueToReturn = NULL_LONG_INDICATOR;
         }
      }

      return valueToReturn;
   }


   public static String removeNullDate(Date value) {

      String formattedDate = null;

      if(value == null) {
         formattedDate = "-";
      } 
      else {
         SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, locale);
         sdf.setLenient(false);

         formattedDate = sdf.format(value);
      }

      return formattedDate;

   }


   public static String removeNullBoolean(Boolean value) {
      String valueToReturn = EMPTY_STRING_INDICATOR;

      if(value == null) {
         valueToReturn = NULL_STRING_INDICATOR;
      } 
      else if(value.booleanValue() == true) {
         valueToReturn = TRUE_BOOLEAN_INDICATOR;
      } 
      else {
         valueToReturn = FALSE_BOOLEAN_INDICATOR;
      }

      return valueToReturn;
   }

   public static String replaceNull(String value, String replacement) {
      if(value == null) {
         return replacement;
      } else {
         return value;
      }
   }


   public static String replaceNull(Object value, String replacement) {

      String result;
      if(value == null) {
         return replacement;
      }
      else {
         result = removeNull(value);
         return result;
      }
   }


   public static boolean isTrue(String value) {

      if (value == null) return false;

      value = value.toUpperCase();

      if (value.equals("TRUE")) return true;
      if (value.equals("SI")) return true;
      if (value.equals("YES")) return true;

      return false;

   }


   public static boolean isEmpty(String string) {
      return ((string == null) || (string.length() == 0));
   }

   public static boolean isEmpty(Object object) {
	  String objectStringValue = DataFilter.removeNull(object); 
      return ((objectStringValue == null) || (objectStringValue.length() == 0));
   }


   public static String replaceEmpty(String value, String replacement) {

      if (isEmpty(value)) return replacement;

      else return value;

   }


}
