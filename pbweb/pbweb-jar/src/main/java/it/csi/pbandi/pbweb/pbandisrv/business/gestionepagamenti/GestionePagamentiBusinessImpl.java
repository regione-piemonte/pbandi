package it.csi.pbandi.pbweb.pbandisrv.business.gestionepagamenti;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.CausalePagamentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.EsitoOperazioneCancellazionePagamento;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.EsitoOperazioneInserimentoPagamento;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.EsitoOperazioneModificaPagamento;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.ModalitaPagamentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.ManagerException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionepagamenti.GestionePagamentoException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiPagamentiDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTipoDocumentoSpesaModalitaPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioEnteGiuridicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ModalitaPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCausalePagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagQuotParteDocSpVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentidispesa.GestioneDocumentiDiSpesaSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionepagamenti.GestionePagamentiSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class GestionePagamentiBusinessImpl extends BusinessImpl implements
		GestionePagamentiSrv {

	private class EsitoScenarioBando {
		private String dataPagamento;
		private boolean visualizzaImportiVociDiSpesa = false;
		

		public void setDataPagamento(String dataPagamento) {
			this.dataPagamento = dataPagamento;
		}

		public String getDataPagamento() {
			return dataPagamento;
		}

		public void setVisualizzaImportiVociDiSpesa(
				boolean visualizzaImportiVociDiSpesa) {
			this.visualizzaImportiVociDiSpesa = visualizzaImportiVociDiSpesa;
		}

		public boolean isVisualizzaImportiVociDiSpesa() {
			return visualizzaImportiVociDiSpesa;
		}

		
	}

	public static final String LOGGER_PREFIX = "pbandisrv";
	private static final String STATO_PAGAMENTO_INSERITO="I";
	
	@Autowired
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	
	@Autowired
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	
	@Autowired
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	
	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	private ProgettoManager progettoManager;
	
	@Autowired
	private SoggettoManager soggettoManager;

	@Autowired
	private GenericDAO genericDAO;
	
	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public PbandiPagamentiDAOImpl getPbandipagamentiDAO() {
		return pbandipagamentiDAO;
	}

	public void setPbandipagamentiDAO(PbandiPagamentiDAOImpl pbandipagamentiDAO) {
		this.pbandipagamentiDAO = pbandipagamentiDAO;
	}
	public void setDocumentoDiSpesaManager(
			DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}

	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}
	
	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public void setPbandiDocumentiDiSpesaDAO(
			PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}
	
	/**
	 * Ricerca l'elenco dei pagamenti per l'idDocumentoDiSpesa e scenario
	 * individuato dall'idBando
	 * 
	 * @param idUtente
	 * @param identitaDigitale
	 * @param idDocumentoDiSpesa
	 * @param idBando
	 * @return
	 * @throws GestionePagamentoException
	 */
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO[] findPagamentiAssociati(

	Long idUtente,

	String identitaDigitale,

	Long idDocumentoDiSpesa,

	Long idBando,

	Long idProgetto

	) throws GestionePagamentoException, FormalParameterException

	{
		/**
		 * Definisco scenario per idbando -linea sar� da rinominare l'input
		 */
		EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);

		String nameParameter[] = { "idDocumentoDiSpesa", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idDocumentoDiSpesa,
				idProgetto);

		/**
		 * Verifico che il doc di spesa non sia di tipo NOTA CREDITO o SPESA
		 * FORFETTARIA se si rilancio eccezione con err E024
		 */
		boolean esito = verificaTipologiaDocumentoDiSpesa(idDocumentoDiSpesa);
		if (esito) {
			throw new GestionePagamentoException(
					ErrorConstants.ERRORE_TIPOLOGIA_DOC_DI_SPESA_PER_IL_PAGAMENTO);
		}

		
		// Per proseguire nella gestione dei pagamenti il documento di spesa,
		// nello scenario delle voci di di spesa, deve avere almeno una voce di
		// spesa assoicata
		// Alex 14/03/2022: commento poichè dà errore inserendo un doc di spesa per Finpiemonte - BANDO FONDO MPMI.
		//                  il controllo forse serviva nel vecchio per la gestione a TAB di doc\VdS\quietanze.
		/*
		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			boolean esitoAssociazione = verificaAssociazioneVociDiSpesaByIdDocumentoIdProgetto(
					idDocumentoDiSpesa, idProgetto);
			if (!esitoAssociazione) {
				throw new GestionePagamentoException(
						ErrorConstants.ERRORE_DOCUMENTO_DI_SPESA_SENZA_VOCI_DI_SPESA);
			}
		}
		*/

		java.util.List<PagamentoQuotePartiVO> listaVO = new ArrayList<PagamentoQuotePartiVO>();
		listaVO = pbandipagamentiDAO.findPagamentiAssociati(idDocumentoDiSpesa,
				idProgetto, esitoScenarioBando.getDataPagamento(),
				esitoScenarioBando.isVisualizzaImportiVociDiSpesa());
		logger.info("[GestionePagamentiBusinessImpl::findPagamentiAssociati]Doc found:"
						+ listaVO.size()+"\n\n");
		PagamentoDTO[] pagamentiDTO = new PagamentoDTO[listaVO.size()];
		getBeanUtil().valueCopy(listaVO.toArray(), pagamentiDTO);

		return pagamentiDTO;

	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO[] findPagamentiAssociatiPerValidazione(

	Long idUtente,

	String identitaDigitale,

	Long idDocumentoDiSpesa,

	Long idBando,

	Long idProgetto

	) throws GestionePagamentoException, FormalParameterException {
		/**
		 * Definisco scenario per idbando -linea sar� da rinominare l'input
		 */
		EsitoScenarioBando esitoScenarioBando = initScenarioBandoPerValidazione(idBando);

		String nameParameter[] = { "idDocumentoDiSpesa", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idDocumentoDiSpesa,
				idProgetto);

		/**
		 * Verifico che il doc di spesa non sia di tipo NOTA CREDITO o SPESA
		 * FORFETTARIA se si rilancio eccezione con err E024
		 */
		boolean esito = verificaTipologiaDocumentoDiSpesa(idDocumentoDiSpesa);
		if (esito) {
			throw new GestionePagamentoException(
					ErrorConstants.ERRORE_TIPOLOGIA_DOC_DI_SPESA_PER_IL_PAGAMENTO);
		}

		/**
		 * Per proseguire nella gestione dei pagamenti il documento di spesa,
		 * nello scenario delle voci di di spesa, deve avere almeno una voce di
		 * spesa assoicata
		 */
		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			boolean esitoAssociazione = verificaAssociazioneVociDiSpesaByIdDocumentoIdProgetto(
					idDocumentoDiSpesa, idProgetto);
			if (!esitoAssociazione) {
				throw new GestionePagamentoException(
						ErrorConstants.ERRORE_DOCUMENTO_DI_SPESA_SENZA_VOCI_DI_SPESA);
			}
		}

		java.util.List<PagamentoQuotePartiVO> listaVO = new ArrayList<PagamentoQuotePartiVO>();
		listaVO = pbandipagamentiDAO.findPagamentiAssociati(idDocumentoDiSpesa,
				idProgetto, esitoScenarioBando.getDataPagamento(),
				esitoScenarioBando.isVisualizzaImportiVociDiSpesa());
		logger
				.debug("[GestionePagamentiBusinessImpl::findPagamentiAssociatiPerValidazione]Doc trovati:"
						+ listaVO.size());
		PagamentoDTO[] pagamentiDTO = new PagamentoDTO[listaVO.size()];
		getBeanUtil().valueCopy(listaVO.toArray(), pagamentiDTO);

		return pagamentiDTO;

	}

	/**
	 * 
	 * Ricerca e popola la tabella degli importi(pagamenti) ripartiti sulle voci
	 * di spesa del documento di spesa associato
	 * 
	 * @param idDocumentoDiSpesa
	 * @param idPagamento
	 * @param utente
	 * @param idProgetto
	 * @return PagamentoVoceDiSpesaDTO[]
	 * @throws GestionePagamentoException
	 */
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] findPagamentiVociDiSpesa(

	Long idUtente,

	String identitaDigitale,

	Long idDocumentoDiSpesa,

	Long idPagamento,

	Long idProgetto

	) throws GestionePagamentoException, FormalParameterException {

		/**
		 * Verifico la validit� dei dati di input tranne idPagamento che in fase
		 * di inserimento sarebbe null
		 */
		String nameParameter[] = { "idDocumentoDiSpesa", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idDocumentoDiSpesa,
				idProgetto);

		if (idPagamento == null || idPagamento <= 0l) {
			idPagamento = -1l;
		}

		// List<PagamentoVoceDiSpesaVO> pagamentiVociDiSpesaVO =
		// pbandipagamentiDAO.findPagamentiVociDiSpesa(idDocumentoDiSpesa,
		// idPagamento);
		/**
		 * FIX: PBandi-383 Le voci di spesa associate al pagamento devono essere
		 * filtrate per il progetto corrente
		 */
		// List<PagamentoVoceDiSpesaVO> pagamentiVociDiSpesaVO =
		// pbandipagamentiDAO
		// .findVociDiSpesaByIdDocumentoByIdPagamento(idDocumentoDiSpesa,
		// idPagamento);
		List<PagamentoVoceDiSpesaVO> pagamentiVociDiSpesaVO = pbandipagamentiDAO
				.findVociDiSpesaByIdDocumentoIdPagamentoIdProgetto(
						idDocumentoDiSpesa, idPagamento, idProgetto);
		logger
				.debug("[GestionePagamentiBusinessImpl::findPagamentiVociDiSpesa] Voci Di Spesa Trovate:"
						+ pagamentiVociDiSpesaVO.size());

		/**
		 * Se idPagamento == null sono in nuovo pagamento, quindi le voci di
		 * spesa alla voce importo quietanzato e importo nuovo avranno '0'
		 * 
		 * 
		 * 
		 */
		List<PagamentoVoceDiSpesaDTO> tempList = new ArrayList<PagamentoVoceDiSpesaDTO>();
		for (PagamentoVoceDiSpesaVO pagamentiVO : pagamentiVociDiSpesaVO) {

			PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO = new PagamentoVoceDiSpesaDTO();
			pagamentoVoceDiSpesaDTO.setIdQuotaParteDocSpesa(pagamentiVO
					.getIdQuotaParteDocSpesa());
			pagamentoVoceDiSpesaDTO.setIdRigoContoEconomico(pagamentiVO
					.getIdRigoContoEconomico());
			pagamentoVoceDiSpesaDTO.setIdVoceDiSpesa(pagamentiVO
					.getIdVoceDiSpesa());
			pagamentoVoceDiSpesaDTO
					.setVoceDiSpesa(pagamentiVO.getVoceDiSpesa());
			pagamentoVoceDiSpesaDTO.setImportoRendicontato(pagamentiVO
					.getImportoRendicontato());

			/**
			 * NOTA: - importoQuietanzato valorizza 'totale altri importi' sul
			 * client - nuovoPagamentoVoceDiSpesa valorizza 'importo del
			 * pagamento corrente'
			 */
			pagamentoVoceDiSpesaDTO.setTotaleAltriPagamenti(pagamentiVO
					.getTotaleAltriPagamenti() == null ? 0d : pagamentiVO
					.getTotaleAltriPagamenti());

			/**
			 * IMPORTANTE: Se sono in modifica avr� l'"importo completamente
			 * pagato" solo se la voce di spesa non � associata al pagamento
			 * corrente, perch� in caso contrario il quietanzato della voce di
			 * spesa del pagamento si rimette in gioco!
			 */
			double totaleAltriPagamenti = pagamentiVO.getTotaleAltriPagamenti() == null ? 0d
					: pagamentiVO.getTotaleAltriPagamenti();
			if (pagamentiVO.getImportoRendicontato() <= totaleAltriPagamenti) {

				/**
				 * Sono in 'importo completamente quietanzato'
				 */
				pagamentoVoceDiSpesaDTO.setImportoVoceDiSpesaCorrente(-1d);
			} else {
				pagamentoVoceDiSpesaDTO
						.setImportoVoceDiSpesaCorrente(pagamentiVO
								.getImportoVoceDiSpesaCorrente() == null ? 0d
								: pagamentiVO.getImportoVoceDiSpesaCorrente());
			}

			tempList.add(pagamentoVoceDiSpesaDTO);
		}

		return (PagamentoVoceDiSpesaDTO[]) tempList
				.toArray(new PagamentoVoceDiSpesaDTO[0]);
	}

	public EsitoOperazioneInserimentoPagamento forzaInserisciPagamento(
			Long idUtente, String identitaDigitale,
			PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,
			PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestionePagamentoException {
		logger.begin();
		
		/**
		 * Simulo il controllo sull'idBando..definisce quattro possibili scenari
		 */
		EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);

		/**
		 * Verifico il nullable dei parametri di input, per quelli obbligatori
		 * la gestione della notifica del messaggio corretto la lascio al client
		 */
		String nameParameter[] = { "PagamentoDTO", "idDocumentoDiSpesa","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento,
				idDocumentoDiSpesa, idProgetto);

		/*
		 * MODIFICA 15 GIUGNO
		 */
		/*
		 * String descrBreveStatoValidazioneSpesa = pagamento
		 * .getDescBreveStatoValidazioneSpesa();logger.debug(
		 * "controllo che lo stato del pagamento rientri tra INSERITO,RILASCIATO e PARZAILMENTE VALIDATO :"
		 * +pagamento .getDescBreveStatoValidazioneSpesa()); if (!pagamento
		 * .getDescBreveStatoValidazioneSpesa() .equals( PagamentiManager
		 * .getCodiceStatoValidazionePagamento
		 * (GestionePagamentiSrv.STATO_VALIDAZIONE_INSERITO)) && !pagamento
		 * .getDescBreveStatoValidazioneSpesa() .equals( PagamentiManager
		 * .getCodiceStatoValidazionePagamento
		 * (GestionePagamentiSrv.STATO_VALIDAZIONE_RILASCIATO)) && !pagamento
		 * .getDescBreveStatoValidazioneSpesa() .equals( PagamentiManager
		 * .getCodiceStatoValidazionePagamento
		 * (GestionePagamentiSrv.STATO_VALIDAZIONE_PARZIALMENTE_VALIDATO)))
		 * throw new GestionePagamentoException(
		 * ERRORE_STATO_VALIDAZIONE_PAGAMENTO);
		 */

		/*
		 * MODIFICA 15 GIUGNO
		 */

		/**
		 * Recupero i dati relativi al Documento di spesa:
		 * dataEmissioneDocumento somma dei pagamenti associati al documento di
		 * spesa importo totale del documento di spesa al netto delle note di
		 * credito se il documento � di tipo 4
		 * 
		 */
		EsitoOperazioneInserimentoPagamento esitoOperazioneInserimento = new EsitoOperazioneInserimentoPagamento();
		esitoOperazioneInserimento.setEsito(Boolean.FALSE);

		DocumentoDiSpesaDTO documentoDTO = new DocumentoDiSpesaDTO();
		PDocumentoDiSpesaVO documentoVO = pbandipagamentiDAO
				.getInfoPagamentoDocumentoDiSpesa(idDocumentoDiSpesa);
		BeanUtil.valueCopy(documentoVO, documentoDTO);
		List<String> messaggi = new ArrayList<String>();

		verificaDatiPagamento(esitoScenarioBando.getDataPagamento(), pagamento,
				documentoDTO.getDataEmissione(), messaggi, idProgetto, documentoVO.getIdTipoDocDiSpesa());

		// JIRA 399 del 16 luglio
		Double sommaImportiNoteDiCredito = getSommaImportiNoteDiCredito(idDocumentoDiSpesa);

		// JIRA 418 del 16 luglio
		// se cedolino il terzo parmaetro non � pi� l'importo totale netto ma
		// il + grande tra importo totale netto e rendicontabile tra + progetti
		// (somma dei vari PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE)
		Double importoTotaleNetto = documentoDTO.getImportoTotaleNetto() == null ? 0.0
				: documentoDTO.getImportoTotaleNetto();
		if (documentoDiSpesaManager
				.isCedolinoOAutodichiarazioneSoci(documentoVO
						.getIdTipoDocDiSpesa())) {
			importoTotaleNetto = calcolaImportoTotaleNetto(idDocumentoDiSpesa,
					importoTotaleNetto);
		}

		// }L{ PBANDI-328
		try {
			validaSommeTotali(pagamento.getImportoPagamento(), documentoDTO
					.getSommaPagamenti(), importoTotaleNetto,
					sommaImportiNoteDiCredito);
		} catch (GestionePagamentoException e) {
			if (e.getMessage().equals(
					ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneInserimento.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double sommaPagamenti = 0D;
				if (documentoDTO.getSommaPagamenti() != null)
					sommaPagamenti = documentoDTO.getSommaPagamenti();
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamenti;
				/*
				 * FIX: PBandi-550 Correzione arrotondamento
				 */
				esitoOperazioneInserimento.setParams(new String[] { String
						.valueOf(NumberUtil.toRoundDouble(residuo)) });
				logger
						.debug(ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO
								+ " {0} = " + residuo.toString());
				esitoOperazioneInserimento
						.setMessage(ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO);
				return esitoOperazioneInserimento;
			} else {
				throw e;
			}
		}

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			try{
				validaCoerenzaImporti(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
				validaSommeParziali(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
			}catch(GestionePagamentoException ex){
				esitoOperazioneInserimento.setEsito(Boolean.FALSE);
				esitoOperazioneInserimento.setParams(new String[]{ex.getMessage()});
				
				esitoOperazioneInserimento.setMessage(ex.getMessage());

				return esitoOperazioneInserimento;
			}
		}

		// List<String> messaggi = new ArrayList<String>();

		Long idNuovoPagamentoAssociato = pbandipagamentiDAO
				.inserisciPagamento(
						pagamento.getIdModalitaPagamento(),
						new java.sql.Date(pagamento.getDtPagamento().getTime()),
						pagamento.getImportoPagamento(),
						idUtente,
						STATO_PAGAMENTO_INSERITO ,
						esitoScenarioBando.getDataPagamento(),
						pagamento.getIdCausalePagamento(),
						pagamento.getRifPagamento(), null);
		
		pbandipagamentiDAO.inserisciPagamentoRDocSpesa(
				idNuovoPagamentoAssociato, idDocumentoDiSpesa, 
				idUtente);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			for (PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO : arrayPagamentiVociDiSpesa) {
				if (pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente()!=null && 
						pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente().doubleValue() > 0.0D) {
					PbandiRPagQuotParteDocSpVO pagQuotParteDocSpVO = trasformaInVO(
							idUtente, idNuovoPagamentoAssociato,
							pagamentoVoceDiSpesaDTO);
					try {
						genericDAO.insert(pagQuotParteDocSpVO);
					} catch (Exception e) {
						throw new UnrecoverableException(
								"Impossibile inserire relazioni tra pagamenti e quote parte.",
								e);
					}
				}
			}
		}

		esitoOperazioneInserimento.setEsito(Boolean.TRUE);
		esitoOperazioneInserimento
				.setParams(new String[] { idNuovoPagamentoAssociato.toString() });
		esitoOperazioneInserimento
				.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		return esitoOperazioneInserimento;

	}

	public EsitoOperazioneModificaPagamento forzaModificaPagamento(
			Long idUtente, String identitaDigitale,
			PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,
			PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestionePagamentoException {
		// TODO Auto-generated method stub
		EsitoOperazioneModificaPagamento esitoOperazioneModifica = new EsitoOperazioneModificaPagamento();
		esitoOperazioneModifica.setEsito(Boolean.FALSE);
		List<String> messaggi = new ArrayList<String>();

		/**
		 * Simulo il controllo sull'idBando..definisce quattro possibili scenari
		 */
		EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);

		/**
		 * Verifico il nullable dei parametri di input, per quelli obbligatori
		 * la gestione della notifica del messaggio corretto la lascio al client
		 */
		String nameParameter[] = { "PagamentoDTO", "idDocumentoDiSpesa" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento,
				idDocumentoDiSpesa);

		/**
		 * 1� controllo Pagamento in stato diverso da INSERITO e RESPINTO
		 * M8(Errore: non � possibile modificare un pagamento in stato
		 * <stato_pagamento>)
		 */
		// AGGIUNTO STATO RESPINTO IL 27 LUGLIO
		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento.
		if (!pagamento
				.getDescBreveStatoValidazioneSpesa()
				.equals(
						PagamentiManager
								.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_INSERITO))
				&& !pagamento
						.getDescBreveStatoValidazioneSpesa()
						.equals(
								PagamentiManager
										.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_RESPINTO))

		)
		
		{
			gestisciEsitoModificaErrato(pagamento, esitoOperazioneModifica,
					messaggi);
			return esitoOperazioneModifica;
		}
		*/

		/**
		 * Recupero i dati relativi al Documento di spesa:
		 * dataEmissioneDocumento somma dei pagamenti associati al documento di
		 * spesa importo totale del documento di spesa al netto delle note di
		 * credito se il documento � di tipo 4
		 * 
		 */
		DocumentoDiSpesaDTO documentoDTO = new DocumentoDiSpesaDTO();
		PDocumentoDiSpesaVO documentoVO = pbandipagamentiDAO
				.getInfoPagamentoDocumentoDiSpesa(idDocumentoDiSpesa);
		BeanUtil.valueCopy(documentoVO, documentoDTO);

		verificaDatiPagamento(esitoScenarioBando.getDataPagamento(), pagamento,
				documentoDTO.getDataEmissione(), messaggi, idProgetto, documentoVO.getIdTipoDocDiSpesa());

		PbandiTPagamentoVO pagamentoVO = new PbandiTPagamentoVO();
		pagamentoVO.setIdPagamento(new BigDecimal(pagamento.getIdPagamento()));
		PbandiTPagamentoVO[] pagamentiPrimaModificaVO = genericDAO
				.findWhere(pagamentoVO);
		// JIRA 399 del 16 luglio
		Double sommaImportiNoteDiCredito = getSommaImportiNoteDiCredito(idDocumentoDiSpesa);

		// JIRA 418 del 16 luglio
		// se cedolino il terzo parmaetro non � pi� l'importo totale netto ma
		// il + grande tra importo totale netto e rendicontabile tra + progetti
		// (somma dei vari PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE)
		Double importoTotaleNetto = documentoDTO.getImportoTotaleNetto() == null ? 0.0
				: documentoDTO.getImportoTotaleNetto();
		if (documentoDiSpesaManager
				.isCedolinoOAutodichiarazioneSoci(documentoVO
						.getIdTipoDocDiSpesa())) {
			importoTotaleNetto = calcolaImportoTotaleNetto(idDocumentoDiSpesa,
					importoTotaleNetto);
		}

		Double sommaPagamenti = documentoDTO.getSommaPagamenti();

		Double importoPagamento = 0D;
		if (!isEmpty(pagamentiPrimaModificaVO)
				&& !isNull(pagamentiPrimaModificaVO[0].getImportoPagamento()))
			importoPagamento = pagamentiPrimaModificaVO[0]
					.getImportoPagamento().doubleValue();

		Double sommaPagamentiDocumentoDiSpesa = 0D;
		if (!isNull(sommaPagamenti) && !isNull(importoPagamento))
			sommaPagamentiDocumentoDiSpesa = sommaPagamenti.doubleValue()
					- importoPagamento.doubleValue();

		// }L{ PBANDI-328
		try {
			validaSommeTotali(pagamento.getImportoPagamento(),
					sommaPagamentiDocumentoDiSpesa, importoTotaleNetto,
					sommaImportiNoteDiCredito);
		} catch (GestionePagamentoException e) {
			if (e.getMessage().equals(
					ERRORE_VOCE_DI_SPESA_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneModifica.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamentiDocumentoDiSpesa;
			//	logger.debug(e.getMessage() + " {0} = " + residuo.toString());
				esitoOperazioneModifica.setParams(new String[] { NumberUtil.getStringValueItalianFormat(residuo)});
				esitoOperazioneModifica.setMessage(e.getMessage());
				return esitoOperazioneModifica;
			} else if (e.getMessage().equals(
					ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneModifica.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamentiDocumentoDiSpesa;
			//	logger.debug(e.getMessage() + " {0} = " + residuo.toString());
				esitoOperazioneModifica.setParams(new String[] {NumberUtil.getStringValueItalianFormat(residuo)});
				esitoOperazioneModifica.setMessage(e.getMessage());
				return esitoOperazioneModifica;
			} else {
				throw e;
			}
		}

		/*
		 * validaSommeTotali(pagamento.getImportoPagamento(),
		 * documentoDTO.getSommaPagamenti() -
		 * pagamentiPrimaModificaVO[0].getImportoPagamento()
		 * .longValue(),importoTotaleNetto ,sommaImportiNoteDiCredito);
		 */
		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {

			// ValidatorInput.verifyArrayNotEmpty("arrayPagamentiVociDiSpesa",
			// arrayPagamentiVociDiSpesa);

			validaCoerenzaImporti(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
			validaSommeParziali(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);

		}

		pbandipagamentiDAO.modificaPagamento(pagamento.getIdPagamento(),
				pagamento.getIdModalitaPagamento(), new java.sql.Date(pagamento
						.getDtPagamento().getTime()), pagamento
						.getImportoPagamento(), idUtente, esitoScenarioBando
						.getDataPagamento(),
						pagamento.getIdCausalePagamento(),
						pagamento.getRifPagamento(), null);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			modificaPagamentiVociDiSpesa(pagamento.getIdPagamento(),
					arrayPagamentiVociDiSpesa, idUtente);
		}

		esitoOperazioneModifica.setEsito(Boolean.TRUE);
		messaggi.add(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		/*
		 * if (messaggi != null && !messaggi.isEmpty() &&
		 * messaggi.contains(ERRORE_VALIDAZIONE_DATA_VALUTA_PAGAMENTO)) // TODO
		 * avviso dataPagamento < dataDichiarazione esitoOperazioneModifica
		 * .setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO); else
		 */
		esitoOperazioneModifica.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		return esitoOperazioneModifica;

	}

	public EsitoOperazioneInserimentoPagamento inserisciPagamento(
			Long idUtente,
			String identitaDigitale,
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento,
			Long idBando,
			Long idDocumentoDiSpesa,
			Long idProgetto

	) throws GestionePagamentoException, FormalParameterException,
			UnrecoverableException {

		logger.begin();
		
		/**
		 * Simulo il controllo sull'idBando..definisce quattro possibili scenari
		 */
		EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);

		/**
		 * Verifico il nullable dei parametri di input, per quelli obbligatori
		 * la gestione della notifica del messaggio corretto la lascio al client
		 */
		String nameParameter[] = { "PagamentoDTO", "idDocumentoDiSpesa",
				"idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento,
				idDocumentoDiSpesa, idProgetto);

		logger.shallowDump(pagamento,"info");
		
		/*
		 * MODIFICA 15 GIUGNO
		 */
		/*
		 * String descrBreveStatoValidazioneSpesa = pagamento
		 * .getDescBreveStatoValidazioneSpesa();logger.debug(
		 * "controllo che lo stato del pagamento rientri tra INSERITO,RILASCIATO e PARZAILMENTE VALIDATO :"
		 * +pagamento .getDescBreveStatoValidazioneSpesa()); if (!pagamento
		 * .getDescBreveStatoValidazioneSpesa() .equals( PagamentiManager
		 * .getCodiceStatoValidazionePagamento
		 * (GestionePagamentiSrv.STATO_VALIDAZIONE_INSERITO)) && !pagamento
		 * .getDescBreveStatoValidazioneSpesa() .equals( PagamentiManager
		 * .getCodiceStatoValidazionePagamento
		 * (GestionePagamentiSrv.STATO_VALIDAZIONE_RILASCIATO)) && !pagamento
		 * .getDescBreveStatoValidazioneSpesa() .equals( PagamentiManager
		 * .getCodiceStatoValidazionePagamento
		 * (GestionePagamentiSrv.STATO_VALIDAZIONE_PARZIALMENTE_VALIDATO)))
		 * throw new GestionePagamentoException(
		 * ERRORE_STATO_VALIDAZIONE_PAGAMENTO);
		 */

		/*
		 * MODIFICA 15 GIUGNO
		 */

		/**
		 * Recupero i dati relativi al Documento di spesa:
		 * dataEmissioneDocumento somma dei pagamenti associati al documento di
		 * spesa importo totale del documento di spesa al netto delle note di
		 * credito se il documento � di tipo 4
		 * 
		 */
		EsitoOperazioneInserimentoPagamento esitoOperazioneInserimento = new EsitoOperazioneInserimentoPagamento();
		esitoOperazioneInserimento.setEsito(Boolean.FALSE);

		DocumentoDiSpesaDTO documentoDTO = new DocumentoDiSpesaDTO();
		PDocumentoDiSpesaVO documentoVO = pbandipagamentiDAO
				.getInfoPagamentoDocumentoDiSpesa(idDocumentoDiSpesa);
		BeanUtil.valueCopy(documentoVO, documentoDTO);
		List<String> messaggi = new ArrayList<String>();
		
		/*
		 * VN: FIX PBandi-1834
		 * I controlli validi sono quelli di 
		 * verificaDataPagamentoCheckNonBloccante
		 */

//		verificaDatiPagamento(esitoScenarioBando.getDataPagamento(), pagamento,
//				documentoDTO.getDataEmissione(), messaggi, idProgetto);

		// JIRA 399 del 16 luglio
		Double sommaImportiNoteDiCredito = getSommaImportiNoteDiCredito(idDocumentoDiSpesa);

		// JIRA 418 del 16 luglio
		// se cedolino il terzo parmaetro non � pi� l'importo totale netto ma
		// il + grande tra importo totale netto e rendicontabile tra + progetti
		// (somma dei vari PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE)
		Double importoTotaleNetto = documentoDTO.getImportoTotaleNetto() == null ? 0.0
				: documentoDTO.getImportoTotaleNetto();
		try {
			if (documentoDiSpesaManager
					.isCedolinoOAutodichiarazioneSoci(documentoVO
							.getIdTipoDocDiSpesa())) {
				importoTotaleNetto = calcolaImportoTotaleNetto(
						idDocumentoDiSpesa, importoTotaleNetto);
			}

			// }L{ PBANDI-328
			validaSommeTotali(pagamento.getImportoPagamento(), documentoDTO
					.getSommaPagamenti(), importoTotaleNetto,
					sommaImportiNoteDiCredito);
		} catch (Exception e) {
			if (e.getMessage().equals(
					ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneInserimento.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double sommaPagamenti = 0D;
				if (documentoDTO.getSommaPagamenti() != null)
					sommaPagamenti = documentoDTO.getSommaPagamenti();
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamenti;
				/*
				 * FIX: PBandi-550 Correzione arrotondamento
				 */
				esitoOperazioneInserimento.setParams(new String[] { String
						.valueOf(NumberUtil.toRoundDouble(residuo)) });
				logger
						.debug(ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO
								+ " {0} = " + residuo.toString());
				esitoOperazioneInserimento
						.setMessage(ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO);
				return esitoOperazioneInserimento;
			} else {
				throw new UnrecoverableException(e.getMessage());
			}
		}

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {

			try{
				validaCoerenzaImporti(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
				validaSommeParziali(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
			}catch(GestionePagamentoException ex){
				esitoOperazioneInserimento.setEsito(Boolean.FALSE);
				esitoOperazioneInserimento.setParams(new String[]{ex.getMessage()});
				
				esitoOperazioneInserimento.setMessage(ex.getMessage());


				return esitoOperazioneInserimento;
			}
		}
		try{
			verificaDataPagamentoCheckNonBloccante(pagamento, documentoDTO
				.getDataEmissione(), idProgetto, documentoVO.getIdTipoDocDiSpesa());
		}catch(GestionePagamentoException ex){
			esitoOperazioneInserimento.setEsito(Boolean.FALSE);
			
			esitoOperazioneInserimento.setParams(new String[]{ex.getMessage()});
			
			esitoOperazioneInserimento.setMessage(ex.getMessage());

			return esitoOperazioneInserimento;
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new GestionePagamentoException("");
		}

		Long idNuovoPagamentoAssociato = pbandipagamentiDAO
				.inserisciPagamento(
						pagamento.getIdModalitaPagamento(),
						new java.sql.Date(pagamento.getDtPagamento().getTime()),
						pagamento.getImportoPagamento(),
						idUtente,
						STATO_PAGAMENTO_INSERITO,
						esitoScenarioBando.getDataPagamento(),
						pagamento.getIdCausalePagamento(),
						pagamento.getRifPagamento(), null);

		pbandipagamentiDAO.inserisciPagamentoRDocSpesa(
				idNuovoPagamentoAssociato, idDocumentoDiSpesa, 
				idUtente);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			if (arrayPagamentiVociDiSpesa != null) {
				for (PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO : arrayPagamentiVociDiSpesa) {
					if (pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente()!=null && 
							pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente().doubleValue() > 0.0D) {
						PbandiRPagQuotParteDocSpVO pagQuotParteDocSpVO = trasformaInVO(
								idUtente, idNuovoPagamentoAssociato,
								pagamentoVoceDiSpesaDTO);
						try {
							genericDAO.insert(pagQuotParteDocSpVO);
						} catch (Exception e) {
							throw new UnrecoverableException(
									"Impossibile inserire relazioni tra pagamenti e quote parte.",
									e);
						}
					}
				}
			}
		}

		esitoOperazioneInserimento.setEsito(Boolean.TRUE);
		esitoOperazioneInserimento
				.setParams(new String[] { idNuovoPagamentoAssociato.toString() });
		esitoOperazioneInserimento
				.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		return esitoOperazioneInserimento;
	}
	public EsitoOperazioneInserimentoPagamento inserisciPagamentoBR62(
			Long idUtente,
			String identitaDigitale,
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento,
			Long idBando,
			Long idDocumentoDiSpesa,
			Long idProgetto

	) throws GestionePagamentoException, FormalParameterException,
			UnrecoverableException {

		logger.begin();


		EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);
		String[] nameParameter = { "PagamentoDTO", "idDocumentoDiSpesa",
				"idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento,
				idDocumentoDiSpesa, idProgetto);
		logger.shallowDump(pagamento,"info");

		EsitoOperazioneInserimentoPagamento esitoOperazioneInserimento = new EsitoOperazioneInserimentoPagamento();
		esitoOperazioneInserimento.setEsito(Boolean.FALSE);

		DocumentoDiSpesaDTO documentoDTO = new DocumentoDiSpesaDTO();
		PDocumentoDiSpesaVO documentoVO = pbandipagamentiDAO
				.getInfoPagamentoDocumentoDiSpesa(idDocumentoDiSpesa);
		BeanUtil.valueCopy(documentoVO, documentoDTO);

		List<PagamentoQuotePartiVO> listaPagamenti = pbandipagamentiDAO.findPagamentiAssociati(idDocumentoDiSpesa, idProgetto);
		List<PagamentoQuotePartiVO> listaPagamentiConFlag = listaPagamenti.stream().filter(p -> p.getFlagPagamento() != null && p.getFlagPagamento().equals("S")).collect(Collectors.toList());

		if(!listaPagamentiConFlag.isEmpty()){
			esitoOperazioneInserimento.setMessage("Errore: è già presente un pagamento associato con quietanza al momento non disponibile");
			return esitoOperazioneInserimento;
		}

		if(pagamento.getFlagPagamento() == null || !pagamento.getFlagPagamento().equals("S")){
			return inserisciPagamento(idUtente, identitaDigitale, arrayPagamentiVociDiSpesa, pagamento, idBando, idDocumentoDiSpesa, idProgetto);
		}

		if(!listaPagamenti.isEmpty()){
			esitoOperazioneInserimento.setMessage("Errore: è già presente un pagamento associato. Impossibile aggiungere un pagamento con quietanza al momento non disponibile");
			return esitoOperazioneInserimento;
		}

		Long idNuovoPagamentoAssociato = pbandipagamentiDAO
				.inserisciPagamento(
						pagamento.getIdModalitaPagamento(),
						new java.sql.Date(pagamento.getDtPagamento().getTime()),
						pagamento.getImportoPagamento(),
						idUtente,
						STATO_PAGAMENTO_INSERITO,
						esitoScenarioBando.getDataPagamento(),
						pagamento.getIdCausalePagamento(),
						pagamento.getRifPagamento(),
						"S");

		pbandipagamentiDAO.inserisciPagamentoRDocSpesa(
				idNuovoPagamentoAssociato, idDocumentoDiSpesa,
				idUtente);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			if (arrayPagamentiVociDiSpesa != null) {
				for (PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO : arrayPagamentiVociDiSpesa) {
					if (pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente()!=null &&
              pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente() > 0.0D) {
						PbandiRPagQuotParteDocSpVO pagQuotParteDocSpVO = trasformaInVO(
								idUtente, idNuovoPagamentoAssociato,
								pagamentoVoceDiSpesaDTO);
						try {
							genericDAO.insert(pagQuotParteDocSpVO);
						} catch (Exception e) {
							throw new UnrecoverableException(
									"Impossibile inserire relazioni tra pagamenti e quote parte.",
									e);
						}
					}
				}
			}
		}

		esitoOperazioneInserimento.setEsito(Boolean.TRUE);
		esitoOperazioneInserimento
				.setParams(new String[] { idNuovoPagamentoAssociato.toString() });
		esitoOperazioneInserimento
				.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		return esitoOperazioneInserimento;
	}
	private PbandiRPagQuotParteDocSpVO trasformaInVO(Long idUtente,
			Long idNuovoPagamentoAssociato,
			PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO) {
		/*
		 * FLAG_MONIT(oraggio) di default a false (quindi non viene inviato al
		 * monitoraggio)
		 */

		Double rendicontato = pagamentoVoceDiSpesaDTO.getImportoRendicontato();
		Double quietanzato = pagamentoVoceDiSpesaDTO.getTotaleAltriPagamenti();
		Double nuovoImporto = pagamentoVoceDiSpesaDTO
				.getImportoVoceDiSpesaCorrente();

		if ((rendicontato - quietanzato) == 0) {
			nuovoImporto = rendicontato;
		}

		PbandiRPagQuotParteDocSpVO pagQuotParteDocSpVO = getBeanUtil()
				.transform(pagamentoVoceDiSpesaDTO,
						PbandiRPagQuotParteDocSpVO.class);

		pagQuotParteDocSpVO.setIdPagamento(new BigDecimal(
				idNuovoPagamentoAssociato));
		pagQuotParteDocSpVO.setImportoQuietanzato(new BigDecimal(nuovoImporto));
		pagQuotParteDocSpVO.setIdUtenteIns(new BigDecimal(idUtente));
		return pagQuotParteDocSpVO;
	}

	private Double getSommaImportiNoteDiCredito(
			Long idDocumentoDiSpesa) {
		Double importoNoteDiCredito = 0.0;
		List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> noteDiCredito = pbandiDocumentiDiSpesaDAO
				.findNoteDiCreditoAssociateFattura(idDocumentoDiSpesa);
		if (!isEmpty(noteDiCredito)) {
			for (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO notaDiCredito : noteDiCredito) {
				if (!isNull(notaDiCredito.getImportoTotaleDocumento()))
					importoNoteDiCredito += notaDiCredito
							.getImportoTotaleDocumento().doubleValue();
			}
		}
		return importoNoteDiCredito;
	}

	/**
	 * 
	 * @param idUtente
	 * @param identitaDigitale
	 * @param arrayPagamentiVociDiSpesa
	 * @param pagamento
	 * @param idBando
	 * @param idDocumentoDiSpesa
	 * @throws GestionePagamentoException
	 * @throws UnrecoverableException
	 */
	public EsitoOperazioneModificaPagamento modificaPagamento(

			Long idUtente,

			String identitaDigitale,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento,

			Long idBando,

			Long idDocumentoDiSpesa,

			Long idProgetto) throws GestionePagamentoException,
			FormalParameterException, UnrecoverableException {
		
		String[] nameParameter = { "PagamentoDTO", "idDocumentoDiSpesa" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento,
				idDocumentoDiSpesa);
		
		logger.shallowDump(pagamento,"info");
		EsitoOperazioneModificaPagamento esitoOperazioneModifica = new EsitoOperazioneModificaPagamento();
		esitoOperazioneModifica.setEsito(Boolean.FALSE);
		List<String> messaggi = new ArrayList<String>();

		/**
		 * Simulo il controllo sull'idBando..definisce quattro possibili scenari
		 */
		EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);

	

		/**
		 * 1� controllo Pagamento in stato diverso da INSERITO e RESPINTO
		 * M8(Errore: non � possibile modificare un pagamento in stato
		 * <stato_pagamento>)
		 */
		// AGGIUNTO STATO RESPINTO IL 27 LUGLIO
		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento.
		if (!pagamento
				.getDescBreveStatoValidazioneSpesa()
				.equals(
						PagamentiManager
								.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_INSERITO))
				&& !pagamento
						.getDescBreveStatoValidazioneSpesa()
						.equals(
								PagamentiManager
										.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_RESPINTO))

		)
		
		{
			gestisciEsitoModificaErrato(pagamento, esitoOperazioneModifica,
					messaggi);
			return esitoOperazioneModifica;
		}
		*/

		/**
		 * Recupero i dati relativi al Documento di spesa:
		 * dataEmissioneDocumento somma dei pagamenti associati al documento di
		 * spesa importo totale del documento di spesa al netto delle note di
		 * credito se il documento � di tipo 4
		 * 
		 */
		DocumentoDiSpesaDTO documentoDTO = new DocumentoDiSpesaDTO();
		PDocumentoDiSpesaVO documentoVO = pbandipagamentiDAO
				.getInfoPagamentoDocumentoDiSpesa(idDocumentoDiSpesa);
		BeanUtil.valueCopy(documentoVO, documentoDTO);
 

		PbandiTPagamentoVO pagamentoVO = new PbandiTPagamentoVO();
		pagamentoVO.setIdPagamento(new BigDecimal(pagamento.getIdPagamento()));
		PbandiTPagamentoVO[] pagamentiPrimaModificaVO = genericDAO
				.findWhere(pagamentoVO);
		// JIRA 399 del 16 luglio
		Double sommaImportiNoteDiCredito = getSommaImportiNoteDiCredito(idDocumentoDiSpesa);

		// JIRA 418 del 16 luglio
		// se cedolino il terzo parmaetro non � pi� l'importo totale netto ma
		// il + grande tra importo totale netto e rendicontabile tra + progetti
		// (somma dei vari PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE)
		Double importoTotaleNetto = documentoDTO.getImportoTotaleNetto() == null ? 0.0
				: documentoDTO.getImportoTotaleNetto();
		try {
			if (documentoDiSpesaManager
					.isCedolinoOAutodichiarazioneSoci(documentoVO
							.getIdTipoDocDiSpesa())) {
				importoTotaleNetto = calcolaImportoTotaleNetto(
						idDocumentoDiSpesa, importoTotaleNetto);
			}
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new GestionePagamentoException("");
		}

		Double sommaPagamenti = documentoDTO.getSommaPagamenti();

		Double importoPagamento = 0D;
		if (!isEmpty(pagamentiPrimaModificaVO)
				&& !isNull(pagamentiPrimaModificaVO[0].getImportoPagamento()))
			importoPagamento = pagamentiPrimaModificaVO[0]
					.getImportoPagamento().doubleValue();

		Double sommaPagamentiDocumentoDiSpesa = 0D;
		if (!isNull(sommaPagamenti) && !isNull(importoPagamento))
			sommaPagamentiDocumentoDiSpesa = sommaPagamenti.doubleValue()
					- importoPagamento.doubleValue();

		// }L{ PBANDI-328
		try {
			validaSommeTotali(pagamento.getImportoPagamento(),
					sommaPagamentiDocumentoDiSpesa, importoTotaleNetto,
					sommaImportiNoteDiCredito);
		} catch (GestionePagamentoException e) {
			if (e.getMessage().equals(
					ERRORE_VOCE_DI_SPESA_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneModifica.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamentiDocumentoDiSpesa;
			//	logger.debug(e.getMessage() + " {0} = " + residuo.toString());
				esitoOperazioneModifica.setParams(new String[] {
						NumberUtil.getStringValueItalianFormat(residuo) 
						 });
				esitoOperazioneModifica.setMessage(e.getMessage());
				return esitoOperazioneModifica;
			} else if (e.getMessage().equals(
					ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneModifica.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamentiDocumentoDiSpesa;
				//logger.debug(e.getMessage() + " {0} = " + residuo.toString());
				esitoOperazioneModifica.setParams(new String[] { NumberUtil.getStringValueItalianFormat(residuo)});
				esitoOperazioneModifica.setMessage(e.getMessage());
				return esitoOperazioneModifica;
			} else {
				throw e;
			}
		}

		/*
		 * validaSommeTotali(pagamento.getImportoPagamento(),
		 * documentoDTO.getSommaPagamenti() -
		 * pagamentiPrimaModificaVO[0].getImportoPagamento()
		 * .longValue(),importoTotaleNetto ,sommaImportiNoteDiCredito);
		 */
		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {

			// ValidatorInput.verifyArrayNotEmpty("arrayPagamentiVociDiSpesa",
			// arrayPagamentiVociDiSpesa);
			try{
				validaCoerenzaImporti(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
				validaSommeParziali(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
			}catch(GestionePagamentoException ex){
				esitoOperazioneModifica.setEsito(Boolean.FALSE);
				
				esitoOperazioneModifica.setParams(new String[]{ex.getMessage()});
				
				esitoOperazioneModifica.setMessage(ex.getMessage());

				return esitoOperazioneModifica;
			}
		}

		try{
			verificaDataPagamentoCheckNonBloccante(pagamento, documentoDTO
				.getDataEmissione(), idProgetto, documentoVO.getIdTipoDocDiSpesa());
		
		}catch(GestionePagamentoException ex){
			esitoOperazioneModifica.setEsito(Boolean.FALSE);
			
			esitoOperazioneModifica.setParams(new String[]{ex.getMessage()});
			
			esitoOperazioneModifica.setMessage(ex.getMessage());

			return esitoOperazioneModifica;
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new GestionePagamentoException("");
		}

		pbandipagamentiDAO.modificaPagamento(pagamento.getIdPagamento(),
				pagamento.getIdModalitaPagamento(), new java.sql.Date(pagamento
						.getDtPagamento().getTime()), pagamento
						.getImportoPagamento(), idUtente,
						esitoScenarioBando.getDataPagamento(),
						pagamento.getIdCausalePagamento(),
						pagamento.getRifPagamento(), null);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			modificaPagamentiVociDiSpesa(pagamento.getIdPagamento(),
					arrayPagamentiVociDiSpesa, idUtente);
		}

		esitoOperazioneModifica.setEsito(Boolean.TRUE);

		/*
		 * if (messaggi != null && !messaggi.isEmpty() &&
		 * messaggi.contains(ERRORE_VALIDAZIONE_DATA_VALUTA_PAGAMENTO)) // TODO
		 * avviso dataPagamento < dataDichiarazione esitoOperazioneModifica
		 * .setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO); else
		 */
		esitoOperazioneModifica.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		return esitoOperazioneModifica;
	}

	public EsitoOperazioneModificaPagamento modificaPagamentoBR62(Long idUtente, String identitaDigitale, it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa, Long idProgetto) throws GestionePagamentoException, FormalParameterException, UnrecoverableException {
		String[] nameParameter = { "PagamentoDTO", "idDocumentoDiSpesa" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento, idDocumentoDiSpesa);
		logger.shallowDump(pagamento, "info");

		EsitoOperazioneModificaPagamento esitoOperazioneModifica = new EsitoOperazioneModificaPagamento();
		esitoOperazioneModifica.setEsito(Boolean.FALSE);

    EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);
		DocumentoDiSpesaDTO documentoDTO = new DocumentoDiSpesaDTO();
		PDocumentoDiSpesaVO documentoVO = pbandipagamentiDAO.getInfoPagamentoDocumentoDiSpesa(idDocumentoDiSpesa);
		BeanUtil.valueCopy(documentoVO, documentoDTO);

		List<PagamentoQuotePartiVO> listaPagamenti = pbandipagamentiDAO.findPagamentiAssociati(idDocumentoDiSpesa, idProgetto);

		if(listaPagamenti.size() > 1 && pagamento.getFlagPagamento() != null){
			esitoOperazioneModifica.setMessage("Errore: è già presente un pagamento associato con quietanza al momento non disponibile");
			return esitoOperazioneModifica;
		}

		if(pagamento.getFlagPagamento() == null || !pagamento.getFlagPagamento().equals("S")){
			return modificaPagamento(idUtente, identitaDigitale, arrayPagamentiVociDiSpesa, pagamento, idBando, idDocumentoDiSpesa, idProgetto);
		}

		pbandipagamentiDAO.modificaPagamento(pagamento.getIdPagamento(), pagamento.getIdModalitaPagamento(),
				new java.sql.Date(pagamento.getDtPagamento().getTime()), pagamento.getImportoPagamento(),
				idUtente, esitoScenarioBando.getDataPagamento(), pagamento.getIdCausalePagamento(), pagamento.getRifPagamento(), "S");

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			modificaPagamentiVociDiSpesa(pagamento.getIdPagamento(), arrayPagamentiVociDiSpesa, idUtente);
		}

		esitoOperazioneModifica.setEsito(Boolean.TRUE);
		esitoOperazioneModifica.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		return esitoOperazioneModifica;
	}
	private void modificaPagamentiVociDiSpesa(Long idPagamento,
			PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, Long idUtente)
			throws UnrecoverableException {
		if (arrayPagamentiVociDiSpesa != null) {

			for (PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO : arrayPagamentiVociDiSpesa) {
				PbandiRPagQuotParteDocSpVO pagQuotParteDocSpVO = trasformaInVO(
						idUtente, idPagamento, pagamentoVoceDiSpesaDTO);
				PbandiRPagQuotParteDocSpVO filter = new PbandiRPagQuotParteDocSpVO();
				filter.setIdPagamento(pagQuotParteDocSpVO.getIdPagamento());
				filter.setIdQuotaParteDocSpesa(pagQuotParteDocSpVO
						.getIdQuotaParteDocSpesa());
				/***
				 * 1� caso: se la voce di spesa � gi� associata al pagamento e
				 * il nuovo importo � > 0 eseguo l'update
				 */
				/**
				 * 2� caso: se la voce di spesa � gi� associata al pagamento e
				 * il nuovo importo � == 0 cancello la relazione tra pagamento e
				 * voce di spesa
				 */
				/**
				 * 3� caso: se la voce di spesa non � associata al pagamento e
				 * il nuovo importo � > 0 eseguo l'insert altrimenti non faccio
				 * nulla
				 */
				try {
					if (pbandipagamentiDAO.esistePagamentoPerVoceDiSpesa(
							idPagamento, pagamentoVoceDiSpesaDTO
									.getIdQuotaParteDocSpesa())) {
						// Modifica per jira 2148
							if (pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente()!=null && 
									pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente().doubleValue() > 0.0D) {
							
							pagQuotParteDocSpVO.setIdUtenteAgg(new BigDecimal(idUtente));
							genericDAO.update(filter, pagQuotParteDocSpVO);
						} else {
							genericDAO
									.deleteWhere(new FilterCondition<PbandiRPagQuotParteDocSpVO>(
											filter));
						}
					} else {
						if (pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente()!=null && 
								pagamentoVoceDiSpesaDTO.getImportoVoceDiSpesaCorrente().doubleValue() > 0.0D) {
							pagQuotParteDocSpVO.setIdUtenteIns(new BigDecimal(idUtente));
							genericDAO.insert(pagQuotParteDocSpVO);
						}
					}
				} catch (Exception e) {
					throw new UnrecoverableException(
							"Impossibile inserire relazioni tra pagamenti e quote parte.",
							e);
				}
			}
		}
	}

	private Double calcolaImportoTotaleNetto(Long idDocumentoDiSpesa,
			Double importoTotaleNetto) throws GestionePagamentoException {
		PbandiRDocSpesaProgettoVO docSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
		try {
			docSpesaProgettoVO.setIdDocumentoDiSpesa(NumberUtil
					.toBigDecimal(idDocumentoDiSpesa));
			PbandiRDocSpesaProgettoVO[] ris = genericDAO
					.findWhere(docSpesaProgettoVO);
			if (!isEmpty(ris)) {
				Double sommaImportoRendicontazione = 0.0;
				for (PbandiRDocSpesaProgettoVO vo : ris) {
					if (!isNull(vo.getImportoRendicontazione()))
						sommaImportoRendicontazione += NumberUtil.toDouble(vo
								.getImportoRendicontazione());
				}
				if (sommaImportoRendicontazione.doubleValue() > importoTotaleNetto
						.doubleValue())
					importoTotaleNetto = sommaImportoRendicontazione;
			}
		} catch (Exception e) {
			logger.error(
					"Errore nella ricerca di importi su PBANDI_R_DOC_SPESA_PROGETTO con "
							+ docSpesaProgettoVO.getIdDocumentoDiSpesa(), e);
			throw new GestionePagamentoException("");
		}
		return importoTotaleNetto;
	}


	/*
	 * FIX PBANDI-2314: Non esiste piu' lo stato del pagamento.
	private void gestisciEsitoModificaErrato(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento,
			EsitoOperazioneModificaPagamento esitoOperazioneModifica,
			List<String> messaggi) {
		esitoOperazioneModifica.setEsito(Boolean.FALSE);
		PbandiTPagamentoVO pagamentoVO = new PbandiTPagamentoVO();
		pagamentoVO.setIdPagamento(NumberUtil.toBigDecimal(pagamento
				.getIdPagamento()));
		PbandiTPagamentoVO vo[] = genericDAO.findWhere(pagamentoVO);
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_VALIDAZ_SPESA, NumberUtil
						.toLong(vo[0].getIdStatoValidazioneSpesa()));
		messaggi.add(decodifica.getDescrizione());
		esitoOperazioneModifica.setParams(new String[] { decodifica
				.getDescrizione() });
		esitoOperazioneModifica.setMessage(ERRORE_STATO_VALIDAZIONE_PAGAMENTO);
	}

	
	private void gestisciEsitoCancellazioneErrato(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento,
			EsitoOperazioneCancellazionePagamento esitoOperazioneCancellazione,
			List<String> messaggi) {
		esitoOperazioneCancellazione.setEsito(Boolean.FALSE);
		PbandiTPagamentoVO pagamentoVO = new PbandiTPagamentoVO();
		pagamentoVO.setIdPagamento(NumberUtil.toBigDecimal(pagamento
				.getIdPagamento()));
		PbandiTPagamentoVO vo[] = genericDAO.findWhere(pagamentoVO);
		DecodificaDTO decodifica = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_VALIDAZ_SPESA, NumberUtil
						.toLong(vo[0].getIdStatoValidazioneSpesa()));
		messaggi.add(decodifica.getDescrizione());
		esitoOperazioneCancellazione.setParams(new String[] { decodifica
				.getDescrizione() });
		esitoOperazioneCancellazione
				.setMessage(ERRORE_STATO_VALIDAZIONE_PAGAMENTO);
	}
	*/

	/**
	 * Ripartisce l'importo totale del pagamento inserito dall'utente sulle voci
	 * di spesa del documento selezionato
	 * 
	 * @param importoTotalePagamento
	 * @param arrayPagamentiVociDiSpesa
	 * @param utente
	 * @param idBando
	 * @return
	 * @throws GestionePagamentoException
	 */
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] ripartisciPagamentoOnVociDiSpesa(

			Long idUtente,

			String identitaDigitale,

			Double importoTotalePagamento,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,

			Long idBando

	) throws GestionePagamentoException, FormalParameterException {

		EsitoScenarioBando esitoScenarioBando = initScenarioBando(idBando);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			String nameParameter[] = { "importoTotalePagamento",
					"arrayPagamentiVociDiSpesa", "idBando" };
			ValidatorInput.verifyNullValue(nameParameter,
					importoTotalePagamento, arrayPagamentiVociDiSpesa, idBando);

			if (isEmpty(arrayPagamentiVociDiSpesa))
				throw new GestionePagamentoException(
						ERRORE_CAMPO_NON_VALORIZZATO);

			Double importoResiduo = importoTotalePagamento;
			logger.debug("importoTotalePagamento: " + importoTotalePagamento);

			for (PagamentoVoceDiSpesaDTO voceDiSpesa : arrayPagamentiVociDiSpesa) {

				Double importoRendicontato = voceDiSpesa
						.getImportoRendicontato();

				// Aggiungo le note di credito al calcolo (PBANDI-802)
				ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO ndcVO = new ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO();
				ndcVO.setIdQuotaParteDocSpesa(new BigDecimal(voceDiSpesa
						.getIdQuotaParteDocSpesa()));
				ndcVO.setIdRigoContoEconomico(new BigDecimal(voceDiSpesa
						.getIdRigoContoEconomico()));
				List<ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO> ndcVOs = genericDAO
						.findListWhere(ndcVO);

				Double importoNoteDiCreditoAssociate = new Double(0);

				for (ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO ndc : ndcVOs) {
					importoNoteDiCreditoAssociate += NumberUtil.toDouble(ndc
							.getTotaleNoteDiCredito());
				}

				/**
				 * Come residuo rendicontabile vale la differenza tra
				 * rendicontato e totaleAltriPagamenti
				 */
				Double importoTotaleAltriPagamenti = voceDiSpesa
						.getTotaleAltriPagamenti();
				Double diffRendicontatoQuietanzato = importoRendicontato
						- importoTotaleAltriPagamenti;
				// ...al netto delle note di credito associate (PBANDI-802)
				diffRendicontatoQuietanzato = diffRendicontatoQuietanzato
						- importoNoteDiCreditoAssociate;

				logger.debug("diffRendicontatoQuietanzato: "
						+ diffRendicontatoQuietanzato);

				if (importoRendicontato > importoTotaleAltriPagamenti) {

					if (importoResiduo > 0) {

						if (diffRendicontatoQuietanzato > 0) {
							if (importoResiduo > diffRendicontatoQuietanzato) {
								voceDiSpesa
										.setImportoVoceDiSpesaCorrente(diffRendicontatoQuietanzato);
							} else {
								voceDiSpesa
										.setImportoVoceDiSpesaCorrente(importoResiduo);
								// break;
							}

							importoResiduo = importoResiduo
									- diffRendicontatoQuietanzato;

							if (importoResiduo < 0d) {
								importoResiduo = 0d;
							}
						} else {
							/**
							 * Importo completamente pagato, imposto il nuovo
							 * importo con l'importo rendicontato altrimenti sul
							 * db salverebbe 0
							 */
							logger.debug("Importo completamente pagato!");
							continue;
						}
					} else {
						/**
						 * Se l'importo l'importo residuo del pagamento � == 0
						 * se sulle successive voci di spesa l'importo nuovo
						 * pagamento a 0
						 */
						if (voceDiSpesa.getImportoVoceDiSpesaCorrente() > 0d) {
							voceDiSpesa.setImportoVoceDiSpesaCorrente(0d);
						}
						// break;
					}
				}
				// else non faccio niente, vuol dire che ho una voce che ha il
				// rendicontato completamente quietanzato
				// in modifica sar� x una voce non associata al pagamento, in
				// inserimento sar� per tutte le voci associate e non al
				// pagamento
			}
		}

		return arrayPagamentiVociDiSpesa;

	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] ripartisciPagamentoOnVociDiSpesaPerValidazione(

			Long idUtente,

			String identitaDigitale,

			Double importoTotalePagamento,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,

			Long idBando

	) throws GestionePagamentoException, FormalParameterException {

		EsitoScenarioBando esitoScenarioBando = initScenarioBandoPerValidazione(idBando);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			String nameParameter[] = { "importoTotalePagamento",
					"arrayPagamentiVociDiSpesa", "idBando" };
			ValidatorInput.verifyNullValue(nameParameter,
					importoTotalePagamento, arrayPagamentiVociDiSpesa, idBando);

			if (isEmpty(arrayPagamentiVociDiSpesa))
				throw new GestionePagamentoException(
						ERRORE_CAMPO_NON_VALORIZZATO);

			Double importoResiduo = importoTotalePagamento;
			logger.debug("importoTotalePagamento: " + importoTotalePagamento);

			for (PagamentoVoceDiSpesaDTO voceDiSpesa : arrayPagamentiVociDiSpesa) {

				Double importoRendicontato = voceDiSpesa
						.getImportoRendicontato();

				// Aggiungo le note di credito al calcolo (PBANDI-802)
				ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO ndcVO = new ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO();
				ndcVO.setIdQuotaParteDocSpesa(new BigDecimal(voceDiSpesa
						.getIdQuotaParteDocSpesa()));
				ndcVO.setIdRigoContoEconomico(new BigDecimal(voceDiSpesa
						.getIdRigoContoEconomico()));
				List<ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO> ndcVOs = genericDAO
						.findListWhere(ndcVO);

				Double importoNoteDiCreditoAssociate = new Double(0);

				for (ImportoTotaleNoteDiCreditoPerRigoContoEconomicoVO ndc : ndcVOs) {
					importoNoteDiCreditoAssociate += NumberUtil.toDouble(ndc
							.getTotaleNoteDiCredito());
				}

				/**
				 * Come residuo rendicontabile vale la differenza tra
				 * rendicontato e totaleAltriPagamenti
				 */
				Double importoTotaleAltriPagamenti = voceDiSpesa
						.getTotaleAltriPagamenti();
				Double diffRendicontatoQuietanzato = importoRendicontato
						- importoTotaleAltriPagamenti;
				// ...al netto delle note di credito associate (PBANDI-802)
				diffRendicontatoQuietanzato = diffRendicontatoQuietanzato
						- importoNoteDiCreditoAssociate;

				logger.debug("diffRendicontatoQuietanzato: "
						+ diffRendicontatoQuietanzato);

				if (importoRendicontato > importoTotaleAltriPagamenti) {

					if (importoResiduo > 0) {

						if (diffRendicontatoQuietanzato > 0) {
							if (importoResiduo > diffRendicontatoQuietanzato) {
								voceDiSpesa
										.setImportoVoceDiSpesaCorrente(diffRendicontatoQuietanzato);
							} else {
								voceDiSpesa
										.setImportoVoceDiSpesaCorrente(importoResiduo);
								// break;
							}

							importoResiduo = importoResiduo
									- diffRendicontatoQuietanzato;

							if (importoResiduo < 0d) {
								importoResiduo = 0d;
							}
						} else {
							/**
							 * Importo completamente pagato, imposto il nuovo
							 * importo con l'importo rendicontato altrimenti sul
							 * db salverebbe 0
							 */
							logger.debug("Importo completamente pagato!");
							continue;
						}
					} else {
						/**
						 * Se l'importo l'importo residuo del pagamento � == 0
						 * se sulle successive voci di spesa l'importo nuovo
						 * pagamento a 0
						 */
						if (voceDiSpesa.getImportoVoceDiSpesaCorrente() > 0d) {
							voceDiSpesa.setImportoVoceDiSpesaCorrente(0d);
						}
						// break;
					}
				}
				// else non faccio niente, vuol dire che ho una voce che ha il
				// rendicontato completamente quietanzato
				// in modifica sar� x una voce non associata al pagamento, in
				// inserimento sar� per tutte le voci associate e non al
				// pagamento
			}
		}

		return arrayPagamentiVociDiSpesa;

	}

	public EsitoOperazioneCancellazionePagamento eliminaPagamento(

	Long idUtente,

	String identitaDigitale,

	it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento,

	Long idBando

	) throws GestionePagamentoException, FormalParameterException {
		EsitoOperazioneCancellazionePagamento esitoOperazioneElimina = new EsitoOperazioneCancellazionePagamento();
		List<String> messaggi = new ArrayList<String>();
		/**
		 * Verifico il nullable dei parametri di input, per quelli obbligatori
		 * la gestione della notifica del messaggio corretto la lascio al client
		 */
		String nameParameter[] = { "pagamento" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento);

		if (isNull(pagamento.getIdPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPO_NON_VALORIZZATO);

		
		try {
			documentoManager.disassociateArchivioFileAssociatedPagamento(idUtente,pagamento
					.getIdPagamento());
		} catch (Exception e) {
			logger.warn("error in disassociateArchivioFileAssociatedPagamento "+e.getMessage());
			throw new GestionePagamentoException("");
		}
		
		
	    
		pbandipagamentiDAO.cancellaRPagamentoDocDiSpesa(pagamento.getIdPagamento());

		pbandipagamentiDAO.cancellaRPagamentoDichDiSpesa(pagamento.getIdPagamento());

		// if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa())
		// }L{ PBANDI-512 : la ripartizione potrebbe esserci in ogni caso
		pbandipagamentiDAO.cancellaPagamentiVociDiSpesa(pagamento.getIdPagamento());

		pbandipagamentiDAO.cancellaPagamento(pagamento.getIdPagamento());

		messaggi.add("Cancellazione avvenuta con successo");
		esitoOperazioneElimina.setMessage("Cancellazione avvenuta con successo");
		esitoOperazioneElimina.setEsito(Boolean.TRUE);
		return esitoOperazioneElimina;

	}

	/*
	 * 
	 * @throws FormalParameterException
	 */
	private EsitoScenarioBando initScenarioBando(

	Long idBandoLinea

	) throws FormalParameterException {

		/*
		 * BR02 per le voci di spesa BR04 per data valuta BR05 per data
		 * effettivo pagamento
		 */

		EsitoScenarioBando e = new EsitoScenarioBando();

		if (regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea,
				BR05_VISUALIZZA_DATA_EFFETTIVA)) {
			e.setDataPagamento(Constants.DATA_EFFETTIVO_PAGAMENTO);
		}

		if (regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea,
				BR04_VISUALIZZA_DATA_VALUTA)) {
			e.setDataPagamento(Constants.DATA_VALUTA_PAGAMENTO);
		}

		if (regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea,
				BR02_VISUALIZZA_VOCI_DI_SPESA)) {
			e.setVisualizzaImportiVociDiSpesa(true);
		} else {
			e.setVisualizzaImportiVociDiSpesa(false);
		}

		return e;
	}

	private EsitoScenarioBando initScenarioBandoPerValidazione(

	Long idBandoLinea

	) throws FormalParameterException {
		/*
		 * Per la validazione non si deve eseguire il test per la regola BR02
		 * perche' dovra' sempre essere possibile ripartire i pagamenti tra le
		 * voci di spesa
		 */

		/*
		 * BR02 per le voci di spesa BR04 per data valuta BR05 per data
		 * effettivo pagamento
		 */

		EsitoScenarioBando e = new EsitoScenarioBando();

		if (regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea,
				BR05_VISUALIZZA_DATA_EFFETTIVA)) {
			e.setDataPagamento(Constants.DATA_EFFETTIVO_PAGAMENTO);
		}

		if (regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea,
				BR04_VISUALIZZA_DATA_VALUTA)) {
			e.setDataPagamento(Constants.DATA_VALUTA_PAGAMENTO);
		}

		e.setVisualizzaImportiVociDiSpesa(true);

		return e;
	}

	/**
	 * TODO: aggiungere la logica necessaria per recuperare la tipologia di
	 * bando e quindi lo scenario ad esso legato. In questo momento il legame
	 * tipo di bando e cosa visualizzare o meno � definito nel file excel
	 * 
	 * @throws FormalParameterException
	 */
	private boolean verificaTipologiaDocumentoDiSpesa(

	Long idDocumentoDiSpesa

	) throws GestionePagamentoException {

		boolean esito = false;

		String tipologiaDocumentoDiSpesa = pbandipagamentiDAO
				.getTipologiaDocumentoDiSpesa(idDocumentoDiSpesa);

		/**
		 * TODO: sostituire con la FIX PBANDI-401 appena viene risolta
		 * 
		 * if (tipologiaDocumentoDiSpesa .equals(DocumentoDiSpesaManager
		 * .getCodiceTipoDocumentoDiSpesa (GestioneDocumentiDiSpesaSrv.
		 * TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO )) ||
		 * tipologiaDocumentoDiSpesa .equals(DocumentoDiSpesaManager
		 * .getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.
		 * TIPO_DOCUMENTO_DI_SPESA_SPESE_FORFETTARIE))) { esito = true; }
		 */

		/**
		 * FIX: PBANDI-401 Le spese forfettarie possono avere pagamenti
		 */
		if (tipologiaDocumentoDiSpesa
				.equals(DocumentoDiSpesaManager
						.getCodiceTipoDocumentoDiSpesa(GestioneDocumentiDiSpesaSrv.TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO))) {
			esito = true;
		}

		return esito;
	}

	private boolean verificaAssociazioneVociDiSpesaByIdDocumentoIdProgetto(
			Long idDocumentoDiSpesa, Long idProgetto)
			throws GestionePagamentoException {
		return pbandipagamentiDAO.esistonoVociDiSpesaByDocumentoProgetto(
				idDocumentoDiSpesa, idProgetto);
	}

	/**
	 * 
	 * @param pagamento
	 * @param dataEmissioneDocumentoDiSpesa
	 * @throws GestionePagamentoException
	 * @throws ManagerException 
	 */
	private void verificaDatiPagamento(String dataPagamento,
			PagamentoDTO pagamento, Date dataEmissioneDocumentoDiSpesa,
			List<String> messaggi, Long idProgetto, Long idTipoDocumento)
			throws GestionePagamentoException, ManagerException {

		if (isNull(pagamento.getDtPagamento())
				&& isNull(pagamento.getIdModalitaPagamento())
				&& isNull(pagamento.getImportoPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPI_OBBLIGATORI);

		/**
		 * La data del pagamento non pu� essere precedente a quella del
		 * documento di spesa
		 */
		if (isNull(pagamento.getDtPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPO_OBBLIGATORIO);
		String codiceErroreValidazioneData = "";
		
		

		if (dataPagamento.equals(Constants.DATA_EFFETTIVO_PAGAMENTO)) {
			codiceErroreValidazioneData = ERRORE_VALIDAZIONE_DATA_EFFETTIVO_PAGAMENTO;
		} else {
			codiceErroreValidazioneData = ERRORE_VALIDAZIONE_DATA_VALUTA_PAGAMENTO;
		}
		/*
		 * FIX PBandi-2349
		 * In caso di quote di ammortamento l'unico controllo
		 * per la data del pagamento e' dataPagamento<=dataOdierna
		 */
		if (!documentoDiSpesaManager.isQuotaAmmortamento(idTipoDocumento)) {
			if (pagamento.getDtPagamento().before(dataEmissioneDocumentoDiSpesa)) {
				messaggi.add(codiceErroreValidazioneData);
				
			}
			
			// L.P AGGIUNTO CONTROLLO CHE DATA PAGAMENTO non pu� essere minore di
			// DATA DOMANDA
			PbandiTDomandaVO pbandiTDomandaVO = null;
			
			pbandiTDomandaVO = progettoManager.getDomandaByProgetto(idProgetto);
			
			if (pagamento.getDtPagamento().before(
					pbandiTDomandaVO.getDtPresentazioneDomanda())) {
				throw new GestionePagamentoException(
						ERRORE_DATA_PAGAMENTO_INFERIORE_ALLA_DATA_DOMANDA);
			}
		}

		// ///////////

		// throw new GestionePagamentoException(codiceErroreValidazioneData);

		String dataOdierna = DateUtil.getData();
		if (pagamento.getDtPagamento().after(DateUtil.getDate(dataOdierna)))
			throw new GestionePagamentoException(
					ERRORE_DATA_PAGAMENTO_SUCCESSIVA_ALLA_DATA_ODIERNA);

		if (isNull(pagamento.getImportoPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPO_OBBLIGATORIO);

		if (isNull(pagamento.getIdModalitaPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPO_OBBLIGATORIO);

	}

	private void verificaDataPagamentoCheckNonBloccante(PagamentoDTO pagamento,
			Date dataEmissioneDocumentoDiSpesa, Long idProgetto, Long idTipoDocumento)
			throws GestionePagamentoException, FormalParameterException, ManagerException {
		
		
		if (isNull(pagamento.getDtPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPO_OBBLIGATORIO);
		
		
		
		
		/*
		 * VN: FIX PBandi-1834
		 * Se c'e' la regola BR30 ed esiste la data di costituzione dell'azienda, 
		 * la data del pagamento puo' essere antecedente alla data del documento di spesa
		 * ma non alla data di costituzione dell'azienda.
		 */
		BeneficiarioProgettoVO beneficiarioVO = new BeneficiarioProgettoVO();
		beneficiarioVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		beneficiarioVO = genericDAO.findSingleWhere(beneficiarioVO);
		
		BeneficiarioEnteGiuridicoVO enteGiuridicoSoggetto =  soggettoManager.findEnteGiuridicoBeneficiario(idProgetto, NumberUtil.toLong(beneficiarioVO.getIdSoggetto()));
		
		/*
		 * FIX PBANDI-2349
		 * In caso di quota di ammortamento l'unico controllo
		 * sulla data del pagamento resta quello relativo
		 * alla data odierna, cioe' dataPagamento<=dataOdierna 
		 */
		if (!documentoDiSpesaManager.isQuotaAmmortamento(idTipoDocumento)) {
			if (regolaManager.isRegolaApplicabileForProgetto(idProgetto,RegoleConstants.BR30_DATA_AMMISSIBILITA_PER_PROGETTTI)
					&& ( !ObjectUtil.isNull(enteGiuridicoSoggetto) && enteGiuridicoSoggetto.getDtCostituzione() != null)) {
				if (pagamento.getDtPagamento().before(enteGiuridicoSoggetto.getDtCostituzione())) {
					throw new GestionePagamentoException(ERRORE_DATA_PAGAMENTO_PREC_DATA_COSTITUZIONE_AZIENDA);
				}
			} else {
				// L.P AGGIUNTO CONTROLLO NN BLOCCANTE JIRA PBANDI-563
				PbandiTDomandaVO pbandiTDomandaVO = null;
				pbandiTDomandaVO = progettoManager.getDomandaByProgetto(idProgetto);
				
				// }L{ PBANDI-2320 questo controllo rimane bloccante da subito
				if (pagamento.getDtPagamento().before(
						pbandiTDomandaVO.getDtPresentazioneDomanda())) {
					throw new GestionePagamentoException(
							ERRORE_DATA_PAGAMENTO_INFERIORE_ALLA_DATA_DOMANDA);
				}
				
				if (pagamento.getDtPagamento().before(dataEmissioneDocumentoDiSpesa)
						&& pagamento.getDtPagamento().after(
								pbandiTDomandaVO.getDtPresentazioneDomanda())) {
					throw new GestionePagamentoException(
							ERRORE_DATA_PAGAMENTO_PREC_DATA_SPESA_AND_SUCC_DATA_DOMANDA);
				}
			}
		}
		
		
		String dataOdierna = DateUtil.getData();
		if (pagamento.getDtPagamento().after(DateUtil.getDate(dataOdierna)))
			throw new GestionePagamentoException(
					ERRORE_DATA_PAGAMENTO_SUCCESSIVA_ALLA_DATA_ODIERNA);
		
		
		if (isNull(pagamento.getImportoPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPO_OBBLIGATORIO);

		if (isNull(pagamento.getIdModalitaPagamento()))
			throw new GestionePagamentoException(ERRORE_CAMPO_OBBLIGATORIO);
	}

	/**
	 * Verifica che l'importo del nuovo pagamento + la somma dei pagamenti gi�
	 * inseriti per il documento di spesa sia <= all'importo totale del
	 * documento di spesa, al netto delle note di credito JIRA 399 del 16 luglio
	 * 
	 * @param importoTotalePagamento
	 * @param sommaPagamentiDocumentoDiSpesa
	 * @param totaleImporto
	 * @return
	 */
	private void validaSommeTotali(Double importoTotalePagamento,
			Double sommaPagamentiDocumentoDiSpesa,
			Double totaleImportoDocDiSpesa, Double sommaImportiNoteDiCredito)
			throws GestionePagamentoException {
		// prendere il totale delle note di credito di tutti i progetti perch�
		// si ragiona sul doc di spesa
		// JIRA 399 del 16 luglio
		/*
		 * FIX PBandi-344
		 */
		totaleImportoDocDiSpesa = NumberUtil
				.toRoundDouble(totaleImportoDocDiSpesa)
				- NumberUtil.toRoundDouble(sommaImportiNoteDiCredito);
		// sommaPagamentiDocumentoDiSpesa = sommaPagamentiDocumentoDiSpesa ==
		// null ? 0D: sommaPagamentiDocumentoDiSpesa;

		double importoTotale = NumberUtil.toRoundDouble(importoTotalePagamento)
				+ NumberUtil.toRoundDouble(sommaPagamentiDocumentoDiSpesa);
		// if(isNull(importoTotale))importoTotale=0D;
		// if(isNull(totaleImportoDocDiSpesa))totaleImportoDocDiSpesa=0D;

		boolean isMinore = NumberUtil.toRoundDouble(importoTotale) <= NumberUtil
				.toRoundDouble(totaleImportoDocDiSpesa);
		boolean maggioreDiZero = NumberUtil
				.toRoundDouble(importoTotalePagamento) > 0D;

		// }L{ PBANDI-328
		if (!isMinore)
			throw new GestionePagamentoException(
					ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO);

		boolean controlloOK = maggioreDiZero && isMinore;
		if (!controlloOK)
			throw new GestionePagamentoException(
					ERRORE_VALIDA_SOMME_TOTALI_PAGAMENTI);
		/*
		 * if (!(importoTotalePagamento > 0D && (importoTotalePagamento +
		 * (sommaPagamentiDocumentoDiSpesa == null ? 0D :
		 * sommaPagamentiDocumentoDiSpesa)) <= totaleImportoDocDiSpesa)) { throw
		 * new GestionePagamentoException(
		 * ERRORE_VALIDA_SOMME_TOTALI_PAGAMENTI); }
		 */
	}

	/**
	 * Verifica che il nuovo importo inserito per il pagamento sia maggiore o
	 * uguale alla somma dei pagamenti inseriti per ogni singola voce di spesa
	 * 
	 * @param importoTotalePagamento
	 * @param pagamentiVociDiSpesa
	 * @return
	 */
	private void validaCoerenzaImporti(Double importoTotalePagamento,
			PagamentoVoceDiSpesaDTO[] pagamentiVociDiSpesa)
			throws GestionePagamentoException {

		Double sommaNuoviPagamentiVociDispesa = 0d;
		if (pagamentiVociDiSpesa != null) {
			for (PagamentoVoceDiSpesaDTO obj : pagamentiVociDiSpesa) {
				sommaNuoviPagamentiVociDispesa += obj
						.getImportoVoceDiSpesaCorrente();
			}

			if (!(NumberUtil.getDoubleValue(importoTotalePagamento) > 0D && NumberUtil
					.toRoundDouble(importoTotalePagamento) >= NumberUtil
					.toRoundDouble(sommaNuoviPagamentiVociDispesa)))
				throw new GestionePagamentoException(
						ERRORE_VALIDA_COERENZA_IMPORTO);
		}
	}

	/**
	 * 
	 * Il nuovo importo ripartito (o modfiicato dall'utente) sulla singola voce
	 * di spesa deve essere >=0 ; deve essere < della
	 * 
	 * @param arrayPagamentiVociDiSpesa
	 * @return
	 */
	private void validaSommeParziali(Double importoTotalePagamento,
			PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa)
			throws GestionePagamentoException {
		Double totaleImportiVociDiSpesa = 0D;

		if (arrayPagamentiVociDiSpesa != null) {
			for (PagamentoVoceDiSpesaDTO importivocidispesa : arrayPagamentiVociDiSpesa) {
				totaleImportiVociDiSpesa += importivocidispesa
						.getImportoVoceDiSpesaCorrente();
			}

			for (PagamentoVoceDiSpesaDTO importivocidispesa : arrayPagamentiVociDiSpesa) {

				if (importivocidispesa.getImportoRendicontato() >= 0) {

					Double nuovoPagamentoVoceDiSpesa = importivocidispesa
							.getImportoVoceDiSpesaCorrente();
					Double importoRendicontato = importivocidispesa
							.getImportoRendicontato();
					Double importoQuietanzato = importivocidispesa
							.getTotaleAltriPagamenti();

					Double diffRendicontatoQuietanzato = importoRendicontato
							- importoQuietanzato;
					// NumberUtil.toRoundDouble(diffRendicontatoQuietanzato);

					if (!(nuovoPagamentoVoceDiSpesa <= NumberUtil
							.toRoundDouble(diffRendicontatoQuietanzato)))
						throw new GestionePagamentoException(
								ERRORE_VALIDA_SOMME_PARZIALI_VOCIDISPESA);

					Double parzialeImportiAltreVociDiSpesa = totaleImportiVociDiSpesa
							- nuovoPagamentoVoceDiSpesa;
					if (!(NumberUtil.toRoundDouble(nuovoPagamentoVoceDiSpesa) <= (NumberUtil
							.toRoundDouble(importoTotalePagamento
									- parzialeImportiAltreVociDiSpesa))))
						throw new GestionePagamentoException(
								ERRORE_VALIDA_SOMME_PARZIALI_VOCIDISPESA);
				} else
					throw new GestionePagamentoException(
							ERRORE_VALIDA_SOMME_PARZIALI_VOCIDISPESA);
			}
		}

	}



	public ModalitaPagamentoDTO[] findModalitaPagamento(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idTipoDocumentoSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestionePagamentoException {
			String nameParameter[] = { "idUtente", "identitaDigitale",
					"idProgetto", "idDocumentoSpesa" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto, idTipoDocumentoSpesa);

			/*
			 * Recupero il progressivo bando linea del progetto
			 */
			BandoLineaProgettoVO bandoLineaVO = progettoManager
					.findBandoLineaForProgetto(idProgetto);

			/*
			 * Ricerco le modalita' di pagamento utilizzando come filtro il
			 * progressivo bando linea del progetto e il tipo di documento di
			 * spesa
			 */
			BandoLineaTipoDocumentoSpesaModalitaPagamentoVO filtro = new BandoLineaTipoDocumentoSpesaModalitaPagamentoVO();
			filtro.setIdTipoDocumentoSpesa(NumberUtil
					.toBigDecimal(idTipoDocumentoSpesa));
			filtro.setProgrBandoLineaIntervento(bandoLineaVO
					.getProgrBandoLineaIntervento());
			filtro.setAscendentOrder("descModalitaPagamento");

			ModalitaPagamentoVO groupByVO = new ModalitaPagamentoVO();			

			List<ModalitaPagamentoVO> modalitaPagamentiVO = genericDAO
					.findListWhereGroupBy(
							new FilterCondition<BandoLineaTipoDocumentoSpesaModalitaPagamentoVO>(
									filtro), groupByVO);

			return beanUtil.transform(modalitaPagamentiVO,
					ModalitaPagamentoDTO.class);

	}
	
	
	public EsitoOperazioneModificaPagamento modificaPagamentoPerValidazione(

			Long idUtente,

			String identitaDigitale,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamento,

			Long idBando,

			Long idDocumentoDiSpesa,

			Long idProgetto) throws GestionePagamentoException,
			FormalParameterException, UnrecoverableException {

		EsitoOperazioneModificaPagamento esitoOperazioneModifica = new EsitoOperazioneModificaPagamento();
		esitoOperazioneModifica.setEsito(Boolean.FALSE);
		List<String> messaggi = new ArrayList<String>();

		/**
		 * Simulo il controllo sull'idBando..definisce quattro possibili scenari
		 */
		EsitoScenarioBando esitoScenarioBando = initScenarioBandoPerValidazione(idBando);

		/**
		 * Verifico il nullable dei parametri di input, per quelli obbligatori
		 * la gestione della notifica del messaggio corretto la lascio al client
		 */
		String nameParameter[] = { "PagamentoDTO", "idDocumentoDiSpesa" };
		ValidatorInput.verifyNullValue(nameParameter, pagamento,
				idDocumentoDiSpesa);

		/*
		 * Non possono essere modificati i pagamenti in stato INSERITO o
		 * RESPINTO
		 */
		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento.
		if (pagamento
				.getDescBreveStatoValidazioneSpesa()
				.equals(
						PagamentiManager
								.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_INSERITO))
				|| pagamento
						.getDescBreveStatoValidazioneSpesa()
						.equals(
								PagamentiManager
										.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_RESPINTO))) {
			gestisciEsitoModificaErrato(pagamento, esitoOperazioneModifica,
					messaggi);
			return esitoOperazioneModifica;
		}
		*/

		/**
		 * Recupero i dati relativi al Documento di spesa:
		 * dataEmissioneDocumento somma dei pagamenti associati al documento di
		 * spesa importo totale del documento di spesa al netto delle note di
		 * credito se il documento � di tipo 4
		 * 
		 */
		DocumentoDiSpesaDTO documentoDTO = new DocumentoDiSpesaDTO();
		PDocumentoDiSpesaVO documentoVO = pbandipagamentiDAO
				.getInfoPagamentoDocumentoDiSpesa(idDocumentoDiSpesa);
		BeanUtil.valueCopy(documentoVO, documentoDTO);
		try {
			
			verificaDatiPagamento(esitoScenarioBando.getDataPagamento(), pagamento,
					documentoDTO.getDataEmissione(), messaggi, idProgetto, documentoVO.getIdTipoDocDiSpesa());
		} catch (ManagerException e1) {
			throw new GestionePagamentoException("");
		}

		PbandiTPagamentoVO pagamentoVO = new PbandiTPagamentoVO();
		pagamentoVO.setIdPagamento(new BigDecimal(pagamento.getIdPagamento()));
		PbandiTPagamentoVO[] pagamentiPrimaModificaVO = genericDAO
				.findWhere(pagamentoVO);

		// JIRA 399 del 16 luglio
		Double sommaImportiNoteDiCredito = getSommaImportiNoteDiCredito(idDocumentoDiSpesa);

		// JIRA 418 del 16 luglio
		// se cedolino il terzo parmaetro non � pi� l'importo totale netto ma
		// il + grande tra importo totale netto e rendicontabile tra + progetti
		// (somma dei vari PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE)
		Double importoTotaleNetto = documentoDTO.getImportoTotaleNetto() == null ? 0.0
				: documentoDTO.getImportoTotaleNetto();
		try {
			if (documentoDiSpesaManager
					.isCedolinoOAutodichiarazioneSoci(documentoVO
							.getIdTipoDocDiSpesa())) {
				importoTotaleNetto = calcolaImportoTotaleNetto(
						idDocumentoDiSpesa, importoTotaleNetto);
			}
		} catch (ManagerException e1) {
			throw new GestionePagamentoException("");
		}

		Double sommaPagamenti = documentoDTO.getSommaPagamenti();

		Double importoPagamento = 0D;
		if (!isEmpty(pagamentiPrimaModificaVO)
				&& !isNull(pagamentiPrimaModificaVO[0].getImportoPagamento()))
			importoPagamento = pagamentiPrimaModificaVO[0]
					.getImportoPagamento().doubleValue();

		Double sommaPagamentiDocumentoDiSpesa = 0D;
		if (!isNull(sommaPagamenti) && !isNull(importoPagamento))
			sommaPagamentiDocumentoDiSpesa = sommaPagamenti.doubleValue()
					- importoPagamento.doubleValue();

		// }L{ PBANDI-328
		try {
			validaSommeTotali(pagamento.getImportoPagamento(),
					sommaPagamentiDocumentoDiSpesa, importoTotaleNetto,
					sommaImportiNoteDiCredito);
		} catch (GestionePagamentoException e) {
			if (e.getMessage().equals(
					ERRORE_VOCE_DI_SPESA_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneModifica.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamentiDocumentoDiSpesa;
				//logger.debug(e.getMessage() + " {0} = " + residuo.toString());
				esitoOperazioneModifica.setParams(new String[] { NumberUtil.getStringValueItalianFormat(residuo) });
				esitoOperazioneModifica.setMessage(e.getMessage());
				return esitoOperazioneModifica;
			} else if (e.getMessage().equals(
					ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO)) {
				esitoOperazioneModifica.setEsito(Boolean.FALSE);
				messaggi.add(e.getMessage());
				Double residuo = importoTotaleNetto - sommaImportiNoteDiCredito
						- sommaPagamentiDocumentoDiSpesa;
			//	logger.debug(e.getMessage() + " {0} = " + residuo.toString());
				esitoOperazioneModifica.setParams(new String[] {NumberUtil.getStringValueItalianFormat(residuo)});
				esitoOperazioneModifica.setMessage(e.getMessage());
				return esitoOperazioneModifica;

			} else {
				throw e;
			}
		}

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {

			// ValidatorInput.verifyArrayNotEmpty("arrayPagamentiVociDiSpesa",
			// arrayPagamentiVociDiSpesa);

			validaCoerenzaImporti(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);
			validaSommeParziali(pagamento.getImportoPagamento(),
					arrayPagamentiVociDiSpesa);

		}

		/*
		 * }L{ PBANDI-509
		 */
		boolean isStatoPagamentoDaModificare = false;

		// controllo se � stato modificato l'importo del pagamento
		PbandiTPagamentoVO oldPagamento = new PbandiTPagamentoVO();
		oldPagamento.setIdPagamento(NumberUtil.toBigDecimal(pagamento
				.getIdPagamento()));
		oldPagamento.setImportoPagamento(BeanUtil.transformToBigDecimal(pagamento
				.getImportoPagamento()));
		List<PbandiTPagamentoVO> pagamentoTrovato = genericDAO
				.findListWhere(oldPagamento);

		if (pagamentoTrovato.isEmpty())
			isStatoPagamentoDaModificare = true;

		// controllo se sono state modificate le voci di spesa (posso evitare se
		// � stato gi� modificato l'importo)
		if (!isStatoPagamentoDaModificare && arrayPagamentiVociDiSpesa != null) {
			for (PagamentoVoceDiSpesaDTO voceDiSpesa : arrayPagamentiVociDiSpesa) {
				PbandiRPagQuotParteDocSpVO oldVoceDiSpesa = new PbandiRPagQuotParteDocSpVO();
				oldVoceDiSpesa.setIdQuotaParteDocSpesa(NumberUtil
						.toBigDecimal(voceDiSpesa.getIdQuotaParteDocSpesa()));
				oldVoceDiSpesa.setIdPagamento(NumberUtil.toBigDecimal(pagamento
						.getIdPagamento()));
				oldVoceDiSpesa.setImportoQuietanzato(BeanUtil
						.transformToBigDecimal(voceDiSpesa
								.getImportoVoceDiSpesaCorrente()));
				List<PbandiRPagQuotParteDocSpVO> voceDiSpesaTrovata = genericDAO
						.findListWhere(oldVoceDiSpesa);
				if (voceDiSpesaTrovata.isEmpty())
					isStatoPagamentoDaModificare = true;
			}
		}

		if (isStatoPagamentoDaModificare) {
			/*
			 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
			String codiceStatoPagamentoDichiarato = PagamentiManager
					.getCodiceStatoValidazionePagamento(GestionePagamentiSrv.STATO_VALIDAZIONE_DICHIARATO);
			DecodificaDTO decodificaStatoPagamentoDichiarato = decodificheManager
					.findDecodifica(
							GestioneDatiDiDominioSrv.STATO_VALIDAZ_SPESA,
							codiceStatoPagamentoDichiarato);
			pbandipagamentiDAO.modificaStatoValidazione(pagamento
					.getIdPagamento(), decodificaStatoPagamentoDichiarato
					.getId());
			*/		

			// PBANDI 852 - METTO A NULL GLI IMPORTI DI TUTTE LE VOCI DI SPESA
			// DEL PAGAMENTO
			if (arrayPagamentiVociDiSpesa != null) {

				for (PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO : arrayPagamentiVociDiSpesa) {
					PbandiRPagQuotParteDocSpVO pagQuotParteDocSpVO = trasformaInVO(
							idUtente, pagamento.getIdPagamento(),
							pagamentoVoceDiSpesaDTO);

					pagQuotParteDocSpVO.setImportoValidato(null);
					PbandiRPagQuotParteDocSpVO filter = new PbandiRPagQuotParteDocSpVO();
					filter.setIdPagamento(pagQuotParteDocSpVO.getIdPagamento());
					filter.setIdQuotaParteDocSpesa(pagQuotParteDocSpVO
							.getIdQuotaParteDocSpesa());
					// filter = genericDAO.findSingleWhere(filter);
					// }L{ ATTENZIONE! � possibile che non esista una
					// associazione
					// (importo non ripartito per una voce di spesa)
					List<PbandiRPagQuotParteDocSpVO> filterList = genericDAO
							.findListWhere(filter);
					if (filterList.size() == 1) {
						filter = filterList.get(0);
						pagQuotParteDocSpVO.setProgrPagQuotParteDocSp(filter
								.getProgrPagQuotParteDocSp());
						try {
							pagQuotParteDocSpVO.setIdUtenteAgg(new BigDecimal(idUtente));
							genericDAO.updateNullables(pagQuotParteDocSpVO);
						} catch (Exception ex) {
							throw new UnrecoverableException(ex.getMessage());
						}
					} else if (filterList.size() > 1) {
						// Il DB � sporco: una ripartizione ha al pi� una VdS
						throw new UnrecoverableException(
								"Trovata pi� di una coppia (ID_PAGAMENTO = "
										+ pagQuotParteDocSpVO.getIdPagamento()
										+ ", ID_QUOTA_PARTE_DOC_SPESA = "
										+ pagQuotParteDocSpVO
												.getIdQuotaParteDocSpesa()
										+ ") su PBANDI_R_PAG_QUOT_PARTE_DOC_SP. Questa condizione non dovrebbe essere possibile.");
					}
				}
			}

		}
		// PBANDI-509 END

		pbandipagamentiDAO.modificaPagamento(pagamento.getIdPagamento(),
				pagamento.getIdModalitaPagamento(), new java.sql.Date(pagamento
						.getDtPagamento().getTime()), pagamento
						.getImportoPagamento(), idUtente, esitoScenarioBando
						.getDataPagamento(),
						pagamento.getIdCausalePagamento(),
						pagamento.getRifPagamento(), null);

		if (esitoScenarioBando.isVisualizzaImportiVociDiSpesa()) {
			modificaPagamentiVociDiSpesa(pagamento.getIdPagamento(),
					arrayPagamentiVociDiSpesa, idUtente);
		}

		esitoOperazioneModifica.setEsito(Boolean.TRUE);
		messaggi.add(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		/*
		 * if (messaggi != null && !messaggi.isEmpty() &&
		 * messaggi.contains(ERRORE_VALIDAZIONE_DATA_VALUTA_PAGAMENTO)) // TODO
		 * avviso dataPagamento < dataDichiarazione esitoOperazioneModifica
		 * .setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO); else
		 */
		esitoOperazioneModifica.setMessage(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		return esitoOperazioneModifica;
	}

	public CausalePagamentoDTO[] getCausaliPagamento(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestionePagamentoException {
		String nameParameter[] = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente ,identitaDigitale);
		logger.info("getCausaliPagamento");
		PbandiDCausalePagamentoVO[] all = genericDAO.findAll(PbandiDCausalePagamentoVO.class);
		logger.info("found causaliPagamento: "+(all!=null?all.length:0));
		CausalePagamentoDTO[] ret = beanUtil.transform(all,CausalePagamentoDTO.class);
		return ret;
	}



}
