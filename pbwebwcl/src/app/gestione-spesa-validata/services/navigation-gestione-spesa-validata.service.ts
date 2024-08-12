import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { FiltroRicercaSpesaValidata } from "../commons/dto/filtro-ricerca-spesa-validata";

@Injectable()
export class NavigationGestioneSpesaValidataService extends NavigationService {
    private filtroRicercaSpesaValidata: FiltroRicercaSpesaValidata = null;

    public set filtroRicercaSpesaValidataSelezionato(value: FiltroRicercaSpesaValidata) {
        this.filtroRicercaSpesaValidata = value;
    }

    public get filtroRicercaSpesaValidataSelezionato(): FiltroRicercaSpesaValidata {
        return this.filtroRicercaSpesaValidata;
    }

}