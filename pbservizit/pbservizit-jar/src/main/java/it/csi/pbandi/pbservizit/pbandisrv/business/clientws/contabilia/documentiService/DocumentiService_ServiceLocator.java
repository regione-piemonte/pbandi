/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * DocumentiService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService;

public class DocumentiService_ServiceLocator extends org.apache.axis.client.Service implements it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_Service {

    public DocumentiService_ServiceLocator() {
    }


    public DocumentiService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DocumentiService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DocumentiServicePort
    private java.lang.String DocumentiServicePort_address = "http://tst-<VH_API_MANAGER>/api/BILANCIO_contabilia_documentiService/1.0";

    public java.lang.String getDocumentiServicePortAddress() {
        return DocumentiServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DocumentiServicePortWSDDServiceName = "DocumentiServicePort";

    public java.lang.String getDocumentiServicePortWSDDServiceName() {
        return DocumentiServicePortWSDDServiceName;
    }

    public void setDocumentiServicePortWSDDServiceName(java.lang.String name) {
        DocumentiServicePortWSDDServiceName = name;
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_PortType getDocumentiServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DocumentiServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDocumentiServicePort(endpoint);
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_PortType getDocumentiServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiServiceSoapBindingStub _stub = new it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDocumentiServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDocumentiServicePortEndpointAddress(java.lang.String address) {
        DocumentiServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiServiceSoapBindingStub _stub = new it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiServiceSoapBindingStub(new java.net.URL(DocumentiServicePort_address), this);
                _stub.setPortName(getDocumentiServicePortWSDDServiceName());
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
        if ("DocumentiServicePort".equals(inputPortName)) {
            return getDocumentiServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "DocumentiService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "DocumentiServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DocumentiServicePort".equals(portName)) {
            setDocumentiServicePortEndpointAddress(address);
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
