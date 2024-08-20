/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, Inject, Injectable, NgModule } from '@angular/core';
import { MatSnackBar, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { BehaviorSubject, Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ElencoRichieste } from '../commons/dto/elenco-richieste';
import { NuovaRichiesta } from '../commons/dto/nuova-richiesta';
import { Richiesta } from '../commons/dto/richiesta';
import { StoricoRichiesta } from '../commons/dto/storico-richiesta';
import * as FileSaver from 'file-saver'
import { Workbook } from "exceljs";
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';

@Component({
  selector: 'app-richiesta-service',
  templateUrl: './pop-up-success.html',
  styles: [`
    .success{
      color: green;
    }
  `],
})
export class PoPupSuccess {
  constructor() { }

  popup: string;
}

@Component({
  selector: 'app-richiesta-service',
  templateUrl: './pop-up-failed.html',
  styles: [`
    .failed{
      color: red;
    }
  `]
})

export class PoPupFailed {
  constructor(@Inject(MAT_SNACK_BAR_DATA) public tipoPopup: string) { }
}


@Injectable({
  providedIn: 'root'
})
export class RichiesteService {
  
  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
    private snackBar: MatSnackBar,
  ) { }

  getSuggestion(value: string, id: number) {

    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getSuggestion';

    let params = new HttpParams().set("value", value.toString().toUpperCase()).set("id", id.toString());

    return this.http.get<any>(url, { params: params });
  }



  cfFiltrati: Array<string>;
  private cfFiltratiInfo = new BehaviorSubject<Array<string>>(null);
  cfFiltratiInfo$: Observable<Array<string>> = this.cfFiltratiInfo.asObservable();

  getSuggestionCf(cfSoggetto: string) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getCfBeneficiario';
    let params = new HttpParams().set("cfSoggetto", cfSoggetto);
    return this.http.get<Array<string>>(url, { params: params });
  }


  domandeFiltrate: Array<string>;
  private domandeFiltrateInfo = new BehaviorSubject<Array<string>>(null);
  domandeFiltrateInfo$: Observable<Array<string>> = this.domandeFiltrateInfo.asObservable();

  getSuggestionDomanda(numeroDomanda: String) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getDomanda';
    let params = new HttpParams().set("domanda", numeroDomanda.toString());
    return this.http.get<Array<string>>(url, { params: params })
  }

  codiciFondoFiltrati: Array<string>;
  private codiciFondoFiltratiInfo = new BehaviorSubject<Array<string>>(null);
  codiciFondoFiltratiInfo$: Observable<Array<string>> = this.codiciFondoFiltratiInfo.asObservable();

  getSuggestionCodiceFondo(codiceBando: string) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getBando';
    let params = new HttpParams().set("codiceBando", codiceBando.toString());
    return this.http.get<Array<string>>(url, { params: params })
  }

  

  richiedentiFiltrati: Array<string>;
  private richiedentiFiltratiInfo = new BehaviorSubject<Array<string>>(null);
  richiedentiFiltratiInfo$: Observable<Array<string>> = this.richiedentiFiltratiInfo.asObservable();

  getSuggestionRichiedente(richiedente: any) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getRichiedente';
    let params = new HttpParams().set("richiedente", richiedente);
    return this.http.get<Array<string>>(url, { params: params })
  }

  elencoRichieste: ElencoRichieste;
  private elencoRichiesteInfo = new BehaviorSubject<ElencoRichieste>(null);
  elencoRichiesteInfo$: Observable<ElencoRichieste> = this.elencoRichiesteInfo.asObservable();

  getElencoRichieste() {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getRichieste';

    return this.http.get<Array<ElencoRichieste>>(url)
  }

  cercaRichieste(tipoRichiesta?, statoRichiesta?, domanda?, codiceFondo?, richiedente?, ordinamento?, colonna?): Observable<Array<ElencoRichieste>> {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/findRichieste';
    let params = new HttpParams()
      .set("tipoRichiesta", tipoRichiesta)
      .set("statoRichiesta", statoRichiesta)
      .set("numeroDomanda", domanda)
      .set("codiceFondo", codiceFondo)
      .set("richiedente", richiedente)
      .set("ordinamento", ordinamento)
      .set("colonna", colonna)
    return this.http.get<Array<ElencoRichieste>>(url, { params: params });
  }

  storicoRichiesta: StoricoRichiesta;
  private storicoRichiestaInfo = new BehaviorSubject<StoricoRichiesta>(null);
  storicoRichiestaInfo$: Observable<StoricoRichiesta> = this.storicoRichiestaInfo.asObservable();

  getStoricoRichiesta(idRichiesta: any) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getStoricoRichieste';
    let params = new HttpParams().set("idRichiesta", idRichiesta)
    return this.http.get<Array<StoricoRichiesta>>(url, { params: params })
  }

  richiesta: Richiesta;
  private richiestaInfo = new BehaviorSubject<Richiesta>(null);
  richiestaInfo$: Observable<Richiesta> = this.richiestaInfo.asObservable();

  getRichiesta(idRichiesta) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/getRichiesta';
    let params = new HttpParams().set("idRichiesta", idRichiesta)
    return this.http.get<Richiesta>(url, { params: params });
  }


  jsonElabora: string;
  elaboraRichiesta(modifiche: Richiesta) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/elaboraRichiesta';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.jsonElabora = JSON.stringify(modifiche);
    return this.http.post<String>(url, this.jsonElabora, { headers });
  } 

  cancellaRichiesta(modifiche: Richiesta): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/annullaRichiesta';
    let params = new HttpParams();
    return this.http.post<boolean>(url, modifiche, {params:params});    
  }

  jsonElaboraDurc: string
  elaboraDurc(modifica: Richiesta, isDocumento:boolean ) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/elaboraDurc';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams()
    .set("isDocumento", isDocumento.toString())
    this.jsonElaboraDurc = JSON.stringify(modifica);
    return this.http.post<String>(url, this.jsonElaboraDurc, { headers,  params:params  });
  }

  jsonElaboraAntimafia: string
  elaboraAntimafia(modifica: Richiesta, isDocumento:boolean) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/elaboraAntimafia';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
        let params = new HttpParams()
      .set("isDocumento", isDocumento.toString())
    this.jsonElaboraAntimafia = JSON.stringify(modifica);
    return this.http.post<String>(url, this.jsonElaboraAntimafia, { headers, params:params });
  }
  
  annullaRichiestaAntimafia(idRichiestaAntimafia: number): Observable<boolean>{
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/annullaRichiestaAntimafia';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams().set("idRichiestaAntimafia", idRichiestaAntimafia.toString())
    return this.http.get<boolean>(url, {headers,  params:params });
  }

  salvaUploadAntiMafia( file: File, fileName: string, idUtente: Number, idTarget: Number, numeroDomanda: string, nag:any,  richiestaVO: any): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/salvaUploadAntiMafia';
    let formData = new FormData();
    formData.append("file", file, fileName);
    formData.append("idUtente", idUtente.toString());
    formData.append("nomeFile", fileName);
    formData.append("idTarget", idTarget.toString());
    formData.append("numeroDomanda", numeroDomanda);
    formData.append("idSoggetto", nag);
    formData.append('richiesta', JSON.stringify(richiestaVO));
    return this.http.post<boolean>(url, formData);
  }
  salvaUploadDurc( file: File, fileName: string, idUtente: Number, idTarget: Number, numeroDomanda: string, idTipoRichiesta: any, idSoggetto: any, richiestaVO: any): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/salvaUploadDurc';
    let formData = new FormData();
    formData.append("file", file, fileName);
    formData.append("idUtente", idUtente.toString());
    formData.append("nomeFile", fileName);
    formData.append("idTarget", idTarget.toString());
    formData.append("numeroDomanda", numeroDomanda);
    formData.append("idTipoRichiesta", idTipoRichiesta);
    formData.append("idSoggetto", idSoggetto.toString());
    formData.append('richiesta', JSON.stringify(richiestaVO));
    return this.http.post<boolean>(url, formData);
  }
  salvaUploadBdna( file: File, fileName: string, idUtente: Number, idTarget: Number, numeroDomanda: string, idSoggetto: any, richiestaVO: any): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/salvaUploadBdna';
    let formData = new FormData();
    formData.append("file", file, fileName);
    formData.append("idUtente", idUtente.toString());
    formData.append("nomeFile", fileName);
    formData.append("idTarget", idTarget.toString());
    formData.append("numeroDomanda", numeroDomanda);
    formData.append("idSoggetto", idSoggetto.toString());
    formData.append('richiesta', JSON.stringify(richiestaVO));
    return this.http.post<boolean>(url, formData);
  }

  jsonElaboraBdna: string
  elaboraBdna(modificha: Richiesta,  isDocumento:boolean) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/elaboraBdna';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.jsonElaboraBdna = JSON.stringify(modificha);
    let params = new HttpParams()
    .set("isDocumento", isDocumento.toString())
    return this.http.post<String>(url, this.jsonElaboraBdna, { headers,   params:params });
  }


  jsonModifiche: string;
  insertRichiesta(nuovaRichiesta: NuovaRichiesta) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneRichieste/insertNuovaRichiesta';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.jsonModifiche = JSON.stringify(nuovaRichiesta);
    return this.http.post<EsitoDTO>(url, this.jsonModifiche, { headers });
  }

  openSnackBar(check: any) {
    if (check) {
      this.snackBar.openFromComponent(PoPupSuccess, {
        duration: 5000,
      });
    } else {
      this.snackBar.openFromComponent(PoPupFailed, {
        duration: 5000,
        data: {
          tipoPopup: "sortFallito",
        },
      });
    }
  }


  generaExcel(nomeFile: string, richieste: Array<ElencoRichieste>) {
    const header = ['Richiedente', 'Codice Fiscale', 'Tipo richiesta', 'Stato richiesta', 'Data richiesta', 
    'Codice bando', 'Numero domanda','Codice progetto'];
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
    richieste.map(p => {
        data.push([p.richiedente, p.codiceFiscale, p.tipoRichiesta,p.statoRichiesta, p.dataRichiesta, p.codiceBando, p.numeroDomanda, p.codiceProgetto]);
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

