/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatiCronoprogrammaFasi } from "./allegati-cronoprogramma-fasi";
import { CronoprogrammaFasiItem } from "./cronoprogramma-fasi-item";

export class CronoprogrammaListFasiItemAllegati {

  fasiItemList: Array<CronoprogrammaFasiItem>
  allegatiList: Array<AllegatiCronoprogrammaFasi>

  constructor() {
    this.fasiItemList = new Array<CronoprogrammaFasiItem>()
    this.allegatiList = new Array<AllegatiCronoprogrammaFasi>()
  }

  pushItem(item: CronoprogrammaFasiItem) {
    this.fasiItemList.push(item)
  }

  pushAllegati(allegato: AllegatiCronoprogrammaFasi) {
    this.allegatiList.push(allegato);
  }

}
