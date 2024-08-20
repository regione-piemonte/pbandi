/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

public class IdDomandaVO {
	
	private String numeroDomanda;
	private Long idDomanda;
	private Long idSoggetto;
	private String descTipoSoggetto; 
	
	
	
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}

	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}

	public Long getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	@Override
	public String toString() {
		return "IdDomandaVO [numeroDomanda=" + numeroDomanda + ", idDomanda=" + idDomanda + ", idSoggetto=" + idSoggetto
				+ "]";
	}




//	@Override
//    public boolean equals(Object that) {
//		IdDomandaVO s = (IdDomandaVO) that;
//      return this.idDomanda.equals(s.getIdDomanda());
//    }
//	
//	 @Override
//	 public int hashCode(){
//	  return this.idDomanda.hashCode();
//	 }

}
