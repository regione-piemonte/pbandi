/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface AbilitazioneRendicontoDAO {

	Boolean isCodiceFiscalePresentePerDomiciliarita(String codiceFiscale, String numeroDomanda);

	Boolean isCodiceFiscalePresentePerResidenzialita(String codiceFiscale);

	HashMap<String, String> getSoggettoBeneficiario(String numeroDomanda);

	Boolean verificaSoggettoPerDomiciliarita(String idProgetto, String codiceFiscaleUtente);

	ArrayList<String> getListaCodiceFiscaleFornitore(int idSoggettoBeneficiario);

	String verificaAbilitazioneARendicontare(String idProgetto);

}