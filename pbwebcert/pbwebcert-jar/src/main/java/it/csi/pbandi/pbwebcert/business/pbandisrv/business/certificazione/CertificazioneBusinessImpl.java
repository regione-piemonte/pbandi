/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business.pbandisrv.business.certificazione;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper.AllegatoVExcel20212027RowMapper;
import it.csi.pbandi.pbwebcert.integration.vo.AllegatoVExcel20212027VO;
import it.csi.pbandi.pbwebcert.util.FileSqlUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.certificazione.CertificazioneException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaInterventoCertificazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoPropostaCertificazUnionPeriodoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DetPropCerQpDocDettPropVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DetPropCerSoggFinDettPropVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DettPropCerFideiusDettPropVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DettPropCertErogazDettPropVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DettPropostaCertifAnnualCCVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ImportiAnticipoPropostaCertificazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoPerCompensazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.PropostaCertificazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ReportPropostaCertificazionePorFesrVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RipartizioneSpesaValidataParFASVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ScostamentoAssePropostaCertificazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDInvioPropostaCertifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoPropostaCertifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPropCertScostAsseVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRPropostaCertifLineaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDettPropostaCertifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.AttachmentVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbwebcert.integration.vo.MailVO;
import it.csi.pbandi.pbwebcert.util.DateUtil;
import it.csi.pbandi.pbwebcert.util.ErrorMessages;
import it.csi.pbandi.pbwebcert.util.MessageConstants;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbwebcert.business.MailUtil;
import it.csi.pbandi.pbwebcert.business.pbandisrv.interfacecsi.certificazione.CertificazioneSrv;
import it.csi.pbandi.pbwebcert.dto.certificazione.EsitoOperazioni;
import it.doqui.index.ecmengine.dto.Node;

public class CertificazioneBusinessImpl extends BusinessImpl implements CertificazioneSrv {
	
	protected FileApiServiceImpl fileApiServiceImpl;

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO genericDAO;

	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.MailDAO mailDAO;
	
	@Autowired
	protected MailUtil mailUtil;
	
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager reportManager;
	
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager documentoManager;
	
	@Autowired
	private it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerNuovaVersione;
	
	@Deprecated
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiCertificazioneDAOImpl pbandiCertificazioneDAO;	

	public it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO getGenericDAO() {
		return genericDAO;
	}
	public void setGenericDAO(it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.MailDAO getMailDAO() {
		return mailDAO;
	}
	public void setMailDAO(it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.MailDAO mailDAO) {
		this.mailDAO = mailDAO;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager getReportManager() {
		return reportManager;
	}
	public void setReportManager(it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager reportManager) {
		this.reportManager = reportManager;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager getDocumentoManager() {
		return documentoManager;
	}
	public void setDocumentoManager(it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}
	public it.csi.pbandi.pbservizit.business.manager.DocumentoManager getDocumentoManagerNuovaVersione() {
		return documentoManagerNuovaVersione;
	}
	public void setDocumentoManagerNuovaVersione(
			it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerNuovaVersione) {
		this.documentoManagerNuovaVersione = documentoManagerNuovaVersione;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiCertificazioneDAOImpl getPbandiCertificazioneDAO() {
		return pbandiCertificazioneDAO;
	}
	public void setPbandiCertificazioneDAO(
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiCertificazioneDAOImpl pbandiCertificazioneDAO) {
		this.pbandiCertificazioneDAO = pbandiCertificazioneDAO;
	}

	public String lanciaCreazionePropostaCertificazione(Long idUtente,
			String identitaDigitale,
			PropostaCertificazioneDTO propostaCertificazione,
			Long[] idLineeDiIntervento)
			throws CSIException, SystemException, UnrecoverableException, CertificazioneException {

		logger.info(" BEGIN");
		
		logger.info( "Lancia Creazione Proposta Certificazione");

		String[] nameParameter = { "idUtente", "identitaDigitale",	"propostaCertificazione.idPropostaCertificaz" };

		Long idPropostaCertificaz = propostaCertificazione.getIdPropostaCertificaz();
		
		logger.info( "idPropostaCertificaz="+idPropostaCertificaz);
		if (idLineeDiIntervento == null)
			logger.info("idLineeDiIntervento = NULL");
		else {
			logger.info( "idLineeDiIntervento.size = "+idLineeDiIntervento.length);
			for (Long l : idLineeDiIntervento)
				logger.info("  idLinea = "+l);
		}
		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,	identitaDigitale, idPropostaCertificaz);

		// just in case...
		aggiustaDescrizioneProposta(propostaCertificazione);
		
		PbandiTPropostaCertificazVO propostaCertificazVO = new PbandiTPropostaCertificazVO();
		propostaCertificazVO.setIdPropostaCertificaz(beanUtil.transform(idPropostaCertificaz, BigDecimal.class));
		String nuovoStato = null;

		if (isBozza(propostaCertificazione)) {
			nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_CODA;
		} else {
			nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_CODA;
		}
		logger.info( "nuovoStato="+nuovoStato);
		
		propostaCertificazVO.setIdStatoPropostaCertif(decodificheManager.decodeDescBreve(
						PbandiDStatoPropostaCertifVO.class, nuovoStato));
		try {
				
			propostaCertificazVO = genericDAO.findSingleWhere(propostaCertificazVO);

			propostaCertificazVO.setDtOraCreazione(new java.sql.Date(new java.util.Date().getTime()));

			if (isBozza(propostaCertificazione)) {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_ELABORAZIONE;
			} else {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_IN_ELABORAZIONE;
			}

			aggiornaStatoPropostaCertificazione(nuovoStato,	propostaCertificazVO, idUtente);
			logger.info( "aggiornaStatoPropostaCertificazione OK 1 ");
			

			eseguiProceduraCreazioneProposta(propostaCertificazione);
			logger.info( "eseguiProceduraCreazioneProposta ");

			// imposto la proposta come aperta, l'elaborazione � terminata con successo
			if (isBozza(propostaCertificazione)) {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA;
			} else {
				nuovoStato = Constants.STATO_PROPOSTA_CERTIFICAZIONE_APERTA;
			}

			aggiornaStatoPropostaCertificazione(nuovoStato,	propostaCertificazVO, idUtente);
			logger.info( "aggiornaStatoPropostaCertificazione OK 2 ");

			// dati persistiti, genero il report e lo invio

			MailVO mailVO = new MailVO();
			mailVO.setSubject("Esito creazione proposta certificazione num. "
					+ propostaCertificazVO.getIdPropostaCertificaz()
					+ (isBozza(propostaCertificazione) ? " (bozza)" : ""));
			mailVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
			mailVO.setToAddresses(caricaDestinatariEmailNotifica(
					idPropostaCertificaz, idLineeDiIntervento));
			//mailVO.setCcAddresses(getAssistenzaEmailAddress());

			try {
				logger.debug( "propostaCertificazVO="+propostaCertificazVO.toString());
				logger.debug( "propostaCertificazione="+propostaCertificazione.toString());
				logger.debug( "idUtente="+idUtente);
				logger.debug( "mailVO="+mailVO.toString());
				logger.debug( "idLineeDiIntervento="+idLineeDiIntervento);

				creaESalvaReportPropostaCertificazione(idUtente,
						propostaCertificazione, propostaCertificazVO, mailVO,
						idLineeDiIntervento, false);
			
			} catch (Exception e) {
				logger.error("Errore nella creazione del report per la proposta "
						+ idPropostaCertificaz
						+ ", impossibile inviarla. Verificare il log applicativo. Causa: "
						+ e.getMessage(),e);
				throw new CertificazioneException(
						"Errore nella creazione del report per la proposta "
								+ idPropostaCertificaz
								+ ", impossibile inviarla. Verificare il log applicativo. Causa: "
								+ e.getMessage());
			}
            logger.info("\n\n\nbefore mailDAO.send\n\n");
			//mailDAO.send(mailVO);
            mailUtil.send(mailVO);
			logger.info("\n\n\nafter mailDAO.send\n\n");

			
			// INIZIO ELABORAZIONE REV.
			
			String destinatariReportRev = leggiCostanteNonObbligatoria("indirizzoEmailReportCertificazioneRev");
			if (StringUtil.isEmpty(destinatariReportRev)) {
				logger.info(" ");
				logger.info("Salto la generazione del REPORT CERTIFICAZIONE REV.");
				logger.info(" ");
			} else {
				try {
					logger.info("Inizio la generazione del REPORT CERTIFICAZIONE REV.");
		
					// Passa da una stringa di mail separate da virgola ad una lista di stringhe.
					List<String> separatedMailAddresses = new ArrayList<String>();
					for (String mailAddress : destinatariReportRev.split(it.csi.pbandi.pbwebcert.util.Constants.MAIL_ADDRESSES_SEPARATOR)) {
						separatedMailAddresses.add(mailAddress.trim());
					}
					
					logger.info(" INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE_REV.MainCreaPropostaCertificazione()");
					BigDecimal myBig = beanUtil.transform(idPropostaCertificaz, BigDecimal.class); 
					logger.info(" Parametro pIdPropostaCertificaz = "+myBig);
					if (!genericDAO.callProcedure().creaPropostaCertificazioneRev(myBig)) {
					logger.error("lanciaCreazionePropostaCertificazione(): PCK_PBANDI_CERTIFICAZIONE_REV.MainCreaPropostaCertificazione() ha restituito un codice di errore.");
					} else {
						
						MailVO mailRevVO = new MailVO();
						mailRevVO.setSubject("Esito creazione proposta certificazione num. "
								+ propostaCertificazVO.getIdPropostaCertificaz()
								+ (isBozza(propostaCertificazione) ? " (bozza)" : "")
								+ " Revisione");
						mailRevVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
						mailRevVO.setToAddresses(separatedMailAddresses);
						//mailRevVO.setCcAddresses(getAssistenzaEmailAddress());
						
						// Cambia i sinonimi per far puntare alle tabelle REV.
						logger.info(" INVOCAZIONE FUNZIONE PLSQL PR_SW_SYNONYM_REV_CERTIF");
						if (!genericDAO.callProcedure().cambiaSinonimiCertificazioneRev()) {
							logger.error("lanciaCreazionePropostaCertificazione(): PR_SW_SYNONYM_REV_CERTIF() ha restituito un codice di errore: SINONIMI NON MODIFICATI.");
						} else {
							
							try {
								creaESalvaReportPropostaCertificazioneREV(idUtente,
										propostaCertificazione, propostaCertificazVO, mailRevVO,
										idLineeDiIntervento, false);
								
							} catch (Exception e) {
								logger.error("Errore nella creazione del report REV per la proposta "
										+ idPropostaCertificaz
										+ ", impossibile inviarla. Verificare il log applicativo. Causa: "
										+ e.getMessage(),e);
							}
				        
							
							logger.info("\n\n\nbefore mailDAO.send REV\n\n");
							//mailDAO.send(mailRevVO);
							mailUtil.send(mailRevVO);
							logger.info("\n\n\nafter mailDAO.send REV\n\n");							
							
						}
						
					}	

				} catch (Exception e) {
					logger.error("lanciaCreazionePropostaCertificazione(): ERRORE nel REPORT CERTIFICAZIONE REV", e);
				} finally {
					
//					// Ripristina i sinonimi.
//					logger.info(" INVOCAZIONE FUNZIONE PLSQL FN_UNDO_SYNONYM_REV_CERTIF()");
//					if (!genericDAO.callProcedure().ripristinaSinonimiCertificazioneRev()) {
//						logger.error("lanciaCreazionePropostaCertificazione(): FN_UNDO_SYNONYM_REV_CERTIF() ha restituito un codice di errore: SINONIMI NON RIPRISTINATI.");
//					}
					
					logger.info("Fine la generazione del REPORT CERTIFICAZIONE REV.");
				}				
			}
						 
			// fine ELABORAZIONE REV.

			 
		} catch (RecordNotFoundException e) {
			logger.error("RecordNotFoundException ",e);
			throw rollbackCreazioneReportPropostaCertificazione(idUtente,
					propostaCertificazVO, isBozza(propostaCertificazione), e,
					"La proposta " + idPropostaCertificaz
							+ " non esiste o non � nello stato da elaborare.");
		} catch (CertificazioneException e) {
			logger.error("CertificazioneException ",e);
			throw rollbackCreazioneReportPropostaCertificazione(idUtente,
					propostaCertificazVO, isBozza(propostaCertificazione), e,
					e.getMessage());
		} catch (Exception e) {
			String message = "Errore non previsto nella creazione del report per la proposta "
					+ idPropostaCertificaz
					+ ", impossibile inviarla. Verificare il log applicativo. Causa: "
					+ e.getMessage();
			logger.error(message,e);
			throw rollbackCreazioneReportPropostaCertificazione(idUtente,
					propostaCertificazVO, isBozza(propostaCertificazione), e,
					message);
		}   catch (Throwable t) {
			logger.error("catching Throwable");
			logger.error("throwable error message: "+t.getMessage(),t);
		}

		// metodo asincrono, non ritorno nulla
		logger.info(" END");
		return null;
	}
	
	private void aggiustaDescrizioneProposta(PropostaCertificazioneDTO propostaCertificazione) {
		// il GenericDAO introduce null con ""
		if (StringUtil.isEmpty(propostaCertificazione.getDescProposta())) {
			propostaCertificazione.setDescProposta(" ");
		}
	}
	
	private boolean isBozza(PropostaCertificazioneDTO propostaCertificazione) {
		return propostaCertificazione.getIsBozza() != null	&& propostaCertificazione.getIsBozza();
	}
	
	private void aggiornaStatoPropostaCertificazione(String keyStatoProposta,
			PbandiTPropostaCertificazVO propostaCertificazVO, Long idUtente)
			throws Exception {

		logger.info(" BEGIN : keyStatoProposta = "+keyStatoProposta);
		
		propostaCertificazVO.setIdStatoPropostaCertif(decodificheManager
				.decodeDescBreve(PbandiDStatoPropostaCertifVO.class,
						keyStatoProposta));
		propostaCertificazVO.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(getBeanUtil().transform(propostaCertificazVO,
				PbandiTPropostaCertificazVO.class));
		logger.info(" END");
	}
	
	private void eseguiProceduraCreazioneProposta(
			PropostaCertificazioneDTO propostaCertificazione) throws Exception {

		logger.debug(" BEGIN");

		PbandiTPropostaCertificazVO propostaCertificazVO = new PbandiTPropostaCertificazVO();
		BigDecimal idPropostaCertificaz = beanUtil.transform(
				propostaCertificazione.getIdPropostaCertificaz(),
				BigDecimal.class);
		logger.debug(" idPropostaCertificaz="+idPropostaCertificaz);
		
		propostaCertificazVO.setIdPropostaCertificaz(idPropostaCertificaz);

		// TODO spostare nel pl/sql

		// controllo le 4 tabelle r e la dett, devono essere tutte vuote
		if ((genericDAO.count(getBeanUtil().transform(propostaCertificazVO,
				DetPropCerQpDocDettPropVO.class))
				+ genericDAO.count(getBeanUtil().transform(
						propostaCertificazVO,
						DettPropCerFideiusDettPropVO.class))
				+ genericDAO.count(getBeanUtil().transform(
						propostaCertificazVO,
						DettPropCertErogazDettPropVO.class))
				+ genericDAO.count(getBeanUtil()
						.transform(propostaCertificazVO,
								DetPropCerSoggFinDettPropVO.class)) + genericDAO
					.count(getBeanUtil().transform(propostaCertificazVO,
							PbandiTDettPropostaCertifVO.class))) > 0) {
			throw new CertificazioneException("Dettagli per la proposta "
					+ propostaCertificazione.getIdPropostaCertificaz()
					+ " giï¿½ presenti.");
		}

		logger.info(" INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE.EXECUTE");
		if (!genericDAO.callProcedure().creaPropostaCertificazione(idPropostaCertificaz)) {
			throw new CertificazioneException("Esecuzione plsql fallita.");
		}
		logger.info(" FINE INVOCAZIONE PROCEDURA PCK_PBANDI_CERTIFICAZIONE.EXECUTE");
		logger.debug(" END");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> caricaDestinatariEmailNotifica(
			Long idPropostaCertificaz, Long[] idLineeDiIntervento)
			throws Exception, CertificazioneException {
		List<String> emailRecipients = new ArrayList<String>();

		Map<String, String> map = new HashMap<String, String>();

		List<PbandiDInvioPropostaCertifVO> linee = new ArrayList<PbandiDInvioPropostaCertifVO>();
		for (Long idLineaDiIntervento : idLineeDiIntervento) {
			PbandiDInvioPropostaCertifVO temp = new PbandiDInvioPropostaCertifVO();
			temp.setIdLineaDiIntervento(new BigDecimal(idLineaDiIntervento));
			linee.add(temp);
		}

		PbandiTPropostaCertificazVO propostaVO = new PbandiTPropostaCertificazVO();
		propostaVO.setIdPropostaCertificaz(beanUtil.transform(
				idPropostaCertificaz, BigDecimal.class));
		propostaVO = genericDAO.findSingleWhere(propostaVO);

		map.put("idStatoPropostaCertif", "idStatoPropostaCertif");
		PbandiDInvioPropostaCertifVO invioPropostaVO = beanUtil.transform(
				propostaVO, PbandiDInvioPropostaCertifVO.class, map);

		emailRecipients
				.addAll((Collection<? extends String>) getBeanUtil()
						.index(genericDAO
								.findListWhere(Condition
										.filterBy(
												getBeanUtil()
														.transformList(
																caricaLineeRadiceDaCertificare(idPropostaCertificaz),
																PbandiDInvioPropostaCertifVO.class,
																map))
										.and(Condition
												.filterBy(invioPropostaVO),
												Condition
														.validOnly(PbandiDInvioPropostaCertifVO.class),
												Condition.filterBy(linee))),
								"email").keySet());

		if (emailRecipients.size() == 0) {
			throw new CertificazioneException(
					"Non sono stati trovati indirizzi email a cui inviare la proposta "
							+ idPropostaCertificaz + ", impossibile inviarla.");
		}
		
		logger.info("caricaDestinatariEmailNotifica(): idPropostaCertificaz = "+idPropostaCertificaz);
		logger.info("caricaDestinatariEmailNotifica(): idLineeDiIntervento.length = "+idLineeDiIntervento.length);
		for (Long l : idLineeDiIntervento)
			logger.info("caricaDestinatariEmailNotifica():    idLinea = "+l);
		logger.info("caricaDestinatariEmailNotifica(): emailRecipients.size = "+emailRecipients.size());
		for (String s : emailRecipients)
			logger.info("caricaDestinatariEmailNotifica():    email = "+s);
		
		return emailRecipients;
	}
	
	public List<PbandiDLineaDiInterventoVO> caricaLineeRadiceDaCertificare(
			Long idPropostaCertificaz) throws CertificazioneException {
		/*
		 * TODO da modificare in modo che legga dal db
		 */

		// FIXME configurazione linee schiantata (da caso d'uso)
		// leggere sul db il flag CERTIFICAZIONE sul bando linea
		// in questo momento si mette il progetto che non si vuole
		// certificare in stato sospeso
		PbandiDLineaDiInterventoVO pbandiDLineaDiIntervento1VO = new PbandiDLineaDiInterventoVO();
		pbandiDLineaDiIntervento1VO
				.setDescBreveLinea(it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR);

		List<PbandiDLineaDiInterventoVO> lineeRadiceCoinvolte = new ArrayList<PbandiDLineaDiInterventoVO>();
		lineeRadiceCoinvolte.add(pbandiDLineaDiIntervento1VO);
		lineeRadiceCoinvolte = genericDAO.findListWhere(Condition.filterBy(
				lineeRadiceCoinvolte).and(
				Condition.validOnly(PbandiDLineaDiInterventoVO.class)));

		if (isEmpty(lineeRadiceCoinvolte)) {
			throw new CertificazioneException(
					"Non sono state trovate  linee di intervento per cui inviare la proposta "
							+ idPropostaCertificaz + ", impossibile inviarla.");
		}
		return lineeRadiceCoinvolte;
	}
	
	public List<String> getAssistenzaEmailAddress() {
		return mailDAO.getAssistenzaEmailAddresses();
	}
	
	private String leggiCostanteNonObbligatoria(String attributo) throws Exception {
		String valore = null;
		try {
			PbandiCCostantiVO c = new PbandiCCostantiVO();
			c.setAttributo(attributo);
			c = genericDAO.findSingleWhere(c);
			valore = c.getValore();
			logger.info("Valore della costante "+attributo+" = "+valore);
			//if (StringUtil.isEmpty(valore)) {
				//throw new Exception("Costante "+attributo+" non valorizzata.");
			//}
		} catch (Exception e) {
			//logger.error("ERRORE in leggiCostante(): "+e);
			//throw new Exception("Costante "+attributo+" non valorizzata.");
			logger.info("Costante "+attributo+" non valorizzata.");
		}
		return valore;
	}
	
	private CertificazioneException rollbackCreazioneReportPropostaCertificazione(
			Long idUtente, PbandiTPropostaCertificazVO propostaCertificazVO,
			boolean isBozza, Exception e, String message)
			throws CertificazioneException {
		try {
			// imposto la proposta come errata, l'elaborazione ï¿½ terminata
			// con
			// fallimento

			// rollback applicativo su db
			// rollbackDettagliPropostaCertificazione(propostaCertificazVO);

			if (isBozza) {
				aggiornaStatoPropostaCertificazione(
						Constants.STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_ERRATA,
						propostaCertificazVO, idUtente);
			} else {
				aggiornaStatoPropostaCertificazione(
						Constants.STATO_PROPOSTA_CERTIFICAZIONE_ERRATA,
						propostaCertificazVO, idUtente);
			}
		} catch (Exception e1) {
			logger.error(
					"Elaborazione fallita. Impossibile persistere lo stato di fallimento e cancellare i dettagli: "
							+ e1.getMessage(), e1);
		}

		MailVO mailVO = new MailVO();
		mailVO.setToAddresses(getAssistenzaEmailAddress());
		mailVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
		mailVO.setSubject(it.csi.pbandi.pbservizit.pbandisrv.util.Constants.SUBJECT_ERRORE_CREAZIONE_PROPOSTA_CERTIFICAZIONE);
		mailVO.setContent(message);
		//mailDAO.send(mailVO);
		mailUtil.send(mailVO);

		return new CertificazioneException(message);
	}
	
	public void creaESalvaReportPropostaCertificazione(Long idUtente,
			PropostaCertificazioneDTO propostaCertificazione,
			PbandiTPropostaCertificazVO propostaCertificazVO, MailVO mailVO,
			Long[] idLineeDiIntervento, boolean isPostGestione) throws Exception {
		
		logger.info(" BEGIN");
		
		BigDecimal idPropostaCertificaz = beanUtil.transform(
				propostaCertificazione.getIdPropostaCertificaz(),
				BigDecimal.class);

		logger.info(" idPropostaCertificaz="+idPropostaCertificaz);
		
		List<AttachmentVO> attachments = new ArrayList<AttachmentVO>();

		if (mailVO.getContent() == null) {
			mailVO.setContent("");
		}

		for (Long idLineaDiIntervento : idLineeDiIntervento) {
			
			/*
			 * carico il report dal database
			 */
			logger.info(" idLineaDiIntervento "+idLineaDiIntervento+" ++++");
			
			ReportPropostaCertificazionePorFesrVO filtroReport = new ReportPropostaCertificazionePorFesrVO();
			
			filtroReport.setIdPropostaCertificaz(idPropostaCertificaz);

			filtroReport.setIdLineaDiIntervento(beanUtil.transform(idLineaDiIntervento, BigDecimal.class));
			
			// Jira PBANDI-2882.
			// List<ReportPropostaCertificazionePorFesrVO> elementiReport = genericDAO.findListWhere(filtroReport);
			FilterCondition filter = new FilterCondition<ReportPropostaCertificazionePorFesrVO>(filtroReport);
			NullCondition<ReportPropostaCertificazionePorFesrVO> nullCond = new NullCondition<ReportPropostaCertificazionePorFesrVO>(ReportPropostaCertificazionePorFesrVO.class,"idProgettoSif");
			AndCondition andCond = new AndCondition<ReportPropostaCertificazionePorFesrVO>(filter, nullCond);
			List<ReportPropostaCertificazionePorFesrVO> elementiReport = genericDAO.findListWhere(andCond);
			// Jira PBANDI-2882 - fine.
			
			// Jira PBANDI-2882: dati con cui popolare il foglio Percettori.
			ReportPropostaCertificazionePorFesrVO filtroPercettori = new ReportPropostaCertificazionePorFesrVO();		
			filtroPercettori.setIdPropostaCertificaz(idPropostaCertificaz);
			filtroPercettori.setIdLineaDiIntervento(beanUtil.transform(idLineaDiIntervento, BigDecimal.class));
			// aggiungo la condizione "idProgettoSif is not null".
			FilterCondition filterCondPercettori = new FilterCondition<ReportPropostaCertificazionePorFesrVO>(filtroPercettori);
			NullCondition<ReportPropostaCertificazionePorFesrVO> nullCondPercettori = new NullCondition<ReportPropostaCertificazionePorFesrVO>(ReportPropostaCertificazionePorFesrVO.class,"idProgettoSif");
			NotCondition<ReportPropostaCertificazionePorFesrVO> notCondPrecettori = new NotCondition<ReportPropostaCertificazionePorFesrVO>(nullCondPercettori); 
			AndCondition andCondPercettori = new AndCondition<ReportPropostaCertificazionePorFesrVO>(filterCondPercettori, notCondPrecettori);
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori = genericDAO.findListWhere(andCondPercettori);
			// Jira PBANDI-2882 - fine
			
			String lineaDiIntervento = decodificheManager.findDescBreve(
					PbandiDLineaDiInterventoVO.class, new BigDecimal(
							idLineaDiIntervento));
			
			logger.info(" lineaDiIntervento="+lineaDiIntervento);
			
			mailVO.setContent(mailVO.getContent()
					+ creaTestoMail(
							lineaDiIntervento,
							!isEmpty(elementiReport),
							propostaCertificazVO.getDescProposta(),
							caricaScostamenti(idPropostaCertificaz),
							isScostamentoCompensato(idPropostaCertificaz),
							caricaProgettiPerCompensazione(idPropostaCertificaz))
					+ "\n\n");

			logger.info(" mailVO.setContent OK");
			
			if (!isEmpty(elementiReport)) {
               
				byte[] reportPropostaCertificazioneFileData = generaReport(
						propostaCertificazVO, 
						elementiReport, 
						lineaDiIntervento,
						elementiPercettori);
				
//				FileUtil.scriviFile("propostaCertificazione1420.xls", reportPropostaCertificazioneFileData);	
				
				if(reportPropostaCertificazioneFileData == null)
					throw new Exception("Il report per la certificazione è null");
				
				String postGestione = "";
				if(isPostGestione){
					postGestione = "PostGestione";
				}
				String nomeFile = "report" 
						+ postGestione
						+ propostaCertificazVO.getIdPropostaCertificaz()
						+ lineaDiIntervento + ".xls";
				logger.info( " nomeFile="+nomeFile);
				logger.info( " elementiReport !=null , creo attachment");
				AttachmentVO attach = new AttachmentVO();
				attach.setData(reportPropostaCertificazioneFileData);
				attach.setName(nomeFile);
				attach.setContentType(MIME_APPLICATION_XSL);

				attachments.add(attach);
				logger.info(" attachment added to list");
			}

			mailVO.setAttachments(attachments);

			for (AttachmentVO attachment : attachments) {
				// deve essere fatta per ultima, perché non è transazionale
				persistiDocumento(idUtente, attachment.getName(),
						attachment.getData(), propostaCertificazVO);
			}
           logger.info("settati attachments");
		}
		  logger.info("END");
	}
	
	public void creaESalvaReportPropostaCertificazioneREV(Long idUtente,
			PropostaCertificazioneDTO propostaCertificazione,
			PbandiTPropostaCertificazVO propostaCertificazVO, MailVO mailVO,
			Long[] idLineeDiIntervento, boolean isPostGestione) throws Exception {
		
		logger.info("\n\n\n   BEGIN creaESalvaReportPropostaCertificazioneREV()\n\n\n");
		
		BigDecimal idPropostaCertificaz = beanUtil.transform(
				propostaCertificazione.getIdPropostaCertificaz(),
				BigDecimal.class);

		logger.info(" idPropostaCertificaz="+idPropostaCertificaz);
		
		List<AttachmentVO> attachments = new ArrayList<AttachmentVO>();

		if (mailVO.getContent() == null) {
			mailVO.setContent("");
		}

		for (Long idLineaDiIntervento : idLineeDiIntervento) {
			
			/*
			 * carico il report dal database
			 */
			logger.info(" idLineaDiIntervento "+idLineaDiIntervento+" ++++");
			
			ReportPropostaCertificazionePorFesrVO filtroReport = new ReportPropostaCertificazionePorFesrVO();
			
			filtroReport.setIdPropostaCertificaz(idPropostaCertificaz);

			filtroReport.setIdLineaDiIntervento(beanUtil.transform(idLineaDiIntervento, BigDecimal.class));
			
			// Jira PBANDI-2882: aggiungo la condizione "idProgettoSif is null".
			// List<ReportPropostaCertificazionePorFesrVO> elementiReport = genericDAO.findListWhere(filtroReport);
			FilterCondition filterCond = new FilterCondition<ReportPropostaCertificazionePorFesrVO>(filtroReport);
			NullCondition<ReportPropostaCertificazionePorFesrVO> nullCond = new NullCondition<ReportPropostaCertificazionePorFesrVO>(ReportPropostaCertificazionePorFesrVO.class,"idProgettoSif");
			AndCondition andCond = new AndCondition<ReportPropostaCertificazionePorFesrVO>(filterCond, nullCond);
			List<ReportPropostaCertificazionePorFesrVO> elementiReport = genericDAO.findListWhere(andCond);
			// Jira PBANDI-2882 - fine.	
			
			// Jira PBANDI-2882: dati con cui popolare il foglio Percettori.
			ReportPropostaCertificazionePorFesrVO filtroPercettori = new ReportPropostaCertificazionePorFesrVO();		
			filtroPercettori.setIdPropostaCertificaz(idPropostaCertificaz);
			filtroPercettori.setIdLineaDiIntervento(beanUtil.transform(idLineaDiIntervento, BigDecimal.class));
			// aggiungo la condizione "idProgettoSif is not null".
			FilterCondition filterCondPercettori = new FilterCondition<ReportPropostaCertificazionePorFesrVO>(filtroPercettori);
			NullCondition<ReportPropostaCertificazionePorFesrVO> nullCondPercettori = new NullCondition<ReportPropostaCertificazionePorFesrVO>(ReportPropostaCertificazionePorFesrVO.class,"idProgettoSif");
			NotCondition<ReportPropostaCertificazionePorFesrVO> notCondPrecettori = new NotCondition<ReportPropostaCertificazionePorFesrVO>(nullCondPercettori); 
			AndCondition andCondPercettori = new AndCondition<ReportPropostaCertificazionePorFesrVO>(filterCondPercettori, notCondPrecettori);
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori = genericDAO.findListWhere(andCondPercettori);
			// Jira PBANDI-2882 - fine
			
			if (isEmpty(elementiReport)) {
				logger.info("elementiReport vuoto");
			} else {
				logger.info("num elementiReport = "+elementiReport.size());
			}
			
			String lineaDiIntervento = decodificheManager.findDescBreve(
					PbandiDLineaDiInterventoVO.class, new BigDecimal(
							idLineaDiIntervento));
			
			logger.info(" lineaDiIntervento="+lineaDiIntervento);
			
			mailVO.setContent(mailVO.getContent()
					+ creaTestoMail(
							lineaDiIntervento,
							!isEmpty(elementiReport),
							propostaCertificazVO.getDescProposta(),
							caricaScostamenti(idPropostaCertificaz),
							isScostamentoCompensato(idPropostaCertificaz),
							caricaProgettiPerCompensazione(idPropostaCertificaz))
					+ "\n\n");

			logger.info(" mailVO.setContent OK");
			
			if (!isEmpty(elementiReport)) {
               
				byte[] reportPropostaCertificazioneFileData = generaReport(
						propostaCertificazVO, 
						elementiReport, 
						lineaDiIntervento,
						elementiPercettori);
				
				if(reportPropostaCertificazioneFileData == null)
					throw new Exception("Il report REV per la certificazione è null");
				logger.info("reportPropostaCertificazioneFileData.length = "+reportPropostaCertificazioneFileData.length);
				
				String postGestione = "";
				if(isPostGestione){
					postGestione = "PostGestione";
				}
				String nomeFile = "report" 
						+ postGestione
						+ propostaCertificazVO.getIdPropostaCertificaz()
						+ lineaDiIntervento + "_REV.xls";
				logger.info( " nomeFile="+nomeFile);
				logger.info( " elementiReport !=null , creo attachment");
				AttachmentVO attach = new AttachmentVO();
				attach.setData(reportPropostaCertificazioneFileData);
				attach.setName(nomeFile);
				attach.setContentType(MIME_APPLICATION_XSL);

				attachments.add(attach);
				logger.info(" attachment added to list");
			}

			mailVO.setAttachments(attachments);

			//for (AttachmentVO attachment : attachments) {
				// deve essere fatta per ultima, perché non è transazionale
			//	persistiDocumento(idUtente, attachment.getName(),
			//			attachment.getData(), propostaCertificazVO);
			//}
			
           logger.info("settati attachments");
		}
		logger.info("\n\n\n   END creaESalvaReportPropostaCertificazioneREV()\n\n\n");
	}
	
	public String creaTestoMail(String lineaDiIntervento,
			boolean reportNotEmpty, String descProposta,
			List<ScostamentoAssePropostaCertificazioneVO> scostamenti,
			boolean scostamentoCompensato,
			List<ProgettoPerCompensazioneVO> progettiPerCompensazione)
			throws Exception {
		StringBuilder message = new StringBuilder();
		descProposta = descProposta == null ? "" : descProposta;		
		
		if (reportNotEmpty) {
			message.append("Report per la proposta di certificazione \""
					+ descProposta + "\" per la linea " + lineaDiIntervento
					+ " generato.\n");
		} else {
			message.append("La proposta di certificazione \"" + descProposta
					+ "\" per la linea " + lineaDiIntervento
					+ " non ha coinvolto nessun progetto.\n");
		}

		if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR.equals(lineaDiIntervento)) {
			
			// SOLO POR-FESR 
			message.append("\n");

			if (!isEmpty(scostamenti)) {
				message.append("Esiste uno scostamento rispetto alle percentuali standard.\n");

				message.append("\n");

				message.append(TableWriter.writeTableToString("scostamenti",
						scostamenti));

				message.append("\n");

				if (scostamentoCompensato) {
					message.append("Lo scostamento è stato compensato completamente.\n");
				} else if (!isEmpty(progettiPerCompensazione)) {
					message.append("Lo scostamento è stato compensato parzialmente.\n");
				} else {
					message.append("Lo scostamento non è stato compensato, nessun progetto disponibile per la compensazione.\n");
				}
			}

			message.append("\n");

			if (!isEmpty(progettiPerCompensazione)) {
				if (progettiPerCompensazione.size() > 1) {
					message.append("Sono stati utilizzati "
							+ progettiPerCompensazione.size()
							+ " per la compensazione:\n");
				} else {
					message.append("è stato utilizzato un solo progetto per la compensazione:\n");
				}

				message.append("\n");

				message.append(TableWriter.writeTableToString(
						"progettiPerCompensazione", progettiPerCompensazione));
			}
		}

		return message.toString();
	}
	
	private List<ScostamentoAssePropostaCertificazioneVO> caricaScostamenti(
			BigDecimal idPropostaCertificaz) {

		logger.info(" BEGIN");
		ScostamentoAssePropostaCertificazioneVO scostAsseVO = new ScostamentoAssePropostaCertificazioneVO();
		scostAsseVO.setIdPropostaCertificaz(idPropostaCertificaz);
		List<ScostamentoAssePropostaCertificazioneVO> scostamenti = genericDAO
				.where(Condition.filterBy(scostAsseVO)).select();
		logger.info(" END");
		return scostamenti;
	}
	
	private boolean isScostamentoCompensato(BigDecimal idPropostaCertificaz) {
		PbandiRPropCertScostAsseVO scostAsseVO = new PbandiRPropCertScostAsseVO();
		scostAsseVO.setIdPropostaCertificaz(idPropostaCertificaz);
		scostAsseVO
				.setFlagComp(Constants.FLAG_FALSE);

		boolean scostamentoCompensato = genericDAO.where(
				Condition.filterBy(scostAsseVO)).count() == 0;
		return scostamentoCompensato;
	}
	
	private List<ProgettoPerCompensazioneVO> caricaProgettiPerCompensazione(
			BigDecimal idPropostaCertificaz) {

		logger.info(" BEGIN");
		ProgettoPerCompensazioneVO p = new ProgettoPerCompensazioneVO();
		p.setIdPropostaCertificaz(idPropostaCertificaz);
		p.setFlagComp(Constants.FLAG_TRUE);
		
		logger.info(" END");
		return genericDAO.where(Condition.filterBy(p)).select();
	}
	
	private byte[] generaReport(
			PbandiTPropostaCertificazVO propostaCertificazVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport,
			String descBreveLinea,
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception {
		
		logger.info(" BEGIN");
		logger.debug( "descBreveLinea: " + descBreveLinea);
		
		byte[] reportBytes = null;
		if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR.equals(descBreveLinea)) {
			reportBytes = generaReportPorFesr(propostaCertificazVO, elementiReport, descBreveLinea);
			
		} else if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_PAR_FAS
				.equals(descBreveLinea)) {
			logger.debug( "prima di generaReportParFas");
			reportBytes = generaReportParFas(propostaCertificazVO, elementiReport, descBreveLinea);
			
		}else if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR_2014_20.equals(descBreveLinea)) {
			logger.debug( " di generaReportPorFesr2014_20");
			reportBytes = generaReportPorFesr2014_20(
					propostaCertificazVO, elementiReport, descBreveLinea, elementiPercettori);
		}
		else if (descBreveLinea.equals("POR-FESR-2021-2027")) {
			logger.debug( " di generaReportPorFesr2021_27");
			reportBytes = generaReportPorFesr2021_27(
					propostaCertificazVO, elementiReport, descBreveLinea, elementiPercettori);
		}

		if(reportBytes!=null)
			logger.debug( "reportPropostaCertificazioneFileData : "+reportBytes);
		else
			logger.debug( "reportPropostaCertificazioneFileData NULL ");
		
		logger.info(" END");
		return reportBytes;
	}


	private byte[] generaReportParFas(
			PbandiTPropostaCertificazVO propostaCertificazVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport,
			String descBreveLinea) throws Exception, IOException {

		logger.info(" BEGIN");
		
		final BigDecimal idPropostaCertificaz = propostaCertificazVO
				.getIdPropostaCertificaz();
		logger.info("idPropostaCertificaz: "+idPropostaCertificaz);
		byte[] reportPropostaCertificazioneFileData;
		String templateKey = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_PAR_FAS;
		logger.info("Popolo il template " + templateKey);
		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);

		reportPropostaCertificazioneFileData = TableWriter
				.writeTableToByteArray(templateKey, new ExcelDataWriter(
						idPropostaCertificaz.toString()), elementiReport,
						linesToJump);
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);

		RipartizioneSpesaValidataParFASVO ripartizioniVO = new RipartizioneSpesaValidataParFASVO();
		ripartizioniVO.setIdPropostaCertificaz(idPropostaCertificaz);
		Map<String, Object> ripartizioni = new HashMap<String, Object>() {
			// le ripartizioni non trovate vanno messe a null
			{
				put("I-1_CPUFAS-STA", null);
				put("I-1_OTHFIN", null);
				put("I-1_CPUFAS-REG", null);
				put("I-2_CPUFAS-STA", null);
				put("I-2_OTHFIN", null);
				put("I-2_CPUFAS-REG", null);
				put("I-3_CPUFAS-STA", null);
				put("I-3_OTHFIN", null);
				put("I-3_CPUFAS-REG", null);
				put("I-4_CPUFAS-STA", null);
				put("I-4_OTHFIN", null);
				put("I-4_CPUFAS-REG", null);
				put("II-1_CPUFAS-STA", null);
				put("II-1_OTHFIN", null);
				put("II-1_CPUFAS-REG", null);
				put("II-2_CPUFAS-STA", null);
				put("II-2_OTHFIN", null);
				put("II-2_CPUFAS-REG", null);
				put("II-3_CPUFAS-STA", null);
				put("II-3_OTHFIN", null);
				put("II-3_CPUFAS-REG", null);
				put("III-1_CPUFAS-STA", null);
				put("III-1_OTHFIN", null);
				put("III-1_CPUFAS-REG", null);
				put("III-2_CPUFAS-STA", null);
				put("III-2_OTHFIN", null);
				put("III-2_CPUFAS-REG", null);
				put("III-3_CPUFAS-STA", null);
				put("III-3_OTHFIN", null);
				put("III-3_CPUFAS-REG", null);
				put("III-4_CPUFAS-STA", null);
				put("III-4_OTHFIN", null);
				put("III-4_CPUFAS-REG", null);
				put("III-5_CPUFAS-STA", null);
				put("III-5_OTHFIN", null);
				put("III-5_CPUFAS-REG", null);
				put("III-6_CPUFAS-STA", null);
				put("III-6_OTHFIN", null);
				put("III-6_CPUFAS-REG", null);
				put("IV-1_CPUFAS-STA", null);
				put("IV-1_OTHFIN", null);
				put("IV-1_CPUFAS-REG", null);
				put("IV-2_CPUFAS-STA", null);
				put("IV-2_OTHFIN", null);
				put("IV-2_CPUFAS-REG", null);
				put("V-511_CPUFAS-STA", null); // modified 17/02/16 pcl, MODIFIED ALSA TEMPLATE XLS
				put("V-511_OTHFIN", null);// modified 17/02/16 pcl
				put("V-511_CPUFAS-REG", null);// modified 17/02/16 pcl
				put("VI-611_CPUFAS-STA", null);
				put("VI-611_OTHFIN", null);
				put("VI-611_CPUFAS-REG", null);
			}
		};
		for (RipartizioneSpesaValidataParFASVO ripartizione : genericDAO.where(
				ripartizioniVO).select()) {
			ripartizioni.put(ripartizione.getReportKey(),
					ripartizione.getSpesaValidata());
			logger.info("ripartizione.getReportKey():"+ripartizione.getReportKey()+"  ,ripartizione.getSpesaValidata():" +ripartizione.getSpesaValidata());
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Per Linea", ripartizioni);

		reportPropostaCertificazioneFileData = reportManager
				.getMergedDocumentFromTemplate(templateKey, singlePageTables,
						singleCellVars);
		
		logger.info(" END");		
		return reportPropostaCertificazioneFileData;

	}

	private byte[] generaReportPorFesr(
			PbandiTPropostaCertificazVO propostaCertificazVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport,
			String descBreveLinea) throws Exception, IOException {

		logger.info(" BEGIN");

		byte[] reportPropostaCertificazioneFileData;
		// conf/report/xls_templates/reportPropostaCertificazionePorFesr.xls
		String templateKey = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR;
		logger.debug( "Popolo il template =" + templateKey);
		
		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);
		reportPropostaCertificazioneFileData = TableWriter
				.writeTableToByteArray(templateKey, new ExcelDataWriter(
						propostaCertificazVO.getIdPropostaCertificaz().toString()), elementiReport, linesToJump);
		
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);
		Map<String, Object> asseVars = new HashMap<String, Object>();
		
		PbandiDLineaDiInterventoVO porFesrVO = new PbandiDLineaDiInterventoVO();
		porFesrVO.setDescBreveLinea(descBreveLinea);
		asseVars.put("codIgrueT13T14", genericDAO.findSingleWhere(porFesrVO).getCodIgrueT13T14());
		
		ImportiAnticipoPropostaCertificazioneVO importiVO = new ImportiAnticipoPropostaCertificazioneVO();
		importiVO.setIdPropostaCertificaz(propostaCertificazVO.getIdPropostaCertificaz());

		Map<String, String> allegatoV = beanUtil.map(genericDAO.where(importiVO).select(), "descBreveLinea", "importoAnticipo");
		
		final String IMPORTO_ANTICIPO_PREFIX = "importoAnticipo";
		
		Map<String, Object> allegatoVVars = new HashMap<String, Object>();
		for (String key : new String[] { "I", "II", "III", "IV" }) {
			String value = allegatoV.get(key);
			if (value != null) {
				allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key,
						new BigDecimal(value));
			} else {
				allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key,
						new BigDecimal(0));
			}
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		
		//Non più neccessaria per la nuova programmazione
		singleCellVars.put("Per Asse", asseVars);
		singleCellVars.put("Allegato V", allegatoVVars);
		
		reportPropostaCertificazioneFileData = reportManager.getMergedDocumentFromTemplate(templateKey, singlePageTables, singleCellVars);
		
		logger.info(" END");
		return reportPropostaCertificazioneFileData;
	}
	
	// Jira PBANDI-2882: aggiunto elementiPercettori.
	private byte[] generaReportPorFesr2014_20(
			PbandiTPropostaCertificazVO propostaCertificazVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport,
			String descBreveLinea,
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception, IOException {

		logger.info(" BEGIN");
		
		byte[] reportPropostaCertificazioneFileData;
		// conf/report/xls_templates/nReportPropostaCertificazionePorFesr2014_20.xls
		String templateKey = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR2014_20;
		logger.info("Popolo il template " + templateKey);
		
		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);
		reportPropostaCertificazioneFileData = TableWriter
				.writeTableToByteArray(templateKey, new ExcelDataWriter(
						propostaCertificazVO.getIdPropostaCertificaz().toString()), elementiReport, linesToJump);
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);
		
		
		// Jira PBANDI-2882: foglio Percettoti.
		logger.info("\n foglio Percettori \n");
		String templateKeyPercettori = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR_PERCETTORI;
		byte[] reportPropostaCertificazioneFileDataPercettori;
		reportPropostaCertificazioneFileDataPercettori = TableWriter
				.writeTableToByteArray(templateKeyPercettori, new ExcelDataWriter(
						propostaCertificazVO.getIdPropostaCertificaz().toString()+"Percettori"), elementiPercettori, linesToJump);		
		ReportPropostaCertificazionePorFesrVO elementoPercettori = new ReportPropostaCertificazionePorFesrVO();
		if (elementiPercettori.size() > 0) {
			elementoPercettori = elementiPercettori.get(0);	
		}
		Map<String, Object> testataVarsPercettori = beanUtil.transformToMap(elementoPercettori);
		// Jira PBANDI-2882: foglio Percettoti - fine.
				
		PbandiDLineaDiInterventoVO porFesrVO = new PbandiDLineaDiInterventoVO();
		porFesrVO.setDescBreveLinea(descBreveLinea);
		
		//Importo Anticipo 
		ImportiAnticipoPropostaCertificazioneVO importiVO = new ImportiAnticipoPropostaCertificazioneVO();
		importiVO.setIdPropostaCertificaz(propostaCertificazVO.getIdPropostaCertificaz());
		
		//******* PREFIXES **************************************
		final String IMPORTO_ANTICIPO_PREFIX = "importoAnticipo";
		final String IMPORTO_NONCOPERTO_PREFIX = "importoNonCoperto";
		final String DIFF_IMPORTI_ANTICIPO_PREFIX = "diffAnticipoImpNonCop";
		final String IMPORTI_PAGVALIDATINETTI_PREFIX = "importoPagValidatiNetti";
		final String IMPORTO_CERTNETTO_PREFIX = "importoCertNetto";
		
		//Inserire gli assi in una costante
		Map<String, BigDecimal> asseLineaInterventoMap = new HashMap<String, BigDecimal>();
		asseLineaInterventoMap.put("I" , 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_I);
		asseLineaInterventoMap.put("II", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_II);
		asseLineaInterventoMap.put("III", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_III);
		asseLineaInterventoMap.put("IV", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_IV);
		asseLineaInterventoMap.put("V", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_V);
		asseLineaInterventoMap.put("VI", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_VI);
		asseLineaInterventoMap.put("VII", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_VII);
		
		Map<String, Object> allegatoVVars = new HashMap<String, Object>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> anticipoMap = new HashMap<String, BigDecimal>();
		List<ImportiAnticipoPropostaCertificazioneVO> anticipi = genericDAO.where(importiVO).select();
		
		for (String key : asseLineaInterventoMap.keySet()) {
			
			BigDecimal idLineaDiIntervento = asseLineaInterventoMap.get(key);
			BigDecimal value = null;
			
			for(ImportiAnticipoPropostaCertificazioneVO anticipo : anticipi){
				if(anticipo.getIdLineaDiIntervento().compareTo(idLineaDiIntervento) == 0){
					 value = anticipo.getImportoAnticipo();
				}
			}
			
			anticipoMap.put(key, value);
		}

		// cerco i dettagli delle proposte di certificazioni appartenenti a progetti SIFP
		List<Map> proposteSIFList = pbandiCertificazioneDAO.findProposteSIF(propostaCertificazVO.getIdPropostaCertificaz());
		logger.info(" proposteSIFList="+proposteSIFList);
		List<BigDecimal> listaII = null;
		
		// popolo una lista con gli id dei progetti SIF per l'id certificazione dato
		if(proposteSIFList!=null && !proposteSIFList.isEmpty()){
			
			listaII = new ArrayList<BigDecimal>();
			for (Map mappa : proposteSIFList) {
				logger.info(" ID_PROGETTO="+mappa.get("ID_PROGETTO"));
				listaII.add((BigDecimal)mappa.get("ID_PROGETTO"));
			}
		}
		
		//impCertNettiMap = sommo colonna AW (ImportiCertificabiliNetti) per Asse
		Map<String, BigDecimal> impCertNettiMap = new HashMap<String, BigDecimal>();
		
		// impPagValidazMap = somma colonna AE (ImportoPagamentiValidati) per Asse 
		// TODO : dubbio : questo valore è corretto o devo prendere ImportoPagValidatiOrig?
		Map<String, BigDecimal> impPagValidazMap = new HashMap<String, BigDecimal>();

		// impEccedValidazMap = somma colonna AF (ImportoEccendenzeValidazione) per Asse
		Map<String, BigDecimal> impEccedValidazMap = new HashMap<String, BigDecimal>();
		
		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> sommaValoriNMap = new HashMap<String, BigDecimal>();
		
		for (ReportPropostaCertificazionePorFesrVO el : elementiReport) {
			
			String asse = el.getDescAsse();
			
			if(listaII!=null && listaII.contains( el.getIdProgetto())){
				
				BigDecimal imp = el.getImpCertificabileNettoSoppr();
				
				logger.info(" SIF: Asse Progetto IdLineaDiIntervento= ["+asse+"] ["+el.getIdProgetto()+"] ["+el.getIdLineaDiIntervento()+"] ");
				
				BigDecimal tmpA = impCertNettiMap.get(asse);
				if(tmpA!=null){
					BigDecimal somA = imp.add(tmpA);
					impCertNettiMap.put(asse, somA);
				}else{
					impCertNettiMap.put(asse, imp);
				}
				
				BigDecimal impB = el.getImportoPagamentiValidati();
				BigDecimal tmpB = impPagValidazMap.get(asse);
				if(tmpB!=null){
					BigDecimal somB = impB.add(tmpB);
					impPagValidazMap.put(asse, somB);
				}else{
					impPagValidazMap.put(asse, impB);
				}
				
				BigDecimal impC = el.getImportoEccendenzeValidazione();
				BigDecimal tmpC = impEccedValidazMap.get(asse);
				if(tmpB!=null){
					BigDecimal somC = impC.add(tmpC);
					impEccedValidazMap.put(asse, somC);
				}else{
					impEccedValidazMap.put(asse, impC);
				}
			}else{
				logger.info(" NON SIF: Asse Progetto IdLineaDiIntervento= ["+asse+"] ["+el.getIdProgetto()+"] ["+el.getIdLineaDiIntervento()+"] ");
			}
			
			// Jira PBANDI-2882: al posto di SommaValoriN ora si usa ValoreN.  
			//BigDecimal impD = el.getSommaValoriN();
			BigDecimal impD = el.getValoreN();
			BigDecimal tmpD = sommaValoriNMap.get(asse);
			if(tmpD!=null){
				BigDecimal somD = impD.add(tmpD);
				sommaValoriNMap.put(asse, somD);
				logger.info(" somD="+somD);
			}else{
				sommaValoriNMap.put(asse, impD);
			}
			
		}
		logger.info(" impCertNettiMap="+impCertNettiMap);
		logger.info(" impPagValidazMap="+impPagValidazMap);
		logger.info(" impEccedValidazMap="+impEccedValidazMap);
		logger.info(" sommaValoriNMap="+sommaValoriNMap);
		
		for (String key : new String[] { "I", "II", "III", "IV" , "V" , "VI" , "VII"}) {
			
			BigDecimal valueSomm = new BigDecimal(0);
			BigDecimal valueAnt = new BigDecimal(0);
			
			if(sommaValoriNMap!=null && sommaValoriNMap.containsKey(key) && sommaValoriNMap.get(key)!=null){
				logger.info(" asse ="+key+" , sommaValoriNMap.get(key)="+sommaValoriNMap.get(key));
				valueSomm = valueSomm.add(sommaValoriNMap.get(key));
			}
			
			if(anticipoMap!=null && anticipoMap.containsKey(key) && anticipoMap.get(key)!=null){
				logger.info(" asse ="+key+" , anticipoMap.get(key)="+anticipoMap.get(key));
				valueAnt = valueAnt.add(anticipoMap.get(key));
			}
			
			logger.info(" asse ="+key+" , valueSomm ="+valueSomm+" , valueAnt="+valueAnt);
			
			// valorizzo la colonna "C" = "Importo complessivo versato come anticipo...."
			allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key, valueSomm);
			
			// valorizzo la colonna "E" = "Importo che non e' stato coperto dalle spese....."
			if(valueAnt.compareTo(valueSomm)<=0){
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueAnt);
			}else{
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueSomm);
			}
			
			// valorizzo la colonna "D" = "C" - "E"
			// calcolo la differenza tra IMPORTO_ANTICIPO_PREFIX e IMPORTO_NONCOPERTO_PREFIX
			BigDecimal diffA = new BigDecimal(0);
			BigDecimal diffB = new BigDecimal(0);
			
			if(allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key)!=null)
				diffA = diffA.add((BigDecimal)allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key));
				
			if(allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key)!=null)
				diffB = diffB.add((BigDecimal)allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key));
			
			BigDecimal diffe = diffA.subtract(diffB);
			logger.info(" asse ="+key+" , diffA="+diffA+" ,diffB ="+diffB+" , diffe=" +diffe);
			allegatoVVars.put(DIFF_IMPORTI_ANTICIPO_PREFIX + key, diffe);
			
			if(impCertNettiMap.get(key)!=null){
				allegatoVVars.put(IMPORTO_CERTNETTO_PREFIX + key, impCertNettiMap.get(key));
			}else{
				allegatoVVars.put(IMPORTO_CERTNETTO_PREFIX + key, new BigDecimal(0));
			}
			
			// impPagValidazMap = somma colonna AE per Asse 
			// impEccedValidazMap = somma colonna AF per Asse
			
			BigDecimal AE = impPagValidazMap.get(key);
			logger.info(" AE="+AE);
			
			if(AE!=null){
				BigDecimal AF ;
				if(impEccedValidazMap.get(key)!=null){
					AF = impEccedValidazMap.get(key);
				}else{
					AF = new BigDecimal(0);
				}
				BigDecimal diff = AE.subtract(AF);
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, diff);
			}else{
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, new BigDecimal(0));
			}
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Allegato V", allegatoVVars);
		// Jira PBANDI-2882.
		singlePageTables.put("Percettori", reportPropostaCertificazioneFileDataPercettori);
		
		reportPropostaCertificazioneFileData = reportManager
				.getMergedDocumentFromTemplate(templateKey, singlePageTables,
						singleCellVars);
		
//		FileUtil.scriviFile("report1420.xls", reportPropostaCertificazioneFileData);
		
		logger.info(" END");
		return reportPropostaCertificazioneFileData;
	}

	private void persistiDocumento(Long idUtente, String nomeFile,
			byte[] reportPropostaCertificazioneFileData,
			PbandiTPropostaCertificazVO propostaCertificazVO) throws Exception {

		logger.info(" BEGIN");
		
		//DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
		//documentoIndexVO.setNomeFile(nomeFile);
		//logger.info( "saving reportPropostaCertificazioneFileData on index ++++++++++++++++ ");
		
		//Node node = indexDAO.creaContentReportPropostaCertificazione(
		//		reportPropostaCertificazioneFileData, documentoIndexVO,
		//		propostaCertificazVO, idUtente);
		
		Node nodoIndex = new Node();
		
		String shaHex = null;
		if(reportPropostaCertificazioneFileData!=null)
			shaHex = DigestUtils.shaHex(reportPropostaCertificazioneFileData);
		
		logger.info( "saving reportPropostaCertificazioneFileData on db +++++++++++++++++ ");
				
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO;
		pbandiTDocumentoIndexVO = documentoManager.salvaInfoNodoIndexSuDbSenzaInsert(
						idUtente,
						nodoIndex,
						nomeFile,
						beanUtil.transform(propostaCertificazVO.getIdPropostaCertificaz(), Long.class),
						null,null,null,
						Constants.TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE,
						PbandiTPropostaCertificazVO.class, null,shaHex);
		
		
		// **********************************************************************************
		// AGGIUNTA PER SALVATAGGIO SU FILESYSTEM.
		pbandiTDocumentoIndexVO.setRepository(Constants.TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE);
		this.salvaFileSuFileSystem(reportPropostaCertificazioneFileData, pbandiTDocumentoIndexVO);
		// **********************************************************************************

	}
	
	private void salvaFileSuFileSystem(
			byte[] file, 
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO vo) throws Exception {
		logger.info("salvaFileSuFileSystem(): file.length = "+file.length+"; NomeFile = "+vo.getNomeFile());
		
		// Trasformo PbandiTDocumentoIndexVO di un DocumentoManager 
		// nel PbandiTDocumentoIndexVO di un altro DocumentoManager (sigh)
		it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO newVO =
			beanUtil.transform(vo, it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO.class);
				
		InputStream is = new ByteArrayInputStream(file);
		boolean esitoSalva = true;
		esitoSalva = documentoManagerNuovaVersione.salvaFile(file, newVO);
		
		if (!esitoSalva)
			throw new Exception("File "+vo.getNomeFile()+" non salvato su file system.");
	
		vo.setIdDocumentoIndex(newVO.getIdDocumentoIndex());
	}
	
	// NUOVA PROGRAMMAZION POR FERS 2014-2020
	// Invia il report al termine della gestione dei progetti. 
	public String inviaReportPostGestione(Long idUtente, String identitaDigitale,
										  PropostaCertificazioneDTO propostaCertificazione,
										  Long[] idLineeDiIntervento)
			throws CSIException, SystemException, UnrecoverableException, CertificazioneException{
		
		logger.info("\n\n\n INVIO REPORT POST GESTIONE \n\n\n");
		
		Long idPropostaCertificazione = propostaCertificazione.getIdPropostaCertificaz();
		logger.info("idPropostaCertificazione = "+idPropostaCertificazione);
		
		String[] valueToCheck = {"idUtente", "identitaDigitale", "idPropostaCertificazione"};
        ValidatorInput.verifyNullValue(valueToCheck, idUtente, identitaDigitale, idPropostaCertificazione);

        PbandiTPropostaCertificazVO propostaCertificazVO = new PbandiTPropostaCertificazVO();
        propostaCertificazVO.setIdPropostaCertificaz(beanUtil.transform(idPropostaCertificazione, BigDecimal.class));

		try{
            propostaCertificazVO = genericDAO.findSingleWhere(propostaCertificazVO);

            MailVO mailVO = new MailVO();
            mailVO.setSubject("Esito gestione proposta certificazione num. "
                    + idPropostaCertificazione
                    + (isBozza(propostaCertificazione) ? " (bozza)" : ""));
            mailVO.setFromAddress(Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
            mailVO.setToAddresses(caricaDestinatariEmailNotifica(
                    idPropostaCertificazione, idLineeDiIntervento));
                 
            mailVO.setCcAddresses(getAssistenzaEmailAddress());

            try {
            	
                creaESalvaReportPropostaCertificazione(idUtente,
                        propostaCertificazione, propostaCertificazVO, mailVO,
                        idLineeDiIntervento, true);
                     
               addProgettiModificatiToMailContent(mailVO, idPropostaCertificazione);
                
            } catch (Exception e) {
                logger.error("Errore nella creazione del report per la proposta "
                        + idPropostaCertificazione
                        + ", impossibile inviarla. Verificare il log applicativo. Causa: "
                        + e.getMessage(),e);
                throw new CertificazioneException(
                        "Errore nella creazione del report per la proposta "
                                + idPropostaCertificazione
                                + ", impossibile inviarla. Verificare il log applicativo. Causa: "
                                + e.getMessage());
            }
            logger.info("\n\n\nbefore mailDAO.send\n\n");
            logger.info("----- MAIL CONTENT ------ \n " + mailVO.getContent());
            //mailDAO.send(mailVO);
            mailUtil.send(mailVO);
            logger.info("\n\n\nafter mailDAO.send\n\n");
        } catch (RecordNotFoundException e) {
            logger.error("RecordNotFoundException ",e);
            throw rollbackCreazioneReportPropostaCertificazione(idUtente,
                    propostaCertificazVO, isBozza(propostaCertificazione), e,
                    "La proposta " + idPropostaCertificazione
                            + " non esiste o non ï¿½ nello stato da elaborare.");
        } catch (CertificazioneException e) {
            logger.error("CertificazioneException ",e);
            throw rollbackCreazioneReportPropostaCertificazione(idUtente,
                    propostaCertificazVO, isBozza(propostaCertificazione), e,
                    e.getMessage());
        } catch (Exception e) {
            String message = "Errore non previsto nella creazione del report per la proposta "
                    + idPropostaCertificazione
                    + ", impossibile inviarla. Verificare il log applicativo. Causa: "
                    + e.getMessage();
            logger.error(message,e);
            throw rollbackCreazioneReportPropostaCertificazione(idUtente,
                    propostaCertificazVO, isBozza(propostaCertificazione), e,
                    message);
        }   catch (Throwable t) {
            logger.error("catching Throwable");
            logger.error("throwable error message: "+t.getMessage(),t);
        }
		
		return null;
	}
	
	// Aggiunge al corpo dell'email la lista dei progetti modificati.
	private void addProgettiModificatiToMailContent(MailVO mailVO, Long idPropostaCertificazione){    
		
         List<PbandiTDettPropostaCertifVO> dettProgettiPropostaModificati = new ArrayList<PbandiTDettPropostaCertifVO>();
         
         try {
			for(PbandiTDettPropostaCertifVO dettProgetto: getDettaglioProgettiProposta(idPropostaCertificazione)){
			 	if(dettProgetto.getImportoCertificazioneNetto().compareTo(dettProgetto.getImpCertifNettoPremodifica()) != 0){
			 		dettProgettiPropostaModificati.add(dettProgetto);
			 	}
			}

			if(!dettProgettiPropostaModificati.isEmpty()){
				mailVO.setContent(mailVO.getContent() 
						+ TableWriter.writeHtmlTableToString(
								"progettiModificati", dettProgettiPropostaModificati));
			}
		} catch (CertificazioneException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}     
	}
	
	private List<PbandiTDettPropostaCertifVO> getDettaglioProgettiProposta(Long idPropostaCertificazione) throws CertificazioneException{
		
		logger.begin();
		
		try{
			//Verificare che idUtente, identitaDigitale e idProgetto non siano nulli
			//se i valori sono nulli la funzione statica ValidatorInput::verifyNullValue 
			//solleva un eccezione
			String[] parameterToCheck = {"idProposta"}; 
			ValidatorInput.verifyNullValue(parameterToCheck, idPropostaCertificazione);
		
			PbandiTDettPropostaCertifVO query = new PbandiTDettPropostaCertifVO();
			query.setIdPropostaCertificaz(new BigDecimal(idPropostaCertificazione));
			
			Condition<PbandiTDettPropostaCertifVO> condizione = Condition.filterBy(query);
			
			List<PbandiTDettPropostaCertifVO> result = genericDAO.where(condizione).select();
			
			return result;
		}catch(Exception e){
			String msg = "Errore nell'ottenimento progetti da gestire :" 
				+ e.getMessage();
			logger.error(msg, e);
			throw new CertificazioneException(msg, e);
		} finally{
			logger.end();
		}		
	}

	public PropostaCertificazioneDTO[] findProposteCertificazione(Long idUtente, String identitaDigitale, List<String> statiProposta)
			throws CSIException, SystemException, UnrecoverableException, CertificazioneException {
		try {
			
			if (statiProposta != null && statiProposta.size() > 0) {
				List<PropostaCertificazVO> filtri = new ArrayList<PropostaCertificazVO>();
				for (String descBreve : statiProposta) {
					PropostaCertificazVO filtro = new PropostaCertificazVO();
					filtro.setDescBreveStatoPropostaCert(descBreve);
					filtri.add(filtro);
				}
				return getBeanUtil().transform(genericDAO.findListWhere(filtri), PropostaCertificazioneDTO.class);
			} else {
				// Jira PBANDI-2556: Estae tutte le proposte indipendentemente dallo stato.
				return getBeanUtil().transform(genericDAO.findListAll(PropostaCertificazVO.class), PropostaCertificazioneDTO.class);
			}			
		} finally {
			logger.end();
		}
	}
	
	public Long findIdLineaDiInterventoFromProposta(Long idProposta) throws CSIException, SystemException,
			UnrecoverableException, CertificazioneException {		
		logger.begin();		
		try{
			String[] parameterToCheck = {"idProposta"}; 
			ValidatorInput.verifyNullValue(parameterToCheck, idProposta);
			
			PbandiRPropostaCertifLineaVO query = new PbandiRPropostaCertifLineaVO();
			query.setIdPropostaCertificaz(new BigDecimal(idProposta));
			Condition<PbandiRPropostaCertifLineaVO> condition = Condition.filterBy(query);
			PbandiRPropostaCertifLineaVO idLineaVO = genericDAO.findFirstWhere(condition);
			
			if(idLineaVO != null){
				PbandiDLineaDiInterventoVO queryLineaIntervento = new PbandiDLineaDiInterventoVO();
				queryLineaIntervento.setIdLineaDiIntervento(idLineaVO.getIdLineaDiIntervento());
				Condition<PbandiDLineaDiInterventoVO> lineaCondition = Condition.filterBy(queryLineaIntervento);
				PbandiDLineaDiInterventoVO lineaResult = genericDAO.findFirstWhere(lineaCondition);
				if(lineaResult != null && lineaResult.getIdLineaDiIntervento() != null){					
					return lineaResult.getIdLineaDiIntervento().longValue();
				}else{
					return null;
				}
			}else{
				return null;
			}			
		}catch(Exception e){
			String msg = "Errore nell'ottenimento del id linea di intervento :" + e.getMessage();
			logger.error(msg, e);
			return null;
			//throw new CertificazioneException(msg, e);
		} finally{
			logger.end();
		}		
	}
	
	public byte[] generaReportChiusuraConti(PbandiTPropostaCertificazVO propostaCertificazVO, 
			Long idUtente, String identitaDigitale) throws Exception, IOException {
		logger.info(" BEGIN");
		
		byte[] reportCC = null;
		
		// File master si trova in:
		// 		conf/report/xls_templates/nReportChiusuraConti.xls
		
		String templateKey = it.csi.pbandi.pbwebcert.util.Constants.TEMPLATE_REPORT_CHIUSURA_CONTI;
		logger.info("Popolo il template " + templateKey);

		String nomeBandoLinea = null;
		String codiceProgetto = null;
		String denominazioneBeneficiario = null;
		Long idLineaDiIntervento = null;
		
		ProgettoCertificazioneIntermediaFinaleDTO[] pcif = findProgettiCertificazioneIntermediaFinaleCC(idUtente, identitaDigitale, 
				propostaCertificazVO.getIdPropostaCertificaz().longValue(), nomeBandoLinea, codiceProgetto, denominazioneBeneficiario, idLineaDiIntervento);
		
		List<ProgettoCertificazioneIntermediaFinaleDTO> elementiReport =  Arrays.asList(pcif);
		
		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);
		
		reportCC = TableWriter.writeTableToByteArrayCC(templateKey, new ExcelDataWriter(propostaCertificazVO.getIdPropostaCertificaz().toString()),
						elementiReport, linesToJump);
		
		if(reportCC!=null)
			logger.info("reportCC.length="+reportCC.length);
		else
			logger.info("reportCC NULL");
		
		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		
		////////////////////////////////////
		// Foglio "Testata"
		Map<String, Object> testataVars = new HashMap<String, Object>();
		
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		testataVars.put("idPropostaCertificaz", propostaCertificazVO.getIdPropostaCertificaz());
		
		BandoPropostaCertificazUnionPeriodoVO bp = new BandoPropostaCertificazUnionPeriodoVO();
		bp.setIdPropostaCertificaz(propostaCertificazVO.getIdPropostaCertificaz());
		
		bp = genericDAO.findSingleOrNoneWhere(bp);
		logger.info(" bp = "+bp.toString());
		
		testataVars.put("descProposta", bp.getDescProposta());
		testataVars.put("dtOraCreazione", simpleDateFormat.format(bp.getDtOraCreazione()));
		testataVars.put("dtAnnoContabile", bp.getDescPeriodoVisualizzata());
				
		// estraggo il valore da inserire nel campo PROGRAMMA
		BandoLineaInterventoCertificazioneVO bb = new BandoLineaInterventoCertificazioneVO();
		bb.setIdPropostaCertificaz(propostaCertificazVO.getIdPropostaCertificaz());
		
		bb = genericDAO.findSingleOrNoneWhere(bb);
		logger.info(" bb = "+bb.toString());
		
		testataVars.put("descProgramma", bb.getDescLinea());
		
		testataVars.put("dtChiusuraConti", simpleDateFormat.format(new Date()));
		singleCellVars.put("Testata", testataVars);

		////////////////////////////////////
		// Foglio "Dettaglio Chiusura Conti"
		singlePageTables.put("Dettaglio Chiusura Conti", reportCC);
		
		////////////////////////////////////
		// Foglio "Per Asse"
		Map<String, Object> perAsseVVars = new HashMap<String, Object>();
		
		final String IMPORTO_CERTNETTOANN_PREFIX = "importoCertNettAnn_";
		final String IMPORTO_COLONNAC_PREFIX = "importoColonnaC_";
		final String IMPORTO_DIFFCNA_PREFIX = "importoDiffCNA_";
		final String IMPORTO_DIFFREV_PREFIX = "importoDiffREV_";
		final String IMPORTO_DIFFREC_PREFIX = "importoDiffREC_";
		final String IMPORTO_DIFFSOPPR_PREFIX = "importoDiffSoppr_";
		
		// prepopolo i valori delle pagina "Per Asse"
		for (String key : new String[] { "I", "II", "III", "IV" , "V" , "VI" , "VII"}) {
			perAsseVVars.put(IMPORTO_CERTNETTOANN_PREFIX + key, new BigDecimal(0));
			perAsseVVars.put(IMPORTO_COLONNAC_PREFIX + key, new BigDecimal(0));
			perAsseVVars.put(IMPORTO_DIFFCNA_PREFIX + key, new BigDecimal(0));
			perAsseVVars.put(IMPORTO_DIFFREV_PREFIX + key, new BigDecimal(0));
			perAsseVVars.put(IMPORTO_DIFFREC_PREFIX + key, new BigDecimal(0));
			perAsseVVars.put(IMPORTO_DIFFSOPPR_PREFIX + key, new BigDecimal(0));
		}
		
		// calcolo le somme
		for (ProgettoCertificazioneIntermediaFinaleDTO ele : elementiReport) {
			
			String asse = ele.getAsse();
			logger.info( "asse="+asse + ", progetto="+ele.getCodiceProgetto());
			
			BigDecimal impCNA = beanUtil.transform(ele.getImportoCertifNettoAnnual(),BigDecimal.class); 
			BigDecimal tmpImpCNA = (BigDecimal)perAsseVVars.get(IMPORTO_CERTNETTOANN_PREFIX + asse);
			perAsseVVars.put(IMPORTO_CERTNETTOANN_PREFIX + asse, tmpImpCNA.add(impCNA));
			
			BigDecimal impColonnaC = beanUtil.transform(ele.getColonnaC(),BigDecimal.class);
			BigDecimal tmpImpColC = (BigDecimal)perAsseVVars.get(IMPORTO_COLONNAC_PREFIX + asse);
			perAsseVVars.put(IMPORTO_COLONNAC_PREFIX + asse, tmpImpColC.add(impColonnaC));
			
			BigDecimal impDiffCna = beanUtil.transform(ele.getDiffCna(),BigDecimal.class);
			BigDecimal tmpDiffCna = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFCNA_PREFIX + asse);
			perAsseVVars.put(IMPORTO_DIFFCNA_PREFIX + asse, tmpDiffCna.add(impDiffCna));
			
			BigDecimal impDiffRev = beanUtil.transform(ele.getDiffRev(),BigDecimal.class);
			BigDecimal tmpDiffRev = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFREV_PREFIX + asse);
			perAsseVVars.put(IMPORTO_DIFFREV_PREFIX + asse, tmpDiffRev.add(impDiffRev));
			
			BigDecimal impDiffRec = beanUtil.transform(ele.getDiffRec(),BigDecimal.class);
			BigDecimal tmpDiffRec = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFREC_PREFIX + asse);
			perAsseVVars.put(IMPORTO_DIFFREC_PREFIX + asse, tmpDiffRec.add(impDiffRec));
			
			BigDecimal impDiffSop = beanUtil.transform(ele.getDiffSoppr(),BigDecimal.class);
			BigDecimal tmpDiffSop = (BigDecimal)perAsseVVars.get(IMPORTO_DIFFSOPPR_PREFIX + asse);
			perAsseVVars.put(IMPORTO_DIFFSOPPR_PREFIX + asse, tmpDiffSop.add(impDiffSop));
			
		}
		
		singleCellVars.put("Per Asse", perAsseVVars);
		logger.info("singleCellVars popolate");
		
		// genero l'excell
		reportCC = reportManager.getMergedDocumentFromTemplate(templateKey, singlePageTables, singleCellVars);
		
		logger.info(" END");
		return reportCC;
	}
	
	private ProgettoCertificazioneIntermediaFinaleDTO[] findProgettiCertificazioneIntermediaFinaleCC(
			Long idUtente, String identitaDigitale, Long idProposta,
			String nomeBandoLinea, String codiceProgetto,
			String denominazioneBeneficiario, Long idLineaDiIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			CertificazioneException {
		logger.info("BEGIN");
		
		try{
			String[] parameterToCheck = {"idUtente", "identitaDigitale", "idProposta"}; 
			ValidatorInput.verifyNullValue(parameterToCheck, idUtente, identitaDigitale, idProposta);
		
			DettPropostaCertifAnnualCCVO query = new DettPropostaCertifAnnualCCVO();
			query.setIdPropostaCertificaz(new BigDecimal(idProposta));
			query.setCodiceProgetto(StringUtil.isEmpty(codiceProgetto) ? null : codiceProgetto);
			query.setNomeBandoLinea(StringUtil.isEmpty(nomeBandoLinea) ? null: nomeBandoLinea);
			query.setBeneficiario(StringUtil.isEmpty(denominazioneBeneficiario) ? null : denominazioneBeneficiario);			
			
			Condition<DettPropostaCertifAnnualCCVO> condizione = Condition.filterBy(query);
			List<DettPropostaCertifAnnualCCVO> result = genericDAO.findListWhere(condizione);
			
			logger.info("END");
			return (ProgettoCertificazioneIntermediaFinaleDTO[]) beanUtil.transform(result, ProgettoCertificazioneIntermediaFinaleDTO.class);
		}catch(Exception ex){
			logger.error("Errore durante dell'esecuzione di findProgettiCertificazioneIntermediaFinale: " + ex);
			throw new CertificazioneException("Errore durante dell'esecuzione di findProgettiCertificazioneIntermediaFinale: " + ex);
		}
	}

	private byte[] generaReportPorFesr2021_27(
			PbandiTPropostaCertificazVO propostaCertificazVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport,
			String descBreveLinea,
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception, IOException {

		logger.info(" BEGIN 2021_2027");

		byte[] reportPropostaCertificazioneFileData;
		// conf/report/xls_templates/nReportPropostaCertificazionePorFesr2014_20.xls
		String templateKey = TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR2021_27;
		logger.info("Popolo il template " + templateKey);

		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);
		reportPropostaCertificazioneFileData = TableWriter
				.writeTableToByteArray(templateKey, new ExcelDataWriter(
						propostaCertificazVO.getIdPropostaCertificaz().toString()), elementiReport, linesToJump);
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);


		// Jira PBANDI-2882: foglio Percettoti.
		logger.info("\n foglio Percettori \n");
		String templateKeyPercettori = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR_PERCETTORI;
		byte[] reportPropostaCertificazioneFileDataPercettori;
		reportPropostaCertificazioneFileDataPercettori = TableWriter
				.writeTableToByteArray(templateKeyPercettori, new ExcelDataWriter(
						propostaCertificazVO.getIdPropostaCertificaz().toString()+"Percettori"), elementiPercettori, linesToJump);
		ReportPropostaCertificazionePorFesrVO elementoPercettori = new ReportPropostaCertificazionePorFesrVO();
		if (elementiPercettori.size() > 0) {
			elementoPercettori = elementiPercettori.get(0);
		}
		Map<String, Object> testataVarsPercettori = beanUtil.transformToMap(elementoPercettori);
		// Jira PBANDI-2882: foglio Percettoti - fine.

		PbandiDLineaDiInterventoVO porFesrVO = new PbandiDLineaDiInterventoVO();
		porFesrVO.setDescBreveLinea(descBreveLinea);

		//Importo Anticipo
		ImportiAnticipoPropostaCertificazioneVO importiVO = new ImportiAnticipoPropostaCertificazioneVO();
		importiVO.setIdPropostaCertificaz(propostaCertificazVO.getIdPropostaCertificaz());

		//******* PREFIXES **************************************
		final String IMPORTO_ANTICIPO_PREFIX = "importoAnticipo";
		final String IMPORTO_NONCOPERTO_PREFIX = "importoNonCoperto";
		final String DIFF_IMPORTI_ANTICIPO_PREFIX = "diffAnticipoImpNonCop";
		final String IMPORTI_PAGVALIDATINETTI_PREFIX = "importoPagValidatiNetti";
		final String IMPORTO_CONTRIBUTIVERSATI_PREFIX = "importoContributiVersati";
		final String IMPORTO_CONTRIBUTOPUBBLICO_PREFIX = "importoContributoPubblico";


		//Inserire gli assi in una costante
		Map<String, BigDecimal> asseLineaInterventoMap = new HashMap<String, BigDecimal>();
		asseLineaInterventoMap.put("PRI" , 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_I);
		asseLineaInterventoMap.put("PRII", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_II);
		asseLineaInterventoMap.put("PRIII", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_III);
		asseLineaInterventoMap.put("PRIV", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_IV);
		asseLineaInterventoMap.put("PRV", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_V);
		asseLineaInterventoMap.put("PRVI", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_VI);
		asseLineaInterventoMap.put("PRVII", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_VII);

		Map<String, Object> allegatoVVars = new HashMap<String, Object>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> anticipoMap = new HashMap<String, BigDecimal>();
		List<ImportiAnticipoPropostaCertificazioneVO> anticipi = genericDAO.where(importiVO).select();

		Map<String, BigDecimal> importoCertificazioneNettoMap = new HashMap<String, BigDecimal>();
		String queryImportoNettoAndImportoPubblicoConc = fileSqlUtil.getQuery("GenerazioneExcelCertificazione20212027.sql");
		List<AllegatoVExcel20212027VO> resultQuery20212017 = getPbandiCertificazioneDAO().getJdbcTemplate().query(queryImportoNettoAndImportoPubblicoConc, new AllegatoVExcel20212027RowMapper());

		for(AllegatoVExcel20212027VO allegatoVExcel20212027VO : resultQuery20212017) {
			String asse = allegatoVExcel20212027VO.getDescBreveLinea();
			importoCertificazioneNettoMap.put(asse, allegatoVExcel20212027VO.getImportoCertificazioneNetto());
		}

		for (String key : asseLineaInterventoMap.keySet()) {

			BigDecimal idLineaDiIntervento = asseLineaInterventoMap.get(key);
			BigDecimal value = null;

			for(ImportiAnticipoPropostaCertificazioneVO anticipo : anticipi){
				if(anticipo.getIdLineaDiIntervento().compareTo(idLineaDiIntervento) == 0){
					value = anticipo.getImportoAnticipo();
				}
			}

			anticipoMap.put(key, value);
		}

		// cerco i dettagli delle proposte di certificazioni appartenenti a progetti SIFP
		List<Map> proposteSIFList = pbandiCertificazioneDAO.findProposteSIF(propostaCertificazVO.getIdPropostaCertificaz());
		logger.info(" proposteSIFList="+proposteSIFList);
		List<BigDecimal> listaII = null;

		// popolo una lista con gli id dei progetti SIF per l'id certificazione dato
		if(proposteSIFList!=null && !proposteSIFList.isEmpty()){

			listaII = new ArrayList<BigDecimal>();
			for (Map mappa : proposteSIFList) {
				logger.info(" ID_PROGETTO="+mappa.get("ID_PROGETTO"));
				listaII.add((BigDecimal)mappa.get("ID_PROGETTO"));
			}
		}

		//impCertNettiMap = sommo colonna AW (ImportiCertificabiliNetti) per Asse
		Map<String, BigDecimal> impCertNettiMap = new HashMap<String, BigDecimal>();

		// impPagValidazMap = somma colonna AE (ImportoPagamentiValidati) per Asse
		// TODO : dubbio : questo valore è corretto o devo prendere ImportoPagValidatiOrig?
		Map<String, BigDecimal> impPagValidazMap = new HashMap<String, BigDecimal>();

		// impEccedValidazMap = somma colonna AF (ImportoEccendenzeValidazione) per Asse
		Map<String, BigDecimal> impEccedValidazMap = new HashMap<String, BigDecimal>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> sommaValoriNMap = new HashMap<String, BigDecimal>();

		for (ReportPropostaCertificazionePorFesrVO el : elementiReport) {

			String asse = el.getDescAsse();

			if(listaII!=null && listaII.contains( el.getIdProgetto())){

				BigDecimal imp = el.getImpCertificabileNettoSoppr();

				logger.info(" SIF: Asse Progetto IdLineaDiIntervento= ["+asse+"] ["+el.getIdProgetto()+"] ["+el.getIdLineaDiIntervento()+"] ");

//				BigDecimal tmpA = impCertNettiMap.get(asse);
//				if(tmpA!=null){
//					BigDecimal somA = imp.add(tmpA);
//					impCertNettiMap.put(asse, somA);
//				}else{
//					impCertNettiMap.put(asse, imp);
//				}

				BigDecimal impB = el.getImportoPagamentiValidati();
				BigDecimal tmpB = impPagValidazMap.get(asse);
				if(tmpB!=null){
					BigDecimal somB = impB.add(tmpB);
					impPagValidazMap.put(asse, somB);
				}else{
					impPagValidazMap.put(asse, impB);
				}

				BigDecimal impC = el.getImportoEccendenzeValidazione();
				BigDecimal tmpC = impEccedValidazMap.get(asse);
				if(tmpB!=null){
					BigDecimal somC = impC.add(tmpC);
					impEccedValidazMap.put(asse, somC);
				}else{
					impEccedValidazMap.put(asse, impC);
				}
			}else{
				logger.info(" NON SIF: Asse Progetto IdLineaDiIntervento= ["+asse+"] ["+el.getIdProgetto()+"] ["+el.getIdLineaDiIntervento()+"] ");
			}

			// Jira PBANDI-2882: al posto di SommaValoriN ora si usa ValoreN.
			//BigDecimal impD = el.getSommaValoriN();
			BigDecimal impD = el.getValoreN();
			BigDecimal tmpD = sommaValoriNMap.get(asse);
			if(tmpD!=null){
				BigDecimal somD = impD.add(tmpD);
				sommaValoriNMap.put(asse, somD);
				logger.info(" somD="+somD);
			}else{
				sommaValoriNMap.put(asse, impD);
			}

		}
		logger.info(" impCertNettiMap="+impCertNettiMap);
		logger.info(" impPagValidazMap="+impPagValidazMap);
		logger.info(" impEccedValidazMap="+impEccedValidazMap);
		logger.info(" sommaValoriNMap="+sommaValoriNMap);

		for (String key : new String[] { "PRI", "PRII", "PRIII", "PRIV" , "PRV" , "PRVI" , "PRVII"}) {

			BigDecimal valueSomm = new BigDecimal(0);
			BigDecimal valueAnt = new BigDecimal(0);

			if(sommaValoriNMap!=null && sommaValoriNMap.containsKey(key) && sommaValoriNMap.get(key)!=null){
				logger.info(" asse ="+key+" , sommaValoriNMap.get(key)="+sommaValoriNMap.get(key));
				valueSomm = valueSomm.add(sommaValoriNMap.get(key));
			}

			if(anticipoMap!=null && anticipoMap.containsKey(key) && anticipoMap.get(key)!=null){
				logger.info(" asse ="+key+" , anticipoMap.get(key)="+anticipoMap.get(key));
				valueAnt = valueAnt.add(anticipoMap.get(key));
			}

			logger.info(" asse ="+key+" , valueSomm ="+valueSomm+" , valueAnt="+valueAnt);

			//Sostituzione placeholders con nuovi valori certificazione 2021 2027
			if (importoCertificazioneNettoMap.get(key) != null) {
				allegatoVVars.put(IMPORTO_CONTRIBUTIVERSATI_PREFIX + key, importoCertificazioneNettoMap.get(key));
				allegatoVVars.put(IMPORTO_CONTRIBUTOPUBBLICO_PREFIX + key, importoCertificazioneNettoMap.get(key));
			} else {
				allegatoVVars.put(IMPORTO_CONTRIBUTIVERSATI_PREFIX + key, new BigDecimal(0));
				allegatoVVars.put(IMPORTO_CONTRIBUTOPUBBLICO_PREFIX  + key, new BigDecimal(0));
			}


			// valorizzo la colonna "C" = "Importo complessivo versato come anticipo...."
			allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key, valueSomm);

			// valorizzo la colonna "E" = "Importo che non e' stato coperto dalle spese....."
			if(valueAnt.compareTo(valueSomm)<=0){
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueAnt);
			}else{
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueSomm);
			}

			// valorizzo la colonna "D" = "C" - "E"
			// calcolo la differenza tra IMPORTO_ANTICIPO_PREFIX e IMPORTO_NONCOPERTO_PREFIX
			BigDecimal diffA = new BigDecimal(0);
			BigDecimal diffB = new BigDecimal(0);

			if(allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key)!=null)
				diffA = diffA.add((BigDecimal)allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key));

			if(allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key)!=null)
				diffB = diffB.add((BigDecimal)allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key));

			BigDecimal diffe = diffA.subtract(diffB);
			logger.info(" asse ="+key+" , diffA="+diffA+" ,diffB ="+diffB+" , diffe=" +diffe);
			allegatoVVars.put(DIFF_IMPORTI_ANTICIPO_PREFIX + key, diffe);



			// impPagValidazMap = somma colonna AE per Asse
			// impEccedValidazMap = somma colonna AF per Asse

			BigDecimal AE = impPagValidazMap.get(key);
			logger.info(" AE="+AE);

			if(AE!=null){
				BigDecimal AF ;
				if(impEccedValidazMap.get(key)!=null){
					AF = impEccedValidazMap.get(key);
				}else{
					AF = new BigDecimal(0);
				}
				BigDecimal diff = AE.subtract(AF);
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, diff);
			}else{
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, new BigDecimal(0));
			}
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Allegato V", allegatoVVars);

		singlePageTables.put("Percettori", reportPropostaCertificazioneFileDataPercettori);

		reportPropostaCertificazioneFileData = reportManager.getMergedDocumentFromTemplate(templateKey, singlePageTables,	singleCellVars);

		logger.info(" END");
		return reportPropostaCertificazioneFileData;
	}


}
