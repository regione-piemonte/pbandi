/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRichiestaAbilitazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DatiProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioSoggettoBeneficiarioDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioSoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DimensioneTerritorialeDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.EmailBeneficiarioPF;
import it.csi.pbandi.pbweberog.dto.datiprogetto.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.FileDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.Recapiti;
import it.csi.pbandi.pbweberog.dto.datiprogetto.RecapitoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.integration.request.DatiSifRequest;
import it.csi.pbandi.pbweberog.integration.request.RequestCambiaStatoSoggettoProgetto;
import it.csi.pbandi.pbweberog.pbandisrv.dto.DatiAggiuntiviDTO;

public interface GestioneDatiProgettoDAO {
	DatiProgettoDTO findDatiProgetto(Long idUtente, String idIride, Long idProgetto, Long idSoggettoBeneficiario)
			throws Exception;

	FileDTO[] getFilesAssociatedProgetto(Long idUtente, String idIride, Long idProgetto) throws Exception;

	SedeProgettoDTO[] findAllSediProgetto(Long idUtente, String idIride, Long idProgetto, Long idSoggettoBeneficiario)
			throws Exception;

	SoggettoProgettoDTO[] findSoggettiProgetto(Long idUtente, String idIride, Long idProgetto)
			throws FormalParameterException;

	SedeProgettoDTO findDettaglioSedeProgetto(Long idUtente, String idIride, Long idProgetto,
			Long idSoggettoBeneficiario, Long idSede) throws Exception;

	EsitoOperazioni modificaSedeIntervento(Long idUtente, String idIride, SedeProgettoDTO sedeIntervento,
			Long idSedeAttuale) throws Exception;

	EsitoOperazioni inserisciSedeInterventoProgetto(Long idUtente, String idIride, SedeProgettoDTO sede,
			Long progrSoggettoProgetto, Long idSedeInterventoAttuale)
			throws UnrecoverableException, FormalParameterException;

	DettaglioSoggettoProgettoDTO findDettaglioSoggettoProgetto(Long idUtente, String idIride,
			Long progrSoggettoProgetto, Long idTipoSoggettoCorrelato, Long progrSoggettiCorrelati)
			throws FormalParameterException;

	EsitoOperazioni cancellaSedeIntervento(Long idUtente, String idIride, Long progrSoggettoProgettoSede)
			throws FormalParameterException, UnrecoverableException;

	EsitoOperazioni salvaDatiProgetto(Long idUtente, String idIride, DatiProgettoDTO datiProgetto,
			Boolean isLineaBANDIREGP) throws Exception;
	
	EsitoOperazioni salvaDatiSif(Long idUtente, String idIride, Long idProgetto, DatiSifRequest request) throws Exception;

	Boolean cambiaStatoSoggettoProgetto(Long idUtente, String idIride, Long idSoggetto, Long idTipoSoggettoCorrelato);

	RecapitoDTO findRecapito(Long idUtente, String idIride, Long idProgetto) throws Exception;

	Boolean updateSoggettoProgettoSede(UserInfoSec userInfo, Long progrSoggettoProgettoSede, Long idRecapiti)
			throws Exception;
	
	Boolean updateSoggettoProgettoSedePec(UserInfoSec userInfo, Long progrSoggettoProgettoSede, Long idRecapiti)
			throws Exception;

	Recapiti getMailFromRecapiti(Long idUtente, String idIride, String email) throws Exception;
	
	Recapiti getPecFromRecapiti(Long idUtente, String idIride, String pec) throws Exception;

	Recapiti insertRecapito(Long idUtente, String idIride, Recapiti rec) throws Exception;

	EsitoScaricaDocumentoDTO findDocumento(Long idUtente, String idIride, Long idDocumentoIndex)
			throws FormalParameterException, Exception;

	EsitoOperazioni saveCup(Long idUtente, String idIride, Long idProgetto, String cup) throws Exception;

	EsitoOperazioni salvaDettaglioSoggettoProgetto(Long idUtente, String idIride,
			DettaglioSoggettoProgettoDTO dettaglioSoggettoProgetto) throws Exception;

	EsitoOperazioni saveTitoloProgetto(Long idUtente, String idIride, Long idProgetto, String titoloProgetto)
			throws FormalParameterException, Exception;

	DimensioneTerritorialeDTO[] findDimensioneTerritoriale(Long idUtente, String idIride, Long idProgetto)
			throws FormalParameterException, Exception;

	DettaglioSoggettoBeneficiarioDTO findDettaglioSoggettoBeneficiario(Long idUtente, String idIride,
			Long progrSoggettoProgetto, Long idSoggettoBeneficiario) throws Exception;

	EsitoOperazioni salvaDettaglioSoggettoBeneficiario(Long idUtente, String idIride,
			DettaglioSoggettoBeneficiarioDTO dettaglioSoggettoBeneficiario) throws FormalParameterException, Exception;

	List<PbandiTUtenteVO> findUtenteVOFromCodiceUtente(String codiceFiscale) throws Exception;

	List<PbandiTRichiestaAbilitazVO> findRichiestaAbilitazVO() throws Exception;

	PbandiTRichiestaAbilitazVO findRichiestaAbilitazVO(
			RequestCambiaStatoSoggettoProgetto requestCambiaStatoSoggettoProgetto) throws Exception;

	int updateRichiestaAbilitazione(Long idU, RequestCambiaStatoSoggettoProgetto requestCambiaStatoSoggettoProgetto,
			String esito);

	void insertPbandiTUtenteVO(Long idUtente, RequestCambiaStatoSoggettoProgetto cambiaStatoRequest);

	int updateDataCessazione(Long idSoggetto, Long idProgetto) throws Exception;

	PbandiTProgettoVO getProgettoRegione(Long idProgetto);

	List<PbandiTUtenteVO> findPbandiTUtenteVO(String codiceUtente) throws Exception;

	List<PbandiTRichiestaAbilitazVO> findCheckAccessoSistema(Long progrSoggettoProgetto, Long progrSoggettiCorrelati)
			throws Exception;

	List<PbandiTRichiestaAbilitazVO> findRichiestaAccessoRifiutata() throws Exception;

	List<PbandiRSoggettoProgettoVO> findListRSoggettoProgetto(BigDecimal idSoggetto, Long idProgetto) throws Exception;

	EmailBeneficiarioPF leggiEmailBeneficiarioPF(Long idProgetto, Long idSoggettoBen) throws Exception;

	EsitoOperazioni salvaEmailBeneficiarioPF(Long idProgetto, Long idSoggettoBen, String email, Long idUtente)
			throws Exception;

	EsitoAssociaFilesDTO associaAllegatiAProgetto(AssociaFilesRequest associaFilesRequest, Long idUtente)
			throws InvalidParameterException, Exception;

	EsitoOperazioni disassociaAllegatoProgetto(Long idDocumentoIndex, Long idProgetto, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	DatiAggiuntiviDTO getDatiAggiuntiviByIdProgetto(Long idProgetto, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

}
