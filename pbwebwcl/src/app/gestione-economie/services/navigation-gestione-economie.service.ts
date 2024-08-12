import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { ParametriRicercaEconomieDTO } from "../commons/dto/parametri-ricerca-economia-dto";

@Injectable()
export class NavigationGestioneEconomieService extends NavigationService {
    private filtroRicerca: ParametriRicercaEconomieDTO = null;

    public set filtroRicercaSelezionato(value: ParametriRicercaEconomieDTO) {
        this.filtroRicerca = value;
    }

    public get filtroRicercaSelezionato(): ParametriRicercaEconomieDTO {
        return this.filtroRicerca;
    }
}