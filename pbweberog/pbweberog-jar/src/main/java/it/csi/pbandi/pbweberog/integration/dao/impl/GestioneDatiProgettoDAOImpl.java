/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DatiProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DimensioneTerritorialeLineaInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.LineaDiInterventoPadreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SoggettoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestioneprogetto.RecapitoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneEsteroVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDNazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDProvinciaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDRuoloEnteCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggProgRuoloEnteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggProgSoggCorrelVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggProgettoSedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggTipoAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettiCorrelatiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDatiProgettoMonitVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteGiuridicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIndirizzoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPersonaFisicaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRecapitiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRichiestaAbilitazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.intf.ErrorConstants;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.LongValue;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DatiProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioProgettoDTO;
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
import it.csi.pbandi.pbweberog.exception.ErroreGestitoException;
import it.csi.pbandi.pbweberog.exception.RecordNotFoundException;
import it.csi.pbandi.pbweberog.integration.dao.GestioneDatiProgettoDAO;
import it.csi.pbandi.pbweberog.integration.dao.impl.rowmapper.TipoDocumentoIndexMapper;
import it.csi.pbandi.pbweberog.integration.request.DatiSifRequest;
import it.csi.pbandi.pbweberog.integration.request.RequestCambiaStatoSoggettoProgetto;
import it.csi.pbandi.pbweberog.integration.vo.TipoDocumentoIndexVO;
import it.csi.pbandi.pbweberog.pbandisrv.dto.DatiAggiuntiviDTO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Component
public class GestioneDatiProgettoDAOImpl extends JdbcDaoSupport implements GestioneDatiProgettoDAO {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;

//	@Autowired
//	DocumentoManager documentoManager;

	private DataSource dataSource;

	@Autowired
	private GenericDAO genericDAO;

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	@Autowired
	GestioneDatiDiDominioBusinessImpl datiDiDominioBusinessImpl;

	@Autowired
	DecodificheManager decodificheManager;

	protected FileApiServiceImpl fileApiServiceImpl;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	public GestioneDatiProgettoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
		this.genericDAO = new GenericDAO(dataSource);
		this.dataSource = dataSource;
	}

	@Autowired
	ProgettoManager progettoManager;

	@Autowired
	SedeManager sedeManager;

	@Autowired
	it.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl archivioFileDAOImpl;

	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////// ALEEGATI
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public DatiProgettoDTO findDatiProgetto(Long idUtente, String idIride, Long idProgetto, Long idSoggettoBeneficiario)
			throws Exception {
		String prf = "[GestioneDatiProgetto1420DAOImpl::findDatiProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "identitaDigitale", "idProgetto", "idSoggettoBeneficiario" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, idSoggettoBeneficiario);

			BandoLineaProgettoVO bandoLineaProgettoVO = progettoManager.findBandoLineaForProgetto(idProgetto);
			PbandiDLineaDiInterventoVO bandoLineaVO = progettoManager
					.getLineaDiInterventoAsse(bandoLineaProgettoVO.getProgrBandoLineaIntervento());
			Long idLineaInterventoAsse = beanUtil.transform(bandoLineaVO.getIdLineaDiIntervento(), Long.class);

			DatiProgettoVO dettaglioVO = new DatiProgettoVO();
			dettaglioVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			dettaglioVO.setIdSoggettoBeneficiario(NumberUtil.toBigDecimal(idSoggettoBeneficiario));

			List<DatiProgettoVO> listDatiProgetto = genericDAO.findListWhereDistinct(Condition.filterBy(dettaglioVO),
					DatiProgettoVO.class);

			BigDecimal maxProgrSoggSede = new BigDecimal(-1);
			DatiProgettoVO datiProgettoVO = new DatiProgettoVO();

			if (listDatiProgetto != null)
				for (DatiProgettoVO vo : listDatiProgetto) {
					if (NumberUtil.compare(vo.getProgrSoggettoProgettoSede(), maxProgrSoggSede) > 0) {
						datiProgettoVO = vo;
						maxProgrSoggSede = vo.getProgrSoggettoProgettoSede();
					}
				}

			DatiProgettoDTO result = beanUtil.transform(datiProgettoVO, DatiProgettoDTO.class);

			result.setDettaglio(beanUtil.transform(datiProgettoVO, DettaglioProgettoDTO.class));

			result.setIdLineaInterventoAsse(idLineaInterventoAsse);
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni salvaDatiProgetto(Long idUtente, String idIride, DatiProgettoDTO datiProgetto,
			Boolean isLineaBANDIREGP) throws Exception {
		String prf = "[GestioneDatiProgetto1420DAOImpl::salvaDatiProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "identitaDigitale", "datiProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, datiProgetto);
			EsitoOperazioni esito = new EsitoOperazioni();
			boolean isRichiestaAutomaticaDelCupDaImpostareANo = false;
			if (isRichiestaAutomaticaDelCupDaImpostareANo(datiProgetto)) {
				datiProgetto.getDettaglio().setFlagRichiestaCup(false);
				isRichiestaAutomaticaDelCupDaImpostareANo = true;
			}

			try {
				boolean cupUnivoco = true;
				if (!StringUtil.isEmpty(datiProgetto.getCup()))
					cupUnivoco = progettoManager.isCUPUnivoco(NumberUtil.toBigDecimal(datiProgetto.getIdProgetto()),
							datiProgetto.getCup());

				if (cupUnivoco) {
					aggiornaDatiDomanda(datiProgetto, NumberUtil.toBigDecimal(idUtente));
					aggiornaDatiProgetto(datiProgetto, NumberUtil.toBigDecimal(idUtente));
					if (isLineaBANDIREGP.equals(Boolean.FALSE)) {
						aggiornaSedeInterventoPerModificaDati(datiProgetto, NumberUtil.toBigDecimal(idUtente));
						aggiornaDatiMonit(datiProgetto, NumberUtil.toBigDecimal(idUtente));
					}
					esito.setEsito(Boolean.TRUE);
					esito.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
					if (isRichiestaAutomaticaDelCupDaImpostareANo) {
						esito.setMsg(MessageConstants.MSG_RICHIESTA_AUTOMATICA_DEL_CUP_IMPOSTATA_A_NO);
					} else {
						esito.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
					}
				} else {
					esito.setEsito(Boolean.FALSE);
					esito.setMsg(ErrorConstants.ERRORE_GESTIONE_PROGETTO_CUP_NON_UNIVOCO);
				}
				LOG.info(prf + " END");
				return esito;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new UnrecoverableException(e.getMessage(), e);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private boolean isRichiestaAutomaticaDelCupDaImpostareANo(DatiProgettoDTO datiProgetto) {
		return datiProgetto.getNumeroDomanda() != null && datiProgetto.getDettaglio() != null
				&& datiProgetto.getDettaglio().getFlagRichiestaCup() == true;
	}

	/**
	 * Aggiorna i dati della domanda
	 * 
	 * @param datiProgetto
	 * @param idUtente
	 * @throws Exception
	 */
	private void aggiornaDatiDomanda(DatiProgettoDTO datiProgetto, BigDecimal idUtente) throws Exception {
		String prf = "[GestioneDatiProgetto1420DAOImpl::aggiornaDatiDomanda]";
		LOG.info(prf + " START");
		String nameParameter[] = { "idDomanda" };
		ValidatorInput.verifyNullValue(nameParameter, datiProgetto.getIdDomanda());

		PbandiTDomandaVO domandaVO = new PbandiTDomandaVO();
		domandaVO.setIdTipoAiuto(NumberUtil.toBigDecimal(datiProgetto.getDettaglio().getIdTipoAiuto()));
		domandaVO.setIdDomanda(NumberUtil.toBigDecimal(datiProgetto.getIdDomanda()));
		domandaVO.setIdUtenteAgg(idUtente);
		genericDAO.update(domandaVO);
		LOG.info(prf + " END");
	}

	/**
	 * Aggiorna i dati del progetto
	 * 
	 * @param datiProgetto
	 * @param idUtente
	 * @throws Exception
	 */
	private void aggiornaDatiProgetto(DatiProgettoDTO datiProgetto, BigDecimal idUtente) throws Exception {
		String prf = "[GestioneDatiProgetto1420DAOImpl::aggiornaDatiProgetto]";
		LOG.info(prf + " START");
		/*
		 * Ricerco il progetto, poiche' devo sovrascrivere solo le proprieta' che sono
		 * presenti nel dto. Inoltre i campi con null devono essere considerati validi,
		 * cioe' le rispettive colonne della tabella devono essere aggiornate con null.
		 */
		PbandiTProgettoVO filtroVO = new PbandiTProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(datiProgetto.getIdProgetto()));
		PbandiTProgettoVO progettoVO = null;
		try {
			progettoVO = genericDAO.findSingleWhere(filtroVO);
		} catch (RecordNotFoundException e) {
			LOG.error("Trovati zero o molti record su PBANDI_T_PROGETTO", e);
			throw new UnrecoverableException("Trovati zero o molti record su PBANDI_T_PROGETTO", e);
		}
		
		//salvo in variabili temporanee le due date del tab dati Sif per evitare che vengano sovrascritte con null
		java.sql.Date dtFirmaAccordo = progettoVO.getDtFirmaAccordo();
		java.sql.Date dtCompletamentoValutazione = progettoVO.getDtCompletamentoValutazione();

		beanUtil.valueCopyNulls(datiProgetto.getDettaglio(), progettoVO);
		progettoVO.setCodiceVisualizzato(datiProgetto.getCodiceVisualizzato());
		progettoVO.setCup(datiProgetto.getCup());
		progettoVO.setTitoloProgetto(datiProgetto.getTitoloProgetto());
		progettoVO.setIdProgetto(NumberUtil.toBigDecimal(datiProgetto.getIdProgetto()));
		
		progettoVO.setDtFirmaAccordo(dtFirmaAccordo);
		progettoVO.setDtCompletamentoValutazione(dtCompletamentoValutazione);

		progettoVO.setIdUtenteAgg(idUtente);
		genericDAO.updateNullables(progettoVO);
		LOG.info(prf + " END");
	}

	private void aggiornaSedeInterventoPerModificaDati(DatiProgettoDTO datiProgetto, BigDecimal idUtente)
			throws Exception {
		String prf = "[GestioneDatiProgetto1420DAOImpl::aggiornaSedeInterventoPerModificaDati]";
		LOG.info(prf + " START");
		BigDecimal idSedeIntervento = NumberUtil.toBigDecimal(datiProgetto.getIdSedeIntervento());
		BigDecimal idAttivitaAteco = NumberUtil.toBigDecimal(datiProgetto.getDettaglio().getIdAttivitaAteco());
		BigDecimal idDimensioneTerritoriale = NumberUtil
				.toBigDecimal(datiProgetto.getDettaglio().getIdDimensioneTerritor());
		BigDecimal progrSoggettoProgetto = NumberUtil.toBigDecimal(datiProgetto.getProgrSoggettoProgetto());

		String nameParameter[] = { "idSedeIntervento", "progrSoggettoProgetto", };
		ValidatorInput.verifyNullValue(nameParameter, idSedeIntervento, progrSoggettoProgetto);

		PbandiTSedeVO sedeVO = new PbandiTSedeVO();
		sedeVO.setIdSede(idSedeIntervento);
		sedeVO = genericDAO.findSingleWhere(sedeVO);

		DecodificaDTO tipoSede = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_SEDE,
				Constants.TIPO_SEDE_INTERVENTO);

		/*
		 * Verifico se esiste sulla una sede di intervento con gli stessi dati
		 * (denominazione, partita iva, attivita ateco e dimensione territoriale) della
		 * sede di intervento attuale. Se esiste allora eseguo solamente l' update su
		 * PBandiRSoggProgettoSede di tutti i record delle sedi di intervento del
		 * progetto con la sede di intervento trovata. Altrimenti inserisco la nuova
		 * sede di intervento su PBandiTSede (con denominazione e partita iva della sede
		 * precedente, e dimensione territoriale e attivita ateco indicata dall' utente)
		 * e poi eseguo l'update su PBandiRSoggProgettoSede di tutti i record delle sedi
		 * di intervento del progetto con la sede di intervento inserita.
		 */

		SedeProgettoVO filtroSedeVO = new SedeProgettoVO();
		filtroSedeVO.setDenominazione(sedeVO.getDenominazione());
		filtroSedeVO.setPartitaIva(sedeVO.getPartitaIva());
		filtroSedeVO.setIdAttivitaAteco(idAttivitaAteco);
		filtroSedeVO.setIdDimensioneTerritor(idDimensioneTerritoriale);
		filtroSedeVO.setIdTipoSede(NumberUtil.toBigDecimal(tipoSede.getId()));
		filtroSedeVO.setDescendentOrder("dtInizioValidita");

		List<SedeProgettoVO> sedi = genericDAO.findListWhere(filtroSedeVO);
		BigDecimal idSedeAggiornamento = null;

		if (sedi.isEmpty()) {
			/*
			 * Eseguo l'inserimento su PBandiTSede
			 */
			LOG.info("Eseguo l' inserimento della nuova sede di intervento.");
			PbandiTSedeVO newSedeVO = new PbandiTSedeVO();
			newSedeVO.setPartitaIva(sedeVO.getPartitaIva());
			newSedeVO.setDenominazione(sedeVO.getDenominazione());
			newSedeVO.setIdAttivitaAteco(idAttivitaAteco);
			newSedeVO.setIdDimensioneTerritor(idDimensioneTerritoriale);
			newSedeVO.setDtInizioValidita(DateUtil.getSysdate());
			newSedeVO.setIdUtenteIns(idUtente);

			newSedeVO = genericDAO.insert(newSedeVO);
			idSedeAggiornamento = newSedeVO.getIdSede();
		} else {
			/*
			 * Considero valida la prima sede restituita dalla query.
			 */
			LOG.info("Trovata sede di intervento con match dei dati.");
			idSedeAggiornamento = sedi.get(0).getIdSede();
		}

		/*
		 * Aggiorno i record della PbandiRSoggProgettoSedeVO ( solo le sedi di
		 * intervento del progetto del beneficiario )
		 */
		PbandiRSoggProgettoSedeVO soggProgSedeVO = new PbandiRSoggProgettoSedeVO();
		soggProgSedeVO.setIdSede(idSedeAggiornamento);
		PbandiRSoggProgettoSedeVO filtroSoggProgSedeVO = new PbandiRSoggProgettoSedeVO();
		filtroSoggProgSedeVO.setProgrSoggettoProgetto(progrSoggettoProgetto);
		filtroSoggProgSedeVO.setIdTipoSede(NumberUtil.toBigDecimal(tipoSede.getId()));
		soggProgSedeVO.setIdUtenteAgg(idUtente);
		genericDAO.update(Condition.filterBy(filtroSoggProgSedeVO), soggProgSedeVO);
		LOG.info(prf + " END");

	}

	/**
	 * Inserisce o aggiorna i dati in PBandiTDatiProgettoMonit
	 * 
	 * @param datiProgetto
	 * @throws Exception
	 */
	private void aggiornaDatiMonit(DatiProgettoDTO datiProgetto, BigDecimal idUtente) throws Exception {
		String prf = "[GestioneDatiProgetto1420DAOImpl::aggiornaDatiMonit]";
		LOG.info(prf + " START");
		String statoProgramma = datiProgetto.getDettaglio().getStatoProgettoProgramma() != null && datiProgetto.getDettaglio().getStatoProgettoProgramma().equals(Boolean.TRUE)
				? Constants.GESTIONE_PROGETTO_STATO_PROGRAMMA_ATTIVO
				: Constants.GESTIONE_PROGETTO_STATO_PROGRAMMA_NON_ATTIVO;

		PbandiTDatiProgettoMonitVO filtroVO = new PbandiTDatiProgettoMonitVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(datiProgetto.getIdProgetto()));

		PbandiTDatiProgettoMonitVO datiMonitVO = beanUtil.transform(datiProgetto.getDettaglio(),
				PbandiTDatiProgettoMonitVO.class);
		datiMonitVO.setIdProgetto(NumberUtil.toBigDecimal(datiProgetto.getIdProgetto()));

		if (checkCodIgrue(datiProgetto)) {
			datiMonitVO.setStatoFs(Constants.GESTIONE_PROGETTO_STATO_PROGRAMMA_NON_ATTIVO); // non attivo
			datiMonitVO.setStatoFas(statoProgramma);
		} else {
			datiMonitVO.setStatoFs(statoProgramma);
			datiMonitVO.setStatoFas(Constants.GESTIONE_PROGETTO_STATO_PROGRAMMA_NON_ATTIVO); // non attivo
		}

		if (genericDAO.findListWhere(filtroVO).isEmpty()) {
			/*
			 * eseguo la insert
			 */
			datiMonitVO.setIdUtenteIns(idUtente);
			genericDAO.insert(datiMonitVO);
		} else {
			/*
			 * eseguo l'update
			 */
			datiMonitVO.setIdUtenteAgg(idUtente);
			genericDAO.update(datiMonitVO);
		}
		LOG.info(prf + " END");
	}

	/**
	 * Verifica se il codice igrue della linea di intervento normativa e' uguale a
	 * 2007PI002FA011
	 * 
	 * @param datiProgetto
	 * @return
	 */
	private boolean checkCodIgrue(DatiProgettoDTO datiProgetto) {
		String prf = "[GestioneDatiProgetto1420DAOImpl::checkCodIgrue]";
		LOG.info(prf + " START");
		boolean result = false;
		BandoLineaProgettoVO bandoLineaProgettoVO = progettoManager
				.findBandoLineaForProgetto(datiProgetto.getIdProgetto());
		if (Constants.COD_IGRUE_2007PI002FA011.equals(
				progettoManager.getLineaDiInterventoNormativa(bandoLineaProgettoVO.getProgrBandoLineaIntervento())
						.getCodIgrueT13T14())) {
			result = true;

		}
		LOG.info(prf + " END");
		return result;
	}

////////////////////////////////////////////////////////////// DATI SIF
////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	@Transactional
	public EsitoOperazioni salvaDatiSif(Long idUtente, String idIride, Long idProgetto, DatiSifRequest request) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::salvaDatiSif]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			
			
			PbandiTProgettoVO vo = new PbandiTProgettoVO();
			vo.setIdProgetto(new BigDecimal(idProgetto.longValue()));
			vo = genericDAO.findSingleOrNoneWhere(vo);
			
			if(vo != null && request.getDtFirmaAccordo() != null && request.getDtCompletamentoValutazione() != null) {
				vo.setDtFirmaAccordo(new java.sql.Date(request.getDtFirmaAccordo().getTime()));
				vo.setDtCompletamentoValutazione(new java.sql.Date(request.getDtCompletamentoValutazione().getTime()));
				
				genericDAO.update(vo);
				esito.setEsito(Boolean.TRUE);
				esito.setMsg("Salvataggio avvenuto con successo.");			
			} else {
				esito.setEsito(Boolean.FALSE);
				esito.setMsg("Errore: progetto non trovato.");
			}			
		} catch (Exception e) {
			LOG.error(prf + "Errore in fase di salvataggio dei dati sif: " + e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return esito;
	}
	
	////////////////////////////////////////////////////////////// ALLEGATI
	////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional
	public FileDTO[] getFilesAssociatedProgetto(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::getFilesAssociatedProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

			List<FileDTO> filez = new ArrayList<FileDTO>();

			PbandiTFileEntitaVO filterEntita = new PbandiTFileEntitaVO();
			filterEntita.setIdTarget(BigDecimal.valueOf(idProgetto));
			// filterEntita.setIdEntita(BigDecimal.valueOf(documentoManager.getIdEntita(PbandiTProgettoVO.class).longValue()));
			filterEntita.setIdProgetto(BigDecimal.valueOf(idProgetto));
			List<PbandiTFileEntitaVO> rfileEntitas = genericDAO.findListWhere(filterEntita);
			for (PbandiTFileEntitaVO rfileEntita : rfileEntitas) {
				ArchivioFileVO archivioFileVO = new ArchivioFileVO();
				archivioFileVO.setIdFile(rfileEntita.getIdFile());
				archivioFileVO = genericDAO.findSingleWhere(archivioFileVO);
				filez.add(createFileDTO(archivioFileVO));
			}

			return filez.toArray(new FileDTO[0]);
		} catch (Exception e) {
			throw e;
		}
	}

	private FileDTO createFileDTO(ArchivioFileVO file) {
		FileDTO fileDTO = new FileDTO();
		fileDTO.setDtAggiornamento(file.getDtAggiornamentoFile());
		fileDTO.setDtInserimento(file.getDtInserimentoFile());
		if (file.getIdDocumentoIndex() != null)
			fileDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		if (file.getIdFolder() != null)
			fileDTO.setIdFolder(file.getIdFolder().longValue());
		fileDTO.setNomeFile(file.getNomeFile());
		fileDTO.setSizeFile(file.getSizeFile() != null ? file.getSizeFile().longValue() : 0l);
		if (file.getIdProgetto() != null)
			fileDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		if (file.getEntitiesAssociated() != null && file.getEntitiesAssociated().longValue() > 0) {
			fileDTO.setEntityAssociated(file.getEntitiesAssociated().longValue());
		}
		if (file.getIslocked() != null && file.getIslocked().equalsIgnoreCase("S"))
			fileDTO.setIsLocked(true);
		else
			fileDTO.setIsLocked(false);
		fileDTO.setNumProtocollo(file.getNumProtocollo());

		if (file.getIdEntita() != null) {
			fileDTO.setIdEntita(file.getIdEntita().longValue());
		}
		if (file.getIdTarget() != null) {
			fileDTO.setIdTarget(file.getIdTarget().longValue());
		}
		fileDTO.setFlagEntita(file.getFlagEntita()); // Jira PBANDI-2815

		fileDTO.setDescStatoTipoDocIndex(file.getDescStatoTipoDocIndex());

		return fileDTO;
	}

	@Override
	@Transactional
	public EsitoScaricaDocumentoDTO findDocumento(Long idUtente, String idIride, Long idDocumentoIndex)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::findDocumento]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idDocumentoIndex);
		LOG.info(prf + " \n\n\nscaricaDocumento for idDocumentoIndex:" + idDocumentoIndex);
		EsitoScaricaDocumentoDTO esitoScaricaDocumentoDTO = new EsitoScaricaDocumentoDTO();
		esitoScaricaDocumentoDTO.setEsito(Boolean.FALSE);

		try {

			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO(
					BigDecimal.valueOf(idDocumentoIndex));
			documentoIndexVO = genericDAO.findSingleWhere(documentoIndexVO);
//			DocumentoDTO doc =
//				getDocumentoManager()
//				.getDocumento(documentoIndexVO.getUuidNodo());
			String tipoDocumento = descBreveDaIdTipoIndex(documentoIndexVO.getIdTipoDocumentoIndex());

			LOG.info(prf + " nomeDocumento: " + documentoIndexVO.getNomeDocumento() + " tipoDocumento: "
					+ tipoDocumento);

			java.io.File file = fileApiServiceImpl.downloadFile(documentoIndexVO.getNomeDocumento(), tipoDocumento);

			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			esitoScaricaDocumentoDTO.setBytesDocumento(fileBytes);

			LOG.info(prf + " sdocumentoIndexVO.getMessageDigest(): " + documentoIndexVO.getMessageDigest());
			if (documentoIndexVO.getMessageDigest() == null) {
				BigDecimal idTipoDocumentoIndex = documentoIndexVO.getIdTipoDocumentoIndex();
				PbandiCTipoDocumentoIndexVO pbandiCTipoDocumentoIndexVO = new PbandiCTipoDocumentoIndexVO(
						idTipoDocumentoIndex);
				pbandiCTipoDocumentoIndexVO = genericDAO.findSingleWhere(pbandiCTipoDocumentoIndexVO);
				LOG.info("pbandiCTipoDocumentoIndexVO.getFlagFirmabile(): "
						+ pbandiCTipoDocumentoIndexVO.getFlagFirmabile());
				if (pbandiCTipoDocumentoIndexVO.getFlagFirmabile() != null
						&& pbandiCTipoDocumentoIndexVO.getFlagFirmabile().equalsIgnoreCase("S")) {
					String shaHex = DigestUtils.shaHex(fileBytes);
					documentoIndexVO.setMessageDigest(shaHex);
					LOG.info(prf + " ****** UPDATING DOC INDEX with MESSGE DIGEST ****** " + shaHex);
					genericDAO.update(documentoIndexVO);
				}
			}

			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null

			esitoScaricaDocumentoDTO.setNomeFile(documentoIndexVO.getNomeFile());
			// esitoScaricaDocumentoDTO.setParams(new String[] { doc.getMimeType() });
			esitoScaricaDocumentoDTO.setEsito(Boolean.TRUE);
		} catch (Exception e) {
			LOG.error(prf + " Impossibile recuperare il documento: " + e.getMessage());
			throw e;
		}

		return esitoScaricaDocumentoDTO;
	}

	private String descBreveDaIdTipoIndex(BigDecimal idTipoDocumentoIndex) {
		String prf = "[GestioneDatiProgettoDAOImpl::descBreveDaIdTipoIndex]";
		LOG.info(prf + " START");
		StringBuilder queryTipoIndex = new StringBuilder();
		queryTipoIndex.append("SELECT * from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX = ")
				.append(idTipoDocumentoIndex);
		TipoDocumentoIndexVO tipoDocumentoVO = getJdbcTemplate().queryForObject(queryTipoIndex.toString(),
				new TipoDocumentoIndexMapper());
		LOG.info(prf + " END");
		return beanUtil.transform(tipoDocumentoVO.getDescBreveTipoDocIndex(), String.class);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////// SEDI
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public SedeProgettoDTO[] findAllSediProgetto(Long idUtente, String idIride, Long idProgetto,
			Long idSoggettoBeneficiario) throws Exception {
		String prf = "[GestioneDatiProgetto1420DAOImpl::findAllSediProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "idProgetto", "idSoggettoBeneficiario" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, idSoggettoBeneficiario);

			List<SedeProgettoVO> sediVO = sedeManager.findSediIntervento(NumberUtil.toBigDecimal(idProgetto),
					NumberUtil.toBigDecimal(idSoggettoBeneficiario));

			return beanUtil.transform(sediVO, SedeProgettoDTO.class);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public SedeProgettoDTO findDettaglioSedeProgetto(Long idUtente, String idIride, Long idProgetto,
			Long idSoggettoBeneficiario, Long idSede) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::findDettaglioSedeProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "idProgetto", "idSoggettoBeneficiario", "idSede" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, idSoggettoBeneficiario,
					idSede);

			DecodificaDTO tipoSede = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_SEDE,
					Constants.TIPO_SEDE_INTERVENTO);
			LOG.info(prf + " idTipoSede: " + tipoSede.getId());

			LOG.info(prf + " descSede: " + tipoSede.getDescrizione());
			SedeProgettoVO filtroSedeVO = new SedeProgettoVO();
			filtroSedeVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			filtroSedeVO.setIdSoggettoBeneficiario(NumberUtil.toBigDecimal(idSoggettoBeneficiario));
			filtroSedeVO.setIdTipoSede(NumberUtil.toBigDecimal(tipoSede.getId()));
			filtroSedeVO.setIdSede(NumberUtil.toBigDecimal(idSede));
			SedeProgettoVO sedeVO = genericDAO.findSingleWhere(filtroSedeVO);
			return beanUtil.transform(sedeVO, SedeProgettoDTO.class);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional
	public EsitoOperazioni modificaSedeIntervento(Long idUtente, String idIride, SedeProgettoDTO sedeIntervento,
			Long idSedeAttuale) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::modificaSedeIntervento]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "sedeIntervento", "idSedeInterventoAttuale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, sedeIntervento, idSedeAttuale);
			ValidatorInput.verifyAtLeastOneNotNullValue(sedeIntervento);
			EsitoOperazioni result = new EsitoOperazioni();
			result.setEsito(Boolean.FALSE);

			PbandiRSoggProgettoSedeVO progettoSedeVO = new PbandiRSoggProgettoSedeVO();
			progettoSedeVO.setProgrSoggettoProgettoSede(
					NumberUtil.toBigDecimal(sedeIntervento.getProgrSoggettoProgettoSede()));
			progettoSedeVO.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
			progettoSedeVO.setFlagSedeAmministrativa(sedeIntervento.getFlagSedeAmministrativa());
			/*
			 * Verifico se la sede esiste. Se esiste aggiorno solamente l' idSede su
			 * PBandiRSoggettoProgettoSede, altrimenti inserisco prima la sede ( con
			 * attivita ateco e dimensione territoriale della sede di intervento iniziale) e
			 * poi aggiorno l' idSede su PBandiRSoggettoProgettoSede.
			 */
			PbandiTSedeVO sedeVO = caricaSede(sedeIntervento, idSedeAttuale, idUtente);
			progettoSedeVO.setIdSede(sedeVO.getIdSede());
			/*
			 * Verifico se esiste l'indirizzo. Se esiste aggiorno l' idIndirizzo su
			 * PBandiRSoggettoProgettoSede, altrimenti inserisco prima l'indirizzo su su
			 * PBANDI_T_INDIRIZZO e poi aggiorno l' idIndirizzo su
			 * PBandiRSoggettoProgettoSede
			 */
			PbandiTIndirizzoVO indirizzoVO = caricaIndirizzo(sedeIntervento, idUtente);
			progettoSedeVO.setIdIndirizzo(indirizzoVO.getIdIndirizzo());
			/*
			 * Verifico se esiste il recapito. Se esiste aggiorno l' idRecapito su
			 * PBandiRSoggettoProgettoSede, altrimenti inserisco prima i dati del recapito
			 * su su PBANDI_T_RECAPITO e poi aggiorno l' idRecapito su
			 * PBandiRSoggettoProgettoSede
			 */
			PbandiTRecapitiVO recapitoVO = caricaRecapito(sedeIntervento, idUtente);
			progettoSedeVO.setIdRecapiti(recapitoVO.getIdRecapiti());
			try {
				genericDAO.update(progettoSedeVO);
				result.setParams(new String[] { progettoSedeVO.getProgrSoggettoProgettoSede().toString() });
				result.setEsito(Boolean.TRUE);
				result.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				LOG.error("Errore di update su PBANDI_R_SOGG_PROGETTO_SEDE.", e);
				throw new UnrecoverableException("Errore di update su PBANDI_R_SOGG_PROGETTO_SEDE", e);
			}

			LOG.info(prf + " END");
			return result;

		} catch (Exception e) {
			throw e;
		}
	}

	private PbandiTRecapitiVO caricaRecapito(SedeProgettoDTO sedeIntervento, Long idUtente)
			throws UnrecoverableException {
		String prf = "[GestioneDatiProgetto1420DAOImpl::caricaRecapito]";
		LOG.info(prf + " START");
		PbandiTRecapitiVO recapitoVO = findRecapito(sedeIntervento);
		if (recapitoVO == null) {
			recapitoVO = new PbandiTRecapitiVO();
			recapitoVO.setEmail(sedeIntervento.getEmail());
			recapitoVO.setFax(sedeIntervento.getFax());
			recapitoVO.setTelefono(sedeIntervento.getTelefono());
			recapitoVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
			try {
				recapitoVO = genericDAO.insert(recapitoVO);
			} catch (Exception e) {
				LOG.error("Errore di inserimento su PBANDI_T_RECAPITO.", e);
				throw new UnrecoverableException("Errore di inserimento su PBANDI_T_RECAPITO", e);
			}
		}
		LOG.info(prf + " END");
		return recapitoVO;
	}

	private PbandiTRecapitiVO findRecapito(SedeProgettoDTO sede) {
		String prf = "[GestioneDatiProgetto1420DAOImpl::findRecapito]";
		LOG.info(prf + " START");
		PbandiTRecapitiVO result = null;
		List<String> campiNulli = new ArrayList<String>();
		PbandiTRecapitiVO filtroVO = new PbandiTRecapitiVO();
		if (!StringUtil.isEmpty(sede.getEmail()))
			filtroVO.setEmail(sede.getEmail());
		else
			campiNulli.add("email");
		if (!StringUtil.isEmpty(sede.getFax()))
			filtroVO.setFax(sede.getFax());
		else
			campiNulli.add("fax");

		if (!StringUtil.isEmpty(sede.getTelefono()))
			filtroVO.setTelefono(sede.getTelefono());
		else
			campiNulli.add("telefono");

		filtroVO.setDescendentOrder("dtInizioValidita");

		NullCondition<PbandiTRecapitiVO> nullCondition = new NullCondition<PbandiTRecapitiVO>(PbandiTRecapitiVO.class,
				"dtFineValidita", beanUtil.transform(campiNulli, String.class));

		AndCondition<PbandiTRecapitiVO> andCondition = new AndCondition<PbandiTRecapitiVO>(Condition.filterBy(filtroVO),
				nullCondition);

		List<PbandiTRecapitiVO> recapiti = genericDAO.findListWhere(andCondition);
		if (!recapiti.isEmpty()) {
			result = recapiti.get(0);
		}
		LOG.info(prf + " END");
		return result;
	}

	private PbandiTIndirizzoVO caricaIndirizzo(SedeProgettoDTO sedeIntervento, Long idUtente)
			throws UnrecoverableException {
		String prf = "[GestioneDatiProgetto1420DAOImpl::caricaIndirizzo]";
		LOG.info(prf + " START");
		PbandiTIndirizzoVO indirizzoVO = findIndirizzo(sedeIntervento);
		/*
		 * Se la nazione non e' ITALIA allora l' idComune del dto si riferisce ad un
		 * comune estero (idComuneEstero)
		 */
		if (indirizzoVO == null) {
			/*
			 * Inserisco l'indirizzo
			 */
			indirizzoVO = new PbandiTIndirizzoVO();
			if (isItalia(sedeIntervento.getIdNazione()))
				indirizzoVO.setIdComune(NumberUtil.toBigDecimal(sedeIntervento.getIdComune()));
			else
				indirizzoVO.setIdComuneEstero(NumberUtil.toBigDecimal(sedeIntervento.getIdComune()));
			indirizzoVO.setDescIndirizzo(sedeIntervento.getDescIndirizzo());
			indirizzoVO.setCap(sedeIntervento.getCap());

			if (sedeIntervento.getCivico() != null)
				indirizzoVO.setCivico(sedeIntervento.getCivico().toString());

			indirizzoVO.setIdProvincia(NumberUtil.toBigDecimal(sedeIntervento.getIdProvincia()));
			indirizzoVO.setIdRegione(NumberUtil.toBigDecimal(sedeIntervento.getIdRegione()));
			indirizzoVO.setIdNazione(NumberUtil.toBigDecimal(sedeIntervento.getIdNazione()));
			indirizzoVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
			try {
				indirizzoVO = genericDAO.insert(indirizzoVO);
			} catch (Exception e) {
				LOG.error("Errore di inserimento su PBANDI_T_INDIRIZZO.", e);
				throw new UnrecoverableException("Errore di inserimento su PBANDI_T_INDIRIZZO", e);
			}
		}
		return indirizzoVO;
	}

	private boolean isItalia(Long idNazione) {
		boolean result = false;
		PbandiDNazioneVO vo = new PbandiDNazioneVO();
		vo.setIdNazione(NumberUtil.toBigDecimal(idNazione));
		vo = genericDAO.findSingleWhere(vo);
		if (vo.getDescNazione().equals(Constants.DESC_NAZIONE_ITALIA))
			result = true;
		return result;
	}

	private PbandiTIndirizzoVO findIndirizzo(SedeProgettoDTO sede) {
		String prf = "[GestioneDatiProgetto1420DAOImpl::findIndirizzo]";
		LOG.info(prf + " START");
		PbandiTIndirizzoVO result = null;
		List<String> campiNulli = new ArrayList<String>();

		PbandiTIndirizzoVO filtroVO = new PbandiTIndirizzoVO();

		if (!StringUtil.isEmpty(sede.getDescIndirizzo()))
			filtroVO.setDescIndirizzo(sede.getDescIndirizzo());
		else
			campiNulli.add("descIndirizzo");

		if (!StringUtil.isEmpty(sede.getCap()))
			filtroVO.setCap(sede.getCap());
		else
			campiNulli.add("cap");

		if (sede.getIdProvincia() == null)
			campiNulli.add("idProvincia");
		else
			filtroVO.setIdProvincia(NumberUtil.toBigDecimal(sede.getIdProvincia()));

		if (sede.getIdRegione() == null)
			campiNulli.add("idRegione");
		else
			filtroVO.setIdRegione(NumberUtil.toBigDecimal(sede.getIdRegione()));

		if (sede.getIdNazione() == null)
			campiNulli.add("idNazione");
		else {
			filtroVO.setIdNazione(NumberUtil.toBigDecimal(sede.getIdNazione()));
			/*
			 * Verifico se la nazione e' ITALIA. Se e' ITALIA allora verifico se l' utente
			 * ha indicato un comune, altrimenti verifico se l' utente ha indicato un comune
			 * estero
			 */
			if (isItalia(sede.getIdNazione())) {
				if (sede.getIdComune() == null)
					campiNulli.add("idComune");
				else
					filtroVO.setIdComune(NumberUtil.toBigDecimal(sede.getIdComune()));
			} else {
				if (sede.getIdComune() == null)
					campiNulli.add("idComuneEstero");
				else
					filtroVO.setIdComuneEstero(NumberUtil.toBigDecimal(sede.getIdComune()));
			}
		}

		filtroVO.setDescendentOrder("dtInizioValidita");

		NullCondition<PbandiTIndirizzoVO> nullCondition = new NullCondition<PbandiTIndirizzoVO>(
				PbandiTIndirizzoVO.class, "dtFineValidita", beanUtil.transform(campiNulli, String.class));

		AndCondition<PbandiTIndirizzoVO> andCondition = new AndCondition<PbandiTIndirizzoVO>(
				Condition.filterBy(filtroVO), nullCondition);
		List<PbandiTIndirizzoVO> indirizzi = genericDAO.findListWhere(andCondition);
		if (!indirizzi.isEmpty()) {
			result = indirizzi.get(0);
		}
		LOG.info(prf + " END");
		return result;
	}

	private PbandiTSedeVO caricaSede(SedeProgettoDTO sedeIntervento, Long idSedeAttuale, Long idUtente)
			throws UnrecoverableException {
		String prf = "[GestioneDatiProgetto1420DAOImpl::caricaSede]";
		LOG.info(prf + " START");
		PbandiTSedeVO sedeAttualeVO = new PbandiTSedeVO();
		sedeAttualeVO.setIdSede(NumberUtil.toBigDecimal(idSedeAttuale));
		sedeAttualeVO = genericDAO.findSingleWhere(sedeAttualeVO);

		PbandiTSedeVO sedeVO = findSede(sedeIntervento.getPartitaIva(), sedeAttualeVO.getIdDimensioneTerritor(),
				sedeAttualeVO.getIdAttivitaAteco());
		if (sedeVO == null) {
			/*
			 * Inserisco la sede
			 */
			sedeVO = new PbandiTSedeVO();
			sedeVO.setPartitaIva(sedeIntervento.getPartitaIva());
			sedeVO.setIdAttivitaAteco(sedeAttualeVO.getIdAttivitaAteco());
			sedeVO.setIdDimensioneTerritor(sedeAttualeVO.getIdDimensioneTerritor());
			sedeVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
			try {
				sedeVO = genericDAO.insert(sedeVO);
			} catch (Exception e) {
				LOG.error("Errore di inserimento su PBANDI_T_SEDE.", e);
				throw new UnrecoverableException("Errore di inserimento su PBANDI_T_SEDE", e);
			}
		}
		LOG.info(prf + " END");
		return sedeVO;
	}

	private PbandiTSedeVO findSede(String partitaIva, BigDecimal idDimensioneTerritoriale, BigDecimal idAttivitaAteco) {
		String prf = "[GestioneDatiProgetto1420DAOImpl::findSede]";
		LOG.info(prf + " START");
		PbandiTSedeVO result = null;
		List<String> campiNulli = new ArrayList<String>();

		PbandiTSedeVO filtroVO = new PbandiTSedeVO();
		if (StringUtil.isEmpty(partitaIva))
			campiNulli.add("partitaIva");
		else
			filtroVO.setPartitaIva(partitaIva);

		filtroVO.setIdAttivitaAteco(idAttivitaAteco);
		filtroVO.setIdDimensioneTerritor(idDimensioneTerritoriale);
		filtroVO.setDescendentOrder("dtInizioValidita");

		NullCondition<PbandiTSedeVO> nullCondition = new NullCondition<PbandiTSedeVO>(PbandiTSedeVO.class,
				"dtFineValidita", beanUtil.transform(campiNulli, String.class));

		AndCondition<PbandiTSedeVO> andCondition = new AndCondition<PbandiTSedeVO>(Condition.filterBy(filtroVO),
				nullCondition);
		List<PbandiTSedeVO> sedi = genericDAO.findListWhere(andCondition);

		if (!sedi.isEmpty()) {
			result = sedi.get(0);
		}
		LOG.info(prf + " END");
		return result;

	}

	@Override
	@Transactional
	public EsitoOperazioni inserisciSedeInterventoProgetto(Long idUtente, String idIride,
			SedeProgettoDTO sedeIntervento, Long progrSoggettoProgetto, Long idSedeInterventoAttuale)
			throws UnrecoverableException, FormalParameterException {
		String prf = "[GestioneDatiProgetto1420DAOImpl::inserisciSedeInterventoProgetto]";
		LOG.info(prf + " BEGIN");

		String nameParameter[] = { "idUtente", "identitaDigitale", "sedeIntervento", "idSedeInterventoAttuale",
				"progrSoggettoProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, sedeIntervento, idSedeInterventoAttuale,
				progrSoggettoProgetto);
		EsitoOperazioni result = new EsitoOperazioni();
		result.setEsito(Boolean.FALSE);
		ValidatorInput.verifyAtLeastOneNotNullValue(sedeIntervento);
		DecodificaDTO tipoSede = decodificheManager.findDecodifica(datiDiDominioBusinessImpl.TIPO_SEDE,
				Constants.TIPO_SEDE_INTERVENTO);
		PbandiRSoggProgettoSedeVO progettoSedeVO = new PbandiRSoggProgettoSedeVO();
		progettoSedeVO.setProgrSoggettoProgetto(NumberUtil.toBigDecimal(progrSoggettoProgetto));
		progettoSedeVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
		progettoSedeVO.setIdTipoSede(NumberUtil.toBigDecimal(tipoSede.getId()));

		/*
		 * Verifico se la sede esiste. Se non esiste inserisco la sede ( con attivita
		 * ateco e dimensione territoriale della sede di intervento iniziale).
		 */
		PbandiTSedeVO sedeVO = caricaSede(sedeIntervento, idSedeInterventoAttuale, idUtente);
		progettoSedeVO.setIdSede(sedeVO.getIdSede());
		/*
		 * Verifico se esiste l'indirizzo. Se non esiste inserisco l' indirizzo.
		 */
		PbandiTIndirizzoVO indirizzoVO = caricaIndirizzo(sedeIntervento, idUtente);
		progettoSedeVO.setIdIndirizzo(indirizzoVO.getIdIndirizzo());
		/*
		 * Verifico se esiste il recapito. Se non esiste inserico il recapito.
		 */
		PbandiTRecapitiVO recapitoVO = caricaRecapito(sedeIntervento, idUtente);
		progettoSedeVO.setIdRecapiti(recapitoVO.getIdRecapiti());

		try {
			/*
			 * Verifico che il record non sia gia' presente
			 */
			List<PbandiRSoggProgettoSedeVO> list = genericDAO.findListWhere(progettoSedeVO);
			if (list == null || list.isEmpty())
				progettoSedeVO = genericDAO.insert(progettoSedeVO);
			result.setParams(
					new String[] { NumberUtil.toLong(progettoSedeVO.getProgrSoggettoProgettoSede()).toString() });
			result.setEsito(Boolean.TRUE);
			result.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			LOG.error("Errore di inserimento su PBANDI_R_SOGG_PROGETTO_SEDE.", e);
			throw new UnrecoverableException("Errore di inserimento su PBANDI_R_SOGG_PROGETTO_SEDE", e);
		}
		LOG.info(prf + " END");
		return result;

	}

	@Override
	@Transactional
	public EsitoOperazioni cancellaSedeIntervento(Long idUtente, String idIride, Long progrSoggettoProgettoSede)
			throws FormalParameterException, UnrecoverableException {
		String prf = "[GestioneDatiProgetto1420DAOImpl::cancellaSedeIntervento]";
		LOG.info(prf + " BEGIN");

		String nameParameter[] = { "idUtente", "identitaDigitale", "progrSoggettoProgettoSede" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, progrSoggettoProgettoSede);

		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(Boolean.FALSE);

		PbandiRSoggProgettoSedeVO filtroVO = new PbandiRSoggProgettoSedeVO();
		filtroVO.setProgrSoggettoProgettoSede(NumberUtil.toBigDecimal(progrSoggettoProgettoSede));

		try {
			genericDAO.delete(filtroVO);
			esito.setEsito(Boolean.TRUE);
			esito.setParams(new String[] { MessageConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO });
		} catch (Exception e) {
			String message = "Errore durante cancellazione record su PBANDI_R_SOGGPROGETTOSEDE " + e.getMessage();
			LOG.error(message, e);
			throw new UnrecoverableException(message, e);
		}

		LOG.info(prf + " END");
		return esito;

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////// SOGGETTI
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional
	public SoggettoProgettoDTO[] findSoggettiProgetto(Long idUtente, String idIride, Long idProgetto)
			throws FormalParameterException {
		String prf = "[GestioneDatiProgetto1420DAOImpl::findSoggettiProgetto]";
		LOG.info(prf + " BEGIN");
		String nameParameter[] = { "idUtente", "idIride", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

		SoggettoProgettoVO enteGiuridicoVO = new SoggettoProgettoVO();
		enteGiuridicoVO.setDescBreveTipoAnagrafica(Constants.RUOLO_ENTE_GIURIDICO);

		SoggettoProgettoVO personaFisicaVO = new SoggettoProgettoVO();
		personaFisicaVO.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);

		SoggettoProgettoVO beneficiarioVO = new SoggettoProgettoVO();
		beneficiarioVO.setDescBreveTipoAnagrafica(Constants.RUOLO_BENEFICIARIO);

		List<SoggettoProgettoVO> filtroTipoAnagraficaVO = new ArrayList<SoggettoProgettoVO>();
		filtroTipoAnagraficaVO.add(enteGiuridicoVO);
		filtroTipoAnagraficaVO.add(personaFisicaVO);
		filtroTipoAnagraficaVO.add(beneficiarioVO);

		SoggettoProgettoVO filtroVO = new SoggettoProgettoVO();
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		filtroVO.setAscendentOrder("codiceFiscaleSoggetto", "denominazione", "idSoggetto");

		AndCondition<SoggettoProgettoVO> andCondition = new AndCondition<SoggettoProgettoVO>(
				Condition.filterBy(filtroVO), Condition.filterBy(filtroTipoAnagraficaVO));

		List<SoggettoProgettoVO> soggettiVO = genericDAO.findListWhere(andCondition);

		List<SoggettoProgettoDTO> soggetti = new ArrayList<SoggettoProgettoDTO>();
		for (SoggettoProgettoVO vo : soggettiVO) {
			SoggettoProgettoDTO soggettoCorrente = beanUtil.transform(vo, SoggettoProgettoDTO.class);
			LOG.info(prf + " IdTipoSoggettoCorrelato: " + vo.getIdTipoSoggettoCorrelato());
			LOG.info(prf + " progrSoggettiCorrelati: " + vo.getProgrSoggettiCorrelati());
			soggetti.add(soggettoCorrente);
		}

		List<SoggettoProgettoDTO> soggetti2 = new ArrayList<SoggettoProgettoDTO>();
		for (SoggettoProgettoDTO s : soggetti) {
			if (s.getIdTipoAnagrafica() == 1) {
				s.setDescTipoSoggettoCorrelato("Beneficiario");
				soggetti2.add(s);
			} else if (s.getIdTipoAnagrafica() == 17) {
				// s.setid
				s.setDescTipoSoggettoCorrelato("");
				// Trova i ruoli ente del soggetto.
				PbandiRSoggProgRuoloEnteVO pbandiRSoggProgRuoloEnteVO = new PbandiRSoggProgRuoloEnteVO();
				pbandiRSoggProgRuoloEnteVO.setProgrSoggettoProgetto(new BigDecimal(s.getProgrSoggettoProgetto()));
				List<PbandiRSoggProgRuoloEnteVO> elencoProgrRuoloEnte = genericDAO
						.findListWhere(pbandiRSoggProgRuoloEnteVO);
				if (elencoProgrRuoloEnte != null) {
					for (PbandiRSoggProgRuoloEnteVO item : elencoProgrRuoloEnte) {
						LOG.info("findSoggettiProgetto(): IdRuoloEnteCompetenza = " + item.getIdRuoloEnteCompetenza());
						// Recupara la descrizione estesa dell'ente.
						PbandiDRuoloEnteCompetenzaVO pbandiDRuoloEnteCompetenzaVO = new PbandiDRuoloEnteCompetenzaVO();
						pbandiDRuoloEnteCompetenzaVO.setIdRuoloEnteCompetenza(item.getIdRuoloEnteCompetenza());
						PbandiDRuoloEnteCompetenzaVO ruoloEnte = genericDAO
								.findSingleWhere(pbandiDRuoloEnteCompetenzaVO);
						if (ruoloEnte != null) {
							LOG.info("findSoggettiProgetto(): DescRuoloEnte = " + ruoloEnte.getDescRuoloEnte());
							// Duplico il soggetto, gli assegno con il ruolo ente corrente e lo aggiungo
							// all'elenco.
							SoggettoProgettoDTO duplicato = beanUtil.transform(s, SoggettoProgettoDTO.class);
							s.setIdTipoSoggettoCorrelato(ruoloEnte.getIdRuoloEnteCompetenza().longValue());
							s.setDescTipoSoggettoCorrelato(ruoloEnte.getDescRuoloEnte());
						}
					}
				}
			} else {
				soggetti2.add(s);
			}
		}

		// Un po' di log...
		for (SoggettoProgettoDTO s : soggetti2) {
			LOG.info(prf + " findSoggettiProgetto(): IdSoggetto = " + s.getProgrSoggettiCorrelati()
					+ "; IdTipoAnagrafica = " + s.getIdTipoAnagrafica() + "; dtFineValidita = " + s.getDtFineValidita()
					+ "; idTipoSoggetto = " + s.getIdTipoSoggetto() + "; idTipoSoggettoCorrelato = "
					+ s.getIdTipoSoggettoCorrelato());
		}
		LOG.info(prf + " END");
		return beanUtil.transform(soggetti2, SoggettoProgettoDTO.class);
	}

	@Override
	@Transactional
	public Boolean cambiaStatoSoggettoProgetto(Long idUtente, String idIride, Long idSoggetto,
			Long idTipoSoggettoCorrelato) {
		String prf = "[GestioneDatiProgettoDAOImpl::findDettaglioSoggettoProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			Date oggi = DateUtil.getSysdateWithoutSeconds();

			// Cerco il record.
			PbandiRSoggProgSoggCorrelVO spsc = new PbandiRSoggProgSoggCorrelVO();
			spsc.setProgrSoggettoProgetto(new BigDecimal(idSoggetto));
			spsc.setProgrSoggettiCorrelati(new BigDecimal(idTipoSoggettoCorrelato));
			spsc = genericDAO.findSingleWhere(spsc);

			// Attiva \ disattiva.
			if (spsc.getDtFineValidita() == null)
				spsc.setDtFineValidita(DateUtil.utilToSqlDate(oggi));
			else
				spsc.setDtFineValidita(null);

			// Salva la modifica.
			spsc.setDtAggiornamento(DateUtil.utilToSqlDate(oggi));
			spsc.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(spsc);

			LOG.info(prf + " Record modificato con DtFineValidita = " + spsc.getDtFineValidita());

			return true;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in cambiaStatoSoggettoProgetto(): " + e);
			return false;
		}
	}

	@Override
	@Transactional
	public DettaglioSoggettoProgettoDTO findDettaglioSoggettoProgetto(Long idUtente, String idIride,
			Long progrSoggettoProgetto, Long idTipoSoggettoCorrelato, Long progrSoggettiCorrelati)
			throws FormalParameterException {
		String prf = "[GestioneDatiProgettoDAOImpl::findDettaglioSoggettoProgetto]";
		LOG.info(prf + " BEGIN");
		String nameParameter[] = { "idUtente", "identitaDigitale", "progrSoggettoProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, progrSoggettoProgetto);
		// BENEFICIARI-PERSONE-FISICHE - modificato il [12/10/2021]
		DettaglioSoggettoProgettoDTO dettaglio = new DettaglioSoggettoProgettoDTO();

		// Cerca PBANDI_R_SOGGETTO_PROGETTO.
		PbandiRSoggettoProgettoVO sp = new PbandiRSoggettoProgettoVO();
		sp.setProgrSoggettoProgetto(new BigDecimal(progrSoggettoProgetto));
		sp = genericDAO.findSingleWhere(sp);
		dettaglio.setIdSoggetto(sp.getIdSoggetto().toString());
		dettaglio.setIdProgetto(sp.getIdProgetto().toString());
		dettaglio.setProgrSoggettoProgetto(sp.getProgrSoggettoProgetto().toString());

		// Cerca PBANDI_T_SOGGETTO
		LOG.info(prf + "Cerco soggetto " + sp.getIdSoggetto());
		PbandiTSoggettoVO soggetto = new PbandiTSoggettoVO();
		soggetto.setIdSoggetto(sp.getIdSoggetto());
		soggetto = genericDAO.findSingleWhere(soggetto);
		dettaglio.setCodiceFiscale(soggetto.getCodiceFiscaleSoggetto());
		LOG.info(prf + "WWWW " + sp.getIdSoggetto() + " - " + soggetto.getCodiceFiscaleSoggetto() + " - "
				+ dettaglio.getCodiceFiscale());

		// Cerca PBANDI_T_PERSONA_FISICA
		if (sp.getIdPersonaFisica() != null) {
			LOG.info(prf + "Cerco persona fisica " + sp.getIdPersonaFisica());
			PbandiTPersonaFisicaVO pf = new PbandiTPersonaFisicaVO();
			pf.setIdPersonaFisica(sp.getIdPersonaFisica());
			pf = genericDAO.findSingleWhere(pf);
			pf = this.completaPersonaFisica(pf);
			dettaglio.setIdPersonaFisica(pf.getIdPersonaFisica().toString());
			dettaglio.setNome(pf.getNome());
			dettaglio.setCognome(pf.getCognome());
			dettaglio.setSesso(pf.getSesso());
			dettaglio.setDataNascita(DateUtil.getDate(pf.getDtNascita()));
			if (pf.getIdNazioneNascita() != null)
				dettaglio.setIdNazione(pf.getIdNazioneNascita().toString());
			// Distingue tra comune italiano o estero.
			if (pf.getIdComuneItalianoNascita() != null) {
				dettaglio.setIdComune(pf.getIdComuneItalianoNascita().toString());
				// Recupera la provincia dal comune (presente a video, ma assente su db).
				PbandiDComuneVO comune = new PbandiDComuneVO();
				comune.setIdComune(pf.getIdComuneItalianoNascita());
				comune = genericDAO.findSingleWhere(comune);
				dettaglio.setIdProvincia(comune.getIdProvincia().toString());
			} else if (pf.getIdComuneEsteroNascita() != null) {
				dettaglio.setIdComune(pf.getIdComuneEsteroNascita().toString());
			}
		}

		// Cerca PBANDI_T_INDIRIZZO
		if (sp.getIdIndirizzoPersonaFisica() != null) {
			LOG.info(prf + "Cerco indirizzo " + sp.getIdIndirizzoPersonaFisica());
			PbandiTIndirizzoVO ind = new PbandiTIndirizzoVO();
			ind.setIdIndirizzo(sp.getIdIndirizzoPersonaFisica());
			ind = genericDAO.findSingleWhere(ind);
			ind = this.completaIndirizzo(ind);
			if (ind.getIdIndirizzo() != null)
				dettaglio.setIdIndirizzo(ind.getIdIndirizzo().toString());
			dettaglio.setIndirizzoRes(ind.getDescIndirizzo());
			dettaglio.setNumCivicoRes(ind.getCivico());
			dettaglio.setCapRes(ind.getCap());
			if (ind.getIdNazione() != null)
				dettaglio.setIdNazioneRes(ind.getIdNazione().toString());
			if (ind.getIdRegione() != null)
				dettaglio.setIdRegioneRes(ind.getIdRegione().toString());
			if (ind.getIdProvincia() != null)
				dettaglio.setIdProvinciaRes(ind.getIdProvincia().toString());
			if (ind.getIdComune() != null)
				dettaglio.setIdComuneRes(ind.getIdComune().toString());
			if (ind.getIdComuneEstero() != null)
				dettaglio.setIdComuneRes(ind.getIdComuneEstero().toString());
		}
		if (idTipoSoggettoCorrelato != null)
			dettaglio.setIdTipoSoggettoCorrelato(NumberUtil.getStringValue(idTipoSoggettoCorrelato));
		if (progrSoggettiCorrelati != null)
			dettaglio.setProgrSoggettiCorrelati(NumberUtil.getStringValue(progrSoggettiCorrelati));

		LOG.info(prf + "Dati restituiti in output:" + dettaglio.toString());
		LOG.info(prf + " END");
		return dettaglio;

	}

	private PbandiTIndirizzoVO completaIndirizzo(PbandiTIndirizzoVO ind) {
		String prf = "[GestioneDatiProgetto1420DAOImpl::completaIndirizzo]";
		LOG.info(prf + " START");
		// Completo eventuali dati mancanti dell'indirizzo in input.
		if (ind.getIdComune() != null && ind.getIdProvincia() == null) {
			PbandiDComuneVO rec = new PbandiDComuneVO();
			rec.setIdComune(ind.getIdComune());
			rec = genericDAO.findSingleWhere(rec);
			ind.setIdProvincia(rec.getIdProvincia());
			LOG.info("Aggiungo provincia " + ind.getIdProvincia());
		}
		if (ind.getIdProvincia() != null && ind.getIdRegione() == null) {
			PbandiDProvinciaVO rec = new PbandiDProvinciaVO();
			rec.setIdProvincia(ind.getIdProvincia());
			rec = genericDAO.findSingleWhere(rec);
			ind.setIdRegione(rec.getIdRegione());
			LOG.info("Aggiungo regione " + ind.getIdRegione());
		}
		if (ind.getIdRegione() != null && ind.getIdNazione() == null) {
			ind.setIdNazione(new BigDecimal(118));
			LOG.info("Aggiungo nazione " + ind.getIdNazione());
		}
		if (ind.getIdComuneEstero() != null && ind.getIdNazione() == null) {
			PbandiDComuneEsteroVO rec = new PbandiDComuneEsteroVO();
			rec.setIdComuneEstero(ind.getIdComuneEstero());
			rec = genericDAO.findSingleWhere(rec);
			ind.setIdNazione(rec.getIdNazione());
			LOG.info("Aggiungo nazione estera " + ind.getIdNazione());
		}
		LOG.info(prf + " END");
		return ind;

	}

	private PbandiTPersonaFisicaVO completaPersonaFisica(PbandiTPersonaFisicaVO pf) {
		String prf = "[GestioneDatiProgetto1420DAOImpl::completaPersonaFisica]";
		LOG.info(prf + " START");
		// Completo eventuali dati mancanti della persona fisica in input.
		if (pf.getIdComuneItalianoNascita() != null && pf.getIdNazioneNascita() == null) {
			pf.setIdNazioneNascita(new BigDecimal(118));
			LOG.info("Aggiungo nazione nascita " + pf.getIdNazioneNascita());
		}
		if (pf.getIdComuneEsteroNascita() != null && pf.getIdNazioneNascita() == null) {
			PbandiDComuneEsteroVO rec = new PbandiDComuneEsteroVO();
			rec.setIdComuneEstero(pf.getIdComuneEsteroNascita());
			rec = genericDAO.findSingleWhere(rec);
			pf.setIdNazioneNascita(rec.getIdNazione());
			LOG.info("Aggiungo nazione nascita " + pf.getIdNazioneNascita());
		}
		LOG.info(prf + " END");
		return pf;

	}

	@Override
	@Transactional
	public RecapitoDTO findRecapito(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::findRecapito]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

			RecapitoVO rec = progettoManager.caricaRecapiti(idProgetto);
			LOG.debug("rec=" + rec);

			RecapitoDTO recapito = new RecapitoDTO();
			beanUtil.valueCopy(rec, recapito);
			if (rec != null) {
				LOG.info(prf + " flag rec: " + rec.getFlagEmailConfermata());
				LOG.info(prf + " flag recapito: " + recapito.getFlagEmailConfermata());
			}
			LOG.info(prf + " END");
			return recapito;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Boolean updateSoggettoProgettoSede(UserInfoSec userInfo, Long progrSoggettoProgettoSede, Long idRecapito)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::updateSoggettoProgettoSede]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "progrSoggettoProgettoSede", "idRecapito" };
			ValidatorInput.verifyNullValue(nameParameter, userInfo.getIdUtente(), userInfo.getIdIride(),
					progrSoggettoProgettoSede, idRecapito);
			int ret = progettoManager.updateSoggettoProgettoSede(userInfo.getIdUtente(), progrSoggettoProgettoSede,
					idRecapito);
			if (ret > 0) {
				LOG.info(prf + " END");
				return false;
			} else {
				LOG.info(prf + " END");
				return true;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	

	@Override
	@Transactional
	public Boolean updateSoggettoProgettoSedePec(UserInfoSec userInfo, Long progrSoggettoProgettoSede, Long idRecapito)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::updateSoggettoProgettoSedePec]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "progrSoggettoProgettoSede", "idRecapito" };
			ValidatorInput.verifyNullValue(nameParameter, userInfo.getIdUtente(), userInfo.getIdIride(),
					progrSoggettoProgettoSede, idRecapito);
			int ret = progettoManager.updateSoggettoProgettoSedePec(userInfo.getIdUtente(), progrSoggettoProgettoSede,
					idRecapito);
			if (ret > 0) {
				LOG.info(prf + " END");
				return false;
			} else {
				LOG.info(prf + " END");
				return true;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Recapiti getMailFromRecapiti(Long idUtente, String idIride, String email) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::getMailFromRecapiti]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "email" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, email);

			RecapitoVO rec = progettoManager.getMailFromRecapiti(email);
			LOG.info(prf + " END");
			return beanUtil.transform(rec, Recapiti.class);

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Recapiti getPecFromRecapiti(Long idUtente, String idIride, String pec) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::getPecFromRecapiti]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "email" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, pec);

			RecapitoVO rec = progettoManager.getPecFromRecapiti(pec);
			LOG.info(prf + " END");
			return beanUtil.transform(rec, Recapiti.class);

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Recapiti insertRecapito(Long idUtente, String idIride, Recapiti recapito) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::insertRecapito]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "recapito" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, recapito);

			PbandiTRecapitiVO vo = new PbandiTRecapitiVO();
			vo.setDtInizioValidita(new java.sql.Date(new GregorianCalendar().getTimeInMillis()));
			if(recapito.getEmail() != null) {
				vo.setEmail(recapito.getEmail().toUpperCase());
			}
			if(recapito.getPec() != null) {
				vo.setPec(recapito.getPec().toUpperCase());
			}
			vo.setFax(recapito.getFax());
//			vo.setFlagEmailConfermata(recapito.getFlagEmailConfermata().toUpperCase());
			vo.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
			vo.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			vo.setTelefono(recapito.getTelefono());

			try {

				vo = genericDAO.insert(vo);

			} catch (Exception e) {
				LOG.error("Error genericDAO.insert(folderVO)", e);
				throw new Exception(e.getMessage());
			}
			LOG.info(prf + " END");
			return beanUtil.transform(vo, Recapiti.class);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni saveCup(Long idUtente, String idIride, Long idProgetto, String cup) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::saveCup]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idUtente", "idIride", "idProgetto", "cup" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, cup);
			LOG.info(prf + " saveCup for idProgetto " + idProgetto + " and cup:" + cup);
			EsitoOperazioni esito = new EsitoOperazioni();
			if (cup.trim().length() < 1)
				throw new Exception("Il cup non puo' essere vuoto");

			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			pbandiTProgettoVO.setCup(cup);
			try {
				genericDAO.update(pbandiTProgettoVO);
			} catch (Exception x) {
				esito.setEsito(false);
				esito.setMsg("Errore nel salvataggio del cup.");
				return esito;
			}
			LOG.info(prf + " END");
			esito.setEsito(true);
			esito.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			return esito;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoOperazioni salvaDettaglioSoggettoProgetto(Long idUtente, String idIride,
			DettaglioSoggettoProgettoDTO dettaglio) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::salvaDettaglioSoggettoProgetto]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();

		String nameParameter[] = { "idUtente", "idIride", "dettaglio" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, dettaglio);

		Date oggi = DateUtil.getSysdateWithoutSeconds();
		Long idRuolo = null;
		boolean altriDatiAbilitati = false;
		if (dettaglio.getIdTipoSoggettoCorrelato() != null
				&& !StringUtil.isEmpty(dettaglio.getIdTipoSoggettoCorrelato())) {
			idRuolo = new Long(dettaglio.getIdTipoSoggettoCorrelato());
			altriDatiAbilitati = this.altriDatiSoggettoProgettoAbilitati(idRuolo);
		}

		// Memorizza se il record PBANDI_R_SOGGETTO_PROGETTO è da inserire o aggiornare.
		boolean soggettoProgettoDaInserire = false;
		if (StringUtil.isEmpty(dettaglio.getProgrSoggettoProgetto())) {
			soggettoProgettoDaInserire = true;
		}
		LOG.info("soggettoProgettoDaInserire = " + soggettoProgettoDaInserire);

		// PBANDI_T_SOGGETTO
		// Gestisco solo l'inserimento, poichè la modifica del codice fiscale non è
		// consentita.
		LOG.info("---------------------------------------------------------------------");
		LOG.info("Tabella PBANDI_T_SOGGETTO: ID_SOGGETTO in input = " + dettaglio.getIdSoggetto());
		try {
			if (StringUtil.isEmpty(dettaglio.getIdSoggetto())) {
				LOG.info("Cerco per IdTipoSoggetto = 1 (persona fisica) e cf = " + dettaglio.getCodiceFiscale());
				PbandiTSoggettoVO soggetto = new PbandiTSoggettoVO();
				soggetto.setCodiceFiscaleSoggetto(dettaglio.getCodiceFiscale());
				soggetto.setIdTipoSoggetto(new BigDecimal(Constants.ID_TIPO_SOGG_PERSONA_FISICA));
				soggetto = genericDAO.findSingleOrNoneWhere(soggetto);
				if (soggetto == null || soggetto.getIdSoggetto() == null) {
					LOG.info("Nessun soggetto trovato.");
					soggetto = new PbandiTSoggettoVO();
					soggetto.setCodiceFiscaleSoggetto(dettaglio.getCodiceFiscale());
					soggetto.setIdTipoSoggetto(new BigDecimal(Constants.ID_TIPO_SOGG_PERSONA_FISICA));
					soggetto.setIdUtenteIns(new BigDecimal(idUtente));
					soggetto.setDtInserimento(DateUtil.utilToSqlDate(oggi));
					soggetto = genericDAO.insert(soggetto);
					if (soggetto.getIdSoggetto() == null)
						throw new Exception("ERRORE: Fallita insert su PBANDI_T_SOGGETTO.");
					else
						LOG.info("Inserito nuovo record con ID_SOGGETTO = " + soggetto.getIdSoggetto());
				} else {
					LOG.info("Trovato record con ID_SOGGETTO = " + soggetto.getIdSoggetto());
				}
				// Memorizzo l'id_soggetto per salvarlo alla fine in PBANDI_R_SOGGETTO_PROGETTO.
				dettaglio.setIdSoggetto(bigdecimalToString(soggetto.getIdSoggetto()));
			}
		} catch (Exception e) {
			LOG.error("ERRORE su PBANDI_T_SOGGETTO: " + e);
			throw new Exception("Errore durante il salvataggio del soggetto.");
		}

		// PBANDI_T_PERSONA_FISICA
		LOG.info("---------------------------------------------------------------------");
		LOG.info("Tabella PBANDI_T_PERSONA_FISICA: ID_PERSONA_FISICA in input = " + dettaglio.getIdPersonaFisica());
		try {
			if (StringUtil.isEmpty(dettaglio.getIdPersonaFisica())) {
				// Determino se inserire nuovo record o usare record già esistente.
				Long idPersonaFisica = esisteGiaPersonaFisica(dettaglio);
				// Long idPersonaFisica =
				// codiceFiscaleAlredyExsist(dettaglio.getCodiceFiscale());

				if (idPersonaFisica == null) {
					LOG.info("Inserisco nuovo record in PBANDI_T_PERSONA_FISICA.");
					idPersonaFisica = salvaPersonaFisica(dettaglio, null, idUtente, oggi);
					LOG.info("IdPersonaFisica del nuovo record: " + idPersonaFisica);
				}

				// Memorizzo l'id_persona_fisica trovato su db
				// per salvarlo alla fine in PBANDI_R_SOGGETTO_PROGETTO.
				LOG.info("Associo al soggetto-progetto il seguente idPersonaFisica: " + idPersonaFisica);
				dettaglio.setIdPersonaFisica(String.valueOf(idPersonaFisica));

			} else {
				LOG.info("Modifico la persona fisica avente id: " + dettaglio.getIdPersonaFisica());
				Long idPersonaFisica = new Long(dettaglio.getIdPersonaFisica());
				salvaPersonaFisica(dettaglio, idPersonaFisica, idUtente, oggi);
			}
		} catch (Exception e) {
			LOG.error("ERRORE su PBANDI_T_PERSONA_FISICA: " + e);
			throw new Exception("Errore durante il salvataggio del soggetto.");
		}

		// PBANDI_T_INDIRIZZO
		if (altriDatiAbilitati) {
			LOG.info("---------------------------------------------------------------------");
			LOG.info("Tabella PBANDI_T_INDIRIZZO: ID_INDIRIZZO in input = " + dettaglio.getIdIndirizzo());
			try {
				if (StringUtil.isEmpty(dettaglio.getIdIndirizzo())) {
					// Determino se inserire nuovo record o usare record già esistente.
					Long idIndirizzo = esisteGiaIndirizzo(dettaglio);
					if (idIndirizzo == null) {
						LOG.info("Inserisco nuovo record in PBANDI_T_INDIRIZZO.");
						idIndirizzo = salvaIndirizzo(dettaglio, null, idUtente, oggi);
					}

					// Memorizzo l'idIndirizzo trovato su db
					// per salvarlo alla fine in PBANDI_R_SOGGETTO_PROGETTO.
					LOG.info("Associo al soggetto-progetto il seguente idIndirizzo: " + idIndirizzo);
					dettaglio.setIdIndirizzo(String.valueOf(idIndirizzo));

				} else {
					LOG.info("Modifico l'indirizzo avente id: " + dettaglio.getIdIndirizzo());
					Long idIndirizzo = new Long(dettaglio.getIdIndirizzo());
					salvaIndirizzo(dettaglio, idIndirizzo, idUtente, oggi);
				}
			} catch (Exception e) {
				LOG.error("ERRORE su PBANDI_T_INDIRIZZO: " + e);
				throw new Exception("Errore durante il salvataggio del soggetto.");
			}
		}

		// PBANDI_R_SOGGETTO_PROGETTO
		LOG.info("---------------------------------------------------------------------");
		LOG.info("Tabella PBANDI_R_SOGGETTO_PROGETTO");
		try {
			if (soggettoProgettoDaInserire) {
				// Cerca se esiste già un record riutilizzabile o se crearne uno nuovo.
				BigDecimal progrSoggettoProgetto = null;
				try {
					progrSoggettoProgetto = findProgrSoggettoProgetto(dettaglio, idUtente, oggi);
					if (progrSoggettoProgetto == null)
						throw new Exception("progrSoggettoProgetto NULLO.");
				} catch (Exception e) {
					LOG.error("ERRORE in findProgrSoggettoProgetto(): " + e);
					throw new Exception("Errore durante il salvataggio del soggetto.");
				}
				LOG.info("progrSoggettoProgetto = " + progrSoggettoProgetto);

				// Memorizza il ProgrSoggettoProgetto, per usarlo successivamente.
				dettaglio.setProgrSoggettoProgetto(bigdecimalToString(progrSoggettoProgetto));
			} else {
				LOG.info("Aggiorno il soggetto-progetto con progr_soggetto_progetto = "
						+ dettaglio.getProgrSoggettoProgetto());
				PbandiRSoggettoProgettoVO sp = new PbandiRSoggettoProgettoVO();
				sp.setProgrSoggettoProgetto(stringToBigdecimal(dettaglio.getProgrSoggettoProgetto()));
				sp.setIdPersonaFisica(stringToBigdecimal(dettaglio.getIdPersonaFisica()));
				sp.setIdIndirizzoPersonaFisica(stringToBigdecimal(dettaglio.getIdIndirizzo()));
				sp.setIdUtenteAgg(new BigDecimal(idUtente));
				sp.setDtAggiornamento(DateUtil.utilToSqlDate(oggi));
				genericDAO.update(sp);
				LOG.info("Valori: IdPersonaFisica = " + sp.getIdPersonaFisica() + "; IdIndirizzoPersonaFisica = "
						+ sp.getIdIndirizzoPersonaFisica());
			}
		} catch (Exception e) {
			LOG.error("ERRORE su PBANDI_R_SOGGETTO_PROGETTO: " + e);
			throw new Exception("Errore durante il salvataggio del soggetto.");
		}

		// GESTIONE MODIFICA RUOLO.
		LOG.info("---------------------------------------------------------------------");
		LOG.info("GESTIONE MODIFICA RUOLO");
		String ruoloNuovo = dettaglio.getIdTipoSoggettoCorrelato();
		String ruoloVecchio = dettaglio.getIdTipoSoggettoCorrelatoAttuale();
		LOG.info("ruoloNuovo = " + ruoloNuovo + "; ruoloVecchio = " + ruoloVecchio);
		// Nel caso di beneficiario persona fisica il ruolo nuovo è nullo, quindi
		// aggiungo l'if sotto.
		if (ruoloNuovo != null) {
			if (ruoloNuovo.equals(ruoloVecchio)) {
				LOG.info("Ruolo invariato.");
			} else {
				LOG.info("Ruolo inserito o modificato.");

				// Cerco l'id_soggetto del Beneficiario del progetto.
				BigDecimal idSoggettoBeneficiario = null;
				try {
					idSoggettoBeneficiario = findIdSoggettoBeneficiario(dettaglio);
					if (idSoggettoBeneficiario == null)
						throw new Exception("idSoggettoBeneficiario NULLO.");
				} catch (Exception e) {
					LOG.error("ERRORE in findIdSoggettoBeneficiario(): " + e);
					throw new Exception("Errore durante il salvataggio del soggetto.");
				}
				LOG.info("idSoggettoBeneficiario = " + idSoggettoBeneficiario);

				// PBANDI_R_SOGGETTI_CORRELATI
				BigDecimal progrSoggettiCorrelatiNUOVORuolo = null;
				try {
					progrSoggettiCorrelatiNUOVORuolo = findProgrSoggettiCorrelati(dettaglio, idSoggettoBeneficiario,
							idUtente, oggi);
					if (progrSoggettiCorrelatiNUOVORuolo == null)
						throw new Exception("progrSoggettiCorrelati NULLO.");
				} catch (Exception e) {
					LOG.error("ERRORE in findProgrSoggettiCorrelati(): " + e);
					throw new Exception("Errore durante il salvataggio del soggetto.");
				}

				// PBANDI_R_SOGG_PROG_SOGG_CORREL
				// Se la coppia <ProgrSoggettoProgetto+ProgrSoggettiCorrelati> esiste già, la
				// riattivo;
				// altrimenti inserisco un nuovo record.
				try {
					LOG.info("Cerco in PBANDI_R_SOGG_PROG_SOGG_CORREL con ProgrSoggettoProgetto = "
							+ dettaglio.getProgrSoggettoProgetto() + "; ProgrSoggettiCorrelati = "
							+ progrSoggettiCorrelatiNUOVORuolo);
					PbandiRSoggProgSoggCorrelVO spsc = new PbandiRSoggProgSoggCorrelVO();
					spsc.setProgrSoggettoProgetto(stringToBigdecimal(dettaglio.getProgrSoggettoProgetto()));
					spsc.setProgrSoggettiCorrelati(progrSoggettiCorrelatiNUOVORuolo);
					PbandiRSoggProgSoggCorrelVO spscSuDB = genericDAO.findSingleOrNoneWhere(spsc);

					if (spscSuDB == null) {

						if (dettaglio.getCodiceRuolo().equals("BEN-MASTER")
								|| dettaglio.getCodiceRuolo().equals("PERSONA-FISICA")) {
							if (soggettoProgettoDaInserire && (dettaglio.getIdTipoSoggettoCorrelato()
									.equals(String.valueOf(Constants.ID_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE))
									|| dettaglio.getIdTipoSoggettoCorrelato()
											.equals(String.valueOf(Constants.ID_TIPO_SOGG_CORRELATO_DELEGATO_FIRMA)))) {

								spsc.setDtFineValidita(DateUtil.utilToSqlDate(oggi));

							}
						}

						LOG.info("Record non trovato: inserisco un nuovo record.");
						spsc.setIdUtenteIns(new BigDecimal(idUtente));
						spsc.setDtInserimento(DateUtil.utilToSqlDate(oggi));
						spsc = genericDAO.insert(spsc);
						LOG.info("Record inserito.");
					} else {
						// Jira PBANDI-2652 : gestito il caso (DtFineValidita() == null).
						if (spscSuDB.getDtFineValidita() == null) {
							LOG.info("Record trovato e attivo : segnalo a video che esiste già");
							esito.setEsito(false);
							esito.setMsg("Soggetto già associato al progetto con questo ruolo.");
							return esito;
						} else {
							LOG.info("Record trovato e disattivo (DtFineValidita = " + spscSuDB.getDtFineValidita()
									+ "): lo riattivo");
							spscSuDB.setDtFineValidita(null);
							genericDAO.updateNullables(spscSuDB);
						}
					}

				} catch (Exception e) {
					LOG.error("ERRORE in PBANDI_R_SOGG_PROG_SOGG_CORREL: " + e.getMessage());
					throw new Exception("Errore durante il salvataggio del soggetto. " + e.getMessage());
				}

				// In caso di ruolo modificato, disattivo il ruolo precedente.
				// Per disattivare il soggetto-progetto, si valorizza la data fine validità.
				String progrSoggettiCorrelatiVECCHIORuolo = dettaglio.getProgrSoggettiCorrelati();

				if (!StringUtil.isEmpty(progrSoggettiCorrelatiVECCHIORuolo)) {
					try {
						LOG.info("Disattivo il ruolo precedente: ProgrSoggettoProgetto = "
								+ dettaglio.getProgrSoggettoProgetto() + "; ProgrSoggettiCorrelati = "
								+ progrSoggettiCorrelatiVECCHIORuolo);
						PbandiRSoggProgSoggCorrelVO spsc = new PbandiRSoggProgSoggCorrelVO();
						spsc.setProgrSoggettoProgetto(stringToBigdecimal(dettaglio.getProgrSoggettoProgetto()));
						spsc.setProgrSoggettiCorrelati(stringToBigdecimal(progrSoggettiCorrelatiVECCHIORuolo));
						spsc.setDtFineValidita(DateUtil.utilToSqlDate(oggi));
						spsc.setIdUtenteAgg(new BigDecimal(idUtente));
						genericDAO.updateNullables(spsc);
					} catch (Exception e) {
						LOG.error("ERRORE in disattivazione PBANDI_R_SOGG_PROG_SOGG_CORREL: " + e);
						throw new Exception("Errore durante il salvataggio del soggetto.");
					}
				}

				LOG.info("progrSoggettiCorrelati = " + progrSoggettiCorrelatiNUOVORuolo);
				dettaglio.setProgrSoggettiCorrelati(String.valueOf(progrSoggettiCorrelatiNUOVORuolo));

			}
		}

		// PBANDI_T_UTENTE (JIRA 2641 inizio)
		LOG.info("---------------------------------------------------------------------");
		LOG.info("Tabella PBANDI_T_UTENTE (");
		try {
//			if (soggettoProgettoDaInserire) {   -- if (StringUtil.isEmpty(dettaglio.getProgrSoggettoProgetto())) {
			if (dettaglio.isAccessoSistema() && (dettaglio.getCodiceRuolo().equals("ADG-ISTRUTTORE")
					|| dettaglio.getCodiceRuolo().equals("ADG-IST-MASTER"))) {
				List<PbandiTUtenteVO> listPbandiTUtenteVO = findPbandiTUtenteVO(dettaglio.getCodiceFiscale());
				if (listPbandiTUtenteVO != null && listPbandiTUtenteVO.size() > 0) {
					LOG.info("Utente con codice fiscale = " + dettaglio.getCodiceFiscale()
							+ " già presente sulla PBANDI_T_UTENTE");
				} else {
					insertPbndiTUtente(dettaglio, oggi, idUtente, soggettoProgettoDaInserire);
				}

			} else if (dettaglio.getCodiceRuolo().equals("BEN-MASTER")
					|| dettaglio.getCodiceRuolo().equals("PERSONA-FISICA")) {

				if (soggettoProgettoDaInserire && (dettaglio.getIdTipoSoggettoCorrelato()
						.equals(String.valueOf(Constants.ID_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE))
						|| dettaglio.getIdTipoSoggettoCorrelato()
								.equals(String.valueOf(Constants.ID_TIPO_SOGG_CORRELATO_DELEGATO_FIRMA)))) {
					LOG.info(
							"Inserimento nuovo record di un Rappresentante Legale/delegato alla firma su PBANDI_T_RICHIESTA_ABILITAZ. In attesa consento del MASTER");
					insertRichiestaAbilitazione(idUtente, dettaglio);
				} else if (dettaglio.isAccessoSistema()) {

					List<PbandiTUtenteVO> listPbandiTUtenteVO = findPbandiTUtenteVO(dettaglio.getCodiceFiscale());
					if (listPbandiTUtenteVO != null && listPbandiTUtenteVO.size() > 0) {
						LOG.info("Utente con codice fiscale = " + dettaglio.getCodiceFiscale()
								+ " già presente sulla PBANDI_T_UTENTE");
					} else {
						LOG.info("Inserimento sulla tabella PBANDI_T_UTENTE con codice fiscale = "
								+ dettaglio.getCodiceFiscale());
						insertPbndiTUtente(dettaglio, oggi, idUtente, soggettoProgettoDaInserire);
					}

				}

//				if(result == -1 && soggettoProgettoDaInserire) {
//					
//					insertPbndiTUtente(dettaglio, oggi, idUtente);
//					
//				}

			} else if (soggettoProgettoDaInserire && (!dettaglio.getIdTipoSoggettoCorrelato()
					.equals(String.valueOf(Constants.ID_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE))
					|| !dettaglio.getIdTipoSoggettoCorrelato()
							.equals(String.valueOf(Constants.ID_TIPO_SOGG_CORRELATO_DELEGATO_FIRMA)))) {

				LOG.info(
						"Sto inserendo ma non un Rappresentante Legale/Delegato alla firma: nessun inserimento su PBANDI_T_UTENTE");

			}

		} catch (Exception e) {
			LOG.error("ERRORE su PBANDI_T_UTENTE: " + e);
			throw new Exception("Errore durante il salvataggio del soggetto.");
		}
		esito.setEsito(true);
		esito.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		// JIRA 2641 fine
		return esito;

	}

	private void insertPbndiTUtente(DettaglioSoggettoProgettoDTO dettaglio, Date oggi, Long idUtente,
			boolean soggettoProgettoDaInserire) throws Exception {

		try {

			LOG.info(
					"Sto inserendo un Rappresentante Legale/Delegato alla firma: inserisco un record su PBANDI_T_UTENTE");
			PbandiTUtenteVO utente = new PbandiTUtenteVO();
			utente.setCodiceUtente(dettaglio.getCodiceFiscale());
			utente.setIdTipoAccesso(new BigDecimal(4));
			utente.setIdSoggetto(new BigDecimal(dettaglio.getIdSoggetto()));
			utente.setDtInserimento(DateUtil.utilToSqlDate(oggi));
			genericDAO.insert(utente);
			if (!esisteGiaSoggTipoAnag(dettaglio.getIdSoggetto())) {
				// Nota: insert aggiunta da Tinti il 20/1/2017
				// test esisteGiaSoggTipoAnag() aggiunto da Alex il 11/4/2017 su richiesta di
				// Panarace\Pacilio.
				LOG.info("\n\n\n\n\n\n++++++++++++++ inserisco record nella PbandiRSoggTipoAnagraficaVO ");
				PbandiRSoggTipoAnagraficaVO pbandiRSoggTipoAnagraficaVO = new PbandiRSoggTipoAnagraficaVO();
				pbandiRSoggTipoAnagraficaVO.setIdSoggetto(new BigDecimal(dettaglio.getIdSoggetto()));
				pbandiRSoggTipoAnagraficaVO
						.setIdTipoAnagrafica(new BigDecimal(Constants.ID_TIPO_ANAGRAFICA_PERSONA_FISICA));
				pbandiRSoggTipoAnagraficaVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
				pbandiRSoggTipoAnagraficaVO.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
				genericDAO.insert(pbandiRSoggTipoAnagraficaVO);
				LOG.info("PbandiRSoggTipoAnagraficaVO inserito\n\n\n\n\n\n\n");
			}

//			if(soggettoProgettoDaInserire) {
//				PbandiRSoggProgSoggCorrelVO spsc = new PbandiRSoggProgSoggCorrelVO();
//				spsc.setProgrSoggettoProgetto(stringToBigdecimal(dettaglio.getProgrSoggettoProgetto()));
//				spsc.setProgrSoggettiCorrelati(stringToBigdecimal(dettaglio.getProgrSoggettiCorrelati()));
//				spsc.setDtFineValidita(null);
////				spsc.setIdUtenteAgg(new BigDecimal(idUtente));
//				genericDAO.updateNullables(spsc);
//			}

		} catch (Exception e) {
			LOG.error("ERRORE su PBANDI_T_UTENTE: " + e);
			throw new Exception("Errore durante il salvataggio del soggetto.");
		}

	}

	private static final String RICHIESTA_ABILITAZIONE = ""
			+ "insert into PBANDI_T_RICHIESTA_ABILITAZ (ID_UTENTE_INS , DT_RICHIESTA , PROGR_SOGGETTO_PROGETTO , PROGR_SOGGETTI_CORRELATI, ACCESSO_SISTEMA) "
			+ "values (?, sysdate, ?, ?,?)";

	private int insertRichiestaAbilitazione(Long idUtente, DettaglioSoggettoProgettoDTO dettaglio) {

		String prf = "[GestioneDatiProgettoDAOImpl::insertRichiestaAbilitazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idUtente = " + idUtente + ", ProgrSoggettoProgetto = "
				+ dettaglio.getProgrSoggettoProgetto() + ", ProgrSoggettiCorrelati = "
				+ dettaglio.getProgrSoggettiCorrelati());
		int result = -1;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String accessoSistema = null;

		try {

			if (dettaglio.isAccessoSistema()) {
				accessoSistema = "S";
			} else {
				accessoSistema = "N";
			}

			result = jdbcTemplate.update(RICHIESTA_ABILITAZIONE, new Object[] { idUtente,
					dettaglio.getProgrSoggettoProgetto(), dettaglio.getProgrSoggettiCorrelati(), accessoSistema });

		} catch (DataIntegrityViolationException e) {

			LOG.debug(prf + " Utente con ProgrSoggettoProgetto = " + dettaglio.getProgrSoggettoProgetto()
					+ " e ProgrSoggettiCorrelati = " + dettaglio.getProgrSoggettiCorrelati() + " "
					+ "gia' presente nella tabella PBANDI_T_RICHIESTA_ABILITAZ. ");

			result = -1;
		}

		LOG.debug(prf + " END");

		return result;

	}

	private boolean esisteGiaSoggTipoAnag(String idSoggetto) {
		String prf = "[GestioneDatiProgettoDAOImpl::esisteGiaSoggTipoAnag]";
		LOG.info(prf + " START");
		PbandiRSoggTipoAnagraficaVO vo = new PbandiRSoggTipoAnagraficaVO();
		vo.setIdSoggetto(new BigDecimal(idSoggetto));
		vo.setIdTipoAnagrafica(new BigDecimal(Constants.ID_TIPO_ANAGRAFICA_PERSONA_FISICA));
		List<PbandiRSoggTipoAnagraficaVO> lista = genericDAO.findListWhere(vo);
		if (lista != null && lista.size() > 0) {
			LOG.info("esisteGiaSoggTipoAnag(): PRESENTE record con idSoggetto = " + idSoggetto
					+ " e idTipoAnagrafica = " + Constants.ID_TIPO_ANAGRAFICA_PERSONA_FISICA);
			return true;
		}
		LOG.info("esisteGiaSoggTipoAnag(): ASSENTE record con idSoggetto = " + idSoggetto + " e idTipoAnagrafica = "
				+ Constants.ID_TIPO_ANAGRAFICA_PERSONA_FISICA);
		return false;
	}

	private BigDecimal findProgrSoggettiCorrelati(DettaglioSoggettoProgettoDTO dettaglio,
			BigDecimal idSoggettoBeneficiario, Long idUtente, Date oggi) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::findProgrSoggettiCorrelati]";
		LOG.info(prf + " START");
		PbandiRSoggettiCorrelatiVO sc = new PbandiRSoggettiCorrelatiVO();
		sc.setIdSoggetto(stringToBigdecimal(dettaglio.getIdSoggetto()));
		sc.setIdSoggettoEnteGiuridico(idSoggettoBeneficiario);
		sc.setIdTipoSoggettoCorrelato(stringToBigdecimal(dettaglio.getIdTipoSoggettoCorrelato()));
		sc.setDescendentOrder("progrSoggettiCorrelati");
		LOG.info("Cerco in PBANDI_R_SOGGETTI_CORRELATI per: IdSoggetto = " + sc.getIdSoggetto()
				+ "; IdSoggettoEnteGiuridico = " + sc.getIdSoggettoEnteGiuridico() + "; IdTipoSoggettoCorrelato = "
				+ sc.getIdTipoSoggettoCorrelato());
		List<PbandiRSoggettiCorrelatiVO> lista = genericDAO.findListWhere(sc);
		// Se esiste scelgo il primo record attivo.
		for (PbandiRSoggettiCorrelatiVO item : lista) {
			if (item.getDtFineValidita() == null) {
				LOG.info("Trovato record attivo.");
				return item.getProgrSoggettiCorrelati();
			}
		}
		LOG.info("Nessun record attivo trovato: inserisco nuovo record");
		sc.setDtInizioValidita(DateUtil.utilToSqlDate(oggi));
		sc.setIdUtenteIns(new BigDecimal(idUtente));
		sc = genericDAO.insert(sc);
		LOG.info(prf + " END");
		return sc.getProgrSoggettiCorrelati();
	}

	private BigDecimal findIdSoggettoBeneficiario(DettaglioSoggettoProgettoDTO dettaglio) {
		String prf = "[GestioneDatiProgettoDAOImpl::findIdSoggettoBeneficiario]";
		LOG.info(prf + " START");
		PbandiRSoggettoProgettoVO sp = new PbandiRSoggettoProgettoVO();
		sp.setIdProgetto(stringToBigdecimal(dettaglio.getIdProgetto()));
		sp.setIdTipoAnagrafica(new BigDecimal(1)); // BENEFICIARIO
		List<PbandiRSoggettoProgettoVO> lista = genericDAO.findListWhere(sp);
		for (PbandiRSoggettoProgettoVO item : lista) {
			if (item.getIdTipoBeneficiario() != null && item.getIdTipoBeneficiario().intValue() != 4) // BEN-ASSOCIATO
				return item.getIdSoggetto();
		}
		LOG.info(prf + " END");
		return null;
	}

	private BigDecimal findProgrSoggettoProgetto(DettaglioSoggettoProgettoDTO dettaglio, Long idUtente, Date oggi)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::findProgrSoggettoProgetto]";
		LOG.info(prf + " START");
		PbandiRSoggettoProgettoVO sp = new PbandiRSoggettoProgettoVO();
		sp.setIdProgetto(stringToBigdecimal(dettaglio.getIdProgetto()));
		sp.setIdSoggetto(stringToBigdecimal(dettaglio.getIdSoggetto()));
		sp.setIdPersonaFisica(stringToBigdecimal(dettaglio.getIdPersonaFisica()));
		sp.setIdIndirizzoPersonaFisica(stringToBigdecimal(dettaglio.getIdIndirizzo()));
		sp.setIdTipoAnagrafica(new BigDecimal(Constants.ID_TIPO_ANAGRAFICA_PERSONA_FISICA));
		sp.setDescendentOrder("progrSoggettoProgetto");
		LOG.info("Cerco in PBANDI_R_SOGGETTO_PROGETTO per: IdProgetto = " + sp.getIdProgetto() + "; IdSoggetto = "
				+ sp.getIdSoggetto() + "; IdPersonaFisica = " + sp.getIdPersonaFisica()
				+ "; IdIndirizzoPersonaFisica = " + sp.getIdIndirizzoPersonaFisica() + "; IdTipoAnagrafica = "
				+ sp.getIdTipoAnagrafica());

		List<PbandiRSoggettoProgettoVO> lista = genericDAO.findListWhere(sp);
		// Se esiste scelgo il primo record attivo.
		for (PbandiRSoggettoProgettoVO item : lista) {
			if (item.getDtFineValidita() == null) {
				LOG.info("Trovato record attivo.");
				return item.getProgrSoggettoProgetto();
			}
		}
		LOG.info("Nessun record attivo trovato: inserisco nuovo record");
		sp.setIdUtenteIns(new BigDecimal(idUtente));
		sp.setDtInizioValidita(DateUtil.utilToSqlDate(oggi));
		sp.setDtInserimento(DateUtil.utilToSqlDate(oggi));
		sp = genericDAO.insert(sp);
		LOG.info(prf + " END");
		return sp.getProgrSoggettoProgetto();
	}

	private Long salvaIndirizzo(DettaglioSoggettoProgettoDTO dettaglio, Long idIndirizzo, Long idUtente, Date oggi)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::salvaIndirizzo]";
		LOG.info(prf + " START");
		Long idNazioneRes = NumberUtil.toNullableLong(dettaglio.getIdNazioneRes());
		// Se sto per inserire, creo un nuovo oggetto.
		// Se sto per modificare, cerco il record su db.
		PbandiTIndirizzoVO i = new PbandiTIndirizzoVO();
		if (idIndirizzo != null) {
			i.setIdIndirizzo(new BigDecimal(idIndirizzo));
			i = genericDAO.findSingleWhere(i);
		}
		// Popolo i dati da inserire.
		LOG.info("Aggiorno indirizzo+civico+cap+stato+regione+provincia+comune.");
		i.setDescIndirizzo(dettaglio.getIndirizzoRes());
		i.setCivico(dettaglio.getNumCivicoRes());
		i.setCap(dettaglio.getCapRes());
		i.setIdNazione(stringToBigdecimal(dettaglio.getIdNazioneRes()));
		if (Constants.ID_NAZIONE_ITALIA.equals(new BigDecimal(idNazioneRes))) {
			i.setIdRegione(stringToBigdecimal(dettaglio.getIdRegioneRes()));
			i.setIdProvincia(stringToBigdecimal(dettaglio.getIdProvinciaRes()));
			i.setIdComune(stringToBigdecimal(dettaglio.getIdComuneRes()));
			i.setIdComuneEstero(null);
		} else {
			i.setIdComuneEstero(stringToBigdecimal(dettaglio.getIdComuneRes()));
			i.setIdRegione(null);
			i.setIdProvincia(null);
			i.setIdComune(null);
		}

		Long output = null;
		if (idIndirizzo == null) {
			LOG.info("Inserisco nuovo record.");
			i.setIdUtenteIns(new BigDecimal(idUtente));
			i.setDtInizioValidita(DateUtil.utilToSqlDate(oggi));
			i = genericDAO.insert(i);
			output = i.getIdIndirizzo().longValue();
			LOG.info("IdIndirizzo nuovo record = " + output);
		} else {
			LOG.info("Modifico record con idIndirizzo = " + idIndirizzo);
			i.setIdUtenteAgg(new BigDecimal(idUtente));
			i.setIdIndirizzo(new BigDecimal(idIndirizzo));
			genericDAO.updateNullables(i);
			output = idIndirizzo;
		}
		LOG.info(prf + " END");
		return output;
	}

	private Long esisteGiaIndirizzo(DettaglioSoggettoProgettoDTO dettaglio) {
		String prf = "[GestioneDatiProgettoDAOImpl::esisteGiaIndirizzo]";
		LOG.info(prf + " START");
		Long idNazioneRes = NumberUtil.toNullableLong(dettaglio.getIdNazioneRes());
		PbandiTIndirizzoVO i = new PbandiTIndirizzoVO();
		LOG.info("Cerco per indirizzo+civico+cap+stato+regione+provincia+comune.");
		i.setDescIndirizzo(dettaglio.getIndirizzoRes());
		i.setCivico(dettaglio.getNumCivicoRes());
		i.setCap(dettaglio.getCapRes());
		i.setIdNazione(stringToBigdecimal(dettaglio.getIdNazioneRes()));
		if (Constants.ID_NAZIONE_ITALIA.equals(new BigDecimal(idNazioneRes))) {
			i.setIdRegione(stringToBigdecimal(dettaglio.getIdRegioneRes()));
			i.setIdProvincia(stringToBigdecimal(dettaglio.getIdProvinciaRes()));
			i.setIdComune(stringToBigdecimal(dettaglio.getIdComuneRes()));
		} else {
			i.setIdComuneEstero(stringToBigdecimal(dettaglio.getIdComuneRes()));
		}

		i.setDescendentOrder("idIndirizzo"); // il primo record trovato è quello con id max.
		List<PbandiTIndirizzoVO> lista = genericDAO.findListWhere(i);
		LOG.info("Record trovati = " + lista.size());

		Long idIndirizzo = null;
		if (lista.size() > 0) {
			// Restituisco in output l'id del primo record trovato.
			if (lista.get(0) != null && lista.get(0).getIdIndirizzo() != null)
				idIndirizzo = lista.get(0).getIdIndirizzo().longValue();
			LOG.info("IdIndirizzo selezionato = " + idIndirizzo);
		} else {
			// Restituisco in output null.
			LOG.info("Nessun IdIndirizzo selezionato.");
		}
		LOG.info(prf + " END");
		return idIndirizzo;
	}

	private java.lang.Long salvaPersonaFisica(DettaglioSoggettoProgettoDTO dettaglio, Long idPersonaFisica,
			java.lang.Long idUtente, Date oggi) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::salvaPersonaFisica]";
		LOG.info(prf + " START");
		Long idNazione = NumberUtil.toNullableLong(dettaglio.getIdNazione());
		Long output = null;

		Long idRuolo = null;
		boolean altriDatiAbilitati = false;
//		if (dettaglio.getIdTipoSoggettoCorrelato() != null && !StringUtil.isDouble(dettaglio.getIdTipoSoggettoCorrelato())){
		if (dettaglio.getIdTipoSoggettoCorrelato() != null) {
			idRuolo = new Long(dettaglio.getIdTipoSoggettoCorrelato());
			altriDatiAbilitati = this.altriDatiSoggettoProgettoAbilitati(idRuolo);
		}

		// Se sto per inserire, creo un nuovo oggetto.
		// Se sto per modificare, cerco il record su db.
		PbandiTPersonaFisicaVO pf = new PbandiTPersonaFisicaVO();
		if (idPersonaFisica != null) {
			pf.setIdPersonaFisica(new BigDecimal(idPersonaFisica));
			pf = genericDAO.findSingleWhere(pf);
		}

		// Popolo i dati da inserire.
		pf.setNome(dettaglio.getNome());
		pf.setCognome(dettaglio.getCognome());
		pf.setIdSoggetto(stringToBigdecimal(dettaglio.getIdSoggetto()));
		if (altriDatiAbilitati) {
			LOG.info("Campi da aggiornare: nome+cognome+sesso+dataNascita+statoNascita+comuneNascita.");
			pf.setSesso(dettaglio.getSesso());
			pf.setDtNascita(stringToSqlDate(dettaglio.getDataNascita()));
			pf.setIdNazioneNascita(stringToBigdecimal(dettaglio.getIdNazione()));
			if (Constants.ID_NAZIONE_ITALIA.equals(new BigDecimal(idNazione))) {
				pf.setIdComuneItalianoNascita(stringToBigdecimal(dettaglio.getIdComune()));
				pf.setIdComuneEsteroNascita(null);
			} else {
				pf.setIdComuneEsteroNascita(stringToBigdecimal(dettaglio.getIdComune()));
				pf.setIdComuneItalianoNascita(null);
			}
		} else {
			LOG.info("Campi da aggiornare: nome+cognome");
		}

		if (idPersonaFisica == null) {
			LOG.info("Eseguo inserimento.");
			pf.setIdUtenteIns(new BigDecimal(idUtente));
			pf.setDtInizioValidita(DateUtil.utilToSqlDate(oggi));
			pf = genericDAO.insert(pf);
			output = pf.getIdPersonaFisica().longValue();
			LOG.info("IdPersonaFisica nuovo record = " + output);
		} else {
			LOG.info("Eseguo modifica");
			pf.setIdUtenteAgg(new BigDecimal(idUtente));
			pf.setIdPersonaFisica(new BigDecimal(idPersonaFisica));
			genericDAO.updateNullables(pf);
			output = idPersonaFisica;
		}

		LOG.info(prf + " END");
		return output;
	}

	private java.lang.Long esisteGiaPersonaFisica(DettaglioSoggettoProgettoDTO dettaglio) throws java.lang.Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::esisteGiaPersonaFisica]";
		LOG.info(prf + " START");

		Long idPersonaFisica = null;
		try {
			String query = "SELECT ID_PERSONA_FISICA 					\r\n"
					+ "FROM PBANDI_T_PERSONA_FISICA 					\r\n"
					+ "WHERE ID_SOGGETTO = ? 							\r\n"
					+ "AND UPPER(COGNOME) = UPPER(?) 					\r\n"
					+ "AND NVL(UPPER(NOME),'0') = NVL(UPPER(?),'0') 	\r\n"
					+ "AND SESSO = ? 									\r\n"
					+ "AND DT_NASCITA = ?								\r\n"
					+ "AND ID_NAZIONE_NASCITA = ? 						\r\n"
					+ "AND DT_FINE_VALIDITA IS NULL						\r\n"
					+ "AND ROWNUM = 1 									\r\n";
			if (dettaglio.getIdNazione() == "118") {
				query += "AND ID_COMUNE_ITALIANO_NASCITA = ? 			\r\n";
			} else {
				query += "AND ID_COMUNE_ESTERO_NASCITA = ? 				\r\n";
			}

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			idPersonaFisica = ((LongValue) jdbcTemplate.queryForObject(query, new BeanRowMapper(LongValue.class),
					new Object[] { dettaglio.getIdSoggetto(), dettaglio.getCognome(), dettaglio.getNome(),
							dettaglio.getSesso(), dettaglio.getDataNascita(), dettaglio.getIdNazione(),
							dettaglio.getIdComune() }))
					.getValue();
		} catch (EmptyResultDataAccessException e) {
			LOG.info("Persona fisica non trovata");
			idPersonaFisica = null;
		}
		// return result;

		/*
		 * Long idNazione = NumberUtil.toNullableLong(dettaglio.getIdNazione()); Long
		 * idRuolo = new Long(dettaglio.getIdTipoSoggettoCorrelato()); boolean
		 * altriDatiAbilitati = this.altriDatiSoggettoProgettoAbilitati(idRuolo);
		 * 
		 * PbandiTPersonaFisicaVO pf = new PbandiTPersonaFisicaVO();
		 * pf.setNome(dettaglio.getNome()); pf.setCognome(dettaglio.getCognome());
		 * pf.setIdSoggetto(stringToBigdecimal(dettaglio.getIdSoggetto())); if
		 * (altriDatiAbilitati) { // Nota: l'analisi non prevede la ricerca per
		 * statoNascita.
		 * LOG.info("Cerco per idSoggetto+nome+cognome+sesso+dataNascita+comuneNascita."
		 * ); pf.setSesso(dettaglio.getSesso()); pf.setDtNascita(
		 * stringToSqlDate(dettaglio.getDataNascita()) );
		 * //pf.setIdNazioneNascita(stringToBigdecimal(dettaglio.getIdNazione())); if
		 * (Constants.ID_NAZIONE_ITALIA.equals(idNazione))
		 * pf.setIdComuneItalianoNascita(stringToBigdecimal(dettaglio.getIdComune()));
		 * else
		 * pf.setIdComuneEsteroNascita(stringToBigdecimal(dettaglio.getIdComune())); }
		 * else { LOG.info("Cerco per idSoggetto+nome+cognome"); }
		 * pf.setDescendentOrder("idPersonaFisica"); // il primo record trovato è quello
		 * con id max. List<PbandiTPersonaFisicaVO> listaPF =
		 * genericDAO.findListWhere(pf); LOG.info("Record trovati = "+listaPF.size());
		 * 
		 * Long idPersonaFisica = null; if (listaPF.size() > 0) { // Restituisco in
		 * output l'id del primo record trovato. if (listaPF.get(0) != null &&
		 * listaPF.get(0).getIdPersonaFisica() != null) idPersonaFisica =
		 * listaPF.get(0).getIdPersonaFisica().longValue();
		 * LOG.info("IdPersonaFisica selezionato = "+idPersonaFisica); } else { //
		 * Restituisco in output null. LOG.info("Nessun IdPersonaFisica selezionato.");
		 * }
		 */

		LOG.info(prf + " END");
		return idPersonaFisica;
	}

	private BigDecimal stringToBigdecimal(String value) {
		if (StringUtil.isEmpty(value))
			return null;
		Long l = new Long(value);
		return new BigDecimal(l);
	}

	private String bigdecimalToString(BigDecimal value) {
		if (value == null)
			return null;
		Long l = new Long(value.longValue());
		return l.toString();
	}

	private java.sql.Date stringToSqlDate(String d) throws Exception {
		if (StringUtil.isEmpty(d))
			return null;
		java.sql.Date sqlDate = null;
		try {
			java.util.Date utilDate = DateUtil.getDate(d);
			sqlDate = DateUtil.utilToSqlDate(utilDate);
		} catch (Exception e) {
			String msg = "stringToSqlDate(): ERRORE nella trasformazione della data: " + d;
			LOG.error(msg);
			throw new Exception(msg);
		}
		return sqlDate;
	}

	private boolean altriDatiSoggettoProgettoAbilitati(Long idRuolo) {
		String prf = "[GestioneDatiProgettoDAOImpl::altriDatiSoggettoProgettoAbilitati]";
		LOG.info(prf + " START");
		boolean esito = false;
		if (idRuolo != null) {
			esito = ((idRuolo.equals(Constants.ID_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE))
					|| (idRuolo.equals(Constants.ID_TIPO_SOGG_CORRELATO_DELEGATO_FIRMA)));
		}
		LOG.info(prf + " IdRuolo = " + idRuolo + " ==> altriDatiSoggettoProgettoAbilitati = " + esito);
		LOG.info(prf + " END");
		return esito;

	}

	@Override
	@Transactional
	public EsitoOperazioni saveTitoloProgetto(Long idUtente, String idIride, Long idProgetto, String titoloProgetto)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::saveTitoloProgetto]";
		LOG.info(prf + " BEGIN");
		String nameParameter[] = { "idUtente", "identitaDigitale", "idProgetto", "titoloProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, titoloProgetto);
		LOG.info("saveTitoloProgetto for idProgetto " + idProgetto + " and titoloProgetto: " + titoloProgetto);
		if (titoloProgetto.trim().length() < 3)
			throw new Exception("Il titolo del progetto non puo' essere inferiore a 3 caratteri");
		if (titoloProgetto.length() > 255) {
			throw new Exception("Il titolo del progetto non puo' essere superirore a 255 caratteri");
		}
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		pbandiTProgettoVO.setTitoloProgetto(titoloProgetto);
		try {
			genericDAO.update(pbandiTProgettoVO);

		} catch (Exception x) {
			throw new Exception("");
		}
		LOG.info(prf + " END");
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(Boolean.TRUE);
		return esito;

	}

	@Override
	@Transactional
	public DimensioneTerritorialeDTO[] findDimensioneTerritoriale(Long idUtente, String idIride, Long idProgetto)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::findDimensioneTerritoriale]";
		LOG.info(prf + " BEGIN");
		String nameParameter[] = { "idUtente", "identitaDigitale", "idProgetto" };

		try {
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

			LineaDiInterventoPadreVO lineaDiInterventoPadreVO = findLineaInterventoPadre(idProgetto);
			;

			DimensioneTerritorialeLineaInterventoVO filtroDimTer = new DimensioneTerritorialeLineaInterventoVO();
			filtroDimTer.setIdLineaDiIntervento(lineaDiInterventoPadreVO.getIdLineaDiIntervento());

			List<DimensioneTerritorialeLineaInterventoVO> listDimTer = genericDAO.findListWhere(filtroDimTer);
			LOG.info(prf + " END");
			return beanUtil.transform(listDimTer, DimensioneTerritorialeDTO.class);
		} catch (Exception x) {
			throw new Exception(x.getMessage());
		}

	}

	private LineaDiInterventoPadreVO findLineaInterventoPadre(Long idProgetto) {
		String prf = "[GestioneDatiProgettoDAOImpl::findLineaInterventoPadre]";
		LOG.info(prf + " START");
		BandoLineaProgettoVO bandoLinea = progettoManager.findBandoLineaForProgetto(idProgetto);
		LineaDiInterventoPadreVO lineaDiInterventoPadreVO = new LineaDiInterventoPadreVO();
		lineaDiInterventoPadreVO.setProgrBandoLineaIntervento(bandoLinea.getProgrBandoLineaIntervento());
		lineaDiInterventoPadreVO.setIdTipoLineaIntervento(new BigDecimal(1));
		// lineaDiInterventoPadreVO.setDescTipoLinea(descTipoLinea);
		List<LineaDiInterventoPadreVO> list = genericDAO.where(lineaDiInterventoPadreVO).select();
		if (list.size() != 0) {
			return list.get(0);
		}
		LOG.info(prf + " END");
		return null;
	}

	@Override
	@Transactional
	public DettaglioSoggettoBeneficiarioDTO findDettaglioSoggettoBeneficiario(Long idUtente, String idIride,
			Long progrSoggettoProgetto, Long idSoggettoBeneficiario) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::findDettaglioSoggettoBeneficiario]";
		LOG.info(prf + " BEGIN");

		String nameParameter[] = { "idUtente", "idIride", "progrSoggettoProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, progrSoggettoProgetto);

		DettaglioSoggettoBeneficiarioDTO dettaglio = new DettaglioSoggettoBeneficiarioDTO();

		try {
			// Cerca PBANDI_R_SOGGETTO_PROGETTO.
			PbandiRSoggettoProgettoVO sp = new PbandiRSoggettoProgettoVO();
			sp.setProgrSoggettoProgetto(new BigDecimal(progrSoggettoProgetto));
			sp = genericDAO.findSingleWhere(sp);

			Long idProgetto = NumberUtil.toLong(sp.getIdProgetto());

			PbandiTEnteGiuridicoVO enteGiuridico = new PbandiTEnteGiuridicoVO(sp.getIdEnteGiuridico());
			enteGiuridico = genericDAO.findSingleWhere(enteGiuridico);

			dettaglio.setIdEnteGiuridico(NumberUtil.toLong(enteGiuridico.getIdEnteGiuridico()));
			dettaglio.setDescrizioneBeneficiario(enteGiuridico.getDenominazioneEnteGiuridico());
			dettaglio.setFlagPubblicoPrivato(NumberUtil.toLong(enteGiuridico.getFlagPubblicoPrivato()));
			dettaglio.setCodUniIpa(enteGiuridico.getCodUniIpa());
			dettaglio.setIdProgetto(idProgetto);

			// Cerca PBANDI_T_SOGGETTO per CODICE FISCALE
			LOG.info("Cerco soggetto " + sp.getIdSoggetto());
			PbandiTSoggettoVO soggetto = new PbandiTSoggettoVO();
			soggetto.setIdSoggetto(sp.getIdSoggetto());
			soggetto = genericDAO.findSingleWhere(soggetto);

			dettaglio.setCodiceFiscale(soggetto.getCodiceFiscaleSoggetto());

			// Cerca SEDI
			try {
				SedeProgettoVO sedeLegale = sedeManager.findSedeLegale(idProgetto, idSoggettoBeneficiario);
				dettaglio.setTelefono(sedeLegale.getTelefono());
				dettaglio.setFax(sedeLegale.getFax());
				dettaglio.setEmail(sedeLegale.getEmail());
				dettaglio.setIdRecapiti(sedeLegale.getIdRecapiti().longValue());
				if (sedeLegale != null)
					dettaglio.setSedeLegale(sedeLegale.toDescBreveSede());
				dettaglio
						.setSediIntervento(beanUtil.transform(sedeManager.findSediIntervento(new BigDecimal(idProgetto),
								new BigDecimal(idSoggettoBeneficiario)), SedeProgettoDTO.class));
			} catch (Exception e) {
				LOG.error("Errore durante la ricerca della sede. Motivo: ", e);
				throw new Exception("Errore durante la ricerca della sede. Motivo: ", e);
			}
		} catch (Exception ex) {
			LOG.error("Errore durante la chiamata del metodo. motivo: ", ex);
			throw new Exception("Errore durante la chiamata del metodo. motivo: ", ex);
		}
		LOG.info(prf + " END");
		return dettaglio;
	}

	@Override
	@Transactional
	public EsitoOperazioni salvaDettaglioSoggettoBeneficiario(Long idUtente, String idIride,
			DettaglioSoggettoBeneficiarioDTO dettaglioSoggettoBeneficiario) throws java.lang.Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::salvaDettaglioSoggettoBeneficiario]";
		LOG.info(prf + " BEGIN");

		String nameParameter[] = { "idUtente", "identitaDigitale", "dettaglioSoggettoBeneficiario" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, dettaglioSoggettoBeneficiario);

		int result = -1;

		try {

			PbandiTEnteGiuridicoVO enteGiuridico = beanUtil.transform(dettaglioSoggettoBeneficiario,
					PbandiTEnteGiuridicoVO.class);
			genericDAO.update(enteGiuridico);

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String UPDATE_RECAPITO = " UPDATE PBANDI_T_RECAPITI SET FAX = ?, TELEFONO = ? WHERE ID_RECAPITI = ? ";
			result = jdbcTemplate.update(UPDATE_RECAPITO, new Object[] { dettaglioSoggettoBeneficiario.getFax(),
					dettaglioSoggettoBeneficiario.getTelefono(), dettaglioSoggettoBeneficiario.getIdRecapiti() });

		} catch (Exception ex) {
			throw new Exception("Errore durante il salvataggio dei dettagli del soggetto. motivo: ", ex);
		}
		EsitoOperazioni esito = new EsitoOperazioni();
		if (result == 1) {
			esito.setEsito(true);
			esito.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} else {
			esito.setEsito(false);
			esito.setMsg(MessageConstants.ERRORE_SALVAGGIO_RECAPITO);
		}

		return esito;
	}

	public Long codiceFiscaleAlredyExsist(String cf) {

		String query = "SELECT  ptu.id_utente as value		\r\n" + "FROM pbandi_t_utente ptu				\r\n"
				+ "WHERE ptu.codice_utente = ?				";

		// pbandiTProgettoVO = jdbcTemplate.queryForObject(query, new
		// ProgettoRegioneRowMapper(), new Object[] { idProgetto});
		// idBando = ((LongValue)getJdbcTemplate().queryForObject(sql, par, new
		// BeanRowMapper(LongValue.class))).getValue();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Long result = ((LongValue) jdbcTemplate.queryForObject(query, new BeanRowMapper(LongValue.class),
				new Object[] { cf })).getValue();

		return result;
	}

	@Override
	public List<PbandiTUtenteVO> findUtenteVOFromCodiceUtente(String codiceFiscale) throws Exception {

		EsitoOperazioni esitoOperazioni = new EsitoOperazioni();

		PbandiTUtenteVO pbandiTUtenteVO = new PbandiTUtenteVO();
		pbandiTUtenteVO.setCodiceUtente(codiceFiscale);
		List<PbandiTUtenteVO> listPbandiTUtenteVO;
		listPbandiTUtenteVO = genericDAO.findListWhere(pbandiTUtenteVO);

//		if(listPbandiTUtenteVO == null || listPbandiTUtenteVO.size() == 0) {
//			esitoOperazioni.setEsito(false);
//			esitoOperazioni.setMsg("L'utente non ha l'accesso al sistema");
//		}else {
//			esitoOperazioni.setEsito(true);
//			esitoOperazioni.setMsg("L'utente ha l'accesso al sistema");
//		}

		return listPbandiTUtenteVO;

	}

	private class pbandiTRichiestaAbilitazVORowMapper implements RowMapper<PbandiTRichiestaAbilitazVO> {

		public PbandiTRichiestaAbilitazVO mapRow(ResultSet rs, int rowNum) throws SQLException {

			PbandiTRichiestaAbilitazVO obj = new PbandiTRichiestaAbilitazVO();
			obj.setProgrSoggettoProgetto(rs.getBigDecimal("PROGR_SOGGETTO_PROGETTO"));
			obj.setProgrSoggettiCorrelati(rs.getBigDecimal("PROGR_SOGGETTI_CORRELATI"));
			obj.setDtRichiesta(rs.getDate("DT_RICHIESTA"));
			obj.setEsito(rs.getString("ESITO"));
			obj.setDtEsito(rs.getDate("DT_ESITO"));
			obj.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
			obj.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
			obj.setAccessoSistema(rs.getString("ACCESSO_SISTEMA"));

			return obj;

		}
	}

	@Override
	public List<PbandiTRichiestaAbilitazVO> findRichiestaAbilitazVO() throws Exception {

		String ESITO_NULL = "SELECT * FROM PBANDI_T_RICHIESTA_ABILITAZ WHERE ESITO IS NULL";
		PbandiTRichiestaAbilitazVO pbandiTRichiestaAbilitazVO = new PbandiTRichiestaAbilitazVO();
		pbandiTRichiestaAbilitazVO.setEsito(null);
		pbandiTRichiestaAbilitazVO.setDtEsito(null);

		List<PbandiTRichiestaAbilitazVO> listPbandiTRichiestaAbilitazVO;
//		listPbandiTRichiestaAbilitazVO = genericDAO.findListWhere(pbandiTRichiestaAbilitazVO);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		listPbandiTRichiestaAbilitazVO = jdbcTemplate.query(ESITO_NULL, new pbandiTRichiestaAbilitazVORowMapper(),
				new Object[] {});

		return listPbandiTRichiestaAbilitazVO;

	}

	@Override
	public PbandiTRichiestaAbilitazVO findRichiestaAbilitazVO(
			RequestCambiaStatoSoggettoProgetto requestCambiaStatoSoggettoProgetto) throws Exception {

		PbandiTRichiestaAbilitazVO pbandiTRichiestaAbilitazVO = new PbandiTRichiestaAbilitazVO();
		pbandiTRichiestaAbilitazVO.setEsito(null);
		pbandiTRichiestaAbilitazVO.setProgrSoggettoProgetto(
				new BigDecimal(requestCambiaStatoSoggettoProgetto.getProgrSoggettoProgetto()));
		pbandiTRichiestaAbilitazVO.setProgrSoggettiCorrelati(
				new BigDecimal(requestCambiaStatoSoggettoProgetto.getProgrSoggettiCorrelati()));

		pbandiTRichiestaAbilitazVO = genericDAO.findSingleOrNoneWhere(pbandiTRichiestaAbilitazVO);

		return pbandiTRichiestaAbilitazVO;

	}

	private static final String UPDATE_RICHIESTA_ABILITAZIONE = ""
			+ "UPDATE PBANDI_T_RICHIESTA_ABILITAZ SET ESITO=?, DT_ESITO=SYSDATE, ID_UTENTE_AGG = ? WHERE PROGR_SOGGETTO_PROGETTO=? AND PROGR_SOGGETTI_CORRELATI=? ";

	@Override
	public int updateRichiestaAbilitazione(Long idU, RequestCambiaStatoSoggettoProgetto cambiaStato, String esito) {

		String prf = "[GestioneDatiProgettoDAOImpl::insertRichiestaAbilitazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> ProgrSoggettoProgetto = " + cambiaStato.getProgrSoggettoProgetto()
				+ ", ProgrSoggettiCorrelati = " + cambiaStato.getProgrSoggettiCorrelati());
		int result = -1;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.update(UPDATE_RICHIESTA_ABILITAZIONE, new Object[] { esito, idU,
				cambiaStato.getProgrSoggettoProgetto(), cambiaStato.getProgrSoggettiCorrelati() });

		LOG.debug(prf + " END");

		return result;

	}

	@Override
	public void insertPbandiTUtenteVO(Long idUtente, RequestCambiaStatoSoggettoProgetto cambiaStatoRequest) {

		LOG.info("Sto inserendo un Rappresentante Legale: inserisco un record su PBANDI_T_UTENTE");
		PbandiTUtenteVO utente = new PbandiTUtenteVO();
		utente.setCodiceUtente(cambiaStatoRequest.getCodiceFiscale());
		utente.setIdTipoAccesso(new BigDecimal(4));
		utente.setIdSoggetto(new BigDecimal(cambiaStatoRequest.getIdSoggetto()));
		utente.setDtInserimento(DateUtil.utilToSqlDate(DateUtil.getSysdateWithoutSeconds()));
		try {
			genericDAO.insert(utente);
			if (!esisteGiaSoggTipoAnag(String.valueOf(cambiaStatoRequest.getIdSoggetto()))) {
				// Nota: insert aggiunta da Tinti il 20/1/2017
				// test esisteGiaSoggTipoAnag() aggiunto da Alex il 11/4/2017 su richiesta di
				// Panarace\Pacilio.
				LOG.info("\n\n\n\n\n\n++++++++++++++ inserisco record nella PbandiRSoggTipoAnagraficaVO ");
				PbandiRSoggTipoAnagraficaVO pbandiRSoggTipoAnagraficaVO = new PbandiRSoggTipoAnagraficaVO();
				pbandiRSoggTipoAnagraficaVO.setIdSoggetto(new BigDecimal(cambiaStatoRequest.getIdSoggetto()));
				pbandiRSoggTipoAnagraficaVO
						.setIdTipoAnagrafica(new BigDecimal(Constants.ID_TIPO_ANAGRAFICA_PERSONA_FISICA));
				pbandiRSoggTipoAnagraficaVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
				pbandiRSoggTipoAnagraficaVO.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
				genericDAO.insert(pbandiRSoggTipoAnagraficaVO);
				LOG.info("PbandiRSoggTipoAnagraficaVO inserito\n\n\n\n\n\n\n");
			}
		} catch (Exception e) {
			LOG.error(e);
		}

	}

	@Override
	public int updateDataCessazione(Long idSoggetto, Long idProgetto) throws Exception {

		String prf = "[GestioneDatiProgettoDAOImpl::updateDataCessazione]";
		LOG.debug(prf + " BEGIN");

		final String UPDATE_DATA_CESSAZIONE = "UPDATE PBANDI_R_SOGGETTO_PROGETTO SET DATA_CESSAZIONE = SYSDATE WHERE ID_SOGGETTO = ? AND ID_PROGETTO = ?";

		int result = -1;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.update(UPDATE_DATA_CESSAZIONE, new Object[] { idSoggetto, idProgetto });

		LOG.debug(prf + " END");

		return result;

	}

	private static final String PROGETTO_IS_FINPIEMONTE = ""
			+ "SELECT p.ID_PROGETTO, p.ID_PROGETTO_PADRE, p.TITOLO_PROGETTO, p.ID_DOMANDA "
			+ "FROM PBANDI_T_PROGETTO p, PBANDI_T_DOMANDA d, PBANDI_R_BANDO_LINEA_ENTE_COMP blec\r\n"
			+ "WHERE p.id_domanda = d.id_domanda\r\n"
			+ "AND d.progr_bando_linea_intervento = blec.progr_bando_linea_intervento\r\n"
			+ "AND blec.id_ruolo_ente_competenza = 1 --destinatario delle comunicazione\r\n"
			+ "AND blec.id_ente_competenza != 2 --NON deve essere Finpiemonte\r\n" + "AND p.id_progetto = ?";

	private class ProgettoRegioneRowMapper implements RowMapper<PbandiTProgettoVO> {

		public PbandiTProgettoVO mapRow(ResultSet rs, int rowNum) throws SQLException {

			PbandiTProgettoVO obj = new PbandiTProgettoVO();
			obj.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
			obj.setIdProgettoPadre(rs.getBigDecimal("ID_PROGETTO_PADRE"));
			obj.setTitoloProgetto(rs.getString("TITOLO_PROGETTO"));
			obj.setIdDomanda(rs.getBigDecimal("ID_DOMANDA"));
			return obj;

		}
	}

	@Override
	public PbandiTProgettoVO getProgettoRegione(Long idProgetto) {

		String prf = "[GestioneDatiProgettoDAOImpl::insertRichiestaAbilitazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idProgetto = " + idProgetto);
		PbandiTProgettoVO pbandiTProgettoVO = null;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			pbandiTProgettoVO = jdbcTemplate.queryForObject(PROGETTO_IS_FINPIEMONTE, new ProgettoRegioneRowMapper(),
					new Object[] { idProgetto });
		} catch (Exception e) {
			pbandiTProgettoVO = null;
		}

		LOG.debug(prf + " END");

		return pbandiTProgettoVO;

	}

	@Override
	public List<PbandiTUtenteVO> findPbandiTUtenteVO(String codiceUtente) throws Exception {

		PbandiTUtenteVO pbandiTUtenteVO = new PbandiTUtenteVO();
		List<PbandiTUtenteVO> listPbandiTUtenteVO = null;
		try {

			pbandiTUtenteVO.setCodiceUtente(codiceUtente);

			listPbandiTUtenteVO = genericDAO.findListWhere(pbandiTUtenteVO);
		} catch (Exception e) {
			listPbandiTUtenteVO = null;
		}

		return listPbandiTUtenteVO;

	}

	@Override
	public List<PbandiTRichiestaAbilitazVO> findCheckAccessoSistema(Long progrSoggettoProgetto,
			Long progrSoggettiCorrelati) throws Exception {

		String prf = "[GestioneDatiProgettoDAOImpl::findCheckAccessoSistema]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> progrSoggettoProgetto = " + progrSoggettoProgetto
				+ ", progrSoggettiCorrelati = " + progrSoggettiCorrelati);

		String CHECK_ACCSSO_SISTEMA = "SELECT * FROM PBANDI_T_RICHIESTA_ABILITAZ WHERE ESITO IS NULL AND PROGR_SOGGETTO_PROGETTO = ? AND PROGR_SOGGETTI_CORRELATI = ?";
		LOG.debug(prf + "query = " + CHECK_ACCSSO_SISTEMA);

		PbandiTRichiestaAbilitazVO pbandiTRichiestaAbilitazVO = new PbandiTRichiestaAbilitazVO();
		pbandiTRichiestaAbilitazVO.setEsito(null);
		pbandiTRichiestaAbilitazVO.setDtEsito(null);

		List<PbandiTRichiestaAbilitazVO> listPbandiTRichiestaAbilitazVO;
//		listPbandiTRichiestaAbilitazVO = genericDAO.findListWhere(pbandiTRichiestaAbilitazVO);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		listPbandiTRichiestaAbilitazVO = jdbcTemplate.query(CHECK_ACCSSO_SISTEMA,
				new pbandiTRichiestaAbilitazVORowMapper(),
				new Object[] { progrSoggettoProgetto, progrSoggettiCorrelati });

		LOG.debug(prf + " END");
		return listPbandiTRichiestaAbilitazVO;

	}

	@Override
	public List<PbandiTRichiestaAbilitazVO> findRichiestaAccessoRifiutata() throws Exception {

		String prf = "[GestioneDatiProgettoDAOImpl::findRichiestaAccessoRifiutata]";
		LOG.debug(prf + " BEGIN");

		String RICHIESTA_RIFIUTATA = "SELECT * FROM PBANDI_T_RICHIESTA_ABILITAZ WHERE ESITO = 'N'";
		LOG.debug(prf + "query = " + RICHIESTA_RIFIUTATA);

		PbandiTRichiestaAbilitazVO pbandiTRichiestaAbilitazVO = new PbandiTRichiestaAbilitazVO();
		pbandiTRichiestaAbilitazVO.setEsito(null);
		pbandiTRichiestaAbilitazVO.setDtEsito(null);

		List<PbandiTRichiestaAbilitazVO> listPbandiTRichiestaAbilitazVO;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		listPbandiTRichiestaAbilitazVO = jdbcTemplate.query(RICHIESTA_RIFIUTATA,
				new pbandiTRichiestaAbilitazVORowMapper(), new Object[] {});

		LOG.debug(prf + " END");
		return listPbandiTRichiestaAbilitazVO;

	}

	@Override
	public List<PbandiRSoggettoProgettoVO> findListRSoggettoProgetto(BigDecimal idSoggetto, Long idProgetto)
			throws Exception {

		String prf = "[GestioneDatiProgettoDAOImpl::findListRSoggettoProgetto]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idProgetto = " + idProgetto + ", idSoggetto = " + idSoggetto);

		// Cerca PBANDI_R_SOGGETTO_PROGETTO.
		PbandiRSoggettoProgettoVO pbandiRSoggettoProgettoVO = new PbandiRSoggettoProgettoVO();
		pbandiRSoggettoProgettoVO.setIdSoggetto(idSoggetto);
		pbandiRSoggettoProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		List<PbandiRSoggettoProgettoVO> listPbandiRSoggettoProgettoVO = new ArrayList<PbandiRSoggettoProgettoVO>();
		listPbandiRSoggettoProgettoVO = genericDAO.findListWhere(pbandiRSoggettoProgettoVO);

		LOG.debug(prf + " END");
		return listPbandiRSoggettoProgettoVO;
	}

	@Override
	public EmailBeneficiarioPF leggiEmailBeneficiarioPF(Long idProgetto, Long idSoggettoBen) throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::leggiEmailBeneficiarioPF] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idSoggettoBen = " + idSoggettoBen);
		try {
			String nameParameter[] = { "idSoggettoBen", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idSoggettoBen, idProgetto);

			it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO rsp = new it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO();
			rsp.setIdProgetto(new BigDecimal(idProgetto));
			rsp.setIdSoggetto(new BigDecimal(idSoggettoBen));
			rsp.setIdTipoAnagrafica(new BigDecimal(1));
			List<it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO> rsps = genericDAO.findListWhere(rsp);
			if (rsps != null) {
				for (it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO vo : rsps) {
					if (!vo.getIdTipoBeneficiario().equals(new BigDecimal(4))) {
						rsp = vo;
					}
				}
			}

			String email = null;
			if (rsp.getIdRecapitiPersonaFisica() == null) {
				// nessuna mail.
			} else {
				PbandiTRecapitiVO recapito = new PbandiTRecapitiVO();
				recapito.setIdRecapiti(rsp.getIdRecapitiPersonaFisica());
				recapito = genericDAO.findSingleWhere(recapito);
				email = recapito.getEmail();
			}

			EmailBeneficiarioPF response = new EmailBeneficiarioPF();
			response.setEmail(email);
			response.setFlagEmailConfermata(rsp.getFlagEmailConfermata());

			LOG.info(prf + " END");
			return response;
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EsitoOperazioni salvaEmailBeneficiarioPF(Long idProgetto, Long idSoggettoBen, String email, Long idUtente)
			throws Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::salvaEmailBeneficiarioPF] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idSoggettoBen = " + idSoggettoBen + "; email = " + email
				+ "; idUtente = " + idUtente);
		try {
			String nameParameter[] = { "idSoggettoBen", "idProgetto", "idUtente" };
			ValidatorInput.verifyNullValue(nameParameter, idSoggettoBen, idProgetto, idUtente);

			if (email != null && email.length() == 0)
				email = null;

			it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO rsp = new it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO();
			rsp.setIdProgetto(new BigDecimal(idProgetto));
			rsp.setIdSoggetto(new BigDecimal(idSoggettoBen));
			rsp.setIdTipoAnagrafica(new BigDecimal(1));
			List<it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO> rsps = genericDAO.findListWhere(rsp);
			if (rsps != null) {
				for (it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO vo : rsps) {
					if (!vo.getIdTipoBeneficiario().equals(new BigDecimal(4))) {
						rsp = vo;
					}
				}
			}

			if (rsp.getIdRecapitiPersonaFisica() == null) {

				// Creo un nuovo recapito.
				PbandiTRecapitiVO recIns = new PbandiTRecapitiVO();
				recIns.setEmail(email);
				recIns.setIdUtenteIns(new BigDecimal(idUtente));
				recIns.setDtInizioValidita(DateUtil.getSysdate());
				recIns = genericDAO.insert(recIns);

				if (recIns.getIdRecapiti() == null)
					throw new Exception("Errore nell'inserimento del racapito.");

				// Assegno il nuovo recapito alla PBANDI_R_SOGGETTO_PROGETTO.
				it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO rspUpd = new it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO();
				rspUpd.setProgrSoggettoProgetto(rsp.getProgrSoggettoProgetto());
				rspUpd.setIdRecapitiPersonaFisica(recIns.getIdRecapiti());
				rspUpd.setFlagEmailConfermata("S");
				rspUpd.setDtAggiornamento(DateUtil.getSysdate());
				rspUpd.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(rspUpd);

			} else {

				// Cerco il recapito già esistente.
				it.csi.pbandi.pbweberog.integration.vo.PbandiTRecapitiVO recapito = new it.csi.pbandi.pbweberog.integration.vo.PbandiTRecapitiVO();
				recapito.setIdRecapiti(rsp.getIdRecapitiPersonaFisica());
				recapito = genericDAO.findSingleWhere(recapito);

				// Creo un nuovo recapito.
				it.csi.pbandi.pbweberog.integration.vo.PbandiTRecapitiVO recIns = new it.csi.pbandi.pbweberog.integration.vo.PbandiTRecapitiVO();
				recIns.setFax(recapito.getFax());
				recIns.setTelefono(recapito.getTelefono());
				recIns.setPec(recapito.getPec());
				recIns.setEmail(email);
				recIns.setIdUtenteIns(new BigDecimal(idUtente));
				recIns.setDtInizioValidita(DateUtil.getSysdate());
				recIns = genericDAO.insert(recIns);

				if (recIns.getIdRecapiti() == null)
					throw new Exception("Errore nell'inserimento del racapito.");

				// Assegno il nuovo recapito alla PBANDI_R_SOGGETTO_PROGETTO.
				it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO rspUpd = new it.csi.pbandi.pbweberog.integration.vo.PbandiRSoggettoProgettoVO();
				rspUpd.setProgrSoggettoProgetto(rsp.getProgrSoggettoProgetto());
				rspUpd.setIdRecapitiPersonaFisica(recIns.getIdRecapiti());
				rspUpd.setFlagEmailConfermata("S");
				rspUpd.setDtAggiornamento(DateUtil.getSysdate());
				rspUpd.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(rspUpd);

			}

			EsitoOperazioni esito = new EsitoOperazioni();
			esito.setEsito(true);
			esito.setMsg("Salvataggio avvenuto con successo.");

			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	// Associa gli allegati ad un fornitore.
	public EsitoAssociaFilesDTO associaAllegatiAProgetto(AssociaFilesRequest associaFilesRequest, Long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::associaAllegatiAProgetto] ";
		LOG.info(prf + "BEGIN");

		EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
		try {

			String sql = "SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_PROGETTO'";
			LOG.info("\n" + sql);
			Long idEntita = (Long) getJdbcTemplate().queryForObject(sql, Long.class);

			// IdProgetto è obbligatorio in associaFiles e in questo specifico caso coincide
			// con idTarget.
			associaFilesRequest.setIdProgetto(associaFilesRequest.getIdTarget());

			esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita, idUtente);
			LOG.info(prf + esito.toString());

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel associare allegati al progetto: ", e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public EsitoOperazioni disassociaAllegatoProgetto(Long idDocumentoIndex, Long idProgetto, Long idUtente,
			String idIride) throws InvalidParameterException, Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::disassociaAllegatoProgetto] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idProgetto = " + idProgetto + "; idUtente = "
				+ idUtente);

		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoOperazioni esito = new EsitoOperazioni();
		try {

			String sql = "SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_PROGETTO'";
			LOG.info("\n" + sql);
			Long idEntita = (Long) getJdbcTemplate().queryForObject(sql, Long.class);

			Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex, idEntita,
					idProgetto, idProgetto);
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMsg(esitoPbandisrv.getMessage());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel disassociare gli allegati del fornitore: ", e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public DatiAggiuntiviDTO getDatiAggiuntiviByIdProgetto(Long idProgetto, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[GestioneDatiProgettoDAOImpl::getDatiAggiuntiviByIdProgetto] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idIride = " + idIride);
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		DatiAggiuntiviDTO datiAggiuntiviDTO = null;
		try {

			String sql = "SELECT pwar.NUMERO_DOMANDA, pwar.NOME_REFERENTE_TECNICO , pwar.COGNOME_REFERENTE_TECNICO, pwar.CF_REFERENTE_TECNICO,\r\n"
					+ "pwar.TELEFONO_REFERENTE_TECNICO, pwar.EMAIL_REFERENTE_TECNICO, pwar.PEC_REFERENTE_TECNICO, pwar.DATA_ULTIMA_TRASMISSIONE, \r\n"
					+ "pwar.DESCRIZIONE_INTERVENTO, pwar.LIVELLO_PROGETTUALE, pwar.DICH_TIPOLOGIA_PROGETTO, pwar.INDIVIDUAZIONE_AMBITO, \r\n"
					+ "pwar.NUMERO_BENI_OGG_INTERVENTO, pwar.DESCRIZIONE_OGGETTI_INTERVENTO, pwar.TIPOLOGIA_BENE, pwar.TITOLO_DISPONIBILITA_BENE, \r\n"
					+ "pwar.TIPOLOGIA_VINCOLO_ISTRUTTORIA, pwar.DECRETO_SOPRINTENDENZA, pwar.COFINANZIAMENTO, pwar.TIPOLOGIA_INTERVENTO, \r\n"
					+ "pwar.IVA_DETRAIBILE_INDETRAIBILE, pwar.ANNO_2023 AS anno2023, pwar.IMPEGNO_2023_N AS impegno2023_N, pwar.ANNO_2024 AS anno2024, \r\n"
					+ "pwar.IMPEGNO_2024_N AS impegno2024_N, pwar.ANNO_2025 AS anno2025, \r\n"
					+ "pwar.IMPEGNO_2025_N AS impegno2025_N, pwar.MOD_EROG_SOST_FIN, pwar.PROVINCIA, pwar.COMUNE_CATASTO, pwar.CATASTO_CODICE_COMUNE, \r\n"
					+ "pwar.FOGLIO_CATASTO, pwar.PARTICELLE_CATASTO, pwar.SUB_PARTICELLA_CATASTO, pwar.REA, pwar.CODICE_ATECO, pwar.CONTABILIA_COD_BEN, \r\n"
					+ "pwar.CODICE_COR, pwar.MOD_AIUTO_STATO_CON, pwar.PRESCRIZIONI_DE_MINIMIS, pwar.DD_ASSEGNAZIONE_N_DEL, \r\n"
					+ "pwar.ATTO_OBBLIGO_DATA_PROTOCOLLO, pwar.ATTO_OBBLIGO_N_PROTOCOLLO\r\n"
					+ "FROM PBANDI_W_ARCHITETTURE_RURALI pwar\r\n"
					+ "JOIN PBANDI_T_DOMANDA ptd ON ptd.NUMERO_DOMANDA = pwar.ID_DOMANDA_REV \r\n"
					+ "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
					+ "WHERE ptp.ID_PROGETTO = ?";
			LOG.info("\n" + sql);
			List<DatiAggiuntiviDTO> datiAggiuntiviDTOs = getJdbcTemplate().query(sql, new Object[] { idProgetto },
					new BeanRowMapper(DatiAggiuntiviDTO.class));

			if (datiAggiuntiviDTOs != null && datiAggiuntiviDTOs.size() > 1) {
				throw new ErroreGestitoException(
						"Risultato atteso 1 o nessuno, risultati ottenuti: " + datiAggiuntiviDTOs.size());
			} else {
				LOG.info(prf + " Record trovati: " + datiAggiuntiviDTOs == null ? 0 : datiAggiuntiviDTOs.size());
			}
			datiAggiuntiviDTO = datiAggiuntiviDTOs == null || datiAggiuntiviDTOs.size() == 0 ? new DatiAggiuntiviDTO()
					: datiAggiuntiviDTOs.get(0);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel caricamento dei dati aggiuntivi: ", e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return datiAggiuntiviDTO;
	}

}
