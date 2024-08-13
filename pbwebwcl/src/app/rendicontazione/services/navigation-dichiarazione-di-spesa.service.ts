/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { DecodificaDTO } from "../commons/dto/decodifica-dto";

@Injectable()
export class NavigationDichiarazioneDiSpesaService extends NavigationService {
    private tipoDichiarazione: string;
    private isVerificaVisible: boolean;
    private rappresentanteLegale: DecodificaDTO;
    private delegato: DecodificaDTO;
    private importoRichiestaErog: number;
    private osservazioni: string;

    public set tipoDichiarazioneSelezionato(value: string) {
        this.tipoDichiarazione = value;
    }
    public get tipoDichiarazioneSelezionato(): string {
        return this.tipoDichiarazione;
    }

    public set lastIsVerificaVisible(value: boolean) {
        this.isVerificaVisible = value;
    }
    public get lastIsVerificaVisible(): boolean {
        return this.isVerificaVisible;
    }

    public set rappresentanteLegaleSelected(value: DecodificaDTO) {
        this.rappresentanteLegale = value;
    }
    public get rappresentanteLegaleSelected(): DecodificaDTO {
        return this.rappresentanteLegale;
    }

    public set delegatoSelected(value: DecodificaDTO) {
        this.delegato = value;
    }
    public get delegatoSelected(): DecodificaDTO {
        return this.delegato;
    }

    public set lastImportoRichiestaErog(value: number) {
        this.importoRichiestaErog = value;
    }
    public get lastImportoRichiestaErog(): number {
        return this.importoRichiestaErog;
    }

    public set lastOsservazioni(value: string) {
        this.osservazioni = value;
    }
    public get lastOsservazioni(): string {
        return this.osservazioni;
    }
}