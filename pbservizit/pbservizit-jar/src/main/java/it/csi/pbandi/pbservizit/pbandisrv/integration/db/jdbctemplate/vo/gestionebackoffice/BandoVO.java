/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class BandoVO extends GenericVO {
	 
	private java.lang.Long idBando = null;
	private java.lang.String titoloBando = null;
	private java.lang.Long idBandoLinea = null;
	private java.lang.String nomeBandoLinea = null;
	private java.lang.String descMateria = null;
	private java.lang.Long idMateria = null;
	private java.lang.Long idLineaDiIntervento = null;
	
	public java.lang.Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	public void setIdLineaDiIntervento(java.lang.Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
	public String getTitoloBando() {
		return titoloBando;
	}
	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}
	public void setIdBandoLinea(java.lang.Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public java.lang.Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setNomeBandoLinea(java.lang.String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	public java.lang.String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	public void setDescMateria(java.lang.String descMateria) {
		this.descMateria = descMateria;
	}
	public java.lang.String getDescMateria() {
		return descMateria;
	}
	public void setIdMateria(java.lang.Long idMateria) {
		this.idMateria = idMateria;
	}
	public java.lang.Long getIdMateria() {
		return idMateria;
	}
}
