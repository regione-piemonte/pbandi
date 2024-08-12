package it.csi.pbandi.pbweb.integration.dao;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbweb.dto.FornitoreComboDTO;
import it.csi.pbandi.pbweb.dto.FornitoreDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.TipologiaVoceDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.util.List;

public interface DecodificheCulturaDAO {
	List<DecodificaDTO> ottieniTipologieFornitore();
	List<DecodificaDTO> ottieniTipologieFornitorePerIdTipoDocSpesa(int idDocumentoDiSpesa);
	List<FornitoreDTO> fornitori(long idSoggettoFornitore, long idTipoFornitore, String fornitoriValidi);
	List<FornitoreComboDTO> fornitoriCombo(long idSoggettoFornitore, long idTipoFornitore, long idFornitore);
	List<DecodificaDTO> fornitoriComboRicerca(long idProgetto, HttpServletRequest req) throws Exception;
	List<DecodificaDTO> tipologieFormaGiuridica(String flagPrivato) throws Exception;
	List<DecodificaDTO> nazioni() throws Exception;
	List<DecodificaDTO> qualifiche(long idUtente) throws InvalidParameterException, Exception;
	List<DecodificaDTO> schemiFatturaElettronica() throws Exception;
	List<String> elencoTask(long idProgetto, long idUtente, HttpServletRequest req) throws Exception;
	List<DecodificaDTO> statiDocumentoDiSpesa() throws Exception;
	List<DecodificaDTO> tipiDocumentoSpesa() throws Exception;
	List<DecodificaDTO> tipiDocumentoIndexUploadable() throws Exception;
	
	List<DecodificaDTO> attivitaCombo(long idUtente, HttpServletRequest request) throws UtenteException, GestioneDatiDiDominioException, UnrecoverableException;

	List<TipologiaVoceDiSpesaDTO> ottieniCategorie() throws Exception;;
}

