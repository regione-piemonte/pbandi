import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { BandoLinea } from '../commons/dto/bando-linea';
import { BeneficiarioQuoteDTO } from '../commons/dto/beneficiario-quote-dto';
import { DatiGeneraliDTO } from '../commons/dto/dati-generali-dto';
import { EconomiaDTO } from '../commons/dto/economia-dto';
import { EsitoOperazioneEconomia } from '../commons/dto/esito-operazione-economia';
import { ModificaDTO } from '../commons/dto/modifica-dto';
import { ParametriRicercaEconomieDTO } from '../commons/dto/parametri-ricerca-economia-dto';
import { ProgettoDTO } from '../commons/dto/progetto-dto';

@Injectable()
export class GestioneEconomieService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    getEconomie(idU: number, request: ParametriRicercaEconomieDTO): Observable<Array<EconomiaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/geteconomie';
        let params = new HttpParams().set("idU", idU.toString());
        return this.http.post<Array<EconomiaDTO>>(url, request, { params: params });
    }

    deleteEconomia(idU: number, idEconomia: number): Observable<EsitoOperazioneEconomia> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/deleteeconomia';
        let params = new HttpParams().set("idU", idU.toString()).set("idEconomia", idEconomia.toString());
        return this.http.get<EsitoOperazioneEconomia>(url, { params: params });
    }

    modifica(idU: number, economiaDTO: EconomiaDTO): Observable<ModificaDTO> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/modifica';
        let params = new HttpParams().set("idU", idU.toString());
        return this.http.post<ModificaDTO>(url, economiaDTO, { params: params });
    }

    getBandi(idU: number): Observable<Array<BandoLinea>> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/getbandi';
        let params = new HttpParams().set("idU", idU.toString());
        return this.http.get<Array<BandoLinea>>(url, { params: params });
    }

    getProgettiByBando(idU: number, idBandoLinea: number): Observable<Array<ProgettoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/getprogettibybando';
        let params = new HttpParams().set("idU", idU.toString()).set("idBandoLinea", idBandoLinea.toString());
        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    cambiaProgetto(idU: number, idProgetto: number, idEconomia: number, idSoggettoBeneficiario: number, tipologiaProgetto: string): Observable<BeneficiarioQuoteDTO> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/cambiaprogetto';
        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idProgetto", idProgetto.toString())
            .set("tipologiaProgetto", tipologiaProgetto.toString());
        if (idEconomia) {
            params = params.set("idEconomia", idEconomia.toString());
        }
        if (idSoggettoBeneficiario) {
            params = params.set("idSoggettoBeneficiario", idSoggettoBeneficiario.toString());
        }
        return this.http.get<BeneficiarioQuoteDTO>(url, { params: params });
    }

    reportDettaglioEconomia(idU: number, request: ParametriRicercaEconomieDTO) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/reportDettaglioEconomia';
        let params = new HttpParams().set("idU", idU.toString());
        return this.http.post(url, request, { params: params, responseType: 'blob', observe: 'response' });
    }

    salvaAggiornamentiEconomia(idU: number, request: ModificaDTO): Observable<EsitoOperazioneEconomia> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/salvaaggiornamentieconomia';
        let params = new HttpParams().set("idU", idU.toString());
        return this.http.post<EsitoOperazioneEconomia>(url, request, { params: params });
    }

    salvaNuovaEconomia(idU: number, request: ModificaDTO): Observable<EsitoOperazioneEconomia> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/salvanuovaeconomia';
        let params = new HttpParams().set("idU", idU.toString());
        return this.http.post<EsitoOperazioneEconomia>(url, request, { params: params });
    }

    infoProgetto(idU: number, idProgetto: number): Observable<DatiGeneraliDTO> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneeconomie/infoProgetto';
        let params = new HttpParams().set("idU", idU.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<DatiGeneraliDTO>(url, { params: params });
    }
}
