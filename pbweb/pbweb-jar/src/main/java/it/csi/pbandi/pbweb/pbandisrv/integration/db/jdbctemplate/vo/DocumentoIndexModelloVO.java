package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DocumentoIndexModelloVO extends GenericVO {
	private String codiceModello;
	private String codiceModulo;
	private String descBreveTipoDocIndex;
	private BigDecimal idProgetto;
	private BigDecimal idTipoModello;
	private String nomeModello;
	private String versioneModello;

	public String getVersioneModello() {
		return versioneModello;
	}

	public void setVersioneModello(String versioneModello) {
		this.versioneModello = versioneModello;
	}

	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}

	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}

	public String getNomeModello() {
		return nomeModello;
	}

	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}

	public void setCodiceModello(String codiceModello) {
		this.codiceModello = codiceModello;
	}

	public String getCodiceModello() {
		return codiceModello;
	}

	public void setCodiceModulo(String codiceModulo) {
		this.codiceModulo = codiceModulo;
	}

	public String getCodiceModulo() {
		return codiceModulo;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public BigDecimal getIdTipoModello() {
		return idTipoModello;
	}

	public void setIdTipoModello(BigDecimal idTipoModello) {
		this.idTipoModello = idTipoModello;
	}

}
