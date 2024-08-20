/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { FiltroRicercaBeneficiari } from "../commons/dto/filtro-ricerca-beneficiari";

@Injectable()
export class NavigationRicercaBeneficiariService extends NavigationService {
    private filtroRicercaBeneficiari: FiltroRicercaBeneficiari = null;

    public set filtroRicercaBeneficiariSelezionato(value: FiltroRicercaBeneficiari) {
        this.filtroRicercaBeneficiari = value;
    }

    public get filtroRicercaBeneficiariSelezionato(): FiltroRicercaBeneficiari {
        return this.filtroRicercaBeneficiari;
    }

}