/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandiutil.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

//----------------------------------------------------------------------------
// StringUtil
//----------------------------------------------------------------------------
public class StringUtil {

 
	/** An empty immutable <code>String</code> array. */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	// --------------------------------------------------------------------------
	// isString
	// verifica se una stringa e' formata da soli caratteri
	// --------------------------------------------------------------------------

	public static boolean isString(String s) {
		boolean hasValidLetters = false;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);

			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
				hasValidLetters = true;
			else {
				hasValidLetters = false;
				break;
			}

		}
		return hasValidLetters;
	}

	/** Alfabeto */
	private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static boolean isFormatoCarattere(String stringa) {
		if (!StringUtil.contieneSolo(stringa.toUpperCase(), ALFABETO))
			return false;
		return true;
	}

	public static boolean isAlfaNumerico(String s) {
		boolean ret = false;

		for (int i = 0; i < s.length(); i++) {
			char ch = s.toUpperCase().charAt(i);

			if ((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) {
				ret = true;
			} else {
				ret = false;
				break;
			}
		}
		return ret;
	}

	// --------------------------------------------------------------------------
	// isNumeric
	// verifica se una stringa e' formata da soli numeri
	// --------------------------------------------------------------------------
	public static boolean isNumeric(String str) {
		if (str == null)
			return false;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '.'
					&& (str.charAt(i) < '0' || '9' < str.charAt(i)))
				return false;
		}

		return true;
	}

	// --------------------------------------------------------------------------
	// getIdentificatore
	// restituisce l'identificatore di dieci caratteri per la registrazione
	// --------------------------------------------------------------------------
	public static String getIdentificatore() {
		Random r = new Random();

		double x = 0;
		int y = 0;

		String id = "";

		for (int i = 0; i < 10; i++) {
			x = r.nextDouble();
			y = (int) (x * 62.0) + 1;

			if (1 <= y && y <= 10)
				id += (char) ('0' + (y - 1));

			else if (11 <= y && y <= 36)
				id += (char) ('A' + (y - 11));

			else
				id += (char) ('a' + (y - 37));
		}

		return id;
	}

	// --------------------------------------------------------------------------
	// getPassword
	// restituisce la password di otto caratteri per la registrazione
	// --------------------------------------------------------------------------
	public static String getPassword() {
		Random r = new Random();

		double x = 0;
		int y = 0;

		String id = "";

		for (int i = 0; i < 8; i++) {
			x = r.nextDouble();
			y = (int) (x * 62.0) + 1;

			if (1 <= y && y <= 10)
				id += (char) ('0' + (y - 1));

			else if (11 <= y && y <= 36)
				id += (char) ('A' + (y - 11));

			else
				id += (char) ('a' + (y - 37));
		}

		return id;
	}

	public static boolean isDouble(String str) {

		if (str == null || str.equals(""))
			return true;
		str = str.replace(',', '.');
		try {
			double m = Double.parseDouble(str);
			if (m < 0.0)
				return false;

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Truncate
	 */
	public static String getStringTruncated(double d) {

		String str = "" + d;
		if (str.indexOf(".") < str.length() - 3)
			str = str.substring(0, str.indexOf(".") + 3);

		return str;
	}

	public static boolean checkValueIsNotTooLong(String value, int maxIntegers) {

		String str = value;
		String nIntegers = "";

		if (str != null && !str.equals("")) {
			if (str.indexOf('.') > -1) {
				nIntegers = str.substring(0, str.indexOf('.'));
			} else
				nIntegers = str;

			if (nIntegers.length() > maxIntegers)
				return false;
		}
		return true;
	}

	/*
	 * public static boolean isValidDate(String _data) { return
	 * isValidDate(_data, null); }
	 */
	/*
	 * public static boolean isValidDate(String _data, String _separ) { if
	 * (_data == null) return false;
	 * 
	 * try { if (_separ == null || _separ.equals("")) _separ = "/";
	 * 
	 * String gg = _data.substring(0, 2), sh1 = _data.substring(2, 3), mm =
	 * _data.substring(3, 5), sh2 = _data.substring(5, 6), aa =
	 * _data.substring(6, 10);
	 * 
	 * if (!sh1.equals(_separ) || !sh2.equals(_separ)) return false;
	 * 
	 * int g = toInt(gg), m = toInt(mm), a = toInt(aa), bisest = 0;
	 * 
	 * if (g == -1 || m == -1 || a == -1) return false;
	 * 
	 * if (a < 1900 || 2100 < a) return false;
	 * 
	 * if (m < 1 || 12 < m) return false;
	 * 
	 * if (g < 1) return false;
	 * 
	 * switch(m) { case 2: if (g % 400 == 0 || (g % 4 == 0 && g % 100 != 0))
	 * bisest = 1;
	 * 
	 * if (g > 28 + bisest) return false;
	 * 
	 * break;
	 * 
	 * case 4: case 6: case 9: case 11: if (g > 30) return false;
	 * 
	 * break;
	 * 
	 * default: if (g > 31) return false; } } catch(Exception e) { return false;
	 * }
	 * 
	 * return true; }
	 */
	// --------------------------------------------------------------------------
	// checkForException:
	// stampa tutte le eccezioni
	// --------------------------------------------------------------------------
	public static void checkForException(Exception ex, String strFunc) {
		if (ex == null)
			return;

	}

	/**
	 * NO BLANK SPACES return a string without spaces
	 */
	public String noBlankSpaces(String s) {
		String part = "";
		String str = "";
		char ch = ' ';
		if (s.indexOf(" ") > -1) {
			for (int i = 0; i < s.length(); i++) {
				ch = s.charAt(i);
				if (ch != ' ') {
					str = str + ch;
				}

			}
		} else
			str = s;

		return str;
	}

	/**
	 * Inserisce automaticamente una stringa di escape davanti ai caratteri
	 * jolly '%' e '_'
	 * 
	 * @param string
	 *            la stringa da modificare
	 * @param escape
	 *            il carattere da usare come escape
	 * @return la stringa modificata con i caratteri di escape
	 */
	public static String escapeWildcards(String string, char escape) {
		String escapeString = Character.toString(escape);
		String result = string.replaceAll(escapeString, escapeString
				+ escapeString);
		result = result.replaceAll("%", escapeString + "%");
		result = result.replaceAll("_", escapeString + "_");
		return result;
	}

	/**
	 * isValidPassword controlla che la password sia formata solo da caratteri
	 * alfanumerici (non tutti alfabetici e non tutti numerici) e da almeno un
	 * carattere speciale Restituisce :
	 * <ul>
	 * <li>0: se la password � valida
	 * <li>1: se contiene caratteri non validi
	 * <li>2: se non contiene lettere
	 * <li>3: se non contiene numeri
	 * <li>4: se non contiene caratteri speciali
	 * </ul>
	 * 
	 */
	public static int isValidPassword(String s) {
		boolean hasInvalidChars = false;
		boolean hasDigits = false;
		boolean hasValidLetters = false;
		boolean hasSpecialChar = false;

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);

			if (ch >= '0' && ch <= '9')
				hasDigits = true;
			else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
				hasValidLetters = true;
			else if ((ch == '.') || (ch == '-') || (ch == '_') || (ch == '?')
					|| (ch == '!') || (ch == '&') || (ch == '$') || (ch == '%')
					|| (ch == '#') || (ch == '@') || (ch == '*'))
				hasSpecialChar = true;
			else {
				hasInvalidChars = true;
				break;
			}
		}
		if (hasInvalidChars)
			return 1;
		if (!hasValidLetters)
			return 2;
		if (!hasDigits)
			return 3;
		if (!hasSpecialChar)
			return 4;
		return 0;
	}

	public static String replaceSlash(String stringa, char newchar) {
		if (stringa != null)
			stringa = replace(stringa, '/', newchar);
		return stringa;
	}

	public static String replaceSpace(String stringa, char newchar) {
		if (stringa != null)
			stringa = replace(stringa, (char) 32, newchar);
		return stringa;
	}

	public static String replace(String stringa, char oldchar, char newchar) {
		if (stringa != null)
			stringa = stringa.replace(oldchar, newchar);
		return stringa;
	}

	/** Lista vocali accentate */
	private static final String LISTA_ACCENTATE = "������������";

	/**
	 * Lista vocali senza accento (vedi <code>LISTA_ACCENTATE</code>).
	 */
	private static final String LISTA_NON_ACCENTATE = "AEEIOUaeeiou";

	// sostituisce in str tutte le occorrenze di oldStr con newStr
	public static String replace(String str, String oldStr, String newStr) {
		if (str == null || "".equals(str))
			return "";

		String res = "";
		int l = oldStr.length();
		int i = 0;

		while ((i = str.indexOf(oldStr)) != -1) {
			res += str.substring(0, i) + newStr;
			str = str.substring(i + l);
		}

		res += str;
		return res;
	}

	public static String replaceLast(String str, String oldStr, String newStr) {
		if (str == null || "".equals(str))
			return "";

		String res = "";
		int l = oldStr.length();
		int i = 0;

		while ((i = str.indexOf(oldStr)) != -1) {
			res += str.substring(0, i) + newStr;
			str = str.substring(i + l);
		}

		res += str;
		return res;
	}

	/**
	 * Restituisce la stringa in input sostituendo le lettere accentate. Es.
	 * "and�", diventa "ando"
	 * 
	 * @param testo
	 *            Stringa da trasformare
	 * @return Stringa trasformata
	 */
	public static String rimuoveAccenti(String testo) {
		int i = 0;
		// scorre la stringa originale
		while (i < testo.length()) {
			int p = LISTA_ACCENTATE.indexOf(testo.charAt(i));
			// se ha trovato una lettera accentata
			if (p > -1) {
				// sostituisce con la relativa non accentata
				testo = testo.substring(0, i) + LISTA_NON_ACCENTATE.charAt(p)
						+ testo.substring(i + 1);
			}
			i++;
		}

		return testo;
	}

	/**
	 * Restituisce true se il parametro testo contiene almeno uno dei caratteri
	 * del parametro alfabeto
	 * 
	 * @param testo
	 * @param alfabeto
	 * @return
	 */
	public static boolean contieneAlmenoUnoAlfabeto(String testo,
			String alfabeto) {
		int i = 0;
		// scorre la stringa originale
		while (i < testo.length()) {
			int p = alfabeto.indexOf(testo.charAt(i));
			// se ha trovato una lettera accentata
			if (p > -1) {
				return true;
			}
			i++;
		}

		return false;
	}

	/**
	 * Restituisce true se il parametro testo contiene solo caratteri del
	 * parametro alfabeto
	 * 
	 * @param testo
	 * @param alfabeto
	 * @return
	 */
	public static boolean contieneSolo(String testo, String alfabeto) {
		int i = 0;
		// scorre la stringa originale
		while (i < testo.length()) {
			int p = alfabeto.indexOf(testo.charAt(i));
			// se ha trovato una lettera accentata
			if (p < 0) {
				return false;
			}
			i++;
		}

		return true;
	}

	public static String getStringaVuotaIfNull(String stringa) {
		if (stringa != null)
			return stringa;
		else
			return "";

	}

	public static String replacePunto(String stringa, char newchar) {
		if (stringa != null)
			stringa = replace(stringa, '.', newchar);
		return stringa;
	}

	public static String changeCharset(String x, String oldS, String newS) {
		String ret = "";
		if (x.indexOf(oldS) > 0) {
			int index = x.indexOf(oldS);
			String pre = x.substring(0, x.indexOf(oldS));
			String post = x.substring((index + 5), x.length());
			ret = pre + newS + post;
		}
		return ret;

	}

	/**
	 * <p>
	 * Find the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains(&quot;&quot;, &quot;&quot;)      = true
	 * StringUtils.contains(&quot;abc&quot;, &quot;&quot;)   = true
	 * StringUtils.contains(&quot;abc&quot;, &quot;a&quot;)  = true
	 * StringUtils.contains(&quot;abc&quot;, &quot;z&quot;)  = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * 
	 * @return true if the String contains the search character, false if not or
	 *         <code>null</code> string input
	 * 
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return (str.indexOf(searchStr) >= 0);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separators specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.split(null, *, *)            = null
	 * StringUtils.split(&quot;&quot;, *, *)              = []
	 * StringUtils.split(&quot;ab de fg&quot;, null, 0)   = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.split(&quot;ab   de fg&quot;, null, 0) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 0)    = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)    = [&quot;ab&quot;, &quot;cdef&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @param max
	 *            the maximum number of elements to include in the array. A zero
	 *            or negative value implies no limit
	 * 
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str, String separatorChars, int max) {
		// Performance tuned for 2.0 (JDK1.4)
		// Direct code is quicker than StringTokenizer.
		// Also, StringTokenizer uses isSpace() not isWhitespace()

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		if (separatorChars == null) {
			// Null separator means use whitespace
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match) {
						if (sizePlus1++ == max) {
							i = len;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// Optimise 1 character case
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match) {
						if (sizePlus1++ == max) {
							i = len;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				match = true;
				i++;
			}
		} else {
			// standard case
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match) {
						if (sizePlus1++ == max) {
							i = len;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				match = true;
				i++;
			}
		}
		if (match) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String join(java.util.List<String> s, String delimiter) {
		if (s.isEmpty())
			return "";
		Iterator<String> iter = s.iterator();
		StringBuffer buffer = new StringBuffer(iter.next());
		while (iter.hasNext()) {
			String propertyValueByName = iter.next();
			buffer.append(delimiter).append(propertyValueByName);
		}
		return buffer.toString();
	}

	public static boolean isStringBufferNotEmpty(StringBuffer sb) {
		return (sb != null && !sb.toString().trim().equals(""));
	}

	public static String aggiungiZeroInizialiAlCap(String cap) {
		if (cap != null) {
			if (cap.length() == 3) {
				cap = "00" + cap;
			} else if (cap.length() == 4) {
				cap = "0" + cap;
			}
		}
		return cap;
	}

	public static boolean isEmpty(String stringa) {
		return (stringa == null || stringa.trim().equals(""));
	}

	public static String toUpperCase(String s) {
		if (s != null)
			return s.toUpperCase();
		else
			return null;
	}

	/*
	 * public static String toItalianFormat(String s){ if(s==null ||
	 * s.length()==0) return ""; else{ if(s.lastIndexOf(',')==s.length()-3)
	 * return s; else if(s.lastIndexOf('.')<=s.length()-3){ s =
	 * s.replace(",","."); //if(s.lastIndexOf('.')==s.length()-3){
	 * s=s.substring(
	 * 0,s.lastIndexOf('.'))+","+s.substring(s.lastIndexOf('.')+1); //} return
	 * s; } else if(s.lastIndexOf('.')>-1){ return s; }else
	 * if(s.lastIndexOf(',')>-1){ s = s.replace(",","."); }
	 * 
	 * } return s; }
	 */
	
	
	/**
	 * Tronca una stringa al numero di caratteri indicato
	 * 
	 * 
	 * @param string
	 *            la stringa da troncare
	 * @param numOfChars
	 *            il numero di caratteri a cui troncare la stringa
	 * @return la stringa troncata se valida, null se la stringa � nulla, 
	 * 		   la stringa intera se il numero di caratteri � maggiore della lunghezza
	 */
	public static String truncate(String string, int numOfChars){
		String newString = null;
		if(string!=null){
			if(string.length()<numOfChars){
				newString = string;
			}
			else{
				newString = string.substring(0, numOfChars);
			}
		}
		return newString;
	}

	
	public static String hash(String strToHash) {
		if (strToHash == null)
			return null;
		strToHash = strToHash.toLowerCase();
		String ruoloFormattato = "!" + strToHash + "pbandi";
		String hash = DigestUtils.shaHex(ruoloFormattato);
		return hash;
	}
	
	
}
