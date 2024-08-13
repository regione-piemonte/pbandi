/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.csi.pbandi.pbweb.dto.*;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.DirezioniRowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.SettoriRowMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.DatiColonnaQteSalDTO;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;
import it.csi.pbandi.pbweb.dto.ElencoDocumentiSpesaItem;
import it.csi.pbandi.pbweb.dto.FiltroRicercaDocumentiSpesa;
import it.csi.pbandi.pbweb.dto.SalCorrenteDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.EsitoRicercaDocumentiDiSpesa;
import it.csi.pbandi.pbweb.dto.rendicontazione.InizializzaRendicontazioneDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.DocumentoDiSpesaDAO;
import it.csi.pbandi.pbweb.integration.dao.RendicontazioneDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.FileSqlUtil;

@Service
public class RendicontazioneDAOImpl extends JdbcDaoSupport implements RendicontazioneDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public RendicontazioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Autowired
	protected ProfilazioneDAO profilazioneDao;

	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;

	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Autowired
	private DocumentoDiSpesaDAO documentoDiSpesaDAO;

	//

	@Override
	// Restituisce i dati con cui popolare Rendicontazione all'apertura:
	public InizializzaRendicontazioneDTO inizializzaRendicontazione(long idProgetto, long idBandoLinea, long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idSoggettoBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[RendicontazioneDAOImpl::inizializzaRendicontazione] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idBandoLinea = " + idBandoLinea
				+ "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idSoggettoBeneficiario = "
				+ idSoggettoBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == 0) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (idSoggetto == 0) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggettoBeneficiario == null) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}

		InizializzaRendicontazioneDTO result = new InizializzaRendicontazioneDTO();
		try {

			// La compo del task la visualizzo solo se e' abilitata la regola BR16.
			Boolean taskVisibile = profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride,
					idProgetto, RegoleConstants.BR16_GESTIONE_CAMPO_TASK);
			result.setTaskVisibile(taskVisibile);

			// Gli allegati ai pagamenti sono ammessi solo se e' abilitata la regola BR51.
			Boolean allegatoDocumentoDiSpesa = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente,
					idIride, idBandoLinea, RegoleConstants.BR51_UPLOAD_ALLEGATI_SPESA);
			result.setAllegatiAmmessiDocumentoSpesa(allegatoDocumentoDiSpesa);
			// Gli allegati alle quietanze sono ammessi solo se e' abilitata la regola BR52.
			Boolean allegatoQuietanza = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
					idBandoLinea, RegoleConstants.BR52_UPLOAD_ALLEGATI_QUIETANZA);
			result.setAllegatiAmmessiQuietanze(allegatoQuietanza);
			// Permette di caricare degli allegati generici (facoltativi) alla dichiarazione
			// di spesa regola BR53
			Boolean allegatoGenerico = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
					idBandoLinea, RegoleConstants.BR53_UPLOAD_ALLEGATI_GENERICI);
			result.setAllegatiAmmessiGenerici(allegatoGenerico);

			// Gli allegati ai pagamenti\quietanze sono ammessi solo se e' abilitata la
			// regola BR42.
			Boolean allegatiAmmessi = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
					idBandoLinea, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
			result.setAllegatiAmmessi(allegatiAmmessi);

			// Le causali nei pagamenti\quietanze sono ammesse solo se e' abilitata la
			// regola BR43.
			Boolean causalePagamentoVisible = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente,
					idIride, idBandoLinea, RegoleConstants.BR43_CAUSALE_PAGAMENTO);
			result.setCausalePagamentoVisible(causalePagamentoVisible);

			boolean utenteAbilitatoAdAssociareVociDiSpesa = profilazioneDao.hasPermesso(idSoggetto, idUtente,
					codiceRuolo, UseCaseConstants.UC_OPEREN017);
			result.setUtenteAbilitatoAdAssociareVociDiSpesa(utenteAbilitatoAdAssociareVociDiSpesa);

			boolean utenteAbilitatoAdAssociarePagamenti = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN033);
			result.setUtenteAbilitatoAdAssociarePagamenti(utenteAbilitatoAdAssociarePagamenti);

			DatiProgettoInizializzazioneDTO datiProgetto = completaDatiProgetto(idProgetto);
			LOG.info("\n\nIdProcesso = " + datiProgetto.getIdProcesso());
			if (datiProgetto != null) {
				result.setCodiceVisualizzatoProgetto(datiProgetto.getCodiceVisualizzato());
				result.setIdProcessoBandoLinea(datiProgetto.getIdProcesso());
				if (datiProgetto.getIdTipoOperazione() != null)
					result.setIdTipoOperazioneProgetto(datiProgetto.getIdTipoOperazione().longValue());
			}

			boolean operen012 = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
					UseCaseConstants.UC_OPEREN012);

			LOG.info(prf + result.toString());

			result.setSolaVisualizzazione(isInSolaVisualizzazione(idProgetto));

			result.setIsBR58(profilazioneDao.isRegolaApplicabileForBandoLinea(idBandoLinea, "BR58", idUtente, idIride));
			if (result.getIsBR58() != null && result.getIsBR58().equals(Boolean.TRUE)) {
				FiltroRicercaDocumentiSpesa filtro = new FiltroRicercaDocumentiSpesa();
				filtro.setPartner(Constants.VALUE_RADIO_RENDICONTAZIONE_CAPOFILA);
				filtro.setIdStato(0L);
				filtro.setDocumentiDiSpesa(Constants.VALUE_RADIO_DOCUMENTI_DI_SPESA_GESTITI);
				EsitoRicercaDocumentiDiSpesa esito = documentoDiSpesaDAO.ricercaDocumentiDiSpesa(idProgetto,
						result.getCodiceVisualizzatoProgetto(), idSoggettoBeneficiario, codiceRuolo, filtro, idUtente,
						idIride, idSoggetto);
				if (esito.getEsito() != null && esito.getEsito().equals(Boolean.TRUE) && esito.getDocumenti() != null) {
					for (ElencoDocumentiSpesaItem doc : esito.getDocumenti()) {
						if (doc.getStato().equals(Constants.DESCRIZIONE_STATO_DOCUMENTO_DICHIARABILE)) {
							result.setHasDocumentoDichiarabile(Boolean.TRUE);
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la inizializzazione di Rendicontazione: ", e);
			throw new DaoException("Errore durante la inizializzazione di Rendicontazione.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	private DatiProgettoInizializzazioneDTO completaDatiProgetto(Long idPrj) {
		String prf = "[RendicontazioneDAOImpl::completaDatiProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idPrj=" + idPrj);

		DatiProgettoInizializzazioneDTO dto = null;
		try {
			if (idPrj != null && idPrj.intValue() != 0) {
				String sql = fileSqlUtil.getQuery("ProgettoBandoLineaVO.sql");
				LOG.info(prf + "\n" + sql + " con id_progetto = " + idPrj);
				ProgettoBandoLineaVO progettoBandoLineaVO = (ProgettoBandoLineaVO) getJdbcTemplate().queryForObject(
						sql.toString(), new Object[] { idPrj }, new BeanRowMapper(ProgettoBandoLineaVO.class));
				if (progettoBandoLineaVO != null) {
					dto = new DatiProgettoInizializzazioneDTO();
					dto.setCodiceVisualizzato(progettoBandoLineaVO.getCodiceVisualizzato());
					dto.setIdProcesso(progettoBandoLineaVO.getIdProcesso());
					if (progettoBandoLineaVO.getIdTipoOperazione() != null)
						dto.setIdTipoOperazione(progettoBandoLineaVO.getIdTipoOperazione().longValue());
				}
			}
		} catch (Exception e) {
			LOG.error(prf + " Exception " + e.getMessage());
		}

		LOG.info(prf + " END");
		return dto;
	}

	private EsitoOperazioneDTO isInSolaVisualizzazione(Long idProgetto) {
		EsitoOperazioneDTO esito = new EsitoOperazioneDTO();
		esito.setEsito(false);
		// Esiste la comunicazione di fine progetto
		PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
		pbandiTDichiarazioneSpesaVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTDichiarazioneSpesaVO.setIdTipoDichiarazSpesa(new BigDecimal(3));
		List<PbandiTDichiarazioneSpesaVO> pbandiTDichiarazioneSpesaVOs = genericDAO
				.findListWhere(pbandiTDichiarazioneSpesaVO);
		if (pbandiTDichiarazioneSpesaVOs != null && pbandiTDichiarazioneSpesaVOs.size() > 0) {
			esito.setEsito(true);
			esito.setCodiceMessaggio(
					"Rendicontazione in sola consultazione perché è stata inviata la comunicazione di fine progetto.");
			return esito;
		}
		// Esiste la comunicazione di rinuncia da parte del beneficiario
		PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTProgettoVO.setIdStatoProgetto(new BigDecimal(3));
		pbandiTProgettoVO = genericDAO.findSingleOrNoneWhere(pbandiTProgettoVO);
		if (pbandiTProgettoVO != null && pbandiTProgettoVO.getIdProgetto() != null) {
			esito.setEsito(true);
			esito.setCodiceMessaggio(
					"Rendicontazione in sola consultazione perché è stata inviata la comunicazione di rinuncia.");
			return esito;
		}
		// Chiusura del progetto
		pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTProgettoVO.setIdStatoProgetto(new BigDecimal(4));
		pbandiTProgettoVO = genericDAO.findSingleOrNoneWhere(pbandiTProgettoVO);
		if (pbandiTProgettoVO != null && pbandiTProgettoVO.getIdProgetto() != null) {
			esito.setEsito(true);
			esito.setCodiceMessaggio("Rendicontazione in sola consultazione perché il progetto è stato chiuso.");
			return esito;
		}
		// Chiusura d'ufficio del progetto
		pbandiTProgettoVO = new PbandiTProgettoVO();
		pbandiTProgettoVO.setIdProgetto(new BigDecimal(idProgetto));
		pbandiTProgettoVO.setIdStatoProgetto(new BigDecimal(14));
		pbandiTProgettoVO = genericDAO.findSingleOrNoneWhere(pbandiTProgettoVO);
		if (pbandiTProgettoVO != null && pbandiTProgettoVO.getIdProgetto() != null) {
			esito.setEsito(true);
			esito.setCodiceMessaggio("Rendicontazione in sola consultazione perché il progetto è stato chiuso.");
			return esito;
		}
		return esito;
	}

	@Override
	public SalCorrenteDTO getSALCorrente(long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente,
			String idIride, Long idBeneficiario) throws InvalidParameterException, Exception {
		String prf = "[RendicontazioneDAOImpl::getSALCorrente] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		SalCorrenteDTO result = null;
		try {
			String sql;
			sql = "SELECT D.DESC_ITER, D.ID_ITER, TDS.DESC_BREVE_TIPO_DICHIARA_SPESA AS DESC_BREVE_TIPO_DS, D.PERC_IMPORTO_CONTRIB  \r\n"
					+ "FROM PBANDI_R_PROGETTO_ITER T,PBANDI_D_ITER D, PBANDI_D_TIPO_DICHIARAZ_SPESA TDS\r\n"
					+ "WHERE T.ID_ITER=D.ID_ITER\r\n"
					+ "AND TDS.ID_TIPO_DICHIARAZ_SPESA = D.ID_TIPO_DICHIARAZ_SPESA\r\n" + "AND T.ID_PROGETTO = ?\r\n"
					+ "AND D.ID_TIPO_DICHIARAZ_SPESA IS NOT NULL\r\n" + "AND T.ID_DICHIARAZIONE_SPESA IS NULL\r\n"
					+ "ORDER BY D.ORDINAMENTO";

			Object[] args = new Object[] { idProgetto };
			LOG.info("<idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<SalCorrenteDTO> iters = getJdbcTemplate().query(sql, args, new BeanRowMapper(SalCorrenteDTO.class));
			if (iters != null && iters.size() > 0) {
				result = iters.get(0);
				if (result != null && result.getDescBreveTipoDs() != null && result.getDescBreveTipoDs().equals("FC")) {
					// setto il tipo finale da FC a F perche in frontend non viene gestito FC per
					// compatibilita con il vecchio
					result.setDescBreveTipoDs("F");
				}
				LOG.info(prf + " SAL corrente trovato con idIter: " + result.getIdIter());

				result.setUltimoIter(isSALCorrenteUltimo(prf, idProgetto, result.getIdIter()));
			} else {
				LOG.info(prf + " SAL corrente non trovato");
			}

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getSALCorrente: ", e);
			throw new Exception(" ERRORE in getSALCorrente.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public SalCorrenteDTO getSALByIdDocSpesa(long idProgetto, long idDocumentoSpesa, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[RendicontazioneDAOImpl::getSALByIdDocSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "idDocumentoSpesa = " + idDocumentoSpesa + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idDocumentoSpesa == 0) {
			throw new InvalidParameterException("idDocumentoSpesa non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		SalCorrenteDTO result = null;
		try {
			String sql;
			sql = "SELECT D.DESC_ITER, D.ID_ITER, TDS.DESC_BREVE_TIPO_DICHIARA_SPESA AS DESC_BREVE_TIPO_DS, D.PERC_IMPORTO_CONTRIB\r\n"
					+ "FROM PBANDI_R_PROGETTO_ITER T,PBANDI_D_ITER D, PBANDI_D_TIPO_DICHIARAZ_SPESA TDS, PBANDI_V_DICH_DOC_SPESA DDS\r\n"
					+ "WHERE T.ID_ITER=D.ID_ITER\r\n"
					+ "AND TDS.ID_TIPO_DICHIARAZ_SPESA = D.ID_TIPO_DICHIARAZ_SPESA\r\n"
					+ "AND DDS.ID_DICHIARAZIONE_SPESA = T.ID_DICHIARAZIONE_SPESA\r\n"
					+ "AND D.ID_TIPO_DICHIARAZ_SPESA IS NOT NULL\r\n" + "AND DDS.ID_DOCUMENTO_DI_SPESA = ? \r\n"
					+ "AND T.ID_PROGETTO = ? \r\n" + "ORDER BY D.ORDINAMENTO";

			Object[] args = new Object[] { idDocumentoSpesa, idProgetto };
			LOG.info("<idDocumentoSpesa>: " + idDocumentoSpesa + ",<idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<SalCorrenteDTO> iters = getJdbcTemplate().query(sql, args, new BeanRowMapper(SalCorrenteDTO.class));
			if (iters != null && iters.size() > 0) {
				result = iters.get(0);
				if (result != null && result.getDescBreveTipoDs() != null && result.getDescBreveTipoDs().equals("FC")) {
					// setto il tipo finale da FC a F perche in frontend non viene gestito FC per
					// compatibilita con il vecchio
					result.setDescBreveTipoDs("F");
				}
				LOG.info(prf + " SAL corrente trovato con idIter: " + result.getIdIter());

				result.setUltimoIter(isSALCorrenteUltimo(prf, idProgetto, result.getIdIter()));
			} else {
				LOG.info(prf + " SAL corrente non trovato");
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getSALByIdDocSpesa: ", e);
			throw new Exception(" ERRORE in getSALByIdDocSpesa.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	private Boolean isSALCorrenteUltimo(String prf, Long idProgetto, Long idIterCorrente) {
		LOG.info(prf + " Verifico se il SAL corrente e' l'ultimo.");
		String sql = "SELECT *\r\n" + "FROM (\r\n" + "	SELECT i.ID_ITER\r\n" + "	FROM PBANDI_R_PROGETTO_ITER pit\r\n"
				+ "	JOIN PBANDI_D_ITER i ON i.ID_ITER = pit.ID_ITER \r\n" + "	WHERE pit.ID_PROGETTO = ? \r\n"
				+ "	ORDER BY i.ORDINAMENTO DESC\r\n" + ")\r\n" + "WHERE ROWNUM = 1";

		Object[] args = new Object[] { idProgetto };
		LOG.info("<idProgetto>: " + idProgetto);
		LOG.info(prf + "\n" + sql + "\n");

		Long idUltimoIter = getJdbcTemplate().queryForObject(sql, args, Long.class);
		if (idUltimoIter != null && idUltimoIter.equals(idIterCorrente)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public List<DatiColonnaQteSalDTO> getDatiColonneQteSALCorrente(Long idProgetto, Long idIter, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[RendicontazioneDAOImpl::getDatiColonneQteSALCorrente] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idIter = " + idIter + "; idUtente = " + idUtente
				+ "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idIter == 0) {
			throw new InvalidParameterException("idIter non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		List<DatiColonnaQteSalDTO> result = null;
		try {
			String sql;
			sql = "SELECT fq.ID_COLONNA_QTES, f.DESC_COLONNA_QTES, d.ESTREMI_ATTO_APPROVAZIONE\r\n"
					+ "FROM PBANDI_D_FASI_QTES f \r\n"
					+ "JOIN PBANDI_R_ITER_FASI_QTES fq ON fq.ID_COLONNA_QTES = f.ID_COLONNA_QTES\r\n"
					+ "LEFT OUTER JOIN PBANDI_T_DATI_PROGETTO_QTES d ON d.ID_COLONNA_QTES = f.ID_COLONNA_QTES AND d.ID_PROGETTO = ?\r\n"
					+ "WHERE fq.ID_ITER = ?";

			Object[] args = new Object[] { idProgetto, idIter };
			LOG.info("<idProgetto>: " + idProgetto + ", <idIter>: " + idIter);
			LOG.info(prf + "\n" + sql + "\n");

			result = getJdbcTemplate().query(sql, args, new BeanRowMapper(DatiColonnaQteSalDTO.class));

			LOG.info(prf + "Numero di record trovati: " + (result != null ? result.size() : 0));

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getDatiColonneQteSALCorrente: ", e);
			throw new Exception(" ERRORE in getDatiColonneQteSALCorrente.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public List<DirezioneDTO> getDirezioniSettori(Long idUtente, String idIride) {
		logger.debug("[RendicontazioneDAOImpl::getDirezioniSettori] BEGIN ");
		List<DirezioneDTO> result = new ArrayList<>();
		String sql;
		try {
			sql = fileSqlUtil.getQuery("DirezioniVO.sql");
			result = getJdbcTemplate().query(sql, new DirezioniRowMapper());
			sql = fileSqlUtil.getQuery("SettoriFromDirezioneVO.sql");
		} catch (IOException e) {
			logger.error("Errore in lettura file DirezioniVO.sql oppure SettoriFromDirezioneVO.sql", e);
			throw new RuntimeException(e);
		}

		for (DirezioneDTO direzioneDTO : result) {
			direzioneDTO.setSettori(getJdbcTemplate().query(sql, new Object[] { direzioneDTO.getIdDirezione() },
					new SettoriRowMapper()));
		}
		logger.debug("[RendicontazioneDAOImpl::getDirezioniSettori] END");
		return result;

	}

	/*
	 * @Autowired protected FileSqlUtil fileSqlUtil;
	 * 
	 * @Autowired protected BeanUtil beanUtil;
	 * 
	 * @Autowired protected InizializzazioneDAOImpl inizializzazioneDAOImpl;
	 * 
	 * @Autowired protected DecodificheDAOImpl decodificheDAOImpl;
	 * 
	 * @Autowired protected
	 * it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl
	 * archivioBusinessImpl;
	 * 
	 * @Autowired protected
	 * it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl
	 * profilazioneBusinessImpl;
	 * 
	 * 
	 * @Override // Restituisce i dati con cui popolare Rendicontazione
	 * all'apertura: // - dati utente: userInfoSec // - dati del progetto public
	 * InizializzaRendicontazioneDTO inizializzaRendicontazione (long idProgetto,
	 * long idSoggetto, long idSoggettoBeneficiario, String codiceRuolo, long
	 * idUtente, HttpServletRequest req) throws InvalidParameterException, Exception
	 * { String prf = "[RendicontazioneDAOImpl::inizializzaRendicontazione] ";
	 * LOG.info(prf + " BEGIN"); InizializzaRendicontazioneDTO result = new
	 * InizializzaRendicontazioneDTO(); try {
	 * 
	 * if (idProgetto == 0) { throw new
	 * InvalidParameterException("idProgetto non valorizzato."); } if (idSoggetto ==
	 * 0) { throw new InvalidParameterException("idSoggetto non valorizzato."); } if
	 * (idSoggettoBeneficiario == 0) { throw new
	 * InvalidParameterException("idSoggettoBeneficiario non valorizzato."); } if
	 * (StringUtil.isEmpty(codiceRuolo)) { throw new
	 * InvalidParameterException("codiceRuolo non valorizzato."); }
	 * 
	 * Long defaultValueLong = new Long(0); String defaultValueStr = "--";
	 * result.setUserInfoSec(inizializzazioneDAOImpl.completaDatiUtente(idProgetto,
	 * idSoggetto, idSoggettoBeneficiario, idUtente, codiceRuolo,
	 * Constants.TASKNAME_DICHDISPESA, null, "StartTaskNeoFlux", req)); if
	 * (result.getUserInfoSec() == null) throw new
	 * UtenteException("Errore nella inizializzazione dei dati utente.");
	 * 
	 * result.setDatiProgetto(inizializzazioneDAOImpl.completaDatiProgetto(
	 * idProgetto));
	 * 
	 * } catch (Exception e) {
	 * LOG.error(prf+" ERRORE durante la inizializzazione di Rendicontazione: ", e);
	 * throw new
	 * DaoException("Errore durante la inizializzazione di Rendicontazione.", e); }
	 * finally { LOG.info(prf+" END"); }
	 * 
	 * return result; }
	 */
}
