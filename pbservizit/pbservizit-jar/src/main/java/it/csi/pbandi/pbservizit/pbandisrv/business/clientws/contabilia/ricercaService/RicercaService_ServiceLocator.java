/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaService_ServiceLocator extends org.apache.axis.client.Service implements it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_Service {

    public RicercaService_ServiceLocator() {
    }


    public RicercaService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RicercaService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RicercaServicePort
    private java.lang.String RicercaServicePort_address = "http://tst-<VH_API_MANAGER>/api/BILANCIO_contabilia_ricercaService/1.0";

    public java.lang.String getRicercaServicePortAddress() {
        return RicercaServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RicercaServicePortWSDDServiceName = "RicercaServicePort";

    public java.lang.String getRicercaServicePortWSDDServiceName() {
        return RicercaServicePortWSDDServiceName;
    }

    public void setRicercaServicePortWSDDServiceName(java.lang.String name) {
        RicercaServicePortWSDDServiceName = name;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_PortType getRicercaServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RicercaServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRicercaServicePort(endpoint);
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_PortType getRicercaServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaServiceSoapBindingStub _stub = new it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRicercaServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRicercaServicePortEndpointAddress(java.lang.String address) {
        RicercaServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaServiceSoapBindingStub _stub = new it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaServiceSoapBindingStub(new java.net.URL(RicercaServicePort_address), this);
                _stub.setPortName(getRicercaServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RicercaServicePort".equals(inputPortName)) {
            return getRicercaServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "RicercaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "RicercaServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RicercaServicePort".equals(portName)) {
            setRicercaServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
