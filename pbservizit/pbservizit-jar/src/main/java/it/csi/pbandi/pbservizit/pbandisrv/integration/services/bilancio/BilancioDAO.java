/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;

import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.InputLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.CapitoloKeyVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ConsultaBeneficiariVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoKeyVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.InserisciAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ProvvedimentoKeyVO;

public abstract class BilancioDAO {
	
	public static BidiMap MAP_ImpegnoKeyVO_TO_ImpegnoKey = new TreeBidiMap();
	public static BidiMap MAP_ImpegnoKey_TO_ImpegnoKeyVO = (BidiMap) MAP_ImpegnoKeyVO_TO_ImpegnoKey.inverseBidiMap();
	static {
		MAP_ImpegnoKeyVO_TO_ImpegnoKey.put("annoImpegno", "annoimp");
		MAP_ImpegnoKeyVO_TO_ImpegnoKey.put("numeroImpegno", "nimp");
	}
	
	public static BidiMap MAP_CapitoloKeyVO_TO_CapitoloKey = new TreeBidiMap();
	public static BidiMap MAP_CapitoloKey_TO_CapitoloKeyVO = (BidiMap) MAP_CapitoloKeyVO_TO_CapitoloKey.inverseBidiMap();
	static {
		MAP_CapitoloKeyVO_TO_CapitoloKey.put("numeroCapitolo", "nroCap");
	}
	
	public static BidiMap MAP_ProvvedimentoKeyVO_TO_ProvvedimentoKey = new TreeBidiMap();
	public static BidiMap MAP_ProvvedimentoKey_TO_ProvvedimentoKeyVO = (BidiMap) MAP_ProvvedimentoKeyVO_TO_ProvvedimentoKey.inverseBidiMap();
	static {
		MAP_ProvvedimentoKeyVO_TO_ProvvedimentoKey.put("annoProvvedimento", "annoProv");
		MAP_ProvvedimentoKeyVO_TO_ProvvedimentoKey.put("direzioneProvvedimento", "direzione");
		MAP_ProvvedimentoKeyVO_TO_ProvvedimentoKey.put("numeroProvvedimento", "nroProv");
		
	}
	
	public static BidiMap MAP_ImpegnoVO_TO_Impegno = new TreeBidiMap();
	public static BidiMap MAP_Impegno_TO_ImpegnoVO = (BidiMap) MAP_ImpegnoVO_TO_Impegno.inverseBidiMap();
	static {
		MAP_ImpegnoVO_TO_Impegno.put("annoEsercizio", "annoEsercizio");
		MAP_ImpegnoVO_TO_Impegno.put("annoImpegno", "annoimp");
		MAP_ImpegnoVO_TO_Impegno.put("annoPerente", "annoPerente");
		MAP_ImpegnoVO_TO_Impegno.put("annoProvvedimento", "annoprov");
		
		MAP_ImpegnoVO_TO_Impegno.put("", "artOrigine");
		MAP_ImpegnoVO_TO_Impegno.put("numeroCapitoloOrigine", "capOrigine");
		MAP_ImpegnoVO_TO_Impegno.put("cig", "cig");
		MAP_ImpegnoVO_TO_Impegno.put("codiceBeneficiario", "codben");
		
		MAP_ImpegnoVO_TO_Impegno.put("", "codIntervClass");
		MAP_ImpegnoVO_TO_Impegno.put("codiceProvvedimento", "codprov");
		MAP_ImpegnoVO_TO_Impegno.put("", "codtitgiu");
		MAP_ImpegnoVO_TO_Impegno.put("", "corrEntr");
		
		MAP_ImpegnoVO_TO_Impegno.put("cup", "cup");
		MAP_ImpegnoVO_TO_Impegno.put("dataAggiornamento", "dataagg");
		MAP_ImpegnoVO_TO_Impegno.put("dataInserimento", "dataemis");
		
		MAP_ImpegnoVO_TO_Impegno.put("dataProvvedimento", "dataprovv");
		MAP_ImpegnoVO_TO_Impegno.put("descCapitolo", "descrCapitolo");
		MAP_ImpegnoVO_TO_Impegno.put("descImpegno", "descri");
		MAP_ImpegnoVO_TO_Impegno.put("descTipologiaBeneficiario", "descTipoForn");
		
		MAP_ImpegnoVO_TO_Impegno.put("descBreveDirezioneProvvedimento", "direzione");
		MAP_ImpegnoVO_TO_Impegno.put("importoDisponibilitaLiquidare", "dispLiq");
		MAP_ImpegnoVO_TO_Impegno.put("totaleLiquidatoImpegno", "totaleliq"); 
		MAP_ImpegnoVO_TO_Impegno.put("importoAttualeImpegno", "impoatt");
		MAP_ImpegnoVO_TO_Impegno.put("importoInizialeImpegno", "impoini");
		
		MAP_ImpegnoVO_TO_Impegno.put("", "impoorig");
		MAP_ImpegnoVO_TO_Impegno.put("numeroImpegno", "nimp");
		MAP_ImpegnoVO_TO_Impegno.put("numeroProvvedimento", "nprov");
		
		MAP_ImpegnoVO_TO_Impegno.put("numeroArticoloCapitolo", "nroArticolo");
		MAP_ImpegnoVO_TO_Impegno.put("numeroCapitolo", "nroCapitolo");
		MAP_ImpegnoVO_TO_Impegno.put("numeroPerente", "nroPerente");
		MAP_ImpegnoVO_TO_Impegno.put("", "plurienn");
		
		MAP_ImpegnoVO_TO_Impegno.put("provenienzaCapitolo", "provCap");
		MAP_ImpegnoVO_TO_Impegno.put("ragioneSocialeBeneficiario", "ragSoc");
		MAP_ImpegnoVO_TO_Impegno.put("descBreveStatoImpegno", "staoper");
		MAP_ImpegnoVO_TO_Impegno.put("descBreveTipoFondo", "tipoFondo");
		
		MAP_ImpegnoVO_TO_Impegno.put("tipologiaBeneficiario", "tipoforn");
		MAP_ImpegnoVO_TO_Impegno.put("", "totaleLiq");
		MAP_ImpegnoVO_TO_Impegno.put("importoTotaleQuietanzato", "totalePagato");
		MAP_ImpegnoVO_TO_Impegno.put("tipologiaTrasferimento", "trasfTipo");
		
		MAP_ImpegnoVO_TO_Impegno.put("voceEconomicaTrasferimento", "trasfVoce");
		MAP_ImpegnoVO_TO_Impegno.put("utente", "utente");
		MAP_ImpegnoVO_TO_Impegno.put("descBreveTipoProvvedimento", "tipoProv");
		MAP_ImpegnoVO_TO_Impegno.put("numeroTotaleMandati", "countMand");
		
		MAP_ImpegnoVO_TO_Impegno.put("flagNoProvvedimento", "flagNoProv");
		MAP_ImpegnoVO_TO_Impegno.put("descBreveDirezioneDelegata", "direzioneDel");
		
		MAP_ImpegnoVO_TO_Impegno.put("flagPersonaFisica", "personaFisica");
		MAP_ImpegnoVO_TO_Impegno.put("partitaIva", "partitaIva");
		MAP_ImpegnoVO_TO_Impegno.put("codiceFiscale", "codiceFiscale");
		MAP_ImpegnoVO_TO_Impegno.put("descBreveEnteCapitolo", "direzioneCapitolo");
		
		
	}
	
	
	public static BidiMap MAP_IMPEGNODTO_TO_IMPEGNOVO = new TreeBidiMap();
	public static BidiMap MAP_IMPEGNOVO_TO_IMPEGNODTO = new TreeBidiMap();
	
	public static BidiMap MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO = new TreeBidiMap();
	public static BidiMap MAP_IMPEGNOBILANCIOVO_TO_IMPEGNODTO = new TreeBidiMap();
	
	public static BidiMap MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO = new TreeBidiMap();
	public static BidiMap MAP_BANDOLINEA_IMPEGNOVO_TO_IMPEGNODTO = new TreeBidiMap();
	
	public static BidiMap MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO = new TreeBidiMap();
	public static BidiMap MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO = new TreeBidiMap();
	
	public static BidiMap MAP_IMPEGNODTO_TO_PbandiWImpegniVO = new TreeBidiMap();
	public static BidiMap MAP_PbandiWImpegniVO_TO_IMPEGNODTO = new TreeBidiMap();
	
	
	static {
		// IMPEGNODTO <---> IMPEGNOVO
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("idImpegno", "idImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("annoImpegno","annoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("numeroImpegno","numeroImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("annoEsercizio","annoEsercizio");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("descImpegno","descImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("importoInizialeImpegno","importoInizialeImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("importoAttualeImpegno","importoAttualeImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("totaleLiquidatoImpegno","totaleLiquidatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("disponibilitaLiquidare","importoDisponibilitaLiquidare");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("totaleQuietanzatoImpegno","importoTotaleQuietanzato");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("dtInserimento","dataInserimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("dtAggiornamento","dataAggiornamento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("cupImpegno","cup");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("cigImpegno","cig");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("numeroCapitoloOrigine","numeroCapitoloOrigine");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("annoPerente","annoPerente");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("numeroPerente","numeroPerente");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("statoImpegno.idStatoImpegno","idStatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("statoImpegno.descBreveStatoImpegno","descBreveStatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("statoImpegno.descStatoImpegno","descStatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.idProvvedimento","idProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.annoProvvedimento","annoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.numeroProvvedimento","numeroProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.tipoProvvedimento.idTipoProvvedimento","idTipoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.tipoProvvedimento.descBreveTipoProvvedimento","descBreveTipoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.tipoProvvedimento.descTipoProvvedimento","descTipoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.enteCompetenza.idEnteCompetenza","idDirezioneProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.enteCompetenza.descEnte","descDirezioneProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.enteCompetenza.descBreveEnte","descBreveDirezioneProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.enteCompetenza.idTipoEnte","idTipoEnteProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.enteCompetenza.descTipoEnte","descTipoEnteProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.enteCompetenza.descBreveTipoEnte","descBreveTipoEnteProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.idCapitolo","idCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.numeroCapitolo","numeroCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.numeroArticolo","numeroArticoloCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.descCapitolo","descCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.enteCompetenza.idEnteCompetenza","idEnteCompetenzaCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.enteCompetenza.descEnte","descEnteCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.enteCompetenza.descBreveEnte","descBreveEnteCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.tipoFondo.idTipoFondo","idTipoFondo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.tipoFondo.descBreveTipoFondo","descBreveTipoFondo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.tipoFondo.descTipoFondo","descTipoFondo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("trasferimento.descBreveTipoTrasferimento","tipologiaTrasferimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("trasferimento.voceEconomica","voceEconomicaTrasferimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("importoInizialeImpegno","importoInizialeImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("numeroTotaleMandati","numeroTotaleMandati");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("capitolo.provenienza","provenienzaCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("beneficiario.descBreveTipologia","tipologiaBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("beneficiario.ragioneSociale","ragioneSocialeBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("beneficiario.codiceFiscale", "codiceFiscale");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("beneficiario.codice","codiceBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("beneficiario.descTipologia","descTipologiaBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("flagNoProvvedimento","flagNoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.dataProvvedimento","dataProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOVO.put("provvedimento.enteDelegata.descBreveEnte","descBreveDirezioneDelegata");
		MAP_IMPEGNOVO_TO_IMPEGNODTO = MAP_IMPEGNODTO_TO_IMPEGNOVO.inverseBidiMap();
		
		
		
		// IMPEGNODTO <--> IMPEGNO_BILANCIO_VO
//		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("idSoggetto", "idSoggetto");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("idImpegno", "idImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("annoImpegno","annoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("numeroImpegno","numeroImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("annoEsercizio","annoEsercizio");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("descImpegno","descImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("importoInizialeImpegno","importoInizialeImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("importoAttualeImpegno","importoAttualeImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("totaleLiquidatoImpegno","totaleLiquidatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("disponibilitaLiquidare","disponibilitaLiquidare");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("totaleQuietanzatoImpegno","totaleQuietanzatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("dtInserimento","dtInserimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("dtAggiornamento","dtAggiornamento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("cupImpegno","cupImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("cigImpegno","cigImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("numeroCapitoloOrigine","numeroCapitoloOrigine");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("annoPerente","annoPerente");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("numeroPerente","numeroPerente");		
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("codiceFiscale","codiceFiscale");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("ragsoc","ragsoc");		
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("statoImpegno.idStatoImpegno","idStatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("statoImpegno.descBreveStatoImpegno","descBreveStatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("statoImpegno.descStatoImpegno","descStatoImpegno");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.idProvvedimento","idProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.annoProvvedimento","annoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.numeroProvvedimento","numeroProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.dataProvvedimento","dtProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.tipoProvvedimento.idTipoProvvedimento","idTipoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.tipoProvvedimento.descBreveTipoProvvedimento","descBreveTipoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.tipoProvvedimento.descTipoProvvedimento","descTipoProvvedimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.enteCompetenza.idEnteCompetenza","idEnteCompetenzaProvv");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.enteCompetenza.descEnte","descEnteProvv");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.enteCompetenza.descBreveEnte","descBreveEnteProvv");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.enteCompetenza.idTipoEnte","idTipoEnteProvv");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.enteCompetenza.descTipoEnte","descTipoEnteProvv");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.enteCompetenza.descBreveTipoEnte","descBreveTipoEnteProvv");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("provvedimento.enteDelegata.descBreveEnte","descBreveDirezioneDelegata");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.idCapitolo","idCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.numeroCapitolo","numeroCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.numeroArticolo","numeroArticolo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.descCapitolo","descCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.enteCompetenza.idEnteCompetenza","idEnteCompetenzaCap");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.enteCompetenza.descEnte","descEnteCap");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.enteCompetenza.descBreveEnte","descBreveEnteCap");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.tipoFondo.idTipoFondo","idTipoFondo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.tipoFondo.descBreveTipoFondo","descBreveTipoFondo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.tipoFondo.descTipoFondo","descTipoFondo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("trasferimento.descBreveTipoTrasferimento","tipologiaTrasferimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("trasferimento.voceEconomica","voceEconomicaTrasferimento");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("numeroTotaleMandati","numeroTotaleMandati");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("capitolo.provenienza","provenienzaCapitolo");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("beneficiario.descBreveTipologia","tipologiaBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("beneficiario.ragioneSociale","ragioneSocialeBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("beneficiario.codice","codiceBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("beneficiario.descTipologia","descTipologiaBeneficiario");
		MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.put("flagNoProvvedimento","flagNoProvvedimento");
		MAP_IMPEGNOBILANCIOVO_TO_IMPEGNODTO = MAP_IMPEGNODTO_TO_IMPEGNOBILANCIO_VO.inverseBidiMap();
		
		
		
		
		
		
		
		// IMPEGNODTO <--> BANDOLINEA_IMPEGNOVO
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("idImpegno", "idImpegno");
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("descImpegno","descImpegno");
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("annoImpegno","annoImpegno");
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("numeroImpegno","numeroImpegno");
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("importoAttualeImpegno","importoAttualeImpegno");
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("capitolo.tipoFondo.idTipoFondo","idTipoFondo");
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("capitolo.tipoFondo.descTipoFondo","descTipoFondo");
		MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.put("disponibilitaLiquidare","disponibilitaLiquidare");
		MAP_BANDOLINEA_IMPEGNOVO_TO_IMPEGNODTO = MAP_IMPEGNODTO_TO_BANDOLINEA_IMPEGNOVO.inverseBidiMap();
		
		
		
		// IMPEGNODTO <--> PROGETTO_IMPEGNOVO
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("idImpegno", "idImpegno");
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("descImpegno","descImpegno");
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("annoImpegno","annoImpegno");
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("numeroImpegno","numeroImpegno");
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("importoAttualeImpegno","importoAttualeImpegno");
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("capitolo.tipoFondo.idTipoFondo","idTipoFondo");
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("capitolo.tipoFondo.descTipoFondo","descTipoFondo");
		MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.put("disponibilitaLiquidare","disponibilitaLiquidare");
		MAP_PROGETTO_IMPEGNOVO_TO_IMPEGNODTO = MAP_IMPEGNODTO_TO_PROGETTO_IMPEGNOVO.inverseBidiMap();
		
		
		
		// IMPEGNODTO <--> PBANDI_W_IMPEGNO_VO
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("annoEsercizio", "annoesercizio");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("annoImpegno", "annoimp");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("numeroImpegno", "nroimp");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("descImpegno", "descimpegno");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("statoImpegno.descBreveStatoImpegno", "stato");		
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("provvedimento.annoProvvedimento", "annoprovv");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("provvedimento.numeroProvvedimento", "nroprov");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("provvedimento.tipoProvvedimento.descBreveTipoProvvedimento", "tipoprov");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("provvedimento.dataProvvedimento", "dataprov");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("provvedimento.enteCompetenza.descBreveEnte", "direzione");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("provvedimento.enteDelegata.descBreveEnte", "direzionedel");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("capitolo.numeroCapitolo", "nrocapitolo");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("capitolo.descCapitolo", "descrcapitolo");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("capitolo.numeroArticolo", "nroarticolo");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("numeroCapitoloOrigine", "nrocapitoloorig");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("capitolo.tipoFondo.descBreveTipoFondo", "tipofondo");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("capitolo.provenienza", "provcap");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("importoInizialeImpegno", "importoiniziale");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("importoAttualeImpegno", "importoattuale");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("totaleLiquidatoImpegno", "totaleliq");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("disponibilitaLiquidare", "displiq");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("totaleQuietanzatoImpegno", "totalepagato");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("numeroTotaleMandati", "conteggiomandati");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("cupImpegno", "cup");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("cigImpegno", "cig");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("dtInserimento", "datains");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("dtAggiornamento", "dataagg");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("flagNoProvvedimento", "flagnoprov");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("annoPerente", "annoperente");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("numeroPerente", "nroperente");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.codice", "codben");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.codiceFiscale", "codFiscale");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.ragioneSociale", "ragsoc");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.tipologia", "tipoforn");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.descTipologia", "desctipoforn");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("trasferimento.descBreveTipoTrasferimento", "trasftipo");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("trasferimento.voceEconomica", "trasfvoce");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("capitolo.enteCompetenza.descBreveEnte", "direzioneprovenienzacapitolo");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.flagPersonaFisica", "flagPf");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.codiceFiscale", "codFiscale");
		MAP_IMPEGNODTO_TO_PbandiWImpegniVO.put("beneficiario.partitaIva", "pIva");

		MAP_PbandiWImpegniVO_TO_IMPEGNODTO = MAP_IMPEGNODTO_TO_PbandiWImpegniVO.inverseBidiMap();
	}
	
	
	
	

	
	// Servizio della vecchia pa\pd di Contabilia non usato.
	// public abstract ConsultaAttiLiquidazioneVO consultaAttiLiquidazione(AttoVO attoVO,Long idUtente);
	
	public abstract ConsultaBeneficiariVO consultaBeneficiari(String codiceFiscale,String pIva, Long maxRec,Long idUtente);

	public abstract List<ImpegnoVO> consultaImpegni(List<ImpegnoKeyVO> impegniFilter, List<ProvvedimentoKeyVO> provvedimentiFilter, 
			List<CapitoloKeyVO> capitoliFilter, Long annoEsercizio, Long numMaxRecord,Long idUtente) throws Exception;

	public abstract InserisciAttoLiquidazioneVO inserisciAttoLiquidazione(InserisciAttoLiquidazioneVO consultaAttoLiquidazioneVO,Long idUtente);

	public abstract OutputConsultaAttoLiquidazione consultaAttoLiquidazione(BigDecimal idAttoLiquidazione,Long idUtente);
	
	public abstract EsitoLeggiStatoElaborazioneDocDTO leggiStatoElaborazioneDoc (InputLeggiStatoElaborazioneDocDTO input, Long idUtente);

}
