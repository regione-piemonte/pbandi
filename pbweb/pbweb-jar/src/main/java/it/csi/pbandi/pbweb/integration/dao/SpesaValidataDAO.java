/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.RettificaVoceItem;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.DichiarazioneSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.EsitoSalvaSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.InizializzaModificaSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.InizializzaSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.MensilitaProgettoDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.RilievoDocSpesaDTO;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ConfermaSalvaSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaSpesaValidataRequest;

public interface SpesaValidataDAO {
	
	InizializzaSpesaValidataDTO inizializzaSpesaValidata(Long idProgetto, Boolean isFP, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	InizializzaModificaSpesaValidataDTO inizializzaModificaSpesaValidata(Long idDocumentoDiSpesa, String numDichiarazione,Long idProgetto, String codiceRuolo, Long idBandoLinea, Long idSoggetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<DocumentoDiSpesa> cercaDocumentiSpesaValidata(CercaDocumentiSpesaValidataRequest cercaDocumentiSpesaValidataRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	List<MensilitaProgettoDTO> recuperaMensilitaProgetto(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaSpesaValidataDTO validaMensilitaProgetto (List<MensilitaProgettoDTO> listaMensilitaProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoLeggiFile reportDettaglioDocumentoDiSpesa (Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<DichiarazioneSpesaValidataDTO> dichiarazioniDocumentoDiSpesa (Long idDocumentoDiSpesa, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	VoceDiSpesaDTO voceDiSpesa (Long idQuotaParte, Long idDocumentoDiSpesa, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaSpesaValidataDTO salvaSpesaValidata (SalvaSpesaValidataRequest salvaSpesaValidataRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaSpesaValidataDTO confermaSalvaSpesaValidata (ConfermaSalvaSpesaValidataRequest confermaSalvaSpesaValidataRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<RettificaVoceItem> dettaglioRettifiche (Long progQuotaParte, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO salvaRilievoDS(Long idDichiarazioneDiSpesa, String note, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	EsitoDTO setNullaDaRilevare(Long idDichiarazioneDiSpesa, Long idProgetto, List<Long> idDocumentiConRilievo, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	EsitoDTO chiudiRilievi(Long idDichiarazioneDiSpesa, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	RilievoDocSpesaDTO getRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, Long idDichiarazioneDiSpesa, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	EsitoDTO salvaRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, String note, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	EsitoDTO deleteRilievoDS(Long idDichiarazioneDiSpesa, Long idUtente, String idIride, Long idSoggetto,	String codiceRuolo) throws InvalidParameterException, Exception;
	EsitoDTO deleteRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	EsitoDTO confermaValiditaRilievi(Long idDichiarazioneDiSpesa, Long idUtente, String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception;
	
}
