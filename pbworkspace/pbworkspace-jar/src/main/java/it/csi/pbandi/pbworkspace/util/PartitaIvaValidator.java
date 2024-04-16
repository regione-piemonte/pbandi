/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.util;

public class PartitaIvaValidator {
	
	/**
	 * Normalizes a PI by removing white spaces.
	 * Useful to clean-up user's input and to save the result in the DB.
	 * @param pi Raw PI, possibly with spaces.
	 * @return Normalized PI.
	 */
	static String normalize(String pi)
	{
		return pi.replaceAll("[ \t\r\n]", "");
	}
	
	/**
	 * Returns the formatted PI. Currently does nothing but normalization.
	 * @param pi Raw PI, possibly with spaces.
	 * @return Formatted PI.
	 */
	static String format(String pi)
	{
		return normalize(pi);
	}
	
	/**
	 * Verifies the basic syntax, length and control code of the given PI.
	 * @param pi Raw PI, possibly with spaces.
	 * @return Null if valid, or string describing why this PI must be
	 * rejected.
	 */
	public static boolean isValid(String pi)
	{
		String result = null;
		pi = normalize(pi);
		if( pi.length() == 0 )
			result = "Empty.";
		else if( pi.length() != 11 )
			result = "Invalid length.";
		if( ! pi.matches("^[0-9]{11}$") )
			result = "Invalid characters.";
		int s = 0;
		for(int i = 0; i < 11; i++){
			int n = pi.charAt(i) - '0';
			if( (i & 1) == 1 ){
				n *= 2;
				if( n > 9 )
					n -= 9;
			}
			s += n;
		}
		if( s % 10 != 0 ) {
			result = "Invalid checksum.";
		}
		if (result==null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
}