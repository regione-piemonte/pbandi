/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.NotificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.business.SecurityHelper;
import it.csi.pbandi.pbwebrce.business.service.AffidamentiService;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.affidamenti.AffidamentoCheckListDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.DatiCumuloDeMinimis;
import it.csi.pbandi.pbwebrce.dto.affidamenti.DocumentoAllegato;
import it.csi.pbandi.pbwebrce.dto.affidamenti.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.FornitoreDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.ImportoDescrizione;
import it.csi.pbandi.pbwebrce.dto.affidamenti.MotiveAssenzaDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.NormativaAffidamentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.RigaTabellaAffidamenti;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipoAffidamentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAppaltoDTO;
import it.csi.pbandi.pbwebrce.integration.dao.AffidamentiDAO;
import it.csi.pbandi.pbwebrce.integration.dao.ArchivioFileDAO;
//import it.csi.pbandi.pbwebrce.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbwebrce.integration.request.AssociateFileRequest;
import it.csi.pbandi.pbwebrce.integration.request.RespingiAffiddamentoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaAffidamentoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaFornitoreRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaVarianteRequest;
import it.csi.pbandi.pbwebrce.integration.request.SubcontrattoRequest;
import it.csi.pbandi.pbwebrce.integration.request.VerificaAffidamentoRequest;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ParamInviaInVerificaDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbwebrce.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.DateUtil;

@Service
public class AffidamentiServiceImpl implements AffidamentiService {
	
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	AffidamentiDAO affidamentiDAO;
	
	@Autowired
	ArchivioFileDAO archivioFileDAO;
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	private ProfilazioneBusinessImpl profilazioneSrv;
	
	@Autowired
	private SecurityHelper springSecurityHelper;
	
	
	
	@Resource
	SessionContext sessionContext;
	private void setSessionContext(SessionContext sessionContext)
	{
		this.sessionContext = sessionContext;
	}


//	@Override
//	public Response getDatiGenerali(HttpServletRequest req, Long idProgetto)
//			throws UtenteException, FileNotFoundException, IOException, Exception {
//		String prf = "[AffidamentiServiceImpl::getDatiGenerali]";
//		LOG.debug(prf + " BEGIN");
//		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
//		
//		try {
//			HttpSession session = req.getSession();
//			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//			String idIride = userInfo.getIdIride();
//			
//			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
//			
//			DatiGenerali dati = new DatiGenerali();
//			
//			Bando b = new Bando();
//			b.setIdBando(datiGenerali.getIdBandoLinea());
//			b.setDescrizione(datiGenerali.getDescrizioneBando());
//			b.setTitolo(datiGenerali.getTitoloBando());
//			dati.setBando(b);
//			LOG.info(prf +" IdBandoLinea() = "+datiGenerali.getIdBandoLinea());
//			LOG.info("GestioneDatiGeneraliBusinessImpl :: PBANDIRCE - datiSrv.getIdTipoOperazione() = "+datiGenerali.getIdTipoOperazione());
//			
//			Progetto p = new Progetto();
//			p.setAcronimo(datiGenerali.getAcronimo());
//			p.setId(idProgetto);
//			p.setCodice(datiGenerali.getCodiceProgetto());
//			p.setCup(datiGenerali.getCup());
//			p.setImportoAgevolato(datiGenerali.getImportoAgevolato());
//			p.setTitolo(datiGenerali.getTitoloProgetto());
//			p.setDtConcessione(datiGenerali.getDtConcessione());
//			dati.setProgetto(p);
//			
//		
//			Beneficiario beneficiario = new Beneficiario();
//			beneficiario.setIdSoggetto(userInfo.getBeneficiarioSelezionato().getIdBeneficiario()); //?
//			beneficiario.setDescrizione(datiGenerali.getDescrizioneBeneficiario());
//			beneficiario.setIdDimensioneImpresa(datiGenerali.getIdDimensioneImpresa());
//			beneficiario.setIdFormaGiuridica(datiGenerali.getIdFormaGiuridica());
//			
//			//beneficiario.setIdDimensioneImpresa(datiSrv.get)
//			// FIXME dovrebbe arrivare dal servizio e non dal dato in sessione
//			beneficiario.setCodiceFiscale(userInfo.getBeneficiarioSelezionato().getCodiceFiscale());
//			beneficiario.setSede(datiGenerali.getSedeIntervento());
//			beneficiario.setPIvaSedeIntervento(datiGenerali.getPIvaSedeIntervento());
//			beneficiario.setSedeLegale(datiGenerali.getSedeLegale());
//			beneficiario.setPIvaSedeLegale(datiGenerali.getPIvaSedeLegale());
//			beneficiario.setIsCapofila(datiGenerali.getIsCapofila());
//			
//			beneficiario.setIsCapofila(datiGenerali.getIsCapofila());
//			dati.setBeneficiario(beneficiario);
//			//dati.setDescrizioneBreve(messageManager.getMessage(MessageKey.FILO_ARIANNA, datiSrv.getTitoloProgetto(), datiSrv.getDescrizioneBando()));
//			dati.setDataPresentazione(datiGenerali.getDtPresentazioneDomanda());
//			
//			ImportoAgevolatoDTO importoAgevolatoDTO[]= datiGenerali.getImportiAgevolati();
//			List <ImportoAgevolato>list=new ArrayList<ImportoAgevolato>();
//			if(!ObjectUtil.isEmpty(importoAgevolatoDTO)){
//				for (ImportoAgevolatoDTO dtoServer : importoAgevolatoDTO) {
//					ImportoAgevolato importoAgevolato=new ImportoAgevolato(); 
//					importoAgevolato.setDescrizione(dtoServer.getDescrizione());
//					importoAgevolato.setImporto(dtoServer.getImporto());
//					importoAgevolato.setImportoAlNettoRevoche(dtoServer.getImportoAlNettoRevoche());
//					list.add(importoAgevolato);
//				}
//				dati.setImportiAgevolati(new ArrayList<ImportoAgevolato>(list));
//			}
//			Double importoCertificatoNettoUltimoPropAmm = datiGenerali.getImportoCertificatoNettoUltimaPropAppr();
//			//dati.setImportoCertificatoNettoUltimaPropAppr(Constants.VALUE_IMPORTO_ULTIMO_CERTIFICATO_NON_PRESENTE);
//			if(importoCertificatoNettoUltimoPropAmm!=null){
//				dati.setImportoCertificatoNettoUltimaPropAppr(importoCertificatoNettoUltimoPropAmm);
//			}	
//
//			if (springSecurityHelper.verifyCurrentUserForUC(null ,UseCaseConstants.UC_TRSWKS005_1)) {
//				dati.setFlagImportoCertificatoNettoVisibile("S");
//			}
//
//			Double importoValidatoNettoRevoche = datiGenerali.getImportoValidatoNettoRevoche();
//			if(importoValidatoNettoRevoche!=null){
//				dati.setImportoValidatoNettoRevoche(importoValidatoNettoRevoche);
//			}	
//			Double importoSoppressioni = datiGenerali.getImportoSoppressioni();
//			if(importoSoppressioni!=null){
//				dati.setImportoSoppressioni(importoSoppressioni);
//			}	
//			dati.setErogazioni(getImportoDescrizioni(datiGenerali.getErogazioni()));
//			dati.setPreRecuperi(getImportoDescrizioni(datiGenerali.getPreRecuperi()));
//			dati.setRecuperi(getImportoDescrizioni(datiGenerali.getRecuperi()));
//			dati.setRevoche(getImportoDescrizioni(datiGenerali.getRevoche()));
//			
//			if(datiGenerali.getImportoImpegno() != null){
//				dati.setImportoImpegno(datiGenerali.getImportoImpegno());
//				LOG.info("caricaDatiGenerali(): PBANDIRCE - datiSrv.getImportoImpegno() = "+datiGenerali.getImportoImpegno()+"; dati.getImportoImpegno() = "+dati.getImportoImpegno());
//			}
//			
//			if(datiGenerali.getImportoRendicontato() != null){
//				dati.setImportoRendicontato(datiGenerali.getImportoRendicontato());
//			}
//			
//			if(datiGenerali.getImportoQuietanzato() != null){
//				dati.setImportoQuietanzato(datiGenerali.getImportoQuietanzato());
//			}
//			
//			if(datiGenerali.getImportoCofinanziamentoPubblico() != null){
//				dati.setImportoCofinanziamentoPubblico(datiGenerali.getImportoCofinanziamentoPubblico());
//			}
//			
//			if(datiGenerali.getImportoCofinanziamentoPrivato() != null){
//				dati.setImportoCofinanziamentoPrivato(datiGenerali.getImportoCofinanziamentoPrivato());
//			}
//			
//			if(datiGenerali.getImportoAmmesso() != null){
//				dati.setImportoAmmesso(datiGenerali.getImportoAmmesso());
//			}
//			LOG.info(prf + " ImportoAmmesso() = "+datiGenerali.getImportoAmmesso()+"; dati.getImportoAmmesso() = "+dati.getImportoAmmesso());
//			
//			dati.setDatiCumuloDeMinimis(getDatiCumuloDeMinimis(userInfo));
//			
//			/*
//			 * Calcolo del residuo ammesso come la differenza
//			 * tra   l' ammesso a finaziamento ed il totale rendicontato
//			 */
//			Double totaleRendicontato = dati.getImportoRendicontato() == null ? 0D : dati.getImportoRendicontato();
//			
//			Double importoAmmesso = dati.getImportoAmmesso() == null ? 0D : dati.getImportoAmmesso();
//			
//			Double importoResiduoAmmesso = NumberUtil.subtract(importoAmmesso, totaleRendicontato);
//			
//			dati.setImportoResiduoAmmesso(NumberUtil.getStringValueItalianFormat(importoResiduoAmmesso));
//			
//			/*
//			 * FIX PBandi-2307. Verifico se il progetto e' legato al bilancio ( presenza della regola BR37
//			 */
//			if (profilazioneSrv.isRegolaApplicabileForBandoLinea(
//					idUtente, 
//					idIride, 
//					b.getIdBando(), 
//					RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO)) {
//				dati.setIsLegatoBilancio(Boolean.TRUE);
//			} else {
//				dati.setIsLegatoBilancio(Boolean.FALSE);
//			}
//			
//			// Economie.
//			if (datiGenerali.getEconomie() == null) {
//				dati.setEconomie(new ArrayList<EconomiaPerDatiGenerali>());
//			} else {
//				ArrayList<EconomiaPerDatiGenerali> economie = new ArrayList<EconomiaPerDatiGenerali>();
//				for(EconomiaPerDatiGeneraliDTO eDTO : datiGenerali.getEconomie()){
//					EconomiaPerDatiGenerali e = new EconomiaPerDatiGenerali(); 
//					e.setDescBreveSoggFinanziatore(eDTO.getDescBreveSoggFinanziatore());
//					e.setImpQuotaEconSoggFinanziat(eDTO.getImpQuotaEconSoggFinanziat());
//					economie.add(e);
//				}
//				dati.setEconomie(economie);
//			}
//			
//			
//			if(datiGenerali == null) {
//				LOG.warn(prf + " datiGenerali == null, inviaErroreInternalServer");
//				return inviaErroreInternalServer("Errore durante il recupero dei dati generali.");
//			}
//					
//			LOG.debug(prf + " END");
//			return Response.ok().entity(dati).build();
//		} catch (Exception e) {
//			LOG.error(prf+"Exception e "+e.getMessage());
//			//e.printStackTrace();
//			throw e;
//		}
//		
//	}
	
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
	
	

//	@Override
//	public Response getAttivitaPregresse(HttpServletRequest req, Long idProgetto)
//			throws UtenteException, FileNotFoundException, IOException, Exception {
//		String prf = "[AffidamentiServiceImpl::getAttivitaPregresse]";
//		LOG.debug(prf + " BEGIN");
//		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
//		
//		try {
//			HttpSession session = req.getSession();
//			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//			String idIride = userInfo.getIdIride();
//			
//			
//			AttivitaPregresseDTO[] dto = gestioneDatiGeneraliBusinessImpl.caricaAttivitaPregresse(idUtente, idIride, idProgetto);
//			if(dto == null) {
//				LOG.warn(prf + " AttivitaPregresseDTO[] == null, inviaErroreInternalServer");
//				return inviaErroreInternalServer("Errore durante il recupero delle attivita pregresse.");
//			}
//					
//			LOG.debug(prf + " END");
//			return Response.ok().entity(dto).build();
//		} catch (Exception e) {
//			LOG.error(prf+"Exception e "+e.getMessage());
//			throw e;
//		}
//	}


	
	
	@Override
	public Response getElencoAffidamenti(HttpServletRequest req, Long idProgetto) throws Exception {
		String prf = "[AffidamentiServiceImpl::getElencoAffidamenti]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idSoggetto = userInfo.getIdSoggetto();
			String codiceRuolo = userInfo.getCodiceRuolo();
			if(idSoggetto == null || codiceRuolo == null) {
				return inviaErroreUnauthorized("Utente non autorizzato");
			}
			LOG.info(prf + " codice Ruolo: "+ codiceRuolo);
			List<AffidamentoDTO> affidamenti = affidamentiDAO.getElencoAffidamenti(idProgetto, codiceRuolo);		
			List<RigaTabellaAffidamenti> elenco1 = beanUtil.transformList(affidamenti, RigaTabellaAffidamenti.class);
			
			// Jira PBANDI-2829.
			for (int i = 0; i < elenco1.size(); i++) {
				String fase1Esito = StringUtil.isEmpty(affidamenti.get(i).getFase1Esito()) ? "" : (affidamenti.get(i).getFase1Esito());
				String fase1Rettifica = StringUtil.isEmpty(affidamenti.get(i).getFase1Rettifica()) ? "" : " CON RETTIFICA";
				String fase2Esito = StringUtil.isEmpty(affidamenti.get(i).getFase2Esito()) ? "" : affidamenti.get(i).getFase2Esito();
				String fase2Rettifica = StringUtil.isEmpty(affidamenti.get(i).getFase2Rettifica()) ? "" : " CON RETTIFICA";	
				elenco1.get(i).setEsitoIntermedio(fase1Esito+fase1Rettifica);
				elenco1.get(i).setEsitoDefinitivo(fase2Esito+fase2Rettifica);
				
				LOG.info(prf + " fase1Esito+fase1Rettifica : "+fase1Esito+fase1Rettifica);
				LOG.info(prf + " fase2Esito+fase2Rettifica : "+ fase2Esito+fase2Rettifica);
				
				LOG.info(prf + " esitoIntermedio : "+ elenco1.get(i).getEsitoIntermedio());
				LOG.info(prf + " esitoDefinitivo : "+ elenco1.get(i).getEsitoDefinitivo());
				
			}
			LOG.debug(prf + " END");
			return Response.ok(elenco1).build();
		} catch(Exception e) {
			throw e;
		}
		
	}

	

	@Override
	public Response getFornitoriAssociabili(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getFornitoriAssociabili]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idSoggetto = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
			FornitoreDTO filtro = new FornitoreDTO();
			
			//filtro.setIdTipoSoggetto(1L);			
			filtro.setIdSoggettoFornitore(idSoggetto);
			
			LOG.info(prf + " idSoggetto = "+ idSoggetto);
			
			List<FornitoreDTO> fornitori =  affidamentiDAO.findFornitori(filtro);
			
			// Elimino fornitori (idFornitore) presenti più volte.
			// PBANDI-3291: aggiunto per eliminare i doppioni nella combo 'fornitori' in Gestione Affidamento.7
			List<FornitoreDTO> fornitoriSingoli = new ArrayList<FornitoreDTO>(); 
			for (FornitoreDTO dto : fornitori) {
				boolean giaPresente = false;
				for (FornitoreDTO dto2 : fornitoriSingoli) {
					if (dto.getIdFornitore().intValue() == dto2.getIdFornitore().intValue())
						giaPresente = true;
				}
				if (!giaPresente)
					fornitoriSingoli.add(dto);
			}
			
			LOG.debug(prf + " END");
			return Response.ok(fornitoriSingoli).build();
		} catch(Exception e) {
			throw e;
		}
	
	
	}
	
	@Override
	public Response getAffidamento(HttpServletRequest req, Long idAppalto) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			if(idAppalto == null) { return inviaErroreBadRequest("Parametro mancato: ?[idAppalto]"); }
			AffidamentoDTO affidamento = affidamentiDAO.findAffidamento(idAppalto);
			if (affidamento.getSopraSoglia() == null)
				affidamento.setSopraSoglia("NA");
			LOG.debug(prf + "affidamento="+affidamento);
			LOG.debug(prf + " END");
			return Response.ok(affidamento).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response getCodiceProgetto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getCodiceProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			if(idProgetto == null) { return inviaErroreBadRequest("Parametro mancato: ?[idProgetto]"); }
			String codiceProgetto = affidamentiDAO.findCodiceProgetto(idProgetto);

			LOG.debug(prf + " END");
			return Response.ok().entity(codiceProgetto).build();
		} catch(Exception e) {
			throw e;
		}
	}
	

	@Override
	public Response getNormativeAffidamento(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getNormativaAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			List<NormativaAffidamentoDTO> normative = affidamentiDAO.findNormativeAffidamento();

			LOG.debug(prf + " END");
			return Response.ok().entity(normative).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response getTipologieAffidamento(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getTipologiaAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			List<TipoAffidamentoDTO> tipologieAffidamento = affidamentiDAO.findTipologieAffidamento();

			LOG.debug(prf + " END");
			return Response.ok().entity(tipologieAffidamento).build();
		} catch(Exception e) {
			throw e;
		}
	}

	

	@Override
	public Response getTipologieAppalto(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getTipologiaAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			List<TipologiaAppaltoDTO> tipologieAppalto = affidamentiDAO.findTipologieAppalto();

			LOG.debug(prf + " END");
			return Response.ok().entity(tipologieAppalto).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response getTipologieProcedureAggiudicazione(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getTipologieProcedureAggiudicazione]";
		LOG.debug(prf + " BEGIN");
		try {
			if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
			
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			DatiGeneraliDTO dg = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null);
			Long progBandoLinea = dg.getIdBandoLinea();
			
			if(progBandoLinea == null) return inviaRispostaVuota("Errore durante il recupero di progBandoLinea dai dati generali.");
			
			List<TipologiaAggiudicazioneDTO> tipologieAppalto = affidamentiDAO.findTipologieProcedureAggiudicazione(progBandoLinea);

			LOG.debug(prf + " END");
			return Response.ok().entity(tipologieAppalto).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response getMotiveAssenza(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getMotiveAssenza]";
		LOG.debug(prf + " BEGIN");
		try {
			
			List<MotiveAssenzaDTO> motiveAssenza = affidamentiDAO.findMotiveAssenza();

			LOG.debug(prf + " END");
			return Response.ok().entity(motiveAssenza).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response salvaAffidamento(HttpServletRequest req, SalvaAffidamentoRequest requestSalvaAffidamento)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::salvaAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			
			AffidamentoDTO affidamentoDTO = beanUtil.transform(requestSalvaAffidamento.getAffidamento(), AffidamentoDTO.class);
			// Se l'utente a video ha selezionato "n.a.", su db metto null.
			if ("NA".equalsIgnoreCase(affidamentoDTO.getSopraSoglia()))
				affidamentoDTO.setSopraSoglia(null);
			
			ProceduraAggiudicazioneDTO procAggDTO = beanUtil.transform(requestSalvaAffidamento.getProcAgg(), ProceduraAggiudicazioneDTO.class);
			affidamentoDTO.setIdProceduraAggiudicaz(procAggDTO.getIdProceduraAggiudicaz());
			affidamentoDTO.setProceduraAggiudicazioneDTO(procAggDTO);
			
			EsitoGestioneAffidamenti esito = affidamentiDAO.salvaAffidamento(idUtente, affidamentoDTO);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response cancellaAffidamento(HttpServletRequest req, Long idAppalto) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::cancellaAffidamento]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			esito = affidamentiDAO.cancellaAffidamento(userInfo.getIdUtente(), userInfo.getIdIride(),idAppalto );
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			LOG.debug(prf + " END");
			throw e;
		}
	}



	
	@Override
	public Response inviaInVerifica(HttpServletRequest req, VerificaAffidamentoRequest verificaRequest) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::inviaInVerifica]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			// verifica input
			if(verificaRequest.getIdAppalto() == null) { return inviaErroreBadRequest("parametro mancato nel body ?{idAppalto}"); }
			if(verificaRequest.getIdProgetto() == null) { return inviaErroreBadRequest("parametro mancato nel body ?{idProgetto}"); }
			
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Response response = inviaInVerifica(verificaRequest, idUtente, idIride);
			return response;
			
		} catch(Exception e) {
			LOG.error(prf +" :\n"+e.getMessage());
			//esito.setEsito(false);
			if(e.getMessage().equals(Constants.MAIL_NON_TROVATA_COD)) {
				return inviaErroreInternalServer(Constants.MAIL_NON_TROVATA_MSG);
			}
			
			throw e;
			
		}
		
		
	}
	
		
	
	@Transactional
	public Response inviaInVerifica(VerificaAffidamentoRequest verificaRequest, Long idUtente, String idIride) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::inviaInVerifica]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			
			Long idProgetto = verificaRequest.getIdProgetto();
			Long idAppalto = verificaRequest.getIdAppalto();
			LOG.info(prf +" idProgetto = "+ idProgetto + " idAppalto = "+ idAppalto);
			String descBreveRuoloEnte = Constants.DESC_BREVE_RUOLO_ENTE;	
			
			DatiGeneraliDTO dg = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null);
			
			ParamInviaInVerificaDTO dto = new ParamInviaInVerificaDTO();
			dto.setIdAppalto(idAppalto);
			dto.setBeneficiario(dg.getDescrizioneBeneficiario());
			dto.setTitoloProgetto(dg.getTitoloProgetto());
			dto.setCodiceProgettoVisualizzato(dg.getCodiceProgetto());
			dto.setNomeBandoLinea(dg.getDescrizioneBando());
			dto.setDescBreveRuoloEnte(descBreveRuoloEnte);
			dto.setIdProgetto(idProgetto);
			
			esito = affidamentiDAO.inviaInVerifica(idUtente, dto);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			LOG.error(prf +" :\n"+e.getMessage());
			//esito.setEsito(false);
			//setSessionContext(new S);
			//sessionContext.setRollbackOnly();
			throw e;
		}
	}

	
	@Override
	public Response getTipologieVarianti(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getTipologieVarianti]";
		LOG.debug(prf + " BEGIN");
		try {		
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			ArrayList<CodiceDescrizioneDTO> dto = affidamentiDAO.findTipologieVarianti(userInfo, GestioneDatiDiDominioSrv.TIPOLOGIE_VARIANTI);

			LOG.debug(prf + " END");
			return Response.ok().entity(dto).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response getRuoli(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getRuoli]";
		LOG.debug(prf + " BEGIN");
		try {
			
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			ArrayList<CodiceDescrizioneDTO> dto = affidamentiDAO.findRuoli(userInfo, GestioneDatiDiDominioSrv.TIPO_PERCETTORE);

			LOG.debug(prf + " END");
			return Response.ok().entity(dto).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	@Override
	public Response salvaVariante(HttpServletRequest req, SalvaVarianteRequest verificaRequest)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::salvaVariante]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			HttpSession session = req.getSession();			
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idAppalto = verificaRequest.getIdAppalto();
			
			// Leggo i parametri in input (obbligatorietà già verificata lato jquery).			
			Long idTipologiaVariante = verificaRequest.getIdTipologiaVariante();
		    Double importoVariante = verificaRequest.getImportoVariante();
			String noteVariante = verificaRequest.getNoteVariante();
			Long idVariante = verificaRequest.getIdVariante();	// nullo in caso di inserimento
			
			// Log...
			LOG.info(prf +" idTipologiaVariante="+idTipologiaVariante);
			LOG.info(prf +" importoVariante="+importoVariante);
			LOG.info(prf + "noteVariante="+noteVariante);
			LOG.info(prf + " idVariante="+idVariante);
			
			// Creo la variante da salvare.
			VarianteAffidamentoDTO dto = new VarianteAffidamentoDTO();
			dto.setIdTipologiaVariante(new Long(idTipologiaVariante));
			//importoVariante = importoVariante.replace(',', '.');
			dto.setImporto(new Double(importoVariante));
			if (!StringUtils.isEmpty(noteVariante))
				dto.setNote(noteVariante);
			if (idVariante != null) {
				dto.setIdVariante(idVariante);
			} else {
				dto.setDtInserimento(DateUtil.getSysdate());
			}
			dto.setIdAppalto(idAppalto);
			
			esito = affidamentiDAO.creaVariante(userInfo.getIdUtente(), userInfo.getIdIride(), dto);
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	
	}


	@Override
	public Response salvaFornitore(HttpServletRequest req, SalvaFornitoreRequest verificaRequest)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::salvaFornitore]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			// Evita che al refresh l'isInit valga true.
			//session.put(Constants.KEY_CURRENT_CONTENT_PANEL,session.get(Constants.KEY_PREVIOUS_CONTENT_PANEL));
			
			HttpSession session = req.getSession();			
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			
			Long idAppalto = verificaRequest.getIdAppalto();			
			// Leggo i parametri in input (obbligatorietà già verificata lato jquery).			
			Long idFornitore = verificaRequest.getIdFornitore();			
			Long idTipoPercettore = verificaRequest.getIdTipoPercettore(); //è idRuoloFornitore
						
			LOG.info(prf + " idFornitore="+idFornitore);
			LOG.info(prf + " idTipoPercettore="+idTipoPercettore);
			LOG.info(prf + " idAppalto="+idAppalto);			
			
			// Creo il fornitore da salvare.
			FornitoreAffidamentoDTO dto = new FornitoreAffidamentoDTO();
			dto.setIdAppalto(idAppalto);
			dto.setIdFornitore(new Long(idFornitore));
			dto.setIdTipoPercettore(new Long(idTipoPercettore));
			
			esito = affidamentiDAO.creaFornitore(userInfo.getIdUtente(), userInfo.getIdIride(), dto);	
		} catch(Exception e) {
			throw e;
		}
		return Response.ok().entity(esito).build();
	}
	


	@Override
	public Response cancellaVariante(HttpServletRequest req, Long idVariante) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::cancellaVariante]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			esito = affidamentiDAO.cancellaVariante(userInfo.getIdUtente(), userInfo.getIdIride(),idVariante );
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			LOG.debug(prf + " END");
			throw e;
		}
	
	}


	@Override
	public Response cancellaFornitore(HttpServletRequest req, Long idFornitore, Long idAppalto, Long idTipoPercettore)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::cancellaFornitore]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			esito = affidamentiDAO.cancellaFornitore(userInfo.getIdUtente(), userInfo.getIdIride(),idFornitore, idAppalto, idTipoPercettore );
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			LOG.debug(prf + " BEGIN");
			throw e;
		}
	}



	@Override
	public Response getDocumento(HttpServletRequest req, Long idDocumentoIndex)
			throws UtenteException, FileNotFoundException, IOException, Exception {
		String prf = "[AffidamentiServiceImpl::getDocumento]";
		LOG.debug(prf + " BEGIN");
		EsitoScaricaDocumentoDTO esito = new EsitoScaricaDocumentoDTO();
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			esito = affidamentiDAO.findDocumento(idUtente, idIride, idDocumentoIndex);
			LOG.info(prf + " bytes: "+ esito.getBytesDocumento());
		
			LOG.debug(prf + " END");
			return Response.ok(esito.getBytesDocumento()).build();

		} catch (Exception e) {
			LOG.error(prf + " Exception "+e.getMessage());
			throw e;
		}
	}

	@Override
	public Response salvaSubcontratto(HttpServletRequest req, SubcontrattoRequest request)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::respingiAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			Long idAppalto = request.getIdAppalto();
			if (idAppalto == null) return inviaErroreBadRequest("Http body mancato ?{idAppalto}");
			
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			
			EsitoGestioneAffidamenti esito = affidamentiDAO.salvaSubcontratto(userInfo.getIdUtente(), userInfo.getIdIride(), request);
			LOG.debug(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response cancellaSubcontratto(HttpServletRequest req, Long idSubcontrattoAffidamento)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::cancellaSubcontratto]";
		LOG.debug(prf + " BEGIN");
		EsitoGestioneAffidamenti esito = new EsitoGestioneAffidamenti();
		try {
			if (idSubcontrattoAffidamento == null) return inviaErroreBadRequest("Http body mancato ?{idSubcontrattoAffidamento}");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			esito = affidamentiDAO.cancellaSubcontratto(userInfo.getIdUtente(), userInfo.getIdIride(),idSubcontrattoAffidamento );
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			LOG.debug(prf + " BEGIN");
			throw e;
		}
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////// SERVIZI DI ARCHIVIO FILE ////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getAllegati(HttpServletRequest req, Long idAppalto) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getAllegati]";
		LOG.debug(prf + " BEGIN");
		try {
			
			List<DocumentoAllegato> allegati = archivioFileDAO.findAllegati(idAppalto);
			LOG.debug(prf + " END");
			return Response.ok(allegati).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Response dissociateFileAffidamento(HttpServletRequest req, Long idDocumentoIndex, Long idProgetto, Long idAppalto) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::dissociateFile]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idEntita = Constants.ID_ENTITA_PBANDI_T_APPALTO;
			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null);
			LOG.info(prf + " dissociating idFile(idDocIndex): "+idDocumentoIndex+" , idEntita(progetto):"+idEntita+" ,idTarget(idProgetto):"+idProgetto);
			
			EsitoOperazioni esito   = archivioFileDAO.dissociateFile(idUtente, idIride, idDocumentoIndex, idEntita, idAppalto, idProgetto);
			LOG.debug(prf + " END");
			return Response.ok(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	@Override
	public Response associateFileAffidamento(HttpServletRequest req,
			AssociateFileRequest associateFileRequest) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::associateFileAffidamento]";
		LOG.debug(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idTarget = associateFileRequest.getIdAppalto();
			
			Long idEntita = Constants.ID_ENTITA_PBANDI_T_APPALTO;
			Long idProgetto = associateFileRequest.getIdProgetto();
			String[] idDocumentoIndexSelezionato = associateFileRequest.getFiles();
			
			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null);
			ArrayList<String> filesNotAssociated = new ArrayList<String>();
			ArrayList<String> filesAssociated = new ArrayList<String>();
			if (!ObjectUtil.isEmpty(idDocumentoIndexSelezionato)) {
				for (String idFile : idDocumentoIndexSelezionato) {
					try {
						esito = archivioFileDAO.associateFile(userInfo, idTarget, idEntita, NumberUtil.toLong(idFile), idProgetto );
						if (esito != null && esito.getEsito()) {
							filesAssociated.add(idFile);
						} else {
							filesNotAssociated.add(idFile);
						} 
					} catch (Exception e) {
						LOG.error(prf + " Errore durante l'associazione del file["+idFile+"] alla entita["+idEntita+"], target["+idTarget+"].", e);
						filesNotAssociated.add(idFile);
					}
				}	
			}
			LOG.debug(prf + " END");
			return Response.ok(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}



	@Override
	public Response getNotifiche(HttpServletRequest req, Long idAppalto) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::associateFileAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Long idEntita = Constants.ID_ENTITA_PBANDI_T_APPALTO;
			
			NotificaDTO[] notifiche = gestioneDatiGeneraliBusinessImpl.findNotifiche(idUtente, idIride, idEntita, idAppalto);
			
			LOG.debug(prf + " END");
			return Response.ok(notifiche).build();
		} catch(Exception e) {
			throw e;
		}
	}





	@Override
	public Response getChecklistAssociatedAffidamento(HttpServletRequest req, Long idAppalto)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getChecklistAssociatedAffidamento]";
		LOG.debug(prf + " BEGIN");
		
		DocumentoAllegato[] documentoAllegato  =null;
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			
			if (idAppalto == null)
				throw new Exception("idAffidamento nullo");
			LOG.info(prf + "  idAffidamento: "+idAppalto );
			documentoAllegato  = archivioFileDAO.getChecklistAssociatedAffidamento(userInfo, idAppalto );
			LOG.info(prf + " Num file trovati: "+(documentoAllegato.length>0?documentoAllegato.length:0));
	
			LOG.debug(prf + " END");
			return Response.ok(documentoAllegato).build();
		} catch (Exception e) {
			LOG.error(prf + " Error gestioneArchivioFileBusinessImpl.getAllegatiComunicazioneFineProgByIdProgetto ",e);
			throw e;
		}

	}



	@Override
	public Response getAllAffidamentoChecklist(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::getAllAffidamentoChecklist]";
		LOG.debug(prf + " BEGIN");
		try {
			
			AffidamentoCheckListDTO[] dto = affidamentiDAO.findAllAffidamentoChecklist();
			LOG.debug(prf + " END");
			return Response.ok(dto).build();
		} catch (Exception e) {
			throw e;
		}
	}



	@Override
	public Response respingiAffidamento(HttpServletRequest req, RespingiAffiddamentoRequest respingiRequest)
			throws UtenteException, Exception {
		String prf = "[AffidamentiServiceImpl::respingiAffidamento]";
		LOG.debug(prf + " BEGIN");
		try {
			 Long idAppalto = respingiRequest.getIdAppalto();
			 String note = respingiRequest.getNote();
			 if (idAppalto == null) return inviaErroreBadRequest("Http body mancato ?{idAppalto}");
			 if (note == null) return inviaErroreBadRequest("Http body mancato ?{note}");
			 
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			
			EsitoGestioneAffidamenti esito = affidamentiDAO.respingiAffidamento(userInfo.getIdUtente(), userInfo.getIdIride(), idAppalto, note);
			LOG.debug(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response allegatiVerbaleChecklist(Long idDocIndex, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(affidamentiDAO.allegatiVerbaleChecklist(idDocIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
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

	private Response inviaErroreNotFound(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.NOT_FOUND).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreUnauthorized(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.UNAUTHORIZED).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

	private Response inviaRispostaVuota(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.OK).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreInternalServer(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}


}
