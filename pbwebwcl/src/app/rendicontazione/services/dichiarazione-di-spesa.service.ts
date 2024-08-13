/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe, DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { AllegatiDichiarazioneDiSpesaDTO } from '../commons/dto/allegati-dichiarazione-di-spesa-dto';
import { DatiDeclaratoriaDTO } from '../commons/dto/dati-declaratoria-dto';
import { DecodificaDTO } from '../commons/dto/decodifica-dto';
import { DocumentoAllegatoDTO } from '../commons/dto/documento-allegato-dto';
import { EsitoOperazioneInviaDichiarazioneDTO } from '../commons/dto/eisto-operazione-invia-dichiarazione-dto';
import { EsitoAssociaFilesDTO } from '../commons/dto/esito-associa-files-dto';
import { EsitoOperazioneVerificaDichiarazioneSpesa } from '../commons/dto/esito-operazione-verifica-dichiarazione-spesa';
import { InizializzaInvioDichiarazioneDiSpesaDTO } from '../commons/dto/inizializza-invio-dichiarazione-di-spesa-dto';
import { TipoAllegatoDTO } from '../commons/dto/tipo-allegato-dto';
import { AnteprimaDichiarazioneDiSpesaCulturaRequest, AnteprimaDichiarazioneDiSpesaRequest } from '../commons/requests/anteprima-dichiarazione-di-spesa-request';
import { AssociaFilesRequest } from '../commons/requests/associa-files-request';
import { VerificaDichiarazioneDiSpesaRequest } from '../commons/requests/verifica-dichiarazione-di-spesa-request';

@Injectable()
export class DichiarazioneDiSpesaService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private datePipe: DatePipe,
        private handleExceptionService: HandleExceptionService,
        @Inject(DOCUMENT) private document: any
    ) { }

    verificaDichiarazioneDiSpesa(request: VerificaDichiarazioneDiSpesaRequest): Observable<EsitoOperazioneVerificaDichiarazioneSpesa> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/verificaDichiarazioneDiSpesa';
        return this.http.post<EsitoOperazioneVerificaDichiarazioneSpesa>(url, request);
    }


    getIsBeneficiarioPrivatoCittadino(idProgetto: number) {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/getIsBeneficiarioPrivatoCittadino';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<Boolean>(url, { params: params });
    }


    getRappresentantiLegali(idProgetto: number): Observable<Array<DecodificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/rappresentantiLegali';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<Array<DecodificaDTO>>(url, { params: params });
    }

    getDelegati(idProgetto: number): Observable<Array<DecodificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/delegatiCombo';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<Array<DecodificaDTO>>(url, { params: params });
    }

    getIban(idProgetto: number, idSoggettoBeneficiario: number) {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/iban';
        let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idSoggettoBeneficiario', idSoggettoBeneficiario.toString());
        return this.http.get(url, { params: params, responseType: 'text' });
    }

    getTipoAllegati(idProgetto: number, idBandoLinea: number, codiceTipoDichiarazioneDiSpesa: string, idDichiarazione: number): Observable<Array<TipoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/tipoAllegati';
        let params = new HttpParams()
            .set('idProgetto', idProgetto.toString())
            .set('idBandoLinea', idBandoLinea.toString())
            .set('codiceTipoDichiarazioneDiSpesa', codiceTipoDichiarazioneDiSpesa);
        if (idDichiarazione) {
            params = params.set('idDichiarazione', idDichiarazione.toString());
        }
        return this.http.get<Array<TipoAllegatoDTO>>(url, { params: params });
    }

    salvaTipoAllegati(listaTipoAllegati: Array<TipoAllegatoDTO>, codiceTipoDichiarazioneDiSpesa: string): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/salvaTipoAllegati';
        let params = new HttpParams().set('codiceTipoDichiarazioneDiSpesa', codiceTipoDichiarazioneDiSpesa);
        return this.http.post<EsitoDTO>(url, listaTipoAllegati, { params: params });
    }

    inizializzaInvioDichiarazioneDiSpesa(idProgetto: number, idBandoLinea: number): Observable<InizializzaInvioDichiarazioneDiSpesaDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/inizializzaInvioDichiarazioneDiSpesa';
        let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idBandoLinea', idBandoLinea.toString());
        return this.http.get<InizializzaInvioDichiarazioneDiSpesaDTO>(url, { params: params });
    }

    anteprimaDichiarazioneDiSpesa(request: AnteprimaDichiarazioneDiSpesaRequest) {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/anteprimaDichiarazioneDiSpesa';
        return this.http.post(url, request, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
    }

    anteprimaDichiarazioneDiSpesaCultura(request: AnteprimaDichiarazioneDiSpesaCulturaRequest) {
        request.allegatiCultura.strutture = request.allegatiCultura.strutture.filter((struttura) => struttura.direzione && struttura.normativa && struttura.settore)
        request.allegatiCultura.stataleComunitaria = request.allegatiCultura.stataleComunitaria.filter((statale) => statale.programma && statale.struttura)

        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/anteprimaDichiarazioneDiSpesaCultura';
        return this.http.post(url, request, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
    }

    getAllegatiDichiarazioneDiSpesaPerIdProgetto(idProgetto: number, codiceTipoDichiarazioneDiSpesa: string): Observable<Array<DocumentoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/allegatiDichiarazioneDiSpesaPerIdProgetto';
        let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('codiceTipoDichiarazioneDiSpesa', codiceTipoDichiarazioneDiSpesa);
        return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
    }

    getAllegatiDichiarazioneDiSpesa(idProgetto: number, idDichiarazioneDiSpesa: number): Observable<Array<AllegatiDichiarazioneDiSpesaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/allegatiDichiarazioneDiSpesa';
        let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idDichiarazioneDiSpesa', idDichiarazioneDiSpesa.toString());
        return this.http.get<Array<AllegatiDichiarazioneDiSpesaDTO>>(url, { params: params });
    }

    getAllegatiDichiarazioneDiSpesaIntegrazioni(idProgetto: number, idDichiarazioneDiSpesa: number): Observable<Array<AllegatiDichiarazioneDiSpesaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/allegatiDichiarazioneDiSpesaIntegrazioni';
        let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idDichiarazioneDiSpesa', idDichiarazioneDiSpesa.toString());
        return this.http.get<Array<AllegatiDichiarazioneDiSpesaDTO>>(url, { params: params });
    }

    associaAllegatiADichiarazioneDiSpesa(request: AssociaFilesRequest, tipoDichiarazione: string): Observable<EsitoAssociaFilesDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/'
            + (tipoDichiarazione === 'F' ? 'associaAllegatiADichiarazioneDiSpesaFinale' : 'associaAllegatiADichiarazioneDiSpesa');
        return this.http.post<EsitoAssociaFilesDTO>(url, request);
    }

    disassociaAllegatoDichiarazioneDiSpesa(idProgetto: number, idDocumentoIndex: number, idDichiarazioneDiSpesa: number, tipoDichiarazione: string): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/disassociaAllegatoDichiarazioneDiSpesa';
        let params = new HttpParams()
            .set('idProgetto', idProgetto.toString())
            .set('idDocumentoIndex', idDocumentoIndex.toString())
            .set('tipoDichiarazione', tipoDichiarazione);
        if (idDichiarazioneDiSpesa) {
            params = params.set('idDichiarazioneDiSpesa', idDichiarazioneDiSpesa.toString());
        }
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    inviaDichiarazioneDiSpesa(idBandoLinea: number, idProgetto: number, idProgettoContributoPiuGreen: number, idSoggetto: number, dataLimiteDocumentiRendicontabili: Date,
        codiceTipoDichiarazioneDiSpesa: string, idRappresentanteLegale: number, idDelegato: number, fileRelazioneTecnica: File,
        listaTipoAllegati: Array<TipoAllegatoDTO>, idSoggettoBeneficiario: number, importoRichiestaSaldo: number, note: string,
        isBR58: boolean): Observable<EsitoOperazioneInviaDichiarazioneDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/inviaDichiarazioneDiSpesa';
        let formData = new FormData();
        formData.append("idBandoLinea", idBandoLinea.toString());
        formData.append("idProgetto", idProgetto.toString());
        formData.append("idSoggetto", idSoggetto.toString());
        formData.append("dataLimiteDocumentiRendicontabili", this.datePipe.transform(dataLimiteDocumentiRendicontabili, 'dd-MM-yyyy'));
        formData.append("codiceTipoDichiarazioneDiSpesa", codiceTipoDichiarazioneDiSpesa);
        formData.append("idSoggettoBeneficiario", idSoggettoBeneficiario.toString());
        formData.append("isBR58", isBR58.toString());
        if (idProgettoContributoPiuGreen) {
            formData.append("idProgettoContributoPiuGreen", idProgettoContributoPiuGreen.toString());
        }
        if (idRappresentanteLegale) {
            formData.append("idRappresentanteLegale", idRappresentanteLegale.toString());
        }
        if (idDelegato) {
            formData.append("idDelegato", idDelegato.toString());
        }
        if (fileRelazioneTecnica) {
            formData.append("fileRelazioneTecnica", fileRelazioneTecnica, fileRelazioneTecnica.name);
        }
        if (listaTipoAllegati) {
            formData.append('listaTipoAllegati', JSON.stringify(listaTipoAllegati));
        }
        if (importoRichiestaSaldo) {
            formData.append("importoRichiestaSaldo", importoRichiestaSaldo.toString());
        }
        if (note) {
            formData.append("note", note);
        }
        return this.http.post<EsitoOperazioneInviaDichiarazioneDTO>(url, formData);
    }

    inviaDichiarazioneDiSpesaCultura(idBandoLinea: number, idProgetto: number, idProgettoContributoPiuGreen: number, idSoggetto: number, dataLimiteDocumentiRendicontabili: Date,
        codiceTipoDichiarazioneDiSpesa: string, idRappresentanteLegale: number, idDelegato: number, fileRelazioneTecnica: File,
        listaTipoAllegati: Array<TipoAllegatoDTO>, idSoggettoBeneficiario: number, importoRichiestaSaldo: number, note: string,
        isBR58: boolean, allegatiCultura: DatiDeclaratoriaDTO): Observable<EsitoOperazioneInviaDichiarazioneDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/inviaDichiarazioneDiSpesa';
        let formData = new FormData();
        formData.append("idBandoLinea", idBandoLinea.toString());
        formData.append("idProgetto", idProgetto.toString());
        formData.append("idSoggetto", idSoggetto.toString());
        formData.append("dataLimiteDocumentiRendicontabili", this.datePipe.transform(dataLimiteDocumentiRendicontabili, 'dd-MM-yyyy'));
        formData.append("codiceTipoDichiarazioneDiSpesa", codiceTipoDichiarazioneDiSpesa);
        formData.append("idSoggettoBeneficiario", idSoggettoBeneficiario.toString());
        formData.append("isBR58", isBR58.toString());

        allegatiCultura.strutture = allegatiCultura.strutture.filter((struttura) => struttura.direzione && struttura.normativa && struttura.settore)
        allegatiCultura.stataleComunitaria = allegatiCultura.stataleComunitaria.filter((statale) => statale.programma && statale.struttura)
        formData.append("allegatiCultura", JSON.stringify(allegatiCultura));

        if (idProgettoContributoPiuGreen) {
            formData.append("idProgettoContributoPiuGreen", idProgettoContributoPiuGreen.toString());
        }
        if (idRappresentanteLegale) {
            formData.append("idRappresentanteLegale", idRappresentanteLegale.toString());
        }
        if (idDelegato) {
            formData.append("idDelegato", idDelegato.toString());
        }
        if (fileRelazioneTecnica) {
            formData.append("fileRelazioneTecnica", fileRelazioneTecnica, fileRelazioneTecnica.name);
        }
        if (listaTipoAllegati) {
            formData.append('listaTipoAllegati', JSON.stringify(listaTipoAllegati));
        }
        if (importoRichiestaSaldo) {
            formData.append("importoRichiestaSaldo", importoRichiestaSaldo.toString());
        }
        if (note) {
            formData.append("note", note);
        }
        return this.http.post<EsitoOperazioneInviaDichiarazioneDTO>(url, formData);
    }


    salvaInvioCartaceo(idDocumentoIndex: number, invioCartaceo: string): Observable<boolean> { //invio cartaceo: boolean "true" o "false"
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/salvaInvioCartaceo';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString()).set('invioCartaceo', invioCartaceo.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    salvaFileFirmato(idDocumentoIndex: number, fileFirmato: File): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/salvaFileFirmato';
        let formData = new FormData();
        formData.append("idDocumentoIndex", idDocumentoIndex.toString());
        formData.append("fileFirmato", fileFirmato, fileFirmato.name);

        return this.http.post<boolean>(url, formData);
    }


    salvaFileFirmaAutografa(idDocumentoIndex: number, fileFirmato: File): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/salvaFileFirmaAutografa';
        let formData = new FormData();
        formData.append("idDocumentoIndex", idDocumentoIndex.toString());
        formData.append("fileFirmaAutografa", fileFirmato, fileFirmato.name);

        return this.http.post<boolean>(url, formData);
    }

    //verificaFirmaDigitale e verificaFirmaDigitaleReturn, sono uguali, ma nel secondo bisogna aspettare il valore di ritorno

    verificaFirmaDigitale(idDocumentoIndex: number): void {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/verificaFirmaDigitale';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString());
        this.http.get<boolean>(url, { params: params }).subscribe(data => { }, err => {
            if (err.name !== "TimeoutError") {//ignoro l'errore di timeout
                this.handleExceptionService.handleNotBlockingError(err);
            }
        });
        //non ho bisogno del valore di ritorno di questo servizio
        //non devo mostare l'errore di timeout in quanto il servizio è molto lungo
    }

    verificaFirmaDigitaleReturn(idDocumentoIndex: number): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/verificaFirmaDigitale';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    leggiFileFirmaAutografa(apiUrl: string, idDocumentoIndex: string): Observable<any> {
        let url = apiUrl + '/restfacade/archivioFile/leggiFileFirmaAutografa';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString());
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    isBandoCultura(idBandoLinea: number): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/isBandoCultura';
        let params = new HttpParams().set('idBandoLinea', idBandoLinea.toString());
        return this.http.get<boolean>(url, { params: params });
    }
}
