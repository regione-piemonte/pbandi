/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandiutil.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlUtil {
	
	public static String addUrlParameters(String url, Map<String, String> parameters) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer(url);
		if (url.indexOf("?") == -1)
			sb.append("?");
		if (sb.indexOf("&") != -1)
			sb.append("&");
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				sb.append(key + "="
						+ URLEncoder.encode(parameters.get(key), "UTF-8") + "&");
			}
		}
		String urlEncoded = sb.substring(0, sb.length() - 1);
		return urlEncoded;
	}

}
