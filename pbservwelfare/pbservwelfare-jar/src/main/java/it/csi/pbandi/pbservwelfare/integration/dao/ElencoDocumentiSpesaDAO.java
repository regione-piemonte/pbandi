/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.List;

import it.csi.pbandi.pbservwelfare.dto.DocumentoSpesa;

public interface ElencoDocumentiSpesaDAO {
	
	List<DocumentoSpesa> getDocumentiDiSpesaDaNumeroDomanda(String numeroDomanda);
	
	List<DocumentoSpesa> getDocumentiDiSpesaDaIdDichiarazioneSpesa(Integer identificativoDichiarazioneDiSpesa);
	
	List<DocumentoSpesa> getDocumentiDiSpesaDaIdDocSpesa(Integer identificativoDocumentoDiSpesa);
	
	DocumentoSpesa getInfoAggiuntive(Integer idDocumentoDiSpesa);

}
