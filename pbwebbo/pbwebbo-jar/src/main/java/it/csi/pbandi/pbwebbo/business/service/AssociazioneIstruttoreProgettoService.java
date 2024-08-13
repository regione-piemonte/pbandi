/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/associazioni")
public interface AssociazioneIstruttoreProgettoService {
	
	
	//Popola la drop down "Bando" nella schermata iniziale
	@GET
	@Path("cercabandi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercabandi( @QueryParam("idU") Long idU,
						@Context HttpServletRequest req) throws Exception;
	
	
	//Ritorna la lista di istruttori
	@GET
	@Path("cercaistruttore") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaIstruttore( @QueryParam("idU") Long idU,
							  @QueryParam("idSoggetto") Long idSoggetto,  
							  @QueryParam("nome") String nome,
							  @QueryParam("cognome") String cognome,
							  @QueryParam("codicefiscale") String Codicefiscale,
							  @QueryParam("idBando") Long idBando,
							  @QueryParam("tipoAnagrafica") String tipoAnagrafica,
							  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("gestisciassociazioni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response gestisciAssociazioni( @QueryParam("idU") Long idU,
								   @QueryParam("idSoggettoIstruttore") Long idSoggettoIstruttore,  //Corrisponde all'id dell'istruttore selezionato 
								   @QueryParam("isIstruttoreAffidamenti") Boolean isIstruttoreAffidamenti,
								   @Context HttpServletRequest req) throws Exception;
	
	//Disassocia il progetto dall'istruttore selezionato
	@GET
	@Path("disprogetti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaProgettiAIstruttore( @QueryParam("idU") Long idU,
											@QueryParam("idSoggetto") Long idSoggetto,
											@QueryParam("codRuolo") String codRuolo,      //Corrispone al ruolo selezionato. ES: ADG-IST-MASTER
											@QueryParam("progrSoggettoProgetto") String progrSoggettoProgetto,  //Corrisponde all'id del progetto selezionato da disassociare
											@Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("cercaprogettibybandolinea") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaProgettiByBandoLinea( @QueryParam("idU") Long idU,
								   @QueryParam("idBandoLinea") Long idBandoLinea,
								   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercabeneficiari") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaBeneficiari( @QueryParam("idU") Long idU,
							   @QueryParam("idBandoLinea") Long idBandoLinea,
							   @QueryParam("idProgetto") Long idProgetto,
							   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercaprogettibybeneficiario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaProgettiByBeneficiario( @QueryParam("idU") Long idU,
									      @QueryParam("idBandoLinea") Long idBandoLinea,
									      @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
									      @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("findprogettidaassociare") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findProgettiDaAssociare( @QueryParam("idU") Long idU,
									  @QueryParam("idBandoLinea") Long idBandoLinea,
								      @QueryParam("idProgetto") Long idProgetto,
								      @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
								      @QueryParam("idSoggetto") Long idSoggetto,
								      @QueryParam("isIstruttoriAssociati") boolean isIstruttoriAssociati,
								      @QueryParam("isIstruttoreAffidamenti") Boolean isIstruttoreAffidamenti,
								      @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("associaprogettiaistruttore") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaProgettiAIstruttore( @QueryParam("idU") Long idU,
								         @QueryParam("idProgetto") String idProgetto,
								         @QueryParam("idSoggettoIstruttore") Long idSoggettoIstruttore,  //Corrisponde all'id dell'istruttore selezionato dopo aver effettuato la ricerca degli istruttori
								         @QueryParam("idSoggetto") Long idSoggetto,
								         @QueryParam("codRuolo") String codRuolo,
								         @QueryParam("isIstruttoreAffidamenti") Boolean isIstruttoreAffidamenti,
								         @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("bandolineaassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findBandolinaGiaAssociatiAIstruttore( @QueryParam("idU") Long idU,
												   @QueryParam("idSoggettoIstruttore") Long idSoggettoIstruttore,
												   @QueryParam("isIstruttoreAffidamenti") Boolean isIstruttoreAffidamenti,
												   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("dettaglioistruttori") 
	@Produces(MediaType.APPLICATION_JSON)
	Response dettaglioIstruttori( @QueryParam("idU") Long idU,
								  @QueryParam("idSoggettoIstruttore") Long idSoggettoIstruttore,
								  @QueryParam("progBandoLina") Long progBandoLina,
								  @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("getbandolineadaAssociare") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getBandoLineaDaAssociareAdIstruttore( @QueryParam("idU") Long idU,
												   @QueryParam("idSoggetto") Long idSoggetto,
												   @QueryParam("idSoggettoIstruttore") Long idSoggettoIstruttore,
												   @QueryParam("descBreveTipoAnagrafica") String descBreveTipoAnagrafica, // dipende dal ruolo dell'utente 2 ADG-IST-MASTER / 4 OI-IST-MASTER
												   @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("associaistruttorebandolinea") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaIstruttoreBandolinea( @QueryParam("idU") Long idU,
										  @QueryParam("progBandoLinaIntervento") Long progBandoLinaIntervento,
										  @QueryParam("idSoggettoIstruttore") Long idSoggettoIstruttore,
										  @QueryParam("ruolo") String ruolo,
										  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("eliminaassociazioneistruttorebandolinea") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaAssocizioneIstruttoreBandoLinea( @QueryParam("idU") Long idU,
													 @QueryParam("idSoggettoIstruttore") Long idSoggettoIstruttore,
													 @QueryParam("progBandoLineaIntervento") Long progBandoLineaIntervento,
													 @Context HttpServletRequest req) throws Exception;
	
}
