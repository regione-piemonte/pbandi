import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { DatiColonnaQteSalDTO } from '../commons/dto/dati-colonna-qte-sal-dto';
import { DecodificaDTO } from '../commons/dto/decodifica-dto';
import { FornitoreComboDTO } from '../commons/dto/fornitore-combo-dto';
import { InizializzaRendicontazioneDTO } from '../commons/dto/inizializza-rendicontazione-dto';
import { SalCorrenteDTO } from '../commons/dto/sal-corrente-dto';
import { TipologiaDocumentoSpesaVo } from '../commons/vo/tipologia-documento-spesa-vo';
import { UserInfoSec } from './../../core/commons/dto/user-info';

@Injectable()
export class RendicontazioneService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        @Inject(DOCUMENT) private document: any
    ) { }

    findTipologieDocumentiDiSpesa(idBandoLinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/tipologieDocumento';

        let params = new HttpParams()
            .set("idBandoLinea", idBandoLinea);

        return this.http.get<Array<TipologiaDocumentoSpesaVo>>(url, { params: params });
    }

    findTipologieDocumentiDiSpesaCultura(idBandoLinea: string, idProgetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/decodificheCultura/tipologieDocumento';

        let params = new HttpParams()
            .set("idBandoLinea", idBandoLinea)
            .set("idProgetto", idProgetto);

        return this.http.get<Array<TipologiaDocumentoSpesaVo>>(url, { params: params });
    }

    findTipologieFornitore(): Observable<Array<DecodificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/tipologieFornitore';
        return this.http.get<Array<DecodificaDTO>>(url);
    }

    findTipologieFornitoreByIdTipoDocSpesa(idTipoDoc: any) {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/tipologieFornitorePerIdTipoDocSpesa';

        let params = new HttpParams()
            .set("idTipoDocumentoDiSpesa", idTipoDoc)

        return this.http.get<Array<DecodificaDTO>>(url, { params: params });
    }

    findFornitoriCombo(idSoggettoFornitore: number, idTipoFornitore: number, idFornitore: number) {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/fornitoriCombo';

        let params = new HttpParams()
            .set("idSoggettoFornitore", idSoggettoFornitore.toString())
            .set("idTipoFornitore", idTipoFornitore.toString());
        if (idFornitore) {
            params = params.set("idFornitore", idFornitore.toString());
        }
        return this.http.get<Array<FornitoreComboDTO>>(url, { params: params });
    }

    getAttivita(idUtente: number): Observable<Array<DecodificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/attivitaCombo';
        let params = new HttpParams().set("idUtente", idUtente.toString());
        return this.http.get<Array<DecodificaDTO>>(url, { params: params });
    }

    inizializzaRendicontazione(idProgetto: number, idBandoLinea: number, idSoggetto: number, codiceRuolo: string) {
        let url = this.configService.getApiURL() + '/restfacade/rendicontazione/inizializzaRendicontazione';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idBandoLinea", idBandoLinea.toString())
            .set("idSoggetto", idSoggetto.toString())
            .set("codiceRuolo", codiceRuolo);
        return this.http.get<InizializzaRendicontazioneDTO>(url, { params: params });
    }

    getElencoTask(userInfo: UserInfoSec, idProgetto: number) {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/elencoTask';

        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idUtente", userInfo.idUtente.toString())

        return this.http.get<Array<string>>(url, { params: params });
    }

    getQualifiche(userInfo: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/qualifiche';

        let params = new HttpParams()
            .set("idUtente", userInfo.idUtente.toString())

        return this.http.get<Array<DecodificaDTO>>(url, { params: params });
    }

    getTipologieFormaGiuridica(flagPrivato: string): Observable<Array<DecodificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/tipologieFormaGiuridica';
        let params = new HttpParams().set("flagPrivato", flagPrivato);
        return this.http.get<Array<DecodificaDTO>>(url, { params: params });
    }

    getStatiDocumentoDiSpesa(): Observable<Array<DecodificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/statiDocumentoDiSpesa';
        return this.http.get<Array<DecodificaDTO>>(url);
    }

    getFornitoriComboRicerca(idProgetto: number): Observable<Array<DecodificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/decodifiche/fornitoriComboRicerca';
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<Array<DecodificaDTO>>(url, { params: params });
    }

    getSALCorrente(idProgetto: number): Observable<SalCorrenteDTO> {
        let url = this.configService.getApiURL() + '/restfacade/rendicontazione/sal';
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<SalCorrenteDTO>(url, { params: params });
    }

    getSALByIdDocSpesa(idProgetto: number, idDocumentoSpesa: number): Observable<SalCorrenteDTO> {
        let url = this.configService.getApiURL() + '/restfacade/rendicontazione/salByIdDocSpesa';
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idDocumentoSpesa", idDocumentoSpesa.toString());
        return this.http.get<SalCorrenteDTO>(url, { params: params });
    }

    getDatiColonneQteSALCorrente(idProgetto: number, idIter: number): Observable<Array<DatiColonnaQteSalDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/rendicontazione/colonneQteSal';
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idIter", idIter.toString());
        return this.http.get<Array<DatiColonnaQteSalDTO>>(url, { params: params });
    }
}
