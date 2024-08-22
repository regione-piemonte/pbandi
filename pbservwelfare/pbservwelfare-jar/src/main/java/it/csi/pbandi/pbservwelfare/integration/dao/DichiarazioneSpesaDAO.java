/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.io.InputStream;
import java.util.HashMap;

public interface DichiarazioneSpesaDAO {
	
	HashMap<String, String> caricaFile(InputStream file, String nomeFile, String numeroDomanda, String dati);

}
