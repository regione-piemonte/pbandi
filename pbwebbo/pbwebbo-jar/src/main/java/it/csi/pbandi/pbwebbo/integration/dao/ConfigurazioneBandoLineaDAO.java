/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import java.util.List;

import it.csi.pbandi.pbwebbo.dto.GenericResponseDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.AnagraficaDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.BancaSuggestVO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiBancariEstesiDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.IbanDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.SendToAmmContDTO;
import it.csi.pbandi.pbwebbo.dto.monitoraggioTempi.ParametriMonitoraggioTempiAssociatiDTO;

public interface ConfigurazioneBandoLineaDAO {
	
	///////////////////////////////   TAB ESTREMI BANCARI //////////////////////////////////////	
	
	public List<ModalitaAgevolazioneDTO> getModalitaAgevolazioneByidBando(Long idBando);	
	
	public List<EstremiBancariEstesiDTO> getEstremiBancari(Long idBando, Long idModalitaAgevolazione)throws Exception;
	
	public String checkBandoLineaByIban(String iban)throws Exception;

	public Long insertEstremiBancari(Long idBanca, String iban, Long idUtenteIns) throws Exception; 
	
	public List<EstremiBancariEstesiDTO> getEstremiBancari(Long idBando)throws Exception;
	
	public void insertAssociazioneBandoModAgevEstrBanc(Long idModalitaAgevolazione, Long idBando, Long idEstremiBancari, String moltiplicatore, 
			Long idUtenteIns, String tipologiaConto)throws Exception; 
	
	public List<BancaSuggestVO> getBancheByDesc(String descrizione) throws Exception; 
	
	public AnagraficaDTO getAnagrafica(Long idBando, Long idEnteCompetenza) throws Exception;
	
	public IbanDTO getIbanInfo(Long idBando) throws Exception;
	
	public SendToAmmContDTO getSendToAmmContDTO(Long idBando, Long idModalitaAgevolazione, Long idBanca, String iban,
			Long idServizio, Long idEntita, String esito) throws Exception;
	
	public void removeEstremiBancari(Long idEstremiBancari) throws Exception;
	
	
	
	
	///////////////////////////////   TAB MONITORAGGIO TEMPI //////////////////////////////////////	
	
	public List<GenericResponseDTO> getParametriMonitoraggioTempi()throws Exception;
	
	
	public List<ParametriMonitoraggioTempiAssociatiDTO> getParametriMonitoraggioTempiAssociatiByBando(Long idBandoLinea)throws Exception;
	
	public ParametriMonitoraggioTempiAssociatiDTO getParametriMonitoraggioTempiAssociatiById(Long idParamMonitBandoLinea)throws Exception;
	
	public Long insertParametriMonitoraggioTempiAssociati (ParametriMonitoraggioTempiAssociatiDTO parametriMonitoraggioTempi)throws Exception;
	
	public Long logicDeleteParametriMonitoraggioTempiAssociati (ParametriMonitoraggioTempiAssociatiDTO parametriMonitoraggioTempi)throws Exception;
	
	public List<ParametriMonitoraggioTempiAssociatiDTO> getParametriMonitoraggioTempiAssociati(Long idParametroMonit, Long progrBandoLineaIntervento) throws Exception;
	
	
	
	
	
}
