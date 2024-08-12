package it.csi.pbandi.pbweb.pbandisrv.business.gestioneappalti;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.EsitoGestioneAppalti;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.EsitoReportAppalti;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.FornitoreAppaltoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoReportDettaglioDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestioneappalti.GestioneAppaltiException;
import it.csi.pbandi.pbweb.pbandisrv.exception.validazionerendicontazione.ValidazioneRendicontazioneException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AppaltoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FornitoreAppaltoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ReportDocumentiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFornitoreAppaltoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRProgettiAppaltiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRibassoAstaVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestioneappalti.GestioneAppaltiSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;

public class GestioneAppaltiBusinessImpl extends BusinessImpl implements
		GestioneAppaltiSrv {

	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	private static final BidiMap MAP_FROM_APPALTOVO_TO_APPALTODTO = new TreeBidiMap();
	private static final BidiMap MAP_FROM_APPALTODTO_TO_APPALTOVO;
	static {
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idAppalto", "idAppalto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtInizioPrevista",
				"dtInizioPrevista");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtConsegnaLavori",
				"dtConsegnaLavori");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtFirmaContratto",
				"dtFirmaContratto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("importoContratto",
				"importoContratto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("bilancioPreventivo",
				"bilancioPreventivo");
		MAP_FROM_APPALTOVO_TO_APPALTODTO
				.put("oggettoAppalto", "oggettoAppalto");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idProceduraAggiudicaz",
				"idProceduraAggiudicaz");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("codVisualizzatoProcAgg",
				"descProcAgg");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("descProcAgg",
				"descrizioneProcAgg");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtGUUE", "dtGuue");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtGURI", "dtGuri");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtQuotNazionali",
				"dtQuotNazionali");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtWebStazAppaltante",
				"dtWebStazAppaltante");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("dtWebOsservatorio",
				"dtWebOsservatorio");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idProgetto", "idProgetto");

		// }L{ PBANDI-1884
		MAP_FROM_APPALTOVO_TO_APPALTODTO
				.put("interventoPisu", "interventoPisu");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("impresaAppaltatrice",
				"impresaAppaltatrice");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("idTipologiaAppalto",
				"idTipologiaAppalto");

		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("importoRibassoAsta",
				"importoRibassoAsta");
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("percentualeRibassoAsta",
				"percentualeRibassoAsta");
		
		MAP_FROM_APPALTOVO_TO_APPALTODTO.put("sopraSoglia",
		"sopraSoglia");

		MAP_FROM_APPALTODTO_TO_APPALTOVO = MAP_FROM_APPALTOVO_TO_APPALTODTO
				.inverseBidiMap();
	}

	public AppaltoDTO[] findAppalti(Long idUtente, String identitaIride,
			AppaltoDTO filtro) throws CSIException, SystemException,
			UnrecoverableException, GestioneAppaltiException {
		AppaltoDTO[] appalti = null;
		String[] nameParameter = { "idUtente", "identitaIride", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride,
				filtro);
		AppaltoVO query = beanUtil.transform(filtro, AppaltoVO.class,
				MAP_FROM_APPALTODTO_TO_APPALTOVO);
		appalti = beanUtil.transform(genericDAO
				.where(Condition.filterBy(query)).select(), AppaltoDTO.class,
				MAP_FROM_APPALTOVO_TO_APPALTODTO);
		return appalti;
	}

	public AppaltoDTO findDettaglioAppalto(Long idUtente, String identitaIride,
			Long idAppalto) throws CSIException, SystemException,
			UnrecoverableException, GestioneAppaltiException {
		AppaltoDTO appalto = null;

		String[] nameParameter = { "idUtente", "identitaIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride,
				idAppalto);
		AppaltoVO query = new AppaltoVO();
		query.setIdAppalto(beanUtil.transform(idAppalto, BigDecimal.class));
		appalto = beanUtil.transform(genericDAO
				.where(Condition.filterBy(query)).selectSingle(),
				AppaltoDTO.class, MAP_FROM_APPALTOVO_TO_APPALTODTO);

		return appalto;
	}

	public EsitoGestioneAppalti creaAppalto(Long idUtente,
			String identitaIride, AppaltoDTO appalto) throws CSIException,
			SystemException, UnrecoverableException, GestioneAppaltiException {
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		try {
			String[] nameParameter = { "idUtente", "identitaIride", "appalto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaIride, appalto);
			esito.setAppalto(insertUpdateAppalto(
					beanUtil.transform(appalto, PbandiTAppaltoVO.class),
					idUtente));
			esito.setEsito(aggiornaRibassoAstaAppalto(idUtente, esito.getAppalto()
					.getIdProceduraAggiudicaz(), appalto
					.getImportoRibassoAsta(), appalto
					.getPercentualeRibassoAsta()));
			if(!esito.getEsito()) {
				logger.warn("Appalto non creato: errore in aggiornaRibassoAstaAppalto");
				esito.setMessage(ERRORE_SERVER);
				esito.setAppalto(appalto);
			}
		} catch (FormalParameterException e) {
			logger.warn("Appalto non creato: " + e.getMessage());
			esito.setEsito(false);
			esito.setMessage(ERRORE_CAMPO_OBBLIGATORIO);
			esito.setAppalto(appalto);
		} catch (GestioneAppaltiException e) {
			logger.warn("Appalto non creato: errore in insertUpdateAppalto");
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
			esito.setAppalto(appalto);
		}
		return esito;
	}

	private Boolean aggiornaRibassoAstaAppalto(Long idUtente, Long idProceduraAggiudicazione,
			Double importoRibassoAsta, Double percentualeRibassoAsta) {
		PbandiTRibassoAstaVO vo = new PbandiTRibassoAstaVO();
		vo.setIdProceduraAggiudicaz(new BigDecimal(idProceduraAggiudicazione));
		vo = genericDAO.findSingleOrNoneWhere(vo);
		if (vo == null) {
			vo = new PbandiTRibassoAstaVO();
			vo.setIdProceduraAggiudicaz(new BigDecimal(idProceduraAggiudicazione));
		}
		vo.setPercentuale(percentualeRibassoAsta != null && NumberUtil.compare(percentualeRibassoAsta, 0D) != 0 ? new BigDecimal(
				percentualeRibassoAsta) : null);
		vo.setImporto(importoRibassoAsta != null && NumberUtil.compare(importoRibassoAsta, 0D) != 0 ? new BigDecimal(
				importoRibassoAsta) : null);
		try {
			if (vo.getImporto() == null && vo.getPercentuale() == null) {
				if (vo.getIdRibassoAsta() != null) {
					return genericDAO.delete(vo);
				} else {
					return true;
				}
			} else {
				if (vo.getIdRibassoAsta() != null) {
					vo.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.updateNullables(vo);
				} else {
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					genericDAO.insert(vo);
				}
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	private AppaltoDTO insertUpdateAppalto(PbandiTAppaltoVO appaltoVO,
			Long idUtente) throws FormalParameterException,
			GestioneAppaltiException {
		String[] nameParameter = { "appalto.oggettoAppalto",
				"appalto.importoContratto", "appalto.dtFirmaContratto",
				"appalto.idProceduraAggiudicaz" };
		ValidatorInput.verifyNullValue(nameParameter,
				appaltoVO.getOggettoAppalto(), appaltoVO.getImportoContratto(),
				appaltoVO.getDtFirmaContratto(),
				appaltoVO.getIdProceduraAggiudicaz());
		try {
			appaltoVO.setIdUtenteIns(new BigDecimal(idUtente));
			appaltoVO = genericDAO.insertOrUpdateExisting(appaltoVO);
		} catch (Exception ex) {
			logger.error("Eccezione durante la insert/update di un appalto", ex);
			throw new GestioneAppaltiException(ERRORE_SERVER);
		}
		return beanUtil.transform(appaltoVO, AppaltoDTO.class,
				MAP_FROM_APPALTOVO_TO_APPALTODTO);
	}

	public EsitoGestioneAppalti modificaAppalto(Long idUtente,
			String identitaIride, AppaltoDTO appalto) throws CSIException,
			SystemException, UnrecoverableException, GestioneAppaltiException {
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		try {
			String[] nameParameter = { "idUtente", "identitaIride", "appalto",
					"appalto.idAppalto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaIride, appalto, appalto.getIdAppalto());
			Map<String, String> trsMap = new HashMap<String, String>();
			trsMap.put("idAppalto", "idAppalto");
			trsMap.put("idUtenteIns", "idUtenteIns");
			trsMap.put("dtInserimento", "dtInserimento");
			AppaltoVO originalVO = beanUtil.transform(appalto, AppaltoVO.class,
					trsMap);
			originalVO = genericDAO.findSingleWhere(originalVO);
			beanUtil.copyNotNullValues(originalVO, appalto, trsMap);
			esito.setAppalto(insertUpdateAppalto(
					beanUtil.transform(appalto, PbandiTAppaltoVO.class),
					idUtente));
			esito.setEsito(aggiornaRibassoAstaAppalto(idUtente, esito.getAppalto()
					.getIdProceduraAggiudicaz(), appalto
					.getImportoRibassoAsta(), appalto
					.getPercentualeRibassoAsta()));
			if(!esito.getEsito()) {
				logger.warn("Appalto non modificato: errore in aggiornaRibassoAstaAppalto");
				esito.setMessage(ERRORE_SERVER);
				esito.setAppalto(appalto);
			}
		} catch (FormalParameterException e) {
			logger.warn("Appalto non modificato: parametri insufficienti");
			esito.setEsito(false);
			esito.setMessage(ERRORE_CAMPO_OBBLIGATORIO);
			esito.setAppalto(appalto);
		} catch (GestioneAppaltiException e) {
			logger.warn("Appalto non modificato: errore in insertUpdateAppalto");
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
			esito.setAppalto(appalto);
		} catch (Exception e) {
			logger.warn("Appalto non modificato: errore in copyNotNullValues");
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
			esito.setAppalto(appalto);
		}
		return esito;
	}

	public EsitoGestioneAppalti rimuoviAppalto(Long idUtente,
			String identitaIride, Long idAppalto) throws CSIException,
			SystemException, UnrecoverableException, GestioneAppaltiException {
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		String[] nameParameter = { "idUtente", "identitaIride", "idAppalto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride,
				idAppalto);
		PbandiTAppaltoVO query = new PbandiTAppaltoVO();
		try {
			query.setIdAppalto(beanUtil.transform(idAppalto, BigDecimal.class));
			esito.setEsito(genericDAO.where(Condition.filterBy(query)).delete());
			esito.setMessage(CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception ex) {
			logger.error("Eccezione durante la delete di un appalto: ", ex);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		return esito;
	}

	public EsitoGestioneAppalti insertAppaltiChecklist(Long idUtente,
			String identitaIride, Long idProgetto,
			AppaltoProgettoDTO[] appaltiProgetto, String tipoChecklist)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAppaltiException {
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		String[] nameParameter = { "idUtente", "identitaIride", "idProgetto",
				"appaltiProgetto", "tipoCheckList" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride,
				idProgetto, appaltiProgetto, tipoChecklist);
		logger.warn("\n\ninsertAppaltiChecklist ,insert "
				+ appaltiProgetto.length + " appalti with tipoCheckList: "
				+ tipoChecklist);
		try {
			PbandiRProgettiAppaltiVO filtro = new PbandiRProgettiAppaltiVO();
			filtro.setIdProgetto(BigDecimal.valueOf(idProgetto.longValue()));
			BigDecimal idTipoDocumentoIndex = decodificheManager
					.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
							tipoChecklist);
			logger.warn("idTipoDocumentoIndex: " + idTipoDocumentoIndex);
			filtro.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			genericDAO
					.deleteWhere(new FilterCondition<PbandiRProgettiAppaltiVO>(
							filtro));
			for (AppaltoProgettoDTO dto : appaltiProgetto) {
				PbandiRProgettiAppaltiVO vo = new PbandiRProgettiAppaltiVO();
				vo.setIdAppalto(BigDecimal.valueOf(dto.getIdAppalto()
						.longValue()));
				vo.setIdProgetto(BigDecimal.valueOf(idProgetto.longValue()));
				vo.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
				logger.warn("inserting for project " + idProgetto
						+ " appalto with id: " + dto.getIdAppalto());
				genericDAO.insert(vo);
			}
			esito.setEsito(Boolean.TRUE);
		} catch (Exception ex) {
			logger.error("Eccezione durante insertAppaltiChecklist", ex);
			esito.setEsito(Boolean.FALSE);
		}

		return esito;
	}

	public EsitoGestioneAppalti eliminaAppaltiChecklist(Long idUtente,
			String identitaIride, Long idProgetto, String tipoChecklist)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAppaltiException {
		EsitoGestioneAppalti esito = new EsitoGestioneAppalti();
		PbandiRProgettiAppaltiVO vo = new PbandiRProgettiAppaltiVO();
		vo.setIdProgetto(BigDecimal.valueOf(idProgetto.longValue()));
		BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(
				PbandiCTipoDocumentoIndexVO.class, tipoChecklist);
		logger.warn("idTipoDocumentoIndex: " + idTipoDocumentoIndex);
		vo.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		try {
			genericDAO
					.deleteWhere(new FilterCondition<PbandiRProgettiAppaltiVO>(
							vo));
			esito.setEsito(Boolean.TRUE);
		} catch (Exception ex) {
			logger.error("Eccezione durante eliminaAppaltiChecklist", ex);
			esito.setEsito(Boolean.FALSE);
		}
		return esito;
	}

	public AppaltoProgettoDTO[] findAppaltiChecklist(Long idUtente,
			String identitaIride, Long idProgetto, String tipoChecklist)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAppaltiException {

		List<AppaltoProgettoDTO> appaltiDTO = new ArrayList<AppaltoProgettoDTO>();
		AppaltoVO filtroAppaltiVO = new AppaltoVO();
		filtroAppaltiVO.setIdProgetto(BigDecimal.valueOf(idProgetto));

		List<AppaltoVO> listAll = genericDAO.where(
				Condition.filterBy(filtroAppaltiVO)).select();
		List<PbandiRProgettiAppaltiVO> listAssociated = new ArrayList<PbandiRProgettiAppaltiVO>();
		PbandiRProgettiAppaltiVO vo = new PbandiRProgettiAppaltiVO();
		vo.setIdProgetto(BigDecimal.valueOf(idProgetto));
		BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(
				PbandiCTipoDocumentoIndexVO.class, tipoChecklist);
		vo.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		listAssociated = genericDAO.findListWhere(vo);
		logger.warn("\n\n\n\nappalti totali: " + listAll.size()
				+ "   , appaltiAssociati a tipoDocIndex:" + tipoChecklist
				+ " --> " + listAssociated.size() + "\n\n\n\n");

		for (AppaltoVO appalto : listAll) {
			AppaltoProgettoDTO dto = new AppaltoProgettoDTO();
			dto.setIdAppalto(appalto.getIdAppalto().longValue());
			dto.setIdProgetto(idProgetto);
			dto.setIsAssociated(Boolean.FALSE);
			dto.setDescrizione(appalto.getOggettoAppalto());
			for (PbandiRProgettiAppaltiVO appaltoAssociato : listAssociated) {
				if (appalto.getIdAppalto().longValue() == appaltoAssociato
						.getIdAppalto().longValue()) {
					dto.setIsAssociated(Boolean.TRUE);
					break;
				}
			}
			appaltiDTO.add(dto);
		}
		return appaltiDTO.toArray(new AppaltoProgettoDTO[0]);
	}

	public EsitoReportAppalti generaReportAppalti(Long idUtente,
			String identitaIride, Long idProgetto
			// PBANDI-2532 inizio
			,Long idTipologiaAppalto,
			Date dtPrevistaInizioLavori,
			Date dtConsegnaLavori,
			Date dtFirmaContratto
			// PBANDI-2532 fine
	) throws CSIException,
			SystemException, UnrecoverableException, GestioneAppaltiException {
		try {

			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaIride, idProgetto);
			AppaltoVO query = new AppaltoVO();
			query.setIdProgetto(BigDecimal.valueOf(idProgetto));
		
			// PBANDI-2532 inizio
			// Se valorizzati, considero i filtri di ricerca popolati a video dall'utente.
			logger.info("Parametri ricerca = "+idTipologiaAppalto+" - "+dtPrevistaInizioLavori+" - "+dtConsegnaLavori+" - "+dtFirmaContratto);
			if (idTipologiaAppalto != null)
				query.setIdTipologiaAppalto(BigDecimal.valueOf(idTipologiaAppalto));
			if (dtPrevistaInizioLavori != null)
				query.setDtInizioPrevista(new java.sql.Date(dtPrevistaInizioLavori.getTime()));
			if (dtConsegnaLavori != null)
				query.setDtConsegnaLavori(new java.sql.Date(dtConsegnaLavori.getTime()));
			if (dtFirmaContratto != null)
				query.setDtFirmaContratto(new java.sql.Date(dtFirmaContratto.getTime()));
			// PBANDI-2532 fine
			
			List<AppaltoVO> elementiReport = genericDAO.where(query).select();
			
			EsitoReportAppalti esito = null;
			
			if (!isEmpty(elementiReport)) {

				byte[] reportDettaglioDocumentiSpesaFileData = TableWriter
						.writeTableToByteArray("reportAppalti",
								new ExcelDataWriter(idProgetto.toString()),
								elementiReport);

				String nomeFile = "reportAppalti" + idProgetto + ".xls";

				esito = new EsitoReportAppalti();
				esito.setExcelBytes(reportDettaglioDocumentiSpesaFileData);
				esito.setNomeFile(nomeFile);
			}

			return esito;
		} catch (Exception e) {
			logger.error(
					"Errore nella creazione del report dettaglio documenti di spesa per id dichiarazione "
							+ idProgetto, e);
			throw new ValidazioneRendicontazioneException(
					"Errore nella creazione del report dettaglio documenti di spesa per id dichiarazione "
							+ idProgetto);
		}
	}

	public EsitoGestioneAppalti associaFornitoreAppalto(Long idUtente,
			String identitaIride, Long idAppalto, Long idFornitore,
			Long idTipoPercettore) throws CSIException, SystemException,
			UnrecoverableException, GestioneAppaltiException {
		// TODO Auto-generated method stub
		logger.info("AssociaFornitoreAppalto:: idAppalto: " + idAppalto + " idFornitore: " + idFornitore + " idTipoPercettore: " + idTipoPercettore);
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "idAppalto", "idFornitore" , "idTipoPercettore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idAppalto, idFornitore, idTipoPercettore);
			PbandiRFornitoreAppaltoVO fornitoreAppaltoInsert = new PbandiRFornitoreAppaltoVO();
			fornitoreAppaltoInsert.setIdAppalto(BigDecimal.valueOf(idAppalto));
			fornitoreAppaltoInsert.setIdFornitore(BigDecimal.valueOf(idFornitore));
			fornitoreAppaltoInsert.setIdTipoPercettore(BigDecimal.valueOf(idTipoPercettore));
			EsitoGestioneAppalti esitoGestione = new EsitoGestioneAppalti();
			try{
				genericDAO.insert(fornitoreAppaltoInsert);
				logger.info("AssociaFornitoreAppalto:: ESITO TRUE");
				esitoGestione.setEsito(Boolean.TRUE);
			}catch (Exception ex){
				esitoGestione.setEsito(Boolean.FALSE);
			}
			return esitoGestione;
		}catch(Exception ex){
			logger.error("Errore durante l'associazione del fornitore " + idFornitore + "  ad appalto " + idAppalto, ex);
			throw new GestioneAppaltiException("Errore durante l'associazione del fornitore " + idFornitore + "  ad appalto " + idAppalto);
		}
	}

	public FornitoreAppaltoDTO[] ottieniFornitoreAssociatiPerAppalto(
			Long idUtente, String identitaIride, Long idAppalto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAppaltiException {
		// TODO Auto-generated method stub
		try{
			FornitoreAppaltoVO query = new FornitoreAppaltoVO();
			query.setIdAppalto(BigDecimal.valueOf(idAppalto));
			List<FornitoreAppaltoVO> fornitoreAssociatiResult = genericDAO.findListWhere(query);
			ArrayList<FornitoreAppaltoDTO> fornitoreAppaltoList = new ArrayList<FornitoreAppaltoDTO>();
			for(FornitoreAppaltoVO fornitoreApp : fornitoreAssociatiResult){
				FornitoreAppaltoDTO fornitoreAppaltoDTO = beanUtil.transform(fornitoreApp, FornitoreAppaltoDTO.class);
				if(null != fornitoreApp)
					if(null != fornitoreApp.getDenominazioneFornitore()){
						fornitoreAppaltoDTO.setDescFornitore(fornitoreApp.getDenominazioneFornitore());
					}
					else{
						fornitoreAppaltoDTO.setDescFornitore(fornitoreApp.getCognomeFornitore()  + " " + fornitoreApp.getNomeFornitore());
					}
				fornitoreAppaltoList.add(fornitoreAppaltoDTO);
			}
			return fornitoreAppaltoList.toArray(new FornitoreAppaltoDTO[fornitoreAppaltoList.size()]);
		}catch(Exception ex){
			logger.error("Errore durante l'ottenimento dei fornitore per l'appalto " + idAppalto, ex);
			throw new GestioneAppaltiException("Errore durante l'ottenimento dei fornitore per l'appalto " + idAppalto);
		}
	}

	public EsitoGestioneAppalti eliminaFornitoreAssociato(Long idUtente,
			String identitaIride, Long idAppalto, Long idFornitore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneAppaltiException {
		// TODO Auto-generated method stub
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "idAppalto", "idFornitore" , "idTipoPercettore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idAppalto, idFornitore);
			PbandiRFornitoreAppaltoVO fornitoreAppaltoFilter = new PbandiRFornitoreAppaltoVO();
			fornitoreAppaltoFilter.setIdAppalto(BigDecimal.valueOf(idAppalto));
			fornitoreAppaltoFilter.setIdFornitore(BigDecimal.valueOf(idFornitore));
			EsitoGestioneAppalti esitoGestione = new EsitoGestioneAppalti();
			try{
				genericDAO.deleteWhere(Condition.filterBy(fornitoreAppaltoFilter));
				logger.info("AssociaFornitoreAppalto:: ESITO TRUE");
				esitoGestione.setEsito(Boolean.TRUE);
			}catch (Exception ex){
				esitoGestione.setEsito(Boolean.FALSE);
			}
			return esitoGestione;
		}catch(Exception ex){
			logger.error("Errore durante l'associazione del fornitore " + idFornitore + "  ad appalto " + idAppalto, ex);
			throw new GestioneAppaltiException("Errore durante l'associazione del fornitore " + idFornitore + "  ad appalto " + idAppalto);
		}
	}

}
