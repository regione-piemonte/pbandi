/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class CronoprogrammaFasiItem implements java.io.Serializable, RowMapper {

	private static final long serialVersionUID = 1L;

	private Long idIter;
	private String descIter;
	private Long idFaseMonit;
	private String descFaseMonit;
	private Date dataLimite;
	private Date dataPrevista;
	private Date dataEffettiva;
	private String estremiAttoAmministrativo;
	private String motivoScostamento;
	private boolean edit;
	private Long idDichiarazioneSpesa;
	private Long idTipoDichiarazSpesa;
	private boolean isFaseIterAttiva;
	private Long flagFaseChiusa;
	private String flagEstremiVisObb;
	private String flagPrevAbilitata;

	public CronoprogrammaFasiItem() {
		this.edit = false;
		this.isFaseIterAttiva = false;
	}

	public Long getIdIter() {
		return idIter;
	}

	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}

	public String getDescIter() {
		return descIter;
	}

	public void setDescIter(String descIter) {
		this.descIter = descIter;
	}

	public Long getIdFaseMonit() {
		return idFaseMonit;
	}

	public void setIdFaseMonit(Long idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}

	public String getDescFaseMonit() {
		return descFaseMonit;
	}

	public void setDescFaseMonit(String descFaseMonit) {
		this.descFaseMonit = descFaseMonit;
	}

	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}

	public Date getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public Date getDataEffettiva() {
		return dataEffettiva;
	}

	public void setDataEffettiva(Date dataEffettiva) {
		this.dataEffettiva = dataEffettiva;
	}

	public String getEstremiAttoAmministrativo() {
		return estremiAttoAmministrativo;
	}

	public void setEstremiAttoAmministrativo(String estremiAttoAmministrativo) {
		this.estremiAttoAmministrativo = estremiAttoAmministrativo;
	}

	public String getMotivoScostamento() {
		return motivoScostamento;
	}

	public void setMotivoScostamento(String motivoScostamento) {
		this.motivoScostamento = motivoScostamento;
	}

	public boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		if (edit == null)
			this.edit = false;
		this.edit = edit;
	}

	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public Long getIdTipoDichiarazSpesa() {
		return idTipoDichiarazSpesa;
	}

	public void setIdTipoDichiarazSpesa(Long idTipoDichiarazSpesa) {
		this.idTipoDichiarazSpesa = idTipoDichiarazSpesa;
	}

	public boolean isFaseIterAttiva() {
		return isFaseIterAttiva;
	}

	public void setFaseIterAttiva(boolean isFaseIterAttiva) {
		this.isFaseIterAttiva = isFaseIterAttiva;
	}

	public Long getFlagFaseChiusa() {
		return flagFaseChiusa;
	}

	public void setFlagFaseChiusa(Long flagFaseChiusa) {
		this.flagFaseChiusa = flagFaseChiusa;
	}

	public String getFlagEstremiVisObb() {
		return flagEstremiVisObb;
	}

	public void setFlagEstremiVisObb(String flagEstremiVisObb) {
		this.flagEstremiVisObb = flagEstremiVisObb;
	}

	public String getFlagPrevAbilitata() {
		return flagPrevAbilitata;
	}

	public void setFlagPrevAbilitata(String flagPrevAbilitata) {
		this.flagPrevAbilitata = flagPrevAbilitata;
	}

	@Override
	public CronoprogrammaFasiItem mapRow(ResultSet rs, int rowNum) throws SQLException {

		CronoprogrammaFasiItem temp = new CronoprogrammaFasiItem();

		temp.idIter = rs.getLong("id_iter");
		temp.descIter = rs.getString("desc_iter");
		temp.idFaseMonit = rs.getLong("id_fase_monit");
		temp.descFaseMonit = rs.getString("desc_fase_monit");
		temp.dataLimite = rs.getDate("data_limite");
		temp.dataPrevista = rs.getDate("data_prevista");
		temp.dataEffettiva = rs.getDate("data_effettiva");
		temp.motivoScostamento = rs.getString("motivo_scostamento");
		temp.idDichiarazioneSpesa = rs.getLong("id_dichiarazione_spesa");
		temp.flagFaseChiusa = rs.getLong("flag_fase_chiusa");
		temp.edit = false;
		temp.flagEstremiVisObb = rs.getString("flag_estremi_vis_obb");
		temp.flagPrevAbilitata = rs.getString("flag_prev_abilitata");
		return temp;
	}

	@Override
	public String toString() {
		return "CronoprogrammaFasiItem [idIter=" + idIter + ", descIter=" + descIter + ", idFaseMonit=" + idFaseMonit
				+ ", descFaseMonit=" + descFaseMonit + ", dataLimite=" + dataLimite + ", dataPrevista=" + dataPrevista
				+ ", dataEffettiva=" + dataEffettiva + ", estremiAttoAmministrativo=" + estremiAttoAmministrativo
				+ ", motivoScostamento=" + motivoScostamento + ", edit=" + edit + ", idDichiarazioneSpesa="
				+ idDichiarazioneSpesa + ", idTipoDichiarazSpesa=" + idTipoDichiarazSpesa + ", isFaseIterAttiva="
				+ isFaseIterAttiva + ", flagFaseChiusa=" + flagFaseChiusa + ", flagEstremiVisObb=" + flagEstremiVisObb
				+ "]";
	}

}
