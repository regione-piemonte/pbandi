/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.business.service.impl.AmministrativoContabileServiceImpl;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PassaggioPerditaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.NoteGeneraliVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.PassaggioPerditaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.TransazioneVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoRevocaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.PassaggioPerditaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.TransazioneVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PassaggioPerditaDAOImpl extends JdbcDaoSupport implements PassaggioPerditaDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	DocumentoManager documentoManager;
	
	@Autowired
	public PassaggioPerditaDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	@Autowired
	private AmministrativoContabileServiceImpl amministrativoContabileServiceImpl;

	@Override
	public Boolean salvaPassaggioPerdita(PassaggioPerditaVO passaggioPerdita, int idModalitaAgev) {
		
		String prf = "[PassaggioPerditaDAOImpl::salvaPassaggioPerdita]";
		LOG.info(prf +"BEGIN");
		
		Boolean result  = true; 
		
		if(passaggioPerdita ==null) {
			throw new InvalidParameterException("passaggio perdita non valorizzato.");
		}
		
		try {
			String sqlSeq = "SELECT seq_PBANDI_T_PASSAGGIO_PERDITA.nextval FROM dual";	
			long idPassaggioPerdita = (long) getJdbcTemplate().queryForObject(sqlSeq.toString(), long.class);
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			
			String dataPassPer = null; 
			
			if (passaggioPerdita.getDataPassaggioPerdita()!=null) {
				dataPassPer = dt.format(passaggioPerdita.getDataPassaggioPerdita());
			}
			String getPP = "SELECT * FROM PBANDI_T_PASSAGGIO_PERDITA"
					+ " WHERE ID_PROGETTO = " + passaggioPerdita.getIdProgetto() + "\r\n"
					+ "AND DT_FINE_VALIDITA IS NULL\r\n"
					+ " and ID_MODALITA_AGEVOLAZIONE= "+ idModalitaAgev + "\r\n"
					+ " ORDER BY DT_INSERIMENTO DESC";
			
			List<PassaggioPerditaVO> lista =  new ArrayList<PassaggioPerditaVO>(); 
			
			
			lista= getJdbcTemplate().query(getPP, new PassaggioPerditaRowMapper()); 
			
			if(lista.size() >0) {
				
				
				PassaggioPerditaVO ppDB = lista.get(0); 
				String modifica = "UPDATE PBANDI_T_PASSAGGIO_PERDITA "
						+ " SET DT_FINE_VALIDITA = sysdate, "
						+ " DT_AGGIORNAMENTO = sysdate,"
						+ " ID_UTENTE_AGG = " + passaggioPerdita.getIdUtenteIns()
						+ " WHERE ID_PASSAGGIO_PERDITA = " + ppDB.getIdPassaggioPerdita();
				
				getJdbcTemplate().update(modifica); 
				
				
				String insert = "INSERT INTO PBANDI_T_PASSAGGIO_PERDITA ("
						+ "ID_PASSAGGIO_PERDITA,\r\n"
						+ "ID_PROGETTO,\r\n"
						+ "DT_PASSAGGIO_PERDITA,\r\n"
						+ "IMP_PERDITA_COMPLESSIVA,\r\n"
						+ "IMP_PERDITA_CAPITALE,\r\n"
						+ "IMP_PERDITA_INTERESSI,\r\n"
						+ "IMP_PERDITA_AGEVOLAZ,\r\n"
						+ "IMP_PERDITA_MORA,\r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "DT_INSERIMENTO,ID_MODALITA_AGEVOLAZIONE\r\n"
						+ ") VALUES (?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?)"; 
				
				getJdbcTemplate().update(insert, new Object[] {
						idPassaggioPerdita,
						passaggioPerdita.getIdProgetto(),
						(dataPassPer!=null && dataPassPer.trim().length()>0) ? Date.valueOf(dataPassPer): null,
						passaggioPerdita.getImpPerditaComplessiva(),
						passaggioPerdita.getImpPerditaCapitale(),
						passaggioPerdita.getImpPerditaInterressi(),
						passaggioPerdita.getImpPerditaAgevolaz(),
						passaggioPerdita.getImpPerditaMora(), 
						passaggioPerdita.getNote(),
						passaggioPerdita.getIdUtenteIns(), idModalitaAgev
				});

				//chiamata ad insert su t_distinta per amm.vo contabile
				setDistinta(passaggioPerdita.getIdProgetto(), passaggioPerdita.getIdUtenteIns(), idPassaggioPerdita, passaggioPerdita, idModalitaAgev);

			} else {
				
				
				String insert = "INSERT INTO PBANDI_T_PASSAGGIO_PERDITA ("
						+ "ID_PASSAGGIO_PERDITA,\r\n"
						+ "ID_PROGETTO,\r\n"
						+ "DT_PASSAGGIO_PERDITA,\r\n"
						+ "IMP_PERDITA_COMPLESSIVA,\r\n"
						+ "IMP_PERDITA_CAPITALE,\r\n"
						+ "IMP_PERDITA_INTERESSI,\r\n"
						+ "IMP_PERDITA_AGEVOLAZ,\r\n"
						+ "IMP_PERDITA_MORA,\r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "DT_INSERIMENTO, ID_MODALITA_AGEVOLAZIONE\r\n"
						+ ") VALUES (?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?)"; 
				
				getJdbcTemplate().update(insert, new Object[] {
						idPassaggioPerdita,
						passaggioPerdita.getIdProgetto(),
						(dataPassPer!=null && dataPassPer.trim().length()>0) ? Date.valueOf(dataPassPer): null,
						passaggioPerdita.getImpPerditaComplessiva(),
						passaggioPerdita.getImpPerditaCapitale(),
						passaggioPerdita.getImpPerditaInterressi(),
						passaggioPerdita.getImpPerditaAgevolaz(),
						passaggioPerdita.getImpPerditaMora(), 
						passaggioPerdita.getNote(),
						passaggioPerdita.getIdUtenteIns(), idModalitaAgev
				});
				
				
				//chiamata ad insert su t_distinta per amm.vo contabile
				setDistinta(passaggioPerdita.getIdProgetto(), passaggioPerdita.getIdUtenteIns(), idPassaggioPerdita,passaggioPerdita, idModalitaAgev);

			}
			
			
			
			LOG.debug(prf + "  param [idPassaggioPerdita] = " + idPassaggioPerdita);
			LOG.debug(prf + "  param [IdProgetto] = " + passaggioPerdita.getIdProgetto());
			LOG.debug(prf + "  param [getDataPassaggioPerdita] = " + passaggioPerdita.getDataPassaggioPerdita());
			LOG.debug(prf + "  param [getImpPerditaComplessiva] = " + passaggioPerdita.getImpPerditaComplessiva());
			LOG.debug(prf + "  param [getImpPerditaCapitale] = " + passaggioPerdita.getImpPerditaCapitale());
			LOG.debug(prf + "  param [getImpPerditaInterressi] = " + passaggioPerdita.getImpPerditaInterressi());
			LOG.debug(prf + "  param [getImpPerditaAgevolaz] = " + passaggioPerdita.getImpPerditaAgevolaz());
			LOG.debug(prf + "  param [getImpPerditaMora] = " + passaggioPerdita.getImpPerditaMora());
			LOG.debug(prf + "  param [getNote] = " + passaggioPerdita.getNote());
			LOG.debug(prf + "  param [IdUtente] = " + passaggioPerdita.getIdUtenteIns());
			
		
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert PBANDI_T_PASSAGGIO_PERDITA", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}

	
	private Long setDistinta(Long idProgetto, Long idUtente, Long idPassaggioPerdita, PassaggioPerditaVO passaggioPerdita, int idModalitaAgev) {
		
		int up1 = 0;
		int up2 = 0; 
		
		try {
			
			// get idDistinta
			String sqlSeq = "SELECT SEQ_PBANDI_T_DISTINTA.nextval FROM dual";
			int idDistinta = getJdbcTemplate().queryForObject(sqlSeq, Integer.class);
			
			// get idDistintaDett
			String sqlSeq2 = "SELECT SEQ_PBANDI_T_DISTINTA_DETT.nextval FROM dual";
			int idDistintaDett = getJdbcTemplate().queryForObject(sqlSeq2, Integer.class);
			
			// get idModalitaAgevolazione da chiarire con amministrativo contabile, 
//			String sqlAge= "SELECT DISTINCT prbmaeb.ID_MODALITA_AGEVOLAZIONE \r\n"
//					+ "FROM PBANDI_T_BANDO ptb \r\n"
//					+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptb.ID_BANDO = prbli.ID_BANDO \r\n"
//					+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \r\n"
//					+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
//					+ "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
//					+ "LEFT JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.ID_BANDO = ptb.ID_BANDO \r\n"
//					+ "WHERE prsp.ID_PROGETTO = " + idProgetto+"\r\n"
//					+ "AND ROWNUM=1";;
//			int idModalitaAgevolazione = getJdbcTemplate().queryForObject(sqlAge.toString(), Integer.class);

			String sqlAge = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
					"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
					"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
			int idModalitaAgevolazioneRif = getJdbcTemplate().queryForObject(sqlAge, Integer.class, idModalitaAgev);
			
			// Insert in T_DISTINTA
			String insert = "INSERT INTO PBANDI.PBANDI_T_DISTINTA\r\n"
					+ "	(ID_DISTINTA,\r\n"
					+ "	ID_ENTITA,\r\n"
					+ "	ID_TIPO_DISTINTA,\r\n"
					+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	ID_STATO_DISTINTA,\r\n"
					+ "	DT_INIZIO_VALIDITA,\r\n"
					+ "	ID_UTENTE_INS,\r\n"
					+ "	DT_INSERIMENTO)\r\n"
					+ "VALUES\r\n"
					+ "(?, ?, ?, ?, ?,\r\n"
					+ "sysdate,\r\n"
					+ "?,\r\n"
					+ "sysdate)"; 
			
			up1 = getJdbcTemplate().update(insert, new Object[] {idDistinta, 509, 4, idModalitaAgev, 8, idUtente});


			Long rigaDistinta = getJdbcTemplate().queryForObject("SELECT COUNT(1) + 1 FROM PBANDI_T_DISTINTA_DETT ptdd WHERE ptdd.ID_DISTINTA = ?", Long.class, idDistinta);

			// Insert T_DISTINTA_DETT
			String insertDist = "INSERT INTO PBANDI.PBANDI_T_DISTINTA_DETT\r\n"
					+ "	(ID_DISTINTA_DETT,\r\n"
					+ "	RIGA_DISTINTA,\r\n"
					+ "	ID_DISTINTA,\r\n"
					+ "	ID_TARGET,\r\n"
					+ "	DT_INIZIO_VALIDITA,\r\n"
					+ "	ID_UTENTE_INS,\r\n"
					+ "	DT_INSERIMENTO)\r\n"
					+ "VALUES\r\n"
					+ "(?, ?, ?, ?, sysdate, ?, sysdate)";
			
			up2 = getJdbcTemplate().update(insertDist, new Object[] {idDistintaDett, rigaDistinta, idDistinta, idPassaggioPerdita, idUtente});

			
		
			//dopo aver inserito su t_distinte, chiama il servizio di amm.vo cont
			Boolean ammRes = amministrativoContabileServiceImpl.callToDistintaErogazionePerdita(idDistinta, rigaDistinta.intValue(), idProgetto.intValue(),
					passaggioPerdita.getDataPassaggioPerdita(), (passaggioPerdita.getImpPerditaComplessiva() != null) ? passaggioPerdita.getImpPerditaComplessiva().doubleValue() : (double) 0,
							idModalitaAgev, idModalitaAgevolazioneRif, idUtente);
			
			LOG.info("Res amm.vo cont: " + ammRes);
		} catch (Exception e) {
			logger.error(e);
			return (long) -1;
		}		
		
		return (long) (up1 - up2);
	}
	
	
	@Override
	public PassaggioPerditaVO getPassaggioPerdita(Long idProgetto, int idModalitaAgev) {
		String prf = "[PassaggioPerditaDAOImpl::getPassaggioPerdita]";
		LOG.info(prf +"BEGIN");
		
		List<PassaggioPerditaVO> lista  =  new ArrayList<PassaggioPerditaVO>(); 
		PassaggioPerditaVO ppDB = new PassaggioPerditaVO();
		try
		{

			String sql= "SELECT 	* FROM  PBANDI_T_PASSAGGIO_PERDITA ptpp\r\n"
					+ "				WHERE ptpp.ID_PROGETTO = " + idProgetto
					+ "				AND ptpp.DT_FINE_VALIDITA IS NULL "
					+ "  and ptpp.ID_MODALITA_AGEVOLAZIONE= " + idModalitaAgev 
					+ "				ORDER BY ID_PASSAGGIO_PERDITA DESC\r\n"	;
			
			lista  = getJdbcTemplate().query(sql, new PassaggioPerditaRowMapper());		
			if(lista.size() >0)
				ppDB = lista.get(0);
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_PASSAGGIO_PERDITA", e); 
		}finally {
			LOG.info(prf + " END");
		}
		
		
		return ppDB;
	}

	@Override
	public List<StoricoRevocaDTO> getStoricoPassaggioPerdita(Long idProgetto, int idModalitaAgev) {
		String prf = "[PassaggioPerditaDAOImpl::getStorico]";
		LOG.info(prf +"BEGIN");
		
		List<StoricoRevocaDTO> lista =  new ArrayList<StoricoRevocaDTO>();
		try
		{

			String sql= " SELECT\r\n"
					+ "    PTPF.COGNOME,\r\n"
					+ "    PTPF.NOME,\r\n"
					+ "    ptpp.DT_INSERIMENTO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_PASSAGGIO_PERDITA ptpp\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptpp.ID_UTENTE_INS\r\n"
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
					+ "    ptpp.ID_PROGETTO = ?\r\n"
					+ "    and ptpp.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND ptpp.DT_FINE_VALIDITA IS NOT NULL\r\n"
					+ "ORDER BY\r\n"
					+ "    ptpp.ID_PASSAGGIO_PERDITA DESC"	;
			
			lista = getJdbcTemplate().query(sql, new StoricoRevocaRowMapper(), new Object[] {
					idProgetto, idModalitaAgev
			});
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert PBANDI_T_PASSAGGIO_PERDITA", e); 
		}finally {
			LOG.info(prf + " END");
		}
		
		return lista;
	}

	@Override
	public Boolean salvaTransazione(TransazioneVO transazioneVO, int idModalitaAgev) {
		
		String prf = "[PassaggioPerditaDAOImpl::salvaTransazione]";
		LOG.info(prf +"BEGIN");
		
		Boolean result  = true; 
		
		if(transazioneVO ==null) {
			throw new InvalidParameterException("Transazione");
		}
		
		try {
			String sqlSeq = "SELECT SEQ_PBANDI_T_TRANSAZIONI_BANCA.nextval FROM dual";	
			long idTransacBanca = (long) getJdbcTemplate().queryForObject(sqlSeq.toString(), long.class);
			String getTT =  "SELECT * "
					+ "FROM PBANDI_T_TRANSAZIONI_BANCA pttb , 	PBANDI_D_BANCA pdb \r\n"
					+ "WHERE PTTB.ID_PROGETTO = \r\n" + transazioneVO.getIdProgetto()
					+ " and pttb.ID_MODALITA_AGEVOLAZIONE = "+ idModalitaAgev + "\r\n"
					+ "AND PTTB .ID_TRANSAZIONE_BANCA  =  PDB .ID_BANCA \r\n"
					+ "AND PTTB.DT_FINE_VALIDITA IS NULL 		\r\n"
					+ "ORDER BY PTTB.DT_INSERIMENTO DESC"; 
		
			
			List<TransazioneVO> lista =  new ArrayList<TransazioneVO>(); 		
			lista= getJdbcTemplate().query(getTT, new TransazioneVORowMapper()); 
			
			if(lista.size() >0) {
				
				TransazioneVO transacDB = lista.get(0); 
				
				String modifica = "UPDATE PBANDI_T_TRANSAZIONI_BANCA "
						+ " SET DT_FINE_VALIDITA = sysdate, "
						+ " DT_AGGIORNAMENTO = sysdate,"
						+ " ID_UTENTE_AGG = " + transazioneVO.getIdUtenteIns()
						+ " WHERE ID_TRANSAZIONE_BANCA = " + transacDB.getIdTransazione();
				
				getJdbcTemplate().update(modifica); 
				
				
				String insert = "INSERT INTO PBANDI_T_TRANSAZIONI_BANCA ("
						+ "ID_TRANSAZIONE_BANCA,\r\n"
						+ "ID_PROGETTO,\r\n"
						+ "IMP_RICONCILIATO,\r\n"
						+ "IMP_TRANSATO,\r\n"
						+ "PERC_TRANSAZIONE,\r\n"
						+ "IMP_PAGATO,\r\n"
						+ "ID_BANCA,\r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "DT_INSERIMENTO, ID_MODALITA_AGEVOLAZIONE\r\n"
						+ ") VALUES (?,?,?,?,?,?,?,?,sysdate,?,sysdate, ? )"; 
				
				getJdbcTemplate().update(insert, new Object[] {
						idTransacBanca,
					    transazioneVO.getIdProgetto(), 
					    transazioneVO.getImpRiconciliato(),
					    transazioneVO.getImpTransato(),
					    transazioneVO.getPercTransazione(),
					    transazioneVO.getImpPagato(), 
					    transazioneVO.getIdBanca(), 
					    transazioneVO.getNote(), 
					    transazioneVO.getIdUtenteIns(), 
					    idModalitaAgev				});

			} else {
				

				String insert = "INSERT INTO PBANDI_T_TRANSAZIONI_BANCA ("
						+ "ID_TRANSAZIONE_BANCA,\r\n"
						+ "ID_PROGETTO,\r\n"
						+ "IMP_RICONCILIATO,\r\n"
						+ "IMP_TRANSATO,\r\n"
						+ "PERC_TRANSAZIONE,\r\n"
						+ "IMP_PAGATO,\r\n"
						+ "ID_BANCA,\r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "DT_INSERIMENTO, ID_MODALITA_AGEVOLAZIONE\r\n"
						+ ") VALUES (?,?,?,?,?,?,?,?,sysdate,?,sysdate, ?)"; 
				
				getJdbcTemplate().update(insert, new Object[] {
						idTransacBanca,
					    transazioneVO.getIdProgetto(), 
					    transazioneVO.getImpRiconciliato(),
					    transazioneVO.getImpTransato(),
					    transazioneVO.getPercTransazione(),
					    transazioneVO.getImpPagato(), 
					    transazioneVO.getIdBanca(), 
					    transazioneVO.getNote(), 
					    transazioneVO.getIdUtenteIns(), idModalitaAgev
				});
				

			}	
			
			LOG.debug(prf + "  param [idTransazioneBanca] = " + idTransacBanca);
			
			
		
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert PBANDI_T_TRANSAZIONI_BANCA", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		return result;
		
		
	}

	@Override
	public TransazioneVO getTransazione(Long idProgetto, int idModalitaAgev) {
		String prf = "[PassaggioPerditaDAOImpl::getTransazione]";
		LOG.info(prf +"BEGIN");
		
		TransazioneVO transazioneVO = new TransazioneVO(); 
		
		try {
			
			String sql =  "SELECT * "
					+ "FROM PBANDI_T_TRANSAZIONI_BANCA pttb , 	PBANDI_D_BANCA pdb \r\n"
					+ "WHERE PTTB.ID_PROGETTO = \r\n" + idProgetto + "\r\n"
					+ " and pttb.ID_MODALITA_AGEVOLAZIONE = "+ idModalitaAgev + "\r\n"
					+ "AND PTTB .ID_BANCA  =  PDB .ID_BANCA \r\n"
					+ "AND PTTB.DT_FINE_VALIDITA IS NULL 		\r\n"; 
             				
            	  transazioneVO = (TransazioneVO) getJdbcTemplate().queryForObject(sql, new TransazioneVORowMapper()); 
			
		} catch ( Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_TRANSAZIONI_BANCA", e);
		}
		
		
		return transazioneVO;
	}

	@Override
	public List<StoricoRevocaDTO> getStoricoTransazioni(Long idProgetto, int idModalitaAgev) {
		
		String prf = "[PassaggioPerditaDAOImpl::getStoricoTransazioni]";
		LOG.info(prf +"BEGIN");
		
		List<StoricoRevocaDTO> storico =  new ArrayList<StoricoRevocaDTO>();
		try
		{

			String sql= " SELECT\r\n"
					+ "    PTPF.COGNOME,\r\n"
					+ "    PTPF.NOME,\r\n"
					+ "    pttb.DT_INSERIMENTO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_TRANSAZIONI_BANCA pttb\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = pttb.ID_UTENTE_INS\r\n"
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
					+ "    pttb.ID_PROGETTO = ?\r\n"
					+ "    and pttb.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND pttb.DT_FINE_VALIDITA IS NOT NULL\r\n"
					+ "ORDER BY\r\n"
					+ "    pttb.ID_TRANSAZIONE_BANCA DESC "	;
			
			storico = getJdbcTemplate().query(sql, new StoricoRevocaRowMapper(), new Object[] {
					idProgetto, idModalitaAgev
			});
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert PBANDI_T_TRANSAZIONI_BANCA", e); 
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;
	}
	
	@Override
	public List<AttivitaDTO> getListaBanche(String string){
		
		List<AttivitaDTO> listaBanche = new ArrayList<AttivitaDTO>();
		
		try {
			
			String sql ="SELECT ID_BANCA, DESC_BANCA FROM PBANDI_D_BANCA pdb"
					+ " where upper(pdb.desc_banca) like upper('%"+ string +"%')"
					+ " AND  ROWNUM <= 100\r\n";
			
			RowMapper<AttivitaDTO> lista= new RowMapper<AttivitaDTO>() {
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO banca =  new AttivitaDTO(); 
					banca.setDescAttivita(rs.getString("DESC_BANCA"));
					banca.setIdAttivita(rs.getLong("ID_BANCA"));	
					return banca;
				}		
			};
			listaBanche = getJdbcTemplate().query(sql, lista); 	
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_D_BANCA", e);
		}
		
		return listaBanche;
	}

	/*@Override
	public Long salvaNote(NoteGeneraliVO noteGeneraliVO, boolean isModifica,  int idModalitaAgevolazione) {
			String prf = "[PassaggioPerditaDAOImpl::salvaNote]";
			LOG.info(prf + " BEGIN");
			
			
			LOG.info(prf + " NOTA from FE: " + noteGeneraliVO.getNote());
			
			Boolean result  = true; 
			Long idAnnotazione = null; 		
		try {
			String sql =  "SELECT SEQ_PBANDI_T_ANNOTAZ_GENERALI.nextval FROM dual"; 
			 idAnnotazione = (Long) getJdbcTemplate().queryForObject(sql, Long.class); 
			
			/*String getNote =  "SELECT * "
					+ "FROM PBANDI_T_ANNOTAZ_GENERALI ptag \r\n"
					+ "WHERE ptag.ID_PROGETTO = " + noteGeneraliVO.getIdProgetto() + " \r\n"
					+ "and ptag.DT_FINE_VALIDITA IS NULL \r\n"
					+ "AND ptag.DT_FINE_VALIDITA IS NULL \r\n"
					+ "ORDER BY ptag.ID_ANNOTAZ_GENERALE DESC"; 
			
			List<NoteGeneraliVO> lista =  new ArrayList<NoteGeneraliVO>(); 
			lista= getJdbcTemplate().query(getNote, new NoteGeneraliVORowMapper());*/
			
			/*if(isModifica) {
				//NoteGeneraliVO ngdb =  lista.get(0); 
				String modifica = "UPDATE PBANDI_T_ANNOTAZ_GENERALI \r\n"
						+ " SET DT_FINE_VALIDITA = sysdate, \r\n"
						+ " DT_AGGIORNAMENTO = sysdate,\r\n"
						+ " ID_UTENTE_AGG = " + noteGeneraliVO.getIdUtenteIns() + "\r\n"
						+ " WHERE ID_ANNOTAZ_GENERALE = " + noteGeneraliVO.getIdAnnotazione();
				
				getJdbcTemplate().update(modifica); 
				
				
				/*String insert = "INSERT INTO PBANDI_T_ANNOTAZ_GENERALI ("
						+ "ID_ANNOTAZ_GENERALE,\r\n"
						+ "ID_PROGETTO, \r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "DT_INSERIMENTO,ID_MODALITA_AGEVOLAZIONE \r\n"
						+ ") VALUES (?,?,?,sysdate,?,sysdate,?)"; 
				
				getJdbcTemplate().update(insert, new Object[] {
						idAnnotazione,
						noteGeneraliVO.getIdProgetto(), 
						noteGeneraliVO.getNote(),
						noteGeneraliVO.getIdUtenteIns(), 
						idModalitaAgevolazione
						});*/
				
				
				/*String insert = "INSERT INTO PBANDI_T_ANNOTAZ_GENERALI (\r\n"
						+ "ID_ANNOTAZ_GENERALE,\r\n"
						+ "ID_PROGETTO,\r\n"
						+ "ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "ID_UTENTE_AGG,\r\n"
						+ "DT_INSERIMENTO,\r\n"
						+ "DT_AGGIORNAMENTO\r\n"
						+ ") SELECT \r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "?,\r\n"
						+ "SYSDATE,\r\n"
						+ "DT_AGGIORNAMENTO\r\n"
						+ "FROM PBANDI_T_ANNOTAZ_GENERALI\r\n"
						+ "WHERE ID_ANNOTAZ_GENERALE = " + noteGeneraliVO.getIdAnnotazione(); 
				
				getJdbcTemplate().update(insert, new Object[] {
						idAnnotazione,
						noteGeneraliVO.getIdProgetto(),
						idModalitaAgevolazione,
						noteGeneraliVO.getNote(),
						noteGeneraliVO.getIdUtenteIns() });
				
			} else {
				
				String insert = "INSERT INTO PBANDI_T_ANNOTAZ_GENERALI ("
						+ "ID_ANNOTAZ_GENERALE,\r\n"
						+ "ID_PROGETTO, \r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "ID_UTENTE_INS,\r\n"
						+ "DT_INSERIMENTO, ID_MODALITA_AGEVOLAZIONE\r\n"
						+ ") VALUES (?,?,?,sysdate,?,sysdate,?)"; 
				
				getJdbcTemplate().update(insert, new Object[] {
						idAnnotazione,
						noteGeneraliVO.getIdProgetto(),
						noteGeneraliVO.getNote(),
						noteGeneraliVO.getIdUtenteIns(), 
						idModalitaAgevolazione
						});
				
			}
			
			/*LOG.info("Form data: " + noteGeneraliVO.getFileDaSalvare());
			
			for (MultipartFormDataInput multi: noteGeneraliVO) {
				LOG.info(multi.get.getFormDataPart("nomeFile", String.class, null));
				LOG.info(multi.getFormDataPart("file", File.class, null));
			}*/
			
			/*LOG.debug(prf + "  param [idNote] = " + idAnnotazione);
			LOG.debug(prf + "  param [Note Generale] = " + noteGeneraliVO);
				
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert PBANDI_T_ANNOTAZ_GENERALI", e);
			idAnnotazione = null;          
		}finally {
			LOG.info(prf + " END");
		}
		
		return idAnnotazione;
	}*/
	

	/*@Override
	public Boolean salvaUploadAllegato(byte[] file, String nomeFile, Long idUtente, BigDecimal idTarget, BigDecimal idTipoDocumentoIndex,BigDecimal idProgetto, BigDecimal idEntita ){
//		private Boolean salvaAllegatoFrom(MultipartFormDataInput multipartFormData) {
			String prf = "[PassaggioPerditaDAOImpl:: salva Allegato]";
			logger.info(prf + " BEGIN");
			
			Boolean result = null; 
			try {
			
				//Long idtipoindex= (long) 69;
				String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
						+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX="+ idTipoDocumentoIndex, String.class);
				if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
					throw new Exception("Tipo documento index non trovato.");					
				
				
				Date currentDate = new Date(System.currentTimeMillis());
				
				DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
				documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
				documentoIndexVO.setNomeFile(nomeFile);
				documentoIndexVO.setIdTarget(idTarget);
				documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
				documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
				documentoIndexVO.setIdEntita(idEntita);
				documentoIndexVO.setRepository(descBreveTipoDocIndex);
				documentoIndexVO.setUuidNodo("UUID");
				documentoIndexVO.setIdProgetto(idProgetto);	
				// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
			    result = documentoManager.salvaFileConVisibilita(file, documentoIndexVO, null);
				
				
			} catch (Exception e) {
				logger.error("errore inserimento pbandi_t_documento_index "+e);
				result= false; 
			}
			return result;
		}*/
	
	/*@Override
	public Boolean aggiornaAllegati(List<GestioneAllegatiVO> allegatiPresenti, Long idTarget){
			String prf = "[PassaggioPerditaDAOImpl:: aggiornaAllegati]";
			logger.info(prf + " BEGIN");
			
			Boolean result = null; 
			try {
				for (GestioneAllegatiVO item : allegatiPresenti) {
									
					String queryModifica = "UPDATE PBANDI.PBANDI_T_DOCUMENTO_INDEX\r\n"
							+ "SET ID_TARGET = " + idTarget + "\r\n"
							+ "WHERE ID_DOCUMENTO_INDEX = " + item.getIdDocumentoIndex();

					getJdbcTemplate().update(queryModifica);
					
					logger.info(prf + " The document idIndex: " + item.getIdDocumentoIndex() + " has been updated with idTarget: " + idTarget);
				}
				
				
				
			} catch (Exception e) {
				logger.error("errore durante l'update di id_target in pbandi_t_documento_index "+e);
				result= false; 
			}
			
			
			logger.info(prf + " END");
			return result;
		}*/

	/*@Override
	public List<NoteGeneraliVO> getNoteGenerale(Long idProgetto, int idModalitaAgevolazione) {
		String prf = "[PassaggioPerditaDAOImpl::getNoteGenerale]";
		LOG.info(prf +"BEGIN");
		
		//NoteGeneraliVO noteGeneraliVO = new NoteGeneraliVO(); 
		List<NoteGeneraliVO> noteGenerali = new ArrayList<NoteGeneraliVO>();
		try {
			
			String getNote =  "SELECT ptag.ID_ANNOTAZ_GENERALE,\r\n"
					+ "    ptag.ID_PROGETTO,\r\n"
					+ "    ptag.NOTE,\r\n"
					+ "    ptag.DT_INIZIO_VALIDITA,\r\n"
					+ "    ptag.DT_FINE_VALIDITA,\r\n"
					+ "    ptag.ID_UTENTE_INS,\r\n"
					+ "    ptag.ID_UTENTE_AGG,\r\n"
					+ "    ptag.DT_INSERIMENTO,\r\n"
					+ "    ptag.DT_AGGIORNAMENTO,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    ptpf2.COGNOME AS COGNOME_AGG,\r\n"
					+ "    ptpf2.NOME AS NOME_AGG\r\n"
					+ "FROM PBANDI_T_ANNOTAZ_GENERALI ptag\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptag.ID_UTENTE_INS\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu2 ON ptu2.ID_UTENTE = ptag.ID_UTENTE_AGG\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf2 ON ptpf2.ID_SOGGETTO = ptu2.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "WHERE ptag.ID_PROGETTO = ?\r\n"
					+ "    AND ptag.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND ptag.DT_FINE_VALIDITA is NULL\r\n"
					+ "ORDER BY\r\n"
					+ "    ptag.ID_ANNOTAZ_GENERALE DESC"; 
			
			noteGenerali =  getJdbcTemplate().query(getNote, new NoteGeneraliVORowMapper(), new Object[] {
					idProgetto, idModalitaAgevolazione
			}); 
			
			if(noteGenerali.size()>0) {
				for (NoteGeneraliVO noteGeneraliVO2 : noteGenerali) {
					List<GestioneAllegatiVO> documenti = new ArrayList<GestioneAllegatiVO>();
					try {
						
						String sql = "SELECT * FROM PBANDI_T_DOCUMENTO_INDEX \r\n"
								+ "WHERE \r\n"
								+ "ID_ENTITA =525\r\n"
								+ "AND ID_TIPO_DOCUMENTO_INDEX=69  \r\n"    // 525 corrispoonde all'id_entita della tabella PBANDI_T_ANNOTAZ_GENERALI
								+ "AND id_target="+noteGeneraliVO2.getIdAnnotazione()+"\r\n";
						
						RowMapper<GestioneAllegatiVO> rowMapper  = new RowMapper<GestioneAllegatiVO>() {
							
							@Override
							public GestioneAllegatiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
								GestioneAllegatiVO doc = new GestioneAllegatiVO();
								
								doc.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
								doc.setIdTarget(rs.getBigDecimal("ID_TARGET"));
								doc.setIdTipoDocumentoIndex(rs.getBigDecimal("ID_TIPO_DOCUMENTO_INDEX"));
								doc.setIdEntita(rs.getBigDecimal("ID_ENTITA"));
								doc.setNomeFile(rs.getString("NOME_FILE"));
								doc.setNomeDocumento(rs.getString("NOME_DOCUMENTO"));
								doc.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
								doc.setIdStatoDocumento(rs.getBigDecimal("ID_STATO_DOCUMENTO"));
					
								/*UUID_NODO,REPOSITORY,DT_INSERIMENTO_INDEX,ID_UTENTE_INS
								ID_UTENTE_AGG,NOTE_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,VERSIONE,ID_MODELLO,NUM_PROTOCOLLO,FLAG_FIRMA_CARTACEA
								MESSAGE_DIGEST,DT_VERIFICA_FIRMA,DT_MARCA_TEMPORALE,ID_SOGG_RAPPR_LEGALE,ID_SOGG_DELEGATO,FLAG_TRASM_DEST,ID_CATEG_ANAGRAFICA_MITT
								NOME_DOCUMENTO_FIRMATO,NOME_DOCUMENTO_MARCATO,ACTA_INDICE_CLASSIFICAZ_ESTESO,FLAG_FIRMA_AUTOGRAFA,ID_DOMANDA,	ID_SOGG_BENEFICIARIO */
								
								/*return doc;
							}
						};
						
						documenti = getJdbcTemplate().query(sql, rowMapper); 
						
						if(documenti.size()>0) noteGeneraliVO2.setAllegatiPresenti(documenti);
						
				}catch (Exception e) {	
					LOG.error(prf + "Exception while trying to read PBANDI_T_ANNOTAZ_GENERALI", e);
				}
					
			  }
			}
			
		} catch (Exception e) {	
		LOG.error(prf + "Exception while trying to read PBANDI_T_ANNOTAZ_GENERALI", e);
		}finally {
		LOG.info(prf + " END");
	}
		return noteGenerali;
	}*/

	/*@Override
	public List<StoricoRevocaDTO> getStoricoNote(Long idProgetto, int idModalitaAgev) {
		String prf = "[PassaggioPerditaDAOImpl::getStoricoNote]";
		LOG.info(prf +"BEGIN");
		
		List<StoricoRevocaDTO> storico =  new ArrayList<StoricoRevocaDTO>();
		try
		{

			String sql= "SELECT\r\n"
					+ "    PVNM.NOTE,\r\n"
					+ "    PTPF.NOME,\r\n"
					+ "    ptpf.cognome,\r\n"
					+ "    PVNM.DT_INSERIMENTO,\r\n"
					+ "    PVNM.NOME_ENTITA_PROVENIENZA\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_V_NOTE_MONITORAGGIO pvnm\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE  = pvnm.ID_UTENTE_INS \r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_SOGGETTO = ptu.ID_SOGGETTO \r\n"
					+ "  AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "WHERE\r\n"
					+ "     PVNM.ID_PROGETTO = ?\r\n"
					+ "    AND PVNM.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "ORDER BY\r\n"
					+ "    DT_INSERIMENTO DESC "	;
			
			
			RowMapper<StoricoRevocaDTO> list =  new RowMapper<StoricoRevocaDTO>() {

				@Override
				public StoricoRevocaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					StoricoRevocaDTO nota = new StoricoRevocaDTO(); 
					nota.setCognome(rs.getString("COGNOME"));
					nota.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
					nota.setNome(rs.getString("NOME"));
					nota.setNote(rs.getString("NOTE"));
					nota.setProvenienza(rs.getString("NOME_ENTITA_PROVENIENZA"));
					
					
					return nota;
				}	
			}; 
			
			storico = getJdbcTemplate().query(sql, list, new Object [] {
					idProgetto, idModalitaAgev
			}); 
			
 			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_TRANSAZIONI_BANCA", e); 
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;
	}*/
	
	
	

}
