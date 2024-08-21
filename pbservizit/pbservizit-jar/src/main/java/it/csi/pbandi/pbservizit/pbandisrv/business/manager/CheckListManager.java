/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import static it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil.getDataOdiernaSenzaOra;
import static it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil.isNull;

import it.csi.pbandi.pbservizit.business.manager.DocumentiFSManager;
import it.csi.pbandi.pbservizit.integration.dao.impl.DecodificheDAOImpl;
//import it.csi.pbandi.pbservizit.integration.dao.impl.GestioneFileDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestioneaffidamenti.GestioneAffidamentiBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestioneappalti.GestioneAppaltiBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoGetModuloCheckListDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.CheckListDataModel;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezRigheAppalto;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.SezioneAppalti;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.TabAppalti;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.Voce;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.gen.checklistdatamodel.VociDiSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistAffidamentoHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoCheckListDocumentaleAffidamentiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoCheckListInLocoAffidamentiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoCheckListInLocoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoRelazioneTecnicaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListAffidamentoDocumentaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListAffidamentoInLocoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListDocumentaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListInLocoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ComunicazioneFineProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ReportCampionamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RigoContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistAffidamentoHtmlFasiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistHtmlAnagraficaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistHtmlIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistItemDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.DocumentNotCreatedException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.XmlDataExtractionException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiChecklistDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CheckListAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DichiarazioneDiSpesaConTipoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexModelloVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ImportiTotaliPerDichiarazioneDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.LineaDiInterventoPadreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SezioneAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SoggettiFinanziatoriPerProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TotaleQuietanzatoValidatoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaConAmmessoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTemplChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoAffidamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipologiaAggiudicazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDocuIndexTipoStatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRTpDocIndBanLiIntVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTBandoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCampionamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTChecklistHtmlVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDettCheckAppItemVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDettCheckAppaltiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEsitiNoteAffidamentVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProceduraAggiudicazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

public class CheckListManager {


	private interface OperationWithinLock {

		BigDecimal run() throws Exception;

	}

	private static final Map<String, String> APPALTO_VO_TO_XML_OBJECT = new HashMap<String, String>();
	private static final Map<String, String> VOCE_TO_XML_OBJECT = new HashMap<String, String>();
	static {
		APPALTO_VO_TO_XML_OBJECT.put("oggettoAppalto", "oggettoAppalto");
		APPALTO_VO_TO_XML_OBJECT.put("dtGuue", "dataGuue");
		APPALTO_VO_TO_XML_OBJECT.put("dtGuri", "dataGuri");
		APPALTO_VO_TO_XML_OBJECT.put("dtQuotNazionali", "dataQuotNazionali");
		APPALTO_VO_TO_XML_OBJECT.put("dtWebStazAppaltante",	"dataWebStazAppaltante");
		APPALTO_VO_TO_XML_OBJECT.put("dtWebOsservatorio", "dataWebOsservatorio");
		APPALTO_VO_TO_XML_OBJECT.put("bilancioPreventivo", "bilancioPreventivo");
		APPALTO_VO_TO_XML_OBJECT.put("importoContratto", "importoContratto");
		APPALTO_VO_TO_XML_OBJECT.put("dtFirmaContratto", "dataFirma");
		APPALTO_VO_TO_XML_OBJECT.put("dtInizioPrevista", "dataInizio");
		APPALTO_VO_TO_XML_OBJECT.put("dtConsegnaLavori", "dataConsegna");
		APPALTO_VO_TO_XML_OBJECT.put("descTipologiaAggiudicazione", "proceduraGara");

		VOCE_TO_XML_OBJECT.put("descVoceDiSpesa", "descrizione");
	}

	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private DocumentiFSManager documentiFSManager;
	
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private DocumentoManager documentoManager;
	@Autowired
	private PbandiChecklistDAOImpl pbandiChecklistDAO;
	@Autowired
	private ProgettoManager progettoManager;
	@Autowired
	private ReportManager reportManager;
	private SoggettoManager soggettoManager;
	private TimerManager timerManager;
	private GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusiness;
	private GestioneAppaltiBusinessImpl gestioneAppaltiBusiness;
	private GestioneAffidamentiBusinessImpl gestioneAffidamentiBusiness;
	
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	
	@Autowired
	protected DecodificheDAOImpl decodificheDAOImpl;
	
//	@Autowired
//	private GestioneFileDAOImpl gestioneFileDAOImpl;
	
	public GestioneAffidamentiBusinessImpl getGestioneAffidamentiBusiness() {
		return gestioneAffidamentiBusiness;
	}

	public void setGestioneAffidamentiBusiness(
			GestioneAffidamentiBusinessImpl gestioneAffidamentiBusiness) {
		this.gestioneAffidamentiBusiness = gestioneAffidamentiBusiness;
	}

	public GestioneAppaltiBusinessImpl getGestioneAppaltiBusiness() {
		return gestioneAppaltiBusiness;
	}

	public void setGestioneAppaltiBusiness(
			GestioneAppaltiBusinessImpl gestioneAppaltiBusiness) {
		this.gestioneAppaltiBusiness = gestioneAppaltiBusiness;
	}

	public GestioneDatiGeneraliBusinessImpl getGestioneDatiGeneraliBusiness() {
		return gestioneDatiGeneraliBusiness;
	}

	public void setGestioneDatiGeneraliBusiness(
			GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusiness) {
		this.gestioneDatiGeneraliBusiness = gestioneDatiGeneraliBusiness;
	}

	public PbandiChecklistDAOImpl getPbandiChecklistDAO() {
		return pbandiChecklistDAO;
	}

	public void setPbandiChecklistDAO(PbandiChecklistDAOImpl pbandiChecklistDAO) {
		this.pbandiChecklistDAO = pbandiChecklistDAO;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

//	public void setModolDAO(ModolDAO modolDAO) {
//		this.modolDAO = modolDAO;
//	}
//
//	public ModolDAO getModolDAO() {
//		return modolDAO;
//	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	private String getBandoRiferimento(BandoLineaVO bandoLineaVO) {
		String descBandoConDeterminaApprovazione = bandoLineaVO
		.getTitoloBando();
		if (!StringUtil.isEmpty(bandoLineaVO.getDeterminaApprovazione())) {
			descBandoConDeterminaApprovazione = descBandoConDeterminaApprovazione
			+ " , " + bandoLineaVO.getDeterminaApprovazione();
		}
		return descBandoConDeterminaApprovazione;
	}



	private String getDataControllo(CheckListDataModel checkListDataModel) {
		String dataControllo = checkListDataModel.getDataControllo();
		String dataControllo2 = StringUtil.isEmpty(dataControllo) ? it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil
				.getData() : dataControllo;
				return dataControllo2;
	}

	private String getDataControllo(ChecklistHtmlAnagraficaDTO checklistHtmlAnagraficaDTO) {
		String dataControllo = checklistHtmlAnagraficaDTO.getDataControllo();
		String dataControllo2 = StringUtil.isEmpty(dataControllo) ? it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil
				.getData() : dataControllo;
				return dataControllo2;
	}

	private String getLuogoControllo(ChecklistHtmlAnagraficaDTO checklistHtmlAnagraficaDTO) {
		String luogoControllo = checklistHtmlAnagraficaDTO.getLuogoControllo()+"";

		return luogoControllo;
	}
	private String getAttoConcessioneContributo(ChecklistHtmlAnagraficaDTO checklistHtmlAnagraficaDTO) {
		String attoConcessioneContributo = checklistHtmlAnagraficaDTO.getAttoConcessioneContributo()+"";
		return attoConcessioneContributo;
	}


	private VociDiSpesa loadVociDiSpesa(BigDecimal idProgetto) {

		VociDiSpesa result = new VociDiSpesa();
		try {
			for (RigoContoEconomicoDTO rigoContoEconomicoDTO : contoEconomicoManager
					.findContoEconomicoMaster(idProgetto).getFigli()) {
				result.addVoce(beanUtil.transform(rigoContoEconomicoDTO,
						Voce.class, VOCE_TO_XML_OBJECT));
			}
		} catch (ContoEconomicoNonTrovatoException e) {
			logger.warn("Conto economico master mancante, in rendicontazione dovrebbe essere sempre presente: "
					+ e.getMessage());
		}

		return result;
	}

	private ImportiTotaliPerDichiarazioneDiSpesaVO getImportiTotaliPerDichiarazione(
			BigDecimal idDichiarazioneSpesa) {
		ImportiTotaliPerDichiarazioneDiSpesaVO importiTotaliVO = new ImportiTotaliPerDichiarazioneDiSpesaVO();
		importiTotaliVO.setIdDichiarazioneSpesa(idDichiarazioneSpesa);
		List<ImportiTotaliPerDichiarazioneDiSpesaVO> importiTotaliVOs = genericDAO
		.findListWhere(importiTotaliVO);
		importiTotaliVO = new ImportiTotaliPerDichiarazioneDiSpesaVO();
		if (importiTotaliVOs.size() == 1) {
			importiTotaliVO = importiTotaliVOs.get(0);
		}
		return importiTotaliVO;
	}

	private String getDescBreveLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		// se ci chiedono di fare un algoritmo per la descrizione non è meglio
		// farlo girare per aggiornare il nome sul db?

		List<LineaDiInterventoPadreVO> lineeIntervento = progettoManager
		.getGerarchiaLineeInterventoConEsclusione(
				progrBandoLineaIntervento,
				it.csi.pbandi.pbservizit.pbandisrv.util.Constants.DESC_TIPO_LINEA_NORMATIVA);
		String descLinea = StringUtil.join(beanUtil.extractValues(
				lineeIntervento, "descBreveLinea", String.class), ".");
		if (!StringUtil.isEmpty(descLinea)) {
			descLinea = descLinea
			+ " - "
			+ lineeIntervento.get(lineeIntervento.size() - 1)
			.getDescLinea();
		}
		return descLinea;
	}


	private String getDescLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		// se ci chiedono di fare un algoritmo per la descrizione non è meglio
		// farlo girare per aggiornare il nome sul db?

		List<LineaDiInterventoPadreVO> lineeIntervento = progettoManager
		.getGerarchiaLineeInterventoConEsclusione(
				progrBandoLineaIntervento,
				it.csi.pbandi.pbservizit.pbandisrv.util.Constants.DESC_TIPO_LINEA_NORMATIVA);
		String descLinea = StringUtil.join(beanUtil.extractValues(
				lineeIntervento, "descLinea", String.class), ".");
		if (!StringUtil.isEmpty(descLinea)) {
			descLinea = descLinea
			+ " - "
			+ lineeIntervento.get(lineeIntervento.size() - 1)
			.getDescLinea();
		}
		return descLinea;
	}


	private String getContributoPubblicoConcesso(Long idProgetto) {
		List<SoggettiFinanziatoriPerProgettoVO> soggettiFinanziatoriNonPrivati = contoEconomicoManager
		.getSoggettiFinanziatoriNonPrivati(new BigDecimal(idProgetto));
		BigDecimal impQuotaSoggFinanziatore = null;
		if (soggettiFinanziatoriNonPrivati != null
				&& soggettiFinanziatoriNonPrivati.size() > 0) {
			impQuotaSoggFinanziatore = NumberUtil.accumulate(
					soggettiFinanziatoriNonPrivati, "impQuotaSoggFinanziatore")
					.getImpQuotaSoggFinanziatore();
		}
		return beanUtil.transform(impQuotaSoggFinanziatore, String.class);
	}

	private String getCostoTotaleAmmesso(Long idProgetto) {
		return beanUtil.transform(contoEconomicoManager
				.getTotaleSpesaAmmmessaInRendicontazione(new BigDecimal(
						idProgetto)), String.class);
	}

	private String getTotaleSpesaRendicontataOperazione(Long idProgetto)
	throws ContoEconomicoNonTrovatoException {
		if(progettoManager.isProgettoContributo(idProgetto)){
			BigDecimal idProgettoFin = progettoManager.findIdProgettoFinanziamento(idProgetto);
			return beanUtil.transform(progettoManager.getTotaleRendicontazione(idProgettoFin.longValue()), String.class);
		}else
			return beanUtil.transform(progettoManager.getTotaleRendicontazione(idProgetto), String.class);
	}

	private String getAssePrioritario(BigDecimal progrBandoLineaIntervento) {
		PbandiDLineaDiInterventoVO asseFromBandoLinea = progettoManager
		.getLineaDiInterventoAsse(progrBandoLineaIntervento);
		String assePrioritario = asseFromBandoLinea.getDescBreveLinea() + " - "
		+ asseFromBandoLinea.getDescLinea();
		return assePrioritario;
	}



	private BigDecimal saveCheckListDataModel(byte[] bytesModuloPdf,
			BigDecimal idDocumentoIndex, BigDecimal idCheckList,
			BigDecimal idDichiarazione, BigDecimal versione ,BigDecimal idTipoModello) throws Exception {
		logger.debug("saveCheckListDataModel commentato da PK");
/*
		CheckListDataModel checkListDataModel = modolDAO
		.extractBusinessDataFromPDFModule(bytesModuloPdf,
				CheckListDataModel.class);

		if (checkListDataModel == null) {
			checkListDataModel = new CheckListDataModel();
		}

		Map<String, String> mapVOToDTO = new HashMap<String, String>();
		mapVOToDTO.put("dataControllo", "dtControllo");
		mapVOToDTO.put("firmaResponsabile", "soggettoControllore");

		PbandiTChecklistVO pbandiTChecklistVO = beanUtil.transform(checkListDataModel, PbandiTChecklistVO.class, mapVOToDTO);

		BeanUtil.setPropertyIfValueIsNullByName(pbandiTChecklistVO,"dtControllo", getDataOdiernaSenzaOra());

		if (StringUtil.isEmpty(checkListDataModel.getIrregolarita())) {
			pbandiTChecklistVO.setFlagIrregolarita(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
		} else {
			pbandiTChecklistVO.setFlagIrregolarita(checkListDataModel.getIrregolarita());
		}

		pbandiTChecklistVO.setIdChecklist(idCheckList);
		pbandiTChecklistVO.setIdDichiarazioneSpesa(idDichiarazione);

		pbandiTChecklistVO.setVersione(versione);
		pbandiTChecklistVO.setIdDocumentoIndex(idDocumentoIndex);
		pbandiTChecklistVO = genericDAO.insertOrUpdateExisting(pbandiTChecklistVO);

		saveSezioneAppalti(checkListDataModel.getSezioneAppalti(), pbandiTChecklistVO.getIdChecklist(),idTipoModello );
		//  logger.info("\n\n\n####################################\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		return pbandiTChecklistVO.getIdChecklist();
		*/
		return null;
	}

	private void saveSezioneAppalti(SezioneAppalti[] sezioneAppalti,
			BigDecimal idChecklist,BigDecimal idTipoModello) throws Exception {
		if(sezioneAppalti!=null && sezioneAppalti.length>0){
			long start=System.currentTimeMillis();
			logger.info("\n\n\n\n\n\n\n****saveSezioneAppalti, sez appalti  is there! " +sezioneAppalti.length);


			List<PbandiCTemplChecklistVO> listTemplateAppaltiChecklist = getTemplateAppaltiChk(idTipoModello);

			List<CodiceDescrizioneDTO> campiEdit=new ArrayList<CodiceDescrizioneDTO>();
			for (PbandiCTemplChecklistVO template : listTemplateAppaltiChecklist) {
				CodiceDescrizioneDTO codiceDescrizioneDTO=new CodiceDescrizioneDTO();
				codiceDescrizioneDTO.setCodice(template.getCodControllo());
				codiceDescrizioneDTO.setDescrizione(template.getNomeCampoEdit());
				campiEdit.add(codiceDescrizioneDTO);
			}
			//  for (CodiceDescrizioneDTO cd : campiEdit) {
			//  logger.info("**** "+cd.getCodice()+" "+cd.getDescrizione());
			//  }

			for (SezioneAppalti sezioneAppalto : sezioneAppalti) {
				TabAppalti[] tabAppalti = sezioneAppalto.getTabAppalti();
				for (TabAppalti tabAppalto : tabAppalti) {
					String idAppalto = tabAppalto.getIdAppalto();
					logger.info("\n\n\n\n**** idAppalto to save: "+idAppalto);
					if(idAppalto!=null && !idAppalto.equals("")){
						PbandiTAppaltoChecklistVO filterAppaltoChecklist=new PbandiTAppaltoChecklistVO();
						filterAppaltoChecklist.setIdAppalto(new BigDecimal(idAppalto));
						filterAppaltoChecklist.setIdChecklist(idChecklist);
						PbandiTAppaltoChecklistVO pbandiTAppaltoChecklistVODB = genericDAO.findSingleOrNoneWhere(filterAppaltoChecklist);
						if(pbandiTAppaltoChecklistVODB==null){
							pbandiTAppaltoChecklistVODB=genericDAO.insert(filterAppaltoChecklist);
							logger.info("**** inserted pbandiTAppaltoChecklistVO ,idAppaltoChecklist: "+pbandiTAppaltoChecklistVODB.getIdAppaltoChecklist());
						}else{
							logger.info("**** pbandiTAppaltoChecklistVO already there! idAppaltoChecklist: "+pbandiTAppaltoChecklistVODB.getIdAppaltoChecklist());
						}

						SezRigheAppalto[] sezRigheAppalto = tabAppalto.getSezRigheAppalto();
						if(sezRigheAppalto!=null && sezRigheAppalto.length>0){
							for (SezRigheAppalto sezRigaAppalto : sezRigheAppalto) {
								//  logger.info("**** sezRigaAppalto.getCod_controllo():"+sezRigaAppalto.getCod_controllo()
								//      +" ,sezRigaAppalto.getValore(): "+sezRigaAppalto.getValore());
								if(!ObjectUtil.isEmpty(sezRigaAppalto.getCod_controllo())){
									logger.info("**** cod_controllo exist, searching PbandiTDettCheckAppaltiVO ");
									PbandiTDettCheckAppaltiVO pbandiTDettCheckAppaltiVO = getDettAppaltoChecklist(
											pbandiTAppaltoChecklistVODB,
											sezRigaAppalto);
									if(pbandiTDettCheckAppaltiVO!=null){
										logger.info("**** pbandiTDettCheckAppaltiVO exist, updating it... ");
										pbandiTDettCheckAppaltiVO.setFlagEsito(getFlagEsito(sezRigaAppalto.getValore()));//S,N,NA
										pbandiTDettCheckAppaltiVO.setNote(sezRigaAppalto.getNota());
										genericDAO.update(pbandiTDettCheckAppaltiVO);

										List<CodiceDescrizioneDTO> chiaviValoreEdit=getChiaveValoreEdit(sezRigaAppalto,campiEdit);
										if(chiaviValoreEdit!=null && ! chiaviValoreEdit.isEmpty()){
											for (CodiceDescrizioneDTO codiceDescrizioneDTO : chiaviValoreEdit) {                     
												String nomeCampoEdit=codiceDescrizioneDTO.getCodice();
												String valoreEdit=codiceDescrizioneDTO.getDescrizione();
												logger.info("**** nomeCampoEdit exist, searching  PbandiTDettCheckAppItemVO... ");
												PbandiTDettCheckAppItemVO pbandiTDettCheckAppItemVO=new PbandiTDettCheckAppItemVO();
												pbandiTDettCheckAppItemVO.setIdDettCheckAppalti(pbandiTDettCheckAppaltiVO.getIdDettCheckAppalti());
												pbandiTDettCheckAppItemVO.setNomeCampoEdit(nomeCampoEdit);
												pbandiTDettCheckAppItemVO = genericDAO.findSingleOrNoneWhere(pbandiTDettCheckAppItemVO);

												if(pbandiTDettCheckAppItemVO!=null){
													logger.info("**** PbandiTDettCheckAppItemVO exist, updating  ... ");
													pbandiTDettCheckAppItemVO.setValoreEdit(valoreEdit);
													genericDAO.update(pbandiTDettCheckAppItemVO);
												}else{
													logger.info("**** PbandiTDettCheckAppItemVO does not exist, inserting  ... ");
													pbandiTDettCheckAppItemVO=new PbandiTDettCheckAppItemVO();
													pbandiTDettCheckAppItemVO.setIdDettCheckAppalti(pbandiTDettCheckAppaltiVO.getIdDettCheckAppalti());
													pbandiTDettCheckAppItemVO.setNomeCampoEdit(nomeCampoEdit);
													pbandiTDettCheckAppItemVO.setValoreEdit(valoreEdit);
													genericDAO.insert(pbandiTDettCheckAppItemVO);
												}
											}
										}
									}else{
										pbandiTDettCheckAppaltiVO=new PbandiTDettCheckAppaltiVO();
										pbandiTDettCheckAppaltiVO.setFlagEsito(getFlagEsito(sezRigaAppalto.getValore()));//S,N,NA
										pbandiTDettCheckAppaltiVO.setNote(sezRigaAppalto.getNota());
										pbandiTDettCheckAppaltiVO.setCodControllo(sezRigaAppalto.getCod_controllo());
										pbandiTDettCheckAppaltiVO.setIdAppaltoChecklist(pbandiTAppaltoChecklistVODB.getIdAppaltoChecklist());
										logger.info("**** pbandiTDettCheckAppaltiVO does not exist, inserting it...idAppalto "+pbandiTAppaltoChecklistVODB.getIdAppalto());
										pbandiTDettCheckAppaltiVO = genericDAO.insert(pbandiTDettCheckAppaltiVO);
										List<CodiceDescrizioneDTO> chiaviValoreEdit=getChiaveValoreEdit(sezRigaAppalto,campiEdit);

										if(chiaviValoreEdit!=null && ! chiaviValoreEdit.isEmpty()){
											for (CodiceDescrizioneDTO codiceDescrizioneDTO : chiaviValoreEdit) {    
												String nomeCampoEdit=codiceDescrizioneDTO.getCodice();
												String valoreEdit=codiceDescrizioneDTO.getDescrizione();
												PbandiTDettCheckAppItemVO pbandiTDettCheckAppItemVO=new PbandiTDettCheckAppItemVO();
												pbandiTDettCheckAppItemVO.setIdDettCheckAppalti(pbandiTDettCheckAppaltiVO.getIdDettCheckAppalti());
												pbandiTDettCheckAppItemVO.setNomeCampoEdit(nomeCampoEdit);
												pbandiTDettCheckAppItemVO.setValoreEdit(valoreEdit);
												logger.info("**** PbandiTDettCheckAppItemVO does not exist, inserting  ... ");
												genericDAO.insert(pbandiTDettCheckAppItemVO);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			logger.info("saveSezioneAppalti executed in ms: "+(System.currentTimeMillis()-start)+"\n\n\n");
		}else
			logger.info("no sezione appalti! nothing to do\n\n\n");
	}

	private PbandiTDettCheckAppaltiVO getDettAppaltoChecklist(
			PbandiTAppaltoChecklistVO pbandiTAppaltoChecklistVODB,
			SezRigheAppalto sezRigaAppalto) {
		PbandiTDettCheckAppaltiVO pbandiTDettCheckAppaltiVO=new PbandiTDettCheckAppaltiVO();
		pbandiTDettCheckAppaltiVO.setCodControllo(sezRigaAppalto.getCod_controllo());
		pbandiTDettCheckAppaltiVO.setIdAppaltoChecklist(pbandiTAppaltoChecklistVODB.getIdAppaltoChecklist());
		pbandiTDettCheckAppaltiVO = genericDAO.findSingleOrNoneWhere(pbandiTDettCheckAppaltiVO);
		return pbandiTDettCheckAppaltiVO;
	}

	private List<PbandiCTemplChecklistVO> getTemplateAppaltiChk(
			BigDecimal idTipoModello) {
		PbandiCTemplChecklistVO pbandiCTemplChecklistVO=new PbandiCTemplChecklistVO();
		pbandiCTemplChecklistVO.setIdTipoModello(idTipoModello);
		NotCondition<PbandiCTemplChecklistVO> notNomeCampoEditNullCondition = new NotCondition<PbandiCTemplChecklistVO>(
				new NullCondition<PbandiCTemplChecklistVO>(
						PbandiCTemplChecklistVO.class,
						"nomeCampoEdit" ));
		NotCondition<PbandiCTemplChecklistVO> notCodControlloNullCondition = new NotCondition<PbandiCTemplChecklistVO>(
				new NullCondition<PbandiCTemplChecklistVO>(
						PbandiCTemplChecklistVO.class,
						"codControllo"));

		List<PbandiCTemplChecklistVO> listTemplateAppaltiChecklist = genericDAO.findListWhere(
				new AndCondition<PbandiCTemplChecklistVO>(notNomeCampoEditNullCondition,notCodControlloNullCondition,new FilterCondition<PbandiCTemplChecklistVO>(pbandiCTemplChecklistVO))
		);
		return listTemplateAppaltiChecklist;
	}

	private List<CodiceDescrizioneDTO> getChiaveValoreEdit(SezRigheAppalto sezRigaAppalto,List<CodiceDescrizioneDTO> editableFields) {
		List<CodiceDescrizioneDTO> chiaviValoreEdit=new ArrayList<CodiceDescrizioneDTO>();
		StringBuilder sb=new StringBuilder();
		for (CodiceDescrizioneDTO dto : editableFields) {
			//  sb.append("\ndto.getCodice() "+dto.getCodice()+" , sezRigaAppalto.getCod_controllo():"+sezRigaAppalto.getCod_controllo());
			if(sezRigaAppalto.getCod_controllo().equalsIgnoreCase(dto.getCodice())){
				String prop =  dto.getDescrizione();
				String val = getBeanUtil().getPropertyValueByName(sezRigaAppalto, prop,String.class);
				//  sb.append("\nprop:"+prop+"  val: "+val);

				if(val!=null){
					logger.info("getChiaveValoreEdit: sezRigaAppalto.getCod_controllo() "+sezRigaAppalto.getCod_controllo()+" , prop "+prop +" ,val: "+val );
					CodiceDescrizioneDTO cd=new CodiceDescrizioneDTO();
					cd.setCodice(prop);
					cd.setDescrizione(val);
					chiaviValoreEdit.add(cd) ;
				}
			} 
		}
		return chiaviValoreEdit;
	}

	private String getFlagEsito(String valore) {
		if(valore==null || valore.equals(""))
			return null;
		if(valore.equals("1")) return "S";
		else if(valore.equals("2")) return "N";
		else if(valore.equals("3")) return "NA"; 
		else return null;
	}

	private String getValore(String flagEsito) {
		if(flagEsito==null || flagEsito.equals(""))
			return null;
		if(flagEsito.equals("S")) return "1";
		else if(flagEsito.equals("N")) return "2";
		else if(flagEsito.equals("NA")) return "3"; 
		else return null;
	}

	public EsitoGetModuloCheckListDTO caricaModuloChecklist(Long idUtente,String identitaIride,
			Long idProgetto, String codTipoDocIndex, String nomeFile,
			BigDecimal idTarget, Class<? extends GenericVO> voClass)
					throws XmlDataExtractionException, 	Exception {
//	throws XmlDataExtractionException, ModuleNotFoundException,
//	Exception {

		logger.debug("caricaModuloChecklist commentato da PK");
		/*
		logger.info("\n\n\n\n\n\n\ncaricaModuloChecklist ####################");

		byte[] bytesPdf = null;

		CheckListDataModel checkListDataModel;
		try {
			DocumentoDTO docDTO = documentoManager.caricaDocumento(idTarget,
					BigDecimal.valueOf(idProgetto),
					voClass, codTipoDocIndex);

			bytesPdf = docDTO.getBytesDocumento();
			nomeFile = docDTO.getNomeFile();

			checkListDataModel = modolDAO.extractBusinessDataFromPDFModule(bytesPdf,CheckListDataModel.class);
		} catch (DocumentNotFoundException e) {
			logger.info("checkListDataModel not found , creating new empty ");
			checkListDataModel = beanUtil.createNewInstance(CheckListDataModel.class);
		}
		logger.info("\n\n\n++++++++++++++++++++++++++++++++++ before caricaIdentificativoModello idProgetto :"+idProgetto+" ,codTipoDocIndex:"+codTipoDocIndex);
		DocumentoIndexModelloVO docIndexModello = caricaIdentificativoModello(idProgetto, codTipoDocIndex);

		logger.info("\n\n\nbefore loadCheckListDataModel idProgetto :"+idProgetto);
		checkListDataModel = loadCheckListDataModel(idUtente, identitaIride,
				docIndexModello,        
				checkListDataModel, 
				idProgetto,
				codTipoDocIndex,
				idTarget);
		//logger.shallowDump(checkListDataModel, "info");
		logger.info("\n\n\n++++++++++++++++++++++++++++++++++ before creaPdfDaModello idProgetto :"+idProgetto);
		bytesPdf = modolDAO.creaPdfDaModello(docIndexModello,checkListDataModel );

		EsitoGetModuloCheckListDTO esitoGetModulo = new EsitoGetModuloCheckListDTO();
		esitoGetModulo.setEsito(true);
		esitoGetModulo.setNomeFile(nomeFile);
		esitoGetModulo.setByteModulo(bytesPdf);
		return esitoGetModulo;
		*/
		return null;
	}

	/*
	 * In funzione dell'id progetto recupera l'id_bando_linea da cui si prende
	 * il nome modello collegato; se ne tiro fuori n, prendo quello con la
	 * versione maggiore in corso di validità e il tipo_doc_index validazione o
	 * in loco a seconda di quanto specificato
	 */
	private DocumentoIndexModelloVO caricaIdentificativoModello(
			final Long idProgetto, final String tipoDocumento) throws Exception {
		DocumentoIndexModelloVO documentoIndexModelloVO = new DocumentoIndexModelloVO();

		try {
			documentoIndexModelloVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			documentoIndexModelloVO.setDescBreveTipoDocIndex(tipoDocumento);
			documentoIndexModelloVO = genericDAO.findSingleWhere(documentoIndexModelloVO);
			logger.shallowDump(documentoIndexModelloVO, "info");
		} catch (RecordNotFoundException e) {
			String message = "Modello per progetto " + idProgetto + " tipo "
			+ tipoDocumento + ", causa: " + e.getMessage();
			logger.error(message, e);
			throw new Exception(message, e);
		}
		return documentoIndexModelloVO;
	}

	public EsitoSalvaModuloCheckListDTO salvaChecklistValidazione(
			final java.lang.Long idUtente, java.lang.Long idProgetto,
			final byte[] bytesModuloPdf, final String codStatoTipoDocIndex,
			final java.lang.Long idDichiarazione) throws Exception {

		logger.info("salvaChecklistValidazione ,idDichiarazione "+idDichiarazione
				+" ,idProgetto:"+idProgetto
				+" ,idUtente:"+idUtente
				+" ,codStatoTipoDocIndex:"+codStatoTipoDocIndex
				+" ,COD_TIPO_DOCUMENTO_INDEX: CL "
				+" ,bytesModuloPdf:"+bytesModuloPdf);

		if(bytesModuloPdf==null)
			logger.warn("bytesModuloPdf NULL !!!!!!");

		Class<PbandiTDichiarazioneSpesaVO> voClass = PbandiTDichiarazioneSpesaVO.class;
		String codTipoDocIndex = GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE;

		EsitoSalvaModuloCheckListDTO esito = new EsitoSalvaModuloCheckListDTO();


		final PbandiTDocumentoIndexVO documentoIndexVO = getDocumentoManager()
		.getDocumentoIndexSuDb(
				idDichiarazione,
				idProgetto,
				codTipoDocIndex,
				new PbandiTDichiarazioneSpesaVO().getTableNameForValueObject());


		final CheckListDocumentaleDTO checkListValidazioneDTO = new CheckListDocumentaleDTO();

		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

		checkListValidazioneDTO.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());

		checkListValidazioneDTO.setBytesModuloPdf(bytesModuloPdf);
		checkListValidazioneDTO.setDataChiusura(new Date());
		checkListValidazioneDTO.setIdDichiarazioneDiSpesa(BigDecimal.valueOf(idDichiarazione));
		checkListValidazioneDTO.setIdProgetto(BigDecimal.valueOf(idProgetto));

		final DocumentoIndexModelloVO  identificativoModello = caricaIdentificativoModello(pbandiTProgettoVO.getIdProgetto().longValue(),"CL");

		if (isNull(documentoIndexVO)) {
			try {
				checkListValidazioneDTO.setVersione(getVersioneIniziale());

				logger.info("saving docIndex on db and index.....");
				PbandiTDocumentoIndexVO newDocumentoIndexVO = documentoManager
				.creaDocumento(idUtente, checkListValidazioneDTO,codStatoTipoDocIndex,null,null,null);

				logger.info("newDocumentoIndexVO.getIdDocumentoIndex(): "+newDocumentoIndexVO.getIdDocumentoIndex());
				salvaDatiChecklistDocumentale(bytesModuloPdf, 
						BigDecimal.valueOf(idDichiarazione),
						newDocumentoIndexVO,
						newDocumentoIndexVO.getIdDocumentoIndex(),identificativoModello.getIdTipoModello());
				esito.setEsito(true);
				esito.setMessage(MessaggiConstants.MSG_CHECKLIST_SALVATA);
				/*
				 * FIX PBandi-2006. Inserisco nell' esito anche l' id del documento
				 * index creato
				 */
				esito.setIdDocumentoIndex(newDocumentoIndexVO.getIdDocumentoIndex().longValue());

			} catch (Exception e) {
				logger.debug("Impossibile salvare: " + e.getMessage());
				esito.setEsito(false);
				esito.setMessage(e.getMessage());
			}
		} else {
			doWithinLock(esito, new OperationWithinLock() {

				public BigDecimal run() throws Exception {
					BigDecimal idDocumentoIndexPrecedente = documentoIndexVO.getIdDocumentoIndex();
					PbandiTDocumentoIndexVO nuovoDocumentoIndexVO = documentoIndexVO;
					if (GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_INVIATO
							.equalsIgnoreCase(getCodStatoTipoDocIndex(idDocumentoIndexPrecedente))) {

						// SALVO NUOVA VERSIONE
						checkListValidazioneDTO.setVersione(NumberUtil.sum(
								documentoIndexVO.getVersione(), new BigDecimal(
										1)));
						nuovoDocumentoIndexVO = documentoManager.creaDocumento(
								idUtente, checkListValidazioneDTO,
								codStatoTipoDocIndex,null,null,null);

					} else {
						// SALVO SULLA VERSIONE CORRENTE
						checkListValidazioneDTO.setNomeFile(documentoIndexVO
								.getNomeFile());
						checkListValidazioneDTO.setUid(documentoIndexVO
								.getUuidNodo());

						documentoManager.aggiornaDocumento(
								checkListValidazioneDTO, idUtente,
								documentoIndexVO, codStatoTipoDocIndex);
					}
					salvaDatiChecklistDocumentale(bytesModuloPdf, BigDecimal.valueOf(idDichiarazione),
							nuovoDocumentoIndexVO, idDocumentoIndexPrecedente,identificativoModello.getIdTipoModello());
					/*
					 * FIX PBandi-2006. Inserisco nell' esito anche l' id del documento
					 * index creato
					 */
					return nuovoDocumentoIndexVO.getIdDocumentoIndex();

				}
			}, idUtente, BigDecimal.valueOf(idProgetto), BigDecimal.valueOf(idDichiarazione), voClass, codTipoDocIndex);
		}

		return esito;
	}






	private void salvaDatiChecklistDocumentale(byte[] bytesModuloPdf,
			BigDecimal idDichiarazioneDiSpesa, PbandiTDocumentoIndexVO documentoIndexVO,
			BigDecimal idDocumentoIndex,BigDecimal idTipoModello) throws Exception {
		PbandiTChecklistVO checklistVO = new PbandiTChecklistVO();
		checklistVO.setIdDichiarazioneSpesa(idDichiarazioneDiSpesa);
		checklistVO.setIdDocumentoIndex(idDocumentoIndex);
		logger.info("searching PbandiTChecklistVO with idDocumentoIndex: " + idDocumentoIndex
				+" and idDichiarazioneDiSpesa: "+idDichiarazioneDiSpesa);
		List<PbandiTChecklistVO> list = genericDAO.where(Condition.filterBy(checklistVO)).select();

		BigDecimal idChecklist = null;
		if (list.size() == 1) {
			idChecklist = list.get(0).getIdChecklist();
		}
		logger.info("idChecklist : "+idChecklist);
		saveCheckListDataModel(bytesModuloPdf,
				documentoIndexVO.getIdDocumentoIndex(), idChecklist,
				documentoIndexVO.getIdTarget(), documentoIndexVO.getVersione(),idTipoModello);
	}

	private void salvaDatiChecklistDocumentaleHtml(byte[] bytesModuloPdf,
			BigDecimal idDichiarazioneDiSpesa, PbandiTDocumentoIndexVO documentoIndexVO,
			BigDecimal idDocumentoIndex,BigDecimal idTipoModello,
			String soggettoControllore,
			String dataControllo,
			String referenteBeneficiario, 
			String flagIrregolarita,
			String importoIrregolarita,
			String contentHtml,
			String codStatoTipoDocIndex,
			String oldCodStatoTipoDocIndex
			
	) throws Exception {
		logger.info("\n\n\nsalvaDatiChecklistDocumentaleHtml");
		PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
		pbandiTChecklistVO.setIdDichiarazioneSpesa(idDichiarazioneDiSpesa);
		pbandiTChecklistVO.setIdDocumentoIndex(idDocumentoIndex);
		logger.info("searching PbandiTChecklistVO with idDocumentoIndex: " + idDocumentoIndex
				+" and idDichiarazioneDiSpesa: "+idDichiarazioneDiSpesa);
		List<PbandiTChecklistVO> list = genericDAO.where(Condition.filterBy(pbandiTChecklistVO)).select();

		BigDecimal idChecklist = null;
		if (list.size() == 1) {
			idChecklist = list.get(0).getIdChecklist();
		}
		logger.info("idChecklist : "+idChecklist);
		
		//TODO: creare un nuovo record ad ogni salvataggio definitivo e non in bozza
		//TODO: il passaggio tra una bozza ad una definitiva
	
		if(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(codStatoTipoDocIndex) && GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(oldCodStatoTipoDocIndex))
			pbandiTChecklistVO.setIdChecklist(null);
		else
			pbandiTChecklistVO.setIdChecklist(idChecklist);
		
		logger.info("idChecklist : "+pbandiTChecklistVO.getIdChecklist());

		if(dataControllo==null)
			pbandiTChecklistVO.setDtControllo(DateUtil.getSysdate());
		else
			pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));

		pbandiTChecklistVO.setFlagIrregolarita(flagIrregolarita);
		pbandiTChecklistVO.setImportoIrregolarita(NumberUtil.toBigDecimalFromItalianFormat(importoIrregolarita));
		
		logger.info("importoIrregolarità : " + pbandiTChecklistVO.getImportoIrregolarita());

		pbandiTChecklistVO.setSoggettoControllore(soggettoControllore);

		pbandiTChecklistVO.setVersione(documentoIndexVO.getVersione());
		pbandiTChecklistVO.setIdDocumentoIndex(idDocumentoIndex);
		pbandiTChecklistVO = genericDAO.insertOrUpdateExisting(pbandiTChecklistVO);


		PbandiTChecklistHtmlVO pbandiTChecklistHtmlVO=new PbandiTChecklistHtmlVO(pbandiTChecklistVO.getIdChecklist());
		pbandiTChecklistHtmlVO=genericDAO.findSingleOrNoneWhere(pbandiTChecklistHtmlVO);
		if(pbandiTChecklistHtmlVO==null){
			getPbandiChecklistDAO().insertChecklistCompiled(contentHtml, pbandiTChecklistVO.getIdChecklist());
		}else{
			getPbandiChecklistDAO().updateChecklistCompiled(contentHtml, pbandiTChecklistVO.getIdChecklist());
		}

	}


	public EsitoSalvaModuloCheckListDTO salvaChecklistInLoco(Long idUtente,
			PbandiTProgettoVO pbandiTProgettoVO ,
			byte[] bytesModuloPdf,
			String codStatoTipoDocIndex,
			BigDecimal idDocumentoIndex)
	throws Exception {

		logger.begin();
		EsitoSalvaModuloCheckListDTO esitoSalvaModuloManager = new EsitoSalvaModuloCheckListDTO();

		CheckListInLocoDTO checkListInLocoDTO = new CheckListInLocoDTO();

		checkListInLocoDTO.setBytesModuloPdf(bytesModuloPdf);
		checkListInLocoDTO.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());
		checkListInLocoDTO.setDataInserimento(new Date());
		checkListInLocoDTO.setIdProgetto(pbandiTProgettoVO.getIdProgetto());
		DocumentoIndexModelloVO  identificativoModello = caricaIdentificativoModello(pbandiTProgettoVO.getIdProgetto().longValue(),
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);

		boolean esito = false;
		if (idDocumentoIndex == null) {
			logger.info("\n\nsalvaChecklistInLoco,  idDocumentoIndex is null, creating new checklist with codStatoTipoDocIndex: "+codStatoTipoDocIndex );

			PbandiTChecklistVO pbandiTChecklistVO = createChecklist(idUtente, bytesModuloPdf,
					checkListInLocoDTO,codStatoTipoDocIndex,
					identificativoModello.getIdTipoModello());
			idDocumentoIndex=pbandiTChecklistVO.getIdDocumentoIndex();
			esito = true;
		} else {
			logger.info("\n\nsalvaChecklistInLoco, modifyChecklistInLoco , idDocumentoIndex is not null : " + idDocumentoIndex);
			esito = modifyChecklistInLoco(idUtente,
					codStatoTipoDocIndex, idDocumentoIndex,
					esitoSalvaModuloManager, bytesModuloPdf, 
					checkListInLocoDTO,
					pbandiTProgettoVO.getIdProgetto().longValue(),
					identificativoModello.getIdTipoModello());
		}

		esitoSalvaModuloManager.setEsito(esito);
		esitoSalvaModuloManager.setIdChecklist(checkListInLocoDTO.getIdChecklist().longValue());
		esitoSalvaModuloManager.setIdDocumentoIndex(idDocumentoIndex.longValue());

		logger.end();
		return esitoSalvaModuloManager;
	}

	private boolean modifyChecklistInLoco(final Long idUtente,
			final String codStatoTipoDocIndex, BigDecimal idDocumentoIndex,
			EsitoSalvaModuloCheckListDTO esito, final byte[] bytesModuloPdf,
			final CheckListInLocoDTO checkListInLocoDTO,Long idProgetto,final BigDecimal idTipoModello) throws Exception {

		logger.begin();
		PbandiTDocumentoIndexVO filtro = new PbandiTDocumentoIndexVO(idDocumentoIndex);
		final PbandiTDocumentoIndexVO documentoIndexVO = genericDAO.findSingleWhere(filtro);

		checkListInLocoDTO.setNomeFile(documentoIndexVO.getNomeFile());
		checkListInLocoDTO.setUid(documentoIndexVO.getUuidNodo());
		checkListInLocoDTO.setIdChecklist(documentoIndexVO.getIdTarget());

		Class<PbandiTChecklistVO> voClass = PbandiTChecklistVO.class;
		String codTipoDocIndex = GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO;
		doWithinLock(
				esito,
				new OperationWithinLock() {
					public BigDecimal run() throws Exception {
						documentoManager.aggiornaDocumento(checkListInLocoDTO,
								idUtente, documentoIndexVO,
								codStatoTipoDocIndex);
						saveCheckListDataModel(bytesModuloPdf,
								documentoIndexVO.getIdDocumentoIndex(),
								documentoIndexVO.getIdTarget(), null, null,idTipoModello);

						/*
						 * FIX PBandi-2006. Inserisco nell' esito anche l' id del documento
						 * index creato
						 */
						return documentoIndexVO.getIdDocumentoIndex();
					}
				}, idUtente, documentoIndexVO.getIdProgetto(),
				documentoIndexVO.getIdTarget(), voClass, codTipoDocIndex);
		logger.end();
		return esito.getEsito();
	}

	private EsitoSalvaModuloCheckListDTO modifyChecklistHtmlInLoco(final Long idUtente,
			final String codStatoTipoDocIndex,
			final PbandiTDocumentoIndexVO documentoIndexVO  ,
			EsitoSalvaModuloCheckListDTO esito, final byte[] bytesModuloPdf,
			final CheckListInLocoDTO checkListInLocoDTO,Long idProgetto,
			final BigDecimal idTipoModello,
			final String soggettoControllore,final String dataControllo,
			final String referenteBeneficiario,
			final String flagIrregolarita) throws Exception {

		logger.begin();
		checkListInLocoDTO.setNomeFile(documentoIndexVO.getNomeFile());
		checkListInLocoDTO.setUid(documentoIndexVO.getUuidNodo());
		checkListInLocoDTO.setIdChecklist(documentoIndexVO.getIdTarget());
		checkListInLocoDTO.setBytesModuloPdf(bytesModuloPdf);
		Class<PbandiTChecklistVO> voClass = PbandiTChecklistVO.class;
		String codTipoDocIndex = GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO;
		doWithinLock(
				esito,
				new OperationWithinLock() {
					public BigDecimal run() throws Exception {
						documentoManager.aggiornaDocumento(checkListInLocoDTO,
								idUtente, documentoIndexVO,
								codStatoTipoDocIndex);


						PbandiTChecklistVO pbandiTChecklistVO =new PbandiTChecklistVO();
						pbandiTChecklistVO.setIdChecklist(checkListInLocoDTO.getIdChecklist());
						if(dataControllo==null)
							pbandiTChecklistVO.setDtControllo(DateUtil.getSysdate());
						else
							pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));
						pbandiTChecklistVO.setSoggettoControllore(soggettoControllore); 
						pbandiTChecklistVO.setReferenteBeneficiario(referenteBeneficiario); 
						pbandiTChecklistVO.setFlagIrregolarita(flagIrregolarita); 

						BeanUtil.setPropertyIfValueIsNullByName(pbandiTChecklistVO,"dtControllo", getDataOdiernaSenzaOra());

						//pbandiTChecklistVO.setIdDichiarazioneSpesa(idDichiarazione);

						//pbandiTChecklistVO.setVersione(versione);
						//pbandiTChecklistVO.setIdDocumentoIndex(idDocumentoIndex);
						genericDAO.update(pbandiTChecklistVO);


						return documentoIndexVO.getIdDocumentoIndex();
					}
				}, idUtente, documentoIndexVO.getIdProgetto(),
				documentoIndexVO.getIdTarget(), voClass, codTipoDocIndex);
		logger.end();
		return esito;
	}



	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO salvaChecklistHtmlInLoco(Long idUtente,
			PbandiTProgettoVO pbandiTProgettoVO ,
			byte[] bytesModuloPdf,
			String contentHtml,
			String codStatoTipoDocIndex,
			PbandiTDocumentoIndexVO documentoIndexVO )
	throws Exception {

		logger.begin();
		if (documentoIndexVO == null)
			logger.info(" documentoIndexVO = null");
		else
			logger.info(" documentoIndexVO.getIdDocumentoIndex() = "+documentoIndexVO.getIdDocumentoIndex());
		logger.info(" codStatoTipoDocIndex = "+codStatoTipoDocIndex);
	
		it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO esitoSalvaModuloManager = new EsitoSalvaModuloCheckListDTO();
		BigDecimal idDocumentoIndex=null;
		CheckListInLocoDTO checkListInLocoDTO = new CheckListInLocoDTO();


		checkListInLocoDTO.setBytesModuloPdf(bytesModuloPdf);
		checkListInLocoDTO.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());
		checkListInLocoDTO.setDataInserimento(new Date());
		checkListInLocoDTO.setIdProgetto(pbandiTProgettoVO.getIdProgetto());
		DocumentoIndexModelloVO  identificativoModello = caricaIdentificativoModello(pbandiTProgettoVO.getIdProgetto().longValue(),
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);

		EsitoSalvaModuloCheckListDTO esito = new EsitoSalvaModuloCheckListDTO();
		String soggettoControllore=  getSoggettoControllore(contentHtml);
		String dataControllo=  getDataControllo(contentHtml);
		String flagIrregolarita =  getFlagIrregolarita(contentHtml);
		String descIrregolarita =  getDescIrregolarita(contentHtml);
		String importoIrregolarita =  getImportoIrregolarita(contentHtml);
		String referenteBeneficiario=  getReferenteBeneficiario(contentHtml);

		if (documentoIndexVO == null) {
			logger.info(" documentoIndexVO is null, creating new checklist with codStatoTipoDocIndex: "+codStatoTipoDocIndex );

			PbandiTChecklistVO pbandiTChecklistVO = createChecklistHtml (idUtente, 
					bytesModuloPdf,
					checkListInLocoDTO,
					codStatoTipoDocIndex,
					identificativoModello.getIdTipoModello(),
					soggettoControllore,
					dataControllo,
					referenteBeneficiario,
					flagIrregolarita,
					importoIrregolarita
			);
			logger.info("createChecklistHtml ok, IdChecklist="+pbandiTChecklistVO.getIdChecklist());
			
			checkListInLocoDTO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
			idDocumentoIndex=pbandiTChecklistVO.getIdDocumentoIndex();
			logger.info("IdDocumentoIndex="+idDocumentoIndex);
			
			//TODO PK : questa qui sotto non funziona---->getLobHandler()  da nullPointerException
			getPbandiChecklistDAO().insertChecklistCompiled(contentHtml, pbandiTChecklistVO.getIdChecklist());

			/* guardo:
			 * PbandiTTracciamentoVO vo = new PbandiTTracciamentoVO(BigDecimal.valueOf(idTracciamento));
					genericDAO.updateClob(vo, "dettaglioErrore", trace);
			 */
			//insert into PBANDI_T_CHECKLIST_HTML (html,id_checklist) values(?,?)
//			PbandiTChecklistHtmlVO vo = new PbandiTChecklistHtmlVO();
//			vo.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
//			genericDAO.insert(vo);
//			logger.info("PBANDI_T_CHECKLIST_HTML inserita");
//			
//			genericDAO.updateClob(vo, "html", contentHtml);
//			logger.info("PBANDI_T_CHECKLIST_HTML aggiornata");
			
			esito.setEsito(true);

		} else {

			// Alex: 29/01/2020
			// Riga aggiunta poichè altrimenti dà errore NullObject alla riga successiva
			//           esitoSalvaModuloManager.setIdDocumentoIndex(idDocumentoIndex.longValue());
			// Stesso controllo che avevo gia' messo in salvaChecklistAffidamentoInLoco().
			idDocumentoIndex = documentoIndexVO.getIdDocumentoIndex();
			
			logger.info("  documentoIndexVO is not null : " + documentoIndexVO.getIdDocumentoIndex()+
					"\ndocumentoIndexVO.getIdTarget( idChecklist ): "+documentoIndexVO.getIdTarget());

			esito = modifyChecklistHtmlInLoco(idUtente,
					codStatoTipoDocIndex, 
					documentoIndexVO,
					esitoSalvaModuloManager,
					bytesModuloPdf, 
					checkListInLocoDTO,
					pbandiTProgettoVO.getIdProgetto().longValue(),
					identificativoModello.getIdTipoModello(),
					soggettoControllore,
					dataControllo,
					referenteBeneficiario,
					flagIrregolarita);
			//logger.info("\n\n\n\n\n\nupdate clob checklist in loco !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			PbandiTChecklistHtmlVO checklistVO=new PbandiTChecklistHtmlVO(documentoIndexVO.getIdTarget());
			genericDAO.updateClob(checklistVO, "html", contentHtml);
		}

		esitoSalvaModuloManager.setEsito(esito.getEsito());
		esitoSalvaModuloManager.setIdChecklist(checkListInLocoDTO.getIdChecklist().longValue());
		esitoSalvaModuloManager.setIdDocumentoIndex(idDocumentoIndex.longValue());
		logger.end();
		return esitoSalvaModuloManager;
	}


	private void doWithinLock(
			EsitoSalvaModuloCheckListDTO esitoSalvaModuloManager,
			OperationWithinLock operationWithinLock, Long idUtente,
			BigDecimal idProgetto, BigDecimal idTarget,
			Class<? extends GenericVO> voClass, String codTipoDocIndex) {

		logger.begin();
		boolean lockScaduto = documentoManager.isLockDocumentoScaduto(idUtente,
				beanUtil.transform(idProgetto, Long.class),
				beanUtil.transform(idTarget, Long.class), voClass,
				codTipoDocIndex);

		logger.info("lockScaduto="+lockScaduto);
		
		if (!lockScaduto) {
			try {
				
				//PK rimuovo entry da PBANDI_T_DOCUMENTO_INDEX_LOCK
				boolean lockRimosso = documentoManager.eliminaLock(
						beanUtil.transform(idUtente, BigDecimal.class),
						idProgetto, idTarget, voClass, codTipoDocIndex);

				logger.debug("lockRimosso="+lockRimosso);
				
				if (!lockRimosso) {
					esitoSalvaModuloManager.setEsito(false);
					esitoSalvaModuloManager.setMessage(MessaggiConstants.KEY_CHECKLIST_LOCCATA);
					logger.info("Operazione annullata. Lock non presente.");
				} else {
					logger.info("doWithinLock PRE RUN");
					BigDecimal idDocumentoIndex = operationWithinLock.run();
					logger.info("doWithinLock idDocumentoIndex="+idDocumentoIndex);
					
					esitoSalvaModuloManager.setEsito(true);
					esitoSalvaModuloManager
					.setMessage(MessaggiConstants.MSG_CHECKLIST_SALVATA);
					/*
					 * FIX PBandi-2006. Inserisco nell' esito l' id del 
					 * documento index creato.
					 */
					esitoSalvaModuloManager.setIdDocumentoIndex(idDocumentoIndex.longValue());
					logger.info("Operazione eseguita, lock rimosso.");
				}
			} catch (Exception e) {
				
				// PK qui potrebbe essere stato tolto il lOCK ma essere andata in eccezione l'operazione run()
				
				logger.error("Operazione annullata. Impossibile ottenere il lock: "
						+ e.getMessage());
				esitoSalvaModuloManager.setEsito(false);
				esitoSalvaModuloManager.setMessage(e.getMessage());
			}
		} else {
			setEsitoWithTimeoutMessage(esitoSalvaModuloManager);
		}
		logger.end();
	}

	private PbandiTChecklistVO createChecklist(Long idUtente, 
			byte[] bytesModuloPdf,
			CheckListInLocoDTO checkListInLocoDTO, 
			String codStatoTipoDocIndex,
			BigDecimal idTipoModello)
	throws Exception {

		checkListInLocoDTO.setIdChecklist(saveCheckListDataModel(
				bytesModuloPdf, null, null, null, null,idTipoModello));


		logger.info("createChecklist, calling documentoManager.creaDocumento()...,codStatoTipoDocIndex :"+codStatoTipoDocIndex);
		PbandiTDocumentoIndexVO documentoIndexVO = documentoManager
		.creaDocumento(idUtente, checkListInLocoDTO,codStatoTipoDocIndex,null,null,null);

		PbandiTChecklistVO checklistVO = new PbandiTChecklistVO();
		checklistVO.setIdChecklist(checkListInLocoDTO.getIdChecklist());
		checklistVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
		genericDAO.update(checklistVO);
		return checklistVO  ;
	}


	private PbandiTChecklistVO createChecklistHtml(Long idUtente, 
			byte[] bytesModuloPdf,
			CheckListInLocoDTO checkListInLocoDTO, 
			String codStatoTipoDocIndex,
			BigDecimal idTipoModello,
			String soggettoControllore,
			String dataControllo,
			String referenteBeneficiario,
			String flagIrregolarita,
			String importoIrregolarita)
	throws Exception {

		logger.begin();
		PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
		if(ObjectUtil.isEmpty(dataControllo)){
			logger.info("dataControllo null , sysdate:  "+DateUtil.getSysdate());
			pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getSysdate()));
		}
		else{
			logger.info("dataControllo:" +dataControllo+" ,DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo) "+DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));
			pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));
		}

		pbandiTChecklistVO.setFlagIrregolarita(flagIrregolarita);
		pbandiTChecklistVO.setReferenteBeneficiario(referenteBeneficiario);
		pbandiTChecklistVO.setSoggettoControllore(soggettoControllore);
		pbandiTChecklistVO.setImportoIrregolarita(NumberUtil.toBigDecimalFromItalianFormat(importoIrregolarita));


		pbandiTChecklistVO = genericDAO.insert(pbandiTChecklistVO);

		checkListInLocoDTO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
		logger.info("createChecklist, calling documentoManager.creaDocumento()...,codStatoTipoDocIndex :"+codStatoTipoDocIndex);
//		PbandiTDocumentoIndexVO documentoIndexVO = documentoManager
//		.creaDocumento(idUtente, checkListInLocoDTO,codStatoTipoDocIndex,null,null,null);
//		if(checkListInLocoDTO.getBytesModuloPdf() !=null){
//			logger.info("checkListInLocoDTO.getBytesModuloPdf().length="+checkListInLocoDTO.getBytesModuloPdf().length);
//		}
		

		// PK salvo su Storage
		
		BigDecimal idTarget = pbandiTChecklistVO.getIdChecklist(); // idFolder
		logger.info("idTarget="+idTarget);
		BigDecimal idTipoDocIndex = decodificheDAOImpl.idDaDescrizione("PBANDI_C_TIPO_DOCUMENTO_INDEX", "ID_TIPO_DOCUMENTO_INDEX", "DESC_BREVE_TIPO_DOC_INDEX", it.csi.pbandi.pbservizit.util.Constants.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);
		logger.info("idTipoDocIndex="+idTipoDocIndex);
		BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", it.csi.pbandi.pbservizit.util.Constants.ENTITA_C_CHECKLIST); 
		logger.info("idEntita="+idEntita);
	
		logger.info("nome file="+documentoManager.getNomeFile(checkListInLocoDTO));
		checkListInLocoDTO.setNomeFile(documentoManager.getNomeFile(checkListInLocoDTO));
		
		it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO vo = new it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO();
		vo.setIdEntita(idEntita);
		vo.setIdTarget(idTarget);
		vo.setIdTipoDocumentoIndex(idTipoDocIndex);
		vo.setIdUtenteIns(new BigDecimal(idUtente));
		vo.setNomeFile(checkListInLocoDTO.getNomeFile());
		//vo.setRepository(it.csi.pbandi.pbservizit.util.Constants.COD_TIPO_DOCUMENTO_INDEX_ARCHIVIO_FILE); //AF ??
		vo.setRepository("CLIL"); //it.csi.pbandi.pbservizit.util.Constants.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO)
		
		// Salva il file su file system e inserisce un record nella PBANDI_T_DOCUMENTO_INDEX.
		boolean esitoSalva = documentiFSManager.salvaFile(checkListInLocoDTO.getBytesModuloPdf(), vo);
		if (!esitoSalva) {
			logger.error("Errore in documentiFSManager.salvaFile()");
			//throw new DaoException("Salvataggio del file fallito.");
			// Restituisce in output l'esito del salvataggio.
//			EsitoInsertFiles fileResult = new EsitoInsertFiles();
//			fileResult.setEsito(false);
//			fileResult.setMessaggio("Salvataggio del file fallito.");
//			fileResult.setNomeFile(fileDTO.getNomeFile());
//			result.add(fileResult);
//			continue;
		}
		

		BigDecimal idDocIndex = vo.getIdDocumentoIndex();
		logger.debug("idDocIndex="+idDocIndex);
		
		
		PbandiTChecklistVO checklistVO = new PbandiTChecklistVO();
		checklistVO.setIdChecklist(checkListInLocoDTO.getIdChecklist());

		//		checklistVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());  //PK : non ho IdDocumentoIndex perche non salvo su index
		checklistVO.setIdDocumentoIndex(idDocIndex);
		genericDAO.update(checklistVO);
		logger.end();
		return checklistVO  ;
	}

	private void setEsitoWithTimeoutMessage( 
			EsitoSalvaModuloCheckListDTO esitoSalvaModuloCheckList) {
		logger.warn("Impossibile salvare, lock scaduto.");

		esitoSalvaModuloCheckList.setEsito(false);
		esitoSalvaModuloCheckList.setMessage(ErrorConstants.ERRORE_CHECKLIST_TRASCORSO_TIMEOUT);

		Long timeInMilliSeconds = documentoManager.getDurataLockInMillisecondi();

		Long timeInMinutes = timeInMilliSeconds == null ? 0L: timeInMilliSeconds / 60000L;
		esitoSalvaModuloCheckList.setParams(new String[] { beanUtil.transform(	timeInMinutes, String.class) });
	}

	public BigDecimal getVersioneIniziale() {
		return   BigDecimal.valueOf(1);
	}

	private String getCodStatoTipoDocIndex(BigDecimal idDocumentoIndex) {
		PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = getDocuIndexTipoStato(idDocumentoIndex);

		return getDecodificheManager().findDescBreve(
				PbandiDStatoTipoDocIndexVO.class,
				pbandiRDocuIndexTipoStatoVO.getIdStatoTipoDocIndex());
	}

	public boolean aggiornaStatoChecklistPerRettifica(Long idUtente,
			Long idProgetto, Set<BigDecimal> dichiarazioniDiSpesa) {

		boolean visualizzaMsgChecklistDaAggiornare = false;

		for (BigDecimal idDichiarazione : dichiarazioniDiSpesa) {
			logger.debug("Aggiorno stato per idDichiarazione "
					+ idDichiarazione);

			boolean visualizzaMsgChecklistDaAggiornare1 = false;

			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentoManager
			.getDocumentoIndexSuDb(
					new Long(idDichiarazione.longValue()),
					idProgetto,
					GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE,
					new PbandiTDichiarazioneSpesaVO()
					.getTableNameForValueObject());

			if (pbandiTDocumentoIndexVO != null) {
				BigDecimal idDocumentoIndex = pbandiTDocumentoIndexVO
				.getIdDocumentoIndex();
				logger.debug("Trovato documento index " + idDocumentoIndex);

				visualizzaMsgChecklistDaAggiornare1 = true;

				// 3) di ognuna controllo lo stato:
				// a) se definitivo lo cambio in da aggiornare,
				// b) se inviata creo una nuova versione clonandola e
				// metto lo stato da aggiornare
				PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = getDocuIndexTipoStato(idDocumentoIndex);

				String descBreveStatoTipoDocIndex = getDecodificheManager()
				.findDescBreve(
						PbandiDStatoTipoDocIndexVO.class,
						pbandiRDocuIndexTipoStatoVO
						.getIdStatoTipoDocIndex());


				documentoManager
				.forceLockDocument(
						idUtente,
						idProgetto,
						idDichiarazione.longValue(),
						PbandiTDichiarazioneSpesaVO.class,
						GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);
				if (descBreveStatoTipoDocIndex
						.equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO)) {

					try {
						PbandiRDocuIndexTipoStatoVO newValue = new PbandiRDocuIndexTipoStatoVO();
						newValue.setIdStatoTipoDocIndex(decodificheManager
								.decodeDescBreve(
										PbandiDStatoTipoDocIndexVO.class,
										GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DA_AGGIORNARE));

						genericDAO.update(pbandiRDocuIndexTipoStatoVO, newValue);
					} catch (Exception e) {
						logger.error(
								"Errore nell'aggiornamento dello stato della checklist: "
								+ e.getMessage(), e);
					}
				} else if (descBreveStatoTipoDocIndex
						.equalsIgnoreCase(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_INVIATO)) {
					logger.debug("Documento in stato inviato -> creo una nuova versione e la marco come da aggiornare.");

					// creare nuova versione!
					try {
						byte[] bytesPdf = documentoManager.caricaDocumento(
								idDocumentoIndex).getBytesDocumento();

						salvaChecklistValidazione(
								idUtente,
								idProgetto,
								bytesPdf,
								GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DA_AGGIORNARE,
								idDichiarazione.longValue());
					} catch (Exception e) {
						logger.error(
								"Errore nella creazione della nuova checklist: "
								+ e.getMessage(), e);
					}
				}

			}
			visualizzaMsgChecklistDaAggiornare = visualizzaMsgChecklistDaAggiornare
			|| visualizzaMsgChecklistDaAggiornare1;
		}


		return visualizzaMsgChecklistDaAggiornare;
	}
	
	// +Green: inizio.
	// Se il progetto è +Green:
	//  - riceve in input id DS finanziamento.
	//  - da questi ricava i corrispondenti id DS contributo.
	//  - chiama aggiornaStatoChecklistPerRettifica() passando gli id DS contributo.
	public boolean aggiornaStatoChecklistPerRettificaPiuGreen(Long idUtente,
			Long idProgetto, Set<BigDecimal> dichiarazioniDiSpesaFinanziamento) {
		
		
		// Verifica se il progetto è +Green.
		Long idProgettoContributo = null;
		try {
			DatiPiuGreenDTO datiPiuGreen = gestioneDatiGeneraliBusiness.findDatiPiuGreen(idUtente, "idFinto", idProgetto);
			if (datiPiuGreen != null && datiPiuGreen.getIdProgettoContributo() != null) {
				idProgettoContributo = datiPiuGreen.getIdProgettoContributo();
				logger.info("aggiornaStatoChecklistPerRettificaPiuGreen(): progetto "+idProgetto+" +GREEN: IdProgettoContributo = "+idProgettoContributo);
			} else {
				logger.info("aggiornaStatoChecklistPerRettificaPiuGreen(): progetto "+idProgetto+" NON +GREEN.");
				return false;
			} 
		} catch (Exception e) {
			logger.error("ERRORE in aggiornaStatoChecklistPerRettificaPiuGreen(): "+e);
			return false;
		}

		boolean esito = true;
		Set<BigDecimal> dichiarazioniDiSpesaContributo = new HashSet<BigDecimal>();
		
		// Dagli id DS finanziamento ricava i corrispettivi id DS contributo.
		for (BigDecimal idDichiarazioneFinanziamento : dichiarazioniDiSpesaFinanziamento) {
			logger.info("aggiornaStatoChecklistPerRettificaPiuGreen(): elaboro id DS finanziamento "+idDichiarazioneFinanziamento);
			PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO();
			vo.setIdDichiarazioneSpesa(idDichiarazioneFinanziamento);
			vo = genericDAO.findSingleWhere(vo);
			logger.info("aggiornaStatoChecklistPerRettificaPiuGreen(): IdDichiarazioneSpesaColl = "+vo.getIdDichiarazioneSpesaColl());
			if (vo.getIdDichiarazioneSpesaColl() != null)
				dichiarazioniDiSpesaContributo.add(vo.getIdDichiarazioneSpesaColl());			
		}
		
		// Chiama aggiornaStatoChecklistPerRettifica() passando gli id DS contributo.
		esito = aggiornaStatoChecklistPerRettifica(idUtente, idProgettoContributo, dichiarazioniDiSpesaContributo);
		logger.info("aggiornaStatoChecklistPerRettificaPiuGreen(): esito di aggiornaStatoChecklistPerRettifica() = "+esito);
		return esito;
	}

	private PbandiRDocuIndexTipoStatoVO getDocuIndexTipoStato(
			BigDecimal idDocumentoIndex) {
		PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
		pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(idDocumentoIndex);
		pbandiRDocuIndexTipoStatoVO = genericDAO
		.findSingleWhere(pbandiRDocuIndexTipoStatoVO);
		return pbandiRDocuIndexTipoStatoVO;
	}

	public boolean isInviata(
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListInLocoDTO checkListInLocoDTO) {
		return checkStatoCheckist(checkListInLocoDTO,
				GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_INVIATO);
	}

	private boolean checkStatoCheckist(Object dto, String statoTipoDocIndex) {
		logger.debug("Stato documento è: " + statoTipoDocIndex);

		boolean equals = false;
		try {
			equals = statoTipoDocIndex.equals(documentoManager
					.getStatoDocumento(dto));
		} catch (Exception e) {
			logger.debug("Impossibile leggere lo stato, assumo NON che sia "
					+ statoTipoDocIndex + ": " + e.getMessage());
		}  
		return equals;
	}

	public boolean isInviataPregressa(
			CheckListDocumentaleDTO checkListValidazioneDTO) throws Exception {
		return checkStatoCheckist(checkListValidazioneDTO,
				GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_INVIATO_PREGRESSO);
	}





	public CheckListDataModel loadCheckListDataModel(Long idUtente,String identitaIride,
			DocumentoIndexModelloVO docIndexModello,
			CheckListDataModel checkListDataModel, Long idProgetto,
			String codTipoDocIndex, BigDecimal idTarget) throws Exception {
		if (checkListDataModel == null) {
			checkListDataModel = new CheckListDataModel();
		}

		try {
			BigDecimal idProgettoB =  BigDecimal.valueOf(idProgetto );

			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(idProgettoB);
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

			PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO(pbandiTProgettoVO.getIdDomanda());

			pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO);

			BandoLineaVO bandoLineaVO = new BandoLineaVO();
			bandoLineaVO.setProgrBandoLineaIntervento(pbandiTDomandaVO.getProgrBandoLineaIntervento());
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);

			BigDecimal progrBandoLineaIntervento = bandoLineaVO.getProgrBandoLineaIntervento();

			checkListDataModel.setAssePrioritario(getAssePrioritario(progrBandoLineaIntervento));
			checkListDataModel.setLineaIntervento(getDescBreveLineaIntervento(progrBandoLineaIntervento));
			checkListDataModel.setBandoRiferimento(getBandoRiferimento(bandoLineaVO));
			checkListDataModel.setCup(pbandiTProgettoVO.getCup());
			checkListDataModel.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());
			checkListDataModel.setTitoloProgetto(pbandiTProgettoVO.getTitoloProgetto());
			checkListDataModel.setDenominazioneBeneficiario(progettoManager.getDenominazioneBeneficiario(idProgettoB));
			checkListDataModel.setCostoTotaleAmmesso(getCostoTotaleAmmesso(idProgetto));
			checkListDataModel.setContributoPubblicoConcesso(getContributoPubblicoConcesso(idProgetto));

			checkListDataModel.setTotaleSpesaRendicontataOperazione(getTotaleSpesaRendicontataOperazione(idProgetto));
			checkListDataModel.setDataControllo(getDataControllo(checkListDataModel));
			if (StringUtil.isEmpty(checkListDataModel.getFirmaResponsabile())) {
				long start= System.currentTimeMillis();
				checkListDataModel.setFirmaResponsabile(soggettoManager
						.getDenominazioneSoggettoCorrente(idUtente,identitaIride));
				logger.info("soggettoManager.getDenominazioneSoggettoCorrente executed in ms: "+(System.currentTimeMillis()-start));
			}
			final String strutturaResponsabileDefault = "Finpiemonte - Controlli di I livello";  

			if (StringUtil.isEmpty(checkListDataModel.getStrutturaResponsabile())) {
				checkListDataModel.setStrutturaResponsabile(strutturaResponsabileDefault);
			}

			if (GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE
					.equals(codTipoDocIndex)) {
				BigDecimal idDichiarazione = idTarget;
				ImportiTotaliPerDichiarazioneDiSpesaVO importiTotali = getImportiTotaliPerDichiarazione(idDichiarazione);
				checkListDataModel.setAmmontareTotaleSpesaRendicontata(beanUtil
						.transform(
								importiTotali.getTotaleImportoRendicontato(),
								String.class));
				checkListDataModel.setAmmontareTotaleSpesaValidata(beanUtil
						.transform(importiTotali.getTotaleImportoValidato(),
								String.class));
			}

			checkListDataModel.setTitoloBando(bandoLineaVO.getTitoloBando());
			checkListDataModel.setVociDiSpesa(loadVociDiSpesa(idProgettoB));



			PbandiTChecklistVO checklistVO =null;
			BigDecimal idChecklist=null;
			if (GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE
					.equals(codTipoDocIndex)) {
				logger.info("********* checklist documentale, checking if existent ");
				PbandiTDocumentoIndexVO documentoIndexVO = getDocumentoManager()
				.getDocumentoIndexSuDb(
						idTarget.longValue(),
						idProgetto,
						codTipoDocIndex,
						new PbandiTDichiarazioneSpesaVO().getTableNameForValueObject());

				if(documentoIndexVO!=null){
					checklistVO = new PbandiTChecklistVO();
					checklistVO.setIdDichiarazioneSpesa(idTarget);
					checklistVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
					logger.info("searching PbandiTChecklistVO with idDocumentoIndex: " + documentoIndexVO.getIdDocumentoIndex()
							+" and idDichiarazioneDiSpesa: "+idTarget);
					List<PbandiTChecklistVO> list = genericDAO.where(Condition.filterBy(checklistVO)).select();

					if (list.size() == 1) {
						idChecklist = list.get(0).getIdChecklist();
					}
				}else{
					logger.info("checklist DOESN'tEXIST!!! !!! ");
				}
			}else if(idTarget!=null){
				checklistVO = new PbandiTChecklistVO();
				checklistVO.setIdChecklist(idTarget);
				checklistVO = genericDAO.findSingleOrNoneWhere(checklistVO);
				if(checklistVO!=null)
					idChecklist= checklistVO.getIdChecklist();
			}


			SezioneAppalti[] sezioniAppalti = loadSezioneAppaltiInChecklistModel(
					docIndexModello, idProgetto, codTipoDocIndex, idChecklist);
			checkListDataModel.setSezioneAppalti(sezioniAppalti);

		} catch (Exception e) {
			logger.error("Lettura dati fallita: " ,e);
			throw e;
		}
		return checkListDataModel;
	}

	private SezioneAppalti[] loadSezioneAppaltiInChecklistModel(
			DocumentoIndexModelloVO docIndexModello,
			Long idProgetto, String codTipoDocIndex,
			BigDecimal idChecklist) {

		String codiceModello=docIndexModello.getCodiceModello();
		BigDecimal idTipoModello=docIndexModello.getIdTipoModello();
		List<PbandiCTemplChecklistVO> listTemplateAppaltiChecklist = getTemplateAppaltiChk(idTipoModello);
		List<CodiceDescrizioneDTO> campiEdit=new ArrayList<CodiceDescrizioneDTO>();
		for (PbandiCTemplChecklistVO template : listTemplateAppaltiChecklist) {
			CodiceDescrizioneDTO codiceDescrizioneDTO=new CodiceDescrizioneDTO();
			codiceDescrizioneDTO.setCodice(template.getCodControllo());
			codiceDescrizioneDTO.setDescrizione(template.getNomeCampoEdit());
			campiEdit.add(codiceDescrizioneDTO);
		}

		SezioneAppalti sezioneAppalti=new SezioneAppalti();

		SezioneAppaltoVO[] sezioniAppalto = genericDAO.callProcedure().getSezioniAppalto(codiceModello,BigDecimal.valueOf(idProgetto),
				codTipoDocIndex, idChecklist);
		String idAppalto="";
		TabAppalti tabAppalti =null;
		for (SezioneAppaltoVO sezioneAppaltoVO : sezioniAppalto) {
			if(!idAppalto.equals(sezioneAppaltoVO.getIdAppalto())){
				tabAppalti =new TabAppalti();
				tabAppalti.setIdAppalto(sezioneAppaltoVO.getIdAppalto());
				tabAppalti.setOggettoAppalto(sezioneAppaltoVO.getOggettoAppalto()); 
				idAppalto=sezioneAppaltoVO.getIdAppalto();
				sezioneAppalti.addTabAppalti(tabAppalti);
			}
			SezRigheAppalto sezRigheAppalto= new SezRigheAppalto();
			sezRigheAppalto.setBilancioPreventivo(sezioneAppaltoVO.getBilancioPreventivo());
			sezRigheAppalto.setCod_controllo(sezioneAppaltoVO.getCodControllo());     
			sezRigheAppalto.setDataConsegna(sezioneAppaltoVO.getDtConsegnaLavori());
			sezRigheAppalto.setDataFirma(sezioneAppaltoVO.getDtFirmaContratto());
			sezRigheAppalto.setDataGuri(sezioneAppaltoVO.getDtGuri());
			sezRigheAppalto.setDataGuue(sezioneAppaltoVO.getDtGuue());
			sezRigheAppalto.setDataInizio(sezioneAppaltoVO.getDtInizioPrevista());
			sezRigheAppalto.setDataQuotNazionali(sezioneAppaltoVO.getDtQuotNazionali());
			sezRigheAppalto.setDataWebOsservatorio(sezioneAppaltoVO.getDtWebOsservatorio());
			sezRigheAppalto.setDataWebStazAppaltante(sezioneAppaltoVO.getDtWebStazAppaltante());
			sezRigheAppalto.setDescRiga(sezioneAppaltoVO.getDescrRiga());   
			sezRigheAppalto.setImportoContratto(sezioneAppaltoVO.getImportoContratto());
			sezRigheAppalto.setNota(sezioneAppaltoVO.getNote());
			sezRigheAppalto.setProceduraGara(sezioneAppaltoVO.getDescTipologiaAggiudicazione());
			sezRigheAppalto.setValore(getValore(sezioneAppaltoVO.getFlagEsito()));
			sezRigheAppalto.setCriteriSelezione(sezioneAppaltoVO.getCriteriselezione());
			sezRigheAppalto.setDataNonContrattuali(sezioneAppaltoVO.getDatanoncontrattuali());
			sezRigheAppalto.setDataNonEseguiti(sezioneAppaltoVO.getDatanoneseguiti());
			sezRigheAppalto.setImportoNonContrattuali(sezioneAppaltoVO.getImportononcontrattuali());
			sezRigheAppalto.setImportoNonEseguiti(sezioneAppaltoVO.getImportononeseguiti());
			sezRigheAppalto.setNorma(sezioneAppaltoVO.getNorma());
			sezRigheAppalto.setNormativaRiferimento(sezioneAppaltoVO.getNormativariferimento());
			sezRigheAppalto.setSpiegazioni(sezioneAppaltoVO.getSpiegazioni ());
			sezRigheAppalto.setSupplementariAmmontare(sezioneAppaltoVO.getSupplementariammontare());
			sezRigheAppalto.setSupplementariDataConsegna(sezioneAppaltoVO.getSupplementaridataconsegna());
			sezRigheAppalto.setSupplementariDataEffettiva(sezioneAppaltoVO.getSupplementaridataeffettiva());
			sezRigheAppalto.setSupplementariDataFirma(sezioneAppaltoVO.getSupplementaridatafirma());
			sezRigheAppalto.setSupplementariGiustificazione(sezioneAppaltoVO.getSupplementarigiustificazione());
			sezRigheAppalto.setSupplementariTitolo(sezioneAppaltoVO.getSupplementarititolo());
			tabAppalti.addSezRigheAppalto(sezRigheAppalto);     
		}

		SezioneAppalti sezioniAppalti[]=new SezioneAppalti[1];
		sezioniAppalti[0]=sezioneAppalti;
		return sezioniAppalti;
	}



	public ChecklistHtmlAnagraficaDTO loadCheckListHtmlAnagraficaDTO(  
			Long idProgetto ,String codTipoDocIndex ,Long  idDocumentoIndex,
			Long  idDichiarazione ,BigDecimal idChecklist, String htmlContent 
	)  throws Exception {
		logger.begin();
		logger.info("loadCheckListHtmlAnagraficaDTO idDocumentoIndex:"+idDocumentoIndex+" , idDichiarazione: "+idDichiarazione+
				" ,idChecklist: "+idChecklist);

		ChecklistHtmlAnagraficaDTO checklistHtmlAnagraficaDTO=new ChecklistHtmlAnagraficaDTO();
		try {
			BigDecimal idProgettoB =  BigDecimal.valueOf(idProgetto );

			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(idProgettoB);
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

			PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO(pbandiTProgettoVO.getIdDomanda());

			pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO);

			BandoLineaVO bandoLineaVO = new BandoLineaVO();
			bandoLineaVO.setProgrBandoLineaIntervento(pbandiTDomandaVO.getProgrBandoLineaIntervento());
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);

			BigDecimal progrBandoLineaIntervento = bandoLineaVO.getProgrBandoLineaIntervento();
			logger.info("progrBandoLineaIntervento="+progrBandoLineaIntervento);
			
			checklistHtmlAnagraficaDTO.setAssePrioritario(getAssePrioritario(progrBandoLineaIntervento));
			checklistHtmlAnagraficaDTO.setLineaIntervento(getDescLineaIntervento(progrBandoLineaIntervento));
			checklistHtmlAnagraficaDTO.setBandoRiferimento(getBandoRiferimento(bandoLineaVO));
			checklistHtmlAnagraficaDTO.setCup(pbandiTProgettoVO.getCup());
			checklistHtmlAnagraficaDTO.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());
			checklistHtmlAnagraficaDTO.setDescrizioneProgetto(pbandiTProgettoVO.getAbstractProgetto());
			
			checklistHtmlAnagraficaDTO.setIdProcesso(bandoLineaVO.getIdProcesso());

			checklistHtmlAnagraficaDTO.setTitoloProgetto(pbandiTProgettoVO.getTitoloProgetto());
			checklistHtmlAnagraficaDTO.setTitoloBando(bandoLineaVO.getTitoloBando());

			checklistHtmlAnagraficaDTO.setDenominazioneBeneficiario(progettoManager.getDenominazioneBeneficiario(idProgettoB));
			BigDecimal totaleAmmmesso = contoEconomicoManager.getTotaleSpesaAmmmessaInRendicontazione( BigDecimal.valueOf(idProgetto)); // costo totale ammesso
			logger.info("totaleAmmmesso="+totaleAmmmesso);

			checklistHtmlAnagraficaDTO.setCostoTotaleAmmesso(NumberUtil.getItalianStringValue( beanUtil.transform(totaleAmmmesso, String.class)));
			checklistHtmlAnagraficaDTO.setContributoPubblicoConcesso(NumberUtil.getItalianStringValue(getContributoPubblicoConcesso(idProgetto)));//contributo pubb concesso
			String ammontareSpesaTotaleRendicontata = NumberUtil.getItalianStringValue(getTotaleSpesaRendicontataOperazione(idProgetto));
			checklistHtmlAnagraficaDTO.setTotaleSpesaRendicontataOperazione(ammontareSpesaTotaleRendicontata);

			checklistHtmlAnagraficaDTO.setAmmontareTotaleSpesaRendicontata(ammontareSpesaTotaleRendicontata);


			if (Constants.COD_TIPO_CHEKCLIST_DOCUMENTALE.equals(codTipoDocIndex) ||
				Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE.equals(codTipoDocIndex)) {		// Jira Pbandi-2859
				

				ImportiTotaliPerDichiarazioneDiSpesaVO importiTotali = getImportiTotaliPerDichiarazione(BigDecimal.valueOf(idDichiarazione));

				checklistHtmlAnagraficaDTO.setSpesaRendicontataDS(NumberUtil.getItalianStringValue(beanUtil
						.transform(importiTotali.getTotaleImportoRendicontato(),
								String.class)));

				checklistHtmlAnagraficaDTO.setSpesaValidataDS(NumberUtil.getItalianStringValue(beanUtil
						.transform(importiTotali.getTotaleImportoValidato(),
								String.class)));
			} 

			TotaleQuietanzatoValidatoProgettoVO totQuietanzatoValidato = progettoManager.getTotaliQuietanzatoValidato(idProgetto);
			BigDecimal totaleValidato = totQuietanzatoValidato.getImportoValidato() == null ? new BigDecimal(0d) : totQuietanzatoValidato.getImportoValidato();
			checklistHtmlAnagraficaDTO.setAmmontareTotaleSpesaValidata(NumberUtil.getItalianStringValue(beanUtil.transform(totaleValidato, String.class)));


			if(idDocumentoIndex!=null && idChecklist!=null){

				PbandiTChecklistVO pbandiTChecklistVO=new PbandiTChecklistVO(idChecklist);
				pbandiTChecklistVO = genericDAO.findSingleWhere(pbandiTChecklistVO);
				checklistHtmlAnagraficaDTO.setFirmaResponsabile( pbandiTChecklistVO.getSoggettoControllore());
				checklistHtmlAnagraficaDTO.setReferenteBeneficiario( pbandiTChecklistVO.getReferenteBeneficiario());
				checklistHtmlAnagraficaDTO.setDataControllo( DateUtil.getDate(pbandiTChecklistVO.getDtControllo()) );
				checklistHtmlAnagraficaDTO.setLuogoControllo( getLuogoControllo(htmlContent) );
				checklistHtmlAnagraficaDTO.setAttoConcessioneContributo(getAttoConcessioneContributo(htmlContent));


			}else{
				checklistHtmlAnagraficaDTO.setDataControllo(getDataControllo(checklistHtmlAnagraficaDTO));
				checklistHtmlAnagraficaDTO.setLuogoControllo(getLuogoControllo(checklistHtmlAnagraficaDTO));
				checklistHtmlAnagraficaDTO.setAttoConcessioneContributo(getAttoConcessioneContributo(checklistHtmlAnagraficaDTO));
			}

		} catch (Exception e) {
			logger.error("Lettura dati anagrafica progetto fallita: " ,e);
			throw e;
		}
		logger.end();
		return checklistHtmlAnagraficaDTO;
	}

	private ChecklistItemDTO getItemSubsection(org.jsoup.nodes.Element tr) {
		ChecklistItemDTO item=new ChecklistItemDTO();

		Elements codiceControllo = tr.getElementsByClass("codiceControllo");
		if(codiceControllo!=null && codiceControllo.size()>0)
			item.setCodiceControlloSubsection(codiceControllo.get(0).text());

		Elements descrizioneAttivitaDiControllo = tr.getElementsByClass("descrizioneAttivitaDiControllo");
		if(descrizioneAttivitaDiControllo!=null && descrizioneAttivitaDiControllo.size()>0)
			item.setDescrizioneAttivitaDiControlloSubsection(descrizioneAttivitaDiControllo.get(0).text());

		Elements documentazioneControllo = tr.getElementsByClass("documentazioneControllo");
		if(documentazioneControllo!=null && documentazioneControllo.size()>0)
			item.setDocumentazioneControlloSubsection(documentazioneControllo.get(0).text());


		Elements note = tr.getElementsByClass("note");
		if(note!=null && note.size()>0)
			item.setNoteSubsection(note.get(0).text());

		Elements pistaDiControllo = tr.getElementsByClass("pistaDiControllo");
		if(pistaDiControllo!=null && pistaDiControllo.size()>0)
			item.setPistaDiControlloSubsection(pistaDiControllo.get(0).text());

		return item;
	}

	private ChecklistItemDTO getItemSection(org.jsoup.nodes.Element tr) {
		ChecklistItemDTO item=new ChecklistItemDTO();

		Elements codiceControllo = tr.getElementsByClass("codiceControllo");
		if(codiceControllo!=null && codiceControllo.size()>0)
			item.setCodiceControlloSection(codiceControllo.get(0).text());

		Elements descrizioneAttivitaDiControllo = tr.getElementsByClass("descrizioneAttivitaDiControllo");
		if(descrizioneAttivitaDiControllo!=null && descrizioneAttivitaDiControllo.size()>0)
			item.setDescrizioneAttivitaDiControlloSection(descrizioneAttivitaDiControllo.get(0).text());

		Elements documentazioneControllo = tr.getElementsByClass("documentazioneControllo");
		if(documentazioneControllo!=null && documentazioneControllo.size()>0)
			item.setDocumentazioneControlloSection(documentazioneControllo.get(0).text());


		Elements note = tr.getElementsByClass("note");
		if(note!=null && note.size()>0)
			item.setNoteSection(note.get(0).text());

		Elements pistaDiControllo = tr.getElementsByClass("pistaDiControllo");
		if(pistaDiControllo!=null && pistaDiControllo.size()>0)
			item.setPistaDiControlloSection(pistaDiControllo.get(0).text());
		return item;
	}

	private ChecklistItemDTO getItemRowCheck(org.jsoup.nodes.Element tr) {
		ChecklistItemDTO item=new ChecklistItemDTO();

		Elements codiceControllo = tr.getElementsByClass("codiceControllo");
		if(codiceControllo!=null && codiceControllo.size()>0)
			item.setCodiceControllo(codiceControllo.get(0).text());

		Elements descrizioneAttivitaDiControllo = tr.getElementsByClass("descrizioneAttivitaDiControllo");
		if(descrizioneAttivitaDiControllo!=null && descrizioneAttivitaDiControllo.size()>0)
			item.setDescrizioneAttivitaDiControllo(descrizioneAttivitaDiControllo.get(0).text());

		Elements documentazioneControllo = tr.getElementsByClass("documentazioneControllo");
		if(documentazioneControllo!=null && documentazioneControllo.size()>0)
			item.setDocumentazioneControllo(documentazioneControllo.get(0).text());

		Elements esito = tr.getElementsByClass("esito");

		if(esito!=null && esito.size()>0){
			Iterator<org.jsoup.nodes.Element> iterator = esito.iterator();
			while(iterator.hasNext()){
				org.jsoup.nodes.Element td = iterator.next();
				Elements checkbox = td.children();
				if(checkbox.hasAttr("checked")){
					if(checkbox.hasClass("si"))
						item.setEsito("POS.");
					else if(checkbox.hasClass("no"))
						item.setEsito("NEG.");
					else if(checkbox.hasClass("na"))
						item.setEsito("N/A");
					break;
				}
			}

		}

		Elements note = tr.getElementsByClass("note");
		if(note!=null && note.size()>0)
			item.setNote(note.get(0).text());

		Elements pistaDiControllo = tr.getElementsByClass("pistaDiControllo");
		if(pistaDiControllo!=null && pistaDiControllo.size()>0)
			item.setPistaDiControllo(pistaDiControllo.get(0).text());


		return item;
	}


	public List<ChecklistItemDTO>   parseHtml(String html) throws ParserConfigurationException, SAXException, IOException{
		//System.out.println(html);
		List<ChecklistItemDTO> list=new ArrayList<ChecklistItemDTO>();
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		org.jsoup.select.Elements tbodys = doc.getElementsByTag("tbody");
		Iterator<org.jsoup.nodes.Element> tbodyIter = tbodys.iterator();
		while(tbodyIter.hasNext()){
			org.jsoup.nodes.Element tbody = tbodyIter.next();
			if(tbody.hasClass("toprint")){
				Elements trs = tbody.children();
				Iterator<org.jsoup.nodes.Element> trIter = trs.iterator();
				while(trIter.hasNext()){
					org.jsoup.nodes.Element tr = trIter.next();
					if(tr.hasClass("row_check")){
						if(tr.hasClass("vds")){
							list.add(getItemVds(tr));
						}else{
							list.add(getItemRowCheck(tr));
						}
					}else if(tr.hasClass("section")){
						list.add(getItemSection(tr));
					}else if(tr.hasClass("subsection")){
						list.add(getItemSubsection(tr));
					}
				}
			}
		}
		return list;
	}


	private ChecklistItemDTO getItemVds(Element tr) {
		ChecklistItemDTO item=new ChecklistItemDTO();

		Elements codiceControllo = tr.getElementsByClass("codiceControllo");
		if(codiceControllo!=null && codiceControllo.size()>0)
			item.setCodiceControllo(codiceControllo.get(0).text());
		Elements pistaDiControllo = tr.getElementsByClass("pistaDiControllo");
		if(pistaDiControllo!=null && pistaDiControllo.size()>0)
			item.setPistaDiControllo(pistaDiControllo.get(0).text());

		Elements descrizioneAttivitaDiControllo = tr.getElementsByClass("descrizioneAttivitaDiControllo");
		if(descrizioneAttivitaDiControllo!=null && descrizioneAttivitaDiControllo.size()>0){
			//Elements dt = tr.getElementsByTag("dt");
			//Elements dd = tr.getElementsByTag("dd");
			for (Element i : descrizioneAttivitaDiControllo){
				item.setDescrizioneAttivitaDiControllo(i.html());
			}
			//item.setDescrizioneAttivitaDiControllo(dt.get(0).text() /*+ "\n\n" + dds*/);
		} 
		return item;
	}

	public String injectAnagraficaProgetto(String html, ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO) {
		logger.begin();
		//logger.shallowDump(checkListHtmlAnagraficaDTO, "info");
		Element el = null;

		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		if(checkListHtmlAnagraficaDTO.getAssePrioritario()!=null)
			doc.getElementById("assePrioritario").text(checkListHtmlAnagraficaDTO.getAssePrioritario());

		if(checkListHtmlAnagraficaDTO.getLineaIntervento()!=null)
			doc.getElementById("lineaIntervento").text(checkListHtmlAnagraficaDTO.getLineaIntervento()); //azione

		if(checkListHtmlAnagraficaDTO.getBandoRiferimento()!=null)
			doc.getElementById("bandoRiferimento").text(checkListHtmlAnagraficaDTO.getBandoRiferimento());

		if(checkListHtmlAnagraficaDTO.getCodiceProgetto()!=null)
			doc.getElementById("codiceProgetto").text(checkListHtmlAnagraficaDTO.getCodiceProgetto());

		if(checkListHtmlAnagraficaDTO.getCup()!=null)
			doc.getElementById("cup").text(checkListHtmlAnagraficaDTO.getCup());

		if(checkListHtmlAnagraficaDTO.getDescrizioneProgetto()!=null && doc.getElementById("descrizioneProgetto")!=null)
			doc.getElementById("descrizioneProgetto").text(checkListHtmlAnagraficaDTO.getDescrizioneProgetto());

		if(checkListHtmlAnagraficaDTO.getTitoloProgetto()!=null)
			doc.getElementById("titoloProgetto").text(checkListHtmlAnagraficaDTO.getTitoloProgetto());


		if(checkListHtmlAnagraficaDTO.getDenominazioneBeneficiario()!=null)
			doc.getElementById("denominazioneBeneficiario").text(checkListHtmlAnagraficaDTO.getDenominazioneBeneficiario());


		if(checkListHtmlAnagraficaDTO.getCostoTotaleAmmesso()!=null){
			el = doc.getElementById("valAmmesso");
			if(el != null)
				el.text(checkListHtmlAnagraficaDTO.getCostoTotaleAmmesso());//checkListHtmlAnagraficaDTO.getAmmontareTotaleSpesaValidata()
			
		}


		if(checkListHtmlAnagraficaDTO.getContributoPubblicoConcesso()!=null){
			el = doc.getElementById("valConcesso");
			if(el != null)
				el.text(checkListHtmlAnagraficaDTO.getContributoPubblicoConcesso());
		}


		if(checkListHtmlAnagraficaDTO.getTotaleSpesaRendicontataOperazione()!=null){
			el = doc.getElementById("valRendicontato");
			if(el != null)
				el.text(checkListHtmlAnagraficaDTO.getTotaleSpesaRendicontataOperazione());
		}

		if(checkListHtmlAnagraficaDTO.getAmmontareTotaleSpesaValidata()!=null){
			if(doc.getElementById("spesaValidata") != null)
				doc.getElementById("spesaValidata").text(checkListHtmlAnagraficaDTO.getAmmontareTotaleSpesaValidata());
			else if(doc.getElementById("spesaValidataCL") != null)
				doc.getElementById("spesaValidataCL").attr("value", checkListHtmlAnagraficaDTO.getAmmontareTotaleSpesaValidata());
		}
		if(checkListHtmlAnagraficaDTO.getSpesaValidataDS() != null && doc.getElementById("spesaValidataDS") != null)
			doc.getElementById("spesaValidataDS").text(checkListHtmlAnagraficaDTO.getSpesaValidataDS());

		if(checkListHtmlAnagraficaDTO.getSpesaRendicontataDS() != null && doc.getElementById("spesaRendicontataDS") != null)
			doc.getElementById("spesaRendicontataDS").text(checkListHtmlAnagraficaDTO.getSpesaRendicontataDS());

		if(checkListHtmlAnagraficaDTO.getFirmaResponsabile()!=null){
			doc.getElementById("firmaResponsabile").attr("value",checkListHtmlAnagraficaDTO.getFirmaResponsabile());
		}
		if(checkListHtmlAnagraficaDTO.getDataControllo()!=null){
			doc.getElementById("dataControllo").attr("value",checkListHtmlAnagraficaDTO.getDataControllo());
		}
		
		if(checkListHtmlAnagraficaDTO.getOggettoAffidamento() != null){
			doc.getElementById("oggettoAffidamento").text(checkListHtmlAnagraficaDTO.getOggettoAffidamento());
		}
		
		if(checkListHtmlAnagraficaDTO.getTipologiaContratto() != null){
			doc.getElementById("tipologiaContratto").text(checkListHtmlAnagraficaDTO.getTipologiaContratto());
		}
		
		if(checkListHtmlAnagraficaDTO.getCodProcAgg() != null){
			Element elmnt = doc.getElementById("cpa");
			if(elmnt != null)
				elmnt.text(checkListHtmlAnagraficaDTO.getCodProcAgg());
		}
		
		if(checkListHtmlAnagraficaDTO.getCigProcAgg() != null){
			Element elmnt = doc.getElementById("cig");
			if(elmnt != null)
				elmnt.text(checkListHtmlAnagraficaDTO.getCigProcAgg());
		}
		
		
		html=doc.body().html();

		logger.end();
		return html;
	}




	public String  getSoggettoControllore(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element firmaResponsabile = doc.getElementById("firmaResponsabile");
		if(firmaResponsabile!=null){
			result = firmaResponsabile.attr("value");
		}

		return result;
	}

	public String  getReferenteBeneficiario(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element referenteBeneficiario = doc.getElementById("referenteBeneficiario");
		if(referenteBeneficiario!=null){
			result = referenteBeneficiario.attr("value");
		}

		return result;
	}

	public String getFlagIrregolarita(String contentHtml) {
		String result="N";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element irregolarita = doc.getElementById("descIrregolarita");
		if(irregolarita!=null){
			String text= irregolarita.text();
			logger.info("text irregolarita ---> "+text);
			if(!ObjectUtil.isEmpty(text))
				result="S";
		}
		return result;
	}

	public String  getDataControllo(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element dataControllo = doc.getElementById("dataControllo");
		if(dataControllo!=null){
			result = dataControllo.attr("value");
		}

		return result;
	}
	public String  getLuogoControllo(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element luogoControllo = doc.getElementById("luogoControllo");
		if(luogoControllo!=null){
			result = luogoControllo.attr("value");
		}

		return result;
	}
	public String  getAttoConcessioneContributo(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element attoConcessioneContributo = doc.getElementById("attoConcessioneContributo");
		if(attoConcessioneContributo!=null){
			result = attoConcessioneContributo.attr("value");
		}

		return result;
	} 


	public String  getDescIrregolarita(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element descIrregolarita = doc.getElementById("descIrregolarita");
		if(descIrregolarita!=null){
			result= descIrregolarita.text();
			logger.info("descIrregolarita ---> "+result);
		}

		return result;
	}


	public String  getImportoIrregolarita(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Element importoIrregolarita = doc.getElementById("importoIrregolarita");
		if(importoIrregolarita!=null){
			result = importoIrregolarita.attr("value");
		}

		return result;
	}

	public String  getContieneIrregolarita(String contentHtml) throws ParserConfigurationException, SAXException, IOException{
		String result= "";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		//ID -> irregSi
		//ID -->  irregNo
		Element contieneIrregolaritaSi = doc.getElementById("irregSi");
		if(contieneIrregolaritaSi!=null){
			if(contieneIrregolaritaSi.hasAttr("checked"))
				result = "SI";
			else
				result = "NO";
		}

		return result;
	}



	public EsitoSalvaModuloCheckListDTO saveChecklistValidazioneHtml(
			final java.lang.Long idUtente, java.lang.Long idProgetto,
			final String codStatoTipoDocIndex,
			final java.lang.Long idDichiarazione,
			ChecklistHtmlDTO checklistHtmlDTO ) throws Exception {
		logger.begin();
		logger.info("idDichiarazione "+idDichiarazione
				+" ,idProgetto:"+idProgetto
				+" ,idUtente:"+idUtente
				+" ,codStatoTipoDocIndex:"+codStatoTipoDocIndex
				+" ,COD_TIPO_DOCUMENTO_INDEX: CL " );

		String codTipoDocIndex = GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE;
		final PbandiTDocumentoIndexVO documentoIndexVO = getDocumentoManager()
		.getDocumentoIndexSuDb(
				idDichiarazione,
				idProgetto,
				codTipoDocIndex,
				new PbandiTDichiarazioneSpesaVO().getTableNameForValueObject());

		final String contentHtml = checklistHtmlDTO.getContentHtml();

		BigDecimal idChecklist = null;

		if(documentoIndexVO!=null){
			PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
			pbandiTChecklistVO.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazione));
			pbandiTChecklistVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());

			List<PbandiTChecklistVO> list = genericDAO.where(Condition.filterBy(pbandiTChecklistVO)).select();

			if (list.size() == 1) {
				idChecklist = list.get(0).getIdChecklist();
			}
			logger.info("idChecklist : "+idChecklist);
			
		}

		Boolean isProgettoSif = progettoManager.isProgettoSIF(idProgetto);
		
		BigDecimal idTipoDocumentoIndexCLIL = decodificheManager
		.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);
		BigDecimal idModello=documentoManager.getIdModello(BigDecimal.valueOf(idProgetto), idTipoDocumentoIndexCLIL);
		logger.info("idModello ----------->  "+idModello);
		byte[] jasperModel = null;
		try {
			jasperModel = pbandiChecklistDAO
			.getModelloJasperChecklist(idModello);

		} catch (Exception e) {
			logger.error("Lettura modello (id:"+idModello+") fallita per idProgetto "+idProgetto, e);
			throw new ChecklistHtmlException("Modello non configurato");
		}


		final byte[] pdfBytes = getChecklistPdfBytes ( idProgetto,
				GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE,
				checklistHtmlDTO,idDichiarazione,idChecklist, isProgettoSif, jasperModel);
		
		logger.info("file trasformato in pdf: "+pdfBytes);

		Class<PbandiTDichiarazioneSpesaVO> voClass = PbandiTDichiarazioneSpesaVO.class;


		EsitoSalvaModuloCheckListDTO esito = new EsitoSalvaModuloCheckListDTO();

		final CheckListDocumentaleDTO checkListValidazioneDTO = new CheckListDocumentaleDTO();

		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

		checkListValidazioneDTO.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());

		checkListValidazioneDTO.setBytesModuloPdf(pdfBytes);
		checkListValidazioneDTO.setDataChiusura(new Date());
		checkListValidazioneDTO.setIdDichiarazioneDiSpesa(BigDecimal.valueOf(idDichiarazione));
		checkListValidazioneDTO.setIdProgetto(BigDecimal.valueOf(idProgetto));


		final String dataControllo= getDataControllo(contentHtml);
		logger.info("DATA CONTROLLO ---> " + dataControllo);
		final String soggettoControllore=  getSoggettoControllore(contentHtml);
		final String flaIrregolarita =  getFlagIrregolarita(contentHtml);
		final String referenteBeneficiario=  getReferenteBeneficiario(contentHtml);
		final String importoIrregolarita = getImportoIrregolarita(contentHtml);

		final DocumentoIndexModelloVO  identificativoModello = caricaIdentificativoModello(pbandiTProgettoVO.getIdProgetto().longValue(),"CL");

		if (isNull(documentoIndexVO)) {
			try {
				checkListValidazioneDTO.setVersione(getVersioneIniziale());

				logger.info("saving docIndex on db and index.....");
				PbandiTDocumentoIndexVO newDocumentoIndexVO = documentoManager
				.creaDocumento(idUtente, checkListValidazioneDTO,codStatoTipoDocIndex,null,null,null);

				logger.info("newDocumentoIndexVO.getIdDocumentoIndex(): "+newDocumentoIndexVO.getIdDocumentoIndex());
				salvaDatiChecklistDocumentaleHtml(pdfBytes, 
						BigDecimal.valueOf(idDichiarazione),
						newDocumentoIndexVO,
						newDocumentoIndexVO.getIdDocumentoIndex(),
						identificativoModello.getIdTipoModello(),
						soggettoControllore,
						dataControllo,
						referenteBeneficiario,
						flaIrregolarita,
						importoIrregolarita,
						contentHtml,
						codStatoTipoDocIndex,
						null);
				esito.setEsito(true);
				esito.setMessage(MessaggiConstants.MSG_CHECKLIST_SALVATA);
				esito.setIdDocumentoIndex(newDocumentoIndexVO.getIdDocumentoIndex().longValue());

			} catch (Exception e) {
				logger.debug("Impossibile salvare la checklist documentale : " + e.getMessage());
				throw e;
			}
		} else {
			logger.info(" DOCUMENTO INDEX != NULL ");
			
			doWithinLock(esito, new OperationWithinLock() {

				public BigDecimal run() throws Exception {
					BigDecimal idDocumentoIndexPrecedente = documentoIndexVO.getIdDocumentoIndex();
					PbandiTDocumentoIndexVO newDocumentoIndexVO = documentoIndexVO;
					String oldCodStatoTipoDocIndex = getCodStatoTipoDocIndex(idDocumentoIndexPrecedente);
					logger.info(" COD STATO TIPO DOC INDEX = [" + codStatoTipoDocIndex + "], OLD COD STATO TIPO DOC INDEX = [" + oldCodStatoTipoDocIndex +"]");

					if (GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_INVIATO.equalsIgnoreCase(oldCodStatoTipoDocIndex) 
							|| (GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(codStatoTipoDocIndex) 
									&& GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(oldCodStatoTipoDocIndex)) 
							|| GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DA_AGGIORNARE.equalsIgnoreCase(oldCodStatoTipoDocIndex)) {
						// SALVO NUOVA VERSIONE
						logger.info(" SALVO NUOVA VERSIONE ");
						checkListValidazioneDTO.setVersione(NumberUtil.sum(documentoIndexVO.getVersione(), new BigDecimal(1)));
						newDocumentoIndexVO = documentoManager.creaDocumento(
								idUtente, checkListValidazioneDTO, codStatoTipoDocIndex,null,null,null);
					} else {
						// SALVO SULLA VERSIONE CORRENTE
						logger.info(" SALVO SULLA VERSIONE CORRENTE ");
						checkListValidazioneDTO.setNomeFile(documentoIndexVO
								.getNomeFile());
						checkListValidazioneDTO.setUid(documentoIndexVO
								.getUuidNodo());

						documentoManager.aggiornaDocumento(
								checkListValidazioneDTO, idUtente,
								documentoIndexVO, codStatoTipoDocIndex);
					}
					salvaDatiChecklistDocumentaleHtml(pdfBytes, 
							BigDecimal.valueOf(idDichiarazione),
							newDocumentoIndexVO,
							newDocumentoIndexVO.getIdDocumentoIndex(),
							identificativoModello.getIdTipoModello(),
							soggettoControllore,
							dataControllo,
							referenteBeneficiario,
							flaIrregolarita,
							importoIrregolarita,
							contentHtml,
							codStatoTipoDocIndex,
							oldCodStatoTipoDocIndex);
					return newDocumentoIndexVO.getIdDocumentoIndex();

				}
			}, idUtente, BigDecimal.valueOf(idProgetto), BigDecimal.valueOf(idDichiarazione), voClass, codTipoDocIndex);
		}
		logger.end();
		return esito;
	}

	/*  */

	public String injectVociDiSpesa(String html, VoceDiSpesaConAmmessoVO [] vociDiSpesaConAmmesso) {
		logger.info("\n\n\ninjectVociDiSpesa");
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Element elementVds = doc.getElementById("vociDiSpesa");
		int cont=1;
		if(elementVds!=null  ){
			Elements tds = elementVds.children();

			if(!ObjectUtil.isEmpty(tds)){
				String codiceControllo="";
				for (Iterator iterator = tds.iterator(); iterator.hasNext();) {
					Element td = (Element) iterator.next();
					if(td.hasClass("codiceControllo")){
						codiceControllo=td.text();
						break;
					}
				}

				if(!ObjectUtil.isEmpty(vociDiSpesaConAmmesso)){
					StringBuilder tr=new StringBuilder();

					for(int n = 0; n < vociDiSpesaConAmmesso.length; n++ ) {
						VoceDiSpesaConAmmessoVO voceDiSpesa = (VoceDiSpesaConAmmessoVO) vociDiSpesaConAmmesso[n];

						if(voceDiSpesa.getIdVoceDiSpesaPadre() == null){
							tr.append("<tr class=\"row_check vds\">");
							tr.append("<td class=\"codiceControllo\">"+codiceControllo+"."+(cont++)+"</td>");
							tr.append("<td class=\"pistaDiControllo\"></td>");
							tr.append("<td colspan=\"6\" class=\"descrizioneAttivitaDiControllo\"><dl><dt>"+voceDiSpesa.getDescVoceDiSpesa()+"</dt>");

						}else{

							tr.append("<dd>"+ "  \n\n" + voceDiSpesa.getDescVoceDiSpesa()+"</dd>");
						}
					}
					elementVds.after(tr.toString());
				}
				elementVds.after("</dl>");
				elementVds.after("</td>");
				elementVds.after("</tr>");
			} 
			elementVds.remove();
		}
		html=doc.body().html();

		return html;
	}




	public byte[] getChecklistPdfBytes( Long idProgetto,String tipoDocIndex,
			it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO checkListHtmlDTO,
			Long idDichiarazione,
			BigDecimal idChecklist,
			Boolean isSif,
			byte[] jasperModel
	) {
		logger.begin();
		byte[] pdfBytes = null;
		try{
//			PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
			List<ChecklistItemDTO> items = parseHtml(checkListHtmlDTO.getContentHtml());
			String descIrregolarita = getDescIrregolarita(checkListHtmlDTO.getContentHtml());
			String importoIrregolarita = getImportoIrregolarita(checkListHtmlDTO.getContentHtml());
			String flagIrregolarita = getContieneIrregolarita(checkListHtmlDTO.getContentHtml());
			ChecklistHtmlIrregolaritaDTO checklistHtmlIrregolaritaDTO = new ChecklistHtmlIrregolaritaDTO();
			checklistHtmlIrregolaritaDTO.setDescIrregolarita(descIrregolarita);
			checklistHtmlIrregolaritaDTO.setImportoIrregolarita(importoIrregolarita);
			checklistHtmlIrregolaritaDTO.setFlagIrregolarita(flagIrregolarita);
			ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = loadCheckListHtmlAnagraficaDTO(idProgetto,tipoDocIndex,
					checkListHtmlDTO.getIdDocumentoIndex(),idDichiarazione ,idChecklist, checkListHtmlDTO.getContentHtml());
			logger.info(" ++++calling reportManager.createChecklist ++++");

			checkListHtmlAnagraficaDTO.setFirmaResponsabile(getSoggettoControllore(checkListHtmlDTO.getContentHtml()));
			checkListHtmlAnagraficaDTO.setAttoConcessioneContributo(getAttoConcessioneContributo(checkListHtmlDTO.getContentHtml()));
			checkListHtmlAnagraficaDTO.setReferenteBeneficiario(getReferenteBeneficiario(checkListHtmlDTO.getContentHtml()));
			checkListHtmlAnagraficaDTO.setLuogoControllo( getLuogoControllo(checkListHtmlDTO.getContentHtml()) );
			checkListHtmlAnagraficaDTO.setIdDichiarazione(String.valueOf(idDichiarazione));
			checkListHtmlAnagraficaDTO.setDataControllo(getDataControllo(checkListHtmlDTO.getContentHtml()));
			Map<String, String> parametersExtra = getJasperParametersFromHtml(checkListHtmlDTO.getContentHtml());

			pdfBytes = getReportManager().createChecklist(tipoDocIndex, checkListHtmlAnagraficaDTO, items, checklistHtmlIrregolaritaDTO, isSif, jasperModel, parametersExtra);
		} catch (Exception e) {
			logger.error("Errore getPdfBytes ",e);
		}
		return pdfBytes;
	}
	
	private Map<String, String> getJasperParametersFromHtml(String html) {
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		//Elements elements = doc.select("input[id^=PBAN_]");
		Elements elements = doc.select("[id^=PBAN_]");

		Map<String, String> hashMap = new HashMap<String, String>();
		
		for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
			Element el = (Element) iterator.next();
			String nomeKey = el.attr("id");
			nomeKey = nomeKey.substring(("PBAN_".length()));
			String value = null;
			if(el.tagName().equalsIgnoreCase("input")){
				value = el.val();
			}else{
				value = el.text();
			}
			hashMap.put(nomeKey, value);
		}
		
		return hashMap;
	}

	private Element trovaElemento(String nome, org.jsoup.nodes.Document doc) throws Exception {
		logger.info("trovaElemento(): cerco "+nome);
		Element elem = doc.getElementById(nome);
		if(elem == null){
			throw new Exception("ERRORE: elemento "+nome+" non trovato.");
		}
		return elem;
	}
	
	// Se fase = 2, l'istruttore ha selezionato un esito definitivo.
	private boolean isEsitoDefinitivo(BigDecimal fase) {
		if (fase == null)
			return false;		
		return (fase.intValue() == it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_ESITO_CHECKLIST_AFFIDAMENTO_DEFINITIVO.intValue());
	}
	
	// Legge i dati degli esiti dall'html della finestra a video.
	private ArrayList<PbandiTEsitiNoteAffidamentVO> estrazioneDatiEsitiNoteAffidamenti(String html) throws Exception {
		
		logger.begin();
		
		ArrayList<PbandiTEsitiNoteAffidamentVO> listaEsiti = new ArrayList<PbandiTEsitiNoteAffidamentVO>();
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(html);
			
			// Legge i dati dell'esito parziale.
			Element checkboxEsitoParzialePositivo = trovaElemento("checkboxEsitoParzialePositivo", doc);
			Element checkboxEsitoParzialeNegativo = trovaElemento("checkboxEsitoParzialeNegativo", doc);
			Element noteEsitoParziale = trovaElemento("noteEsitoParziale", doc);
			// Jira PBANDI-2829.
			Element checkboxEsitoParzialeConRettifica = null;
			try {
				checkboxEsitoParzialeConRettifica = trovaElemento("checkboxEsitoParzialeConRettifica", doc);  
			} catch (Exception e) {
				checkboxEsitoParzialeConRettifica = null;
			}
			
			// Popola l'esito parziale.
			if (checkboxEsitoParzialePositivo.hasAttr("checked") ||
					checkboxEsitoParzialeNegativo.hasAttr("checked")) {
				Long fase = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_ESITO_CHECKLIST_AFFIDAMENTO_PARZIALE;
				PbandiTEsitiNoteAffidamentVO esitoParziale = new PbandiTEsitiNoteAffidamentVO();
				esitoParziale.setFase(BigDecimal.valueOf(fase));
				esitoParziale.setNote(noteEsitoParziale.text());
				if (checkboxEsitoParzialePositivo.hasAttr("checked"))
					esitoParziale.setEsito(checkboxEsitoParzialePositivo.attr("value"));
				if (checkboxEsitoParzialeNegativo.hasAttr("checked"))
					esitoParziale.setEsito(checkboxEsitoParzialeNegativo.attr("value"));
				// Jira PBANDI-2829.
				if (checkboxEsitoParzialeConRettifica != null && checkboxEsitoParzialeConRettifica.hasAttr("checked"))
					esitoParziale.setFlagRettifica("S");
				else
					esitoParziale.setFlagRettifica(null);				
				logger.shallowDump(esitoParziale, "info");
				listaEsiti.add(esitoParziale);
			} else {
				logger.info("Nessun esito parziale.");
			}
			
			// Legge i dati dell'esito definitivo.
			Element checkboxEsitoDefinitivoPositivo = trovaElemento("checkboxEsitoDefinitivoPositivo", doc);
			Element checkboxEsitoDefinitivoNegativo = trovaElemento("checkboxEsitoDefinitivoNegativo", doc);
			Element noteEsitoDefinitivo = trovaElemento("noteEsitoDefinitivo", doc);
			// Jira PBANDI-2829.
			Element checkboxEsitoDefinitivoConRettifica = null;
			try {
				checkboxEsitoDefinitivoConRettifica = trovaElemento("checkboxEsitoDefinitivoConRettifica", doc);  
			} catch (Exception e) {
				checkboxEsitoDefinitivoConRettifica = null;
			}
			
			// Popola l'esito definitivo.
			if (checkboxEsitoDefinitivoPositivo.hasAttr("checked") ||
					checkboxEsitoDefinitivoNegativo.hasAttr("checked")) {
				Long fase = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_ESITO_CHECKLIST_AFFIDAMENTO_DEFINITIVO;
				PbandiTEsitiNoteAffidamentVO esitoDefinitivo = new PbandiTEsitiNoteAffidamentVO();
				esitoDefinitivo.setFase(BigDecimal.valueOf(fase));
				esitoDefinitivo.setNote(noteEsitoDefinitivo.text());
				if (checkboxEsitoDefinitivoPositivo.hasAttr("checked"))
					esitoDefinitivo.setEsito(checkboxEsitoDefinitivoPositivo.attr("value"));
				if (checkboxEsitoDefinitivoNegativo.hasAttr("checked"))
					esitoDefinitivo.setEsito(checkboxEsitoDefinitivoNegativo.attr("value"));
				// Jira PBANDI-2829.				
				if (checkboxEsitoDefinitivoConRettifica != null && checkboxEsitoDefinitivoConRettifica.hasAttr("checked"))
					esitoDefinitivo.setFlagRettifica("S");
				else
					esitoDefinitivo.setFlagRettifica(null);				
				logger.shallowDump(esitoDefinitivo, "info");
				listaEsiti.add(esitoDefinitivo);
			} else {
				logger.info("Nessun esito definitivo.");
			}			
		} catch (Exception e) {
			throw new Exception("Errore nella lettura degli esiti.");
		}
		logger.end();
		return listaEsiti;
	}
	
	// Jira PBANDI-2773: aggiunto isRichiestaIntegrazione e noteRichiestaIntegrazione.
	public EsitoSalvaModuloCheckListDTO saveChecklistAffidamentoValidazioneHtml(
			final java.lang.Long idUtente, java.lang.Long idProgetto,
			final String codStatoTipoDocIndex,
			final java.lang.Long idAffidamento,
			ChecklistAffidamentoHtmlDTO checklistHtmlDTO,
			final boolean isRichiestaIntegrazione,
			final String noteRichiestaIntegrazione) throws Exception {
		logger.begin();
		logger.info("idAffidamento "+idAffidamento
				+" ,idProgetto:"+idProgetto
				+" ,idUtente:"+idUtente
				+" ,codStatoTipoDocIndex:"+codStatoTipoDocIndex
				+" ,COD_TIPO_DOCUMENTO_INDEX: CLA " );
		
		// Estrae dall'html i dati relativi agli esiti e popola una lista.
		// l'istruttore può aver indicato:
		//  - solo un esito parziale
		//  - solo un esito definititvo
		//  - entrambi gli esiti
		final ArrayList<PbandiTEsitiNoteAffidamentVO> listaEsiti = estrazioneDatiEsitiNoteAffidamenti(checklistHtmlDTO.getContentHtml());
		
		
		String codTipoDocIndex = Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE;		// Jira Pbandi-2859
		logger.debug("codTipoDocIndex="+codTipoDocIndex);
		
		// Leggo dalla PBANDI_T_DOCUMENTO_INDEX  
		final PbandiTDocumentoIndexVO documentoIndexVO = getDocumentoManager()
					.getDocumentoIndexSuDb(	idAffidamento,
											idProgetto,
											codTipoDocIndex,
											new PbandiTAppaltoVO().getTableNameForValueObject());

		final String contentHtml = checklistHtmlDTO.getContentHtml();

		BigDecimal idChecklist = null;

		if(documentoIndexVO!=null){
			
			logger.info("trovato documentoIndexVO.getIdDocumentoIndex="+documentoIndexVO.getIdDocumentoIndex());
			logger.info("trovato documentoIndexVO.getNomeDocumento="+documentoIndexVO.getNomeDocumento());
			
			CheckListAppaltoVO checkListAppaltoVO = new CheckListAppaltoVO();
			checkListAppaltoVO.setIdAppalto(new BigDecimal(idAffidamento));
			checkListAppaltoVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());

			// PK legge da PBANDI_T_APPALTO_CHECKLIST
			List<CheckListAppaltoVO> list = genericDAO.where(Condition.filterBy(checkListAppaltoVO)).select();

			if (list.size() == 1) {
				idChecklist = list.get(0).getIdChecklist();
			}
			logger.info("idChecklist : "+idChecklist);
			
		}

		// genero il PDF
		// PK recupera informazioni da 25 tabelle tra cui PBANDI_T_PROGETTO, PBANDI_T_DOMANDA, PBANDI_T_BANDO, PBANDI_T_SOGGETTO
		final byte[] pdfBytes = getChecklistAffidamentoPdfBytes ( idProgetto,
				Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE,		// Jira Pbandi-2859
				checklistHtmlDTO,idAffidamento,idChecklist, listaEsiti);
		
		if (pdfBytes == null || pdfBytes.length == 0)
			throw new Exception("File pdf vuoto.");
		logger.info("file trasformato in pdf: length = "+pdfBytes.length);

		Class<PbandiTAppaltoVO> voClass = PbandiTAppaltoVO.class;

		EsitoSalvaModuloCheckListDTO esito = new EsitoSalvaModuloCheckListDTO();

		final CheckListAffidamentoDocumentaleDTO checkListValidazioneDTO = new CheckListAffidamentoDocumentaleDTO();

		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

		checkListValidazioneDTO.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());

		checkListValidazioneDTO.setBytesModuloPdf(pdfBytes);
		checkListValidazioneDTO.setDataChiusura(new Date());
		checkListValidazioneDTO.setIdAppalto(new BigDecimal(idAffidamento));
		checkListValidazioneDTO.setIdProgetto(BigDecimal.valueOf(idProgetto));

		final String dataControllo= getDataControllo(contentHtml);
		logger.info("dataControllo="+dataControllo);
		
		final String soggettoControllore=  getSoggettoControllore(contentHtml);
		logger.info("soggettoControllore="+soggettoControllore);
		
		//final String flaIrregolarita =  getFlagIrregolarita(contentHtml);
		final String referenteBeneficiario=  getReferenteBeneficiario(contentHtml);
		logger.info("referenteBeneficiario="+referenteBeneficiario);
		
		final DocumentoIndexModelloVO  identificativoModello = 
			caricaIdentificativoModello(pbandiTProgettoVO.getIdProgetto().longValue(),
					Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE);		// Jira Pbandi-2859

		if (isNull(documentoIndexVO)) {
			try {
				checkListValidazioneDTO.setVersione(getVersioneIniziale());
				
				checkListValidazioneDTO.setNomeFile(creaNomeFileChecklistAffidamentoDocumentale(checkListValidazioneDTO, listaEsiti));

				logger.info("saving docIndex on db and index.....");
//				PbandiTDocumentoIndexVO newDocumentoIndexVO = documentoManager
//							.creaDocumento(idUtente, checkListValidazioneDTO,codStatoTipoDocIndex,null,null,null);

				PbandiTDocumentoIndexVO newDocumentoIndexVO = creaDocumentoCL(
						idUtente, checkListValidazioneDTO, codStatoTipoDocIndex, codTipoDocIndex);
				
				logger.info("newDocumentoIndexVO.getIdDocumentoIndex(): "+newDocumentoIndexVO.getIdDocumentoIndex());
				logger.info("newDocumentoIndexVO.getNomeDocumento(): "+newDocumentoIndexVO.getNomeDocumento());
				
				salvaDatiChecklistAffidamentoDocumentaleHtml(pdfBytes, 
						BigDecimal.valueOf(idAffidamento),
						newDocumentoIndexVO,
						newDocumentoIndexVO.getIdDocumentoIndex(),
						identificativoModello.getIdTipoModello(),
						soggettoControllore,
						dataControllo,
						referenteBeneficiario,
						contentHtml,
						codStatoTipoDocIndex,
						null,
						listaEsiti, idUtente, 
						isRichiestaIntegrazione,
						noteRichiestaIntegrazione);
				esito.setEsito(true);
				esito.setMessage(MessaggiConstants.MSG_CHECKLIST_SALVATA);
				esito.setIdDocumentoIndex(newDocumentoIndexVO.getIdDocumentoIndex().longValue());

			} catch (Exception e) {
				logger.error("Impossibile salvare la checklist documentale : " + e.getMessage());
				throw e;
			}
		} else {
			logger.info(" DOCUMENTO INDEX != NULL ");
			
			doWithinLock(esito, new OperationWithinLock() {

				public BigDecimal run() throws Exception {
					logger.begin();
					BigDecimal idDocumentoIndexPrecedente = documentoIndexVO.getIdDocumentoIndex();
					PbandiTDocumentoIndexVO newDocumentoIndexVO = documentoIndexVO;
					String oldCodStatoTipoDocIndex = getCodStatoTipoDocIndex(idDocumentoIndexPrecedente);
					logger.info(" COD STATO TIPO DOC INDEX : " + codStatoTipoDocIndex + ", OLD COD STATO TIPO DOC INDEX " + oldCodStatoTipoDocIndex);

					// Alex: 05/02/2020: modifico l'if poichè, come segnalato da Carla,
					// se la checklist vecchia è definitiva e la nuova è bozza, viene sovrescritta la vecchia definitiva.
					// Ora genero una nuova versione se la checklist vecchia e definitiva, indipendentemente dalla nuova.
					// Lo stato INVIATO che vuol dire? nel dubbio lo lascio.
					/*
					if (GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_INVIATO.equalsIgnoreCase(oldCodStatoTipoDocIndex) 
							|| (GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(codStatoTipoDocIndex) 
									&& GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(oldCodStatoTipoDocIndex))) {
					*/
					if (GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_INVIATO.equalsIgnoreCase(oldCodStatoTipoDocIndex) 
						|| GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(oldCodStatoTipoDocIndex)) {
						// SALVO NUOVA VERSIONE
						logger.info(" SALVO NUOVA VERSIONE ");
						
						checkListValidazioneDTO.setVersione(NumberUtil.sum(
								documentoIndexVO.getVersione(), new BigDecimal(1)));
						
						checkListValidazioneDTO.setNomeFile(creaNomeFileChecklistAffidamentoDocumentale(checkListValidazioneDTO, listaEsiti));

//						newDocumentoIndexVO = documentoManager.creaDocumento(
//								idUtente, checkListValidazioneDTO,
//								codStatoTipoDocIndex,null,null,null);

						newDocumentoIndexVO = creaDocumentoCL(
								idUtente, checkListValidazioneDTO, codStatoTipoDocIndex, codTipoDocIndex);

						// TODO PK : devo assegnare esito.setEsito(true); ????? dove ????

					} else {
						// SALVO SULLA VERSIONE CORRENTE
						logger.info(" SALVO SULLA VERSIONE CORRENTE ");
						
						checkListValidazioneDTO.setUid(documentoIndexVO.getUuidNodo());
						
						// checkListValidazioneDTO.setNomeFile(documentoIndexVO.getNomeFile());
						checkListValidazioneDTO.setVersione(documentoIndexVO.getVersione());	// serve per creare il nome del pdf.
						String nomeFile = creaNomeFileChecklistAffidamentoDocumentale(checkListValidazioneDTO, listaEsiti);
						logger.info(" nomefile="+nomeFile);
						
						String oldNomeDocumento = documentoIndexVO.getNomeDocumento(); // lo passo per elminarlo dallo storage
						logger.info(" oldNomeDocumento="+oldNomeDocumento);

						checkListValidazioneDTO.setNomeFile(nomeFile);
						documentoIndexVO.setNomeFile(nomeFile);				// altrimenti salva il vecchio nome.
						
						// Nome univoco con cui il file verra' salvato su File System.
						String newNomeDocumento = nomeFile.replaceFirst("\\.", "_"+documentoIndexVO.getIdDocumentoIndex().longValue()+".");
						logger.info(" newNomeDocumento="+newNomeDocumento);
						documentoIndexVO.setNomeDocumento(newNomeDocumento); //lo setto per aggiornarlo nella PBANDI_T_DOCUMENTO_INDEX
						
						//TODO : PK : non devo aggiornare il doc su index ma sullo storage
//						documentoManager.aggiornaDocumento(
//								checkListValidazioneDTO, idUtente,
//								documentoIndexVO, codStatoTipoDocIndex);

						documentiFSManager.aggiornaDocumento(newNomeDocumento, oldNomeDocumento, codTipoDocIndex, pdfBytes );

						documentoManager.aggiornaInfoNodoIndexSuDb(idUtente, documentoIndexVO, codStatoTipoDocIndex);
					}
					salvaDatiChecklistAffidamentoDocumentaleHtml(pdfBytes, 
							BigDecimal.valueOf(idAffidamento),
							newDocumentoIndexVO,
							newDocumentoIndexVO.getIdDocumentoIndex(),
							identificativoModello.getIdTipoModello(),
							soggettoControllore,
							dataControllo,
							referenteBeneficiario,
							contentHtml,
							codStatoTipoDocIndex,
							oldCodStatoTipoDocIndex,
							listaEsiti, idUtente, 
							isRichiestaIntegrazione, 
							noteRichiestaIntegrazione);
					return newDocumentoIndexVO.getIdDocumentoIndex();

				}
			}, idUtente, BigDecimal.valueOf(idProgetto), BigDecimal.valueOf(idAffidamento), voClass, codTipoDocIndex);
		
			
			logger.info(" esito.getMessage="+esito.getMessage());
			logger.info(" esito.getEsito="+esito.getEsito());
			logger.info(" esito.getIdDocumentoIndex="+esito.getIdDocumentoIndex());
			
		}
		logger.end();
		return esito;
	}
	
	// Calcola il nome del file della checklist documentale affidamento: 
	// "checkListAffidamento_"+idAppalto+"INT\DEF"+"_V"+versione+"_"+time+".pdf". 
	private String creaNomeFileChecklistAffidamentoDocumentale(
			CheckListAffidamentoDocumentaleDTO dto,
			ArrayList<PbandiTEsitiNoteAffidamentVO> listaEsiti) {
		String time = DateUtil.getTime(new java.util.Date(),Constants.DATEHOUR_FORMAT_PER_NOME_FILE);
		String idAppalto = ""+dto.getIdAppalto();
		String versione = (dto.getVersione() == null) ? "" : ""+dto.getVersione().intValue();
		String esito = "";
		if (listaEsiti == null) {
			esito = "";
		} else if (listaEsiti.size() == 1) {
			if (listaEsiti.get(0).getFase() != null) {
				int fase = listaEsiti.get(0).getFase().intValue();				
				if (fase == 1) {
					esito = "_INT";
				} else if (fase == 2) {
					esito = "_DEF";
				}
			}			
		} else if (listaEsiti.size() == 2) {
			esito = "_DEF";
		}
		String nomeFile = "checkListAffidamento_"+idAppalto+esito+"_V"+versione+"_"+time+".pdf";
		logger.info(" nomeFile = "+nomeFile);
		return nomeFile;
	}
	
	// Jira PBANDI-2773: aggiunto isRichiestaIntegrazione e noteRichiestaIntegrazione.
	private void salvaDatiChecklistAffidamentoDocumentaleHtml(byte[] bytesModuloPdf,
			BigDecimal idAffidamento, PbandiTDocumentoIndexVO documentoIndexVO,
			BigDecimal idDocumentoIndex,BigDecimal idTipoModello,
			String soggettoControllore,
			String dataControllo,
			String referenteBeneficiario, 
			String contentHtml,
			String codStatoTipoDocIndex,
			String oldCodStatoTipoDocIndex,
			ArrayList<PbandiTEsitiNoteAffidamentVO> listaEsiti,
			Long idUtente,
			boolean isRichiestaIntegrazione,
			String noteRichiestaIntegrazione
	) throws Exception {
		logger.begin();
		
		logger.info("idAffidamento : "+idAffidamento);
		logger.info("documentoIndexVO : "+documentoIndexVO);
		logger.info("idDocumentoIndex : "+idDocumentoIndex);
		logger.info("idTipoModello : "+idTipoModello);
		logger.info("soggettoControllore : "+soggettoControllore);
		//logger.info("contentHtml : "+contentHtml);
		logger.info("codStatoTipoDocIndex : "+codStatoTipoDocIndex);
		logger.info("oldCodStatoTipoDocIndex : "+oldCodStatoTipoDocIndex);
		logger.info("isRichiestaIntegrazione : "+isRichiestaIntegrazione);
		logger.info("noteRichiestaIntegrazione : "+noteRichiestaIntegrazione);
		
		PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
		
		CheckListAppaltoVO checkListAppaltoVO = new CheckListAppaltoVO();
		checkListAppaltoVO.setIdAppalto(idAffidamento);
		checkListAppaltoVO.setIdDocumentoIndex(idDocumentoIndex);
		
		
		List<CheckListAppaltoVO> list = genericDAO.where(Condition.filterBy(checkListAppaltoVO)).select();

		BigDecimal idChecklist = null;
		if (list.size() == 1) {
			idChecklist = list.get(0).getIdChecklist();
		}
		logger.info("idChecklist : "+idChecklist);
		
		if(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(codStatoTipoDocIndex) && GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(oldCodStatoTipoDocIndex))
			pbandiTChecklistVO.setIdChecklist(null);
		else
			pbandiTChecklistVO.setIdChecklist(idChecklist);
		
		logger.info("idChecklist : "+pbandiTChecklistVO.getIdChecklist());

		if(dataControllo==null)
			pbandiTChecklistVO.setDtControllo(DateUtil.getSysdate());
		else
			pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));

		pbandiTChecklistVO.setSoggettoControllore(soggettoControllore);
		
		pbandiTChecklistVO.setFlagIrregolarita("N");

		pbandiTChecklistVO.setVersione(documentoIndexVO.getVersione());
		pbandiTChecklistVO.setIdDocumentoIndex(idDocumentoIndex);
		
		logger.info("pbandiTChecklistVO = "+pbandiTChecklistVO);
		
		pbandiTChecklistVO = genericDAO.insertOrUpdateExisting(pbandiTChecklistVO);

		PbandiTChecklistHtmlVO pbandiTChecklistHtmlVO=new PbandiTChecklistHtmlVO(pbandiTChecklistVO.getIdChecklist());
		pbandiTChecklistHtmlVO=genericDAO.findSingleOrNoneWhere(pbandiTChecklistHtmlVO);
		logger.info("pbandiTChecklistHtmlVO = "+pbandiTChecklistHtmlVO);
		
		if(pbandiTChecklistHtmlVO==null){
			getPbandiChecklistDAO().insertChecklistCompiled(contentHtml, pbandiTChecklistVO.getIdChecklist());
			
			PbandiTAppaltoChecklistVO appaltoChecklistVO = new PbandiTAppaltoChecklistVO();
			appaltoChecklistVO.setIdAppalto(idAffidamento);
			appaltoChecklistVO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
			genericDAO.insert(appaltoChecklistVO);
			
		}else{
			getPbandiChecklistDAO().updateChecklistCompiled(contentHtml, pbandiTChecklistVO.getIdChecklist());
		}
		
		// SALVO I DATI SOLO SE LO STATO DELLA CHECKLIST è DEFINITIVO
		// Salva i dati degli esiti su db e aggiorna lo stato dell'affidamento.
		if(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(codStatoTipoDocIndex))
			this.salvaEsitiNoteAffidamenti(listaEsiti, pbandiTChecklistVO.getIdChecklist(), idAffidamento, idUtente);
		
		logger.info("POST salvaEsitiNoteAffidamenti");
		
		// Jira PBANDI-2773.
		if (isRichiestaIntegrazione) {	
			gestisciRichiestaIntegrazione(idAffidamento,new BigDecimal(idUtente),noteRichiestaIntegrazione);
			
			//PK rimetto lo stato in PBANDI_R_DOCU_INDEX_TIPO_STATO a BOZZA
			if(GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO.equalsIgnoreCase(codStatoTipoDocIndex)) {
				PbandiRDocuIndexTipoStatoVO rdoc = new PbandiRDocuIndexTipoStatoVO();
				rdoc.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
				rdoc.setIdTipoDocumentoIndex(documentoIndexVO.getIdTipoDocumentoIndex());
				
				PbandiRDocuIndexTipoStatoVO rdocNew = new PbandiRDocuIndexTipoStatoVO();
				rdocNew.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
				rdocNew.setIdTipoDocumentoIndex(documentoIndexVO.getIdTipoDocumentoIndex());
				logger.info( "codStatoTipoDocIndex: "+codStatoTipoDocIndex);
				BigDecimal idStatoTipoDocIndex = decodificheManager.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class, GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA);
				logger.info( "idStatoTipoDocIndex: "+idStatoTipoDocIndex);
				rdocNew.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
				
				genericDAO.update(rdoc, rdocNew);
				logger.info( "stato della checklist aggiornato");
			}
		}
		logger.end();
	}
	
	// Jira PBANDI-2773.
	// Se la checklist (in questo caso solo bozza) è relativa ad una richiesta di integrazione:
	//  - assegna all'affidamento lo stato RICINTEGRAZ.
	//  - invia una notifica al beneficiario.
	private void gestisciRichiestaIntegrazione (BigDecimal idAffidamento, BigDecimal idUtente, String note) throws Exception {
		logger.info("gestisciRichiestaIntegrazione(): assegno lo stato RICINTEGRAZ.");
		PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
		appalto.setIdAppalto(idAffidamento);			
		appalto.setIdStatoAffidamento(new BigDecimal(Constants.ID_STATO_AFFIDAMENTO_RICINTEGRAZ));
		appalto.setDtAggiornamento(DateUtil.getSysdate());
		appalto.setIdUtenteAgg(idUtente);
		genericDAO.update(appalto);
		logger.info("gestisciRichiestaIntegrazione(): invio la notifica.");
		invioNotificaRichiestaIntefrazione(idAffidamento, idUtente, note);
	}
	
	// Jira PBANDI-2773.
	// Invia al beneficiario una notifica di richiesta integrazione per un affidamento.
	private void invioNotificaRichiestaIntefrazione (BigDecimal idAffidamento, BigDecimal idUtente, String note) throws Exception {
	
		// Tramite Appalto -> Proc Aggiudicazione, recupera l'id progetto.
		PbandiTAppaltoVO appaltoVO = new PbandiTAppaltoVO();
		appaltoVO.setIdAppalto(idAffidamento);
		appaltoVO = genericDAO.findSingleWhere(appaltoVO);
		
		PbandiTProceduraAggiudicazVO proc = new PbandiTProceduraAggiudicazVO();
		proc.setIdProceduraAggiudicaz(appaltoVO.getIdProceduraAggiudicaz());
		proc = genericDAO.findSingleWhere(proc);
		
		// Invia la notifica al beneficiario.
		String importo = NumberUtil.getStringValue(appaltoVO.getImportoContratto());
		List<MetaDataVO>metaDatas = creaParametriNotificaRichiestaIntefrazione(appaltoVO.getOggettoAppalto(), importo, note);
		
		logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
		genericDAO.callProcedure().putNotificationMetadata(metaDatas);
		
		logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
		String descrBreveTemplateNotifica=Notification.NOTIFICA_RICHIESTA_INTEGRAZIONE_AFFIDAMENTO;
		genericDAO.callProcedure().sendNotificationMessage2(proc.getIdProgetto(),descrBreveTemplateNotifica,Notification.BENEFICIARIO,idUtente.longValue(),idAffidamento.longValue());		
	}
	
	// Jira PBANDI-2773.
	private List<MetaDataVO> creaParametriNotificaRichiestaIntefrazione(String oggetto, String importo, String note) {
		
		List<MetaDataVO> metaDatas = new ArrayList<MetaDataVO>();
		
		MetaDataVO metadata1 = new MetaDataVO(); 
		metadata1.setNome(Notification.DATA_RIC_INTEGRAZ_AFFIDAMENTO);
		metadata1.setValore(DateUtil.getDate(new Date()));
		metaDatas.add(metadata1);
		
		MetaDataVO metadata2 = new MetaDataVO(); 
		metadata2.setNome(Notification.OGGETTO_AFFIDAMENTO);
		metadata2.setValore(oggetto);
		metaDatas.add(metadata2);
		
		MetaDataVO metadata3 = new MetaDataVO(); 
		metadata3.setNome(Notification.IMP_AGGIUDICATO_AFFIDAMENTO);
		metadata3.setValore(importo);
		metaDatas.add(metadata3);
		
		MetaDataVO metadata4 = new MetaDataVO(); 
		metadata4.setNome(Notification.NOTE_RIC_INTEGRAZ_AFFIDAMENTO);
		metadata4.setValore(note);
		metaDatas.add(metadata4);
		
		return metaDatas;
	}
	
	// Salva i dati degli esiti su db e aggiorna lo stato dell'affidamento.
	private void salvaEsitiNoteAffidamenti(ArrayList<PbandiTEsitiNoteAffidamentVO> listaEsiti, BigDecimal idChecklist, BigDecimal idAffidamento, Long idUtente) 
			throws Exception {		
		logger.begin();
		if (idChecklist == null) {
			logger.error("salvaEsitiNoteAffidamenti(): idChecklist nullo."); 
			throw new Exception("Impossibile salvare gli esiti.");
		}
			
		java.sql.Date oggi = DateUtil.getSysdate();
		boolean esitoDefinitivoSelezionato = false;
		
		// Cancella gli esiti precedenti associati alla checklist.
		PbandiTEsitiNoteAffidamentVO filtro = new PbandiTEsitiNoteAffidamentVO();
		filtro.setIdChecklist(idChecklist);
		genericDAO.deleteWhere(new FilterCondition<PbandiTEsitiNoteAffidamentVO>(filtro));
		
		// Inserisce gli esiti.
		for(PbandiTEsitiNoteAffidamentVO esito : listaEsiti) {
						
			if (isEsitoDefinitivo(esito.getFase()))
				esitoDefinitivoSelezionato = true;
			
			//PK il campo PBANDI_T_ESITI_NOTE_AFFIDAMENT.note e' un varchar2(4000) se supero 4000 caratteri si rompe
			// NON segnala eccezione ma non salva, mentre il messaggio e' un OK
			if (esito.getNote()!=null) {
				logger.info("esito.getNote().length()="+esito.getNote().length());
				//TODO PK : eliminare questo controllo quando PBANDI_T_ESITI_NOTE_AFFIDAMENT.note diventera' CLOB
				if(esito.getNote().length()>4000) {
					logger.error("capo note eccede i 4000 caratteri");
					throw new Exception("capo note eccede i 4000 caratteri");
				}
			}
			esito.setIdChecklist(idChecklist);
			esito.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			esito.setDataIns(oggi);
			esito = genericDAO.insert(esito);
		}
		
		// Aggiorna lo stato dell'affidamento.
		int nuovoStato;
		if (esitoDefinitivoSelezionato) {
			nuovoStato = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_STATO_AFFIDAMENTO_VDEFINITIVA;
		} else {
			nuovoStato = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_STATO_AFFIDAMENTO_VPARZIALE;
		}
		PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
		appalto.setIdAppalto(idAffidamento);			
		appalto.setIdStatoAffidamento(new BigDecimal(nuovoStato));
		appalto.setDtAggiornamento(oggi);
		appalto.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(appalto);	
		logger.end();
	}
	
	public byte[] getChecklistAffidamentoPdfBytes( Long idProgetto,String tipoDocIndex,
			ChecklistAffidamentoHtmlDTO checkListHtmlDTO,
			Long idAffidamento,
			BigDecimal idChecklist,
			ArrayList<PbandiTEsitiNoteAffidamentVO> listaEsiti
	) {
		logger.begin();
		logger.info("idProgetto="+ idProgetto);
		logger.info("tipoDocIndex="+ tipoDocIndex);
		logger.info("idAffidamento="+ idAffidamento);
		logger.info("idChecklist="+ idChecklist);
		
		byte[] pdfBytes = null;
		try{
			PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
			List<ChecklistItemDTO> items = parseHtml(checkListHtmlDTO.getContentHtml());
			
			ChecklistAffidamentoHtmlFasiDTO fasiChecklist = new ChecklistAffidamentoHtmlFasiDTO();
			
			// Popola i dati degli esiti.
			if(tipoDocIndex.equals(Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE)) {		// Jira Pbandi-2859
				String positivo = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ESITO_CHECKLIST_AFFIDAMENTO_POSITIVO;
				for (PbandiTEsitiNoteAffidamentVO esito : listaEsiti) {
					if (this.isEsitoDefinitivo(esito.getFase())) {
						fasiChecklist.setFase2(positivo.equalsIgnoreCase(esito.getEsito()));
						fasiChecklist.setNoteFase2(esito.getNote());
						fasiChecklist.setFlagRettifica2(esito.getFlagRettifica());  // Jira PBANDI-2829.
					} else {					
						fasiChecklist.setFase1(positivo.equalsIgnoreCase(esito.getEsito()));
						fasiChecklist.setNoteFase1(esito.getNote());
						fasiChecklist.setFlagRettifica1(esito.getFlagRettifica());  // Jira PBANDI-2829.
					}
				}
			}
			
			//PK : carico una marea di dati (da circa 24 tabelle...)
			ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = loadCheckListHtmlAnagraficaDTO(idProgetto,tipoDocIndex,
					checkListHtmlDTO.getIdDocumentoIndex(),idAffidamento ,idChecklist, checkListHtmlDTO.getContentHtml());
			logger.info(" ++++calling reportManager.createChecklist ++++");

			checkListHtmlAnagraficaDTO.setFirmaResponsabile(getSoggettoControllore(checkListHtmlDTO.getContentHtml()));
			checkListHtmlAnagraficaDTO.setAttoConcessioneContributo(getAttoConcessioneContributo(checkListHtmlDTO.getContentHtml()));
			checkListHtmlAnagraficaDTO.setReferenteBeneficiario(getReferenteBeneficiario(checkListHtmlDTO.getContentHtml()));
			checkListHtmlAnagraficaDTO.setLuogoControllo( getLuogoControllo(checkListHtmlDTO.getContentHtml()) );
			checkListHtmlAnagraficaDTO.setDataControllo(getDataControllo(checkListHtmlDTO.getContentHtml()));
			checkListHtmlAnagraficaDTO.setNomeChecklist(getNomeChecklist(checkListHtmlDTO.getContentHtml()));
			
			//PK leggo da PBANDI_T_APPALTO e PBANDI_T_PROCEDURA_AGGIUDICAZ
			caricaDatiAffidamento(checkListHtmlAnagraficaDTO, idAffidamento);
			
			//PK carico il template Jasper e genero il pdf
			// PK : per i bandi PSC carico altro template
			

			PbandiTBandoVO bando = new PbandiTBandoVO();
			bando.setTitoloBando(checkListHtmlAnagraficaDTO.getTitoloBando());
			bando = genericDAO.findSingleWhere(bando);
			logger.info("bando="+bando);
			
//			pdfBytes = getReportManager().createChecklistAffidamento(tipoDocIndex, checkListHtmlAnagraficaDTO, items, fasiChecklist, bando);
			pdfBytes = getReportManager().createChecklistAffidamento(tipoDocIndex, checkListHtmlAnagraficaDTO, items, fasiChecklist, isBandoPSC(bando.getIdLineaDiIntervento()));
			
		} catch (Exception e) {
			logger.error("Errore getPdfBytes ",e);
		}
		logger.end();
		return pdfBytes;
	}

	private boolean isBandoPSC(BigDecimal idLineaIntervento) {
		logger.debug("idLineaIntervento = " + idLineaIntervento );
		boolean ret = false;
		PbandiCCostantiVO ccost = new PbandiCCostantiVO();
		ccost.setAttributo("lineeintervento_PSC"); //256,5555

		String valore = null;
		try {
			valore = getGenericDAO().findSingleWhere(ccost).getValore();
			logger.debug( "lineeintervento_PSC= [" + valore + "]");
			
			if(valore!=null && valore.contains(""+idLineaIntervento)) {
				ret = true;
			}
		} catch (RecordNotFoundException e) {
			// non faccio nulla
			logger.warn("RecordNotFoundException, configurazione non trovata nella PBANDI_C_COSTANTI");
			ret = false;
		}
		logger.debug("ret="+ret);
		return ret;
	}
	
	public void caricaDatiAffidamento(ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO, Long idAffidamento) throws Exception{
		
		logger.begin();
		PbandiTAppaltoVO appalto = new PbandiTAppaltoVO();
		appalto.setIdAppalto(BigDecimal.valueOf(idAffidamento));
		appalto = genericDAO.findSingleOrNoneWhere(appalto);

		logger.debug("[CheckListManager::caricaDatiAffidamento] appalto="+appalto);
		
		if(appalto != null){ 
			checkListHtmlAnagraficaDTO.setOggettoAffidamento(appalto.getOggettoAppalto() != null ? appalto.getOggettoAppalto() : "");
			String tipologiaContratto = getDecodificheManager().findDescrizioneById(PbandiDTipoAffidamentoVO.class, appalto.getIdTipoAffidamento());
			checkListHtmlAnagraficaDTO.setTipologiaContratto(tipologiaContratto != null ? tipologiaContratto : "");
		}
		
		PbandiTProceduraAggiudicazVO proceduraAggiudicaz = new PbandiTProceduraAggiudicazVO();
		proceduraAggiudicaz.setIdProceduraAggiudicaz(appalto.getIdProceduraAggiudicaz());
		proceduraAggiudicaz = genericDAO.findSingleOrNoneWhere(proceduraAggiudicaz);
	
		if(proceduraAggiudicaz != null){
			checkListHtmlAnagraficaDTO.setCodProcAgg(proceduraAggiudicaz.getCodProcAgg());
			checkListHtmlAnagraficaDTO.setCigProcAgg(proceduraAggiudicaz.getCigProcAgg());
		}
		logger.end();
	}
	

	private String getNomeChecklist(String contentHtml) {
		String result="";

		org.jsoup.nodes.Document doc = Jsoup.parse(contentHtml);
		Elements nomeChecklist = doc.getElementsByClass("nomechecklist");
		if(nomeChecklist!=null && nomeChecklist.size()>0)
			result = nomeChecklist.get(0).text();
		
		return result;
	}
	
	
	public EsitoSalvaModuloCheckListDTO salvaChecklistAffidamentoInLoco(Long idUtente,
			PbandiTProgettoVO pbandiTProgettoVO ,
			byte[] bytesModuloPdf,
			ChecklistAffidamentoHtmlDTO checkListDTO,
			String codStatoTipoDocIndex,
			PbandiTDocumentoIndexVO documentoIndexVO, Long idAffidamento,
			BigDecimal versione)
	throws Exception {
		logger.info("salvaChecklistAffidamentoInLoco  BEGIN");
	
		EsitoSalvaModuloCheckListDTO esitoSalvaModuloManager = new EsitoSalvaModuloCheckListDTO();
		BigDecimal idDocumentoIndex = null;
		CheckListAffidamentoInLocoDTO checkListInLocoDTO = new CheckListAffidamentoInLocoDTO();
		String contentHtml = checkListDTO.getContentHtml();

//		logger.info("salvaChecklistAffidamentoInLoco contentHtml="+contentHtml);
		
		checkListInLocoDTO.setBytesModuloPdf(bytesModuloPdf);
		checkListInLocoDTO.setCodiceProgetto(pbandiTProgettoVO.getCodiceVisualizzato());
		checkListInLocoDTO.setDataInserimento(new Date());
		checkListInLocoDTO.setIdProgetto(pbandiTProgettoVO.getIdProgetto());
		checkListInLocoDTO.setIdAppalto(BigDecimal.valueOf(idAffidamento));
		checkListInLocoDTO.setIdChecklist(beanUtil.transform(checkListDTO.getIdChecklist(), BigDecimal.class));
		checkListInLocoDTO.setVersione(versione);
		
		DocumentoIndexModelloVO  identificativoModello = caricaIdentificativoModello(pbandiTProgettoVO.getIdProgetto().longValue(),
				Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO);		// Jira Pbandi-2859

		boolean esito = false;
		String soggettoControllore = getSoggettoControllore(contentHtml);
		String dataControllo = getDataControllo(contentHtml);
		String referenteBeneficiario = getReferenteBeneficiario(contentHtml);

		logger.info("documentoIndexVO  ="+documentoIndexVO);
		
		if (documentoIndexVO == null) {
			logger.info(" documentoIndexVO is null, creating new checklist with codStatoTipoDocIndex: "+codStatoTipoDocIndex );

			PbandiTChecklistVO pbandiTChecklistVO = createChecklistAffidamentoHtml (idUtente, 
					bytesModuloPdf,
					checkListInLocoDTO,
					codStatoTipoDocIndex,
					identificativoModello.getIdTipoModello(),
					soggettoControllore,
					dataControllo,
					referenteBeneficiario);
			
			checkListInLocoDTO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
			idDocumentoIndex=pbandiTChecklistVO.getIdDocumentoIndex();

			getPbandiChecklistDAO().insertChecklistCompiled(contentHtml, pbandiTChecklistVO.getIdChecklist());

			esito = true;

		} else {
			
			// Alex: 20/01/2020
			// Riga aggiunta poichè altrimenti dà errore NullObject alla riga successiva
			//           esitoSalvaModuloManager.setIdDocumentoIndex(idDocumentoIndex.longValue());
			// Spero non crei casini.
			idDocumentoIndex = documentoIndexVO.getIdDocumentoIndex();
			
			logger.info("salvaChecklistAffidamentoInLoco, modifyChecklistHtmlInLoco , idDocumentoIndex is not null : " + idDocumentoIndex+
					"\ndocumentoIndexVO.getIdTarget( idChecklist ): "+documentoIndexVO.getIdTarget());

			logger.info("salvaChecklistAffidamentoInLoco, getNomeFile="+documentoIndexVO.getNomeFile());
			logger.info("salvaChecklistAffidamentoInLoco, getNomeDocumento="+documentoIndexVO.getNomeDocumento());
			
			//documentoIndexVO.setVersione(versione);
			
			esito = modifyChecklistAffidamentoInLoco(idUtente,
					codStatoTipoDocIndex, 
					documentoIndexVO,
					esitoSalvaModuloManager,
					bytesModuloPdf, 
					checkListInLocoDTO,
					pbandiTProgettoVO.getIdProgetto().longValue(),
					identificativoModello.getIdTipoModello(),
					soggettoControllore,
					dataControllo,
					referenteBeneficiario, checkListInLocoDTO.getIdChecklist());
			
			//logger.info("\n\n\n\n\n\nupdate clob checklist in loco !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			PbandiTChecklistHtmlVO checklistVO=new PbandiTChecklistHtmlVO(checkListInLocoDTO.getIdChecklist());
			//PbandiTChecklistHtmlVO updateChecklist = new PbandiTChecklistHtmlVO();
			//updateChecklist.setHtml(contentHtml);
			//genericDAO.update(checklistVO, updateChecklist);
			// Usato al posto dell'update per non stampare tutto l'html della checklist nel log.

			logger.info("salvaChecklistAffidamentoInLoco checklistVO="+checklistVO);
			
			genericDAO.updateClob(checklistVO, "html", contentHtml);
		}

		esitoSalvaModuloManager.setEsito(esito);
		esitoSalvaModuloManager.setIdChecklist(checkListInLocoDTO.getIdChecklist().longValue());
		esitoSalvaModuloManager.setIdDocumentoIndex(idDocumentoIndex.longValue());

		return esitoSalvaModuloManager;
	}
	
	private PbandiTChecklistVO createChecklistAffidamentoHtml(Long idUtente, 
			byte[] bytesModuloPdf,
			CheckListAffidamentoInLocoDTO checkListInLocoDTO, 
			String codStatoTipoDocIndex,
			BigDecimal idTipoModello,
			String soggettoControllore,
			String dataControllo,
			String referenteBeneficiario)
	throws Exception {
		logger.begin();
		PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
		if(ObjectUtil.isEmpty(dataControllo)){
			logger.info("dataControllo null , sysdate:  "+DateUtil.getSysdate());
			pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getSysdate()));
		}
		else{
			logger.info("dataControllo:" +dataControllo+" ,DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo) "+DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));
			pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));
		}
		pbandiTChecklistVO.setReferenteBeneficiario(referenteBeneficiario);
		pbandiTChecklistVO.setSoggettoControllore(soggettoControllore);
		pbandiTChecklistVO.setVersione(checkListInLocoDTO.getVersione());
		
		pbandiTChecklistVO = genericDAO.insert(pbandiTChecklistVO);

		checkListInLocoDTO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
		
		logger.info("createChecklist, calling documentoManager.creaDocumento()...,codStatoTipoDocIndex :"+codStatoTipoDocIndex);
//		PbandiTDocumentoIndexVO documentoIndexVO = documentoManager.creaDocumento(idUtente, checkListInLocoDTO, codStatoTipoDocIndex, null,null,null);

		//PK : sostituisco l'invocaziona a DocumentoManager con DocumentiFSManager.
		PbandiTDocumentoIndexVO documentoIndexVO = documentiFSManager.creaDocumento(idUtente, checkListInLocoDTO, codStatoTipoDocIndex, null,null,null);
		logger.info("documentoIndexVO salvato su storage e PBANDI_T_DOCUMENTO_INDEX");

		PbandiTChecklistVO checklistVO = new PbandiTChecklistVO();
		checklistVO.setIdChecklist(checkListInLocoDTO.getIdChecklist());
		checklistVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
		genericDAO.update(checklistVO);
		
		PbandiTAppaltoChecklistVO appaltoChecklist = new PbandiTAppaltoChecklistVO();
		appaltoChecklist.setIdAppalto(checkListInLocoDTO.getIdAffidamento());
		appaltoChecklist.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
		genericDAO.insert(appaltoChecklist);
		
		return checklistVO  ;
	}
	
	
	private boolean modifyChecklistAffidamentoInLoco(final Long idUtente,
			final String codStatoTipoDocIndex,
			final PbandiTDocumentoIndexVO documentoIndexVO  ,
			EsitoSalvaModuloCheckListDTO esito, final byte[] bytesModuloPdf,
			final CheckListAffidamentoInLocoDTO checkListInLocoDTO,Long idProgetto,
			final BigDecimal idTipoModello,
			final String soggettoControllore,
			final String dataControllo,
			final String referenteBeneficiario, final BigDecimal idChecklist) throws Exception {
		
		String prf = "[CheckListManager::modifyChecklistAffidamentoInLoco]";
		logger.info(prf + " BEGIN");
		
		checkListInLocoDTO.setNomeFile(documentoIndexVO.getNomeFile());
		checkListInLocoDTO.setUid(documentoIndexVO.getUuidNodo());
		checkListInLocoDTO.setIdChecklist(idChecklist);
		checkListInLocoDTO.setBytesModuloPdf(bytesModuloPdf);
		Class<PbandiTAppaltoVO> voClass = PbandiTAppaltoVO.class;
		String codTipoDocIndex = Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO;		// Jira Pbandi-2859
//		doWithinLock(
//				esito,
//				new OperationWithinLock() {
//					public BigDecimal run() throws Exception {
		
					//TODO : PK : non devo aggiornare il doc su index ma sullo storage
//						documentoManager.aggiornaDocumento(checkListInLocoDTO,
//								idUtente, documentoIndexVO,
//								codStatoTipoDocIndex);
		
						documentiFSManager.aggiornaDocumento(documentoIndexVO.getNomeFile(), documentoIndexVO.getNomeFile(), codTipoDocIndex, 
								bytesModuloPdf );
						logger.info(prf + " aggiornaDocumento OK");
						
						PbandiTChecklistVO pbandiTChecklistVO =new PbandiTChecklistVO();
						pbandiTChecklistVO.setIdChecklist(checkListInLocoDTO.getIdChecklist());
						if(dataControllo==null)
							pbandiTChecklistVO.setDtControllo(DateUtil.getSysdate());
						else
							pbandiTChecklistVO.setDtControllo(DateUtil.utilToSqlDate(DateUtil.getDate(dataControllo)));
						pbandiTChecklistVO.setSoggettoControllore(soggettoControllore); 
						pbandiTChecklistVO.setReferenteBeneficiario(referenteBeneficiario); 

						BeanUtil.setPropertyIfValueIsNullByName(pbandiTChecklistVO,"dtControllo", getDataOdiernaSenzaOra());
						genericDAO.update(pbandiTChecklistVO);


						esito.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex().longValue());
						
						// PK devo aggiornare lo stato della checklist in PBANDI_R_DOCU_INDEX_TIPO_STATO
						
						PbandiRDocuIndexTipoStatoVO rdoc = new PbandiRDocuIndexTipoStatoVO();
						rdoc.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
						rdoc.setIdTipoDocumentoIndex(documentoIndexVO.getIdTipoDocumentoIndex());
						
						PbandiRDocuIndexTipoStatoVO rdocNew = new PbandiRDocuIndexTipoStatoVO();
						rdocNew.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
						rdocNew.setIdTipoDocumentoIndex(documentoIndexVO.getIdTipoDocumentoIndex());
						logger.info(prf + "codStatoTipoDocIndex: "+codStatoTipoDocIndex);
						BigDecimal idStatoTipoDocIndex = decodificheManager.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class, codStatoTipoDocIndex);
						
						logger.info(prf + "idStatoTipoDocIndex: "+idStatoTipoDocIndex);
						rdocNew.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
						
						genericDAO.update(rdoc, rdocNew);
						logger.info(prf + "stato della checklist aggiornato");
						
		/*				
		 * 
						//PK devo aggiornare alcune info sulla pbandiDocumentoIndex (es versione e id_stato_documento)
						logger.info(prf + "codStatoTipoDocIndex: "+codStatoTipoDocIndex);
						BigDecimal idStatoTipoDocIndex = decodificheManager.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,	codStatoTipoDocIndex);
						
						logger.info(prf + "idStatoTipoDocIndex: "+idStatoTipoDocIndex);
						
						documentoIndexVO.setIdStatoDocumento(idStatoTipoDocIndex);
						genericDAO.update(documentoIndexVO);
		*/	
						
//					}
//				}, idUtente, documentoIndexVO.getIdProgetto(),
//				documentoIndexVO.getIdTarget(), voClass, codTipoDocIndex);

		return esito.getEsito();
	}

	public String injectSezioneAppalti(Long idUtente, String identitaIride, String tipoChecklist, Long idProgetto, String html) {
		logger.info("\n\ninjectSezioneAppalti");
		
		try{
			AppaltoProgettoDTO[] appalti = gestioneAppaltiBusiness.findAppaltiChecklist(idUtente, identitaIride, idProgetto, tipoChecklist);
			
			if(appalti != null && appalti.length > 0){

				org.jsoup.nodes.Document doc = Jsoup.parse(html);
				String appaltiHtml = pbandiChecklistDAO.getChecklistHtmlModel(new BigDecimal(20));
				Element appaltiTable = doc.getElementById("caricaappalti");
				boolean esisteAppalto = false;
				
				for(AppaltoProgettoDTO appalto : appalti){
					if(appalto.getIsAssociated()){
						String resHtml = injectAppalto(idUtente, identitaIride, appalto.getIdAppalto(), new String(appaltiHtml), appaltiTable);
						appaltiTable.after(resHtml);
						esisteAppalto = true;
					}
				} 
				
				if(esisteAppalto){
					doc.getElementById("noappalti").remove();
				}
				
				html=doc.body().html();
			}
		}catch(Exception e){
			logger.error("Errore dureante il reperimento degli appalti per la checklist", e);
		}
		
		return html;
	}

	private String injectAppalto(Long idUtente, String identitaIride, Long idAppalto, String appaltoHtml,
			Element appaltiTable) throws Exception {
		
		try{
			AffidamentoDTO affidamento = gestioneAffidamentiBusiness.findAffidamento(idUtente, identitaIride, idAppalto);
			
			org.jsoup.nodes.Document appaltoModel = Jsoup.parseBodyFragment(appaltoHtml);

			if(affidamento.getOggettoAppalto() != null)
				appaltoModel.getElementById("titoloAppalto").text(affidamento.getOggettoAppalto());
			
			if(affidamento.getImportoContratto() != null)
				appaltoModel.getElementById("importoContratto").text(affidamento.getImportoContratto() + "");
			else
				appaltoModel.getElementById("dataFirmaContratto").text("N.D.");
			
			if(affidamento.getDtFirmaContratto() != null)
				appaltoModel.getElementById("dataFirmaContratto").text(DateUtil.getDate(affidamento.getDtFirmaContratto()));
			else
				appaltoModel.getElementById("dataFirmaContratto").text("N.D.");
			
			if(affidamento.getDtInizioPrevista() != null)
				appaltoModel.getElementById("dataInizioProgetto").text(DateUtil.getDate(affidamento.getDtInizioPrevista()));
			else
				appaltoModel.getElementById("dataInizioProgetto").text("N.D.");
			
			if(affidamento.getDtConsegnaLavori() != null)
				appaltoModel.getElementById("dataConsegnaLavori").text(DateUtil.getDate(affidamento.getDtConsegnaLavori()));
			else
				appaltoModel.getElementById("dataConsegnaLavori").text("N.D.");
			
			if(affidamento.getDtGuue() != null)
				appaltoModel.getElementById("dataGUUE").text(DateUtil.getDate(affidamento.getDtGuue()));
			else
				appaltoModel.getElementById("dataGUUE").text("N.D.");
			
			if(affidamento.getDtGuri() != null)
				appaltoModel.getElementById("dataGURI").text(DateUtil.getDate(affidamento.getDtGuri()));
			else
				appaltoModel.getElementById("dataGURI").text("N.D.");
		
			if(affidamento.getDtQuotNazionali() != null)
				appaltoModel.getElementById("dataQUOTNAZ").text(DateUtil.getDate(affidamento.getDtQuotNazionali()));
			else
				appaltoModel.getElementById("dataQUOTNAZ").text("N.D.");
			
			if(affidamento.getDtWebOsservatorio() != null)
				appaltoModel.getElementById("dataOssOOPP").text(DateUtil.getDate(affidamento.getDtWebOsservatorio()));
			else
				appaltoModel.getElementById("dataOssOOPP").text("N.D.");
			
			
//			dataStazApp
			if(affidamento.getDtWebStazAppaltante() != null)
				appaltoModel.getElementById("dataStazApp").text(DateUtil.getDate(affidamento.getDtWebStazAppaltante()));
			else
				appaltoModel.getElementById("dataStazApp").text("N.D.");
			
			
			if(affidamento.getProceduraAggiudicazioneDTO() != null && affidamento.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz() != null){
				PbandiDTipologiaAggiudicazVO tipologia = new PbandiDTipologiaAggiudicazVO();
				tipologia.setIdTipologiaAggiudicaz(beanUtil.transform(affidamento.getProceduraAggiudicazioneDTO().getIdTipologiaAggiudicaz(), BigDecimal.class));
				tipologia = genericDAO.findSingleWhere(tipologia);
				appaltoModel.getElementById("procAgg").text(tipologia != null ? tipologia.getDescTipologiaAggiudicazione() : "N.D.");
			}

			return appaltoModel.getElementsByTag("tbody").outerHtml();
		}catch(Exception ex){
			throw ex;
		}

	}
	
	
	@SuppressWarnings("static-access")
	public PbandiTDocumentoIndexVO creaDocumentoCL(Long idUtente, Object dto,
			String codStatoTipoDocIndex, String codTipoDocIndex) throws DocumentNotCreatedException {
		logger.begin();
	
		try {
			String nomeFile = getNomeFile(dto);			
			beanUtil.setPropertyValueByName(dto, "nomeFile", nomeFile);
			logger.info(" nomeFile = "+nomeFile);
			logger.info(" codStatoTipoDocIndex = "+codStatoTipoDocIndex);
			
			if("7".equals(codStatoTipoDocIndex)){
				EsitoSalvaModuloCheckListDTO DTO = (EsitoSalvaModuloCheckListDTO) dto;
				logger.info("DTO.getIdChecklist : " + DTO.getIdChecklist());
				logger.info("DTO.getIdDocumentoIndex: " + DTO.getIdDocumentoIndex());
				logger.info("DTO.getByteModulo() length: " + (DTO.getByteModulo() != null ? DTO.getByteModulo().length : "niente"));
				logger.info("DTO.getEsito: " + DTO.getEsito());
				nomeFile += codStatoTipoDocIndex;
			}
			
			Class<? extends GenericVO> classeEntita = getEntitaPerDocumento(dto.getClass());
			logger.info("classeEntita= "+classeEntita);

			return salvaInfoSuStorageDB(getBytes(dto), //node 
										  nomeFile,
										  beanUtil.getPropertyValueByName(dto,getNomeProprietaChiave(classeEntita),	BigDecimal.class), 
										  null, //idRappLegale, 
										  null, // idDelegato, 
										  beanUtil.getPropertyValueByName( dto, "idProgetto", BigDecimal.class),
										  getTipoDocIndex(dto.getClass()), 
										  classeEntita, 
										  codStatoTipoDocIndex, 
										  beanUtil.getPropertyValueByName(dto,"versione", BigDecimal.class),
										  idUtente,
										  null //shaHex
										  );
		} catch (Exception e) {
			throw new DocumentNotCreatedException(e.getMessage());
		}finally {
			logger.end();
		}
	}
	
	private String getNomeProprietaChiave(Class<? extends GenericVO> classeEntita)
			throws InstantiationException, IllegalAccessException {
		return (String) classeEntita.newInstance().getPK().get(0);
	}
	
	private static String getTipoDocIndex(Class<? extends Object> dtoClass) {
		return MAP_DTO_TIPO_DOCUMENTO_INDEX.get(dtoClass);
	}
	
	public static final Map<Class<? extends Object>, String> MAP_DTO_TIPO_DOCUMENTO_INDEX = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, String>() {
				{
					this.put(
							CheckListInLocoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);
					this.put(
							AllegatoCheckListInLocoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_VERBALE_CONTROLLO_VALIDAZIONE);
					this.put(
							AllegatoCheckListInLocoAffidamentiDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_VERBALE_CONTROLLO_IN_LOCO_AFFIDAMENTI);
					this.put(
							AllegatoCheckListDocumentaleAffidamentiDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_VERBALE_CONTROLLO_DOCUMENTALE_AFFIDAMENTI);
					this.put(
							CheckListDocumentaleDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);
					this.put(
							AllegatoRelazioneTecnicaDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RELAZIONE_TECNICA);
					this.put(
							ComunicazioneFineProgettoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_FINE_PROGETTO);
					this.put(
							CheckListAffidamentoDocumentaleDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_VALIDAZIONE);					
					this.put(
							CheckListAffidamentoInLocoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_IN_LOCO);
					
					this.put(ReportCampionamentoDTO.class, GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_FILE_CAMPIONAMENTO_CERT);
				}
			});
	
	// PK : ex salvaInfoNodoIndexSuDb
	public PbandiTDocumentoIndexVO salvaInfoSuStorageDB(byte[] file,
			String nomeFile, BigDecimal idTarget, Long idRappLegale, Long idDelegato, BigDecimal idProgetto,
			String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,Long idUtente,String shaHex) {
		
		logger.begin();
		logger.info(" file: " + file);
		logger.info(" nomeFile: " + nomeFile);
		logger.info(" idTarget: " + idTarget);
		logger.info(" idRappLegale: " + idRappLegale);
		logger.info(" idDelegato: " + idDelegato);
		logger.info(" idProgetto: " + idProgetto);
		logger.info(" tipoDocIndex: " + tipoDocIndex);
		logger.info(" entita: " + entita);
		logger.info(" codStatoTipoDocIndex: " + codStatoTipoDocIndex);
		logger.info(" versione: " + versione);
		logger.info(" shaHex: " + shaHex);
		
		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();

		try {
			BigDecimal idTipoDocumentoIndex = decodificheManager
					.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,	tipoDocIndex);
			
			logger.info(" idTipoDocumentoIndex: " + idTipoDocumentoIndex);
			
			documentoIndexVO.setDtInserimentoIndex(DateUtil.getSysdate());
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdProgetto(idProgetto);
			documentoIndexVO.setIdEntita(getIdEntita(entita));
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setVersione(versione);
			documentoIndexVO.setRepository(tipoDocIndex); //  PK ex Constants.APP_COMPANY_HOME_CM_BANDI 
			documentoIndexVO.setUuidNodo("UUID");//nodoCreato.getUid());  // PK per lo storage schianto "UUID"
			if(idRappLegale!=null)
				documentoIndexVO.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
			if(idDelegato!=null)
				documentoIndexVO.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
			documentoIndexVO.setMessageDigest(shaHex);
			DecodificaDTO statoGenerato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_GENERATO);
			documentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoGenerato.getId()));
			
			if(tipoDocIndex.startsWith("CL"))
				documentoIndexVO.setIdModello(getIdModello(idProgetto,idTipoDocumentoIndex)); // only for checklist
			
			documentoIndexVO.setFlagFirmaCartacea(this.getFlagFirmaCartacea(idTipoDocumentoIndex, idTarget, idProgetto));
			
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));


//			documentoIndexVO = genericDAO.insert(documentoIndexVO);  //TODO PK : qui non posso chiamare DocumentoManager.salvaFile(file, documentoIndexVO) ????
			
			it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO docIndexVO = new it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO(documentoIndexVO);
			
			documentiFSManager.salvaFile(file, docIndexVO);
			logger.info("docIndexVO.getIdDocumentoIndex=" + docIndexVO.getIdDocumentoIndex());
			
			documentoIndexVO.setIdDocumentoIndex(docIndexVO.getIdDocumentoIndex());
			
			//solo per checklist !
			// Jira PBANDI-2856: tolgo il controllo su "D" messo da Chiesa col commit del 11/12/19
			// poiche' non inserire in PbandiRDocuIndexTipoStato impedisce alla ricerca di trovare
			// le checklist in loco bozza. Rimetto quindi il controllo che esisteva prima.
			//if("D".equals(codStatoTipoDocIndex)){
			if(codStatoTipoDocIndex!=null){
				   logger.info("codStatoTipoDocIndex: "+codStatoTipoDocIndex);
					BigDecimal idStatoTipoDocIndex = decodificheManager
							.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,	codStatoTipoDocIndex);
					
					logger.info("idStatoTipoDocIndex: "+idStatoTipoDocIndex);
					
					if (idStatoTipoDocIndex != null) {
						PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
						pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(docIndexVO.getIdDocumentoIndex());
						pbandiRDocuIndexTipoStatoVO.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
						pbandiRDocuIndexTipoStatoVO.setIdTipoDocumentoIndex(beanUtil.transform(idTipoDocumentoIndex, BigDecimal.class));
						genericDAO.insert(pbandiRDocuIndexTipoStatoVO);
					}
			}else{
				logger.info(" codStatoTipoDocIndex null , non e' una checklist");
			}

		} catch (Exception e) {
			String message = "Impossibile inserire il documento:" + e.getMessage();
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}  
		logger.end();
		return documentoIndexVO;
	}
	
	private String getFlagFirmaCartacea(BigDecimal idTipoDocumentoIndex, BigDecimal idTarget, BigDecimal idProgetto) {
		logger.info("getFlagFirmaCartacea(): idTipoDocumentoIndex = "+idTipoDocumentoIndex+"; idTarget = "+idTarget);
		if (idTipoDocumentoIndex != null && idTipoDocumentoIndex.intValue() == 1) {
			// Si tratta di Ducumento Di Spesa.
			PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO();
			vo.setIdDichiarazioneSpesa(idTarget);
			vo = genericDAO.findSingleOrNoneWhere(vo);
			if (vo != null) {
				String tipoInvioDs = vo.getTipoInvioDs();
				logger.info("getFlagFirmaCartacea(): tipoInvioDs = "+tipoInvioDs);
				if (Constants.TIPO_INVIO_DS_CARTACEO.equalsIgnoreCase(tipoInvioDs)) {
					return "S";
				}
			}
		}
		if (idTipoDocumentoIndex != null && idTipoDocumentoIndex.intValue() == 17) {
			// Si tratta di Comunicazione di fine Progetto.
			DichiarazioneDiSpesaConTipoVO vo = dichiarazioneDiSpesaManager.getDichiarazioneFinaleConComunicazione(idProgetto.longValue());
			if (vo == null) {
				logger.info("DichiarazioneFinale non trovata per idProgetto = "+idProgetto);
				return null;
			}
			String tipoInvioDs = vo.getTipoInvioDs();
			logger.info("getFlagFirmaCartacea(): tipoInvioDs = "+tipoInvioDs);
			if (Constants.TIPO_INVIO_DS_CARTACEO.equalsIgnoreCase(tipoInvioDs)) {
				return "S";
			}
		}
		return null;
	}
	
	public BigDecimal getIdModello(BigDecimal idProgetto,
			BigDecimal idTipoDocumentoIndex) {
		PbandiRTpDocIndBanLiIntVO tpDocIndBanLiIntVO = new PbandiRTpDocIndBanLiIntVO();
		tpDocIndBanLiIntVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);

		BandoLineaProgettoVO bandoLineaProgettoVO = new BandoLineaProgettoVO();
		bandoLineaProgettoVO.setIdProgetto(idProgetto);

		List<Pair<GenericVO, PbandiRTpDocIndBanLiIntVO, BandoLineaProgettoVO>> list = genericDAO
				.join(Condition.filterBy(tpDocIndBanLiIntVO),
						Condition.filterBy(bandoLineaProgettoVO))
				.by("progrBandoLineaIntervento").select();

		BigDecimal idModello = list.size() == 1 ? list.get(0).getFirst()
				.getIdModello() : null;

		return idModello;
	}
	
	private BigDecimal getIdEntita(String nomeEntita) {
		PbandiCEntitaVO entitaVO = new PbandiCEntitaVO();
		entitaVO.setNomeEntita(nomeEntita);

		BigDecimal idEntita = null;
		try {
			idEntita = getGenericDAO().findSingleWhere(entitaVO).getIdEntita();
			logger.debug(nomeEntita + " -> " + idEntita);
		} catch (RecordNotFoundException e) {
			// FIXME insomma, la runtime non e' bella...
			throw new RuntimeException("Non trovato id per codice "	+ nomeEntita);
		}

		return idEntita;
	}
	
	public BigDecimal getIdEntita(Class<? extends GenericVO> voClass) {
		return getIdEntita(GenericVO.getTableNameForValueObject(voClass));
	}
	
	private static Class<? extends GenericVO> getEntitaPerDocumento(
			Class<? extends Object> dtoClass) {
		return MAP_DTO_DOCUMENTO_VO_ENTITA.get(dtoClass);
	}
	
	public static final Map<Class<? extends Object>, Class<? extends GenericVO>> MAP_DTO_DOCUMENTO_VO_ENTITA = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, Class<? extends GenericVO>>() {
				{
					this.put(AllegatoCheckListInLocoDTO.class,
							PbandiTChecklistVO.class);
					this.put(AllegatoCheckListInLocoAffidamentiDTO.class,
							PbandiTChecklistVO.class);
					this.put(AllegatoCheckListDocumentaleAffidamentiDTO.class,
							PbandiTChecklistVO.class);
					this.put(CheckListInLocoDTO.class, PbandiTChecklistVO.class);
					this.put(CheckListDocumentaleDTO.class,PbandiTDichiarazioneSpesaVO.class);

					this.put(AllegatoRelazioneTecnicaDTO.class,
							PbandiTDichiarazioneSpesaVO.class);
					this.put(ComunicazioneFineProgettoDTO.class,
							PbandiTComunicazFineProgVO.class);
					
					this.put(CheckListAffidamentoDocumentaleDTO.class, PbandiTAppaltoVO.class);
					
					this.put(CheckListAffidamentoInLocoDTO.class, PbandiTAppaltoVO.class);
					
					this.put(ReportCampionamentoDTO.class, PbandiTCampionamentoVO.class);
					
				}
			});

	
	private static final String[] BYTES_DOCUMENTO_PROP_NAMES = new String[] {"bytesDocumento", "bytesModuloPdf", "excelByte"};
	
	private byte[] getBytes(Object dto) {
		byte[] result = null;
		for (String propName : BYTES_DOCUMENTO_PROP_NAMES) {
			result = beanUtil.getPropertyValueByName(dto, propName,
					byte[].class);
			if (result != null) {
				break;
			}
		}
		return result;
	}
	
	private interface GeneratoreNomeFile<T> {

		String generaNomeFile(T dto);
	};
	
	public <T> String getNomeFile(T dto) {
		logger.dump(dto);

		String nomeFileFromDTO = beanUtil.getPropertyValueByName(dto,
				"nomeFile", String.class);

		String nomeFile = "nomeFileGenerico.bin";
		if (!StringUtil.isEmpty(nomeFileFromDTO)) {
			logger.debug("nome file preso dal dto");
			nomeFile = nomeFileFromDTO;
		} else {
			@SuppressWarnings("unchecked")
			GeneratoreNomeFile<T> generatoreNomeFile = (GeneratoreNomeFile<T>) MAP_DTO_GENERATORE_NOME_FILE
					.get(dto.getClass());
			if (generatoreNomeFile != null) {
				nomeFile = generatoreNomeFile.generaNomeFile(dto);
			} else {
				String baseName = MAP_DTO_NOME_FILE.get(dto.getClass());
				if (baseName == null) {
					baseName = nomeFile;
				}

				nomeFile = baseName
						.replace(
								".",
								("_" + beanUtil.getPropertyValueByName(dto,	"idProgetto", String.class)
									 + "_" + DateUtil.getTime(
												new java.util.Date(),
												Constants.DATEHOUR_FORMAT_PER_NOME_FILE)
								 ) + ".");
			}
		}

		logger.info("nome file: " + nomeFile);
		return nomeFile;
	}
	
	public static final Map<Class<? extends Object>, String> MAP_DTO_NOME_FILE = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, String>() {
				{
					this.put(CheckListInLocoDTO.class, "checkList.pdf");
					this.put(AllegatoCheckListInLocoDTO.class,	"verbaleChecklist.pdf");
				}
			});
	
	private static final Map<Class<?>, GeneratoreNomeFile<?>> MAP_DTO_GENERATORE_NOME_FILE = new HashMap<Class<?>, GeneratoreNomeFile<?>>();

	static {
		MAP_DTO_GENERATORE_NOME_FILE.put(CheckListDocumentaleDTO.class,
				new GeneratoreNomeFile<CheckListDocumentaleDTO>() {

					public String generaNomeFile(CheckListDocumentaleDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "checkList_" + dto.getIdDichiarazioneDiSpesa()
								+ "_V" + dto.getVersione() + "_" + time
								+ ".pdf";
					}
				});
		MAP_DTO_GENERATORE_NOME_FILE.put(ComunicazioneFineProgettoDTO.class,
				new GeneratoreNomeFile<ComunicazioneFineProgettoDTO>() {

					public String generaNomeFile(ComunicazioneFineProgettoDTO dto) {
						String time = DateUtil.getTime(dto.getDtComunicazione(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "ComunicazioneDiFineProgetto_" + dto.getIdComunicazFineProg()
								+ "_" + time
								+ ".pdf";
					}
				});
		/*   Jira Pbandi-2859
		MAP_DTO_GENERATORE_NOME_FILE.put(CheckListAffidamentoDocumentaleDTO.class,
				new GeneratoreNomeFile<CheckListAffidamentoDocumentaleDTO>() {

					public String generaNomeFile(CheckListAffidamentoDocumentaleDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "checkListAffidamento_" + dto.getIdAppalto()
								+ "_V" + dto.getVersione() + "_" + time
								+ ".pdf";
					}
				});
		*/		
		MAP_DTO_GENERATORE_NOME_FILE.put(CheckListAffidamentoInLocoDTO.class,
				new GeneratoreNomeFile<CheckListAffidamentoInLocoDTO>() {

					public String generaNomeFile(CheckListAffidamentoInLocoDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "checkList_" + dto.getIdAffidamento()
								+ "_" + time
								+ ".pdf";
					}
				});
		
		MAP_DTO_GENERATORE_NOME_FILE.put(ReportCampionamentoDTO.class,
				new GeneratoreNomeFile<ReportCampionamentoDTO>() {

					public String generaNomeFile(ReportCampionamentoDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return dto.getTemplateName()
								+ "_" + time
								+ ".xls";
					}
				});
	}
}
