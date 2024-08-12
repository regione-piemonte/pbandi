package it.csi.pbandi.pbweb.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbweb.dto.AttivitaAtecoDTO;
import it.csi.pbandi.pbweb.dto.AttivitaAtecoNodoDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
//import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.dto.EsitoRicercaFornitori;
import it.csi.pbandi.pbweb.dto.FatturaRiferimentoDTO;
import it.csi.pbandi.pbweb.dto.Fornitore;
import it.csi.pbandi.pbweb.dto.FornitoreFormDTO;
import it.csi.pbandi.pbweb.dto.NazioneDTO;
import it.csi.pbandi.pbweb.dto.QualificaFormDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.FornitoreDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificheRequest;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.EsitoOperazioneInserimento;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.EsitoOperazioneModifica;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.QualificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionefornitori.GestioneFornitoriException;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.ErrorMessages;
import it.csi.pbandi.pbweb.util.RequestUtil;
import it.csi.pbandi.pbweb.util.StringUtil;
import it.csi.pbandi.pbweb.util.TraduttoreMessaggiPbandisrv;
import it.csi.pbandi.pbweb.util.ValidatorCodiceFiscale;
import it.csi.pbandi.pbweb.util.ValidatorPartitaIva;

@Service
public class FornitoreDAOImpl extends JdbcDaoSupport implements FornitoreDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	DocumentoDiSpesaDAOImpl documentoDiSpesaDAOImpl;
	
	@Autowired
	//protected ArchivioFileDAOImpl archivioFileDAOImpl;
	protected it.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl archivioFileDAOImpl;
	
	@Autowired
	protected DecodificheDAOImpl decodificheDAOImpl;
	
	@Autowired
	protected GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
	
	@Autowired
	private it.csi.pbandi.pbweb.pbandisrv.business.gestionefornitori.GestioneFornitoriBusinessImpl gestioneFornitoriBusinessImpl;
	
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentidispesa.GestioneDocumentiDiSpesaBusinessImpl gestioneDocumentiDiSpesaBusinessImp;
	
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;
	
	@Autowired
	public FornitoreDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	// Servizio di prova per testare il rollback.
	//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {InvalidParameterException.class, Exception.class})
	//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class, InvalidParameterException.class, Exception.class})
	@Transactional(rollbackFor = {InvalidParameterException.class, Exception.class})
	public Boolean testTransactional (HttpServletRequest request) throws RuntimeException, InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::testTransactional] ";
		LOG.info(prf + " BEGIN 3");
			
			StringBuilder sql = new StringBuilder();
			sql.append("update PBANDI_R_FORNITORE_QUALIFICA set DT_FINE_VALIDITA = SYSDATE where PROGR_FORNITORE_QUALIFICA = 54807");
			logger.info("\n"+sql);
			
			getJdbcTemplate().update(sql.toString(), new Object[] {});
			
			if (2 > 1)
				throw new Exception("EXCEPTION FINTA");
				//throw new RuntimeException("EXCEPTION FINTA");
			
		return true;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public Boolean testTransactionalJUnit() throws RuntimeException, InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::testTransactional] ";
		LOG.info(prf + " BEGIN 3");
			
			StringBuilder sql = new StringBuilder();
			sql.append("update PBANDI_R_FORNITORE_QUALIFICA set DT_FINE_VALIDITA = SYSDATE where PROGR_FORNITORE_QUALIFICA = 54807");
			logger.info("\n"+sql);
			
			getJdbcTemplate().update(sql.toString(), new Object[] {});
			
			if (2 > 1)
				throw new DaoException("DaoException FINTA");
				//throw new InvalidParameterException("InvalidParameterException FINTA");
				//throw new Exception("Exception FINTA");
				//throw new RuntimeException("RuntimeException FINTA");
			
		return true;
	}

	@Override
	// popolaSezioneAteco() in fornitore.js 
	public ArrayList<AttivitaAtecoNodoDTO> alberoAttivitaAteco(long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::alberoAttivitaAteco] ";
		LOG.info(prf + " BEGIN");
		
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		ArrayList<AttivitaAtecoNodoDTO> result = new ArrayList<AttivitaAtecoNodoDTO>();
		try {
			
			AttivitaAtecoNodoDTO prevCodLevel1 = null;
			AttivitaAtecoNodoDTO prevCodLevel2 = null;
			AttivitaAtecoNodoDTO prevCodLevel3 = null;
			AttivitaAtecoNodoDTO prevCodLevel4 = null;
			
			ArrayList<AttivitaAtecoDTO> listaAteco = this.attivitaAteco(idUtente, request);
			for (AttivitaAtecoDTO ateco : listaAteco) {
				
				Long livello = ateco.getLivello();						
				String[] actualCod = ateco.getCodAteco().split("\\.");
				
				//LOG.info("ATECO CORRENTE: codAteco "+ateco.getCodAteco()+"; livello = "+ateco.getLivello()+"; actualCod.length = "+actualCod.length);
				
				AttivitaAtecoNodoDTO nodo = this.creaNodo(ateco);
				
				if (livello.intValue() == 1) {
					result.add(nodo);
					prevCodLevel1 = nodo;
					prevCodLevel2 = null;
					prevCodLevel3 = null;
					prevCodLevel4 = null;					
				} else if (actualCod.length == 2) {
					// può essere di livello 2 o 3
					if (actualCod[1].length() < 2) {						
						prevCodLevel1.getFigli().add(nodo);
						prevCodLevel2 = nodo;
						prevCodLevel3 = null;
						prevCodLevel4 = null;
					} else {
						prevCodLevel2.getFigli().add(nodo);
						prevCodLevel3 = nodo;
						prevCodLevel4 = null;
					}
				} else if (actualCod.length == 3) {
					// Può essere di livello 4 oppure foglia finale (se c'e' 0 lo 00 è foglia finale).
					if (actualCod[2].equals("0")) {
						prevCodLevel3.getFigli().add(nodo);
						prevCodLevel4 = nodo;
					} else if (actualCod[2].equals("00")) {
						if (prevCodLevel4 != null) {
							prevCodLevel4.getFigli().add(nodo);
						} else {
							prevCodLevel3.getFigli().add(nodo);
						}
					} else {						
						if (ateco.getLivello().intValue() == 5) {
							//LOG.info("livello = 5 con prevCodLevel4 = "+(prevCodLevel4 == null ? "null" : prevCodLevel4.getCodAteco())+"; e prevCodLevel3 = "+(prevCodLevel3 == null ? "null" : prevCodLevel3.getCodAteco()));
							if (prevCodLevel4 != null) {
								prevCodLevel4.getFigli().add(nodo);
							} else {
								prevCodLevel3.getFigli().add(nodo);
							}
							
						} else {
							prevCodLevel3.getFigli().add(nodo);
							prevCodLevel4 = nodo;
						}						
					}
				}				
			}

		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca delle ativita ateco.: ", e);
			throw new Exception("Errore durante la ricerca delle ativita ateco.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	/* versione più vicina al javascript originale, in futuro da cancellare.
	@Override
	// popolaSezioneAteco() in fornitore.js 
	public ArrayList<AttivitaAtecoNodoDTO> alberoAttivitaAteco(long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::alberoAttivitaAteco] ";
		LOG.info(prf + " BEGIN");
		
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		ArrayList<AttivitaAtecoNodoDTO> result = new ArrayList<AttivitaAtecoNodoDTO>();
		try {
			
			
			String prevCodLevel1 = "";
			String prevCodLevel2 = "";
			String prevCodLevel3 = "";
			String prevCodLevel4 = "";
			ArrayList<AttivitaAtecoDTO> listaAteco = this.attivitaAteco(idUtente, request);
			for (AttivitaAtecoDTO ateco : listaAteco) {
				
				Long livello = ateco.getLivello();						
				String[] actualCod = ateco.getCodAteco().split("\\.");
				
				LOG.info("ATECO CORRENTE: codAteco "+ateco.getCodAteco()+"; livello = "+ateco.getLivello()+"; actualCod.length = "+actualCod.length);
				
				if (livello.intValue() == 1) {
					prevCodLevel1 = ateco.getCodAteco();
					prevCodLevel2 = "";
					prevCodLevel3 = "";
					prevCodLevel4 = "";
					result.add(this.creaNodo(ateco));
				} else if (actualCod.length == 2) {
					// può essere di livello 2 o 3
					if (actualCod[1].length() < 2) {
						AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel1, result);
						nodo.getFigli().add(this.creaNodo(ateco));
						prevCodLevel2 = ateco.getCodAteco();
						prevCodLevel3 = "";
						prevCodLevel4 = "";
					} else {
						AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel2, result);
						nodo.getFigli().add(this.creaNodo(ateco));
						prevCodLevel3 = ateco.getCodAteco();
						prevCodLevel4 = "";
					}
				} else if (actualCod.length == 3) {
					// Può essere di livello 4 oppure foglia finale (se c'e' 0 lo 00 è foglia finale).
					if (actualCod[2].equals("0")) {
						AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel3, result);
						nodo.getFigli().add(this.creaNodo(ateco));
						prevCodLevel4 = ateco.getCodAteco();
					} else if (actualCod[2].equals("00")) {
						if (!StringUtil.isEmpty(prevCodLevel4)) {
							AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel4, result);
							nodo.getFigli().add(this.creaNodo(ateco));
						} else {
							AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel3, result);
							nodo.getFigli().add(this.creaNodo(ateco));
						}
					} else {						
						if (ateco.getLivello().intValue() == 5) {
							LOG.info("livello = 5 con prevCodLevel4 = "+prevCodLevel4+"; e prevCodLevel3 = "+prevCodLevel3);
							//AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel4, result);
							//nodo.getFigli().add(this.creaNodo(ateco));
							if (!StringUtil.isEmpty(prevCodLevel4)) {
								AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel4, result);
								nodo.getFigli().add(this.creaNodo(ateco));
							} else {
								AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel3, result);
								nodo.getFigli().add(this.creaNodo(ateco));
							}
						} else {
							AttivitaAtecoNodoDTO nodo = trovaNodo(prevCodLevel3, result);
							nodo.getFigli().add(this.creaNodo(ateco));
							prevCodLevel4 = ateco.getCodAteco();
						}						
					}
				}				
			}

		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca delle ativita ateco.: ", e);
			throw new Exception("Errore durante la ricerca delle ativita ateco.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
		private AttivitaAtecoNodoDTO trovaNodo(String codAteco, ArrayList<AttivitaAtecoNodoDTO> albero) {
		String[] spitInput = codAteco.split("\\.");
		for (AttivitaAtecoNodoDTO nodo : albero) {
			if (spitInput[0].equals("01"))
				LOG.info("Confronto nodo "+codAteco+" con nodo "+nodo.getCodAteco());			
			if (nodo.getCodAteco().equals(codAteco)) {
				// Il nodo corrente è quello cercato.
				return nodo;
			}
			// Se si è nel ramo giusto, cerco tra i figli (cerco "01.1" solo nel ramo "01").
			String[] spitNodo = nodo.getCodAteco().split("\\.");
			if (spitInput[0].equals(spitNodo[0])) {
				AttivitaAtecoNodoDTO nodoFiglio = trovaNodo(codAteco, nodo.getFigli());
				if (nodoFiglio != null)
					return nodoFiglio;
			}
		}
		return null;
	}
	*/
	
	private AttivitaAtecoNodoDTO creaNodo(AttivitaAtecoDTO ateco) {
		if (ateco == null) {
			LOG.info("Creato nodo nullo");
			return null;	
		}
		AttivitaAtecoNodoDTO nodo = new AttivitaAtecoNodoDTO();
		nodo.setIdAttivitaAteco(ateco.getIdAttivitaAteco());
		nodo.setCodAteco(ateco.getCodAteco());
		nodo.setCodDescAteco(ateco.getCodDescAteco());
		nodo.setFigli(new ArrayList<AttivitaAtecoNodoDTO>());
		//LOG.info("Aggiunto nodo "+nodo.getCodAteco());
		return nodo;
	}
	
	/*
	@Override
	// popolaSezioneAteco() in fornitore.js 
	public ArrayList<AttivitaAtecoNodoDTO> alberoAttivitaAteco(long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception {
		String prf = "[DecodificheDAOImpl::alberoAttivitaAteco] ";
		LOG.info(prf + " BEGIN");
		
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		ArrayList<AttivitaAtecoNodoDTO> result = new ArrayList<AttivitaAtecoNodoDTO>();
		try {
			
			ArrayList<AttivitaAtecoDTO> listaAteco = this.attivitaAteco(idUtente, request);
			
			for (AttivitaAtecoDTO ateco : listaAteco) {
				int livelloAlberatora = 1;
				inserisciNodoInAlberaturaAteco(livelloAlberatora, ateco, result);
			}

		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca in PBANDI_D_FORMA_GIURIDICA: ", e);
			throw new Exception("Errore durante la ricerca delle ativita ateco.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	private void inserisciNodoInAlberaturaAteco(int livelloAlberatura, AttivitaAtecoDTO ateco, ArrayList<AttivitaAtecoNodoDTO> alberaturaAteco) {
		String codiceAtecoDaInserire = ateco.getCodAteco();
		LOG.info("codiceAtecoDaInserire = "+codiceAtecoDaInserire);		
		int i = StringUtil.trovaOccorrenza(codiceAtecoDaInserire, '.', livelloAlberatura);
		LOG.info("i = "+i);
		String codiceAtecoParziale = codiceAtecoDaInserire;
		if (i > -1)
			codiceAtecoParziale = codiceAtecoDaInserire.substring(0, i);
		LOG.info("codiceAtecoParziale = "+codiceAtecoParziale);
		boolean trovato = false;
		for (AttivitaAtecoNodoDTO nodo : alberaturaAteco) {
			if (nodo.getCodAteco().equals(codiceAtecoParziale)) {
				// Trovato un nodo con lo stesso codice ateco parziale: proseguo con i figli.
				inserisciNodoInAlberaturaAteco(livelloAlberatura + 1, ateco, nodo.getFigli());
				break;
			}
		}
		if (!trovato)
			alberaturaAteco.add(this.creaNodo(ateco));
	}
	*/

	@Override
	// ex gestioneDatiDiDominioSrv.findAttivitaAteco()
	public ArrayList<AttivitaAtecoDTO> attivitaAteco(long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::attivitaAteco] ";
		LOG.info(prf + " BEGIN");
		
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		ArrayList<AttivitaAtecoDTO> result = new ArrayList<AttivitaAtecoDTO>();
		try {
			
			String idIride = RequestUtil.idIrideInSessione(request);
			
			// Servizio pbandisrv.
			if (gestioneDatiDiDominioBusinessImpl == null)
				LOG.error(prf+"gestioneDatiDiDominioBusinessImpl NULLO");
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.AttivitaAtecoDTO[] listaAtecoPbandisrv = null;
			listaAtecoPbandisrv = gestioneDatiDiDominioBusinessImpl.findAttivitaAteco(idUtente, idIride);
			
			// Da dto di pbandisrv a dto di pbweb.
			result = beanUtil.transformToArrayList(listaAtecoPbandisrv, AttivitaAtecoDTO.class);
			//ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.AttivitaAtecoDTO> l = beanUtil.transformToArrayList(listaAtecoPbandisrv, it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.AttivitaAtecoDTO.class);

		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca delle ativita ateco: ", e);
			throw new Exception("Errore durante la ricerca delle ativita ateco.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	

	@Override
	// ex FornitoreAction.salva()
	@Transactional(rollbackFor = {Exception.class})
	public EsitoDTO salvaFornitore(FornitoreFormDTO fornitoreFormDTO, long idUtente, long idSoggettoBeneficiario, HttpServletRequest request) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::salvaFornitore] ";
		LOG.info(prf + " BEGIN");
		
		if (fornitoreFormDTO == null) {
			throw new InvalidParameterException("fornitore non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idSoggettoBeneficiario == 0) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		
		LOG.info(fornitoreFormDTO.toString());
		
		EsitoDTO esito = new EsitoDTO();
		try {
			
			String idIride = RequestUtil.idIrideInSessione(request);
			
			esito = validaFormFornitore(fornitoreFormDTO, idUtente, idIride);
			if (!esito.getEsito()) {
				return esito;
			}
			
			boolean generaCF = ("S".equalsIgnoreCase(fornitoreFormDTO.getCfAutoGenerato()));
			FornitoreDTO fornitore = this.popolaFornitoreDTO(fornitoreFormDTO, idSoggettoBeneficiario);
			
			if (fornitore.getIdFornitore() != null) {				
				LOG.info(prf + "Modifico fornitore con id = "+fornitore.getIdFornitore());
				try {					 
					EsitoOperazioneModifica esitoModifica = gestioneFornitoriBusinessImpl
							.modificaFornitoreSemplificazione(idUtente, idIride, fornitore, generaCF);
					esito.setEsito(esitoModifica.getEsito());
					esito.setId(esitoModifica.getIdFornitore());
					LOG.info(prf + "ID fornitore modificato = "+esito.getId()+" (potrebbe essere un id nuovo in caso di storicizzazione).");
					if (esitoModifica.getMessaggi() != null && esitoModifica.getMessaggi().length > 0)
						esito.setMessaggio(esitoModifica.getMessaggi()[0]);					
				} catch (Exception e) {
					LOG.error(prf+"Errore durante la modifica di un fornitore.",e);
					esito.setEsito(false);
					esito.setMessaggio(e.getMessage());
				}
			} else {
				LOG.info(prf + "Inserisco il fornitore.");
				try {
					EsitoOperazioneInserimento esitoInserimento = gestioneFornitoriBusinessImpl
							.inserisciFornitoreSemplificazione(idUtente, idIride, fornitore, generaCF);
					esito.setEsito(esitoInserimento.getEsito());
					if (esitoInserimento.getMessaggi() != null && esitoInserimento.getMessaggi().length > 0)
						esito.setMessaggio(esitoInserimento.getMessaggi()[0]);
					if (esitoInserimento.getFornitore() != null)
						esito.setId(esitoInserimento.getFornitore().getIdFornitore());
					LOG.info(prf + "ID fornitore inserito = "+esito.getId());
				} catch (Exception e) {
					LOG.error(prf+"Errore durante l'inserimento di un fornitore.",e);
					esito.setEsito(false);
					esito.setMessaggio(e.getMessage());
				}
			}
			if (esito.getEsito())
				esito.setMessaggio("I dati del fornitore sono stati correttamente aggiornati.");
			LOG.info(esito.toString());
							
		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante il salvataggio del fornitore: ", e);
			throw new Exception("Errore durante il salvataggio del fornitore.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	private FornitoreDTO popolaFornitoreDTO (FornitoreFormDTO fornitoreFormDTO, long idSoggettoBeneficiario) {
		
		Long idTipoSoggetto = fornitoreFormDTO.getIdTipoSoggetto();
		Long idAttivitaAteco = fornitoreFormDTO.getIdAttivitaAteco();
		Long idNazione = fornitoreFormDTO.getIdNazione();
		
		FornitoreDTO fornitore = new FornitoreDTO();
		fornitore.setIdFornitore(fornitoreFormDTO.getIdFornitore());
		fornitore.setIdTipoSoggetto(idTipoSoggetto);
		fornitore.setCodiceFiscaleFornitore(fornitoreFormDTO.getCodiceFiscaleFornitore());
		fornitore.setIdSoggettoFornitore(idSoggettoBeneficiario);
		fornitore.setFlagPubblicoPrivato(fornitoreFormDTO.getFlagPubblicoPrivato());

		if (idTipoSoggetto.intValue() == Constants.ID_TIPOLOGIA_PERSONA_GIURIDICA) {
			fornitore.setAltroCodice(fornitoreFormDTO.getAltroCodice());
			fornitore.setDenominazioneFornitore(fornitoreFormDTO.getDenominazioneFornitore());
			fornitore.setPartitaIvaFornitore(fornitoreFormDTO.getPartitaIvaFornitore());
			fornitore.setIdFormaGiuridica(fornitoreFormDTO.getIdFormaGiuridica());
			if (idAttivitaAteco != null && idAttivitaAteco.intValue() != 0)
				fornitore.setIdAttivitaAteco(idAttivitaAteco);
			if (idNazione != null &&  idNazione.intValue() != 0)
				fornitore.setIdNazione(idNazione);
			if ("S".equalsIgnoreCase(fornitoreFormDTO.getCfAutoGenerato()) && 
				StringUtil.isEmpty(fornitoreFormDTO.getAltroCodice())) {
				fornitore.setAltroCodice(fornitoreFormDTO.getAltroCodice());
			}
			if (fornitoreFormDTO.getFlagPubblicoPrivato().intValue() == Constants.FLAG_FORMA_GIURIDICA_PUBBLICA){
				fornitore.setCodUniIpa(fornitoreFormDTO.getCodUniIpa());
			}			
		} else {
			fornitore.setNomeFornitore(fornitoreFormDTO.getNomeFornitore());
			fornitore.setCognomeFornitore(fornitoreFormDTO.getCognomeFornitore());
		}
		
		return fornitore;
	}
	
//	private FornitoreFormDTO popolaFornitoreFormDTO (FornitoreDTO fornitoreDTO) {
	private FornitoreFormDTO popolaFornitoreFormDTO (Fornitore fornitoreDTO) {
		
		Long idTipoSoggetto = fornitoreDTO.getIdTipoSoggetto();
		Long idAttivitaAteco = fornitoreDTO.getIdAttivitaAteco();
		Long idNazione = fornitoreDTO.getIdNazione();
		
		FornitoreFormDTO fornitoreForm = new FornitoreFormDTO();
		fornitoreForm.setIdFornitore(fornitoreDTO.getIdFornitore());
		fornitoreForm.setIdTipoSoggetto(idTipoSoggetto);
		fornitoreForm.setCodiceFiscaleFornitore(fornitoreDTO.getCodiceFiscaleFornitore());
		fornitoreForm.setFlagPubblicoPrivato(fornitoreDTO.getFlagPubblicoPrivato());

		if (idTipoSoggetto.intValue() == Constants.ID_TIPOLOGIA_PERSONA_GIURIDICA) {
			fornitoreForm.setAltroCodice(fornitoreDTO.getAltroCodice());
			fornitoreForm.setDenominazioneFornitore(fornitoreDTO.getDenominazioneFornitore());
			fornitoreForm.setPartitaIvaFornitore(fornitoreDTO.getPartitaIvaFornitore());
			fornitoreForm.setIdFormaGiuridica(fornitoreDTO.getIdFormaGiuridica());
			if (idAttivitaAteco != null && idAttivitaAteco.intValue() != 0)
				fornitoreForm.setIdAttivitaAteco(idAttivitaAteco);
			if (idNazione != null &&  idNazione.intValue() != 0)
				fornitoreForm.setIdNazione(idNazione);
			fornitoreForm.setAltroCodice(fornitoreDTO.getAltroCodice());			
			if (fornitoreDTO.getFlagPubblicoPrivato().intValue() == Constants.FLAG_FORMA_GIURIDICA_PUBBLICA){
				fornitoreForm.setCodUniIpa(fornitoreDTO.getCodUniIpa());
			}			
		} else {
			fornitoreForm.setNomeFornitore(fornitoreDTO.getNomeFornitore());
			fornitoreForm.setCognomeFornitore(fornitoreDTO.getCognomeFornitore());
		}
		
		fornitoreForm.setDocumentiAllegati(fornitoreDTO.getDocumentiAllegati());
		
		return fornitoreForm;
	}
	
	private EsitoDTO validaFormFornitore(FornitoreFormDTO fornitore, long idUtente, String idIride) {
		String prf = "[FornitoreDAOImpl::validaFormFornitore] ";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(true);
		
		Long idTipoSoggetto = fornitore.getIdTipoSoggetto();
		String codiceFiscale = fornitore.getCodiceFiscaleFornitore();
		String partitaIva = fornitore.getPartitaIvaFornitore();
		
		if (idTipoSoggetto != null) {
			if (idTipoSoggetto.intValue() == Constants.ID_TIPOLOGIA_PERSONA_FISICA) {
				esito = validaPersonaFisica(codiceFiscale, partitaIva);
			} else if (idTipoSoggetto.equals(Constants.ID_TIPOLOGIA_PERSONA_GIURIDICA)) {
				esito = validaEnteGiuridico(fornitore);
			}
		}
		if (!esito.getEsito())
			return esito;
		
		// PBANDI-2760
		Long idFornitore = fornitore.getIdFornitore();
		if (idFornitore != null) {
			try {
				if (gestioneFornitoriBusinessImpl.isFornitoreAssociatoAdAffidamenti(idUtente, idIride, new Long(idFornitore))) {
									esito.setEsito(false);
					esito.setMessaggio(ErrorMessages.FORNITORE_ASSOCIATO_AFFIDAMENTO);
					return esito;
				}
			} catch (Exception e) {
				logger.error("Exception e="+e.getMessage());
			}
		}
		LOG.info(prf + " END");
		return esito;
	}
	
	private EsitoDTO validaPersonaFisica(String codiceFiscale, String partitaIva) {
		String prf = "[FornitoreDAOImpl::validaPersonaFisica] ";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(true);
		
		try {
				this.checkCodiceFiscale(Constants.ID_TIPOLOGIA_PERSONA_FISICA, codiceFiscale);
			} catch (Exception e) {
				esito.setEsito(false);
				esito.setMessaggio(ErrorMessages.CODICE_FISCALE_FORMALMENTE_SCORRETTO);
				return esito;
			}

		if (!StringUtil.isEmpty(partitaIva)) {
			try {
				this.checkPartitaIva(partitaIva);
			} catch (Exception e) {
				esito.setEsito(false);
				esito.setMessaggio(e.getMessage());
				return esito;
			}
		}
		
		LOG.info(prf + " END");
		return esito;

	}

	private void checkCodiceFiscale(int idTipoSoggetto, String codiceFiscale) throws Exception {
		if (idTipoSoggetto == Constants.ID_TIPOLOGIA_PERSONA_FISICA) {
			ValidatorCodiceFiscale.checkLength(codiceFiscale);
			ValidatorCodiceFiscale.checkCodiceControllo(codiceFiscale);
		} else {
			if (codiceFiscale.length() == 11)
				ValidatorCodiceFiscale.controllaCodiceFiscalePersonaGiuridica(codiceFiscale);
			else if (codiceFiscale.length() == 16) {
				ValidatorCodiceFiscale.checkLength(codiceFiscale);
				ValidatorCodiceFiscale.checkCodiceControllo(codiceFiscale);
			} else {
				throw new Exception("Lunghezza Codice fiscale "+codiceFiscale+" non corretta");
			}
		}
	}
	
	private void checkPartitaIva(String partitaIva) throws Exception {
		if (partitaIva != null && !partitaIva.equals(""))
			ValidatorPartitaIva.controllaPartitaIVA(partitaIva);
	}
	
	private EsitoDTO validaEnteGiuridico(FornitoreFormDTO fornitore) {
		String prf = "[FornitoreDAOImpl::validaEnteGiuridico] ";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(true);
				
		String partitaIva = fornitore.getPartitaIvaFornitore();
		if (!StringUtil.isEmpty(partitaIva)) {
			try {
				this.checkPartitaIva(partitaIva);
			} catch (Exception e) {
				esito.setEsito(false);
				esito.setMessaggio(e.getMessage());
				return esito;
			}
		}
		
		LOG.info(prf + " END");
		return esito;
	}
	
	@Override
	// ex pbandiweb.GestioneFornitoriBusinessImpl.findFornitoreById() 
	public FornitoreFormDTO cercaFornitore (Long idFornitore, Long idSoggettoBeneficiario, Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::cercaFornitore] ";
		LOG.info(prf + " BEGIN");
		
		if (idFornitore == 0) {
			throw new InvalidParameterException("idFornitore non valorizzato.");
		}		
		if (idSoggettoBeneficiario == 0) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		FornitoreFormDTO result = new FornitoreFormDTO();
		try {
			
			FornitoreDTO filtro = new FornitoreDTO();
			filtro.setIdFornitore(idFornitore);
			
			// Sostituito poichè non restituisce l'elenco degli allegati del fornitore.
			//ArrayList<FornitoreDTO> lista = this.ricercaElencoFornitoriSemplificazione(filtro, idSoggettoBeneficiario, idUtente, idIride);
			
			// Dati fornitore + suoi allegati.
			EsitoRicercaFornitori esito = ricercaElencoFornitori(filtro, idSoggettoBeneficiario, idProgetto, idUtente, idIride);
			if (!esito.getEsito())
				throw new Exception(esito.getMessaggio());
				
			ArrayList<Fornitore> lista = esito.getFornitori();
			
			if (lista == null || lista.size() == 0) {
				LOG.warn(prf+"Nessun fornitore trovato.");
			}
			
			result = this.popolaFornitoreFormDTO(lista.get(0));
							
		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca del fornitore: ", e);
			throw new Exception("Errore durante la ricerca del fornitore.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	// Ricerca dei fornitori in Gestione Fornitori. 
	public EsitoRicercaFornitori ricercaElencoFornitori(FornitoreDTO filtro, Long idSoggettoBeneficiario, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::ricercaElencoFornitori] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idSoggettoBeneficiario = "+idSoggettoBeneficiario+"; idUtente = "+idUtente);		
				
		if (filtro == null) {
			throw new InvalidParameterException("filtro non valorizzato.");
		}
		
		if (idSoggettoBeneficiario == null) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		EsitoRicercaFornitori esito = new EsitoRicercaFornitori (); 
		try {			
			
			ArrayList<FornitoreDTO> fornitoriPbandisrv = new ArrayList<FornitoreDTO>();
			try {				
				fornitoriPbandisrv = this.ricercaElencoFornitoriSemplificazione(filtro,idSoggettoBeneficiario,idUtente,idIride);
			} catch (GestioneFornitoriException e) {
				LOG.error(prf+"GestioneFornitoriException: "+e.getMessage());
				esito.setEsito(false);
				esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(e.getMessage()));
				return esito;
			}
			
			ArrayList<Fornitore> result = new ArrayList<Fornitore>();
			if (fornitoriPbandisrv == null) {
				LOG.warn(prf+"ricercaElencoFornitoriSemplificazione ha restituito fornitoriPbandisrv = NULL.");
			} else {
				for (FornitoreDTO dto : fornitoriPbandisrv) {
					result.add(this.rimappaObjFornitoreClient(dto));
				}
			}
			
			// NB: questa parte corrisponde a CPBESGestioneFornitori.aggiungiDocumentiAssociatiRicercaFornitori().
			
			// per ogni fornitore trovato, estraggo i documenti del fornitore e verifico se ne esiste almeno uno associato
			// ad una "dichiarazione di spesa" in stato NON "dichiarabile"
			
			// Jira PBANDI-2890: aggiunto test su PBANDI_T_FILE_ENTITA.DT_ASSOCIAZIONE.
			// il vecchio controllo su "dichiarabile" si fa solo se DT_ASSOCIAZIONE è nulla.
			
			for (Fornitore forn : ObjectUtil.lazyIterator(result)) {
				
				List<DocumentoAllegatoDTO> docs;
				docs = documentoDiSpesaDAOImpl.allegatiFornitore(forn.getIdFornitore(), idProgetto, idUtente, idIride);
				
				// Visualizza o meno l'icona per disassociare i documenti (Jira PBANDI-2683).
				if(docs!=null && !docs.isEmpty()){

					// Data dell'ultima DS del progetto, con almeno 1 doc di spesa associato al fornitore corrente.
					Date dataDichiarazioneMax = this.dataDichiarazioneMax(idProgetto, forn.getIdFornitore());
					
					boolean isDisass = gestioneFornitoriBusinessImpl.isAllegatoFornitoreDisassociabile( idUtente, idIride, forn.getIdFornitore(), idProgetto);					
					LOG.info(prf+"fornitore="+forn.getIdFornitore()+", isDisass="+isDisass);
					
					for (DocumentoAllegatoDTO documentoAllegato : docs) {
						if (documentoAllegato.getDtAssociazione() == null) {
							// Vecchio test pre Jira PBANDI-2890. 
							documentoAllegato.setDisassociabile(isDisass);
							LOG.info(prf+"Doc allegato "+documentoAllegato.getId()+" : DtAssociazione = null, quindi assegno isDisass.");
						} else {
							// Nuovo test Jira PBANDI-2890.
							boolean disasociabile = (dataDichiarazioneMax == null || dataDichiarazioneMax.compareTo(documentoAllegato.getDtAssociazione()) < 0);
							documentoAllegato.setDisassociabile(disasociabile);
							LOG.info(prf+"Doc allegato "+documentoAllegato.getId()+" : nuovo controllo: assegno disasociabile = "+disasociabile);
						}
					}
				}
				
				ArrayList<DocumentoAllegatoDTO> arrayDocs = new ArrayList<DocumentoAllegatoDTO>();
				for (DocumentoAllegatoDTO documentoAllegato : docs) {
					arrayDocs.add(documentoAllegato);
				}

				forn.setDocumentiAllegati(arrayDocs);
				forn.setIsLinkAllegaFileVisible(true);

			}
			
			esito.setEsito(true);
			esito.setMessaggio(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
			esito.setFornitori(result);
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca dei fornitori: ", e);
			esito.setEsito(false);
			esito.setMessaggio(ErrorMessages.ERRORE_GENERICO);
			//throw new Exception("Errore durante la ricerca dei fornitori.");			
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	} 
	
	private Date dataDichiarazioneMax(Long idProgetto, Long idFornitore) {
		LOG.info("dataDichiarazioneMax(): idProgetto = "+idProgetto+"; idFornitore = "+idFornitore);
		String sql = "SELECT MAX(ds.DT_DICHIARAZIONE) ";
		sql += "FROM PBANDI_T_DICHIARAZIONE_SPESA ds , PBANDI_R_PAG_QUOT_PARTE_DOC_SP pqpds, PBANDI_T_QUOTA_PARTE_DOC_SPESA qpds, PBANDI_T_DOCUMENTO_DI_SPESA doc ";
		sql += "WHERE ds.ID_PROGETTO = "+idProgetto+" ";
		sql += "AND pqpds.ID_DICHIARAZIONE_SPESA = ds.ID_DICHIARAZIONE_SPESA ";
		sql += "AND qpds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa ";
		sql += "AND doc.ID_DOCUMENTO_DI_SPESA = qpds.ID_DOCUMENTO_DI_SPESA ";
		sql += "AND doc.ID_FORNITORE = "+idFornitore+" ";
		LOG.info("\n"+sql);			
		Date dt = (Date) getJdbcTemplate().queryForObject(sql, Date.class);
		if (dt == null) {
			LOG.info("dataDichiarazioneMax(): dataDichiarazioneMax = NULL");
		} else {
			try { 
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				LOG.info("dataDichiarazioneMax(): dataDichiarazioneMax = " + sdf.format(dt));
			} catch (Exception e) {
				LOG.error("dataDichiarazioneMax(): errore nella formattazione della data "+dt);
			}
		}
		return dt;
	}
	
	private Fornitore rimappaObjFornitoreClient(FornitoreDTO fornitoreDTO) {
		Fornitore fornitore = new Fornitore();
		if (fornitoreDTO != null) {
			fornitore.setIdFornitore(fornitoreDTO.getIdFornitore());
			fornitore.setIdSoggettoFornitore(fornitoreDTO
					.getIdSoggettoFornitore());
			fornitore.setCodiceFiscaleFornitore(fornitoreDTO
					.getCodiceFiscaleFornitore());
			fornitore.setCognomeFornitore(fornitoreDTO.getCognomeFornitore());
			fornitore.setNomeFornitore(fornitoreDTO.getNomeFornitore());
			fornitore.setDenominazioneFornitore(fornitoreDTO
					.getDenominazioneFornitore());
			fornitore.setPartitaIvaFornitore(fornitoreDTO
					.getPartitaIvaFornitore());

			if (!StringUtils.isEmpty(fornitoreDTO.getAltroCodice())) {
				fornitore.setPartitaIvaFornitore(fornitoreDTO.getAltroCodice());
			}

			if (fornitoreDTO.getIdTipoSoggetto() != null
					&& fornitoreDTO.getIdTipoSoggetto().longValue() > 0) {
				fornitore.setIdTipoSoggetto(fornitoreDTO.getIdTipoSoggetto());
				fornitore.setDescTipoSoggetto(fornitoreDTO
						.getDescTipoSoggetto());
			}
			if (fornitoreDTO.getIdQualifica() != null) {
				fornitore.setIdQualifica(fornitoreDTO.getIdQualifica());
				fornitore.setDescQualifica(fornitoreDTO.getDescQualifica());
			}

			fornitore.setMonteOre(fornitoreDTO.getMonteOre());
			fornitore.setCostoOrario(fornitoreDTO.getCostoOrario());
			fornitore.setCostoRisorsa(fornitoreDTO.getCostoRisorsa());
			fornitore.setIdFormaGiuridica(fornitoreDTO.getIdFormaGiuridica());
			fornitore.setIdAttivitaAteco(fornitoreDTO.getIdAttivitaAteco());
			fornitore.setIdNazione(fornitoreDTO.getIdNazione());
			fornitore.setAltroCodice(fornitoreDTO.getAltroCodice());
			fornitore.setCodUniIpa(fornitoreDTO.getCodUniIpa());
			fornitore.setFlagPubblicoPrivato(fornitoreDTO
					.getFlagPubblicoPrivato());
		}
		return fornitore;
	}
	
	private ArrayList<FornitoreDTO> ricercaElencoFornitoriSemplificazione(
			FornitoreDTO filtro, long idSoggettoBeneficiario, long idUtente, String idIride) throws Exception {
		String prf = "[FornitoreDAOImpl::ricercaElencoFornitoriSemplificazione] ";
		LOG.info(prf + " BEGIN");
				
		filtro.setIdSoggettoFornitore(idSoggettoBeneficiario);
		
		ArrayList<FornitoreDTO> listRet = new ArrayList<FornitoreDTO>();
		FornitoreDTO[] list = gestioneFornitoriBusinessImpl.findFornitori(idUtente, idIride, filtro);
		
		LOG.info(prf+"list.length = "+list.length);
		
		Long idFornitore = null;
		FornitoreDTO dtoPrec = null;

		for (FornitoreDTO dto : list) {

			if (dto.getCodiceFiscaleFornitore() != null
					&& dto.getCodiceFiscaleFornitore().startsWith("PBANDI")) {
				dto.setCodiceFiscaleFornitore("N.d.");
			}
			if (idFornitore == null) {
				idFornitore = dto.getIdFornitore();
				dtoPrec = dto;
			}
			if (idFornitore.compareTo(dto.getIdFornitore()) != 0) {
				// Inserimento del fornitore precedente nella lista.
				listRet.add(dtoPrec);
				dtoPrec = dto;
				idFornitore = dto.getIdFornitore();
			}
		}

		// Inserimento dell' ultimo fornitore della lista.
		if (dtoPrec != null) {
			listRet.add(dtoPrec);
		}

		if (listRet.isEmpty()) {
			listRet = null;
			LOG.warn(prf+"Elenco fornitori restituiti NULLO.");
		} else {
			LOG.info(prf+"Num fornitori restituiti: "+listRet.size());
		}
		
		LOG.info(prf+" END");
		return listRet;
	}
	
	// Ex GestioneFornitoriBusinessImpl.findQualificheSemplificazione()
	public ArrayList<QualificaDTO> qualificheFornitore (long idFornitore, long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::qualificheFornitore] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idFornitore = "+idFornitore+"; idUtente = "+idUtente);
				
		if (idFornitore == 0) {
			throw new InvalidParameterException("idFornitore non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		ArrayList<QualificaDTO> result = new ArrayList<QualificaDTO>();
		try {
			String idIride = RequestUtil.idIrideInSessione(request);
			QualificaDTO[] lista = gestioneFornitoriBusinessImpl.findQualificheSemplificazione(idUtente, idIride, idFornitore);
			result = beanUtil.transformToArrayList(lista, QualificaDTO.class);
		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca delle qualifiche fornitore: ", e);
			throw new Exception("Errore durante la ricerca delle qualifiche del fornitore.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	} 
	
	@Override
	// ex QualificaFornitoreAction.salva()
	@Transactional(rollbackFor = {Exception.class})
	public EsitoDTO salvaQualifica(SalvaQualificaRequest salvaQualificaRequest, HttpServletRequest request) throws InvalidParameterException, Exception {	
		String prf = "[FornitoreDAOImpl::salvaQualifica] ";
		LOG.info(prf + " BEGIN");
		
		if (salvaQualificaRequest == null) {
			throw new InvalidParameterException("salvaQualificaRequest non valorizzata.");
		}
		
		QualificaFormDTO qualificaFormDTO = salvaQualificaRequest.getQualificaFormDTO();
		
		Long idUtente = salvaQualificaRequest.getIdUtente();
		LOG.info(prf + "idUtente = "+idUtente);
		
		if (qualificaFormDTO == null) {
			throw new InvalidParameterException("qualifica non valorizzata.");
		}
		if (idUtente == null || idUtente.intValue() == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		LOG.info(qualificaFormDTO.toString());
		EsitoDTO esito = new EsitoDTO();
		try {
			
			String idIride = RequestUtil.idIrideInSessione(request);
			
			QualificaDTO qualifica = beanUtil.transform(qualificaFormDTO, QualificaDTO.class);
			
			if (qualifica.getProgrFornitoreQualifica() != null) {
				LOG.info(prf + "Modifico qualifica con progressivo = "+qualifica.getProgrFornitoreQualifica());
				try {					 
					EsitoOperazioneModifica esitoModifica = gestioneFornitoriBusinessImpl
							.modificaQualificaSemplificazione(idUtente, idIride, qualifica);
					esito.setEsito(esitoModifica.getEsito());
					if (esitoModifica.getMessaggi() != null && esitoModifica.getMessaggi().length > 0)
						esito.setMessaggio(esitoModifica.getMessaggi()[0]);					
				} catch (Exception e) {
					logger.error(prf+"Errore durante la modifica di una qualifica.",e);
					esito.setEsito(false);
					esito.setMessaggio(e.getMessage());
				}
			} else {
				LOG.info(prf + "Inserisco la qualifica.");
					EsitoOperazioneInserimento esitoInserimento = gestioneFornitoriBusinessImpl
							.inserisciQualificaSemplificazione(idUtente, idIride, qualifica);
					esito.setEsito(esitoInserimento.getEsito());
					if (esitoInserimento.getMessaggi() != null && esitoInserimento.getMessaggi().length > 0)
						esito.setMessaggio(esitoInserimento.getMessaggi()[0]);
			}
							
		} catch (Exception e) {
			String msg = "Errore durante il salvataggio della qualifica.";
			LOG.error(prf+msg, e);
			throw new Exception(msg);
		}
		finally {
			LOG.info(esito.toString());
			LOG.info(prf+" END");
		}
				
		return esito;
	}
	
	@Override
	// Inserisce\modifica più qualifiche.
	@Transactional(rollbackFor = {Exception.class})
	public EsitoDTO salvaQualifiche(SalvaQualificheRequest salvaQualificheRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception {	
		String prf = "[FornitoreDAOImpl::salvaQualifiche] ";
		LOG.info(prf + " BEGIN");
		
		if (salvaQualificheRequest == null) {
			throw new InvalidParameterException("salvaQualificheRequest non valorizzata.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		ArrayList<QualificaFormDTO> listaQualificaFormDTO = salvaQualificheRequest.getListaQualificaFormDTO();
		if (listaQualificaFormDTO == null) {
			throw new InvalidParameterException("listaQualificaFormDTO non valorizzata.");
		}
		LOG.info(prf + "idUtente = "+idUtente+"; listaQualificaFormDTO.size = "+listaQualificaFormDTO.size());
				
		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(true);
		esito.setMessaggio("Operazione eseguita con successo.");
		try {
			
			for (QualificaFormDTO qualificaFormDTO : listaQualificaFormDTO) {
				LOG.info(qualificaFormDTO.toString());
				
				QualificaDTO qualifica = beanUtil.transform(qualificaFormDTO, QualificaDTO.class);
				
				if (qualifica.getProgrFornitoreQualifica() != null) {
					LOG.info(prf + "Modifico qualifica con progressivo = "+qualifica.getProgrFornitoreQualifica());					 
					EsitoOperazioneModifica esitoModifica = gestioneFornitoriBusinessImpl
								.modificaQualificaSemplificazione(idUtente, idIride, qualifica);
					esito.setEsito(esitoModifica.getEsito());
				} else {
					LOG.info(prf + "Inserisco la qualifica.");
					EsitoOperazioneInserimento esitoInserimento = gestioneFornitoriBusinessImpl
								.inserisciQualificaSemplificazione(idUtente, idIride, qualifica);
					esito.setEsito(esitoInserimento.getEsito());
				}
				
			}
			
			if (!esito.getEsito())
				esito.setMessaggio("Non tutte le qualifiche sono state salvate.");
		
		} catch (Exception e) {
			String msg = "Errore durante il salvataggio delle qualifiche.";
			LOG.error(prf+msg, e);
			throw new Exception(msg);
		}
		finally {
			LOG.info(esito.toString());
			LOG.info(prf+" END");
		}
				
		return esito;
	}
	
	// ex pbandiweb.GestioneFornitoriBusinessImpl.cercaFornitoreFattElett() 
	public FornitoreFormDTO cercaFornitoreFattElett (String cf, long idSoggettoBeneficiario, long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::cercaFornitoreFattElett] ";
		LOG.info(prf + " BEGIN");
		
		if (cf == null || cf.length() == 0) {
			throw new InvalidParameterException("codice fiscale non valorizzato.");
		}
		if (idSoggettoBeneficiario == 0) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		FornitoreFormDTO result = new FornitoreFormDTO();
		try {
			
			FornitoreDTO filtro = new FornitoreDTO();
			filtro.setIdFormaGiuridica(new Long(Constants.ID_TIPOLOGIA_PERSONA_GIURIDICA));
			filtro.setCodiceFiscaleFornitore(cf);
			filtro.setIdSoggettoFornitore(idSoggettoBeneficiario);
			
			ArrayList<FornitoreDTO> lista = this.ricercaElencoFornitoriSemplificazione(filtro, idSoggettoBeneficiario, idUtente, idIride);
			
			if (lista == null || lista.size() == 0) {
				LOG.info(prf+"Nessun fornitore trovato.");
				return null;
			}
			
			Fornitore f = this.rimappaObjFornitoreClient(lista.get(0));
			result = this.popolaFornitoreFormDTO(f);
			LOG.info(prf+result.toString());
							
		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca del fornitore della fattura elettronica: ", e);
			throw new Exception("Errore durante la ricerca del fornitore della fattura elettronica.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	// Ex pbandiweb GestioneDocumentiDiSpesaBusinessImpl.findFattureFornitore()
	public ArrayList<FatturaRiferimentoDTO> fattureFornitore (long idFornitore, long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::fattureFornitore] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idFornitore = "+idFornitore+"; idProgetto = "+idProgetto);
				
		if (idFornitore == 0) {
			throw new InvalidParameterException("idFornitore non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		ArrayList<FatturaRiferimentoDTO> result = new ArrayList<FatturaRiferimentoDTO>();
		try {
			
			// Richiamo il servizio di pbandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO[] lista = null;
			lista = gestioneDocumentiDiSpesaBusinessImp.findFattureFornitoreSemplificazione(idUtente, idIride, idFornitore, idProgetto);
			
			if (lista != null) {
				for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO dtpPbandisrv : lista) {
					FatturaRiferimentoDTO dto = new FatturaRiferimentoDTO();
					dto.setIdDocumentoDiSpesa(dtpPbandisrv.getIdDocumentoDiSpesa());
					dto.setDescrizione(dtpPbandisrv.getDescTipologiaDocumentoDiSpesa()
							+ " "
							+ dtpPbandisrv.getNumeroDocumento()
							+ " del "
							+ DateUtil.formatDate(dtpPbandisrv.getDataDocumentoDiSpesa()));
					dto.setImportoRendicontabile(dtpPbandisrv.getImportoRendicontabile());
					dto.setImportoTotaleDocumentoIvato(dtpPbandisrv.getImportoTotaleDocumentoIvato());
					dto.setImportoTotaleQuietanzato(dtpPbandisrv.getImportoTotaleQuietanzato());
					//LOG.info(dto.toString());
					result.add(dto);					
				}
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca delle fatture del fornitore: ", e);
			throw new Exception("Errore durante la ricerca delle fatture del fornitore.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	} 
	
	// Ex pbandisrv gestioneDatiDiDominioSrv.findNazioniCompleto()
	public List<NazioneDTO> nazioni (HttpServletRequest request) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::nazioni] ";
		LOG.info(prf + " BEGIN");
		
		List<NazioneDTO> result = new ArrayList<NazioneDTO>();
		try {
			
			String sql = "SELECT FLAG_APPARTENENZA_UE, ID_NAZIONE, COD_ISTAT_NAZIONE, DESC_NAZIONE, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA FROM PBANDI_D_NAZIONE ";
			sql += "WHERE (TRUNC(sysdate) >= NVL(TRUNC(DT_INIZIO_VALIDITA), TRUNC(sysdate)-1) AND TRUNC(sysdate) < NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(sysdate)+1)) ";
			sql += "ORDER BY DESC_NAZIONE ASC";
			LOG.info("\n"+sql);
			
			result =  getJdbcTemplate().query(sql, new BeanRowMapper(NazioneDTO.class));
			LOG.info(prf + "Record trovati = " + result.size());
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la ricerca delle nazioni: ", e);
			throw new Exception("Errore durante la ricerca delle nazioni.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	} 
	
	@Override
	// Ex pbandiweb ArchivioFileAction.dissociate()
	public EsitoDTO disassociaAllegatoFornitore(Long idDocumentoIndex, Long idFornitore, Long idProgetto, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::disassociaAllegatoFornitore] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idFornitore = " + idFornitore + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idFornitore == null) {
			throw new InvalidParameterException("idFornitore non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		EsitoDTO esito = new EsitoDTO();
		try {
			
			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", Constants.ENTITA_T_FORNITORE);
			if (idEntita == null)
				throw new DaoException("Id entita non trovato.");
			
			Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex, idEntita.longValue(), idFornitore, idProgetto);
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMessaggio(esitoPbandisrv.getMessage());
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nel disassociare gli allegati del fornitore: ", e);
			throw new DaoException(" ERRORE nel disassociare gli allegati del fornitore.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Associa gli allegati ad un fornitore.
	public EsitoAssociaFilesDTO associaAllegatiAFornitore(AssociaFilesRequest associaFilesRequest, Long idUtente) 
			throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::associaAllegatiAFornitore] ";
		LOG.info(prf + "BEGIN");
			
		EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
		try {
			
			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", Constants.ENTITA_T_FORNITORE);
			
			esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita.longValue(), idUtente);
			LOG.info(prf+esito.toString());
		
		} catch (InvalidParameterException e) {
			LOG.error(prf+e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nel associare allegati al fornitore: ", e);
			throw new DaoException(" ERRORE nel associare al fornitore.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Elimina un fornitore, fisicamente o storicizzando.
	@Transactional(rollbackFor = {Exception.class})
	public EsitoOperazioneDTO eliminaFornitore(Long idFornitore, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::eliminaFornitore] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idFornitore = "+idFornitore+"; idUtente = "+idUtente);
		
		if (idFornitore == null) {
			throw new InvalidParameterException("idFornitore non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
			
		EsitoOperazioneDTO esito = new EsitoOperazioneDTO();
		try {
			
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.EsitoOperazioneElimina esitoPbandisrv;
			esitoPbandisrv = gestioneFornitoriBusinessImpl.cancellaFornitoreSemplificazione(idUtente, idIride, idFornitore);
			
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMessaggi(TraduttoreMessaggiPbandisrv.traduci(esitoPbandisrv.getMessaggi()));
			LOG.info(prf+esito.toString());
		
		} catch (InvalidParameterException e) {
			LOG.error(prf+e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nel eliminare il fornitore: ", e);
			throw new DaoException(" ERRORE nel eliminare il fornitore.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Ex QualificaFornitoreAction.isDocumentiAssociatiAQualificaFornitore()
	public Boolean esistonoDocumentiAssociatiAQualificaFornitore(Long progrFornitoreQualifica, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::esistonoDocumentiAssociatiAQualificaFornitore] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idFornitore = "+progrFornitoreQualifica+"; idUtente = "+idUtente);
		
		if (progrFornitoreQualifica == null) {
			throw new InvalidParameterException("progrFornitoreQualifica non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
			
		Boolean esito = new Boolean(true);
		try {
			
			esito = gestioneFornitoriBusinessImpl.isDocumentiAssociatiAQualificaFornitore(idUtente, idIride, progrFornitoreQualifica);
			LOG.info(prf+"esito = "+esito);
		
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca di documenti associati alla qualifica: ", e);
			throw new DaoException(" ERRORE nella ricerca di documenti associati alla qualifica.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Ex QualificaFornitoreAction.cancella
	public EsitoDTO eliminaQualifica(Long progrFornitoreQualifica, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[FornitoreDAOImpl::eliminaQualifica] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idFornitore = "+progrFornitoreQualifica+"; idUtente = "+idUtente);
		
		if (progrFornitoreQualifica == null) {
			throw new InvalidParameterException("progrFornitoreQualifica non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
			
		EsitoDTO esito = new EsitoDTO();
		try {
			
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.EsitoOperazioneElimina esitoPbandisrv;
			esitoPbandisrv = gestioneFornitoriBusinessImpl.cancellaQualificaSemplificazione(idUtente, idIride, progrFornitoreQualifica);
			
			esito.setEsito(esitoPbandisrv.getEsito());
			if (esitoPbandisrv.getMessaggi() != null && esitoPbandisrv.getMessaggi().length > 0)
				esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitoPbandisrv.getMessaggi()[0]));
			LOG.info(prf+"esito = "+esito);
		
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella eliminazione della qualifica: ", e);
			esito.setEsito(false);
			esito.setMessaggio(ErrorMessages.ERRORE_GENERICO);
			//throw new DaoException(" ERRORE nella  eliminazione della qualifica.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
}
