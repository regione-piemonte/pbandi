/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.liquidazionebilancio;


import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.csi.wrapper.UserException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.BilancioManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.AliquotaRitenutaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.BeneficiarioBilancioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CausaleLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiIntegrativiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DettaglioBeneficiarioBilancioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EnteCompetenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoAggiornaLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoAggiornaRiepilogoFondiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoCreaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoDatiProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoInfoCreaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiAttoLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiRiepilogoFondiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoRitenuteDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.FideiussioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.FonteFinanziariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ImpegnoAllineatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.InfoCreaAttoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.InputLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.LiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ModAgevolazioneContributoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ModalitaAgevolazioneLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RichiestaErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RipartizioneImpegniLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RitenutaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SedeBilancioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SettoreEnteDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SpesaProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ConfigurationDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiDocumentiDiSpesaDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiErogazioneDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiPagamentiDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CreaAttoImpegniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CreaAttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DatiPagamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.FornitoreCreaAttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IndirizzoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoCreaAttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ModalitaAgevolazioneProgettoContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RichiestaErogazioneCausaleVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SoggettiFinanziatoriPerProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SoggettoEnteCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SoggettoSettoreEnteCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.BeneficiarioBilancioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.ModalitaAgevolazioneLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.ProceduraAggiudicazioneCIGVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.RipartizioneFontiImpegniBandoLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.RipartizioneFontiImpegniProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.GreaterThanCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDAliquotaRitenutaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDCausaleErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneEsteroVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDModalitaAgevolazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDNazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDProvinciaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDSettoreEnteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoOperazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiLLogStatoElabDocVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoCausaleErogazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRContoEconomModAgevVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRContoEconomModAgevVO2;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggProgettoSedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTBeneficiarioBilancioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDatiPagamentoAttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteGiuridicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEstremiBancariVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIndirizzoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPersonaFisicaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRecapitiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRecuperoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.BilancioDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.AttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.AttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ConsultaBeneficiariVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.DatiBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.FornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.InserisciAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.RitenutaAttoNewVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.RitenutaAttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.liquidazionebilancio.LiquidazioneBilancioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.neoflux.NeofluxSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

//import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;





public class LiquidazioneBilancioBusinessImpl extends BusinessImpl implements LiquidazioneBilancioSrv {
	
	private static final String ESITO_POSITIVO = "0";
	private static final int MAX_LENGTH_NOTE = 49;
	private static final int MAX_LENGTH_NUMERO_DOC_SPESA = 200;
	
	@Autowired
	private BilancioDAO bilancioDAO;
	@Autowired
	private BilancioManager bilancioManager;
	@Autowired
	private ConfigurationManager configurationManager;
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private NeofluxSrv neofluxBusiness;
	@Autowired
	private PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO;
	@Autowired
	private PbandiPagamentiDAOImpl pbandipagamentiDAO;
	@Autowired
	private PbandiErogazioneDAOImpl pbandiErogazioneDAO;
	@Autowired
	private ProgettoManager progettoManager;
	@Autowired
	private SedeManager sedeManager;
	
	public NeofluxSrv getNeofluxBusiness() {
		return neofluxBusiness;
	}

	public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
		this.neofluxBusiness = neofluxBusiness;
	}
	
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public void setSedeManager(SedeManager sedeManager) {
		this.sedeManager = sedeManager;
	}

	public SedeManager getSedeManager() {
		return sedeManager;
	}

	public BilancioManager getBilancioManager() {
		return bilancioManager;
	}

	public void setBilancioManager(BilancioManager bilancioManager) {
		this.bilancioManager = bilancioManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setContoEconomicoManager(ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setBilancioDAO(BilancioDAO bilancioDAO) {
		this.bilancioDAO = bilancioDAO;
	}

	public BilancioDAO getBilancioDAO() {
		return bilancioDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}
	public void setPbandiDocumentiDiSpesaDAO(PbandiDocumentiDiSpesaDAOImpl pbandiDocumentiDiSpesaDAO) {
		this.pbandiDocumentiDiSpesaDAO = pbandiDocumentiDiSpesaDAO;
	}

	public PbandiDocumentiDiSpesaDAOImpl getPbandiDocumentiDiSpesaDAO() {
		return pbandiDocumentiDiSpesaDAO;
	}

	public void setPbandipagamentiDAO(PbandiPagamentiDAOImpl pbandipagamentiDAO) {
		this.pbandipagamentiDAO = pbandipagamentiDAO;
	}

	public PbandiPagamentiDAOImpl getPbandipagamentiDAO() {
		return pbandipagamentiDAO;
	}

	public void setPbandiErogazioneDAO(PbandiErogazioneDAOImpl pbandiErogazioneDAO) {
		this.pbandiErogazioneDAO = pbandiErogazioneDAO;
	}

	public PbandiErogazioneDAOImpl getPbandiErogazioneDAO() {
		return pbandiErogazioneDAO;
	}
	
	// Jira PBANDI-2697
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ModAgevolazioneContributoDTO getModAgevolazioneContributo(
			Long idUtente,
			String identitaDigitale,
			Long idProgetto
	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException
	{
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,	identitaDigitale, idProgetto);
		
		ModAgevolazioneContributoDTO modAgevolazioneContributoDTO = new ModAgevolazioneContributoDTO();
		try {
			logger.info("Inizio getModAgevolazioneContributo() con idProgetto = "+idProgetto);
			// PROGETTO
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(new BigDecimal(idProgetto));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			// Da PROGETTO a DOMANDA
			PbandiTDomandaVO domandaVO = new PbandiTDomandaVO();
			domandaVO.setIdDomanda(progettoVO.getIdDomanda());
			domandaVO = genericDAO.findSingleWhere(domandaVO);
			// Da DOMANDA a PROGR BANDO LINEA
			PbandiRBandoLineaInterventVO bandoLineaVO = new PbandiRBandoLineaInterventVO();
			bandoLineaVO.setProgrBandoLineaIntervento(domandaVO.getProgrBandoLineaIntervento());
			bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);
			// CONTO ECONOMICO MASTER
			BigDecimal idContoEconomicoMaster = contoEconomicoManager.getIdContoMaster(new BigDecimal(idProgetto));
			// Query modificata per gestire l'agevolazione in base al bando.
			PbandiRContoEconomModAgevVO2 vo = new PbandiRContoEconomModAgevVO2();
			vo.setIdBando(bandoLineaVO.getIdBando());
			vo.setIdContoEconomico(idContoEconomicoMaster);
			vo = genericDAO.findSingleWhere(vo);			
			if (vo != null) {
				if (vo.getQuotaImportoAgevolato() != null) {
					modAgevolazioneContributoDTO.setQuotaImportoAgevolato(NumberUtil.toDouble(vo.getQuotaImportoAgevolato()));
				}
				if (vo.getIdModalitaAgevolazione() != null) {								
					logger.info("Cerco PbandiDModalitaAgevolazione con id = "+vo.getIdModalitaAgevolazione());
					PbandiDModalitaAgevolazioneVO modAgevVO = new PbandiDModalitaAgevolazioneVO();
					modAgevVO.setIdModalitaAgevolazione(vo.getIdModalitaAgevolazione());
					modAgevVO = genericDAO.findSingleWhere(modAgevVO);
					modAgevolazioneContributoDTO.setIdModAgevolazioneContributo(vo.getIdModalitaAgevolazione().longValue());
					modAgevolazioneContributoDTO.setDescBreveModAgevolazioneContributo(modAgevVO.getDescBreveModalitaAgevolaz());				
				}
			}
		} catch (Exception ex) {
			logger.error("ERRORE in getModAgevolazioneContributo(): "+ex);
			modAgevolazioneContributoDTO.setIdModAgevolazioneContributo(null);
			//throw new UnrecoverableException(ex.getMessage(), ex);
		} 
		logger.info("modAgevolazioneContributoDTO: IdModAgevolazioneContributo = "+modAgevolazioneContributoDTO.getIdModAgevolazioneContributo());
		logger.info("modAgevolazioneContributoDTO: DescBreveModAgevolazioneContributo = "+modAgevolazioneContributoDTO.getDescBreveModAgevolazioneContributo());
		logger.info("modAgevolazioneContributoDTO: QuotaImportoAgevolato() = "+modAgevolazioneContributoDTO.getQuotaImportoAgevolato());
		return  modAgevolazioneContributoDTO;
	}
 

	public ModalitaAgevolazioneLiquidazioneDTO[] findModalitaAgevolazioneLiquidazione(Long idUtente,
			String identitaDigitale, Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, LiquidazioneBilancioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale, idProgetto);

		List<ModalitaAgevolazioneLiquidazioneDTO> result = null;
		ContoEconomicoDTO ce = null;
		try {
			/*
			 * Ricerco le modalita' di agevolazioni associate al progetto
			 */
			try {
				 ce = contoEconomicoManager.findContoEconomicoMaster(NumberUtil.toBigDecimal(idProgetto));
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error("Attenzione contoeconomico MASTER non trovato per il progetto "+idProgetto,e);
				LiquidazioneBilancioException lbe = new LiquidazioneBilancioException("Attenzione contoeconomico MASTER non trovato per il progetto "+idProgetto,e);
				throw lbe;
			}
			
			ModalitaAgevolazioneProgettoContoEconomicoVO filterModalitaVO = new ModalitaAgevolazioneProgettoContoEconomicoVO();
			filterModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			filterModalitaVO.setIdContoEconomico(ce.getIdContoEconomico());
			filterModalitaVO.setAscendentOrder("descModalitaAgevolazione");
			
			List<ModalitaAgevolazioneProgettoContoEconomicoVO> listModalita = genericDAO.findListWhereDistinct(Condition.filterBy(filterModalitaVO),ModalitaAgevolazioneProgettoContoEconomicoVO.class);
			
			if (!ObjectUtil.isEmpty(listModalita)) {
				result = new ArrayList<ModalitaAgevolazioneLiquidazioneDTO>();
				for (ModalitaAgevolazioneProgettoContoEconomicoVO modalitaVO : listModalita) {
					ModalitaAgevolazioneLiquidazioneDTO modalita = beanUtil.transform(modalitaVO, ModalitaAgevolazioneLiquidazioneDTO.class);
					modalita.setTotaleImportoLiquidato(0D);
					ModalitaAgevolazioneLiquidazioneVO filtroModalitaVO = new ModalitaAgevolazioneLiquidazioneVO();
					filtroModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
					filtroModalitaVO.setIdModalitaAgevolazione(modalitaVO.getIdModalitaAgevolazione());
					/*
					 * Per ogni modalita' associata al progetto ricerco le liquidazioni
					 * associate alla modalita' ed al progetto
					 */
					List<ModalitaAgevolazioneLiquidazioneVO> listModalitaAgevolazioneVO = genericDAO.findListWhere(filtroModalitaVO);
					if (!ObjectUtil.isEmpty(listModalitaAgevolazioneVO)) {
						BigDecimal totaleImportoLiquidatoModalita = new BigDecimal(0);
						List<CausaleLiquidazioneDTO> causaliModalita = new ArrayList<CausaleLiquidazioneDTO>();
						for (ModalitaAgevolazioneLiquidazioneVO vo : listModalitaAgevolazioneVO) {
								totaleImportoLiquidatoModalita = NumberUtil.sum(totaleImportoLiquidatoModalita, vo.getImportoLiquidatoAtto());
								CausaleLiquidazioneDTO causale = beanUtil.transform(vo, CausaleLiquidazioneDTO.class);
								causaliModalita.add(causale);
						}
						/*
						 * Associo le causali alla modalita'
						 */
						modalita.setCausaliLiquidazioni(causaliModalita.toArray(new CausaleLiquidazioneDTO[]{}));
						
						/*
						 * Setto il totale delle liquidazioni per la modalita' 
						 */
						modalita.setTotaleImportoLiquidato(NumberUtil.toDouble(totaleImportoLiquidatoModalita));
					}
					/*
					 * Inserisco la modalita' di agevolazione al risultato
					 */
					result.add(modalita);
				}
			}
		} finally {
		}
		return beanUtil.transform(result, ModalitaAgevolazioneLiquidazioneDTO.class);
	}


	public EsitoAggiornaLiquidazioneDTO aggiornaLiquidazioneContributi(
			Long idUtente, String identitaDigitale, Long idSoggetto,
			LiquidazioneDTO liquidazione,Long idSoggettoBeneficiario) throws CSIException, SystemException,
			UnrecoverableException, LiquidazioneBilancioException {
		EsitoAggiornaLiquidazioneDTO esito = new EsitoAggiornaLiquidazioneDTO();
		esito.setEsito(false);
		String[] nameParameter = { "idUtente", "identitaDigitale","idSoggetto",
				"liquidazione", "liquidazione.idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idSoggetto, liquidazione, liquidazione.getIdProgetto());

		try {
			PbandiTAttoLiquidazioneVO attoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();

			PbandiTAttoLiquidazioneVO filtroAttoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
			filtroAttoLiquidazioneVO.setIdStatoAtto(bilancioManager
					.statoAttoBozza().getIdStatoAtto());
			filtroAttoLiquidazioneVO.setIdProgetto(beanUtil.transform(
					liquidazione.getIdProgetto(), BigDecimal.class));
			List<PbandiTAttoLiquidazioneVO> attiLiquidazioneInBozza = genericDAO
					.findListWhere(filtroAttoLiquidazioneVO);
			if (attiLiquidazioneInBozza.size() > 0
					&& attiLiquidazioneInBozza.size() == 1) {
				if (liquidazione.getIdAttoLiquidazione() == null) {
					throw new LiquidazioneBilancioException(
							"il clent non ha specificato id liquidazione, evito inserimento");
				}
			}
			if (liquidazione.getIdAttoLiquidazione() != null) {
				attoLiquidazioneVO = attiLiquidazioneInBozza.get(0);
			}
			BigDecimal idProgetto = new BigDecimal(liquidazione.getIdProgetto());
			BigDecimal idUtenteIns = new BigDecimal(idUtente);
			PbandiDModalitaAgevolazioneVO modalitaAgevolazioneVO = new PbandiDModalitaAgevolazioneVO();			
			
			// Jira PBANDI-2697: ora la la modalita agevolazione contributo � dinamica.			
			// Codice originale commentato.
			//modalitaAgevolazioneVO.setDescBreveModalitaAgevolaz(Constants.DESCRIZIONE_BREVE_MODALITA_AGEOLAZIONE_CONTRIBUTO);			
			ModAgevolazioneContributoDTO dto = getModAgevolazioneContributo(idUtente, identitaDigitale, liquidazione.getIdProgetto());
			modalitaAgevolazioneVO.setDescBreveModalitaAgevolaz(dto.getDescBreveModAgevolazioneContributo());
			
			modalitaAgevolazioneVO = genericDAO.findSingleWhere(
					modalitaAgevolazioneVO);
			liquidazione.setIdModalitaAgevolazione(beanUtil.transform(
					modalitaAgevolazioneVO.getIdModalitaAgevolazione(), Long.class));
			String codCausale = new StringTokenizer(
					liquidazione.getCodCausaleLiquidazione(), "@").nextToken();
			DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, codCausale);
			liquidazione.setIdCausaleLiquidazione(beanUtil.transform(
					decodificaVO.getId(), Long.class));

			Date sysdate = DateUtil.getSysdate();
			PbandiTBeneficiarioBilancioVO beneficiarioBilancioVO = new PbandiTBeneficiarioBilancioVO();
			BigDecimal idDatiPagamentoAtto=null;
			if(attoLiquidazioneVO.getIdBeneficiarioBilancio()==null){
//				Al momento dell'inserimento dell'atto di liquidazione in bozza, il sistema deve:
//					1)  Cercare se per lo stesso progetto esiste gi� un'altro atto di liquidazione
				BeneficiarioBilancioVO beneficiarioBilancio = getBeneficiarioBilancioByIdProgetto(idProgetto);
				if(beneficiarioBilancio!=null)
					idDatiPagamentoAtto=beneficiarioBilancio.getIdDatiPagamentoAtto();
//
//					altrimenti
//
//					2)  Cercare se per lo stesso id_soggetto esiste gi� un'altro atto di liquidazione
				PbandiTDatiPagamentoAttoVO pbandiTDatiPagamentoAttoVO= new PbandiTDatiPagamentoAttoVO();
				if(isNull(beneficiarioBilancio)){
					beneficiarioBilancio = getBeneficiarioBilancioByIdSoggetto(new BigDecimal(idSoggettoBeneficiario));
					if(beneficiarioBilancio!=null)
						idDatiPagamentoAtto=beneficiarioBilancio.getIdDatiPagamentoAtto();
				}
				
				if(!isNull(beneficiarioBilancio)){
					beanUtil.valueCopy(beneficiarioBilancio,beneficiarioBilancioVO );
					
				}else{
					BeneficiarioProgettoVO beneficiarioVO = new BeneficiarioProgettoVO();
					beneficiarioVO.setIdProgetto(idProgetto);
					BeneficiarioProgettoVO beneficiario = genericDAO
							.findSingleWhere(Condition.filterBy(beneficiarioVO).and(
									Condition.validOnly(BeneficiarioProgettoVO.class)));
					
					beneficiarioBilancioVO = beanUtil.transform(beneficiario, PbandiTBeneficiarioBilancioVO.class);
					SedeProgettoVO sede = sedeManager.findSedeLegale(idProgetto.longValue(), NumberUtil.toLong(beneficiario.getIdSoggetto()));
					if(sede!=null){
						beneficiarioBilancioVO.setIdSede(sede.getIdSede());
						
						
						
						PbandiRSoggettoProgettoVO pbandiRSoggettoProgettoVO =new PbandiRSoggettoProgettoVO();
						pbandiRSoggettoProgettoVO.setIdProgetto(idProgetto);
						pbandiRSoggettoProgettoVO.setIdSoggetto(new BigDecimal(idSoggettoBeneficiario));
						List<PbandiRSoggettoProgettoVO>listSoggProgg = genericDAO.findListWhere(pbandiRSoggettoProgettoVO);
						if(!isEmpty(listSoggProgg)){
							pbandiRSoggettoProgettoVO = listSoggProgg.get(0);
						}
						
						PbandiRSoggProgettoSedeVO pbandiRSoggProgettoSedeVO=new PbandiRSoggProgettoSedeVO();
						pbandiRSoggProgettoSedeVO.setIdSede(sede.getIdSede());
						pbandiRSoggProgettoSedeVO.setProgrSoggettoProgettoSede(pbandiRSoggettoProgettoVO.getProgrSoggettoProgetto());
						
						List<PbandiRSoggProgettoSedeVO> listSedi = genericDAO.findListWhere(pbandiRSoggProgettoSedeVO);
						if(!isEmpty(listSedi)){
							pbandiRSoggProgettoSedeVO = listSedi.get(0);
							beneficiarioBilancioVO.setIdIndirizzo(pbandiRSoggProgettoSedeVO.getIdIndirizzo());
							beneficiarioBilancioVO.setIdRecapiti(pbandiRSoggProgettoSedeVO.getIdRecapiti());
						}
					}
					beanUtil.valueCopy(beneficiarioBilancio,beneficiarioBilancioVO );
					beneficiarioBilancioVO.setIdUtenteIns(new BigDecimal(idUtente));
					beneficiarioBilancioVO = genericDAO.insert(beneficiarioBilancioVO);
				}
				
				
				// Se per il benef trovato esistono dati pagamento atto, ne clono i dati ma inserisco un nuovo record
				if(idDatiPagamentoAtto!=null){
					pbandiTDatiPagamentoAttoVO.setIdDatiPagamentoAtto(idDatiPagamentoAtto);
					pbandiTDatiPagamentoAttoVO=genericDAO.findSingleWhere(pbandiTDatiPagamentoAttoVO);
					
				}
				pbandiTDatiPagamentoAttoVO.setIdDatiPagamentoAtto(null);
				pbandiTDatiPagamentoAttoVO.setIdUtenteIns(new BigDecimal(idUtente));
				pbandiTDatiPagamentoAttoVO= genericDAO.insert(pbandiTDatiPagamentoAttoVO);
				idDatiPagamentoAtto=pbandiTDatiPagamentoAttoVO.getIdDatiPagamentoAtto();
				
			} else {
				beneficiarioBilancioVO.setIdBeneficiarioBilancio(attoLiquidazioneVO.getIdBeneficiarioBilancio());
				if(attoLiquidazioneVO.getIdDatiPagamentoAtto()!=null)
					idDatiPagamentoAtto=attoLiquidazioneVO.getIdDatiPagamentoAtto();
			}
			BigDecimal importoLiquidazioni = null;
			if (liquidazione.getIdAttoLiquidazione() == null) {
				importoLiquidazioni = getImportoLiquidazioniPerModalitaAgevolazionePerInserimento(
						beanUtil.transform(idProgetto, Long.class), liquidazione);
			} else {
				importoLiquidazioni = getImportoLiquidazioniPerModalitaAgevolazione(
						beanUtil.transform(idProgetto, Long.class), liquidazione);
			}
			boolean error = false;
			if (NumberUtil.compare(importoLiquidazioni, contoEconomicoManager
						.getQuotaImportoAgevolato(idProgetto,
								modalitaAgevolazioneVO.getDescBreveModalitaAgevolaz())) > 0) {
				esito.setMessage(ErrorConstants.ERRORE_BILANCIO_SOMMA_LIQUIDAZ_AL_NETTO_REC_MAGG_IMPORTO_AGEVOLATO);
				/*
				 * In questo caso l' errore non e' bloccante
				 */
				error = false;
			}
			
			logger.info("\n\nattoLiquidazioneVO.getIdAttoLiquidazione() = "+attoLiquidazioneVO.getIdAttoLiquidazione()+"\n\n");
			if (attoLiquidazioneVO.getIdAttoLiquidazione() == null) {
				Long idAliquota = this.getIdAliquotaRitenuta(idUtente, identitaDigitale, idProgetto.longValue());
				if (attoLiquidazioneVO != null && idAliquota != null) {
					attoLiquidazioneVO.setIdAliquotaRitenuta(new BigDecimal(idAliquota));
				}
				logger.info("\n\nidAliquota = "+idAliquota+"\n\n");
			}
			
			
			if(!error){
				attoLiquidazioneVO = salvaLiquidazione(idProgetto,
						beanUtil.transform(idSoggetto, BigDecimal.class),
						idUtenteIns, sysdate, liquidazione, attoLiquidazioneVO,
						beneficiarioBilancioVO.getIdBeneficiarioBilancio(),
						idDatiPagamentoAtto);
				esito.setLiquidazione(beanUtil.transform(attoLiquidazioneVO,
						LiquidazioneDTO.class));
				esito.setEsito(true);
			}	
		} catch (Exception ex) {
				throw new UnrecoverableException(ex.getMessage(), ex);
		} finally {
		}
		return esito;
	}

	
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoAggiornaRiepilogoFondiDTO aggiornaRiepilogoFondiPostAllineamentoImpegni(

			Long idUtente,

			String identitaDigitale,

			Long idProgetto,

			Long idAttoLiquidazione,

			RipartizioneImpegniLiquidazioneDTO[] ripartizioneImpegniLiquidazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException

	{
		EsitoAggiornaRiepilogoFondiDTO esito = new EsitoAggiornaRiepilogoFondiDTO();
		esito.setRipartizioneImpegniLiquidazione(ripartizioneImpegniLiquidazione);

		try {
			
			esito.setEsito(false);
			String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto",
					"idAttoLiquidazione", "ripartizioneImpegniLiquidazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto, idAttoLiquidazione, ripartizioneImpegniLiquidazione);

			PbandiRLiquidazioneVO liquidazioniDaCancellare = new PbandiRLiquidazioneVO();
			liquidazioniDaCancellare.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
			genericDAO.deleteWhere(Condition
					.filterBy(liquidazioniDaCancellare));
			
			/*
			 * Ricerco i dati del progetto
			 */
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			
			PbandiDStatoLiquidazioneVO statoLiquidazioneVO = new PbandiDStatoLiquidazioneVO();
			statoLiquidazioneVO.setDescBreveStatoLiquidazione(DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_EMESSO);
			statoLiquidazioneVO = genericDAO.findSingleWhere(statoLiquidazioneVO);
			for (RipartizioneImpegniLiquidazioneDTO ripartizione : ripartizioneImpegniLiquidazione) {
				PbandiRLiquidazioneVO liquidazione = new PbandiRLiquidazioneVO();
				liquidazione.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
				liquidazione.setIdImpegno(beanUtil.transform(ripartizione.getIdImpegno(), BigDecimal.class));
				liquidazione.setCigLiquidazione(ripartizione.getCig());
				liquidazione.setCupLiquidazione(progettoVO.getCup());
				liquidazione.setAnnoEsercizio(new BigDecimal(DateUtil.getAnno()));
				liquidazione.setIdStatoLiquidazione(statoLiquidazioneVO.getIdStatoLiquidazione());
				// inserisci solo quelli con importo da liquidare > 0
				if (NumberUtil.compare(ripartizione.getImportoDaLiquidare(), new Double(0)) > 0) {
					liquidazione.setImportoLiquidato(beanUtil.transform(ripartizione.getImportoDaLiquidare(), BigDecimal.class));
					Date sysdate = DateUtil.getSysdate();
					liquidazione.setDtInserimento(sysdate);
					liquidazione.setIdUtenteIns(beanUtil.transform(idUtente, BigDecimal.class));
					genericDAO.insert(liquidazione);
				}	
			}	
			esito.setEsito(true);
		} catch (Exception ex) {
			throw new UnrecoverableException(ex.getMessage(), ex);
		}  
		return  esito;
	}

	
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiRiepilogoFondiDTO getRiepilogoFondiPostAllineamentoImpegni(

			Long idUtente,

			String identitaDigitale,

			Long idProgetto,

			Long idAttoLiquidazione,

			FonteFinanziariaDTO[] fontiFinanziarie,

			RipartizioneImpegniLiquidazioneDTO[] ripartizioneImpegniLiquidazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException

	{
		
		EsitoLeggiRiepilogoFondiDTO esito = new EsitoLeggiRiepilogoFondiDTO();
		ArrayList<Long> fontiFinanziarieImportiSpalmati = new ArrayList<Long>();
		for (int progrFonte = 0; progrFonte < fontiFinanziarie.length; progrFonte++) {
			FonteFinanziariaDTO fonteFinanziaria = beanUtil.transform(fontiFinanziarie[progrFonte], FonteFinanziariaDTO.class);
			Long idFonteFinanziaria = fonteFinanziaria.getIdFonteFinanziaria();
			for (int progrRiepilogo = 0; progrRiepilogo < ripartizioneImpegniLiquidazione.length; progrRiepilogo++) {
				RipartizioneImpegniLiquidazioneDTO ripartizione = beanUtil.transform(ripartizioneImpegniLiquidazione[progrRiepilogo], RipartizioneImpegniLiquidazioneDTO.class);
				if((ripartizione.getIdFonteFinanziaria().longValue() == idFonteFinanziaria.longValue())  && (!fontiFinanziarieImportiSpalmati.contains(ripartizione.getIdFonteFinanziaria()))){
					if (NumberUtil.compare(ripartizione.getDisponibilitaResidua(), fonteFinanziaria.getImportoRispTotale()) >= 0){
						ripartizione.setDisponibilitaResidua(NumberUtil.subtract(ripartizione.getDisponibilitaResidua(), fonteFinanziaria.getImportoRispTotale()));
						ripartizione.setImportoDaLiquidare(fonteFinanziaria.getImportoRispTotale());
						fontiFinanziarieImportiSpalmati.add(ripartizione.getIdFonteFinanziaria());
					} else {
						ripartizione.setImportoDaLiquidare(ripartizione.getDisponibilitaResidua());
						ripartizione.setDisponibilitaResidua(0D);
					}
					fonteFinanziaria.setImportoRispTotale(NumberUtil.subtract(fonteFinanziaria.getImportoRispTotale(), ripartizione.getImportoDaLiquidare()));
				} else if((ripartizione.getIdFonteFinanziaria().longValue() == idFonteFinanziaria.longValue()) && fontiFinanziarieImportiSpalmati.contains(ripartizione.getIdFonteFinanziaria())){
					ripartizione.setImportoDaLiquidare(0D);
				}
				ripartizioneImpegniLiquidazione[progrRiepilogo].setImportoDaLiquidare(ripartizione.getImportoDaLiquidare());
			}
			
		}
		
		
		
		esito.setEsito(true);
		esito.setFontiFinanziarie(fontiFinanziarie);
		esito.setRipartizioneImpegniLiquidazione(ripartizioneImpegniLiquidazione);
		return esito;
	}

		
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ImpegnoAllineatoDTO[] getInfoImpegniPostAllineamento(

			Long idUtente,

			String identitaDigitale,

			Long[] impegni

			)
	{
		
		List<PbandiTImpegnoVO> filtri = new ArrayList<PbandiTImpegnoVO>();

		for (Long idImpegno : impegni) {
			PbandiTImpegnoVO filtroVO = new PbandiTImpegnoVO();
			filtroVO.setIdImpegno(beanUtil.transform(idImpegno,
					BigDecimal.class));
			filtri.add(filtroVO);
		}
		ArrayList<ImpegnoAllineatoDTO> impegniAllineati = new ArrayList<ImpegnoAllineatoDTO>();
		List<PbandiTImpegnoVO> result = genericDAO.findListWhere(filtri);
		for (PbandiTImpegnoVO impegnoVO : result) {
			ImpegnoAllineatoDTO impegnoAllineato = beanUtil.transform(
					impegnoVO, ImpegnoAllineatoDTO.class,
					new HashMap<String, String>() {
						{
							put("idImpegno", "idImpegno");
							put("disponibilitaLiquidare","disponibilitaResidua");
							put("cigImpegno","cigImpegno");
						}
					});
			impegniAllineati.add(impegnoAllineato);
		}
		return impegniAllineati
				.toArray(new ImpegnoAllineatoDTO[impegniAllineati.size()]);
	}

	
	/**
	 * 
	 * @param idProgetto
	 * @param idUtenteIns
	 * @param sysdate
	 * @param liquidazione
	 * @param idBeneficiarioBilancio
	 * @param idDatiPagamentoAtto
	 * @return
	 * @throws LiquidazioneBilancioException
	 */
	private PbandiTAttoLiquidazioneVO salvaLiquidazione(BigDecimal idProgetto,BigDecimal idSoggetto, 
			BigDecimal idUtenteIns, Date sysdate,
			LiquidazioneDTO liquidazione, PbandiTAttoLiquidazioneVO attoLiquidazioneVO,
			BigDecimal idBeneficiarioBilancio,
			BigDecimal idDatiPagamentoAtto) throws LiquidazioneBilancioException {
		
		attoLiquidazioneVO.setIdProgetto(idProgetto);
		BigDecimal idStatoAtto = bilancioManager.statoAttoBozza().getIdStatoAtto();
		attoLiquidazioneVO.setIdStatoAtto(idStatoAtto);
		attoLiquidazioneVO.setDtEmissioneAtto(sysdate);
		attoLiquidazioneVO.setAnnoAtto(beanUtil.transform(DateUtil
				.getAnno(beanUtil.transform(sysdate, String.class)), BigDecimal.class));
		attoLiquidazioneVO.setIdBeneficiarioBilancio(idBeneficiarioBilancio);
		attoLiquidazioneVO.setIdUtenteIns(idUtenteIns);
		attoLiquidazioneVO.setIdProgetto(idProgetto); 
		attoLiquidazioneVO.setNumeroAtto(new String("0"));
		attoLiquidazioneVO.setIdAttoLiquidazione(beanUtil.transform(liquidazione.getIdAttoLiquidazione(), BigDecimal.class)); 
		attoLiquidazioneVO.setDtInserimento(sysdate);
		attoLiquidazioneVO.setDtEmissioneAtto(sysdate);
		attoLiquidazioneVO.setDtAggiornamento(sysdate);
		attoLiquidazioneVO.setIdStatoAtto(idStatoAtto);
		attoLiquidazioneVO.setIdModalitaAgevolazione(beanUtil.transform(liquidazione.getIdModalitaAgevolazione(), BigDecimal.class));
		attoLiquidazioneVO.setIdCausaleErogazione(beanUtil.transform(liquidazione.getIdCausaleLiquidazione(), BigDecimal.class));
		attoLiquidazioneVO.setImportoAtto(beanUtil.transform(liquidazione.getImportoLiquidazioneEffettiva(), BigDecimal.class));
		if (liquidazione.getIdAttoLiquidazione() == null) {
			attoLiquidazioneVO.setFlagAllegatiDichiarazione("N");
			attoLiquidazioneVO.setFlagAllegatiDocGiustificat("N");
			attoLiquidazioneVO.setFlagAllegatiEstrattoProv("N");
			attoLiquidazioneVO.setFlagAllegatiFatture("N");
		}	
		if(idDatiPagamentoAtto!=null)
			attoLiquidazioneVO.setIdDatiPagamentoAtto(idDatiPagamentoAtto);
		
		
		if(attoLiquidazioneVO.getIdAttoLiquidazione()==null){
			SoggettoSettoreEnteCompetenzaVO filtroSettoreEnte = new SoggettoSettoreEnteCompetenzaVO();
			filtroSettoreEnte.setDescBreveTipoEnteCompetenz("ADG");
			filtroSettoreEnte.setIdSoggetto(idSoggetto);
			List<SoggettoSettoreEnteCompetenzaVO> elencoSoggettoEnteCompetenzaVOs = (List<SoggettoSettoreEnteCompetenzaVO>)
			genericDAO.findListWhere(filtroSettoreEnte);
			attoLiquidazioneVO.setIdSettoreEnte(elencoSoggettoEnteCompetenzaVOs.get(0).getIdSettoreEnte());
		}
		try {
			attoLiquidazioneVO.setIdUtenteAgg(idUtenteIns);
			genericDAO.insertOrUpdateExisting(attoLiquidazioneVO);
		} catch (Exception e) {
			logger.error("Errore nell'inserimento della liquidazione in BOZZA", e);
			LiquidazioneBilancioException re = new LiquidazioneBilancioException(ERRORE_SERVER);
			throw re;
		}
		return attoLiquidazioneVO;
	}


	//TODO refactoring con gestione erogazioni
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

	// CDU-105-V03: 
	// - modificato il metodo per far restituire atti in stato BOZZA, IN LAVORAZIONE e BLOCCATO
	//   (prima restituiva solo atti in stato BOZZA).
	// - se l'atto � in stato IN LAVORAZIONE, si invoca la leggiStatoElaborazioneDocumento() di Contabilia
	//   per vedere se lo stato � cambiato.
	public EsitoLeggiAttoLiquidazioneDTO getAttoLiquidazioneContributi(
			Long idUtente, String identitaDigitale, Long idProgetto,Long idFormaGiuridica,
			Long idDimensioneImpresa, Long idBandoLinea)
			throws CSIException, SystemException, UnrecoverableException,
			LiquidazioneBilancioException {
		EsitoLeggiAttoLiquidazioneDTO esito = new EsitoLeggiAttoLiquidazioneDTO();
		esito.setEsito(false);
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idFormaGiuridica", "idDimensioneImpresa",
				"idBandoLinea" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale, idProgetto);

		try {
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO filtroAttoLiquidazioneVO = new it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO();
			// CDU-105-V03: inizio
			// commentato poich� ho inserito direttamente nella select la condizione "id_stato_atto in (1,9,10)".
			//filtroAttoLiquidazioneVO.setIdStatoAtto(bilancioManager.statoAttoBozza().getIdStatoAtto());
			// CDU-105-V03: fine
			filtroAttoLiquidazioneVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO> attiLiquidazioneInBozza= genericDAO.findListWhere(filtroAttoLiquidazioneVO);
			if(attiLiquidazioneInBozza.size()>0 && attiLiquidazioneInBozza.size()==1){
				
				it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO  attoLiquidazione = attiLiquidazioneInBozza.get(0);
				
				// CDU-105-V03: inizio
				// Se l'atto � in stato IN LAVORAZIONE, si invoca la leggiStatoElaborazioneDocumento() di Contabilia
				// per vedere se lo stato � cambiato.
				attoLiquidazione = this.gestioneAttoInLavorazione(attoLiquidazione, idProgetto, 
						//esito.getMessage(), 
						idUtente, identitaDigitale);
				if (attoLiquidazione == null) {
					// L'atto � passato da IN LAVORAZIONE ad EMESSO.
					LiquidazioneDTO liquidazione = new LiquidazioneDTO();
					liquidazione.setImportoLiquidazioneEffettiva(new Double(0));
					liquidazione.setPercLiquidazioneEffettiva(new Double(0));
					esito.setLiquidazione(liquidazione);
					return esito;
				}
				// Se l'atto � in stato BOZZA, potrebbe essere perch� rifiutato da Contabilia.
				// Recuper l'eventuale msg di errore di Contabilia, per visualizzarlo a video.
				String msgErroreContabilia = this.gestioneAttoBozza(attoLiquidazione);
				esito.setMessage(msgErroreContabilia);
				// CDU-105-V03: fine
				
				esito.setIdAttoLiquidazione(beanUtil.transform(attoLiquidazione.getIdAttoLiquidazione(), Long.class));
				
				LiquidazioneDTO  liquidazioneDTO = beanUtil.transform(attoLiquidazione,
						LiquidazioneDTO.class,
						new HashMap<String, String>() {
							{
								put("idAttoLiquidazione", "idAttoLiquidazione");
								put("idCausaleErogazione","idCausaleLiquidazione");
								put("importoAtto","importoLiquidazioneEffettiva");
								put("idModalitaAgevolazione","idModalitaAgevolazione");
								put("idProgetto", "idProgetto");
								put("idSoggetto", "idSoggetto");
								put("idSettoreEnte", "idSettoreEnte");
								// CDU-105-V03: inizio
								put("idStatoAtto", "idStatoAtto");
								put("numeroDocumentoSpesa", "numeroDocumentoSpesa");
								// CDU-105-V03: fine
							}
						});

				DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE,
						beanUtil.transform(liquidazioneDTO.getIdCausaleLiquidazione(), Long.class));
				
				BandoLineaVO bandoLineaVO = new BandoLineaVO();
				bandoLineaVO.setProgrBandoLineaIntervento(NumberUtil
						.toBigDecimal(idBandoLinea));
				bandoLineaVO = genericDAO.findSingleWhere(bandoLineaVO);

				PbandiRBandoCausaleErogazVO bandiRBandoCausaleErogazVO = getPBandiRBandoCausaliErogazioniPerBandoEFormaGiuridicaECausale(
						idFormaGiuridica, idDimensioneImpresa, bandoLineaVO,liquidazioneDTO.getIdCausaleLiquidazione());

				BigDecimal percErogazione = bandiRBandoCausaleErogazVO
						.getPercErogazione();
				BigDecimal percLimite = bandiRBandoCausaleErogazVO.getPercLimite();
				if (percErogazione == null)
					percErogazione = new BigDecimal(0);
				if (percLimite == null)
					percLimite = new BigDecimal(0);
				
				liquidazioneDTO.setCodCausaleLiquidazione(decodificaVO
						.getDescrizioneBreve()
						+ "@"
						+ percErogazione
						+ "@"
						+ percLimite);
				//NTH costruire la stringa a FE
				if(attoLiquidazione.getIdAttoLiquidazione()!=null){
					liquidazioneDTO.setDescStatoAtto(bilancioManager.statoAttoBozza().getDescStatoAtto() + " del " + attoLiquidazione.getDtEmissioneAtto().toString());
				} else {
					liquidazioneDTO.setDescStatoAtto("Nuova");
				}
				// Stato liquidazione=StatoAtto.descStatoAtto || ' del '  || AttoDiLiquidazione.dataAtto

				esito.setLiquidazione(liquidazioneDTO);
				esito.setEsito(true);
			} else if(attiLiquidazioneInBozza.size()>1){
				//TODO aggiungere messaggio in costants
				String message = "Trovati pi� atti in stato bozza"; 
				esito.setEsito(true);
				logger.error(message);
				throw new LiquidazioneBilancioException(message);
			} else { // inserimento
				LiquidazioneDTO liquidazione = new LiquidazioneDTO();
				liquidazione.setImportoLiquidazioneEffettiva(new Double(0));
				liquidazione.setPercLiquidazioneEffettiva(new Double(0));
				esito.setLiquidazione(liquidazione);
			}
		} catch (Exception e) {
			logger.error("Errore nel recupero della Liquidazione", e);
			throw new LiquidazioneBilancioException(e.getMessage());
		}	
		return esito;
	}
	
	// CDU-105-V03
	// Se l'atto � in stato IN LAVORAZIONE, si invoca la leggiStatoElaborazioneDocumento() di Contabilia
	// per vedere se lo stato � cambiato.
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO gestioneAttoInLavorazione(
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO  attoLiquidazione,
			Long idProgetto, 
//			String msgErroreContabilia,
			Long idUtente, String identitaDigitale) {
		
		Integer tempIdStatoAtto = new Integer (attoLiquidazione.getIdStatoAtto().intValue());
		String idStatoAttoIniziale = tempIdStatoAtto.toString();
		logger.info("gestioneAttoInLavorazione(): IdStatoAtto iniziale = "+idStatoAttoIniziale);
				
		if (Constants.ID_STATO_ATTO_IN_LAVORAZIONE.equalsIgnoreCase(idStatoAttoIniziale)) {
			logger.info("gestioneAttoInLavorazione(): IdStatoAtto iniziale = IN_LAVORAZIONE");
			InputLeggiStatoElaborazioneDocDTO input = new InputLeggiStatoElaborazioneDocDTO();
			input.setIdAttoLiquidazione(attoLiquidazione.getIdAttoLiquidazione().longValue());
			try {
				// Aggiorna lo stato dell'atto invocando Contabilia.
				logger.info("gestioneAttoInLavorazione(): invoco leggiStatoElaborazioneDocumento() di Contabilia");
				EsitoLeggiStatoElaborazioneDocDTO esito = null;
				esito = leggiStatoElaborazioneDoc(idUtente, identitaDigitale, input);
/*
				// Se l'atto � stato rifiutato da Contabilia, memorizzo il msg di errore,
				// per poterlo poi visualizzare a video.
				if (Constants.CONTABILIA_ESITO_FALLIMENTO.equalsIgnoreCase(esito.getEsitoContabilia()) &&
					Constants.CONTABILIA_STATO_ELAB_CONCLUSA.equalsIgnoreCase(esito.getStatoElaborazioneContabilia())) {
					logger.info("YYYYYYYYYYYYYY msgErroreContabilia = "+msgErroreContabilia );
					msgErroreContabilia = esito.getErrore();
				}
*/
				// Rilegge l'atto dal db di Bandi (stesso codice di getAttoLiquidazioneContributi).
				logger.info("gestioneAttoInLavorazione(): rileggo da db l'atto");
				it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO filtroAttoLiquidazioneVO = new it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO();
				filtroAttoLiquidazioneVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
				List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO> attiLiquidazioneInBozza= genericDAO.findListWhere(filtroAttoLiquidazioneVO);
				if(attiLiquidazioneInBozza.size() > 0 && attiLiquidazioneInBozza.size() == 1){
					// Sostituisco l'atto in input con la versione aggiornata appena letta.
					attoLiquidazione = attiLiquidazioneInBozza.get(0);
				} else { 
					// Se non trovo nulla, l'atto � passato da IN LAVORAZIONE ad EMESSO.
					return null;
				}
			} catch (Exception e) {
				return attoLiquidazione;
			}

		}
		logger.info("gestioneAttoInLavorazione(): IdStatoAtto fine = "+attoLiquidazione.getIdStatoAtto());
		return attoLiquidazione;
		// CDU-105-V03: fine
	}
	
	// CDU-105-V03
	// Se l'atto � in stato BOZZA, potrebbe essere dovuto ad un invio precedente rifiutato da Contabilia.
	// In questo caso recuper il msg di errore restituito da Contabilia, in modo che sia visualizzato a video.
	private String gestioneAttoBozza(
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio.AttoLiquidazioneVO  attoLiquidazione) {
		
		Integer tempIdStatoAtto = new Integer (attoLiquidazione.getIdStatoAtto().intValue());
		String idStatoAtto = tempIdStatoAtto.toString();
		logger.info("gestioneAttoBozza(): IdStatoAtto = "+idStatoAtto);

		String msgErroreContabilia = null;
		if (Constants.ID_STATO_ATTO_BOZZA.equalsIgnoreCase(idStatoAtto)) {
			logger.info("gestioneAttoBozza(): IdStatoAtto iniziale = BOZZA");
			InputLeggiStatoElaborazioneDocDTO input = new InputLeggiStatoElaborazioneDocDTO();
			input.setIdAttoLiquidazione(attoLiquidazione.getIdAttoLiquidazione().longValue());
			try {
				// Verifica se l'ultimo invio dell'atto a Contabilia � stato rifiutato.
				PbandiLLogStatoElabDocVO voLog = new PbandiLLogStatoElabDocVO();
				voLog.setIdAttoLiquidazione(attoLiquidazione.getIdAttoLiquidazione());
				voLog.setDescendentOrder("idChiamata");		// il primo record sar� quello inserito per ultimo.						
				List<PbandiLLogStatoElabDocVO> lista = genericDAO.findListWhere(voLog);
				if (lista.size() > 0) {
					logger.info("gestioneAttoBozza(): record PbandiLLogStatoElabDoc trovato: idChiamata = "+lista.get(0).getIdChiamata());
					msgErroreContabilia = lista.get(0).getErroreLeggiStato();
				}
			} catch (Exception e) {
				return "Errore nella ricerca del messaggio di errore";
			}

		}
		logger.info("gestioneAttoBozza(): msgErroreContabilia = "+msgErroreContabilia);
		return msgErroreContabilia;
		// CDU-105-V03: fine
	}
	
	private BigDecimal getImportoLiquidazioniPerModalitaAgevolazionePerInserimento(
			Long idProgetto, LiquidazioneDTO liquidazione) {
		PbandiTAttoLiquidazioneVO pbandiTLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
		pbandiTLiquidazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTLiquidazioneVO.setIdModalitaAgevolazione(new BigDecimal(
				liquidazione.getIdModalitaAgevolazione()));
		BigDecimal idStatoAttoBozza = bilancioManager.statoAttoBozza().getIdStatoAtto();
		List<PbandiTAttoLiquidazioneVO> list = genericDAO
				.findListWhere(pbandiTLiquidazioneVO);
		BigDecimal importo = new BigDecimal(0);
		if (list != null) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				PbandiTAttoLiquidazioneVO vo = (PbandiTAttoLiquidazioneVO) iter.next();
				if((idStatoAttoBozza.intValue()!=vo.getIdStatoAtto().intValue())){
					importo = NumberUtil.sum(importo, vo.getImportoAtto());
				}	
			}
		}


		BigDecimal totaleRecuperi = getTotaleRecuperiPerModalita(idProgetto,
				liquidazione.getIdModalitaAgevolazione());

		importo = NumberUtil.subtract(importo, totaleRecuperi);

		importo = NumberUtil.sum(importo,
				new BigDecimal(liquidazione.getImportoLiquidazioneEffettiva()));

		return importo;
	}
	
	
	private BigDecimal getImportoLiquidazioniPerModalitaAgevolazione(
			Long idProgetto, LiquidazioneDTO liquidazione) {
		PbandiTAttoLiquidazioneVO pbandiTLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
		pbandiTLiquidazioneVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTLiquidazioneVO.setIdModalitaAgevolazione(new BigDecimal(
				liquidazione.getIdModalitaAgevolazione()));
		List<PbandiTAttoLiquidazioneVO> list = genericDAO
				.findListWhere(pbandiTLiquidazioneVO);
		BigDecimal importo = new BigDecimal(0);
		BigDecimal idStatoAttoBozza = bilancioManager.statoAttoBozza().getIdStatoAtto();

		
		boolean isImportoCorrenteSommato = false;
		if (list != null) {
			Iterator<PbandiTAttoLiquidazioneVO> iter = list.iterator();
			while (iter.hasNext()) {
				PbandiTAttoLiquidazioneVO vo = (PbandiTAttoLiquidazioneVO) iter.next();
				if (vo.getIdAttoLiquidazione().equals(
						new BigDecimal(liquidazione.getIdAttoLiquidazione()))) {
					isImportoCorrenteSommato = true;
					importo = NumberUtil.sum(
							importo,
							new BigDecimal(liquidazione
									.getImportoLiquidazioneEffettiva()));
				} else {
					if((idStatoAttoBozza.intValue()!=vo.getIdStatoAtto().intValue()) ){
						importo = NumberUtil.sum(importo, vo.getImportoAtto());
					}
				}
			}
		}

		if (isImportoCorrenteSommato == false) {
			importo = NumberUtil.sum(importo,
					new BigDecimal(liquidazione.getImportoLiquidazioneEffettiva()));
		}

		BigDecimal totaleRecuperi = getTotaleRecuperiPerModalita(idProgetto,
								liquidazione.getIdModalitaAgevolazione());

		importo = NumberUtil.subtract(importo, totaleRecuperi);

		return importo;
	}
	//NTH merjare con funzione delle erogazioni
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiRiepilogoFondiDTO getRiepilogoFondi(	Long idUtente,
	String identitaDigitale,	Long idProgetto,	Long idAttoLiquidazione, Long progrBandoLineaIntervento)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException

	{
		 
		EsitoLeggiRiepilogoFondiDTO esito = new EsitoLeggiRiepilogoFondiDTO();
		esito.setEsito(false);
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idAttoLiquidazione"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale, idProgetto, idAttoLiquidazione);

		
		try {
			/*
			 * Ricerco i soggetti finanziatori(o fonti finanziarie)
			 */
			List<SoggettiFinanziatoriPerProgettoVO> soggettiFinanziatoriNonPrivati = contoEconomicoManager
			.getSoggettiFinanziatoriNonPrivatiAnnoCorrente(new BigDecimal(idProgetto));
			
			/*
			 * Verifico se esiste l' atto di liquidazione
			 */
			PbandiTAttoLiquidazioneVO filtroAttoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
			filtroAttoLiquidazioneVO.setIdStatoAtto(bilancioManager.statoAttoBozza().getIdStatoAtto());
			filtroAttoLiquidazioneVO.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
			filtroAttoLiquidazioneVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			List<PbandiTAttoLiquidazioneVO> attiLiquidazioneInBozza= genericDAO.findListWhere(filtroAttoLiquidazioneVO);
			if (ObjectUtil.isEmpty(attiLiquidazioneInBozza)){
				throw new LiquidazioneBilancioException("Errore: non trovati atto di liquidazione in bozza con id_atto_liquidazione = " + idAttoLiquidazione.toString());
			}
			
			BigDecimal importoAtto = attiLiquidazioneInBozza.get(0).getImportoAtto();
			ArrayList<FonteFinanziariaDTO> ripartizioneFonteFinanziaria = new ArrayList<FonteFinanziariaDTO>();
			BigDecimal totaleImportoSulleFontiTrovate = new BigDecimal(0D);
			/*
			 * Calcolo l'importo da liquidare su ogni singola fonte (o soggetto finanziatore) in base all' importo totale da liquidare (importo atto) 
			 */
			for(SoggettiFinanziatoriPerProgettoVO fonte: soggettiFinanziatoriNonPrivati){
				FonteFinanziariaDTO fonteDTO = new FonteFinanziariaDTO();
				fonteDTO.setDescFonteFinanziaria(fonte.getDescSoggFinanziatore());
				fonteDTO.setIdFonteFinanziaria(beanUtil.transform(fonte.getIdSoggettoFinanziatore(), Long.class));
				fonteDTO.setPercentuale(beanUtil.transform(fonte.getPercQuotaSoggFinanziatore(), Double.class));
				BigDecimal importo = NumberUtil.divide(NumberUtil.multiply(fonte.getPercQuotaSoggFinanziatore(), importoAtto), new BigDecimal(100));
				fonteDTO.setImporto(NumberUtil.toDouble(importo));
				totaleImportoSulleFontiTrovate = NumberUtil.sum(totaleImportoSulleFontiTrovate, importo);
				ripartizioneFonteFinanziaria.add(fonteDTO);
			}
			
			/*
			 * Poiche' le percentuali delle fonti devono essere riferite al 100% del contributo
			 * ricalcolo le percentuali rapportandoli al totale delle percentuali delle fonti considerate.
			 */
			BigDecimal importoTotaleCalcolatoPercentualiFonti = new BigDecimal(0);
			for(FonteFinanziariaDTO fonteRipartita: ripartizioneFonteFinanziaria){
				Double percentualeRispTotaleDaLiquidare = NumberUtil.toDouble(NumberUtil.divide(NumberUtil.multiply(beanUtil.transform(fonteRipartita.getImporto(), BigDecimal.class), new BigDecimal(100)), totaleImportoSulleFontiTrovate));
				fonteRipartita.setPercentualeRispTotale(percentualeRispTotaleDaLiquidare);
				BigDecimal importoRispTotale = NumberUtil.divide(NumberUtil.multiply(beanUtil.transform(fonteRipartita.getPercentualeRispTotale(), BigDecimal.class), importoAtto), new BigDecimal(100));
				fonteRipartita.setImportoRispTotale(NumberUtil.toDouble(importoRispTotale));
				importoTotaleCalcolatoPercentualiFonti = NumberUtil.sum(importoRispTotale, importoTotaleCalcolatoPercentualiFonti);
				
			}
			
			/*
			 * Poiche' a causa dell' arrotondamento nel calcolo degli importi riferiti alle percentuali delle
			 * fonti finanziarie, sull' ultima fonte si associata l'importo del residuo.
			 */
			BigDecimal differenzaArrotondamento = NumberUtil.subtract(importoAtto, importoTotaleCalcolatoPercentualiFonti);
			if(!ObjectUtil.isEmpty(ripartizioneFonteFinanziaria)) {
				int index = ripartizioneFonteFinanziaria.size()-1;
				FonteFinanziariaDTO ultimaFonte = ripartizioneFonteFinanziaria.get(index);
				Double importoUltimaFonte = ultimaFonte.getImportoRispTotale();
				ultimaFonte.setImportoRispTotale(NumberUtil.sum(importoUltimaFonte, NumberUtil.toDouble(differenzaArrotondamento)));
			}

			// SE esiste almeno 1 associazione impegni-progetto
			RipartizioneFontiImpegniProgettoVO filtroRipProgetto = new RipartizioneFontiImpegniProgettoVO();
			filtroRipProgetto.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));

			ConfigurationDTO configuration = configurationManager.getConfiguration();
			Long importoLiquidabileCentesimiBilancio = configuration.getMinImportoLiquidabileCentesimiBilancio();
			Long maxNumeroRecImpegni = configuration.getMaxNumRecordImpegni();
			Long maxNumeroRecImpegniAllineabiliBil = configuration.getMaxNumRecordImpegniAllineabiliBil();
			if(maxNumeroRecImpegniAllineabiliBil==null){
				maxNumeroRecImpegniAllineabiliBil = maxNumeroRecImpegni; // default
			}
			/*
			 * Ricerco gli impegni associati al progetto
			 */
			RipartizioneFontiImpegniProgettoVO filtroRipImporto = new RipartizioneFontiImpegniProgettoVO();

			BigDecimal importoMinimoDaLiquidare = importoLiquidabileCentesimiBilancio!=null?NumberUtil.divide(new BigDecimal(importoLiquidabileCentesimiBilancio), new BigDecimal(100)): new BigDecimal(0.50D); 
			filtroRipImporto.setDisponibilitaLiquidare(importoMinimoDaLiquidare);

			GreaterThanCondition<RipartizioneFontiImpegniProgettoVO> greaterThan = new GreaterThanCondition<RipartizioneFontiImpegniProgettoVO>(
					filtroRipImporto);

			AndCondition<RipartizioneFontiImpegniProgettoVO> condizioneComplessiva = new AndCondition<RipartizioneFontiImpegniProgettoVO>(
					greaterThan, new FilterCondition<RipartizioneFontiImpegniProgettoVO>(
							filtroRipProgetto));
			List<RipartizioneFontiImpegniProgettoVO> elencoRipartizioneFontiImpegniProgetto= genericDAO.findListWhere(condizioneComplessiva);
			ArrayList<RipartizioneImpegniLiquidazioneDTO> impegniLiquidazione = new ArrayList<RipartizioneImpegniLiquidazioneDTO>();
			if (!ObjectUtil.isEmpty(elencoRipartizioneFontiImpegniProgetto)){
				for (RipartizioneFontiImpegniProgettoVO ripartizioneFontiImpegniProgettoVO : elencoRipartizioneFontiImpegniProgetto) {
					RipartizioneImpegniLiquidazioneDTO ripartizione = new RipartizioneImpegniLiquidazioneDTO();
					ripartizione.setAnnoEsercizio(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getAnnoEsercizio(), Long.class));
					ripartizione.setNumeroImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getNumeroImpegno(), Long.class));
					ripartizione.setAnnoImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getAnnoImpegno(), Long.class));
					ripartizione.setIdImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getIdImpegno(), Long.class));
					ripartizione.setIdFonteFinanziaria(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getIdSoggettoFinanziatore(), Long.class));
					ripartizione.setDescFonteFinanziaria(ripartizioneFontiImpegniProgettoVO.getDescSoggFinanziatore());
					ripartizione.setCig(ripartizioneFontiImpegniProgettoVO.getCig());
					ripartizione.setCapitoloNumero(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getNumeroCapitolo(), String.class));
					// ripartizione.setImportoDaLiquidare(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getImportoAttualeImpegno(), Double.class));
					ripartizione.setDisponibilitaResidua(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getDisponibilitaLiquidare(), Double.class));
					ripartizione.setImportoImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getImportoAttualeImpegno(), Double.class));
					ripartizione.setImpegnoAnnoNumero(ripartizioneFontiImpegniProgettoVO.getAnnoImpegno() +  "/" + ripartizioneFontiImpegniProgettoVO.getNumeroImpegno());
					ripartizione.setAnnoPerente(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getAnnoPerente(), Long.class));
					ripartizione.setNumeroPerente(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getNumeroPerente(), Long.class));
					if (!StringUtil.isEmpty(ripartizioneFontiImpegniProgettoVO.getAnnoPerente()))
						ripartizione.setImpegnoPerenteAnnoNumero(ripartizioneFontiImpegniProgettoVO.getAnnoPerente() +  "/" + ripartizioneFontiImpegniProgettoVO.getNumeroPerente());
					impegniLiquidazione.add(ripartizione);
				}
				
			} 

			/*
			 * Ricerco gli impegni associati al bandolinea
			 */
			RipartizioneFontiImpegniBandoLineaVO filtroRipBandoLinea = new RipartizioneFontiImpegniBandoLineaVO();
			filtroRipBandoLinea.setProgrBandoLineaIntervento(beanUtil.transform(progrBandoLineaIntervento, BigDecimal.class));

			RipartizioneFontiImpegniBandoLineaVO filtroRipImportoBandoLinea = new RipartizioneFontiImpegniBandoLineaVO();
			filtroRipBandoLinea.setProgrBandoLineaIntervento(beanUtil.transform(progrBandoLineaIntervento, BigDecimal.class));
			filtroRipImportoBandoLinea.setDisponibilitaLiquidare(importoMinimoDaLiquidare);

			GreaterThanCondition<RipartizioneFontiImpegniBandoLineaVO> greaterConditionBandoLinea = new GreaterThanCondition<RipartizioneFontiImpegniBandoLineaVO>(
					filtroRipImportoBandoLinea);

			AndCondition<RipartizioneFontiImpegniBandoLineaVO> condizioneComplessivaBandoLinea = new AndCondition<RipartizioneFontiImpegniBandoLineaVO>(
					greaterConditionBandoLinea, new FilterCondition<RipartizioneFontiImpegniBandoLineaVO>(
							filtroRipBandoLinea));
			List<RipartizioneFontiImpegniBandoLineaVO> elencoRipartizioneFontiImpegniBandoLinea= genericDAO.findListWhere(condizioneComplessivaBandoLinea);
			if(elencoRipartizioneFontiImpegniBandoLinea!=null && elencoRipartizioneFontiImpegniBandoLinea.size()>0){
				for (RipartizioneFontiImpegniBandoLineaVO ripartizioneFontiImpegniProgettoVO : elencoRipartizioneFontiImpegniBandoLinea) {
					RipartizioneImpegniLiquidazioneDTO ripartizione = new RipartizioneImpegniLiquidazioneDTO();
					ripartizione.setAnnoEsercizio(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getAnnoEsercizio(), Long.class));
					ripartizione.setNumeroImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getNumeroImpegno(), Long.class));
					ripartizione.setAnnoImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getAnnoImpegno(), Long.class));
					ripartizione.setIdImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getIdImpegno(), Long.class));
					ripartizione.setIdFonteFinanziaria(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getIdSoggettoFinanziatore(), Long.class));
					ripartizione.setDescFonteFinanziaria(ripartizioneFontiImpegniProgettoVO.getDescSoggFinanziatore());
					ripartizione.setCig(ripartizioneFontiImpegniProgettoVO.getCig());
					ripartizione.setCapitoloNumero(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getNumeroCapitolo(), String.class));
					// ripartizione.setImportoDaLiquidare(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getImportoAttualeImpegno(), Double.class));
					ripartizione.setDisponibilitaResidua(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getDisponibilitaLiquidare(), Double.class));
					ripartizione.setImportoImpegno(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getImportoAttualeImpegno(), Double.class));
					ripartizione.setImpegnoAnnoNumero(ripartizioneFontiImpegniProgettoVO.getAnnoImpegno() +  "/" + ripartizioneFontiImpegniProgettoVO.getNumeroImpegno());
					ripartizione.setAnnoPerente(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getAnnoPerente(), Long.class));
					ripartizione.setNumeroPerente(beanUtil.transform(ripartizioneFontiImpegniProgettoVO.getNumeroPerente(), Long.class));
					if (!StringUtil.isEmpty(ripartizioneFontiImpegniProgettoVO.getAnnoPerente()))
						ripartizione.setImpegnoPerenteAnnoNumero(ripartizioneFontiImpegniProgettoVO.getAnnoPerente() +  "/" + ripartizioneFontiImpegniProgettoVO.getNumeroPerente());
					impegniLiquidazione.add(ripartizione);
				}
				
			} 
			
			if (ObjectUtil.isEmpty(impegniLiquidazione)) {
				esito.setMessage(ErrorConstants.ERRORE_BILANCIO_NON_TROVA_ASSOCIAZIONE_PROGETTO_IMPEGNO);
			} else {
				esito.setRipartizioneImpegniLiquidazione(impegniLiquidazione
						.toArray(new RipartizioneImpegniLiquidazioneDTO[impegniLiquidazione
																			.size()]));
				esito.setEsito(true);
			}
		
			esito.setMassimoNumeroImpegni(maxNumeroRecImpegni);
			esito.setMassimoNumeroImpegniAllineabiliBil(maxNumeroRecImpegniAllineabiliBil);
			esito.setFontiFinanziarie(ripartizioneFonteFinanziaria.toArray(new FonteFinanziariaDTO[ripartizioneFonteFinanziaria.size()]));
			return esito;
		} catch (Exception e) {
			logger.error("Errore nel recupero riepilogo Fondi", e);
			throw new LiquidazioneBilancioException(e.getMessage());
		}  
	}



	public CodiceDescrizioneDTO[] findCIGProgetto(Long idUtente,
			String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			LiquidazioneBilancioException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);


		ProceduraAggiudicazioneCIGVO conditionVO = new ProceduraAggiudicazioneCIGVO();
		conditionVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));

		List<ProceduraAggiudicazioneCIGVO> result = genericDAO
				.findListWhere(conditionVO);
		
		ArrayList<CodiceDescrizioneDTO> listaCIG = new ArrayList<CodiceDescrizioneDTO>();
		String indice = "0"; // indice fittizio
		if(result!=null && result.size()>0){
			for(ProceduraAggiudicazioneCIGVO pa: result){
				CodiceDescrizioneDTO cd = new CodiceDescrizioneDTO();
				cd.setCodice(indice);
				cd.setDescrizione(pa.getCigProcAgg());
				listaCIG.add(cd);
			}
		}	
		return listaCIG.toArray(new CodiceDescrizioneDTO[listaCIG.size()]);	
	}
	
	public BeneficiarioBilancioDTO findBeneficiario(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idAttoLiquidazione)
			throws CSIException, SystemException, UnrecoverableException,
			LiquidazioneBilancioException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idAttoLiquidazione"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale, idProgetto, idAttoLiquidazione);
		
		BeneficiarioBilancioDTO ret=null;
		//// 
		StringBuilder sb=new StringBuilder("\n\n\n\n\n\n\n");
		sb.append("cerco il beneficiario con idAttoLiquidazione: "+idAttoLiquidazione);
		sb.append(" e idProgetto): "+idProgetto);
	
		////
		
		BeneficiarioBilancioVO filtro=new BeneficiarioBilancioVO();
	    filtro.setIdAttoLiquidazione(new BigDecimal(idAttoLiquidazione));
	    List<BeneficiarioBilancioVO> list = genericDAO.findListWhere(filtro);	
	    if(!isEmpty(list)) {
	    	BeneficiarioBilancioVO beneficiarioBilancioVO = list.get(0);
	    	
	    	caricaDatiPagamento( beneficiarioBilancioVO);
	    	
	    	ret = beanUtil.transform(beneficiarioBilancioVO, BeneficiarioBilancioDTO.class);
	    	
	    	
	    	/*
	    	 * FIX PBandi-2137.
	    	 * Gestione della sede secondaria. Se l'idIndirizzo della sede principale (presente nella
	    	 * tabella PBandiTBeneficiarioBilancio) e' diverso dall' idIndirizzoSedeSecondaria (presente
	    	 * nella tabella PBandiTDatiPagamentoAtto) allora devo restituire i dati della
	    	 * sede secondaria
	    	 */
	    	BigDecimal idIndirizzoSedePrincipale = beneficiarioBilancioVO.getIdIndirizzo();
	    	BigDecimal idIndirizzoSedeSecondaria =beneficiarioBilancioVO.getIdIndirizzoSedeSecondaria();
	    	if (idIndirizzoSedeSecondaria != null) {
	    		if (idIndirizzoSedePrincipale == null || idIndirizzoSedePrincipale.compareTo(idIndirizzoSedeSecondaria) != 0) {	
	    			/*
	    			 * Recupero i dati dell' indirizzo della sede secondaria
	    			 */
	    			IndirizzoVO filtroIndirizzoSedeSecondaria = new IndirizzoVO();
	    			filtroIndirizzoSedeSecondaria.setIdIndirizzo(idIndirizzoSedeSecondaria);
	    			List<IndirizzoVO> indirizzi = genericDAO.findListWhere(filtroIndirizzoSedeSecondaria);
	    			if (!ObjectUtil.isEmpty(indirizzi)) {
	    				IndirizzoVO indirizzoSedeSecondaria = indirizzi.get(0);
	    				String indirizzo = indirizzoSedeSecondaria.getDescIndirizzo();
	    				String cap = indirizzoSedeSecondaria.getCap();
	    				BigDecimal idComuneSede = indirizzoSedeSecondaria.getIdComune();
	    				BigDecimal idComuneSedeEstero = indirizzoSedeSecondaria.getIdComuneEstero();
	    				BigDecimal idStatoSede = indirizzoSedeSecondaria.getIdNazione();
	    				BigDecimal idProvinciaSede = indirizzoSedeSecondaria.getIdProvincia();
	    				BigDecimal idSede = beneficiarioBilancioVO.getIdSedeSecondaria();
	    				String descSede = indirizzoSedeSecondaria.getDescIndirizzo() == null ? "" : indirizzoSedeSecondaria.getDescIndirizzo();
	    				descSede = indirizzoSedeSecondaria.getCap() == null ? descSede : descSede + " - " + indirizzoSedeSecondaria.getCap();
	    				descSede = indirizzoSedeSecondaria.getDescComune() == null ? descSede + " " : descSede + " - " + indirizzoSedeSecondaria.getDescComune() + " ";
	    				descSede = indirizzoSedeSecondaria.getSiglaProvincia() == null ? descSede : descSede + " (" + indirizzoSedeSecondaria.getSiglaProvincia() + ")";
	    				descSede = indirizzoSedeSecondaria.getDescComuneEstero() == null ? descSede : descSede + " - " + indirizzoSedeSecondaria.getDescComuneEstero();
	    				if (indirizzoSedeSecondaria.getDescNazione() != null && !indirizzoSedeSecondaria.getDescNazione().equalsIgnoreCase("ITALIA") ) {
	    					descSede = descSede + " - " + indirizzoSedeSecondaria.getDescNazione();
	    				}
	    				ret.setCab(cap);
	    				ret.setIndirizzo(indirizzo);
	    				ret.setIdComuneSede(NumberUtil.toLong(idComuneSede));
	    				ret.setIdComuneSedeEstero(NumberUtil.toLong(idComuneSedeEstero));
	    				ret.setIdStatoSede(NumberUtil.toLong(idStatoSede));
	    				ret.setIdProvinciaSede(NumberUtil.toLong(idProvinciaSede));
	    				ret.setIdSede(NumberUtil.toLong(idSede));
	    				ret.setSede(descSede);
	    				ret.setIdIndirizzo(NumberUtil.toLong(idIndirizzoSedeSecondaria));
	    				
	    				
	    				if (idIndirizzoSedePrincipale != null) {
		    				/*
		    				 * Recupero i dati dell' indirizzo della sede principale
		    				 */
		    				IndirizzoVO filtroIndirizzoSedePrincipale = new IndirizzoVO();
		    				filtroIndirizzoSedePrincipale.setIdIndirizzo(idIndirizzoSedePrincipale);
		    				List<IndirizzoVO> indirizziSedePrincipale = genericDAO.findListWhere(filtroIndirizzoSedePrincipale);
		    				if (!ObjectUtil.isEmpty(indirizziSedePrincipale)) {
		    					IndirizzoVO indirizzoSedePrincipale = indirizziSedePrincipale.get(0);
		    					SedeBilancioDTO sedePrincipale = new SedeBilancioDTO();
		    					sedePrincipale.setCap(indirizzoSedePrincipale.getCap());
		    					if (indirizzoSedePrincipale.getDescNazione() != null && !indirizzoSedePrincipale.getDescNazione().equalsIgnoreCase("ITALIA")) {
		    						sedePrincipale.setDescComune(indirizzoSedePrincipale.getDescComuneEstero());
		    						sedePrincipale.setDescProvincia("EE");
		    					} else {
		    						sedePrincipale.setDescComune(indirizzoSedePrincipale.getDescComune());
		    						sedePrincipale.setDescProvincia(indirizzoSedePrincipale.getSiglaProvincia());
		    					}
		    					sedePrincipale.setIndirizzo(indirizzoSedePrincipale.getDescIndirizzo());
		    					ret.setSedePrincipale(sedePrincipale);
		    				}
	    				}
	    				
	    			}
	    			
	    		}
	    		
	    	}
	    	
	    	if(beneficiarioBilancioVO.getDenominazione()!=null){
	    		ret.setRagioneSociale(beneficiarioBilancioVO.getDenominazione());
	    		ret.setPersonaFisica(false);
	    	}else if(beneficiarioBilancioVO.getCognome()!=null){
	    		ret.setCognome(beneficiarioBilancioVO.getCognome());
	    		ret.setNome(beneficiarioBilancioVO.getNome());
	    		ret.setPersonaFisica(true);
	    	}else {
	    		ret.setPersonaFisica(false);
	    	}
	    	logger.dump(ret);
		}else{
			sb.append("ERRORE!!! non ci sono beneficiari del bilancio per  idAttoLiquidazione "+idAttoLiquidazione);
		}
	    logger.info("\n\n\n\n\n"+sb.toString());
	    
		if(!isNull(ret)) {
			return ret;
		}
		else
			throw new LiquidazioneBilancioException("Beneficiario Bilancio non trovato");
	}
	
	
	

	private void caricaDatiPagamento(
			BeneficiarioBilancioVO beneficiarioBilancioVO) {
		
		
		
		BigDecimal idDatiPagamentoAtto = beneficiarioBilancioVO.getIdDatiPagamentoAtto();
        // se nullo cerco cmq i dati a partire dal medesimo prj. se non c'e' nulla ,
    	// cerco per il soggetto
    	
    	if(isNull(idDatiPagamentoAtto)){
    		
    		logger.debug(" idDatiPagamentoAtto � null,cerco beneficiario per idProgetto");
    		BeneficiarioBilancioVO beneficiarioBilancioPagamentoVO = getBeneficiarioBilancioByIdProgetto(beneficiarioBilancioVO.getIdProgetto());
 
    		if(isNull(beneficiarioBilancioPagamentoVO)||
    				isNull(beneficiarioBilancioPagamentoVO.getIdDatiPagamentoAtto()) ){
    			beneficiarioBilancioPagamentoVO = getBeneficiarioBilancioByIdSoggetto(beneficiarioBilancioVO.getIdSoggetto());
    			if(isNull(beneficiarioBilancioPagamentoVO)||
    					isNull(beneficiarioBilancioPagamentoVO.getIdDatiPagamentoAtto()) ){
    				logger.debug(" NON trovati dati del pagamento in base a idsoggetto: "+beneficiarioBilancioVO.getIdSoggetto());
    			}else{
    				logger.debug(" trovati dati del pagamento in base a idsoggetto: "+beneficiarioBilancioVO.getIdSoggetto());
    			}
    		}else{
    			logger.debug("  trovati dati del pagamento in base a idProgetto: "+beneficiarioBilancioVO.getIdProgetto());
			}
    		
			if(!isNull(beneficiarioBilancioPagamentoVO)){
				BigDecimal idAttoLiquidazione= beneficiarioBilancioVO.getIdAttoLiquidazione();	
				beanUtil.valueCopy(beneficiarioBilancioPagamentoVO, beneficiarioBilancioVO);
				beneficiarioBilancioVO.setIdAttoLiquidazione(idAttoLiquidazione);
		    	}
			}
    }

	private BeneficiarioBilancioVO getBeneficiarioBilancioByIdSoggetto (
			BigDecimal idSoggetto) {
		BeneficiarioBilancioVO filtro=new BeneficiarioBilancioVO();
		BeneficiarioBilancioVO filtroStatoAttoNonChiuso=new BeneficiarioBilancioVO();
		filtroStatoAttoNonChiuso.setDescBreveStatoAtto(Constants.DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_EMESSO);
		
		filtro.setIdSoggetto(idSoggetto);
		filtro.setDescendentOrder("dtEmissioneAtto");
		 
		/*List<BeneficiarioBilancioVO>list = genericDAO
			.findListWhere(new AndCondition<BeneficiarioBilancioVO>(Condition.filterBy(filtro),
					new NotCondition<BeneficiarioBilancioVO>(
							new FilterCondition<BeneficiarioBilancioVO>(
									filtroStatoAttoNonChiuso))));
		*/
		List<BeneficiarioBilancioVO>list = genericDAO
		.findListWhere(new AndCondition<BeneficiarioBilancioVO>(Condition.filterBy(filtro),
				Condition.filterBy(
								filtroStatoAttoNonChiuso)));
		
		for (BeneficiarioBilancioVO vo : list) {
			if( !isNull(vo.getIdDatiPagamentoAtto())){
				logger.debug("trovato idDatiPagamentoAtto :"+ vo.getIdDatiPagamentoAtto()+
						" per idAttoLiquidazione "+vo.getIdAttoLiquidazione());
				return vo;
			}
		}
		return null;
	}

	private BeneficiarioBilancioVO getBeneficiarioBilancioByIdProgetto(
			BigDecimal idProgetto) {
		BeneficiarioBilancioVO filtro=new BeneficiarioBilancioVO();
		BeneficiarioBilancioVO filtroStatoAttoNonChiuso=new BeneficiarioBilancioVO();
		filtroStatoAttoNonChiuso.setDescBreveStatoAtto(Constants.DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_EMESSO);
		
		filtro.setIdProgetto(idProgetto);
		filtro.setDescendentOrder("dtEmissioneAtto");
		 
		/*List<BeneficiarioBilancioVO>list = genericDAO
			.findListWhere(new AndCondition<BeneficiarioBilancioVO>(Condition.filterBy(filtro),
					new NotCondition<BeneficiarioBilancioVO>(
							new FilterCondition<BeneficiarioBilancioVO>(
									filtroStatoAttoNonChiuso))));
		*/
		List<BeneficiarioBilancioVO>list = genericDAO
		.findListWhere(new AndCondition<BeneficiarioBilancioVO>(Condition.filterBy(filtro),
				Condition.filterBy(
								filtroStatoAttoNonChiuso)));
	
		
		for (BeneficiarioBilancioVO vo : list) {
			if( !isNull(vo.getIdDatiPagamentoAtto())){
				logger.debug("trovato idDatiPagamentoAtto :"+ vo.getIdDatiPagamentoAtto()+
						" per idAttoLiquidazione "+vo.getIdAttoLiquidazione());
				return vo;
			}
		}
		return null;
	}



	public DettaglioBeneficiarioBilancioDTO[] elencoBeneficiariBilancio(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idAttoLiquidazione,
			String codiceFiscaleBen,String pIva)
			throws CSIException, SystemException, UnrecoverableException,
			LiquidazioneBilancioException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idAttoLiquidazione"
				};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale, idProgetto, idAttoLiquidazione);
		
		DettaglioBeneficiarioBilancioDTO[] elenco=null;
			
		//// 
		StringBuilder sb=new StringBuilder("\n\n\n\n\n\n\n");
		sb.append("cerco i beneficiari legati a idAttoLiquidazione: "+idAttoLiquidazione);
		logger.info("\n\n\n\n\n"+sb.toString());
		////
		String codiceFiscale=codiceFiscaleBen;;
		if( codiceFiscale.contains("-")){
			codiceFiscale=codiceFiscale.substring(0, codiceFiscale.indexOf("-"));
		}

		Long maxRec=20l;
		List<DettaglioBeneficiarioBilancioDTO> list=null; 
	    ConsultaBeneficiariVO beneficiariCF = getBilancioDAO().
	    					consultaBeneficiari(codiceFiscale,null,maxRec,idUtente);
	    if(beneficiariCF!=null && beneficiariCF.getBeneficiario()!=null){
	    	DatiBeneficiarioVO[] beneficiario = beneficiariCF.getBeneficiario();
	    	list = rimappaBeneficiari(beneficiario);
	    }else{
	    	throw new LiquidazioneBilancioException(ErrorConstants.SERVIZIO_BILANCIO_NON_DISPONIBILE);
	    }
	    
	 
	    if(isEmpty(list)){
	    	list=new ArrayList<DettaglioBeneficiarioBilancioDTO>();
	    }
	    
   
    	elenco=list.toArray(new DettaglioBeneficiarioBilancioDTO[] {});
		return elenco;
	}

	private List<DettaglioBeneficiarioBilancioDTO> rimappaBeneficiari(
			DatiBeneficiarioVO[] beneficiario) {
		List <DettaglioBeneficiarioBilancioDTO> list = new ArrayList<DettaglioBeneficiarioBilancioDTO>() ;
	    
	    for (int i = 0; i < beneficiario.length; i++) {
	    	BeneficiarioVO[] beneficiarioVo = beneficiario[i].getBeneficiario();
	    	for (int j = 0; j < beneficiarioVo.length; j++) {
	    		DettaglioBeneficiarioBilancioDTO dto=new  DettaglioBeneficiarioBilancioDTO();
	    		
	    		FornitoreVO fornitore = beneficiario[i].getFornitore();
		    	dto.setCap(fornitore.getCap());
		    	dto.setCodiceFiscaleBen(fornitore.getCodfisc());
		    	dto.setDescProvincia(fornitore.getProv());
		    	if(fornitore.getProv()!=null && fornitore.getProv().equalsIgnoreCase("EE")){
		    		dto.setComuneStatoEstero(fornitore.getComune());
		    	}else{
		    		dto.setDescComune(fornitore.getComune());
		    	}
		    	
		    	if(isTrue(fornitore.isPersonaFisica())){
		    			if(fornitore.getRagsoc()!=null){
		    				if(fornitore.getRagsoc().indexOf(".")>0){
		    					String[] split = fornitore.getRagsoc().split("\\.");
		    					if(split!=null && split.length>1){
		    						dto.setCognome(split[0]);
		    						dto.setNome(split[1]);
		    					}else{
		    						dto.setCognome(fornitore.getRagsoc());
		    						dto.setNome("");
		    					}
		    				}else{
		    					dto.setCognome(fornitore.getRagsoc());
		    				}
		    			}
		    			dto.setDescComuneNascita(fornitore.getComuneNascita());
		    			dto.setDescProvinciaNascita(fornitore.getProvNascita());
		    			dto.setDtNascita(fornitore.getDataNascita());
		    			dto.setSesso(fornitore.getSesso());
		    			dto.setPersonaFisica(true);
		    	} else {
		    		dto.setRagioneSociale(fornitore.getRagsoc());
		    	}
		    	
		    	dto.setMail(fornitore.getEmail());
		    	dto.setPartitaIva(fornitore.getPartiva());
		    	String sede="";
		    	if (!isEmpty(fornitore.getVia())){
		    		sede=fornitore.getVia();
		    		dto.setIndirizzo(fornitore.getVia());
		    	}
		    	
		    	
		    	if (!isEmpty(fornitore.getCap()))
		    		sede+=" "+fornitore.getCap();
		    
		    	if (!isEmpty(fornitore.getComune()))
		    		sede+=" "+fornitore.getComune();
		    	
		    	if (!isEmpty(fornitore.getProv()))
		    		sede+=" ("+fornitore.getProv()+")";
		    	
		    	dto.setSede(sede);
	    		if (!isNull(beneficiarioVo[j].getCodBen()))
	    			dto.setCodiceBeneficiarioBilancio( beneficiarioVo[j].getCodBen().longValue());
	    		dto.setAbi(beneficiarioVo[j].getDescrAbi());
	    		dto.setCab(beneficiarioVo[j].getDescrCab());
	    		dto.setModalitaPagamento(beneficiarioVo[j].getDescrCodAccre());
	    		dto.setIban(beneficiarioVo[j].getIban());
	    		if (!isNull( beneficiarioVo[j].getProgBen()))
	    			dto.setCodModPagBilancio( beneficiarioVo[j].getProgBen().longValue());
	    		
	    		/*
	    		 * FIX PBandi-2130
	    		 */
	    		dto.setNumeroConto(beneficiarioVo[j].getNroCC());
	    		dto.setSecondario(false);
	    		
	    		if(!isEmpty(beneficiarioVo[j].getRagSocSede())){
	    			
	    			dto.setSecondario(true);
		    		dto.setRagioneSocialeSecondaria(beneficiarioVo[j].getRagSocSede());
		    		dto.setCap( beneficiarioVo[j].getCap());
		    		dto.setDescComune( beneficiarioVo[j].getComuneSede());
		    		dto.setDescProvincia( beneficiarioVo[j].getProvSede());
		    		String sedeSecondaria="";
			    	if(!isEmpty(beneficiarioVo[j].getViaSede())){
			    		sedeSecondaria=beneficiarioVo[j].getViaSede();
			    		dto.setIndirizzo(beneficiarioVo[j].getViaSede());
			    	}
			    	
			    	if(!isEmpty(beneficiarioVo[j].getCap()))
			    		sedeSecondaria+=" "+beneficiarioVo[j].getCap();
			    
			    	if(!isEmpty(beneficiarioVo[j].getComuneSede()))
			    		sedeSecondaria+=" "+beneficiarioVo[j].getComuneSede();
			    	
			    	if(!isEmpty(beneficiarioVo[j].getProvSede()))
			    		sedeSecondaria+=" ("+beneficiarioVo[j].getProvSede()+")";
			    	dto.setSedeSecondaria(sedeSecondaria);
			    	dto.setSede(sedeSecondaria);
			    	
			    	/*
	    			 * FIX PBandi-2137
			    	 * Setto la sedePrincipale per non perdere i dati
			    	 * nel caso in cui ci sia anche la sede secondaria
			    	 */
			    	SedeBilancioDTO sedePrincipale = new SedeBilancioDTO();
			    	sedePrincipale.setCap(fornitore.getCap());
			    	sedePrincipale.setIndirizzo(fornitore.getVia());
			    	sedePrincipale.setDescComune(fornitore.getComune());
			    	sedePrincipale.setDescProvincia(fornitore.getProv());
			    	dto.setSedePrincipale(sedePrincipale);
	    		}
		    	
		    	list.add(dto);
		    }
		}
		return list;
	}


	
		public BeneficiarioBilancioDTO aggiornaBeneficiario(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idAttoLiquidazione,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario)
			throws CSIException, SystemException, UnrecoverableException,
			LiquidazioneBilancioException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idAttoLiquidazione","dettaglioBeneficiario"
				};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale, idProgetto, idAttoLiquidazione,dettaglioBeneficiario);
		
		logger.dump(dettaglioBeneficiario);
		//// 
		StringBuilder sb=new StringBuilder("\n\n\n\n\n\n\n");
		sb.append("aggiornaBeneficiario per idAttoLiquidazione: "+idAttoLiquidazione)
		.append("\t beneficiario con codiceBilancio : "+dettaglioBeneficiario.getCodiceBeneficiarioBilancio())
		.append("\t beneficiario con idBeneficiarioBilancio : "+dettaglioBeneficiario.getIdBeneficiarioBilancio());
		
		
		
		try{
			PbandiTAttoLiquidazioneVO pbandiTAttoLiquidazioneVO= new PbandiTAttoLiquidazioneVO();
			pbandiTAttoLiquidazioneVO.setIdAttoLiquidazione(new BigDecimal(idAttoLiquidazione)) ;
			pbandiTAttoLiquidazioneVO=	genericDAO.findSingleWhere(pbandiTAttoLiquidazioneVO);
			
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO =
				findPBandiTBeneficiarioBilancioVO(dettaglioBeneficiario,idAttoLiquidazione);
			
			
			if(isTrue(dettaglioBeneficiario.getPersonaFisica())){
				salvaPersonaFisica(idUtente,dettaglioBeneficiario,pbandiTAttoLiquidazioneVO,pbandiTBeneficiarioBilancioVO);
			}else{
				sb.append("\t salvo ente giuridico con ragione sociale: "+dettaglioBeneficiario.getRagioneSociale());
				salvaEnteGiuridico(idUtente,dettaglioBeneficiario,pbandiTAttoLiquidazioneVO,pbandiTBeneficiarioBilancioVO);
			}
			sb.append("\t salvaRecapiti con email: "+dettaglioBeneficiario.getMail());
			salvaRecapiti(idUtente,dettaglioBeneficiario, pbandiTBeneficiarioBilancioVO);
			
			sb.append("\t salvoSede con partitaIva: "+dettaglioBeneficiario.getPartitaIva());
			salvaSede(idUtente,dettaglioBeneficiario, pbandiTBeneficiarioBilancioVO);
			
			sb.append("\t salvoIndirizzo : "+dettaglioBeneficiario.getPartitaIva());
			salvaIndirizzo(idUtente,dettaglioBeneficiario, pbandiTBeneficiarioBilancioVO);
			
			BigDecimal idSedeSecondaria=null;
			if(!isEmpty( dettaglioBeneficiario.getRagioneSocialeSecondaria())){
				PbandiTSedeVO pbandiTSedeVO=new PbandiTSedeVO();
				if(!ObjectUtil.isEmpty(dettaglioBeneficiario.getRagioneSocialeSecondaria())){
					pbandiTSedeVO.setPartitaIva(dettaglioBeneficiario.getPartitaIva());
					pbandiTSedeVO.setDenominazione(dettaglioBeneficiario.getRagioneSocialeSecondaria());
					List<PbandiTSedeVO> listSedi = genericDAO.findListWhere(pbandiTSedeVO);
					if(!isEmpty(listSedi)){
						pbandiTSedeVO = listSedi.get(0);
					}else{
						pbandiTSedeVO.setIdUtenteIns(new BigDecimal(idUtente));
						pbandiTSedeVO=genericDAO.insert(pbandiTSedeVO);
					}
				}
				idSedeSecondaria=pbandiTSedeVO.getIdSede();
			} else {
				idSedeSecondaria=null;
			}
				
			
			sb.append("\t salvaDatiPagamentoAttoEEstremiBancari: "+dettaglioBeneficiario.getPartitaIva());
			salvaDatiPagamentoAttoEEstremiBancari(idUtente,dettaglioBeneficiario,
					pbandiTBeneficiarioBilancioVO,pbandiTAttoLiquidazioneVO,idSedeSecondaria);
			
		
			
			pbandiTBeneficiarioBilancioVO.setCodiceBeneficiarioBilancio(
					NumberUtil.toBigDecimal(dettaglioBeneficiario.getCodiceBeneficiarioBilancio()));
			pbandiTBeneficiarioBilancioVO.setIdSoggetto(new BigDecimal(dettaglioBeneficiario.getIdSoggetto()));

			
			if(pbandiTBeneficiarioBilancioVO.getIdBeneficiarioBilancio()!=null){
				pbandiTBeneficiarioBilancioVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(pbandiTBeneficiarioBilancioVO);
			}
			pbandiTAttoLiquidazioneVO.setIdBeneficiarioBilancio(pbandiTBeneficiarioBilancioVO.getIdBeneficiarioBilancio());
			pbandiTAttoLiquidazioneVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(pbandiTAttoLiquidazioneVO);
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new LiquidazioneBilancioException(e.getMessage());
		}
		logger.info(sb.toString()+"\n\n\n\n\n");
		return findBeneficiario(idUtente, identitaDigitale, idProgetto, idAttoLiquidazione);
	
	}

		private PbandiTBeneficiarioBilancioVO findPBandiTBeneficiarioBilancioVO(
				DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario,Long idAttoLiquidazione) {
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO=new PbandiTBeneficiarioBilancioVO();
			if(dettaglioBeneficiario.getIdBeneficiarioBilancio()!=null){
				pbandiTBeneficiarioBilancioVO.setIdBeneficiarioBilancio(new BigDecimal(dettaglioBeneficiario.getIdBeneficiarioBilancio()));
				pbandiTBeneficiarioBilancioVO=genericDAO.findSingleWhere(pbandiTBeneficiarioBilancioVO);
			}else{
				// ho cliccato su elenco benef del bilancio
				//a) cerco benef bil su tbenefbil con stesso codice: se trovato uso quello,altrimenti
				//b) uso quello legato all'atto in corso
			
				
				
				pbandiTBeneficiarioBilancioVO.setCodiceBeneficiarioBilancio(new BigDecimal(dettaglioBeneficiario.getCodiceBeneficiarioBilancio()));
				List<PbandiTBeneficiarioBilancioVO> listBeneficiari = genericDAO.findListWhere(pbandiTBeneficiarioBilancioVO);
				if(!isEmpty(listBeneficiari)){
					//a
					pbandiTBeneficiarioBilancioVO=listBeneficiari.get(0);
					
				}else{
					//b
					PbandiTAttoLiquidazioneVO attoLiquidazioneVO=new PbandiTAttoLiquidazioneVO();
					attoLiquidazioneVO.setIdAttoLiquidazione(new BigDecimal(idAttoLiquidazione)) ;
					PbandiTAttoLiquidazioneVO atto = genericDAO.findSingleWhere(attoLiquidazioneVO);
					pbandiTBeneficiarioBilancioVO=new PbandiTBeneficiarioBilancioVO();
					pbandiTBeneficiarioBilancioVO.setIdBeneficiarioBilancio(atto.getIdBeneficiarioBilancio());
					pbandiTBeneficiarioBilancioVO= genericDAO.findSingleWhere(pbandiTBeneficiarioBilancioVO);
				}
			}
			return pbandiTBeneficiarioBilancioVO;
		}	
	




	


	public CodiceDescrizioneDTO[] getTipiAliquotaRitenuta(
			java.lang.Long idUtente, String identitaDigitale,
			Long idTipoRitenuta, Long idTipoSoggettoRitenuta)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException

	{
		PbandiDAliquotaRitenutaVO aliquotaRitenutaVO = new PbandiDAliquotaRitenutaVO();
		aliquotaRitenutaVO.setIdTipoRitenuta(beanUtil.transform(idTipoRitenuta, BigDecimal.class));
		aliquotaRitenutaVO.setIdTipoSoggRitenuta(beanUtil.transform(idTipoSoggettoRitenuta,BigDecimal.class));
		List<PbandiDAliquotaRitenutaVO> aliquoteRitenuta = genericDAO.findListWhere(aliquotaRitenutaVO);
		ArrayList<CodiceDescrizioneDTO> elencoAliquoteRitenuta = new ArrayList<CodiceDescrizioneDTO>();
		//FIXME aggiuntege cond data fine validita
		for (PbandiDAliquotaRitenutaVO aliqRitenutaVO : aliquoteRitenuta){
			CodiceDescrizioneDTO codDescAliquotaRitenuta = beanUtil.transform(
					aliqRitenutaVO, CodiceDescrizioneDTO.class, 
					new HashMap<String, String>(){
						{
							put("idAliquotaRitenuta","codice");
							put("descAliquota","descrizione");
						}
					});	
			elencoAliquoteRitenuta.add(codDescAliquotaRitenuta);
		}
		return elencoAliquoteRitenuta
		.toArray(new CodiceDescrizioneDTO[elencoAliquoteRitenuta.size()]);

	}




	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoRitenuteDTO getRitenuta(

	Long idUtente,

	String identitaDigitale,

	Long idAttoLiquidazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException

	{
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idAttoLiquidazione" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale,idAttoLiquidazione);
		EsitoRitenuteDTO esito = new EsitoRitenuteDTO();
		try {
			esito = aGetRitenuta(idAttoLiquidazione);
		} catch (Exception e) {
			logger.error("Errore nel recupero riepilogo ritenuta", e);
		} 
		return esito;
	}

	private EsitoRitenuteDTO aGetRitenuta(Long idAttoLiquidazione) {
		EsitoRitenuteDTO esito = new EsitoRitenuteDTO();
		esito.setEsito(false);
		PbandiTAttoLiquidazioneVO  ritenutaVO = new PbandiTAttoLiquidazioneVO();
		ritenutaVO.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
		RitenutaDTO ritenuta = beanUtil.transform(genericDAO.findSingleWhere(ritenutaVO), RitenutaDTO.class,
				new HashMap<String, String>() {
			{
						put("idAttoLiquidazione","idAttoLiquidazione");
						put("impNonSoggettoRitenuta","sommeNonImponibili"); 
						put("idAliquotaRitenuta","idAliquotaRitenuta");
						put("idSituazioneInps","idSituazioneInps");
						put("idTipoAltraCassaPrev","idRitenutaPrevidenziale");
						put("idAltraCassaPrevidenz","idAltraCassaPrevidenziale");
						put("idAttivitaInps","idAttivitaInps");
						put("dtInpsDal","dtInpsDal");
						put("dtInpsAl","dtInpsAl");
						put("idRischioInail","idRischioInail");
						put("importoImponibile","imponibile");
			}
		});			
		ritenuta.setIdTipoRitenuta(null);
		ritenuta.setIdTipoSoggettoRitenuta(null);
		if(ritenuta.getIdAliquotaRitenuta()!=null){
			PbandiDAliquotaRitenutaVO tipoAliquitaVO = new PbandiDAliquotaRitenutaVO();
			tipoAliquitaVO.setIdAliquotaRitenuta(beanUtil.transform(ritenuta.getIdAliquotaRitenuta(), BigDecimal.class));
			ritenuta.setIdTipoRitenuta(beanUtil.transform(genericDAO.findSingleWhere(tipoAliquitaVO).getIdTipoRitenuta(), Long.class));
			ritenuta.setIdTipoSoggettoRitenuta(beanUtil.transform(genericDAO.findSingleWhere(tipoAliquitaVO).getIdTipoSoggRitenuta(), Long.class));
		}
		esito.setRitenuta(ritenuta);
		esito.setEsito(true);
		return esito;
	}

	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoRitenuteDTO aggiornaRitenuta(
	Long idUtente,
	String identitaDigitale,
	RitenutaDTO ritenuta

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException

	{
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"ritenuta","ritenuta.idAttoLiquidazione"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
		identitaDigitale,ritenuta, ritenuta.getIdAttoLiquidazione());
		
		
		EsitoRitenuteDTO esito = new EsitoRitenuteDTO();
		esito.setEsito(false);
		PbandiTAttoLiquidazioneVO attoDiLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
		attoDiLiquidazioneVO.setIdAttoLiquidazione(beanUtil.transform(ritenuta.getIdAttoLiquidazione(), BigDecimal.class));
		attoDiLiquidazioneVO = genericDAO.findSingleWhere(attoDiLiquidazioneVO);
		
		attoDiLiquidazioneVO.setImportoImponibile(beanUtil.transform(ritenuta.getImponibile(), BigDecimal.class));
		attoDiLiquidazioneVO.setImpNonSoggettoRitenuta(beanUtil.transform(ritenuta.getSommeNonImponibili(), BigDecimal.class));
		attoDiLiquidazioneVO.setIdAliquotaRitenuta(beanUtil.transform(ritenuta.getIdAliquotaRitenuta(), BigDecimal.class));
//		attoDiLiquidazioneVO.setIdAliquotaRitenuta(beanUtil.transform(ritenuta.getIdAliquotaRitenuta() == null? attoDiLiquidazioneVO.getIdAliquotaRitenuta() 
//				                                                                                              : ritenuta.getIdAliquotaRitenuta(), BigDecimal.class) );
		
		/*
		attoDiLiquidazioneVO.setImpNonSoggettoRitenuta(beanUtil.transform(ritenuta.getSommeNonImponibili(), BigDecimal.class));
		attoDiLiquidazioneVO.setIdAliquotaRitenuta(beanUtil.transform(ritenuta.getIdAliquotaRitenuta(), BigDecimal.class));
		attoDiLiquidazioneVO.setIdSituazioneInps(beanUtil.transform(ritenuta.getIdSituazioneInps(), BigDecimal.class));
		attoDiLiquidazioneVO.setIdAltraCassaPrevidenz(beanUtil.transform(ritenuta.getIdAltraCassaPrevidenziale(), BigDecimal.class));
		attoDiLiquidazioneVO.setIdTipoAltraCassaPrev(beanUtil.transform(ritenuta.getIdRitenutaPrevidenziale(), BigDecimal.class));
		attoDiLiquidazioneVO.setIdAttivitaInps(beanUtil.transform(ritenuta.getIdAttivitaInps(), BigDecimal.class));
		attoDiLiquidazioneVO.setDtInpsDal(beanUtil.transform(ritenuta.getDtInpsDal(), Date.class));
		attoDiLiquidazioneVO.setDtInpsAl(beanUtil.transform(ritenuta.getDtInpsAl(), Date.class));
		attoDiLiquidazioneVO.setIdRischioInail(beanUtil.transform(ritenuta.getIdRischioInail(), BigDecimal.class));
		 */
		
		try {
			attoDiLiquidazioneVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(attoDiLiquidazioneVO);
			return aGetRitenuta(ritenuta.getIdAttoLiquidazione());
		} catch (Exception e) {
			logger.error("Errore nell aggiornamento ritenute per la creazione atti di liquidazione", e);
			LiquidazioneBilancioException re = new LiquidazioneBilancioException(ERRORE_SERVER);
			throw re;
		}  
	}
	
		
	public EsitoInfoCreaAttoDTO getInfoCreaAtto(

			Long idUtente,
				
			String identitaDigitale,

			Long idAttoLiquidazione

			)
					throws it.csi.csi.wrapper.CSIException,
					it.csi.csi.wrapper.SystemException,
					it.csi.csi.wrapper.UnrecoverableException

					,
					it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException

			{
		
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idAttoLiquidazione"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
			identitaDigitale,idAttoLiquidazione);
			EsitoInfoCreaAttoDTO esito = new EsitoInfoCreaAttoDTO();
			esito.setEsito(false);
			try {
				BigDecimal bdIdAtto = beanUtil.transform(idAttoLiquidazione, BigDecimal.class);
				InfoCreaAttoVO infoCreaAttoVO = new InfoCreaAttoVO();
				infoCreaAttoVO.setIdAttoLiquidazione(bdIdAtto);
				esito.setInfoCreaAtto(beanUtil.transform(genericDAO.findSingleWhere(infoCreaAttoVO), InfoCreaAttoDTO.class));
				
				// Su richiesta di Carla: "in corrispondenza dell'etichetta "Settore", bisognerebbe riportare:
				// PBANDI_T_SETTORE_ENTE.desc_breve_settore || ' '|| PBANDI_T_SETTORE_ENTE.desc_settore
				// riferiti a PBANDI_T_ATTO_LIQUIDAZIONE.id_settore_ente".
				String newDescSettore = "";
				PbandiTAttoLiquidazioneVO atto = new PbandiTAttoLiquidazioneVO();
				atto.setIdAttoLiquidazione(bdIdAtto);
				atto = genericDAO.findSingleWhere(atto);
				if (atto.getIdSettoreEnte() != null) {
					PbandiDSettoreEnteVO se = new PbandiDSettoreEnteVO();
					se.setIdSettoreEnte(atto.getIdSettoreEnte());
					se = genericDAO.findSingleWhere(se);
					newDescSettore = se.getDescBreveSettore()+" "+se.getDescSettore();
				}
				if (esito.getInfoCreaAtto() != null) {
					esito.getInfoCreaAtto().setDescSettore(newDescSettore);
				}
				
				esito.setEsito(true);
			} catch (Exception e) {
				logger.error("Errore nel recupero dati di riepilogo dell atto di liquidazione", e);
				LiquidazioneBilancioException re = new LiquidazioneBilancioException(ERRORE_SERVER);
				throw re;
			}  
			return esito;
			
	}
	
	

	
	//Double importoCaricoEnte, Double importoCaricoSoggetto
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoCreaAttoDTO creaAtto(
			Long idUtente, String identitaDigitale, Long idAttoLiquidazione,Long idProgetto)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idAttoLiquidazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idAttoLiquidazione);
		EsitoCreaAttoDTO esitoCreaAtto = new EsitoCreaAttoDTO();
		esitoCreaAtto.setEsito(false);
		logger.warn("\n\n\n##############################################\ninside creaAtto...");
		try {			
			InserisciAttoLiquidazioneVO inserisciAtto = new InserisciAttoLiquidazioneVO();
			
			AttoLiquidazioneVO liquidazioneVO = new AttoLiquidazioneVO();

			CreaAttoVO creaAttoVO = new CreaAttoVO();
			creaAttoVO.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
			creaAttoVO = genericDAO.findSingleWhere(creaAttoVO);
            
			DatiPagamentoVO datiPagamentoVO = new DatiPagamentoVO();
			datiPagamentoVO.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
			datiPagamentoVO = genericDAO.findSingleWhere(datiPagamentoVO);
			
			logger.shallowDump(datiPagamentoVO,"info");
			
			
			CreaAttoImpegniVO creaAttoImpegniVO = new CreaAttoImpegniVO();
			creaAttoImpegniVO.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
			List<CreaAttoImpegniVO> listAttiImpegniVO = genericDAO.findListWhere(creaAttoImpegniVO);
			
			
			List<ImpegnoVO> listaImpegnoVO = new ArrayList<ImpegnoVO>();
			if (listAttiImpegniVO != null && listAttiImpegniVO.size() > 0) {
				logger.warn("listAttiImpegniVO: "+listAttiImpegniVO.size());
				for (CreaAttoImpegniVO attoImpegno : listAttiImpegniVO) {
					ImpegnoVO impegnoVo = new ImpegnoVO();
					if (!attoImpegno.getImporto().equals(new BigDecimal(0))) {
						String nroCapitolo = beanUtil.transform(attoImpegno.getNroCapitolo(), String.class);
						impegnoVo.setNumeroCapitolo(nroCapitolo);
						impegnoVo.setAnnoProvvedimento(attoImpegno.getAnnoProv());
						impegnoVo.setNumeroProvvedimento(attoImpegno.getNroProv());
						impegnoVo.setDescBreveTipoProvvedimento(attoImpegno.getTipoProv());
						impegnoVo.setDescBreveDirezioneProvvedimento(attoImpegno.getDirezione());
						impegnoVo.setAnnoImpegno(attoImpegno.getAnnoImp());
						impegnoVo.setNumeroImpegno(new Integer(attoImpegno.getNroImp()));
						impegnoVo.setImportoAttualeImpegno(attoImpegno.getImporto());
						impegnoVo.setCup(attoImpegno.getCup());
						impegnoVo.setCig(attoImpegno.getCig());
						impegnoVo.setAnnoEsercizio(attoImpegno.getAnnoEsercizio());
						listaImpegnoVO.add(impegnoVo);
						logger.shallowDump(attoImpegno,"info");
						logger.shallowDump(impegnoVo,"info");
					}
				}
			}else{
				logger.warn("listAttiImpegniVO is null ");
			}

			FornitoreCreaAttoVO fornitoreAttoVO = new FornitoreCreaAttoVO();
			fornitoreAttoVO.setIdAttoLiquidazione(idAttoLiquidazione);
			fornitoreAttoVO = genericDAO.findSingleWhere(fornitoreAttoVO);
			
			FornitoreVO fornitoreVO = new FornitoreVO();

			
			fornitoreVO.setCodben(fornitoreAttoVO.getCodBen());
			
			if (fornitoreAttoVO.getIdEnteGiuridico() != null) {
				fornitoreVO.setCodfisc(StringUtil.truncate(fornitoreAttoVO.getCodiceFiscale(), 11));
				fornitoreVO.setPartiva(fornitoreAttoVO.getPartIva());
			} else if (fornitoreAttoVO.getIdPersonaFisica() != null) {
				fornitoreVO.setCodfisc(fornitoreAttoVO.getCodiceFiscale());
				fornitoreVO.setPartiva(fornitoreAttoVO.getPartIva());
			}
			

			fornitoreVO.setRagsoc(fornitoreAttoVO.getRagSoc());
			fornitoreVO.setVia(fornitoreAttoVO.getIndirizzo());
			fornitoreVO.setCap(fornitoreAttoVO.getCap());
			fornitoreVO.setComune(fornitoreAttoVO.getComune());
			fornitoreVO.setProv(fornitoreAttoVO.getProv());
			fornitoreVO.setEmail(fornitoreAttoVO.getMail());
			fornitoreVO.setSesso(fornitoreAttoVO.getSesso());
			fornitoreVO.setDataNascita(fornitoreAttoVO.getDtNascita());
			fornitoreVO.setComuneNascita(fornitoreAttoVO.getComuneNascita());
			fornitoreVO.setProvNascita(fornitoreAttoVO.getProvNascita());
			liquidazioneVO.setFornitore(fornitoreVO);

			logger.shallowDump(fornitoreAttoVO,"info");
			logger.shallowDump(fornitoreVO,"info");
			
			liquidazioneVO.setAtto(new AttoVO());
			liquidazioneVO.getAtto().setSettoreAtto(creaAttoVO.getSettoreAtto());
			liquidazioneVO.getAtto().setDirAtto(creaAttoVO.getDirAtto());

			liquidazioneVO.getAtto().setAnnoAtto(creaAttoVO.getAnnoAtto());
			liquidazioneVO.getAtto().setRitenuta(new RitenutaAttoVO());
			liquidazioneVO.getAtto().getRitenuta().setTipoSoggetto(creaAttoVO.getTipoSoggetto());
			liquidazioneVO.getAtto().getRitenuta().setTipoRitenuta(creaAttoVO.getTipoRitenuta());
			liquidazioneVO.getAtto().getRitenuta().setIrpNonSoggette(
							beanUtil.transform(creaAttoVO.getImpNonSoggette(),
									Double.class));
			liquidazioneVO.getAtto().getRitenuta().setAliqIrpef(
							beanUtil.transform(creaAttoVO.getAliqIrpef(),Double.class));
			liquidazioneVO.getAtto().getRitenuta().setDatoInps(
							beanUtil.transform(creaAttoVO.getDatoInps(),Integer.class));
			liquidazioneVO.getAtto().getRitenuta().setInpsAltraCassa(creaAttoVO.getInpsAltraCassa());
			liquidazioneVO.getAtto().getRitenuta().setInpsDal(creaAttoVO.getInpsDal());
			liquidazioneVO.getAtto().getRitenuta().setInpsAl(creaAttoVO.getInpsAl());
			liquidazioneVO.getAtto().getRitenuta().setRischioInail(creaAttoVO.getRischioInail());
			// FIXME valore fisso, prevedere valore nell'interfaccia
			liquidazioneVO.getAtto().setDivisaUsata("E");
			liquidazioneVO.getAtto().setFlFatture(creaAttoVO.getFlFatture());
			liquidazioneVO.getAtto().setFlEstrCopiaProv(creaAttoVO.getFlEstrCopiaProv());
			liquidazioneVO.getAtto().setFlDocGiustif(creaAttoVO.getFlDocGiustif());
			liquidazioneVO.getAtto().setFlDichiaraz(creaAttoVO.getFlDichiaraz());
			// FIXME rinominare in flall
			liquidazioneVO.getAtto().setFlAltro(creaAttoVO.getAllAltro());
			liquidazioneVO.getAtto().setDataScadenza(creaAttoVO.getDataScadenza());
			liquidazioneVO.getAtto().setNroTelLiq(creaAttoVO.getNroTelLiq());
			liquidazioneVO.getAtto().setNomeLiq(creaAttoVO.getNomeLiq());
			liquidazioneVO.getAtto().setNomeDir(creaAttoVO.getNomeDir());
			liquidazioneVO.getAtto().setDescri(creaAttoVO.getDescAtto());
			// CDU-110-V03 inizio
			inserisciAtto.setIdAttoLiquidazione(idAttoLiquidazione);
			liquidazioneVO.getAtto().setDescCausale(creaAttoVO.getDescCausale());			
			liquidazioneVO.getAtto().setStrutturaAmmContabile(creaAttoVO.getDirAtto()+creaAttoVO.getSettoreAtto());
			
			String codiceVisualizzatoProgetto = getCodiceVisualizzatoProgetto(idAttoLiquidazione);
			
			String numeroDocSpesa = idAttoLiquidazione.toString()+codiceVisualizzatoProgetto;
			if (numeroDocSpesa.length() > MAX_LENGTH_NUMERO_DOC_SPESA)
			numeroDocSpesa = numeroDocSpesa.substring(0, MAX_LENGTH_NUMERO_DOC_SPESA);
			liquidazioneVO.getAtto().setNumeroDocSpesa(numeroDocSpesa);
			
			String descDocSpesa = "Atto liquidazione progetto "+codiceVisualizzatoProgetto;
			liquidazioneVO.getAtto().setDescizioneDocSpesa(descDocSpesa);
			
			// Data emissione forzata a data odierna.
			java.util.Date oggi = it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil.getDataOdierna();
			liquidazioneVO.getAtto().setDataEmisAtto(oggi);
			
			// SOVRASCIVO L'ANNO BILANCIO CON 2016 SOLO PER I TEST CON CONTABILIA: POI DA TOGLIERE!!
			//liquidazioneVO.getAtto().setAnnoAtto(getAnnoAttoMock());		// DA ELIMINARE: forza 2016
			
			// CDU-110-V03 fine

			logger.shallowDump(creaAttoVO,"info");
			logger.shallowDump(liquidazioneVO.getAtto(),"info");
			
			// Dati pagamento e beneficiario
			BeneficiarioVO beneficiarioVo = new BeneficiarioVO();

			if (datiPagamentoVO.getProgModPag() != null
					&& !datiPagamentoVO.getProgModPag().equals("0")) {
				beneficiarioVo.setProgBen(beanUtil.transform(datiPagamentoVO.getProgModPag(), Integer.class));

			} else {
				beneficiarioVo.setProgBen(null);
				String descBreveModErogazione = beanUtil.transform(datiPagamentoVO.getCodAccre(), String.class);
				beneficiarioVo.setCodaccre(descBreveModErogazione);
				/*
				 * FIX PBandi-2130
				 * Nel caso in cui la modalita' di erogazione (codAccre) e'
				 * GIRO FONDI o CONTO CORRENTE POSTALE allora al servizio deve 
				 * essere valorizzato solo nroCc e gli altri campi (iban, abi, cab,
				 * cin e bic) devono essere null.
				 * 
				 * Nel caso in cui la modalita' di erogazione (codAccre) e'
				 * ASSEGNO DI BONIFICO o ASSEGNO CIRCOLARE allora al servizio
				 * non devono essere valorizzati i campi iban, abi, cab,
				 * cin, bic e nroCc.
				 */
				logger.warn("datiPagamentoVO.getCodAccre() "+descBreveModErogazione);
				if (descBreveModErogazione != null) {
					if (descBreveModErogazione.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.DESC_BREVE_MODALITA_EROGAZIONE_CONTO_CORRENTE_POSTALE) ||
						descBreveModErogazione.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.DESC_BREVE_MODALITA_EROGAZIONE_GIRO_FONDI)) {
						beneficiarioVo.setNroCC(datiPagamentoVO.getNroCc());
					} else if (!descBreveModErogazione.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.DESC_BREVE_MODALITA_EROGAZIONE_ASSEGNO_CIRCOLARE) &&
							   !descBreveModErogazione.equals(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.DESC_BREVE_MODALITA_EROGAZIONE_ASSEGNO_DI_BONIFICO)) {
						beneficiarioVo.setNroCC(datiPagamentoVO.getNroCc());
						beneficiarioVo.setAbi(datiPagamentoVO.getAbi());
						beneficiarioVo.setCab(datiPagamentoVO.getCab());
						beneficiarioVo.setCin(datiPagamentoVO.getCin());
						beneficiarioVo.setIban(datiPagamentoVO.getIban());
						beneficiarioVo.setBic(datiPagamentoVO.getBic());
					} 
				}

			}
			logger.shallowDump(beneficiarioVo,"info");
			liquidazioneVO.setBeneficiario(beneficiarioVo);
			// Inserisco gli impegni
			inserisciAtto.setImpegni(listaImpegnoVO.toArray(new ImpegnoVO[] {}));
			inserisciAtto.setAttoLiquidazione(liquidazioneVO);
			inserisciAtto.setMaxRec(new Integer(1));

			// Gestisco il campo note
			if (creaAttoVO.getNote() != null) {
				liquidazioneVO.getAtto().setNote(StringUtil.truncate(creaAttoVO.getNote(),MAX_LENGTH_NOTE));
			}
			
			// Dati Revoca nuova gestione.
			popolaDatiRevoca(inserisciAtto, creaAttoVO);
						 
			// Chiamo il servizio
			InserisciAttoLiquidazioneVO inserisciAttoVO = bilancioDAO.inserisciAttoLiquidazione(inserisciAtto,idUtente);
			
			esitoCreaAtto.setEsito(inserisciAttoVO.getResult().isEsito());
			esitoCreaAtto.setEsitoOperazione(inserisciAttoVO.getEsitoContabilia());	//successo\fallimento
			esitoCreaAtto.setDescEsitoOperazione("");
			esitoCreaAtto.setMessage(inserisciAttoVO.getErroreContabilia());
			esitoCreaAtto.setAnnoAtto(inserisciAtto.getAttoLiquidazione().getAtto().getAnnoAtto());
			esitoCreaAtto.setDataAtto(inserisciAtto.getAttoLiquidazione().getAtto().getDataEmisAtto());
			esitoCreaAtto.setNumeroDocSpesa(liquidazioneVO.getAtto().getNumeroDocSpesa());
			esitoCreaAtto.setIdOperazioneAsincrona(inserisciAttoVO.getIdOperazioneAsincrona());
			logger.info("creaAtto(): esitoCreaAtto: esito = "+esitoCreaAtto.getEsito()+" - IdOperazioneAsincrona = "+esitoCreaAtto.getIdOperazioneAsincrona()); 
			
			if (esitoCreaAtto.getEsito()) {
				logger.warn("\n\n\n############################ NEOFLUX unlock LIQUIDAZIONE ##############################\n");
				neofluxBusiness.unlockAttivita(idUtente, identitaDigitale, idProgetto,Task.LIQUIDAZIONE);
				logger.warn("############################ NEOFLUX unlock LIQUIDAZIONE ##############################\n\n\n\n");				
			}

		} catch (Exception e) {
			logger.error("Error creaAtto: "+e.getMessage(), e);
			/*
			if (UserException.class.isInstance(e)) {
				throw (UserException) e;
			}
			throw new UnrecoverableException(e.getMessage(), e);
			*/
			esitoCreaAtto.setEsito(false);
			esitoCreaAtto.setEsitoOperazione(Constants.CONTABILIA_ESITO_FALLIMENTO);	//successo\fallimento
			esitoCreaAtto.setDescEsitoOperazione("");
			esitoCreaAtto.setMessage("Errore imprevisto nella creazione dell'atto.");
		}  

		return esitoCreaAtto;
	}
	
	private void popolaDatiRevoca(InserisciAttoLiquidazioneVO inserisciAtto, CreaAttoVO creaAttoVO) throws Exception {
		if (creaAttoVO.getIdAliquotaRitenuta() == null) {
			logger.info("popolaDatiRevoca(): IdAliquotaRitenuta non valorizzato: nessuna ritenuta."); 
			inserisciAtto.setRitenutaAttoNew(null);
			return;	
		}			
		
		// Cerco l'aliquota.
		PbandiDAliquotaRitenutaVO ar = new PbandiDAliquotaRitenutaVO();
		ar.setIdAliquotaRitenuta(creaAttoVO.getIdAliquotaRitenuta());
		ar = genericDAO.findSingleOrNoneWhere(ar);
		if (ar == null) {
			logger.error("popolaDatiRevoca(): nessuna Aliquota Ritenuta con id = "+creaAttoVO.getIdAliquotaRitenuta());
			throw new Exception("Aliquota Ritenuta non trovata.");
		}
		
		RitenutaAttoNewVO ritenuta = new RitenutaAttoNewVO();
		ritenuta.setCodOnere(ar.getCodOnere());
		ritenuta.setCodNaturaOnere(ar.getCodNaturaOnere());
		ritenuta.setImportoImponibile(creaAttoVO.getImportoImponibile());
		if (creaAttoVO.getImpNonSoggettoRitenuta() == null) {
			ritenuta.setImpNonSoggettoRitenuta(new BigDecimal(0));
		} else {
			ritenuta.setImpNonSoggettoRitenuta(creaAttoVO.getImpNonSoggettoRitenuta());
		}
		ritenuta.setImportoCaricoEnte(calcolaPercentuale(
				creaAttoVO.getImportoImponibile(),
				creaAttoVO.getImpNonSoggettoRitenuta(),
				ar.getPercCaricoEnte()));
		ritenuta.setImportoCaricoSoggetto(calcolaPercentuale(
				creaAttoVO.getImportoImponibile(),
				creaAttoVO.getImpNonSoggettoRitenuta(),
				ar.getPercCaricoSoggetto()));
		
		inserisciAtto.setRitenutaAttoNew(ritenuta);		
	}
	
	private BigDecimal calcolaPercentuale(BigDecimal imponibile, BigDecimal sommaNonSoggetta, BigDecimal perc) {
		logger.info("calcolaPercentuale(): imponibile = "+imponibile+"; sommaNonSoggetta = "+sommaNonSoggetta+"; perc = "+perc);
		BigDecimal risultato = null;
		if (imponibile == null || perc == null) {
			risultato = new BigDecimal(0); 
		} else if (nonValorizzato(imponibile) || nonValorizzato(perc)) {
			risultato = new BigDecimal(0);
		} else {
			BigDecimal differenza = null;
			if (nonValorizzato(sommaNonSoggetta)) {
				differenza = imponibile;
	 		} else {
	 			differenza = NumberUtil.subtract(imponibile, sommaNonSoggetta);
	 		}
			logger.info("calcolaPercentuale(): differenza = "+differenza);
			risultato = differenza.multiply(perc).divide(new BigDecimal(100));
		}
		logger.info("calcolaPercentuale(): risultato = "+risultato);
		return risultato;
	}
	
	private boolean nonValorizzato(BigDecimal val) {
		return (val == null || val.compareTo(BigDecimal.ZERO) == 0);
	}
	
	/*
	private String getAnnoAttoMock() {
		return "2016";
	}
	
	private String getNumeroDocSpesaMock(Long idAttoLiquidazione, String codiceVisualizzatoProgetto) {
		Calendar calendar = new GregorianCalendar();
		int minuti = calendar.get(Calendar.MINUTE);
		int secondi = calendar.get(Calendar.SECOND);
		return idAttoLiquidazione.toString()+"-"+minuti+secondi+"-"+codiceVisualizzatoProgetto;
	}
	*/
	public EsitoLeggiStatoElaborazioneDocDTO leggiStatoElaborazioneDoc(
			Long idUtente, String identitaDigitale, InputLeggiStatoElaborazioneDocDTO input )
	throws CSIException, SystemException, UnrecoverableException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale","input" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,	identitaDigitale, input);
		
		EsitoLeggiStatoElaborazioneDocDTO esito = new EsitoLeggiStatoElaborazioneDocDTO();
		esito.setEsito(false);
		logger.warn("\n\n\n##############################################\ninside leggiStatoElaborazioneDoc...");
		try {
			
			// Chiamo il servizio
			esito = bilancioDAO.leggiStatoElaborazioneDoc(input, idUtente);
			logger.info("leggiStatoElaborazioneDoc(): esito = "+esito.getEsito()+" - numeroAtto = "+esito.getNumeroAtto()+" - errore = "+esito.getErrore());
			
		} catch (Exception e) {
			logger.error("Error leggiStatoElaborazioneDoc: "+e.getMessage(), e);
			if (UserException.class.isInstance(e)) {
				throw (UserException) e;
			}
			throw new UnrecoverableException(e.getMessage(), e);
		}  

		return esito;
	}
	
 
	public DatiIntegrativiDTO findDatiIntegrativi(Long idUtente,
			String identitaDigitale, Long idAttoLiquidazione)
			throws CSIException, SystemException, UnrecoverableException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idAttoLiquidazione"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale,idAttoLiquidazione);
		
		DatiIntegrativiDTO datiIntegrativiDTO ;
		try{
			PbandiTAttoLiquidazioneVO filtroAttoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
			filtroAttoLiquidazioneVO.setIdAttoLiquidazione(beanUtil.transform(idAttoLiquidazione, BigDecimal.class));
			filtroAttoLiquidazioneVO = genericDAO.findSingleWhere(filtroAttoLiquidazioneVO);
			datiIntegrativiDTO = beanUtil.transform(filtroAttoLiquidazioneVO, DatiIntegrativiDTO.class);
			PbandiTProgettoVO pbandiTProgettoVO=null;

		
			/*if(isEmpty( filtroAttoLiquidazioneVO.getNoteAtto())){
				pbandiTProgettoVO=new PbandiTProgettoVO();
				pbandiTProgettoVO.setIdProgetto(filtroAttoLiquidazioneVO.getIdProgetto());
				pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
				String cup = pbandiTProgettoVO.getCup();
				if(isEmpty(cup)){
					cup="CUP/CIG non previsti";
				}
				datiIntegrativiDTO.setNoteAtto(cup);
			}
			*/
			if(isEmpty(datiIntegrativiDTO.getDescAtto())){
					//Precompilare il campo con i seguenti valori:
				    // CUP - 
				    // NomeBandoLinea � 
					// TitoloProgetto -
				    // CausaleLiquidazione  -
				    // codice visualizzato
				   if(pbandiTProgettoVO==null){
					  pbandiTProgettoVO=new PbandiTProgettoVO();				   
				      pbandiTProgettoVO.setIdProgetto(filtroAttoLiquidazioneVO.getIdProgetto());
					  pbandiTProgettoVO = genericDAO.findSingleWhere(pbandiTProgettoVO);
				   }
				   ProgettoBandoLineaVO progettoBandoLineaVO = new ProgettoBandoLineaVO();
				   progettoBandoLineaVO.setIdProgetto(filtroAttoLiquidazioneVO.getIdProgetto());
				   progettoBandoLineaVO = genericDAO.findSingleWhere(progettoBandoLineaVO);
				   PbandiDCausaleErogazioneVO causaleErogazioneVO=new PbandiDCausaleErogazioneVO();
				   causaleErogazioneVO.setIdCausaleErogazione(filtroAttoLiquidazioneVO.getIdCausaleErogazione());
				   causaleErogazioneVO = genericDAO.findSingleWhere(causaleErogazioneVO);
				   /*Precompilare il campo con i seguenti valori:
				    *  �CUP �codiceCup -substr(NomeBandoLinea,1,80)�CausaleLiquidazione  
se manca il CUP 
Precompilare il campo con i seguenti valori: CodiceVisualizzato -substr(NomeBandoLinea,1,80)�CausaleLiquidazione  
 (AttoLiquidazione.desc_atto)
**/
				 //  String cup = pbandiTProgettoVO.getCup();
				   //   String titoloProgetto = pbandiTProgettoVO.getTitoloProgetto();
				   String nomeBandoLinea = progettoBandoLineaVO.getDescrizioneBando();
				   String causaleLiquidazione =causaleErogazioneVO.getDescCausale();
				   String codiceVisualizzato = pbandiTProgettoVO.getCodiceVisualizzato();
				   StringBuilder sb = new StringBuilder();
				   if(!StringUtil.isEmpty(codiceVisualizzato)) {
					   if (codiceVisualizzato.length() > 45)				   
						   sb.append(codiceVisualizzato.substring(0,45));
					   else
						   sb.append(codiceVisualizzato);
				   }  
				   if(!StringUtil.isEmpty(nomeBandoLinea)) {
					   if (nomeBandoLinea.length() > 170) 
						   sb.append(" - " + nomeBandoLinea.substring(0,170));
					   else
						   sb.append(" - " + nomeBandoLinea);
				   }
				   if(!StringUtil.isEmpty(causaleLiquidazione)) {
					   if (causaleLiquidazione.length() > 40)				  
						   sb.append(" - " + causaleLiquidazione.substring(0,40));
					   else
						   sb.append(" - " + causaleLiquidazione);
				   }
				   datiIntegrativiDTO.setDescAtto(sb.toString());
			}
			
		} catch (Exception e) {
				logger.error("Errore nel findDatiIntegrativi", e);
				LiquidazioneBilancioException re = new LiquidazioneBilancioException(ERRORE_SERVER);
				throw re;
		} 
		return datiIntegrativiDTO;
	}

	public void salvaDatiIntegrativi(Long idUtente, String identitaDigitale,
			DatiIntegrativiDTO datiIntegrativi) throws CSIException,
			SystemException, UnrecoverableException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idAttoLiquidazione"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale,datiIntegrativi);
		logger.dump(datiIntegrativi);
		
		try{
			PbandiTAttoLiquidazioneVO filtroAttoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
			filtroAttoLiquidazioneVO.setIdAttoLiquidazione(beanUtil.transform(datiIntegrativi.getIdAttoLiquidazione(), BigDecimal.class));
			filtroAttoLiquidazioneVO = genericDAO.findSingleWhere(filtroAttoLiquidazioneVO);
			filtroAttoLiquidazioneVO.setDescAtto(datiIntegrativi.getDescAtto());
			if(!isNull(datiIntegrativi.getDtScadenzaAtto()))
				filtroAttoLiquidazioneVO.setDtScadenzaAtto(new java.sql.Date(datiIntegrativi.getDtScadenzaAtto().getTime()));
			
			if(datiIntegrativi.getFlagAllegatiDichiarazione()!=null){ 
				if(datiIntegrativi.getFlagAllegatiDichiarazione().equalsIgnoreCase("true") )
					datiIntegrativi.setFlagAllegatiDichiarazione("S");
				else
					datiIntegrativi.setFlagAllegatiDichiarazione("N");
			}
			
			filtroAttoLiquidazioneVO.setFlagAllegatiDichiarazione( datiIntegrativi.getFlagAllegatiDichiarazione());
		
			if(datiIntegrativi.getFlagAllegatiDocGiustificat()!=null){
				if(datiIntegrativi.getFlagAllegatiDocGiustificat().equalsIgnoreCase("true") )
					datiIntegrativi.setFlagAllegatiDocGiustificat("S");
				else
					datiIntegrativi.setFlagAllegatiDocGiustificat("N");
			}
			filtroAttoLiquidazioneVO.setFlagAllegatiDocGiustificat( datiIntegrativi.getFlagAllegatiDocGiustificat());
		
			if(datiIntegrativi.getFlagAllegatiEstrattoProv()!=null){
				if(datiIntegrativi.getFlagAllegatiEstrattoProv().equalsIgnoreCase("true") )
					datiIntegrativi.setFlagAllegatiEstrattoProv("S");
				else
					datiIntegrativi.setFlagAllegatiEstrattoProv("N");
			}
			filtroAttoLiquidazioneVO.setFlagAllegatiEstrattoProv( datiIntegrativi.getFlagAllegatiEstrattoProv());
			
			if(datiIntegrativi.getFlagAllegatiFatture()!=null){
				if(datiIntegrativi.getFlagAllegatiFatture().equalsIgnoreCase("true") )
					datiIntegrativi.setFlagAllegatiFatture("S");
				else
					datiIntegrativi.setFlagAllegatiFatture("N");
			}
			filtroAttoLiquidazioneVO.setFlagAllegatiFatture( datiIntegrativi.getFlagAllegatiFatture());
			
			filtroAttoLiquidazioneVO.setIdSettoreEnte(NumberUtil.toBigDecimal(datiIntegrativi.getIdSettoreEnte()));
			filtroAttoLiquidazioneVO.setNomeDirigenteLiquidatore( datiIntegrativi.getNomeDirigenteLiquidatore());
			filtroAttoLiquidazioneVO.setNomeLiquidatore( datiIntegrativi.getNomeLiquidatore());
			filtroAttoLiquidazioneVO.setNoteAtto( datiIntegrativi.getNoteAtto());
			filtroAttoLiquidazioneVO.setNumeroTelefonoLiquidatore( datiIntegrativi.getNumeroTelefonoLiquidatore());
			filtroAttoLiquidazioneVO.setTestoAllegatiAltro( datiIntegrativi.getTestoAllegatiAltro());
			
			filtroAttoLiquidazioneVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(filtroAttoLiquidazioneVO);
			} catch (Exception e) {
					logger.error("Errore nel salvaDatiIntegrativi", e);
					LiquidazioneBilancioException re = new LiquidazioneBilancioException(ERRORE_SERVER);
					throw re;
			}  
		
	}

	public SettoreEnteDTO[] findSettoreEnte(Long idUtente,
			String identitaDigitale, Long idSoggetto,
			Long idEnte) throws CSIException,
			SystemException, UnrecoverableException,
			LiquidazioneBilancioException {
		SettoreEnteDTO[] ret =null;
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idSoggetto"};

		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale,idSoggetto);
		try{
			SoggettoSettoreEnteCompetenzaVO filtroSettoreEnte = new SoggettoSettoreEnteCompetenzaVO();
			filtroSettoreEnte.setDescBreveTipoEnteCompetenz("ADG");
			filtroSettoreEnte.setIdSoggetto(new BigDecimal(idSoggetto));
			
			if(idEnte!=null)
				filtroSettoreEnte.setIdEnteCompetenza(new BigDecimal(idEnte));
			
			List<SoggettoSettoreEnteCompetenzaVO> list = (List<SoggettoSettoreEnteCompetenzaVO>)genericDAO.findListWhere(filtroSettoreEnte);
	         ret = beanUtil.transform(list, SettoreEnteDTO.class);
			} catch (Exception e) {
				logger.error("Errore findSettoreEnte", e);
				LiquidazioneBilancioException re = new LiquidazioneBilancioException(ERRORE_SERVER);
				throw re;
			}  
        return ret;
	}
	
	
	/**
	 * Verifica che sia stata definita almeno una quota (percentuale) per una delle fonti finanziarie associate al progetto.
	 * @param idUtente
	 * @param identitaDigitale
	 * @param idAttoLiquidazione
	 * @param progrBandoLineaIntervento
	 * @return
	 * @throws CSIException
	 * @throws SystemException
	 * @throws UnrecoverableException
	 * @throws LiquidazioneBilancioException
	 */
	public Boolean esisteQuotaFontiFinanziarie(Long idUtente,String identitaDigitale, Long idProgetto)throws CSIException,
			SystemException, UnrecoverableException,
				LiquidazioneBilancioException {
		Boolean result = new Boolean(Boolean.FALSE);
			String[] nameParameter = { "idUtente", "identitaDigitale",
			"idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale,idProgetto);
			
			/*
			 * Ricerco i soggetti finanziatori(o fonti finanziarie)
			 */
			List<SoggettiFinanziatoriPerProgettoVO> soggettiFinanziatoriNonPrivati = contoEconomicoManager
			.getSoggettiFinanziatoriNonPrivati(new BigDecimal(idProgetto));
			
			if (!ObjectUtil.isEmpty(soggettiFinanziatoriNonPrivati)) {
				for (SoggettiFinanziatoriPerProgettoVO fonte: soggettiFinanziatoriNonPrivati) {
					if (NumberUtil.compare(fonte.getPercQuotaSoggFinanziatore(), new BigDecimal(0)) > 0) {
						result = new Boolean(Boolean.TRUE);
						break;
					}
				}
			}
			
			return result;
		
	}
	
	
	public EsitoDatiProgettoDTO findDatiProgetto (Long idUtente,
			String identitaDigitale, Long idProgetto)throws CSIException,
			SystemException, UnrecoverableException,
			LiquidazioneBilancioException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
			"idProgetto"};
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale,idProgetto);
			EsitoDatiProgettoDTO result = new EsitoDatiProgettoDTO();
			result.setEsito(Boolean.TRUE);
			
			SpesaProgettoDTO spesaProgetto = new SpesaProgettoDTO();
			Double importoAmmessoAContributo = 0D;
			Double totaleSpesaAmmessa = 0D;
			Double sommaImportiQuietanzati = 0D;
			Double sommaImportiValidati = 0D;
			
			/*
			 * Ricerco i dati relativi alla spesa del progetto
			 */
			PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
			progettoVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
			progettoVO = genericDAO.findSingleWhere(progettoVO);
			
			PbandiDTipoOperazioneVO tipoOperazioneVO = new PbandiDTipoOperazioneVO();
			tipoOperazioneVO.setIdTipoOperazione(progettoVO.getIdTipoOperazione());
			try {
				tipoOperazioneVO = genericDAO.findSingleWhere(tipoOperazioneVO);
				spesaProgetto.setTipoOperazione(tipoOperazioneVO.getDescTipoOperazione());
			} catch (Exception e) {
				logger.error("Eccezione gestita. Errore in fase di ricerca del tipo operazione per il progetto "+idProgetto, e);
			}
			
			/*
			 * Calcolo importo ammesso della modalita' di agevolazione Contributo
			 */
			try {
				BigDecimal idContoEconomicoMaster = contoEconomicoManager.getIdContoMaster(new BigDecimal(idProgetto));				
				
				/* Jira PBANDI-2697: codice originale pre jira.
				PbandiDModalitaAgevolazioneVO contributoFilterVO = new PbandiDModalitaAgevolazioneVO();
				contributoFilterVO.setDescBreveModalitaAgevolaz("Contributo");
				try {
					contributoFilterVO = genericDAO.findSingleWhere(contributoFilterVO);
				} catch (Exception e) {
					logger.error("Errore durante la ricerca della modalita' di agevolazione Contributo per il progetto "+idProgetto, e);
					LiquidazioneBilancioException lbe = new LiquidazioneBilancioException("Errore durante la ricerca della modalita' di agevolazione Contributo per il progetto "+idProgetto, e);
					throw lbe;
				}
				
				PbandiRContoEconomModAgevVO modalitaContributoFilterVO = new PbandiRContoEconomModAgevVO();
				modalitaContributoFilterVO.setIdModalitaAgevolazione(contributoFilterVO.getIdModalitaAgevolazione());
				modalitaContributoFilterVO.setIdContoEconomico(idContoEconomicoMaster);
				try {
					PbandiRContoEconomModAgevVO modalitaAssociataContoMaster = genericDAO.findSingleWhere(modalitaContributoFilterVO);
					// verr� messo in spesaProgetto.setImportoAmmessoContributo()
					importoAmmessoAContributo = NumberUtil.toDouble(modalitaAssociataContoMaster.getQuotaImportoAgevolato());
				} catch (Exception e) {
					logger.error("Errore durante la ricerca della quotaImportoAgevolat per  la modalita' di agevolazione Contributo per il progetto "+idProgetto, e);
					result.setEsito(Boolean.FALSE);
					result.setMessage(ERRORE_BILANCIO_LIQUIDAZIONE_CONTRIBUTO_NON_ASSOCIATO_AL_CONTOECONOMICO);
					return result;
				}*/
				
				// Jira PBANDI-2697
				ModAgevolazioneContributoDTO dto = getModAgevolazioneContributo(idUtente, identitaDigitale, idProgetto);
				spesaProgetto.setImportoAmmessoContributo(dto.getQuotaImportoAgevolato());
				
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error("Conto economico MASTER non trovato per il progetto "+idProgetto, e);
				result.setEsito(Boolean.FALSE);
				result.setMessage(ERRORE_BILANCIO_LIQUIDAZIONE_CONTRIBUTO_NON_ASSOCIATO_AL_CONTOECONOMICO);
				return result;
			}			
			
			try {
				 totaleSpesaAmmessa = contoEconomicoManager.getTotaleSpesaAmmmessaInRendicontazioneDouble(new BigDecimal(idProgetto));
			} catch (Exception e) {
				logger.error("Eccezione gestita. Errore durante il calcolo del totale spesa ammessa in rendicondazione per il progetto "+idProgetto,e);
			}
			spesaProgetto.setTotaleSpesaAmmessa(totaleSpesaAmmessa);
			
			
			
			/*
			 * Ricerco i documenti gestiti nel progetto
			 */
			DocumentoDiSpesaDTO documentoDiSpesa = new DocumentoDiSpesaDTO();
			documentoDiSpesa.setIdProgetto(idProgetto);
			documentoDiSpesa.setIsGestitiNelProgetto(new Boolean(true));
			
			List<DocumentoDiSpesaVO> listDocumenti = getPbandiDocumentiDiSpesaDAO().findDocumentiDiSpesa(documentoDiSpesa, null);
			
			if (!ObjectUtil.isEmpty(listDocumenti)) {
				try {
					for (DocumentoDiSpesaVO obj : listDocumenti) {
						/*
						 * Ricerco i pagamenti
						 */
						List<PagamentoQuotePartiVO> listPagamenti = getPbandipagamentiDAO().findPagamentiAssociati(obj.getIdDocumentoDiSpesa(),idProgetto);
						if (!ObjectUtil.isEmpty(listPagamenti)) {
							for(PagamentoQuotePartiVO objPagamento : listPagamenti) {
								/*
								 * FIX PBandi-2351
								 */
								Double importoQuietanzato = pbandipagamentiDAO.getImportoQuietanzato(idProgetto,NumberUtil.toLong(objPagamento.getIdPagamento()));
								if (importoQuietanzato != null) {
									sommaImportiQuietanzati = NumberUtil.sum(sommaImportiQuietanzati, importoQuietanzato);
								}
								Double importoValidato = pbandipagamentiDAO.getImportoValidato(idProgetto,NumberUtil.toLong(objPagamento.getIdPagamento()));
								if (importoValidato != null) {
									sommaImportiValidati = NumberUtil.sum(sommaImportiValidati, importoValidato);
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error("Eccezione gestita. Errore durante il calcolo dell' importo validato e dell' importo quietanzato per il progetto "+idProgetto, e);
					sommaImportiQuietanzati = null;
					sommaImportiValidati = null;
				}
			}
			
			spesaProgetto.setTotaleSpesaSostenuta(sommaImportiQuietanzati);
			spesaProgetto.setTotaleSpesaValidata(sommaImportiValidati);
			/*
			 * Calcolo del totale della spesa sostenuta
			 */
			if (sommaImportiQuietanzati != null && sommaImportiQuietanzati > 0) {
				BigDecimal percAvanzamentoSpesaSostenuta = NumberUtil.percentage(sommaImportiQuietanzati, totaleSpesaAmmessa);
				spesaProgetto.setAvanzamentoSpesaSostenuta(NumberUtil.toDouble(percAvanzamentoSpesaSostenuta));
			}
			
			/*
			 * Calcolo del totale della spesa validata
			 */
			if (sommaImportiValidati != null && sommaImportiValidati > 0) {
				BigDecimal percAvanzamentoSpesaValidata = NumberUtil.percentage(sommaImportiValidati, totaleSpesaAmmessa);
				spesaProgetto.setAvanzamentoSpesaValidata(NumberUtil.toDouble(percAvanzamentoSpesaValidata));
			}
			result.setSpesaProgetto(spesaProgetto);
			
			
			
			
			/*
			 * FIDEIUSSIONI del progetto
			 */
			try {
				FideiussioneVO[] fideiussioniVO = getPbandiErogazioneDAO().findFideiussioniAttive(idProgetto);
				result.setFideiussioni(beanUtil.transform(fideiussioniVO, FideiussioneDTO.class));
			} catch (Exception e) {
				logger.error("Eccezione gestita. Errore durante la ricerca delle fideiussioni per il progetto "+idProgetto);
			}
			
			/*
			 * RICHIESTE LIQUIDAZIONE del progetto
			 */
			List<RichiestaErogazioneDTO> richiesteErogazione = new ArrayList<RichiestaErogazioneDTO>();
			BigDecimal sommaTotaliImportiRichiesteErogazioni = new BigDecimal(0D);
			try {
				RichiestaErogazioneCausaleVO richiestaErogazioneFilterVO = new RichiestaErogazioneCausaleVO();
				richiestaErogazioneFilterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
				List<RichiestaErogazioneCausaleVO> listRichiestaErogazioni = genericDAO.findListWhere(richiestaErogazioneFilterVO);
				if (!ObjectUtil.isEmpty(listRichiestaErogazioni)) {
					for (RichiestaErogazioneCausaleVO richiestaVO : listRichiestaErogazioni) {
						RichiestaErogazioneDTO dto = beanUtil.transform(richiestaVO, RichiestaErogazioneDTO.class);
						sommaTotaliImportiRichiesteErogazioni = NumberUtil.sum(sommaTotaliImportiRichiesteErogazioni, richiestaVO.getImportoRichiestaErogazione());
						richiesteErogazione.add(dto);
					}
				}
			} catch (Exception e) {
				logger.error("Eccezione gestita. Errore durante la ricerca delle richieste di erogazione per il progetto "+idProgetto,e);
				sommaTotaliImportiRichiesteErogazioni = new BigDecimal(0D);
				richiesteErogazione = new ArrayList<RichiestaErogazioneDTO>();
			}
			result.setRichiesteErogazione(richiesteErogazione.toArray(new RichiestaErogazioneDTO[]{}));
			result.setImportoTotaleRichiesto(NumberUtil.toDouble(sommaTotaliImportiRichiesteErogazioni));
			
			return result;
			
		} finally {
		}
	}
	
	private void salvaPersonaFisica(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario,
			PbandiTAttoLiquidazioneVO pbandiTAttoLiquidazioneVO, 
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO) throws Exception {
		
		PbandiTPersonaFisicaVO pbandiTPersonaFisicaVO = findPersonaFisica(dettaglioBeneficiario);
	
		if(isNull(pbandiTPersonaFisicaVO)){
			//cognome e nome data nascita , provincia e comune di nascita
			pbandiTPersonaFisicaVO=inserisciPersonaFisica(idUtente,dettaglioBeneficiario);
			pbandiTBeneficiarioBilancioVO.setIdPersonaFisica(pbandiTPersonaFisicaVO.getIdPersonaFisica());
		}
		pbandiTBeneficiarioBilancioVO.setIdPersonaFisica(pbandiTPersonaFisicaVO.getIdPersonaFisica());
	
	}

	private PbandiTPersonaFisicaVO findPersonaFisica(
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario) {
		PbandiTPersonaFisicaVO pbandiTPersonaFisicaVO=new PbandiTPersonaFisicaVO();
		pbandiTPersonaFisicaVO.setCognome(dettaglioBeneficiario.getCognome());
		pbandiTPersonaFisicaVO.setDtNascita(dettaglioBeneficiario.getDtNascita()!=null?
				new java.sql.Date(dettaglioBeneficiario.getDtNascita().getTime()):null);
		pbandiTPersonaFisicaVO.setIdComuneItalianoNascita(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneNascita()));
		pbandiTPersonaFisicaVO.setNome(dettaglioBeneficiario.getNome());
		List<PbandiTPersonaFisicaVO> listPersoneFisiche = genericDAO.findListWhere(pbandiTPersonaFisicaVO);
		if(!isEmpty(listPersoneFisiche)){
		 return	listPersoneFisiche.get(0);
		}
		return null;
	}

	private void salvaDatiPagamentoAttoEEstremiBancari(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario,
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO,
			PbandiTAttoLiquidazioneVO pbandiTAttoLiquidazioneVO,
			BigDecimal idSedeSecondaria) throws Exception {
		PbandiTDatiPagamentoAttoVO pbandiTDatiPagamentoAttoVO=new PbandiTDatiPagamentoAttoVO();
		pbandiTDatiPagamentoAttoVO.setIdDatiPagamentoAtto(pbandiTAttoLiquidazioneVO.getIdDatiPagamentoAtto());
		if(pbandiTAttoLiquidazioneVO.getIdDatiPagamentoAtto()!=null){
			pbandiTDatiPagamentoAttoVO=	genericDAO.findSingleWhere(pbandiTDatiPagamentoAttoVO);
		}
		pbandiTDatiPagamentoAttoVO.setIdSede(idSedeSecondaria);
		pbandiTDatiPagamentoAttoVO.setIdDatiPagamentoAtto(pbandiTAttoLiquidazioneVO.getIdDatiPagamentoAtto());
		pbandiTDatiPagamentoAttoVO.setCodModPagBilancio(NumberUtil.toBigDecimal(dettaglioBeneficiario.getCodModPagBilancio()));
		pbandiTDatiPagamentoAttoVO.setIdModalitaErogazione(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdModalitaErogazione()));

		/*
		 * FIX PBandi-2137.
		 * Aggiorno i dati dell' indirizzo.
		 * Se ho una sede secondaria allora inserisco idIndirizzo della sede secondaria, altrimenti inserisco l' indirizzo
		 * della sede principale
		 */
		
		IndirizzoVO filtroIndirizzoVO = new IndirizzoVO();
		filtroIndirizzoVO.setCap(dettaglioBeneficiario.getCap());
		if (!isItalia(dettaglioBeneficiario.getIdStatoSede()))
			filtroIndirizzoVO.setIdComuneEstero(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneSedeEstero()));
		else {
			filtroIndirizzoVO.setIdComune(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneSede()));
			filtroIndirizzoVO.setIdProvincia(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdProvinciaSede()));
		}
		filtroIndirizzoVO.setDescIndirizzo(dettaglioBeneficiario.getIndirizzo());
		
		List<IndirizzoVO> indirizzi = genericDAO.findListWhere(filtroIndirizzoVO);
		if (!ObjectUtil.isEmpty(indirizzi)) {
			pbandiTDatiPagamentoAttoVO.setIdIndirizzo(indirizzi.get(0).getIdIndirizzo());
		}
		
		
		/*
		 * FIX PBANDI-2130
		 * Nel caso in cui la modalita' di erogazione e' CONTO CORRENTE POSTALE => iban e bic sono null ed e' valorizzato solo il numeroconto.
		 * Nel caso in cui la modalita' di erogazione e' GIRO FONDI => iban e bic sono null ed e' valorizzato solo il numeroconto.
		 * Nel caso in cui la modalita' di erogazione e' ASSEGNO DI BONIFICO o ASSEGNO CIRCOLARE => iban, bic e numeroconto sono null.
		 * 
		 */
		if(dettaglioBeneficiario.getIban()!=null || dettaglioBeneficiario.getNumeroConto()!=null){
			PbandiTEstremiBancariVO pbandiTEstremiBancariVO=new PbandiTEstremiBancariVO();
			if (dettaglioBeneficiario.getIban() == null)
				pbandiTEstremiBancariVO.setIban(" ");	
			else 
				pbandiTEstremiBancariVO.setIban(dettaglioBeneficiario.getIban());
			pbandiTEstremiBancariVO.setBic(dettaglioBeneficiario.getBic());
			pbandiTEstremiBancariVO.setNumeroConto(dettaglioBeneficiario.getNumeroConto());
			
			
			List<PbandiTEstremiBancariVO> listEstremi = genericDAO.findListWhere(pbandiTEstremiBancariVO);
			if(!isEmpty(listEstremi)){
				pbandiTEstremiBancariVO = listEstremi.get(0);
			}else{
				pbandiTEstremiBancariVO.setIdUtenteIns(new BigDecimal(idUtente));
				pbandiTEstremiBancariVO=genericDAO.insert(pbandiTEstremiBancariVO);
			}
		
			pbandiTDatiPagamentoAttoVO.setIdEstremiBancari(pbandiTEstremiBancariVO.getIdEstremiBancari());
		} else {
			/*
			 * Sono in una modalita' di erogazione che non richiede estremi bancari,
			 * quindi setto a null IdEstremiBancari di PBANDI_T_DATI_PAGAMENTO_ATTO
			 */
			pbandiTDatiPagamentoAttoVO.setIdEstremiBancari(null);
		}
		
		if(pbandiTDatiPagamentoAttoVO.getIdDatiPagamentoAtto()!=null){
			pbandiTDatiPagamentoAttoVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(pbandiTDatiPagamentoAttoVO);
		}else{
			pbandiTDatiPagamentoAttoVO.setIdUtenteIns(new BigDecimal(idUtente));
			pbandiTDatiPagamentoAttoVO=genericDAO.insert(pbandiTDatiPagamentoAttoVO);
		}
		pbandiTAttoLiquidazioneVO.setIdDatiPagamentoAtto(pbandiTDatiPagamentoAttoVO.getIdDatiPagamentoAtto());
	}




	/*
	 *  d)	se ha modificato <Indirizzo e cap sede >  cercare nell'entit� Indirizzo una occorrenza con medesimo  indirizzo (inteso come
	 *   descrizione  indirizzo, comune, prov e cap). 
	 *   Se non la trova inserisce una nuova occorrenza  nell'entit� Indirizzo ,  e poi usa l'id del nuovo Indirizzo .
	 *   Se la trova usa l'identificativo trovato. Aggiornare il campo BeneficiarioBilanco.idIndirizzo*/
	private void salvaIndirizzo(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario,
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO) throws Exception {
		
		/*
		 * FIX PBandi-2137: Nel caso in cui esiste una sede secondaria allora dobbiamo ricercare
		 * i dati della sede principale
		 */
		
		if (dettaglioBeneficiario.getSecondario()) {
			/*
			 * Recupero i dati della sede principale
			 */
			SedeBilancioDTO sedePrincipale = dettaglioBeneficiario.getSedePrincipale();
			IndirizzoVO filtroIndirizzoVO = new IndirizzoVO();
			filtroIndirizzoVO.setCap(sedePrincipale.getCap());
			if (sedePrincipale.getDescProvincia().equalsIgnoreCase("EE"))
				filtroIndirizzoVO.setDescComuneEstero(sedePrincipale.getDescComune());
			else {
				filtroIndirizzoVO.setDescComune(sedePrincipale.getDescComune());
				filtroIndirizzoVO.setSiglaProvincia(sedePrincipale.getDescProvincia());
			}
			filtroIndirizzoVO.setDescIndirizzo(sedePrincipale.getIndirizzo());
			
			List<IndirizzoVO> indirizzi = genericDAO.findListWhere(filtroIndirizzoVO);
			PbandiTIndirizzoVO indirizzoSedePrincipaleVO = null;
			
			
			if (ObjectUtil.isEmpty(indirizzi)) {
				/*
				 * Inserisco l' indirizzo
				 */
				PbandiTIndirizzoVO indirizzoVO = new PbandiTIndirizzoVO();
				indirizzoVO.setCap(sedePrincipale.getCap());
				indirizzoVO.setDescIndirizzo(sedePrincipale.getIndirizzo());
				
				if (sedePrincipale.getDescProvincia().equalsIgnoreCase("EE")) {
					/*
					 * Gestione del paese Estero
					 */
					PbandiDComuneEsteroVO filtroComuneEsteroVO = new PbandiDComuneEsteroVO();
					filtroComuneEsteroVO.setDescComuneEstero(sedePrincipale.getDescComune());
					List<PbandiDComuneEsteroVO>  listComuni = genericDAO.findListWhere(filtroComuneEsteroVO);
					if (!ObjectUtil.isEmpty(listComuni)) {
						indirizzoVO.setIdComuneEstero(listComuni.get(0).getIdComuneEstero());
						indirizzoVO.setIdNazione(listComuni.get(0).getIdNazione());
					}
				} else {
					/*
					 * Gestione dell' ITALIA
					 */
					PbandiDComuneVO filtroComuneVO = new PbandiDComuneVO();
					filtroComuneVO.setDescComune(sedePrincipale.getDescComune());
					List<PbandiDComuneVO> listComuni = genericDAO.findListWhere(filtroComuneVO);
					if (!ObjectUtil.isEmpty(listComuni)) {
						indirizzoVO.setIdComune(listComuni.get(0).getIdComune());
						indirizzoVO.setIdProvincia(listComuni.get(0).getIdProvincia());
						/*
						 * Ricerco la provincia
						 */
						if (!ObjectUtil.isNull(listComuni.get(0).getIdProvincia())) {
							PbandiDProvinciaVO filtroProvinciaVO = new PbandiDProvinciaVO();
							filtroProvinciaVO.setIdProvincia(listComuni.get(0).getIdProvincia());
							PbandiDProvinciaVO provincia = genericDAO.findSingleOrNoneWhere(filtroProvinciaVO);
							/*
							 * Ricerco la regione
							 */
							if (!ObjectUtil.isNull(provincia)) {
								indirizzoVO.setIdRegione(provincia.getIdRegione());
								/*
								 * Ricerco la nazione ITALIA
								 */
								if (!ObjectUtil.isNull(provincia.getIdRegione())) {
									PbandiDNazioneVO filtroNazioneVO = new PbandiDNazioneVO();
									filtroNazioneVO.setDescNazione("ITALIA");
									PbandiDNazioneVO  nazione = genericDAO.findSingleOrNoneWhere(filtroNazioneVO);
									 if (!ObjectUtil.isNull(nazione)) {
										 indirizzoVO.setIdNazione(nazione.getIdNazione());
									 }
								}
							}
						}
						
					}
				}
				indirizzoVO.setIdUtenteIns(new BigDecimal(idUtente));
				indirizzoSedePrincipaleVO = genericDAO.insert(indirizzoVO);
			} else {
				/*
				 * Recupero l'indirizzo
				 */
				PbandiTIndirizzoVO filtroIndirizzo = new PbandiTIndirizzoVO();
				filtroIndirizzo.setIdIndirizzo(indirizzi.get(0).getIdIndirizzo());
				indirizzoSedePrincipaleVO = genericDAO.findSingleOrNoneWhere(filtroIndirizzo);
			}
			pbandiTBeneficiarioBilancioVO.setIdIndirizzo(indirizzoSedePrincipaleVO.getIdIndirizzo());
			
			/*
			 * Verifico che esiste anche l'indirizzo della sede secondaria
			 */
			PbandiTIndirizzoVO pbandiTIndirizzoVO=new PbandiTIndirizzoVO();
			pbandiTIndirizzoVO.setCap(dettaglioBeneficiario.getCap());
			pbandiTIndirizzoVO.setDescIndirizzo(dettaglioBeneficiario.getIndirizzo());
			pbandiTIndirizzoVO.setIdComune(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneSede()));
			pbandiTIndirizzoVO.setIdComuneEstero(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneSedeEstero()));
			pbandiTIndirizzoVO.setIdNazione(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdStatoSede()));
			pbandiTIndirizzoVO.setIdProvincia(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdProvinciaSede()));
			pbandiTIndirizzoVO.setCap(dettaglioBeneficiario.getCap());
			List<PbandiTIndirizzoVO> listIndirizzi = genericDAO.findListWhere(pbandiTIndirizzoVO);
			if (isEmpty(listIndirizzi)) {
				pbandiTIndirizzoVO.setIdUtenteIns(new BigDecimal(idUtente));
				genericDAO.insert(pbandiTIndirizzoVO);
			}
		} else {
			/*
			 * Il beneficiario non ha la sede secondaria
			 */
			PbandiTIndirizzoVO pbandiTIndirizzoVO=new PbandiTIndirizzoVO();
			pbandiTIndirizzoVO.setCap(dettaglioBeneficiario.getCap());
			pbandiTIndirizzoVO.setDescIndirizzo(dettaglioBeneficiario.getIndirizzo());
			pbandiTIndirizzoVO.setIdComune(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneSede()));
			pbandiTIndirizzoVO.setIdComuneEstero(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneSedeEstero()));
			pbandiTIndirizzoVO.setIdNazione(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdStatoSede()));
			pbandiTIndirizzoVO.setIdProvincia(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdProvinciaSede()));
			pbandiTIndirizzoVO.setCap(dettaglioBeneficiario.getCap());
			List<PbandiTIndirizzoVO> listIndirizzi = genericDAO.findListWhere(pbandiTIndirizzoVO);
			if (!isEmpty(listIndirizzi)) {
				pbandiTIndirizzoVO=	listIndirizzi.get(0);
				pbandiTBeneficiarioBilancioVO.setIdIndirizzo(pbandiTIndirizzoVO.getIdIndirizzo());
			} else {
				pbandiTIndirizzoVO.setIdUtenteIns(new BigDecimal(idUtente));
				pbandiTIndirizzoVO=genericDAO.insert(pbandiTIndirizzoVO);
				pbandiTBeneficiarioBilancioVO.setIdIndirizzo(pbandiTIndirizzoVO.getIdIndirizzo());
			}
		}
	}

	private void salvaSede(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario,
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO) throws Exception {
		PbandiTSedeVO pbandiTSedeVO=new PbandiTSedeVO();
		if(dettaglioBeneficiario.getPartitaIva()!=null){
			pbandiTSedeVO.setPartitaIva(dettaglioBeneficiario.getPartitaIva());
			List<PbandiTSedeVO> listSedi = genericDAO.findListWhere(pbandiTSedeVO);
			if(!isEmpty(listSedi)){
				pbandiTSedeVO = listSedi.get(0);
				pbandiTBeneficiarioBilancioVO.setIdSede(pbandiTSedeVO.getIdSede());
				
			}else{
				pbandiTSedeVO.setIdUtenteIns(new BigDecimal(idUtente));
				pbandiTSedeVO=genericDAO.insert(pbandiTSedeVO);
				pbandiTBeneficiarioBilancioVO.setIdSede(pbandiTSedeVO.getIdSede());
			}
		}
	}


	
	private void salvaRecapiti(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario,
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO)
			throws Exception {
		if(dettaglioBeneficiario.getMail()!=null){
			PbandiTRecapitiVO pbandiTRecapitiVO=new PbandiTRecapitiVO();
			pbandiTRecapitiVO.setEmail(dettaglioBeneficiario.getMail());
			List<PbandiTRecapitiVO> recapiti = genericDAO.findListWhere(pbandiTRecapitiVO);
			if(!isEmpty(recapiti)){
				pbandiTRecapitiVO =	recapiti.get(0);
				pbandiTBeneficiarioBilancioVO.setIdRecapiti(pbandiTRecapitiVO.getIdRecapiti());
			}else{
				pbandiTRecapitiVO.setIdUtenteIns(new BigDecimal(idUtente));
				pbandiTRecapitiVO=genericDAO.insert(pbandiTRecapitiVO);
				pbandiTBeneficiarioBilancioVO.setIdRecapiti(pbandiTRecapitiVO.getIdRecapiti());
			}
			
		}
	}

	
	
	private PbandiTPersonaFisicaVO inserisciPersonaFisica(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario) throws Exception {
		PbandiTPersonaFisicaVO pbandiTPersonaFisicaVO=new PbandiTPersonaFisicaVO();
		pbandiTPersonaFisicaVO.setCognome(dettaglioBeneficiario.getCognome());
		pbandiTPersonaFisicaVO.setDtNascita(dettaglioBeneficiario.getDtNascita()!=null?
				new java.sql.Date(dettaglioBeneficiario.getDtNascita().getTime()):null);
		pbandiTPersonaFisicaVO.setIdComuneItalianoNascita(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdComuneNascita()));
		pbandiTPersonaFisicaVO.setIdNazioneNascita(NumberUtil.toBigDecimal(dettaglioBeneficiario.getIdStatoNascita()));
		pbandiTPersonaFisicaVO.setIdSoggetto(new BigDecimal(dettaglioBeneficiario.getIdSoggetto()));
		pbandiTPersonaFisicaVO.setNome(dettaglioBeneficiario.getNome());
		pbandiTPersonaFisicaVO.setSesso(dettaglioBeneficiario.getSesso());
		pbandiTPersonaFisicaVO.setIdUtenteIns(new BigDecimal(idUtente));
		pbandiTPersonaFisicaVO=genericDAO.insert(pbandiTPersonaFisicaVO);
		return pbandiTPersonaFisicaVO;
	}



	private boolean salvaEnteGiuridico(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario,
			PbandiTAttoLiquidazioneVO pbandiTAttoLiquidazioneVO,
			PbandiTBeneficiarioBilancioVO pbandiTBeneficiarioBilancioVO) throws Exception {
	
		PbandiTEnteGiuridicoVO pbandiTEnteGiuridicoVO = findEnteGiuridico(dettaglioBeneficiario);
		if(pbandiTEnteGiuridicoVO==null){
		
			 pbandiTEnteGiuridicoVO = inserisciEnteGiuridico(idUtente,dettaglioBeneficiario);
			 pbandiTBeneficiarioBilancioVO.setIdEnteGiuridico(pbandiTEnteGiuridicoVO.getIdEnteGiuridico());
			 return true;
		}
		pbandiTBeneficiarioBilancioVO.setIdEnteGiuridico(pbandiTEnteGiuridicoVO.getIdEnteGiuridico());
		return false;
	}

	private PbandiTEnteGiuridicoVO findEnteGiuridico(
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario) {
		PbandiTEnteGiuridicoVO pbandiTEnteGiuridicoVO = new PbandiTEnteGiuridicoVO();
		pbandiTEnteGiuridicoVO.setDenominazioneEnteGiuridico(dettaglioBeneficiario.getRagioneSociale());
		List<PbandiTEnteGiuridicoVO> enti = genericDAO.findListWhere(pbandiTEnteGiuridicoVO);
		if(!isEmpty(enti)){
			return enti.get(0);
		}
		return null;
	}

	private PbandiTEnteGiuridicoVO inserisciEnteGiuridico(Long idUtente,
			DettaglioBeneficiarioBilancioDTO dettaglioBeneficiario) throws Exception {
		PbandiTEnteGiuridicoVO pbandiTEnteGiuridicoVO=new PbandiTEnteGiuridicoVO();
		pbandiTEnteGiuridicoVO.setDenominazioneEnteGiuridico(dettaglioBeneficiario.getRagioneSociale());
		pbandiTEnteGiuridicoVO.setIdSoggetto(new BigDecimal(dettaglioBeneficiario.getIdSoggetto()));
		pbandiTEnteGiuridicoVO.setIdUtenteIns(new BigDecimal(idUtente));
		pbandiTEnteGiuridicoVO=genericDAO.insert(pbandiTEnteGiuridicoVO);
		return pbandiTEnteGiuridicoVO;
		
	}
	
	private void aggiornaAttoLiquidazione(Long idUtente,
			CreaAttoVO creaAttoVO, InserisciAttoLiquidazioneVO inserisciAttoLiquidazioneVO) throws LiquidazioneBilancioException{
		DecodificaDTO decodificaVO = decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_ATTO , Constants.DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_EMESSO
				);
		PbandiTAttoLiquidazioneVO attoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
		attoLiquidazioneVO.setIdAttoLiquidazione(creaAttoVO.getIdAttoLiquidazione());
		attoLiquidazioneVO.setIdStatoAtto(new BigDecimal(decodificaVO.getId()));
		attoLiquidazioneVO.setNumeroAtto(inserisciAttoLiquidazioneVO.getAttoLiquidazione().getAtto().getNroAtto());
		PbandiTBeneficiarioBilancioVO beneficiarioBilancioVO = new PbandiTBeneficiarioBilancioVO();
		beneficiarioBilancioVO.setIdBeneficiarioBilancio(creaAttoVO.getIdBeneficiarioBilancio());
		BigDecimal codiceBeneficiario = beanUtil.transform(inserisciAttoLiquidazioneVO.getAttoLiquidazione().getFornitore().getCodben(), BigDecimal.class);
		beneficiarioBilancioVO.setCodiceBeneficiarioBilancio(codiceBeneficiario);
		PbandiTDatiPagamentoAttoVO datiPagamentoAttoVO = new PbandiTDatiPagamentoAttoVO();
		datiPagamentoAttoVO.setIdDatiPagamentoAtto(creaAttoVO.getIdDatiPagamentoAtto());
		BigDecimal codModPagamentoBilancio = beanUtil.transform(inserisciAttoLiquidazioneVO.getAttoLiquidazione().getBeneficiario().getProgBen(), BigDecimal.class);
		datiPagamentoAttoVO.setCodModPagBilancio(codModPagamentoBilancio);
		
		try {
			attoLiquidazioneVO.setIdUtenteAgg(new BigDecimal(idUtente));
			beneficiarioBilancioVO.setIdUtenteAgg(new BigDecimal(idUtente));
			datiPagamentoAttoVO.setIdUtenteAgg(new BigDecimal(idUtente));
			logger.warn("\n\nupdating attoLiquidazioneVO on db, attoLiquidazioneVO.getNumeroAtto(): "+attoLiquidazioneVO.getNumeroAtto());
			genericDAO.update(attoLiquidazioneVO);
			genericDAO.update(beneficiarioBilancioVO);
			genericDAO.update(datiPagamentoAttoVO);
			
		} catch (Exception ex) {
			logger.error("Errore nell'aggiornamento dei dati dell'atto di liquidazione", ex);
			LiquidazioneBilancioException re = new LiquidazioneBilancioException(ERRORE_SERVER);
			throw re;
		}  
		
	}

	



	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EnteCompetenzaDTO[] findEntiCompetenza(
			Long idUtente, String identitaDigitale, Long idSoggetto)
			throws CSIException, SystemException, UnrecoverableException,
			LiquidazioneBilancioException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto"};

			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto);
		
			List<SoggettoEnteCompetenzaVO> entiCompetenzaSoggetto = bilancioManager.findEntiCompetenzaSoggetto(idSoggetto, "ADG","descEnte");
			EnteCompetenzaDTO[] entiCompetenza = beanUtil.transform(entiCompetenzaSoggetto, EnteCompetenzaDTO.class);
			return entiCompetenza;
	}
	
	
	private PbandiDNazioneVO getItalia () {
		PbandiDNazioneVO nazioneVO = new PbandiDNazioneVO();
		nazioneVO.setDescNazione("ITALIA");
		return genericDAO.findSingleOrNoneWhere(nazioneVO);
	}
	
	
	private boolean isItalia(Long idNazione) {
		boolean result = false;
		if (idNazione != null) {
			PbandiDNazioneVO italia = getItalia();
			if (!ObjectUtil.isNull(italia) && italia.getIdNazione() != null) {
				if (italia.getIdNazione().compareTo(NumberUtil.toBigDecimal(idNazione)) == 0 )
					result = true;
			}
		} 
		
		return result;
	}

	// CDU-110-V03 inizio
	
	private String getCodiceVisualizzatoProgetto(Long idAttoLiquidazione) {
		String cvp = "";
		PbandiTAttoLiquidazioneVO atto = new PbandiTAttoLiquidazioneVO();
		atto.setIdAttoLiquidazione(new BigDecimal(idAttoLiquidazione));
		atto = genericDAO.findSingleOrNoneWhere(atto);
		if (atto != null) {
			PbandiTProgettoVO progetto = new PbandiTProgettoVO();
			progetto.setIdProgetto(atto.getIdProgetto());
			progetto = genericDAO.findSingleOrNoneWhere(progetto);
			if (progetto != null) {
				cvp = progetto.getCodiceVisualizzato();						
			} else {
				logger.info("getCodiceVisualizzatoProgetto(): nessun PbandiTProgetto con id "+atto.getIdProgetto());
			}
		} else {
			logger.info("getCodiceVisualizzatoProgetto(): nessun PbandiTAttoLiquidazione con id "+idAttoLiquidazione);
		}
		logger.info("getCodiceVisualizzatoProgetto(): idAttoLiquidazione "+idAttoLiquidazione+" => CodiceVisualizzatoProgetto "+cvp);
		return cvp;
	}

	// NumeroDocSpesa = idAttoLiquidazione+"-"CodiceVisualizzatoProgetto
	private String getNumeroDocSpesa(BigDecimal idAttoLiquidazione) {
		String numeroDocSpesa = ""+idAttoLiquidazione.intValue();
		if (idAttoLiquidazione != null) {
			PbandiTAttoLiquidazioneVO atto = new PbandiTAttoLiquidazioneVO();
			atto.setIdAttoLiquidazione(idAttoLiquidazione);
			atto = genericDAO.findSingleOrNoneWhere(atto);
			if (atto != null) {
				PbandiTProgettoVO progetto = new PbandiTProgettoVO();
				progetto.setIdProgetto(atto.getIdProgetto());
				progetto = genericDAO.findSingleOrNoneWhere(progetto);
				if (progetto != null) {
					numeroDocSpesa = numeroDocSpesa + progetto.getCodiceVisualizzato();						
				} else {
					logger.info("getNumeroDocSpesa(): nessun PbandiTProgetto con id "+atto.getIdProgetto());
				}
			} else {
				logger.info("getNumeroDocSpesa(): nessun PbandiTAttoLiquidazione con id "+idAttoLiquidazione);
			}
		} else {
			logger.info("getNumeroDocSpesa(): idAttoLiquidazione nullo");
		}
		numeroDocSpesa = numeroDocSpesa+"-"+idAttoLiquidazione.intValue();
		if (numeroDocSpesa.length() > 20)
			numeroDocSpesa = numeroDocSpesa.substring(0, 20);
		logger.info("getNumeroDocSpesa(): idAttoLiquidazione "+idAttoLiquidazione+" => NumeroDocSpesa "+numeroDocSpesa);
		return numeroDocSpesa;
	}

	// CDU-110-V03 fine
	
	public AliquotaRitenutaAttoDTO[] getAliquotaRitenutaAtto(Long idUtente, String identitaDigitale) 
		throws CSIException, SystemException, UnrecoverableException, LiquidazioneBilancioException  {
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale);		
		try {
			NullCondition<PbandiDAliquotaRitenutaVO> dtFineValiditaNullCondition = new NullCondition<PbandiDAliquotaRitenutaVO>(PbandiDAliquotaRitenutaVO.class, "dtFineValidita");
			PbandiDAliquotaRitenutaVO filtro = new PbandiDAliquotaRitenutaVO();
			filtro.setIdTipoRitenuta(new BigDecimal(3));
			filtro.setAscendentOrder("idAliquotaRitenuta");
			List<PbandiDAliquotaRitenutaVO> lista1 = genericDAO.findListWhere(
					new AndCondition<PbandiDAliquotaRitenutaVO>(dtFineValiditaNullCondition,Condition.filterBy(filtro)
			));			
			AliquotaRitenutaAttoDTO[] lista2 = new AliquotaRitenutaAttoDTO[lista1.size()];
			int i = 0;
			for (PbandiDAliquotaRitenutaVO vo : lista1) {
				AliquotaRitenutaAttoDTO dto = new AliquotaRitenutaAttoDTO();
				dto.setCodNaturaOnere(vo.getCodNaturaOnere());
				dto.setCodOnere(vo.getCodOnere());				
				dto.setIdAliquotaRitenuta(vo.getIdAliquotaRitenuta().longValue());
				if (vo.getPercCaricoEnte() != null) {
					dto.setPercCaricoEnte(vo.getPercCaricoEnte().doubleValue());
				}
				if (vo.getPercCaricoSoggetto() != null) {
					dto.setPercCaricoSoggetto(vo.getPercCaricoSoggetto().doubleValue());
				}
				
				String s = vo.getCodNaturaOnere()+"-"+vo.getDescNaturaOnere()+"-"+vo.getCodOnere()+"-"+vo.getDescAliquota();
				dto.setDescNaturaOnere(s);

				lista2[i++] = dto;
			}
			return lista2;			
		} catch (Exception ex) {
			logger.error("ERRORE in getAliquotaRitenutaAtto: ", ex);
			LiquidazioneBilancioException re = new LiquidazioneBilancioException("Errore imprevisto nella lettura delle aliquote ritenuta.");
			throw re;
		}  
	}
	
	public Long getIdAliquotaRitenuta(Long idUtente, String identitaDigitale, Long idProgetto) 
	throws CSIException, SystemException, UnrecoverableException, LiquidazioneBilancioException  {
	String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
	ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idProgetto);		
	try {
		
		PbandiTProgettoVO p = new PbandiTProgettoVO();
		p.setIdProgetto(new BigDecimal(idProgetto));
		p = genericDAO.findSingleWhere(p);
		
		if (p.getIdDomanda() == null) {
			return null;
		}
		
		PbandiTDomandaVO d = new PbandiTDomandaVO();
		d.setIdDomanda(p.getIdDomanda());
		d = genericDAO.findSingleWhere(d);
		
		if (d.getIdAliquotaRitenuta() == null) {
			return null;
		} else {
			return d.getIdAliquotaRitenuta().longValue();
		}
		
	} catch (Exception ex) {
		logger.error("ERRORE in getIdAliquotaRitenuta: ", ex);
		LiquidazioneBilancioException re = new LiquidazioneBilancioException("Errore imprevisto nella lettura dell'id aliquota ritenuta.");
		throw re;
	}  
}

	public PbandiDAliquotaRitenutaVO getAliquota(PbandiDAliquotaRitenutaVO aliquotaVO) {
		return genericDAO.findSingleWhere(aliquotaVO);
	
	}
	
}
