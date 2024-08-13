/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;

import java.math.BigDecimal;

public class DocumentoIndexMaxVersioneDefinitivoVO extends PbandiTDocumentoIndexVO {
	
	//id_stato_tipo_doc_index
	
	private BigDecimal idStatoTipoDocIndex;

	public BigDecimal getIdStatoTipoDocIndex() {
		return idStatoTipoDocIndex;
	}

	public void setIdStatoTipoDocIndex(BigDecimal idStatoTipoDocIndex) {
		this.idStatoTipoDocIndex = idStatoTipoDocIndex;
	}
	
}
