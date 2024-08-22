/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbservwelfare.dto.Fornitori;

public interface GestioneFornitoriDAO {

    Long getIdSoggettoBeneficiario(String numeroDomanda);

    List<Long> getIdFornitore(Long idSoggBenef, String codiceFiscale);

    void disabilitaFornitore(Long idFornitore);

    void salvaContratto(String numeroDomanda, Long idSoggBenef, String codiceFiscale, Long idFornitore, byte[] file, String nomeFile, Date dataInizio, Date dataFine);

    Long salvaFornitore(Long idSoggBenef, String nome, String cognome, String denominazione, String codiceFiscale, String partitaIva, String codiceFormaGiuridica, Date dataInizio, Date dataFine);

    List<Fornitori> getFornitori(String numeroDomanda);
}
