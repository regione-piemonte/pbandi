/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.exception.DaoException;


//import java.security.InvalidParameterException;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import it.csi.csi.wrapper.UnrecoverableException;
//import it.csi.pbandi.pbservizit.exception.UtenteException;
//import it.csi.pbandi.pbservizit.dto.FornitoreComboDTO;
//import it.csi.pbandi.pbservizit.dto.FornitoreDTO;
//import it.csi.pbandi.pbservizit.dto.profilazione.DecodificaDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

public interface DecodificheDAO {
//	List<DecodificaDTO> ottieniTipologieFornitore();
//	List<DecodificaDTO> ottieniTipologieFornitorePerIdTipoDocSpesa(int idDocumentoDiSpesa);
//	List<FornitoreDTO> fornitori(long idSoggettoFornitore, long idTipoFornitore, String fornitoriValidi);
//	List<FornitoreComboDTO> fornitoriCombo(long idSoggettoFornitore, long idTipoFornitore, long idFornitore);
//	List<DecodificaDTO> tipologieFormaGiuridica(String flagPrivato) throws Exception;
//	List<DecodificaDTO> nazioni() throws Exception;
//	List<DecodificaDTO> qualifiche(long idUtente) throws InvalidParameterException, Exception;
//	List<DecodificaDTO> schemiFatturaElettronica() throws Exception;
//	List<String> elencoTask(long idProgetto, long idUtente, HttpServletRequest req) throws Exception;
//	
//	List<DecodificaDTO> attivitaCombo(long idUtente, HttpServletRequest request) throws UtenteException, GestioneDatiDiDominioException, UnrecoverableException;
	
	BigDecimal idDaDescrizione(String tabella, String colonnaId, String colonnaDescrizione, String valoreDescrizione) 
			throws DaoException ;
	
	String costante(String attributo, boolean obbligatoria) throws DaoException;
		
}