package it.csi.pbandi.pbweb.dto.gestioneEconomica;

import java.io.Serializable;
import java.util.Arrays;

import it.csi.pbandi.pbweb.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.BandoLinea;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.EconomiaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.ProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie.QuotaDTO;

public class ModificaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private QuotaDTO[] quoteProgettoCedente;
	private QuotaDTO[] quoteEconomiaCedente;
	private QuotaDTO[] quoteProgettoRicevente;
	private QuotaDTO[] quoteEconomiaRicevente;
	private BandoLinea[] bandoLinea;
	private EconomiaDTO[] economiaDTO;
	private ProgettoDTO[] progettiBandoLinea;
	private DatiGeneraliDTO datiGeneraliDTO;
	private Double economieGiaCedute;
	private Double importoMaxCedibile;
	private ResponseCodeMessage responseCodeMessage;
	
	public QuotaDTO[] getQuoteProgettoCedente() {
		return quoteProgettoCedente;
	}
	public void setQuoteProgettoCedente(QuotaDTO[] quoteProgettoCedente) {
		this.quoteProgettoCedente = quoteProgettoCedente;
	}
	public QuotaDTO[] getQuoteEconomiaCedente() {
		return quoteEconomiaCedente;
	}
	public void setQuoteEconomiaCedente(QuotaDTO[] quoteEconomiaCedente) {
		this.quoteEconomiaCedente = quoteEconomiaCedente;
	}
	public QuotaDTO[] getQuoteProgettoRicevente() {
		return quoteProgettoRicevente;
	}
	public void setQuoteProgettoRicevente(QuotaDTO[] quoteProgettoRicevente) {
		this.quoteProgettoRicevente = quoteProgettoRicevente;
	}
	public QuotaDTO[] getQuoteEconomiaRicevente() {
		return quoteEconomiaRicevente;
	}
	public void setQuoteEconomiaRicevente(QuotaDTO[] quoteEconomiaRicevente) {
		this.quoteEconomiaRicevente = quoteEconomiaRicevente;
	}
	public BandoLinea[] getBandoLinea() {
		return bandoLinea;
	}
	public void setBandoLinea(BandoLinea[] bandoLinea) {
		this.bandoLinea = bandoLinea;
	}
	public EconomiaDTO[] getEconomiaDTO() {
		return economiaDTO;
	}
	public void setEconomiaDTO(EconomiaDTO[] economiaDTO) {
		this.economiaDTO = economiaDTO;
	}
	public ProgettoDTO[] getProgettiBandoLinea() {
		return progettiBandoLinea;
	}
	public void setProgettiBandoLinea(ProgettoDTO[] progettiBandoLinea) {
		this.progettiBandoLinea = progettiBandoLinea;
	}
	public DatiGeneraliDTO getDatiGeneraliDTO() {
		return datiGeneraliDTO;
	}
	public void setDatiGeneraliDTO(DatiGeneraliDTO datiGeneraliDTO) {
		this.datiGeneraliDTO = datiGeneraliDTO;
	}
	public Double getEconomieGiaCedute() {
		return economieGiaCedute;
	}
	public void setEconomieGiaCedute(Double economieGiaCedute) {
		this.economieGiaCedute = economieGiaCedute;
	}
	public Double getImportoMaxCedibile() {
		return importoMaxCedibile;
	}
	public void setImportoMaxCedibile(Double importoMaxCedibile) {
		this.importoMaxCedibile = importoMaxCedibile;
	}
	public ResponseCodeMessage getResponseCodeMessage() {
		return responseCodeMessage;
	}
	public void setResponseCodeMessage(ResponseCodeMessage responseCodeMessage) {
		this.responseCodeMessage = responseCodeMessage;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModificaDTO [quoteProgettoCedente=");
		builder.append(Arrays.toString(quoteProgettoCedente));
		builder.append(", quoteEconomiaCedente=");
		builder.append(Arrays.toString(quoteEconomiaCedente));
		builder.append(", quoteProgettoRicevente=");
		builder.append(Arrays.toString(quoteProgettoRicevente));
		builder.append(", quoteEconomiaRicevente=");
		builder.append(Arrays.toString(quoteEconomiaRicevente));
		builder.append(", bandoLinea=");
		builder.append(Arrays.toString(bandoLinea));
		builder.append(", economiaDTO=");
		builder.append(Arrays.toString(economiaDTO));
		builder.append(", progettiBandoLinea=");
		builder.append(Arrays.toString(progettiBandoLinea));
		builder.append(", datiGeneraliDTO=");
		builder.append(datiGeneraliDTO);
		builder.append(", economieGiaCedute=");
		builder.append(economieGiaCedute);
		builder.append(", importoMaxCedibile=");
		builder.append(importoMaxCedibile);
		builder.append(", responseCodeMessage=");
		builder.append(responseCodeMessage);
		builder.append("]");
		return builder.toString();
	}

}