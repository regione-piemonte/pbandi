/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbworkspace.dto.CodiceDescrizione;
import it.csi.pbandi.pbworkspace.dto.EsitoDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaComboDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaSchedaProgettoDTO;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.EsitoSchedaProgetto;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.IdDescBreveDescEstesa;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgettoInfoBase;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SedeIntervento;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoGenerico;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.integration.dao.SchedaProgettoDAO;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbworkspace.integration.request.SalvaSchedaProgettoRequest;
import it.csi.pbandi.pbworkspace.util.BeanUtil;
import it.csi.pbandi.pbworkspace.util.Constants;
import it.csi.pbandi.pbworkspace.util.ConstantsMap;
import it.csi.pbandi.pbworkspace.util.DateUtil;

@Service
public class SchedaProgettoDAOImpl extends JdbcDaoSupport implements SchedaProgettoDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	private static final String FILTER_FIELD_PROGR_BANDO_LINEA = "progrBandoLineaIntervento";

	@Autowired
	private LoggerUtil loggerUtil;

	@Autowired
	public SchedaProgettoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public SchedaProgettoDAOImpl() {
	}

	@Autowired
	it.csi.pbandi.pbworkspace.pbandisrv.business.GestioneAvvioProgettoBusinessImpl gestioneAvvioProgettoBusinessImpl;

	@Autowired
	it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;

	@Autowired
	protected BeanUtil beanUtil;

	@Override
	public InizializzaSchedaProgettoDTO inizializzaSchedaProgetto(Long progrBandoLineaIntervento, Long idProgetto,
			Long idSoggetto, String codiceRuolo, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[SchedaProgettoDAOImpl::inizializzaSchedaProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento = " + progrBandoLineaIntervento + "; idProgetto = " + idProgetto
				+ "; idUtente = " + idUtente);

		if (progrBandoLineaIntervento == null) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		InizializzaSchedaProgettoDTO result = new InizializzaSchedaProgettoDTO();
		try {

			String sqlBli = "SELECT NOME_BANDO_LINEA, ID_PROCESSO FROM PBANDI_R_BANDO_LINEA_INTERVENT WHERE PROGR_BANDO_LINEA_INTERVENTO = "
					+ progrBandoLineaIntervento;
			LOG.info("\n" + sqlBli);
			PbandiRBandoLineaInterventVO vo = (PbandiRBandoLineaInterventVO) getJdbcTemplate().queryForObject(sqlBli,
					new BeanRowMapper(PbandiRBandoLineaInterventVO.class));

			result.setNomeBandoLinea(vo.getNomeBandoLinea());
			if (vo.getIdProcesso() != null)
				result.setIdProcesso(vo.getIdProcesso().longValue());
			LOG.info(prf + "idProcesso = " + result.getIdProcesso());

			EsitoSchedaProgetto esitoSchedaProgetto = caricaSchedaProgetto(progrBandoLineaIntervento, idProgetto,
					idUtente, idIride);
			result.setSchedaProgetto(esitoSchedaProgetto.getSchedaProgetto());

			LOG.info(prf + "idAsse = " + esitoSchedaProgetto.getSchedaProgetto().getIdLineaAsse());
			LOG.info(prf + "IdBandoLinea = " + esitoSchedaProgetto.getSchedaProgetto().getIdBandoLinea());
			LOG.info(prf + "IdLineaNormativa = " + esitoSchedaProgetto.getSchedaProgetto().getIdLineaNormativa());
			LOG.info(prf + "IdProgetto = " + esitoSchedaProgetto.getSchedaProgetto().getIdProgetto());
			LOG.info(prf + esitoSchedaProgetto.getSchedaProgetto().getInformazioniBase());

			String idAsse = esitoSchedaProgetto.getSchedaProgetto().getIdLineaAsse();

			result.setComboPrioritaQsn(this.popolaComboPrioritaQsn(idUtente, idIride, idAsse));

			// verifico se programmazione 21-27
			String sql = "SELECT pdldi.DESC_BREVE_LINEA  \r\n" + "FROM PBANDI_R_BANDO_LINEA_INTERVENT prbli\r\n"
					+ "JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n"
					+ "JOIN PBANDI_D_LINEA_DI_INTERVENTO pdldi ON pdldi.ID_LINEA_DI_INTERVENTO = ptb.ID_LINEA_DI_INTERVENTO\r\n"
					+ "WHERE PROGR_BANDO_LINEA_INTERVENTO  = ?";
			LOG.info("\n" + sql);
			Object[] args = new Object[] { progrBandoLineaIntervento };
			LOG.info(prf + " <progrBandoLineaIntervento>: " + progrBandoLineaIntervento);
			List<String> descBreveLinea = getJdbcTemplate().queryForList(sql, args, String.class);

			if (descBreveLinea != null && descBreveLinea.size() > 0 && descBreveLinea.get(0).equals(Constants.DESC_BREVE_NORMATIVA_21_27)) {
				result.setIsProgrammazione2127(Boolean.TRUE);
			}

		} catch (Exception e) {
			String msg = "Errore nella inizializzazione della scheda progetto.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	private void stampaCombo(ArrayList<CodiceDescrizione> lista) {
		if (lista == null) {
			LOG.info("Combo = NULL");
			return;
		}
		LOG.info("Combo con " + lista.size() + " elementi:");
		for (CodiceDescrizione cd : lista) {
			LOG.info("   " + cd.getCodice() + "  --  " + cd.getDescrizione());
		}
	}

	// private EsitoSchedaProgetto caricaSchedaProgetto(GestioneSchedaProgettoModel
	// theModel) throws Exception {
	private EsitoSchedaProgetto caricaSchedaProgetto(Long progrBandoLineaIntervento, Long idProgetto, Long idUtente,
			String idIride) throws Exception {
		String prf = "[SchedaProgettoDAOImpl::caricaSchedaProgetto] ";
		LOG.info(prf + " BEGIN");

		SchedaProgetto schedaProgetto = new SchedaProgetto();

		if (idProgetto != null)
			schedaProgetto.setIdProgetto(idProgetto.toString());

		schedaProgetto.setIdBandoLinea(progrBandoLineaIntervento.toString());

		EsitoSchedaProgetto esitoSchedaProgetto = new EsitoSchedaProgetto();

		esitoSchedaProgetto = this.popolaSchedaProgetto(idUtente, idIride, schedaProgetto);

		letturaTabSchedaProgetto(esitoSchedaProgetto.getSchedaProgetto());

		letturaTabInterniSoggetto(esitoSchedaProgetto.getSchedaProgetto());

		// Alex: veniva messo in AppDataannoConcessioneOld: che me ne faccio?
		String AnnoConcessioneOld = aggiornaAnnoConcessioneOld(esitoSchedaProgetto.getSchedaProgetto(), false);

		if (esitoSchedaProgetto.getSchedaProgetto() != null
				&& esitoSchedaProgetto.getSchedaProgetto().getBeneficiario() != null
				&& esitoSchedaProgetto.getSchedaProgetto().getBeneficiario().getDatiPG() != null) {

			String tipoDipDir = esitoSchedaProgetto.getSchedaProgetto().getBeneficiario().getDatiPG().getTipoDipDir();
			String idSettoreEnte = esitoSchedaProgetto.getSchedaProgetto().getBeneficiario().getDatiPG()
					.getIdSettoreEnte();
			TabDirRegSogg tabPA = esitoSchedaProgetto.getSchedaProgetto().getBeneficiario().getDatiPG().getTabPA();
			TabDirRegSogg tabDirReg = esitoSchedaProgetto.getSchedaProgetto().getBeneficiario().getDatiPG()
					.getTabDirReg();

			loggerUtil.info("\n\n\ntipoDipDir = " + tipoDipDir + "\n\n\n");

			if (it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_PA.equalsIgnoreCase(tipoDipDir)) {
				if (tabPA != null) {
					tabPA.setIdSettore(idSettoreEnte);
					loggerUtil.info("\n\n\ntipoDipDir =  " + tipoDipDir + "; idSettoreEnte = " + idSettoreEnte
							+ "; tabPA.getIdSettore() =  " + tabPA.getIdSettore() + "\n\n\n");
				}
			}

			if (it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DR.equalsIgnoreCase(tipoDipDir)) {
				if (tabDirReg != null) {
					tabDirReg.setIdSettore(idSettoreEnte);
					loggerUtil.info("\n\n\nQQQQQQ " + tipoDipDir + "  -  " + idSettoreEnte + "  -  "
							+ tabDirReg.getIdSettore() + "\n\n\n");
				}
			}

		}

		LOG.info(prf + " END");
		return esitoSchedaProgetto;
	}

	private String aggiornaAnnoConcessioneOld(SchedaProgetto schedaProgetto, boolean fonteAllineamento) {
		String dataConcessione = schedaProgetto.getInformazioniBase().getDataConcessione();
		String anno = DateUtil.isValidDate(dataConcessione)
				? beanUtil.transform(DateUtil.getAnno(dataConcessione), String.class)
				: "";
		return anno;
	}

	private void letturaTabSchedaProgetto(SchedaProgetto schedaProgetto) throws Exception {
		String prf = "[SchedaProgettoDAOImpl::letturaTabSchedaProgetto] ";
		LOG.info(prf + " BEGIN");

		SchedaProgettoInfoBase schedaProgettoInfoBase = new SchedaProgettoInfoBase();

		// Alex: spero che la sostituzione sia giusta.
		// schedaProgettoInfoBase.setIdBandoLinea(getProgrBandoLineaIntervento(theModel));
		schedaProgettoInfoBase.setIdBandoLinea(schedaProgetto.getIdBandoLinea());

		schedaProgettoInfoBase.setIdLineaNormativa(schedaProgetto.getIdLineaNormativa());
		schedaProgettoInfoBase.setIdLineaAsse(schedaProgetto.getIdLineaAsse());
		schedaProgettoInfoBase.setId(schedaProgetto.getIdProgetto());
		schedaProgettoInfoBase.setFlagSalvaIntermediario(schedaProgetto.getFlagSalvaIntermediario());

		loggerUtil.info("\n\nschedaProgetto.InformazioniBase: ");
		loggerUtil.shallowDump(schedaProgetto.getInformazioniBase(), "info");

		LOG.info(prf + " END");
	}

	private void letturaTabInterniSoggetto(SchedaProgetto schedaProgetto) throws Exception {
		String prf = "[SchedaProgettoDAOImpl::caricaSchedaProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " lettura Beneficiario");
		letturaTabSoggettoInternoAlTAbSoggetto(schedaProgetto.getBeneficiario());
		LOG.info(prf + " lettura AltroSoggettoDefault");
		letturaTabSoggettoInternoAlTAbSoggetto(schedaProgetto.getAltroSoggettoDefault());
		LOG.info(prf + " lettura Intermediario");
		letturaTabSoggettoInternoAlTAbSoggetto(schedaProgetto.getIntermediario());
		LOG.info(prf + " END");
	}

	private void letturaTabSoggettoInternoAlTAbSoggetto(SoggettoGenerico soggetto) throws Exception {
		soggetto.getDatiPG().setTabAltro(new TabDirRegSogg());
		soggetto.getDatiPG().setTabDipUni(new TabDirRegSogg());
		soggetto.getDatiPG().setTabDirReg(new TabDirRegSogg());
		soggetto.getDatiPG().setTabPA(new TabDirRegSogg());

		if ((!StringUtil.isEmpty(soggetto.getDatiPG().getTipoDipDir()) && (soggetto.getDatiPG().getTipoDipDir()
				.equals(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_NA)))) {
			beanUtil.valueCopy(soggetto.getDatiPG(), soggetto.getDatiPG().getTabAltro());
			soggetto.getDatiPG().getTabAltro().setRuolo(copiaArrayRuoli(soggetto.getDatiPG().getRuolo()));
		} else if (soggetto.getDatiPG().getTipoDipDir()
				.equals(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DR)) {
			beanUtil.valueCopy(soggetto.getDatiPG(), soggetto.getDatiPG().getTabDirReg());
			soggetto.getDatiPG().getTabDirReg().setRuolo(copiaArrayRuoli(soggetto.getDatiPG().getRuolo()));
			soggetto.getDatiPG().getTabDirReg().setIdSettore(soggetto.getDatiPG().getIdSettoreEnte());
		} else if (soggetto.getDatiPG().getTipoDipDir()
				.equals(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_PA)) {
			beanUtil.valueCopy(soggetto.getDatiPG(), soggetto.getDatiPG().getTabPA());
			soggetto.getDatiPG().getTabPA().setRuolo(copiaArrayRuoli(soggetto.getDatiPG().getRuolo()));
			soggetto.getDatiPG().getTabPA().setIdSettore(soggetto.getDatiPG().getIdSettoreEnte());
		} else if (soggetto.getDatiPG().getTipoDipDir()
				.equals(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DU)) {
			beanUtil.valueCopy(soggetto.getDatiPG(), soggetto.getDatiPG().getTabDipUni());
			soggetto.getDatiPG().getTabDipUni().setRuolo(copiaArrayRuoli(soggetto.getDatiPG().getRuolo()));
		}

		completaComuneTabInterni(soggetto.getDatiPG().getTabAltro(), soggetto.getDatiPG().getSedeLegale());
		completaComuneTabInterni(soggetto.getDatiPG().getTabDirReg(), soggetto.getDatiPG().getSedeLegale());
		completaComuneTabInterni(soggetto.getDatiPG().getTabPA(), soggetto.getDatiPG().getSedeLegale());
		completaComuneTabInterni(soggetto.getDatiPG().getTabDipUni(), soggetto.getDatiPG().getSedeLegale());
	}

	private void completaComuneTabInterni(TabDirRegSogg tab, Comune comune) {
		if (tab.getSedeLegale() == null || StringUtil.isEmpty(tab.getSedeLegale().getIdNazione())) {
			tab.setSedeLegale(new Comune());
			beanUtil.valueCopy(comune, tab.getSedeLegale());
		}
	}

	private void copiaArrayRuoli(SoggettoGenerico soggetto, ArrayList<String> ruoli) {
		soggetto.getDatiPG().setRuolo(new ArrayList<String>());
		if (ruoli != null && (ruoli.size() > 0)) {
			for (Iterator iterator = ruoli.iterator(); iterator.hasNext();) {
				String ruolo = (String) iterator.next();
				soggetto.getDatiPG().getRuolo().add(new String(ruolo));
			}
		}
	}

	private ArrayList<String> copiaArrayRuoli(ArrayList<String> ruoli) {
		int index = 0;
		ArrayList<String> nuovaLista = new ArrayList<String>();
		if (ruoli == null)
			return nuovaLista;
		if (ruoli != null && (ruoli.size() > 0)) {
			for (Iterator iterator = ruoli.iterator(); iterator.hasNext();) {
				String ruolo = (String) iterator.next();
				nuovaLista.add(new String(ruolo));
				index++;
			}
		}
		return nuovaLista;
	}

	private EsitoSchedaProgetto popolaSchedaProgetto(Long idUtente, String idIride, SchedaProgetto schedaProgetto)
			throws Exception {
		String prf = "[SchedaProgettoDAOImpl::popolaSchedaProgetto] ";
		LOG.info(prf + " BEGIN");
		it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSchedaProgettoDTO esito;
		esito = gestioneAvvioProgettoBusinessImpl.caricaSchedaProgetto(idUtente, idIride, beanUtil.transform(
				schedaProgetto, it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO.class));
		LOG.info(prf + " arricchimento");
		EsitoSchedaProgetto result = arricchimento(esito);
		LOG.info(prf + " END");
		return result;
	}

	private EsitoSchedaProgetto arricchimento(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSchedaProgettoDTO esitoSchedaProgettoDTO) {

		EsitoSchedaProgetto esitoSchedaProgetto = beanUtil.transform(esitoSchedaProgettoDTO, EsitoSchedaProgetto.class);

		SchedaProgetto schedaProgetto = esitoSchedaProgetto.getSchedaProgetto();
		loggerUtil.info("\n\n\n\n\n\n\n\n\n\n\narricchimento  +++++++++++++++++++");
		loggerUtil.shallowDump(esitoSchedaProgettoDTO.getSchedaProgetto().getInformazioniBase(), "info");
		loggerUtil.shallowDump(schedaProgetto.getInformazioniBase(), "info");
		schedaProgetto.getInformazioniBase();

		it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO schedaProgettoDTO = esitoSchedaProgettoDTO
				.getSchedaProgetto();
		schedaProgetto.setAltriSoggetti(
				beanUtil.transformToArrayList(schedaProgettoDTO.getAltriSoggetti(), SoggettoGenerico.class));

		schedaProgetto.setSediIntervento(
				beanUtil.transformToArrayList(schedaProgettoDTO.getSediIntervento(), SedeIntervento.class));

		long i = 0;
		if (schedaProgetto.getSediIntervento() != null) {
			for (SedeIntervento s : schedaProgetto.getSediIntervento()) {
				s.setId(i);
				i++;
			}
		}
		i = 0;
		if (schedaProgetto.getAltriSoggetti() != null) {
			for (SoggettoGenerico s : schedaProgetto.getAltriSoggetti()) {
				s.setId(i);
				i++;
			}
		}
		arricchimentoRuoliSoggetto(schedaProgetto.getBeneficiario(), schedaProgettoDTO.getBeneficiario());
		arricchimentoRuoliSoggetto(schedaProgetto.getIntermediario(), schedaProgettoDTO.getIntermediario());
		int j = 0;
		for (SoggettoGenerico sogg : schedaProgetto.getAltriSoggetti()) {
			arricchimentoRuoliSoggetto(sogg, schedaProgettoDTO.getAltriSoggetti()[j]);
			j++;
		}
		esitoSchedaProgetto.setChiaviCampi(new ArrayList<String>());
		if (esitoSchedaProgettoDTO.getChiaviCampi() != null) {
			for (String chiave : esitoSchedaProgettoDTO.getChiaviCampi()) {
				esitoSchedaProgetto.getChiaviCampi().add(chiave);
			}
		}
		esitoSchedaProgetto.setErroriCampi(new ArrayList<String>());
		if (esitoSchedaProgettoDTO.getErroriCampi() != null) {
			for (String errore : esitoSchedaProgettoDTO.getErroriCampi()) {
				esitoSchedaProgetto.getErroriCampi().add(errore);
			}
		}
		esitoSchedaProgetto.setErroriGlobali(new ArrayList<String>());
		if (esitoSchedaProgettoDTO.getErroriGlobali() != null) {
			for (String errore : esitoSchedaProgettoDTO.getErroriGlobali()) {
				esitoSchedaProgetto.getErroriGlobali().add(errore);
			}
		}

		return esitoSchedaProgetto;
	}

	private void arricchimentoRuoliSoggetto(SoggettoGenerico sogg,
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO soggDTO) {
		sogg.getDatiPG().setRuolo(beanUtil.transformToArrayList(soggDTO.getDatiPG().getRuolo(), String.class));
		sogg.getDatiPF().setRuolo(beanUtil.transformToArrayList(soggDTO.getDatiPF().getRuolo(), String.class));
	}

	@Override
	// Popola le combo che non dipendono da altre combo.
	public InizializzaComboDTO inizializzaCombo(Long progrBandoLineaIntervento, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[SchedaProgettoDAOImpl::inizializzaCombo] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento = " + progrBandoLineaIntervento + "; idUtente = " + idUtente);

		if (progrBandoLineaIntervento == null) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		InizializzaComboDTO result = new InizializzaComboDTO();
		try {

			PbandiRBandoLineaInterventVO bandoLinea = this.trovaBandoLineaIntervento(progrBandoLineaIntervento);

			Long idProcesso = (bandoLinea.getIdProcesso() == null) ? null : bandoLinea.getIdProcesso().longValue();
			LOG.info(prf + "idProcesso = " + idProcesso);

			String progrBandoLineaStr = progrBandoLineaIntervento.toString();
			String idProcessoStr = (idProcesso == null) ? "" : idProcesso.toString();

			result.setComboStrumentoAttuativo(
					this.popolaComboStrumentoAttuativo(idUtente, idIride, progrBandoLineaIntervento));
			result.setComboSettoreCpt(this.popolaComboSettoreCpt(idUtente, idIride));
			result.setComboTemaPrioritario(this.popolaComboTemaPrioritario(idUtente, idIride, progrBandoLineaStr));
			result.setComboIndicatoreRisultatoProgramma(
					this.popolaComboIndicatoreRisultatoProgramma(idUtente, idIride, progrBandoLineaStr));
			result.setComboIndicatoreQsn(this.popolaComboIndicatoreQsn(idUtente, idIride, progrBandoLineaStr));
			result.setComboTipoAiuto(this.popolaComboTipoAiuto(idUtente, idIride, progrBandoLineaStr));
			result.setComboStrumentoProgrammazione(this.popolaComboStrumentoProgrammazione(idUtente, idIride));
			result.setComboDimensioneTerritoriale(this.popolaComboDimensioneTerritoriale(idUtente, idIride,
					idProcessoStr, progrBandoLineaIntervento));
			result.setComboProgettoComplesso(
					this.popolaComboProgettoComplesso(idUtente, idIride, idProcessoStr, progrBandoLineaIntervento));
			result.setComboSettoreCipe(this.popolaComboSettoreCipe(idUtente, idIride));
			result.setComboNaturaCipe(this.popolaComboNaturaCipe(idUtente, idIride));
			result.setComboRuoli(this.popolaComboRuoli(idUtente, idIride, progrBandoLineaIntervento));
			result.setComboAteneo(this.popolaComboAteneo(idUtente, idIride));
			result.setComboSettoreAttivita(
					this.popolaComboSettoreAttivita(idUtente, idIride, progrBandoLineaIntervento));
			result.setComboDenominazioneEnteDipReg(this.popolaComboDenominazioneEnteDipReg(idUtente, idIride));
			result.setComboDenominazioneEntePA(this.popolaComboDenominazioneEntePA(idUtente, idIride));
			result.setComboDimensioneImpresa(this.popolaComboDimensioneImpresa(idUtente, idIride));
			result.setComboFormaGiuridica(this.popolaComboFormaGiuridica(idUtente, idIride));
			result.setComboRelazioneColBeneficiario(this.popolaComboRelazioneColBeneficiario(idUtente, idIride));
			result.setComboNazione(this.popolaComboNazione(idUtente, idIride, false));
			result.setComboNazioneNascita(this.popolaComboNazione(idUtente, idIride, true));
			result.setComboTipoOperazione(this.popolaComboTipoOperazione(idUtente, idIride));

			result.setComboObiettivoTematico(new ArrayList<CodiceDescrizione>());
			result.setComboGrandeProgetto(new ArrayList<CodiceDescrizione>());
			if (idProcesso != null && (idProcesso.toString().equals(Constants.ID_PROCESSO_NUOVA_PROGRAMMAZIONE) 
					|| idProcesso.toString().equals(Constants.ID_PROCESSO_CULTURA) || idProcesso.toString().equals(Constants.ID_PROCESSO_WELFARE_ABITATIVO))) {
				logger.info("carico combo per processo " + idProcesso);
				result.setComboObiettivoTematico(this.popolaComboObiettivoTematico(idUtente, idIride));
				result.setComboGrandeProgetto(this.popolaComboGrandeProgetto(idUtente, idIride));
			}

		} catch (Exception e) {
			String msg = "Errore nella inizializzazione delle combo senza dipendenze.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	private PbandiRBandoLineaInterventVO trovaBandoLineaIntervento(Long progrBandoLineaIntervento) throws Exception {
		String sqlBli = "SELECT NOME_BANDO_LINEA, ID_PROCESSO FROM PBANDI_R_BANDO_LINEA_INTERVENT WHERE PROGR_BANDO_LINEA_INTERVENTO = "
				+ progrBandoLineaIntervento;
		LOG.info("\n" + sqlBli);
		PbandiRBandoLineaInterventVO vo = (PbandiRBandoLineaInterventVO) getJdbcTemplate().queryForObject(sqlBli,
				new BeanRowMapper(PbandiRBandoLineaInterventVO.class));
		return vo;
	}

	private ArrayList<CodiceDescrizione> popolaComboStrumentoAttuativo(Long idUtente, String idIride,
			Long progrBandoLinea) throws Exception {
		loggerUtil
				.info("\n\n\n\n\n\n\n\ngestioneDatiDiDominioBusinessImpl.findDecodificheMultiProgr per progrBandoLinea:"
						+ progrBandoLinea);
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheByProgrBandoLinea(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.STRUMENTO_ATTUATIVO, progrBandoLinea),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboSettoreCpt(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.SETTORE_CPT),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboTemaPrioritario(Long idUtente, String idIride,
			String progrBandoLinea) throws Exception {
		return beanUtil.transformToArrayList(gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente,
				idIride, gestioneDatiDiDominioBusinessImpl.TEMA_PRIORITARIO_BANDO_LINEA, FILTER_FIELD_PROGR_BANDO_LINEA,
				progrBandoLinea), CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboIndicatoreRisultatoProgramma(Long idUtente, String idIride,
			String progrBandoLinea) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.IND_RISULTATO_PROGRAM_BANDO_LINEA,
						FILTER_FIELD_PROGR_BANDO_LINEA, progrBandoLinea),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboIndicatoreQsn(Long idUtente, String idIride, String progrBandoLinea)
			throws Exception {
		return beanUtil.transformToArrayList(gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente,
				idIride, gestioneDatiDiDominioBusinessImpl.INDICATORE_QSN_BANDO_LINEA, FILTER_FIELD_PROGR_BANDO_LINEA,
				progrBandoLinea), CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboTipoAiuto(Long idUtente, String idIride, String progrBandoLinea)
			throws Exception {
		return beanUtil.transformToArrayList(gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente,
				idIride, gestioneDatiDiDominioBusinessImpl.TIPO_AIUTO_BANDO_LINEA, FILTER_FIELD_PROGR_BANDO_LINEA,
				progrBandoLinea), CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboStrumentoProgrammazione(Long idUtente, String idIride)
			throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.TIPO_STRUMENTO_PROGR),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboDimensioneTerritoriale(Long idUtente, String idIride,
			String idProcesso, Long progrBandoLinea) throws Exception {
		loggerUtil.info("\n\n\n\npopolaComboDimensioneTerritoriale per nuova programmazione progrBandoLinea "
				+ progrBandoLinea);
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheByProgrBandoLinea(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.DIMENSIONE_TERRITOR, progrBandoLinea),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboProgettoComplesso(Long idUtente, String idIride, String idProcesso,
			Long progrBandoLinea) throws Exception {
		loggerUtil.info(
				"\n\n\n\npopolaComboProgettoComplesso per nuova programmazione progrBandoLinea " + progrBandoLinea);
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheByProgrBandoLinea(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.PROGETTO_COMPLESSO, progrBandoLinea),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboSettoreCipe(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.SETTORE_CIPE),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboNaturaCipe(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.NATURA_CIPE),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboRuoli(Long idUtente, String idIride, Long progrBandoLinea)
			throws Exception {
		loggerUtil.info(
				"\n\n\n\n\n\n\n\n\n\n\n ********************************** findDecodificheFiltratoByProgrBandoLinea\n\n");
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheFiltratoByProgrBandoLinea(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.RUOLO_ENTE_COMPETENZA, "flagVisibile",
						Constants.KEY_FLAG_VISIBILE, progrBandoLinea),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboAteneo(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.ATENEO),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboSettoreAttivita(Long idUtente, String idIride, Long progBandoLinea)
			throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheByProgrBandoLinea(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.SETTORE_ATTIVITA, progBandoLinea),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboDenominazioneEnteDipReg(Long idUtente, String idIride)
			throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.ENTE_DI_COMPETENZA, "descBreveTipoEnteCompetenz",
						Constants.DESC_BREVE_TIPO_ENTE_COMPETENZ_DIR_REG),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboDenominazioneEnteDipUni(Long idUtente, String idIride, Long idAteneo)
			throws Exception {
		it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica[] decodifiche;
		decodifiche = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
				gestioneDatiDiDominioBusinessImpl.DIPARTIMENTO, "idAteneo", idAteneo.toString());
		ArrayList<CodiceDescrizione> lista = new ArrayList<CodiceDescrizione>();
		for (it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica d : decodifiche) {
			CodiceDescrizione cd = new CodiceDescrizione();
			cd.setCodice(d.getId().toString());
			cd.setDescrizione(d.getDescrizioneBreve() + " " + d.getDescrizione());
			lista.add(cd);
		}
		return lista;
	}

	private ArrayList<CodiceDescrizione> popolaComboDenominazioneEntePA(Long idUtente, String idIride)
			throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.ENTE_DI_COMPETENZA, "descBreveTipoEnteCompetenz",
						Constants.DESC_BREVE_TIPO_ENTE_COMPETENZ_PA),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboDimensioneImpresa(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.DIMENSIONE_IMPRESA),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboFormaGiuridica(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.FORMA_GIURIDICA),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboRelazioneColBeneficiario(Long idUtente, String idIride)
			throws Exception {
		return beanUtil.transformToArrayList(gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente,
				idIride, gestioneDatiDiDominioBusinessImpl.TIPO_SOGG_CORRELATO, "flagVisibile",
				Constants.KEY_FLAG_VISIBILE), CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	// usato per setAppDatacaricaComboNazione e setAppDatacaricaComboNazioneNas
	private ArrayList<IdDescBreveDescEstesa> popolaComboNazione(Long idUtente, String idIride, boolean all)
			throws Exception {
		if (!all) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
							gestioneDatiDiDominioBusinessImpl.NAZIONE),
					IdDescBreveDescEstesa.class, ConstantsMap.MAP_ID_DESC_BREVE_DESC_ESTESA);
		} else {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheStorico(idUtente, idIride,
							gestioneDatiDiDominioBusinessImpl.NAZIONE),
					IdDescBreveDescEstesa.class, ConstantsMap.MAP_ID_DESC_BREVE_DESC_ESTESA);
		}
	}

	// Combo usata solo per processo 2.
	private ArrayList<CodiceDescrizione> popolaComboObiettivoTematico(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.OBIETTIVO_TEMATICO),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	// Combo usata solo per processo 2.
	private ArrayList<CodiceDescrizione> popolaComboGrandeProgetto(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						gestioneDatiDiDominioBusinessImpl.GRANDE_PROGETTO),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboAttivitaAteco(Long idUtente, String idIride, Long idSettoreAttivita)
			throws Exception {
		if (idSettoreAttivita != null) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.ATTIVITA_ATECO, "idSettoreAttivita", idSettoreAttivita.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return new ArrayList<CodiceDescrizione>();
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboObiettivoGeneraleQsn(Long idUtente, String idIride,
			Long idListaPrioritaQsn) throws Exception {
		if (idListaPrioritaQsn != null) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.OBIETTIVO_GEN_QSN, "idPrioritaQsn", idListaPrioritaQsn.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return new ArrayList<CodiceDescrizione>();
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboObiettivoSpecificoQsn(Long idUtente, String idIride,
			Long idObiettivoGenerale) throws Exception {
		if (idObiettivoGenerale != null) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.OBIETTIVO_SPECIF_QSN, "idObiettivoGenQsn",
							idObiettivoGenerale.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return new ArrayList<CodiceDescrizione>();
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboClassificazioneRA(Long idUtente, String idIride,
			Long idObiettivoTematico) throws Exception {
		if (idObiettivoTematico != null) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.CLASSIFICAZIONE_RA, "idObiettivoTem",
							idObiettivoTematico.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return new ArrayList<CodiceDescrizione>();
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboSottoSettoreCipe(Long idUtente, String idIride, Long idSettoreCipe)
			throws Exception {
		if (idSettoreCipe != null) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.SOTTO_SETTORE_CIPE, "idSettoreCipe", idSettoreCipe.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return new ArrayList<CodiceDescrizione>();
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboCategoriaCipe(Long idUtente, String idIride, Long idSottoSettoreCipe)
			throws Exception {
		if (idSottoSettoreCipe != null) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.CATEGORIA_CIPE, "idSottoSettoreCipe",
							idSottoSettoreCipe.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return new ArrayList<CodiceDescrizione>();
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboTipologiaCipe(Long idUtente, String idIride, Long idNaturaCipe)
			throws Exception {
		if (idNaturaCipe != null) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.TIPOLOGIA_CIPE, "idNaturaCipe", idNaturaCipe.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return new ArrayList<CodiceDescrizione>();
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboRegione(Long idUtente, String idIride) throws Exception {
		return this.popolaComboRegione(idUtente, idIride, false);
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboRegioneNascita(Long idUtente, String idIride) throws Exception {
		return this.popolaComboRegione(idUtente, idIride, true);
	}

	private ArrayList<CodiceDescrizione> popolaComboRegione(Long idUtente, String idIride, boolean all)
			throws Exception {
		if (!all) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
							GestioneDatiDiDominioSrv.REGIONE),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheStorico(idUtente, idIride,
							GestioneDatiDiDominioSrv.REGIONE),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboProvincia(Long idUtente, String idIride, Long idRegione)
			throws Exception {
		return this.popolaComboProvincia(idUtente, idIride, idRegione, false);
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboProvinciaNascita(Long idUtente, String idIride, Long idRegione)
			throws Exception {
		return this.popolaComboProvincia(idUtente, idIride, idRegione, true);
	}

	private ArrayList<CodiceDescrizione> popolaComboProvincia(Long idUtente, String idIride, Long idRegione,
			boolean all) throws Exception {
		if (!all) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.PROVINCIA, "idRegione", idRegione.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltratoStorico(idUtente, idIride,
							GestioneDatiDiDominioSrv.PROVINCIA, "idRegione", idRegione.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboComuneEstero(Long idUtente, String idIride, Long idNazione)
			throws Exception {
		return this.popolaComboComuneEstero(idUtente, idIride, idNazione, false);
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboComuneEsteroNascita(Long idUtente, String idIride, Long idNazione)
			throws Exception {
		return this.popolaComboComuneEstero(idUtente, idIride, idNazione, true);
	}

	private ArrayList<CodiceDescrizione> popolaComboComuneEstero(Long idUtente, String idIride, Long idNazione,
			boolean all) throws Exception {
		if (!all) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.COMUNE_ESTERO, "idNazione", idNazione.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltratoStorico(idUtente, idIride,
							GestioneDatiDiDominioSrv.COMUNE_ESTERO, "idNazione", idNazione.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		}
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboComuneItaliano(Long idUtente, String idIride, Long idProvincia)
			throws Exception {
		return this.popolaComboComuneItaliano(idUtente, idIride, idProvincia, false);
	}

	@Override
	public ArrayList<CodiceDescrizione> popolaComboComuneItalianoNascita(Long idUtente, String idIride,
			Long idProvincia) throws Exception {
		return this.popolaComboComuneItaliano(idUtente, idIride, idProvincia, true);
	}

	public ArrayList<CodiceDescrizione> popolaComboComuneItaliano(Long idUtente, String idIride, Long idProvincia,
			boolean all) throws Exception {
		if (!all) {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.COMUNE, "idProvincia", idProvincia.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		} else {
			return beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltratoStorico(idUtente, idIride,
							GestioneDatiDiDominioSrv.COMUNE, "idProvincia", idProvincia.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		}
	}

	// Vale per popolare la combo dei settori sia per DirReg sia per PA.
	@Override
	public ArrayList<CodiceDescrizione> popolaComboDenominazioneSettori(Long idUtente, String idIride, Long idEnte)
			throws Exception {
		ArrayList<CodiceDescrizione> result;
		if (idEnte == null)
			result = new ArrayList<CodiceDescrizione>();
		else
			result = beanUtil.transformToArrayList(
					gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.SETTORE_ENTE, "idEnteCompetenza", idEnte.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
		return result;
	}

	private ArrayList<CodiceDescrizione> popolaComboPrioritaQsn(Long idUtente, String idIride, String idLineaAsse)
			throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
						GestioneDatiDiDominioSrv.PRIORITA_QSN_LINEA, "idLineaDiIntervento", idLineaAsse),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	private ArrayList<CodiceDescrizione> popolaComboTipoOperazione(Long idUtente, String idIride) throws Exception {
		return beanUtil.transformToArrayList(
				gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
						GestioneDatiDiDominioSrv.TIPO_OPERAZIONE),
				CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
	}

	@Override
	// Popola la popup di ricerca dei Beneficiari in base al codice fiscale.
	// Ex SchedaProgettoAction.ricercaBeneficiarioCsp()
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.BeneficiarioCspDTO[] ricercaBeneficiarioCsp(
			String codiceFiscale, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SchedaProgettoDAOImpl::ricercaBeneficiarioCsp] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codiceFiscale = " + codiceFiscale + "; idUtente = " + idUtente);

		if (codiceFiscale == null) {
			throw new InvalidParameterException("codiceFiscale non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.BeneficiarioCspDTO[] result = null;
		try {

			result = gestioneAvvioProgettoBusinessImpl.ricercaBeneficiarioCsp(idUtente, idIride, codiceFiscale);

			// Cancello il campo data che dà problemi a JSONU.
			for (it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.BeneficiarioCspDTO b : result)
				b.setDtCostituzione(null);

		} catch (Exception e) {
			String msg = "Errore nella ricerca dei Beneficiari.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	// Popola la popup di ricerca dei Rappresentanti Legali in base al codice
	// fiscale.
	// Ex SchedaProgettoAction.ricercaRapprLegaleCsp()
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.RapprLegaleCspDTO[] ricercaRapprLegaleCsp(
			String codiceFiscale, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SchedaProgettoDAOImpl::ricercaRapprLegaleCsp] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codiceFiscale = " + codiceFiscale + "; idUtente = " + idUtente);

		if (codiceFiscale == null) {
			throw new InvalidParameterException("codiceFiscale non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.RapprLegaleCspDTO[] result = null;
		try {

			result = gestioneAvvioProgettoBusinessImpl.ricercaRapprLegaleCsp(idUtente, idIride, codiceFiscale);

			// Cancello il campo data che dà problemi a JSONU.
			for (it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.RapprLegaleCspDTO b : result)
				b.setDtNascita(null);

		} catch (Exception e) {
			String msg = "Errore nella ricerca dei Rappresentanti Legali.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex GestioneAvvioProgettoBusinessImpl.salvaSchedaProgetto()
	public EsitoDTO salvaSchedaProgetto(SalvaSchedaProgettoRequest salvaSchedaProgettoRequest, Long idUtente,
			String idIride) throws InvalidParameterException, Exception {
		String prf = "[SchedaProgettoDAOImpl::salvaSchedaProgetto] ";
		LOG.info(prf + " BEGIN");

		if (salvaSchedaProgettoRequest == null) {
			throw new InvalidParameterException("salvaSchedaProgettoRequest non valorizzato.");
		}
		if (salvaSchedaProgettoRequest.getSchedaProgetto() == null) {
			throw new InvalidParameterException("schedaProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		EsitoDTO result = new EsitoDTO();
		try {

			SchedaProgetto schedaProgetto = salvaSchedaProgettoRequest.getSchedaProgetto();
			Boolean datiCompletiPerAvvio = salvaSchedaProgettoRequest.getDatiCompletiPerAvvio();
			String flagCambiatoCup = "N";

			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO schedaSrv = null;
			schedaSrv = beanUtil.transform(schedaProgetto,
					it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO.class);

			String idSettoreEnte = null;
			try {
				String tipoDipDir = schedaProgetto.getBeneficiario().getDatiPG().getTipoDipDir();
				if (it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_PA.equalsIgnoreCase(tipoDipDir)) {
					idSettoreEnte = schedaProgetto.getBeneficiario().getDatiPG().getTabPA().getIdSettore();
				} else if (it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DR.equalsIgnoreCase(tipoDipDir)) {
					idSettoreEnte = schedaProgetto.getBeneficiario().getDatiPG().getTabDirReg().getIdSettore();
				}
				schedaSrv.getBeneficiario().getDatiPG().setIdSettoreEnte(idSettoreEnte);
				LOG.info(prf + "idSettoreEnte = " + idSettoreEnte);
			} catch (Exception e) {
				logger.error("Errore nell'assegnare idSettoreEnte: " + e);
			}

			loggerUtil.info("\n\nschedaProgetto: ");
			loggerUtil.shallowDump(schedaProgetto, "info");

			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSchedaProgettoDTO esito;
			esito = gestioneAvvioProgettoBusinessImpl.salvaSchedaProgetto(idUtente, idIride, schedaSrv, flagCambiatoCup,
					datiCompletiPerAvvio);

			result.setEsito(esito.getEsito());
			if (esito.getSchedaProgetto() != null)
				result.setId(esito.getSchedaProgetto().getIdProgetto());
			if (esito.getEsito())
				result.getMessaggi().add("Salvataggio avvenuto con successo.");
			else
				result.getMessaggi()
						.add("Si \u00E8 verificato un errore. Si prega di contattare il servizio assistenza.");

		} catch (Exception e) {
			String msg = "Errore nel salvataggio della scheda progetto.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	// Restituisce true se il num domanda è unico.
	public Boolean verificaNumeroDomandaUnico(String numDomanda, Long idProgetto, Long idBandoLinea) throws Exception {
		String prf = "[SchedaProgettoDAOImpl::numeroDomandaUnico] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "numDomanda = " + numDomanda + "; idBandoLinea = " + idBandoLinea + "; idProgetto = "
				+ idProgetto);

		Boolean esito = new Boolean(true);
		try {

			if (!StringUtils.isBlank(numDomanda)) {

				numDomanda = numDomanda.toUpperCase().trim();

				String sqlDom = "select count(*) from PBANDI_T_CSP_PROGETTO where ";
				sqlDom += "PROGR_BANDO_LINEA_INTERVENTO = " + idBandoLinea + " and NUMERO_DOMANDA = '" + numDomanda
						+ "' and ";
				sqlDom += "DT_ELABORAZIONE is null ";
				if (idProgetto != null)
					sqlDom += "and ID_CSP_PROGETTO <> " + idProgetto;
				LOG.info(prf + "\n" + sqlDom);
				Integer nDom = getJdbcTemplate().queryForObject(sqlDom, Integer.class);
				LOG.info(prf + "Record trovati = " + nDom);

				String sqlDom2 = "select count(*) from PBANDI_T_DOMANDA where ";
				sqlDom2 += "PROGR_BANDO_LINEA_INTERVENTO = " + idBandoLinea + " and NUMERO_DOMANDA = '" + numDomanda
						+ "' ";
				LOG.info(prf + "\n" + sqlDom2);
				Integer nDom2 = getJdbcTemplate().queryForObject(sqlDom2, Integer.class);
				LOG.info(prf + "Record trovati = " + nDom2);

				if (nDom > 0 || nDom2 > 1) {
					esito = false;
				}
			}

		} catch (Exception e) {
			String msg = "Errore nella verifica del numero domanda.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Restituisce true se il cup è unico.
	public Boolean verificaCupUnico(String cup, Long idProgetto) throws Exception {
		String prf = "[SchedaProgettoDAOImpl::verificaCupUnico] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "cup = " + cup + "; idProgetto = " + idProgetto);

		Boolean esito = new Boolean(true);
		try {

			if (!StringUtils.isBlank(cup)) {

				cup = cup.toUpperCase().trim();

				String sqlCup = "select count(*) from PBANDI_T_CSP_PROGETTO where ";
				sqlCup += "CUP = '" + cup + "' and DT_ELABORAZIONE is null ";
				if (idProgetto != null)
					sqlCup += "and ID_CSP_PROGETTO <> " + idProgetto;
				LOG.info(prf + "\n" + sqlCup);
				Integer nCup = getJdbcTemplate().queryForObject(sqlCup, Integer.class);
				LOG.info(prf + "Record trovati = " + nCup);

				if (nCup > 0) {
					esito = false;
				}

			}

		} catch (Exception e) {
			String msg = "Errore nella verifica del cup.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO caricaInfoDirezioneRegionale(
			Long idEnteCompetenza, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SchedaProgettoDAOImpl::caricaInfoDirezioneRegionale] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idEnteCompetenza = " + idEnteCompetenza);

		if (idEnteCompetenza == null) {
			throw new InvalidParameterException("idEnteCompetenza non valorizzato.");
		}

		it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO result = null;
		try {

			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPGDTO dto = new it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPGDTO();
			dto.setDenominazioneEnteDirReg(idEnteCompetenza.toString());

			result = gestioneAvvioProgettoBusinessImpl.caricaInfoDirezioneRegionale(idUtente, idIride, dto);

		} catch (Exception e) {
			String msg = "Errore nella ricerca dei dati della Direzione Regionale.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO caricaInfoPubblicaAmministrazione(
			Long idEnteCompetenza, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SchedaProgettoDAOImpl::caricaInfoPubblicaAmministrazione] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idEnteCompetenza = " + idEnteCompetenza);

		if (idEnteCompetenza == null) {
			throw new InvalidParameterException("idEnteCompetenza non valorizzato.");
		}

		it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO result = null;
		try {

			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPGDTO dto = new it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPGDTO();
			dto.setDenominazioneEntePA(idEnteCompetenza.toString());

			result = gestioneAvvioProgettoBusinessImpl.caricaInfoPubblicaAmministrazione(idUtente, idIride, dto);

		} catch (Exception e) {
			String msg = "Errore nella ricerca dei dati della PA.";
			LOG.error(prf + msg, e);
			throw new ErroreGestitoException(msg, e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

}
