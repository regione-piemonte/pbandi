package it.csi.pbandi.pbweb.pbandisrv.business.gestionedatigenerali;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.CertificazioneManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.RevocaRecuperoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.richiestaerogazione.RichiestaErogazioneBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.AttivitaPregresseDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.CoordinateBancarieDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiCumuloDeMinimisDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DelegatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DichiarazioneSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.EconomiaPerDatiGeneraliDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.EsitoOperazioneSalvaTipoAllegati;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoAgevolatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.NotificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.RigoAttivitaPregresseDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.SedeDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException;
import it.csi.pbandi.pbweb.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AllegatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AttivitaPregresseVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioEnteGiuridicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DelegatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EconomiaPerDatiGeneraliVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneCausaleModalitaAgevolazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EstremiBancariVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ImportoImpegnoAppaltiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.LiquidazioneModalitaAgevolazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RigoContoEconomicoProgettoCulturaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RigoContoEconomicoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SedeProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SedeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoFinanziatoreProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TotaleQuietanzatoValidatoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.recupero.RecuperoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca.ModalitaAgevolazioneTotaleRevocheVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca.RevocaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca.TotaleRevocaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.LikeStartsWithCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCausaleErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMacroSezioneModuloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDichSpesaDocAllegVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocProtocolloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDomandaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTNotificaProcessoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWProgettoDocAllegVO;
//import it.csi.pbandi.pbweb.pbandisrv.integration.services.moni.MoniDAO;
//import it.csi.pbandi.pbweb.pbandisrv.integration.services.moni.exception.CumuloDeMinimisException;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatigenerali.GestioneDatiGeneraliSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class GestioneDatiGeneraliBusinessImpl extends BusinessImpl implements
		GestioneDatiGeneraliSrv {

	@Autowired
	private CertificazioneManager certificazioneManager;
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	private ProgettoManager progettoManager;
	@Autowired
	private RevocaRecuperoManager revocaRecuperoManager;
	@Autowired
	private SedeManager sedeManager;
	@Autowired
	private SoggettoManager soggettoManager;
	@Autowired
	private RichiestaErogazioneBusinessImpl richiestaErogazioneBusiness;

	public CertificazioneManager getCertificazioneManager() {
		return certificazioneManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public void setCertificazioneManager(
			CertificazioneManager certificazioneManager) {
		this.certificazioneManager = certificazioneManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public RevocaRecuperoManager getRevocaRecuperoManager() {
		return revocaRecuperoManager;
	}

	public void setRevocaRecuperoManager(
			RevocaRecuperoManager revocaRecuperoManager) {
		this.revocaRecuperoManager = revocaRecuperoManager;
	}

	public void setSedeManager(SedeManager sedeManager) {
		this.sedeManager = sedeManager;
	}

	public SedeManager getSedeManager() {
		return sedeManager;
	}
	
	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	@Autowired
	private GenericDAO genericDAO;
	
//	private MoniDAO moniDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

//	public void setMoniDAO(MoniDAO moniDAO) {
//		this.moniDAO = moniDAO;
//	}
//
//	public MoniDAO getMoniDAO() {
//		return moniDAO;
//	}

	public RichiestaErogazioneBusinessImpl getRichiestaErogazioneBusiness() {
		return richiestaErogazioneBusiness;
	}

	public void setRichiestaErogazioneBusiness(
			RichiestaErogazioneBusinessImpl richiestaErogazioneBusiness) {
		this.richiestaErogazioneBusiness = richiestaErogazioneBusiness;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO caricaDatiGenerali(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idProgetto, java.lang.Long idSoggettoBeneficiario)
			throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException,
			FormalParameterException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };

		DatiGeneraliDTO datiGeneraliDTO = null;

	 
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);

		datiGeneraliDTO = caricaDatiGenerali(idProgetto);


		return datiGeneraliDTO;

		 
	}

	
	private BigDecimal getIdProcesso(Long progrBandoLineaIntervento) {
		PbandiRBandoLineaInterventVO pbandiRBandoLineaInterventVO=new PbandiRBandoLineaInterventVO();
		pbandiRBandoLineaInterventVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
		pbandiRBandoLineaInterventVO= genericDAO.findSingleWhere(pbandiRBandoLineaInterventVO);
		BigDecimal idProcesso = pbandiRBandoLineaInterventVO.getIdProcesso();
		logger.info("idProcesso --> "+ idProcesso);
		return idProcesso;
	}
	
	private DatiGeneraliDTO caricaDatiGenerali(Long idProgetto) throws GestioneDatiGeneraliException {

		Beneficiario beneficiario = progettoManager.getBeneficiario(idProgetto);

		Long idSoggettoBeneficiario = beneficiario.getId_soggetto();

		/*
		 * utente autorizzato, carico i dati generali
		 */
		DatiGeneraliDTO datiGeneraliDTO;
		ProgettoBandoLineaVO progettoBandoLineaVO = new ProgettoBandoLineaVO();
		progettoBandoLineaVO.setIdProgetto(new BigDecimal(idProgetto));
		progettoBandoLineaVO = genericDAO.findSingleWhere(progettoBandoLineaVO);
		datiGeneraliDTO = beanUtil.transform(progettoBandoLineaVO,DatiGeneraliDTO.class);
		datiGeneraliDTO.setCodiceProgetto(progettoBandoLineaVO.getCodiceVisualizzato());
		BigDecimal idProcesso = getIdProcesso(progettoBandoLineaVO.getIdBandoLinea());
		if(idProcesso!=null)datiGeneraliDTO.setIdProcesso(idProcesso.longValue());
		
		PbandiDLineaDiInterventoVO pbandiDLineaDiInterventoVO=progettoManager.getLineaDiInterventoNormativa(new BigDecimal(progettoBandoLineaVO.getIdBandoLinea()));// idBandoLinea qui � il progrBandoLineaIntervento !!
		datiGeneraliDTO.setDescBreveLineaNormativa(pbandiDLineaDiInterventoVO.getDescBreveLinea());
		datiGeneraliDTO.setIdLineaDiIntervento(pbandiDLineaDiInterventoVO.getIdLineaDiIntervento().longValue());
		
		// Cultura.
		boolean isBandoCultura = (pbandiDLineaDiInterventoVO.getIdProcesso() != null && pbandiDLineaDiInterventoVO.getIdProcesso().intValue() == 3);
		datiGeneraliDTO.setIsBandoCultura(isBandoCultura);
		logger.info("descBreveLineaNormativa per idProgetto "+idProgetto+"; isBandoCultura = "+isBandoCultura+"\n\n\n\n\n");
		
		/*
		 * Verifico il capofila
		 */
		BigDecimal idProgettoB = NumberUtil.toBigDecimal(idProgetto);

		datiGeneraliDTO.setIsCapofila(progettoManager.isCapofila(idProgettoB));

		datiGeneraliDTO
		.setDtPresentazioneDomanda(caricaDtPresentazioneDomanda(progettoBandoLineaVO));

		datiGeneraliDTO.setImportoAgevolato(contoEconomicoManager
				.findImportoAgevolato(idProgetto));

		datiGeneraliDTO.setDescrizioneBeneficiario(progettoManager
				.getDenominazioneBeneficiario(idProgettoB));

		datiGeneraliDTO.setIdDimensioneImpresa(beneficiario
				.getIdDimensioneImpresa());

		datiGeneraliDTO.setIdFormaGiuridica(beneficiario.getIdFormaGiuridica());



		/*
		 * Sede Intervento
		 */
		String descSedeIntervento = "";
		String pIvaSedeIntervento = "";
		try {
			SedeVO sedeIntervento = sedeManager.findSedeIntervento(idProgetto, idSoggettoBeneficiario);
			
			 String descIndirizzo = sedeIntervento.getDescIndirizzo();
			 if(isEmpty(descIndirizzo)) descIndirizzo="";			
			
			 String cap = sedeIntervento.getCap();
			 if(isEmpty(cap)) cap="";	
			 else cap+=" ";
			 
			 if(!isEmpty(descIndirizzo) && !isEmpty(cap))
				 descSedeIntervento=descIndirizzo +" - "+cap;
			 
			 String descComune = sedeIntervento.getDescComune();
			 if(isEmpty(descComune)) descComune="";	
			 
			 String siglaProvincia = sedeIntervento.getSiglaProvincia();
			 if(isEmpty(siglaProvincia)) siglaProvincia="";	
			 
			 if(!isEmpty(descComune) )
				 descSedeIntervento+=descComune;
			 if(!isEmpty(siglaProvincia) )	 
				 descSedeIntervento+="(" +siglaProvincia+")";
				 
			
			pIvaSedeIntervento = sedeIntervento.getPartitaIva();
		} catch (Exception e) {
			logger.warn("Eccezione gestita. Non trovata la sede di intervento per il progetto["+idProgetto+"] ed il beneficiario["+idSoggettoBeneficiario+"]" + e.getMessage());
		}
		datiGeneraliDTO.setSedeIntervento(descSedeIntervento);
		datiGeneraliDTO.setPIvaSedeIntervento(pIvaSedeIntervento);



		/*
		 * Sede legale
		 */
		String descSedeLegale = "";
		String pIvaSedeLegale = "";
		try {
			SedeProgettoVO sedeLegale = sedeManager.findSedeLegale(idProgetto, idSoggettoBeneficiario);
			
			 String descIndirizzo = sedeLegale.getDescIndirizzo();
	 
			
			 if(isEmpty(descIndirizzo)) descIndirizzo="";			 			 
			
			 String cap = sedeLegale.getCap();
			 if(isEmpty(cap)) cap="";	
			 else cap+=" ";
			 
			// PBANDI-2408 inizio
			
			// codice originale commentato
			// if(!isEmpty(descIndirizzo) && !isEmpty(cap))
			//	 descSedeLegale=descIndirizzo +" - "+cap;
			
			// Nel costruire la descrizione della sede legale, 
			// considera anche il numero civico, ma solo se l'indirizzo non finisce gia' con un numero.
			// In generale, quindi, descSedeLegale = indirizzo + civico + " - " + cap;  
			if (!StringUtil.isEmpty(descIndirizzo) && !StringUtil.isEmpty(cap)) {
				descSedeLegale = descIndirizzo;
				if (!StringUtil.isEmpty(sedeLegale.getCivico())) {
					descIndirizzo = descIndirizzo.trim();
					String ultimoChar = String.valueOf(descIndirizzo.charAt(descIndirizzo.length()-1));
					if (!NumberUtil.isNumber(ultimoChar)) 
						descSedeLegale = descSedeLegale + " " + sedeLegale.getCivico(); 
				}
				descSedeLegale = descSedeLegale + " - " + cap;
			 }			 
			
			// PBANDI-2408 fine
			 
			 
			 String descComune = sedeLegale.getDescComune();
			 if(isEmpty(descComune)) descComune="";	
			 
			 String siglaProvincia = sedeLegale.getSiglaProvincia();
			 if(isEmpty(siglaProvincia)) siglaProvincia="";	
			 
			 if(!isEmpty(descComune) )
				 descSedeLegale+=descComune;
			 if(!isEmpty(siglaProvincia) )	 
				 descSedeLegale+="(" +siglaProvincia+")";
			
			pIvaSedeLegale = sedeLegale.getPartitaIva();
			
			datiGeneraliDTO.setIdRegione(NumberUtil.toLong(sedeLegale.getIdRegione()));
			datiGeneraliDTO.setCodIstatComuneSedeLegale(sedeLegale.getCodIstatComune());
			datiGeneraliDTO.setViaSedeLegale(descIndirizzo);
			datiGeneraliDTO.setCivicoSedeLegale(sedeLegale.getCivico()!=null?sedeLegale.getCivico():"");
			
			
		} catch (Exception e) {
			logger.warn("Eccezione gestita. Non trovata la sede legale per il progetto["+idProgetto+"] ed il beneficiario["+idSoggettoBeneficiario+"]"+e.getMessage());

		}
		datiGeneraliDTO.setSedeLegale(descSedeLegale);
		datiGeneraliDTO.setPIvaSedeLegale(pIvaSedeLegale);
		
		/*
		 * Importo Ammesso
		 */
		BigDecimal importoAmmesso = null;
		BigDecimal importoAmmessoVociDiEntrata = null;
		if (isBandoCultura) {
			importoAmmesso = getTotaleAmmessoCultura(idProgetto);
			importoAmmessoVociDiEntrata = getTotaleAmmessoCulturaVociDiEntrata(idProgetto);	
		} else {
			importoAmmesso = getTotaleAmmesso(idProgetto);
		}
		datiGeneraliDTO.setImportoAmmesso(NumberUtil.toDouble(importoAmmesso));
		datiGeneraliDTO.setImportoAmmessoVociDiEntrata(NumberUtil.toDouble(importoAmmessoVociDiEntrata));
		
		
		
		/*
		 * Soggetti finanziatori
		 */
		BigDecimal totaleCofinanziamentoPrivato = getTotaleCofinanziamentoPrivato(idProgetto);
		BigDecimal totaleCofinanziamentoPubblico = getTotaleCofinanzimentoPubblico(idProgetto);
		datiGeneraliDTO.setImportoCofinanziamentoPrivato(NumberUtil.toDouble(totaleCofinanziamentoPrivato));
		datiGeneraliDTO.setImportoCofinanziamentoPubblico(NumberUtil.toDouble(totaleCofinanziamentoPubblico));
	
		

		/*
		 * Revoche
		 */
		List<RevocaProgettoVO> revoche = progettoManager.getTotaleRevochePerModalitaAgevolazione(idProgetto);
		BigDecimal totaleRevocato = new BigDecimal(0D);
		if (!ObjectUtil.isEmpty(revoche)) {
			List<ImportoDescrizioneDTO> elencoRevoche = new ArrayList<ImportoDescrizioneDTO>();
			for (RevocaProgettoVO r : revoche) {
				ImportoDescrizioneDTO revoca = new ImportoDescrizioneDTO();
				revoca.setDescBreve(r.getDescBreveModalitaAgevolaz());
				revoca.setDescrizione(r.getDescModalitaAgevolazione());
				revoca.setImporto(NumberUtil.toDouble(r.getImporto()));
				totaleRevocato = NumberUtil.sum(totaleRevocato, r.getImporto());
				elencoRevoche.add(revoca);
			}
			datiGeneraliDTO.setRevoche(elencoRevoche.toArray(new ImportoDescrizioneDTO []{}));
		}


		/*
		 * Quietanzato e validato
		 */
		TotaleQuietanzatoValidatoProgettoVO totQuietanzatoValidato = progettoManager.getTotaliQuietanzatoValidato(idProgetto);
		BigDecimal totaleQuietanzato = totQuietanzatoValidato.getImportoQuietanzato() == null ? new BigDecimal(0d) : totQuietanzatoValidato.getImportoQuietanzato();
		BigDecimal totaleValidato = totQuietanzatoValidato.getImportoValidato() == null ? new BigDecimal(0d) : totQuietanzatoValidato.getImportoValidato();
		datiGeneraliDTO.setImportoQuietanzato(NumberUtil.toDouble(totaleQuietanzato));
		datiGeneraliDTO.setImportoValidatoNettoRevoche(NumberUtil.subtract(NumberUtil.toDouble(totaleValidato), NumberUtil.toDouble(totaleRevocato)));

		/*
		 * Rendicontato
		 */
		BigDecimal  totaleRendicontato = progettoManager.getTotaleRendicontazione(idProgetto);
		datiGeneraliDTO.setImportoRendicontato(NumberUtil.toDouble(totaleRendicontato));
		
		
		/*
		 * Recuperi
		 */
		List<RecuperoProgettoVO> recuperi = progettoManager.getTotaleRecuperiPerModalitaAgevolazione(idProgetto);
		if (!ObjectUtil.isEmpty(recuperi)) {
			List<ImportoDescrizioneDTO> elencoRecuperi = new ArrayList<ImportoDescrizioneDTO>();
			for (RecuperoProgettoVO r : recuperi) {
				ImportoDescrizioneDTO recupero = new ImportoDescrizioneDTO();
				recupero.setDescBreve(r.getDescBreveModalitaAgevolaz());
				recupero.setDescrizione(r.getDescModalitaAgevolazione());
				recupero.setImporto(NumberUtil.toDouble(r.getImportoRecupero()));
				elencoRecuperi.add(recupero);
			}
			datiGeneraliDTO.setRecuperi(elencoRecuperi.toArray(new ImportoDescrizioneDTO[]{}));
		}

		/*
		 * Pre-recuperi
		 */
		List<RecuperoProgettoVO> prerecuperi = progettoManager.getTotalePreRecuperiPerModalitaAgevolazione(idProgetto);
		if (!ObjectUtil.isEmpty(recuperi)) {
			List<ImportoDescrizioneDTO> elencoPreRecuperi = new ArrayList<ImportoDescrizioneDTO>();
			for (RecuperoProgettoVO r : prerecuperi) {
				ImportoDescrizioneDTO preRecupero = new ImportoDescrizioneDTO();
				preRecupero.setDescBreve(r.getDescBreveModalitaAgevolaz());
				preRecupero.setDescrizione(r.getDescModalitaAgevolazione());
				preRecupero.setImporto(NumberUtil.toDouble(r.getImportoRecupero()));
				elencoPreRecuperi.add(preRecupero);
			}
			datiGeneraliDTO.setPreRecuperi(elencoPreRecuperi.toArray(new ImportoDescrizioneDTO[]{}));
		}
		
		/*
		 * JIRA 2120: Se il bando del progetto ha la regola BR37 allora calcolo le liquidazioni
		 * altrimenti calcolo le erogazioni.
		 */
		Boolean isRegolaBR37 = null;
		try {
		 
			isRegolaBR37 = regolaManager.isRegolaApplicabileForProgetto(idProgetto, RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO);
		} catch (Exception e ) {
			logger.warn("Eccezione gestita. Errore nella verifica della regola "+RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO+" per il progetto["+idProgetto+"] "+e.getMessage());
		}	
		Boolean isRegolaDematAttiva = null;
		try {
			isRegolaDematAttiva = regolaManager.isRegolaApplicabileForBandoLinea(progettoBandoLineaVO.getIdBandoLinea(), RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
		} catch (Exception e ) {
			logger.warn("Eccezione gestita. Errore nella verifica della regola "+RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE+" per il progettoBandoLineaVO.getIdBandoLinea():"+progettoBandoLineaVO.getIdBandoLinea()+" "+e.getMessage());
		}	
		datiGeneraliDTO.setRegolaDematAttiva(isRegolaDematAttiva);
		
		if (isRegolaBR37 != null){
			if (isRegolaBR37) {
				List<LiquidazioneModalitaAgevolazioneVO> liquidazioni =  progettoManager.getTotaleLiquidazioniPerModalitaAgevolazione(idProgetto);
				if (!ObjectUtil.isEmpty(liquidazioni)) {
					List<ImportoDescrizioneDTO> elencoLiquidazioni = new ArrayList<ImportoDescrizioneDTO>();
					for (LiquidazioneModalitaAgevolazioneVO liq : liquidazioni) {
						ImportoDescrizioneDTO liquidazione = new ImportoDescrizioneDTO();
						liquidazione.setDescBreve(liq.getDescBreveModalitaAgevolaz());
						liquidazione.setDescrizione(liq.getDescModalitaAgevolazione());
						liquidazione.setImporto(NumberUtil.toDouble(liq.getImportoQuietanzato()));
						elencoLiquidazioni.add(liquidazione);
					}
					datiGeneraliDTO.setErogazioni(elencoLiquidazioni.toArray(new ImportoDescrizioneDTO[]{}));
				}
			} else {
				
				/*
				 * Erogazioni
				 */
				List<ErogazioneCausaleModalitaAgevolazioneVO> erogazioni = progettoManager.getTotaleErogazioniPerModalitaAgevolazione(idProgetto);
				if (!ObjectUtil.isEmpty(erogazioni)) {
					List<ImportoDescrizioneDTO> elencoErogazioni = new ArrayList<ImportoDescrizioneDTO>();
					for (ErogazioneCausaleModalitaAgevolazioneVO erog : erogazioni) {
						ImportoDescrizioneDTO erogazione = new ImportoDescrizioneDTO();
						erogazione.setDescBreve(erog.getDescBreveModalitaAgevolaz());
						erogazione.setDescrizione(erog.getDescModalitaAgevolazione());
						erogazione.setImporto(NumberUtil.toDouble(erog.getImportoErogazione()));
						elencoErogazioni.add(erogazione);
					}
					datiGeneraliDTO.setErogazioni(elencoErogazioni.toArray(new ImportoDescrizioneDTO[]{}));
				}
			}
		}
		
		
		/*
		 * Importo impegno vincolante
		 */
		BigDecimal importoImpegnoVincolante = null;
		try {
			
			//TODO: 
//			Nella sezione DATI GENERALI/DATI FINANZIARI PROGETTO presente in tutte le attivit� da svolgere per tutti i profili � necessario cambiare 
//			l'algoritmo di calcolo dell'Iimporto IMPEGNO per i progetti con pbandi_t_progetto.id_tipo_operazione in (1,2) SOLO PER I BANDI 14/20. 
//			In questi casi l'impegno � la sommatoria di PBANDI_T_APPALTO.IMP_RENDICONTABILE per gli affidamenti del progetto in stato 2,3
//			select SUM(APP.IMP_RENDICONTABILE) from PBANDI_R_PROGETTI_APPALTI PA, PBANDI_T_APPALTO APP
//			where PA.ID_APPALTO = APP.ID_APPALTO
//			AND APP.ID_STATO_AFFIDAMENTO IN (2,3)
//			AND PA.ID_PROGETTO = XXX;
			
			boolean isTipoOperazioneOk = progettoBandoLineaVO.getIdTipoOperazione().compareTo(new BigDecimal(1)) == 0 || progettoBandoLineaVO.getIdTipoOperazione().compareTo(new BigDecimal(2)) == 0;
			boolean isLinea1420Ok = pbandiDLineaDiInterventoVO.getIdLineaDiIntervento().compareTo(ID_LINEA_DI_INTERVENTO_POR_FESR_14_20) == 0;
			
			if( isLinea1420Ok && isTipoOperazioneOk ){
				ImportoImpegnoAppaltiVO importoImpegno = new ImportoImpegnoAppaltiVO();
				importoImpegno.setIdProgetto(new BigDecimal(idProgetto));
				importoImpegno = genericDAO.findSingleOrNoneWhere(importoImpegno);
				if(importoImpegno != null)
					importoImpegnoVincolante = importoImpegno.getImportoImpegno();
			}else{
				BigDecimal idContoEconomicoMaster = contoEconomicoManager.getIdContoMaster(NumberUtil.toBigDecimal(idProgetto));
				PbandiTContoEconomicoVO filterVO = new PbandiTContoEconomicoVO();
				filterVO.setIdContoEconomico(idContoEconomicoMaster);
				PbandiTContoEconomicoVO ce = genericDAO.findSingleOrNoneWhere(filterVO);
				if (ce != null) {
					importoImpegnoVincolante = ce.getImportoImpegnoVincolante();
				}
			}
		} catch (ContoEconomicoNonTrovatoException e1) {
			logger.error("Eccezione gestita. Contoeconomico MASTER per il progetto["+idProgetto+"]",e1);
		}
		logger.info("[GestioneDatiGeneraliBusinessImpl :: caricaDatiGenerali()] - importo impegno = "+ NumberUtil.toDouble(importoImpegnoVincolante));
		datiGeneraliDTO.setImportoImpegno(NumberUtil.toDouble(importoImpegnoVincolante));
		
		/*
		 * ENTE GIURIDICO 
		 */
		
		
		BeneficiarioEnteGiuridicoVO enteGiuridico = soggettoManager.findEnteGiuridicoBeneficiario(idProgetto, idSoggettoBeneficiario);
		
		datiGeneraliDTO.setFlagPubblicoPrivato(NumberUtil.toLong(enteGiuridico.getFlagPubblicoPrivato()));
		
		datiGeneraliDTO.setCodUniIpa(enteGiuridico.getCodUniIpa());
		
		datiGeneraliDTO.setIdEnteGiuridico(NumberUtil.toLong(enteGiuridico.getIdEnteGiuridico()));


		/*
		 * Sedi Progetto
		 */
		
		List<SedeProgettoVO> sediProgetto = sedeManager.findSediProgetto(new BigDecimal(idProgetto), new BigDecimal(idSoggettoBeneficiario));	
		datiGeneraliDTO.setSedi(beanUtil.transform(sediProgetto, SedeDTO.class));
		
		
		/*
		 * Soppressioni
		 */
		//BigDecimal totaleSoppressioni = progettoManager.getTotaleSoppressioni(idProgetto);


		impostaModalitaAgevolazioneEImportoValidatoAlNettoRevocheInDatiGenerali(idProgetto, datiGeneraliDTO);		


		datiGeneraliDTO.setAcronimo(progettoManager.caricaAcronimo(idProgetto));

		/*
		 * Verifico se il progetto e' gestito da GEFO
		 */
		datiGeneraliDTO.setIsGestitoGefo(progettoManager
				.isGestitoGEFO(idProgettoB));
		try {
			datiGeneraliDTO
			.setImportoCertificatoNettoUltimaPropAppr(certificazioneManager
					.findImportoUltimaPropostaCertificazionePerProgetto(
							idProgetto,
							it.csi.pbandi.pbweb.pbandiutil.common.Constants.STATO_PROPOSTA_CERTIFICAZIONE_APPROVATA));
		} catch (Exception e) {
			throw new GestioneDatiGeneraliException(
					"Errore nel recupero dell'importo certificazione netto, idProgetto: "
					+ idProgetto);
		}

		PbandiTProgettoVO progetto = progettoManager.getProgetto(idProgetto);
		if (progetto.getIdTipoOperazione() != null)
			datiGeneraliDTO.setIdTipoOperazione(progetto.getIdTipoOperazione().longValue());
		
		// Economie.
		List<EconomiaPerDatiGeneraliVO> economie = contoEconomicoManager.findEconomiePerDatiGenerali(idProgetto);
		if (economie == null)
			datiGeneraliDTO.setEconomie(null);
		else
			datiGeneraliDTO.setEconomie(beanUtil.transform(economie, EconomiaPerDatiGeneraliDTO.class));
		
		return datiGeneraliDTO;
	}

	private void impostaModalitaAgevolazioneEImportoValidatoAlNettoRevocheInDatiGenerali(
			Long idProgetto, DatiGeneraliDTO datiGeneraliDTO)
			throws GestioneDatiGeneraliException {
		ImportoAgevolatoDTO[] importiAgevolati = null;
		try {
			Map<String, String> mapPropsToCopy = estraiMappaModalitaAgevolazione();
			List<ModalitaDiAgevolazioneDTO> modalita = estraiModalitaAgevolazioneDaContoEconomico(
					idProgetto, mapPropsToCopy);

			ImportoAgevolatoDTO[] result = null;
			if (!isEmpty(modalita)) {
				mapPropsToCopy.clear();
				mapPropsToCopy.put("descrizione", "descrizione");
				mapPropsToCopy.put("importoAgevolatoUltimo", "importo");
				Iterator<ModalitaDiAgevolazioneDTO> iter = modalita.iterator();
				List<ImportoAgevolatoDTO> list = new ArrayList<ImportoAgevolatoDTO>();
				while (iter.hasNext()) {
					ModalitaDiAgevolazioneDTO modalitaDiAgevolazioneDTO = iter
							.next();
					if (modalitaDiAgevolazioneDTO.getImportoAgevolatoUltimo() == null
							|| modalitaDiAgevolazioneDTO
									.getImportoAgevolatoUltimo().doubleValue() == 0d) {
						iter.remove();
					} else {
						ModalitaAgevolazioneTotaleRevocheVO totaliVO = null;
						try {
							totaliVO = revocaRecuperoManager
									.findTotaleRevocheRecuperi(
											idProgetto,
											modalitaDiAgevolazioneDTO
													.getIdModalitaDiAgevolazione());
						} catch (Exception e) {
							// revoca non trovata
						}
						ImportoAgevolatoDTO importoAgevolatoDTO = getBeanUtil()
								.transform(modalitaDiAgevolazioneDTO,
										ImportoAgevolatoDTO.class,
										mapPropsToCopy);
						if (totaliVO != null
								&& totaliVO.getTotaleImportoRevocato() != null) {
							importoAgevolatoDTO
									.setImportoAlNettoRevoche(NumberUtil.toDouble(NumberUtil.subtract(
											NumberUtil
													.createScaledBigDecimal(importoAgevolatoDTO
															.getImporto()),
											totaliVO.getTotaleImportoRevocato())));
						} else {
							importoAgevolatoDTO
									.setImportoAlNettoRevoche(importoAgevolatoDTO
											.getImporto());
						}
						list.add(importoAgevolatoDTO);
					}
				}
				result = list.toArray(new ImportoAgevolatoDTO[list.size()]);
			}
			importiAgevolati = result;
			datiGeneraliDTO.setImportiAgevolati(importiAgevolati);
		} catch (Exception e) {
			throw new GestioneDatiGeneraliException(
					"Errore nel recupero degli importi agevolati, idProgetto: "
							+ idProgetto);
		}
		try {
			List<TotaleRevocaProgettoVO> totaliVOs = revocaRecuperoManager
					.findTotaleRevocheSulProgetto(idProgetto);
			Iterator<TotaleRevocaProgettoVO> iter = totaliVOs.iterator();
			BigDecimal importoRevocatoTotaleSulProgetto = new BigDecimal(0);
			while (iter.hasNext()) {
				TotaleRevocaProgettoVO item = iter.next();
				importoRevocatoTotaleSulProgetto = NumberUtil.sum(
						importoRevocatoTotaleSulProgetto,
						item.getTotaleImportoRevocato());
			}

			it.csi.pbandi.pbweb.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoMaster = null;
			try {
				contoEconomicoMaster = contoEconomicoManager
						.findContoEconomicoMasterPotato(new BigDecimal(
								idProgetto));
				datiGeneraliDTO.setImportoValidatoNettoRevoche(NumberUtil
						.toDouble(NumberUtil.subtract(
								contoEconomicoMaster.getImportoValidato(),
								importoRevocatoTotaleSulProgetto)));
			} catch (Exception e) {
				logger.warn("Eccezione gestita : "+e.getMessage() );
			}

			if (datiGeneraliDTO.getImportoValidatoNettoRevoche() != null
					&& datiGeneraliDTO.getImportoValidatoNettoRevoche() < 0) {
				datiGeneraliDTO.setImportoValidatoNettoRevoche(new Double(0));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiGeneraliException(
					"Errore nel recupero dell'importo validato al netto delle revoche, idProgetto: "
							+ idProgetto);
		}
	}

	private List<ModalitaDiAgevolazioneDTO> estraiModalitaAgevolazioneDaContoEconomico(
			Long idProgetto, Map<String, String> mapPropsToCopy) {
		List<ModalitaDiAgevolazioneDTO> modalita = null;
		try {
			modalita = getBeanUtil().transformList(

					contoEconomicoManager.caricaModalitaAgevolazione(
							new BigDecimal(idProgetto),
							Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER),
					ModalitaDiAgevolazioneDTO.class, mapPropsToCopy);
		} catch (Exception e) {
			logger.warn("Errore nel recupero degli importi agevolati nel progetto "
					+ idProgetto + " per i dati generali ");
		}
		return modalita;
	}

	private Map<String, String> estraiMappaModalitaAgevolazione() {
		Map<String, String> mapPropsToCopy = new HashMap<String, String>();
		mapPropsToCopy
				.put("idModalitaAgevolazione", "idModalitaDiAgevolazione");
		mapPropsToCopy.put("descModalitaAgevolazione", "descrizione");
		mapPropsToCopy
				.put("massimoImportoAgevolato", "importoMassimoAgevolato");
		mapPropsToCopy.put("importoErogazioni", "importoErogato");
		mapPropsToCopy.put("quotaImportoAgevolato", "importoAgevolatoNuovo");
		mapPropsToCopy.put("quotaImportoAgevolato", "importoAgevolatoUltimo");
		mapPropsToCopy.put("quotaImportoRichiesto", "importoRichiestoUltimo");
		mapPropsToCopy.put("percImportoAgevolatoBando",
				"percImportoMassimoAgevolato");
		return mapPropsToCopy;
	}

	private Date caricaDtPresentazioneDomanda(
			ProgettoBandoLineaVO progettoBandoLineaVO) {
		PbandiTDomandaVO pbandiTDomandaVO = new PbandiTDomandaVO();
		pbandiTDomandaVO.setIdDomanda(progettoBandoLineaVO.getIdDomanda());
		pbandiTDomandaVO = genericDAO.findSingleWhere(pbandiTDomandaVO);

		Date dtPresentazioneDomanda = pbandiTDomandaVO
				.getDtPresentazioneDomanda();
		return dtPresentazioneDomanda;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO caricaDatiGeneraliPerValidazione(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idProgetto, java.lang.Long idSoggettoBeneficiario,
			java.lang.Long idDichiarazione)
			throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException,
			FormalParameterException {

		    String[] nameParameter = { "idUtente", "identitaDigitale",
				"idProgetto", "idDichiarazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProgetto, idDichiarazione);
			logger.info("###########################\ncaricaDatiGeneraliPerValidazione idProgetto:"+idProgetto+
		    " ,idDichiarazione:"+idDichiarazione+" ,idSoggettoBeneficiario:"+idSoggettoBeneficiario);
			DatiGeneraliDTO datiGeneraliDTO =  caricaDatiGenerali(idProgetto);
			datiGeneraliDTO.setIdDichiarazione(idDichiarazione);
			
			PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = new PbandiTDichiarazioneSpesaVO(new BigDecimal(idDichiarazione));
		 
			PbandiTDichiarazioneSpesaVO[] dichiarazioniDiSpesaVO = genericDAO.findWhere(dichiarazioneDiSpesaVO);
			DecodificaDTO statoNonValidato = null;
			DecodificaDTO statoInviato= null;
			DecodificaDTO statoClassificato= null;
			DecodificaDTO statoProtocollato= null;
			if (dichiarazioniDiSpesaVO.length == 1) {
				statoNonValidato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_NON_VALIDATO);
				statoInviato = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_INVIATO);
				statoClassificato = decodificheManager.findDecodifica(
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_CLASSIFICATO);
				statoProtocollato = decodificheManager.findDecodifica(
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_PROTOCOLLATO);
				  
				java.sql.Date dtDichiarazione = dichiarazioniDiSpesaVO[0].getDtDichiarazione();
				datiGeneraliDTO.setCodiceDichiarazione(idDichiarazione + "-"+ DateUtil.getData(dtDichiarazione.getTime()));
				datiGeneraliDTO.setDtDichiarazione(dtDichiarazione);
				BigDecimal idTipoDichiarazSpesa = dichiarazioniDiSpesaVO[0].getIdTipoDichiarazSpesa();
				DecodificaDTO decodificaTipoDich = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DICHIARAZ_SPESA, idTipoDichiarazSpesa);
				PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
				if(decodificaTipoDich.getDescrizioneBreve().equals("FC")){
					logger.info("tipo dich FC");
					documentoIndexVO.setIdEntita(getDocumentoManager().getIdEntita(PbandiTComunicazFineProgVO.class));
					documentoIndexVO.setIdProgetto(BigDecimal.valueOf( idProgetto));
					documentoIndexVO.setDescendentOrder("idDocumentoIndex");
					List<PbandiTDocumentoIndexVO> list = genericDAO.findListWhere(documentoIndexVO)  ;
					if(!list.isEmpty()){
						PbandiTDocumentoIndexVO docIndex=list.get(0);
						datiGeneraliDTO.setIdDocIndexComunicFineProgetto(docIndex.getIdDocumentoIndex().longValue());
						datiGeneraliDTO.setIdDocumentoIndexDichiarazione(dichiarazioniDiSpesaVO[0].getIdDichiarazioneSpesa().longValue());
						PbandiTDocProtocolloVO pbandiTDocProtocolloVO= new PbandiTDocProtocolloVO();
						pbandiTDocProtocolloVO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex());
						pbandiTDocProtocolloVO = genericDAO.findSingleOrNoneWhere(pbandiTDocProtocolloVO);
						if(pbandiTDocProtocolloVO!=null) datiGeneraliDTO.setProtocollo(pbandiTDocProtocolloVO.getNumProtocollo());
						if(docIndex.getIdStatoDocumento()!=null && (
								docIndex.getIdStatoDocumento().longValue()==statoInviato.getId().longValue() 
								||
								docIndex.getIdStatoDocumento().longValue()==statoClassificato.getId().longValue() 
								||
								docIndex.getIdStatoDocumento().longValue()==statoProtocollato.getId().longValue() 
								)								
								){
							datiGeneraliDTO.setStatoDichiarazione("firmata");
						}else  
							datiGeneraliDTO.setStatoDichiarazione("non firmata");
						
						datiGeneraliDTO.setFlagFirmaCartacea(docIndex.getFlagFirmaCartacea());
						logger.info("docIndex.getIdDocumentoIndex() trovato per comunicazione fine progetto : "+docIndex.getIdDocumentoIndex() +" , dichiarazioniDiSpesaVO[0].getIdDichiarazioneSpesa() : "+dichiarazioniDiSpesaVO[0].getIdDichiarazioneSpesa());
						
						// Aggiunto perch� nella finestra di validazione si vuole visualizzare 
						// id+data della comunicazione fine progetto al posto di id+data della dichiarazione di spesa.
						PbandiTComunicazFineProgVO vo = new PbandiTComunicazFineProgVO();
						vo.setIdProgetto(new BigDecimal(idProgetto));
						vo = genericDAO.findSingleOrNoneWhere(vo);
						if (vo != null) {
							datiGeneraliDTO.setIdComunicazFineProg(vo.getIdComunicazFineProg().longValue());
							if (vo.getDtComunicazione() != null);
								datiGeneraliDTO.setDtComunicazione(""+DateUtil.getData(vo.getDtComunicazione().getTime()));
							logger.info("IdComunicazFineProg = "+datiGeneraliDTO.getIdComunicazFineProg());
							logger.info("DtComunicazione = "+datiGeneraliDTO.getDtComunicazione());
						}
					}
				}else{
					logger.info("\n\n\n\ntipo dich NON FC, idDichiarazione "+dichiarazioniDiSpesaVO[0].getIdDichiarazioneSpesa());
					documentoIndexVO.setIdEntita(getDocumentoManager().getIdEntita(PbandiTDichiarazioneSpesaVO.class));
					documentoIndexVO.setIdTarget(BigDecimal.valueOf(idDichiarazione));
					documentoIndexVO.setNomeFile("Dich");
					LikeStartsWithCondition<PbandiTDocumentoIndexVO> startsWithCondition = new LikeStartsWithCondition<PbandiTDocumentoIndexVO>(documentoIndexVO);
					List<PbandiTDocumentoIndexVO> list = genericDAO.findListWhere(startsWithCondition)  ;
					if(!list.isEmpty()){
						PbandiTDocumentoIndexVO docIndex=list.get(0);
						datiGeneraliDTO.setIdDocumentoIndexDichiarazione(docIndex.getIdDocumentoIndex().longValue());
						logger.info("docIndex.getIdDocumentoIndex():"+docIndex.getIdDocumentoIndex()+
								" found for dich di spesa : "+docIndex.getIdDocumentoIndex());
						PbandiTDocProtocolloVO pbandiTDocProtocolloVO= new PbandiTDocProtocolloVO();
						pbandiTDocProtocolloVO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex());
						pbandiTDocProtocolloVO = genericDAO.findSingleOrNoneWhere(pbandiTDocProtocolloVO);
						if(pbandiTDocProtocolloVO!=null) datiGeneraliDTO.setProtocollo(pbandiTDocProtocolloVO.getNumProtocollo());
						if(pbandiTDocProtocolloVO!=null) datiGeneraliDTO.setProtocollo(pbandiTDocProtocolloVO.getNumProtocollo());
						if(docIndex.getIdStatoDocumento()!=null && (
								docIndex.getIdStatoDocumento().longValue()==statoInviato.getId().longValue() 
								||
								docIndex.getIdStatoDocumento().longValue()==statoClassificato.getId().longValue() 
								||
								docIndex.getIdStatoDocumento().longValue()==statoProtocollato.getId().longValue() 
								)								
								){
							datiGeneraliDTO.setStatoDichiarazione("firmata");
						}else  {
							datiGeneraliDTO.setStatoDichiarazione("non firmata");
						}
						datiGeneraliDTO.setFlagFirmaCartacea(docIndex.getFlagFirmaCartacea());
					}
				}				
				
				// Aggiungo i dati +Green
				integraDatiPiuGreen(idUtente, identitaDigitale, idProgetto, datiGeneraliDTO, statoInviato, statoClassificato, statoProtocollato, decodificaTipoDich.getDescrizioneBreve(), dichiarazioniDiSpesaVO[0].getIdTipoDichiarazSpesa());
				
			} else {
				throw new GestioneDatiGeneraliException(
						"Dichiarazione di spesa non trovata, idDichiarazione: "+ idDichiarazione);
			}
		logger.shallowDump(datiGeneraliDTO, "info");
			
		return datiGeneraliDTO;
	}
	
	private void integraDatiPiuGreen(Long idUtente, String identitaDigitale, Long idProgetto, DatiGeneraliDTO datiGeneraliDTO,
			DecodificaDTO statoInviato, DecodificaDTO statoClassificato, DecodificaDTO statoProtocollato,
			String descrizioneBreveTipoDich, BigDecimal idTipoDichiarazSpesa) 
			throws GestioneDatiGeneraliException {

		// Verifico se si tratta di progetto +Green.
		DatiPiuGreenDTO datiPiuGreen = null;
		logger.info("+Green: verifico se il progetto "+idProgetto+" ha associato un progetto a contributo");
		try {
			datiPiuGreen = findDatiPiuGreen(idUtente, identitaDigitale, idProgetto);
		} catch (Exception e) {
			throw new GestioneDatiGeneraliException("Errore nella ricerca del progetto a contributo del progetto "+ idProgetto);
		}
		
		Long idProgettoContributo = datiPiuGreen.getIdProgettoContributo();
		if (idProgettoContributo == null)
			return;

		if ("FC".equalsIgnoreCase(descrizioneBreveTipoDich)) {

			// Si sta validando una Comunicazione di Fine Progetto.
			logger.info("+Green: gestione CFP");

			//PBANDI_T_COMUNICAZ_FINE_PROG
			PbandiTComunicazFineProgVO vo = new PbandiTComunicazFineProgVO();
			vo.setIdProgetto(new BigDecimal(idProgettoContributo));
			vo = genericDAO.findSingleOrNoneWhere(vo);
			if (vo == null)
				return;
			datiGeneraliDTO.setIdComunicazFineProgPiuGreen(vo.getIdComunicazFineProg().longValue());
			if (vo.getDtComunicazione() != null) {
				datiGeneraliDTO.setDtComunicazionePiuGreen(""+DateUtil.getData(vo.getDtComunicazione().getTime()));
			}				
			logger.info("+Green: IdComunicazFineProgPiuGreen = "+datiGeneraliDTO.getIdComunicazFineProgPiuGreen());
			logger.info("+Green: DtComunicazionePiuGreen = "+datiGeneraliDTO.getDtComunicazionePiuGreen());

			// PBANDI_T_DOCUMENTO_INDEX
			PbandiTDocumentoIndexVO docIndexVOPiuGreen = new PbandiTDocumentoIndexVO();
			docIndexVOPiuGreen.setIdEntita(getDocumentoManager().getIdEntita(PbandiTComunicazFineProgVO.class));
			docIndexVOPiuGreen.setIdProgetto(BigDecimal.valueOf(idProgettoContributo));
			docIndexVOPiuGreen.setDescendentOrder("idDocumentoIndex");
			List<PbandiTDocumentoIndexVO> list = genericDAO.findListWhere(docIndexVOPiuGreen);
			if(!list.isEmpty()){
				PbandiTDocumentoIndexVO docIndex = list.get(0);
				// ID DOC INDEX
				logger.info("+Green: trovato documento index con id = "+docIndex.getIdDocumentoIndex());
				datiGeneraliDTO.setIdDocIndexComunicFineProgettoPiuGreen(docIndex.getIdDocumentoIndex().longValue());
				// STATO DICHIARAZIONE.
				if(docIndex.getIdStatoDocumento()!= null && (
						docIndex.getIdStatoDocumento().longValue()==statoInviato.getId().longValue() 
						||
						docIndex.getIdStatoDocumento().longValue()==statoClassificato.getId().longValue() 
						||
						docIndex.getIdStatoDocumento().longValue()==statoProtocollato.getId().longValue())								
				){
					datiGeneraliDTO.setStatoDichiarazionePiuGreen("firmata");
				} else { 
					datiGeneraliDTO.setStatoDichiarazionePiuGreen("non firmata");
				}
				logger.info("+Green: StatoDichiarazionePiuGreen = "+datiGeneraliDTO.getStatoDichiarazionePiuGreen());
				// FLAG FIRMA CARTACEA.
				datiGeneraliDTO.setFlagFirmaCartaceaPiuGreen(docIndex.getFlagFirmaCartacea());
				logger.info("+Green: FlagFirmaCartaceaPiuGreen = "+datiGeneraliDTO.getFlagFirmaCartaceaPiuGreen());
				// PROTOCOLLO.
				PbandiTDocProtocolloVO pbandiTDocProtocolloVO= new PbandiTDocProtocolloVO();
				pbandiTDocProtocolloVO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex());
				pbandiTDocProtocolloVO = genericDAO.findSingleOrNoneWhere(pbandiTDocProtocolloVO);
				if(pbandiTDocProtocolloVO != null)
					datiGeneraliDTO.setProtocolloPiuGreen(pbandiTDocProtocolloVO.getNumProtocollo());
				logger.info("+Green: ProtocolloPiuGreen = "+datiGeneraliDTO.getProtocolloPiuGreen());

			} else {
				logger.info("+Green: nessun record trovato in PBANDI_T_DOCUMENTO_INDEX");
			}

		} else {

			// Si sta validando una Dichiarazione di Spesa (ad es. Integrativa).
			logger.info("+Green: gestione DS");

			// PBANDI_T_DICHIARAZIONE_SPESA
			PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = new PbandiTDichiarazioneSpesaVO();
			dichiarazioneDiSpesaVO.setIdProgetto(new BigDecimal(idProgettoContributo));
			dichiarazioneDiSpesaVO.setIdTipoDichiarazSpesa(idTipoDichiarazSpesa);
			dichiarazioneDiSpesaVO.setDescendentOrder("idDichiarazioneSpesa");
			List<PbandiTDichiarazioneSpesaVO> listaDS = genericDAO.findListWhere(dichiarazioneDiSpesaVO);
			if (listaDS.size() > 0) {

				PbandiTDichiarazioneSpesaVO dichSpesaContributo = listaDS.get(0);
				logger.info("+Green: trovato id dichSpesaContributo = "+dichSpesaContributo.getIdDichiarazioneSpesa());

				// NOTA: uso questi campi, inizialmente previsti solo per le CFP, 
				//       per metterci i dati della DS contributo.
				datiGeneraliDTO.setIdComunicazFineProgPiuGreen(dichSpesaContributo.getIdDichiarazioneSpesa().longValue());
				if (dichSpesaContributo.getDtDichiarazione() != null)
					datiGeneraliDTO.setDtComunicazionePiuGreen(""+DateUtil.getData(dichSpesaContributo.getDtDichiarazione().getTime()));
				
				// PBANDI_T_DOCUMENTO_INDEX
				PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
				documentoIndexVO.setIdEntita(getDocumentoManager().getIdEntita(PbandiTDichiarazioneSpesaVO.class));
				documentoIndexVO.setIdTarget(dichSpesaContributo.getIdDichiarazioneSpesa());
				documentoIndexVO.setNomeFile("Dich");
				LikeStartsWithCondition<PbandiTDocumentoIndexVO> startsWithCondition = new LikeStartsWithCondition<PbandiTDocumentoIndexVO>(documentoIndexVO);
				List<PbandiTDocumentoIndexVO> list = genericDAO.findListWhere(startsWithCondition)  ;
				if(!list.isEmpty()){

					PbandiTDocumentoIndexVO docIndex=list.get(0);

					// ID DOC INDEX
					// NOTA: metto l'id doc index della DS Contributo nel campo IdDocIndexComunicFineProgettoPiuGreen
					//       nonostante il nome di quest'ultimo sia riferito alla CFP.
					datiGeneraliDTO.setIdDocIndexComunicFineProgettoPiuGreen(docIndex.getIdDocumentoIndex().longValue());
					logger.info("+Green: trovato documento index con id = "+datiGeneraliDTO.getIdDocIndexComunicFineProgettoPiuGreen());
					
					// PROTOCOLLO.
					PbandiTDocProtocolloVO pbandiTDocProtocolloVO= new PbandiTDocProtocolloVO();
					pbandiTDocProtocolloVO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex());
					pbandiTDocProtocolloVO = genericDAO.findSingleOrNoneWhere(pbandiTDocProtocolloVO);
					if(pbandiTDocProtocolloVO != null) 
						datiGeneraliDTO.setProtocolloPiuGreen(pbandiTDocProtocolloVO.getNumProtocollo());
					logger.info("+Green: ProtocolloPiuGreen = "+datiGeneraliDTO.getProtocolloPiuGreen());

					// STATO DICHIARAZIONE.
					if(docIndex.getIdStatoDocumento()!=null && (
							docIndex.getIdStatoDocumento().longValue()==statoInviato.getId().longValue() 
							||
							docIndex.getIdStatoDocumento().longValue()==statoClassificato.getId().longValue() 
							||
							docIndex.getIdStatoDocumento().longValue()==statoProtocollato.getId().longValue())								
					){
						datiGeneraliDTO.setStatoDichiarazionePiuGreen("firmata");
					} else {
						datiGeneraliDTO.setStatoDichiarazionePiuGreen("non firmata");
					}
					logger.info("+Green: StatoDichiarazionePiuGreen = "+datiGeneraliDTO.getStatoDichiarazionePiuGreen());

					// FLAG FIRMA CARTACEA.
					datiGeneraliDTO.setFlagFirmaCartaceaPiuGreen(docIndex.getFlagFirmaCartacea());
					logger.info("+Green: FlagFirmaCartaceaPiuGreen = "+datiGeneraliDTO.getFlagFirmaCartaceaPiuGreen());

				} else {
					logger.info("+Green: nessun record trovato in PBANDI_T_DOCUMENTO_INDEX");
				}

			} else {
				logger.info("+Green: nessun record trovato in PBANDI_T_DICHIARAZIONE_SPESA");
			}			
		}
	}
	
	// integraDatiPiuGreen() rimaneggiata per la nuova versione di PBandi.
	public DatiGeneraliDTO integraDatiPiuGreenPerNuovaVersione(Long idUtente, String identitaDigitale, Long idProgetto, Long idDichiarazioneDiSpesa) 
			throws GestioneDatiGeneraliException {
		
		DecodificaDTO statoInviato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_INVIATO);
		DecodificaDTO statoClassificato = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_CLASSIFICATO);
		DecodificaDTO statoProtocollato = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
						GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_PROTOCOLLATO);

		// Verifico se si tratta di progetto +Green.
		DatiPiuGreenDTO datiPiuGreen = null;
		logger.info("+Green: verifico se il progetto "+idProgetto+" ha associato un progetto a contributo");
		try {
			datiPiuGreen = findDatiPiuGreen(idUtente, identitaDigitale, idProgetto);
		} catch (Exception e) {
			logger.error("ERRORE in findDatiPiuGreen(): "+e);
			throw new GestioneDatiGeneraliException("Errore nella ricerca del progetto a contributo del progetto "+ idProgetto);
		}
		
		Long idProgettoContributo = datiPiuGreen.getIdProgettoContributo();
		if (idProgettoContributo == null)
			// Progetto NON +Green: restituisco null.
			return null;
		
		// Recupero ls DS e poi il tipo di DS.
		it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO ds = new it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO();
		ds.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa));
		ds = genericDAO.findSingleWhere(ds);
		BigDecimal idTipoDichiarazSpesa = ds.getIdTipoDichiarazSpesa();
		it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDichiarazSpesaVO tipoDS = new it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDichiarazSpesaVO();
		tipoDS.setIdTipoDichiarazSpesa(idTipoDichiarazSpesa);
		tipoDS = genericDAO.findSingleWhere(tipoDS);
		String descrizioneBreveTipoDich = tipoDS.getDescBreveTipoDichiaraSpesa();

		DatiGeneraliDTO datiGeneraliDTO = new DatiGeneraliDTO();
		
		if ("FC".equalsIgnoreCase(descrizioneBreveTipoDich)) {

			// Si sta validando una Comunicazione di Fine Progetto.
			logger.info("+Green: gestione CFP");

			//PBANDI_T_COMUNICAZ_FINE_PROG
			PbandiTComunicazFineProgVO vo = new PbandiTComunicazFineProgVO();
			vo.setIdProgetto(new BigDecimal(idProgettoContributo));
			vo = genericDAO.findSingleOrNoneWhere(vo);
			if (vo == null)
				return null;
			datiGeneraliDTO.setIdComunicazFineProgPiuGreen(vo.getIdComunicazFineProg().longValue());
			if (vo.getDtComunicazione() != null) {
				datiGeneraliDTO.setDtComunicazionePiuGreen(""+DateUtil.getData(vo.getDtComunicazione().getTime()));
			}				
			logger.info("+Green: IdComunicazFineProgPiuGreen = "+datiGeneraliDTO.getIdComunicazFineProgPiuGreen());
			logger.info("+Green: DtComunicazionePiuGreen = "+datiGeneraliDTO.getDtComunicazionePiuGreen());

			// PBANDI_T_DOCUMENTO_INDEX
			PbandiTDocumentoIndexVO docIndexVOPiuGreen = new PbandiTDocumentoIndexVO();
			docIndexVOPiuGreen.setIdEntita(getDocumentoManager().getIdEntita(PbandiTComunicazFineProgVO.class));
			docIndexVOPiuGreen.setIdProgetto(BigDecimal.valueOf(idProgettoContributo));
			docIndexVOPiuGreen.setDescendentOrder("idDocumentoIndex");
			List<PbandiTDocumentoIndexVO> list = genericDAO.findListWhere(docIndexVOPiuGreen);
			if(!list.isEmpty()){
				PbandiTDocumentoIndexVO docIndex = list.get(0);
				// ID DOC INDEX
				logger.info("+Green: trovato documento index con id = "+docIndex.getIdDocumentoIndex());
				datiGeneraliDTO.setIdDocIndexComunicFineProgettoPiuGreen(docIndex.getIdDocumentoIndex().longValue());
				// STATO DICHIARAZIONE.
				if(docIndex.getIdStatoDocumento()!= null && (
						docIndex.getIdStatoDocumento().longValue()==statoInviato.getId().longValue() 
						||
						docIndex.getIdStatoDocumento().longValue()==statoClassificato.getId().longValue() 
						||
						docIndex.getIdStatoDocumento().longValue()==statoProtocollato.getId().longValue())								
				){
					datiGeneraliDTO.setStatoDichiarazionePiuGreen("firmata");
				} else { 
					datiGeneraliDTO.setStatoDichiarazionePiuGreen("non firmata");
				}
				logger.info("+Green: StatoDichiarazionePiuGreen = "+datiGeneraliDTO.getStatoDichiarazionePiuGreen());
				// FLAG FIRMA CARTACEA.
				datiGeneraliDTO.setFlagFirmaCartaceaPiuGreen(docIndex.getFlagFirmaCartacea());
				logger.info("+Green: FlagFirmaCartaceaPiuGreen = "+datiGeneraliDTO.getFlagFirmaCartaceaPiuGreen());
				// PROTOCOLLO.
				PbandiTDocProtocolloVO pbandiTDocProtocolloVO= new PbandiTDocProtocolloVO();
				pbandiTDocProtocolloVO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex());
				pbandiTDocProtocolloVO = genericDAO.findSingleOrNoneWhere(pbandiTDocProtocolloVO);
				if(pbandiTDocProtocolloVO != null)
					datiGeneraliDTO.setProtocolloPiuGreen(pbandiTDocProtocolloVO.getNumProtocollo());
				logger.info("+Green: ProtocolloPiuGreen = "+datiGeneraliDTO.getProtocolloPiuGreen());

			} else {
				logger.info("+Green: nessun record trovato in PBANDI_T_DOCUMENTO_INDEX");
			}

		} else {

			// Si sta validando una Dichiarazione di Spesa (ad es. Integrativa).
			logger.info("+Green: gestione DS");

			// PBANDI_T_DICHIARAZIONE_SPESA
			PbandiTDichiarazioneSpesaVO dichiarazioneDiSpesaVO = new PbandiTDichiarazioneSpesaVO();
			dichiarazioneDiSpesaVO.setIdProgetto(new BigDecimal(idProgettoContributo));
			dichiarazioneDiSpesaVO.setIdTipoDichiarazSpesa(idTipoDichiarazSpesa);
			dichiarazioneDiSpesaVO.setDescendentOrder("idDichiarazioneSpesa");
			List<PbandiTDichiarazioneSpesaVO> listaDS = genericDAO.findListWhere(dichiarazioneDiSpesaVO);
			if (listaDS.size() > 0) {

				PbandiTDichiarazioneSpesaVO dichSpesaContributo = listaDS.get(0);
				logger.info("+Green: trovato id dichSpesaContributo = "+dichSpesaContributo.getIdDichiarazioneSpesa());

				// NOTA: uso questi campi, inizialmente previsti solo per le CFP, 
				//       per metterci i dati della DS contributo.
				datiGeneraliDTO.setIdComunicazFineProgPiuGreen(dichSpesaContributo.getIdDichiarazioneSpesa().longValue());
				if (dichSpesaContributo.getDtDichiarazione() != null)
					datiGeneraliDTO.setDtComunicazionePiuGreen(""+DateUtil.getData(dichSpesaContributo.getDtDichiarazione().getTime()));
				
				// PBANDI_T_DOCUMENTO_INDEX
				PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
				documentoIndexVO.setIdEntita(getDocumentoManager().getIdEntita(PbandiTDichiarazioneSpesaVO.class));
				documentoIndexVO.setIdTarget(dichSpesaContributo.getIdDichiarazioneSpesa());
				documentoIndexVO.setNomeFile("Dich");
				LikeStartsWithCondition<PbandiTDocumentoIndexVO> startsWithCondition = new LikeStartsWithCondition<PbandiTDocumentoIndexVO>(documentoIndexVO);
				List<PbandiTDocumentoIndexVO> list = genericDAO.findListWhere(startsWithCondition)  ;
				if(!list.isEmpty()){

					PbandiTDocumentoIndexVO docIndex=list.get(0);

					// ID DOC INDEX
					// NOTA: metto l'id doc index della DS Contributo nel campo IdDocIndexComunicFineProgettoPiuGreen
					//       nonostante il nome di quest'ultimo sia riferito alla CFP.
					datiGeneraliDTO.setIdDocIndexComunicFineProgettoPiuGreen(docIndex.getIdDocumentoIndex().longValue());
					logger.info("+Green: trovato documento index con id = "+datiGeneraliDTO.getIdDocIndexComunicFineProgettoPiuGreen());
					
					// PROTOCOLLO.
					PbandiTDocProtocolloVO pbandiTDocProtocolloVO= new PbandiTDocProtocolloVO();
					pbandiTDocProtocolloVO.setIdDocumentoIndex(docIndex.getIdDocumentoIndex());
					pbandiTDocProtocolloVO = genericDAO.findSingleOrNoneWhere(pbandiTDocProtocolloVO);
					if(pbandiTDocProtocolloVO != null) 
						datiGeneraliDTO.setProtocolloPiuGreen(pbandiTDocProtocolloVO.getNumProtocollo());
					logger.info("+Green: ProtocolloPiuGreen = "+datiGeneraliDTO.getProtocolloPiuGreen());

					// STATO DICHIARAZIONE.
					if(docIndex.getIdStatoDocumento()!=null && (
							docIndex.getIdStatoDocumento().longValue()==statoInviato.getId().longValue() 
							||
							docIndex.getIdStatoDocumento().longValue()==statoClassificato.getId().longValue() 
							||
							docIndex.getIdStatoDocumento().longValue()==statoProtocollato.getId().longValue())								
					){
						datiGeneraliDTO.setStatoDichiarazionePiuGreen("firmata");
					} else {
						datiGeneraliDTO.setStatoDichiarazionePiuGreen("non firmata");
					}
					logger.info("+Green: StatoDichiarazionePiuGreen = "+datiGeneraliDTO.getStatoDichiarazionePiuGreen());

					// FLAG FIRMA CARTACEA.
					datiGeneraliDTO.setFlagFirmaCartaceaPiuGreen(docIndex.getFlagFirmaCartacea());
					logger.info("+Green: FlagFirmaCartaceaPiuGreen = "+datiGeneraliDTO.getFlagFirmaCartaceaPiuGreen());

				} else {
					logger.info("+Green: nessun record trovato in PBANDI_T_DOCUMENTO_INDEX");
				}

			} else {
				logger.info("+Green: nessun record trovato in PBANDI_T_DICHIARAZIONE_SPESA");
			}			
		}
		
		return datiGeneraliDTO;
	}
		
	private BigDecimal getTotaleCofinanzimentoPubblico (Long idProgetto) {
		List<SoggettoFinanziatoreProgettoVO> soggettiFinanziatoriPubblici = progettoManager.findSoggettiFinanziatoriPubblici(idProgetto);
		BigDecimal totale = new BigDecimal(0d);
		if (!ObjectUtil.isEmpty(soggettiFinanziatoriPubblici)) {
			for (SoggettoFinanziatoreProgettoVO s : soggettiFinanziatoriPubblici) {
				totale = NumberUtil.sum(totale, s.getImpQuotaSoggFinanziatore());
			}
		}
		return totale;
	}
	
	private BigDecimal getTotaleCofinanziamentoPrivato (Long idProgetto) {
		List<SoggettoFinanziatoreProgettoVO> soggettiFinanziatoriPrivati = progettoManager.findSoggettiFinanziatoriPrivati(idProgetto);
		BigDecimal totale = new BigDecimal(0d);
		if (!ObjectUtil.isEmpty(soggettiFinanziatoriPrivati)) {
			for (SoggettoFinanziatoreProgettoVO s : soggettiFinanziatoriPrivati) {
				totale = NumberUtil.sum(totale, s.getImpQuotaSoggFinanziatore());
			}
		}
		return totale;
	}
	
	private BigDecimal getTotaleAmmesso(Long idProgetto) {
		List<RigoContoEconomicoProgettoVO> righi = progettoManager.getRigheContoEconomicoMaster(idProgetto);
		BigDecimal totaleAmmesso = new BigDecimal(0D);
		if (!ObjectUtil.isEmpty(righi)) {
			for (RigoContoEconomicoProgettoVO r : righi) {						
				if (r.getImportoAmmessoFinanziamento() != null) {	// PBANDI-2808: aggiunto if
					totaleAmmesso = NumberUtil.sum(totaleAmmesso, r.getImportoAmmessoFinanziamento());
				}
				logger.info("getTotaleAmmesso(): ImportoAmmessoFinanziamento = "+r.getImportoAmmessoFinanziamento()+"; totaleAmmesso = "+totaleAmmesso);
			}
		}
		logger.info("getTotaleAmmesso() : totaleAmmesso finale = "+totaleAmmesso);
		return totaleAmmesso;		
	}
	
	private BigDecimal getTotaleAmmessoCultura(Long idProgetto) {
		List<RigoContoEconomicoProgettoCulturaVO> righi = progettoManager.getRigheContoEconomicoMasterCultura(idProgetto);
		BigDecimal totaleAmmesso = new BigDecimal(0D);
		if (!ObjectUtil.isEmpty(righi)) {
			for (RigoContoEconomicoProgettoCulturaVO r : righi) {
				if (r.getIdVoceDiSpesa() != null) {
						if (r.getImportoAmmessoFinanziamento() != null) {
							totaleAmmesso = NumberUtil.sum(totaleAmmesso, r.getImportoAmmessoFinanziamento());
						}
						logger.info("getTotaleAmmessoCultura(): ImportoAmmessoFinanziamento = "+r.getImportoAmmessoFinanziamento()+"; totaleAmmesso = "+totaleAmmesso);
				}
			}
		}
		logger.info("getTotaleAmmessoCultura() : totaleAmmesso finale = "+totaleAmmesso);
		return totaleAmmesso;		
	}
	
	private BigDecimal getTotaleAmmessoCulturaVociDiEntrata(Long idProgetto) {
		List<RigoContoEconomicoProgettoCulturaVO> righi = progettoManager.getRigheContoEconomicoMasterCultura(idProgetto);
		BigDecimal totaleAmmesso = new BigDecimal(0D);
		if (!ObjectUtil.isEmpty(righi)) {
			for (RigoContoEconomicoProgettoCulturaVO r : righi) {
				if (r.getIdVoceDiEntrata() != null) {
						if (r.getImportoAmmessoFinanziamento() != null) {
							totaleAmmesso = NumberUtil.sum(totaleAmmesso, r.getImportoAmmessoFinanziamento());
						}
						logger.info("getTotaleAmmessoCultura(): ImportoAmmessoFinanziamento = "+r.getImportoAmmessoFinanziamento()+"; totaleAmmesso = "+totaleAmmesso);
				}
			}
		}
		logger.info("getTotaleAmmessoCultura() : totaleAmmesso finale = "+totaleAmmesso);
		return totaleAmmesso;		
	}	

	public DatiCumuloDeMinimisDTO caricaDatiCumuloDeMinimis(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idSoggettoBeneficiario)
			throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException,
			FormalParameterException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale","idSoggettoBeneficiario" };

		DatiCumuloDeMinimisDTO datiCumuloDeMinimis = null;

		// PK : Pacilio dice che non serve a nulla.... non cancello il metodo per non dover modificare l'interfaccia
//		 
//		ValidatorInput.verifyNullValue(nameParameter, idUtente,
//				identitaDigitale, idSoggettoBeneficiario);
//		String codiceFiscale = genericDAO.findSingleWhere(
//				new PbandiTSoggettoVO(
//						new BigDecimal(idSoggettoBeneficiario)))
//				.getCodiceFiscaleSoggetto();
//		try {
//			datiCumuloDeMinimis = beanUtil.transform(moniDAO.calcolaCumulo(codiceFiscale, DateUtil.getDataOdierna()), DatiCumuloDeMinimisDTO.class);
//		} catch (CumuloDeMinimisException e) {
//			logger.info("Errore nel calcolo del cumulo de minimis. Restituisco un dettaglio fittizio (NaN)."+e.getMessage());
//		}
			

	 
		return setDefaultValueIfDatiCumuloDeMinimisIsNull(datiCumuloDeMinimis);
	}

	private DatiCumuloDeMinimisDTO setDefaultValueIfDatiCumuloDeMinimisIsNull(DatiCumuloDeMinimisDTO datiCumuloDeMinimis) {
		
		if(datiCumuloDeMinimis == null){
			datiCumuloDeMinimis = new DatiCumuloDeMinimisDTO();
			datiCumuloDeMinimis.setCumulo(Double.NaN);
			datiCumuloDeMinimis.setImportoDisponibile(Double.NaN);
			datiCumuloDeMinimis.setImportoSuperato(Double.NaN);
		}
		
		return datiCumuloDeMinimis;
	}

	public AttivitaPregresseDTO[] caricaAttivitaPregresse(Long idUtente,
			String identitaIride, Long idProgetto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiGeneraliException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };
 
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaIride, idProgetto);
			
			List<AttivitaPregresseVO> attivita = progettoManager.getAttivitaPregresse(idProgetto);
			List<AttivitaPregresseDTO> result = new ArrayList<AttivitaPregresseDTO>();
			if (!ObjectUtil.isEmpty(attivita)) {
				AttivitaPregresseDTO dto = null;
				List<RigoAttivitaPregresseDTO> righe = null;
				for (AttivitaPregresseVO vo : attivita){
					if (dto == null) {
						dto = new AttivitaPregresseDTO();
						dto.setData(DateUtil.utilToSqlDate(vo.getData()));
						dto.setDescAttivita(vo.getDescElemento());
						
						dto.setIdDocumentoIndex(vo.getIdDocumentoIndex() == null ? null : ""+vo.getIdDocumentoIndex());
						dto.setCodAttivita(vo.getCodElemento());
						righe = new ArrayList<RigoAttivitaPregresseDTO>();
					}
					/*
					 * Controllo che la data ed il tipo di attivita' siano uguali
					 */
					
					
					if (dto.getDescAttivita().equals(vo.getDescElemento()) && dto.getData()!=null && dto.getData().equals(vo.getData())) {
						/*
						 * l' attivita' e' la stessa di quella precendente.
						 */
						if (!StringUtil.isEmpty(vo.getNomeColonna())) {
							/*
							 * Aggiungo il rigo
							 */
							RigoAttivitaPregresseDTO rigo = new RigoAttivitaPregresseDTO();
							rigo.setEtichetta(vo.getNomeEtichetta());
							try {
								Object value = BeanMapper.getBeanValueByDBFieldNameByIntrospection(vo,vo.getNomeColonna());
								/*
								 * format degli importi
								 */
								if (value instanceof BigDecimal) {
									String importo = NumberUtil.getStringValue((BigDecimal)value);
									BeanMapper.setBeanValueByDBFieldNameByIntrospection(rigo, "valore", importo);

								} else {
									
									BeanMapper.setBeanValueByDBFieldNameByIntrospection(rigo, "valore", value);
								}
								righe.add(rigo);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						/*
						 * cambio di attivita.
						 * Aggiungo le righe all' attivita' precedente
						 */
						dto.setRighe(righe.toArray(new RigoAttivitaPregresseDTO[]{}));
						
						result.add(dto);
						
						
						dto = new AttivitaPregresseDTO();
						dto.setData(DateUtil.utilToSqlDate(vo.getData()));
						dto.setDescAttivita(vo.getDescElemento());
						
						dto.setIdDocumentoIndex(vo.getIdDocumentoIndex() == null ? null : ""+vo.getIdDocumentoIndex());
						dto.setCodAttivita(vo.getCodElemento());
						
						righe = new ArrayList<RigoAttivitaPregresseDTO>();
						
						if (!StringUtil.isEmpty(vo.getNomeColonna())) {
							/*
							 * Aggiungo il rigo
							 */
							RigoAttivitaPregresseDTO rigo = new RigoAttivitaPregresseDTO();
							rigo.setEtichetta(vo.getNomeEtichetta());
							try {
								Object value = BeanMapper.getBeanValueByDBFieldNameByIntrospection(vo,vo.getNomeColonna());
								/*
								 * format degli importi
								 */
								if (value instanceof BigDecimal) {
									String importo = NumberUtil.getStringValue((BigDecimal)value);
									BeanMapper.setBeanValueByDBFieldNameByIntrospection(rigo, "valore", importo);

								} else {
									
									BeanMapper.setBeanValueByDBFieldNameByIntrospection(rigo, "valore", value);
								}
								righe.add(rigo);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
				}
				/*
				 * Aggiungo l'ultima attivita
				 */
				dto.setRighe(righe.toArray(new RigoAttivitaPregresseDTO[]{}));
				
				result.add(dto);
			}
			return result.toArray(new AttivitaPregresseDTO[]{});
	}
	
	
	
	
	// PBANDI_R_SOGGETTI_CORRELATI --> settare ID_TIPO_SOGGETTO_CORRELATO = 21 (delegato). la tab � in join con :
	// PBANDI_R_SOGG_PROG_SOGG_CORREL (lega soggetto a progetto)
	public  DelegatoDTO[] findDelegati(Long idUtente,String identitaIride,Long idProgetto ) throws CSIException,
	SystemException, UnrecoverableException,
	GestioneDatiGeneraliException {
		logger.info("findDelegati idProgetto"+idProgetto);
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaIride, idProgetto);
	    DelegatoVO delegatoVO = new DelegatoVO();
	    delegatoVO.setIdProgetto(idProgetto);
		List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
		return	 beanUtil.transform(delegati,DelegatoDTO.class);
	}

	public  TipoAllegatoDTO[] findTipoAllegati(Long idUtente,String identitaIride,Long progrBandoLineaIntervento,
			String codTipoDocIndex, Long idDichiarazione, Long idProgetto) throws CSIException,
	SystemException, UnrecoverableException,
	GestioneDatiGeneraliException {
		String[] nameParameter = { "idUtente", "identitaDigitale","progrBandoLineaIntervento","codTipoDocIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaIride, progrBandoLineaIntervento,codTipoDocIndex);
		logger.info("\n\n\nfindTipoAllegati progrBandoLineaIntervento:"+progrBandoLineaIntervento+
				" ,codTipoDocIndex "+codTipoDocIndex);
		
		TipoAllegatoDTO[] tipoAllegatiDTOResult = null;
		BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, codTipoDocIndex);
		
		if(idDichiarazione != null && idDichiarazione > 0){
			TipoAllegatoDichiarazioneVO filtroTipoAllegato = new TipoAllegatoDichiarazioneVO();
			filtroTipoAllegato.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			filtroTipoAllegato.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			filtroTipoAllegato.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazione));
			tipoAllegatiDTOResult = beanUtil.transform(genericDAO.findListWhere(filtroTipoAllegato), TipoAllegatoDTO.class);
		}
		else if(idProgetto != null && idProgetto > 0){
			TipoAllegatoProgettoVO filtroTipoAllProg = new TipoAllegatoProgettoVO();
			filtroTipoAllProg.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			filtroTipoAllProg.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			filtroTipoAllProg.setIdProgetto(BigDecimal.valueOf(idProgetto));
			tipoAllegatiDTOResult = beanUtil.transform(genericDAO.findListWhere(filtroTipoAllProg), TipoAllegatoDTO.class);		
		}
		
		if(tipoAllegatiDTOResult == null || tipoAllegatiDTOResult.length == 0){
			//Non esistono allegati gi� fleggati allora carico i tipi allegati per un particolare bando linea
			TipoAllegatoVO tipoAllegatoVO = new TipoAllegatoVO();
			tipoAllegatoVO.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			tipoAllegatoVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			List<TipoAllegatoVO> tipoAllegatiVO = genericDAO.findListWhere(tipoAllegatoVO);
			tipoAllegatiDTOResult = beanUtil.transform(tipoAllegatiVO, TipoAllegatoDTO.class);
		}
		return	 tipoAllegatiDTOResult;
	}

	public  TipoAllegatoDTO[] findTipoAllegatiErogazione(Long idUtente,String identitaIride,
			Long progrBandoLineaIntervento,
			String codCausaleErogazione, Long idProgetto) throws CSIException,
	SystemException, UnrecoverableException,
	GestioneDatiGeneraliException {
		String[] nameParameter = { "idUtente", "identitaDigitale","progrBandoLineaIntervento","codCausaleErogazione" }; 
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaIride, progrBandoLineaIntervento,codCausaleErogazione);
		logger.info("\n\n\nfindTipoAllegatiErogazione progrBandoLineaIntervento:"+progrBandoLineaIntervento+
				" ,codCausaleErogazione "+codCausaleErogazione);
		
		BigDecimal idCausaleErogazione = decodificheManager.decodeDescBreve(PbandiDCausaleErogazioneVO.class, codCausaleErogazione);
		
		BigDecimal idMacroAllegati = decodificheManager.decodeDescBreve(PbandiDMacroSezioneModuloVO.class,
				GestioneDatiDiDominioSrv.DESC_BREVE_MACRO_SEZ_ALLEGATI);
		
		AllegatoVO allegatoVO=new AllegatoVO();
		allegatoVO.setIdMacroSezioneModulo(idMacroAllegati);
		
		BigDecimal idTipoDocIndex = richiestaErogazioneBusiness.getIdTipoDocIndex(codCausaleErogazione);
		allegatoVO.setIdTipoDocumentoIndex(idTipoDocIndex);
		allegatoVO.setAscendentOrder("numOrdinamentoMicroSezione");
		allegatoVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
		
		List<AllegatoVO> allegati = genericDAO.findListWhere(allegatoVO);
		Map <String,String> mapProps=new HashMap<String,String>();
		mapProps.put("contenutoMicroSezione","descTipoAllegato");
		mapProps.put("idTipoDocumentoIndex", "idTipoDocumentoIndex");
		mapProps.put("progrBandoLineaIntervento", "progrBandoLineaIntervento");
		mapProps.put("numOrdinamentoMicroSezione", "numOrdinamentoMicroSezione");
		mapProps.put("idMicroSezioneModulo", "idMicroSezioneModulo");
		
		TipoAllegatoDTO[] resultList = beanUtil.transform(allegati, TipoAllegatoDTO.class, mapProps);
		
		List<PbandiWProgettoDocAllegVO> progettoDocAlleg = beanUtil.transformToArrayList(resultList, PbandiWProgettoDocAllegVO.class);
		
		if(progettoDocAlleg != null && progettoDocAlleg.size() > 0){
			
				List<TipoAllegatoProgettoVO> arrayFiltroVO = beanUtil.transformToArrayList(resultList, TipoAllegatoProgettoVO.class);
				arrayFiltroVO.get(0).setIdProgetto(new BigDecimal(idProgetto));
				TipoAllegatoProgettoVO[] tipoAllegatiList = genericDAO.findWhere(Condition.filterBy(arrayFiltroVO));
				if(tipoAllegatiList != null && tipoAllegatiList.length > 0){
					resultList = beanUtil.transform(tipoAllegatiList , TipoAllegatoDTO.class);
				}
				
		}
		
		return resultList;
	 
		 
	}

	public CoordinateBancarieDTO getCoordinateBancarie(Long idUtente,
			String identitaIride, Long idProgetto, Long idSoggettoBeneficiario)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDatiGeneraliException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto","idSoggettoBeneficiario" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaIride, idProgetto,idSoggettoBeneficiario);
		logger.info("\n\n\n\n\ngetCoordinateBancarie idProgetto:"+idProgetto+
				" ,idSoggettoBeneficiario "+idSoggettoBeneficiario);
		CoordinateBancarieDTO coordinateBancarieDTO=new CoordinateBancarieDTO();
		 EstremiBancariVO vo=new EstremiBancariVO();
		 vo.setIdSoggetto(BigDecimal.valueOf(idSoggettoBeneficiario));
		 vo.setIdProgetto(BigDecimal.valueOf(idProgetto));
		 List<EstremiBancariVO> vos = genericDAO.findListWhere(vo);
		 if(!isEmpty(vos)){
			   coordinateBancarieDTO = beanUtil.transform(vos.get(0), CoordinateBancarieDTO.class) ;
			   logger.shallowDump(coordinateBancarieDTO, "info");
		 }
		 return coordinateBancarieDTO;
	}

	public EsitoOperazioneSalvaTipoAllegati salvaTipoAllegati(Long idUtente,
			String identitaIride, TipoAllegatoDTO[] tipoAllegatiDTO)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDatiGeneraliException {
		String[] nameParameter = {"idUtente", "identitaIride"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride);
		ValidatorInput.verifyArrayNotEmpty("tipoAllegatiDTO", tipoAllegatiDTO);
		EsitoOperazioneSalvaTipoAllegati esito = new EsitoOperazioneSalvaTipoAllegati();
		//Memorizzare i tipo allegati nella tabella PBANDI_R_DICH_SPESA_DOC_ALLEG 
		//TODO: check if idDichiarazione is null || idProgetto is null
		Long idDichiarazioneSpesa = tipoAllegatiDTO[0].getIdDichiarazioneSpesa();
		Long idProgetto = tipoAllegatiDTO[0].getIdProgetto();
		
		if((idDichiarazioneSpesa == null || idDichiarazioneSpesa == 0) && idProgetto != null){
			List<PbandiWProgettoDocAllegVO> progettoDocAlleg = beanUtil.transformToArrayList(tipoAllegatiDTO, PbandiWProgettoDocAllegVO.class);
			if(progettoDocAlleg != null && progettoDocAlleg.size() > 0){
				try{
					//TipoAllegatoProgettoVO filtroVO = new TipoAllegatoProgettoVO(); 
					//filtroVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
					
					List<TipoAllegatoProgettoVO> arrayFiltroVO = beanUtil.transformToArrayList(tipoAllegatiDTO, TipoAllegatoProgettoVO.class);
					List <TipoAllegatoProgettoVO> tipoAllegatiList = genericDAO.findListWhere(Condition.filterBy(arrayFiltroVO));
					if(tipoAllegatiList == null || tipoAllegatiList.isEmpty()){
						genericDAO.insert(progettoDocAlleg);
					}
					else{
						genericDAO.insertOrUpdateExisting(progettoDocAlleg);
					}
					esito.setEsito(Boolean.TRUE);
				}catch(Exception ex){
					esito.setEsito(Boolean.FALSE);
					esito.setMessage("Errore durante il salvataggio");
				}
			}
		}
		else if(idDichiarazioneSpesa != null ){
			List<PbandiRDichSpesaDocAllegVO> dichSpesaDocAlleg = beanUtil.transformToArrayList(tipoAllegatiDTO, PbandiRDichSpesaDocAllegVO.class);
			if(dichSpesaDocAlleg != null && dichSpesaDocAlleg.size() > 0){
				try{
					TipoAllegatoDichiarazioneVO filtroVO = new TipoAllegatoDichiarazioneVO(); 
					filtroVO.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazioneSpesa));
					List <TipoAllegatoDichiarazioneVO> tipoAllegatiList = genericDAO.findListWhere(filtroVO);
					if(tipoAllegatiList == null || tipoAllegatiList.isEmpty()){
						genericDAO.insert(dichSpesaDocAlleg);
					}
					else{
						genericDAO.insertOrUpdateExisting(dichSpesaDocAlleg);
					}
					esito.setEsito(Boolean.TRUE);
				}catch(Exception ex){
					esito.setEsito(Boolean.FALSE);
					esito.setMessage("Errore durante il salvataggio");
				}
			} else {
				esito.setEsito(Boolean.FALSE);
				esito.setMessage("Nessun elemento da salvare");
			}
		}
		return esito;
	}
	
	public DatiPiuGreenDTO findDatiPiuGreen(Long idUtente, String identitaDigitale, Long idProgettoFinanziamento)
		throws CSIException, SystemException, UnrecoverableException,
		GestioneDatiGeneraliException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgettoFinanziamento"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idProgettoFinanziamento);
		logger.info("findDatiPiuGreen(): idProgettoFinanziamento:"+idProgettoFinanziamento);
		
		DatiPiuGreenDTO datiPiuGreenDTO = new DatiPiuGreenDTO();
		
		// Verifica se il bando-linea del progetto ha la regola BR44.
		try {
			datiPiuGreenDTO.setRegolaBR44Attiva(regolaManager.isRegolaApplicabileForProgetto(idProgettoFinanziamento, "BR44"));
		} catch (Exception e ) {
			logger.warn("Errore nella verifica della regola BR44 per il progetto["+idProgettoFinanziamento+"] "+e.getMessage());
		}
		
		logger.info("datiPiuGreenDTO.getRegolaBR44Attiva() = "+datiPiuGreenDTO.getRegolaBR44Attiva());
		
		if (datiPiuGreenDTO.getRegolaBR44Attiva()) {
			
			if (genericDAO == null)
				logger.error("genericDAO == null");
			
			// Verifica se il progetto finanziamento ha associato un progetto contributo.
			PbandiTProgettoVO vo = new PbandiTProgettoVO();
			vo.setIdProgettoFinanz(new BigDecimal(idProgettoFinanziamento));
			vo = genericDAO.findSingleOrNoneWhere(vo);
			if (vo != null)
				datiPiuGreenDTO.setIdProgettoContributo(vo.getIdProgetto().longValue());
			
			// Verifica se in PBANDI_T_DICHIARAZIONE_SPESA esiste una DS Integrativa NON CHIUSA associata al progetto contributo.
			if (vo != null && vo.getIdProgetto() != null) {
				PbandiTDichiarazioneSpesaVO dsContributoFiltro = new PbandiTDichiarazioneSpesaVO();
				dsContributoFiltro.setIdProgetto(vo.getIdProgetto());
				dsContributoFiltro.setIdTipoDichiarazSpesa(new BigDecimal(Constants.ID_TIPO_DICHIARAZIONE_INTEGRATIVA));
				dsContributoFiltro.setDescendentOrder("idDichiarazioneSpesa");
				List<PbandiTDichiarazioneSpesaVO> listaDS = genericDAO.findListWhere(dsContributoFiltro);
				if (listaDS.size() > 0) {
					PbandiTDichiarazioneSpesaVO ds = listaDS.get(0);
					if (ds != null)
						datiPiuGreenDTO.setIdDichSpesaIntegrativa(ds.getIdDichiarazioneSpesa().longValue());
				}		
			}
			
		}

		logger.info("findDatiPiuGreen(): RegolaBR44Attiva = "+datiPiuGreenDTO.getRegolaBR44Attiva()+"; IdProgettoContributo:"+datiPiuGreenDTO.getIdProgettoContributo()+"; IdDichSpesaIntegrativa:"+datiPiuGreenDTO.getIdDichSpesaIntegrativa());
		return datiPiuGreenDTO;
	}
	
	private TipoAllegatoDTO[] findTipoAllegatiFineProgetto(Long progrBandoLineaIntervento,
			BigDecimal idTipoDocumentoIndex, Long idDichiarazione) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiGeneraliException{
		
		TipoAllegatoDTO[] tipoAllegatiDTOResult = null;
		
		if(idDichiarazione != null && idDichiarazione > 0){
			TipoAllegatoDichiarazioneVO filtroTipoAllegato = new TipoAllegatoDichiarazioneVO();
			filtroTipoAllegato.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			filtroTipoAllegato.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			filtroTipoAllegato.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazione));
			tipoAllegatiDTOResult = beanUtil.transform(genericDAO.findListWhere(filtroTipoAllegato), TipoAllegatoDTO.class);
		}
		
		if(tipoAllegatiDTOResult == null || tipoAllegatiDTOResult.length == 0){
			//Non esistono allegati gi� fleggati allora carico i tipi allegati per un particolare bando linea
			TipoAllegatoVO tipoAllegatoVO = new TipoAllegatoVO();
			tipoAllegatoVO.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			tipoAllegatoVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			List<TipoAllegatoVO> tipoAllegatiVO = genericDAO.findListWhere(tipoAllegatoVO);
			tipoAllegatiDTOResult = beanUtil.transform(tipoAllegatiVO, TipoAllegatoDTO.class);
		}
		return	 tipoAllegatiDTOResult;
	}
	
	// Dato l'id di una DS finanziamento, restituisce l'id della Ds Integrativa contributo.
	public DichiarazioneSpesaDTO findDsIntegrativaContributo(Long idUtente, String identitaDigitale, Long idDichiarazioneSpesaFinanziamento)
	throws CSIException, SystemException, UnrecoverableException,
	GestioneDatiGeneraliException {
	
	String[] nameParameter = { "idUtente", "identitaDigitale", "idDichiarazioneSpesaFinanziamento"};
	ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDichiarazioneSpesaFinanziamento);
	logger.info("findDsIntegrativaContributo(): idDichiarazioneSpesaFinanziamento:"+idDichiarazioneSpesaFinanziamento);
	
	DichiarazioneSpesaDTO dichiarazioneSpesaDTO = new DichiarazioneSpesaDTO();
	
	if (idDichiarazioneSpesaFinanziamento == null)
		return dichiarazioneSpesaDTO;
	
	// Cerca la Ds in PBANDI_T_DICHIARAZIONE_SPESA.
	PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO();
	vo.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneSpesaFinanziamento));
	vo = genericDAO.findSingleWhere(vo);
	
	if (vo != null && vo.getIdDichiarazioneSpesaColl() != null) {
		dichiarazioneSpesaDTO.setIdDichiarazioneSpesa(vo.getIdDichiarazioneSpesaColl().longValue());
		dichiarazioneSpesaDTO.setIdProgetto(vo.getIdProgetto().longValue());
	}
	
	return dichiarazioneSpesaDTO;
	}

	public Boolean ottieniFlagInvioCartaceo(Long idUtente,
			String identitaDigitale, Long idDocumentoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDatiGeneraliException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDocumentoIndex"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDocumentoIndex);
		
		logger.info("ottieniFlagInvioCartaceo(): idDocumentoIndex:"+idDocumentoIndex);
		PbandiTDocumentoIndexVO filtroDocumentoIndex = new PbandiTDocumentoIndexVO();
		filtroDocumentoIndex.setIdDocumentoIndex(new BigDecimal(idDocumentoIndex));
		filtroDocumentoIndex = genericDAO.findSingleOrNoneWhere(filtroDocumentoIndex);
		return filtroDocumentoIndex != null && filtroDocumentoIndex.getFlagFirmaCartacea().equals("S");
	}
	
	// Jira PBANDI-2773: fatto per affidamenti, ma usabile in generale poich� 
	// parametrizzato con idEntita e idTarget.  
	public NotificaDTO[] findNotifiche(Long idUtente,String identitaIride,
			Long idEntita, Long idTarget) throws CSIException,
	SystemException, UnrecoverableException,
	GestioneDatiGeneraliException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idEntita","idTarget" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaIride, idEntita,idTarget);
		logger.info("\n\n\nfindNotifiche(): idEntita = "+idEntita+"; idTarget = "+idTarget);
		
		PbandiTNotificaProcessoVO filtro = new PbandiTNotificaProcessoVO();
		filtro.setIdEntita(new BigDecimal(idEntita));
		filtro.setIdTarget(new BigDecimal(idTarget));
		filtro.setDescendentOrder("dtNotifica");
		
		ArrayList<PbandiTNotificaProcessoVO> notifiche = null;
		notifiche = (ArrayList<PbandiTNotificaProcessoVO>) genericDAO.findListWhere(filtro);
		
		NotificaDTO[] result = beanUtil.transform(notifiche, NotificaDTO.class);		
		return	 result;
	}
	
	public NotificaDTO findNotifica(Long idUtente,String identitaIride,
			Long idNotifica) throws CSIException, SystemException, UnrecoverableException,
					GestioneDatiGeneraliException {
		
		logger.begin();
		String[] nameParameter = { "idUtente", "identitaDigitale","idNotifica" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaIride, idNotifica);
		logger.info(" idNotifica = "+idNotifica);
		
		PbandiTNotificaProcessoVO filtro = new PbandiTNotificaProcessoVO();
		filtro.setIdNotifica(new BigDecimal(idNotifica));
		
		PbandiTNotificaProcessoVO notif = genericDAO.findSingleWhere(filtro);
		
		NotificaDTO result = beanUtil.transform(notif, NotificaDTO.class);
		logger.end();
		return	 result;
	}

	public Integer aggiornaStatoNotifiche(Long idUtente,String identitaIride,
			long[] elencoIdNotifiche, String stato) throws FormalParameterException, GestioneDatiGeneraliException  { 
		
		logger.begin();
		Integer ret = 0;
		String[] nameParameter = { "idUtente", "identitaDigitale", "idNotifica", "stato" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, elencoIdNotifiche, stato);
		logger.info(" idNotifica = "+elencoIdNotifiche);
		logger.info(" stato = "+stato);
		
		// Stati delle notifiche:
		// I : Inviata
		// C : chiusa/archiviata
		// R : read/letta
		if(stato!=null && StringUtil.contieneSolo(stato.toUpperCase(), "ICR")){

			// devo aggiornare piu' notifiche
			ret = progettoManager.aggiornaStatoNotifiche(elencoIdNotifiche, stato.toUpperCase());

		}else{
			logger.warn(" parametro stato non consentito");
		}
		
		if (ret==0)
			logger.info(" Nessuna notifica aggiornata");
		else
			logger.info(" Aggiornate " + ret + "notifiche.");

		logger.end();
		return ret;
	}
	
	public NotificaDTO[] findElencoNotifiche(
			Long idUtente, String identitaIride, Long idBandoLineaIntervento, Long idSoggettoBeneficiario, long[] idProgetti, String codiceRuolo) 
				throws FormalParameterException, GestioneDatiGeneraliException  {
		
		logger.begin();
		
		NotificaDTO[] arr = null;
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBandoLineaIntervento", "idSoggettoBeneficiario"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaIride, idBandoLineaIntervento, idSoggettoBeneficiario);
		logger.info(" idBandoLineaIntervento = "+idBandoLineaIntervento);
		logger.info(" idSoggettoBeneficiario = "+idSoggettoBeneficiario);
		logger.info(" idProgetti = "+idProgetti);
		logger.info(" codiceRuolo = "+codiceRuolo);
		
		List<PbandiTNotificaProcessoVO> listaNotifiche = 
			 progettoManager.caricaListaNotifiche(idBandoLineaIntervento ,idSoggettoBeneficiario, idProgetti, codiceRuolo);
			
		if(listaNotifiche!=null){
			logger.info(" trovata listaNotifiche di dimensione = "+listaNotifiche.size());
		}else{
			logger.info(" listaNotifiche NULL");
		}

		arr = beanUtil.transform(listaNotifiche, NotificaDTO.class);
		
		if(arr != null)
			logger.info(" NotificaDTO[X] length="+arr.length);
		else
			logger.info(" NotificaDTO[X] null");
		
		logger.end();
		return arr;
	}
			
}