/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstantsMap {
	
	
	public static final Map<String, String> MAP_CODICE_DESCRIZIONE = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("id", "codice");
			put("descrizione", "descrizione");
		}
	});

}
