/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.ammvoservrest.dto.DebitoResiduo;
import it.csi.pbandi.ammvoservrest.dto.RecuperiRevoche;
import it.csi.pbandi.pbgestfinbo.business.service.impl.AmministrativoContabileServiceImpl;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaBeneficiariCreditiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.gestioneGaranzie.DatiDettaglioGaranziaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.objectweb.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class RicercaBeneficiariCreditiDAOImpl extends JdbcDaoSupport implements RicercaBeneficiariCreditiDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public RicercaBeneficiariCreditiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Autowired
	AmministrativoContabileServiceImpl amministrativoService;

	public RicercaBeneficiariCreditiDAOImpl() {
	}

	/*@Autowired
	NeofluxBusinessImpl neofluxBusinessImpl;*/
	
	
	@Autowired
	private AmministrativoContabileServiceImpl amministrativoContabileServiceImpl;
	
	@Autowired
	private AnagraficaBeneficiarioDAO anagraficaBen;
	
	@Autowired
	private PropostaRevocaDAO propostaRevoca;
	
	@Autowired
	private SuggestRevocheDAO suggRevoca;

	
	
	@Override
	public List<BeneficiarioCreditiVO> getElencoBeneficiari(String codiceFiscale, String ndg, String partitaIva, String denominazione, String descBando, String codiceProgetto, HttpServletRequest req) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getElencoBeneficiari]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " NDG: " + ndg);
		LOG.info(prf + " partitaIva: " + partitaIva);
		LOG.info(prf + " denominazione: " + denominazione);
		LOG.info(prf + " descBando: " + descBando);
		LOG.info(prf + " codiceProgetto: " + codiceProgetto);
		LOG.info(prf + " codiceFiscale: " + codiceFiscale); 
		
		//UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		List<BeneficiarioCreditiVO> ben = new ArrayList<BeneficiarioCreditiVO>();

		try {
			StringBuilder query = new StringBuilder();

			String descrizioneBandoFormattato = descBando.replaceAll("'", "''");
			String denominazioneFormattata = denominazione.replaceAll("'", "''");
			
			//LOG.info(prf + " newDescBando: " + newDescBan);
			//LOG.info(prf + " newDenom: " + newDenom);

			query.append("SELECT DISTINCT\r\n"
					+ "	pts.id_soggetto,\r\n"
					+ "    pts.NDG,\r\n"
					+ "    pts.ID_TIPO_SOGGETTO,\r\n"
					+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO, \r\n"
					+ "    ptpf.COGNOME || ' ' || ptpf.NOME AS DENOMINAZIONE_PERSONA_FISICA,\r\n"
					+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
					+ "    ptse.PARTITA_IVA,\r\n"
					+ "    pdfg.ID_FORMA_GIURIDICA,\r\n"
					+ "    pdfg.DESC_FORMA_GIURIDICA,\r\n"
					+ "    ptssa.ID_STATO_AZIENDA,\r\n"
					+ "    pdsa.DESC_STATO_AZIENDA,\r\n"
					+ "    pdr.ID_RATING,\r\n"
					+ "    pdr.CODICE_RATING,\r\n"
					+ "    ptssa.dt_inizio_validita AS data_procedura\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    JOIN pbandi_t_soggetto pts ON prsp.ID_SOGGETTO = pts.id_soggetto\r\n"
					+ "    LEFT JOIN pbandi_r_sogg_progetto_sede prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA \r\n"
					+ "    LEFT JOIN pbandi_t_progetto ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "    LEFT JOIN pbandi_t_Sede ptse ON ptse.ID_SEDE = prsps.ID_SEDE\r\n"
					+ "    LEFT JOIN pbandi_t_ente_giuridico pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "    LEFT JOIN pbandi_d_forma_giuridica pdfg ON pdfg.ID_FORMA_GIURIDICA = pteg.ID_FORMA_GIURIDICA\r\n"
					+ "    LEFT JOIN PBANDI_T_SOGG_STATO_AZIENDA ptssa ON prsp.ID_SOGGETTO = ptssa.ID_SOGGETTO AND ptssa.DT_FINE_VALIDITA IS NULL\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_AZIENDA pdsa ON ptssa.ID_STATO_AZIENDA = pdsa.ID_STATO_AZIENDA\r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO_RATING ptsr ON prsp.ID_SOGGETTO = ptsr.ID_SOGGETTO AND ptsr.DT_AGGIORNAMENTO IS NULL\r\n"
					+ "    LEFT JOIN PBANDI_D_RATING pdr ON ptsr.ID_RATING = pdr.ID_RATING\r\n"
					+ "    LEFT JOIN pbandi_t_domanda ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    LEFT JOIN pbandi_r_bando_linea_intervent prbli on ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "    LEFT JOIN pbandi_t_bando ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n"
					+ "WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsp.PROGR_SOGGETTO_PROGETTO IN (\r\n"
					+ "        SELECT MAX(prsp.PROGR_SOGGETTO_PROGETTO) AS PROGR_SOGGETTO_PROGETTO\r\n"
					+ "        FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "        WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "            AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "        GROUP BY ID_SOGGETTO\r\n"
					+ "    )\r\n");


			// Build della query in base ai campi compilati in front-end

			if (!"".equals(codiceFiscale)) {query.append("AND UPPER(pts.CODICE_FISCALE_SOGGETTO) = UPPER('" + codiceFiscale + "') \r\n");}

			if (!"".equals(ndg)) {query.append("AND UPPER(pts.NDG) = UPPER('" + ndg + "') \r\n");}

			if (!"".equals(partitaIva)) {query.append("AND UPPER(ptse.PARTITA_IVA) = UPPER('" + partitaIva + "') \r\n");}

			if (!"".equals(denominazione)) {query.append("AND ((UPPER(DENOMINAZIONE_ENTE_GIURIDICO) = UPPER('" + denominazioneFormattata + "')) OR (UPPER(ptpf.COGNOME || ' ' || ptpf.NOME) = UPPER('" + denominazioneFormattata + "'))) \r\n");}

			if (!"".equals(descBando)) {query.append("AND UPPER(prbli.NOME_BANDO_LINEA) = UPPER('" + descrizioneBandoFormattato + "') \r\n");}

			if (!"".equals(codiceProgetto)) {query.append("AND UPPER(ptp.CODICE_VISUALIZZATO) = UPPER('" + codiceProgetto + "') \r\n");}

			query.append("AND prsp.DT_FINE_VALIDITA IS NULL \r\n"
					+ " AND rownum < 200 \r\n"
					+ "ORDER BY pteg.DENOMINAZIONE_ENTE_GIURIDICO, DENOMINAZIONE_PERSONA_FISICA ASC");
			
			ben = getJdbcTemplate().query(query.toString(), new RowMapper<BeneficiarioCreditiVO>() {
				
				@Override
				public BeneficiarioCreditiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BeneficiarioCreditiVO bc = new BeneficiarioCreditiVO();
					
					bc.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					bc.setNdg(rs.getString("NDG"));
					bc.setIdTipoSoggetto(rs.getLong("ID_TIPO_SOGGETTO"));
					if(bc.getIdTipoSoggetto() == 1) {
						bc.setDenominazione(rs.getString("DENOMINAZIONE_PERSONA_FISICA"));
					} else {
						bc.setDenominazione(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
					}
					bc.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
					bc.setPartitaIva(rs.getString("PARTITA_IVA"));
					bc.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
					bc.setFormaGiuridica(rs.getString("DESC_FORMA_GIURIDICA"));
					bc.setIdStatoAzienda(rs.getLong("ID_STATO_AZIENDA"));
					bc.setStatoAzienda(rs.getString("DESC_STATO_AZIENDA"));
					bc.setIdRating(rs.getLong("ID_RATING"));
					bc.setRating(rs.getString("CODICE_RATING"));
					bc.setDataProcedura(rs.getDate("DATA_PROCEDURA"));
					
					// Recupero i dettagli del beneficiario
					bc.setListaDettagli(getDettaglioBeneficiario(bc.getIdSoggetto(), codiceProgetto, descrizioneBandoFormattato));
					
					return bc;
				}}
			);

		} catch (Exception e){
			LOG.error(prf + "Exception while trying to read BeneficiarioCreditiVO", e);
			throw new ErroreGestitoException("DaoException while trying to read BeneficiarioCreditiVO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return ben;
	}
	
	
	List<DettaglioBeneficiarioCreditiVO> getDettaglioBeneficiario(Long idSoggetto, String codiceProgetto, String descrizioneBando) {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getElencoBeneficiari::getDettaglioBeneficiario]";
		LOG.info(prf + " BEGIN");
		
		List<DettaglioBeneficiarioCreditiVO> dettaglio = new ArrayList<DettaglioBeneficiarioCreditiVO>();
		StringBuilder dettQuery = new StringBuilder();

		dettQuery.append("SELECT DISTINCT sopro.ID_PROGETTO,\r\n"
				+ "    sopro.ID_SOGGETTO,\r\n"
				+ "    --ptb.ID_BANDO,\r\n"
				+ "    prbli.ID_BANDO,\r\n"
				+ "    sopro.PROGR_SOGGETTO_PROGETTO,\r\n"
				+ "    pro.CODICE_VISUALIZZATO , \r\n"
				+ "    modag.ID_MODALITA_AGEVOLAZIONE AS ID_MODALITA_AGEVOLAZIONE_ORIG,\r\n"
				+ "    modag.DESC_BREVE_MODALITA_AGEVOLAZ AS DESC_BREVE_MOD_AG_ORIG,\r\n"
				+ "    modag.DESC_MODALITA_AGEVOLAZIONE AS DESC_MOD_AG_ORIG,\r\n"
				+ "    modag.ID_MODALITA_AGEVOLAZIONE_RIF,\r\n"
				+ "    modag2.DESC_MODALITA_AGEVOLAZIONE AS DESC_MODALITA_AGEVOLAZIONE_RIF,\r\n"
				+ "    modag2.DESC_BREVE_MODALITA_AGEVOLAZ AS DESC_BREVE_MOD_AG_RIF,\r\n"
				+ "    ( SELECT count(pte.ID_MODALITA_AGEVOLAZIONE) tot\r\n"
				+ "        FROM PBANDI_T_EROGAZIONE pte\r\n"
				+ "        WHERE pte.ID_PROGETTO = sopro.ID_PROGETTO AND pte.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "        GROUP BY pte.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "    ) AS num_erogazioni, -- Usato nella logica per valorizzare Stato agevolazione \r\n"
				+ "    ( SELECT pte.ID_CAUSALE_EROGAZIONE\r\n"
				+ "        FROM pbandi_t_erogazione pte\r\n"
				+ "        WHERE pte.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE AND pte.ID_PROGETTO = sopro.ID_PROGETTO\r\n"
				+ "            AND pte.ID_EROGAZIONE = ( SELECT max(ID_EROGAZIONE)\r\n"
				+ "                FROM PBANDI_T_EROGAZIONE\r\n"
				+ "                WHERE id_progetto = pte.ID_PROGETTO AND ID_MODALITA_AGEVOLAZIONE = pte.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "            )\r\n"
				+ "    ) AS id_ultima_causale,\r\n"
				+ "    conecomodag.QUOTA_IMPORTO_AGEVOLATO AS totale_erogato,\r\n"
				+ "    ( SELECT SUM(b.IMPORTO_EROGAZIONE)\r\n"
				+ "        FROM PBANDI_R_SOGGETTO_PROGETTO a, PBANDI_T_EROGAZIONE b\r\n"
				+ "        WHERE a.ID_TIPO_ANAGRAFICA = '1' AND a.ID_TIPO_BENEFICIARIO <> '4' AND a.ID_PROGETTO = sopro.ID_PROGETTO\r\n"
				+ "            AND a.ID_SOGGETTO = sopro.ID_SOGGETTO AND a.ID_PROGETTO = b.ID_PROGETTO\r\n"
				+ "            AND b.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "        GROUP BY b.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "    ) AS importo_finpiemonte,\r\n"
				+ "    ( SELECT SUM(IMPORTO_FINANZIAMENTO_BANCA)\r\n"
				+ "        FROM PBANDI_R_SOGGETTO_PROGETTO a, PBANDI_R_SOGGETTO_DOMANDA b, PBANDI_T_CONTO_ECONOMICO c\r\n"
				+ "            JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = c.ID_CONTO_ECONOMICO\r\n"
				+ "        WHERE a.ID_PROGETTO = sopro.ID_PROGETTO AND a.ID_SOGGETTO = sopro.ID_SOGGETTO\r\n"
				+ "            AND a.id_tipo_anagrafica = 1 AND a.id_tipo_beneficiario <> 4\r\n"
				+ "            AND a.PROGR_SOGGETTO_DOMANDA = b.PROGR_SOGGETTO_DOMANDA AND b.ID_DOMANDA = c.ID_DOMANDA\r\n"
				+ "            AND c.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO\r\n"
				+ "            AND prcema.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE AND c.DT_FINE_VALIDITA IS NULL\r\n"
				+ "    ) AS totale_banca,\r\n"
				+ "    /*( SELECT SUM(ptrce.IMPORTO_AMMESSO_FINANZIAMENTO)\r\n"
				+ "        FROM PBANDI_T_RIGO_CONTO_ECONOMICO ptrce, PBANDI_T_CONTO_ECONOMICO ptce, PBANDI_T_PROGETTO ptp\r\n"
				+ "        WHERE ptrce.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO AND ptce.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
				+ "            AND ptce.DT_FINE_VALIDITA IS NULL AND ptp.ID_PROGETTO = sopro.ID_PROGETTO\r\n"
				+ "    ) AS totale_ammesso_old,*/\r\n"
				+ "    conecomodag.IMPORTO_AMMESSO_FINPIS,\r\n"
				+ "    IMPORTO_FINANZIAMENTO_BANCA,\r\n"
				+ "    soprobaben.DENOM_SOGGETTO_TERZO AS cessione_soggetto_terzo,\r\n"
				+ "    pdsc.ID_STATO_CESSIONE,\r\n"
				+ "    pdsc.DESC_STATO_CESSIONE,\r\n"
				+ "    CASE WHEN sopro.ID_PROGETTO IN (\r\n"
				+ "            SELECT a.ID_PROGETTO\r\n"
				+ "            FROM PBANDI_T_LIBERAZ_BANCA a\r\n"
				+ "            WHERE a.DT_FINE_VALIDITA IS NULL\r\n"
				+ "        ) THEN 'Si' ELSE 'No'\r\n"
				+ "    END AS Rev_Mandato_Ban,\r\n"
				+ "    CASE WHEN sopro.ID_PROGETTO IN (\r\n"
				+ "            SELECT p.ID_PROGETTO\r\n"
				+ "            FROM PBANDI_T_REVOCA p\r\n"
				+ "            WHERE p.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "        ) THEN 'Si' ELSE 'No'\r\n"
				+ "    END AS Rev_amm,\r\n"
				+ "    --ptb.TITOLO_BANDO,\r\n" // non leggiamo più da questo campo
				+ "    prbli.NOME_BANDO_LINEA,\r\n"
				+ "    ptrb.DT_REVOCA AS data_revoca_bancaria,\r\n"
				+ "    ptlb.ID_LIBERAZ_BANCA,\r\n"
				+ "    pdscf.DESC_STATO_CREDITO_FP\r\n"
				+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
				+ "    LEFT JOIN PBANDI_T_PROGETTO pro ON sopro.ID_PROGETTO = pro.ID_PROGETTO\r\n"
				+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO coneco ON pro.ID_DOMANDA = coneco.ID_DOMANDA AND coneco.DT_FINE_VALIDITA IS NULL\r\n"
				+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV conecomodag ON coneco.ID_CONTO_ECONOMICO = conecomodag.ID_CONTO_ECONOMICO\r\n"
				+ "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modag ON conecomodag.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modag2 ON modag2.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE_RIF\r\n"
				+ "    LEFT JOIN PBANDI_T_EROGAZIONE ero ON sopro.ID_PROGETTO = ero.ID_PROGETTO AND conecomodag.ID_MODALITA_AGEVOLAZIONE = ero.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "    LEFT JOIN PBANDI_R_SOGG_PROG_BANCA_BEN soprobaben ON sopro.PROGR_SOGGETTO_PROGETTO = soprobaben.PROGR_SOGGETTO_PROGETTO AND soprobaben.DT_FINE_VALIDITA IS NULL\r\n"
				+ "    LEFT JOIN pbandi_t_domanda ptd ON ptd.ID_DOMANDA = pro.ID_DOMANDA\r\n"
				+ "    LEFT JOIN pbandi_r_bando_linea_intervent prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "    --LEFT JOIN pbandi_t_bando ptb ON ptb.ID_BANDO = prbli.ID_BANDO -- La descrizione del bando ora vene letta da pbandi_r_bando_linea_intervent\r\n"
				+ "    LEFT JOIN pbandi_t_revoca_bancaria ptrb ON ptrb.ID_PROGETTO = sopro.ID_PROGETTO AND ptrb.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE AND ptrb.DT_FINE_VALIDITA IS NULL\r\n"
				+ "    LEFT JOIN pbandi_t_liberaz_banca ptlb ON ptlb.ID_PROGETTO = sopro.ID_PROGETTO AND ptlb.DT_FINE_VALIDITA IS NULL AND ptlb.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "    LEFT JOIN PBANDI_T_CESSIONE_QUOTA_FP ptcq ON sopro.ID_PROGETTO = ptcq.ID_PROGETTO AND ptcq.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE AND ptcq.DT_FINE_VALIDITA IS NULL\r\n"
				+ "    LEFT JOIN PBANDI_D_STATO_CESSIONE pdsc ON ptcq.ID_STATO_CESSIONE = pdsc.ID_STATO_CESSIONE\r\n"
				+ "    LEFT JOIN PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf ON sopro.PROGR_SOGGETTO_PROGETTO = prspscf.PROGR_SOGGETTO_PROGETTO AND prspscf.DT_FINE_VALIDITA IS NULL AND prspscf.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "    LEFT JOIN PBANDI_D_STATO_CREDITO_FP pdscf ON pdscf.ID_STATO_CREDITO_FP = prspscf.ID_STATO_CREDITO_FP\r\n"
				+ "WHERE sopro.ID_TIPO_ANAGRAFICA = '1' AND sopro.ID_TIPO_BENEFICIARIO <> '4'\r\n"
				+ "	   AND conecomodag.QUOTA_IMPORTO_AGEVOLATO > 0\r\n"
				+ "    AND sopro.DT_FINE_VALIDITA IS NULL AND sopro.id_soggetto = " + idSoggetto + "\r\n");
		
		if (!"".equals(codiceProgetto)) {dettQuery.append("AND UPPER(pro.CODICE_VISUALIZZATO) = UPPER('" + codiceProgetto + "') \r\n");}
		if (!"".equals(descrizioneBando)) {dettQuery.append("AND UPPER(prbli.NOME_BANDO_LINEA) = UPPER('" + descrizioneBando + "') \r\n");}
		
		dettQuery.append("ORDER BY CODICE_VISUALIZZATO , DESC_MODALITA_AGEVOLAZIONE_RIF");
		
		dettaglio = getJdbcTemplate().query(dettQuery.toString(), new RowMapper<DettaglioBeneficiarioCreditiVO>() {
			@Override
			public DettaglioBeneficiarioCreditiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DettaglioBeneficiarioCreditiVO bc = new DettaglioBeneficiarioCreditiVO();

				bc.setIdBando(rs.getLong("ID_BANDO"));
				bc.setIdProgetto(rs.getLong("ID_PROGETTO"));
				bc.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
				bc.setProgrSoggettoProgetto(rs.getLong("PROGR_SOGGETTO_PROGETTO"));
				
				bc.setIdModalitaAgevolazioneOrig(rs.getLong("ID_MODALITA_AGEVOLAZIONE_ORIG"));
				bc.setDescBreveModalitaAgevolazioneOrig(rs.getString("DESC_BREVE_MOD_AG_ORIG"));
				bc.setDescModalitaAgevolazioneOrig(rs.getString("DESC_MOD_AG_ORIG"));
				bc.setIdModalitaAgevolazioneRif(rs.getLong("ID_MODALITA_AGEVOLAZIONE_RIF"));
				bc.setDescBreveModalitaAgevolazioneRif(rs.getString("DESC_BREVE_MOD_AG_RIF"));
				bc.setDescModalitaAgevolazioneRif(rs.getString("DESC_MODALITA_AGEVOLAZIONE_RIF"));
				
				// Per visualizzare solo Contributo, Finanziamento o Garanzia
				switch (bc.getIdModalitaAgevolazioneRif().intValue()) {
				case 1:
					bc.setDispDescAgevolazione("Contributo");
					break;
				case 5:
					bc.setDispDescAgevolazione("Finanziamento");
					break;
				case 10:
					bc.setDispDescAgevolazione("Garanzia");
					break;
				default:
					bc.setDispDescAgevolazione(bc.getIdModalitaAgevolazioneRif().toString());
					break;
				}

				bc.setCodProgetto(rs.getString("CODICE_VISUALIZZATO"));
				bc.setTitoloBando(rs.getString("NOME_BANDO_LINEA"));
				
				// Valorizzo stato agevolazione
				/* erogato se esiste almeno un record nella pbandi_t_erogazione, concesso altrimenti */
				Long tempNumErogazioni = rs.getLong("NUM_EROGAZIONI");
				if(tempNumErogazioni > 0) {
					if(Objects.equals(bc.getIdModalitaAgevolazioneRif(), 10L)) { // Garanzia
						bc.setDispStatoAgevolazione("Escusso");
					} else {
						bc.setDispStatoAgevolazione("Erogato");
					}
				} else {
					bc.setDispStatoAgevolazione("Concesso");
				}
				bc.setNumErogazioni(tempNumErogazioni);
				
				bc.setIdUltimaCausale(rs.getLong("ID_ULTIMA_CAUSALE"));
				bc.setImportoAmmesso(rs.getBigDecimal("IMPORTO_AMMESSO_FINPIS")); // Prima era totale_ammesso_old
				bc.setTotaleErogatoFin(rs.getBigDecimal("IMPORTO_FINPIEMONTE"));
				bc.setTotaleErogatoBanca(rs.getBigDecimal("TOTALE_BANCA"));
				bc.setCreditoResiduo(null);
				bc.setAgevolazione(null);
				bc.setTotaleErogato(rs.getBigDecimal("totale_erogato"));
				bc.setIdStatoCessione(rs.getLong("ID_STATO_CESSIONE"));
				bc.setDescStatoCredito(rs.getString("DESC_STATO_CREDITO_FP")); // questo campo in realtà non va valorizzato in questo modo, da sistemare la query 21/08/23
				
				bc.setStatoCessione(rs.getString("DESC_STATO_CESSIONE"));
				bc.setDataRevocaBancaria(rs.getDate("DATA_REVOCA_BANCARIA"));
				
				// Valorizzo Liberazione mandato Banca
				/* Se si tratta di Contributo (id 1) o Garanzia (id 10) non va valorizzata, visualizzo il trattino */
				//bc.setLibMandatoBanca(rs.getString("ID_LIBERAZ_BANCA")); // OLD
				if(Objects.equals(bc.getIdModalitaAgevolazioneRif(), 5L)) { // Finanziamento id 5
					String libBancaTemp = null;
							libBancaTemp =  rs.getString("ID_LIBERAZ_BANCA");
					if(libBancaTemp != null) { // Da capire perché lo valorizzo solo con id 1 (ho già trovato questa logica ma senza commento)
						bc.setLibMandatoBanca("Si");
					} else {
						bc.setLibMandatoBanca("No");
					}
				} else {
					bc.setLibMandatoBanca("-");
				}
				
				
				// valorizzo Revoca Amministrativa 
				String revoche = setRevocaAmministrativa(bc.getIdModalitaAgevolazioneRif(), bc.getIdProgetto());  
				if(revoche!=null) {
					bc.setRevocaAmministrativa("Si");
					bc.setListaRevoche(revoche);
				} else {
					bc.setRevocaAmministrativa("No");
				}
				
				return bc;
			}});	
		
		LOG.info(prf + " END");
		
		return dettaglio;
	}



	private String setRevocaAmministrativa(Long idModalitaAgevolazioneRif, Long idProgetto) {
		// TODO: RECUPERARE la revoca corrispondente al dettaglio poi controllare i campi in base agli id_modalita_agevolazione_ref 
		
		List <Long> revoche = new ArrayList<Long>();
		StringBuilder getRevoche =  new StringBuilder();
		
		String getRevoca="SELECT ptgr.ID_GESTIONE_REVOCA AS id_revoca\r\n"
				+ "FROM PBANDI_T_GESTIONE_REVOCA ptgr \r\n"
				+ "WHERE ptgr.ID_STATO_REVOCA IN (8, 9, 10)\r\n"
				+ "	AND ptgr.ID_TIPOLOGIA_REVOCA = 3\r\n"
				+ "	AND ptgr.ID_PROGETTO = ?\r\n"; 
		getRevoche.append(getRevoca);
		if(idModalitaAgevolazioneRif == null) {
			return null; 
		}
		
		switch (idModalitaAgevolazioneRif.intValue()) {
		case 1:
			getRevoche.append("AND ptgr.IMP_CONTRIB_REVOCA_RECU IS NOT NULL "); 
			break;
		case 5:
			getRevoche.append("AND ptgr.IMP_FINANZ_REVOCA_RECU IS NOT NULL "); 
			break;
		case 10:
			getRevoche.append("AND ptgr.IMP_GARANZIA_REVOCA_RECUPERO IS NOT NULL "); 
			break;
		}
		
		revoche = getJdbcTemplate().queryForList(getRevoche.toString(), Long.class, 
				new Object [] { idProgetto });
		
		if (revoche.size()>0) {			
			return revoche.stream()
					.map(Object::toString)
					.collect(Collectors.joining(","));
		}
		
		
		return null;	
	}

	@Override
	public List<String> getSuggestion(String value, String id) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getSuggestion]";
		LOG.info(prf + " BEGIN");
			
		List<String> ben = null;
		
		try {
			String query = "";
			
		//FRONT-END FIELD IDS
			//codFis  1
			//nag     2 NOW NDG
			//partIVA 3
			//denom   4
			//descBan 5
			//codProg 6
			
			// DEBUG
			//LOG.info("Value: " + value);
			//LOG.info("Id: " + id);
			
			// Query differenti per ogni campo, stesso servizio
			switch(id) {
			
			case "1":
				query = "SELECT DISTINCT pts.CODICE_FISCALE_SOGGETTO\r\n"
						+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
						+ "LEFT JOIN PBANDI_T_SOGGETTO pts ON sopro.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "WHERE sopro.ID_TIPO_ANAGRAFICA = '1' AND sopro.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(CODICE_FISCALE_SOGGETTO) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
						+ "ORDER BY pts.CODICE_FISCALE_SOGGETTO ASC";
			break;
			
			case "2":
				query = "SELECT DISTINCT pts.NDG AS ID_SOGGETTO\r\n"
						+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
						+ "LEFT JOIN PBANDI_T_SOGGETTO pts ON sopro.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "WHERE sopro.ID_TIPO_ANAGRAFICA = '1' AND sopro.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(pts.NDG) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
						+ "ORDER BY pts.NDG ASC";
			break;
			
			case "3":
				query = "SELECT DISTINCT pts.PARTITA_IVA\r\n"
						+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
						+ "LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE soprose ON sopro.PROGR_SOGGETTO_PROGETTO = soprose.PROGR_SOGGETTO_PROGETTO AND soprose.ID_TIPO_SEDE = 1\r\n"
						+ "LEFT JOIN PBANDI_T_SEDE pts ON soprose.ID_SEDE = pts.ID_SEDE\r\n"
						+ "WHERE sopro.ID_TIPO_ANAGRAFICA = '1' AND sopro.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(pts.PARTITA_IVA) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
						+ "ORDER BY pts.PARTITA_IVA ASC";
			break;
			
			case "4":
				/*	OLD
				query.append("SELECT DISTINCT TRIM(persfis.COGNOME) || ' ' || TRIM(persfis.NOME) AS cognome_nome\r\n"
						+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
						+ "LEFT JOIN PBANDI_T_PERSONA_FISICA persfis ON sopro.ID_PERSONA_FISICA = persfis.ID_PERSONA_FISICA\r\n"
						+ "WHERE\r\n"
						+ "	sopro.ID_TIPO_ANAGRAFICA = '1' AND sopro.ID_TIPO_BENEFICIARIO <> '4' AND \r\n"
						+ "	(UPPER(persfis.COGNOME) || ' ' || UPPER(persfis.NOME)) LIKE UPPER('%" + value + "%') AND ROWNUM <= 100\r\n"
						+ "	--OR (UPPER(persfis.NOME) || ' ' || UPPER(persfis.COGNOME)) LIKE UPPER('%giovanni%')\r\n"
						+ "UNION\r\n"
						+ "	SELECT DISTINCT TRIM(entegiu.DENOMINAZIONE_ENTE_GIURIDICO) AS sk\r\n"
						+ "	FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
						+ "	LEFT JOIN PBANDI_T_ENTE_GIURIDICO entegiu ON sopro.ID_ENTE_GIURIDICO = entegiu.ID_ENTE_GIURIDICO \r\n"
						+ "	WHERE sopro.ID_TIPO_ANAGRAFICA = '1' AND sopro.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(entegiu.DENOMINAZIONE_ENTE_GIURIDICO) LIKE UPPER('%" + value + "%') AND ROWNUM <= 100\r\n"
						+ "ORDER BY cognome_nome ASC");*/
				query = "WITH selezione AS (\r\n"
						+ "	SELECT DISTINCT ptpf.COGNOME || ' ' || ptpf.NOME AS denominazione, prsp.ID_SOGGETTO\r\n"
						+ "	FROM pbandi_r_soggetto_progetto prsp\r\n"
						+ "		LEFT JOIN pbandi_t_persona_fisica ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
						+ "	WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1 AND ptpf.COGNOME IS NOT NULL\r\n"
						+ "	--ORDER BY denominazione ASC \r\n"
						+ "	UNION SELECT DISTINCT pteg.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione, prsp.ID_SOGGETTO\r\n"
						+ "	FROM pbandi_r_soggetto_progetto prsp\r\n"
						+ "		LEFT JOIN pbandi_t_ente_giuridico pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO AND pteg.DENOMINAZIONE_ENTE_GIURIDICO IS NOT NULL \r\n"
						+ "	WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1 \r\n"
						+ "	ORDER BY denominazione ASC )\r\n"
						+ "SELECT selezione.denominazione\r\n"
						+ "FROM selezione\r\n"
						+ "WHERE UPPER(denominazione) LIKE UPPER('%' || ? || '%')\r\n"
						+ "AND ROWNUM <= 100";
			break;
			
			case "5":
				//String newDescBan = value.replaceAll("'", "''");
				
				query = "SELECT DISTINCT prbli.NOME_BANDO_LINEA\r\n"
						+ "FROM PBANDI_R_BANDO_LINEA_INTERVENT prbli\r\n"
						+ "WHERE UPPER(prbli.NOME_BANDO_LINEA) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
						+ "ORDER BY prbli.NOME_BANDO_LINEA ASC";
			break;
			
			case "6":
				query = "SELECT DISTINCT ptp.CODICE_VISUALIZZATO\r\n"
						+ "FROM PBANDI_T_PROGETTO ptp\r\n"
						+ "WHERE UPPER(ptp.CODICE_VISUALIZZATO) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
						+ "ORDER BY ptp.CODICE_VISUALIZZATO ASC";
			break;
			
			default:				
				LOG.error("There was an error with ids");				
			}
			
			
			ben = getJdbcTemplate().queryForList(query.toString(), String.class, new Object [] { value });
			
			// DEBUG
			//LOG.info(ben);
			
		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read BeneficiarioCreditiVO - suggestion", e);
			throw new ErroreGestitoException("DaoException while trying to read BeneficiarioCreditiVO - suggestion", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return ben;
			
	}
	
		
	@Override
	public List<RevocaAmministrativaCreditiVO> getRevocaAmministrativa(Long idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, String listRevoche, Long idUtente) throws Exception {

		String prf = "[RicercaBeneficiariCreditiDAOImpl::getRevocaAmministrativa]";
		LOG.info(prf + " BEGIN");
			
		List<RevocaAmministrativaCreditiVO> revocam = new ArrayList<RevocaAmministrativaCreditiVO>();
		
		try {
			/*StringBuilder quePec = new StringBuilder();
			
			quePec.append("WITH importoAmesso AS (\r\n"
					+ "	SELECT\r\n"
					+ "		SUM (a.IMPORTO_AMMESSO_FINANZIAMENTO) AS importo_amesso,\r\n"
					+ "		c.ID_PROGETTO\r\n"
					+ "	FROM\r\n"
					+ "		PBANDI_T_RIGO_CONTO_ECONOMICO a,\r\n"
					+ "		PBANDI_T_CONTO_ECONOMICO b,\r\n"
					+ "		PBANDI_T_PROGETTO c,\r\n"
					+ "		PBANDI_D_STATO_CONTO_ECONOMICO d,\r\n"
					+ "		PBANDI_D_TIPOLOGIA_CONTO_ECON e\r\n"
					+ "	WHERE\r\n"
					+ "		c.ID_DOMANDA = b.ID_DOMANDA\r\n"
					+ "		AND b.DT_FINE_VALIDITA is NULL\r\n"
					+ "		AND b.ID_CONTO_ECONOMICO = a.ID_CONTO_ECONOMICO\r\n"
					+ "		AND b.ID_STATO_CONTO_ECONOMICO = d.ID_STATO_CONTO_ECONOMICO\r\n"
					+ "		AND d.ID_TIPOLOGIA_CONTO_ECONOMICO = e.ID_TIPOLOGIA_CONTO_ECONOMICO\r\n"
					+ "		AND e.ID_TIPOLOGIA_CONTO_ECONOMICO = 2\r\n"
					+ "	GROUP BY\r\n"
					+ "		c.ID_PROGETTO\r\n"
					+ "),\r\n"
					+ "totale_erogato AS (\r\n"
					+ "	SELECT\r\n"
					+ "		SUM(b.IMPORTO_EROGAZIONE) AS totale,\r\n"
					+ "		a.id_progetto,\r\n"
					+ "		b.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "	FROM\r\n"
					+ "		PBANDI_R_SOGGETTO_PROGETTO a,\r\n"
					+ "		PBANDI_T_EROGAZIONE b\r\n"
					+ "	WHERE\r\n"
					+ "		a.ID_TIPO_ANAGRAFICA = '1'\r\n"
					+ "		AND a.ID_TIPO_BENEFICIARIO <> '4'\r\n"
					+ "		AND a.ID_PROGETTO = b.ID_PROGETTO\r\n"
					+ "	GROUP BY\r\n"
					+ "		a.id_progetto,\r\n"
					+ "		b.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ ")\r\n"
					+ "SELECT\r\n"
					+ "	prsp.ID_PROGETTO,\r\n"
					+ "	ptr.ID_REVOCA,\r\n"
					+ "	pdcb.ID_CAUSALE_,\r\n"
					+ "	pdcb.DESC_CAUSALE_BLOCCO,\r\n"
					+ "	pdca.DESC_CATEG_ANAGRAFICA,\r\n"
					+ "	pdca.ID_CATEG_ANAGRAFICA,\r\n"
					+ "	ptgr.DT_GESTIONE,\r\n"
					+ "	ptgr.DT_STATO_REVOCA,\r\n"
					+ "	pdsr.DESC_STATO_REVOCA,\r\n"
					+ "	a.importo_amesso,\r\n"
					+ "	pdmr.ID_MOTIVO_REVOCA,\r\n"
					+ "	pdmr.DESC_MOTIVO_REVOCA,\r\n"
					+ "	te.totale AS totale_erogato,\r\n"
					+ "	(\r\n"
					+ "		SELECT\r\n"
					+ "			sum(ptre.IMPORTO_RECUPERO)\r\n"
					+ "		FROM\r\n"
					+ "			pbandi_t_recupero ptre\r\n"
					+ "			LEFT JOIN pbandi_r_soggetto_progetto prsp2 ON prsp2.ID_PROGETTO = ptre.ID_PROGETTO\r\n"
					+ "		WHERE\r\n"
					+ "			prsp2.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "			AND prsp2.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "			AND ptre.ID_MODALITA_AGEVOLAZIONE = ptr.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "			AND prsp2.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "	) AS importo_recupero,\r\n"
					+ "	ptgr.FLAG_ORDINE_RECUPERO,\r\n"
					+ "	(\r\n"
					+ "		SELECT\r\n"
					+ "			sum(ptre.IMPORTO)\r\n"
					+ "		FROM\r\n"
					+ "			pbandi_t_revoca ptre\r\n"
					+ "			LEFT JOIN pbandi_r_soggetto_progetto prsp2 ON prsp2.ID_PROGETTO = ptre.ID_PROGETTO\r\n"
					+ "		WHERE\r\n"
					+ "			prsp2.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "			AND prsp2.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "			AND ptre.ID_MODALITA_AGEVOLAZIONE = ptr.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "			AND prsp2.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "	) AS importo_revoca,\r\n"
					+ "	(\r\n"
					+ "		SELECT\r\n"
					+ "			SUM(imp_quota_sogg_finanziatore) AS importo_bando\r\n"
					+ "		FROM\r\n"
					+ "			PBANDI_R_SOGGETTO_PROGETTO prsp3\r\n"
					+ "			LEFT JOIN pbandi_r_prog_sogg_finanziat prpsf ON prpsf.ID_PROGETTO = prsp3.ID_PROGETTO\r\n"
					+ "			LEFT JOIN pbandi_d_soggetto_finanziatore pdsf ON pdsf.ID_SOGGETTO_FINANZIATORE = prpsf.ID_SOGGETTO_FINANZIATORE\r\n"
					+ "		WHERE\r\n"
					+ "			prsp3.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "			AND prsp3.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "			AND pdsf.FLAG_AGEVOLATO = 'S'\r\n"
					+ "			AND prsp3.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "	) AS importo_bando , ptgr.NUMERO_REVOCA \r\n"
					+ "FROM\r\n"
					+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "	LEFT JOIN pbandi_t_revoca ptr ON ptr.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "	LEFT JOIN pbandi_t_gestione_revoca ptgr ON ptgr.ID_GESTIONE_REVOCA = ptr.ID_GESTIONE_REVOCA\r\n"
					+ "	LEFT JOIN pbandi_d_causale_blocco pdcb ON ptgr.ID_CAUSALE_BLOCCO = pdcb.ID_CAUSALE_BLOCCO\r\n"
					+ "	LEFT JOIN pbandi_d_categ_anagrafica pdca ON pdca.ID_CATEG_ANAGRAFICA = ptgr.ID_CATEG_ANAGRAFICA\r\n"
					+ "	LEFT JOIN pbandi_d_stato_revoca pdsr ON pdsr.ID_STATO_REVOCA = ptgr.ID_STATO_REVOCA\r\n"
					+ "	LEFT JOIN pbandi_t_progetto ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "	LEFT JOIN pbandi_d_motivo_revoca pdmr ON pdmr.ID_MOTIVO_REVOCA = ptr.ID_MOTIVO_REVOCA\r\n"
					+ "	LEFT JOIN totale_erogato te ON prsp.ID_PROGETTO = te.id_progetto AND te.ID_MODALITA_AGEVOLAZIONE = ptr.ID_MODALITA_AGEVOLAZIONE \r\n"
					+ "	LEFT JOIN importoAmesso a ON a.id_progetto = prsp.ID_PROGETTO\r\n"
					+ "WHERE\r\n"
					+ "	prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "	AND ptgr.ID_TIPOLOGIA_REVOCA = 3\r\n"
					+ "	AND ptgr.DT_FINE_VALIDITA IS NULL\r\n"
					+ "	AND prsp.ID_PROGETTO = ?\r\n"
					+ "	AND ptr.ID_MODALITA_AGEVOLAZIONE = ?");*/
			
			// Nuova query
			String getRevocaAmm = "SELECT\r\n"
					+ "    ptgr.ID_GESTIONE_REVOCA,\r\n"
					+ "    ptgr.NUMERO_REVOCA,\r\n"
					+ "    prsp.ID_PROGETTO,\r\n"
					+ "    pdcb.DESC_CAUSALE_BLOCCO,\r\n"
					+ "    pdcb.ID_CAUSALE_BLOCCO,\r\n"
					+ "    -- corrisponde alla causa della revoca\r\n"
					+ "    ptgr.DT_GESTIONE,\r\n"
					+ "    -- corrisponde alla DATA provvedimento revoca\r\n"
					+ "    (\r\n"
					+ "        SELECT\r\n"
					+ "            QUOTA_IMPORTO_AGEVOLATO\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_R_CONTO_ECONOM_MOD_AGEV a,\r\n"
					+ "            PBANDI_T_CONTO_ECONOMICO b,\r\n"
					+ "            PBANDI_T_PROGETTO c\r\n"
					+ "        WHERE\r\n"
					+ "            a.ID_CONTO_ECONOMICO = b.ID_CONTO_ECONOMICO\r\n"
					+ "            AND b.ID_DOMANDA = c.ID_DOMANDA\r\n"
					+ "            AND a.ID_MODALITA_AGEVOLAZIONE = pdma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "            AND b.DT_FINE_VALIDITA IS NULL\r\n"
					+ "            AND c.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "    ) AS importo_concesso,\r\n"
					+ "    pdsr.DESC_STATO_REVOCA,\r\n"
					+ "    ptgr.DT_NOTIFICA,\r\n"
					+ "    -- corrisponde alla DATA notifica provvedimento revoca\r\n"
					+ "    (\r\n"
					+ "        SELECT\r\n"
					+ "            sum(pte.IMPORTO_EROGAZIONE)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_EROGAZIONE pte\r\n"
					+ "        WHERE\r\n"
					+ "            pte.ID_MODALITA_AGEVOLAZIONE = pdma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "            AND pte.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
					+ "    ) AS importo_erogato_finp,\r\n"
					+ "    (\r\n"
					+ "       CASE\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 1 THEN  ( NVL(ptgr.IMP_CONTRIB_REVOCA_RECU, 0) + NVL(ptgr.IMP_CONTRIB_REVOCA_NO_RECU, 0) )\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 5 THEN  (nvl (ptgr.IMP_FINANZ_REVOCA_RECU ,0)+ nvl (ptgr.IMP_FINANZ_REVOCA_NO_RECU ,0)+ nvl (ptgr.IMP_FINANZ_PRERECUPERO,0)) \r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 10 THEN (nvl (ptgr.IMP_GARANZIA_REVOCA_NO_RECU ,0)+ nvl (ptgr.IMP_GARANZIA_REVOCA_RECUPERO ,0)+ nvl(ptgr.IMP_GARANZIA_PRERECUPERO,0)) \r\n"
					+ "            ELSE 0\r\n"
					+ "        END\r\n"
					+ "    ) AS importo_totale_revocato,\r\n"
					+ "    (\r\n"
					+ "        CASE\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 1 THEN ptgr.IMP_CONTRIB_INTERESSI\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 5 THEN ptgr.IMP_FINANZ_INTERESSI\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 10 THEN ptgr.IMP_GARANZIA_INTERESSI\r\n"
					+ "            ELSE 0\r\n"
					+ "        END\r\n"
					+ "    ) AS importo_agevolazione,\r\n"
					+ "    (\r\n"
					+ "        CASE\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 1 THEN ptgr.IMP_CONTRIB_REVOCA_RECU\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 5 THEN ptgr.IMP_FINANZ_REVOCA_RECU\r\n"
					+ "            WHEN pdma.ID_MODALITA_AGEVOLAZIONE_RIF = 10 THEN ptgr.IMP_GARANZIA_REVOCA_RECUPERO\r\n"
					+ "            ELSE 0\r\n"
					+ "        END\r\n"
					+ "    ) AS QUOTA_CAPITALE,\r\n"
					+ "    ptgr.ID_GESTIONE_REVOCA\r\n"
					+ "FROM\r\n"
					+ "     PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_T_GESTIONE_REVOCA ptgr ON prsp.ID_PROGETTO = ptgr.ID_PROGETTO\r\n"
					+ "    AND ptgr.DT_FINE_VALIDITA IS NULL\r\n"
					+ "    LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON pdcb.ID_CAUSALE_BLOCCO = ptgr.ID_CAUSALE_BLOCCO\r\n"
					+ "    LEFT JOIN pbandi_t_progetto ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO \r\n"
					+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_DOMANDA = ptp.ID_DOMANDA and ptce.DT_FINE_VALIDITA IS NULL \r\n"
					+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO \r\n"
					+ "    --LEFT JOIN PBANDI_T_REVOCA ptr ON ptr.ID_GESTIONE_REVOCA = ptgr.ID_GESTIONE_REVOCA\r\n"
					+ "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON prcema.ID_MODALITA_AGEVOLAZIONE = pdma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_REVOCA pdsr ON pdsr.ID_STATO_REVOCA = ptgr.ID_STATO_REVOCA\r\n"
					+ "WHERE\r\n"
					+ "    prsp.ID_PROGETTO = ?\r\n"
					+ "    AND pdma.ID_MODALITA_AGEVOLAZIONE = ?\r\n"
					+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND ptgr.ID_TIPOLOGIA_REVOCA = 3\n"
					+ "	   AND ptgr.ID_GESTIONE_REVOCA IN ("+listRevoche+")";
			
			/*5.1.9	Recupero quota capitale
			TBD Nuovo servizio di Amministrativo Contabile
			5.1.10	Recupero agevolazione
			TBD Nuovo servizio di Amministrativo Contabile*/ 
			// questi importi saranno valorizzati dal un nuovo servizio di amm.vo contabile non ancora implementato
			
			
			revocam = getJdbcTemplate().query(getRevocaAmm, new RevocaAmministrativaCreditiVORowMapper(), new Object[] {idProgetto, idModalitaAgevolazione});
			
			// Recupero gli importi da amm.vo contabile
			for (RevocaAmministrativaCreditiVO itemRevoca : revocam) {
				RecuperiRevoche recuperoProvv = new RecuperiRevoche();

				recuperoProvv = amministrativoContabileServiceImpl.callToRecuperiRevoche(Integer.parseInt(itemRevoca.getIdProgetto()), idModalitaAgevolazione, idModalitaAgevolazioneRif, idUtente, itemRevoca.getNumeroRevoca(), itemRevoca.getIdGestioneRevoca());
				LOG.info(prf + " recuperoObjAmmvo: " + recuperoProvv);

				if(recuperoProvv != null) {
					if(recuperoProvv.getOneriRecuperati() != null) {
						itemRevoca.setRecuperoAgevolazione(BigDecimal.valueOf(recuperoProvv.getOneriRecuperati()));
					}
					if(recuperoProvv.getCapitaleRecuperato() != null) {
						itemRevoca.setRecuperoQuotaCapitale(BigDecimal.valueOf(recuperoProvv.getCapitaleRecuperato()));
					}

				}
			}
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BeneficiarioCreditiVO - getRevocaAmministrativa", e);
			throw new ErroreGestitoException("DaoException while trying to read BeneficiarioCreditiVO - getRevocaAmministrativa", e);
			//revocam.get(0).setEsito(false);
			//revocam.get(0).setExc(e.toString());
			//return revocam;
		}
				
		return revocam;
			
	}
	
	
	
	@Override
	public DebitoResiduo getCreditoResiduoEAgevolazione(int idProgetto, int idBando, int idModalitaAgevolazioneOrig, int idModalitaAgevolazioneRif, Long idUtente) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getCreditoResiduoEAgevolazione]";
		LOG.info(prf + " BEGIN");
		
		DebitoResiduo deb = new DebitoResiduo();
		
		try {
			deb = amministrativoContabileServiceImpl.callToDebitoResiduo(idProgetto, idBando, idModalitaAgevolazioneOrig, idModalitaAgevolazioneRif, idUtente);							

		} catch (Exception e) {
			LOG.error("Exception in getCreditoResiduoEAgevolazione: " + e);
		}
		
		LOG.info(prf + " END");
		
		if(deb == null) {
			return null;
		}

		return deb;
		
	}
	
	
	
	
	// Attività Istruttore Area Crediti //
	
	
	/*@Override
	public List<BoxListVO> getBoxList(Long idModalitaAgevolazione, Long idArea) throws Exception, InvalidParameterException {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getBoxList]";
		LOG.info(prf + " BEGIN");
		
		LOG.info("È stato passato " + idModalitaAgevolazione + " come valore di idModalitaAgevolazione");
		LOG.info("È stato passato " + idArea + " come valore di idArea");
		
		List<BoxListVO> boxs = null;
		
		if(idArea <= 0 || idArea > 2 || idArea == null) {
			throw new InvalidParameterException("Valore di idArea non valido.");
		}
		
		if (idModalitaAgevolazione == 0 || idModalitaAgevolazione == null) {
			throw new InvalidParameterException("Valore di idModalitaAgevolazione non valido.");
		}
		
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT pracg.ID_MODALITA_AGEVOLAZIONE, pracg.ID_ENTITA, pce.NOME_ENTITA, pracg.FLAG_AREA_CREDITI, pracg.FLAG_GARANZIE\r\n"
					+ "FROM PBANDI_R_AGEV_CREDITI_GARANZIE pracg\r\n"
					+ "INNER JOIN PBANDI_C_ENTITA pce ON pracg.ID_ENTITA = pce.ID_ENTITA\r\n"
					+ "WHERE pracg.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione);

			
			boxs = getJdbcTemplate().query(query.toString(), new ResultSetExtractor<ArrayList<BoxListVO>>() {
				@Override
				public ArrayList<BoxListVO> extractData(ResultSet rs) throws SQLException {
					ArrayList<BoxListVO>elencoDati = new ArrayList<>();

					while (rs.next()) {
						BoxListVO item = new BoxListVO();
						
						item.setIdEntita(rs.getLong("ID_ENTITA"));
						item.setNomeEntita(rs.getString("NOME_ENTITA"));
						
						if(idArea == 2) {
							item.setFlag(rs.getString("FLAG_GARANZIE"));
							item.setVisible((rs.getString("FLAG_GARANZIE") != null ? true : false));
						} else {
							item.setFlag(rs.getString("FLAG_AREA_CREDITI"));
							item.setVisible((rs.getString("FLAG_AREA_CREDITI") != null ? true : false));
						}
						
						elencoDati.add(item);
					}
					return elencoDati;
				}
			});
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BoxListVO ", e);
			throw new ErroreGestitoException("Exception in " + prf, e);
		}
		finally {
			LOG.info(prf + " END");
		}
			
		return boxs;	
	}*/
	
	@Override
	public List<SchedaClienteMainVO> getSchedaCliente(Long idProgetto, Long idModalitaAgevolazione) throws Exception {
	String prf = "[RicercaBeneficiariCreditiDAOImpl::getSchedaCliente]";
	LOG.info(prf + " BEGIN");
	
	LOG.info(prf + " idProgetto: " + idProgetto);
	LOG.info(prf + " idModalitaAgevolazione: " + idModalitaAgevolazione);
		
	List<SchedaClienteMainVO> schedaCliente = null;
	
	List<SchedaClienteSectionDettaglioErogatoVO> dettaglioErogato = null;
	
	List<String> statiAzienda = null;
	
	List<String> statiCredito = null;
	
	//List<String> providers = null;
	List<SuggestRatingVO> ratings = null;
		
	List<String> motivazioni = null;
	
	List<SchedaClienteStoricoAllVO> storicoStatoAzienda = null;
	
	List<SchedaClienteStoricoAllVO> storicoStatoCredito = null;

	List<SchedaClienteStoricoAllVO> storicoRating = null;

	List<SchedaClienteStoricoAllVO> storicoBanca = null;

	
	try {
		StringBuilder mainQuery = new StringBuilder();
				
		StringBuilder queryDettaglioErogato = new StringBuilder();
		
		StringBuilder queryStatiAzienda = new StringBuilder();

		StringBuilder queryStatiCredito = new StringBuilder();
		//StringBuilder queryProviders = new StringBuilder();
		StringBuilder queryRatings = new StringBuilder();
		
		StringBuilder queryMotivazioni = new StringBuilder();
		
		StringBuilder queryStoricoAzienda = new StringBuilder();
		
		StringBuilder queryStoricoCredito = new StringBuilder();
		
		StringBuilder queryStoricoRating = new StringBuilder();
		
		StringBuilder queryStoricoBanca = new StringBuilder();


		
		mainQuery.append("SELECT DISTINCT \r\n"
				+ "	sopro.ID_SOGGETTO,\r\n"
				+ "	sopro.ID_PROGETTO,\r\n"
				+ "	sopro.PROGR_SOGGETTO_PROGETTO,\r\n"
				+ "	sostaaz.ID_SOGGETTO_STATO_AZIENDA AS staaz_currentid,\r\n"
				+ "	sostaaz.ID_STATO_AZIENDA AS staaz_idstatoazienda,\r\n"
				+ "	sostaaz.DT_INIZIO_VALIDITA AS staaz_dtiniziovalidita,\r\n"
				+ "	sostaaz.DT_FINE_VALIDITA AS staaz_dtfinevalidita,\r\n"
				+ "	soprostacre.ID_SOGG_PROG_STATO_CREDITO_FP AS stacre_currentid,\r\n"
				+ "	soprostacre.ID_STATO_CREDITO_FP AS stacre_idstatocredito,\r\n"
				+ "	sorat.ID_SOGGETTO_RATING AS sorat_currentid,\r\n"
				+ "	soprobaben.ID_SOGG_PROG_BANCA_BEN AS banben_currentid,\r\n"
				+ "	soprobaben.ID_BANCA, \r\n"
				+ "	ban.TITOLO_BANDO AS bando,\r\n"
				+ "	prog.CODICE_VISUALIZZATO AS progetto,\r\n"
				+ "	engiu.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
				+ "	perfis.COGNOME || ' ' || perfis.NOME AS denominazione_persona_fisica,\r\n"
				+ "	sogg.CODICE_FISCALE_SOGGETTO,\r\n"
				+ "	sede.PARTITA_IVA,\r\n"
				+ "	forgiu.DESC_FORMA_GIURIDICA,\r\n"
				+ "	tisog.DESC_TIPO_SOGGETTO,\r\n"
				+ "	staaz.DESC_STATO_AZIENDA,\r\n"
				+ "	stacre.DESC_STATO_CREDITO_FP ,\r\n"
				+ "	rati.CODICE_RATING || ' - ' || rati.DESC_RATING AS rating,\r\n"
				+ "	prov.DESC_PROVIDER AS provider,\r\n"
				+ "	sorat.DT_CLASSIFICAZIONE AS data_classificazione_rating,\r\n"
				+ "	claris.ID_CLASSE_RISCHIO || ' - ' || claris.DESC_CLASSE_RISCHIO AS classe_rischio,\r\n"
				+ "	soproclaris.DT_INIZIO_VALIDITA AS data_classificazione_rischio,\r\n"
				+ "	banca.DESC_BANCA, \r\n"
				+ "	CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.DESC_BANCA ELSE pdb3.DESC_BANCA  END AS desc_banca2,\r\n"
				+ "	CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.ID_BANCA  ELSE pdb3.ID_BANCA  END AS id_banca2,\r\n"
				+ " 	pdb2.DESC_BANCA as deliberato_banca,\r\n"
				+ "	prog.CONFIDI,\r\n"
				+ "	prcema.GARANZIE\r\n"
				+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
				+ "	-- BANDO E PROGETTO --\r\n"
				+ "LEFT JOIN PBANDI_T_PROGETTO prog ON sopro.ID_PROGETTO = prog.ID_PROGETTO\r\n"
				+ "LEFT JOIN PBANDI_T_DOMANDA dom ON prog.ID_DOMANDA = dom.ID_DOMANDA\r\n"
				+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT linint ON dom.PROGR_BANDO_LINEA_INTERVENTO = linint.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "LEFT JOIN PBANDI_T_BANDO ban ON linint.ID_BANDO = ban.ID_BANDO\r\n"
				+ "	-- BENEFICIARIO - DENOMINAZIONE --\r\n"
				+ "LEFT JOIN PBANDI_T_ENTE_GIURIDICO engiu ON sopro.ID_ENTE_GIURIDICO = engiu.ID_ENTE_GIURIDICO\r\n"
				+ "	-- BENEFICIARIO - COGNOME E NOME --\r\n"
				+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON sopro.ID_PERSONA_FISICA = perfis.ID_PERSONA_FISICA\r\n"
				+ "	-- CODICE FISCALE --\r\n"
				+ "LEFT JOIN PBANDI_T_SOGGETTO sogg ON sopro.ID_SOGGETTO = sogg.ID_SOGGETTO \r\n"
				+ "	-- PARTITA IVA --\r\n"
				+ "LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE soprose ON sopro.PROGR_SOGGETTO_PROGETTO = soprose.PROGR_SOGGETTO_PROGETTO AND soprose.ID_TIPO_SEDE = 1\r\n"
				+ "LEFT JOIN PBANDI_T_SEDE sede ON soprose.ID_SEDE = sede.ID_SEDE\r\n"
				+ "	-- FORMA GIURIDICA --\r\n"
				+ "LEFT JOIN PBANDI_D_FORMA_GIURIDICA forgiu ON engiu.ID_FORMA_GIURIDICA = forgiu.ID_FORMA_GIURIDICA\r\n"
				+ "	-- TIPO ANAGRAFICA --\r\n"
				+ "LEFT JOIN PBANDI_D_TIPO_SOGGETTO tisog ON sogg.ID_TIPO_SOGGETTO = tisog.ID_TIPO_SOGGETTO\r\n"
				+ "	-- STATO AZIENDA --\r\n"
				+ "LEFT JOIN PBANDI_T_SOGG_STATO_AZIENDA sostaaz ON sogg.ID_SOGGETTO = sostaaz.ID_SOGGETTO AND sostaaz.DT_FINE_VALIDITA IS NULL  \r\n"
				+ "LEFT JOIN PBANDI_D_STATO_AZIENDA staaz ON sostaaz.ID_STATO_AZIENDA = staaz.ID_STATO_AZIENDA\r\n"
				+ "	-- STATO CREDITO FINPIEMONTE --\r\n"
				+ "LEFT JOIN PBANDI_R_SOGG_PROG_STA_CRED_FP soprostacre ON sopro.PROGR_SOGGETTO_PROGETTO = soprostacre.PROGR_SOGGETTO_PROGETTO AND soprostacre.DT_FINE_VALIDITA IS NULL\r\n"
				+ "LEFT JOIN PBANDI_D_STATO_CREDITO_FP stacre ON soprostacre.ID_STATO_CREDITO_FP = stacre.ID_STATO_CREDITO_FP\r\n"
				+ "	-- RATING E DATA CLASSIFICAZIONE RATING--\r\n"
				+ "LEFT JOIN PBANDI_T_SOGGETTO_RATING sorat ON sogg.ID_SOGGETTO = sorat.ID_SOGGETTO AND sorat.DT_FINE_VALIDITA IS NULL AND sorat.DT_AGGIORNAMENTO IS NULL\r\n"
				+ "LEFT JOIN PBANDI_D_RATING rati ON sorat.ID_RATING = rati.ID_RATING \r\n"
				+ "	-- PROVIDER --\r\n"
				+ "LEFT JOIN PBANDI_D_PROVIDER prov ON rati.ID_PROVIDER = prov.ID_PROVIDER\r\n"
				+ "	-- CLASSE DI RISCHIO E DATA CLASSIFICAZIONE RISCHIO --\r\n"
				+ "LEFT JOIN PBANDI_T_SOGGETTO_CLA_RISCHIO soproclaris ON sopro.ID_SOGGETTO = soproclaris.ID_SOGGETTO AND soproclaris.DT_FINE_VALIDITA IS NULL AND soproclaris.DT_AGGIORNAMENTO IS NULL\r\n"
				+ "LEFT JOIN PBANDI_D_CLASSE_RISCHIO claris ON soproclaris.ID_CLASSE_RISCHIO = claris.ID_CLASSE_RISCHIO \r\n"
				+ "	-- BANCA BENEFICIARIO --\r\n"
				+ "LEFT JOIN PBANDI_R_SOGG_PROG_BANCA_BEN soprobaben ON sopro.PROGR_SOGGETTO_PROGETTO = soprobaben.PROGR_SOGGETTO_PROGETTO AND soprobaben.DT_FINE_VALIDITA IS NULL\r\n"
				+ "LEFT JOIN PBANDI_D_BANCA banca ON soprobaben.ID_BANCA = banca.ID_BANCA\r\n"
				+ "LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pteb.ID_ESTREMI_BANCARI  = sopro.ID_ESTREMI_BANCARI \r\n"
				+ "LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA \r\n"
				+ "LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA \r\n"
				+ "LEFT JOIN PBANDI_D_BANCA pdb3 ON pteb.ID_BANCA = pdb3.ID_BANCA\r\n"
				+ "	-- ALTRE GARANZIE --\r\n"
				+ "LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON prog.ID_DOMANDA = ptce.ID_DOMANDA AND ptce.DT_FINE_VALIDITA IS NULL\r\n"
				+ "LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO AND soprostacre.ID_MODALITA_AGEVOLAZIONE = prcema.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "WHERE sopro.ID_TIPO_ANAGRAFICA = '1' AND sopro.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "	AND sopro.ID_PROGETTO = " + idProgetto + " AND soprostacre.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
				+ "ORDER BY staaz_currentid DESC, stacre_currentid DESC, sorat_currentid DESC, banben_currentid DESC");
		schedaCliente = getJdbcTemplate().query(mainQuery.toString(), new SchedaClienteMainVORowMapper());
	
		

		if (schedaCliente.size() > 0) {
			// TODO: modificare
			queryDettaglioErogato.append("SELECT DISTINCT prog.ID_DOMANDA,\r\n"
					+ "	sopro.ID_SOGGETTO,\r\n"
					+ "	sopro.ID_PROGETTO,\r\n"
					+ "	modag.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	modag.ID_MODALITA_AGEVOLAZIONE_RIF,\r\n"
					+ "	modag.DESC_BREVE_MODALITA_AGEVOLAZ,\r\n"
					+ "	modag.DESC_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	( SELECT count(pte.ID_MODALITA_AGEVOLAZIONE) tot\r\n"
					+ "        FROM PBANDI_T_EROGAZIONE pte\r\n"
					+ "        WHERE pte.ID_PROGETTO = sopro.ID_PROGETTO AND pte.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "        GROUP BY pte.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "    ) AS num_erogazioni, -- Usato nella logica per valorizzare label e causale    \r\n"
					+ "	cauero.ID_CAUSALE_EROGAZIONE,\r\n"
					+ "	cauero.DESC_BREVE_CAUSALE,\r\n"
					+ "	cauero.DESC_CAUSALE,\r\n"
					+ "	ero.DT_CONTABILE AS data_erogazione,\r\n"
					+ "	ero.IMPORTO_EROGAZIONE 	AS Totale_Finpiemonte,\r\n"
					+ "	( SELECT SUM(IMPORTO_FINANZIAMENTO_BANCA)\r\n"
					+ "		FROM PBANDI_R_SOGGETTO_PROGETTO a, PBANDI_R_SOGGETTO_DOMANDA b, PBANDI_T_CONTO_ECONOMICO c\r\n"
					+ "		WHERE a.ID_PROGETTO = sopro.ID_PROGETTO AND a.id_tipo_anagrafica = 1\r\n"
					+ "			AND a.id_tipo_beneficiario <> 4 AND a.PROGR_SOGGETTO_DOMANDA = b.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "			AND b.ID_DOMANDA = c.ID_DOMANDA AND c.DT_FINE_VALIDITA IS NULL\r\n"
					+ "	) AS Totale_Banca\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
					+ "	-- TIPO AGEVOLAZIONE --\r\n"
					+ "	LEFT JOIN PBANDI_T_PROGETTO prog ON sopro.ID_PROGETTO = prog.ID_PROGETTO\r\n"
					+ "	LEFT JOIN PBANDI_T_CONTO_ECONOMICO coneco ON prog.ID_DOMANDA = coneco.ID_DOMANDA AND coneco.DT_FINE_VALIDITA IS NULL\r\n"
					+ "	LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV conecomodag ON coneco.ID_CONTO_ECONOMICO = conecomodag.ID_CONTO_ECONOMICO\r\n"
					+ "	LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modag ON conecomodag.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "	-- DATA EROGAZIONE --\r\n"
					+ "	LEFT JOIN PBANDI_T_EROGAZIONE ero ON sopro.ID_PROGETTO = ero.ID_PROGETTO AND ero.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "	-- CUSALE EROGAZIONE --\r\n"
					+ "	LEFT JOIN PBANDI_D_CAUSALE_EROGAZIONE cauero ON ero.ID_CAUSALE_EROGAZIONE = cauero.ID_CAUSALE_EROGAZIONE \r\n"
					+ "WHERE sopro.ID_TIPO_ANAGRAFICA = 1 AND sopro.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	AND sopro.ID_PROGETTO = " + idProgetto + " AND modag.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "ORDER BY ero.DT_CONTABILE ASC\r\n");
			dettaglioErogato = getJdbcTemplate().query(queryDettaglioErogato.toString(), new RowMapper<SchedaClienteSectionDettaglioErogatoVO>() {
				
				@Override
				public SchedaClienteSectionDettaglioErogatoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					SchedaClienteSectionDettaglioErogatoVO bc = new SchedaClienteSectionDettaglioErogatoVO();
					
					bc.setIdDomanda(rs.getBigDecimal("ID_DOMANDA"));
					bc.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
					bc.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
					
					bc.setIdAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
					bc.setIdAgevolazioneRif(rs.getLong("ID_MODALITA_AGEVOLAZIONE_RIF"));
					bc.setDescBreveTipoAgevolazione(rs.getString("DESC_BREVE_MODALITA_AGEVOLAZ"));
					bc.setDescTipoAgevolazione(rs.getString("DESC_MODALITA_AGEVOLAZIONE"));
					
					// Per visualizzare solo Contributo, Finanziamento o Garanzia
					switch (bc.getIdAgevolazioneRif().intValue()) {
					case 1:
						bc.setDispTipoAgevolazione("Contributo");
						break;
					case 5:
						bc.setDispTipoAgevolazione("Finanziamento");
						break;
					case 10:
						bc.setDispTipoAgevolazione("Garanzia");
						break;
					default:
						bc.setDispTipoAgevolazione(bc.getIdAgevolazioneRif().toString());
						break;
					}
					
					// Valorizzo la label in base all'agevolazione
					/* Escussione se garanzia, Causale altrimenti */
					if(bc.getIdAgevolazioneRif().equals(10L)) { // Garanzia
						bc.setDispLabelCausale("Escussione");
					} else {
						bc.setDispLabelCausale("Causale");
					}
					
					// Valorizzo il valore di dettaglio della label
					/* Per finanziamento e contributo se esiste un solo record nella pbandi_t_erogazione e la causale è saldo, viene valorizzato come erogato unica soluzione, altrimenti si riporta la causale della pbandi_t_erogazione 
					 * Per garanzia se esiste un solo record nella pbandi_t_erogazione e la causale è saldo, viene valorizzato come escusso totale , altrimenti si riporta la causale della pbandi_t_erogazione */
					Long tempNumErogazioni = rs.getLong("NUM_EROGAZIONI");
					Long tempIdCausale = rs.getLong("ID_CAUSALE_EROGAZIONE");
					String tempDescCausale = rs.getString("DESC_CAUSALE");
					/*LOG.info(prf + " tempNumErogazioni: " + tempNumErogazioni);
					LOG.info(prf + " tempIdCausale: " + tempIdCausale);
					LOG.info(prf + " tempDescCausale: " + tempDescCausale);
					LOG.info(prf + " bc.getIdAgevolazioneRif().equals(10L): " + bc.getIdAgevolazioneRif().equals(10L));
					LOG.info(prf + " tempNumErogazioni.equals(1L): " + tempNumErogazioni.equals(1L));
					LOG.info(prf + " tempIdCausale.equals(4L): " + tempIdCausale.equals(4L));*/
					if(bc.getIdAgevolazioneRif().equals(10L)) { // Garanzia
						if(tempNumErogazioni.equals(1L) && tempIdCausale.equals(4L)) { // Se una sola erogazione & id causale == saldo
							bc.setDescCausale("Escusso totale");
						} else {
							bc.setDescCausale(tempDescCausale);
						}
					} else {
						if(tempNumErogazioni.equals(1L) && tempIdCausale.equals(4L)) { // Se una sola erogazione & id causale == saldo
							bc.setDescCausale("Erogato unica soluzione");
						} else {
							bc.setDescCausale(tempDescCausale);
						}
					}
					bc.setNumErogazioni(tempNumErogazioni);
					bc.setIdCausale(tempIdCausale);
					
					bc.setDescBreveCausale(rs.getString("DESC_BREVE_CAUSALE"));
					bc.setDataErogazione(rs.getDate("DATA_EROGAZIONE"));
					bc.setTotFin(rs.getBigDecimal("TOTALE_FINPIEMONTE"));
					bc.setTotBan(rs.getBigDecimal("TOTALE_BANCA"));

					return bc;
				}
			});
			schedaCliente.get(0).setDettaglioErogato(dettaglioErogato);
			
			
			// STATI AZIENDA
			queryStatiAzienda.append("SELECT pdsa.DESC_STATO_AZIENDA \r\n"
					+ "FROM PBANDI_D_STATO_AZIENDA pdsa\r\n"
					+ "ORDER BY pdsa.ID_STATO_AZIENDA ASC");
			statiAzienda = getJdbcTemplate().queryForList(queryStatiAzienda.toString(), String.class);
			schedaCliente.get(0).setStAz_statiAzienda(statiAzienda);

			// STATI CREDITO
			queryStatiCredito.append("SELECT stacre.DESC_STATO_CREDITO_FP\r\n"
					+ "FROM PBANDI_D_STATO_CREDITO_FP stacre\r\n"
					+ "WHERE ID_STATO_CREDITO_FP <> 6\r\n"	// Escludiamo Warning dagli stati selezionabili
					+ "ORDER BY stacre.ID_STATO_CREDITO_FP ASC");
			statiCredito = getJdbcTemplate().queryForList(queryStatiCredito.toString(), String.class);
			schedaCliente.get(0).setStaCre_statiCredito(statiCredito);
			
			// PROVIDERS
			// "Alla partenza del nuovo Gestionale Finanziamenti sar� inserito UN SOLO valore, "Innolva", che � l'attuale provider a cui si appoggia Finpiemonte"
			/*queryProviders.append("SELECT prov.DESC_PROVIDER \r\n"
					+ "FROM PBANDI_D_PROVIDER prov \r\n"
					+ "ORDER BY prov.ID_PROVIDER ASC");		
			providers = getJdbcTemplate().queryForList(queryProviders.toString(), String.class);
			schedaCliente.get(0).setRating_providers(providers);*/

			// RATINGS
			queryRatings.append("SELECT *\r\n"
					+ "FROM PBANDI_D_RATING rati\r\n"
					+ "INNER JOIN PBANDI_D_PROVIDER pdp ON rati.ID_PROVIDER = pdp.ID_PROVIDER\r\n"
					+ "ORDER BY rati.ID_PROVIDER ASC, rati.CODICE_RATING ASC, rati.ID_RATING ASC");
			//ratings = getJdbcTemplate().queryForList(queryRatings.toString(), String.class);
			RowMapper<SuggestRatingVO> rw = new RowMapper<SuggestRatingVO>() {
				@Override
				public SuggestRatingVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					SuggestRatingVO rat = new SuggestRatingVO();
					rat.setIdRating(rs.getLong("ID_RATING"));
					rat.setIdProvider(rs.getLong("ID_PROVIDER"));
					rat.setCodiceRating(rs.getString("CODICE_RATING"));
					rat.setDescRating(rs.getString("DESC_RATING"));
					rat.setDescProvider(rs.getString("DESC_PROVIDER"));
					return rat;
				}};
			ratings = getJdbcTemplate().query(queryRatings.toString(), rw);
			schedaCliente.get(0).setRating_ratings(ratings);
			
			// MOTIVAZIONI
			queryMotivazioni.append("SELECT amoncred.DESC_ATT_MONIT_CRED\r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED amoncred\r\n"
					+ "WHERE amoncred.ID_TIPO_MONIT_CRED = 9\r\n"
					+ "ORDER BY amoncred.ID_ATTIVITA_MONIT_CRED ASC");
			motivazioni = getJdbcTemplate().queryForList(queryMotivazioni.toString(), String.class);
			schedaCliente.get(0).setBanBen_motivazioni(motivazioni);

			BigDecimal idSoggetto = schedaCliente.get(0).getIdSoggetto();

			// STORICO STATO AZIENDA
			queryStoricoAzienda.append("SELECT\r\n"
					+ "	staaz.DESC_STATO_AZIENDA,\r\n"
					+ "	sostaaz.DT_INIZIO_VALIDITA,\r\n"
					+ "	sostaaz.DT_FINE_VALIDITA,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS cognome_nome\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_T_SOGGETTO sogg\r\n"
					+ "	-- DESCRIZIONE STATO AZIENDA, DATA INIZIO E DATA FINE --\r\n"
					+ "INNER JOIN PBANDI_T_SOGG_STATO_AZIENDA sostaaz ON sogg.id_soggetto = sostaaz.ID_SOGGETTO\r\n"
					+ "LEFT JOIN PBANDI_D_STATO_AZIENDA staaz ON sostaaz.ID_STATO_AZIENDA = staaz.ID_STATO_AZIENDA\r\n"
					+ "	-- UTENTE MODIFICA --\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON sostaaz.ID_UTENTE_INS = ute.ID_UTENTE \r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO AND \r\n"
					+ "		(perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO))\r\n"
					+ "WHERE\r\n"
					+ "	--ute.ID_UTENTE = '5655' AND \r\n"
					+ "	sogg.ID_SOGGETTO = " + idSoggetto + "\r\n"
					//+ "	AND perfis.ID_PERSONA_FISICA IN (\r\n"
					//+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					//+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					//+ "		GROUP BY perfis1.ID_SOGGETTO)\r\n"
					+ "	ORDER BY sostaaz.ID_SOGGETTO_STATO_AZIENDA desc");
			storicoStatoAzienda = getJdbcTemplate().query(queryStoricoAzienda.toString(), new SchedaClienteStoricoStatoAziendaVORowMapper());
			schedaCliente.get(0).setStoricoStatoAzienda(storicoStatoAzienda);
			
			// STORICO STATO CREDITO
			queryStoricoCredito.append("SELECT stacrefp.DESC_STATO_CREDITO_FP,\r\n"
					+ "	soprostacre.DT_INIZIO_VALIDITA,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS cognome_nome\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
					+ "	-- STATO CREDITO E DATA MODIFICA --\r\n"
					+ "INNER JOIN PBANDI_R_SOGG_PROG_STA_CRED_FP soprostacre ON sopro.progr_soggetto_progetto = soprostacre.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "LEFT JOIN PBANDI_D_STATO_CREDITO_FP stacrefp ON soprostacre.ID_STATO_CREDITO_FP = stacrefp.ID_STATO_CREDITO_FP\r\n"
					+ "	-- UTENTE MODIFICA --\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON soprostacre.ID_UTENTE_AGG = ute.ID_UTENTE \r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO AND \r\n"
					+ "		(perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO))\r\n"
					+ "WHERE sopro.ID_TIPO_ANAGRAFICA = '1'\r\n"
					+ "	AND sopro.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	AND sopro.ID_PROGETTO = " + idProgetto + "\r\n"
					+ "	ORDER BY soprostacre.DT_INIZIO_VALIDITA DESC, SOPROSTACRE.ID_SOGG_PROG_STATO_CREDITO_FP DESC ");
			storicoStatoCredito = getJdbcTemplate().query(queryStoricoCredito.toString(), new SchedaClienteStoricoStatoCreditoFinVORowMapper());
			schedaCliente.get(0).setStoricoStatoCreditoFin(storicoStatoCredito);
			
			// STORICO RATING
			queryStoricoRating.append("SELECT\r\n"
					+ "	rati.CODICE_RATING || ' - ' || rati.DESC_RATING AS rating,\r\n"
					+ "	prov.DESC_PROVIDER,\r\n"
					+ "	sorat.DT_CLASSIFICAZIONE,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS cognome_nome\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_T_SOGGETTO sogg\r\n"
					+ "	-- RATING E DATA CLASSIFICAZIONE --\r\n"
					+ "INNER JOIN PBANDI_T_SOGGETTO_RATING sorat ON sogg.ID_SOGGETTO = sorat.ID_SOGGETTO --AND sorat.DT_FINE_VALIDITA IS NULL\r\n"
					+ "LEFT JOIN PBANDI_D_RATING rati ON sorat.ID_RATING = rati.ID_RATING\r\n"
					+ "	-- PROVIDER --\r\n"
					+ "LEFT JOIN PBANDI_D_PROVIDER prov ON rati.ID_PROVIDER = prov.ID_PROVIDER\r\n"
					+ "	-- UTENTE MODIFICA --\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON sorat.ID_UTENTE_AGG = ute.ID_UTENTE \r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO AND \r\n"
					+ "		(perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO))\r\n"
					+ "WHERE\r\n"
					+ "	sogg.ID_SOGGETTO = " + idSoggetto + "\r\n"
					//+ "	AND perfis.ID_PERSONA_FISICA IN (\r\n"
					//+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					//+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					//+ "		GROUP BY perfis1.ID_SOGGETTO)\r\n"
					+ "	--AND ute.ID_UTENTE = '2249'\r\n"
					+ "	ORDER BY sorat.DT_CLASSIFICAZIONE DESC ");			
			storicoRating = getJdbcTemplate().query(queryStoricoRating.toString(), new SchedaClienteStoricoRatingVORowMapper());
			schedaCliente.get(0).setStoricoRating(storicoRating);

			// STORICO BANCA
			queryStoricoBanca.append("SELECT\r\n"
					+ "	ban.DESC_BANCA,\r\n"
					+ "	soprobaben.DT_INSERIMENTO,\r\n"
					+ "	attmoncred.DESC_ATT_MONIT_CRED AS motivazione,\r\n"
					+ "	soprobaben.DENOM_SOGGETTO_TERZO,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS cognome_nome\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
					+ "	-- DATA MODIFICA E SOGGETTO TERZO --\r\n"
					+ "INNER JOIN PBANDI_R_SOGG_PROG_BANCA_BEN soprobaben ON sopro.PROGR_SOGGETTO_PROGETTO = soprobaben.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "	-- BANCA --\r\n"
					+ "LEFT JOIN PBANDI_D_BANCA ban ON soprobaben.ID_BANCA = ban.ID_BANCA\r\n"
					+ "	-- MOTIVAZIONE --\r\n"
					+ "LEFT JOIN PBANDI_D_ATTIVITA_MONIT_CRED attmoncred ON soprobaben.ID_ATTIVITA_BANCA_BEN = attmoncred.ID_ATTIVITA_MONIT_CRED\r\n"
					+ "	-- UTENTE MODIFICA --\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON soprobaben.ID_UTENTE_INS = ute.ID_UTENTE \r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO AND \r\n"
					+ "		(perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO))\r\n"
					+ "WHERE\r\n"
					+ "	sopro.ID_TIPO_ANAGRAFICA = '1'\r\n"
					+ "	AND sopro.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	AND sopro.ID_SOGGETTO = " + idSoggetto + "\r\n"
					//+ "	AND perfis.ID_PERSONA_FISICA IN (\r\n"
					//+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					//+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					//+ "		GROUP BY perfis1.ID_SOGGETTO)\r\n"
					+ "	--AND ute.ID_UTENTE = '27345'\r\n"
					+ "	ORDER BY soprobaben.DT_INSERIMENTO DESC");
			storicoBanca = getJdbcTemplate().query(queryStoricoBanca.toString(), new SchedaClienteStoricoBancaBeneficiarioVORowMapper());
			schedaCliente.get(0).setStoricoBancaBeneficiario(storicoBanca);
			
		}

	}

	catch (RecordNotFoundException e)
	{
		throw e;
	}

	catch (Exception e)
	{
		LOG.error(prf + "Exception while trying to read SchedaClienteMainVO", e);
		throw new ErroreGestitoException("DaoException while trying to read SchedaClienteMainVO", e);
	}
	finally
	{
		LOG.info(prf + " END");
	}

	return schedaCliente;
	}


	
	@Override
	public List<String> getListBanche(String value) throws Exception {

		String prf = "[RicercaBeneficiariCreditiDAOImpl::getListBanche]";
		LOG.info(prf + " BEGIN");
			
		List<String> banche = null;
		
		try {
			StringBuilder queryBanche = new StringBuilder();
				
			queryBanche.append("SELECT ban.DESC_BANCA\r\n"
					+ "FROM PBANDI_D_BANCA ban\r\n"
					+ "WHERE UPPER(ban.DESC_BANCA) LIKE UPPER('%" + value +"%')\r\n"
					+ "AND ROWNUM <= 100\r\n"
					+ "ORDER BY ban.DESC_BANCA ASC");
			banche = getJdbcTemplate().queryForList(queryBanche.toString(), String.class);
		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read BeneficiarioCreditiVO - ListBanche", e);
			throw new ErroreGestitoException("DaoException while trying to read BeneficiarioCreditiVO - ListBanche", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return banche;
	}
	
	

	@Override
	public void setStatoAzienda(SaveSchedaClienteAllVO statoAzienda, Long idModalitaAgevolazione, Boolean flagStatoAzienda, Long idProgetto) throws ErroreGestitoException {

		String prf = "[RicercaBeneficiarioDAOImpl::setSchedaCliente]";
		LOG.info(prf + " BEGIN");

		BigDecimal idCauBlo  = null; 

		try {
			
			String sqlRecordIsPresent = "SELECT COUNT(*) AS num\r\n"
					+ "FROM PBANDI_T_SOGG_STATO_AZIENDA sostaaz\r\n"
					+ "WHERE sostaaz.DT_FINE_VALIDITA IS NULL AND sostaaz.ID_SOGGETTO = " + statoAzienda.getIdSoggetto();
			int nRecordPresent = getJdbcTemplate().queryForObject(sqlRecordIsPresent, Integer.class);
			
			String sqlIdStatoAz = "SELECT staaz.ID_STATO_AZIENDA \r\n"
					+ "FROM PBANDI_D_STATO_AZIENDA staaz\r\n"
					+ "WHERE staaz.DESC_STATO_AZIENDA = '" + statoAzienda.getStaAz_statoAzienda() + "'";
			int idStatoAzienda = getJdbcTemplate().queryForObject(sqlIdStatoAz, Integer.class);
			
			
			// Se lo stato azienda passa da ATTIVA ad altro, inserisco una proposta di revoca e creo un blocco.
			Map<String, Object> mapFlag = new HashMap<>();
			if(flagStatoAzienda) {

				String sqlFlagBlocco = "SELECT ID_CAUSALE_BLOCCO, FLAG_REVOCA, FLAG_BLOCCO_ANAGRAFICO \r\n"
						+ "FROM PBANDI_D_CAUSALE_BLOCCO pdcb\r\n"
						+ "WHERE DESC_MACROAREA = 'Stato azienda' AND ID_STATO_AZIENDA = " + idStatoAzienda;
				mapFlag = getJdbcTemplate().queryForMap(sqlFlagBlocco);

				LOG.info(prf + " ID_CAUSALE_BLOCCO: " + mapFlag.get("ID_CAUSALE_BLOCCO"));
				LOG.info(prf + " FLAG_REVOCA: " + mapFlag.get("FLAG_REVOCA"));
				LOG.info(prf + " FLAG_BLOCCO_ANAGRAFICO: " + mapFlag.get("FLAG_BLOCCO_ANAGRAFICO"));


				BloccoVO bloccoObj = new BloccoVO();
				bloccoObj.setIdSoggetto(new BigDecimal(statoAzienda.getIdSoggetto()));
				idCauBlo = (BigDecimal) mapFlag.get("ID_CAUSALE_BLOCCO");
				bloccoObj.setIdCausaleBlocco(idCauBlo.longValue());
				bloccoObj.setIdUtente(new BigDecimal(statoAzienda.getIdUtente()));

				if("S".equals(mapFlag.get("FLAG_BLOCCO_ANAGRAFICO"))) {
					// Blocco soggetto

					Boolean resBloccoSogg = anagraficaBen.insertBlocco(bloccoObj);
					LOG.info(prf + " resBloccoSogg: " + resBloccoSogg);

				} else {
					// Blocco domanda

					String sqlNumeroDomanda = "SELECT ptd.NUMERO_DOMANDA \r\n"
							+ "   FROM pbandi_t_progetto ptp\r\n"
							+ "   INNER JOIN pbandi_t_domanda ptd ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
							+ "   WHERE ptp.ID_PROGETTO = " + idProgetto;
					String numeroDomanda = getJdbcTemplate().queryForObject(sqlNumeroDomanda, String.class);

					Boolean resBloccoDom = anagraficaBen.insertBloccoDomanda(bloccoObj, bloccoObj.getIdSoggetto().toString(), numeroDomanda);
					LOG.info(prf + " resBloccoDom: " + resBloccoDom);
				}

			
				
				
			} // TODO 
				else if(idStatoAzienda==1) { // se lo stato azienda ==1 attiva allora controllo se ci sono blocchi stato azienda e li tolgo
								
						checkInserisciSblocco(statoAzienda.getIdSoggetto(), statoAzienda.getIdUtente(),2);
					
			}
			
			// inserimento proposta variazione stato credito solo se lo stato che voglio inserire è diverso da attivo==1
			if(idStatoAzienda!=1) {


				
//				recupero i progetti
				
				String getProgettiBenef = "SELECT\r\n"
						+ "    DISTINCT sopro.ID_PROGETTO,\r\n"
						+ "    sopro.ID_SOGGETTO,\r\n"
						+ "    ptb.ID_BANDO,\r\n"
						+ "    sopro.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    pro.CODICE_VISUALIZZATO,\r\n"
						+ "    modag.ID_MODALITA_AGEVOLAZIONE AS ID_MODALITA_AGEVOLAZIONE_ORIG,\r\n"
						+ "    modag.ID_MODALITA_AGEVOLAZIONE_RIF,\r\n"
						+ "    pdscf.ID_STATO_CREDITO_FP , \r\n"
						+ "    pdscf.DESC_BREVE_STATO_CREDITO_FP \r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO sopro\r\n"
						+ "    LEFT JOIN PBANDI_T_PROGETTO pro ON sopro.ID_PROGETTO = pro.ID_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO coneco ON pro.ID_DOMANDA = coneco.ID_DOMANDA\r\n"
						+ "    AND coneco.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV conecomodag ON coneco.ID_CONTO_ECONOMICO = conecomodag.ID_CONTO_ECONOMICO\r\n"
						+ "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modag ON conecomodag.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modag2 ON modag2.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE_RIF\r\n"
						+ "    LEFT JOIN PBANDI_T_EROGAZIONE ero ON sopro.ID_PROGETTO = ero.ID_PROGETTO\r\n"
						+ "    AND conecomodag.ID_MODALITA_AGEVOLAZIONE = ero.ID_MODALITA_AGEVOLAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_BANCA_BEN soprobaben ON sopro.PROGR_SOGGETTO_PROGETTO = soprobaben.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    AND soprobaben.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    LEFT JOIN pbandi_t_domanda ptd ON ptd.ID_DOMANDA = pro.ID_DOMANDA\r\n"
						+ "    LEFT JOIN pbandi_r_bando_linea_intervent prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
						+ "    LEFT JOIN pbandi_t_bando ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf ON sopro.PROGR_SOGGETTO_PROGETTO = prspscf.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    AND prspscf.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prspscf.ID_MODALITA_AGEVOLAZIONE = modag.ID_MODALITA_AGEVOLAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_CREDITO_FP pdscf ON pdscf.ID_STATO_CREDITO_FP = prspscf.ID_STATO_CREDITO_FP\r\n"
						+ "WHERE\r\n"
						+ "    sopro.ID_TIPO_ANAGRAFICA = '1'\r\n"
						+ "    AND sopro.ID_TIPO_BENEFICIARIO <> '4'\r\n"
						+ "    AND sopro.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND sopro.id_soggetto ="+statoAzienda.getIdSoggetto(); 
			
			 List<DettaglioBeneficiarioCreditiVO> progettiDelBeneficiario =  new ArrayList<DettaglioBeneficiarioCreditiVO>();
			 
			 RowMapper<DettaglioBeneficiarioCreditiVO> rowMapper = new RowMapper<DettaglioBeneficiarioCreditiVO>() {
				
				@Override
				public DettaglioBeneficiarioCreditiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					DettaglioBeneficiarioCreditiVO progetto = new DettaglioBeneficiarioCreditiVO(); 
					progetto.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					progetto.setIdProgetto(rs.getLong("ID_PROGETTO"));
					progetto.setIdModalitaAgevolazioneOrig(rs.getLong("ID_MODALITA_AGEVOLAZIONE_ORIG"));
					progetto.setIdModalitaAgevolazioneRif(rs.getLong("ID_MODALITA_AGEVOLAZIONE_RIF"));
					progetto.setIdBando(rs.getLong("ID_BANDO"));
					progetto.setDescStatoCredito(rs.getString("DESC_BREVE_STATO_CREDITO_FP"));
					progetto.setIdStatoCredito(rs.getInt("ID_STATO_CREDITO_FP"));
					
					return progetto;
				}
			};
			
			
			progettiDelBeneficiario = getJdbcTemplate().query(getProgettiBenef, rowMapper); 
			
	
			
//				DENTRO IL METODO checkStatoAzienda faccio tutti i controlli elencati qui sopra	 		
				 int getStatoCredito =  checkStatoAziendaAndGetStatoCredito(statoAzienda, idStatoAzienda); 
				 
				 
				 
				 for(DettaglioBeneficiarioCreditiVO dettaglio: progettiDelBeneficiario) {
//					  VIENE CHIAMATO AMM.VO CONTABILE  getCreditoResiduoEAgevolazione
					DebitoResiduo deb = new DebitoResiduo();
					try {
						deb = amministrativoContabileServiceImpl.callToDebitoResiduo(dettaglio.getIdProgetto().intValue(), dettaglio.getIdBando().intValue(),
				    dettaglio.getIdModalitaAgevolazioneOrig().intValue(),dettaglio.getIdModalitaAgevolazioneRif().intValue(),Long.parseLong(statoAzienda.getIdUtente()));							
						
					} catch (Exception e) {
						deb = null; 
					}
					// se c'è il debito residuo oppure oneri di agevolazione allora chiamo
					// controllo se lo stato credito è sofferenza 
					if(deb!=null &&
						((deb.getDebitoResiduo()!=null && deb.getDebitoResiduo()>0) || ( deb.getOneriAgevolazione()!=null && deb.getOneriAgevolazione()>0 ))
						&& dettaglio.getIdStatoCredito()!=4) {
						dettaglio.setCreditoResiduo(new BigDecimal(deb.getDebitoResiduo()));
						dettaglio.setAgevolazione(new BigDecimal(deb.getOneriAgevolazione()));
						
						if(dettaglio.getIdStatoCredito()!= getStatoCredito) {									
							boolean result  =	insertPropostaVariazioneStatoCredito(dettaglio.getIdProgetto(),  dettaglio.getIdModalitaAgevolazioneOrig(), 
									statoAzienda, dettaglio.getIdStatoCredito(), getStatoCredito); 
							if( result == true) {
								LOG.info("inserimento proposta variazione stato credito andato a buon fine");
							}
						}
					}
					// se esiste un credito residuo oppure gli oneri di ageevolazione allora devo inserire la revoca!
					if(deb!=null &&	((deb.getDebitoResiduo()!=null && deb.getDebitoResiduo()>0) || ( deb.getOneriAgevolazione()!=null && deb.getOneriAgevolazione()>0 ))){						
						if("S".equals(mapFlag.get("FLAG_REVOCA"))) {
							// deve la proposta di revoca su tutti i progetti del beneficiario che hanno credito residuo
							
							//Long idCauBlo = Long.valueOf( mapFlag.get("ID_CAUSALE_BLOCCO").toString());
							Long newIdUtente = Long.valueOf(statoAzienda.getIdUtente());
							Long resPropostaRevoca = propostaRevoca.creaPropostaRevoca(suggRevoca.newNumeroRevoca(), dettaglio.getIdProgetto(), idCauBlo.longValue(), null, java.sql.Date.valueOf(LocalDate.now()), newIdUtente);
							LOG.info(prf + " resPropostaRevoca: " + resPropostaRevoca);
						}
					}
				}
				 
				 // se lo stato credito è diverso da null e è uguale a UTP = 1
//				if(getStatoCredito==1) {
//				} else if( getStatoCredito == 4){
//				}

			
			}
			
			// Quello che ha sempre fatto
			if (nRecordPresent > 0) {
				
				String sqlDupl = "INSERT INTO PBANDI_T_SOGG_STATO_AZIENDA (\r\n"
						+ "	ID_SOGGETTO_STATO_AZIENDA,\r\n"
						+ "	ID_SOGGETTO,\r\n"
						+ "	ID_STATO_AZIENDA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	DT_FINE_VALIDITA)\r\n"
						+ "SELECT\r\n"
						+ "	SEQ_PBANDI_T_SOGG_STATO_AZI.nextval,\r\n" + "	?,\r\n" + "	?,\r\n" + "	?,\r\n" + "	SYSDATE,\r\n" + "	?,\r\n" + "	?\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_T_SOGG_STATO_AZIENDA\r\n"
						+ "WHERE\r\n"
						+ "	ID_SOGGETTO_STATO_AZIENDA = ?";
				Object[] args2 = new Object[] { statoAzienda.getIdSoggetto(), idStatoAzienda, statoAzienda.getIdUtente(),
						statoAzienda.getStaAz_dataInizio(), statoAzienda.getStaAz_dataFine(), statoAzienda.getStaAz_idCurrentRecord() };
				
				int[] types2 = new int[]{ Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.DATE, Types.DATE, Types.INTEGER };
				
				getJdbcTemplate().update(sqlDupl.toString(), args2, types2);

				
				
				String sqlDtFinePresent = "SELECT COUNT(sostaaz.DT_FINE_VALIDITA) AS num \r\n"
						+ "FROM PBANDI_T_SOGG_STATO_AZIENDA sostaaz\r\n"
						+ "WHERE sostaaz.ID_SOGGETTO_STATO_AZIENDA = " + statoAzienda.getStaAz_idCurrentRecord() + " AND sostaaz.DT_FINE_VALIDITA IS NOT NULL ";
				int dtFinePresent = getJdbcTemplate().queryForObject(sqlDtFinePresent, Integer.class);
				
				
				StringBuilder queryUpdate = new StringBuilder();
								
				queryUpdate.append("UPDATE PBANDI_T_SOGG_STATO_AZIENDA sostaaz\r\n"
						+ "SET\r\n"
						+ "	sostaaz.DT_AGGIORNAMENTO = SYSDATE,\r\n"
						+ "	sostaaz.ID_UTENTE_AGG = ?\r\n");
						
				if (dtFinePresent == 0) {
					queryUpdate.append("	, sostaaz.DT_FINE_VALIDITA = ? \r\n");
				}
						
				queryUpdate.append("WHERE\r\n"
						+ "	sostaaz.ID_SOGGETTO_STATO_AZIENDA = ?");
				
				if (dtFinePresent == 0) {
					
					Object[] args = new Object[] { statoAzienda.getIdUtente(), statoAzienda.getStaAz_dataInizio(), statoAzienda.getStaAz_idCurrentRecord() };
					int[] types = new int[]{ Types.INTEGER, Types.DATE, Types.INTEGER };
					
					getJdbcTemplate().update(queryUpdate.toString(), args, types);
				} else {
					
					Object[] args = new Object[] { statoAzienda.getIdUtente(), statoAzienda.getStaAz_idCurrentRecord() };
					int[] types = new int[] { Types.INTEGER, Types.INTEGER };
					
					getJdbcTemplate().update(queryUpdate.toString(), args, types);
				}
				
				
			} else {
				
				String sqlNew = "INSERT INTO PBANDI_T_SOGG_STATO_AZIENDA sostaaz (\r\n"
						+ "	ID_SOGGETTO_STATO_AZIENDA,\r\n"
						+ "	ID_SOGGETTO,\r\n"
						+ "	ID_STATO_AZIENDA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	DT_FINE_VALIDITA)\r\n"
						+ "VALUES (\r\n"
						+ "	SEQ_PBANDI_T_SOGG_STATO_AZI.nextval, ?, ?, ?, sysdate, ?, ?)";
				
				Object[] args3 = new Object[] { statoAzienda.getIdSoggetto(), idStatoAzienda, statoAzienda.getIdUtente(),
						statoAzienda.getStaAz_dataInizio(), statoAzienda.getStaAz_dataFine() };
				
				int[] types3 = new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.DATE, Types.DATE };
				
				getJdbcTemplate().update(sqlNew.toString(), args3, types3);
			}
			
			
			// se lo stato azienda che voglio inserire è  attiva => 1 
			// controllo se ci sono stati blocchi stato azienda e li toglo tutti 
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception during setStatoAzienda", e);
			throw new ErroreGestitoException("Exception during setStatoAzienda", e);
		} finally {
			LOG.info(prf + " END");
		}

	}





	private int checkStatoAziendaAndGetStatoCredito(SaveSchedaClienteAllVO statoAzienda, int idNuovoStatoAzienda) {
		
		
		// 
		int idStatoCredito=0;
		try {
			
//			Stato Azienda
//			0	Finpiemonte
//			1	Attiva
//			2	Fallimento
//			3	Scioglim Liquidaz
//			4	Concordato
//			5	Amm straordinaria
//			6	Liquidaz coatta
//			7	Cessata
//			8	Cancellata
//			9	Crisi da sovraindeb
//			10	Inattiva
			
//		Stato credito
//			1	In bonis	In bonis
//			2	Past due	Past due
//			3	UTP	Unlikely to pay
//			4	Sofferenze	Sofferenze
//			5	Bonis non attive	Bonis non attive
//			6	Warning	Warning
			
			// recupero lo satto credito attuale dal
			
			int idStatoAttuale =  getJdbcTemplate().queryForObject("SELECT id_stato_Azienda \r\n"
					+ "from  PBANDI_T_SOGG_STATO_AZIENDA sostaaz where sostaaz.ID_SOGGETTO_STATO_AZIENDA="+statoAzienda.getStaAz_idCurrentRecord(), Integer.class);
			
			//  controllo se lo stato precedente è diverso dal nuovo stato
			if (idStatoAttuale !=  idNuovoStatoAzienda) {
				
				//   se lo stato precedente (idstatoAttuale) è attivo e il nuovo stato è : Scioglimento e liquidazione(3) Concordato (4) 
				//	 Amm. Straordinaria (5) Cessata (7) Cancellata (8) Crisi da sovraindebitamento (9) allora lo stato credito sara sicuramente utp con id:  3
				// 	 nel caso contrario ritorno sofferenze con id 4
				if(idStatoAttuale == 1) {
					switch (idNuovoStatoAzienda) {
					case 3:
					case 4:
					case 5:
					case 7:
					case 8:
					case 9:
						idStatoCredito=3;
						break;
					case 2:
					case 6:
					case 10:
						idStatoCredito=4;
						break;
					default:
						break;
					}
				} else {
					switch (idNuovoStatoAzienda) {
					case 6:
					case 10:
					case 2:
						idStatoCredito=4;
						break;
					default:
						break;
					}
				}
			} else {
				return 0; 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		return idStatoCredito;
	}

	private boolean insertPropostaVariazioneStatoCredito(Long idProgetto, Long idModalitaAgevolazione, SaveSchedaClienteAllVO statoAzienda, int idStatoCreditoAttuale, int idNuovoStaoCredito) {

		
		boolean result = true; 
		
		try {
			
			// prima di inserire un record dentro la tabella PBANDI_R_SOGG_PROG_STA_CRED_FP devo controllare se esiste già un record attivo
			// con lo stato credito attuale. 
			
			
			String idProgStaCre =  null; 
			
			String getIdStatoCredito = "   SELECT psc.ID_SOGG_PROG_STATO_CREDITO_FP \r\n"
					+ "    FROM PBANDI_R_SOGG_PROG_STA_CRED_FP psc\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_PROGETTO  = psc.PROGR_SOGGETTO_PROGETTO \r\n"
					+ "    WHERE prsp.ID_PROGETTO = ?\r\n"
					+ "    AND psc.ID_STATO_CREDITO_FP =?\r\n"
					+ "    AND psc.DT_FINE_VALIDITA IS NULL \r\n"
					+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND rownum <2"; 
			
			idProgStaCre =  getJdbcTemplate().queryForObject(getIdStatoCredito, String.class,  new Object[] {
					idProgetto, 
					(idStatoCreditoAttuale>0)?idStatoCreditoAttuale:  1
			});
			if(idProgStaCre==null) {
				
				String sqlIdProgStaCre = "SELECT SEQ_PBANDI_R_SOG_PROG_STA_CRED.nextval FROM dual";
				idProgStaCre = getJdbcTemplate().queryForObject(sqlIdProgStaCre, String.class);
				
				String sqlIns = "INSERT INTO PBANDI_R_SOGG_PROG_STA_CRED_FP ( \r\n"
						+ "   ID_SOGG_PROG_STATO_CREDITO_FP,\r\n"
						+ "   PROGR_SOGGETTO_PROGETTO ,\r\n"
						+ "   ID_STATO_CREDITO_FP , \r\n"
						+ "   ID_MODALITA_AGEVOLAZIONE ,\r\n"
						+ "   ID_UTENTE_INS ,\r\n"
						+ "   DT_INSERIMENTO ,\r\n"
						+ "   DT_INIZIO_VALIDITA \r\n"
						+ "   ) VALUES (?,\r\n"
						+ "   ( SELECT prsp.PROGR_SOGGETTO_PROGETTO \r\n"
						+ "   FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "   WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "   AND prsp.ID_TIPO_ANAGRAFICA  = 1\r\n"
						+ "   AND prsp.DT_FINE_VALIDITA IS NULL \r\n"
						+ "   AND prsp.ID_PROGETTO =?),\r\n"
						+ "   ?,\r\n"
						+ "   ?,\r\n"
						+ "   ?,\r\n"
						+ "   TRUNC(SYSDATE),TRUNC(SYSDATE))";
				Object[] args10 = new Object[] { idProgStaCre, idProgetto,
						(idStatoCreditoAttuale>0)?idStatoCreditoAttuale:  1, 
								idModalitaAgevolazione, statoAzienda.getIdUtente() };
				int[] types10 = new int[]{ Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER };				
				getJdbcTemplate().update(sqlIns.toString(), args10, types10);
			}
			
			
			// inserimento dentro la tabella PBANDI_T_VARIAZ_ST_CREDITO
			
			String sqlInsVarStaCre = "INSERT INTO PBANDI_T_VARIAZ_ST_CREDITO (\r\n"
					+ "   ID_VARIAZ_ST_CREDITO ,\r\n"
					+ "   ID_SOGG_PROG_STATO_CREDITO_FP ,\r\n"
					+ "   ID_NUOVO_STATO_CREDITO_FP ,\r\n"
					+ "   ID_STATO_PROP_VAR_CRE ,\r\n"
					+ "   ID_MODALITA_AGEVOLAZIONE ,\r\n"
					+ "   NUM_RATA ,\r\n"
					+ "   ID_UTENTE_INS ,\r\n"
					+ "   DT_INIZIO_VALIDITA ,\r\n"
					+ "   DT_INSERIMENTO \r\n"
					+ "   ) VALUES (SEQ_PBANDI_T_VARIAZ_ST_CREDITO.nextval,?,?,?,?,?,?,\r\n"
					+ "   TRUNC(SYSDATE),TRUNC(SYSDATE))";
			Object[] args11 = new Object[] { (idProgStaCre),
					idNuovoStaoCredito, 2, idModalitaAgevolazione, 0, statoAzienda.getIdUtente() };

			//int[] types11 = new int[]{ Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER };

			getJdbcTemplate().update(sqlInsVarStaCre.toString(), args11);
			
		} catch (Exception e) {
			result = false; 
		}
		
		return result;
	}

	@Override
	public void setStatoCredito(SaveSchedaClienteAllVO statoCredito, Long idModalitaAgevolazione, Long idProgetto) throws ErroreGestitoException {
		String prf = "[RicercaBeneficiarioDAOImpl::setStatoCredito]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf +"PRINT OBJ FROM BODY: " + statoCredito);

		try {
			// Controllo se i flag revoca e blocco sono settati per il corrispondente stato selezionato
			try {
				Map<String, Object> mapFlag = new HashMap<>();

				String sqlFlagBlocco = "SELECT ID_CAUSALE_BLOCCO, FLAG_REVOCA, FLAG_BLOCCO_ANAGRAFICO\r\n"
						+ "FROM PBANDI_D_CAUSALE_BLOCCO pdcb\r\n"
						+ "WHERE DESC_MACROAREA = 'Stato credito' AND DESC_CAUSALE_BLOCCO = '" + statoCredito.getStaCre_stato() + "'";
				mapFlag = getJdbcTemplate().queryForMap(sqlFlagBlocco);

				LOG.info(prf + " ID_CAUSALE_BLOCCO: " + mapFlag.get("ID_CAUSALE_BLOCCO"));
				LOG.info(prf + " FLAG_REVOCA: " + mapFlag.get("FLAG_REVOCA"));
				LOG.info(prf + " FLAG_BLOCCO_ANAGRAFICO: " + mapFlag.get("FLAG_BLOCCO_ANAGRAFICO"));
				
				// Inserisco i blocchi
				BloccoVO bloccoObj = new BloccoVO();
				bloccoObj.setIdSoggetto(new BigDecimal(statoCredito.getIdSoggetto()));
				BigDecimal idCauBlo = (BigDecimal) mapFlag.get("ID_CAUSALE_BLOCCO");
				bloccoObj.setIdCausaleBlocco(idCauBlo.longValue());
				bloccoObj.setIdUtente(new BigDecimal(statoCredito.getIdUtente()));

				if("S".equals(mapFlag.get("FLAG_BLOCCO_ANAGRAFICO"))) {
					// Blocco soggetto

					Boolean resBloccoSogg = anagraficaBen.insertBlocco(bloccoObj);
					LOG.info(prf + " resBloccoSogg: " + resBloccoSogg);

				} else {
					// Blocco domanda

					String sqlNumeroDomanda = "SELECT ptd.NUMERO_DOMANDA \r\n"
							+ "   FROM pbandi_t_progetto ptp\r\n"
							+ "   INNER JOIN pbandi_t_domanda ptd ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
							+ "   WHERE ptp.ID_PROGETTO = " + idProgetto;
					String numeroDomanda = getJdbcTemplate().queryForObject(sqlNumeroDomanda, String.class);

					Boolean resBloccoDom = anagraficaBen.insertBloccoDomanda(bloccoObj, bloccoObj.getIdSoggetto().toString(), numeroDomanda);
					LOG.info(prf + " resBloccoDom: " + resBloccoDom);
				}

				if("S".equals(mapFlag.get("FLAG_REVOCA"))) {

					Long resPropostaRevoca = propostaRevoca.creaPropostaRevoca(suggRevoca.newNumeroRevoca(), idProgetto, bloccoObj.getIdCausaleBlocco(), null, java.sql.Date.valueOf(LocalDate.now()), Long.valueOf(statoCredito.getIdUtente()));
					LOG.info(prf + " resPropostaRevoca: " + resPropostaRevoca);
				}
				
				
			} catch (EmptyResultDataAccessException e) {
				LOG.info(prf + " Nessun blocco presente per lo stato selezionato.");
			}
			
			//LOG.info(prf + " *test* Roba fuori");
							
			// Quello che ha sempre fatto
			
			String queryIdStaCre = "SELECT fp.ID_STATO_CREDITO_FP AS id\r\n"
					+ "FROM PBANDI_D_STATO_CREDITO_FP fp\r\n"
					+ "WHERE fp.DESC_STATO_CREDITO_FP = '" + statoCredito.getStaCre_stato() + "'";
			int ID_STATO_CREDITO_FP = getJdbcTemplate().queryForObject(queryIdStaCre, Integer.class);	
			
			
			
			String queryIdSoProStaCre = "select SEQ_PBANDI_R_SOG_PROG_STA_CRED.nextval from dual";
			Long ID_SOGG_PROG_STATO_CREDITO_FP = getJdbcTemplate().queryForObject(queryIdSoProStaCre, Long.class);
			
			String sqlAgeRif = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
					"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
					"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
			int idModalitaAgevolazioneRif = getJdbcTemplate().queryForObject(sqlAgeRif, Integer.class, idModalitaAgevolazione);
				
			if (statoCredito.getStaCre_idCurrentRecord() == null) {

				String sqlNew = "INSERT INTO PBANDI_R_SOGG_PROG_STA_CRED_FP stacrefp (\r\n"
						+ "	ID_SOGG_PROG_STATO_CREDITO_FP,\r\n"
						+ "	PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "	ID_STATO_CREDITO_FP,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	DT_INIZIO_VALIDITA,"
						+ " ID_MODALITA_AGEVOLAZIONE )\r\n"
						+ "VALUES (\r\n"
						+ "	" + ID_SOGG_PROG_STATO_CREDITO_FP + ", ?, ?, ?, sysdate, ?, ?)";

				Object[] args1 = new Object[] { statoCredito.getStaCre_PROGR_SOGGETTO_PROGETTO(), ID_STATO_CREDITO_FP, statoCredito.getIdUtente(), statoCredito.getStaCre_dataMod(), idModalitaAgevolazione };

				int[] types1 = new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.DATE , Types.NUMERIC};

				getJdbcTemplate().update(sqlNew, args1, types1);

				// Chiamo amm.vo cont. per comunicargli il cambio di stato 
				LOG.info(prf + " Chiamo amm.vo");

				Boolean ammRes = false;

				ammRes = amministrativoService.setStatoCredito(ID_SOGG_PROG_STATO_CREDITO_FP, ID_STATO_CREDITO_FP, idProgetto.intValue(), Date.valueOf(statoCredito.getStaCre_dataMod()), idModalitaAgevolazione.intValue(), idModalitaAgevolazioneRif, Long.parseLong(statoCredito.getIdUtente()));
				LOG.info(prf + " Amm.vo res: " + ammRes);

			} else {
								
				String queryCopyAndInsert = "INSERT INTO PBANDI_R_SOGG_PROG_STA_CRED_FP (\r\n"
						+ "	ID_SOGG_PROG_STATO_CREDITO_FP,\r\n"
						+ "	PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "	ID_STATO_CREDITO_FP,\r\n"
						+ "	ID_UTENTE_INS,ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	DT_INIZIO_VALIDITA)\r\n"
						+ "SELECT\r\n"
						+ "	?,\r\n"
						+ "	PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "	?,\r\n"
						+ "	ID_UTENTE_INS,?,\r\n"
						+ "	SYSDATE,\r\n"
						+ "	?\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_R_SOGG_PROG_STA_CRED_FP\r\n"
						+ "WHERE\r\n"
						+ "	ID_SOGG_PROG_STATO_CREDITO_FP = " + statoCredito.getStaCre_idCurrentRecord();
				
				Object[] args2 = new Object[] { ID_SOGG_PROG_STATO_CREDITO_FP, ID_STATO_CREDITO_FP, idModalitaAgevolazione  ,statoCredito.getStaCre_dataMod() };
				
				int[] types2 = new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC , Types.DATE };
				
				getJdbcTemplate().update(queryCopyAndInsert, args2, types2);
				
				
				String queryUpdatePrev = "UPDATE PBANDI_R_SOGG_PROG_STA_CRED_FP\r\n"
						+ "SET\r\n"
						+ "	ID_UTENTE_AGG = ?,\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE,\r\n"
						+ "	DT_FINE_VALIDITA = ? \r\n"
						+ "WHERE\r\n"
						+ "	ID_SOGG_PROG_STATO_CREDITO_FP = " + statoCredito.getStaCre_idCurrentRecord();
				
				Object[] args3 = new Object[] { statoCredito.getIdUtente(), statoCredito.getStaCre_dataMod() };
				
				int[] types3 = new int[] { Types.NUMERIC, Types.DATE };
				
				getJdbcTemplate().update(queryUpdatePrev, args3, types3);
				
				
				// Chiamo amm.vo cont. per comunicargli il cambio di stato 
				LOG.info(prf + " Chiamo amm.vo");

				Boolean ammRes = false;
				
				ammRes = amministrativoService.setStatoCredito(ID_SOGG_PROG_STATO_CREDITO_FP, ID_STATO_CREDITO_FP, idProgetto.intValue(), Date.valueOf(statoCredito.getStaCre_dataMod()), idModalitaAgevolazione.intValue(), idModalitaAgevolazioneRif, Long.parseLong(statoCredito.getIdUtente()));
				LOG.info(prf + " Amm.vo res: " + ammRes);
				
				
				
			}
			
			// se lo stato credito che voglio inserire è  In bonis => 1 
			// controllo se ci sono stati blocchi per lo stato precedente e li toglo tutti 
			//  recupero l'id_stato_credito dello stato precendente per poter togliere il blocco 
			if(ID_STATO_CREDITO_FP==1) {				
				checkInserisciSblocco(statoCredito.getIdSoggetto(), statoCredito.getIdUtente(),1);
			}

		} catch (Exception e) {
			LOG.error(prf + "Exception during setStatoCredito", e);
			throw new ErroreGestitoException("Exception during setStatoCredito", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	
	private void checkInserisciSblocco(String idSoggetto, String idUtente, int macroArea) {
		String prf = "[RicercaBeneficiarioDAOImpl::checkInserisciSblocco]";
		LOG.info(prf + " BEGIN");
		
		try {
			// controllo se ci sono blocchi stato credito e li toglo 
			// macroArea = 1 => stato credito 
			// macroArea = 2 => stato Azienda
			String sql = null;
				if(macroArea==1) {
					// recupero la causale blocco dello stato credito precendente: 
					 sql = "SELECT ptsdb.*, prsd.ID_SOGGETTO \r\n"
							+ ", pdcb.DESC_CAUSALE_BLOCCO , ptd.NUMERO_DOMANDA \r\n"
							+ "FROM PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb \r\n"
							+ "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON ptsdb.ID_CAUSALE_BLOCCO = pdcb.ID_CAUSALE_BLOCCO \r\n"
							+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
							+ "WHERE prsd.ID_SOGGETTO =?\r\n"
							+ " AND pdcb.DESC_MACROAREA ='Stato credito'\r\n"
						    + " AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL ";
				} else {
					
					sql = "SELECT ptsdb.*, prsd.ID_SOGGETTO \r\n"
							+ ", pdcb.DESC_CAUSALE_BLOCCO , ptd.NUMERO_DOMANDA \r\n"
							+ "FROM PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb \r\n"
							+ "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON ptsdb.ID_CAUSALE_BLOCCO = pdcb.ID_CAUSALE_BLOCCO \r\n"
							+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
							+ "WHERE prsd.ID_SOGGETTO =?\r\n"
							+ " AND pdcb.DESC_MACROAREA ='Stato azienda'\r\n"
							+ " AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL \r\n"
							+ " AND ptsdb.DT_FINE_VALIDITA IS NULL  ";
				}
					RowMapper<BloccoVO> lista= new RowMapper<BloccoVO>() {
						@Override
						public BloccoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
							BloccoVO blocco =  new BloccoVO(); 
							blocco.setDescCausaleBlocco(rs.getString("DESC_CAUSALE_BLOCCO"));
							blocco.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
							blocco.setDataInserimentoBlocco(rs.getDate("DT_INSERIMENTO_BLOCCO")); 
							blocco.setIdCausaleBlocco(rs.getLong("ID_CAUSALE_BLOCCO"));
							blocco.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
							blocco.setDescMacroArea(rs.getString("DESC_MACROAREA"));
							blocco.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
							blocco.setIdUtente(new BigDecimal(idUtente));
							return blocco;
						}		
					};
					List<BloccoVO> listaBlocchi = new ArrayList<BloccoVO>();
					listaBlocchi = getJdbcTemplate().query(sql, lista, idSoggetto);
					 
					if(listaBlocchi.size()>0) {
						for (BloccoVO bloccoVO : listaBlocchi) {
						
							Boolean resBloccoDom = 
									anagraficaBen.updateBloccoDomanda(bloccoVO, bloccoVO.getNumeroDomanda());
							LOG.info(prf + " resBloccoDom: " + resBloccoDom);
						}
					}
				
					
			
		} catch (Exception e) {
			LOG.error(prf + "Exception during setStatoCredito", e);
			throw new ErroreGestitoException("Exception during setStatoCredito", e);
		}
		
	}



	@Override
	public void setRating(SaveSchedaClienteAllVO rating, Long idModalitaAgevolazione) throws ErroreGestitoException {

		String prf = "[RicercaBeneficiarioDAOImpl::setRating]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf +"PRINT OBJ FROM BODY: " + rating);

		try {
			
			/*String queryIdRating = "SELECT rat.ID_RATING \r\n"
					+ "FROM PBANDI_D_RATING rat\r\n"
					+ "WHERE rat.CODICE_RATING || ' - ' || rat.DESC_RATING = '" + rating.getRat_rating() + "'";
			String ID_RATING = getJdbcTemplate().queryForObject(queryIdRating, String.class);*/

			if (rating.getRat_idCurrentRecord() == null) {
				
				String sqlNew = "INSERT INTO PBANDI_T_SOGGETTO_RATING (\r\n"
						+ "	ID_SOGGETTO_RATING,\r\n"
						+ "	ID_SOGGETTO,\r\n"
						+ "	ID_RATING,\r\n"
						+ "	DT_CLASSIFICAZIONE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO)\r\n"
						+ "VALUES (\r\n"
						+ "	SEQ_PBANDI_T_SOGG_RATING.nextval, ?, ?, ?, ?, ?, sysdate)";
				
				Object[] args1 = new Object[] { rating.getIdSoggetto(), rating.getRat_idRating(), rating.getRat_dataClassificazione(), rating.getRat_dataClassificazione(), rating.getIdUtente() };
				
				int[] types1 = new int[] { Types.NUMERIC, Types.NUMERIC, Types.DATE, Types.DATE, Types.NUMERIC };
				
				getJdbcTemplate().update(sqlNew, args1, types1);
				
			} else {
				String dtClassificazione = null;
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				if (rating.getRat_dataClassificazione() != null)
					 dtClassificazione = dt.format(rating.getRat_dataClassificazione());
				
				String queryCopyAndInsert = "INSERT INTO PBANDI_T_SOGGETTO_RATING (\r\n"
						+ "	ID_SOGGETTO_RATING,\r\n"
						+ "	ID_SOGGETTO,\r\n"
						+ "	ID_RATING,\r\n"
						+ "	DT_CLASSIFICAZIONE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO)\r\n"
						+ "SELECT\r\n"
						+ "	SEQ_PBANDI_T_SOGG_RATING.nextval,\r\n"
						+ "	ID_SOGGETTO,\r\n"
						+ "	?,\r\n"
						+ "	?,\r\n"
						+ "	?,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	SYSDATE\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_T_SOGGETTO_RATING\r\n"
						+ "WHERE\r\n"
						+ "	ID_SOGGETTO_RATING = " + rating.getRat_idCurrentRecord();
				
				Object[] args2 = new Object[] { rating.getRat_idRating(), dtClassificazione, rating.getRat_dataClassificazione() };
				
				int[] types2 = new int[] { Types.NUMERIC, Types.DATE, Types.DATE };
				
				getJdbcTemplate().update(queryCopyAndInsert, args2, types2);
				
				
				String queryUpdatePrev = "UPDATE PBANDI_T_SOGGETTO_RATING \r\n"
						+ "SET\r\n"
						+ "	DT_FINE_VALIDITA = ?,\r\n"
						+ "	ID_UTENTE_AGG = ?,\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE\r\n"
						+ "WHERE\r\n"
						+ "	ID_SOGGETTO_RATING = " + rating.getRat_idCurrentRecord();
				
				Object[] args3 = new Object[] { rating.getRat_dataClassificazione(), rating.getIdUtente() };
				
				int[] types3 = new int[] { Types.DATE, Types.NUMERIC };
				
				getJdbcTemplate().update(queryUpdatePrev, args3, types3);
				
			}			

		} catch (Exception e) {
			LOG.error(prf + "Exception during setRating", e);
			throw new ErroreGestitoException("Exception during setRating", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	
	@Override
	public void setBanca(SaveSchedaClienteAllVO banca, Long idModalitaAgevolazione) throws ErroreGestitoException {

		String prf = "[RicercaBeneficiarioDAOImpl::setBanca]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf +"PRINT OBJ FROM BODY: " + banca);

		try {
			
			String queryIdBanca = "SELECT *\r\n"
					+ "FROM (\r\n"
					+ "	SELECT ban.ID_BANCA\r\n"
					+ "	FROM PBANDI_D_BANCA ban\r\n"
					+ "	WHERE ban.DESC_BANCA = '" + banca.getBanBen_banca() + "')\r\n"
					+ "WHERE\r\n"
					+ "	rownum = 1";
			String ID_BANCA = getJdbcTemplate().queryForObject(queryIdBanca, String.class);
			
			String queryIdAttivita = "SELECT atmoncred.ID_ATTIVITA_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED atmoncred\r\n"
					+ "WHERE atmoncred.ID_TIPO_MONIT_CRED = 9 AND atmoncred.DESC_ATT_MONIT_CRED = '" + banca.getBenBen_motivazione() + "'";
			String ID_ATTIVITA_BANCA_BEN = getJdbcTemplate().queryForObject(queryIdAttivita, String.class);

			
			if (banca.getBanBen_idCurrentRecord() == null) {
				
				String sqlNew = "INSERT INTO PBANDI_R_SOGG_PROG_BANCA_BEN (\r\n"
						+ "	ID_SOGG_PROG_BANCA_BEN,\r\n"
						+ "	PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "	ID_BANCA,\r\n"
						+ "	ID_ATTIVITA_BANCA_BEN,\r\n"
						+ "	DENOM_SOGGETTO_TERZO,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO)\r\n"
						+ "VALUES (\r\n"
						+ "	SEQ_PBANDI_R_SOGG_PROG_BAN_BEN.nextval, ?, ?, ?, ?, ?, ?, sysdate)";
				
				Object[] args1 = new Object[] { banca.getBanBen_PROGR_SOGGETTO_PROGETTO(), ID_BANCA, ID_ATTIVITA_BANCA_BEN, banca.getBanBen_soggettoTerzo(), banca.getBanBen_dataModifica(), banca.getIdUtente() };
				
				int[] types1 = new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.DATE, Types.NUMERIC };
				
				getJdbcTemplate().update(sqlNew, args1, types1);
				
			} else {
				
				String queryCopyAndInsert = "INSERT INTO PBANDI_R_SOGG_PROG_BANCA_BEN (\r\n"
						+ "	ID_SOGG_PROG_BANCA_BEN,\r\n"
						+ "	PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "	ID_BANCA,\r\n"
						+ "	ID_ATTIVITA_BANCA_BEN,\r\n"
						+ "	DENOM_SOGGETTO_TERZO,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO)\r\n"
						+ "SELECT\r\n"
						+ "	SEQ_PBANDI_R_SOGG_PROG_BAN_BEN.nextval,\r\n"
						+ "	?,\r\n"
						+ "	?,\r\n"
						+ "	?,\r\n"
						+ "	?,\r\n"
						+ "	?,\r\n"
						+ "	?,\r\n"
						+ "	SYSDATE\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_R_SOGG_PROG_BANCA_BEN \r\n"
						+ "WHERE\r\n"
						+ "	ID_SOGG_PROG_BANCA_BEN = " + banca.getBanBen_idCurrentRecord();
				
				Object[] args2 = new Object[] { banca.getBanBen_PROGR_SOGGETTO_PROGETTO(), ID_BANCA, ID_ATTIVITA_BANCA_BEN, banca.getBanBen_soggettoTerzo(), banca.getBanBen_dataModifica(), banca.getIdUtente() };
				
				int[] types2 = new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.DATE, Types.NUMERIC };
				
				getJdbcTemplate().update(queryCopyAndInsert, args2, types2);
				
				
				
				String queryUpdatePrev = "UPDATE PBANDI_R_SOGG_PROG_BANCA_BEN \r\n"
						+ "SET\r\n"
						+ "	DT_FINE_VALIDITA = ?,\r\n"
						+ "	ID_UTENTE_AGG = ?,\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE\r\n"
						+ "WHERE\r\n"
						+ "	ID_SOGG_PROG_BANCA_BEN = " + banca.getBanBen_idCurrentRecord();
				
				Object[] args3 = new Object[] { banca.getBanBen_dataModifica(), banca.getIdUtente() };
				
				int[] types3 = new int[] { Types.DATE, Types.NUMERIC };
				
				getJdbcTemplate().update(queryUpdatePrev, args3, types3);
				
			}

		} catch (Exception e) {
			LOG.error(prf + "Exception during setBanca", e);
			throw new ErroreGestitoException("Exception during setBanca", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	// Attività Istruttore Area Crediti //
	
	@Override
	public List<LiberazioneGaranteVO> getLiberazioneGarante(String idProgetto, Long idModalitaAgevolazione) throws Exception {
		
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getLiberazioneGarante]";
		LOG.info(prf + " BEGIN");
			
		List<LiberazioneGaranteVO> ben = null;
		
		try {
			
			StringBuilder query = new StringBuilder();
					
			query.append("SELECT libgar.ID_LIBERAZ_GARANTE,\r\n"
					+ "	libgar.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	libgar.DENOM_GARANTE_LIBERATO,\r\n"
					+ "	libgar.DT_LIBERAZ_GARANTE,\r\n"
					+ "	perfis.ID_SOGGETTO ,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS cognome_nome,\r\n"
					+ "	libgar.IMP_LIBERAZIONE,\r\n"
					+ "	libgar.NOTE,\r\n"
					+ "	libgar.DT_INSERIMENTO,\r\n"
					+ "	libgar.DT_AGGIORNAMENTO,\r\n"
					+ "	libgar.DT_FINE_VALIDITA\r\n"
					+ "FROM PBANDI_T_LIBERAZ_GARANTE libgar\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON libgar.ID_UTENTE_INS = ute.ID_UTENTE\r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO\r\n"
					+ "WHERE libgar.ID_PROGETTO = " + idProgetto + " AND libgar.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					//+ "	AND perfis.ID_PERSONA_FISICA IN (\r\n"
					//+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					//+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					//+ "		GROUP BY perfis1.ID_SOGGETTO)\r\n"
					+ "ORDER BY libgar.DT_LIBERAZ_GARANTE DESC");
					
			
			ben = getJdbcTemplate().query(query.toString(), new LiberazioneGaranteVORowMapper());
			
		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read LiberazioneGaranteVO", e);
			throw new ErroreGestitoException("DaoException while trying to read LiberazioneGaranteVO", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		//LOG.info(ben);
		return ben;
		}
	
	
	
	@Override
	public void setLiberazioneGarante(LiberazioneGaranteVO liberazioneGarante, Long idModalitaAgevolazione) throws ErroreGestitoException {
		
		String prf = "[RicercaBeneficiarioDAOImpl::setLiberazioneGarante]";
		LOG.info(prf + " BEGIN");

		try {
			
			if ("new".equals(liberazioneGarante.getOperation())) {
				
				String queryNew = "INSERT INTO PBANDI_T_LIBERAZ_GARANTE libgar (\r\n"
						+ "	libgar.ID_LIBERAZ_GARANTE,\r\n"
						+ "	libgar.ID_PROGETTO,\r\n"
						+ "	libgar.ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	libgar.DT_LIBERAZ_GARANTE,\r\n"
						+ "	libgar.IMP_LIBERAZIONE,\r\n"
						+ "	libgar.DENOM_GARANTE_LIBERATO,\r\n"
						+ "	libgar.NOTE,\r\n"
						+ "	libgar.DT_INIZIO_VALIDITA,\r\n"
						+ "	libgar.DT_INSERIMENTO,\r\n"
						+ "	libgar.ID_UTENTE_INS )\r\n"
						+ "VALUES (\r\n"
						+ "	SEQ_PBANDI_T_LIBERAZ_GARANTE.nextval, ?, ?, ?, ?, ?, ?, sysdate, sysdate, ?)";
				
				Object[] args1 = new Object[] {
						liberazioneGarante.getIdProgetto(), idModalitaAgevolazione, liberazioneGarante.getDataLiberazione(), 
						liberazioneGarante.getImportoLiberazione(), liberazioneGarante.getGaranteLiberato(), 
						liberazioneGarante.getNote(), liberazioneGarante.getIdUtente() };
				
				int[] types1 = new int[]{ Types.INTEGER, Types.INTEGER, Types.DATE, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
				
				getJdbcTemplate().update(queryNew, args1, types1);
				
				
			} else if ("edit".equals(liberazioneGarante.getOperation())) {

				//LOG.info(liberazioneGarante);
				//LOG.info(TimeZone.getDefault());
				
				String queryCopyInsert = "INSERT INTO PBANDI_T_LIBERAZ_GARANTE (\r\n"
						+ "	ID_LIBERAZ_GARANTE,\r\n"
						+ "	ID_PROGETTO,\r\n"
						+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	DT_LIBERAZ_GARANTE,\r\n"
						+ "	IMP_LIBERAZIONE,\r\n"
						+ "	DENOM_GARANTE_LIBERATO,\r\n"
						+ "	NOTE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	ID_UTENTE_INS)\r\n"
						+ "SELECT\r\n"
						+ "	SEQ_PBANDI_T_LIBERAZ_GARANTE.nextval, ?, ?, ?, ?, ?, ?,\r\n"
						+ "	SYSDATE, SYSDATE, ?\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_T_LIBERAZ_GARANTE\r\n"
						+ "WHERE\r\n"
						+ "	ID_LIBERAZ_GARANTE = " + liberazioneGarante.getIdLibGar();
				
				Object[] args2 = new Object[] {
						liberazioneGarante.getIdProgetto(), idModalitaAgevolazione, liberazioneGarante.getDataLiberazione(), 
						liberazioneGarante.getImportoLiberazione(), liberazioneGarante.getGaranteLiberato(), 
						liberazioneGarante.getNote(), liberazioneGarante.getIdUtente() };
				
				int[] types2 = new int[]{ Types.INTEGER, Types.INTEGER, Types.DATE, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
				
				getJdbcTemplate().update(queryCopyInsert, args2, types2);
				
				
				String queryUpdatePrevious = "UPDATE PBANDI_T_LIBERAZ_GARANTE\r\n"
						+ "SET\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE,\r\n"
						+ "	ID_UTENTE_AGG = " + liberazioneGarante.getIdUtente() + ",\r\n"
						+ "	DT_FINE_VALIDITA = SYSDATE \r\n"
						+ "WHERE\r\n"
						+ "	ID_LIBERAZ_GARANTE = " + liberazioneGarante.getIdLibGar();
				
				getJdbcTemplate().update(queryUpdatePrevious);
								
			} else {
				LOG.error(prf + "Id operation ERROR");
			}


		} catch (Exception e) {
			LOG.error(prf + "Exception during setLiberazioneGarante", e);
			throw new ErroreGestitoException("Exception during setLiberazioneGarante", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	
	@Override
	public List<EscussioneConfidiVO> getEscussioneConfidi(String idProgetto, Long idModalitaAgevolazione) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getEscussioneConfidi]";
		LOG.info(prf + " BEGIN");
			
		List<EscussioneConfidiVO> ben = null;
		
		List<String> garanzie = null;
		
		try {
			
			StringBuilder query = new StringBuilder();
			
			StringBuilder listQuery = new StringBuilder();

			query.append("SELECT escuconf.ID_ESCUSS_CONFIDI,\r\n"
					+ "	escuconf.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	escuconf.DENOM_CONFIDI,\r\n"
					+ "	escuconf.DT_ESCUSS_CONFIDI,\r\n"
					+ "	escuconf.DT_PAGAM_CONFIDI,\r\n"
					+ "	escuconf.ID_ATTIVITA_GARANZIA_CONFIDI,\r\n"
					+ "	moncred.DESC_ATT_MONIT_CRED,\r\n"
					+ "	escuconf.PERC_GARANZIA,\r\n"
					+ "	escuconf.NOTE,\r\n"
					+ "	escuconf.DT_INSERIMENTO,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS istruttore\r\n"
					+ "FROM PBANDI_T_ESCUSS_CONFIDI escuconf\r\n"
					+ "LEFT JOIN PBANDI_D_ATTIVITA_MONIT_CRED moncred ON escuconf.ID_ATTIVITA_GARANZIA_CONFIDI = moncred.ID_ATTIVITA_MONIT_CRED\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON escuconf.ID_UTENTE_INS = ute.ID_UTENTE \r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO\r\n"
					+ "WHERE perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO)\r\n"
					+ "	AND escuconf.ID_PROGETTO = " + idProgetto + " AND escuconf.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "ORDER BY escuconf.ID_ESCUSS_CONFIDI DESC");
					
			
			ben = getJdbcTemplate().query(query.toString(), new EscussioneConfidiVORowMapper());

			if (ben.size() == 0) { ben.add(new EscussioneConfidiVO()); }

			listQuery.append("SELECT moncred.DESC_ATT_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED moncred\r\n"
					+ "WHERE moncred.ID_TIPO_MONIT_CRED = 3");
			garanzie = getJdbcTemplate().queryForList(listQuery.toString(), String.class);

			ben.get(0).setListGaranzie(garanzie);

		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read EscussioneConfidiVO", e);
			throw new ErroreGestitoException("DaoException while trying to read EscussioneConfidiVO", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		//LOG.info(ben);
		return ben;
		}
	
	
	@Override
	public void setEscussioneConfidi(EscussioneConfidiVO escussioneConfidi, Long idModalitaAgevolazione) throws ErroreGestitoException {
		String prf = "[RicercaBeneficiarioDAOImpl::setEscussioneConfidi]";
		LOG.info(prf + " BEGIN");

		try {
			
			String idGaranzia = null;
			
			if (escussioneConfidi.getGaranzia() != null) {
				String queryIdGaranzia = "SELECT mon.ID_ATTIVITA_MONIT_CRED\r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED mon\r\n"
					+ "WHERE mon.DESC_ATT_MONIT_CRED = '" + escussioneConfidi.getGaranzia() + "'";
				idGaranzia = getJdbcTemplate().queryForObject(queryIdGaranzia, String.class);
			}
			

			if (escussioneConfidi.getIdCurrentRecord() == null) {

				String queryNew = "INSERT INTO PBANDI_T_ESCUSS_CONFIDI (\r\n"
						+ "	ID_ESCUSS_CONFIDI,\r\n"
						+ "	ID_PROGETTO,\r\n"
						+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	ID_ATTIVITA_GARANZIA_CONFIDI,\r\n"
						+ "	DENOM_CONFIDI,\r\n"
						+ "	DT_ESCUSS_CONFIDI,\r\n"
						+ "	DT_PAGAM_CONFIDI,\r\n"
						+ "	PERC_GARANZIA,\r\n"
						+ "	NOTE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	ID_UTENTE_INS )\r\n"
						+ "VALUES (\r\n"
						+ "	SEQ_PBANDI_T_ESCUSS_CONFIDI.nextval, ?, ?, ?, ?, ?, ?, ?, ?,\r\n"
						+ "	sysdate, sysdate, ? )";

				Object[] args1 = new Object[] {
						escussioneConfidi.getIdProgetto(), idModalitaAgevolazione, idGaranzia, escussioneConfidi.getNominativo(), 
						escussioneConfidi.getDataEscussione(), escussioneConfidi.getDataPagamento(), 
						escussioneConfidi.getPercGaranzia(), escussioneConfidi.getNote(),
						escussioneConfidi.getIdUtente() };

				int[] types1 = new int[]{
						Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.DATE, Types.DATE, Types.DECIMAL,
						Types.VARCHAR, Types.INTEGER };

				getJdbcTemplate().update(queryNew, args1, types1);


			} else {

				String queryInsertEdit = "INSERT INTO PBANDI_T_ESCUSS_CONFIDI (\r\n"
						+ "	ID_ESCUSS_CONFIDI,\r\n"
						+ "	ID_PROGETTO,\r\n"
						+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	ID_ATTIVITA_GARANZIA_CONFIDI,\r\n"
						+ "	DENOM_CONFIDI,\r\n"
						+ "	DT_ESCUSS_CONFIDI,\r\n"
						+ "	DT_PAGAM_CONFIDI,\r\n"
						+ "	PERC_GARANZIA,\r\n"
						+ "	NOTE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	ID_UTENTE_INS )\r\n"
						+ "VALUES (\r\n"
						+ "	SEQ_PBANDI_T_ESCUSS_CONFIDI.nextval, ?, ?, ?, ?, ?, ?, ?, ?,\r\n"
						+ "	sysdate, sysdate, ? )";

				Object[] args2 = new Object[] {
						escussioneConfidi.getIdProgetto(), idModalitaAgevolazione, idGaranzia, escussioneConfidi.getNominativo(), 
						escussioneConfidi.getDataEscussione(), escussioneConfidi.getDataPagamento(), 
						escussioneConfidi.getPercGaranzia(), escussioneConfidi.getNote(),
						escussioneConfidi.getIdUtente() };

				int[] types2 = new int[]{
						Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.DATE, Types.DATE, Types.DECIMAL,
						Types.VARCHAR, Types.INTEGER };

				getJdbcTemplate().update(queryInsertEdit, args2, types2);


				String queryUpdatePrevious = "UPDATE PBANDI_T_ESCUSS_CONFIDI\r\n"
						+ "SET\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE,\r\n"
						+ "	ID_UTENTE_AGG = " + escussioneConfidi.getIdUtente() + ",\r\n"
						+ "	DT_FINE_VALIDITA = SYSDATE \r\n"
						+ "WHERE\r\n"
						+ "	ID_ESCUSS_CONFIDI = " + escussioneConfidi.getIdCurrentRecord();

				getJdbcTemplate().update(queryUpdatePrevious);
			}

		} catch (Exception e) {
			LOG.error(prf + "Exception during setEscussioneConfidi", e);
			throw new ErroreGestitoException("Exception during setEscussioneConfidi", e);
		} finally {
			LOG.info(prf + " END");
		}
		
	}
	
	
	
	
	
	
	@Override
	public List<PianoRientroVO> getPianoRientro(String idProgetto, Long idModalitaAgevolazione) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getPianoRientro]";
		LOG.info(prf + " BEGIN");
			
		List<PianoRientroVO> piari = null;
		
		List<String> esiti = null;
		List<String> recuperi = null;
		
		try {
			
			StringBuilder query = new StringBuilder();
					
			StringBuilder queryEsiti = new StringBuilder();
			StringBuilder queryRecuperi = new StringBuilder();
			
			query.append("SELECT piari.ID_PIANO_RIENTRO,\r\n"
					+ "	piari.ID_MODALITA_AGEVOLAZIONE, \r\n"
					+ "	piari.ID_ATTIVITA_ESITO,\r\n"
					+ "	atmoncred.DESC_ATT_MONIT_CRED AS esito,\r\n"
					+ "	piari.DT_ESITO,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS cognome_nome,\r\n"
					+ "	piari.DT_PROPOSTA,\r\n"
					+ "	piari.IMP_QUOTA_CAPITALE,\r\n"
					+ "	piari.IMP_AGEVOLAZIONE,\r\n"
					+ "	piari.ID_ATTIVITA_RECUPERO,\r\n"
					+ "	atmoncredd.DESC_ATT_MONIT_CRED AS recupero,\r\n"
					+ "	piari.NOTE,\r\n"
					+ "	piari.DT_INSERIMENTO,\r\n"
					+ "	piari.ID_PROGETTO,\r\n"
					+ "	piari.DT_INIZIO_VALIDITA,\r\n"
					+ "	piari.DT_FINE_VALIDITA,\r\n"
					+ "	piari.ID_UTENTE_INS,\r\n"
					+ "	piari.ID_UTENTE_AGG,\r\n"
					+ "	piari.DT_INSERIMENTO,\r\n"
					+ "	piari.DT_AGGIORNAMENTO\r\n"
					+ "FROM PBANDI_T_PIANO_RIENTRO piari\r\n"
					+ "LEFT JOIN PBANDI_D_ATTIVITA_MONIT_CRED atmoncred ON piari.ID_ATTIVITA_ESITO = atmoncred.ID_ATTIVITA_MONIT_CRED\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON piari.ID_UTENTE_INS = ute.ID_UTENTE\r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO\r\n"
					+ "LEFT JOIN PBANDI_D_ATTIVITA_MONIT_CRED atmoncredd ON piari.ID_ATTIVITA_RECUPERO = atmoncredd.ID_ATTIVITA_MONIT_CRED\r\n"
					+ "WHERE piari.ID_PROGETTO = " + idProgetto + " AND piari.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					//+ "	AND perfis.ID_PERSONA_FISICA IN (\r\n"
					//+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					//+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					//+ "		GROUP BY perfis1.ID_SOGGETTO)\r\n"
					+ "ORDER BY piari.ID_PIANO_RIENTRO DESC");
			
			piari = getJdbcTemplate().query(query.toString(), new PianoRientroVORowMapper());
			
			if (piari.size() == 0) { piari.add(new PianoRientroVO()); }

			queryEsiti.append("SELECT atmoncred.DESC_ATT_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED atmoncred\r\n"
					+ "WHERE atmoncred.ID_TIPO_MONIT_CRED = 4");
			esiti = getJdbcTemplate().queryForList(queryEsiti.toString(), String.class);
			piari.get(0).setEsiti(esiti);

			queryRecuperi.append("SELECT atmoncred.DESC_ATT_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED atmoncred\r\n"
					+ "WHERE atmoncred.ID_TIPO_MONIT_CRED = 5");
			recuperi = getJdbcTemplate().queryForList(queryRecuperi.toString(), String.class);
			piari.get(0).setRecuperi(recuperi);


		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read PianoRientroVO", e);
			throw new ErroreGestitoException("DaoException while trying to read PianoRientroVO", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return piari;
		}
	
	
	@Override
	public void setPianoRientro(PianoRientroVO pianoRientro, Long idModalitaAgevolazione) throws ErroreGestitoException {
		String prf = "[RicercaBeneficiarioDAOImpl::setPianoRientro]";
		LOG.info(prf + " BEGIN");

		try {
			
			String idAttivitaEsito = null;
			String idAttivitaRecupero = null;
			
			if (pianoRientro.getEsito() != null) {

				String queryIdEsito = "SELECT atmoncred.ID_ATTIVITA_MONIT_CRED \r\n"
						+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED atmoncred\r\n"
						+ "WHERE atmoncred.DESC_ATT_MONIT_CRED = '" + pianoRientro.getEsito() + "'";
				idAttivitaEsito = getJdbcTemplate().queryForObject(queryIdEsito, String.class);
			}

			if (pianoRientro.getRecupero() != null) {
				
				String queryIdRecupero = "SELECT atmoncred.ID_ATTIVITA_MONIT_CRED \r\n"
						+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED atmoncred\r\n"
						+ "WHERE atmoncred.DESC_ATT_MONIT_CRED = '" + pianoRientro.getRecupero() + "'";
				idAttivitaRecupero = getJdbcTemplate().queryForObject(queryIdRecupero, String.class);
			}
			
			
			if ("new".equals(pianoRientro.getOperation())) {
				
				String queryNew = "INSERT INTO PBANDI_T_PIANO_RIENTRO piari (\r\n"
						+ "	piari.ID_PIANO_RIENTRO,\r\n"
						+ "	piari.ID_PROGETTO,\r\n"
						+ "	piari.ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	piari.DT_PROPOSTA,\r\n"
						+ "	piari.IMP_QUOTA_CAPITALE,\r\n"
						+ "	piari.IMP_AGEVOLAZIONE,\r\n"
						+ "	piari.ID_ATTIVITA_ESITO,\r\n"
						+ "	piari.DT_ESITO,\r\n"
						+ "	piari.ID_ATTIVITA_RECUPERO,\r\n"
						+ "	piari.NOTE,\r\n"
						+ "	piari.DT_INIZIO_VALIDITA,\r\n"
						+ "	piari.ID_UTENTE_INS,\r\n"
						+ "	piari.DT_INSERIMENTO)\r\n"
						+ "VALUES (\r\n"
						+ "	SEQ_PBANDI_T_PIANO_RIENTRO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, sysdate)";
				
				Object[] args1 = new Object[] {
						pianoRientro.getIdProgetto(), idModalitaAgevolazione, pianoRientro.getDataProposta(),
						pianoRientro.getImportoCapitale(), pianoRientro.getImportoAgevolazione(),
						idAttivitaEsito, pianoRientro.getDataEsito(), idAttivitaRecupero,
						pianoRientro.getNote(), pianoRientro.getIdUtente() };
				
				int[] types1 = new int[]{
						Types.INTEGER, Types.INTEGER, Types.DATE, Types.DECIMAL, Types.DECIMAL, Types.INTEGER,
						Types.DATE, Types.INTEGER, Types.VARCHAR, Types.INTEGER };
				
				getJdbcTemplate().update(queryNew, args1, types1);
				
				
			} else if ("edit".equals(pianoRientro.getOperation())) {
				
				String queryCopyUpdate = "INSERT INTO PBANDI_T_PIANO_RIENTRO (\r\n"
						+ "	ID_PIANO_RIENTRO,\r\n"
						+ "	ID_PROGETTO,\r\n"
						+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	DT_PROPOSTA,\r\n"
						+ "	IMP_QUOTA_CAPITALE,\r\n"
						+ "	IMP_AGEVOLAZIONE,\r\n"
						+ "	ID_ATTIVITA_ESITO,\r\n"
						+ "	DT_ESITO,\r\n"
						+ "	ID_ATTIVITA_RECUPERO,\r\n"
						+ "	NOTE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO)\r\n"
						+ "SELECT\r\n"
						+ "	SEQ_PBANDI_T_PIANO_RIENTRO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?,\r\n"
						+ "	SYSDATE, ?, SYSDATE\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_T_PIANO_RIENTRO\r\n"
						+ "WHERE\r\n"
						+ "	ID_PIANO_RIENTRO = " + pianoRientro.getIdCurrentRecord();
				
				Object[] args2 = new Object[] {
						pianoRientro.getIdProgetto(), idModalitaAgevolazione, pianoRientro.getDataProposta(),
						pianoRientro.getImportoCapitale(), pianoRientro.getImportoAgevolazione(),
						idAttivitaEsito, pianoRientro.getDataEsito(), idAttivitaRecupero,
						pianoRientro.getNote(), pianoRientro.getIdUtente() };
				
				int[] types2 = new int[]{
						Types.INTEGER, Types.INTEGER, Types.DATE, Types.DECIMAL, Types.DECIMAL, Types.INTEGER,
						Types.DATE, Types.INTEGER, Types.VARCHAR, Types.INTEGER };
				
				getJdbcTemplate().update(queryCopyUpdate, args2, types2);
				
				
				String queryUpdatePrevious = "UPDATE PBANDI_T_PIANO_RIENTRO\r\n"
						+ "SET\r\n"
						+ "	DT_FINE_VALIDITA = SYSDATE,\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE,\r\n"
						+ "	ID_UTENTE_AGG = " + pianoRientro.getIdUtente() + "\r\n"
						+ "WHERE\r\n"
						+ "	ID_PIANO_RIENTRO = " + pianoRientro.getIdCurrentRecord();
				
				getJdbcTemplate().update(queryUpdatePrevious);
							
			} else {
				LOG.error("There was an error with the operation id");
			}
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception during setPianoRientro", e);
			throw new ErroreGestitoException("Exception during setPianoRientro", e);
		} finally {
			LOG.info(prf + " END");
		}	
	}
	
	
	
	@Override
	public List<LiberazioneBancaVO> getLiberazioneBanca(String idProgetto, Long idModalitaAgevolazione) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getLiberazioneBanca]";
		LOG.info(prf + " BEGIN");
			
		List<LiberazioneBancaVO> liban = null;
		
		List<String> motivazioni = null;
		
		try {
			
			StringBuilder query = new StringBuilder();
					
			StringBuilder queryMotivazioni = new StringBuilder();
			
			query.append("SELECT liban.ID_LIBERAZ_BANCA,\r\n"
					+ "	liban.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	liban.DT_LIBERAZ_BANCA,\r\n"
					+ "	liban.ID_ATTIVITA_LIBERAZIONE,\r\n"
					+ "	atmoncred.DESC_ATT_MONIT_CRED,\r\n"
					+ "	liban.BANCA_LIBERATA,\r\n"
					+ "	liban.NOTE,\r\n"
					+ "	liban.DT_INSERIMENTO,\r\n"
					+ "	perfis.COGNOME || ' ' || perfis.NOME AS istruttore\r\n"
					+ "FROM PBANDI_T_LIBERAZ_BANCA liban\r\n"
					+ "LEFT JOIN PBANDI_D_ATTIVITA_MONIT_CRED atmoncred ON liban.ID_ATTIVITA_LIBERAZIONE = atmoncred.ID_ATTIVITA_MONIT_CRED\r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON liban.ID_UTENTE_INS = ute.ID_UTENTE\r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO \r\n"
					+ "WHERE /*perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO) AND*/\r\n"
					+ "	liban.ID_PROGETTO = " + idProgetto + " AND liban.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "ORDER BY liban.ID_LIBERAZ_BANCA DESC");
			
			liban = getJdbcTemplate().query(query.toString(), new LiberazioneBancaVORowMapper());

			if (liban.size() == 0) { liban.add(new LiberazioneBancaVO()); }

			queryMotivazioni.append("SELECT atmoncred.DESC_ATT_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED atmoncred \r\n"
					+ "WHERE atmoncred.ID_TIPO_MONIT_CRED = 6\r\n"
					+ "ORDER BY atmoncred.ID_ATTIVITA_MONIT_CRED ASC");
			motivazioni = getJdbcTemplate().queryForList(queryMotivazioni.toString(), String.class);
			liban.get(0).setMotivazioni(motivazioni);


			//LOG.info(liban);
			
		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read LiberazioneBancaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read LiberazioneBancaVO", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return liban;
		}
	
	
	
	
	@Override
	public void setLiberazioneBanca(LiberazioneBancaVO liberazioneBanca, Long idModalitaAgevolazione) throws ErroreGestitoException {
		String prf = "[RicercaBeneficiarioDAOImpl::setLiberazioneBanca]";
		LOG.info(prf + " BEGIN");

		try {

			BigDecimal idMotivazione = null;
			
			if (liberazioneBanca.getMotivazione() != null) {
				// FIND THE ID OF THE SELECTED MOTIVAZIONE FROM FRONT-END
				String findId = "SELECT atmoncred.ID_ATTIVITA_MONIT_CRED AS id\r\n"
						+ "FROM PBANDI_D_ATTIVITA_MONIT_CRED atmoncred\r\n"
						+ "WHERE atmoncred.ID_TIPO_MONIT_CRED = 6 AND\r\n" + "atmoncred.DESC_ATT_MONIT_CRED = '"
						+ liberazioneBanca.getMotivazione() + "'";
				idMotivazione = getJdbcTemplate().queryForObject(findId, BigDecimal.class);
				//LOG.info(idMotivazione);
			}

			StringBuilder queryIsOneRecord = new StringBuilder();
			queryIsOneRecord.append("SELECT COUNT(liban.ID_LIBERAZ_BANCA) AS n\r\n"
					+ "FROM PBANDI_T_LIBERAZ_BANCA liban\r\n"
					+ "WHERE liban.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + " AND liban.ID_PROGETTO = " + liberazioneBanca.getIdProgetto());
			BigDecimal nRecordsPerProj = getJdbcTemplate().queryForObject(queryIsOneRecord.toString(), BigDecimal.class);
			//LOG.info(nRecordsPerProj);
			
			// CHECK IF A RECORD ALREADY EXISTS 
			if (nRecordsPerProj.compareTo(BigDecimal.ZERO) > 0) {
				
				LOG.info("A previous record was found");
				
				// UPDATE THE PREVIOUS RECORD
				String update = "UPDATE PBANDI_T_LIBERAZ_BANCA liban\r\n"
						+ "SET\r\n"
						+ "	liban.DT_FINE_VALIDITA = SYSDATE,\r\n"
						+ "	liban.ID_UTENTE_AGG = " + liberazioneBanca.getIdUtente() + ",\r\n"
						+ "	liban.DT_AGGIORNAMENTO = SYSDATE\r\n"
						+ "WHERE\r\n"
						+ "	liban.ID_LIBERAZ_BANCA = " + liberazioneBanca.getIdCurrentRecord();
				getJdbcTemplate().update(update);
			}
			
			// CREATE A NEW ROW
			String queryNew = "INSERT INTO PBANDI_T_LIBERAZ_BANCA liban (\r\n"
					+ "	liban.ID_LIBERAZ_BANCA,\r\n"
					+ "	liban.ID_PROGETTO,\r\n"
					+ "	liban.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	liban.ID_ATTIVITA_LIBERAZIONE,\r\n"
					+ "	liban.DT_LIBERAZ_BANCA,\r\n"
					+ "	liban.BANCA_LIBERATA,\r\n"
					+ "	liban.NOTE,\r\n"
					+ "	liban.DT_INIZIO_VALIDITA,\r\n"
					+ "	liban.ID_UTENTE_INS,\r\n"
					+ "	liban.DT_INSERIMENTO )\r\n"
					+ "VALUES (\r\n"
					+ "	SEQ_PBANDI_T_LIBERAZ_BANCA.nextval, ?, ?, ?, ?, ?, ?, sysdate, ?, sysdate)";
			Object[] args = new Object[] {
					liberazioneBanca.getIdProgetto(),
					idModalitaAgevolazione,
					idMotivazione,
					liberazioneBanca.getDataLiberazione(),
					liberazioneBanca.getBancaLiberata(),
					liberazioneBanca.getNote(),
					liberazioneBanca.getIdUtente()};
			int[] types = new int[]{
					Types.NUMERIC,
					Types.NUMERIC,
					Types.NUMERIC,
					Types.DATE,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.NUMERIC};
			getJdbcTemplate().update(queryNew, args, types);

			
		} catch (Exception e) {
			LOG.error(prf + "Exception during setLiberazioneBanca", e);
			throw new ErroreGestitoException("Exception during setLiberazioneBanca", e);
		} finally {
			LOG.info(prf + " END");
		}	
	}
	
	
	
	
	@Override
	public List<IscrizioneRuoloVO> getIscrizioneRuolo(String idProgetto, Long idModalitaAgevolazione) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getIscrizioneRuolo]";
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
	public void setIscrizioneRuolo(IscrizioneRuoloVO iscrizioneRuolo, Long idModalitaAgevolazione) throws ErroreGestitoException {
		String prf = "[RicercaBeneficiarioDAOImpl::setIscrizioneRuolo]";
		LOG.info(prf + " BEGIN");

		try {

			if (iscrizioneRuolo.getIdCurrentRecord() == null) {
				
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
						+ "	SEQ_PBANDI_T_ISCRIZIONE_RUOLO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, sysdate)";
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
				
				getJdbcTemplate().update(queryNew, args1, types1);
				
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
	
	
	
	
	@Override
	public List<CessioneQuotaVO> getCessioneQuota(String idProgetto, Long idModalitaAgevolazione) throws Exception {
		String prf = "[RicercaBeneficiariCreditiDAOImpl::getCessioneQuota]";
		LOG.info(prf + " BEGIN");
			
		List<CessioneQuotaVO> cess = null;
		
		List<String> stati = null;
		
		try {
			//LOG.info(idProgetto);
			String query = "SELECT cessquo.*, stace.DESC_ATTIVITA_SERVICER, perfis.COGNOME || ' ' || perfis.NOME AS istruttore\r\n"
					+ "FROM PBANDI_T_SERVICER cessquo\r\n"
					+ "LEFT JOIN PBANDI_D_ATTIVITA_SERVICER stace ON cessquo.ID_ATTIVITA_SERVICER = stace.ID_ATTIVITA_SERVICER \r\n"
					+ "LEFT JOIN PBANDI_T_UTENTE ute ON cessquo.ID_UTENTE_INS = ute.ID_UTENTE \r\n"
					+ "LEFT JOIN PBANDI_T_PERSONA_FISICA perfis ON ute.ID_SOGGETTO = perfis.ID_SOGGETTO\r\n"
					+ "WHERE /*perfis.ID_PERSONA_FISICA IN (\r\n"
					+ "		SELECT MAX(perfis1.ID_PERSONA_FISICA) AS mass\r\n"
					+ "		FROM PBANDI_T_PERSONA_FISICA perfis1\r\n"
					+ "		GROUP BY perfis1.ID_SOGGETTO) AND*/\r\n"
					+ "	cessquo.ID_PROGETTO = " + idProgetto + " AND cessquo.ID_MODALITA_AGEVOLAZIONE = " + idModalitaAgevolazione + "\r\n"
					+ "ORDER BY cessquo.ID_SERVICER DESC";
			/* Ho commentato il filtro per l'istruttore (perfis1) perché generava errore, sperando che il problema del doppio nome sia stato risolto dopo la migrazione. */
			cess = getJdbcTemplate().query(query, new CessioneQuotaVORowMapper());	
			
			if (cess.size() == 0) { cess.add(new CessioneQuotaVO()); }

			String queryStati = "SELECT stace.DESC_ATTIVITA_SERVICER \r\n"
					+ "FROM PBANDI_D_ATTIVITA_SERVICER stace\r\n"
					+ "ORDER BY stace.ID_ATTIVITA_SERVICER ASC";
			stati = getJdbcTemplate().queryForList(queryStati, String.class);
			cess.get(0).setListStati(stati);
			
			//LOG.info(cess);
		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read CessioneQuotaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read CessioneQuotaVO", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return cess;
		}
	
	
	
	
	@Override
	public void setCessioneQuota(CessioneQuotaVO cessioneQuota, Long idModalitaAgevolazione) throws ErroreGestitoException {
		String prf = "[RicercaBeneficiarioDAOImpl::setCessioneQuota]";
		LOG.info(prf + " BEGIN");

		try {
			
			BigDecimal idStatoCessione = null;
			
			if (cessioneQuota.getStatoCess() != null) {
				
				String findId = "SELECT stace.ID_ATTIVITA_SERVICER \r\n"
						+ "FROM PBANDI_D_ATTIVITA_SERVICER stace\r\n"
						+ "WHERE stace.DESC_ATTIVITA_SERVICER = '" + cessioneQuota.getStatoCess() + "'";
				idStatoCessione = getJdbcTemplate().queryForObject(findId, BigDecimal.class);
				
			}
			String sqlSeq = "SELECT SEQ_PBANDI_T_SERVICER.nextval FROM dual";
			long idCessioneQuota = (long) getJdbcTemplate().queryForObject(sqlSeq.toString(), long.class);
			

			if (cessioneQuota.getIdCurrentRecord() == null) {
				
				String queryNew = "INSERT INTO PBANDI_T_SERVICER cessquo (\r\n"
						+ "	cessquo.ID_SERVICER,\r\n"
						+ "	cessquo.ID_PROGETTO,\r\n"
						+ "	cessquo.ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	cessquo.IMP_CESSIONE_CAPITALE,\r\n"
						+ "	cessquo.IMP_CESSIONE_ONERI,\r\n"
						+ "	cessquo.IMP_CESSIONE_INTERESSI_MORA,\r\n"
						+ "	cessquo.IMP_CESSIONE_COMPLESSIVA,\r\n"
						+ "	cessquo.DT_CESSIONE,\r\n"
						+ "	cessquo.IMP_CORRISP_CESSIONE,\r\n"
						+ "	cessquo.DENOM_CESSIONARIO,\r\n"
						+ "	cessquo.NOTE,\r\n"
						+ "	cessquo.DT_INIZIO_VALIDITA,\r\n"
						+ "	cessquo.ID_UTENTE_INS,\r\n"
						+ "	cessquo.DT_INSERIMENTO,\r\n"
						+ "	cessquo.ID_ATTIVITA_SERVICER)\r\n"
						+ "VALUES (\r\n"
						+ "	?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,\r\n"
						+ "	sysdate, ?, sysdate, ?)";
				
				Object[] args1 = new Object[] {idCessioneQuota, 
						cessioneQuota.getIdProgetto(), idModalitaAgevolazione, cessioneQuota.getImpCessQuotaCap(),
						cessioneQuota.getImpCessOneri(), cessioneQuota.getImpCessInterMora(),
						cessioneQuota.getImpTotCess(), cessioneQuota.getDataCessione(),
						cessioneQuota.getCorrispettivoCess(), cessioneQuota.getNominativoCess(),
						cessioneQuota.getNote(), cessioneQuota.getIdUtente(), idStatoCessione };
				
				int[] types1 = new int[]{Type.LONG, 
						Types.INTEGER, Types.INTEGER, Types.DECIMAL, Types.DECIMAL, Types.DECIMAL, Types.DECIMAL,
						Types.DATE, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER };
				
				getJdbcTemplate().update(queryNew, args1, types1);
				
				//chiamata ad insert su t_distinta per amm.vo contabile
				//setDistinta(cessioneQuota.getIdProgetto(), cessioneQuota.getIdUtente(), idCessioneQuota, cessioneQuota);
				
			} else {
				
				String queryCopyAndInsert = "INSERT INTO PBANDI_T_SERVICER (\r\n"
						+ "	ID_SERVICER,\r\n"
						+ "	ID_PROGETTO,\r\n"
						+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
						+ "	IMP_CESSIONE_CAPITALE,\r\n"
						+ "	IMP_CESSIONE_ONERI,\r\n"
						+ "	IMP_CESSIONE_INTERESSI_MORA,\r\n"
						+ "	IMP_CESSIONE_COMPLESSIVA,\r\n"
						+ "	DT_CESSIONE,\r\n"
						+ "	IMP_CORRISP_CESSIONE,\r\n"
						+ "	DENOM_CESSIONARIO,\r\n"
						+ "	NOTE,\r\n"
						+ "	DT_INIZIO_VALIDITA,\r\n"
						+ "	ID_UTENTE_INS,\r\n"
						+ "	DT_INSERIMENTO,\r\n"
						+ "	ID_ATTIVITA_SERVICER)\r\n"
						+ "SELECT\r\n"
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,\r\n"
						+ "	SYSDATE, ?, SYSDATE, ?\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_T_SERVICER\r\n"
						+ "WHERE\r\n"
						+ "	ID_SERVICER = " + cessioneQuota.getIdCurrentRecord();
				
				Object[] args2 = new Object[] {idCessioneQuota, 
						cessioneQuota.getIdProgetto(), idModalitaAgevolazione, cessioneQuota.getImpCessQuotaCap(),
						cessioneQuota.getImpCessOneri(), cessioneQuota.getImpCessInterMora(),
						cessioneQuota.getImpTotCess(), cessioneQuota.getDataCessione(),
						cessioneQuota.getCorrispettivoCess(), cessioneQuota.getNominativoCess(),
						cessioneQuota.getNote(), cessioneQuota.getIdUtente(), idStatoCessione };
				
				int[] types2 = new int[]{Type.LONG,
						Types.INTEGER, Types.INTEGER, Types.DECIMAL, Types.DECIMAL, Types.DECIMAL, Types.DECIMAL,
						Types.DATE, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER };
				
				getJdbcTemplate().update(queryCopyAndInsert, args2, types2);
				
				//chiamata ad insert su t_distinta per amm.vo contabile
				//setDistinta(cessioneQuota.getIdProgetto(), cessioneQuota.getIdUtente(), idCessioneQuota, cessioneQuota);
								
				
				String queryUpdatePrevious = "UPDATE PBANDI_T_SERVICER\r\n"
						+ "SET\r\n"
						+ "	DT_FINE_VALIDITA = SYSDATE,\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE,\r\n"
						+ "	ID_UTENTE_AGG = " + cessioneQuota.getIdUtente() + " \r\n"
						+ "WHERE\r\n"
						+ "	ID_SERVICER = " + cessioneQuota.getIdCurrentRecord();
				
				getJdbcTemplate().update(queryUpdatePrevious);
			}
			
		} catch (Exception e) {
			LOG.error(prf + "Exception during setCessioneQuota", e);
			throw new ErroreGestitoException("Exception during setCessioneQuota", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	/*private long setDistinta(Long idProgetto, Long idUtente, long idCessioneQuota, CessioneQuotaVO cessioneQuota) {
		int up1 = 0;
		int up2 = 0; 
		
		try {
			
			// get idDistinta
			String sqlSeq = "SELECT SEQ_PBANDI_T_DISTINTA.nextval FROM dual";
			int idDistinta = getJdbcTemplate().queryForObject(sqlSeq.toString(), Integer.class);
			
			// get idDistintaDett
			String sqlSeq2 = "SELECT SEQ_PBANDI_T_DISTINTA_DETT.nextval FROM dual";
			int idDistintaDett = getJdbcTemplate().queryForObject(sqlSeq2.toString(), Integer.class);
			
			// get idModalitaAgevolazione da chiarire con amministrativo contabile, 
			String sqlAge= "SELECT DISTINCT prbmaeb.ID_MODALITA_AGEVOLAZIONE \r\n"
					+ "FROM PBANDI_T_BANDO ptb \r\n"
					+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptb.ID_BANDO = prbli.ID_BANDO \r\n"
					+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \r\n"
					+ "LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
					+ "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "LEFT JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.ID_BANDO = ptb.ID_BANDO \r\n"
					+ "WHERE prsp.ID_PROGETTO = " + idProgetto +"\r\n"
					+ "AND ROWNUM=1";
			int idModalitaAgevolazione = getJdbcTemplate().queryForObject(sqlAge.toString(), Integer.class);

			sqlAge = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
					"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
					"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
			int idModalitaAgevolazioneRif = getJdbcTemplate().queryForObject(sqlAge, Integer.class, idModalitaAgevolazione);
			
			
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
			
			up1 = getJdbcTemplate().update(insert, new Object[] {idDistinta, getIdEntitaQuota(), 5, idModalitaAgevolazione, 8, idUtente});

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
			
			up2 = getJdbcTemplate().update(insertDist, new Object[] {idDistintaDett, rigaDistinta, idDistinta, idCessioneQuota, idUtente});

			
		
			//dopo aver inserito su t_distinte, chiama il servizio di amm.vo cont
			amministrativoContabileServiceImpl.callToDistintaErogazioneCessione(idDistinta, rigaDistinta.intValue(), idProgetto.intValue(),
					cessioneQuota.getDataCessione(), (cessioneQuota.getImpTotCess() != null) ? cessioneQuota.getImpTotCess().doubleValue() : (double) 0,
					idModalitaAgevolazione, idModalitaAgevolazioneRif, idUtente);
			
		} catch (Exception e) {
			LOG.error(e);
			return (long) -1;
		}		
		
		return (long) (up1 - up2);
	}
	
	public Long getIdEntitaQuota() throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		Long idEntita;

		try {

			idEntita = getJdbcTemplate().queryForObject(
					"SELECT entita.id_entita AS idEntita \n" +
							"FROM PBANDI_C_ENTITA entita \n" +
							"WHERE entita.nome_entita = 'PBANDI_T_SERVICER'",
					Long.class
			);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			LOG.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			LOG.info(prf + " END");
		}

		return idEntita;
	}*/
}
