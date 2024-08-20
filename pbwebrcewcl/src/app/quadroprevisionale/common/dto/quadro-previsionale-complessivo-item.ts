/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface QuadroPrevisionaleComplessivoItem {
  codiceErrore?: string;
  daRealizzare?: string;
  dataUltimaPreventivo?: string;
  dataUltimaSpesaAmmessa?: string;
  dataNuovoPreventivo?: string;
  haFigli?: boolean;
  id?: string;
  idPadre?: string;
  idVoce?: number;
  isError?: boolean;
  isMacroVoce?: boolean;
  isRigaDate?: boolean;
  isTotaleComplessivo?: boolean;
  isVociVisibili?: boolean;
  label?: string;
  nuovoPreventivo?: string;
  realizzato?: string;
  ultimaSpesaAmmessa?: string;
  ultimoPreventivo?: string;
}
