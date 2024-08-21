/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.integration.dao.CronoProgrammaDAO;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.IndicatoriManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoFindFasiMonitoraggio;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoSaveFasiMonitoraggio;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.IterDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.MotivoScostamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.TipoOperazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.FaseMonitoraggioProgettoPre2016VO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.FaseMonitoraggioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDFaseMonitVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDIterVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDMotivoScostamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDNaturaCipeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoOperazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRProgettoFaseMonitVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTBandoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.FileSqlUtil;
import it.csi.pbandi.pbservizit.util.DateUtil;
import it.csi.pbandi.pbservizit.util.ErrorMessages;
import it.csi.pbandi.pbservizit.business.intf.MessageConstants;
import it.csi.pbandi.pbservizit.util.Constants;

@Component
public class CronoProgrammaDAOImpl  extends JdbcDaoSupport implements CronoProgrammaDAO {

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	protected FileSqlUtil fileSqlUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private NeofluxBusinessImpl neofluxBusinessImpl;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	protected FileApiServiceImpl fileApiServiceImpl;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private IndicatoriManager indicatoriManager;
	
	@Autowired
	public CronoProgrammaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
		this.genericDAO = new GenericDAO(dataSource);
	}

	public CronoProgrammaDAOImpl() { }

	@Override
	@Transactional
	public String findCodiceProgetto(Long idProgetto) {
		String prf = "[CronoProgrammaDAOImpl::findCodiceProgetto]";
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
	public String findProgrammazioneByIdProgetto(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException {
		String prf = "[CronoProgrammaDAOImpl::findProgrammazioneByIdProgetto]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride","idProgetto" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);
		String programmazione = "";
		try {
			LOG.info(prf +" idProgetto = "+idProgetto);
			PbandiTProgettoVO progetto = new PbandiTProgettoVO();
			progetto.setIdProgetto(new BigDecimal(idProgetto));
			progetto = genericDAO.findSingleWhere(progetto);
			
			LOG.info(prf +"  idDomanda = "+progetto.getIdDomanda());
			PbandiTDomandaVO domanda = new PbandiTDomandaVO();
			domanda.setIdDomanda(progetto.getIdDomanda());
			domanda = genericDAO.findSingleWhere(domanda);
			
			LOG.info(prf + " progrBandoLinea = "+domanda.getProgrBandoLineaIntervento());
			PbandiRBandoLineaInterventVO bandoLinea = new PbandiRBandoLineaInterventVO();
			bandoLinea.setProgrBandoLineaIntervento(domanda.getProgrBandoLineaIntervento());
			bandoLinea = genericDAO.findSingleWhere(bandoLinea);
			
			LOG.info(prf + "  idBando = "+bandoLinea.getIdBando());
			PbandiTBandoVO bando = new PbandiTBandoVO();
			bando.setIdBando(bandoLinea.getIdBando());
			bando = genericDAO.findSingleWhere(bando);
			
			LOG.info(prf + " idLineaInterventoBando = "+bando.getIdLineaDiIntervento());						
			if (bando.getIdLineaDiIntervento() == null) {				
				// Se il bando non ha la normativa valorizzata, è vecchia programmazione.
				programmazione = Constants.PROGRAMMAZIONE_PRE_2016;			
			} else { programmazione = Constants.PROGRAMMAZIONE_2016; }
		
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
		LOG.info(prf + " programmazione = "+programmazione);
		LOG.info(prf + " END");
		return programmazione;
	}

	@Override
	@Transactional
	public TipoOperazioneDTO[] findTipoOperazione(Long idUtente, String idIride, Long idProgetto,
			String programmazione) throws UnrecoverableException, FormalParameterException {
		String prf = "[CronoProgrammaDAOImpl::findTipoOperazione]";
		LOG.info(prf + " BEGIN");
		
		String[] nameParameter = { "idUtente", "idIride", "idProgetto" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				idIride, idProgetto);

		TipoOperazioneDTO[] tipoOperazioneDTO = null;
		try {			
			
			if (Constants.PROGRAMMAZIONE_PRE_2016.equals(programmazione)) {
				// Valore di default: ID_TIPO_OPERAZIONE sulla pbandi_t_progetto
				PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(new BigDecimal(idProgetto));
				pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
				PbandiDTipoOperazioneVO tipoOperazioneOrdinato = new PbandiDTipoOperazioneVO();
				tipoOperazioneOrdinato.setAscendentOrder("descTipoOperazione");
				List<PbandiDTipoOperazioneVO> listSenzaDefault = genericDAO.findListWhere(tipoOperazioneOrdinato);
				List<PbandiDTipoOperazioneVO> listTipoOperazioniConDefault = new ArrayList<PbandiDTipoOperazioneVO>();
				for (PbandiDTipoOperazioneVO vo : listSenzaDefault) {
					if (vo.getIdTipoOperazione().longValue() == pbandiTProgettoVO
							.getIdTipoOperazione().longValue()) {
						listTipoOperazioniConDefault.add(0, vo);
					} else
						listTipoOperazioniConDefault.add(vo);
				}
				tipoOperazioneDTO = beanUtil.transform(listTipoOperazioniConDefault, TipoOperazioneDTO.class);
			}
			
			if (Constants.PROGRAMMAZIONE_2016.equals(programmazione)) {
				PbandiTBandoVO bando = getBandoByIdProgetto(idProgetto);
				List<PbandiDTipoOperazioneVO> lista = new ArrayList<PbandiDTipoOperazioneVO>();
				if (bando.getIdNaturaCipe() != null) {
					PbandiDNaturaCipeVO naturaCipe = new PbandiDNaturaCipeVO();
					naturaCipe.setIdNaturaCipe(bando.getIdNaturaCipe());
					naturaCipe = genericDAO.findSingleWhere(naturaCipe);
					if (naturaCipe != null) {
						PbandiDTipoOperazioneVO tipoOp = new PbandiDTipoOperazioneVO();
						tipoOp.setIdTipoOperazione(naturaCipe.getIdNaturaCipe());
						tipoOp.setDescTipoOperazione(naturaCipe.getDescNaturaCipe());
						lista.add(tipoOp);
					}
				}
				tipoOperazioneDTO = beanUtil.transform(lista, TipoOperazioneDTO.class);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} 
			
		LOG.info(prf + " END");
		return tipoOperazioneDTO;
	}
	
	// CDU-80-V04
	private PbandiTBandoVO getBandoByIdProgetto(Long idProgetto) {
		String prf = "[CronoProgrammaDAOImpl::getBandoByIdProgetto]";
		LOG.info(prf + " START");
		LOG.info(prf + " idProgetto = "+idProgetto);
		PbandiTProgettoVO progetto = new PbandiTProgettoVO();
		progetto.setIdProgetto(new BigDecimal(idProgetto));
		progetto = genericDAO.findSingleWhere(progetto);
		
		LOG.info(prf + " idDomanda = "+progetto.getIdDomanda());
		PbandiTDomandaVO domanda = new PbandiTDomandaVO();
		domanda.setIdDomanda(progetto.getIdDomanda());
		domanda = genericDAO.findSingleWhere(domanda);
		
		LOG.info(prf + " progrBandoLinea = "+domanda.getProgrBandoLineaIntervento());
		PbandiRBandoLineaInterventVO bandoLinea = new PbandiRBandoLineaInterventVO();
		bandoLinea.setProgrBandoLineaIntervento(domanda.getProgrBandoLineaIntervento());
		bandoLinea = genericDAO.findSingleWhere(bandoLinea);
		
		LOG.info(prf + " idBando = "+bandoLinea.getIdBando());
		PbandiTBandoVO bando = new PbandiTBandoVO();
		bando.setIdBando(bandoLinea.getIdBando());
		bando = genericDAO.findSingleWhere(bando);
		
		LOG.info(prf + " id bando trovato = "+bando.getIdBando());
		LOG.info(prf + " END");
		return bando;
	}

	

	@Override
	@Transactional
	public EsitoFindFasiMonitoraggio findFasiMonitoraggioGestione(Long idUtente, String idIride, Long idProgetto,
			String programmazione) throws FormalParameterException, UnrecoverableException {
		String prf = "[CronoProgrammaDAOImpl::findFasiMonitoraggioGestione]";
		LOG.info(prf + " BEGIN");
		
		EsitoFindFasiMonitoraggio esitoFindFasiMonitoraggio = new EsitoFindFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "idIride", "idProgetto" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride, idProgetto);

		try {
			// Ordinate in ordine alfabetico in base al campo COD_IGRUE_T35
			// dell’entità FASE_MONIT.
			
			// Cerco i dati in maniera diversa in base alla programmazione.
			//String programmazione = getProgrammazioneByIdProgetto(idUtente, identitaDigitale, idProgetto);
			List<FaseMonitoraggioProgettoVO> fasiProgetto = null;
			if (Constants.PROGRAMMAZIONE_PRE_2016.equals(programmazione)) {			
				LOG.info(prf + " PRE_2016");
				FaseMonitoraggioProgettoPre2016VO vo = new FaseMonitoraggioProgettoPre2016VO();
				vo.setIdProgetto(idProgetto);
				List<FaseMonitoraggioProgettoPre2016VO> fasi = genericDAO.findListWhere(vo);
				fasiProgetto = beanUtil.transformList(fasi, FaseMonitoraggioProgettoVO.class);
			} else {
				LOG.info(prf + " 2016");
				FaseMonitoraggioProgettoVO vo = new FaseMonitoraggioProgettoVO();
				vo.setIdProgetto(idProgetto);
				fasiProgetto = genericDAO.findListWhere(vo);
			}
			
			List<FaseMonitoraggioDTO> fasi = null;

			if (!isEmpty(fasiProgetto)) {

				FaseMonitoraggioProgettoVO faseProgettoVO = fasiProgetto.get(0);

				esitoFindFasiMonitoraggio.setDescIter(faseProgettoVO.getDescIter());

				fasi = getFasiMonitoraggio(faseProgettoVO.getIdIter());

				for (FaseMonitoraggioDTO faseMonitoraggio : fasi) {
					for (FaseMonitoraggioProgettoVO faseProgetto : fasiProgetto) {
						if (faseMonitoraggio.getIdFaseMonit().longValue() == faseProgetto
								.getIdFaseMonit().longValue()) {
							faseMonitoraggio.setDtInizioEffettiva(faseProgetto
									.getDtInizioEffettiva());
							faseMonitoraggio.setDtInizioPrevista(faseProgetto
									.getDtInizioPrevista());
							faseMonitoraggio.setDtFineEffettiva(faseProgetto
									.getDtFineEffettiva());
							faseMonitoraggio.setDtFinePrevista(faseProgetto
									.getDtFinePrevista());
							if (faseProgetto.getIdMotivoScostamento() != null) {
								faseMonitoraggio
										.setIdMotivoScostamento(faseProgetto
												.getIdMotivoScostamento()
												.longValue());
							}
							 
						}
					}
				}
			}

			java.util.Date dataConcessione = getDataConcessisone(idProgetto);
			esitoFindFasiMonitoraggio.setDtConcessione(dataConcessione);

			if (fasi != null && !fasi.isEmpty()) {
				esitoFindFasiMonitoraggio.setFasiMonitoraggio(fasi
						.toArray(new FaseMonitoraggioDTO[fasi.size()]));
				
				String codFaseObbligatoriaFinale=getFaseObbligatoriaFinale(fasi);
				esitoFindFasiMonitoraggio.setCodFaseObbligatoriaFinale(codFaseObbligatoriaFinale);
			}
			esitoFindFasiMonitoraggio.setSuccesso(true);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}  
		return esitoFindFasiMonitoraggio;
	}
	
	private List<FaseMonitoraggioDTO> getFasiMonitoraggio(Long idIter) {
		String prf = "[CronoProgrammaDAOImpl::getFasiMonitoraggio]";
		LOG.info(prf + " START");
		PbandiDFaseMonitVO pbandiDFaseMonitVO = new PbandiDFaseMonitVO();
		pbandiDFaseMonitVO.setIdIter(new BigDecimal(idIter));
		pbandiDFaseMonitVO.setAscendentOrder("codIgrueT35");
		Condition<PbandiDFaseMonitVO> conditionDataFineNull = new AndCondition<PbandiDFaseMonitVO>(
				new FilterCondition<PbandiDFaseMonitVO>(pbandiDFaseMonitVO),
				Condition.validOnly(PbandiDFaseMonitVO.class));
		List<PbandiDFaseMonitVO> fasiDB = genericDAO
				.findListWhere(conditionDataFineNull);

		List<FaseMonitoraggioDTO> fasi = new ArrayList<FaseMonitoraggioDTO>();
		for (PbandiDFaseMonitVO voDB : fasiDB) {
			FaseMonitoraggioDTO dto = beanUtil.transform(voDB,
					FaseMonitoraggioDTO.class);
			if (voDB.getFlagControlloIndicat() != null
					&& voDB.getFlagControlloIndicat().equalsIgnoreCase("S")) {
				dto.setControlloIndicatori(true);
			} else {
				dto.setControlloIndicatori(false);
			}
			if (voDB.getFlagObbligatorio() != null
					&& voDB.getFlagObbligatorio().equalsIgnoreCase("S")) {
				dto.setObbligatorio(true);
			} else {
				dto.setObbligatorio(false);
			}
			fasi.add(dto);
		}
		LOG.info(prf + " END");
		return fasi;
	}

	private String getFaseObbligatoriaFinale(List<FaseMonitoraggioDTO> fasi) {
		String prf = "[CronoProgrammaDAOImpl::getFaseObbligatoriaFinale]";
		LOG.info(prf + " START");
		Set <String> set=new TreeSet<String>();
		for (FaseMonitoraggioDTO faseMonitoraggioDTO : fasi) {
			if(faseMonitoraggioDTO.getObbligatorio())
				set.add(faseMonitoraggioDTO.getCodIgrueT35());
			}
		String[]codici=set.toArray(new String[]{});
		LOG.info(prf + " END");
		return codici[codici.length-1];
	}


	private java.util.Date getDataConcessisone(Long idProgetto) {
		String prf = "[CronoProgrammaDAOImpl::getDataConcessisone]";
		LOG.info(prf + " START");
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(
				new BigDecimal(idProgetto));
		pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
		java.util.Date dataConcessione = null;
		dataConcessione = pbandiTProgettoVO.getDtConcessione();
		if (dataConcessione == null)
			dataConcessione = pbandiTProgettoVO.getDtComitato();
		LOG.info(prf + " END");
		return dataConcessione;
	}
	

	@Override
	@Transactional
	public MotivoScostamentoDTO[] findMotivoScostamento(Long idUtente, String idIride) throws UnrecoverableException, FormalParameterException {
		String prf = "[CronoProgrammaDAOImpl::findMotivoScostamento]";
		LOG.info(prf + " BEGIN");
		
		String[] nameParameter = { "idUtente", "idIride" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride);

		List<MotivoScostamentoDTO> listConDefault = new ArrayList<MotivoScostamentoDTO>();
		try {
			PbandiDMotivoScostamentoVO pbandiDMotivoScostamentoVO = new PbandiDMotivoScostamentoVO();
			Condition<PbandiDMotivoScostamentoVO> conditionDataFineNull = new AndCondition<PbandiDMotivoScostamentoVO>(
					new FilterCondition<PbandiDMotivoScostamentoVO>(
							pbandiDMotivoScostamentoVO), Condition
							.validOnly(PbandiDMotivoScostamentoVO.class));
			List<PbandiDMotivoScostamentoVO> listSenzaDefault = genericDAO
					.findListWhere(conditionDataFineNull);
			// Valore di default: COD_IGRUE_T37_T49_T53 = 1 SULLA
			// pbandi_D_MOTIVO_SCOSTAMENTO
			for (PbandiDMotivoScostamentoVO vo : listSenzaDefault) {
				if (vo.getCodIgrueT37T49T53().intValue() == 1) {
					listConDefault.add(0, beanUtil.transform(vo,
							MotivoScostamentoDTO.class));
				} else {
					listConDefault.add(beanUtil.transform(vo,
							MotivoScostamentoDTO.class));
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} 
		
		return listConDefault.toArray(new MotivoScostamentoDTO[listConDefault.size()]);
	}
	

	@Override
	@Transactional
	public IterDTO[] findIter(Long idUtente, String idIride, Long idTipoOperazione, String programmazione) throws UnrecoverableException, FormalParameterException {
		String prf = "[CronoProgrammaDAOImpl::findIter]";
		LOG.info(prf + " BEGIN");	
		 
		String[] nameParameter = { "idUtente", "identitaDigitale","idTipoOperazione" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				idIride, idTipoOperazione);
		final String It03 = "It03";
		final String It05 = "It05";
		final String EROGAZIONE = "03";
		final String ACQUISIZIONE = "02";
		IterDTO[] iterDTO = null;
		try {
			
			if (Constants.PROGRAMMAZIONE_PRE_2016.equals(programmazione)) {
				PbandiDTipoOperazioneVO tipoOperazione = new PbandiDTipoOperazioneVO();
				tipoOperazione
						.setIdTipoOperazione(new BigDecimal(idTipoOperazione));
				tipoOperazione = genericDAO.findSingleWhere(tipoOperazione);
				PbandiDIterVO pbandiDIterVO = new PbandiDIterVO();
				pbandiDIterVO.setIdTipoOperazione(new BigDecimal(idTipoOperazione));
				Condition<PbandiDIterVO> conditionDataFineNull = new AndCondition<PbandiDIterVO>(
						new FilterCondition<PbandiDIterVO>(pbandiDIterVO),
						Condition.validOnly(PbandiDIterVO.class));
	
				List<PbandiDIterVO> listSenzaDefault = genericDAO
						.findListWhere(conditionDataFineNull);
	
				// se tipo operazione =desc_breve=03 "Erogazione ecc" -> l'iter di
				// default COD_IGRUE_T35 = It05
				// se tipo operazione =desc_breve=02 "Acquisizione ecc" -> l'iter di
				// default è COD_IGRUE_T35= It03
				List<PbandiDIterVO> listIterConDefault = new ArrayList<PbandiDIterVO>();
	
				String def = "";
				if (tipoOperazione.getDescBreveTipoOperazione().equalsIgnoreCase(
						EROGAZIONE)) {
					def = It05;
				} else if (tipoOperazione.getDescBreveTipoOperazione()
						.equalsIgnoreCase(ACQUISIZIONE)) {
					def = It03;
				}
				for (PbandiDIterVO vo : listSenzaDefault) {
					if (vo.getCodIgrueT35().equalsIgnoreCase(def)) {
						listIterConDefault.add(0, vo);
					} else {
						listIterConDefault.add(vo);
					}
				}
	
				iterDTO = beanUtil.transform(listIterConDefault, IterDTO.class);
			}
			
			if (Constants.PROGRAMMAZIONE_2016.equals(programmazione)) {
				// Nel caso di nuova programmazione, idTipoOperazione contiene in realtà
				// il campo PBANDI_T_BANDO.ID_NATURA_CIPE
				
				PbandiDIterVO iter = new PbandiDIterVO();
				iter.setIdNaturaCipe(new BigDecimal(idTipoOperazione));
				List<PbandiDIterVO> lista = genericDAO.findListWhere(iter);
				//for (PbandiDIterVO vo : lista) {}
				iterDTO = beanUtil.transform(lista, IterDTO.class);
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		LOG.info(prf + " END");
		return iterDTO;
	}



	@Override
	@Transactional
	public EsitoSaveFasiMonitoraggio salvaFasiMonitoraggioGestione(Long idUtente, String idIride, Long idProgetto, FaseMonitoraggioDTO[] fasiDto) throws UnrecoverableException, FormalParameterException {
		String prf = "[CronoProgrammaDAOImpl::salvaFasiMonitoraggioGestione]";
		LOG.info(prf + " BEGIN");
		EsitoSaveFasiMonitoraggio esitoSaveFasiMonitoraggio = new EsitoSaveFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "idIride", "idProgetto", "fasiMonitoraggio" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, fasiDto);

		try {

			boolean isAvvio = false;

			if (!indicatoriManager.checkIndicatori(idProgetto, fasiDto)) {
				return createEsitoMancaIndicatoreCore();
			}

			inserisciFasiMonitoraggio(idProgetto, fasiDto, isAvvio,
					new BigDecimal(idUtente));
			esitoSaveFasiMonitoraggio.setSuccesso(true);
			esitoSaveFasiMonitoraggio.setMessaggi(new String[] {MessageConstants.MSG_SALVA_SUCCESSO});
		} catch (Exception e) {
			LOG.error(prf + " " + e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		LOG.info(prf + " END");
		return esitoSaveFasiMonitoraggio;

	}

	
	private void inserisciFasiMonitoraggio(Long idProgetto, FaseMonitoraggioDTO[] fasiDto, boolean isAvvio,
			BigDecimal idUtente) throws Exception {
		String prf = "[CronoProgrammaDAOImpl::inserisciFasiMonitoraggio]";
		LOG.info(prf + " START");
		PbandiRProgettoFaseMonitVO pbandiRProgettoFaseMonitVO = new PbandiRProgettoFaseMonitVO();
		pbandiRProgettoFaseMonitVO.setIdProgetto(new BigDecimal(idProgetto));
		genericDAO.deleteWhere(new FilterCondition<PbandiRProgettoFaseMonitVO>(
				pbandiRProgettoFaseMonitVO));
		for (FaseMonitoraggioDTO faseMonitoraggioDTO : fasiDto) {

			pbandiRProgettoFaseMonitVO.setIdFaseMonit(new BigDecimal(
					faseMonitoraggioDTO.getIdFaseMonit()));

			Map<String, String> mapIndicatorePropsToCopy = new HashMap<String, String>();
			mapIndicatorePropsToCopy
					.put("dtInizioPrevista", "dtInizioPrevista");
			mapIndicatorePropsToCopy.put("dtFinePrevista", "dtFinePrevista");
			mapIndicatorePropsToCopy.put("dtInizioEffettiva",
					"dtInizioEffettiva");
			mapIndicatorePropsToCopy.put("dtFineEffettiva", "dtFineEffettiva");
			mapIndicatorePropsToCopy.put("idMotivoScostamento",
					"idMotivoScostamento");
			if (isAvvio) {
				pbandiRProgettoFaseMonitVO.setDtFineEffettiva(null);
				pbandiRProgettoFaseMonitVO.setDtFinePrevista(null);
			}
			beanUtil.valueCopy(faseMonitoraggioDTO, pbandiRProgettoFaseMonitVO,
					mapIndicatorePropsToCopy);

			java.sql.Date data = DateUtil.getSysdate();
			pbandiRProgettoFaseMonitVO.setDtInserimento(data);
			pbandiRProgettoFaseMonitVO.setDtAggiornamento(data);
			pbandiRProgettoFaseMonitVO.setIdUtenteIns(idUtente);			
			genericDAO.insert(pbandiRProgettoFaseMonitVO);
			
			LOG.info(prf + " END");

		}
	}

	private EsitoSaveFasiMonitoraggio createEsitoMancaIndicatoreCore() {
		String prf = "[CronoProgrammaDAOImpl::createEsitoMancaIndicatoreCore]";
		LOG.info(prf + " START");
		EsitoSaveFasiMonitoraggio esito = new EsitoSaveFasiMonitoraggio();
		esito.setSuccesso(false);
		List<String> messaggi = new ArrayList<String>();
		messaggi.add(ErrorMessages.KEY_MSG_MANCA_INDICATORE_CORE_CRONO);
		esito.setMessaggi(messaggi.toArray(new String[messaggi.size()]));
		LOG.info(prf + " END");
		return esito;
	}

	
	

	@Override
	@Transactional
	public Date findDataConcessione(Long idProgetto) {
		String prf = "[CronoProgrammaDAOImpl::findDataConcessione]";
		LOG.info(prf + " BEGIN");
		
		try {
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(
					new BigDecimal(idProgetto));
			pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
			Date dataConcessione = null;
			dataConcessione = pbandiTProgettoVO.getDtConcessione();
			if (dataConcessione == null)
				dataConcessione = pbandiTProgettoVO.getDtComitato();
			
			LOG.info(prf + " END");
			return dataConcessione;
		}  catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}  


	}
	@Override
	public Date findDataPresentazioneDomanda(Long idProgetto) {
		String prf = "[CronoProgrammaDAOImpl::findDataPresentazioneDomanda]";
		LOG.info(prf + " BEGIN");
		
		try {
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(
					new BigDecimal(idProgetto));
			pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
			Date dtPresentazioneDomanda = null;
			PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO(pbandiTProgettoVO.getIdDomanda());
			pbandiTDomandaVO = getGenericDAO().findSingleWhere(pbandiTDomandaVO);
			dtPresentazioneDomanda = pbandiTDomandaVO.getDtPresentazioneDomanda();
			
			LOG.info(prf + " END");
			return dtPresentazioneDomanda;
		}  catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}  
	}

	
	


	/****************************************************************** GESTIONE CRONOPROGRAMMA - AVVIO  ******************************************************************/


	@Override
	@Transactional
	public EsitoFindFasiMonitoraggio findFasiMonitoraggioAvvio(Long idUtente, String idIride, Long idProgetto,
			String programmazione, Long idIter) throws FormalParameterException, UnrecoverableException {
		String prf = "[CronoProgrammaDAOImpl::findFasiMonitoraggioAvvio]";
		LOG.info(prf + " BEGIN");
		EsitoFindFasiMonitoraggio esitoFindFasiMonitoraggio = new EsitoFindFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "idIride","idProgetto", "idIter" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, idIter);

		try {
			// Ordinate in ordine alfabetico in base al campo COD_IGRUE_T35
			// dell’entità FASE_MONIT.
			// select * from PBANDI_D_FASE_MONIT where id_iter=? order by
			// COD_IGRUE_T35 ;
			List<FaseMonitoraggioDTO> fasi = getFasiMonitoraggio(idIter);
			java.util.Date dataConcessione = getDataConcessisone(idProgetto);
			esitoFindFasiMonitoraggio.setDtConcessione(dataConcessione);

			esitoFindFasiMonitoraggio.setSuccesso(true);
			esitoFindFasiMonitoraggio.setFasiMonitoraggio(fasi
					.toArray(new FaseMonitoraggioDTO[fasi.size()]));

		} catch (Exception e) {
			LOG.info(prf + " END");
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		LOG.info(prf + " END");
		return esitoFindFasiMonitoraggio;
	}


	@Override
	@Transactional
	public EsitoSaveFasiMonitoraggio salvaFasiMonitoraggioAvvio(Long idUtente, String idIride, Long idProgetto, Long idTipoOperazione,
			FaseMonitoraggioDTO[] fasiDto) throws FormalParameterException, UnrecoverableException {
		String prf = "[CronoProgrammaDAOImpl::salvaFasiMonitoraggioAvvio]";
		LOG.info(prf + " BEGIN");
		EsitoSaveFasiMonitoraggio esitoSaveFasiMonitoraggio = new EsitoSaveFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "idIride", "idProgetto", "fasiMonitoraggio" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, fasiDto);

		try {

			boolean isAvvio = true;

			if (!indicatoriManager.checkIndicatori(idProgetto, fasiDto)) {
				return createEsitoMancaIndicatoreCore();
			}
			inserisciFasiMonitoraggio(idProgetto, fasiDto, isAvvio,new BigDecimal(idUtente));
			
			// CDU-80-V04: inizio
			// Nel caso di nuova programmazione, il parametro idTipoOperazione contiene l'id della 'natura cipe'.
			// In questo caso sostituisco l'idTipoOperazione ricevuto in input con
			// l'idTipoOperazione della natura cipe.
			String programmazione = this.findProgrammazioneByIdProgetto(idUtente, idIride, idProgetto);
			if (Constants.PROGRAMMAZIONE_2016.equals(programmazione)) {
				BigDecimal idNaturaCipe = new BigDecimal(idTipoOperazione);
				PbandiDNaturaCipeVO naturaCipe = new PbandiDNaturaCipeVO();
				naturaCipe.setIdNaturaCipe(idNaturaCipe);
				naturaCipe = genericDAO.findSingleWhere(naturaCipe);
				if (naturaCipe == null || naturaCipe.getIdTipoOperazione() == null) {
					LOG.error(prf + " idTipoOperazione della natura cipe "+idNaturaCipe.intValue()+" non trovata.");
					throw new Exception("Id operazione non trovato.");
				}				
				LOG.info(prf + " idTipoOperazione della natura cipe "+idNaturaCipe.intValue()+" = "+naturaCipe.getIdTipoOperazione());
				idTipoOperazione = naturaCipe.getIdTipoOperazione().longValue();
			}
			// CDU-80-V04: fine

			PbandiTProgettoVO pbandiTProgettoVO=new PbandiTProgettoVO();
			pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
			pbandiTProgettoVO=genericDAO.findSingleWhere(pbandiTProgettoVO);
			pbandiTProgettoVO.setIdTipoOperazione(new BigDecimal(idTipoOperazione));
			pbandiTProgettoVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(pbandiTProgettoVO);
			esitoSaveFasiMonitoraggio.setSuccesso(true);						
			esitoSaveFasiMonitoraggio.setMessaggi(new String[] {MessageConstants.MSG_SALVA_SUCCESSO});
			
			logger.warn("\n\n############################ NEOFLUX CRONOPROGRAMMA AVVIO ##############################\n");
			neofluxBusinessImpl.endAttivita(idUtente, idIride, idProgetto,Constants.CRONOPROG_AVVIO);
			logger.warn("############################ NEOFLUX CRONOPROGRAMMA AVVIO ###############################\n\n\n\n");
		
			LOG.info(prf + " END");
		} catch (Exception e) {
			LOG.error(prf + " " + e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		LOG.info(prf + " END");
		return esitoSaveFasiMonitoraggio;
	}


	protected boolean isEmpty(java.util.List<?> o){
		return ObjectUtil.isEmpty(o);
				
	}

}
