/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.AnagraficaBeneficiarioService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagAltriDati_MainVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AtlanteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DettaglioImpresaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElencoDomandeProgettiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.IscrizioneRegistroVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StatoDomandaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.UpdateAnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Service
public class AnagrafeBeneficiarioServiceImpl implements AnagraficaBeneficiarioService{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected AnagraficaBeneficiarioDAO beneficiarioDao;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////SERVIZI PER RICERCA E DETTAGLIO /////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Response getAnagBeneficiario(Long idSoggetto,Long idProgetto,  String numDomanda, HttpServletRequest req)
			throws DaoException {

		List<AnagraficaBeneficiarioVO> beneficiario = this.beneficiarioDao.getAnagBeneficiario(idSoggetto,idProgetto, numDomanda, req);

		return Response.ok().entity(beneficiario).build();
	}


	@Override
	public Response getIscrizioneRegistroImprese(Long idSoggetto, HttpServletRequest req) throws DaoException {

		IscrizioneRegistroVO iscrizione = this.beneficiarioDao.getIscrizioneRegistroImprese(idSoggetto, req);

		return Response.ok().entity(iscrizione).build();
	}


	@Override
	public Response getStatoDomanda(Long idSoggetto,Long idDomanda,  HttpServletRequest req) throws DaoException {
		StatoDomandaVO statoDomanda = this.beneficiarioDao.getStatoDomanda(idSoggetto, idDomanda,req);
		return Response.ok().entity(statoDomanda).build();
	}

	@Override
	public Response getAnagBeneFisico(Long idSoggetto, Long idProgetto, String numDomanda,  HttpServletRequest req) throws DaoException {
		AnagraficaBeneficiarioPfVO beneficiario = this.beneficiarioDao.getAnagBeneFisico(idSoggetto,numDomanda, idProgetto,req);

		return Response.ok().entity(beneficiario).build();
	}


	@Override
	public Response getNazioni(HttpServletRequest req) throws DaoException {
		List<AtlanteVO> nazioni = this.beneficiarioDao.getNazioni(req);
		return Response.ok().entity(nazioni).build();
	}



	@Override
	public Response getRegioni(HttpServletRequest req) throws DaoException {
		List<AtlanteVO> regioni = this.beneficiarioDao.getRegioni(req);
		return Response.ok().entity(regioni).build();
	}



	@Override
	public Response getProvincie(HttpServletRequest req) throws DaoException {
		List<AtlanteVO> provincie = this.beneficiarioDao.getProvincie(req);
		return Response.ok().entity(provincie).build();
	}



	@Override
	public Response getComuni(String idProvincia,HttpServletRequest req) throws DaoException {
		List<AtlanteVO> comuni = this.beneficiarioDao.getComuni(idProvincia,req);
		return Response.ok().entity(comuni).build();
	}


	@Override
	public Response updateAnagBeneGiuridico(AnagraficaBeneficiarioVO anag,
			String idSoggetto,String idDomanda,
			HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {

		return Response.ok(beneficiarioDao.updateAnagBeneGiuridico(anag, idSoggetto, idDomanda, false)).build();
		
	}


	@Override
	public Response getElencoDomandeProgetti(Long idSoggetto, boolean isEnteGiuridico,HttpServletRequest req) throws DaoException {
		List<ElencoDomandeProgettiVO> elenco = this.beneficiarioDao.getElencoDomandeProgetti(idSoggetto,isEnteGiuridico,req);
		return Response.ok().entity(elenco).build();
	}


	@Override
	public Response getAltriDati(Long idSoggetto, Long idEnteGiuridico, String numeroDomanda, HttpServletRequest req)
			throws DaoException {
		AnagAltriDati_MainVO data = this.beneficiarioDao.getAltriDati(idSoggetto, idEnteGiuridico, numeroDomanda, req);
		return Response.ok().entity(data).build();
	}
		  
	
	@Override
	public Response getDettaglioImpresa(String idSoggetto, BigDecimal anno, HttpServletRequest req) throws DaoException {
		List<DettaglioImpresaVO> dett = this.beneficiarioDao.getDettaglioImpresa(idSoggetto, anno, req);
		return Response.ok().entity(dett).build();
	}

	/*@Override
	public Response getDimensioneImpresa(Long idSoggetto, Long numeroDomanda, HttpServletRequest req)
			throws DaoException {
		List<AnagAltriDati_DimensioneImpresaVO> data = this.beneficiarioDao.getDimensioneImpresa(idSoggetto,numeroDomanda, req);
		return Response.ok().entity(data).build();
	}*/


	/*@Override
	public Response getDurc(Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		List<AnagAltriDati_DurcVO> data = this.beneficiarioDao.getDurc(idSoggetto, req);
		return Response.ok().entity(data).build();
	}*/




	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////SERVIZI PER MODIFICA  ///////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//////////BLOCCHI///////////////

	@Override
	public Response getElencoBlocchi(BigDecimal idSoggetto, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoBlocchi(idSoggetto, false)).build();
	}

	@Override
	public Response getStoricoBlocchi(Long idSoggetto, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getStoricoBlocchi(idSoggetto, false)).build();
	}

	@Override
	public Response getListaCausali(Long idSoggetto, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getListaCausali(idSoggetto)).build();
	}

	@Override
	public Response insertBlocco(BloccoVO bloccoVO, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		return Response.ok(beneficiarioDao.insertBlocco(bloccoVO)).build();
	}


	@Override
	public Response modificaBlocco(BloccoVO bloccoVO, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		return Response.ok(beneficiarioDao.modificablocco(bloccoVO)).build();
	}



	
	////////////// SOGGETTI CORRELATI INDIPENDENTI DA DOMANDA/////////////

	@Override
	public Response getElencoSoggCorrIndipDaDomanda(String idDomanda, String idSoggetto,String progSoggDomanda, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoSoggCorrIndipDaDomanda(idDomanda,  idSoggetto)).build();
	}


	@Override
	public Response getSoggCorrIndipDaDomandaEG(String idDomanda, String idSoggetto, BigDecimal idSoggCorr,HttpServletRequest req)
			throws DaoException {
		return Response.ok(beneficiarioDao.getSoggCorrIndDaDomEG(idDomanda, idSoggetto, idSoggCorr)).build();
	}


	@Override
	public Response getSoggCorrIndipDaDomandaPF(String idDomanda, String idSoggetto, HttpServletRequest req)
			throws DaoException {
		return Response.ok(beneficiarioDao.getSoggCorrIndDaDomPF(idDomanda, idSoggetto)).build();
	}

	//////////////// MODIFICA SOGGETTO CORRELATO INDIPENDENTE DA DOMANDA/////////////

	@Override
	public Response modificaSoggCorrIndipDaDomandaEG(AnagraficaBeneficiarioVO soggetto, String idSoggetto, String idDomanda, BigDecimal idSoggCorr,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(beneficiarioDao.modificaSoggcorrEG(soggetto, idSoggetto, idDomanda, idSoggCorr)).build();  
	}


	@Override
	public Response getElencoformaGiuridica(HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.elencoFormeGiuridiche()).build();
	}


	@Override
	public Response modificaSoggCorrIndipDaDomandaPF(AnagraficaBeneficiarioPfVO anag, String idSoggetto,
			String idDomanda, HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(beneficiarioDao.modificaSoggcorrPF(anag, idSoggetto, idDomanda)).build();
	}


	@Override
	public Response getElencoAteco(String idAttivitaAteco, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoAteco(idAttivitaAteco)).build();
	}


	@Override
	public Response getElencoRuoloIndipendente(HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoRuoloIndipendente()).build();
	}


	@Override
	public Response getElencoSezioneSpeciale(HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoSezioni()).build();
	}

//// ALTRI DATI 
	@Override
	public Response getDatiAreaCreditiSoggetto(String idSoggetto, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getDatiAreaCrediti(idSoggetto)).build();
	}

	// blocchi soggetto domanda

	@Override
	public Response getElencoBlocchiSoggettoDomanda(String idSoggetto, String idDomanda, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoBlocchiSoggettoDomanda(idSoggetto, idDomanda, false)).build();
	}


	@Override
	public Response insertBloccoDomanda(BloccoVO bloccoVO, String idSoggetto, String idDomanda, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		return Response.ok(beneficiarioDao.insertBloccoDomanda(bloccoVO, idSoggetto, idDomanda)).build();
	}


	@Override
	public Response modificaBloccoDomanda(BloccoVO blocco,String numeroDomanda, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		return Response.ok(beneficiarioDao.updateBloccoDomanda(blocco, numeroDomanda)).build();
	}


	@Override
	public Response getListaCausaliDomanda(String idSoggetto, String idDomanda, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getListaCausaliDomanda(idSoggetto, idDomanda)).build();
	}


	@Override
	public Response getStoricoBlocchiDomanda(Long idSoggetto, String numeroDomanda, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getStoricoBlocchiDomanda(idSoggetto,numeroDomanda, false)).build();
	}


	@Override
	public Response getDatiImpresa(Long idSoggetto, String numeroDomanda, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getDatiDimensione(idSoggetto, numeroDomanda)).build();
	}
	@Override
	public Response getDatiImpresaSoggetto(Long idSoggetto, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getDatiDimensioneSoggetto(idSoggetto)).build();
	}


	@Override
	public Response getDsan(Long idSoggetto, String numeroDomanda, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getDsan(idSoggetto, numeroDomanda)).build();
	}


	@Override
	public Response getComuneEstero(Long idNazione, HttpServletRequest req) throws DaoException {
	
		return Response.ok(beneficiarioDao.getComuniEsteri(idNazione)).build();
	}


	@Override
	public Response getProvinciaConIdRegione(Long idRegione, HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getProvincieConidRegioni(idRegione)).build();
	}


	@Override
	public Response getElencoRuoloDipendente(HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoRuoliDipendente()).build();
	}


	@Override
	public Response getStatoAttivita(HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getStatoAttivita()).build();
	}


	@Override
	public Response getElencoTipoAnag(HttpServletRequest req) throws DaoException {
		return Response.ok(beneficiarioDao.getElencoTipoAnag()).build();
	}


	@Override
	public Response updateAnagraficaPF(AnagraficaBeneficiarioPfVO anag, String idSoggetto, String numeroDomanda,
			HttpServletRequest request) throws InvalidParameterException, Exception {
		return Response.ok(beneficiarioDao.updateAnagBeneFisico(anag, idSoggetto, numeroDomanda)).build();
	}


	@Override
	public Response getTipoDocumento() throws DaoException {
		// TODO Auto-generated method stub
		return Response.ok(beneficiarioDao.getListTipoDocumento()).build();
	}






}
