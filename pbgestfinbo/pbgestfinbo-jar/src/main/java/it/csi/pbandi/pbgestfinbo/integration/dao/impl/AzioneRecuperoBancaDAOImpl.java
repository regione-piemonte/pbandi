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
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoAzioneRecuperoBancaDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.AzioneRecuperoBancaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AttivitaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AzioneRecuperoBancaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoAzioneRecuperoBancaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.AzioneRecuperoBancaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

public class AzioneRecuperoBancaDAOImpl  extends JdbcDaoSupport implements AzioneRecuperoBancaDAO {
	
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public AzioneRecuperoBancaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public Boolean insertAzioneRecuperoBanca(AzioneRecuperoBancaVO azioneRecupVO, Long idUtente, Long idProgetto,int idModalitaAgev) {
		
		String prf = "[AzioneRecuperoBancaDAOImpl::insertAzioneRecupero ]";
		LOG.info(prf + "BEGIN");
			
		
		if (azioneRecupVO == null) {
			throw new InvalidParameterException("azione non valorizzata.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		Boolean result = true;
		try {
			String sqlSeq = "SELECT seq_PBANDI_T_AZION_RECUP_BANCA.nextval FROM dual";
			long idAzioneRecup = (long) getJdbcTemplate().queryForObject(sqlSeq.toString(), long.class);
			
			SimpleDateFormat dt =  new SimpleDateFormat("yyyy-MM-dd"); 			
			
			AzioneRecuperoBancaVO azioneDaSalvare =  this.popolaAzione(azioneRecupVO, idAzioneRecup,idProgetto, idUtente);
		
			String dataAzione = (azioneDaSalvare.getDataAzione()!=null)?dt.format(azioneDaSalvare.getDataAzione()): null;
			
			
			String sql4 = "INSERT INTO PBANDI_T_AZIONE_RECUP_BANCA ("
					+ "ID_AZIONE_RECUPERO_BANCA,\r\n"
					+ "ID_PROGETTO,\r\n"
					+ "ID_ATTIVITA_AZIONE,\r\n"
					+ "DT_AZIONE,\r\n"
					+ "NUM_PROTOCOLLO,\r\n"
					+ "NOTE,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "DT_INSERIMENTO,\r\n"
					+ "ID_UTENTE_INS,  ID_MODALITA_AGEVOLAZIONE\r\n"
					+ ")"
					+ "VALUES(?,?,?,?,?,?,sysdate,sysdate,?, ?)";
			getJdbcTemplate().update(sql4, new Object[] {
					azioneDaSalvare.getIdAzioneRecupero(),
					azioneDaSalvare.getIdProgetto(),
					azioneDaSalvare.getIdAttivitaAzione(),
					(dataAzione!=null&&dataAzione.trim().length()>0)?Date.valueOf(dataAzione): null, 
					azioneDaSalvare.getNumProtocollo(),
					azioneDaSalvare.getNote(),
					idUtente, 
					idModalitaAgev
					});
			LOG.debug(prf + "  param [IdAzioneRecupero] = " + azioneDaSalvare.getIdAzioneRecupero());
			LOG.debug(prf + "  param [IdProgetto] = " + azioneDaSalvare.getIdProgetto());
			LOG.debug(prf + "  param [IdAttivitaAzione] = " + azioneDaSalvare.getIdAttivitaAzione());
			LOG.debug(prf + "  param [DataAzione] = " + azioneDaSalvare.getDataAzione());
			LOG.debug(prf + "  param [NumProtocollo] = " + azioneDaSalvare.getNumProtocollo());
			LOG.debug(prf + "  param [Note] = " + azioneDaSalvare.getNote());
			LOG.debug(prf + "  param [IdUtenteIns] = " + azioneDaSalvare.getIdUtenteIns());
			LOG.debug(prf + "  param [IdUtenteAgg] = " + azioneDaSalvare.getIdUtenteIns());
			LOG.debug(prf + "  query = " + sql4);
		
			result= true;
	
		
			
	}
		catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert into PBANDI_T_AZIONE_RECUP_BANCA", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		return result;
	}
	
	@Override
	public Boolean modificaAzioneRecuperoBanca(AzioneRecuperoBancaVO azioneRecupVO, Long idUtente, Long idProgetto,
			Long idAzioneRecupero,int idModalitaAgev) {
		
		String prf = "[AzioneRecuperoBancaDAOImpl::salvaAzioneRecupero ]";
		LOG.info(prf + "BEGIN");
			
		
		if (azioneRecupVO == null) {
			throw new InvalidParameterException("azione non valorizzata.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		
		Boolean result = true;
		try {
					String sql2 = "UPDATE PBANDI_T_AZIONE_RECUP_BANCA "
							+ " SET DT_FINE_VALIDITA = sysdate,"
							+ " DT_AGGIORNAMENTO = sysdate,"
							+ " ID_UTENTE_AGG = " + idUtente
							+ " WHERE ID_AZIONE_RECUPERO_BANCA = " + idAzioneRecupero;
					
					getJdbcTemplate().update(sql2); 
					
					result =  insertAzioneRecuperoBanca(azioneRecupVO, idUtente, idProgetto, idModalitaAgev); 
							 
		}catch(Exception e) {
					LOG.error(prf + "Exception while trying to read PBANDI_T_AZIONE_RECUP_BANCA", e);
					result = false; 
				}finally {
					LOG.info(prf + " END");
				}
				return result;
	}

	private AzioneRecuperoBancaVO popolaAzione(AzioneRecuperoBancaVO azioneRecupVO, long idAzioneRecup, Long idProgetto,
			Long idUtente) {
		
		AzioneRecuperoBancaVO azioneDaSalvare = new AzioneRecuperoBancaVO();
		
		azioneDaSalvare.setIdAzioneRecupero(idAzioneRecup);
		azioneDaSalvare.setIdProgetto(idProgetto);
		azioneDaSalvare.setIdAttivitaAzione(azioneRecupVO.getIdAttivitaAzione());
		azioneDaSalvare.setNumProtocollo(azioneRecupVO.getNumProtocollo());
		azioneDaSalvare.setDataAzione(azioneRecupVO.getDataAzione());
		azioneDaSalvare.setNote(azioneRecupVO.getNote());
		azioneDaSalvare.setIdUtenteIns(idUtente);
		azioneDaSalvare.setIdUtenteAgg(idUtente);

		return azioneDaSalvare;
	}


	@Override
	public AzioneRecuperoBancaVO getAzioneRecuperoBanca(Long idUtente, Long idProgetto, Long idAzioneRecupero,int idModalitaAgev) {
		
		String prf = "[AzioneRecuperoBancaDAOImpl::salvaAzioneRecupero ]";
		LOG.info(prf + "BEGIN"); 
		
		AzioneRecuperoBancaVO azioneRecupero = new AzioneRecuperoBancaVO();
		try {
			
			String sql= "SELECT * FROM PBANDI_T_AZIONE_RECUP_BANCA "
					+ "WHERE ID_AZIONE_RECUPERO_BANCA = " + idAzioneRecupero + "\n"
					+ " and ID_MODALITA_AGEVOLAZIONE = "+idModalitaAgev +"\n "
					+ "ORDER BY DT_AGGIORNAMENTO DESC"; 
			
		
			azioneRecupero = (AzioneRecuperoBancaVO) getJdbcTemplate().queryForObject(sql, new AzioneRecuperoBancaRowMapper());
			 
		
		
		}	
			catch (Exception e) {
				LOG.error(prf + "Exception while trying to read PBANDI_T_AZIONE_RECUP_BANCA", e);
		}finally {
		LOG.info(prf + " END");
		}
				
		return azioneRecupero;
	}



	@Override
	public ArrayList<AttivitaDTO> getListaAttivitaAzione() {
		
		String prf = "[AzioneRecuperoBancaDAOImpl::getListaAttivita]";
		LOG.info(prf + "BEGIN"); 
		
		ArrayList<AttivitaDTO> listaAttivita = new ArrayList<AttivitaDTO>(); 
		
		try {
			String sql = "SELECT DISTINCT DESC_ATT_MONIT_CRED, ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED pdamc \r\n"
					+ "WHERE PDAMC .ID_TIPO_MONIT_CRED = 1 \r\n"
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
	public ArrayList<StoricoAzioneRecuperoBancaDTO> getStoricoAzioni(Long idUtente, Long idProgetto,int idModalitaAgev) {
		
		String prf = "[AzioneRecuperoBancaDAOImpl::getStoricoAzione]";
		LOG.info(prf + "BEGIN"); 
		
		
		ArrayList<StoricoAzioneRecuperoBancaDTO> storico = new ArrayList<StoricoAzioneRecuperoBancaDTO>();
		try {
			String sql = "SELECT ptpf.NOME,\r\n"
					+ "			 ptpf.COGNOME, \r\n"
					+ "		     ptarb.DT_INSERIMENTO, \r\n"
					+ "			 PTARB .DT_AZIONE ,\r\n"
					+ "			 pdamc.DESC_ATT_MONIT_CRED,\r\n"
					+ "			 PTARB .NUM_PROTOCOLLO,\r\n"
					+ "			 PTARB .NOTE, \r\n"
					+ "			 PTARB .ID_AZIONE_RECUPERO_BANCA \r\n"
					+ "FROM PBANDI_T_AZIONE_RECUP_BANCA ptarb \r\n"
					+ " 	LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptarb.ID_UTENTE_INS\r\n"
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
					+ " WHERE ptarb.ID_PROGETTO = ?\r\n"
					+ " and ptarb.ID_MODALITA_AGEVOLAZIONE=?\r\n"
					+ " AND pdamc.ID_ATTIVITA_MONIT_CRED = ptarb.ID_ATTIVITA_AZIONE\r\n"
					+ " AND PTARB .DT_FINE_VALIDITA IS not null \r\n"
					+ " ORDER BY DT_AZIONE DESC, PTARB .ID_AZIONE_RECUPERO_BANCA DESC  ";
			
			storico = (ArrayList<StoricoAzioneRecuperoBancaDTO>) getJdbcTemplate().query(sql, new StoricoAzioneRecuperoBancaRowMapper(), new Object [] {
					idProgetto, idModalitaAgev
			}); 
			LOG.info(prf +  " param [IdProgetto] = " + idProgetto);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_ATTIVITA_MONIT_CRED", e);
	}finally {
	LOG.info(prf + " END");
	}
		
		return storico;
	}

	@Override
	public ArrayList<StoricoAzioneRecuperoBancaDTO> getStoricoAzioneRecupBanca(Long idUtente, Long idProgetto,int idModalitaAgev) {
		String prf = "[AzioneRecuperoBancaDAOImpl::getStoricoAzione]";
		LOG.info(prf + "BEGIN"); 
		
		
		ArrayList<StoricoAzioneRecuperoBancaDTO> storico = new ArrayList<StoricoAzioneRecuperoBancaDTO>();
		try {
			String sql = "SELECT ptpf.NOME,\r\n"
					+ "			 ptpf.COGNOME, \r\n"
					+ "		     ptarb.DT_INSERIMENTO, \r\n"
					+ "			 PTARB .DT_AZIONE ,\r\n"
					+ "			 pdamc.DESC_ATT_MONIT_CRED,\r\n"
					+ "			 PTARB .NUM_PROTOCOLLO,\r\n"
					+ "			 PTARB .NOTE, \r\n"
					+ "			 PTARB .ID_AZIONE_RECUPERO_BANCA \r\n"
					+ "FROM PBANDI_T_AZIONE_RECUP_BANCA ptarb \r\n"
					+ " 	LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptarb.ID_UTENTE_INS\r\n"
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
					+ " WHERE ptarb.ID_PROGETTO = ?\r\n"
					+ " and ptarb.ID_MODALITA_AGEVOLAZIONE=?\r\n"
					+ " AND pdamc.ID_ATTIVITA_MONIT_CRED = ptarb.ID_ATTIVITA_AZIONE\r\n"
					+ " AND PTARB .DT_FINE_VALIDITA IS null \r\n"
					+ " ORDER BY DT_AZIONE DESC, PTARB .ID_AZIONE_RECUPERO_BANCA DESC  ";
			
			storico = (ArrayList<StoricoAzioneRecuperoBancaDTO>) getJdbcTemplate().query(sql, new StoricoAzioneRecuperoBancaRowMapper(), new Object [] {
					idProgetto, idModalitaAgev
			});
			
			LOG.info(prf +  " param [IdProgetto] = " + idProgetto);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_ATTIVITA_MONIT_CRED", e);
	}finally {
	LOG.info(prf + " END");
	}
		
		return storico;
	}




}
