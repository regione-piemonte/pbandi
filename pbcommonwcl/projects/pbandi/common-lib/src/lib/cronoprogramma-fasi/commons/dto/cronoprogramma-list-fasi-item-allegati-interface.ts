/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatiCronoprogrammaFasi } from "./allegati-cronoprogramma-fasi";
import { CronoprogrammaFasiItemInterface } from "./cronoprogramma-fasi-item-interface";

export class CronoprogrammaListFasiItemAllegatiInterface {

  
  fasiItemList : Array<CronoprogrammaFasiItemInterface>
  allegatiList : Array<AllegatiCronoprogrammaFasi>

  
  constructor(){
    this.fasiItemList = new Array<CronoprogrammaFasiItemInterface> ()
    this.allegatiList = new Array<AllegatiCronoprogrammaFasi>  ()
  }

  
  

}
