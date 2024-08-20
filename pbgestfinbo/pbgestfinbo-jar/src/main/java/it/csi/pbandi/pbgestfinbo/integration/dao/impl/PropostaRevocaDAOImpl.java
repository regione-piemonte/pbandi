/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.ammvoservrest.dto.ImportiRevoche;
import it.csi.pbandi.pbgestfinbo.business.service.AmministrativoContabileService;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.PropostaRevocaVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.ModalitaAgevolazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.PropostaRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.*;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;


@Service
public class PropostaRevocaDAOImpl extends JdbcDaoSupport implements PropostaRevocaDAO {
	@Autowired
	AmministrativoContabileService amministrativoContabileService;
	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public PropostaRevocaDAOImpl(DataSource dataSource){ setDataSource(dataSource); }
	@Override
	public List<PropostaRevocaVO> getProposteRevoche(Long idSoggetto, Long idBando, Long idProgetto, Long idCausaleBlocco, Long idStatoRevoca, Long numeroPropostaRevoca, Date dataPropostaRevocaFrom, Date dataPropostaRevocaTo, HttpServletRequest req) {
		String prf = "[PropostaRevocaDAOImpl::getProposteRevoche]";
		LOG.info(prf + " BEGIN");
		List<PropostaRevocaVO> lista;

		try {
			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT UNIQUE \n" +
							"gestioneRevoca.id_gestione_revoca AS idPropostaRevoca, \n" +
							"gestioneRevoca.numero_revoca AS numeroPropostaRevoca,\n" +
							"soggetto.id_soggetto AS idSoggetto, \n" +
							"soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto, \n" +
							"(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.id_persona_fisica) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.id_ente_giuridico END) END) AS idBeneficiario, \n" +
							"(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazioneBeneficiario, \n" +
							"domanda.id_domanda AS idDomanda, \n" +
							"bandoLineaIntervento.progr_bando_linea_intervento AS progrBandoLineaIntervento, \n" +
							"bandoLineaIntervento.nome_bando_linea AS nomeBandoLinea, \n" +
							"progetto.titolo_progetto AS titoloProgetto, \n" +
							"progetto.codice_visualizzato AS codiceVisualizzatoProgetto,\n" +
							"causaleBlocco.id_causale_blocco AS idCausaleBlocco, \n" +
							"causaleBlocco.desc_causale_blocco AS descCausaleBlocco, \n" +
							"gestioneRevoca.dt_gestione AS dataPropostaRevoca, \n" +
							"statoRevoca.id_stato_revoca AS idStatoRevoca, \n" +
							"statoRevoca.desc_stato_revoca AS descStatoRevoca, \n" +
							"gestioneRevoca.dt_stato_revoca AS dataStatoRevoca\n" +
							"FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
							"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = gestioneRevoca.id_progetto \n" +
							"JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
							"JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
							"JOIN (SELECT DISTINCT \n" +
							"\tsoggetto.id_soggetto,\n" +
							"\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
							"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
							"\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
							"\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
							"\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
							"\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
							"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
							"JOIN (SELECT DISTINCT \n" +
							"\tsoggetto.id_soggetto,\n" +
							"\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
							"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
							"\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
							"\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
							"\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica \n" +
							"\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
							"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
							"JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
							"JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
							"JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco \n" +
							"JOIN PBANDI_D_STATO_REVOCA statoRevoca ON statoRevoca.id_stato_revoca = gestioneRevoca.id_stato_revoca \n" +
							"WHERE gestioneRevoca.id_tipologia_revoca = 1 \n" +
							"AND gestioneRevoca.dt_fine_validita IS NULL \n" +
							"AND soggettoProgetto.id_tipo_anagrafica = 1 \n" +
							"AND soggettoProgetto.id_tipo_beneficiario <> 4 "
			);

			List<Object> args = new ArrayList<>();

			if (idSoggetto != null) {
				args.add(idSoggetto);
				query.append("AND soggetto.id_soggetto = ? \n");
			}
			if (idBando != null) {
				args.add(idBando);
				query.append("AND bandoLineaIntervento.progr_bando_linea_intervento = ? \n");
			}
			if (idProgetto != null) {
				args.add(idProgetto);
				query.append("AND progetto.id_progetto = ? \n");
			}
			if (idCausaleBlocco != null) {
				args.add(idCausaleBlocco);
				query.append("AND causaleBlocco.id_causale_blocco = ? \n");
			}
			if (idStatoRevoca != null) {
				args.add(idStatoRevoca);
				query.append("AND statoRevoca.id_stato_revoca = ? \n");
			}
			if (numeroPropostaRevoca != null) {
				args.add(numeroPropostaRevoca);
				query.append("AND gestioneRevoca.numero_revoca = ? \n");
			}
			if (dataPropostaRevocaFrom != null) {
				args.add(new java.sql.Date(dataPropostaRevocaFrom.getTime()));
				query.append("AND gestioneRevoca.dt_gestione >= ? \n");
			}
			if (dataPropostaRevocaTo != null) {
				args.add(new java.sql.Date(dataPropostaRevocaTo.getTime()));
				query.append("AND gestioneRevoca.dt_gestione <= ? ");
			}

			LOG.info(prf + " query: " + query + "\n" + prf + " args: " + args);
			lista = getJdbcTemplate().query(
					query.toString(),
					ps -> {
						for(int i = 0; i < args.size(); i++){
							ps.setObject(i+1, args.get(i));
						}
					},
					new PropostaRevocaVORowMapper()
			);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PropostaRevocaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read PropostaRevocaVO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return lista;
	}

	@Override
	public DataPropostaRevocaVO getInfoRevoche(Long idGestioneRevoca, Long idSoggetto) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query1 =
				"SELECT\n" +
						"\tptgr.id_gestione_revoca,\n" +
						"\tptgr.numero_revoca,\n" +
						"\tptgr.id_progetto,\n" +
						"\tprsp.id_soggetto,\n" +
						"\tptp.id_domanda,\n" +
						"\tptgr.dt_gestione,\n" +
						"\tptgr.dt_stato_revoca, \n" +
						"\tsoggetto.codice_fiscale_soggetto,\n" +
						"\t(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione_beneficiario, \n" +
						"\t(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.id_persona_fisica) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.id_ente_giuridico END) END) AS id_beneficiario, \n" +
						"\tprbli.nome_bando_linea,\n" +
						"\tptp.codice_visualizzato,\n" +
						"\tptp.titolo_progetto,\n" +
						"\tpdcb.desc_causale_blocco,\n" +
						"\tpdca.desc_categ_anagrafica,\n" +
						"\tpdsr.desc_stato_revoca\n" +
						"FROM PBANDI_T_GESTIONE_REVOCA ptgr\n" +
						"JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON ptgr.id_progetto =  prsp.id_progetto\n" +
						"JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = prsp.id_soggetto \n" +
						"JOIN (SELECT DISTINCT \n" +
						"\tsoggetto.id_soggetto,\n" +
						"\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
						"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
						"\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
						"\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
						"\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
						"\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
						"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
						"JOIN (SELECT DISTINCT \n" +
						"\tsoggetto.id_soggetto,\n" +
						"\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
						"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
						"\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
						"\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
						"\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica \n" +
						"\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
						"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
						"LEFT JOIN PBANDI_T_PROGETTO ptp ON ptgr.id_progetto = ptp.id_progetto \n" +
						"LEFT JOIN PBANDI_T_DOMANDA ptd ON ptp.id_domanda = ptd.id_domanda \n" +
						"LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento \n" +
						"LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON ptgr.id_causale_blocco = pdcb.id_causale_blocco \n" +
						"LEFT JOIN PBANDI_D_CATEG_ANAGRAFICA pdca ON ptgr.id_categ_anagrafica = pdca.id_categ_anagrafica \n" +
						"LEFT JOIN PBANDI_D_STATO_REVOCA pdsr ON ptgr.id_stato_revoca = pdsr.id_stato_revoca \n" +
						"WHERE ptgr.id_gestione_revoca = ? \n" +
						"AND prsp.id_soggetto = ? \n";
		DataPropostaRevocaVO data;

		try{
			LOG.info(prf + "\nQuery: \n\n" + query1 + "\n\n");
			data = getJdbcTemplate().queryForObject(
					query1,
					new Object[] {idGestioneRevoca, idSoggetto},
					new DataPropostaRevocaVORowMapper()
			);
		}catch (EmptyResultDataAccessException e){
			data = null;
		}
		LOG.info(prf + "END");

		return data;
	}

	@Override
	public NomeCognomeIstruttore getNomeCognomeIstruttore(Long idGestioneRevoca)throws ErroreGestitoException {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"SELECT *\r\n"
						+ "FROM (\r\n"
						+ "			SELECT ptpf.ID_SOGGETTO, ptpf.NOME, ptpf.COGNOME, ptpf.ID_PERSONA_FISICA  \r\n"
						+ "			FROM PBANDI_T_GESTIONE_REVOCA ptgr\r\n"
						+ "			LEFT JOIN PBANDI_T_UTENTE ptu   \r\n"
						+ "			ON ptgr.ID_UTENTE_INS = ptu.ID_UTENTE\r\n"
						+ "			LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n"
						+ "			ON ptu.ID_SOGGETTO = ptpf.ID_SOGGETTO \r\n"
						+ "			WHERE  ptgr.ID_GESTIONE_REVOCA IN (SELECT MIN(temp.ID_GESTIONE_REVOCA) \r\n"
						+ "											FROM PBANDI_T_GESTIONE_REVOCA temp \r\n"
						+ "											WHERE temp.ID_GESTIONE_REVOCA = ?)\r\n"
						+ "			ORDER BY ptpf.ID_PERSONA_FISICA DESC \r\n"
						+ "	)\r\n"
						+ "WHERE  rownum = 1";

		ArrayList <Object>  args = new ArrayList<>();
		args.add(idGestioneRevoca);

		List <NomeCognomeIstruttore> data;

		LOG.info(prf + "Query: \n\n" + query + "\n");
		data = getJdbcTemplate().query(
				query,
				ps -> {
					for(int i = 0; i < args.size(); i++){
						ps.setObject(i+1, args.get(i));
					}
				},
				new NomeCognomeIstruttoreRowMapper()
		);


		NomeCognomeIstruttore result;

		if(data == null || data.size() <= 0)
			result = null;
		else
			result = data.get(0);


		LOG.info(prf + "END");

		return result;


	}

	@Override
	public List<ImportiPropostaRevocaVO> getImportiRevoche(Long idGestioneRevoca, Long idUtente) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"SELECT DISTINCT\n" +
						"ptgr.id_gestione_revoca,\n" +
						"ptp.id_progetto,\n" +
						"ptd.id_domanda,\n" +
						"prbli.id_bando,\n" +
						"ptgr.id_stato_revoca,\n" +
						"ptgr.dt_stato_revoca,\n" +
						"pdma.id_modalita_agevolazione,\n" +
						"pdma.id_modalita_agevolazione_rif,\n" +
						"pdma.desc_modalita_agevolazione,\n" +
						"tica.importo_concesso,\n" +
						"tirc.importo_revocato,\n" +
						"impAmmesso.importo_ammesso_iniziale\n" +
						"FROM PBANDI_T_GESTIONE_REVOCA ptgr\n" +
						"JOIN PBANDI_T_PROGETTO ptp ON ptgr.id_progetto = ptp.id_progetto \n" +
						"JOIN PBANDI_T_DOMANDA ptd ON ptp.id_domanda = ptd.id_domanda\n" +
						"JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento \n" +
						"JOIN ( /* IMPORTO CONCESSO */\n" +
						"\tSELECT ptgr.ID_GESTIONE_REVOCA, prcema.QUOTA_IMPORTO_AGEVOLATO AS importo_concesso, pdma.ID_MODALITA_AGEVOLAZIONE\n" +
						"\tFROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema\n" +
						"\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = prcema.ID_MODALITA_AGEVOLAZIONE \n" +
						"\tJOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO \n" +
						"\tJOIN (\n" +
						"\t\tSELECT MIN(ptce.DT_INIZIO_VALIDITA) AS DT_INIZIO_VALIDITA, ptce.ID_DOMANDA \n" +
						"\t\tFROM PBANDI_T_CONTO_ECONOMICO ptce \n" +
						"\t\tGROUP BY ptce.ID_DOMANDA \n" +
						"\t) ptceOriginale ON ptceOriginale.ID_DOMANDA = ptce.ID_DOMANDA \n" +
						"\t\tAND ptceOriginale.DT_INIZIO_VALIDITA = PTCE.DT_INIZIO_VALIDITA \n" +
						"\tJOIN PBANDI_T_PROGETTO ptp ON ptp.ID_DOMANDA = ptce.ID_DOMANDA\n" +
						"\tJOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_PROGETTO = ptp.ID_PROGETTO \n" +
						") tica ON ptgr.id_gestione_revoca = tica.id_gestione_revoca\n" +
						"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON tica.ID_MODALITA_AGEVOLAZIONE = pdma.id_modalita_agevolazione\n" +
						"LEFT JOIN (   /* IMPORTO REVOCATO */\n" +
						"\tSELECT ptgr.ID_GESTIONE_REVOCA, sum(ptr.importo) AS importo_revocato, pdma.ID_MODALITA_AGEVOLAZIONE\n" +
						"\tFROM PBANDI_T_REVOCA ptr \n" +
						"\tJOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptr.ID_PROGETTO = ptgr.ID_PROGETTO \n" +
						"\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = ptr.ID_MODALITA_AGEVOLAZIONE \n" +
						"\tWHERE pdma.ID_MODALITA_AGEVOLAZIONE_RIF IN (1, 5, 10)\n" +
						"\tAND (\n" +
						"\t\t(ptr.ID_GESTIONE_REVOCA IS NULL AND (ptgr.DT_GESTIONE IS NULL OR ptr.DT_REVOCA+1 < ptgr.DT_GESTIONE)) OR \n" +
						"\t\t(ptr.ID_GESTIONE_REVOCA IS NOT NULL AND ptgr.ID_GESTIONE_REVOCA > ptr.ID_GESTIONE_REVOCA)\n" +
						"\t) GROUP BY ptgr.ID_GESTIONE_REVOCA, pdma.ID_MODALITA_AGEVOLAZIONE\n" +
						") tirc ON ptgr.id_gestione_revoca = tirc.id_gestione_revoca AND tirc.ID_MODALITA_AGEVOLAZIONE = pdma.id_modalita_agevolazione\n" +
						"LEFT JOIN ( /* IMPORTO AMMESSO INIZIALE */\n" +
						"\tSELECT ptce.ID_DOMANDA, prcema.ID_MODALITA_AGEVOLAZIONE, prcema.IMPORTO_AMMESSO_FINPIS AS importo_ammesso_iniziale\n" +
						"\tFROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema \n" +
						"\tJOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO \n" +
						"\tWHERE ptce.DT_FINE_VALIDITA IS NULL\n" +
						") impAmmesso ON impAmmesso.id_modalita_agevolazione = pdma.id_modalita_agevolazione\n" +
						"\tAND impAmmesso.id_domanda = ptd.id_domanda\n" +
						"WHERE ptgr.id_gestione_revoca = ?";

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		List <ImportiPropostaRevocaVO> data = getJdbcTemplate().query(
				query,
				ps -> ps.setObject(1, idGestioneRevoca),
				new ImportiPropostaRevocaVORowMapper()
		);

		for(ImportiPropostaRevocaVO importiPropostaRevocaVO : data){
			try {
				ImportiRevoche[] importiRevoche = amministrativoContabileService.callToImportiRevocheImporti(
						importiPropostaRevocaVO.getIdProgetto(),
						importiPropostaRevocaVO.getIdBando(),
						(importiPropostaRevocaVO.getIdStatoRevoca() == 1 ? new Date() : importiPropostaRevocaVO.getDtStatoRevoca()),
						importiPropostaRevocaVO.getIdModalitaAgevolazione(),
						importiPropostaRevocaVO.getIdModalitaAgevolazioneRif(),
						idGestioneRevoca,
						idUtente
				);

				for(ImportiRevoche importo : importiRevoche){
					switch (importo.getCausale()) {
						case "Erogazioni":
							if(importiPropostaRevocaVO.getImportoErogato() != null){
								importiPropostaRevocaVO.setImportoErogato(importiPropostaRevocaVO.getImportoErogato() + importo.getImporto());
							}else{
								importiPropostaRevocaVO.setImportoErogato(importo.getImporto());
							}
							break;
						case "Rimborsi":
							if(importiPropostaRevocaVO.getImportoRimborsato() != null){
								importiPropostaRevocaVO.setImportoRimborsato(importiPropostaRevocaVO.getImportoRimborsato() + importo.getImporto());
							}else{
								importiPropostaRevocaVO.setImportoRimborsato(importo.getImporto());
							}
							break;
						case "Recuperi":
							if(importiPropostaRevocaVO.getImportoRecuperato() != null){
								importiPropostaRevocaVO.setImportoRecuperato(importiPropostaRevocaVO.getImportoRecuperato() + importo.getImporto());
							}else{
								importiPropostaRevocaVO.setImportoRecuperato(importo.getImporto());
							}
							break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.info(prf+"Errore durante la chiamata ad amministrativo contabile!");
			} finally {
				importiPropostaRevocaVO.setImportoConcessoAlNettoRevocato();
				importiPropostaRevocaVO.setImportoErogatoAlNettoRecuperato();
				importiPropostaRevocaVO.setImportoErogatoAlNettoRecuperatoRimborsato();
			}
		}

		LOG.info(prf + "END");

		return data;
	}

	@Override
	public NotaRevocaVO getNotaRevoche(Long idGestioneRevoca) throws ErroreGestitoException {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"SELECT ptgr.NOTE \r\n"
						+ "	FROM PBANDI_T_GESTIONE_REVOCA ptgr\r\n"
						+ "	WHERE ptgr.ID_GESTIONE_REVOCA = ? ";


		ArrayList <Object>  args = new ArrayList<>();
		args.add(idGestioneRevoca);

		List <NotaRevocaVO> data;

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		data = getJdbcTemplate().query(
				query,
				ps -> {
					for(int i = 0; i < args.size(); i++){
						ps.setObject(i+1, args.get(i));
					}
				},
				new NotaRevocaVORowMapper()
		);


		LOG.info(prf + "END");

		if(data.size() > 0)
			return data.get(0);
		else
			return null;


	}

	@Override
	public void updateNotaRevoche(Long idGestioneRevoca, String nota, Long idUtente) throws ErroreGestitoException {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"UPDATE PBANDI_T_GESTIONE_REVOCA\r\n"
						+ "	SET note = ?,\r\n"
						+ "	ID_UTENTE_AGG  = ?,\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE\r\n"
						+ "	WHERE ID_GESTIONE_REVOCA = ? ";

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		getJdbcTemplate().update(
				query,
				nota, idUtente, idGestioneRevoca);

		LOG.info(prf + "END");
	}

	@Override
	public void disableCurrentGestioneRevoca(Long idGestioneRevoca, Long idUtente) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"UPDATE PBANDI_T_GESTIONE_REVOCA\r\n"
						+ "SET \r\n"
						+ "	DT_FINE_VALIDITA = SYSDATE,\r\n"
						+ "	ID_UTENTE_AGG  = ?,\r\n"
						+ "	DT_AGGIORNAMENTO = SYSDATE\r\n"
						+ "WHERE ID_GESTIONE_REVOCA = ?";

		ArrayList <Object>  args = new ArrayList<>();
		args.add(idUtente);
		args.add(idGestioneRevoca);

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		getJdbcTemplate().update(
				query,
				ps -> {
					for(int i = 0; i < args.size(); i++){
						ps.setObject(i+1, args.get(i));
					}
				}
		);

		LOG.info(prf + "END");
	}

	@Override
	public Long cloneGestioneRevoca(Long idGestioneRevoca) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String getIdGestioneRevoca ="select SEQ_PBANDI_T_GESTIONE_REVOCA.nextval from dual";
		LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
		Long newIdGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, Long.class);

		String query =
				"INSERT INTO PBANDI_T_GESTIONE_REVOCA (\n" +
						"ID_GESTIONE_REVOCA,\n" +
						"NUMERO_REVOCA,\n" +
						"ID_TIPOLOGIA_REVOCA,\n" +
						"ID_PROGETTO,\n" +
						"ID_CAUSALE_BLOCCO,\n" +
						"ID_CATEG_ANAGRAFICA,\n" +
						"DT_GESTIONE,\n" +
						"NUM_PROTOCOLLO,\n" +
						"FLAG_ORDINE_RECUPERO,\n" +
						"ID_MANCATO_RECUPERO,\n" +
						"ID_STATO_REVOCA,\n" +
						"DT_STATO_REVOCA,\n" +
						"DT_NOTIFICA,\n" +
						"GG_RISPOSTA,\n" +
						"FLAG_PROROGA,\n" +
						"IMP_DA_REVOCARE_CONTRIB,\n" +
						"IMP_DA_REVOCARE_FINANZ,\n" +
						"IMP_DA_REVOCARE_GARANZIA,\n" +
						"FLAG_DETERMINA,\n" +
						"ESTREMI,\n" +
						"DT_DETERMINA,\n" +
						"NOTE,\n" +
						"ID_ATTIVITA_REVOCA,\n" +
						"DT_ATTIVITA,\n" +
						"ID_MOTIVO_REVOCA,\n" +
						"FLAG_CONTRIB_REVOCA,\n" +
						"FLAG_CONTRIB_MINOR_SPESE,\n" +
						"FLAG_CONTRIB_DECURTAZ,\n" +
						"FLAG_FINANZ_REVOCA,\n" +
						"FLAG_FINANZ_MINOR_SPESE,\n" +
						"FLAG_FINANZ_DECURTAZ,\n" +
						"FLAG_GARANZIA_REVOCA,\n" +
						"FLAG_GARANZIA_MINOR_SPESE,\n" +
						"FLAG_GARANZIA_DECURTAZ,\n" +
						"IMP_CONTRIB_REVOCA_NO_RECU,\n" +
						"IMP_CONTRIB_REVOCA_RECU,\n" +
						"IMP_CONTRIB_INTERESSI,\n" +
						"IMP_FINANZ_REVOCA_NO_RECU,\n" +
						"IMP_FINANZ_REVOCA_RECU,\n" +
						"IMP_FINANZ_INTERESSI,\n" +
						"IMP_GARANZIA_REVOCA_NO_RECU,\n" +
						"IMP_GARANZIA_REVOCA_RECUPERO,\n" +
						"IMP_GARANZIA_INTERESSI,\n" +
						"COVAR,\n" +
						"DT_INIZIO_VALIDITA,\n" +
						"DT_FINE_VALIDITA,\n" +
						"ID_UTENTE_INS,\n" +
						"ID_UTENTE_AGG,\n" +
						"DT_INSERIMENTO,\n" +
						"DT_AGGIORNAMENTO,\n" +
						//campi aggiunti per invia incarico ad erogazione
						"IMP_IRES,\n"+
						"IMP_DA_EROGARE_CONTRIBUTO,\n"+
						"IMP_DA_EROGARE_FINANZIAMENTO,\n"+
						"ID_CAUSALE_EROG_CONTR,\n"+
						"ID_CAUSALE_EROG_FIN,\n"+
						"ID_DICHIARAZIONE_SPESA\n"+
						")\n" +
						"(SELECT\n" +
						"?,\n" +
						"NUMERO_REVOCA,\n" +
						"ID_TIPOLOGIA_REVOCA,\n" +
						"ID_PROGETTO,\n" +
						"ID_CAUSALE_BLOCCO,\n" +
						"ID_CATEG_ANAGRAFICA,\n" +
						"DT_GESTIONE,\n" +
						"NUM_PROTOCOLLO,\n" +
						"FLAG_ORDINE_RECUPERO,\n" +
						"ID_MANCATO_RECUPERO,\n" +
						"ID_STATO_REVOCA,\n" +
						"DT_STATO_REVOCA,\n" +
						"DT_NOTIFICA,\n" +
						"GG_RISPOSTA,\n" +
						"FLAG_PROROGA,\n" +
						"IMP_DA_REVOCARE_CONTRIB,\n" +
						"IMP_DA_REVOCARE_FINANZ,\n" +
						"IMP_DA_REVOCARE_GARANZIA,\n" +
						"FLAG_DETERMINA,\n" +
						"ESTREMI,\n" +
						"DT_DETERMINA,\n" +
						"NOTE,\n" +
						"ID_ATTIVITA_REVOCA,\n" +
						"DT_ATTIVITA,\n" +
						"ID_MOTIVO_REVOCA,\n" +
						"FLAG_CONTRIB_REVOCA,\n" +
						"FLAG_CONTRIB_MINOR_SPESE,\n" +
						"FLAG_CONTRIB_DECURTAZ,\n" +
						"FLAG_FINANZ_REVOCA,\n" +
						"FLAG_FINANZ_MINOR_SPESE,\n" +
						"FLAG_FINANZ_DECURTAZ,\n" +
						"FLAG_GARANZIA_REVOCA,\n" +
						"FLAG_GARANZIA_MINOR_SPESE,\n" +
						"FLAG_GARANZIA_DECURTAZ,\n" +
						"IMP_CONTRIB_REVOCA_NO_RECU,\n" +
						"IMP_CONTRIB_REVOCA_RECU,\n" +
						"IMP_CONTRIB_INTERESSI,\n" +
						"IMP_FINANZ_REVOCA_NO_RECU,\n" +
						"IMP_FINANZ_REVOCA_RECU,\n" +
						"IMP_FINANZ_INTERESSI,\n" +
						"IMP_GARANZIA_REVOCA_NO_RECU,\n" +
						"IMP_GARANZIA_REVOCA_RECUPERO,\n" +
						"IMP_GARANZIA_INTERESSI,\n" +
						"COVAR,\n" +
						"DT_INIZIO_VALIDITA,\n" +
						"DT_FINE_VALIDITA,\n" +
						"ID_UTENTE_INS,\n" +
						"ID_UTENTE_AGG,\n" +
						"DT_INSERIMENTO,\n" +
						"DT_AGGIORNAMENTO,\n" +
						//campi aggiunti per invia incarico ad erogazione
						"IMP_IRES,\n"+
						"IMP_DA_EROGARE_CONTRIBUTO,\n"+
						"IMP_DA_EROGARE_FINANZIAMENTO,\n"+
						"ID_CAUSALE_EROG_CONTR,\n"+
						"ID_CAUSALE_EROG_FIN,\n"+
						"ID_DICHIARAZIONE_SPESA\n"+
						"FROM PBANDI_T_GESTIONE_REVOCA\n" +
						"WHERE ID_GESTIONE_REVOCA = ?\n" +
						")";

		ArrayList <Object>  args = new ArrayList<>();
		args.add(newIdGestioneRevoca);
		args.add(idGestioneRevoca);

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		getJdbcTemplate().update(
				query,
				ps -> {
					for(int i = 0; i < args.size(); i++){
						ps.setObject(i+1, args.get(i));
					}
				}
		);


		LOG.info(prf + "END");
		return newIdGestioneRevoca;
	}

	@Override
	public void spostaAllegatoRevoca(Long idGestioneRevoca, Long newIdGestioneRevoca) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"UPDATE PBANDI_T_DOCUMENTO_INDEX SET ID_TARGET = ?\n" +
				"WHERE ID_DOCUMENTO_INDEX IN (\n" +
				"\tSELECT ptdi.ID_DOCUMENTO_INDEX FROM PBANDI_T_DOCUMENTO_INDEX ptdi \n" +
				"\tJOIN PBANDI_C_ENTITA entita ON entita.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
				"\tWHERE ptdi.ID_ENTITA = entita.ID_ENTITA \n" +
				"\tAND ptdi.id_target = ?\n" +
				")";

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		getJdbcTemplate().update(
				query,
				newIdGestioneRevoca, idGestioneRevoca
		);

		LOG.info(prf + "END");
	}

	@Override
	public void updateRevoca(Long idGestioneRevoca, String note, Long idTipologiaRevoca, Long idStatoRevoca, Long idUtente) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"UPDATE PBANDI_T_GESTIONE_REVOCA\n" +
						"SET \n" +
						"DT_INIZIO_VALIDITA = CURRENT_DATE, \n" +
						"ID_UTENTE_INS = ?, \n" +
						"DT_INSERIMENTO = CURRENT_DATE, \n" +
						"DT_STATO_REVOCA = CURRENT_DATE, \n" +
						"ID_TIPOLOGIA_REVOCA = ?, \n" +
						"ID_STATO_REVOCA = ?, \n" +
						"ID_ATTIVITA_REVOCA = NULL, \n" +
						"DT_ATTIVITA = NULL \n" +
						((note!=null)
							?	", NOTE = ? \n"
							:	"") +
						((idStatoRevoca==5)
							?	", NUM_PROTOCOLLO = NULL \n"
							:	"") +
						((idTipologiaRevoca==3 || idStatoRevoca == 5)
							?	", DT_GESTIONE = NULL \n" +
								", DT_NOTIFICA = NULL \n" +
								", GG_RISPOSTA = NULL \n"
							:	"") +
						"WHERE ID_GESTIONE_REVOCA = ? ";

		ArrayList <Object>  args = new ArrayList<>();
		args.add(idUtente);
		args.add(idTipologiaRevoca);
		args.add(idStatoRevoca);
		if(note!=null){
			args.add(note);
		}
		args.add(idGestioneRevoca);

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		getJdbcTemplate().update(
				query,
				ps -> {
					for(int i = 0; i < args.size(); i++){
						ps.setObject(i+1, args.get(i));
					}
				}
		);
		LOG.info(prf + "END");
	}

	@Override
	public Long creaPropostaRevoca(Long numeroRevoca, Long idProgetto, Long idCausaleBlocco, Long idAutoritaControllante, Date dataPropostaRevoca,Long idUtente) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"INSERT INTO PBANDI_T_GESTIONE_REVOCA \n" +
						"(ID_GESTIONE_REVOCA, NUMERO_REVOCA, ID_TIPOLOGIA_REVOCA, " +
						"ID_PROGETTO, ID_CAUSALE_BLOCCO," + ((idCausaleBlocco==23) ? " ID_CATEG_ANAGRAFICA, " : " ") +
						"ID_STATO_REVOCA, DT_STATO_REVOCA, DT_INIZIO_VALIDITA, DT_GESTIONE, " +
						"ID_UTENTE_INS, DT_INSERIMENTO) \n" +
						"VALUES \n" +
						"(?, ?, 1, ?, ?," + ((idCausaleBlocco==23) ? " ?, " : " ") + "1, ?, ?, ?, ?, ?)";


		String getIdGestioneRevoca ="select SEQ_PBANDI_T_GESTIONE_REVOCA.nextval from dual";
		LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
		Long idGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, Long.class);

		ArrayList <Object>  args = new ArrayList<>();
		args.add(idGestioneRevoca); //ID_GESTIONE_REVOCA
		args.add(numeroRevoca); //NUMERO_REVOCA
		//ID_TIPOLOGIA_REVOCA
		args.add(idProgetto); //ID_PROGETTO
		args.add(idCausaleBlocco); //ID_CAUSALE_BLOCCO
		if(idCausaleBlocco==23)
			args.add(idAutoritaControllante); //ID_CATEG_ANAGRAFICA
		//ID_STATO_REVOCA
		args.add(new java.sql.Date(dataPropostaRevoca.getTime())); //DATE
		args.add(new java.sql.Date(dataPropostaRevoca.getTime())); //DATE
		args.add(new java.sql.Date(dataPropostaRevoca.getTime())); //DATE
		args.add(idUtente);	//idUtente
		args.add(new java.sql.Date(dataPropostaRevoca.getTime())); //DATE

		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		getJdbcTemplate().update(
				query,
				ps -> {
					for(int i = 0; i < args.size(); i++){
						ps.setObject(i+1, args.get(i));
					}
				}
		);
		LOG.info(prf + "END");

		return idGestioneRevoca;
	}

	@Override
	public void verificaInserimentoPropostaRevoca(Date dataPropostaRevoca, Long idProgetto) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"SELECT pdma.ID_MODALITA_AGEVOLAZIONE, pdma.ID_MODALITA_AGEVOLAZIONE_RIF " +
						"FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema\n" +
						"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = prcema.ID_MODALITA_AGEVOLAZIONE \n" +
						"WHERE prcema.ID_CONTO_ECONOMICO IN (\n" +
						"SELECT ID_CONTO_ECONOMICO FROM PBANDI_T_CONTO_ECONOMICO ptce \n" +
						"WHERE ID_DOMANDA IN (\n" +
						"SELECT ID_DOMANDA FROM PBANDI_T_PROGETTO prpsf\n" +
						"WHERE ID_PROGETTO = ?)\n" +
						"AND DT_FINE_VALIDITA IS NULL)\n" +
						"AND pdma.ID_MODALITA_AGEVOLAZIONE_RIF IN (1, 5, 10)";
		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		List<ModalitaAgevolazioneVO> modalitaAgevolazione = getJdbcTemplate().query(
				query,
				ps -> ps.setLong(1, idProgetto),
				(rs, rowNum) -> {
					ModalitaAgevolazioneVO modalitaAgevolazioneVO = new ModalitaAgevolazioneVO();

					modalitaAgevolazioneVO.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
					modalitaAgevolazioneVO.setIdModalitaAgevolazioneRif(rs.getLong("ID_MODALITA_AGEVOLAZIONE_RIF"));

					return modalitaAgevolazioneVO;
				}
		);

		//Verifica 1, 2 e 3
		for(ModalitaAgevolazioneVO modalitaAgevolaz : modalitaAgevolazione){
			String getDtFrom;
			java.sql.Date dtFrom;
			if(modalitaAgevolaz.getIdModalitaAgevolazioneRif()==1){
				getDtFrom = "SELECT DT_CONTABILE FROM PBANDI_T_EROGAZIONE pte WHERE id_progetto = ? AND ID_MODALITA_AGEVOLAZIONE =? AND ID_CAUSALE_EROGAZIONE ='4'";
				try {
					LOG.info(prf + "\nQuery: \n\n" + getDtFrom + "\n\n");
					dtFrom = getJdbcTemplate().queryForObject(getDtFrom, new Object[]{idProgetto, modalitaAgevolaz.getIdModalitaAgevolazione()}, java.sql.Date.class);
				}catch (EmptyResultDataAccessException e){
					dtFrom = null;
				}
			}else{
				getDtFrom =
						"SELECT DISTINCT DT_DICHIARAZIONE\n" +
								"FROM PBANDI_T_DICHIARAZIONE_SPESA ptds \n" +
								"WHERE ID_PROGETTO = ? \n" +
								"AND ID_TIPO_DICHIARAZ_SPESA IN (\n" +
								"\tSELECT DISTINCT ID_TIPO_DICHIARAZ_SPESA \n" +
								"\tFROM PBANDI_T_DICHIARAZIONE_SPESA \n" +
								"\tWHERE ID_PROGETTO = ?\n" +
								"\tAND ID_TIPO_DICHIARAZ_SPESA = (\n" +
								"\t\tSELECT max(ID_TIPO_DICHIARAZ_SPESA) \n" +
								"\t\tFROM PBANDI_T_DICHIARAZIONE_SPESA \n" +
								"\t\tWHERE ID_PROGETTO = ?\n" +
								"\t)\n" +
								") \n" +
								"AND ID_TIPO_DICHIARAZ_SPESA IN ('2','3','4') \n" +
								"AND DT_DICHIARAZIONE IN (\n" +
								"\tSELECT DISTINCT DT_DICHIARAZIONE \n" +
								"\tFROM PBANDI_T_DICHIARAZIONE_SPESA pdtds \n" +
								"\tWHERE DT_DICHIARAZIONE = (\n" +
								"\t\tSELECT max(DT_DICHIARAZIONE) \n" +
								"\t\tFROM PBANDI_T_DICHIARAZIONE_SPESA \n" +
								"\t\tWHERE ID_PROGETTO = ?\n" +
								"\t)\n" +
								") \n";
				try{
					LOG.info(prf + "\nQuery: \n\n" + getDtFrom + "\n\n");
					dtFrom = getJdbcTemplate().queryForObject(getDtFrom, new Object[]{idProgetto, idProgetto, idProgetto, idProgetto}, java.sql.Date.class);
				}catch (EmptyResultDataAccessException e){
					dtFrom = null;
				}
			}

			String getPeriodoStabilita =
					"SELECT PERIODO_STABILITA FROM PBANDI_R_BANDO_MODALITA_AGEVOL prbma  WHERE ID_MODALITA_AGEVOLAZIONE = ? \n" +
							"AND ID_BANDO IN(SELECT ID_BANDO FROM PBANDI_R_BANDO_LINEA_INTERVENT prbli \n" +
							"WHERE PROGR_BANDO_LINEA_INTERVENTO IN(SELECT PROGR_BANDO_LINEA_INTERVENTO FROM PBANDI_T_DOMANDA ptd \n" +
							"WHERE ID_DOMANDA IN (SELECT ID_DOMANDA FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?))) \n";
			Integer periodoStabilita;
			try{
				LOG.info(prf + "\nQuery: \n\n" + getPeriodoStabilita + "\n\n");
				periodoStabilita = getJdbcTemplate().queryForObject(getPeriodoStabilita, new Object[]{modalitaAgevolaz.getIdModalitaAgevolazione(), idProgetto}, Integer.class);
			}catch (EmptyResultDataAccessException e) {
				periodoStabilita = null;
			}

			if(dtFrom != null && periodoStabilita != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dtFrom);
				cal.add(Calendar.YEAR, periodoStabilita);

				if(modalitaAgevolazione.size()==1 && dataPropostaRevoca.after(cal.getTime())){
					if(modalitaAgevolaz.getIdModalitaAgevolazioneRif()==1){
						throw new ErroreGestitoException("Non è possibile creare una nuova proposta di revoca per la modalità di agevolazione contributo perché sono trascorsi "+ periodoStabilita +" anni dalla data di erogazione del saldo");
					}else{
						throw new ErroreGestitoException("Non è possibile creare una nuova proposta di revoca per la modalità di agevolazione "+ (modalitaAgevolaz.getIdModalitaAgevolazioneRif()==5 ? "finanziamento" : "garanzia") +" perché sono trascorsi "+ periodoStabilita +" anni dalla data di rendicontazione finale");
					}
				}
			}
		}
		if(modalitaAgevolazione.size() == 0){
			throw new ErroreGestitoException("Non è possibile definire la tipologia di agevolazione");
		}
		//verifica 4
		boolean checkErr1 = true;
		boolean checkErr5 = true;
		Long modalitaAgevolazErr = null;
		for(ModalitaAgevolazioneVO modalitaAgevolaz : modalitaAgevolazione){
			String getImportoConcesso =
					"SELECT QUOTA_IMPORTO_AGEVOLATO \n" +
							"FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV a, PBANDI_T_CONTO_ECONOMICO b, PBANDI_T_PROGETTO c \n" +
							"WHERE a.ID_CONTO_ECONOMICO = b.ID_CONTO_ECONOMICO \n" +
							"AND b.ID_DOMANDA = c.ID_DOMANDA \n" +
							"AND c.ID_PROGETTO = ? \n" +
							"AND ID_MODALITA_AGEVOLAZIONE = ? \n" +
							"AND b.DT_FINE_VALIDITA IS NULL\n";
			BigDecimal importoConcesso;
			try {
				LOG.info(prf + "\nQuery: \n\n" + getImportoConcesso + "\n\n");
				importoConcesso = getJdbcTemplate().queryForObject(getImportoConcesso, new Object[]{idProgetto, modalitaAgevolaz.getIdModalitaAgevolazione()}, BigDecimal.class);
			}catch(EmptyResultDataAccessException e){
				importoConcesso = null;
			}
			String getImportoRevocato =
					"SELECT sum(importo) AS IMPORTO FROM PBANDI_T_REVOCA \n" +
							"WHERE ID_PROGETTO = ? \n" +
							"AND ID_MODALITA_AGEVOLAZIONE = ? \n";
			BigDecimal importoRevocato;
			try {
				LOG.info(prf + "\nQuery: \n\n" + getImportoRevocato + "\n\n");
				importoRevocato = getJdbcTemplate().queryForObject(getImportoRevocato, new Object[]{idProgetto, modalitaAgevolaz.getIdModalitaAgevolazione()}, BigDecimal.class);
			}catch(EmptyResultDataAccessException e){
				importoRevocato = null;
			}
			if(importoConcesso != null){
				if(importoRevocato == null || importoRevocato.compareTo(importoConcesso) < 0){
					checkErr5 = false;
					checkErr1 = false;
					break;
				}
				checkErr1 = importoRevocato.compareTo(importoConcesso) == 0;
				checkErr5 = importoRevocato.compareTo(importoConcesso) > 0;
			}
			if(checkErr5 || checkErr1){
				modalitaAgevolazErr = modalitaAgevolaz.getIdModalitaAgevolazione();
			}
		}
		if(checkErr5 || checkErr1){
			String descModAgev = getJdbcTemplate().queryForObject(
					"SELECT modalitaAgevolazione.desc_modalita_agevolazione\n" +
							"FROM PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione\n" +
							"WHERE modalitaAgevolazione.id_modalita_agevolazione = ?",
					String.class,
					modalitaAgevolazErr
			);

			throw new ErroreGestitoException((checkErr1)
				? "Non è possibile creare una nuova proposta di revoca perché la somma degli importi già revocati è pari all’importo concesso per la modalità di agevolazione " + descModAgev
				: "Non è possibile creare una nuova proposta di revoca perché la somma degli importi già revocati è superiore all’importo concesso per la modalità di agevolazione " + descModAgev
			);
		}

		LOG.info(prf + "END");
	}
}






