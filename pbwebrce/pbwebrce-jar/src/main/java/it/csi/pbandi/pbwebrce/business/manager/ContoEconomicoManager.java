/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.business.intf.MessageKey;
//import it.csi.pbandi.pbservizitpbandiutil.commonweb.MessageManager;
import it.csi.pbandi.pbwebrce.dto.ContoEconomico;
import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;
import static it.csi.pbandi.pbwebrce.util.Constants.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class ContoEconomicoManager {
	
	@Autowired
	private  LoggerUtil logger;
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}
	public LoggerUtil getLogger() {
		return logger;
	}
	
	public ArrayList<ContoEconomicoItem> mappaContoEconomicoPerVisualizzazione(
			ContoEconomico contoEconomico,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			ArrayList<ContoEconomicoItem> contoEconomicoItemListPerImporto,
			boolean isCopiaPresente,
			boolean isModifica,
			Date dataFineIstruttoria,
			Boolean inIstruttoria,
			Boolean isRimodulazione) {
		
		if (isRimodulazione) {
			
			logger.info("mappaContoEconomicoPerVisualizzazione per Rimodulazione");
			
			Double  importo= new Double(0);
			Map <String,Double>mapImporto=new HashMap<String,Double>();
			mapImporto.put(IMPORTO,importo);
			
			ArrayList<ContoEconomicoItem> contoEconomicoItemList=new ArrayList<ContoEconomicoItem>();
			if (!ObjectUtil.isNull(contoEconomicoRimodulazione)) {

				ContoEconomicoRimodulazioneDTO contoEconomicoDTOarray[] = contoEconomicoRimodulazione.getFigli();
				if (!ObjectUtil.isEmpty(contoEconomicoDTOarray)) {
					int level = 1;
					mappaContiEconomiciFigliPerVisualizzazione(
							contoEconomicoItemList, contoEconomicoDTOarray, level,
							isCopiaPresente,isRimodulazione,mapImporto,null,isModifica,
							 dataFineIstruttoria,
							 inIstruttoria);
				}
			}
			
			if(ObjectUtil.isEmpty(contoEconomicoItemListPerImporto))
				contoEconomicoItemListPerImporto=contoEconomicoItemList;
			
	        importo = calcolaImportoPerVisualizzazione(contoEconomicoItemListPerImporto, isRimodulazione);
			mapImporto.put(IMPORTO, importo);
			
			mappaPrimeRighePerVisualizzazione(contoEconomicoRimodulazione, 
					contoEconomicoItemListPerImporto, 
					contoEconomico,
					mapImporto,
					isModifica);
			
			calcolaImportiMacroVociPerVisualizzazione(contoEconomicoItemListPerImporto, isRimodulazione);		
						
		} else {

			logger.info("mappaContoEconomicoPerVisualizzazione per Proposta Rimodulazione");
			
			Double  importo= new Double(0);
			Map<String, Double> mapImporto=new HashMap<String, Double>();
			mapImporto.put(IMPORTO,importo);
			if(ObjectUtil.isEmpty(contoEconomicoItemListPerImporto)) {
				ArrayList<ContoEconomicoItem> contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();
				if (!ObjectUtil.isNull(contoEconomicoRimodulazione)) {
					contoEconomicoItemListPerImporto = getContoEconomicoItemList(
							contoEconomicoItemList,
							contoEconomicoRimodulazione,
							isCopiaPresente,
							isModifica,
							isRimodulazione,
							dataFineIstruttoria,
							inIstruttoria,
							mapImporto
							);
				}
			}

	        importo = calcolaImportoPerVisualizzazione(contoEconomicoItemListPerImporto, isRimodulazione);
			mapImporto.put(IMPORTO, importo);
			mappaPrimeRighePerVisualizzazione(contoEconomicoRimodulazione, 
					contoEconomicoItemListPerImporto, 
					contoEconomico,	
					mapImporto,
					isModifica);
			
			calcolaImportiMacroVociPerVisualizzazione(contoEconomicoItemListPerImporto, false);
			
		}
		return contoEconomicoItemListPerImporto;
	}
	
	private ArrayList<ContoEconomicoItem> getContoEconomicoItemList(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			boolean isCopiaPresente,
			boolean isModifica,
			boolean isRimodulazione,
			Date dataFineIstruttoria,
			Boolean inIstruttoria,
			Map<String, Double> mapImporto
			) {
		ContoEconomicoRimodulazioneDTO contoEconomicoDTOarray[] = contoEconomicoRimodulazione.getFigli();
	
		if (!ObjectUtil.isEmpty(contoEconomicoDTOarray)) {
			int level = 1;
			mappaContiEconomiciFigliPerVisualizzazione(
				contoEconomicoItemList, contoEconomicoDTOarray, level,
				isCopiaPresente,isRimodulazione,
				mapImporto,null,isModifica,dataFineIstruttoria,inIstruttoria);
		}
		return contoEconomicoItemList;
	}

	private void mappaContiEconomiciFigliPerVisualizzazione(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			ContoEconomicoRimodulazioneDTO[] contoEconomicoDTOarray, 
			int level,
			boolean isCopiaPresente,
			boolean isRimodulazione,
			Map <String,Double>mapImporto,
			String idPadre,
			boolean isModifica,
			Date dataFineIstruttoria,
			Boolean inIstruttoria) {


		boolean copySpesaAmmessa=false;
		boolean copySpesaAmmessaUltima=false;
		boolean copySpesaAmmessaInIstruttoria=false;
		boolean copyNuovaProposta=false;
		boolean copyUltimaProposta=false;
		boolean copyRichiestoInDomanda=false;
		if(isRimodulazione){
			if(isCopiaPresente){
				copySpesaAmmessa=true;
			}else if(dataFineIstruttoria !=null){
				copySpesaAmmessaUltima=true;
			}else if(inIstruttoria){
				copySpesaAmmessaInIstruttoria=true;
			}else copyRichiestoInDomanda=true;
		}else{
			if(isCopiaPresente){
				copyNuovaProposta=true;
			}else if(dataFineIstruttoria !=null){
				copySpesaAmmessaUltima=true;
			}else
				copyRichiestoInDomanda=true;
		}
		
		for (ContoEconomicoRimodulazioneDTO contoEconomicoDTO : contoEconomicoDTOarray) {
			ContoEconomicoItem contoEconomicoItem = new ContoEconomicoItem();
			BeanUtil.valueCopy(contoEconomicoDTO, contoEconomicoItem);
			contoEconomicoItem.setIdPadre(idPadre);
			
			// Alex: conversioni aggiunte per uniformare la formattazione di tutti gli importi.
			// BeanUtil.valueCopy() sopra crea stringhe del tipo 1119984.0,
			// mentre il codice altrove usa NumberUtil.getStringValueItalianFormat().
			contoEconomicoItem.setImportoRichiestoInDomanda(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoRichiestoInDomanda()));
			contoEconomicoItem.setImportoRichiestoUltimaProposta(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoRichiestoUltimaProposta()));
			contoEconomicoItem.setImportoSpesaAmmessaInDetermina(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoSpesaAmmessaInDetermina()));
			contoEconomicoItem.setImportoSpesaAmmessaUltima(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoSpesaAmmessaUltima()));
			contoEconomicoItem.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoSpesaAmmessaRimodulazione()));
			contoEconomicoItem.setImportoSpesaRendicontata(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoSpesaRendicontata()));
			contoEconomicoItem.setImportoSpesaQuietanziata(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoSpesaQuietanziata()));
			contoEconomicoItem.setImportoSpesaValidataTotale(NumberUtil.getStringValueItalianFormat(contoEconomicoDTO.getImportoSpesaValidataTotale()));
				
			// VALORI DI DEFAULT
			Double zero=new Double(0);
			Double spesaAmmessa=contoEconomicoDTO.getImportoSpesaAmmessaRimodulazione()!=null?contoEconomicoDTO.getImportoSpesaAmmessaRimodulazione():zero;
			Double spesaAmmessaUltima=contoEconomicoDTO.getImportoSpesaAmmessaUltima()!=null?contoEconomicoDTO.getImportoSpesaAmmessaUltima():zero;
			Double spesaAmmessaInIstruttoria=contoEconomicoDTO.getImportoSpesaAmmessaInDetermina()!=null?contoEconomicoDTO.getImportoSpesaAmmessaInDetermina():zero;
			Double nuovaProposta=contoEconomicoDTO.getImportoRichiestoNuovaProposta()!=null?contoEconomicoDTO.getImportoRichiestoNuovaProposta():zero;
			Double ultimaProposta=contoEconomicoDTO.getImportoRichiestoUltimaProposta()!=null?contoEconomicoDTO.getImportoRichiestoUltimaProposta():zero;
			Double importoRichiestoInDomanda= contoEconomicoDTO.getImportoRichiestoInDomanda()!=null?contoEconomicoDTO.getImportoRichiestoInDomanda():zero;
				
			Double value=null;
				
			if(copySpesaAmmessa){
				value = spesaAmmessa;
			}else if(copySpesaAmmessaUltima){
				value = spesaAmmessaUltima;
			}else if(copySpesaAmmessaInIstruttoria){
				value = spesaAmmessaInIstruttoria;
			}else if(copyNuovaProposta){
				value = nuovaProposta;
			}else if(copyUltimaProposta){
				value = ultimaProposta;
			}else if(copyRichiestoInDomanda){
				value = importoRichiestoInDomanda;
			}
			else {
				value=zero;
			}
				
			if(isRimodulazione){
				contoEconomicoItem.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValueItalianFormat(value));
			}else{
				contoEconomicoItem.setImportoRichiestoNuovaProposta(NumberUtil.getStringValueItalianFormat(value));
			}
			mapImporto.put(IMPORTO,value);
				
			// VALORI DI DEFAULT				
			contoEconomicoItem.setLevel(level);
			contoEconomicoItem.setId(contoEconomicoDTO.getIdVoce().toString());

			if (!ObjectUtil.isEmpty(contoEconomicoDTO.getFigli())) {
				contoEconomicoItem.setHaFigli(true);
				contoEconomicoItem.setModificabile(false);

				contoEconomicoItemList.add(contoEconomicoItem);
				ContoEconomicoRimodulazioneDTO contoEconomicoFigli[] = contoEconomicoDTO.getFigli();
				
				String idVocePadre=contoEconomicoDTO.getIdVoce()!=null?contoEconomicoDTO.getIdVoce().toString():null;
				
				mappaContiEconomiciFigliPerVisualizzazione(
						contoEconomicoItemList, contoEconomicoFigli,
						level + 1, isCopiaPresente,isRimodulazione,mapImporto,idVocePadre,isModifica,
						dataFineIstruttoria,inIstruttoria);
				// non modificabile
			} else {
				// modificabile
				contoEconomicoItem.setModificabile(true);
				contoEconomicoItemList.add(contoEconomicoItem);
			}
		}
	}
	
	public Double calcolaImportoPerVisualizzazione(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			boolean isRimodulazione) {

		Double importo = new Double(0);
		for (ContoEconomicoItem contoEconomicoItem : contoEconomicoItemList) {
			//logger.info(""+contoEconomicoItem.getLabel()+" idRigo:"+contoEconomicoItem.getIdRigoContoEconomico()+" "+contoEconomicoItem.getImportoRichiestoNuovaProposta());
			if (contoEconomicoItem.getModificabile()) {
				importo = getImporto(isRimodulazione, contoEconomicoItem,importo);
			}
			//logger.info("ContoEconomicoManager::calcolaImportoPerVisualizzazione() - spesaAmmessaInRimodulazione = "+ contoEconomicoItem.getImportoSpesaAmmessaRimodulazione()); 
		}
		//logger.info("calcolaImportoPerVisualizzazione  importo: "+importo);
		
		return importo;
	}
	
	private Double getImporto(boolean isRimodulazione,
			ContoEconomicoItem contoEconomicoItem, Double importo) {
	
		Double value;
		if (isRimodulazione) {			
			value=NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaAmmessaRimodulazione());			
			if(value!=null && value>0){
				if(importo==null)importo=0d;
				importo = NumberUtil.sum(importo, value);
			}
		} else {	
			if (contoEconomicoItem.getIdVoce()!=null) {
				value=NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoRichiestoNuovaProposta());
				logger.info("label" +contoEconomicoItem.getLabel()
						+" , id: " +contoEconomicoItem.getId()
					    +" , idRigo: " +contoEconomicoItem.getIdRigoContoEconomico()
					    +" , idVoce: " +contoEconomicoItem.getIdVoce()
						+" , contoEconomicoItem.getImportoRichiestoNuovaProposta()"+contoEconomicoItem.getImportoRichiestoNuovaProposta()
						+" NumberUtil.toNullableDoubleItalianFormat: "+value+"\n");
				if (value!=null && value>0) {
					if(importo==null)importo=0d;
					importo = NumberUtil.sum(importo,value);
				}
			}
		}
	
		return importo;
	}
	
	protected  void calcolaImportiMacroVociPerVisualizzazione(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			boolean isRimodulazione) {
		logger.begin();
		Map <String,Double>mapMacroVoci=new HashMap<String,Double>();
		for (ContoEconomicoItem contoEconomicoItem : contoEconomicoItemList) {
			logger.info(""+contoEconomicoItem.getLabel()+" idRigo:"+contoEconomicoItem.getIdRigoContoEconomico()+
					" "+contoEconomicoItem.getImportoRichiestoNuovaProposta());
			String idPadre=contoEconomicoItem.getIdPadre();
			Long idVoce=contoEconomicoItem.getIdVoce();
			Double importo=mapMacroVoci.get(idPadre);
		
			importo = getImporto(isRimodulazione, contoEconomicoItem,importo);
				
			if (idPadre!=null && importo!=null) {
				mapMacroVoci.put(idPadre,importo);
			} else if (!contoEconomicoItem.getHaFigli() && importo!=null) {
				mapMacroVoci.put(idVoce.toString(),importo);
			}
		}
		logger.info("\n\n\n\n");
		for (ContoEconomicoItem contoEconomicoItem : contoEconomicoItemList) {
			if (contoEconomicoItem.getIdVoce()!=null) {
				logger.info("contoEconomicoItem.getIdVoce().toString()="+contoEconomicoItem.getIdVoce().toString());
				if (mapMacroVoci.containsKey(contoEconomicoItem.getIdVoce().toString())) {
					Double importo=mapMacroVoci.get(contoEconomicoItem.getIdVoce().toString());
					if (isRimodulazione) {
						contoEconomicoItem.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValueItalianFormat(importo));
					}else{
						contoEconomicoItem.setImportoRichiestoNuovaProposta(NumberUtil.getStringValueItalianFormat(importo));
					}
				}
			}
		}

		logger.end();
	}
	
	protected  void mappaPrimeRighePerVisualizzazione(
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			ContoEconomico contoEconomico,
			Map <String,Double>mapImporto,
			boolean isModifica) {
		logger.begin();
		
		// VN: Ribaltamento d'asta. Aggiungo come parametro il flag della modifica
		 
		setRigaConDate(contoEconomicoItemList, contoEconomico, isModifica);
		
		int indice=1;
		if (isModifica) {
			setRigaRibaltamento(contoEconomicoItemList,indice);
			indice=2;
		} else if (!ObjectUtil.isEmpty(contoEconomicoItemList) 
				 && contoEconomicoItemList.get(indice).getLabel()!=null
				 && contoEconomicoItemList.get(indice).getLabel().equals(" ") ){
			// se non sono in modifica ma la seconda riga ha la label vuota era la 
			// riga dei ribaltamenti che va eliminata
			contoEconomicoItemList.remove(indice);
		}
		
		ContoEconomicoItem contoEconomicoItemSecondaRiga = new ContoEconomicoItem();

		contoEconomicoItemSecondaRiga.setImportoRichiestoInDomanda(
				NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione.getImportoRichiestoInDomanda()));
		
		contoEconomicoItemSecondaRiga.setImportoRichiestoUltimaProposta(
				NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione.getImportoRichiestoUltimaProposta()));
		
		Double importo=mapImporto.get(IMPORTO);
		
		if (importo==null) 
			importo=0D;

		contoEconomicoItemSecondaRiga.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValueItalianFormat(importo));
		
		contoEconomicoItemSecondaRiga.setImportoRichiestoNuovaProposta(NumberUtil.getStringValueItalianFormat(importo));
		
		contoEconomicoItemSecondaRiga
				.setImportoSpesaAmmessaInDetermina(NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione
						.getImportoSpesaAmmessaInDetermina()));
		contoEconomicoItemSecondaRiga
				.setImportoSpesaAmmessaUltima(NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione
						.getImportoSpesaAmmessaUltima()));
		contoEconomicoItemSecondaRiga
				.setImportoSpesaRendicontata(NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione
						.getImportoSpesaRendicontata()));
		contoEconomicoItemSecondaRiga
				.setImportoSpesaQuietanziata(NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione
						.getImportoSpesaQuietanziata()));
		contoEconomicoItemSecondaRiga.setLabel("Conto Economico");
		contoEconomicoItemSecondaRiga
				.setImportoSpesaValidataTotale(NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione
						.getImportoSpesaValidataTotale()));
		contoEconomicoItemSecondaRiga
				.setPercSpesaQuietanziataSuAmmessa(NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione
						.getPercSpesaQuietanziataSuAmmessa()));
		contoEconomicoItemSecondaRiga
				.setPercSpesaValidataSuAmmessa(NumberUtil.getStringValueItalianFormat(contoEconomicoRimodulazione
						.getPercSpesaValidataSuAmmessa()));
		contoEconomicoItemSecondaRiga.setModificabile(false);
		contoEconomicoItemSecondaRiga.setHaFigli(true);
		contoEconomicoItemSecondaRiga.setId("0");
		contoEconomicoItemSecondaRiga.setLevel(0);
		
		if(!ObjectUtil.isEmpty(contoEconomicoItemList) 
				&& !ObjectUtil.isEmpty(contoEconomicoItemList.get(indice).getLabel())
				&& contoEconomicoItemList.get(indice).getLabel().equals("Conto Economico"))
			contoEconomicoItemList.set(indice, contoEconomicoItemSecondaRiga);
		else
			contoEconomicoItemList.add(indice, contoEconomicoItemSecondaRiga);

		logger.end();
	}
	
	private void setRigaConDate(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			ContoEconomico contoEconomico,
			boolean isModifica) {
		
		ContoEconomicoItem contoEconomicoItemPrimaRiga = new ContoEconomicoItem();	
		contoEconomicoItemPrimaRiga.setLabel("");
		contoEconomicoItemPrimaRiga.setDataPresentazioneDomanda(contoEconomico.getDataPresentazioneDomanda());
		contoEconomicoItemPrimaRiga.setDataUltimaProposta(contoEconomico.getDataUltimaProposta());
		contoEconomicoItemPrimaRiga.setDataUltimaRimodulazione(contoEconomico.getDataUltimaRimodulazione());
		contoEconomicoItemPrimaRiga.setDataFineIstruttoria(contoEconomico.getDataFineIstruttoria());
		
		// VN: Ribaltamento d'asta. Abilito o no il campo
		 
		contoEconomicoItemPrimaRiga.setModificabile(isModifica);
		
		if (!ObjectUtil.isEmpty(contoEconomicoItemList) 
				&& !ObjectUtil.isNull(contoEconomicoItemList.get(0).getLabel())
				&& contoEconomicoItemList.get(0).getLabel().equals(""))
			contoEconomicoItemList.set(0, contoEconomicoItemPrimaRiga);
		else
			contoEconomicoItemList.add(0, contoEconomicoItemPrimaRiga);	
	}
	
	public void setRigaRibaltamento(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,int indice) {
		ContoEconomicoItem contoEconomicoItem=new ContoEconomicoItem ();
		contoEconomicoItem.setLabel(" ");
		if(!ObjectUtil.isEmpty(contoEconomicoItemList) 
				&& !ObjectUtil.isNull(contoEconomicoItemList.get(indice).getLabel())
				&& contoEconomicoItemList.get(indice).getLabel().equals(" "))
			contoEconomicoItemList.set(indice, contoEconomicoItem);
		else
			contoEconomicoItemList.add(indice, contoEconomicoItem);
	}
	
	public BigDecimal calcolaPercentualeInPropostaRimodulazione (ArrayList<ContoEconomicoItem> listItem) {
		
		BigDecimal totaleNuovaProposta = new BigDecimal(0);
		BigDecimal totaleUltimoAmmesso = new BigDecimal(0);
		
		for (ContoEconomicoItem item : listItem) {				
			 // Cerco le micro voci di spesa o le macro voci di spesa senza figli.			 
			if (item.getIdVoce() != null && !item.getHaFigli()) {
				BigDecimal importoNuovaRichiesta = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoRichiestoNuovaProposta());
				if (importoNuovaRichiesta != null) {
					totaleNuovaProposta = NumberUtil.sum(totaleNuovaProposta, importoNuovaRichiesta);
				}
				BigDecimal importoTotaleUltimoAmmesso = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaUltima());
				if (importoTotaleUltimoAmmesso != null) {
					totaleUltimoAmmesso = NumberUtil.sum(totaleUltimoAmmesso, importoTotaleUltimoAmmesso);
				}
			}
		}
		
		BigDecimal differenza = NumberUtil.subtract(totaleNuovaProposta, totaleUltimoAmmesso);
		
		// Calcolo la percentuale (nuovaProposta - ultimoAmmesso)/ultimoAmmesso
		BigDecimal percentuale = NumberUtil.divide(NumberUtil.multiply(differenza,new BigDecimal(100)),totaleUltimoAmmesso);
		return percentuale;
	}
	
	public BigDecimal calcolaDifferenzaInPropostaRimodulazione (ArrayList<ContoEconomicoItem> listItem) {
		
		BigDecimal totaleNuovaProposta = new BigDecimal(0);
		BigDecimal totaleUltimoAmmesso = new BigDecimal(0);
		
		for (ContoEconomicoItem item : listItem) {				
			// Cerco le micro voci di spesa o le macro voci di spesa senza figli.			 
			if (item.getIdVoce() != null && !item.getHaFigli()) {
				BigDecimal importoNuovaRichiesta = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoRichiestoNuovaProposta());
				if (importoNuovaRichiesta != null) {
					totaleNuovaProposta = NumberUtil.sum(totaleNuovaProposta, importoNuovaRichiesta);
				}
				BigDecimal importoTotaleUltimoAmmesso = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaUltima());
				if (importoTotaleUltimoAmmesso != null) {
					totaleUltimoAmmesso = NumberUtil.sum(totaleUltimoAmmesso, importoTotaleUltimoAmmesso);
				}
			}
		}
		
		BigDecimal differenza = NumberUtil.subtract(totaleNuovaProposta, totaleUltimoAmmesso);
		return differenza;
	}
	
	public void impostaDifferenzaInPropostaRimodulazione (ContoEconomicoItem rigaRibaltaDati, BigDecimal differenza) {
		rigaRibaltaDati.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(differenza));
	}
	
	public void impostaPercentualeInPropostaRimodulazione (ContoEconomicoItem rigaPercentuale, BigDecimal percentuale) {
		rigaPercentuale.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(percentuale));
	}
	
	public  Set<String>  controllaImportiPerProposta(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			Set<String> messaggiErrore) {
		
		ContoEconomicoRimodulazioneDTO contoEconomicoDTOFigli[] = contoEconomicoRimodulazione.getFigli();
		Set<String> messaggiWarnings=new HashSet <String>();
		
		Map<String,Double> totaleNuovaProposta=new HashMap<String, Double>();
		totaleNuovaProposta.put("totaleNuovaProposta", new Double(0));
		Map<String,Double> totaleUltimaSpesaAmmessa=new HashMap<String, Double>();
		totaleUltimaSpesaAmmessa.put("totaleUltimaSpesaAmmessa", new Double(0));
		
		for (ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO : contoEconomicoDTOFigli) {
		
			if(contoEconomicoRimodulazioneDTO.getFigli()!=null){
				for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
					controllaImportiItemPerProposta(index,contoEconomicoFiglioDTO,messaggiErrore,
							messaggiWarnings,
							totaleNuovaProposta,
							totaleUltimaSpesaAmmessa);
				}
			} else {
				controllaImportiItemPerProposta(index,contoEconomicoRimodulazioneDTO,
					messaggiErrore,
					messaggiWarnings,
					totaleNuovaProposta,
					totaleUltimaSpesaAmmessa);
			}
		}
					
		if(ObjectUtil.isEmpty(messaggiErrore)){
			if(NumberUtil.compare(totaleNuovaProposta.get("totaleNuovaProposta"), 
					totaleUltimaSpesaAmmessa.get("totaleUltimaSpesaAmmessa"))<0 ) {
				messaggiWarnings.add((MessageKey.TOTALE_NUOVA_PROPOSTA_INFERIORE_ULTIMA_SPESA_AMMESSA));
			} else if(NumberUtil.compare(totaleNuovaProposta.get("totaleNuovaProposta"), 
					totaleUltimaSpesaAmmessa.get("totaleUltimaSpesaAmmessa"))>0 ){
				messaggiWarnings.add((MessageKey.TOTALE_NUOVA_PROPOSTA_SUPERIORE_ULTIMA_SPESA_AMMESSA));
			}
			return messaggiWarnings;
		}
		else{
			return messaggiErrore;
		}
	}
	
	private  void  controllaImportiItemPerProposta(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO,
			Collection<String> messaggiErrore,
			Collection<String> messaggiWarnings,
			Map<String,Double> totaleNuovaProposta,
			Map<String,Double> totaleUltimaSpesaAmmessa) {
		
		if(contoEconomicoRimodulazioneDTO.getFigli()!=null){
			for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
				controllaImportiItemPerProposta(index,contoEconomicoFiglioDTO,
						messaggiErrore,messaggiWarnings,
						totaleNuovaProposta,
						totaleUltimaSpesaAmmessa);
			}
		}	
		else{
			if (contoEconomicoRimodulazioneDTO.getIdVoce() != null) {
				
				ContoEconomicoItem contoEconomicoItem=(ContoEconomicoItem)index.get(contoEconomicoRimodulazioneDTO.getIdVoce());
				
				if(contoEconomicoItem!=null) {
					
					contoEconomicoItem.setCodiceErrore(null);
					
					String importoRichiestoNuovaProposta=contoEconomicoItem.getImportoRichiestoNuovaProposta();
					
					if(ObjectUtil.isEmpty(importoRichiestoNuovaProposta) ||
					   NumberUtil.toNullableDoubleItalianFormat(importoRichiestoNuovaProposta)==null) {
						contoEconomicoItem.setCodiceErrore("X");
						messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
					}else{
					
						Double richiestoNuovaProposta         = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoRichiestoNuovaProposta());
						Double importoSpesaRendicontata  	  = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaRendicontata());
						Double importoSpesaQuietanziata 	  = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaQuietanziata());
						Double importoSpesaValidataTotale	  = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaValidataTotale());
						Double importoSpesaAmmessaUltima 	  = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaAmmessaUltima());
						Double importoAmmessoDaBando 		  = contoEconomicoRimodulazioneDTO.getImportoAmmessoDaBando();
						Double importoRichiestoUltimaProposta = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoRichiestoUltimaProposta());
						
						
						Double totNuovaProposta=totaleNuovaProposta.get("totaleNuovaProposta");
						Double sommaNuovaProposta=NumberUtil.sum(totNuovaProposta, richiestoNuovaProposta);
						if(sommaNuovaProposta!=null){
							totNuovaProposta=sommaNuovaProposta;
							totaleNuovaProposta.put("totaleNuovaProposta",totNuovaProposta);
						}
						
						Double totUltimaSpesaAmmessa=totaleUltimaSpesaAmmessa.get("totaleUltimaSpesaAmmessa");
						Double sommaUltimaSpesaAmmessa=NumberUtil.sum(totUltimaSpesaAmmessa, importoSpesaAmmessaUltima);
						if(sommaUltimaSpesaAmmessa!=null){
							totUltimaSpesaAmmessa=sommaUltimaSpesaAmmessa;
							totaleUltimaSpesaAmmessa.put("totaleUltimaSpesaAmmessa",totUltimaSpesaAmmessa);
						}
						
						if(richiestoNuovaProposta!=null){
							if(richiestoNuovaProposta.doubleValue()<0 ){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
							}
							else if(richiestoNuovaProposta.doubleValue()>MAX_IMPORTO_AMMMESSO ){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
							} 
							else if(importoAmmessoDaBando!=null &&
									 NumberUtil.compare(richiestoNuovaProposta, importoAmmessoDaBando)>0
									){
								contoEconomicoItem.setCodiceErrore("e");
								messaggiWarnings.add((MessageKey.IMPORTO_SUPERIORE_AMMISSIBILE));
							}
							else if(NumberUtil.compare(richiestoNuovaProposta, importoSpesaValidataTotale)<0 &&
									importoSpesaValidataTotale!=null){
								contoEconomicoItem.setCodiceErrore("c");
								messaggiWarnings.add((MessageKey.IMPORTO_INFERIORE_VALIDATO));	
							}else if(NumberUtil.compare(richiestoNuovaProposta, importoSpesaQuietanziata)<0 &&
									importoSpesaQuietanziata!=null){
								contoEconomicoItem.setCodiceErrore("b");
								messaggiWarnings.add((MessageKey.IMPORTO_INFERIORE_QUIETANZIATO));	
							}else if(NumberUtil.compare(richiestoNuovaProposta, importoSpesaRendicontata)<0 &&
									importoSpesaRendicontata!=null){
								contoEconomicoItem.setCodiceErrore("a");
								messaggiWarnings.add((MessageKey.IMPORTO_INFERIORE_RENDICONTATO));
							}
							else if(NumberUtil.compare(richiestoNuovaProposta, importoSpesaAmmessaUltima)>0 &&
									importoSpesaAmmessaUltima!=null){
								contoEconomicoItem.setCodiceErrore("m");
								messaggiWarnings.add((MessageKey.IMPORTO_SUPERIORE_ULTIMA_SPESA_AMMESSA));
							}
							else if(NumberUtil.compare(richiestoNuovaProposta, importoSpesaAmmessaUltima)<0 &&
									importoSpesaAmmessaUltima!=null){
								contoEconomicoItem.setCodiceErrore("n");
								messaggiWarnings.add((MessageKey.IMPORTO_INFERIORE_ULTIMA_SPESA_AMMESSA));
							}
							else if(NumberUtil.compare(richiestoNuovaProposta, importoRichiestoUltimaProposta)>0 &&
									importoRichiestoUltimaProposta!=null){
								contoEconomicoItem.setCodiceErrore("h");
								messaggiWarnings.add((MessageKey.IMPORTO_SUPERIORE_ULTIMA_PROPOSTA));
							}
							else if(NumberUtil.compare(richiestoNuovaProposta, importoRichiestoUltimaProposta)<0 &&
									importoRichiestoUltimaProposta!=null){
								contoEconomicoItem.setCodiceErrore("d");
								messaggiWarnings.add((MessageKey.IMPORTO_INFERIORE_ULTIMA_PROPOSTA));
							}	
						}else{
							contoEconomicoItem.setCodiceErrore("X");
							//messaggiErrore.add(messageManager.getMessage(MessageKey.IMPORTI_NON_VALIDI));
							messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
						}
					}
			    }
			}
		}
	
	}
	
	public void removeCodiceWarning(List <ContoEconomicoItem> list) {
		//	        ContoEconomicoItem contoEconomicoItem= (ContoEconomicoItem)pairs.getValue();
		//	        contoEconomicoItem.setCodiceErrore(null);
		for (Iterator <ContoEconomicoItem>iterator = list.iterator(); iterator.hasNext();) {
			ContoEconomicoItem contoEconomicoItem = (ContoEconomicoItem) iterator
					.next();
			if(contoEconomicoItem.getCodiceErrore()!=null && !
					contoEconomicoItem.getCodiceErrore().equalsIgnoreCase("X"))
			contoEconomicoItem.setCodiceErrore(null);
		}
	}
	
	public  void  mappaContoEconomicoPerSalvataggio(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione) {
		long begin = System.currentTimeMillis();
		
		ContoEconomicoRimodulazioneDTO contoEconomicoDTOFigli[] = contoEconomicoRimodulazione.getFigli();
		if (!ObjectUtil.isEmpty(contoEconomicoDTOFigli)) {
			for (ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO : contoEconomicoDTOFigli) {
				mappaContoEconomicoPerSalvataggio(index, contoEconomicoRimodulazioneDTO);
				logger.info("[ContoEconomicoManager :: mappaContoEconomicPerSalvataggio] - Importo spesa ammessa ultima figlio: "+ contoEconomicoRimodulazioneDTO.getImportoSpesaAmmessaUltima());
				logger.info("[ContoEconomicoManager :: mappaContoEconomicPerSalvataggio] - Importo spesa ammessa rimodulazione figlio: "+ contoEconomicoRimodulazioneDTO.getImportoSpesaAmmessaRimodulazione());
			}
		}
		if (contoEconomicoRimodulazione.getIdVoce() != null) {
			ContoEconomicoItem item= index.get(contoEconomicoRimodulazione.getIdVoce());
			BeanUtil.valueCopy(item, contoEconomicoRimodulazione);
		}
		long end = System.currentTimeMillis() - begin;
		logger.info("ContoEconomico rimappato per SALVATAGGIO da DTO Presentation a DTO server   in "+ end + " millisec\n\n\n");
		logger.info("=============================================================================");
		logger.shallowDump("[ContoEconomicoManager :: mappaContoEconomicPerSalvataggio] - contoEconomicoRimodulazione: "+contoEconomicoRimodulazione, "info");
		logger.info("=============================================================================");
	}
	
	/*
	
	private BeanUtil beanUtil;
	public  void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}
	public BeanUtil getBeanUtil() {
		return beanUtil;
	}


	

	

	
	
	
	
	
	
	
	
	
	private  void  controllaImportiItemPerRimodulazione(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO,
			Collection<String> messaggiErrore,
			Collection<String> messaggiWarnings,
			Map<String,Double> totaleSpesaAmmessa,
			Map<String,Double> totaleUltimaSpesaAmmessa) {
		
		if(contoEconomicoRimodulazioneDTO.getFigli()!=null){
			for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
				controllaImportiItemPerRimodulazione(index,contoEconomicoFiglioDTO,
						messaggiErrore,messaggiWarnings,totaleSpesaAmmessa,totaleUltimaSpesaAmmessa);
			}
		}	
		else{
			if (contoEconomicoRimodulazioneDTO.getIdVoce() != null) {
				
				ContoEconomicoItem contoEconomicoItem=(ContoEconomicoItem)index.get(contoEconomicoRimodulazioneDTO.getIdVoce());
				
				if(contoEconomicoItem!=null){
					String importoSpesaAmmessaRimodulazione=contoEconomicoItem.getImportoSpesaAmmessaRimodulazione();
					
					contoEconomicoItem.setCodiceErrore(null);
					
					//importoSpesaAmmessaRimodulazione=removeWarning(importoSpesaAmmessaRimodulazione);
					
					if(ObjectUtil.isEmpty(importoSpesaAmmessaRimodulazione)
								||
								NumberUtil.toNullableDoubleItalianFormat(importoSpesaAmmessaRimodulazione)==null
								){
						contoEconomicoItem.setCodiceErrore("X");
						messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
					}
					else{
						Double spesaAmmessaRimodulazione      = NumberUtil.toNullableDoubleItalianFormat(importoSpesaAmmessaRimodulazione);
						Double importoSpesaRendicontata   	  = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaRendicontata());
						Double importoSpesaQuietanziata   	  = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaQuietanziata());
						Double importoSpesaValidataTotale     = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoSpesaValidataTotale());
						Double importoRichiestoUltimaProposta = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoRichiestoUltimaProposta());
						Double importoAmmessoDaBando          = contoEconomicoRimodulazioneDTO.getImportoAmmessoDaBando();
						Double ultimaSpesaAmmessa             = contoEconomicoRimodulazioneDTO.getImportoSpesaAmmessaUltima();
						
						Double totSpesaAmmessa=totaleSpesaAmmessa.get("totaleSpesaAmmessa");
						Double sommaSpesaAmmessa=NumberUtil.sum(totSpesaAmmessa, spesaAmmessaRimodulazione);
						if(sommaSpesaAmmessa!=null){
							totSpesaAmmessa=sommaSpesaAmmessa;
							totaleSpesaAmmessa.put("totaleSpesaAmmessa",totSpesaAmmessa);
						}
						
						Double totUltimaSpesaAmmessa=totaleUltimaSpesaAmmessa.get("totaleUltimaSpesaAmmessa");
						Double sommaUltimaSpesaAmmessa=NumberUtil.sum(totUltimaSpesaAmmessa, ultimaSpesaAmmessa);
						if(sommaUltimaSpesaAmmessa!=null){
							totUltimaSpesaAmmessa=sommaUltimaSpesaAmmessa;
							totaleUltimaSpesaAmmessa.put("totaleUltimaSpesaAmmessa",totUltimaSpesaAmmessa);
						}
						
						if(spesaAmmessaRimodulazione!=null){
							if(spesaAmmessaRimodulazione.doubleValue()<0){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
							}
							else if(spesaAmmessaRimodulazione.doubleValue()> MAX_IMPORTO_AMMMESSO ){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
							}
							else if(importoAmmessoDaBando!=null &&
									 NumberUtil.compare(spesaAmmessaRimodulazione, importoAmmessoDaBando)>0){
								contoEconomicoItem.setCodiceErrore("e");
								messaggiWarnings.add(MessageKey.IMPORTO_SUPERIORE_AMMISSIBILE);
							}
							else if(importoSpesaValidataTotale != null &&
									NumberUtil.compare(spesaAmmessaRimodulazione, importoSpesaValidataTotale)<0){
								contoEconomicoItem.setCodiceErrore("c");
								messaggiWarnings.add(MessageKey.IMPORTO_INFERIORE_VALIDATO);
							}
							else if(importoSpesaQuietanziata != null &&
									NumberUtil.compare(spesaAmmessaRimodulazione, importoSpesaQuietanziata)<0){
								contoEconomicoItem.setCodiceErrore("b");
								messaggiWarnings.add(MessageKey.IMPORTO_INFERIORE_QUIETANZIATO);
							}
							else if(importoSpesaRendicontata!=null &&
									NumberUtil.compare(spesaAmmessaRimodulazione, importoSpesaRendicontata)<0){
								contoEconomicoItem.setCodiceErrore("a");
								messaggiWarnings.add(MessageKey.IMPORTO_INFERIORE_RENDICONTATO);
							}
							
							else if(ultimaSpesaAmmessa!=null && 
									 NumberUtil.compare(spesaAmmessaRimodulazione, ultimaSpesaAmmessa)>0){
								contoEconomicoItem.setCodiceErrore("m");
								messaggiWarnings.add(MessageKey.IMPORTO_SUPERIORE_ULTIMA_SPESA_AMMESSA);
							}
							else if(ultimaSpesaAmmessa!=null && 
									 NumberUtil.compare(spesaAmmessaRimodulazione, ultimaSpesaAmmessa)<0){
								contoEconomicoItem.setCodiceErrore("n");
								messaggiWarnings.add(MessageKey.IMPORTO_INFERIORE_ULTIMA_SPESA_AMMESSA);
							}
							else if(importoRichiestoUltimaProposta!=null && 
									 NumberUtil.compare(spesaAmmessaRimodulazione, importoRichiestoUltimaProposta)>0){
								contoEconomicoItem.setCodiceErrore("h");
								messaggiWarnings.add(MessageKey.IMPORTO_SUPERIORE_ULTIMA_PROPOSTA);
							}
							else if(importoRichiestoUltimaProposta!=null && 
									 NumberUtil.compare(spesaAmmessaRimodulazione, importoRichiestoUltimaProposta)<0){
								contoEconomicoItem.setCodiceErrore("d");
								messaggiWarnings.add(MessageKey.IMPORTO_INFERIORE_ULTIMA_PROPOSTA);
							} 
							
						}else{
							contoEconomicoItem.setCodiceErrore("X");
							messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
						}
				    }
				}
			}
		}
	}
	
	
	
	

	

	
	
	
	
	
	public  Set<String>  controllaImportiPerRimodulazione(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			Set<String> messaggiErrore) {
		
		ContoEconomicoRimodulazioneDTO contoEconomicoDTOFigli[] = contoEconomicoRimodulazione.getFigli();
		
		// Il totale della spesa ammessa in rimodulazione � inferiore rispetto al totale dell'ultima spesa ammessa
		
		Map<String,Double> totaleSpesaAmmessa=new HashMap<String, Double>();
		totaleSpesaAmmessa.put("totaleSpesaAmmessa", new Double(0));
		Map<String,Double> totaleUltimaSpesaAmmessa=new HashMap<String, Double>();
		totaleUltimaSpesaAmmessa.put("totaleUltimaSpesaAmmessa", new Double(0));
		
		Set<String> messaggiWarnings=new HashSet <String>();
		
		for (ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO : contoEconomicoDTOFigli) {
		
			if(contoEconomicoRimodulazioneDTO.getFigli()!=null){
				for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
					controllaImportiItemPerRimodulazione(index,
							contoEconomicoFiglioDTO,messaggiErrore,
							messaggiWarnings,
							totaleSpesaAmmessa,totaleUltimaSpesaAmmessa);
				}
			}
			else{
				controllaImportiItemPerRimodulazione(index,
						contoEconomicoRimodulazioneDTO,messaggiErrore,
						messaggiWarnings,
						totaleSpesaAmmessa,totaleUltimaSpesaAmmessa);
			}
		}
		
		if(ObjectUtil.isEmpty(messaggiErrore)){
			if(NumberUtil.compare(totaleSpesaAmmessa.get("totaleSpesaAmmessa"), totaleUltimaSpesaAmmessa.get("totaleUltimaSpesaAmmessa"))<0 ){
			
				messaggiWarnings.add((MessageKey.TOTALE_RIMODULAZIONE_INFERIORE_ULTIMA_SPESA_AMMESSA));
			}
			else if(NumberUtil.compare(totaleSpesaAmmessa.get("totaleSpesaAmmessa"), totaleUltimaSpesaAmmessa.get("totaleUltimaSpesaAmmessa"))>0 ){
				messaggiWarnings.add((MessageKey.TOTALE_RIMODULAZIONE_SUPERIORE_ULTIMA_SPESA_AMMESSA));
			}	
			return messaggiWarnings;
		}else{
			return messaggiErrore;
		}
		
	}
	
	
	




	

	

	
	


	


		
		
		
	
		

		
		
		private MessageManager messageManager;

		public void setMessageManager(MessageManager messageManager) {
			this.messageManager = messageManager;
		}

		public MessageManager getMessageManager() {
			return messageManager;
		}
		
		

		

		
		

	

	


	



	
	
	
	
	
		
	
	
	public static String toItalianFormat(String val){
		String importo= NumberUtil.getItalianStringValue(val); 
		return importo;
	}	
	
	
	
	public void applicaPercentualeInPropostaRimodulazione(ArrayList<ContoEconomicoItem> listItem, BigDecimal percentuale) {
		ContoEconomicoItem rigaRibaltaDati = listItem.get(1);
		ContoEconomicoItem rigaContoEconomico = listItem.get(2);
		BigDecimal totaleNuovaProposta = new BigDecimal(0);
		String idPadre = null;
		ContoEconomicoItem itemPadre = null;
		BigDecimal importoTotaleNuovaPropostaPadre = new BigDecimal(0);
		for (ContoEconomicoItem item : listItem) {
			
			//Cerco le micro voci di spesa o le macro voci di spesa senza figli.
			
			// VN: Fix PBandi-1500. La percentuale si applica sull' ultima spesa ammessa.
			
			if (item.getIdVoce() != null && !item.getHaFigli()) {
				//BigDecimal importoNuovaRichiesta = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoRichiestoNuovaProposta());
				BigDecimal importoNuovaRichiesta = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaUltima());
				BigDecimal importoPercentuale = NumberUtil.divide(NumberUtil.multiply(importoNuovaRichiesta, percentuale), new BigDecimal(100));
				BigDecimal totale = NumberUtil.sum(importoNuovaRichiesta, importoPercentuale);
				if (totale != null) {
					//logger.info("\n4 importo: "+NumberUtil.getStringValue(totale)+"\n");
					item.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(totale));
					totaleNuovaProposta = NumberUtil.sum(totaleNuovaProposta, totale);
					if (itemPadre != null) {
						if (item.getIdPadre() != null && item.getIdPadre().equals(idPadre) ) {
							importoTotaleNuovaPropostaPadre = NumberUtil.sum(importoTotaleNuovaPropostaPadre, totale);
						} 
					}
				} else {
					item.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(new BigDecimal(0)));
				}
				
			}
			
			// Gestione delle macro voci con figli
			 
			if (item.getIdVoce() != null && item.getHaFigli()) {
				if (itemPadre != null) {
				//	logger.info("\n5 importo: "+NumberUtil.getStringValue(importoTotaleNuovaPropostaPadre)+"\n");
					itemPadre.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(importoTotaleNuovaPropostaPadre));
					importoTotaleNuovaPropostaPadre = new BigDecimal(0);
				}
				idPadre = NumberUtil.getStringValue(item.getIdVoce());
				itemPadre = item;
			}
		}
		
		 // Aggiorno il totale dell' ultima macro voce 
		
		if (itemPadre != null) {
			//logger.info("\n6 importo: "+NumberUtil.getStringValue(importoTotaleNuovaPropostaPadre)+"\n");
			itemPadre.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(importoTotaleNuovaPropostaPadre));
		}
		
		
		// Aggiorno il totale nuova proposta
		
		//logger.info("\n7 importo: "+NumberUtil.getStringValue(totaleNuovaProposta)+"\n");
		rigaContoEconomico.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(totaleNuovaProposta));
		
		
		
		// Aggiorno la differenza tra il totale nuova proposta ed il totale ultima spesa ammessa
		
		BigDecimal importoTotaleAmmesso = NumberUtil.toBigDecimalFromItalianFormat(rigaContoEconomico.getImportoSpesaAmmessaUltima());
		BigDecimal differenza = NumberUtil.subtract(totaleNuovaProposta, importoTotaleAmmesso);
		rigaRibaltaDati.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(differenza));
		//logger.info("\n8 importo: "+NumberUtil.getStringValue(differenza)+"\n");
	
	}
	
	
	public BigDecimal calcolaPercentualeInPropostaRimodulazione (ArrayList<ContoEconomicoItem> listItem) {
		BigDecimal totaleNuovaProposta = new BigDecimal(0);
		BigDecimal totaleUltimoAmmesso = new BigDecimal(0);
		for (ContoEconomicoItem item : listItem) {	
			
			 // Cerco le micro voci di spesa o le macro voci di spesa senza figli.
			 
			if (item.getIdVoce() != null && !item.getHaFigli()) {
				BigDecimal importoNuovaRichiesta = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoRichiestoNuovaProposta());
				if (importoNuovaRichiesta != null) {
					totaleNuovaProposta = NumberUtil.sum(totaleNuovaProposta, importoNuovaRichiesta);
				}
				BigDecimal importoTotaleUltimoAmmesso = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaUltima());
				if (importoTotaleUltimoAmmesso != null) {
					totaleUltimoAmmesso = NumberUtil.sum(totaleUltimoAmmesso, importoTotaleUltimoAmmesso);
				}
			}
		}
		
		
		BigDecimal differenza = NumberUtil.subtract(totaleNuovaProposta, totaleUltimoAmmesso);
		
		
		
		// Calcolo la percentuale (nuovaProposta - ultimoAmmesso)/ultimoAmmesso
		
		
		BigDecimal percentuale = NumberUtil.divide(NumberUtil.multiply(differenza,new BigDecimal(100)),totaleUltimoAmmesso);
		return percentuale;
	}
	
	

	
	
	
	
	
	
	public void applicaPercentualeInRimodulazione(ArrayList<ContoEconomicoItem> listItem, BigDecimal percentuale) {
		ContoEconomicoItem rigaRibaltaDati = listItem.get(1);
		ContoEconomicoItem rigaContoEconomico = listItem.get(2);
		BigDecimal totaleSpesaAmmessa = new BigDecimal(0);
		String idPadre = null;
		ContoEconomicoItem itemPadre = null;
		BigDecimal importoTotaleSpesaAmmessaPadre = new BigDecimal(0);
		for (ContoEconomicoItem item : listItem) {
			
		
			 * Cerco le micro voci di spesa o le macro voci di spesa senza figli.
			 
			 // VN. Fix PBandi-1500. La percentuale si applica sull' importo dell' ultima spesa ammessa.
			 
			if (item.getIdVoce() != null && !item.getHaFigli()) {
				//BigDecimal importoSpesaAmmessa = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaRimodulazione());
				BigDecimal importoSpesaAmmessa = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaUltima());
				BigDecimal importoPercentuale = NumberUtil.divide(NumberUtil.multiply(importoSpesaAmmessa, percentuale), new BigDecimal(100));
				BigDecimal totale = NumberUtil.sum(importoSpesaAmmessa, importoPercentuale);
				if (totale != null) {
					item.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(totale));
					totaleSpesaAmmessa = NumberUtil.sum(totaleSpesaAmmessa, totale);
					if (itemPadre != null) {
						if (item.getIdPadre() != null && item.getIdPadre().equals(idPadre) ) {
							importoTotaleSpesaAmmessaPadre = NumberUtil.sum(importoTotaleSpesaAmmessaPadre, totale);
						} 
					}
				} else {
					item.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(new BigDecimal(0)));
				}
				
			}
			
			// Gestione delle macro voci con figli
			
			if (item.getIdVoce() != null && item.getHaFigli()) {
				if (itemPadre != null) {
					itemPadre.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(importoTotaleSpesaAmmessaPadre));
					importoTotaleSpesaAmmessaPadre = new BigDecimal(0);
				}
				idPadre = NumberUtil.getStringValue(item.getIdVoce());
				itemPadre = item;
			}
		}
		
		// Aggiorno il totale dell' ultima macro voce 
		
		if (itemPadre != null) {
			itemPadre.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(importoTotaleSpesaAmmessaPadre));
		}
		
		
		// Aggiorno il totale nuova proposta
		 
		rigaContoEconomico.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(totaleSpesaAmmessa));
		
		
		
		// Aggiorno la differenza tra il totale nuova proposta ed il totale ultima spesa ammessa
		
		BigDecimal importoTotaleUltimaSpesaAmmessa = NumberUtil.toBigDecimalFromItalianFormat(rigaContoEconomico.getImportoSpesaAmmessaUltima());
		BigDecimal differenza = NumberUtil.subtract(totaleSpesaAmmessa, importoTotaleUltimaSpesaAmmessa);
		rigaRibaltaDati.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(differenza));
	
	}
	
	
	
	public BigDecimal calcolaPercentualeInRimodulazione (ArrayList<ContoEconomicoItem> listItem) {
		BigDecimal totaleSpesaAmmessa = new BigDecimal(0);
		BigDecimal totaleUltimaSpesaAmmessa = new BigDecimal(0);
		for (ContoEconomicoItem item : listItem) {	
			
			// Cerco le micro voci di spesa o le macro voci di spesa senza figli.
			
			if (item.getIdVoce() != null && !item.getHaFigli()) {
				BigDecimal importoSpesaAmmessa = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaRimodulazione());
				if (importoSpesaAmmessa != null) {
					totaleSpesaAmmessa = NumberUtil.sum(totaleSpesaAmmessa, importoSpesaAmmessa);
				}
				BigDecimal importoUltimaSpesaAmmessa = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaUltima());
				if (importoUltimaSpesaAmmessa != null) {
					totaleUltimaSpesaAmmessa = NumberUtil.sum(totaleUltimaSpesaAmmessa, importoUltimaSpesaAmmessa);
				}
			}
		}
		
		
		
		
		// Calcolo la differenza tra il totale nuova proposta ed il totale ultima spesa ammessa
		
		BigDecimal differenza = NumberUtil.subtract(totaleSpesaAmmessa, totaleUltimaSpesaAmmessa);
		
		
		// Calcolo la percentuale (totaleSpesaAmmessa - ultimoAmmesso)/ultimoAmmesso
		
		BigDecimal percentuale = NumberUtil.divide(NumberUtil.multiply(differenza,new BigDecimal(100)),totaleUltimaSpesaAmmessa);
		
		return percentuale;
	}
	
	public BigDecimal calcolaDifferenzaInRimodulazione (ArrayList<ContoEconomicoItem> listItem) {
		BigDecimal totaleSpesaAmmessa = new BigDecimal(0);
		BigDecimal totaleUltimaSpesaAmmessa = new BigDecimal(0);
		for (ContoEconomicoItem item : listItem) {	
			
			// Cerco le micro voci di spesa o le macro voci di spesa senza figli.
			
			if (item.getIdVoce() != null && !item.getHaFigli()) {
				BigDecimal importoSpesaAmmessa = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaRimodulazione());
				if (importoSpesaAmmessa != null) {
					totaleSpesaAmmessa = NumberUtil.sum(totaleSpesaAmmessa, importoSpesaAmmessa);
				}
				BigDecimal importoUltimaSpesaAmmessa = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaUltima());
				if (importoUltimaSpesaAmmessa != null) {
					totaleUltimaSpesaAmmessa = NumberUtil.sum(totaleUltimaSpesaAmmessa, importoUltimaSpesaAmmessa);
				}
			}
		}
		
		
		// Calcolo la differenza tra il totale nuova proposta ed il totale ultima spesa ammessa
		
		BigDecimal differenza = NumberUtil.subtract(totaleSpesaAmmessa, totaleUltimaSpesaAmmessa);
		
		return differenza;
		
	}
	
	
	public void ricalcolaTotaliInRimodulazione (ArrayList<ContoEconomicoItem> listItem) {
	
		// Ricerco le macro voci con figli o la voce conto economico
		/ ed applico la percentuale al campo importoSpesaAmmessaRimodulazione
		
		ContoEconomicoItem macroVoceItem = null;
		ContoEconomicoItem contoEconomicoItem = null;
		BigDecimal totaleImportoSpesaAmmessaMacroVoce = null;
		BigDecimal totaleImportoSpesaAmmessa = new BigDecimal(0);
		for (ContoEconomicoItem item : listItem) {	
			if ( item.getIdVoce() != null ) {
				if (item.getHaFigli()) {
					
					// macro voce
					
					macroVoceItem = item;
					totaleImportoSpesaAmmessaMacroVoce = new BigDecimal(0);
				} else {
					BigDecimal importoSpesaAmmessaVoce = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoSpesaAmmessaRimodulazione());
					totaleImportoSpesaAmmessa = NumberUtil.sum(importoSpesaAmmessaVoce, totaleImportoSpesaAmmessa);
					if (macroVoceItem != null && item.getIdPadre() != null) {
						if (item.getIdPadre().equals(NumberUtil.getStringValue(macroVoceItem.getIdVoce()))) {
					
							 // voce figlia della macro voce
							 	
							totaleImportoSpesaAmmessaMacroVoce = NumberUtil.sum(importoSpesaAmmessaVoce, totaleImportoSpesaAmmessaMacroVoce);
							macroVoceItem.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(totaleImportoSpesaAmmessaMacroVoce));
						}
					} else {
						
						// voce senza figli e senza padre
						
						macroVoceItem = null;
					}
					if (contoEconomicoItem != null) {
						contoEconomicoItem.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(totaleImportoSpesaAmmessa));
					}
				}
			} else if (item.getIdVoce() == null && item.getLabel() != null && 
					   item.getLabel().trim().length() > 0) {
				
				// Conto economico
				
				contoEconomicoItem = item;
			}
		}
	}
	
	
	public void ricalcolaTotaliInPropostaRimodulazione (ArrayList<ContoEconomicoItem> listItem) {
	
		 // Ricerco le macro voci con figli o la voce conto economico
		// ed applico la percentuale al campo importoRichiestoNuovaProposta
		 
		ContoEconomicoItem macroVoceItem = null;
		ContoEconomicoItem contoEconomicoItem = null;
		BigDecimal totaleImportoRichiestoMacroVoce = null;
		BigDecimal totaleImportoRichiesto = new BigDecimal(0);
		for (ContoEconomicoItem item : listItem) {	
			if ( item.getIdVoce() != null ) {
				if (item.getHaFigli()) {
					macroVoceItem = item;
					totaleImportoRichiestoMacroVoce = new BigDecimal(0);
				} else {
					BigDecimal importoRichiestoVoce = NumberUtil.toBigDecimalFromItalianFormat(item.getImportoRichiestoNuovaProposta());
					totaleImportoRichiesto = NumberUtil.sum(importoRichiestoVoce, totaleImportoRichiesto);
					if (macroVoceItem != null && item.getIdPadre() != null) {
						if (item.getIdPadre().equals(NumberUtil.getStringValue(macroVoceItem.getIdVoce()))) {
						
							 // voce figlia della macro voce
							 	
							totaleImportoRichiestoMacroVoce = NumberUtil.sum(importoRichiestoVoce, totaleImportoRichiestoMacroVoce);
							macroVoceItem.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(totaleImportoRichiestoMacroVoce));
						}
					} else {
						
						 // voce senza figli e senza padre
						
						macroVoceItem = null;
					}
					if (contoEconomicoItem != null) {
						//logger.info("\n10 importo: "+NumberUtil.getStringValue(totaleImportoRichiesto)+"\n");
						contoEconomicoItem.setImportoRichiestoNuovaProposta(NumberUtil.getStringValue(totaleImportoRichiesto));
					}
				}
			} else if (item.getIdVoce() == null && item.getLabel() != null && 
					   item.getLabel().trim().length() > 0) {
				
				// Conto economico
				
				contoEconomicoItem = item;
			}
		}
	}
	
	
	
	public void impostaPercentualeInRimodulazione (ContoEconomicoItem rigaPercentuale, BigDecimal percentuale) {
		rigaPercentuale.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(percentuale));
	}
	
	public void impostaDifferenzaInRimodulazione (ContoEconomicoItem rigaRibaltaDati, BigDecimal differenza) {
		rigaRibaltaDati.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValue(differenza));
	}
	
	
	*/
	
}
