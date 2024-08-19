/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.csi.wrapper.UserException;
import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.archivio.ArchivioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.*;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EnteAppartenenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.richiestaerogazione.RichiestaErogazioneException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.CausaleErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.ModalitaAgevolazioneErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.*;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.dichiarazionedispesa.DichiarazioneDiSpesaSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweberog.business.intf.ErrorConstants;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.erogazione.FideiussioneDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.SpesaProgettoDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.*;
import it.csi.pbandi.pbweberog.exception.ErogazioneException;
import it.csi.pbandi.pbweberog.integration.dao.ErogazioneDAO;
import it.csi.pbandi.pbweberog.util.*;
import it.doqui.index.ecmengine.dto.Node;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;

@Component
public class ErogazioneDAOImpl extends JdbcDaoSupport implements ErogazioneDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	protected FileApiServiceImpl fileApiServiceImpl;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;

	@Autowired
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	
	@Autowired
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	
	@Autowired
	private PbandiErogazioneDAOImpl pbandiErogazioneDAO;
	
	@Autowired
	private RegolaManager regolaManager;
	
	@Autowired
	private DecodificheManager decodificheManager;
	
	@Autowired
	private NeofluxBusinessImpl neofluxBusiness;
	
	@Autowired
	private SoggettoManager soggettoManager;
	
	@Autowired
	PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	
	@Autowired
	PopolaTemplateManager popolaTemplateManager;
	
	@Autowired
	RappresentanteLegaleManager rappresentanteLegaleManager;
	
	@Autowired
	ProgettoManager progettoManager;
	
	@Autowired
	TipoAllegatiManager tipoAllegatiManager;
	
	@Autowired
	PbandiArchivioFileDAOImpl archivioFileDAOImpl;
	
	@Autowired
	ArchivioBusinessImpl archivioBusinessImpl;
	
	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public ErogazioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String findCodiceProgetto(Long idProgetto) {
		String prf = "[ErogazioneDAOImpl::findCodiceProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] {idProgetto};
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			LOG.info(prf + " END");
			return codiceProgetto;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	
	@Override
	@Transactional
	public EsitoErogazioneDTO findErogazione(Long idUtente, String idIride, Long idProgetto, Long idBandoLinea, Long idFormaGiuridica, Long idDimensioneImpresa) throws Exception {
		String prf = "[ErogazioneDAOImpl::findErogazione]";
		LOG.info(prf + " BEGIN");
		try {
			EsitoErogazioneDTO esitoErogazioneDTO = new EsitoErogazioneDTO();
			ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
			SpesaProgettoDTO spesaProgetto = new SpesaProgettoDTO();
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };

			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

			// DATI DELLA SPESA PROGETTO
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			PbandiDTipoOperazioneVO tipoOperazioneVO = new PbandiDTipoOperazioneVO();
			tipoOperazioneVO.setIdTipoOperazione(progettoVO
					.getIdTipoOperazione());
			tipoOperazioneVO = genericDAO.findSingleWhere(tipoOperazioneVO);
			spesaProgetto.setTipoOperazione(tipoOperazioneVO.getDescTipoOperazione());
			spesaProgetto.setImportoAmmessoContributo(contoEconomicoManager.findImportoAgevolato(idProgetto));
			Double totaleSpesaAmmessa = contoEconomicoManager.getTotaleSpesaAmmmessaInRendicontazioneDouble(new BigDecimal( idProgetto));
			spesaProgetto.setTotaleSpesaAmmessa(totaleSpesaAmmessa);
			DocumentoDiSpesaDTO documentoDiSpesa = new DocumentoDiSpesaDTO();
			documentoDiSpesa.setIdProgetto(idProgetto);
			documentoDiSpesa.setIsGestitiNelProgetto(new Boolean(true));
			double sommaImportiQuietanzati = 0;
			double sommaImportiValidati = 0;
			List<DocumentoDiSpesaVO> list = pbandiDocumentiDiSpesaDAO.findDocumentiDiSpesa(documentoDiSpesa, null);
			Iterator<DocumentoDiSpesaVO> iter = list.iterator();
			while (iter.hasNext()) {
				DocumentoDiSpesaVO obj = (DocumentoDiSpesaVO) iter.next();
				List<PagamentoQuotePartiVO> listPagamenti = pbandipagamentiDAO.findPagamentiAssociati(obj.getIdDocumentoDiSpesa(), idProgetto);
				Iterator<PagamentoQuotePartiVO> iterPagamenti = listPagamenti.iterator();

				while (iterPagamenti.hasNext()) {
					PagamentoQuotePartiVO objPagamento = (PagamentoQuotePartiVO) iterPagamenti.next();
					Double importoQuietanzato = pbandipagamentiDAO.getImportoQuietanzato(idProgetto,NumberUtil.toLong(objPagamento.getIdPagamento()));
					if (importoQuietanzato != null) {
						sommaImportiQuietanzati += importoQuietanzato.doubleValue();
					}
					Double importoValidato = pbandipagamentiDAO.getImportoValidato(idProgetto,NumberUtil.toLong(objPagamento.getIdPagamento()));
					if (importoValidato != null) {
						sommaImportiValidati += importoValidato.doubleValue();
					}
				}

				if (sommaImportiQuietanzati > 0) {
					spesaProgetto.setTotaleSpesaSostenuta(sommaImportiQuietanzati);
					// spesaProgetto.setAvanzamentoSpesaSostenuta(sommaImportiQuietanzati/spesaProgetto.getImportoAmmessoContributo()*100);
					spesaProgetto.setAvanzamentoSpesaSostenuta(sommaImportiQuietanzati / spesaProgetto.getTotaleSpesaAmmessa() * 100);
				}
				if (sommaImportiValidati > 0) {
					spesaProgetto.setTotaleSpesaValidata(sommaImportiValidati);
					// spesaProgetto.setAvanzamentoSpesaValidata(sommaImportiValidati/spesaProgetto.getImportoAmmessoContributo());
					spesaProgetto.setAvanzamentoSpesaValidata(sommaImportiValidati / spesaProgetto.getTotaleSpesaAmmessa() * 100);
				}
			}

			erogazioneDTO.setSpesaProgetto(spesaProgetto);
			// DATI 2 FIDEIUSSIONE
			FideiussioneVO[] fideiussioniVO = pbandiErogazioneDAO.findFideiussioniAttive(idProgetto);
			if (fideiussioniVO != null) {
				FideiussioneDTO[] fideiussioniDTO = new FideiussioneDTO[fideiussioniVO.length];
				beanUtil.valueCopy(fideiussioniVO, fideiussioniDTO);
				erogazioneDTO.setFideiussioni(fideiussioniDTO);
			}
			// DATI 3 EROGAZIONI DEL PROGETTO
			BigDecimal sommaTotaliImportiErogazioni = new BigDecimal(0);
			BigDecimal sommaTotaleImportiRecupero = new BigDecimal(0);
			ErogazioneCausaleModalitaAgevolazioneVO erogazioneVO = new ErogazioneCausaleModalitaAgevolazioneVO();
			erogazioneVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			List<ErogazioneCausaleModalitaAgevolazioneVO> erogazioni = genericDAO.findListWhere(erogazioneVO);
			LOG.info(prf + " size: " + erogazioni.size());
			if (erogazioni != null) {
				List<ErogazioneDTO> listErogazioni = new ArrayList<ErogazioneDTO>();
				List<PbandiTRecuperoVO> recuperiVO = new ArrayList<PbandiTRecuperoVO>();
				for (ErogazioneCausaleModalitaAgevolazioneVO vo : erogazioni) {
					ErogazioneDTO dto = new ErogazioneDTO();
					dto.setIdErogazione(NumberUtil.toLong(vo.getIdErogazione()));
					
					dto.setDescrizioneCausaleErogazione(vo.getDescCausale());
					dto.setDescModalitaAgevolazione(vo.getDescModalitaAgevolazione());
					
					dto.setCodCausaleErogazione(vo.getIdCausaleErogazione().toString());
					dto.setCodModalitaAgevolazione(vo.getIdModalitaAgevolazione().toString());
					dto.setCodModalitaErogazione(vo.getIdModalitaErogazione().toString());
					
					dto.setDescBreveCausaleErogazione(vo.getDescBreveCausale());
					dto.setDescBreveModalitaAgezolazione(vo.getDescBreveModalitaAgevolaz());
					
					PbandiDModalitaErogazioneVO modalitaErogazioneVO = new PbandiDModalitaErogazioneVO();
					modalitaErogazioneVO.setIdModalitaErogazione(vo.getIdModalitaErogazione());
					modalitaErogazioneVO = genericDAO.findSingleWhere(modalitaErogazioneVO);					
					dto.setDescBreveModalitaErogazione(modalitaErogazioneVO.getDescBreveModalitaErogazione());
					dto.setDescModalitaErogazione(modalitaErogazioneVO.getDescModalitaErogazione());

					
					BandoLineaVO bandoLineaVO = new BandoLineaVO();
					bandoLineaVO.setProgrBandoLineaIntervento(NumberUtil.toBigDecimal(idBandoLinea));
					bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);
					
//					List<PbandiRBandoCausaleErogazVO> bandoCausaleVOs = this.getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridica(
//							idFormaGiuridica, idDimensioneImpresa, bandoLineaVO);
//
//					LOG.info(prf + "  idCausale vo: " + vo.getIdCausaleErogazione());
//					PbandiRBandoCausaleErogazVO bandoCausaleVO = new PbandiRBandoCausaleErogazVO();
//					for(PbandiRBandoCausaleErogazVO v : bandoCausaleVOs) {
//						LOG.info(prf + "  idBando: " + v.getIdBando() + " idCausale: " + v.getIdCausaleErogazione());
//						if(v.getIdCausaleErogazione() == vo.getIdCausaleErogazione() ) {
//							LOG.info(prf + " trovato bando causale erogazione");
//							bandoCausaleVO = v;
//						}
//					}
					
//					PbandiDCausaleErogazioneVO bandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
//					
//					PbandiDCausaleErogazioneVO bandiDCausaleErogazioneVO1 = new PbandiDCausaleErogazioneVO();
//					bandiDCausaleErogazioneVO.setIdCausaleErogazione(vo.getIdCausaleErogazione());
//					bandiDCausaleErogazioneVO.setAscendentOrder("progrOrdinamento");
//					bandiDCausaleErogazioneVO1 = genericDAO.findSingleWhere(bandiDCausaleErogazioneVO);					
//					BigDecimal percErogazione = bandoCausaleVO.getPercErogazione();					
//					BigDecimal percLimite = bandoCausaleVO.getPercLimite();
					
					PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridicaECausale(
							idFormaGiuridica, idDimensioneImpresa, bandoLineaVO, NumberUtil.toLong(vo.getIdCausaleErogazione()));
					DecodificaDTO decodificaVO = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, NumberUtil.toLong(vo.getIdCausaleErogazione()));
					BigDecimal percErogazione = bandiRBandoCausaleErogazVO.getPercErogazione();
					BigDecimal percLimite = bandiRBandoCausaleErogazVO.getPercLimite();
					if (percErogazione == null)
						percErogazione = new BigDecimal(0);
					if (percLimite == null)
						percLimite = new BigDecimal(0);
										
					LOG.info(prf + "percErogazione: " + percErogazione + " percLimite: " + percLimite);

					// DESC BREVE CAUSALE
					dto.setDescBreveCausaleErogazione(decodificaVO.getDescrizioneBreve());
					
					LOG.info(prf + "descBreveErogazione: " + dto.getDescBreveCausaleErogazione());					
					Double importoCalcolato = contoEconomicoManager.findImportoAgevolato(idProgetto);
					if (percErogazione.doubleValue() > 0) {
						// CASO A	
						LOG.info(prf + "CASO A");
						dto.setPercentualeErogazioneFinanziaria(percLimite.doubleValue());							
						Double importoErogazioneCalcolato = NumberUtil.toRoundDouble(importoCalcolato * percErogazione.doubleValue() / 100);			
						dto.setImportoErogazioneFinanziario(importoErogazioneCalcolato);						
						dto.setPercentualeErogazioneEffettiva(percErogazione.doubleValue());
						dto.setImportoErogazioneEffettivo(NumberUtil.toRoundDouble(importoCalcolato * percErogazione.doubleValue() / 100));						
					}  else if (percErogazione.doubleValue() == 0 && percLimite.doubleValue() > 0) {
						// CASO B
						LOG.info(prf + "CASO B");
						dto.setPercentualeErogazioneFinanziaria(percLimite.doubleValue());						
						Double importoErogazioneDaIterFinanziario = NumberUtil.toRoundDouble((importoCalcolato * percLimite.doubleValue() / 100));			
						dto.setImportoErogazioneFinanziario(importoErogazioneDaIterFinanziario)	;
						dto.setPercentualeErogazioneEffettiva(percLimite.doubleValue());
						dto.setImportoErogazioneEffettivo(importoErogazioneDaIterFinanziario);
					} else {
						// CASO C
						LOG.info(prf + "CASO C");
						dto.setPercentualeErogazioneFinanziaria(0d);
						dto.setImportoErogazioneFinanziario(0d);
						dto.setPercentualeErogazioneEffettiva(0d);
						dto.setImportoErogazioneEffettivo(0d);
					}
					
					dto.setImportoErogazioneEffettivo(NumberUtil.toDouble(vo.getImportoErogazione()));
					dto.setDataContabile(vo.getDtContabile());
					dto.setNumeroRiferimento(vo.getCodRiferimentoErogazione());					
					dto.setNoteErogazione(vo.getNoteErogazione());
					
					listErogazioni.add(dto);

					sommaTotaliImportiErogazioni = NumberUtil.sum(NumberUtil.createScaledBigDecimal(vo.getImportoErogazione()), sommaTotaliImportiErogazioni);

					PbandiTRecuperoVO recuperoVO = new PbandiTRecuperoVO();
					recuperoVO.setIdModalitaAgevolazione(vo.getIdModalitaAgevolazione());
					recuperiVO.add(recuperoVO);
				}
				if (!recuperiVO.isEmpty()) {
					/*
					 * ricerco i recuperi per le modalita' di agevolazioni
					 */
					PbandiTRecuperoVO filtroVO = new PbandiTRecuperoVO();
					filtroVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
					FilterCondition<PbandiTRecuperoVO> conditionModalita = new FilterCondition<PbandiTRecuperoVO>( recuperiVO);
					FilterCondition<PbandiTRecuperoVO> filterCondition = new FilterCondition<PbandiTRecuperoVO>(filtroVO);
					AndCondition<PbandiTRecuperoVO> andCondition = new AndCondition<PbandiTRecuperoVO>(filterCondition, conditionModalita);					
					
					
					List<PbandiTRecuperoVO> listRecuperi = genericDAO.findListWhere(andCondition);
					if (listRecuperi != null) {
						for (PbandiTRecuperoVO vo : listRecuperi) {
							sommaTotaleImportiRecupero = NumberUtil.sum( NumberUtil.createScaledBigDecimal(vo.getImportoRecupero()), sommaTotaleImportiRecupero);
						}
					}
				}
				erogazioneDTO.setErogazioni(beanUtil.transform(listErogazioni,
						ErogazioneDTO.class));
			}
			erogazioneDTO.setImportoTotaleRecuperi(NumberUtil.getDoubleValue(sommaTotaleImportiRecupero));

			/*
			 * JIRA-1194. Ricerca delle modalita' di agevolazione
			 */
			ModalitaAgevolazioneErogazioneVO filtroModalitaVO = new ModalitaAgevolazioneErogazioneVO();
			filtroModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			filtroModalitaVO.setAscendentOrder("idModalitaAgevolazione");

			List<ModalitaAgevolazioneErogazioneVO> listModalitaAgevolazioneVO = genericDAO.findListWhere(filtroModalitaVO);
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
				listModalitaAgevolazioneContoEconomicoVO = contoEconomicoManager.caricaModalitaAgevolazione( NumberUtil.toBigDecimal(idProgetto), Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error("Nessun conto economico trovato", e);
				ErogazioneException re = new ErogazioneException(Constants.ERRORE_SERVER);
				throw re;
			}

			if (listModalitaAgevolazioneContoEconomicoVO != null) {
				/*
				 * ricerco le causali di erogazione per il progetto
				 */
				CausaleErogazioneVO filtroCausaleVO = new CausaleErogazioneVO();
				filtroCausaleVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
				filtroCausaleVO.setAscendentOrder("idProgetto", "idModalitaAgevolazione", "dtContabile");
					List<CausaleErogazioneVO> listCausaliErogazioneVO = genericDAO.findListWhere(filtroCausaleVO);

					for (ModalitaDiAgevolazioneContoEconomicoVO modContoEconomicoVO : listModalitaAgevolazioneContoEconomicoVO) {
						ModalitaAgevolazioneErogazioneDTO modalitaDTO = beanUtil.transform(modContoEconomicoVO, ModalitaAgevolazioneErogazioneDTO.class);
						modalitaDTO.setUltimoImportoAgevolato(NumberUtil.toDouble(modContoEconomicoVO.getQuotaImportoAgevolato()));
						for (ModalitaAgevolazioneErogazioneVO vo : listModalitaAgevolazioneVO) {
							if (NumberUtil.compare(modContoEconomicoVO.getIdModalitaAgevolazione(), vo.getIdModalitaAgevolazione()) == 0) {
								modalitaDTO.setImportoTotaleErogazioni(NumberUtil.toDouble(vo.getImportoTotaleErogazioni()));
								modalitaDTO.setImportoTotaleRecupero(NumberUtil.toDouble(vo.getImportoTotaleRecupero()));
								modalitaDTO.setImportoResiduoDaErogare(NumberUtil.toDouble
										( NumberUtil.subtract(modContoEconomicoVO.getQuotaImportoAgevolato(), NumberUtil.sum(vo.getImportoTotaleErogazioni(), vo.getImportoTotaleRecupero()))
												) );
								Double importoRevocato = getImportoRevocato(vo.getIdModalitaAgevolazione(), idProgetto); 
								if(importoRevocato!=null) 
								modalitaDTO.setImportoRevocato(importoRevocato);
								break;
							}
						}

						if (listCausaliErogazioneVO != null) {
							List<CausaleErogazioneDTO> causali = new ArrayList<CausaleErogazioneDTO>();
							for (CausaleErogazioneVO causaleVO : listCausaliErogazioneVO) {
								if (NumberUtil.compare(causaleVO.getIdModalitaAgevolazione(), modContoEconomicoVO.getIdModalitaAgevolazione()) == 0) {
									CausaleErogazioneDTO causale = beanUtil.transform(causaleVO, CausaleErogazioneDTO.class);
									causali.add(causale);
								}
							}
							modalitaDTO.setCausaliErogazione(beanUtil.transform(causali, CausaleErogazioneDTO.class));
						}
						modalitaAgevolazioneErogazione.add(modalitaDTO);
					}
				}

				erogazioneDTO.setModalitaAgevolazioni(beanUtil.transform(modalitaAgevolazioneErogazione, ModalitaAgevolazioneErogazioneDTO.class));
				erogazioneDTO.setImportoTotaleErogato(NumberUtil.toDouble(sommaTotaliImportiErogazioni));
				/*
			 * JIRA-1195 Consideriamo anche il totale dei recuperi
			 */
			BigDecimal importoTotaleResiduo = NumberUtil.sum(NumberUtil.createScaledBigDecimal(erogazioneDTO.getSpesaProgetto().getImportoAmmessoContributo()), NumberUtil
					.createScaledBigDecimal(sommaTotaleImportiRecupero));

			importoTotaleResiduo = NumberUtil.subtract(importoTotaleResiduo,
							NumberUtil.createScaledBigDecimal(sommaTotaliImportiErogazioni));

			erogazioneDTO.setImportoTotaleResiduo(NumberUtil.toDouble(importoTotaleResiduo));

			// DATI 4 RICHIESTE EROGAZIONI DEL PROGETTO
			double sommaTotaliImportiRichiesteErogazioni = 0;
			PbandiTRichiestaErogazioneVO richiestaErogazioneVO = new PbandiTRichiestaErogazioneVO();
			richiestaErogazioneVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			List<PbandiTRichiestaErogazioneVO> richiestaErogazioni = genericDAO.findListWhere(richiestaErogazioneVO);
			if (richiestaErogazioni != null) {
				ArrayList<RichiestaErogazione> listRichiestaErog = new ArrayList<RichiestaErogazione>();
				Iterator<PbandiTRichiestaErogazioneVO> iterRichiestaErog = richiestaErogazioni.iterator();
				while (iterRichiestaErog.hasNext()) {
					PbandiTRichiestaErogazioneVO vo = (PbandiTRichiestaErogazioneVO) iterRichiestaErog.next();
					RichiestaErogazione dto = new RichiestaErogazione();
					PbandiDCausaleErogazioneVO causaleErogazioneVO = new PbandiDCausaleErogazioneVO();
					causaleErogazioneVO.setIdCausaleErogazione(vo.getIdCausaleErogazione());
					causaleErogazioneVO = genericDAO.findSingleWhere(causaleErogazioneVO);
					
					dto.setDescCausaleErogazione(causaleErogazioneVO.getDescCausale());
					dto.setDataRichiestaErogazione(vo.getDtRichiestaErogazione());
					sommaTotaliImportiRichiesteErogazioni = sommaTotaliImportiRichiesteErogazioni + NumberUtil.getDoubleValue(vo.getImportoErogazioneRichiesto());
					dto.setImportoRichiestaErogazione(NumberUtil.getDoubleValue(vo.getImportoErogazioneRichiesto()));
					dto.setNumeroRichiestaErogazione(vo.getNumeroRichiestaErogazione().toString());
					listRichiestaErog.add(dto);
				}
				if (!listRichiestaErog.isEmpty()) {
					RichiestaErogazione[] array = listRichiestaErog.toArray(new RichiestaErogazione[] {});
					erogazioneDTO.setRichiesteErogazioni(array);
				}

				// erogazioneDTO.setRichiesteErogazioni((RichiestaErogazioneDTO[])listRichiestaErog.toArray());
			}
			erogazioneDTO.setImportoTotaleRichiesto(sommaTotaliImportiRichiesteErogazioni);

			esitoErogazioneDTO.setErogazione(erogazioneDTO);

			/*
			 * GESTIONE REGOLA PER EROGAZIONE
			 */
			if (idBandoLinea != null) {
				// }L{ Il BandoLinea potrebbe essere null (es se chiamato in
				// rinuncia)
				boolean isRegolaApplicabile = regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea, Constants.BR13_ATTIVITA_EROGAZIONE_DISPONIBILE);
				esitoErogazioneDTO.setIsRegolaAttiva(isRegolaApplicabile);
			}

	 
		LOG.info(prf + " END");
		return esitoErogazioneDTO;

		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	private Double getImportoRevocato(BigDecimal idModalitaAgevolazione, Long idProgetto) {
		String prf = "[ErogazioneDAOImpl::getImportoRevocato]";
		LOG.info(prf + " BEGIN");
		Double importoRevocato = null; 
		
		try {
			
			String getImportoRevocato ="  select\r\n"
					+ "  nvl(ptr.IMPORTO, 0)  AS importo\r\n"
					+ "  FROM pbandi_r_Soggetto_progetto prsp\r\n"
					+ "  LEFT JOIN pbandi_t_revoca ptr ON prsp.ID_PROGETTO = ptr.ID_PROGETTO \r\n"
					+ "  WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "  AND prsp.ID_TIPO_ANAGRAFICA= 1\r\n"
					+ "  AND prsp.id_progetto=? \r\n"
					+ "  AND ptr.ID_MODALITA_AGEVOLAZIONE =?";
			
			importoRevocato = getJdbcTemplate().queryForObject(getImportoRevocato, Double.class, new Object[] {
					idProgetto, idModalitaAgevolazione
			});
			
			
			
		} catch (Exception e) {
			LOG.error("Dato non presente al DB");
			return null; 
		}
		
		LOG.info("END");
		return importoRevocato;
	}

	@Override
	@Transactional
	public CodiceDescrizioneCausaleDTO[] findCausaliErogazione(Long idUtente, String idIride, Long idProgetto,
			Long idBandoLinea, Long idFormaGiuridica, Long idDimensioneImpresa) throws Exception {
		String prf = "[ErogazioneDAOImpl::findCausaliErogazione]";
		LOG.info(prf + " BEGIN");
		try {
			List<CodiceDescrizioneCausaleDTO> list = new ArrayList<CodiceDescrizioneCausaleDTO>();
			 
			String[] nameParameter = { "idUtente", "idIride", "idBandoLinea" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idBandoLinea);
			BandoLineaVO bandoLineaVO = new BandoLineaVO();
			bandoLineaVO.setProgrBandoLineaIntervento(NumberUtil.toBigDecimal(idBandoLinea));
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);
			List<PbandiRBandoCausaleErogazVO> l = getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridica(
					idFormaGiuridica, idDimensioneImpresa, bandoLineaVO);

			Iterator<PbandiRBandoCausaleErogazVO> iter = l.iterator();
			while (iter.hasNext()) {
				PbandiRBandoCausaleErogazVO vo = (PbandiRBandoCausaleErogazVO) iter.next();
				PbandiDCausaleErogazioneVO bandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
				bandiDCausaleErogazioneVO.setIdCausaleErogazione(vo.getIdCausaleErogazione());
				bandiDCausaleErogazioneVO.setAscendentOrder("progrOrdinamento");
				bandiDCausaleErogazioneVO = genericDAO.findSingleWhere(bandiDCausaleErogazioneVO);
				CodiceDescrizioneCausaleDTO codiceDescrizione = new CodiceDescrizioneCausaleDTO();
				BigDecimal percErogazione = vo.getPercErogazione();
				BigDecimal percLimite = vo.getPercLimite();
				if (percErogazione == null)
					percErogazione = new BigDecimal(0);
				if (percLimite == null)
					percLimite = new BigDecimal(0);
				codiceDescrizione.setCodice(bandiDCausaleErogazioneVO.getDescBreveCausale());
				codiceDescrizione.setDescrizione(bandiDCausaleErogazioneVO.getDescCausale());
				codiceDescrizione.setPercErogazione(percErogazione.doubleValue());
				codiceDescrizione.setPercLimite(percLimite.doubleValue());
				list.add(codiceDescrizione);
			}

			LOG.info(prf + " END");
			return list.toArray(new CodiceDescrizioneCausaleDTO[] {});
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}
	
	private List<PbandiRBandoCausaleErogazVO> getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridica(
			Long idFormaGiuridica, Long idDimensioneImpresa,
			BandoLineaVO bandoLineaVO) {
		String prf = "[ErogazioneDAOImpl::getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridica]";
		LOG.info(prf + " START");
		PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = null;

		List<PbandiRBandoCausaleErogazVO> l = null;

		if (idFormaGiuridica != null) {
			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
			bandiRBandoCausaleErogazVO.setIdFormaGiuridica(new BigDecimal(idFormaGiuridica));
			bandiRBandoCausaleErogazVO.setAscendentOrder("idCausaleErogazione");
			l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
			if (l == null || l.isEmpty()) {
				bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
				bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
				if(idDimensioneImpresa!=null)
					bandiRBandoCausaleErogazVO.setIdDimensioneImpresa(new BigDecimal(idDimensioneImpresa));
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
		LOG.info(prf + " END");
		return l;
	}

	@Override
	@Transactional
	public CodiceDescrizioneDTO[] findModalitaAgevolazionePerContoEconomico(Long idUtente, String idIride,
			Long idProgetto) throws Exception {
		String prf = "[ErogazioneDAOImpl::findModalitaAgevolazionePerContoEconomico]";
		LOG.info(prf + " BEGIN");
		List<CodiceDescrizioneDTO> listCD = new ArrayList<CodiceDescrizioneDTO>();
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					idIride, idProgetto);
			ModalitaDiAgevolazioneContoEconomicoVO modalitaDiAgevolazioneContoEconomicoVO = new ModalitaDiAgevolazioneContoEconomicoVO();
			modalitaDiAgevolazioneContoEconomicoVO.setFlagLvlprj(Constants.FLAG_TRUE);
			modalitaDiAgevolazioneContoEconomicoVO.setIdContoEconomico(contoEconomicoManager.getIdContoMaster(new BigDecimal(idProgetto)));
			List<ModalitaDiAgevolazioneContoEconomicoVO> list = genericDAO.findListWhere(modalitaDiAgevolazioneContoEconomicoVO);
		
			if (list != null) {
				Iterator<ModalitaDiAgevolazioneContoEconomicoVO> iter = list.iterator();
				while (iter.hasNext()) {
					ModalitaDiAgevolazioneContoEconomicoVO vo = iter.next();
					CodiceDescrizioneDTO cd = new CodiceDescrizioneDTO();
					cd.setCodice(vo.getDescBreveModalitaAgevolaz());
					cd.setDescrizione(vo.getDescModalitaAgevolazione());
					listCD.add(cd);
				}
			}
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
		LOG.info(prf + " END");
		if (!listCD.isEmpty())
			return listCD.toArray(new CodiceDescrizioneDTO[] {});
		else
			return null;
	}

	@Override
	@Transactional
	public EsitoErogazioneDTO inserisciErogazione(Long idUtente, String idIride, Long idProgetto,
			ErogazioneDTO erogazione, String codUtenteFlux) throws UserException, UnrecoverableException {
		String prf = "[ErogazioneDAOImpl::inserisciErogazione]";
		LOG.info(prf + " BEGIN");
		
		EsitoErogazioneDTO esito = new EsitoErogazioneDTO();

		try {
			String[] nameParameter = { "idUtente", "idIride","idProgetto", "erogazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride, idProgetto, erogazione);
			BigDecimal importoErogazioni = getImportoErogazioniPerModalitaAgevolazionePerInserimento(idProgetto, erogazione);

			if (NumberUtil.compare(importoErogazioni, contoEconomicoManager
					.getQuotaImportoAgevolato(new BigDecimal(idProgetto),
							erogazione.getCodModalitaAgevolazione())) > 0) {
				throw new ErogazioneException(
						ErrorMessages.ERRORE_SOMMA_EROGAZIONI_PER_MOD_AGEVOLAZ_SUPERIORE_IMPORTO_AGEVOLATO_CONTO_ECONOMICO);
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
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO decodificaVO = decodificheManager.findDecodifica(	GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, codCausale);
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
			esito.setMessages(new String[] {   MessageConstants.MSG_EROGAZIONE_SALVATA });
			ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
			erogazioneDTO.setIdErogazione(NumberUtil.toLong(ret
					.getIdErogazione()));
			esito.setErogazione(erogazioneDTO);
			
		/*	logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+idProgetto);
			Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, idProgetto);
			logger.warn("processo: "+processo);
			if(processo!=null){*/
				logger.warn("\n\n############################ NEOFLUX UNLOCK EROGAZIONE ##############################\n");
				neofluxBusiness.unlockAttivita(idUtente, idIride, idProgetto,Task.EROGAZIONE);
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
		LOG.info(prf + " END");

		return esito;
		
	}

	private BigDecimal getImportoErogazioniPerModalitaAgevolazionePerInserimento(Long idProgetto,
			ErogazioneDTO erogazione) {
		String prf = "[ErogazioneDAOImpl::getImportoErogazioniPerModalitaAgevolazionePerInserimento]";
		LOG.info(prf + " START");
		PbandiTErogazioneVO pbandiTErogazioneVO = new PbandiTErogazioneVO();
		pbandiTErogazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
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
		LOG.info(prf + " END");
		return importo;
	}
	
	private BigDecimal getImportoErogazioniPerModalitaAgevolazionePerModifica(Long idProgetto,
			ErogazioneDTO erogazione) {
		String prf = "[ErogazioneDAOImpl::getImportoErogazioniPerModalitaAgevolazionePerInserimento]";
		LOG.info(prf + " START");
		PbandiTErogazioneVO pbandiTErogazioneVO = new PbandiTErogazioneVO();
		pbandiTErogazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
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
				//sommo gli importi delle erogazioni esclusa quella corrente
				if(!erogazione.getIdErogazione().equals(new Long(vo.getIdErogazione().longValue()))) {
					importo = NumberUtil.sum(importo, vo.getImportoErogazione());
				}

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
		LOG.info(prf + " END");
		return importo;
	}

	private BigDecimal getTotaleRecuperiPerModalita(Long idProgetto, Long idModalitaAgevolazione) {
		String prf = "[ErogazioneDAOImpl::getTotaleRecuperiPerModalita]";
		LOG.info(prf + " START");
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
		LOG.info(prf + " END");
		return totaleRecuperi;
	}

	@Override
	@Transactional
	public EsitoOperazioni eliminaErogazione(Long idUtente, String idIride, Long idErogazione) throws FormalParameterException, ErogazioneException {
		String prf = "[ErogazioneDAOImpl::eliminaErogazione]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni(); 
		String[] nameParameter = { "idUtente", "idIride", "idErogazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idErogazione);
		PbandiTErogazioneVO pbandiTErogazioneVO = new PbandiTErogazioneVO();
		pbandiTErogazioneVO.setIdErogazione(NumberUtil.toBigDecimal(idErogazione));
		try {
			genericDAO.delete(pbandiTErogazioneVO);
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			throw new ErogazioneException(ErrorConstants.EROGAZIONE_NON_ELIMINABILE);
		}
		esito.setEsito(new Boolean(true));
		//esito.setParams(new String[] { MessageConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO });
		esito.setMsg(MessageConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		LOG.info(prf + " END");
		return esito;

	}

	@Override
	@Transactional
	public EsitoOperazioni modificaErogazione(Long idUtente, String idIride, Long idProgetto, ErogazioneDTO erogazione) throws FormalParameterException, ErogazioneException, UnrecoverableException {
		String prf = "[ErogazioneDAOImpl::modificaErogazione]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto", "erogazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, erogazione);

		if (isTotaleErogatoSuperioreImportoAgevolato(idProgetto, erogazione)) {
			esito.setEsito(Boolean.FALSE);
			esito.setMsg(MessageConstants.ERRORE_SOMMA_QUOTE_EROGATE_SUPERIORE_A_IMPORTO_AGEVOLATO);
			return esito;
		}

		BigDecimal importoErogazioni = getImportoErogazioniPerModalitaAgevolazionePerModifica(idProgetto, erogazione);
		try {
			if (NumberUtil.compare(importoErogazioni, contoEconomicoManager.getQuotaImportoAgevolato(new BigDecimal(idProgetto),
							erogazione.getCodModalitaAgevolazione())) > 0) {
				esito.setEsito(Boolean.FALSE);
				esito.setMsg(MessageConstants.ERRORE_SOMMA_EROGAZIONI_PER_MOD_AGEVOLAZ_SUPERIORE_IMPORTO_AGEVOLATO_CONTO_ECONOMICO);
				return esito;
			}
		} catch (ContoEconomicoNonTrovatoException ex) {
			throw new UnrecoverableException(ex.getMessage(), ex);
		}
		PbandiTErogazioneVO vo = new PbandiTErogazioneVO();
		vo.setIdErogazione(NumberUtil.toBigDecimal(erogazione.getIdErogazione()));
		vo.setImportoErogazione(BeanUtil.transformToBigDecimal(erogazione.getImportoErogazioneEffettivo()));
		vo.setDtContabile(new java.sql.Date(erogazione.getDataContabile().getTime()));
		vo.setCodRiferimentoErogazione(erogazione.getNumeroRiferimento());
		vo.setNoteErogazione(erogazione.getNoteErogazione());
		vo.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
		vo.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		String codCausale = new StringTokenizer( erogazione.getCodCausaleErogazione(), "@").nextToken();
		DecodificaDTO decodificaVO = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, codCausale);
		vo.setIdCausaleErogazione(NumberUtil.toBigDecimal(decodificaVO.getId()));
		decodificaVO = decodificheManager.findDecodifica( GestioneDatiDiDominioSrv.MODALITA_EROGAZIONE, erogazione.getCodModalitaErogazione());
		vo.setIdModalitaErogazione(NumberUtil.toBigDecimal(decodificaVO.getId()));
		decodificaVO = decodificheManager.findDecodifica( GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE, erogazione.getCodModalitaAgevolazione());
		vo.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(decodificaVO.getId()));
		// }L{ fix aggiornamento DB 31/03/2010
		if (!StringUtil.isEmpty(erogazione.getCodDirezione())) {
			PbandiTEnteCompetenzaVO ente = new PbandiTEnteCompetenzaVO();
			ente.setDescBreveEnte(erogazione.getCodDirezione());
			vo.setIdEnteCompetenza(genericDAO.findSingleWhere(ente).getIdEnteCompetenza());
		}
		try {
			vo.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(vo);
		} catch (Exception ex) {
			throw new UnrecoverableException(ex.getMessage(), ex);
		}
		esito.setEsito(new Boolean(true));
		esito.setMsg( MessageConstants.MSG_EROGAZIONE_SALVATA );
		//esito.setParams(new String[] {});
		LOG.info(prf + " END");
		return esito;
		
	}

	private boolean isTotaleErogatoSuperioreImportoAgevolato(Long idProgetto, ErogazioneDTO erogazione) {
		String prf = "[ErogazioneDAOImpl::modificaErogazione]";
		LOG.info(prf + " START");
		boolean ret = false;
		BigDecimal importoTotaleAgevolato = NumberUtil.createScaledBigDecimal(erogazione.getSpesaProgetto().getImportoAmmessoContributo());
		BigDecimal sommaTotaliImportiErogazioni = new BigDecimal(0);

		PbandiTErogazioneVO erogazioneVO = new PbandiTErogazioneVO();
		erogazioneVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		List<PbandiTErogazioneVO> erogazioni = genericDAO.findListWhere(erogazioneVO);
		/* * Calcolo il totale dei recuperi e lo agguingo all' importo totale  agevolato */
		BigDecimal totaleRecuperi = getTotaleRecuperi(idProgetto);

		importoTotaleAgevolato = NumberUtil.sum(importoTotaleAgevolato, totaleRecuperi);
		if (erogazioni != null) {
			Iterator<PbandiTErogazioneVO> iter = erogazioni.iterator();
			while (iter.hasNext()) {
				PbandiTErogazioneVO vo = (PbandiTErogazioneVO) iter.next();
				if (vo.getIdErogazione().intValue() == erogazione.getIdErogazione().intValue())
					sommaTotaliImportiErogazioni = NumberUtil.sum(
							sommaTotaliImportiErogazioni, NumberUtil.createScaledBigDecimal(erogazione.getImportoErogazioneEffettivo()));
				else
					sommaTotaliImportiErogazioni = NumberUtil.sum( sommaTotaliImportiErogazioni, NumberUtil.createScaledBigDecimal(vo.getImportoErogazione()));
			}
		}
		if (NumberUtil.compare(sommaTotaliImportiErogazioni,
				importoTotaleAgevolato) > 0) {
			ret = true;
		}
		LOG.info(prf + " END");
		return ret;
	}
	
	private BigDecimal getTotaleRecuperi(Long idProgetto) {
		String prf = "[ErogazioneDAOImpl::modificaErogazione]";
		LOG.info(prf + " START");
		PbandiTRecuperoVO recuperoVO = new PbandiTRecuperoVO();
		BigDecimal totaleRecuperi = new BigDecimal(0);
		recuperoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		List<PbandiTRecuperoVO> listRecuperi = genericDAO.findListWhere(recuperoVO);
		if (listRecuperi != null) {
			for (PbandiTRecuperoVO vo : listRecuperi) {
				totaleRecuperi = NumberUtil.sum(totaleRecuperi, vo.getImportoRecupero());
			}
		}
		LOG.info(prf + " END");
		return totaleRecuperi;
	}

	
	////////////////////////////////////////////////////////Avvio lavori / Richiesta erogazione acconto /////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	@Transactional
	public EsitoRichiestaErogazioneDTO findDatiRiepilogoRichiestaErogazione(Long idUtente, String idIride,
			Long idProgetto, String codCausaleErogazione, Long idDimensioneImpresa, Long idFormaGiuridica, Long idBandoLinea, Long idSoggetto) throws Exception {
		String prf = "[ErogazioneDAOImpl::findDatiRiepilogoRichiestaErogazione]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + "idProgetto: " + idProgetto + " codCausale: " + codCausaleErogazione + " idDimensioneImpresa: " + idDimensioneImpresa
				+"idFormaGiuridica: " + idFormaGiuridica + " idBandoLinea" + idBandoLinea + "idSoggetto" + idSoggetto);
		
		EsitoRichiestaErogazioneDTO esito = new EsitoRichiestaErogazioneDTO();
		List<String> messages = new ArrayList<String>();
		it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazioneDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO();
		it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO spesaProgetto = new it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO();
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto", "idBandoLinea", "idSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, idBandoLinea, idSoggetto);
			BandoLineaVO bandoLineaVO = new BandoLineaVO();
			bandoLineaVO.setProgrBandoLineaIntervento(new BigDecimal(idBandoLinea));
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);
			PbandiDCausaleErogazioneVO pbandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
			pbandiDCausaleErogazioneVO.setDescBreveCausale(codCausaleErogazione);
			pbandiDCausaleErogazioneVO = genericDAO.findSingleWhere(pbandiDCausaleErogazioneVO);
			BigDecimal idCausaleErogazione = pbandiDCausaleErogazioneVO.getIdCausaleErogazione();
			PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = getBandiRBandoCausaleErogazioneVO(idCausaleErogazione, idDimensioneImpresa, idFormaGiuridica,bandoLineaVO);
			
			if (bandiRBandoCausaleErogazVO == null) {
				// ECCEZIONE CON UN MESSAGGIO D'ERRORE
				throw new RichiestaErogazioneException(ErrorMessages.ERRORE_BANDO_NON_GESTISCE_RICHIESTA_EROGAZIONE);

			}

			LOG.info(prf + " =============================== DATI DELLA SPESA PROGETTO ===============================");
			//////////////////////////////////// DATI DELLA SPESA PROGETTO ////////////////////////////////////
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(new BigDecimal(idProgetto));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			Double totaleSpesaAmmessa = contoEconomicoManager.getTotaleSpesaAmmmessaInRendicontazioneDouble(new BigDecimal(idProgetto));
			spesaProgetto.setImportoAmmessoContributo(contoEconomicoManager.findImportoAgevolato(idProgetto));
			if (spesaProgetto.getImportoAmmessoContributo() == null) {
				throw new RichiestaErogazioneException(ErrorMessages.ERRORE_EROGAZIONE_IMPORTO_AGEVOLATO_NULLO);
			}
			
			DocumentoDiSpesaDTO documentoDiSpesa = new DocumentoDiSpesaDTO();
			documentoDiSpesa.setIdProgetto(idProgetto);
			documentoDiSpesa.setIsGestitiNelProgetto(new Boolean(true));
			double sommaImportiQuietanzati = 0;
			double sommaImportiValidati = 0;
			List<DocumentoDiSpesaVO> list = pbandiDocumentiDiSpesaDAO.findDocumentiDiSpesa(documentoDiSpesa, null);
			Iterator<DocumentoDiSpesaVO> iter = list.iterator();
			while (iter.hasNext()) {
				DocumentoDiSpesaVO obj = (DocumentoDiSpesaVO) iter.next();
				List<PagamentoQuotePartiVO> listPagamenti = pbandipagamentiDAO.findPagamentiAssociati(obj.getIdDocumentoDiSpesa(), idProgetto);
				Iterator<PagamentoQuotePartiVO> iterPagamenti = listPagamenti.iterator();

				while (iterPagamenti.hasNext()) {
					PagamentoQuotePartiVO objPagamento = (PagamentoQuotePartiVO) iterPagamenti.next();
					/** FIX PBandi-2351 */
					Double importoQuietanzato = pbandipagamentiDAO.getImportoQuietanzato(idProgetto,NumberUtil.toLong(objPagamento.getIdPagamento()));
					if (importoQuietanzato != null) {
						sommaImportiQuietanzati += importoQuietanzato.doubleValue();
					}
					/** FIX PBandi-2351 */
					Double importoValidato = pbandipagamentiDAO.getImportoValidato(idProgetto,NumberUtil.toLong(objPagamento.getIdPagamento()));
					if (importoValidato != null) {
						sommaImportiValidati += importoValidato.doubleValue();
					}

				}

				if (sommaImportiQuietanzati > 0) {
					spesaProgetto.setTotaleSpesaSostenuta(sommaImportiQuietanzati);
					BigDecimal div = NumberUtil.divide(new BigDecimal(sommaImportiQuietanzati), new BigDecimal(totaleSpesaAmmessa));
					BigDecimal ret = NumberUtil.multiply(div, new BigDecimal(100));
					spesaProgetto.setAvanzamentoSpesaSostenuta(NumberUtil.toDouble(ret));
				}
				if (sommaImportiValidati > 0) {
					spesaProgetto.setTotaleSpesaValidata(sommaImportiValidati);
					BigDecimal div = NumberUtil.divide(new BigDecimal(sommaImportiValidati), new BigDecimal(totaleSpesaAmmessa));
					BigDecimal ret = NumberUtil.multiply(div, new BigDecimal(100));
					spesaProgetto.setAvanzamentoSpesaValidata(NumberUtil.toDouble(ret));
				}
			}
			spesaProgetto.setAvanzamentoSpesaPrevistaBando(NumberUtil.toRoundBigDecimal(bandiRBandoCausaleErogazVO.getPercSogliaSpesaQuietanzata()));
			Double importoSpesaDaRaggiungere = 0D;
			if (totaleSpesaAmmessa != null && spesaProgetto.getAvanzamentoSpesaPrevistaBando().doubleValue() > 0) {
				// }L{ PBANDI-1064
				// importoSpesaDaRaggiungere =
				// NumberUtil.toRoundDouble(spesaProgetto.getTotaleSpesaSostenuta().doubleValue()*spesaProgetto.getAvanzamentoSpesaPrevistaBando().doubleValue()/100);
				importoSpesaDaRaggiungere = NumberUtil.toRoundDouble(totaleSpesaAmmessa * spesaProgetto.getAvanzamentoSpesaPrevistaBando().doubleValue() / 100);
			}
			spesaProgetto.setImportoSpesaDaRaggiungere(importoSpesaDaRaggiungere);
			richiestaErogazioneDTO.setSpesaProgetto(spesaProgetto);
									
			////////////////////////////////////// DATI FIDEIUSSIONI ////////////////////////////////////
			LOG.info(prf + " =============================== DATI FIDEIUSSIONI ===============================");
			FideiussioneVO[] fideiussioniVO = pbandiErogazioneDAO.findFideiussioniAttive(idProgetto);
			ArrayList<FideiussioneVO> fideiussioniFiltrateVO = new ArrayList();
			if (fideiussioniVO != null) {
				for (int i = 0; i < fideiussioniVO.length; i++) {
					FideiussioneVO vo = fideiussioniVO[i];
					if (codCausaleErogazione.equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO) && vo.getDescBreveTipoTrattamento().equals(Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO)) {
						fideiussioniFiltrateVO.add(vo);
					} else if (codCausaleErogazione.equals(Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO) && (vo.getDescBreveTipoTrattamento().equals(Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
						fideiussioniFiltrateVO.add(vo);
					} else if (codCausaleErogazione.equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO) && (vo.getDescBreveTipoTrattamento().equals(Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
						fideiussioniFiltrateVO.add(vo);
					} else if (codCausaleErogazione.equals(Constants.COD_CAUSALE_EROGAZIONE_SALDO) && (vo.getDescBreveTipoTrattamento().equals(Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_TRE))) {
						fideiussioniFiltrateVO.add(vo);
					}
				}
			}
			if (fideiussioniFiltrateVO != null) {
				FideiussioneVO[] fideiussioniValide = fideiussioniFiltrateVO.toArray(new FideiussioneVO[] {});
				it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] fideiussioniDTO = new it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[fideiussioniValide.length];
				beanUtil.valueCopy(fideiussioniValide, fideiussioniDTO);
				richiestaErogazioneDTO.setFideiussioni(fideiussioniDTO);
			}

			FideiussioneVO[] fideiussioniTipoTrattamentoVO = pbandiErogazioneDAO.findFideiussioniAttivePerTipoTrattamento(idProgetto);
			if (fideiussioniTipoTrattamentoVO != null) {
				FideiussioneTipoTrattamentoDTO[] fideiussioniDTO = new FideiussioneTipoTrattamentoDTO[fideiussioniTipoTrattamentoVO.length];
				beanUtil.valueCopy(fideiussioniTipoTrattamentoVO, fideiussioniDTO);
				richiestaErogazioneDTO.setFideiussioniTipoTrattamento(fideiussioniDTO);
			}
			
			
			LOG.info(prf + " =============================== DATI EROGAZIONE ===============================");
			////////////////////////////////////// DATI EROGAZIONE //////////////////////////////////////
			PbandiDCausaleErogazioneVO pBandiDCausaliErogazioneVO = new PbandiDCausaleErogazioneVO();
			pBandiDCausaliErogazioneVO.setIdCausaleErogazione(idCausaleErogazione);
			pBandiDCausaliErogazioneVO = genericDAO.findSingleWhere(pBandiDCausaliErogazioneVO);
			richiestaErogazioneDTO.setDescrizioneCausaleErogazione(pBandiDCausaliErogazioneVO.getDescCausale());
			richiestaErogazioneDTO.setDescBreveCausaleErogazione(codCausaleErogazione);
						
			Map<String, String> map = new HashMap<String, String>();
			Double importo = null;
			if (bandiRBandoCausaleErogazVO.getPercErogazione() != null && bandiRBandoCausaleErogazVO.getPercErogazione().intValue() > 0) {
				BigDecimal per = NumberUtil.multiply(new BigDecimal( spesaProgetto.getImportoAmmessoContributo()), bandiRBandoCausaleErogazVO.getPercErogazione());
				BigDecimal ret = NumberUtil.divide(per, new BigDecimal(100));
				importo = NumberUtil.toDouble(ret);
				map.put("percErogazione", "percentualeErogazione");
			} else {
				BigDecimal per = NumberUtil.multiply(new BigDecimal( spesaProgetto.getImportoAmmessoContributo()), bandiRBandoCausaleErogazVO.getPercLimite());
				BigDecimal ret = NumberUtil.divide(per, new BigDecimal(100));
				importo = NumberUtil.toDouble(ret);
				map.put("percLimite", "percentualeLimite");
			}
			
			try {
				beanUtil.valueCopy(bandiRBandoCausaleErogazVO, richiestaErogazioneDTO, map);
			} catch (Exception ex) {
				throw new UnrecoverableException(ex.getMessage());
			}
			richiestaErogazioneDTO.setImportoRichiesto(importo);
			
			/*
			BigDecimal idMacroAllegati = decodificheManager.decodeDescBreve(PbandiDMacroSezioneModuloVO.class, GestioneDatiDiDominioSrv.DESC_BREVE_MACRO_SEZ_ALLEGATI);					
			AllegatoVO allegatoVO = new AllegatoVO();
			allegatoVO.setIdMacroSezioneModulo(idMacroAllegati);
			
			BigDecimal idTipoDocIndex = getIdTipoDocIndex(codCausaleErogazione);
			allegatoVO.setIdTipoDocumentoIndex(idTipoDocIndex);
			allegatoVO.setAscendentOrder("numOrdinamentoMicroSezione");
			allegatoVO.setProgrBandoLineaIntervento(new BigDecimal(idBandoLinea));
			
			AllegatoVO[] allegati = genericDAO.findWhere(allegatoVO);
			Map <String,String> mapProps=new HashMap<String,String>();
			mapProps.put("contenutoMicroSezione","descTipoAllegato");
			TipoAllegatoDTO[]tipoAllegati=beanUtil.transform(allegati, TipoAllegatoDTO.class,mapProps);			
			richiestaErogazioneDTO.setTipoAllegati(tipoAllegati);
			*/
			
			LOG.info(prf + " =============================== TIPI DOCUMENTI ALLEGATI ==========================");
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] tipoAllegatiCompleti = null;
			tipoAllegatiCompleti = gestioneDatiGeneraliBusinessImpl.findTipoAllegatiErogazione(
					idUtente, idIride, idBandoLinea, codCausaleErogazione, idProgetto);
			if (tipoAllegatiCompleti != null) {
				for(it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO dto : tipoAllegatiCompleti) {
					if (dto.getDescTipoAllegato().startsWith("<b>")) {
						dto.setFlagAllegato("S");
					}
				}
			}
			richiestaErogazioneDTO.setTipoAllegatiCompleti(tipoAllegatiCompleti);
			
			LOG.info(prf + " =============================== DOCUMENTI ALLEGATI ===============================");			
			FileDTO[] fileAllegati = this.getFilesAssociatedRichiestaErogazioneByIdProgetto(idProgetto);
			richiestaErogazioneDTO.setAllegati(fileAllegati);
			
			LOG.info(prf + " =============================== DATI ESTREMI BANCARI ===============================");
			////////////////////////////////////// DATI ESTREMI BANCARI //////////////////////////////////////
			PbandiRSoggettoProgettoVO pbandiRSoggettoProgettoVO = new PbandiRSoggettoProgettoVO();
			pbandiRSoggettoProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
			pbandiRSoggettoProgettoVO.setIdSoggetto(new BigDecimal(idSoggetto));
			pbandiRSoggettoProgettoVO.setDtFineValidita(null);
			pbandiRSoggettoProgettoVO.setIdTipoAnagrafica(new BigDecimal(1));
			PbandiRSoggettoProgettoVO filtro = new PbandiRSoggettoProgettoVO();
			filtro.setIdTipoBeneficiario(new BigDecimal(4));

			List<PbandiRSoggettoProgettoVO> listSoggettoProgetto = genericDAO.findListWhere(new AndCondition<PbandiRSoggettoProgettoVO>(
							new FilterCondition<PbandiRSoggettoProgettoVO>(
									pbandiRSoggettoProgettoVO),
							new NotCondition<PbandiRSoggettoProgettoVO>(
									new FilterCondition<PbandiRSoggettoProgettoVO>(
											filtro))));
			if (listSoggettoProgetto != null && !listSoggettoProgetto.isEmpty()) {
				LOG.info(prf + " listSoggettoProgetto not null");
				PbandiRSoggettoProgettoVO vo = listSoggettoProgetto.get(0);
				if (vo.getIdEstremiBancari() != null) {
					LOG.info(prf + " idEstremiBancari not null");
					EstremiBancariDTO estremiBancariDTO = new EstremiBancariDTO();
					PbandiTEstremiBancariVO pbandiTEstremiBancariVO = new PbandiTEstremiBancariVO();
					pbandiTEstremiBancariVO.setIdEstremiBancari(vo.getIdEstremiBancari());
					pbandiTEstremiBancariVO = genericDAO.findSingleWhere(pbandiTEstremiBancariVO);					
					BeanUtil.valueCopy(pbandiTEstremiBancariVO, estremiBancariDTO);
					if (pbandiTEstremiBancariVO.getIdBanca() != null) {
						PbandiDBancaVO pbandiBancaVO = new PbandiDBancaVO();
						pbandiBancaVO.setIdBanca(pbandiTEstremiBancariVO.getIdBanca());
						pbandiBancaVO = genericDAO.findSingleWhere(pbandiBancaVO);
						estremiBancariDTO.setDescrizioneBanca(pbandiBancaVO.getDescBanca());
					}
					if (pbandiTEstremiBancariVO.getIdAgenzia() != null) {
						PbandiTAgenziaVO pbandiTAgenziaVO = new PbandiTAgenziaVO();
						pbandiTAgenziaVO.setIdAgenzia(pbandiTEstremiBancariVO.getIdAgenzia());
						pbandiTAgenziaVO = genericDAO.findSingleWhere(pbandiTAgenziaVO);
						estremiBancariDTO.setDescrizioneAgenzia(pbandiTAgenziaVO.getDescAgenzia());

					}

					richiestaErogazioneDTO.setEstremiBancari(estremiBancariDTO);
				}
			}
			
			LOG.info(prf + " GESTIONE MESSAGGI D'ERRORE");
			//////////////////////////////////// GESTIONE MESSAGGI D'ERRORE ////////////////////////////////////
			if ((spesaProgetto.getTotaleSpesaSostenuta() == null || spesaProgetto.getTotaleSpesaSostenuta().doubleValue() == 0)
					&& (spesaProgetto.getAvanzamentoSpesaPrevistaBando() == null || spesaProgetto.getAvanzamentoSpesaPrevistaBando() == 0)
					&& (richiestaErogazioneDTO.getFideiussioni() == null || richiestaErogazioneDTO.getFideiussioni().length == 0)) {
				messages.add(ErrorMessages.ERRORE_EROGAZIONE_SPESA_SOSTENUTA_ZERO);
			} else {
				if (NumberUtil.compare(spesaProgetto.getAvanzamentoSpesaSostenuta(), spesaProgetto
						.getAvanzamentoSpesaPrevistaBando()) < 0) {
					if (checkSogliaAvanzamentoPerCodCausaleErogazione( codCausaleErogazione, messages, richiestaErogazioneDTO, spesaProgetto)) {
						if (NumberUtil.compare( getImportoTotaleFideiussioni(richiestaErogazioneDTO), new BigDecimal(richiestaErogazioneDTO.getImportoRichiesto())) < 0) {
							messages.add(ErrorMessages.ERRORE_EROGAZIONE_IMPORTO_FIDEIUSSIONE_MINORE_ANTICIPO);
						}
					}
				}
			}
					
			LOG.info(prf + " GESTIONE REGOLA PER EROGAZIONE");
			/** GESTIONE REGOLA PER EROGAZIONE */
			boolean isRegolaApplicabile = regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea, RegoleConstants.BR15_ATTIVITA_RICHIESTA_EROGAZIONE_DISPONIBILE);
			esito.setEsito(true);
			esito.setIsRegolaAttiva(isRegolaApplicabile);
			esito.setMessages(messages.toArray(new String[] {}));
			esito.setRichiestaErogazione(richiestaErogazioneDTO);		
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			throw e;
		}
			
		
	}
	
	private FileDTO createFileDTO(ArchivioFileVO file) {
		FileDTO fileDTO=new FileDTO();
		fileDTO.setDtAggiornamento(file.getDtAggiornamentoFile());
		fileDTO.setDtInserimento(file.getDtInserimentoFile());
		if (file.getIdDocumentoIndex() != null)
			fileDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		if (file.getIdFolder() != null)
			fileDTO.setIdFolder(file.getIdFolder().longValue());
		fileDTO.setNomeFile(file.getNomeFile());
		fileDTO.setSizeFile(file.getSizeFile()!=null?file.getSizeFile().longValue():0l);
		if (file.getIdProgetto() != null)
			fileDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		if(file.getEntitiesAssociated()!=null && file.getEntitiesAssociated().longValue()>0){
			fileDTO.setEntityAssociated(file.getEntitiesAssociated().longValue());
		}
		if(file.getIslocked()!=null && file.getIslocked().equalsIgnoreCase("S"))
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
		fileDTO.setFlagEntita(file.getFlagEntita());	//Jira PBANDI-2815
		
		fileDTO.setDescStatoTipoDocIndex(file.getDescStatoTipoDocIndex());

		return fileDTO;
	}
	
	private BigDecimal getImportoTotaleFideiussioni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazioneDTO) {
		FideiussioneTipoTrattamentoDTO[] fideiussioni = richiestaErogazioneDTO.getFideiussioniTipoTrattamento();
		BigDecimal importo = new BigDecimal(0);
		if (fideiussioni != null) {
			for (int i = 0; i < fideiussioni.length; i++) {
				FideiussioneTipoTrattamentoDTO dto = fideiussioni[i];
				importo = NumberUtil.sum(importo, new BigDecimal(dto.getImporto()));

			}
		}
		return importo;
	}
	
	private boolean checkSogliaAvanzamentoPerCodCausaleErogazione( String codCausaleErogazione, List<String> messages,
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazioneDTO,
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO spesaProgetto) {
		boolean ret = true;
		if (codCausaleErogazione.equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)) {
			if ((NumberUtil.compare(spesaProgetto.getAvanzamentoSpesaSostenuta(), spesaProgetto.getAvanzamentoSpesaPrevistaBando()) < 0)
					&& (!existFideiussioniTipoTrattamento( richiestaErogazioneDTO, Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO))) {
				ret = false;
				messages.add(ErrorMessages.ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
			}
		} else if (codCausaleErogazione.equals(Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)) {
			if ((NumberUtil.compare(spesaProgetto.getAvanzamentoSpesaSostenuta(), spesaProgetto.getAvanzamentoSpesaPrevistaBando()) < 0)
					&& (!existFideiussioniTipoTrattamento( richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO) || !existFideiussioniTipoTrattamento(
							richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
				ret = false;
				messages.add(ErrorMessages.ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
			}
		} else if (codCausaleErogazione.equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)) {
			if ((NumberUtil.compare(spesaProgetto.getAvanzamentoSpesaSostenuta(), spesaProgetto.getAvanzamentoSpesaPrevistaBando()) < 0)
					&& (!existFideiussioniTipoTrattamento( richiestaErogazioneDTO, Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO) || !existFideiussioniTipoTrattamento( richiestaErogazioneDTO,
							Constants.COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE))) {
				ret = false;
				messages
						.add(ErrorMessages.ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
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
						.add(ErrorMessages.ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE);
			}
		}
		return ret;
	}
	
	private boolean existFideiussioniTipoTrattamento(
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazioneDTO,
			String codTipoFideiussione) {
		FideiussioneTipoTrattamentoDTO[] fideiussioni = richiestaErogazioneDTO.getFideiussioniTipoTrattamento();
		boolean ret = false;
		if (fideiussioni != null) {
			for (int i = 0; i < fideiussioni.length; i++) {
				FideiussioneTipoTrattamentoDTO dto = fideiussioni[i];
				if (dto.getDescBreveTipoTrattamento().equals(codTipoFideiussione)) {
					ret = true;
				}
			}
		}
		return ret;
	}
	
	public BigDecimal getIdTipoDocIndex(String codCausaleErogazione) {
		BigDecimal idTipoDocIndex=null;
//		id_caus= 1(PA) , id_doc_ind= 20 , RA
//		id_caus= 2(SA) , id_doc_ind= 19 , RUA
//		id_caus= 3(UA) , id_doc_ind= 18 , R2A
		if (codCausaleErogazione.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, Constants.COD_RICHIESTA_ANTICIPAZIONE);
		} else if (codCausaleErogazione.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, Constants.COD_RICHIESTA_II_ACCONTO);
		} else if (codCausaleErogazione.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, Constants.COD_RICHIESTA_ULTERIORE_ACCONTO);
		} else if (codCausaleErogazione.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_SALDO)) {
			idTipoDocIndex=	decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, Constants.COD_RICHIESTA_SALDO);
		}
		LOG.info("getIdTipoDocIndex(): codCausaleErogazione = "+codCausaleErogazione+"; idTipoDocIndex = "+idTipoDocIndex);
		return idTipoDocIndex;
	}
	
	private PbandiRBandoCausaleErogazVO getBandiRBandoCausaleErogazioneVO( BigDecimal idCausaleErogazione, Long idDimensioneImpresa, Long idFormaGiuridica, BandoLineaVO bandoLineaVO) {
		List<PbandiRBandoCausaleErogazVO> l = new ArrayList<PbandiRBandoCausaleErogazVO>();
		PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = null;

		if (idFormaGiuridica != null) {
			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdCausaleErogazione(idCausaleErogazione);
			bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
			bandiRBandoCausaleErogazVO.setIdFormaGiuridica(new BigDecimal(idFormaGiuridica));
			l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
			if (l == null || l.isEmpty()) {
				if (idDimensioneImpresa != null) {
					bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
					bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
					bandiRBandoCausaleErogazVO.setIdCausaleErogazione(idCausaleErogazione);
					bandiRBandoCausaleErogazVO.setIdDimensioneImpresa(new BigDecimal(idDimensioneImpresa));
					l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
					if (l == null || l.isEmpty()) {
						bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
						bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
						bandiRBandoCausaleErogazVO.setIdCausaleErogazione(idCausaleErogazione);
						l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
					}
				} else {
					/*
					 * FIX PBANDI-2383
					 */
					if (l == null || l.isEmpty()) {
						bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
						bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
						bandiRBandoCausaleErogazVO.setIdCausaleErogazione(idCausaleErogazione);
						l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
					}
				}

			}
		} else {
			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
			bandiRBandoCausaleErogazVO.setIdCausaleErogazione(idCausaleErogazione);
			l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
		}
		if (l != null && !l.isEmpty())
			return l.get(0);
		else
			return null;
	}

	@Override
	@Transactional
	public RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente, String idIride, Long idProgetto,
			Long idSoggettoRappresentante) {
		String prf = "[ErogazioneDAOImpl::findRappresentantiLegali]";
		LOG.info(prf + " BEGIN");
		List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentantiVO = soggettoManager.findRappresentantiLegali(idProgetto, idSoggettoRappresentante);
		LOG.info(prf + " END");
		return beanUtil.transform(rappresentantiVO, RappresentanteLegaleDTO.class);

	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoReportRichiestaErogazioneDTO creaReportRichiestaErogazione(Long idUtente, String idIride,
			Long idProgetto,
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazioneDTO,
			RappresentanteLegaleDTO rappresentanteLegaleDTO, Long idDelegato, Long idSoggetto, List<PbandiTAffidServtecArVO> affidamentiServiziLavori) throws java.lang.Exception {
		String prf = "[ErogazioneDAOImpl::creaReportRichiestaErogazione]";
		LOG.info(prf + " BEGIN");
		
		EsitoReportRichiestaErogazioneDTO esito = new EsitoReportRichiestaErogazioneDTO();
		LOG.info(prf + "  creaReportRichiestaErogazione --> idDelegato: "+idDelegato);
		
		RichiestaErogazioneReportDTO richiestaErogazioneReportDTO = new RichiestaErogazioneReportDTO();
		
		try {
			
			// Salvo i tipi allegato: nel vecchio erano salvati puntualmente al momento della
			// selezione\deselezione dell'utente: ora sono salvati tutti insieme al Salva.
			// NB: primo acconto, secondo e successivo prevedono tipi allegato, il saldo no.
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] tipoAllegati = richiestaErogazioneDTO.getTipoAllegatiCompleti();
			if (tipoAllegati != null && tipoAllegati.length > 0) {
				LOG.info(prf + "Salvataggio tipi allgato - inizio");
				for(int i = 0; i < tipoAllegati.length; i++) {
					tipoAllegati[i].setIdProgetto(idProgetto);
				}
				it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.EsitoOperazioneSalvaTipoAllegati esitoTipiAllegati;
				esitoTipiAllegati = gestioneDatiGeneraliBusinessImpl.salvaTipoAllegati(idUtente, idIride, tipoAllegati);
				if (esitoTipiAllegati == null || !esitoTipiAllegati.getEsito()) {
					throw new Exception("Errore durante il salvataggio dei tipi allegato.");
				}
				LOG.info(prf + "Salvataggio tipi allgato - fine");
			} else {
				LOG.info(prf + "Nessun tipo allgato in input.");
			}
					
			/*** Carico i dati dell' ente di appartenenza */
			EnteAppartenenzaDTO ente = findEnteAppartenenza(idProgetto, DichiarazioneDiSpesaSrv.CODICE_RUOLO_ENTE_DESTINATARIO);
	
			/**  Carico i dati del beneficiario */
			BeneficiarioVO beneficiarioVO = pbandiDichiarazioneDiSpesaDAO.findBeneficiario(idSoggetto, idProgetto);
			BeneficiarioDTO beneficiario = new BeneficiarioDTO();
			beanUtil.valueCopy(beneficiarioVO, beneficiario);
	
			/** Carico dati del progetto */
			ProgettoDTO progetto = new ProgettoDTO();
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
			pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
			beanUtil.valueCopy(pbandiTProgettoVO, progetto);
				
			Double totaleQuietanzato = null;
			try {
				ContoEconomicoDTO contoEconomicoMaster =  contoEconomicoManager.findContoEconomicoMaster(new BigDecimal(idProgetto));
				
				LOG.info(prf + "Contributo definitivo = " + contoEconomicoMaster.getImportoImpegnoVincolante()); //PK TODO usarlo e passarlo in jasper "contributo_definitivo"
				
				totaleQuietanzato=NumberUtil.toDouble(contoEconomicoMaster.getImportoQuietanzato());
			} catch (ContoEconomicoNonTrovatoException e1) {
				LOG.warn( prf + " Attenzione!!! Non trovato conto economico master per progetto "+idProgetto);
			}
			
			BigDecimal percentuale_spesa=null;
			Double sogliaSpesaCalcErogazioni = NumberUtil.toDouble(pbandiTProgettoVO.getSogliaSpesaCalcErogazioni());
			if(sogliaSpesaCalcErogazioni!=null &&
					sogliaSpesaCalcErogazioni>0	){
				if(totaleQuietanzato!=null &&  totaleQuietanzato > 0){							
					BigDecimal divide = NumberUtil.divide(totaleQuietanzato, sogliaSpesaCalcErogazioni);
					percentuale_spesa= NumberUtil.multiply(divide,new BigDecimal(100));
				}
				
			} else{
			
				try {
					ContoEconomicoDTO contoEconomicoMaster = contoEconomicoManager.findContoEconomicoMaster(new BigDecimal(idProgetto));
					if(contoEconomicoMaster!=null){
						Double importoSpesaAmmessaUltima = NumberUtil.toDouble(contoEconomicoMaster.getImportoAmmesso());
						if(importoSpesaAmmessaUltima!=null && importoSpesaAmmessaUltima>0){
							BigDecimal divide = NumberUtil.divide(totaleQuietanzato, importoSpesaAmmessaUltima);
							percentuale_spesa= NumberUtil.multiply(divide,new BigDecimal(100));
						}
					}					
				} catch (ContoEconomicoNonTrovatoException e) {
					LOG.warn(prf + " L'ORRORE! non trovato il conto economico master per il progetto "+idProgetto);
				}			
			}
			
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_TOTALE_QUIETANZIATO, totaleQuietanzato);			
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PERCENTUALE_SPESA, NumberUtil.toDouble(percentuale_spesa));
			
			richiestaErogazioneReportDTO.setBeneficiario(beneficiario);
			richiestaErogazioneReportDTO.setEnte(ente);
			richiestaErogazioneReportDTO.setRappresentanteLegale(rappresentanteLegaleDTO);
			richiestaErogazioneReportDTO.setProgetto(progetto);
			richiestaErogazioneReportDTO.setEstremiBancari(richiestaErogazioneDTO.getEstremiBancari());
			richiestaErogazioneReportDTO.setFideiussioni(richiestaErogazioneDTO.getFideiussioni());
			richiestaErogazioneReportDTO.setAllegati(richiestaErogazioneDTO.getTipoAllegati());
			richiestaErogazioneReportDTO.setDescCausaleErogazione(richiestaErogazioneDTO.getDescrizioneCausaleErogazione());
			richiestaErogazioneReportDTO.setPercentualeErogazione(richiestaErogazioneDTO.getPercentualeErogazione());
			richiestaErogazioneReportDTO.setImportoRichiesto(richiestaErogazioneDTO.getImportoRichiesto());
			richiestaErogazioneReportDTO.setImportoTotaleSpesaQuietanzata(richiestaErogazioneDTO.getSpesaProgetto().getTotaleSpesaSostenuta());
			
			richiestaErogazioneReportDTO.setDataInizioLavori(richiestaErogazioneDTO.getDtInizioLavori());
			richiestaErogazioneReportDTO.setDataStipulazioneContratti(richiestaErogazioneDTO.getDtStipulazioneContratti());
			richiestaErogazioneReportDTO.setResidenzaDirettoreLavori(richiestaErogazioneDTO.getResidenzaDirettoreLavori());
			richiestaErogazioneReportDTO.setDirettoreLavori(richiestaErogazioneDTO.getDirettoreLavori());
			
			Node nodoCreato = null;
			try {
				// DATA DI SISTEMA PER LE VARIE INSERT e PER IL NOME FILE
				Date currentDate = new Date(System.currentTimeMillis());
				// INSERT SU DB
				PbandiTRichiestaErogazioneVO richiestaErogazioneVO = new PbandiTRichiestaErogazioneVO();
				PbandiDCausaleErogazioneVO pbandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
				pbandiDCausaleErogazioneVO.setDescBreveCausale(richiestaErogazioneDTO.getDescBreveCausaleErogazione());
				pbandiDCausaleErogazioneVO = genericDAO.findSingleWhere(pbandiDCausaleErogazioneVO);
				richiestaErogazioneVO.setNumeroRichiestaErogazione(new BigDecimal(0));				
				richiestaErogazioneVO.setIdCausaleErogazione(pbandiDCausaleErogazioneVO.getIdCausaleErogazione());
				richiestaErogazioneVO.setIdProgetto(new BigDecimal(idProgetto));
				richiestaErogazioneVO.setDtRichiestaErogazione(DateUtil.utilToSqlDate(currentDate));
				richiestaErogazioneVO.setImportoErogazioneRichiesto(new BigDecimal(richiestaErogazioneDTO.getImportoRichiesto()));
				richiestaErogazioneVO.setIdUtenteIns(new BigDecimal(idUtente));
				
				LOG.info(prf + " Data Inizio Lavori(java.util): "+ richiestaErogazioneDTO.getDtInizioLavori());
				LOG.info(prf + " Data Stipulazione Lavori(java.util): "+ richiestaErogazioneDTO.getDtStipulazioneContratti());				
				LOG.info(prf + " Data Inizio Lavori(java.sql): "+ DateUtil.utilToSqlDate(richiestaErogazioneDTO.getDtInizioLavori()) );
				LOG.info(prf + " Data Stipulazione Lavori(java.sql): "+ DateUtil.utilToSqlDate(richiestaErogazioneDTO.getDtStipulazioneContratti()) );
				
				richiestaErogazioneVO.setDtInizioLavori(DateUtil.utilToSqlDate(richiestaErogazioneDTO.getDtInizioLavori()));
				richiestaErogazioneVO.setDtStipulazioneContratti(DateUtil.utilToSqlDate(richiestaErogazioneDTO.getDtStipulazioneContratti()));
				richiestaErogazioneVO.setDirettoreLavori(richiestaErogazioneDTO.getDirettoreLavori());
				richiestaErogazioneVO.setResidenzaDirettoreLavori(richiestaErogazioneDTO.getResidenzaDirettoreLavori());
				LOG.info(prf + " BEFORE : INSERT SU PBANDI_T_RICHIESTA_EROGAZIONE SENZA NUMERO RICHIESTA ");
				richiestaErogazioneVO = genericDAO.insert(richiestaErogazioneVO);
				LOG.info(prf + " AFTER : INSERT SU PBANDI_T_RICHIESTA_EROGAZIONE SENZA NUMERO RICHIESTA ");
				// UPDATE SU PBANDI_T_RICHIESTA_EROGAZIONE CON NUMERO RICHIESTA
				richiestaErogazioneVO.setNumeroRichiestaErogazione(richiestaErogazioneVO.getIdRichiestaErogazione());
				richiestaErogazioneVO.setIdUtenteAgg(new BigDecimal(idUtente));
				LOG.info(prf + " BEFORE : UPDATE SU PBANDI_T_RICHIESTA_EROGAZIONE CON NUMERO RICHIESTA ");
				genericDAO.update(richiestaErogazioneVO);
				LOG.info(prf + " AFTER : UPDATE SU PBANDI_T_RICHIESTA_EROGAZIONE CON NUMERO RICHIESTA ");
				richiestaErogazioneReportDTO.setNumeroRichiestaErogazione(NumberUtil.toLong(richiestaErogazioneVO.getIdRichiestaErogazione()));
				
				if (affidamentiServiziLavori != null && affidamentiServiziLavori.size() > 0) {
					for (PbandiTAffidServtecArVO aff : affidamentiServiziLavori) {
						aff.setIdRichiestaErogazione(new Long(richiestaErogazioneVO.getIdRichiestaErogazione().longValue()));
						genericDAO.insert(aff);
						LOG.info("inserito PbandiTAffidServtecArVO.fornitore="+aff.getFornitore());
					}
				}else {
					LOG.info("affidamentiServiziLavori null o vuoto");
				}
				
				String tipoModello = getTipoModello(richiestaErogazioneDTO.getDescBreveCausaleErogazione());	
				LOG.info(prf + " tipoModello="+tipoModello);
				popolaTemplateManager.setTipoModello(tipoModello);
				
				if(idDelegato!=null) {
					LOG.info(prf + " idDelegato is not null ");
					popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_RAPPRESENTANTE_LEGALE, idDelegato);
						 LOG.info("il delegato non e' NULL "+idDelegato+", lo metto al posto del rapp legale");
						 DelegatoVO delegatoVO = new DelegatoVO();
						 delegatoVO.setIdSoggetto(idDelegato);
						 delegatoVO.setIdProgetto(idProgetto);
					     List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
					     if(delegati!=null && !delegati.isEmpty()){
					    	delegatoVO=delegati.get(0);
					     }
					     it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegale = beanUtil.transform(delegatoVO, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
						 popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegale);
				} else {		
					 LOG.info(prf + " idDelegato is null ");
					 it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegale = rappresentanteLegaleManager.findRappresentanteLegale(idProgetto, 
							 rappresentanteLegaleDTO.getIdSoggetto());
						LOG.info(prf + " After rappresentanteLegaleManager.findRappresentanteLegale, rappresentanteLegale: "+ rappresentanteLegale.getCognome());
					 popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegale);
					 popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_RAPPRESENTANTE_LEGALE, rappresentanteLegaleDTO.getIdSoggetto());
				}
								
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_DATA_RICHIESTA_EROG, currentDate);

				//anticipazione,II acconto, ulteriore acconto, saldo				
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_IMPORTO_RICHIESTA_SALDO,richiestaErogazioneReportDTO.getImportoRichiesto());				
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RICHIESTA_EROGAZIONE, richiestaErogazioneReportDTO);
				
				BigDecimal perc_raggiung_spesa=null;
				
				LOG.info(prf + " BEFORE:  progettoManager.getProgettoBandoLinea");		
				ProgettoBandoLineaVO progettoBandoLinea = progettoManager.getProgettoBandoLinea(idProgetto);
				LOG.info(prf + " AFTER:  progettoManager.getProgettoBandoLinea");	
				Long progBandoLinea=progettoBandoLinea.getIdBandoLinea();
				LOG.info(prf + " progBandoLinea: " + progBandoLinea);	
				
				LOG.info(prf + " BEFORE: genericDAO.findSingleWhere(bandoLineaInterventVO) \n");	
				PbandiRBandoLineaInterventVO bandoLineaInterventVO=new PbandiRBandoLineaInterventVO();				
				bandoLineaInterventVO.setProgrBandoLineaIntervento(new BigDecimal(progBandoLinea));				
				bandoLineaInterventVO=genericDAO.findSingleWhere(bandoLineaInterventVO);								
				PbandiRBandoCausaleErogazVO bandoCausaleErogazVO=new PbandiRBandoCausaleErogazVO();
				bandoCausaleErogazVO.setIdCausaleErogazione(pbandiDCausaleErogazioneVO.getIdCausaleErogazione());
				bandoCausaleErogazVO.setIdBando(bandoLineaInterventVO.getIdBando());
				LOG.info(prf + " AFTER: genericDAO.findSingleWhere(bandoLineaInterventVO) \n");	
				
				LOG.info(prf + " BEFORE: genericDAO.findSingleWhere(bandoCausaleErogazVO) \n");	
				try{
					bandoCausaleErogazVO = genericDAO.findSingleWhere(bandoCausaleErogazVO);				
					perc_raggiung_spesa=bandoCausaleErogazVO.getPercSogliaSpesaQuietanzata();					
				}catch (Exception e) {
					LOG.warn(prf + " Attenzione!!! non trovata configurazione PbandiRBandoCausaleErogaz per causale "+pbandiDCausaleErogazioneVO.getIdCausaleErogazione()+" , bando "+progBandoLinea);
				}
				LOG.info(prf + " AFTER: genericDAO.findSingleWhere(bandoCausaleErogazVO) perc_raggiung_spesa = " + perc_raggiung_spesa + "\n");	
				
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PERCENTUALE_RAGG_SPESA, NumberUtil.toDouble(perc_raggiung_spesa));
				
				LOG.info(prf + " BEFORE: DescBreveCausale = " + pbandiDCausaleErogazioneVO.getDescBreveCausale() );	
				/** ALLEGATI AL PROGETTO */
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ALLEGATI, tipoAllegatiManager.findTipoAllegatiErogazione(progBandoLinea, pbandiDCausaleErogazioneVO
						.getDescBreveCausale(), idProgetto));	
				LOG.info(prf + " idCausale = " + pbandiDCausaleErogazioneVO.getIdCausaleErogazione());	

				// 11/12/15 added footer 
				popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,""+richiestaErogazioneVO.getIdRichiestaErogazione().longValue());
				
				LOG.info(prf + " BEFORE: popolaTemplateManager.popolaModello \n");			
				Modello modello = popolaTemplateManager.popolaModello(idProgetto);	
				LOG.info(prf + " AFTER: popolaTemplateManager.popolaModello \n");			
				
				LOG.info(prf + " BEFORE: jasperFillManager.fillReport \n");			
				long startFillReport=System.currentTimeMillis();
				JasperPrint jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(), modello.getMasterParameters(),new JREmptyDataSource());
				LOG.info(prf + " ########################\nJasperFillManager.fillReport eseguito in "+(System.currentTimeMillis()-startFillReport)+" ms\n");
				
				LOG.info(prf + " BEFORE: JasperExportManager.exportReportToPdf \n");	
				long startExport=System.currentTimeMillis();
				byte[] bytesPdf = JasperExportManager.exportReportToPdf(jasperPrint);
				LOG.info(prf + " ########################\nJasperPrint esportato to pdf in "+(System.currentTimeMillis()-startExport)+" ms\n");
				
				
				
				// PK TODO contributo_definitivo e' lo stesso di quello della rimodulazione del CE ????
				// Se non lo è devo chiamarlo in un altro modo
				if(modello.getMasterParameters()!=null) {
					logger.info("modello.getMasterParameters[contributo_definitivo]=" + modello.getMasterParameters().get("contributo_definitivo"));
				}
				
				/*
				//PK TODO eliminare
				// stapo il pdf e lancio eccezzione per non sputtanare il caso prova
				Path path = Paths.get("C:\\tmp\\RichiestaErogazione.pdf");
				Files.write(path, bytesPdf);
				
				if(true) {
					throw new RichiestaErogazioneException(
						"ERRORE GRAVE FITTIZIO PK : report PROPOSTA RIMODULAZIONE salvato in locale");
				}
				// PK 
			*/
				
				
				
				
				
				
				
				LOG.info(prf + " BEFORE: creaNomefile, IdRichiestaErogazione = " + richiestaErogazioneVO.getIdRichiestaErogazione());	
				esito.setPdfBytes(bytesPdf);
				String nomeFile= creaNomefile(richiestaErogazioneVO.getIdRichiestaErogazione(), currentDate);						
				esito.setNomeReport(nomeFile);
				LOG.info(prf + " AFTER: creaNomefile, nomeFile = " + nomeFile);
		
				// INSERT SUL NUOVO FILESYSTEM
//				nodoCreato = indexDAO.creaContentRichiestaErogazione( richiestaErogazioneVO, pbandiTProgettoVO, cfBeneficiario, idSoggetto, idUtente, esito
//								.getNomeReport(), bytesPdf);
				
				nodoCreato = new Node();				
						
				// PASSO1:///////////////////// INSERT SU PBANDI_T_DOCUMENTO_INDEX /////////////////////
				LOG.info(" ***************************** PASSO 1: INSERT DATI SU PBANDI_T_DOCUMENTO_INDEX *********************************");
				String shaHex = null;
				if(bytesPdf!=null)
				  shaHex = DigestUtils.shaHex(bytesPdf);
				
				LOG.info(" BEFORE:  insertDatiDBPerIndex  shahex = " + shaHex);						 
				PbandiTDocumentoIndexVO tDoc = insertDatiDBPerIndex(idUtente, nodoCreato,
							richiestaErogazioneVO.getIdRichiestaErogazione(),
							BigDecimal.valueOf(idProgetto), currentDate, esito.getNomeReport(),
								getIdTipoDocIndex(richiestaErogazioneDTO.getDescBreveCausaleErogazione()),
								rappresentanteLegaleDTO.getIdSoggetto(), idDelegato,shaHex);
				BigDecimal idDocIndex = tDoc.getIdDocumentoIndex();
				String nomeFilePerFileSystem = tDoc.getNomeDocumento();
				
				//PASSO2: ///////////////////////// Crea file su pbandi filestorage /////////////////////
				LOG.info(" ***************************** PASSO 2: UPLOAD FILE AL NUOVO FILE STORAGE *********************************");		
				List<Boolean> uploadResult = new ArrayList<>();	
				String tipoDocumento = Constants.TIPO_DOCUMENTO_INDEX_RICHIESTA_EROGAZIONE;
				LOG.info(" BEFORE:  fileApiServiceImpl.dirExists");		
				Boolean dirEsiste = fileApiServiceImpl.dirExists(tipoDocumento, true);
				LOG.info(" AFTER:  fileApiServiceImpl.dirExists");		
				if(dirEsiste) {
					LOG.info(prf + "dirEsiste: "+ dirEsiste);
					InputStream inputStream = new ByteArrayInputStream(bytesPdf);
					Boolean isUploaded = fileApiServiceImpl.uploadFile(inputStream, nomeFilePerFileSystem, tipoDocumento);
					uploadResult.add(isUploaded);
				} else {
					//Se la cartella non viene trovata, deve prima inserirla sotto \PBAN.
					FileApiServiceImpl fileApi = new FileApiServiceImpl(Constants.DIR_PBAN);
					InputStream inputStream = new ByteArrayInputStream(bytesPdf);
					Boolean isUploaded = fileApi.uploadFile(inputStream, nomeFilePerFileSystem, tipoDocumento);
					uploadResult.add(isUploaded);
				}
								
				esito.setIdDocIndex(idDocIndex.longValue());				
				LOG.info(prf + " check progBandoLinea "+progBandoLinea);								
				boolean isBrDemat= regolaManager.isRegolaApplicabileForBandoLinea(progBandoLinea, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
				if(isBrDemat)
					associateAllegati(richiestaErogazioneVO.getIdRichiestaErogazione(), BigDecimal .valueOf(idProgetto));

				LOG.info(prf + " ############################ NEOFLUX RICH-EROG-CALC-CAU ##############################\n");
				//se la causale della richiesta erogazione effettuata è il saldo endAttivita, else unlock
				LOG.info(prf + " pbandiDCausaleErogazioneVO.getDescBreveCausale() : "+pbandiDCausaleErogazioneVO.getDescBreveCausale());
							
				if(pbandiDCausaleErogazioneVO.getDescBreveCausale().equalsIgnoreCase(Constants.COD_CAUSALE_EROGAZIONE_SALDO)) {
					neofluxBusiness.endAttivita(idUtente, idIride, idProgetto,Task.RICH_EROG_CALC_CAU);
				}
				else {
					neofluxBusiness.unlockAttivita(idUtente, idIride, idProgetto,Task.RICH_EROG_CALC_CAU);
				}							 

				String descrBreveTemplateNotifica=getDescrTemplateNotifica(pbandiDCausaleErogazioneVO.getDescBreveCausale());
				
				LOG.info(prf + " calling genericDAO.callProcedure().sendNotificationMessage....");
				genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.ISTRUTTORE,idUtente);

				LOG.info("############################ NEOFLUX ##############################\n\n\n\n");
				
				esito.setEsito(true);
				esito.setMessages(new String[] { "L\u2019operazione di richiesta erogazione \u00E8 stata completata correttamente. Scaricare la richiesta per poterla stampare nuovamente." });
				esito.setPdfBytes(null);	// Ad angular non serve ricevere il contenuto del file, basta l'idDocIndex.

					
			} catch (Exception e) {
				RichiestaErogazioneException ex = new RichiestaErogazioneException(
						ErrorMessages.ERRORE_IMPOSSIBILE_CREARE_PDF + " " + e.getMessage(), e);
				throw ex;
			}
			
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(prf + " " + e.getMessage());
			throw e;
		}
	}

	private String getDescrTemplateNotifica(String descBreveCausale) {
		String prf = "[ErogazioneDAOImpl::getDescrTemplateNotifica]";
		LOG.info(prf + " START");
		if(descBreveCausale.equalsIgnoreCase(Constants.COD_CAUSALE_EROGAZIONE_SALDO)){
			LOG.info(prf + " END");
			return Notification.NOTIFICA_RICHIESTA_EROGAZIONE_SALDO;
		}else if(descBreveCausale.equalsIgnoreCase(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)){
			LOG.info(prf + " END");
			return  Notification.NOTIFICA_RICHIESTA_EROGAZIONE_PRIMO_ANTICIPO;
		}else if(descBreveCausale.equalsIgnoreCase(Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)){
			LOG.info(prf + " END");
			return  Notification.NOTIFICA_RICHIESTA_EROGAZIONE_ACCONTO;
		}else if(descBreveCausale.equalsIgnoreCase(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)){
			LOG.info(prf + " END");
			return  Notification.NOTIFICA_RICHIESTA_EROGAZIONE_ULTERIORE_ACCONTO;
		} 
		LOG.info(prf + " END");
		return null;
	}

	private void associateAllegati(BigDecimal idRichiestaErogazione, BigDecimal idProgetto) {
		String prf = "[ErogazioneDAOImpl::associateAllegati]";
		LOG.info(prf + " START");
		// where idPRogetto is null && id_entita=t_rich and id_target= id_progetto
		LOG.info("associating allegati to idRichiestaErogazione "+idRichiestaErogazione+" ,idProgetto ");
		archivioFileDAOImpl.associateAllegatiToRichiestaErogazione(idRichiestaErogazione, idProgetto);
		LOG.info(prf + " END");
	}

	private PbandiTDocumentoIndexVO insertDatiDBPerIndex(Long idUtente, Node nodoCreato, BigDecimal idRichiestaErogazione,
			BigDecimal idProgetto, Date currentDate, String nomeReport, BigDecimal idTipoDocIndex, Long idRappLegale,
			Long idDelegato, String shaHex) throws Exception {
		String prf = "[ErogazioneDAOImpl::insertDatiDBPerIndex]";
		LOG.info(prf + " START");
		
		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
		documentoIndexVO.setDtInserimentoIndex(DateUtil.utilToSqlDate(currentDate));

		PbandiCEntitaVO entitaVO = new PbandiCEntitaVO();
		String tableName = new PbandiTRichiestaErogazioneVO().getTableNameForValueObject();
		entitaVO.setNomeEntita(tableName);
		PbandiCEntitaVO risbandiCEntitaVO[] = getGenericDAO().findWhere( entitaVO);

		if (risbandiCEntitaVO == null)
			throw new RuntimeException("Non trovato id per codice " + tableName);

		logger.info(" decodificaEntita.getId() " + risbandiCEntitaVO[0].getIdEntita());
		documentoIndexVO.setIdEntita(risbandiCEntitaVO[0].getIdEntita());
		documentoIndexVO.setIdTarget(idRichiestaErogazione);
		documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocIndex);
		documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
		documentoIndexVO.setRepository(Constants.TIPO_DOCUMENTO_INDEX_RICHIESTA_EROGAZIONE);

		/*
		 * FIX PBandi-561
		 */
		documentoIndexVO.setNomeFile(nomeReport);

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
		
		//documentoIndexVO.setUuidNodo(nodoCreato.getUid());
		documentoIndexVO.setUuidNodo("UUID");
		
		documentoIndexVO = genericDAO.insert(documentoIndexVO);
		logger.debug("idDocIndex inserito sul db "
				+ documentoIndexVO.getIdDocumentoIndex());
			
		// Nome univoco con cui il file verrà salvato su File System.		
		PbandiTDocumentoIndexVO voPerUpdate = new PbandiTDocumentoIndexVO();
		voPerUpdate.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
		String newName = nomeReport;
		voPerUpdate.setNomeDocumento(newName.replaceFirst("\\.", "_"+documentoIndexVO.getIdDocumentoIndex().longValue()+"."));
		genericDAO.update(voPerUpdate);

		documentoIndexVO.setNomeDocumento(voPerUpdate.getNomeDocumento());
		return documentoIndexVO;
	}

	private String getTipoModello(String codCausaleErogazione) {
//		id_caus= 1(PA) , id_doc_ind= 20 , RA
//		id_caus= 2(SA) , id_doc_ind= 19 , RUA
//		id_caus= 3(UA) , id_doc_ind= 18 , R2A
		if (codCausaleErogazione
				.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO)
				) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_ANTICIPAZIONE;
		} else if (codCausaleErogazione
				.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO)
			) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_II_ACCONTO;
		} else if (codCausaleErogazione
				.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO)
				) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_ULTERIORE_ACCONTO;
		} else if (codCausaleErogazione
				.equals(it.csi.pbandi.pbweberog.pbandisrv.util.Constants.COD_CAUSALE_EROGAZIONE_SALDO)
				) {
			return PopolaTemplateManager.MODELLO_RICHIESTA_SALDO;
		}
		return "";
	}

	private String creaNomefile(BigDecimal idRichiestaErogazione, Date currentDate) {
		String nomeFile;
		nomeFile = "RichiestaErogazione_" + idRichiestaErogazione + "_"
				+ DateUtil.getTime(currentDate, Constants.TIME_FORMAT_PER_NOME_FILE)
				+ ".pdf";

		logger.info("nomeFile della richiesta erogazione : " + nomeFile);

		return nomeFile;
	}

	private EnteAppartenenzaDTO findEnteAppartenenza(Long idProgetto, String codiceTipoRuoloEnte) {
		String prf = "[ErogazioneDAOImpl::findEnteAppartenenza]";
		LOG.info(prf + " START");
		EnteAppartenenzaVO enteVO = pbandiDichiarazioneDiSpesaDAO.findEnteAppartenenza(idProgetto, codiceTipoRuoloEnte);
		EnteAppartenenzaDTO enteDTO = new EnteAppartenenzaDTO();
		beanUtil.valueCopy(enteVO, enteDTO);
		LOG.info(prf + " END");
		return enteDTO;

	}

	@Override
	@Transactional
	public EsitoErogazioneDTO findDettaglioErogazione(Long idUtente, String idIride, Long idErogazione,
			Long idFormaGiuridica, Long idDimensioneImpresa, Long idBandoLinea, Long idProgetto) throws FormalParameterException {
		String prf = "[ErogazioneDAOImpl::findDettaglioErogazione]";
		LOG.info(prf + " BEGIN");
		
		EsitoErogazioneDTO esito = new EsitoErogazioneDTO();
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idErogazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idErogazione);

		PbandiTErogazioneVO erogazioneVO = new PbandiTErogazioneVO();

		erogazioneVO.setIdErogazione(new BigDecimal(idErogazione));
		erogazioneVO = genericDAO.findSingleWhere(erogazioneVO);
		ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
		erogazioneDTO.setIdErogazione(NumberUtil.toLong(erogazioneVO.getIdErogazione()));
		// BISOGNA CREARE IL COD COMPLESSO
		// erogazioneDTO.setCodCausaleErogazione(val)
		BandoLineaVO bandoLineaVO = new BandoLineaVO();
		bandoLineaVO.setProgrBandoLineaIntervento(NumberUtil.toBigDecimal(idBandoLinea));
		bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);

		PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridicaECausale(
				idFormaGiuridica, idDimensioneImpresa, bandoLineaVO, NumberUtil.toLong(erogazioneVO.getIdCausaleErogazione()));
		DecodificaDTO decodificaVO = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, NumberUtil.toLong(erogazioneVO.getIdCausaleErogazione()));
		BigDecimal percErogazione = bandiRBandoCausaleErogazVO.getPercErogazione();
		BigDecimal percLimite = bandiRBandoCausaleErogazVO.getPercLimite();
		if (percErogazione == null)
			percErogazione = new BigDecimal(0);
		if (percLimite == null)
			percLimite = new BigDecimal(0);
		
		erogazioneDTO.setPercErogazione(percErogazione.doubleValue());
		erogazioneDTO.setPercLimite(percLimite.doubleValue());
		
		erogazioneDTO.setCodCausaleErogazione(decodificaVO.getDescrizioneBreve());
		erogazioneDTO.setDescrizioneCausaleErogazione(decodificaVO.getDescrizione());
		erogazioneDTO.setDescBreveCausaleErogazione(decodificaVO.getDescrizioneBreve());
		
		decodificaVO = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE, NumberUtil.toLong(erogazioneVO.getIdModalitaAgevolazione()));
		erogazioneDTO.setCodModalitaAgevolazione(decodificaVO.getDescrizioneBreve());	
		erogazioneDTO.setDescModalitaAgevolazione(decodificaVO.getDescrizione());
		erogazioneDTO.setDescBreveModalitaAgezolazione(decodificaVO.getDescrizioneBreve());
		
		decodificaVO = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.MODALITA_EROGAZIONE, NumberUtil.toLong(erogazioneVO.getIdModalitaErogazione()));
		erogazioneDTO.setCodModalitaErogazione(decodificaVO.getDescrizioneBreve());
		erogazioneDTO.setDescModalitaErogazione(decodificaVO.getDescrizione());
		erogazioneDTO.setDescBreveModalitaErogazione(decodificaVO.getDescrizioneBreve());
		
		// }L{ fix aggiornamento DB 31/03/2010
		PbandiTEnteCompetenzaVO ente = new PbandiTEnteCompetenzaVO();
		ente.setIdEnteCompetenza(erogazioneVO.getIdEnteCompetenza());
		erogazioneDTO.setCodDirezione(genericDAO.findSingleWhere(ente).getDescBreveEnte());
		erogazioneDTO.setDataContabile(erogazioneVO.getDtContabile());
		erogazioneDTO.setImportoErogazioneEffettivo(NumberUtil.toDouble(erogazioneVO.getImportoErogazione()));
		erogazioneDTO.setNumeroRiferimento(erogazioneVO.getCodRiferimentoErogazione());
		erogazioneDTO.setNoteErogazione(erogazioneVO.getNoteErogazione());
		
		
		Double importoCalcolato = contoEconomicoManager.findImportoAgevolato(idProgetto);
		if (percErogazione.doubleValue() > 0) {
			// CASO A	
			LOG.info(prf + "CASO A");
			erogazioneDTO.setPercentualeErogazioneFinanziaria(percLimite.doubleValue());	
			
			Double importoErogazioneCalcolato = NumberUtil.toRoundDouble(importoCalcolato * percErogazione.doubleValue() / 100);			
			erogazioneDTO.setImportoErogazioneFinanziario(importoErogazioneCalcolato);
			
			erogazioneDTO.setPercentualeErogazioneEffettiva(percErogazione.doubleValue());
			
		}  else if (percErogazione.doubleValue() == 0 && percLimite.doubleValue() > 0) {
			// CASO B
			LOG.info(prf + "CASO B");
			erogazioneDTO.setPercentualeErogazioneFinanziaria(percLimite.doubleValue());						
			Double importoErogazioneDaIterFinanziario = NumberUtil.toRoundDouble((importoCalcolato * percLimite.doubleValue() / 100));			
			erogazioneDTO.setImportoErogazioneFinanziario(importoErogazioneDaIterFinanziario)	;
//			if (importoErogazioneDaIterFinanziario > erogazioneVO.getImportoCalcolato()) {
//				erogazioneDTO.setPercentualeErogazioneEffettiva( NumberUtil.toRoundDouble(importoResiduoSpettante / importoCalcolato * 100));
//				erogazioneDTO.setImportoErogazioneEffettiva(importoResiduoSpettante);
//			} else {
//				erogazioneDTO.setPercentualeErogazioneEffettiva(percLimite.doubleValue());
//				erogazioneDTO.setImportoErogazioneEffettivo(importoErogazioneDaIterFinanziario);
//			}
		} else {
			// CASO C
			LOG.info(prf + "CASO C");
			erogazioneDTO.setPercentualeErogazioneFinanziaria(0d);
			erogazioneDTO.setImportoErogazioneFinanziario(0d);
			erogazioneDTO.setPercentualeErogazioneEffettiva(0d);
			erogazioneDTO.setImportoErogazioneEffettivo(0d);
		}

		erogazioneDTO.setImportoErogazioneEffettivo(NumberUtil.toDouble(erogazioneVO.getImportoErogazione()));
		esito.setErogazione(erogazioneDTO);

		LOG.info(prf + " END");
		return esito;

	}

	private PbandiRBandoCausaleErogazVO getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridicaECausale(
			Long idFormaGiuridica, Long idDimensioneImpresa, BandoLineaVO bandoLineaVO, Long idCausaleErogazione) {
		PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = null;

		List<PbandiRBandoCausaleErogazVO> l = new ArrayList<PbandiRBandoCausaleErogazVO>();
		PbandiDCausaleErogazioneVO pBandiDCausaliErogazioneVO = new PbandiDCausaleErogazioneVO();
		pBandiDCausaliErogazioneVO.setIdCausaleErogazione(NumberUtil.toBigDecimal(idCausaleErogazione));
		pBandiDCausaliErogazioneVO = genericDAO.findSingleWhere(pBandiDCausaliErogazioneVO);
		if ((pBandiDCausaliErogazioneVO.getDescBreveCausale().equals(Constants.COD_CAUSALE_EROGAZIONE_SALDO_NO_STANDARD))
				|| (pBandiDCausaliErogazioneVO.getDescBreveCausale().equals(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO_NO_STANDARD))
				|| (pBandiDCausaliErogazioneVO.getDescBreveCausale().equals(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO_NO_STANDARD))) {

			bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil.toBigDecimal(idCausaleErogazione));
			bandiRBandoCausaleErogazVO.setPercLimite(null);
			bandiRBandoCausaleErogazVO.setPercErogazione(null);
			l.add(bandiRBandoCausaleErogazVO);
		} else {
			if (idFormaGiuridica != null) {
				bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
				bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
				bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil.toBigDecimal(idCausaleErogazione));
				bandiRBandoCausaleErogazVO.setIdFormaGiuridica(NumberUtil.toBigDecimal(idFormaGiuridica));
				l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);

				if (l == null || l.isEmpty()) {
					bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
					bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
					bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil.toBigDecimal(idCausaleErogazione));
					bandiRBandoCausaleErogazVO.setIdDimensioneImpresa(NumberUtil.toBigDecimal(idDimensioneImpresa));
					l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
					if (l == null || l.isEmpty()) {
						bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
						bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
						bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil.toBigDecimal(idCausaleErogazione));
						l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);

					}
				}
			} else {
				bandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
				bandiRBandoCausaleErogazVO.setIdBando(bandoLineaVO.getIdBando());
				bandiRBandoCausaleErogazVO.setIdCausaleErogazione(NumberUtil.toBigDecimal(idCausaleErogazione));
				l = genericDAO.findListWhere(bandiRBandoCausaleErogazVO);
			}
		}
		
		//Fabio Cirillo - 15/11/2021:  Aggiunto controllo if/else per il caso in cui l.size() <= 0 ed evitare che venga sollevata un'eccezione
		if(l != null && l.size() > 0) {
			return l.get(0);
		}else {
			l = new ArrayList<PbandiRBandoCausaleErogazVO>();
			PbandiRBandoCausaleErogazVO pbandiRBandoCausaleErogazVO = new PbandiRBandoCausaleErogazVO();
			pbandiRBandoCausaleErogazVO.setPercErogazione(null);
			pbandiRBandoCausaleErogazVO.setPercLimite(null);
			
			l.add(pbandiRBandoCausaleErogazVO);
			
			return  l.get(0);
		}
		
	}
	
	@Override
	// Trova i file associati all'erogazione prima che questa sia stata creata su db.
	// Dal momento che l'erogazione non esiste ancora, al posto dell'id erogazione si usa l'id progetto.
	public FileDTO[] getFilesAssociatedRichiestaErogazioneByIdProgetto(Long idProgetto) throws Exception {	
		String prf = "[ErogazioneDAOImpl::associaAllegatiARichiestaErogazione] ";
		LOG.info(prf + "BEGIN");
		List<ArchivioFileVO> list = archivioFileDAOImpl.getFilesAssociatedByIdProgetto( 
				BigDecimal.valueOf(idProgetto),"PBANDI_T_RICHIESTA_EROGAZIONE");
		List <FileDTO> filez = new ArrayList<FileDTO>();
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		LOG.info(prf+" END");
		return filez.toArray(new FileDTO[0]);
	}

	@Override
	public FileDTO[] getFilesAssociatedRichiestaErogazioneByIdErogazione(Long idProgetto) throws Exception {
		String prf = "[ErogazioneDAOImpl::getFilesAssociatedRichiestaErogazioneByIdErogazione] ";
		FileDTO[] result = getFilesAssociatedByIdErogazione(BigDecimal.valueOf(idProgetto),"PBANDI_T_RICHIESTA_EROGAZIONE").toArray(new FileDTO[0]);
		LOG.info(prf+" END");
		return result;
	}

	public List<FileDTO> getFilesAssociatedByIdErogazione(
			BigDecimal idErogazione, String nomeEntita) {
		String sqlSelect =
				"SELECT" +
						" ID_DOCUMENTO_INDEX," +
						" ID_FOLDER," +
						" SIZE_FILE," +
						" PBANDI_T_FILE.ID_UTENTE_INS," +
						" DT_INSERIMENTO," +
						" PBANDI_T_FILE.ID_UTENTE_AGG," +
						" DT_AGGIORNAMENTO," +
						" PBANDI_T_FILE.NOME_FILE," +
						" DOCIND.ID_ENTITA," +
						" DOCIND.ID_TARGET," +
						" ID_TIPO_DOCUMENTO_INDEX" +
						" FROM" +
						" PBANDI_T_FILE" +
						" JOIN" +
						" PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)" +
						" JOIN" +
						" PBANDI_T_FILE_ENTITA using (ID_FILE)" +
						" where" +
						" PBANDI_T_FILE_ENTITA.ID_ENTITA =  (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA= ?)" +
						" AND PBANDI_T_FILE_ENTITA.ID_TARGET = ?" +
						" order by ID_DOCUMENTO_INDEX";

		Object[] params = new Object[]{nomeEntita, idErogazione};
		return getJdbcTemplate().query(sqlSelect, params, new BeanPropertyRowMapper<>(FileDTO.class));
	}
	@Override
	// Da chiamare quando si esce da Archivio File.
	// Se l'erogazione non esiste ancora, idTarget deve essere null: al suo posto verrà usato l'id progetto.
	public EsitoAssociaFilesDTO associaAllegatiARichiestaErogazione(AssociaFilesRequest associaFilesRequest, Long idUtente, String idIride) throws Exception {
		String prf = "[ErogazioneDAOImpl::associaAllegatiARichiestaErogazione] ";
		LOG.info(prf + "BEGIN");
			
		EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
		try {
			
			String[] nameParameter = { "associaFilesRequest", "elencoIdDocumentoIndex", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, associaFilesRequest, associaFilesRequest.getElencoIdDocumentoIndex(), associaFilesRequest.getIdProgetto());
			
			Long idProgetto = associaFilesRequest.getIdProgetto();			
			
			Long idTarget = associaFilesRequest.getIdTarget();
			if (idTarget == null)
				idTarget = idProgetto; 
			
			String sql = "SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_RICHIESTA_EROGAZIONE'";
			LOG.info("\n"+sql);
			Long idEntita = (Long) getJdbcTemplate().queryForObject(sql, Long.class);	
			
			for (Long idFile : associaFilesRequest.getElencoIdDocumentoIndex()) {
				try {					
					if (!archivioBusinessImpl.isFileAssociated(idUtente, idIride, idFile, idTarget, idEntita)) {			
						Esito esitoSrv = archivioBusinessImpl.associateFile(idUtente, idIride, idFile, idTarget, idEntita, idProgetto);
						if (esitoSrv != null && esitoSrv.getEsito()) {
							esito.getElencoIdDocIndexFilesAssociati().add(idFile);
						} else {
							esito.getElencoIdDocIndexFilesNonAssociati().add(idFile);
						}
					} else {
						logger.warn("Impossibile associare il file["+idFile+"] alla entita["+idEntita+"], target["+idTarget+"] poiche' il file e' gia' associato.");
						esito.getElencoIdDocIndexFilesNonAssociati().add(idFile);
					}
				} catch (Exception e) {
					logger.error("Errore durante l'associazione del file["+idFile+"] alla entita["+idEntita+"], target["+idTarget+"].", e);
					esito.getElencoIdDocIndexFilesNonAssociati().add(idFile);
				}
			}
		
		} catch (InvalidParameterException e) {
			LOG.error(prf+e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nel associare allegati alla richiesta erogazione: ", e);
			throw new Exception(" ERRORE nel associare allegati alla richiesta erogazione.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Se l'erogazione non esiste ancora, idErogazione deve essere null: al suo posto verrà usato l'id progetto.
	public EsitoOperazioni disassociaAllegatiARichiestaErogazione(Long idDocumentoIndex, Long idErogazione, Long idProgetto, Long idUtente, String idIride) throws Exception {
		String prf = "[ErogazioneDAOImpl::disassociaAllegatiARichiestaErogazione] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idErogazione = " + idErogazione + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
			
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			
			String[] nameParameter = { "idDocumentoIndex", "idProgetto", "idUtente" };
			ValidatorInput.verifyNullValue(nameParameter, idDocumentoIndex, idProgetto, idUtente);
			
			String sql = "SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_RICHIESTA_EROGAZIONE'";
			LOG.info("\n"+sql);
			Long idEntita = (Long) getJdbcTemplate().queryForObject(sql, Long.class);
			
			if (idErogazione == null)
				idErogazione = idProgetto;
			
			Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex, idEntita, idErogazione, idProgetto);
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMsg(esitoPbandisrv.getMessage());
		
		} catch (InvalidParameterException e) {
			LOG.error(prf+e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nel associare allegati alla richiesta erogazione: ", e);
			throw new Exception(" ERRORE nel associare allegati alla richiesta erogazione.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	public InizializzaErogazioneDTO inizializzaErogazione(Long idProgetto) throws Exception {
		String prf = "[ErogazioneDAOImpl::inizializzaErogazione] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto);
			
		InizializzaErogazioneDTO result = new InizializzaErogazioneDTO();
		try {
			
			String[] nameParameter = { "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);			
			
			// Codice visualizzato del progetto.
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = "+idProgetto;
			LOG.info("\n"+sql);
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, String.class);
			result.setCodiceVisualizzatoProgetto(codiceProgetto);
			
			// Codice visualizzato del progetto.
			String attributo = "conf.digitalSignFileSizeLimit";
			String sql2 = "SELECT VALORE FROM PBANDI_C_COSTANTI WHERE ATTRIBUTO = '"+attributo+"'";
			LOG.info("\n"+sql2);
			String valore = (String) getJdbcTemplate().queryForObject(sql2, String.class);			
			if (StringUtil.isEmpty(valore)) {
				throw new Exception("Costante "+attributo+" non trovata.");
			}
			result.setDimMaxFileFirmato(new Long(valore));
		
		} catch (InvalidParameterException e) {
			LOG.error(prf+e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella inizializzazione: ", e);
			throw new Exception(" ERRORE nella inizializzazione.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}

	@Override
	public Object getModalitaAgevolazioneDaVisualizzare(Long idModalitaAgev) {
		
		String prf = "[ErogazioneDAOImpl::inizializzaErogazione] ";
		LOG.info(prf + "BEGIN");
			
		String descModalitaAgevolazione= null;
		try {
		
			String sql = " SELECT \r\n"
					+ "  	 pdma.ID_MODALITA_AGEVOLAZIONE_RIF  \r\n"
					+ "  FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma\r\n"
					+ "  WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";

			Object[] params = new Object[]{idModalitaAgev};
			int idModalitaRif = getJdbcTemplate().queryForObject(sql, params, Integer.class);
		
			switch (idModalitaRif) {
			case 1:
				descModalitaAgevolazione="contributo";
				break;
			case 5:
				descModalitaAgevolazione="finanziamento";
				break;
			case 10:
				descModalitaAgevolazione="garanzia";
				break;

			default:
				break;
			}
			
		} catch (InvalidParameterException e) {
			LOG.error(prf+e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf+" ERRORE lettura tabella pbandi_d_modalita_agevolazione : ", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return descModalitaAgevolazione;
	}

	@Override
	public PbandiTRichiestaErogazioneVO findRichiestaErogazione(Long idErogazione) throws RuntimeException {
		String prf = "[ErogazioneDAOImpl::findRichiestaErogazione] ";
		LOG.info(prf + "BEGIN");
		PbandiTRichiestaErogazioneVO vo = new PbandiTRichiestaErogazioneVO();
		vo.setIdRichiestaErogazione(BigDecimal.valueOf(idErogazione));
		PbandiTRichiestaErogazioneVO result = genericDAO.findSingleOrNoneWhere(vo);

		if (result == null) {
			LOG.error(prf + " ERRORE lettura tabella pbandi_t_richiesta_erogazione");
			throw new RuntimeException("Errore in findRichiestaErogazione");
		}
		LOG.info(prf + " END");
		return result;
	}

	@Override
	public PbandiDCausaleErogazioneVO findCausaleErogazione(Long idCausaleErogazione) {
		String prf = "[ErogazioneDAOImpl::findCausaleErogazione] ";
		LOG.info(prf + "BEGIN");
		PbandiDCausaleErogazioneVO vo = new PbandiDCausaleErogazioneVO();
		vo.setIdCausaleErogazione(BigDecimal.valueOf(idCausaleErogazione));
		PbandiDCausaleErogazioneVO result = genericDAO.findSingleOrNoneWhere(vo);

		if (result == null) {
			LOG.error(prf + " ERRORE lettura tabella pbandi_d_causale_erogazione");
			throw new RuntimeException("Errore in findCausaleErogazione");
		}
		if(result.getIdTipoDocumentoIndex() == null){
			LOG.info(prf + "idTipoDocumentoIndex null!!!");
		}
		LOG.info(prf + " END");
		return result;
	}

	@Override
	public List<PbandiTDocumentoIndexVO> findAllegatiRichiestaErogazione(Long idProgetto, Long idErogazione) throws RuntimeException {
		String prf = "[ErogazioneDAOImpl::findAllegatiRichiestaErogazione] ";
		LOG.info(prf + "BEGIN");
		String sql = "SELECT * FROM PBANDI_T_DOCUMENTO_INDEX \n" +
				"WHERE ID_ENTITA = 182 \n" +
				"AND MESSAGE_DIGEST IS NOT NULL\n" +
				//"AND ID_STATO_DOCUMENTO = 1 \n" +
				//"AND FLAG_FIRMA_CARTACEA IS NOT NULL\n" +
				"AND ID_SOGG_RAPPR_LEGALE IS NOT NULL\n" +
				"AND ID_PROGETTO = ? \n" +
				"AND ID_TARGET = ? \n";

		Object[] params = new Object[]{idProgetto, idErogazione};

		List<PbandiTDocumentoIndexVO> result = getJdbcTemplate().query(sql, params, new BeanPropertyRowMapper<>(PbandiTDocumentoIndexVO.class));
		if(result.isEmpty()){
			LOG.error(prf + "nessun documento trovato nella tabella pbandi_t_documento_index");
		}
		LOG.info(prf + " END");

		return result;
	}

	@Override
	public List<PbandiTAffidServtecArVO> findAffidamentiRichiestaErogazione(Long idErogazione) {
		String prf = "[ErogazioneDAOImpl::findAffidamentiRichiestaErogazione] ";
		LOG.info(prf + "BEGIN");
		PbandiTAffidServtecArVO vo = new PbandiTAffidServtecArVO();
		vo.setIdRichiestaErogazione(idErogazione);
		List<PbandiTAffidServtecArVO> result = genericDAO.findListWhere(vo);
		LOG.info(prf + " END");
		return result;
	}

	@Override
	public List<FideiussioneTipoTrattamentoDTO> findFideiussioniRichiestaErogazione(Long idProgetto) {
		String prf = "[ErogazioneDAOImpl::findFideiussioniRichiestaErogazione] ";
		LOG.info(prf + "BEGIN");
		List<PbandiTFideiussioneVO> fideiussioni = findFideiussioniPerProgetto(idProgetto);
		PbandiDTipoTrattamentoVO[] tipiTrattamento = genericDAO.findAll(PbandiDTipoTrattamentoVO.class);
		List<FideiussioneTipoTrattamentoDTO> result = new ArrayList<>(4);

		for (PbandiDTipoTrattamentoVO tipoTrattamento : tipiTrattamento) {
			FideiussioneTipoTrattamentoDTO temp = new FideiussioneTipoTrattamentoDTO();
			temp.setDescrizioneTipoTrattamento(tipoTrattamento.getDescTipoTrattamento());
			temp.setDescBreveTipoTrattamento(tipoTrattamento.getDescBreveTipoTrattamento());
			temp.setIdTipoTrattamento(tipoTrattamento.getIdTipoTrattamento().doubleValue());
			result.add(temp);
		}
		for(FideiussioneTipoTrattamentoDTO tipoFideiussione : result){
			BigDecimal importo = fideiussioni.stream()
					.filter((tipo) -> tipo.getIdTipoTrattamento().compareTo(BigDecimal.valueOf(tipoFideiussione.getIdTipoTrattamento())) == 0)
					.map((tipo) -> tipo.getImportoFideiussione() != null ? tipo.getImportoFideiussione() : BigDecimal.ZERO)
					.reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
			tipoFideiussione.setImporto(importo.doubleValue());
		}

		LOG.info(prf + " END");
		return result;
	}

	@Override
	public List<PbandiTFideiussioneVO> findFideiussioniPerProgetto(Long idProgetto) {
		String prf = "[ErogazioneDAOImpl::findFideiussioniPerProgetto] ";
		LOG.info(prf + "BEGIN");
		PbandiTFideiussioneVO vo = new PbandiTFideiussioneVO();
		vo.setIdProgetto(BigDecimal.valueOf(idProgetto));
		List<PbandiTFideiussioneVO> result = genericDAO.findListWhere(vo);
		LOG.info(prf + " END");
		return result;
	}

	@Override
	public EsitoOperazioni verificaErogazione(Long idErogazione, Long idUtente, boolean verificato) {
		EsitoOperazioni esitoOperazioni = new EsitoOperazioni();
		PbandiTRichiestaErogazioneVO vo = new PbandiTRichiestaErogazioneVO();
		vo.setIdRichiestaErogazione(BigDecimal.valueOf(idErogazione));
		vo = genericDAO.findSingleOrNoneWhere(vo);

		if(vo == null){
			esitoOperazioni.setEsito(false);
			esitoOperazioni.setMsg("Nessuna erogazione trovata con id " + idErogazione);
			return esitoOperazioni;
		}

		if(vo.getFlagBollinoVerificaErogaz() != null){
			esitoOperazioni.setEsito(false);
			esitoOperazioni.setMsg("L'erogazione con id " + idErogazione + " è già stata verificata");
			return esitoOperazioni;
		}

		vo.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
		if (verificato) {
			vo.setFlagBollinoVerificaErogaz("P");
		} else {
			vo.setFlagBollinoVerificaErogaz("N");
		}

		try{
			genericDAO.update(vo);
			esitoOperazioni.setEsito(true);
		}
		catch (Exception e){
			esitoOperazioni.setEsito(false);
			esitoOperazioni.setMsg("Errore durante la verifica dell'erogazione con id " + idErogazione);
		}
		return esitoOperazioni;
	}

	@Override
	public it.csi.pbandi.pbservizit.pbandisrv.dto.manager.EstremiBancariDTO findEstremiBancariVerificaErogazione(Long idProgetto) {
		String prf = "[ErogazioneDAOImpl::findEstremiBancariVerificaErogazione] ";
		LOG.info(prf + "BEGIN");
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.EstremiBancariDTO result = null;
		String sql = "SELECT DISTINCT ban.*\n" +
				"FROM\n" +
				"PBANDI.pbandi_t_domanda dom,\n" +
				"PBANDI.pbandi_t_progetto pro,\n" +
				"PBANDI.pbandi_r_bando_linea_intervent rel,\n" +
				"PBANDI.pbandi_r_soggetto_progetto sogg,\n" +
				"PBANDI.pbandi_t_estremi_bancari ban\n" +
				"WHERE\n" +
				"pro.ID_DOMANDA = dom.ID_DOMANDA\n" +
				"AND dom.PROGR_BANDO_LINEA_INTERVENTO = rel.PROGR_BANDO_LINEA_INTERVENTO\n" +
				"AND pro.ID_PROGETTO = sogg.ID_PROGETTO\n" +
				"AND sogg.ID_ESTREMI_BANCARI = ban.ID_ESTREMI_BANCARI\n" +
				"AND sogg.ID_TIPO_ANAGRAFICA = 1\n" +
				"AND sogg.ID_TIPO_BENEFICIARIO <> 4\n" +
				"AND pro.ID_PROGETTO = ? \n";
		Object[] params = new Object[]{idProgetto};
		try{
			result = getJdbcTemplate().queryForObject(sql, params, new BeanPropertyRowMapper<>(it.csi.pbandi.pbservizit.pbandisrv.dto.manager.EstremiBancariDTO.class));
		}
		catch (Exception e){
			LOG.error(prf + "Errore durante la ricerca degli estremi bancari per il progetto con id " + idProgetto);
		}
		LOG.info(prf + "END");
		return result;
	}

}
