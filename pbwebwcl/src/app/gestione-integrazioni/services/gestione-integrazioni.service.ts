import { Observable } from 'rxjs';
import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { IntegrazioniRevocaDTO } from '../commons/dto/integrazioniRevocaDTO';
import { AllegatiIntegrazioniVO } from '../commons/dto/allegatiIntegrazioniDTO';
import { IntegrazioniRendicontazioneDTO } from '../commons/dto/integrazioniRendicontazioneDTO';
import { AllegatiDichiarazioneSpesaVO } from '../commons/dto/allegatiDichiarazioneSpesaVO';
import { DocumentiSpesaSospesiVO } from '../commons/dto/DocumentiSpesaSospesiVO';
import { AllegatiControlliVO } from '../commons/dto/allegatiControlliDTO';
import { IntegrazioniControlliDTO } from '../commons/dto/integrazioniControlliDTO';
import { AllegatiPrecedentementeVO } from '../commons/allegatiPrecedentementeVO';
import { DocIntegrRendDTO } from '../commons/dto/doc-integr-rend-dto';
import { DocumentoDiSpesaSospesoDTO } from '../commons/dto/documento-di-spesa-sospeso-dto';
import { AllegatiDocSpesaQuietanzeDTO } from '../commons/dto/allegati-doc-spesa-quietanze-dto';
import { QuietanzaDTO } from '../commons/dto/quietanza-dto';

@Injectable()
export class GestioneIntegrazioniService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        @Inject(DOCUMENT) private document: any
    ) { }

    //RENDICONTAZIONE


    getIntegrazioniRendicontazione(idProgetto: number, idBandoLinea: number): Observable<Array<IntegrazioniRendicontazioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getIntegrazioniRendicontazione';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<Array<IntegrazioniRendicontazioneDTO>>(url, { params: params });
    }

    getRegoleIntegrazione(idBandoLinea: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getRegoleIntegrazione';
        let params = new HttpParams().set('idBandoLinea', idBandoLinea.toString());
        return this.http.get<any>(url, { params: params });
    }


    getLetteraAccIntegrazRendicont(idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getLetteraAccIntegrazRendicont';
        let params = new HttpParams().set('idIntegrazione', idIntegrazione.toString());
        return this.http.get<Array<DocIntegrRendDTO>>(url, { params: params });
    }

    getAllegatiIstruttoreRevoche(idRichIntegraz: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiIstruttoreRevoche';
        let params = new HttpParams().set('idRichIntegraz', idRichIntegraz.toString());
        return this.http.get<Array<AllegatiPrecedentementeVO>>(url, { params: params });
    }

    getAllegatiDichiarazioneSpesa(idDichiarazioneSpesa: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiDichiarazioneSpesa';
        let params = new HttpParams().set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString());
        return this.http.get<Array<AllegatiPrecedentementeVO>>(url, { params: params });
    }

    getAllegatiIntegrazioneDS(idDichiarazioneSpesa: number, idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiIntegrazioneDS';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString());
        return this.http.get<Array<AllegatiPrecedentementeVO>>(url, { params: params });
    }

    getAllegatiNuovaIntegrazioneDS(idDichiarazioneSpesa: number, idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiNuovaIntegrazioneDS';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString());
        return this.http.get<Array<AllegatiDichiarazioneSpesaVO>>(url, { params: params });
    }

    getDocumentiDiSpesaSospesi(idDichiarazioneSpesa: number, idProgetto: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getDocumentiDiSpesaSospesi';
        let params = new HttpParams()
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString())
            .set('idProgetto', idProgetto.toString());
        return this.http.get<Array<DocumentoDiSpesaSospesoDTO>>(url, { params: params });
    }

    getDocumentiDiSpesaIntegrati(idProgetto: number, idDichiarazioneSpesa: number, idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getDocumentiDiSpesaIntegrati';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString())
            .set('idProgetto', idProgetto.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }


    getQuietanze(idDocumentoDiSpesa: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getQuietanze';
        let params = new HttpParams().set('idDocumentoSpesa', idDocumentoDiSpesa.toString());
        return this.http.get<Array<QuietanzaDTO>>(url, { params: params });
    }

    getAllegatiQuietanza(idQuietanza: number, idDichiarazioneSpesa: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiQuietanza';
        let params = new HttpParams()
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString())
            .set('idQuietanza', idQuietanza.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }

    getAllegatiNuovaIntegrazioneQuietanza(idQuietanza: number, idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiNuovaIntegrazioneQuietanza';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idQuietanza', idQuietanza.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }

    getAllegatiIntegrazioneQuietanza(idQuietanza: number, idDichiarazioneSpesa: number, idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiIntegrazioneQuietanza';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString())
            .set('idQuietanza', idQuietanza.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }

    getAllegatiIntegrazioneDocS(idDocumentoDiSpesa: number, idDichiarazioneSpesa: number, idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiIntegrazioneDocS';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString())
            .set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }

    getAllegatiNuovaIntegrazioneDocS(idDocumentoDiSpesa: number, idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiNuovaIntegrazioneDocS';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }

    getAllegatiDocumentoSpesaDocS(idDocumentoDiSpesa: number, idDichiarazioneSpesa: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegatiDocumentoSpesa';
        let params = new HttpParams()
            .set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString())
            .set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }


    getReportValidazione(idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getReportValidazione';
        let params = new HttpParams().set('idIntegrazione', idIntegrazione.toString());
        return this.http.get<Array<any>>(url, { params: params });
    }

    deleteAllegati(allegato) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/deleteAllegato`;
        return this.http.post<any>(url, allegato)
    }

    rimuoviAllegato(idFileEntita) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/rimuoviAllegato';
        let params = new HttpParams()
            .set("idFileEntita", idFileEntita.toString())
        return this.http.delete<boolean>(url, { params: params });
    }



    salvaAllegati(allegati) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/salvaAllegati`;
        return this.http.post<any>(url, allegati)
    }

    salvaAllegatiGenerici(allegati: Array<AllegatiDichiarazioneSpesaVO>, idDichiarazioneSpesa: number, idIntegrazioneSpesa: number) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/salvaAllegatiGenerici`;
        let params = new HttpParams().set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString())
            .set('idIntegrazioneSpesa', idIntegrazioneSpesa.toString());
        return this.http.post<any>(url, allegati, { params: params })
    }

    inviaIntegrazioneRendicontazione(idIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/inviaIntegrazioneRendicontazione/${idIntegrazione}`;
        return this.http.post<any>(url, idIntegrazione)

    }

    //INTEGRAZIONE REVOCHE

    getIntegrazioniRevoca(idProgetto: number, idBandoLinea: number): Observable<Array<IntegrazioniRevocaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getIntegrazioniRevoca';
        let params = new HttpParams()
            .set('idProgetto', idProgetto.toString())
            .set('idBandoLinea', idBandoLinea.toString());
        return this.http.get<Array<IntegrazioniRevocaDTO>>(url, { params: params });
    }


    uploadFileIntegrazioni(datiBackend: AllegatiControlliVO[], idRichIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/salvaUploadLetteraAllegatoIntegr/${idRichIntegrazione}`;
        return this.http.post<any>(url, datiBackend)

    }

    aggiornaIntegrazione(datiBackend) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/aggiornaIntegrazione';
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        return this.http.post<any>(url, datiBackend, { headers })

    }

    getAllegatiIntegrazione(idRichIntegraz) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getAllegati';
        let params = new HttpParams().set('idRichIntegraz', idRichIntegraz.toString());
        return this.http.get<any[]>(url, { params: params });
    }

    getLetteraIstruttore(idIntegrazione) {
        let url = this.configService.getApiURL() + '/restfacade/IntegrazioniControlliInLoco/getLetteraIstruttore';
        let params = new HttpParams().set('idIntegrazione', idIntegrazione.toString());
        return this.http.get<any[]>(url, { params: params });
    }


    aggiornaIntegrazioneRevoche(idIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/inviaIntegrazione/${idIntegrazione}`;
        return this.http.post<any>(url, idIntegrazione)

    }

    //Controlli in loco

    getAllegatiControlli(idIntegrazione) {
        let url = this.configService.getApiURL() + '/restfacade/IntegrazioniControlliInLoco/getAllegati';
        let params = new HttpParams().set('idIntegrazione', idIntegrazione.toString());
        return this.http.get<any[]>(url, { params: params });
    }

    uploadFileControlli(datiBackend: AllegatiControlliVO[], idIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/IntegrazioniControlliInLoco/inserisciLetteraAllegato/${idIntegrazione}`;
        return this.http.post<any>(url, datiBackend)

    }

    getIntegrazioniControlli(idProgetto: number): Observable<Array<IntegrazioniRevocaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/IntegrazioniControlliInLoco/getIntegrazioniList';
        let params = new HttpParams()
            .set('idProgetto', idProgetto.toString());
        return this.http.get<Array<IntegrazioniControlliDTO>>(url, { params: params });
    }

    /* aggiornaControlli(idIntegrazione: number): Observable<Array<IntegrazioniRevocaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/IntegrazioniControlliInLoco/aggiornaIntegrazione';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString());
        return this.http.get<Array<IntegrazioniControlliDTO>>(url, { params: params });
    }
 */
    aggiornaControlli(idIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/IntegrazioniControlliInLoco/aggiornaIntegrazione/${idIntegrazione}`;
        return this.http.post<any>(url, idIntegrazione)

    }



    //PROREGHE 

    getInfoProrogheControlli(idIntegrazione: number, idControllo: number, idProgetto: number): Observable<Array<IntegrazioniRevocaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/IntegrazioniControlliInLoco/getAbilitaRichProroga';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString())
            .set('idControllo', idControllo.toString())
            .set('idProgetto', idProgetto.toString());
        return this.http.get<Array<IntegrazioniRevocaDTO>>(url, { params: params });
    }

    getInfoProrogheRevoche(idIntegrazione: number): Observable<Array<IntegrazioniRevocaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getRichProroga';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString());
        return this.http.get<Array<IntegrazioniRevocaDTO>>(url, { params: params });
    }

    getInfoProrogheRendicontazione(idIntegrazione: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/getRichProrogaRendicontazione';
        let params = new HttpParams()
            .set('idIntegrazione', idIntegrazione.toString());
        return this.http.get<Array<IntegrazioniRevocaDTO>>(url, { params: params });

    }


    inserisciRichProrogaControlli(datiBackend, idIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/IntegrazioniControlliInLoco/inserisciRichProroga/${idIntegrazione}`;
        return this.http.post<any>(url, datiBackend)

    }

    inserisciRichProrogaRendicontazione(datiBackend, idIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/inserisciRichProrogaRendicontazione/${idIntegrazione}`;
        return this.http.post<any>(url, datiBackend)

    }

    inserisciRichProrogaRevoche(datiBackend, idIntegrazione: number) {
        let url = this.configService.getApiURL() + `/restfacade/gestioneIntegrazioni/inserisciRichProroga/${idIntegrazione}`;
        return this.http.post<any>(url, datiBackend)

    }

    getAllegatiDocSpesaQuietanzeDaInviare(idIntegrazioneSpesa: number) {
        let url = this.configService.getApiURL() + '/restfacade/gestioneIntegrazioni/allegatiDocSpesaQuietanzeDaInviare';
        let params = new HttpParams().set('idIntegrazioneSpesa', idIntegrazioneSpesa.toString());
        return this.http.get<Array<AllegatiDocSpesaQuietanzeDTO>>(url, { params: params });

    }
}
