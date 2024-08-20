/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.AtecoDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.FormaGiuridicaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.RuoloDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.SezioneSpecialeDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.BloccoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.DatiAreaCreditiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.DatidimensioneImpresaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SoggettiCorrelatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AltriDatiDsan;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagAltriDati_MainVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AtlanteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DettaglioImpresaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElencoDomandeProgettiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.IscrizioneRegistroVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StatoDomandaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.UpdateAnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.UpdateAnagraficaBeneficiarioPgVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneVO;

public interface AnagraficaBeneficiarioDAO {
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////SERVIZI PER RICERCA E DETTAGLIO /////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	List<AnagraficaBeneficiarioVO> getAnagBeneficiario(Long idSoggetto,Long idProgetto, String numDomanda, HttpServletRequest req)  throws DaoException;
	
	AnagraficaBeneficiarioPfVO getAnagBeneFisico(Long idSoggetto, String numDomanda, Long idProgetto, HttpServletRequest req)  throws DaoException;
	
	StatoDomandaVO getStatoDomanda(Long idSoggetto,Long idDomanda,  HttpServletRequest req)  throws DaoException;
	
	List<AtlanteVO> getNazioni(HttpServletRequest req)  throws DaoException;
	
	List<AtlanteVO> getRegioni(HttpServletRequest req)  throws DaoException;
	
	List<AtlanteVO> getProvincie(HttpServletRequest req)  throws DaoException;
	
	List<AtlanteVO> getComuni(String idProvincia,HttpServletRequest req)  throws DaoException;
	
	IscrizioneRegistroVO getIscrizioneRegistroImprese(Long idSoggetto, HttpServletRequest req)  throws DaoException;
	
	AnagAltriDati_MainVO getAltriDati(Long idSoggetto, Long idEnteGiuridico, String numeroDomanda, HttpServletRequest req) throws DaoException; 
	
	List<DettaglioImpresaVO> getDettaglioImpresa(String idSoggetto, BigDecimal anno, HttpServletRequest req)  throws DaoException;
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////// SERVIZI PER MODIFICA /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Boolean updateAnagBeneFisico(AnagraficaBeneficiarioPfVO anag, String idSoggetto, String numeroDomanda) throws ErroreGestitoException; 

	
	Boolean updateAnagBeneGiuridico(AnagraficaBeneficiarioVO anag,
			String idSoggetto,String idDomanda, boolean isSoggettoCorrelato) throws ErroreGestitoException;

	List<ElencoDomandeProgettiVO> getElencoDomandeProgetti(Long idSoggetto, boolean isEnteGiuridico, HttpServletRequest req) throws DaoException;


	//List<AnagAltriDati_DimensioneImpresaVO> getDimensioneImpresa(Long idSoggetto, Long numeroDomanda, HttpServletRequest req) throws DaoException; 

	//List<AnagAltriDati_DurcVO> getDurc(Long idSoggetto, HttpServletRequest req) throws DaoException; 
	
	
	///////// ALGORITMI COMUNI /////////
	
	Boolean sistemaDiBlocchi(Long idSoggetto, Long idUtente, List<Integer> listaCampi);
	
	Boolean sistemaDiNotifiche(Long idSoggetto, Long idProgetto, Long idUtente, Long idCausaleBlocco, boolean isBlocco);
	
	Boolean sistemaDiNotificheFinistr(List<Integer> listaCampi, String numeroDomanda, Long idSoggetto, String ragioneSociale, Long idFormaGiuridica);

	///////// BLOCCHI/////////
	
	List<BloccoVO> getElencoBlocchi(BigDecimal idSoggetto, boolean flagErogazione);

	List<BloccoVO> getStoricoBlocchi(Long idSoggetto, boolean flagErogazione);

	List<AttivitaDTO> getListaCausali(Long idSoggetto);

	boolean insertBlocco(BloccoVO bloccoVO);

	boolean modificablocco(BloccoVO bloccoVO);
	
	// questo nuovo piu giusto per inserimento blocco domanda.. 
	boolean insertBloccoConDomanda(BloccoVO blocco, String numeroDomanda) throws Exception;
	
/////////// SOGGETTI CORRELATI INDIPENDENTI DA DOMANDA////////////// 

	List<SoggettiCorrelatiVO> getElencoSoggCorrIndipDaDomanda(String idDomanda, String idSoggetto);

	AnagraficaBeneficiarioVO getSoggCorrIndDaDomEG(String idDomanda, String idSoggetto ,BigDecimal idSoggCorr);

	AnagraficaBeneficiarioPfVO getSoggCorrIndDaDomPF(String idDomanda, String idSoggetto);

	Boolean modificaSoggcorrEG(AnagraficaBeneficiarioVO soggetto, String idSoggetto, String idDomanda, BigDecimal idSoggCorr);
	List<FormaGiuridicaDTO>elencoFormeGiuridiche();

	Boolean modificaSoggcorrPF(AnagraficaBeneficiarioPfVO anag, String idSoggetto, String idDomanda);
	
	
	// select // 
	//List<AttivitaPrevalenteDTO> getElencoAttivita(); da rivedere
	List<RuoloDTO> getElencoRuoloIndipendente(); 
	List<RuoloDTO> getElencoRuoliDipendente(); 
	
	List<SezioneSpecialeDTO> getElencoSezioni(); 
	List<AtecoDTO> getElencoAteco(String idAttivitaAteco); 
	
	/// ALTRI DATI 
	DatiAreaCreditiVO getDatiAreaCrediti(String idSoggetto); 
	
	// elenco blocchi soggetto domanda
	
	List<BloccoVO> getElencoBlocchiSoggettoDomanda(String idSoggetto, String numeroDomanda, boolean flagErogazione); 
	Boolean insertBloccoDomanda(BloccoVO blocco,String idSoggetto,  String idDomanda); 
	List<AttivitaDTO> getListaCausaliDomanda(String idSoggetto, String idDomanda);
	boolean updateBloccoDomanda(BloccoVO blocco,String numeroDomanda); 
	
	List<BloccoVO>  getStoricoBlocchiDomanda(Long idSoggetto, String numeroDomanda, boolean flagErogazione);
	
	/// dimensione impresa e dsna 
	
	List<DatidimensioneImpresaVO> getDatiDimensione(Long idSoggetto, String numeroDomanda); 
	List<DettaglioImpresaVO> getDatiDimensioneSoggetto(Long idSoggetto); 
	List<AltriDatiDsan> getDsan(Long idSoggetto, String numeroDomanda); 
	
	List<AtlanteVO> getComuniEsteri(Long idNazioneEstera); 
	List<AtlanteVO> getProvincieConidRegioni(Long idRegione);

	List<AttivitaDTO> getStatoAttivita();

	Object getElencoTipoAnag();

	Object getListTipoDocumento();

	
}
