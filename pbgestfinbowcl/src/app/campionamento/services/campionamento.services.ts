/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { AttivitaDTO } from "src/app/gestione-crediti/commons/dto/attivita-dto";
import { StoricoRicercaCampionamentiDTO } from "../commons/dto/StoricoRicercaCampionamentiDTO";
import { RicercaCampionamentiVO } from "../commons/dto/RicercaCampionamentiVO";
import { NuovoCampionamentoVO } from "../commons/vo/NuovoCampionamentoVO";
import { ProgettoEstrattoVO } from "../commons/vo/ProgettoEstrattoVO";
import { ProgettiEstrattiEsclusiDTO } from "../commons/dto/ProgettiEstratiEsclusiDTO";
import { Workbook } from "exceljs";
import * as FileSaver from 'file-saver-es'

@Injectable()
export class CampionamentoService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    getElencoCampionamenti(ricercaCampio: RicercaCampionamentiVO)
        : Observable<Array<StoricoRicercaCampionamentiDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/ricerCampionamenti/getListaCampionamenti';
        let params = new HttpParams();
        return this.http.post<Array<StoricoRicercaCampionamentiDTO>>(url, ricercaCampio, { params: params });
    }
    getListaTipologie(): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/ricerCampionamenti/getListaTipologie';
        return this.http.get<Array<AttivitaDTO>>(url);
    }

    getListaStati(): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/ricerCampionamenti/getListaStati';
        return this.http.get<Array<AttivitaDTO>>(url);
    }

    getListaUtenti(string: String): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/ricerCampionamenti/getListaUtenti';
        let params = new HttpParams().set("string", string.toString());
        return this.http.get<Array<AttivitaDTO>>(url, { params: params });
    }
    getListaBandi(string: String): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/ricerCampionamenti/getListaBandi';
        let params = new HttpParams().set("string", string.toString());
        return this.http.get<Array<AttivitaDTO>>(url, { params: params });
    }

    getTipoDichiaraziSpesa(): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/ricerCampionamenti/tipologieDichiaSpesa';
        return this.http.get<Array<AttivitaDTO>>(url);
    }
    elaboraCampionamento(nuovoCampionamentoVO: NuovoCampionamentoVO): Observable<Array<ProgettoEstrattoVO>> {

        let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/elaborazione';
        let params = new HttpParams();
        return this.http.post<Array<ProgettoEstrattoVO>>(url, nuovoCampionamentoVO, { params: params });
    }
    eseguiEstrazione(progetti: ProgettiEstrattiEsclusiDTO, idUtente: number): Observable<boolean> {

        let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/estrazione';
        let params = new HttpParams().set("idUtente", idUtente.toString());
        return this.http.post<boolean>(url, progetti, { params: params });
    }
    campionaProgetti(nuovoCampionamentoVO: NuovoCampionamentoVO, idUtente: any): Observable<Array<ProgettoEstrattoVO>> {
        let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/campiona';
        let params = new HttpParams().set("idUtente", idUtente.toString());
        return this.http.post<Array<ProgettoEstrattoVO>>(url, nuovoCampionamentoVO, { params: params });
    }

    getImportoTotale(nuovoCampionamentoVO: NuovoCampionamentoVO): Observable<string> {
        let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/importoTotale';
        let params = new HttpParams();
        return this.http.post<string>(url, nuovoCampionamentoVO, { params: params });
    }
    creaControlloLoco(progetti: ProgettiEstrattiEsclusiDTO, idUtente: number): Observable<boolean> {

        let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/creaControlloLoco';
        let params = new HttpParams().set("idUtente", idUtente.toString());
        return this.http.post<boolean>(url, progetti, { params: params });
    }
    annullaCampionamento(idCampionamento: number, idUtente: number): Observable<boolean> {

        let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/annullaCampionamento';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idCampionamento", idCampionamento.toString());
        return this.http.get<boolean>(url, { params: params });
    }
    generaExcelPropostaProgetto(nomeFile: string, elencoProgetti: Array<ProgettoEstrattoVO>) {
        const header = ['Bando', 'Progetto', 'Forma giuridica', 'Codice fiscale', 'Comune sede legale', 'Provincia sede legale', 'Comune sede interv.',
            'Provincia sede interv'];
        // Crea workbook and worksheet
        const workbook = new Workbook();
        const worksheet = workbook.addWorksheet('Sheet1');
        // Add Header Row
        const headerRow = worksheet.addRow(header);
        // Cell Style : Fill and Border
        headerRow.eachCell((cell, number) => {
            cell.fill = {
                type: 'pattern',
                pattern: 'solid',
                fgColor: { argb: 'CECECE' },
            };
            cell.border = { top: { style: 'thin' }, left: { style: 'thin' }, bottom: { style: 'thin' }, right: { style: 'thin' } };
        });

        const data = new Array<any>();
        elencoProgetti.map(p => {
            data.push([p.descBando, p.codVisualizzato, p.descFormaGiuridica,p.codiceFiscaleSoggetto, p.comuneSedeLegale, p.provSedeLegale, p.comuneSedeIntervento, p.provSedeIntervento]);
        })
        worksheet.addRows(data);

        let fileName: string = nomeFile;
        // Generate il file Excel
        workbook.xlsx.writeBuffer().then((data: any) => {
            const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            FileSaver.saveAs(blob, fileName + '.xlsx');
        });
    }
}