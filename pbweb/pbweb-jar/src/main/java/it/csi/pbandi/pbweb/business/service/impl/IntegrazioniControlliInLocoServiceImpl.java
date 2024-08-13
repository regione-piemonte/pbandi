/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.IntegrazioniControlliInLocoService;
import it.csi.pbandi.pbweb.integration.dao.IntegrazioniControlliInLocoDAO;
import it.csi.pbandi.pbweb.integration.vo.IntegrazioniControlliInLocoVO;
import it.csi.pbandi.pbweb.util.Constants;

@Service
@Transactional

public class IntegrazioniControlliInLocoServiceImpl implements IntegrazioniControlliInLocoService {
	
		private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
		
		
	@Autowired
	IntegrazioniControlliInLocoDAO integrazioniDAO;
	
	
	@Override
	public Response getIntegrazioniResponse(int idProgetto, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		List<Integer> idControllo;
		List<Integer> idControlloLoco;
		int idEntita;
		List<IntegrazioniControlliInLocoVO> vc = new ArrayList<IntegrazioniControlliInLocoVO>();
		List<IntegrazioniControlliInLocoVO> vc2 = new ArrayList<IntegrazioniControlliInLocoVO>();

		idControlloLoco= integrazioniDAO.getIdControlloLoco(idProgetto);
		idControllo = integrazioniDAO.getIdControllo(idProgetto);
		if (idControlloLoco.size()==0 && idControllo.size()> 0) {
			idEntita = 608;
			for (int i: idControllo) {
			vc2.addAll(integrazioniDAO.getIntegrazioni(i, idEntita));}
			for(int i =0; i<vc2.size(); i++) {
			if(integrazioniDAO.getLettera(vc2.get(i).getIdIntegrazione()).getNumProtocollo()!= null) {
				vc.add(vc2.get(i));
			}}
			for(IntegrazioniControlliInLocoVO integr: vc) {
					integr.setNumProtocollo(integrazioniDAO.getLettera(integr.getIdIntegrazione()).getNumProtocollo());
					if(integrazioniDAO.getStatoProroga(integr.getIdIntegrazione()).isEmpty()) {
					integr.setStatoProroga(null);
					}else {
					integr.setStatoProroga(integrazioniDAO.getStatoProroga(integr.getIdIntegrazione()).get(0).getStatoProroga());}
					int pos=0;
					for(IntegrazioniControlliInLocoVO v : integrazioniDAO.getAllegati(integr.getIdIntegrazione())) {
						if(v.getFlagEntita() != null) {
							pos++;
						}
						}
						if(pos==0) {
					          integr.setAllegatiInseriti(false); 
							}else {
								integr.setAllegatiInseriti(true); 
							}
			        
				 
					}
			
		}else if (idControlloLoco.size()> 0 && idControllo.size()== 0){
			idEntita= 570;
			for (int i: idControlloLoco) {
				vc2.addAll(integrazioniDAO.getIntegrazioni(i, idEntita));}
			for(int i =0; i<vc2.size(); i++) {
			if(integrazioniDAO.getLettera(vc2.get(i).getIdIntegrazione()).getNumProtocollo()!= null) {
				vc.add(vc2.get(i));
			}}
			for(IntegrazioniControlliInLocoVO integr: vc) {
				integr.setNumProtocollo(integrazioniDAO.getLettera(integr.getIdIntegrazione()).getNumProtocollo());
				if(integrazioniDAO.getStatoProroga(integr.getIdIntegrazione()).isEmpty()) {
					integr.setStatoProroga(null);
					}else {
					integr.setStatoProroga(integrazioniDAO.getStatoProroga(integr.getIdIntegrazione()).get(0).getStatoProroga());}
				int pos=0;
				for(IntegrazioniControlliInLocoVO v : integrazioniDAO.getAllegati(integr.getIdIntegrazione())) {
					if(v.getFlagEntita() != null) {
						pos++;
					}
					}
					if(pos==0) {
				          integr.setAllegatiInseriti(false); 
						}else {
							integr.setAllegatiInseriti(true); 
						}
		        
			}
			
			}
		
		return Response.ok(vc).build();
	}
	

	@Override
	public Response getAllegati(int idIntegrazione, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		// TODO Auto-generated method stub
		return Response.ok(integrazioniDAO.getAllegati(idIntegrazione)).build();
	}


	@Override
	public Response inserisciLetteraAllegato(Long idIntegrazione, List<IntegrazioniControlliInLocoVO> allegati,
			HttpServletRequest req) throws ErroreGestitoException, Exception {
		// TODO Auto-generated method stub
		return Response.ok(integrazioniDAO.insertFileEntita(allegati, idIntegrazione)).build();
	}


	@Override
	public Response aggiornaIntegrazione(int idIntegrazione, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok(integrazioniDAO.aggiornaIntegrazione( userInfo.getIdUtente(), idIntegrazione)).build();
	}


	@Override
	public Response deleteAllegato(IntegrazioniControlliInLocoVO cv, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		// TODO Auto-generated method stub
		return Response.ok(integrazioniDAO.deleteAllegato(cv.getIdFileEntita())).build();
	}



	@Override
	public Response getAbilitaRichProroga(int idIntegrazione, int idControllo, int idProgetto, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		List<Integer> idContr = new ArrayList<Integer>();
		int idEntita;
		List<IntegrazioniControlliInLocoVO> cp = new ArrayList<IntegrazioniControlliInLocoVO>();
		idContr= integrazioniDAO.getIdControlloLoco(idProgetto);
		
		if (idContr.size()== 0) {
			idContr = integrazioniDAO.getIdControllo(idProgetto);
			for(int i: idContr) {
			if(i == idControllo) {
			idEntita = 608;
			cp.addAll(integrazioniDAO.getStatoProroga( idIntegrazione));
			for(IntegrazioniControlliInLocoVO cp2 : cp) {
			cp2.setRichProroga(integrazioniDAO.getAbilitaRichProroga(idControllo, idEntita).getRichProroga());
			cp2.setStatoRich(integrazioniDAO.getAbilitaRichProroga(idControllo, idEntita).getStatoRich());
			}}}
		}else {
			for(int i: idContr) {
			if(i == idControllo) {
			idEntita= 570;
			cp.addAll(integrazioniDAO.getStatoProroga( idIntegrazione));
			for(IntegrazioniControlliInLocoVO cp2 : cp) {
			cp2.setRichProroga(integrazioniDAO.getAbilitaRichProroga(idControllo, idEntita).getRichProroga());
			cp2.setStatoRich(integrazioniDAO.getAbilitaRichProroga(idControllo, idEntita).getStatoRich());
			}}}
			}
		
		
		return Response.ok(cp).build();
	
	}

	@Override
	public Response getLetteraIstruttore(int idIntegrazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		return Response.ok(integrazioniDAO.getLettera(idIntegrazione)).build();
	}


	@Override
	public Response inserisciRichProroga(Long idIntegrazione, IntegrazioniControlliInLocoVO cp, HttpServletRequest req)
			throws ErroreGestitoException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok(integrazioniDAO.inserisciRichProroga(cp.getGgRichiesti(), cp.getMotivazione(), idIntegrazione, userInfo.getIdUtente())).build();
	}
}
