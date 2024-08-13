/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbwebbo.dto.GenericResponseDTO;
import it.csi.pbandi.pbwebbo.dto.IntegerValueDTO;
import it.csi.pbandi.pbwebbo.dto.StringValueDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.AnagraficaDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.BancaSuggestVO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiBancariEstesiDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.IbanDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.SendToAmmContDTO;
import it.csi.pbandi.pbwebbo.dto.monitoraggioTempi.ParametriMonitoraggioTempiAssociatiDTO;
import it.csi.pbandi.pbwebbo.integration.dao.ConfigurazioneBandoLineaDAO;
import it.csi.pbandi.pbwebbo.util.BeanRowMapper;
import it.csi.pbandi.pbwebbo.util.Constants;


@Service
public class ConfigurazioneBandoLineaDAOImpl extends JdbcDaoSupport implements ConfigurazioneBandoLineaDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public ConfigurazioneBandoLineaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	
	///////////////////////////////   TAB ESTREMI BANCARI //////////////////////////////////////	
	
	public List<ModalitaAgevolazioneDTO> getModalitaAgevolazioneByidBando(Long idBando) {
		
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		Long idServizio = 2L;
		Long idEntita = 128L;
		String esito = "OK";

		//Controllo parametri		
		
		List<ModalitaAgevolazioneDTO> modAgevDTOList = new ArrayList<ModalitaAgevolazioneDTO>();
		
		String sql;
		sql =   "SELECT DISTINCT \r\n"
				+ "	pdma.ID_MODALITA_AGEVOLAZIONE,\r\n"
				+ "	pdma.DESC_BREVE_MODALITA_AGEVOLAZ,\r\n"
				+ "	pdma.DESC_MODALITA_AGEVOLAZIONE\r\n"
				+ "FROM PBANDI_R_BANDO_MODALITA_AGEVOL prbma\r\n"
				+ "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma\r\n"
				+ "	ON  prbma.ID_MODALITA_AGEVOLAZIONE  = pdma.ID_MODALITA_AGEVOLAZIONE \r\n"
				+ "WHERE prbma.ID_BANDO = ? ";
		
		Object[] args = new Object[] { idBando};
			
		logger.info(prf + "\n" + sql + "\n");	
		modAgevDTOList = getJdbcTemplate().query(sql, args, new BeanRowMapper( ModalitaAgevolazioneDTO.class));

		
		
		//TODO MOCK da togliere
		//estremiBancariDTOList  = ModAgevEstremiBancariDTO.getMockElement();
		
		logger.info(prf + " END");
		
		return modAgevDTOList;
	
		
	}
	
	public List<EstremiBancariEstesiDTO> getEstremiBancari(Long idBando, Long idModalitaAgevolazione)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		

		//Controllo parametri		
		
		List<EstremiBancariEstesiDTO> estremiBancariDTOList = new ArrayList<EstremiBancariEstesiDTO>();
		
		try {
			
			String sql;
			sql =     "SELECT \r\n"
					+ "	prbaeb.ID_ESTREMI_BANCARI,\r\n"
					+ "	pteb.ID_BANCA,\r\n"
					+ "	pdb.DESC_BANCA, \r\n"
					+ "	pteb.IBAN,\r\n"
					+ "	prbaeb.MOLTIPLICATORE, \r\n"
					+ "	prbaeb.TIPOLOGIA_CONTO,"
					+ "	prbaeb.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	CASE \r\n"
					+ "		WHEN ptmsac.ID_SERVIZIO = 2 AND ptmsac.ID_ENTITA = 128 AND ptmsac.ESITO = 'OK' THEN 1\r\n"
					+ "		ELSE 0\r\n"
					+ "	END sendToAmmCont\r\n"
					+ "FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbaeb\r\n"
					+ "JOIN PBANDI_T_ESTREMI_BANCARI pteb\r\n"
					+ "	ON pteb.ID_ESTREMI_BANCARI =  prbaeb.ID_ESTREMI_BANCARI \r\n"
					+ "JOIN PBANDI_D_BANCA pdb\r\n"
					+ "	ON pteb.ID_BANCA  = pdb.ID_BANCA \r\n"
					+ "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma\r\n"
					+ "	ON  prbaeb.ID_MODALITA_AGEVOLAZIONE  = pdma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "LEFT JOIN PBANDI_T_MON_SERV_AMMVO_CONTAB ptmsac\r\n"
					+ "	ON prbaeb.ID_ESTREMI_BANCARI = ptmsac.ID_TARGET \r\n"
					+ "WHERE prbaeb.ID_BANDO = ? AND prbaeb.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "ORDER BY pteb.ID_BANCA";
			
			Object[] args = new Object[] { idBando, idModalitaAgevolazione};			
			
			estremiBancariDTOList = getJdbcTemplate().query(sql, args, new BeanRowMapper( EstremiBancariEstesiDTO.class));

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		//TODO MOCK da togliere
		//estremiBancariDTOList  = EstremiBancariDTO.getMockElement();
		
		return estremiBancariDTOList;
	
	}

	@Override
	public String checkBandoLineaByIban(String iban)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		List<StringValueDTO> result;
		
		try {
			
			String sql;
			sql =     "SELECT distinct\r\n"
					+ "	pbi.NOME_BANDO_LINEA as value\r\n"
					+ "FROM \r\n"
					+ "	PBANDI_T_ESTREMI_BANCARI pteb,\r\n"
					+ "	PBANDI_R_BANDO_MOD_AG_ESTR_BAN pbrbmaeb,\r\n"
					+ "	PBANDI_R_BANDO_LINEA_INTERVENT pbi,\r\n"
					+ "	PBANDI_R_BANDO_LINEA_ENTE_COMP pbl\r\n"
					+ "WHERE pteb.ID_ESTREMI_BANCARI = pbrbmaeb.ID_ESTREMI_BANCARI \r\n"
					+ "AND pbrbmaeb.ID_BANDO = pbi.ID_BANDO \r\n"
					+ "AND  pbl.PROGR_BANDO_LINEA_INTERVENTO =pbi.PROGR_BANDO_LINEA_INTERVENTO \r\n"
					+ "AND iban= ? \r\n";
			
			Object[] args = new Object[] { iban };
			
			
			result = getJdbcTemplate().query(sql, args, new BeanRowMapper( StringValueDTO.class)) ;			
			

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		if(result.size() == 0)
			return null;
		
		String nomeBandoLinea = result.get(0).getValue();
		return nomeBandoLinea;
		
		
		
		
		
	
	
	}

	@Override
	public Long insertEstremiBancari(Long idBanca, String iban, Long idUtenteIns)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		Long idEstremiBancari;
		
		try {
									
			String sqlSeq = "select SEQ_PBANDI_T_ESTREMI_BANCA.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			idEstremiBancari = (Long) getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
			logger.info("Nuovo id PBANDI_T_NEWS = "+idEstremiBancari);
			
			String sql;
			sql =     "INSERT INTO  PBANDI_T_ESTREMI_BANCARI \r\n"
					+ "(ID_ESTREMI_BANCARI, ID_BANCA, IBAN, DT_INIZIO_VALIDITA, ID_UTENTE_INS)\r\n"
					+ "VALUES \r\n"
					+ "(?, ?, ?, SYSDATE, ?)	";
			
			logger.info("\n"+sql);
			
			Object[] args = new Object[] { idEstremiBancari, idBanca, iban, idUtenteIns };		
			
			getJdbcTemplate().update(sql, args);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		return idEstremiBancari;
	
	}

	public void insertAssociazioneBandoModAgevEstrBanc(Long idModalitaAgevolazione, Long idBando, Long idEstremiBancari, String moltiplicatore, 
			Long idUtenteIns, String tipologiaConto)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		String  nomeBandoLinea;
		
		try {
									
			String sqlSeq = "select SEQ_PBANDI_R_BAN_MOD_AG_ES_BAN.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			Long newId = (Long) getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
			logger.info("Nuovo id PBANDI_T_NEWS = "+newId);
			
			String sql;
			sql =     "INSERT INTO  PBANDI_R_BANDO_MOD_AG_ESTR_BAN(\r\n"
					+ "	ID_BANDO_MOD_AG_ESTR_BAN, \r\n"
					+ "	ID_MODALITA_AGEVOLAZIONE, \r\n"
					+ "	ID_BANDO, \r\n"
					+ "	ID_ESTREMI_BANCARI, \r\n"
					+ "	MOLTIPLICATORE, \r\n"
					+ "	DT_INIZIO_VALIDITA, \r\n"
					+ "	ID_UTENTE_INS, \r\n"
					+ "	TIPOLOGIA_CONTO)\r\n"
					+ "VALUES (\r\n"
					+ "	?, \r\n"
					+ "	?, \r\n"
					+ "	?, \r\n"
					+ "	?, \r\n"
					+ "	?, \r\n"
					+ "	SYSDATE, \r\n"
					+ "	?, \r\n"
					+ "	?)";
			
			logger.info("\n"+sql);
			
			Object[] args = new Object[] { 
				newId, 
				idModalitaAgevolazione, 
				idBando, 
				idEstremiBancari, 
				moltiplicatore,
				idUtenteIns, 
				tipologiaConto
			};		
			
			getJdbcTemplate().update(sql, args);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		
	
	}
	
	//Get estremi bancari per amministrativo contabile 
	@Override
	public List<EstremiBancariEstesiDTO> getEstremiBancari(Long idBando)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		

		//Controllo parametri		
		
		List<EstremiBancariEstesiDTO> estremiBancariDTOList = new ArrayList<EstremiBancariEstesiDTO>();
		
		try {
			
			String sql;
			/*
			sql =     "SELECT \r\n"
					+ "	prbaeb.ID_ESTREMI_BANCARI,\r\n"
					+ "	pteb.ID_BANCA,\r\n"
					+ "	pdb.DESC_BANCA, \r\n"
					+ "	pteb.IBAN,\r\n"
					+ "	prbaeb.MOLTIPLICATORE, \r\n"
					+ "	prbaeb.TIPOLOGIA_CONTO,"
					+ "	prbaeb.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	CASE \r\n"
					+ "		WHEN ptmsac.ID_SERVIZIO = 2 AND ptmsac.ID_ENTITA = 128 AND ptmsac.ESITO = 'OK' THEN 1\r\n"
					+ "		ELSE 0\r\n"
					+ "	END sendToAmmCont\r\n"
					+ "FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbaeb\r\n"
					+ "JOIN PBANDI_T_ESTREMI_BANCARI pteb\r\n"
					+ "	ON pteb.ID_ESTREMI_BANCARI =  prbaeb.ID_ESTREMI_BANCARI \r\n"
					+ "JOIN PBANDI_D_BANCA pdb\r\n"
					+ "	ON pteb.ID_BANCA  = pdb.ID_BANCA \r\n"
					+ "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma\r\n"
					+ "	ON  prbaeb.ID_MODALITA_AGEVOLAZIONE  = pdma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "LEFT JOIN PBANDI_T_MON_SERV_AMMVO_CONTAB ptmsac\r\n"
					+ "	ON prbaeb.ID_ESTREMI_BANCARI = ptmsac.ID_TARGET \r\n"
					+ "WHERE prbaeb.ID_BANDO = ? \r\n"
					+ "ORDER BY pdma.ID_MODALITA_AGEVOLAZIONE, pteb.ID_BANCA";
					*/
			
			
			sql =     "SELECT \r\n"
					+ "	MAX(ptmsac.ID_MONIT_AMMVO_CONT) AS ID_MONIT_AMMVO_CONT,\r\n"
					+ "	prbaeb.ID_BANDO,\r\n"
					+ "	prbaeb.ID_ESTREMI_BANCARI,\r\n"
					+ "	pteb.ID_BANCA,\r\n"
					+ "	pdb.DESC_BANCA, \r\n"
					+ "	pteb.IBAN,\r\n"
					+ "	prbaeb.MOLTIPLICATORE, \r\n"
					+ "	prbaeb.TIPOLOGIA_CONTO,\r\n"
					+ "	prbaeb.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbaeb\r\n"
					+ "JOIN PBANDI_T_ESTREMI_BANCARI pteb\r\n"
					+ "	ON pteb.ID_ESTREMI_BANCARI =  prbaeb.ID_ESTREMI_BANCARI \r\n"
					+ "JOIN PBANDI_D_BANCA pdb\r\n"
					+ "	ON pteb.ID_BANCA  = pdb.ID_BANCA \r\n"
					+ "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma\r\n"
					+ "	ON  prbaeb.ID_MODALITA_AGEVOLAZIONE  = pdma.ID_MODALITA_AGEVOLAZIONE \r\n"
					+ "LEFT JOIN PBANDI_T_MON_SERV_AMMVO_CONTAB ptmsac\r\n"
					+ "	ON prbaeb.ID_ESTREMI_BANCARI = ptmsac.ID_TARGET\r\n"
					+ "WHERE prbaeb.ID_BANDO = ? \r\n"
					+ "AND (ptmsac.ESITO IS NULL OR (NOT ptmsac.ESITO = 'OK'))\r\n"
					+ "AND (ptmsac.ID_SERVIZIO IS NULL OR (NOT ptmsac.ID_SERVIZIO = 2))\r\n"
					+ "AND (ptmsac.ID_ENTITA IS NULL OR (NOT ptmsac.ID_ENTITA = 128))\r\n"
					+ "GROUP BY \r\n\r\n"
					+ "	prbaeb.ID_BANDO,"
					+ "	prbaeb.ID_ESTREMI_BANCARI,\r\n"
					+ "	pteb.ID_BANCA,\r\n"
					+ "	pdb.DESC_BANCA, \r\n"
					+ "	pteb.IBAN,\r\n"
					+ "	prbaeb.MOLTIPLICATORE, \r\n"
					+ "	prbaeb.TIPOLOGIA_CONTO,\r\n"
					+ "	prbaeb.ID_MODALITA_AGEVOLAZIONE";
			
			Object[] args = new Object[] { idBando};			
			
			estremiBancariDTOList = getJdbcTemplate().query(sql, args, new BeanRowMapper( EstremiBancariEstesiDTO.class));

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		//TODO MOCK da togliere
		//estremiBancariDTOList  = EstremiBancariDTO.getMockElement();
		
		return estremiBancariDTOList;
	
	}
	
	
	@Override
	public List<BancaSuggestVO> getBancheByDesc(String descrizione)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		

		//Controllo parametri		
		
		List<BancaSuggestVO> bancaSuggest = new ArrayList<BancaSuggestVO>();
		
		try {
			
			String sql;
			sql =   "SELECT \r\n"
					+ "	ID_BANCA, \r\n"
					+ "	DESC_BANCA \r\n"
					+ "FROM PBANDI_D_BANCA \r\n"
					+ "WHERE (DT_FINE_VALIDITA IS NULL OR DT_FINE_VALIDITA > SYSDATE)\r\n"
					+ "AND DESC_BANCA LIKE '%' || ? || '%'"
					+ "AND  ROWNUM <= 100\r\n"
					+ "ORDER BY DESC_BANCA";
			
			Object[] args = new Object[] { descrizione};			
			
			bancaSuggest = getJdbcTemplate().query(sql, args, new BeanRowMapper( BancaSuggestVO.class));

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}		
		
		
		return bancaSuggest;
	}

	@Override
	public AnagraficaDTO getAnagrafica(Long idBando, Long idEnteCompetenza) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		

		//Controllo parametri		
		
		AnagraficaDTO anagrafica = new AnagraficaDTO();
		
		try {
			
			String sql;
			sql =     "SELECT \r\n"
					+ "	ptb.ID_BANDO, \r\n"
					+ "	ptb.TITOLO_BANDO, \r\n"
					+ "	pbl.id_ente_competenza \r\n"
					+ "FROM PBANDI_T_BANDO ptb,\r\n"
					+ "PBANDI_R_BANDO_LINEA_INTERVENT pbi\r\n"
					+ ",PBANDI_R_BANDO_LINEA_ENTE_COMP pbl\r\n"
					+ "WHERE ptb.ID_BANDO = ? \r\n"
					+ "AND pbi.ID_BANDO =ptb.ID_BANDO \r\n"
					+ "AND pbl.PROGR_BANDO_LINEA_INTERVENTO =pbi.PROGR_BANDO_LINEA_INTERVENTO \r\n"
					+ "AND pbl.id_ruolo_ente_competenza = ?";
			
			Object[] args = new Object[] { idBando,idEnteCompetenza };			
			
			 List<AnagraficaDTO> queryResult = getJdbcTemplate().query(sql, args, new BeanRowMapper( AnagraficaDTO.class));
			 if(queryResult == null || queryResult.size() == 0 )
				 anagrafica = null;
			 else
				 anagrafica = queryResult.get(0);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}		
		
		
		return anagrafica;
	}

	public IbanDTO getIbanInfo(Long idBando) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		

		//Controllo parametri		
		
		IbanDTO ibanDTO = new IbanDTO();
		
		try {
			
			String sql;
			sql =     "SELECT prbaeb.ID_MODALITA_AGEVOLAZIONE, \r\n"
					+ "	prbaeb.ID_ESTREMI_BANCARI,\r\n"
					+ "	prbaeb.MOLTIPLICATORE, \r\n"
					+ "	prbaeb.TIPOLOGIA_CONTO,\r\n"
					+ "	pteb.ID_BANCA,\r\n"
					+ "	pteb.IBAN\r\n"
					+ "FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbaeb,\r\n"
					+ "PBANDI_R_BANDO_MODALITA_AGEVOL prbma,\r\n"
					+ "PBANDI_T_ESTREMI_BANCARI pteb,\r\n"
					+ "PBANDI_D_BANCA pdb\r\n"
					+ "WHERE prbaeb.ID_BANDO = prbma.ID_BANDO\r\n"
					+ "AND prbaeb.ID_MODALITA_AGEVOLAZIONE = prbma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "AND pteb.ID_ESTREMI_BANCARI =prbaeb.ID_ESTREMI_BANCARI \r\n"
					+ "AND pdb.ID_BANCA  = pteb.ID_BANCA\r\n"
					+ "AND prbaeb.ID_BANDO = ?";
			
			Object[] args = new Object[] { idBando};			
			
			 List<IbanDTO> queryResult = getJdbcTemplate().query(sql, args, new BeanRowMapper( IbanDTO.class));
			 ibanDTO = queryResult.get(0);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}		
		
		
		return ibanDTO;
	}

	public SendToAmmContDTO getSendToAmmContDTO(Long idBando, Long idModalitaAgevolazione, Long idBanca, String iban,
			Long idServizio, Long idEntita, String esito) throws Exception {



		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		

		//Controllo parametri		
		
		SendToAmmContDTO sendToAmmContDTO = new SendToAmmContDTO();
		
		try {
			
			String sql;
			sql =     "SELECT \r\n"
					+ "	prbaeb.ID_BANDO,\r\n"
					+ "	prbaeb.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	pteb.ID_BANCA,\r\n"
					+ "	prbaeb.ID_ESTREMI_BANCARI,\r\n"
					+ "	pteb.IBAN,	\r\n"
					+ "	CASE \r\n"
					+ "		WHEN ptmsac.ID_SERVIZIO = ? AND ptmsac.ID_ENTITA = ? AND ptmsac.ESITO = ? THEN 1\r\n"
					+ "		ELSE 0\r\n"
					+ "	END SEND_TO_AMM_CONT\r\n"
					+ "FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbaeb\r\n"
					+ "JOIN PBANDI_T_ESTREMI_BANCARI pteb\r\n"
					+ "	ON pteb.ID_ESTREMI_BANCARI =  prbaeb.ID_ESTREMI_BANCARI \r\n"
					+ "JOIN PBANDI_D_BANCA pdb\r\n"
					+ "	ON pteb.ID_BANCA  = pdb.ID_BANCA \r\n"
					+ "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma\r\n"
					+ "	ON  prbaeb.ID_MODALITA_AGEVOLAZIONE  = pdma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "LEFT JOIN PBANDI_T_MON_SERV_AMMVO_CONTAB ptmsac\r\n"
					+ "	ON prbaeb.ID_ESTREMI_BANCARI = ptmsac.ID_TARGET \r\n"
					+ "WHERE prbaeb.ID_BANDO = ? \r\n"
					+ "AND prbaeb.ID_MODALITA_AGEVOLAZIONE = ? \r\n"
					+ "AND pteb.IBAN = ? \r\n"
					+ "ORDER BY pteb.ID_BANCA";
			
			Object[] args = new Object[] { idServizio, idEntita, esito, idBando, idModalitaAgevolazione, iban };	
			
			logger.info(prf + " Query \n\n" + sql + "\n");
			
			 List<SendToAmmContDTO> ResultList = getJdbcTemplate().query(sql, args, new BeanRowMapper( SendToAmmContDTO.class));
			 
			 if(ResultList.size() == 0)
				 throw new Exception ("Nessun iban trovato con i parametri inseriti");
			 
			 sendToAmmContDTO = ResultList.get(0);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}		
		
		
		return sendToAmmContDTO;
		
	}

	public void removeEstremiBancari(Long idEstremiBancari) throws Exception { 


		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		
		try {
			
			String sql;
			sql =     "DELETE FROM PBANDI.PBANDI_T_ESTREMI_BANCARI pteb\r\n"
					+ "	WHERE pteb.ID_ESTREMI_BANCARI = ?";
			
			Object[] args = new Object[] { idEstremiBancari };	
			
			logger.info(prf + " Query \n\n" + sql + "\n");
			
			getJdbcTemplate().update(sql, args);			 
			

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}		
		
		
	}
	
	public void removeBandoModAgEstrBan(Long idEstremiBancari) throws Exception { 


		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		
		try {
			
			String sql;
			sql =     "DELETE FROM PBANDI.PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb\r\n"
					+ "	WHERE prbmaeb.ID_ESTREMI_BANCARI = ?";
			
			Object[] args = new Object[] { idEstremiBancari };	
			
			logger.info(prf + " Query \n\n" + sql + "\n");
			
			getJdbcTemplate().update(sql, args);			 
			

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}		
		
		
	}
	
	
	///////////////////////////////   TAB MONITORAGGIO TEMPI //////////////////////////////////////	

	@Override
	public List<GenericResponseDTO> getParametriMonitoraggioTempi()throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		
		List<GenericResponseDTO> resultList = new ArrayList<GenericResponseDTO>();
		
		try {
			
			String sql;
			
			
			sql =   "SELECT \r\n"
					+ "	ID_PARAMETRO_MONIT AS id,\r\n"
					+ "	DESC_PARAMETRO_MONIT AS descrizione,\r\n"
					+ "	DESC_BREVE_PARAMETRO_MONIT AS code \r\n"
					+ "FROM PBANDI_C_PARAMETRI_MONIT pcpm ";
			
			Object[] args = new Object[] {};			
			
			resultList = getJdbcTemplate().query(sql, args, new BeanRowMapper( GenericResponseDTO.class));

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
	
		return resultList;
	
	}
	
	
	@Override
	public List<ParametriMonitoraggioTempiAssociatiDTO> getParametriMonitoraggioTempiAssociatiByBando(Long idBandoLinea)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		
		List<ParametriMonitoraggioTempiAssociatiDTO> resultList = new ArrayList<ParametriMonitoraggioTempiAssociatiDTO>();
		
		try {
			
			String sql;
			sql =   "SELECT \r\n"
					+ "ID_PARAM_MONIT_BANDO_LINEA,\r\n"
					+ "ID_PARAMETRO_MONIT,\r\n"
					+ "PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "NUM_GIORNI,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "DT_FINE_VALIDITA\r\n"
					+ "FROM PBANDI_R_PARAM_MONIT_BANDO_LIN\r\n"
					+ "WHERE PROGR_BANDO_LINEA_INTERVENTO = ?";
			
			sql =   "SELECT \r\n"
					+ "prpmb.ID_PARAM_MONIT_BANDO_LINEA,\r\n"
					+ "prpmb.ID_PARAMETRO_MONIT,\r\n"
					+ "prpmb.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "prpmb.NUM_GIORNI,\r\n"
					+ "prpmb.DT_INIZIO_VALIDITA,\r\n"
					+ "prpmb.DT_FINE_VALIDITA,\r\n"
					+ "pcpm.DESC_BREVE_PARAMETRO_MONIT,\r\n"
					+ "pcpm.DESC_PARAMETRO_MONIT \r\n"
					+ "FROM PBANDI_R_PARAM_MONIT_BANDO_LIN prpmb\r\n"
					+ "JOIN PBANDI_C_PARAMETRI_MONIT pcpm \r\n"
					+ "	ON prpmb.ID_PARAMETRO_MONIT = pcpm.ID_PARAMETRO_MONIT \r\n"
					+ "WHERE PROGR_BANDO_LINEA_INTERVENTO = ?\r\n"
					+ "AND DT_FINE_VALIDITA IS NULL";
			
			Object[] args = new Object[] { idBandoLinea };			
			
			resultList = getJdbcTemplate().query(sql, args, new BeanRowMapper( ParametriMonitoraggioTempiAssociatiDTO.class));

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
	
		return resultList;
	
	}

	@Override
	public ParametriMonitoraggioTempiAssociatiDTO getParametriMonitoraggioTempiAssociatiById(Long idParamMonitBandoLinea)throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		
		ParametriMonitoraggioTempiAssociatiDTO result = null;
		
		try {
			
			String sql;
			sql =     "SELECT \r\n"
					+ "ID_PARAM_MONIT_BANDO_LINEA,\r\n"
					+ "ID_PARAMETRO_MONIT,\r\n"
					+ "PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "NUM_GIORNI,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "DT_FINE_VALIDITA\r\n"
					+ "FROM PBANDI_R_PARAM_MONIT_BANDO_LIN\r\n"
					+ "WHERE ID_PARAM_MONIT_BANDO_LINEA = ?";
			
			Object[] args = new Object[] { idParamMonitBandoLinea };			
			
			List<ParametriMonitoraggioTempiAssociatiDTO> resultList;
			resultList = getJdbcTemplate().query(sql, args, new BeanRowMapper( ParametriMonitoraggioTempiAssociatiDTO.class));
			
			if (resultList != null && resultList.size() > 0)
				result = resultList.get(0);
			
			

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
	
		return result;
	
	}
	
	@Override
	public Long insertParametriMonitoraggioTempiAssociati (ParametriMonitoraggioTempiAssociatiDTO parametriMonitoraggioTempi)throws Exception{
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		Long idParamMonitBandoLinea;
		
		try {
									
			String sqlSeq = "select SEQ_PBANDI_R_PAR_MONIT_BAN_LIN.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			idParamMonitBandoLinea = (Long) getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
			logger.info("Nuovo id PBANDI_R_PARAM_MONIT_BANDO_LIN = "+idParamMonitBandoLinea);
			
			String sql;
			sql =     "INSERT INTO PBANDI_R_PARAM_MONIT_BANDO_LIN(\r\n"
					+ "	ID_PARAM_MONIT_BANDO_LINEA,\r\n"
					+ "	ID_PARAMETRO_MONIT,\r\n"
					+ "	PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "	NUM_GIORNI,\r\n"
					+ "	DT_INIZIO_VALIDITA,\r\n"
					+ "	DT_FINE_VALIDITA \r\n"
					+ ")\r\n"
					+ "VALUES(\r\n"
					+ "	?,\r\n"
					+ "	?,\r\n"
					+ "	?,\r\n"
					+ "	?,\r\n"
					+ "	SYSDATE,\r\n"
					+ "	NULL \r\n"
					+ ")";
			
			logger.info("\n"+sql);
			
			Object[] args = new Object[] { 
											idParamMonitBandoLinea, 
											parametriMonitoraggioTempi.getIdParametroMonit(), 
											parametriMonitoraggioTempi.getProgrBandoLineaIntervento(), 
											parametriMonitoraggioTempi.getNumGiorni() };		
			
			getJdbcTemplate().update(sql, args);
			
			parametriMonitoraggioTempi.setIdParamMonitBandoLinea(idParamMonitBandoLinea);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		return idParamMonitBandoLinea;
	
		
	}
			
	@Override
	public Long logicDeleteParametriMonitoraggioTempiAssociati (ParametriMonitoraggioTempiAssociatiDTO parametriMonitoraggioTempi)throws Exception{
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		Long idParamMonitBandoLinea;
		
		try {
			
			String sql;
			sql =     "UPDATE PBANDI_R_PARAM_MONIT_BANDO_LIN\r\n"
					+ "SET \r\n"
					+ "	DT_FINE_VALIDITA = SYSDATE\r\n"
					+ "WHERE ID_PARAM_MONIT_BANDO_LINEA = ?";
			
			logger.info("\n"+sql);
			
			Object[] args = new Object[] { parametriMonitoraggioTempi.getIdParamMonitBandoLinea()};		
			
			getJdbcTemplate().update(sql, args);
			
			
		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		return parametriMonitoraggioTempi.getProgrBandoLineaIntervento();
	
		
	}


	public List<ParametriMonitoraggioTempiAssociatiDTO> getParametriMonitoraggioTempiAssociati(Long idParametroMonit, Long progrBandoLineaIntervento) throws Exception{
		
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		
		List<ParametriMonitoraggioTempiAssociatiDTO> resultList = new ArrayList<ParametriMonitoraggioTempiAssociatiDTO>();
		
		try {
			
			String sql;			
			
			sql =   "SELECT \r\n"
					+ "prpmb.ID_PARAM_MONIT_BANDO_LINEA,\r\n"
					+ "prpmb.ID_PARAMETRO_MONIT,\r\n"
					+ "prpmb.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
					+ "prpmb.NUM_GIORNI,\r\n"
					+ "prpmb.DT_INIZIO_VALIDITA,\r\n"
					+ "prpmb.DT_FINE_VALIDITA,\r\n"
					+ "pcpm.DESC_BREVE_PARAMETRO_MONIT,\r\n"
					+ "pcpm.DESC_PARAMETRO_MONIT \r\n"
					+ "FROM PBANDI_R_PARAM_MONIT_BANDO_LIN prpmb\r\n"
					+ "JOIN PBANDI_C_PARAMETRI_MONIT pcpm \r\n"
					+ "	ON prpmb.ID_PARAMETRO_MONIT = pcpm.ID_PARAMETRO_MONIT \r\n"
					+ "WHERE prpmb.ID_PARAMETRO_MONIT = ? \r\n"
					+ "AND prpmb.PROGR_BANDO_LINEA_INTERVENTO = ? \r\n"
					+ "AND DT_FINE_VALIDITA IS NULL";
			
			Object[] args = new Object[] { idParametroMonit, progrBandoLineaIntervento };			
			
			resultList = getJdbcTemplate().query(sql, args, new BeanRowMapper( ParametriMonitoraggioTempiAssociatiDTO.class));
			//getJdbcTemplate().queryForObject(sql, null)

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
	
		return resultList;
	}


	public boolean salvaFlagMonitoraggioTempi(Long progrBandoLineaIntervento, String flagMonitoraggioTempi) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");
		
		try {
			
			String update = "update PBANDI_R_BANDO_LINEA_INTERVENT set FLAG_MONITORAGGIO_TEMPI =?\r\n"
					+ " where PROGR_BANDO_LINEA_INTERVENTO =?";
			
			getJdbcTemplate().update(update, new Object[] { flagMonitoraggioTempi.trim(), progrBandoLineaIntervento });
			
		} catch (Exception e) {
			logger.error(prf + e);
			return false;
		}
		
		return true;
	}
	
	
	



}
