/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * RicercaServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService;

public class RicercaServiceSoapBindingStub extends org.apache.axis.client.Stub implements it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[14];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaCapitoloEntrataGestione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestione"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestione.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestioneResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestioneResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestioneResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaOrdinativoIncasso");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncasso"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncasso"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncasso.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncassoResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncassoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncassoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaSinteticaSoggetti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggetti"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggetti"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggetti.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggettiResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggettiResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaEstesaOrdinativiSpesa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesa"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesa.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesaResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaDocumentoSpesa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoSpesa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoSpesa"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesa.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaDocumentoSpesaResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoSpesaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaImpegno");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegno"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegno"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegno.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegnoResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegnoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaOrdinativoSpesa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesa"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesa.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesaResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaDettaglioSoggetto");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggetto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggetti"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggetti.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggettiResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggettoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaCapitoloUscitaGestione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestione"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestione.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestioneResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestioneResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaProvvisoriDiCassa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaProvvisoriDiCassa"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaProvvisoriDiCassa"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassa.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaProvvisoriDiCassaResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaProvvisoriDiCassaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaAccertamento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamento"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamento.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamentoResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamentoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamentoResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaEstesaLiquidazioni");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioni"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioni"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioni.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioniResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioniResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaLiquidazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazione"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazione.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazioneResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazioneResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazioneResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaDocumentoEntrata");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoEntrata"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoEntrata"), it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrata.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaDocumentoEntrataResponse"));
        oper.setReturnClass(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrataResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoEntrataResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

    }

    public RicercaServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public RicercaServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public RicercaServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "baseRicercaDocumentoResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaDocumentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaDocumentoEntrataResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrataResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaDocumentoSpesaResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/documenti/svc/1.0", "ricercaProvvisoriDiCassaResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "accertamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Accertamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseRequest");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseRicercaRequest");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "baseRicercaResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "capitolo");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Capitolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "capitoloEntrataGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloEntrataGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "capitoloUscitaGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloUscitaGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "categoria");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Categoria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "classificatoreGenerico");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ClassificatoreGenerico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "contatti");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Contatti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ente");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "entitaBase");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "entitaCodificataBase");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.EntitaCodificataBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "errore");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "esito");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "impegno");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "importo");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Importo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "importoCapitoloEntrataGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloEntrataGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "importoCapitoloUscitaGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ImportoCapitoloUscitaGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "liquidazione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Liquidazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "liquidazioneAtti");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "macroaggregato");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Macroaggregato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "mandatoDiPagamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "messaggio");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Messaggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "messaggioBase");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MessaggioBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "missione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Missione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "modalitaPagamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "movimentoGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MovimentoGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "naturaGiuridica");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.NaturaGiuridica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ordinativo");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ordinativo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ordinativoIncasso");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoIncasso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ordinativoPagamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.OrdinativoPagamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "pianoDeiContiFinanziario");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.PianoDeiContiFinanziario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "programma");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Programma.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "provvedimento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "recapito");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ricercaPaginataRequest");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "ricercaPaginataResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaPaginataResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "sede");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "sesso");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sesso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "siNoEnum");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoEnum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "siNoIndifferenteEnum");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SiNoIndifferenteEnum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "soggetto");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "stato");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Stato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "strutturaAmministrativa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subAccertamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubAccertamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subImpegno");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubImpegno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subOrdinativoIncasso");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoIncasso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "subOrdinativoPagamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.SubOrdinativoPagamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipoFinanziamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFinanziamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipoFondo");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoFondo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipologia");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Tipologia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "tipoProvvisorioDiCassa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.TipoProvvisorioDiCassa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/integ/data/1.0", "titolo");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Titolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "documento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Documento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "documentoEntrata");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoEntrata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "documentoSpesa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "ordine");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Ordine.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/data/1.0", "provvisorioDiCassa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ProvvisorioDiCassa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "baseRicercaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "baseRicercaSoggetti");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaSoggetti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "baseRicercaSoggettiResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.BaseRicercaSoggettiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamento");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaAccertamentoResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamentoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitolo");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloEntrataGestioneResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestioneResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaCapitoloUscitaGestioneResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggetti");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggetti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDettaglioSoggettiResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoEntrata");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaDocumentoSpesa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioni");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaLiquidazioniResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaEstesaOrdinativiSpesaResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegno");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaImpegnoResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaLiquidazioneResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazioneResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaMovimentoGestione");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaMovimentoGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncasso");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncasso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoIncassoResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncassoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaOrdinativoSpesaResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaProvvisoriDiCassa");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggetti");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggetti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://siac.csi.it/ricerche/svc/1.0", "ricercaSinteticaSoggettiResponse");
            cachedSerQNames.add(qName);
            cls = it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse.class;
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

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestioneResponse ricercaCapitoloEntrataGestione(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestione ricercaCapitoloEntrataGestione) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaCapitoloEntrataGestione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaCapitoloEntrataGestione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestioneResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestioneResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloEntrataGestioneResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncassoResponse ricercaOrdinativoIncasso(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncasso ricercaOrdinativoIncasso) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaOrdinativoIncasso"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaOrdinativoIncasso});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncassoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncassoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoIncassoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse ricercaSinteticaSoggetti(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggetti ricercaSinteticaSoggetti) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaSinteticaSoggetti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaSinteticaSoggetti});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse ricercaEstesaOrdinativiSpesa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesa ricercaEstesaOrdinativiSpesa) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaEstesaOrdinativiSpesa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaEstesaOrdinativiSpesa});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse ricercaDocumentoSpesa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesa ricercaDocumentoSpesa) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaDocumentoSpesa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaDocumentoSpesa});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse ricercaImpegno(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegno ricercaImpegno) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaImpegno"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaImpegno});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesaResponse ricercaOrdinativoSpesa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesa ricercaOrdinativoSpesa) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaOrdinativoSpesa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaOrdinativoSpesa});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaOrdinativoSpesaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse ricercaDettaglioSoggetto(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggetti ricercaDettaglioSoggetto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaDettaglioSoggetto"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaDettaglioSoggetto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse ricercaCapitoloUscitaGestione(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestione ricercaCapitoloUscitaGestione) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaCapitoloUscitaGestione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaCapitoloUscitaGestione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassaResponse ricercaProvvisoriDiCassa(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassa ricercaProvvisoriDiCassa) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaProvvisoriDiCassa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaProvvisoriDiCassa});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaProvvisoriDiCassaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamentoResponse ricercaAccertamento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamento ricercaAccertamento) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaAccertamento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaAccertamento});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamentoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamentoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaAccertamentoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse ricercaEstesaLiquidazioni(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioni ricercaEstesaLiquidazioni) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaEstesaLiquidazioni"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaEstesaLiquidazioni});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazioneResponse ricercaLiquidazione(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazione ricercaLiquidazione) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaLiquidazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaLiquidazione});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazioneResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazioneResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaLiquidazioneResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrataResponse ricercaDocumentoEntrata(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrata ricercaDocumentoEntrata) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ricercaDocumentoEntrata"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ricercaDocumentoEntrata});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrataResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrataResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoEntrataResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
