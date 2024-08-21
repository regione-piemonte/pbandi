/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservrest.business.service.DomandeBeneficiarioApi;
import it.csi.pbandi.pbservrest.dto.DomandaInfo;
import it.csi.pbandi.pbservrest.dto.DomandaInfoListResponse;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.DomandaBeneficiarioDAO;
import it.csi.pbandi.pbservrest.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class DomandeBeneficiarioApiImpl extends BaseApiServiceImpl implements DomandeBeneficiarioApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	DomandaBeneficiarioDAO domandaBeneficiarioDAO;
	
	@Override
	public Response getDatiBeneficiarioDoquiActa(String tipoSogg, BigDecimal codiceBando, String numeroDomanda,
			String ndg, BigDecimal numRelazDichSpesa, String pec, String codiceFiscaleSoggetto,
			String denominazioneEnteGiuridico, String partitaIva, String acronimoProgetto, String nome, String cognome,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		String prf = "[DomandaBeneficiarioServiceImpl::getDatiBeneficiarioDoquiActa]";
		LOG.info(prf + " BEGIN");
		
		List<DomandaInfo> domandaInfo = new ArrayList<>();
		DomandaInfoListResponse domandaRsp = new DomandaInfoListResponse();
		String numeroDom = null;
		
		try {
			domandaRsp.setEsito(Constants.ESITO.OK);
			
			// tipoSogg REQUIRED
			// questo lo gestisce rest
			if(tipoSogg == null || "".equals(tipoSogg)){
				LOG.warn(prf + " tipoSogg NULL.");
				domandaRsp.setEsito(Constants.ESITO.KO);
				domandaRsp.setMessaggioErrore((Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI));
				return Response.ok(domandaRsp).build();
			}
			
			if(codiceBando==null && isNull(numeroDomanda) && isNull(ndg) && numRelazDichSpesa==null && isNull(pec) && isNull(codiceFiscaleSoggetto)
					&& isNull(denominazioneEnteGiuridico) && isNull(partitaIva) && isNull(acronimoProgetto) && isNull(nome) && isNull(cognome)) {
				// deve essere valorizzato almeno uno dei restanti parametri
				LOG.warn(prf + " parametri di INPUT insufficienti.");
				domandaRsp.setEsito(Constants.ESITO.KO);
				domandaRsp.setMessaggioErrore((Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI));
				return Response.ok(domandaRsp).build();
				
			}
				
			// verifico che esista il tipoSogg indicato sul DB
			Integer idTipoSoggetto = domandaBeneficiarioDAO.getIdTipoSoggetto(tipoSogg);
			if(idTipoSoggetto==null) {
				LOG.warn(prf + " idTipoSoggetto non riconosciuto");
				domandaRsp.setEsito(Constants.ESITO.KO);
				domandaRsp.setMessaggioErrore((Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI));
				return Response.ok(domandaRsp).build();
			}
				
			// TODO PK : perche cerca numeroDom se ce l'ha gia???
			// numerodom va cercarcato col LIKE ???
			if (numeroDomanda!=null && numeroDomanda!="") {
				numeroDom= domandaBeneficiarioDAO.getNumeroDom(numeroDomanda);
			}
			LOG.info(prf + " numeroDom="+numeroDom);


			if(("eg").equalsIgnoreCase(tipoSogg)) {
				domandaInfo = domandaBeneficiarioDAO.getDomandeBeneficiarioProgetto(numeroDom, numRelazDichSpesa, codiceBando, ndg,  pec, codiceFiscaleSoggetto, denominazioneEnteGiuridico, partitaIva, acronimoProgetto, tipoSogg);
				if(domandaInfo.size()==0) {
					domandaInfo = domandaBeneficiarioDAO.getDomandeBeneficiarioDomanda(numeroDom, numRelazDichSpesa, codiceBando, ndg,  pec, codiceFiscaleSoggetto, denominazioneEnteGiuridico, partitaIva, acronimoProgetto, tipoSogg);
				}
			} else if(("pf").equalsIgnoreCase(tipoSogg)) {
				domandaInfo = domandaBeneficiarioDAO.getDomandeBeneficiarioFisicoProgetto(numeroDom, numRelazDichSpesa, codiceBando, ndg, pec, codiceFiscaleSoggetto, nome, cognome, tipoSogg);
				if(domandaInfo.size()==0) {
					domandaInfo = domandaBeneficiarioDAO.getDomandeBeneficiarioFisicoDomanda(numeroDom, numRelazDichSpesa, codiceBando, ndg, pec, codiceFiscaleSoggetto, nome, cognome, tipoSogg);
				}
			}

			LOG.info(prf + "sono state trovate " + (domandaInfo==null ? "0" : domandaInfo.size()) + " domandaInfo");
			domandaRsp.setDomandaInfoList(domandaInfo);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DomandaInfoVO", e);
//			throw new ErroreGestitoException("DaoException while trying to read DomandaInfoVO", e);
			domandaRsp.setEsito(Constants.ESITO.KO);
			domandaRsp.setMessaggioErrore((Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI));
			return Response.ok(domandaRsp).build();
		} finally {
			LOG.info(prf + " END");
		}
		return Response.ok(domandaRsp).build();
	}

	
	/**
	 *  Restituisce TRUE se il parametro param e' null o stringa vuota
	 *  
	 * @param param
	 * @return
	 */
	private boolean isNull(String param) {
		boolean ret = false;
		if(param == null || "".equals(param)){
			ret = true;
		}
		return ret;
	}
}
