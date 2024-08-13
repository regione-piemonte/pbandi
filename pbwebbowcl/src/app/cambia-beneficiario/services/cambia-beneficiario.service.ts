/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

//Import utili
import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//Import di oggetti
import { Beneficiario } from 'src/app/cambia-beneficiario/commons/dto/beneficiario';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { BeneficiarioDTO } from '../commons/dto/beneficiario-dto';
import { DatiBeneficiarioProgettoDTO } from '../commons/dto/dati-beneficiario-progetto-dto';
import { ResponseCodeMessage } from 'src/app/shared/commons/dto/response-code-message-dto';
import { Progetto } from './../commons/dto/progetto';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';


@Injectable()
export class CambiaBeneficiarioService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }


    //Chiamata al servizio spring cercaBeneficiari
    cercaBeneficiari(user: UserInfoSec, progettoSelezionato: Progetto): Observable<Array<Beneficiario>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercabeneficiari';
        let params = new HttpParams();
        params = params.set("idU", user.idUtente.toString());
        params = params.set("idSoggetto", user.idSoggetto.toString());
        params = params.set("ruolo", user.codiceRuolo);
        if (progettoSelezionato != undefined && progettoSelezionato != null) {

            //COMMENTI TEST
            console.log("[cambia beneficiario service -- cerca beneficiari] \n" +
                "Richiesta inviata al server: \n" +
                "id progetto = " + progettoSelezionato.idProgetto);

            params = params.set("idProgetto", progettoSelezionato.idProgetto.toString());
        }

        return this.http.get<Array<Beneficiario>>(url, { params: params });
    }

    //Chiamata al servizio spring cercaProgetti
    cercaProgetti(user: UserInfoSec, beneficiarioSelezionato: Beneficiario,): Observable<Array<Progetto>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercaProgetti';
        let params = new HttpParams();
        params = params.set("idU", user.idUtente.toString());
        params = params.set("idSoggetto", user.idSoggetto.toString());
        params = params.set("ruolo", user.codiceRuolo);

        //COMMENTI TEST
        console.log("[cambia progetto service -- cerca progetti] \nParametri della chiamata al server \n" +
            "idU = " + user.idUtente.toString() + " // idSoggetto =" + user.idSoggetto.toString() + " // ruolo = " + user.codiceRuolo)

        if (beneficiarioSelezionato != undefined && beneficiarioSelezionato != null) {

            //COMMENTI TEST
            console.log("[cambia progetto service -- cerca progetti] \n" +
                "Beneficiario selezionato = " + beneficiarioSelezionato);

            params = params.set("idSoggettoBeneficiario", beneficiarioSelezionato.id_soggetto.toString());
        }

        return this.http.get<Array<Progetto>>(url, { params: params });
    }

    //Chiamata al servizio spring cercabenprog
    cercabenprog(user: UserInfoSec, beneficiarioSelezionato: Beneficiario, progettoSelezionato: Progetto): Observable<BeneficiarioDTO> {

        let url = this.configService.getApiURL() + '/restfacade/associazione/cercabenprog';
        let params = new HttpParams();
        params = params.set("idU", user.idUtente.toString());
        if (progettoSelezionato) {
            params = params.set("idProgSelez", progettoSelezionato.idProgetto.toString());
            params = params.set("codiceVisualizzato", progettoSelezionato.codiceVisualizzatoProgetto.toString());
        }
        if (beneficiarioSelezionato) {
            params = params.set("idBeneficiario", beneficiarioSelezionato.id_soggetto.toString());
        }
        return this.http.get<BeneficiarioDTO>(url, { params: params });
    }

    //Chiamata al servizio spring cercabenprog restituisce l'oggetto datiBenificiario
    cercabensub(user: UserInfoSec, cfBeneficiarioSubentrante: string, beneficiarioDTO: BeneficiarioDTO): Observable<DatiBeneficiarioProgettoDTO> {

        let url = this.configService.getApiURL() + '/restfacade/associazione/cercabensub';

        //var body : Array<BeneficiarioDTO> = []
        //body.push(beneficiarioDTO)

        let params = new HttpParams();
        params = params.set("idU", user.idUtente.toString());
        params = params.set("beneficiarioSubentrante", cfBeneficiarioSubentrante);

        //COMMENTI TEST
        console.log("[cambia beneficiario service -- cercabensub] \n" +
            "Richiesta inviata al server: \n" +
            "idU = " + user.idUtente.toString() +
            " // codice fiscale BeneficiarioSubentrante = " + cfBeneficiarioSubentrante);

        return this.http.post<DatiBeneficiarioProgettoDTO>(url, beneficiarioDTO, { params: params });

    }

    //Chiamata al servizio spring cambiabeneficiario che modifica il beneficiario e ritona un ResponseCodeMessage
    cambiabeneficiario(idUtente: string, datiBeneficiarioProgettoDTO: DatiBeneficiarioProgettoDTO, isAggiornabile: boolean): Observable<ResponseCodeMessage> {

        let url = this.configService.getApiURL() + '/restfacade/associazione/cambiabeneficiario';

        let params = new HttpParams();
        params = params.set("idU", idUtente);
        isAggiornabile ? params = params.set("isAggiornabile", "true") : params = params.set("isAggiornabile", "false");
        //params = params.set("isAggiornabile", "true"); //non so come parie se tur o false


        //COMMENTI TEST
        console.log("[cambia beneficiario service -- cambiabeneficiario] \n" +
            "Richiesta inviata al server: \n" +
            "idU = " + idUtente +
            " // isAggiornabile = " + isAggiornabile);

        return this.http.post<ResponseCodeMessage>(url, datiBeneficiarioProgettoDTO, { params: params });

    }

    salvaNuovoBeneficiario(idUtente: string, datiBeneficiarioProgettoDTO: DatiBeneficiarioProgettoDTO): Observable<DatiBeneficiarioProgettoDTO> {

        let url = this.configService.getApiURL() + '/restfacade/associazione/salvanuovobeneficiario';

        let params = new HttpParams();
        params = params.set("idU", idUtente);

        //COMMENTI TEST
        console.log("[cambia beneficiario service -- slavaNuovoBeneficiario] \n" +
            "Richiesta inviata al server: \n" +
            "idU = " + idUtente)

        return this.http.post<DatiBeneficiarioProgettoDTO>(url, datiBeneficiarioProgettoDTO, { params: params });

    }

    findRegioni(idUtente: string) {

        let url = this.configService.getApiURL() + '/restfacade/associazione/findregioni';

        let params = new HttpParams();
        params = params.set("idU", idUtente);

        //COMMENTI TEST
        console.log("[cambia beneficiario service -- findRegioni] \n" +
            "Richiesta inviata al server: \n" +
            "idU = " + idUtente)

        return this.http.get<Array<Decodifica>>(url, { params: params });

    }

    findProvince(idUtente: string, idRedione: string) {

        let url = this.configService.getApiURL() + '/restfacade/associazione/findprovince';

        let params = new HttpParams();
        params = params.set("idU", idUtente);
        params = params.set("idComune", idRedione); //penso che qui sia sbagliato dovrebbe essere id regione

        //COMMENTI TEST
        console.log("[cambia beneficiario service -- findProvince] \n" +
            "Richiesta inviata al server: \n" +
            "idU = " + idUtente)

        return this.http.get<Array<Decodifica>>(url, { params: params });

    }

    findComune(idUtente: string, idProvincia: string) {

        let url = this.configService.getApiURL() + '/restfacade/associazione/findcomune';

        let params = new HttpParams();
        params = params.set("idU", idUtente);
        params = params.set("idProvincia", idProvincia);

        //COMMENTI TEST
        console.log("[cambia beneficiario service -- findComune] \n" +
            "Richiesta inviata al server: \n" +
            "idU = " + idUtente)

        return this.http.get<Array<Decodifica>>(url, { params: params });

    }



    /*
        TODO adattare la funzione per il nostro cerca

    cercaPermessi(descrizione: string, codice: string, idUtente: number): Observable<Array<PermessoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercapermessi';
        let params = new HttpParams().set("idU", idUtente.toString());
        if (descrizione) {
            params = params.set("descrizione", descrizione);
        }
        if (codice) {
            params = params.set("codice", codice);
        }
        return this.http.get<Array<PermessoDTO>>(url, { params: params });
    }

    //var body = JSON.stringify({'datiBeneficiarioProgetto': datiBeneficiarioProgettoDTO,'isAggiornabile': true,})


        var body : Array<any> = [] 
        body.push(datiBeneficiarioProgettoDTO)
        body.push(true)

    */
}
