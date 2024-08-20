/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ConfigService } from "src/app/core/services/config.service";
import { Riassicurazione_BeneficiarioDomandaVO } from "../commons/dto/riassicurazione_BeneficiarioDomandaVO";
import { Riassicurazione_RiassicurazioniVO } from "../commons/dto/riassicurazione_RiassicurazioniVO";
import { DatePipe } from "@angular/common";
import { initGestioneEscussioneRiassicurazioniVO } from "../commons/dto/init-gestione-escussione-riassicurazioni-VO";
import { modificaEscussioneRiassicurazioniDTO } from "../commons/dto/modifica-escussione-riassicurazioni-DTO";

@Injectable()
export class GestioneRiassicurazioniService {
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private datePipe: DatePipe
    ) { }

    /*getStatiEscussione(): Observable<Array<FiltroGestioneGaranzie>> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getListaStatiEscussione'; 
        return this.http.get<Array<FiltroGestioneGaranzie>>(url);
    }*/

    cercaBeneficiariRiassicurazioni(descrizioneBando = "", codiceProgetto = "", codiceFiscale = "", nag = "", partitaIva = "", denominazioneCognomeNome = "", statoEscussione = "", denominazioneBanca = "") {
        let url = this.configService.getApiURL() + '/restfacade/riassicurazioni/ricercaBeneficiariRiassicurazioni';

        let params = new HttpParams()
        .set("descrizioneBando", descrizioneBando.toString())
        .set("codiceProgetto", codiceProgetto.toString())
        .set("codiceFiscale", codiceFiscale.toString())
        .set("nag", nag.toString())
        .set("partitaIva", partitaIva.toString())
        .set("denominazioneCognomeNome", denominazioneCognomeNome.toString())
        .set("statoEscussione", statoEscussione.toString())
        .set("denominazioneBanca", denominazioneBanca.toString())

        return this.http.get<Array<Riassicurazione_BeneficiarioDomandaVO>>(url, { params: params });
    }


    getDettaglioRiassicurazioni(idProgetto: number = 0, idRiassicurazione: number = 0, getStorico: boolean = false) {
        let url = this.configService.getApiURL() + '/restfacade/riassicurazioni/getDettaglioRiassicurazioni';

        let params = new HttpParams()
        .set("idProgetto", idProgetto.toString())
        .set("idRiassicurazione", idRiassicurazione.toString())
        .set("getStorico", getStorico.toString());

        return this.http.get<Array<Riassicurazione_RiassicurazioniVO>>(url, { params: params });
    }
    
    modificaRiassicurazione(idRiassicurazione: number, isImportoModified: boolean, importoEscusso: number = 0, isDataEscussioneModified: boolean, dataEscussione: Date = null, isDataScaricoModified: boolean, dataScarico: Date = null) {
        let url = this.configService.getApiURL() + '/restfacade/riassicurazioni/modificaRiassicurazione';

        if (!importoEscusso) importoEscusso = 0;

        /*let params = new HttpParams()
        .set("idRiassicurazione", idRiassicurazione.toString())
        .set("isImportoModified", isImportoModified.toString())
        .set("importoEscusso", importoEscusso.toString())
        .set("isDataEscussioneModified", isDataEscussioneModified.toString())
        .set("isDataScaricoModified", isDataScaricoModified.toString()); */
        //if(dataEscussione) params.append("dataEscussione", dataEscussione?.toLocaleDateString());
        //if(dataScarico )  params.append("dataScarico", dataScarico?.toLocaleDateString());
        
        let params:{[param: string]: string | string[]} = {
            'idRiassicurazione': idRiassicurazione+'',
            //'isImportoModified': isImportoModified+'',
            'importoEscusso': importoEscusso+'',
            //'isDataEscussioneModified': isDataEscussioneModified+'',
            //'isDataScaricoModified': isDataScaricoModified+'',
        }

        if (dataEscussione) {

            const data: string = dataEscussione.toLocaleDateString();
            const dataSplitted = data.split("/");
            const anno = dataSplitted[2];
            const mese = dataSplitted[1];
            const giorno = dataSplitted[0];
            const dataConvertita = `${anno}/${mese}/${giorno}`;

            params['dataEscussione'] = dataConvertita;

        } 

        if (dataScarico) {
            const data2 = dataScarico.toLocaleDateString();
            const dataSplitted2 = data2.split("/");
            const anno2 = dataSplitted2[2];
            const mese2 = dataSplitted2[1];
            const giorno2 = dataSplitted2[0];
            const dataConvertita2 = `${anno2}/${mese2}/${giorno2}`;

            params['dataScarico'] = dataConvertita2;
        }

        return this.http.get<Boolean>(url, { params: params });
    }

    initGestioneEscussioneRiassicurazioni(idProgetto: number = 0, idEscussione: number = 0) {
        let url = this.configService.getApiURL() + '/restfacade/riassicurazioni/initGestioneEscussioneRiassicurazioni';
        let params = new HttpParams()
        .set("idEscussione", idEscussione.toString())
        .set("idProgetto", idProgetto.toString());
        return this.http.get<initGestioneEscussioneRiassicurazioniVO>(url, { params: params });
    }

    modificaEscussioneRiassicurazioni(dati: modificaEscussioneRiassicurazioniDTO, inserisciNuovo: boolean) {
        
        let url = this.configService.getApiURL() + '/restfacade/riassicurazioni/modificaEscussioneRiassicurazioni';

        let params = new HttpParams().set("inserisciNuovo", inserisciNuovo.toString())

        return this.http.post<any>(url, dati, { params: params })
    }
}