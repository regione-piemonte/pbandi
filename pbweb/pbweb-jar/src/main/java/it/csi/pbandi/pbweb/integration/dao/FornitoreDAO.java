package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbweb.dto.AttivitaAtecoDTO;
import it.csi.pbandi.pbweb.dto.AttivitaAtecoNodoDTO;
//import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.dto.EsitoRicercaFornitori;
import it.csi.pbandi.pbweb.dto.FatturaRiferimentoDTO;
import it.csi.pbandi.pbweb.dto.Fornitore;
import it.csi.pbandi.pbweb.dto.FornitoreFormDTO;
import it.csi.pbandi.pbweb.dto.NazioneDTO;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificheRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.QualificaDTO;


public interface FornitoreDAO {
	
	Boolean testTransactional (HttpServletRequest request) throws RuntimeException, InvalidParameterException, Exception;
	Boolean testTransactionalJUnit () throws RuntimeException, InvalidParameterException, Exception;
	
	ArrayList<AttivitaAtecoDTO> attivitaAteco (long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception;
	ArrayList<AttivitaAtecoNodoDTO> alberoAttivitaAteco( long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception;
	EsitoDTO salvaFornitore (FornitoreFormDTO fornitoreFormDTO, long idUtente, long idSoggettoBeneficiario, HttpServletRequest request) throws InvalidParameterException, Exception;
	FornitoreFormDTO cercaFornitore (Long idFornitore, Long idSoggettoBeneficiario, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<QualificaDTO> qualificheFornitore (long idFornitore, long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception;
	//EsitoDTO salvaQualifica (QualificaFormDTO qualificaFormDTO, long idUtente, HttpServletRequest request) throws InvalidParameterException, Exception;
	EsitoDTO salvaQualifica (SalvaQualificaRequest salvaQualificaRequest, HttpServletRequest request) throws InvalidParameterException, Exception;
	EsitoDTO salvaQualifiche (SalvaQualificheRequest salvaQualificheRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<FatturaRiferimentoDTO> fattureFornitore (long idFornitore, long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	List<NazioneDTO> nazioni (HttpServletRequest request) throws InvalidParameterException, Exception;
	EsitoRicercaFornitori ricercaElencoFornitori(FornitoreDTO filtro, Long idSoggettoBeneficiario, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO disassociaAllegatoFornitore(Long idDocumentoIndex, Long idFornitore, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaAllegatiAFornitore(AssociaFilesRequest associaFilesRequest, Long idUtente) throws InvalidParameterException, Exception;
	EsitoOperazioneDTO eliminaFornitore(Long idFornitore, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean esistonoDocumentiAssociatiAQualificaFornitore(Long progrFornitoreQualifica, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO eliminaQualifica(Long progrFornitoreQualifica, Long idUtente, String idIride) throws InvalidParameterException, Exception;
}
