/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";

@Injectable()
export class NavigationLineeFinanziamentoService extends NavigationService {
    private titoloLinea: string;

    public set lastTitoloLinea(value: string) {
        this.titoloLinea = value;
    }

    public get lastTitoloLinea(): string {
        return this.titoloLinea;
    }
}