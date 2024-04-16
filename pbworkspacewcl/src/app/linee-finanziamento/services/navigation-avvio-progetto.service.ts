/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";

@Injectable()
export class NavigationAvvioProgettoService extends NavigationService {
    private codiceProgetto: string;
    private cup: string;
    private titoloProgetto: string;
    private beneficiario: string;

    public set lastCodiceProgetto(value: string) {
        this.codiceProgetto = value;
    }

    public get lastCodiceProgetto(): string {
        return this.codiceProgetto;
    }

    public set lastCup(value: string) {
        this.cup = value;
    }

    public get lastCup(): string {
        return this.cup;
    }

    public set lastTitoloProgetto(value: string) {
        this.titoloProgetto = value;
    }

    public get lastTitoloProgetto(): string {
        return this.titoloProgetto;
    }

    public set lastBeneficiario(value: string) {
        this.beneficiario = value;
    }

    public get lastBeneficiario(): string {
        return this.beneficiario;
    }
}