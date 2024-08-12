package it.csi.pbandi.pbweb.dto.gestioneEconomica;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.QuotaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.Beneficiario;

public class BeneficiarioQuoteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Beneficiario[] beneficiario;
	private QuotaDTO[] quotaDTO;
	private QuotaDTO[] quotaEconomiaDTO;
	private Double importoCeduto;
	private Double importoMaxCedibile;
	private Double economieGiaCedute;
	
	public Beneficiario[] getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Beneficiario[] beneficiario) {
		this.beneficiario = beneficiario;
	}
	public QuotaDTO[] getQuotaDTO() {
		return quotaDTO;
	}
	public void setQuotaDTO(QuotaDTO[] quotaDTO) {
		this.quotaDTO = quotaDTO;
	}
	public QuotaDTO[] getQuotaEconomiaDTO() {
		return quotaEconomiaDTO;
	}
	public void setQuotaEconomiaDTO(QuotaDTO[] quotaEconomiaDTO) {
		this.quotaEconomiaDTO = quotaEconomiaDTO;
	}
	public Double getImportoCeduto() {
		return importoCeduto;
	}
	public void setImportoCeduto(Double importoCeduto) {
		this.importoCeduto = importoCeduto;
	}
	public Double getImportoMaxCedibile() {
		return importoMaxCedibile;
	}
	public void setImportoMaxCedibile(Double importoMaxCedibile) {
		this.importoMaxCedibile = importoMaxCedibile;
	}
	public Double getEconomieGiaCedute() {
		return economieGiaCedute;
	}
	public void setEconomieGiaCedute(Double economieGiaCedute) {
		this.economieGiaCedute = economieGiaCedute;
	}
	
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		builder.append("beneficiario", Arrays.toString(beneficiario));
		builder.append("quotaDTO", Arrays.toString(quotaDTO));
		builder.append("quotaEconomiaDTO", Arrays.toString(quotaEconomiaDTO));
		builder.append("importoCeduto", importoCeduto);
		builder.append("importoMaxCedibile", importoMaxCedibile);
		builder.append("economieGiaCedute", economieGiaCedute);
		return builder.toString();
	}

}
