/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.util;

public class ValidatorPartitaIva {


	public static final int LUNGH_PARTITA_IVA = 11;

	public static void controllaPartitaIVA(String partitaIVA) throws Exception{


		if (partitaIVA.length() == 0) {
			throw new Exception ("La partita IVA deve essere lunga 11 caratteri.");

		}

		if (partitaIVA.length() != LUNGH_PARTITA_IVA) {

			throw new Exception ( "La partita IVA deve essere lunga 11 caratteri.");
		}
		for (int i = 0; i < 11; i++) {
			if (partitaIVA.charAt(i) < '0' || partitaIVA.charAt(i) > '9') {
				throw new Exception ( "La partita IVA deve contenere solo cifre.");
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
			throw new Exception ( "Partita IVA non valida: il codice di controllo non corretto.");
		}

	}

	public static boolean isPartitaIvaValid(String partitaIVA) {



		boolean isValid = true;

		if (partitaIVA.length() == 0) {
			isValid = false;
		}

		else {

			if (partitaIVA.length() != LUNGH_PARTITA_IVA) {
				isValid = false;
			} else {
				for (int i = 0; i < 11; i++) {
					if (partitaIVA.charAt(i) < '0'
							|| partitaIVA.charAt(i) > '9') {
						isValid = false;
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
					isValid = false;
				}
			}
		}

		
		return isValid;
	}

}
