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
import javax.ws.rs.ServerErrorException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.filestorage.util.Constants;
import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoMessaMoraDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.MessaMoraDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AttivitaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.MessaMoraVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoMessaMoraDTORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.MessaMoraVO;

public class MessaMoraDAOImpl extends JdbcDaoSupport implements MessaMoraDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME); 
	
	@Autowired
	public MessaMoraDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	

	@Override
	public Boolean insertMessaMora(MessaMoraVO messaMora, int idModalitaAgev) {
		String prf = "[MessaMoraDAOImpl::insertMessaMora]";
		LOG.info(prf + "BEGIN");
		
		Boolean result = true; 
		
		if(messaMora== null) {
			throw new InvalidParameterException(" Messa in mora  non valorizzata.");
		}
		
		try {
			
			String sqlSeq = "SELECT seq_PBANDI_T_MESSA_IN_MORA.nextval FROM dual";
			Long idMessaMora = getJdbcTemplate().queryForObject(sqlSeq, long.class); 
			
			String dataMesMor = null, dataNotif = null,dataPagam = null; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd"); 
			
			if(messaMora.getDataMessaMora()!=null) {
				 dataMesMor = dt.format(messaMora.getDataMessaMora());
			} 
			
			if(messaMora.getDataNotifica()!=null) {
				dataNotif=  dt1.format(messaMora.getDataNotifica());
			}
			
			
			if(messaMora.getDataPagamento()!=null) {
				 dataPagam = dt2.format(messaMora.getDataPagamento());
			}
			
			 		
			
			String sql = "INSERT INTO PBANDI_T_MESSA_IN_MORA ("
					+ "ID_MESSA_IN_MORA,\r\n"
					+ "ID_PROGETTO,\r\n"
					+ "ID_ATTIVITA_MORA,\r\n"
					+ "IMP_MESSA_IN_MORA_COMPLESSIVA,\r\n"
					+ "IMP_QUOTA_CAPITALE_REVOCA,\r\n"
					+ "IMP_AGEVOLAZ_REVOCA,\r\n"
					+ "IMP_CREDITO_RESIDUO,\r\n"
					+ "IMP_INTERESSI_MORA,\r\n"
					+ "ID_ATTIVITA_RECUPERO_MORA,\r\n"
					+ "DT_MESSA_IN_MORA,\r\n"
					+ "DT_NOTIFICA,\r\n"
					+ "DT_PAGAMENTO,\r\n"
					+ "NOTE,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INSERIMENTO,\r\n"
					+ "ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "NUM_PROTOCOLLO \r\n"
					+ " ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?, ?)"; 
			
			getJdbcTemplate().update(sql, new Object[] {
				idMessaMora,
				messaMora.getIdProgetto(),
				messaMora.getIdAttivitaMora(),
				messaMora.getImpMessaMoraComplessiva(),
				messaMora.getImpQuotaCapitaleRevoca(),
				messaMora.getImpAgevolazRevoca(), 
				messaMora.getImpCreditoResiduo(),
				messaMora.getImpInteressiMora(),
				messaMora.getIdAttivitaRecuperoMora(),
				(dataMesMor !=null && dataMesMor.trim().length()>0) ? Date.valueOf(dataMesMor):null,
				(dataNotif !=null && dataNotif.trim().length()>0) ? Date.valueOf(dataNotif):null,
				(dataPagam !=null && dataPagam.trim().length()>0) ? Date.valueOf(dataPagam):null,
				messaMora.getNote(),
				messaMora.getIdUtenteIns(), idModalitaAgev, messaMora.getNumeroProtocollo()
				
			});
			
			LOG.debug(prf + "  param [idMessaMora] = " + idMessaMora );
		    LOG.debug(prf + "  param [IdProgetto] = " + messaMora.getIdProgetto());
		    LOG.debug(prf + "  param [IdAttivitaMora] = " + messaMora.getIdAttivitaMora());
		    LOG.debug(prf + "  param [DataMessaMora] = " + messaMora.getDataMessaMora());
		    LOG.debug(prf + "  param [ImpMessaMoraComplessiva] = " + messaMora.getImpMessaMoraComplessiva());
		    LOG.debug(prf + "  param [ImpQuotaCapitaleRevoca] = " + messaMora.getImpQuotaCapitaleRevoca());
		    LOG.debug(prf + "  param [ImpAgevolazRevoca] = " + messaMora.getImpAgevolazRevoca());
		    LOG.debug(prf + "  param [ImpCreditoResiduo] = " + messaMora.getImpCreditoResiduo());
		    LOG.debug(prf + "  param [tImpInteressiMora = " + messaMora.getImpInteressiMora());
		    LOG.debug(prf + "  param [DataNotifica] = " + messaMora.getDataNotifica());
		    LOG.debug(prf + "  param [IdAttivitaRecuperoMora] = " + messaMora.getIdAttivitaRecuperoMora());
		    LOG.debug(prf + "  param [DataPagamento] = " + messaMora.getDataPagamento());
		    LOG.debug(prf + "  param [numeroProtocollo] = " + messaMora.getNumeroProtocollo());
		    LOG.debug(prf + "  param [Note] = " + messaMora.getNote());
		    LOG.debug(prf + "  param [IdUtenteIns] = " + messaMora.getIdUtenteIns());
			
		} catch(Exception e) {
			LOG.error(prf + "Exception while trying to update PBANDI_T_MESSA_IN_MORA", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}

	@Override
	public Boolean modificaMessaMora(MessaMoraVO messaMora, Long idMessaMora, int idModalitaAgev) {
		String prf = "[MessaMoraDAOImpl::insertMessaMora]";
		LOG.info(prf + "BEGIN");
		
		Boolean result = true; 
		
		if(idMessaMora == 0) {
			throw new InvalidParameterException(" idMessaMora  non valorizzato.");
		} 
		if(messaMora == null ) {
			throw new InvalidParameterException(" Messa in mora  non valorizzata.");
		}
		if(messaMora.getIdAttivitaMora()==0) {
			messaMora.setIdAttivitaMora(null);
		}
		if(messaMora.getIdAttivitaRecuperoMora()==0) {
			messaMora.setIdAttivitaRecuperoMora(null);
		}
		//long idARecup; 
		//idARecup = (messaMora.getIdAttivitaRecuperoMora() != 0) ? messaMora.getIdAttivitaRecuperoMora() : null ; 
			try {
			
			String sql = "UPDATE PBANDI_T_MESSA_IN_MORA "
					+ "	  SET DT_FINE_VALIDITA = SYSDATE, "
					+ "   DT_AGGIORNAMENTO = SYSDATE,"
					+ "	  ID_UTENTE_AGG = "+ messaMora.getIdUtenteIns()
					+ "    WHERE ID_MESSA_IN_MORA = " + idMessaMora; 
					
			getJdbcTemplate().update(sql); 
			result  = insertMessaMora(messaMora, idModalitaAgev); 
			
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to update PBANDI_T_MESSA_IN_MORA", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		
		
		return result;
	}

	@Override
	public MessaMoraVO getMessaMora(Long idMessaMora, int idModalitaAgev) {
		
		String prf = "[MessaMoraDAOImpl::getMessaMora ]";
		LOG.info(prf + "BEGIN");
		
		
		if(idMessaMora == 0 ) {
			throw new InvalidParameterException("idMessaMora non valorizzato.");
			
		}
		
		MessaMoraVO messaMoraDB = new MessaMoraVO();
		try {
			String sql = "SELECT * FROM PBANDI_T_MESSA_IN_MORA"
					+ " WHERE ID_MESSA_IN_MORA = " + idMessaMora + "\r\n"
					+ " and ID_MODALITA_AGEVOLAZIONE= "+ idModalitaAgev; 
			
			messaMoraDB = getJdbcTemplate().queryForObject(sql, new MessaMoraVORowMapper()); 
			
		} catch (ServerErrorException e) {
			LOG.error(prf+ "Exception while trying to read PBANDI_T_MESSA_IN_MORA");
		}finally {
			LOG.info(prf + " END");
		}	
		return messaMoraDB;
	}

	@Override
	public ArrayList<AttivitaDTO> getAttivitaMessaMora() {
		
		String prf = "[MessaMoraDAOImpl::getAttivitaMessaMora ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "SELECT DISTINCT DESC_ATT_MONIT_CRED, ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED pdamc \r\n"
					+ "WHERE PDAMC .ID_TIPO_MONIT_CRED = 7 \r\n"
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
	public ArrayList<AttivitaDTO> getAttivitaRecupero() {
		
		String prf = "[MessaMoraDAOImpl::getAttivitaRecupero ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "SELECT DISTINCT DESC_ATT_MONIT_CRED, ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED pdamc \r\n"
					+ "WHERE PDAMC .ID_TIPO_MONIT_CRED = 8 \r\n"
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
	public ArrayList<StoricoMessaMoraDTO> getStoricoMessaMora(Long idProgetto, int idModalitaAgev) {
		
		String prf = "[MessaMoraDAOImpl::getStoricoMessaMora ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList<StoricoMessaMoraDTO> storico =  new ArrayList<StoricoMessaMoraDTO>();
		
		try {
			
			String sql ="SELECT\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    messamora.descMessaMora,\r\n"
					+ "    recupero.descRecupero,\r\n"
					+ "    ptmin.DT_INSERIMENTO,\r\n"
					+ "    ptmin.DT_PAGAMENTO,\r\n"
					+ "    ptmin.DT_MESSA_IN_MORA,\r\n"
					+ "    ptmin.DT_NOTIFICA,\r\n"
					+ "    ptmin.IMP_MESSA_IN_MORA_COMPLESSIVA,\r\n"
					+ "    ptmin.IMP_QUOTA_CAPITALE_REVOCA,\r\n"
					+ "    ptmin.IMP_AGEVOLAZ_REVOCA,\r\n"
					+ "    ptmin.IMP_CREDITO_RESIDUO,\r\n"
					+ "    ptmin.IMP_INTERESSI_MORA,\r\n"
					+ "    ptmin.NOTE,\r\n"
					+ "    ptmin.ID_MESSA_IN_MORA,\r\n"
					+ "    ptmin.NUM_PROTOCOLLO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_MESSA_IN_MORA ptmin\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descMessaMora\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED pdamc\r\n"
					+ "    ) messamora ON messamora.ID_ATTIVITA_MONIT_CRED = ptmin.ID_ATTIVITA_MORA\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descRecupero\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED pdamc2\r\n"
					+ "    ) recupero ON recupero.ID_ATTIVITA_MONIT_CRED = PTMIN.ID_ATTIVITA_RECUPERO_MORA\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptmin.ID_UTENTE_INS\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "WHERE\r\n"
					+ "    ptmin.ID_PROGETTO = ?\r\n"
					+ "    and ptmin.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND ptmin.DT_FINE_VALIDITA IS null\r\n"
					+ "ORDER BY\r\n"
					+ "    ptmin.ID_MESSA_IN_MORA DESC";
			
			storico = (ArrayList<StoricoMessaMoraDTO>) getJdbcTemplate().query(sql, new StoricoMessaMoraDTORowMapper(), new Object[] {
					idProgetto, idModalitaAgev
			});
			
			
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_MESSA_IN_MORA", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;
	}

	@Override
	public ArrayList<StoricoMessaMoraDTO> getStorico(Long IdProgetto,  int idModalitaAgev) {
		
		String prf = "[MessaMoraDAOImpl::getStorico ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList<StoricoMessaMoraDTO> storico =  new ArrayList<StoricoMessaMoraDTO>();
		
		try {
			
			String sql ="SELECT\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    messamora.descMessaMora,\r\n"
					+ "    recupero.descRecupero,\r\n"
					+ "    ptmin.DT_INSERIMENTO,\r\n"
					+ "    ptmin.DT_PAGAMENTO,\r\n"
					+ "    ptmin.DT_MESSA_IN_MORA,\r\n"
					+ "    ptmin.DT_NOTIFICA,\r\n"
					+ "    ptmin.IMP_MESSA_IN_MORA_COMPLESSIVA,\r\n"
					+ "    ptmin.IMP_QUOTA_CAPITALE_REVOCA,\r\n"
					+ "    ptmin.IMP_AGEVOLAZ_REVOCA,\r\n"
					+ "    ptmin.IMP_CREDITO_RESIDUO,\r\n"
					+ "    ptmin.IMP_INTERESSI_MORA,\r\n"
					+ "    ptmin.NOTE,\r\n"
					+ "    ptmin.ID_MESSA_IN_MORA,\r\n"
					+ "    ptmin.NUM_PROTOCOLLO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_MESSA_IN_MORA ptmin\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descMessaMora\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED pdamc\r\n"
					+ "    ) messamora ON messamora.ID_ATTIVITA_MONIT_CRED = ptmin.ID_ATTIVITA_MORA\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descRecupero\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED pdamc2\r\n"
					+ "    ) recupero ON recupero.ID_ATTIVITA_MONIT_CRED = PTMIN.ID_ATTIVITA_RECUPERO_MORA\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptmin.ID_UTENTE_INS\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "WHERE\r\n"
					+ "    ptmin.ID_PROGETTO = ?\r\n"
					+ "    and ptmin.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "  AND ptmin.DT_FINE_VALIDITA IS not null \r\n"
					+ "ORDER BY\r\n"
					+ "    ptmin.ID_MESSA_IN_MORA DESC";
			
			storico = (ArrayList<StoricoMessaMoraDTO>) getJdbcTemplate().query(sql, new StoricoMessaMoraDTORowMapper(), new Object[] {
					IdProgetto, idModalitaAgev
			});
			
			
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_MESSA_IN_MORA", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;
	}

}
