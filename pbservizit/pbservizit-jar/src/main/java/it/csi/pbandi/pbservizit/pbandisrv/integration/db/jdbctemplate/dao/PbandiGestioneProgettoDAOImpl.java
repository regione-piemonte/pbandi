/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestioneprogetto.RecapitoVO;

public class PbandiGestioneProgettoDAOImpl extends PbandiDAO {

	public PbandiGestioneProgettoDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	public String findDescrizioneBeneficiario(Long idProgetto,Long idSoggettoBeneficiario) {
		logger.begin();
		String ret = "";
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT "+
			  " PBANDI_T_PERSONA_FISICA.cognome "+
			  " ||' ' "+
			  " || PBANDI_T_PERSONA_FISICA.nome AS DESCRIZIONEBENEFICIARIO "+
			  " FROM PBANDI_R_SOGGETTO_PROGETTO, "+
			  " PBANDI_T_PERSONA_FISICA          "+
			  " WHERE PBANDI_R_SOGGETTO_PROGETTO.ID_PROGETTO             =:idProgetto "+
			  " AND PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO =:idSoggettoBeneficiario "+
			  " AND (PBANDI_T_PERSONA_FISICA.id_persona_fisica             =PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica "+
			  " AND PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico          IS NULL ) "+
			  " UNION "+
			  " SELECT  "+
			  " PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico AS descrizioneBeneficiario "+
			  " FROM PBANDI_R_SOGGETTO_PROGETTO, "+
			  " PBANDI_T_ENTE_GIURIDICO          "+
			  " WHERE PBANDI_R_SOGGETTO_PROGETTO.ID_PROGETTO             =:idProgetto "+
			  " AND PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO =:idSoggettoBeneficiario "+
			  " AND PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico              =PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico")); 
			
			params.addValue("idProgetto",idProgetto,Types.BIGINT);
			params.addValue("idSoggettoBeneficiario",idSoggettoBeneficiario,Types.BIGINT);
			List<Map>	list = queryForList(sqlSelect.toString(), params);
			
			if(!isEmpty(list)){
				for (int i = 0; i < list.size(); i++) {
					Map<String,String> map=(Map<String,String> )list.get(i);
					ret= map.get("DESCRIZIONEBENEFICIARIO");				   
				}
			}
		} finally{
			 logger.end();
		}
		return ret;
	}

	public RecapitoVO caricaRecapiti(Long idProgetto) {
		logger.begin();
		RecapitoVO ret = new RecapitoVO();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			
			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append("sps.ID_RECAPITI, r.EMAIL, r.PEC, r.FAX, r.TELEFONO, r.ID_UTENTE_INS, r.ID_UTENTE_AGG, ");
			sqlSelect.append("r.DT_INIZIO_VALIDITA, r.DT_FINE_VALIDITA, sps.FLAG_EMAIL_CONFERMATA,  sps.FLAG_PEC_CONFERMATA, "); //PK prima ERA : r.FLAG_EMAIL_CONFERMATA, ");
			sqlSelect.append("sps.progr_soggetto_progetto, sps.progr_soggetto_progetto_sede ");
			sqlSelect.append("from pbandi_t_recapiti r, pbandi_r_soggetto_progetto sp, pbandi_r_sogg_progetto_sede sps ");
			sqlSelect.append("where sp.id_progetto = :idProgetto ");
			sqlSelect.append("and sp.progr_soggetto_progetto= sps.progr_soggetto_progetto ");
			sqlSelect.append("and r.id_recapiti(+) = sps.id_recapiti ");
			sqlSelect.append("and sp.id_tipo_anagrafica=1 ");
			sqlSelect.append("and sp.id_tipo_beneficiario <>4 ");
			sqlSelect.append("and sps.id_tipo_sede =1 ");
			sqlSelect.append("AND ROWNUM = 1 ");
			sqlSelect.append("order by sp.dt_inserimento desc , sp.dt_aggiornamento desc");
			
			logger.debug("sqlSelect="+sqlSelect.toString());

			ret = (RecapitoVO)queryForObject(sqlSelect.toString(), params, new RecapitoVORowMapper());
			logger.debug("ret="+ret);

		}catch(Exception e){
			logger.error("Errore "+e.getMessage());
			
		} finally{
			 logger.end();
		}
		return ret;
	}
	
	
	
	private class RecapitoVORowMapper implements RowMapper<RecapitoVO> {

		public RecapitoVO mapRow(
				ResultSet rs, int row) throws SQLException {

			// PK : queste non funzionano, forse perche la query ha r.
//			BeanMapper beanMapper = new BeanMapper();
//			RecapitoVO vo =	(RecapitoVO) beanMapper.toBean(rs, RecapitoVO.class);
			RecapitoVO vo = new RecapitoVO();
			vo.setIdRecapiti(rs.getLong(1));
			vo.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
			vo.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
			vo.setEmail(rs.getString("EMAIL"));
			vo.setPec(rs.getString("PEC"));
			vo.setFax(rs.getString("FAX"));
			vo.setFlagEmailConfermata(rs.getString("FLAG_EMAIL_CONFERMATA"));
			vo.setFlagPecConfermata(rs.getString("FLAG_PEC_CONFERMATA"));
			vo.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
			vo.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
			vo.setTelefono(rs.getString("TELEFONO"));
			vo.setProgrSoggettoProgetto(rs.getLong("progr_soggetto_progetto"));
			vo.setProgrSoggettoProgettoSede(rs.getLong("progr_soggetto_progetto_sede"));
			return vo;
		}
	}


	public RecapitoVO getMailFromRecapiti(String email) {
		logger.begin();
		RecapitoVO ret = null;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("email", email, Types.VARCHAR);
			
			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append(" r.ID_RECAPITI, r.EMAIL, r.FAX, r.TELEFONO, r.ID_UTENTE_INS, r.ID_UTENTE_AGG, ");
			sqlSelect.append(" r.DT_INIZIO_VALIDITA, r.DT_FINE_VALIDITA, '' as FLAG_EMAIL_CONFERMATA, ");
			sqlSelect.append(" 0 as progr_soggetto_progetto, 0 as progr_soggetto_progetto_sede ");
			sqlSelect.append(" FROM pbandi_t_recapiti r ");
			sqlSelect.append(" WHERE EMAIL= UPPER(:email)");
			sqlSelect.append(" order by ID_RECAPITI desc");
			
			logger.debug("sqlSelect="+sqlSelect.toString());

			List<RecapitoVO> lista = query(sqlSelect.toString(), params, new RecapitoVORowMapper());
			if(lista!=null && !lista.isEmpty()){
				ret = lista.get(0);
			}
			logger.debug("ret="+ret);

		}catch(Exception e){
			logger.error("Errore "+e.getMessage());
			
		} finally{
			logger.end();
		}
		return ret;
	}

	public RecapitoVO getPecFromRecapiti(String pec) {
		logger.begin();
		RecapitoVO ret = null;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("pec", pec, Types.VARCHAR);
			
			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append(" r.ID_RECAPITI, r.PEC, r.FAX, r.TELEFONO, r.ID_UTENTE_INS, r.ID_UTENTE_AGG, ");
			sqlSelect.append(" r.DT_INIZIO_VALIDITA, r.DT_FINE_VALIDITA, '' as FLAG_PEC_CONFERMATA, ");
			sqlSelect.append(" 0 as progr_soggetto_progetto, 0 as progr_soggetto_progetto_sede ");
			sqlSelect.append(" FROM pbandi_t_recapiti r ");
			sqlSelect.append(" WHERE PEC= UPPER(:pec)");
			sqlSelect.append(" order by ID_RECAPITI desc");
			
			logger.debug("sqlSelect="+sqlSelect.toString());

			List<RecapitoVO> lista = query(sqlSelect.toString(), params, new RecapitoVORowMapper());
			if(lista!=null && !lista.isEmpty()){
				ret = lista.get(0);
			}
			logger.debug("ret="+ret);

		}catch(Exception e){
			logger.error("Errore "+e.getMessage());
			
		} finally{
			logger.end();
		}
		return ret;
	}
	
	public int updateSoggettoProgettoSede(Long idUtente, Long progrSoggettoProgettoSede, Long idRecapito) {
		logger.begin();
		
	    int result = 0;
		StringBuilder sqlUpdate = new StringBuilder("");
//		sqlUpdate
//				.append("UPDATE PBANDI_R_SOGG_PROGETTO_SEDE ")
//				.append(" SET id_recapiti = :idRecapiti ")
//				.append(" WHERE progr_soggetto_progetto = :progrSoggettoProgetto");
		
		sqlUpdate
		.append("UPDATE PBANDI_R_SOGG_PROGETTO_SEDE ")
		.append(" SET id_recapiti = :idRecapito, flag_email_confermata='S', id_utente_agg = :idUtente")
		.append(" WHERE progr_soggetto_progetto_sede = :progrSoggettoProgettoSede");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("idRecapito", idRecapito, Types.BIGINT);
		params.addValue("idUtente", idUtente, Types.BIGINT);
		params.addValue("progrSoggettoProgettoSede", progrSoggettoProgettoSede, Types.BIGINT);
		
		result = modificaConRitorno(sqlUpdate.toString(), params);
		logger.debug("modified record: "+result);
			
		logger.end();
		return result;
	}
	
	public int updateSoggettoProgettoSedePec(Long idUtente, Long progrSoggettoProgettoSede, Long idRecapito) {
		logger.begin();
		
	    int result = 0;
		StringBuilder sqlUpdate = new StringBuilder("");
//		sqlUpdate
//				.append("UPDATE PBANDI_R_SOGG_PROGETTO_SEDE ")
//				.append(" SET id_recapiti = :idRecapiti ")
//				.append(" WHERE progr_soggetto_progetto = :progrSoggettoProgetto");
		
		sqlUpdate
		.append("UPDATE PBANDI_R_SOGG_PROGETTO_SEDE ")
		.append(" SET id_recapiti = :idRecapito, flag_pec_confermata='S', id_utente_agg = :idUtente")
		.append(" WHERE progr_soggetto_progetto_sede = :progrSoggettoProgettoSede");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("idRecapito", idRecapito, Types.BIGINT);
		params.addValue("idUtente", idUtente, Types.BIGINT);
		params.addValue("progrSoggettoProgettoSede", progrSoggettoProgettoSede, Types.BIGINT);
		
		result = modificaConRitorno(sqlUpdate.toString(), params);
		logger.debug("modified record: "+result);
			
		logger.end();
		return result;
	}

}
