/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ValidatorCodiceFiscale {
	/**
	 * Codice relativo ai mesi dell'anno.
	 */
	
	
	
	private static final String MESI = "ABCDEHLMPRST";

	/** Vocali */
	private static final String VOCALI = "AEIOU";

	/** Consonanti */
	private static final String CONSONANTI = "BCDFGHJKLMNPQRSTVWXYZ";

	/** Alfabeto */
	private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final int LUNGH_CODICE_FISCALE_PERSONA_GIURIDICA = 11;
	private static final int LUNGH_CODICE_FISCALE_PERSONA_FISICA = 16;

	/** Matrice per il calcolo del carattere di controllo */
	private static int[][] matriceCodiceControllo = null;

	static {
		matriceCodiceControllo = new int[91][2];
		matriceCodiceControllo[0][1] = 1;
		matriceCodiceControllo[0][0] = 0;
		matriceCodiceControllo[1][1] = 0;
		matriceCodiceControllo[1][0] = 1;
		matriceCodiceControllo[2][1] = 5;
		matriceCodiceControllo[2][0] = 2;
		matriceCodiceControllo[3][1] = 7;
		matriceCodiceControllo[3][0] = 3;
		matriceCodiceControllo[4][1] = 9;
		matriceCodiceControllo[4][0] = 4;
		matriceCodiceControllo[5][1] = 13;
		matriceCodiceControllo[5][0] = 5;
		matriceCodiceControllo[6][1] = 15;
		matriceCodiceControllo[6][0] = 6;
		matriceCodiceControllo[7][1] = 17;
		matriceCodiceControllo[7][0] = 7;
		matriceCodiceControllo[8][1] = 19;
		matriceCodiceControllo[8][0] = 8;
		matriceCodiceControllo[9][1] = 21;
		matriceCodiceControllo[9][0] = 9;
		matriceCodiceControllo[10][1] = 1;
		matriceCodiceControllo[10][0] = 0;
		matriceCodiceControllo[11][1] = 0;
		matriceCodiceControllo[11][0] = 1;
		matriceCodiceControllo[12][1] = 5;
		matriceCodiceControllo[12][0] = 2;
		matriceCodiceControllo[13][1] = 7;
		matriceCodiceControllo[13][0] = 3;
		matriceCodiceControllo[14][1] = 9;
		matriceCodiceControllo[14][0] = 4;
		matriceCodiceControllo[15][1] = 13;
		matriceCodiceControllo[15][0] = 5;
		matriceCodiceControllo[16][1] = 15;
		matriceCodiceControllo[16][0] = 6;
		matriceCodiceControllo[17][1] = 17;
		matriceCodiceControllo[17][0] = 7;
		matriceCodiceControllo[18][1] = 19;
		matriceCodiceControllo[18][0] = 8;
		matriceCodiceControllo[19][1] = 21;
		matriceCodiceControllo[19][0] = 9;
		matriceCodiceControllo[20][1] = 2;
		matriceCodiceControllo[20][0] = 10;
		matriceCodiceControllo[21][1] = 4;
		matriceCodiceControllo[21][0] = 11;
		matriceCodiceControllo[22][1] = 18;
		matriceCodiceControllo[22][0] = 12;
		matriceCodiceControllo[23][1] = 20;
		matriceCodiceControllo[23][0] = 13;
		matriceCodiceControllo[24][1] = 11;
		matriceCodiceControllo[24][0] = 14;
		matriceCodiceControllo[25][1] = 3;
		matriceCodiceControllo[25][0] = 15;
		matriceCodiceControllo[26][1] = 6;
		matriceCodiceControllo[26][0] = 16;
		matriceCodiceControllo[27][1] = 8;
		matriceCodiceControllo[27][0] = 17;
		matriceCodiceControllo[28][1] = 12;
		matriceCodiceControllo[28][0] = 18;
		matriceCodiceControllo[29][1] = 14;
		matriceCodiceControllo[29][0] = 19;
		matriceCodiceControllo[30][1] = 16;
		matriceCodiceControllo[30][0] = 20;
		matriceCodiceControllo[31][1] = 10;
		matriceCodiceControllo[31][0] = 21;
		matriceCodiceControllo[32][1] = 22;
		matriceCodiceControllo[32][0] = 22;
		matriceCodiceControllo[33][1] = 25;
		matriceCodiceControllo[33][0] = 23;
		matriceCodiceControllo[34][1] = 24;
		matriceCodiceControllo[34][0] = 24;
		matriceCodiceControllo[35][1] = 23;
		matriceCodiceControllo[35][0] = 25;
		;
	}

	/**
	 * Restituisce i 3 caratteri del codice fiscale derivati dal cognome.
	 * 
	 * @param cognome
	 *            Cognome completo.
	 * @return cognome codificato.
	 */
	private static String codificaCognome(String cognome) {
		int i = 0;
		String stringa = "";
		// trova consonanti
		while ((stringa.length() < 3) && (i + 1 <= cognome.length())) {
			if (CONSONANTI.indexOf(cognome.charAt(i)) > -1) {
				stringa += cognome.charAt(i);
			}
			i++;
		}
		i = 0;
		// se non bastano prende vocali
		while ((stringa.length() < 3) && (i + 1 <= cognome.length())) {
			if (VOCALI.indexOf(cognome.charAt(i)) > -1) {
				stringa += cognome.charAt(i);
			}
			i++;
		}
		// se non bastano aggiungo le x
		if (stringa.length() < 3) {
			for (i = stringa.length(); i < 3; i++) {
				stringa += "X";
			}
		}
		return stringa;
	}

	/**
	 * Restituisce i 3 caratteri del codice fiscale derivati dal nome.
	 * 
	 * @param nome
	 *            Nome completo.
	 * @return nome codificato.
	 */
	private static String codificaNome(String nome) {
		int i = 0;
		String stringa = "";
		String cons = "";
		// trova consonanti
		while ((cons.length() < 4) && (i + 1 <= nome.length())) {
			if (CONSONANTI.indexOf(nome.charAt(i)) > -1) {
				cons += nome.charAt(i);
			}
			i++;
		}
		// se sono + di 3 prende 1� 3� 4�
		if (cons.length() > 3) {
			stringa = cons.substring(0, 1) + cons.substring(2, 4);
			return stringa;
		} else {
			stringa = cons;
		}
		i = 0;
		// se non bastano prende vocali
		while ((stringa.length() < 3) && (i + 1 <= nome.length())) {
			if (VOCALI.indexOf(nome.charAt(i)) > -1) {
				stringa += nome.charAt(i);
			}
			i++;
		}
		// se non bastano aggiungo le x
		if (stringa.length() < 3) {
			for (i = stringa.length(); i < 3; i++) {
				stringa += "X";
			}
		}
		return stringa;

	}
	
	public static boolean isCodiceFiscalePersonaFisicaValido(String codiceFiscale) {
		return (codiceFiscale != null) && 
		(codiceFiscale.length() == LUNGH_CODICE_FISCALE_PERSONA_FISICA) &&
		(isCodiceControlloCorretto(codiceFiscale.toUpperCase()));
	}

	@Deprecated
	public static boolean isCodiceControlloValido(String codiceFiscale) {
		return isCodiceControlloCorretto(codiceFiscale.toUpperCase());
	}
	
	@Deprecated
	public static void checkLength(String codiceFiscale)throws Exception{
		if(codiceFiscale==null || codiceFiscale.length()!=LUNGH_CODICE_FISCALE_PERSONA_FISICA)
			throw new Exception("Codice fiscale "+codiceFiscale+" non corretto");
	}
	
	@Deprecated
	public static void checkCodiceControllo(String codiceFiscale) throws Exception{
		if(!isCodiceControlloCorretto(codiceFiscale.toUpperCase()))
			throw new Exception("Codice fiscale "+codiceFiscale+" non corretto");
	}
	
	private static boolean isCodiceControlloCorretto(String codiceFiscale) {
		boolean  ok=false;
		try {
			int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11,
					3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	
			int s = 0;
			//try {
			for (int i = 1; i <= 13; i += 2) {
				int c = codiceFiscale.charAt(i);
				if (c >= '0' && c <= '9') {
					s = s + c - '0';
				} else {
					s = s + c - 'A';
				}
			}
			for (int i = 0; i <= 14; i += 2) {
				int c = codiceFiscale.charAt(i);
				if (c >= '0' && c <= '9') {
					c = c - '0' + 'A';
				}
				s = s + setdisp[c - 'A'];
			}
			ok=true;
			if (s % 26 + 'A' != codiceFiscale.charAt(15))
				ok=false;
		}catch(Exception ex) {
			ok=false;
		}
		return ok;
	}

	public static boolean isCodiceValido(String codiceFiscale, String nome,
			String cognome, Date dataNascita, boolean isMaschio) {
		String cf2;

		if (StringUtil.isEmpty(codiceFiscale))
			return false;

		if (codiceFiscale.length() != LUNGH_CODICE_FISCALE_PERSONA_FISICA) {
			return false;
		}

		cf2 = codiceFiscale.toUpperCase();
		for (int i = 0; i < LUNGH_CODICE_FISCALE_PERSONA_FISICA; i++) {
			int c = cf2.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
				return false;
			}
		}

		if (!isCodiceControlloCorretto(cf2))
			return false;

		if (StringUtil.isEmpty(nome) || StringUtil.isEmpty(cognome))
			return false;

		if (!verificaNomeCognome(nome.toUpperCase(), cognome.toUpperCase(), cf2))
			return false;

		if (!verificaDataNascita(dataNascita, isMaschio, cf2))
			return false;

		return true;
	}

	private static boolean verificaDataNascita(Date dataNascita,
			boolean isMaschio, String cf2) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dataNascita);
		String a = Integer.toString(cal.get(Calendar.YEAR));
		a = a.substring(a.length() - 2, a.length());
		int anno = Integer.parseInt(a);
		int mese = cal.get(Calendar.MONTH);
		int giorno = cal.get(Calendar.DATE);
		if (!isMaschio) {
			giorno = giorno + 40;
		}
		String annoMeseGiorno = ((anno < 10) ? "0" : "")
				+ Integer.toString(anno) + MESI.charAt(mese)
				+ ((giorno < 10) ? "0" : "") + Integer.toString(giorno);

		if (cf2.indexOf(annoMeseGiorno) != 6)
			return false;
		else
			return true;
	}

	private static boolean verificaNomeCognome(String nome, String cognome,
			String cf2) {
		String cognomeTrattato = StringUtil.rimuoveAccenti(cognome.trim());
		
		String nomeTrattato = StringUtil.rimuoveAccenti(nome.trim());

		String primeSeiLettere = codificaCognome(cognomeTrattato)
				+ codificaNome(nomeTrattato);

		if (cf2.indexOf(primeSeiLettere) != 0)
			return false;
		else
			return true;
	}

	
	public static void controllaCodiceFiscalePersonaGiuridica(String codiceFiscalePersonaGiuridica) throws Exception{
		

		if (codiceFiscalePersonaGiuridica.length() == 0) {
		
			throw new Exception ("Il codice fiscale della persona giuridica deve essere lungo 11 caratteri");

		}

		if (codiceFiscalePersonaGiuridica.length() != LUNGH_CODICE_FISCALE_PERSONA_GIURIDICA) {
			
			throw new Exception ( "Il codice fiscale della persona giuridica deve essere lungo 11 caratteri");
		}
		for (int i = 0; i < 11; i++) {
			if (codiceFiscalePersonaGiuridica.charAt(i) < '0' || codiceFiscalePersonaGiuridica.charAt(i) > '9') {
				throw new Exception ( "La partita IVA contiene dei caratteri non ammessi: "
						+ "la partita IVA dovrebbe contenere solo cifre.");
			}
		}
		int s = 0;
		for (int i = 0; i <= 9; i += 2) {
			s += codiceFiscalePersonaGiuridica.charAt(i) - '0';
		}

		for (int i = 1; i <= 9; i += 2) {
			int c = 2 * (codiceFiscalePersonaGiuridica.charAt(i) - '0');
			if (c > 9)
				c = c - 9;
			s += c;
		}
		if ((10 - s % 10) % 10 != codiceFiscalePersonaGiuridica.charAt(10) - '0') {
			
			throw new Exception ( "Codice fiscale della persona giuridica non valido: il codice di controllo non corrisponde.");
		}

	
	}

	
	
}
