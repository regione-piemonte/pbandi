/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.csi.bilsrvrp.cmpsrvrp.dto.bilancio.attiliquid.ConsultaAttoLiquidazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.consultazioneattibilancio.AttoDiLiquidazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandolineaEnteCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioAttoLiquidazioneListaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioAttoLiquidazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoEnteCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio.AnnoEsercizioBilancioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCBatchImpegniVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoAttoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRImpegnoBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRImpegnoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWAttiLiquidazioneDlVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWAttiLiquidazioneDmVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWAttiLiquidazioneDtVO;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;

public class BilancioManager {
	private GenericDAO genericDAO;
	private BeanUtil beanUtil;
	protected DecodificheManager decodificheManager;
	protected LoggerUtil logger;
	private String DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_BOZZA = "0";
	public LoggerUtil getLogger() {
		return logger;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
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

	public List<SoggettoEnteCompetenzaVO> findEntiCompetenzaSoggetto(
			Long idSoggetto, String descBreveTipoEnte,String ascOrder) {
		SoggettoEnteCompetenzaVO filtroVO = new SoggettoEnteCompetenzaVO();
		filtroVO.setIdSoggetto(NumberUtil.toBigDecimal(idSoggetto));
		filtroVO.setDescBreveTipoEnteCompetenz(descBreveTipoEnte);
		
		filtroVO.setAscendentOrder(ascOrder);
		
		List<SoggettoEnteCompetenzaVO> entiCompetenzaSoggetto = genericDAO.findListWhere(Condition.filterBy(filtroVO));
		return entiCompetenzaSoggetto;
	}
	
	public List<DettaglioAttoLiquidazioneListaVO> findAttiDiLiquidazioneManager(
			AttoDiLiquidazioneDTO filtro) {
		List<DettaglioAttoLiquidazioneListaVO> atti;

		DettaglioAttoLiquidazioneVO al = beanUtil.transform(filtro, DettaglioAttoLiquidazioneVO.class);
		al.setAscendentOrder("estremiAtto", "descStatoAtto", "codiceVisualizzatoProgetto", "denominazioneBeneficiarioBil");	
		Condition<DettaglioAttoLiquidazioneVO>  cond = Condition.filterBy(al);
		
		DettaglioAttoLiquidazioneVO notBozza=new DettaglioAttoLiquidazioneVO();
		notBozza.setDescBreveStatoAtto(DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_BOZZA);
		
		atti = genericDAO.findListWhereDistinct(new AndCondition<DettaglioAttoLiquidazioneVO>(Condition
				.filterBy(al), Condition.not(Condition
						.filterBy(notBozza))),
				DettaglioAttoLiquidazioneListaVO.class);
		
		return atti;
	}


	public PbandiDStatoAttoVO statoAttoBozza() {
		PbandiDStatoAttoVO statoAttoVO = new PbandiDStatoAttoVO();
		statoAttoVO.setDescBreveStatoAtto(it.csi.pbandi.pbweb.pbandiutil.common.Constants.STATO_ATTO_LIQUIDAZIONE_BOZZA);
		statoAttoVO = genericDAO.findSingleWhere(statoAttoVO);
		return statoAttoVO;
	}
	

	public PbandiDStatoAttoVO statoAttoCancellato() {
		PbandiDStatoAttoVO statoAttoVO = new PbandiDStatoAttoVO();
		statoAttoVO.setDescBreveStatoAtto(it.csi.pbandi.pbweb.pbandiutil.common.Constants.STATO_ATTO_LIQUIDAZIONE_CANCELLATO);
		statoAttoVO = genericDAO.findSingleWhere(statoAttoVO);
		return statoAttoVO;
	}
	
	public List<AnnoEsercizioBilancioVO> findAnniEsercizioForImpegni() {
		PbandiCBatchImpegniVO filterVO = new PbandiCBatchImpegniVO();
		filterVO.setDescendentOrder("annoEsercizio");
		List<AnnoEsercizioBilancioVO> list = genericDAO.findListWhereDistinct(Condition.filterBy(filterVO), AnnoEsercizioBilancioVO.class);
		return list;
	}
	
	public List<BandolineaEnteCompetenzaVO> findBandolineaForImpegni (BandolineaEnteCompetenzaVO filter) throws Exception {
		try {
			List<BandolineaEnteCompetenzaVO> result = new ArrayList<BandolineaEnteCompetenzaVO>();
			result = genericDAO.findListWhereDistinct(Condition.filterBy(filter), BandolineaEnteCompetenzaVO.class);
			return result;
		} catch (Exception e) {
			logger.error("Errore durante la ricerca dei bandolinea.", e);
			throw e;
		}
	}
	
	public List<BandoLineaProgettoVO> findProgettiForImpegni ( BandoLineaProgettoVO filter ) throws Exception {
		try {
			List<BandoLineaProgettoVO> result = new ArrayList<BandoLineaProgettoVO>();
			result = genericDAO.findListWhereDistinct(Condition.filterBy(filter), BandoLineaProgettoVO.class);
			return result;
		} catch (Exception e) {
			logger.error("Errore durante la ricerca dei progetti.", e);
			throw e;
			
		}
	}
	
	/**
	 * Popola le tabelle temporanee PBANDI_W_ATTI_LIQUIDAZIONE_DT/DM/DL con i
	 * dati contenuti nell'atto di liquidazione passato
	 * 
	 * @param attoLiquidazione
	 * @return ID di riferimento per richiamare la procedura PL-SQL o null in caso di errore
	 */
	public BigDecimal caricaAttoDiLiquidazione(
			PbandiWAttiLiquidazioneDtVO recDT,
			ArrayList<PbandiWAttiLiquidazioneDlVO> listaDL,
			ArrayList<PbandiWAttiLiquidazioneDmVO> listaDM) {
		try {
			
			// Nota: creo dei doppioni, poich� altrimenti le insert falliscono con errore
			//       "NON POSSO ESEGUIRE L'INSERT: PK NON VALIDA,CONTROLLARE SE E' STATA SETTATA LA SEQUENCE".

			PbandiWAttiLiquidazioneDtVO recDTnew = beanUtil.transform(recDT,PbandiWAttiLiquidazioneDtVO.class); 
		
			recDTnew = genericDAO.insert(recDTnew);
			logger.info("caricaAttoDiLiquidazione(): insert PbandiWAttiLiquidazioneDt eseguita: IdAttiLiquidazioneDt = "+recDTnew.getIdAttiLiquidazioneDt());
			
			logger.info("caricaAttoDiLiquidazione(): eseguo insert PbandiWAttiLiquidazioneDl: num record = "+listaDL.size());
			for (PbandiWAttiLiquidazioneDlVO recDL : listaDL) {
				PbandiWAttiLiquidazioneDlVO recDLnew = beanUtil.transform(recDL,PbandiWAttiLiquidazioneDlVO.class);
				recDLnew.setIdAttiLiquidazioneDt(recDTnew.getIdAttiLiquidazioneDt());
				genericDAO.insert(recDLnew);
			}
			
			logger.info("caricaAttoDiLiquidazione(): eseguo insert PbandiWAttiLiquidazioneDm: num record = "+listaDM.size());
			for (PbandiWAttiLiquidazioneDmVO recDM : listaDM) {
				PbandiWAttiLiquidazioneDmVO recDMnew = beanUtil.transform(recDM,PbandiWAttiLiquidazioneDmVO.class);
				recDMnew.setIdAttiLiquidazioneDt(recDTnew.getIdAttiLiquidazioneDt());
				genericDAO.insert(recDMnew);
			}
			
			return recDTnew.getIdAttiLiquidazioneDt();
			
		} catch (Exception e) {
			logger.error("caricaAttoDiLiquidazione(): errore nelle insert delle tabelle W\n"+e);
			return null;
		}		
	}
	/* versione precedente
	public BigDecimal caricaAttoDiLiquidazione(AttoLiquidazione attoLiquidazione) {
		PbandiWAttiLiquidazioneDtVO dt = beanUtil
				.transform(attoLiquidazione,
						PbandiWAttiLiquidazioneDtVO.class,
						MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO);
		dt.setTiporecord(it.csi.pbandi.pbweb.pbandisrv.util.Constants.TIPO_RECORD_DT);
		dt.setFlagOnline(Constants.FLAG_TRUE);
		try {
			dt = genericDAO.insert(dt);
		} catch (Exception e) {
			logger.error(
					"Errore nell'inserimento del DT per il caricamento dell'atto, impossibile continuare", e);
			return null;
		}
		for (DettaglioConsultazioneAtto dettaglio : attoLiquidazione
				.getDettagliAtto()) {
			PbandiWAttiLiquidazioneDlVO dl = beanUtil
					.transform(dettaglio,
							PbandiWAttiLiquidazioneDlVO.class,
							MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO);
			// }L{ campi aggiuntivi non del dettaglio
			dl.setAnnoprovv(dt.getAnnoatto());
			dl.setNroprov(dt.getNroatto());
			dl.setDirezione(dt.getDiratto());

			dl.setIdAttiLiquidazioneDt(dt.getIdAttiLiquidazioneDt());
			
			
			dl.setTiporecord(it.csi.pbandi.pbweb.pbandisrv.util.Constants.TIPO_RECORD_DL);
			dl.setFlagOnline(Constants.FLAG_TRUE);
			PbandiWAttiLiquidazioneDmVO dm = beanUtil
					.transform(dettaglio, PbandiWAttiLiquidazioneDmVO.class,
							MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO);
			dm.setIdAttiLiquidazioneDt(dt.getIdAttiLiquidazioneDt());
			dm.setTiporecord(it.csi.pbandi.pbweb.pbandisrv.util.Constants.TIPO_RECORD_DM);
			dm.setFlagOnline(Constants.FLAG_TRUE);
			try {
				genericDAO.insert(dl);
				genericDAO.insert(dm);
			} catch (Exception e) {
				logger.error(
						"Errore nell'inserimento di DL e DM per il caricamento dell'atto, si preferisce NON continuare",
						e);
				return null;
			}
		}
		
		return dt.getIdAttiLiquidazioneDt();
	}
	*/
	
	public boolean hasImpegniAssociatiProgetto (Long idProgetto) {
		boolean result = false;
		PbandiRImpegnoProgettoVO progettoFilterVO = new PbandiRImpegnoProgettoVO();
		progettoFilterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		List<PbandiRImpegnoProgettoVO> impegniProgetto = genericDAO.findListWhere(progettoFilterVO);
		if (!ObjectUtil.isEmpty(impegniProgetto)) 
			result = true;
		return result;
	}
	
	public boolean hasImpegniAssociatiBandolinea (Long progrBandolinea) {
		boolean result = false;
		PbandiRImpegnoBandoLineaVO bandolineaFilterVO = new PbandiRImpegnoBandoLineaVO();
		bandolineaFilterVO.setProgrBandoLineaIntervento(NumberUtil.toBigDecimal(progrBandolinea));
		List<PbandiRImpegnoBandoLineaVO> impegniBandolinea = genericDAO.findListWhere(bandolineaFilterVO);
		if (!ObjectUtil.isEmpty(impegniBandolinea)) 
			result = true;
		return result;
	}



	private static Map<String,String> MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO = new HashMap<String,String>();
	static {
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.altroDaSpec","testoaltro");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.annoAtto","annoatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataAnnullAtto","dataannulatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataComplAtto","datacomplatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataRicAtto","dataricatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataRichMod","datarichmod");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataRifiuto","datarifatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataAggAtto","dataaggatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataEmisAtto","dataemisatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dataScadenza","datascadenza");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.descri","descri");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.dirAtto","diratto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.flDichiaraz","fldichiaraz");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.flDocGiustif","fldocgiust");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.flEstrCopiaProv","flestrcopiaprov");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.flFatture","flfatture");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.importoAtto","importoatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.testoRichMod","testorichmod");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.note","note");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.nroAtto","nroatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.nroElencoAtto","nroelenco");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.aliqIrpef","aliqirpef");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.codAltraCassa","codaltracassa");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.codAttivita","codattivita");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.datoInps","datoinps");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.inpsAl","impsal");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.inpsAltraCassa","inpsaltracassa");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.inpsDal","inpsdal");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.irpNonSoggette","irpnonsoggette");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.rischioInail","rischioinail");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.tipoRitenuta","tiporitenuta");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.ritenuta.tipoSoggetto","tiposoggetto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.settoreAtto","settatto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("atto.stato","stato");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.bic","bic");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.capSede","capsede");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.cin","cin");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.codaccre","codaccre");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.cab","cab");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.abi","abi");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.codbenCedente","codbenCedente");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.codbenCeduto","codbenCeduto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.nroCC","nrocc");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.codfiscQuiet","codfiscquiet");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.comuneSede","comunesede");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.descrAbi","descabi");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.descrCab","desccab");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.descrCodAccre","desccodaccre");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.iban","iban");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.progBen","progmodpag");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.progbenCeduto","progbenCeduto");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.provSede","provsede");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.quiet","quietanzante");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.ragsocAgg","ragsocagg");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("beneficiario.viaSede","viasede");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.cap","cap");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.codben","codben");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.codfisc","codfisc");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.comune","comune");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.partiva","partiva");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.personaFisica","flagperfis");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.prov","prov");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.ragsoc","ragsoc");
		MAP_ConsultaAttoLiquidazioneDTO_TO_PbandiWAttiLiquidazioneDtVO.put("fornitore.via","via");
	}
	private static Map<String,String> MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO = new HashMap<String,String>();
	static {
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("annoEser", "annoeser");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("cigMand", "cigmand");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("cupMand", "cupmand");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("dataQuiet", "dataquiet");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("flagPign", "flagpign");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("importoMandLordo", "importomandlordo");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("importoMandNetto", "importomandnetto");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("importoQuiet", "importoquiet");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("importoRitenute", "importoritenute");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("nLiq", "nliq");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDmVO.put("nroMand", "nromand");
	}
	private static Map<String,String> MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO = new HashMap<String,String>();
	static {
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("annoBilRif", "annobilrif");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("annoEser", "annoeser");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("annoImp", "annoimp");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("annoProvv", "annoprovimp");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("cigLiq", "cigliq");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("cupLiq", "cupliq");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("dataAgg", "dataagg");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("direzione", "direzioneprovimp");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("importo", "importo");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("nLiq", "nliq");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("nLiqPrec", "nliqprec");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("nroArticolo", "nroarticolo");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("nroCapitolo", "nrocapitolo");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("nroImp", "nroimp");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("nroProv", "nroprov");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("nroProv", "nroprovimp");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("subImpegno", "subimpegno");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("tipoFondo", "tipofondo");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("tipoProv", "tipoprovimp");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("trasfTipo", "trasftipo");
		MAP_DettaglioConsultazioneAtto_TO_PbandiWAttiLiquidazioneDlVO.put("trasfVoce", "trasfvoce");

	}
	
	public Boolean isResultOk(
			ConsultaAttoLiquidazioneDTO consultaAttoLiquidazione) {
		if(consultaAttoLiquidazione.getResult() == null) {
			logger.error("BILANCIO : ERRORE : IL RISULTATO NON E' STATO CORRETTAMENTE POPOLATO");
			return false;
		}
		int resultCode = consultaAttoLiquidazione.getResultCode();
		if(resultCode == it.csi.pbandi.pbweb.pbandisrv.util.Constants.CONSULTAZIONE_ATTO_BILANCIO_CODICE_NESSUN_ERRORE) {
			return true;
		} else {
			String result = consultaAttoLiquidazione.getResultDesc();
			switch (resultCode) {
			case it.csi.pbandi.pbweb.pbandisrv.util.Constants.CONSULTAZIONE_ATTO_BILANCIO_CODICE_PROBLEMA_TECNICO:
				logger.error("BILANCIO : PROBLEMA TECNICO : " + result);
				break;
			case it.csi.pbandi.pbweb.pbandisrv.util.Constants.CONSULTAZIONE_ATTO_BILANCIO_CODICE_ANNO_ATTO_NON_VALIDO:
				logger.error("BILANCIO : ERRORE DATI : " + result);
				break;
			case it.csi.pbandi.pbweb.pbandisrv.util.Constants.CONSULTAZIONE_ATTO_BILANCIO_CODICE_ATTO_INESISTENTE:
				logger.error("BILANCIO : ERRORE DATI : " + result);
				break;
			case it.csi.pbandi.pbweb.pbandisrv.util.Constants.CONSULTAZIONE_ATTO_BILANCIO_CODICE_DIREZIONE_NON_VALIDA:
				logger.error("BILANCIO : ERRORE DATI : " + result);
				break;
			case it.csi.pbandi.pbweb.pbandisrv.util.Constants.CONSULTAZIONE_ATTO_BILANCIO_CODICE_ERRORE_ORACLE:
				logger.error("BILANCIO : ERRORE ORACLE : " + result);
				break;
			case it.csi.pbandi.pbweb.pbandisrv.util.Constants.CONSULTAZIONE_ATTO_BILANCIO_CODICE_NUMERO_ATTO_NON_VALIDO:
				logger.error("BILANCIO : ERRORE DATI : " + result);
				break;
			default:
				logger.error("BILANCIO : CODICE SCONOSCIUTO (" + resultCode + ") : " + result);
				break;
			}
		}
		return false;
	}
	
	private String objectToXML(final Object object) {
		String xml = "";		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			XMLEncoder xmlEncoder = new XMLEncoder(baos);
			xmlEncoder.writeObject(object);
			xmlEncoder.close();
			xml = baos.toString();
		} catch (Exception e) {
			xml = "Errore nella stampa dell'oggetto.";
		}
		return xml.toString();
	}

}
