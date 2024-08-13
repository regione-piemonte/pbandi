/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.rettifica;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.CertificazioneManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.CheckListManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.VoceDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DichiarazioneDiSpesaRettificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoReportDettaglioDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaContoEconomicoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbweb.pbandisrv.exception.recupero.RecuperoException;
import it.csi.pbandi.pbweb.pbandisrv.exception.rettifica.RettificaException;
import it.csi.pbandi.pbweb.pbandisrv.exception.validazionerendicontazione.ValidazioneRendicontazioneException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ContoEconomicoConRighiEVociVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PagamentoDocumentoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ReportDocumentiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaAssociataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica.DichiarazioneSpesaRettificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica.DocumentoDiSpesaRettificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica.DocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica.PagamentoRettificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica.RettificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica.RettificaVoceDiSpesaPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica.VoceDiSpesaRettificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.LikeContainsCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.OrCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoPropostaCertifVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagQuotParteDocSpVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDettPropostaCertifVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRettificaDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.rettifica.RettificaSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.Constants;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class RettificaBusinessImpl extends BusinessImpl implements RettificaSrv {

	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private CertificazioneManager certificazioneManager;
	
	@Autowired
	private CheckListManager checkListManager;
	
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	
	@Autowired
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	
	private NeofluxSrv neofluxBusiness;
	
	@Autowired
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	
	private VoceDiSpesaManager voceDiSpesaManager;

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public NeofluxSrv getNeofluxBusiness() {
		return neofluxBusiness;
	}

	public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
		this.neofluxBusiness = neofluxBusiness;
	}

	public VoceDiSpesaManager getVoceDiSpesaManager() {
		return voceDiSpesaManager;
	}

	public void setVoceDiSpesaManager(VoceDiSpesaManager voceDiSpesaManager) {
		this.voceDiSpesaManager = voceDiSpesaManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public void setCheckListManager(CheckListManager checkListManager) {
		this.checkListManager = checkListManager;
	}

	public CheckListManager getCheckListManager() {
		return checkListManager;
	}


	public CertificazioneManager getCertificazioneManager() {
		return certificazioneManager;
	}

	public void setCertificazioneManager(CertificazioneManager certificazioneManager) {
		this.certificazioneManager = certificazioneManager;
	}
	
	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}

	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}

	public void setDocumentoDiSpesaManager(DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}

	public DocumentoDiSpesaDTO[] findDocumentiDiSpesaPerRettifica(
			Long idUtente, String identitaDigitale, DocumentoDiSpesaDTO filtro)
			throws CSIException, SystemException, UnrecoverableException,
			RettificaException {
	 
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			BigDecimal idContoEcomonico = null;
			if (filtro.getIdProgetto() != null) {
				/*
				 * Ricerco l' identificativo del conto economico master
				 */
				try {
					idContoEcomonico = contoEconomicoManager
							.getIdContoMaster(BigDecimal.valueOf(filtro.getIdProgetto()));
				} catch (ContoEconomicoNonTrovatoException e) {
					logger.error("conto economico master not found for tettifiche with idProgetto "+filtro.getIdProgetto(), e);
					RettificaException re = new RettificaException(ERRORE_SERVER);
					throw re;
				}

			}

			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO filtro1VO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			filtro1VO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
			filtro1VO.setIdContoEconomico(idContoEcomonico);
			filtro1VO.setIdVoceDiSpesa(NumberUtil.toBigDecimal(filtro.getIdVoceSpesa()));
			filtro1VO.setIdTipoFornitore(NumberUtil.toBigDecimal(filtro.getIdTipoFornitore()));
			filtro1VO.setIdTipoDocumentoSpesa(NumberUtil.toBigDecimal(filtro.getIdTipoDocumentoSpesa()));
			filtro1VO.setDtEmissioneDocumento(DateUtil.utilToSqlDate(filtro.getDtEmissioneDocumento()));
			filtro1VO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(filtro.getIdDichiarazioneSpesa()));
			if (filtro.getTask() != null
					&& filtro.getTask().trim().length() > 0)
				filtro1VO.setTask(filtro.getTask());

			/*
			 * Filtro per le like
			 */
			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO filtro2VO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			filtro2VO.setNumeroDocumento(filtro.getNumeroDocumento());
			filtro2VO.setPartitaIvaFornitore(filtro.getPartitaIvaFornitore());
			filtro2VO.setCodiceFiscaleFornitore(filtro.getCodiceFiscaleFornitore());
			filtro2VO.setCognomeFornitore(filtro.getCognomeFornitore());
			filtro2VO.setNomeFornitore(filtro.getNomeFornitore());
			filtro2VO.setDenominazioneFornitore(filtro.getDenominazioneFornitore());

			/*
			 * Filtro per gli stati dei pagamenti
			 */
			List<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filtroStatiDocumentoVO = new ArrayList<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>();
 
			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO documentoValidatoVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			documentoValidatoVO.setDescBreveStatoDocSpesa(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO);

			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO documentoNonValidatoVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			documentoNonValidatoVO.setDescBreveStatoDocSpesa(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO);
		
			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO documentoParzValidatoVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			documentoParzValidatoVO.setDescBreveStatoDocSpesa(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO);
			
			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO documentoDaCompletareVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			documentoDaCompletareVO.setDescBreveStatoDocSpesa(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE);
			
			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO documentoSospesoVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			documentoDaCompletareVO.setDescBreveStatoDocSpesa(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_SOSPESO);
			
			// Jira PBANDI-2769
			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO documentoInValidazioneVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			documentoInValidazioneVO.setDescBreveStatoDocSpesa(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE);

			filtroStatiDocumentoVO.add(documentoValidatoVO);
			filtroStatiDocumentoVO.add(documentoNonValidatoVO);
			filtroStatiDocumentoVO.add(documentoParzValidatoVO);
			filtroStatiDocumentoVO.add(documentoDaCompletareVO);
			filtroStatiDocumentoVO.add(documentoInValidazioneVO);	// Jita PBANDI-2769
			filtroStatiDocumentoVO.add(documentoSospesoVO);

			LikeContainsCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> likeCondition = new LikeContainsCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filtro2VO);
			FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterCondition = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filtro1VO);
			FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterConditionStatiPagamento = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filtroStatiDocumentoVO);

			AndCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> andCondition = new AndCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filterCondition, likeCondition,
					filterConditionStatiPagamento);
			
			/*
			 * Aggiunge il filtro per i documenti con rettifiche
			 */
			if (filtro.getIsRettificato() != null && filtro.getIsRettificato()) {
				andCondition = new AndCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
						filterCondition, likeCondition,
						filterConditionStatiPagamento,Condition.not(new NullCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO.class, "totaleRettificaDoc")));
			}

			// Alex: commento ls select sotto poichè nel nuovo i campi nella group by sono ordinati diversamente rispetto al vecchio.
			//List<DocumentoDiSpesaVO> result = genericDAO.findListWhereGroupBy(andCondition, DocumentoDiSpesaVO.class);
			
			// Alex: ho creato una versione di findListWhereGroupBy() che riceve in input l'elenco dei campi da mettere nella group by.
			// listaCampiGroupBy contiene i campi presenti nella group by nella vecchia versione di bandi.
			String listaCampiGroupBy = "ID_TIPO_FORNITORE , DESC_TIPO_DOCUMENTO_SPESA , DESC_BREVE_TIPO_DOC_SPESA , DESC_STATO_DOCUMENTO_SPESA , PARTITA_IVA_FORNITORE , TOTALE_RETTIFICA_DOC , COGNOME_FORNITORE , DT_EMISSIONE_DOCUMENTO , TIPO_INVIO , VALIDATO_PER_DICHIARAZIONE , ID_TIPO_DOCUMENTO_SPESA , DESC_BREVE_STATO_DOC_SPESA , IMPORTO_TOTALE_DOCUMENTO , ID_PROGETTO , DENOMINAZIONE_FORNITORE , CODICE_FISCALE_FORNITORE , NOME_FORNITORE , ID_FORNITORE , ID_DICHIARAZIONE_SPESA , ID_DOCUMENTO_DI_SPESA , ID_STATO_DOCUMENTO_SPESA , TASK , NUMERO_DOCUMENTO, RILIEVO_CONTABILE, DT_RILIEVO_CONTABILE";
			List<DocumentoDiSpesaVO> result = genericDAO.findListWhereGroupBy(andCondition, DocumentoDiSpesaVO.class, listaCampiGroupBy);
			
			return beanUtil.transform(result, DocumentoDiSpesaDTO.class);

		 
	}

	public PagamentoRettificaDTO[] findPagamentiAssociatiDocumentoVoceSpesa(
			Long idUtente, String identitaDigitale, DocumentoDiSpesaDTO filtro)
			throws CSIException, SystemException, UnrecoverableException,
			RettificaException {

		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);

			List<PagamentoRettificaVO> result = findPagamentiRettifica(filtro);

			BigDecimal idPagamento = new BigDecimal(-1);
			PagamentoRettificaDTO dto = null;
			List<VoceDiSpesaRettificaDTO> voci = null;
			List<PagamentoRettificaDTO> listPagamenti = new ArrayList<PagamentoRettificaDTO>();
			for (PagamentoRettificaVO vo : result) {
				if (!vo.getIdPagamento().equals(idPagamento)) {
					if (voci != null) {
						dto.setVociDiSpesa(beanUtil.transform(voci,
								VoceDiSpesaRettificaDTO.class));
					}
					idPagamento = vo.getIdPagamento();
					dto = new PagamentoRettificaDTO();
					voci = new ArrayList<VoceDiSpesaRettificaDTO>();
					dto.setIdPagamento(NumberUtil.toLong(vo.getIdPagamento()));
					dto.setDescBreveModalitaPagamento(vo
							.getDescBreveModalitaPagamento());
					dto.setDescModalitaPagamento(vo.getDescModalitaPagamento());
					/* FIX PBANDI-2314 - Lo stato del pagamento non esiste piu'
					dto.setDescStatoValidazioneSpesa(vo
							.getDescStatoValidazioneSpesa());
					*/
					dto.setDtPagamento(vo.getDtPagamento());
					dto.setDtValuta(vo.getDtValuta());
					dto.setImportoPagamento(NumberUtil.toDouble(vo
							.getImportoPagamento()));
					listPagamenti.add(dto);

				}
				VoceDiSpesaRettificaDTO voce = new VoceDiSpesaRettificaDTO();
				voce.setDescVoceDiSpesa(vo.getDescVoceDiSpesa());
				voce.setIdPagamento(NumberUtil.toLong(vo.getIdPagamento()));
				voce.setIdQuotaParteDocSpesa(NumberUtil.toLong(vo
						.getIdQuotaParteDocSpesa()));
				voce.setImportoQuietanzato(NumberUtil.toDouble(vo
						.getImportoQuietanzato()));
				voce.setImportoValidato(NumberUtil.toDouble(vo
						.getImportoValidato()));
				voce.setProgrPagQuotParteDocSp(NumberUtil.toLong(vo
						.getProgrPagQuotParteDocSp()));
				voce.setImportoTotaleRettifica(NumberUtil.toDouble(vo
						.getImportoTotaleRettifica()));
				voce.setImportoValidatoLordo(NumberUtil.toDouble(vo
						.getImportoValidatoLordo()));
				voci.add(voce);
			}
			if (voci != null) {
				dto.setVociDiSpesa(beanUtil.transform(voci,
						VoceDiSpesaRettificaDTO.class));
			}

			return beanUtil.transform(listPagamenti,
					PagamentoRettificaDTO.class);
		} finally {
		}
	}

	// PBANDI-3064: aggiunto il parametro dichiarazioniDiSpesa.
	private void rispatarramento(Long idUtente,  
			VoceDiSpesaRettificaDTO vds,
			List<PbandiRPagQuotParteDocSpVO> rQuoteDb,
			Set<BigDecimal> dichiarazioniDiSpesa) throws Exception {
		double importoValidato=vds.getImportoValidato();
		double numQuoteParte=rQuoteDb.size();
		logger.warn("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nimportoValidato da rispatarrare: "+importoValidato+" , quote da rispatarrare: "+numQuoteParte);
		double importoQuotaParteDaSanare= importoValidato/numQuoteParte;
		logger.warn("importoQuotaParteDaSanare: "+importoQuotaParteDaSanare);
		int decimalPlace = 2;
		BigDecimal importoQuotaParte = new BigDecimal(importoQuotaParteDaSanare);
		importoQuotaParte = importoQuotaParte.setScale(decimalPlace, BigDecimal.ROUND_DOWN);
		logger.warn("importo da rispatarrare sulla quota: "+importoQuotaParte);
		double importoSenzaResiduo=importoQuotaParte.doubleValue()*numQuoteParte;
		logger.warn("importo senza residuo da spatarrare sulle quote : "+importoSenzaResiduo);
		double rest= NumberUtil.subtract(importoValidato,new BigDecimal(importoSenzaResiduo).doubleValue());
		logger.warn("importo residuo da aggiungere a una quota : "+rest);
		BigDecimal quotaMaggiorata=new BigDecimal(rest+importoQuotaParte.doubleValue());
		logger.warn("quotaMaggiorata: "+quotaMaggiorata.doubleValue());
		
		for (PbandiRPagQuotParteDocSpVO pbandiRPagQuotParteDocSpVO : rQuoteDb) {
			BigDecimal oldImportoValidato = pbandiRPagQuotParteDocSpVO.getImportoValidato();
			
			if(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato()!=null && 
					(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato().doubleValue()>=importoValidato)){
				//metto tutto sulla prima,le altre si beccano 0
				logger.info(" quota parte > di importo validato, setto importoValidato: "+importoValidato);
				pbandiRPagQuotParteDocSpVO.setImportoValidato(BigDecimal.valueOf(importoValidato));
				importoValidato=0;
			}else{
				
				//importoValidato=NumberUtil.subtract(totaleAssociato, totaleAssociato);
				//pbandiRPagQuotParteDocSpVO.setImportoValidato(BigDecimal.valueOf(importoValidato));
				logger.info(" quota parte < di importo validato "+importoValidato+" , tengo il valore che aveva pbandiRPagQuotParteDocSpVO.getImportoQuietanzato() : "+pbandiRPagQuotParteDocSpVO.getImportoQuietanzato());
				pbandiRPagQuotParteDocSpVO.setImportoValidato(pbandiRPagQuotParteDocSpVO.getImportoQuietanzato());
				importoValidato = NumberUtil.subtract(importoValidato , pbandiRPagQuotParteDocSpVO.getImportoQuietanzato().doubleValue() );
				
			}
			pbandiRPagQuotParteDocSpVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(pbandiRPagQuotParteDocSpVO);
			 
			//Set<BigDecimal> dichiarazioniDiSpesa = new HashSet<BigDecimal>();
			logger.info("oldImportoValidato: "+oldImportoValidato+" newImportoValidato: "+pbandiRPagQuotParteDocSpVO.getImportoValidato());
			if (NumberUtil.compare(
					oldImportoValidato,
					pbandiRPagQuotParteDocSpVO.getImportoValidato()) != 0) {
				insertRettifica(idUtente,
						dichiarazioniDiSpesa, vds,
						pbandiRPagQuotParteDocSpVO.getIdPagamento(),
						pbandiRPagQuotParteDocSpVO,
						oldImportoValidato);
			}
		}
	}
	
	
	
	
	public EsitoRettificaPagamenti rettificaPagamenti(Long idUtente,
			String identitaDigitale, Long idDichiarazioneDiSpesa,
			Long idDocumentoDiSpesa, Long idProgetto,
			PagamentoRettificaDTO[] pagamenti) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {

	    	String[] nameParameter = { "idUtente", "identitaDigitale",
					"istanzaAttivita", "idDocumentoDiSpesa", "idProgetto","pagamenti" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,
							idDocumentoDiSpesa, idProgetto, pagamenti);

			EsitoRettificaPagamenti result = new EsitoRettificaPagamenti();
			result.setEsito(Boolean.FALSE);

			
			//Non � possibile eseguire la rettifica.Esiste una proposta di certificazione in fase di elaborazione.  
			checkPropostaCertificazione();
			
			Set<BigDecimal> dichiarazioniDiSpesa = new HashSet<BigDecimal>();

			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();
			Double importoTotaleValidato = new Double(0);
			boolean quotaParteModificata = false;
			for (PagamentoRettificaDTO pagamento : pagamenti) {
				/*
				 * aggiorno l' importo validato delle quote parti (importo validato delle voci di spesa) del pagamento
				 */
				VoceDiSpesaRettificaDTO[] voci = pagamento.getVociDiSpesa();
				boolean validato = false;
				boolean nonValidato = false;
				boolean parzialmenteValidato = false;
				logger.info("for progetto "+idProgetto+" and dichiarazione : "+idDichiarazioneDiSpesa+"  , pagamento with id "+pagamento.getIdPagamento()+" has voci di spesa "+voci.length   );
				
				if (voci != null) {
					try {
						for (VoceDiSpesaRettificaDTO vds : voci) {
							
							Double importoAss = vds.getImportoQuietanzato();
							Double importoVal = vds.getImportoValidato();
							Double importoValidatoLordo = vds.getImportoValidatoLordo();
							Double importoTotaleRettifica = vds.getImportoTotaleRettifica();
	
							/*
							 * Calcolo il totale dell' importo validato. Se
							 * l'importo validato e' nullo allora l' utente non ha
							 * modificato il validato ( quindi il saldo dell'
							 * importo validato sara' dato dalla differenza tra l
							 * 'importo lordo e il totale delle rettifiche),
							 * altrimenti il nuovo importo validato sara' quello
							 * inserito dall'utente.
							 */
							if (vds.getImportoValidato() == null) {
								importoTotaleValidato = NumberUtil
										.toDouble(NumberUtil.sum(
												BeanUtil.transformToBigDecimal(importoTotaleValidato),
												NumberUtil.subtract(
														BeanUtil.transformToBigDecimal(importoValidatoLordo),
														BeanUtil.transformToBigDecimal(importoTotaleRettifica))));
							} else {
								importoTotaleValidato = NumberUtil.sum(
										importoTotaleValidato, importoVal);
								quotaParteModificata = true;
							} 
	
	
							if (NumberUtil.compare(
									NumberUtil.sum(importoAss, importoVal),
									importoAss) == 0)
								nonValidato = true;
							else if (NumberUtil.compare(importoAss, importoVal) > 0)
								parzialmenteValidato = true;
							else if (NumberUtil.compare(importoAss, importoVal) == 0)
								validato = true;
	
							BigDecimal idPagamento = new BigDecimal(vds.getIdPagamento());
	
							PbandiRPagQuotParteDocSpVO filter = new PbandiRPagQuotParteDocSpVO();
						//	filter.setIdPagamento(idPagamento);
							filter.setIdDichiarazioneSpesa(  BigDecimal.valueOf(idDichiarazioneDiSpesa));
							filter.setIdQuotaParteDocSpesa(  BigDecimal.valueOf(vds.getIdQuotaParteDocSpesa()));
							 
							logger.warn("cerco vecchie quote parte con idQuotaParteDocSpesa: "+vds.getIdQuotaParteDocSpesa() +" e idDichiarazione : "+ idDichiarazioneDiSpesa );
							List <PbandiRPagQuotParteDocSpVO> rQuoteDb = genericDAO.findListWhere(filter);
	
							
							// nel caso di pi� quote parte per la stessa voce di spesa, devo rispatarrare
							if(rQuoteDb.size()>1){
								// PBANDI-3064: aggiunto il parametro dichiarazioniDiSpesa.
								logger.info("\n\n\nci sono  pi� quote parte per la stessa voce di spesa, devo rispatarrare"); 
								rispatarramento(idUtente, vds ,rQuoteDb, dichiarazioniDiSpesa);
							} else{
								logger.info("\n\n\nthere's only one quota parte for the same voce di spesa, no need 'rispatarrare' but rettify  importo validato "); 
								PbandiRPagQuotParteDocSpVO pbandiRPagQuotParteDocSpVO =	rQuoteDb.get(0);
								BigDecimal oldImportoValidato = pbandiRPagQuotParteDocSpVO.getImportoValidato();
								logger.info("oldImportoValidato: "+oldImportoValidato);
								logger.info("newImportoValidato: "+vds.getImportoValidato());
								if(oldImportoValidato==null) oldImportoValidato=BigDecimal.valueOf(0d);
								pbandiRPagQuotParteDocSpVO.setImportoValidato(new BigDecimal(vds.getImportoValidato()));
								pbandiRPagQuotParteDocSpVO.setIdUtenteAgg(new BigDecimal(idUtente));
								genericDAO.update(pbandiRPagQuotParteDocSpVO);
								logger.info("updated pbandiRPagQuotParteDocSpVO with new importo :"+vds.getImportoValidato());
								if (NumberUtil.compare(
										oldImportoValidato,
										pbandiRPagQuotParteDocSpVO.getImportoValidato()) != 0) {
									insertRettifica(idUtente,
											dichiarazioniDiSpesa, vds,
											idPagamento,
											pbandiRPagQuotParteDocSpVO,oldImportoValidato);
								}else{
									logger.info("old importo validato == new importo validato, don't insert rettifica!");
								}
								
							}
							
							 
						}
					} catch (Exception e) {
						logger.error(
								"Errore nella update di PBANDI_R_PAG_QUOT_PARTE_DOC_SP ",
								e);
						throw new ValidazioneRendicontazioneException(ERRORE_SERVER);
					}	
					
				}
			}
			
			 checkListManager
					.aggiornaStatoChecklistPerRettifica(idUtente, idProgetto,
							dichiarazioniDiSpesa);
			 
			 // Gestisco l'eventuale caso +Green.
			 checkListManager
				.aggiornaStatoChecklistPerRettificaPiuGreen(idUtente, idProgetto,
						dichiarazioniDiSpesa);

			/*
			 * Aggiorno lo stato del documento solo se ho modificato lo stato di
			 * almeno un pagamento.
			 * 
			 * FIX PBANDI-2314 Aggiorno lo stato del documento solo se
			 * ho modificato almeno un importo validato.
			 * if (pagamentoModificato) {
			 */
			if (quotaParteModificata) {
				  DecodificaDTO decodificaStatoDaCompletare = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE);
				PbandiRDocSpesaProgettoVO documentoDiSpesaVO=new PbandiRDocSpesaProgettoVO();
				documentoDiSpesaVO.setIdDocumentoDiSpesa(BigDecimal.valueOf(idDocumentoDiSpesa));
				documentoDiSpesaVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
				documentoDiSpesaVO = genericDAO.findSingleWhere(documentoDiSpesaVO);
				if(documentoDiSpesaVO.getIdStatoDocumentoSpesa().longValue()!=decodificaStatoDaCompletare.getId().longValue()){
					EsitoRettificaPagamenti esitoUpdateStatoDocumento = updateStatoDocumento( idUtente,
						BigDecimal.valueOf(idDocumentoDiSpesa),
						BigDecimal.valueOf(idProgetto));
					
					if (esitoUpdateStatoDocumento != null
							&& !esitoUpdateStatoDocumento.getEsito()) {
						result.setEsito(Boolean.FALSE);
						if (esitoUpdateStatoDocumento.getMsgs() != null)
							result.setMsgs(esitoUpdateStatoDocumento.getMsgs());
						return result;
					}
				}
			}

			result.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			msgs.add(msg);

			result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));

			return result;
	}
	
	
	private void insertRettifica(Long idUtente,
			Set<BigDecimal> dichiarazioniDiSpesa, VoceDiSpesaRettificaDTO vds,
			BigDecimal idPagamento,
			PbandiRPagQuotParteDocSpVO pbandiRPagQuotParteDocSpVO,
			BigDecimal oldImportoValidato)
			throws RettificaException {
		logger.info("per la quota parte in oggetto (progrpagquot: "+pbandiRPagQuotParteDocSpVO.getProgrPagQuotParteDocSp()+") il nuovo importo validato � diverso dal vecchio");
		PbandiTRettificaDiSpesaVO rettificaVO = new PbandiTRettificaDiSpesaVO();
		rettificaVO.setProgrPagQuotParteDocSp(pbandiRPagQuotParteDocSpVO.getProgrPagQuotParteDocSp());
		BigDecimal importoRettifica =pbandiRPagQuotParteDocSpVO.getImportoValidato();
		if(pbandiRPagQuotParteDocSpVO.getImportoValidato()!=null)
		  importoRettifica = NumberUtil.subtract(
				  pbandiRPagQuotParteDocSpVO.getImportoValidato(),
				  	oldImportoValidato);
		logger.info(" pbandiRPagQuotParteDocSpVO.getImportoValidato() - quotaParteoldVO.getImportoValidato() ["+ pbandiRPagQuotParteDocSpVO.getImportoValidato()+"  "+oldImportoValidato+"] = "+importoRettifica);
		rettificaVO.setImportoRettifica(importoRettifica);
		rettificaVO.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
		rettificaVO.setDtRettifica(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
		rettificaVO.setRiferimento(vds.getRiferimento());
		try {
			logger.info("^^^^^^^^^^^^^^^^^^^^ inserisco rettifica ^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			genericDAO.insert(rettificaVO);

			dichiarazioniDiSpesa
					.add(dichiarazioneDiSpesaManager
							.getIdUltimaDichiarazioneValidataPerIlPagamento(idPagamento));

		} catch (Exception e) {
			logger.error(
					"Errore di inserimento in PBANDI_T_Rettifica_Di_Spesa ",
					e);
			RettificaException ex = new RettificaException(
					ERRORE_SERVER);
			throw ex;
		}
	}

	private void checkPropostaCertificazione() throws RettificaException {
		PbandiTPropostaCertificazVO filtroElab=new PbandiTPropostaCertificazVO();
		filtroElab.setIdStatoPropostaCertif(decodificheManager
				.decodeDescBreve(PbandiDStatoPropostaCertifVO.class,
						Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_ELABORAZIONE));//03elab
		
		PbandiTPropostaCertificazVO filtroElabBozza=new PbandiTPropostaCertificazVO();
		filtroElabBozza.setIdStatoPropostaCertif(decodificheManager
				.decodeDescBreve(PbandiDStatoPropostaCertifVO.class,
						Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_ELABORAZIONE));//03elab
		

		Condition<PbandiTPropostaCertificazVO> query = new OrCondition<PbandiTPropostaCertificazVO>(
				Condition.filterBy(filtroElab),
				Condition.filterBy(filtroElabBozza));
		
		Boolean response = genericDAO.count(query) > 0;
		
		if(response)
			throw new RettificaException (ERRORE_RETTIFICA_PROPOSTA_IN_CORSA);
	}

	
	
	
	private EsitoRettificaPagamenti updateStatoDocumento(Long idUtente,
			BigDecimal idDocumentoDiSpesa, BigDecimal idProgetto) throws RettificaException {
		EsitoRettificaPagamenti esito = new EsitoRettificaPagamenti();
		esito.setEsito(Boolean.TRUE);

		String codiceStatoValidato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_VALIDATO;

		String codiceStatoNonValidato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO;

		String codiceStatoParzialmenteValidato = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO;

		String codiceStatoDichiarabile = GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_DICHIARABILE;

		/*
		 * JIRA-1302. Per calcolare il totale rendicontabile del documento,
		 * ricerco il documento legato a tutti i progetti ed i pagamenti legati
		 * al documento per tutti i progetti.
		 */
		
		/*
		 * FIX PBANDI-2314. Poiche' lo stato del documento e' stato
		 * spostato sulla PBANDI_R_DOC_SPESA_PROGETTO considero solo
		 * i dati relativi al documento e progetto
		 */

		DocumentoDiSpesaRettificaVO docRettificaVO = new DocumentoDiSpesaRettificaVO();
		docRettificaVO.setIdDocumentoDiSpesa(idDocumentoDiSpesa);
		docRettificaVO.setIdProgetto(idProgetto);

		List<DocumentoDiSpesaRettificaVO> documentiVO = genericDAO
				.findListWhere(docRettificaVO);

		BigDecimal totaleRendicontabileDocumento = new BigDecimal(0);
		BigDecimal totaleImportoAssociato = new BigDecimal(0);
		BigDecimal importoTotalePagamenti = new BigDecimal(0);
		BigDecimal totaleValidatoPagamenti = new BigDecimal(0);
		BigDecimal importoTotaleDocumento = null;
		BigDecimal numDichAperteTotale = null;
		BigDecimal numDichAperteProgetto = null;

		if (documentiVO != null) {
			for (DocumentoDiSpesaRettificaVO doc : documentiVO) {
				/*
				 * Calcolo l'importo rendicontabile al netto delle note di
				 * credito
				 */
				BigDecimal totaleRendicontabileParziale = NumberUtil.subtract(
						doc.getImportoRendicontazione(),
						doc.getTotaleRendicontabileNote());

				/*
				 * Calcolo il totale rendicontabile del documento come la somma
				 * di tutti i rendicontabile al netto dell' importo
				 * rendicontabile delle note di credito
				 */
				totaleRendicontabileDocumento = NumberUtil.sum(
						totaleRendicontabileDocumento,
						totaleRendicontabileParziale);

				if (numDichAperteTotale == null)
					numDichAperteTotale = doc.getDichAperteTotale();
				
				/*
				 * FIX PBANDI-2314. Le dichiarazione aperte
				 * da considerare sono quelle per progetto
				 */
				if (numDichAperteProgetto == null)
					numDichAperteProgetto = doc.getDichAperteProgetto();
				
				if (importoTotaleDocumento == null)
					importoTotaleDocumento = doc.getImportoTotaleDocumento();
				
				/*
				 * Al totale delle documento sottraggo il totale delle
				 * note di credito
				 */
				importoTotaleDocumento = NumberUtil.subtract(importoTotaleDocumento, doc.getTotaleImportoNote());
			}
		}

		/*
		 * Calcolo il totale importo associato dei pagamenti associati al
		 * documento come la somma degli importoQuietanzato ed il totale degli
		 * importi dei pagamenti come la somma degli importoPagamento.
		 */

 
		boolean existPagamentoInseritoRespinto = false;
		boolean isAllDichiarazioniChiuse = NumberUtil.compare(
				numDichAperteProgetto, new BigDecimal(0)) == 0;

		/*
		 * Ricerco tutti il pagamenti associati al documento (per tutti i
		 * progetti).
		 */
		/*
		 * FIX PBANDI-2314. Ricerco solo i pagamenti associati al documento
		 * per il progetto corrente
		 */
		DocumentoDiSpesaDTO filtroDocumento = new DocumentoDiSpesaDTO();
		filtroDocumento.setIdDocumentoDiSpesa(NumberUtil
				.toLong(idDocumentoDiSpesa));
		filtroDocumento.setIdProgetto(NumberUtil.toLong(idProgetto));
		List<PagamentoDocumentoProgettoVO> pagamentiDocumento = findAllPagamentiDocumentoRettifica(filtroDocumento);
		BigDecimal idPagamentoPrec = null;
		if (pagamentiDocumento != null) {
			for (PagamentoDocumentoProgettoVO pag : pagamentiDocumento) {
				BigDecimal importoQuietanzato = pag.getImportoQuietanzato() == null ? new BigDecimal(0):pag.getImportoQuietanzato();
				BigDecimal importoValidato = pag.getImportoValidato() == null ? new BigDecimal(0) : pag.getImportoValidato();
				totaleImportoAssociato = NumberUtil.sum(totaleImportoAssociato,importoQuietanzato);

				/*
				 * Calcolo il totale degli importi dei pagamenti associati al
				 * documento. Per lo stesso pagamento, l'importo del pagamento
				 * e' lo stesso.
				 */
				if (idPagamentoPrec == null) { 
					idPagamentoPrec = pag.getIdPagamento();
					importoTotalePagamenti = NumberUtil.sum(importoTotalePagamenti, pag.getImportoPagamento());
				}
				if (NumberUtil.compare(idPagamentoPrec, pag.getIdPagamento()) != 0) {
					importoTotalePagamenti = NumberUtil.sum(importoTotalePagamenti, pag.getImportoPagamento());
				}

				/*
				 * Calcolo il totale degli importi validati dei pagamenti
				 * associati al documento per ogni progetto (NB: non posso
				 * utilizzare l' importo calcolato in precedenza poiche' devo
				 * considerare tutti i progetti)
				 */
				totaleValidatoPagamenti = NumberUtil.sum(totaleValidatoPagamenti, importoValidato);

				/*
				 * FIX PBANDI-2314- Lo stato del pagamento non esiste piu'
				 * 
				if (pag.getDescBreveStatoValidazSpesa().equals(
						decodificaStatoInserito.getDescrizioneBreve())
						|| pag.getDescBreveStatoValidazSpesa().equals(
								decodificaStatoRespinto.getDescrizioneBreve())) {
					existPagamentoInseritoRespinto = true;
				}
				*/

			}
		}

		DecodificaDTO decodificaStatoDocumento = null;

		/*
		 * FIX. PBandi-1302
		 */

		if (!existPagamentoInseritoRespinto
				&& isAllDichiarazioniChiuse
				&& NumberUtil.compare(importoTotalePagamenti,
						importoTotaleDocumento) == 0) {
			/*
			 * Se la somma degli importi validati di tutti i pagamenti del
			 * documento e' uguale alla somma degli importi rendicontabili del
			 * documento (di tutti i progetti) al netto degli importi
			 * rendicontabili delle note di credito, lo stato del documento
			 * diviene VALIDATO.
			 * 
			 * FIX PBANDI-2314 i calcoli sono relativi alla documento
			 * di spesa per il progetto corrente, poiche' lo stato del
			 * documento e' relativo al progetto.
			 */
			if (NumberUtil.compare(totaleValidatoPagamenti,
					totaleRendicontabileDocumento) == 0) {
				decodificaStatoDocumento = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
						codiceStatoValidato);
			}
			/*
			 * Se la somma degli importi validati di tutti i pagamenti del
			 * documento e' uguale 0 allora lo stato del documento diviene NON
			 * VALIDATO
			 */
			else if (NumberUtil.compare(totaleValidatoPagamenti,
					new BigDecimal(0)) == 0) {
				decodificaStatoDocumento = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
						codiceStatoNonValidato);
			}
			/*
			 * Se la somma degli importi validati di tutti i pagamenti del
			 * documento e' maggiore della somma degli importi rendicontabili
			 * del documento (di tutti i progetti) al netto degli importi
			 * rendicontabili delle note di credito, rilasciamo una eccezione
			 * poiche' abbiamo validato di piu' di quanto era possibile.
			 * 
			 * FIX PBANDI-2314 i calcoli sono relativi alla documento
			 * di spesa per il progetto corrente, poiche' lo stato del
			 * documento e' relativo al progetto.
			 */
			else if (NumberUtil.compare(totaleValidatoPagamenti,
					totaleRendicontabileDocumento) > 0) {
				esito.setEsito(Boolean.FALSE);
				List<MessaggioDTO> errorMsgs = new ArrayList<MessaggioDTO>();
				MessaggioDTO error = new MessaggioDTO();
				error.setMsgKey(ERRORE_RETTIFICA_IMPORTO_TOTALE_RENDICONTABILE);
				errorMsgs.add(error);
				esito.setMsgs(beanUtil.transform(errorMsgs, MessaggioDTO.class));
				RettificaException e = new RettificaException(
						ERRORE_RETTIFICA_IMPORTO_TOTALE_RENDICONTABILE);
				logger.error(
						"Importo totale validato dei pagamenti maggiore dell' importo totale rendicontabile del documento (al netto delle note di credito).",
						e);
				return esito;
			}
			/*
			 * Se la somma degli importi validati di tutti i pagamenti del
			 * documento e' minore della somma degli importi rendicontabili del
			 * documento (di tutti i progetti) al netto degli importi
			 * rendicontabili delle note di credito, lo stato del documento
			 * diviene PARZIALMENTE VALIDATO.
			 * 
			 *  FIX PBANDI-2314 i calcoli sono relativi alla documento
			 * di spesa per il progetto corrente, poiche' lo stato del
			 * documento e' relativo al progetto.
			 */
			else {
				decodificaStatoDocumento = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA,
						codiceStatoParzialmenteValidato);
			}
		}

		/*
		 * Aggiorno lo stato del documento e delle note di credito associate al
		 * documento
		 */
		if (decodificaStatoDocumento != null && idDocumentoDiSpesa != null) {
			PbandiRDocSpesaProgettoVO docVO = new PbandiRDocSpesaProgettoVO();
			docVO.setIdDocumentoDiSpesa(idDocumentoDiSpesa);
			docVO.setIdProgetto(idProgetto);
			docVO.setIdStatoDocumentoSpesa(NumberUtil
					.toBigDecimal(decodificaStatoDocumento.getId()));

			DecodificaDTO decodificaTipoDocumentoNC = decodificheManager
					.findDecodifica(
							GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA,
							DocumentoDiSpesaManager
									.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO));
			
			List<DocumentoDiSpesaProgettoVO> noteCreditoAssociate = getDocumentoDiSpesaManager().findNoteCreditoFattura(idProgetto.longValue(), idDocumentoDiSpesa.longValue());
			
			try {
				docVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(docVO);
				for (DocumentoDiSpesaProgettoVO notaAssociate : noteCreditoAssociate) {
					/*
					 * Imposto il filtro per l'update dello stato della nota di credito. 
					 */
					PbandiRDocSpesaProgettoVO filtroNotaCreditoVO = new PbandiRDocSpesaProgettoVO();
					filtroNotaCreditoVO.setIdProgetto(idProgetto);
					filtroNotaCreditoVO.setIdDocumentoDiSpesa(notaAssociate.getIdDocumentoDiSpesa());
					
					/*
					 * Aggiorno lo stato della nota di credito associata al progetto.
					 */
					PbandiRDocSpesaProgettoVO notaCreditoVO = new PbandiRDocSpesaProgettoVO();
					notaCreditoVO.setIdStatoDocumentoSpesa(NumberUtil
					.toBigDecimal(decodificaStatoDocumento.getId()));
					notaCreditoVO.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.update(filtroNotaCreditoVO, notaCreditoVO);
				}

			} catch (Exception e) {
				logger.error(
						"Errore nella update di PBANDI_R_DOC_SPESA_PROGETTO ",
						e);
				RettificaException ex = new RettificaException(ERRORE_SERVER);
				throw ex;
			}
		}
		return esito;
	}

	private BigDecimal getIdContoEconomicoMaster(Long idProgetto)
			throws RettificaException {
		/*
		 * Recupero l'identificativo del conto economico
		 */
		BigDecimal idContoEconomico = null;
		try {
			idContoEconomico = contoEconomicoManager
					.getIdContoMaster(NumberUtil.toBigDecimal(idProgetto));
		} catch (ContoEconomicoNonTrovatoException e) {
			logger.error("Conto economico non trovato", e);
			RettificaException re = new RettificaException(ERRORE_SERVER);
			throw re;
		}
		return idContoEconomico;
	}

	public VoceDiSpesaContoEconomicoDTO findDettaglioVoceContoEconomico(
			Long idUtente, String identitaDigitale,
			VoceDiSpesaContoEconomicoDTO filtro) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);

			ContoEconomicoConRighiEVociVO filtroVO = new ContoEconomicoConRighiEVociVO();
			filtroVO.setIdContoEconomico(NumberUtil.toBigDecimal(filtro
					.getIdContoEconomico()));
			filtroVO.setIdVoceDiSpesa(NumberUtil.toBigDecimal(filtro
					.getIdVoceDiSpesa()));
			filtroVO.setIdVoceDiSpesaPadre(NumberUtil.toBigDecimal(filtro
					.getIdVoceDiSpesaPadre()));

			ContoEconomicoConRighiEVociVO result = genericDAO
					.findSingleWhere(filtroVO);
			return beanUtil.transform(result,
					VoceDiSpesaContoEconomicoDTO.class);

		} finally {
		}
	}

	public VoceDiSpesaContoEconomicoDTO[] findVociSpesaAssociate(Long idUtente,
			String identitaDigitale, Long progrBandoLinea) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, progrBandoLinea);

			/*
			 * Ricerco idBando
			 */
			PbandiRBandoLineaInterventVO bandoVO = new PbandiRBandoLineaInterventVO();
			bandoVO.setProgrBandoLineaIntervento(NumberUtil
					.toBigDecimal(progrBandoLinea));

			bandoVO = genericDAO.findSingleWhere(bandoVO);

			VoceDiSpesaAssociataVO filtro = new VoceDiSpesaAssociataVO();
			filtro.setIdBando(bandoVO.getIdBando());
			filtro.setAscendentOrder("descVoceDiSpesaComposta");
			List<VoceDiSpesaAssociataVO> voci = voceDiSpesaManager
					.findVociDiSpesaAssociate(filtro);

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descVoceDiSpesaComposta", "descVoceDiSpesa");
			map.put("idVoceDiSpesa", "idVoceDiSpesa");

			return beanUtil.transform(voci, VoceDiSpesaContoEconomicoDTO.class,
					map);

		} finally {
		}
	}

	public EsitoRettificaPagamenti checkRettificaPagamenti(Long idUtente,
			String identitaDigitale, Long idProgetto,
			PagamentoRettificaDTO[] pagamenti) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);

			EsitoRettificaPagamenti esito = new EsitoRettificaPagamenti();

			/*
			 * Verifico che l' importo totale validato per il progetto non sia
			 * superiore al totale delle erogazioni per il progetto
			 */
			DocumentoDiSpesaDTO filtroDoc = new DocumentoDiSpesaDTO();
			filtroDoc.setIdProgetto(idProgetto);

			List<PagamentoRettificaVO> pagamentiProgetto = findPagamentiRettificaPerCheck(
					filtroDoc, pagamenti);
			BigDecimal importoTotaleValidatoProgetto = new BigDecimal(0D);
			
			if (pagamentiProgetto != null) {
				for (PagamentoRettificaVO pagamento : pagamentiProgetto) {
					importoTotaleValidatoProgetto = NumberUtil.sum(
							importoTotaleValidatoProgetto,
							pagamento.getImportoValidato());
				}
			}

			
			// jira 1927 TNT nel totale sommava anche l'importo vecchio oltre al nuovo che arriva dal web: lo ricerco
			// nel db e lo tolgo
			Double importoTotaleValidatoOld = 0d;
			for (PagamentoRettificaDTO pagamento : pagamenti) {
				VoceDiSpesaRettificaDTO[] vociDiSpesa = pagamento
						.getVociDiSpesa();
				for (VoceDiSpesaRettificaDTO vds : vociDiSpesa) {
					PbandiRPagQuotParteDocSpVO pagQuotParteVO = new PbandiRPagQuotParteDocSpVO();
					pagQuotParteVO.setIdPagamento(NumberUtil.toBigDecimal(vds
							.getIdPagamento()));
					pagQuotParteVO.setIdQuotaParteDocSpesa(NumberUtil
							.toBigDecimal(vds.getIdQuotaParteDocSpesa()));
					pagQuotParteVO.setProgrPagQuotParteDocSp(NumberUtil
							.toBigDecimal(vds.getProgrPagQuotParteDocSp()));
					pagQuotParteVO = genericDAO.findSingleWhere(pagQuotParteVO);

					if (pagQuotParteVO.getImportoValidato() != null) {
						importoTotaleValidatoOld = NumberUtil
								.toDouble(NumberUtil.sum(
										BeanUtil.transformToBigDecimal(importoTotaleValidatoOld),
										pagQuotParteVO.getImportoValidato()));
					}

				}
			}
			
			if(importoTotaleValidatoProgetto.doubleValue() >0d && importoTotaleValidatoOld>0d)
				importoTotaleValidatoProgetto=NumberUtil.subtract(importoTotaleValidatoProgetto,
						BeanUtil.transformToBigDecimal(importoTotaleValidatoOld));
			
			
			// jira 1927 TNT FINE 
			
			
			/*
			 * Aggiungo al totaleImportoValidatoProgetto anche gli importi
			 * validati del documento di spesa che si vuole rettificare, solo se
			 * questi sono maggiori di zero
			 */
			if (pagamenti != null) {
				for (PagamentoRettificaDTO pagamento : pagamenti) {
					if (pagamento.getVociDiSpesa() != null) {
						for (VoceDiSpesaRettificaDTO voce : pagamento
								.getVociDiSpesa()) {
							if (NumberUtil.compare(voce.getImportoValidato(),
									0D) > 0) {
								importoTotaleValidatoProgetto = NumberUtil.sum(
										importoTotaleValidatoProgetto, BeanUtil
												.transformToBigDecimal(voce
														.getImportoValidato()));
							}
						}
					}
				}
			}

			PbandiTErogazioneVO erogazioneVO = new PbandiTErogazioneVO();
			erogazioneVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			List<PbandiTErogazioneVO> erogazioniProgetto = genericDAO
					.findListWhere(erogazioneVO);
			BigDecimal importoTotaleErogazioni = new BigDecimal(0D);
			if (erogazioniProgetto != null && !erogazioniProgetto.isEmpty()) {
				for (PbandiTErogazioneVO vo : erogazioniProgetto) {
					importoTotaleErogazioni = NumberUtil.sum(
							importoTotaleErogazioni, vo.getImportoErogazione());
				}
				if (NumberUtil.compare(importoTotaleValidatoProgetto,
						importoTotaleErogazioni) < 0) {
					logger.warn("Le rettifiche effettuate sulla spesa validata hanno prodotto un totale validato ("
							+ importoTotaleValidatoProgetto
							+ ") inferiore al totale erogato ("
							+ importoTotaleErogazioni + ").");
					esito.setEsito(Boolean.FALSE);
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(WARNING_RETTIFICA_IMPORTO_EROGAZIONI_SUPERATO);
					String argImportoTotaleValidatoProgettoString = "";
					String argImportoTotaleErogazioni = "";
					if (importoTotaleValidatoProgetto != null) {
						argImportoTotaleValidatoProgettoString = NumberUtil
								.getStringValueItalianFormat(NumberUtil
										.toDouble(importoTotaleValidatoProgetto));
					}

					if (importoTotaleErogazioni != null) {
						argImportoTotaleErogazioni = NumberUtil
								.getStringValueItalianFormat(NumberUtil
										.toDouble(importoTotaleErogazioni));
					}
					msg.setParams(new String[] {
							argImportoTotaleValidatoProgettoString,
							argImportoTotaleErogazioni });
					esito.setMsgs(new MessaggioDTO[] { msg });
					return esito;
				}
			}
			esito.setEsito(Boolean.TRUE);
			return esito;
		} finally {
		}

	}

	private List<PagamentoRettificaVO> findPagamentiRettifica(
			DocumentoDiSpesaDTO filtro) throws RettificaException {

		BigDecimal idContoEconomico = null;
		if (filtro.getIdProgetto() != null) {
			/*
			 * Recupero l'identificativo del conto economico master
			 */
			try {
				idContoEconomico = contoEconomicoManager
						.getIdContoMaster(NumberUtil.toBigDecimal(filtro
								.getIdProgetto()));
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error("Conto economico non trovato", e);
				RettificaException re = new RettificaException(ERRORE_SERVER);
				throw re;
			}
		}
		DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO filtroVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
		filtroVO.setIdContoEconomico(idContoEconomico);
		filtroVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(filtro.getIdDocumentoDiSpesa()));
		filtroVO.setIdVoceDiSpesa(NumberUtil.toBigDecimal(filtro.getIdVoceSpesa()));
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
		filtroVO.setIdDichiarazioneSpesa(NumberUtil.toBigDecimal(filtro.getIdDichiarazioneSpesa()));
 

		FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterCondition = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
				filtroVO);
	 
		

		PagamentoRettificaVO groupByVO = new PagamentoRettificaVO();
		groupByVO.setAscendentOrder("idPagamento");

		List<PagamentoRettificaVO> result = genericDAO.findListWhereGroupBy(
				filterCondition, groupByVO);
		return result;
	}

	/*
	 * Resituisce tutti i pagamenti del progetto meno quelli legati al documento
	 * di spesa per il quale si vuole rettficare la spesa validata e per i quali
	 * e' stato inserito un importo di rettifica maggiore di zero (importo
	 * legato alla quotapartedocspesa)
	 */
	private List<PagamentoRettificaVO> findPagamentiRettificaPerCheck(
			DocumentoDiSpesaDTO filtro, PagamentoRettificaDTO[] pagamenti)
			throws RettificaException {

		BigDecimal idContoEconomico = null;
		if (filtro.getIdProgetto() != null) {
			/*
			 * Recupero l'identificativo del conto economico master
			 */
			try {
				idContoEconomico = contoEconomicoManager
						.getIdContoMaster(NumberUtil.toBigDecimal(filtro
								.getIdProgetto()));
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error("Conto economico non trovato", e);
				RettificaException re = new RettificaException(ERRORE_SERVER);
				throw re;
			}
		}
		DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO filtroVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
		filtroVO.setIdContoEconomico(idContoEconomico);
		filtroVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(filtro
				.getIdDocumentoDiSpesa()));
		filtroVO.setIdVoceDiSpesa(NumberUtil.toBigDecimal(filtro
				.getIdVoceSpesa()));
		filtroVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));

		/*
		 * Filtro per gli stati dei pagamenti
		 */
		
		/*
		 * FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
		List<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filtroStatiPagamentoVO = new ArrayList<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>();

		DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO pagamentoValidatoVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
		pagamentoValidatoVO
				.setDescBreveStatoValidazSpesa(pagamentoManager
						.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_VALIDATO));

		DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO pagamentoNonValidatoVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
		pagamentoNonValidatoVO
				.setDescBreveStatoValidazSpesa(pagamentoManager
						.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_NON_VALIDATO));

		DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO pagamentoParzValidatoVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
		pagamentoParzValidatoVO
				.setDescBreveStatoValidazSpesa(pagamentoManager
						.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_PARZIALMENTE_VALIDATO));
		filtroStatiPagamentoVO.add(pagamentoValidatoVO);
		filtroStatiPagamentoVO.add(pagamentoNonValidatoVO);
		filtroStatiPagamentoVO.add(pagamentoParzValidatoVO);
		*/


		List<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filtroQuoteParte = new ArrayList<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>();
		if (pagamenti != null) {
			for (PagamentoRettificaDTO pagamento : pagamenti) {
				if (pagamento.getVociDiSpesa() != null) {
					for (VoceDiSpesaRettificaDTO voce : pagamento
							.getVociDiSpesa()) {
						if (NumberUtil.compare(voce.getImportoValidato(), 0D) > 0) {
							DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO quotaParteVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
							quotaParteVO.setProgrPagQuotParteDocSp(NumberUtil
									.toBigDecimal(voce
											.getProgrPagQuotParteDocSp()));
							filtroQuoteParte.add(quotaParteVO);
						}
					}
				}
			}
		}

		/*
		 * FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
		FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterConditionStatiPagamento = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
				filtroStatiPagamentoVO);
		*/
		FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterCondition = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
				filtroVO);

		/*
		 * FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
		AndCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> andCondition = new AndCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
				filterCondition, filterConditionStatiPagamento);
		*/
		
		PagamentoRettificaVO groupByVO = new PagamentoRettificaVO();
		groupByVO.setAscendentOrder("idPagamento");
		
		List<PagamentoRettificaVO> result = null;

		if (!filtroQuoteParte.isEmpty()) {
			FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterConditionQuoteParte = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filtroQuoteParte);
			AndCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> andCondition = new AndCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filterCondition, filterConditionQuoteParte);
			result = genericDAO.findListWhereGroupBy(
					andCondition, groupByVO);
		} else {
			 result = genericDAO.findListWhereGroupBy(
					 filterCondition, groupByVO);
		}


		return result;
	}

	public RettificaDTO[] findRettifiche(Long idUtente,
			String identitaDigitale, RettificaDTO filtro) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);

			RettificaVoceDiSpesaPagamentoVO filtroVO = new RettificaVoceDiSpesaPagamentoVO();
			filtroVO.setIdPagamento(NumberUtil.toBigDecimal(filtro
					.getIdPagamento()));
			filtroVO.setIdRettificaDiSpesa(NumberUtil.toBigDecimal(filtro
					.getIdRettificaDiSpesa()));
			filtroVO.setIdVoceDiSpesa(NumberUtil.toBigDecimal(filtro
					.getIdVoceDiSpesa()));
			filtroVO.setProgrPagQuotParteDocSp(NumberUtil.toBigDecimal(filtro
					.getProgrPagQuotParteDocSp()));
			filtroVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(filtro
					.getIdDocumentoDiSpesa()));
			filtroVO.setIdProgetto(NumberUtil.toBigDecimal(filtro
					.getIdProgetto()));
			filtroVO.setAscendentOrder("idPagamento", "progrPagQuotParteDocSp",
					"dtRettifica", "idRettificaDiSpesa");

			RettificaVO groupByVO = new RettificaVO();
			
			List<RettificaVO> result = genericDAO.findListWhereGroupBy(
					new FilterCondition<RettificaVoceDiSpesaPagamentoVO>(
							filtroVO), groupByVO);
			return beanUtil.transform(result, RettificaDTO.class);
		} finally {
		}
	}

	public Boolean checkPropostaCertificazione(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			Boolean result = Boolean.TRUE;

			ValidatorInput.verifyNullValue(nameParameter, idProgetto);
			PbandiTDettPropostaCertifVO vo = new PbandiTDettPropostaCertifVO();
			vo.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));

			if (genericDAO.where(Condition.filterBy(vo)).count() == 0)
				result = Boolean.FALSE;

			return result;

		} finally {
		}
	}

	public DichiarazioneDiSpesaRettificaDTO[] findDichiarazioniPerRettifica(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			RettificaException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);
			DichiarazioneSpesaRettificaVO filtroVO = new DichiarazioneSpesaRettificaVO();
			filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			filtroVO.setDescendentOrder("dtChiusuraValidazione");

			List<DichiarazioneSpesaRettificaVO> result = genericDAO
					.findListWhere(filtroVO);
			return beanUtil.transform(result,
					DichiarazioneDiSpesaRettificaDTO.class);

	}

	private List<PagamentoDocumentoProgettoVO> findAllPagamentiDocumentoRettifica(
			DocumentoDiSpesaDTO filtro) throws RettificaException {
		
		
		PagamentoDocumentoProgettoVO filterVO = new PagamentoDocumentoProgettoVO();
		filterVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(filtro.getIdDocumentoDiSpesa()));
		filterVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
		 List<PagamentoDocumentoProgettoVO> result = genericDAO.findListWhere(Condition.filterBy(filterVO));

		
		return result;
	}

	public void concludiAttivaRettifica(Long idUtente, String identitaDigitale,
			IstanzaAttivitaDTO istanzaAttivita,Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"istanzaAttivita" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, istanzaAttivita);
			try {
				
				 
			/*	logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+idProgetto);
				Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, idProgetto);
				logger.warn("processo: "+processo);
				if(processo!=null){*/
					logger.warn("\n\n############################ NEOFLUX ##############################\n");
					//9/7/2015 bruno don't call end attivita, it there always must be  
				/*	neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto, Task.GEST_SPESA_VALID);
					logger.warn("############################ NEOFLUX ##############################\n\n\n\n");
					*/
					
					//	${${data_rettifica_spesa_validata}}	DATA_RETTIFICA_SPESA_VALIDATA
					List<MetaDataVO>metaDatas=new ArrayList<MetaDataVO>();
					String descrBreveTemplateNotifica=Notification.NOTIFICA_GESTIONE_SPESA_VALIDATA;
					MetaDataVO metadata1=new MetaDataVO(); 
					metadata1.setNome(Notification.DATA_RETTIFICA_SPESA_VALIDATA);
					metadata1.setValore( DateUtil.getDate(new Date()));
					metaDatas.add(metadata1);

					
					logger.info("calling genericDAO.callProcedure().putNotificationMetadata....");
					genericDAO.callProcedure().putNotificationMetadata(metaDatas);
					
					logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
					genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.BENEFICIARIO,idUtente);
					
					
			/*	}else{
				
				
				
				
				// +flux+ 
					logger.warn("\n\n############################ OLDFLUX ##############################\ncalling getProcessManager().concludiAttivaRettifica " );
					getProcessManager().concludiAttivaRettifica(idUtente,identitaDigitale);
					logger.warn("concludiAttivaRettifica OK\n############################ OLDFLUX ##############################\n\n\n\n");
				}*/
				
				
			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
				RecuperoException re = new RecuperoException(ERRORE_SERVER);
				throw re;
			}
		 

	}

	public void setDichiarazioneDiSpesaManager(
			DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager) {
		this.dichiarazioneDiSpesaManager = dichiarazioneDiSpesaManager;
	}

	public DichiarazioneDiSpesaManager getDichiarazioneDiSpesaManager() {
		return dichiarazioneDiSpesaManager;
	}

	public EsitoRettificaPagamenti controlloPagamenti(Long idUtente,
			String identitaDigitale, java.lang.Long idDichiarazioneDiSpesa,Long idDocumentoDiSpesa, Long idProgetto,
			PagamentoRettificaDTO[] pagamenti,
			java.lang.Double importoValidatoContoEconomico) throws CSIException,
			SystemException, UnrecoverableException, RettificaException {
		try {
			EsitoRettificaPagamenti result = new EsitoRettificaPagamenti();
			result.setEsito(Boolean.FALSE);

			Double importoTotaleValidato = new Double(0);
			Double importoTotaleValidatoOld  = new Double(0);
			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();
			BigDecimal idContoEconomico = getIdContoEconomicoMaster(idProgetto);
			boolean isRettifica = false;

			/*
			 * recupero dell' importo cerificato dell'ultima proposta approvata 
			 */

			Double  importoUltimoCertificato = certificazioneManager.findImportoUltimaPropostaCertificazionePerProgetto(idProgetto, it.csi.pbandi.pbweb.pbandiutil.common.Constants.STATO_PROPOSTA_CERTIFICAZIONE_APPROVATA);
			for (PagamentoRettificaDTO pagamento : pagamenti) {
				/*
				 * aggiorno l' importo validato delle quote parti (importo
				 * validato delle voci di spesa) del pagamento
				 */
			// dichiarazioneDiSpesaManager.getIdUltimaDichiarazioneValidataPerIlPagamento(BigDecimal.valueOf(pagamento.getIdPagamento()));
				
				
				VoceDiSpesaRettificaDTO[] voci = pagamento.getVociDiSpesa();
				logger.info("\n\n\n\n\nil pagamento con id "+pagamento.getIdPagamento()+" ha queste voci di spesa "+voci.length 
						+" e idDichiarazione  : "+idDichiarazioneDiSpesa );

				if (voci != null) {
					boolean hasVoceError = false;
					for (VoceDiSpesaRettificaDTO vds : voci) {
						logger.info("vds.getImportoValidato() : "+	  vds.getImportoValidato());
						Double importoAss = vds.getImportoQuietanzato();
						Double importoVal = vds.getImportoValidato();
						Double importoValidatoLordo = vds.getImportoValidatoLordo();
						Double importoTotaleRettifica = vds.getImportoTotaleRettifica();
						/*
						 * Calcolo il totale dell' importo validato. Se
						 * l'importo validato e' nullo allora l' utente non ha
						 * modificato il validato ( quindi il saldo dell'
						 * importo validato sara' dato dalla differenza tra l
						 * 'importo lordo e il totale delle rettifiche),
						 * altrimenti il nuovo importo validato sara' quello
						 * inserito dall'utente.
						 */
						if (vds.getImportoValidato() == null) {
							importoTotaleValidato = NumberUtil
									.toDouble(NumberUtil.sum(
											BeanUtil.transformToBigDecimal(importoTotaleValidato),
											NumberUtil.subtract(
													BeanUtil.transformToBigDecimal(importoValidatoLordo),
													BeanUtil.transformToBigDecimal(importoTotaleRettifica))));
						} else {
							importoTotaleValidato = NumberUtil.sum(
									importoTotaleValidato, importoVal);
						}
						/*
						 * calcolo importo validato precedente alla validazione
						 */
					 
						PbandiRPagQuotParteDocSpVO filter = new PbandiRPagQuotParteDocSpVO();
						filter.setIdDichiarazioneSpesa( BigDecimal.valueOf(idDichiarazioneDiSpesa));
						filter.setIdQuotaParteDocSpesa(  BigDecimal.valueOf(vds.getIdQuotaParteDocSpesa()));
						 
						logger.info("cerco vecchie quote parte con idQuotaParteDocSpesa: "+vds.getIdQuotaParteDocSpesa() 
								+" e idDichiarazione : "+ idDichiarazioneDiSpesa );
						List <PbandiRPagQuotParteDocSpVO> rQuoteDb = genericDAO.findListWhere(filter);
						for (PbandiRPagQuotParteDocSpVO pbandiRPagQuotParteDocSpVO : rQuoteDb) {
							if(pbandiRPagQuotParteDocSpVO.getImportoValidato()!=null)
								importoTotaleValidatoOld=NumberUtil.sum(importoTotaleValidatoOld, pbandiRPagQuotParteDocSpVO.getImportoValidato().doubleValue());
						}

						 
						
						if (vds.getImportoValidato() != null) {

							/*
							 * Verifico che l'importo validato sia maggiore di 0
							 */
							if (NumberUtil.compare(importoVal, 0D) < 0) {
								RettificaException e = new RettificaException(
										ERRORE_RETTIFICA_IMPORTO_VALIDATO_NEGATIVO);
								logger.error(
										"Impossibile inserire importo validato",
										e);
								MessaggioDTO msg = new MessaggioDTO();
								msg.setMsgKey(ERRORE_RETTIFICA_IMPORTO_VALIDATO_NEGATIVO);
								msg.setId(vds.getProgrPagQuotParteDocSp());
								msgs.add(msg);
								hasVoceError = true;
							}

							/*
							 * Verifico che l'importo validato sia minore o
							 * uguale all' importo associato
							 */
							if (!hasVoceError
									&& NumberUtil.compare(importoAss,
											importoVal) < 0) {
								RettificaException e = new RettificaException(
										ERRORE_RETTIFICA_IMPORTO_VALIDATO_MAGGIORE_IMPORTO_ASSOCIATO);
								logger.error("Importo validato " + importoVal
										+ " maggiore dell' importo associato "
										+ importoAss, e);
								MessaggioDTO msg = new MessaggioDTO();
								msg.setMsgKey(ERRORE_RETTIFICA_IMPORTO_VALIDATO_MAGGIORE_IMPORTO_ASSOCIATO);
								msg.setId(vds.getProgrPagQuotParteDocSp());
								msgs.add(msg);
								hasVoceError = true;
							}

							/*
							 * Verifico che l'importo totale validato per la
							 * voce di spesa non sia superiore all' importo
							 * quota parte del documento di spesa.
							 */
							DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO filtroVoceVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
							filtroVoceVO.setIdDocumentoDiSpesa(NumberUtil
									.toBigDecimal(idDocumentoDiSpesa));
							filtroVoceVO
									.setIdQuotaParteDocSpesa(NumberUtil
											.toBigDecimal(vds
													.getIdQuotaParteDocSpesa()));
							filtroVoceVO.setIdProgetto(NumberUtil
									.toBigDecimal(idProgetto));
							filtroVoceVO.setIdContoEconomico(idContoEconomico);
							//tnt 25/05/2015
							filtroVoceVO.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazioneDiSpesa));
							
							List<VoceDiSpesaRettificaVO> listVoci = genericDAO
									.findListWhereGroupBy(
											new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
													filtroVoceVO),VoceDiSpesaRettificaVO.class);
							
							BigDecimal importoTotaleValidatoVoce = new BigDecimal(0D);
							if (listVoci != null) {
								for (VoceDiSpesaRettificaVO vo : listVoci) {
									importoTotaleValidatoVoce = NumberUtil.sum(importoTotaleValidatoVoce,vo.getImportoValidato());
								}
							}

							PbandiTQuotaParteDocSpesaVO documentoQuotaParteVO = new PbandiTQuotaParteDocSpesaVO();
							documentoQuotaParteVO
									.setIdDocumentoDiSpesa(BigDecimal.valueOf(idDocumentoDiSpesa));
							documentoQuotaParteVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
							documentoQuotaParteVO.setIdQuotaParteDocSpesa(BigDecimal.valueOf(vds.getIdQuotaParteDocSpesa()));
							documentoQuotaParteVO = genericDAO.findSingleWhere(documentoQuotaParteVO);
							logger.warn("\n\n\n\n\n\n\n\n\nimportoTotaleValidatoVoce : "+importoTotaleValidatoVoce+" , documentoQuotaParteVO.getImportoQuotaParteDocSpesa(); "+documentoQuotaParteVO.getImportoQuotaParteDocSpesa());
							
							if (!hasVoceError
									&& NumberUtil.compare(
													importoTotaleValidatoVoce,
													documentoQuotaParteVO.getImportoQuotaParteDocSpesa()) > 0) {
								RettificaException e = new RettificaException(
										ERRORE_RETTIFICA_IMPORTO_QUOTA_PARTE_SUPERATO);
								logger.error(
										"Importo validato per la voce di spesa maggiore dell' importo quota parte del documento.",
										e);
								MessaggioDTO msg = new MessaggioDTO();
								msg.setMsgKey(ERRORE_RETTIFICA_IMPORTO_QUOTA_PARTE_SUPERATO);
								msg.setParams(new String[] { vds.getDescVoceDiSpesa() });
								msgs.add(msg);
								hasVoceError = true;
							}

							/*
							 * Verifico se per la voce di spesa e' stato
							 * eseguita una rettifica. Quindi confronto l'
							 * importo validato inserito dall'utente con l'
							 * importo validato che ho sul db
							 */
						 	logger.info("importoTotaleValidatoOld: "+importoTotaleValidatoOld
						 			+" vs vds.getImportoValidato():"+ vds.getImportoValidato());
							if (NumberUtil.compare(BigDecimal.valueOf(importoTotaleValidatoOld),
									BeanUtil.transformToBigDecimal(vds
											.getImportoValidato())) != 0) {
								/*
								 * E' stata eseguita una rettifica
								 */
								isRettifica = true;
							}
						}
					}
				}
			}

			/*
			 * verifico che  il totale nuovo validato sia inferiore all'ultimo certificato dell'ultima proposta approvata
			 * il controllo ha senzo se � stata eseguita una rettifica
			 */

			// parametro di input
			if (importoUltimoCertificato != null
					&& importoTotaleValidato != null && importoValidatoContoEconomico !=null) {
				Double importoTotaleValidatoPostRettificaDelProgetto = NumberUtil
						.toDouble(NumberUtil
								.sum(
										BeanUtil
												.transformToBigDecimal(importoValidatoContoEconomico),
										NumberUtil
												.subtract(
														BeanUtil
																.transformToBigDecimal(importoTotaleValidato),
														BeanUtil
																.transformToBigDecimal(importoTotaleValidatoOld))));
				if (NumberUtil.compare(importoUltimoCertificato,
						importoTotaleValidatoPostRettificaDelProgetto) > 0) {
					RettificaException e = new RettificaException(
							ERRORE_TOTALE_VALIDATO_PER_RETTIFICA_INFERIORE_ULTIMO_CERTIFICATO_NETTO);
					logger.error("Importo totale validato "
							+ importoTotaleValidatoPostRettificaDelProgetto
							+ " inferiore a ultimo certificato "
							+ importoUltimoCertificato, e);

					MessaggioDTO msg = new MessaggioDTO();
					
					msg
							.setMsgKey(ERRORE_TOTALE_VALIDATO_PER_RETTIFICA_INFERIORE_ULTIMO_CERTIFICATO_NETTO);
					msg.setParams(new String[] { String
							.valueOf(importoUltimoCertificato) });
					msgs.add(msg);
				}
			}

			/*
			 * Se non � stata eseguita nessuna rettifica segnalo l' errore
			 */

			if (!isRettifica) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessaggiConstants.MSG_RETTIFICA_NESSUN_DATO_MODIFICATO);
				msgs.add(msg);
			}
			
			if (msgs.isEmpty()) {
				result.setEsito(Boolean.TRUE);
			}
			result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));
			return result;

		} finally {
		}

	}

	
	public it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoReportDettaglioDocumentiDiSpesaDTO generaReportDettaglioDocumentoDiSpesa(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ValidazioneRendicontazioneException {

		try {

			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			ReportDocumentiSpesaVO reportVO = new ReportDocumentiSpesaVO();
			reportVO.setIdProgetto(
					new BigDecimal(idProgetto));
			reportVO.setDescendentOrder("idDichiarazioneSpesa");
			
			/*
			 * FIX PBandi-2314.
			 * Escludo i documento-progetto in stato IN VALIDAZIONE 
			 */
			DecodificaDTO statoInValidazione = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_DOCUMENTO_SPESA, GestioneDatiDiDominioSrv.STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE);
			
			
			
			List<ReportDocumentiSpesaVO> filterStati = new ArrayList<ReportDocumentiSpesaVO>();
			ReportDocumentiSpesaVO filterStatoInValidazione = new ReportDocumentiSpesaVO();
			filterStatoInValidazione.setIdStatoDocumentoSpesa(NumberUtil.toBigDecimal(statoInValidazione.getId()));
			filterStati.add(filterStatoInValidazione);
			
			
			AndCondition<ReportDocumentiSpesaVO> andCondition = new AndCondition<ReportDocumentiSpesaVO>(Condition.filterBy(reportVO), Condition.not(Condition.filterBy(filterStati)));
			
			List<ReportDocumentiSpesaVO> elementiReport = genericDAO
					.findListWhere(andCondition);

			EsitoReportDettaglioDocumentiDiSpesaDTO esito = null;
			if (!isEmpty(elementiReport)) {
				for (ReportDocumentiSpesaVO reportDocumentiSpesaVO : elementiReport) {
					logger.warn("note chiusura id dich: "+reportDocumentiSpesaVO.getIdDichiarazioneSpesa()+" : "+reportDocumentiSpesaVO.getNoteChiusuraValidazione()+"\n,note doc "+reportDocumentiSpesaVO.getIdDocumentoDiSpesa()+" : "+reportDocumentiSpesaVO.getNoteValidazione());
				}
				
				byte[] reportDettaglioDocumentiSpesaFileData = TableWriter
						.writeTableToByteArray(
								"reportDocumentiSpesaRettifica",
								new ExcelDataWriter(idProgetto
										.toString()), elementiReport);

				String nomeFile = "reportValidazione"
						+ idProgetto + ".xls";

				esito = new EsitoReportDettaglioDocumentiDiSpesaDTO();
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
	
	
/*
	public DichiarazioneDiSpesaRettificaDTO[] findDichiarazioniDocumento(
			Long idUtente, String identitaDigitale, Long idDocumentoSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, RettificaException {

		 
			String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoSpesa", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoSpesa, idProgetto);
			DocumentoDiSpesaDichiarazioneVO filter = new DocumentoDiSpesaDichiarazioneVO();
			filter.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoSpesa));
			filter.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			filter.setAscendentOrder("idDichiarazioneSpesa");
		 
			NullCondition<DocumentoDiSpesaDichiarazioneVO> dtChiusuraValidazioneNull = new NullCondition<DocumentoDiSpesaDichiarazioneVO>(DocumentoDiSpesaDichiarazioneVO.class,"dtChiusuraValidazione");
		 
			DocumentoDiSpesaDichiarazioneVO nomeFileDichFilter=new DocumentoDiSpesaDichiarazioneVO();
			nomeFileDichFilter.setNomeFile("DichiarazioneDiSpesa");
			DocumentoDiSpesaDichiarazioneVO nomeFileReportFilter=new DocumentoDiSpesaDichiarazioneVO();
			nomeFileReportFilter.setNomeFile("reportDettaglioDocumentiDiSpesa");
			DocumentoDiSpesaDichiarazioneVO nomeFileReportValidazioneFilter=new DocumentoDiSpesaDichiarazioneVO();
			nomeFileReportFilter.setNomeFile("reportValidazione");
			OrCondition<DocumentoDiSpesaDichiarazioneVO> orCondition = new OrCondition<DocumentoDiSpesaDichiarazioneVO>(
					new LikeStartsWithCondition<DocumentoDiSpesaDichiarazioneVO>(nomeFileDichFilter), 
					new LikeStartsWithCondition<DocumentoDiSpesaDichiarazioneVO>(nomeFileReportFilter),
					new LikeStartsWithCondition<DocumentoDiSpesaDichiarazioneVO>(nomeFileReportValidazioneFilter));
			AndCondition<DocumentoDiSpesaDichiarazioneVO> condition = new AndCondition<DocumentoDiSpesaDichiarazioneVO>(Condition.filterBy(filter), 
					Condition.not(dtChiusuraValidazioneNull) ,orCondition );
			List<DocumentoDiSpesaDichiarazioneVO> result = genericDAO.findListWhere(condition);
			List<DichiarazioneDiSpesaRettificaDTO> dichiarazioni=new ArrayList<DichiarazioneDiSpesaRettificaDTO>();
			for (DocumentoDiSpesaDichiarazioneVO vo : result) {
				DichiarazioneDiSpesaRettificaDTO dto = beanUtil.transform(vo,DichiarazioneDiSpesaRettificaDTO.class);
				if(vo.getNomeFile().startsWith("DichiarazioneDiSpesa"))
					dto.setIdDocIndexDichSpesa(vo.getIdDocumentoIndex().longValue());
				else if(vo.getNomeFile().startsWith("reportDettaglioDocumentiDiSpesa") ||
						vo.getNomeFile().startsWith("reportValidazione") )
					dto.setIdDocIndexReportDettaglio(vo.getIdDocumentoIndex().longValue());
				dichiarazioni.add(dto);
			}
			return dichiarazioni.toArray(new DichiarazioneDiSpesaRettificaDTO[0]);
					
	}
*/
	
	public DichiarazioneDiSpesaRettificaDTO[] findDichiarazioniDocumento(
			Long idUtente, String identitaDigitale, Long idDocumentoSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, RettificaException {

		 
			String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoSpesa", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoSpesa, idProgetto);
			
			
			List<DocumentoDiSpesaDichiarazioneVO> result = pbandiDichiarazioneDiSpesaDAO.findDichiarazioni(idProgetto, idDocumentoSpesa);
			
			List<DichiarazioneDiSpesaRettificaDTO> dichiarazioni=new ArrayList<DichiarazioneDiSpesaRettificaDTO>();
			
			// Codice precedente alla jira PBANDI-2769.
			/*
			for (DocumentoDiSpesaDichiarazioneVO vo : result) {
				DichiarazioneDiSpesaRettificaDTO dto = beanUtil.transform(vo,DichiarazioneDiSpesaRettificaDTO.class);
				if(vo.getNomeFile()!=null){
					if(vo.getNomeFile().toUpperCase().startsWith("DICHIARAZIONEDISPESA") || vo.getNomeFile().toUpperCase().startsWith("COMUNICAZIONE"))
						dto.setIdDocIndexDichSpesa(vo.getIdDocumentoIndex().longValue());
					else if(vo.getNomeFile().toUpperCase().startsWith("REPORTDETTAGLIODOCUMENTIDISPESA") ||
							vo.getNomeFile().toUpperCase().startsWith("REPORTVALIDAZIONE") )
						dto.setIdDocIndexReportDettaglio(vo.getIdDocumentoIndex().longValue());
					} 
				dichiarazioni.add(dto);
			}
			*/
			
			// Codice nuovo per la jira PBANDI-2769.
			List<DocumentoDiSpesaDichiarazioneVO> files = null;
			for (DocumentoDiSpesaDichiarazioneVO vo : result) {
				DichiarazioneDiSpesaRettificaDTO dto = beanUtil.transform(vo,DichiarazioneDiSpesaRettificaDTO.class);
				
				// considero i file associati a PBANDI_T_DICHIARAZIONE_SPESA: pdf della DS e xls report validazione.
				files = pbandiDichiarazioneDiSpesaDAO.findDocIndexDs(
						vo.getIdProgetto().longValue(), 
						vo.getIdDichiarazioneSpesa().longValue(),
						"PBANDI_T_DICHIARAZIONE_SPESA");
				// Popolo IdDocIndexDichSpesa e IdDocIndexReportDettaglio.
				for (DocumentoDiSpesaDichiarazioneVO file : files) {
					if(file.getNomeFile().toUpperCase().startsWith("DICHIARAZIONEDISPESA") || 
					   file.getNomeFile().toUpperCase().startsWith("COMUNICAZIONE")) {
						dto.setIdDocIndexDichSpesa(file.getIdDocumentoIndex().longValue());
						logger.info(dto.getIdDocIndexDichSpesa()+" in IdDocIndexDichSpesa");
					} else if(file.getNomeFile().toUpperCase().startsWith("REPORTDETTAGLIODOCUMENTIDISPESA") ||
							file.getNomeFile().toUpperCase().startsWith("REPORTVALIDAZIONE")) {
						dto.setIdDocIndexReportDettaglio(file.getIdDocumentoIndex().longValue());
						logger.info(dto.getIdDocIndexReportDettaglio()+" in IdDocIndexReportDettaglio");
					}
				}
				
				// DS finale con comunicazione: considero il pdf associato a PBANDI_T_COMUNICAZ_FINE_PROG.
				if (dto.getIdTipoDichiarazSpesa().intValue() == 3) {					
					Long idTarget = pbandiDichiarazioneDiSpesaDAO.findIdComunicazFineProgetto(vo.getIdProgetto().longValue());
					files = pbandiDichiarazioneDiSpesaDAO.findDocIndexDs(
							vo.getIdProgetto().longValue(),
							idTarget,
							"PBANDI_T_COMUNICAZ_FINE_PROG");
					// Popolo IdDocIndexDichSpesa e IdDocIndexReportDettaglio.
					for (DocumentoDiSpesaDichiarazioneVO file : files) {
						if(file.getNomeFile().toUpperCase().startsWith("DICHIARAZIONEDISPESA") || 
						   file.getNomeFile().toUpperCase().startsWith("COMUNICAZIONE")) {
							dto.setIdDocIndexDichSpesa(file.getIdDocumentoIndex().longValue());
							logger.info(dto.getIdDocIndexDichSpesa()+" in IdDocIndexDichSpesa");
						} else if(file.getNomeFile().toUpperCase().startsWith("REPORTDETTAGLIODOCUMENTIDISPESA") ||
								file.getNomeFile().toUpperCase().startsWith("REPORTVALIDAZIONE")) {
							dto.setIdDocIndexReportDettaglio(file.getIdDocumentoIndex().longValue());
							logger.info(dto.getIdDocIndexReportDettaglio()+" in IdDocIndexReportDettaglio");
						}
					}
				}				
				
				dichiarazioni.add(dto);
			}
			
			return dichiarazioni.toArray(new DichiarazioneDiSpesaRettificaDTO[0]);
					
	}
}