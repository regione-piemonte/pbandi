/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.GestioneIntegrazioniService;
import it.csi.pbandi.pbweb.dto.IntegrazioneRendicontazioneDTO;
import it.csi.pbandi.pbweb.dto.IntegrazioneRevocaDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.AllegatiDocSpesaQuietanzeDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.AllegatiQuietanzeDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.AllegatoDTO;
import it.csi.pbandi.pbweb.integration.dao.GestioneIntegrazioniDAO;
import it.csi.pbandi.pbweb.integration.vo.AllegatiDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.integration.vo.AllegatoDocSpesaQuietanzaVO;
import it.csi.pbandi.pbweb.integration.vo.VisualizzaIntegrazioniVO;
import it.csi.pbandi.pbweb.util.Constants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestioneIntegrazioniServiceImpl implements GestioneIntegrazioniService {
	
	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    private GestioneIntegrazioniDAO gestioneIntegrazioniDAO;


    @Override
    public Response getAbilitaInviaIntegr(int idRichIntegrazione, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAbilitaInviaIntegr(idRichIntegrazione)).build();
    }

    @Override
    public Response getAllegatiIstruttoreRevoche(int idRichIntegraz, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiIstruttoreRevoche(idRichIntegraz)).build();
    }

    @Override
    public Response salvaUploadLetteraAllegatoIntegr(Long idRichIntegraz,
                                                     List<VisualizzaIntegrazioniVO> allegati, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.insertFileEntita(allegati, idRichIntegraz)).build();
    }

    @Override
    public Response updateRichIntegrazione(HttpServletRequest req, VisualizzaIntegrazioniVO vc) {
        return Response.ok(gestioneIntegrazioniDAO.updateRichIntegrazione(vc.getIdUtente(), vc.getIdRichIntegrazione())).build();

    }

    @Override
    public Response getAllegati(int idRichIntegraz, HttpServletRequest req) throws Exception {
        return Response.ok(gestioneIntegrazioniDAO.getAllegati(idRichIntegraz)).build();
    }

    @Override
    public Response getLetteraIstruttore(int idRichIntegraz, HttpServletRequest req) throws Exception {
        return Response.ok(gestioneIntegrazioniDAO.getLetteraIstruttore(idRichIntegraz)).build();
    }

    @Override
    public Response deleteAllegato(VisualizzaIntegrazioniVO acv, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.deleteAllegato(acv.getIdFileEntita())).build();
    }

    @Override
    public Response getAllegatiDichSpesa(int idDichSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiDichSpesa(idDichSpesa)).build();
    }

    @Override
    public Response getDocumentiSpesaSospesi(int idProgetto, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getDocumentiSpesaSospesi(idProgetto)).build();
    }

    @Override
    public Response salvaAllegatiGenerici(Long idDichiarazioneSpesa, Long idIntegrazioneSpesa,
                                          List<AllegatiDichiarazioneSpesaVO> allegati, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.salvaAllegatiGenerici(allegati, idDichiarazioneSpesa, idIntegrazioneSpesa)).build();
    }



    //INTEGRAZIONE ALLA REVOCA
    @Override
    public Response getIntegrazioniRevoca(Long idProgetto, Long idBandoLinea, HttpServletRequest req) throws Exception {
        return Response.ok().entity(gestioneIntegrazioniDAO.getIntegrazioniRevoca(idProgetto, idBandoLinea)).build();
    }
    @Override
    public Response getRichProroga(Long idIntegrazione, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getRichProroga(idIntegrazione)).build();
    }
    @Override
    public Response inserisciRichProroga(Long idIntegrazione, IntegrazioneRevocaDTO integrazioneRevocaDTO, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.inserisciRichProroga(idIntegrazione, integrazioneRevocaDTO, req)).build();
    }
    @Override
    public Response inviaIntegrazione(Long idIntegrazione, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.inviaIntegrazione(idIntegrazione, req)).build();
    }

    //INTEGRAZIONE ALLA RENDICONTAZIONE
    @Override
    public Response getIntegrazioniRendicontazione(Long idProgetto, HttpServletRequest req) throws Exception {
        return Response.ok(gestioneIntegrazioniDAO.getIntegrazioniRendicontazione(idProgetto, req)).build();
    }
    @Override
    public Response getRegoleIntegrazione(Long idBandoLinea, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getRegoleIntegrazione(idBandoLinea, req)).build();
    }
    @Override
    public Response getRichProrogaRendicontazione(Long idIntegrazione, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getRichProrogaRendicontazione(idIntegrazione)).build();
    }
    @Override
    public Response inserisciRichProrogaRendicontazione(Long idIntegrazione, IntegrazioneRendicontazioneDTO integrazioneRendicontazioneDTO, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.inserisciRichProrogaRendicontazione(idIntegrazione, integrazioneRendicontazioneDTO, req)).build();
    }
    @Override
    public Response inviaIntegrazioneRendicontazione(Long idIntegrazione, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.inviaIntegrazioneRendicontazione(idIntegrazione, req)).build();
    }

    //ALLEGATI

    @Override
    public Response getLetteraAccIntegrazRendicont(Long idIntegrazione, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getLetteraAccIntegrazRendicont(idIntegrazione)).build();
    }

    @Override
    public Response getReportValidazione(Long idIntegrazione, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getReportValidazione(idIntegrazione)).build();
    }

    @Override
    public Response getAllegatiDichiarazioneSpesa(Long idDichiarazioneSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiDichiarazioneSpesa(idDichiarazioneSpesa)).build();
    }

    @Override
    public Response getAllegatiIntegrazioneDS(Long idIntegrazione, Long idDichiarazioneSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiIntegrazioneDS(idIntegrazione, idDichiarazioneSpesa)).build();
    }

    @Override
    public Response getAllegatiNuovaIntegrazioneDS(Long idIntegrazione, Long idDichiarazioneSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiNuovaIntegrazioneDS(idIntegrazione, idDichiarazioneSpesa)).build();
    }

    @Override
    public Response getDocumentiDiSpesaSospesi(Long idDichiarazioneSpesa, Long idProgetto, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getDocumentiDiSpesaSospesi(idDichiarazioneSpesa, idProgetto)).build();
    }

    @Override
    public Response getDocumentiDiSpesaIntegrati(Long idIntegrazione, Long idDichiarazioneSpesa, Long idProgetto, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getDocumentiDiSpesaIntegrati(idIntegrazione, idDichiarazioneSpesa, idProgetto)).build();
    }

    @Override
    public Response getAllegatiDocumentoSpesa(Long idDichiarazioneSpesa, Long idDocumentoSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiDocumentoSpesa(idDichiarazioneSpesa, idDocumentoSpesa)).build();
    }

    @Override
    public Response getAllegatiIntegrazioneDocS(Long idIntegrazione, Long idDichiarazioneSpesa, Long idDocumentoSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiIntegrazioneDocS(idIntegrazione, idDichiarazioneSpesa, idDocumentoSpesa)).build();
    }

    @Override
    public Response getAllegatiNuovaIntegrazioneDocS(Long idIntegrazione, Long idDocumentoSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiNuovaIntegrazioneDocS(idIntegrazione, idDocumentoSpesa)).build();
    }

    @Override
    public Response getQuietanze(Long idDocumentoSpesa, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getQuietanze(idDocumentoSpesa)).build();
    }

    @Override
    public Response getAllegatiQuietanza(Long idDichiarazioneSpesa, Long idQuietanza, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiQuietanza(idDichiarazioneSpesa, idQuietanza)).build();
    }

    @Override
    public Response getAllegatiIntegrazioneQuietanza(Long idIntegrazione, Long idDichiarazioneSpesa, Long idQuietanza, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiIntegrazioneQuietanza(idIntegrazione, idDichiarazioneSpesa, idQuietanza)).build();
    }

    @Override
    public Response getAllegatiNuovaIntegrazioneQuietanza(Long idIntegrazione, Long idQuietanza, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.getAllegatiNuovaIntegrazioneQuietanza(idIntegrazione, idQuietanza)).build();
    }

    @Override
    public Response salvaAllegati(List<AllegatoDTO> allegati, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.salvaAllegati(allegati, req)).build();
    }

    @Override
    public Response rimuoviAllegato(Long idFileEntita, HttpServletRequest req) {
        return Response.ok(gestioneIntegrazioniDAO.rimuoviAllegato(idFileEntita, req)).build();
    }
    
    @Override
	public Response getAllegatiDocSpesaQuietanzeDaInviare(Long idIntegrazioneSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneServiceImpl::getAllegatiDocSpesaQuietanzeDaInviare] ";

		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idIntegrazioneSpesa=" + idIntegrazioneSpesa);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		List<AllegatoDocSpesaQuietanzaVO> allegatiVO =  gestioneIntegrazioniDAO.getAllegatiDocSpesaQuietanzeDaInviare(idIntegrazioneSpesa, userInfo.getIdUtente(),
				userInfo.getIdIride(),userInfo.getCodiceRuolo());
		
		List<AllegatiDocSpesaQuietanzeDTO> allegatiDTO = new ArrayList<AllegatiDocSpesaQuietanzeDTO>();
		if(allegatiVO != null && allegatiVO.size() > 0) {
			//aggiungo i documenti di spesa e i loro allegati
			for(AllegatoDocSpesaQuietanzaVO allegatoVO : allegatiVO) {
				//aggiungo il documento di spesa se non presente
				if(!allegatiDTO.stream().filter(a -> a.getIdDocumentoDiSpesa().equals(allegatoVO.getIdDocumentoDiSpesa())).findFirst().isPresent()) {
					AllegatiDocSpesaQuietanzeDTO dto = new AllegatiDocSpesaQuietanzeDTO();
					dto.setIdDocumentoDiSpesa(allegatoVO.getIdDocumentoDiSpesa());
					dto.setFornitore(allegatoVO.getDenomFornitore());
					dto.setImporto(allegatoVO.getImportoDocSpesa());
					dto.setNote(allegatoVO.getNoteValidazione());
					dto.setDocumento(allegatoVO.getTipoNumeroDocSpesa());
					dto.setNomiAllegati(new ArrayList<String>());		
					dto.setAllegatiQuietanze(new ArrayList<AllegatiQuietanzeDTO>());
					allegatiDTO.add(dto);	
				}
				//aggiungo il nome dell'allegato al documento di spesa
				allegatiDTO.stream().filter(a -> a.getIdDocumentoDiSpesa().equals(allegatoVO.getIdDocumentoDiSpesa()) && allegatoVO.getIdPagamento() == null)
				.forEach(a->a.getNomiAllegati().add(allegatoVO.getNomeFile()));
			}
			
			//aggiungo le quietanze e i loro allegati
			List<AllegatoDocSpesaQuietanzaVO> allegatiQuietanzeVO =allegatiVO.stream().filter(a->a.getIdPagamento()!=null).collect(Collectors.toList());
			for(AllegatoDocSpesaQuietanzaVO allegatoQuietanzaVO : allegatiQuietanzeVO) {
				//prendo il documento di spesa della quietanza
				allegatiDTO.stream().filter(a -> a.getIdDocumentoDiSpesa().equals(allegatoQuietanzaVO.getIdDocumentoDiSpesa())).forEach(a ->{
					//aggiungo la quietanza al documento se non presente
					if(!a.getAllegatiQuietanze().stream().filter(allQ -> allQ.getIdPagamento().equals(allegatoQuietanzaVO.getIdPagamento())).findFirst().isPresent()) {
						AllegatiQuietanzeDTO allQ = new AllegatiQuietanzeDTO();
						allQ.setIdPagamento(allegatoQuietanzaVO.getIdPagamento());
						allQ.setDescModalitaPagamento(allegatoQuietanzaVO.getModalitaPagamento());
						allQ.setImportoPagamento(allegatoQuietanzaVO.getImportoPagamento());
						allQ.setDataPagamento(allegatoQuietanzaVO.getDtPagamento());
						allQ.setNomiAllegati(new ArrayList<String>());
						a.getAllegatiQuietanze().add(allQ);

					}
					//aggiungo il nome dell'allegato alla quietanza
					a.getAllegatiQuietanze().stream().filter(allQ -> allQ.getIdPagamento().equals(allegatoQuietanzaVO.getIdPagamento()))
					.forEach(allQ->allQ.getNomiAllegati().add(allegatoQuietanzaVO.getNomeFile()));
				});
				
			}
		}
		
		LOG.info(prf + "END");
		return Response.ok().entity(allegatiDTO).build();
	}


}
