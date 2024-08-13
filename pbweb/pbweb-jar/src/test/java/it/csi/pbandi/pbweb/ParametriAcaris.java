/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb;

public class ParametriAcaris {
    private Ambiente ambiente;
    private String repositoryName;
    private long idAoo;
    private long idStruttura;
    private long idNodo;
    private String codiceFiscale;
    private String appKey;

    public ParametriAcaris() {
    }
    
    public ParametriAcaris(String server, String context, int port) {
        this.ambiente = new Ambiente(server, context, port);
    }
    
    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public long getIdAoo() {
        return idAoo;
    }

    public void setIdAoo(long idAoo) {
        this.idAoo = idAoo;
    }

    public long getIdStruttura() {
        return idStruttura;
    }

    public void setIdStruttura(long idStruttura) {
        this.idStruttura = idStruttura;
    }

    public long getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(long idNodo) {
        this.idNodo = idNodo;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getServer() {
        return getAmbiente().server;
    }
    
    public String getContext() {
        return getAmbiente().context;
    }
    
    public int getPort() {
        return getAmbiente().port;
    }
    
    public Ambiente getAmbiente() {
        return ambiente;
    }

    class Ambiente {

        private String server;
        private String context;
        private int port;

        public Ambiente() {}
        
        public Ambiente(String server, String context, int port) {
            this.setServer(server);
            this.setContext(context);
            this.setPort(port);
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
