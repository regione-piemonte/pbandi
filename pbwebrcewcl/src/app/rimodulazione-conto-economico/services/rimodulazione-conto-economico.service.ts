/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleExceptionService } from '@pbandi/common-lib';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DocumentoAllegato } from 'src/app/affidamenti/commons/dto/documento-allegato';
import { ConfigService } from 'src/app/core/services/config.service';
import { EsitoAssociaFilesDTO } from 'src/app/shared/commons/dto/esito-associa-files-dto';
import { EsitoOperazioni } from 'src/app/shared/commons/dto/esito-operazioni';
import { AssociaFilesRequest } from 'src/app/shared/commons/requests/associa-files-request';
import { EsitoInviaPropostaRimodulazioneDTO } from '../commons/dto/esito-invia-proposta-rimodulazione-dto';
import { EsitoSalvaRimodulazioneConfermataDTO } from '../commons/dto/esito-salva-rimodulazione-confermata-dto';
import { EsitoSalvaPropostaRimodulazioneDTO, EsitoSalvaRimodulazioneDTO } from '../commons/dto/esito-salva-rimodulazione-dto';
import { EsitoValidaRimodulazioneConfermataDTO } from '../commons/dto/esito-valida-rimodulazione-confermata-dto';
import { InizializzaConcludiRichiestaContoEconomicoDTO } from '../commons/dto/inizializza-concludi-conto-economico-dto';
import { InizializzaConcludiPropostaRimodulazioneDTO } from '../commons/dto/inizializza-concludi-proposta-rimodulazione-dto';
import { InizializzaConcludiRimodulazioneDTO } from '../commons/dto/inizializza-concludi-rimodulazione-dto';
import { InizializzaPropostaRimodulazioneDTO } from '../commons/dto/inizializza-proposta-rimodulazione-dto';
import { InizializzaRimodulazioneDTO } from '../commons/dto/inizializza-rimodulazione-dto';
import { InizializzaRimodulazioneIstruttoriaDTO } from '../commons/dto/inizializza-rimodulazione-istruttoria-dto';
import { InizializzaUploadFileFirmatoDTO } from '../commons/dto/inizializza-upload-file-firmato-dto';
import { InviaPropostaRimodulazioneRequest } from '../commons/request/invia-proposta-rimodulazione-request';
import { SalvaRichiestaContoEconomicoRequest } from '../commons/request/salva-richiesta-conto-economico-request';
import { SalvaRimodulazioneConfermataRequest } from '../commons/request/salva-rimodulazione-confermata-request';
import { SalvaPropostaRimodulazioneRequest, SalvaRimodulazioneRequest } from '../commons/request/salva-rimodulazione-request';
import { ValidaRimodulazioneConfermataRequest } from '../commons/request/valida-rimodulazione-confermata-request';
import { AltriCostiDTO } from '../commons/dto/altri-costi-dto';
import { TipoAllegatoDTO } from '../commons/dto/tipo-allegato-dto';

@Injectable()
export class RimodulazioneContoEconomicoService {
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService
    ) { }

    inizializzaPropostaRimodulazione(idProgetto: number): Observable<InizializzaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaPropostaRimodulazione";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaPropostaRimodulazioneDTO>(url, { params: params });
    }

    contoEconomicoLocked(idProgetto: number): Observable<boolean> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/contoEconomicoLocked";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    salvaPropostaRimodulazione(request: SalvaPropostaRimodulazioneRequest): Observable<EsitoSalvaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaPropostaRimodulazione";
        return this.http.post<EsitoSalvaPropostaRimodulazioneDTO>(url, request);
    }

    salvaPropostaRimodulazioneConfermata(request: SalvaPropostaRimodulazioneRequest): Observable<EsitoSalvaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaPropostaRimodulazioneConfermata";
        return this.http.post<EsitoSalvaPropostaRimodulazioneDTO>(url, request);
    }

    inizializzaConcludiPropostaRimodulazione(idProgetto: number): Observable<InizializzaConcludiPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaConcludiPropostaRimodulazione";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaConcludiPropostaRimodulazioneDTO>(url, { params: params });
    }

    associaAllegatiAPropostaRimodulazione(request: AssociaFilesRequest): Observable<EsitoAssociaFilesDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/associaAllegatiAPropostaRimodulazione";
        return this.http.post<EsitoAssociaFilesDTO>(url, request);
    }

    disassociaAllegatoPropostaRimodulazione(idDocumentoIndex: number, idProgetto: number): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/disassociaAllegatoPropostaRimodulazione";
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<EsitoOperazioni>(url, { params: params });
    }

    allegatiPropostaRimodulazione(idProgetto: number): Observable<Array<DocumentoAllegato>> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/allegatiPropostaRimodulazione";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<Array<DocumentoAllegato>>(url, { params: params });
    }

    inviaPropostaRimodulazione(request: InviaPropostaRimodulazioneRequest): Observable<EsitoInviaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/inviaPropostaRimodulazione";
        return this.http.post<EsitoInviaPropostaRimodulazioneDTO>(url, request);
    }

    inizializzaUploadFileFirmato(idProgetto: Number): Observable<InizializzaUploadFileFirmatoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/contoEconomico/inizializzaUploadFileFirmato';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<InizializzaUploadFileFirmatoDTO>(url, { params: params });
    }

    salvaInvioCartaceo(idDocumentoIndex: number, invioCartaceo: string): Observable<boolean> { //invio cartaceo: boolean "true" o "false"
        let url = this.configService.getApiURL() + '/restfacade/contoEconomico/salvaInvioCartaceo';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString()).set('invioCartaceo', invioCartaceo.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    salvaFileFirmato(idDocumentoIndex: number, fileFirmato: File): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/contoEconomico/salvaFileFirmato';
        let formData = new FormData();
        formData.append("idDocumentoIndex", idDocumentoIndex.toString());
        formData.append("fileFirmato", fileFirmato, fileFirmato.name);

        return this.http.post<boolean>(url, formData);
    }

    verificaFirmaDigitale(idDocumentoIndex: number): void {
        let url = this.configService.getApiURL() + '/restfacade/contoEconomico/verificaFirmaDigitale';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString());
        this.http.get<boolean>(url, { params: params }).subscribe(data => { }, err => {
            if (err.name !== "TimeoutError") {//ignoro l'errore di timeout
                this.handleExceptionService.handleNotBlockingError(err);
            }
        });
        //non ho bisogno del valore di ritorno di questo servizio
        //non devo mostare l'errore di timeout in quanto il servizio è molto lungo
    }

    inizializzaRimodulazione(idProgetto: number): Observable<InizializzaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaRimodulazione";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaRimodulazioneDTO>(url, { params: params });
    }

    salvaRimodulazione(request: SalvaRimodulazioneRequest): Observable<EsitoSalvaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaRimodulazione";
        return this.http.post<EsitoSalvaRimodulazioneDTO>(url, request);
    }

    inizializzaConcludiRimodulazione(idProgetto: number, isIstruttoria: boolean, idBandoLinea: number): Observable<InizializzaConcludiRimodulazioneDTO> {
        let url: string
        if (isIstruttoria) {
            url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaConcludiRimodulazioneIstruttoria";
        } else {
            url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaConcludiRimodulazione";
        }
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idBandoLinea", idBandoLinea.toString());
        return this.http.get<InizializzaConcludiRimodulazioneDTO>(url, { params: params });
    }

    salvaRimodulazioneConfermata(request: SalvaRimodulazioneConfermataRequest, isIstruttoria: boolean): Observable<EsitoSalvaRimodulazioneConfermataDTO> {
        let url: string
        if (isIstruttoria) {
            url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaRimodulazioneIstruttoriaConfermata";
        } else {
            url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaRimodulazioneConfermata";
        }
        return this.http.post<EsitoSalvaRimodulazioneConfermataDTO>(url, request);
    }

    validaRimodulazioneConfermata(request: ValidaRimodulazioneConfermataRequest): Observable<EsitoValidaRimodulazioneConfermataDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/validaRimodulazioneConfermata";
        return this.http.post<EsitoValidaRimodulazioneConfermataDTO>(url, request);
    }

    scaricaRimodulazione(idProgetto: number, idSoggettoBeneficiario: number, idContoEconomico: number) {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/scaricaRimodulazione";
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idSoggettoBeneficiario", idSoggettoBeneficiario.toString())
            .set("idContoEconomico", idContoEconomico.toString());
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    inizializzaRimodulazioneIstruttoria(idProgetto: number): Observable<InizializzaRimodulazioneIstruttoriaDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaRimodulazioneIstruttoria";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaRimodulazioneIstruttoriaDTO>(url, { params: params });
    }

    validaRimodulazioneIstruttoria(request: SalvaRimodulazioneRequest): Observable<EsitoSalvaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/validaRimodulazioneIstruttoria";
        return this.http.post<EsitoSalvaRimodulazioneDTO>(url, request);
    }

    salvaRimodulazioneIstruttoria(request: SalvaRimodulazioneRequest): Observable<EsitoSalvaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaRimodulazioneIstruttoria";
        return this.http.post<EsitoSalvaRimodulazioneDTO>(url, request);
    }

    inizializzaPropostaRimodulazioneInDomanda(idProgetto: number): Observable<InizializzaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaPropostaRimodulazioneInDomanda";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaPropostaRimodulazioneDTO>(url, { params: params });
    }

    validaPropostaRimodulazioneInDomanda(request: SalvaPropostaRimodulazioneRequest): Observable<EsitoSalvaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/validaPropostaRimodulazioneInDomanda";
        return this.http.post<EsitoSalvaPropostaRimodulazioneDTO>(url, request);
    }

    salvaPropostaRimodulazioneInDomanda(request: SalvaPropostaRimodulazioneRequest): Observable<EsitoSalvaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaPropostaRimodulazioneInDomanda";
        return this.http.post<EsitoSalvaPropostaRimodulazioneDTO>(url, request);
    }

    inizializzaConcludiRichiestaContoEconomico(idProgetto: number): Observable<InizializzaConcludiRichiestaContoEconomicoDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/inizializzaConcludiRichiestaContoEconomico";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaConcludiRichiestaContoEconomicoDTO>(url, { params: params });
    }

    salvaRichiestaContoEconomico(request: SalvaRichiestaContoEconomicoRequest): Observable<EsitoSalvaPropostaRimodulazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/salvaRichiestaContoEconomico";
        return this.http.post<EsitoSalvaPropostaRimodulazioneDTO>(url, request);
    }

    getAltriCosti(idBandoLinea: number, idProgetto: number): Observable<Array<AltriCostiDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/getAltriCosti";
        let params = new HttpParams().set("idBandoLinea", idBandoLinea.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<Array<AltriCostiDTO>>(url, { params: params });
    }

    getTipiAllegatiProposta(idBandoLinea: number, idProgetto: number): Observable<Array<TipoAllegatoDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/contoEconomico/getTipiAllegatiProposta";
        let params = new HttpParams().set("idBandoLinea", idBandoLinea.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<Array<TipoAllegatoDTO>>(url, { params: params });
    }

    getEntrate(idProgetto: any): any {
		let url = this.configService.getApiURL() + '/restfacade/contoEconomico/vociDiEntrataPerRimodulazione';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<any>(url, { params: params });
	}
    
    getSpesa(idProgetto: any): any {
		let url = this.configService.getApiURL() + '/restfacade/contoEconomico/vociDiSpesaCultura';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<any>(url, { params: params });
	}
}
