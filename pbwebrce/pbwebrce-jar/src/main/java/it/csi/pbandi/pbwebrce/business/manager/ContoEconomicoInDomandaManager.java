/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.manager;

import static it.csi.pbandi.pbwebrce.util.Constants.MAX_IMPORTO_AMMMESSO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.business.intf.MessageKey;
import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class ContoEconomicoInDomandaManager 
//extends ContoEconomicoManager 
{
	
	@Autowired
	private  LoggerUtil logger;
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}
	public LoggerUtil getLogger() {
		return logger;
	}

	public ArrayList<ContoEconomicoItem>  mappaContoEconomicoPerVisualizzazioneInDomanda(
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,boolean setZeroToNullVal) {
		
		long begin = System.currentTimeMillis();
		
		ContoEconomicoRimodulazioneDTO contoEconomicoDTOarray[] = contoEconomicoRimodulazione.getFigli();
		if(ObjectUtil.isEmpty(contoEconomicoItemList)){
			 contoEconomicoItemList = new ArrayList<ContoEconomicoItem>();
			if (!ObjectUtil.isEmpty(contoEconomicoDTOarray)) {
			
				int level = 1;
				mappaContiEconomiciFigliPerVisualizzazione(
					contoEconomicoDTOarray,contoEconomicoItemList,
					level,
					null,
					setZeroToNullVal					
					);
			}
		}
		
		Double importo = calcolaImportoPerVisualizzazione(contoEconomicoItemList);
			
		mappaPrimeRighePerVisualizzazione(contoEconomicoRimodulazione, 
				contoEconomicoItemList	,importo			
				);
		
		calcolaImportiMacroVociPerVisualizzazione(contoEconomicoItemList);
		
	
		long end = System.currentTimeMillis() - begin;
		getLogger()
				.debug("\nContoEconomico rimappato da DTO server a Presentation in "
						+ end + " millisec\n\n\n");
		

		
		return contoEconomicoItemList;
	}

	
	
	public Double calcolaImportoPerVisualizzazione(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList) {

		Double importo = new Double(0);
		for (ContoEconomicoItem contoEconomicoItem : contoEconomicoItemList) {

			if (contoEconomicoItem.getModificabile()) {
				importo = getImporto(contoEconomicoItem,importo);
			}
		}
		return importo;
	}
	
	
	private void mappaContiEconomiciFigliPerVisualizzazione(	
			ContoEconomicoRimodulazioneDTO contoEconomicoDTOarray[],
			ArrayList<ContoEconomicoItem> contoEconomicoItemList,
			int level,
			String idPadre,
			boolean setZeroToNullVal){
		for (ContoEconomicoRimodulazioneDTO contoEconomicoDTO : contoEconomicoDTOarray) {
			ContoEconomicoItem contoEconomicoItem = new ContoEconomicoItem();
			BeanUtil.valueCopy(contoEconomicoDTO, contoEconomicoItem);
			
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
						
			if(setZeroToNullVal && contoEconomicoItem.getImportoRichiestoInDomanda()==null){
				contoEconomicoItem.setImportoRichiestoInDomanda("0,00");
			}
			contoEconomicoItem.setIdPadre(idPadre);
			contoEconomicoItem.setLevel(level);
			contoEconomicoItem.setId(contoEconomicoDTO.getIdVoce().toString());
			
			
			if (!ObjectUtil.isEmpty(contoEconomicoDTO.getFigli())) {
				contoEconomicoItem.setHaFigli(true);
				contoEconomicoItem.setModificabile(false);

				contoEconomicoItemList.add(contoEconomicoItem);
				ContoEconomicoRimodulazioneDTO contoEconomicoFigli[] = contoEconomicoDTO
						.getFigli();
				
				String idVocePadre=contoEconomicoDTO.getIdVoce()!=null?contoEconomicoDTO.getIdVoce().toString():null;
				
				mappaContiEconomiciFigliPerVisualizzazione(
						 contoEconomicoFigli,contoEconomicoItemList,
						 level + 1, idVocePadre,setZeroToNullVal);
				// non modificabile
			} else {
				// modificabile
				contoEconomicoItem.setModificabile(true);
				contoEconomicoItemList.add(contoEconomicoItem);
			}
			
		}
	}
	
	
	
	
	protected  void mappaPrimeRighePerVisualizzazione(
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			ArrayList<ContoEconomicoItem> contoEconomicoItemList, Double importo
			) {
			
		ContoEconomicoItem contoEconomicoItemSecondaRiga = new ContoEconomicoItem();

		
		int indice=0;
		contoEconomicoItemSecondaRiga.setImportoRichiestoInDomanda(NumberUtil.getStringValueItalianFormat(importo));
		contoEconomicoItemSecondaRiga.setLabel("Conto Economico");
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

	}

	
	
	

	protected  void calcolaImportiMacroVociPerVisualizzazione(
			ArrayList<ContoEconomicoItem> contoEconomicoItemList
			) {
			
			Map <String,Double>mapMacroVoci=new HashMap<String,Double>();
			for (ContoEconomicoItem contoEconomicoItem : contoEconomicoItemList) {
				
				String idPadre=contoEconomicoItem.getIdPadre();
				Long idVoce=contoEconomicoItem.getIdVoce();
				Double importo=mapMacroVoci.get(idPadre);
				
				importo = getImporto(contoEconomicoItem,importo);
				
				if(idPadre!=null && importo!=null){
					mapMacroVoci.put(idPadre,importo);
				}else if(!contoEconomicoItem.getHaFigli() && importo!=null){
					mapMacroVoci.put(idVoce.toString(),importo);
				}
			}
			
			for (ContoEconomicoItem contoEconomicoItem : contoEconomicoItemList) {
				if(contoEconomicoItem.getIdVoce()!=null){
					if(mapMacroVoci.containsKey(contoEconomicoItem.getIdVoce().toString())){
						
						Double importo=mapMacroVoci.get(contoEconomicoItem.getIdVoce().toString());
						
						contoEconomicoItem.setImportoRichiestoInDomanda(NumberUtil.getStringValueItalianFormat(importo));
						
					}
				}
			}
	}	
	
	
	private Double getImporto(
			ContoEconomicoItem contoEconomicoItem, Double importo) {
		Double	value=NumberUtil.toNullableDoubleItalianFormat(
				contoEconomicoItem.getImportoRichiestoInDomanda());
		
		if(value!=null && value>0){
			if(importo==null)importo=0d;
				importo = NumberUtil.sum(importo,value);
		}
		return importo;
	}
	
	
	
	
	public  Set<String>  controllaImportiPerRichiestoInDomanda(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazione,
			Set<String> messaggiErrore) {
		
		ContoEconomicoRimodulazioneDTO contoEconomicoDTOFigli[] = contoEconomicoRimodulazione.getFigli();

		Set<String> messaggiWarnings=new HashSet <String>();
		
		for (ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO : contoEconomicoDTOFigli) {
		
			if(contoEconomicoRimodulazioneDTO.getFigli()!=null){
				for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
					controllaImportiItemPerRichiestoInDomanda(index,contoEconomicoFiglioDTO,messaggiErrore,
							messaggiWarnings);
				}
			}
			else{
				controllaImportiItemPerRichiestoInDomanda(index,contoEconomicoRimodulazioneDTO,
					messaggiErrore,
					messaggiWarnings);
			}
		}
			
		
		if(ObjectUtil.isEmpty(messaggiErrore)){
			return messaggiWarnings;
		}
		else{
			return messaggiErrore;
		}
	}
	
	private  void  controllaImportiItemPerRichiestoInDomanda(
			Map<?, ContoEconomicoItem> index,
			ContoEconomicoRimodulazioneDTO contoEconomicoRimodulazioneDTO,
			Collection<String> messaggiErrore,
			Collection<String> messaggiWarnings) {
		
		if(contoEconomicoRimodulazioneDTO.getFigli()!=null){
			for (ContoEconomicoRimodulazioneDTO contoEconomicoFiglioDTO : contoEconomicoRimodulazioneDTO.getFigli()) {
				controllaImportiItemPerRichiestoInDomanda(index,contoEconomicoFiglioDTO,
						messaggiErrore,messaggiWarnings);
			}
		}	
		else{
			if (contoEconomicoRimodulazioneDTO.getIdVoce() != null) {
				
				ContoEconomicoItem contoEconomicoItem=(ContoEconomicoItem)index.get(contoEconomicoRimodulazioneDTO.getIdVoce());
				
				if(contoEconomicoItem!=null){
					
					contoEconomicoItem.setCodiceErrore(null);
					
					String importoRichiestoInDomanda=contoEconomicoItem.getImportoRichiestoInDomanda();
					
					if(ObjectUtil.isEmpty(importoRichiestoInDomanda)
							||
							NumberUtil.toNullableDoubleItalianFormat(importoRichiestoInDomanda)==null
							){
						contoEconomicoItem.setCodiceErrore("X");
						messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
					}else{
					
						Double richiestoIndomanda     = NumberUtil.toNullableDoubleItalianFormat(contoEconomicoItem.getImportoRichiestoInDomanda());
						Double importoAmmessoDaBando  = contoEconomicoRimodulazioneDTO.getImportoAmmessoDaBando();
						
						
						if(richiestoIndomanda!=null){
							if(richiestoIndomanda.doubleValue()<0 ){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
							}
							else if(richiestoIndomanda.doubleValue()>MAX_IMPORTO_AMMMESSO ){
								contoEconomicoItem.setCodiceErrore("X");
								messaggiErrore.add(MessageKey.IMPORTI_NON_VALIDI);
							} 
							else if(importoAmmessoDaBando!=null &&
									 NumberUtil.compare(richiestoIndomanda, importoAmmessoDaBando)>0
									){
								contoEconomicoItem.setCodiceErrore("e");
								messaggiWarnings.add((MessageKey.IMPORTO_SUPERIORE_AMMISSIBILE));
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
	
	public void removeCodiceWarning(List <ContoEconomicoItem> list) {
		for (Iterator <ContoEconomicoItem>iterator = list.iterator(); iterator.hasNext();) {
			ContoEconomicoItem contoEconomicoItem = (ContoEconomicoItem) iterator.next();
			if(contoEconomicoItem.getCodiceErrore()!=null && !contoEconomicoItem.getCodiceErrore().equalsIgnoreCase("X"))
			contoEconomicoItem.setCodiceErrore(null);
		}
	}
	
}
