/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.disimpegni.Revoca;
import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;
import it.csi.pbandi.pbweberog.dto.revoca.DettaglioRevoca;
import it.csi.pbandi.pbweberog.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbweberog.dto.revoca.RigaRevocaItem;
import it.csi.pbandi.pbweberog.integration.request.RequestAssociaIrregolarita;
import it.csi.pbandi.pbweberog.integration.request.RequestFindIrregolarita;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaDisimpegno;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRevoche;

@Path("/disimpegni")
@Api(value = "disimpegni")
public interface GestioneDisimpegniService {

	@GET
	@Path("/esistaProposta") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verificare che per il progetto non esista una proposta di certificazione", response=Boolean.class)
	Response checkPropostaCertificazione( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	
	@GET
	@Path("/modalitaAgevolazione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds modalita agevolazione per disimpegni", response=it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO.class, responseContainer = "List")
	Response getModalitaAgevolazione( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	

	@POST
	@Path("/erogazioni")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds erogazioni", response=RigaModificaRevocaItem.class, responseContainer = "List")
	Response getErogazioni( @Context HttpServletRequest req, it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] modalita) throws UtenteException, Exception;
	
	

	@POST
	@Path("/disimpegni")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds disimpegni", response=RigaModificaRevocaItem.class, responseContainer = "List")
	Response getDisimpegni( @Context HttpServletRequest req, it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] modalita) throws UtenteException, Exception;
	
	//////////////////////////////////////////// MODIFCA DISIMPEGNO //////////////////////////////////////////////////
	@GET
	@Path("/dettaglio") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( MODIFICA DISIMPEGNO ) Finds dettaglio revoca by idProgetto and idRevoca)", response=DettaglioRevoca.class)
	Response getDettaglioRevoca( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto,
													@QueryParam("idRevoca") Long idRevoca) throws UtenteException, Exception;
	
	@GET
	@Path("/revocaConIrregolarita") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( MODIFICA DISIMPEGNO ) check per il messaggio: Non è possibile modificare l’importo della revoca perchè è associato ad una irregolarità)", response=DettaglioRevoca.class)
	Response getRevocaConIrregolarita( @Context HttpServletRequest req, @QueryParam("idRevoca") Long idRevoca) throws Exception;
	
	@PUT
	@Path("/disimpegno")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates disimpegno", response=EsitoSalvaRevocaDTO.class)
	Response modificaDisimpegno( @Context HttpServletRequest req, RequestModificaDisimpegno modificaRequest) throws UtenteException, Exception;
	
	
	//////////////////////////////////////////// NUOVO DISIMPEGNO //////////////////////////////////////////////////
	@GET
	@Path("/motivazioni") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( NUOVO DISIMPEGNO ) Finds Motivazioni (init combo motivazioni)", response=CodiceDescrizione.class, responseContainer = "List")
	Response getMotivazioni( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/causaleDisimpegno") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( NUOVO DISIMPEGNO ) Finds Causale Disimpegno (init combo Causale Disimpegno)", response=CodiceDescrizione.class, responseContainer = "List")
	Response getCausaleDisimpegno( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	@GET
	@Path("/annoContabile") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( NUOVO DISIMPEGNO ) Finds Anno Contabile (init combo Anno Contabile)", response=CodiceDescrizione.class, responseContainer = "List")
	Response getAnnoContabile( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	@GET
	@Path("/modalitaRecupero") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( NUOVO DISIMPEGNO ) Finds Modalita Recupero (init combo Modalita Recupero)", response=CodiceDescrizione.class, responseContainer = "List")
	Response getModalitaRecupero( @Context HttpServletRequest req) throws UtenteException, Exception;
	

	@GET
	@Path("/importoValidatoTotale") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( NUOVO DISIMPEGNO ) Finds Importo Validato Totale", response=Double.class)
	Response getImportoValidatoTotale( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/revoche") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "( NUOVO DISIMPEGNO ) Finds revoche by idProgetto", response=RigaRevocaItem.class, responseContainer = "List")
	Response getRevoche( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@POST
	@Path("/revoche")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves dismpegni", response=EsitoSalvaRevocaDTO.class)
	Response salvaDisimpegni( @Context HttpServletRequest req, RequestSalvaRevoche requestSalva) throws UtenteException, Exception;
	
	@DELETE
	@Path("/revoche")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deletes revoca by idRevoca and idProgetto", response=EsitoSalvaRevocaDTO.class)
	Response eliminaRevoca( @Context HttpServletRequest req, @QueryParam("idRevoca") Long idRevoca, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	
	//ASSOCIAZIONE TRA REVOCA E IRREGOLARELARITA
	@GET
	@Path("/dsIrregolarita") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Ds irregolarita", response=Long.class, responseContainer = "List")
	Response findDsIrregolarita( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/classRevocaIrreg") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds class revoca irregolarita", response=Decodifica.class, responseContainer = "List")
	Response findClassRevocaIrreg( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@POST
	@Path("/irregolarita") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds irregolarita", response=Revoca.class)
	Response findIrregolarita( @Context HttpServletRequest req, RequestFindIrregolarita requestFindIrregolarita) throws UtenteException, Exception;
	

	@PUT
	@Path("/irregolarita")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves Irregolarita", response=EsitoSalvaRevocaDTO.class)
	Response salvaIrregolarita( @Context HttpServletRequest req, RequestAssociaIrregolarita requestSalva) throws UtenteException, Exception;
	
	
	
}
