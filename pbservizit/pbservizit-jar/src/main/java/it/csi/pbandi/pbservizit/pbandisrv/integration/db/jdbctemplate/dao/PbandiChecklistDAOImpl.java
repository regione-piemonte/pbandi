/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiChecklistDAOImpl extends PbandiDAO {

	
	 public PbandiChecklistDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	class ClobMapper implements RowMapper<Clob> {

	        public Clob mapRow(ResultSet rs, int rowNum) throws SQLException {
	            return rs.getClob(1);
	        }
	}
	 
	 class BlobMapper implements RowMapper<Blob> {

	        public Blob mapRow(ResultSet rs, int rowNum) throws SQLException {
	            return rs.getBlob(1);
	        }
	}
	
		public String getChecklistHtmlModel(BigDecimal idModello) {
			 logger.info("getChecklistHtmlModel for idModello "+idModello); 
			 String res=null;
			 String query = "select HTML from PBANDI_C_CHECKLIST_MODEL_HTML where ID_MODELLO=:idModello";
			 try {
				 	MapSqlParameterSource map = new MapSqlParameterSource();
				 	map.addValue("idModello",idModello);
				 	Clob o = (Clob)this.getNamedJdbcTemplate().queryForObject(query, map,new ClobMapper());
		            res= o.getSubString(1, (int) o.length());
		            //logger.info("Clob for idModello:"+idModello+" -->\n"+res+"\n");
		        } catch (SQLException ex) {
		           logger.error(ex.getMessage(), ex);
		        }
			 
			return res;
		}
		
	 
	public String getChecklistHtmlCompiled(BigDecimal idChecklist) {
		 logger.info("getChecklistHtmlCompiled for idChecklist "+idChecklist); 
		 String res=null;
		 String query = "select HTML from PBANDI_T_CHECKLIST_HTML where ID_CHECKLIST=:idChecklist";
		 try {
			 	MapSqlParameterSource map = new MapSqlParameterSource();
			 	map.addValue("idChecklist",idChecklist);  
			 	Clob o = (Clob)this.getNamedJdbcTemplate().queryForObject(query, map,new ClobMapper());
	            res= o.getSubString(1, (int) o.length());
	        } catch (Exception ex) {
	        	 logger.error(ex.getMessage(), ex);
	     }
		return res;
	}
	

	
	public int insertChecklistCompiled(final String checklistHtml,final BigDecimal idChecklist){
		int update=-1;
		logger.info("insertChecklistCompiled for idChecklist "+idChecklist);
		
		logger.info(">> getLobHandler()");
		
		update = this.getNamedJdbcTemplate().getJdbcOperations().
			 update("insert into PBANDI_T_CHECKLIST_HTML (html,id_checklist) values(?,?)",
			 new PreparedStatementSetter(){	
				 public void setValues(PreparedStatement ps) throws SQLException {
				 			getLobHandler() .getLobCreator().setClobAsString(ps, 1, checklistHtml);
				 			ps.setLong(2, idChecklist.longValue());
			 	}
			 });
		
		//PK : sostituisco le righe precedenti con le successive per problemi sul LobHandler..
		
//		String sql = "insert into PBANDI_T_CHECKLIST_HTML (html,id_checklist) values(?,?)";
//		
//		Connection connection;
//		PreparedStatement ps = null;
//		try {
//			connection = (Connection) getDataSource().getConnection();
//			ps = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
//			ps.setLong(2, idChecklist.longValue());
//			Reader reader = new StringReader(checklistHtml);
//			ps.setClob(1, reader);
//			update = ps.executeUpdate();
//		
//		} catch (SQLException e) {
//			logger.error("SQLException error:"+e.getMessage());
//		}finally {
//			try {
//				if(ps!=null)
//					ps.close();
//			} catch (SQLException e) {
//				logger.error("SQLException nella chiusura del prepareStatement; error:"+e.getMessage());
//			}
//		}

		logger.info("insertChecklistCompiled record inserted:  "+update);
		  
		return update;
	}
	
	
	
	public int updateChecklistCompiled(final String checklistHtml,final BigDecimal idChecklist){
		int update=-1;
		 logger.info("updateChecklistCompiled for idChecklist "+idChecklist);
		 try{	
			   update = this.getNamedJdbcTemplate().getJdbcOperations().
					 update("update PBANDI_T_CHECKLIST_HTML set html=? where id_checklist=?",
					 new PreparedStatementSetter(){	
				 public void setValues(PreparedStatement ps) throws SQLException {
				 			getLobHandler() .getLobCreator().setClobAsString(ps, 1, checklistHtml);
				 			ps.setLong(2, idChecklist.longValue());
			 	}
			    });
			   logger.info("updateChecklistCompiled record modified: "+update);
		 } catch (Exception ex) {
			 logger.error(ex.getMessage(), ex);
		 }
		 return update;
	}


	public int deleteChecklistCompiled(BigDecimal idChecklist) {
		int delete=-1;
		 logger.info("\n\n\ndeleteChecklistCompiled for idChecklist "+idChecklist);
		 try{	
			 MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("idChecklist", idChecklist, Types.NUMERIC);
			 delete = this.getNamedJdbcTemplate().
					 update("delete from PBANDI_T_CHECKLIST_HTML  where id_checklist=:idChecklist",params);
			 logger.info("deleteChecklistCompiled record modified: "+delete);
		 } catch (Exception ex) {
			 logger.error(ex.getMessage(), ex);
		 }
		 return delete;
		
	}
	
	public byte[] getModelloJasperChecklist(BigDecimal idModello) throws Exception {
		 logger.info("getModelloJasperChecklist for idModello "+idModello); 
		 byte[] res=null;
		 String query = "SELECT frontespizio FROM pbandi_c_modello WHERE ID_MODELLO=:idModello";
		 try {
			 
			 	MapSqlParameterSource map = new MapSqlParameterSource();
			 	map.addValue("idModello",idModello);
			 	Blob o = (Blob)this.getNamedJdbcTemplate().queryForObject(query, map,new BlobMapper());
			 	if(o != null)
			 		res= IOUtils.toByteArray(o.getBinaryStream());

		 } catch (SQLException ex) {
	           logger.error(ex.getMessage(), ex);
	        }
		 
		return res;
	}


	public BigDecimal getIdLineaDiIntervento(Long idProgetto) {
		logger.info("getIdLineaDiIntervento , idProgetto ="+idProgetto); 
		BigDecimal b = null;
		String query = "select id_linea_di_intervento "
				+ "from PBANDI_D_LINEA_DI_INTERVENTO "
		+ "where id_linea_di_intervento_padre IS NULL  " //-- Linea di intervento radice (Normativa)
		+ "connect by prior id_linea_di_intervento_padre = id_linea_di_intervento "
		+ "start with id_linea_di_intervento =  "
        + " (select id_linea_di_intervento "
        + "   from PBANDI_R_BANDO_LINEA_INTERVENT a "
		+ "   join PBANDI_T_DOMANDA b on b.progr_bando_linea_intervento = a.progr_bando_linea_intervento "
		+ "   JOIN PBANDI_T_PROGETTO c on c.id_domanda = b.id_domanda "
        + "    where c.id_progetto = :idProgetto)";
		
		logger.info("query="+query);
		try {
			MapSqlParameterSource map = new MapSqlParameterSource();
		 	map.addValue("idProgetto",idProgetto);
		    b = this.getNamedJdbcTemplate().queryForObject(query, map, BigDecimal.class);
		    logger.info("trovato IdLineaDiIntervento = " + b);
		    
		} catch (Exception ex) {
	           logger.error(ex.getMessage(), ex);
	    }
		return b;
	}
	
	
}
