/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstantsMap {

	public static final String PREFIX_TAB_INFORMAZIONI = "informazioniBase";
	public static final String PREFIX_TAB_BENEFICIARIO = "beneficiario";

	public static final Map<String, String> MAP_ID_DESC_BREVE_DESC_ESTESA = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("id", "id");
			put("descrizioneBreve", "descBreve");
			put("descrizione", "descEstesa");
		}
	});

	public static final Map<String, String> MAP_CODICE_DESCRIZIONE = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("id", "codice");
			put("descrizione", "descrizione");
		}
	});

	
	public static final Map<String, String> MAP_CODICE_ERRORE_INFORMAZIONI_BASE = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("informazioniBase.titoloProgetto", "appDatainformazioniBase.titoloProgetto");
			put("informazioniBase.numeroDomanda", "appDatainformazioniBase.numeroDomanda");
			put("informazioniBase.cup", "appDatainformazioniBase.cup");
			put("informazioniBase.idAttivitaAteco", "appDatainformazioniBase.idAttivitaAteco");
			put("informazioniBase.idTipoOperazione", "appDatainformazioniBase.idTipoOperazione");
			put("informazioniBase.idIndicatoreQsn", "appDatainformazioniBase.idIndicatoreQsn");
			put("informazioniBase.idIndicatoreRisultatoProgramma", "appDatainformazioniBase.idIndicatoreRisultatoProgramma");
			put("informazioniBase.idObiettivoSpecificoQsn", "appDatainformazioniBase.idObiettivoSpecificoQsn");
			put("informazioniBase.idDimensioneTerritoriale", "appDatainformazioniBase.idDimensioneTerritoriale");
			put("informazioniBase.idAttivitaAteco", "appDatainformazioniBase.idAttivitaAteco");
			put("informazioniBase.idStrumentoAttuativo", "appDatainformazioniBase.idStrumentoAttuativo");
			put("informazioniBase.idSettoreCpt", "appDatainformazioniBase.idSettoreCpt");
			put("informazioniBase.idTipoStrumentoProgrammazione", "appDatainformazioniBase.idTipoStrumentoProgrammazione");
			put("informazioniBase.dataComitato", "appDatainformazioniBase.dataComitato");
			put("informazioniBase.dataConcessione", "appDatainformazioniBase.dataConcessione");
			put("informazioniBase.idCategoriaCipe", "appDatainformazioniBase.idCategoriaCipe");
			put("informazioniBase.idTipologiaCipe", "appDatainformazioniBase.idTipologiaCipe");
			put("informazioniBase.dataPresentazioneDomanda", "appDatainformazioniBase.dataPresentazioneDomanda");
			/*
			 * FIX PBandi-2141 -Aggiungo gli altri campi per il controllo di obbligatorieta'
			 */
			
			put("informazioniBase.idSettoreAttivita", "appDatainformazioniBase.idSettoreAttivita");
			put("informazioniBase.idPrioritaQsn", "appDatainformazioniBase.idPrioritaQsn");
			put("informazioniBase.idObiettivoGeneraleQsn", "appDatainformazioniBase.idObiettivoGeneraleQsn");
			put("informazioniBase.idTemaPrioritario", "appDatainformazioniBase.idTemaPrioritario");
			put("informazioniBase.idTipoAiuto", "appDatainformazioniBase.idTipoAiuto");
			put("informazioniBase.idSettoreCipe", "appDatainformazioniBase.idSettoreCipe");
			put("informazioniBase.idSottoSettoreCipe", "appDatainformazioniBase.idSottoSettoreCipe");
			put("informazioniBase.idNaturaCipe", "appDatainformazioniBase.idNaturaCipe");
			
			// nuovi campi per programmazione 2014_20
			put("informazioniBase.idObiettivoTematico", "appDatainformazioniBase.idObiettivoTematico");
			put("informazioniBase.idClassificazioneRA", "appDatainformazioniBase.idClassificazioneRA");
			put("informazioniBase.idGrandeProgetto", "appDatainformazioniBase.idGrandeProgetto");
		}
	});


	
	
	public static final Map<String, String> MAP_CODICE_ERRORE_BENEFICIARIO_PF = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{	
			put("beneficiario.datiPF.codiceFiscale", "appDatasoggettoBeneficiario.datiPF.codiceFiscale");
			put("beneficiario.datiPF.iban", "appDatasoggettoBeneficiario.datiPF.iban");
			put("beneficiario.datiPF.cognome", "appDatasoggettoBeneficiario.datiPF.cognome");
			put("beneficiario.datiPF.nome", "appDatasoggettoBeneficiario.datiPF.nome");
			put("beneficiario.datiPF.indirizzoRes", "appDatasoggettoBeneficiario.datiPF.indirizzoRes");
			put("beneficiario.datiPF.comuneRes.idComune", "appDatasoggettoBeneficiario.datiPF.comuneRes.idComune");
			put("beneficiario.datiPF.comuneNas.idComune", "appDatasoggettoBeneficiario.datiPF.comuneNas.idComune");
			put("beneficiario.datiPF.capRes", "appDatasoggettoBeneficiario.datiPF.capRes");
			put("beneficiario.datiPF.faxRes", "appDatasoggettoBeneficiario.datiPF.faxRes");
			put("beneficiario.datiPF.emailRes", "appDatasoggettoBeneficiario.datiPF.emailRes");
			put("beneficiario.datiPF.telefonoRes", "appDatasoggettoBeneficiario.datiPF.telefonoRes");
			put("beneficiario.datiPF.dataNascita", "appDatasoggettoBeneficiario.datiPF.dataNascita");
			/*
			 * FIX PBandi-2141 -Aggiungo gli altri campi per il controllo di obbligatorieta'
			 */
			put("beneficiario.datiPF.comuneRes.idNazione", "appDatasoggettoBeneficiario.datiPF.comuneRes.idNazione");
			put("beneficiario.datiPF.comuneRes.idRegione", "appDatasoggettoBeneficiario.datiPF.comuneRes.idRegione");
			put("beneficiario.datiPF.comuneRes.idProvincia", "appDatasoggettoBeneficiario.datiPF.comuneRes.idProvincia");
			put("beneficiario.datiPF.comuneNas.idNazione", "appDatasoggettoBeneficiario.datiPF.comuneNas.idNazione");
			put("beneficiario.datiPF.comuneNas.idRegione", "appDatasoggettoBeneficiario.datiPF.comuneNas.idRegione");
			put("beneficiario.datiPF.comuneNas.idProvincia", "appDatasoggettoBeneficiario.datiPF.comuneNas.idProvincia");
			

		}
	});

	public static final Map<String, String> MAP_CODICE_ERRORE_BENEFICIARIO_DR = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("beneficiario.datiPG.denominazioneEnteDirReg", "appDatasoggettoBeneficiario.datiPG.tabDirReg.denominazioneEnteDirReg");
			put("beneficiario.datiPG.iban", "appDatasoggettoBeneficiario.datiPG.tabDirReg.iban");
			put("beneficiario.datiPG.sedeLegale.idComune", "appDatasoggettoBeneficiario.datiPG.tabDirReg.sedeLegale.idComune");
			put("beneficiario.datiPG.emailSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDirReg.emailSedeLegale");
			put("beneficiario.datiPG.partitaIvaSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDirReg.partitaIvaSedeLegale");
			put("beneficiario.datiPG.indirizzoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDirReg.indirizzoSedeLegale");
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDirReg.capSedeLegale");
			put("beneficiario.datiPG.faxSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDirReg.faxSedeLegale");
			put("beneficiario.datiPG.telefonoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDirReg.telefonoSedeLegale");
			/*
			 * FIX PBandi-2141 -Aggiungo gli altri campi per il controllo di obbligatorieta'
			 */
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDirReg.capSedeLegale");
			put("beneficiario.datiPG.sedeLegale.idNazione", "appDatasoggettoBeneficiario.datiPG.tabDirReg.sedeLegale.idNazione");
			put("beneficiario.datiPG.sedeLegale.idRegione", "appDatasoggettoBeneficiario.datiPG.tabDirReg.sedeLegale.idRegione");
			put("beneficiario.datiPG.sedeLegale.idProvincia", "appDatasoggettoBeneficiario.datiPG.tabDirReg.sedeLegale.idProvincia");
		}
	});
	
	public static final Map<String, String> MAP_CODICE_ERRORE_BENEFICIARIO_PA = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("beneficiario.datiPG.denominazioneEntePA", "appDatasoggettoBeneficiario.datiPG.tabPA.denominazioneEntePA");
			put("beneficiario.datiPG.iban", "appDatasoggettoBeneficiario.datiPG.tabPA.iban");
			put("beneficiario.datiPG.sedeLegale.idComune", "appDatasoggettoBeneficiario.datiPG.tabPA.sedeLegale.idComune");
			put("beneficiario.datiPG.emailSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabPA.emailSedeLegale");
			put("beneficiario.datiPG.partitaIvaSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabPA.partitaIvaSedeLegale");
			put("beneficiario.datiPG.indirizzoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabPA.indirizzoSedeLegale");
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabPA.capSedeLegale");
			put("beneficiario.datiPG.faxSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabPA.faxSedeLegale");
			put("beneficiario.datiPG.telefonoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabPA.telefonoSedeLegale");
			/*
			 * FIX PBandi-2141 -Aggiungo gli altri campi per il controllo di obbligatorieta'
			 */
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabPA.capSedeLegale");
			put("beneficiario.datiPG.sedeLegale.idNazione", "appDatasoggettoBeneficiario.datiPG.tabPA.sedeLegale.idNazione");
			put("beneficiario.datiPG.sedeLegale.idRegione", "appDatasoggettoBeneficiario.datiPG.tabPA.sedeLegale.idRegione");
			put("beneficiario.datiPG.sedeLegale.idProvincia", "appDatasoggettoBeneficiario.datiPG.tabPA.sedeLegale.idProvincia");
		}
	});


	public static final Map<String, String> MAP_CODICE_ERRORE_BENEFICIARIO_NA = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("beneficiario.datiPG.codiceFiscale", "appDatasoggettoBeneficiario.datiPG.tabAltro.codiceFiscale");
			put("beneficiario.datiPG.iban", "appDatasoggettoBeneficiario.datiPG.tabAltro.iban");
			put("beneficiario.datiPG.denominazione", "appDatasoggettoBeneficiario.datiPG.tabAltro.denominazione");
			put("beneficiario.datiPG.formaGiuridica", "appDatasoggettoBeneficiario.datiPG.tabAltro.formaGiuridica");
			put("beneficiario.datiPG.attivitaAteco", "appDatasoggettoBeneficiario.datiPG.tabAltro.attivitaAteco");
			put("beneficiario.datiPG.sedeLegale.idComune", "appDatasoggettoBeneficiario.datiPG.tabAltro.sedeLegale.idComune");
			put("beneficiario.datiPG.partitaIvaSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabAltro.partitaIvaSedeLegale");
			put("beneficiario.datiPG.emailSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabAltro.emailSedeLegale");
			put("beneficiario.datiPG.indirizzoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabAltro.indirizzoSedeLegale");
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabAltro.capSedeLegale");
			put("beneficiario.datiPG.faxSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabAltro.faxSedeLegale");
			put("beneficiario.datiPG.telefonoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabAltro.telefonoSedeLegale");
			put("beneficiario.datiPG.dataCostituzioneAzienda", "appDatasoggettoBeneficiario.datiPG.tabAltro.dataCostituzioneAzienda");
			/*
			 * FIX PBandi-2141 -Aggiungo gli altri campi per il controllo di obbligatorieta'
			 */
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabAltro.capSedeLegale");
			put("beneficiario.datiPG.sedeLegale.idNazione", "appDatasoggettoBeneficiario.datiPG.tabAltro.sedeLegale.idNazione");
			put("beneficiario.datiPG.sedeLegale.idRegione", "appDatasoggettoBeneficiario.datiPG.tabAltro.sedeLegale.idRegione");
			put("beneficiario.datiPG.sedeLegale.idProvincia", "appDatasoggettoBeneficiario.datiPG.tabAltro.sedeLegale.idProvincia");
			put("beneficiario.datiPG.formaGiuridica", "appDatasoggettoBeneficiario.datiPG.tabAltro.formaGiuridica");
			put("beneficiario.datiPG.settoreAttivita", "appDatasoggettoBeneficiario.datiPG.tabAltro.settoreAttivita");
			
			
			
		}
	});

	public static final Map<String, String> MAP_CODICE_ERRORE_BENEFICIARIO_DU = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("beneficiario.datiPG.ateneo", "appDatasoggettoBeneficiario.datiPG.tabDipUni.ateneo");
			put("beneficiario.datiPG.iban", "appDatasoggettoBeneficiario.datiPG.tabDipUni.iban");
			put("beneficiario.datiPG.sedeLegale.idComune", "appDatasoggettoBeneficiario.datiPG.tabDipUni.sedeLegale.idComune");
			put("beneficiario.datiPG.partitaIvaSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDipUni.partitaIvaSedeLegale");
			put("beneficiario.datiPG.emailSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDipUni.emailSedeLegale");
			put("beneficiario.datiPG.indirizzoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDipUni.indirizzoSedeLegale");
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDipUni.capSedeLegale");
			put("beneficiario.datiPG.faxSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDipUni.faxSedeLegale");
			put("beneficiario.datiPG.telefonoSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDipUni.telefonoSedeLegale");
			/*
			 * FIX PBandi-2141 -Aggiungo gli altri campi per il controllo di obbligatorieta'
			 */
			put("beneficiario.datiPG.capSedeLegale", "appDatasoggettoBeneficiario.datiPG.tabDipUni.capSedeLegale");
			put("beneficiario.datiPG.sedeLegale.idNazione", "appDatasoggettoBeneficiario.datiPG.tabDipUni.sedeLegale.idNazione");
			put("beneficiario.datiPG.sedeLegale.idRegione", "appDatasoggettoBeneficiario.datiPG.tabDipUni.sedeLegale.idRegione");
			put("beneficiario.datiPG.sedeLegale.idProvincia", "appDatasoggettoBeneficiario.datiPG.tabDipUni.sedeLegale.idProvincia");
			
			
			
			
		}
	});

	public static final Map<String, String> MAP_CODICE_ERRORE_INTERMEDIARIO_PF = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{	
			put("intermediario.datiPF.codiceFiscale", "appDatasoggettoIntermediario.datiPF.codiceFiscale");
			put("intermediario.datiPF.iban", "appDatasoggettoIntermediario.datiPF.iban");
			put("intermediario.datiPF.cognome", "appDatasoggettoIntermediario.datiPF.cognome");
			put("intermediario.datiPF.nome", "appDatasoggettoIntermediario.datiPF.nome");
			put("intermediario.datiPF.comuneRes.idComune", "appDatasoggettoIntermediario.datiPF.comuneRes.idComune");
			put("intermediario.datiPF.indirizzoRes", "appDatasoggettoIntermediario.datiPF.indirizzoRes");
			put("intermediario.datiPF.capRes", "appDatasoggettoIntermediario.datiPF.capRes");
			put("intermediario.datiPF.faxRes", "appDatasoggettoIntermediario.datiPF.faxRes");
			put("intermediario.datiPF.emailRes", "appDatasoggettoIntermediario.datiPF.emailRes");
			put("intermediario.datiPF.telefonoRes", "appDatasoggettoIntermediario.datiPF.telefonoRes");
			put("intermediario.datiPF.dataNascita", "appDatasoggettoIntermediario.datiPF.dataNascita");
		}
	});

	public static final Map<String, String> MAP_CODICE_ERRORE_RAPPRESENTANTE_LEGALE = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{	
			put("rappresentanteLegale.datiPF.codiceFiscale", "appDatasoggettoRappresentanteLegale.datiPF.codiceFiscale");
			put("rappresentanteLegale.datiPF.indirizzoRes", "appDatasoggettoRappresentanteLegale.datiPF.indirizzoRes");
			put("rappresentanteLegale.datiPF.comuneRes.idComune", "appDatasoggettoRappresentanteLegale.datiPF.comuneRes.idComune");
			put("rappresentanteLegale.datiPF.cognome", "appDatasoggettoRappresentanteLegale.datiPF.cognome");
			put("rappresentanteLegale.datiPF.nome", "appDatasoggettoRappresentanteLegale.datiPF.nome");
			put("rappresentanteLegale.datiPF.capRes", "appDatasoggettoRappresentanteLegale.datiPF.capRes");
			put("rappresentanteLegale.datiPF.faxRes", "appDatasoggettoRappresentanteLegale.datiPF.faxRes");
			put("rappresentanteLegale.datiPF.emailRes", "appDatasoggettoRappresentanteLegale.datiPF.emailRes");
			put("rappresentanteLegale.datiPF.telefonoRes", "appDatasoggettoRappresentanteLegale.datiPF.telefonoRes");
			put("rappresentanteLegale.datiPF.dataNascita", "appDatasoggettoRappresentanteLegale.datiPF.dataNascita");
			
			/*
			 * FIX PBandi-2141. Altri campi obbligatori
			 */
			put("rappresentanteLegale.datiPF.comuneRes.idNazione", "appDatasoggettoRappresentanteLegale.datiPF.comuneRes.idNazione");
			put("rappresentanteLegale.datiPF.comuneRes.idRegione", "appDatasoggettoRappresentanteLegale.datiPF.comuneRes.idRegione");
			put("rappresentanteLegale.datiPF.comuneRes.idProvincia", "appDatasoggettoRappresentanteLegale.datiPF.comuneRes.idProvincia");
			
			put("rappresentanteLegale.datiPF.comuneNas.idNazione", "appDatasoggettoRappresentanteLegale.datiPF.comuneNas.idNazione");
			put("rappresentanteLegale.datiPF.comuneNas.idRegione", "appDatasoggettoRappresentanteLegale.datiPF.comuneNas.idRegione");
			put("rappresentanteLegale.datiPF.comuneNas.idProvincia", "appDatasoggettoRappresentanteLegale.datiPF.comuneNas.idProvincia");
			put("rappresentanteLegale.datiPF.comuneNas.idComune", "appDatasoggettoRappresentanteLegale.datiPF.comuneNas.idComune");
			
		}
	});

	public static final Map<String, String> MAP_CODICE_ERRORE_INTERMEDIARIO_DR = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("intermediario.datiPG.denominazioneEnteDirReg", "appDatasoggettoIntermediario.datiPG.tabDirReg.denominazioneEnteDirReg");
			put("intermediario.datiPG.iban", "appDatasoggettoIntermediario.datiPG.tabDirReg.iban");
			put("intermediario.datiPG.sedeLegale.idComune", "appDatasoggettoIntermediario.datiPG.tabDirReg.sedeLegale.idComune");
			put("intermediario.datiPG.partitaIvaSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDirReg.partitaIvaSedeLegale");
			put("intermediario.datiPG.emailSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDirReg.emailSedeLegale");
			put("intermediario.datiPG.indirizzoSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDirReg.indirizzoSedeLegale");
			put("intermediario.datiPG.capSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDirReg.capSedeLegale");
			put("intermediario.datiPG.faxSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDirReg.faxSedeLegale");
			put("intermediario.datiPG.telefonoSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDirReg.telefonoSedeLegale");

		}
	});


	public static final Map<String, String> MAP_CODICE_ERRORE_INTERMEDIARIO_NA = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("intermediario.datiPG.codiceFiscale", "appDatasoggettoIntermediario.datiPG.tabAltro.codiceFiscale");
			put("intermediario.datiPG.iban", "appDatasoggettoIntermediario.datiPG.tabAltro.iban");
			put("intermediario.datiPG.denominazione", "appDatasoggettoIntermediario.datiPG.tabAltro.denominazione");
			put("intermediario.datiPG.formaGiuridica", "appDatasoggettoIntermediario.datiPG.tabAltro.formaGiuridica");
			put("intermediario.datiPG.attivitaAteco", "appDatasoggettoIntermediario.datiPG.tabAltro.attivitaAteco");
			put("intermediario.datiPG.sedeLegale.idComune", "appDatasoggettoIntermediario.datiPG.tabAltro.sedeLegale.idComune");
			put("intermediario.datiPG.partitaIvaSedeLegale", "appDatasoggettoIntermediario.datiPG.tabAltro.partitaIvaSedeLegale");
			put("intermediario.datiPG.emailSedeLegale", "appDatasoggettoIntermediario.datiPG.tabAltro.emailSedeLegale");
			put("intermediario.datiPG.indirizzoSedeLegale", "appDatasoggettoIntermediario.datiPG.tabAltro.indirizzoSedeLegale");
			put("intermediario.datiPG.capSedeLegale", "appDatasoggettoIntermediario.datiPG.tabAltro.capSedeLegale");
			put("intermediario.datiPG.faxSedeLegale", "appDatasoggettoIntermediario.datiPG.tabAltro.faxSedeLegale");
			put("intermediario.datiPG.telefonoSedeLegale", "appDatasoggettoIntermediario.datiPG.tabAltro.telefonoSedeLegale");
		}
	});

	public static final Map<String, String> MAP_CODICE_ERRORE_INTERMEDIARIO_DU = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			put("intermediario.datiPG.ateneo", "appDatasoggettoIntermediario.datiPG.tabDipUni.ateneo");
			put("intermediario.datiPG.iban", "appDatasoggettoIntermediario.datiPG.tabDipUni.iban");
			put("intermediario.datiPG.sedeLegale.idComune", "appDatasoggettoIntermediario.datiPG.tabDipUni.sedeLegale.idComune");
			put("intermediario.datiPG.partitaIvaSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDipUni.partitaIvaSedeLegale");
			put("intermediario.datiPG.emailSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDipUni.emailSedeLegale");
			put("intermediario.datiPG.indirizzoSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDipUni.indirizzoSedeLegale");
			put("intermediario.datiPG.capSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDipUni.capSedeLegale");
			put("intermediario.datiPG.faxSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDipUni.faxSedeLegale");
			put("intermediario.datiPG.telefonoSedeLegale", "appDatasoggettoIntermediario.datiPG.tabDipUni.telefonoSedeLegale");
		}
	});

	public static final Map<String, String> MAP_CODICE_ERRORE_COMPLETO_NON_DIPENDENTE = Collections
	.unmodifiableMap(new HashMap<String, String>() {
		{
			putAll(MAP_CODICE_ERRORE_INFORMAZIONI_BASE);
			putAll(MAP_CODICE_ERRORE_RAPPRESENTANTE_LEGALE);
		}
	});


}
