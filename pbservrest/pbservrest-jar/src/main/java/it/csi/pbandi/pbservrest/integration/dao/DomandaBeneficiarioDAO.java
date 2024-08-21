/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao;

import java.math.BigDecimal;
import java.util.List;

import it.csi.pbandi.pbservrest.dto.DomandaInfo;

public interface DomandaBeneficiarioDAO {
	
	
	List<DomandaInfo> getDomandeBeneficiarioProgetto(String numeroDomanda, BigDecimal numRelazDichSpesa, BigDecimal codiceBando,
			String ndg, String pec, String codiceFiscaleSoggetto, String denominazioneEnteGiuridico,
			String partitaIva, String acronimoProgetto, String tipoSogg);
	List<DomandaInfo> getDomandeBeneficiarioFisicoProgetto(String numeroDomanda, BigDecimal numRelazDichSpesa,
			BigDecimal codiceBando, String ndg, String pec, String codiceFiscaleSoggetto, String nome,
			String cognome, String tipoSogg);
	List<DomandaInfo> getDomandeBeneficiarioDomanda(String numeroDomanda, BigDecimal numRelazDichSpesa,
			BigDecimal codiceBando, String ndg, String pec, String codiceFiscaleSoggetto,
			String denominazioneEnteGiuridico, String partitaIva, String acronimoProgetto, String tipoSogg);
	List<DomandaInfo> getDomandeBeneficiarioFisicoDomanda(String numeroDomanda, BigDecimal numRelazDichSpesa,
			BigDecimal codiceBando, String ndg, String pec, String codiceFiscaleSoggetto, String nome, String cognome, String tipoSogg);
	String getNumeroDom(String numeroDomanda);
	Integer getIdTipoSoggetto(String tipoSogg);
}