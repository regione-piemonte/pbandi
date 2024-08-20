/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.util.oauth;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import it.csi.pbandi.pbwebfin.util.Constants;

import java.lang.reflect.Proxy;


/**
 * Factory Bean (in terminologia spring) dei wrapper
 * <p>
 * Il metodo <code>create</code> ritorna i wrapper
 * <p>
 * Per la configurazione occorre:
 * <p>
 * creare un GenericWrapperFactoryBean mediante il costruttore di default
 * impostare i seguenti parametri mediante setter:
 * <p>
 * <p>
 * endpoint
 * classe dell'interfaccia da wrappare
 * oggetto port da wrappare
 * un TokenRetryManager
 * <p>
 * chiamare il metodo create() per ottenere un oggetto wrappato
 *
 * <p>
 * Esempio di uso:
 * <pre>
 *      GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
 *      gwfb.setEndPoint("http://<VH_TEST_Oauth>/api/ITT_QSP_echo/1.0");
 *      gwfb.setWrappedInterface(EipMockServiceAxisPortType.class);
 *      gwfb.setPort(port);
 *      gwfb.setTokenRetryManager(trm);
 *      EipMockServiceAxisPortType p = (EipMockServiceAxisPortType) gwfb.create();
 * </pre>
 *
 * @author CSI PIEMONTE
 */
public class ResteasyOauthWrapperFactory /*extends BaseFactoryBean*/ {


    /**
     * The constant logger.
     */
    protected static Logger logger = Logger.getLogger(Constants.COMPONENT_NAME);


    /**
     * The Wrapped interface.
     */
    protected Class wrappedInterface;
    /**
     * The End point.
     */
    protected String endPoint;


    /**
     * Gets end point.
     *
     * @return l 'endpoint del servizio
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * imposta l'endpoint del servizio
     *
     * @param endPoint endpoint del servizio (tipicamente un indirizzo sull'API Manager)
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Gets wrapped interface.
     *
     * @return ottiene l'interfaccia wrappata del servizio
     */
    public Class getWrappedInterface() {
        return wrappedInterface;
    }

    /**
     * imposta l'interfaccia del servizio
     *
     * @param wrappedInterface interfaccia del servizio
     */
    public void setWrappedInterface(Class wrappedInterface) {
        this.wrappedInterface = wrappedInterface;
    }

    /**
     * Create object.
     *
     * @return un oggetto che implementa l'interfaccia <code>wrappedClass</code> e wrappa l'oggetto <code>port</code>
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public Object create() throws ClassNotFoundException {
        logger.info("[GenericWrapperFactoryBean.create]" +
                "\nendpoint ......: " + endPoint +
                "\nwrappedClass ..: " + wrappedInterface);


        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(endPoint);

        ResteasyOauthWrapper wrapper = new ResteasyOauthWrapper(target, wrappedInterface, oauthHelper);
        Object o = Proxy.newProxyInstance(wrappedInterface.getClassLoader(), new Class[]{wrappedInterface}, wrapper);

        if (logger.isDebugEnabled())
            logger.debug("[GenericWrapperFactoryBean.create] created " + o.getClass().getName());
        return o;
    }


    private OauthHelper oauthHelper;

    /**
     * Gets oauth helper.
     *
     * @return il <code>tokenRetryManager</code> proprietario dell'oggetto <code>port</code>
     */
    public OauthHelper getOauthHelper() {
        return oauthHelper;
    }

    /**
     * Sets oauth helper.
     *
     * @param oauthHelper il proprietario dell'oggetto <code>port</code>
     */
    public void setOauthHelper(OauthHelper oauthHelper) {
        this.oauthHelper = oauthHelper;
    }

}