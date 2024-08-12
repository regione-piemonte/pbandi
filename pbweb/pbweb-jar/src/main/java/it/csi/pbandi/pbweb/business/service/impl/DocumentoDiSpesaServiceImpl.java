package it.csi.pbandi.pbweb.business.service.impl;

import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.DocumentoDiSpesaService;
import it.csi.pbandi.pbweb.dto.ElencoDocumentiSpesaItem;
import it.csi.pbandi.pbweb.dto.FiltroRicercaDocumentiSpesa;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.DocumentoDiSpesaCulturaDAO;
import it.csi.pbandi.pbweb.integration.dao.DocumentoDiSpesaDAO;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaPagamentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.PagamentiAssociatiRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class DocumentoDiSpesaServiceImpl implements DocumentoDiSpesaService {

	@Autowired
	private DocumentoDiSpesaDAO documentoDiSpesaDAO;

	@Autowired
	private DocumentoDiSpesaCulturaDAO documentoDiSpesaCulturaDAO;

	private Long getIdUtente(HttpServletRequest req) {
		Long idUtente = null;
		try {
			UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			idUtente = userInfo.getIdUtente();
		} catch (Exception e) {

		}
		return idUtente;
	}

	private String getIdIride(HttpServletRequest req) {
		String idIride = null;
		try {
			UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			idIride = userInfo.getIdIride();
		} catch (Exception e) {

		}
		return idIride;
	}

	@Override
	public Response elencoAffidamenti(long idProgetto, long idFornitoreDocSpesa, String codiceRuolo)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.elencoAffidamenti(idProgetto, idFornitoreDocSpesa, codiceRuolo))
				.build();
	}

	@Override
	public Response allegatiFornitore(long idFornitore, long idProgetto, long idUtente, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(documentoDiSpesaDAO.allegatiFornitore(idFornitore, idProgetto, idUtente, userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response allegatiDocumentoDiSpesa(long idDocumentoDiSpesa, String flagDocElettronico, long idProgetto,
			long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		String idIride = userInfo.getIdIride();

		return Response.ok().entity(documentoDiSpesaDAO.allegatiDocumentoDiSpesa(idDocumentoDiSpesa, flagDocElettronico,
				idProgetto, idUtente, idIride)).build();
	}

	@Override
	public Response disassociaAllegatoDocumentoDiSpesa(long idDocumentoIndex, long idDocumentoDiSpesa, long idProgetto,
			long idUtente, HttpServletRequest req) throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.disassociaAllegatoDocumentoDiSpesa(idDocumentoIndex,
				idDocumentoDiSpesa, idProgetto, idUtente, req)).build();
	}

	@Override
	public Response disassociaAllegatoPagamento(long idDocumentoIndex, long idPagamento, long idProgetto,
			HttpServletRequest req) throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();

		return Response.ok().entity(documentoDiSpesaDAO.disassociaAllegatoPagamento(idDocumentoIndex, idPagamento,
				idProgetto, idUtente, idIride)).build();
	}

	@Override
	public Response salvaDocumentoDiSpesa(DocumentoDiSpesaDTO documentoDiSpesaDTO, Long progrBandoLinea, long idUtente,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		return Response.ok()
				.entity(documentoDiSpesaDAO.salvaDocumentoDiSpesa(documentoDiSpesaDTO, progrBandoLinea, idUtente, req))
				.build();
	}

	@Override
	public Response salvaDocumentoDiSpesaCultura(DocumentoDiSpesaDTO documentoDiSpesaDTO, Long progrBandoLinea,
			long idUtente, long tipoBeneficiario, HttpServletRequest req) throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaCulturaDAO.salvaDocumentoDiSpesa(documentoDiSpesaDTO,
				progrBandoLinea, idUtente, tipoBeneficiario, req)).build();
	}

	@Override
	public Response salvaDocumentoDiSpesaValidazione(DocumentoDiSpesaDTO documentoDiSpesaDTO, long idUtente,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		return Response.ok()
				.entity(documentoDiSpesaDAO.salvaDocumentoDiSpesaValidazione(documentoDiSpesaDTO, idUtente, req))
				.build();
	}

	@Override
	public Response vociDiSpesaAssociateRendicontazione(long idDocumentoDiSpesa, long idProgetto, long idSoggetto,
			String codiceRuolo, String tipoOperazioneDocSpesa, String descBreveStatoDocSpesa, long idUtente,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		return Response.ok()
				.entity(documentoDiSpesaDAO.vociDiSpesaAssociateRendicontazione(idDocumentoDiSpesa, idProgetto,
						idSoggetto, codiceRuolo, tipoOperazioneDocSpesa, descBreveStatoDocSpesa, idUtente, req))
				.build();
	}

	@Override
	public Response vociDiSpesaAssociateValidazione(long idDocumentoDiSpesa, long idProgetto, long idSoggetto,
			String codiceRuolo, String tipoOperazioneDocSpesa, String descBreveStatoDocSpesa, long idUtente,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		return Response
				.ok().entity(documentoDiSpesaDAO.vociDiSpesaAssociateValidazione(idDocumentoDiSpesa, idProgetto,
						idSoggetto, codiceRuolo, tipoOperazioneDocSpesa, descBreveStatoDocSpesa, idUtente, req))
				.build();
	}

	@Override
	public Response macroVociDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.macroVociDiSpesa(idDocumentoDiSpesa, idProgetto, idUtente, req))
				.build();
	}

	@Override
	public Response associaVoceDiSpesa(VoceDiSpesaDTO voceDiSpesaDTO, long idUtente, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.associaVoceDiSpesa(voceDiSpesaDTO, idUtente, req)).build();
	}

	@Override
	public Response disassociaVoceDiSpesa(long idQuotaParteDocSpesa, long idUtente, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.disassociaVoceDiSpesa(idQuotaParteDocSpesa, idUtente, req))
				.build();
	}

	@Override
	public Response modalitaPagamento(long idDocumentoDiSpesa, long idProgetto, long idUtente, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok()
				.entity(documentoDiSpesaDAO.modalitaPagamento(idDocumentoDiSpesa, idProgetto, idUtente, req)).build();
	}

	@Override
	public Response causaliPagamento(HttpServletRequest req) throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.causaliPagamento(req)).build();
	}

	@Override
	public Response associaPagamento(AssociaPagamentoRequest associaPagamentoRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();

		return Response.ok().entity(documentoDiSpesaDAO.associaPagamento(associaPagamentoRequest, idUtente, idIride)).build();
	}

	@Override
	public Response disassociaPagamento(long idPagamento, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.disassociaPagamento(idPagamento, req)).build();
	}

	@Override
	public Response associaAllegatiAPagamento(AssociaFilesRequest associaFilesRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok()
				.entity(documentoDiSpesaDAO.associaAllegatiAPagamento(associaFilesRequest, this.getIdUtente(req)))
				.build();
	}

	@Override
	public Response associaAllegatiADocSpesa(AssociaFilesRequest associaFilesRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok()
				.entity(documentoDiSpesaDAO.associaAllegatiADocSpesa(associaFilesRequest, this.getIdUtente(req)))
				.build();
	}

	@Override
	public Response vociDiSpesaRicerca(long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.vociDiSpesaRicerca(idProgetto, req)).build();
	}

	@Override
	public Response vociDiSpesaRicercaCultura(long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaCulturaDAO.vociDiSpesaRicerca(idProgetto, req)).build();
	}

	@Override
	public Response partners(long idProgetto, long idBandoLinea, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.partners(idProgetto, idBandoLinea, req)).build();
	}

	@Override
	public Response ricercaDocumentiDiSpesa(Long idProgetto, String codiceProgettoCorrente, Long idSoggettoBeneficiario,
			String codiceRuolo, FiltroRicercaDocumentiSpesa filtro, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		Long idSoggetto = userInfo.getIdSoggetto();

		return Response.ok().entity(documentoDiSpesaDAO.ricercaDocumentiDiSpesa(idProgetto, codiceProgettoCorrente,
				idSoggettoBeneficiario, codiceRuolo, filtro, idUtente, idIride, idSoggetto)).build();
	}

	@Override
	public Response documentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(
				documentoDiSpesaDAO.documentoDiSpesa(idDocumentoDiSpesa, idProgetto, getIdUtente(req), getIdIride(req)))
				.build();
	}

	@Override
	public Response eliminaDocumentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(documentoDiSpesaDAO.eliminaDocumentoDiSpesa(idDocumentoDiSpesa, idProgetto,
				getIdUtente(req), getIdIride(req))).build();
	}

	/*
	 * @Override public Response pagamentiAssociati(long idDocumentoDiSpesa, String
	 * tipoInvioDocumentoDiSpesa, String descBreveStatoDocSpesa, String
	 * tipoOperazioneDocSpesa, long idProgetto, long idBandoLinea, String
	 * codiceRuolo, boolean validazione, HttpServletRequest req) throws
	 * InvalidParameterException, Exception {
	 * 
	 * UserInfoSec userInfo = (UserInfoSec)
	 * req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR); Long idUtente
	 * = userInfo.getIdUtente(); String idIride = userInfo.getIdIride(); Long
	 * idSoggetto = userInfo.getIdSoggetto();
	 * 
	 * return Response.ok().entity(documentoDiSpesaDAO.pagamentiAssociati(
	 * idDocumentoDiSpesa, tipoInvioDocumentoDiSpesa, descBreveStatoDocSpesa,
	 * tipoOperazioneDocSpesa, idProgetto, idBandoLinea, codiceRuolo, validazione,
	 * idUtente, idIride, idSoggetto)).build(); }
	 */

	@Override
	public Response pagamentiAssociati(PagamentiAssociatiRequest pagamentiAssociatiRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		Long idSoggetto = userInfo.getIdSoggetto();

		return Response.ok().entity(
				documentoDiSpesaDAO.pagamentiAssociati(pagamentiAssociatiRequest, idUtente, idIride, idSoggetto))
				.build();
	}

	@Override
	public Response noteDiCreditoFattura(long idDocumentoDiSpesa, long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();

		return Response.ok()
				.entity(documentoDiSpesaDAO.noteDiCreditoFattura(idDocumentoDiSpesa, idProgetto, idUtente, idIride))
				.build();
	}

	@Override
	public Response popolaFormAssociaDocSpesa(Long idDocumentoDiSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();

		return Response.ok()
				.entity(documentoDiSpesaDAO.popolaFormAssociaDocSpesa(idDocumentoDiSpesa, idUtente, idIride)).build();
	}

	@Override
	public Response associaDocumentoDiSpesaAProgetto(Long idDocumentoDiSpesa, Long idProgetto, Long idProgettoDocumento,
			String task, Double importoRendicontabile, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();

		return Response.ok().entity(documentoDiSpesaDAO.associaDocumentoDiSpesaAProgetto(idDocumentoDiSpesa, idProgetto,
				idProgettoDocumento, task, importoRendicontabile, idUtente, idIride)).build();
	}

	@Override
	public Response esitoLetturaXmlFattElett(Long idDocumentoIndex, Long idSoggettoBeneficiario,
			Long idTipoOperazioneProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();

		return Response.ok().entity(documentoDiSpesaDAO.esitoLetturaXmlFattElett(idDocumentoIndex,
				idSoggettoBeneficiario, idTipoOperazioneProgetto, idUtente, idIride)).build();
	}

	@Override
	public Response getQuotaImportoAgevolato(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(documentoDiSpesaDAO.getQuotaImportoAgevolato(idProgetto, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	@Override
	public Response getDocumentazioneDaAllegare(Long progrBandoLineaIntervento, Long idTipoDocumentoSpesa,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentoDiSpesaDAO.getDocumentazioneDaAllegare(progrBandoLineaIntervento,
				idTipoDocumentoSpesa, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response getParametriCompensi(HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(documentoDiSpesaDAO.getParametriCompensi(userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response getTipoBeneficiario(Long idSoggetto, Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(documentoDiSpesaDAO.getTipoBeneficiario(idProgetto,userInfo.getIdSoggetto(),
				userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride() )).build();
	}

	@Override
	public Response getImportoASaldo(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(documentoDiSpesaDAO.getImportoASaldo(idProgetto, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}
	@Override
	public Response getGiorniMassimiQuietanzaPerBando(Long idBandoLinea, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if(idBandoLinea == null || idBandoLinea <= 0) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("idBandoLinea non valorizzato")
					.build();
		}

		return Response.ok()
				.entity(documentoDiSpesaDAO.getGiorniMassimiQuietanzaPerBando(idBandoLinea, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

}
