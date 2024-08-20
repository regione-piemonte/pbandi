/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.filestorage.util;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class Utility {
	/**
	 * Questo calcola l'MD5 di un inputStream
	 * 
	 * @param input InputStream del file
	 * @return String MD5 calcolato
	 */	
	public static String getMd5(InputStream input) {
	    if(input == null) {
	        return null;
	    }
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        DigestInputStream dis = new DigestInputStream(input, md);
	        byte[] buffer = new byte[1024 * 8]; 
	        while(dis.read(buffer) != -1) {;}
	        dis.close();
	        byte[] raw = md.digest();

	        BigInteger bigInt = new BigInteger(1, raw);
	        StringBuilder hash = new StringBuilder(bigInt.toString(16));

	        while(hash.length() < 32 ){
	            hash.insert(0, '0');
	        }
	        return hash.toString();
	    } catch (Throwable t) {
	        return null;
	    }
	}
}
