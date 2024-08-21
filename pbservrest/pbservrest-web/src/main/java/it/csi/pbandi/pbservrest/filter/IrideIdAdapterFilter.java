/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;
import it.csi.pbandi.pbservrest.util.Constants;

/**
 * Inserisce in sessione:
 * <ul>
 * <li>l'identit&agrave; digitale relativa all'utente autenticato.
 * <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticaizone previsto e la
 * logica applicativa.
 *
 * @author CSIPiemonte
 */
public class IrideIdAdapterFilter implements Filter {

	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
	public final static Long ID_UTENTE_FITTIZIO = -1L;

	protected static final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME + ".security");


	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {

		String prf = "[IrideIdAdapterFilter::doFilter] ";
		
		LOG.info(prf + " BEGIN");
		
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpSession session = hreq.getSession();
		
		/*
		 * Memorizzazione dell' identita iride in fase di accesso alle risorse
		 */
		Identita identitaInSessione = (Identita) session.getAttribute(IRIDE_ID_SESSIONATTR);
		LOG.info(prf + " identitaInSessione=" + identitaInSessione);

		if (identitaInSessione == null) {
			String marker = getToken(hreq);

			if (devmode) {
				marker = "AAAAAA00A11O000W/CSI PIEMONTE/DEMO 34/ACTALIS_EU/20210209102032/16/E3h5Biin6U6Nee0r5RnmJg==";
			}

			LOG.info(prf + " marker = " + marker);
			if (marker != null) {
				try {
					Identita identitaDaShibboleth = new Identita(normalizeToken(marker));
					LOG.debug(prf + " Inserito in sessione marcatore IRIDE:"
							+ identitaDaShibboleth);

//					UserInfoSec userInfo = new UserInfoSec();
//					userInfo.setCodFisc(identitaDaShibboleth.getCodFiscale());
//					userInfo.setCognome(identitaDaShibboleth.getCognome());
//					userInfo.setNome(identitaDaShibboleth.getNome());
//					userInfo.setIdIride(identitaDaShibboleth.getRappresentazioneInterna());
					
					if (devmode) {
//						userInfo.setIdUtente(21957L);
//						userInfo.setIdSoggetto(2130090L);
						//userInfo.setCodiceRuolo("ADG-ISTRUTTORE");
					}

					//
					// qui valorizzo l'oggetto userInfo con le sole informazioni derivanti
					// dall'identita digitale
					//

					hreq.getSession().setAttribute(IRIDE_ID_SESSIONATTR, identitaDaShibboleth);
					//hreq.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);
					hreq.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, null);

					String idIrideShib = identitaDaShibboleth == null ? null : identitaDaShibboleth.toString();
					LOG.info(prf + " idIrideShib: " + idIrideShib);

					if (identitaDaShibboleth != null) {
						try {
							String action = hreq.getRequestURI();

							LOG.info(prf + " memorizzo identita su db");
//							profilazioneSrv.memorizzaIride(ID_UTENTE_FITTIZIO, idIrideShib, session.getId(), action);

						} catch (Exception e) {
//							LOG.error(prf + " Exception " + e, e);
//							e.printStackTrace();
						}
					}

				} catch (MalformedIdTokenException e) {
					LOG.error(prf + " MalformedIdTokenException " + e.toString(), e);
				}

			} else {
				// il marcatore deve sempre essere presente altrimenti e' una
				// condizione di errore (escluse le pagine home e di servizio)
				if (mustCheckPage(hreq.getRequestURI())) {
					LOG.error(
							prf + " Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
					LOG.error(prf + " END");
					throw new ServletException(
							"Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
				}
			}
		}
		LOG.info(prf + " END");
		fchn.doFilter(req, resp);
	}

	private boolean mustCheckPage(String requestURI) {
		return requestURI != null;
	}

	public void destroy() {
		// NOP
	}

	private static final String DEVMODE_INIT_PARAM = "devmode";

	private boolean devmode = true;

	public void init(FilterConfig fc) throws ServletException {
		String sDevmode = fc.getInitParameter(DEVMODE_INIT_PARAM);
		LOG.info("[IrideIdAdapterFilter::init] sDevmode=" + sDevmode);
		devmode = "true".equals(sDevmode);

		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, fc.getServletContext());
	}

	public String getToken(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getHeader(AUTH_ID_MARKER);
		if (marker == null && devmode) {
			return getTokenDevMode(httpreq);
		} else {
			return marker;
		}
	}

	private String getTokenDevMode(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getParameter(AUTH_ID_MARKER);
		return marker;
	}

	private String normalizeToken(String token) {
		return token;
	}

}
