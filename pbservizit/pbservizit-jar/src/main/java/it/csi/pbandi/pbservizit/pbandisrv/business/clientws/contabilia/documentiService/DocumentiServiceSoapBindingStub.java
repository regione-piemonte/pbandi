/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * DocumentiServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService;

import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;

import javax.xml.soap.SOAPException;

public class DocumentiServiceSoapBindingStub extends org.apache.axis.client.Stub implements it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elaboraDocumento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumento"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumentoResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumentoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("elaboraDocumentoAsync");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumentoAsync"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumento"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumentoAsyncResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumentoAsyncResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("leggiStatoElaborazioneDocumento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "leggiStatoElaborazioneDocumento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "leggiStatoElaborazioneDocumento"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumento.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "leggiStatoElaborazioneDocumentoResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "leggiStatoElaborazioneDocumentoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public DocumentiServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public DocumentiServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public DocumentiServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumentoAsyncResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "elaboraDocumentoResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "leggiStatoElaborazioneDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "leggiStatoElaborazioneDocumentoResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "statoElaborazione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.StatoElaborazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseAsyncResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.BaseAsyncResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseRequest");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.BaseRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.BaseResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ente");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Ente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "entitaBase");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.EntitaBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "entitaCodificataBase");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.EntitaCodificataBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "errore");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "esito");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Esito.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "messaggio");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Messaggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "messaggioBase");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.MessaggioBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "stato");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Stato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse elaboraDocumento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento elaboraDocumento) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "elaboraDocumento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {elaboraDocumento});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse elaboraDocumentoAsync(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento elaboraDocumentoAsync) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "elaboraDocumentoAsync"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {elaboraDocumentoAsync});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse leggiStatoElaborazioneDocumento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumento leggiStatoElaborazioneDocumento) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "leggiStatoElaborazioneDocumento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        
	 
	 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {leggiStatoElaborazioneDocumento});
	 
        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
		throw axisFaultException;
  }
} 
    
    private void stampaReqRes(org.apache.axis.client.Call _call, String nomeOperazione) {
    	String str = "stampaReqRes(): "+nomeOperazione+": ";
      	if (_call == null) {
      		System.out.println(str+"_call nullo");
      		return;
      	}
      	try {
			 //String requestLeggiStato = _call.getMessageContext().getRequestMessage().getSOAPPart().getEnvelope().getBody().toString();
			 //String responseLeggiStato = _call.getMessageContext().getResponseMessage().getSOAPPart().getEnvelope().getBody().toString();
			 String req = _call.getMessageContext().getRequestMessage().getSOAPPart().getEnvelope().toString();
			 System.out.println(str+"REQUEST\n"+req);
			 String res = _call.getMessageContext().getResponseMessage().getSOAPPart().getEnvelope().toString();
			 System.out.println(str+"RESPONSE\n"+res);
		 } catch (SOAPException e) {
			 System.out.println("Errore nella lettura di request e response");
		 }
      	
    }
    
}
