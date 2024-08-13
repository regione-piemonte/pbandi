/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

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
	
	// Creato perchè farsi restituire il Clob e poi leggerlo dava l'errore "Coneesione chiusa".
	// Così faccio restituire la stringa con l'html direttamente dal mapper.
	class ClobMapperAlex implements RowMapper<String> {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Clob o = rs.getClob(1);
            return o.getSubString(1, (int) o.length());
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
			 	
			 	//Clob o = (Clob)this.getNamedJdbcTemplate().queryForObject(query, map,new ClobMapper());
	            //res= o.getSubString(1, (int) o.length());
			 	
			 	String html = this.getNamedJdbcTemplate().queryForObject(query, map,new ClobMapperAlex());
			 	res = html;
			 	
			 	/*
			 	if (html == null)
			 		logger.info("getChecklistHtmlCompiled(): html == null");
			 	else {
			 		logger.info("getChecklistHtmlCompiled(): html length = "+html.length());
			 		logger.info("\n"+html+"\n");
			 	}
			 	*/
			 				 	
	        } catch (Exception ex) {
	        	 logger.error(ex.getMessage(), ex);
	     }
		return res;
	}
	

	
	public int insertChecklistCompiled(final String checklistHtml,final BigDecimal idChecklist){
		int update=-1;
		logger.info("insertChecklistCompiled for idChecklist "+idChecklist);
		
			   update = this.getNamedJdbcTemplate().getJdbcOperations().
					 update("insert into PBANDI_T_CHECKLIST_HTML (html,id_checklist) values(?,?)",
					 new PreparedStatementSetter(){	
				 public void setValues(PreparedStatement ps) throws SQLException {
				 			getLobHandler() .getLobCreator().setClobAsString(ps, 1, checklistHtml);
				 			ps.setLong(2, idChecklist.longValue());
			 	}
			    });
		
		
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
		update = this.getNamedJdbcTemplate().getJdbcOperations().
			 update("update PBANDI_T_CHECKLIST_HTML set html=? where id_checklist=?",
			 new PreparedStatementSetter(){	
				 public void setValues(PreparedStatement ps) throws SQLException {
			 			getLobHandler() .getLobCreator().setClobAsString(ps, 1, checklistHtml);
			 			ps.setLong(2, idChecklist.longValue());
		 		}
			 });
			 
		//PK : sostituisco le righe precedenti con le successive per problemi sul LobHandler..
//		String sql = "update PBANDI_T_CHECKLIST_HTML set html = ? WHERE id_checklist =?";
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
//					logger.error("SQLException nella chiusura del prepareStatement; error:"+e.getMessage());
//			}
//				
//		}
			 
		logger.info("updateChecklistCompiled record modified: "+update);
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
	@Deprecated
	public byte[] getModelloJasperChecklist(BigDecimal idModello) throws Exception {
		
		//PK : da sostituire con la getModelloTemplatePDFJasperChecklist
		
		 logger.info("DEPRECATA .... getModelloJasperChecklist for idModello "+idModello); 
		 
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
	
	public byte[] getModelloTemplatePDFJasperChecklist(BigDecimal idModello) throws Exception {
		 logger.info(" idModello "+idModello); 
		 byte[] res=null;
		 String query = "SELECT template_pdf FROM pbandi_c_modello WHERE ID_MODELLO=:idModello";
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
	
	
}
