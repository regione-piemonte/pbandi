/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.vo.configurazioneBandoLinea;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class EstremiBancariDTO implements RowMapper, Serializable {
	
	private Long idEstremiBancari;
	private String moltiplicatore;
	private String tipologiaConto;
	private Long idBanca;
	private Long idMonitAmmvoCont;	
	
	
	
	public Long getIdEstremiBancari() {
		return idEstremiBancari;
	}
	public void setIdEstremiBancari(Long idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	public String getMoltiplicatore() {
		return moltiplicatore;
	}
	public void setMoltiplicatore(String moltiplicatore) {
		this.moltiplicatore = moltiplicatore;
	}
	public String getTipologiaConto() {
		return tipologiaConto;
	}
	public void setTipologiaConto(String tipologiaConto) {
		this.tipologiaConto = tipologiaConto;
	}
	public Long getIdBanca() {
		return idBanca;
	}
	public void setIdBanca(Long idBanca) {
		this.idBanca = idBanca;
	}
	public Long getIdMonitAmmvoCont() {
		return idMonitAmmvoCont;
	}
	public void setIdMonitAmmvoCont(Long idMonitAmmvoCont) {
		this.idMonitAmmvoCont = idMonitAmmvoCont;
	}
	
	
	
	@Override
	public EstremiBancariDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		EstremiBancariDTO estremiBancariDTO = new EstremiBancariDTO();		
		estremiBancariDTO.setIdEstremiBancari(rs.getLong("ID_ESTREMI_BANCARI"));
		estremiBancariDTO.setIdBanca(rs.getLong("ID_BANCA"));
		estremiBancariDTO.setIdMonitAmmvoCont(rs.getLong("ID_MONIT_AMMVO_CONT"));
		estremiBancariDTO.setMoltiplicatore(rs.getString("MOLTIPLICATORE"));
		estremiBancariDTO.setTipologiaConto(rs.getString("TIPOLOGIA_CONTO"));
		
		return estremiBancariDTO;
	}
	
	
	

}
