/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.dichiarazionedispesa;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.EjbSessionContextBean;
//import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.TimerManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.TipoAllegatiManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoContabileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EnteAppartenenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneAnteprimaDichiarazioneSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneInviaDichiarazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneScaricaDichiarazioneSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneVerificaDichiarazioneSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.InfoDichiarazioneSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.IstanzaAttivitaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RelazioneTecnicaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.VoceDiCostoDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoRelazioneTecnicaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.SedeDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.ManagerException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.DocumentNotCreatedException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiPagamentiDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DelegatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoSpesaDaInviareVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.NotaDiCreditoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.PagamentoDocumentoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.QuotaParteTipoDocumentoFornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoDichiarazioneVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DichiarazioneDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.VoceDiCostoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoIndexAssociatoDocSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoDocumentoDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.LessThanOrEqualCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDMicroSezioneDinVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoDichiarazSpesaVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDichSpesaDocAllegVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPagamentoDichSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPagamentoDocSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiWProgettoDocAllegVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.dichiarazionedispesa.DichiarazioneDiSpesaSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.doqui.index.ecmengine.dto.Node;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


import org.apache.commons.codec.digest.DigestUtils;

/**
 */
public class DichiarazioneDiSpesaBusinessImpl extends BusinessImpl implements
		DichiarazioneDiSpesaSrv {
	private ContoEconomicoManager contoEconomicoManager;
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	private DocumentoManager documentoManager;
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	private EjbSessionContextBean ejbSessionContext;
	private GenericDAO genericDAO;
	private IndexDAO indexDAO;
	private NeofluxSrv neofluxBusiness;
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	private PopolaTemplateManager popolaTemplateManager;
	private ProgettoManager progettoManager;
	private RappresentanteLegaleManager rappresentanteLegaleManager;
	private SedeManager sedeManager;
	private SoggettoManager soggettoManager;
	private TimerManager timerManager;	
	private TipoAllegatiManager tipoAllegatiManager;
	
	
	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setSedeManager(SedeManager sedeManager) {
		this.sedeManager = sedeManager;
	}

	public SedeManager getSedeManager() {
		return sedeManager;
	}
	public NeofluxSrv getNeofluxBusiness() {
		return neofluxBusiness;
	}

	public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
		this.neofluxBusiness = neofluxBusiness;
	}

	public EjbSessionContextBean getEjbSessionContext() {
		return ejbSessionContext;
	}

	public void setEjbSessionContext(EjbSessionContextBean ejbSessionContext) {
		this.ejbSessionContext = ejbSessionContext;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setIndexDAO(IndexDAO indexDAO) {
		this.indexDAO = indexDAO;
	}

	public IndexDAO getIndexDAO() {
		return indexDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	private ReportManager reportManager;

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;

	public void setPbandiDocumentiDiSpesaDAO(
			PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}

	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}

	public void setDichiarazioneDiSpesaManager(
			DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager) {
		this.dichiarazioneDiSpesaManager = dichiarazioneDiSpesaManager;
	}

	public DichiarazioneDiSpesaManager getDichiarazioneDiSpesaManager() {
		return dichiarazioneDiSpesaManager;
	}

	public void setPopolaTemplateManager(
			PopolaTemplateManager popolaTemplateManager) {
		this.popolaTemplateManager = popolaTemplateManager;
	}

	public PopolaTemplateManager getPopolaTemplateManager() {
		return popolaTemplateManager;
	}
	

	/*
	 * regole gestite: BR01 Gestione attributi qualifica, monte ore e costo
	 * annuo per dipendenti BR03 Rilascio in dichiarazione di spesa SOLO di
	 * spese quietanzate. BR07 Controllo spese totalmente quietanzate in
	 * dichiarazione finale.
	 */

	public TipoAllegatiManager getTipoAllegatiManager() {
		return tipoAllegatiManager;
	}

	public void setTipoAllegatiManager(TipoAllegatiManager tipoAllegatiManager) {
		this.tipoAllegatiManager = tipoAllegatiManager;
	}

	public it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException

	{

		    String[] nameParameter = { "idUtente", "identitaDigitale","filtroDichiarazioneDiSpesaDTO" };
		 
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, filtroDichiarazioneDiSpesa);
			logger.info("\n\n\n\n\nverificaDichiarazioneDiSpesa new timeout mgmnt.... *******************");
			logger.shallowDump(filtroDichiarazioneDiSpesa, "info");
			long start = getTimerManager().start();
			EsitoOperazioneVerificaDichiarazioneSpesa esito = new EsitoOperazioneVerificaDichiarazioneSpesa();
            try{
			boolean isFinaleOIntegrativa = Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE
					.equalsIgnoreCase(filtroDichiarazioneDiSpesa
							.getTipoDichiarazione())
					|| Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA
							.equalsIgnoreCase(filtroDichiarazioneDiSpesa
									.getTipoDichiarazione());

			// documentiDaInviare: tutti i documenti che non sono note di credito
			List<DocumentoSpesaDaInviareVO> documentiDaInviare = findDocumentiDaInviare(filtroDichiarazioneDiSpesa);

			String statoDocumentoDaCompletare = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE;
			DecodificaDTO decodificaStatoDaCompletare = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
					statoDocumentoDaCompletare);
			
			
			// documentiDaRestituire: tutti i documenti compresi le note di
			// credito
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO> documentiDaRestituire = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO>();

			int totDocumentiDaInviare = 0;
			java.util.Set<BigDecimal> nonInviabili = new HashSet<BigDecimal>();
			/* 
			 * FIX PBANDI-2314 
			 */
			//int pagamenti = 0;
			if (!isEmpty(documentiDaInviare)) {
				totDocumentiDaInviare = documentiDaInviare.size();
				 
				boolean isBr01 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR01_GESTIONE_ATTRIBUTI_QUALIFICA);

				boolean isBr02 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);

				boolean isBr03 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR03_RILASCIO_SPESE_QUIETANZATE);

				boolean isBr04 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR04_VISUALIZZA_DATA_VALUTA);

				boolean isBr05 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA);

				boolean isBr07 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR07_CONTROLLO_SPESE_TOTALMENTE_QUIETANZATE);
				
				boolean isBr39 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR39_CONTROLLO_QUIETANZA_NON_INFERIORE_RENDICONTABILE);
				
				boolean isBr42 = regolaManager
						.isRegolaApplicabileForBandoLinea(
								filtroDichiarazioneDiSpesa.getIdBandoLinea(),
								RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
				

				if (isBr07 && isFinaleOIntegrativa)
					isBr03 = true;
 

				List<DocumentoSpesaDaInviareVO> noteDiCredito = new ArrayList<DocumentoSpesaDaInviareVO>();

				List<BigDecimal> listIdDoc = beanUtil.extractValues(
						documentiDaInviare, "idDocumentoDiSpesa",
						BigDecimal.class);
				for (BigDecimal idDocRiferimento : listIdDoc) {
					NotaDiCreditoVO nota = new NotaDiCreditoVO();
					nota.setIdDocDiRiferimento(idDocRiferimento);
					nota.setIdProgetto(new BigDecimal(
							filtroDichiarazioneDiSpesa.getIdProgetto()));
					nota.setIdSoggetto(new BigDecimal(
							filtroDichiarazioneDiSpesa.getIdSoggetto()));
					noteDiCredito.add(nota);
				}
				noteDiCredito = genericDAO.findListWhere(noteDiCredito);

				// cerco eventuali note di credito associate a quei documenti,
				// se le trovo le faccio vedere al client,ma non rientrano negli
				// eventuali scarti

				documentiDaInviare.addAll(noteDiCredito);

				List<Long> fattureInvalidateDaNotaDiCredito = new ArrayList<Long>();
				
				for (DocumentoSpesaDaInviareVO documentoDiSpesaVO : documentiDaInviare) {
					if (!getTimerManager().checkTimeout(start))
						throw new DichiarazioneDiSpesaException(
								ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);
				 
					it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO docDaRestituire = new it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO();
					beanUtil.valueCopy(documentoDiSpesaVO, docDaRestituire);
					
					String cfFornitore=documentoDiSpesaVO.getCodiceFiscaleFornitore();
					if(cfFornitore!=null){
						String nomeFornitore=documentoDiSpesaVO.getDenominazioneFornitore();
						if(nomeFornitore==null){
							nomeFornitore=documentoDiSpesaVO.getNomeFornitore()+" "+documentoDiSpesaVO.getCognomeFornitore();
						}
						if(cfFornitore.startsWith("PBAN")){
							cfFornitore="n.d. - Fornitore estero";
						}
						docDaRestituire.setNomeFornitore(nomeFornitore+" - "+cfFornitore);
					}
					
					
					if(documentoDiSpesaVO.getFlagAllegati()!=null && documentoDiSpesaVO.getFlagAllegati().equalsIgnoreCase("S"))
						docDaRestituire.setAllegatiPresenti(true);
					else 
						docDaRestituire.setAllegatiPresenti(false);
					
					documentiDaRestituire.add(docDaRestituire);
					boolean completamenteQuietanziato = isCompletamenteQuietanziato(documentoDiSpesaVO, filtroDichiarazioneDiSpesa);
					boolean importoQuietanzatoSuperioreImportoDocumento = isImportoQuietanzatoSuperioreImportoDocumento(documentoDiSpesaVO);
					boolean cedolino = documentoDiSpesaManager.isCedolinoOAutodichiarazioneSoci(documentoDiSpesaVO);
					boolean notaDiCredito = isNotaDiCredito(documentoDiSpesaVO);
					
					boolean scartato = false;

					if (isNull(documentoDiSpesaVO.getTotaleImportoPagamenti())
							&& !notaDiCredito && !scartato) {
						logger.debug("Non ci sono pagamenti per il documento "
								+ documentoDiSpesaVO.getIdDocumentoDiSpesa()
								+ " e non e' una nota di credito");
						docDaRestituire
								.setMotivazione(DOCUMENTO_SENZA_PAGAMENTI);
						scartato = true;
						nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
					}
					
					
					boolean documentoElettronico = documentoDiSpesaVO.getFlagElettronico() != null && documentoDiSpesaVO.getFlagElettronico().equalsIgnoreCase("S") ? true : false;
					boolean documentoConAllegatiDigitali = isTipoInvioDigitale(documentoDiSpesaVO.getTipoInvio());
					
					/*
					 * Se il documento prevede il tipo di invio
					 * ELETTRONICO (E) o ALLEGATI_DIGITALI(S)
					 * allora verifico se esistono dei file 
					 * associati al documento ed ai pagamenti
					 */
					if (!scartato && isBr42 && (documentoConAllegatiDigitali || documentoElettronico)) {
						/*
						 * Ricerco i file allegati al documento-progetto
						 */
				 
						logger.info("\n\n\n\n\n\n\n\n\n\nfiltroDichiarazioneDiSpesa.getIdProgetto()"+filtroDichiarazioneDiSpesa.getIdProgetto());
						DocumentoIndexAssociatoDocSpesaVO filterDocIndexDocSpesa = new DocumentoIndexAssociatoDocSpesaVO();
						filterDocIndexDocSpesa.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
						filterDocIndexDocSpesa.setIdProgetto(new BigDecimal(filtroDichiarazioneDiSpesa.getIdProgetto()));
					    List<DocumentoIndexAssociatoDocSpesaVO> filesAssociatiDocSpesa =  genericDAO.findListWhere(filterDocIndexDocSpesa);
					   
						if (ObjectUtil.isEmpty(filesAssociatiDocSpesa)
							&& !documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesaVO.getDescBreveTipoDocSpesa()))	 {
							logger.warn("Nessun file associato al documento "+documentoDiSpesaVO.getIdDocumentoDiSpesa());
							scartato = true;
							docDaRestituire.setMotivazione(WARNING_DOCUMENTO_DI_SPESA_SENZA_ALLEGATO);
							nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
						} else if(!documentoDiSpesaManager.isCedolinoCostiStandard(documentoDiSpesaVO.getDescBreveTipoDocSpesa()) 
								&& !documentoDiSpesaManager.isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesaVO.getDescBreveTipoDocSpesa())){
							/*
							 * Ricerco i pagamenti del documento e verifico se esiste almeno un file
							 * associato per ogni pagamento. Non serve idProgetto, panarace 12/06/2015
							 */
							PbandiRPagamentoDocSpesaVO pagamentiFilterVO = new PbandiRPagamentoDocSpesaVO();
							pagamentiFilterVO.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
							List<PbandiRPagamentoDocSpesaVO> pagamentiDocumento = genericDAO.findListWhere(pagamentiFilterVO);
							if (!ObjectUtil.isEmpty(pagamentiDocumento))  {
								BigDecimal idEntitaPagamento= documentoManager.getIdEntita(PbandiTPagamentoVO.class);
								for (PbandiRPagamentoDocSpesaVO pagDoc : pagamentiDocumento) {
									
									PbandiTFileEntitaVO pbandiTFileEntitaVO=new PbandiTFileEntitaVO();
									pbandiTFileEntitaVO.setIdEntita(idEntitaPagamento);
									pbandiTFileEntitaVO.setIdTarget(pagDoc.getIdPagamento());
									
									
									List<PbandiTFileEntitaVO> rfileEntitaPag = genericDAO.findListWhere(pbandiTFileEntitaVO);
									if (ObjectUtil.isEmpty(rfileEntitaPag)) {
										logger.warn("\n\nNo file associated to pagamento["+pagDoc.getIdPagamento()+"] ,  documento di spesa["+documentoDiSpesaVO.getIdDocumentoDiSpesa()+"]. Doc DESCARDED!!!");
										scartato = true;
										docDaRestituire.setMotivazione(WARNING_DOCUMENTO_DI_SPESA_CON_PAGAMENTI_SENZA_ALLEGATI);
										nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
										break;
									}
								}
							}
							
						}
						
						// check if exist at least one T_doc_index with numero_protocollo not null for allegati doc/progetto
						
					/* COMMENTATO PER PNC 17/12/2015
					 * 	if(documentoElettronico){
							 boolean hasNumeroProtocollo=false;
								for (DocumentoIndexAssociatoDocSpesaVO documentoIndexAssociatoDocSpesaVO : filesAssociatiDocSpesa) {
									if(!ObjectUtil.isEmpty(documentoIndexAssociatoDocSpesaVO.getNumProtocollo())){
											 hasNumeroProtocollo=true;
											 break;
									}
								} 
							
							if(!hasNumeroProtocollo){
								logger.warn("NO NUM PROTOCOLLO, doc DESCARDED!");
								scartato = true;
								docDaRestituire.setMotivazione(WARNING_DOCUMENTO_DI_SPESA_SENZA_NUMERO_PROTOCOLLO);
								nonInviabili.add(documentoDiSpesaVO.getIdDocumentoDiSpesa());
							}
						}
					/* COMMENTATO PER PNC 17/12/2015 */
						
					}
					
					
					
					
					/*
					 * Se il documento e' in stato DA COMPLETARE
					 * e non e' presente la regola BR03,
					 * devo verificare che siano stati inseriti 
					 * nuovi pagamenti
					 */
					if ( documentoDiSpesaVO.getIdStatoDocumentoSpesa().compareTo(NumberUtil.toBigDecimal(decodificaStatoDaCompletare.getId())) == 0 
							&& !scartato && !notaDiCredito && !isBr03) {
						
						BigDecimal numPagamentiInviabili = isNull(documentoDiSpesaVO.getNumPagamentiInviabili()) ? new BigDecimal(0) : documentoDiSpesaVO.getNumPagamentiInviabili();
						
						if ( NumberUtil.compare(numPagamentiInviabili, new BigDecimal(0)) == 0) {
							scartato = true;
							nonInviabili.add(documentoDiSpesaVO
									.getIdDocumentoDiSpesa());
						 	logger.warn("No  new  pagamenti per   doc "
									+ documentoDiSpesaVO.getIdDocumentoDiSpesa()
									+ " e non e' una nota di credito, DESCARDED!"); 
							docDaRestituire.setMotivazione(DOCUMENTO_SENZA_NUOVI_PAGAMENTI);
						}
								
					}

					
					
					if (!scartato
							&& !isRendicontabileCompletamenteAssociato(
									documentoDiSpesaVO,
									filtroDichiarazioneDiSpesa)) {
						logger.warn("RENDICONTABILE_NON_COMPLETAMENTE_ASSOCIATO, doc DESCARDED!");
						docDaRestituire
								.setMotivazione(RENDICONTABILE_NON_COMPLETAMENTE_ASSOCIATO);
						scartato = true;
						if (!notaDiCredito) {
							nonInviabili.add(documentoDiSpesaVO
									.getIdDocumentoDiSpesa());
						}
					}
					
					
					if (isBr03 && !scartato) {
						logger.debug("Regola BR03 applicabile ! controllo che il doc "
								+ documentoDiSpesaVO.getIdDocumentoDiSpesa()
								+ " sia completamente quietanziato (solo se non � una spesa forfettaria)");
						if (!notaDiCredito && !completamenteQuietanziato) {
							logger.debug("BR03 - il doc <"
									+ documentoDiSpesaVO
											.getIdDocumentoDiSpesa()
									+ "> NON �  completamente quietanziato !");
							docDaRestituire
									.setMotivazione(SPESA_NON_TOTALMENTE_QUIETANZATA);
							scartato = true;
							nonInviabili.add(documentoDiSpesaVO
									.getIdDocumentoDiSpesa());
						}

					}

					if (!scartato && isFattura(documentoDiSpesaVO)) {
						logger.debug("E'una fattura");
						if (importoQuietanzatoSuperioreImportoDocumento) {
							docDaRestituire
									.setMotivazione(FATTURA_QUIETANZATO_MAGGIORE_DEL_DOCUMENTO);
							scartato = true;
							nonInviabili.add(documentoDiSpesaVO
									.getIdDocumentoDiSpesa());
						}
					}

					if (!scartato) {
						String errorCode = documentoDiSpesaManager
								.controlliVociSpesaNoteCreditoDocumento(
										documentoDiSpesaVO
												.getIdDocumentoDiSpesa(),
										documentoDiSpesaVO.getIdProgetto(),
										notaDiCredito);
						if (errorCode != null) {
							docDaRestituire.setMotivazione(errorCode);
							scartato = true;
							if (!notaDiCredito) {
								nonInviabili.add(documentoDiSpesaVO
										.getIdDocumentoDiSpesa());
							}
						}
					}

			 

					if (isBr01 && !scartato) {
						if (cedolino
								&& isMonteOreSuperato(documentoDiSpesaVO,
										filtroDichiarazioneDiSpesa)) {
							docDaRestituire.setMotivazione(MONTE_ORE_SUPERATO);
							scartato = true;
							if (!notaDiCredito) {
								nonInviabili.add(documentoDiSpesaVO
										.getIdDocumentoDiSpesa());
							}
						}
					}

				 
					// }L{ PBANDI-2280
					if (!notaDiCredito && !scartato && isBr39) {
						boolean importoQuietanzatoInferioreRendicontabileProgetto = isImportoQuietanzatoInferioreRendicontabileProgetto(documentoDiSpesaVO);
						if (importoQuietanzatoInferioreRendicontabileProgetto) {
							docDaRestituire
									.setMotivazione(QUIETANZATO_INFERIORE_RENDICONTABILE);
							scartato = true;
							if (!notaDiCredito) {
								nonInviabili.add(documentoDiSpesaVO
										.getIdDocumentoDiSpesa());
							}
						}
					}

					// }L{ PBANDI-1723
					// se una NdC non passa i controlli, non deve passarli
					// neanche la fattura associata
					if (notaDiCredito && scartato) {
						fattureInvalidateDaNotaDiCredito.add(beanUtil
								.transform(documentoDiSpesaVO
										.getIdDocDiRiferimento(), Long.class));
					}

				}
				// }L{ PBANDI-1723
				if (!fattureInvalidateDaNotaDiCredito.isEmpty()) {
					for (DocumentoDiSpesaDTO documento : documentiDaRestituire) {
						if (fattureInvalidateDaNotaDiCredito.contains(documento
								.getIdDocumentoDiSpesa())) {
							logger.debug("Il documento "
									+ documento.getIdDocumentoDiSpesa()
									+ " di tipo "
									+ documento.getDescTipoDocumentoDiSpesa()
									+ " � invalidato dalla nota di credito associata");
							documento
									.setMotivazione(NOTA_DI_CREDITO_ASSOCIATA_INVALIDA);
							// }L{ il documento referenziato, NON la nota di
							// credito, che e' stata gia' scartata
							nonInviabili.add(new BigDecimal(documento
									.getIdDocumentoDiSpesa()));
						}
					}
				}
			}

			
			esito.setDocumentiDiSpesa(documentiDaRestituire
					.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO[] {}));

			int documentiInviabili = totDocumentiDaInviare
					- nonInviabili.size();

			List<String> messages = new ArrayList<String>();

			// DICH INTERMEDIA
			if (Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTERMEDIA
					.equalsIgnoreCase(filtroDichiarazioneDiSpesa
							.getTipoDichiarazione())) {

				if (totDocumentiDaInviare == 0) {
					logger.info("Non sono stati trovati dei documenti di spesa nel periodo indicato.");
					esito.setEsito(Boolean.FALSE);
					esito.setMessage(ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
				} else if (totDocumentiDaInviare > 0) {
					if (documentiInviabili == 0) {
						esito.setEsito(Boolean.FALSE);
						esito.setMessage(MessaggiConstants.KEY_MSG_SI_CONSIGLIA_CORREZIONE_DOCUMENTI);
					}					 
					else if (documentiInviabili == totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.VERIFICA_DOCUMENTI_SUPERATA);
					} else if (documentiInviabili < totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.KEY_MSG_SI_CONSIGLIA_CORREZIONE_DOCUMENTI);
					}
				}
			} else if (isFinaleOIntegrativa) {
				// DICH FINALE o INTEGRATIVA
				if (totDocumentiDaInviare == 0) {
					logger.info("Non sono stati trovati dei documenti di spesa nel periodo indicato.");
					esito.setEsito(Boolean.TRUE);
					messages.add(ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
					messages.add(MessaggiConstants.KEY_MSG_POSSIBILE_DICHIARARE_CONCLUSA_DICHIARAZIONE_SENZA_DOC);
				} else if (totDocumentiDaInviare > 0) {
					if (documentiInviabili == 0
							|| documentiInviabili < totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.KEY_MSG_SI_CONSIGLIA_CORREZIONE_DOCUMENTI);
						messages.add(MessaggiConstants.KEY_MSG_POSSIBILE_DICHIARARE_CONCLUSA_DICHIARAZIONE_SENZA_DOC);
					} else if (documentiInviabili == totDocumentiDaInviare) {
						esito.setEsito(Boolean.TRUE);
						messages.add(MessaggiConstants.VERIFICA_DOCUMENTI_SUPERATA);
					}
				}
			}

			esito.setMessaggi(messages.toArray(new String[] {}));
            }  catch(DichiarazioneDiSpesaException ex){
            	logger.error("Errore DichiarazioneDiSpesaException"+ex.getMessage(), ex);
            	throw ex;
            }  catch(Throwable t){
            	logger.error("Errore throwable"+t.getMessage(), t);
            	if(t instanceof DichiarazioneDiSpesaException)
            		throw (DichiarazioneDiSpesaException)t;
            	else{
            		logger.info("erorre timeout? ms:"+(System.currentTimeMillis()-start));
            		if (!getTimerManager().checkTimeout(start))
						throw new DichiarazioneDiSpesaException(
								ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);
            		throw new UnrecoverableException(t.getMessage());
            	}
            }
			return esito;
		 
	}

	/**
	 * Verifica se il quietanzato residuo del documento e' strettamente minore del
	 * rendicontabile residuo
	 * 
	 * @see PBANDI-2280
	 */

	private boolean isImportoQuietanzatoInferioreRendicontabileProgetto(
			DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
	
		// QUIETANZATO: somma di T_PAGAMENTO.importo_pagamento
		PagamentoDocumentoProgettoVO rpdsVO = new PagamentoDocumentoProgettoVO();
		rpdsVO.setIdDocumentoDiSpesa(documentoDiSpesaVO.getIdDocumentoDiSpesa());
		rpdsVO.setIdProgetto(documentoDiSpesaVO.getIdProgetto());
		List<PagamentoDocumentoProgettoVO> pagamentiDocSpesa = genericDAO.findListWhere(Condition.filterBy(rpdsVO));
						
		BigDecimal residuoTotPagamento = new BigDecimal(0);
		BigDecimal quietanzatoTotale = new BigDecimal(0);
		for (PagamentoDocumentoProgettoVO pag : pagamentiDocSpesa) {
			
			BigDecimal residuoPagamento = pag.getResiduoUtilePagamento() == null ? new BigDecimal(0) : pag.getResiduoUtilePagamento();
			BigDecimal quietanzato = pag.getImportoQuietanzato() == null ? new BigDecimal(0) : pag.getImportoQuietanzato();
			residuoTotPagamento = NumberUtil.sum(residuoTotPagamento, residuoPagamento);
			quietanzatoTotale = NumberUtil.sum(quietanzato, quietanzatoTotale);
			
		}
		
		
		BigDecimal rendicontabileResiduoProgetto = NumberUtil.subtract(documentoDiSpesaVO.getImportoRendicontazione(),quietanzatoTotale);

		if (NumberUtil.compare(residuoTotPagamento, rendicontabileResiduoProgetto) < 0) {
			return true;
		}
		

		return false;

	}

	private List<DocumentoSpesaDaInviareVO> findDocumentiDaInviare(
			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa) {
		DocumentoSpesaDaInviareVO documentoSpesaVO = new DocumentoSpesaDaInviareVO();
		documentoSpesaVO.setIdProgetto(new BigDecimal(
				filtroDichiarazioneDiSpesa.getIdProgetto()));
		documentoSpesaVO.setIdSoggetto(new BigDecimal(
				filtroDichiarazioneDiSpesa.getIdSoggetto()));
		/*
		 * FIX PBANDI-2314
		 * Aggiundo il filtro sullo stato del documento.
		 * Possono essere inviati solamente i documenti in stato dichiarabile
		 * o DA COMPLETARE
		 */
		String statoDocumentoDichiarabile = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;
		String statoDocumentoDaCompletare = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE;
		DecodificaDTO decodificaStatoDichiarabile = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
				statoDocumentoDichiarabile);
		DecodificaDTO decodificaStatoDaCompletare = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
				statoDocumentoDaCompletare);
		
		List<DocumentoSpesaDaInviareVO> filterStatiDocumento = new ArrayList<DocumentoSpesaDaInviareVO>();
		DocumentoSpesaDaInviareVO filterStatoDichiarabile = new DocumentoSpesaDaInviareVO();
		filterStatoDichiarabile.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(decodificaStatoDichiarabile.getId()));
		filterStatiDocumento.add(filterStatoDichiarabile);
		
		DocumentoSpesaDaInviareVO filterStatoDaCompletare = new DocumentoSpesaDaInviareVO();
		filterStatoDaCompletare.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(decodificaStatoDaCompletare.getId()));
		filterStatiDocumento.add(filterStatoDaCompletare);
		
		
		DocumentoSpesaDaInviareVO documentoSpesaVOConData = new DocumentoSpesaDaInviareVO();
		documentoSpesaVOConData.setDataDocumentoDiSpesa(DateUtil
				.utilToSqlDate(filtroDichiarazioneDiSpesa
						.getDataFineRendicontazione()));

		LessThanOrEqualCondition<DocumentoSpesaDaInviareVO> lessCondition = new LessThanOrEqualCondition<DocumentoSpesaDaInviareVO>(
				documentoSpesaVOConData);

		
		List<DocumentoSpesaDaInviareVO> documentiDaInviare = genericDAO
				.findListWhere(lessCondition.and(new FilterCondition<DocumentoSpesaDaInviareVO>(
						documentoSpesaVO), new FilterCondition<DocumentoSpesaDaInviareVO>(filterStatiDocumento)));
		return documentiDaInviare;
	}


	/**
	 * Verifico se il documento � di tipo: - Fattura FT - Fattura fideiussoria
	 * FF - Fattura leasing FL
	 * 
	 * @param documentoDiSpesaVO
	 * @return
	 */
	private boolean isFattura(DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
			Long idTipoDocumentoDiSpesa = documentoDiSpesaVO
					.getIdTipoDocumentoDiSpesa().longValue();
			DecodificaDTO decodifica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
					idTipoDocumentoDiSpesa);
			if (decodifica != null
					&& (decodifica
							.getDescrizioneBreve()
							.equals(DocumentoDiSpesaManager
									.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA))
							|| decodifica
									.getDescrizioneBreve()
									.equals(DocumentoDiSpesaManager
											.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA)) || decodifica
							.getDescrizioneBreve()
							.equals(DocumentoDiSpesaManager
									.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING)))) {
				return true;

			} else {
				return false;
			}
		 
	}

	private List<String> getStatiValidiDocDiSpesaPerDichiarazioneFinale() {
		List<String> statiAmmessi = null;
		statiAmmessi = new ArrayList<String>();
		statiAmmessi
				.add(DocumentoDiSpesaManager
						.getCodiceStatoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.STATO_DICHIARABILE));
		return statiAmmessi;
	}

	/*private List<String> getStatiValidiPagamento() {
		List<String> statiPagamentoAmmessi = new ArrayList<String>();
		statiPagamentoAmmessi
				.add(PagamentiManager
						.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_INSERITO));
		statiPagamentoAmmessi
				.add(PagamentiManager
						.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_RESPINTO));
		return statiPagamentoAmmessi;
	}*/

	/**
	 * Verifica che il numero totale delle ore lavorate non superi il monte ore
	 * orario del fornitore
	 * 
	 * @param documentoDiSpesaVO
	 * @param filtroDichiarazioneDiSpesa
	 * @return
	 */
	private boolean isMonteOreSuperato(
			DocumentoSpesaDaInviareVO documentoDiSpesaVO,
			DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa) {
	 

			/*
			 * Calcolo il totale delle ore lavorate
			 */
			/*
			 * FIX PBandi-580: Il totale delle ore lavorate e' calcolato su
			 * tutti i cedolini del fornitore
			 */

			QuotaParteTipoDocumentoFornitoreVO filtroCedolino = new QuotaParteTipoDocumentoFornitoreVO();
			filtroCedolino.setIdFornitore(documentoDiSpesaVO.getIdFornitore()
					.longValue());
			filtroCedolino
					.setDescBreveTipoDocSpesa(DocumentoDiSpesaManager
							.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_CEDOLINO));

			QuotaParteTipoDocumentoFornitoreVO filtroAutodichiarazioneSoci = new QuotaParteTipoDocumentoFornitoreVO();
			filtroAutodichiarazioneSoci.setIdFornitore(documentoDiSpesaVO
					.getIdFornitore().longValue());
			filtroAutodichiarazioneSoci
					.setDescBreveTipoDocSpesa(DocumentoDiSpesaManager
							.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI));
			List<QuotaParteTipoDocumentoFornitoreVO> quote = genericDAO.where(
					Condition.filterBy(filtroCedolino).or(
							Condition.filterBy(filtroAutodichiarazioneSoci)))
					.select();

			double totaleOreLavorate = 0.0;
			for (QuotaParteTipoDocumentoFornitoreVO quota : quote) {
				Double oreLavorate = quota.getOreLavorate() == null ? 0
						: NumberUtil.toDouble(quota.getOreLavorate());
				totaleOreLavorate += oreLavorate;
			}

			/*
			 * Ricavo il monte ore del fornitore
			 */
			Long idFornitore = documentoDiSpesaVO.getIdFornitore().longValue();
			PbandiRFornitoreQualificaVO qualifica = pbandiDichiarazioneDiSpesaDAO
					.findQualificaFornitore(idFornitore);
			if (qualifica == null)
				return true;

			// FIXME }L{ da rimuovere
			double monteOreFornitore = 0d; // NumberUtil.getDoubleValue(qualifica.getMonteOre());
			/*
			 * FIX: PBandi-550 Correzione arrotondamento
			 */
			if (NumberUtil.toRoundDouble(totaleOreLavorate) > NumberUtil
					.toRoundDouble(monteOreFornitore))
				return true;
			else
				return false;

	}

	/**
	 * Verifica che la somma di tutte le quote parte del documento di
	 * spesa/progetto sia maggiore o  uguale all' importo rendicontabile del documento di
	 * spesa
	 * 
	 * @param documentoDiSpesaVO
	 * @param filtroDichiarazioneDiSpesa
	 * @return
	 */
	private boolean isRendicontabileCompletamenteAssociato(
			DocumentoSpesaDaInviareVO documentoDiSpesaVO,
			DichiarazioneDiSpesaDTO filtroDichiarazioneDiSpesa) {

		if (NumberUtil.toRoundDouble(NumberUtil.toDouble(documentoDiSpesaVO
				.getTotaleImportoQuotaParte())) >= NumberUtil
				.toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO
						.getImportoRendicontazione()))) {
			return true;
		} else {
			return false;
		}
		

	}

	private boolean isNotaDiCredito(DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
		if (documentoDiSpesaVO.getDescBreveTipoDocSpesa().equalsIgnoreCase(
				DocumentoDiSpesaManager.DESC_BREVE_NOTA_CREDITO))
			return true;
		return false;
	}


	
	private boolean isCompletamenteQuietanziato(
			DocumentoSpesaDaInviareVO documentoDiSpesaVO,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO)
			throws ManagerException {
		boolean result = true;
		double importoTotaleDocumento = 0;
		/*
		 * Se il documento e' un CEDOLINO allora l' importo sul quale applicare
		 * l' algoritmo e' dato dal massimo tra l' importo rendicontabile e
		 * l'importo del documento. In tutti gli altri casi l' importo e'
		 * rappresentato dal totale del documento.
		 */
		if (documentoDiSpesaManager
				.isCedolinoOAutodichiarazioneSoci(documentoDiSpesaVO)) {
			double totaleDocumento = NumberUtil
					.getDoubleValue(documentoDiSpesaVO
							.getImportoTotaleDocumento());
			double importoRendicontabile = NumberUtil
					.getDoubleValue(documentoDiSpesaVO
							.getImportoRendicontazione());
			if (totaleDocumento > importoRendicontabile)
				importoTotaleDocumento = totaleDocumento;
			else
				importoTotaleDocumento = importoRendicontabile;
		} else {
			importoTotaleDocumento = NumberUtil
					.getDoubleValue(documentoDiSpesaVO
							.getImportoTotaleDocumento());
		}
		List<Long> idDocumenti = new ArrayList<Long>();
		idDocumenti.add(documentoDiSpesaVO.getIdDocumentoDiSpesa().longValue());
		/*
		 * Recupero le eventuali note di credito alle quali il documento e'
		 * associato e calcolo il totale del documento al netto di eventuali
		 * note di credito
		 */
		/*
		 * FIX. PBandi-471. Riattivo il controllo per le note di credito.
		 */

		importoTotaleDocumento = importoTotaleDocumento
				- NumberUtil.getDoubleValue(documentoDiSpesaVO
						.getTotaleNoteCredito());

		/*
		 * FIX: PBandi-550 Correzione arrotondamento
		 */
		if (NumberUtil.toRoundDouble(importoTotaleDocumento) > NumberUtil
				.toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO
						.getTotaleImportoPagamenti())))
			result = false;

		return result;

	}

	/**
	 * // 1) creazione report su index // 2) salva su PBANDI_T_DOCUMENTO_INDEX
	 * // 3) salva su PBANDI_T_DOCUMENTO_INDEX , PBANDI_R_PAGAMENTO_DICH_SPESA
	 * where PBANDI_R_PAGAMENTO_DICH_SPESA.ID_DICHIARAZIONE_SPESA=
	 * PBANDI_T_DOCUMENTO_INDEX.ID_DOCUMENTO_INDEX 3a) conclude il task di bpm
	 * (operazione non annullabile in caso di fallimento) // 4) restituisce i
	 * byte del pdf
	 */
	public EsitoOperazioneScaricaDichiarazioneSpesa scaricaDichiarazioneDiSpesa(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"dichiarazioneDiSpesaDTO" };

		EsitoOperazioneScaricaDichiarazioneSpesa result = new EsitoOperazioneScaricaDichiarazioneSpesa();
		result.setEsito(Boolean.FALSE);

		try {
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, dichiarazioneDiSpesaDTO);

			logger.shallowDump(dichiarazioneDiSpesaDTO, "info");

			result.setPdfBytes(indexDAO
					.recuperaContenuto(dichiarazioneDiSpesaDTO
							.getUuidDocumento()));

			result.setNomeFile(dichiarazioneDiSpesaDTO.getNomeFile());
			result.setEsito(Boolean.TRUE);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			RuntimeException re = new RuntimeException(
					"Documento non trovato su servizio INDEX");
			throw re;
		}  
	}

	private void aggiornaStatoDocumentiDiSpesa(java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			Long idDichiarazioneDiSpesa, List<Long> idDocumentiDiSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {

		// // a) cerco tutti i doc di spesa
		// java.util.List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO>
		// allDocumenti = pbandiDocumentiDiSpesaDAO
		// .findDocumentiDiSpesa(dichiarazioneDiSpesaDTO,
		// statiAmmessiDocumento,statiAmmessiPagamento);
		//
		// // 25 maggio: elisabetta conferma che cambiano stato solo quelli
		// // bonificati
		//
		if (!isEmpty(idDocumentiDiSpesa)) {

			// d) cerco stato dichiarato
			String codiceStatoDichiarato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE;

			DecodificaDTO decodificaStatoDichiarato = decodificheManager
					.findDecodifica(
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
							codiceStatoDichiarato);
			if (isNull(decodificaStatoDichiarato)
					|| isNull(decodificaStatoDichiarato.getId()))
				throw new RuntimeException("Non trovato id per codice "
						+ codiceStatoDichiarato);

			pbandiDocumentiDiSpesaDAO.updateStatoDocumentiDiSpesa(idUtente,
					idDocumentiDiSpesa, dichiarazioneDiSpesaDTO.getIdProgetto(), decodificaStatoDichiarato.getId());
			
			/*
			 * FIX PBandi-2314.
			 * Se il documento e' in stato DA COMPLETARE ed il totale del documento
			 * al netto delle note di credito e' minore o uguale al totale dei pagamenti
			 * allora lo stato per il documento-progetto che ha subito gia' una validazione
			 * deve passare da DA COMPLETARE allo stato della validazione. 
			 */
			DecodificaDTO statoDaCompletare = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE);
			DecodificaDTO statoValidato = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO);
			DecodificaDTO statoNonValidato = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO);
			DecodificaDTO statoParzValidato = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO);
			DecodificaDTO statoDichiarabile = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE);
			DecodificaDTO statoRespinto = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_RESPINTO);
			
			
			for (Long idDoc : idDocumentiDiSpesa) {
				/*
				 * Verifico se esiste almento un progetto in cui
				 * lo stato del documento e' DA COMPLETARE
				 */
				PbandiRDocSpesaProgettoVO filterDocPrj = new PbandiRDocSpesaProgettoVO();
				filterDocPrj.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDoc));
				filterDocPrj.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(statoDaCompletare.getId()));
				
				
				 List<PbandiRDocSpesaProgettoVO> docPrjDaCompletare= genericDAO.findListWhere(filterDocPrj);
				
				if (!ObjectUtil.isEmpty(docPrjDaCompletare)) {
					PbandiTDocumentoDiSpesaVO filter = new PbandiTDocumentoDiSpesaVO();
					filter.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDoc));
					PbandiTDocumentoDiSpesaVO documento = genericDAO.findSingleWhere(filter);
					
					BigDecimal importoTotaleDocumento = documento.getImportoTotaleDocumento() == null ? new BigDecimal(0.0) : documento.getImportoTotaleDocumento();
					
					
					/*
					 * Calcolo il totale dei pagamenti
					 */
					BigDecimal importoTotalePagamenti = documentoDiSpesaManager.getImportoTotalePagamenti(dichiarazioneDiSpesaDTO.getIdProgetto(), idDoc);
					
					/*
					 * Calcolo il totale delle note di credito
					 */
					BigDecimal importoTotaleNote = documentoDiSpesaManager.getImportoTotaleNoteCredito(idDoc);
					
					
					BigDecimal totaleDocumentoNetto = NumberUtil.subtract(importoTotaleDocumento, importoTotaleNote);
					if (NumberUtil.compare(totaleDocumentoNetto, importoTotalePagamenti) <= 0) {
						/*
						 * Aggiorno lo stato dei documento-progetto in stato DA_COMPLETARE.
						 * Per i documento-progetto che hanno lo stato validazione in VALIDATO, NON VALIDATO o PARZIALMENTE VALIDATO
						 * alllora lo stato del documento-progetto sara' lo stato della validazione,mentre per i documento-progetti
						 * per i quali lo stato della validazione e' RESPINTO allora lo stato della documento-progetto
						 * sara' DICHIARABILE
						 */
						
						PbandiRDocSpesaProgettoVO filtroDoc = new PbandiRDocSpesaProgettoVO();
						filtroDoc.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDoc));
						
						PbandiRDocSpesaProgettoVO filtroPrj = new PbandiRDocSpesaProgettoVO();
						filtroPrj.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(dichiarazioneDiSpesaDTO.getIdProgetto()));
						
						List<PbandiRDocSpesaProgettoVO> filtroStatiValidazione = new ArrayList<PbandiRDocSpesaProgettoVO>();
						// stato validazione VALIDATO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneValidato = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneValidato.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoValidato.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneValidato);
						// stato validazione NON VALIDATO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneNonValidato = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneNonValidato.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoNonValidato.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneNonValidato);
						// stato validazione PARZIALMENTO VALIDATO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneParzValidato = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneParzValidato.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoParzValidato.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneParzValidato);
						// stato validazione RESPINTO
						PbandiRDocSpesaProgettoVO filtroStatoValidazioneRespinto = new PbandiRDocSpesaProgettoVO();
						filtroStatoValidazioneRespinto.setIdStatoDocumentoSpesaValid(NumberUtil.toBigDecimal(statoRespinto.getId()));
						filtroStatiValidazione.add(filtroStatoValidazioneRespinto);
						
						// stato  DA COMPLETARE
						PbandiRDocSpesaProgettoVO filtroStatoDaCompletare = new PbandiRDocSpesaProgettoVO();
						filtroStatoDaCompletare.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(statoDaCompletare.getId()));
						
						
						AndCondition<PbandiRDocSpesaProgettoVO> andCondition = new AndCondition<PbandiRDocSpesaProgettoVO>(
								Condition.filterBy(filtroDoc), 
								Condition.not(Condition.filterBy(filtroPrj)), 
								Condition.filterBy(filtroStatiValidazione), 
								Condition.filterBy(filtroStatoDaCompletare)
						);
						
						List<PbandiRDocSpesaProgettoVO> documenti = genericDAO.findListWhere(andCondition);
						
						for (PbandiRDocSpesaProgettoVO d : documenti) {
							/*
							 * Se lo stato validazione e' RESPINTO allora lo stato del documento sara' DICHIARABILE,
							 * mentre in tutti gli altri casi lo stato del documento sara' lo stesso dello stato
							 * della validazione
							 */
							if (NumberUtil.compare(d.getIdStatoDocumentoSpesaValid(), NumberUtil.toBigDecimal(statoRespinto.getId())) == 0) {
								d.setIdStatoDocumentoSpesa( NumberUtil.toBigDecimal(statoDichiarabile.getId()));
							} else {
								d.setIdStatoDocumentoSpesa( d.getIdStatoDocumentoSpesaValid() );
							}
							d.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
							try {
								logger.warn("aggiorno stato doc di spesa id "+d.getIdDocumentoDiSpesa()+
										" con idstato:"+d.getIdStatoDocumentoSpesa());
								genericDAO.update(d);
							} catch (Exception e) {
								String msg = "Errore durante l'update su PBANDI_R_DOCSPESA_PROGETTO per il documento["+d.getIdDocumentoDiSpesa()+"] ed il progetto ["+d.getIdProgetto()+"]. ";
								logger.error(msg,e);
								throw new RuntimeException(msg);
							}
							
							/*
							 * Aggiorno anche lo stato delle note di credito
							 * con lo stato del documento-progetto
							 */
							PbandiTDocumentoDiSpesaVO filterNote = new PbandiTDocumentoDiSpesaVO();
							filterNote.setIdDocRiferimento(d.getIdDocumentoDiSpesa());
						    List<PbandiTDocumentoDiSpesaVO> note = genericDAO.findListWhere(filterNote);
						    for (PbandiTDocumentoDiSpesaVO nota : note) {
						    	PbandiRDocSpesaProgettoVO notaprj = new PbandiRDocSpesaProgettoVO();
						    	notaprj.setIdDocumentoDiSpesa(nota.getIdDocumentoDiSpesa());
						    	notaprj.setIdProgetto(d.getIdProgetto());
						    	notaprj.setIdStatoDocumentoSpesa(d.getIdStatoDocumentoSpesa());
						    	notaprj.setIdUtenteAgg(NumberUtil.toBigDecimal(idUtente));
						    	try {
									genericDAO.update(notaprj);
								} catch (Exception e) {
									String msg = "Errore durante l'update su PBANDI_R_DOCSPESA_PROGETTO per il documento["+nota.getIdDocumentoDiSpesa()+"] ed il progetto ["+d.getIdProgetto()+"]. ";
									logger.error(msg,e);
									throw new RuntimeException(msg);
								}
						    }
						}
						
						
					}
					
				}
			}
			

			/*
			 *  aggiorna pagamenti
			 * 
			 * 
			 * 2. Pagamento per tutti i pagamenti dei documenti di spesa che
			 * sono stati �dichiarati" � StatoValidazioneSpesa uguale a
			 * DICHIARATO o RILASCIATO � Inserire la relazione con la
			 * DichiarazioneDiSpesa
			 */

			
			
			if (idDocumentiDiSpesa != null && idDocumentiDiSpesa.size() > 0) {
				
				List<PagamentoDocumentoProgettoVO> listFilterDoc = new ArrayList<PagamentoDocumentoProgettoVO>();
				List<PagamentoDocumentoDichiarazioneVO> listFilterDocDich = new ArrayList<PagamentoDocumentoDichiarazioneVO>();
				
				for (Long idDocumento : idDocumentiDiSpesa) {
					PagamentoDocumentoProgettoVO filtroDoc = new PagamentoDocumentoProgettoVO();
					filtroDoc.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumento));
					listFilterDoc.add(filtroDoc);
					PagamentoDocumentoDichiarazioneVO filtroDocDich = new PagamentoDocumentoDichiarazioneVO();
					filtroDocDich.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumento));
					listFilterDocDich.add(filtroDocDich);
				}
				
				PagamentoDocumentoProgettoVO filtroProgetto = new PagamentoDocumentoProgettoVO();
				filtroProgetto.setIdProgetto(NumberUtil.toBigDecimal(dichiarazioneDiSpesaDTO.getIdProgetto()));
				/*
				 * I Pagamenti non devono essere stati utilizzati in altre
				 * dichiarazioni del progetto
				 */
				filtroProgetto.setFlagIsUsedDichPrj(Constants.FLAG_FALSE);
				
				AndCondition<PagamentoDocumentoProgettoVO> andCondition = new AndCondition<PagamentoDocumentoProgettoVO>(Condition.filterBy(filtroProgetto), Condition.filterBy(listFilterDoc));
				
				List<PagamentoDocumentoProgettoVO> pagamentiDisponibili = genericDAO.findListWhere(andCondition);
				List<PbandiRPagamentoDichSpesaVO> pagamenti = new ArrayList<PbandiRPagamentoDichSpesaVO>();
				
				for (PagamentoDocumentoProgettoVO pag : pagamentiDisponibili) {
					/*
					 * FIX PBANDI-2314
					 * I pagamenti da inserire sono i pagamenti  associati
					 * al documento per il quali esiste ancora del residuo e non sono
					 * stati utilizzati in altre dichiarazioni del progetto
					 */
					BigDecimal residuoPagamento = pag.getResiduoUtilePagamento() == null ? new BigDecimal(0.0) : pag.getResiduoUtilePagamento();
					if (NumberUtil.compare(residuoPagamento, new BigDecimal(0.0)) > 0 && pag.getFlagIsUsedDichPrj().equals(Constants.FLAG_FALSE)) {
						PbandiRPagamentoDichSpesaVO pagamento = new PbandiRPagamentoDichSpesaVO();
						pagamento.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa));
						pagamento.setIdPagamento(pag.getIdPagamento());
						pagamenti.add(pagamento);
					}
				}
				
				if (!ObjectUtil.isEmpty(pagamenti)) {
					try {
						genericDAO.insert(pagamenti);
					}  catch (Exception e) {
						throw new DichiarazioneDiSpesaException("Errore durante l'inserimento in PBANDI_R_PAGAMENTO_DICH_SPESA.",e);
					}
				}
				
				
				/*
				 * Inserisco i dati dei pagamenti, della dichiarazionne di spesa e del documento
				 * nella tabella PBANDI_S_DICH_DOC_SPESA.
				 * Ricerco i pagamenti dei documenti associati alla dichiarazione di spesa corrente
				 */
				PagamentoDocumentoDichiarazioneVO filterDich = new PagamentoDocumentoDichiarazioneVO();
				filterDich.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa));
				filterDich.setAscendentOrder("idDocumentoDiSpesa");
				
				List<PagamentoDocumentoDichiarazioneVO> pagamentiDocumentiDichiarazione = genericDAO.findListWhere(Condition.filterBy(filterDich).and(Condition.filterBy(listFilterDocDich)));
				
				BigDecimal idDoc = null;
				List<BigDecimal> idPagamentiDocumento = new ArrayList<BigDecimal>();
				for (PagamentoDocumentoDichiarazioneVO pagDocDich : pagamentiDocumentiDichiarazione) {
					if (idDoc == null)
						idDoc = pagDocDich.getIdDocumentoDiSpesa();
					
					if (NumberUtil.compare(idDoc, pagDocDich.getIdDocumentoDiSpesa()) == 0) {
						idPagamentiDocumento.add(pagDocDich.getIdPagamento());
					} else {
						/*
						 * Rottura per il documento di spesa.
						 * Inserisco i pagamenti-documento-dichiarazione precedenti
						 */
						try {
							pbandiDichiarazioneDiSpesaDAO.inserisciDocumentoPagamentiDichiarazione(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa), idDoc, idPagamentiDocumento);
						} catch (Exception e) {
							
							String msg = "Errore durante l'insert su PBANDI_S_DICH_DOC_SPESA per il documento["+idDoc+"] e la dichiarazione ["+idDichiarazioneDiSpesa+"]. ";
							logger.error(msg,e);
							throw new RuntimeException(msg);
							
						}
						/*
						 * IL documento corrente diviene il nuovo idDoc
						 */
						idDoc = pagDocDich.getIdDocumentoDiSpesa();
						
						/*
						 * Reset della lista dei pagamenti del documento
						 */
						idPagamentiDocumento = new ArrayList<BigDecimal>(); 
						
						/*
						 * Inserisco nella lista dei pagamenti, il pagamento
						 * corrente
						 */
						idPagamentiDocumento.add(pagDocDich.getIdPagamento());
					}
				}
				
				/*
				 * Inserisco l' ultima lista dei pagamenti
				 */
				if (!ObjectUtil.isEmpty(idPagamentiDocumento)) {
					try {
						pbandiDichiarazioneDiSpesaDAO.inserisciDocumentoPagamentiDichiarazione(NumberUtil.toBigDecimal(idDichiarazioneDiSpesa), idDoc, idPagamentiDocumento);
					} catch (Exception e) {
						
						String msg = "Errore durante l'insert su PBANDI_S_DICH_DOC_SPESA per il documento["+idDoc+"] e la dichiarazione ["+idDichiarazioneDiSpesa+"]. ";
						logger.error(msg,e);
						throw new RuntimeException(msg);
						
					}
					
				}
				
			}

		} else {
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
			logger.error("Non sono stati trovati documenti di spesa.", ex);
			throw ex;
			// Errore! Non sono stati trovati dei documenti di spesa da
			// rilasciare nella dichiarazione per il periodo selezionato
		}
	}

	public DichiarazioneDiSpesaReportDTO findDatiDichiarazioneForReport(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idProgetto, java.util.Date dal, java.util.Date al)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException {
	 
			/**
			 * Gli stati dei pagamenti sono I (INSERITO) e T(RESPINTO)
			 */
			 
			/**
			 * Carico i dati del legale rappresentante
			 */
			RappresentanteLegaleDTO rappresentante = findAnagraficaRappresentanteLegale(idProgetto);

			/**
			 * Carico i dati del progetto
			 */
			ProgettoVO progetto = dichiarazioneDiSpesaManager.findDatiProgetto(
					idProgetto, al, null,  null);
			ProgettoDTO progettoDTO = new ProgettoDTO();
			getBeanUtil().valueCopy(progetto, progettoDTO);
			/**
			 * Carico i dati dell' ente di appartenenza
			 */
			EnteAppartenenzaDTO ente = findEnteAppartenenza(idProgetto,
					DichiarazioneDiSpesaSrv.CODICE_RUOLO_ENTE_DESTINATARIO);

			/**
			 * Carico i dati del beneficiario
			 */
			BeneficiarioVO beneficiarioVO = findBeneficiario(1L, idProgetto);
			BeneficiarioDTO beneficiario = new BeneficiarioDTO();
			getBeanUtil().valueCopy(beneficiarioVO, beneficiario);

			DichiarazioneDiSpesaReportDTO dichiarazione = new DichiarazioneDiSpesaReportDTO();
			dichiarazione.setBeneficiario(beneficiario);
			dichiarazione.setEnte(ente);
			dichiarazione.setProgetto(progettoDTO);
			dichiarazione.setRappresentanteLegale(rappresentante);
			dichiarazione.setDataAl(al);
			dichiarazione.setDataDal(dal);

			return dichiarazione;

		 
	}

	public it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.VoceDiCostoDTO[] findVociDiCosto(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idProgetto, java.util.Date dal, java.util.Date al)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException {

			return null;

	}

	public DocumentoContabileDTO[] findDocumentiContabili(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idProgetto, java.util.Date dal, java.util.Date al)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException {
		 
			return null;
		 
	}

	public void setPbandipagamentiDAO(PbandiPagamentiDAOImpl pbandipagamentiDAO) {
		this.pbandipagamentiDAO = pbandipagamentiDAO;
	}

	public PbandiPagamentiDAOImpl getPbandipagamentiDAO() {
		return pbandipagamentiDAO;
	}

	public EsitoOperazioneInviaDichiarazione inviaDichiarazioneDiSpesa(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, String codUtente,
			IstanzaAttivitaDTO istanzaAttivita,
			RappresentanteLegaleDTO rappresentanteLegale,
			Long idDelegato,
			RelazioneTecnicaDTO relazioneTecnica) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idBeneficiario", "idProgetto", "codUtente" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, dichiarazioneDiSpesaDTO, codUtente);

		ValidatorInput.verifyBeansNotEmpty(new String[] { "istanzaAttivita" },
				istanzaAttivita);

		logger.shallowDump(dichiarazioneDiSpesaDTO,"info");

		if (isEmpty(dichiarazioneDiSpesaDTO.getTipoDichiarazione()))
			throw new FormalParameterException("TIPO DICHIARAZIONE");

		if (Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE
				.equalsIgnoreCase(dichiarazioneDiSpesaDTO
						.getTipoDichiarazione())) {
			return inviaDichiarazioneFinale(idUtente, identitaDigitale,
					dichiarazioneDiSpesaDTO, codUtente, istanzaAttivita ,
					relazioneTecnica);
		} else if (Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTERMEDIA
				.equalsIgnoreCase(dichiarazioneDiSpesaDTO
						.getTipoDichiarazione())) {
			return inviaDichiarazioneIntermedia(idUtente, identitaDigitale,
					dichiarazioneDiSpesaDTO, codUtente, istanzaAttivita,
					rappresentanteLegale,idDelegato, relazioneTecnica);
		} else if (Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA
				.equalsIgnoreCase(dichiarazioneDiSpesaDTO
						.getTipoDichiarazione())) {
			return inviaDichiarazioneIntegrativa(idUtente, identitaDigitale,
					dichiarazioneDiSpesaDTO, codUtente, istanzaAttivita,
					rappresentanteLegale,idDelegato, relazioneTecnica);
		} else {
			throw new DichiarazioneDiSpesaException(
					"Attenzione! Tipo dichiarazione "
							+ dichiarazioneDiSpesaDTO.getTipoDichiarazione()
							+ " non supportato!");
		}
	}

	private void allegaRelazioneTecnica(Long idUtente,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			RelazioneTecnicaDTO relazioneTecnica, Long idDichiarazioneSpesa,
			Date currentDate) throws DocumentNotCreatedException {
		AllegatoRelazioneTecnicaDTO allegatoRelazioneTecnica = beanUtil
				.transform(relazioneTecnica, AllegatoRelazioneTecnicaDTO.class);
		allegatoRelazioneTecnica.setBytesDocumento(relazioneTecnica
				.getByteAllegato());
		allegatoRelazioneTecnica.setIdProgetto(beanUtil.transform(
				dichiarazioneDiSpesaDTO.getIdProgetto(), BigDecimal.class));
		BeneficiarioProgettoVO beneficiarioProgettoVO = new BeneficiarioProgettoVO();
		beneficiarioProgettoVO.setIdProgetto(beanUtil.transform(
				dichiarazioneDiSpesaDTO.getIdProgetto(), BigDecimal.class));
		beneficiarioProgettoVO.setIdSoggetto(beanUtil.transform(
				dichiarazioneDiSpesaDTO.getIdSoggetto(), BigDecimal.class));
		beneficiarioProgettoVO = genericDAO
				.findSingleWhere(beneficiarioProgettoVO);
		allegatoRelazioneTecnica.setCodiceProgetto(beneficiarioProgettoVO
				.getCodiceVisualizzatoProgetto());
		allegatoRelazioneTecnica.setCfBeneficiario(beneficiarioProgettoVO
				.getCodiceFiscaleSoggetto());
		allegatoRelazioneTecnica.setIdBeneficiario(beneficiarioProgettoVO
				.getIdSoggetto());
		allegatoRelazioneTecnica.setDataDichiarazione(currentDate);
		allegatoRelazioneTecnica.setIdDichiarazioneSpesa(beanUtil.transform(
				idDichiarazioneSpesa, BigDecimal.class));
		String shaHex = null;
		if(relazioneTecnica.getByteAllegato()!=null)
		  shaHex = DigestUtils.shaHex(relazioneTecnica.getByteAllegato());
		documentoManager.creaDocumento(idUtente, allegatoRelazioneTecnica,null,shaHex,null,null);
	}

	private PbandiTDocumentoIndexVO salvaInfoNodoDichiarazioneSuDb(java.lang.Long idUtente,
			Node nodoCreato, DichiarazioneDiSpesaVO dichiarazioneDiSpesaVO,
			Long idDichiarazioneDiSpesa,Long idRappLegale,Long idDelegato, Date currentDate, String nomeFile,String shaHex) {
		return getDocumentoManager()
				.salvaInfoNodoIndexSuDb(
						idUtente,
						nodoCreato,
						nomeFile,
						idDichiarazioneDiSpesa,idRappLegale,idDelegato,
						dichiarazioneDiSpesaVO.getIdProgetto(),
						GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_DI_SPESA,
						PbandiTDichiarazioneSpesaVO.class, null,  shaHex);
	}

	// +Green: aggiunto test su DichiarazioneDiSpesaDTO.IdProgettoContributoPiuGreen.
	public EsitoOperazioneAnteprimaDichiarazioneSpesa anteprimaDichiarazioneDiSpesa(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			RappresentanteLegaleDTO rappresentanteLegale,
			Long idDelegato) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"dichiarazioneDiSpesaDTO", "rappresentanteLegale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, dichiarazioneDiSpesaDTO, rappresentanteLegale);
			
		logger.info("\n\n\n\nanteprimaDichiarazioneDiSpesa ");
		logger.shallowDump(dichiarazioneDiSpesaDTO,"info");

		EsitoOperazioneAnteprimaDichiarazioneSpesa result = null;
		Long id = dichiarazioneDiSpesaDTO.getIdProgettoContributoPiuGreen();
		if (id == null) {
			logger.info("anteprimaDichiarazioneDiSpesa: IdProgettoContributoPiuGreen nullo: chiamo creaAnteprimaDichiarazioneDiSpesa().");
			result = creaAnteprimaDichiarazioneDiSpesa(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO, rappresentanteLegale, idDelegato);
		} else {
			logger.info("anteprimaDichiarazioneDiSpesa: IdProgettoContributoPiuGreen = "+id+" : chiamo creaAnteprimaDichiarazioneDiSpesaPiuGreen().");
			result = creaAnteprimaDichiarazioneDiSpesaPiuGreen(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO, rappresentanteLegale, idDelegato);
		}

		return result;	
	}

	public EsitoOperazioneAnteprimaDichiarazioneSpesa creaAnteprimaDichiarazioneDiSpesa(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			RappresentanteLegaleDTO rappresentanteLegale,
			Long idDelegato) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {
		
			// NOTA: in casi di modifiche, aggiornare anche creaAnteprimaDichiarazioneDiSpesaPiuGreen().
		   
			SessionContext ejbSession = ejbSessionContext.getEjbSession();

			EsitoOperazioneAnteprimaDichiarazioneSpesa result = new EsitoOperazioneAnteprimaDichiarazioneSpesa();
	
			logger.shallowDump(dichiarazioneDiSpesaDTO, "info");

			/*
			 *Recupero i documenti validi
			 */

			EsitoOperazioneVerificaDichiarazioneSpesa esitoVerificaDichiarazioneDiSpesa = verificaDichiarazioneDiSpesa(
					idUtente, identitaDigitale, dichiarazioneDiSpesaDTO);

			DocumentoDiSpesaDTO[] documentiDiSpesaInviabili = esitoVerificaDichiarazioneDiSpesa
					.getDocumentiDiSpesa();

			List<Long> idDocumenti = extractId(documentiDiSpesaInviabili);
			logger.info("[DichiarazioneDiSpesaBusinessImpl::creaAnteprimaDichiarazioneDiSpesa() ] dto: "+ dichiarazioneDiSpesaDTO.toString());
			/*
			 * 1) CREO LA DICHIARAZIONE DI SPESA
			 */

			/*
			 * 19/08/2010 GERRY SANSEVERINO: non va bene la sysdate, dobbiamo
			 * mettere come data fine quella della presentation(periodo al), non
			 * la sysdate. Inoltre periodo dal bisogna mettere la data
			 * presentazione domanda
			 */
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto( BigDecimal.valueOf(dichiarazioneDiSpesaDTO.getIdProgetto()));
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
			PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO();
			pbandiTDomandaVO.setIdDomanda(pbandiTProgettoVO.getIdDomanda());
			pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO);
			
			DichiarazioneDiSpesaVO dichiarazioneDiSpesaVO = new DichiarazioneDiSpesaVO();
			dichiarazioneDiSpesaVO.setCodiceProgetto(dichiarazioneDiSpesaDTO.getCodiceProgetto());
			dichiarazioneDiSpesaVO.setCodiceFiscaleBeneficiario(dichiarazioneDiSpesaDTO.getCodiceFiscaleBeneficiario());
			dichiarazioneDiSpesaVO.setDataFineRendicontazione(dichiarazioneDiSpesaDTO.getDataFineRendicontazione());
			dichiarazioneDiSpesaVO.setDataInizioRendicontazione(pbandiTDomandaVO.getDtPresentazioneDomanda());
			
			Long idDichiarazioneDiSpesa = new Long(0);

			dichiarazioneDiSpesaVO.setIdDichiarazione(idDichiarazioneDiSpesa);
			dichiarazioneDiSpesaVO.setIdProgetto(dichiarazioneDiSpesaDTO.getIdProgetto());
			dichiarazioneDiSpesaVO.setIdSoggetto(dichiarazioneDiSpesaDTO.getIdSoggetto());
			dichiarazioneDiSpesaVO.setIdUtente(idUtente);
			Date currentDate = new Date(System.currentTimeMillis());
			dichiarazioneDiSpesaVO.setDataDichiarazioneDiSpesa(currentDate);
			dichiarazioneDiSpesaVO.setIdTipoDichiarazSpesa(getIdTipoDichiarazione(dichiarazioneDiSpesaDTO
							.getTipoDichiarazione()));
			// eliminare un'eventuale dich di spesa con id a 0
			try {
				PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO(BigDecimal.valueOf(idDichiarazioneDiSpesa));
				genericDAO.delete(vo);
			} catch (Exception e) {
				logger.error(
						"cancellazione dich spesa con id 0,potrebbe non esseri",
						e);
			}

			pbandiDichiarazioneDiSpesaDAO.inserisci(dichiarazioneDiSpesaVO);
			
			/*
			 * 2) Aggiorna stato documenti di spesa a dichiarato e dei pagamenti
			 */

			if (!isEmpty(idDocumenti)) {
				aggiornaStatoDocumentiDiSpesa(idUtente, identitaDigitale,
						dichiarazioneDiSpesaDTO, idDichiarazioneDiSpesa,
						idDocumenti);
			}

			/*
			 * 3) Ripartisco i pagamenti
			 */

			/*
			 * Se non ci sono documenti da validare restituisco il messaggio
			 */
			if (isEmpty(idDocumenti)) {
				idDocumenti = new ArrayList<Long>();
			} else {
				ripartisciPagamenti(idUtente, dichiarazioneDiSpesaDTO,
						idDocumenti, idDichiarazioneDiSpesa);
			}

			if (ejbSession == null) {
				throw new DichiarazioneDiSpesaException(
						ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
			}

			

			/*
			 * 4) CREO IL REPORT
			 */

			byte pdfBytes[] = creaReportDichiarazioneDiSpesaDinamico(idUtente,
					idDichiarazioneDiSpesa, idDocumenti,
					dichiarazioneDiSpesaDTO, rappresentanteLegale, idDelegato,true);

			if (pdfBytes == null)
				throw new DichiarazioneDiSpesaException(
						ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);

			/*
			 * 5) CREO IL NOME DEL FILE DEL REPORT
			 */
			// uso la sequence+la data come identificatore univoco
			String nomeFile = creaNomefile(idDichiarazioneDiSpesa, currentDate);

			/*
			 * Completo il risultato
			 */
			result.setEsito(Boolean.TRUE);
			result.setNomeFile(nomeFile);
			result.setPdfBytes(pdfBytes);
			result.setDichiarazioneDiSpesa(dichiarazioneDiSpesaDTO);
			ejbSession.setRollbackOnly();

			return result;		
	}
	

	// +Green: inizio
	// Variante del metodo creaAnteprimaDichiarazioneDiSpesa() per gestire 
	// la stampa della DS per il progetto a contributo +green.
	// Dati del report:
	//  - documenti validi: idProgettoFinanziamento (gli stessi per finanaziamento e contributo).
	public EsitoOperazioneAnteprimaDichiarazioneSpesa creaAnteprimaDichiarazioneDiSpesaPiuGreen(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			RappresentanteLegaleDTO rappresentanteLegale,
			Long idDelegato) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {		    
		   
			SessionContext ejbSession = ejbSessionContext.getEjbSession();

			EsitoOperazioneAnteprimaDichiarazioneSpesa result = new EsitoOperazioneAnteprimaDichiarazioneSpesa();
			
			/*
			 *Recupero i documenti validi
			 */

			EsitoOperazioneVerificaDichiarazioneSpesa esitoVerificaDichiarazioneDiSpesa = verificaDichiarazioneDiSpesa(
					idUtente, identitaDigitale, dichiarazioneDiSpesaDTO);

			DocumentoDiSpesaDTO[] documentiDiSpesaInviabili = esitoVerificaDichiarazioneDiSpesa
					.getDocumentiDiSpesa();

			List<Long> idDocumenti = extractId(documentiDiSpesaInviabili);
			
			/*
			 * 1) CREO LA DICHIARAZIONE DI SPESA
			 * 		Nota: su db viene salvata un record PbandiTProgetto con id_dichiarazione = 0
			 * 			  questo record verr� usato per ottenere le voci di spesa nel pdf della ds.
			 */

			Long idDichiarazioneDiSpesa = new Long(0);
			Date currentDate = new Date(System.currentTimeMillis());
			
			
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto( BigDecimal.valueOf(dichiarazioneDiSpesaDTO.getIdProgetto()));
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
			PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO();
			pbandiTDomandaVO.setIdDomanda(pbandiTProgettoVO.getIdDomanda());
			pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO);
			
			DichiarazioneDiSpesaVO dichiarazioneDiSpesaVO = new DichiarazioneDiSpesaVO();
			dichiarazioneDiSpesaVO.setCodiceProgetto(dichiarazioneDiSpesaDTO.getCodiceProgetto());
			dichiarazioneDiSpesaVO.setCodiceFiscaleBeneficiario(dichiarazioneDiSpesaDTO.getCodiceFiscaleBeneficiario());
			dichiarazioneDiSpesaVO.setDataFineRendicontazione(dichiarazioneDiSpesaDTO.getDataFineRendicontazione());
			dichiarazioneDiSpesaVO.setDataInizioRendicontazione(pbandiTDomandaVO.getDtPresentazioneDomanda());
			
			dichiarazioneDiSpesaVO.setIdDichiarazione(idDichiarazioneDiSpesa);
			dichiarazioneDiSpesaVO.setIdProgetto(dichiarazioneDiSpesaDTO.getIdProgetto());
			dichiarazioneDiSpesaVO.setIdSoggetto(dichiarazioneDiSpesaDTO.getIdSoggetto());
			dichiarazioneDiSpesaVO.setIdUtente(idUtente);			
			dichiarazioneDiSpesaVO.setDataDichiarazioneDiSpesa(currentDate);
			dichiarazioneDiSpesaVO.setIdTipoDichiarazSpesa(getIdTipoDichiarazione(dichiarazioneDiSpesaDTO
							.getTipoDichiarazione()));

			// eliminare un'eventuale dich di spesa con id a 0
			try {
				PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO(BigDecimal.valueOf(idDichiarazioneDiSpesa));
				genericDAO.delete(vo);
			} catch (Exception e) {
				logger.error(
						"cancellazione dich spesa con id 0,potrebbe non esseri",
						e);
			}

			pbandiDichiarazioneDiSpesaDAO.inserisci(dichiarazioneDiSpesaVO);
			
						
			/*
			 * 2) Aggiorna stato documenti di spesa a dichiarato e dei pagamenti
			 */

			
			if (!isEmpty(idDocumenti)) {
				aggiornaStatoDocumentiDiSpesa(idUtente, identitaDigitale,
						dichiarazioneDiSpesaDTO, idDichiarazioneDiSpesa, idDocumenti);
			}
			

			/*
			 * 3) Ripartisco i pagamenti
			 */

			/*
			 * Se non ci sono documenti da validare restituisco il messaggio
			 */
			
			
			if (isEmpty(idDocumenti)) {
				idDocumenti = new ArrayList<Long>();
			} else {
				ripartisciPagamenti(idUtente, dichiarazioneDiSpesaDTO,
						idDocumenti, idDichiarazioneDiSpesa);
			}

			if (ejbSession == null) {
				throw new DichiarazioneDiSpesaException(
						ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
			}
			
			

			/*
			 * 4) CREO IL REPORT
			 */

			Long idDichiarazioneDiSpesaContributo = new Long(0);	// nelle anteprime, l'id della DS � sempre 0.
			byte pdfBytes[] = creaReportDichiarazioneDiSpesaDinamicoPiuGreen(idUtente,
					idDichiarazioneDiSpesa, idDichiarazioneDiSpesaContributo, idDocumenti,
					dichiarazioneDiSpesaDTO, rappresentanteLegale, idDelegato,true);

			if (pdfBytes == null)
				throw new DichiarazioneDiSpesaException(
						ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);

			/*
			 * 5) CREO IL NOME DEL FILE DEL REPORT
			 */
			// uso la sequence+la data come identificatore univoco
			String nomeFile = creaNomefile(idDichiarazioneDiSpesa, currentDate);

			/*
			 * Completo il risultato
			 */
			result.setEsito(Boolean.TRUE);
			result.setNomeFile(nomeFile);
			result.setPdfBytes(pdfBytes);
			result.setDichiarazioneDiSpesa(dichiarazioneDiSpesaDTO);
			ejbSession.setRollbackOnly();

			return result;	
	}
	// +Green: fine
	
	public EsitoOperazioneAnteprimaDichiarazioneSpesa ricreaPdf(Long idUtente,
			String identitaDigitale, Long idDichiarazioneDiSpesa,
			Long idSoggetto) throws CSIException, SystemException,
			UnrecoverableException, DichiarazioneDiSpesaException {

		EsitoOperazioneAnteprimaDichiarazioneSpesa result = new EsitoOperazioneAnteprimaDichiarazioneSpesa();

		/*
		 * Ricerco i documenti che fanno parte della dichiarazione di spesa
		 */
		List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> documentiValidi = pbandiDocumentiDiSpesaDAO
				.findDocumentiDiSpesaDichiarazione(idDichiarazioneDiSpesa);

		/*
		 * Recupero i dati della dichiarazione
		 */
		List<DichiarazioneDiSpesaVO> dichiarazioni = pbandiDichiarazioneDiSpesaDAO
				.findDatiDichiarazione(idDichiarazioneDiSpesa);

		if (dichiarazioni != null && dichiarazioni.size() > 0) {
			DichiarazioneDiSpesaDTO dichiarazione = new DichiarazioneDiSpesaDTO();
			BeanUtil.valueCopy(dichiarazioni.get(0), dichiarazione);
			byte pdfBytes[] = recreateReport(dichiarazione, documentiValidi,
					idDichiarazioneDiSpesa, idSoggetto, false);

			/*
			 * Creo il nome file
			 */
			String nomeFile = creaNomefile(idDichiarazioneDiSpesa,
					dichiarazione.getDataInizioRendicontazione());

			/*
			 * Completo il risultato
			 */
			result.setEsito(Boolean.TRUE);
			result.setNomeFile(nomeFile);
			result.setPdfBytes(pdfBytes);
		} else {
			DichiarazioneDiSpesaException e = new DichiarazioneDiSpesaException(
					ERRORE_DICHIARAZIONE_NON_PRESENTE);
			logger.error("Nessuna dato relativo alla dichiarazione "
					+ idDichiarazioneDiSpesa + " trovato.", e);
			throw e;
		}

		return result;
	}

	private RappresentanteLegaleDTO findAnagraficaRappresentanteLegale(
			Long idProgetto) {
	 
			/*
			 * FIX: PBandi-403 Nel caso in cui i rappresentanti legali sono > di
			 * 0 prendo il primo.
			 */
			List<RappresentanteLegaleDichiarazioneVO> rappresentantiVO = pbandiDichiarazioneDiSpesaDAO
					.findRappresentanteLegale(idProgetto, null);
			RappresentanteLegaleDichiarazioneVO rapprVO = new RappresentanteLegaleDichiarazioneVO();
			if (rappresentantiVO != null && rappresentantiVO.size() > 0) {
				rapprVO = rappresentantiVO.get(0);
			}

			return getBeanUtil().transform(rapprVO,
					RappresentanteLegaleDTO.class);

		 
	}

	private EnteAppartenenzaDTO findEnteAppartenenza(Long idProgetto,
			String codiceTipoRuoloEnte) {
		 
			EnteAppartenenzaVO enteVO = pbandiDichiarazioneDiSpesaDAO
					.findEnteAppartenenza(idProgetto, codiceTipoRuoloEnte);
			EnteAppartenenzaDTO enteDTO = new EnteAppartenenzaDTO();
			getBeanUtil().valueCopy(enteVO, enteDTO);
			return enteDTO;
		 
	}

	private byte[] recreateReport(
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> documentiValidi,
			Long idDichiarazione, Long idSoggetto, boolean isBozza)
			throws CSIException {
	 
			byte[] result = null;
			Long idProgetto = dichiarazioneDiSpesaDTO.getIdProgetto();
			Date dal = dichiarazioneDiSpesaDTO.getDataInizioRendicontazione();
			Date al = dichiarazioneDiSpesaDTO.getDataFineRendicontazione();
			Long idBandoLinea = dichiarazioneDiSpesaDTO.getIdBandoLinea();

			boolean isBr02 = regolaManager
					.isRegolaApplicabileForBandoLinea(idBandoLinea,
							RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);

			boolean isBr05 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA);

			boolean isBr16 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR16_GESTIONE_CAMPO_TASK);

			PbandiDTipoDichiarazSpesaVO tipoDichiarazioneVO = new PbandiDTipoDichiarazSpesaVO();
			tipoDichiarazioneVO
					.setIdTipoDichiarazSpesa(NumberUtil
							.toBigDecimal(getIdTipoDichiarazione(dichiarazioneDiSpesaDTO
									.getTipoDichiarazione())));

			tipoDichiarazioneVO = genericDAO
					.findSingleWhere(tipoDichiarazioneVO);

			/**
			 * Gli stati dei pagamenti sono R
			 */
			//List<String> statiAmmessiPagamento = new ArrayList<String>();
		//	statiAmmessiPagamento.add("R");

			/**
			 * Carico i dati del legale rappresentante
			 */
			RappresentanteLegaleDTO rappresentante = findAnagraficaRappresentanteLegale(idProgetto);

			/**
			 * Carico i dati del progetto
			 */

			List<Long> idDocumentiValidi = null;
			if (documentiValidi != null && documentiValidi.size() > 0) {
				idDocumentiValidi = beanUtil.extractValues(documentiValidi,
						"idDocumentoDiSpesa", Long.class);
			}

			ProgettoVO progetto = dichiarazioneDiSpesaManager.findDatiProgetto(
					idProgetto, al, idDocumentiValidi, 
					idDichiarazione);
			ProgettoDTO progettoDTO = new ProgettoDTO();
			getBeanUtil().valueCopy(progetto, progettoDTO);

			/**
			 * Carico i dati dell' ente di appartenenza
			 */
			EnteAppartenenzaDTO ente = findEnteAppartenenza(idProgetto,
					DichiarazioneDiSpesaSrv.CODICE_RUOLO_ENTE_DESTINATARIO);

			/**
			 * Carico i dati del beneficiario
			 */
			BeneficiarioVO beneficiarioVO = findBeneficiario(idSoggetto,
					idProgetto);

			BeneficiarioDTO beneficiario = new BeneficiarioDTO();
			getBeanUtil().valueCopy(beneficiarioVO, beneficiario);

			/**
			 * Creo il dto della dichiarazione utilizzata nel report
			 */
			DichiarazioneDiSpesaReportDTO dichiarazione = new DichiarazioneDiSpesaReportDTO();
			dichiarazione.setBeneficiario(beneficiario);
			dichiarazione.setEnte(ente);
			dichiarazione.setProgetto(progettoDTO);
			dichiarazione.setRappresentanteLegale(rappresentante);
			dichiarazione.setDataAl(al);
			dichiarazione.setDataDal(dal);
			dichiarazione.setIdDichiarazione(idDichiarazione);
			dichiarazione.setDescTipoDichiarazione(tipoDichiarazioneVO
					.getDescTipoDichiarazioneSpesa());

			/**
			 * Carico le voci di costo
			 */
			VoceDiCostoDTO[] vociDiCosto = findVociDiCostoPerRicreaReport(
					idProgetto, al, idDocumentiValidi);

			List<DocumentoContabileVO> documentiContabiliVO = dichiarazioneDiSpesaManager
					.findDocumentiContabili(idProgetto, al, idDocumentiValidi,
							  idDichiarazione);

			DocumentoContabileDTO[] documentiContabiliDTO = new DocumentoContabileDTO[documentiContabiliVO
					.size()];
			getBeanUtil().valueCopy(documentiContabiliVO.toArray(),
					documentiContabiliDTO);

			try {
				result = reportManager.createDichiarazioneDiSpesaPDF(
						dichiarazioneDiSpesaDTO, isBozza, isBr02, isBr05,
						isBr16, dichiarazione, vociDiCosto,
						documentiContabiliDTO);

			} catch (Exception e) {
				DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
						ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
				throw ex;
			}

			return result;
		 
	}

	private it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.VoceDiCostoDTO[] findVociDiCostoPerRicreaReport(
			Long idProgetto, Date al, List<Long> idDocumentiValidi) {
	 
			List<VoceDiCostoVO> vociVO = pbandiDichiarazioneDiSpesaDAO
					.findVociDiCostoForRicreaReport(idProgetto, al,
							idDocumentiValidi);
			VoceDiCostoDTO[] vociDTO = new VoceDiCostoDTO[vociVO.size()];
			getBeanUtil().valueCopy(vociVO.toArray(), vociDTO);
			return vociDTO;
	 
	}

	private BeneficiarioVO findBeneficiario(Long idSoggetto, Long idProgetto) {
		 
			return beanUtil.transform(
					getProgettoManager().findBeneficiario(idSoggetto,
							idProgetto), BeneficiarioVO.class);
		 
	}

	private String creaNomefile(Long idDichiarazione, Date currentDate) {
		String nomeFile;

		nomeFile = "DichiarazioneDiSpesa_" + idDichiarazione + "_"
				+ DateUtil.getTime(currentDate, TIME_FORMAT_PER_NOME_FILE)
				+ ".pdf";

		logger.info("nomeFile della dichiarazione di spesa : " + nomeFile);

		return nomeFile;
	}




	/**
	 * *************************************************************************
	 * ***************************************************************** DAL
	 * BRANCH
	 */

	/**
	 * Verifica che il totale dei pagamenti � superiore al totale della fattura,
	 * al netto delle note di credito.
	 * 
	 * @param documentoDiSpesaVO
	 * @param totaleNoteCredito
	 * @param totalePagamenti
	 * @return
	 */
	private boolean isImportoQuietanzatoSuperioreImportoDocumento(
			DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
		double totaleDocumento = documentoDiSpesaVO.getImportoTotaleDocumento() == null ? 0
				: documentoDiSpesaVO.getImportoTotaleDocumento().doubleValue();
		/*
		 * FIX: PBandi-550 Correzione arrotondamento
		 */
		if (NumberUtil.toRoundDouble(totaleDocumento
				- NumberUtil.toRoundDouble(NumberUtil
						.getDoubleValue(documentoDiSpesaVO
								.getTotaleNoteCredito()))) < NumberUtil
				.toRoundDouble(NumberUtil.getDoubleValue(documentoDiSpesaVO
						.getTotaleImportoPagamenti())))
			return true;
		else
			return false;

	}

	// private boolean scarta(
	// List<it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO>
	// scarti,
	// DocumentoSpesaDaInviareVO documentoDiSpesaVO) {
	// boolean scartato;
	// it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO
	// scartoDTO = new
	// it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO();
	// beanUtil.valueCopy(documentoDiSpesaVO, scartoDTO);
	// scarti.add(scartoDTO);
	// scartato = true;
	// return scartato;
	// }

	private void ripartisciPagamenti(Long idUtente,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			List<Long> idDocumenti,
			Long idDichiarazione) throws FormalParameterException {
		/*
		 * 1) Se il bando non segue la regola BR02 allora applico l' algoritmo
		 * che ripartisce l' importo del pagamento tra le voci di spesa
		 * associate per ogni documento di spesa valido
		 */
		Boolean isBr02 = regolaManager.isRegolaApplicabileForBandoLinea(
				dichiarazioneDiSpesaDTO.getIdBandoLinea(),
				RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);
		Long idProgetto = dichiarazioneDiSpesaDTO.getIdProgetto();

		if (!isBr02) {
			StringBuilder idDocumentiConcatenati = new StringBuilder();
			for (Long idDocumento : idDocumenti) {
				idDocumentiConcatenati.append(idDocumento.toString());
				idDocumentiConcatenati.append(",");
			}
			logger.warn("new BigDecimal(idProgetto) : "+ idProgetto +
					"\nidDocumentiConcatenati.toString() : "+idDocumentiConcatenati.toString()
					+"\nidDichiarazione:" +idDichiarazione+"\nidUtente:"+idUtente);
			
			if (!isEmpty(idDocumentiConcatenati.toString())) {
				genericDAO.callProcedure().ripartizionePagamenti(
						new BigDecimal(idProgetto),
						idDocumentiConcatenati.toString(),
						idDichiarazione,
						new BigDecimal(idUtente));
			}
		}

		/*
		 * Recupero le note di credito associate ai documenti validi e li
		 * aggiungo a questi ultimi
		 */
		// if(!isEmpty(idDocumenti)){
		// List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO>
		// noteDiCredito = findNoteDiCredito(
		// dichiarazioneDiSpesaDTO.getIdProgetto(), documentiValidi);
		//
		// documentiValidi.addAll(noteDiCredito);
		// }
	}

	public RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente,
			String identitaDigitale, Long idProgetto,
			Long idSoggettoRappresentante) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {
		 
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentanti = soggettoManager
					.findRappresentantiLegali(idProgetto,
							idSoggettoRappresentante);
			return beanUtil.transform(rappresentanti,
					RappresentanteLegaleDTO.class);
		 
	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setDocumentoDiSpesaManager(
			DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}

	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}

	public String findNoteChiusuraValidazione(Long idUtente,
			String identitaDigitale, Long idDichiarazione) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idDichiarazione" };
		 

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idDichiarazione);

			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(
					idDichiarazione));
			pbandiTDichiarazioneSpesaVO = genericDAO
					.findSingleWhere(pbandiTDichiarazioneSpesaVO);
			return pbandiTDichiarazioneSpesaVO.getNoteChiusuraValidazione();
	 
	}

	public boolean hasDichiarazioneFinale(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {
	 
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		boolean ret = false;
		try {

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			ret = dichiarazioneDiSpesaManager.hasDichiarazioneFinale(idUtente,
					identitaDigitale, idProgetto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return ret;
	}

	public boolean hasDichiarazioneFinaleOFinaleComunicazione(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException,
			DichiarazioneDiSpesaException {
	 
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		boolean ret = false;
		try {

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			ret = dichiarazioneDiSpesaManager
					.hasDichiarazioneFinaleOFinaleComunicazione(idUtente,
							identitaDigitale, idProgetto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return ret;
	}


	private EsitoOperazioneInviaDichiarazione inviaDichiarazioneIntermedia(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, String codUtente,
			IstanzaAttivitaDTO istanzaAttivita,
			RappresentanteLegaleDTO rappresentanteLegale,
			Long idDelegato,
			RelazioneTecnicaDTO relazioneTecnica)
			throws DichiarazioneDiSpesaException {
		Node nodoCreato = null;

		try {
			long start = getTimerManager().start();
			EsitoOperazioneInviaDichiarazione result = new EsitoOperazioneInviaDichiarazione();

			EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa = verificaDichiarazioneDiSpesa(
					idUtente, identitaDigitale, dichiarazioneDiSpesaDTO);

			DocumentoDiSpesaDTO[] documentiDiSpesa = verificaDichiarazioneDiSpesa
					.getDocumentiDiSpesa();
			
			Long idDichiarazioneDiSpesa = pbandiDichiarazioneDiSpesaDAO
					.getSeqPbandiTDichSpesa().nextLongValue();

			List<Long> idDocumenti = extractId(documentiDiSpesa);

			/*
			 * Se non ci sono documenti da validare restituisco il messaggio
			 */
			if (isEmpty(idDocumenti)) {
				result.setEsito(Boolean.FALSE);
				result.setMsg(ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);
			} else {

				// DATA DI SISTEMA PER LE VARIE INSERT e PER IL NOME FILE
				Date currentDate = new Date(System.currentTimeMillis());
				
				/*
				 * 1) Salvo la dichiarazione sul db
				 */

				PbandiTDomandaVO pbandiTDomandaVO = progettoManager
						.getDomandaByProgetto(dichiarazioneDiSpesaDTO
								.getIdProgetto());

				DichiarazioneDiSpesaVO dichiarazioneDiSpesaVO = new DichiarazioneDiSpesaVO();

				dichiarazioneDiSpesaVO
						.setCodiceProgetto(dichiarazioneDiSpesaDTO
								.getCodiceProgetto());
				dichiarazioneDiSpesaVO
						.setCodiceFiscaleBeneficiario(dichiarazioneDiSpesaDTO
								.getCodiceFiscaleBeneficiario());
				dichiarazioneDiSpesaVO
						.setDataFineRendicontazione(dichiarazioneDiSpesaDTO
								.getDataFineRendicontazione());

				dichiarazioneDiSpesaVO.setIdProgetto(dichiarazioneDiSpesaDTO
						.getIdProgetto());
				dichiarazioneDiSpesaVO.setIdSoggetto(dichiarazioneDiSpesaDTO
						.getIdSoggetto());

				dichiarazioneDiSpesaVO
						.setDataInizioRendicontazione(pbandiTDomandaVO
								.getDtPresentazioneDomanda());

				dichiarazioneDiSpesaVO
						.setIdDichiarazione(idDichiarazioneDiSpesa);

				dichiarazioneDiSpesaVO.setIdUtente(idUtente);

				dichiarazioneDiSpesaVO.setDataDichiarazioneDiSpesa(currentDate);
				dichiarazioneDiSpesaVO
						.setIdTipoDichiarazSpesa(getIdTipoDichiarazione(dichiarazioneDiSpesaDTO
								.getTipoDichiarazione()));
				// Se almeno 1 dei doc di questa ds � cartaceo, TipoInvioDs vale "C" (null altrimenti).
				logger.info("TipoInvioDs prima di inserire = "+dichiarazioneDiSpesaDTO.getTipoInvioDs());
				dichiarazioneDiSpesaVO.setTipoInvioDs(dichiarazioneDiSpesaDTO.getTipoInvioDs());
				
				logger.warn("insert idDichiarazioneDiSpesa with id:"+idDichiarazioneDiSpesa);
				pbandiDichiarazioneDiSpesaDAO.inserisci(dichiarazioneDiSpesaVO);
				
				
				logger.warn("idDichiarazioneDiSpesa inserted  with id:"	+ idDichiarazioneDiSpesa);
				
				
				/*
				 * Sposta i Tipo Allegati dalla tabella di supporto alla tabella principale
				 */
				tipoAllegatiManager.spostaTipoAllegati(dichiarazioneDiSpesaDTO.getIdBandoLinea(), dichiarazioneDiSpesaDTO.getIdProgetto(), idDichiarazioneDiSpesa);

				
				/*
				 * 2) Aggiorno lo stato dei documenti e dei pagamenti
				 * 
				 * Il sistema dovr� aggiornare i seguenti oggetti: 1.
				 * DocumentoDiSpesaProgetto per tutti i documenti di spesa che
				 * sono stati �dichiarati" � StatoDocumentoDiSpesa uguale a
				 * DICHIARATOLa dichiarazione di spesa � stata conclusa con
				 * successo. E� ora necessario compilare e quindi inviare la
				 * comunicazione di fine lavori per chiudere la rendicontazione
				 * del progetto.
				 */
				logger.warn("updating  stato documenti di spesa");
				aggiornaStatoDocumentiDiSpesa(idUtente, identitaDigitale,
						dichiarazioneDiSpesaDTO, idDichiarazioneDiSpesa,
						idDocumenti);
				
				

				/*
				 * 3) Ripartisco i pagamenti
				 */
				ripartisciPagamenti(idUtente, dichiarazioneDiSpesaDTO,
						idDocumenti, idDichiarazioneDiSpesa);

				if (!getTimerManager().checkTimeout(start))
					throw new DichiarazioneDiSpesaException(
							ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

				
			

				/*
				 * 4) Creo il report
				 */
				boolean isBozza = false;
				byte pdfBytes[] = creaReportDichiarazioneDiSpesaDinamico(
						idUtente, idDichiarazioneDiSpesa, idDocumenti,
						dichiarazioneDiSpesaDTO, rappresentanteLegale,  idDelegato, isBozza);

				if (pdfBytes == null)
					throw new DichiarazioneDiSpesaException(
							ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);

				/*
				 * 5) Creo il nome del file del report
				 */
				// uso la sequence+la data come identificatore univoco
				String nomeFile = creaNomefile(idDichiarazioneDiSpesa,
						currentDate);

				/*
				 * 6) Creo doc su index
				 */
				if (!getTimerManager().checkTimeout(start))
					throw new DichiarazioneDiSpesaException(
							ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

				nodoCreato = indexDAO.creaContentDichiarazioneDiSpesa(pdfBytes,
						nomeFile, dichiarazioneDiSpesaVO, idUtente);

				/*
				 * 7) Salvo il documento index rappresentativo della
				 * dichiarazione di spesa sul db.
				 */
				
				String shaHex = null;
				if(pdfBytes!=null)
				  shaHex = DigestUtils.shaHex(pdfBytes);
				
				PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = salvaInfoNodoDichiarazioneSuDb(idUtente, nodoCreato,
						dichiarazioneDiSpesaVO, idDichiarazioneDiSpesa,rappresentanteLegale.getIdSoggetto(),idDelegato,
						currentDate, nomeFile,shaHex);

				/*
				 * 8) Creo la relazione tecnica e la salvo du INDEX
				 */

				if (relazioneTecnica != null
						&& (!StringUtil.isEmpty(relazioneTecnica.getNomeFile()))) {
					allegaRelazioneTecnica(idUtente, dichiarazioneDiSpesaDTO,
							relazioneTecnica, idDichiarazioneDiSpesa,
							currentDate);
				}

				
				
				
				boolean isBrDemat= regolaManager
						.isRegolaApplicabileForBandoLinea(
								dichiarazioneDiSpesaDTO.getIdBandoLinea(),
								RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
				if(isBrDemat)
					associateAllegati(idDichiarazioneDiSpesa,dichiarazioneDiSpesaVO.getIdProgetto());
				
				if (!getTimerManager().checkTimeout(start))
					throw new DichiarazioneDiSpesaException(
							ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

				/*
				 * 9) Chiudo l' attivita'  .
				 */
				
				

				/*logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+dichiarazioneDiSpesaDTO.getIdProgetto());
				Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO.getIdProgetto());
				logger.warn("processo: "+processo);
				if(processo!=null){*/
					logger.warn("\n\n############################  NEOFLUX UNLOCK DICH_DI_SPESA  (intermedia) ##############################\n");
					neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO.getIdProgetto(),Task.DICH_DI_SPESA);
					logger.warn("############################ NEOFLUX UNLOCK (intermedia) ##############################\n\n\n\n");
				/*}else{
			//+flux+ 
					logger.warn("\n\n############################ OLDFLUX ##############################\ncalling getProcessManager().inviaDichiarazioneDiSpesa (intermedia)idProgetto :"+dichiarazioneDiSpesaDTO.getIdProgetto());
					processManager.inviaDichiarazioneDiSpesa(
							dichiarazioneDiSpesaDTO.getIdProgetto(), idUtente,
							identitaDigitale);
					logger.warn("inviaDichiarazioneDiSpesa (intermedia) OK\n############################ OLDFLUX ##############################\n\n\n\n");
				}
				*/
				
				/*
				 * Completo il risultato
				 */
				dichiarazioneDiSpesaDTO.setNomeFile(nomeFile);
				dichiarazioneDiSpesaDTO.setUuidDocumento(nodoCreato.getUid());
				dichiarazioneDiSpesaDTO.setIdDocIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
				logger.info("&&&&&&&&&&&&&&& pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue():"+pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue()+"\n\n\n\n\n\n");
				result.setEsito(Boolean.TRUE);
				result.setDichiarazioneDTO(dichiarazioneDiSpesaDTO);
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			if (e instanceof DichiarazioneDiSpesaException) {
				throw (DichiarazioneDiSpesaException) e;
			} else {
				RuntimeException re = new RuntimeException(
						"Attivit� non conclusa su flux e Documento non creato su servizio INDEX");
				throw re;
			}
		}  
	}
		
	private List<Long> extractId(DocumentoDiSpesaDTO[] documentiDiSpesa) {
		List<Long> listIdDoc = new ArrayList<Long>();
		if (isEmpty(documentiDiSpesa)) {
			return listIdDoc;
		}
		List<DocumentoDiSpesaDTO> listDocsValidi = new ArrayList<DocumentoDiSpesaDTO>();
		for (DocumentoDiSpesaDTO documentoDiSpesaDTO : documentiDiSpesa) {
			if (isEmpty(documentoDiSpesaDTO.getMotivazione())) {
				listDocsValidi.add(documentoDiSpesaDTO);
			}
		}
		listIdDoc = beanUtil.extractValues(listDocsValidi,
				"idDocumentoDiSpesa", Long.class);

		return listIdDoc;
	}

	private EsitoOperazioneInviaDichiarazione inviaDichiarazioneFinale(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, String codUtente,
			IstanzaAttivitaDTO istanzaAttivita,
			RelazioneTecnicaDTO relazioneTecnica)
			throws DichiarazioneDiSpesaException {

		try {
			long start = getTimerManager().start();

			EsitoOperazioneInviaDichiarazione result = new EsitoOperazioneInviaDichiarazione();

			EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa = verificaDichiarazioneDiSpesa(
					idUtente, identitaDigitale, dichiarazioneDiSpesaDTO);

			DocumentoDiSpesaDTO[] documentiDiSpesa = verificaDichiarazioneDiSpesa
					.getDocumentiDiSpesa();

			List<Long> idDocumenti = extractId(documentiDiSpesa);
			
			Long idDichiarazioneDiSpesa = pbandiDichiarazioneDiSpesaDAO.getSeqPbandiTDichSpesa().nextLongValue();
			
			/*
			 * 1) Salvo la dichiarazione di spesa
			 */

			PbandiTDomandaVO pbandiTDomandaVO = progettoManager
					.getDomandaByProgetto(dichiarazioneDiSpesaDTO
							.getIdProgetto());

			Date currentDate = new Date(System.currentTimeMillis());

			PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = new PbandiTDichiarazioneSpesaVO();

			dichiarazioneDiSpesaVO.setIdProgetto(new BigDecimal(
					dichiarazioneDiSpesaDTO.getIdProgetto()));

			dichiarazioneDiSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(
					idDichiarazioneDiSpesa));

			dichiarazioneDiSpesaVO.setDtDichiarazione(new java.sql.Date(
					currentDate.getTime()));

			dichiarazioneDiSpesaVO.setIdTipoDichiarazSpesa(new BigDecimal(
					getIdTipoDichiarazione(dichiarazioneDiSpesaDTO
							.getTipoDichiarazione())));
			dichiarazioneDiSpesaVO.setPeriodoAl(new java.sql.Date(
					dichiarazioneDiSpesaDTO.getDataFineRendicontazione()
							.getTime()));
			dichiarazioneDiSpesaVO.setPeriodoDal(pbandiTDomandaVO
					.getDtPresentazioneDomanda());
			dichiarazioneDiSpesaVO.setIdUtenteIns(new BigDecimal(idUtente));
			
			// Se almeno 1 dei doc di questa ds � cartaceo, TipoInvioDs vale "C" (null altrimenti).
			logger.info("TipoInvioDs prima di inserire = "+dichiarazioneDiSpesaDTO.getTipoInvioDs());
			dichiarazioneDiSpesaVO.setTipoInvioDs(dichiarazioneDiSpesaDTO.getTipoInvioDs());
			
			genericDAO.insert(dichiarazioneDiSpesaVO);
			
			
			/*
			 * 2) aggiorna stato documenti di spesa a dichiarato Il sistema
			 * dovr� aggiornare i seguenti oggetti: 1. DocumentoDiSpesaProgetto
			 * per tutti i documenti di spesa che sono stati �dichiarati" �
			 * StatoDocumentoDiSpesa uguale a DICHIARATO
			 */
			if (!isEmpty(idDocumenti)) {
				aggiornaStatoDocumentiDiSpesa(idUtente, identitaDigitale,
						dichiarazioneDiSpesaDTO, idDichiarazioneDiSpesa,
						idDocumenti);
			}
			
			
			
			/*
			 * 3) Ripartisco i pagamenti
			 * Se non ci sono documenti da validare restituisco il messaggio
			 */
			if (isEmpty(idDocumenti)) {
				idDocumenti = new ArrayList<Long>();
			} else {
				ripartisciPagamenti(idUtente, dichiarazioneDiSpesaDTO,
						idDocumenti,idDichiarazioneDiSpesa);
			}

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(
						ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

			

			/*
			 * VN: Cambio lo stato del conto economico master solo se il suo
			 * stato e' IPG
			 */
			ContoEconomicoDTO ce = contoEconomicoManager
					.findContoEconomicoMaster(NumberUtil
							.toBigDecimal(dichiarazioneDiSpesaDTO
									.getIdProgetto()));
			if (STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA
					.equalsIgnoreCase(ce.getDescBreveStatoContoEconom())) {
				contoEconomicoManager.aggiornaStatoContoEconomico(
						NumberUtil.toLong(ce.getIdContoEconomico()),
						STATO_CONTO_ECONOMICO_APPROVATO,
						NumberUtil.toBigDecimal(idUtente));
			}

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(
						ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);



			

			if (relazioneTecnica != null
					&& (!StringUtil.isEmpty(relazioneTecnica.getNomeFile()))) {
				allegaRelazioneTecnica(idUtente, dichiarazioneDiSpesaDTO,
						relazioneTecnica, idDichiarazioneDiSpesa, currentDate);
			}

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(
						ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);
 

			
			
			
			/*logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+dichiarazioneDiSpesaDTO.getIdProgetto());
			Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO.getIdProgetto());
			logger.warn("processo: "+processo);
			if(processo!=null){*/
				logger.warn("\n\n############################ NEOFLUX DICH_DI_SPESA (FINALE) ##############################\n");
				neofluxBusiness.endAttivita(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO.getIdProgetto(),Task.DICH_DI_SPESA);
				logger.warn("############################ NEOFLUX DICH_DI_SPESA (FINALE)  ##############################\n\n\n\n");
			/*}else{
				// +flux+ 
				logger.warn("\n\n############################ OLDFLUX ##############################\ncalling getProcessManager().inviaDichiarazioneDiSpesa (finale)idProgetto :"+dichiarazioneDiSpesaDTO.getIdProgetto());
				processManager.inviaDichiarazioneDiSpesa(
						dichiarazioneDiSpesaDTO.getIdProgetto(), idUtente,
						identitaDigitale);
				logger.warn("inviaDichiarazioneDiSpesa finale OK\n############################ OLDFLUX ##############################\n\n\n\n");
			}*/
			
			
			
			
			
			/*
			 * Completo il risultato
			 */
			result.setEsito(Boolean.TRUE);
			result.setDichiarazioneDTO(dichiarazioneDiSpesaDTO);

			boolean isBrCronoProgramma = regolaManager
					.isRegolaApplicabileForBandoLinea(
							dichiarazioneDiSpesaDTO.getIdBandoLinea(),
							RegoleConstants.BR34_CONTROLLO_ATTIVITA_CRONOPROGRAMMA_VALIDA);
			boolean isBrIndicatori = regolaManager
					.isRegolaApplicabileForBandoLinea(
							dichiarazioneDiSpesaDTO.getIdBandoLinea(),
							RegoleConstants.BR35_CONTROLLO_ATTIVITA_INDICATORI_VALIDA);

			List<String> messages = new ArrayList<String>();
			messages.add(MessaggiConstants.KEY_MSG_DICHIARAZIONE_CONCLUSA_CON_SUCCESSO);
			messages.add(MessaggiConstants.KEY_MSG_NECESSARIA_COMUNICAZIONE_FINE_LAVORI);

			if (isBrIndicatori)
				messages.add(MessaggiConstants.KEY_MSG_NECESSARI_INDICATORI_FINALI);
			if (isBrCronoProgramma)
				messages.add(MessaggiConstants.KEY_MSG_NECESSARIO_CRONOPROGRAMMA);

			result.setEsito(Boolean.TRUE);
			result.setDichiarazioneDTO(dichiarazioneDiSpesaDTO);
			result.setMessaggi(messages.toArray(new String[] {}));

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (e instanceof DichiarazioneDiSpesaException) {
				throw (DichiarazioneDiSpesaException) e;
			} else {
				RuntimeException re = new RuntimeException(
						"Attivit� non conclusa su flux ");
				throw re;
			}
		}  
	}

	private EsitoOperazioneInviaDichiarazione inviaDichiarazioneIntegrativa(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, String codUtente,
			IstanzaAttivitaDTO istanzaAttivita,
			RappresentanteLegaleDTO rappresentanteLegale,
			Long idDelegato,
			RelazioneTecnicaDTO relazioneTecnica)
			throws DichiarazioneDiSpesaException {
		
		Node nodoCreato = null;		

		try {
			long start = getTimerManager().start();
			EsitoOperazioneInviaDichiarazione result = new EsitoOperazioneInviaDichiarazione();

			EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa = verificaDichiarazioneDiSpesa(
					idUtente, identitaDigitale, dichiarazioneDiSpesaDTO);

			DocumentoDiSpesaDTO[] documentiDiSpesa = verificaDichiarazioneDiSpesa
					.getDocumentiDiSpesa();
			
			Long idDichiarazioneDiSpesa = pbandiDichiarazioneDiSpesaDAO
					.getSeqPbandiTDichSpesa().nextLongValue();
			
			/*
			 * 1) CREO LA DICHIARAZIONE DI SPESA
			 */

			// DATA DI SISTEMA PER LE VARIE INSERT e PER IL NOME FILE
			Date currentDate = new Date(System.currentTimeMillis());

			DichiarazioneDiSpesaVO dichiarazioneDiSpesaVO = new DichiarazioneDiSpesaVO();
			dichiarazioneDiSpesaVO.setCodiceProgetto(dichiarazioneDiSpesaDTO
					.getCodiceProgetto());
			dichiarazioneDiSpesaVO
					.setCodiceFiscaleBeneficiario(dichiarazioneDiSpesaDTO
							.getCodiceFiscaleBeneficiario());
			dichiarazioneDiSpesaVO
					.setDataFineRendicontazione(dichiarazioneDiSpesaDTO
							.getDataFineRendicontazione());
		 
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto(new BigDecimal(
					dichiarazioneDiSpesaDTO.getIdProgetto()));
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
			PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO();
			pbandiTDomandaVO.setIdDomanda(pbandiTProgettoVO.getIdDomanda());
			pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO);
			dichiarazioneDiSpesaVO
					.setDataInizioRendicontazione(pbandiTDomandaVO
							.getDtPresentazioneDomanda());

	 

			dichiarazioneDiSpesaVO.setIdDichiarazione(idDichiarazioneDiSpesa);
			dichiarazioneDiSpesaVO.setIdProgetto(dichiarazioneDiSpesaDTO
					.getIdProgetto());
			dichiarazioneDiSpesaVO.setIdSoggetto(dichiarazioneDiSpesaDTO
					.getIdSoggetto());
			dichiarazioneDiSpesaVO.setIdUtente(idUtente);
			dichiarazioneDiSpesaVO.setDataDichiarazioneDiSpesa(currentDate);
			dichiarazioneDiSpesaVO
					.setIdTipoDichiarazSpesa(getIdTipoDichiarazione(dichiarazioneDiSpesaDTO
							.getTipoDichiarazione()));
			
			// Se almeno 1 dei doc di questa ds � cartaceo, TipoInvioDs vale "C" (null altrimenti).
			dichiarazioneDiSpesaVO.setTipoInvioDs(dichiarazioneDiSpesaDTO.getTipoInvioDs());

			pbandiDichiarazioneDiSpesaDAO.inserisci(dichiarazioneDiSpesaVO);

	 
			List<Long> idDocumenti = extractId(documentiDiSpesa);
			
			/*
			 * 2) Aggiorna stato documenti di spesa a dichiarato.
			 */
			if (!isEmpty(idDocumenti)) {
				aggiornaStatoDocumentiDiSpesa(idUtente, identitaDigitale,
						dichiarazioneDiSpesaDTO, idDichiarazioneDiSpesa,
						idDocumenti);
			}
			
			/*
			 * Se non ci sono documenti da validare restituisco il messaggio
			 */
			/*
			 * 3) Ripartisco i pagamenti
			 */
			if (!isEmpty(idDocumenti)) {
				ripartisciPagamenti(idUtente, dichiarazioneDiSpesaDTO,
						idDocumenti, idDichiarazioneDiSpesa);
			}

			/*
			 * Recupero le note di credito associate ai documenti validi e li
			 * aggiungo a questi ultimi
			 */

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(
						ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);


			/*
			 * 4) CREO IL REPORT
			 */
			boolean isBozza = false;
			byte pdfBytes[] = creaReportDichiarazioneDiSpesaDinamico(idUtente,
					idDichiarazioneDiSpesa, idDocumenti,
					dichiarazioneDiSpesaDTO, rappresentanteLegale,   idDelegato,isBozza);

			if (pdfBytes == null)
				throw new DichiarazioneDiSpesaException(
						ErrorConstants.ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO);

			/*
			 * 5) CREO IL NOME DEL FILE DEL REPORT
			 */
			// uso la sequence+la data come identificatore univoco
			String nomeFile = creaNomefile(idDichiarazioneDiSpesa, currentDate);

			/*
			 * 6) CREO DOC SU INDEX
			 */
			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(
						ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

			nodoCreato = indexDAO.creaContentDichiarazioneDiSpesa(pdfBytes,
					nomeFile, dichiarazioneDiSpesaVO, idUtente);

			// DEMAT FASE II
			String shaHex = DigestUtils.shaHex(pdfBytes);
			// DEMAT FASE II
		 
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = salvaInfoNodoDichiarazioneSuDb(idUtente, nodoCreato,
					dichiarazioneDiSpesaVO, idDichiarazioneDiSpesa,rappresentanteLegale.getIdSoggetto(),idDelegato,
					currentDate, nomeFile,shaHex);

			if (relazioneTecnica != null
					&& (!StringUtil.isEmpty(relazioneTecnica.getNomeFile()))) {
				allegaRelazioneTecnica(idUtente, dichiarazioneDiSpesaDTO,
						relazioneTecnica, idDichiarazioneDiSpesa, currentDate);
			}
			

			boolean isBrDemat= regolaManager
					.isRegolaApplicabileForBandoLinea(
							dichiarazioneDiSpesaDTO.getIdBandoLinea(),
							RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
			if(isBrDemat)
				associateAllegati(idDichiarazioneDiSpesa,dichiarazioneDiSpesaVO.getIdProgetto());
			
			
			// INIZIO GESTIONE EVENTUALE PROGETTO CONTRIBUTO +GREEN.
			Long idProgettoContributo = dichiarazioneDiSpesaDTO.getIdProgettoContributoPiuGreen(); 
			logger.info("\n\ninviaDichiarazioneIntegrativaPiuGreen(): idProgettoContributo = "+idProgettoContributo+"\n\n");
			if (idProgettoContributo != null) {
				logger.info("\n\ninviaDichiarazioneIntegrativaPiuGreen(): INIZIO GESTIONE PROGETTO CONTRIBUTO PIU GREEN "+idProgettoContributo+"\n\n");
				
				/*EsitoOperazioneInviaDichiarazione esitoPiuGreen = inviaDichiarazioneIntegrativaPiuGreen(
						idUtente, identitaDigitale,
						dichiarazioneDiSpesaDTO, codUtente,
						istanzaAttivita,
						rappresentanteLegale,
						idDelegato,
						relazioneTecnica);*/
				
				// Recupero i dati del progetto contributo.
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): cerco il progetto contributo.");
				PbandiTProgettoVO progettoContributo = new PbandiTProgettoVO();
				progettoContributo.setIdProgetto(new BigDecimal(idProgettoContributo));
				progettoContributo = genericDAO.findSingleWhere(progettoContributo);
				
				Long idDichiarazioneDiSpesaContributo = pbandiDichiarazioneDiSpesaDAO.getSeqPbandiTDichSpesa().nextLongValue();
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): idDichiarazioneDiSpesaContributo = "+idDichiarazioneDiSpesaContributo);
				
				// 1) PiuGreen: Inserisco in PBANDI_T_DICHIARAZIONE_SPESA la DS del progetto contributo.
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): insert in PBANDI_T_DICHIARAZIONE_SPESA.");
				DichiarazioneDiSpesaVO dsContributo = new DichiarazioneDiSpesaVO();
				dsContributo.setIdDichiarazione(idDichiarazioneDiSpesaContributo);
				dsContributo.setIdProgetto(progettoContributo.getIdProgetto().longValue());
				dsContributo.setCodiceProgetto(progettoContributo.getCodiceProgetto());
				dsContributo.setCodiceFiscaleBeneficiario(dichiarazioneDiSpesaVO.getCodiceFiscaleBeneficiario());
				dsContributo.setDataFineRendicontazione(dichiarazioneDiSpesaVO.getDataFineRendicontazione());			 
				dsContributo.setDataInizioRendicontazione(dichiarazioneDiSpesaVO.getDataInizioRendicontazione());
				dsContributo.setIdSoggetto(dichiarazioneDiSpesaVO.getIdSoggetto());
				dsContributo.setIdUtente(dichiarazioneDiSpesaVO.getIdUtente());
				dsContributo.setDataDichiarazioneDiSpesa(dichiarazioneDiSpesaVO.getDataDichiarazioneDiSpesa());
				dsContributo.setIdTipoDichiarazSpesa(dichiarazioneDiSpesaVO.getIdTipoDichiarazSpesa());
				dsContributo.setTipoInvioDs(dichiarazioneDiSpesaVO.getTipoInvioDs());

				pbandiDichiarazioneDiSpesaDAO.inserisci(dsContributo);
				logger.info("inviaDichiarazioneIntegrativaPiuGreen(): inserito PBANDI_T_DICHIARAZIONE_SPESA con id = "+idDichiarazioneDiSpesaContributo);
				
				// Nel record della DS finanziamento in PbandiTDichiarazioneSpesa 
				// memorizzo l'id della DS contributo.
				PbandiTDichiarazioneSpesaVO dsFinanziamento = new PbandiTDichiarazioneSpesaVO();
				dsFinanziamento.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa));
				dsFinanziamento.setIdDichiarazioneSpesaColl(new BigDecimal(idDichiarazioneDiSpesaContributo));
				genericDAO.update(dsFinanziamento);
				
				// 2) PiuGreen: CREO IL REPORT
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): creazione pdf progetto contributo.");
				byte pdfBytesPiuGreen[] = creaReportDichiarazioneDiSpesaDinamicoPiuGreen(idUtente,
						idDichiarazioneDiSpesa, idDichiarazioneDiSpesaContributo, idDocumenti,
						dichiarazioneDiSpesaDTO, rappresentanteLegale, idDelegato, isBozza);

				if (pdfBytesPiuGreen == null)
					throw new DichiarazioneDiSpesaException("Errore nella creazione del pdf del progetto contributo.");

				// 3) PiuGreen: CREO IL NOME DEL FILE DEL REPORT
				String nomeFilePiuGreen = creaNomefile(idDichiarazioneDiSpesaContributo, currentDate);
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): nomeFilePiuGreen = "+nomeFilePiuGreen);
				
				// 4) PiuGreen: CREO DOC SU INDEX				
				if (!getTimerManager().checkTimeout(start))
					throw new DichiarazioneDiSpesaException(ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): creo il nodo su INDEX.");
				Node nodoCreatoPiuGreen = indexDAO.creaContentDichiarazioneDiSpesa(pdfBytesPiuGreen,
						nomeFilePiuGreen, dsContributo, idUtente);
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): nodoCreatoPiuGreen.getUid() = "+nodoCreatoPiuGreen.getUid());

				String shaHexPiuGreen = DigestUtils.shaHex(pdfBytesPiuGreen);
			 
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): insert su PbandiTDocumentoIndex");
				PbandiTDocumentoIndexVO pbandiTDocumentoIndexVOPiuGreen = salvaInfoNodoDichiarazioneSuDb(
						idUtente, nodoCreatoPiuGreen,
						dsContributo, idDichiarazioneDiSpesaContributo,
						rappresentanteLegale.getIdSoggetto(), idDelegato,
						currentDate, nomeFilePiuGreen, shaHexPiuGreen);
				logger.info("\ninviaDichiarazioneIntegrativaPiuGreen(): PbandiTDocumentoIndex: id = "+pbandiTDocumentoIndexVOPiuGreen.getIdDocumentoIndex());
				
				DichiarazioneDiSpesaDTO dsPiuGreen = new DichiarazioneDiSpesaDTO();
				dsPiuGreen.setIdDocIndex(pbandiTDocumentoIndexVOPiuGreen.getIdDocumentoIndex().longValue());
				dsPiuGreen.setNomeFile(nomeFilePiuGreen);
				dsPiuGreen.setUuidDocumento(nodoCreatoPiuGreen.getUid());
				result.setDichiarazionePiuGreenDTO(dsPiuGreen);
				
				logger.info("\n\ninviaDichiarazioneIntegrativaPiuGreen(): fine GESTIONE PROGETTO CONTRIBUTO PIU GREEN\n\n");
				
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//     TOGLIERE FINITI I TEST
//				if (1 < 2)
//					throw new DichiarazioneDiSpesaException("Errore nella creazione del pdf del progetto contributo.");
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!					
				
			}			
			// fine GESTIONE EVENTUALE PROGETTO CONTRIBUTO +GREEN.

			if (!getTimerManager().checkTimeout(start))
				throw new DichiarazioneDiSpesaException(
						ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA);

			/*
			logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+dichiarazioneDiSpesaDTO.getIdProgetto());
			Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO.getIdProgetto());
			logger.warn("processo: "+processo);
			if(processo!=null){*/
				logger.warn("\n\n############################ NEOFLUX UNLOCK DICH_SPESA_INTEGRATIVA ##############################\n");
				neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, dichiarazioneDiSpesaDTO.getIdProgetto(),Task.DICH_SPESA_INTEGRATIVA);
				logger.warn("############################ NEOFLUX UNLOCK DICH_SPESA_INTEGRATIVA ##############################\n\n\n\n");
			/*}else{
			//+flux+ 
				logger.warn("\n\n############################ OLDFLUX ##############################\ncalling getProcessManager().inviaDichiarazioneDiSpesa idProgetto :"+dichiarazioneDiSpesaDTO.getIdProgetto());
				processManager.inviaDichiarazioneDiSpesa(
						dichiarazioneDiSpesaDTO.getIdProgetto(), idUtente,
						identitaDigitale);
				logger.warn("inviaDichiarazioneDiSpesa OK\n############################ OLDFLUX ##############################\n\n\n\n");
			}*/
			

			/*
			 * Completo il risultato
			 */
			logger.info("&&&&&&&&&&&&&&& pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue():"+pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue()+"\n\n\n\n\n\n");
			dichiarazioneDiSpesaDTO.setIdDocIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
			dichiarazioneDiSpesaDTO.setNomeFile(nomeFile);
			dichiarazioneDiSpesaDTO.setUuidDocumento(nodoCreato.getUid());
			result.setEsito(Boolean.TRUE);
			result.setDichiarazioneDTO(dichiarazioneDiSpesaDTO);

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (e instanceof DichiarazioneDiSpesaException) {
				throw (DichiarazioneDiSpesaException) e;
			} else {
				RuntimeException re = new RuntimeException(
						"Attivit� non conclusa su flux e Documento non creato su servizio INDEX");
				throw re;
			}
		} 
	}
	
	// OTTOBRE 2015 DEMAT II
	private void associateAllegati(Long idDichiarazioneDiSpesa,Long idProgetto) {

		// where idPRogetto is null && id_entita=t_dicha and id_target= id_progetto
		logger.info("associating allegati to idDichiarazione "+idDichiarazioneDiSpesa+" ,idProgetto ");
		pbandiArchivioFileDAOImpl.associateAllegatiToDichiarazione(idDichiarazioneDiSpesa, idProgetto);
		
	}

	private Long getIdTipoDichiarazione(String descBreve) {

		return beanUtil.transform(decodificheManager.decodeDescBreve(
				PbandiDTipoDichiarazSpesaVO.class, descBreve), Long.class);

	}

	public InfoDichiarazioneSpesaDTO getInfoDichiarazioneDiSpesa(Long idUtente,
			String identitaDigitale, Long idDichiarazioneDiSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, DichiarazioneDiSpesaException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idDichiarazioneDiSpesa", "idProgetto" };

		InfoDichiarazioneSpesaDTO infoDichiarazioneSpesaDTO = new InfoDichiarazioneSpesaDTO();
		try {

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idDichiarazioneDiSpesa, idProgetto);

			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(
					idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO
					.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			String descBreveTipoDich = getDecodificheManager()
					.findDescBreveStorico(
							PbandiDTipoDichiarazSpesaVO.class,
							pbandiTDichiarazioneSpesaVO
									.getIdTipoDichiarazSpesa());

			if (descBreveTipoDich
					.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA)
					|| descBreveTipoDich
							.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE)
					|| descBreveTipoDich
							.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE)) {
				infoDichiarazioneSpesaDTO.setIsFinaleOIntegrativa(true);
				// aggiungo le info sulle altre integrative non ancora validate
				infoDichiarazioneSpesaDTO
						.setAltreDichiarazioniDiSpesaIntermedieNonValidate(dichiarazioneDiSpesaManager
								.isDichiarazioniDaValidareById(idProgetto));
			} else if (descBreveTipoDich
					.equalsIgnoreCase(Constants.DESC_BREVE_TIPO_DICHIARAZIONE_INTERMEDIA)) {
				infoDichiarazioneSpesaDTO.setIsIntermedia(true);
				infoDichiarazioneSpesaDTO
						.setAltreDichiarazioniDiSpesaIntermedieNonValidate(false);
			}
			infoDichiarazioneSpesaDTO
					.setDichiarazioneFinalePresente(dichiarazioneDiSpesaManager
							.hasDichiarazioneFinaleOFinaleComunicazione(
									idUtente, identitaDigitale, idProgetto));

	 
			infoDichiarazioneSpesaDTO.setPagamentiDaRespingere(false);
			infoDichiarazioneSpesaDTO.setPagamentiRespinti(false);
			infoDichiarazioneSpesaDTO.setIsProgettoChiuso(false);

			

			List<String> messaggi = new ArrayList<String>();
			infoDichiarazioneSpesaDTO.setMessaggi(messaggi
					.toArray(new String[] {}));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return infoDichiarazioneSpesaDTO;
	}
	
	private byte[] creaReportDichiarazioneDiSpesaDinamico(Long idUtente,
			Long idDichiarazione, List<Long> idDocumentiValidi,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,
			RappresentanteLegaleDTO rappresentanteLegale,Long idDelegato,boolean isBozza)
			throws DichiarazioneDiSpesaException {

		logger.info("creaReportDichiarazioneDiSpesaDinamico...");
		boolean isBr02 = false;
		try {
			isBr02 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);
		} catch (FormalParameterException e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la verifica della regola "
							+ RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA
							+ " per il bandolinea");
			throw dse;
		}

		boolean isBr05 = false;
		try {
			isBr05 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA);
		} catch (FormalParameterException e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la verifica della regola "
							+ RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA
							+ " per il bandolinea");
			throw dse;
		}

		boolean isBR16 = false;
		try {
			isBR16 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR16_GESTIONE_CAMPO_TASK);
		} catch (FormalParameterException e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la verifica della regola "
							+ RegoleConstants.BR16_GESTIONE_CAMPO_TASK
							+ " per il bandolinea");
			throw dse;
		}

		
		Date al = dichiarazioneDiSpesaDTO.getDataFineRendicontazione();
		Long idProgetto = dichiarazioneDiSpesaDTO.getIdProgetto();
		Long idSoggettoBeneficiario = dichiarazioneDiSpesaDTO.getIdSoggetto();

				
		

		/**
		 * Carico i dati del progetto
		 */

		ProgettoVO progettoVO = dichiarazioneDiSpesaManager.findDatiProgetto(
				idProgetto, al, idDocumentiValidi, 
				idDichiarazione);

		/*
		 * Progetto
		 */
		
		Map<String, String> props = new HashMap<String, String>();
		props.put("idProgetto", "idProgetto");
		props.put("titoloProgetto", "titoloProgetto");
		props.put("codiceProgetto", "codiceVisualizzato");//jira 2495
		props.put("dtConcessione", "dataConcessione");
		props.put("cup", "cup");
		props.put("sogliaSpesaCalcErogazioni", "sogliaSpesaCalcErogazioni");
		
	
		
		if (progettoVO != null) {
			
			popolaTemplateManager
					.addChiave(
							PopolaTemplateManager.CHIAVE_PROGETTO,
							beanUtil.transform(
									progettoVO,
									it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class));
	
			props.put("totaleQuietanzato", "totaleQuietanzato");
			popolaTemplateManager
					.addChiave(
							PopolaTemplateManager.CHIAVE_PROGETTO,
							beanUtil.transform(
									progettoVO,
									it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class,
									props));

		} else {
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);

			popolaTemplateManager
					.addChiave(
							PopolaTemplateManager.CHIAVE_PROGETTO,
							beanUtil.transform(
									pbandiTProgettoVO,
									it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class,
									props));
		}
		

		/**
		 * Carico i dati del beneficiario
		 */
		BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
		filtroBeneficiario.setIdProgetto(BigDecimal.valueOf(idProgetto));
		filtroBeneficiario.setIdSoggetto(BigDecimal.valueOf(idSoggettoBeneficiario));
		
		BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);

	
		/*
		 * 1) Imposto il modello
		 */
		popolaTemplateManager
				.setTipoModello(PopolaTemplateManager.MODELLO_DICHIARAZIONE_DI_SPESA);

		/*
		 * 2) Imposto i parametri
		 */
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO, idProgetto);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO_PIU_GREEN, null);	// +Green

		/*
		 * Dichiarazione di spesa
		 */
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiSpesaDTO dichReport = new it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiSpesaDTO();
		dichReport.setNumero(beanUtil.transform(idDichiarazione, String.class));
		dichReport.setBeneficiario(beanUtil.transform(beneficiarioVO,
				it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));
		
		DecodificaDTO tipoDichiarazione = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DICHIARAZ_SPESA,
				dichiarazioneDiSpesaDTO.getTipoDichiarazione());
		
		dichReport.setDescTipoDichiarazione(tipoDichiarazione.getDescrizione());
		Date currentDate = new Date(System.currentTimeMillis());
		dichReport.setDataAl(currentDate);
		dichReport.setDataDal(currentDate);
		dichReport.setDataFineRendicontazione(dichiarazioneDiSpesaDTO.getDataFineRendicontazione());

		Map<String, String> mapVociCosto = new HashMap<String, String>();
		mapVociCosto.put("descVoceDiSpesa", "descVoceDiSpesa");
		mapVociCosto.put("idVoceSpesa", "idVoceSpesa");
		mapVociCosto.put("idVoceDiSpesaPadre", "idVoceDiSpesaPadre");
		mapVociCosto.put("descVoceDiSpesaPadre", "descVoceDiSpesaPadre");
		mapVociCosto.put("importoAmmessoAFinanziamento","importoAmmessoFinanziamento");
		mapVociCosto.put("importoQuietanzato", "importoQuietanzato");
		mapVociCosto.put("importoValidato", "importoValidato");
		mapVociCosto.put("importoRendicontato", "importoRendicontato");

		/**
		 * Carico le voci di costo
		 */
		VoceDiCostoDTO[] vociDTO = dichiarazioneDiSpesaManager.findVociDiCosto(
				idDichiarazione, idProgetto);
		
		dichReport
				.setVociDiCosto(beanUtil
						.transformToArrayList(
								vociDTO,
								it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.VoceDiCostoDTO.class,
								mapVociCosto));
		
		/**
		 * Carico i documenti contabili
		 */

		List<DocumentoContabileVO> documentiContabiliVO = dichiarazioneDiSpesaManager
				.findDocumentiContabili(idProgetto, al, idDocumentiValidi, idDichiarazione);
		
		for (DocumentoContabileVO vo :documentiContabiliVO) {
			if(vo.getFlagElettronico()!=null && vo.getFlagElettronico().equalsIgnoreCase("S")){
				
				
			    PbandiDLineaDiInterventoVO lineaDiInterventoNormativa = progettoManager.getLineaDiInterventoNormativa(idProgetto);
				
			    logger.info("trovato doc con flag elettronico a S, cerco contenuto dinamico per varibile 'Programma' per id linea normativa: "+lineaDiInterventoNormativa.getIdLineaDiIntervento());
			    PbandiDMicroSezioneDinVO microSez= new PbandiDMicroSezioneDinVO();
			    microSez.setIdLineaNormativa(lineaDiInterventoNormativa.getIdLineaDiIntervento());
				
				microSez = genericDAO.findSingleOrNoneWhere(microSez);
				if(microSez!=null){
					logger.info("PbandiDMicroSezioneDinVO per lineaDiInterventoNormativa: "+lineaDiInterventoNormativa.getIdLineaDiIntervento()+" valore:  "+microSez.getValore());
					popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGRAMMA, microSez.getValore());
				}else{
					logger.warn(" per lineaDiInterventoNormativa: "+lineaDiInterventoNormativa.getIdLineaDiIntervento()+"  non sono configurati records su PbandiDMicroSezioneDi,prendo il defaultn\n\n\n");
					microSez= new PbandiDMicroSezioneDinVO();
					microSez.setNome("Programma");
					NullCondition<PbandiDMicroSezioneDinVO> nullIdLineaCondition = new NullCondition<PbandiDMicroSezioneDinVO>(
							PbandiDMicroSezioneDinVO.class,
							"idLineaNormativa");
					AndCondition<PbandiDMicroSezioneDinVO> filterMicroSezDefault = new AndCondition<PbandiDMicroSezioneDinVO>
					(new FilterCondition<PbandiDMicroSezioneDinVO>(microSez),nullIdLineaCondition);
					
					microSez = genericDAO.findSingleOrNoneWhere(filterMicroSezDefault );
					if(microSez!=null){
						logger.info(" trovato default: setto Programma =  "+ microSez.getValore());
						popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGRAMMA, microSez.getValore());
					}else{
						logger.warn("non trovata microSezDin default !!!");
					}
					
				}
				break;
			}
		}
		
		
		dichReport
				.setDocumentiContabili(beanUtil
						.transformList(
								documentiContabiliVO,
								it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO.class));
		
	 
		
		dichReport.setIsBr02(isBr02);
		dichReport.setIsBr05(isBr05);
		dichReport.setIsBR16(isBR16);

		popolaTemplateManager.addChiave(
				PopolaTemplateManager.CHIAVE_DS_DICHIARAZIONE_DI_SPESA,
				dichReport);

		/*
		 * Rappresentante legale / delegato
		 */
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO = beanUtil.transform(
				rappresentanteLegale,
				it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
		if(idDelegato!=null){
			logger.info("il delegato non � NULL "+idDelegato+", lo metto al posto del rapp legale");
			 DelegatoVO delegatoVO = new DelegatoVO();
			 delegatoVO.setIdSoggetto(idDelegato);
			 delegatoVO.setIdProgetto(idProgetto);
		     List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
		     if(delegati!=null && !delegati.isEmpty()){
		    	delegatoVO=delegati.get(0);
		     }
			 logger.shallowDump(delegatoVO,"info");
			 rappresentanteLegaleDTO = beanUtil.transform(
						delegatoVO,
						it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
			 logger.shallowDump(rappresentanteLegaleDTO,"info");
		}
		popolaTemplateManager
				.addChiave(
						PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE,rappresentanteLegaleDTO
						);

		/*
		 * Beneficiario
		 */
		popolaTemplateManager
				.addChiave(
						PopolaTemplateManager.CHIAVE_BENEFICIARIO,
						beanUtil.transform(
								beneficiarioVO,
								it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));
		popolaTemplateManager.addChiave(
				PopolaTemplateManager.CHIAVE_ID_BENEFICIARIO, beneficiarioVO
						.getIdSoggetto().longValue());

		/*
		 * Sede intervento
		 */
		/**
		 * Carico i dati della sede di intervento e sede amministrativa
		 */
		SedeVO sedeIntervento = null;
		SedeVO sedeAmministrativa = null;
		try {
			sedeIntervento= sedeManager.findSedeIntervento(idProgetto,
					idSoggettoBeneficiario);
			
			sedeAmministrativa = sedeManager.findSedeAmministrativa(idProgetto, idSoggettoBeneficiario);

			/*JIRA PBANDI-2764 - FASSIL*/
			if(sedeAmministrativa != null) {
				sedeIntervento = sedeAmministrativa;
			}
			logger.info("[FineProgettoBusinessImpl :: creaReportDichiarazioneDiSpesaDinamico] - sede intervento: ");
			logger.shallowDump(sedeIntervento, "info");
			logger.info("[FineProgettoBusinessImpl :: creaReportDichiarazioneDiSpesaDinamico] - sede amministrativa: ");
			logger.shallowDump(sedeAmministrativa, "info");
			
			
		} catch (Exception e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la ricerca della sede di intervento");
			throw dse;
		}
		
		popolaTemplateManager.addChiave(
				PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,
				beanUtil.transform(sedeIntervento, SedeDTO.class));
		
		
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_AMMINISTRATIVA, 
				beanUtil.transform(sedeAmministrativa, SedeDTO.class));
		/*
		 * Bozza
		 */
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IS_BOZZA,
				isBozza);

		
		
		// 11/12/15 added footer 
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,""+idDichiarazione);
	
		
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI, tipoAllegatiManager.findTipoAllegati(idDichiarazione, idProgetto, dichiarazioneDiSpesaDTO.getIdBandoLinea(), "DS"));
		
		Modello modello = null;
		try {
			modello = popolaTemplateManager.popolaModello(idProgetto);
			logger.shallowDump(modello, "info");
		} catch (Exception e) {
			logger.error("Errore in fase di creazione del modello.", e);
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					"Errore in fase di creazione del modello.", e);
			throw ex;
		}

		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(
					modello.getMasterReport(), modello.getMasterParameters(),
					new JREmptyDataSource());
		} catch (JRException e) {
			logger.error("Errore in fase di disegno del modello.", e);
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					"Errore in fase di disegno del modello.", e);
			throw ex;
		}

		byte[] bytes;
		try {
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			logger.error("Errore in fase di export del pdf del modello.", e);
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					"Errore in fase di export del pdf del modello.", e);
			throw ex;
		}
	
		
		return bytes;
	}


	// +Green: inizio
	// Variante del metodo creaAnteprimaDichiarazioneDiSpesa() per gestire 
	// la stampa della DS per il progetto a contributo +green.
	// Dati del report:
	//	- beneficiario : idProgettoFinanziamento (� lo stesso per finanaziamento e contributo)
	//	- rappresentante legale \ delegato : idProgettoFinanziamento (� lo stesso per finanaziamento e contributo)
	//	- progetto : idProgettoContributo
	//	- sede di intervento : idProgettoFinanziamento
	//  - totale quietanziato : idProgettoFinanziamento
	private byte[] creaReportDichiarazioneDiSpesaDinamicoPiuGreen(Long idUtente,
			Long idDichiarazione, Long idDichiarazioneDiSpesaContributo, 
			List<Long> idDocumentiValidi,
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO,			
			RappresentanteLegaleDTO rappresentanteLegale,Long idDelegato,boolean isBozza)
			throws DichiarazioneDiSpesaException {

		logger.info("creaReportDichiarazioneDiSpesaDinamicoPiuGreen...");
		boolean isBr02 = false;
		try {
			isBr02 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA);
		} catch (FormalParameterException e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la verifica della regola "
							+ RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA
							+ " per il bandolinea");
			throw dse;
		}

		boolean isBr05 = false;
		try {
			isBr05 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA);
		} catch (FormalParameterException e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la verifica della regola "
							+ RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA
							+ " per il bandolinea");
			throw dse;
		}

		boolean isBR16 = false;
		try {
			isBR16 = regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazioneDiSpesaDTO.getIdBandoLinea(),
					RegoleConstants.BR16_GESTIONE_CAMPO_TASK);
		} catch (FormalParameterException e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la verifica della regola "
							+ RegoleConstants.BR16_GESTIONE_CAMPO_TASK
							+ " per il bandolinea");
			throw dse;
		}

		Long idProgettoFinanziamento = dichiarazioneDiSpesaDTO.getIdProgetto();
		Long idProgettoContributo = dichiarazioneDiSpesaDTO.getIdProgettoContributoPiuGreen();
		
		Date al = dichiarazioneDiSpesaDTO.getDataFineRendicontazione();
		//Long idProgetto = dichiarazioneDiSpesaDTO.getIdProgetto();
		Long idSoggettoBeneficiario = dichiarazioneDiSpesaDTO.getIdSoggetto();

		/**
		 * Carico i dati del progetto
		 * findDatiProgetto() restiuisce dati che vanno presi sia dal progetto a finanziamento sia da quello a contributo:
		 * PBANDI_T_PROGETTO.id_progetto						progetto contributo
		 * PBANDI_T_PROGETTO.titolo_progetto					progetto contributo
		 * PBANDI_T_PROGETTO.cup								progetto contributo
		 * PBANDI_T_PROGETTO.DT_CONCESSIONE						progetto contributo
		 * PBANDI_T_PROGETTO.CODICE_VISUALIZZATO				progetto contributo
		 * PBANDI_T_PROGETTO.SOGLIA_SPESA_CALC_EROGAZIONI		progetto finanziamento
		 * SUM(QUIETANZATO_PROGETTO.IMPORTO_QUIETANZATO)		progetto finanziamento
		 */

		// Recupero l'importo quietanzato del progetto finanziamento.
		ProgettoVO progettoVOfinanziamento = dichiarazioneDiSpesaManager.findDatiProgetto(
				idProgettoFinanziamento, al, idDocumentiValidi, 
				idDichiarazione);
		
		// Recupero i dati del progetto contributo.
		PbandiTProgettoVO pbandiTProgettoVOcontributo = new PbandiTProgettoVO();
		pbandiTProgettoVOcontributo.setIdProgetto(new BigDecimal(idProgettoContributo));
		pbandiTProgettoVOcontributo = genericDAO.findSingleWhere(pbandiTProgettoVOcontributo);

		/*
		 * Progetto
		 */
		
		Map<String, String> props = new HashMap<String, String>();
		props.put("idProgetto", "idProgetto");
		props.put("titoloProgetto", "titoloProgetto");
		props.put("codiceProgetto", "codiceVisualizzato");//jira 2495
		props.put("dtConcessione", "dataConcessione");
		props.put("cup", "cup");
		props.put("sogliaSpesaCalcErogazioni", "sogliaSpesaCalcErogazioni");
				
		if (progettoVOfinanziamento != null) {
			
			// Sostituisco i dati del progetto finanziamento con quelli del progetto contributo.
			// Il totale quietanzato resta quello del progetto finanziamento.
			progettoVOfinanziamento.setIdProgetto(pbandiTProgettoVOcontributo.getIdProgetto().longValue());
			progettoVOfinanziamento.setTitoloProgetto(pbandiTProgettoVOcontributo.getTitoloProgetto());
			progettoVOfinanziamento.setCodiceProgetto(pbandiTProgettoVOcontributo.getCodiceVisualizzato());
			progettoVOfinanziamento.setDtConcessione(pbandiTProgettoVOcontributo.getDtConcessione());
			progettoVOfinanziamento.setCup(pbandiTProgettoVOcontributo.getCup());
			progettoVOfinanziamento.setSogliaSpesaCalcErogazioni(pbandiTProgettoVOcontributo.getSogliaSpesaCalcErogazioni());
			
			popolaTemplateManager
					.addChiave(
							PopolaTemplateManager.CHIAVE_PROGETTO,
							beanUtil.transform(
									progettoVOfinanziamento,
									it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class));
	
			props.put("totaleQuietanzato", "totaleQuietanzato");
			popolaTemplateManager
					.addChiave(
							PopolaTemplateManager.CHIAVE_PROGETTO,
							beanUtil.transform(
									progettoVOfinanziamento,
									it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class,
									props));

		} else {
			

			popolaTemplateManager
					.addChiave(
							PopolaTemplateManager.CHIAVE_PROGETTO,
							beanUtil.transform(
									pbandiTProgettoVOcontributo,
									it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class,
									props));
		}
		

		/**
		 * Carico i dati del beneficiario
		 */
		BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
		filtroBeneficiario.setIdProgetto(BigDecimal.valueOf(idProgettoFinanziamento));
		filtroBeneficiario.setIdSoggetto(BigDecimal.valueOf(idSoggettoBeneficiario));
		
		BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);

	
		/*
		 * 1) Imposto il modello
		 */
		popolaTemplateManager
				.setTipoModello(PopolaTemplateManager.MODELLO_DICHIARAZIONE_DI_SPESA);

		/*
		 * 2) Imposto i parametri
		 */
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO, idProgettoContributo);
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO_PIU_GREEN, idProgettoFinanziamento);

		/*
		 * Dichiarazione di spesa
		 */
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiSpesaDTO dichReport = new it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiSpesaDTO();
		dichReport.setNumero(beanUtil.transform(idDichiarazioneDiSpesaContributo, String.class));
		dichReport.setBeneficiario(beanUtil.transform(beneficiarioVO,
				it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));
		
		DecodificaDTO tipoDichiarazione = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.TIPO_DICHIARAZ_SPESA,
				dichiarazioneDiSpesaDTO.getTipoDichiarazione());
		
		dichReport.setDescTipoDichiarazione(tipoDichiarazione.getDescrizione());
		Date currentDate = new Date(System.currentTimeMillis());
		dichReport.setDataAl(currentDate);
		dichReport.setDataDal(currentDate);
		dichReport.setDataFineRendicontazione(dichiarazioneDiSpesaDTO.getDataFineRendicontazione());

		Map<String, String> mapVociCosto = new HashMap<String, String>();
		mapVociCosto.put("descVoceDiSpesa", "descVoceDiSpesa");
		mapVociCosto.put("idVoceSpesa", "idVoceSpesa");
		mapVociCosto.put("idVoceDiSpesaPadre", "idVoceDiSpesaPadre");
		mapVociCosto.put("descVoceDiSpesaPadre", "descVoceDiSpesaPadre");
		mapVociCosto.put("importoAmmessoAFinanziamento","importoAmmessoFinanziamento");
		mapVociCosto.put("importoQuietanzato", "importoQuietanzato");
		mapVociCosto.put("importoValidato", "importoValidato");
		mapVociCosto.put("importoRendicontato", "importoRendicontato");

		/**
		 * Carico le voci di costo
		 */
		VoceDiCostoDTO[] vociDTO = dichiarazioneDiSpesaManager.findVociDiCosto(
				idDichiarazione, idProgettoFinanziamento);
		
		dichReport
				.setVociDiCosto(beanUtil
						.transformToArrayList(
								vociDTO,
								it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.VoceDiCostoDTO.class,
								mapVociCosto));
		
		/**
		 * Carico i documenti contabili
		 */

		List<DocumentoContabileVO> documentiContabiliVO = dichiarazioneDiSpesaManager
				.findDocumentiContabili(idProgettoFinanziamento, al, idDocumentiValidi, idDichiarazione);
		
		for (DocumentoContabileVO vo :documentiContabiliVO) {
			if(vo.getFlagElettronico()!=null && vo.getFlagElettronico().equalsIgnoreCase("S")){
				
				
			    PbandiDLineaDiInterventoVO lineaDiInterventoNormativa = progettoManager.getLineaDiInterventoNormativa(idProgettoFinanziamento);
				
			    logger.info("trovato doc con flag elettronico a S, cerco contenuto dinamico per varibile 'Programma' per id linea normativa: "+lineaDiInterventoNormativa.getIdLineaDiIntervento());
			    PbandiDMicroSezioneDinVO microSez= new PbandiDMicroSezioneDinVO();
			    microSez.setIdLineaNormativa(lineaDiInterventoNormativa.getIdLineaDiIntervento());
				
				microSez = genericDAO.findSingleOrNoneWhere(microSez);
				if(microSez!=null){
					logger.info("PbandiDMicroSezioneDinVO per lineaDiInterventoNormativa: "+lineaDiInterventoNormativa.getIdLineaDiIntervento()+" valore:  "+microSez.getValore());
					popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGRAMMA, microSez.getValore());
				}else{
					logger.warn(" per lineaDiInterventoNormativa: "+lineaDiInterventoNormativa.getIdLineaDiIntervento()+"  non sono configurati records su PbandiDMicroSezioneDi,prendo il defaultn\n\n\n");
					microSez= new PbandiDMicroSezioneDinVO();
					microSez.setNome("Programma");
					NullCondition<PbandiDMicroSezioneDinVO> nullIdLineaCondition = new NullCondition<PbandiDMicroSezioneDinVO>(
							PbandiDMicroSezioneDinVO.class,
							"idLineaNormativa");
					AndCondition<PbandiDMicroSezioneDinVO> filterMicroSezDefault = new AndCondition<PbandiDMicroSezioneDinVO>
					(new FilterCondition<PbandiDMicroSezioneDinVO>(microSez),nullIdLineaCondition);
					
					microSez = genericDAO.findSingleOrNoneWhere(filterMicroSezDefault );
					if(microSez!=null){
						logger.info(" trovato default: setto Programma =  "+ microSez.getValore());
						popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGRAMMA, microSez.getValore());
					}else{
						logger.warn("non trovata microSezDin default !!!");
					}
					
				}
				break;
			}
		}
		
		
		dichReport
				.setDocumentiContabili(beanUtil
						.transformList(
								documentiContabiliVO,
								it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO.class));
		
	 
		
		dichReport.setIsBr02(isBr02);
		dichReport.setIsBr05(isBr05);
		dichReport.setIsBR16(isBR16);
		
		popolaTemplateManager.addChiave(
				PopolaTemplateManager.CHIAVE_DS_DICHIARAZIONE_DI_SPESA,
				dichReport);

		/*
		 * Rappresentante legale / delegato
		 */
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO = beanUtil.transform(
				rappresentanteLegale,
				it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
		if(idDelegato!=null){
			logger.info("il delegato non � NULL "+idDelegato+", lo metto al posto del rapp legale");
			 DelegatoVO delegatoVO = new DelegatoVO();
			 delegatoVO.setIdSoggetto(idDelegato);
			 delegatoVO.setIdProgetto(idProgettoFinanziamento);
		     List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
		     if(delegati!=null && !delegati.isEmpty()){
		    	delegatoVO=delegati.get(0);
		     }
			 logger.shallowDump(delegatoVO,"info");
			 rappresentanteLegaleDTO = beanUtil.transform(
						delegatoVO,
						it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
			 logger.shallowDump(rappresentanteLegaleDTO,"info");
		}
		popolaTemplateManager
				.addChiave(
						PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE,rappresentanteLegaleDTO
						);

		/*
		 * Beneficiario
		 */
		popolaTemplateManager
				.addChiave(
						PopolaTemplateManager.CHIAVE_BENEFICIARIO,
						beanUtil.transform(
								beneficiarioVO,
								it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));
		popolaTemplateManager.addChiave(
				PopolaTemplateManager.CHIAVE_ID_BENEFICIARIO, beneficiarioVO
						.getIdSoggetto().longValue());

		/*
		 * Sede intervento
		 */
		/**
		 * Carico i dati della sede di intervento
		 */
		SedeVO sedeIntervento = null;
		SedeVO sedeAmministrativa = null;
		try {
			sedeIntervento= sedeManager.findSedeIntervento(idProgettoFinanziamento,
					idSoggettoBeneficiario);
			sedeAmministrativa = sedeManager.findSedeAmministrativa(idProgettoFinanziamento, idSoggettoBeneficiario);
		} catch (Exception e) {
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
					"Errore durante la ricerca della sede di intervento");
			throw dse;
		}
		popolaTemplateManager.addChiave(
				PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,
				beanUtil.transform(sedeIntervento, SedeDTO.class));
		
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_AMMINISTRATIVA, beanUtil.transform(sedeAmministrativa, SedeDTO.class));

		/*
		 * Bozza
		 */
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IS_BOZZA,
				isBozza);

		
		
		// 11/12/15 added footer 
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,""+idDichiarazione);
	
		
		popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI, tipoAllegatiManager.findTipoAllegati(idDichiarazione, idProgettoFinanziamento, dichiarazioneDiSpesaDTO.getIdBandoLinea(), "DS"));
		
		Modello modello = null;
		try {
			modello = popolaTemplateManager.popolaModelloPiuGreen(idProgettoFinanziamento, idProgettoContributo);
			logger.shallowDump(modello, "info");
		} catch (Exception e) {
			logger.error("Errore in fase di creazione del modello.", e);
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					"Errore in fase di creazione del modello.", e);
			throw ex;
		}

		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(
					modello.getMasterReport(), modello.getMasterParameters(),
					new JREmptyDataSource());
		} catch (JRException e) {
			logger.error("Errore in fase di disegno del modello.", e);
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					"Errore in fase di disegno del modello.", e);
			throw ex;
		}

		byte[] bytes;
		try {
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			logger.error("Errore in fase di export del pdf del modello.", e);
			DichiarazioneDiSpesaException ex = new DichiarazioneDiSpesaException(
					"Errore in fase di export del pdf del modello.", e);
			throw ex;
		}

		return bytes;
	}	
	// +Green: fine

	
	
	private boolean isTipoInvioDigitale (String tipoInvio) {
		if (tipoInvio != null && tipoInvio.equalsIgnoreCase(GestioneDocumentiDiSpesaSrv.TIPO_INVIO_DOCUMENTO_DIGITALE))
			return true;
		else 
			return false;
	}

	
	public java.lang.Long findIdDocIndexReport(
								java.lang.Long idUtente,
								java.lang.String identitaDigitale,
								java.lang.Long idDichiarazioneDiSpesa)throws CSIException,SystemException, UnrecoverableException,
																	DichiarazioneDiSpesaException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDichiarazioneDiSpesa" };
	
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idDichiarazioneDiSpesa);
	
		PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();
		vo.setIdTarget(new BigDecimal(idDichiarazioneDiSpesa));
		BigDecimal idTipoDocumentoIndex = decodificheManager
				.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
						GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RDDS);
		vo.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		logger.warn("\n\n\n\nfinding idDocIndex for report dettaglio doc spesa validazione");
		vo = genericDAO.findSingleOrNoneWhere(vo);
		if(vo!=null){
			logger.warn("\nidDocIndex found !!! : "+vo.getIdDocumentoIndex());
			return vo.getIdDocumentoIndex().longValue();
		}else{
			logger.warn("\nidDocIndex NOT found !!!   " );
			return null;
		}
	}

	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}

	public void setRappresentanteLegaleManager(
			RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}

	public DichiarazioneDiSpesaDTO findDichiarazioneFinale(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };
		
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);
	
		return beanUtil.transform(dichiarazioneDiSpesaManager.getDichiarazioneFinale(idProgetto), DichiarazioneDiSpesaDTO.class);
	}
}
