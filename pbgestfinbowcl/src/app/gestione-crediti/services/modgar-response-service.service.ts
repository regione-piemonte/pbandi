/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { StatoCreditoVO } from "src/app/gestione-garanzie/commons/stato-credito-vo";
import { AllegatoVO } from "../commons/dto/allegatoVO";
import { DettaglioFinanziamentoErogato } from "../commons/dto/dettaglio-finanziamento-erogato";
import { FinanziamentoErogato } from "../commons/dto/finanziamento-erogato";
import { Messaggio } from "../commons/dto/messaggio-dto";
import { SaveSchedaCliente } from "../commons/dto/save-scheda-cliente.all";
import { SaveStatoEscussioneGaranzia } from "../commons/dto/save-statoescussione-garanzia.all";
import { SchedaClienteMain } from "../commons/dto/scheda-cliente-main";
import { VisualizzaStoricoEscussioneVO } from "../commons/dto/un-vo-che-avrebbe-dovuto-esserci-da-anni-VO";

@Injectable({
    providedIn: 'root'
})

export class ModGarResponseService {
  
    
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService
    ) {}

    getSchedaCliente(idUtente = "", idProgetto = "") {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getVisualizzaDatiAnagrafici';
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idUtente", idUtente);
        return this.http.get<any>(url, { params: params });
    }

    getBancaSuggestion(value: string) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getBancaSuggestion';
        let params = new HttpParams().set("value", value.toString().toUpperCase());
        return this.http.get<any>(url, { params: params });
    }

    getIbanSuggestion(value: string) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getIbanSuggestion';
        let params = new HttpParams().set("value", value.toString().toUpperCase());
        return this.http.get<any>(url, { params: params });
    }

    getStoricoStatoCredito(idProgetto: any, idUtente: any): Observable <Array<StatoCreditoVO>> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getVisualizzaSatoCreditoeStorico';
        let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idUtente', idUtente.toString());
        return this.http.get<Array<StatoCreditoVO>>(url, { params: params })
    }

    initDialogEscussione(idStatoEscussione: any): Observable <any> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/initDialogEscussione';
        let params = new HttpParams().set('idStatoEscussione', idStatoEscussione.toString());
        return this.http.get<any>(url, { params: params })
    }

    getStatoCredito(idProgetto: any) : Observable <StatoCreditoVO> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getStatoCredito';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<StatoCreditoVO>(url, { params: params }); 
    }

    getVisualizzaStoricoEscussione(idProgetto = "", codBanca = "", codiceFiscale = "") {
        if (!codBanca) codBanca = ""; // Necessario nel caso in cui codBanca fosse Null
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getVisualizzaStoricoEscussione';
        let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('codBanca', codBanca.toString()).set('codUtente', codiceFiscale.toString());
        return this.http.get<Array<SchedaClienteMain>>(url, { params: params });
    }

    getListaStatiEscussione() {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getListaStatiEscussione';
        return this.http.get<Array<SchedaClienteMain>>(url);
    }

    getListaStatiCredito() {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getListaStatiCredito';
        return this.http.get<Array<SchedaClienteMain>>(url);
    }

    getAllegati(idEscussione = "") {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getAllegati';
        let params = new HttpParams().set('idEscussione', idEscussione.toString());
        return this.http.get<any>(url, { params: params });
    }

    

    /* Non usato da nessuna parte
    cercaDettagliBeneficiari(idProgetto: number) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getVisualizzaDettaglioGaranzia';
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<DettaglioFinanziamentoErogato>(url, { params: params });
    }*/

    jsonModifiche: string;
    jsonModifiche2: string;
    jsonModifiche3: string;
    jsonModifiche4: string;
    jsonModifiche5: string;
    jsonModifiche6: string;
    jsonModifiche7: string;
    jsonModifiche8: string;
    
    updateEscussione(datiBackend, listaAllegatiPresenti: Array<number>) {

        let tempObj: VisualizzaStoricoEscussioneVO = new VisualizzaStoricoEscussioneVO();

        /*
        dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione,[Validators.required]),
                    numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
                    : new FormControl(this.infoEscussione.numProtoNotifica),
                    : new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
                    : new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                    : new FormControl(this.infoEscussione.dataStato, [Validators.required]),
                    : new FormControl(this.infoEscussione.dataNotifica),
                    : new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
                    : new FormControl(this.infoEscussione.importoApprovato),
                    : new FormControl(this.infoEscussione.causaleBonifico),
                    : new FormControl(this.infoEscussione.ibanBancaBenef),
                    : new FormControl(this.infoEscussione.denominazioneBanca),
                    : new FormControl(this.infoEscussione.note),
                     : this.idProgetto,
                     :this.infoEscussione.idEscussione,
                    : this.progrSoggettoProgetto,
                    : this.idBanca,
                    : this.idSoggProgBancaBen,
                    : this.listaAllegatiPresenti
        */

        tempObj.dtRichEscussione = datiBackend.dtRichEscussione;
        tempObj.numProtocolloRich = datiBackend.numProtocolloRich
        tempObj.numProtocolloNotif = datiBackend.numProtocolloNotif
        tempObj.descTipoEscussione = datiBackend.descTipoEscussione
        tempObj.descStatoEscussione = datiBackend.descStatoEscussione
        tempObj.dtInizioValidita = datiBackend.dtInizioValidita
        tempObj.dtNotifica = datiBackend.dtNotifica
        tempObj.impRichiesto = datiBackend.impRichiesto
        tempObj.impApprovato = datiBackend.impApprovato
        tempObj.causaleBonifico = datiBackend.causaleBonifico
        tempObj.ibanBonifico = datiBackend.ibanBonifico
        tempObj.descBanca = datiBackend.descBanca
        tempObj.note = datiBackend.note
        tempObj.idProgetto = datiBackend.idProgetto
        tempObj.idEscussione = datiBackend.idEscussione
        tempObj.ProgrSoggettoProgetto = datiBackend.ProgrSoggettoProgetto
        tempObj.idBanca = datiBackend.idBanca
        tempObj.idSoggProgBancaBen = datiBackend.idSoggProgBancaBen
        tempObj.listaAllegatiPresenti = listaAllegatiPresenti


        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/updateEscussione';
        //let params = new HttpParams().set('listaAllegatiPresenti', listaAllegatiPresenti.toString());
        return this.http.post<any>(url,tempObj)
    }

    

    insertEscussione(datiBackend){
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/insertEscussione';
        return this.http.post<any>(url,datiBackend)
        
    }

    updateStatoCredito(newSchedaCliente: SaveSchedaCliente) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/updateStatoCredito';
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        this.jsonModifiche2 = JSON.stringify(newSchedaCliente);
        return this.http.post<any>(url, this.jsonModifiche2, { headers })
    }

    updateStatoEscussione(datiBackend) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/updateStatoEscussione';
        return this.http.post<any>(url,datiBackend)
    }

    inserisciAllegatiEscussione(allegati, idEscussione: number): Observable<any> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/salvaAllegatiGenerici';
        let params = new HttpParams()
          .set("idEscussione", idEscussione.toString());
        let formData = new FormData();
        if (allegati && allegati.length) {
            for (var i = 0; i < allegati.length; i++) {
              formData.append("file", allegati[i], allegati[i].name);
            }
          }
        return this.http.post<any>(url, formData, { params: params });
    }

    inserisciAllegatoEsito(allegati, idProgetto: number, idEscussione, idStatoEscussione, idTipoEscussione: number): Observable<any> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/salvaEsito';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idEscussione", idEscussione.toString())
            .set("idStatoEscussione", idStatoEscussione.toString())
            .set("idTipoEscussione", idTipoEscussione.toString());

        let formData = new FormData();

        if(allegati.length > 0) {
            formData.append("file", allegati[0], allegati[0].name);
            formData.append("nomeFile", allegati[0].name); 
        }
        

        return this.http.post<any>(url, formData, { params: params });
    }

    inserisciAllegatoIntegrazione(allegati, idProgetto: number, giorniScadenza, idEscussione): Observable<any> { //THIS
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/salvaRichiestaIntELettera';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idEscussione", idEscussione.toString())
            .set("giorniScadenza", giorniScadenza);

        let formData = new FormData();

        formData.append("file", allegati[0], allegati[0].name);
        formData.append("nomeFile", allegati[0].name);

        return this.http.post<any>(url, formData, { params: params });
    }


    updateStatoEscussioneRichIntegrazione(newSchedaCliente: SaveStatoEscussioneGaranzia) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/updateStatoEscussioneRicIntegrazione';
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        this.jsonModifiche6 = JSON.stringify(newSchedaCliente);
        return this.http.post<String>(url, this.jsonModifiche6, { headers });
    }



    // updateStatoEscussioneIntegrazione(newSchedaCliente: SaveStatoEscussioneGaranzia) {
    //     let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/updateStatoEscussioneRicIntegrazione';
    //     let headers = new HttpHeaders().set('Content-Type', 'application/json');
    //     this.jsonModifiche6 = JSON.stringify(newSchedaCliente);
    //     return this.http.post<String>(url, this.jsonModifiche6, { headers })
    // }

    uploadTheFile(file: File, fileName: string, idUtente: string, idProgetto: number): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/salvaUploadAllegato';
        let formData = new FormData();
        formData.append("file", file, file.name);
        formData.append("nomeFile", fileName);
        formData.append("idUtenteIns", idUtente.toString());
        formData.append("idProgetto", idProgetto.toString());
        return this.http.post<boolean>(url, formData);
    }

    

    insertIntegrazione(newSchedaCliente: SaveSchedaCliente) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/insertRichiestaIntegraz';
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        this.jsonModifiche4 = JSON.stringify(newSchedaCliente);
        return this.http.post<String>(url, this.jsonModifiche4, { headers })
    }

    uploadIntegrazione(file: File, fileName: string, idUtente: string, idTarget: number): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/salvaUploadRichiestaIntegraz';
        let formData = new FormData();
        formData.append("file", file, file.name);
        formData.append("nomeFile", fileName);
        formData.append("idUtenteIns", idUtente.toString());
        formData.append("idTarget", idTarget.toString());
        return this.http.post<boolean>(url, formData);
    }

    insertEsito(newSchedaCliente: SaveSchedaCliente) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/insertDistinta';
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        this.jsonModifiche5 = JSON.stringify(newSchedaCliente);
        return this.http.post<String>(url, this.jsonModifiche5, { headers })
    }

    uploadEsito(file: File, fileName: string, idUtente: string, idTarget: number): Observable<any> {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/salvaUploadLettera';
        let formData = new FormData();
        formData.append("file", file, file.name);
        formData.append("nomeFile", fileName);
        formData.append("idUtenteIns", idUtente.toString());
        formData.append("idTarget", idTarget.toString());
        return this.http.post<any>(url, formData);
    }

    deleteAllegato(newSchedaCliente: AllegatoVO) {
        let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/deleteAllegato';
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        this.jsonModifiche8 = JSON.stringify(newSchedaCliente);
        return this.http.post<any>(url, this.jsonModifiche8, { headers });
    }

}