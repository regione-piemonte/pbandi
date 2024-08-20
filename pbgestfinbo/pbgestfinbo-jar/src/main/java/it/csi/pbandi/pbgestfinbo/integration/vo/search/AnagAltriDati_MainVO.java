/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AnagAltriDati_MainVO {
	
	private String dimImpr_numDomanda;
	private String dimImpr_dataValutazione;
	private String dimImpr_esito;
	
	private Date durc_dataEmiss;
	private String durc_esito;
	private Date durc_dataScadenza;
	private String durc_numProto;
	
	private String bdna_numDomanda;
	private Date bdna_dataRic;
	private String bdna_numProto;
	
	private String anti_numDom;
	private Date anti_dataEmiss;
	private String anti_esito;
	private Date anti_dataScad;
	private String anti_numProto;
	// dati del documento 
	private String nomeDocumento;
	private BigDecimal idDocumentoIndex; 
	private Long idTipoDocumentoIndex; 
	
	private List<AnagAltriDati_MainVO> dimImpresa;
	private List<AnagAltriDati_MainVO> durc;
	private List<AnagAltriDati_MainVO> bdna;
	private List<AnagAltriDati_MainVO> antiMafia;
	private List<DettaglioImpresaVO>  dettaglio; 
	
	
	public AnagAltriDati_MainVO(String dimImpr_numDomanda, String dimImpr_dataValutazione, String dimImpr_esito,
			Date durc_dataEmiss, String durc_esito, Date durc_dataScadenza, String durc_numProto,
			String bdna_numDomanda, Date bdna_dataRic, String bdna_numProto, String anti_numDom, Date anti_dataEmiss,
			String anti_esito, Date anti_dataScad, String anti_numProto, List<AnagAltriDati_MainVO> dimImpresa,
			List<AnagAltriDati_MainVO> durc, List<AnagAltriDati_MainVO> bdna, List<AnagAltriDati_MainVO> antiMafia) {
		this.dimImpr_numDomanda = dimImpr_numDomanda;
		this.dimImpr_dataValutazione = dimImpr_dataValutazione;
		this.dimImpr_esito = dimImpr_esito;
		this.durc_dataEmiss = durc_dataEmiss;
		this.durc_esito = durc_esito;
		this.durc_dataScadenza = durc_dataScadenza;
		this.durc_numProto = durc_numProto;
		this.bdna_numDomanda = bdna_numDomanda;
		this.bdna_dataRic = bdna_dataRic;
		this.bdna_numProto = bdna_numProto;
		this.anti_numDom = anti_numDom;
		this.anti_dataEmiss = anti_dataEmiss;
		this.anti_esito = anti_esito;
		this.anti_dataScad = anti_dataScad;
		this.anti_numProto = anti_numProto;
		this.dimImpresa = dimImpresa;
		this.durc = durc;
		this.bdna = bdna;
		this.antiMafia = antiMafia;
	}

	

	

	public Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}





	public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}





	public String getNomeDocumento() {
		return nomeDocumento;
	}




	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}




	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}




	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}




	public AnagAltriDati_MainVO() {
	}


	public String getDimImpr_numDomanda() {
		return dimImpr_numDomanda;
	}

	public List<DettaglioImpresaVO> getDettaglio() {
		return dettaglio;
	}


	public void setDettaglio(List<DettaglioImpresaVO> dettaglio) {
		this.dettaglio = dettaglio;
	}


	public void setDimImpr_numDomanda(String dimImpr_numDomanda) {
		this.dimImpr_numDomanda = dimImpr_numDomanda;
	}


	public String getDimImpr_dataValutazione() {
		return dimImpr_dataValutazione;
	}


	public void setDimImpr_dataValutazione(String dimImpr_dataValutazione) {
		this.dimImpr_dataValutazione = dimImpr_dataValutazione;
	}


	public String getDimImpr_esito() {
		return dimImpr_esito;
	}


	public void setDimImpr_esito(String dimImpr_esito) {
		this.dimImpr_esito = dimImpr_esito;
	}


	public Date getDurc_dataEmiss() {
		return durc_dataEmiss;
	}


	public void setDurc_dataEmiss(Date durc_dataEmiss) {
		this.durc_dataEmiss = durc_dataEmiss;
	}


	public String getDurc_esito() {
		return durc_esito;
	}


	public void setDurc_esito(String durc_esito) {
		this.durc_esito = durc_esito;
	}


	public Date getDurc_dataScadenza() {
		return durc_dataScadenza;
	}


	public void setDurc_dataScadenza(Date durc_dataScadenza) {
		this.durc_dataScadenza = durc_dataScadenza;
	}


	public String getDurc_numProto() {
		return durc_numProto;
	}


	public void setDurc_numProto(String durc_numProto) {
		this.durc_numProto = durc_numProto;
	}


	public String getBdna_numDomanda() {
		return bdna_numDomanda;
	}


	public void setBdna_numDomanda(String bdna_numDomanda) {
		this.bdna_numDomanda = bdna_numDomanda;
	}


	public Date getBdna_dataRic() {
		return bdna_dataRic;
	}


	public void setBdna_dataRic(Date bdna_dataRic) {
		this.bdna_dataRic = bdna_dataRic;
	}


	public String getBdna_numProto() {
		return bdna_numProto;
	}


	public void setBdna_numProto(String bdna_numProto) {
		this.bdna_numProto = bdna_numProto;
	}


	public String getAnti_numDom() {
		return anti_numDom;
	}


	public void setAnti_numDom(String anti_numDom) {
		this.anti_numDom = anti_numDom;
	}


	public Date getAnti_dataEmiss() {
		return anti_dataEmiss;
	}


	public void setAnti_dataEmiss(Date anti_dataEmiss) {
		this.anti_dataEmiss = anti_dataEmiss;
	}


	public String getAnti_esito() {
		return anti_esito;
	}


	public void setAnti_esito(String anti_esito) {
		this.anti_esito = anti_esito;
	}


	public Date getAnti_dataScad() {
		return anti_dataScad;
	}


	public void setAnti_dataScad(Date anti_dataScad) {
		this.anti_dataScad = anti_dataScad;
	}


	public String getAnti_numProto() {
		return anti_numProto;
	}


	public void setAnti_numProto(String anti_numProto) {
		this.anti_numProto = anti_numProto;
	}


	public List<AnagAltriDati_MainVO> getDimImpresa() {
		return dimImpresa;
	}


	public void setDimImpresa(List<AnagAltriDati_MainVO> dimImpresa) {
		this.dimImpresa = dimImpresa;
	}


	public List<AnagAltriDati_MainVO> getDurc() {
		return durc;
	}


	public void setDurc(List<AnagAltriDati_MainVO> durc) {
		this.durc = durc;
	}


	public List<AnagAltriDati_MainVO> getBdna() {
		return bdna;
	}


	public void setBdna(List<AnagAltriDati_MainVO> bdna) {
		this.bdna = bdna;
	}


	public List<AnagAltriDati_MainVO> getAntiMafia() {
		return antiMafia;
	}


	public void setAntiMafia(List<AnagAltriDati_MainVO> antiMafia) {
		this.antiMafia = antiMafia;
	}


	@Override
	public String toString() {
		return "AnagAltriDati_MainVO [dimImpr_numDomanda=" + dimImpr_numDomanda + ", dimImpr_dataValutazione="
				+ dimImpr_dataValutazione + ", dimImpr_esito=" + dimImpr_esito + ", durc_dataEmiss=" + durc_dataEmiss
				+ ", durc_esito=" + durc_esito + ", durc_dataScadenza=" + durc_dataScadenza + ", durc_numProto="
				+ durc_numProto + ", bdna_numDomanda=" + bdna_numDomanda + ", bdna_dataRic=" + bdna_dataRic
				+ ", bdna_numProto=" + bdna_numProto + ", anti_numDom=" + anti_numDom + ", anti_dataEmiss="
				+ anti_dataEmiss + ", anti_esito=" + anti_esito + ", anti_dataScad=" + anti_dataScad
				+ ", anti_numProto=" + anti_numProto + ", dimImpresa=" + dimImpresa + ", durc=" + durc + ", bdna="
				+ bdna + ", antiMafia=" + antiMafia + "]";
	}
	
	
	
	
	

}
