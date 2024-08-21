/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.util.List;

public class AllegatiMacroSezioneStampaRimodulazCEVO extends GenericVO {
	
	static final long serialVersionUID = 1;
	
	private String descMicroSezione;
	private String descTipoAllegato;
	private BigDecimal idTipoDocumentoIndex;
	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal numOrdinamentoMacroSezione;
	private BigDecimal numOrdinamentoMicroSezione;
	private String flagAllegato;;
	private BigDecimal idMicroSezioneModulo;
	private BigDecimal idProgetto;
	
	public String getDescMicroSezione() {
		return descMicroSezione;
	}
	public void setDescMicroSezione(String descMicroSezione) {
		this.descMicroSezione = descMicroSezione;
	}
	public String getDescTipoAllegato() {
		return descTipoAllegato;
	}
	public void setDescTipoAllegato(String descTipoAllegato) {
		this.descTipoAllegato = descTipoAllegato;
	}
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public BigDecimal getNumOrdinamentoMacroSezione() {
		return numOrdinamentoMacroSezione;
	}
	public void setNumOrdinamentoMacroSezione(BigDecimal numOrdinamentoMacroSezione) {
		this.numOrdinamentoMacroSezione = numOrdinamentoMacroSezione;
	}
	public BigDecimal getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}
	public void setNumOrdinamentoMicroSezione(BigDecimal numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
	public String getFlagAllegato() {
		return flagAllegato;
	}
	public void setFlagAllegato(String flagAllegato) {
		this.flagAllegato = flagAllegato;
	}
	public BigDecimal getIdMicroSezioneModulo() {
		return idMicroSezioneModulo;
	}
	public void setIdMicroSezioneModulo(BigDecimal idMicroSezioneModulo) {
		this.idMicroSezioneModulo = idMicroSezioneModulo;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	@Override
	public boolean isPKValid() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List getPK() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "AllegatiMacroSezioneStampaRimodulazCEVO [descMicroSezione=" + descMicroSezione + ", descTipoAllegato="
				+ descTipoAllegato + ", idTipoDocumentoIndex=" + idTipoDocumentoIndex + ", progrBandoLineaIntervento="
				+ progrBandoLineaIntervento + ", numOrdinamentoMacroSezione=" + numOrdinamentoMacroSezione
				+ ", numOrdinamentoMicroSezione=" + numOrdinamentoMicroSezione + ", flagAllegato=" + flagAllegato
				+ ", idMicroSezioneModulo=" + idMicroSezioneModulo + ", idProgetto=" + idProgetto + "]";
	}
	
	/*	
	SELECT DISTINCT micro.desc_micro_sezione ,
    micro.contenuto_micro_sezione desc_tipo_allegato,
    relaz.id_tipo_documento_index ,
    relaz.progr_bando_linea_intervento ,
    RELAZ.NUM_ORDINAMENTO_MACRO_SEZIONE ,
    relaz2.num_ordinamento_micro_sezione,
    flag_allegati.FLAG_ALLEGATO,
    flag_allegati.ID_MICRO_SEZIONE_MODULO,
    flag_allegati.ID_PROGETTO,
  FROM pbandi_d_macro_sezione_modulo macro,
    pbandi_d_micro_sezione_modulo micro,
    pbandi_r_bl_tipo_doc_sez_mod relaz,
    pbandi_r_bl_tipo_doc_MICRO_sez relaz2,
    pbandi_c_tipo_documento_index tipo,
    pbandi_r_bando_linea_intervent rbl,
    PBANDI_W_PROGETTO_DOC_ALLEG flag_allegati
  WHERE 1=1
  AND (micro.desc_micro_sezione LIKE '%ALLEGATI%' OR micro.desc_micro_sezione LIKE tipo.desc_breve_tipo_doc_index)
  AND relaz.id_macro_sezione_modulo      = macro.id_macro_sezione_modulo
  AND relaz.progr_bl_tipo_doc_sez_mod    = relaz2.progr_bl_tipo_doc_sez_mod
  AND relaz2.id_micro_sezione_modulo     = micro.id_micro_sezione_modulo
  AND relaz.id_tipo_documento_index      = tipo.id_tipo_documento_index
  AND relaz.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento
  AND flag_allegati.ID_MICRO_SEZIONE_MODULO = micro.ID_MICRO_SEZIONE_MODULO
  AND flag_allegati.NUM_ORDINAMENTO_MICRO_SEZIONE = relaz2.num_ordinamento_micro_sezione
  AND flag_allegati.ID_PROGETTO = 118553
  AND  relaz.progr_bando_linea_intervento = 3500
  ORDER BY RELAZ.PROGR_BANDO_LINEA_INTERVENTO,
    relaz.num_ordinamento_macro_sezione,
    relaz2.num_ordinamento_micro_sezione;
	*/
}
