/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.AreaControlliService;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AreaControlliDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaIntegrazioneControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaProrogaVO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AreaControlliServiceImpl implements AreaControlliService {

	@Autowired
	AreaControlliDAO areaControlliDao; 
	@Autowired
	IterAutorizzativiDAO iterAutoDao; 
	@Autowired
	SuggestRevocheDAO suggestRevocaDao; 
	@Autowired 
	PropostaRevocaDAO propostaRevocaDao;
	
	@Override
	public Response getListaSugg(RicercaControlliDTO suggestDTO, int id, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getListaSugg(suggestDTO, id)).build();
	}

	@Override
	public Response getStatoControllo(HttpServletRequest req) {
		return Response.ok(areaControlliDao.getStatoControllo()).build();
	}

	@Override
	public Response getElencoControlli(RicercaControlliDTO controlloDTO, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getElencoControlli(controlloDTO, req)).build();
	}

	@Override
	public Response getAutoritaControllante(HttpServletRequest req) {
		return Response.ok(areaControlliDao.getAutoritaControllante(req)).build();
	}

	@Override
	public Response getControllo(BigDecimal idControllo, BigDecimal idProgetto, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getControllo(idControllo, idProgetto, req)).build();
	}

	@Override
	public Response gestioneControllo(ControlloLocoVO controllo, HttpServletRequest req) {
		return Response.ok(areaControlliDao.gestioneControllo(controllo, req)).build();
	}

	@Override
	public Response suggerimentoTipovista(HttpServletRequest req) {
		return null;
	}

	@Override
	public Response checkRichiestaIntegrazione(BigDecimal idControllo, HttpServletRequest req) {
		return Response.ok(areaControlliDao.checkRichiestaIntegrazione(idControllo, req)).build();
	}

//	@Override
//	public Response getElencoAllegati(BigDecimal idControllo, HttpServletRequest req) throws DaoException {
//		return Response.ok(areaControlliDao.getElencoAllegati(idControllo)).build();
//	}



	@Override
	public Response salvaAllegato(MultipartFormDataInput multipartFormData, HttpServletRequest req) {
		return Response.ok(areaControlliDao.salvaAllegato(multipartFormData)).build();
	}
	
	@Override
	public Response salvaAllegati(MultipartFormDataInput multipartFormData, HttpServletRequest req) {
	
		return Response.ok(areaControlliDao.salvaAllegati(multipartFormData, req)).build();
	}

	@Override
	public Response avviaIterIntegrazione(RichiestaIntegrazioneControlloLocoVO richiesta, BigDecimal idUtente, HttpServletRequest req) {
		return Response.ok(areaControlliDao.avviaIterIntegrazione(richiesta, idUtente)).build();
	}

	@Override
	public Response chiamaIterAuto002(MultipartFormDataInput multipartFormData, HttpServletRequest req) {
		return Response.ok(areaControlliDao.chiamaIterAuto002(multipartFormData)).build();
	}

	@Override
	public Response chiudiRichiestaIntegr(BigDecimal idRichiestaIntegr, BigDecimal idUtente, HttpServletRequest req) {
		return Response.ok(areaControlliDao.chiudiRichiestaIntegr(idRichiestaIntegr, idUtente)).build();
	}

	@Override
	public Response getRichProroga(BigDecimal idRichiestaIntegr, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getRichProroga(idRichiestaIntegr)).build();

	}
	
	@Override
	public Response getElencoRichProroga(BigDecimal idRichiestaIntegr, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getElencoRichProroga(idRichiestaIntegr)).build();
		
	}

	@Override
	public Response gestisciProroga(RichiestaProrogaVO proroga, BigDecimal idUtente, int id, HttpServletRequest req) {
		return Response.ok(areaControlliDao.gestisciProroga(proroga, idUtente, id)).build();
	}

	@Override
	public Response salvaDataNotifica(RichiestaIntegrazioneControlloLocoVO richiesta, HttpServletRequest req) {
		return Response.ok(areaControlliDao.salvaDataNotifica(richiesta, req)).build();
	}

	@Override
	public Response salvaAltroControllo(ControlloLocoVO controllo, BigDecimal idUtente, int idAttivitaControllo, HttpServletRequest req) {
		return Response.ok(areaControlliDao.salvaAltroControllo(controllo, idUtente, idAttivitaControllo)).build();
	}

	@Override
	public Response getListaBando(BigDecimal idSoggetto, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getListaBando(idSoggetto)).build();
	}

	@Override
	public Response getListaProgetto(BigDecimal idSoggetto, BigDecimal progBandoLinea, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getListaProgetto(idSoggetto, progBandoLinea)).build();
	}

	@Override
	public Response getElencoAltriControlli(RicercaControlliDTO searchDTO, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getAltriControlli(searchDTO)).build();
	}

	@Override
	public Response getAltroControllo(BigDecimal idControllo, BigDecimal idProgetto, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getAltroControllo(idControllo, idProgetto)).build();
	}
	
	@Override
	public Response updateControlloFinp(ControlloLocoVO controllo, BigDecimal idUtente, int idAttivitaControllo, HttpServletRequest req) {
		
		EsitoDTO esito = new EsitoDTO();
		Long idPropostaRevoca =null; 
		try {
		int idPreviousState = areaControlliDao.getPreviousStateControllo(controllo.getIdControllo(),1);
		// controllo prima se lo stato del controllo è positivo: in modo da chiamare iter002
		if(controllo.getIdStatoControllo()==2) {			
			// chiamo iter002 con i parametri  
			// String erroreIter = iterAutoDao.avviaIterAutorizzativo( (long)7, (long)608, controllo.getIdControllo().longValue(), controllo.getIdProgetto().longValue(), idUtente.longValue());
			Boolean restult = areaControlliDao.avviaIterEsitoPositivoControllo(controllo.getIdProgetto().longValue(), controllo.getIdControllo().longValue(), false, req);
			if(restult== false){
				return inviaErroreBadRequest("ATTENZIONE! Non è stato possibile avviare l’iter autorizzativo di esito positivo dei controllo in loco!");
			}
		}
		Boolean resultUpdate = areaControlliDao.updateControlloFinp(controllo, idUtente, idAttivitaControllo); 
		if(resultUpdate==false) {
			return	inviaErroreBadRequest("Errore salvattagio del controllo!");
		}
		switch (controllo.getIdStatoControllo()) {
		case 4:
			if(idPreviousState!=4 && idPreviousState!=5) {
				// chiamo servizio revoche
				idPropostaRevoca = propostaRevocaDao.creaPropostaRevoca(suggestRevocaDao.newNumeroRevoca(), 
						controllo.getIdProgetto().longValue(), (long)18, null, Date.valueOf(LocalDate.now()), idUtente.longValue());
			}
			break;
		case 5:
			if(idPreviousState!=5) {
				// chiamo servizio revoche
				idPropostaRevoca = propostaRevocaDao.creaPropostaRevoca(suggestRevocaDao.newNumeroRevoca(),
						controllo.getIdProgetto().longValue(), (long)18, null, Date.valueOf(LocalDate.now()), idUtente.longValue());
			}
			break;

		default:
			break;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		esito.setEsito(true);
		esito.setId(idPropostaRevoca);
		esito.setMessaggio("Salvataggio del controllo avvenuto con successo!");
		
		return Response.ok(esito).build();
	}

	@Override
	public Response updateAltroControllo(ControlloLocoVO controllo, BigDecimal idUtente, HttpServletRequest req) {
		EsitoDTO esito = new EsitoDTO();
		Long idPropostaRevoca =null; 
		try {
		int idPreviousState = areaControlliDao.getPreviousStateControllo(controllo.getIdControllo(),2);
		// controllo prima se lo stato del controllo è positivo: in modo da chiamare iter002
		if(controllo.getIdStatoControllo()==2) {			
			// chiamo iter002 con i parametri  
			// String erroreIter = iterAutoDao.avviaIterAutorizzativo( (long)7, (long)608, controllo.getIdControllo().longValue(), controllo.getIdProgetto().longValue(), idUtente.longValue());
			Boolean restult = areaControlliDao.avviaIterEsitoPositivoControllo(controllo.getIdProgetto().longValue(), controllo.getIdControllo().longValue(), true, req);
			if(restult== false){
				return inviaErroreBadRequest("ATTENZIONE! Non è stato possibile avviare l’iter autorizzativo di esito positivo dei controllo in loco!");
			}
		}
		Boolean resultUpdate = areaControlliDao.updateAltroControllo(controllo, idUtente); 
		if(resultUpdate==false) {
		return	inviaErroreBadRequest("Errore salvattagio del controllo!");
		}
		switch (controllo.getIdStatoControllo()) {
		case 4:
			if(idPreviousState!=4 && idPreviousState!=5) {
				// chiamo servizio revoche// ho bisogno che il sservizio di propostaRevocaDao.creaPropostaRevoca mi restituisca l'id_gestione_revoca 
		
				idPropostaRevoca = propostaRevocaDao.creaPropostaRevoca(suggestRevocaDao.newNumeroRevoca(), 
							controllo.getIdProgetto().longValue(), (long)18, null, Date.valueOf(LocalDate.now()), idUtente.longValue());
		
			}
			break;
		case 5:
			if(idPreviousState!=5) {
				// chiamo servizio revoche
				idPropostaRevoca =	propostaRevocaDao.creaPropostaRevoca(suggestRevocaDao.newNumeroRevoca(),
						controllo.getIdProgetto().longValue(), (long)18, null, Date.valueOf(LocalDate.now()), idUtente.longValue());
			}
			break;
		default:
			break;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		esito.setEsito(true);
		esito.setId(idPropostaRevoca);
		esito.setMessaggio("Salvataggio del controllo avvenuto con successo!");
		return Response.ok(esito).build();
	}

	@Override
	public Response checkRichiestaIntegrAltroControllo(BigDecimal idControllo, HttpServletRequest req) {
		return Response.ok(areaControlliDao.checkRichiestaIntegrAltroControllo(idControllo)).build();
	}
	
	@Override
	public Response getElencoFile(BigDecimal idControllo, BigDecimal idEntita, HttpServletRequest req) {
		return Response.ok(areaControlliDao.getElencoFile(idControllo, idEntita)).build();
	}

	@Override
	public Response getElencoAllegatiBeneficiario(BigDecimal idTarget, BigDecimal idEntita, HttpServletRequest req)
			throws DaoException {
		return Response.ok(areaControlliDao.getElencoFileBeneficario(idTarget, idEntita)).build();
	}

	@Override
	public Response avviaIterEsitoPositivoControllo(Long idProgetto, Long idTarget, Boolean isAltriControlli, HttpServletRequest req) throws DaoException {
		return Response.ok().build();
	}

//	@Override
//	public Response getRichProrogaAltroControllo(BigDecimal idRichiestaIntegr, HttpServletRequest req)
//			throws DaoException {
//		return Response.ok(areaControlliDao.getRichProrogaAltroControllo(idRichiestaIntegr)).build();
//	}
	private Response inviaErroreBadRequest(String msg) {
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(false);
		esito.setMessaggio(msg);
		return Response.ok(esito).type( MediaType.APPLICATION_JSON).build();
	}
}
