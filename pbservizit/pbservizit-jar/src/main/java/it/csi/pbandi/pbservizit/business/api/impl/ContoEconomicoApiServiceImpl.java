/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.ContoEconomicoApi;
import it.csi.pbandi.pbservizit.dto.contoeconomico.VoceDiSpesaPreventivataDTO;
import it.csi.pbandi.pbservizit.integration.dao.ContoEconomicoDAO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VoceDiEntrataCulturaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSpesaPreventivataVO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.Constants;
import java.math.BigInteger;

@Service
public class ContoEconomicoApiServiceImpl implements ContoEconomicoApi {
	
	@Autowired
	private ContoEconomicoDAO contoEconomicoDAO;

	Logger log = Logger.getLogger(getClass().getName());
	@Override 
	public Response inizializzaVisualizzaContoEconomico (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.inizializzaVisualizzaContoEconomico(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
	
	@Override 
	public Response aggiornaVisualizzaContoEconomico (Long idProgetto, String tipoRicerca, Long idPartner, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(contoEconomicoDAO.aggiornaVisualizzaContoEconomico(idProgetto, tipoRicerca, idPartner, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}


	@Override 
	public Response getSpesaAmmessaContoEconomico (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		ContoEconomicoDTO contoEconomico = contoEconomicoDAO.trovaContoEconomico(idProgetto);
		return Response.ok().entity(contoEconomico != null ? contoEconomico.getImportoSpesaAmmessa() : null).build();
	}
	
	@Override
	public Response vociDiSpesaCultura (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.vociDiSpesaCultura(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}

	@Override
	public Response vociDiEntrataCultura (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.vociDiEntrataCultura(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}

	@Override
	public Response contoEconomicoProposta(Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.trovaContoEconomicoProposta(idProgetto, userInfo.getIdUtente())).build();
	}

	@Override
	public Response salvaVociDiEntrataCultura(Map<String, Object> requestData, HttpServletRequest req) throws Exception {
		log.info("salvaVociDiEntrataCultura with the following params: " + requestData.toString());
		log.info(Long.parseLong(requestData.get("idProgetto").toString()));
		log.info(requestData.get("vociDiEntrataCultura").toString());
		List<VoceDiEntrataCulturaDTO> vociDiEntrata = new ArrayList<>();
		Long idProgetto = null;
		UserInfoSec userInfo;
		try {
			userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			idProgetto = Long.parseLong(requestData.get("idProgetto").toString());
			List<Map<String, Object>> vociDiEntrataList = (List<Map<String, Object>>) requestData.get("vociDiEntrataCultura");
			if (vociDiEntrataList != null && !vociDiEntrataList.isEmpty()) {
				for (Map<String, Object> voceDiEntrataMap : vociDiEntrataList) {
					VoceDiEntrataCulturaDTO voceDiEntrata = new VoceDiEntrataCulturaDTO();
					if(voceDiEntrataMap.get("idConsuntivoEntrata") != null)
						voceDiEntrata.setIdConsuntivoEntrata(new Long(String.valueOf(voceDiEntrataMap.get("idConsuntivoEntrata"))));
					voceDiEntrata.setIdRigoContoEconomico(new Long(String.valueOf(voceDiEntrataMap.get("idRigoContoEconomico"))));
					voceDiEntrata.setImportoAmmesso(new BigDecimal(String.valueOf(voceDiEntrataMap.get("importoAmmesso"))));
					voceDiEntrata.setCompletamento((String) voceDiEntrataMap.get("completamento"));
					voceDiEntrata.setIdContoEconomico(new Long(String.valueOf(voceDiEntrataMap.get("idContoEconomico"))));
					voceDiEntrata.setIdVoceDiEntrata(new Long(String.valueOf(voceDiEntrataMap.get("idVoceDiEntrata"))));
					voceDiEntrata.setFlagEdit((String) voceDiEntrataMap.get("flagEdit"));
					vociDiEntrata.add(voceDiEntrata);
				}
			} else return Response.status(204).build();


		if (idProgetto < 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}

		if (userInfo.getIdUtente() == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		for (VoceDiEntrataCulturaDTO voceDiEntrata : vociDiEntrata) {
			log.info(voceDiEntrata.toString());
			if (voceDiEntrata.getIdVoceDiEntrata() < 0) {
				throw new InvalidParameterException("idVoceDiEntrata non valorizzato.");
			}
			if(voceDiEntrata.getImportoAmmesso() == null){
				voceDiEntrata.setImportoAmmesso(BigDecimal.valueOf(0));
			}
			if (voceDiEntrata.getImportoAmmesso().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidParameterException("importo non valorizzato correttamente.");
			}
		}

		} catch (Exception e) {
			log.error("Errore nel recupero dei parametri.");
			log.info(e.getMessage());
			return Response.status(400).build();
		}
		boolean result = contoEconomicoDAO.salvaVociDiEntrataCultura(vociDiEntrata, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride());
		log.info("vociDiEntrata" + vociDiEntrata);

		if (!result) return Response.status(500).build();
		else return Response.ok().build();
	}


	@Override
	public Response percImportoAgevolato(Long idBando, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if (idBando == null || idBando < 0 || userInfo.getIdUtente() == null) {
			return Response.status(400).build();
		}
		Double perc;
		try {
			perc = contoEconomicoDAO.getPercentualeImportoAgevolato(idBando, userInfo.getIdUtente());
		}
		catch (Exception e) {
			log.error("Errore nel recupero della percentuale di importo agevolato.");
			log.error(e.getMessage());
			return Response.status(404).build();
		}
		if (perc == null) {
			return Response.status(404).build();
		}
		return Response.ok().entity(perc).build();
	}


	@Override
	public Response salvaSpesePreventivate(Map<String, Object> requestData, HttpServletRequest req) throws Exception {
		log.info("salvaSpesePreventivate INIZIO");
		log.debug("salvaSpesePreventivate with the following params: " + requestData.toString());
		//Uso ContoEconomicoRimodulazioneDTO per non fare una classe nuova per 2 campi
		ArrayList<VoceDiSpesaPreventivataDTO> vociDiSpesa = new ArrayList<>();
		UserInfoSec userInfo;
		try {
			userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			List<Map<String, Object>> spesePreventivateList = (List<Map<String, Object>>) requestData.get("spesePreventivate");
			if (spesePreventivateList == null || spesePreventivateList.isEmpty()) return Response.status(400).build();

			for (Map<String, Object> spesaPreventivataMap : spesePreventivateList) {
				VoceDiSpesaPreventivataDTO voceDiSpesa = new VoceDiSpesaPreventivataDTO();
				String idVoce = String.valueOf(spesaPreventivataMap.get("idVoce"));
				String idContoEconomico = String.valueOf(spesaPreventivataMap.get("idContoEconomico"));
				String importoSpesaPreventivata = String.valueOf(spesaPreventivataMap.get("importoSpesaPreventivata"));
				String idRigoContoEconomico = String.valueOf(spesaPreventivataMap.get("idRigoContoEconomico"));

				if(idRigoContoEconomico.equals("null"))
					idRigoContoEconomico = "0";

				if(Long.parseLong(idRigoContoEconomico) < 0) {
					log.error("idRigoContoEconomico non valorizzato");
					throw new InvalidParameterException("idRigoContoEconomico non valorizzato");
				}
				if(importoSpesaPreventivata.equals("null") || Double.parseDouble(importoSpesaPreventivata) < 0)
					importoSpesaPreventivata = "0";

				if(idVoce.equals("null") || Long.parseLong(idVoce) < 0) {
					log.error("idVoce (DiSpesa) non valorizzato");
					throw new InvalidParameterException("idVoce (DiSpesa) non valorizzato");
				}
				if(idContoEconomico.equals("null") || Long.parseLong(idContoEconomico) < 0) {
					log.error("idContoEconomico non valorizzato");
					throw new InvalidParameterException("idContoEconomico non valorizzato");
				}
				voceDiSpesa.setImportoSpesaPreventivata(new BigDecimal(importoSpesaPreventivata));
				voceDiSpesa.setIdVoceDiSpesa(new BigDecimal(idVoce));
				voceDiSpesa.setIdContoEconomico(new BigDecimal(idContoEconomico));
				voceDiSpesa.setIdRigoContoEconomico(new BigDecimal(idRigoContoEconomico));
				vociDiSpesa.add(voceDiSpesa);
			}

			if (userInfo.getIdUtente() == null) {
				log.error("idUtente non valorizzato");
				throw new InvalidParameterException("idUtente non valorizzato");
			}

		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
		boolean result = contoEconomicoDAO.salvaSpesePreventivate(vociDiSpesa, userInfo.getIdUtente(), userInfo.getIdIride());

		log.info("salvaSpesePreventivate FINE");
		return result ? Response.ok().build() : Response.status(500).build();
	}
	
	@Override
	public Response inviaDichiarazioneDiSpesaCultura(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.inviaDichiarazioneDiSpesaCultura(multipartFormData,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response vociDiEntrataPerRimodulazione (Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoDAO.vociDiEntrataPerRimodulazione(idProgetto, userInfo.getIdUtente(),userInfo.getIdIride())).build();
	}
}
