/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.integration.vo;

import java.beans.IntrospectionException;
import java.math.*;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class AffidamentoVO extends PbandiTAppaltoVO {

	private BigDecimal idProgetto;
	private String descTipologiaAppalto;
	private String cigProcAgg;
	private String codProcAgg;
	private String descStatoAffidamento;
	private BigDecimal idTipoDocumentoIndex;
	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}
	public void setDescTipologiaAppalto(String descTipologiaAppalto) {
		this.descTipologiaAppalto = descTipologiaAppalto;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getDescStatoAffidamento() {
		return descStatoAffidamento;
	}
	public void setDescStatoAffidamento(String descStatoAffidamento) {
		this.descStatoAffidamento = descStatoAffidamento;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nPbandiTAppaltoVO: ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
