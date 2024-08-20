/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface ExecResults {
  fldErrors?: { [key: string]: string };
  globalErrors?: Array<string>;
  globalMessages?: Array<string>;
  model?: any;
  resultCode?: string;
}
