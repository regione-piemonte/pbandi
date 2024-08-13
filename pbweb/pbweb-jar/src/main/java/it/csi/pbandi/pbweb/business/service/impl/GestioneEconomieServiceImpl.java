/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.GestioneEconomieService;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.gestioneEconomica.BeneficiarioQuoteDTO;
import it.csi.pbandi.pbweb.dto.gestioneEconomica.ModificaDTO;
import it.csi.pbandi.pbweb.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.BandoLinea;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.EconomiaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.EsitoOperazioneEconomia;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.ParametriRicercaEconomieDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.ProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.QuotaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoReportDettaglioDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestioneeconomie.GestioneEconomieSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbweb.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class GestioneEconomieServiceImpl implements GestioneEconomieService {
	
private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private GestioneEconomieSrv gestioneEconomieSrv;
	
	@Autowired
	private GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	private ProfilazioneBusinessImpl profilazioneBusinessImpl;

	@Override
	public Response getEconomie(Long idU, ParametriRicercaEconomieDTO parametriRicerca, HttpServletRequest req) throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::cercaDirezioneRicercaImpegni]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", parametriRicerca ="+parametriRicerca.toString());
		
		EconomiaDTO[] economiaDTO;
		
		try {
			economiaDTO = gestioneEconomieSrv.findEconomie2(idU, getIride(req), parametriRicerca);
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(economiaDTO).build();
		
	}
	
	
	@Override
	public Response deleteEconomia(Long idU, Long idEconomia, HttpServletRequest req) throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::deleteEconomia]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idEconomia ="+idEconomia);
		
		EsitoOperazioneEconomia esitoOperazioneEconomia = null;
		
		try {
			esitoOperazioneEconomia = gestioneEconomieSrv.eliminaEconomia(idU, getIride(req), idEconomia);
		}catch (Exception e) {
			LOG.error(prf + e);	
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneEconomia).build();
		
	}
	

	@Override
	public Response modifica(Long idU, EconomiaDTO filtro, HttpServletRequest req) throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::modifica]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", filtro = "+filtro.toString());
		
		QuotaDTO[] quoteProgettoCedente;
		QuotaDTO[] quoteEconomiaCedente;
		QuotaDTO[] quoteProgettoRicevente;
		QuotaDTO[] quoteEconomiaRicevente;
		BandoLinea[] bandoLinea;
		EconomiaDTO[] economiaDTO;
		ProgettoDTO[] progettiBandoLinea;
		DatiGeneraliDTO datiGeneraliDTO;
		ModificaDTO modificaDTO = new ModificaDTO();
		ResponseCodeMessage responseCodeMessage = new ResponseCodeMessage();
		
		try {
			
			economiaDTO = gestioneEconomieSrv.findEconomie(idU,  getIride(req), filtro);
			modificaDTO.setEconomiaDTO(economiaDTO);
			datiGeneraliDTO = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idU, getIride(req), economiaDTO[0].getIdProgettoCedente(), economiaDTO[0].getIdBeneficiarioCedente());
			modificaDTO.setDatiGeneraliDTO(datiGeneraliDTO);
			Double economieGiaCedute = gestioneEconomieSrv.getImportoCedutoProgetto(idU, getIride(req), economiaDTO[0].getIdProgettoCedente(), economiaDTO[0].getIdEconomia());
			LOG.debug(prf + "importoCedutoProgetto = "+economieGiaCedute);
			modificaDTO.setEconomieGiaCedute(economieGiaCedute);
			
			boolean importoOK = checkImportoCeduto(datiGeneraliDTO, economieGiaCedute,  economiaDTO[0].getImportoCeduto());
			if(importoOK) {
				
				//caricaComboBandiPerRegola
				bandoLinea = gestioneEconomieSrv.findBandoLineaDaRegola(idU, getIride(req), "BR46");
				modificaDTO.setBandoLinea(bandoLinea);
				progettiBandoLinea = gestioneEconomieSrv.findProgettiByBandoLinea(idU, getIride(req), datiGeneraliDTO.getIdBandoLinea());
				modificaDTO.setProgettiBandoLinea(progettiBandoLinea);
				//economia.setImportoMassimoCedibile(importoAmmesso - importoValidato - importoTotaleCeduto);
				modificaDTO.setImportoMaxCedibile( datiGeneraliDTO.getImportoAmmesso() - datiGeneraliDTO.getImportoValidatoNettoRevoche() - modificaDTO.getEconomieGiaCedute() );
				
				quoteProgettoCedente = gestioneEconomieSrv.findQuoteProgetto(idU, getIride(req), economiaDTO[0].getIdProgettoCedente());
				modificaDTO.setQuoteProgettoCedente(quoteProgettoCedente);
				quoteEconomiaCedente = gestioneEconomieSrv.findQuoteEconomia(idU, getIride(req), economiaDTO[0].getIdProgettoCedente(), filtro.getIdEconomia(), "C");
				modificaDTO.setQuoteEconomiaCedente(quoteEconomiaCedente);
				if(economiaDTO[0].getIdProgettoRicevente() != null && economiaDTO[0].getIdProgettoRicevente() > 0 ) {
					quoteProgettoRicevente = gestioneEconomieSrv.findQuoteProgetto(idU, getIride(req), economiaDTO[0].getIdProgettoRicevente());
					modificaDTO.setQuoteProgettoRicevente(quoteProgettoRicevente);
					quoteEconomiaRicevente = gestioneEconomieSrv.findQuoteEconomia(idU, getIride(req), economiaDTO[0].getIdProgettoRicevente(), filtro.getIdEconomia(), "R");
					modificaDTO.setQuoteEconomiaRicevente(quoteEconomiaRicevente);
				}
				
				
				
			}else {
				LOG.debug(prf + "Importo ceduto maggiore della somma stabilita");
				responseCodeMessage.setMessage("Importo ceduto maggiore della somma stabilita");
				modificaDTO.setResponseCodeMessage(responseCodeMessage);
			}
			
			
		}catch (Exception e) {
			LOG.error(prf + e);
			economiaDTO = new EconomiaDTO[1];
			responseCodeMessage.setCode("ERRORE");
			responseCodeMessage.setMessage("Errore: " +e);
			modificaDTO.setResponseCodeMessage(responseCodeMessage);
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(modificaDTO).build();
		
	}
	
	
	@Override
	public Response getBandi(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::getBandi]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		BandoLinea[] bandoLinea;
		
		try {
			//caricaComboBandiPerRegola
			bandoLinea = gestioneEconomieSrv.findBandoLineaDaRegola(idU, getIride(req), "BR46");
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(bandoLinea).build();
		
	}
	
	
	@Override
	public Response getProgettiByBando(Long idU, Long idBandoLinea, HttpServletRequest req) throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::getProgettiByBando]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idBandoLinea = "+idBandoLinea);
		
		ProgettoDTO[] progettoDTO;
		
		try {
			progettoDTO = gestioneEconomieSrv.findProgettiByBandoLinea(idU, getIride(req), idBandoLinea);
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(progettoDTO).build();
		
	}
	
	
	@Override
	public Response cambiaProgetto(Long idU, Long idProgetto, Long idEconomia, Long idSoggettoBeneficiario, String tipologiaProgetto, HttpServletRequest req)throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::getBeneficiarioQuoteByIdProgetto]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto = "+idProgetto+", idEconomia = "+idEconomia+", "
				                                      + "tipologiaProgetto = "+tipologiaProgetto+", idSoggettoBeneficiario = "+idSoggettoBeneficiario);
		
		BeneficiarioQuoteDTO beneficiarioQuoteDTO = new BeneficiarioQuoteDTO();
		
		try {
			
			beneficiarioQuoteDTO.setBeneficiario( profilazioneBusinessImpl.findBeneficiariProgetto(idU, getIride(req), idProgetto) );
			beneficiarioQuoteDTO.setQuotaDTO( gestioneEconomieSrv.findQuoteProgetto(idU, getIride(req), idProgetto) );
			beneficiarioQuoteDTO.setQuotaEconomiaDTO( gestioneEconomieSrv.findQuoteEconomia(idU, getIride(req), idProgetto, idEconomia, tipologiaProgetto) );
			
			if(tipologiaProgetto.equalsIgnoreCase("C")) {
				beneficiarioQuoteDTO.setImportoCeduto( gestioneEconomieSrv.getImportoCedutoProgetto(idU, getIride(req), idProgetto, idEconomia) );
				DatiGeneraliDTO datiGeneraliDTO = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idU, getIride(req), idProgetto, idSoggettoBeneficiario);
				beneficiarioQuoteDTO.setImportoMaxCedibile( datiGeneraliDTO.getImportoAmmesso() - datiGeneraliDTO.getImportoValidatoNettoRevoche() - beneficiarioQuoteDTO.getImportoCeduto() );
				beneficiarioQuoteDTO.setEconomieGiaCedute( gestioneEconomieSrv.getImportoCedutoProgetto(idU, getIride(req), idProgetto, idEconomia ) );
			}
			
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(beneficiarioQuoteDTO).build();
		
	}
	
	
	@Override
	public Response salvaAggiornamentiEconomia(Long idU, ModificaDTO modificaDTO, HttpServletRequest req)throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::salavaAggiornamentiEconomia]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", economia ="+modificaDTO.toString());
		
		EsitoOperazioneEconomia esitoOperazioneEconomia = null;
		
		try {
			
			esitoOperazioneEconomia = gestioneEconomieSrv.saveUpdateEconomia(idU, getIride(req), modificaDTO.getEconomiaDTO()[0]);
			esitoOperazioneEconomia = gestioneEconomieSrv.modificaQuoteProgetto(idU, getIride(req), modificaDTO.getQuoteProgettoCedente(), modificaDTO.getQuoteProgettoRicevente());
			
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneEconomia).build();
		
	}
	
	
	@Override
	public Response salvaNuovaEconomia(Long idU, ModificaDTO modificaDTO, HttpServletRequest req) throws Exception {
		String prf = "[GestioneEconomieServiceImpl::infoProgetto]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", economia ="+modificaDTO.toString());
		
		EsitoOperazioneEconomia esitoOperazioneEconomia = null;
		
		try {
			
			esitoOperazioneEconomia = gestioneEconomieSrv.saveUpdateEconomia(idU, getIride(req), modificaDTO.getEconomiaDTO()[0]);
			esitoOperazioneEconomia = gestioneEconomieSrv.saveQuoteProgetto(idU, getIride(req), modificaDTO.getQuoteEconomiaCedente(), modificaDTO.getQuoteEconomiaRicevente(), 
					                                                        esitoOperazioneEconomia.getIdEconomia());

			
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoOperazioneEconomia).build();
	}
	
	
	@Override
	public Response infoProgetto(Long idU, Long idProgetto, HttpServletRequest req) throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::infoProgetto]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto ="+idProgetto);
		
		DatiGeneraliDTO datiGeneraliDTO = null;
		
		try {
			datiGeneraliDTO = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idU, getIride(req), idProgetto, null);
		}catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(datiGeneraliDTO).build();
		
	}
	
	
	@Override
	public Response reportDettaglioEconomia(Long idU, ParametriRicercaEconomieDTO parametriRicercaEconomieDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[GestioneEconomieServiceImpl::reportDettaglioEconomia]";
		LOG.debug(prf + " BEGIN");
		LOG.debug(prf + " Parametri passati in input: idU = "+idU+"; parametriRicercaEconomieDTO = "+parametriRicercaEconomieDTO);
		
		EconomiaDTO[] elementiReportArray = gestioneEconomieSrv.findEconomie2(idU, getIride(req), parametriRicercaEconomieDTO);
		
		List<EconomiaDTO> elementiReport = new ArrayList<EconomiaDTO>(Arrays.asList(elementiReportArray));
		
		EsitoReportDettaglioDocumentiDiSpesaDTO esitoSrv = null;
		if (elementiReport != null || elementiReport.size() > 0) {
			
			byte[] reportDettaglioDocumentiSpesaFileData = TableWriter
					.writeTableToByteArray(
							"reportEconomia",
							new ExcelDataWriter(parametriRicercaEconomieDTO.getFiltro()), elementiReport);

			String nomeFile = "reportEconomie.xls";

			esitoSrv = new EsitoReportDettaglioDocumentiDiSpesaDTO();
			esitoSrv.setExcelBytes(reportDettaglioDocumentiSpesaFileData);
			esitoSrv.setNomeFile(nomeFile);
		}
		
		if (esitoSrv == null || esitoSrv.getExcelBytes() == null || esitoSrv.getExcelBytes().length == 0) {
			throw new Exception("Report non generato.");
		}
		
		EsitoLeggiFile esito = new EsitoLeggiFile();
		esito.setNomeFile(esitoSrv.getNomeFile());
		esito.setBytes(esitoSrv.getExcelBytes());
		
		LOG.debug(prf + " END");
		
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();

	}
	
	
//	@Override
//	public Response gestioneQuote(Long idU, Long idProgetto, Long idEconomia, String tipologiaProgetto, HttpServletRequest req) throws Exception {
//		
//		String prf = "[GestioneEconomieServiceImpl::gestioneQuoteProgettoRicevente]";
//		LOG.debug(prf + " BEGIN");
//		
//		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto ="+idProgetto+", tipologiaProgetto = "+tipologiaProgetto);
//		
//		ModificaDTO modificaDTO = new ModificaDTO();
//		
//		try {
//			modificaDTO = getQuote(idU, idProgetto, idEconomia, tipologiaProgetto, req);
//		}catch (Exception e) {
//			LOG.error(prf + e);
//			
//		}
//		
//		LOG.debug(prf + " END");
//		
//		return Response.ok().entity(modificaDTO).build();
//		
//	}
//
//	
//	private ModificaDTO getQuote(Long idU, Long idProgetto, Long idEconomia, String tipologiaProgetto, HttpServletRequest req) throws Exception {
//		
//		String prf = "[GestioneEconomieServiceImpl::getQuote]";
//		LOG.debug(prf + " BEGIN");
//		
//		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgetto ="+idProgetto+", tipologiaProgetto = "+tipologiaProgetto);
//		
//		QuotaDTO[] quoteProgetto;
//		QuotaDTO[] quoteEconomia;
//		ModificaDTO modificaDTO = new ModificaDTO();
//		
//		try {
//			quoteProgetto = gestioneEconomieSrv.findQuoteProgetto(idU, getIride(req), idProgetto);
//			quoteEconomia = gestioneEconomieSrv.findQuoteEconomia(idU, getIride(req), idProgetto, idEconomia, tipologiaProgetto);
//			modificaDTO.setQuoteProgetto(quoteProgetto);
//			modificaDTO.setQuoteEconomia(quoteEconomia);
//		}catch (Exception e) {
//			LOG.error(prf + e);
//			throw e;
//		}
//		
//		LOG.debug(prf + " END");
//		
//		return modificaDTO;
//		
//	}
	
	private boolean checkImportoCeduto(DatiGeneraliDTO datiGenerali, Double importoTotaleCeduto, Double importoCedutoProgetto ) throws Exception {
		
		boolean importoOK = false;
		
		if(datiGenerali != null){
			Double importoAmmesso = datiGenerali.getImportoAmmesso();
			Double importoRendicontato = datiGenerali.getImportoRendicontato();
			importoOK = (importoCedutoProgetto <= (importoAmmesso - importoRendicontato - importoTotaleCeduto));
			LOG.debug(" Importo Ammesso = " + importoAmmesso + " Importo Rendicontato = " + importoRendicontato + " Importo Totale Ceduto = " + importoTotaleCeduto);
			importoOK = true;
		}
		return importoOK;
	}

	private String getIride(HttpServletRequest req) {
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return userInfo.getIdIride();
	}

}
