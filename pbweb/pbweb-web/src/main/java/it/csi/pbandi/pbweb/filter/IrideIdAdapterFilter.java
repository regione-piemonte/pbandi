/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.filter;

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
import it.csi.pbandi.pbservizit.security.UserInfoSec;

import it.csi.pbandi.pbweb.util.Constants;
//import it.csi.iride2.policy.entity.Identita;
//import it.csi.iride2.policy.exceptions.MalformedIdTokenException;
//import it.csi.pbandi.pbworkspace.integration.dao.ProfilazioneDAO;
//import it.csi.pbandi.pbworkspace.security.UserInfoSec;
//import it.csi.pbandi.pbworkspace.util.Constants;

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

//	@Autowired
//	ProfilazioneDAO profilazioneDao;

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {

		LOG.info("[IrideIdAdapterFilter::doFIlter] BEGIN");
		/**
		 * public void doFilter(ServletRequest servletRequest, ServletResponse
		 * servletResponse, FilterChain chain)
		 */
		HttpServletRequest hreq = (HttpServletRequest) req;
//		HttpServletResponse hresp = (HttpServletResponse) resp;
		HttpSession session = hreq.getSession();

		/*
		 * TODO : serve verificare il cookie ??
		 * 
		 * try { boolean verificaCookie =
		 * datiDominioSrv.getConfiguration(ID_UTENTE_FITTIZIO, "").getVerificaCookie();
		 * logger.info("getVerificaCookie() = "+verificaCookie);
		 * 
		 * if (verificaCookie) { if (!isErroreAutenticazioneAction(request) &&
		 * !verificaCookie(request, response)) { String urlWorkspace =
		 * redirectManager.findRedirectAppUrl("workspacesrv.url", null);
		 * response.sendRedirect(urlWorkspace + "/erroreAutenticazione.do"); return; } }
		 * } catch (Exception e) {
		 * logger.error("PBandiFilter. Errore durante l'accesso ai dati di dominio.",
		 * e); }
		 * 
		 */
		/*
		 * Memorizzazione dell' identita iride in fase di accesso a workspace
		 */
		Identita identitaInSessione = (Identita) session.getAttribute(IRIDE_ID_SESSIONATTR);
		LOG.info("[IrideIdAdapterFilter::doFIlter] identitaInSessione=" + identitaInSessione);

		if (identitaInSessione == null) {
			String marker = getToken(hreq);

			// TODO : PK : se non trovo nulla in sessione do errore..... ???? uso COOKIE ??

			if (devmode) {
				
		
			}

			LOG.info("[IrideIdAdapterFilter::doFIlter] marker = " + marker);
			if (marker != null) {
				try {
					Identita identitaDaShibboleth = new Identita(normalizeToken(marker));
					LOG.debug("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:"
							+ identitaDaShibboleth);

					UserInfoSec userInfo = new UserInfoSec();
					userInfo.setCodFisc(identitaDaShibboleth.getCodFiscale());
					userInfo.setCognome(identitaDaShibboleth.getCognome());
					userInfo.setNome(identitaDaShibboleth.getNome());
					userInfo.setIdIride(identitaDaShibboleth.getRappresentazioneInterna());
					
					if (devmode) {
						// Alex 6/4/2021: forzo anche l'idUtente e l'idSoggetto, altrimenti resta valorizzato solo l'idIride.
						
						// AAAAAA00A11O000W
						userInfo.setIdUtente(21957L);
						userInfo.setIdSoggetto(2130090L);
						
						// AAAAAA00A11B000J
						//userInfo.setIdUtente(21421L);
						//userInfo.setIdSoggetto(2128953L);
					}

					//
					// PK : qui valorizzo l'oggetto userInfo con le sole informazioni derivanti
					// dall'identita digitale
					// : Tutte le altre informazioni dell'oggetto li valorizzo alla prima chiamata
					// -> getInfoUtente ??
					//

					hreq.getSession().setAttribute(IRIDE_ID_SESSIONATTR, identitaDaShibboleth);
					hreq.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);
//
//					String idIrideShib = identitaDaShibboleth == null ? null : identitaDaShibboleth.toString();
//					LOG.info("[IrideIdAdapterFilter::doFilter] idIrideShib: "+idIrideShib);
//					
//					if ( identitaDaShibboleth != null) {
//						try {
//							String action = hreq.getRequestURI();
//													
//							LOG.info("[IrideIdAdapterFilter::doFilter] memorizzo identita su db");
//							profilazioneDao.memorizzaIride(ID_UTENTE_FITTIZIO, idIrideShib, session.getId(), action);
//	
//						} catch (Exception e) {
//							LOG.error("[IrideIdAdapterFilter::doFilter] Exception "+e, e);
////							e.printStackTrace();
//						}
//					}

				} catch (MalformedIdTokenException e) {
					LOG.error("[IrideIdAdapterFilter::doFilter] MalformedIdTokenException " + e.toString(), e);
				}

			} else {
				// il marcatore deve sempre essere presente altrimenti e' una
				// condizione di errore (escluse le pagine home e di servizio)
				if (mustCheckPage(hreq.getRequestURI())) {
					LOG.error(
							"[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
					LOG.error("[IrideIdAdapterFilter::doFilter] END");
					throw new ServletException(
							"Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
				}
			}

			// TODO Verificare se e come gestire MiniApps
			// TODO Verificare se e come accesso come "incaricato"

		}
		LOG.info("[IrideIdAdapterFilter::doFilter] END");

		fchn.doFilter(req, resp);

	}

	private boolean mustCheckPage(String requestURI) {
		LOG.info("[IrideIdAdapterFilter::mustCheckPage] requestURI=" + requestURI);
		if (requestURI.indexOf("HomePage.do") > -1 || requestURI.indexOf("sessionExpired.do") > -1
				|| requestURI.indexOf("fatalError") > -1 || requestURI.indexOf("notFoundError") > -1
				|| requestURI.indexOf("sessionExpired.jsp") > -1 || requestURI.indexOf("Logout") > -1)
			return false;
		else
			return true;
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
