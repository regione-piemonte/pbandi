/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.SecurityHelper;
import it.csi.pbandi.pbservizit.business.api.DatiGeneraliAttivitaPregresseApi;
import it.csi.pbandi.pbservizit.dto.EsitoOperazioni;
import it.csi.pbandi.pbservizit.dto.datiprogetto.Bando;
import it.csi.pbandi.pbservizit.dto.datiprogetto.Beneficiario;
import it.csi.pbandi.pbservizit.dto.datiprogetto.DatiCumuloDeMinimis;
import it.csi.pbandi.pbservizit.dto.datiprogetto.DatiGenerali;
import it.csi.pbandi.pbservizit.dto.datiprogetto.EconomiaPerDatiGenerali;
import it.csi.pbandi.pbservizit.dto.datiprogetto.ImportoAgevolato;
import it.csi.pbandi.pbservizit.dto.datiprogetto.ImportoDescrizione;
import it.csi.pbandi.pbservizit.dto.datiprogetto.Progetto;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.AttivitaPregresseDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.EconomiaPerDatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.ImportoAgevolatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
//import it.csi.pbandi.pbwebrce.dto.Beneficiario;
//import it.csi.pbandi.pbwebrce.dto.DatiGenerali;
//import it.csi.pbandi.pbwebrce.dto.EconomiaPerDatiGenerali;
//import it.csi.pbandi.pbwebrce.dto.Progetto;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.Bando;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.ImportoAgevolato;
//import it.csi.pbandi.pbwebrce.pbandiutil.common.NumberUtil;
//import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;

@Service
public class DatiGeneraliAttivitaPregresseImpl implements DatiGeneraliAttivitaPregresseApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	private SecurityHelper springSecurityHelper;
	
	@Autowired
	private ProfilazioneBusinessImpl profilazioneSrv;
	
	@Override
	public Response getDatiGenerali(HttpServletRequest req, Long idProgetto)
			throws UtenteException, FileNotFoundException, IOException, Exception {
		String prf = "[DatiGeneraliAttivitaPregresseImpl::getDatiGenerali]";
		LOG.debug(prf + " BEGIN");
		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			LOG.info(prf +" caricaDatiGenerali OK");
			
			DatiGenerali dati = new DatiGenerali();
			
			Bando b = new Bando();
			b.setIdBando(datiGenerali.getIdBandoLinea());
			b.setDescrizione(datiGenerali.getDescrizioneBando());
			b.setTitolo(datiGenerali.getTitoloBando());
			dati.setBando(b);
			LOG.info(prf +" IdBandoLinea() = "+datiGenerali.getIdBandoLinea());
			LOG.info(prf + " datiGenerali.getIdTipoOperazione() = "+datiGenerali.getIdTipoOperazione());
			
			Progetto p = new Progetto();
			p.setAcronimo(datiGenerali.getAcronimo());
			p.setId(idProgetto);
			p.setCodice(datiGenerali.getCodiceProgetto());
			p.setCup(datiGenerali.getCup());
			p.setImportoAgevolato(datiGenerali.getImportoAgevolato());
			p.setTitolo(datiGenerali.getTitoloProgetto());
			p.setDtConcessione(datiGenerali.getDtConcessione());
			dati.setProgetto(p);
			
		
			Beneficiario beneficiario = new Beneficiario();
			beneficiario.setIdSoggetto(userInfo.getBeneficiarioSelezionato().getIdBeneficiario()); //?
			beneficiario.setDescrizione(datiGenerali.getDescrizioneBeneficiario());
			beneficiario.setIdDimensioneImpresa(datiGenerali.getIdDimensioneImpresa());
			beneficiario.setIdFormaGiuridica(datiGenerali.getIdFormaGiuridica());
			
			//beneficiario.setIdDimensioneImpresa(datiSrv.get)
			// FIXME dovrebbe arrivare dal servizio e non dal dato in sessione
			beneficiario.setCodiceFiscale(userInfo.getBeneficiarioSelezionato().getCodiceFiscale());
			beneficiario.setSede(datiGenerali.getSedeIntervento());
			beneficiario.setPIvaSedeIntervento(datiGenerali.getPIvaSedeIntervento());
			beneficiario.setSedeLegale(datiGenerali.getSedeLegale());
			beneficiario.setPIvaSedeLegale(datiGenerali.getPIvaSedeLegale());
			beneficiario.setIsCapofila(datiGenerali.getIsCapofila());
			
			beneficiario.setIsCapofila(datiGenerali.getIsCapofila());
			dati.setBeneficiario(beneficiario);
			//dati.setDescrizioneBreve(messageManager.getMessage(MessageKey.FILO_ARIANNA, datiSrv.getTitoloProgetto(), datiSrv.getDescrizioneBando()));
			dati.setDataPresentazione(datiGenerali.getDtPresentazioneDomanda());
			
			ImportoAgevolatoDTO importoAgevolatoDTO[]= datiGenerali.getImportiAgevolati();
			List <ImportoAgevolato>list=new ArrayList<ImportoAgevolato>();
			if(!ObjectUtil.isEmpty(importoAgevolatoDTO)){
				for (ImportoAgevolatoDTO dtoServer : importoAgevolatoDTO) {
					ImportoAgevolato importoAgevolato=new ImportoAgevolato(); 
					importoAgevolato.setDescrizione(dtoServer.getDescrizione());
					importoAgevolato.setImporto(dtoServer.getImporto());
					importoAgevolato.setImportoAlNettoRevoche(dtoServer.getImportoAlNettoRevoche());
					list.add(importoAgevolato);
				}
				dati.setImportiAgevolati(new ArrayList<ImportoAgevolato>(list));
			}
			Double importoCertificatoNettoUltimoPropAmm = datiGenerali.getImportoCertificatoNettoUltimaPropAppr();
			//dati.setImportoCertificatoNettoUltimaPropAppr(Constants.VALUE_IMPORTO_ULTIMO_CERTIFICATO_NON_PRESENTE);
			if(importoCertificatoNettoUltimoPropAmm!=null){
				dati.setImportoCertificatoNettoUltimaPropAppr(importoCertificatoNettoUltimoPropAmm);
			}	

			if (profilazioneSrv.verifyCurrentUserForUC(userInfo.getCodiceRuolo() ,UseCaseConstants.UC_TRSWKS005_1)) {
				dati.setFlagImportoCertificatoNettoVisibile("S");
			}			

			Double importoValidatoNettoRevoche = datiGenerali.getImportoValidatoNettoRevoche();
			if(importoValidatoNettoRevoche!=null){
				dati.setImportoValidatoNettoRevoche(importoValidatoNettoRevoche);
			}	
			Double importoSoppressioni = datiGenerali.getImportoSoppressioni();
			if(importoSoppressioni!=null){
				dati.setImportoSoppressioni(importoSoppressioni);
			}	
			dati.setErogazioni(getImportoDescrizioni(datiGenerali.getErogazioni()));
			dati.setPreRecuperi(getImportoDescrizioni(datiGenerali.getPreRecuperi()));
			dati.setRecuperi(getImportoDescrizioni(datiGenerali.getRecuperi()));
			dati.setRevoche(getImportoDescrizioni(datiGenerali.getRevoche()));
			
			if(datiGenerali.getImportoImpegno() != null){
				dati.setImportoImpegno(datiGenerali.getImportoImpegno());
				LOG.info( prf + "getImportoImpegno() = "+datiGenerali.getImportoImpegno()+"; dati.getImportoImpegno() = "+dati.getImportoImpegno());
			}
			
			if(datiGenerali.getImportoRendicontato() != null){
				dati.setImportoRendicontato(datiGenerali.getImportoRendicontato());
			}
			
			if(datiGenerali.getImportoQuietanzato() != null){
				dati.setImportoQuietanzato(datiGenerali.getImportoQuietanzato());
			}
			
			if(datiGenerali.getImportoCofinanziamentoPubblico() != null){
				dati.setImportoCofinanziamentoPubblico(datiGenerali.getImportoCofinanziamentoPubblico());
			}
			
			if(datiGenerali.getImportoCofinanziamentoPrivato() != null){
				dati.setImportoCofinanziamentoPrivato(datiGenerali.getImportoCofinanziamentoPrivato());
			}
			
			if(datiGenerali.getImportoAmmesso() != null){
				dati.setImportoAmmesso(datiGenerali.getImportoAmmesso());
			}
			LOG.info(prf + " ImportoAmmesso() = "+datiGenerali.getImportoAmmesso()+"; dati.getImportoAmmesso() = "+dati.getImportoAmmesso());
			
			dati.setDatiCumuloDeMinimis(getDatiCumuloDeMinimis(userInfo));
			
			/*
			 * Calcolo del residuo ammesso come la differenza
			 * tra   l' ammesso a finaziamento ed il totale rendicontato
			 */
			Double totaleRendicontato = dati.getImportoRendicontato() == null ? 0D : dati.getImportoRendicontato();
			LOG.info(prf + " totaleRendicontato = "+ totaleRendicontato);
			
			Double importoAmmesso = dati.getImportoAmmesso() == null ? 0D : dati.getImportoAmmesso();
			
			Double importoResiduoAmmesso = NumberUtil.subtract(importoAmmesso, totaleRendicontato);
			
			dati.setImportoResiduoAmmesso(NumberUtil.getStringValueItalianFormat(importoResiduoAmmesso));
			
			/*
			 * FIX PBandi-2307. Verifico se il progetto e' legato al bilancio ( presenza della regola BR37
			 */
			if (profilazioneSrv.isRegolaApplicabileForBandoLinea(
					idUtente, 
					idIride, 
					b.getIdBando(), 
					RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO)) {
				dati.setIsLegatoBilancio(Boolean.TRUE);
			} else {
				dati.setIsLegatoBilancio(Boolean.FALSE);
			}
			LOG.info(prf + " impostato  IsLegatoBilancio = " + dati.getIsLegatoBilancio());
			
			// Economie.
			if (datiGenerali.getEconomie() == null) {
				dati.setEconomie(new ArrayList<EconomiaPerDatiGenerali>());
			} else {
				ArrayList<EconomiaPerDatiGenerali> economie = new ArrayList<EconomiaPerDatiGenerali>();
				for(EconomiaPerDatiGeneraliDTO eDTO : datiGenerali.getEconomie()){
					EconomiaPerDatiGenerali e = new EconomiaPerDatiGenerali(); 
					e.setDescBreveSoggFinanziatore(eDTO.getDescBreveSoggFinanziatore());
					e.setImpQuotaEconSoggFinanziat(eDTO.getImpQuotaEconSoggFinanziat());
					economie.add(e);
				}
				dati.setEconomie(economie);
			}
			
			dati.setImpTotBanca(datiGenerali.getImpTotBanca());
			
			if(datiGenerali == null) {
				LOG.warn(prf + " datiGenerali == null, inviaErroreInternalServer");
				return inviaErroreInternalServer("Errore durante il recupero dei dati generali.");
			}
					
			LOG.debug(prf + " END");
			return Response.ok().entity(dati).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public Response getAttivitaPregresse(HttpServletRequest req, Long idProgetto)
			throws UtenteException, FileNotFoundException, IOException, Exception {
		String prf = "[DatiGeneraliAttivitaPregresseImpl::getAttivitaPregresse]";
		LOG.debug(prf + " BEGIN");
		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			AttivitaPregresseDTO[] dto = gestioneDatiGeneraliBusinessImpl.caricaAttivitaPregresse(userInfo, idProgetto);
			if(dto == null) {
				LOG.warn(prf + " AttivitaPregresseDTO[] == null, inviaErroreInternalServer");
				return inviaErroreInternalServer("Errore durante il recupero delle attivita pregresse.");
			}
					
			LOG.debug(prf + " END");
			return Response.ok().entity(dto).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			throw e;
		}
	}

	private ArrayList<ImportoDescrizione> getImportoDescrizioni(ImportoDescrizioneDTO[] importoDescrizioneDTO){
		ArrayList<ImportoDescrizione> listaImportoDescrizioni = new ArrayList<ImportoDescrizione>();
		if(importoDescrizioneDTO != null){
			for(ImportoDescrizioneDTO importoDescrizioneDto : importoDescrizioneDTO){
				ImportoDescrizione importoDescrizione = new ImportoDescrizione();
				importoDescrizione.setDescBreve(importoDescrizioneDto.getDescBreve());
				importoDescrizione.setDescrizione(importoDescrizioneDto.getDescrizione());
				importoDescrizione.setImporto(importoDescrizioneDto.getImporto());
				listaImportoDescrizioni.add(importoDescrizione);
			}
		}
		return listaImportoDescrizioni;
	}

	private DatiCumuloDeMinimis getDatiCumuloDeMinimis(UserInfoSec userInfo) throws Exception {	
		DatiCumuloDeMinimis datiCumuloDeMinimis = new DatiCumuloDeMinimis();
		return datiCumuloDeMinimis;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// METODI DI RESPONSE HTTP /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}
	
	private Response inviaErroreInternalServer(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}
}
