/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FormControl } from "@angular/forms";

export class DatiSif {
    dataFirmaAccordo: FormControl;
    dataComplValut: FormControl;

    constructor() {
        this.dataFirmaAccordo = new FormControl();
        this.dataComplValut = new FormControl();
    }
}