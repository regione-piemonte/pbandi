/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { parseI18nMeta } from '@angular/compiler/src/render3/view/i18n/meta';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { DettaglioImpresa } from '../commons/dettaglioImpresa';
import { AltriDatiDsan } from '../commons/dto/altri-dati-dsan';
import { AnagAltriDati_Main } from '../commons/dto/anagAltriDati_Main';
import { AnagraficaBeneficiarioSec } from '../commons/dto/anagrafica-beneficiario';
import { AnagraficaBeneFisico } from '../commons/dto/anagraficaBeneFisico';
import { AtecoDTO } from '../commons/dto/atecoDTO';
import { AtlanteVO } from '../commons/dto/atlante-vo';
import { BeneficiarioSoggCorrPF } from '../commons/dto/beneficiario-sogg-corr-PF';
import { BloccoVO } from '../commons/dto/bloccoVO';
import { DatiDimensioneImpresaVO } from '../commons/dto/data-dimensione-impresa-vo';
import { DatiAreaCreditiSoggetto } from '../commons/dto/datiSoggettoAreaCreditiVO';
import { ElencoDomandeProgettiSec } from '../commons/dto/elenco-domande-progetti';
import { RuoloDTO } from '../commons/dto/ruoloDTO';
import { SezioneSpecialeDTO } from '../commons/dto/sezioneSpecialeDTO';
import { SoggettoGiuridicoIndipendenteDomanda } from '../commons/dto/soggettoGiuridicoIndipendenteDomanda';
import { statoDomanda } from '../commons/dto/statoDomanda';
import * as FileSaver from 'file-saver'
import { Workbook } from "exceljs";

@Injectable({
  providedIn: 'root'
})
export class AnagraficaBeneficiarioService {


  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
  ) { }


  // anagBeneficiario: AnagraficaBeneficiarioSec;
  // private anagBeneficiarioInfo = new BehaviorSubject<AnagraficaBeneficiarioSec>(null);
  // anagBeneficiarioInfo$: Observable<AnagraficaBeneficiarioSec> = this.anagBeneficiarioInfo.asObservable();


  getAnagBeneficiario(idSoggetto: any, idProgetto: any, numeroDomanda: string): Observable<any> {

    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getAnagBeneficiario';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())
      .set("idProgetto", (idProgetto != null) ? idProgetto.toString() : 0)
      .set("idDomanda", (numeroDomanda != null) ? numeroDomanda.toString() : null);

    return this.http.get<any>(url, { params: params });

  }

  getDettaglioImpresa(idSoggetto: any, dataValEsito: any) {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getDettaglioImpresa';

    let params = new HttpParams()
      .set("idSoggetto", idSoggetto.toString())
      .set("anno", (dataValEsito != null) ? dataValEsito.toString() : null);

    return this.http.get<any>(url, { params: params })
  }

  /*getDimensioneImpresa(idSoggetto: any, numeroDomanda: any) {

    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getDimensioneImpresa';

    let params = new HttpParams()
    .set("idSoggetto", idSoggetto.toString())
    .set("numeroDomanda", numeroDomanda.toString())
    ;

    return this.http.get<any>(url, { params: params })

  }*/

  /*getDurc(idSoggetto: any) {

    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getDurc';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString());

    return this.http.get<any>(url, { params: params })
  }*/

  getAltriDati(idSoggetto: any = 0, numeroDomanda: any = 0, idEnteGiuridico: any = 0) {

    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getAltriDati';

    let params = new HttpParams()
      .set("idSoggetto", idSoggetto.toString())
      .set("numeroDomanda", numeroDomanda.toString());
    if (idEnteGiuridico != null) {
      params = params.set("idEnteGiuridico", idEnteGiuridico.toString());
    }

    return this.http.get<AnagAltriDati_Main>(url, { params: params })
  }

  anagBeneFisico: AnagraficaBeneFisico;
  private anagBeneFisicoInfo = new BehaviorSubject<AnagraficaBeneFisico>(null);
  anagBeneFisicoInfo$: Observable<AnagraficaBeneFisico> = this.anagBeneFisicoInfo.asObservable();

  getAnagBeneFisico(idSoggetto: any, numDomanda: any) {

    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getAnagBeneFisico';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())
      .set("numeroDomanda", numDomanda.toString());

    return this.http.get<AnagraficaBeneFisico>(url, { params: params }).subscribe(data => {
      if (data) {
        this.anagBeneFisico = data;
        this.anagBeneFisicoInfo.next(data);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

  }


  elenco: ElencoDomandeProgettiSec;
  private elencoInfo = new BehaviorSubject<ElencoDomandeProgettiSec>(null);
  elencoInfo$: Observable<ElencoDomandeProgettiSec> = this.elencoInfo.asObservable();

  getElencoDomandeProgetti(idSoggetto: any, isEnteGiuridico: any) {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoDomandeProgetti';

    let params = new HttpParams()
      .set("idSoggetto", idSoggetto.toString())
      .set("enteGiuridico", isEnteGiuridico.toString())
      ;

    return this.http.get<any>(url, { params: params })
  }


  statoUltimaDomanda: statoDomanda;
  private statoUltimaDomandaInfo = new BehaviorSubject<statoDomanda>(null);
  statoUltimaDomandaInfo$: Observable<statoDomanda> = this.statoUltimaDomandaInfo.asObservable();

  getStatoDomanda(idSoggetto: any, numeroDomanda: any) {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getStatoDomanda';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())
    .set("idDomanda", numeroDomanda.toString());

    return this.http.get<statoDomanda>(url, { params: params }).subscribe(data => {
      if (data) {
        this.statoUltimaDomanda = data;
        this.statoUltimaDomandaInfo.next(data);
      }
    }, err => {
      console.log(err);
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  getElencoBlocchi(idSoggetto: number): Observable<Array<BloccoVO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoBlocchi';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString());
    return this.http.get<Array<BloccoVO>>(url, { params: params });
  }

  getStoricoBlocchi(idSoggetto: number): Observable<Array<BloccoVO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getStoricoBlocchi';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString());
    return this.http.get<Array<BloccoVO>>(url, { params: params });
  }
  insertBlocco(bloccoVO: BloccoVO): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/insertBlocco';
    return this.http.post<boolean>(url, bloccoVO);
  }
  modificaBlocco(bloccoVO: BloccoVO): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/modificaBlocco';
    return this.http.post<boolean>(url, bloccoVO);
  }
  getListaCausali(idSoggetto: number): Observable<Array<AttivitaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getListaCausali';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString());
    return this.http.get<Array<AttivitaDTO>>(url, { params: params });
  }
  getElencoRuoloIndipendenti(): Observable<Array<RuoloDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoRuoloIndipendente';
    return this.http.get<Array<RuoloDTO>>(url);
  }
  getElencoRuoloDipendenti(): Observable<Array<RuoloDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoRuoloDipendente';
    return this.http.get<Array<RuoloDTO>>(url);
  }
  getElencoSezioneSpeciale(): Observable<Array<SezioneSpecialeDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoSezSpec';
    return this.http.get<Array<SezioneSpecialeDTO>>(url);
  }

  getElencoAteco(idAttivitaAteco: string): Observable<Array<AtecoDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoAteco';
    let params = new HttpParams().set("idAttivitaAteco", idAttivitaAteco.toString());
    return this.http.get<Array<AtecoDTO>>(url, { params: params });
  }

  getDatiAreaCreditiSoggetto(idSoggetto: any): Observable<DatiAreaCreditiSoggetto> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getDatiAreaCreditiSoggetto';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString());
    return this.http.get<DatiAreaCreditiSoggetto>(url, { params: params });
  }

  getElencoBlocchiSoggettoDomanda(idSoggetto: any, idDomanda: any): Observable<Array<BloccoVO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoBlocchiSoggettoDomanda';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())
      .set("idDomanda", idDomanda);
    return this.http.get<Array<BloccoVO>>(url, { params: params });
  }

  getStoricoBlocchiDomanda(idSoggetto: number, numeroDomanda: string): Observable<Array<BloccoVO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getStoricoBlocchiDomanda';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("numeroDomanda", numeroDomanda);
    return this.http.get<Array<BloccoVO>>(url, { params: params });
  }
  getListaCausaliDomanda(idSoggetto: any, idDomanda: any): Observable<Array<AttivitaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getListaCausaliDomanda';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda.toString());
    return this.http.get<Array<AttivitaDTO>>(url, { params: params });
  }

  insertBloccoDomanda(bloccoVO: BloccoVO, idSoggetto: any, idDomanda: any): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/insertBloccoDomanda';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda.toString());
    return this.http.post<boolean>(url, bloccoVO, { params: params });
  }

  modificaBloccoDomanda(bloccoVO: BloccoVO, numeroDomanda: any): Observable<Boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/modificaBloccoDomanda';
    let params = new HttpParams().set("numeroDomanda", numeroDomanda.toString());
    return this.http.post<Boolean>(url, bloccoVO, { params: params });
  }

  getDatiImpresaDomanda(idSoggetto: string, numeroDomanda: string): Observable<Array<DatiDimensioneImpresaVO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getDatiImpresa';
    let params = new HttpParams().set("idSoggetto", idSoggetto).set("numeroDomanda", numeroDomanda);
    return this.http.get<Array<DatiDimensioneImpresaVO>>(url, { params: params });
  }
  getDettaglioDimImpresaSoggetto(idSoggetto: string): Observable<Array<DettaglioImpresa>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getDettaglioDimImpresaSoggetto';
    let params = new HttpParams().set("idSoggetto", idSoggetto);
    return this.http.get<Array<DettaglioImpresa>>(url, { params: params });
  }

  getDsanDomanda(idSoggetto: string, numeroDomanda: string): Observable<Array<AltriDatiDsan>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getDsan';
    let params = new HttpParams().set("idSoggetto", idSoggetto).set("numeroDomanda", numeroDomanda);
    return this.http.get<Array<AltriDatiDsan>>(url, { params: params });
  }

  getProvinciaConIdRegione(idRegione: string): Observable<Array<AtlanteVO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getProvinciaConIdRegione';
    let params = new HttpParams().set("idRegione", idRegione);
    return this.http.get<Array<AtlanteVO>>(url, { params: params });
  }

  getComuneEstero(idNazioneEstera: string): Observable<Array<AtlanteVO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getComuneEstero';
    let params = new HttpParams().set("idNazioneEstera", idNazioneEstera);
    return this.http.get<Array<AtlanteVO>>(url, { params: params });
  }
  getListaAttivita(): Observable<Array<AttivitaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getStatoAttivita';
    return this.http.get<Array<AttivitaDTO>>(url);
  }
  getElencoTipoAnag(): Observable<Array<AttivitaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoTipoAnag';
    return this.http.get<Array<AttivitaDTO>>(url);
  }
  getElencoTipoDocumento(): Observable<Array<AttivitaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoTipoDocumento';
    return this.http.get<Array<AttivitaDTO>>(url);
  }
  modificaSoggCorrIndipDaDomandaEG(sogg: SoggettoGiuridicoIndipendenteDomanda, idSoggetto: any, idDomanda: any, idSoggCorr: any): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/modificaSoggCorrIndipDaDomandaEG';
    let params = new HttpParams().set("idSoggetto", idSoggetto).set("idDomanda", idDomanda).set("idSoggCorr", idSoggCorr.toString());
    return this.http.post<boolean>(url, sogg, { params: params });
  }
  modificaSoggCorrIndipDaDomandaPF(sogg: BeneficiarioSoggCorrPF, idSoggetto: any, idDomanda: any): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/modificaSoggCorrIndipDaDomandaPF';
    let params = new HttpParams().set("idSoggetto", idSoggetto).set("idDomanda", idDomanda);
    return this.http.post<boolean>(url, sogg, { params: params });
  }
  getBenefSoggCorrPF(idSoggetto: any, idDomanda: any): Observable<BeneficiarioSoggCorrPF> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getSoggCorrIndipDaDomandaPF';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())
      .set("idDomanda", idDomanda.toString());
    return this.http.get<BeneficiarioSoggCorrPF>(url, { params: params });
  }
  getAnagBeneFisicoNew(idSoggetto: any, idDomanda: any, idProgetto: any): Observable<BeneficiarioSoggCorrPF> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getAnagBeneFisico';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())
      .set("idDomanda", (idDomanda != null) ? idDomanda.toString() : null)
      .set("idProgetto", (idProgetto != null) ? idProgetto.toString() : 0);
    return this.http.get<BeneficiarioSoggCorrPF>(url, { params: params });
  }

  generaExcel(nomeFile: string, richieste: Array<ElencoDomandeProgettiSec>) {
    const header = ['Codice bando', 'Numero domanda', 'Stato domanda', 'Codice progetto', 'Stato progetto', 'Legale rappresentante'];
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
      data.push([p.codiceBando, p.numeroDomanda, p.descStatoDomanda, p.descProgetto, p.descStatoProgetto, p.codiceBando, p.legaleRappresentante]);
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
