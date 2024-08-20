/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;


import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.apache.log4j.Logger;

import it.csi.pbandi.pbgestfinbo.business.service.impl.AmministrativoContabileServiceImpl;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.RevocaBancariaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RevocaBancariaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.RevocaBancariaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoRevocaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.RevocaBancariaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;


public class RevocaBancariaDAOImpl extends JdbcDaoSupport implements RevocaBancariaDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public RevocaBancariaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Autowired
	AmministrativoContabileServiceImpl amministrativoService;

	@Autowired
	SuggestRevocheDAO suggestRevocaDao; 
	@Autowired 
	PropostaRevocaDAO propostaRevocaDao;
		
	@Autowired
	private AnagraficaBeneficiarioDAO anagraficaBen;
	
	@Override
	public 	Boolean salvaRevoca(RevocaBancariaDTO revocaBancariaDTO, Long idUtente, Long idProgetto, int idModalitaAgev) throws NullPointerException{
		
		String prf = "[RevocaBancariaDAOImpl::salvaRevocaBancaria]";
		LOG.info(prf + "BEGIN");
		
		if (revocaBancariaDTO == null) {
			throw new InvalidParameterException("revoca non valorizzata.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		Boolean result = true; 
		
		try {
			
			String sqlSeq = "SELECT seq_pbandi_t_revoca_bancaria.nextval FROM dual";
			long idRevocaBancaria = (long) getJdbcTemplate().queryForObject(sqlSeq.toString(), long.class);
						
			RevocaBancariaVO revocaBancariaVO =  this.popolaRevocaBancaria(revocaBancariaDTO, idRevocaBancaria, idProgetto, idUtente); 
			List<RevocaBancariaDTO> lista = new ArrayList<RevocaBancariaDTO>(); 
			RevocaBancariaDTO rbDB =  new RevocaBancariaDTO(); 
			String dataRicez =  null, dataRevoca = null; 
			
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		
			
			 if (revocaBancariaVO.getDataRicezComunicazioneRevoca()!=null) {
				dataRicez = dt.format(revocaBancariaVO.getDataRicezComunicazioneRevoca());
			}
			 
			if (revocaBancariaVO.getDataRevoca()!=null) {
				dataRevoca = dt1.format(revocaBancariaVO.getDataRevoca());
			} 
			
			String sql= "SELECT * FROM PBANDI_T_REVOCA_BANCARIA "
					+ " WHERE ID_PROGETTO = " + idProgetto + "\r\n"
					+ " AND DT_FINE_VALIDITA IS NULL \r\n"
					+ " and ID_MODALITA_AGEVOLAZIONE= " + idModalitaAgev + "\r\n"
					+ " ORDER BY DT_INSERIMENTO DESC "; 
			
			 lista =  getJdbcTemplate().query(sql, new RevocaBancariaRowMapper());
			 if(lista.size()>0) {
				 rbDB = lista.get(0);
			 	
				String sql2 = "UPDATE PBANDI_T_REVOCA_BANCARIA "
						+ " SET DT_FINE_VALIDITA = sysdate, "
						+ " DT_AGGIORNAMENTO = sysdate,"
						+ " ID_UTENTE_AGG = " + idUtente
						+ " WHERE ID_REVOCA_BANCARIA = " + rbDB.getIdRevoca();
				getJdbcTemplate().update(sql2); 
				
				
				String query = " INSERT INTO PBANDI_T_REVOCA_BANCARIA "
						+"("
						+ "ID_REVOCA_BANCARIA,\r\n"
						+ "ID_PROGETTO,\r\n"
						+ "DT_RICEZIONE_COMUNICAZ,\r\n"
						+ "DT_REVOCA,\r\n"
						+ "IMP_DEBITO_RESIDUO_BANCA,\r\n"
						+ "IMP_DEBITO_RESIDUO_FP,\r\n"
						+ "PERC_COFINANZ_FP,\r\n"
						+ "NUM_PROTOCOLLO,\r\n"
						+ "NOTE,\r\n"
						+ "DT_INIZIO_VALIDITA,\r\n"
						+ "DT_INSERIMENTO,\r\n"
						+ "ID_UTENTE_INS,ID_MODALITA_AGEVOLAZIONE \r\n"
						+" )"
						+ "VALUES(?,?,?,?,?,?,?,?,?,sysdate,sysdate,?, ?)";
				
				getJdbcTemplate().update(query, new Object[] {
						revocaBancariaVO.getIdRevoca(),
						revocaBancariaVO.getIdProgetto(),
						(dataRicez!=null && dataRicez.trim().length()>0) ? Date.valueOf(dataRicez): null,
						(dataRevoca!=null && dataRevoca.trim().length()>0) ? Date.valueOf(dataRevoca): null,
						revocaBancariaVO.getImpDebitoResiduoBanca(),
						revocaBancariaVO.getImpDebitoResiduoFinpiemonte(),
						revocaBancariaVO.getPerCofinanziamentoFinpiemonte(),
						revocaBancariaVO.getNumeroProtocollo(),
						revocaBancariaVO.getNote(),
						revocaBancariaVO.getIdUtenteIns(), idModalitaAgev
						
				});
							
			 } else {

				 String    query = " INSERT INTO PBANDI_T_REVOCA_BANCARIA "
						 +"("
						 + "ID_REVOCA_BANCARIA,\r\n"
						 + "ID_PROGETTO,\r\n"
						 + "DT_RICEZIONE_COMUNICAZ,\r\n"
						 + "DT_REVOCA,\r\n"
						 + "IMP_DEBITO_RESIDUO_BANCA,\r\n"
						 + "IMP_DEBITO_RESIDUO_FP,\r\n"
						 + "PERC_COFINANZ_FP,\r\n"
						 + "NUM_PROTOCOLLO,\r\n"
						 + "NOTE,\r\n"
						 + "DT_INIZIO_VALIDITA,\r\n"
						 + "DT_INSERIMENTO,\r\n"
						 + "ID_UTENTE_INS, ID_MODALITA_AGEVOLAZIONE\r\n"
						 +")"
						 + "VALUES(?,?,?,?,?,?,?,?,?,sysdate,sysdate,?, ? )";

				 getJdbcTemplate().update(query, new Object[] {
						 revocaBancariaVO.getIdRevoca(),
						 revocaBancariaVO.getIdProgetto(),
						 (dataRicez!=null && dataRicez.trim().length()>0) ? Date.valueOf(dataRicez): null,
								 (dataRevoca!=null && dataRevoca.trim().length()>0) ? Date.valueOf(dataRevoca): null,
										 revocaBancariaVO.getImpDebitoResiduoBanca(),
										 revocaBancariaVO.getImpDebitoResiduoFinpiemonte(),
										 revocaBancariaVO.getPerCofinanziamentoFinpiemonte(),
										 revocaBancariaVO.getNumeroProtocollo(),
										 revocaBancariaVO.getNote(),
										 revocaBancariaVO.getIdUtenteIns(), idModalitaAgev

				 });
				 
				 
				 String sqlAge = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
							"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
							"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
				 int idModalitaAgevolazioneRif = getJdbcTemplate().queryForObject(sqlAge, Integer.class, idModalitaAgev);

				 LOG.info(prf + " Nessuna condizione, chiamo amm.vo");

				 Boolean ammRes = false;
				 
				 Date dateToSend = (dataRicez!=null && dataRicez.trim().length()>0) ? Date.valueOf(dataRicez): null;
				 ammRes = amministrativoService.setRevocaBancaria(idRevocaBancaria, (int) idRevocaBancaria, 1, idProgetto.intValue(), dateToSend, revocaBancariaVO.getImpDebitoResiduoBanca(), revocaBancariaVO.getPerCofinanziamentoFinpiemonte(), idModalitaAgev, idModalitaAgevolazioneRif, idUtente);
				 LOG.info(prf + " Amm.vo res: " + ammRes);
				 
				 
				 // inserimento della proposta di revoca
				 
				 // La proposta di revoca viene già inserita quando si crea un blocco, non necessaria qui
				 //Long idPropostaRevoca = propostaRevocaDao.creaPropostaRevoca(suggestRevocaDao.newNumeroRevoca(), revocaBancariaVO.getIdProgetto().longValue(), (long)11, null, Date.valueOf(LocalDate.now()), idUtente.longValue());
				 
				 /*if (idPropostaRevoca!=null) {
					LOG.info("PROPOSTA REVOCA INSERITA CON SUCCESSO!");
				 } else {
					 LOG.info("PROPOSTA REVOCA NON E' INSERITA");
				 }*/
				 
				 // inserimento blocco anagrafico con causale 11 
				 
				 
					BloccoVO bloccoObj = new BloccoVO();
					// get id_Soggettto
					BigDecimal idSoggetto = getJdbcTemplate().queryForObject("SELECT\r\n"
							+ "    prsp.ID_SOGGETTO\r\n"
							+ "FROM\r\n"
							+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "WHERE\r\n"
							+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    AND prsp.ID_PROGETTO = "+ idProgetto , BigDecimal.class); 
					
					bloccoObj.setIdSoggetto(idSoggetto);
					bloccoObj.setIdCausaleBlocco((long)11);
					bloccoObj.setIdUtente(new BigDecimal(revocaBancariaVO.getIdUtenteIns()));
					

					String sqlNumeroDomanda = "SELECT ptd.NUMERO_DOMANDA \r\n"
							+ "   FROM pbandi_t_progetto ptp\r\n"
							+ "   INNER JOIN pbandi_t_domanda ptd ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
							+ "   WHERE ptp.ID_PROGETTO = " + idProgetto;
					String numeroDomanda = getJdbcTemplate().queryForObject(sqlNumeroDomanda, String.class);

					Boolean resBloccoDom = anagraficaBen.insertBloccoDomanda(bloccoObj, bloccoObj.getIdSoggetto().toString(), numeroDomanda);
					LOG.info(prf + " resBloccoDom: " + resBloccoDom);
					
				 result = true;
			 }
			
			LOG.debug(prf + "  param [idRevoca] = " + revocaBancariaVO.getIdRevoca());
			LOG.debug(prf + "  param [idProgetto] = " + revocaBancariaVO.getIdProgetto());
			LOG.debug(prf + "  param [dataRicezComunicazioneRevoca] = " + revocaBancariaVO.getDataRicezComunicazioneRevoca());
			LOG.debug(prf + "  param [dataRevoca] = " + revocaBancariaVO.getDataRevoca());
			LOG.debug(prf + "  param [debitoResiduoBanca] = " + revocaBancariaVO.getImpDebitoResiduoBanca());
			LOG.debug(prf + "  param [debitoResiduoFinpiemonte] = " + revocaBancariaVO.getImpDebitoResiduoFinpiemonte());
			LOG.debug(prf + "  param [perCofinanziamentoFinpiemonte] = " + revocaBancariaVO.getPerCofinanziamentoFinpiemonte());
			LOG.debug(prf + "  param [numeroProtocollo] = " + revocaBancariaVO.getNumeroProtocollo());
			LOG.debug(prf + "  param [note] = " + revocaBancariaVO.getNote());
			LOG.debug(prf + "  param [idUtenteIns] = " + revocaBancariaVO.getIdUtenteIns());

		
		} catch(Exception e) {
			logger.error(prf+" ERROR" + e);
			result = false; 
		}finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}
	
	private RevocaBancariaVO popolaRevocaBancaria(RevocaBancariaDTO revocaBancariaDTO, long idRevocaBancaria,
			long idProgetto, long idUtente) {
		
		RevocaBancariaVO revocaBancariaVO =  new RevocaBancariaVO(); 
		
		revocaBancariaVO.setIdUtenteIns(idUtente);
		revocaBancariaVO.setIdProgetto(idProgetto);
		revocaBancariaVO.setIdRevoca(idRevocaBancaria);
		
		revocaBancariaVO.setDataRicezComunicazioneRevoca(revocaBancariaDTO.getDataRicezComunicazioneRevoca());
		revocaBancariaVO.setDataRevoca(revocaBancariaDTO.getDataRevoca());
		revocaBancariaVO.setNumeroProtocollo(revocaBancariaDTO.getNumeroProtocollo());
		revocaBancariaVO.setPerCofinanziamentoFinpiemonte(revocaBancariaDTO.getPerCofinanziamentoFinpiemonte());;
		revocaBancariaVO.setNote(revocaBancariaDTO.getNote());
		revocaBancariaVO.setImpDebitoResiduoBanca(revocaBancariaDTO.getImpDebitoResiduoBanca());
		revocaBancariaVO.setImpDebitoResiduoFinpiemonte(revocaBancariaDTO.getImpDebitoResiduoFinpiemonte());;

		return revocaBancariaVO;
	}
	
	
	public List<RevocaBancariaDTO> getListRevoche(Long idUtente, Long idProgetto, int idModalitaAgev) throws DaoException{
		String prf = "[RevocaBancariaDAOImpl::getListRevoche]";
		LOG.info(prf + "BEGIN");
		
		List<RevocaBancariaDTO> listaRB = new ArrayList<RevocaBancariaDTO>();
		
		try {
			String sql= "SELECT * FROM PBANDI_T_REVOCA_BANCARIA"
					+ "WHERE ID_PROGETTO = " + idProgetto +" \r\n"
					+ " AND ID_MODALITA_AGEVOLAZIONE="+ idModalitaAgev+"\r\n"
					+ " ORDER BY ID_REVOCA_BANCARIA"; 
			
		listaRB = getJdbcTemplate().query(sql, new RevocaBancariaRowMapper()); 	
			
			
			
		}  catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_REVOCA_BANCARIA", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_REVOCA_BANCARIA", e);
		}
		finally {
			LOG.info(prf + " END");
		}
			
		return listaRB;
		
	}



	@Override
	public ArrayList<StoricoRevocaDTO> getStoricoRevoca(Long idUtente, Long idProgetto, int idModalitaAgev) throws DaoException {
		
		String prf = "[RevocaBancariaDAOImpl::getStoricoRevoca]";
		LOG.info(prf + "BEGIN");
		
		ArrayList<StoricoRevocaDTO> listaRB = new ArrayList<StoricoRevocaDTO>();
		
		try {
			String sql= "SELECT\r\n"
					+ "    PTPF.COGNOME,\r\n"
					+ "    PTPF.NOME,\r\n"
					+ "    ptrb.DT_INSERIMENTO,\r\n"
					+ "    ptrb.DT_FINE_VALIDITA\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_REVOCA_BANCARIA ptrb\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptrb.ID_UTENTE_INS\r\n"
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
					+ "    PTRB.ID_PROGETTO = ?\r\n"
					+ "    AND ptrb.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND ptrb.DT_FINE_VALIDITA IS NOT NULL\r\n"
					+ "ORDER BY\r\n"
					+ "    PTRB.ID_REVOCA_BANCARIA DESC "; 
			
			listaRB =  (ArrayList<StoricoRevocaDTO>) getJdbcTemplate().query(sql, new StoricoRevocaRowMapper(), new Object[] {
					idProgetto, idModalitaAgev
			});
			
			
			
		}catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_REVOCA_BANCARIA", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_REVOCA_BANCARIA", e);
		}
		finally {
			LOG.info(prf + " END");
		}
	
		return listaRB;
	}



	@Override
	public RevocaBancariaDTO getRevoca(Long idUtente, Long idProgetto, int idModalitaAgev) throws NullPointerException {
		String prf = "[RevocaBancariaDAOImpl::getRevoca]";
		LOG.info(prf + "BEGIN");
		RevocaBancariaDTO revocaBancariaDB = new RevocaBancariaDTO(); 
		List<RevocaBancariaDTO> lista  = new ArrayList<RevocaBancariaDTO>(); 
		
		try { 
			String sql= "SELECT * FROM PBANDI_T_REVOCA_BANCARIA "
					+ " WHERE ID_PROGETTO = " + idProgetto
					+ " AND DT_FINE_VALIDITA IS NULL "
					+ " AND ID_MODALITA_AGEVOLAZIONE="+ idModalitaAgev+"\r\n"
					+ " ORDER BY DT_INSERIMENTO DESC "; 
			
			lista = getJdbcTemplate().query(sql, new RevocaBancariaRowMapper()); 
			
			if( lista.size()>0) {
				revocaBancariaDB = lista.get(0); 
			} 
			
		}	  catch (Exception e) {
		LOG.error(prf + "Exception while trying to read PBANDI_T_REVOCA_BANCARIA", e);
		
	}finally {
		LOG.info(prf + " END");
		}
		return revocaBancariaDB;
	}
	
	
}
