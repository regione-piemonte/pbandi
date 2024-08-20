/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.pbandi.pbgestfinbo.business.service.ControlliPreErogazioneService;
import it.csi.pbandi.pbgestfinbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.ControlloPreErogazioneDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.GestioneRichiesteDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.DistintaInterventiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ControlloNonApplicabileVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.NuovaRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SaveControlloPreErogazioneVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class ControlliPreErogazioneServiceImpl implements ControlliPreErogazioneService{
	@Autowired
	protected ControlloPreErogazioneDAO controlloPreErogazioneDAO;

	@Autowired
	protected AnagraficaBeneficiarioDAO anagraficaBeneficiarioDAO;

	@Autowired
	protected GestioneRichiesteDAO gestioneRichiesteDAO;
	@Autowired
	protected IterAutorizzativiDAO iterAutorizzativiDAO;

	@Override
	public Response fetchData(Long idProposta, HttpServletRequest req) throws Exception {
		return Response.ok().entity(controlloPreErogazioneDAO.fetchData(idProposta)).build();
	}

	@Override
	public Response getBloccoSoggetto(Long idSoggetto, HttpServletRequest req) {
		List<BloccoVO> list = anagraficaBeneficiarioDAO.getElencoBlocchi(BigDecimal.valueOf(idSoggetto), true);
		list.addAll(anagraficaBeneficiarioDAO.getStoricoBlocchi(idSoggetto, true));

		return Response.ok().entity(list).build();
	}

	@Override
	public Response getBloccoDomanda(Long idSoggetto, String numeroDomanda, HttpServletRequest req) {
		List<BloccoVO> list = anagraficaBeneficiarioDAO.getElencoBlocchiSoggettoDomanda(String.valueOf(idSoggetto), numeroDomanda, true);
		list.addAll(anagraficaBeneficiarioDAO.getStoricoBlocchiDomanda(idSoggetto, numeroDomanda, true));

		return Response.ok().entity(list).build();
	}

	@Override
	public Response getControlloDurc(Long idProgetto, HttpServletRequest req) throws Exception {
		return Response.ok().entity(controlloPreErogazioneDAO.getControlloDurc(idProgetto)).build();
	}

	@Override
	public Response getControlloAntimafia(Long idProgetto, HttpServletRequest req) throws Exception {
		return Response.ok().entity(controlloPreErogazioneDAO.getControlloAntimafia(idProgetto)).build();
	}


	@Override
	public Response getControlloDeggendorf(Long idSoggetto, HttpServletRequest req) throws Exception {
		return Response.ok().entity(controlloPreErogazioneDAO.getControlloDeggendorf(idSoggetto)).build();
	}

	@Override
	public Response getControlloNonApplicabile(Long idProposta, Long idTipoRichiesta, HttpServletRequest req) throws Exception {
		return Response.ok().entity(controlloPreErogazioneDAO.getControlloNonApplicabile(idProposta, idTipoRichiesta)).build();
	}

	@Override
	public Response inviaRichiesta(NuovaRichiestaVO nuovaRichiestaVO, HttpServletRequest req) throws Exception {
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		nuovaRichiestaVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));

		return Response.ok().entity(gestioneRichiesteDAO.insertNuovaRichiesta(nuovaRichiestaVO, req) == 1).build();
	}

	@Override
	public Response controlloNonApplicabile(ControlloNonApplicabileVO controlloNonApplicabileVO, HttpServletRequest req) throws Exception {
		return Response.ok().entity(controlloPreErogazioneDAO.controlloNonApplicabile(controlloNonApplicabileVO.getIdProposta(),
				controlloNonApplicabileVO.getIdTipoRichiesta(), controlloNonApplicabileVO.getMotivazione(), req)).build();
	}

	@Override
	public Response saveControlliPreErogazione(SaveControlloPreErogazioneVO saveControlloPreErogazioneVO, HttpServletRequest req) throws Exception {
		return Response.ok().entity(controlloPreErogazioneDAO.saveControlliPreErogazione(saveControlloPreErogazioneVO.getIdProposta(),
				saveControlloPreErogazioneVO.getIdSoggetto(), saveControlloPreErogazioneVO.getEsitoDeggendorf(), saveControlloPreErogazioneVO.getVercor(), req)).build();
	}

	public Response checkControlliPreErogazione(Long idProposta, HttpServletRequest req) {
		return Response.ok().entity(controlloPreErogazioneDAO.checkControlliPreErogazione(idProposta, req)).build();
	}

	@Override
	public Response avviaIterEsitoValidazione(Long idProposta, MultipartFormDataInput multipartFormData, HttpServletRequest req) {
		Object result;
		try {
			//CONTROLLI
			if(idProposta == null)
				throw new ErroreGestitoException("Variabile idProposta non valorizzata");

			//CHIAMATA A DAO --->>
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idEntita = controlloPreErogazioneDAO.getIdEntitaPropostaErogazione();
			Long idProgetto = controlloPreErogazioneDAO.getIdProgetto(idProposta);

			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();

			//lettereAccompagnatoria
			try{
				List<InputPart> letteraAccompagnatoria = map.get("letteraAccompagnatoria");
				String visibilitaLettera = multipartFormData.getFormDataPart("visibilitaLettera", String.class, null);
				controlloPreErogazioneDAO.aggiungiAllegato(BigDecimal.valueOf(idProposta), BigDecimal.valueOf(idEntita), null, BigDecimal.valueOf(60), BigDecimal.valueOf(userInfoSec.getIdUtente()), IOUtils.toByteArray(letteraAccompagnatoria.get(0).getBody(InputStream.class, null)), getFileName(letteraAccompagnatoria.get(0).getHeaders()), visibilitaLettera);
			} catch (Exception e){
				throw new ErroreGestitoException("Lettera Accompagnatoria non valorizzata correttamente");
			}

			//reportValidazioneSpesa facoltativo
			try {
				List<InputPart> reportValidazioneSpesa = map.get("reportValidazioneSpesa");
				String visibilitaReport = multipartFormData.getFormDataPart("visibilitaReport", String.class, null);
				controlloPreErogazioneDAO.aggiungiAllegato(BigDecimal.valueOf(idProposta), BigDecimal.valueOf(idEntita), null, BigDecimal.valueOf(63), BigDecimal.valueOf(userInfoSec.getIdUtente()), IOUtils.toByteArray(reportValidazioneSpesa.get(0).getBody(InputStream.class, null)), getFileName(reportValidazioneSpesa.get(0).getHeaders()), visibilitaReport);
			} catch (Exception ignored){ }

			//checklistInterna
			try {
				List<InputPart> checklistInterna = map.get("checklistInterna");
				String visibilitaChecklist = multipartFormData.getFormDataPart("visibilitaChecklist", String.class, null);
				controlloPreErogazioneDAO.aggiungiAllegato(BigDecimal.valueOf(idProposta), BigDecimal.valueOf(idEntita), null, BigDecimal.valueOf(33), BigDecimal.valueOf(userInfoSec.getIdUtente()), IOUtils.toByteArray(checklistInterna.get(0).getBody(InputStream.class, null)), getFileName(checklistInterna.get(0).getHeaders()), visibilitaChecklist);
			} catch (Exception e){
				throw new ErroreGestitoException("Checklist interna non valorizzata correttamente");
			}

			String errore = iterAutorizzativiDAO.avviaIterAutorizzativo(18L, idEntita, idProposta, idProgetto, userInfoSec.getIdUtente());

			if(errore.equals("")) {
				result = new ResponseCodeMessage("OK", "Iter autorizzativo avviato con successo!");
			}else {
				result = new ResponseCodeMessage("KO", errore);
			}		}
		catch(Exception e){
			result = new ResponseCodeMessage("KO", e.getMessage());
		}

		return Response.ok().entity(result).build();
	}

	@Override
	public Response avviaIterCommunicazioneIntervento(Long idProposta, MultipartFormDataInput multipartFormData, HttpServletRequest req) {
		Object result;
		try {
			//CONTROLLI
			if(idProposta == null)
				throw new ErroreGestitoException("Variabile idProposta non valorizzata");

			//CHIAMATA A DAO --->>
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idEntita = controlloPreErogazioneDAO.getIdEntitaPropostaErogazione();
			Long idProgetto = controlloPreErogazioneDAO.getIdProgetto(idProposta);

			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();

			//lettereAccompagnatoria
			try{
				List<InputPart> letteraAccompagnatoria = map.get("letteraAccompagnatoria");
				String visibilitaLettera = multipartFormData.getFormDataPart("visibilitaLettera", String.class, null);
				controlloPreErogazioneDAO.aggiungiAllegato(BigDecimal.valueOf(idProposta), BigDecimal.valueOf(idEntita), null, BigDecimal.valueOf(84), BigDecimal.valueOf(userInfoSec.getIdUtente()), IOUtils.toByteArray(letteraAccompagnatoria.get(0).getBody(InputStream.class, null)), getFileName(letteraAccompagnatoria.get(0).getHeaders()), visibilitaLettera);
			} catch (Exception e){
				throw new ErroreGestitoException("Lettera Accompagnatoria non valorizzata correttamente");
			}

			String errore = iterAutorizzativiDAO.avviaIterAutorizzativo(19L, idEntita, idProposta, idProgetto, userInfoSec.getIdUtente());

			if(errore.equals("")) {
				result = new ResponseCodeMessage("OK", "Iter autorizzativo avviato con successo!");
			}else {
				result = new ResponseCodeMessage("KO", errore);
			}
		}
		catch(Exception e){
			result = new ResponseCodeMessage("KO", e.getMessage());
		}

		return Response.ok().entity(result).build();
	}

	@Override
	@Transactional
	public Response creaDistintaInterventi(MultipartFormDataInput multipartFormData, HttpServletRequest req) {
		Object result;
		try {
			//CONTROLLI

			DistintaInterventiVO distintaInterventiVO;
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				distintaInterventiVO = objectMapper.readValue(multipartFormData.getFormDataPart("infoDistinta", String.class, null), DistintaInterventiVO.class);
			}catch (Exception e){
				throw new ErroreGestitoException("Errore nella ricezione dei parametri");
			}

			if(distintaInterventiVO.getDescrizione() == null || distintaInterventiVO.getDescrizione().isEmpty()){
				throw new ErroreGestitoException("Inserire la descrizione");
			}

			Long idProgetto = controlloPreErogazioneDAO.getIdProgetto(distintaInterventiVO.getIdProposta());

			//CHIAMATA A DAO --->>
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

			Long idEntita = controlloPreErogazioneDAO.getIdEntitaDistinta();

			//Aggiorna proposta erogazione e crea proposte erogazioni figlie per interventi sostitutivi
			controlloPreErogazioneDAO.inserisciInterventiSostitutivi(distintaInterventiVO.getIdProposta(), distintaInterventiVO.getListaInterventi(), req);

			//Creo distinta
			Long idDistinta = controlloPreErogazioneDAO.creaDistintaInterventiSostitutivi(distintaInterventiVO.getIdProposta(), distintaInterventiVO.getDescrizione(), req);

			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();

			//lettereAccompagnatoria
			try{
				List<InputPart> letteraAccompagnatoria = map.get("letteraAccompagnatoria");
				String visibilitaLettera = multipartFormData.getFormDataPart("visibilitaLettera", String.class, null);
				controlloPreErogazioneDAO.aggiungiAllegato(BigDecimal.valueOf(idDistinta), BigDecimal.valueOf(idEntita), idProgetto, BigDecimal.valueOf(42), BigDecimal.valueOf(userInfoSec.getIdUtente()), IOUtils.toByteArray(letteraAccompagnatoria.get(0).getBody(InputStream.class, null)), getFileName(letteraAccompagnatoria.get(0).getHeaders()), visibilitaLettera);
			} catch (Exception e){
				throw new ErroreGestitoException("Lettera Accompagnatoria non valorizzata correttamente");
			}

			//reportValidazioneSpesa
			try {
				List<InputPart> reportValidazioneSpesa = map.get("reportValidazioneSpesa");
				String visibilitaReport = multipartFormData.getFormDataPart("visibilitaReport", String.class, null);
				controlloPreErogazioneDAO.aggiungiAllegato(BigDecimal.valueOf(idDistinta), BigDecimal.valueOf(idEntita), idProgetto, BigDecimal.valueOf(63), BigDecimal.valueOf(userInfoSec.getIdUtente()), IOUtils.toByteArray(reportValidazioneSpesa.get(0).getBody(InputStream.class, null)), getFileName(reportValidazioneSpesa.get(0).getHeaders()), visibilitaReport);
			} catch (Exception ignored){ }

			//checklistInterna
			try {
				List<InputPart> checklistInterna = map.get("checklistInterna");
				String visibilitaChecklist = multipartFormData.getFormDataPart("visibilitaChecklist", String.class, null);
				controlloPreErogazioneDAO.aggiungiAllegato(BigDecimal.valueOf(idDistinta), BigDecimal.valueOf(idEntita), idProgetto, BigDecimal.valueOf(33), BigDecimal.valueOf(userInfoSec.getIdUtente()), IOUtils.toByteArray(checklistInterna.get(0).getBody(InputStream.class, null)), getFileName(checklistInterna.get(0).getHeaders()), visibilitaChecklist);
			} catch (Exception e){
				throw new ErroreGestitoException("Checklist interna non valorizzata correttamente");
			}

			//Avvia iter
			String errore = iterAutorizzativiDAO.avviaIterAutorizzativo(13L, idEntita, idDistinta, null, userInfoSec.getIdUtente());
			if(errore.equals("")) {
				result = new ResponseCodeMessage("OK", "Iter autorizzativo avviato con successo!");
			}else {
				result = new ResponseCodeMessage("KO", errore);
			}
		}
		catch(Exception e){
			result = new ResponseCodeMessage("KO", e.getMessage());
		}

		return Response.ok().entity(result).build();
	}

	@Override
	public Response getDestinatariInterventi(HttpServletRequest req) {
		Object result;
		try {
			//CHIAMATA A DAO --->>
			result = controlloPreErogazioneDAO.getDestinatariInterventi();
		}
		catch(Exception e){
			result = new ResponseCodeMessage("KO", e.getMessage());
		}

		return Response.ok().entity(result).build();
	}

	@Override
	public Response salvaImportoDaErogare(Long idProposta, BigDecimal importoDaErogare, HttpServletRequest req) {
		//CHIAMATA A DAO --->>
		return Response.ok().entity(controlloPreErogazioneDAO.salvaImportoDaErogare(idProposta, importoDaErogare, req)).build();
	}

	//UTILS
	public static String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String value : contentDisposition) {
			if (value.trim().startsWith("filename")) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}

}
