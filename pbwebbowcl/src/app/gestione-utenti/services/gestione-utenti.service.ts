/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ResponseCodeMessage } from './../../shared/commons/dto/response-code-message-dto';
import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UtenteRicercatoDTO } from '../commons/dto/utente-ricercato-dto';
import { GestioneBackOfficeEsitoGenerico } from '../commons/dto/gestione-back-office-esito-generico';
import { EsitoSalvaUtente } from '../commons/dto/esito-salva-utente';
import { BeneficiarioProgettoAssociatoDTO } from '../commons/dto/beneficiario-progetto-associato-dto';


@Injectable()
export class GestioneUtentiService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    findTipoAnagrafica(user: UserInfoSec): Observable<any> {

        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/findtipoanagrafica';
        let params = new HttpParams();
        params = params.set("idU", user.idUtente.toString());

        //LOG
        console.log("[cambia progetto service -- findTipoAnagrafica] \nChiamata avviata \n")

        return this.http.get<any>(url, { params: params });

    }

    findUtenti(user: UserInfoSec, utenteRicercatoFilter: UtenteRicercatoDTO): Observable<any> {

        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/cercautenti';
        let params = new HttpParams();
        params = params.set("idU", user.idUtente.toString());
        params = params.set("idSoggetto", user.idSoggetto.toString());
        params = params.set("ruolo", user.codiceRuolo);

        //LOG
        console.log("[cambia progetto service -- findUtenti] \nChiamata avviata \n")
        console.log(utenteRicercatoFilter)

        return this.http.post<any>(url, utenteRicercatoFilter, { params: params });
    }

    salvaNuovoUtente(idUser: string, nome: string, cognome: string, codiceFiscale: string, idTipoAnagrafica: string, ruolo: string, idEntiAssociati: Array<string>, email: string): Observable<EsitoSalvaUtente> {

        //let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/salvanuovoUtente';
        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/salvanuovoutente';
        let params = new HttpParams();
        params = params.set("idU", idUser);
        params = params.set("nome", nome);
        params = params.set("cognome", cognome);
        params = params.set("codiceFiscale", codiceFiscale);
        params = params.set("idTipoAnagrafica", idTipoAnagrafica);
        params = params.set("ruolo", ruolo);
        if (email?.length > 0) {
            params = params.set("email", email);
        }

        //LOG
        console.log("idU -> ", idUser, "// nome -> ", nome, "// cognome -> ", cognome, "// codiceFiscale -> ", codiceFiscale, "// idTipoAnagrafica -> ", idTipoAnagrafica, "// ruolo -> ", ruolo, "// idEntiAssociati -> ", idEntiAssociati)

        return this.http.post<EsitoSalvaUtente>(url, idEntiAssociati, { params: params });


    }

    salvaModificaUtente(idUser: string, nome: string, cognome: string, codiceFiscale: string,
        idTipoAnagrafica: string, entiAssociatiStr: Array<string>, email: string): Observable<ResponseCodeMessage> {

        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/salvamodificautente';
        let params = new HttpParams();
        params = params.set("idU", idUser);
        params = params.set("nome", nome);
        params = params.set("cognome", cognome);
        params = params.set("codiceFiscale", codiceFiscale);
        params = params.set("idTipoAnagrafica", idTipoAnagrafica);
        if (email?.length > 0) {
            params = params.set("email", email);
        }

        //LOG
        console.log("get parameters:" + "idU -> ", idUser, "// nome -> ", nome, "// cognome -> ", cognome, "// codiceFiscale -> ", codiceFiscale, "// idTipoAnagrafica -> ", idTipoAnagrafica)
        console.log("get parameters: ", entiAssociatiStr)

        //return this.http.get<ResponseCodeMessage>(url,{params : params});
        return this.http.post<ResponseCodeMessage>(url, entiAssociatiStr, { params: params });


    }

    getEnte(idUser: string, idSoggetto: string, idTipoAnagrafica: string, ruolo: string): Observable<any> {

        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/getenti';
        let params = new HttpParams();

        params = params.set("idU", idUser);
        params = params.set("idSoggetto", idSoggetto);
        params = params.set("idTipoAnagrafica", idTipoAnagrafica);
        params = params.set("ruolo", ruolo);

        //LOG
        console.log("[cambia progetto service -- salvaNuovoUtente] \nChiamata avviata \n")

        return this.http.get<any>(url, { params: params });

    }

    dettaglioUtente(idUser: string, idPersonaFisica: string, idTipoAnagrafica: string, descBreveTipoAnagrafica: string, idSoggetto: string) {

        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/dettaglioutente';

        let params = new HttpParams();
        params = params.set("idU", idUser);
        params = params.set("idPersonaFisica", idPersonaFisica);
        params = params.set("idTipoAnagrafica", idTipoAnagrafica);
        params = params.set("descBreveTipoAnagrafica", descBreveTipoAnagrafica);
        params = params.set("idSoggetto", idSoggetto);

        //LOG
        console.log("[cambia progetto service -- dettaglioUtente] \nChiamata avviata. Parametri: \n")
        console.log("idU: " + idUser + " // idPersonaFisica: " + idPersonaFisica + " // idTipoAnagrafica: " + idTipoAnagrafica + " // idSoggetto: " + idSoggetto)

        return this.http.get<any>(url, { params: params });

    }

    eliminaUtente(idU: string, idSoggetto: string, idTipoAnagraficaSoggetto: string, idPersonaFisica: string): Observable<GestioneBackOfficeEsitoGenerico> {

        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/eliminautente';
        let params = new HttpParams();
        params = params.set("idU", idU)
        params = params.set("idSoggetto", idSoggetto)
        params = params.set("idTipoAnagraficaSoggetto", idTipoAnagraficaSoggetto)
        params = params.set("idPersonaFisica", idPersonaFisica)

        //LOG
        console.log("[cambia progetto service -- eliminaUtente] \nChiamata avviata \n")

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    caricaFile(file: File): Observable<ResponseCodeMessage> {

        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/upload';
        let formData = new FormData();
        formData.append("file", file, file.name);

        //LOG
        console.log("[cambia progetto service -- eliminaUtente] \nChiamata avviata \n")
        console.log(file)

        return this.http.post<ResponseCodeMessage>(url, formData);

    }

    gestioneBeneficiarioProgetto(idU: number, idSoggetto: number): Observable<Array<BeneficiarioProgettoAssociatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/gestioneBeneficiarioProgetto';
        let params = new HttpParams().set("idU", idU.toString()).set("idSoggetto", idSoggetto.toString());
        return this.http.get<Array<BeneficiarioProgettoAssociatoDTO>>(url, { params: params });
    }

    eliminaAssociazioneBeneficiarioProgetto(idU: number, idSoggetto: number, codiceProgettoVisualizzato: string): Observable<GestioneBackOfficeEsitoGenerico> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/eliminabeneficiarioprogetto';
        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idSoggetto", idSoggetto.toString())
            .set("codiceProgettoVisualizzato", codiceProgettoVisualizzato);
        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    aggiungiAssociazioneBeneficiarioProgetto(idU: number, idSoggetto: number, codiceFiscaleBeneficiario: string, codiceVisualizzatoProgetto: string, isRappresentanteLegale: string): Observable<GestioneBackOfficeEsitoGenerico> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/aggiungi';
        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idSoggetto", idSoggetto.toString())
            .set("codiceFiscaleBeneficiario", codiceFiscaleBeneficiario)
            .set("codiceVisualizzatoProgetto", codiceVisualizzatoProgetto)
            .set("isRappresentanteLegale", isRappresentanteLegale);
        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    modificaAssociazioneBeneficiarioProgetto(idU: number, idSoggetto: number, idProgetto: number, codiceFiscaleBeneficiario: string, codiceVisualizzatoProgetto: string, isRappresentanteLegale: string): Observable<GestioneBackOfficeEsitoGenerico> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneutenti/modifica';
        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idSoggetto", idSoggetto.toString())
            .set("idProgetto", idProgetto.toString())
            .set("codiceFiscaleBeneficiario", codiceFiscaleBeneficiario)
            .set("codiceVisualizzatoProgetto", codiceVisualizzatoProgetto)
            .set("isRappresentanteLegale", isRappresentanteLegale);
        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }




}
