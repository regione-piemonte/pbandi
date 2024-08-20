/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import java.security.InvalidParameterException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbgestfinbo.dto.ProgettiEscludiEstrattiDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.NuovoCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoCampioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroAcqProgettiVO;

@Path("/nuovoCampionamento")
public interface NuovoCampionamentoService {

	@POST
	@Path("/elaborazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response elaboraNuovoCamp(NuovoCampionamentoVO nuovoCampVO) throws InvalidParameterException, Exception;

	@POST
	@Path("/estrazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response estrazioneProgetti(ProgettiEscludiEstrattiDTO progetti,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente)
			throws InvalidParameterException, Exception;
	
	@POST
	@Path("/campiona")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response campionaProgetti(NuovoCampionamentoVO nuovoCampVO,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente)
					throws InvalidParameterException, Exception;
	
	@POST
	@Path("/importoTotale")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response importoTotale(NuovoCampionamentoVO nuovoCampVO)
					throws InvalidParameterException, Exception;
	
	@POST
	@Path("/creaControlloLoco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response creaControlloLoco(ProgettiEscludiEstrattiDTO progetti,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente)
			throws InvalidParameterException, Exception;

	@GET
	@Path("/annullaCampionamento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response annullaCampionamento(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idCampionamento") Long idCampionamento
			)
					throws InvalidParameterException, Exception;
	
	
	//////// IMPORT PROGETTI CAMPIONATI DA FINPIEMAONTE //////////////
	
	@GET
	@Path("/getNormative")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getNormative(@DefaultValue("") @QueryParam("normativa") String suggest)
					throws InvalidParameterException, Exception;
	
	
	@POST
	@Path("/acquisisciProgetti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response acquisisciProgetti(FiltroAcqProgettiVO filtro)
			throws InvalidParameterException, Exception;
	@POST
	@Path("/confermaProgettiAcquisiti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response confermaProgettiAcquisiti(FiltroAcqProgettiVO filtro,
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente)
			throws InvalidParameterException, Exception;
	

}
