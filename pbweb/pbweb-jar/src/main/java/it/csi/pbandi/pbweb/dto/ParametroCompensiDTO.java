package it.csi.pbandi.pbweb.dto;

public class ParametroCompensiDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idParametroCompenso;
	private Long categoria;
	private Long oreSettimanali;
	private Double compensoDovutoMensile;
	private Long giorniLavorabiliSettimanali;
	private Double orarioMedioGiornaliero;
	private Double budgetInizialeTirocinante;
	private Double budgetInizialeImpresa;

	public Long getIdParametroCompenso() {
		return idParametroCompenso;
	}

	public void setIdParametroCompenso(Long idParametroCompenso) {
		this.idParametroCompenso = idParametroCompenso;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public Long getOreSettimanali() {
		return oreSettimanali;
	}

	public void setOreSettimanali(Long oreSettimanali) {
		this.oreSettimanali = oreSettimanali;
	}

	public Double getCompensoDovutoMensile() {
		return compensoDovutoMensile;
	}

	public void setCompensoDovutoMensile(Double compensoDovutoMensile) {
		this.compensoDovutoMensile = compensoDovutoMensile;
	}

	public Long getGiorniLavorabiliSettimanali() {
		return giorniLavorabiliSettimanali;
	}

	public void setGiorniLavorabiliSettimanali(Long giorniLavorabiliSettimanali) {
		this.giorniLavorabiliSettimanali = giorniLavorabiliSettimanali;
	}

	public Double getOrarioMedioGiornaliero() {
		return orarioMedioGiornaliero;
	}

	public void setOrarioMedioGiornaliero(Double orarioMedioGiornaliero) {
		this.orarioMedioGiornaliero = orarioMedioGiornaliero;
	}

	public Double getBudgetInizialeTirocinante() {
		return budgetInizialeTirocinante;
	}

	public void setBudgetInizialeTirocinante(Double budgetInizialeTirocinante) {
		this.budgetInizialeTirocinante = budgetInizialeTirocinante;
	}

	public Double getBudgetInizialeImpresa() {
		return budgetInizialeImpresa;
	}

	public void setBudgetInizialeImpresa(Double budgetInizialeImpresa) {
		this.budgetInizialeImpresa = budgetInizialeImpresa;
	}

}
