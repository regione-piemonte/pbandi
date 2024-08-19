/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoCampionamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoElencoProgettiCampione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.FiltroRilevazioniDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.RilevazioneProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CampionamentoProgettiEsistentiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoCampionatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli.AsseRilevazioniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli.BandoRilevazioniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli.NormativaRilevazioniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.LikeContainsCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDCategAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoLineaInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRCampionamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCampionamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPeriodoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiVRilevazioniVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.ElenchiProgettiCampionamento;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.FiltroRilevazione;
import it.csi.pbandi.pbweberog.integration.dao.MonitoraggioControlliDAO;
import it.csi.pbandi.pbweberog.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbweberog.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbweberog.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Component
public class MonitoraggioControlliDAOImpl extends JdbcDaoSupport implements MonitoraggioControlliDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	ReportManager reportManager;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public MonitoraggioControlliDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
	}


	@Override
	@Transactional
	public CodiceDescrizioneDTO[] findNormative(Long idUtente, String idIride, Boolean isConsultazione,
			String flagCampionRilev) {
		String prf = "[MonitoraggioControlliDAOImpl::findNormative]";
		LOG.debug(prf + " BEGIN");
		try {
			if(isConsultazione){			
				PbandiDTipoLineaInterventoVO tipoLineaIntervento = new PbandiDTipoLineaInterventoVO();
				tipoLineaIntervento.setDescTipoLinea(Constants.DESC_TIPO_LINEA_NORMATIVA);
				tipoLineaIntervento = (PbandiDTipoLineaInterventoVO) genericDAO.findSingleOrNoneWhere(tipoLineaIntervento);

				NormativaRilevazioniVO linea = new NormativaRilevazioniVO();
				linea.setIdTipoLineaIntervento(tipoLineaIntervento.getIdTipoLineaIntervento());
				FilterCondition<NormativaRilevazioniVO> filterCondition = new FilterCondition<NormativaRilevazioniVO>(linea);
				
				NormativaRilevazioniVO likeConditionVO = new NormativaRilevazioniVO();
				likeConditionVO.setFlagCampionRilev(flagCampionRilev);
				LikeContainsCondition<NormativaRilevazioniVO> likeCondition = new LikeContainsCondition<NormativaRilevazioniVO>(likeConditionVO);		

				AndCondition<NormativaRilevazioniVO> andCondition = new AndCondition<NormativaRilevazioniVO>(filterCondition, likeCondition);
				ArrayList<NormativaRilevazioniVO> listResult = (ArrayList<NormativaRilevazioniVO>) genericDAO.findListWhere(andCondition);				

				HashMap<String, String> trsMap = new HashMap<String, String>();
				trsMap.put("normativa", "descrizione");
				trsMap.put("idNormativa", "codice");
				LOG.debug(prf + " END");
				return 	beanUtil.transform(listResult, CodiceDescrizioneDTO.class, trsMap);

			} else {

				PbandiDTipoLineaInterventoVO tipoLineaIntervento = new PbandiDTipoLineaInterventoVO();
				tipoLineaIntervento.setDescTipoLinea(Constants.DESC_TIPO_LINEA_NORMATIVA);
				tipoLineaIntervento = (PbandiDTipoLineaInterventoVO) genericDAO.findSingleOrNoneWhere(tipoLineaIntervento);

				PbandiDLineaDiInterventoVO linea = new PbandiDLineaDiInterventoVO();
				linea.setIdTipoLineaIntervento(tipoLineaIntervento.getIdTipoLineaIntervento());
				FilterCondition<PbandiDLineaDiInterventoVO> filterCondition = new FilterCondition<PbandiDLineaDiInterventoVO>(linea);

				PbandiDLineaDiInterventoVO likeConditionVO = new PbandiDLineaDiInterventoVO();
				likeConditionVO.setFlagCampionRilev(flagCampionRilev);
				LikeContainsCondition<PbandiDLineaDiInterventoVO> likeCondition = new LikeContainsCondition<PbandiDLineaDiInterventoVO>(likeConditionVO);				

				AndCondition<PbandiDLineaDiInterventoVO> andCondition = new AndCondition<PbandiDLineaDiInterventoVO>(filterCondition, likeCondition);
				ArrayList<PbandiDLineaDiInterventoVO> listResult = (ArrayList<PbandiDLineaDiInterventoVO>) genericDAO.findListWhere(andCondition);

				if(flagCampionRilev.equalsIgnoreCase(Constants.FLAG_CAMPIONIONAMENTO_CERTIFICAZIONE)){
					ArrayList<CodiceDescrizioneDTO> elencoNormative = new ArrayList<CodiceDescrizioneDTO>();
					CodiceDescrizioneDTO cd = null;
					for (PbandiDLineaDiInterventoVO normativa : listResult) {
						cd = new CodiceDescrizioneDTO();
						cd.setCodice(normativa.getIdLineaDiIntervento().toString());
						cd.setDescrizione(normativa.getDescBreveLinea());
						elencoNormative.add(cd);
					}
					LOG.debug(prf + " END");
					return elencoNormative.toArray(new CodiceDescrizioneDTO[0]);
				}else{
					HashMap<String, String> trsMap = new HashMap<String, String>();
					trsMap.put("descLinea", "descrizione");
					trsMap.put("idLineaDiIntervento", "codice");
					LOG.debug(prf + " END");
					return beanUtil.transform(listResult, CodiceDescrizioneDTO.class, trsMap);
				}

			}
		} catch(Exception e) {
			throw e;
		}
	}
	

	@Override
	@Transactional
	public CodiceDescrizioneDTO[] findAsse(Long idUtente, String idIride, Boolean isConsultazione,
			FiltroRilevazioniDTO filtroRilevazione) {
		String prf = "[MonitoraggioControlliDAOImpl::findAsse]";
		LOG.debug(prf + " BEGIN");
		try {
			if(isConsultazione){
				AsseRilevazioniVO filtroVRilevazioneVO = new AsseRilevazioniVO();
				if(filtroRilevazione != null && filtroRilevazione.getIdLineaIntervento() != null)
					filtroVRilevazioneVO.setIdNormativa(beanUtil.transform(filtroRilevazione.getIdLineaIntervento(), BigDecimal.class));
				ArrayList<AsseRilevazioniVO> elencoRilevazione = (ArrayList<AsseRilevazioniVO>) genericDAO.findListWhere(filtroVRilevazioneVO);

				HashMap<String, String> trsMap = new HashMap<String, String>();
				trsMap.put("asse", "descrizione");
				trsMap.put("idAsse", "codice");
				LOG.debug(prf + " END");
				return beanUtil.transform(elencoRilevazione, CodiceDescrizioneDTO.class, trsMap);

			}else{
				PbandiDTipoLineaInterventoVO tipoLineaIntervento = new PbandiDTipoLineaInterventoVO();
				tipoLineaIntervento.setDescTipoLinea(Constants.DESC_TIPO_LINEA_ASSE);
				tipoLineaIntervento = (PbandiDTipoLineaInterventoVO) genericDAO.findSingleOrNoneWhere(tipoLineaIntervento);

				PbandiDLineaDiInterventoVO linea = new PbandiDLineaDiInterventoVO();
				linea.setIdTipoLineaIntervento(tipoLineaIntervento.getIdTipoLineaIntervento());
				linea.setIdLineaDiInterventoPadre(beanUtil.transform(filtroRilevazione.getIdLineaIntervento(), BigDecimal.class));

				ArrayList<PbandiDLineaDiInterventoVO> listResult = (ArrayList<PbandiDLineaDiInterventoVO>) genericDAO.findListWhere(linea);

				HashMap<String, String> trsMap = new HashMap<String, String>();
				trsMap.put("descLinea", "descrizione");
				trsMap.put("idLineaDiIntervento", "codice");
				LOG.debug(prf + " END");
				return beanUtil.transform(listResult, CodiceDescrizioneDTO.class, trsMap);
			}
			
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public CodiceDescrizioneDTO[] findBandi(Long idUtente, String idIride, Boolean isConsultazione,
			FiltroRilevazioniDTO filtroRilevazione) {
		String prf = "[MonitoraggioControlliDAOImpl::findBandi]";
		LOG.debug(prf + " BEGIN");
		try {
			if(isConsultazione){
				BandoRilevazioniVO filtroVRilevazioneVO = new BandoRilevazioniVO();

				if(filtroRilevazione != null){
					if(filtroRilevazione.getIdLineaIntervento() != null)
						filtroVRilevazioneVO.setIdNormativa(beanUtil.transform(filtroRilevazione.getIdLineaIntervento(), BigDecimal.class));
					if(filtroRilevazione.getIdAsse() != null)
						filtroVRilevazioneVO.setIdAsse(beanUtil.transform(filtroRilevazione.getIdAsse(), BigDecimal.class));
				}

				ArrayList<BandoRilevazioniVO> elencoRilevazione = (ArrayList<BandoRilevazioniVO>) genericDAO.findListWhere(filtroVRilevazioneVO);

				HashMap<String, String> trsMap = new HashMap<String, String>();
				trsMap.put("nomeBandoLinea", "descrizione");
				trsMap.put("progrBandoLineaIntervento", "codice");
				LOG.debug(prf + " END");
				return beanUtil.transform(elencoRilevazione, CodiceDescrizioneDTO.class, trsMap);

			}else{

				PbandiRBandoLineaInterventVO tipoLineaIntervento = new PbandiRBandoLineaInterventVO();
				tipoLineaIntervento.setIdLineaDiIntervento(beanUtil.transform(filtroRilevazione.getIdAsse(), BigDecimal.class));

				ArrayList<PbandiRBandoLineaInterventVO> listResult = (ArrayList<PbandiRBandoLineaInterventVO>) genericDAO.findListWhere(tipoLineaIntervento);

				HashMap<String, String> trsMap = new HashMap<String, String>();
				trsMap.put("nomeBandoLinea", "descrizione");
				trsMap.put("progrBandoLineaIntervento", "codice");
				LOG.debug(prf + " END");
				return beanUtil.transform(listResult, CodiceDescrizioneDTO.class, trsMap);
			}
			
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public CodiceDescrizioneDTO[] findAnnoContabili(Long idUtente, String idIride, Boolean isConsultazione) {
		String prf = "[MonitoraggioControlliDAOImpl::findAnnoContabili]";
		LOG.debug(prf + " BEGIN");
		try {
			PbandiTPeriodoVO periodoVO = new PbandiTPeriodoVO();
			periodoVO.setIdTipoPeriodo(Constants.TIPO_PERIODO_ANNO_CONTABILE);
			List<PbandiTPeriodoVO> listPeriodo = genericDAO.findListWhere(periodoVO);	
			HashMap<String, String> transMap = new HashMap<String, String>();
			transMap.put("idPeriodo", "codice");
			transMap.put("descPeriodoVisualizzata", "descrizione");
			LOG.debug(prf + " END");
			return beanUtil.transform(listPeriodo, CodiceDescrizioneDTO.class, transMap);
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public CodiceDescrizioneDTO[] findAutoritaControlli(Long idUtente, String idIride, Boolean isConsultazione) {
		String prf = "[MonitoraggioControlliDAOImpl::findAutoritaControlli]";
		LOG.debug(prf + " BEGIN");
		try {
			PbandiDCategAnagraficaVO anagraficaVO = new PbandiDCategAnagraficaVO();
			List<PbandiDCategAnagraficaVO> listAnagrafica = genericDAO.findListWhere(anagraficaVO);	
			HashMap<String, String> transMap = new HashMap<String, String>();
			transMap.put("idCategAnagrafica", "codice");
			transMap.put("descCategAnagrafica", "descrizione");
			LOG.debug(prf + " END");
			return beanUtil.transform(listAnagrafica, CodiceDescrizioneDTO.class, transMap);			
		} catch(Exception e) {
			throw e;
		}
	}

	
	@Override
	@Transactional
	public EsitoGenerazioneReportDTO generaReportCampionamento(Long idUtente, String idIride,
			FiltroRilevazioniDTO filtroRilevazione) throws Exception {
		String prf = "[MonitoraggioControlliDAOImpl::generaReportCampionamento]";
		LOG.debug(prf + " BEGIN");
		try {
			PbandiVRilevazioniVO vrilev = new PbandiVRilevazioniVO();
			vrilev.setIdNormativa(beanUtil.transform(filtroRilevazione.getIdLineaIntervento(), BigDecimal.class));			
			vrilev.setIdPeriodo(beanUtil.transform(filtroRilevazione.getIdAnnoContabile(), BigDecimal.class));
			vrilev.setIdCategAnagrafica(beanUtil.transform(filtroRilevazione.getIdAutoritaControllante(), BigDecimal.class));	
			vrilev.setIdAsse(beanUtil.transform(filtroRilevazione.getIdAsse(), BigDecimal.class));
			vrilev.setProgrBandoLineaIntervento(beanUtil.transform(filtroRilevazione.getIdBando(), BigDecimal.class));
			//Verifico il tipo controllo
			// se tipo controllo == "A" (Significa tutti quindi non devo filtrare per il tipo Controllo
			if(filtroRilevazione.getTipoControllo() != null && !filtroRilevazione.getTipoControllo().equalsIgnoreCase("A")){
				vrilev.setTipoControlli(filtroRilevazione.getTipoControllo());
			}

			//carica dati per inserire sul file excel || Ottenere i progetti utilizzando il filtro
			List<PbandiVRilevazioniVO> elementiReport = new ArrayList<PbandiVRilevazioniVO>();
			elementiReport = (ArrayList<PbandiVRilevazioniVO>) genericDAO.findListWhere(vrilev);

			//creare un DTO custom 
			//La vista presente le provvisorie e le definitive in modo separato
			List<RilevazioneProgettoDTO> elencoElementiReport = new ArrayList<RilevazioneProgettoDTO>();
			RilevazioneProgettoDTO rilevazioneR = null;
			for (PbandiVRilevazioniVO elemento : elementiReport) {
				rilevazioneR = beanUtil.transform(elemento, RilevazioneProgettoDTO.class);
				if ((elemento.getIdIrregolaritaCollegProvv() != null && elemento.getIdIrregolaritaCollegProvv() != null && elemento.getIdIrregolaritaProvv().compareTo(elemento.getIdIrregolaritaCollegProvv()) == 0)|| (elemento.getIrregolaritaAnnullataProvv() != null && elemento.getIrregolaritaAnnullataProvv().equalsIgnoreCase("N"))) {
					rilevazioneR.setEsitoProvvisorio("IRREGOLARE");
				} else if (elemento.getIrregolaritaAnnullataProvv() != null && elemento.getIrregolaritaAnnullataProvv().equalsIgnoreCase("S")) {
					rilevazioneR.setEsitoProvvisorio("REGOLARE");
				}
				//ESITO DEF.
				if(elemento.getIdEsitoControllo() != null && elemento.getIdEsitoControllo().longValue() > 0){
					rilevazioneR.setEsitoDefinitivo("REGOLARE");
				} else if (elemento.getIdIrregolarita() != null) {
					rilevazioneR.setEsitoDefinitivo("IRREGOLARE");
				} else if (elemento.getIrregolaritaAnnullataProvv() != null && elemento.getIrregolaritaAnnullataProvv().equalsIgnoreCase("S") && elemento.getIdIrregolarita() == null) {
					rilevazioneR.setEsitoDefinitivo("REGOLARE");
				}
				
				if(elemento.getIdEsitoControllo() != null && elemento.getIdEsitoControllo().longValue() > 0){
					rilevazioneR.setDataInizioControlli(elemento.getDtInizioControlliRegolari());
					rilevazioneR.setDataFineControlli(elemento.getDtFineControlliRegolari());
				}else if(elemento.getIdIrregolarita() != null){
					rilevazioneR.setDataInizioControlli(elemento.getDataInizioControlli());
					rilevazioneR.setDataFineControlli(elemento.getDataFineControlli());
					rilevazioneR.setNoteEsitoRegolare(elemento.getNoteIrregolarita());
				}else if(elemento.getIdIrregolaritaProvv() != null){
					rilevazioneR.setDataInizioControlli(elemento.getDataInizioControlliProvv());
					rilevazioneR.setDataFineControlli(elemento.getDataFineControlliProvv());
				}				
				elencoElementiReport.add(rilevazioneR);
			}

			ArrayList<Long> linesToJump = new ArrayList<Long>();
			linesToJump.add(1L);
			linesToJump.add(2L);

			String templateKey = Constants.TEMPLATE_REPORT_PROGETTI_CAMPIONI;
			
			byte[] reportResult = TableWriter
			.writeTableToByteArray(templateKey, new ExcelDataWriter(Constants.TEMPLATE_REPORT_PROGETTI_CAMPIONI), elencoElementiReport, linesToJump, false);

			Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
			singlePageTables.put("REPORT", reportResult);

			reportResult = reportManager.getMergedDocumentFromTemplate(templateKey, singlePageTables, null);

			LOG.info("############################# REPORT GENERATO CON SUCCESSO! #############################");

			EsitoGenerazioneReportDTO report = new EsitoGenerazioneReportDTO();
			report.setEsito(Boolean.TRUE);
			report.setReport(reportResult);
			LOG.debug(prf + " END");
			return report;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoElencoProgettiCampione caricaProgettiCampione(Long idUtente, String idIride, Long[] elencoIdprogetti,
			FiltroRilevazioniDTO filtroRilevazione) throws Exception {
		String prf = "[MonitoraggioControlliDAOImpl::caricaProgettiCampione]";
		LOG.debug(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaIride", "elencoIdprogetti", "filtroRilevazione.idLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, elencoIdprogetti, filtroRilevazione.getIdLineaIntervento());

			for (Long id : elencoIdprogetti) {
				LOG.info("   "+id);
			}
			
			BigDecimal idLineaIntervento = NumberUtil.toBigDecimal(filtroRilevazione.getIdLineaIntervento());
			BigDecimal idCategAnagrafica = NumberUtil.toBigDecimal(filtroRilevazione.getIdAutoritaControllante());
			BigDecimal idPeriodo = NumberUtil.toBigDecimal(filtroRilevazione.getIdAnnoContabile());
			
			LOG.debug("---inizio carico lista progettiGiaCampionati---");
			// Recupera i progetti gia' campionati per normativa, categ. anagr., periodo e tipo controllo. 
			CampionamentoProgettiEsistentiVO filtroProgettiEsistenti = new CampionamentoProgettiEsistentiVO();
			filtroProgettiEsistenti.setIdLineaDiIntervento(idLineaIntervento);
			filtroProgettiEsistenti.setIdCategAnagrafica(idCategAnagrafica);
			filtroProgettiEsistenti.setIdPeriodo(idPeriodo);
			filtroProgettiEsistenti.setTipoControlli(filtroRilevazione.getTipoControllo());
			filtroProgettiEsistenti.setDataCampionamento(new java.sql.Date(filtroRilevazione.getDataCampione().getTime()));
			
			// progettiGiaCampionati = tutti i progetti del campione
			ArrayList<CampionamentoProgettiEsistentiVO> progettiGiaCampionati = null;
			progettiGiaCampionati = (ArrayList<CampionamentoProgettiEsistentiVO>) genericDAO.findListWhere(filtroProgettiEsistenti);

			if(progettiGiaCampionati!=null){
				for (CampionamentoProgettiEsistentiVO p : progettiGiaCampionati)
					LOG.info("progettiGiaCampionati: "+p.getIdProgetto());
			}else{
				LOG.info("nessun progettiGiaCampionati trovato"); 
			}
			LOG.debug("---fine carico lista progettiGiaCampionati---");
			
			// Inizializza gli elenchi da restituire in output.
			ArrayList<ProgettoDTO> progettiEsistenti = new ArrayList<ProgettoDTO>();
			ArrayList<ProgettoDTO> progettiDaAggiungere = new ArrayList<ProgettoDTO>();
			ArrayList<ProgettoDTO> progettiDelCampione = this.trasf(progettiGiaCampionati);
			
			String progettiScartati = "";
			
			// Scorre i progetti in input, dividendoli in già presenti, da aggiungere e scartati.			
			for(Long idProgetto : elencoIdprogetti) {

				// Verifica se il progetto in input e' tra quelli già campionati (gia' esistenti).
				ProgettoDTO progettoEsistente = this.isProgettoGiaEsistente(idProgetto, progettiDelCampione);
				if (progettoEsistente != null) {
					progettiEsistenti.add(progettoEsistente);
//					progettiDelCampione.add(progettoEsistente);
					LOG.info("=======> id progetto "+idProgetto+" già esistente.");
					// Passa al progetto successivo.
					continue;
				}

				// Verifica se il progetto esiste e ha la stessa normativa.
				ProgettoCampionatoVO progettoDaAggiungere = new ProgettoCampionatoVO();
				progettoDaAggiungere.setIdProgetto(new BigDecimal(idProgetto));
				progettoDaAggiungere.setIdLineaDiIntervento(idLineaIntervento);
				progettoDaAggiungere = genericDAO.findSingleOrNoneWhere(progettoDaAggiungere);
				if (progettoDaAggiungere != null) {
					ProgettoDTO dto = beanUtil.transform(progettoDaAggiungere, ProgettoDTO.class);
					progettiDaAggiungere.add(dto);
					LOG.info("=======> id progetto "+dto.getIdProgetto()+" da aggiungere.");
					// Passa al progetto successivo.
					continue;
				}
				
				// Inserisce il progetto tra gli scartati.
				if (progettiScartati.length() > 0)
					progettiScartati = progettiScartati + ";";
				progettiScartati = progettiScartati + idProgetto.intValue();
				LOG.info("=======> id progetto "+idProgetto+" scartato.");
			}
			
			EsitoElencoProgettiCampione output = new EsitoElencoProgettiCampione();
			output.setProgettiGiaPresenti(progettiEsistenti.toArray(new ProgettoDTO[progettiEsistenti.size()]));
			output.setProgettiDaAggiungere(progettiDaAggiungere.toArray(new ProgettoDTO[progettiDaAggiungere.size()]));
			output.setProgettiScartati(progettiScartati);
			output.setProgettiDelCampione(progettiDelCampione.toArray(new ProgettoDTO[progettiDelCampione.size()]));
			
			for (ProgettoDTO p : output.getProgettiGiaPresenti())
				LOG.info("ProgettiGiaPresenti: "+p.getIdProgetto());
			for (ProgettoDTO p : output.getProgettiDaAggiungere())
				LOG.info("ProgettiDaAggiungere: "+p.getIdProgetto());
			LOG.info("ProgettiScartati: "+output.getProgettiScartati());
			for (ProgettoDTO p : output.getProgettiDelCampione())
				LOG.info("ProgettiDelCampione: "+p.getIdProgetto());
			
			LOG.info("getElencoProgettiCampione(): FINE");
			LOG.debug(prf + " END");
			return output;
		} catch(Exception e) {
			throw e;
		}
	}

	private ProgettoDTO isProgettoGiaEsistente(Long idProgetto, ArrayList<ProgettoDTO> progettiDelCampione) {
		String prf = "[MonitoraggioControlliDAOImpl::isProgettoGiaEsistente]";
		LOG.debug(prf + " START");
		
		for (ProgettoDTO progettoDTO : progettiDelCampione) {
			if(idProgetto.intValue() == progettoDTO.getIdProgetto().intValue()){
				LOG.debug(prf + " END");
				return progettoDTO;
			}
		}
		LOG.debug(prf + " END");
		return null;
	}

	private ArrayList<ProgettoDTO> trasf(ArrayList<CampionamentoProgettiEsistentiVO> progettiGiaCampionati) {
		String prf = "[MonitoraggioControlliDAOImpl::trasf]";
		LOG.debug(prf + " START");
		ArrayList<ProgettoDTO> lista = null;
		if(progettiGiaCampionati!=null){
			
			lista = new ArrayList<ProgettoDTO>();
			for (CampionamentoProgettiEsistentiVO progettoGiaCampionato : progettiGiaCampionati) {
				ProgettoCampionatoVO vo = new ProgettoCampionatoVO();
				vo.setIdProgetto(new BigDecimal(progettoGiaCampionato.getIdProgetto().longValue()));
				vo.setIdLineaDiIntervento(progettoGiaCampionato.getIdLineaDiIntervento());
				vo = genericDAO.findSingleWhere(vo);
				ProgettoDTO dto = beanUtil.transform(vo, ProgettoDTO.class);
				dto.setDataCampionamento(progettoGiaCampionato.getDataCampionamento());
				lista.add(dto);
			}
		}
		LOG.debug(prf + " END");
		return lista;
	}

	@Override
	@Transactional
	public EsitoCampionamentoDTO registraCampionamentoProgetti(Long idUtente, String idIride,
			FiltroRilevazioniDTO filtroRilevazione, Long[] elencoIdprogetti) throws Exception {
		String prf = "[MonitoraggioControlliDAOImpl::registraCampionamentoProgetti]";
		LOG.debug(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "filtroRilevazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, filtroRilevazione);
			
			if (elencoIdprogetti != null && elencoIdprogetti.length > 0) {
				for (Long id : elencoIdprogetti) {
					LOG.info(prf + " registraCampionamentoProgetti(): id progetto da inserire : "+id);	
				}
			} else {			
				LOG.info(prf + " registraCampionamentoProgetti(): nessun id progetto da inserire.");	
			}
	
			// Verifica se esiste già un record nella T_CAMPIONAMENTO avente i parametri in input.
			PbandiTCampionamentoVO campionamentoVO = new PbandiTCampionamentoVO();
			campionamentoVO.setIdCategAnagrafica(NumberUtil.toBigDecimal(filtroRilevazione.getIdAutoritaControllante()));
			campionamentoVO.setIdPeriodo(NumberUtil.toBigDecimal(filtroRilevazione.getIdAnnoContabile()));
			campionamentoVO.setIdLineaDiIntervento(NumberUtil.toBigDecimal(filtroRilevazione.getIdLineaIntervento()));
			campionamentoVO.setTipoControlli(filtroRilevazione.getTipoControllo());
			campionamentoVO.setDataCampionamento(DateUtil.utilToSqlDate(filtroRilevazione.getDataCampione()));
			PbandiTCampionamentoVO campionamentoExisting = genericDAO.findSingleOrNoneWhere(campionamentoVO);
	
			// Se tale record non esiste, lo inserisco.
			if (campionamentoExisting == null) {
				LOG.info(prf + " registraCampionamentoProgetti(): insert su PBANDI_T_CAMPIONAMENTO.");
				campionamentoExisting = genericDAO.insert(campionamentoVO);	
			} else {
				LOG.info(prf + " registraCampionamentoProgetti(): record su PBANDI_T_CAMPIONAMENTO già presente.");	
			} 
			
			// Inserisco i progetti sulla R_CAMPIONAMENTO.
			PbandiRCampionamentoVO progettoCampione = null;
			for (Long idProgetto : elencoIdprogetti) {
				progettoCampione = new PbandiRCampionamentoVO();
				progettoCampione.setIdCampione(campionamentoExisting.getIdCampione());
				progettoCampione.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
				LOG.info(prf + " registraCampionamentoProgetti(): insert su PBANDI_R_CAMPIONAMENTO: idCampione = "+progettoCampione.getIdCampione()+" e idprogetto = "+progettoCampione.getIdProgetto());
				progettoCampione = genericDAO.insert(progettoCampione);
			}
			
			EsitoCampionamentoDTO esito = new EsitoCampionamentoDTO();
			esito.setEsito(Boolean.TRUE);
			esito.setMessagge(MessageConstants.MSG_ACQUISIZIONE_CAMPIONE_SUCCESSO + " " + elencoIdprogetti.toString());			
			
			LOG.debug(prf + " END");
			return esito;
		} catch(Exception e) {
			throw e;
		}	
	}

	
	
}
