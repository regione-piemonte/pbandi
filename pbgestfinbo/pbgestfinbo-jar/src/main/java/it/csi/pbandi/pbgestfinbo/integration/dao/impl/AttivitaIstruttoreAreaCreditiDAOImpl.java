/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.business.service.impl.AmministrativoContabileServiceImpl;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AttivitaIstruttoreAreaCreditiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.IscrizioneRuoloVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.NoteGeneraliVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.BoxListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.IscrizioneRuoloVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LavorazioneBoxListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;



@Service
public class AttivitaIstruttoreAreaCreditiDAOImpl extends JdbcDaoSupport implements AttivitaIstruttoreAreaCreditiDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	DocumentoManager documentoManager;
	
	@Autowired
	AmministrativoContabileServiceImpl amministrativoService;
	
	@Autowired
	public AttivitaIstruttoreAreaCreditiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public AttivitaIstruttoreAreaCreditiDAOImpl() {
	}

	
	@Override
	public BoxListVO getBoxList(Long idModalitaAgevolazione, Long idArea) throws Exception, InvalidParameterException {
		String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::getBoxList]";
		LOG.info(prf + " BEGIN");
		
		LOG.info("È stato passato " + idModalitaAgevolazione + " come valore di idModalitaAgevolazione");
		LOG.info("È stato passato " + idArea + " come valore di idArea");
		
		BoxListVO boxs = new BoxListVO();
		
		if(idArea <= 0 || idArea > 2 || idArea == null) {
			throw new InvalidParameterException("Valore di idArea non valido.");
		}
		
		if (idModalitaAgevolazione == 0 || idModalitaAgevolazione == null) {
			throw new InvalidParameterException("Valore di idModalitaAgevolazione non valido.");
		}
		
		List<Integer> idsList = new ArrayList<>();
		
		try {
			StringBuilder query = new StringBuilder();

			// Mi ricavo la lista degli id dei box che saranno visibili per mod di agev.
			if (idArea.equals(1L)) {
				query.append("SELECT DISTINCT	pracg.ID_ENTITA\r\n"
						+ "FROM PBANDI_R_AGEV_CREDITI_GARANZIE pracg\r\n"
						+ "--INNER JOIN PBANDI_C_ENTITA pce ON pracg.ID_ENTITA = pce.ID_ENTITA\r\n"
						+ "WHERE pracg.FLAG_AREA_CREDITI = 'S' AND pracg.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione);
			} else {
				query.append("SELECT DISTINCT	pracg.ID_ENTITA\r\n"
						+ "FROM PBANDI_R_AGEV_CREDITI_GARANZIE pracg\r\n"
						+ "--INNER JOIN PBANDI_C_ENTITA pce ON pracg.ID_ENTITA = pce.ID_ENTITA\r\n"
						+ "WHERE pracg.FLAG_GARANZIE = 'S' AND pracg.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione);
			}


			idsList = getJdbcTemplate().queryForList(query.toString(), Integer.class);
			
			// Setto a true i box corrispondenti
			for (Integer item: idsList) {
				switch (item) {
				case 492: //PBANDI_T_TRANSAZIONI_BANCA
					boxs.setPBANDI_T_TRANSAZIONI_BANCA(true);
					break;
				
				case 501: //PBANDI_T_SEGNALAZ_CORTE_CONTI
					boxs.setPBANDI_T_SEGNALAZ_CORTE_CONTI(true);
					break;
					
				case 502: //PBANDI_T_SALDO_STRALCIO
					boxs.setPBANDI_T_SALDO_STRALCIO(true);
					break;
					
				case 507: //PBANDI_T_REVOCA_BANCARIA
					boxs.setPBANDI_T_REVOCA_BANCARIA(true);
					break;
					
				case 508: //PBANDI_T_PIANO_RIENTRO
					boxs.setPBANDI_T_PIANO_RIENTRO(true);
					break;
					
				case 509: //PBANDI_T_PASSAGGIO_PERDITA
					boxs.setPBANDI_T_PASSAGGIO_PERDITA(true);
					break;
					
				case 512: //PBANDI_T_MESSA_IN_MORA
					boxs.setPBANDI_T_MESSA_IN_MORA(true);
					break;
					
				case 513: //PBANDI_T_LIBERAZ_GARANTE
					boxs.setPBANDI_T_LIBERAZ_GARANTE(true);
					break;
					
				case 514: //PBANDI_T_LIBERAZ_BANCA
					boxs.setPBANDI_T_LIBERAZ_BANCA(true);
					break;
					
				case 515: //PBANDI_T_ISCRIZIONE_RUOLO
					boxs.setPBANDI_T_ISCRIZIONE_RUOLO(true);
					break;
					
				case 517: //PBANDI_T_ESCUSS_CONFIDI
					boxs.setPBANDI_T_ESCUSS_CONFIDI(true);
					break;
					
				case 522: //PBANDI_T_CESSIONE_QUOTA_FP
					boxs.setPBANDI_T_CESSIONE_QUOTA_FP(true);
					break;
					
				case 524: //PBANDI_T_AZIONE_RECUP_BANCA
					boxs.setPBANDI_T_AZIONE_RECUP_BANCA(true);
					break;
					
				case 525: //PBANDI_T_ANNOTAZ_GENERALI
					boxs.setPBANDI_T_ANNOTAZ_GENERALI(true);
					break;
					
				case 527: //PBANDI_T_ABBATTIM_GARANZIE
					boxs.setPBANDI_T_ABBATTIM_GARANZIE(true);
					break;
					
				case 720: //PBANDI_T_PROCEDURE_CONCORS
					boxs.setPBANDI_T_PROCEDURE_CONCORS(true);
					break;
					
				case 722: //PBANDI_T_CREDITORIE
					boxs.setPBANDI_T_CREDITORIE(true);
					break;
					
				case 723: //PBANDI_T_ALLUNG_PIANO_AMMORT
					boxs.setPBANDI_T_ALLUNG_PIANO_AMMORT(true);
					break;
				default:
					LOG.error(prf + " Errore negli idEntita dei box, fuori range: " + item);
					break;
				}
			}
			
			
		} catch (org.springframework.dao.DataAccessException e) {
			LOG.error(prf + " DataAccessException while trying to read BoxListVO ", e);
			throw new ErroreGestitoException(" DataAccessException in " + prf, e);			
		} catch (Exception e) {
			LOG.error(prf + " Exception while trying to read BoxListVO ", e);
			throw new ErroreGestitoException("Exception in " + prf, e);
		}
		finally {
			LOG.info(prf + " END");
		}
			
		return boxs;
	}
	
	
	@Override
	public List<LavorazioneBoxListVO> getLavorazioneBox(Long idModalitaAgevolazione, Long idProgetto) throws Exception, InvalidParameterException {
		String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::getLavorazioneBox]";
		LOG.info(prf + " BEGIN");
		
		LOG.info("È stato passato " + idModalitaAgevolazione + " come valore di idModalitaAgevolazione");
		LOG.info("È stato passato " + idProgetto + " come valore di idProgetto");
		
		List<LavorazioneBoxListVO> listLavorazione = new ArrayList<LavorazioneBoxListVO>();
		
		/*if(idProgetto <= 0 || idProgetto == null) {
			throw new InvalidParameterException("Valore di idProgetto non valido.");
		}
		
		if (idModalitaAgevolazione == 0 || idModalitaAgevolazione == null) {
			throw new InvalidParameterException("Valore di idModalitaAgevolazione non valido.");
		}*/
		
		
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT * FROM (\r\n"
					+ "SELECT 'Revoca bancaria' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_REVOCA_BANCARIA\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Azioni recupero banca' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_AZIONE_RECUP_BANCA\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Liberazione garante' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_LIBERAZ_GARANTE\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Abbattimento garanzie' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_ABBATTIM_GARANZIE\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Escussione Confidi' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_ESCUSS_CONFIDI\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Piano di rientro' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_PIANO_RIENTRO\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Saldo e stralcio' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_SALDO_STRALCIO\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Passaggio a perdita' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_PASSAGGIO_PERDITA\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Liberazione banca' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_LIBERAZ_BANCA\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Messa in mora' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_MESSA_IN_MORA\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Iscrizione al ruolo' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_ISCRIZIONE_RUOLO\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Segnalazione Corte dei Conti' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_SEGNALAZ_CORTE_CONTI\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Transazioni' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_TRANSAZIONI_BANCA\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT 'Servicer' AS nome_attivita,\r\n"
					+ "    MIN(DT_INIZIO_VALIDITA) AS data_inizio_attivita,\r\n"
					+ "    MAX(DT_INIZIO_VALIDITA) AS data_ultima_modifica\r\n"
					+ "FROM PBANDI_T_SERVICER\r\n"
					+ "WHERE ID_PROGETTO = " + idProgetto + " AND ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "ORDER BY nome_attivita\r\n"
					+ ") tabelle\r\n"
					+ "WHERE data_inizio_attivita IS NOT NULL");
			
			
			listLavorazione = getJdbcTemplate().query(query.toString(), new RowMapper<LavorazioneBoxListVO>() {
				
				@Override
				public LavorazioneBoxListVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					LavorazioneBoxListVO lav = new LavorazioneBoxListVO();
					
					lav.setNomeAttivita(rs.getString("NOME_ATTIVITA"));
					lav.setDataInizio(rs.getDate("DATA_INIZIO_ATTIVITA"));
					lav.setDataUltimaModifica(rs.getDate("DATA_ULTIMA_MODIFICA"));
					
					return lav;
				}});


		} catch (org.springframework.dao.DataAccessException e) {
			LOG.error(prf + " DataAccessException while trying to read LavorazioneBoxListVO ", e);
			throw new ErroreGestitoException(" DataAccessException in " + prf, e);			
		} catch (Exception e) {
			LOG.error(prf + " Exception while trying to read LavorazioneBoxListVO ", e);
			throw new ErroreGestitoException("Exception in " + prf, e);
		}
		finally {
			LOG.info(prf + " END");
		}
			
		return listLavorazione;
	}
	
	
	@Override
	@Transactional
	public EsitoDTO salvaAllegati(Long idProgetto, Boolean letteraAccompagnatoria, int ambitoAllegato, byte[] allegato, String nomeAllegato, HttpServletRequest req) throws Exception {
        //  ambitoAllegato -> 0 = emissione, 1 = conferma provvedimento, 2 = ritiro in autotutela
		String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::getBoxList]";
		LOG.info(prf + " BEGIN");

		EsitoDTO esito = new EsitoDTO();
		
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
 
        String descBreveTipoDocIndex = getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
				+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=36", String.class);	
		if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
			throw new Exception("Tipo documento index non trovato.");
        
        DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
 
        documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(36));
        documentoIndexVO.setIdEntita(new BigDecimal(605));
        documentoIndexVO.setIdTarget(BigDecimal.valueOf(idProgetto));
        documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
        documentoIndexVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));
        documentoIndexVO.setNomeFile(nomeAllegato);
        //documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
        documentoIndexVO.setRepository(descBreveTipoDocIndex);
        /*Dati Obbligatori*/
        documentoIndexVO.setUuidNodo("UUID");

        //salva allegato
        if(!documentoManager.salvaFileConVisibilita(allegato, documentoIndexVO, null)) {
            throw new ErroreGestitoException("Errore durante il salvataggio dell'allegato");
        }

        LOG.info(prf + "END");
        
		return esito;
    }
	
	// Iscrizione a Ruolo //
	
	@Override
	public List<IscrizioneRuoloVO> getIscrizioneRuolo(String idProgetto, Long idModalitaAgevolazione) throws Exception {
		String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::getIscrizioneRuolo]";
		LOG.info(prf + " BEGIN");
			
		List<IscrizioneRuoloVO> isc = null;
		
		try {
			//LOG.info(idProgetto);
			String query = "SELECT isruo.*, perfis.COGNOME || ' ' || perfis.NOME AS istruttore\r\n"
					+ "FROM PBANDI_T_ISCRIZIONE_RUOLO isruo\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON isruo.ID_UTENTE_INS = ute.ID_UTENTE\r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO \r\n"
					+ "WHERE /*perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO) AND*/\r\n"
					+ "	isruo.ID_PROGETTO = " + idProgetto + " AND isruo.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "ORDER BY isruo.ID_ISCRIZIONE_RUOLO DESC";
			/* Ho commentato il filtro per l'istruttore (perfis1) perché generava errore, sperando che il problema del doppio nome sia stato risolto dopo la migrazione. */
			
			isc = getJdbcTemplate().query(query, new IscrizioneRuoloVORowMapper());			
			//LOG.info(isc);
		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read IscrizioneRuoloVO", e);
			throw new ErroreGestitoException("DaoException while trying to read IscrizioneRuoloVO", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return isc;
		}
	
	
	
	
	@Override
	public void setIscrizioneRuolo(IscrizioneRuoloVO iscrizioneRuolo, int idModalitaAgevolazione) throws ErroreGestitoException {
		String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::setIscrizioneRuolo]";
		LOG.info(prf + " BEGIN");

		//System.out.println("PRINT OBJ FROM BODY: " + iscrizioneRuolo);

		
		try {

			if (iscrizioneRuolo.getIdCurrentRecord() == null) {
				
				String sqlIdIscrizioneRuolo = "select SEQ_PBANDI_T_ISCRIZIONE_RUOLO.nextval from dual";
				Long idIscrizioneRuolo = getJdbcTemplate().queryForObject(sqlIdIscrizioneRuolo, Long.class);
				
				String sqlAge = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
						"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
						"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
				int idModalitaAgevolazioneRif = getJdbcTemplate().queryForObject(sqlAge, Integer.class, idModalitaAgevolazione);
				
				
				String queryNew = "INSERT INTO PBANDI_T_ISCRIZIONE_RUOLO (\r\n"
						+ "	ID_ISCRIZIONE_RUOLO,\r\n"
						+ "	ID_PROGETTO,\r\n"
						+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	DT_RICHIESTA_ISCRIZIONE,\r\n"
						+ "	NUM_PROTOCOLLO,\r\n"
						+ "	DT_RICHIESTA_DISCARICO,\r\n"
						+ "	NUM_PROTOCOLLO_DISCARICO,\r\n"
						+ "	DT_ISCRIZIONE_RUOLO,\r\n"
						+ "	DT_DISCARICO,\r\n"
						+ "	NUM_PROTOCOLLO_DISCAR_REG,\r\n"
						+ "	DT_RICHIESTA_SOSP,\r\n"
						+ "	NUM_PROTOCOLLO_SOSP,\r\n"
						+ "	IMP_CAPITALE_RUOLO,\r\n"
						+ "	IMP_AGEVOLAZ_RUOLO,\r\n"
						+ "	DT_ISCRIZIONE,\r\n"
						+ "	NUM_PROTOCOLLO_REGIONE,\r\n"
						+ "	TIPO_PAGAMENTO,\r\n"
						+ "	NOTE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO)\r\n"
						+ "VALUES (\r\n"
						+ "	?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, sysdate)";
				Object[] args1 = new Object[] {
						idIscrizioneRuolo,
						iscrizioneRuolo.getIdProgetto(), idModalitaAgevolazione, iscrizioneRuolo.getDataRichiestaIscrizione(),
						iscrizioneRuolo.getNumProtocollo(), iscrizioneRuolo.getDataRichiestaDiscarico(),
						iscrizioneRuolo.getNumProtocolloDiscarico(), iscrizioneRuolo.getDataIscrizioneRuolo(),
						iscrizioneRuolo.getDataDiscarico(), iscrizioneRuolo.getNumProtoDiscReg(),
						iscrizioneRuolo.getDataRichiestaSospensione(), iscrizioneRuolo.getNumProtoSosp(),
						iscrizioneRuolo.getCapitaleRuolo(), iscrizioneRuolo.getAgevolazioneRuolo(),
						iscrizioneRuolo.getDataIscrizione(), iscrizioneRuolo.getNumProtoReg(),
						iscrizioneRuolo.getTipoPagamento(), iscrizioneRuolo.getNote(), iscrizioneRuolo.getIdUtente()
				};
				
				int[] types1 = new int[]{
						Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.DATE, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.DATE,
						Types.DATE, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.DECIMAL, Types.DECIMAL,
						Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER
				};
				
				getJdbcTemplate().update(queryNew, args1, types1);
				
				
				LOG.info(prf + " Nessuna condizione, chiamo amm.vo");

				Boolean ammRes = false;
				
				ammRes = amministrativoService.setIscrizioneRuolo(Integer.parseInt(iscrizioneRuolo.getIdProgetto()), idModalitaAgevolazione, idModalitaAgevolazioneRif, iscrizioneRuolo.getDataIscrizioneRuolo(), Long.parseLong(iscrizioneRuolo.getIdUtente()), idIscrizioneRuolo);
				LOG.info(prf + " Amm.vo res: " + ammRes);
				
			} else {
				
				String queryCopyAndInsert = "INSERT INTO PBANDI_T_ISCRIZIONE_RUOLO (\r\n"
						+ "	ID_ISCRIZIONE_RUOLO,\r\n"
						+ "	ID_PROGETTO,\r\n"
						+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	DT_RICHIESTA_ISCRIZIONE,\r\n"
						+ "	NUM_PROTOCOLLO,\r\n"
						+ "	DT_RICHIESTA_DISCARICO,\r\n"
						+ "	NUM_PROTOCOLLO_DISCARICO,\r\n"
						+ "	DT_ISCRIZIONE_RUOLO,\r\n"
						+ "	DT_DISCARICO,\r\n"
						+ "	NUM_PROTOCOLLO_DISCAR_REG,\r\n"
						+ "	DT_RICHIESTA_SOSP,\r\n"
						+ "	NUM_PROTOCOLLO_SOSP,\r\n"
						+ "	IMP_CAPITALE_RUOLO,\r\n"
						+ "	IMP_AGEVOLAZ_RUOLO,\r\n"
						+ "	DT_ISCRIZIONE,\r\n"
						+ "	NUM_PROTOCOLLO_REGIONE,\r\n"
						+ "	TIPO_PAGAMENTO,\r\n"
						+ "	NOTE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO)\r\n"
						+ "SELECT\r\n"
						+ "	SEQ_PBANDI_T_ISCRIZIONE_RUOLO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,\r\n"
						+ "	SYSDATE,\r\n"
						+ "	?,\r\n"
						+ "	SYSDATE\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_T_ISCRIZIONE_RUOLO\r\n"
						+ "WHERE\r\n"
						+ "	ID_ISCRIZIONE_RUOLO = " + iscrizioneRuolo.getIdCurrentRecord();
				
				Object[] args1 = new Object[] {
						iscrizioneRuolo.getIdProgetto(), idModalitaAgevolazione, iscrizioneRuolo.getDataRichiestaIscrizione(),
						iscrizioneRuolo.getNumProtocollo(), iscrizioneRuolo.getDataRichiestaDiscarico(),
						iscrizioneRuolo.getNumProtocolloDiscarico(), iscrizioneRuolo.getDataIscrizioneRuolo(),
						iscrizioneRuolo.getDataDiscarico(), iscrizioneRuolo.getNumProtoDiscReg(),
						iscrizioneRuolo.getDataRichiestaSospensione(), iscrizioneRuolo.getNumProtoSosp(),
						iscrizioneRuolo.getCapitaleRuolo(), iscrizioneRuolo.getAgevolazioneRuolo(),
						iscrizioneRuolo.getDataIscrizione(), iscrizioneRuolo.getNumProtoReg(),
						iscrizioneRuolo.getTipoPagamento(), iscrizioneRuolo.getNote(), iscrizioneRuolo.getIdUtente()
				};
				
				int[] types1 = new int[]{
						Types.INTEGER, Types.INTEGER, Types.DATE, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.DATE,
						Types.DATE, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.DECIMAL, Types.DECIMAL,
						Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER
				};
				
				getJdbcTemplate().update(queryCopyAndInsert, args1, types1);
				
				
				
				String queryUpdatePrevious = "UPDATE PBANDI_T_ISCRIZIONE_RUOLO \r\n"
						+ "SET\r\n"
						+ "	DT_FINE_VALIDITA = SYSDATE,\r\n"
						+ "	ID_UTENTE_AGG = " + iscrizioneRuolo.getIdUtente() + ",\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE\r\n"
						+ "WHERE\r\n"
						+ "	ID_ISCRIZIONE_RUOLO = " + iscrizioneRuolo.getIdCurrentRecord();
				
				getJdbcTemplate().update(queryUpdatePrevious);
			}
			
		} catch (Exception e) {
			LOG.error(prf + "Exception during setIscrizioneRuolo", e);
			throw new ErroreGestitoException("Exception during setIscrizioneRuolo", e);
		} finally {
			LOG.info(prf + " END");
		}	
	}
	
	
	// Note Generali //
	
	@Override
	public Long salvaNote(NoteGeneraliVO noteGeneraliVO, boolean isModifica,  int idModalitaAgevolazione) {
			String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::salvaNote]";
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
			
			if(isModifica) {
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
				
				
				String insert = "INSERT INTO PBANDI_T_ANNOTAZ_GENERALI (\r\n"
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
			
			LOG.debug(prf + "  param [idNote] = " + idAnnotazione);
			LOG.debug(prf + "  param [Note Generale] = " + noteGeneraliVO);
				
		}catch(Exception e) {
			LOG.error(prf + "Exception while trying to insert PBANDI_T_ANNOTAZ_GENERALI", e);
			idAnnotazione = null;          
		}finally {
			LOG.info(prf + " END");
		}
		
		return idAnnotazione;
	}
	
	@Override
	public Boolean salvaUploadAllegato(byte[] file, String nomeFile, Long idUtente, BigDecimal idTarget, BigDecimal idTipoDocumentoIndex,BigDecimal idProgetto, BigDecimal idEntita ){
//		private Boolean salvaAllegatoFrom(MultipartFormDataInput multipartFormData) {
			String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl:: salva Allegato]";
			LOG.info(prf + " BEGIN");
			
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
				LOG.error("errore inserimento pbandi_t_documento_index "+e);
				result= false; 
			}
			return result;
		}
	
	@Override
	public Boolean aggiornaAllegati(List<GestioneAllegatiVO> allegatiPresenti, Long idTarget){
			String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl:: aggiornaAllegati]";
			LOG.info(prf + " BEGIN");
			
			Boolean result = null; 
			try {
				for (GestioneAllegatiVO item : allegatiPresenti) {
									
					String queryModifica = "UPDATE PBANDI.PBANDI_T_DOCUMENTO_INDEX\r\n"
							+ "SET ID_TARGET = " + idTarget + "\r\n"
							+ "WHERE ID_DOCUMENTO_INDEX = " + item.getIdDocumentoIndex();

					getJdbcTemplate().update(queryModifica);
					
					LOG.info(prf + " The document idIndex: " + item.getIdDocumentoIndex() + " has been updated with idTarget: " + idTarget);
				}
				
				
				
			} catch (Exception e) {
				LOG.error("errore durante l'update di id_target in pbandi_t_documento_index "+e);
				result= false; 
			}
			
			
			LOG.info(prf + " END");
			return result;
		}

	@Override
	public List<NoteGeneraliVO> getNoteGenerale(Long idProgetto, int idModalitaAgevolazione) {
		String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::getNoteGenerale]";
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
					+ "    AND ptag.FLAG_ELIMINAZIONE IS NULL\r\n"
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
								
								return doc;
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
	}

	@Override
	public List<StoricoRevocaDTO> getStoricoNote(Long idProgetto, int idModalitaAgev) {
		String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl::getStoricoNote]";
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
					+ "    DT_INSERIMENTO DESC ";
			
			
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
	}
	
	
	@Override
	public Boolean eliminaNota(int idAnnotazione, int idModalitaAgevolazione){
			String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl:: eliminaNota]";
			LOG.info(prf + " BEGIN");
			
			Boolean result = null; 
			try {
				
				// Controllo se la nota da eliminare esiste o se l'id è corrretto
				Integer numRecordExist =  getJdbcTemplate().queryForObject("SELECT COUNT(ID_ANNOTAZ_GENERALE)\r\n"
						+ "FROM PBANDI_T_ANNOTAZ_GENERALI\r\n"
						+ "WHERE ID_ANNOTAZ_GENERALE = " + idAnnotazione, Integer.class);
				if (numRecordExist == null || numRecordExist <= 0) {
					throw new Exception("Id Annotazione non valida");
				}
					
				// Elimino logicamente la nota

				String queryEliminaNota = "UPDATE PBANDI.PBANDI_T_ANNOTAZ_GENERALI\r\n"
						+ "SET FLAG_ELIMINAZIONE = 'SI'\r\n"
						+ "WHERE ID_ANNOTAZ_GENERALE = " + idAnnotazione;
				getJdbcTemplate().update(queryEliminaNota);

				LOG.info(prf + " La nota con idAnnotazione: " + idAnnotazione + " è stata marcata come eliminata.");
				
				result = true;

			} catch (Exception e) {
				LOG.error("errore durante l'eliminazione della nota (idAnnotazione " + idAnnotazione + ") " + e);
				result = false; 
			}
			
			
			LOG.info(prf + " END");
			return result;
		}
	
	
	@Override
	public Boolean eliminaAllegato(int idAnnotazione, int idModalitaAgevolazione){
			String prf = "[AttivitaIstruttoreAreaCreditiDAOImpl:: eliminaAllegato]";
			LOG.info(prf + " BEGIN");
			
			Boolean result = null; 
			try {
				
				
				
			} catch (Exception e) {
				LOG.error("errore durante l'update di id_target in pbandi_t_documento_index "+e);
				result= false; 
			}
			
			
			LOG.info(prf + " END");
			return result;
		}
	
	
}
