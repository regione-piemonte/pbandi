/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.erogazione;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.csi.wrapper.UserException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CausaleErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.DichiarazioneDiRinunciaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.DichiarazioneDiRinunciaReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EnteAppartenenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoOperazioneAssociazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoScaricaDichiarazioneDiRinuncia;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.FideiussioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.IstanzaAttivitaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ModalitaAgevolazioneErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.PagamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.RichiestaErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.SpesaProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.SedeDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.fineprogetto.FineProgettoException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiErogazioneDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiPagamentiDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DelegatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.EnteDiAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneCausaleModalitaAgevolazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ModalitaDiAgevolazioneContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.CausaleErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.ModalitaAgevolazioneErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDCausaleErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDModalitaAgevolazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDModalitaErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoOperazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoCausaleErogazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRecuperoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRichiestaErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRinunciaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.erogazione.ErogazioneSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.doqui.index.ecmengine.dto.Node;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ErogazioneBusinessImpl extends BusinessImpl implements
		ErogazioneSrv {

	private DocumentoManager documentoManager;
	@Autowired
	private GenericDAO genericDAO;
	private IndexDAO indexDAO;
	private NeofluxSrv neofluxBusiness;
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	private PbandiErogazioneDAOImpl pbandiErogazioneDAO;
	private PopolaTemplateManager popolaTemplateManager;
	private ProgettoManager progettoManager;
	private RappresentanteLegaleManager rappresentanteLegaleManager;
	private RegolaManager regolaManager;
	private ReportManager reportManager;
	private SedeManager sedeManager;
	private SoggettoManager soggettoManager;
	
	public NeofluxSrv getNeofluxBusiness() {
		return neofluxBusiness;
	}

	public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
		this.neofluxBusiness = neofluxBusiness;
	}
	public void setSedeManager(SedeManager sedeManager) {
		this.sedeManager = sedeManager;
	}

	public SedeManager getSedeManager() {
		return sedeManager;
	}

	public RegolaManager getRegolaManager() {
		return regolaManager;
	}

	public void setRegolaManager(RegolaManager regolaManager) {
		this.regolaManager = regolaManager;
	}
	public void setPopolaTemplateManager(PopolaTemplateManager popolaTemplateManager) {
		this.popolaTemplateManager = popolaTemplateManager;
	}

	public PopolaTemplateManager getPopolaTemplateManager() {
		return popolaTemplateManager;
	}
	private ContoEconomicoManager contoEconomicoManager;

	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public void setPbandiDocumentiDiSpesaDAO(
			PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public IndexDAO getIndexDAO() {
		return indexDAO;
	}

	public void setIndexDAO(IndexDAO indexDAO) {
		this.indexDAO = indexDAO;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}
	public void setRappresentanteLegaleManager(
			RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}

	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}
	public EsitoErogazioneDTO inserisciErogazione(Long idUtente,
			String identitaDigitale, Long idProgetto, ErogazioneDTO erogazione,
			String codUtente, IstanzaAttivitaDTO istanzaAttivita)
			throws CSIException, SystemException, UnrecoverableException,
			ErogazioneException {
	 
		EsitoErogazioneDTO esito = new EsitoErogazioneDTO();

		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto", "erogazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto, erogazione);

			BigDecimal importoErogazioni = getImportoErogazioniPerModalitaAgevolazionePerInserimento(
					idProgetto, erogazione);

			if (NumberUtil.compare(importoErogazioni, contoEconomicoManager
					.getQuotaImportoAgevolato(new BigDecimal(idProgetto),
							erogazione.getCodModalitaAgevolazione())) > 0) {
				throw new ErogazioneException(
						ErrorConstants.ERRORE_SOMMA_EROGAZIONI_PER_MOD_AGEVOLAZ_SUPERIORE_IMPORTO_AGEVOLATO_CONTO_ECONOMICO);
			}
			PbandiTErogazioneVO vo = new PbandiTErogazioneVO();
			vo.setImportoErogazione(BeanUtil.transformToBigDecimal(erogazione
					.getImportoErogazioneEffettivo()));
			vo.setDtContabile(new java.sql.Date(erogazione.getDataContabile()
					.getTime()));
			vo.setCodRiferimentoErogazione(erogazione.getNumeroRiferimento());
			vo.setNoteErogazione(erogazione.getNoteErogazione());
			vo.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
			vo.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			String codCausale = new StringTokenizer(erogazione.getCodCausaleErogazione(), "@").nextToken();
			DecodificaDTO decodificaVO = decodificheManager.findDecodifica(	GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, codCausale);
			vo.setIdCausaleErogazione(NumberUtil.toBigDecimal(decodificaVO.getId()));
			decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.MODALITA_EROGAZIONE,
					erogazione.getCodModalitaErogazione());
			vo.setIdModalitaErogazione(NumberUtil.toBigDecimal(decodificaVO.getId()));
			decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE,
					erogazione.getCodModalitaAgevolazione());
			vo.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(decodificaVO.getId()));
			// }L{ fix aggiornamento DB 31/03/2010
			if (!StringUtil.isEmpty(erogazione.getCodDirezione())) {
				PbandiTEnteCompetenzaVO ente = new PbandiTEnteCompetenzaVO();
				ente.setDescBreveEnte(erogazione.getCodDirezione());
				vo.setIdEnteCompetenza(genericDAO.findSingleWhere(ente).getIdEnteCompetenza());
			}

			PbandiTErogazioneVO ret = genericDAO.insert(vo);
			esito.setEsito(new Boolean(true));
			esito.setMessages(new String[] { MessaggiConstants.MSG_EROGAZIONE_SALVATA });
			ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
			erogazioneDTO.setIdErogazione(NumberUtil.toLong(ret
					.getIdErogazione()));
			esito.setErogazione(erogazioneDTO);
			
		/*	logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+idProgetto);
			Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, idProgetto);
			logger.warn("processo: "+processo);
			if(processo!=null){*/
				logger.warn("\n\n############################ NEOFLUX UNLOCK EROGAZIONE ##############################\n");
				neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto,Task.EROGAZIONE);
				logger.warn("############################ NEOFLUX UNLOCK EROGAZIONE ##############################\n\n\n\n");
				
				logger.info("calling genericDAO.callProcedure().sendNotificationMessage for EROGAZIONE....");
				genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),Notification.NOTIFICA_EROGAZIONE,Notification.BENEFICIARIO,idUtente);
				logger.info("calling genericDAO.callProcedure().sendNotificationMessage for EROGAZIONE ok");
				
			/*}else{
				//+flux+ 
				logger.warn("\n\n############################ OLDFLUX ##############################\ncalling getProcessManager().inserisciErogazione idProgetto :"+idProgetto);
				getProcessManager().inserisciErogazione(idUtente,identitaDigitale);
				logger.warn("inserisciErogazione OK\n############################ OLDFLUX ##############################\n\n\n\n");
			}*/
			
			
		

			
			
			
		} catch (Exception e) {
			if (UserException.class.isInstance(e)) {
				throw (UserException) e;
			}
			throw new UnrecoverableException(e.getMessage(), e);
		}  

		return esito;
	}

	private BigDecimal getImportoErogazioniPerModalitaAgevolazione(
			Long idProgetto, ErogazioneDTO erogazione) {
		PbandiTErogazioneVO pbandiTErogazioneVO = new PbandiTErogazioneVO();
		pbandiTErogazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE,
				erogazione.getCodModalitaAgevolazione());
		pbandiTErogazioneVO.setIdModalitaAgevolazione(new BigDecimal(
				decodificaVO.getId()));
		List<PbandiTErogazioneVO> list = genericDAO
				.findListWhere(pbandiTErogazioneVO);
		BigDecimal importo = new BigDecimal(0);
		boolean isImportoCorrenteSommato = false;
		if (list != null) {
			Iterator<PbandiTErogazioneVO> iter = list.iterator();
			while (iter.hasNext()) {
				PbandiTErogazioneVO vo = (PbandiTErogazioneVO) iter.next();
				if (vo.getIdErogazione().equals(
						new BigDecimal(erogazione.getIdErogazione()))) {
					isImportoCorrenteSommato = true;
					importo = NumberUtil.sum(
							importo,
							new BigDecimal(erogazione
									.getImportoErogazioneEffettivo()));
				} else {
					importo = NumberUtil
							.sum(importo, vo.getImportoErogazione());
				}

			}

		}

		if (isImportoCorrenteSommato == false) {
			importo = NumberUtil.sum(importo,
					new BigDecimal(erogazione.getImportoErogazioneEffettivo()));
		}

		/*
		 * JIRA-1197. All' importo totale delle erogazioni per modalita' di
		 * agevolazione per il progetto devono essere sottratti i recuperi
		 * eseguiti per la modalita'
		 */
		BigDecimal totaleRecuperi = getTotaleRecuperiPerModalita(idProgetto,
				decodificaVO.getId());

		importo = NumberUtil.subtract(importo, totaleRecuperi);

		return importo;
	}

	private BigDecimal getImportoErogazioniPerModalitaAgevolazionePerInserimento(
			Long idProgetto, ErogazioneDTO erogazione) {
		PbandiTErogazioneVO pbandiTErogazioneVO = new PbandiTErogazioneVO();
		pbandiTErogazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE,
				erogazione.getCodModalitaAgevolazione());
		pbandiTErogazioneVO.setIdModalitaAgevolazione(new BigDecimal(
				decodificaVO.getId()));
		List<PbandiTErogazioneVO> list = genericDAO
				.findListWhere(pbandiTErogazioneVO);
		BigDecimal importo = new BigDecimal(0);
		if (list != null) {

			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				PbandiTErogazioneVO vo = (PbandiTErogazioneVO) iter.next();
				importo = NumberUtil.sum(importo, vo.getImportoErogazione());

			}
		}

		/*
		 * JIRA-1195 Sottrarre all' importo totale dell' erogazione il totale
		 * dei recuperi per la modalita' di agevolazione di quel progetto
		 */
		BigDecimal totaleRecuperi = getTotaleRecuperiPerModalita(idProgetto,
				decodificaVO.getId());

		importo = NumberUtil.subtract(importo, totaleRecuperi);

		importo = NumberUtil.sum(importo,
				new BigDecimal(erogazione.getImportoErogazioneEffettivo()));

		return importo;
	}

	public EsitoErogazioneDTO findErogazione(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idBandoLinea)
			throws CSIException, SystemException, UnrecoverableException,
			ErogazioneException {
		 
		EsitoErogazioneDTO esitoErogazioneDTO = new EsitoErogazioneDTO();
		ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
		SpesaProgettoDTO spesaProgetto = new SpesaProgettoDTO();
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			// DATI DELLA SPESA PROGETTO
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			PbandiDTipoOperazioneVO tipoOperazioneVO = new PbandiDTipoOperazioneVO();
			tipoOperazioneVO.setIdTipoOperazione(progettoVO
					.getIdTipoOperazione());
			tipoOperazioneVO = genericDAO.findSingleWhere(tipoOperazioneVO);
			spesaProgetto.setTipoOperazione(tipoOperazioneVO
					.getDescTipoOperazione());
			spesaProgetto.setImportoAmmessoContributo(contoEconomicoManager
					.findImportoAgevolato(idProgetto));
			Double totaleSpesaAmmessa = contoEconomicoManager
					.getTotaleSpesaAmmmessaInRendicontazioneDouble(new BigDecimal(
							idProgetto));
			spesaProgetto.setTotaleSpesaAmmessa(totaleSpesaAmmessa);
			DocumentoDiSpesaDTO documentoDiSpesa = new DocumentoDiSpesaDTO();
			documentoDiSpesa.setIdProgetto(idProgetto);
			documentoDiSpesa.setIsGestitiNelProgetto(new Boolean(true));
			double sommaImportiQuietanzati = 0;
			double sommaImportiValidati = 0;
			List<DocumentoDiSpesaVO> list = pbandiDocumentiDiSpesaDAO
					.findDocumentiDiSpesa(documentoDiSpesa, null);
			Iterator<DocumentoDiSpesaVO> iter = list.iterator();
			while (iter.hasNext()) {
				DocumentoDiSpesaVO obj = (DocumentoDiSpesaVO) iter.next();
				List<PagamentoQuotePartiVO> listPagamenti = pbandipagamentiDAO
						.findPagamentiAssociati(obj.getIdDocumentoDiSpesa(),
								idProgetto);
				Iterator<PagamentoQuotePartiVO> iterPagamenti = listPagamenti
						.iterator();

				while (iterPagamenti.hasNext()) {
					PagamentoQuotePartiVO objPagamento = (PagamentoQuotePartiVO) iterPagamenti
							.next();
					// if
					// (objPagamento.getDescBreveStatoValidazioneSpesa().equals(Constants.COD_STATO_VALIDAZIONE_DICHIARATO)){//
					
					/*
					 * FIX PBandi-2351
					 */
					Double importoQuietanzato = pbandipagamentiDAO
							.getImportoQuietanzato(idProgetto,NumberUtil
									.toLong(objPagamento.getIdPagamento()));
					if (importoQuietanzato != null) {
						sommaImportiQuietanzati += importoQuietanzato
								.doubleValue();
					}
					// }else if
					// (objPagamento.getDescBreveStatoValidazioneSpesa().equals(Constants.COD_STATO_VALIDAZIONE_VALIDATO)
					// ||
					// objPagamento.getDescBreveStatoValidazioneSpesa().equals(Constants.COD_STATO_VALIDAZIONE_PARZIALMENTE_VALIDATO)){
					/*
					 * FIX PBandi-2351
					 */
					Double importoValidato = pbandipagamentiDAO
							.getImportoValidato(idProgetto,NumberUtil.toLong(objPagamento
									.getIdPagamento()));
					if (importoValidato != null) {
						sommaImportiValidati += importoValidato.doubleValue();
					}
					// }
				}

				if (sommaImportiQuietanzati > 0) {
					spesaProgetto
							.setTotaleSpesaSostenuta(sommaImportiQuietanzati);
					// spesaProgetto.setAvanzamentoSpesaSostenuta(sommaImportiQuietanzati/spesaProgetto.getImportoAmmessoContributo()*100);
					spesaProgetto
							.setAvanzamentoSpesaSostenuta(sommaImportiQuietanzati
									/ spesaProgetto.getTotaleSpesaAmmessa()
									* 100);
				}
				if (sommaImportiValidati > 0) {
					spesaProgetto.setTotaleSpesaValidata(sommaImportiValidati);
					// spesaProgetto.setAvanzamentoSpesaValidata(sommaImportiValidati/spesaProgetto.getImportoAmmessoContributo());
					spesaProgetto
							.setAvanzamentoSpesaValidata(sommaImportiValidati
									/ spesaProgetto.getTotaleSpesaAmmessa()
									* 100);
				}
			}

			erogazioneDTO.setSpesaProgetto(spesaProgetto);
			// DATI 2 FIDEIUSSIONE
			FideiussioneVO[] fideiussioniVO = pbandiErogazioneDAO
					.findFideiussioniAttive(idProgetto);
			if (fideiussioniVO != null) {
				FideiussioneDTO[] fideiussioniDTO = new FideiussioneDTO[fideiussioniVO.length];
				getBeanUtil().valueCopy(fideiussioniVO, fideiussioniDTO);
				erogazioneDTO.setFideiussioni(fideiussioniDTO);
			}
			// DATI 3 EROGAZIONI DEL PROGETTO
			BigDecimal sommaTotaliImportiErogazioni = new BigDecimal(0);
			BigDecimal sommaTotaleImportiRecupero = new BigDecimal(0);
			ErogazioneCausaleModalitaAgevolazioneVO erogazioneVO = new ErogazioneCausaleModalitaAgevolazioneVO();
			erogazioneVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			List<ErogazioneCausaleModalitaAgevolazioneVO> erogazioni = genericDAO
					.findListWhere(erogazioneVO);
			if (erogazioni != null) {
				List<ErogazioneDTO> listErogazioni = new ArrayList<ErogazioneDTO>();
				List<PbandiTRecuperoVO> recuperiVO = new ArrayList<PbandiTRecuperoVO>();
				for (ErogazioneCausaleModalitaAgevolazioneVO vo : erogazioni) {
					ErogazioneDTO dto = new ErogazioneDTO();
					dto.setIdErogazione(NumberUtil.toLong(vo.getIdErogazione()));
					dto.setDescrizioneCausaleErogazione(vo.getDescCausale());
					dto.setImportoErogazioneEffettivo(NumberUtil.toDouble(vo
							.getImportoErogazione()));
					dto.setDataContabile(vo.getDtContabile());
					dto.setNumeroRiferimento(vo.getCodRiferimentoErogazione());
					dto.setDescModalitaAgevolazione(vo
							.getDescModalitaAgevolazione());
					listErogazioni.add(dto);

					sommaTotaliImportiErogazioni = NumberUtil.sum(NumberUtil
							.createScaledBigDecimal(vo.getImportoErogazione()),
							sommaTotaliImportiErogazioni);

					PbandiTRecuperoVO recuperoVO = new PbandiTRecuperoVO();
					recuperoVO.setIdModalitaAgevolazione(vo
							.getIdModalitaAgevolazione());
					recuperiVO.add(recuperoVO);
				}
				if (!recuperiVO.isEmpty()) {
					/*
					 * ricerco i recuperi per le modalita' di agevolazioni
					 */
					PbandiTRecuperoVO filtroVO = new PbandiTRecuperoVO();
					filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
					FilterCondition<PbandiTRecuperoVO> conditionModalita = new FilterCondition<PbandiTRecuperoVO>(
							recuperiVO);
					FilterCondition<PbandiTRecuperoVO> filterCondition = new FilterCondition<PbandiTRecuperoVO>(
							filtroVO);
					AndCondition<PbandiTRecuperoVO> andCondition = new AndCondition<PbandiTRecuperoVO>(
							filterCondition, conditionModalita);

					List<PbandiTRecuperoVO> listRecuperi = genericDAO
							.findListWhere(andCondition);
					if (listRecuperi != null) {
						for (PbandiTRecuperoVO vo : listRecuperi) {
							sommaTotaleImportiRecupero = NumberUtil.sum(
									NumberUtil.createScaledBigDecimal(vo
											.getImportoRecupero()),
									sommaTotaleImportiRecupero);
						}
					}
				}
				erogazioneDTO.setErogazioni(beanUtil.transform(listErogazioni,
						ErogazioneDTO.class));
			}
			erogazioneDTO.setImportoTotaleRecuperi(NumberUtil
					.getDoubleValue(sommaTotaleImportiRecupero));

			/*
			 * JIRA-1194. Ricerca delle modalita' di agevolazione
			 */
			ModalitaAgevolazioneErogazioneVO filtroModalitaVO = new ModalitaAgevolazioneErogazioneVO();
			filtroModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			filtroModalitaVO.setAscendentOrder("idModalitaAgevolazione");

			List<ModalitaAgevolazioneErogazioneVO> listModalitaAgevolazioneVO = genericDAO
					.findListWhere(filtroModalitaVO);
			List<ModalitaAgevolazioneErogazioneDTO> modalitaAgevolazioneErogazione = new ArrayList<ModalitaAgevolazioneErogazioneDTO>();

			/*
			 * JIRA-1221
			 */
			List<ModalitaDiAgevolazioneContoEconomicoVO> listModalitaAgevolazioneContoEconomicoVO = null;
			/*
			 * ricerco le modalita' di agevolazione legate al conto economico
			 * master del progetto
			 */
			try {
				listModalitaAgevolazioneContoEconomicoVO = contoEconomicoManager
						.caricaModalitaAgevolazione(
								NumberUtil.toBigDecimal(idProgetto),
								Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error("Nessun conto economico trovato", e);
				ErogazioneException re = new ErogazioneException(ERRORE_SERVER);
				throw re;
			}

			if (listModalitaAgevolazioneContoEconomicoVO != null) {
				/*
				 * ricerco le causali di erogazione per il progetto
				 */
				CausaleErogazioneVO filtroCausaleVO = new CausaleErogazioneVO();
				filtroCausaleVO.setIdProgetto(NumberUtil
						.toBigDecimal(idProgetto));
				filtroCausaleVO.setAscendentOrder("idProgetto",
						"idModalitaAgevolazione", "dtContabile");
				List<CausaleErogazioneVO> listCausaliErogazioneVO = genericDAO
						.findListWhere(filtroCausaleVO);

				for (ModalitaDiAgevolazioneContoEconomicoVO modContoEconomicoVO : listModalitaAgevolazioneContoEconomicoVO) {
					ModalitaAgevolazioneErogazioneDTO modalitaDTO = beanUtil
							.transform(modContoEconomicoVO,
									ModalitaAgevolazioneErogazioneDTO.class);
					modalitaDTO.setUltimoImportoAgevolato(NumberUtil
							.toDouble(modContoEconomicoVO
									.getQuotaImportoAgevolato()));

					for (ModalitaAgevolazioneErogazioneVO vo : listModalitaAgevolazioneVO) {
						if (NumberUtil
								.compare(modContoEconomicoVO
										.getIdModalitaAgevolazione(), vo
										.getIdModalitaAgevolazione()) == 0) {
							modalitaDTO.setImportoTotaleErogazioni(NumberUtil
									.toDouble(vo.getImportoTotaleErogazioni()));
							modalitaDTO.setImportoTotaleRecupero(NumberUtil
									.toDouble(vo.getImportoTotaleRecupero()));
							break;
						}
					}

					if (listCausaliErogazioneVO != null) {
						List<CausaleErogazioneDTO> causali = new ArrayList<CausaleErogazioneDTO>();
						for (CausaleErogazioneVO causaleVO : listCausaliErogazioneVO) {
							if (NumberUtil.compare(causaleVO
									.getIdModalitaAgevolazione(),
									modContoEconomicoVO
											.getIdModalitaAgevolazione()) == 0) {
								CausaleErogazioneDTO causale = beanUtil
										.transform(causaleVO,
												CausaleErogazioneDTO.class);
								causali.add(causale);
							}
						}
						modalitaDTO.setCausaliErogazione(beanUtil.transform(
								causali, CausaleErogazioneDTO.class));
					}
					modalitaAgevolazioneErogazione.add(modalitaDTO);
				}
			}

			erogazioneDTO.setModalitaAgevolazioni(beanUtil.transform(
					modalitaAgevolazioneErogazione,
					ModalitaAgevolazioneErogazioneDTO.class));
			erogazioneDTO.setImportoTotaleErogato(NumberUtil
					.toDouble(sommaTotaliImportiErogazioni));
			/*
			 * JIRA-1195 Consideriamo anche il totale dei recuperi
			 */
			BigDecimal importoTotaleResiduo = NumberUtil.sum(NumberUtil
					.createScaledBigDecimal(erogazioneDTO.getSpesaProgetto()
							.getImportoAmmessoContributo()), NumberUtil
					.createScaledBigDecimal(sommaTotaleImportiRecupero));

			importoTotaleResiduo = NumberUtil
					.subtract(
							importoTotaleResiduo,
							NumberUtil
									.createScaledBigDecimal(sommaTotaliImportiErogazioni));

			erogazioneDTO.setImportoTotaleResiduo(NumberUtil
					.toDouble(importoTotaleResiduo));

			// DATI 4 RICHIESTE EROGAZIONI DEL PROGETTO
			double sommaTotaliImportiRichiesteErogazioni = 0;
			PbandiTRichiestaErogazioneVO richiestaErogazioneVO = new PbandiTRichiestaErogazioneVO();
			richiestaErogazioneVO.setIdProgetto(NumberUtil
					.toBigDecimal(idProgetto));
			List<PbandiTRichiestaErogazioneVO> richiestaErogazioni = genericDAO
					.findListWhere(richiestaErogazioneVO);
			if (richiestaErogazioni != null) {
				ArrayList<RichiestaErogazioneDTO> listRichiestaErog = new ArrayList<RichiestaErogazioneDTO>();
				Iterator<PbandiTRichiestaErogazioneVO> iterRichiestaErog = richiestaErogazioni
						.iterator();
				while (iterRichiestaErog.hasNext()) {
					PbandiTRichiestaErogazioneVO vo = (PbandiTRichiestaErogazioneVO) iterRichiestaErog
							.next();
					RichiestaErogazioneDTO dto = new RichiestaErogazioneDTO();
					PbandiDCausaleErogazioneVO causaleErogazioneVO = new PbandiDCausaleErogazioneVO();
					causaleErogazioneVO.setIdCausaleErogazione(vo
							.getIdCausaleErogazione());
					causaleErogazioneVO = genericDAO
							.findSingleWhere(causaleErogazioneVO);

					dto.setDescCausaleErogazione(causaleErogazioneVO
							.getDescCausale());
					dto.setDataRichiestaErogazione(vo
							.getDtRichiestaErogazione());
					sommaTotaliImportiRichiesteErogazioni = sommaTotaliImportiRichiesteErogazioni
							+ NumberUtil.getDoubleValue(vo
									.getImportoErogazioneRichiesto());
					dto.setImportoRichiestaErogazione(NumberUtil
							.getDoubleValue(vo.getImportoErogazioneRichiesto()));
					dto.setNumeroRichiestaErogazione(vo
							.getNumeroRichiestaErogazione().toString());
					listRichiestaErog.add(dto);
				}
				if (!listRichiestaErog.isEmpty()) {
					RichiestaErogazioneDTO[] array = listRichiestaErog
							.toArray(new RichiestaErogazioneDTO[] {});
					erogazioneDTO.setRichiesteErogazioni(array);
				}

				// erogazioneDTO.setRichiesteErogazioni((RichiestaErogazioneDTO[])listRichiestaErog.toArray());
			}
			erogazioneDTO
					.setImportoTotaleRichiesto(sommaTotaliImportiRichiesteErogazioni);

			esitoErogazioneDTO.setErogazione(erogazioneDTO);

			/*
			 * GESTIONE REGOLA PER EROGAZIONE
			 */
			if (idBandoLinea != null) {
				// }L{ Il BandoLinea potrebbe essere null (es se chiamato in
				// rinuncia)
				boolean isRegolaApplicabile = regolaManager
						.isRegolaApplicabileForBandoLinea(
								idBandoLinea,
								RegoleConstants.BR13_ATTIVITA_EROGAZIONE_DISPONIBILE);
				esitoErogazioneDTO.setIsRegolaAttiva(isRegolaApplicabile);
			}

	 

		return esitoErogazioneDTO;
	}

	public EsitoErogazioneDTO findDettaglioErogazione(Long idUtente,
			String identitaDigitale, Long idErogazione, Long idFormaGiuridica,
			Long idDimensioneImpresa, Long idBandoLinea) throws CSIException,
			SystemException, UnrecoverableException {
		 
		EsitoErogazioneDTO esito = new EsitoErogazioneDTO();
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idErogazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idErogazione);

			PbandiTErogazioneVO erogazioneVO = new PbandiTErogazioneVO();

			erogazioneVO.setIdErogazione(new BigDecimal(idErogazione));
			erogazioneVO = genericDAO.findSingleWhere(erogazioneVO);
			ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
			erogazioneDTO.setIdErogazione(NumberUtil.toLong(erogazioneVO
					.getIdErogazione()));
			// BISOGNA CREARE IL COD COMPLESSO
			// erogazioneDTO.setCodCausaleErogazione(val)
			BandoLineaVO bandoLineaVO = new BandoLineaVO();
			bandoLineaVO.setProgrBandoLineaIntervento(NumberUtil
					.toBigDecimal(idBandoLinea));
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);

			PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridicaECausale(
					idFormaGiuridica, idDimensioneImpresa, bandoLineaVO,
					NumberUtil.toLong(erogazioneVO.getIdCausaleErogazione()));
			DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE,
					NumberUtil.toLong(erogazioneVO.getIdCausaleErogazione()));
			BigDecimal percErogazione = bandiRBandoCausaleErogazVO
					.getPercErogazione();
			BigDecimal percLimite = bandiRBandoCausaleErogazVO.getPercLimite();
			if (percErogazione == null)
				percErogazione = new BigDecimal(0);
			if (percLimite == null)
				percLimite = new BigDecimal(0);
			erogazioneDTO.setCodCausaleErogazione(decodificaVO
					.getDescrizioneBreve()
					+ "@"
					+ percErogazione
					+ "@"
					+ percLimite);

			decodificaVO = decodificheManager
					.findDecodifica(
							GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE,
							NumberUtil.toLong(erogazioneVO
									.getIdModalitaAgevolazione()));
			erogazioneDTO.setCodModalitaAgevolazione(decodificaVO
					.getDescrizioneBreve());
			decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.MODALITA_EROGAZIONE,
					NumberUtil.toLong(erogazioneVO.getIdModalitaErogazione()));
			erogazioneDTO.setCodModalitaErogazione(decodificaVO
					.getDescrizioneBreve());
			// }L{ fix aggiornamento DB 31/03/2010
			PbandiTEnteCompetenzaVO ente = new PbandiTEnteCompetenzaVO();
			ente.setIdEnteCompetenza(erogazioneVO.getIdEnteCompetenza());
			erogazioneDTO.setCodDirezione(genericDAO.findSingleWhere(ente)
					.getDescBreveEnte());
			erogazioneDTO.setDataContabile(erogazioneVO.getDtContabile());
			erogazioneDTO.setImportoErogazioneEffettivo(NumberUtil
					.toDouble(erogazioneVO.getImportoErogazione()));
			erogazioneDTO.setNumeroRiferimento(erogazioneVO
					.getCodRiferimentoErogazione());
			erogazioneDTO.setNoteErogazione(erogazioneVO.getNoteErogazione());
			esito.setErogazione(erogazioneDTO);

		 
		return esito;
	}

	public EsitoErogazioneDTO modificaErogazione(Long idUtente,
			String identitaDigitale, Long idProgetto, ErogazioneDTO erogazione)
			throws CSIException, SystemException, UnrecoverableException,
			ErogazioneException {
		 
		EsitoErogazioneDTO esito = new EsitoErogazioneDTO();
	 

			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto", "erogazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto, erogazione);

			if (isTotaleErogatoSuperioreImportoAgevolato(idProgetto, erogazione)) {
				throw new ErogazioneException(
						ERRORE_SOMMA_QUOTE_EROGATE_SUPERIORE_A_IMPORTO_AGEVOLATO);
			}

			BigDecimal importoErogazioni = getImportoErogazioniPerModalitaAgevolazione(
					idProgetto, erogazione);
			try {
				if (NumberUtil.compare(importoErogazioni, contoEconomicoManager
						.getQuotaImportoAgevolato(new BigDecimal(idProgetto),
								erogazione.getCodModalitaAgevolazione())) > 0) {
					throw new ErogazioneException(
							ErrorConstants.ERRORE_SOMMA_EROGAZIONI_PER_MOD_AGEVOLAZ_SUPERIORE_IMPORTO_AGEVOLATO_CONTO_ECONOMICO);
				}
			} catch (ContoEconomicoNonTrovatoException ex) {
				throw new UnrecoverableException(ex.getMessage(), ex);
			}
			PbandiTErogazioneVO vo = new PbandiTErogazioneVO();
			vo.setIdErogazione(NumberUtil.toBigDecimal(erogazione
					.getIdErogazione()));
			vo.setImportoErogazione(BeanUtil.transformToBigDecimal(erogazione
					.getImportoErogazioneEffettivo()));
			vo.setDtContabile(new java.sql.Date(erogazione.getDataContabile()
					.getTime()));
			vo.setCodRiferimentoErogazione(erogazione.getNumeroRiferimento());
			vo.setNoteErogazione(erogazione.getNoteErogazione());
			vo.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
			vo.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			String codCausale = new StringTokenizer(
					erogazione.getCodCausaleErogazione(), "@").nextToken();
			DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, codCausale);
			vo.setIdCausaleErogazione(NumberUtil.toBigDecimal(decodificaVO
					.getId()));
			decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.MODALITA_EROGAZIONE,
					erogazione.getCodModalitaErogazione());
			vo.setIdModalitaErogazione(NumberUtil.toBigDecimal(decodificaVO
					.getId()));
			decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE,
					erogazione.getCodModalitaAgevolazione());
			vo.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(decodificaVO
					.getId()));
			// }L{ fix aggiornamento DB 31/03/2010
			if (!StringUtil.isEmpty(erogazione.getCodDirezione())) {
				PbandiTEnteCompetenzaVO ente = new PbandiTEnteCompetenzaVO();
				ente.setDescBreveEnte(erogazione.getCodDirezione());
				vo.setIdEnteCompetenza(genericDAO.findSingleWhere(ente)
						.getIdEnteCompetenza());
			}
			try {
				vo.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.updateNullables(vo);
			} catch (Exception ex) {
				throw new UnrecoverableException(ex.getMessage(), ex);
			}
			esito.setEsito(new Boolean(true));
			esito.setMessages(new String[] { MessaggiConstants.MSG_EROGAZIONE_SALVATA });
		 
		return esito;
	}

	public CodiceDescrizioneDTO[] findCausaliErogazione(Long idUtente,
			String identitaDigitale, Long idBandoLinea, Long idFormaGiuridica,
			Long idDimensioneImpresa) throws CSIException, SystemException,
			UnrecoverableException, ErogazioneException {
		 
		List<CodiceDescrizioneDTO> list = new ArrayList<CodiceDescrizioneDTO>();
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBandoLinea" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBandoLinea);
			BandoLineaVO bandoLineaVO = new BandoLineaVO();
			bandoLineaVO.setProgrBandoLineaIntervento(NumberUtil
					.toBigDecimal(idBandoLinea));
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);
			List<PbandiRBandoCausaleErogazVO> l = getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridica(
					idFormaGiuridica, idDimensioneImpresa, bandoLineaVO);

			Iterator<PbandiRBandoCausaleErogazVO> iter = l.iterator();
			while (iter.hasNext()) {
				PbandiRBandoCausaleErogazVO vo = (PbandiRBandoCausaleErogazVO) iter
						.next();
				PbandiDCausaleErogazioneVO bandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
				bandiDCausaleErogazioneVO.setIdCausaleErogazione(vo
						.getIdCausaleErogazione());
				bandiDCausaleErogazioneVO.setAscendentOrder("progrOrdinamento");
				bandiDCausaleErogazioneVO = genericDAO
						.findSingleWhere(bandiDCausaleErogazioneVO);
				CodiceDescrizioneDTO codiceDescrizione = new CodiceDescrizioneDTO();
				BigDecimal percErogazione = vo.getPercErogazione();
				BigDecimal percLimite = vo.getPercLimite();
				if (percErogazione == null)
					percErogazione = new BigDecimal(0);
				if (percLimite == null)
					percLimite = new BigDecimal(0);
				codiceDescrizione.setCodice(bandiDCausaleErogazioneVO
						.getDescBreveCausale()
						+ "@"
						+ percErogazione
						+ "@"
						+ percLimite);
				codiceDescrizione.setDescrizione(bandiDCausaleErogazioneVO
						.getDescCausale());
				list.add(codiceDescrizione);
			}

		 
		return list.toArray(new CodiceDescrizioneDTO[] {});
	}

	private List<PbandiRBandoCausaleErogazVO> getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridica(
			Long idFormaGiuridica, Long idDimensioneImpresa,
			BandoLineaVO bandoLineaVO) {
		PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = null;

		List<PbandiRBandoCausaleErogazVO> l = null;

		if (idFormaGiuridica != null) {
			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
			bandiRBandoCausaleErogazVO.setIdFormaGiuridica(NumberUtil
					.toBigDecimal(idFormaGiuridica));
			bandiRBandoCausaleErogazVO.setAscendentOrder("idCausaleErogazione");
			l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
			if (l == null || l.isEmpty()) {
				bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
				bandiRBandoCausaleErogazVO
						.setIdBando(bandoLineaVO.getIdBando());
				bandiRBandoCausaleErogazVO.setIdDimensioneImpresa(NumberUtil
						.toBigDecimal(idDimensioneImpresa));
				bandiRBandoCausaleErogazVO.setAscendentOrder("idCausaleErogazione");
				l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
				if (l == null || l.isEmpty()) {
					bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
					bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO
							.getIdBando());
					bandiRBandoCausaleErogazVO.setAscendentOrder("idCausaleErogazione");
					l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
				}
			}
		} else {
			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
			bandiRBandoCausaleErogazVO.setAscendentOrder("idCausaleErogazione");
			l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
		}
		return l;
	}

	private PbandiRBandoCausaleErogazVO getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridicaECausale(
			Long idFormaGiuridica, Long idDimensioneImpresa,
			BandoLineaVO bando, Long idCausaleErogazione) {

		PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = null;

		List<PbandiRBandoCausaleErogazVO> l = new ArrayList<PbandiRBandoCausaleErogazVO>();
		PbandiDCausaleErogazioneVO pBandiDCausaliErogazioneVO = new PbandiDCausaleErogazioneVO();
		pBandiDCausaliErogazioneVO.setIdCausaleErogazione(NumberUtil
				.toBigDecimal(idCausaleErogazione));
		pBandiDCausaliErogazioneVO = genericDAO
				.findSingleWhere(pBandiDCausaliErogazioneVO);
		if ((pBandiDCausaliErogazioneVO.getDescBreveCausale()
				.equals(Constants.COD_CAUSALE_EROGAZIONE_SALDO_NO_STANDARD))
				|| (pBandiDCausaliErogazioneVO.getDescBreveCausale()
						.equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO_NO_STANDARD))
				|| (pBandiDCausaliErogazioneVO.getDescBreveCausale()
						.equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO_NO_STANDARD))) {

			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil
					.toBigDecimal(idCausaleErogazione));
			bandiRBandoCausaleErogazVO.setPercLimite(null);
			bandiRBandoCausaleErogazVO.setPercErogazione(null);
			l.add(bandiRBandoCausaleErogazVO);
		} else {
			if (idFormaGiuridica != null) {
				bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
				bandiRBandoCausaleErogazVO.setIdBando(bando.getIdBando());
				bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil
						.toBigDecimal(idCausaleErogazione));
				bandiRBandoCausaleErogazVO.setIdFormaGiuridica(NumberUtil
						.toBigDecimal(idFormaGiuridica));
				l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);

				if (l == null || l.isEmpty()) {
					bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
					bandiRBandoCausaleErogazVO.setIdBando(bando.getIdBando());
					bandiRBandoCausaleErogazVO
							.setIdCausaleErogazione(NumberUtil
									.toBigDecimal(idCausaleErogazione));
					bandiRBandoCausaleErogazVO
							.setIdDimensioneImpresa(NumberUtil
									.toBigDecimal(idDimensioneImpresa));
					l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
					if (l == null || l.isEmpty()) {
						bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
						bandiRBandoCausaleErogazVO.setIdBando(bando
								.getIdBando());
						bandiRBandoCausaleErogazVO
								.setIdCausaleErogazione(NumberUtil
										.toBigDecimal(idCausaleErogazione));
						l = genericDAO
								.findListWhere(bandiRBandoCausaleErogazVO);

					}
				}
			} else {
				bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
				bandiRBandoCausaleErogazVO.setIdBando(bando.getIdBando());
				bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil
						.toBigDecimal(idCausaleErogazione));
				l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
			}
		}
		return l.get(0);
	}

	private boolean isTotaleErogatoSuperioreImportoAgevolato(Long idProgetto,
			ErogazioneDTO erogazione) {
		boolean ret = false;
		BigDecimal importoTotaleAgevolato = NumberUtil
				.createScaledBigDecimal(erogazione.getSpesaProgetto()
						.getImportoAmmessoContributo());
		BigDecimal sommaTotaliImportiErogazioni = new BigDecimal(0);

		PbandiTErogazioneVO erogazioneVO = new PbandiTErogazioneVO();
		erogazioneVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		List<PbandiTErogazioneVO> erogazioni = genericDAO
				.findListWhere(erogazioneVO);

		/*
		 * Calcolo il totale dei recuperi e lo agguingo all' importo totale
		 * agevolato
		 */
		BigDecimal totaleRecuperi = getTotaleRecuperi(idProgetto);

		importoTotaleAgevolato = NumberUtil.sum(importoTotaleAgevolato,
				totaleRecuperi);

		if (erogazioni != null) {
			Iterator<PbandiTErogazioneVO> iter = erogazioni.iterator();
			while (iter.hasNext()) {
				PbandiTErogazioneVO vo = (PbandiTErogazioneVO) iter.next();
				if (vo.getIdErogazione().intValue() == erogazione
						.getIdErogazione().intValue())
					sommaTotaliImportiErogazioni = NumberUtil.sum(
							sommaTotaliImportiErogazioni, NumberUtil
									.createScaledBigDecimal(erogazione
											.getImportoErogazioneEffettivo()));

				else
					sommaTotaliImportiErogazioni = NumberUtil.sum(
							sommaTotaliImportiErogazioni, NumberUtil
									.createScaledBigDecimal(vo
											.getImportoErogazione()));

			}
		}
		if (NumberUtil.compare(sommaTotaliImportiErogazioni,
				importoTotaleAgevolato) > 0) {
			ret = true;
		}

		return ret;
	}

	public EsitoOperazioneAssociazioneDTO associaPagamentiQuietanzati(
			Long idUtente, String identitaDigitale, Long[] idPagamenti,
			Long idErogazione) throws CSIException, SystemException,
			UnrecoverableException, ErogazioneException {
		 
		EsitoOperazioneAssociazioneDTO esito = new EsitoOperazioneAssociazioneDTO();
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idErogazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idErogazione);
		ValidatorInput.verifyArrayNotEmpty("idPagamenti", idPagamenti);
		boolean ret = pbandiErogazioneDAO
				.aggiornaAssociazioniPagamentiQuietanzati(idErogazione,
						idPagamenti);
		if (ret) {
			esito.setEsito(new Boolean(true));
			esito.setMessages(new String[] { MessaggiConstants.MSG_ASSOCIAZIONE_CREATA });
		}
		 
		return esito;
	}

	public PagamentoDTO[] findPagamentiQuietanzati(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException, ErogazioneException {
		 
		List<PagamentoDTO> listPagamentiDTO = new ArrayList<PagamentoDTO>();
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);

			DocumentoDiSpesaDTO documentoDiSpesa = new DocumentoDiSpesaDTO();
			documentoDiSpesa.setIdProgetto(idProgetto);
			documentoDiSpesa.setIsGestitiNelProgetto(new Boolean(true));
			List<DocumentoDiSpesaVO> list = pbandiDocumentiDiSpesaDAO
					.findDocumentiDiSpesa(documentoDiSpesa, null);
			Iterator<DocumentoDiSpesaVO> iter = list.iterator();
			while (iter.hasNext()) {
				DocumentoDiSpesaVO obj = (DocumentoDiSpesaVO) iter.next();
				List<PagamentoQuotePartiVO> listPagamenti = pbandipagamentiDAO
						.findPagamentiQuietanzatiNonAssociati(
								obj.getIdDocumentoDiSpesa(), idProgetto);
				Iterator<PagamentoQuotePartiVO> iterPagamenti = listPagamenti
						.iterator();
				while (iterPagamenti.hasNext()) {
					PagamentoQuotePartiVO objP = (PagamentoQuotePartiVO) iterPagamenti
							.next();
					PagamentoDTO dto = new PagamentoDTO();
					dto.setIdPagamento(NumberUtil.toLong(objP.getIdPagamento()));
					if (objP.getDtPagamento() != null)
						dto.setDataContabile(objP.getDtPagamento());
					else
						dto.setDataContabile(objP.getDtValuta());
					dto.setImportoQuietanzato(NumberUtil.toDouble(objP
							.getImportoPagamento()));
					dto.setNumeroDocumento(objP.getNumeroDocumentoDiSpesa());
					/*
					 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento.
					dto.setStatoValidazione(objP.getDescStatoValidazioneSpesa());
					*/
					dto.setDataDocumento(objP.getDtEmissioneDocumentoDiSpesa());
					listPagamentiDTO.add(dto);
				}
			}

	 
		if (!listPagamentiDTO.isEmpty())
			return listPagamentiDTO.toArray(new PagamentoDTO[] {});
		else
			return null;

	}

	public EsitoErogazioneDTO eliminaErogazione(Long idUtente,
			String identitaDigitale, Long idErogazione) throws CSIException,
			SystemException, UnrecoverableException, ErogazioneException {
		 
		EsitoErogazioneDTO esito = new EsitoErogazioneDTO();
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idErogazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idErogazione);
			PbandiTErogazioneVO pbandiTErogazioneVO = new PbandiTErogazioneVO();
			pbandiTErogazioneVO.setIdErogazione(NumberUtil
					.toBigDecimal(idErogazione));
			try {
				genericDAO.delete(pbandiTErogazioneVO);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new ErogazioneException(
						ErrorConstants.EROGAZIONE_NON_ELIMINABILE);
			}
			esito.setEsito(new Boolean(true));
			esito.setMessages(new String[] { MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO });

		 
		return esito;
	}

	public PbandiErogazioneDAOImpl getPbandiErogazioneDAO() {
		return pbandiErogazioneDAO;
	}

	public void setPbandiErogazioneDAO(
			PbandiErogazioneDAOImpl pbandiErogazioneDAO) {
		this.pbandiErogazioneDAO = pbandiErogazioneDAO;
	}

	public void setPbandipagamentiDAO(PbandiPagamentiDAOImpl pbandipagamentiDAO) {
		this.pbandipagamentiDAO = pbandipagamentiDAO;
	}

	public PbandiPagamentiDAOImpl getPbandipagamentiDAO() {
		return pbandipagamentiDAO;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public CodiceDescrizioneDTO[] findModalitaAgevolazionePerContoEconomico(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ErogazioneException {

		List<CodiceDescrizioneDTO> listCD = new ArrayList<CodiceDescrizioneDTO>();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);
			ModalitaDiAgevolazioneContoEconomicoVO modalitaDiAgevolazioneContoEconomicoVO = new ModalitaDiAgevolazioneContoEconomicoVO();
			modalitaDiAgevolazioneContoEconomicoVO
					.setFlagLvlprj(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
			modalitaDiAgevolazioneContoEconomicoVO
					.setIdContoEconomico(contoEconomicoManager
							.getIdContoMaster(new BigDecimal(idProgetto)));
			List<ModalitaDiAgevolazioneContoEconomicoVO> list = genericDAO
					.findListWhere(modalitaDiAgevolazioneContoEconomicoVO);

			if (list != null) {
				Iterator<ModalitaDiAgevolazioneContoEconomicoVO> iter = list
						.iterator();
				while (iter.hasNext()) {
					ModalitaDiAgevolazioneContoEconomicoVO vo = iter.next();
					CodiceDescrizioneDTO cd = new CodiceDescrizioneDTO();
					cd.setCodice(vo.getDescBreveModalitaAgevolaz());
					cd.setDescrizione(vo.getDescModalitaAgevolazione());
					listCD.add(cd);
				}
			}

		} catch (ContoEconomicoNonTrovatoException e) {
			// logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}

		if (!listCD.isEmpty())
			return listCD.toArray(new CodiceDescrizioneDTO[] {});
		else
			return null;

	}

	public ErogazioneDTO findDettagliAggiuntiviErogazione(Long idUtente,
			String identitaDigitale, ErogazioneDTO erogazione)
			throws CSIException, SystemException, UnrecoverableException,
			ErogazioneException {
		StringTokenizer st = new StringTokenizer(
				erogazione.getCodCausaleErogazione(), "@");
		String codCausale = st.nextToken();

		PbandiDCausaleErogazioneVO causaleErogazione = new PbandiDCausaleErogazioneVO();
		causaleErogazione.setDescBreveCausale(codCausale);
		PbandiDModalitaAgevolazioneVO modalitaAgevolazione = new PbandiDModalitaAgevolazioneVO();
		modalitaAgevolazione.setDescBreveModalitaAgevolaz(erogazione
				.getCodModalitaAgevolazione());
		PbandiDModalitaErogazioneVO modalitaErogazione = new PbandiDModalitaErogazioneVO();
		modalitaErogazione.setDescBreveModalitaErogazione(erogazione
				.getCodModalitaErogazione());
		PbandiTEnteCompetenzaVO enteCompetenzaDirezione = new PbandiTEnteCompetenzaVO();
		enteCompetenzaDirezione.setDescBreveEnte(erogazione.getCodDirezione());

		erogazione.setDescrizioneCausaleErogazione(genericDAO.findSingleWhere(
				causaleErogazione).getDescCausale());
		erogazione.setDescModalitaAgevolazione(genericDAO.findSingleWhere(
				modalitaAgevolazione).getDescModalitaAgevolazione());
		erogazione.setDescTipoDirezione(genericDAO.findSingleWhere(
				enteCompetenzaDirezione).getDescEnte());
		erogazione.setDescModalitaErogazione(genericDAO.findSingleWhere(
				modalitaErogazione).getDescModalitaErogazione());

		return erogazione;
	}

	private BigDecimal getTotaleRecuperiPerModalita(Long idProgetto,
			Long idModalitaAgevolazione) {
		PbandiTRecuperoVO recuperoVO = new PbandiTRecuperoVO();
		BigDecimal totaleRecuperi = new BigDecimal(0);
		recuperoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		recuperoVO.setIdModalitaAgevolazione(NumberUtil
				.toBigDecimal(idModalitaAgevolazione));
		List<PbandiTRecuperoVO> listRecuperi = genericDAO
				.findListWhere(recuperoVO);
		if (listRecuperi != null) {
			for (PbandiTRecuperoVO vo : listRecuperi) {
				totaleRecuperi = NumberUtil.sum(totaleRecuperi,
						vo.getImportoRecupero());
			}
		}
		return totaleRecuperi;
	}

	private BigDecimal getTotaleRecuperi(Long idProgetto) {
		PbandiTRecuperoVO recuperoVO = new PbandiTRecuperoVO();
		BigDecimal totaleRecuperi = new BigDecimal(0);
		recuperoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		List<PbandiTRecuperoVO> listRecuperi = genericDAO
				.findListWhere(recuperoVO);
		if (listRecuperi != null) {
			for (PbandiTRecuperoVO vo : listRecuperi) {
				totaleRecuperi = NumberUtil.sum(totaleRecuperi,
						vo.getImportoRecupero());
			}
		}
		return totaleRecuperi;
	}

	 

	private PbandiTProgettoVO aggiornaStatoProgetto(Long idUtente,Long idProgetto) throws Exception {
		PbandiTProgettoVO progetto = new PbandiTProgettoVO();
		progetto.setIdProgetto(new BigDecimal(idProgetto));
		progetto.setIdStatoProgetto(decodificheManager.decodeDescBreve(
				PbandiDStatoProgettoVO.class, Constants.STATO_PROGETTO_RINUNCIA_BENEFICIARIO));
		progetto.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(progetto);

		progetto = new PbandiTProgettoVO();
		progetto.setIdProgetto(new BigDecimal(idProgetto));
		progetto = genericDAO.findSingleWhere(progetto);

		return progetto;
	}

	private DichiarazioneDiRinunciaReportDTO getDichiarazioneRinunciaPerReport(
			DichiarazioneDiRinunciaDTO dichiarazione) {
		Long idProgetto = dichiarazione.getIdProgetto();
		Long idRappresentanteLegale = dichiarazione.getIdRappresentanteLegale();

		DichiarazioneDiRinunciaReportDTO result = beanUtil.transform(
				dichiarazione, DichiarazioneDiRinunciaReportDTO.class);

		RappresentanteLegaleDTO rappresentanteLegale = beanUtil.transform(
				soggettoManager.findRappresentantiLegali(idProgetto,
						idRappresentanteLegale).get(0),
				RappresentanteLegaleDTO.class);
		result.setRappresentanteLegale(rappresentanteLegale);

		PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
		progettoVO.setIdProgetto(beanUtil.transform(idProgetto,
				BigDecimal.class));
		progettoVO = genericDAO.findSingleWhere(progettoVO);
		result.setProgetto(beanUtil.transform(progettoVO, ProgettoDTO.class,
				new HashMap<String, String>() {
					{
						put("titoloProgetto", "titoloProgetto");
						put("codiceVisualizzato", "codiceProgetto");
						put("cup", "cup");
					}
				}));

		BeneficiarioProgettoVO beneficiarioVO = new BeneficiarioProgettoVO();
		beneficiarioVO.setIdProgetto(beanUtil.transform(idProgetto,
				BigDecimal.class));
		BeneficiarioDTO beneficiario = beanUtil.transform(genericDAO
				.findSingleWhere(Condition.filterBy(beneficiarioVO).and(
						Condition.validOnly(BeneficiarioProgettoVO.class))),
				BeneficiarioDTO.class, new HashMap<String, String>() {
					{
						put("idSoggetto", "idBeneficiario");
						put("codiceFiscaleSoggetto", "codiceFiscale");
						put("denominazioneBeneficiario", "denominazione");
					}
				});
		result.setBeneficiario(beneficiario);

		EnteDiAppartenenzaVO enteVO = new EnteDiAppartenenzaVO();
		enteVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
		enteVO.setDescBreveRuoloEnte(Constants.CODICE_RUOLO_ENTE_DESTINATARIO);
		enteVO = genericDAO.findSingleWhere(enteVO);

		result.setEnte(beanUtil.transform(enteVO, EnteAppartenenzaDTO.class,
				new HashMap<String, String>() {
					{
						put("cap", "cap");
						put("descComune", "comune");
						put("descEnte", "descEnte");
						put("indirizzo", "indirizzo");
						put("siglaProvincia", "siglaProvincia");
					}
				}));

		return result;
	}

	public EsitoScaricaDichiarazioneDiRinuncia scaricaDichiarazioneDiRinuncia(
			Long idUtente, String identitaDigitale, String uuidNodoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			ErogazioneException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"uuidNodoIndex" };
			logger.info("scaricaDichiarazioneDiRinuncia uuidNodoIndex: "+uuidNodoIndex);
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, uuidNodoIndex);
			/*
			 * Recupero le informazioni relative al documento index selezionato
			 */
			PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();
			vo.setUuidNodo(uuidNodoIndex);
			PbandiTDocumentoIndexVO documentoIndexVO = genericDAO
					.findSingleWhere(vo);

			byte[] file = indexDAO.recuperaContenuto(documentoIndexVO
					.getUuidNodo());
			EsitoScaricaDichiarazioneDiRinuncia esito = new EsitoScaricaDichiarazioneDiRinuncia();
			esito.setPdfBytes(file);
			esito.setNomeFile(documentoIndexVO.getNomeFile());
			esito.setEsito(true);
			return esito;
		} catch (Exception e) {
			ErogazioneException ce = new ErogazioneException(
					"Errore scarico file da Index", e);
			ce.initCause(e);
			throw ce;
		}  
	}

	
	
	public DichiarazioneDiRinunciaDTO inviaDichiarazioneDiRinuncia(
			Long idUtente, String identitaDigitale,
			DichiarazioneDiRinunciaDTO dichiarazione,
			IstanzaAttivitaDTO istanzaAttivita) throws CSIException,
			SystemException, UnrecoverableException, ErogazioneException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"dichiarazione", "dichiarazione.giorniRinuncia",
					"dichiarazione.idProgetto",
					"dichiarazione.idRappresentanteLegale",
					"dichiarazione.importoDaRestituire",
					"dichiarazione.motivoRinuncia" };
			Long idProgetto = dichiarazione.getIdProgetto();
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, dichiarazione,
					dichiarazione.getGiorniRinuncia(), idProgetto,
					dichiarazione.getIdRappresentanteLegale(),
					dichiarazione.getImportoDaRestituire(),
					dichiarazione.getMotivoRinuncia());

			if (dichiarazione.getUuidNodoIndex() != null) {
				logger.warn("La dichiarazione di rinuncia appena inviata ha gi� un UUID di INDEX:"
						+ dichiarazione.getUuidNodoIndex());
			}

			PbandiTRinunciaVO rinunciaVO = beanUtil.transform(dichiarazione,
					PbandiTRinunciaVO.class, new HashMap<String, String>() {
						{
							put("idProgetto", "idProgetto");
							put("dataRinuncia", "dtRinuncia");
							put("motivoRinuncia", "motivoRinuncia");
							put("giorniRinuncia", "giorniRinuncia");
							put("importoDaRestituire", "importoDaRestituire");
						}
					});
		
			PbandiTSoggettoVO beneficiario = new PbandiTSoggettoVO();
			beneficiario.setIdSoggetto(new BigDecimal(progettoManager
					.getIdBeneficiario(idProgetto)));
			beneficiario = genericDAO.findSingleWhere(beneficiario);

			PbandiTProgettoVO progetto = aggiornaStatoProgetto(idUtente ,idProgetto);

			rinunciaVO.setIdUtenteIns(new BigDecimal(idUtente));
			rinunciaVO = genericDAO.insert(rinunciaVO);
			
			/*
			 * VN: Cambio lo stato del conto economico master
			 * solo se il suo stato e' IPG
			 */
			ContoEconomicoDTO ce = contoEconomicoManager.findContoEconomicoMaster(NumberUtil.toBigDecimal(idProgetto));
			if (STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA.equalsIgnoreCase(ce.getDescBreveStatoContoEconom())) {
				contoEconomicoManager.aggiornaStatoContoEconomico(NumberUtil.toLong(ce.getIdContoEconomico()), 
						STATO_CONTO_ECONOMICO_APPROVATO, NumberUtil.toBigDecimal(idUtente));
			}

			String nomeFile = Constants.NOME_FILE_RINUNCIA_PREFIX + "_"
					+ rinunciaVO.getIdRinuncia() + "_"
					+ DateUtil.utilToSqlDate(DateUtil.getDataOdierna())
					+ ".pdf";



			PbandiTProgettoVO progettoVO = progettoManager.getProgetto(idProgetto.longValue());
			getPopolaTemplateManager().addChiave(PopolaTemplateManager.CHIAVE_PROGETTO, beanUtil.transform(progettoVO,
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class));
			
			
			getPopolaTemplateManager().setTipoModello(PopolaTemplateManager.MODELLO_COMUNICAZIONE_DI_RINUNCIA);
			getPopolaTemplateManager().addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO,  idProgetto.longValue());
			
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentantiLegali = soggettoManager.findRappresentantiLegali(idProgetto, 
					dichiarazione.getIdRappresentanteLegale());
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO = rappresentantiLegali.get(0);
			
			if(dichiarazione.getIdDelegato()!=null){
				logger.info("il delegato non � NULL "+dichiarazione.getIdDelegato()+", lo metto al posto del rapp legale");
				 DelegatoVO delegatoVO = new DelegatoVO();
				 delegatoVO.setIdSoggetto(dichiarazione.getIdDelegato());
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
			
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegaleDTO);
			
			BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
			filtroBeneficiario.setIdProgetto(new BigDecimal(idProgetto));
			filtroBeneficiario.setIdSoggetto(new BigDecimal(progettoManager
					.getIdBeneficiario(idProgetto)));
			
			BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);
			popolaTemplateManager.addChiave(popolaTemplateManager.CHIAVE_BENEFICIARIO, beanUtil.transform(beneficiarioVO,
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO.class));
			
			SedeVO sedeIntervento = null;
			try {
				 sedeIntervento = getSedeManager().findSedeIntervento(idProgetto, beneficiarioVO.getIdSoggetto().longValue());
			} catch (Exception e) {
				FineProgettoException dse = new FineProgettoException("Errore durante la ricerca della sede di intervento");
				throw dse;
			}
			/*
			 * Sede intervento
			 */
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,beanUtil.transform(sedeIntervento,SedeDTO.class));
			
			
			/*
			 * Dichiarazione di rinuncia per report
			 */
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiRinunciaDTO rinuncia = beanUtil.transform(dichiarazione, 
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiRinunciaDTO.class);
			rinuncia.setIdDichiarazione(beanUtil.transform(rinunciaVO.getIdRinuncia(), Long.class));
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_COMUNICAZIONE_DI_RINUNCIA,rinuncia);
			
			
			// 11/12/15 added footer 
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,""+rinunciaVO.getIdRinuncia());
		
			
			
			Modello modello = getPopolaTemplateManager().popolaModello(idProgetto.longValue());
			

			
			long startFillReport=System.currentTimeMillis();
			JasperPrint jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(),
					modello.getMasterParameters(),new JREmptyDataSource());
			logger.info("\n\n\n########################\nJasperFillManager.fillReport eseguito in "+(System.currentTimeMillis()-startFillReport)+" ms\n");
			
			long startExport=System.currentTimeMillis();
			byte[] bytesPdf = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("\n\n\n########################\nJasperPrint esportato to pdf in "+(System.currentTimeMillis()-startExport)+" ms\n");
			
			// NEW DYNAMIC REPORT
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
							
			Node nodoCreato = indexDAO.creaContentDichiarazioneRinuncia(
					idUtente, nomeFile, rinunciaVO, beneficiario, progetto,
					bytesPdf);
			String shaHex = null;
			if(bytesPdf!=null)
			  shaHex = DigestUtils.shaHex(bytesPdf);
			
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentoManager
					.salvaInfoNodoIndexSuDb(idUtente,
							nodoCreato,
							nomeFile,
							beanUtil.transform(rinunciaVO.getIdRinuncia(),Long.class),
							dichiarazione.getIdRappresentanteLegale(),dichiarazione.getIdDelegato(),
							beanUtil.transform(rinunciaVO.getIdProgetto(),Long.class),
							it.csi.pbandi.pbservizit.pbandiutil.common.Constants.TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA,
							PbandiTRinunciaVO.class, null,shaHex);
			dichiarazione.setUuidNodoIndex(nodoCreato.getUid());
			dichiarazione.setIdDocumentoIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
			dichiarazione.setFileName(nomeFile);
			
		/*    logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+idProgetto);
			Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, idProgetto);
			logger.warn("processo: "+processo);
			if(processo!=null){*/
				logger.warn("\n\n\n############################ NEOFLUX ENDATTIVITA COMUNIC_RINUNCIA ##############################\n");
				neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto,Task.COMUNIC_RINUNCIA);
				logger.warn("############################ NEOFLUX UNLOCK COMUNIC_RINUNCIA ##############################\n\n\n\n");
				
//				${data_invio_rinuncia}	DATA_INVIO_RINUNCIA
				List<MetaDataVO>metaDatas=new ArrayList<MetaDataVO>();
				String descrBreveTemplateNotifica=Notification.NOTIFICA_COMUNICAZIONE_RINUNCIA;
				MetaDataVO metadata1=new MetaDataVO(); 
				metadata1.setNome(Notification.DATA_INVIO_RINUNCIA);
				metadata1.setValore( DateUtil.getDate(new Date()));
				metaDatas.add(metadata1);

				
				logger.info("calling genericDAO.callProcedure().putNotificationMetadata for COMUNIC_RINUNCIA....");
				genericDAO.callProcedure().putNotificationMetadata(metaDatas);
				
				logger.info("calling genericDAO.callProcedure().sendNotificationMessage for COMUNIC_RINUNCIA....");
				genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.ISTRUTTORE,idUtente);
				logger.info("genericDAO.callProcedure().sendNotificationMessage for COMUNIC_RINUNCIA OK");
			/*}else{
				// +flux+ 
				logger.warn("\n\n############################ OLDFLUX ##############################\ncalling getProcessManager().inviaDichiarazioneDiRinuncia idProgetto :"+idProgetto);
				getProcessManager().inviaDichiarazioneDiRinuncia(idUtente,identitaDigitale);
				logger.warn("inviaDichiarazioneDiRinuncia OK\n############################ OLDFLUX ##############################\n\n\n\n");
			}*/
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ErogazioneException(ERRORE_SERVER);
		} 
		return dichiarazione;
	}


	public java.lang.Boolean isRinunciaPresente(
			Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException 
			  {
		String nameParameter[] = { "idProgetto" };
		ValidatorInput.verifyBeansNotEmpty(nameParameter, idProgetto);

		PbandiTRinunciaVO query = new PbandiTRinunciaVO();
		query.setIdProgetto(new BigDecimal(idProgetto));
		java.lang.Boolean response = genericDAO.findListWhere(query).size() > 0;

		return response;
	}


}
