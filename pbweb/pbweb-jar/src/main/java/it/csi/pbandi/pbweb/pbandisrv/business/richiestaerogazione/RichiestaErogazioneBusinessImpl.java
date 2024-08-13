/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.richiestaerogazione;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.RappresentanteLegaleManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.TipoAllegatiManager;
import it.csi.pbandi.pbweb.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbweb.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.BeneficiarioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EnteAppartenenzaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EsitoReportRichiestaErogazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EsitoRichiestaErogazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.ProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneReportDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbweb.pbandisrv.exception.richiestaerogazione.RichiestaErogazioneException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiErogazioneDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiPagamentiDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AllegatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DelegatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDBancaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCausaleErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMacroSezioneModuloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoCausaleErogazVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAgenziaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEstremiBancariVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRichiestaErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.dichiarazionedispesa.DichiarazioneDiSpesaSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.richiestaerogazione.RichiestaErogazioneSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;
import it.doqui.index.ecmengine.dto.Node;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class RichiestaErogazioneBusinessImpl extends BusinessImpl implements
		RichiestaErogazioneSrv {


	private ContoEconomicoManager contoEconomicoManager;
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	private DocumentoManager documentoManager;
	private GenericDAO genericDAO;
	private IndexDAO indexDAO;
	private NeofluxSrv neofluxBusiness;
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	private PbandiErogazioneDAOImpl pbandiErogazioneDAO;
	private PopolaTemplateManager popolaTemplateManager;
	private ProgettoManager progettoManager;
	private RappresentanteLegaleManager rappresentanteLegaleManager;
	private ReportManager reportManager;
	private SoggettoManager soggettoManager;
	private TipoAllegatiManager tipoAllegatiManager;

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}
	
	public NeofluxSrv getNeofluxBusiness() {
		return neofluxBusiness;
	}

	public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
		this.neofluxBusiness = neofluxBusiness;
	}
	
 
	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}


	public void setDichiarazioneDiSpesaManager(
			DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager) {
		this.dichiarazioneDiSpesaManager = dichiarazioneDiSpesaManager;
	}

	public DichiarazioneDiSpesaManager getDichiarazioneDiSpesaManager() {
		return dichiarazioneDiSpesaManager;
	}

	public void setPopolaTemplateManager(PopolaTemplateManager popolaTemplateManager) {
		this.popolaTemplateManager = popolaTemplateManager;
	}

	public PopolaTemplateManager getPopolaTemplateManager() {
		return popolaTemplateManager;
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

	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}

	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public PbandiErogazioneDAOImpl getPbandiErogazioneDAO() {
		return pbandiErogazioneDAO;
	}

	public void setPbandiErogazioneDAO(
			PbandiErogazioneDAOImpl pbandiErogazioneDAO) {
		this.pbandiErogazioneDAO = pbandiErogazioneDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public PbandiPagamentiDAOImpl getPbandipagamentiDAO() {
		return pbandipagamentiDAO;
	}

	public void setPbandipagamentiDAO(PbandiPagamentiDAOImpl pbandipagamentiDAO) {
		this.pbandipagamentiDAO = pbandipagamentiDAO;
	}

	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public void setPbandiDocumentiDiSpesaDAO(
			PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public TipoAllegatiManager getTipoAllegatiManager() {
		return tipoAllegatiManager;
	}

	public void setTipoAllegatiManager(TipoAllegatiManager tipoAllegatiManager) {
		this.tipoAllegatiManager = tipoAllegatiManager;
	}

	public EsitoRichiestaErogazioneDTO findDatiRiepilogoRichiestaErogazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			String codCausaleErogazione, Long idDimensioneImpresa,
			Long idFormaGiuridica, Long idBandoLinea, Long idSoggetto)
			throws CSIException, SystemException, UnrecoverableException,
			RichiestaErogazioneException {
		EsitoRichiestaErogazioneDTO esito = new EsitoRichiestaErogazioneDTO();
		List<String> messages = new ArrayList<String>();
		RichiestaErogazioneDTO richiestaErogazioneDTO = new RichiestaErogazioneDTO();
		SpesaProgettoDTO spesaProgetto = new SpesaProgettoDTO();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProgetto", "codCausaleErogazione", "idBandoLinea",
					"idSoggetto" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto, codCausaleErogazione,
					idBandoLinea, idSoggetto);

			BandoLineaVO bandoLineaVO = new BandoLineaVO();
			bandoLineaVO.setProgrBandoLineaIntervento(new BigDecimal(
					idBandoLinea));
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);
			PbandiDCausaleErogazioneVO pbandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
			pbandiDCausaleErogazioneVO
					.setDescBreveCausale(codCausaleErogazione);
			pbandiDCausaleErogazioneVO = genericDAO
					.findSingleWhere(pbandiDCausaleErogazioneVO);
			BigDecimal idCausaleErogazione = pbandiDCausaleErogazioneVO
					.getIdCausaleErogazione();
			PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = getBandiRBandoCausaleErogazioneVO(
					idCausaleErogazione, idDimensioneImpresa, idFormaGiuridica,
					bandoLineaVO);

			if (bandiRBandoCausaleErogazVO == null) {
				// ECCEZIONE CON UN MESSAGGIO D'ERRORE
				throw new RichiestaErogazioneException(
						ERRORE_BANDO_NON_GESTISCE_RICHIESTA_EROGAZIONE);

			}

			// DATI DELLA SPESA PROGETTO
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(new BigDecimal(idProgetto));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			Double totaleSpesaAmmessa = contoEconomicoManager
					.getTotaleSpesaAmmmessaInRendicontazioneDouble(new BigDecimal(
							idProgetto));
			spesaProgetto.setImportoAmmessoContributo(contoEconomicoManager
					.findImportoAgevolato(idProgetto));
			if (spesaProgetto.getImportoAmmessoContributo() == null) {
				throw new RichiestaErogazioneException(
						ERRORE_EROGAZIONE_IMPORTO_AGEVOLATO_NULLO);
			}

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

					/*
					 * FIX PBandi-2351
					 */
					Double importoValidato = pbandipagamentiDAO
							.getImportoValidato(idProgetto,NumberUtil.toLong(objPagamento
									.getIdPagamento()));
					if (importoValidato != null) {
						sommaImportiValidati += importoValidato.doubleValue();
					}

				}

				if (sommaImportiQuietanzati > 0) {
					spesaProgetto
							.setTotaleSpesaSostenuta(sommaImportiQuietanzati);
					BigDecimal div = NumberUtil.divide(new BigDecimal(
							sommaImportiQuietanzati), new BigDecimal(
							totaleSpesaAmmessa));
					BigDecimal ret = NumberUtil.multiply(div, new BigDecimal(
							100));
					spesaProgetto.setAvanzamentoSpesaSostenuta(NumberUtil
							.toDouble(ret));
				}
				if (sommaImportiValidati > 0) {
					spesaProgetto.setTotaleSpesaValidata(sommaImportiValidati);
					BigDecimal div = NumberUtil.divide(new BigDecimal(
							sommaImportiValidati), new BigDecimal(
							totaleSpesaAmmessa));
					BigDecimal ret = NumberUtil.multiply(div, new BigDecimal(
							100));
					spesaProgetto.setAvanzamentoSpesaValidata(NumberUtil
							.toDouble(ret));
				}
			}
			spesaProgetto.setAvanzamentoSpesaPrevistaBando(NumberUtil
					.toRoundBigDecimal(bandiRBandoCausaleErogazVO
							.getPercSogliaSpesaQuietanzata()));
			Double importoSpesaDaRaggiungere = 0D;
			if (totaleSpesaAmmessa != null
					&& spesaProgetto.getAvanzamentoSpesaPrevistaBando()
							.doubleValue() > 0) {
				// }L{ PBANDI-1064
				// importoSpesaDaRaggiungere =
				// NumberUtil.toRoundDouble(spesaProgetto.getTotaleSpesaSostenuta().doubleValue()*spesaProgetto.getAvanzamentoSpesaPrevistaBando().doubleValue()/100);
				importoSpesaDaRaggiungere = NumberUtil
						.toRoundDouble(totaleSpesaAmmessa
								* spesaProgetto
										.getAvanzamentoSpesaPrevistaBando()
										.doubleValue() / 100);
			}
			spesaProgetto
					.setImportoSpesaDaRaggiungere(importoSpesaDaRaggiungere);
			richiestaErogazioneDTO.setSpesaProgetto(spesaProgetto);

			// DATI FIDEIUSSIONI

			FideiussioneVO[] fideiussioniVO = pbandiErogazioneDAO
					.findFideiussioniAttive(idProgetto);

			ArrayList<FideiussioneVO> fideiussioniFiltrateVO = new ArrayList();
			if (fideiussioniVO != null) {
				for (int i = 0; i < fideiussioniVO.length; i++) {
					FideiussioneVO vo = fideiussioniVO[i];
					if (codCausaleErogazione
							.equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)
							&& vo
									.getDescBreveTipoTrattamento()
									.equals(
											Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO)) {
						fideiussioniFiltrateVO.add(vo);
					} else if (codCausaleErogazione
							.equals(Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)
							&& (vo.getDescBreveTipoTrattamento()
									.equals(Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
						fideiussioniFiltrateVO.add(vo);
					} else if (codCausaleErogazione
							.equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)
							&& (vo.getDescBreveTipoTrattamento()
									.equals(Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
						fideiussioniFiltrateVO.add(vo);
					} else if (codCausaleErogazione
							.equals(Constants.COD_CAUSALE_EROGAZIONE_SALDO)
							&& (vo.getDescBreveTipoTrattamento()
									.equals(Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_TRE))) {
						fideiussioniFiltrateVO.add(vo);
					}
				}
			}

			if (fideiussioniFiltrateVO != null) {
				FideiussioneVO[] fideiussioniValide = fideiussioniFiltrateVO
						.toArray(new FideiussioneVO[] {});
				FideiussioneDTO[] fideiussioniDTO = new FideiussioneDTO[fideiussioniValide.length];
				getBeanUtil().valueCopy(fideiussioniValide, fideiussioniDTO);

				richiestaErogazioneDTO.setFideiussioni(fideiussioniDTO);
			}

			FideiussioneVO[] fideiussioniTipoTrattamentoVO = pbandiErogazioneDAO
					.findFideiussioniAttivePerTipoTrattamento(idProgetto);
			if (fideiussioniTipoTrattamentoVO != null) {
				FideiussioneTipoTrattamentoDTO[] fideiussioniDTO = new FideiussioneTipoTrattamentoDTO[fideiussioniTipoTrattamentoVO.length];
				getBeanUtil().valueCopy(fideiussioniTipoTrattamentoVO,
						fideiussioniDTO);
				richiestaErogazioneDTO
						.setFideiussioniTipoTrattamento(fideiussioniDTO);

			}
			// DATI EROGAZIONE

			PbandiDCausaleErogazioneVO pBandiDCausaliErogazioneVO = new PbandiDCausaleErogazioneVO();
			pBandiDCausaliErogazioneVO
					.setIdCausaleErogazione(idCausaleErogazione);
			pBandiDCausaliErogazioneVO = genericDAO
					.findSingleWhere(pBandiDCausaliErogazioneVO);
			richiestaErogazioneDTO
					.setDescrizioneCausaleErogazione(pBandiDCausaliErogazioneVO
							.getDescCausale());
			richiestaErogazioneDTO
					.setDescBreveCausaleErogazione(codCausaleErogazione);

			Map<String, String> map = new HashMap<String, String>();
			Double importo = null;
			if (bandiRBandoCausaleErogazVO.getPercErogazione() != null
					&& bandiRBandoCausaleErogazVO.getPercErogazione()
							.intValue() > 0) {
				BigDecimal per = NumberUtil.multiply(new BigDecimal(
						spesaProgetto.getImportoAmmessoContributo()),
						bandiRBandoCausaleErogazVO.getPercErogazione());
				BigDecimal ret = NumberUtil.divide(per, new BigDecimal(100));
				importo = NumberUtil.toDouble(ret);

				map.put("percErogazione", "percentualeErogazione");
			} else {
				BigDecimal per = NumberUtil.multiply(new BigDecimal(
						spesaProgetto.getImportoAmmessoContributo()),
						bandiRBandoCausaleErogazVO.getPercLimite());
				BigDecimal ret = NumberUtil.divide(per, new BigDecimal(100));
				importo = NumberUtil.toDouble(ret);
				map.put("percLimite", "percentualeLimite");
			}

			try {
				getBeanUtil().valueCopy(bandiRBandoCausaleErogazVO,
						richiestaErogazioneDTO, map);
			} catch (Exception ex) {
				throw new UnrecoverableException(ex.getMessage());
			}
			richiestaErogazioneDTO.setImportoRichiesto(importo);

					
			
			/*
			 *  NUOVA GESTIONE ALLEGATI la descrizione degli allegati ora va presa cosi:
			 *  Accedere alla tabella di relazione BandoLineaTipoDocSezioneModulo del modello concettuale avendo impostato:
			-	l�identificativo del BandoLineaIntervento
			-	l�identificativo della MacroSezioneModulo uguale a 6 
			-	l�identificativo della DocumentoIndex uguale a 20 per causale erogazione 1
			-	l�identificativo della DocumentoIndex uguale a 18 per causale erogazione 2
			-	l�identificativo della DocumentoIndex uguale a 19 per causale erogazione 3
				ordinando l�elenco per numOrdinamentoMicroSezione
				id_caus= 1 , id_doc_ind= 20
				id_caus= 2 , id_doc_ind= 19
				id_caus= 3 , id_doc_ind= 18

			Accedere alla tabella MicroSezioneModulo con l�identificativo dell�elenco e
			visualizzare il campo contenutoMicroSezione.

			 */
			
			BigDecimal idMacroAllegati = decodificheManager.decodeDescBreve(PbandiDMacroSezioneModuloVO.class,
					GestioneDatiDiDominioSrv.DESC_BREVE_MACRO_SEZ_ALLEGATI);
			
			
			AllegatoVO allegatoVO=new AllegatoVO();
			allegatoVO.setIdMacroSezioneModulo(idMacroAllegati);
			
			BigDecimal idTipoDocIndex=getIdTipoDocIndex(codCausaleErogazione);
			allegatoVO.setIdTipoDocumentoIndex(idTipoDocIndex);
			allegatoVO.setAscendentOrder("numOrdinamentoMicroSezione");
			allegatoVO.setProgrBandoLineaIntervento(new BigDecimal(idBandoLinea));
			
			AllegatoVO[] allegati = genericDAO.findWhere(allegatoVO);
			Map <String,String> mapProps=new HashMap<String,String>();
			mapProps.put("contenutoMicroSezione","descTipoAllegato");
			TipoAllegatoDTO[]tipoAllegati=beanUtil.transform(allegati, TipoAllegatoDTO.class,mapProps);
			
			richiestaErogazioneDTO.setTipoAllegati(tipoAllegati);
			
			// DATI DOCUMENTI ALLEGATI
			// VECCHIA GESTIONE
		/*	PbandiRBandoCausErTipAllVO pbandiRBandoCausErTipAllVO = new PbandiRBandoCausErTipAllVO();
			pbandiRBandoCausErTipAllVO.setIdBando(bandoLineaVO.getIdBando());
			pbandiRBandoCausErTipAllVO
					.setIdCausaleErogazione(idCausaleErogazione);
			List<PbandiRBandoCausErTipAllVO> listAllegati = genericDAO
					.findListWhere(pbandiRBandoCausErTipAllVO);
			if (listAllegati != null && !listAllegati.isEmpty()) {
				ArrayList<TipoAllegatoDTO> tipiAllegato = new ArrayList();
				Iterator iterAllegati = listAllegati.iterator();
				while (iterAllegati.hasNext()) {
					PbandiRBandoCausErTipAllVO vo = (PbandiRBandoCausErTipAllVO) iterAllegati
							.next();
					TipoAllegatoDTO tipoAllegatoDTO = new TipoAllegatoDTO();
					PbandiDTipoAllegatoVO pbandiDTipoAllegatoVO = new PbandiDTipoAllegatoVO();
					pbandiDTipoAllegatoVO.setIdTipoAllegato(vo
							.getIdTipoAllegato());
					pbandiDTipoAllegatoVO = genericDAO
							.findSingleWhere(pbandiDTipoAllegatoVO);
					getBeanUtil().valueCopy(pbandiDTipoAllegatoVO,
							tipoAllegatoDTO);
					tipiAllegato.add(tipoAllegatoDTO);

				}
				richiestaErogazioneDTO.setTipoAllegati(tipiAllegato
						.toArray(new TipoAllegatoDTO[] {}));
			}
			*/

			// DATI ESTREMI BANCARI
			PbandiRSoggettoProgettoVO pbandiRSoggettoProgettoVO = new PbandiRSoggettoProgettoVO();
			pbandiRSoggettoProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
			pbandiRSoggettoProgettoVO.setIdSoggetto(new BigDecimal(idSoggetto));
			pbandiRSoggettoProgettoVO.setDtFineValidita(null);
			pbandiRSoggettoProgettoVO.setIdTipoAnagrafica(new BigDecimal(1));
			PbandiRSoggettoProgettoVO filtro = new PbandiRSoggettoProgettoVO();
			filtro.setIdTipoBeneficiario(new BigDecimal(4));

			List<PbandiRSoggettoProgettoVO> listSoggettoProgetto = genericDAO
					.findListWhere(new AndCondition<PbandiRSoggettoProgettoVO>(
							new FilterCondition<PbandiRSoggettoProgettoVO>(
									pbandiRSoggettoProgettoVO),
							new NotCondition<PbandiRSoggettoProgettoVO>(
									new FilterCondition<PbandiRSoggettoProgettoVO>(
											filtro))));
			if (listSoggettoProgetto != null && !listSoggettoProgetto.isEmpty()) {
				PbandiRSoggettoProgettoVO vo = listSoggettoProgetto.get(0);
				if (vo.getIdEstremiBancari() != null) {
					EstremiBancariDTO estremiBancariDTO = new EstremiBancariDTO();
					PbandiTEstremiBancariVO pbandiTEstremiBancariVO = new PbandiTEstremiBancariVO();
					pbandiTEstremiBancariVO.setIdEstremiBancari(vo
							.getIdEstremiBancari());
					pbandiTEstremiBancariVO = genericDAO
							.findSingleWhere(pbandiTEstremiBancariVO);
					getBeanUtil().valueCopy(pbandiTEstremiBancariVO,
							estremiBancariDTO);
					if (pbandiTEstremiBancariVO.getIdBanca() != null) {
						PbandiDBancaVO pbandiBancaVO = new PbandiDBancaVO();
						pbandiBancaVO.setIdBanca(pbandiTEstremiBancariVO
								.getIdBanca());
						pbandiBancaVO = genericDAO
								.findSingleWhere(pbandiBancaVO);
						estremiBancariDTO.setDescrizioneBanca(pbandiBancaVO
								.getDescBanca());
					}
					if (pbandiTEstremiBancariVO.getIdAgenzia() != null) {
						PbandiTAgenziaVO pbandiTAgenziaVO = new PbandiTAgenziaVO();
						pbandiTAgenziaVO.setIdAgenzia(pbandiTEstremiBancariVO
								.getIdAgenzia());
						pbandiTAgenziaVO = genericDAO
								.findSingleWhere(pbandiTAgenziaVO);
						estremiBancariDTO
								.setDescrizioneAgenzia(pbandiTAgenziaVO
										.getDescAgenzia());

					}

					richiestaErogazioneDTO.setEstremiBancari(estremiBancariDTO);
				}
			}

			// ///////////GESTIONE MESSAGGI D'ERRORE
			if ((spesaProgetto.getTotaleSpesaSostenuta() == null || spesaProgetto
					.getTotaleSpesaSostenuta().doubleValue() == 0)
					&& (spesaProgetto.getAvanzamentoSpesaPrevistaBando() == null || spesaProgetto
							.getAvanzamentoSpesaPrevistaBando() == 0)
					&& (richiestaErogazioneDTO.getFideiussioni() == null || richiestaErogazioneDTO
							.getFideiussioni().length == 0)) {

				messages.add(ERRORE_EROGAZIONE_SPESA_SOSTENUTA_ZERO);
			} else {
				if (NumberUtil.compare(spesaProgetto
						.getAvanzamentoSpesaSostenuta(), spesaProgetto
						.getAvanzamentoSpesaPrevistaBando()) < 0) {
					if (checkSogliaAvanzamentoPerCodCausaleErogazione(
							codCausaleErogazione, messages,
							richiestaErogazioneDTO, spesaProgetto)) {

						if (NumberUtil
								.compare(
										getImportoTotaleFideiussioni(richiestaErogazioneDTO),
										new BigDecimal(richiestaErogazioneDTO
												.getImportoRichiesto())) < 0) {
							messages
									.add(ERRORE_EROGAZIONE_IMPORTO_FIDEIUSSIONE_MINORE_ANTICIPO);
						}
					}
				}
			}

			/*
			 * GESTIONE REGOLA PER EROGAZIONE
			 */
			boolean isRegolaApplicabile = regolaManager
					.isRegolaApplicabileForBandoLinea(
							idBandoLinea,
							RegoleConstants.BR15_ATTIVITA_RICHIESTA_EROGAZIONE_DISPONIBILE);
			esito.setIsRegolaAttiva(isRegolaApplicabile);

			esito.setMessages(messages.toArray(new String[] {}));
			esito.setRichiestaErogazione(richiestaErogazioneDTO);
		} finally {
		}

		return esito;
	}

	

	private boolean checkSogliaAvanzamentoPerCodCausaleErogazione(
			String codCausaleErogazione, List<String> messages,
			RichiestaErogazioneDTO richiestaErogazioneDTO,
			SpesaProgettoDTO spesaProgetto) {
		boolean ret = true;
		if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)) {
			if ((NumberUtil.compare(spesaProgetto
					.getAvanzamentoSpesaSostenuta(), spesaProgetto
					.getAvanzamentoSpesaPrevistaBando()) < 0)
					&& (!existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO))) {
				ret = false;
				messages
						.add(ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
			}
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)) {
			if ((NumberUtil.compare(spesaProgetto
					.getAvanzamentoSpesaSostenuta(), spesaProgetto
					.getAvanzamentoSpesaPrevistaBando()) < 0)
					&& (!existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO) || !existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
				ret = false;
				messages
						.add(ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
			}
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)) {
			if ((NumberUtil.compare(spesaProgetto
					.getAvanzamentoSpesaSostenuta(), spesaProgetto
					.getAvanzamentoSpesaPrevistaBando()) < 0)
					&& (!existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO) || !existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
				ret = false;
				messages
						.add(ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
			}
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_SALDO)) {
			if ((NumberUtil.compare(spesaProgetto
					.getAvanzamentoSpesaSostenuta(), spesaProgetto
					.getAvanzamentoSpesaPrevistaBando()) < 0)
					&& (!existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO)
							|| !existFideiussioniTipoTrattamento(
									richiestaErogazioneDTO,
									Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE) || !existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_TRE))) {
				ret = false;
				messages
						.add(ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
			}
		}
		return ret;
	}

	private boolean existFideiussioniTipoTrattamento(
			RichiestaErogazioneDTO richiestaErogazioneDTO,
			String codTipoFideiussione) {
		FideiussioneTipoTrattamentoDTO[] fideiussioni = richiestaErogazioneDTO
				.getFideiussioniTipoTrattamento();
		boolean ret = false;
		if (fideiussioni != null) {
			for (int i = 0; i < fideiussioni.length; i++) {
				FideiussioneTipoTrattamentoDTO dto = fideiussioni[i];
				if (dto.getDescBreveTipoTrattamento().equals(
						codTipoFideiussione)) {
					ret = true;
				}
			}
		}
		return ret;
	}

	private BigDecimal getImportoTotaleFideiussioni(
			RichiestaErogazioneDTO richiestaErogazioneDTO) {
		FideiussioneTipoTrattamentoDTO[] fideiussioni = richiestaErogazioneDTO
				.getFideiussioniTipoTrattamento();
		BigDecimal importo = new BigDecimal(0);
		if (fideiussioni != null) {
			for (int i = 0; i < fideiussioni.length; i++) {

				FideiussioneTipoTrattamentoDTO dto = fideiussioni[i];
				importo = NumberUtil.sum(importo, new BigDecimal(dto
						.getImporto()));

			}
		}
		return importo;
	}

	private PbandiRBandoCausaleErogazVO getBandiRBandoCausaleErogazioneVO(
			BigDecimal idCausaleErogazione, Long idDimensioneImpresa,
			Long idFormaGiuridica, BandoLineaVO bandoLineaVO) {
		List<PbandiRBandoCausaleErogazVO> l = new ArrayList<PbandiRBandoCausaleErogazVO>();
		PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = null;

		if (idFormaGiuridica != null) {
			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO
					.setIdCausaleErogazione(idCausaleErogazione);
			bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
			bandiRBandoCausaleErogazVO.setIdFormaGiuridica(new BigDecimal(
					idFormaGiuridica));
			l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
			if (l == null || l.isEmpty()) {
				if (idDimensioneImpresa != null) {
					bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
					bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO
							.getIdBando());
					bandiRBandoCausaleErogazVO
							.setIdCausaleErogazione(idCausaleErogazione);
					bandiRBandoCausaleErogazVO
							.setIdDimensioneImpresa(new BigDecimal(
									idDimensioneImpresa));
					l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
					if (l == null || l.isEmpty()) {
						bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
						bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO
								.getIdBando());
						bandiRBandoCausaleErogazVO
								.setIdCausaleErogazione(idCausaleErogazione);
						l = genericDAO
								.findListWhere(bandiRBandoCausaleErogazVO);
					}
				} else {
					/*
					 * FIX PBANDI-2383
					 */
					if (l == null || l.isEmpty()) {
						bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
						bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO
								.getIdBando());
						bandiRBandoCausaleErogazVO
								.setIdCausaleErogazione(idCausaleErogazione);
						l = genericDAO
								.findListWhere(bandiRBandoCausaleErogazVO);
					}
				}

			}
		} else {
			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
			bandiRBandoCausaleErogazVO
					.setIdCausaleErogazione(idCausaleErogazione);
			l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
		}
		if (l != null && !l.isEmpty())
			return l.get(0);
		else
			return null;
	}

	
	
	private BigDecimal insertDatiDBPerIndex(java.lang.Long idUtente, Node nodoCreato,
			BigDecimal idRichiestaErogazione, BigDecimal idProgetto,
			Date currentDate, String nomeFile,BigDecimal idTipoDocIndex,
			Long idRappLegale,Long idDelegato,String shaHex) throws Exception {
		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();

		documentoIndexVO.setDtInserimentoIndex(DateUtil
				.utilToSqlDate(currentDate));


		PbandiCEntitaVO entitaVO = new PbandiCEntitaVO();
		String tableName = new PbandiTRichiestaErogazioneVO().getTableNameForValueObject();
		entitaVO.setNomeEntita(tableName);

		PbandiCEntitaVO risbandiCEntitaVO[] = getGenericDAO().findWhere(
				entitaVO);

		if (isEmpty(risbandiCEntitaVO))
			throw new RuntimeException("Non trovato id per codice " + tableName);

		logger.debug(" decodificaEntita.getId() "
				+ risbandiCEntitaVO[0].getIdEntita());
		documentoIndexVO.setIdEntita(risbandiCEntitaVO[0].getIdEntita());

		documentoIndexVO.setIdTarget(idRichiestaErogazione);


		documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocIndex);

		documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));

		documentoIndexVO.setRepository(Constants.APP_COMPANY_HOME_CM_BANDI);

		documentoIndexVO.setUuidNodo(nodoCreato.getUid());

		/*
		 * FIX PBandi-561
		 */
		documentoIndexVO.setNomeFile(nomeFile);

		documentoIndexVO.setIdProgetto(idProgetto);
		if(idRappLegale!=null)
			documentoIndexVO.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
		if(idDelegato!=null)
			documentoIndexVO.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
		documentoIndexVO.setMessageDigest(shaHex);
		DecodificaDTO statoGenerato= decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_GENERATO);
		documentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoGenerato.getId()));
		documentoIndexVO = genericDAO.insert(documentoIndexVO);
		logger.debug("idDocIndex inserito sul db "
				+ documentoIndexVO.getIdDocumentoIndex());
		return documentoIndexVO.getIdDocumentoIndex();

	}

	public RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente,
			String identitaDigitale, Long idProgetto,
			Long idSoggettoRappresentante) throws CSIException,
			SystemException, UnrecoverableException,
			RichiestaErogazioneException {
			List<it.csi.pbandi.pbweb.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentantiVO = soggettoManager
					.findRappresentantiLegali(idProgetto,
							idSoggettoRappresentante);
			return beanUtil.transform(rappresentantiVO,
					RappresentanteLegaleDTO.class);
	}

	private String creaNomefile(BigDecimal idRichestaErogazione,
			Date currentDate) {
		String nomeFile;

		nomeFile = "RichiestaErogazione_" + idRichestaErogazione + "_"
				+ DateUtil.getTime(currentDate, TIME_FORMAT_PER_NOME_FILE)
				+ ".pdf";

		logger.info("nomeFile della richiesta erogazione : " + nomeFile);

		return nomeFile;
	}

	private EnteAppartenenzaDTO findEnteAppartenenza(Long idProgetto,
			String codiceTipoRuoloEnte) {
			EnteAppartenenzaVO enteVO = pbandiDichiarazioneDiSpesaDAO
					.findEnteAppartenenza(idProgetto, codiceTipoRuoloEnte);
			EnteAppartenenzaDTO enteDTO = new EnteAppartenenzaDTO();
			getBeanUtil().valueCopy(enteVO, enteDTO);
			return enteDTO;
	}


	
	public BigDecimal getIdTipoDocIndex(String codCausaleErogazione) {
		BigDecimal idTipoDocIndex=null;
//		id_caus= 1(PA) , id_doc_ind= 20 , RA
//		id_caus= 2(SA) , id_doc_ind= 19 , RUA
//		id_caus= 3(UA) , id_doc_ind= 18 , R2A
		if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)
				) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,COD_RICHIESTA_ANTICIPAZIONE);
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)
			) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,COD_RICHIESTA_II_ACCONTO);
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)
				) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,COD_RICHIESTA_ULTERIORE_ACCONTO);
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_SALDO)
				) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,COD_RICHIESTA_SALDO);
		}
		return idTipoDocIndex;
	}

	private String getTipoModello(String codCausaleErogazione) {
		
//		id_caus= 1(PA) , id_doc_ind= 20 , RA
//		id_caus= 2(SA) , id_doc_ind= 19 , RUA
//		id_caus= 3(UA) , id_doc_ind= 18 , R2A
		if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)
				) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_ANTICIPAZIONE;
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)
			) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_II_ACCONTO;
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)
				) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_ULTERIORE_ACCONTO;
		} else if (codCausaleErogazione
				.equals(Constants.COD_CAUSALE_EROGAZIONE_SALDO)
				) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_SALDO;
		}
		return "";
		
	}
	
	
 
	
	public EsitoReportRichiestaErogazioneDTO creaReportRichiestaErogazione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			Long idSoggetto, RichiestaErogazioneDTO richiestaErogazioneDTO,
			RappresentanteLegaleDTO rappresentanteLegaleDTO,
			IstanzaAttivitaDTO istanzaAttivita, String cfBeneficiario,
			String codiceUtente,Long idDelegato) throws CSIException, SystemException,
			UnrecoverableException, RichiestaErogazioneException {

		    EsitoReportRichiestaErogazioneDTO esito = new EsitoReportRichiestaErogazioneDTO();
			logger.info("\n\n\ncreaReportRichiestaErogazione --> idDelegato: "+idDelegato);
			
			RichiestaErogazioneReportDTO richiestaErogazioneReportDTO = new RichiestaErogazioneReportDTO();
			
			/**
			 * Carico i dati dell' ente di appartenenza
			 */
			EnteAppartenenzaDTO ente = findEnteAppartenenza(idProgetto,
					DichiarazioneDiSpesaSrv.CODICE_RUOLO_ENTE_DESTINATARIO);

			/**
			 * Carico i dati del beneficiario
			 */
			BeneficiarioVO beneficiarioVO = pbandiDichiarazioneDiSpesaDAO
					.findBeneficiario(idSoggetto, idProgetto);
			BeneficiarioDTO beneficiario = new BeneficiarioDTO();
			getBeanUtil().valueCopy(beneficiarioVO, beneficiario);

			/*
			 * Carico dati del progetto
			 */
			ProgettoDTO progetto = new ProgettoDTO();
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
			getBeanUtil().valueCopy(pbandiTProgettoVO, progetto);
			

			Double totaleQuietanzato = null;
			try {
				it.csi.pbandi.pbweb.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoMaster = 
					contoEconomicoManager.findContoEconomicoMaster(new BigDecimal(idProgetto));
				totaleQuietanzato=NumberUtil.toDouble(contoEconomicoMaster.getImportoQuietanzato());
			} catch (ContoEconomicoNonTrovatoException e1) {
				logger.warn("\n\n\nAttenzione!!! Non trovato conto economico master per progetto "+idProgetto);
			}
			
			BigDecimal percentuale_spesa=null;
			Double sogliaSpesaCalcErogazioni = NumberUtil.toDouble(pbandiTProgettoVO.getSogliaSpesaCalcErogazioni());
			if(sogliaSpesaCalcErogazioni!=null &&
					sogliaSpesaCalcErogazioni>0	){
				if(totaleQuietanzato!=null && 
						totaleQuietanzato>0){							
					BigDecimal divide = NumberUtil.divide(totaleQuietanzato, sogliaSpesaCalcErogazioni);
					percentuale_spesa= NumberUtil.multiply(divide,new BigDecimal(100));
				}
				
			}else{
			
				try {
					ContoEconomicoDTO contoEconomicoMaster = contoEconomicoManager.findContoEconomicoMaster(new BigDecimal(idProgetto));
					if(contoEconomicoMaster!=null){
						Double importoSpesaAmmessaUltima = NumberUtil
						.toDouble(contoEconomicoMaster.getImportoAmmesso());
						if(importoSpesaAmmessaUltima!=null && importoSpesaAmmessaUltima>0){
							BigDecimal divide = NumberUtil.divide(totaleQuietanzato, importoSpesaAmmessaUltima);
							percentuale_spesa= NumberUtil.multiply(divide,new BigDecimal(100));
						}
					}
					
				} catch (ContoEconomicoNonTrovatoException e) {
					logger.warn("L'ORRORE! non trovato il conto economico master per il progetto "+idProgetto);
				}
			
			}
			
			
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_TOTALE_QUIETANZIATO,
					totaleQuietanzato);
			
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PERCENTUALE_SPESA,
					NumberUtil.toDouble(percentuale_spesa));
			
			richiestaErogazioneReportDTO.setBeneficiario(beneficiario);
			richiestaErogazioneReportDTO.setEnte(ente);
			richiestaErogazioneReportDTO
					.setRappresentanteLegale(rappresentanteLegaleDTO);
			richiestaErogazioneReportDTO.setProgetto(progetto);
			richiestaErogazioneReportDTO
					.setEstremiBancari(richiestaErogazioneDTO
							.getEstremiBancari());
			richiestaErogazioneReportDTO.setFideiussioni(richiestaErogazioneDTO
					.getFideiussioni());
			richiestaErogazioneReportDTO.setAllegati(richiestaErogazioneDTO
					.getTipoAllegati());
			richiestaErogazioneReportDTO
					.setDescCausaleErogazione(richiestaErogazioneDTO
							.getDescrizioneCausaleErogazione());
			richiestaErogazioneReportDTO
					.setPercentualeErogazione(richiestaErogazioneDTO
							.getPercentualeErogazione());
			richiestaErogazioneReportDTO
					.setImportoRichiesto(richiestaErogazioneDTO
							.getImportoRichiesto());
			richiestaErogazioneReportDTO
					.setImportoTotaleSpesaQuietanzata(richiestaErogazioneDTO
							.getSpesaProgetto().getTotaleSpesaSostenuta());
			// NEW from PBANDI-1884
			richiestaErogazioneReportDTO
					.setDataInizioLavori(richiestaErogazioneDTO
							.getDtInizioLavori());
			richiestaErogazioneReportDTO
					.setDataStipulazioneContratti(richiestaErogazioneDTO
							.getDtStipulazioneContratti());
			richiestaErogazioneReportDTO
					.setResidenzaDirettoreLavori(richiestaErogazioneDTO
							.getResidenzaDirettoreLavori());
			richiestaErogazioneReportDTO
					.setDirettoreLavori(richiestaErogazioneDTO
							.getDirettoreLavori());
			Node nodoCreato = null;
			try {

				// DATA DI SISTEMA PER LE VARIE INSERT e PER IL NOME FILE
				Date currentDate = new Date(System.currentTimeMillis());
				// INSERT SU DB
				PbandiTRichiestaErogazioneVO richiestaErogazioneVO = new PbandiTRichiestaErogazioneVO();
				PbandiDCausaleErogazioneVO pbandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
				pbandiDCausaleErogazioneVO
						.setDescBreveCausale(richiestaErogazioneDTO
								.getDescBreveCausaleErogazione());
				pbandiDCausaleErogazioneVO = genericDAO
						.findSingleWhere(pbandiDCausaleErogazioneVO);
				richiestaErogazioneVO
						.setNumeroRichiestaErogazione(new BigDecimal(0));
				
				richiestaErogazioneVO
						.setIdCausaleErogazione(pbandiDCausaleErogazioneVO
								.getIdCausaleErogazione());
				richiestaErogazioneVO.setIdProgetto(new BigDecimal(idProgetto));
				richiestaErogazioneVO.setDtRichiestaErogazione(DateUtil
						.utilToSqlDate(currentDate));
				richiestaErogazioneVO
						.setImportoErogazioneRichiesto(new BigDecimal(
								richiestaErogazioneDTO.getImportoRichiesto()));
				richiestaErogazioneVO.setIdUtenteIns(new BigDecimal(idUtente));
				// }L{ NEW from PBANDI-1884
				richiestaErogazioneVO.setDtInizioLavori(DateUtil
						.utilToSqlDate(richiestaErogazioneDTO
								.getDtInizioLavori()));
				richiestaErogazioneVO.setDtStipulazioneContratti(DateUtil
						.utilToSqlDate(richiestaErogazioneDTO
								.getDtStipulazioneContratti()));
				richiestaErogazioneVO.setDirettoreLavori(richiestaErogazioneDTO
						.getDirettoreLavori());
				richiestaErogazioneVO
						.setResidenzaDirettoreLavori(richiestaErogazioneDTO
								.getResidenzaDirettoreLavori());
				richiestaErogazioneVO = genericDAO
						.insert(richiestaErogazioneVO);
				// UPDATE SU PBANDI_T_RICHIESTA_EROGAZIONE CON NUMERO RICHIESTA
				richiestaErogazioneVO
						.setNumeroRichiestaErogazione(richiestaErogazioneVO
								.getIdRichiestaErogazione());
				richiestaErogazioneVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(richiestaErogazioneVO);

				richiestaErogazioneReportDTO
						.setNumeroRichiestaErogazione(NumberUtil
								.toLong(richiestaErogazioneVO
										.getIdRichiestaErogazione()));
			
				String tipoModello=getTipoModello(richiestaErogazioneDTO.getDescBreveCausaleErogazione());
				
				popolaTemplateManager.setTipoModello(tipoModello);
				
				if(idDelegato!=null){
					popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_RAPPRESENTANTE_LEGALE,
							idDelegato);
						logger.info("il delegato non � NULL "+idDelegato+", lo metto al posto del rapp legale");
						 DelegatoVO delegatoVO = new DelegatoVO();
						 delegatoVO.setIdSoggetto(idDelegato);
						 delegatoVO.setIdProgetto(idProgetto);
					     List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
					     if(delegati!=null && !delegati.isEmpty()){
					    	delegatoVO=delegati.get(0);
					     }
						 it.csi.pbandi.pbweb.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegale = beanUtil.transform(
									delegatoVO,
									it.csi.pbandi.pbweb.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
						 logger.shallowDump(rappresentanteLegale,"info");
						 popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE,
								 rappresentanteLegale);
				}else{			
					 it.csi.pbandi.pbweb.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegale = getRappresentanteLegaleManager().findRappresentanteLegale(idProgetto, 
							 rappresentanteLegaleDTO.getIdSoggetto());
					 popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE,
							 rappresentanteLegale);
					popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_RAPPRESENTANTE_LEGALE,
						rappresentanteLegaleDTO.getIdSoggetto());
				}
				
				
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_DATA_RICHIESTA_EROG,
						currentDate);

				//anticipazione,II acconto, ulteriore acconto, saldo
				

				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IMPORTO_RICHIESTA_SALDO,richiestaErogazioneReportDTO.getImportoRichiesto());
				
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RICHIESTA_EROGAZIONE,
						richiestaErogazioneReportDTO);
				
				BigDecimal perc_raggiung_spesa=null;
			
			
				ProgettoBandoLineaVO progettoBandoLinea = progettoManager
				.getProgettoBandoLinea(idProgetto);
				Long progBandoLinea=progettoBandoLinea.getIdBandoLinea();
				
				PbandiRBandoLineaInterventVO bandoLineaInterventVO=new PbandiRBandoLineaInterventVO();
				
				bandoLineaInterventVO.setProgrBandoLineaIntervento(new BigDecimal(progBandoLinea));
				
				bandoLineaInterventVO=genericDAO.findSingleWhere(bandoLineaInterventVO);				
				
				PbandiRBandoCausaleErogazVO bandoCausaleErogazVO=new PbandiRBandoCausaleErogazVO();
				bandoCausaleErogazVO.setIdCausaleErogazione(pbandiDCausaleErogazioneVO
						.getIdCausaleErogazione());
				bandoCausaleErogazVO.setIdBando(bandoLineaInterventVO.getIdBando());
				
				try{
					bandoCausaleErogazVO = genericDAO.findSingleWhere(bandoCausaleErogazVO);
				
					perc_raggiung_spesa=bandoCausaleErogazVO.getPercSogliaSpesaQuietanzata();
					
				}catch (Exception e) {
					logger.warn("Attenzione!!! non trovata configurazione PbandiRBandoCausaleErogaz per causale "+pbandiDCausaleErogazioneVO
							.getIdCausaleErogazione()+" , bando "+progBandoLinea);
				}
				
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PERCENTUALE_RAGG_SPESA,
						NumberUtil.toDouble(perc_raggiung_spesa));
				
				/*
				 * ALLEGATI AL PROGETTO
				 */
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI, tipoAllegatiManager.findTipoAllegatiErogazione(progBandoLinea, pbandiDCausaleErogazioneVO
						.getDescBreveCausale(), idProgetto));
				
				// 11/12/15 added footer 
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,""+richiestaErogazioneVO.getIdRichiestaErogazione().longValue());
				
				
				Modello modello = popolaTemplateManager.popolaModello(idProgetto);
				
				long startFillReport=System.currentTimeMillis();
				JasperPrint jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(),
						modello.getMasterParameters(),new JREmptyDataSource());
				logger.info("\n\n\n########################\nJasperFillManager.fillReport eseguito in "+(System.currentTimeMillis()-startFillReport)+" ms\n");
				
				long startExport=System.currentTimeMillis();
				byte[] bytesPdf = JasperExportManager.exportReportToPdf(jasperPrint);
				logger.info("\n\n\n########################\nJasperPrint esportato to pdf in "+(System.currentTimeMillis()-startExport)+" ms\n");
				
				
				
				esito.setPdfBytes(bytesPdf);
				String nomeFile=creaNomefile(richiestaErogazioneVO
						.getIdRichiestaErogazione(), currentDate);
						
				esito.setNomeReport(nomeFile);

				//SOLO PER TEST decommentare
				//FileUtil.scriviFile(nomeFile,esito.getPdfBytes());
		
				// INSERT SU INDEX
				nodoCreato = indexDAO.creaContentRichiestaErogazione(
						richiestaErogazioneVO, pbandiTProgettoVO,
						cfBeneficiario, idSoggetto, idUtente, esito
								.getNomeReport(), bytesPdf);

				String shaHex = null;
				if(bytesPdf!=null)
				  shaHex = DigestUtils.shaHex(bytesPdf);
				
				// INSERT SU PBANDI_T_DOCUMENTO_INDEX
				BigDecimal idDocIndex = insertDatiDBPerIndex(idUtente, nodoCreato,
						richiestaErogazioneVO.getIdRichiestaErogazione(),
						   BigDecimal .valueOf(idProgetto), currentDate, esito
								.getNomeReport(),
								getIdTipoDocIndex(richiestaErogazioneDTO.getDescBreveCausaleErogazione()),
								rappresentanteLegaleDTO.getIdSoggetto(), idDelegato,shaHex);
				
				
				esito.setIdDocIndex(idDocIndex.longValue());
				
				
				logger.info("check progBandoLinea "+progBandoLinea);
				
				
				boolean isBrDemat= regolaManager
						.isRegolaApplicabileForBandoLinea(
								progBandoLinea,
								RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
				if(isBrDemat)
					associateAllegati(richiestaErogazioneVO.getIdRichiestaErogazione(), BigDecimal .valueOf(idProgetto));
				
				
				
				
				
				
			/*	logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+idProgetto);
				Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, idProgetto);
				logger.warn("processo: "+processo);
				if(processo!=null){*/
					logger.info("\n\n############################ NEOFLUX RICH-EROG-CALC-CAU ##############################\n");
					//se la causale della richiesta erogazione effettuata � il saldo endAttivita, else unlock
					logger.info("pbandiDCausaleErogazioneVO.getDescBreveCausale() : "+pbandiDCausaleErogazioneVO.getDescBreveCausale());
					
				
					if(pbandiDCausaleErogazioneVO.getDescBreveCausale().equalsIgnoreCase(COD_CAUSALE_EROGAZIONE_SALDO)){
						neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto,Task.RICH_EROG_CALC_CAU);
					}
					else{
						neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto,Task.RICH_EROG_CALC_CAU);
					}
					
				 

					String descrBreveTemplateNotifica=getDescrTemplateNotifica(pbandiDCausaleErogazioneVO.getDescBreveCausale());
					
					logger.info("calling genericDAO.callProcedure().sendNotificationMessage....");
					genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.ISTRUTTORE,idUtente);
					
					
					
					logger.info("############################ NEOFLUX ##############################\n\n\n\n");
			/*	}else{
					// +flux+ 
					logger.warn("\n\n############################ OLDFLUX rRichiestaErogazione ##############################\ncalling getProcessManager().creaReportRichiestaErogazione idProgetto :"+idProgetto);
					getProcessManager().creaReportRichiestaErogazione(idProgetto,idUtente,identitaDigitale);
					logger.warn("creaReportRichiestaErogazione OK\n############################ OLDFLUX ##############################\n\n\n\n");
				}*/
				

			

			} catch (Exception e) {
				RichiestaErogazioneException ex = new RichiestaErogazioneException(
						ErrorConstants.ERRORE_IMPOSSIBILE_CREARE_PDF, e);
				throw ex;
			}
		 
		return esito;
	}

	private String getDescrTemplateNotifica(String descBreveCausale) {
		if(descBreveCausale.equalsIgnoreCase(COD_CAUSALE_EROGAZIONE_SALDO)){
			return Notification.NOTIFICA_RICHIESTA_EROGAZIONE_SALDO;
		}else if(descBreveCausale.equalsIgnoreCase(COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)){
			return  Notification.NOTIFICA_RICHIESTA_EROGAZIONE_PRIMO_ANTICIPO;
		}else if(descBreveCausale.equalsIgnoreCase(COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)){
			return  Notification.NOTIFICA_RICHIESTA_EROGAZIONE_ACCONTO;
		}else if(descBreveCausale.equalsIgnoreCase(COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)){
			return  Notification.NOTIFICA_RICHIESTA_EROGAZIONE_ULTERIORE_ACCONTO;
		} 
		return null;
	}

	// FEBBRAIO 2015 DEMAT II
	private void associateAllegati(BigDecimal idRichiestaErogazione,BigDecimal idProgetto) {

		// where idPRogetto is null && id_entita=t_rich and id_target= id_progetto
		logger.info("associating allegati to idRichiestaErogazione "+idRichiestaErogazione+" ,idProgetto ");
		getPbandiArchivioFileDAOImpl().associateAllegatiToRichiestaErogazione(idRichiestaErogazione, idProgetto);
		
	}

	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}
	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}

	public void setRappresentanteLegaleManager(
			RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}
}
