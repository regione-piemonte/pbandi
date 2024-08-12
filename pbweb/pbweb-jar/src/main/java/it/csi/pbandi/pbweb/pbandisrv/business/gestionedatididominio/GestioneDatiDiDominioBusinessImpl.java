package it.csi.pbandi.pbweb.pbandisrv.business.gestionedatididominio;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.BilancioManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ComuniManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.AttivitaAtecoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ComuneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ConfigurationDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.CoordinateBancarieDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Entita;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.FormaGiuridicaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.InfoLineaDiInterventoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.NazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.PeriodoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ProvinciaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionebackoffice.GestioneBackOfficeException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AttivitaEconomicaAtecoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.LineaDiInterventoPadreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoEnteCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDBancaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDComuneEsteroVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDFormaGiuridicaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDModalitaErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDProvinciaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoLineaInterventoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAgenziaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTBandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPeriodoVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.Constants;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class GestioneDatiDiDominioBusinessImpl extends BusinessImpl implements
		GestioneDatiDiDominioSrv {
	
	private ComuniManager comuniManager;
	private ConfigurationManager configurationManager;
	private BilancioManager bilancioManager;
	
	@Autowired
	private GenericDAO genericDAO;

	public void setComuniManager(ComuniManager comuniManager) {
		this.comuniManager = comuniManager;
	}

	public ComuniManager getComuniManager() {
		return comuniManager;
	}
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findEntiDiCompetenzaPerTipo(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.String descBreveTipoAnagrafica)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException {
	 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"descBreveTipoAnagraficaSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, descBreveTipoAnagrafica);

			final String tipoEnteCompetenza = descBreveTipoAnagrafica
					.substring(0, descBreveTipoAnagrafica.indexOf('-'));

			EnteDiCompetenzaVO query = new EnteDiCompetenzaVO();
			query.setDescBreveTipoEnteCompetenz(tipoEnteCompetenza);
			List<EnteDiCompetenzaVO> entiVO = genericDAO.findListWhere(query);

			Map<String, String> map = new HashMap<String, String>();
			map.put("idEnteCompetenza", "id");
			map.put("descEnte", "descrizione");
			map.put("descBreveEnte", "descrizioneBreve");
			map.put("dtInizioValidita", "dataInizioValidita");
			map.put("dtFineValidita", "dataFineValidita");
			return beanUtil.transform(entiVO, Decodifica.class, map);
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile caricare gli enti di competenza.", e);
		}  
	}

	public Decodifica[] findEntiDiCompetenzaFiltrato(Long idUtente, String identitaDigitale, String descBreveTipoAnagrafica, Long idSoggetto, String ruolo)
			throws CSIException, SystemException, UnrecoverableException, GestioneDatiDiDominioException {
		
		Decodifica[] ret = null;
		List<SoggettoEnteCompetenzaVO> entiVO;
		if (Constants.RUOLO_ADG_IST_MASTER.equalsIgnoreCase(ruolo) || Constants.RUOLO_OI_IST_MASTER.equalsIgnoreCase(ruolo)) {
			if (ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER))
				entiVO = bilancioManager.findEntiCompetenzaSoggetto(idSoggetto,"ADG","descEnte");
			else
				entiVO = bilancioManager.findEntiCompetenzaSoggetto(idSoggetto,"OI","descEnte");
		
			Map<String, String> map = new HashMap<String, String>();
			map.put("idEnteCompetenza", "id");
			map.put("descEnte", "descrizione");
			map.put("descBreveEnte", "descrizioneBreve");
			return beanUtil.transform(entiVO, Decodifica.class, map);
		} else {
			ret = findEntiDiCompetenzaPerTipo(idUtente, identitaDigitale, descBreveTipoAnagrafica);
		}		
		return ret;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica findDecodifica(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long id, java.lang.String chiave)
			throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException {
			return getBeanUtil().transform(
					decodificheManager.findDecodifica(chiave, id),
					Decodifica.class);
	}

	/**
	 * 
	 * @param idUtente
	 * @param identitaDigitale
	 * @param descrizioneBreve
	 * @param chiave
	 * @return Una singola decodifica senza considerare la data di fine
	 *         validita'
	 * @throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException
	 */
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica findDecodificaPerDescrizioneBreve(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.String descrizioneBreve, java.lang.String chiave)
			throws it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException {

			return getBeanUtil().transform(
					decodificheManager.findDecodifica(chiave, descrizioneBreve), Decodifica.class);

	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheConEsclusioni(
			java.lang.Long idUtente, String identitaDigitale, String chiave,
			String[] codiciDaEscludere) throws GestioneDatiDiDominioException,
			UnrecoverableException {
		Decodifica[] decodifiche = null;

		DecodificaDTO[] list;
		try {
			list = decodificheManager.findDecodifiche(chiave);
			if (list != null) {
				List<DecodificaDTO> decodificheValide = new ArrayList<DecodificaDTO>();
				for (DecodificaDTO decodifica : list) {
					if (!Arrays.asList(codiciDaEscludere).contains(
							decodifica.getDescrizioneBreve()))
						decodificheValide.add(decodifica);
				}
				decodifiche = beanUtil.transform(decodificheValide,
						Decodifica.class);
			}
		} catch (Exception e) {
			String message = "Impossibile trovare decodifiche della chiave "
					+ chiave + ", ragione: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}

		return decodifiche;
	}

	public Decodifica[] findDecodifiche(java.lang.Long idUtente,
			String identitaDigitale, String chiave)
			throws GestioneDatiDiDominioException, UnrecoverableException {
	 
		System.out.println("findDecodifiche beanUtil="+beanUtil);
		System.out.println("findDecodifiche decodificheManager="+decodificheManager);
		
		try {
			return beanUtil.transform(decodificheManager
					.findDecodifiche(chiave), Decodifica.class);
		} catch (Exception e) {
			String message = "Impossibile trovare decodifiche della chiave "
					+ chiave + ", ragione: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}  
	}
	
	public Decodifica[] findDecodificheMultiProgr(java.lang.Long idUtente,
			String identitaDigitale, String chiave, Long idLineaDiIntervento)
			throws GestioneDatiDiDominioException, UnrecoverableException {
	 
		try {
			return beanUtil.transform(decodificheManager
					.findDecodificheMultiProgr(chiave, idLineaDiIntervento), Decodifica.class);
		} catch (Exception e) {
			String message = "Impossibile trovare decodifiche della chiave "
					+ chiave + ", ragione: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}  
	}

	public Decodifica[] findDecodificheStorico(Long idUtente,
			String identitaDigitale, String chiave)
			throws GestioneDatiDiDominioException, UnrecoverableException {
		 
		try {
			return beanUtil.transform(decodificheManager
					.findDecodificheStorico(chiave), Decodifica.class);
		} catch (Exception e) {
			String message = "Impossibile trovare decodifiche della chiave "
					+ chiave + ", ragione: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}  
	}

	public Decodifica[] findDecodificheFiltrato(Long idUtente,
			String identitaDigitale, String chiave, String nomeCampo,
			String valore) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			GestioneDatiDiDominioException {
	 
		Decodifica[] result = null;
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"chiave", "nomeCampo", "valore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, chiave, nomeCampo, valore);

			result = beanUtil.transform(decodificheManager
					.findDecodificheFiltrato(chiave, nomeCampo, valore),
					Decodifica.class);
		} catch (Exception e) {
			throw new GestioneDatiDiDominioException("Impossibile caricare "
					+ chiave, e);
		}  
		return result;
	}
	
	public Decodifica[] findDecodificheFiltratoMultiProgr(Long idUtente,
			String identitaDigitale, String chiave, String nomeCampo,
			String valore, Long idLineaDiIntervento) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			GestioneDatiDiDominioException {
	 
		Decodifica[] result = null;
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"chiave", "nomeCampo", "valore", "idLineaDiIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, chiave, nomeCampo, valore, idLineaDiIntervento);

			result = beanUtil.transform(decodificheManager
					.findDecodificheFiltratoMultiProgr(chiave, nomeCampo, valore, idLineaDiIntervento),
					Decodifica.class);
		} catch (Exception e) {
			throw new GestioneDatiDiDominioException("Impossibile caricare "
					+ chiave, e);
		}  
		return result;
	}

	public Decodifica[] findDecodificheFiltratoStorico(Long idUtente,
			String identitaDigitale, String chiave, String nomeCampo,
			String valore) throws CSIException, SystemException,
			UnrecoverableException, GestioneDatiDiDominioException {
 
		Decodifica[] result = null;
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"chiave", "nomeCampo", "valore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, chiave, nomeCampo, valore);

			result = beanUtil.transform(decodificheManager
					.findDecodificheFiltratoStorico(chiave, nomeCampo, valore),
					Decodifica.class);
		} catch (Exception e) {
			throw new GestioneDatiDiDominioException("Impossibile caricare "
					+ chiave, e);
		}  
		return result;
	}

	public ProvinciaDTO[] findProvincie(Long idUtente, String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

		ProvinciaDTO[] result=null;
			try {
				PbandiDProvinciaVO[] province = comuniManager.findProvince();
				Map<String, String> map=new HashMap();
				map.put("descProvincia","descrizione");
				map.put("idProvincia","idProvincia");
				map.put("idRegione","idRegione");
				map.put("siglaProvincia","sigla");
				result=beanUtil.transform(province, ProvinciaDTO.class, map);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new GestioneDatiDiDominioException(e.getMessage(),e);
			}
		return result;
	}

	public ComuneDTO[] findComuni(Long idUtente, String identitaDigitale,
			Long idProvincia) throws CSIException, SystemException,
			UnrecoverableException, GestioneDatiDiDominioException {
	 
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

		ComuneDTO[] result=null;
		try {
			PbandiDComuneVO[] comuni = comuniManager.findComune(idProvincia);
			Map<String, String> map=new HashMap();
			map.put("cap","cap");
			map.put("codIstatComune","codIstatComune");
			map.put("descComune","descrizione");
			map.put("idComune","idComune");
			map.put("idProvincia","idProvincia");
			result=beanUtil.transform(comuni, ComuneDTO.class, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		return result;
	}

	public ConfigurationDTO getConfiguration(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneDatiDiDominioException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);
		ConfigurationDTO result = null;
		try {
			result = beanUtil.transform(configurationManager.getConfiguration(),ConfigurationDTO.class);			
		} catch (Exception e) {
			logger.error("Errore durante la lettura della configuration.", e);
		}  

		return result;
	}

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public Decodifica[] findNazioni(Long idUtente, String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

		Decodifica[] result=null;
			try {
				PbandiDNazioneVO pbandiDNazioneVO=new PbandiDNazioneVO();
				pbandiDNazioneVO.setAscendentOrder("descNazione");
				List<PbandiDNazioneVO> nazioni = genericDAO.findListWhere(pbandiDNazioneVO);
				Map<String, String> map=new HashMap();
				map.put("idNazione","id");
				map.put("descNazione","descrizione");
				result=beanUtil.transform(nazioni, Decodifica.class, map);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new GestioneDatiDiDominioException(e.getMessage(),e);
			}
		return result;
	}

	public Decodifica[] findComuniEsteri(Long idUtente,
			String identitaDigitale, Long idNazione) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
	 
		String[] nameParameter = { "idUtente", "identitaDigitale" ,"idNazione"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale,idNazione);

		Decodifica[] result=null;
		try {
			PbandiDComuneEsteroVO pbandiDComuneEsteroVO = new PbandiDComuneEsteroVO();
			pbandiDComuneEsteroVO.setIdNazione(new BigDecimal( idNazione));
			pbandiDComuneEsteroVO.setAscendentOrder("descComuneEstero");
			List<PbandiDComuneEsteroVO> comuniEsteri = genericDAO.findListWhere(pbandiDComuneEsteroVO);
			Map<String, String> map=new HashMap();
			map.put("idComuneEstero","id");
			map.put("descComuneEstero","descrizione");
			result=beanUtil.transform(comuniEsteri, Decodifica.class, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		 
		return result;
	}

	public CoordinateBancarieDTO checkIBAN(Long idUtente, String identitaDigitale, String iban) throws CSIException, SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {

/*Per verificare la corretta compilazione dell'IBAN occorre scomporlo e controllare che solo se :
 * in prima posizione ci siano i caratteri �IT� deve essere lungo 27 e in 3 e 4  posizione ci sia un numero
in 5 posizione ci sia una lettera . Che poi va riportata nel campo CIN
dalla posizione 6 alla 11 (5 ch) ci sia un codice numerico che � l'ABI, ossia il codice della banca. Questo lo si pu� verificare andando a leggere la tabella BANCHE e estrarne la decodifica trovata. Se non presente , visualizza al posto della descrizione della banca �Banca non presente in archivio�. I codice scomposta va riportato in corrispondenza dell'etichetta ABI con relativa descrizione
dalla posizione 11 alla 15 (5 ch) ci sia un codice numerico che � il CAB, ossia il codice della agenzia . Questo lo si pu� verificare andando a leggere la tabella AGENZIE  con il codice ABI  e estrarne la decodifica trovata. Se non presente , visualizza al posto della descrizione della banca �Agenzia  non presente in archivio� .Il codice scomposta va riportato in corrispondenza dell'etichetta CAB  con relativa descrizione
dalla posizione 16 alla 27 ci devono essere 12 caratteri  compresi anche di 0 non significativi in testa. Questo campo va messo in corrispondenza dell'etichetta Numero Conto Corrente

		 */
		 
		String[] nameParameter = { "idUtente", "identitaDigitale" ,"iban"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,iban);
		CoordinateBancarieDTO result = new CoordinateBancarieDTO();
		try {
			if (iban.length() > 2 && iban.substring(0,2).equalsIgnoreCase("IT")) {
				if (iban.length() != 27) throw new GestioneDatiDiDominioException("Lunghezza IBAN non valida");
				if (!(NumberUtil.isNumber(iban.substring(2,4)))) throw new GestioneDatiDiDominioException("caratteri 3 e 4 non numerici");
				
				String cin = iban.substring(4,5);
				if (!(StringUtil.isString(cin))) throw new GestioneDatiDiDominioException("codice cin non alfabetico");
				result.setCin(cin);
				
				String abi = iban.substring(5,10);
				if (!(NumberUtil.isNumber(abi))) throw new GestioneDatiDiDominioException("codice abi non numerico");
				result.setAbi(abi);
				
				PbandiDBancaVO bancaVo = new PbandiDBancaVO();
				bancaVo.setCodBanca(abi);
				try {
					bancaVo = genericDAO.findSingleWhere(bancaVo);
				}
				catch (Exception e) {
					result.setBanca("Agenzia non presente in archivio");
					result.setFiliale("Filiale non presente in archivio");
				}
				
				BigDecimal idBanca = null;
				if (bancaVo != null) {
					result.setBanca(bancaVo.getDescBanca());
					idBanca = bancaVo.getIdBanca();
					
					String cab = iban.substring(10,15);
					if (!(NumberUtil.isNumber(cab))) throw new GestioneDatiDiDominioException("codice cab non numerico");
					result.setCab(cab);

					PbandiTAgenziaVO agenziaVO = new PbandiTAgenziaVO();
					agenziaVO.setIdBanca(idBanca);
					agenziaVO.setCodAgenzia(cab);
					agenziaVO.setAscendentOrder("descAgenzia");
					
					Condition<PbandiTAgenziaVO> cond = Condition.filterBy(agenziaVO);

					try {
						agenziaVO = genericDAO.findFirstWhere(cond);
					}
					catch (Exception e) {
						result.setFiliale("Filiale non presente in archivio");
					}
					
					if (agenziaVO != null) {
						result.setFiliale(agenziaVO.getDescAgenzia());
					}
				}

				result.setIban(iban);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		 
		return result;
	}
	
	
	public Decodifica[] findModalitaErogazioneFiltratePerBilancio(Long idUtente,
			String identitaDigitale) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

		Decodifica[] result=null;
		try {
			PbandiDModalitaErogazioneVO filtro = new PbandiDModalitaErogazioneVO();
			filtro.setFlagBilancio("S");
			filtro.setAscendentOrder("DESC_MODALITA_EROGAZIONE");
			List<PbandiDModalitaErogazioneVO> list = genericDAO.findListWhere(filtro);
			Map<String, String> map=new HashMap();
			map.put("idModalitaErogazione","id");
			map.put("descModalitaErogazione","descrizione");
			result=beanUtil.transform(list, Decodifica.class, map);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		 
		return result;
	}

	public BilancioManager getBilancioManager() {
		return bilancioManager;
	}

	public void setBilancioManager(BilancioManager bilancioManager) {
		this.bilancioManager = bilancioManager;
	}

	public String findCodIgrueT13T14(Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {

	 
		String[] nameParameter = { "idUtente", "identitaDigitale" ,"progrBandoLineaIntervento"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaIntervento);
		
		String codIgrueT13T14 = null;
		try {
			
			LineaDiInterventoPadreVO lineaDiInterventoPadreVO = new LineaDiInterventoPadreVO();
			lineaDiInterventoPadreVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
			lineaDiInterventoPadreVO.setDescTipoLinea(DESC_TIPO_LINEA_NORMATIVA);
			
			lineaDiInterventoPadreVO = genericDAO.findSingleOrNoneWhere(lineaDiInterventoPadreVO);
			
			if(lineaDiInterventoPadreVO != null){
				codIgrueT13T14 = lineaDiInterventoPadreVO.getCodIgrueT13T14();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		} 
		
		return codIgrueT13T14;
	}

	public String findCodIgrueT13T14ByIdBando(Long idUtente,
			String identitaDigitale, Long idBando) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {

		 
		String[] nameParameter = { "idUtente", "identitaDigitale" ,"idBando"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idBando);
		
		String codIgrueT13T14 = null;
		try {
			
			LineaDiInterventoPadreVO lineaDiInterventoPadreVO = new LineaDiInterventoPadreVO();
			lineaDiInterventoPadreVO.setIdBando(new BigDecimal(idBando));
			lineaDiInterventoPadreVO.setDescTipoLinea(DESC_TIPO_LINEA_NORMATIVA);
			
			lineaDiInterventoPadreVO = genericDAO.findSingleOrNoneWhere(lineaDiInterventoPadreVO);
			
			if(lineaDiInterventoPadreVO != null){
				codIgrueT13T14 = lineaDiInterventoPadreVO.getCodIgrueT13T14();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		} 
		
		return codIgrueT13T14;
		
	}

	public Entita findEntita(Long idUtente, String identitaDigitale,
			String nomeEntita) throws CSIException, SystemException,
			UnrecoverableException, GestioneDatiDiDominioException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale" ,"nomeEntita"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, nomeEntita);
		try {
			Entita result = new Entita();
			PbandiCEntitaVO filterVO = new PbandiCEntitaVO();
			filterVO.setNomeEntita(nomeEntita);
			PbandiCEntitaVO entita = genericDAO.findSingleOrNoneWhere(filterVO);
			result.setIdEntita(NumberUtil.toLong(entita.getIdEntita()));
			result.setNomeEntita(entita.getNomeEntita());
			return result;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}  
		
	}

	public InfoLineaDiInterventoDTO[] getLineaDiIntervento(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
		String[] nameParameter = { "idUtente", "identitaDigitale" ,"progrBandoLineaIntervento"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaIntervento);
		
		InfoLineaDiInterventoDTO[] infoLinee = null;
		try {
			
			LineaDiInterventoPadreVO lineaDiInterventoPadreVO = new LineaDiInterventoPadreVO();
			lineaDiInterventoPadreVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
			
			List<LineaDiInterventoPadreVO> linee = genericDAO.findListWhere(lineaDiInterventoPadreVO);
			
			infoLinee = beanUtil.transform(linee, InfoLineaDiInterventoDTO.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		
		return infoLinee;
	}

	
	public java.lang.String getContextSuffix(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale

			)
					throws it.csi.csi.wrapper.CSIException,
					it.csi.csi.wrapper.SystemException,
					it.csi.csi.wrapper.UnrecoverableException,
					it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException

			{
				String[] nameParameter = { "idUtente", "identitaDigitale" };
				ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
				
				try {
					logger.info("searching  contextSuffix ");
					String contextSuffix = configurationManager.getConfiguration().getServerConfiguration().getContextSuffix();
					logger.info("returning contextSuffix "+contextSuffix);
					return contextSuffix;
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new GestioneDatiDiDominioException(e.getMessage(),e);
				}
		
			}
	
	public java.lang.Boolean setFlagCartaceo(
		 Long idUtente,String identitaDigitale, Long idDocIndex)
					throws it.csi.csi.wrapper.CSIException,
					it.csi.csi.wrapper.SystemException,
					it.csi.csi.wrapper.UnrecoverableException
					,
					it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException
					{
						String[] nameParameter = { "idUtente", "identitaDigitale", "idDocIndex"};
						ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,idDocIndex);
						try {
							logger.info("\n\nsetFlagCartaceo for idDocIndex:"+idDocIndex);
							PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO();
							pbandiTDocumentoIndexVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocIndex));
							pbandiTDocumentoIndexVO.setFlagFirmaCartacea("S");
							genericDAO.update(pbandiTDocumentoIndexVO);
							return true;
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							throw new GestioneDatiDiDominioException(e.getMessage(),e);
						}
				
					}

	public FormaGiuridicaDTO[] findFormeGiuridiche(Long idUtente,
			String identitaDigitale, String flagPrivato) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
		String[] nameParameter = { "idUtente", "identitaDigitale"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		FormaGiuridicaDTO[] formeGiuridiche =null;
		try {
			logger.info("\n\nfindFormeGiuridiche , flagPrivato:"+flagPrivato);
			PbandiDFormaGiuridicaVO pbandiDFormaGiuridicaVO = new PbandiDFormaGiuridicaVO();
			if(!isEmpty(flagPrivato)){
				pbandiDFormaGiuridicaVO.setFlagPrivato(flagPrivato);
			}
			pbandiDFormaGiuridicaVO.setAscendentOrder("descFormaGiuridica");
			  List<PbandiDFormaGiuridicaVO> forme = genericDAO.findListWhere(pbandiDFormaGiuridicaVO);
			logger.info("trovate "+forme.size());
			formeGiuridiche = beanUtil.transform(forme, FormaGiuridicaDTO.class);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		return formeGiuridiche;
	}

	public AttivitaAtecoDTO[] findAttivitaAteco(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneDatiDiDominioException {
		String[] nameParameter = { "idUtente", "identitaDigitale"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		AttivitaAtecoDTO[] attivitaAtecoDTO =null;
		try {
			AttivitaEconomicaAtecoVO[]attivita = genericDAO.findAll(AttivitaEconomicaAtecoVO.class);
			attivitaAtecoDTO = beanUtil.transform(attivita, AttivitaAtecoDTO.class);
			
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		return attivitaAtecoDTO;
	}

	public NazioneDTO[] findNazioniCompleto(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneDatiDiDominioException {
		String[] nameParameter = { "idUtente", "identitaDigitale"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		NazioneDTO[] nazioniDTO =null;
		try {
			logger.info("\n\nfindNazioniCompleto");
			PbandiDNazioneVO filter=new PbandiDNazioneVO();
			filter.setAscendentOrder("descNazione");
			List<PbandiDNazioneVO>nazioni = genericDAO.findListWhere(new FilterCondition(filter ).and(Condition
					.validOnly(PbandiDNazioneVO.class)));
			
		
			nazioniDTO = beanUtil.transform(nazioni, NazioneDTO.class);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneDatiDiDominioException(e.getMessage(),e);
		}
		return nazioniDTO;
	}

	public Decodifica[] findDecodificheByProgrBandoLinea(Long idUtente,
			String identitaDigitale, String chiave,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
		logger.info("\n\n\nfindDecodificheByProgrBandoLinea ----> chiave "
			        +chiave+"\nprogrBandoLineaIntervento:"+progrBandoLineaIntervento);
		String[] nameParameter = { "idUtente", "identitaDigitale","progrBandoLineaIntervento"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,progrBandoLineaIntervento);
		try {
			BigDecimal idLineaDiInterventoNormativa = findIdLineaDiInterventoNormativa(progrBandoLineaIntervento);
			return beanUtil.transform(decodificheManager
					.findDecodificheMultiProgr(chiave,
							idLineaDiInterventoNormativa.longValue()), Decodifica.class);
		} catch (Exception e) {
			String message = "Impossibile trovare decodifiche della chiave "
					+ chiave + ", ragione: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}  
	}

 

	public Decodifica[] findDecodificheFiltratoByProgrBandoLinea(Long idUtente,
			String identitaDigitale, String chiave, String nomeCampo,
			String valore, Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDatiDiDominioException {
		logger.info("\n\n\nfindDecodificheFiltratoByProgrBandoLinea ----> chiave "
		        +chiave+"\nvalore "+valore+"\nprogrBandoLineaIntervento:"+progrBandoLineaIntervento);
		String[] nameParameter = { "idUtente", "identitaDigitale","progrBandoLineaIntervento"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,progrBandoLineaIntervento);
		try {
			BigDecimal idLineaDiInterventoNormativa = findIdLineaDiInterventoNormativa(progrBandoLineaIntervento);
			return beanUtil.transform(decodificheManager
					.findDecodificheFiltratoMultiProgr(chiave,nomeCampo,valore,
							idLineaDiInterventoNormativa.longValue()), Decodifica.class);
		} catch (Exception e) {
			String message = "Impossibile trovare decodifiche della chiave "
					+ chiave + ", ragione: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}  
	}
	
	
	
	
	
	
	
	private BigDecimal findIdLineaDiInterventoNormativa( Long progrBandoLineaIntervento) throws Exception{
		BigDecimal idNormativa=null;
		try {

			// Accede a Pbandi_R_Bando_Linea_Intervent per chiave;
			PbandiRBandoLineaInterventVO pbandiRBandoLineaInterventVO = new PbandiRBandoLineaInterventVO(); 			
			pbandiRBandoLineaInterventVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
			pbandiRBandoLineaInterventVO = genericDAO.findSingleOrNoneWhere(pbandiRBandoLineaInterventVO);

			// Ricava Normativa, Asse, Misura, Linea partendo da LineaDiIntervento.			
			if (pbandiRBandoLineaInterventVO.getIdLineaDiIntervento() != null) {
				
				BigDecimal idLineaDiIntervento = pbandiRBandoLineaInterventVO.getIdLineaDiIntervento();
				logger.info("pbandiRBandoLineaIntervent.IdLineaDiIntervento = "+idLineaDiIntervento);

				// Legge il dettaglio del bando linea per capirne il tipo (normativa, asse, etc).
				PbandiDLineaDiInterventoVO pbandiDLineaDiInterventoVO;
				pbandiDLineaDiInterventoVO = findPbandiDLineaDiInterventoVO(idLineaDiIntervento);
			 
				logger.info("pbandiDLineaDiIntervento.IdTipoLineaIntervento = "+pbandiDLineaDiInterventoVO.getIdTipoLineaIntervento());
				
				// Legge il tipo di linea
				PbandiDTipoLineaInterventoVO pbandiDTipoLineaInterventoVO = new PbandiDTipoLineaInterventoVO();
				pbandiDTipoLineaInterventoVO.setIdTipoLineaIntervento(pbandiDLineaDiInterventoVO.getIdTipoLineaIntervento());
				pbandiDTipoLineaInterventoVO = genericDAO.findSingleOrNoneWhere(pbandiDTipoLineaInterventoVO);
				
				String descTipoLinea = pbandiDTipoLineaInterventoVO.getDescTipoLinea();
				logger.info("pbandiDTipoLineaIntervento.getDescTipoLinea = "+descTipoLinea);
												
				String descAsse = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_ASSE;
				String descMisura = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_MISURA;
				String descLinea = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_LINEA;
				
				PbandiDLineaDiInterventoVO normativa = null, asse = null, misura = null, linea = null;		
												
				if (descLinea.equals(descTipoLinea)) {					
					// Recupera misura, asse e normativa.
					linea = pbandiDLineaDiInterventoVO;
					misura = this.findPbandiDLineaDiInterventoVO(pbandiDLineaDiInterventoVO.getIdLineaDiInterventoPadre());
					asse = this.findPbandiDLineaDiInterventoVO(misura.getIdLineaDiInterventoPadre());
					normativa = this.findPbandiDLineaDiInterventoVO(asse.getIdLineaDiInterventoPadre());
				} else if (descMisura.equals(descTipoLinea)) {					
					// Recupera asse e normativa.
					misura = pbandiDLineaDiInterventoVO;
					asse = this.findPbandiDLineaDiInterventoVO(misura.getIdLineaDiInterventoPadre());
					normativa = this.findPbandiDLineaDiInterventoVO(asse.getIdLineaDiInterventoPadre());
				} else if (descAsse.equals(descTipoLinea)) {					
					// Recupera la normativa.
					asse = pbandiDLineaDiInterventoVO;
					normativa = this.findPbandiDLineaDiInterventoVO(asse.getIdLineaDiInterventoPadre());
				} else {
					normativa = pbandiDLineaDiInterventoVO;
				}
				
			 
				if (normativa != null) {
					idNormativa=normativa.getIdLineaDiIntervento() ;
					logger.info("IdNormativa = "+idNormativa);
				}
			} else {
				logger.info("pbandiRBandoLineaIntervent.IdLineaDiIntervento nullo.");
			}
			
		 
			
			return idNormativa;

		} catch (Exception e) {
			throw new UnrecoverableException(
					"Errore findIdLineaDiInterventoNormativa.", e);
		}
	}
	
	
	private PbandiDLineaDiInterventoVO findPbandiDLineaDiInterventoVO(BigDecimal idLinea) throws Exception
	 {

			PbandiDLineaDiInterventoVO vo = new PbandiDLineaDiInterventoVO();
			vo.setIdLineaDiIntervento(idLinea);
			vo = genericDAO.findSingleOrNoneWhere(vo);
		
			return vo;

	 }
	
	// Jira PBANDI-2658
	public Decodifica[] findTipologieProcedureAggiudicazione(java.lang.Long idUtente,
			String identitaDigitale, Long progrBandoLinea)
			throws GestioneDatiDiDominioException, UnrecoverableException {
		logger.info("progrBandoLinea = "+progrBandoLinea);
		try {
			if (progrBandoLinea == null) {
				// Restituisce tutte le tipologie.
				logger.info("progrBandoLinea nullo: restituisco tutte le tipologie.");
				return findDecodifiche(idUtente, identitaDigitale,GestioneDatiDiDominioSrv.TIPOLOGIA_AGGIUDICAZ);
			} else {
				// Dal progrBandoLinea ricava l'idLineaDiIntervento del bando associato.
				PbandiRBandoLineaInterventVO bandoLinea = new PbandiRBandoLineaInterventVO();
				bandoLinea.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
				bandoLinea = genericDAO.findSingleOrNoneWhere(bandoLinea);
				PbandiTBandoVO bando = new PbandiTBandoVO();
				bando.setIdBando(bandoLinea.getIdBando());
				bando = genericDAO.findSingleOrNoneWhere(bando);
				logger.info("bando.IdLineaDiIntervento = "+bando.getIdLineaDiIntervento());
				// Determina il tipo di programmazione
				Long idLineaDiIntervento = null;
				if (bando.getIdLineaDiIntervento() == null) {
					// Vecchia programmazione.
					logger.info("Vecchia programmazione: forzo idLineaDiIntervento = 5");
					idLineaDiIntervento = new Long(5);
				} else {
					// Nuova programmazione.
					idLineaDiIntervento = bando.getIdLineaDiIntervento().longValue();
				}
				logger.info("idLineaDiIntervento = "+idLineaDiIntervento);
				// Restituisce le tipologie abbinate all'idLineaDiIntervento.
				return findDecodificheMultiProgr(idUtente, identitaDigitale, 
						GestioneDatiDiDominioSrv.TIPOLOGIA_AGGIUDICAZ, idLineaDiIntervento);					
			}
		} catch (Exception e) {
			String message = "Impossibile trovare le Tipologie Procedure Aggiudicazione per progrBandoLinea "
					+ progrBandoLinea + ", ragione: " + e.getMessage();
			logger.error(message, e);
			throw new UnrecoverableException(message, e);
		}  
	}

	public PeriodoDTO[] findPeriodo(Long idUtente, String identitaDigitale,
			Long idTipoPeriodo) throws CSIException, SystemException,
			UnrecoverableException, GestioneDatiDiDominioException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		logger.info("\n\n\n\nfindPeriodi");
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale);
		PbandiTPeriodoVO pbandiTPeriodoVO=new PbandiTPeriodoVO();
		
		//Applico il filtro per idTipoPeriodo se esso � presente, altrimenti trasmetto tutti i periodi presenti in tabella
		if(idTipoPeriodo != null)
			pbandiTPeriodoVO.setIdTipoPeriodo(BigDecimal.valueOf(idTipoPeriodo));
		
		pbandiTPeriodoVO.setDescendentOrder("dtInizioContabile");
		List<PbandiTPeriodoVO> periodi = genericDAO.findListWhere(new FilterCondition(pbandiTPeriodoVO));

		PeriodoDTO[] result = beanUtil.transform(periodi, PeriodoDTO.class);
		return result;
		
	}
	
}
