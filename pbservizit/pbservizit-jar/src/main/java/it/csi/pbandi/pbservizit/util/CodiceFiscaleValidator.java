/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.util;

public class CodiceFiscaleValidator {
	
	/**
	 * Normalizes a CF by removing white spaces and converting to upper-case.
	 * Useful to clean-up user's input and to save the result in the DB.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Normalized CF.
	 */
	static String normalize(String cf)
	{
		cf = cf.replaceAll("[ \t\r\n]", "");
		cf = cf.toUpperCase();
		return cf;
	}
	
	/**
	 * Returns the formatted CF. Currently does nothing but normalization.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Formatted CF.
	 */
	static String format(String cf)
	{
		return normalize(cf);
	}
	
	/**
	 * Validates a regular CF.
	 * @param cf Normalized, 16 characters CF.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	private static String validate_regular(String cf)
	{
		if( ! cf.matches("^[0-9A-Z]{16}$") )
			return "Invalid characters.";
		int s = 0;
		String even_map = "BAFHJNPRTVCESULDGIMOQKWZYX";
		for(int i = 0; i < 15; i++){
			int c = cf.charAt(i);
			int n;
			if( '0' <= c && c <= '9' )
				n = c - '0';
			else
				n = c - 'A';
			if( (i & 1) == 0 )
				n = even_map.charAt(n) - 'A';
			s += n;
		}
		if( s%26 + 'A' != cf.charAt(15) )
			return "Invalid checksum.";
		return null;
	}
	
	/**
	 * Validates a temporary CF.
	 * @param cf Normalized, 11 characters CF.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	private static String validate_temporary(String cf)
	{
		if( ! cf.matches("^[0-9]{11}$") )
			return "Invalid characters.";
		int s = 0;
		for(int i = 0; i < 11; i++){
			int n = cf.charAt(i) - '0';
			if( (i & 1) == 1 ){
				n *= 2;
				if( n > 9 )
					n -= 9;
			}
			s += n;
		}
		if( s % 10 != 0 )
			return "Invalid checksum.";
		return null;
	}
	
	/**
	 * Verifies the basic syntax, length and control code of the given CF.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	public static boolean isValid(String cf)
	{
		String result = null;
		cf = normalize(cf);
		if( cf.length() == 0 )
			result = "Empty.";
		else if( cf.length() == 16 )
			result = validate_regular(cf);
		else if( cf.length() == 11 )
			result = validate_temporary(cf);
		else
			result = "Invalid length.";
		if (result==null) {
			return true;
		}
		else {
			return false;
		}
	}
	
}