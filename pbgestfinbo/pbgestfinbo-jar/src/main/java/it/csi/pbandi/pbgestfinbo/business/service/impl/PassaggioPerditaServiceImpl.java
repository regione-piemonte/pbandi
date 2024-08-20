/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.pbandi.pbgestfinbo.business.service.PassaggioPerditaService;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PassaggioPerditaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.PassaggioPerditaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.TransazioneVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Service
@Transactional
public class PassaggioPerditaServiceImpl implements PassaggioPerditaService {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	PassaggioPerditaDAO passaggioPerditaDAO; 

	@Override
	public Response salvaPassaggioPerdita(PassaggioPerditaVO passaggioPerdita, int idModalitaAgev) {
		return Response.ok(passaggioPerditaDAO.salvaPassaggioPerdita(passaggioPerdita, idModalitaAgev)).build();
	}

	@Override
	public Response getPassaggioPerdita(Long idProgetto, int idModalitaAgev) {
		return Response.ok(passaggioPerditaDAO.getPassaggioPerdita(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoPassaggioPerdita(Long idProgetto, int idModalitaAgev) {
		return Response.ok(passaggioPerditaDAO.getStoricoPassaggioPerdita(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response salvaTransazione(TransazioneVO transazioneVO, int idModalitaAgev) {
		return Response.ok(passaggioPerditaDAO.salvaTransazione(transazioneVO, idModalitaAgev)).build();
	}

	@Override
	public Response getTransazione(Long idProgetto, int idModalitaAgev) {
		return Response.ok(passaggioPerditaDAO.getTransazione(idProgetto, idModalitaAgev)).build();
	}

	@Override
	public Response getStoricoTransazioni(Long idProgetto, int idModalitaAgev) {
		return Response.ok(passaggioPerditaDAO.getStoricoTransazioni(idProgetto, idModalitaAgev)).build();
	}


	// Note Generali
	
	// Spostate in AttivitaIstruttoreAreaCreditiService //

	/* Vecchio metodo - guarda EXPsalvaNota per il metodo aggiornato
	@Override
	public Response salvaNote(MultipartFormDataInput form, boolean isModifica, int idModalitaAgevolazione) throws Exception {
				
		ObjectMapper objectMapper = new ObjectMapper();
		NoteGeneraliVO noteObj = objectMapper.readValue(form.getFormDataPart("noteGenerali", String.class, null), NoteGeneraliVO.class);
		Long idAnnotazione = passaggioPerditaDAO.salvaNote(noteObj, isModifica, idModalitaAgevolazione);
		//LOG.info(form.getFormDataPart("file", File.class, null));
		Map<String, List<InputPart>> formDataMap = form.getFormDataMap();
		if(idAnnotazione==null) {
			return Response.ok(false).build();
		}
		
		if(isModifica) {
			passaggioPerditaDAO.aggiornaAllegati(noteObj.getAllegatiPresenti(), idAnnotazione);
		}
		
		
		
		for (Map.Entry<String, List<InputPart>> entry : formDataMap.entrySet()) {
		    // Controllo se la chiave è "file"
		    if (entry.getKey().equals("file")) {
		        // Prendere il file e il nome del file dalla entry map
		        List<InputPart> fileList = entry.getValue();
		          
		        if (!fileList.isEmpty()) {
		        	 for (InputPart inputPart : fileList) {
		        		 byte[] file = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
				         String nomeFile = getFileName(inputPart.getHeaders()); 
				         passaggioPerditaDAO.salvaUploadAllegato(file, nomeFile, noteObj.getIdUtenteIns(), new BigDecimal(idAnnotazione), new BigDecimal(69), new BigDecimal(noteObj.getIdProgetto()), new BigDecimal(525));     	    
		        	 }
		            //InputPart inputPart = fileList.get(0);
		            
		        }
		    }
		}
		
		return Response.ok(true).build();
	}*/
	
	/*public static String getFileName(MultivaluedMap<String, String> header) {

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
	}*/

	/*@Override
	public Response getNote(Long idProgetto, int idModalitaAgevolazione) {
		return Response.ok(passaggioPerditaDAO.getNoteGenerale(idProgetto, idModalitaAgevolazione)).build();
	}

	@Override
	public Response getStoricoNote(Long idProgetto, int idModalitaAgevolazione) {
		return Response.ok(passaggioPerditaDAO.getStoricoNote(idProgetto, idModalitaAgevolazione)).build();
	}*/

	@Override
	public Response getListaBanche(String banca) {
		return Response.ok(passaggioPerditaDAO.getListaBanche(banca)).build();
	}
	
	/*@Override
	public Response EXPsalvaNota(NoteGeneraliVO nuovaNota, boolean isModifica, int idModalitaAgevolazione, HttpServletRequest req) throws Exception {
				
		/*ObjectMapper objectMapper = new ObjectMapper();
		NoteGeneraliVO noteObj = objectMapper.readValue(form.getFormDataPart("noteGenerali", String.class, null), NoteGeneraliVO.class);
		Long idAnnotazione = passaggioPerditaDAO.salvaNote(noteObj, isModifica, idModalitaAgevolazione);
		//LOG.info(form.getFormDataPart("file", File.class, null));
		Map<String, List<InputPart>> formDataMap = form.getFormDataMap();
		if(idAnnotazione==null) {
			return Response.ok(false).build();
		}
		
		if(isModifica) {
			passaggioPerditaDAO.aggiornaAllegati(noteObj.getAllegatiPresenti(), idAnnotazione);
		}
		
		
		
		for (Map.Entry<String, List<InputPart>> entry : formDataMap.entrySet()) {
		    // Controllo se la chiave è "file"
		    if (entry.getKey().equals("file")) {
		        // Prendere il file e il nome del file dalla entry map
		        List<InputPart> fileList = entry.getValue();
		          
		        if (!fileList.isEmpty()) {
		        	 for (InputPart inputPart : fileList) {
		        		 byte[] file = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
				         String nomeFile = getFileName(inputPart.getHeaders()); 
				         passaggioPerditaDAO.salvaUploadAllegato(file, nomeFile, noteObj.getIdUtenteIns(), new BigDecimal(idAnnotazione), new BigDecimal(69), new BigDecimal(noteObj.getIdProgetto()), new BigDecimal(525));     	    
		        	 }
		            //InputPart inputPart = fileList.get(0);
		            
		        }
		    }
		}*//*
		
		//LOG.info(nuovaNota);
		
		Long idAnnotazione = passaggioPerditaDAO.salvaNote(nuovaNota, isModifica, idModalitaAgevolazione);
		if(idAnnotazione==null) {
			return Response.ok(false).build();
		}
		
		if(isModifica) {
			passaggioPerditaDAO.aggiornaAllegati(nuovaNota.getAllegatiPresenti(), idAnnotazione);
		}
		
		if(nuovaNota.getNuoviAllegati() != null) {
			for (GestioneAllegatiVO item : nuovaNota.getNuoviAllegati()) {
				byte[] decodedFile = Base64.getDecoder().decode(item.getNuovoAllegatoBase64().getBytes(StandardCharsets.UTF_8));
			
				passaggioPerditaDAO.salvaUploadAllegato(decodedFile, item.getNomeNuovoAllegato(), nuovaNota.getIdUtenteIns(), new BigDecimal(idAnnotazione), new BigDecimal(69), new BigDecimal(nuovaNota.getIdProgetto()), new BigDecimal(525));
			}
		}
		
		return Response.ok(true).build();
	}*/

}
