package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione;

import java.util.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class FideiussioneVO extends GenericVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double importo;
	private String descrizione;
	private Date dataScadenza;
	private Date dataDecorrenza;
	private Long idTipoTrattamento;
	private String descrizioneTipoTrattamento;
	private String descBreveTipoTrattamento;
	private String numero;
	private String descEnteEmittente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	public Long getIdTipoTrattamento() {
		return idTipoTrattamento;
	}
	public void setIdTipoTrattamento(Long idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizioneTipoTrattamento(String descrizioneTipoTrattamento) {
		this.descrizioneTipoTrattamento = descrizioneTipoTrattamento;
	}
	public String getDescrizioneTipoTrattamento() {
		return descrizioneTipoTrattamento;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumero() {
		return numero;
	}
	public String getDescBreveTipoTrattamento() {
		return descBreveTipoTrattamento;
	}
	public void setDescBreveTipoTrattamento(String descBreveTipoTrattamento) {
		this.descBreveTipoTrattamento = descBreveTipoTrattamento;
	}
	public void setDescEnteEmittente(String descEnteEmittente) {
		this.descEnteEmittente = descEnteEmittente;
	}
	public String getDescEnteEmittente() {
		return descEnteEmittente;
	}
	

}
