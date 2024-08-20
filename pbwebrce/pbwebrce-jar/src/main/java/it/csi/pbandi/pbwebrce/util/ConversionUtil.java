/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConversionUtil {
	/**
	 * implementa la strategia di conversione "NOME_CAMPO" -> "nomeCampo"
	 * 
	 * @param dbFieldName
	 *            nome della colonna sul DB
	 * @return nome del campo in camelCase
	 */
	public static String getPropertyNameByDBFieldName(String dbFieldName) {
		// tutto in minuscolo
		dbFieldName = dbFieldName.toLowerCase();

		// split sul carattere "_"
		String[] namePart = dbFieldName.split("_");

		String propertyName = "";
		String part = "";
		for (int i = 0; i < namePart.length; i++) {
			if (i == 0) // nella prima parte lascio tutto minuscolo
				part = namePart[i];
			else
				// nelle successive metto la prima maiuscola
				part = namePart[i].substring(0, 1).toUpperCase()
						+ namePart[i].substring(1);
			propertyName = propertyName + part;
		}

		return propertyName;
	}

	/**
	 * implementa la strategia di conversione "nomeCampo" -> "NOME_CAMPO"
	 * 
	 * @param propertyName
	 *            nome del campo in camelCase
	 * @return nome della colonna sul DB
	 */
	public static String getDBFieldNameByPropertyName(String propertyName) {
		StringBuffer sb = new StringBuffer();
		char lastChar = 'a';
		for (int i = 0; i < propertyName.length(); i++) {
			char currentChar = propertyName.charAt(i);
			if (Character.isUpperCase(currentChar)
					&& !Character.isUpperCase(lastChar)) {
				sb.append('_');
			}
			sb.append(Character.toUpperCase(currentChar));
			lastChar = currentChar;
		}
		return sb.toString();
	}

	/**
	 * Convert ResourceBundle into a Properties object.
	 * 
	 * @see http://www.kodejava.org/examples/341.html
	 * @param resource
	 *            a resource bundle to convert.
	 * @return Properties a properties version of the resource bundle.
	 */
	public static Properties convertResourceBundleToProperties(
			ResourceBundle resource) {
		Properties properties = new Properties();

		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}

		return properties;
	}

	/**
	 * Convert File into byte[]
	 * 
	 * @throws IOException
	 */
	public static byte[] toByteArray(File file) throws FileNotFoundException,
			IOException {
		FileInputStream fis;
		byte[] bytes;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		fis = new FileInputStream(file);
		while (fis.available() > 0) {
			bos.write(fis.read());
		}
		bytes = bos.toByteArray();

		return bytes;
	}

	public static String getCodUtenteFlux(String idSoggetto, String codFiscale,
			String codiceRuolo) {
		String codUtente = idSoggetto + "_" + codFiscale + "@" + codiceRuolo;
		return codUtente;
	}
}
