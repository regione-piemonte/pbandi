/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service.impl;

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

import it.csi.pbandi.pbservrest.business.service.CreditoApi;
import it.csi.pbandi.pbservrest.dto.StatoCredito;
import it.csi.pbandi.pbservrest.dto.StatoCreditoListResponse;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.CreditoDAO;
import it.csi.pbandi.pbservrest.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class CreditoApiImpl extends BaseApiServiceImpl implements CreditoApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	CreditoDAO creditoDao;
	
	@Override
	public Response getStatoCreditoProgettiBeneficiario(String numeroDomanda, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		String prf = "[CreditoServiceImpl::getStatoCreditoProgettiBeneficiario]";
		LOG.info(prf + " BEGIN");
		StatoCreditoListResponse scr = new StatoCreditoListResponse();
		
		try {
			List<StatoCredito> statoCredito = new ArrayList<StatoCredito>();	
			LOG.info(prf + " numeroDomanda="+numeroDomanda);
			if(numeroDomanda!=null) {
				statoCredito= creditoDao.getStatoCredito(numeroDomanda);
				LOG.info(prf + "sono stati trovate " + statoCredito.size() + " stati del credito");

				scr.setStatiCreditoList(statoCredito);
				
			}else {
				LOG.error("Numero domanda nullo");
				throw new ErroreGestitoException("Numero Domanda nullo");
			}
		} catch (RecordNotFoundException e) {
			LOG.error(prf + "RecordNotFoundException", e);
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read StatoCredito", e);
			throw new ErroreGestitoException("DaoException while trying to read StatoCredito", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok(scr).build();
	}
}
