/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

//java    it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO
export class TipoAllegatoDTO {
  public descTipoAllegato: string;
  public idTipoDocumentoIndex: number;
  public progrBandoLineaIntervento: number;
  public idDichiarazioneSpesa: number;
  public idMicroSezioneModulo: number;
  public flagAllegato: string;
  public numOrdinamentoMicroSezione: number;
  public idProgetto: number;
  public checked: boolean; //solo frontend
}
