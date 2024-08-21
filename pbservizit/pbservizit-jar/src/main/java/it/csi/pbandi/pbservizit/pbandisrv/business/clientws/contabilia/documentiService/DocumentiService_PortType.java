/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * DocumentiService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService;

public interface DocumentiService_PortType extends java.rmi.Remote {
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse elaboraDocumento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento elaboraDocumento) throws java.rmi.RemoteException;
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse elaboraDocumentoAsync(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento elaboraDocumentoAsync) throws java.rmi.RemoteException;
    public it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse leggiStatoElaborazioneDocumento(it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumento leggiStatoElaborazioneDocumento) throws java.rmi.RemoteException;
}
