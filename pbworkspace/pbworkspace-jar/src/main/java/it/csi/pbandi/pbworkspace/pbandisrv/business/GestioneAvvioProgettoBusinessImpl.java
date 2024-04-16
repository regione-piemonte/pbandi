/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.business;

import static it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition.filterBy;
import static it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition.filterByKeyOf;
import static it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition.isFieldNull;
import static it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition.not;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioCspConCivicoVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ComuneVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.ComuneVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CspProgettoSoggettoBeneficiarioVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.CspProgettoSoggettoBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaLightVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RapprLegaleCspVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo.RapprLegaleCspConCivicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.LikeContainsCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.LikeStartsWithCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDAttivitaAtecoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDCategoriaCipeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDClassificazioneRaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneEsteroVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDNaturaCipeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDNazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDObiettivoGenQsnVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDObiettivoSpecifQsnVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDObiettivoTemVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDProvinciaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDRegioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDSettoreCipeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDSottoSettoreCipeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoEnteCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoSoggCorrelatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoSoggettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipologiaCipeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRObtemClassraVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTBandoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCspProgSedeIntervVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCspProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCspSoggRuoloEnteVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCspSoggettoVO;
import it.csi.pbandi.pbworkspace.pbandisrv.integration.db.vo.PbandiTCspSoggettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteCompetenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIndirizzoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.TmpNumDomandaGefoVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbworkspace.util.BeanUtil;
import it.csi.pbandi.pbworkspace.util.DateUtil;
import it.csi.pbandi.pbworkspace.pbandisrv.BusinessImpl;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.BeneficiarioCspDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ComuneDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoAvvioProcessoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSchedaProgettoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.IndirizziRapprLegaleCspDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ProgettoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.RapprLegaleCspDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeInterventoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeLegaleBeneficiarioCspDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPFDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoPGDTO;
import it.csi.pbandi.pbworkspace.pbandisrv.dto.simon.DettaglioCupVO;
import it.csi.pbandi.pbworkspace.pbandisrv.exception.GestioneAvvioProgettoException;

public class GestioneAvvioProgettoBusinessImpl extends BusinessImpl implements GestioneAvvioProgettoSrv {

	private static final String DESC_BREVE_TIPO_ENTE_COMPETENZA_DIREZIONE_REGIONALE = "ADG";
	private static final String DESC_BREVE_TIPO_ENTE_COMPETENZA_PUBBLICA_AMMINISTRAZIONE = "PA";

	private static final String BENEFICIARIO = "beneficiario";
	private static final String FLAG_PERSONA_FISICA = "flagPersonaFisica";
	private static final String RAPPRESENTANTE_LEGALE = "rappresentanteLegale";
	private static final String STATO_PROG_RELAZ_PROGRAMMA_ATTIVO = "1";
	private static final String STATO_PROG_RELAZ_PROGRAMMA_INATTIVO = "2";

	private static final BidiMap MAP_PROGETTO_DTO_TO_VO = new TreeBidiMap();
	private static final BidiMap MAP_PROGETTO_VO_TO_DTO;

	private static final BidiMap MAP_SEDE_DTO_TO_VO = new TreeBidiMap();
	private static final BidiMap MAP_SEDE_VO_TO_DTO;

	private static final BidiMap MAP_COMUNE_DTO_TO_VO = new TreeBidiMap();

	private static final BidiMap MAP_SOGG_DTO_TO_VO = new TreeBidiMap();
	private static final BidiMap MAP_SOGG_VO_TO_DTO;

	private static final BidiMap MAP_PF_DTO_TO_VO = new TreeBidiMap();
	private static final BidiMap MAP_PF_VO_TO_DTO;

	private static final BidiMap MAP_PG_DTO_TO_VO = new TreeBidiMap();
	private static final BidiMap MAP_PG_VO_TO_DTO;

	private static final BidiMap MAP_DETTAGLIO_CUP_DTO_TO_VO = new TreeBidiMap();
	private static final BidiMap MAP_DETTAGLIO_CUP_VO_TO_DTO;

	static {
		MAP_PROGETTO_DTO_TO_VO.put("idProgetto", "idCspProgetto");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.titoloProgetto", "titoloProgetto");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.dataConcessione", "dtConcessione");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.dataGenerazione", "dtInizioValidita");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.dataComitato", "dtComitato");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagCardine", "flagCardine");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagGeneratoreEntrate", "flagGeneratoreEntrate");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagLeggeObiettivo", "flagLeggeObbiettivo");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagAltroFondo", "flagAltroFondo");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idTipoAiuto", "idTipoAiuto");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idTipologiaCipe", "idTipologiaCipe");
		MAP_PROGETTO_DTO_TO_VO.put("idBandoLinea", "progrBandoLineaIntervento");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idObiettivoSpecificoQsn", "idObiettivoSpecifQsn");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idSettoreCpt", "idSettoreCpt");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idTemaPrioritario", "idTemaPrioritario");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idIndicatoreRisultatoProgramma", "idIndRisultatoProgram");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idTipoOperazione", "idTipoOperazione");

		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idIndicatoreQsn", "idIndicatoreQsn");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idCategoriaCipe", "idCategoriaCipe");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.numeroDomanda", "numeroDomanda");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idAttivitaAteco", "idAttivitaAteco");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.cup", "cup");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idStrumentoAttuativo", "idStrumentoAttuativo");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idDimensioneTerritoriale", "idDimensioneTerritor");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idProgettoComplesso", "idProgettoComplesso");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idTipoStrumentoProgrammazione", "idTipoStrumentoProgr");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagDettaglioCup", "flagDettaglioCup");

		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagBeneficiarioCup", "flagBeneficiarioCup");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.annoConcessioneOld", "annoConcessioneOld");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.dataPresentazioneDomanda", "dtPresentazioneDomanda");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagProgettoDaInviareAlMonitoraggio", "flagInvioMonit");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagRichiestaAutomaticaDelCup", "flagRichiestaCup");
		// programamzione 2014_20
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idObiettivoTematico", "idObiettivoTem");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idClassificazioneRA", "idClassificazioneRa");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.idGrandeProgetto", "idGrandeProgetto");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagPPP", "flagPPP");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.flagStrategico", "flagStrategico");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.dtFirmaAccordo", "dtFirmaAccordo");
		MAP_PROGETTO_DTO_TO_VO.put("informazioniBase.dtCompletamentoValutazione", "dtCompletamentoValutazione");

		MAP_PROGETTO_VO_TO_DTO = MAP_PROGETTO_DTO_TO_VO.inverseBidiMap();

		MAP_SEDE_DTO_TO_VO.put("partitaIva", "partitaIva");
		MAP_SEDE_DTO_TO_VO.put("indirizzo", "indirizzo");
		MAP_SEDE_DTO_TO_VO.put("numeroCivico", "civico"); // PBANDI-2408
		MAP_SEDE_DTO_TO_VO.put("cap", "cap");
		MAP_SEDE_DTO_TO_VO.put("email", "email");
		MAP_SEDE_DTO_TO_VO.put("fax", "fax");
		MAP_SEDE_DTO_TO_VO.put("telefono", "telefono");
		MAP_SEDE_DTO_TO_VO.put("comuneSede.idProvincia", "idProvincia");
		MAP_SEDE_DTO_TO_VO.put("comuneSede.idRegione", "idRegione");
		MAP_SEDE_DTO_TO_VO.put("comuneSede.idNazione", "idNazione");

		MAP_COMUNE_DTO_TO_VO.put("idProvincia", "idProvincia");
		MAP_COMUNE_DTO_TO_VO.put("idRegione", "idRegione");
		MAP_COMUNE_DTO_TO_VO.put("idNazione", "idNazione");

		MAP_SEDE_VO_TO_DTO = MAP_SEDE_DTO_TO_VO.inverseBidiMap();

		MAP_SOGG_VO_TO_DTO = MAP_SOGG_DTO_TO_VO.inverseBidiMap();

		Map<String, String> MAP_PERSONA_DTO_TO_VO = new HashMap<String, String>();
		MAP_PERSONA_DTO_TO_VO.put("iban", "iban");
		MAP_PERSONA_DTO_TO_VO.put("idRelazioneColBeneficiario", "idTipoSoggettoCorrelato");

		MAP_PF_DTO_TO_VO.putAll(MAP_PERSONA_DTO_TO_VO);
		MAP_PF_DTO_TO_VO.put("codiceFiscale", "codiceFiscale");
		MAP_PF_DTO_TO_VO.put("cognome", "cognome");
		MAP_PF_DTO_TO_VO.put("nome", "nome");
		MAP_PF_DTO_TO_VO.put("dataNascita", "dtNascita");
		MAP_PF_DTO_TO_VO.put("sesso", "sesso");
		MAP_PF_DTO_TO_VO.put("indirizzoRes", "indirizzoPf");
		MAP_PF_DTO_TO_VO.put("numCivicoRes", "civico");
		MAP_PF_DTO_TO_VO.put("capRes", "capPf");
		MAP_PF_DTO_TO_VO.put("emailRes", "emailPf");
		MAP_PF_DTO_TO_VO.put("faxRes", "faxPf");
		MAP_PF_DTO_TO_VO.put("telefonoRes", "telefonoPf");

		MAP_PF_VO_TO_DTO = MAP_PF_DTO_TO_VO.inverseBidiMap();

		MAP_PG_DTO_TO_VO.putAll(MAP_PERSONA_DTO_TO_VO);
		MAP_PG_DTO_TO_VO.put("codiceFiscale", "codiceFiscale");
		MAP_PG_DTO_TO_VO.put("denominazione", "denominazione");
		MAP_PG_DTO_TO_VO.put("partitaIvaSedeLegale", "partitaIvaSedeLegale");
		MAP_PG_DTO_TO_VO.put("indirizzoSedeLegale", "indirizzoSedeLegale");
		MAP_PG_DTO_TO_VO.put("numCivicoSedeLegale", "civico"); // PBANDI-2408
		MAP_PG_DTO_TO_VO.put("capSedeLegale", "capSedeLegale");
		MAP_PG_DTO_TO_VO.put("emailSedeLegale", "emailSedeLegale");
		MAP_PG_DTO_TO_VO.put("pecSedeLegale", "pecSedeLegale");
		MAP_PG_DTO_TO_VO.put("faxSedeLegale", "faxSedeLegale");
		MAP_PG_DTO_TO_VO.put("telefonoSedeLegale", "telefonoSedeLegale");
		MAP_PG_DTO_TO_VO.put("formaGiuridica", "idFormaGiuridica");
		MAP_PG_DTO_TO_VO.put("attivitaAteco", "idAttivitaAteco");
		MAP_PG_DTO_TO_VO.put("dimensioneImpresa", "idDimensioneImpresa");
		MAP_PG_DTO_TO_VO.put("dataCostituzioneAzienda", "dtCostituzioneAzienda");

		MAP_PG_VO_TO_DTO = MAP_PG_DTO_TO_VO.inverseBidiMap();

		MAP_DETTAGLIO_CUP_DTO_TO_VO.put("informazioniBase.cup", "datiGeneraliProgetto.codiceCup");
		MAP_DETTAGLIO_CUP_DTO_TO_VO.put("informazioniBase.numeroDomanda", "datiGeneraliProgetto.codiceProgetto");
		MAP_DETTAGLIO_CUP_DTO_TO_VO.put("informazioniBase.dataGenerazione", "datiGeneraliProgetto.dataGenerazione");
		MAP_DETTAGLIO_CUP_DTO_TO_VO.put("informazioniBase.titoloProgetto", "descrizioneDettaglioCup.titoloProgetto");

		MAP_DETTAGLIO_CUP_VO_TO_DTO = MAP_DETTAGLIO_CUP_DTO_TO_VO.inverseBidiMap();

	}

	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private ProgettoManager progettoManager;

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

	/*
	 * @Autowired private SoggettoManager soggettoManager; public SoggettoManager
	 * getSoggettoManager() { return soggettoManager; } public void
	 * setSoggettoManager(SoggettoManager soggettoManager) { this.soggettoManager =
	 * soggettoManager; }
	 */

	/*
	 * private Checker checker; private DettaglioCupManager dettaglioCupManager;
	 * private NeofluxSrv neofluxBusiness; private SimonDAO simonDAO; private
	 * SoggettoPGDTO datiPG;
	 * 
	 * 
	 * public NeofluxSrv getNeofluxBusiness() { return neofluxBusiness; }
	 * 
	 * public void setNeofluxBusiness(NeofluxSrv neofluxBusiness) {
	 * this.neofluxBusiness = neofluxBusiness; }
	 * 
	 * public void setChecker(Checker checker) { this.checker = checker; }
	 * 
	 * public Checker getChecker() { return checker; }
	 * 
	 * 
	 * 
	 * public SimonDAO getSimonDAO() { return simonDAO; }
	 * 
	 * public void setSimonDAO(SimonDAO simonDAO) { this.simonDAO = simonDAO; }
	 * 
	 */

	public ProgettoDTO[] findProgetti(Long idUtente, String identitaIride, String codUtente, ProgettoDTO progetto,
			Boolean visualizzaSoloSchedeProgetto)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {

		String[] nameParameter = { "codUtente" };
		ValidatorInput.verifyNullValue(nameParameter, codUtente);

		ProgettoDTO[] result = null;
		try {
			Map<String, String> mapVOToDTO = new HashMap<String, String>();
			mapVOToDTO.put("titoloProgetto", "titolo");
			mapVOToDTO.put(BENEFICIARIO, BENEFICIARIO);

			// Boolean visualizzaSoloSchedeProgetto =
			// getSoggettoManager().hasPermessoUtenteCorrente(idUtente,identitaIride,UseCaseConstants.UC_TRSCSP001);

			logger.info("getBeneficiario  " + progetto.getBeneficiario());
			logger.info("getCodice  " + progetto.getCodice());
			logger.info("getCup  " + progetto.getCup());
			logger.info("getIdBandoLinea  " + progetto.getIdBandoLinea());
			logger.info("getTitolo  " + progetto.getTitolo());
			logger.info("getId  " + progetto.getId());
			logger.info("visualizzaSoloSchedeProgetto  " + visualizzaSoloSchedeProgetto);

			if (visualizzaSoloSchedeProgetto != null && visualizzaSoloSchedeProgetto) {
				mapVOToDTO.put("idCspProgetto", "id");
				mapVOToDTO.put("cup", "cup");
				mapVOToDTO.put("numeroDomanda", "codice");

				Map<String, String> mapDTOToVO = new HashMap<String, String>();
				mapDTOToVO.put("titolo", "titoloProgetto");
				mapDTOToVO.put("cup", "cup");
				mapDTOToVO.put("codice", "numeroDomanda");
				mapDTOToVO.put(BENEFICIARIO, BENEFICIARIO);
				CspProgettoSoggettoBeneficiarioVO filtroForm = beanUtil.transform(progetto,
						CspProgettoSoggettoBeneficiarioVO.class, mapDTOToVO);

				CspProgettoSoggettoBeneficiarioVO filtroBandoLinea = new CspProgettoSoggettoBeneficiarioVO();
				filtroBandoLinea
						.setProgrBandoLineaIntervento(beanUtil.transform(progetto.getIdBandoLinea(), BigDecimal.class));

				result = beanUtil.transform(genericDAO
						.where(filterBy(filtroBandoLinea)
								.and(isFieldNull(CspProgettoSoggettoBeneficiarioVO.class, "dtElaborazione"))
								.and(new LikeStartsWithCondition<CspProgettoSoggettoBeneficiarioVO>(filtroForm)))
						.select(), ProgettoDTO.class, mapVOToDTO);

			} else {
				mapVOToDTO.put("idProgetto", "id");
				mapVOToDTO.put("codiceVisualizzato", "codice");
				mapVOToDTO.put("cup", "cup");

				ProgettoBandoLineaVO progettoVO = new ProgettoBandoLineaVO();
				progettoVO.setCodiceVisualizzato(progetto.getCodice());
				progettoVO.setTitoloProgetto(progetto.getTitolo());
				progettoVO.setCup(progetto.getCup());
				ProgettoBandoLineaVO beneficiarioProgettoVO = new ProgettoBandoLineaVO();
				beneficiarioProgettoVO.setBeneficiario(progetto.getBeneficiario());
				ProgettoBandoLineaVO progettoFiltroVO = new ProgettoBandoLineaVO();
				progettoFiltroVO.setIdBandoLinea(beanUtil.transform(progetto.getIdBandoLinea(), Long.class));

				List<String> isNullProperties = new ArrayList<String>();
				isNullProperties.add("idIstanzaProcesso");

				List<ProgettoBandoLineaVO> progettiVO = genericDAO.findListWhere(new AndCondition<ProgettoBandoLineaVO>(
						new LikeStartsWithCondition<ProgettoBandoLineaVO>(progettoVO),
						new LikeContainsCondition<ProgettoBandoLineaVO>(beneficiarioProgettoVO),
						new FilterCondition<ProgettoBandoLineaVO>(progettoFiltroVO),
						new NullCondition<ProgettoBandoLineaVO>(ProgettoBandoLineaVO.class, "idIstanzaProcesso"),
						new NullCondition<ProgettoBandoLineaVO>(ProgettoBandoLineaVO.class, "flagPrenotAvvio")));

				result = getBeanUtil().transform(progettiVO.toArray(), ProgettoDTO.class, mapVOToDTO);
			}
		} catch (Exception e) {
			String message = "Impossibile caricare la lista dei progetti:" + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}
		return result;
	}

	public EsitoAvvioProcessoDTO[] avviaProgetto(Long idUtente, String identitaIride, String codUtente,
			String idDefinizioneProcesso, Long[] idProgetti, Boolean abilitatoUC_TRSCSP001)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {

		logger.begin();
		List<EsitoAvvioProcessoDTO> result = new ArrayList<EsitoAvvioProcessoDTO>();

		logger.debug("idDefinizioneProcesso=" + idDefinizioneProcesso);
		logger.debug("idProgetti=" + idProgetti);
		String[] nameParameter = { "codUtente", "idDefinizioneProcesso", "idProgetti" };
		ValidatorInput.verifyNullValue(nameParameter, codUtente, idDefinizioneProcesso, idProgetti);

		BandoLineaVO valueObject = new BandoLineaVO();
		valueObject.setProgrBandoLineaIntervento(new BigDecimal(idDefinizioneProcesso));
		idDefinizioneProcesso = genericDAO.findSingleWhere(valueObject).getUuidProcesso();

		logger.warn("\n\n\n\nAVVIO PROGETTO \nidUtente:" + idUtente + "\tidentitaIride: " + identitaIride);
		if (idProgetti != null) {
			logger.warn("idProgetti da avviare: " + idProgetti.length);

		}

		Boolean visualizzaSoloSchedeProgetto = Boolean.FALSE;
		if (idUtente != -1)
			visualizzaSoloSchedeProgetto = abilitatoUC_TRSCSP001;
		// visualizzaSoloSchedeProgetto =
		// getSoggettoManager().hasPermessoUtenteCorrente(idUtente,identitaIride,
		// UseCaseConstants.UC_TRSCSP001);

		logger.warn("visualizzaSoloSchedeProgetto ----> " + visualizzaSoloSchedeProgetto);
		if (visualizzaSoloSchedeProgetto != null && visualizzaSoloSchedeProgetto) {
			int i = 0;
			for (Long id : idProgetti) {
				logger.warn("idProgetto da avviare:" + id);
				Long idProgetto = chiamaProceduraCaricamentoSchedaProgetto(id, result);
				logger.warn("idProgetto da avviare restituito da " + idProgetto);
				if (idProgetto != null) {

					logger.warn(
							"\n\n############################ NEOFLUX ############################## --> DON't call avvioProgetto for idProgetto "
									+ idProgetto);
					PbandiTProgettoVO progettoVO = new PbandiTProgettoVO();
					progettoVO.setIdProgetto(new BigDecimal(idProgetto));
					progettoVO = genericDAO.findSingleWhere(progettoVO);
					EsitoAvvioProcessoDTO esitoAvvioProcesso = new EsitoAvvioProcessoDTO();
					esitoAvvioProcesso.setCodiceProgetto(progettoVO.getCodiceVisualizzato());
					esitoAvvioProcesso.setCodiceMessaggio(MessaggiConstants.PROCESSO_AVVIATO);
					result.add(esitoAvvioProcesso);

				}
				i++;
			}
		}
		logger.end();
		return result.toArray(new EsitoAvvioProcessoDTO[result.size()]);
	}

	private Long chiamaProceduraCaricamentoSchedaProgetto(Long idCspProgetto, List<EsitoAvvioProcessoDTO> esitiAvvio) {
		Long idProgetto = null;
		boolean fallimento = false;
		String titoloProgetto = "id " + idCspProgetto;

		String chiaveMessaggio = PROCESSI_NON_AVVIATI;
		try {
			PbandiTCspProgettoVO cspFiltroProgettoVO = new PbandiTCspProgettoVO();
			cspFiltroProgettoVO.setIdCspProgetto(new BigDecimal(idCspProgetto));
			PbandiTCspProgettoVO cspProgettoVO = genericDAO.findSingleWhere(cspFiltroProgettoVO);

			titoloProgetto = cspProgettoVO.getTitoloProgetto();
			logger.info("chiamaProceduraCaricamentoSchedaProgetto, titoloProgetto: " + titoloProgetto
					+ " ,idCspProgetto: " + idCspProgetto);
			if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.FLAG_TRUE.equals(cspProgettoVO.getFlagAvviabile())) {

				if (genericDAO.callProcedure().caricamentoSchedaProgetto(new BigDecimal(idCspProgetto))) {
					// l'esecuzione della procedura � andata bene, leggo l'id progetto risultante
					logger.warn("La stored procedure di caricamento scheda progetto ha ritornato esito positivo");
					idProgetto = genericDAO.findSingleWhere(cspFiltroProgettoVO).getIdProgetto().longValue();
				} else {
					logger.warn(
							"La stored procedure di caricamento scheda progetto ha ritornato esito negativo, il progetto non verr� avviato.");
					fallimento = true;
				}
			} else {
				fallimento = true;
				chiaveMessaggio = MessaggiConstants.CSP_PROGETTO_INCOMPLETO;
			}
		} catch (Throwable t) {
			logger.error("Fallita la conversione da scheda progetto a progetto: " + t.getMessage(), t);
			fallimento = true;
		}

		if (fallimento) {
			// fornisco l'esito al client
			EsitoAvvioProcessoDTO esitoAvvioProcesso = new EsitoAvvioProcessoDTO();
			esitoAvvioProcesso.setCodiceMessaggio(chiaveMessaggio);
			esitoAvvioProcesso.setCodiceProgetto(titoloProgetto);
			esitiAvvio.add(esitoAvvioProcesso);
		}

		return idProgetto;
	}

	public Boolean hasProgettiSifAvviati(Long idUtente, String identitaIride, Long progrBandoLinea)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {
		ProgettoBandoLineaLightVO vo = new ProgettoBandoLineaLightVO();
		vo.setIdBandoLinea(progrBandoLinea);
		vo.setFlag_sif("S");
		int count = genericDAO.count(new FilterCondition<ProgettoBandoLineaLightVO>(vo));

		if (count > 0)
			return true;
		return false;
	}

	public EsitoSchedaProgettoDTO caricaSchedaProgetto(Long idUtente, String identitaIride,
			SchedaProgettoDTO schedaProgetto)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {
		logger.info("\n\n\ncaricaSchedaProgetto");
		EsitoSchedaProgettoDTO esitoSchedaProgettoDTO = new EsitoSchedaProgettoDTO();

		String[] nameParameter = { "schedaProgetto", "schedaProgetto.idBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, schedaProgetto,
				schedaProgetto != null ? schedaProgetto.getIdBandoLinea() : null);
		logger.info("schedaProgetto.getIdProgetto() : " + schedaProgetto.getIdProgetto()
				+ "\nschedaProgetto.idBandoLinea (progrBandoLinea): " + schedaProgetto.getIdBandoLinea());

		schedaProgetto = doCaricaSchedaProgetto(schedaProgetto);

		logger.info("==============================================================================");
		logger.info("[GestioneAvvioProgettoBusinessImpl :: caricaSchedaProgetto()] - ob ="
				+ schedaProgetto.getInformazioniBase().getIdObiettivoTematico());
		logger.info("==============================================================================");

		esitoSchedaProgettoDTO.setSchedaProgetto(schedaProgetto);

		esitoSchedaProgettoDTO.setEsito(true);

		return esitoSchedaProgettoDTO;
	}

	private SchedaProgettoDTO doCaricaSchedaProgetto(SchedaProgettoDTO schedaProgetto) throws UnrecoverableException {
		BigDecimal idBandoLinea = beanUtil.transform(schedaProgetto.getIdBandoLinea(), BigDecimal.class);
		BigDecimal idProcesso = getIdProcesso(schedaProgetto.getIdBandoLinea());

		if (schedaProgetto.getIdProgetto() == null) {
			schedaProgetto = caricaDefault(idBandoLinea, idProcesso);
		} else {
			Long idCspProgetto = schedaProgetto.getIdProgetto();
			try {
				schedaProgetto = caricaDefault(idBandoLinea, idProcesso);
				schedaProgetto.setIdProgetto(idCspProgetto);
				caricaProgetto(schedaProgetto);
				caricaSedi(schedaProgetto);
				caricaSoggetti(schedaProgetto);
			} catch (Exception e) {
				String message = "Impossibile caricare la scheda progetto con id: " + schedaProgetto.getIdProgetto()
						+ " (" + e.getMessage() + ")";
				logger.error(message, e);
				throw new UnrecoverableException(message, e);
			}
		}
		schedaProgetto.setIdBandoLinea(beanUtil.transform(idBandoLinea, String.class));
		impostaValoriCalcolabili(schedaProgetto);

		return schedaProgetto;
	}

	private BigDecimal getIdProcesso(String idBandoLinea) {
		PbandiRBandoLineaInterventVO pbandiRBandoLineaInterventVO = new PbandiRBandoLineaInterventVO();
		pbandiRBandoLineaInterventVO.setProgrBandoLineaIntervento(new BigDecimal(idBandoLinea));
		pbandiRBandoLineaInterventVO = genericDAO.findSingleWhere(pbandiRBandoLineaInterventVO);
		// modifica chiesta da PCL 23 febb 17
		BigDecimal idProcesso = pbandiRBandoLineaInterventVO.getIdProcesso();
		logger.info("idProcesso --> " + idProcesso);

		return idProcesso;
	}

	private SchedaProgettoDTO caricaDefault(BigDecimal progrBandoLineaIntervento, BigDecimal idProcesso)
			throws UnrecoverableException {
		SchedaProgettoDTO schedaProgettoDTO = beanUtil.createEmptyInstance(SchedaProgettoDTO.class);

		PbandiDLineaDiInterventoVO lineaDiInterventoNormativaVO = progettoManager
				.getLineaDiInterventoNormativa(progrBandoLineaIntervento);

		schedaProgettoDTO.setIdLineaNormativa(
				beanUtil.transform(lineaDiInterventoNormativaVO.getIdLineaDiIntervento(), String.class));

		schedaProgettoDTO.getInformazioniBase().setFlagProgettoDaInviareAlMonitoraggio(FLAG_TRUE);

		PbandiDLineaDiInterventoVO lineaDiInterventoAsseVO = progettoManager
				.getLineaDiInterventoAsse(progrBandoLineaIntervento);

		// BigDecimal idProcesso = lineaDiInterventoAsseVO.getIdProcesso();

		schedaProgettoDTO
				.setIdLineaAsse(beanUtil.transform(lineaDiInterventoAsseVO.getIdLineaDiIntervento(), String.class));

		schedaProgettoDTO.getInformazioniBase().setIdStrumentoAttuativo(
				beanUtil.transform(lineaDiInterventoNormativaVO.getIdStrumentoAttuativo(), String.class));

		PbandiTBandoVO bandoVO = new PbandiTBandoVO();
		PbandiRBandoLineaInterventVO bandoLineaInterventVO = new PbandiRBandoLineaInterventVO();
		bandoLineaInterventVO.setProgrBandoLineaIntervento(progrBandoLineaIntervento);
		bandoVO.setIdBando(genericDAO.findSingleWhere(bandoLineaInterventVO).getIdBando());
		bandoVO = genericDAO.findSingleWhere(bandoVO);

		schedaProgettoDTO.getInformazioniBase()
				.setIdSettoreCpt(beanUtil.transform(bandoVO.getIdSettoreCpt(), String.class));

		schedaProgettoDTO.getInformazioniBase()
				.setIdSottoSettoreCipe(beanUtil.transform(bandoVO.getIdSottoSettoreCipe(), String.class));

		schedaProgettoDTO.getInformazioniBase()
				.setIdNaturaCipe(beanUtil.transform(bandoVO.getIdNaturaCipe(), String.class));

		schedaProgettoDTO.getInformazioniBase().setIdTipoStrumentoProgrammazione(
				beanUtil.transform(lineaDiInterventoNormativaVO.getIdTipoStrumentoProgr(), String.class));

		schedaProgettoDTO.getInformazioniBase().setFlagCardine(FLAG_FALSE);
		schedaProgettoDTO.getInformazioniBase().setFlagGeneratoreEntrate(FLAG_FALSE);
		schedaProgettoDTO.getInformazioniBase().setFlagLeggeObiettivo(FLAG_FALSE);
		schedaProgettoDTO.getInformazioniBase().setFlagAltroFondo(FLAG_FALSE);
		schedaProgettoDTO.getInformazioniBase().setFlagStatoProgettoProgramma(FLAG_TRUE);
		schedaProgettoDTO.getInformazioniBase().setFlagRichiestaAutomaticaDelCup(FLAG_FALSE);

		schedaProgettoDTO.getInformazioniBase()
				.setIdTipoOperazione(beanUtil.transform(bandoVO.getIdTipoOperazione(), String.class));

		caricaSedeInterventoDefault(schedaProgettoDTO.getSedeInterventoDefault());

		caricaBeneficiarioDefault(schedaProgettoDTO);

		schedaProgettoDTO.setRappresentanteLegale(beanUtil.clone(schedaProgettoDTO.getBeneficiario()));

		schedaProgettoDTO.getRappresentanteLegale().setFlagPersonaFisica(FLAG_TRUE);

		schedaProgettoDTO.setFlagSalvaIntermediario(FLAG_FALSE);
		schedaProgettoDTO.setIntermediario(beanUtil.clone(schedaProgettoDTO.getBeneficiario()));

		schedaProgettoDTO.setAltroSoggettoDefault(beanUtil.clone(schedaProgettoDTO.getBeneficiario()));

		// nuova programmaz 2014_20
		if (idProcesso != null && idProcesso.longValue() == ID_PROCESSO_2014_20) {
			schedaProgettoDTO.getInformazioniBase().setIdObiettivoTematico("");
			schedaProgettoDTO.getInformazioniBase().setIdClassificazioneRA("");
			schedaProgettoDTO.getInformazioniBase().setIdGrandeProgetto("");
		}

		return schedaProgettoDTO;
	}

	private void caricaProgetto(SchedaProgettoDTO schedaProgetto) {
		PbandiTCspProgettoVO cspProgettoVO = new PbandiTCspProgettoVO();
		cspProgettoVO.setIdCspProgetto(beanUtil.transform(schedaProgetto.getIdProgetto(), BigDecimal.class));
		PbandiTCspProgettoVO progettoVO = genericDAO.findSingleWhere(cspProgettoVO);
		logger.info("progettoVO.getIdClassificazioneRa() : " + progettoVO.getIdClassificazioneRa());
		beanUtil.valueCopy(progettoVO, schedaProgetto, MAP_PROGETTO_VO_TO_DTO);
		logger.info("\n\n\n\n\n\n\n\ncarica progetto +++++++++++++++++++++ ");
		logger.shallowDump(schedaProgetto, "info");
		logger.shallowDump(schedaProgetto.getInformazioniBase(), "info");
		settaStatoProgettoProgramma(schedaProgetto, progettoVO.getStatoProgramma());
	}

	private void settaStatoProgettoProgramma(SchedaProgettoDTO schedaProgetto, String statoProgramma) {
		if (STATO_PROG_RELAZ_PROGRAMMA_ATTIVO.equals(statoProgramma)) {
			schedaProgetto.getInformazioniBase().setFlagStatoProgettoProgramma(FLAG_TRUE);
		} else {
			schedaProgetto.getInformazioniBase().setFlagStatoProgettoProgramma(FLAG_FALSE);
		}
	}

	private void caricaSedeInterventoDefault(SedeInterventoDTO sedeInterventoDefaultDTO) {
		sedeInterventoDefaultDTO.getComuneSede().setFlagNazioneItaliana(FLAG_TRUE);

		String nazione = nazioneDefault();

		sedeInterventoDefaultDTO.getComuneSede().setIdNazione(nazione);

		// Alex: aggiunto poichè sembra che sul vecchio popoli anche la regione come
		// Piemonte.
		sedeInterventoDefaultDTO.getComuneSede().setIdRegione("1");

	}

	private String nazioneDefault() {
		PbandiDNazioneVO nazioneVO = new PbandiDNazioneVO();
		nazioneVO.setDescNazione((Constants.NAZIONE_ITALIA));
		String nazione = beanUtil.transform(genericDAO.findSingleWhere(nazioneVO).getIdNazione(), String.class);
		return nazione;
	}

	private void caricaBeneficiarioDefault(SchedaProgettoDTO schedaProgettoDTO) {
		schedaProgettoDTO.getBeneficiario().setFlagPersonaFisica(FLAG_FALSE);
		caricaBeneficiarioPFDefault(schedaProgettoDTO);
		caricaBeneficiarioPGDefault(schedaProgettoDTO, schedaProgettoDTO.getSedeInterventoDefault().getComuneSede());
	}

	private void caricaBeneficiarioPFDefault(SchedaProgettoDTO schedaProgettoDTO) {
		schedaProgettoDTO.getBeneficiario().getDatiPF()
				.setComuneNas(beanUtil.clone(schedaProgettoDTO.getSedeInterventoDefault().getComuneSede()));
		schedaProgettoDTO.getBeneficiario().getDatiPF()
				.setComuneRes(beanUtil.clone(schedaProgettoDTO.getSedeInterventoDefault().getComuneSede()));
	}

	private void caricaAltroSoggettoDefault(SchedaProgettoDTO schedaProgettoDTO) {

		String nazione = nazioneDefault();

		ComuneDTO comuneDTO = new ComuneDTO();
		comuneDTO.setIdNazione(nazione);
		schedaProgettoDTO.setAltroSoggettoDefault(new SoggettoGenericoDTO());
		schedaProgettoDTO.getAltroSoggettoDefault().setDatiPF(new SoggettoPFDTO());
		schedaProgettoDTO.getAltroSoggettoDefault().setDatiPG(new SoggettoPGDTO());
		schedaProgettoDTO.getAltroSoggettoDefault().setFlagPersonaFisica(FLAG_FALSE);
		schedaProgettoDTO.getAltroSoggettoDefault().getDatiPG().setSedeLegale(beanUtil.clone(comuneDTO));
		schedaProgettoDTO.getAltroSoggettoDefault().getDatiPG()
				.setTipoDipDir(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_NA);
		schedaProgettoDTO.getAltroSoggettoDefault().getDatiPF()
				.setComuneNas(beanUtil.clone(schedaProgettoDTO.getSedeInterventoDefault().getComuneSede()));
		schedaProgettoDTO.getAltroSoggettoDefault().getDatiPF()
				.setComuneRes(beanUtil.clone(schedaProgettoDTO.getSedeInterventoDefault().getComuneSede()));
	}

	private void caricaBeneficiarioPGDefault(SchedaProgettoDTO schedaProgettoDTO, ComuneDTO comuneSede) {
		schedaProgettoDTO.getBeneficiario().getDatiPG().setSedeLegale(beanUtil.clone(comuneSede));
		schedaProgettoDTO.getBeneficiario().getDatiPG()
				.setTipoDipDir(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_NA);
	}

	private void caricaSedi(SchedaProgettoDTO schedaProgetto) {
		PbandiTCspProgSedeIntervVO cspProgSedeVO = new PbandiTCspProgSedeIntervVO();
		cspProgSedeVO.setIdCspProgetto(beanUtil.transform(schedaProgetto.getIdProgetto(), BigDecimal.class));

		schedaProgetto
				.setSediIntervento(beanUtil.transform(genericDAO.where(cspProgSedeVO).select(), SedeInterventoDTO.class,
						beanUtil.new TransformationSequence<PbandiTCspProgSedeIntervVO, SedeInterventoDTO>(
								beanUtil.new MapTransformation<PbandiTCspProgSedeIntervVO, SedeInterventoDTO>(
										MAP_SEDE_VO_TO_DTO),
								new BeanUtil.Transformation<PbandiTCspProgSedeIntervVO, SedeInterventoDTO>() {

									@SuppressWarnings("static-access")
									public void transform(PbandiTCspProgSedeIntervVO objSrc, SedeInterventoDTO objDest)
											throws Exception {
										caricaDescrizioniPerComune(objSrc, objDest.getComuneSede());

										logger.info("\n\n\n\n\n\n\n\ncarica SEDI +++++++++++++++++++++");
										if (objSrc.getIdComuneEstero() == null)
											logger.info("\n\n\nobjSrc.getIdComuneEstero() = null");
										else
											logger.info("\n\n\nobjSrc.getIdComuneEstero() = "
													+ objSrc.getIdComuneEstero().intValue());
										if (objSrc.getIdComune() == null)
											logger.info("\n\n\nobjSrc.getIdComune() = null");
										else
											logger.info(
													"\n\n\nobjSrc.getIdComune() = " + objSrc.getIdComune().intValue());

										if (objSrc.getIdComuneEstero() == null) {
											objDest.getComuneSede().setFlagNazioneItaliana(FLAG_TRUE);
											objDest.getComuneSede().setIdComune(
													beanUtil.transform(objSrc.getIdComune(), String.class));
											/*
											 * Alex: commentato poichè la select non contiene l'id e tira su tutta la
											 * tabella. beanUtil.setPropertyValueByName( objDest,
											 * "comuneSede.descComune", decodificheManager .findDescrizioneById(
											 * PbandiDComuneVO.class, objSrc.getIdComune()));
											 */
											if (objSrc.getIdComune() != null) {
												PbandiDComuneVO filtro = new PbandiDComuneVO();
												filtro.setIdComune(objSrc.getIdComune());
												filtro = genericDAO.findSingleOrNoneWhere(filtro);
												if (filtro != null)
													objDest.getComuneSede().setDescComune(filtro.getDescComune());
											}
										} else {
											objDest.getComuneSede().setFlagNazioneItaliana(FLAG_FALSE);
											objDest.getComuneSede().setIdComune(
													beanUtil.transform(objSrc.getIdComuneEstero(), String.class));

											/*
											 * Alex: commentato poichè la select non contiene l'id e tira su tutta la
											 * tabella. beanUtil.setPropertyValueByName( objDest,
											 * "comuneSede.descComune", decodificheManager .findDescrizioneById(
											 * PbandiDComuneEsteroVO.class, objSrc.getIdComuneEstero()));
											 */
											PbandiDComuneEsteroVO filtro = new PbandiDComuneEsteroVO();
											filtro.setIdComuneEstero(objSrc.getIdComuneEstero());
											filtro = genericDAO.findSingleOrNoneWhere(filtro);
											if (filtro != null)
												objDest.getComuneSede().setDescComune(filtro.getDescComuneEstero());

										}
									}
								})));
	}

	@SuppressWarnings("static-access")
	private void caricaDescrizioniPerComune(Object objSrc, ComuneDTO comuneSede) throws Exception {
		beanUtil.setPropertyValueByName(comuneSede, "descProvincia", decodificheManager.findDescrizioneById(
				PbandiDProvinciaVO.class, (BigDecimal) beanUtil.getPropertyValueByName(objSrc, "idProvincia")));
		beanUtil.setPropertyValueByName(comuneSede, "descRegione", decodificheManager.findDescrizioneById(
				PbandiDRegioneVO.class, (BigDecimal) beanUtil.getPropertyValueByName(objSrc, "idRegione")));
		beanUtil.setPropertyValueByName(comuneSede, "descNazione", decodificheManager.findDescrizioneById(
				PbandiDNazioneVO.class, (BigDecimal) beanUtil.getPropertyValueByName(objSrc, "idNazione")));
	}

	private void impostaValoriCalcolabili(SchedaProgettoDTO schedaProgetto) {
		try {
			popolaObiettivoGeneraleQsn(schedaProgetto);
			popolaObiettivoPrioritaQsn(schedaProgetto);
			popolaSettoreAttivita(schedaProgetto);
			popolaSettoreAttivitaSoggetto(schedaProgetto.getBeneficiario());
			popolaSettoreAttivitaSoggetto(schedaProgetto.getIntermediario());
			popolaSottoSettoreCipe(schedaProgetto);
			popolaSettoreCipe(schedaProgetto);
			popolaNaturaCipe(schedaProgetto);
			popolaDatiComune(schedaProgetto);
			popolaObiettivoTematico(schedaProgetto);
		} catch (Exception e) {
			logger.warn("Impossibile impostare tutti i valori calcolati per la scheda progetto: " + e.getMessage());
		}
	}

	private void popolaObiettivoGeneraleQsn(SchedaProgettoDTO schedaProgetto) throws Exception {
		if (!StringUtil.isEmpty(schedaProgetto.getInformazioniBase().getIdObiettivoSpecificoQsn())) {
			PbandiDObiettivoSpecifQsnVO cspObbSpecFiltroVO = new PbandiDObiettivoSpecifQsnVO();
			cspObbSpecFiltroVO.setIdObiettivoSpecifQsn(beanUtil
					.transform(schedaProgetto.getInformazioniBase().getIdObiettivoSpecificoQsn(), BigDecimal.class));
			ArrayList<String> obbSpec = new ArrayList<String>();
			for (PbandiDObiettivoSpecifQsnVO cspObbSpecVO : genericDAO.where(cspObbSpecFiltroVO).select()) {
				obbSpec.add(beanUtil.transform(cspObbSpecVO.getIdObiettivoGenQsn(), String.class));
			}
			if (obbSpec.size() >= 1) {
				schedaProgetto.getInformazioniBase().setIdObiettivoGeneraleQsn(obbSpec.get(0));
			}
		}
	}

	private void popolaObiettivoPrioritaQsn(SchedaProgettoDTO schedaProgetto) throws Exception {
		if (!StringUtil.isEmpty(schedaProgetto.getInformazioniBase().getIdObiettivoGeneraleQsn())) {
			PbandiDObiettivoGenQsnVO cspObbGenFiltroVO = new PbandiDObiettivoGenQsnVO();
			cspObbGenFiltroVO.setIdObiettivoGenQsn(beanUtil
					.transform(schedaProgetto.getInformazioniBase().getIdObiettivoGeneraleQsn(), BigDecimal.class));
			ArrayList<String> obbGenRes = new ArrayList<String>();
			for (PbandiDObiettivoGenQsnVO cspObbGenVO : genericDAO.where(cspObbGenFiltroVO).select()) {
				obbGenRes.add(beanUtil.transform(cspObbGenVO.getIdPrioritaQsn(), String.class));
			}
			if (obbGenRes.size() >= 1) {
				schedaProgetto.getInformazioniBase().setIdPrioritaQsn(obbGenRes.get(0));
			}
		}
	}

	private void popolaDatiComune(SchedaProgettoDTO schedaProgetto) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("idProvincia", "idProvincia");
		map.put("idRegione", "idRegione");
		map.put("idNazione", "idNazione");

		List<ComuneDTO> comuni = new ArrayList<ComuneDTO>();
		comuni.addAll(getComuni(schedaProgetto.getBeneficiario()));
		comuni.addAll(getComuni(schedaProgetto.getRappresentanteLegale()));
		comuni.addAll(getComuni(schedaProgetto.getIntermediario()));

		if (schedaProgetto.getAltriSoggetti() != null) {
			for (SoggettoGenericoDTO sogg : schedaProgetto.getAltriSoggetti()) {
				comuni.addAll(getComuni(sogg));
			}
		}

		for (ComuneDTO comuneDTO : comuni) {
			BigDecimal idComune = beanUtil.transform(comuneDTO.getIdComune(), BigDecimal.class);
			if (idComune != null) {
				ComuneVO comuneVO = new ComuneVO();
				comuneVO.setIdComune(idComune);
				comuneVO.setFlagNazioneItaliana(comuneDTO.getFlagNazioneItaliana());

				comuneVO = genericDAO.findSingleWhere(comuneVO);

				caricaDescrizioniPerComune(comuneVO, comuneDTO);

				beanUtil.valueCopy(comuneVO, comuneDTO, map);
			}
		}
	}

	private void popolaSettoreAttivita(SchedaProgettoDTO schedaProgetto) throws Exception {
		if (!StringUtil.isEmpty(schedaProgetto.getInformazioniBase().getIdAttivitaAteco())) {
			PbandiDAttivitaAtecoVO cspFiltroVO = new PbandiDAttivitaAtecoVO();
			cspFiltroVO.setIdAttivitaAteco(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getIdAttivitaAteco(), BigDecimal.class));
			ArrayList<String> attAtecoRes = new ArrayList<String>();
			for (PbandiDAttivitaAtecoVO cspSettAttVO : genericDAO.where(cspFiltroVO).select()) {
				attAtecoRes.add(beanUtil.transform(cspSettAttVO.getIdSettoreAttivita(), String.class));
			}
			if (attAtecoRes.size() >= 1) {
				schedaProgetto.getInformazioniBase().setIdSettoreAttivita(attAtecoRes.get(0));
			}
		}
	}

	private void popolaSettoreAttivitaSoggetto(SoggettoGenericoDTO soggetto) throws Exception {
		if (FLAG_FALSE.equals(soggetto.getFlagPersonaFisica())
				&& it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_NA
						.equals(soggetto.getDatiPG().getTipoDipDir())) {
			if (!StringUtil.isEmpty(soggetto.getDatiPG().getAttivitaAteco())) {
				PbandiDAttivitaAtecoVO cspFiltroVO = new PbandiDAttivitaAtecoVO();
				cspFiltroVO.setIdAttivitaAteco(
						beanUtil.transform(soggetto.getDatiPG().getAttivitaAteco(), BigDecimal.class));
				ArrayList<String> attAtecoRes = new ArrayList<String>();
				for (PbandiDAttivitaAtecoVO cspSettAttVO : genericDAO.where(cspFiltroVO).select()) {
					attAtecoRes.add(beanUtil.transform(cspSettAttVO.getIdSettoreAttivita(), String.class));
				}
				if (attAtecoRes.size() >= 1) {
					soggetto.getDatiPG().setSettoreAttivita(attAtecoRes.get(0));
				}
			}
		}
	}

	private void popolaSottoSettoreCipe(SchedaProgettoDTO schedaProgetto) throws Exception {
		if (!StringUtil.isEmpty(schedaProgetto.getInformazioniBase().getIdCategoriaCipe())) {
			PbandiDCategoriaCipeVO cspFiltroVO = new PbandiDCategoriaCipeVO();
			cspFiltroVO.setIdCategoriaCipe(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getIdCategoriaCipe(), BigDecimal.class));
			ArrayList<String> sottoSettRes = new ArrayList<String>();
			for (PbandiDCategoriaCipeVO cspCategCipeVO : genericDAO.where(cspFiltroVO).select()) {
				sottoSettRes.add(beanUtil.transform(cspCategCipeVO.getIdSottoSettoreCipe(), String.class));
			}
			if (sottoSettRes.size() >= 1) {
				schedaProgetto.getInformazioniBase().setIdSottoSettoreCipe(sottoSettRes.get(0));
			}
		}
	}

	private void popolaSettoreCipe(SchedaProgettoDTO schedaProgetto) throws Exception {
		if (!StringUtil.isEmpty(schedaProgetto.getInformazioniBase().getIdSottoSettoreCipe())) {
			PbandiDSottoSettoreCipeVO cspFiltroVO = new PbandiDSottoSettoreCipeVO();
			cspFiltroVO.setIdSottoSettoreCipe(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getIdSottoSettoreCipe(), BigDecimal.class));
			ArrayList<String> settCipeRes = new ArrayList<String>();
			for (PbandiDSottoSettoreCipeVO cspCategCipeVO : genericDAO.where(cspFiltroVO).select()) {
				settCipeRes.add(beanUtil.transform(cspCategCipeVO.getIdSettoreCipe(), String.class));
			}
			if (settCipeRes.size() >= 1) {
				schedaProgetto.getInformazioniBase().setIdSettoreCipe(settCipeRes.get(0));
			}
		}
	}

	private void popolaObiettivoTematico(SchedaProgettoDTO schedaProgetto) throws Exception {
		logger.info(
				"=================================================================================================");
		logger.info("START");
		logger.info(
				"=================================================================================================");
		logger.info("[GestioneAvvioProgettoBusinessImpl :: popolaObiettivoTematico()] - idBandoLinea"
				+ schedaProgetto.getIdBandoLinea());

		PbandiRBandoLineaInterventVO bliVO = new PbandiRBandoLineaInterventVO();
		bliVO.setProgrBandoLineaIntervento(beanUtil.transform(schedaProgetto.getIdBandoLinea(), BigDecimal.class));
		List<PbandiRBandoLineaInterventVO> bliVOs = genericDAO.where(bliVO).select();
		logger.info(
				"==================================linea intervento first====================================================");
		logger.info(" " + bliVOs.get(0));
		logger.info("size " + bliVOs.size());
		logger.info(
				"=================================================================================================");
		PbandiRBandoLineaInterventVO bliVOFiltrato = bliVOs.get(0);

		PbandiRObtemClassraVO ocVO = new PbandiRObtemClassraVO();
		ocVO.setIdClassificazioneRa(bliVOFiltrato.getIdClassificazioneRa());
		List<PbandiRObtemClassraVO> ocVOs = genericDAO.where(ocVO).select();
		logger.info("==================================oc first====================================================");
		logger.info(" " + ocVOs.get(0));
		logger.info("size " + ocVOs.size());
		logger.info(
				"=================================================================================================");
		PbandiRObtemClassraVO ocVOFiltrato = ocVOs.get(0);

		PbandiDObiettivoTemVO obVO = new PbandiDObiettivoTemVO();
		obVO.setIdObiettivoTem(ocVOFiltrato.getIdObiettivoTem());
		List<PbandiDObiettivoTemVO> obVOs = genericDAO.where(obVO).select();
		logger.info("==================================ob first====================================================");
		logger.info(" " + obVOs.get(0));
		logger.info("size " + obVOs.size());
		logger.info(
				"=================================================================================================");
		PbandiDObiettivoTemVO obVOFiltrato = obVOs.get(0);

		PbandiDClassificazioneRaVO craVO = new PbandiDClassificazioneRaVO();
		craVO.setIdClassificazioneRa(ocVOFiltrato.getIdClassificazioneRa());
		List<PbandiDClassificazioneRaVO> craVOs = genericDAO.where(craVO).select();
		logger.info("==================================cra first====================================================");
		logger.info(" " + craVOs.get(0));
		logger.info("size " + craVOs.size());
		logger.info(
				"=================================================================================================");
		PbandiDClassificazioneRaVO craVOFiltrato = craVOs.get(0);

		logger.info(
				"=====================================RESULT============================================================");
		logger.info("[GestioneAvvioProgettoBusinessImpl :: popolaObiettivoTematico()] - obiettivo = "
				+ obVOFiltrato.getIdObiettivoTem());
		logger.info("[GestioneAvvioProgettoBusinessImpl :: popolaObiettivoTematico()] - classificazione = "
				+ craVOFiltrato.getIdClassificazioneRa());
		logger.info(
				"=================================================================================================");

		schedaProgetto.getInformazioniBase().setIdObiettivoTematico(
				beanUtil.transform(genericDAO.findSingleWhere(obVOFiltrato).getIdObiettivoTem(), String.class));
		schedaProgetto.getInformazioniBase().setIdClassificazioneRA(
				beanUtil.transform(genericDAO.findSingleWhere(craVOFiltrato).getIdClassificazioneRa(), String.class));

		logger.info(
				"=================================================================================================");
		logger.info("END");
		logger.info(
				"=================================================================================================");
	}

	private void popolaNaturaCipe(SchedaProgettoDTO schedaProgetto) throws Exception {
		if (!StringUtil.isEmpty(schedaProgetto.getInformazioniBase().getIdTipologiaCipe())) {
			PbandiDTipologiaCipeVO cspFiltroVO = new PbandiDTipologiaCipeVO();
			cspFiltroVO.setIdTipologiaCipe(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getIdTipologiaCipe(), BigDecimal.class));

			schedaProgetto.getInformazioniBase().setIdNaturaCipe(
					beanUtil.transform(genericDAO.findSingleWhere(cspFiltroVO).getIdNaturaCipe(), String.class));
		}
	}

	// @return esito dell'operazione � false se coi dati di input � possibile
	// effettuare la ricerca ma l'esito non � univoco
	private boolean popolaNaturaCipeDaDettaglioCup(DettaglioCupVO dettaglioCupVO, SchedaProgettoDTO schedaProgetto)
			throws Exception {
		boolean esito = false;
		if (!StringUtil.isEmpty(dettaglioCupVO.getDatiGeneraliProgetto().getNatura())) {
			schedaProgetto.getInformazioniBase().setIdNaturaCipe(null);
			schedaProgetto.getInformazioniBase().setIdTipologiaCipe(null);
			PbandiDNaturaCipeVO cspFiltroVO = new PbandiDNaturaCipeVO();
			cspFiltroVO.setDescNaturaCipe(dettaglioCupVO.getDatiGeneraliProgetto().getNatura());
			List<PbandiDNaturaCipeVO> listNaturaCipe = genericDAO.findListWhere(cspFiltroVO);
			if (listNaturaCipe.size() == 1) {
				schedaProgetto.getInformazioniBase().setIdNaturaCipe(beanUtil
						.transform(((PbandiDNaturaCipeVO) (listNaturaCipe.get(0))).getIdNaturaCipe(), String.class));
				esito = true;
			}
		}
		return esito;
	}

	private void popolaTipologiaCipeDaDettaglioCup(DettaglioCupVO dettaglioCupVO, SchedaProgettoDTO schedaProgetto)
			throws Exception {
		if (!StringUtil.isEmpty(dettaglioCupVO.getDatiGeneraliProgetto().getTipologia())) {
			PbandiDTipologiaCipeVO cspFiltroVO = new PbandiDTipologiaCipeVO();
			cspFiltroVO.setDescTipologiaCipe(dettaglioCupVO.getDatiGeneraliProgetto().getTipologia());
			cspFiltroVO.setIdNaturaCipe(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getIdNaturaCipe(), BigDecimal.class));
			List<PbandiDTipologiaCipeVO> listTipologiaCipe = genericDAO.findListWhere(cspFiltroVO);
			if (listTipologiaCipe.size() == 1) {
				schedaProgetto.getInformazioniBase().setIdTipologiaCipe(beanUtil.transform(
						((PbandiDTipologiaCipeVO) (listTipologiaCipe.get(0))).getIdTipologiaCipe(), String.class));
			}
		}
	}

	// @return esito dell'operazione � false se coi dati di input � possibile
	// effettuare la ricerca ma l'esito non � univoco
	private boolean popolaSettoreCipeDaDettaglioCup(DettaglioCupVO dettaglioCupVO, SchedaProgettoDTO schedaProgetto)
			throws Exception {
		boolean esito = false;
		if (!StringUtil.isEmpty(dettaglioCupVO.getDatiGeneraliProgetto().getSettore())) {
			schedaProgetto.getInformazioniBase().setIdSettoreCipe(null);
			schedaProgetto.getInformazioniBase().setIdSottoSettoreCipe(null);
			schedaProgetto.getInformazioniBase().setIdCategoriaCipe(null);
			PbandiDSettoreCipeVO cspFiltroVO = new PbandiDSettoreCipeVO();
			cspFiltroVO.setDescSettoreCipe(dettaglioCupVO.getDatiGeneraliProgetto().getSettore());
			List<PbandiDSettoreCipeVO> listSettoreCipe = genericDAO.findListWhere(cspFiltroVO);
			if (listSettoreCipe.size() == 1) {
				schedaProgetto.getInformazioniBase().setIdSettoreCipe(beanUtil
						.transform(((PbandiDSettoreCipeVO) (listSettoreCipe.get(0))).getIdSettoreCipe(), String.class));
				esito = true;
			}
		}
		return esito;
	}

	// @return esito dell'operazione � false se coi dati di input � possibile
	// effettuare la ricerca ma l'esito non � univoco
	private boolean popolaSottoSettoreCipeDaDettaglioCup(DettaglioCupVO dettaglioCupVO,
			SchedaProgettoDTO schedaProgetto) throws Exception {
		boolean esito = false;
		if (!StringUtil.isEmpty(dettaglioCupVO.getDatiGeneraliProgetto().getSottosettore())) {
			PbandiDSottoSettoreCipeVO cspFiltroVO = new PbandiDSottoSettoreCipeVO();
			cspFiltroVO.setDescSottoSettoreCipe(dettaglioCupVO.getDatiGeneraliProgetto().getSottosettore());
			cspFiltroVO.setIdSettoreCipe(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getIdSettoreCipe(), BigDecimal.class));
			List<PbandiDSottoSettoreCipeVO> listSottoSettoreCipe = genericDAO.findListWhere(cspFiltroVO);
			if (listSottoSettoreCipe.size() == 1) {
				schedaProgetto.getInformazioniBase()
						.setIdSottoSettoreCipe(beanUtil.transform(
								((PbandiDSottoSettoreCipeVO) (listSottoSettoreCipe.get(0))).getIdSottoSettoreCipe(),
								String.class));
				esito = true;
			}
		}
		return esito;
	}

	private void popolaCategoriaCipeDaDettaglioCup(DettaglioCupVO dettaglioCupVO, SchedaProgettoDTO schedaProgetto)
			throws Exception {
		if (!StringUtil.isEmpty(dettaglioCupVO.getDatiGeneraliProgetto().getCategoria())) {
			PbandiDCategoriaCipeVO cspFiltroVO = new PbandiDCategoriaCipeVO();
			cspFiltroVO.setDescCategoriaCipe(dettaglioCupVO.getDatiGeneraliProgetto().getCategoria());
			cspFiltroVO.setIdSottoSettoreCipe(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getIdSottoSettoreCipe(), BigDecimal.class));
			List<PbandiDCategoriaCipeVO> listCategoriaCipe = genericDAO.findListWhere(cspFiltroVO);
			if (listCategoriaCipe.size() == 1) {
				schedaProgetto.getInformazioniBase().setIdCategoriaCipe(beanUtil.transform(
						((PbandiDCategoriaCipeVO) (listCategoriaCipe.get(0))).getIdCategoriaCipe(), String.class));
			}
		}
	}

	private void caricaSoggetti(SchedaProgettoDTO schedaProgetto) throws Exception {
		logger.info("\n\n\n\n\n\n\n\ncarica SOGGETTI +++++++++++++++++++++");
		PbandiTCspSoggettoVO beneficiarioFiltroVO = creaFiltroSoggetto(schedaProgetto,
				Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BENEFICIARIO, null);
		schedaProgetto.setBeneficiario(
				mappaSoggetto(genericDAO.findListWhere(beneficiarioFiltroVO), schedaProgetto.getBeneficiario()));
		PbandiTCspSoggettoVO rappresentanteFiltroVO = creaFiltroSoggetto(schedaProgetto,
				Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA,
				Constants.DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE);
		schedaProgetto.setRappresentanteLegale(mappaSoggetto(genericDAO.findListWhere(rappresentanteFiltroVO),
				schedaProgetto.getRappresentanteLegale()));
		PbandiTCspSoggettoVO intermediarioFiltroVO = creaFiltroSoggetto(schedaProgetto, null,
				Constants.DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_INTERMEDIARIO);

		List<PbandiTCspSoggettoVO> listIntermediario = genericDAO.findListWhere(intermediarioFiltroVO);
		if (listIntermediario.size() == 1) {
			schedaProgetto.setFlagSalvaIntermediario(FLAG_TRUE);
			schedaProgetto.setIntermediario(mappaSoggetto(listIntermediario, schedaProgetto.getIntermediario()));
		}

		ArrayList<SoggettoGenericoDTO> altriSoggetti = new ArrayList<SoggettoGenericoDTO>();
		for (PbandiTCspSoggettoVO soggettoVO : genericDAO
				.where(filterBy(creaFiltroSoggetto(schedaProgetto, null, null)).and(not(filterBy(beneficiarioFiltroVO)))
						.and(not(filterBy(intermediarioFiltroVO))).and(not(filterBy(rappresentanteFiltroVO))))
				.select()) {
			List<PbandiTCspSoggettoVO> list = new ArrayList<PbandiTCspSoggettoVO>();
			list.add(soggettoVO);
			altriSoggetti.add(mappaSoggetto(list, beanUtil.clone(schedaProgetto.getAltroSoggettoDefault())));
		}
		schedaProgetto.setAltriSoggetti(altriSoggetti.toArray(new SoggettoGenericoDTO[altriSoggetti.size()]));
		logger.info("\n\nfine carica SOGGETTI +++++++++++++++++++++");
	}

	private PbandiTCspSoggettoVO creaFiltroSoggetto(SchedaProgettoDTO schedaProgetto, String descBreveTipoAnagrafica,
			String descBreveTipoSoggettoCorrelato) {
		PbandiTCspSoggettoVO cspSoggettoFiltroVO = new PbandiTCspSoggettoVO();
		cspSoggettoFiltroVO.setIdCspProgetto(beanUtil.transform(schedaProgetto.getIdProgetto(), BigDecimal.class));
		cspSoggettoFiltroVO.setIdTipoAnagrafica(decodeDescBreveTipoAnagrafica(descBreveTipoAnagrafica));
		cspSoggettoFiltroVO
				.setIdTipoSoggettoCorrelato(decodeDescBreveTipoSoggettoCorrelato(descBreveTipoSoggettoCorrelato));
		return cspSoggettoFiltroVO;
	}

	private SoggettoGenericoDTO mappaSoggetto(List<PbandiTCspSoggettoVO> list, SoggettoGenericoDTO soggetto)
			throws Exception {
		// mappo il soggetto sul dto
		if (list != null && list.size() == 1) {
			PbandiTCspSoggettoVO sogg = list.get(0);

			boolean personaFisica = decodeDescBreveTipoSoggetto(DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_FISICA)
					.equals(sogg.getIdTipoSoggetto());

			beanUtil.valueCopy(sogg, soggetto, MAP_SOGG_VO_TO_DTO);

			soggetto.setFlagPersonaFisica(personaFisica ? it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE
					: it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);

			mappaDatiPF(soggetto, sogg);
			mappaDatiPG(soggetto, sogg);
		}

		return soggetto;
	}

	private Collection<? extends ComuneDTO> getComuni(SoggettoGenericoDTO s) {
		List<ComuneDTO> comuni = new ArrayList<ComuneDTO>();

		ComuneDTO c = s.getDatiPF().getComuneNas();
		if (c != null) {
			comuni.add(c);
		}
		c = s.getDatiPF().getComuneRes();
		if (c != null) {
			comuni.add(c);
		}
		c = s.getDatiPG().getSedeLegale();
		if (c != null) {
			comuni.add(c);
		}

		return comuni;
	}

	// FIXME usare le decodifiche?
	private BigDecimal decodeDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		BigDecimal idTipoAnagrafica = null;
		if (descBreveTipoAnagrafica != null) {
			PbandiDTipoAnagraficaVO tipoAnagrafica = new PbandiDTipoAnagraficaVO();
			tipoAnagrafica.setDescBreveTipoAnagrafica(descBreveTipoAnagrafica);
			idTipoAnagrafica = genericDAO.findSingleWhere(tipoAnagrafica).getIdTipoAnagrafica();
		}

		return idTipoAnagrafica;
	}

	// FIXME usare le decodifiche?
	private BigDecimal decodeDescBreveTipoSoggettoCorrelato(String descBreveTipoSoggettoCorrelato) {
		BigDecimal idTipoSoggettoCorrelato = null;
		if (descBreveTipoSoggettoCorrelato != null) {
			PbandiDTipoSoggCorrelatoVO tipoSoggettoCorrelato = new PbandiDTipoSoggCorrelatoVO();
			tipoSoggettoCorrelato.setDescBreveTipoSoggCorrelato(descBreveTipoSoggettoCorrelato);
			idTipoSoggettoCorrelato = genericDAO.findSingleWhere(tipoSoggettoCorrelato).getIdTipoSoggettoCorrelato();
		}
		return idTipoSoggettoCorrelato;
	}

	private BigDecimal decodeDescBreveTipoSoggetto(String descBreveTipoSoggetto) {
		PbandiDTipoSoggettoVO tipoSoggetto = new PbandiDTipoSoggettoVO();
		tipoSoggetto.setDescBreveTipoSoggetto(descBreveTipoSoggetto);
		BigDecimal idTipoSoggetto = genericDAO.findSingleWhere(tipoSoggetto).getIdTipoSoggetto();
		return idTipoSoggetto;
	}

	private void mappaDatiPF(SoggettoGenericoDTO soggetto, PbandiTCspSoggettoVO sogg)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException, Exception {
		Object dati;
		Map<String, String> map = new HashMap<String, String>();

		dati = soggetto.getDatiPF();

		map.putAll(MAP_PF_VO_TO_DTO);
		preparaMappaturaComune(sogg, dati, map, "Nascita", "comuneNas");
		preparaMappaturaComune(sogg, dati, map, "Residenza", "comuneRes");
		soggetto.setDatiPF((SoggettoPFDTO) dati);
		mappaDati(sogg, dati, map);
	}

	private void mappaDatiPG(SoggettoGenericoDTO soggetto, PbandiTCspSoggettoVO sogg)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException, Exception {
		Object dati;
		Map<String, String> map = new HashMap<String, String>();

		SoggettoPGDTO soggettoPGDTO = soggetto.getDatiPG();
		dati = soggettoPGDTO;

		map.putAll(MAP_PG_VO_TO_DTO);
		preparaMappaturaComune(sogg, dati, map, "SedeLegale", "sedeLegale");

		if (sogg.getIdAteneo() != null) {
			map.put("idDipartimento", "denominazioneEnteDipUni");
			map.put("idAteneo", "ateneo");
			soggettoPGDTO.setTipoDipDir(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DU);
		} else if (sogg.getIdEnteCompetenza() != null) {
			// IdEnteCompetenza c'� sia per TIPO_DIP_DIR_DR sia per TIPO_DIP_DIR_PA.
			// Cerco l'ente per distinguere i 2 casi.
			String tipo = getDescBreveTipoEnteCompetenza(sogg.getIdEnteCompetenza());
			if (DESC_BREVE_TIPO_ENTE_COMPETENZA_PUBBLICA_AMMINISTRAZIONE.equalsIgnoreCase(tipo)) {
				map.put("idEnteCompetenza", "denominazioneEntePA");
				soggettoPGDTO.setTipoDipDir(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_PA);
			} else {
				map.put("idEnteCompetenza", "denominazioneEnteDirReg");
				soggettoPGDTO.setTipoDipDir(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DR);
			}
		} else {
			soggettoPGDTO.setTipoDipDir(it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_NA);
		}

		soggetto.setDatiPG(soggettoPGDTO);
		mappaDati(sogg, dati, map);

		try {
			if (sogg.getIdSettoreEnte() != null) {
				soggetto.getDatiPG().setIdSettoreEnte("" + sogg.getIdSettoreEnte().longValue());
			}
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("static-access")
	private void preparaMappaturaComune(PbandiTCspSoggettoVO sogg, Object dati, Map<String, String> map,
			String tipoCampo, String tipoComune)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException, Exception {
		logger.info("\n\n\n\n\n\n\n\npreparaMappaturaComune +++++++++++++++++++++");

		String flagValue = FLAG_TRUE;
		String propNameVO = "idComuneEstero" + tipoCampo;

		String propNameComuneDTO = tipoComune + ".idComune";
		Class<? extends GenericVO> comuneType;

		if (beanUtil.getPropertyValueByName(sogg, propNameVO) != null) {
			flagValue = FLAG_FALSE;
			comuneType = PbandiDComuneEsteroVO.class;
		} else {
			propNameVO = "idComuneItaliano" + tipoCampo;
			comuneType = PbandiDComuneVO.class;
		}
		map.put(propNameVO, propNameComuneDTO);

		ComuneDTO comuneDTO = (ComuneDTO) beanUtil.getPropertyValueByName(dati, tipoComune);

		logger.info(
				"\n\nfindDescrizioneById() con comuneType = " + comuneType.getName() + "; propNameVO = " + propNameVO);

		BigDecimal id = (BigDecimal) beanUtil.getPropertyValueByName(sogg, propNameVO);
		if (id != null) {
			logger.info("\n\ngetPropertyValueByName = " + id.intValue());

			// Alex: aggiunto if su PbandiDComuneVO poichè findDescrizioneById() non prende
			// l'id
			// e quindi tira su tutta la tabella D_COMUNI.
			if ("it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneVO"
					.equalsIgnoreCase(comuneType.getName())) {
				PbandiDComuneVO filtro = new PbandiDComuneVO();
				filtro.setIdComune(id);
				filtro = genericDAO.findSingleOrNoneWhere(filtro);
				if (filtro != null)
					comuneDTO.setDescComune(filtro.getDescComune());
			} else if ("it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneEsteroVO"
					.equalsIgnoreCase(comuneType.getName())) {
				PbandiDComuneEsteroVO filtro = new PbandiDComuneEsteroVO();
				filtro.setIdComuneEstero(id);
				filtro = genericDAO.findSingleOrNoneWhere(filtro);
				if (filtro != null)
					comuneDTO.setDescComune(filtro.getDescComuneEstero());
			} else {
				beanUtil.setPropertyValueByName(comuneDTO, "descComune", decodificheManager.findDescrizioneById(
						comuneType, (BigDecimal) beanUtil.getPropertyValueByName(sogg, propNameVO)));
			}

		} else {
			logger.info("\n\ngetPropertyValueByName = NULL -> evito decodificheManager.findDescrizioneById()");
		}

		beanUtil.setPropertyValueByName(dati, tipoComune + ".flagNazioneItaliana", flagValue);
	}

	private String getDescBreveTipoEnteCompetenza(BigDecimal idEnteCompetenza) {
		try {
			PbandiTEnteCompetenzaVO ente = new PbandiTEnteCompetenzaVO();
			ente.setIdEnteCompetenza(idEnteCompetenza);
			ente = genericDAO.findSingleWhere(ente);
			PbandiDTipoEnteCompetenzaVO tipo = new PbandiDTipoEnteCompetenzaVO();
			tipo.setIdTipoEnteCompetenza(ente.getIdTipoEnteCompetenza());
			tipo = genericDAO.findSingleWhere(tipo);
			return tipo.getDescBreveTipoEnteCompetenz();
		} catch (Exception e) {
			logger.info("Errore in getDescBreveTipoEnteCompetenza(): " + e);
			return DESC_BREVE_TIPO_ENTE_COMPETENZA_DIREZIONE_REGIONALE;
		}
	}

	@SuppressWarnings("static-access")
	private void mappaDati(PbandiTCspSoggettoVO sogg, Object dati, Map<String, String> map) throws Exception {
		beanUtil.valueCopy(sogg, dati, map);
		beanUtil.setPropertyValueByName(dati, "descRelazioneColBeneficiario", decodificheManager
				.findDescrizioneById(PbandiDTipoSoggCorrelatoVO.class, sogg.getIdTipoSoggettoCorrelato()));

		PbandiTCspSoggRuoloEnteVO cspSoggRuoloEnteFiltroVO = new PbandiTCspSoggRuoloEnteVO();
		cspSoggRuoloEnteFiltroVO.setIdCspSoggetto(sogg.getIdCspSoggetto());

		ArrayList<String> ruoli = new ArrayList<String>();
		for (PbandiTCspSoggRuoloEnteVO cspSoggRuoloEnteVO : genericDAO.where(cspSoggRuoloEnteFiltroVO).select()) {
			ruoli.add(beanUtil.transform(cspSoggRuoloEnteVO.getIdRuoloEnteCompetenza(), String.class));
		}
		beanUtil.setPropertyValueByName(dati, "ruolo", ruoli.toArray(new String[ruoli.size()]));
	}

	// Dato un codice fiscale, recupara i beneficiari che verranno visualizzati
	// nella popup di selezione
	// in Scheda Progeto - Beneficiario - Persona Giuridica.
	public BeneficiarioCspDTO[] ricercaBeneficiarioCsp(Long idUtente, String identitaIride, String codiceFiscale)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {

		String[] nameParameter = { "idUtente", "identitaIride", "codiceFiscale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, codiceFiscale);

		BeneficiarioCspDTO[] result = new BeneficiarioCspDTO[0];
		try {

			// Legge da db i beneficiari.
			BeneficiarioCspConCivicoVO vo = new BeneficiarioCspConCivicoVO();
			vo.setCodiceFiscaleSoggetto(codiceFiscale);
			List<BeneficiarioCspConCivicoVO> listaVO = genericDAO.findListWhere(vo);
			if (listaVO == null)
				throw new Exception("ricercaBeneficiarioCsp(): lista beneficiari nulla.");
			logger.info("ricercaBeneficiarioCsp(): num record letti da db: " + listaVO.size());

			if (listaVO.size() == 0)
				return new BeneficiarioCspDTO[0];

			// Completa eventuali dati mancanti della sede legale.
			List<BeneficiarioCspConCivicoVO> listaVOcompletata = new ArrayList<BeneficiarioCspConCivicoVO>();
			for (BeneficiarioCspConCivicoVO b : listaVO) {
				BeneficiarioCspConCivicoVO b1 = this.completaIndirizzo(b);
				listaVOcompletata.add(b1);
				logger.info("ricercaBeneficiarioCsp(): BeneficiarioCspVO = " + b1.toString());
			}

			// Compatta i record (1 beneficiario possono essere associato pi� sedi legali),
			// passando da BeneficiarioCspVO a BeneficiarioCspDTO.
			logger.info("inizio compattamento");
			List<BeneficiarioCspDTO> listaDTO = new ArrayList<BeneficiarioCspDTO>();
			BeneficiarioCspConCivicoVO voCorrente = new BeneficiarioCspConCivicoVO();
			BeneficiarioCspDTO dtoCorrente = new BeneficiarioCspDTO();
			List<SedeLegaleBeneficiarioCspDTO> listaSediCorrente = new ArrayList<SedeLegaleBeneficiarioCspDTO>();
			for (BeneficiarioCspConCivicoVO b : listaVOcompletata) {

				logger.info("Elaboro record " + b.getDenominazioneEnteGiuridico());
				// Gestisce il passaggio da un beneficiario all'altro.
				if (!uguali(b, dtoCorrente)) {
					logger.info("Diverso");
					// Salva in listaDTO l'oggetto DTO (1 beneficiario con pi� sedi legali) popolato
					// finora.
					if (!StringUtil.isEmpty(dtoCorrente.getDenominazioneEnteGiuridico())) {
						logger.info("Aggancio: num sedi = " + listaSediCorrente.size());
						SedeLegaleBeneficiarioCspDTO[] a = new SedeLegaleBeneficiarioCspDTO[1];
						a = listaSediCorrente.toArray(a);
						dtoCorrente.setSediLegali(a);
						listaDTO.add(dtoCorrente);
						listaSediCorrente = new ArrayList<SedeLegaleBeneficiarioCspDTO>();
						logger.info("dim1 = " + listaSediCorrente.size() + "; dim2 = " + a.length);
					}
					// Creo un nuovo oggetto DTO.
					logger.info("Creo un nuovo oggetto DTO");
					dtoCorrente = new BeneficiarioCspDTO();
					dtoCorrente.setCodiceFiscaleSoggetto(b.getCodiceFiscaleSoggetto());
					dtoCorrente.setDenominazioneEnteGiuridico(b.getDenominazioneEnteGiuridico());
					dtoCorrente.setIban(b.getIban());
					dtoCorrente.setDtCostituzione(b.getDtCostituzione());
					if (b.getDtCostituzione() != null)
						dtoCorrente.setDtCostituzioneStringa(DateUtil.getDate(b.getDtCostituzione()));
					dtoCorrente.setIdFormaGiuridica(b.getIdFormaGiuridica());
					dtoCorrente.setDescFormaGiuridica(b.getDescFormaGiuridica());
					dtoCorrente.setIdSettoreAttivita(b.getIdSettoreAttivita());
					dtoCorrente.setDescSettore(b.getDescSettore());
					dtoCorrente.setIdAttivitaAteco(b.getIdAttivitaAteco());
					dtoCorrente.setDescAteco(b.getDescAteco());
				}

				// Aggiunge la sede legale all'oggetto DTO corrente.
				logger.info("Aggiunge la sede legale " + b.getPartitaIva());
				SedeLegaleBeneficiarioCspDTO sede = new SedeLegaleBeneficiarioCspDTO();
				sede.setPartitaIva(b.getPartitaIva());
				sede.setDescIndirizzo(b.getDescIndirizzo());
				sede.setCivico(b.getCivico());
				sede.setCap(b.getCap());
				sede.setIdNazione(b.getIdNazione());
				sede.setDescNazione(b.getDescNazione());
				sede.setIdRegione(b.getIdRegione());
				sede.setDescRegione(b.getDescRegione());
				sede.setIdProvincia(b.getIdProvincia());
				sede.setDescProvincia(b.getDescProvincia());
				sede.setIdComune(b.getIdComune());
				sede.setDescComune(b.getDescComune());
				sede.setIdComuneEstero(b.getIdComuneEstero());
				sede.setDescComuneEstero(b.getDescComuneEstero());
				sede.setEmail(b.getEmail());
				sede.setTelefono(b.getTelefono());
				sede.setFax(b.getFax());
				listaSediCorrente.add(sede);
			}
			logger.info("Fine ciclo");
			if (!StringUtil.isEmpty(dtoCorrente.getDenominazioneEnteGiuridico())) {
				logger.info("Aggancio finale: num sedi = " + listaSediCorrente.size());
				SedeLegaleBeneficiarioCspDTO[] a = new SedeLegaleBeneficiarioCspDTO[1];
				a = listaSediCorrente.toArray(a);
				dtoCorrente.setSediLegali(a);
				listaDTO.add(dtoCorrente);
			}

			// Trasforma l'elenco finale da ArrayList a [].
			result = listaDTO.toArray(result);
			logger.info("ricercaBeneficiarioCsp(): num beneficiari trasformati: " + result.length);
			for (BeneficiarioCspDTO b : result) {
				logger.info("ricercaBeneficiarioCsp(): BeneficiarioCspDTO = " + b.toString());
			}

		} catch (Exception e) {
			String message = "Impossibile caricare la lista dei Beneficiari: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}

		return result;
	}

	private BeneficiarioCspConCivicoVO completaIndirizzo(BeneficiarioCspConCivicoVO b) {
		// Completo eventuali dati mancanti dell'indirizzo in input.
		if (b.getIdComune() != null && b.getIdProvincia() == null) {
			PbandiDComuneVO comune = new PbandiDComuneVO();
			comune.setIdComune(new BigDecimal(b.getIdComune()));
			comune = genericDAO.findSingleWhere(comune);
			if (comune.getIdProvincia() != null) {
				PbandiDProvinciaVO prov = new PbandiDProvinciaVO();
				prov.setIdProvincia(comune.getIdProvincia());
				prov = genericDAO.findSingleWhere(prov);
				b.setIdProvincia(prov.getIdProvincia().longValue());
				b.setDescProvincia(prov.getDescProvincia());
				// logger.info("Aggiungo provincia "+b.getIdProvincia()+"
				// "+b.getDescProvincia());
			}
		}
		if (b.getIdProvincia() != null && b.getIdRegione() == null) {
			PbandiDProvinciaVO prov = new PbandiDProvinciaVO();
			prov.setIdProvincia(new BigDecimal(b.getIdProvincia()));
			prov = genericDAO.findSingleWhere(prov);
			if (prov.getIdRegione() != null) {
				PbandiDRegioneVO reg = new PbandiDRegioneVO();
				reg.setIdRegione(prov.getIdRegione());
				reg = genericDAO.findSingleWhere(reg);
				b.setIdRegione(reg.getIdRegione().longValue());
				b.setDescRegione(reg.getDescRegione());
				// logger.info("Aggiungo regione "+b.getIdRegione()+" "+b.getDescRegione());
			}
		}
		if (b.getIdRegione() != null && b.getIdNazione() == null) {
			b.setIdNazione(new Long(118));
			b.setDescNazione("ITALIA");
			// logger.info("Aggiungo nazione "+b.getIdNazione()+" "+b.getDescNazione());
		}
		if (b.getIdComuneEstero() != null && b.getIdNazione() == null) {
			PbandiDComuneEsteroVO comEstero = new PbandiDComuneEsteroVO();
			comEstero.setIdComuneEstero(new BigDecimal(b.getIdComuneEstero()));
			comEstero = genericDAO.findSingleWhere(comEstero);
			if (comEstero.getIdNazione() != null) {
				PbandiDNazioneVO naz = new PbandiDNazioneVO();
				naz.setIdNazione(comEstero.getIdNazione());
				naz = genericDAO.findSingleWhere(naz);
				b.setIdNazione(naz.getIdNazione().longValue());
				b.setDescNazione(naz.getDescNazione());
				// logger.info("Aggiungo nazione estera "+b.getIdNazione()+"
				// "+b.getDescNazione());
			}
		}

		return b;
	}

	private boolean uguali(BeneficiarioCspConCivicoVO b1, BeneficiarioCspDTO b2) {
		if (!uguali(b1.getDenominazioneEnteGiuridico(), b2.getDenominazioneEnteGiuridico()))
			return false;
		if (!uguali(b1.getIban(), b2.getIban()))
			return false;
		java.sql.Date sqlDate = null;
		if (b2.getDtCostituzione() != null)
			sqlDate = new java.sql.Date(b2.getDtCostituzione().getTime());
		if (!uguali(b1.getDtCostituzione(), sqlDate))
			return false;
		if (!uguali(b1.getIdFormaGiuridica(), b2.getIdFormaGiuridica()))
			return false;
		if (!uguali(b1.getIdSettoreAttivita(), b2.getIdSettoreAttivita()))
			return false;
		if (!uguali(b1.getIdAttivitaAteco(), b2.getIdAttivitaAteco()))
			return false;
		return true;
	}

	private boolean uguali(RapprLegaleCspConCivicoVO r1, RapprLegaleCspDTO r2) {
		if (!uguali(r1.getCognome(), r2.getCognome()))
			return false;
		if (!uguali(r1.getNome(), r2.getNome()))
			return false;
		if (!uguali(r1.getSesso(), r2.getSesso()))
			return false;
		java.sql.Date sqlDate = null;
		if (r2.getDtNascita() != null)
			sqlDate = new java.sql.Date(r2.getDtNascita().getTime());
		if (!uguali(r1.getDtNascita(), sqlDate))
			return false;
		return true;
	}

	private boolean uguali(IndirizziRapprLegaleCspDTO i1, IndirizziRapprLegaleCspDTO i2) {
		if (!uguali(i1.getDescIndirizzo(), i2.getDescIndirizzo()))
			return false;
		if (!uguali(i1.getCap(), i2.getCap()))
			return false;
		if (!uguali(i1.getEmail(), i2.getEmail()))
			return false;
		if (!uguali(i1.getFax(), i2.getFax()))
			return false;
		if (!uguali(i1.getTelefono(), i2.getTelefono()))
			return false;

		if (!uguali(i1.getIdNazioneRes(), i2.getIdNazioneRes()))
			return false;
		if (!uguali(i1.getIdRegioneRes(), i2.getIdRegioneRes()))
			return false;
		if (!uguali(i1.getIdProvinciaRes(), i2.getIdProvinciaRes()))
			return false;
		if (!uguali(i1.getIdComuneRes(), i2.getIdComuneRes()))
			return false;
		if (!uguali(i1.getIdComuneEsteroRes(), i2.getIdComuneEsteroRes()))
			return false;

		if (!uguali(i1.getIdNazioneNascita(), i2.getIdNazioneNascita()))
			return false;
		if (!uguali(i1.getIdRegioneNascita(), i2.getIdRegioneNascita()))
			return false;
		if (!uguali(i1.getIdProvinciaNascita(), i2.getIdProvinciaNascita()))
			return false;
		if (!uguali(i1.getIdComuneNascita(), i2.getIdComuneNascita()))
			return false;
		if (!uguali(i1.getIdComuneEsteroNascita(), i2.getIdComuneEsteroNascita()))
			return false;
		return true;
	}

	private boolean uguali(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null && s2 != null)
			return false;
		if (s1 != null && s2 == null)
			return false;
		return s1.equals(s2);
	}

	private boolean uguali(Long s1, Long s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null && s2 != null)
			return false;
		if (s1 != null && s2 == null)
			return false;
		return s1.equals(s2);
	}

	private boolean uguali(Date s1, Date s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null && s2 != null)
			return false;
		if (s1 != null && s2 == null)
			return false;
		return s1.equals(s2);
	}

	public EsitoSchedaProgettoDTO salvaSchedaProgetto(Long idUtente, String identitaIride,
			SchedaProgettoDTO schedaProgetto, String flagCambiatoCup, boolean datiCompletiPerAvvio)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {

		String[] nameParameter = { "schedaProgetto", "schedaProgetto.informazioniBase", "schedaProgetto.idBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, schedaProgetto, schedaProgetto.getInformazioniBase(),
				schedaProgetto.getIdBandoLinea());

		logger.info("\n\n\n\n\n\n\nsalvaSchedaProgetto#######################");
		String idBandoLinea = schedaProgetto.getIdBandoLinea();
		BigDecimal idProcesso = getIdProcesso(idBandoLinea);

		Long idCspProgetto = doSalvaSchedaProgetto(idUtente, schedaProgetto, datiCompletiPerAvvio);

		EsitoSchedaProgettoDTO result = new EsitoSchedaProgettoDTO();
		result.setEsito(true);
		result.setSchedaProgetto(new it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SchedaProgettoDTO());
		result.getSchedaProgetto().setIdProgetto(idCspProgetto);

		return result;

	}

	private Long doSalvaSchedaProgetto(Long idUtente, SchedaProgettoDTO schedaProgetto, boolean datiCompletiPerAvvio)
			throws UnrecoverableException {
		Date sysdate = DateUtil.getSysdateWithoutSeconds();

		try {
			PbandiTCspProgettoVO cspProgettoVO = persistiProgetto(idUtente, schedaProgetto, sysdate,
					datiCompletiPerAvvio);
			schedaProgetto.setIdProgetto(beanUtil.transform(cspProgettoVO.getIdCspProgetto(), Long.class));
			persistiSedi(idUtente, schedaProgetto, sysdate, cspProgettoVO);

			List<PbandiTCspSoggettoVO> cspSoggettiVO = new ArrayList<PbandiTCspSoggettoVO>();

			List<PbandiTCspSoggRuoloEnteVO> cspRuoliVO = new ArrayList<PbandiTCspSoggRuoloEnteVO>();
			cspSoggettiVO.add(persistiSoggettoGenerico(schedaProgetto.getBeneficiario(), sysdate, cspProgettoVO,
					idUtente, cspRuoliVO, Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BENEFICIARIO, null));
			cspSoggettiVO.add(persistiSoggettoGenerico(schedaProgetto.getRappresentanteLegale(), sysdate, cspProgettoVO,
					idUtente, cspRuoliVO, Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA,
					Constants.DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE));

			/*
			 * Alex: intermediario non più gestito if (hasIntermediario(schedaProgetto)) {
			 * cspSoggettiVO .add(persistiSoggettoGenerico(
			 * schedaProgetto.getIntermediario(), sysdate, cspProgettoVO, idUtente,
			 * cspRuoliVO, null,
			 * Constants.DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_INTERMEDIARIO)); }
			 */

			/*
			 * Alex: Altri Soggetti non più gestito for (SoggettoGenericoDTO soggetto :
			 * schedaProgetto .getAltriSoggetti()) {
			 * cspSoggettiVO.add(persistiSoggettoGenerico(soggetto, sysdate, cspProgettoVO,
			 * idUtente, cspRuoliVO, null, null)); }
			 */

			PbandiTCspSoggettoVO filtroProgettoSoggetto = new PbandiTCspSoggettoVO();
			filtroProgettoSoggetto.setIdCspProgetto(cspProgettoVO.getIdCspProgetto());

			// trovo i soggetti cancellati
			List<PbandiTCspSoggettoVO> soggettiDaCancellareVO = genericDAO
					.where(filterBy(filtroProgettoSoggetto).and(filterByKeyOf(cspSoggettiVO).negate())).select();

			Map<String, String> map = new HashMap<String, String>();
			map.put("idCspSoggetto", "idCspSoggetto");
			// cancello i ruoli dei soggetti inseriti e dei soggetti cancellati
			genericDAO.where(filterBy(beanUtil.transformList(cspSoggettiVO, PbandiTCspSoggRuoloEnteVO.class, map))
					.or(filterBy(beanUtil.transformList(soggettiDaCancellareVO, PbandiTCspSoggRuoloEnteVO.class, map))))
					.delete();

			if (soggettiDaCancellareVO.size() > 0) {
				genericDAO.where(filterByKeyOf(soggettiDaCancellareVO)).delete();
			}
			genericDAO.insertOrUpdateExisting(cspRuoliVO);

			if (cspProgettoVO == null || cspProgettoVO.getIdCspProgetto() == null)
				return null;
			else
				return cspProgettoVO.getIdCspProgetto().longValue();

		} catch (Exception e) {
			String message = "Errore nel persistere la scheda progetto: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}
	}

	private PbandiTCspProgettoVO persistiProgetto(Long idUtente, SchedaProgettoDTO schedaProgetto, Date sysdate,
			boolean datiCompletiPerAvvio) throws Exception {
		PbandiTCspProgettoVO cspProgettoVO = beanUtil.transform(schedaProgetto, PbandiTCspProgettoVO.class,
				MAP_PROGETTO_DTO_TO_VO);

		cspProgettoVO.setCup(
				StringUtil.isEmpty(cspProgettoVO.getCup()) ? null : cspProgettoVO.getCup().toUpperCase().trim());

		cspProgettoVO.setNumeroDomanda(StringUtil.isEmpty(cspProgettoVO.getNumeroDomanda()) ? null
				: cspProgettoVO.getNumeroDomanda().toUpperCase().trim());

		cspProgettoVO.setDtInizioValidita(sysdate);
		if (!StringUtil.isEmpty(schedaProgetto.getInformazioniBase().getDataGenerazione())) {
			cspProgettoVO.setDtInizioValidita(
					beanUtil.transform(schedaProgetto.getInformazioniBase().getDataGenerazione(), Date.class));
		}
		if (FLAG_TRUE.equals(schedaProgetto.getInformazioniBase().getFlagStatoProgettoProgramma())) {
			cspProgettoVO.setStatoProgramma(STATO_PROG_RELAZ_PROGRAMMA_ATTIVO);
		} else {
			cspProgettoVO.setStatoProgramma(STATO_PROG_RELAZ_PROGRAMMA_INATTIVO);
		}
		impostaUtente(cspProgettoVO, idUtente);
		cspProgettoVO
				.setFlagAvviabile(datiCompletiPerAvvio ? it.csi.pbandi.pbservizit.pbandisrv.util.Constants.FLAG_TRUE
						: it.csi.pbandi.pbservizit.pbandisrv.util.Constants.FLAG_FALSE);

		cspProgettoVO.setFlagDettaglioCup(it.csi.pbandi.pbservizit.pbandisrv.util.Constants.FLAG_FALSE);

		genericDAO.insertOrUpdateExisting(cspProgettoVO);

		return cspProgettoVO;
	}

	@SuppressWarnings("static-access")
	private void impostaUtente(GenericVO valueObject, Long idUtente) throws Exception {
		final String propName = "idUtenteIns";

		Object idUtenteIns;
		if (valueObject.isPKValid()) {
			beanUtil.setPropertyValueByName(valueObject, "idUtenteAgg", idUtente);
			idUtenteIns = beanUtil.getPropertyValueByName(genericDAO.where(filterByKeyOf(valueObject)).select().get(0),
					propName);
		} else {
			idUtenteIns = idUtente;
		}
		beanUtil.setPropertyValueByName(valueObject, propName, idUtenteIns);
	}

	private PbandiTCspSoggettoVO persistiSoggettoGenerico(SoggettoGenericoDTO soggetto, Date sysdate,
			PbandiTCspProgettoVO cspProgettoVO, Long idUtente, List<PbandiTCspSoggRuoloEnteVO> cspRuoliVO,
			String descBreveTipoAnagrafica, String descBreveTipoSoggettoCorrelato) throws Exception {
		boolean personaFisica = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.FLAG_TRUE
				.equals(soggetto.getFlagPersonaFisica());

		PbandiTCspSoggettoVO cspSoggettoVO = beanUtil.transform(soggetto, PbandiTCspSoggettoVO.class,
				MAP_SOGG_DTO_TO_VO);

		if (personaFisica) {
			beanUtil.valueCopy(soggetto.getDatiPF(), cspSoggettoVO, MAP_PF_DTO_TO_VO);
			impostaComune(cspSoggettoVO, soggetto.getDatiPF().getComuneNas(), "Nascita");
			impostaComune(cspSoggettoVO, soggetto.getDatiPF().getComuneRes(), "Residenza");

			cspSoggettoVO.setIdTipoSoggetto(decodeDescBreveTipoSoggetto(DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_FISICA));

			descBreveTipoAnagrafica = descBreveTipoAnagrafica == null
					? Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA
					: descBreveTipoAnagrafica;
		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.putAll(MAP_PG_DTO_TO_VO);

			if (it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DU.equals(soggetto.getDatiPG().getTipoDipDir())) {
				map.put("denominazioneEnteDipUni", "idDipartimento");
				map.put("ateneo", "idAteneo");
			} else if (it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_DR
					.equals(soggetto.getDatiPG().getTipoDipDir())) {
				map.put("denominazioneEnteDirReg", "idEnteCompetenza");
			} else if (it.csi.pbandi.pbworkspace.util.Constants.TIPO_DIP_DIR_PA
					.equals(soggetto.getDatiPG().getTipoDipDir())) {
				map.put("denominazioneEntePA", "idEnteCompetenza");
			} else {
				map.put("codiceFiscale", "codiceFiscale");
			}

			beanUtil.valueCopy(soggetto.getDatiPG(), cspSoggettoVO, map);

			impostaComune(cspSoggettoVO, soggetto.getDatiPG().getSedeLegale(), "SedeLegale");

			cspSoggettoVO.setIdTipoSoggetto(decodeDescBreveTipoSoggetto(DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_GIURIDICA));

			descBreveTipoAnagrafica = descBreveTipoAnagrafica == null
					? Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_GIURIDICA
					: descBreveTipoAnagrafica;

			if (soggetto != null && soggetto.getDatiPG() != null) {
				String idSettoreEnte = soggetto.getDatiPG().getIdSettoreEnte();
				if (!StringUtil.isEmpty(idSettoreEnte)) {
					BigDecimal bd = new BigDecimal(idSettoreEnte);
					cspSoggettoVO.setIdSettoreEnte(bd);
				}
			}

		}

		if (descBreveTipoSoggettoCorrelato != null) {
			cspSoggettoVO
					.setIdTipoSoggettoCorrelato(decodeDescBreveTipoSoggettoCorrelato(descBreveTipoSoggettoCorrelato));
		}

		cspSoggettoVO.setIdTipoAnagrafica(decodeDescBreveTipoAnagrafica(descBreveTipoAnagrafica));

		if (Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BENEFICIARIO.equals(descBreveTipoAnagrafica)) {
			PbandiDTipoBeneficiarioVO tipoBeneficiario = new PbandiDTipoBeneficiarioVO();
			tipoBeneficiario.setDescBreveTipoBeneficiario(Constants.DESCRIZIONE_BREVE_TIPO_BENEFICIARIO_BEN_SINGOLO);
			cspSoggettoVO.setIdTipoBeneficiario(genericDAO.findSingleWhere(tipoBeneficiario).getIdTipoBeneficiario());
		}

		cspSoggettoVO.setCodiceFiscale(
				cspSoggettoVO.getCodiceFiscale() != null ? cspSoggettoVO.getCodiceFiscale().toUpperCase() : null);
		cspSoggettoVO.setIdCspProgetto(cspProgettoVO.getIdCspProgetto());
		cspSoggettoVO.setDtInizioValidita(sysdate);
		impostaUtente(cspSoggettoVO, idUtente);

		genericDAO.insertOrUpdateExisting(cspSoggettoVO);

		String[] ruoli = personaFisica ? soggetto.getDatiPF().getRuolo() : soggetto.getDatiPG().getRuolo();
		if (ruoli != null) {
			for (String ruolo : ruoli) {
				if (!StringUtil.isEmpty(ruolo)) {
					PbandiTCspSoggRuoloEnteVO cspSoggRuoloEnteVO = new PbandiTCspSoggRuoloEnteVO();
					cspSoggRuoloEnteVO.setIdCspSoggetto(cspSoggettoVO.getIdCspSoggetto());
					cspSoggRuoloEnteVO.setDtInizioValidita(sysdate);
					impostaUtente(cspSoggRuoloEnteVO, idUtente);
					cspSoggRuoloEnteVO.setIdRuoloEnteCompetenza(beanUtil.transform(ruolo, BigDecimal.class));

					cspRuoliVO.add(cspSoggRuoloEnteVO);
				}
			}
		}
		return cspSoggettoVO;
	}

	@SuppressWarnings("static-access")
	private void impostaComune(PbandiTCspSoggettoVO cspSoggettoVO, ComuneDTO comuneDTO, String tipo) throws Exception {
		if (comuneDTO != null) {
			String luogo;
			if (FLAG_TRUE.equals(comuneDTO.getFlagNazioneItaliana())) {
				luogo = "Italiano";
			} else {
				luogo = "Estero";
			}
			beanUtil.setPropertyValueByName(cspSoggettoVO, "idComune" + luogo + tipo, comuneDTO.getIdComune());
		}
	}

	private void impostaComune(PbandiTCspProgSedeIntervVO cspSedeVO, ComuneDTO comuneDTO) throws Exception {
		if (comuneDTO != null) {
			BigDecimal idComune = beanUtil.transform(comuneDTO.getIdComune(), BigDecimal.class);
			if (FLAG_TRUE.equals(comuneDTO.getFlagNazioneItaliana())) {
				cspSedeVO.setIdComune(idComune);
			} else {
				cspSedeVO.setIdComuneEstero(idComune);
			}
		}
	}

	private void persistiSedi(Long idUtente, SchedaProgettoDTO schedaProgetto, Date sysdate,
			PbandiTCspProgettoVO cspProgettoVO) throws Exception {

		List<PbandiTCspProgSedeIntervVO> sediVO = new ArrayList<PbandiTCspProgSedeIntervVO>();
		for (SedeInterventoDTO sedeDTO : schedaProgetto.getSediIntervento()) {
			PbandiTCspProgSedeIntervVO cspSedeVO = beanUtil.transform(sedeDTO, PbandiTCspProgSedeIntervVO.class,
					MAP_SEDE_DTO_TO_VO);
			impostaComune(cspSedeVO, sedeDTO.getComuneSede());
			cspSedeVO.setIdCspProgetto(cspProgettoVO.getIdCspProgetto());
			cspSedeVO.setDtInizioValidita(sysdate);
			impostaUtente(cspSedeVO, idUtente);

			sediVO.add(cspSedeVO);
		}
		PbandiTCspProgSedeIntervVO filtroProgetto = new PbandiTCspProgSedeIntervVO();
		filtroProgetto.setIdCspProgetto(cspProgettoVO.getIdCspProgetto());

		genericDAO.where(filterBy(filtroProgetto).and(not(filterByKeyOf(sediVO)))).delete();
		genericDAO.insertOrUpdateExisting(sediVO);
	}

	private boolean hasIntermediario(SchedaProgettoDTO schedaProgetto) {
		return schedaProgetto.getFlagSalvaIntermediario() == null ? false
				: beanUtil.transform(schedaProgetto.getFlagSalvaIntermediario(), Boolean.class);
	}

	// Dato un codice fiscale, recupara i rappr. legali che verranno visualizzati
	// nella popup di selezione
	// in Scheda Progeto - Rappr.Legale/Resp.Intervento.
	public RapprLegaleCspDTO[] ricercaRapprLegaleCsp(Long idUtente, String identitaIride, String codiceFiscale)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {

		String[] nameParameter = { "idUtente", "identitaIride", "codiceFiscale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, codiceFiscale);

		RapprLegaleCspDTO[] result = new RapprLegaleCspDTO[0];
		try {

			// Legge da db i rappr. legali.
			RapprLegaleCspConCivicoVO vo = new RapprLegaleCspConCivicoVO();
			vo.setCodiceFiscaleSoggetto(codiceFiscale);
			List<RapprLegaleCspConCivicoVO> listaVO = genericDAO.findListWhere(vo);
			if (listaVO == null)
				throw new Exception("ricercaRapprLegaleCsp(): lista rappr. legali nulla.");
			logger.info("ricercaRapprLegaleCsp(): num record letti da db: " + listaVO.size());

			if (listaVO.size() == 0)
				return new RapprLegaleCspDTO[0];

			// Completa eventuali dati mancanti della sede legale.
			List<RapprLegaleCspConCivicoVO> listaVOcompletata = new ArrayList<RapprLegaleCspConCivicoVO>();
			for (RapprLegaleCspConCivicoVO b : listaVO) {
				RapprLegaleCspConCivicoVO b1 = this.completaIndirizziRapprLegale(b);
				listaVOcompletata.add(b1);
				logger.info("ricercaRapprLegaleCsp(): RapprLegaleCspVO = " + b1.toString());
			}

			// Compatta i record (1 rappr legale pu� essere associato a pi� indirizzi),
			// passando da RapprLegaleCspVO a RapprLegaleCspDTO.
			logger.info("inizio compattamento");
			List<RapprLegaleCspDTO> listaDTO = new ArrayList<RapprLegaleCspDTO>();
			RapprLegaleCspConCivicoVO voCorrente = new RapprLegaleCspConCivicoVO();
			RapprLegaleCspDTO dtoCorrente = new RapprLegaleCspDTO();
			List<IndirizziRapprLegaleCspDTO> listaIndirizziCorrente = new ArrayList<IndirizziRapprLegaleCspDTO>();
			for (RapprLegaleCspConCivicoVO b : listaVOcompletata) {

				logger.info("Elaboro record " + b.getCognome() + " " + b.getNome());
				// Gestisce il passaggio da un beneficiario all'altro.
				if (!uguali(b, dtoCorrente)) {
					logger.info("Diverso");
					// Salva in listaDTO l'oggetto DTO (1 beneficiario con pi� indirizzi) popolato
					// finora.
					if (!StringUtil.isEmpty(dtoCorrente.getCognome())) {
						logger.info("Aggancio: num indirizzi = " + listaIndirizziCorrente.size());
						IndirizziRapprLegaleCspDTO[] a = new IndirizziRapprLegaleCspDTO[1];
						a = listaIndirizziCorrente.toArray(a);
						dtoCorrente.setIndirizzi(a);
						listaDTO.add(dtoCorrente);
						listaIndirizziCorrente = new ArrayList<IndirizziRapprLegaleCspDTO>();
						logger.info("dim1 = " + listaIndirizziCorrente.size() + "; dim2 = " + a.length);
					}
					// Creo un nuovo oggetto DTO.
					logger.info("Creo un nuovo oggetto DTO");
					dtoCorrente = new RapprLegaleCspDTO();
					dtoCorrente.setCodiceFiscaleSoggetto(b.getCodiceFiscaleSoggetto());
					dtoCorrente.setCognome(b.getCognome());
					dtoCorrente.setNome(b.getNome());
					dtoCorrente.setSesso(b.getSesso());
					dtoCorrente.setDtNascita(b.getDtNascita());
					if (b.getDtNascita() != null)
						dtoCorrente.setDtNascitaStringa(DateUtil.getDate(b.getDtNascita()));
				}

				// Aggiunge gli indirizzi all'oggetto DTO corrente.
				IndirizziRapprLegaleCspDTO ind = new IndirizziRapprLegaleCspDTO();
				ind.setDescIndirizzo(b.getDescIndirizzo());
				ind.setCivico(b.getCivico());
				ind.setCap(b.getCap());
				ind.setEmail(b.getEmail());
				ind.setTelefono(b.getTelefono());
				ind.setFax(b.getFax());
				ind.setIdNazioneRes(b.getIdNazioneRes());
				ind.setDescNazioneRes(b.getDescNazioneRes());
				ind.setIdRegioneRes(b.getIdRegioneRes());
				ind.setDescRegioneRes(b.getDescRegioneRes());
				ind.setIdProvinciaRes(b.getIdProvinciaRes());
				ind.setDescProvinciaRes(b.getDescProvinciaRes());
				ind.setIdComuneRes(b.getIdComuneRes());
				ind.setDescComuneRes(b.getDescComuneRes());
				ind.setIdComuneEsteroRes(b.getIdComuneEsteroRes());
				ind.setDescComuneEsteroRes(b.getDescComuneEsteroRes());
				ind.setIdNazioneNascita(b.getIdNazioneNascita());
				ind.setDescNazioneNascita(b.getDescNazioneNascita());
				ind.setIdRegioneNascita(b.getIdRegioneNascita());
				ind.setDescRegioneNascita(b.getDescRegioneNascita());
				ind.setIdProvinciaNascita(b.getIdProvinciaNascita());
				ind.setDescProvinciaNascita(b.getDescProvinciaNascita());
				ind.setIdComuneNascita(b.getIdComuneNascita());
				ind.setDescComuneNascita(b.getDescComuneNascita());
				ind.setIdComuneEsteroNascita(b.getIdComuneEsteroNascita());
				ind.setDescComuneEsteroNascita(b.getDescComuneEsteroNascita());
				if (!indirizzoGiaInserito(ind, listaIndirizziCorrente)) {
					listaIndirizziCorrente.add(ind);
				}
			}
			logger.info("Fine ciclo");
			if (!StringUtil.isEmpty(dtoCorrente.getCognome())) {
				logger.info("Aggancio finale: num indirizzi = " + listaIndirizziCorrente.size());
				IndirizziRapprLegaleCspDTO[] a = new IndirizziRapprLegaleCspDTO[1];
				a = listaIndirizziCorrente.toArray(a);
				dtoCorrente.setIndirizzi(a);
				listaDTO.add(dtoCorrente);
			}

			// Trasforma l'elenco finale da ArrayList a [].
			result = listaDTO.toArray(result);
			logger.info("ricercaRapprLegaleCsp(): num rappr. legali trasformati: " + result.length);
			for (RapprLegaleCspDTO b : result) {
				logger.info("ricercaRapprLegaleCsp(): RapprLegaleCspDTO = " + b.toString());
			}

		} catch (Exception e) {
			String message = "Impossibile caricare la lista dei Rappresentanti Legali: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}
		return result;
	}

	private boolean indirizzoGiaInserito(IndirizziRapprLegaleCspDTO ind, List<IndirizziRapprLegaleCspDTO> lista) {
		if (ind == null || lista == null)
			return false;
		for (IndirizziRapprLegaleCspDTO indGiaInserito : lista) {
			if (uguali(ind, indGiaInserito))
				return true;
		}
		return false;
	}

	private RapprLegaleCspConCivicoVO completaIndirizziRapprLegale(RapprLegaleCspConCivicoVO b) {

		// Completo eventuali dati mancanti dell'indirizzo di residenza in input.
		if (b.getIdComuneRes() != null && b.getIdProvinciaRes() == null) {
			PbandiDComuneVO comune = new PbandiDComuneVO();
			comune.setIdComune(new BigDecimal(b.getIdComuneRes()));
			comune = genericDAO.findSingleWhere(comune);
			if (comune.getIdProvincia() != null) {
				PbandiDProvinciaVO prov = new PbandiDProvinciaVO();
				prov.setIdProvincia(comune.getIdProvincia());
				prov = genericDAO.findSingleWhere(prov);
				b.setIdProvinciaRes(prov.getIdProvincia().longValue());
				b.setDescProvinciaRes(prov.getDescProvincia());
				// logger.info("Aggiungo provinciaRes "+b.getIdProvinciaRes()+"
				// "+b.getDescProvinciaRes());
			}
		}
		if (b.getIdProvinciaRes() != null && b.getIdRegioneRes() == null) {
			PbandiDProvinciaVO prov = new PbandiDProvinciaVO();
			prov.setIdProvincia(new BigDecimal(b.getIdProvinciaRes()));
			prov = genericDAO.findSingleWhere(prov);
			if (prov.getIdRegione() != null) {
				PbandiDRegioneVO reg = new PbandiDRegioneVO();
				reg.setIdRegione(prov.getIdRegione());
				reg = genericDAO.findSingleWhere(reg);
				b.setIdRegioneRes(reg.getIdRegione().longValue());
				b.setDescRegioneRes(reg.getDescRegione());
				// logger.info("Aggiungo regioneRes "+b.getIdRegioneRes()+"
				// "+b.getDescRegioneRes());
			}
		}
		if (b.getIdRegioneRes() != null && b.getIdNazioneRes() == null) {
			b.setIdNazioneRes(new Long(118));
			b.setDescNazioneRes("ITALIA");
			// logger.info("Aggiungo nazioneRes "+b.getIdNazioneRes()+"
			// "+b.getDescNazioneRes());
		}
		if (b.getIdComuneEsteroRes() != null && b.getIdNazioneRes() == null) {
			PbandiDComuneEsteroVO comEstero = new PbandiDComuneEsteroVO();
			comEstero.setIdComuneEstero(new BigDecimal(b.getIdComuneEsteroRes()));
			comEstero = genericDAO.findSingleWhere(comEstero);
			if (comEstero.getIdNazione() != null) {
				PbandiDNazioneVO naz = new PbandiDNazioneVO();
				naz.setIdNazione(comEstero.getIdNazione());
				naz = genericDAO.findSingleWhere(naz);
				b.setIdNazioneRes(naz.getIdNazione().longValue());
				b.setDescNazioneRes(naz.getDescNazione());
				// logger.info("Aggiungo nazione estera res "+b.getIdNazioneRes()+"
				// "+b.getDescNazioneRes());
			}
		}

		// Completo eventuali dati mancanti dell'indirizzo di nascita in input.
		if (b.getIdComuneNascita() != null && b.getIdProvinciaNascita() == null) {
			PbandiDComuneVO comune = new PbandiDComuneVO();
			comune.setIdComune(new BigDecimal(b.getIdComuneNascita()));
			comune = genericDAO.findSingleWhere(comune);
			if (comune.getIdProvincia() != null) {
				PbandiDProvinciaVO prov = new PbandiDProvinciaVO();
				prov.setIdProvincia(comune.getIdProvincia());
				prov = genericDAO.findSingleWhere(prov);
				b.setIdProvinciaNascita(prov.getIdProvincia().longValue());
				b.setDescProvinciaNascita(prov.getDescProvincia());
				// logger.info("Aggiungo provincia nascita "+b.getIdProvinciaNascita()+"
				// "+b.getDescProvinciaNascita());
			}
		}
		if (b.getIdProvinciaNascita() != null && b.getIdRegioneNascita() == null) {
			PbandiDProvinciaVO prov = new PbandiDProvinciaVO();
			prov.setIdProvincia(new BigDecimal(b.getIdProvinciaNascita()));
			prov = genericDAO.findSingleWhere(prov);
			if (prov.getIdRegione() != null) {
				PbandiDRegioneVO reg = new PbandiDRegioneVO();
				reg.setIdRegione(prov.getIdRegione());
				reg = genericDAO.findSingleWhere(reg);
				b.setIdRegioneNascita(reg.getIdRegione().longValue());
				b.setDescRegioneNascita(reg.getDescRegione());
				// logger.info("Aggiungo regione Nascita "+b.getIdRegioneNascita()+"
				// "+b.getDescRegioneNascita());
			}
		}
		if (b.getIdRegioneNascita() != null && b.getIdNazioneNascita() == null) {
			b.setIdNazioneNascita(new Long(118));
			b.setDescNazioneNascita("ITALIA");
			// logger.info("Aggiungo nazione Nascita "+b.getIdNazioneNascita()+"
			// "+b.getDescNazioneNascita());
		}
		if (b.getIdComuneEsteroNascita() != null && b.getIdNazioneNascita() == null) {
			PbandiDComuneEsteroVO comEstero = new PbandiDComuneEsteroVO();
			comEstero.setIdComuneEstero(new BigDecimal(b.getIdComuneEsteroNascita()));
			comEstero = genericDAO.findSingleWhere(comEstero);
			if (comEstero.getIdNazione() != null) {
				PbandiDNazioneVO naz = new PbandiDNazioneVO();
				naz.setIdNazione(comEstero.getIdNazione());
				naz = genericDAO.findSingleWhere(naz);
				b.setIdNazioneNascita(naz.getIdNazione().longValue());
				b.setDescNazioneNascita(naz.getDescNazione());
				// logger.info("Aggiungo nazione estera Nascita "+b.getIdNazioneNascita()+"
				// "+b.getDescNazioneNascita());
			}
		}

		return b;
	}

	public EsitoSoggettoDTO caricaInfoDirezioneRegionale(Long idUtente, String identitaIride, SoggettoPGDTO datiPG)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {

		EsitoSoggettoDTO esitoSoggettoDTO = new EsitoSoggettoDTO();

		doCaricaInfoDirezioneRegionalePG(datiPG);
		esitoSoggettoDTO.setDatiPG(datiPG);
		esitoSoggettoDTO.setEsito(true);

		return esitoSoggettoDTO;
	}

	private void doCaricaInfoDirezioneRegionalePG(SoggettoPGDTO soggettoDTO) throws UnrecoverableException {
		PbandiTEnteCompetenzaVO competenzaVO = new PbandiTEnteCompetenzaVO();
		competenzaVO
				.setIdEnteCompetenza(beanUtil.transform(soggettoDTO.getDenominazioneEnteDirReg(), BigDecimal.class));
		List<PbandiTEnteCompetenzaVO> listCompetenza = genericDAO.findListWhere(competenzaVO);
		soggettoDTO.setPartitaIvaSedeLegale("");
		soggettoDTO.setSedeLegale(new ComuneDTO());
		String nazione = nazioneDefault();

		ComuneVO comuneVO = new ComuneVO();
		comuneVO.setIdNazione(beanUtil.transform(nazione, BigDecimal.class));
		try {
			caricaDescrizioniPerComune(soggettoDTO.getSedeLegale(), soggettoDTO.getSedeLegale());
		} catch (Exception e) {
			throw new UnrecoverableException(e.getMessage(), e);
		}
		soggettoDTO.setSedeLegale(beanUtil.transform(comuneVO, ComuneDTO.class));
		soggettoDTO.setIndirizzoSedeLegale("");
		soggettoDTO.setCapSedeLegale("");

		if (listCompetenza.size() == 1) {
			BigDecimal idIndirizzo = listCompetenza.get(0).getIdIndirizzo();
			PbandiTIndirizzoVO indirizzoVO = new PbandiTIndirizzoVO();
			indirizzoVO.setIdIndirizzo(idIndirizzo);
			if (idIndirizzo != null) {
				List<PbandiTIndirizzoVO> listIndirizzo = genericDAO.findListWhere(indirizzoVO);
				comuneVO = new ComuneVO();
				comuneVO.setIdComune(listIndirizzo.get(0).getIdComune());
				if (listIndirizzo.get(0).getIdComuneEstero() != null) {
					comuneVO.setIdComune(listIndirizzo.get(0).getIdComuneEstero());
					comuneVO.setFlagNazioneItaliana(FLAG_FALSE);
				} else {
					comuneVO.setIdComune(listIndirizzo.get(0).getIdComune());
					comuneVO.setFlagNazioneItaliana(FLAG_TRUE);
				}
				comuneVO = genericDAO.findSingleWhere(comuneVO);
				soggettoDTO.setSedeLegale(beanUtil.transform(comuneVO, ComuneDTO.class));
				try {
					caricaDescrizioniPerComune(comuneVO, soggettoDTO.getSedeLegale());
				} catch (Exception e) {
					throw new UnrecoverableException(e.getMessage(), e);
				}
				soggettoDTO.setIndirizzoSedeLegale(listIndirizzo.get(0).getDescIndirizzo());
				soggettoDTO.setCapSedeLegale(listIndirizzo.get(0).getCap());
			}
			String pariva = listCompetenza.get(0).getCodiceFiscaleEnte();
			if (pariva != null && pariva.contains("-")) {
				soggettoDTO.setPartitaIvaSedeLegale(pariva.substring(0, pariva.indexOf("-")));
			}
		}
	}

	// Creato sulla falsa riga di caricaInfoDirezioneRegionale
	public EsitoSoggettoDTO caricaInfoPubblicaAmministrazione(Long idUtente, String identitaIride, SoggettoPGDTO datiPG)
			throws CSIException, SystemException, UnrecoverableException, GestioneAvvioProgettoException {

		EsitoSoggettoDTO esitoSoggettoDTO = new EsitoSoggettoDTO();

		doCaricaInfoPubblicaAmministrazionePG(datiPG);
		esitoSoggettoDTO.setDatiPG(datiPG);
		esitoSoggettoDTO.setEsito(true);

		return esitoSoggettoDTO;
	}

	// Creato sulla falsa riga di doCaricaInfoDirezioneRegionalePG().
	private void doCaricaInfoPubblicaAmministrazionePG(SoggettoPGDTO soggettoDTO) throws UnrecoverableException {

		// PbandiTEnteCompetenzaVO competenzaVO = new PbandiTEnteCompetenzaVO();
		// competenzaVO.setIdEnteCompetenza(beanUtil.transform(soggettoDTO.getDenominazioneEntePA(),
		// BigDecimal.class));
		// List<PbandiTEnteCompetenzaVO> listCompetenza =
		// genericDAO.findListWhere(competenzaVO);

		it.csi.pbandi.pbworkspace.integration.vo.PbandiTEnteCompetenzaVO competenzaVO = new it.csi.pbandi.pbworkspace.integration.vo.PbandiTEnteCompetenzaVO();
		competenzaVO.setIdEnteCompetenza(beanUtil.transform(soggettoDTO.getDenominazioneEntePA(), BigDecimal.class));
		List<it.csi.pbandi.pbworkspace.integration.vo.PbandiTEnteCompetenzaVO> listCompetenza = genericDAO
				.findListWhere(competenzaVO);

		soggettoDTO.setPartitaIvaSedeLegale("");
		soggettoDTO.setSedeLegale(new ComuneDTO());
		String nazione = nazioneDefault();
		ComuneVO comuneVO = new ComuneVO();
		comuneVO.setIdNazione(beanUtil.transform(nazione, BigDecimal.class));
		try {
			caricaDescrizioniPerComune(soggettoDTO.getSedeLegale(), soggettoDTO.getSedeLegale());
		} catch (Exception e) {
			throw new UnrecoverableException(e.getMessage(), e);
		}
		soggettoDTO.setSedeLegale(beanUtil.transform(comuneVO, ComuneDTO.class));
		soggettoDTO.setIndirizzoSedeLegale("");
		soggettoDTO.setCapSedeLegale("");

		if (listCompetenza.size() == 1) {
			BigDecimal idIndirizzo = listCompetenza.get(0).getIdIndirizzo();
			PbandiTIndirizzoVO indirizzoVO = new PbandiTIndirizzoVO();
			indirizzoVO.setIdIndirizzo(idIndirizzo);
			if (idIndirizzo != null) {
				List<PbandiTIndirizzoVO> listIndirizzo = genericDAO.findListWhere(indirizzoVO);
				comuneVO = new ComuneVO();
				comuneVO.setIdComune(listIndirizzo.get(0).getIdComune());
				if (listIndirizzo.get(0).getIdComuneEstero() != null) {
					comuneVO.setIdComune(listIndirizzo.get(0).getIdComuneEstero());
					comuneVO.setFlagNazioneItaliana(FLAG_FALSE);
				} else {
					comuneVO.setIdComune(listIndirizzo.get(0).getIdComune());
					comuneVO.setFlagNazioneItaliana(FLAG_TRUE);
				}
				comuneVO = genericDAO.findSingleWhere(comuneVO);
				soggettoDTO.setSedeLegale(beanUtil.transform(comuneVO, ComuneDTO.class));
				try {
					caricaDescrizioniPerComune(comuneVO, soggettoDTO.getSedeLegale());
				} catch (Exception e) {
					throw new UnrecoverableException(e.getMessage(), e);
				}
				soggettoDTO.setIndirizzoSedeLegale(listIndirizzo.get(0).getDescIndirizzo());
				soggettoDTO.setCapSedeLegale(listIndirizzo.get(0).getCap());
			}

			String cf = listCompetenza.get(0).getCodiceFiscaleEnte();
			String pIva = listCompetenza.get(0).getPartitaIvaEnte();
			if (!StringUtil.isEmpty(pIva))
				soggettoDTO.setPartitaIvaSedeLegale(pIva);
			else
				soggettoDTO.setPartitaIvaSedeLegale(cf);
		}
	}

	/*
	 * 
	 * public ProgettoDTO[] findProgetti(Long idUtente, String identitaIride, String
	 * codUtente, ProgettoDTO progetto) throws CSIException, SystemException,
	 * UnrecoverableException, GestioneAvvioProgettoException {
	 * 
	 * 
	 * String[] nameParameter = { "codUtente" };
	 * ValidatorInput.verifyNullValue(nameParameter, codUtente);
	 * 
	 * ProgettoDTO[] result = null; try { Map<String, String> mapVOToDTO = new
	 * HashMap<String, String>(); mapVOToDTO.put("titoloProgetto", "titolo");
	 * mapVOToDTO.put(BENEFICIARIO, BENEFICIARIO);
	 * 
	 * Boolean visualizzaSoloSchedeProgetto = getSoggettoManager()
	 * .hasPermessoUtenteCorrente(idUtente,identitaIride,UseCaseConstants.
	 * UC_TRSCSP001);
	 * 
	 * if (visualizzaSoloSchedeProgetto != null && visualizzaSoloSchedeProgetto) {
	 * mapVOToDTO.put("idCspProgetto", "id"); mapVOToDTO.put("cup", "cup");
	 * mapVOToDTO.put("numeroDomanda", "codice");
	 * 
	 * Map<String, String> mapDTOToVO = new HashMap<String, String>();
	 * mapDTOToVO.put("titolo", "titoloProgetto"); mapDTOToVO.put("cup", "cup");
	 * mapDTOToVO.put("codice", "numeroDomanda"); mapDTOToVO.put(BENEFICIARIO,
	 * BENEFICIARIO); CspProgettoSoggettoBeneficiarioVO filtroForm = beanUtil
	 * .transform(progetto, CspProgettoSoggettoBeneficiarioVO.class, mapDTOToVO);
	 * 
	 * CspProgettoSoggettoBeneficiarioVO filtroBandoLinea = new
	 * CspProgettoSoggettoBeneficiarioVO(); filtroBandoLinea
	 * .setProgrBandoLineaIntervento(beanUtil.transform( progetto.getIdBandoLinea(),
	 * BigDecimal.class));
	 * 
	 * result = beanUtil .transform( genericDAO .where(filterBy(filtroBandoLinea)
	 * .and(isFieldNull( CspProgettoSoggettoBeneficiarioVO.class, "dtElaborazione"))
	 * .and(new LikeStartsWithCondition<CspProgettoSoggettoBeneficiarioVO>(
	 * filtroForm))).select(), ProgettoDTO.class, mapVOToDTO);
	 * 
	 * } else { mapVOToDTO.put("idProgetto", "id");
	 * mapVOToDTO.put("codiceVisualizzato", "codice"); mapVOToDTO.put("cup", "cup");
	 * 
	 * ProgettoBandoLineaVO progettoVO = new ProgettoBandoLineaVO();
	 * progettoVO.setCodiceVisualizzato(progetto.getCodice());
	 * progettoVO.setTitoloProgetto(progetto.getTitolo());
	 * progettoVO.setCup(progetto.getCup()); ProgettoBandoLineaVO
	 * beneficiarioProgettoVO = new ProgettoBandoLineaVO();
	 * beneficiarioProgettoVO.setBeneficiario(progetto .getBeneficiario());
	 * ProgettoBandoLineaVO progettoFiltroVO = new ProgettoBandoLineaVO();
	 * progettoFiltroVO.setIdBandoLinea(beanUtil.transform(
	 * progetto.getIdBandoLinea(), Long.class));
	 * 
	 * List<String> isNullProperties = new ArrayList<String>();
	 * isNullProperties.add("idIstanzaProcesso");
	 * 
	 * List<ProgettoBandoLineaVO> progettiVO = genericDAO .findListWhere(new
	 * AndCondition<ProgettoBandoLineaVO>( new
	 * LikeStartsWithCondition<ProgettoBandoLineaVO>( progettoVO), new
	 * LikeContainsCondition<ProgettoBandoLineaVO>( beneficiarioProgettoVO), new
	 * FilterCondition<ProgettoBandoLineaVO>( progettoFiltroVO), new
	 * NullCondition<ProgettoBandoLineaVO>( ProgettoBandoLineaVO.class,
	 * "idIstanzaProcesso"), new NullCondition<ProgettoBandoLineaVO>(
	 * ProgettoBandoLineaVO.class, "flagPrenotAvvio")));
	 * 
	 * result = getBeanUtil().transform(progettiVO.toArray(), ProgettoDTO.class,
	 * mapVOToDTO); } } catch (Exception e) { String message =
	 * "Impossibile caricare la lista dei progetti:" + e.getMessage();
	 * logger.error(message, e); throw new UnrecoverableException(message, e); }
	 * return result; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public EsitoSchedaProgettoDTO caricaDettaglioCup(Long idUtente, String
	 * identitaIride, SchedaProgettoDTO schedaProgetto) throws CSIException,
	 * SystemException, UnrecoverableException, GestioneAvvioProgettoException {
	 * String[] nameParameter = { "schedaProgetto", "schedaProgetto.idBandoLinea" };
	 * ValidatorInput.verifyNullValue(nameParameter, schedaProgetto, schedaProgetto
	 * != null ? schedaProgetto.getIdBandoLinea() : null);
	 * 
	 * logger.info("\n\n\ncaricaDettaglioCup....");
	 * 
	 * EsitoSchedaProgettoDTO esitoSchedaProgettoDTO = new EsitoSchedaProgettoDTO();
	 * 
	 * esitoSchedaProgettoDTO = doCaricaDettaglioCup(idUtente, identitaIride,
	 * schedaProgetto);
	 * 
	 * 
	 * return esitoSchedaProgettoDTO; }
	 * 
	 * public EsitoOperazioneDTO verificaCup(Long idUtente, String identitaIride,
	 * String codiceCup, String idCspProgetto) throws CSIException, SystemException,
	 * UnrecoverableException, GestioneAvvioProgettoException {
	 * 
	 * String[] nameParameter = { "codiceCup" };
	 * ValidatorInput.verifyNullValue(nameParameter, codiceCup); EsitoOperazioneDTO
	 * result = new EsitoOperazioneDTO(); result.setEsito(true);
	 * 
	 * String cupToCheck = StringUtil.isEmpty(codiceCup) ? codiceCup :
	 * codiceCup.toUpperCase().trim();
	 * 
	 * final CheckRunner checkRunnerControlloCongruitaDati = getChecker() .getNew();
	 * checkRunnerControlloCongruitaDati.add(new CheckUnivocitaCUP(cupToCheck,
	 * beanUtil.transform(idCspProgetto, BigDecimal.class))); SchedaProgettoDTO
	 * schedaProgetto = new SchedaProgettoDTO();
	 * schedaProgetto.setInformazioniBase(new InformazioniBaseDTO());
	 * schedaProgetto.getInformazioniBase().setCup(cupToCheck);
	 * checkRunnerControlloCongruitaDati.add(new FieldCheckLenght(
	 * "informazioniBase.cup", schedaProgetto,
	 * Constants.CUP_SCHEDA_PROGETTO_LUNGHEZZA_CAMPO.intValue(),
	 * Constants.CUP_SCHEDA_PROGETTO_LUNGHEZZA_CAMPO.intValue(),
	 * ErrorConstants.DIMENSIONE_CAMPO_CUP_ERRATA));
	 * 
	 * if (!checkRunnerControlloCongruitaDati.runAll()) { Set<String> chiaviCampi =
	 * new HashSet<String>(); Set<String> erroriCampi = new HashSet<String>(); for
	 * (CheckRunner check : new CheckRunner[] { checkRunnerControlloCongruitaDati })
	 * { Map<String, Set<String>> fieldMessages = check .getFieldMessages(); for
	 * (String fieldKey : fieldMessages.keySet()) { for (String messageKey :
	 * fieldMessages.get(fieldKey)) { chiaviCampi.add("informazioniBase.cup");
	 * erroriCampi.add(messageKey); result.setEsito(false); } } for (String message
	 * : check.getMessages()) { if (message.equals(ErrorConstants.KEY_CUP_PRESENTE))
	 * chiaviCampi.add("informazioniBase.cup"); erroriCampi.add(message);
	 * result.setEsito(false); } result.setChiaviCampi(chiaviCampi .toArray(new
	 * String[chiaviCampi.size()])); result.setErroriCampi(erroriCampi .toArray(new
	 * String[erroriCampi.size()])); } }
	 * 
	 * 
	 * return result; }
	 * 
	 * private EsitoSchedaProgettoDTO doCaricaDettaglioCup(Long idUtente, String
	 * identitaIride, SchedaProgettoDTO schedaProgetto) throws CSIException,
	 * SystemException, UnrecoverableException, GestioneAvvioProgettoException {
	 * 
	 * EsitoSchedaProgettoDTO result = new EsitoSchedaProgettoDTO();
	 * 
	 * try {
	 * logger.info("Allineamento informazioni base: invoking simonDAO.allineaCup ");
	 * DettaglioCupVO dettaglioCup = simonDAO.allineaCup(idUtente,
	 * schedaProgetto.getInformazioniBase().getCup(),
	 * schedaProgetto.getIdProgetto()); if (!dettaglioCup.isEsito()) {
	 * result.setEsito(false); String[] s = new String[1]; s[0] = new
	 * String(dettaglioCup.getCodiceMessaggio()); result.setErroriCampi(s);
	 * result.setSchedaProgetto(schedaProgetto); } else {
	 * caricaDefaultInfoBaseAllineaCup(schedaProgetto);
	 * beanUtil.valueCopy(dettaglioCup, schedaProgetto,
	 * MAP_DETTAGLIO_CUP_VO_TO_DTO);
	 * 
	 * 
	 * for (String path : MAP_SIMON_TIPO_OPERAZIONE.keySet()) { if ((Boolean)
	 * BeanUtil.getPropertyValueByName( dettaglioCup, path)) { DecodificaDTO[]
	 * decodifiche = decodificheManager
	 * .findDecodifiche(GestioneDatiDiDominioSrv.TIPO_OPERAZIONE); for
	 * (DecodificaDTO d : decodifiche) { if
	 * (MAP_SIMON_TIPO_OPERAZIONE.get(path).equals( d.getDescrizioneBreve())) {
	 * beanUtil.setPropertyValueByName( schedaProgetto,
	 * "informazioniBase.idTipoOperazione", d.getId()); } } } }
	 * 
	 * caricaAltroSoggettoDefault(schedaProgetto); if
	 * (popolaNaturaCipeDaDettaglioCup(dettaglioCup, schedaProgetto)) {
	 * popolaTipologiaCipeDaDettaglioCup(dettaglioCup, schedaProgetto); } if
	 * (popolaSettoreCipeDaDettaglioCup(dettaglioCup, schedaProgetto) &&
	 * popolaSottoSettoreCipeDaDettaglioCup( dettaglioCup, schedaProgetto)) {
	 * popolaCategoriaCipeDaDettaglioCup(dettaglioCup, schedaProgetto); } if
	 * (!StringUtil.isEmpty(dettaglioCup .getDatiGeneraliProgetto().getStato())) {
	 * String stato = dettaglioCup.getDatiGeneraliProgetto() .getStato(); stato =
	 * StringUtil.isEmpty(stato) ? "" : stato .toUpperCase();
	 * schedaProgetto.getInformazioniBase() .setFlagStatoProgettoProgramma(
	 * MAP_DETTAGLIO_CUP_STATO_PROGRAMMA .get(stato)); }
	 * 
	 * ArrayList<LocalizzazioneVO> localizzazioni =
	 * dettaglioCup.getLocalizzazioni(); ArrayList<ComuneVO> comuniTrovatiVO = new
	 * ArrayList<ComuneVO>(); if (localizzazioni != null && localizzazioni.size() >
	 * 0) { for (LocalizzazioneVO localizzazione : localizzazioni) { String
	 * codiceIstat = dettaglioCupManager.getCodiceComuneIstat(localizzazione); //
	 * String codiceIstat = //
	 * ricavaCodiceIstatComuneDaCodice(localizzazione.getCodice()); if
	 * (StringUtil.isEmpty(codiceIstat) || ((!StringUtil.isEmpty(codiceIstat)) &&
	 * (!aggiungiComuneSeTrovatoPerIstat( comuniTrovatiVO, codiceIstat)))) {
	 * String[] infoComune = dettaglioCupManager
	 * .getInfoComuneProvinciaDaDescrizione(localizzazione); // String[] infoComune
	 * = // ricavaInfoComuneDaDescrizione(localizzazione // .getDescrizione()); if
	 * (!StringUtil.isEmpty(infoComune[0])) { aggiungiComuneSeTrovatoPerDescrizione(
	 * comuniTrovatiVO, infoComune); } } } schedaProgetto
	 * .setSediIntervento(allineaSedi(comuniTrovatiVO)); }
	 * schedaProgetto.getInformazioniBase().setFlagBeneficiarioCup(FLAG_FALSE);
	 * 
	 * if (!StringUtil.isEmpty(dettaglioCup
	 * .getDescrizioneDettaglioCup().getDenominazione())) {
	 * schedaProgetto.getInformazioniBase() .setFlagBeneficiarioCup(FLAG_TRUE);
	 * schedaProgetto.getBeneficiario().setDatiPG( new SoggettoPGDTO());
	 * schedaProgetto.getBeneficiario().getDatiPG() .setSedeLegale(new ComuneDTO());
	 * SedeInterventoDTO sedeInterventoDefault = new SedeInterventoDTO();
	 * sedeInterventoDefault.setComuneSede(new ComuneDTO());
	 * caricaSedeInterventoDefault(sedeInterventoDefault);
	 * schedaProgetto.getBeneficiario().setFlagPersonaFisica(FLAG_FALSE);
	 * caricaBeneficiarioPGDefault(schedaProgetto,sedeInterventoDefault.
	 * getComuneSede()); allineaSoggettoBeneficiarioPG(schedaProgetto,dettaglioCup);
	 * } if (!StringUtil.isEmpty(dettaglioCup
	 * .getDescrizioneDettaglioCup().getDescStrumento())) {
	 * PbandiDTipoStrumentoProgrVO tipoStrumentoProgrVO = new
	 * PbandiDTipoStrumentoProgrVO();
	 * tipoStrumentoProgrVO.setDescStrumento(dettaglioCup
	 * .getDescrizioneDettaglioCup() .getDescStrumento());
	 * List<PbandiDTipoStrumentoProgrVO> listTipoStrumentoProgrVO = genericDAO
	 * .findListWhere(tipoStrumentoProgrVO); if (listTipoStrumentoProgrVO.size() ==
	 * 1) { schedaProgetto .getInformazioniBase() .setIdTipoStrumentoProgrammazione(
	 * beanUtil.transform( listTipoStrumentoProgrVO .get(0)
	 * .getIdTipoStrumentoProgr(), String.class)); } }
	 * 
	 * if (!StringUtil.isEmpty(dettaglioCup
	 * .getAttivitaEconomicaBeneficiario().getSezione())) { PbandiDSettoreAttivitaVO
	 * settoreAttivitaVO = new PbandiDSettoreAttivitaVO();
	 * settoreAttivitaVO.setDescSettore(dettaglioCup
	 * .getAttivitaEconomicaBeneficiario() .getSezione());
	 * List<PbandiDSettoreAttivitaVO> listaSettoreAttivita = genericDAO
	 * .findListWhere(settoreAttivitaVO); if (listaSettoreAttivita.size() == 1) {
	 * schedaProgetto .getInformazioniBase() .setIdSettoreAttivita(
	 * beanUtil.transform( listaSettoreAttivita .get(0) .getIdSettoreAttivita(),
	 * String.class)); if (!StringUtil.isEmpty(dettaglioCup
	 * .getAttivitaEconomicaBeneficiario() .getClasse())) { AttivitaAtecoVO
	 * attivitaAtecoVO = new AttivitaAtecoVO();
	 * attivitaAtecoVO.setDescAteco(dettaglioCup .getAttivitaEconomicaBeneficiario()
	 * .getClasse()); attivitaAtecoVO .setIdSettoreAttivita(listaSettoreAttivita
	 * .get(0).getIdSettoreAttivita()); List<AttivitaAtecoVO> listAttivitaAteco =
	 * genericDAO .findListWhere(attivitaAtecoVO); if (listAttivitaAteco.size() ==
	 * 1) { schedaProgetto .getInformazioniBase() .setIdAttivitaAteco(
	 * beanUtil.transform( listAttivitaAteco .get(0) .getIdAttivitaAteco(),
	 * String.class)); } } } }
	 * popolaAttivitaAtecoBeneficiarioDaInformazioniBase(schedaProgetto);
	 * 
	 * result.setSchedaProgetto(schedaProgetto); CheckRunner
	 * checkRunnerControlloCongruitaDati =
	 * creaControlliCongruitaDati(schedaProgetto);
	 * checkRunnerControlloCongruitaDati.runAll();
	 * 
	 * Long idProgetto = schedaProgetto.getIdProgetto(); BigDecimal idProcesso=null;
	 * if(idProgetto!=null){ Long processo =
	 * neofluxBusiness.getProcesso(idUtente,identitaIride,idProgetto);
	 * if(processo!=null){ idProcesso=new BigDecimal(processo); } }
	 * 
	 * CheckRunner checkRunnerControlloSufficientiAlSalvataggio =
	 * creaControlliDatiSufficientiAlSalvataggio(schedaProgetto,idProcesso);
	 * checkRunnerControlloSufficientiAlSalvataggio.runAll(); CheckRunner
	 * checkRunnerControlloDatiPerAvvio =
	 * creaControlliDatiPerAvvio(schedaProgetto,idProcesso);
	 * 
	 * checkRunnerControlloDatiPerAvvio.runAll();
	 * 
	 * ArrayList<String> chiaviCampi = settaCampiErrori(result,
	 * checkRunnerControlloCongruitaDati,
	 * checkRunnerControlloSufficientiAlSalvataggio,
	 * checkRunnerControlloDatiPerAvvio);
	 * 
	 * result.setEsito(true);
	 * 
	 * }
	 * 
	 * } catch (Exception e) { String message =
	 * "Errore nell'allineamento cup della scheda progetto: " + e.getMessage();
	 * logger.error(message, e); throw new UnrecoverableException(message, e); }
	 * 
	 * return result; }
	 * 
	 * private void allineaSoggettoBeneficiarioPG( SchedaProgettoDTO schedaProgetto,
	 * DettaglioCupVO dettaglioCup) { String denominazione =
	 * dettaglioCup.getDescrizioneDettaglioCup() .getDenominazione(); SoggettoPGDTO
	 * soggettoPGDTO = schedaProgetto.getBeneficiario() .getDatiPG();
	 * 
	 * soggettoPGDTO.setAteneo(null);
	 * soggettoPGDTO.setDenominazioneEnteDipUni(null);
	 * soggettoPGDTO.setDenominazioneEnteDirReg(null);
	 * soggettoPGDTO.setTipoDipDir(null);
	 * 
	 * PbandiDAteneoVO ateneoVO = new PbandiDAteneoVO();
	 * ateneoVO.setDescAteneo(denominazione); List<PbandiDAteneoVO> listAteneo =
	 * genericDAO.findListWhere(ateneoVO); if (listAteneo.size() == 1) { ateneoVO =
	 * listAteneo.get(0);
	 * soggettoPGDTO.setAteneo(beanUtil.transform(ateneoVO.getIdAteneo(),
	 * String.class)); soggettoPGDTO
	 * .setTipoDipDir(it.csi.pbandi.pbandiutil.common.Constants.TIPO_DIP_DIR_DU); }
	 * else { PbandiDDipartimentoVO dipartimentoVO = new PbandiDDipartimentoVO();
	 * dipartimentoVO.setDescDipartimento(denominazione);
	 * List<PbandiDDipartimentoVO> listaDipartimento = genericDAO
	 * .findListWhere(dipartimentoVO); if (listaDipartimento.size() == 1) {
	 * soggettoPGDTO.setAteneo(beanUtil.transform(listaDipartimento
	 * .get(0).getIdAteneo(), String.class));
	 * soggettoPGDTO.setDenominazioneEnteDipUni(beanUtil.transform(
	 * listaDipartimento.get(0).getIdDipartimento(), String.class)); soggettoPGDTO
	 * .setTipoDipDir(it.csi.pbandi.pbandiutil.common.Constants.TIPO_DIP_DIR_DU); }
	 * else { final String cDirezione = "DIREZIONE"; if
	 * (denominazione.toUpperCase().startsWith(cDirezione)) { String
	 * descEnteCompetenza = ltrim(denominazione.substring(
	 * cDirezione.length()).trim()); PbandiTEnteCompetenzaVO enteCompetenzaVO = new
	 * PbandiTEnteCompetenzaVO();
	 * 
	 * enteCompetenzaVO.setDescEnte(descEnteCompetenza);
	 * List<PbandiTEnteCompetenzaVO> listaEnteCompetenza = genericDAO
	 * .findListWhere(new LikeEndsWithCondition<PbandiTEnteCompetenzaVO>(
	 * enteCompetenzaVO)); if (listaEnteCompetenza.size() == 1) { BigDecimal
	 * idEnteCompetenza = listaEnteCompetenza.get(0).getIdEnteCompetenza(); String
	 * tipo = this.getDescBreveTipoEnteCompetenza(idEnteCompetenza); if
	 * (DESC_BREVE_TIPO_ENTE_COMPETENZA_PUBBLICA_AMMINISTRAZIONE.equalsIgnoreCase(
	 * tipo)) { soggettoPGDTO.setDenominazioneEntePA(beanUtil
	 * .transform(listaEnteCompetenza.get(0) .getIdEnteCompetenza(), String.class));
	 * soggettoPGDTO
	 * .setTipoDipDir(it.csi.pbandi.pbandiutil.common.Constants.TIPO_DIP_DIR_PA); }
	 * else { soggettoPGDTO.setDenominazioneEnteDirReg(beanUtil
	 * .transform(listaEnteCompetenza.get(0) .getIdEnteCompetenza(), String.class));
	 * soggettoPGDTO
	 * .setTipoDipDir(it.csi.pbandi.pbandiutil.common.Constants.TIPO_DIP_DIR_DR); }
	 * 
	 * } } } if (StringUtil.isEmpty(soggettoPGDTO.getTipoDipDir())) { soggettoPGDTO
	 * .setTipoDipDir(it.csi.pbandi.pbandiutil.common.Constants.TIPO_DIP_DIR_NA);
	 * soggettoPGDTO.setDenominazione(denominazione);
	 * soggettoPGDTO.setPartitaIvaSedeLegale(dettaglioCup
	 * .getDescrizioneDettaglioCup().getPartitaIva());
	 * soggettoPGDTO.setCodiceFiscale(dettaglioCup
	 * .getDescrizioneDettaglioCup().getPartitaIva()); } } }
	 * 
	 * // NTH spostare statica in StringUtil private String ltrim(String s) { if (s
	 * == null) { return ""; } while (s.charAt(0) == ' ' && s.length() > 0) { s =
	 * s.substring(1); } return s; }
	 * 
	 * private void aggiungiComuneSeTrovatoPerDescrizione( ArrayList<ComuneVO>
	 * comuniTrovatiVO, String[] infoComune) { ComuneVO comuneVO = new ComuneVO();
	 * comuneVO.setDescComune(infoComune[0]);
	 * comuneVO.setSiglaProvincia(infoComune[1]);
	 * comuneVO.setFlagNazioneItaliana(FLAG_TRUE); List<ComuneVO> comuniVO =
	 * genericDAO.findListWhere(comuneVO); if (comuniVO != null && comuniVO.size() >
	 * 0) { if (comuniVO.size() == 1) { comuniTrovatiVO.add(comuniVO.get(0)); } }
	 * else { comuneVO.setSiglaProvincia(null);
	 * comuneVO.setFlagNazioneItaliana(FLAG_FALSE); comuniVO =
	 * genericDAO.findListWhere(comuneVO); if (comuniVO != null && comuniVO.size()
	 * == 1) { comuniTrovatiVO.add(comuniVO.get(0)); } } }
	 * 
	 * private boolean aggiungiComuneSeTrovatoPerIstat( ArrayList<ComuneVO>
	 * comuniTrovatiVO, String codiceIstat) { boolean trovato = false; ComuneVO
	 * comuneVO = new ComuneVO(); comuneVO.setCodIstatComune(codiceIstat);
	 * comuneVO.setFlagNazioneItaliana(FLAG_TRUE); List<ComuneVO> comuniVO =
	 * genericDAO.findListWhere(comuneVO); if (comuniVO != null && comuniVO.size() >
	 * 0) { if (comuniVO.size() == 1) { comuniTrovatiVO.add(comuniVO.get(0));
	 * trovato = true; } } return trovato; }
	 * 
	 * 
	 * 
	 * // private String[] ricavaInfoComuneDaDescrizione(String descrizione) { //
	 * String stringaDaEscludere = "Comune di "; // String descComune = ""; //
	 * String[] infoComune = new String[2]; // infoComune[0] = new String(""); //
	 * infoComune[1] = null; // int pos = descrizione.toUpperCase().indexOf( //
	 * stringaDaEscludere.toUpperCase()); // if (pos >= 0) { // descComune =
	 * descrizione.substring(0, pos) // + descrizione.substring(pos +
	 * stringaDaEscludere.length()); // int posProvincia = descComune.indexOf("(");
	 * // if (posProvincia > 0) { // infoComune[0] = descComune.substring(0,
	 * posProvincia - 1) // .trim(); // } else { // infoComune[0] =
	 * descComune.trim(); // } // int posProvinciaLast = descComune.indexOf(")"); //
	 * if (posProvinciaLast > 0 && posProvinciaLast >= posProvincia + 2) { // if
	 * (descComune.substring(posProvincia + 1, posProvinciaLast) // .trim().length()
	 * == 2) { // infoComune[1] = descComune.substring(posProvincia + 1, //
	 * posProvinciaLast).trim(); // } // } // } // return infoComune; // }
	 * 
	 * // private String ricavaCodiceIstatComuneDaCodice(String codice) { // String
	 * codiceIstat = ""; // if (!StringUtil.isEmpty(codice) && codice.length() >= 6)
	 * { // codiceIstat = codice // .substring(codice.length() - 6,
	 * codice.length()); // } // return codiceIstat; // }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private SedeInterventoDTO[] allineaSedi(List<ComuneVO> comuniVO) { return
	 * beanUtil .transform( comuniVO, SedeInterventoDTO.class, beanUtil.new
	 * TransformationSequence<ComuneVO, SedeInterventoDTO>( beanUtil.new
	 * MapTransformation<ComuneVO, SedeInterventoDTO>( MAP_SEDE_VO_TO_DTO), new
	 * BeanUtil.Transformation<ComuneVO, SedeInterventoDTO>() {
	 * 
	 * @SuppressWarnings("static-access") public void transform(ComuneVO objSrc,
	 * SedeInterventoDTO objDest) throws Exception {
	 * caricaDescrizioniPerComune(objSrc, objDest.getComuneSede());
	 * 
	 * objDest.getComuneSede() .setFlagNazioneItaliana(
	 * objSrc.getFlagNazioneItaliana()); objDest.getComuneSede().setIdComune(
	 * beanUtil.transform( objSrc.getIdComune(), String.class));
	 * beanUtil.setPropertyValueByName( objDest, "comuneSede.descComune",
	 * objSrc.getDescComune()); } })); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private SchedaProgettoDTO caricaDefault(BigDecimal
	 * progrBandoLineaIntervento,BigDecimal idProcesso) throws
	 * UnrecoverableException { SchedaProgettoDTO schedaProgettoDTO = beanUtil
	 * .createEmptyInstance(SchedaProgettoDTO.class);
	 * 
	 * PbandiDLineaDiInterventoVO lineaDiInterventoNormativaVO = progettoManager
	 * .getLineaDiInterventoNormativa(progrBandoLineaIntervento);
	 * 
	 * schedaProgettoDTO.setIdLineaNormativa(beanUtil.transform(
	 * lineaDiInterventoNormativaVO.getIdLineaDiIntervento(), String.class));
	 * 
	 * 
	 * schedaProgettoDTO.getInformazioniBase()
	 * .setFlagProgettoDaInviareAlMonitoraggio(FLAG_TRUE);
	 * 
	 * PbandiDLineaDiInterventoVO lineaDiInterventoAsseVO = progettoManager
	 * .getLineaDiInterventoAsse(progrBandoLineaIntervento);
	 * 
	 * // BigDecimal idProcesso = lineaDiInterventoAsseVO.getIdProcesso();
	 * 
	 * schedaProgettoDTO .setIdLineaAsse(beanUtil.transform(
	 * lineaDiInterventoAsseVO.getIdLineaDiIntervento(), String.class));
	 * 
	 * schedaProgettoDTO.getInformazioniBase().setIdStrumentoAttuativo(
	 * beanUtil.transform( lineaDiInterventoNormativaVO.getIdStrumentoAttuativo(),
	 * String.class));
	 * 
	 * PbandiTBandoVO bandoVO = new PbandiTBandoVO(); PbandiRBandoLineaInterventVO
	 * bandoLineaInterventVO = new PbandiRBandoLineaInterventVO();
	 * bandoLineaInterventVO
	 * .setProgrBandoLineaIntervento(progrBandoLineaIntervento);
	 * bandoVO.setIdBando(genericDAO.findSingleWhere(bandoLineaInterventVO)
	 * .getIdBando()); bandoVO = genericDAO.findSingleWhere(bandoVO);
	 * 
	 * schedaProgettoDTO.getInformazioniBase().setIdSettoreCpt(
	 * beanUtil.transform(bandoVO.getIdSettoreCpt(), String.class));
	 * 
	 * schedaProgettoDTO.getInformazioniBase().setIdSottoSettoreCipe(
	 * beanUtil.transform(bandoVO.getIdSottoSettoreCipe(), String.class));
	 * 
	 * schedaProgettoDTO.getInformazioniBase().setIdNaturaCipe(
	 * beanUtil.transform(bandoVO.getIdNaturaCipe(), String.class));
	 * 
	 * schedaProgettoDTO.getInformazioniBase() .setIdTipoStrumentoProgrammazione(
	 * beanUtil.transform(lineaDiInterventoNormativaVO .getIdTipoStrumentoProgr(),
	 * String.class));
	 * 
	 * schedaProgettoDTO.getInformazioniBase().setFlagCardine(FLAG_FALSE);
	 * schedaProgettoDTO.getInformazioniBase().setFlagGeneratoreEntrate(
	 * FLAG_FALSE); schedaProgettoDTO.getInformazioniBase().setFlagLeggeObiettivo(
	 * FLAG_FALSE);
	 * schedaProgettoDTO.getInformazioniBase().setFlagAltroFondo(FLAG_FALSE);
	 * schedaProgettoDTO.getInformazioniBase().setFlagStatoProgettoProgramma(
	 * FLAG_TRUE);
	 * schedaProgettoDTO.getInformazioniBase().setFlagRichiestaAutomaticaDelCup(
	 * FLAG_FALSE);
	 * 
	 * schedaProgettoDTO.getInformazioniBase() .setIdTipoOperazione(
	 * beanUtil.transform(bandoVO.getIdTipoOperazione(), String.class));
	 * 
	 * caricaSedeInterventoDefault(schedaProgettoDTO .getSedeInterventoDefault());
	 * 
	 * caricaBeneficiarioDefault(schedaProgettoDTO);
	 * 
	 * schedaProgettoDTO.setRappresentanteLegale(beanUtil
	 * .clone(schedaProgettoDTO.getBeneficiario()));
	 * 
	 * schedaProgettoDTO.getRappresentanteLegale().setFlagPersonaFisica( FLAG_TRUE);
	 * 
	 * schedaProgettoDTO.setFlagSalvaIntermediario(FLAG_FALSE);
	 * schedaProgettoDTO.setIntermediario(beanUtil.clone(schedaProgettoDTO
	 * .getBeneficiario()));
	 * 
	 * schedaProgettoDTO.setAltroSoggettoDefault(beanUtil
	 * .clone(schedaProgettoDTO.getBeneficiario()));
	 * 
	 * // nuova programmaz 2014_20 if(idProcesso!=null &&
	 * idProcesso.longValue()==ID_PROCESSO_2014_20){
	 * schedaProgettoDTO.getInformazioniBase().setIdObiettivoTematico("");
	 * schedaProgettoDTO.getInformazioniBase().setIdClassificazioneRA("");
	 * schedaProgettoDTO.getInformazioniBase().setIdGrandeProgetto(""); }
	 * 
	 * return schedaProgettoDTO; }
	 * 
	 * private void caricaDefaultInfoBaseAllineaCup( SchedaProgettoDTO
	 * schedaProgettoDTO) throws UnrecoverableException {
	 * schedaProgettoDTO.getInformazioniBase().setIdSettoreAttivita(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdAttivitaAteco(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdTipoOperazione(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdTipoStrumentoProgrammazione(null
	 * ); schedaProgettoDTO.getInformazioniBase().setIdSettoreCipe(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdSottoSettoreCipe(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdCategoriaCipe(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdNaturaCipe(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdTipologiaCipe(null);
	 * schedaProgettoDTO.getInformazioniBase().setFlagStatoProgettoProgramma(
	 * FLAG_TRUE); // nuova programmaz 2014_20
	 * schedaProgettoDTO.getInformazioniBase().setIdObiettivoTematico(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdClassificazioneRA(null);
	 * schedaProgettoDTO.getInformazioniBase().setIdGrandeProgetto(null); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private boolean isCupValorizzato(SchedaProgettoDTO schedaProgetto) { return
	 * schedaProgetto.getInformazioniBase() != null &&
	 * schedaProgetto.getInformazioniBase().getNumeroDomanda() != null; }
	 * 
	 * private void popolaAttivitaAtecoBeneficiarioDaInformazioniBase(
	 * SchedaProgettoDTO schedaProgetto) { if
	 * ((!StringUtil.isEmpty(schedaProgetto.getInformazioniBase()
	 * .getIdAttivitaAteco())) &&
	 * (FLAG_FALSE.equals(schedaProgetto.getBeneficiario() .getFlagPersonaFisica()))
	 * && (it.csi.pbandi.pbandiutil.common.Constants.TIPO_DIP_DIR_NA
	 * .equals(schedaProgetto.getBeneficiario().getDatiPG() .getTipoDipDir())) &&
	 * (StringUtil.isEmpty(schedaProgetto.getBeneficiario()
	 * .getDatiPG().getAttivitaAteco()))) { schedaProgetto .getBeneficiario()
	 * .getDatiPG() .setSettoreAttivita( schedaProgetto.getInformazioniBase()
	 * .getIdSettoreAttivita()); schedaProgetto .getBeneficiario() .getDatiPG()
	 * .setAttivitaAteco( schedaProgetto.getInformazioniBase()
	 * .getIdAttivitaAteco()); } }
	 * 
	 * private void aggiungiErroreCupCambiato(EsitoSchedaProgettoDTO result) {
	 * ArrayList<String> erroriGlobali = new ArrayList<String>(); for (int i = 0; i
	 * < result.getErroriGlobali().length; i++) {
	 * erroriGlobali.add(result.getErroriGlobali()[i]); }
	 * erroriGlobali.add(ErrorConstants.KEY_ERR_CUP_NON_ALLINEATO);
	 * result.setErroriGlobali(erroriGlobali.toArray(new String[erroriGlobali
	 * .size()])); }
	 * 
	 * private ArrayList<String> settaCampiErrori(EsitoSchedaProgettoDTO result,
	 * CheckRunner checkRunnerControlloCongruitaDati, CheckRunner
	 * checkRunnerControlloSufficientiAlSalvataggio, CheckRunner
	 * checkRunnerControlloDatiPerAvvio) { settaErroriGlobali(result,
	 * checkRunnerControlloCongruitaDati,
	 * checkRunnerControlloSufficientiAlSalvataggio,
	 * checkRunnerControlloDatiPerAvvio);
	 * 
	 * ArrayList<String> chiaviCampi = settaChiaviErrori(result,
	 * checkRunnerControlloCongruitaDati,
	 * checkRunnerControlloSufficientiAlSalvataggio,
	 * checkRunnerControlloDatiPerAvvio); return chiaviCampi; }
	 * 
	 * private void settaErroriGlobali(EsitoSchedaProgettoDTO result, CheckRunner
	 * checkRunnerControlloCongruitaDati, CheckRunner
	 * checkRunnerControlloSufficientiAlSalvataggio, CheckRunner
	 * checkRunnerControlloDatiPerAvvio) { Set<String> set = new HashSet<String>();
	 * set.addAll(checkRunnerControlloCongruitaDati.getMessages());
	 * set.addAll(checkRunnerControlloSufficientiAlSalvataggio.getMessages());
	 * set.addAll(checkRunnerControlloDatiPerAvvio.getMessages()); Map<String,
	 * Set<String>> fieldMessages =
	 * checkRunnerControlloDatiPerAvvio.getFieldMessages(); Set<String> keySet =
	 * fieldMessages.keySet();
	 * logger.info("\n\n\n\n\n\nsettaErroriGlobali messages:"
	 * +checkRunnerControlloDatiPerAvvio.getMessages()); for (String key : keySet) {
	 * logger.info("key: "+key); } result.setErroriGlobali(beanUtil.transform(set,
	 * String[].class)); }
	 * 
	 * private ArrayList<String> settaChiaviErrori(EsitoSchedaProgettoDTO result,
	 * CheckRunner checkRunnerControlloCongruitaDati, CheckRunner
	 * checkRunnerControlloSufficientiAlSalvataggio, CheckRunner
	 * checkRunnerControlloDatiPerAvvio) { ArrayList<String> chiaviCampi = new
	 * ArrayList<String>();
	 * 
	 * ArrayList<String> erroriCampi = new ArrayList<String>();
	 * 
	 * boolean erroreCupTrovato = false;
	 * 
	 * for (CheckRunner check : new CheckRunner[] {
	 * checkRunnerControlloCongruitaDati,
	 * checkRunnerControlloSufficientiAlSalvataggio,
	 * checkRunnerControlloDatiPerAvvio }) { Map<String, Set<String>> fieldMessages
	 * = check.getFieldMessages();
	 * 
	 * 
	 * // Ricerco l' errore sul cup presente if (!erroreCupTrovato &&
	 * !ObjectUtil.isEmpty(check.getMessages())) { if
	 * (check.getMessages().contains(ErrorConstants.KEY_CUP_PRESENTE)) {
	 * chiaviCampi.add("informazioniBase.cup");
	 * erroriCampi.add(ErrorConstants.KEY_CUP_PRESENTE); erroreCupTrovato = true; }
	 * }
	 * 
	 * for (String fieldKey : fieldMessages.keySet()) { for (String messageKey :
	 * fieldMessages.get(fieldKey)) { chiaviCampi.add(fieldKey);
	 * erroriCampi.add(messageKey); } } }
	 * 
	 * result.setChiaviCampi(chiaviCampi.toArray(new String[chiaviCampi.size()]));
	 * result.setErroriCampi(erroriCampi.toArray(new String[erroriCampi.size()]));
	 * logger.info("\n\n\n\n\n\n\n\nsettaChiaviErrori BEGIN"); StringBuilder sb=new
	 * StringBuilder(); for (String chiaveCampo : chiaviCampi) {
	 * sb.append("\nerrore chiaveCampo: "+chiaveCampo); }
	 * 
	 * logger.info(sb.toString()+"\n\nsettaChiaviErrori END\n\n\n\n\n\n\n\n");
	 * return chiaviCampi; }
	 * 
	 * private CheckRunner creaControlliDatiSufficientiAlSalvataggio(
	 * SchedaProgettoDTO schedaProgetto, BigDecimal idProcesso) {
	 * 
	 * logger.shallowDump(schedaProgetto.getInformazioniBase(),"info");
	 * 
	 * CheckRunner checkRunnerControlloSufficientiAlSalvataggio = getChecker()
	 * .getNew();
	 * 
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new
	 * FieldCheckRequired("informazioniBase.titoloProgetto", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * 
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.dataPresentazioneDomanda", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * 
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.idTipoOperazione", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * 
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.idStrumentoAttuativo", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new
	 * FieldCheckRequired("informazioniBase.idSettoreCpt", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.idTipoStrumentoProgrammazione", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new
	 * FieldCheckRequired("informazioniBase.flagCardine", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.flagGeneratoreEntrate", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.flagLeggeObiettivo", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new
	 * FieldCheckRequired("informazioniBase.flagAltroFondo", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.flagStatoProgettoProgramma", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * 
	 * 
	 * if(idProcesso!=null && idProcesso.longValue()==ID_PROCESSO_2014_20){
	 * logger.info(" **************** check idObiettivoTematico : "+schedaProgetto.
	 * getInformazioniBase().getIdObiettivoTematico()+
	 * " , getIdClassificazioneRA : "+schedaProgetto.getInformazioniBase().
	 * getIdClassificazioneRA()+
	 * " , idGrandeProgetto "+schedaProgetto.getInformazioniBase().
	 * getIdGrandeProgetto()); checkRunnerControlloSufficientiAlSalvataggio .add(new
	 * FieldCheckRequired( "informazioniBase.idObiettivoTematico", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO));
	 * checkRunnerControlloSufficientiAlSalvataggio .add(new FieldCheckRequired(
	 * "informazioniBase.idClassificazioneRA", schedaProgetto,
	 * ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO)); //commentato per richiesta di PCL
	 * // checkRunnerControlloSufficientiAlSalvataggio //.add(new
	 * FieldCheckRequired( // "informazioniBase.idGrandeProgetto", //
	 * schedaProgetto, // ErrorConstants.ERRORE_CAMPO_OBBLIGATORIO)); }
	 * 
	 * 
	 * return checkRunnerControlloSufficientiAlSalvataggio; }
	 * 
	 * 
	 * private CheckRunner creaControlliCongruitaDati( final SchedaProgettoDTO
	 * schedaProgetto) { Map<String, Class<? extends GenericVO>> propToVOClass = new
	 * HashMap<String, Class<? extends GenericVO>>(); Map<Class<? extends
	 * GenericVO>, Map<?, ?>> voClassToMap = new HashMap<Class<? extends GenericVO>,
	 * Map<?, ?>>();
	 * 
	 * for (Object prop : MAP_PROGETTO_DTO_TO_VO.keySet()) {
	 * propToVOClass.put((String) prop, PbandiTCspProgettoVO.class); }
	 * voClassToMap.put(PbandiTCspProgettoVO.class, MAP_PROGETTO_DTO_TO_VO);
	 * 
	 * Map mapSogg = new HashMap();
	 * mapSogg.putAll(prefixMapKeyWith("beneficiario.datiPF.", MAP_PF_DTO_TO_VO));
	 * mapSogg.putAll(prefixMapKeyWith("beneficiario.datiPG.", MAP_PG_DTO_TO_VO));
	 * mapSogg.putAll(prefixMapKeyWith("rappresentanteLegale.datiPF.",
	 * MAP_PF_DTO_TO_VO));
	 * mapSogg.putAll(prefixMapKeyWith("rappresentanteLegale.datiPG.",
	 * MAP_PG_DTO_TO_VO)); mapSogg.putAll(prefixMapKeyWith("intermediario.datiPF.",
	 * MAP_PF_DTO_TO_VO)); mapSogg.putAll(prefixMapKeyWith("intermediario.datiPG.",
	 * MAP_PG_DTO_TO_VO));
	 * 
	 * for (Object prop : mapSogg.keySet()) { propToVOClass.put((String) prop,
	 * PbandiTCspSoggettoVO.class); } voClassToMap.put(PbandiTCspSoggettoVO.class,
	 * mapSogg);
	 * 
	 * final CheckRunner checkRunnerControlloCongruitaDati = getChecker() .getNew();
	 * 
	 * final CheckRunner checkRunnerDataCostituzioneAzienda = getChecker()
	 * .getNew(); checkRunnerDataCostituzioneAzienda.add(new FieldCheckDate(
	 * "informazioniBase.dataCostituzioneAzienda", schedaProgetto));
	 * 
	 * final CheckRunner checkRunnerDataPresentazioneDomanda = getChecker()
	 * .getNew(); checkRunnerDataPresentazioneDomanda.add(new FieldCheckDate(
	 * "informazioniBase.dataPresentazioneDomanda", schedaProgetto));
	 * 
	 * final CheckRunner checkRunnerDataComitato = getChecker().getNew();
	 * checkRunnerDataComitato.add(new FieldCheckDate(
	 * "informazioniBase.dataComitato", schedaProgetto));
	 * 
	 * final CheckRunner checkRunnerDataConcessione = getChecker().getNew();
	 * checkRunnerDataConcessione.add(new FieldCheckDate(
	 * "informazioniBase.dataConcessione", schedaProgetto));
	 * 
	 * for (String propName : beanUtil.enumerateProperties(schedaProgetto)) { if
	 * (propName.endsWith(FLAG_PERSONA_FISICA)) { String propSoggetto =
	 * propName.substring(0, propName.indexOf(FLAG_PERSONA_FISICA)); try { if
	 * (beanUtil.getPropertyValueByName(schedaProgetto, propName, Boolean.class)) {
	 * checkRunnerControlloCongruitaDati .add(new
	 * FieldCheckCodiceFiscalePersonaFisica( propSoggetto + "datiPF.codiceFiscale",
	 * schedaProgetto)); checkRunnerControlloCongruitaDati .add(new
	 * FieldCheckEmail(propSoggetto + "datiPF.emailRes", schedaProgetto));
	 * checkRunnerControlloCongruitaDati .add(new FieldCheckCap(propSoggetto +
	 * "datiPF.capRes", schedaProgetto)); checkRunnerControlloCongruitaDati .add(new
	 * FieldCheckPhoneNumber(propSoggetto + "datiPF.telefonoRes", schedaProgetto));
	 * checkRunnerControlloCongruitaDati .add(new FieldCheckFaxNumber(propSoggetto +
	 * "datiPF.faxRes", schedaProgetto)); } else { checkRunnerControlloCongruitaDati
	 * .add(new FieldCheckCodiceFiscalePersonaGiuridica( propSoggetto +
	 * "datiPG.codiceFiscale", schedaProgetto)); checkRunnerControlloCongruitaDati
	 * .add(new FieldCheckCodiceFiscalePersonaGiuridica( propSoggetto +
	 * "datiPG.partitaIvaSedeLegale", schedaProgetto));
	 * checkRunnerControlloCongruitaDati .add(new FieldCheckEmail(propSoggetto +
	 * "datiPG.emailSedeLegale", schedaProgetto)); checkRunnerControlloCongruitaDati
	 * .add(new FieldCheckCap(propSoggetto + "datiPG.capSedeLegale",
	 * schedaProgetto)); checkRunnerControlloCongruitaDati .add(new
	 * FieldCheckPhoneNumber(propSoggetto + "datiPG.telefonoSedeLegale",
	 * schedaProgetto)); checkRunnerControlloCongruitaDati .add(new
	 * FieldCheckFaxNumber(propSoggetto + "datiPG.faxSedeLegale", schedaProgetto));
	 * 
	 * checkRunnerControlloCongruitaDati .add(new Checker.FieldCheck() { private
	 * boolean failed; private final String fieldId =
	 * "beneficiario.datiPG.dataCostituzioneAzienda";
	 * 
	 * public void run() { failed = true;
	 * 
	 * boolean isDataCostituzioneAziendaValorizzata = (!StringUtil.isEmpty(beanUtil
	 * .getPropertyValueByName( schedaProgetto,
	 * "beneficiario.datiPG.dataCostituzioneAzienda", String.class))) &&
	 * (!StringUtil.isEmpty(beanUtil .getPropertyValueByName( schedaProgetto,
	 * "beneficiario.datiPG.tipoDipDir", String.class))) &&
	 * it.csi.pbandi.pbandiutil.common.Constants.TIPO_DIP_DIR_NA.equals(beanUtil
	 * .getPropertyValueByName( schedaProgetto, "beneficiario.datiPG.tipoDipDir",
	 * String.class));
	 * 
	 * boolean isDataConcessioneValorizzata = !StringUtil.isEmpty(beanUtil
	 * .getPropertyValueByName( schedaProgetto, "informazioniBase.dataConcessione",
	 * String.class));
	 * 
	 * boolean isDataComitatoValorizzata = !StringUtil.isEmpty(beanUtil
	 * .getPropertyValueByName( schedaProgetto, "informazioniBase.dataComitato",
	 * String.class));
	 * 
	 * try { failed = true; if ((!isDataCostituzioneAziendaValorizzata)) { failed =
	 * false; } else { Date dataCostituzioneAzienda = null; if
	 * (!checkRunnerDataCostituzioneAzienda .runAll()) { failed = false; // blocca
	 * il // check sul // controllo // data } else { dataCostituzioneAzienda =
	 * beanUtil .transform( schedaProgetto .getBeneficiario() .getDatiPG()
	 * .getDataCostituzioneAzienda(), Date.class); if (isDataComitatoValorizzata) {
	 * if (checkRunnerDataComitato .runAll()) { Date dataComitato = beanUtil
	 * .transform( schedaProgetto .getInformazioniBase() .getDataComitato(),
	 * Date.class); failed = !DateUtil .before(dataCostituzioneAzienda,
	 * dataComitato); } else { failed = false; // blocca // il // check // sul //
	 * controllo // data } } else { failed = false; } if (!failed) { if
	 * (isDataConcessioneValorizzata) { if (checkRunnerDataConcessione .runAll()) {
	 * Date dataConcessione = beanUtil .transform( schedaProgetto
	 * .getInformazioniBase() .getDataConcessione(), Date.class); failed = !DateUtil
	 * .before(dataCostituzioneAzienda, dataConcessione); } else { failed = false; }
	 * } else { failed = false; } } } } } catch (Exception e) {
	 * logger.warn("Controlli dataCostituzioneAzienda falliti: " + e.getMessage());
	 * }
	 * 
	 * }
	 * 
	 * public boolean isFailed() { return failed; }
	 * 
	 * public String getMessageCode() { return
	 * ErrorConstants.ERRORE_DATA_ANTECEDENTE_DATA_COMITATO_E_CONCESSIONE; }
	 * 
	 * public String getFieldId() { return fieldId; } });
	 * 
	 * } } catch (Throwable t) { logger.warn("Controlli soggetti falliti: " +
	 * t.getMessage()); } }
	 * 
	 * if (propName.contains(".data")) { checkRunnerControlloCongruitaDati .add(new
	 * FieldCheckNonFutureDate(propName, schedaProgetto));
	 * checkRunnerControlloCongruitaDati.add(new FieldCheckDate( propName,
	 * schedaProgetto)); }
	 * 
	 * Class<? extends GenericVO> voClass = propToVOClass.get(propName); if (voClass
	 * != null) { Map<?, ?> map = voClassToMap.get(voClass); if (map != null) {
	 * String voFieldName = (String) map.get(propName); if (voFieldName != null &&
	 * (!propName.contains(".data"))) { checkRunnerControlloCongruitaDati .add(new
	 * FieldCheckDbFormat(voClass, propName, schedaProgetto, voFieldName)); } } } }
	 * String numeroDomandaToCheck = StringUtil.isEmpty(schedaProgetto
	 * .getInformazioniBase().getNumeroDomanda()) ? schedaProgetto
	 * .getInformazioniBase().getNumeroDomanda() : schedaProgetto
	 * .getInformazioniBase().getNumeroDomanda().toUpperCase().trim();
	 * checkRunnerControlloCongruitaDati.add(new CheckUnivocitaNumeroDomanda(
	 * beanUtil.transform(schedaProgetto.getIdBandoLinea(), BigDecimal.class),
	 * numeroDomandaToCheck, beanUtil .transform(schedaProgetto.getIdProgetto(),
	 * BigDecimal.class))); String cupToCheck =
	 * checkUnivocitaCup(schedaProgetto,checkRunnerControlloCongruitaDati);
	 * 
	 * SchedaProgettoDTO schedaProgettoPerCup = new SchedaProgettoDTO();
	 * schedaProgettoPerCup.setInformazioniBase(new InformazioniBaseDTO());
	 * schedaProgettoPerCup.getInformazioniBase().setCup(cupToCheck);
	 * 
	 * checkRunnerControlloCongruitaDati.add(new FieldCheckLenght(
	 * "informazioniBase.cup", schedaProgettoPerCup,
	 * Constants.CUP_SCHEDA_PROGETTO_LUNGHEZZA_CAMPO.intValue(),
	 * Constants.CUP_SCHEDA_PROGETTO_LUNGHEZZA_CAMPO.intValue(),
	 * ErrorConstants.DIMENSIONE_CAMPO_CUP_ERRATA));
	 * 
	 * 
	 * checkRunnerControlloCongruitaDati.add(new Checker.FieldCheck() { private
	 * boolean failed; private final String fieldId =
	 * "informazioniBase.dataPresentazioneDomanda";
	 * 
	 * public void run() { failed = true;
	 * 
	 * boolean isDataPresentazioneDomandaValorizzata = !StringUtil
	 * .isEmpty(beanUtil.getPropertyValueByName( schedaProgetto,
	 * "informazioniBase.dataPresentazioneDomanda", String.class));
	 * 
	 * boolean isDataConcessioneValorizzata = !StringUtil
	 * .isEmpty(beanUtil.getPropertyValueByName( schedaProgetto,
	 * "informazioniBase.dataConcessione", String.class));
	 * 
	 * boolean isDataComitatoValorizzata = !StringUtil
	 * .isEmpty(beanUtil.getPropertyValueByName( schedaProgetto,
	 * "informazioniBase.dataComitato", String.class));
	 * 
	 * try { failed = true; if ((!isDataPresentazioneDomandaValorizzata)) { failed =
	 * false; } else { Date dataPresentazioneDomanda = null; if
	 * (!checkRunnerDataPresentazioneDomanda.runAll()) { failed = false; // blocca
	 * il check sul controllo // data } else { dataPresentazioneDomanda =
	 * beanUtil.transform( schedaProgetto.getInformazioniBase()
	 * .getDataPresentazioneDomanda(), Date.class); if (isDataComitatoValorizzata) {
	 * if (checkRunnerDataComitato.runAll()) { Date dataComitato =
	 * beanUtil.transform( schedaProgetto .getInformazioniBase() .getDataComitato(),
	 * Date.class); failed = !DateUtil.before( dataPresentazioneDomanda,
	 * dataComitato); } else { failed = false; // blocca il check sul // controllo
	 * data } } else { failed = false; } if (!failed) { if
	 * (isDataConcessioneValorizzata) { if (checkRunnerDataConcessione.runAll()) {
	 * Date dataConcessione = beanUtil .transform(schedaProgetto
	 * .getInformazioniBase() .getDataConcessione(), Date.class); failed =
	 * !DateUtil.before( dataPresentazioneDomanda, dataConcessione); } else { failed
	 * = false; // blocca il check sul // controllo data } } else { failed = false;
	 * } } } } } catch (Exception e) {
	 * logger.warn("Controlli dataPresentazioneDomanda falliti: " + e.getMessage());
	 * }
	 * 
	 * }
	 * 
	 * public boolean isFailed() { return failed; }
	 * 
	 * public String getMessageCode() { return
	 * ErrorConstants.ERRORE_DATA_ANTECEDENTE_DATA_COMITATO_E_CONCESSIONE; }
	 * 
	 * public String getFieldId() { return fieldId; } });
	 * 
	 * return checkRunnerControlloCongruitaDati;
	 * 
	 * }
	 * 
	 * private String checkUnivocitaCup(final SchedaProgettoDTO schedaProgetto,
	 * final CheckRunner checkRunnerControlloCongruitaDati) { String cupToCheck =
	 * StringUtil.isEmpty(schedaProgetto .getInformazioniBase().getCup()) ?
	 * schedaProgetto .getInformazioniBase().getCup() : schedaProgetto
	 * .getInformazioniBase().getCup().toUpperCase().trim();
	 * 
	 * checkRunnerControlloCongruitaDati.add(new CheckUnivocitaCUP(cupToCheck,
	 * beanUtil.transform(schedaProgetto.getIdProgetto(), BigDecimal.class)));
	 * return cupToCheck; }
	 * 
	 * private <T> Map<String, T> prefixMapKeyWith(final String keyPrefix, final
	 * Map<String, T> map) { Map<String, T> result = new HashMap<String, T>(); for
	 * (String key : map.keySet()) { result.put(keyPrefix + key, map.get(key)); }
	 * return result; }
	 * 
	 * private CheckRunner creaControlliDatiPerAvvio(final SchedaProgettoDTO
	 * schedaProgetto,BigDecimal idProcesso) { CheckRunner
	 * checkRunnerControlloDatiPerAvvio = getChecker().getNew();
	 * 
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idDimensioneTerritoriale", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idIndicatoreRisultatoProgramma", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idIndicatoreQsn", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new CheckNotEmpty(schedaProgetto
	 * .getSediIntervento(), ErrorConstants.NESSUNA_SEDE_INTERVENTO_PRESENTE));
	 * 
	 * 
	 * // FIX PBandi-1766 checkRunnerControlloDatiPerAvvio.add(new
	 * FieldCheckRequired( "informazioniBase.idCategoriaCipe", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idTipologiaCipe", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * addCheckSoggetto(schedaProgetto,
	 * BENEFICIARIO,checkRunnerControlloDatiPerAvvio);
	 * 
	 * addCheckSoggetto(schedaProgetto,
	 * RAPPRESENTANTE_LEGALE,checkRunnerControlloDatiPerAvvio);
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "rappresentanteLegale.datiPF.indirizzoRes", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "rappresentanteLegale.datiPF.comuneRes.idComune",schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * 
	 * if (hasIntermediario(schedaProgetto)) { addCheckSoggetto(schedaProgetto,
	 * "intermediario", checkRunnerControlloDatiPerAvvio); } String
	 * descRuoloAtt=Constants.RUOLO_ENTE_COMPETENZA_ATTUATORE; if(idProcesso!=null
	 * && idProcesso.longValue()==ID_PROCESSO_2014_20){
	 * descRuoloAtt=Constants.RUOLO_ENTE_COMPETENZA_ATTUATORENEW; } final String
	 * descRuoloAttuatore=descRuoloAtt;
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new Checker.Check() { private boolean
	 * failed;
	 * 
	 * public void run() { failed = true;
	 * logger.info("descRuoloAttuatore :"+descRuoloAttuatore); Set<String>
	 * ruoliDaControllare = new HashSet<String>();
	 * 
	 * ruoliDaControllare.addAll(getRuoli(schedaProgetto.getBeneficiario())); for
	 * (String r : ruoliDaControllare) {
	 * logger.info("ruoloDaControllare del beneficiario : "+r); }
	 * SoggettoGenericoDTO[] altriSoggetti = schedaProgetto.getAltriSoggetti(); if
	 * (altriSoggetti != null) { for (SoggettoGenericoDTO soggettoGenericoDTO :
	 * altriSoggetti) { ruoliDaControllare .addAll(getRuoli(soggettoGenericoDTO)); }
	 * }
	 * 
	 * 
	 * String idRuoloAttuatore = beanUtil.transform(
	 * decodificheManager.decodeDescBreve( PbandiDRuoloEnteCompetenzaVO.class,
	 * descRuoloAttuatore), String.class);
	 * logger.info("descRuoloAttuatore "+descRuoloAttuatore+" , idRuoloAttuatore : "
	 * +idRuoloAttuatore);
	 * 
	 * failed = !ruoliDaControllare.contains(idRuoloAttuatore);
	 * logger.info("ruoliDaControllare failed : "+failed); }
	 * 
	 * public boolean isFailed() { return failed; }
	 * 
	 * public String getMessageCode() { return
	 * ErrorConstants.RUOLO_ATTUATORE_NON_PRESENTE; } });
	 * 
	 * 
	 * if(idProcesso!=null && idProcesso.longValue()!=ID_PROCESSO_2014_20){
	 * 
	 * // FIX PBandi-2141 - Aggiunti i controlli di obbligatorieta'
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idPrioritaQsn", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idObiettivoGeneraleQsn", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idObiettivoSpecificoQsn", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * }
	 * 
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idSettoreCipe", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idSottoSettoreCipe", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * checkRunnerControlloDatiPerAvvio.add(new FieldCheckRequired(
	 * "informazioniBase.idNaturaCipe", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * checkRunnerControlloDatiPerAvvio .add(new FieldCheckRequired(
	 * "informazioniBase.dataConcessione", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * checkRunnerControlloDatiPerAvvio .add(new FieldCheckRequired(
	 * "informazioniBase.idSettoreAttivita", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * checkRunnerControlloDatiPerAvvio .add(new FieldCheckRequired(
	 * "informazioniBase.idAttivitaAteco", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * checkRunnerControlloDatiPerAvvio .add(new FieldCheckRequired(
	 * "informazioniBase.idTemaPrioritario", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * checkRunnerControlloDatiPerAvvio .add(new FieldCheckRequired(
	 * "informazioniBase.idTipoAiuto", schedaProgetto,
	 * ErrorConstants.CAMPO_OBBLIGATORIO_PER_AVVIO));
	 * 
	 * return checkRunnerControlloDatiPerAvvio; }
	 * 
	 * private Set<String> getRuoli(SoggettoGenericoDTO s) { Set<String> result =
	 * new HashSet<String>(); String p = null; if
	 * (beanUtil.getPropertyValueByName(s, FLAG_PERSONA_FISICA, Boolean.class)) { p
	 * = "datiPF"; } else { p = "datiPG"; } String[] strings =
	 * beanUtil.getPropertyValueByName(s, p + ".ruolo", String[].class); if (strings
	 * != null && strings.length > 0) { for (String string : strings) {
	 * result.add(string); } }
	 * 
	 * return result; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public void setDettaglioCupManager(DettaglioCupManager dettaglioCupManager) {
	 * this.dettaglioCupManager = dettaglioCupManager; }
	 * 
	 * public DettaglioCupManager getDettaglioCupManager() { return
	 * dettaglioCupManager; }
	 * 
	 * public void prenotaAvvioProgetti(Long idUtente, String identitaIride, Long[]
	 * idProgetti) throws CSIException, SystemException, UnrecoverableException,
	 * GestioneAvvioProgettoException { List<EsitoAvvioProcessoDTO> result = new
	 * ArrayList<EsitoAvvioProcessoDTO>();
	 * 
	 * String[] nameParameter = { "idUtente", "identitaIride","idProgetti" };
	 * ValidatorInput.verifyNullValue(nameParameter,idUtente, identitaIride ,
	 * idProgetti);
	 * 
	 * PbandiTProgettoVO vo= new PbandiTProgettoVO(); try{
	 * logger.info("Prenoto "+idProgetti.length+ " progetti per l'avvio "); for
	 * (Long idProgetto : idProgetti) { vo.setIdProgetto(
	 * BigDecimal.valueOf(idProgetto));
	 * vo.setFlagPrenotAvvio(BigDecimal.valueOf(1));
	 * vo.setIdUtenteAgg(BigDecimal.valueOf(idUtente)); genericDAO.update(vo); }
	 * }catch(Exception ex){
	 * logger.error("Errore nel prenotaAvvioProgetti"+ex.getMessage(), ex); throw
	 * new GestioneAvvioProgettoException(ex.getMessage()); } }
	 * 
	 * // Dato un codice fiscale, recupara i beneficiari che verranno visualizzati
	 * nella popup di selezione // in Scheda Progeto - Beneficiario - Persona
	 * Giuridica. public BeneficiarioCspDTO[] ricercaBeneficiarioCsp(Long idUtente,
	 * String identitaIride, String codiceFiscale) throws CSIException,
	 * SystemException, UnrecoverableException, GestioneAvvioProgettoException {
	 * 
	 * String[] nameParameter = { "idUtente", "identitaIride", "codiceFiscale" };
	 * ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride,
	 * codiceFiscale);
	 * 
	 * BeneficiarioCspDTO[] result = new BeneficiarioCspDTO[0]; try {
	 * 
	 * //Legge da db i beneficiari. BeneficiarioCspVO vo = new BeneficiarioCspVO();
	 * vo.setCodiceFiscaleSoggetto(codiceFiscale); List<BeneficiarioCspVO> listaVO =
	 * genericDAO.findListWhere(vo); if (listaVO == null) throw new
	 * Exception("ricercaBeneficiarioCsp(): lista beneficiari nulla.");
	 * logger.info("ricercaBeneficiarioCsp(): num record letti da db: "+listaVO.size
	 * ());
	 * 
	 * if (listaVO.size() == 0) return new BeneficiarioCspDTO[0];
	 * 
	 * // Completa eventuali dati mancanti della sede legale.
	 * List<BeneficiarioCspVO> listaVOcompletata = new
	 * ArrayList<BeneficiarioCspVO>(); for (BeneficiarioCspVO b : listaVO) {
	 * BeneficiarioCspVO b1 = this.completaIndirizzo(b); listaVOcompletata.add(b1);
	 * logger.info("ricercaBeneficiarioCsp(): BeneficiarioCspVO = "+b1.toString());
	 * }
	 * 
	 * // Compatta i record (1 beneficiario possono essere associato pi� sedi
	 * legali), // passando da BeneficiarioCspVO a BeneficiarioCspDTO.
	 * logger.info("inizio compattamento"); List<BeneficiarioCspDTO> listaDTO = new
	 * ArrayList<BeneficiarioCspDTO>(); BeneficiarioCspVO voCorrente = new
	 * BeneficiarioCspVO(); BeneficiarioCspDTO dtoCorrente = new
	 * BeneficiarioCspDTO(); List<SedeLegaleBeneficiarioCspDTO> listaSediCorrente =
	 * new ArrayList<SedeLegaleBeneficiarioCspDTO>(); for (BeneficiarioCspVO b :
	 * listaVOcompletata) {
	 * 
	 * logger.info("Elaboro record "+b.getDenominazioneEnteGiuridico()); // Gestisce
	 * il passaggio da un beneficiario all'altro. if (!uguali(b, dtoCorrente)) {
	 * logger.info("Diverso"); // Salva in listaDTO l'oggetto DTO (1 beneficiario
	 * con pi� sedi legali) popolato finora. if
	 * (!StringUtil.isEmpty(dtoCorrente.getDenominazioneEnteGiuridico())) {
	 * logger.info("Aggancio: num sedi = "+listaSediCorrente.size());
	 * SedeLegaleBeneficiarioCspDTO[] a = new SedeLegaleBeneficiarioCspDTO[1]; a =
	 * listaSediCorrente.toArray(a); dtoCorrente.setSediLegali(a);
	 * listaDTO.add(dtoCorrente); listaSediCorrente = new
	 * ArrayList<SedeLegaleBeneficiarioCspDTO>();
	 * logger.info("dim1 = "+listaSediCorrente.size()+"; dim2 = "+a.length); } //
	 * Creo un nuovo oggetto DTO. logger.info("Creo un nuovo oggetto DTO");
	 * dtoCorrente = new BeneficiarioCspDTO();
	 * dtoCorrente.setCodiceFiscaleSoggetto(b.getCodiceFiscaleSoggetto());
	 * dtoCorrente.setDenominazioneEnteGiuridico(b.getDenominazioneEnteGiuridico());
	 * dtoCorrente.setIban(b.getIban());
	 * dtoCorrente.setDtCostituzione(b.getDtCostituzione()); if
	 * (b.getDtCostituzione() != null)
	 * dtoCorrente.setDtCostituzioneStringa(DateUtil.getDate(b.getDtCostituzione()))
	 * ; dtoCorrente.setIdFormaGiuridica(b.getIdFormaGiuridica());
	 * dtoCorrente.setDescFormaGiuridica(b.getDescFormaGiuridica());
	 * dtoCorrente.setIdSettoreAttivita(b.getIdSettoreAttivita());
	 * dtoCorrente.setDescSettore(b.getDescSettore());
	 * dtoCorrente.setIdAttivitaAteco(b.getIdAttivitaAteco());
	 * dtoCorrente.setDescAteco(b.getDescAteco()); }
	 * 
	 * // Aggiunge la sede legale all'oggetto DTO corrente.
	 * logger.info("Aggiunge la sede legale "+b.getPartitaIva());
	 * SedeLegaleBeneficiarioCspDTO sede = new SedeLegaleBeneficiarioCspDTO();
	 * sede.setPartitaIva(b.getPartitaIva());
	 * sede.setDescIndirizzo(b.getDescIndirizzo()); sede.setCap(b.getCap());
	 * sede.setIdNazione(b.getIdNazione()); sede.setDescNazione(b.getDescNazione());
	 * sede.setIdRegione(b.getIdRegione()); sede.setDescRegione(b.getDescRegione());
	 * sede.setIdProvincia(b.getIdProvincia());
	 * sede.setDescProvincia(b.getDescProvincia());
	 * sede.setIdComune(b.getIdComune()); sede.setDescComune(b.getDescComune());
	 * sede.setIdComuneEstero(b.getIdComuneEstero());
	 * sede.setDescComuneEstero(b.getDescComuneEstero());
	 * sede.setEmail(b.getEmail()); sede.setTelefono(b.getTelefono());
	 * sede.setFax(b.getFax()); listaSediCorrente.add(sede); }
	 * logger.info("Fine ciclo"); if
	 * (!StringUtil.isEmpty(dtoCorrente.getDenominazioneEnteGiuridico())) {
	 * logger.info("Aggancio finale: num sedi = "+listaSediCorrente.size());
	 * SedeLegaleBeneficiarioCspDTO[] a = new SedeLegaleBeneficiarioCspDTO[1]; a =
	 * listaSediCorrente.toArray(a); dtoCorrente.setSediLegali(a);
	 * listaDTO.add(dtoCorrente); }
	 * 
	 * // Trasforma l'elenco finale da ArrayList a []. result =
	 * listaDTO.toArray(result);
	 * logger.info("ricercaBeneficiarioCsp(): num beneficiari trasformati: "+result.
	 * length); for (BeneficiarioCspDTO b : result) {
	 * logger.info("ricercaBeneficiarioCsp(): BeneficiarioCspDTO = "+b.toString());
	 * }
	 * 
	 * } catch (Exception e) { String message =
	 * "Impossibile caricare la lista dei Beneficiari: " + e.getMessage();
	 * logger.error(message, e); throw new UnrecoverableException(message, e); }
	 * 
	 * return result; }
	 * 
	 * private BeneficiarioCspVO completaIndirizzo(BeneficiarioCspVO b) { //
	 * Completo eventuali dati mancanti dell'indirizzo in input. if (b.getIdComune()
	 * != null && b.getIdProvincia() == null) { PbandiDComuneVO comune = new
	 * PbandiDComuneVO(); comune.setIdComune(new BigDecimal(b.getIdComune()));
	 * comune = genericDAO.findSingleWhere(comune); if (comune.getIdProvincia() !=
	 * null) { PbandiDProvinciaVO prov = new PbandiDProvinciaVO();
	 * prov.setIdProvincia(comune.getIdProvincia()); prov =
	 * genericDAO.findSingleWhere(prov);
	 * b.setIdProvincia(prov.getIdProvincia().longValue());
	 * b.setDescProvincia(prov.getDescProvincia());
	 * //logger.info("Aggiungo provincia "+b.getIdProvincia()+" "+b.getDescProvincia
	 * ()); } } if (b.getIdProvincia() != null && b.getIdRegione() == null) {
	 * PbandiDProvinciaVO prov = new PbandiDProvinciaVO(); prov.setIdProvincia(new
	 * BigDecimal(b.getIdProvincia())); prov = genericDAO.findSingleWhere(prov); if
	 * (prov.getIdRegione() != null) { PbandiDRegioneVO reg = new
	 * PbandiDRegioneVO(); reg.setIdRegione(prov.getIdRegione()); reg =
	 * genericDAO.findSingleWhere(reg);
	 * b.setIdRegione(reg.getIdRegione().longValue());
	 * b.setDescRegione(reg.getDescRegione());
	 * //logger.info("Aggiungo regione "+b.getIdRegione()+" "+b.getDescRegione()); }
	 * } if (b.getIdRegione() != null && b.getIdNazione() == null) {
	 * b.setIdNazione(new Long(118)); b.setDescNazione("ITALIA");
	 * //logger.info("Aggiungo nazione "+b.getIdNazione()+" "+b.getDescNazione()); }
	 * if (b.getIdComuneEstero() != null && b.getIdNazione() == null) {
	 * PbandiDComuneEsteroVO comEstero = new PbandiDComuneEsteroVO();
	 * comEstero.setIdComuneEstero(new BigDecimal(b.getIdComuneEstero())); comEstero
	 * = genericDAO.findSingleWhere(comEstero); if (comEstero.getIdNazione() !=
	 * null) { PbandiDNazioneVO naz = new PbandiDNazioneVO();
	 * naz.setIdNazione(comEstero.getIdNazione()); naz =
	 * genericDAO.findSingleWhere(naz);
	 * b.setIdNazione(naz.getIdNazione().longValue());
	 * b.setDescNazione(naz.getDescNazione());
	 * //logger.info("Aggiungo nazione estera "+b.getIdNazione()+" "+b.
	 * getDescNazione()); } }
	 * 
	 * return b; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public Boolean isBandoFinpiemonte(Long idUtente, String identitaIride, Long
	 * progrBandoLinea) throws CSIException, SystemException,
	 * UnrecoverableException, GestioneAvvioProgettoException {
	 * 
	 * if (progrBandoLinea == null) return false;
	 * 
	 * TmpNumDomandaGefoVO vo = new TmpNumDomandaGefoVO();
	 * vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea)); int count =
	 * genericDAO.count(new FilterCondition<TmpNumDomandaGefoVO>(vo));
	 * 
	 * if(count > 0) return true; return false; }
	 * 
	 * 
	 */
}