/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.util.oauth;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Dato un ResteasyWebTarget, e data la classe di interfaccia JAX-RS, questo InvocationHandler
 * fornisce l'implementazione di un proxy dinamico che, in aggiunta,
 * implementa il meccanismo di autenticazione oauth2, che prevede:
 * 1) il richiamo del servizio utilizzando un token oauth2 in cache (gestita dall'OauthHelper)
 * 2) se l'invocazione fallisce (http 401) si effettua il refresh del token, si aggiorna
 * la cache e si rieffettua la chiamata
 * Poiche' si utilizza un proxy dinamico questa classe implementa InvocationHandler.
 * Di fatto si tratta di un proxy di proxy. Il proxy più a valle e' un proxy costruito con le client api
 * di resteasy, e che implementa l'interfaccia JAX-RS dell'API effettiva. Il proxy su cui si basa questo
 * InvocationHandler implementa la stessa interfaccia ma wrappa il proxy effettivo con la logica di
 * gestione dell'autenticazione oauth e del retry.
 * <p>
 * Per ottenere un nuovo token quando necessario viene utilizzato un oggetto di tipo OAuthHelper, che deve
 * essere istanziato e configurato esternamente e passato al costruttore.
 * <p>
 * E' prevista una cache del token OAuth2, in modo da non dover ogni volta ottenere un nuovo token.
 *
 * @author CSI PIEMONTE
 */
public class ResteasyOauthWrapper implements InvocationHandler {

    private final Class jaxrsIntf;

    private final OauthHelper oauthHelper;

    private final ResteasyWebTarget webTarget;

    /**
     * Instantiates a new Resteasy oauth wrapper.
     *
     * @param target      il web-target base del servizio effettivo (da questo viene ottenuto il proxy                    jax-rs
     * @param jaxrsIntf   l'interfaccia jaxrs del servizio effettivo
     * @param oauthHelper helper per la gestione dei token
     */
    public ResteasyOauthWrapper(ResteasyWebTarget target, Class jaxrsIntf, OauthHelper oauthHelper) {
        this.jaxrsIntf = jaxrsIntf;
        this.oauthHelper = oauthHelper;
        this.webTarget = target;
        // registrazione di un interceptor che inserisce nelle chiamate l'header di autenticazione oauth2
        webTarget.register(new BearerAuthentication(oauthHelper));
    }


    /**
     * @param proxy  non utilizzato (si crea un proxy jaxrs ogni volta, a partire dal web-target)
     * @param method il metodo invocato
     * @param args   gli argomenti da passare al metodo
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // questo proxy implementa jaxrsintf
        Object jaxrsProxy = getNewJaxrsProxy();
        try {
            Response resp = (Response) method.invoke(jaxrsProxy, args);
            // in caso di errore di autenticazione (401) non viene restituita una eccezione
            // ma una response con codice 401
            if (isOauthError(resp)) {
                // refresh token & retry....
                refreshToken();
                jaxrsProxy = getNewJaxrsProxy();
                try {
                    return method.invoke(jaxrsProxy, args);
                } catch (InvocationTargetException ite2) {
                    throw ite2.getTargetException();
                }
            } else {
                return resp;
            }
        } catch (InvocationTargetException ite) {
            // se l'eccezione e' legata ad un problema di autenticazione effettua il giro di retry
            // altrimenti restituisce l'eccezione interna.
            // controllo inserito per completezza ma normalmente un errore 401 non viene restituito
            // come exception
            /*
            if (isOauthError(ite.getTargetException()) && this.oauthHelper != null) {
                // refresh token & retry
                refreshToken();
                jaxrsProxy = getNewJaxrsProxy();
                try {
                    return method.invoke(jaxrsProxy, args);
                } catch (InvocationTargetException ite2) {
                    throw ite2.getTargetException();
                }
            }
            else {

             */
            throw ite.getTargetException();
            //}
        }
    }


    /**
     * tramite l'oauth helper ottiene un nuovo token (che viene mantenuto nella cache
     * di OauthHelper)
     */
    private void refreshToken() {
        oauthHelper.getNewToken();
    }

    /**
     * Gli errori di autenticazione OAUTH2 sono  restituiti come HTTP 401
     *
     * @param resp
     * @return
     */
    private boolean isOauthError(Response resp) {
        return resp.getStatus() == 401;
    }

    /**
     * Resteasy non restituisce un'eccezione in caso di errore di autenticazione ma solo
     * uno status 401. Al momento quindi questo test e' sempre falso
     *
     * @param targetException
     * @return
     */
    private boolean isOauthError(Throwable targetException) {
        return false;
    }

    private Object _cachedProxy = null;

    /**
     * crea un nuovo proxy per l''interfaccia jax-rs specificata, a partire dal
     * web target configurato
     */
    @SuppressWarnings("unchecked")
    private Object getNewJaxrsProxy() {
        if (_cachedProxy == null) {
            Object newProxy = this.webTarget.proxy(jaxrsIntf);
            _cachedProxy = newProxy;
        }
        //
        return _cachedProxy;
    }

}