/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.IndicatoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.TipoIndicatoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IndicatoreDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IndicatoreSifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IndicatoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaAssociataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDFaseMonitVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoIndicatoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;

public class IndicatoriManager {
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private RegolaManager regolaManager;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private ProgettoManager progettoManager;

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public RegolaManager getRegolaManager() {
		return regolaManager;
	}

	public void setRegolaManager(RegolaManager regolaManager) {
		this.regolaManager = regolaManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
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

	public List<VoceDiSpesaAssociataVO> findVociDiSpesaAssociate(VoceDiSpesaAssociataVO filtro) {
		List<VoceDiSpesaAssociataVO> voci = getGenericDAO().findListWhere(filtro);
		return voci;
	}

	public TipoIndicatoreDTO[] findIndicatoriRimappatiAvvio(Long idProgetto, boolean monitoraggio) {
		// ) Il sistema presenta gli indicatori disponibili raggruppati per
		// tipo
		// se monitoraggio flag_monit=S
		boolean isAvvio = true;
		Map<String, String> mapPropsTipoIndicatoreToCopy = new HashMap<String, String>();
		mapPropsTipoIndicatoreToCopy.put("desc_tipo_indicatore", "descTipoIndicatore");
		mapPropsTipoIndicatoreToCopy.put("id_tipo_indicatore", "idTipoIndicatore");
		Map<String, String> mapPropsIndicatoreToCopy = new HashMap<String, String>();
		mapPropsIndicatoreToCopy.put("desc_indicatore", "descIndicatore");
		mapPropsIndicatoreToCopy.put("id_indicatori", "idIndicatore");
		mapPropsIndicatoreToCopy.put("flag_obbligatorio", "flagObbligatorio");
		mapPropsIndicatoreToCopy.put("cod_igrue", "codIgrue");
		mapPropsIndicatoreToCopy.put("desc_unita_misura", "descUnitaMisura");
		mapPropsIndicatoreToCopy.put("flag_non_applicabile", "flagNonApplicabile");
		return findIndicatoriRimappati(idProgetto, monitoraggio, mapPropsTipoIndicatoreToCopy, mapPropsIndicatoreToCopy,
				isAvvio);
	}

	public TipoIndicatoreDTO[] findIndicatoriPerFineAttivita(Long idProgetto) {
		return findIndicatoriRimappatiGestione(idProgetto, null);
	}

	public TipoIndicatoreDTO[] findIndicatoriRimappatiGestione(Long idProgetto, Boolean monitoraggio) {
		// ) Il sistema presenta gli indicatori disponibili raggruppati per
		// tipo
		// se monitoraggio flag_monit=S
		// ) Il sistema presenta gli indicatori disponibili raggruppati per
		// tipo
		// se monitoraggio flag_monit=S
		Map<String, String> mapPropsTipoIndicatoreToCopy = new HashMap<String, String>();
		mapPropsTipoIndicatoreToCopy.put("desc_tipo_indicatore", "descTipoIndicatore");
		mapPropsTipoIndicatoreToCopy.put("flag_monit", "flagMonit");
		mapPropsTipoIndicatoreToCopy.put("id_tipo_indicatore", "idTipoIndicatore");
		Map<String, String> mapPropsIndicatoreToCopy = new HashMap<String, String>();
		mapPropsIndicatoreToCopy.put("desc_indicatore", "descIndicatore");
		mapPropsIndicatoreToCopy.put("id_indicatori", "idIndicatore");
		mapPropsIndicatoreToCopy.put("flag_obbligatorio", "flagObbligatorio");
		mapPropsIndicatoreToCopy.put("cod_igrue", "codIgrue");
		mapPropsIndicatoreToCopy.put("desc_unita_misura", "descUnitaMisura");
		mapPropsIndicatoreToCopy.put("valore_prog_iniziale", "valoreIniziale");
		mapPropsIndicatoreToCopy.put("valore_prog_agg", "valoreAggiornamento");
		mapPropsIndicatoreToCopy.put("valore_concluso", "valoreFinale");
		boolean isAvvio = false;
		return findIndicatoriRimappati(idProgetto, monitoraggio, mapPropsTipoIndicatoreToCopy, mapPropsIndicatoreToCopy,
				isAvvio);

	}

	// spostato da GestioneCronoprogrammaBusinessImpl
	// 1) per ogni idfasemonitoraggio che sto salvando controllo :
	// a) se � presente data fine effettiva
	// b) controllo se su PBANDI_D_FASE_MONIT per uuell'idFase il
	// FLAG_CONTROLLO_INDICAT='S'
	//
	// 2) con iddomanda vado su rPBANDI_R_DOMANDA_INDICATORI e controllo che
	// esista almeno un
	// indicatore con idTipo=2 CORE con valore_concluso non null
	public boolean checkIndicatori(Long idProgetto, FaseMonitoraggioDTO[] fasiMonitoraggio)
			throws FormalParameterException {

		if (!regolaManager.isRegolaApplicabileForProgetto(idProgetto,
				RegoleConstants.BR35_CONTROLLO_ATTIVITA_INDICATORI_VALIDA)) {
			return true;
		}

		PbandiTDomandaVO pbandiTDomandaVO = progettoManager.getDomandaByProgetto(idProgetto);

		// Esiste un indicatore CORE?
		IndicatoreDomandaVO indicatoreDomandaVO = new IndicatoreDomandaVO();
		indicatoreDomandaVO.setId_domanda(pbandiTDomandaVO.getIdDomanda());
		BigDecimal idTipoIndicatore = decodificheManager.decodeDescBreve(PbandiDTipoIndicatoreVO.class,
				Constants.DESC_BREVE_TIPO_INDICATORE_CORE);
		indicatoreDomandaVO.setId_tipo_indicatore(idTipoIndicatore);
		List<IndicatoreDomandaVO> indicatoriDomandaCore = genericDAO.findListWhere(indicatoreDomandaVO);

		if (!ObjectUtil.isEmpty(indicatoriDomandaCore)) {

			boolean isIndicatoreCore = false;
			for (IndicatoreDomandaVO pbandiRDomandaIndicatoriVO : indicatoriDomandaCore) {
				if (pbandiRDomandaIndicatoriVO.getValore_concluso() != null) {
					isIndicatoreCore = true;
				}
			}

			// Se si inserisce la data di fine effettiva per una fase con
			// FLAG_CONTROLLO_INDICAT=S,
			// il sistema verifica che esista almeno un indicatore CORE
			// (ID_TIPO_INDICATORE = 2)
			// sulla PBANDI_R_DOMANDA_INDICATORI con il valore finale
			// valorizzato (VALORE_CONCLUSO non null).
			if (!isIndicatoreCore) {
				for (FaseMonitoraggioDTO faseMonitoraggioDTO : fasiMonitoraggio) {
					if (faseMonitoraggioDTO.getDtFineEffettiva() != null) {
						// � valorizzata una data fine effettiva
						// verifichiamo se la fa se ha FLAG_CONTROLLO_INDICAT=S
						PbandiDFaseMonitVO pbandiDFaseMonitVO = new PbandiDFaseMonitVO();
						pbandiDFaseMonitVO.setIdFaseMonit(new BigDecimal(faseMonitoraggioDTO.getIdFaseMonit()));
						pbandiDFaseMonitVO = genericDAO.findSingleWhere(pbandiDFaseMonitVO);
						if (pbandiDFaseMonitVO.getFlagControlloIndicat() != null
								&& pbandiDFaseMonitVO.getFlagControlloIndicat().equalsIgnoreCase("S")) {
							return false;

						}
					}
				}
			}
		}

		return true;
	}

	private TipoIndicatoreDTO[] findIndicatoriRimappati(Long idProgetto, Boolean monitoraggio,
			Map<String, String> mapPropsTipoIndicatoreToCopy, Map<String, String> mapPropsIndicatoreToCopy,
			boolean isAvvio) {
		TipoIndicatoreDTO[] tipiIndicatore;

		PbandiTDomandaVO pbandiTDomandaVO = getProgettoManager().getDomandaByProgetto(idProgetto);

		BandoLineaVO bandoLineaVO = findBandoLinea(pbandiTDomandaVO);

		List<IndicatoreVO> indicatori = findIndicatori(monitoraggio, bandoLineaVO);

		Map<BigDecimal, TipoIndicatoreDTO> mapTipoIndicatori = new LinkedHashMap<BigDecimal, TipoIndicatoreDTO>();

		rimappaIndicatori(mapPropsTipoIndicatoreToCopy, mapPropsIndicatoreToCopy, indicatori, mapTipoIndicatori);

		logger.warn("trovati " + mapTipoIndicatori.size() + " indicatori");

		if (!isAvvio) {
			mapPropsIndicatoreToCopy.put("flag_non_applicabile", "flagNonApplicabile");
			rimappaIndicatoriDomanda(monitoraggio, pbandiTDomandaVO, mapTipoIndicatori, mapPropsIndicatoreToCopy);
		}

		Collection<TipoIndicatoreDTO> indicatoriTrovati = mapTipoIndicatori.values();
		tipiIndicatore = indicatoriTrovati.toArray(new TipoIndicatoreDTO[indicatoriTrovati.size()]);

		// Jira PBANDI-2654: aggiungo ad ogni indicatore l'id bando.
		for (TipoIndicatoreDTO tipo : tipiIndicatore) {
			for (IndicatoreDTO i : tipo.getIndicatori()) {
				i.setIdBando(bandoLineaVO.getIdBando().longValue());
				// logger.info("ZZZZZZZ"+i.getIdBando()+" - "+i.getIdIndicatore());
			}
		}

		return tipiIndicatore;
	}

	private void rimappaIndicatori(Map<String, String> mapPropsTipoIndicatoreToCopy,
			Map<String, String> mapPropsIndicatoreToCopy, List<IndicatoreVO> indicatori,
			Map<BigDecimal, TipoIndicatoreDTO> mapTipoIndicatori) {
		for (IndicatoreVO indicatoreVO : indicatori) {
			TipoIndicatoreDTO tipoIndicatoreDTO = new TipoIndicatoreDTO();
			BigDecimal idTipoIndicatore = indicatoreVO.getId_tipo_indicatore();
			List<IndicatoreDTO> list = null;
			IndicatoreDTO indicatoreDTO = new IndicatoreDTO();
			if (mapTipoIndicatori.containsKey(idTipoIndicatore)) {
				tipoIndicatoreDTO = mapTipoIndicatori.get(indicatoreVO.getId_tipo_indicatore());
				IndicatoreDTO[] indicatoriDTO = tipoIndicatoreDTO.getIndicatori();
				list = new ArrayList(Arrays.asList(indicatoriDTO));
			} else {
				getBeanUtil().valueCopy(indicatoreVO, tipoIndicatoreDTO, mapPropsTipoIndicatoreToCopy);
				list = new ArrayList<IndicatoreDTO>();
			}

			getBeanUtil().valueCopy(indicatoreVO, indicatoreDTO, mapPropsIndicatoreToCopy);
			indicatoreDTO.setFlagNonApplicabile(Boolean.FALSE);
			list.add(indicatoreDTO);
			tipoIndicatoreDTO.setIndicatori(list.toArray(new IndicatoreDTO[list.size()]));
			mapTipoIndicatori.put(idTipoIndicatore, tipoIndicatoreDTO);
		}
	}

	private BandoLineaVO findBandoLinea(PbandiTDomandaVO pbandiTDomandaVO) {
		BandoLineaVO bandoLineaVO = new BandoLineaVO();
		bandoLineaVO.setProgrBandoLineaIntervento(pbandiTDomandaVO.getProgrBandoLineaIntervento());
		bandoLineaVO = getGenericDAO().findSingleWhere(bandoLineaVO);
		return bandoLineaVO;
	}

	private void rimappaIndicatoriDomanda(Boolean monitoraggio, PbandiTDomandaVO pbandiTDomandaVO,
			Map<BigDecimal, TipoIndicatoreDTO> mapTipoIndicatori, Map<String, String> mapIndicatorePropsToCopy) {

		List<IndicatoreDomandaVO> indicatoriDomanda = findIndicatoriDomanda(monitoraggio, pbandiTDomandaVO);
		mapIndicatorePropsToCopy.put("valore_prog_iniziale", "valoreIniziale");
		mapIndicatorePropsToCopy.put("valore_prog_agg", "valoreAggiornamento");
		mapIndicatorePropsToCopy.put("valore_concluso", "valoreFinale");
		for (IndicatoreDomandaVO indicatoreDomandaVO : indicatoriDomanda) {

			TipoIndicatoreDTO tipoIndicatore = mapTipoIndicatori.get(indicatoreDomandaVO.getId_tipo_indicatore());
			IndicatoreDTO indicatori[] = tipoIndicatore.getIndicatori();
			for (IndicatoreDTO indicatoreDTO : indicatori) {
				if (indicatoreDTO.getIdIndicatore() == indicatoreDomandaVO.getId_indicatori().longValue()) {
					beanUtil.valueCopy(indicatoreDomandaVO, indicatoreDTO, mapIndicatorePropsToCopy);
					if (indicatoreDomandaVO.getFlag_monit() != null
							&& indicatoreDomandaVO.getFlag_monit().equalsIgnoreCase("S")) {
						tipoIndicatore.setFlagMonit(true);
					} else {
						tipoIndicatore.setFlagMonit(false);
					}
					if (indicatoreDomandaVO.getFlag_non_applicabile() != null
							&& indicatoreDomandaVO.getFlag_non_applicabile().equalsIgnoreCase("S")) {
						indicatoreDTO.setFlagNonApplicabile(true);
					} else {
						indicatoreDTO.setFlagNonApplicabile(false);
					}
					break;
				}
			}

		}

	}

	private List<IndicatoreDomandaVO> findIndicatoriDomanda(Boolean monitoraggio, PbandiTDomandaVO pbandiTDomandaVO) {
		IndicatoreDomandaVO indicatoreDomandaVO = new IndicatoreDomandaVO();
		indicatoreDomandaVO.setId_domanda(pbandiTDomandaVO.getIdDomanda());
		if (monitoraggio != null) {
			if (monitoraggio)
				indicatoreDomandaVO.setFlag_monit("S");
			else
				indicatoreDomandaVO.setFlag_monit("N");
		}
		List<IndicatoreDomandaVO> indicatoriDomanda = genericDAO.findListWhere(indicatoreDomandaVO);

		return indicatoriDomanda;
	}

	private List<IndicatoreVO> findIndicatori(Boolean monitoraggio, BandoLineaVO bandoLineaVO) {
		String prf = "IndicatoriManager::findIndicatori";
		Log.info(prf + " BEGIN");
		IndicatoreVO indicatoreVO = new IndicatoreVO();
		indicatoreVO.setId_bando(bandoLineaVO.getIdBando());
		if (monitoraggio != null) {
			if (monitoraggio)
				indicatoreVO.setFlag_monit("S");
			else
				indicatoreVO.setFlag_monit("N");
		}
		List<IndicatoreVO> indicatori = genericDAO.findListWhere(indicatoreVO);
		Log.info(prf + " END");
		return indicatori;
	}

	public List<IndicatoreSifVO> findIndicatoriSif(Boolean monitoraggio, Long idProgetto) {
		String prf = "IndicatoriManager::findIndicatoriSif";
		Log.info(prf + " BEGIN");
		IndicatoreSifVO indicatoreSifVO = new IndicatoreSifVO();
		indicatoreSifVO.setId_progetto(new BigDecimal(idProgetto));
		if (monitoraggio != null) {
			if (monitoraggio)
				indicatoreSifVO.setFlag_monit("S");
			else
				indicatoreSifVO.setFlag_monit("N");
		}
		List<IndicatoreSifVO> indicatori = genericDAO.findListWhere(indicatoreSifVO);
		Log.info(prf + " END");
		return indicatori;
	}

	// Restituisce TRUE se per l'id progetto in input esiste un record in
	// PBANDI_T_DICHIARAZIONE_SPESA
	// avente ID_TIPO_DICHIARAZ_SPESA > 2 (come indicato nella jira PBANDI-2814).
	public Boolean esisteCFP(Long idProgetto) {
		PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO();
		vo.setIdProgetto(new BigDecimal(idProgetto));
		List<PbandiTDichiarazioneSpesaVO> listaDs = genericDAO.findListWhere(vo);
		for (PbandiTDichiarazioneSpesaVO ds : listaDs) {
			BigDecimal idTipoDs = ds.getIdTipoDichiarazSpesa();
			if (idTipoDs != null && idTipoDs.intValue() > 2)
				return new Boolean(true);
		}
		return new Boolean(false);
	}

	// Restituisce TRUE se per l'id progetto in input esiste un record in
	// PBANDI_T_DICHIARAZIONE_SPESA
	// avente ID_TIPO_DICHIARAZ_SPESA = 2.
	public Boolean esisteDsFinale(Long idProgetto) {
		PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO();
		vo.setIdProgetto(new BigDecimal(idProgetto));
		List<PbandiTDichiarazioneSpesaVO> listaDs = genericDAO.findListWhere(vo);
		for (PbandiTDichiarazioneSpesaVO ds : listaDs) {
			BigDecimal idTipoDs = ds.getIdTipoDichiarazSpesa();
			if (idTipoDs != null && idTipoDs.intValue() == 2)
				return new Boolean(true);
		}
		return new Boolean(false);
	}

}
