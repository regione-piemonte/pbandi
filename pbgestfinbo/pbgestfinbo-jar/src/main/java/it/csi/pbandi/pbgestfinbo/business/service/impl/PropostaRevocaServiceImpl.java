/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.PropostaRevocaService;
import it.csi.pbandi.pbgestfinbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ArchivioRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.DataPropostaRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.NomeCognomeIstruttore;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.PropostaRevocaMiniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroPropostaRevocaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.Date;

@Service
public class PropostaRevocaServiceImpl implements PropostaRevocaService {
    @Autowired
    PropostaRevocaDAO propostaRevocaDAO;
    
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	// GET PROPOSTE REVOCHE
	@Override
    public Response getProposteRevoche(FiltroPropostaRevocaVO filtroPropostaRevocaVO, HttpServletRequest req) throws Exception {
        return Response.ok().entity(propostaRevocaDAO.getProposteRevoche(
                filtroPropostaRevocaVO.getIdSoggetto(), filtroPropostaRevocaVO.getIdBando(), filtroPropostaRevocaVO.getIdProgetto(),
                filtroPropostaRevocaVO.getIdCausaleBlocco(), filtroPropostaRevocaVO.getIdStatoRevoca(), filtroPropostaRevocaVO.getNumeroPropostaRevoca(),
                filtroPropostaRevocaVO.getDataPropostaRevocaFrom(), filtroPropostaRevocaVO.getDataPropostaRevocaTo(), req
        )).build();
    }

    // GET INFO REVOCHE	
    @Override
	public Response getInfoRevoche(Long idGestioneRevoca, Long idSoggetto, Long idDomanda, HttpServletRequest req) {
    	String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN"); 	
		
    	Object result;
		
    	try {
		//result = DataPropostaRevocaVO.createMokObject();
		//CONTROLLI
		if(idGestioneRevoca == null)
			throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
		if(idSoggetto == null)
			throw new ErroreGestitoException("Variabile idSoggetto non valorizzata");
		//CHIAMATA A DAO --->>
		DataPropostaRevocaVO dataPropostaRevocaVO = propostaRevocaDAO.getInfoRevoche(idGestioneRevoca, idSoggetto);
		NomeCognomeIstruttore nomeCognomeIstruttore = propostaRevocaDAO.getNomeCognomeIstruttore(idGestioneRevoca);
		dataPropostaRevocaVO.setNomeIstruttore(nomeCognomeIstruttore.getNome());
		dataPropostaRevocaVO.setCognomeIstruttore(nomeCognomeIstruttore.getCognome());

		result = dataPropostaRevocaVO;
    	}
    	catch(Exception e){
    		result = new ResponseCodeMessage("KO", e.getMessage());
    		LOG.info(prf + e.getMessage());
			//throw e;
    	}
    	
    	LOG.info(prf + "END"); 
		
		return Response.ok().entity(result).build();
	}
	
	// GET IMPORTI REVOCHE
    @Override
	public Response getImportiRevoche(Long idGestioneRevoca, HttpServletRequest req) {
    	
    	String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN"); 	
		
    	Object result;


    	try {
    	//result =  ImportiPropostaRevocaVO.createMokObjectList();
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		//CONTROLLI
		if(idGestioneRevoca == null)
			throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
		//CHIAMATA A DAO --->>
		result = propostaRevocaDAO.getImportiRevoche(idGestioneRevoca, userInfoSec.getIdUtente());
    	}
    	catch(Exception e){
    		result = new ResponseCodeMessage("KO", e.getMessage());
    		LOG.info(prf + e.getMessage()); 	
    	}
    	
    	LOG.info(prf + "END"); 
		
		return Response.ok().entity(result).build();
	}

	// GET NOTA REVOCA
	@Override
	public Response getNotaRevoche(Long idGestioneRevoca, HttpServletRequest req) throws ErroreGestitoException {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN"); 	
		
    	Object result;
		
    	try {
			//CONTROLLI
			if(idGestioneRevoca == null)
				throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
			//CHIAMATA A DAO --->>
			result = propostaRevocaDAO.getNotaRevoche(idGestioneRevoca);
    	}
    	catch(Exception e){
    		result = new ResponseCodeMessage("KO", e.getMessage());
    	}
    	
    	LOG.info(prf + "END"); 
		
		return Response.ok().entity(result).build();		
	}
	
	//UPDATE NOTA REVOCA
	@Override
	public Response updateNotaRevoche(Long idGestioneRevoca, String nota, HttpServletRequest req) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN"); 	
		
    	Object result;
		
    	try {
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			//CONTROLLI
			if(idGestioneRevoca == null)
				throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
			//CHIAMATA A DAO --->>
			propostaRevocaDAO.updateNotaRevoche(idGestioneRevoca, nota, userInfoSec.getIdUtente());
    		result = new ResponseCodeMessage("OK", "Nota aggiornata con successo!");
    	}
    	catch(Exception e){
    		result = new ResponseCodeMessage("KO", e.getMessage());
    		LOG.info(prf + e.getMessage()); 	
    		//throw e;
    	}
    	
    	LOG.info(prf + "END"); 
		
		return Response.ok().entity(result).build();
	}
	
	//CREA BOZZA PROCEDIMENTO DI REVOCA
	@Override
	public Response creaBozzaProcedimentoRevoca(MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
    	Object result;

    	try {
			Long idGestioneRevoca = multipartFormDataInput.getFormDataPart("idGestioneRevoca", Long.class, null);
			Long idUtente = multipartFormDataInput.getFormDataPart("idUtente", Long.class, null);
			//CONTROLLI
			if(idGestioneRevoca == null)
				throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
			//CHIAMATA A DAO (Algoritmo 7.1)--->>

			//2 inserimento nuovo record proposta di revoca che è la copia di quello con idGestioneRevoca corrispondente
			propostaRevocaDAO.updateRevoca(propostaRevocaDAO.cloneGestioneRevoca(idGestioneRevoca), null, 1L, 2L, idUtente);
			//3 inserimento nuovo record procediemnto di revoca
			//4 copia proposta di revoca nel procedimento, tranne il campo [dt_gestione]
			Long newIdGestioneRevoca = propostaRevocaDAO.cloneGestioneRevoca(idGestioneRevoca);
			propostaRevocaDAO.updateRevoca(newIdGestioneRevoca, null, 2L, 5L, idUtente);
			//1 Disabilitazione record attuale
			propostaRevocaDAO.disableCurrentGestioneRevoca(idGestioneRevoca, idUtente);
			// Sposta lettera della DS da proposta a procedimento
			propostaRevocaDAO.spostaAllegatoRevoca(idGestioneRevoca, newIdGestioneRevoca);
    		result = new ResponseCodeMessage("OK", "Bozza Procedimento Revoca creata con successo!");
    	}
    	catch(Exception e){
    		result = new ResponseCodeMessage("KO", e.getMessage());
    		LOG.info(prf + e.getMessage()); 	
    		//throw e;
    	}
    	
    	LOG.info(prf + "END");
		return Response.ok().entity(result).build();
	}

	//CREA BOZZA PROVVEDIMENTO DI REVOCA
	@Override
	public Response creaBozzaProvvedimentoRevoca(MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		Object result;

		try {
			Long idGestioneRevoca = multipartFormDataInput.getFormDataPart("idGestioneRevoca", Long.class, null);
			Long idUtente = multipartFormDataInput.getFormDataPart("idUtente", Long.class, null);
			//CONTROLLI
			if(idGestioneRevoca == null)
				throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
			//CHIAMATA A DAO (Algoritmo 7.1)--->>
			//2 inserimento nuovo record proposta di revoca che è la copia di quello con idGestioneRevoca corrispondente
			propostaRevocaDAO.updateRevoca(propostaRevocaDAO.cloneGestioneRevoca(idGestioneRevoca), null, 1L, 3L, idUtente);
			//3 inserimento nuovo record provvedimento di revoca
			//4 copia proposta di revoca nel provvedimento, tranne il campo [dt_gestione]
			propostaRevocaDAO.updateRevoca(propostaRevocaDAO.cloneGestioneRevoca(idGestioneRevoca), null, 3L, 5L, idUtente);
			//1 Disabilitazione record attuale
			propostaRevocaDAO.disableCurrentGestioneRevoca(idGestioneRevoca, idUtente);
			result = new ResponseCodeMessage("OK", "Bozza Provvedimento Revoca creata con successo!");
		}
		catch(Exception e){
			result = new ResponseCodeMessage("KO", e.getMessage());
			LOG.info(prf + e.getMessage());
			//throw e;
		}

		LOG.info(prf + "END");
		return Response.ok().entity(result).build();
	}

	//ARCHIVIA PROPOSTA DI REVOCA
	@Override
	public Response archiviaPropostaRevoca(ArchivioRevocaVO archivioRevocaVO, Long idUtente, HttpServletRequest req) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		Object result;

		try {
			//CONTROLLI
			if(archivioRevocaVO.getIdGestioneRevoca() == null)
				throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
			//CHIAMATA A DAO (Algoritmo 7.1)--->>
			//2 inserimento nuovo record proposta di revoca che è la copia di quello con idGestioneRevoca corrispondente
			propostaRevocaDAO.updateRevoca(propostaRevocaDAO.cloneGestioneRevoca(archivioRevocaVO.getIdGestioneRevoca()), archivioRevocaVO.getNoteRevoca(), 1L, 4L, idUtente);
			//1 Disabilitazione record attuale
			propostaRevocaDAO.disableCurrentGestioneRevoca(archivioRevocaVO.getIdGestioneRevoca(), idUtente);
			result = new ResponseCodeMessage("OK", "Proposta Revoca Archiviata con successo!");
		}
		catch(Exception e){
			result = new ResponseCodeMessage("KO", e.getMessage());
			LOG.info(prf + e.getMessage());
			//throw e;
		}

		LOG.info(prf + "END");
		return Response.ok().entity(result).build();
	}

	//CREA PROPOSTA DI REVOCA
	@Override
	public Response creaPropostaRevoca(PropostaRevocaMiniVO propostaRevocaMiniVO, HttpServletRequest req) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		Object result;

		try {
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			//CONTROLLI
			if(propostaRevocaMiniVO.getNumeroRevoca() == null)
				throw new ErroreGestitoException("Variabile numeroRevoca non valorizzata");
			if(propostaRevocaMiniVO.getIdProgetto() == null)
				throw new ErroreGestitoException("Variabile idProgetto non valorizzata");
			if(propostaRevocaMiniVO.getIdCausaleBlocco() == null)
				throw new ErroreGestitoException("Variabile idCausaleBlocco non valorizzata");
			if(propostaRevocaMiniVO.getIdCausaleBlocco() == 23 && propostaRevocaMiniVO.getIdAutoritaControllante() == null)
				throw new ErroreGestitoException("Variabile idAutoritaControllante non valorizzata nonostante causale blocco: Altri controlli");
			if(propostaRevocaMiniVO.getDataPropostaRevoca() == null){
				propostaRevocaMiniVO.setDataPropostaRevoca(new Date());
			}
			//PBAN-OPE-STE-V01-REV024-Verifica inserimento proposta di revoca
			propostaRevocaDAO.verificaInserimentoPropostaRevoca(propostaRevocaMiniVO.getDataPropostaRevoca(), propostaRevocaMiniVO.getIdProgetto());
			//CHIAMATA A DAO (Algoritmo 7.1)--->>
			propostaRevocaDAO.creaPropostaRevoca(
					propostaRevocaMiniVO.getNumeroRevoca(),
					propostaRevocaMiniVO.getIdProgetto(),
					propostaRevocaMiniVO.getIdCausaleBlocco(),
					propostaRevocaMiniVO.getIdAutoritaControllante(),
					propostaRevocaMiniVO.getDataPropostaRevoca(),
					userInfoSec.getIdUtente()
			);
			result = new ResponseCodeMessage("OK", "Proposta Revoca Creata con successo!");
		}
		catch(Exception e){
			result = new ResponseCodeMessage("KO", e.getMessage());
			LOG.info(prf + e.getMessage());
			//throw e;
		}

		LOG.info(prf + "END");
		return Response.ok().entity(result).build();
	}
}
