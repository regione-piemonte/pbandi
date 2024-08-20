/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.AffidamentoCheckListDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.FornitoreDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.MotiveAssenzaDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.NormativaAffidamentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipoAffidamentoDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAppaltoDTO;
import it.csi.pbandi.pbwebrce.exception.GestioneAffidamentiException;
import it.csi.pbandi.pbwebrce.integration.request.SubcontrattoRequest;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ParamInviaInVerificaDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO;

public interface AffidamentiDAO {

	List<AffidamentoDTO> getElencoAffidamenti(Long idProgetto, String codiceRuolo) throws FileNotFoundException, IOException, Exception;

	List<FornitoreDTO> findFornitori(FornitoreDTO filtro) throws FileNotFoundException, IOException, GestioneAffidamentiException;

	AffidamentoDTO findAffidamento(Long idAppalto) throws FileNotFoundException, IOException, GestioneAffidamentiException;


	String findCodiceProgetto(Long idProgetto);

	List<NormativaAffidamentoDTO> findNormativeAffidamento();

	List<TipoAffidamentoDTO> findTipologieAffidamento();

	List<TipologiaAppaltoDTO> findTipologieAppalto();

	List<TipologiaAggiudicazioneDTO> findTipologieProcedureAggiudicazione(Long progrBandoLinea) throws Exception;

	List<MotiveAssenzaDTO> findMotiveAssenza() throws Exception;

	EsitoGestioneAffidamenti salvaAffidamento(Long idUtente, AffidamentoDTO affidamentoDTO) throws GestioneAffidamentiException, Exception;

	EsitoGestioneAffidamenti inviaInVerifica(Long idUtente, ParamInviaInVerificaDTO dto) throws GestioneAffidamentiException, FormalParameterException, Exception;

	VarianteAffidamentoDTO[] findVarianti(Long idUtente, VarianteAffidamentoDTO filtro) throws FormalParameterException;

	ArrayList<CodiceDescrizioneDTO> findRuoli(UserInfoSec userInfo, String tipoPercettore) throws GestioneDatiDiDominioException, SystemException, UnrecoverableException, CSIException;

	ArrayList<CodiceDescrizioneDTO> findTipologieVarianti(UserInfoSec userInfo, String tipologieVarianti) throws GestioneDatiDiDominioException, SystemException, UnrecoverableException, CSIException;

	EsitoGestioneAffidamenti creaVariante(Long idUtente, String idIride, VarianteAffidamentoDTO dto) throws GestioneAffidamentiException, FormalParameterException, Exception;

	EsitoGestioneAffidamenti creaFornitore(Long idUtente, String idIride, FornitoreAffidamentoDTO dto) throws FormalParameterException, Exception;

	EsitoGestioneAffidamenti cancellaVariante(Long idUtente, String idIride, Long idVariante) throws FormalParameterException, GestioneAffidamentiException;

	EsitoGestioneAffidamenti cancellaFornitore(Long idUtente, String idIride, Long idFornitore, Long idAppalto,
			Long idTipoPercettore) throws FormalParameterException, GestioneAffidamentiException;

	EsitoScaricaDocumentoDTO findDocumento(Long idUtente, String idIride, Long idDocumentoIndex) throws FormalParameterException, Exception;

	EsitoGestioneAffidamenti cancellaAffidamento(Long idUtente, String idIride, Long cancellaAffidamento) throws FormalParameterException, GestioneAffidamentiException;

	AffidamentoCheckListDTO[] findAllAffidamentoChecklist();

	EsitoGestioneAffidamenti respingiAffidamento(Long idUtente, String idIride, Long idAppalto, String note) throws GestioneAffidamentiException, FormalParameterException, FileNotFoundException, IOException, Exception;

	EsitoGestioneAffidamenti salvaSubcontratto(Long idUtente, String idIride, SubcontrattoRequest request) throws FormalParameterException;

	EsitoGestioneAffidamenti cancellaSubcontratto(Long idUtente, String idIride, Long idSubcontrattoAffidamento) throws FormalParameterException, GestioneAffidamentiException;

	List<DocumentoAllegatoDTO> allegatiVerbaleChecklist(Long idDocIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception;
}
