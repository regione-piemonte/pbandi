/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.ammvoservrest.dto.StatoDistinte;
import it.csi.pbandi.pbgestfinbo.dto.amministrativoContabile.MonitoringAmministrativoContabileDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.AmministrativoContabileDAO;
import it.csi.pbandi.pbgestfinbo.util.BeanRowMapper;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;


@Service
public class AmministrativoContabileDAOImpl extends JdbcDaoSupport implements AmministrativoContabileDAO {

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public AmministrativoContabileDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }


    // 7.1	Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile
    @Override
    public Long trackCallToAmministrativoContabilePre(
            Long idServizio, // 7 = AnagraficaFondo.Anagrafica, 8 = AnagraficaFondo.IbanFondo
            Long idUtente,
            String modalitaChiamata, // I = insert, U = update
            String parametriInput, // Concatenzaione chiave-valore
            String parametriOutput,
            Long idEntita, // Valorizzato a seconda del servizio chiamato, es: 5 se è stato chiamato il servizio AnagraficaFondo.Anagrafica, 128 se è stato chiamato il servizio AnagraficaFondo.IbanFondo
            Long idTarget // Valorizzato con l’identificativo relative all’ID_ENTITA, es: Coincide con l’ID_BANDO se è stato chiamato il servizio AnagraficaFondo.Anagrafica, Coincide con ID_ESTREMI_BANCARI se è stato chiamato il servizio AnagraficaFondo.IbanFondo

    ) throws Exception {


        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Long newId;
        
        String sql = "";

        try {


            String sqlSeq = "select SEQ_PBANDI_T_MON_SERV.nextval from dual";
            //logger.info("\n" + sqlSeq);
            newId = getJdbcTemplate().queryForObject(sqlSeq, Long.class);
            logger.info(prf + " Nuovo id PBANDI_T_MON_SERV = " + newId);
            
            sql = "INSERT INTO PBANDI_T_MON_SERV (\r\n"
                    + "	ID_MONIT, ID_SERVIZIO, ID_UTENTE, MODALITA_CHIAMATA, "
                    + "	DATETIME_INIZIO_CHIAMATA,\r\n"
                    + "	PARAMETRI_DI_INPUT,	PARAMETRI_DI_OUTPUT,\r\n"
                    + "	ID_ENTITA,ID_TARGET ) "
                    + "VALUES (\r\n"
                    + "	?, 	?, 	?, 	?, \r\n"
                    + "	SYSDATE, \r\n"
                    + "	?, 	?, \r\n"
                    + "	?,	? )";

            //logger.info("\n" + sql + "\n");

            Object[] args = new Object[]{
                    newId,
                    idServizio,
                    idUtente,
                    modalitaChiamata,
                    parametriInput,
                    parametriOutput,
                    idEntita,
                    idTarget
            };

            getJdbcTemplate().update(sql, args);

        } catch (Exception e) {
        	logger.error(prf + " idServizio: " + idServizio);
        	logger.error(prf + " idUtente: " + idUtente);
        	logger.error(prf + " modalitaChiamata: " + modalitaChiamata);
        	logger.error(prf + " parametriInput: " + parametriInput);
        	logger.error(prf + " parametriOutput: " + parametriOutput);
        	logger.error(prf + " idEntita: " + idEntita);
        	logger.error(prf + " idTarget: " + idTarget);
        	
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
            throw new Exception(msg, e);
        } finally {
            logger.info(prf + " END");
        }
        return newId;
    }

    @Override
    public void trackCallToAmministrativoContabilePost(Long idMonitAmmCont, String esito, String codErrore, String msgErr, String parametriOutput) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");
        
        String sql = "";

        try {
            // Le colonne MESSAGGIO_ERRORE e PARAMETRI_DI_OUTPUT supportano, momentaneamente, una lunghezza max di 4000 caratteri
        	if(parametriOutput != null && parametriOutput.length() >= 4000) {
        		parametriOutput = "Output troppo lungo.";
        	}
        	if(msgErr != null && msgErr.length() >= 4000) {
        		msgErr = "Messaggio troppo lungo.";
        	}
        	
            sql = "UPDATE PBANDI_T_MON_SERV \r\n"
                    + "SET \r\n"
                    + "	ESITO = :ESITO,\r\n"
                    + "	CODICE_ERRORE = :CODICE_ERRORE,\r\n"
                    + "	MESSAGGIO_ERRORE = :MESSAGGIO_ERRORE,\r\n"
                    + "	DATETIME_FINE_CHIAMATA = SYSDATE,\r\n"
                    + "	PARAMETRI_DI_OUTPUT = :PARAMETRI_DI_OUTPUT \r\n"
                    + "WHERE ID_MONIT = :ID_MONIT_AMMVO_CONT \r\n";

            //logger.info("\n" + sql + "\n");

            Object[] args = new Object[]{esito, codErrore, msgErr, parametriOutput, idMonitAmmCont};
            getJdbcTemplate().update(sql, args);

        } catch (Exception e) {
        	logger.error(prf + " idMonitAmmCont: " + idMonitAmmCont);
        	logger.error(prf + " esito: " + esito);
        	logger.error(prf + " codErrore: " + codErrore);
        	logger.error(prf + " msgErr: " + msgErr);
        	logger.error(prf + " parametriOutput: " + parametriOutput);
        	
            String msg = " Errore l'esecuzione della query";
            logger.error(prf + msg, e);
            throw new Exception(msg, e);
        } finally {
            logger.info(prf + " END");
        }
    }


    public MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile(Long idAmmCont) throws Exception {

        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        MonitoringAmministrativoContabileDTO result;

        try {


            String sql;
            sql = "SELECT * \r\n"
                    + "from PBANDI_T_MON_SERV\r\n"
                    + "WHERE ID_MONIT = ?";

            logger.info("\n" + sql + "\n");

            Object[] args = new Object[]{idAmmCont};

            result = (MonitoringAmministrativoContabileDTO) getJdbcTemplate().queryForObject(sql, args, new BeanRowMapper(MonitoringAmministrativoContabileDTO.class));


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
    public Long getIdEntitaGestioneRevoche() throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Long idEntitaGestioneRevoca;

        try {

            idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                    "SELECT entita.id_entita AS idEntita \n" +
                            "FROM PBANDI_C_ENTITA entita \n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                    Long.class
            );

        } catch (Exception e) {
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
            throw new Exception(msg, e);
        } finally {
            logger.info(prf + " END");
        }

        return idEntitaGestioneRevoca;
    }

    @Override
    public Long getIdEntitaDistinta() throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Long idEntitaDistinta;

        try {

            idEntitaDistinta = getJdbcTemplate().queryForObject(
                    "SELECT entita.id_entita AS idEntita \n" +
                            "FROM PBANDI_C_ENTITA entita \n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_DISTINTA'",
                    Long.class
            );

        } catch (Exception e) {
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
            throw new Exception(msg, e);
        } finally {
            logger.info(prf + " END");
        }

        return idEntitaDistinta;
    }

    @Override
    public Long getIdEntitaConto() throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Long idEntitaDistinta;

        try {

            idEntitaDistinta = getJdbcTemplate().queryForObject(
                    "SELECT entita.id_entita AS idEntita \n" +
                            "FROM PBANDI_C_ENTITA entita \n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_PROGETTO'",
                    Long.class
            );

        } catch (Exception e) {
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
            throw new Exception(msg, e);
        } finally {
            logger.info(prf + " END");
        }

        return idEntitaDistinta;
    }

	/*@Override
	public Long getIdEntitaRevoche() throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Long idEntitaRevoche;

        try {

            idEntitaRevoche = getJdbcTemplate().queryForObject(
                    "SELECT entita.id_entita AS idEntita \n" +
                            "FROM PBANDI_C_ENTITA entita \n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_REVOCA'",
                    Long.class
            );

        } catch (Exception e) {
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
            throw new Exception(msg, e);
        } finally {
            logger.info(prf + " END");
        }

        return idEntitaRevoche;
	}*/

    //Assegnazione valori

    @Override
    public String getIbanBeneficiario(int idProgetto) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        String iban;

        try {

            iban = getJdbcTemplate().queryForObject(
                    "SELECT pteb.IBAN\n" +
                            "FROM PBANDI_T_PROGETTO ptp \n" +
                            "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \n" +
                            "JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pteb.ID_ESTREMI_BANCARI = prsp.ID_ESTREMI_BANCARI \n" +
                            "WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                            "AND prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                            "AND ptp.ID_PROGETTO = ?\n" +
                            "AND pteb.DT_FINE_VALIDITA IS NULL",
                    String.class,
                    idProgetto
            );

        } catch (Exception e) {
            String msg = "Errore durante l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return iban;
    }

    @Override
    public String getIbanInterventoSostitutivo(int idDistintaDett) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        String iban = null;

        try {

            iban = getJdbcTemplate().queryForObject(
                    "SELECT pddis.IBAN \n" +
                            "FROM PBANDI_T_DISTINTA_DETT ptdd\n" +
                            "JOIN PBANDI_T_PROPOSTA_EROGAZIONE ptpe ON ptpe.ID_PROPOSTA = ptdd.ID_TARGET\n" +
                            "JOIN PBANDI_D_DEST_INTERV_SOSTITUT pddis ON pddis.ID_DESTINATARIO_INTERVENTO = ptpe.ID_DESTINATARIO_INTERVENTO \n" +
                            "WHERE ptdd.ID_DISTINTA_DETT = ?",
                    String.class,
                    idDistintaDett
            );

        } catch (Exception ignored) {
        } finally {
            logger.info(prf + " END");
        }

        return iban;
    }

    @Override
    public String getIbanFondo(int idBando, int idModalitaAgevolazione) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");
        
        //LOG.info(prf + " idBando: " + idBando);
        //LOG.info(prf + " idModalitaAgevolazione: " + idModalitaAgevolazione);

        String iban;

        try {

            iban = getJdbcTemplate().queryForObject(
                    "SELECT pteb.IBAN\n" +
                            "FROM PBANDI_T_ESTREMI_BANCARI pteb\n" +
                            "WHERE pteb.ID_ESTREMI_BANCARI IN (\n" +
                            "\tSELECT prbmaeb.ID_ESTREMI_BANCARI\n" +
                            "\tFROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb\n" +
                            "\tWHERE prbmaeb.ID_BANDO = ?\n" +
                            "\tAND prbmaeb.ID_MODALITA_AGEVOLAZIONE = ?\n" +
                            ") AND pteb.DT_FINE_VALIDITA IS NULL",
                    String.class,
                    idBando,
                    idModalitaAgevolazione
            );
            
            LOG.info(prf + " founded iban: " + iban + " (idBando: " + idBando + " - idAgev: " + idModalitaAgevolazione + ")");

        } catch (Exception e) {
            String msg = "Errore durante l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return iban;
    }

    @Override
    public Long getIdFondo(int idProgetto) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Long idFondo;
        try {

            idFondo = getJdbcTemplate().queryForObject(
                    "SELECT DISTINCT \n" +
                            "CASE WHEN (prbmaeb.codice_fondo_finpis IS NOT NULL) \n" +
                            "\tTHEN CAST(prbmaeb.codice_fondo_finpis AS int) \n" +
                            "\tELSE CASE WHEN (ptp.codice_fondo_finpis IS NOT NULL) \n" +
                            "\t\tTHEN CAST(ptp.codice_fondo_finpis AS int) \n" +
                            "\t\tELSE CAST(prbli.id_bando AS int) \n" +
                            "\tEND\n" +
                            "END AS id_fondo\n" +
                            "FROM PBANDI_T_PROGETTO ptp \n" +
                            "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n" +
                            "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
                            "JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.ID_BANDO = prbli.ID_BANDO \n" +
                            "WHERE ptp.ID_PROGETTO = ?",
                    Long.class,
                    idProgetto
            );


        } catch (Exception e) {
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
//			throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return idFondo;
    }

    @Override
    public Long getNdgBeneficiario(int idProgetto) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Long ndg;

        try {

            ndg = getJdbcTemplate().queryForObject(
                    "SELECT pts.NDG \n" +
                            "FROM PBANDI_R_SOGGETTO_PROGETTO prsp \n" +
                            "JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO \n" +
                            "WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                            "AND prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                            "AND prsp.ID_PROGETTO = ?",
                    Long.class,
                    idProgetto
            );

        } catch (Exception e) {
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return ndg;
    }

    @Override
    public String getCodiceDomanda(int idProgetto) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        String numeroDomanda = null;

        try {

            numeroDomanda = getJdbcTemplate().queryForObject(
                    "SELECT ptp.CODICE_VISUALIZZATO  \n" +
                            "FROM PBANDI_R_SOGGETTO_PROGETTO prsp \n" +
                            "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO  = prsp.ID_PROGETTO \n" +
                            "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n" +
                            "WHERE prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                            "AND prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                            "AND prsp.ID_PROGETTO = ?",
                    String.class,
                    idProgetto
            ).substring(4, 10).trim();

            int i;
            for (i = 0; i < numeroDomanda.length(); i++) {
                char p = numeroDomanda.charAt(i);
                if (p != '0') {
                    break;
                }
            }
            numeroDomanda = numeroDomanda.substring(i);

        } catch (Exception e) {
            String msg = "Errore durante l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return numeroDomanda;
    }

    @Override
    public Integer getIdTipoDistinta(String descTipoDistinta) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Integer idTipoDistinta = null;

        try {

            idTipoDistinta = getJdbcTemplate().queryForObject(
                    "SELECT ID_TIPO_DISTINTA_AMMVO  \n" +
                            "FROM PBANDI_D_TIPO_DISTINTA pdtd \n" +
                            "WHERE pdtd.DESC_TIPO_DISTINTA = ?",
                    Integer.class,
                    descTipoDistinta
            );

        } catch (Exception e) {
            String msg = "Errore durante l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return idTipoDistinta;
    }

    @Override
    public Double getImportoAmmessoTotale(int idProgetto) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Double importo = null;

        try {

            importo = getJdbcTemplate().queryForObject(
                    "SELECT SUM(prpsf.IMP_QUOTA_SOGG_FINANZIATORE) AS importoFondo\n" +
                            "FROM PBANDI_R_PROG_SOGG_FINANZIAT prpsf\n" +
                            "JOIN PBANDI_D_SOGGETTO_FINANZIATORE pdsf ON pdsf.ID_SOGGETTO_FINANZIATORE = prpsf.ID_SOGGETTO_FINANZIATORE\n" +
                            "WHERE prpsf.ID_PROGETTO = ?\n" +
                            "AND pdsf.FLAG_AGEVOLATO ='S'",
                    Double.class,
                    idProgetto
            );

        } catch (Exception e) {
            String msg = "Errore durante l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return importo;
    }

    @Override
    public Double getImportoAmmessoBanca(int idProgetto) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        Double importo = null;

        try {

            importo = getJdbcTemplate().queryForObject(
                    "SELECT ptce.IMPORTO_FINANZIAMENTO_BANCA AS importoBanca\n" +
                            "FROM PBANDI_T_PROGETTO ptp\n" +
                            "JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_DOMANDA = ptp.ID_DOMANDA\n" +
                            "JOIN PBANDI_D_STATO_CONTO_ECONOMICO pdsce ON pdsce.ID_STATO_CONTO_ECONOMICO = ptce.ID_STATO_CONTO_ECONOMICO\n" +
                            "WHERE ptp.ID_PROGETTO = ?\n" +
                            "AND ptce.DT_FINE_VALIDITA IS NULL\n" +
                            "AND pdsce.ID_TIPOLOGIA_CONTO_ECONOMICO = 2",
                    Double.class,
                    idProgetto
            );

        } catch (Exception e) {
            String msg = "Errore durante l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return importo;
    }

    @Override
    public String getCodiceProgetto(int idProgetto, int idModalitaAgevolazione) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        String codiceProgetto;

        try {
        	int idModAgevEff =0; 
        	
        	switch (idModalitaAgevolazione) {
			case 9:
			case 5:
				idModAgevEff = 5; 
				break;
			case 1:
			case 6:
			case 13:
				idModAgevEff = 1;
				break;
			case 10:
			case 4:
				idModAgevEff = 10;	// Perché prima era 4??	
				break;

			default:
				break;
			}

        	try {				
        		codiceProgetto = getJdbcTemplate().queryForObject(
        				         "SELECT  prcema.codice_progetto_agev \r\n"
        						+ "FROM PBANDI_T_PROGETTO ptp \r\n"
        						+ "LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_DOMANDA = ptp.ID_DOMANDA AND ptce.DT_FINE_VALIDITA IS NULL\r\n"
        						+ "LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO\r\n"
        						+ "LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE  = prcema.ID_MODALITA_AGEVOLAZIONE \r\n"
        						+ "WHERE ptp.id_progetto = ?\r\n"
        						+ "AND pdma.ID_MODALITA_AGEVOLAZIONE_RIF =? ",
        						String.class,
        						idProgetto,
        						idModAgevEff
        				);
        		
			} catch (Exception e) {
				LOG.warn(prf + "codice progetto AGEV non trovato: "+ e);
				codiceProgetto = null; 
			}
        	LOG.info(prf + "codiceProgetto = "+ codiceProgetto);
        	
        	// PK : il "-" e' stato introdotto per Finpis in quanto 145 ptogetti importati presentano sul conto economico piu' agevolazioni della stessa tipologia
        	if(codiceProgetto==null || "-".equals(codiceProgetto)) { 
        		codiceProgetto = getJdbcTemplate().queryForObject(
				         "SELECT CODICE_VISUALIZZATO \r\n"
				         + "FROM pbandi_t_progetto \r\n"
				         + "WHERE ID_PROGETTO = ? ",
						String.class,
						idProgetto
				);
        	}

        } catch (Exception e) {
            String msg = "Errore durante l'esecuzione della query";
            logger.error(prf + msg, e);
            //throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return codiceProgetto;
    }




}
