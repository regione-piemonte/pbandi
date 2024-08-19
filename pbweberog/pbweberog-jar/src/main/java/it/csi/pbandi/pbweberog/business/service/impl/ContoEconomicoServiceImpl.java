/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.ContoEconomicoService;
import it.csi.pbandi.pbweberog.dto.ContoEconomicoItem;
import it.csi.pbandi.pbweberog.dto.Progetto;
import it.csi.pbandi.pbweberog.dto.contoeconomico.ContoEconomicoDTO;
import it.csi.pbandi.pbweberog.integration.dao.ContoEconomicoDAO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;

@Service
public class ContoEconomicoServiceImpl implements ContoEconomicoService{
	
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	ContoEconomicoDAO contoEconomicoDAO;
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;
	
	private static final String[] TYPES = new String[] { "ContoEconomico",
			"MacroVoceDiSpesa", "MicroVoceDiSpesa" };
	
	@Override
	public Response getDatiContoEconomico(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[ContoEconomicoServiceImpl::getDatiContoEconomico]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			ContoEconomicoDTO c =contoEconomicoDAO.findDatiContoEconomico(idUtente, idIride, idProgetto);
			ArrayList<ContoEconomicoItem> result = new ArrayList<ContoEconomicoItem>();

			creaItem(result, c, 0, 0, 1);
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch(Exception e) {
			throw e;
		}
	}

	private int creaItem(ArrayList<ContoEconomicoItem> result, ContoEconomicoDTO c, int l, int uniqueIdPadre, int uniqueIdCorrente) {
		String prf = "[ContoEconomicoServiceImpl::creaItem]";
		LOG.debug(prf + " START");
		int nextUniqueId = uniqueIdCorrente + 1;
		
		ContoEconomicoItem item = new ContoEconomicoItem();
		item.setId(Integer.toString(uniqueIdCorrente));
		item.setIdPadre(Integer.toString(uniqueIdPadre));
		item.setLevel(l);
		item.setType(TYPES[l >= TYPES.length ? TYPES.length - 1 : l]);
		String label = c.getLabel();
		if (label == null && l == 0) {
			// TODO aggiungere un gettext
			label = "Conto Economico";
		}
		item.setLabel(label);
		item.setImportoSpesaAmmessa(c.getImportoSpesaAmmessa());
		item.setImportoSpesaValidataTotale(c.getImportoSpesaValidataTotale());
		item.setImportoSpesaQuietanziata(c.getImportoSpesaQuietanziata());
		item.setImportoSpesaRendicontata(c.getImportoSpesaRendicontata());
		item.setPercentualeSpesaQuietanziataSuAmmessa(c
				.getPercSpesaQuietanziataSuAmmessa());
		item.setPercentualeSpesaValidataSuAmmessa(c
				.getPercSpesaValidataSuAmmessa());
		result.add(item);

		if (c.getFigli() != null && c.getFigli().length > 0) {
			item.setHaFigli(true);
			for (ContoEconomicoDTO figlio : c.getFigli()) {
				nextUniqueId = creaItem(result, figlio, l + 1, uniqueIdCorrente, nextUniqueId);
			}
		} else {
			item.setHaFigli(false);
		}
		LOG.debug(prf + " END");
		return nextUniqueId;
	}
	
}
	
