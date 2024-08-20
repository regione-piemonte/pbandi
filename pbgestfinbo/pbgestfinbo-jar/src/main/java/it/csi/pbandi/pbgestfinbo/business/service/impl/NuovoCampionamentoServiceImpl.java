/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.filestorage.util.Constants;
import it.csi.pbandi.pbgestfinbo.business.service.NuovoCampionamentoService;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.ProgettiEscludiEstrattiDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.NuovoCampionamentoDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ElenchiProgettiCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.NuovoCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoCampioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroAcqProgettiVO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoElencoProgettiCampione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.FiltroRilevazioniDTO;

import org.apache.log4j.Logger;
@Service
public class NuovoCampionamentoServiceImpl implements NuovoCampionamentoService {
	
	@Autowired
	private NuovoCampionamentoDAO nuovoCampDao; 

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Override
	public Response elaboraNuovoCamp(NuovoCampionamentoVO nuovoCampVO)
			throws InvalidParameterException, Exception {	
		return Response.ok(nuovoCampDao.elaboraNuovoCampionamento(nuovoCampVO)).build();
	}

	@Override
	public Response estrazioneProgetti(ProgettiEscludiEstrattiDTO progetti, Long idUtente)
			throws InvalidParameterException, Exception {
		return Response.ok(nuovoCampDao.estrazioneProgetti(progetti, idUtente)).build();
	}

	@Override
	public Response campionaProgetti(NuovoCampionamentoVO nuovoCampVO, Long idUtente)
			throws InvalidParameterException, Exception {
		return Response.ok(nuovoCampDao.campionaProgetti(nuovoCampVO, idUtente)).build();
	}

	@Override
	public Response importoTotale(NuovoCampionamentoVO nuovoCampVO) throws InvalidParameterException, Exception {
		return Response.ok(nuovoCampDao.getImportoValidatoTotale(nuovoCampVO)).build();
	}

	@Override
	public Response creaControlloLoco(ProgettiEscludiEstrattiDTO progetti, Long idUtente)
			throws InvalidParameterException, Exception {
		return Response.ok(nuovoCampDao.creaControlloLoco(progetti,idUtente)).build();
	}

	@Override
	public Response annullaCampionamento(Long idUtente, Long idCampionamento)
			throws InvalidParameterException, Exception {
	
		return Response.ok(nuovoCampDao.annullaCampionamento(idCampionamento,idUtente)).build();
	}

	@Override
	public Response getNormative(String suggest) throws InvalidParameterException, Exception {
		return Response.ok(nuovoCampDao.getNormative(suggest)).build();
	}

	@Override
	public Response acquisisciProgetti(FiltroAcqProgettiVO filtro) throws InvalidParameterException, Exception {
		
		if (filtro.getIdBandoLineaIntervent() == null || (filtro.getIdBandoLineaIntervent()==0)) 
			return inviaErroreBadRequest("Parametro mancante: normativa");

		if (filtro.getProgetti() == null || (filtro.getProgetti().trim().length()<=0))
			return inviaErroreBadRequest("Parametro mancante: elenco progetti");

		if (filtro.getDataCampione() == null ) {
			return inviaErroreBadRequest("Parametro mancante data campione!");
		}
		if (filtro.getNumCampionamento() == null ) {
			return inviaErroreBadRequest("Parametro mancante numero campionamento!");
		}
		
		//  controllare se il campionamento esiste sulla tabella PBANDI_T_CAMPIONAMENTO_FINP con num_campionamento_Esterno = ? e flag_campionamento_esterno=S
		
		Long idCampionamento = nuovoCampDao.checkCampionamento(filtro.getNumCampionamento());
		if(idCampionamento!=null) {
			return inviaErroreBadRequest("Numero campionamento già esistente: cambiare!"); 
		}
	
	 
			
			String progettiToParse = filtro.getProgetti();
			String[] idProgetti = progettiToParse.trim().split(";");
			
			ArrayList<String> idProgettiList = new ArrayList<String>();
			for (String id : idProgetti) {
				try {
					idProgettiList.add(id);
				} catch (Exception e) {
					LOG.error("Errore di parsificazione dei codici progetto.", e);
					return inviaErroreBadRequest("RequestGetProgettiCampione.filtro.elencoProgetti L'elenco dei progetti campionati non &egrave; scritto correttamente.");
				}
			}
			//Rimozione duplicati
			Set<String> set = new HashSet<>(idProgettiList);
			idProgettiList = new ArrayList<String>();
			idProgettiList.addAll(set);
			
			
			// CARICA PROGETTI CAMPIONE
			ElenchiProgettiCampionamentoVO esito = nuovoCampDao.acquisisciProgetti(filtro, idProgettiList);
						
//		    ElenchiProgettiCampionamentoVO elenchi = new ElenchiProgettiCampionamentoVO();
//			elenchi.setProgettiGiaPresenti(beanUtil.transformToArrayList(esito.getProgettiGiaPresenti(), ProgettoCampioneVO.class));
//			elenchi.setProgettiDaAggiungere(beanUtil.transformToArrayList(esito.getProgettiDaAggiungere(), ProgettoCampioneVO.class));
//			elenchi.setProgettiScartati(esito.getProgettiScartati());
//						
//			elenchi.setProgettiDelCampione(beanUtil.transformToArrayList(esito.getProgettiDelCampione(), ProgettoCampioneVO.class));
			
		return Response.ok(esito).build();
	}
	
	private Response inviaErroreBadRequest(String msg) {
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(false);
		esito.setMessaggio(msg);
		return Response.ok(esito).type( MediaType.APPLICATION_JSON).build();
	}
//
//	@Override
//	public Response confermaAcquisizione(List<ProgettoCampioneVO> progettiDaConfermare,
//			Date dataCampionamento,Long numCampionamento, String descCampionamento, HttpServletRequest request)
//			throws InvalidParameterException, Exception {
//		return Response.ok(nuovoCampDao.confermaAcquisizione(progettiDaConfermare, filtro, request)).build();
//	}

//	@Override
//	public Response confermaAcquisizione(FiltroAcqProgettiVO conferme, HttpServletRequest request)
//			throws InvalidParameterException, Exception {
//		LOG.info(conferme);
//		
//		FiltroAcqProgettiVO filtro = new FiltroAcqProgettiVO();
//		filtro.setDataCampione(conferme.getDataCampione());
//		filtro.setNumCampionamento(conferme.getNumCampionamento());
//		filtro.setDescCampionamento(conferme.getDescCampionamento());
////		nuovoCampDao.confermaAcquisizione(conferme.getProgetti(), filtro, request)
//		return  Response.ok().build();
//	}

	@Override
	public Response confermaProgettiAcquisiti(FiltroAcqProgettiVO filtro, Long idUtente)
			throws InvalidParameterException, Exception {
		
		return Response.ok(nuovoCampDao.confermaAcquisizione(filtro.getProgettiConfermati(), filtro, idUtente)).build();
	}

}
