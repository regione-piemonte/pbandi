/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.base.PbwebcertJunitClassRunner;
import it.csi.pbandi.pbservwelfare.base.TestBaseService;

@Ignore
@RunWith(PbwebcertJunitClassRunner.class)
public class PbservwelfareTest extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(PbservwelfareTest.class);

	@Before()
	public void before() {
		LOG.info("[PbservwelfareTest::before]START");
	}

	@After
	public void after() {
		LOG.info("[PbservwelfareTest::after]START");
	}


	@Test
	public void salvaXmlTypeTest() throws Exception {
		String prf = "[PbservwelfareTest :: salvaXmlTypeTest] ";
		LOG.debug(prf + "START");

		try {
			// Creo il record sulla PBANDI_T_FOLDER
	        String sql = "SELECT max(ID_CARICAMENTO ) from PBANDI_W_CARICAMENTO_XML";
	        MapSqlParameterSource param = new MapSqlParameterSource();
	        Long idCaricamento = this.queryForLong(sql, param);
	        LOG.debug(prf + "idCaricamento="+idCaricamento);
	        
	        
	        File file = new File("C:\\eclipse\\eclipse202203r\\workspace\\pbservwelfare\\pbservwelfare-jar\\src\\test\\java\\it\\csi\\pbandi\\pbservwelfare\\domanda.xml");
//	        File file = new File("C:\\eclipse\\eclipse202203r\\workspace\\pbservwelfare\\pbservwelfare-jar\\src\\test\\java\\it\\csi\\pbandi\\pbservwelfare\\domanda_min2000.xml");
	        LOG.debug(prf + "file="+file);
	        
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        StringBuffer sb = new StringBuffer();
	        String st;
	        while ((st = br.readLine()) != null) {
 	            sb.append(st);
	        }
//	        LOG.debug(prf + "file="+sb.toString());
	        LOG.debug(prf + "file length="+sb.toString().length());


	        String sql1 = "INSERT INTO PBANDI.PBANDI_W_CARICAMENTO_XML "
	        		+ "(ID_CARICAMENTO, FILE_XML, NOME_FILE, FLAG_VALIDO, FLAG_CARICATO, NUMERO_DOMANDA, FONTE, DT_INSERIMENTO, XML_VALID) "
	        		+ "VALUES(:id, (XMLType(:filexml)), :nomeFile, null, 'NO', :numDom, 'BANDIND', sysdate, null)";
	        
	        MapSqlParameterSource param1 = new MapSqlParameterSource();
	        param1.addValue("id", 20000009L);
	        param1.addValue("nomeFile", "pokeLA.pdf");
	        param1.addValue("numDom", "PK1234");
	        param1.addValue("filexml", "<xml/>");
//	        
	        int ris = this.namedParameterJdbcTemplate.update(sql1, param1);
	        LOG.debug(prf + "insert ris="+ris);

//	        updateXML("<xml><tre>3</tre></xml>", 20000009L);
	        int u = updateXML(sb.toString(), 20000009L);
			LOG.debug(prf + "PBANDI_W_CARICAMENTO_XML pdated row=" + u);
			
	        
		}catch(Exception e) {
			LOG.error(prf + "err="+e.getMessage());
			e.printStackTrace();
		}

		
		LOG.debug(prf + "END");
	}

	public int updateXML(final String str,final Long id) throws Exception {
		int update=-1;
		 logger.info("updateChecklistCompiled for idChecklist "+id);

			   update = this.namedParameterJdbcTemplate.getJdbcOperations().update("update PBANDI_W_CARICAMENTO_XML set FILE_XML=(XMLType(?)) where ID_CARICAMENTO=?",
					 new PreparedStatementSetter(){	
				 public void setValues(PreparedStatement ps) throws SQLException {
				 			getLobHandler() .getLobCreator().setClobAsString(ps, 1, str);
				 			ps.setLong(2, id);
			 	}
			    });
			   logger.info("updateChecklistCompiled record modified: "+update);
		
		 return update;
	}
	
  }
