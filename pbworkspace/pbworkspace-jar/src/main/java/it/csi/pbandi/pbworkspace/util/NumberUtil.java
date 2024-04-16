/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author gruppo bandi
 * 
 *         <p>
 *         Classe di utilit� per operazioni di calcolo, formattazione e
 *         parsificazione di numeri.
 *         <p>
 *         Particolare attenzione � rivolta alle operazioni tra decimali.
 *         <p>
 * 
 * @since 2.1.0
 *        <p>
 *        In data 02/11/2009 la classe � stata integrata con porzioni di codice
 *        dell'equivalente classe definita nella <br>
 *        componente PBANDIWEB, nell'ottica di produrre un unico artefatto
 *        software (in forma di libreria) <br>
 *        da condividere tra le varie miniApp ed il servizio di piattoforma
 *        bandi.
 *        <p>
 * 
 *        Dalla versione precedente sono stati rimossi i seguenti metodi non pi�
 *        usati (sia lato pbandisrv che pbandiweb):
 *        <p>
 *        - isNumeric - isDouble - toDouble - getStringTruncated -
 *        checkValueIsNotTooLong - toBigDecimalSeparatoreVirgola -
 *        toAbsoluteBigDecimal
 * 
 */
public class NumberUtil {
	public static final String DECIMAL_FORMAT = "###,###,##0.00";
	public static final String INTEGER_FORMAT = "###,###,##0";
	public static final String DECIMAL_FORMAT_NO_MIGLIAIA_SEPARATOR = "####0.00";
	public static final String DECIMAL_SEPARATOR_PUNTO = ".";
	public static final String DECIMAL_SEPARATOR_VIRGOLA = ",";
	static final int SCALE = 2; // DA RIMPIAZZARE OVUNQUE!!

	// --------------------------------------------------------------------------
	// isNumeric
	// verifica se una stringa e' formata da soli numeri
	// --------------------------------------------------------------------------
	/**
	 * public static boolean isNumeric(String str) { if (str == null) return
	 * false;
	 * 
	 * for (int i = 0; i < str.length(); i++) { if (str.charAt(i) != ',' &&
	 * (str.charAt(i) < '0' || '9' < str.charAt(i))) return false; }
	 * 
	 * return true; }
	 */

	/**
	 * Metodo di verifica di conformit� della stringa al pattern di formato
	 * dell'importo in bandi.<br>
	 * Il pattern � '#.##0.00'
	 */
	public static boolean isImporto(String str) {
		if (str == null || str.equals(""))
			return false;
		try {
			NumberFormat df = NumberFormat.getNumberInstance(Locale.ITALY);
			df.setMinimumFractionDigits(SCALE);
			df.setMaximumFractionDigits(SCALE);
			df.parse(str);

			// il parsing � ok, dunque la stringa � un numero conforme al
			// formato
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifica che la stringa in ingresso sia un importo e che il massimo
	 * numero di cifre della parte intera sia <= maxIntegerDigits
	 */
	public static boolean isImporto(String str, int maxIntegerDigits) {
		if (isImporto(str)) {
			if (maxIntegerDigits < 1)
				return false;
			BigDecimal d = toBigDecimal(str);
			DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT,
					new DecimalFormatSymbols(java.util.Locale.ITALIAN));
			df.setMinimumFractionDigits(SCALE);
			df.setMaximumFractionDigits(SCALE);
			try {
				Long n = new Long(df.parse(df.format(d)).longValue());
				if (n.toString().length() > maxIntegerDigits)
					return false;
				else
					return true;
			} catch (Exception e) {
				return false;
			}
		} else
			return false;

	}

	public static boolean isNumber(String str) {
		if (str == null || str.equals(""))
			return false;

		if (toBigDecimal(str) != null)
			return true;
		else
			return false;
	}

	private static BigDecimal toBigDecimal(String str) {
		if (str == null || str.length() == 0)
			return null;
		BigDecimal ret = null;
		str = str.replace(',', '.');
		int indice = str.lastIndexOf('.');
		// FIXME perch� fa il controllo sui decimali di 2 o 3???
		int decimali = str.length() - indice;
		if (indice > -1 && (decimali == 3 || decimali == 2)) {
			String tmp = str.substring(0, indice);
			String coda = str.substring(indice + 1, str.length());
			tmp = tmp.replace(".", "");
			str = tmp + "." + coda;
		}
		try {
			ret = new BigDecimal(str);
		} catch (Exception x) {
			return null;
		}
		return ret;
	}

	/**
	 * Parsificazione di una stringa con valore intero positivo
	 * 
	 * @param str
	 *            la stringa contenente l'intero positivo
	 * @return <p>
	 *         - 0 (zero) se: str � null o se � un intero negativo o se non �
	 *         parsificabile - il valore parsificato
	 */
	public static int toInt(String str) {
		if (str == null)
			return 0;

		try {
			int m = Integer.parseInt(str);

			if (m < 0)
				return 0;

			return m;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Parsificazione di una stringa con valore long positivo
	 * 
	 * @param str
	 *            la stringa contenente l'intero positivo
	 * @return <p>
	 *         - 0 (zero) se: str � null o se � negativo o se non �
	 *         parsificabile - il valore parsificato
	 */
	public static long toLong(String str) {
		if (str == null)
			return 0L;

		try {
			long m = Long.parseLong(str);

			if (m < 0L)
				return 0L;

			return m;
		} catch (Exception e) {
			return 0L;
		}
	}

	/*
	 * public static boolean isDouble(String str) {
	 * 
	 * if (str == null || str.equals("")) return true; str = str.replace(',',
	 * '.'); try { double m = Double.parseDouble(str); if (m < 0.0) return
	 * false;
	 * 
	 * return true; } catch (Exception e) { return false; } }
	 * 
	 * public static double toDouble(String str) { if (str == null ||
	 * str.equals("")) return 0.0; try { double m = Double.parseDouble(str); if
	 * (m < 0.0) return 0.0;
	 * 
	 * return m; } catch (Exception e) { return 0.0; } }
	 * 
	 * 
	 * public static String getStringTruncated(double d) {
	 * 
	 * String str = "" + d; if (str.indexOf(".") < str.length() - 3) str =
	 * str.substring(0, str.indexOf(".") + 3);
	 * 
	 * return str; }
	 * 
	 * public static boolean checkValueIsNotTooLong(String value, int
	 * maxIntegers) {
	 * 
	 * String str = value; String nIntegers = "";
	 * 
	 * if (str != null && !str.equals("")) { if (str.indexOf('.') > -1) {
	 * nIntegers = str.substring(0, str.indexOf('.')); } else nIntegers = str;
	 * 
	 * if (nIntegers.length() > maxIntegers) return false; } return true; }
	 */
	public static String getStringValue(String format, BigDecimal val) {
		if (val == null)
			return null;
		return new java.text.DecimalFormat(format,
				new java.text.DecimalFormatSymbols(java.util.Locale.ITALIAN))
				.format(val);
	}

	public static String getStringValue(BigDecimal val) {
		if (val == null)
			return null;
		return new java.text.DecimalFormat(DECIMAL_FORMAT,
				new java.text.DecimalFormatSymbols(java.util.Locale.ITALIAN))
				.format(val);
	}
	
	public static String getStringValueAnglFormat(BigDecimal val) {
		if (val == null)
			return null;
		DecimalFormat decimalFormat = new java.text.DecimalFormat();
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setDecimalFormatSymbols(new java.text.DecimalFormatSymbols(java.util.Locale.ENGLISH));
		return decimalFormat.format(val);
	}

	public static String getAbsoluteStringValue(BigDecimal val) {
		if (val == null)
			return null;
		return new java.text.DecimalFormat(INTEGER_FORMAT,
				new java.text.DecimalFormatSymbols(java.util.Locale.ITALIAN))
				.format(val);
	}

	public static String getStringValue(Integer val) {
		if (val == null)
			return null;
		return val.toString();
	}

	public static String getStringValue(Long number) {
		String ret = "";
		if (number != null && number > 0)
			ret = String.valueOf(number);
		return ret;
	}

	public static String getStringValue(Double number) {
		String ret = "";

		if (number != null && number > 0)
			ret = String.valueOf(number);
		return ret;
	}

	public static String getStringValue(Float number) {
		String ret = "";
		if (number != null && number > 0)
			ret = String.valueOf(number);
		return ret;
	}

	/*
	 * public static BigDecimal toBigDecimalSeparatoreVirgola(String text) {
	 * BigDecimal retValue = null;
	 * 
	 * if (text != null && text.length() > 0) { text = text.replace(',', '.');
	 * while (text.indexOf(".") != text.lastIndexOf('.')) text =
	 * text.substring(0, text.indexOf(".")) + text.substring(text.indexOf(".") +
	 * 1, text.length()); retValue = new BigDecimal(text); }
	 * 
	 * return retValue; }
	 * 
	 * 
	 * public static BigDecimal toAbsoluteBigDecimal(String text) { BigDecimal
	 * retValue = null;
	 * 
	 * if (text != null && text.length() > 0) { retValue = new BigDecimal(text);
	 * retValue = new BigDecimal(getAbsoluteIntValue(retValue)); }
	 * 
	 * return retValue; }
	 */

	/**
	 * Converte una String in un Integer.
	 * 
	 * @param text
	 *            Testo da convertire.
	 * @return Il BigDecimal, oppure null.
	 */

	/*
	 * public static Integer toInteger(String text) { Integer retValue = null;
	 * 
	 * if (text != null && text.length() > 0) { retValue = new Integer(text); }
	 * 
	 * return retValue; }
	 * 
	 * 
	 * public static String add(BigDecimal a, BigDecimal b) { String somma = "";
	 * if (a == null && b == null) return ""; if (a != null && b != null) return
	 * getStringValue(a.add(b)); if (a == null) return getStringValue(b); if (b
	 * == null) return getStringValue(a);
	 * 
	 * return somma; }
	 * 
	 * 
	 * 
	 * public static String addNoFormat(BigDecimal a, BigDecimal b) { String
	 * somma = ""; if (a == null && b == null) return ""; if (a != null && b !=
	 * null) return getStringValueNoFormat(a.add(b)); if (a == null) return
	 * getStringValueNoFormat(b); if (b == null) return
	 * getStringValueNoFormat(a);
	 * 
	 * return somma; }
	 * 
	 * 
	 * 
	 * public static String add(Integer a, Integer b) { String somma = ""; if (a
	 * == null && b == null) return ""; if (a != null && b != null) return
	 * getStringValue(a.intValue() + b.intValue()); if (a == null) return
	 * getStringValue(b); if (b == null) return getStringValue(a);
	 * 
	 * return somma; }
	 * 
	 * 
	 * public static String getStringValueNoFormat(BigDecimal val) { if (val ==
	 * null) return null; return val.toString(); }
	 */

	/**
	 * Estrae la parte intera di un BigDecimal
	 * 
	 * @param val
	 *            A string containing
	 * @return a string containing only the integer part of the number
	 */

	/*
	 * public static String getIntegerPart(String val) { if (val == null) {
	 * return null; } else if (val.indexOf(".") != -1) { return val.substring(0,
	 * val.indexOf(".")); } else { return val.toString(); }
	 * 
	 * }
	 */

	/**
	 * Estrae la parte decimale di un BigDecimal
	 * 
	 * @param val
	 *            A string containing a BigDecimal value
	 * @return a string containing only the decimal part of the number
	 */

	/*
	 * public static String getDecimalPart(String val) { if (val == null) {
	 * return null; } else if (val.indexOf(".") != -1) { return
	 * val.substring(val.indexOf(".") + 1); } else { return ""; }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public static String getDecimalPart(BigDecimal val) {
	 * 
	 * String ret = ""; if (val != null) { String text = val.toString(); text =
	 * text.replace(',', '.'); if (text.indexOf(".") != -1) { ret =
	 * text.substring(text.indexOf(".") + 1); } }
	 * 
	 * return ret; }
	 * 
	 * public static String getDecimalPart(BigDecimal val, int decimali) {
	 * 
	 * String ret = ""; if (val != null) { String text = val.toString(); text =
	 * text.replace(',', '.'); if (text.indexOf(".") != -1) { ret =
	 * text.substring(text.indexOf(".") + 1); if (ret.length() > decimali) { ret
	 * = ret.substring(0, decimali); } } }
	 * 
	 * return ret; }
	 * 
	 * public static String getFormattedDecimalPart(String val) { if (val ==
	 * null) { return null; } else if (val.indexOf(".") != -1) { return
	 * val.substring(val.indexOf(".") + 1); } else { return "00"; }
	 * 
	 * }
	 * 
	 * public static String mergeDecimalIntParts(String intPart, String
	 * decimalPart) { String parteIntera = "0"; if (intPart != null &&
	 * !intPart.equals("")) parteIntera = intPart; String parteDec = "0"; if
	 * (decimalPart != null && !decimalPart.equals("")) parteDec = decimalPart;
	 * if ((intPart != null && !intPart.equals("")) || (decimalPart != null &&
	 * !decimalPart.equals(""))) return parteIntera + "." + parteDec; else
	 * return ""; }
	 * 
	 * public static String mergeDecimalIntPartsPerStampa(String intPart, String
	 * decimalPart) { String parteIntera = "0"; if (intPart != null &&
	 * !intPart.equals("")) parteIntera = intPart; String parteDec = "0"; if
	 * (decimalPart != null && !decimalPart.equals("")) parteDec = decimalPart;
	 * if ((intPart != null && !intPart.equals("")) || (decimalPart != null &&
	 * !decimalPart.equals(""))) return parteIntera + "," + parteDec; else
	 * return ""; }
	 * 
	 * 
	 * public static int getIntValue(BigDecimal val) { if (val != null) return
	 * val.intValue(); return 0; }
	 * 
	 * public static int getAbsoluteIntValue(BigDecimal val) { if (val != null)
	 * return val.abs().intValue(); return 0; }
	 */

	public static double getDoubleValue(BigDecimal val) {
		if (val != null) {
			BigDecimal valRounded = createScaledBigDecimal(val);
			return valRounded.doubleValue();
		}
		return 0;
	}

	public static double getDoubleValue(Double val) {
		if (val != null) {
			BigDecimal valRounded = createScaledBigDecimal(new BigDecimal(val));
			return valRounded.doubleValue();
		}
		return 0;
	}

	
	public static Long toLong(BigDecimal val) {
		Long ret = null;
		if (val != null) {
			ret = new Long(val.longValue());
		}
		return ret;
	}

	public static Double toDouble(BigDecimal val) {
		Double ret = null;
		if (val != null) {
			ret = new Double(val.doubleValue());
		}
		return ret;
	}

	/**
	 * @deprecated
	 */
	public static BigDecimal toBigDecimal(Long val) {
		BigDecimal ret = null;
		if (val != null) {
			ret = new BigDecimal(val.longValue());
		}
		return ret;
	}

	/**
	 * Arrotondamento del Double alle due cifre decimali
	 * 
	 * @param _double
	 * @return
	 */
	public static double toRoundDouble(Double _double) {

		BigDecimal ret = null;
		if (_double != null) {
			ret = new BigDecimal(_double);
			ret = ret.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
			return ret.doubleValue();
		} else
			return 0D;

	}

	/**
	 * Stessa implementazione della compare(BigDecimal a, BigDecimal b).
	 * Rimuovere? Il nome del metodo � anche abbastanza ambiguo. Va tenuto per
	 * retrocompatibilit�, ma deprecato. (Paolo)
	 * 
	 * @deprecated
	 * @param _bigDecimal
	 * @return
	 */
	public static double toRoundBigDecimal(BigDecimal _bigDecimal) {
		return createScaledBigDecimal(_bigDecimal).doubleValue();
	}

	/**
	 * Comparazione tra BigDecimal "arrotondati" alle due cifre decimali
	 * 
	 * @param a
	 * @param b
	 * @return -1,0 o 1 (a seconda che a&lt;b, a==b o a&gt;b)
	 */
	public static int compare(BigDecimal a, BigDecimal b) {
		return createScaledBigDecimal(a).compareTo(createScaledBigDecimal(b));
	}

	/**
	 * Overloading su compare(BigDecimal a, BigDecimal b)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compare(Double a, Double b) {
		return createScaledBigDecimal(a).compareTo(createScaledBigDecimal(b));
	}

	/**
	 * Overloading su compare(BigDecimal a, BigDecimal b)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compare(Long a, Long b) {
		return createScaledBigDecimal(a).compareTo(createScaledBigDecimal(b));
	}

	public static BigDecimal sum(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			return null;
		}
		return createScaledBigDecimal(a).add(createScaledBigDecimal(b));
	}

	public static Double sum(Double a, Double b) {
		if (a == null || b == null) {
			return null;
		}
		try {
			return toDouble(sum(createScaledBigDecimal(a),
					createScaledBigDecimal(b)));
		} catch (ArithmeticException e) {
			return null;
		}
	}

	public static Double subtract(Double a, Double b) {
		if (a == null || b == null) {
			return null;
		}
		try {
			return toDouble(subtract(createScaledBigDecimal(a),
					createScaledBigDecimal(b)));
		} catch (ArithmeticException e) {
			return null;
		}
	}

	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			return null;
		}
		return createScaledBigDecimal(a).subtract(createScaledBigDecimal(b));
	}

	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			return null;
		}
		return createScaledBigDecimal(createScaledBigDecimal(a).multiply(
				createScaledBigDecimal(b)));
	}

	public static BigDecimal multiply(Double a, Double b) {
		if (a == null || b == null) {
			return null;
		}
		return multiply(createScaledBigDecimal(a), createScaledBigDecimal(b));
	}

	public static BigDecimal divide(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			return null;
		}
		try {
			return createScaledBigDecimal(a).divide(createScaledBigDecimal(b),
					SCALE, BigDecimal.ROUND_HALF_UP);
		} catch (ArithmeticException e) {
			return null;
		}
	}
	
	public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
		if (a == null || b == null) {
			return null;
		}
		try {
			return createScaledBigDecimal(a, scale).divide(createScaledBigDecimal(b, scale),
					scale, BigDecimal.ROUND_HALF_UP);
		} catch (ArithmeticException e) {
			return null;
		}
	}

	public static BigDecimal divide(Double a, Double b) {
		if (a == null || b == null) {
			return null;
		}
		return divide(createScaledBigDecimal(a), createScaledBigDecimal(b));
	}
	
	public static BigDecimal multiply(BigDecimal a, BigDecimal b, int scale) {
		if (a == null || b == null) {
			return null;
		}
		return createScaledBigDecimal(createScaledBigDecimal(a, scale).multiply(
				createScaledBigDecimal(b, scale)), scale);
	}
	
	public static BigDecimal multiply(Double a, Double b, int scale) {
		if (a == null || b == null) {
			return null;
		}
		return multiply(createScaledBigDecimal(a, scale), createScaledBigDecimal(b, scale), scale);
	}

	/**
	 * Il metodo � responsabile di:
	 * <p>
	 * - impostare la precisione dell BigDecimal in input a due cifre decimali <br>
	 * - arrotondare secondo la modalit� BigDecimal.ROUND_HALF_UP, cio� se la
	 * cifra scartata � >= 5 allora arrotonda verso l'alto, altrimenti verso il
	 * basso.
	 * 
	 * @param n
	 * @return
	 */
	public static BigDecimal createScaledBigDecimal(BigDecimal n) {
		n = n == null ? new BigDecimal(0) : n;
		n = n.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		return n;
	}
	/**
	 * Crea un BigDecimal con max due cifre decimali
	 * @param n
	 * @return
	 */
	public static BigDecimal createScaledBigDecimal(Double n) {
		BigDecimal b = n == null ? new BigDecimal(0) : new BigDecimal(n);
		return createScaledBigDecimal(b);
	}
	/**
	 * Crea un BigDecimal con max due cifre decimali
	 * @param n
	 * @return
	 */
	public static BigDecimal createScaledBigDecimal(Long n) {
		BigDecimal b = n == null ? new BigDecimal(0) : new BigDecimal(n);
		return createScaledBigDecimal(b);
	}
	/**
	 * Crea un BigDecimal col numero di cifre decimali indicate
	 * @param n
	 * @param scale rappresenta il numero di cifre decimali gestite
	 * @return
	 */
	public static BigDecimal createScaledBigDecimal(BigDecimal n,int scale) {
		n = n == null ? new BigDecimal(0) : n;
		n = n.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return n;
	}
	/**
	 * Crea un BigDecimal col numero di cifre decimali indicate
	 * @param n
	 * @param scale rappresenta il numero di cifre decimali gestite
	 * @return
	 */
	public static BigDecimal createScaledBigDecimal(Double n,int scale) {
		BigDecimal b = n == null ? new BigDecimal(0) : new BigDecimal(n);
		return createScaledBigDecimal(b,scale);
	}


	/**
	 * Metodo di parsificazione di una stringa rappresentante un importo.<br>
	 * Il formato decimale previsto in bandi � il seguente: '#.##0.00'.
	 * 
	 * @return <p>
	 *         - Null, se la stringa passata � nulla o vuota oppure non rispetta
	 *         il formato<br>
	 *         - La rappresentazione dell'importo in Double in tutti gli altri
	 *         casi
	 */
	public static Double toNullableDouble(String str) {
		if (str == null || str.equals(""))
			return null;
		try {
			/*
			 * CsiNumericConverter converter = new CsiNumericConverter(); Object
			 * result = converter.convertFromString(new HashMap(), new String[]
			 * {str}, Double.class); if(result.getClass().equals(Double.class))
			 * { return (Double)result; }
			 */

			java.text.NumberFormat df = java.text.DecimalFormat
					.getInstance(java.util.Locale.ITALIAN);
			df.setMinimumFractionDigits(SCALE);
			df.setMaximumFractionDigits(SCALE);
			return (Double) df.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Long toNullableLong(String str) {
		if (str == null || str.equals(""))
			return null;
		try {
			return new Long(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converte il Double in un valore valido per il db. Se il valore � null o 0
	 * allora il valore. da salvare sul db � null
	 * 
	 * @param val
	 * @return Metodo importato da pbandiweb
	 */
	public static Double convertForDB(Double val) {
		if (val != null && val == 0D)
			return null;
		else
			return val;
	}

	/**
	 * Converte il Long in un valore valido per il db. Se il valpre � null o 0
	 * allora il valore da salvare sul db � null
	 * 
	 * @param val
	 * @return Metodo importato da pbandiweb
	 */
	public static Long convertForDB(Long val) {
		if (val != null && val == 0L)
			return null;
		else
			return val;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Crea un Bean i cui valori sono la somma dei singoli valori dei Beans introdotti come addendi
	 * @param addendi la lista dei beans che andranno sommati tra loro
	 * @param property il nome di una propriet� che andr� popolata con la somma del valore della propriet� corrispondende di ogni addendo
	 * @param props propriet� aggiuntive
	 * @return il bean rappresentante la somma degli addendi o null se non ci sono addendi
	 */
	public static <T> T accumulate(T[] addendi, String property, String... props) {
		if (addendi != null && addendi.length == 0) {
			try {
				return (T) addendi.getClass().getComponentType().newInstance();
			} catch (Exception e) {
				throw new RuntimeException("Accumulate failure, reason: "
						+ e.getMessage(), e);
			}
		}
		return accumulate(Arrays.asList(addendi), property, props);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Crea un Bean i cui valori sono la somma dei singoli valori dei Beans introdotti come addendi
	 * @param addendi la lista dei beans che andranno sommati tra loro
	 * @param property il nome di una propriet� che andr� popolata con la somma del valore della propriet� corrispondende di ogni addendo
	 * @param props propriet� aggiuntive
	 * @return il bean rappresentante la somma degli addendi o null se non ci sono addendi
	 */
	public static <T> T accumulate(Iterable<T> addendi, String property,
			String... props) {
		T result = null;
		if (addendi != null && addendi.iterator().hasNext()) {
			T next = addendi.iterator().next();

			try {
				result = (T) next.getClass().newInstance();

				List<String> propList = new ArrayList<String>();
				propList.add(property);
				if (props != null) {
					propList.addAll(Arrays.asList(props));
				}

				for (String propName : propList) {
					BeanUtil.setPropertyValueByName(result, propName,
							new BigDecimal(0));
				}

				for (T a : addendi) {
					for (String propName : propList) {
						BeanUtil.setPropertyValueByName(
								result,
								propName,
								NumberUtil.sum(
										NumberUtil
												.createScaledBigDecimal(BeanUtil
														.convert(
																BigDecimal.class,
																BeanUtil.getPropertyValueByName(
																		a,
																		propName))),
										BeanUtil.convert(
												BigDecimal.class,
												BeanUtil.getPropertyValueByName(
														result, propName))));
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("Accumulate failure, reason: "
						+ e.getMessage(), e);
			}
		}

		return result;
	}
	/**
	 * Che percentuale � 8 rispetto a  40? percentage(8,40)   --> returns 20
	 * @param percentage
	 * @param value
	 * @return
	 */
	public static BigDecimal percentage(BigDecimal a, BigDecimal b) {
		if (a == null || b == null) {
			return null;
		}

		return NumberUtil.divide(NumberUtil.multiply(new BigDecimal(100.0),
				createScaledBigDecimal(a)), createScaledBigDecimal(b));
	}
	/**
	 * Che percentuale � 8 rispetto a  40? percentage(8,40)   --> returns 20
	 * @param percentage
	 * @param value
	 * @return
	 */
	public static BigDecimal percentage(Double a, Double b) {
		if (a == null || b == null) {
			return null;
		}

		return percentage(createScaledBigDecimal(a), createScaledBigDecimal(b));
	}

	/**
	 * Quanto � il 20% di 40? percentageOf(20,40)   --> returns 8
	 * @param percentage
	 * @param value
	 * @return
	 */
	public static BigDecimal percentageOf(BigDecimal percentage,
			BigDecimal value) {
		if (percentage == null || value == null) {
			return null;
		}

		return NumberUtil.divide(NumberUtil.multiply(
				createScaledBigDecimal(percentage),
				createScaledBigDecimal(value)), new BigDecimal(100.0));
	}
	/**
	 * Quanto � il 20% di 40? percentageOf(20,40)   --> returns 8
	 * @param percentage
	 * @param value
	 * @return
	 */
	public static BigDecimal percentageOf(Double percentage, Double value) {
		if (percentage == null || value == null) {
			return null;
		}

		return percentageOf(createScaledBigDecimal(percentage),
				createScaledBigDecimal(value));
	}

	public static String getStringValueItalianFormat(Double val) {
		if (val == null || val.equals(Double.NaN)){
			return null;
		}
		return new java.text.DecimalFormat(DECIMAL_FORMAT,
				new java.text.DecimalFormatSymbols(java.util.Locale.ITALIAN))
				.format(val);
	}
	
	public static String getStringValueItalianFormatSetNanToNotAvailable(Double val) {
		String esito = null;
		if (val != null){
			if(val.equals(Double.NaN)){
				esito = "n/a";
			}else{
				esito = new DecimalFormat(DECIMAL_FORMAT,
						new DecimalFormatSymbols(java.util.Locale.ITALIAN))
				.format(val);
			}
		}
		return esito;
	}

	public static BigDecimal toBigDecimalFromItalianFormat(String str) {
		if (str == null || str.equals(""))
			return null;
		try {
			if (str.equals("0") || str.equals("0,0") || str.equals("0,00")
					|| str.equals("0.0") || str.equals("0.00"))
				return new BigDecimal("0");

			// valido per formato anglosassone

			return toBigDecimal(str);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Double toNullableDoubleItalianFormat(String str) {
		return toDouble(toBigDecimalFromItalianFormat(str));
	}

	public static String getItalianStringValue(String number) {
		String ret = "";
		Double d = toNullableDoubleItalianFormat(number);
		if (d != null)
			ret = getStringValueItalianFormat(d);

		return ret;
	}

	
	/**Returns a true if :
	 *  <p>
	 * - the string input param is a valid number <br> 
	 * - the decimal digits don't exceed the integer input param
	 * @param number             the number to check
	 * @param maxDecimalDigits   the max decimal digits allowed
	 */
	
	public static boolean isDecimalValid(String number, int maxDecimalDigits) {
		try {
			BigDecimal d = toBigDecimalNoDecimalDigitLimits(number);
			int scale = d.scale();
			if (scale <= maxDecimalDigits)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private static BigDecimal toBigDecimalNoDecimalDigitLimits(String str) {
		if (str == null || str.length() == 0)
			return null;
		BigDecimal ret = null;
		str = str.replace(',', '.');
		int indice = str.lastIndexOf('.');
		if (indice > -1) {
			String tmp = str.substring(0, indice);
			String coda = str.substring(indice + 1, str.length());
			tmp = tmp.replace(".", "");
			str = tmp + "." + coda;
		}
		try {
			ret = new BigDecimal(str);
		} catch (Exception x) {
			return null;
		}
		return ret;
	}

/**
 * Ritorna il numero rappresentato come stringa, con tante cifre decimali 
 * indicate dal parametro scale,quelle in eccedenza vengono tagliate,
 * il padding � dato dallo '0'
 * @param val
 * @param scale cifre decimali considerate nella conversione
 * @return
 */
	public static String getStringValueItalianFormat(Double val,int scale) {
		if (val == null)
			return null;
		String decimalFormat=DECIMAL_FORMAT;
		int indice = decimalFormat.lastIndexOf('.');
		if (indice > -1) {
			String tmp = decimalFormat.substring(0, indice+1);
			for (int i = 0; i < scale; i++) {
				tmp=tmp+"0";
			}
			decimalFormat=tmp;
		}
			
		return new java.text.DecimalFormat(decimalFormat,
				new java.text.DecimalFormatSymbols(java.util.Locale.ITALIAN))
				.format(val);
	}

	public static boolean isImportoNoDecimalDigitLimits(String str, int maxIntegerDigits) {
		
			if (maxIntegerDigits < 1)
				return false;
			try {
				BigDecimal d = toBigDecimalNoDecimalDigitLimits(str);
				if(d==null)
					return false;
				Long n = d.longValue();
				if (n.toString().length() > maxIntegerDigits)
					return false;
				else
					return true;
			} catch (Exception e) {
				return false;
			}		
	}

	
	public static Double toNullableDoubleNoDecimalDigitLimits(String str) {
		return toDouble(toBigDecimalNoDecimalDigitLimits(str));
	}
	
	
	
	public static void main(String[] args) {
		BigDecimal val=new BigDecimal(123498712345.567);
		System.out.println(val);
		System.out.println(getStringValueAnglFormat(val));
	}
	
	
}
