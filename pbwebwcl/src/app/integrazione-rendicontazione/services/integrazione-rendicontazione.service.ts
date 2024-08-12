import { Observable } from 'rxjs';
import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { RigaTabRichiesteIntegrazioniDs } from '../commons/dto/riga-tab-richieste-integrazioni-ds';
import { AssociaFilesRequest } from 'src/app/rendicontazione/commons/requests/associa-files-request';
import { EsitoAssociaFilesDTO } from 'src/app/rendicontazione/commons/dto/esito-associa-files-dto';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { InizializzaIntegrazioneDiSpesaDTO } from '../commons/dto/inizializza-integrazione-di-spesa-dto';

@Injectable()
export class IntegrazioneRendicontazioneService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        @Inject(DOCUMENT) private document: any
    ) { }

    inizializzaIntegrazioneDiSpesa(idProgetto: number): Observable<InizializzaIntegrazioneDiSpesaDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/inizializzaIntegrazioneDiSpesa';
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaIntegrazioneDiSpesaDTO>(url, { params: params });
    }

    integrazioniSpesaByIdProgetto(idProgetto: number): Observable<Array<RigaTabRichiesteIntegrazioniDs>> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/integrazioniSpesaByIdProgetto';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<Array<RigaTabRichiesteIntegrazioniDs>>(url, { params: params });
    }

    associaAllegatiAIntegrazioneDiSpesa(request: AssociaFilesRequest): Observable<EsitoAssociaFilesDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/associaAllegatiAIntegrazioneDiSpesa';
        return this.http.post<EsitoAssociaFilesDTO>(url, request);
    }

    disassociaAllegatoIntegrazioneDiSpesa(idProgetto: number, idDocumentoIndex: number, idIntegrazioneDiSpesa: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/disassociaAllegatoIntegrazioneDiSpesa';
        let params = new HttpParams()
            .set('idProgetto', idProgetto.toString())
            .set('idDocumentoIndex', idDocumentoIndex.toString())
            .set('idIntegrazioneDiSpesa', idIntegrazioneDiSpesa.toString());
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    marcaComeDichiarazioneDiIntegrazione(idProgetto: number, idDocumentoIndex: number, idIntegrazioneDiSpesa: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/marcaComeDichiarazioneDiIntegrazione';
        let params = new HttpParams()
            .set('idProgetto', idProgetto.toString())
            .set('idDocumentoIndex', idDocumentoIndex.toString())
            .set('idIntegrazioneDiSpesa', idIntegrazioneDiSpesa.toString());
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    inviaIntegrazioneDiSpesaAIstruttore(idIntegrazioneDiSpesa: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/inviaIntegrazioneDiSpesaAIstruttore';
        let params = new HttpParams().set('idIntegrazioneDiSpesa', idIntegrazioneDiSpesa.toString());
        return this.http.get<EsitoDTO>(url, { params: params });
    }
}
