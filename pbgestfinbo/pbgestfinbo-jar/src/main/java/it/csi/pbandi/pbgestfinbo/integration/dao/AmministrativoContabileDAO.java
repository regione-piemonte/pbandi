/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.ammvoservrest.dto.StatoDistinte;
import it.csi.pbandi.pbgestfinbo.dto.amministrativoContabile.MonitoringAmministrativoContabileDTO;

import java.math.BigDecimal;

public interface AmministrativoContabileDAO {

    // 7.1	Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile
    Long trackCallToAmministrativoContabilePre(
            Long idServizio, // 7 = AnagraficaFondo.Anagrafica, 8 = AnagraficaFondo.IbanFondo
            Long idUtente,
            String modalitaChiamata, // I = insert, U = update
            String parametriInput, // Concatenzaione chiave-valore
            String parametriOutput,
            Long idEntita, // Valorizzato a seconda del servizio chiamato, es: 5 se è stato chiamato il servizio AnagraficaFondo.Anagrafica, 128 se è stato chiamato il servizio AnagraficaFondo.IbanFondo
            Long idTarget // Valorizzato con l’identificativo relative all’ID_ENTITA, es: Coincide con l’ID_BANDO se è stato chiamato il servizio AnagraficaFondo.Anagrafica, Coincide con ID_ESTREMI_BANCARI se è stato chiamato il servizio AnagraficaFondo.IbanFondo

    ) throws Exception;

    // 7.1	Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile
    void trackCallToAmministrativoContabilePost(Long idMonitAmmCont, String esito, String codErrore, String msgErr, String parametriOutput) throws Exception;

    MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile(Long idAmmCont) throws Exception;

    Long getIdEntitaGestioneRevoche() throws Exception;

    Long getIdEntitaDistinta() throws Exception;

    Long getIdEntitaConto() throws Exception;

    //Long getIdEntitaRevoche() throws Exception;

    Long getIdFondo(int idProgetto);

    String getIbanBeneficiario(int idProgetto);

    String getIbanInterventoSostitutivo(int idDistintaDett);

    String getIbanFondo(int idBando, int idModalitaAgevolazione);

    String getCodiceProgetto(int idProgetto, int idModalitaAgevolazione);

    Long getNdgBeneficiario(int idProgetto);

    String getCodiceDomanda(int idProgetto);

    Integer getIdTipoDistinta(String descTipoDistinta);

    Double getImportoAmmessoTotale(int idProgetto);
    
    Double getImportoAmmessoBanca(int idProgetto);

}
