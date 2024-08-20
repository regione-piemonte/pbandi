/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.ammvoservrest.dto.*;

import java.math.BigDecimal;
import java.util.Date;

public interface AmministrativoContabileService {

//    MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile(Long idAmmCont) throws Exception;

//	Long callToAnagraficaFondoIbanFondo (long idBando, String iban, int idModalitaAgev, String tipologiaConto,
//			int moltiplicatore, String note, String fondoInps, long idUtente)  throws Exception;

    StatoDistinte calltoStatoDistinte(int idDistinta, int rigaDistinta, long idUtente) throws Exception;

    ImportiRevoche[] callToImportiRevocheImporti(int idProgetto, int idBando, Date dataErogazione, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idGestioneRevoca, long idUtente) throws Exception;

    Long callToDistintaErogazioneRevoca(int idDistinta, int rigaDistinta, Date dataErogazione, double oneri, double importoRevocaCapitale, int numeroRevoca, int tipoRevoca, int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    Long callToDistintaErogazioneEscussione(int idDistinta, int rigaDistinta, int idProgetto, int idBando, String ibanBeneficiario, Date dtInizioValidita, double importoApprovato, int idTipoEscussione, String causaleBonifico, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    Long callToDistintaErogazioneContributo(int idDistinta, int rigaDistinta, int idDistintaDett, int idProgetto, int idBando, Date dataErogazione, double importo, double importoIres, int idCausaleErogazione, String iban, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    Long callToDistintaErogazioneFinanziamento(int idDistinta, int rigaDistinta, int idProgetto, int idBando, Date dataErogazione, double importo, int numRate, int freqRate, String tipoPeriodo, double percentualeInteressi, double importoIres, int idCausaleErogazione, int preammortamento, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    Long callToDistintaErogazioneGaranzia(int idDistinta, int rigaDistinta, int idProgetto,  int idBando, Date dataErogazione, double importo, int idCausaleErogazione, int numRate, int freqRate, String tipoPeriodo, double percentualeInteressi, int preammortamento, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    Boolean callToDistintaErogazionePerdita(int idDistinta, int rigaDistinta, int idProgetto, Date dataPassaggioPerdita, double importoPerdita, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    // Non più usato
    //Long callToDistintaErogazioneCessione(int idDistinta, int rigaDistinta, int idProgetto, Date dataCessione, double impTotCess, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    // AREA CREDITI - AMM.VO CONTABILE

    	// Lettura //
    
    EstrattoConto callToEstrattoConto(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    PianoAmmortamento callToPianoAmmortamento(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    DebitoResiduo callToDebitoResiduo(int idProgetto, int idBando, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);
    
    RecuperiRevoche callToRecuperiRevoche(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente, Long numeroRevoca, Long idGestioneRevoca) throws Exception;
    
    	// Scrittura //
    
    Boolean callToDistintaErogazioneSaldoStralcio(int idDistinta, int rigaDistinta, int idProgetto, Date dataEsito, double impConfidi, double impDebitore, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    Boolean setSegnalazioneCorteConti(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, Date dataSegnalazione, Long idUtente, Long idSegnalazioneCorteConti);

    Boolean setIscrizioneRuolo(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, Date dataIscrizione, Long idUtente, Long idIscrizioneRuolo);
    
    Boolean setRevocaBancaria(Long idRevocaBancaria, int idDistinta, int rigaDistinta, int idProgetto, Date dataErogazione, BigDecimal importo, BigDecimal importoIres, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente);

    Boolean setStatoCredito(Long idStatoCredito, int idStato, int idProgetto, Date dataCambioStato, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, Long idUtente);
    
    
}
