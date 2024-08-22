/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.dto.IbanBeneficiario;
import it.csi.pbandi.pbservwelfare.dto.RequestRicezioneIban;
import it.csi.pbandi.pbservwelfare.dto.TemplateNotifica;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneBeneficiarioDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class GestioneBeneficiarioDAOImpl extends BaseDAO implements GestioneBeneficiarioDAO {

    private final Logger LOG;

    public GestioneBeneficiarioDAOImpl() {
        LOG = Logger.getLogger(Constants.COMPONENT_NAME);
    }

    private final static int ID_UTENTE = -20;

    @Override
    public IbanBeneficiario getInfoIbanBeneficiario(String numeroDomanda) {
        String prf = "[GestioneBeneficiarioDaoImpl::getInfoIbanBeneficiario]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", numeroDomanda=" + numeroDomanda);

        IbanBeneficiario ibanBeneficiario = null;

        try {
            String sql = "SELECT \n" +
                    "ptd.ID_DOMANDA AS ID_DOMANDA,\n" +
                    "ptp.ID_PROGETTO AS ID_PROGETTO,\n" +
                    "prsp.ID_SOGGETTO AS ID_SOGGETTO_BENEFICIARIO,\n" +
                    "prsp.PROGR_SOGGETTO_PROGETTO AS PROGR_SOGGETTO_PROGETTO,\n" +
                    "prsp.ID_PERSONA_FISICA AS ID_PERSONA_FISICA,\n" +
                    "prsp.ID_ESTREMI_BANCARI AS ID_ESTREMI_BANCARI,\n" +
                    "ptpf.COGNOME || ' ' || ptpf.NOME AS DENOMINAZIONE_BENEFICIARIO,\n" +
                    "pts.CODICE_FISCALE_SOGGETTO AS CF_SOGGETTO\n" +
                    "FROM PBANDI_T_DOMANDA ptd \n" +
                    "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \n" +
                    "JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA \n" +
                    "JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO \n" +
                    "WHERE prsp.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                    "AND ptd.NUMERO_DOMANDA = :numeroDomanda";

            LOG.debug(sql);

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

            ibanBeneficiario = this.queryForObject(sql, param, IbanBeneficiario.class, rs -> {
                IbanBeneficiario dto = null;

                if(rs.next()) {
                    dto = new IbanBeneficiario();
                    dto.setIdDomanda(rs.getInt("ID_DOMANDA"));
                    dto.setIdProgetto(rs.getInt("ID_PROGETTO"));
                    dto.setIdSoggettoBeneficiario(rs.getInt("ID_SOGGETTO_BENEFICIARIO"));
                    dto.setProgrSoggettoProgetto(rs.getInt("PROGR_SOGGETTO_PROGETTO"));
                    dto.setIdPersonaFisica(rs.getInt("ID_PERSONA_FISICA"));
                    dto.setIdEstremiBancari(rs.getInt("ID_ESTREMI_BANCARI"));
                    if (rs.wasNull()) {
                        dto.setIdEstremiBancari(null);
                    }
                    dto.setDenominazioneBeneficiario(rs.getString("DENOMINAZIONE_BENEFICIARIO"));
                    dto.setCodiceFiscaleBeneficiario(rs.getString("CF_SOGGETTO"));
                }

                return dto;
            });

        } catch (IncorrectResultSizeDataAccessException ignored) {
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to getInfoIbanBeneficiario", e);
            throw new ErroreGestitoException("DaoException while trying to getInfoIbanBeneficiario", e);
        } finally {
            LOG.info(prf + " END");
        }

        return ibanBeneficiario;
    }

    @Override
	public void censimentoNotificaProcesso(IbanBeneficiario infoIbanBeneficiario, RequestRicezioneIban request) {
        String prf = "[GestioneBeneficiarioDaoImpl::censimentoNotificaProcesso]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", InfoIbanBeneficiario=" + infoIbanBeneficiario.toString());

        try {
            // Recupero informazioni per la notifica
            String sql =
                    "SELECT ID_TEMPLATE_NOTIFICA, COMP_MESSAGE\n" +
                    "FROM PBANDI_D_TEMPLATE_NOTIFICA pdtn \n" +
                    "WHERE pdtn.DESCR_BREVE_TEMPLATE_NOTIFICA = :descBreveTemplateNotifica";
            LOG.debug(sql);
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue(
                    "descBreveTemplateNotifica",
                    (CommonUtil.isEmpty(request.getCodiceStruttura())) ?
                        "NotificaCambioIBAN"
                    :
                        "NotificaCambioIBANRES",
                    Types.VARCHAR
            );

            TemplateNotifica templateNotifica = this.queryForObject(sql, param, TemplateNotifica.class, rs -> {
                TemplateNotifica dto = null;

                if(rs.next()) {
                    dto = new TemplateNotifica();
                    dto.setIdTemplateNotifica(rs.getInt("ID_TEMPLATE_NOTIFICA"));
                    dto.setCompMessage(rs.getString("COMP_MESSAGE"));
                    String indicazioneIntestatario = "";
                    if(!CommonUtil.isEmpty(request.getIntestatario())) {
                    	indicazioneIntestatario = " Il nuovo intestatario è il seguente: " + request.getIntestatario();
                    }
                    
					dto.setElaboratedCompMessage(dto.getCompMessage()
							.replace("${cod_fiscale}", infoIbanBeneficiario.getCodiceFiscaleBeneficiario())
							.replace("${denominazione}", infoIbanBeneficiario.getDenominazioneBeneficiario())
							.replace("${codstruttura}",
									!CommonUtil.isEmpty(request.getCodiceStruttura()) ? request.getCodiceStruttura()
											: "")
							.replace("${denstruttura}",
									!CommonUtil.isEmpty(request.getDenominazioneStruttura())
											? request.getDenominazioneStruttura()
											: "")
							.replace("${iban}", request.getIban()) + indicazioneIntestatario
                    );
                }

                return dto;
            });

            LOG.info(prf + " Id template notifica = " + templateNotifica.getIdTemplateNotifica());
            LOG.info(prf + " Messaggio generato = " + templateNotifica.getElaboratedCompMessage());

            // Aggiornamento o inserimento iban nella PBANDI_T_FORNITORE_STRUTTURA
			if (!CommonUtil.isEmpty(request.getCodiceStruttura())) {
            	String queryStruttura =
                        "SELECT COD_STRUTTURA \n" + 
                        "FROM PBANDI_T_FORNITORE_STRUTTURA ptfs \n" + 
                        "WHERE COD_STRUTTURA = :codStruttura \n" + 
                        "  AND NUMERO_DOMANDA = :numeroDomanda";
				LOG.info(queryStruttura);
				MapSqlParameterSource paramStruttura = new MapSqlParameterSource();
				paramStruttura.addValue("codStruttura", request.getCodiceStruttura(), Types.VARCHAR);
				paramStruttura.addValue("numeroDomanda", request.getNumeroDomanda(), Types.VARCHAR);

				String codStruttura = this.namedParameterJdbcTemplate.query(queryStruttura, paramStruttura, rs -> {
					String codice = "";

					while (rs.next()) {
						codice = rs.getString("COD_STRUTTURA");
					}

					return codice;
				});
                
				if (!CommonUtil.isEmpty(codStruttura)) {
	            	String updateQuery = "UPDATE PBANDI_T_FORNITORE_STRUTTURA \n" + 
	            			"SET IBAN = :iban, \n" +
	            			"DT_AGGIORNAMENTO = SYSDATE \n" + 
	            			"WHERE COD_STRUTTURA = :codiceStruttura";
					LOG.info(updateQuery);
					MapSqlParameterSource paramUpdate = new MapSqlParameterSource();
					paramUpdate.addValue("iban", request.getIban(), Types.VARCHAR);
					paramUpdate.addValue("codiceStruttura", request.getCodiceStruttura(), Types.VARCHAR);
					this.update(updateQuery, paramUpdate);
                } else {
                	String insertQuery = "INSERT INTO PBANDI_T_FORNITORE_STRUTTURA(NUMERO_DOMANDA, COD_STRUTTURA, IBAN, INTESTATARIO, DT_INSERIMENTO) \n" + 
                			"VALUES(:numeroDomanda, \n" + 
                			"       :codStruttura, \n" + 
                			"       :iban, \n" + 
                			"       :intestatario, \n" +
                			"		SYSDATE)";
					LOG.info(insertQuery);
					MapSqlParameterSource paramInsert = new MapSqlParameterSource();
					paramInsert.addValue("numeroDomanda", request.getNumeroDomanda());
					paramInsert.addValue("codStruttura", request.getCodiceStruttura());
					paramInsert.addValue("iban", request.getIban());
					paramInsert.addValue("intestatario", request.getIntestatario());
					this.update(insertQuery, paramInsert);
                }
            }

            //Genero il progressivo per il record sulla PBANDI_T_NOTIFICA_PROCESSO
            sql = "SELECT SEQ_PBANDI_T_NOTIFICA_PROCESSO.nextval FROM dual";
            param = new MapSqlParameterSource();
            Integer idNotifica = this.queryForInteger(sql, param);

            //Inserisco il record sulla PBANDI_T_NOTIFICA_PROCESSO
            sql = "INSERT INTO PBANDI_T_NOTIFICA_PROCESSO ptnp \n" +
                    "(ID_NOTIFICA, ID_PROGETTO, ID_RUOLO_DI_PROCESSO_DEST, SUBJECT_NOTIFICA, STATO_NOTIFICA, ID_UTENTE_MITT, DT_NOTIFICA, ID_TEMPLATE_NOTIFICA, MESSAGE_NOTIFICA, MESSAGE_NOTIFICA2) \n" +
                    "VALUES (:idNotifica, :idProgetto, :idRuoloDiProcessoDest, :subjectNotifica, :statoNotifica, :idUtente, CURRENT_DATE, :idTemplateNotifica, :messageNotifica, :messageNotifica2)";
            param = new MapSqlParameterSource();
            param.addValue("idNotifica", idNotifica, Types.INTEGER);
            param.addValue("idProgetto", infoIbanBeneficiario.getIdProgetto(), Types.INTEGER);
            param.addValue("idRuoloDiProcessoDest", 1, Types.INTEGER);
            param.addValue("subjectNotifica", "Notifica Cambio Iban del soggetto beneficiario", Types.VARCHAR);
            param.addValue("statoNotifica", "I", Types.VARCHAR);
            param.addValue("idUtente", ID_UTENTE, Types.INTEGER);
            param.addValue("idTemplateNotifica", templateNotifica.getIdTemplateNotifica(), Types.INTEGER);
            param.addValue("messageNotifica", templateNotifica.getElaboratedCompMessage(), Types.VARCHAR);
            param.addValue("messageNotifica2", templateNotifica.getElaboratedCompMessage(), Types.VARCHAR);

            if(this.update(sql, param) > 0){
                LOG.info("Nuovo record sulla PBANDI_T_NOTIFICA_PROCESSO con ID_NOTIFICA = " + idNotifica);
            }
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to getInfoIbanBeneficiario", e);
            throw new ErroreGestitoException("DaoException while trying to getInfoIbanBeneficiario", e);
        } finally {
            LOG.info(prf + " END");
        }
    }

    @Override
    public void updateEstremiBancari(IbanBeneficiario infoIbanBeneficiario, String iban) {
        String prf = "[GestioneBeneficiarioDaoImpl::updateEstremiBancari]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", infoIbanBeneficiario=" + infoIbanBeneficiario + ", iban=" + iban);

        try {
            String sql;
            MapSqlParameterSource param;

            //PBANDI_T_ESTREMI_BANCARI
            final String CIN = iban.substring(4, 5);
            final String ABI = iban.substring(5, 10);
            final String CAB = iban.substring(10, 15);
            final String numeroConto = iban.substring(15, 27);

            sql = "SELECT pdb.ID_BANCA FROM PBANDI_D_BANCA pdb WHERE pdb.COD_BANCA = :codBanca";
            param = new MapSqlParameterSource();
            param.addValue("codBanca", ABI, Types.INTEGER);
            final Integer idBanca = this.queryForInteger(sql, param);
            LOG.info(prf + " idBanca = " + idBanca);

            sql = "SELECT pta.ID_AGENZIA FROM PBANDI_T_AGENZIA pta WHERE pta.ID_BANCA = :idBanca";
            param = new MapSqlParameterSource();
            param.addValue("idBanca", idBanca, Types.INTEGER);
            final Integer idAgenzia = this.queryForInteger(sql, param);
            LOG.info(prf + " idAgenzia = " + idAgenzia);

            if(infoIbanBeneficiario.getIdEstremiBancari() == null){
                //Genero nuovo idEstremiBancari
                sql = "SELECT SEQ_PBANDI_T_ESTREMI_BANCA.nextval FROM dual";
                param = new MapSqlParameterSource();
                infoIbanBeneficiario.setIdEstremiBancari(this.queryForInteger(sql, param));

                //Genero nuovo record su tabella PBANDI_T_ESTREMI_BANCARI
                sql = "INSERT INTO PBANDI_T_ESTREMI_BANCARI \n" +
                        "(ID_ESTREMI_BANCARI, NUMERO_CONTO, CIN, ABI, CAB, IBAN, ID_UTENTE_INS, ID_AGENZIA, ID_BANCA, DT_INIZIO_VALIDITA)\n" +
                        "VALUES (:idEstremiBancari, :numeroConto, :CIN, :ABI, :CAB, :IBAN, :idUtente, :idAgenzia, :idBanca, CURRENT_DATE)";
                param = new MapSqlParameterSource();
                param.addValue("idEstremiBancari", infoIbanBeneficiario.getIdEstremiBancari(), Types.INTEGER);
                param.addValue("numeroConto", numeroConto, Types.VARCHAR);
                param.addValue("CIN", CIN, Types.VARCHAR);
                param.addValue("ABI", ABI, Types.VARCHAR);
                param.addValue("CAB", CAB, Types.VARCHAR);
                param.addValue("IBAN", iban, Types.VARCHAR);
                param.addValue("idUtente", ID_UTENTE, Types.INTEGER);
                param.addValue("idAgenzia", idAgenzia, Types.INTEGER);
                param.addValue("idBanca", idBanca, Types.INTEGER);

                if(this.update(sql, param) > 0) {
                    LOG.info(prf + " Nuovo record sulla PBANDI_T_ESTREMI_BANCARI con ID_ESTREMI_BANCARI = " + infoIbanBeneficiario.getIdEstremiBancari());
                }

                //Assegno gli estremi bancari al beneficiario
                sql = "UPDATE PBANDI_R_SOGGETTO_PROGETTO SET \n" +
                        "ID_ESTREMI_BANCARI = :idEstremiBancari,\n" +
                        "ID_UTENTE_AGG = :idUtente,\n" +
                        "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                        "WHERE PROGR_SOGGETTO_PROGETTO = :progrSoggettoProgetto";
                param = new MapSqlParameterSource();
                param.addValue("idEstremiBancari", infoIbanBeneficiario.getIdEstremiBancari(), Types.INTEGER);
                param.addValue("idUtente", ID_UTENTE, Types.INTEGER);
                param.addValue("progrSoggettoProgetto", infoIbanBeneficiario.getProgrSoggettoProgetto(), Types.INTEGER);

                if(this.update(sql, param) > 0) {
                    LOG.info(prf + "Su PBANDI_R_SOGGETTO_PROGETTO con PROGR_SOGGETTO_PROGETTO = " + infoIbanBeneficiario.getProgrSoggettoProgetto() + " impostato ID_ESTREMI_BANCARI = " + infoIbanBeneficiario.getIdEstremiBancari());
                }
            }else{
                //Aggiorno estremi bancari
                sql = "UPDATE PBANDI_T_ESTREMI_BANCARI SET \n" +
                        "NUMERO_CONTO = :numeroConto,\n" +
                        "CIN = :CIN,\n" +
                        "ABI = :ABI,\n" +
                        "CAB = :CAB,\n" +
                        "IBAN = :IBAN,\n" +
                        "ID_UTENTE_INS = :idUtente,\n" +
                        "ID_AGENZIA = :idAgenzia,\n" +
                        "ID_BANCA = :idBanca,\n" +
                        "DT_INIZIO_VALIDITA = CURRENT_DATE \n" +
                        "WHERE ID_ESTREMI_BANCARI = :idEstremiBancari";
                param = new MapSqlParameterSource();
                param.addValue("numeroConto", numeroConto, Types.VARCHAR);
                param.addValue("CIN", CIN, Types.VARCHAR);
                param.addValue("ABI", ABI, Types.VARCHAR);
                param.addValue("CAB", CAB, Types.VARCHAR);
                param.addValue("IBAN", iban, Types.VARCHAR);
                param.addValue("idUtente", ID_UTENTE, Types.INTEGER);
                param.addValue("idAgenzia", idAgenzia, Types.INTEGER);
                param.addValue("idBanca", idBanca, Types.INTEGER);
                param.addValue("idEstremiBancari", infoIbanBeneficiario.getIdEstremiBancari(), Types.INTEGER);

                if(this.update(sql, param) > 0){
                    LOG.info(prf + " Aggiornato record sulla PBANDI_T_ESTREMI_BANCARI con ID_ESTREMI_BANCARI = " + infoIbanBeneficiario.getIdEstremiBancari());
                }
            }
        } catch (IncorrectResultSizeDataAccessException ignored) {
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to updateEstremiBancari", e);
            throw new ErroreGestitoException("DaoException while trying to updateEstremiBancari", e);
        } finally {
            LOG.info(prf + " END");
        }
    }

}
