package it.csi.pbandi.pbweb.business.service.impl;

import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbweb.business.service.DecodificheService;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.integration.dao.DecodificheDAO;
import it.csi.pbandi.pbweb.integration.dao.DocumentoDiSpesaDAO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

@Service
public class DecodificheServiceImpl implements DecodificheService{
	
	@Autowired
	private DecodificheDAO decodificheDAO;
	
	@Autowired
	private DocumentoDiSpesaDAO documentoDiSpesaDAO;
	
	@Override
	public Response ottieniTipologieDocumentiDiSpesa(@DefaultValue("0") @QueryParam("idBandoLinea") int idBandoLinea) {
	
		return Response.ok().entity(documentoDiSpesaDAO.ottieniTipologieDocumentiDiSpesaByBandoLinea(idBandoLinea)).build();
	}
	
	@Override
	public Response ottieniTipologieFornitore() {
	
		return Response.ok().entity(decodificheDAO.ottieniTipologieFornitore()).build();
	}
	
	@Override
	public Response ottieniTipologieFornitorePerIdTipoDocSpesa(@DefaultValue("0") @QueryParam("idTipoDocumentoDiSpesa") int idTipoDocumentoDiSpesa) {
	
		return Response.ok().entity(decodificheDAO.ottieniTipologieFornitorePerIdTipoDocSpesa(idTipoDocumentoDiSpesa)).build();
	}
	
	@Override
	public Response fornitori(
			@DefaultValue("0") @QueryParam("idSoggettoFornitore") long idSoggettoFornitore,
			@DefaultValue("0") @QueryParam("idTipoFornitore") long idTipoFornitore,
			@DefaultValue("")  @QueryParam("fornitoriValidi") String fornitoriValidi) {
	
		return Response.ok().entity(decodificheDAO.fornitori(idSoggettoFornitore,idTipoFornitore,fornitoriValidi)).build();
	}
	
	@Override
	public Response fornitoriCombo(
			@DefaultValue("0") @QueryParam("idSoggettoFornitore") long idSoggettoFornitore,
			@DefaultValue("0") @QueryParam("idTipoFornitore") long idTipoFornitore,
			@DefaultValue("0") @QueryParam("idFornitore") long idFornitore) {
	
		return Response.ok().entity(decodificheDAO.fornitoriCombo(idSoggettoFornitore,idTipoFornitore,idFornitore)).build();
	}
	
	@Override
	public Response fornitoriComboRicerca(long idProgetto, HttpServletRequest req) throws Exception {
		return Response.ok().entity(decodificheDAO.fornitoriComboRicerca(idProgetto, req)).build();
	}
	
	@Override
	public Response tipologieFormaGiuridica(
			@DefaultValue("") @QueryParam("flagPrivato") String flagPrivato) throws Exception {
				
		return Response.ok().entity(decodificheDAO.tipologieFormaGiuridica(flagPrivato)).build();
	}
	
	@Override
	public Response nazioni() throws Exception {
				
		return Response.ok().entity(decodificheDAO.nazioni()).build();
	}
	
	@Override
	public Response qualifiche(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
				
		return Response.ok().entity(decodificheDAO.qualifiche(idUtente)).build();
	}

	
	// usa vecchie classi
	@Override
	public Response attivitaCombo(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			HttpServletRequest request) throws UtenteException, GestioneDatiDiDominioException, UnrecoverableException {
		List<DecodificaDTO> l = decodificheDAO.attivitaCombo(idUtente, request);
		return Response.ok().entity(l).build();
	}
	
	@Override
	public Response elencoTask(long idProgetto, long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception {	
		return Response.ok().entity(decodificheDAO.elencoTask(idProgetto, idUtente, req)).build();
	}
	
	@Override
	public Response statiDocumentoDiSpesa() throws Exception {				
		return Response.ok().entity(decodificheDAO.statiDocumentoDiSpesa()).build();
	}
	
	@Override
	public Response tipiDocumentoSpesa() throws Exception {			
		return Response.ok().entity(decodificheDAO.tipiDocumentoSpesa()).build();
	}
	
	@Override
	public Response tipiDocumentoIndexUploadable() throws Exception {			
		return Response.ok().entity(decodificheDAO.tipiDocumentoIndexUploadable()).build();
	}
}
