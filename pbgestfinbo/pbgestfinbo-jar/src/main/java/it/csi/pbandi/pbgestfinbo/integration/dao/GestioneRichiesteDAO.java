/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.ElaboraRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.GestioneRichiesteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.NuovaRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoRichiestaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SuggestionRichiesteVO;

public interface GestioneRichiesteDAO {
	
	List<GestioneRichiesteVO> getRichieste(HttpServletRequest req) throws DaoException;
	
	List<GestioneRichiesteVO> findRichieste(BigDecimal tipoRichiesta, BigDecimal statoRichiesta, String numeroDomanda, 
			String codiceFondo, String richiedente, String ordinamento, String colonna, HttpServletRequest req) throws DaoException;
	
	List<SuggestionRichiesteVO> getDomandaNuovaRichiesta(String domandaNuovaRichiesta, HttpServletRequest req) throws DaoException;
	List<SuggestionRichiesteVO> getCodiceProgetto(String codiceProgetto, HttpServletRequest req) throws DaoException;
	List<StoricoRichiestaVO> getStoricoRichiesta(String idRichiesta, HttpServletRequest req) throws DaoException;
	List<ElaboraRichiestaVO> getRichiesta(String idRichiesta, HttpServletRequest req) throws DaoException;
	List<String> getSuggestion(String value, String id, HttpServletRequest req);
	
	int insertNuovaRichiesta(NuovaRichiestaVO nuovaRichiesta, HttpServletRequest req) throws DaoException;

	int elaboraRichiesta(ElaboraRichiestaVO elaboraRichiesta, HttpServletRequest req);

	BigDecimal elaboraDurc(ElaboraRichiestaVO elaboraDurc, HttpServletRequest req);

	BigDecimal elaboraBdna(ElaboraRichiestaVO elaboraBdna, HttpServletRequest req);

	BigDecimal elaboraAntimafia(ElaboraRichiestaVO elaboraAntimafia, HttpServletRequest req);

	Boolean salvaUploadAntiMafia(MultipartFormDataInput multipartFormData);

	Boolean salvaUploadDurc(MultipartFormDataInput multipartFormData);

	Boolean salvaUploadBdna(MultipartFormDataInput multipartFormData);

	Object annullaRichiesta(ElaboraRichiestaVO elaboraRichiesta);
	
	Object elencoTipoRichieste();

	boolean callToFinistr(ElaboraRichiestaVO elaboraDurc, String nomeFile, String pathDocumento);

	String getPreviousState(String idRichiesta);

	ElaboraRichiestaVO getRichiestaUltimaRichiestaDurc(String string);

	List<ElaboraRichiestaVO> getRichiesteSoggetto(String codiceFiscale, BigDecimal idTipoRichiesta);

	ElaboraRichiestaVO getRichiestaUltimaRichiestaDSan(String codiceFiscale);

	ElaboraRichiestaVO getRichiesteAntimafia(String idDomanda);

	Boolean annullaRichiestaAntimafia(String idRichiesta, HttpServletRequest req); 



}
