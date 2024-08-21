/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.AttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.InserisciAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.RitenutaAttoNewVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;

public class BilancioXMLManager {
	
	/*
	 * Questa classe gestisce la creazione e lettura degli xml 
	 * relativi ai servizi esposti da Contabilia via web services.
	 * Utilizza le librerie DOM (Document Object Model) incluse in java.
	 */

	//private String DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_BOZZA = "0";
	private String UID = "-1";
	private String STATO_OP_DEFINITIVO = "DEFINITIVO";
	private String TIPO_ATTO_CODICE_ALG = "ALG";
	private String TIPO_DOC_CODICE = "PBANDI";
	private String TIPO_DOC_TIPO_FAMIGLIA = "SPESA";
	private String CODICE_BOLLO = "99";
	private String STATO_VALIDO = "VALIDO";
	@Autowired
	protected LoggerUtil logger;

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	// Data la stringa xml restituita dal servizio "leggiStatoElaborazioneDocumento()" esposto da Contabilia, 
	// legge l'attributo <numero> in <allegatoAtto.attoAmministrativo>' avente tipoAtto.codice=ALG.
	public String leggiXmlLeggiStatoElaborazioneDocumento_OLD(String xml) throws Exception {
		
		// Crea un documento parsificando la stringa in input..
		Document doc = this.parsificaDocumento(xml);		
		
		// Cerca i tag <attoAmministrativo>.
		NodeList nListAttoAmm = doc.getElementsByTagName("attoAmministrativo");
		int numTagAttoAmm = nListAttoAmm.getLength(); 
		if (numTagAttoAmm == 0) {
			logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo' non trovato");
			return "";
		}
		
		// Scorre i tag <allegatoAtto>.
		logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): scorro "+numTagAttoAmm+" tag <attoAmministrativo>");
		for(int i = 0; i < numTagAttoAmm; i++) {
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): tag <attoAmministrativo> numero "+i);
			Element elemAttoAmministrativo = (Element) nListAttoAmm.item(i);
			
			// Legge il valore del tag <attoAmministrativo.stato>.
			// Se stato � diverso da VALIDO, si ignora il tag <attoAmministrativo> corrente.
			NodeList nListStato = elemAttoAmministrativo.getElementsByTagName("stato");
			if (nListStato.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.stato' non trovato");
				return "";
			}
			String statoAttoAmm = nListStato.item(0).getTextContent();
			if (!STATO_VALIDO.equalsIgnoreCase(statoAttoAmm))  {
				logger.info("attoAmministrativo.stato = "+statoAttoAmm+" : passo al tag successivo");
				continue;
			}
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.stato = "+statoAttoAmm);
			
			// Cerca il tag <attoAmministrativo.tipoAtto>.
			NodeList nListTipoAtto = elemAttoAmministrativo.getElementsByTagName("tipoAtto");
			if (nListTipoAtto.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.tipoAtto' non trovato");
				return "";
			}
			Element elemTipoAtto = (Element) nListTipoAtto.item(0);
			
			// Legge il valore del tag <attoAmministrativo.tipoAtto.codice>.
			// Se vale ALG, � il tag in cui leggere il numero atto.
			// Altrimenti devo passare al tag <attoAmministrativo> successivo.
			NodeList nTipoAttoCodice = elemTipoAtto.getElementsByTagName("codice");
			if (nTipoAttoCodice.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.tipoAtto.codice' non trovato");
				return "";
			}
			String tipoAttoCodice = nTipoAttoCodice.item(0).getTextContent();
			if (!TIPO_ATTO_CODICE_ALG.equalsIgnoreCase(tipoAttoCodice))  {
				logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.tipoAtto.codice = "+tipoAttoCodice+" : passo al tag successivo");
				continue;
			}
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.tipoAttoCodice = "+tipoAttoCodice);
			
			// Legge il valore del tag <attoAmministrativo.numero>.
			NodeList nNumero = elemAttoAmministrativo.getElementsByTagName("numero");
			if (nNumero.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.numero' non trovato");
				return "";
			}
			String numero = nNumero.item(0).getTextContent();
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.numero = "+numero);
			
			return numero;
		}
		
		logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): elaborati tutti i tag 'attoAmministrativo': nessun numero atto trovato");
		return "";
	}
	
	// Dato l'xml restituito dal servizio "leggiStatoElaborazioneDocumento()" esposto da Contabilia, legge:
	// - l'attributo <numero> in <allegatoAtto.attoAmministrativo> avente tipoAtto.codice=ALG.
	// - l'attributo <dataEmissione> in <documentoSpesa>.
	public BilancioXMLManagerOutput leggiXmlLeggiStatoElaborazioneDocumento(String xml) throws Exception {
		
		// Crea un documento parsificando la stringa in input..
		Document doc = this.parsificaDocumento(xml);		
		
		BilancioXMLManagerOutput output = new BilancioXMLManagerOutput();
		output.setNumeroAtto(leggiNumeroAtto(doc));
		output.setDataEmissione(leggiDataEmissione(doc));
						
		return output;
	}
	
	// Legge il tag <numero> restituito da leggiStatoElaborazioneDocumento()
	// in <allegatoAtto>.<attoAmministrativo> avente tipoAtto.codice = ALG.
	private String leggiNumeroAtto(Document doc) throws Exception {
		
		// Cerca i tag <attoAmministrativo>.
		NodeList nListAttoAmm = doc.getElementsByTagName("attoAmministrativo");
		int numTagAttoAmm = nListAttoAmm.getLength(); 
		if (numTagAttoAmm == 0) {
			logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo' non trovato");
			return "";
		}
		
		// Scorre i tag <allegatoAtto>.
		logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): scorro "+numTagAttoAmm+" tag <attoAmministrativo>");
		for(int i = 0; i < numTagAttoAmm; i++) {
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): tag <attoAmministrativo> numero "+i);
			Element elemAttoAmministrativo = (Element) nListAttoAmm.item(i);
			
			// Legge il valore del tag <attoAmministrativo.stato>.
			// Se stato � diverso da VALIDO, si ignora il tag <attoAmministrativo> corrente.
			NodeList nListStato = elemAttoAmministrativo.getElementsByTagName("stato");
			if (nListStato.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.stato' non trovato");
				return "";
			}
			String statoAttoAmm = nListStato.item(0).getTextContent();
			if (!STATO_VALIDO.equalsIgnoreCase(statoAttoAmm))  {
				logger.info("attoAmministrativo.stato = "+statoAttoAmm+" : passo al tag successivo");
				continue;
			}
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.stato = "+statoAttoAmm);
			
			// Cerca il tag <attoAmministrativo.tipoAtto>.
			NodeList nListTipoAtto = elemAttoAmministrativo.getElementsByTagName("tipoAtto");
			if (nListTipoAtto.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.tipoAtto' non trovato");
				return "";
			}
			Element elemTipoAtto = (Element) nListTipoAtto.item(0);
			
			// Legge il valore del tag <attoAmministrativo.tipoAtto.codice>.
			// Se vale ALG, � il tag in cui leggere il numero atto.
			// Altrimenti devo passare al tag <attoAmministrativo> successivo.
			NodeList nTipoAttoCodice = elemTipoAtto.getElementsByTagName("codice");
			if (nTipoAttoCodice.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.tipoAtto.codice' non trovato");
				return "";
			}
			String tipoAttoCodice = nTipoAttoCodice.item(0).getTextContent();
			if (!TIPO_ATTO_CODICE_ALG.equalsIgnoreCase(tipoAttoCodice))  {
				logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.tipoAtto.codice = "+tipoAttoCodice+" : passo al tag successivo");
				continue;
			}
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.tipoAttoCodice = "+tipoAttoCodice);
			
			// Legge il valore del tag <attoAmministrativo.numero>.
			NodeList nNumero = elemAttoAmministrativo.getElementsByTagName("numero");
			if (nNumero.getLength() == 0) {
				logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'attoAmministrativo.numero' non trovato");
				return "";
			}
			
			String numero = nNumero.item(0).getTextContent();
			logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): attoAmministrativo.numero = "+numero);
			
			return numero;
			
		}
		
		logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): elaborati tutti i tag 'attoAmministrativo': nessun numero atto trovato");
		return "";
	}

	// Legge il tag <documentoSpesa>.<dataEmissione> restituito da leggiStatoElaborazioneDocumento().
	private java.sql.Date leggiDataEmissione(Document doc) throws Exception {
		
		// Cerca i tag <documentoSpesa>.
		NodeList nListDocumentoSpesa = doc.getElementsByTagName("documentoSpesa");
		int numTagDocumentoSpesa = nListDocumentoSpesa.getLength(); 
		if (numTagDocumentoSpesa == 0) {
			logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'documentoSpesa' non trovato");
			return null;
		}
		
		// Considera il primo tag <documentoSpesa>. 
		Element elemDocumentoSpesa = (Element) nListDocumentoSpesa.item(0);
		
		// Legge il valore del tag <documentoSpesa.dataEmissione> (stringa tipo "2017-03-27T00:00:00+02:00").
		NodeList nListDataEmissione = elemDocumentoSpesa.getElementsByTagName("dataEmissione");
		if (nListDataEmissione.getLength() == 0) {
			logger.error("leggiXmlLeggiStatoElaborazioneDocumento(): tag 'documentoSpesa.dataEmissione' non trovato");
			return null;
		}

		String dataEmissione = nListDataEmissione.item(0).getTextContent();
		logger.info("leggiXmlLeggiStatoElaborazioneDocumento(): documentoSpesa.dataEmissione = "+dataEmissione);
		
		return formattaData(dataEmissione);
	}
	
	// In base ai dati di input, restituisce una stringa contenente
	// l'xlm da assegnare all'attributo "contenutoDocumento" del servizio "elaboraDocumento()" esposto da Contabilia.
	// Formato importi: 15000.21
	// Formato date:    2016-11-30
	public String creaXmlElaboraDocumento(InserisciAttoLiquidazioneVO vo) throws Exception {
		
		// Istanzia un nuovo documento.
		Document doc = istanziaDocumento();
		
		// Popola il documento.
		popolaXmlElaboraDocumento(doc, vo);
		
		// Serializza il documento (da Document a String).
		String xml = serializzaDocumento(doc);
		
		return xml;
	}
	
	private Document popolaXmlElaboraDocumento(Document doc, InserisciAttoLiquidazioneVO vo) {
		creaElenchiDocumentiAllegato(doc, vo);		
		return doc;	
	}
	
	private Document creaElenchiDocumentiAllegato(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaElenchiDocumentiAllegato() inizio");
		Element elem = doc.createElement("elenchiDocumentiAllegato");
		elem.appendChild(creaElenchi(doc, vo));
		doc.appendChild(elem);
		logger.info("BilancioXMLManager.creaElenchiDocumentiAllegato() fine");
		return doc;	
	}
	
	private Element creaElenchi(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaElenchi() inizio");
		Element elem = doc.createElement("elenchi");
		elem.appendChild(creaElenco(doc, vo));
		logger.info("BilancioXMLManager.creaElenchi() fine");
		return elem;	
	}
	
	private Element creaElenco(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaElenco() inizio");
		
		//Element annoSysEsterno = creaAttributo("annoSysEsterno","2016",doc);
		Element annoSysEsterno = creaAttributo("annoSysEsterno",vo.getAttoLiquidazione().getAtto().getAnnoAtto(),doc);
		
		Element numeroSysEsterno = creaAttributo("numeroSysEsterno",Constants.CONTABILIA_CODICE_FRUITORE,doc);
		Element allegatoAtto = creaAllegatoAtto(doc, vo);
		Element subDocumenti = creaSubdocumenti(doc, vo);
		
		Element elem = doc.createElement("elenco");
		elem.appendChild(annoSysEsterno);
		elem.appendChild(numeroSysEsterno);
		elem.appendChild(allegatoAtto);
		elem.appendChild(subDocumenti);
		
		logger.info("BilancioXMLManager.creaElenco() fine");
		return elem;	
	}
	
	private Element creaAllegatoAtto(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaAllegatoAtto() inizio");
		
		Element uid = creaAttributo("uid",UID,doc);
		Element causale = creaAttributo("causale",vo.getAttoLiquidazione().getAtto().getCausalePagam(),doc);
		Element datiSensibili = creaAttributo("datiSensibili","false",doc);
		Element attoAmministrativo = creaAttoAmministrativo(doc, vo);
		
		Element elem = doc.createElement("allegatoAtto");
		elem.appendChild(uid);
		elem.appendChild(causale);
		elem.appendChild(datiSensibili);
		elem.appendChild(attoAmministrativo);
		
		logger.info("BilancioXMLManager.creaAllegatoAtto() fine");
		return elem;	
	}
	
	private Element creaAttoAmministrativo(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaAttoAmministrativo() inizio");
		
		//Element anno = creaAttributo("anno","2016",doc);
		Element anno = creaAttributo("anno",vo.getAttoLiquidazione().getAtto().getAnnoAtto(),doc);

		Element tipoAtto = creaTipoAtto(doc, vo);
		Element statoOperativo = creaAttributo("statoOperativo",STATO_OP_DEFINITIVO,doc);
		String ogg = "LIQUIDAZIONE "+vo.getAttoLiquidazione().getAtto().getDescCausale();
		if (ogg.length() > 150)
			ogg = ogg.substring(0, 150);
		Element oggetto = creaAttributo("oggetto",ogg,doc);
		Element strutturaAmmContabile = creaStrutturaAmmContabile(doc, vo);
		
		Element elem = doc.createElement("attoAmministrativo");
		elem.appendChild(anno);
		elem.appendChild(tipoAtto);
		elem.appendChild(statoOperativo);
		elem.appendChild(oggetto);
		elem.appendChild(strutturaAmmContabile);
		
		logger.info("BilancioXMLManager.creaAttoAmministrativo() fine");
		return elem;	
	}
	
	private Element creaTipoAtto(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaTipoAtto() inizio");
		
		Element codice = creaAttributo("codice",TIPO_ATTO_CODICE_ALG,doc);
		
		Element elem = doc.createElement("tipoAtto");
		elem.appendChild(codice);
		
		logger.info("BilancioXMLManager.creaTipoAtto() fine");
		return elem;	
	}
	
	private Element creaStrutturaAmmContabile(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaTipoAtto() inizio");
		
		Element codice = creaAttributo("codice",vo.getAttoLiquidazione().getAtto().getStrutturaAmmContabile(),doc);
		
		Element elem = doc.createElement("strutturaAmmContabile");
		elem.appendChild(codice);
		
		logger.info("BilancioXMLManager.creaTipoAtto() fine");
		return elem;	
	}
	
	private Element creaSubdocumenti(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaSubdocumenti() inizio");

		Element elem = doc.createElement("subdocumenti");
		
		ImpegnoVO[] impegni = vo.getImpegni();
		
		if (impegni != null) {
			int numSubDocumenti = impegni.length;
			
			// Calcola la somma degli importi dei vari impegni.
			BigDecimal sommaImporti = new BigDecimal(0); 
			for(int i = 0; i < numSubDocumenti; i++) {
				sommaImporti = sommaImporti.add(impegni[i].getImportoAttualeImpegno());
			}
			
			// Genera l'xml relativo a ciascun singolo impegno.
			for(int i = 0; i < numSubDocumenti; i++) {
				Element subdocumentoSpesa = creaSubdocumentoSpesa(doc, vo, i, sommaImporti);
				elem.appendChild(subdocumentoSpesa);
			}
		}
		
		logger.info("BilancioXMLManager.creaSubdocumenti() fine");
		return elem;	
	}
	
	private Element creaSubdocumentoSpesa(Document doc, InserisciAttoLiquidazioneVO vo, int i, BigDecimal sommaImporti) {
		logger.info("BilancioXMLManager.creaSubdocumentoSpesa() inizio");
		
		ImpegnoVO[] impegni = vo.getImpegni();
		AttoVO atto = vo.getAttoLiquidazione().getAtto();
		
		Element documentoSpesa = creaDocumentoSpesa(doc, vo, i, sommaImporti);
		Element importo = creaAttributo("importo",formattaImporto(impegni[i].getImportoAttualeImpegno()),doc);	// formato: 15000.21
		String descr = "Quota sul movimento "+impegni[i].getAnnoImpegno()+"/"+impegni[i].getNumeroImpegno();
		Element descrizione = creaAttributo("descrizione",descr,doc);
		Element note = creaAttributo("note",(i+1)+" quota",doc);
		Element impegno = creaImpegno(doc, vo, i);
		Element modPagam = creaModalitaPagamentoSoggetto(doc, vo);
		Element cup = creaAttributo("cup",impegni[i].getCup(),doc);
		
		Element elem = doc.createElement("subdocumentoSpesa");
		elem.appendChild(documentoSpesa);
		elem.appendChild(importo);
		elem.appendChild(descrizione);
		elem.appendChild(note);
		elem.appendChild(impegno);
		elem.appendChild(modPagam);
		elem.appendChild(cup);
				
		logger.info("BilancioXMLManager.creaSubdocumentoSpesa() fine");
		return elem;	
	}
	
	private Element creaDocumentoSpesa(Document doc, InserisciAttoLiquidazioneVO vo, int i, BigDecimal sommaImporti) {
		logger.info("BilancioXMLManager.creaDdocumentoSpesa() inizio");
		
		Element elem = doc.createElement("documentoSpesa");
		
		Element uid = creaAttributo("uid",UID,doc);
		elem.appendChild(uid);
		
		// Elementi previsti solo nella prima occorrenza del tag 'subdocumentoSpesa',
		// in cui il tag 'documentoSpesa' � contenuto.
		if (i == 0) {
			AttoVO atto = vo.getAttoLiquidazione().getAtto();
			
			//Element anno = creaAttributo("anno","2016",doc);
			Element anno = creaAttributo("anno",atto.getAnnoAtto(),doc);
			
			Element numero = creaAttributo("numero",atto.getNumeroDocSpesa(),doc);
			Element importo = creaAttributo("importo",formattaImporto(sommaImporti),doc);
			Element descrizione = creaAttributo("descrizione",atto.getDescizioneDocSpesa(),doc);
			Element codiceBollo = creaCodiceBollo(doc, vo);
			//Element dataEmissione = creaAttributo("dataEmissione",formattaDataOdierna(),doc);
			Element dataEmissione = creaAttributo("dataEmissione",formattaData(atto.getDataEmisAtto()),doc);
			Element soggetto = creaSoggetto(doc, vo);
			Element tipoDocumento = creaTipoDocumento(doc, vo);
			
			elem.appendChild(anno);
			elem.appendChild(numero);
			elem.appendChild(importo);
			elem.appendChild(descrizione);
			elem.appendChild(codiceBollo);
			elem.appendChild(dataEmissione);
			elem.appendChild(soggetto);
			elem.appendChild(tipoDocumento);
			if (vo.getRitenutaAttoNew() != null) {
				Element ritenuteDocumento = creaRitenuteDocumento(doc, vo);
				elem.appendChild(ritenuteDocumento);
			}
		}
		
		logger.info("BilancioXMLManager.creaDocumentoSpesa() fine");
		return elem;	
	}
	
	private Element creaCodiceBollo(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaCodiceBollo() inizio");
		
		Element codice = creaAttributo("codice",CODICE_BOLLO,doc);
		
		Element elem = doc.createElement("codiceBollo");
		elem.appendChild(codice);
		
		logger.info("BilancioXMLManager.creaCodiceBollo() fine");
		return elem;	
	}
	
	private Element creaSoggetto(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaSoggetto() inizio");
		
		String codBen = "";
		if (vo != null && vo.getAttoLiquidazione() != null && 
			vo.getAttoLiquidazione().getFornitore() != null &&
			vo.getAttoLiquidazione().getFornitore().getCodben() != null)
			codBen = vo.getAttoLiquidazione().getFornitore().getCodben().toString();
				
		Element codice = creaAttributo("codiceSoggetto",codBen,doc);
		
		Element elem = doc.createElement("soggetto");
		elem.appendChild(codice);
		
		logger.info("BilancioXMLManager.creaSoggetto() fine");
		return elem;	
	}
	
	private Element creaTipoDocumento(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaTipoDocumento() inizio");
		
		Element codice = creaAttributo("codice",TIPO_DOC_CODICE,doc);
		Element tipoFamigliaDocumento = creaAttributo("tipoFamigliaDocumento",TIPO_DOC_TIPO_FAMIGLIA,doc);
		
		Element elem = doc.createElement("tipoDocumento");
		elem.appendChild(codice);
		elem.appendChild(tipoFamigliaDocumento);		
		
		logger.info("BilancioXMLManager.creaTipoDocumento() fine");
		return elem;	
	}
	
	// Alex: nuova gestione ritenuta.
	private Element creaRitenuteDocumento(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaRitenuteDocumento() inizio");
		
		Element listaOnere = creaListaOnere(doc, vo);
			
		Element elem = doc.createElement("ritenuteDocumento");
		elem.appendChild(listaOnere);
		
		logger.info("BilancioXMLManager.creaRitenuteDocumento() fine");
		return elem;	
	}
	
	// Alex: nuova gestione ritenuta.
	private Element creaListaOnere(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaListaOnere() inizio");
		
		RitenutaAttoNewVO r = vo.getRitenutaAttoNew();
		Element importoCaricoEnte = creaAttributo("importoCaricoEnte",formattaImporto(r.getImportoCaricoEnte()),doc);
		Element importoCaricoSoggetto = creaAttributo("importoCaricoSoggetto",formattaImporto(r.getImportoCaricoSoggetto()),doc);
		Element importoImponibile = creaAttributo("importoImponibile",formattaImporto(r.getImportoImponibile()),doc);
		Element sommaNonSoggetta = creaAttributo("sommaNonSoggetta",formattaImporto(r.getImpNonSoggettoRitenuta()),doc);
		Element tipoOnere = creaTipoOnere(doc, vo);
	
		Element elem = doc.createElement("listaOnere");
		elem.appendChild(importoCaricoEnte);
		elem.appendChild(importoCaricoSoggetto);
		elem.appendChild(importoImponibile);
		elem.appendChild(sommaNonSoggetta);
		elem.appendChild(tipoOnere);
		
		logger.info("BilancioXMLManager.creaListaOnere() fine");
		return elem;	
	}
	
	// Alex: nuova gestione ritenuta.
	private Element creaTipoOnere(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaTipoOnere() inizio");
		
		RitenutaAttoNewVO r = vo.getRitenutaAttoNew();
		Element codice = creaAttributo("codice",r.getCodOnere(),doc);
		Element naturaOnere = creaNaturaOnere(doc, vo);
	
		Element elem = doc.createElement("tipoOnere");
		elem.appendChild(codice);
		elem.appendChild(naturaOnere);
		
		logger.info("BilancioXMLManager.creaTipoOnere() fine");
		return elem;	
	}
	
	// Alex: nuova gestione ritenuta.
	private Element creaNaturaOnere(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaNaturaOnere() inizio");
		
		RitenutaAttoNewVO r = vo.getRitenutaAttoNew();
		Element codice = creaAttributo("codice",r.getCodNaturaOnere(),doc);
	
		Element elem = doc.createElement("naturaOnere");
		elem.appendChild(codice);
		
		logger.info("BilancioXMLManager.creaNaturaOnere() fine");
		return elem;	
	}
	
	private Element creaImpegno(Document doc, InserisciAttoLiquidazioneVO vo, int i) {
		logger.info("BilancioXMLManager.creaImpegno() inizio");
		
		ImpegnoVO[] impegni = vo.getImpegni();
		String annoImpegno = impegni[i].getAnnoImpegno();
		String numeroImpegno = "";
		if (impegni[i].getNumeroImpegno() != null)
			numeroImpegno = impegni[i].getNumeroImpegno().toString();
		
		Element annoMovimento = creaAttributo("annoMovimento",annoImpegno,doc);
		Element numero = creaAttributo("numero",numeroImpegno,doc);
		
		Element elem = doc.createElement("impegno");
		elem.appendChild(annoMovimento);
		elem.appendChild(numero);		
		
		logger.info("BilancioXMLManager.creaImpegno() fine");
		return elem;	
	}
	
	private Element creaModalitaPagamentoSoggetto(Document doc, InserisciAttoLiquidazioneVO vo) {
		logger.info("BilancioXMLManager.creaModalitaPagamentoSoggetto() inizio");
		
		String progBen = "";
		if (vo.getAttoLiquidazione().getBeneficiario() != null &&
			vo.getAttoLiquidazione().getBeneficiario().getProgBen() != null)
			progBen = vo.getAttoLiquidazione().getBeneficiario().getProgBen().toString();
			
		Element codiceModalitaPagamento = creaAttributo("codiceModalitaPagamento",progBen,doc);
		
		Element elem = doc.createElement("modalitaPagamentoSoggetto");
		elem.appendChild(codiceModalitaPagamento);
		
		logger.info("BilancioXMLManager.creaModalitaPagamentoSoggetto() fine");
		return elem;	
	}
	
	private Element creaAttributo(String nome, String valore, Document doc) {
		Element elem = doc.createElement(nome);
		if (!StringUtils.isEmpty(valore)) {
			Text text = doc.createTextNode(valore);
			elem.appendChild(text);
		}
		return elem;
	}
	
	// Crea un nuovo Documento xml vuoto.
	private Document istanziaDocumento() throws Exception {		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			return docBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			logger.error("BilancioXMLManager.istanziaDocumento(): "+e);
			throw new Exception("Errore nella creazione del documento xml.");
		}		
	}
	
	// Trasforma un Documento xml in una stringa.
	private String serializzaDocumento(Document doc) throws Exception {				
		try {
			
			DOMSource domSource = new DOMSource(doc);
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			
			Transformer txformer = TransformerFactory.newInstance().newTransformer();
			txformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    txformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		    txformer.setOutputProperty(OutputKeys.INDENT, "yes");
			txformer.transform(domSource, sr);
			
			String stringaXml = sw.toString();
			//logger.info("BilancioXMLManager.serializzaDocumento(): xml serializzato\n\n"+stringaXml+"\n\n");			
			return stringaXml;
			
		} catch (Exception e) {
			logger.error("BilancioXMLManager.serializzaDocumento(): "+e);
			throw new Exception("Errore nella serializzazione del documento xml.");
		} 		
	}
	
	// Crea un Documento xml a partire da una stringa.
	private Document parsificaDocumento(String xml) throws Exception {				
		try {
			
			StringReader sr = new StringReader(xml); 
			InputSource is = new InputSource(sr); 
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);	
			doc.getDocumentElement().normalize();
			return doc;
			
		} catch (Exception e) {
			logger.error("BilancioXMLManager.parsificaDocumento(): "+e);
			throw new Exception("Errore nella parsificazione della stringa xml.");
		} 		
	}
	
	// Restituisce una stringa nel formato: 15000.21
	private String formattaImporto(BigDecimal bd) {
		logger.info("BilancioXMLManager.formattaImporto(): input = "+bd);
		String output = "";
		if (bd != null)
			output = NumberUtil.getStringValueAnglFormat(bd);
		logger.info("BilancioXMLManager.formattaImporto(): output = "+output);
		return output;
	}
	
	// Restituisce una stringa nel formato: 15000.21
	private String formattaImporto(Double d) {
		logger.info("BilancioXMLManager.formattaImporto(): input = "+d);
		String output = "";
		if (d != null)
			output = NumberUtil.getStringValue(d);
		logger.info("BilancioXMLManager.formattaImporto(): output = "+output);
		return output;
	}
	
	// Restituisce una data nel formato: 2016-11-30
	private String formattaData(java.util.Date d) {
		logger.info("BilancioXMLManager.formattaData(): input = "+d);
		String output = "";
		if (d != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			output = sdf.format(d);
		}
		logger.info("BilancioXMLManager.formattaData(): output = "+output);
		return output;
	}
	
	// Restituisce la data odierna nel formato: 2016-11-30
	private String formattaDataOdierna() {
		String output = "";
		java.util.Date oggi = it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil.getDataOdierna();
		if (oggi != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			output = sdf.format(oggi);
		}
		logger.info("BilancioXMLManager.formattaDataOdierna(): output = "+output);
		return output;
	}
	
	// Trasforma una stringa del tipo "2017-03-27T00:00:00+02:00" in java.sql.Date.
	private java.sql.Date formattaData(String s) {
		logger.error("formattaData(): stringa input = "+s);
		if (s == null || s.length() < 10)
			return null;
		java.sql.Date output = null;
		try {
			String s1 = s.substring(0,10);
			logger.error("formattaData(): stringa ridotta = "+s1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse(s1);
			output = it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil.utilToSqlDate(d);
		} catch (Exception e) {
			logger.error("formattaData(): errore nella formattazione "+s);
			logger.error("formattaData(): "+e);
			return null;
		}
		logger.info("BilancioXMLManager.formattaData(): output = "+output);
		return output;
	}
	
}
