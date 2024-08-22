/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.util;

import java.math.BigDecimal;

public class CommonUtil {

	public static final int LUNGH_PARTITA_IVA = 11;

	/**
	 * Verifica se una stringa è null o vuota
	 * 
	 * @param stringa
	 * @return
	 */
	public static boolean isEmpty(String stringa) {
		return (stringa == null || stringa.trim().equals(""));
	}
	
	/**
	 * @param s
	 * @return
	 */
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
	
	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str == null || str.equals(""))
			return false;

		if (toBigDecimal(str) != null)
			return true;
		else
			return false;
	}

	/**
	 * @param str
	 * @return
	 */
	private static BigDecimal toBigDecimal(String str) {
		if (str == null || str.length() == 0)
			return null;
		BigDecimal ret = null;
		str = str.replace(',', '.');
		int indice = str.lastIndexOf('.');
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
	 * Controlla la validità di un codice fiscale in base alla lunghezza
	 * 
	 * @param codiceFiscale
	 * @return
	 */
	public static boolean checkCodiceFiscale(String codiceFiscale) {

		boolean isCodiceFiscale = false;

		if (!codiceFiscale.isEmpty() && (codiceFiscale.length() == 16 || codiceFiscale.length() == 11)) {
			isCodiceFiscale = true;
		}

		return isCodiceFiscale;

	}

	/**
	 * Controlla la validità di una partita IVA
	 * 
	 * @param partitaIVA
	 * @return
	 */
	public static boolean checkPartitaIVA(String partitaIVA) {

		boolean isPartitaIVA = true;

		if (partitaIVA.length() != LUNGH_PARTITA_IVA) {
			isPartitaIVA = false;
		} else {
			for (int i = 0; i < 11; i++) {
				if (partitaIVA.charAt(i) < '0' || partitaIVA.charAt(i) > '9') {
					isPartitaIVA = false;
				}
			}
			int s = 0;
			for (int i = 0; i <= 9; i += 2) {
				s += partitaIVA.charAt(i) - '0';
			}

			for (int i = 1; i <= 9; i += 2) {
				int c = 2 * (partitaIVA.charAt(i) - '0');
				if (c > 9)
					c = c - 9;
				s += c;
			}
			if ((10 - s % 10) % 10 != partitaIVA.charAt(10) - '0') {
				isPartitaIVA = false;
			}
		}

		return isPartitaIVA;

	}

}
