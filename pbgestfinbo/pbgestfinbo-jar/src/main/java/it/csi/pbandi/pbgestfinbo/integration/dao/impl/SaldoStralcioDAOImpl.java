/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.security.InvalidParameterException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbgestfinbo.business.service.impl.AmministrativoContabileServiceImpl;
import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoSaldoStralcioDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SaldoStralcioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AttivitaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.SaldoStralcioRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoSaldoStralcioRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.SaldoStralcioVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

public class SaldoStralcioDAOImpl extends JdbcDaoSupport implements SaldoStralcioDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public SaldoStralcioDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	@Autowired
	private AmministrativoContabileServiceImpl amministrativoContabileServiceImpl;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Boolean insertSaldoStralcio(SaldoStralcioVO saldoStralcioVO, Long idUtente, Long idProgetto ,int idModalitaAgev) throws Exception {
		String prf = "[SaldoStralcioDAOImpl::insertSaldoStralcio]";
		LOG.info(prf + " BEGIN");
		
		Boolean result =  true; 
		
		if(saldoStralcioVO ==null) {
			throw new InvalidParameterException("Saldo o stralcio non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		
		try {
			String sqlSeq = "SELECT seq_PBANDI_T_SALDO_STRALCIO.nextval FROM dual";
			long idSaldoStralcio = (long) getJdbcTemplate().queryForObject(sqlSeq.toString(), long.class);
			
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt3 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt4 = new SimpleDateFormat("yyyy-MM-dd");
			
			String dataProposta = null;  
		
			if(saldoStralcioVO.getDataProposta()!=null) {	
				dataProposta = dt.format(saldoStralcioVO.getDataProposta()); 
			}
			
			String dataAccetaz = null;
			if(saldoStralcioVO.getDataAcettazione()!=null) {	
				 dataAccetaz = dt1.format(saldoStralcioVO.getDataAcettazione()); 
			}
			
			String dataEsito = null;
			if(saldoStralcioVO.getDataEsito()!=null) {	
				dataEsito = dt2.format(saldoStralcioVO.getDataEsito()); 
			}
			
			String dataPagamConf = null;
			if(saldoStralcioVO.getDataPagamConfidi()!=null) {	
				 dataPagamConf = dt3.format(saldoStralcioVO.getDataPagamConfidi()); 
			}
			
			String dataPagamDeb = null;
			if(saldoStralcioVO.getDataPagamDebitore()!=null) {	
				 dataPagamDeb = dt4.format(saldoStralcioVO.getDataPagamDebitore()); 
			}			
			
			String sql = "INSERT INTO PBANDI_T_SALDO_STRALCIO ("
					
					+ "ID_SALDO_STRALCIO,\r\n"
					+ "ID_PROGETTO,\r\n"
					+ "ID_ATTIVITA_SALDO_STRALCIO,\r\n"
					+ "DT_PROPOSTA,\r\n"
					+ "DT_ACCETTAZIONE,\r\n"
					+ "IMP_DEBITORE,\r\n"
					+ "IMP_CONFIDI,\r\n"
					+ "ID_ATTIVITA_ESITO,\r\n"
					+ "DT_ESITO,\r\n"
					+ "DT_PAGAM_DEBITORE,\r\n"
					+ "DT_PAGAM_CONFIDI,\r\n"
					+ "ID_ATTIVITA_RECUPERO,\r\n"
					+ "IMP_RECUPERATO,\r\n"
					+ "IMP_PERDITA,\r\n"
					+ "NOTE,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INSERIMENTO, ID_MODALITA_AGEVOLAZIONE, FLAG_AGEVOLAZIONE, IMP_DISIMPEGNO\r\n"
					+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate, ?, ?, ?)"; 
			
			getJdbcTemplate().update(sql, new Object[]{
					idSaldoStralcio, 
					idProgetto,
					saldoStralcioVO.getIdAttivitaSaldoStralcio(),
					(dataProposta!=null && dataProposta.trim().length()>0) ? Date.valueOf(dataProposta): null,
					(dataAccetaz!=null && dataAccetaz.trim().length()>0) ? Date.valueOf(dataAccetaz) : null,
					saldoStralcioVO.getImpDebitore(),
					saldoStralcioVO.getImpConfindi(),
					saldoStralcioVO.getIdAttivitaEsito(),
					(dataEsito!=null && dataEsito.trim().length()>0) ? Date.valueOf(dataEsito) : null,
					(dataPagamDeb!=null && dataPagamDeb.trim().length()>0) ? Date.valueOf(dataPagamDeb) : null,
					(dataPagamConf!=null && dataPagamConf.trim().length()>0) ? Date.valueOf(dataPagamConf) : null,
					saldoStralcioVO.getIdAttivitaRecupero(),
					saldoStralcioVO.getImpRecuperato(),
					saldoStralcioVO.getImpPerdita(),
					saldoStralcioVO.getNote(),
					idUtente, idModalitaAgev, (saldoStralcioVO.getFlagAgevolazione() ? "S" : "N"),
					saldoStralcioVO.getImpDisimpegno()
			});
			
			LOG.info(prf + "  param [IdSaldoStralcio] = " + idSaldoStralcio );
			LOG.info(prf + "  param [IdProgetto] = " + idProgetto );
			LOG.info(prf + "  param [IdAttivitaSaldoStralcio] = " + saldoStralcioVO.getIdAttivitaSaldoStralcio());
			LOG.info(prf + "  param [DataProposta] = " + saldoStralcioVO.getDataProposta());
			LOG.info(prf + "  param [DataAcettazione] = " + saldoStralcioVO.getDataAcettazione());
			LOG.info(prf + "  param [ImpDebitore] = " + saldoStralcioVO.getImpDebitore());
			LOG.info(prf + "  param [ImpConfindi] = " + saldoStralcioVO.getImpConfindi());
			LOG.info(prf + "  param [IdAttivitaEsito] = " + saldoStralcioVO.getIdAttivitaEsito());
			LOG.info(prf + "  param [DataEsito] = " + saldoStralcioVO.getDataEsito());
			LOG.info(prf + "  param [DataPagamDebitore] = " + saldoStralcioVO.getDataPagamDebitore());
			LOG.info(prf + "  param [DataPagamConfidi] = " + saldoStralcioVO.getDataPagamConfidi());
			LOG.info(prf + "  param [IdAttivitaRecupero] = " + saldoStralcioVO.getIdAttivitaRecupero());
			LOG.info(prf + "  param [ImpRecuperato] = " + saldoStralcioVO.getImpRecuperato());
			LOG.info(prf + "  param [ImpPerdita] = " + saldoStralcioVO.getImpPerdita());
			LOG.info(prf + "  param [ImpDisimpegno] = " + saldoStralcioVO.getImpDisimpegno());
			LOG.info(prf + "  param [Note] = " + saldoStralcioVO.getNote());
			LOG.info(prf + "  param [FlagAgevolazione] = " + saldoStralcioVO.getFlagAgevolazione() + " => " + (saldoStralcioVO.getFlagAgevolazione() ? "S" : "N"));
			LOG.info(prf + "  param [IdUtente] = " + idUtente);
			
			
			// Se l'esito del saldo stralcio è accolto allora chiamo amm contabile
			// Chiamata ad insert su t_distinta per amm.vo contabile
			if(saldoStralcioVO.getIdAttivitaEsito() == (long)14 && checkCallToAmmContabile(idProgetto, saldoStralcioVO.getIdSaldoStralcio())) {
				LOG.info(prf + " L'esito è accolto e il saldo stralcio non è già stato inserito.");
				setDistinta(saldoStralcioVO.getIdProgetto(), idUtente, idSaldoStralcio,saldoStralcioVO, idModalitaAgev);
			}

		} catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SALDO_STRALCIO", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		//LOG.info(prf + " END");
		
		return result;
	}

	
	// Controlla se è gia stato chiamato amm.vo contabile per questo saldo stralcio
	// E comunque Franky è un grande!
	private boolean checkCallToAmmContabile(Long idProgetto, Long idSaldoStralcio) {
		String prf = "[SaldoStralcioDAOImpl::insertSaldoStralcio::checkCallToAmmContabile]";
		LOG.info(prf + " BEGIN");
		
		
		LOG.info(prf + " Controllo per idProgetto: " + idProgetto + ", idSaldoStralcio: " + idSaldoStralcio);

		//se è gia stato inserito un saldo stralcio con questo id e questo progetto nella tabella t_monit_amm_contabile torno false

		// Se l'id è nullo, sicuramente non è stato inserito
		if(idSaldoStralcio==null) {
			LOG.info(prf + " idSaldoStralcio nullo, non già inserito");
			return true;
		} 
		List<Long> idMonitorraggi = new ArrayList<Long>();
		try {


			String checkAmmContabile ="SELECT ptmsac.ID_MONIT_AMMVO_CONT\r\n"
					+ "FROM PBANDI_T_MON_SERV ptmsac \r\n"
					+ "LEFT JOIN PBANDI_T_DISTINTA ptd ON ptmsac.ID_TARGET  = ptd.ID_DISTINTA \r\n"
					+ "LEFT JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA  = ptd.ID_DISTINTA \r\n"
					+ "LEFT JOIN PBANDI_T_SALDO_STRALCIO ptss ON ptss.ID_SALDO_STRALCIO  = ptdd.ID_TARGET \r\n"
					+ "WHERE ptmsac.ID_ENTITA = (SELECT entita.id_entita AS idEntita \r\n"
					+ "FROM PBANDI_C_ENTITA entita \r\n"
					+ "WHERE entita.nome_entita = 'PBANDI_T_DISTINTA')\r\n"
					+ "AND ptd.ID_ENTITA =(SELECT entita.id_entita AS idEntita \r\n"
					+ "FROM PBANDI_C_ENTITA entita \r\n"
					+ "WHERE entita.nome_entita = 'PBANDI_T_SALDO_STRALCIO')\r\n"
					+ "AND ptss.ID_SALDO_STRALCIO =?\r\n"
					+ "AND ptss.ID_PROGETTO =?";

			idMonitorraggi = getJdbcTemplate().queryForList(checkAmmContabile, Long.class, 
					new Object [] {idSaldoStralcio, idProgetto});

		} catch (Exception e) {
			logger.info(prf + "exception: " + e );
		}

		if(idMonitorraggi.size() < 1) {
			LOG.info(prf + " Saldo stralcio NON presente");
			LOG.info(prf + " END");
			return true; // Non è stato inserito
		}
			

		LOG.info(prf + " Saldo stralcio presente");
		LOG.info(prf + " END");
		return false; // E' stato inserito
	}

	@Override
	public Boolean modificaSaldoStralcio(SaldoStralcioVO saldoStralcioVO, Long idUtente, Long idProgetto,
			Long idSS ,int idModalitaAgev) {
		
		String prf = "[SaldoStralcioDAOImpl::modificaSaldoStralcio ]";
		LOG.info(prf + "BEGIN");
		Boolean result =  true; 
		
		if(saldoStralcioVO ==null) {
			throw new InvalidParameterException("Saldo o stralcio non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		saldoStralcioVO.setIdAttivitaEsito((saldoStralcioVO.getIdAttivitaEsito()!=0)?saldoStralcioVO.getIdAttivitaEsito():null);
		saldoStralcioVO.setIdAttivitaRecupero((saldoStralcioVO.getIdAttivitaRecupero()!=0)?saldoStralcioVO.getIdAttivitaRecupero():null);
		saldoStralcioVO.setIdAttivitaSaldoStralcio((saldoStralcioVO.getIdAttivitaSaldoStralcio()!=0)?saldoStralcioVO.getIdAttivitaSaldoStralcio():null);
		saldoStralcioVO.setIdSaldoStralcio(idSS);
		try {
			
			String sqlmodifica = "UPDATE PBANDI_T_SALDO_STRALCIO "
					+ " SET DT_FINE_VALIDITA = sysdate,"
					+ " DT_AGGIORNAMENTO = sysdate,"
					+ " ID_UTENTE_AGG = " + idUtente
					+ " WHERE ID_SALDO_STRALCIO = " + idSS;
			
			
			getJdbcTemplate().update(sqlmodifica); 
				
			result =  insertSaldoStralcio(saldoStralcioVO, idUtente, idProgetto, idModalitaAgev);
						
		} catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SALDO_STRALCIO", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}
	
	// Anche qui, Franky è un grande!
	@Transactional
	public Long setDistinta(Long idProgetto, Long idUtente, Long idPassaggioPerdita, SaldoStralcioVO saldoStralcio, int idModalitaAgev) throws Exception {
		String prf = "[SaldoStralcioDAOImpl::insertSaldoStralcio::setDistinta]";
		LOG.info(prf + " BEGIN");
		
		int up1 = 0;
		int up2 = 0; 
		
		try {
			
			// get idDistinta
			String sqlSeq = "SELECT SEQ_PBANDI_T_DISTINTA.nextval FROM dual";
			Long idDistinta = getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
			LOG.info(prf + " newIdDistinta: " + idDistinta);
			
			// get idDistintaDett
			String sqlSeq2 = "SELECT SEQ_PBANDI_T_DISTINTA_DETT.nextval FROM dual";
			Long idDistintaDett = getJdbcTemplate().queryForObject(sqlSeq2.toString(), Long.class);
			LOG.info(prf + " newIdDistintaDett: " + idDistintaDett);
			
			// get idModalitaAgevolazione da chiarire con amministrativo contabile, 
//			String sqlAge= "SELECT DISTINCT prbmaeb.ID_MODALITA_AGEVOLAZIONE \r\n"
//					+ "FROM PBANDI_T_BANDO ptb \r\n"
//					+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptb.ID_BANDO = prbli.ID_BANDO \r\n"
//					+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \r\n"
//					+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
//					+ "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
//					+ "LEFT JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.ID_BANDO = ptb.ID_BANDO \r\n"
//					+ "WHERE prsp.ID_PROGETTO = " + idProgetto+"\r\n"
//					+ "AND ROWNUM=1";
//			int idModalitaAgevolazione = getJdbcTemplate().queryForObject(sqlAge.toString(), Integer.class);

			String sqlAge = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
					"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
					"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
			int idModalitaAgevolazioneRif = getJdbcTemplate().queryForObject(sqlAge, Integer.class, idModalitaAgev);
			LOG.info(prf + " idModalitaAgevolazioneRif: " + idModalitaAgevolazioneRif);
			

			String sqlEntita = "SELECT entita.id_entita AS idEntita \n" +
					"FROM PBANDI_C_ENTITA entita \n" +
					"WHERE entita.nome_entita = 'PBANDI_T_SALDO_STRALCIO'";
			Long idEntita = getJdbcTemplate().queryForObject(sqlEntita, Long.class);
			LOG.info(prf + " idEntita: " + idEntita);
				

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
			
			up1 = getJdbcTemplate().update(insert, new Object[] {idDistinta, idEntita, 3, idModalitaAgev, 8, idUtente});
			LOG.info(prf + " Distinta inserita - up1: " + up1);

			Long rigaDistinta = getJdbcTemplate().queryForObject("SELECT COUNT(1) + 1 FROM PBANDI_T_DISTINTA_DETT ptdd WHERE ptdd.ID_DISTINTA = ?", Long.class, idDistinta);
			LOG.info(prf + " newRigaDistinta: " + rigaDistinta);

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
			LOG.info(prf + " Distinta dettaglio inserita - up2: " + up2);
		
			//dopo aver inserito su t_distinte, chiama il servizio di amm.vo cont
			Boolean ammRes = false;
			// Se si tratta di Garanzia (id 10), come importo passo solo Disimpegno
			// Negli altri casi sarà importo confidi + importo debitore
			if(idModalitaAgevolazioneRif == 10) {
				LOG.info(prf + " Chiamo amm.vo - si tratta di garanzia (10)");
				ammRes = amministrativoContabileServiceImpl.callToDistintaErogazioneSaldoStralcio(idDistinta.intValue(), rigaDistinta.intValue(), idProgetto.intValue(),
						saldoStralcio.getDataEsito(), (saldoStralcio.getImpDisimpegno() != null) ? saldoStralcio.getImpDisimpegno().doubleValue() : (double) 0,
								(double) 0, idModalitaAgev, idModalitaAgevolazioneRif, idUtente);

			} else {
				LOG.info(prf + " Chiamo amm.vo - NON si tratta di garanzia - idModAgevRif: " + idModalitaAgevolazioneRif);
				ammRes = amministrativoContabileServiceImpl.callToDistintaErogazioneSaldoStralcio(idDistinta.intValue(), rigaDistinta.intValue(), idProgetto.intValue(),
						saldoStralcio.getDataEsito(), (saldoStralcio.getImpConfindi() != null) ? saldoStralcio.getImpConfindi().doubleValue() : (double) 0,
								(saldoStralcio.getImpDebitore() != null) ? saldoStralcio.getImpDebitore().doubleValue() : (double) 0, idModalitaAgev, idModalitaAgevolazioneRif, idUtente);
			}
			
			
			LOG.info(prf + "Amm.vo cont. Res: " + ammRes);
			
			if(!ammRes) {
				throw new Exception();
			}
			
		} catch (Exception e) {
			logger.error(e);
			return (long) -1;
		}	
		
		return (long) (up1 - up2);
	}

/*public Long getIdEntitaSaldoStralcio() throws Exception {
	String prf = "[SaldoStralcioDAOImpl::insertSaldoStralcio::setDistinta::]";
	LOG.info(prf + " BEGIN");
	logger.info(prf + " BEGIN");

	Long idEntita;

	try {

		idEntita = getJdbcTemplate().queryForObject(
				"SELECT entita.id_entita AS idEntita \n" +
						"FROM PBANDI_C_ENTITA entita \n" +
						"WHERE entita.nome_entita = 'PBANDI_T_SALDO_STRALCIO'",
				Long.class
		);

	} catch (Exception e) {
		String msg = "Errore l'esecuzione della query";
		logger.error(prf + msg, e);
		throw new Exception(msg, e);
	} finally {
		logger.info(prf + " END");
	}

	return idEntita;
}*/

	@Override
	public SaldoStralcioVO getSaldoStralcio(Long idSaldoStralcio, int idModalitaAgev) {
		
		String prf = "[SaldoStralcioDAOImpl::getSaldoStralcio ]";
		LOG.info(prf + "BEGIN");
		
		if(idSaldoStralcio == null ) {
			throw new InvalidParameterException("idSaldoStralcio non valorizzato.");		
		}
		
		SaldoStralcioVO saldoStralcio = new SaldoStralcioVO(); 
		try {
			
			String sql = "SELECT * FROM PBANDI_T_SALDO_STRALCIO "
					+ " WHERE ID_SALDO_STRALCIO = " + idSaldoStralcio; 
			
		saldoStralcio = (SaldoStralcioVO) getJdbcTemplate().queryForObject(sql, new SaldoStralcioRowMapper()); 
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SALDO_STRALCIO", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return saldoStralcio;
	}

	@Override
	public ArrayList<AttivitaDTO> getAttivitaSaldoStralcio() {
		
		String prf = "[SaldoStralcioDAOImpl::getListaTipoSaldoStralcio ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "SELECT DISTINCT DESC_ATT_MONIT_CRED, ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED pdamc \r\n"
					+ "WHERE PDAMC .ID_TIPO_MONIT_CRED = 10 \r\n"
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
	public ArrayList<AttivitaDTO> getAttivitaEsito() {
		
		String prf = "[SaldoStralcioDAOImpl::getListaEsito ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "SELECT DISTINCT DESC_ATT_MONIT_CRED, ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED pdamc \r\n"
					+ "WHERE PDAMC .ID_TIPO_MONIT_CRED = 4 \r\n"
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
		
		String prf = "[SaldoStralcioDAOImpl::getListaRecupero ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "SELECT DISTINCT DESC_ATT_MONIT_CRED, ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED pdamc \r\n"
					+ "WHERE PDAMC .ID_TIPO_MONIT_CRED = 5 \r\n"
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
	public ArrayList<StoricoSaldoStralcioDTO> getListaSaldoStralcio(Long idProgetto ,int idModalitaAgev) {
		
		String prf = "[SaldoStralcioDAOImpl::getListaSaldoStralcio ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList<StoricoSaldoStralcioDTO> storico =  new ArrayList<StoricoSaldoStralcioDTO>();
		
		try {
			
			String sql ="SELECT\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    ptss.DT_INSERIMENTO,\r\n"
					+ "    ptss.DT_ACCETTAZIONE,\r\n"
					+ "    saldo.descSaldoStralcio,\r\n"
					+ "    esito.descEsito,\r\n"
					+ "    recupero.descRecupero,\r\n"
					+ "    ptss.DT_ESITO,\r\n"
					+ "    ptss.DT_PAGAM_CONFIDI,\r\n"
					+ "    ptss.DT_PAGAM_DEBITORE,\r\n"
					+ "    ptss.IMP_CONFIDI,\r\n"
					+ "    ptss.ID_SALDO_STRALCIO,\r\n"
					+ "    ptss.IMP_DEBITORE,\r\n"
					+ "    ptss.IMP_PERDITA,\r\n"
					+ "    ptss.IMP_RECUPERATO,\r\n"
					+ "    ptss.NOTE,\r\n"
					+ "    ptss.DT_PROPOSTA,\r\n"
					+ "    ptss.ID_ATTIVITA_ESITO,\r\n"
					+ "    ptss.ID_ATTIVITA_RECUPERO,\r\n"
					+ "    ptss.FLAG_AGEVOLAZIONE,\r\n"
					+ "    ptss.IMP_DISIMPEGNO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_SALDO_STRALCIO ptss\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descSaldoStralcio\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED\r\n"
					+ "    ) saldo ON saldo.ID_ATTIVITA_MONIT_CRED = PTSS.ID_ATTIVITA_SALDO_STRALCIO\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descRecupero\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED\r\n"
					+ "    ) recupero ON recupero.ID_ATTIVITA_MONIT_CRED = PTSS.ID_ATTIVITA_RECUPERO\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descEsito\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED\r\n"
					+ "    ) esito ON esito.ID_ATTIVITA_MONIT_CRED = PTSS.ID_ATTIVITA_ESITO\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = PTSS.ID_UTENTE_INS\r\n"
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
					+ "    ptss.ID_PROGETTO = ?\r\n"
					+ "    and ptss.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND PTSS.DT_FINE_VALIDITA IS NULL\r\n"
					+ "ORDER BY\r\n"
					+ "    DT_ESITO DESC,\r\n"
					+ "    ptss.ID_SALDO_STRALCIO DESC ";
				
			
			storico = (ArrayList<StoricoSaldoStralcioDTO>) getJdbcTemplate().query(sql, new StoricoSaldoStralcioRowMapper(), new Object[] {
					idProgetto, idModalitaAgev
			});
			
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SALDO_STRALCIO", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;
	}

	@Override
	public ArrayList<StoricoSaldoStralcioDTO> getStorico(Long idProgetto, int idModalitaAgev) {
		
		String prf = "[SaldoStralcioDAOImpl::getStorico ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList<StoricoSaldoStralcioDTO> storico =  new ArrayList<StoricoSaldoStralcioDTO>();
			try {
			
			String sql = "SELECT\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    ptss.DT_INSERIMENTO,\r\n"
					+ "    ptss.DT_ACCETTAZIONE,\r\n"
					+ "    saldo.descSaldoStralcio,\r\n"
					+ "    esito.descEsito,\r\n"
					+ "    recupero.descRecupero,\r\n"
					+ "    ptss.DT_ESITO,\r\n"
					+ "    ptss.DT_PAGAM_CONFIDI,\r\n"
					+ "    ptss.DT_PAGAM_DEBITORE,\r\n"
					+ "    ptss.IMP_CONFIDI,\r\n"
					+ "    ptss.ID_SALDO_STRALCIO,\r\n"
					+ "    ptss.IMP_DEBITORE,\r\n"
					+ "    ptss.IMP_PERDITA,\r\n"
					+ "    ptss.IMP_RECUPERATO,\r\n"
					+ "    ptss.NOTE,\r\n"
					+ "    ptss.DT_PROPOSTA,\r\n"
					+ "    ptss.ID_ATTIVITA_ESITO,\r\n"
					+ "    ptss.ID_ATTIVITA_RECUPERO,\r\n"
					+ "    ptss.FLAG_AGEVOLAZIONE\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_SALDO_STRALCIO ptss\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descSaldoStralcio\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED\r\n"
					+ "    ) saldo ON saldo.ID_ATTIVITA_MONIT_CRED = PTSS.ID_ATTIVITA_SALDO_STRALCIO\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descRecupero\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED\r\n"
					+ "    ) recupero ON recupero.ID_ATTIVITA_MONIT_CRED = PTSS.ID_ATTIVITA_RECUPERO\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT\r\n"
					+ "            DISTINCT ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "            DESC_ATT_MONIT_CRED AS descEsito\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_D_ATTIVITA_MONIT_CRED\r\n"
					+ "    ) esito ON esito.ID_ATTIVITA_MONIT_CRED = PTSS.ID_ATTIVITA_ESITO\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = PTSS.ID_UTENTE_INS\r\n"
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
					+ "    ptss.ID_PROGETTO = ?\r\n"
					+ "    and ptss.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND PTSS.DT_FINE_VALIDITA IS not NULL\r\n"
					+ "ORDER BY\r\n"
					+ "    DT_ESITO DESC,\r\n"
					+ "    ptss.ID_SALDO_STRALCIO DESC"; 

			
			storico = (ArrayList<StoricoSaldoStralcioDTO>) getJdbcTemplate().query(sql, new StoricoSaldoStralcioRowMapper(), new Object[] {
					idProgetto, idModalitaAgev
			});
			
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SALDO_STRALCIO", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;

	}

}
