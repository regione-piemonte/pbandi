/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.datigenerali.DatiGeneraliVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTNotificaProcessoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiDatiGeneraliDAOImpl extends PbandiDAO {

	public PbandiDatiGeneraliDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub //PK
	}

	public DatiGeneraliVO findSedeIntervento(Long idProgetto,
			Long idSoggettoBeneficiario,String tipoSede){
		getLogger().begin();
		// TODO questa andrebbe filtrata utilizzando la logica di individuazione dei beneficiari
		DatiGeneraliVO result = new DatiGeneraliVO();
		try {
			
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder("PBANDI_T_PROGETTO"));
			tables.append(",PBANDI_R_SOGGETTO_PROGETTO")
			.append(",PBANDI_T_SOGGETTO")
			.append(",PBANDI_R_SOGG_PROGETTO_SEDE")
			.append(",PBANDI_D_TIPO_SEDE")
			.append(",PBANDI_T_INDIRIZZO")
			.append(",PBANDI_D_COMUNE")
			.append(",PBANDI_D_PROVINCIA");
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			conditionList.add((new StringBuilder()).append("PBANDI_T_PROGETTO.ID_PROGETTO=:idProgetto"));
			
			StringBuilder joinSede= new StringBuilder(""); 
			joinSede.append(" PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO=PBANDI_R_SOGG_PROGETTO_SEDE.PROGR_SOGGETTO_PROGETTO");
			joinSede.append(" and PBANDI_D_TIPO_SEDE.DESC_BREVE_TIPO_SEDE=:tipoSede");
			joinSede.append(" and PBANDI_R_SOGG_PROGETTO_SEDE.ID_TIPO_SEDE=PBANDI_D_TIPO_SEDE.ID_TIPO_SEDE");
			conditionList.add(joinSede);
			
			StringBuilder joinIndirizzo= new StringBuilder(" PBANDI_R_SOGG_PROGETTO_SEDE.ID_INDIRIZZO=PBANDI_T_INDIRIZZO.ID_INDIRIZZO");
			joinIndirizzo.append(" and PBANDI_T_INDIRIZZO.ID_COMUNE=PBANDI_D_COMUNE.ID_COMUNE");
			joinIndirizzo.append(" and PBANDI_T_INDIRIZZO.ID_PROVINCIA=PBANDI_D_PROVINCIA.ID_PROVINCIA");
			conditionList.add(joinIndirizzo);
				
			StringBuilder joinSoggetto= new StringBuilder(" PBANDI_T_SOGGETTO.ID_SOGGETTO=:idSoggettoBeneficiario");
			joinSoggetto.append(" and PBANDI_T_PROGETTO.ID_PROGETTO=PBANDI_R_SOGGETTO_PROGETTO.ID_PROGETTO");
			joinSoggetto.append(" and PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO=PBANDI_T_SOGGETTO.ID_SOGGETTO");
			conditionList.add(joinSoggetto);
			
			// PARAMETRI DINAMICI
			params.addValue("idProgetto",idProgetto,Types.BIGINT);
			params.addValue("idSoggettoBeneficiario",idSoggettoBeneficiario,Types.BIGINT);
			params.addValue("tipoSede",tipoSede,Types.VARCHAR);
	
			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append("PBANDI_T_INDIRIZZO.DESC_INDIRIZZO ||   ' - ' ||   PBANDI_D_COMUNE.cap || ") 
            .append("' ' || PBANDI_D_COMUNE.desc_comune || ")
            .append("'(' || PBANDI_D_PROVINCIA.SIGLA_PROVINCIA || ')' as sedeIntervento")
			.append(" FROM ")
			.append(tables);
			
			setWhereClause( conditionList, sqlSelect,tables);		
			
			List<DatiGeneraliVO> list = query(sqlSelect.toString(), params, new PbandiDatiGeneraliRowMapper());
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
		} finally{
			 getLogger().end();
		}
		return result;
	}

	private class PbandiDatiGeneraliRowMapper implements RowMapper<DatiGeneraliVO>{

		public DatiGeneraliVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			DatiGeneraliVO vo=(DatiGeneraliVO) beanMapper.toBean(rs,DatiGeneraliVO.class);
			return vo;
		}
	}

	public String findDescrizioneBeneficiario(Long idProgetto,Long idSoggettoBeneficiario) {
		getLogger().begin();
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
			 getLogger().end();
		}
		return ret;
	}


	public List<PbandiTNotificaProcessoVO> caricaListaNotifiche(
			Long idBandoLineaIntervento,Long idSoggettoBeneficiario, long[] idProgetti, String codiceRuolo) {
		logger.begin();
		
		logger.debug("idBandoLineaIntervento="+idBandoLineaIntervento+", idSoggettoBeneficiario="+idSoggettoBeneficiario
		 + ", idProgetto="+idProgetti+", codiceRuolo="+codiceRuolo);
		
		List<PbandiTNotificaProcessoVO> lista = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		int idRuolo = 0;
		if("".equals(codiceRuolo) || codiceRuolo == null){
			idRuolo = 0;
		} else if("BENEFICIARIO".equals(codiceRuolo) || "PERSONA-FISICA".equals(codiceRuolo) || "BEN-MASTER".equals(codiceRuolo) ){
			idRuolo = 2;
		} else {
			idRuolo = 1;
		}
		
		StringBuilder sqlSelect = new StringBuilder("");

		sqlSelect.append("SELECT ID_NOTIFICA, ID_PROGETTO, ID_RUOLO_DI_PROCESSO_DEST, SUBJECT_NOTIFICA, ");
		sqlSelect.append("  STATO_NOTIFICA, ID_UTENTE_MITT, DT_NOTIFICA, ID_UTENTE_AGG, ");
		sqlSelect.append("  DT_AGG_STATO_NOTIFICA, ID_TEMPLATE_NOTIFICA, ID_ENTITA, ID_TARGET, MESSAGE_NOTIFICA ");
		sqlSelect.append("FROM PBANDI_T_NOTIFICA_PROCESSO ");
		//MESSAGE_NOTIFICA2,
		
		if(idProgetti!=null){
			
			sqlSelect.append("WHERE id_progetto IN (");
			int max = idProgetti.length -1;
			
			for (int i = 0; i < idProgetti.length; i++) {
				sqlSelect.append(idProgetti[i]);
				if(i<max)
					sqlSelect.append(" , ");
			}
			sqlSelect.append(")");
			
		}else{
			sqlSelect.append("WHERE id_progetto IN ");
			sqlSelect.append("( SELECT DISTINCT id_progetto ");
			sqlSelect.append("   FROM pbandi_v_processo_ben_bl b ");
			sqlSelect.append("   WHERE b.PROGR_BANDO_LINEA_INTERVENTO = :idBandoLineaIntervento ");
			sqlSelect.append("     AND b.ID_SOGGETTO_BENEFICIARIO = :idSoggettoBeneficiario ) ");
			
			params.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, Types.BIGINT);
			params.addValue("idBandoLineaIntervento", idBandoLineaIntervento, Types.BIGINT);
		}

		if(idRuolo>0){
			sqlSelect.append(" AND ID_RUOLO_DI_PROCESSO_DEST = :idRuolo");
			params.addValue("idRuolo", idRuolo, Types.INTEGER);
		}
		lista = query(sqlSelect.toString(), params, new PbandiTNotificaProcessoVORowMapper());
		
		logger.end();
		return lista;
	}

	private class PbandiTNotificaProcessoVORowMapper implements
		RowMapper<PbandiTNotificaProcessoVO> {

		public PbandiTNotificaProcessoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			PbandiTNotificaProcessoVO vo = new PbandiTNotificaProcessoVO();
			
			vo.setIdEntita(rs.getBigDecimal("ID_ENTITA"));
			vo.setIdNotifica(rs.getBigDecimal("ID_NOTIFICA"));
			vo.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
			vo.setIdRuoloDiProcessoDest(rs.getBigDecimal("ID_RUOLO_DI_PROCESSO_DEST"));
			vo.setIdTarget(rs.getBigDecimal("ID_TARGET"));
			vo.setIdTemplateNotifica(rs.getBigDecimal("ID_TEMPLATE_NOTIFICA"));
			vo.setIdUtenteAgg(rs.getBigDecimal("ID_UTENTE_AGG"));
			vo.setIdUtenteMitt(rs.getBigDecimal("ID_UTENTE_MITT"));
			vo.setMessageNotifica(rs.getString("MESSAGE_NOTIFICA"));
			vo.setStatoNotifica(rs.getString("STATO_NOTIFICA"));
			vo.setSubjectNotifica(rs.getString("SUBJECT_NOTIFICA"));
			
			//PK: faccio in questo modo altrimenti perdo le HH:mm:ss
			String stDataNotifica = rs.getString("DT_NOTIFICA");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
			try {
				if(stDataNotifica!=null){
					java.util.Date dtJv = sdf.parse(stDataNotifica);
					java.sql.Date dtsql = new java.sql.Date(dtJv.getTime());
					vo.setDtNotifica( dtsql);
				}
			} catch (ParseException e) {
				vo.setDtNotifica(null);
			}
				
			String stDataAggNotifica = rs.getString("DT_AGG_STATO_NOTIFICA");	
			try {
				if(stDataAggNotifica!=null){
					java.util.Date dtJv = sdf.parse(stDataAggNotifica);
					java.sql.Date dtsql = new java.sql.Date(dtJv.getTime());
					vo.setDtAggStatoNotifica( dtsql);
				}
			} catch (ParseException e) {
				vo.setDtAggStatoNotifica(null);
			}
			
			return vo;
		}
	}
	
	public Integer aggiornaStatoNotifiche(long[] elencoIdNotifiche, String stato) {
		logger.begin();
		
		logger.info("stato="+stato);
		Integer ris = 0;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuilder sqlSelect = new StringBuilder("");
		
		sqlSelect.append("UPDATE PBANDI_T_NOTIFICA_PROCESSO ");
		sqlSelect.append("  SET STATO_NOTIFICA = :stato , DT_AGG_STATO_NOTIFICA = sysdate");
		sqlSelect.append(" WHERE ID_NOTIFICA in (");
		
		int max = elencoIdNotifiche.length - 1;
		
		for (int i = 0; i < elencoIdNotifiche.length; i++) {
			sqlSelect.append(  elencoIdNotifiche[i] );
			
			if(i<max){
				sqlSelect.append( ", ");
			}
		}
		sqlSelect.append( ")");
		
		String q = sqlSelect.toString();
		logger.info("query="+q);
		
		params.addValue("stato", stato, Types.VARCHAR);
		
		ris = modificaConRitorno(q, params);
//		modificaSenzaTracciamento(q, params);
		
		logger.end();
		return ris;
	}


	public Boolean isProgettoAssociatoRegola(Long idProgetto, String codRegola) {
		logger.begin();
		
		boolean ris = false;
		Integer idRegola = null;
		
		logger.debug("idProgetto="+idProgetto);
		logger.debug("codRegola="+codRegola);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		params.addValue("codRegola", codRegola, Types.VARCHAR);
		
		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect.append("select r.ID_REGOLA ");
		sqlSelect.append("from pbandi_t_domanda d ,  pbandi_t_progetto p, PBANDI_R_REGOLA_BANDO_LINEA bl , pbandi_c_regola r ");
		sqlSelect.append("where p.ID_PROGETTO = :idProgetto ");
		sqlSelect.append("and r.DESC_BREVE_REGOLA = :codRegola ");
		sqlSelect.append("and r.DT_FINE_VALIDITA is null ");
		sqlSelect.append("and d.ID_DOMANDA = p.ID_DOMANDA ");
		sqlSelect.append("and bl.PROGR_BANDO_LINEA_INTERVENTO = d.PROGR_BANDO_LINEA_INTERVENTO ");
		sqlSelect.append("and bl.ID_REGOLA = r.id_regola ");
		
		idRegola = queryForInt(sqlSelect.toString(), params);
		logger.debug("idRegola="+idRegola);
		
		if(idRegola!=null){
			ris = true;
		}
		logger.end();
		return ris;
	}


	public List getListaIdModalitaAgevolazioni(Long idProgetto) {
		logger.begin();
		
		List<Long> lista = null;
		logger.debug("idProgetto="+idProgetto);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		
		StringBuilder sqlSelect = new StringBuilder("");
		
		sqlSelect.append("select id_modalita_agevolazione ");
		sqlSelect.append(" from PBANDI_T_CONTO_ECONOMICO ce, PBANDI_D_STATO_CONTO_ECONOMICO sce, PBANDI_R_CONTO_ECONOM_MOD_AGEV m ");
		sqlSelect.append("where ce.id_domanda = ( select p.id_domanda from pbandi_t_progetto p where p.ID_PROGETTO=:idProgetto) ");
		sqlSelect.append("and ce.DT_FINE_VALIDITA is null ");
		sqlSelect.append("and sce.ID_TIPOLOGIA_CONTO_ECONOMICO = 2 ");
		sqlSelect.append("and m.QUOTA_IMPORTO_AGEVOLATO > 0 ");
		sqlSelect.append("and ce.ID_STATO_CONTO_ECONOMICO = sce.ID_STATO_CONTO_ECONOMICO ");
		sqlSelect.append("and m.ID_CONTO_ECONOMICO = ce.ID_CONTO_ECONOMICO ");
		
		lista = query(sqlSelect.toString(), params, new IdModalitaAgevolazionirowMapper());
		
		logger.end();
		return lista;
	}
	
	
	private class IdModalitaAgevolazionirowMapper implements RowMapper<Long> {
		public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
			return (rs.getBigDecimal("id_modalita_agevolazione")).longValue();
		}
	}

	public String getCausaleErogazionePerContoEconomicoErogazione(
			Long idProgetto, Long idModalitaAgevolazione) {
		logger.begin();
		String ret = null;
		logger.debug("idProgetto="+idProgetto+", idModalitaAgevolazione="+idModalitaAgevolazione);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		params.addValue("idModalitaAgevolazione", idModalitaAgevolazione, Types.BIGINT);
		
		StringBuilder sqlSelect = new StringBuilder("");
		
		sqlSelect.append("select DESC_BREVE_CAUSALE ");
		sqlSelect.append("from PBANDI_T_EROGAZIONE te,  PBANDI_D_CAUSALE_EROGAZIONE ce ");
		sqlSelect.append("where te.ID_PROGETTO = :idProgetto ");
		sqlSelect.append("and te.ID_MODALITA_AGEVOLAZIONE = :idModalitaAgevolazione ");
		sqlSelect.append("and (te.ID_CAUSALE_EROGAZIONE = 4 OR te.ID_CAUSALE_EROGAZIONE = 7)");
		sqlSelect.append("and ce.ID_CAUSALE_EROGAZIONE = te.ID_CAUSALE_EROGAZIONE ");
		
		ret = queryForString(sqlSelect.toString(), params);
		
		logger.end();
		return ret;
	}


	public String getCausaleErogazionePerContoEconomicoLiquidazione(
			Long idProgetto, Long idModalitaAgevolazione) {

		logger.begin();
		String ret = null;
		logger.debug("idProgetto="+idProgetto+", idModalitaAgevolazione="+idModalitaAgevolazione);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		params.addValue("idModalitaAgevolazione", idModalitaAgevolazione, Types.BIGINT);
		
		StringBuilder sqlSelect = new StringBuilder("");
		
		sqlSelect.append("select DESC_BREVE_CAUSALE ");
		sqlSelect.append("from PBANDI_T_ATTO_LIQUIDAZIONE te,  PBANDI_D_CAUSALE_EROGAZIONE ce ");
		sqlSelect.append("where te.ID_PROGETTO = :idProgetto ");
		sqlSelect.append("and te.ID_MODALITA_AGEVOLAZIONE = :idModalitaAgevolazione ");
		sqlSelect.append("and te.DT_ANNULAMENTO_ATTO is null ");
		sqlSelect.append("and te.ID_STATO_ATTO=7 ");
		sqlSelect.append("and (te.ID_CAUSALE_EROGAZIONE = 4 OR te.ID_CAUSALE_EROGAZIONE = 7)");
		sqlSelect.append("and ce.ID_CAUSALE_EROGAZIONE = te.ID_CAUSALE_EROGAZIONE ");
		
		ret = queryForString(sqlSelect.toString(), params);
		
		logger.end();
		return ret;
	}


	public Boolean checkErogazioneSaldoProgettoContributo(Long idProgetto) {
		logger.begin();
		
		boolean ris = false;
		Integer idErogazione = null;
		
		logger.debug("idProgetto="+idProgetto);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		
		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect.append("SELECT  te.id_erogazione ");
		sqlSelect.append("from PBANDI_T_EROGAZIONE te,  PBANDI_D_CAUSALE_EROGAZIONE ce  ");
		sqlSelect.append("where te.ID_PROGETTO = ( ");
		sqlSelect.append("SELECT p.ID_PROGETTO FROM PBANDI_T_PROGETTO p WHERE  p.ID_PROGETTO_FINANZ = :idProgetto ) ");
		sqlSelect.append("and te.ID_MODALITA_AGEVOLAZIONE = 6 ");
		sqlSelect.append("and (te.ID_CAUSALE_EROGAZIONE = 4 OR te.ID_CAUSALE_EROGAZIONE = 7) ");
		sqlSelect.append("and ce.ID_CAUSALE_EROGAZIONE = te.ID_CAUSALE_EROGAZIONE  ");
		
		idErogazione = queryForInt(sqlSelect.toString(), params);
		logger.debug("idErogazione="+idErogazione);
		
		if(idErogazione!=null){
			ris = true;
		}
		logger.end();
		return ris;
	}


	public Long getIdProgettoAContributo(Long idProgettoAContributo) {
		logger.begin();
		Long idProgetto = null;
		
		logger.debug("idProgettoAContributo="+idProgettoAContributo);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgettoAContributo, Types.BIGINT);
		
		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect.append("SELECT p.ID_PROGETTO FROM PBANDI_T_PROGETTO p WHERE p.ID_PROGETTO_FINANZ = :idProgetto");
		
		idProgetto = queryForLong(sqlSelect.toString(), params);
		logger.debug("idProgetto="+idProgetto);
		
		logger.end();
		return idProgetto;
	}

}
