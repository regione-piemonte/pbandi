/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.gestioneeconomie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

//import antlr.StringUtils;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.BandoLinea;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.EconomiaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.EsitoOperazioneEconomia;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.MessaggioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.ParametriRicercaEconomieDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.ProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.QuotaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestioneeconomie.GestioneEconomieException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EconomiaByBeneficiarioRiceventeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EconomiaByBeneficiarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EconomiaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EconomieProgettiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ImportoCedutoEconomiaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RegolaAssociataBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestioneeconomie.QuotaEconomiaSoggettoFinanziatoreRiceventeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestioneeconomie.QuotaEconomiaSoggettoFinanziatoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestioneeconomie.QuotaSoggettoFinanziatoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.LikeStartsWithCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.OrCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiREconomSoggFinanziatVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEconomieVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestioneeconomie.GestioneEconomieSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;


public class GestioneEconomieBusinessImpl implements GestioneEconomieSrv {
	
	@Autowired
	private RegolaManager regolaManager;
	@Autowired
	private BeanUtil beanUtil;
	private GestioneBackofficeBusinessImpl gestioneBackofficeBusiness;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	protected LoggerUtil logger;
	@Autowired
	private ProgettoManager progettoManager;


	public BandoLinea[] findBandoLineaDaRegola(Long idUtente,
			String identitaDigitale, String regola) throws CSIException,
			SystemException, UnrecoverableException, GestioneEconomieException {
		
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "regola" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, regola);
			List<RegolaAssociataBandoLineaVO> queryResult = regolaManager.findBandolineaAssociatiRegola("BR46");
			return beanUtil.transform(queryResult, BandoLinea.class);
		}catch(Exception ex){
			throw new GestioneEconomieException("Errore durante la chiamata del metodo findBandoLineaDaRegola: " + ex);
		}	
	}

	public ProgettoDTO[] findProgettiByBandoLinea(Long idUtente,
			String identitaDigitale, Long idBandoLinea) throws CSIException,
			SystemException, UnrecoverableException, GestioneEconomieException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
		"idBando" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idBandoLinea);
		
		BandoLineaProgettoVO filtroBandoLineaProgetto = new BandoLineaProgettoVO();
		filtroBandoLineaProgetto.setProgrBandoLineaIntervento(new BigDecimal(idBandoLinea));
		filtroBandoLineaProgetto.setAscendentOrder("codiceVisualizzato");
		return beanUtil.transform(genericDAO.findListWhere(filtroBandoLineaProgetto), ProgettoDTO.class);
	}
	
	public EconomiaDTO[] findEconomie(Long idUtente, String identitaDigitale,
			EconomiaDTO filtro) throws CSIException, SystemException,
			UnrecoverableException, GestioneEconomieException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);
		
		if(filtro == null){
			return beanUtil.transform(genericDAO.findListAll(EconomieProgettiVO.class), EconomiaDTO.class);
		}else{
			EconomieProgettiVO filtroVO = beanUtil.transform(filtro, EconomieProgettiVO.class);
			List<EconomieProgettiVO> economieProgettoList = genericDAO.findListWhere(Condition.filterBy(filtroVO));
			EconomiaDTO[] economieDTO = beanUtil.transform(economieProgettoList, EconomiaDTO.class);
			for(EconomiaDTO econoDTO : economieDTO){
				econoDTO.setIdBandoLineaCedente(progettoManager.findBandoLineaForProgetto(econoDTO.getIdProgettoCedente()).getProgrBandoLineaIntervento().longValue());
				if(econoDTO.getIdProgettoRicevente() != null)
					econoDTO.setIdBandoLineaRicevente(progettoManager.findBandoLineaForProgetto(econoDTO.getIdProgettoRicevente()).getProgrBandoLineaIntervento().longValue());
			}
			return economieDTO;
		}
	}
	
	// CDU-EC-V02
	public EconomiaDTO[] findEconomie2(Long idUtente, String identitaDigitale,
			ParametriRicercaEconomieDTO parametriRicerca) throws CSIException, SystemException,
			UnrecoverableException, GestioneEconomieException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "parametriRicerca"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,	identitaDigitale, parametriRicerca);
		
		logger.info("findEconomie2(): parametri:");
		logger.shallowDump(parametriRicerca, "info");
		
		String filtro = parametriRicerca.getFiltro();
		
		// Filtro per forzare l'ordinamento.
		EconomiaVO vo = new EconomiaVO();
		vo.setDescendentOrder("dataInserimento");
		FilterCondition filter = new FilterCondition<EconomiaVO>(vo);
		
		NullCondition<EconomiaVO> nullCondImporto = new NullCondition<EconomiaVO>(EconomiaVO.class,"importoCeduto");
		NotCondition<EconomiaVO> notNullCondImporto = new NotCondition<EconomiaVO>(nullCondImporto);
		
		List<EconomiaVO> lista = new ArrayList<EconomiaVO>();
		if ("1".equals(filtro)) {
			NullCondition<EconomiaVO> nullCond = new NullCondition<EconomiaVO>(EconomiaVO.class,"importoCeduto");
			NotCondition<EconomiaVO> notNullCond = new NotCondition<EconomiaVO>(nullCond);
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCond);
			// Tutte le economie.
			lista = genericDAO.findListWhere(andCond);
		} else if ("2".equals(filtro)) {
			// Economie ancora da ricevere: idProgettoRicevente = null.
			NullCondition<EconomiaVO> nullCond = new NullCondition<EconomiaVO>(EconomiaVO.class,"idProgettoRicevente");
			
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,nullCond, notNullCondImporto);
			lista = genericDAO.findListWhere(andCond);
		} else if ("3".equals(filtro)) {
			// Economie gi� ricevute: idProgettoRicevente not null.			
			NullCondition<EconomiaVO> nullCond = new NullCondition<EconomiaVO>(EconomiaVO.class,"idProgettoRicevente");
			NotCondition<EconomiaVO> notNullCond = new NotCondition<EconomiaVO>(nullCond);
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCond, notNullCondImporto);
			lista = genericDAO.findListWhere(andCond);
		} else if ("4".equals(filtro)) {
			// Economie da utilizzare: idProgettoRicevente not null and dataUtilizzo = null.
			NullCondition<EconomiaVO> nullCond1 = new NullCondition<EconomiaVO>(EconomiaVO.class,"idProgettoRicevente");
			NotCondition<EconomiaVO> notNullCond1 = new NotCondition<EconomiaVO>(nullCond1);
			NullCondition<EconomiaVO> nullCond2 = new NullCondition<EconomiaVO>(EconomiaVO.class,"dataUtilizzo");
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCond1,nullCond2, notNullCondImporto);
			lista = genericDAO.findListWhere(andCond);
		} else if ("5".equals(filtro)) {
			// Economie gi� utilizzate: idProgettoRicevente not null and dataUtilizzo not null.
			NullCondition<EconomiaVO> nullCond1 = new NullCondition<EconomiaVO>(EconomiaVO.class,"idProgettoRicevente");
			NotCondition<EconomiaVO> notNullCond1 = new NotCondition<EconomiaVO>(nullCond1);
			NullCondition<EconomiaVO> nullCond2 = new NullCondition<EconomiaVO>(EconomiaVO.class,"dataUtilizzo");
			NotCondition<EconomiaVO> notNullCond2 = new NotCondition<EconomiaVO>(nullCond2);
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCond1,notNullCond2, notNullCondImporto);
			lista = genericDAO.findListWhere(andCond);
		} else if ("6".equals(filtro)) {
			//  Filtra per Beneficiario cedente.
			String beneficiario = "%"+parametriRicerca.getBeneficiario()+"%";
			EconomiaByBeneficiarioVO vo1 = new EconomiaByBeneficiarioVO();
			vo1.setDenominazioneEnteGiuridico(beneficiario);
			vo1.setDescendentOrder("dataInserimento");
			LikeStartsWithCondition likeCond = new LikeStartsWithCondition<EconomiaByBeneficiarioVO>(vo1);
			
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCondImporto, likeCond);
			
			lista = genericDAO.findListWhere(andCond);
		} else if ("7".equals(filtro)) {
			// Filtra per codice progetto cedente.	
			String progetto = "%"+parametriRicerca.getProgetto()+"%";
			vo.setCodiceVisualizzatoCedente(progetto);
			LikeStartsWithCondition likeCond = new LikeStartsWithCondition<EconomiaVO>(vo);
			
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCondImporto, likeCond);
			
			lista = genericDAO.findListWhere(andCond);
		} else if ("8".equals(filtro)) {
			//  Filtra per Beneficiario ricevente.
			String beneficiario = "%"+parametriRicerca.getBeneficiarioRicevente()+"%";
			EconomiaByBeneficiarioRiceventeVO vo2 = new EconomiaByBeneficiarioRiceventeVO();
			vo2.setDenominazioneEnteGiuridico(beneficiario);
			vo2.setDescendentOrder("dataInserimento");
			LikeStartsWithCondition likeCond = new LikeStartsWithCondition<EconomiaByBeneficiarioRiceventeVO>(vo2);
			
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCondImporto, likeCond);
			
			lista = genericDAO.findListWhere(andCond);
		} else if ("9".equals(filtro)) {
			// Filtra per codice progetto ricevente.	
			String progetto = "%"+parametriRicerca.getProgettoRicevente()+"%";
			vo.setCodiceVisualizzatoRicevente(progetto);
			LikeStartsWithCondition likeCond = new LikeStartsWithCondition<EconomiaVO>(vo);
			
			AndCondition andCond = new AndCondition<EconomiaVO>(filter,notNullCondImporto, likeCond);
			
			lista = genericDAO.findListWhere(andCond);
		} else if ("99".equals(filtro)) {
			// Estrae le economie aventi un dato idProgetto come cedente o ricevente.
			// Usato quando si arriva in Economie da Rendicontazione.
			if (!StringUtil.isEmpty(parametriRicerca.getProgettoRicevente())) {
				BigDecimal idProgetto = new BigDecimal(parametriRicerca.getProgettoRicevente());
				EconomiaVO ricevente = new EconomiaVO();
				ricevente.setIdProgettoRicevente(idProgetto);
				EconomiaVO cedente = new EconomiaVO();
				cedente.setIdProgettoCedente(idProgetto);
				Condition<EconomiaVO> orCondition = new OrCondition<EconomiaVO>(
						Condition.filterBy(ricevente),
						Condition.filterBy(cedente));
				lista = genericDAO.findListWhere(orCondition);
			}
		}
		
		EconomiaDTO[] result = beanUtil.transform(lista, EconomiaDTO.class);
		
		return result;
	}
	
	public EsitoOperazioneEconomia updateDataUtilizzoEconomia(Long idUtente,
			String identitaDigitale, Long idEconomia, java.util.Date dataUtilizzo) throws CSIException,
			SystemException, UnrecoverableException, GestioneEconomieException {
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "idEconomia"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idEconomia);
			
			PbandiTEconomieVO vo = new PbandiTEconomieVO();
			vo.setIdEconomia(new BigDecimal(idEconomia));
			vo = genericDAO.findSingleWhere(vo);
			
			vo.setDataUtilizzo(DateUtil.utilToSqlDate(dataUtilizzo));
			vo.setIdUtenteAgg(new BigDecimal(idUtente)); 
			vo.setDataModifica(DateUtil.getSysdate());
			genericDAO.updateNullables(vo);
			
			EsitoOperazioneEconomia esito = new EsitoOperazioneEconomia();
			esito.setEsito(true);			
			
			return esito;
			
		}catch(Exception ex){
			throw new GestioneEconomieException("Errore durante l'esecuzione di updateDataUtilizzoEconomia: " + ex.getMessage());
		}
	}
	
	public EsitoOperazioneEconomia saveUpdateEconomia(Long idUtente,
			String identitaDigitale, EconomiaDTO economia) throws CSIException,
			SystemException, UnrecoverableException, GestioneEconomieException {
	
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "economia"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, economia);

			EsitoOperazioneEconomia esito = new EsitoOperazioneEconomia();

			PbandiTEconomieVO economiaVO = beanUtil.transform(economia, PbandiTEconomieVO.class);
			
			if(economiaVO.isPKValid()){
				economiaVO.setIdUtenteAgg(new BigDecimal(idUtente)); 
				economiaVO.setDataModifica(DateUtil.getSysdate());
			}
			else
				economiaVO.setIdUtenteIns(new BigDecimal(idUtente));
	
			economiaVO = genericDAO.insertOrUpdateExisting(economiaVO);
			
			if(economiaVO != null && economiaVO.getIdEconomia() != null){
				esito.setEsito(Boolean.TRUE);
				esito.setIdEconomia(economiaVO.getIdEconomia().longValue());
			}else{
				esito.setEsito(Boolean.FALSE);
			}
			
			return esito;
		}catch(Exception ex){
			throw new GestioneEconomieException("Errore durante l'esecuzione di saveUpdateEconomia: " + ex.getMessage());
		}
	}
	
	public Double getImportoCedutoProgetto(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idEconomia) throws CSIException,
			SystemException, UnrecoverableException, GestioneEconomieException {
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);
			
			ImportoCedutoEconomiaVO filtroImportoCeduto = new ImportoCedutoEconomiaVO(); 
			filtroImportoCeduto.setIdProgettoCedente(new BigDecimal(idProgetto));
			Double importoEconomiaAttuale = null;
			if(idEconomia != null){
				PbandiTEconomieVO economia = new PbandiTEconomieVO();
				economia.setIdEconomia(new BigDecimal(idEconomia));
				economia = genericDAO.findFirstWhere(Condition.filterBy(economia));
				importoEconomiaAttuale = economia.getImportoCeduto();
			}
			filtroImportoCeduto = genericDAO.findSingleOrNoneWhere(filtroImportoCeduto);
			if(filtroImportoCeduto != null)
				return importoEconomiaAttuale == null ? filtroImportoCeduto.getImportoCeduto() : filtroImportoCeduto.getImportoCeduto() - importoEconomiaAttuale;
			else 
				return new Double(0);
		}catch(Exception ex){
			logger.error("Errore durante l'esecuzione del metodo getImportoCedutoProgetto ", ex);
			throw new GestioneEconomieException("Errore durante l'esecuzione del metodo getImportoCedutoProgetto " + ex);
		}
	}

	public EsitoOperazioneEconomia eliminaEconomia(Long idUtente,
			String identitaDigitale, Long idEconomia) throws CSIException,
			SystemException, UnrecoverableException, GestioneEconomieException {
		
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "idEconomia"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idEconomia);
			
			EsitoOperazioneEconomia esito = new EsitoOperazioneEconomia();
			PbandiTEconomieVO economiaToDeleteVO = new PbandiTEconomieVO(new BigDecimal(idEconomia));
			economiaToDeleteVO = genericDAO.findFirstWhere(Condition.filterBy(economiaToDeleteVO));
			if(economiaToDeleteVO != null && economiaToDeleteVO.getDataUtilizzo() != null){
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorConstants.ERRORE_IMPOSSIBILE_ELIMINARE_ECOMONIA);
				esito.setMsgs(msg);
			}else{
				//Eliminare i recoda dalla tabella pbandi_r_econom_sogg_finanziat
				PbandiREconomSoggFinanziatVO filtroESF = new PbandiREconomSoggFinanziatVO();
				filtroESF.setIdEconomia(new BigDecimal(idEconomia));
				List<PbandiREconomSoggFinanziatVO> econoSoggettiFinanziatList = (List<PbandiREconomSoggFinanziatVO>) genericDAO.findListWhere(filtroESF);
				if(econoSoggettiFinanziatList != null) {
					for (PbandiREconomSoggFinanziatVO vo : econoSoggettiFinanziatList) {
						genericDAO.delete(vo);
					}
				}
				esito.setEsito(genericDAO.delete(economiaToDeleteVO));
			}
			return esito;
		}catch(Exception ex){
			logger.error("Errore durante l'esecuzione del metodo eliminaEconomia: ", ex);
			throw new GestioneEconomieException("Errore durante l'esecuzione del metodo eliminaEconomia: " + ex);
		}
	}
	
	public QuotaDTO[] findQuoteProgetto(Long idUtente, String identitaDigitale,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneEconomieException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto);
			
			QuotaSoggettoFinanziatoreVO filtroQuote = new QuotaSoggettoFinanziatoreVO();
			filtroQuote.setIdProgetto(BigDecimal.valueOf(idProgetto));
			List<QuotaSoggettoFinanziatoreVO> queryResult = genericDAO.findListWhere(filtroQuote);
			return beanUtil.transform(queryResult, QuotaDTO.class);
		} catch (Exception ex) {
			logger.error("Errore durante l'esecuzione del metodo eliminaEconomia: ", ex);
			throw new GestioneEconomieException("Errore durante l'esecuzione del metodo eliminaEconomia: " + ex);
		}
	}

	public EsitoOperazioneEconomia saveQuoteProgetto(Long idUtente, String identitaDigitale,
			QuotaDTO[] quote, QuotaDTO[] quoteRicevente, Long idEconomia) throws CSIException, SystemException,
			UnrecoverableException, GestioneEconomieException {
		
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "quote"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, quote);
			
			EsitoOperazioneEconomia esito = new EsitoOperazioneEconomia();
			Map<String, String> trsMap = new HashMap<String, String>();
			
			trsMap.put("percQuotaSoggFinanziatore", "percQuotaSoggFinanziat");
			trsMap.put("importo", "impQuotaEconSoggFinanziat");
			trsMap.put("idEconomia", "idEconomia");
			trsMap.put("idSoggettoFinanziatore", "idSoggettoFinanziatore");
			
			// Quote progetto cedente.			
			List<PbandiREconomSoggFinanziatVO> economSoggFinanziat = beanUtil.transformToArrayList(quote, PbandiREconomSoggFinanziatVO.class, trsMap);			
			for(PbandiREconomSoggFinanziatVO ecSoFi : economSoggFinanziat) {
				ecSoFi.setIdUtenteIns(BigDecimal.valueOf(idUtente));
				ecSoFi.setDtInserimento(DateUtil.getSysdate());
				ecSoFi.setTipologiaProgetto(Constants.TIPOLOGIA_PROGETTO_CEDENTE);
				ecSoFi.setIdEconomia( new BigDecimal( idEconomia) );
			}			
			genericDAO.insert(economSoggFinanziat);
			
			// Quote progetto ricevente.			
			if (quoteRicevente != null && quoteRicevente.length > 0) {			
				List<PbandiREconomSoggFinanziatVO> economSoggFinanziatRicevente = beanUtil.transformToArrayList(quoteRicevente, PbandiREconomSoggFinanziatVO.class, trsMap);			
				for(PbandiREconomSoggFinanziatVO ecSoFi : economSoggFinanziatRicevente){
					ecSoFi.setIdUtenteIns(BigDecimal.valueOf(idUtente));
					ecSoFi.setDtInserimento(DateUtil.getSysdate());
					ecSoFi.setTipologiaProgetto(Constants.TIPOLOGIA_PROGETTO_RICEVENTE);
					ecSoFi.setIdEconomia( new BigDecimal( idEconomia) );
				}				
				genericDAO.insert(economSoggFinanziatRicevente);
			}
			
			esito.setEsito(true);
			return esito;
		} catch (Exception ex) {
			logger.error("Errore durante l'esecuzione del metodo salvaQuoteProgetto: ", ex);
			throw new GestioneEconomieException("Errore durante l'esecuzione del metodo salvaQuoteProgetto: " + ex);
		}
	}
	
	public EsitoOperazioneEconomia modificaQuoteProgetto(Long idUtente,
			String identitaDigitale, QuotaDTO[] quote, QuotaDTO[] quoteRicevente) throws CSIException,
			SystemException, UnrecoverableException, GestioneEconomieException {
		
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "quote"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, quote);
			
			// Id Economia.
			Long idEconomia = quote[0].getIdEconomia();
			if (idEconomia == null) {
				throw new Exception("Id Economia non trovato.");
			}
			logger.info("modificaQuoteProgetto(): id economia = "+idEconomia);
			
			EsitoOperazioneEconomia esito = new EsitoOperazioneEconomia();
			Map<String, String> trsMap = new HashMap<String, String>();
			
			trsMap.put("percQuotaSoggFinanziatore", "percQuotaSoggFinanziat");
			trsMap.put("importo", "impQuotaEconSoggFinanziat");
			trsMap.put("idEconomia", "idEconomia");
			trsMap.put("idSoggettoFinanziatore", "idSoggettoFinanziatore");
			
			// Quote progetto cedente.
			List<PbandiREconomSoggFinanziatVO> economSoggFinanziat = beanUtil.transformToArrayList(quote, PbandiREconomSoggFinanziatVO.class, trsMap);			
			for(PbandiREconomSoggFinanziatVO ecSoFi : economSoggFinanziat) {
				ecSoFi.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
				ecSoFi.setDtAggiornamento(DateUtil.getSysdate());
				ecSoFi.setTipologiaProgetto(Constants.TIPOLOGIA_PROGETTO_CEDENTE);
				genericDAO.update(ecSoFi);
			}
			
			// Quote progetto ricevente.
			
			logger.info("modificaQuoteProgetto(): Cancello le quote ricevente gi� esistenti.");
			PbandiREconomSoggFinanziatVO filtro = new PbandiREconomSoggFinanziatVO();
			filtro.setIdEconomia(new BigDecimal(idEconomia));
			filtro.setTipologiaProgetto(Constants.TIPOLOGIA_PROGETTO_RICEVENTE);
			List<PbandiREconomSoggFinanziatVO> lista = genericDAO.findListWhere(filtro);
			for (PbandiREconomSoggFinanziatVO vo : lista) {
				genericDAO.delete(vo);
			}
			
			logger.info("modificaQuoteProgetto(): Inserisco le quote ricevente passate in input.");
			if (quoteRicevente != null && quoteRicevente.length > 0) {				
				List<PbandiREconomSoggFinanziatVO> economSoggFinanziatRicevente = beanUtil.transformToArrayList(quoteRicevente, PbandiREconomSoggFinanziatVO.class, trsMap);			
				for(PbandiREconomSoggFinanziatVO ecSoFi : economSoggFinanziatRicevente) {
					// se importo valorizzato e maggiore di 0
					if (ecSoFi.getImpQuotaEconSoggFinanziat() != null &&
					    ecSoFi.getImpQuotaEconSoggFinanziat().signum() > 0) {
						ecSoFi.setIdUtenteIns(BigDecimal.valueOf(idUtente));
						ecSoFi.setDtInserimento(DateUtil.getSysdate());
						ecSoFi.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
						ecSoFi.setDtAggiornamento(DateUtil.getSysdate());
						ecSoFi.setTipologiaProgetto(Constants.TIPOLOGIA_PROGETTO_RICEVENTE);
						genericDAO.insert(ecSoFi);
					}					
				}
			}
			
			esito.setEsito(true);
			return esito;
		} catch (Exception ex) {
			logger.error("Errore durante l'esecuzione del metodo salvaQuoteProgetto: ", ex);
			throw new GestioneEconomieException("Errore durante l'esecuzione del metodo salvaQuoteProgetto: " + ex);
		}
	}

	// tipologiaProgetto: C = cedente; R = ricevente.
	public QuotaDTO[] findQuoteEconomia(Long idUtente, String identitaDigitale,
			Long idProgetto, Long idEconomia, String tipologiaProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneEconomieException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto", "tipologiaProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto, tipologiaProgetto);
			
			if (idEconomia != null) {
				
				Map<String, String> trsMap = new HashMap<String, String>();
				trsMap.put("descSoggFinanziatore", "descSoggFinanziatore");
				trsMap.put("idSoggettoFinanziatore",
						"idSoggettoFinanziatore");
				trsMap.put("idEconomia", "idEconomia");
				trsMap.put("impQuotaEconSoggFinanziat",
						"impQuotaEconSoggFinanziat");
				trsMap.put("percQuotaSoggFinanziat",
						"percQuotaSoggFinanziatore");
				trsMap.put("idProgetto", "idProgetto");
				
				// Distinguo tra progetto cedente "C" e ricevente "R".
				if ("C".equalsIgnoreCase(tipologiaProgetto)) {
					QuotaEconomiaSoggettoFinanziatoreVO filtroQuote = new QuotaEconomiaSoggettoFinanziatoreVO();
					filtroQuote.setIdProgetto(BigDecimal.valueOf(idProgetto));
					filtroQuote.setIdEconomia(BigDecimal.valueOf(idEconomia));
					filtroQuote.setTipologiaProgetto(tipologiaProgetto);
					List<QuotaEconomiaSoggettoFinanziatoreVO> queryResult = genericDAO
						.findListWhere(filtroQuote);
					if (queryResult != null && queryResult.size() > 0) {
						return beanUtil.transform(queryResult, QuotaDTO.class,trsMap);
					}
				} else {
					QuotaEconomiaSoggettoFinanziatoreRiceventeVO filtroQuote = new QuotaEconomiaSoggettoFinanziatoreRiceventeVO();
					filtroQuote.setIdProgetto(BigDecimal.valueOf(idProgetto));
					filtroQuote.setIdEconomia(BigDecimal.valueOf(idEconomia));
					filtroQuote.setTipologiaProgetto(tipologiaProgetto);
					List<QuotaEconomiaSoggettoFinanziatoreRiceventeVO> queryResult = genericDAO
						.findListWhere(filtroQuote);
					if (queryResult != null && queryResult.size() > 0) {
						return beanUtil.transform(queryResult, QuotaDTO.class,trsMap);
					}
				}
			}
			
			QuotaSoggettoFinanziatoreVO filtroQuoteProgetto = new QuotaSoggettoFinanziatoreVO();
			filtroQuoteProgetto.setIdProgetto(BigDecimal.valueOf(idProgetto));
			List<QuotaSoggettoFinanziatoreVO> queryResultProgetto = genericDAO.findListWhere(filtroQuoteProgetto);
			return beanUtil.transform(queryResultProgetto, QuotaDTO.class);
						
		} catch (Exception ex) {
			logger.error("Errore durante l'esecuzione del metodo findQuoteEconomia: ", ex);
			throw new GestioneEconomieException("Errore durante l'esecuzione del metodo findQuoteEconomia: " + ex);
		}
	}
	
	public Boolean isProgettoRicevente(Long idUtente, String identitaDigitale,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneEconomieException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,	identitaDigitale, idProgetto);
		logger.info("isProgettoRicevente(): idProgetto = "+idProgetto);
		
		PbandiTEconomieVO vo = new PbandiTEconomieVO(); 
		vo.setIdProgettoRicevente(new BigDecimal(idProgetto));
		List<PbandiTEconomieVO> lista = genericDAO.findListWhere(vo);
		boolean esito = (lista.size() > 0);
		logger.info("isProgettoRicevente(): esito = "+esito);
		
		return esito;
	}
	
	// Calcola la somma degli importi ceduti delle economie 
	// gi� utilizzate (data_utilizzo valorizzata) e
	// aventi il progetto in input come progetto ricevente.
	public Double findSommaEconomieUtilizzate(Long idUtente, String identitaDigitale,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneEconomieException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,	identitaDigitale, idProgetto);
		logger.info("findSommaEconomieUtilizzate(): idProgetto = "+idProgetto);
				
		PbandiTEconomieVO vo = new PbandiTEconomieVO();
		vo.setIdProgettoRicevente(new BigDecimal(idProgetto));
		FilterCondition filter = new FilterCondition<PbandiTEconomieVO>(vo);
		NotCondition<PbandiTEconomieVO> dtUtilizzoValorizzata = new NotCondition<PbandiTEconomieVO>(
				new NullCondition<PbandiTEconomieVO>(PbandiTEconomieVO.class,"dataUtilizzo"));
		AndCondition andCond = new AndCondition<PbandiTEconomieVO>(filter,dtUtilizzoValorizzata);
		List<PbandiTEconomieVO> lista = genericDAO.findListWhere(andCond);
		
		Double somma = new Double(0.0);
		for (PbandiTEconomieVO e : lista) {
			somma = somma + e.getImportoCeduto();
		}
		logger.info("findSommaEconomieUtilizzate(): somma ImportoCeduto = "+somma);
		
		return somma;
	}
	
	public RegolaManager getRegolaManager() {
		return regolaManager;
	}

	public void setRegolaManager(RegolaManager regolaManager) {
		this.regolaManager = regolaManager;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}
	
	public GestioneBackofficeBusinessImpl getGestioneBackofficeBusiness() {
		return gestioneBackofficeBusiness;
	}

	public void setGestioneBackofficeBusiness(
			GestioneBackofficeBusinessImpl gestioneBackofficeBusiness) {
		this.gestioneBackofficeBusiness = gestioneBackofficeBusiness;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}
}
