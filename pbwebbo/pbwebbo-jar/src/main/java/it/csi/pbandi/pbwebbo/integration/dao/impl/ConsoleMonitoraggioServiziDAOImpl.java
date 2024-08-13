/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import it.csi.pbandi.pbwebbo.dto.MonitoraggioServiziDTO;
import it.csi.pbandi.pbwebbo.dto.PbandiServiziContDTO;
import it.csi.pbandi.pbwebbo.dto.PbandiTMonServAmmvoContabVO;
import it.csi.pbandi.pbwebbo.integration.dao.ConsoleMonitoraggioServiziDAO;
import it.csi.pbandi.pbwebbo.util.Constants;
import java.time.LocalDate;
@Component("ConsoleMonitoraggioServiziDAO")
public class ConsoleMonitoraggioServiziDAOImpl  extends JdbcDaoSupport implements ConsoleMonitoraggioServiziDAO {
	

private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

@Autowired
public ConsoleMonitoraggioServiziDAOImpl(DataSource dataSource) {
	setDataSource(dataSource);
}




private class PbandiMonitInterventoRowMapper implements RowMapper<PbandiTMonServAmmvoContabVO> {

	public PbandiTMonServAmmvoContabVO mapRow(ResultSet rs, int rowNum) throws SQLException {

		PbandiTMonServAmmvoContabVO obj = new PbandiTMonServAmmvoContabVO();
		obj.setEsito(rs.getString("ESITO"));
		obj.setCodiceErrore(rs.getString("CODICE_ERRORE"));
		obj.setMessaggioErrore(rs.getString("MESSAGGIO_ERRORE"));
		return obj;
		
	}
}


private class PbandiMonitInterventoRowMapperMonit implements RowMapper<PbandiTMonServAmmvoContabVO> {

	public PbandiTMonServAmmvoContabVO mapRow(ResultSet rs, int rowNum) throws SQLException {

		PbandiTMonServAmmvoContabVO obj = new PbandiTMonServAmmvoContabVO();
		obj.setEsito(rs.getString("ESITO"));
		obj.setIdServizio(rs.getInt("ID_SERVIZIO"));
		obj.setNumeroKo(rs.getInt("NUMERO_KO"));
		obj.setCategoriaServizio(rs.getString("DESC_CATEG_SERVIZIO"));
		return obj;
		
	}
}

private class rowMapperMonitoraggioServiziDTO implements RowMapper<MonitoraggioServiziDTO> {

	public MonitoraggioServiziDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		MonitoraggioServiziDTO obj = new MonitoraggioServiziDTO();
		obj.setEsito(rs.getString("ESITO"));
		obj.setCodiceErrore(rs.getString("CODICE_ERRORE"));
		obj.setMessaggioErrore(rs.getString("MESSAGGIO_ERRORE"));
		obj.setNomeServizio(rs.getString("NOME_SERVIZIO"));
		obj.setCategoriaServizio(rs.getString("DESC_CATEG_SERVIZIO"));
		obj.setDataInizioChiamata(rs.getTimestamp("DATETIME_INIZIO_CHIAMATA"));
		obj.setDataFineChiamata(rs.getTimestamp("DATETIME_FINE_CHIAMATA"));
		return obj;
		
	}
}

private class rowMapperPbandiServiziContDTO implements RowMapper<PbandiServiziContDTO> {

	public PbandiServiziContDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		PbandiServiziContDTO obj = new PbandiServiziContDTO();
		obj.setNomeServizio(rs.getString("NOME_SERVIZIO"));
		obj.setIdServizio(rs.getInt("ID_SERVIZIO"));
		return obj;
		
	}
}


@Override
public List<PbandiTMonServAmmvoContabVO> findError() {
	String prf = "[ConsoleMonitoraggioServiziDAOImpl::findError]";
	LOG.info(prf + " BEGIN");
	

	List<PbandiTMonServAmmvoContabVO> listErrorMessage = null;
	
	try {
		
		
		
		StringBuilder sqlSelect = new StringBuilder(
				"SELECT DISTINCT ESITO, CODICE_ERRORE, MESSAGGIO_ERRORE \r\n"
				+ "FROM PBANDI_T_MON_SERV WHERE CODICE_ERRORE  IS NOT NULL AND MESSAGGIO_ERRORE  IS NOT NULL  \r\n");
		
		LOG.info(sqlSelect);
		listErrorMessage = getJdbcTemplate().query(sqlSelect.toString(), new PbandiMonitInterventoRowMapper());
		
	}catch (Exception e) {
		LOG.error(e);
	}
	
	LOG.info(prf + " END");
	return listErrorMessage;
}


@Override
public List<PbandiTMonServAmmvoContabVO> getStatusMonitoraggio(java.util.Date dataInizio, java.util.Date dataFine, String codiceErrore, Long idServizio) {
	String prf = "[ConsoleMonitoraggioServiziDAOImpl::getEsitoMonitoraggio]";
	LOG.info(prf + " BEGIN");
	

	List<PbandiTMonServAmmvoContabVO> listaEsitiMonitoraggio = null;
	
	try {
		
		
		StringBuilder sqlSelect = new StringBuilder("SELECT  \r\n"
		+ "COUNT(PBANDI_T_MON_SERV.ID_SERVIZIO) AS NUMERO_KO, PBANDI_T_MON_SERV.ESITO, PBANDI_T_MON_SERV.ID_SERVIZIO, PBANDI_D_CATEG_SERVIZI.DESC_CATEG_SERVIZIO \r\n"
		+ "FROM PBANDI_T_MON_SERV \r\n"
		+ "		JOIN PBANDI_D_SERVIZI ON PBANDI_T_MON_SERV.ID_SERVIZIO = PBANDI_D_SERVIZI.ID_SERVIZIO \r\n"
		+ "		JOIN PBANDI_D_CATEG_SERVIZI ON PBANDI_D_CATEG_SERVIZI.ID_CATEG_SERVIZIO = PBANDI_D_SERVIZI.ID_CATEG_SERVIZIO \r\n"
		+ "		WHERE PBANDI_T_MON_SERV.ESITO IN ('KO', 'OK') AND DATETIME_INIZIO_CHIAMATA  >= ? AND DATETIME_FINE_CHIAMATA  <= ?  \r\n");
		if(codiceErrore != null) {
			sqlSelect.append("AND PBANDI_T_MON_SERV.CODICE_ERRORE  = ?");
		}	
		if(idServizio != null) {
			sqlSelect.append("AND PBANDI_T_MON_SERV.ID_SERVIZIO  = ?");
		}
		sqlSelect.append(" GROUP BY PBANDI_T_MON_SERV.ESITO, PBANDI_T_MON_SERV.ID_SERVIZIO, PBANDI_D_CATEG_SERVIZI.DESC_CATEG_SERVIZIO  \r\n"
		+ "ORDER BY NUMERO_KO DESC\r\n");
		
		LOG.info(sqlSelect);
		if(codiceErrore != null && idServizio != null) {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new PbandiMonitInterventoRowMapperMonit(), dataInizio, dataFine, codiceErrore, idServizio);
		}else if(codiceErrore == null && idServizio != null) {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new PbandiMonitInterventoRowMapperMonit(), dataInizio, dataFine,idServizio);
		}else if(idServizio == null && codiceErrore != null) {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new PbandiMonitInterventoRowMapperMonit(), dataInizio, dataFine,codiceErrore);
		}else {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new PbandiMonitInterventoRowMapperMonit(), dataInizio, dataFine);
		}
		
		
	}catch (Exception e) {
		LOG.error(e);
	}
	
	LOG.info(prf + " END");
	return listaEsitiMonitoraggio;
}

@Override
public List<MonitoraggioServiziDTO> getErrorMonitoraggio(java.util.Date dataInizio, java.util.Date dataFine, String codiceErrore,Long idServizio) {
	String prf = "[ConsoleMonitoraggioServiziDAOImpl::getEsitoMonitoraggio]";
	LOG.info(prf + " BEGIN");
	

	List<MonitoraggioServiziDTO> listaEsitiMonitoraggio = null;
	
	try {
		
		StringBuilder sqlSelect = new StringBuilder(
				
	
		 "SELECT PBANDI_T_MON_SERV.ESITO,\r\n"
		 + "       PBANDI_T_MON_SERV.CODICE_ERRORE,\r\n"
		 + "       SUBSTR(PBANDI_T_MON_SERV.MESSAGGIO_ERRORE, 1, 50) AS MESSAGGIO_ERRORE,\r\n"
		 + "       PBANDI_T_MON_SERV.DATETIME_INIZIO_CHIAMATA,\r\n"
		 + "       PBANDI_T_MON_SERV.DATETIME_FINE_CHIAMATA,\r\n"
		 + "       PBANDI_D_SERVIZI.NOME_SERVIZIO,\r\n"
		 + "       PBANDI_D_CATEG_SERVIZI.DESC_CATEG_SERVIZIO \r\n"
		 + "FROM PBANDI_T_MON_SERV\r\n"
		 + "JOIN PBANDI_D_SERVIZI ON PBANDI_T_MON_SERV.ID_SERVIZIO = PBANDI_D_SERVIZI.ID_SERVIZIO\r\n"
		 + "JOIN PBANDI_D_CATEG_SERVIZI ON PBANDI_D_CATEG_SERVIZI.ID_CATEG_SERVIZIO = PBANDI_D_SERVIZI.ID_CATEG_SERVIZIO\r\n"
		 + "");
		sqlSelect.append(" WHERE PBANDI_T_MON_SERV.DATETIME_INIZIO_CHIAMATA  >=  ?\r\n"
				+ "AND PBANDI_T_MON_SERV.DATETIME_FINE_CHIAMATA  <=  ?");
		
		if(codiceErrore != null) {
			sqlSelect.append(" AND PBANDI_T_MON_SERV.CODICE_ERRORE  = ? \r\n");
			LOG.info(sqlSelect);
			
		}
		if(idServizio != null) {
			sqlSelect.append(" AND PBANDI_T_MON_SERV.ID_SERVIZIO  = ?");
		}
			
		
		
		if(codiceErrore != null && idServizio != null) {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new rowMapperMonitoraggioServiziDTO(), dataInizio, dataFine, codiceErrore, idServizio);
		}else if(codiceErrore == null && idServizio != null) {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new rowMapperMonitoraggioServiziDTO(), dataInizio, dataFine,idServizio);
		}else if(idServizio == null && codiceErrore != null) {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new rowMapperMonitoraggioServiziDTO(), dataInizio, dataFine,codiceErrore);
		}else {
			listaEsitiMonitoraggio = getJdbcTemplate().query(sqlSelect.toString(), new rowMapperMonitoraggioServiziDTO(), dataInizio, dataFine);
		}
	}catch (Exception e) {
		LOG.error(e);
	}
	
	LOG.info(prf + " END");
	return listaEsitiMonitoraggio;
}

@Override	
public List<PbandiServiziContDTO> findServizi() {
	String prf = "[ConsoleMonitoraggioServiziDAOImpl::findServizi]";
	LOG.info(prf + " BEGIN");
	

	List<PbandiServiziContDTO> listaServizi = null;
	
	try {
		
		StringBuilder sqlSelect = new StringBuilder(
				"SELECT  NOME_SERVIZIO, ID_SERVIZIO   FROM PBANDI_D_SERVIZI \r\n"
				+ "ORDER BY ID_SERVIZIO DESC");
		
		LOG.info(sqlSelect);
		listaServizi = getJdbcTemplate().query(sqlSelect.toString(), new rowMapperPbandiServiziContDTO());
		
	}catch (Exception e) {
		LOG.error(e);
	}
	
	LOG.info(prf + " END");
	return listaServizi;
}

}
