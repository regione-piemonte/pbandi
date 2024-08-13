/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.services.index;

import it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.PropostaRimodulazioneReportDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.RimodulazioneReportDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita.IrregolaritaDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettPropostaCertifVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DichiarazioneDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRichiestaErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRinunciaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.index.exception.NodeNotFoundException;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.index.vo.ContentMetadataVO;
import it.doqui.index.ecmengine.dto.Node;
import it.doqui.index.ecmengine.dto.Path;
import it.doqui.index.ecmengine.dto.engine.search.ResultContent;

public interface IndexDAO {

	public Node creaContentDichiarazioneDiSpesa(byte[] pdfBytes,
			String nomeFile, DichiarazioneDiSpesaVO dichiarazioneDiSpesaVO,
			Long idUtente);

	/**
	 * Effettua la ricerca su INDEX della serie tipologica chiamata come il
	 * parametro in ingresso
	 * 
	 * @param name
	 *            il nome della serie tipologica di documenti
	 * @return L'oggetto {@link Node} relativo alla serie tipologica di
	 *         documenti desiderata.
	 * @throws MandatibcIntegrationException
	 *             Se si verifica un'errore nell'invocazione dei servizi di
	 *             INDEX.
	 */
	public Node cercaNodoRoot() throws Exception;

	public Node cercaNodo(String uid) throws Exception;

	public Node createFolder(Node padre, String keyPath) throws Exception;

	public void cancellaNodo(Node nodoDaCancellare)
			throws NodeNotFoundException;

	public byte[] recuperaContenuto(String uuid) throws NodeNotFoundException;

	public ContentMetadataVO recuperaMetadata(String uuid)
			throws NodeNotFoundException;

	public void aggiornaContent(Object dto, Long idUtente);

	public Node creaContentPropostaCertificazione(byte[] bytes,
			DocumentoIndexVO doc, PbandiTPropostaCertificazVO proposta,
			Long idUtente);

	public Node creaContentAllegatoPropostaProgettoCertificazione(byte[] bytes,
			DocumentoIndexVO doc, DettPropostaCertifVO dettaglioProposta,
			Long idUtente);

	public Node creaContentAllegatoDichichiarazioneFinalePropostaCertificazione(
			byte[] bytes, DocumentoIndexVO doc,
			PbandiTPropostaCertificazVO proposta, Long idUtente);

	public Node creaContentReportPropostaCertificazione(byte[] bytes,
			DocumentoIndexVO documentoVO,
			PbandiTPropostaCertificazVO propostaVO, Long idUtente);

	// nuovo per Report Chiusura Conti
	public Node creaContentReportChiusuraContiPropostaCertificazione(byte[] bytes,
			DocumentoIndexVO documentoVO,
			PbandiTPropostaCertificazVO propostaVO, Long idUtente);
	
	public Node creaContentRichiestaErogazione(
			PbandiTRichiestaErogazioneVO richiestaErogazioneVO,
			PbandiTProgettoVO progettoVO, String cfBeneficiario,
			Long idBeneficiario, Long idUtente, String nomeFile,
			byte[] reportBytes) throws Exception;

	public Node creaContentPropostaDiRimodulazione(
			PropostaRimodulazioneReportDTO dto, Long idUtente) throws Exception;

	public Node creaContentRimodulazione(RimodulazioneReportDTO dto,
			Long idUtente) throws Exception;

	public Node creaContentIrregolarita(IrregolaritaDTO irregolaritaDTO,
			String tipoDocumento, Long idUtente);

	public void aggiornaContentIrregolarita(IrregolaritaDTO irregolaritaDTO,
			String tipoDocumento, String uuidNodo, Long idUtente);

	public Node creaContentDichiarazioneRinuncia(Long idUtente,
			String nomeFile, PbandiTRinunciaVO rinuncia,
			PbandiTSoggettoVO beneficiario, PbandiTProgettoVO progetto,
			byte[] pdfBytes);

	public Node creaContent(Object dto, Long idUtente);

	public void rollbackNodesModifications();
	
	
	public Path[] getPaths(Node node) throws Exception;
	
	public Node[]  cercaNodiXPath(String xPath) throws Exception ;
	
	public Node[] findNodesByDate(java.util.Date from, java.util.Date to);
	
	public Node creaContentArchivioFile(Long idUtente,Long idSoggettoBen,
			String cfBeneficiario,Long idFolder, String nomeFile,byte[] bytes) throws Exception;

	public void aggiornaContentArchivioFile(String identitaDigitale,
			Long idUtente, String uuidNodo, Long idFolder)throws Exception;

	public String addTimestampToFileName(String nomeFile);
	
	public Node creaContentReportDettaglioDocSpesa(Long idUtente,Long idProgetto,
			String codiceProgetto, 
			Long idDichiarazione,
			Long idSoggettoBen,
			String cfBeneficiario, String nomeFile, byte []reportBytes)throws Exception;

	public void updateWithSignedContent(String uuidNodo, byte[] bytes)throws Exception;

	public void updateWithSignedContentNew(String uuidNodo, byte[] bytes)throws Exception;
	
	public void updateWithTimestampedContent(String uuidNodo,
			byte[] bytesTimestamped)throws Exception;
	
	public byte[] getTsd(String uuid) throws NodeNotFoundException;
	public byte[] getP7m(String uuid) throws NodeNotFoundException;

	public ResultContent getContentMetaData(String uuid) throws Exception;
	
	public Node creaContentUploadFile(Long idUtente,String nomeFile,byte[] bytes, Long idProgetto, String tipoDocumento, String categAnag) throws Exception;

}