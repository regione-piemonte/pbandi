/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

// ----------------------------------------------------------------------------
// DataUtil
// ----------------------------------------------------------------------------
public class DateUtil {

	static Logger logger = Logger.getLogger(DateUtil.class.getName());

	// --------------------------------------------------------------------------
	// getTimeStamp
	// restituisce un long con il numero di millisecondi
	// trascorsi dal 01/01/1970 00:00:00 GMT
	// --------------------------------------------------------------------------
	public static long getTimeStamp() {
		// return Calendar.getInstance().getTime().getTime();
		return System.currentTimeMillis();
	}

	// --------------------------------------------------------------------------
	// getData
	// restituisce una stringa con la data odierna
	// nel formato: gg/mm/aaaa
	// --------------------------------------------------------------------------
	public static String getData() {

		Calendar adesso = new GregorianCalendar(Locale.ITALY);

		adesso.setTime(new java.util.Date());

		int nGiorno = adesso.get(Calendar.DATE), nMese = adesso
				.get(Calendar.MONTH) + 1, nAnno = adesso.get(Calendar.YEAR);

		String strDate = (nGiorno < 10) ? "0" + nGiorno : "" + nGiorno;

		strDate += (nMese < 10) ? "/0" + nMese : "/" + nMese;
		strDate += "/" + nAnno;

		return strDate;
	}

	/**
	 * Una copia del metodo getData(), con un parametro in input.
	 * 
	 * @author 71732
	 * @param timeInMillis
	 *            i millisecondi trascorsi dal 01/01/1970 00:00:00 GMT della
	 *            data che si vuole convertire
	 * @return la data convertita in formato DD/MM/YYYY
	 */
	public static String getData(long timeInMillis) {

		Calendar cal = new GregorianCalendar(Locale.ITALY);

		cal.setTime(new java.util.Date(timeInMillis));

		int nGiorno = cal.get(Calendar.DATE), nMese = cal.get(Calendar.MONTH) + 1, nAnno = cal
				.get(Calendar.YEAR);

		String strDate = (nGiorno < 10) ? "0" + nGiorno : "" + nGiorno;

		strDate += (nMese < 10) ? "/0" + nMese : "/" + nMese;
		strDate += "/" + nAnno;

		return strDate;
	}

	// --------------------------------------------------------------------------
	// getOra
	// restituisce una stringa con l'ora attuale
	// nel formato: hh:mm:ss.mmm
	// --------------------------------------------------------------------------
	public static String getOra() {
		Calendar adesso = Calendar.getInstance(TimeZone.getTimeZone("CET"));

		int nOra = adesso.get(Calendar.HOUR), nMinuto = adesso
				.get(Calendar.MINUTE), nSecondo = adesso.get(Calendar.SECOND), nMilliSec = adesso
				.get(Calendar.MILLISECOND);

		String strTime = (nOra < 10) ? "0" + nOra : "" + nOra;

		strTime += (nMinuto < 10) ? ":0" + nMinuto : ":" + nMinuto;
		strTime += (nSecondo < 10) ? ":0" + nSecondo : ":" + nSecondo;
		strTime += (nMilliSec < 100) ? ".0"
				+ ((nMilliSec < 10) ? "0" + nMilliSec : "" + nMilliSec) : "."
				+ nMilliSec;

		return strTime;
	}

	public static String getOraCorta() {
		Calendar adesso = Calendar.getInstance(TimeZone.getTimeZone("CET"));

		int nOra = adesso.get(Calendar.HOUR), nMinuto = adesso
				.get(Calendar.MINUTE);

		String strTime = (nOra < 10) ? "0" + nOra : "" + nOra;

		strTime += (nMinuto < 10) ? ":0" + nMinuto : ":" + nMinuto;

		return strTime;
	}

	// --------------------------------------------------------------------------
	// getTimeLog
	// restituisce una stringa per il file di log
	// nel formato: [gg/mm/aaaa hh:mm:ss.mmm]
	// --------------------------------------------------------------------------
	public static String getTimeLog() {
		return "[" + getData() + " " + getOra() + "]";
	}

	// --------------------------------------------------------------------------
	// getGiorno
	// restituisce un int con il numero del giorno di oggi
	// --------------------------------------------------------------------------
	public static int getGiorno() {
		return Calendar.getInstance(TimeZone.getTimeZone("ECT")).get(
				Calendar.DATE);
	}

	public static String getGiorno(String _data) {

		if (!isValidDate(_data))
			return "";
		return _data.substring(0, 2);
	}

	// --------------------------------------------------------------------------
	// getMese
	// restituisce un int con il numero del mese di oggi
	// --------------------------------------------------------------------------
	public static int getMese() {
		return Calendar.getInstance(TimeZone.getTimeZone("ECT")).get(
				Calendar.MONTH) + 1;
	}

	public static String getMese(String _data) {
		if (!isValidDate(_data))
			return "";

		return _data.substring(3, 5);
	}

	// --------------------------------------------------------------------------
	// getAnno
	// restituisce un int con il numero dell'anno di oggi
	// --------------------------------------------------------------------------
	public static int getAnno() {
		return Calendar.getInstance(TimeZone.getTimeZone("CET")).get(
				Calendar.YEAR);
	}

	public static int getAnno(String _data) {
		if (!isValidDate(_data))
			return -1;

		return NumberUtil.toInt(_data.substring(6, 10));
	}

	// --------------------------------------------------------------------------
	// getDate
	// --------------------------------------------------------------------------
	public static Date getDate(String _data) {
		Date data = null;
		try {
			if (_data == null || _data.trim().equals("")) {
				logger.info("[StringUtil::getDate] Inserita data nulla:["
						+ _data + "]");
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			sdf.setLenient(false);
			data = sdf.parse(_data);

			if (data != null
					&& data.before(new Date(new GregorianCalendar(1880,
							Calendar.JANUARY, 1).getTimeInMillis()))) {
				logger.info("[StringUtil::getDate] Inserita data antecedente al 1880:["
						+ _data + "]");
				return null;
			}
			if (data != null
					&& data.after(new Date(new GregorianCalendar(2100,
							Calendar.JANUARY, 1).getTimeInMillis()))) {
				logger.info("[StringUtil::getDate] Inserita data successiva al 2100:["
						+ _data + "]");
				return null;
			}
		} catch (ParseException e) {
			logger.info("[StringUtil::getDate] Inserita data in formato non valido:["
					+ _data + "]");
		}

		return data;
	}

	// --------------------------------------------------------------------------
	// isValidDate
	// --------------------------------------------------------------------------
	public static boolean isValidDate(String _data) {
		return (getDate(_data) != null);
	}

	// --------------------------------------------------------------------------
	// utilToSqlDate
	// Converte una data java.util.Date in java.sql.Date
	// --------------------------------------------------------------------------
	public static java.sql.Date utilToSqlDate(Date data) {
		return (data == null) ? null : new java.sql.Date(data.getTime());
	}

	public static java.sql.Timestamp utilToSqlTimeStamp(Date data) {
		return (data == null) ? null : new java.sql.Timestamp(data.getTime());
	}

	public static java.sql.Timestamp utilToSqlTimeStamp(Long currentTime) {
		return new java.sql.Timestamp(currentTime);
	}

	/**
	 * Rappresenta una data in formato stringa
	 * 
	 * @param data
	 *            Data da rappresentare
	 * @return Stringa contenenete la data o null
	 */
	public static String getDate(Date data) {
		String ret = null;

		if (null != data) {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			ret = sdf.format(data);
		}

		return ret;
	}

	public static int getYear(Date data) throws Exception {
		int ret = 0;

		if (null != data) {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.YEAR_FORMAT);
			String year = sdf.format(data);
			ret = Integer.parseInt(year);
		}

		return ret;
	}

	public static String getTime(Date data) {
		return getTimeFormattato(data, Constants.TIME_FORMAT);
	}

	private static String getTimeFormattato(Date data, String format) {
		String ret = null;
		if (null != data) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			ret = sdf.format(data);
		}
		return ret;
	}

	public static String getTime(long data, String format) {
		Date date = new Date(data);
		return getTimeFormattato(date, format);
	}

	public static String getTime(long data) {
		Date date = new Date(data);
		return getTimeFormattato(date, Constants.TIME_FORMAT);
	}

	public static String getTime(Date data, String format) {
		return getTimeFormattato(data, format);
	}

	public static Timestamp getDataMock() {
		Calendar cal = Calendar.getInstance();
		// 26/11/2007 14.09.30
		// 26/11/2007 17.10.42
		cal.set(Calendar.DAY_OF_MONTH, 26);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.YEAR, 2007);
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 10);
		cal.set(Calendar.SECOND, 42);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static Timestamp getDataAsTimeStamp(long dataSistema) {
		return new Timestamp(dataSistema);
	}

	public static String getDataAsString(long dataSistema) {
		return new Timestamp(dataSistema).toString();
	}

	/*
	 * public static boolean isBeforeDataSistema(String data){
	 * 
	 * boolean ret = false; Date d = DateUtil.getDate(data);
	 * 
	 * 
	 * if (d.getTime() > RegistrazioneManager.getDataSistema()){ ret = false;
	 * }else ret = true;
	 * 
	 * 
	 * return ret; }
	 */

	public static Date parseDate(String ggMMyyyy) {
		try {
			return (ggMMyyyy == null || ggMMyyyy.equals("") ? null
					: (new SimpleDateFormat("dd/MM/yyyy")).parse(ggMMyyyy));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Deprecated
	/**
	 * � preferibile utilizzare il metodo getDate(String _data): Date 
	 */
	public static String formatDate(Date data) {
		return (data == null ? null : (new SimpleDateFormat("dd/MM/yyyy",
				Locale.ITALIAN)).format(data));
	}

	/**
	 * Aggiunge o sottrae giorni ad una data
	 * 
	 * @param d
	 * @param g
	 * @return
	 */
	public static Date addGiorni(Date d, int g) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, g);
		return c.getTime();
	}

	/**
	 * 
	 * @return
	 */
	public static Date getDataOdierna() {
		Calendar adesso = new GregorianCalendar(Locale.ITALY);
		return adesso.getTime();
	}

	public static Date getDataOdiernaSenzaOra() {

		Calendar adesso = new GregorianCalendar(Locale.ITALY);
		adesso.setTime(getDataOdierna());
		adesso.set(Calendar.AM_PM, Calendar.AM);
		adesso.set(Calendar.HOUR, 0);
		adesso.set(Calendar.MINUTE, 0);
		adesso.set(Calendar.SECOND, 0);
		return adesso.getTime();

	}

	/*
	 * @return true se la prima � maggiore secondo il pattern dd/MM/yyyy
	 */
	public static boolean after(Date d1, Date d2) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		// formatto togliendo ore minuti e secondi
		Date d3 = sdf.parse(sdf.format(d1));
		Date d4 = sdf.parse(sdf.format(d2));
		if (d3.after(d4))
			return true;
		else
			return false;
	}

	/*
	 * @return true se la prima � minore secondo il pattern dd/MM/yyyy
	 */
	public static boolean before(Date d1, Date d2) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		// formatto togliendo ore minuti e secondi
		Date d3 = sdf.parse(sdf.format(d1));
		Date d4 = sdf.parse(sdf.format(d2));
		if (d3.before(d4))
			return true;
		else
			return false;
	}

	/*
	 * @return true se sono equivalenti secondo il pattern dd/MM/yyyy
	 */
	public static boolean equals(Date d1, Date d2) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		// formatto togliendo ore minuti e secondi
		Date d3 = sdf.parse(sdf.format(d1));
		Date d4 = sdf.parse(sdf.format(d2));
		if (d3.compareTo(d4) == 0)
			return true;
		else
			return false;
	}

	public static java.sql.Date getSysdateWithoutSeconds() {
		return new java.sql.Date(getDataOdiernaSenzaOra().getTime());
	}

	public static java.sql.Date getSysdate() {
		return new java.sql.Date(getDataOdierna().getTime());
	}
	public static void main(String[] args) {
		String time = getTime(new Date(), "ddMMyyyy");
		System.out.println(time);
	}

}
