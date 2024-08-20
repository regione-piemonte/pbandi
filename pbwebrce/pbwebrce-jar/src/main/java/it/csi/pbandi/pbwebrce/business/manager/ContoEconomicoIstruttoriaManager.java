/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.manager;

import static it.csi.pbandi.pbwebrce.util.Constants.IMPORTO;
import static it.csi.pbandi.pbwebrce.util.Constants.MAX_IMPORTO_AMMMESSO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.business.intf.MessageKey;
//import it.csi.pbandi.pbservizit.pbandiutil.commonweb.MessageManager;
import it.csi.pbandi.pbwebrce.dto.ContoEconomico;
import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContoEconomicoIstruttoriaManager extends ContoEconomicoManager {

	public  ArrayList<ContoEconomicoItem> mappaContoEconomicoPerVisualizzazioneIstruttoria(
			ContoEconomico contoEconomico,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			ArrayList<ContoEconomicoItem> contoEconomicoItemListPerImporto,
			boolean isModifica, boolean ribaltaRichiestoInAmmesso) {
		
		boolean isRimodulazione=true;
		
		Double  importo= new Double(0);
		Map <String,Double>mapImporto=new HashMap<String,Double>();
		mapImporto.put(IMPORTO,importo);
		
		ArrayList<ContoEconomicoItem> contoEconomicoItemList=new ArrayList<ContoEconomicoItem>();
		if (!ObjectUtil.isNull(contoEconomicoRimodulazione)) {

			ContoEconomicoRimodulazioneDTO contoEconomicoDTOarray[] = contoEconomicoRimodulazione.getFigli();
			
			if (!ObjectUtil.isEmpty(contoEconomicoDTOarray)) {
				int level = 1;
				mappaContiEconomiciFigliPerVisualizzazione(
						contoEconomicoItemList,
						contoEconomicoDTOarray, 
						level,
						mapImporto,
						null,
						isModifica,
						ribaltaRichiestoInAmmesso);
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
		
		return contoEconomicoItemListPerImporto;		
	}
	
	private void mappaContiEconomiciFigliPerVisualizzazione(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			ContoEconomicoRimodulazioneDTO[] contoEconomicoDTOarray, 
			int level,
			Map <String,Double>mapImporto,
			String idPadre,
			boolean isModifica,
			boolean ribaltaRichiestoInAmmesso) {
		
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
			Double spesaAmmessa=contoEconomicoDTO.getImportoSpesaAmmessaInDetermina()!=null?contoEconomicoDTO.getImportoSpesaAmmessaInDetermina():zero;
			//Double spesaAmmessaRimodulazione=contoEconomicoDTO.getImportoSpesaAmmessaRimodulazione()!=null?contoEconomicoDTO.getImportoSpesaAmmessaRimodulazione():zero;
				
			Double importoRichiestoInDomanda= contoEconomicoDTO.getImportoRichiestoInDomanda()!=null?contoEconomicoDTO.getImportoRichiestoInDomanda():zero;
			
			Double value=null;
							
			value = spesaAmmessa;
				
			if(value==null)				
				value=zero;
				
			contoEconomicoItem.setImportoSpesaAmmessaRimodulazione(NumberUtil.getStringValueItalianFormat(value));
				
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
					level + 1, mapImporto,idVocePadre,isModifica,ribaltaRichiestoInAmmesso);
				// non modificabile
				
			} else {
				// modificabile
				contoEconomicoItem.setModificabile(true);
				contoEconomicoItemList.add(contoEconomicoItem);
			}
		}
	}
	
	public  Set<String>  controllaImportiPerRimodulazioneIstruttoria(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			Set<String> messaggiErrore, Map<String, String> fieldErrors) {
	
		ContoEconomicoRimodulazioneDTO contoEconomicoDTOFigli[] = contoEconomicoRimodulazione.getFigli();
		
		Set<String> messaggiWarnings=new HashSet <String>();
		
		for (ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO : contoEconomicoDTOFigli) {
		
			if (contoEconomicoRimodulazioneDTO.getFigli() != null) {
				for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
					controllaImportiItemPerRimodulazioneIstruttoria(index,
							contoEconomicoFiglioDTO,messaggiErrore,
							messaggiWarnings,fieldErrors);
				}
			} else {
				controllaImportiItemPerRimodulazioneIstruttoria(index,
						contoEconomicoRimodulazioneDTO,messaggiErrore,
						messaggiWarnings,fieldErrors);
			}
		}

		if (ObjectUtil.isEmpty(messaggiErrore)) {
			return messaggiWarnings;
		} else {
			return messaggiErrore;
		}
		
	}
	
	private  void  controllaImportiItemPerRimodulazioneIstruttoria(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO,
			Collection<String> messaggiErrore,
			Collection<String> messaggiWarnings,
			Map<String, 
			String> fieldErrors) {
		
		if (contoEconomicoRimodulazioneDTO.getFigli() != null){
			for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
				controllaImportiItemPerRimodulazioneIstruttoria(index,contoEconomicoFiglioDTO, messaggiErrore,messaggiWarnings,fieldErrors);
			}
		} else {
			if (contoEconomicoRimodulazioneDTO.getIdVoce() != null) {
				
				ContoEconomicoItem contoEconomicoItem=(ContoEconomicoItem)index.get(contoEconomicoRimodulazioneDTO.getIdVoce());
				                               
				if (contoEconomicoItem != null) {

					String importoSpesaAmmessaRimodulazione=contoEconomicoItem.getImportoSpesaAmmessaRimodulazione();
					
					contoEconomicoItem.setCodiceErrore(null);
					
					
					if (ObjectUtil.isEmpty(importoSpesaAmmessaRimodulazione) ||
					   NumberUtil.toNullableDoubleItalianFormat(importoSpesaAmmessaRimodulazione)==null) {
						contoEconomicoItem.setCodiceErrore("X");
						messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
					}
					else{
						Double spesaAmmessaRimodulazione      = NumberUtil.toNullableDoubleItalianFormat(importoSpesaAmmessaRimodulazione);
						Double richiestoInDomanda             = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoRichiestoInDomanda());
						Double importoAmmessoDaBando          = contoEconomicoRimodulazioneDTO.getImportoAmmessoDaBando();
												
						if (spesaAmmessaRimodulazione != null) {
							if(spesaAmmessaRimodulazione.doubleValue()<0){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);							
							} else if(spesaAmmessaRimodulazione.doubleValue()> MAX_IMPORTO_AMMMESSO ){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);							
							} else if(importoAmmessoDaBando!=null &&
									 NumberUtil.compare(spesaAmmessaRimodulazione, importoAmmessoDaBando)>0){
								contoEconomicoItem.setCodiceErrore("e");
								messaggiWarnings.add(MessageKey.IMPORTO_SUPERIORE_AMMISSIBILE);						
							} else if (richiestoInDomanda!=null && 
									  NumberUtil.compare(spesaAmmessaRimodulazione, richiestoInDomanda)>0) {
								contoEconomicoItem.setCodiceErrore("a");
								messaggiWarnings.add(MessageKey.IMPORTO_SUPERIORE_RICHIESTO_IN_DOMANDA);							
							} else if (richiestoInDomanda!=null && 
									   NumberUtil.compare(spesaAmmessaRimodulazione, richiestoInDomanda)<0) {
								contoEconomicoItem.setCodiceErrore("b");
								messaggiWarnings.add(MessageKey.IMPORTO_INFERIORE_RICHIESTO_IN_DOMANDA);								
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
		
}
