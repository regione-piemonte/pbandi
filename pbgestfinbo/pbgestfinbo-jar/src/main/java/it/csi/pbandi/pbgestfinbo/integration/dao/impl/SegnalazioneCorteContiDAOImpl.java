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

import it.csi.pbandi.filestorage.util.Constants;
import it.csi.pbandi.pbgestfinbo.business.service.impl.AmministrativoContabileServiceImpl;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoSegnalazioneCorteContiDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SegnalazioneCorteContiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.SegnalazioneCorteContiRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoSegnalazioneCorteContiDTORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.SegnalazioneCorteContiVO;

public class SegnalazioneCorteContiDAOImpl extends JdbcDaoSupport implements SegnalazioneCorteContiDAO {
	
	@Autowired
	AmministrativoContabileServiceImpl amministrativoService;
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME); 
	
	
	@Autowired
	public SegnalazioneCorteContiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	
	@Override
	public Boolean insertSegnalazione(SegnalazioneCorteContiVO segnalazioneCorteContiVO, int idModalitaAgev) {
		String prf = "[SegnalazioneCorteContiDAOImpl::insertSegnalazione]";
		LOG.info(prf + " BEGIN");
		
		Boolean result = true; 
		
		if(segnalazioneCorteContiVO == null) {
			throw new InvalidParameterException(" Segnalazione corte dei conti  non valorizzata.");
		}
		
		try {	
			String sqlId = "SELECT SEQ_PBANDI_T_SEGN_CORTE_CONTI.nextval FROM dual"; 
			Long idSegnalazioneCC = getJdbcTemplate().queryForObject(sqlId, Long.class); 

			String sqlAge = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
					"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
					"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
			int idModalitaAgevolazioneRif = getJdbcTemplate().queryForObject(sqlAge, Integer.class, idModalitaAgev);


			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt3 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt4 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt5 = new SimpleDateFormat("yyyy-MM-dd");

			String dataSegna =  null, dataPianoRient =  null, dataPagam =  null, dataArch =  null,  dataDissegn = null, dataSaldoS = null;

			dataSegna = (segnalazioneCorteContiVO.getDataSegnalazione()!=null) ? dt.format(segnalazioneCorteContiVO.getDataSegnalazione()): null; 

			dataPianoRient =(segnalazioneCorteContiVO.getDataPianoRientro()!=null) ? dt1.format(segnalazioneCorteContiVO.getDataPianoRientro()): null; 

			dataSaldoS = (segnalazioneCorteContiVO.getDataSaldoStralcio()!=null) ? dt2.format(segnalazioneCorteContiVO.getDataSaldoStralcio()): null; 

			dataPagam = (segnalazioneCorteContiVO.getDataPagamento()!=null)?dt3.format(segnalazioneCorteContiVO.getDataPagamento()):null; 

			dataDissegn = (segnalazioneCorteContiVO.getDataDissegnalazione()!=null)?dt4.format(segnalazioneCorteContiVO.getDataDissegnalazione()) : null; 

			dataArch = (segnalazioneCorteContiVO.getDataArchiv()!=null)?dt5.format(segnalazioneCorteContiVO.getDataArchiv()):null;


			String sqlIns = "INSERT into PBANDI_T_SEGNALAZ_CORTE_CONTI ("
					+ "ID_SEGNALAZIONE_CORTE_CONTI,\r\n"
					+ "ID_PROGETTO,\r\n"
					+ "DT_SEGNALAZIONE,\r\n"
					+ "NUM_PROTOCOLLO_SEGN,\r\n"
					+ "IMP_CRED_RES_CAPITALE,\r\n"
					+ "IMP_ONERI_AGEVOLAZ,\r\n"
					+ "IMP_QUOTA_SEGNALAZ,\r\n"
					+ "IMP_GARANZIA,\r\n"
					+ "FLAG_PIANO_RIENTRO,\r\n"
					+ "DT_PIANO_RIENTRO,\r\n"
					+ "FLAG_SALDO_STRALCIO,\r\n"
					+ "DT_SALDO_STRALCIO,\r\n"
					+ "FLAG_PAGAM_INTEGRALE,\r\n"
					+ "DT_PAGAMENTO,\r\n"
					+ "FLAG_DISSEGNALAZIONE,\r\n"
					+ "DT_DISSEGNALAZIONE,\r\n"
					+ "NUM_PROTOCOLLO_DISS,\r\n"
					+ "FLAG_DECRETO_ARCHIV,\r\n"
					+ "DT_ARCHIV,\r\n"
					+ "NUM_PROTOCOLLO_ARCHIV,\r\n"
					+ "FLAG_COMUNICAZ_REGIONE,\r\n"
					+ "NOTE,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INSERIMENTO, ID_MODALITA_AGEVOLAZIONE\r\n"
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?)"; 
			getJdbcTemplate().update(sqlIns, new Object[] {
					idSegnalazioneCC,
					segnalazioneCorteContiVO.getIdProgetto(),
					(dataSegna!= null && dataSegna.trim().length()>0) ? Date.valueOf(dataSegna) : null,
					segnalazioneCorteContiVO.getNumProtocolloSegn(),
					segnalazioneCorteContiVO.getImpCredResCapitale(),
					segnalazioneCorteContiVO.getImpOneriAgevolaz(),
					segnalazioneCorteContiVO.getImpQuotaSegnalaz(),
					segnalazioneCorteContiVO.getImpGaranzia(),
					segnalazioneCorteContiVO.getFlagPianoRientro(),
					(dataPianoRient!= null && dataPianoRient.trim().length()>0) ? Date.valueOf(dataPianoRient) : null,
					segnalazioneCorteContiVO.getFlagSaldoStralcio(),
					(dataSaldoS!=null && dataSaldoS.trim().length()>0) ? Date.valueOf(dataSaldoS): null,
					segnalazioneCorteContiVO.getFlagPagamIntegrale(),
					(dataPagam !=null && dataPagam.trim().length()>0) ? Date.valueOf(dataPagam): null,
					segnalazioneCorteContiVO.getFlagDissegnalazione(),
					(dataDissegn !=null && dataDissegn.trim().length()>0)? Date.valueOf(dataDissegn): null,
					segnalazioneCorteContiVO.getNumProtocolloDiss(),
					segnalazioneCorteContiVO.getFlagDecretoArchiv(),
					(dataArch!=null && dataArch.trim().length()>0)? Date.valueOf(dataArch): null,
					segnalazioneCorteContiVO.getNumProtocolloArchiv(),
					segnalazioneCorteContiVO.getFlagComunicazRegione(),
					segnalazioneCorteContiVO.getNote(),
					segnalazioneCorteContiVO.getIdUtenteIns(),
					idModalitaAgev
			});
			
            LOG.debug(prf + "  param [IdProgetto] = " + idSegnalazioneCC );
            LOG.debug(prf + "  param [DataSegnalazione] = " +  segnalazioneCorteContiVO.getIdProgetto());
            LOG.debug(prf + "  param [NumProtocolloSegn] = " +  segnalazioneCorteContiVO.getDataSegnalazione());
            LOG.debug(prf + "  param [ImpCredResCapitale] = " +  segnalazioneCorteContiVO.getNumProtocolloSegn());
            LOG.debug(prf + "  param [ImpCredResCapitale] = " +  segnalazioneCorteContiVO.getImpCredResCapitale());
            LOG.debug(prf + "  param [ImpOneriAgevolaz] = " +  segnalazioneCorteContiVO.getImpOneriAgevolaz());
            LOG.debug(prf + "  param [ImpQuotaSegnalaz] = " +  segnalazioneCorteContiVO.getImpQuotaSegnalaz());
            LOG.debug(prf + "  param [ImpGaranzia] = " +  segnalazioneCorteContiVO.getImpGaranzia());
            LOG.debug(prf + "  param [FlagPianoRientro] = " +  segnalazioneCorteContiVO.getFlagPianoRientro());
            LOG.debug(prf + "  param [DataPianoRientro] = " +  segnalazioneCorteContiVO.getDataPianoRientro());
            LOG.debug(prf + "  param [FlagSsaldoStralcio] = " +  segnalazioneCorteContiVO.getFlagSaldoStralcio());
            LOG.debug(prf + "  param [DatSaldoStralcio] = " +  segnalazioneCorteContiVO.getDataSaldoStralcio());
            LOG.debug(prf + "  param [FlagPagamIntegrale] = " +  segnalazioneCorteContiVO.getFlagPagamIntegrale());
            LOG.debug(prf + "  param [DataPagamento] = " +  segnalazioneCorteContiVO.getDataPagamento());
            LOG.debug(prf + "  param [FlagDissegnalazione] = " +  segnalazioneCorteContiVO.getFlagDissegnalazione());
            LOG.debug(prf + "  param [DataDissegnalazione] = " +  segnalazioneCorteContiVO.getDataDissegnalazione());
            LOG.debug(prf + "  param [NumProtocolloDiss] = " +  segnalazioneCorteContiVO.getNumProtocolloDiss());
            LOG.debug(prf + "  param [FlagDecretoArchiv] = " +  segnalazioneCorteContiVO.getFlagDecretoArchiv());
            LOG.debug(prf + "  param [DataArchiv] = " +  segnalazioneCorteContiVO.getDataArchiv());
            LOG.debug(prf + "  param [NumProtocolloArchiv] = " +  segnalazioneCorteContiVO.getNumProtocolloArchiv());
            LOG.debug(prf + "  param [FlagComunicazRegione] = " +  segnalazioneCorteContiVO.getFlagComunicazRegione());
            LOG.debug(prf + "  param [Note] = " +  segnalazioneCorteContiVO.getNote());
            LOG.debug(prf + "  param [IdUtenteIns] = " +  segnalazioneCorteContiVO.getIdUtenteIns());
            
            LOG.info(prf + " Nessuna condizione, chiamo amm.vo");
            
            Boolean ammRes = false;
            
            // int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, Date dataSegnalazione, Long idUtente, Long idSegnalazioneCorteConti
            ammRes = amministrativoService.setSegnalazioneCorteConti(segnalazioneCorteContiVO.getIdProgetto().intValue(), idModalitaAgev, idModalitaAgevolazioneRif, segnalazioneCorteContiVO.getDataSegnalazione(), segnalazioneCorteContiVO.getIdUtenteIns(), idSegnalazioneCC);
            LOG.info(prf + " Service res: " + ammRes);
            
		} catch(Exception e) {
			LOG.error(prf + "Exception while trying to update PBANDI_T_SEGNALAZ_CORTE_CONTI", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}

	@Override
	public Boolean modificaSegnalazione(SegnalazioneCorteContiVO segnalazioneCorteContiVO,
			Long idSegnalazioneCorteConti, int idModalitaAgev) {
		String prf = "[SegnalazioneCorteContiDAOImpl::modificaSegnalazione]";
		LOG.info(prf + "BEGIN");
		
		Boolean result = true; 
		
		if(segnalazioneCorteContiVO == null) {
			throw new InvalidParameterException(" Segnalazione corte dei conti  non valorizzata.");
		}
		if(idSegnalazioneCorteConti == null) {
			throw new InvalidParameterException("identificativo segnalazione corte dei conti  non valorizzato.");
		}
		
		
		try {
			String sqlUpdate = "update PBANDI_T_SEGNALAZ_CORTE_CONTI"
					+ " SET DT_FINE_VALIDITA = sysdate ,"
					+ " DT_AGGIORNAMENTO = sysdate,"
					+ " ID_UTENTE_AGG = " + segnalazioneCorteContiVO.getIdUtenteIns()
					+ " WHERE ID_SEGNALAZIONE_CORTE_CONTI = " + idSegnalazioneCorteConti; 
			
			getJdbcTemplate().update(sqlUpdate); 
			
			result = insertSegnalazione(segnalazioneCorteContiVO, idModalitaAgev); 
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to update PBANDI_T_SEGNALAZ_CORTE_CONTI", e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public SegnalazioneCorteContiVO getSegnalazione(Long idSegnalazione) {
		String prf = "[SegnalazioneCorteContiDAOImpl::getSegnalazione]";
		LOG.info(prf + "BEGIN");
		
		SegnalazioneCorteContiVO segnalazioneCorteConti = new SegnalazioneCorteContiVO(); 
		
		try {
			
			String query = "SELECT * FROM PBANDI_T_SEGNALAZ_CORTE_CONTI"
					+ " WHERE ID_SEGNALAZIONE_CORTE_CONTI = " + idSegnalazione; 
			
			segnalazioneCorteConti = getJdbcTemplate().queryForObject(query, new SegnalazioneCorteContiRowMapper());
			
		
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEGNALAZ_CORTE_CONTI", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		
		return segnalazioneCorteConti;
	}

	@Override
	public ArrayList<StoricoSegnalazioneCorteContiDTO> getStorico(Long idProgetto,  int idModalitaAgev) {
		String prf = "[SegnalazioneCorteContiDAOImpl::getStorico]";
		LOG.info(prf + "BEGIN");
		
		
		ArrayList<StoricoSegnalazioneCorteContiDTO> storico =  new ArrayList<StoricoSegnalazioneCorteContiDTO>(); 
		
		try {
			
			
			String  query = " SELECT \r\n"
					+ "ptscc.ID_SEGNALAZIONE_CORTE_CONTI,\r\n"
					+ "ptscc.ID_PROGETTO,\r\n"
					+ "ptscc.DT_SEGNALAZIONE,\r\n"
					+ "ptscc.NUM_PROTOCOLLO_SEGN,\r\n"
					+ "ptscc.IMP_CRED_RES_CAPITALE,\r\n"
					+ "ptscc.IMP_ONERI_AGEVOLAZ,\r\n"
					+ "ptscc.IMP_QUOTA_SEGNALAZ,\r\n"
					+ "ptscc.IMP_GARANZIA,\r\n"
					+ "ptscc.FLAG_PIANO_RIENTRO,\r\n"
					+ "ptscc.DT_PIANO_RIENTRO,\r\n"
					+ "ptscc.FLAG_SALDO_STRALCIO,\r\n"
					+ "ptscc.DT_SALDO_STRALCIO,\r\n"
					+ "ptscc.FLAG_PAGAM_INTEGRALE,\r\n"
					+ "ptscc.DT_PAGAMENTO,\r\n"
					+ "ptscc.FLAG_DISSEGNALAZIONE,\r\n"
					+ "ptscc.DT_DISSEGNALAZIONE,\r\n"
					+ "ptscc.NUM_PROTOCOLLO_DISS,\r\n"
					+ "ptscc.FLAG_DECRETO_ARCHIV,\r\n"
					+ "ptscc.DT_ARCHIV,\r\n"
					+ "ptscc.NUM_PROTOCOLLO_ARCHIV,\r\n"
					+ "ptscc.FLAG_COMUNICAZ_REGIONE,\r\n"
					+ "ptscc.NOTE,\r\n"
					+ "ptscc.DT_INSERIMENTO ,\r\n"
					+ "ptscc.DT_AGGIORNAMENTO ,\r\n"
					+ "PTPF .NOME,\r\n"
					+ "PTPF .COGNOME\r\n"
					+ "FROM PBANDI_T_SEGNALAZ_CORTE_CONTI ptscc \r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptscc.ID_UTENTE_INS\r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "WHERE PTSCC .ID_UTENTE_INS= ptu.ID_UTENTE \r\n"
					+ "AND ptscc.ID_PROGETTO  = "+idProgetto+"\r\n"
					+ "and ptscc.ID_MODALITA_AGEVOLAZIONE = "+idModalitaAgev+"\r\n"
					+ "AND PTSCC .DT_FINE_VALIDITA IS not NULL \r\n"
					+ "ORDER BY ptscc.ID_SEGNALAZIONE_CORTE_CONTI DESC \r\n"; 
			
			
			
			
			storico = (ArrayList<StoricoSegnalazioneCorteContiDTO>) getJdbcTemplate().query(query, new StoricoSegnalazioneCorteContiDTORowMapper());  
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEGNALAZ_CORTE_CONTI", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storico;
	}

	@Override
	public ArrayList<StoricoSegnalazioneCorteContiDTO> getSotricoSegnalazioni(Long idProgetto, int idModalitaAgev) {
		String prf = "[SegnalazioneCorteContiDAOImpl::getSegnalazioni]";
		LOG.info(prf + "BEGIN");

		ArrayList<StoricoSegnalazioneCorteContiDTO> storicoSegnalazioneCorteContiDTOs =  new ArrayList<StoricoSegnalazioneCorteContiDTO>(); 
		
		try {
			
			String  query = " SELECT \r\n"
					+ "ptscc.ID_SEGNALAZIONE_CORTE_CONTI,\r\n"
					+ "ptscc.ID_PROGETTO,\r\n"
					+ "ptscc.DT_SEGNALAZIONE,\r\n"
					+ "ptscc.NUM_PROTOCOLLO_SEGN,\r\n"
					+ "ptscc.IMP_CRED_RES_CAPITALE,\r\n"
					+ "ptscc.IMP_ONERI_AGEVOLAZ,\r\n"
					+ "ptscc.IMP_QUOTA_SEGNALAZ,\r\n"
					+ "ptscc.IMP_GARANZIA,\r\n"
					+ "ptscc.FLAG_PIANO_RIENTRO,\r\n"
					+ "ptscc.DT_PIANO_RIENTRO,\r\n"
					+ "ptscc.FLAG_SALDO_STRALCIO,\r\n"
					+ "ptscc.DT_SALDO_STRALCIO,\r\n"
					+ "ptscc.FLAG_PAGAM_INTEGRALE,\r\n"
					+ "ptscc.DT_PAGAMENTO,\r\n"
					+ "ptscc.FLAG_DISSEGNALAZIONE,\r\n"
					+ "ptscc.DT_DISSEGNALAZIONE,\r\n"
					+ "ptscc.NUM_PROTOCOLLO_DISS,\r\n"
					+ "ptscc.FLAG_DECRETO_ARCHIV,\r\n"
					+ "ptscc.DT_ARCHIV,\r\n"
					+ "ptscc.NUM_PROTOCOLLO_ARCHIV,\r\n"
					+ "ptscc.FLAG_COMUNICAZ_REGIONE,\r\n"
					+ "ptscc.NOTE,\r\n"
					+ "ptscc.DT_INSERIMENTO ,\r\n"
					+ "ptscc.DT_AGGIORNAMENTO ,\r\n"
					+ "PTPF .NOME,\r\n"
					+ "PTPF .COGNOME\r\n"
					+ "FROM PBANDI_T_SEGNALAZ_CORTE_CONTI ptscc \r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptscc.ID_UTENTE_INS\r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "WHERE PTSCC .ID_UTENTE_INS= ptu.ID_UTENTE \r\n"
					+ "AND ptscc.ID_PROGETTO  = "+idProgetto+"\r\n"
					+ "and ptscc.ID_MODALITA_AGEVOLAZIONE = "+idModalitaAgev+"\r\n"
					+ "AND PTSCC .DT_FINE_VALIDITA IS NULL \r\n"
					+ "ORDER BY ptscc.ID_SEGNALAZIONE_CORTE_CONTI DESC \r\n"
					+ " "; 
			
			storicoSegnalazioneCorteContiDTOs =  (ArrayList<StoricoSegnalazioneCorteContiDTO>) getJdbcTemplate().query(query, new StoricoSegnalazioneCorteContiDTORowMapper());
			
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEGNALAZ_CORTE_CONTI", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return storicoSegnalazioneCorteContiDTOs;
	}

}
