/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbwebcert.dto.ProgettoNuovaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BandoLineaDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BeneficiarioDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.CodiceDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.Documento;
import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.EsitoOperazioni;
import it.csi.pbandi.pbwebcert.dto.certificazione.FiltroRicercaDocumentoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.LineaDiInterventoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.StatoPropostaDTO;
import it.csi.pbandi.pbwebcert.dto.manager.ReportCampionamentoDTO;
import it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbwebcert.exception.CertificazioneException;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaStatoRequest;

public interface CertificazioneDao {

	public PropostaCertificazioneDTO[] findProposteCertificazione(Long idUtente, String identitaDigitale,
			List<String> statiProposta) throws Exception;

	public LineaDiInterventoDTO[] getLineeDiInterventoFromIdLinee(List<Long> idLineeIntervento) throws Exception;

	public PropostaCertificazioneDTO findPropostaCertificazione(BigDecimal idProposta) throws Exception;

	public DocumentoCertificazioneDTO[] findAllegatiPropostaCertificazione(BigDecimal idPropostaCertificazione,
			List<String> codiciTipoDocumento) throws Exception;
	
	public EsitoOperazioni cancellaAllegati(List<String> idDocumentiSelezionati) throws Exception;
	
	public List<LineaDiInterventoDTO> findAttivitaLineaIntervento(Long idLineaIntervento) throws Exception;

	public List<ProgettoDTO> findAllProgetti(Long idLineaIntervento, Long idProposta, Long idBeneficiario);

	public List<BeneficiarioDTO> findAllBeneficiari(Long idProposta, Long idLineaIntervento) throws Exception;

	public List<PropostaCertificazioneDTO> findProgettiProposta(Long idProgetto, Long idProposta, Long idLineaIntervento, Long idBeneficiario);

	public EsitoOperazioni allegaFileProposta(Long idUtente, Long idProposta,Long idProgetto, List<InputPart> file, String tipoDocumento) throws IOException, IncorrectUploadPathException, Exception;

	public EsitoOperazioni creaIntermediaFinale(Long idUtente) throws it.csi.pbandi.pbwebcert.exception.CertificazioneException;

	public List<CodiceDescrizione> getAnnoContabile() throws CertificazioneException;

	public List<CodiceDescrizione> getLineeInterventoNormative(boolean isConsultazione, String flagCampionionamentoCertificazione);

	public EsitoGenerazioneReportDTO eseguiEstrazioneCampionamento(Long idNormativa, Long idUtente) throws Exception;

	public EsitoOperazioni modificaAllegati(List<DocumentoDescrizione> documentiDescrizioni);

	public EsitoGenerazioneReportDTO getContenutoDocumentoById(Long idDocumentoIndex) throws Exception;

	public List<DocumentoCertificazioneDTO> ricercaDocumenti(FiltroRicercaDocumentoDTO filtroRicerca) throws FileNotFoundException, IOException, Exception;

	public List<it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO> getElencoReportCampionamento(Long idNormativa, Long idAnnoContabile) throws FileNotFoundException, IOException;

	public PropostaCertificazioneDTO findDettaglioProposta(Long idProposta) throws Exception;

	public List<StatoPropostaDTO> getStatiSelezionabili() throws CertificazioneException;


	public Integer aggiornaStatoProposta(Long idUtente, Long idProposta, Long idStatoNuovo) throws CertificazioneException;

	public EsitoOperazioni creaAnteprimaPropostaCertificazione(Long idUtente, PropostaCertificazioneDTO dto,
			Long[] idLineeDiIntervento) throws CertificazioneException;

	public List<ProgettoNuovaCertificazioneDTO> getGestisciProposta(Long idProposta, String nomeBandoLinea,
			String codiceProgetto, String denominazioneBeneficiario, boolean progettiModificati) throws CertificazioneException;

	public List<BandoLineaDTO> getBandoLinea(Long idProposta) throws CertificazioneException;

	public List<BeneficiarioDTO> findAllBeneficiariDaProposta(Long idProposta, String nomeBandoLinea) throws CertificazioneException;

	public List<ProgettoDTO> findAllProgettiDaProposta(Long idProposta, String nomeBandoLinea,
			String denominazioneBeneficiario) throws CertificazioneException;

	public EsitoOperazioni aggiornaImportoNetto(Long idUtente, Long idProposta, Long idDettProposta, Double nuovoImportoNetto,
			String nota, Long idProgetto) throws CertificazioneException;

	public List<ProgettoDTO> findProgettiIntermediaFinale(Long idProposta, String nomeBandoLinea, String denominazioneBeneficiario) throws CertificazioneException;

	public List<BandoLineaDTO> findBandoLineaIntermediaFinale(Long idProposta) throws CertificazioneException;

	public List<BeneficiarioDTO> findBeneficiariIntermediaFinale(Long idProposta, String nomeBandoLinea) throws CertificazioneException;

	public Boolean checkProceduraAggiornamentoTerminata() throws FileNotFoundException, IOException, Exception;

	public List<ProgettoCertificazioneIntermediaFinaleDTO> getGestisciPropostaIntermediaFinale(Long idProposta,
			String nomeBandoLinea, String codiceProgetto, String denominazioneBeneficiario, Long idLineaIntervento) throws CertificazioneException;

	public EsitoOperazioni modificaProgettiIntermediaFinale(
			List<ProgettoCertificazioneIntermediaFinaleDTO> progettiCertificazioneIntermediaFinale) throws FileNotFoundException, IOException, CertificazioneException;

	public EsitoOperazioni chiusuraContiPropostaIntermediaFinale(Long idUtente, Long idProposta) throws Exception;

	public List<BandoLineaDTO> findBandoLineaPerAnteprima(Long idProposta) throws CertificazioneException;

	public List<BeneficiarioDTO> findBeneficiariPerAnteprima(Long idProposta, Long progrBandoLineaIntervento) throws CertificazioneException;

	public List<ProgettoDTO> findProgettiPerAnteprima(Long idProposta, Long progrBandoLineaIntervento,
			Long idBeneficiario) throws CertificazioneException;

	public List<LineaDiInterventoDTO> findLineeDiInterventoDisponibili(Long idSoggetto, String codiceRuolo) throws FileNotFoundException, IOException, CertificazioneException;

	public List<PropostaProgettoDTO> findAntePrimaProposta(Long idProposta, Long progrBandoLineaIntervento,
			Long idBeneficiario, Long idProgetto, Long idLineaDiIntervento) throws CertificazioneException;

	public EsitoOperazioni sospendiProgettiProposta(Long idUtente, Long[] idsPreviewDettPropCer) throws CertificazioneException;
	
	public EsitoOperazioni sospendiProgettiPropostaRev(Long idUtente, Long[] idsPreviewDettPropCer) throws CertificazioneException;

	public EsitoOperazioni ammettiProgettiProposta(Long idUtente, Long[] idsPreviewDettPropCer) throws CertificazioneException;
	
	public EsitoOperazioni ammettiProgettiPropostaRev(Long idUtente, Long[] idsPreviewDettPropCer) throws CertificazioneException;

	public EsitoOperazioni accodaPropostaCertificazione(Long idUtente, PropostaCertificazioneDTO proposta) throws CertificazioneException;

	public EsitoOperazioni lanciaCreazioneProposta(Long idUtente, PropostaCertificazioneDTO proposta,
			Long[] idLineeDiIntervento) throws CertificazioneException;

	public EsitoOperazioni inviaReportPostGestione(Long idUtente, PropostaCertificazioneDTO dto,
			Long[] idLineeDiIntervento) throws FileNotFoundException, IOException, Exception;

	public LineaDiInterventoDTO findLineaDiInterventoFromProposta(Long idUtente, Long idProposta);

	public String aggiornaDatiIntermediaFinale(Long idUtente, Long idProposta) throws CertificazioneException;

	public PropostaCertificazioneDTO[] propostePerRicercaDocumenti(Long idUtente, String identitaDigitale, List<String> statiProposta) throws Exception;
	
}
