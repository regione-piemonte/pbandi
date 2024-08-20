/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class QuadroPrevisionaleItem {
  public codiceErrore: string;
  public daRealizzare: string;
  public dataNuovoPreventivo: string;
  public dataUltimaPreventivo: string;
  public haFigli: boolean;
  public id: string;
  public idPadre: string;
  public idRigoQuadro: number;
  public idVoce: number;
  public isError: boolean;
  public isMacroVoce: boolean;
  public isPeriodo: boolean;
  public isRigaDate: boolean;
  public isRigaRibaltamento: boolean;
  public isRigaTotale: boolean;
  public isVociVisibili: boolean;
  public label: string;
  public level: number;
  public modificabile: boolean;
  public nuovoPreventivo: string;
  public periodo: string;
  public realizzato: string;
  public ultimaSpesaAmmessa: string;
  public ultimoPreventivo: string;
}
