/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AttivitaAtecoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaDaCuiDuplicareModelliVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTemaPrioritarioAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTipoPeriodoAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ClassificazioneRaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DeliberaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PeriodoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PrioritaQsnLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoSoggettiPrivatiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.UtentePersonaFisicaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.ModelloBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCSchemaFattElettVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCategAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCategoriaCipeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDClassRevocaIrregVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDClassificazioneMetVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDimensioneTerritorVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDGrandeProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIndRisultatoProgramVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIndicatoreQsnVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIntesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDLivelloIstituzioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDModalitaAgevolazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDModalitaPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNaturaCipeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNormativaAffidamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoGenQsnVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoSpecifQsnVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoTemVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDPrioritaQsnVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDProgettoComplessoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDProvinciaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDRuoloEnteCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreAttivitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreCipeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreCptVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStrumentoAttuativoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTemaPrioritarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAffidamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAiutoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDocumentoSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoIndicatoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoLocalizzazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoOperazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoProceduraOrigVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoStrumentoProgrVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipolVoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaAggiudicazVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaAttivazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaCipeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologieVariantiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiEntrataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProcessoVO;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;

public class DecodificheManager {
	private static final String DB_FIELD_TRUE_VALUE = "S";
	private static final String FLAG_SELEZIONABILE = "flagSelezionabile";
	private static final String ID_PROPERTY_NAME = "id";
	private static final String DESCRIZIONE_PROPERTY_NAME = "descrizione";
	private static final String DESCRIZIONE_BREVE_PROPERTY_NAME = "descrizioneBreve";
	private static final long CACHE_TIMEOUT = 300000;// 600 sec,10 min
	private static final Map<Class<?>, Map<String, String>> ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO = new HashMap<Class<?>, Map<String, String>>();
	private static final Map<Class<?>, List<String>> ORDER_BY_SPECIFICI_PER_VO = new HashMap<Class<?>, List<String>>();
	static {
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDComuneVO.class,
				new HashMap<String, String>() {
					{
						put("cap", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDProvinciaVO.class,
				new HashMap<String, String>() {
					{
						put("siglaProvincia", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDNazioneVO.class,
				new HashMap<String, String>() {
					{
						put("codIstatNazione", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDObiettivoGenQsnVO.class, new HashMap<String, String>() {
					{
						put("descObiettivoGeneraleQsn",
								DESCRIZIONE_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDObiettivoSpecifQsnVO.class,
				new HashMap<String, String>() {
					{
						put("descObiettivoSpecificoQsn",
								DESCRIZIONE_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDIndRisultatoProgramVO.class,
				new HashMap<String, String>() {
					{
						put("descIndRisultatoProgramma",
								DESCRIZIONE_PROPERTY_NAME);
						put("idLineaDiIntervento", ID_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(AttivitaAtecoVO.class,
				new HashMap<String, String>() {
					{
						put("codAteco", DESCRIZIONE_BREVE_PROPERTY_NAME);
						put("descAteco", DESCRIZIONE_PROPERTY_NAME);
						put("idAttivitaAteco", ID_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipoStrumentoProgrVO.class,
				new HashMap<String, String>() {
					{
						put("descStrumento", DESCRIZIONE_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDDimensioneTerritorVO.class,
				new HashMap<String, String>() {
					{
						put("descDimensioneTerritoriale",
								DESCRIZIONE_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(EnteDiCompetenzaVO.class,
				new HashMap<String, String>() {
					{
						put("idEnteCompetenza", ID_PROPERTY_NAME);
						put("descEnte", DESCRIZIONE_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PeriodoProgettoVO.class,
				new HashMap<String, String>() {
					{
						put("idPeriodo", ID_PROPERTY_NAME);
						put("descPeriodoVisualizzata",
								DESCRIZIONE_PROPERTY_NAME);
					}
				});

		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				ProgettoSoggettiPrivatiVO.class, new HashMap<String, String>() {
					{
						put("idSoggetto", ID_PROPERTY_NAME);
						put("denominazioneSoggettoPrivato",
								DESCRIZIONE_PROPERTY_NAME);
					}
				});

		// }L{ PBANDI-1023 START
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				BandoLineaTemaPrioritarioAssociatoVO.class,
				new HashMap<String, String>() {
					{
						put("idTemaPrioritario", ID_PROPERTY_NAME);
						put("descTemaPrioritario", DESCRIZIONE_PROPERTY_NAME);
						put("codIgrueT4", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PrioritaQsnLineaVO.class,
				new HashMap<String, String>() {
					{
						put("codIgrueT12", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDPrioritaQsnVO.class,
				new HashMap<String, String>() {
					{
						put("codIgrueT12", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDIntesaVO.class,
				new HashMap<String, String>() {
					{
						put("codIgrueT11", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipoOperazioneVO.class, new HashMap<String, String>() {
					{
						put("codIgrueT0", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDSettoreCipeVO.class,
				new HashMap<String, String>() {
					{
						put("codSettoreCipe", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDNaturaCipeVO.class,
				new HashMap<String, String>() {
					{
						put("codNaturaCipe", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDSettoreCptVO.class,
				new HashMap<String, String>() {
					{
						put("codIgrueT3", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipologiaAttivazioneVO.class,
				new HashMap<String, String>() {
					{
						put("codIgrueT50", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDObiettivoGenQsnVO.class, new HashMap<String, String>() {
					{
						put("codIgrueT2", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDObiettivoSpecifQsnVO.class,
				new HashMap<String, String>() {
					{
						put("codIgrueT2", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDCategoriaCipeVO.class, new HashMap<String, String>() {
					{
						put("codCategoriaCipe", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipologiaCipeVO.class, new HashMap<String, String>() {
					{
						put("codTipologiaCipe", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(PbandiDTipoAiutoVO.class,
				new HashMap<String, String>() {
					{
						put("codIgrueT1", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTemaPrioritarioVO.class, new HashMap<String, String>() {
					{
						put("codIgrueT4", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDIndicatoreQsnVO.class, new HashMap<String, String>() {
					{
						put("codIgrueT12", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		// }L{ PBANDI-1023 END
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				UtentePersonaFisicaVO.class, new HashMap<String, String>() {
					{
						put("idUtente", ID_PROPERTY_NAME);
						put("cognomeNomeCodice", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		// CDU-14 V04
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				BandoLineaTipoPeriodoAssociatoVO.class, new HashMap<String, String>() {
					{
						put("idTipoPeriodo", ID_PROPERTY_NAME);
						put("descTipoPeriodo", DESCRIZIONE_PROPERTY_NAME);
						put("descBreveTipoPeriodo", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				BandoLineaDaCuiDuplicareModelliVO.class, new HashMap<String, String>() {
					{
						put("progrBandoLineaIntervento", ID_PROPERTY_NAME);
						put("nomeBandoLinea", DESCRIZIONE_PROPERTY_NAME);
						
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				ModelloBandoLineaVO.class, new HashMap<String, String>() {
					{
						put("idTipoDocumentoIndex", ID_PROPERTY_NAME);
						put("descTipoDocIndex", DESCRIZIONE_PROPERTY_NAME);
						put("descBreveTipoDocIndex", DESCRIZIONE_BREVE_PROPERTY_NAME);
						
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDModalitaPagamentoVO.class, new HashMap<String, String>() {
					{
						put("idModalitaPagamento", ID_PROPERTY_NAME);
						put("descModalitaPagamento", DESCRIZIONE_PROPERTY_NAME);
						put("descBreveModalitaPagamento", DESCRIZIONE_BREVE_PROPERTY_NAME);
						
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipoDocumentoSpesaVO.class, new HashMap<String, String>() {
					{
						put("idTipoDocumentoSpesa", ID_PROPERTY_NAME);
						put("descTipoDocumentoSpesa", DESCRIZIONE_PROPERTY_NAME);
						put("descBreveTipoDocSpesa", DESCRIZIONE_BREVE_PROPERTY_NAME);
						
					}
				});
		// CDU-14 V04 fine
		
		// JIRA PBANDI-1390
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiTProcessoVO.class, new HashMap<String, String>() {
					{
						put("idProcesso", ID_PROPERTY_NAME);
						put("descrProcesso", DESCRIZIONE_PROPERTY_NAME);						
					}
				});
		// JIRA PBANDI-1390 fine
		
		// CDU-14 V07: inizio
		/*	codice che legge i dati da db commentato causa collegamento con findom sospeso.
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDAllegatiVO.class, new HashMap<String, String>() {
					{
						put("idAllegato", ID_PROPERTY_NAME);
						put("descrizione", DESCRIZIONE_PROPERTY_NAME);
						
					}
				});
		*/
		// CDU-14 V07: fine
		
		// CDU-13 V07: inizio
		/* commentato causa sospensione collegamento con findom.
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDCampiInterventoVO.class, new HashMap<String, String>() {
					{
						put("idCampoIntervento", ID_PROPERTY_NAME);
						put("codCampoIntervento", DESCRIZIONE_BREVE_PROPERTY_NAME);
						put("descrizione", DESCRIZIONE_PROPERTY_NAME);
						
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDSettoreAttivitaVO.class, new HashMap<String, String>() {
					{
						put("idSettoreAttivita", ID_PROPERTY_NAME);
						put("codSettore", DESCRIZIONE_BREVE_PROPERTY_NAME);
						put("descSettore", DESCRIZIONE_PROPERTY_NAME);
						
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDAtecoVO.class, new HashMap<String, String>() {
					{
						put("idAteco", ID_PROPERTY_NAME);
						put("codAteco", DESCRIZIONE_BREVE_PROPERTY_NAME);
						put("descAteco", DESCRIZIONE_PROPERTY_NAME);
						
					}
				});
		*/
		// CDU-13 V07: fine
		// CDU-13 V08: inizio
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiRBandoLineaInterventVO.class, new HashMap<String, String>() {
					{
						put("progrBandoLineaIntervento", ID_PROPERTY_NAME);
						put("nomeBandoLinea", DESCRIZIONE_PROPERTY_NAME);						
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDObiettivoTemVO.class, new HashMap<String, String>() {
					{
						put("idObiettivoTem", ID_PROPERTY_NAME);
						put("codObiettivoTem", DESCRIZIONE_BREVE_PROPERTY_NAME);
						put("descrObiettivoTem", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				ClassificazioneRaVO.class, new HashMap<String, String>() {
					{
						put("idClassificazioneRa", ID_PROPERTY_NAME);
						put("codClassificazioneRa", DESCRIZIONE_BREVE_PROPERTY_NAME);
						put("descrClassificazioneRa", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		// CDU-13 V08: fine
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDGrandeProgettoVO.class, new HashMap<String, String>() {
					{
						put("codGrandeProgetto", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDStrumentoAttuativoVO.class, new HashMap<String, String>() {
					{
						put("descStrumentoAttuativo", DESCRIZIONE_PROPERTY_NAME);
						put("tipologia", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		// Jira PBANDI-2658
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipologiaAggiudicazVO.class, new HashMap<String, String>() {
					{
						put("descTipologiaAggiudicazione", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDCategAnagraficaVO.class, new HashMap<String, String>() {
					{
						put("idCategAnagrafica", ID_PROPERTY_NAME);
						put("descCategAnagrafica", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDNormativaAffidamentoVO.class, new HashMap<String, String>() {
					{
						put("idNorma", ID_PROPERTY_NAME);
						put("descNorma", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipoAffidamentoVO.class, new HashMap<String, String>() {
					{
						put("idTipoAffidamento", ID_PROPERTY_NAME);
						put("descTipoAffidamento", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipologieVariantiVO.class, new HashMap<String, String>() {
					{
						put("idTipologiaVariante", ID_PROPERTY_NAME);
						put("descrizione", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipoProceduraOrigVO.class, new HashMap<String, String>() {
					{
						put("idTipoProceduraOrig", ID_PROPERTY_NAME);
						put("descTipoProceduraOrig", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDClassRevocaIrregVO.class, new HashMap<String, String>() {
					{
						put("idClassRevocaIrreg", ID_PROPERTY_NAME);
						put("descrizione", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiCSchemaFattElettVO.class, new HashMap<String, String>() {
					{
						put("idSchemaFattElett", ID_PROPERTY_NAME);
						put("descrizione", DESCRIZIONE_PROPERTY_NAME);
						put("descrBreve", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		
		// Cultura.
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDTipolVoceDiSpesaVO.class, new HashMap<String, String>() {
					{
						put("idTipologiaVoceDiSpesa", ID_PROPERTY_NAME);
						put("descrizione", DESCRIZIONE_PROPERTY_NAME);
					}
				});
		
		ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO.put(
				PbandiDVoceDiEntrataVO.class, new HashMap<String, String>() {
					{
						put("idVoceDiEntrata", ID_PROPERTY_NAME);
						put("descrizione", DESCRIZIONE_PROPERTY_NAME);
						put("descrizioneBreve", DESCRIZIONE_BREVE_PROPERTY_NAME);
					}
				});
		
	}
	static {
		ORDER_BY_SPECIFICI_PER_VO.put(DeliberaVO.class,
				new ArrayList<String>() {
					{
						add("numero");
						add("anno");
						add("descQuota");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(
				BandoLineaTemaPrioritarioAssociatoVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT4");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PrioritaQsnLineaVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT12");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDIntesaVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT11");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipoOperazioneVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT0");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDSettoreCipeVO.class,
				new ArrayList<String>() {
					{
						add("codSettoreCipe");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDNaturaCipeVO.class,
				new ArrayList<String>() {
					{
						add("codNaturaCipe");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDSettoreCptVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT3");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipologiaAttivazioneVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT50");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDObiettivoGenQsnVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT2");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDObiettivoSpecifQsnVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT2");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDCategoriaCipeVO.class,
				new ArrayList<String>() {
					{
						add("codCategoriaCipe");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipologiaCipeVO.class,
				new ArrayList<String>() {
					{
						add("codTipologiaCipe");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipoAiutoVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT1");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTemaPrioritarioVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT4");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDIndicatoreQsnVO.class,
				new ArrayList<String>() {
					{
						add("codIgrueT12");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(UtentePersonaFisicaVO.class,
				new ArrayList<String>() {
					{
						add("cognomeNomeCodice");
					}
				});
		
		// CDU-14 V04
		ORDER_BY_SPECIFICI_PER_VO.put(BandoLineaTipoPeriodoAssociatoVO.class,
				new ArrayList<String>() {
					{
						add("descBreveTipoPeriodo");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(BandoLineaDaCuiDuplicareModelliVO.class,
				new ArrayList<String>() {
					{
						add("nome_bando_linea");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(ModelloBandoLineaVO.class,
				new ArrayList<String>() {
					{
						add("idTipoDocumentoIndex");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipoDocumentoSpesaVO.class,
				new ArrayList<String>() {
					{
						add("descBreveTipoDocSpesa");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDModalitaPagamentoVO.class,
				new ArrayList<String>() {
					{
						add("descBreveModalitaPagamento");
					}
				});		
		// CDU-14 V04 fine
		
		// JIRA PBANDI-1390
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiTProcessoVO.class,
				new ArrayList<String>() {
					{
						add("descrProcesso");
					}
				});			
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDSettoreAttivitaVO.class,
				new ArrayList<String>() {
					{
						add("codSettore");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDClassRevocaIrregVO.class,
				new ArrayList<String>() {
					{
						add("descrizione");
					}
				});	
		// JIRA PBANDI-1390 fine
		
		// CDU-14 V07: inizio
		/*	codice che legge i dati da db commentato causa collegamento con findom sospeso.
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDAllegatiVO.class,
				new ArrayList<String>() {
					{
						add("descrizione");
					}
				});
		*/
		// CDU-14 V07: fine
		
		// CDU-13 V07: inizio
		/* commentato causa sospensione collegamento con findom.
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDCampiInterventoVO.class,
				new ArrayList<String>() {
					{
						add("descrizione");
					}
				});
		
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDAtecoVO.class,
				new ArrayList<String>() {
					{
						add("codAteco");
					}
				});
		*/
		// CDU-13 V07: fine
		// CDU-13 V08: inizio
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiRBandoLineaInterventVO.class,
				new ArrayList<String>() {
					{
						add("nomeBandoLinea");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDObiettivoTemVO.class,
				new ArrayList<String>() {
					{
						add("descrObiettivoTem");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(ClassificazioneRaVO.class,
				new ArrayList<String>() {
					{
						add("descrClassificazioneRa");
					}
				});
		// CDU-13 V08: fine
		// Jira PBANDI-2658
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipologiaAggiudicazVO.class,
				new ArrayList<String>() {
					{
						add("descTipologiaAggiudicazione");
					}
				});	
		
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDCategAnagraficaVO.class,
				new ArrayList<String>() {
					{
						add("idCategAnagrafica");
					}
				});	
		
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDNormativaAffidamentoVO.class,
				new ArrayList<String>() {
					{
						add("descNorma");
					}
				});	
		
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipoAffidamentoVO.class,
				new ArrayList<String>() {
					{
						add("descTipoAffidamento");
					}
				});
		
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipologieVariantiVO.class,
				new ArrayList<String>() {
					{
						add("descrizione");
					}
				});
		
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipoProceduraOrigVO.class,
				new ArrayList<String>() {
					{
						add("descTipoProceduraOrig");
					}
				});
		
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiCSchemaFattElettVO.class,
				new ArrayList<String>() {
					{
						add("descrBreve");
					}
				});
		
		// Cultura.
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDTipolVoceDiSpesaVO.class,
				new ArrayList<String>() {
					{
						add("descrizione");
					}
				});
		ORDER_BY_SPECIFICI_PER_VO.put(PbandiDVoceDiEntrataVO.class,
				new ArrayList<String>() {
					{
						add("descrizione");
					}
				});
	}
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;

	private long startTime = 0;// 900 sec,15 min

	private final HashMap<String, DecodificaDTO[]> cache = new HashMap<String, DecodificaDTO[]>();

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

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	@SuppressWarnings("unchecked")
	public DecodificaDTO[] findDecodifiche(String chiave) throws Exception {
		return findDecodifiche(chiave, true);
	}

	@SuppressWarnings("unchecked")
	private DecodificaDTO[] findDecodifiche(String chiave, boolean checkSelezionabile) throws Exception {
		// System.out.println("DecodificheManager>findDecodifiche");
		Class<GenericVO> voClass = (Class<GenericVO>) GenericVO.class
				.getClassLoader().loadClass(chiave);
		return findDecodifiche(voClass, Condition.validOnly(voClass), checkSelezionabile);
	}

	public DecodificaDTO[] findDecodificheStorico(String chiave)
			throws Exception {
		return findDecodificheStorico(chiave, true);
	}

	@SuppressWarnings("unchecked")
	private DecodificaDTO[] findDecodificheStorico(String chiave, boolean checkSelezionabile)
			throws Exception {
		Class<GenericVO> voClass = (Class<GenericVO>) GenericVO.class
				.getClassLoader().loadClass(chiave);
		return findDecodifiche(voClass, Condition.filterBy(voClass
				.newInstance()), checkSelezionabile);
	}

	public <T extends GenericVO> DecodificaDTO[] findDecodificheFiltrato(
			String chiave, String nomeCampo, String valore) throws Exception {
		return findDecodificheFiltrato(chiave, nomeCampo, valore, true);
	}

	public <T extends GenericVO> DecodificaDTO[] findDecodificheFiltratoStorico(
			String chiave, String nomeCampo, String valore) throws Exception {
		return findDecodificheFiltrato(chiave, nomeCampo, valore, false);
	}

	private <T extends GenericVO> DecodificaDTO[] findDecodifiche(
			Class<T> voClass, Condition<T> condition, boolean checkFlagSelezionabile) throws Exception {
		
		// System.out.println("DecodificheManager > findDecodifiche2 "); 
		T vo = voClass.newInstance();
		DecodificaMap decodificaMap = new DecodificaMap(voClass);
		vo.setAscendentOrder(true);
		List<String> orderPropertyNamesList = new ArrayList<String>();
		List<String> orderBys = ORDER_BY_SPECIFICI_PER_VO.get(voClass);
		for (String property : ObjectUtil.lazyIterator(orderBys)) {
			// ATTENZIONE: il nome della property � strettamente legato alla
			// tabella
			orderPropertyNamesList.add(property);
		}
		
		Condition<T> filter=null;
		Set props=beanUtil.getProperties(voClass);
		if(checkFlagSelezionabile && props.contains(FLAG_SELEZIONABILE)){
			BeanUtil.setPropertyValueByName(vo,FLAG_SELEZIONABILE,DB_FIELD_TRUE_VALUE);
		}
			
		orderPropertyNamesList.add((String) decodificaMap.inverseBidiMap().get(
				DESCRIZIONE_PROPERTY_NAME));
		vo.setOrderPropertyNamesList(orderPropertyNamesList);
		
		// System.out.println("DecodificheManager > findDecodifiche2 beanUtil="+beanUtil); 
		// System.out.println("DecodificheManager > findDecodifiche2 genericDAO="+genericDAO); 
		
		return beanUtil.transform(genericDAO.findListWhere(Condition.filterBy(
				vo).and(condition)), DecodificaDTO.class, decodificaMap);
	}

	private <T extends GenericVO> DecodificaDTO[] findDecodificheStorico(
			Class<T> voClass) throws Exception {
		T vo = voClass.newInstance();
		DecodificaMap decodificaMap = new DecodificaMap(voClass);
		vo.setAscendentOrder(true);
		List<String> orderPropertyNamesList = new ArrayList<String>();
		List<String> orderBys = ORDER_BY_SPECIFICI_PER_VO.get(voClass);
		for (String property : ObjectUtil.lazyIterator(orderBys)) {
			// ATTENZIONE: il nome della property � strettamente legato alla
			// tabella
			orderPropertyNamesList.add(property);
		}
		orderPropertyNamesList.add((String) decodificaMap.inverseBidiMap().get(
				DESCRIZIONE_PROPERTY_NAME));
		vo.setOrderPropertyNamesList(orderPropertyNamesList);
		return beanUtil.transform(genericDAO.findListWhere(Condition.filterBy(
				vo)), DecodificaDTO.class, decodificaMap);
	}

	@SuppressWarnings("unchecked")
	private <T extends GenericVO> DecodificaDTO[] findDecodificheFiltrato(
			String chiave, String nomeCampo, String valore, boolean isValidOnly)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, Exception {
		Class<T> voClass = (Class<T>) GenericVO.class.getClassLoader()
				.loadClass(chiave);
		T vo = voClass.newInstance();
		beanUtil.setPropertyValueByName(vo, nomeCampo, valore);
	
		Condition<T> condition = Condition.filterBy(vo).and(
				Condition.validOnly(voClass));
		if (isValidOnly) {
			condition = condition.and(Condition.validOnly(voClass));
		}
	
		return findDecodifiche(voClass, condition, true);
	}

	// FIXME ottimizzabilissimo
	public String findDescrizioneByDescBreve(Class<? extends GenericVO> key,
			String descBreve) {
		return findDescrizioneById(key, decodeDescBreve(key, descBreve));
	}

	public String findDescrizioneById(Class<? extends GenericVO> key,
			BigDecimal id) {
		return findDecodifica(key.getCanonicalName(),
				id != null ? id.longValue() : null).getDescrizione();
	}

	public BigDecimal decodeDescBreve(Class<? extends GenericVO> key,
			String descBreve) {
		return beanUtil.transform(findDecodifica(key.getCanonicalName(),
				descBreve).getId(), BigDecimal.class);
	}

	public BigDecimal decodeDescBreveStorico(Class<? extends GenericVO> key,
			String descBreve) {
		return beanUtil.transform(findDecodificaStorico(key.getCanonicalName(),
				descBreve).getId(), BigDecimal.class);
	}

	public String findDescBreve(Class<? extends GenericVO> key, BigDecimal id) {
		return findDecodifica(key.getCanonicalName(),
				id != null ? id.longValue() : null).getDescrizioneBreve();
	}

	public String findDescBreveStorico(Class<? extends GenericVO> key, BigDecimal id) {
		return findDecodificaStorico(key.getCanonicalName(),
				id != null ? id.longValue() : null).getDescrizioneBreve();
	}

	public DecodificaDTO findDecodifica(String chiave, BigDecimal id) {
		if (id == null) return null;
		
		return findDecodifica(chiave, id.longValue());
	}
	/**
	 * 
	 * @param chiave
	 *            : costante codificata in GestioneDatiDiDominioSrv
	 * @param id
	 * @return
	 */
	public DecodificaDTO findDecodifica(String chiave, Long id) {
		DecodificaDTO decodificaVO = new DecodificaDTO();
		try {
			DecodificaDTO[] list = null;
			long cacheTime = System.currentTimeMillis() - startTime;
			if (cacheTime < CACHE_TIMEOUT)
				list = cache.get(chiave);
			if (list == null || list.length == 0) {
				list = findDecodifiche(chiave, false);
				startTime = System.currentTimeMillis();
				cache.put(chiave, list);
			}
	
			// FIXME NOOOO cacheamo tutta la tabella e poi ci cerchiamo dentro
			// NOOOO
			if (list != null && id != null) {
				for (DecodificaDTO decodifica : list)
					if (decodifica.getId().equals(id)) {
						decodificaVO = decodifica;
						break;
					}
			}
		} catch (Exception e) {
			logger.warn("Impossibile effettuare la decodifica: " + chiave
					+ ", ragione: " + e.getMessage());
		}
		return decodificaVO;
	}

	/**
	 * 
	 * @param chiave
	 *            : costante codificata in GestioneDatiDiDominioSrv
	 * @param descBreve
	 * @return
	 */

	public DecodificaDTO findDecodifica(String chiave, String descBreve) {
		
		DecodificaDTO decodificaVO = new DecodificaDTO();
		try {
			DecodificaDTO[] list = null;
			if ((System.currentTimeMillis() - startTime) > CACHE_TIMEOUT) {
				list = cache.get(chiave);
			}

			if (list == null || list.length == 0) {
				list = findDecodifiche(chiave, false);
				startTime = System.currentTimeMillis();
				cache.put(chiave, list);
			}

			if (list != null && descBreve != null) {
				for (DecodificaDTO decodifica : list)
					if (decodifica.getDescrizioneBreve().equals(descBreve)) {
						decodificaVO = decodifica;
						break;
					}
			}
		} catch (Exception e) {
			logger.warn("Impossibile effettuare la decodifica: " + chiave
					+ ", ragione: " + e.getMessage());
		}  
		
		return decodificaVO;
	}

	private DecodificaDTO findDecodificaStorico(String chiave, Long id) {
		DecodificaDTO decodificaVO = new DecodificaDTO();
		try {
			DecodificaDTO[] list = null;
			long cacheTime = System.currentTimeMillis() - startTime;
			if (cacheTime < CACHE_TIMEOUT)
				list = cache.get(chiave+"STORICO");
			if (list == null || list.length == 0) {
				list = findDecodificheStorico(chiave, false);
				startTime = System.currentTimeMillis();
				cache.put(chiave+"STORICO", list);
			}
			if (list != null && id != null) {
				for (DecodificaDTO decodifica : list)
					if (decodifica.getId().equals(id)) {
						decodificaVO = decodifica;
						break;
					}
			}
		} catch (Exception e) {
			logger.warn("Impossibile effettuare la decodifica: " + chiave
					+ ", ragione: " + e.getMessage());
		}
		return decodificaVO;
	}

	private DecodificaDTO findDecodificaStorico(String chiave, String descBreve) {
		
		DecodificaDTO decodificaVO = new DecodificaDTO();
		try {
			DecodificaDTO[] list = null;
			if ((System.currentTimeMillis() - startTime) > CACHE_TIMEOUT) {
				list = cache.get(chiave+"STORICO");
			}
	
			if (list == null || list.length == 0) {
				Class<GenericVO> voClass = (Class<GenericVO>) GenericVO.class
				.getClassLoader().loadClass(chiave);
				list = findDecodificheStorico(voClass);
				startTime = System.currentTimeMillis();
				cache.put(chiave+"STORICO", list);
			}
	
			if (list != null && descBreve != null) {
				for (DecodificaDTO decodifica : list)
					if (decodifica.getDescrizioneBreve().equals(descBreve)) {
						decodificaVO = decodifica;
						break;
					}
			}
		} catch (Exception e) {
			logger.warn("Impossibile effettuare la decodifica: " + chiave
					+ ", ragione: " + e.getMessage());
		}  
		
		return decodificaVO;
	}

	private class DecodificaMap implements Map<String, String> {
		private static final String DESC_BREVE_PREFIX = "descBreve";
		private static final String DESC_PREFIX = "desc";
		private static final String DESC_ESTESA_PREFIX = "descEstesa";
		private static final String ID_PREFIX = ID_PROPERTY_NAME;

		private BidiMap map = new TreeBidiMap();

		public <T extends GenericVO> DecodificaMap(Class<T> voClass) {
			String simpleName = voClass.getSimpleName();
			String voPrefix = "Pbandi";
			String entityName = simpleName.substring(simpleName
					.contains(voPrefix) ? voPrefix.length() + 1 : 0, simpleName
					.length()
					- "VO".length());

			Map<String, Integer> scoreMap = new HashMap<String, Integer>();

			BidiMap winners = new TreeBidiMap();

			List<String> prefixes = new ArrayList<String>();
			prefixes.add(ID_PREFIX);
			prefixes.add(DESC_BREVE_PREFIX);
			prefixes.add(DESC_PREFIX);
			prefixes.add(DESC_ESTESA_PREFIX);

			for (String property : GenericDAO
					.extractMappableProperties(voClass)) {
				for (String prefix : prefixes) {
					int score = calculateScore(prefix, entityName, property);
					Integer prevScore = scoreMap.get(prefix);
					Integer scorePerProperty = scoreMap.get(winners
							.inverseBidiMap().get(property));
					if ((prevScore == null || score > prevScore)
							&& (scorePerProperty == null || score > scorePerProperty)) {
						scoreMap.put(prefix, score);
						winners.put(prefix, property);
					}
				}
			}
			put((String) winners.get(ID_PREFIX), ID_PREFIX);
			Integer descEstesaScore = scoreMap.get(DESC_ESTESA_PREFIX);
			Integer descScore = scoreMap.get(DESC_PREFIX);
			if ((descEstesaScore != null && descScore != null && descEstesaScore < descScore)
					|| (descScore != null)) {
				put((String) winners.get(DESC_PREFIX),
						DESCRIZIONE_PROPERTY_NAME);
			} else if (descEstesaScore != null) {
				put((String) winners.get(DESC_ESTESA_PREFIX),
						DESCRIZIONE_PROPERTY_NAME);
			}
			put((String) winners.get(DESC_BREVE_PREFIX), "descrizioneBreve");

			put("dtInizioValidita", "dataInizioValidita");
			put("dtFineValidita", "dataFineValidita");

			Map<String, String> map = ECCEZIONI_PER_MAPPATURA_IN_DECODIFICAVO
					.get(voClass);
			if (map != null) {
				putAll(map);
			}
		}

		private int calculateScore(String prefix, String entityName,
				String property) {
			int score = 0;

			char[] chars = (prefix + entityName).toCharArray();
			char[] propChars = property.toCharArray();

			for (int i = 0; i < chars.length && i < propChars.length; i++) {
				if (chars[i] == propChars[i]) {
					score = score + 2;
				} else {
					score = score - 1;
				}
			}

			return score;
		}

		public BidiMap inverseBidiMap() {
			return map.inverseBidiMap();
		}

		public void clear() {
			map.clear();
		}

		public boolean containsKey(Object key) {
			return map.containsKey(key);
		}

		public boolean containsValue(Object value) {
			return map.containsValue(value);
		}

		@SuppressWarnings("unchecked")
		public Set<java.util.Map.Entry<String, String>> entrySet() {
			return map.entrySet();
		}

		public String get(Object key) {
			return (String) map.get(key);
		}

		public boolean isEmpty() {
			return map.isEmpty();
		}

		@SuppressWarnings("unchecked")
		public Set<String> keySet() {
			return map.keySet();
		}

		public String put(String key, String value) {
			String result = null;
			if (key != null) {
				result = (String) map.put(key, value);
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		public void putAll(Map<? extends String, ? extends String> t) {
			map.putAll(t);
		}

		public String remove(Object key) {
			return (String) map.remove(key);
		}

		public int size() {
			return map.size();
		}

		@SuppressWarnings("unchecked")
		public Collection<String> values() {
			return map.values();
		}
	}
	
	// ******************************************
	//     INIZIO METODI MULTI-PROGRAMMAZIONE    
	// ******************************************
	
	private static final String ID_LINEA_INTERVENTO_PROPERTY_NAME = "idLineaDiIntervento";
	private static final String NOME_CLASSE_PROPERTY_NAME = "nomeClasse";
	private static final String NOME_CAMPO_JOIN_PROPERTY_NAME = "nomeCampoJoin";
	
	// Inserire qui:
	//  - la classe che mappa la tabella di decodifica (D).
	//  - HashMap contenente:
	//     - la classe che mappa la tabella di relazione (R) relativa a quella di decodifica (D).
	//     - il campo su cui fare la join tra la tabella D e quella R.
	private static final Map<Class<?>, Map<String, String>> TABELLE_R_DECODIFICA = new HashMap<Class<?>, Map<String, String>>();
	static {
		TABELLE_R_DECODIFICA.put(
				PbandiDTipoOperazioneVO.class, 
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaTipoOperazioneVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idTipoOperazione");
					}
		});	
		TABELLE_R_DECODIFICA.put(
				PbandiDTipologiaAttivazioneVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaTipologiaAttivVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idTipologiaAttivazione");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDTipoIndicatoreVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaTipoIndicatoreVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idTipoIndicatore");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDTipoLocalizzazioneVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaTipoLocalVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idTipoLocalizzazione");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDLivelloIstituzioneVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaLivelloIstituzVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idLivelloIstituzione");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDProgettoComplessoVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaProgComplessoVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idProgettoComplesso");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDModalitaAgevolazioneVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaModAgevolVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idModalitaAgevolazione");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDTipoAiutoVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaTipoAiutoVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idTipoAiuto");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDTemaPrioritarioVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaTemaPriorVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idTemaPrioritario");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDIndicatoreQsnVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaIndicatoreQsnVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idIndicatoreQsn");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDClassificazioneMetVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaClassifMetVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idClassificazioneMet");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDRuoloEnteCompetenzaVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaRuoloEnteCompVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idRuoloEnteCompetenza");
					}
		});
		TABELLE_R_DECODIFICA.put(
				PbandiDStrumentoAttuativoVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaStrumentoAttVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idStrumentoAttuativo");
					}
		});
		
		TABELLE_R_DECODIFICA.put(
				PbandiDDimensioneTerritorVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaDimensioneTerVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idDimensioneTerritor");
					}
		});
		
		TABELLE_R_DECODIFICA.put(
				PbandiDProgettoComplessoVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaProgComplessoVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idProgettoComplesso");
					}
		});
		
		// Jira PBANDI-2658
		TABELLE_R_DECODIFICA.put(
				PbandiDTipologiaAggiudicazVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaTipologiaAggVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idTipologiaAggiudicaz");
					}
		});
		
		
		TABELLE_R_DECODIFICA.put(
				PbandiDSettoreAttivitaVO.class,
				new HashMap<String, String>() {
					{ 
						put(NOME_CLASSE_PROPERTY_NAME, "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRLineaSettoreAttVO");
						put(NOME_CAMPO_JOIN_PROPERTY_NAME, "idSettoreAttivita");
					}
		});
		
		
	}
	
	@SuppressWarnings("unchecked")
	// Metodo copiato da findDecodifiche(String).
	public DecodificaDTO[] findDecodificheMultiProgr(String chiave, Long idLineaDiIntervento) throws Exception {
		return findDecodificheMultiProgr(chiave, true, idLineaDiIntervento);
	}
	
	@SuppressWarnings("unchecked")
	// Metodo copiato da findDecodifiche(String, boolean).
	private DecodificaDTO[] findDecodificheMultiProgr(String chiave, boolean checkSelezionabile, Long idLineaDiIntervento) throws Exception {
		Class<GenericVO> voClass = (Class<GenericVO>) GenericVO.class
				.getClassLoader().loadClass(chiave);
		return findDecodificheMultiProgr(voClass, Condition.validOnly(voClass), checkSelezionabile, idLineaDiIntervento);
	}
	
	public <T extends GenericVO> DecodificaDTO[] findDecodificheFiltratoMultiProgr(
			String chiave, String nomeCampo, String valore, Long idLineaDiIntervento) throws Exception {
		return findDecodificheFiltratoMultiProgr(chiave, nomeCampo, valore, true, idLineaDiIntervento);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends GenericVO> DecodificaDTO[] findDecodificheFiltratoMultiProgr(
			String chiave, String nomeCampo, String valore, boolean isValidOnly, Long idLineaDiIntervento)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, Exception {
		Class<T> voClass = (Class<T>) GenericVO.class.getClassLoader()
				.loadClass(chiave);
		T vo = voClass.newInstance();
		beanUtil.setPropertyValueByName(vo, nomeCampo, valore);
	
		Condition<T> condition = Condition.filterBy(vo).and(
				Condition.validOnly(voClass));
		if (isValidOnly) {
			condition = condition.and(Condition.validOnly(voClass));
		}
	
		return findDecodificheMultiProgr(voClass, condition, true, idLineaDiIntervento);
	}
	
	// Crea dinamicamente una select composta da un join tra 
	//  - la classe di decodifica in input 
	//  - e la classe di relazione associata
	// imponendo come vincolo l'idLineDiIntervento in input.
	// Metodo copiato da findDecodifiche(Class<T>, Condition<T>, boolean).
	private <T extends GenericVO> DecodificaDTO[] findDecodificheMultiProgr(
			Class<T> voClass, Condition<T> condition, boolean checkFlagSelezionabile,
			Long idLineaDiIntervento) throws Exception {
		
		logger.info("\n******* Eseguo findDecodificheMultiProgr con idLineaDiIntervento = "+idLineaDiIntervento+" e classe = "+voClass.getName());
		
		// Classe della tabella di decodifica.
		T vo = voClass.newInstance();
		vo.setAscendentOrder(true);
		
		// Recupero l'ordinamento previsto per la tabella di decodifica.
		List<String> orderPropertyNamesList = new ArrayList<String>();
		List<String> orderBys = ORDER_BY_SPECIFICI_PER_VO.get(voClass);
		for (String property : ObjectUtil.lazyIterator(orderBys)) {
			orderPropertyNamesList.add(property);
		}
		DecodificaMap decodificaMap = new DecodificaMap(voClass);
		orderPropertyNamesList.add((String) decodificaMap.inverseBidiMap().get(
				DESCRIZIONE_PROPERTY_NAME));
		vo.setOrderPropertyNamesList(orderPropertyNamesList);
		
		Condition<T> filter = null;
		Set props = beanUtil.getProperties(voClass);
		if(checkFlagSelezionabile && props.contains(FLAG_SELEZIONABILE)){
			BeanUtil.setPropertyValueByName(vo,FLAG_SELEZIONABILE,DB_FIELD_TRUE_VALUE);
		}
		
		// Data la classe in input (che mappa la tabella D di decodifica), recupero:
		//  - il nome della classe che mappa la tabella R di relazione (PBANDI_R_LINEA_qualcosa).
		//  - il nome del campo su cui costruire la join tra tabella D e tabella R. 
		Map<String, String> map = TABELLE_R_DECODIFICA.get(voClass);
		String nomeClasse = (String)map.get(NOME_CLASSE_PROPERTY_NAME);
		String nomeCampoJoin = (String)map.get(NOME_CAMPO_JOIN_PROPERTY_NAME);
		
		// Istanzio la classe corrispondente alla tabella R di relazione e
		// valorizzo il campo idLineaDiIntervento.
		T voJoin = (T)Class.forName(nomeClasse).newInstance();
		BeanUtil.setPropertyValueByName(voJoin,ID_LINEA_INTERVENTO_PROPERTY_NAME,idLineaDiIntervento);
		
		// Eseguo la join.
		List<Pair<GenericVO, T, T>> lista = null;
		lista = genericDAO.join(Condition.filterBy(vo).and(condition),
								Condition.filterBy(voJoin)).by(nomeCampoJoin)
								.select();
		
		// Trasformo il risultato della select in un array di oggetti DecodificaDTO. 
		DecodificaDTO[] dto = new DecodificaDTO[lista.size()];
		int i = 0;
		for (Pair<GenericVO, T, T> pair : lista) {
			dto[i] = beanUtil.transform(pair.getFirst(), DecodificaDTO.class, decodificaMap);
			//logger.info("findDecodificheMultiProgr(): codifica = "+dto[i].getId()+" - "+dto[i].getDescrizioneBreve()+" - "+dto[i].getDescrizione());
			i = i + 1;
		}

		return dto;
	}
	
	// ******************************************
	//      FINE METODI MULTI-PROGRAMMAZIONE     
	// ******************************************
}
