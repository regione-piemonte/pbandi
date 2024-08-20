/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.vo.affidamenti;

import java.math.BigDecimal;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;


public class DocumentoIndexMaxVersioneDefinitivoVO extends PbandiTDocumentoIndexVO {

	private BigDecimal idStatoTipoDocIndex;

	public BigDecimal getIdStatoTipoDocIndex() {
		return idStatoTipoDocIndex;
	}

	public void setIdStatoTipoDocIndex(BigDecimal idStatoTipoDocIndex) {
		this.idStatoTipoDocIndex = idStatoTipoDocIndex;
	}
}
