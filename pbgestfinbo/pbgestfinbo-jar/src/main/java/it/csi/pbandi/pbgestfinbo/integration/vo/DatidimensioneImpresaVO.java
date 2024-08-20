/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.DettaglioImpresaVO;

public class DatidimensioneImpresaVO {
	
	private String numeroDomanda; 
	private String descDimImpresa; 
	private Date dataInseriemnto;
	private Date dataValutazioneEsito;
	private Long anno; 
	private BigDecimal idSoggetto; 
	
	
	
	
	
	
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Date getDataValutazioneEsito() {
		return dataValutazioneEsito;
	}

	public void setDataValutazioneEsito(Date dataValutazioneEsito) {
		this.dataValutazioneEsito = dataValutazioneEsito;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	private List<DettaglioImpresaVO> dettaglio = new ArrayList<DettaglioImpresaVO>();

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public String getDescDimImpresa() {
		return descDimImpresa;
	}

	public void setDescDimImpresa(String descDimImpresa) {
		this.descDimImpresa = descDimImpresa;
	}

	public Date getDataInseriemnto() {
		return dataInseriemnto;
	}

	public void setDataInseriemnto(Date dataInseriemnto) {
		this.dataInseriemnto = dataInseriemnto;
	}

	public List<DettaglioImpresaVO> getDettaglio() {
		return dettaglio;
	}

	public void setDettaglio(List<DettaglioImpresaVO> dettaglio) {
		this.dettaglio = dettaglio;
	} 
	
	
	
	

}
