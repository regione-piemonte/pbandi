/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;


import java.security.InvalidParameterException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoAbbattimentoGaranzieDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.AbbattimentoGaranzieDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AbbattimentoGaranzieRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AttivitaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoAbbattimentoGaranzieRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.AbbattimentoGaranzieVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

public class AbbattimentoGaranzieDAOImpl  extends JdbcDaoSupport implements AbbattimentoGaranzieDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public AbbattimentoGaranzieDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public Boolean insertAbbattimento(AbbattimentoGaranzieVO abbattimento, Long idUtente, Long idProgetto,  int idModalitaAgev) {
		
		String prf = "[AbbattimentoGaranzieDAOImpl::insertAbbattimentoGaranzie ]";
		LOG.info(prf + "BEGIN");
		Boolean result = true; 
		
		if(abbattimento ==null) {
			throw new InvalidParameterException("abbattimento non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		try {
			String sqlSeq = "SELECT seq_PBANDI_T_ABBATTIM_GARANZIE.nextval FROM dual";									
			long idAbbattimento = (long) getJdbcTemplate().queryForObject(sqlSeq.toString(), long.class);
			
			AbbattimentoGaranzieVO abbattimentoDaSalvare = this.popolaAbbattim(abbattimento, idUtente, idProgetto, idAbbattimento);
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			String dataAbba = (abbattimentoDaSalvare.getDataAbbattimGaranzie()!=null)?  dt.format(abbattimentoDaSalvare.getDataAbbattimGaranzie()): null; 
			
			String sql="INSERT INTO PBANDI_T_ABBATTIM_GARANZIE ("
					+ "ID_ABBATTIM_GARANZIE,"
					+ "ID_PROGETTO,"
					+ "ID_ATTIVITA_GARANZIE,"
					+ "DT_ABBATTIM_GARANZIE,"
					+ "IMP_INIZIALE,"
					+ "IMP_LIBERATO,"
					+ "IMP_NUOVO,"
					+ "NOTE,"
					+ "DT_INIZIO_VALIDITA,"
					+ "ID_UTENTE_INS,"
					+ "DT_INSERIMENTO, ID_MODALITA_AGEVOLAZIONE)"
					+ "VALUES(?,?,?,?,?,?,?,?,sysdate,?,sysdate, ? )";
			
			getJdbcTemplate().update(sql, new Object[] {
					abbattimentoDaSalvare.getIdAbbattimGaranzie(),
					abbattimentoDaSalvare.getIdProgetto(), 
					abbattimentoDaSalvare.getIdAttivitaGaranzie(),
					(dataAbba!=null && dataAbba.trim().length()>0)?Date.valueOf(dataAbba): null,
					abbattimentoDaSalvare.getImpIniziale(),
					abbattimentoDaSalvare.getImpLiberato(),
					abbattimentoDaSalvare.getImpNuovo(),
					abbattimentoDaSalvare.getNote(),
					abbattimentoDaSalvare.getIdUtenteIns()	, 
					idModalitaAgev
			});
			LOG.debug(prf + "  param [IdAbbattimGaranzie] = " + abbattimentoDaSalvare.getIdAbbattimGaranzie());
			LOG.debug(prf + "  param [IdProgetto] = " + abbattimentoDaSalvare.getIdProgetto());
			LOG.debug(prf + "  param [IdAttivitaGaranzie] = " + abbattimento.getIdAttivitaGaranzie());
			LOG.debug(prf + "  param [IdAbbattimGaranzie] = " + abbattimentoDaSalvare.getDataAbbattimGaranzie());
			LOG.debug(prf + "  param [ImpIniziale] = " + abbattimentoDaSalvare.getImpIniziale());
			LOG.debug(prf + "  param [ImpLiberato] = " + abbattimentoDaSalvare.getImpLiberato());
			LOG.debug(prf + "  param [ImpNuovo] = " + abbattimentoDaSalvare.getImpNuovo());
			LOG.debug(prf + "  param [Note] = " + abbattimentoDaSalvare.getNote());
			LOG.debug(prf + "  param [IdUtenteIns] = " + abbattimentoDaSalvare.getIdUtenteIns());
			
		}
		catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert PBANDI_T_ABBATTIM_GARANZIE", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}

	private AbbattimentoGaranzieVO popolaAbbattim(AbbattimentoGaranzieVO abbattimento, Long idUtente, Long idProgetto,
			long idAbbattimento) {
		
		AbbattimentoGaranzieVO abbattimentoDaSalvare = new AbbattimentoGaranzieVO(); 
		abbattimentoDaSalvare.setIdAbbattimGaranzie(idAbbattimento);
		abbattimentoDaSalvare.setDataAbbattimGaranzie(abbattimento.getDataAbbattimGaranzie());
		abbattimentoDaSalvare.setNote(abbattimento.getNote());
		abbattimentoDaSalvare.setIdAttivitaGaranzie(abbattimento.getIdAttivitaGaranzie());
		abbattimentoDaSalvare.setIdUtenteIns(idUtente);
		abbattimentoDaSalvare.setImpIniziale(abbattimento.getImpIniziale());
		abbattimentoDaSalvare.setImpLiberato(abbattimento.getImpLiberato());
		abbattimentoDaSalvare.setImpNuovo(abbattimento.getImpNuovo());
		abbattimentoDaSalvare.setIdProgetto(idProgetto);
		
		return abbattimentoDaSalvare;
	}

	@Override
	public Boolean modificaAbbattimento(AbbattimentoGaranzieVO abbattimento, Long idUtente, Long idProgetto,Long idAbbattimentoGraranzie, int idModalitaAgev) {
		
		String prf = "[AbbattimentoGaranzieDAOImpl::modificaAbbattimentoGaranzie ]";
		LOG.info(prf + "BEGIN");
		Boolean result = true; 
		
		if(abbattimento ==null) {
			throw new InvalidParameterException("abbattimento non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		try {								
			
			String sqlmodifica = "UPDATE PBANDI_T_ABBATTIM_GARANZIE "
					+ " SET DT_FINE_VALIDITA = sysdate,"
					+ " DT_AGGIORNAMENTO = sysdate,"
					+ " ID_UTENTE_AGG = " + idUtente
					+ " WHERE ID_ABBATTIM_GARANZIE = " + idAbbattimentoGraranzie;
			
			
			getJdbcTemplate().update(sqlmodifica); 
			
			result = insertAbbattimento(abbattimento, idUtente, idProgetto, idModalitaAgev); 
			
			
		} catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_ABBATTIM_GARANZIE", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
			
		return result;
	}
	

	@Override
	public AbbattimentoGaranzieVO getAbbatimento(Long idUtente, Long idProgetto, Long idAbbattimentoGraranzie, int idModalitaAgev) {
		
		String prf = "[AbbattimentoGaranzieDAOImpl::getAbbattimentoGaranzie ]";
		LOG.info(prf + "BEGIN");
		
		if(idAbbattimentoGraranzie == null ) {
			throw new InvalidParameterException("idAbbattimento non valorizzato.");
		}
		
		AbbattimentoGaranzieVO abbattimentoDB = new AbbattimentoGaranzieVO(); 
		
		try {
			
			String sql = "SELECT * FROM PBANDI_T_ABBATTIM_GARANZIE "
					+ " WHERE ID_ABBATTIM_GARANZIE = " + idAbbattimentoGraranzie +"\r\n"
					 + "and ID_MODALITA_AGEVOLAZIONE= "+idModalitaAgev;
			
			abbattimentoDB = (AbbattimentoGaranzieVO) getJdbcTemplate().queryForObject(sql, new AbbattimentoGaranzieRowMapper());
			
		} catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_ABBATTIM_GARANZIE", e);
			
		}finally {
			LOG.info(prf + " END");
		}
		
		return abbattimentoDB;
	}

	@Override
	public ArrayList<AttivitaDTO> getListaAttivitaAbbattimento() {
		String prf = "[AbbattimentoGaranzieDAOImpl::getListaAttivitaAbbattimento ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "SELECT DISTINCT DESC_ATT_MONIT_CRED, ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED pdamc \r\n"
					+ "WHERE PDAMC .ID_TIPO_MONIT_CRED = 2 \r\n"
					+ "ORDER BY ID_ATTIVITA_MONIT_CRED \r\n";
			
			listaAttivita = (ArrayList<AttivitaDTO>) getJdbcTemplate().query(sql, new AttivitaRowMapper()); 
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_ATTIVITA_MONIT_CRED", e);
		}finally {
			LOG.info(prf + " END");
	}
		
		return listaAttivita;
	}

	@Override
	public ArrayList<StoricoAbbattimentoGaranzieDTO> storicoAbbattimenti(Long idUtente, Long idProgetto, int idModalitaAgev) {
		
		
		String prf = "[AbattimentoGaranzieDAOImpl::getStoricoAbbattimento]";
		LOG.info(prf + "BEGIN"); 
		
		
		ArrayList<StoricoAbbattimentoGaranzieDTO> storico = new ArrayList<StoricoAbbattimentoGaranzieDTO>();
		try {
			String sql = "SELECT ptpf.NOME,\r\n"
					+ "			 ptpf.COGNOME, \r\n"
					+ "		     ptag.DT_INSERIMENTO, \r\n"
					+ "			 ptag .DT_ABBATTIM_GARANZIE ,\r\n"
					+ "			 pdamc.DESC_ATT_MONIT_CRED,\r\n"
					+ "			 ptag .IMP_INIZIALE,\r\n"
					+ "			 ptag .IMP_LIBERATO,\r\n"
					+ "			 ptag .IMP_NUOVO,\r\n"
					+ "			 ptag .NOTE, \r\n"
					+ "			 ptag .ID_ABBATTIM_GARANZIE,  \r\n"
					+ "			 ptag .DT_AGGIORNAMENTO \r\n"
					+ " FROM PBANDI_T_ABBATTIM_GARANZIE ptag\r\n"
					+ " 	LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptag.ID_UTENTE_INS\r\n"
					+ "	LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    ),\r\n"
					+ "    PBANDI_D_ATTIVITA_MONIT_CRED pdamc\r\n"
					+ " WHERE ptag.ID_PROGETTO = ?\r\n"
					+ " and ptag.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ " AND pdamc.ID_ATTIVITA_MONIT_CRED = ptag.ID_ATTIVITA_GARANZIE\r\n"
					+ " AND ptag .DT_FINE_VALIDITA IS not null\r\n"
					+ " ORDER BY ptag .ID_ABBATTIM_GARANZIE DESC  ";
			
			storico = (ArrayList<StoricoAbbattimentoGaranzieDTO>) getJdbcTemplate().query(sql, new StoricoAbbattimentoGaranzieRowMapper(), new Object [] {
					idProgetto, idModalitaAgev	}); 
			LOG.info(prf +  " param [IdProgetto] = " + idProgetto);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read tables ", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;
	}

	@Override
	public ArrayList<StoricoAbbattimentoGaranzieDTO> storicoAbbattimentoGaranzie(Long idUtente, Long idProgetto, int idModalitaAgev) {

		String prf = "[AbattimentoGaranzieDAOImpl::storicoAbbattimentoGaranzie]";
		LOG.info(prf + "BEGIN"); 
		
		
		ArrayList<StoricoAbbattimentoGaranzieDTO> storico = new ArrayList<StoricoAbbattimentoGaranzieDTO>();
		try {
			String sql = "SELECT ptpf.NOME,\r\n"
					+ "			 ptpf.COGNOME, \r\n"
					+ "		     ptag.DT_INSERIMENTO, \r\n"
					+ "			 ptag .DT_ABBATTIM_GARANZIE ,\r\n"
					+ "			 pdamc.DESC_ATT_MONIT_CRED,\r\n"
					+ "			 ptag .IMP_INIZIALE,\r\n"
					+ "			 ptag .IMP_LIBERATO,\r\n"
					+ "			 ptag .IMP_NUOVO,\r\n"
					+ "			 ptag .NOTE, \r\n"
					+ "			 ptag .ID_ABBATTIM_GARANZIE,  \r\n"
					+ "			 ptag .DT_AGGIORNAMENTO \r\n"
					+ " FROM PBANDI_T_ABBATTIM_GARANZIE ptag\r\n"
					+ " 	LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptag.ID_UTENTE_INS\r\n"
					+ "	LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    ),\r\n"
					+ "    PBANDI_D_ATTIVITA_MONIT_CRED pdamc\r\n"
					+ " WHERE ptag.ID_PROGETTO = ?\r\n"
					+ " and ptag.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ " AND pdamc.ID_ATTIVITA_MONIT_CRED = ptag.ID_ATTIVITA_GARANZIE\r\n"
					+ " AND ptag .DT_FINE_VALIDITA IS null\r\n"
					+ " ORDER BY ptag .ID_ABBATTIM_GARANZIE DESC  ";
			
			storico = (ArrayList<StoricoAbbattimentoGaranzieDTO>) getJdbcTemplate().query(sql, new StoricoAbbattimentoGaranzieRowMapper(), new Object [] {
					idProgetto, idModalitaAgev	}); 
			
			LOG.info(prf +  " param [IdProgetto] = " + idProgetto);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read tables ", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;

	}

}
