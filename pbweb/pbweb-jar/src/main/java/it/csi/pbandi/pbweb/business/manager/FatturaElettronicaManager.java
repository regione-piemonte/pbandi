/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.csi.pbandi.pbweb.dto.FornitoreFormDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DatiFatturaElettronica;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.EsitoLetturaXmlFattElett;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.impl.FornitoreDAOImpl;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.NumberUtil;
import it.csi.pbandi.pbweb.util.RequestUtil;
import it.csi.pbandi.pbweb.util.StringUtil;

public class FatturaElettronicaManager  extends JdbcDaoSupport {
	
	//Metodi per la gestione della fattura elettronica usata in Rendicontazione -> Documento di Spesa.
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected DecodificheDAOImpl decodificheDAOImpl;
	
	@Autowired
	protected FornitoreDAOImpl fornitoreDAOImpl;	
	
	@Autowired
	protected DocumentoManager documentoManager;
			
	@Autowired
	public FatturaElettronicaManager(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	// Ex DocumentoDiSpesaAction.esitoLetturaXmlFattElett()
	// Legge i dati dall'xml selezionato tramite Archivio File e
	// restituisce in output l'esito della lettura e del confronto tra fornitore in fattura e su db.
	// - idDocumentoIndex = id della fatt elett selezionata tramite Archivio File.
	// - idSoggettoBeneficiario = user.getBeneficiarioSelezionato().getIdSoggetto().
	public EsitoLetturaXmlFattElett esitoLetturaXmlFattElett(Long idDocumentoIndex, Long idSoggettoBeneficiario, Long idTipoOperazioneProgetto, Long idUtente, String idIride) {
		String prf = "[FatturaElettronicaManager::esitoLetturaXmlFattElett] ";
		LOG.info(prf + "BEGIN");
		
		EsitoLetturaXmlFattElett output = new EsitoLetturaXmlFattElett();
		try {
			
			if (idDocumentoIndex == null) {				
				output.setEsito(Constants.FATT_ELETT_NESSUN_XML_SELEZIONATO);
				return output;
			}
				
			//  TODO ****  NOTA: IN ORIGINE E' IL DOC DI SPESA A VIDEO - TEMPORANEAMENTE NE CREO UNO VUOTO E POI SI VEDRA'. 
			//DocumentoDiSpesaDTO docSpesa = new DocumentoDiSpesaDTO(); 
			
			// Tolgo un eventuale fornitore precedentemente selezionato a video.
			//  TODO ****  NOTA: DA SCOMMENTARE SE DOCSPESA CONTERRA' I VALORI A VIDEO ANZICHE' ESSERE VUOTO.
			//docSpesa.setIdFornitore(null);
						
			// Legge i dati della fattura dal file xml. 
			DatiFatturaElettronica datiFattura = this.caricaFatturaElettronica(idDocumentoIndex);
			output.setDatiFatturaElettronica(datiFattura);
			
			if (datiFattura == null) {
				output.setEsito(Constants.FATT_ELETT_XML_NON_CORRETTO);
				return output;
			}
			
			LOG.info(prf + datiFattura.toString());			
			
			// Verifico se per il beneficiario corrente esiste già nel db un fornitore persona giuridica con lo stesso cf della fattura.
			FornitoreFormDTO fornitoreDB = fornitoreDAOImpl.cercaFornitoreFattElett(datiFattura.getCodiceFiscaleFornitore(), idSoggettoBeneficiario, idUtente, idIride);
			output.setFornitoreDb(fornitoreDB);
			
			// Casi gestiti :
			//  - nessun fornitore trovato:
			//     se TipoOperazione = 3, si può inserire
			//     se TipoOperazione = 1 o 2, non si può inserire.
			//  - fornitore con cf , denominazione e partita iva uguali.
			//  - fornitore con cf uguale, ma denominazione e partita iva diversi.
			int idTipoOperazione = new Long(idTipoOperazioneProgetto).intValue();
			if (fornitoreDB == null) {
				LOG.info(prf + "Nessun fornitore su db.");	
				if (idTipoOperazione == 3) {
					// La fattura non prevede un affidamento: verrà aperta la popup Nuovo Fornitore.
					output.setEsito(Constants.FATT_ELETT_FORNITORE_NUOVO_TIPO_OP_3);
				} else {
					// La fattura prevede un affidamento: non posso inserire un nuovo fornitore.					
					output.setEsito(Constants.FATT_ELETT_FORNITORE_NUOVO);							
				}	
			} else {
				LOG.info(prf + "Trovato fornitore su db con id fornitore = " + fornitoreDB.getIdFornitore());
				if (fornitoriUguali(datiFattura, fornitoreDB)) {
					output.setEsito(Constants.FATT_ELETT_FORNITORE_UGUALE);
					// Tale fornitore verra' selezionato nella combo dei fornitori.
				} else {
					if (idTipoOperazione == 3) {
						// La fattura non prevede un affidamento: verrà aperta una popup per scegliere tra i 2 fornitori. 
						output.setEsito(Constants.FATT_ELETT_FORNITORE_DIVERSO_TIPO_OP_3);
					} else {
						// La fattura prevede un affidamento: non si può usare un nuovo fornitore.
						// Uso i dati del fornitore trovato su db.				
						output.setEsito(Constants.FATT_ELETT_FORNITORE_DIVERSO);
					}	
				}
			}
			
			
		} catch (Exception e) {
			LOG.error(prf + "ERRORE: ",e);
			output.setEsito(Constants.FATT_ELETT_ERRORE_NON_PREVISTO);			
		} finally {
			LOG.info(prf + "esito = "+output.getEsito());
			LOG.info(prf+" END");
		}
			
		return output;
	}
	
	private DatiFatturaElettronica caricaFatturaElettronica(Long idDocumentoIndex) throws Exception{
		String prf = "[FatturaElettronicaManager::caricaFatturaElettronica] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex);
		
		List<DecodificaDTO> schemiFattElett = decodificheDAOImpl.schemiFatturaElettronica();
		
		// EsitoScaricaDocumentoDTO esitoFatturaElettronica = gestioneDocumentazioneSrv.scaricaDocumento(idUtente, idIride, idDocumentoIndex);
		FileDTO fileDTO = documentoManager.leggiFile(idDocumentoIndex);		
		if (fileDTO == null || fileDTO.getBytes() == null) {
			LOG.error(prf + "File fattura nullo.");
			return null;
		}
		LOG.info(prf + "Dimensione file fattura = "+fileDTO.getBytes().length);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		DatiFatturaElettronica datiFattura = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			ByteArrayInputStream bis = new ByteArrayInputStream(fileDTO.getBytes());
			Document doc = dBuilder.parse(bis);
			LOG.info(prf + "File parsificato. ");
			doc.getDocumentElement().normalize();
			LOG.info(prf + "File normalizzato; root element : " + doc.getDocumentElement().getNodeName());
			
			if (!xmlFattElettValido(doc, schemiFattElett)) {
				LOG.error(prf + "Xml non valido.");
				return null;
			}
			LOG.info(prf + "Xml valido.");
			
			datiFattura = popolaDocumentoDiSpesaDaFattura(doc);
			
		} catch (SAXException ex){
			logger.error("Error: " , ex);
			ex.printStackTrace();
			logger.error("Error: " , ex);
			return null;
		}catch (ParserConfigurationException ex){
			ex.printStackTrace();
			return null;
		}catch(IOException e){
			logger.error("Error: " , e);
			e.printStackTrace();
			return null;
		}
		return datiFattura;
	}
	
	private boolean xmlFattElettValido(Document doc, List<DecodificaDTO> schemiFattElett) {
		String prf = "[FatturaElettronicaManager::xmlFattElettValido] ";
		String formato = null;
		try {
			formato = getNodeValue("FormatoTrasmissione" , doc);
			LOG.info(prf + "FormatoTrasmissione = "+formato);
		} catch (Exception e) {
			LOG.error(prf + "Errore nella lettura del FormatoTrasmissione: "+e);
			return false;
		}
		if (StringUtils.isBlank(formato)) {
			return false;
		}
		if (schemiFattElett != null) {
			for (DecodificaDTO dto : schemiFattElett) {
				if (formato.equalsIgnoreCase(dto.getDescrizioneBreve())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String getNodeValue(String tagName, Document doc) throws Exception{
		try {
			NodeList nodeList = doc.getElementsByTagName(tagName).item(0).getChildNodes();
			Node node =(Node) nodeList.item(0);
			return node.getNodeValue();
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getNodeValue(String tagName, Element el) {
		try {
			NodeList nodeList = el.getElementsByTagName(tagName).item(0).getChildNodes();
			Node node =(Node) nodeList.item(0);
			return node.getNodeValue();
		} catch (Exception e) {
			return "";
		}
	}
	
	private DatiFatturaElettronica popolaDocumentoDiSpesaDaFattura(Document doc) throws Exception {
		String prf = "[FatturaElettronicaManager::popolaDocumentoDiSpesaDaFattura] ";
		LOG.info(prf + " BEGIN");
		
		DatiFatturaElettronica docSpesa = new DatiFatturaElettronica();
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		//Dati generali documento

		// Numero documento di spesa.
		docSpesa.setNumeroDocumento(getNodeValue("Numero" , doc));
		
		//Data documento di spesa
		try {
			String data = getNodeValue("Data", doc);		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			Date dateObject = sdf.parse(data);				
			docSpesa.setDataDocumentoDiSpesa(dateObject);	
		} catch (Exception e) {
			docSpesa.setDataDocumentoDiSpesa(null);
		}
		
		//Descrizione documento: provo prima con <Causale>, poi con <Descrizione>.
		String descr = "";
		NodeList causali = doc.getElementsByTagName("Causale");
		if (causali != null && causali.getLength() > 0) {
		    for (int i = 0; i < causali.getLength(); i++) {
		      Node node = causali.item(i);
		      Element elem = (Element) node;
		      String separatore = (descr.length() == 0) ? "" : " ";
		      descr = descr + elem.getTextContent();
		    }
		}
		if (StringUtils.isBlank(descr)) {
			Node descrizioneDatiBeniServizi = doc.getElementsByTagName("DatiBeniServizi").item(0);
			Element elementDescrizione = (Element) descrizioneDatiBeniServizi;
			String numeroLinee = getNodeValue("NumeroLinea", elementDescrizione);
			if(numeroLinee != null && !StringUtil.isEmpty(numeroLinee)) {
				String[] linee = numeroLinee.split(",");
				if(linee.length == 1){
					descr = getNodeValue("Descrizione", elementDescrizione);					
				}
			}
		}
		docSpesa.setDescrizioneDocumentoDiSpesa(descr);
		
		
		//Importi
		docSpesa.setImponibile(NumberUtil.toNullableDoubleItalianFormat(getNodeValue("ImponibileImporto", doc)));
		docSpesa.setImportoIva(NumberUtil.toNullableDoubleItalianFormat(getNodeValue("Imposta", doc)));
		docSpesa.setImportoTotaleDocumentoIvato(NumberUtil.toNullableDoubleItalianFormat(getNodeValue("ImportoPagamento", doc)));		
		
		//Dati fornitore
		Node datiFornitoreNode = doc.getElementsByTagName("CedentePrestatore").item(0);		
		Element elFornitore = (Element) datiFornitoreNode;
		docSpesa.setDenominazioneFornitore(getNodeValue("Denominazione", elFornitore));
		docSpesa.setCodiceFiscaleFornitore(getNodeValue("CodiceFiscale", elFornitore));
		docSpesa.setPartitaIvaFornitore(getNodeValue("IdCodice", elFornitore));
		// Se il tag <CodiceFiscale> non è presente nell'xml, lo si valorizza con la partita iva. 
		if (StringUtils.isBlank(docSpesa.getCodiceFiscaleFornitore())) {
			docSpesa.setCodiceFiscaleFornitore(docSpesa.getPartitaIvaFornitore());
		}
		
		docSpesa.setFlagPubblicoPrivatoFornitore("S");
		docSpesa.setIdTipoFornitore(new Long(Constants.ID_TIPOLOGIA_PERSONA_GIURIDICA));
		docSpesa.setCodTipologiaFornitore("EG");
		docSpesa.setFlagElettronico("S");
		docSpesa.setFlagElettXml("S");						// marca che è stato allegata una fattura elettronica.
		docSpesa.setIdAppalto(null);
		docSpesa.setDescrizioneAppalto(null);
		
		// Accorcia i campi per il db.
		if (docSpesa.getNumeroDocumento().length() > 100)
			docSpesa.setNumeroDocumento(docSpesa.getNumeroDocumento().substring(0,100));		
		if (docSpesa.getDescrizioneDocumentoDiSpesa().length() > 2000)
			docSpesa.setDescrizioneDocumentoDiSpesa(docSpesa.getDescrizioneDocumentoDiSpesa().substring(0,2000));		
		if (docSpesa.getDenominazioneFornitore().length() > 255)
			docSpesa.setDenominazioneFornitore(docSpesa.getDenominazioneFornitore().substring(0,255));		
		if (docSpesa.getCodiceFiscaleFornitore().length() > 32)
			docSpesa.setCodiceFiscaleFornitore(docSpesa.getCodiceFiscaleFornitore().substring(0,32));		
		if (docSpesa.getPartitaIvaFornitore().length() > 11)
			docSpesa.setPartitaIvaFornitore(docSpesa.getPartitaIvaFornitore().substring(0,11));
		
		if (docSpesa.getNumeroDocumento().length() > 100)
			docSpesa.setNumeroDocumento(docSpesa.getNumeroDocumento().substring(0,100));
		if (docSpesa.getNumeroDocumento().length() > 100)
			docSpesa.setNumeroDocumento(docSpesa.getNumeroDocumento().substring(0,100));
		if (docSpesa.getNumeroDocumento().length() > 100)
			docSpesa.setNumeroDocumento(docSpesa.getNumeroDocumento().substring(0,100));
		
		LOG.info(prf + " END");
		return docSpesa;
	}
	
	// Restituisce true se i 2 fornitori in input hanno le stesse denominazioni e p.iva.
	private boolean fornitoriUguali(DatiFatturaElettronica datiFattura, FornitoreFormDTO fornitoreDB) {
		return (datiFattura.getDenominazioneFornitore().equalsIgnoreCase(fornitoreDB.getDenominazioneFornitore()) && 
				datiFattura.getPartitaIvaFornitore().equalsIgnoreCase(fornitoreDB.getPartitaIvaFornitore()));
	}

}
