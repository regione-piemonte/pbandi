/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.util.oauth;

/**
 * The type Util.
 *
 * @author CSI PIEMONTE
 */
public class Util {
    /**
     * maschera una parte di una stringa con '*' per il log.
     *
     * @param in la stringa da mascherare
     * @return la stringa mascherata
     */
    public static String maskForLog(String in) {
        if (in != null && in.length() > 5) {
            int n = in.length() / 3;
            String pre = in.substring(0, n);
            String end = in.substring(in.length() - n);
            StringBuilder sb = new StringBuilder();
            sb.append(pre);
            for (int i = 0; i < in.length() - 2 * n; i++)
                sb.append("*");
            sb.append(end);
            return sb.toString();
        }
        return "*****";
    }
}