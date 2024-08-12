package it.csi.pbandi.pbweb.pbandisrv.business.gestionecronoprogramma;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.dichiarazionedispesa.DichiarazioneDiSpesaBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.IndicatoriManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.neoflux.Task;
//import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.LineaDiInterventoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.EsitoFindFasiMonitoraggio;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.EsitoSaveFasiMonitoraggio;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.IterDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.MotivoScostamentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.TipoOperazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionecronoprogramma.GestioneCronoprogrammaException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FaseMonitoraggioProgettoPre2016VO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.FaseMonitoraggioProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDFaseMonitVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIterVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMotivoScostamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNaturaCipeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoOperazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRProgettoFaseMonitVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTBandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionecronoprogramma.GestioneCronoprogrammaSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

public class GestioneCronoprogrammaBusinessImpl extends BusinessImpl implements
		GestioneCronoprogrammaSrv { 
	
	@Autowired
	private GenericDAO genericDAO;
	
	private DichiarazioneDiSpesaBusinessImpl dichiarazioneDiSpesaBusiness;	
	private IndicatoriManager indicatoriManager;
	private NeofluxSrv neofluxBusiness;
	private ProgettoManager progettoManager;
	private GestioneBackofficeBusinessImpl gestioneBackofficeBusiness;

	public GestioneBackofficeBusinessImpl getGestioneBackofficeBusiness() {
		return gestioneBackofficeBusiness;
	}

	public void setGestioneBackofficeBusiness(
			GestioneBackofficeBusinessImpl gestioneBackofficeBusiness) {
		this.gestioneBackofficeBusiness = gestioneBackofficeBusiness;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	public NeofluxSrv getNeofluxBusiness() {
		return neofluxBusiness;
	}

	public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
		this.neofluxBusiness = neofluxBusiness;
	}
	
 

	public void setDichiarazioneDiSpesaBusiness(
			DichiarazioneDiSpesaBusinessImpl dichiarazioneDiSpesaBusiness) {
		this.dichiarazioneDiSpesaBusiness = dichiarazioneDiSpesaBusiness;
	}

	public DichiarazioneDiSpesaBusinessImpl getDichiarazioneDiSpesaBusiness() {
		return dichiarazioneDiSpesaBusiness;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setIndicatoriManager(IndicatoriManager indicatoriManager) {
		this.indicatoriManager = indicatoriManager;
	}

	public IndicatoriManager getIndicatoriManager() {
		return indicatoriManager;
	}

	public EsitoFindFasiMonitoraggio findFasiMonitoraggioAvvio(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idIter)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneCronoprogrammaException {
		EsitoFindFasiMonitoraggio esitoFindFasiMonitoraggio = new EsitoFindFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto", "idIter" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, idIter);

		try {
			// Ordinate in ordine alfabetico in base al campo COD_IGRUE_T35
			// dell�entit� FASE_MONIT.
			// select * from PBANDI_D_FASE_MONIT where id_iter=? order by
			// COD_IGRUE_T35 ;
			List<FaseMonitoraggioDTO> fasi = getFasiMonitoraggio(idIter);
			java.util.Date dataConcessione = getDataConcessisone(idProgetto);
			esitoFindFasiMonitoraggio.setDtConcessione(dataConcessione);

			esitoFindFasiMonitoraggio.setSuccesso(true);
			esitoFindFasiMonitoraggio.setFasiMonitoraggio(fasi
					.toArray(new FaseMonitoraggioDTO[fasi.size()]));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esitoFindFasiMonitoraggio;
	}

	private List<FaseMonitoraggioDTO> getFasiMonitoraggio(Long idIter) {
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
		return fasi;
	}

	// CDU-80-V04: differenziati i casi in base alla programmazione.
	public EsitoFindFasiMonitoraggio findFasiMonitoraggioGestione(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneCronoprogrammaException {
		EsitoFindFasiMonitoraggio esitoFindFasiMonitoraggio = new EsitoFindFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idProgetto);

		try {
			// Ordinate in ordine alfabetico in base al campo COD_IGRUE_T35
			// dell�entit� FASE_MONIT.
			
			// Cerco i dati in maniera diversa in base alla programmazione.
			String programmazione = getProgrammazioneByIdProgetto(idUtente, identitaDigitale, idProgetto);
			List<FaseMonitoraggioProgettoVO> fasiProgetto = null;
			if (Constants.PROGRAMMAZIONE_PRE_2016.equals(programmazione)) {				
				FaseMonitoraggioProgettoPre2016VO vo = new FaseMonitoraggioProgettoPre2016VO();
				vo.setIdProgetto(idProgetto);
				List<FaseMonitoraggioProgettoPre2016VO> fasi = genericDAO.findListWhere(vo);
				fasiProgetto = beanUtil.transformList(fasi, FaseMonitoraggioProgettoVO.class);
			} else {
				FaseMonitoraggioProgettoVO vo = new FaseMonitoraggioProgettoVO();
				vo.setIdProgetto(idProgetto);
				fasiProgetto = genericDAO.findListWhere(vo);
			}
			
			List<FaseMonitoraggioDTO> fasi = null;

			if (!isEmpty(fasiProgetto)) {

				FaseMonitoraggioProgettoVO faseProgettoVO = fasiProgetto.get(0);

				esitoFindFasiMonitoraggio.setDescIter(faseProgettoVO
						.getDescIter());

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

			if (fasi != null) {
				esitoFindFasiMonitoraggio.setFasiMonitoraggio(fasi
						.toArray(new FaseMonitoraggioDTO[fasi.size()]));
				
				String codFaseObbligatoriaFinale=getFaseObbligatoriaFinale(fasi);
				esitoFindFasiMonitoraggio.setCodFaseObbligatoriaFinale(codFaseObbligatoriaFinale);
			}
			esitoFindFasiMonitoraggio.setSuccesso(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esitoFindFasiMonitoraggio;
	}

	private String getFaseObbligatoriaFinale(
			List<FaseMonitoraggioDTO> fasi) {
		Set <String> set=new TreeSet<String>();
		for (FaseMonitoraggioDTO faseMonitoraggioDTO : fasi) {
			if(faseMonitoraggioDTO.getObbligatorio())
				set.add(faseMonitoraggioDTO.getCodIgrueT35());
			}
		String[]codici=set.toArray(new String[]{});
		return codici[codici.length-1];
	}


	private java.util.Date getDataConcessisone(Long idProgetto) {
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(
				new BigDecimal(idProgetto));
		pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
		java.util.Date dataConcessione = null;
		dataConcessione = pbandiTProgettoVO.getDtConcessione();
		if (dataConcessione == null)
			dataConcessione = pbandiTProgettoVO.getDtComitato();
		return dataConcessione;
	}

	// CDU-80-V04: differenziati i casi in base alla programmazione.
	public TipoOperazioneDTO[] getTipoOperazione(Long idUtente,
			String identitaDigitale, Long idProgetto, String programmazione) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneCronoprogrammaException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);

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
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		} 
		return tipoOperazioneDTO;
	}

	// CDU-80-V04: differenziati i casi in base alla programmazione.
	public IterDTO[] getIter(Long idUtente, String identitaDigitale,Long idTipoOperazione, String programmazione) throws CSIException, SystemException,
			UnrecoverableException, GestioneCronoprogrammaException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale","idTipoOperazione" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idTipoOperazione);
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
				// default � COD_IGRUE_T35= It03
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
				// Nel caso di nuova programmazione, idTipoOperazione contiene in realt�
				// il campo PBANDI_T_BANDO.ID_NATURA_CIPE
				
				PbandiDIterVO iter = new PbandiDIterVO();
				iter.setIdNaturaCipe(new BigDecimal(idTipoOperazione));
				List<PbandiDIterVO> lista = genericDAO.findListWhere(iter);
				//for (PbandiDIterVO vo : lista) {}
				iterDTO = beanUtil.transform(lista, IterDTO.class);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return iterDTO;
	}

	public MotivoScostamentoDTO[] getMotivoScostamento(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneCronoprogrammaException {

		String[] nameParameter = { "idUtente", "identitaDigitale" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);

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
		return listConDefault.toArray(new MotivoScostamentoDTO[listConDefault
				.size()]);
	}
 
	// CDU-80-V04: differenziati i casi in base alla programmazione.
	public EsitoSaveFasiMonitoraggio saveFasiMonitoraggioAvvio(Long idUtente,
			String identitaDigitale, Long idProgetto,
			Long idTipoOperazione,
			FaseMonitoraggioDTO[] fasiMonitoraggio,
			IstanzaAttivitaDTO istanzaAttivita) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneCronoprogrammaException {
		EsitoSaveFasiMonitoraggio esitoSaveFasiMonitoraggio = new EsitoSaveFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "fasiMonitoraggio" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, fasiMonitoraggio);

		try {
			boolean isAvvio = true;
			inserisciFasiMonitoraggio(idProgetto, fasiMonitoraggio, isAvvio,
					new BigDecimal(idUtente));
			
			// CDU-80-V04: inizio
			// Nel caso di nuova programmazione, il parametro idTipoOperazione contiene l'id della 'natura cipe'.
			// In questo caso sostituisco l'idTipoOperazione ricevuto in input con
			// l'idTipoOperazione della natura cipe.
			String programmazione = this.getProgrammazioneByIdProgetto(idUtente, identitaDigitale, idProgetto);
			if (Constants.PROGRAMMAZIONE_2016.equals(programmazione)) {
				BigDecimal idNaturaCipe = new BigDecimal(idTipoOperazione);
				PbandiDNaturaCipeVO naturaCipe = new PbandiDNaturaCipeVO();
				naturaCipe.setIdNaturaCipe(idNaturaCipe);
				naturaCipe = genericDAO.findSingleWhere(naturaCipe);
				if (naturaCipe == null || naturaCipe.getIdTipoOperazione() == null) {
					logger.error("saveFasiMonitoraggioAvvio(): idTipoOperazione della natura cipe "+idNaturaCipe.intValue()+" non trovata.");
					throw new Exception("Id operazione non trovato.");
				}				
				logger.info("saveFasiMonitoraggioAvvio(): idTipoOperazione della natura cipe "+idNaturaCipe.intValue()+" = "+naturaCipe.getIdTipoOperazione());
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
			
			/*logger.warn("\n\n\n\n################# OLD or NEO FLUX ? searching process id by idProgetto "+idProgetto);
			Long processo = neofluxBusiness.getProcesso(idUtente, identitaDigitale, idProgetto);
			logger.warn("processo: "+processo);
			if(processo!=null){*/
				logger.warn("\n\n############################ NEOFLUX CRONOPROGRAMMA AVVIO ##############################\n");
				neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto,Task.CRONOPROG_AVVIO);
				logger.warn("############################ NEOFLUX CRONOPROGRAMMA AVVIO ###############################\n\n\n\n");
		/*	}else{
				logger.warn("\n\n############################ OLDFLUX saveFasiMonitoraggioAvvio##############################\ncalling getProcessManager().saveFasiMonitoraggioAvvio idProgetto :"+idProgetto);
				getProcessManager().saveFasiMonitoraggioAvvio(idUtente,identitaDigitale);
				logger.warn("saveFasiMonitoraggioAvvio OK\n############################ OLDFLUX ##############################\n\n\n\n");
			}*/
						
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  

		return esitoSaveFasiMonitoraggio;
	}

	private void inserisciFasiMonitoraggio(Long idProgetto,
			FaseMonitoraggioDTO[] fasiMonitoraggio, boolean isAvvio,
			BigDecimal idUtente) throws Exception {

		PbandiRProgettoFaseMonitVO pbandiRProgettoFaseMonitVO = new PbandiRProgettoFaseMonitVO();
		pbandiRProgettoFaseMonitVO.setIdProgetto(new BigDecimal(idProgetto));
		genericDAO.deleteWhere(new FilterCondition<PbandiRProgettoFaseMonitVO>(
				pbandiRProgettoFaseMonitVO));
		for (FaseMonitoraggioDTO faseMonitoraggioDTO : fasiMonitoraggio) {

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

		}
	}

	public EsitoSaveFasiMonitoraggio saveFasiMonitoraggioGestione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			FaseMonitoraggioDTO[] fasiMonitoraggio) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneCronoprogrammaException {
		EsitoSaveFasiMonitoraggio esitoSaveFasiMonitoraggio = new EsitoSaveFasiMonitoraggio();

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "fasiMonitoraggio" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, fasiMonitoraggio);

		try {
			// Se NON c'� una dichiarazione di spesa finale il sistema:
			// Se si inseriscono le date di inizio e fine effettive per una fase
			// con
			// FLAG_CONTROLLO_INDICAT=S, il sistema verifica che esista almeno
			// un
			// indicatore CORE (ID_TIPO_INDICATORE = 2) sulla
			// PBANDI_R_DOMANDA_INDICATORI con il valore finale valorizzato
			// (VALORE_CONCLUSO non null).

			// PBANDI-1590: controllo indicatore CORE anche in salvataggio
			// if (dichiarazioneDiSpesaBusiness.hasDichiarazioneFinale(idUtente,
			// identitaDigitale, idProgetto)) {

			boolean isAvvio = false;

			if (!indicatoriManager.checkIndicatori(idProgetto, fasiMonitoraggio)) {
				return createEsitoMancaIndicatoreCore();
			}
			// }

			inserisciFasiMonitoraggio(idProgetto, fasiMonitoraggio, isAvvio,
					new BigDecimal(idUtente));
			esitoSaveFasiMonitoraggio.setSuccesso(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  

		return esitoSaveFasiMonitoraggio;
	}

	public EsitoSaveFasiMonitoraggio chiudiFasiMonitoraggioGestione(
			Long idUtente, String identitaDigitale, Long idProgetto,
			FaseMonitoraggioDTO[] fasiMonitoraggio,
			IstanzaAttivitaDTO istanzaAttivita) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneCronoprogrammaException {

		EsitoSaveFasiMonitoraggio esitoSaveFasiMonitoraggio = new EsitoSaveFasiMonitoraggio();
		 

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "fasiMonitoraggio" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto, fasiMonitoraggio);

		try {
			// Se NON c'� una dichiarazione di spesa finale il sistema:
			// Se si inseriscono le date di inizio e fine effettive per una fase
			// con
			// FLAG_CONTROLLO_INDICAT=S, il sistema verifica che esista almeno
			// un
			// indicatore CORE (ID_TIPO_INDICATORE = 2) sulla
			// PBANDI_R_DOMANDA_INDICATORI con il valore finale valorizzato
			// (VALORE_CONCLUSO non null).
			/*
			 * if (dichiarazioneDiSpesaBusiness.hasDichiarazioneFinale(idUtente,
			 * identitaDigitale, idProgetto)) {
			 * 
			 * 
			 * }
			 */
			boolean isAvvio = false;

			if (!indicatoriManager.checkIndicatori(idProgetto, fasiMonitoraggio)) {
				return createEsitoMancaIndicatoreCore();
			}

			inserisciFasiMonitoraggio(idProgetto, fasiMonitoraggio, isAvvio,
					new BigDecimal(idUtente));
			
			
			logger.warn("\n\n############################ NEOFLUX CRONOPROGRAMMA endattivita ##############################\n");
			neofluxBusiness.endAttivita(idUtente, identitaDigitale, idProgetto,Task.CRONOPROGRAMMA);
			logger.warn("############################ NEOFLUX CRONOPROGRAMMA endattivita ##############################\n\n\n\n");
	 
			esitoSaveFasiMonitoraggio.setSuccesso(true);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}  
		return esitoSaveFasiMonitoraggio;
	}

	private EsitoSaveFasiMonitoraggio createEsitoMancaIndicatoreCore() {
		EsitoSaveFasiMonitoraggio esito = new EsitoSaveFasiMonitoraggio();
		esito.setSuccesso(false);
		List<String> messaggi = new ArrayList<String>();
		messaggi.add(ErrorConstants.KEY_MSG_MANCA_INDICATORE_CORE_CRONO);
		esito.setMessaggi(messaggi.toArray(new String[messaggi.size()]));
		return esito;
	}

	public static void main(String[] args) {
		Set <String>set=new TreeSet<String>();
		set.add("A00");
		set.add("A02");
		set.add("A07");
		set.add("A13");
		set.add("A05");
		set.add("A04");
		set.add("A16");
		for (String object : set) {
			System.out.println(object);	
		}
		String[]codici=set.toArray(new String[]{});
		System.out.println(codici[codici.length-1]);	
	}
	
	// CDU-80-V04
	public String getProgrammazioneByIdProgetto(Long idUtente, String identitaDigitale, Long idProgetto) 
		throws CSIException, SystemException, UnrecoverableException, GestioneCronoprogrammaException {
 
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgetto);
		
		String programmazione = "";
		try {
						
			logger.info("getProgrammazioneByIdProgetto(): idProgetto = "+idProgetto);
			PbandiTProgettoVO progetto = new PbandiTProgettoVO();
			progetto.setIdProgetto(new BigDecimal(idProgetto));
			progetto = genericDAO.findSingleWhere(progetto);
			
			logger.info("getProgrammazioneByIdProgetto(): idDomanda = "+progetto.getIdDomanda());
			PbandiTDomandaVO domanda = new PbandiTDomandaVO();
			domanda.setIdDomanda(progetto.getIdDomanda());
			domanda = genericDAO.findSingleWhere(domanda);
			
			logger.info("getProgrammazioneByIdProgetto(): progrBandoLinea = "+domanda.getProgrBandoLineaIntervento());
			PbandiRBandoLineaInterventVO bandoLinea = new PbandiRBandoLineaInterventVO();
			bandoLinea.setProgrBandoLineaIntervento(domanda.getProgrBandoLineaIntervento());
			bandoLinea = genericDAO.findSingleWhere(bandoLinea);
			
			logger.info("getProgrammazioneByIdProgetto(): idBando = "+bandoLinea.getIdBando());
			PbandiTBandoVO bando = new PbandiTBandoVO();
			bando.setIdBando(bandoLinea.getIdBando());
			bando = genericDAO.findSingleWhere(bando);
			
			logger.info("getProgrammazioneByIdProgetto(): idLineaInterventoBando = "+bando.getIdLineaDiIntervento());						
			if (bando.getIdLineaDiIntervento() == null) {
				
				// Se il bando non ha la normativa valorizzata, � vecchia programmazione.
				programmazione = Constants.PROGRAMMAZIONE_PRE_2016;
				
			} else {
				
				programmazione = Constants.PROGRAMMAZIONE_2016;
			
				/*
				// Leggo le normative relative alla vecchia programmazione.
				LineaDiInterventoDTO[] linee = null;
				try {
					linee = gestioneBackofficeBusiness.findLineeDiIntervento(idUtente, identitaDigitale,
							 null, Constants.PROGRAMMAZIONE_PRE_2016);
				} catch (Exception e) {
					logger.error("getProgrammazioneByIdProgetto(): errore nel richiamo di gestioneBackofficeBusinessImpl.findLineeDiIntervento():\n"+e);
				}
				
				// Verifica se la normativa del bando � una di quelle vecchia programmazione.
				programmazione = Constants.PROGRAMMAZIONE_2016;
				Long idLineaIntervBando = bando.getIdLineaDiIntervento().longValue();
				for (LineaDiInterventoDTO linea : linee) {
					if (idLineaIntervBando.intValue() == linea.getIdLinea().intValue()) {
						logger.info("getProgrammazioneByIdProgetto(): programmazione pre 2016.");
						programmazione = Constants.PROGRAMMAZIONE_PRE_2016;
						break;
					}
				}
				*/
								
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		
		logger.info("getProgrammazioneByIdProgetto(): programmazione = "+programmazione);
		return programmazione;
		
		}
	
	// CDU-80-V04
	private String getProgrammazioneByBando(Long idUtente, String identitaDigitale, PbandiTBandoVO bando) {
				
		logger.info("getProgrammazioneByBando(): idBando = "+bando.getIdBando()+"; idLineaInterventoBando = "+bando.getIdLineaDiIntervento());
		
		// Se il bando non ha la normativa valorizzata, � vecchia programmazione.
		if (bando.getIdLineaDiIntervento() == null) {
			logger.info("programmazioneProgetto(): idLineaInterventoBando nulla: programmazione pre 2016.");
			return Constants.PROGRAMMAZIONE_PRE_2016;
		}
		
		/*
		// Leggo le normative relative alla vecchia programmazione.
		LineaDiInterventoDTO[] linee = null;
		try {
			linee = gestioneBackofficeBusiness.findLineeDiIntervento(idUtente, identitaDigitale,
					 null, Constants.PROGRAMMAZIONE_PRE_2016);
		} catch (Exception e) {
			logger.error("programmazioneProgetto(): errore nel richiamo di gestioneBackofficeBusinessImpl.findLineeDiIntervento():\n"+e);
		}
		
		// Verifica se la normativa del bando � una di quelle vecchia programmazione.
		Long idLineaIntervBando = bando.getIdLineaDiIntervento().longValue();
		for (LineaDiInterventoDTO linea : linee) {
			if (idLineaIntervBando.intValue() == linea.getIdLinea().intValue()) {
				logger.info("programmazioneProgetto(): programmazione pre 2016.");
				return Constants.PROGRAMMAZIONE_PRE_2016;
			}
		}
		*/
		logger.info("programmazioneProgetto(): programmazione 2016.");
		return Constants.PROGRAMMAZIONE_2016;
	}
	
	// CDU-80-V04
	private PbandiTBandoVO getBandoByIdProgetto(Long idProgetto) {
		
		logger.info("getBandoByIdProgetto(): idProgetto = "+idProgetto);
		PbandiTProgettoVO progetto = new PbandiTProgettoVO();
		progetto.setIdProgetto(new BigDecimal(idProgetto));
		progetto = genericDAO.findSingleWhere(progetto);
		
		logger.info("getBandoByIdProgetto(): idDomanda = "+progetto.getIdDomanda());
		PbandiTDomandaVO domanda = new PbandiTDomandaVO();
		domanda.setIdDomanda(progetto.getIdDomanda());
		domanda = genericDAO.findSingleWhere(domanda);
		
		logger.info("getBandoByIdProgetto(): progrBandoLinea = "+domanda.getProgrBandoLineaIntervento());
		PbandiRBandoLineaInterventVO bandoLinea = new PbandiRBandoLineaInterventVO();
		bandoLinea.setProgrBandoLineaIntervento(domanda.getProgrBandoLineaIntervento());
		bandoLinea = genericDAO.findSingleWhere(bandoLinea);
		
		logger.info("getBandoByIdProgetto(): idBando = "+bandoLinea.getIdBando());
		PbandiTBandoVO bando = new PbandiTBandoVO();
		bando.setIdBando(bandoLinea.getIdBando());
		bando = genericDAO.findSingleWhere(bando);
		
		logger.info("getBandoByIdProgetto(): id bando trovato = "+bando.getIdBando());
		
		return bando;
	}
	
}
