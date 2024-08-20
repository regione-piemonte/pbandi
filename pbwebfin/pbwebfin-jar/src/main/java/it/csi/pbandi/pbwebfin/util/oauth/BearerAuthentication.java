/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.util.oauth;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;


/**
 * Client filter che inserisce l'header necessario per la autenticazione OAuth2.
 * I token sono forniti da una istanza di OauthHelper che deve essere passata come parametro
 * al costruttore, e che mantiene anche una cache dell'ultimo token valido, per evitare invocazioni
 * inutili al servizio /token".
 * E' necessario allocare una istanza di questo filter e registrarlo nel target o nel client.
 *
 * @author CSI PIEMONTE
 */
public class BearerAuthentication implements ClientRequestFilter {
    private final OauthHelper oauthHelper;

    /**
     * Instantiates a new Bearer authentication.
     *
     * @param oauthHelper l'istanza di OauthHelper utilizzata per ottenere i token oauth2
     */
    public BearerAuthentication(final OauthHelper oauthHelper) {
        this.oauthHelper = oauthHelper;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        StringBuilder buf = new StringBuilder(oauthHelper.getToken());
        String authHeader = "Bearer " + buf.toString();

        requestContext.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, authHeader);
    }
}