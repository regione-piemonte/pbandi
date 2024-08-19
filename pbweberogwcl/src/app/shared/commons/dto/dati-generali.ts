/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Bando } from './bando';
import { Beneficiario } from './beneficiario';
import { DatiCumuloDeMinimis } from './dati-cumulo-de-minimis';
import { EconomiaPerDatiGenerali } from './economia-per-dati-generali';
import { ImportoDescrizione } from './importo-descrizione';
import { ImportoAgevolato } from './importo-agevolato';
import { Progetto } from './progetto';
export interface DatiGenerali {
  bando?: Bando;
  beneficiario?: Beneficiario;
  codiceDichiarazione?: string;
  dataPresentazione?: string;
  datiCumuloDeMinimis?: DatiCumuloDeMinimis;
  descrizioneBreve?: string;
  economie?: Array<EconomiaPerDatiGenerali>;
  erogazioni?: Array<ImportoDescrizione>;
  flagImportoCertificatoNettoVisibile?: string;
  importiAgevolati?: Array<ImportoAgevolato>;
  importoAmmesso?: number;
  importoCertificatoNettoUltimaPropAppr?: number;
  importoCofinanziamentoPrivato?: number;
  importoCofinanziamentoPubblico?: number;
  importoImpegno?: number;
  importoQuietanzato?: number;
  importoRendicontato?: number;
  importoResiduoAmmesso?: string;
  importoSoppressioni?: number;
  importoValidatoNettoRevoche?: number;
  isLegatoBilancio?: boolean;
  preRecuperi?: Array<ImportoDescrizione>;
  progetto?: Progetto;
  recuperi?: Array<ImportoDescrizione>;
  revoche?: Array<ImportoDescrizione>;
}
