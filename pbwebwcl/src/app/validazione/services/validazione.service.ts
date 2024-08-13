/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Observable } from 'rxjs';
import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { InizializzaValidazioneDTO } from '../commons/dto/inizializza-validazione-dto';
import { CercaDocumentiDiSpesaValidazioneRequest } from '../commons/requests/cerca-documenti-di-spesa-validazione-request';
import { RigaRicercaDocumentiDiSpesaDTO } from '../commons/dto/riga-ricerca-documenti-di-spesa-dto';
import { DocumentoDiSpesaDematDTO } from '../commons/dto/documento-di-spesa-demat-dto';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { ValidaParzialmenteDocumentoRequest } from '../commons/requests/valida-parzialmente-documento-request';
import { map } from 'rxjs/operators';
import { VerificaOperazioneMassivaRequest } from '../commons/requests/verifica-operazione-massiva-request';
import { EsitoVerificaOperazioneMassivaDTO } from '../commons/dto/esito-verifica-operazione-massiva-dto';
import { OperazioneMassivaRequest } from '../commons/requests/operazione-massiva-request';
import { RichiediIntegrazioneRequest } from '../commons/requests/richiesta-integrazione-request';
import { InizializzaPopupChiudiValidazioneDTO } from '../commons/dto/inizializza-popup-chiudi-validazione-dto';
import { ProseguiChiudiValidazioneRequest } from '../commons/requests/prosegui-chiudi-validazione-request';
import { ChecklistHtmlDTO } from '../../shared/commons/dto/checklisthtml-dto';
import { SalvaCheckListValidazioneDocumentaleHtmlRequest } from '../commons/requests/salva-checklist-validazioneDocumentaleHml-request';
import { EsitoSalvaModuloCheckListHtml } from '../commons/dto/esito-modulo-checkList';
import { EsitoOperazioni } from '../commons/dto/esito-operazioni';
import { NewRichiediIntegrazioneRequest } from '../commons/requests/new-richiesta-integrazione-request';
import { ValidaMensilitaRequest } from '../commons/requests/valida-mensilita-request';
import { MensilitaDichiarazioneSpesaDTO } from '../commons/dto/mensilita-dichiarazione-spesa-dto';
import { NuovoDocumentoDiSpesaDTO } from '../commons/dto/nuovo-documento-di-spesa-dto';

@Injectable()
export class ValidazioneService {
  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    @Inject(DOCUMENT) private document: any
  ) { }

  inizializzaValidazione(idDichiarazioneDiSpesa: number, idProgetto: number, idBandoLinea: number, codiceRuolo: string): Observable<InizializzaValidazioneDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/inizializzaValidazione';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString())
      .set("idBandoLinea", idBandoLinea.toString())
      .set("codiceRuolo", codiceRuolo);
    return this.http.get<InizializzaValidazioneDTO>(url, { params: params });
  }

  cercaDocumentiDiSpesaValidazione(request: CercaDocumentiDiSpesaValidazioneRequest): Observable<Array<RigaRicercaDocumentiDiSpesaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/cercaDocumentiDiSpesaValidazione';
    return this.http.post<Array<RigaRicercaDocumentiDiSpesaDTO>>(url, request);
  }

  recuperaMensilitaDichiarazioneSpesa(idDichiarazioneSpesa: number): Observable<Array<MensilitaDichiarazioneSpesaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/recuperaMensilitaDichiarazioneSpesa';
    let params = new HttpParams().set("idDichiarazioneSpesa", idDichiarazioneSpesa.toString());
    return this.http.get<Array<MensilitaDichiarazioneSpesaDTO>>(url, { params: params });
  }

  validaMensilita(validaMensilitaRequest: Array<ValidaMensilitaRequest>): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/validaMensilita';
    return this.http.post<EsitoDTO>(url, validaMensilitaRequest);
  }

  dettaglioDocumentoDiSpesaPerValidazione(idDichiarazioneDiSpesa: number, idDocumentoDiSpesa: number, idProgetto: number, idBandoLinea: number): Observable<NuovoDocumentoDiSpesaDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/dettaglioDocumentoDiSpesaPerValidazione';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString())
      .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
      .set("idBandoLinea", idBandoLinea.toString());
    return this.http.get<NuovoDocumentoDiSpesaDTO>(url, { params: params });
  }

  IsBandoCompetenzaFinpiemonte(progBandoLineaIntervento: number): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/bandoIsEnteCompetenzaFinpiemonte';
    let params = new HttpParams().set("progBandoLineaIntervento", progBandoLineaIntervento.toString());
    return this.http.get<any>(url, { params: params });
  }

  setDataNotifica(idIntegrazioneSpesa: number, dataNotifica: string): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/setDataNotifica';
    let params = new HttpParams()
      .set("idIntegrazioneSpesa", idIntegrazioneSpesa.toString())
      .set("dataNotifica", dataNotifica.toString());
    return this.http.get<EsitoDTO>(url, { params: params });
  }


  chiudiRichiestaIntegrazione(idIntegrazione: number) {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/chiudiRichiestaIntegrazione';
    let params = new HttpParams().set('idIntegrazione', idIntegrazione.toString());
    return this.http.post<any>(url, { body: null }, { params: params });
  }

  avviaIterAutorizzativo(idTipoIter: number, idEntita: number, idTarget: number, idProgetto: number, idUtente: number): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/avvia';
    let params = new HttpParams()
      .set("idTipoIter", idTipoIter.toString())
      .set("idEntita", idEntita.toString())
      .set("idTarget", idTarget.toString())
      .set("idProgetto", idProgetto.toString())
      .set("idUtente", idUtente.toString());
    return this.http.get<any>(url, { params: params });
  }

  sospendiDocumento(idDichiarazioneDiSpesa: number, idDocumentoDiSpesa: number, idProgetto: number, noteValidazione: string): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/sospendiDocumento';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
    if (noteValidazione) {
      params = params.set("noteValidazione", noteValidazione);
    }
    return this.http.get<EsitoDTO>(url, { params: params });
  }

  respingiDocumento(idDichiarazioneDiSpesa: number, idDocumentoDiSpesa: number, idProgetto: number, noteValidazione: string): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/respingiDocumento';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
    if (noteValidazione) {
      params = params.set("noteValidazione", noteValidazione);
    }
    return this.http.get<EsitoDTO>(url, { params: params });
  }

  invalidaDocumento(idDichiarazioneDiSpesa: number, idDocumentoDiSpesa: number, idProgetto: number, noteValidazione: string): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/invalidaDocumento';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
    if (noteValidazione) {
      params = params.set("noteValidazione", noteValidazione);
    }
    return this.http.get<EsitoDTO>(url, { params: params });
  }

  validaDocumento(idDichiarazioneDiSpesa: number, idDocumentoDiSpesa: number, idProgetto: number, noteValidazione: string): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/validaDocumento';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
    if (noteValidazione) {
      params = params.set("noteValidazione", noteValidazione);
    }
    return this.http.get<EsitoDTO>(url, { params: params });
  }

  validaParzialmenteDocumento(request: ValidaParzialmenteDocumentoRequest): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/validaParzialmenteDocumento';
    return this.http.post<EsitoDTO>(url, request);
  }

  reportDettaglioDocumentoDiSpesa(idDichiarazioneDiSpesa: number) {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/reportDettaglioDocumentoDiSpesa';
    let params = new HttpParams().set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
    return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    /* return this.http.get(url, { params: params, responseType: 'blob' }).pipe(map(
        (res) => {
            return new Blob([res]);
        })); */
  }

  verificaOperazioneMassiva(request: VerificaOperazioneMassivaRequest): Observable<EsitoVerificaOperazioneMassivaDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/verificaOperazioneMassiva';
    return this.http.post<EsitoVerificaOperazioneMassivaDTO>(url, request);
  }

  operazioneMassiva(request: OperazioneMassivaRequest): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/operazioneMassiva';
    return this.http.post<EsitoDTO>(url, request);
  }

  richiediIntegrazione(request: RichiediIntegrazioneRequest): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/richiediIntegrazione';
    return this.http.post<EsitoDTO>(url, request);
  }

  newRichiediIntegrazione(letteraAccompagnatoria: File, visibilitaLettera: boolean, checkListInterna: File, visibilitaChecklist: boolean, reportValidazione: File, visibilitaReport: boolean,
    idUtente: string, idDichiarazioneSpesa: string, idProgetto: string, numGgScadenza: number,
    motivazione: string, idStatoRichiesta: number, inviaIter: number): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/newRichiediIntegrazione';

    let params = new HttpParams()
    let formData = new FormData();

    if (letteraAccompagnatoria) {
      formData.append("file", letteraAccompagnatoria, letteraAccompagnatoria.name);
      formData.append("nomeFile", letteraAccompagnatoria.name);
      formData.append("visibilitaLettera", visibilitaLettera ? "1" : "0");
    }

    if (checkListInterna) {
      formData.append("checkListInterna", checkListInterna, checkListInterna.name);
      formData.append("nomeCheckListInterna", checkListInterna.name);
      formData.append("visibilitaChecklist", visibilitaChecklist ? "1" : "0");
    }

    if (reportValidazione) {
      formData.append("reportValidazione", reportValidazione, reportValidazione.name);
      formData.append("nomeReportValidazione", reportValidazione.name);
      formData.append("visibilitaReport", visibilitaReport ? "1" : "0");
    }

    formData.append("idUtente", idUtente);
    formData.append("idDichiarazioneSpesa", idDichiarazioneSpesa);
    formData.append("idProgetto", idProgetto);
    formData.append("motivazione", motivazione);
    formData.append("numGgScadenza", numGgScadenza ? numGgScadenza.toString() : '-1');
    formData.append("idStatoRichiesta", idStatoRichiesta.toString());
    formData.append("inviaIter", inviaIter.toString());     //1 se FP


    return this.http.post<any>(url, formData, { params: params });
  }

  chiudiValidazione(idDichiarazioneDiSpesa: number, idDocumentoIndex: number, idBandoLinea: number, invioExtraProcedura: string): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/chiudiValidazione';
    let params = new HttpParams()
      .set("idBandoLinea", idBandoLinea.toString())
      .set("idDocumentoIndex", idDocumentoIndex.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString())
      .set("invioExtraProcedura", invioExtraProcedura);

    return this.http.get<EsitoDTO>(url, { params: params });
  }

  salvaProtocollo(idDocumentoIndex: number, protocollo: string): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/salvaProtocollo';
    let params = new HttpParams()
      .set("idDocumentoIndex", idDocumentoIndex.toString())
      .set("protocollo", protocollo);

    return this.http.get<boolean>(url, { params: params });
  }

  inizializzaPopupChiudiValidazione(idDichiarazioneDiSpesa: number, idProgetto: number, idBandoLinea: number,): Observable<InizializzaPopupChiudiValidazioneDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/inizializzaPopupChiudiValidazione';
    let params = new HttpParams()
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString())
      .set("idProgetto", idProgetto.toString())
      .set("idBandoLinea", idBandoLinea.toString());

    return this.http.get<InizializzaPopupChiudiValidazioneDTO>(url, { params: params });
  }

  proseguiChiudiValidazione(request: ProseguiChiudiValidazioneRequest): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/proseguiChiudiValidazione';
    return this.http.post<EsitoDTO>(url, request);
  }

  getModuloCheckListValidazioneHtml(idProgetto: number, idDichiarazioneDiSpesa: number): Observable<ChecklistHtmlDTO> {
    let url = this.configService.getApiURL() + "/restfacade/validazioneRendicontazione/getModuloCheckListValidazioneHtml";
    let params = new HttpParams().set("idProgetto", idProgetto.toString())
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());

    console.log('ANG CheckListService getModuloCheckListValidazioneHtml ,idProgetto =' + idProgetto + ', idDichiarazioneDiSpesa=' + idDichiarazioneDiSpesa);

    return this.http.get<ChecklistHtmlDTO>(url, { params: params });
  }

  saveCheckListDocumentaleHtml(request: SalvaCheckListValidazioneDocumentaleHtmlRequest): Observable<EsitoSalvaModuloCheckListHtml> {

    let url = this.configService.getApiURL() + "/restfacade/validazioneRendicontazione/saveCheckListDocumentaleHtml";
    return this.http.post<EsitoSalvaModuloCheckListHtml>(url, request);
  }

  chiudiValidazioneChecklistHtml(idProgetto: number, idDichiarazioneDiSpesa: number,
    idDocumentoIndex: number, statoChecklist: string, checklistHtml: string, idBandoLinea: number, codiceProgetto: string, dsIntegrativaConsentita: boolean,
    files: Array<File>): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/validazioneRendicontazione/chiudiValidazioneChecklistHtml";
    let formData = new FormData();
    formData.append("idProgetto", idProgetto.toString());
    formData.append("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
    formData.append("idDocumentoIndex", idDocumentoIndex.toString());
    formData.append("idBandoLinea", idBandoLinea.toString());
    formData.append("dsIntegrativaConsentita", dsIntegrativaConsentita.toString());
    if (statoChecklist) {
      formData.append("statoChecklist", statoChecklist);
    }
    formData.append("checklistHtml", checklistHtml);
    formData.append("codiceProgetto", codiceProgetto);
    if (files && files.length) {
      for (var i = 0; i < files.length; i++) {
        formData.append("file", files[i], files[i].name);
      }
    }
    return this.http.post<EsitoOperazioni>(url, formData);

  }


  //metodo chiamato in esito-validazione-dich-spesa
  getDropDown(idProgetto: number): Observable<any> {
    let url = this.configService.getApiURL() + "/restfacade/validazioneRendicontazione/initDropDownCL";

    let params = new HttpParams().set("idProgetto", idProgetto.toString());

    return this.http.get<any>(url, { params: params });
  }
  ////////////// BOTTONE ALLEGATI IN PIU

  chiudiValidazioneEsito(
    isChiudiValidazione: number,
    letteraAccompagnatoria: File, visibilitaLettera: boolean,
    checkListInterna: File, visibilitaChecklist: boolean,
    reportValidazione: File, visibilitaReport: boolean,
    idUtente: string,
    idIride: string, idDichiarazioneSpesa: string,
    idProgetto: string, entita: string,
    statoCheckList: string, checklistHtml: string,
    idChecklist: string, files: Array<File>,

    idDichiarazioneDiSpesa: string, idDocumentoIndex: string,
    statoChecklist: string, idBandoLinea: string,
    codiceProgetto: string, dsIntegrativaConsentita: string,

    idEsitoValidSpesa: string, idAttribValidSpesa: string,
    importoDaErogare: string, importoIres: string,
    listIdModalitaAgevolazione: Array<number>,
    premialita: number,
    idCausaleErogFinanz: number,
    idCausaleErogContributo: number
  ): Observable<any> {
    console.log('statoCheckList');
    console.log(statoCheckList);


    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/chiudiValidazioneEsito';
    let params = new HttpParams()
    let formData = new FormData();
    formData.append("isChiudiValidazione", isChiudiValidazione.toString());
    if (letteraAccompagnatoria) {
      formData.append("letteraAccompagnatoria", letteraAccompagnatoria, letteraAccompagnatoria.name);
      formData.append("nomeLetteraAccompagnatoria", letteraAccompagnatoria.name);
      formData.append("visibilitaLettera", visibilitaLettera ? "1" : "0");
    }
    if (checkListInterna) {
      formData.append("checkListInterna", checkListInterna, checkListInterna.name);
      formData.append("nomeCheckListInterna", checkListInterna.name);
      formData.append("visibilitaChecklist", visibilitaChecklist ? "1" : "0");
    }
    if (reportValidazione) {
      formData.append("reportValidazione", reportValidazione, reportValidazione.name);
      formData.append("nomeReportValidazione", reportValidazione.name);
      formData.append("visibilitaReport", visibilitaReport ? "1" : "0");
    }
    formData.append("idUtente", idUtente);
    formData.append("idIride", idIride);
    formData.append("idDichiarazioneSpesa", idDichiarazioneSpesa);
    formData.append("idProgetto", idProgetto);
    formData.append("entita", entita);
    // formData.append("statoChecklist", statoChecklist);
    formData.append("checklistHtml", checklistHtml);
    formData.append("idChecklist", idChecklist);
    if (files) {
      for (var i = 0; i < files.length; i++) {
        formData.append("file", files[i], files[i].name);
      }
    }
    formData.append("idAttribValidSpesa", idAttribValidSpesa);
    formData.append("idEsitoValidSpesa", idEsitoValidSpesa);
    formData.append("importoDaErogare", importoDaErogare);
    formData.append("importoIres", importoIres);
    formData.append("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa);
    formData.append("idDocumentoIndex", idDocumentoIndex);
    formData.append("statoChecklist", statoCheckList);
    if (statoChecklist) {
      formData.append("statoChecklist", statoChecklist);
    }
    formData.append("idBandoLinea", idBandoLinea);
    formData.append("codiceProgetto", codiceProgetto);
    formData.append("dsIntegrativaConsentita", dsIntegrativaConsentita);
    if (listIdModalitaAgevolazione?.length > 0) {
      formData.append("listIdModalitaAgevolazione", JSON.stringify(listIdModalitaAgevolazione));
    }
    if (premialita !== null) {
      formData.append("premialita", premialita.toString());
    }
    if (idCausaleErogContributo !== null) {
      formData.append("idCausaleErogContributo", idCausaleErogContributo.toString());
    }
    if (idCausaleErogFinanz !== null) {
      formData.append("idCausaleErogFinanz", idCausaleErogFinanz.toString());
    }
    /* if (files && files.length) {
      for (var i = 0; i < files.length; i++) {
        formData.append("file", files[i], files[i].name);
      }
    }
 */
    return this.http.post<any>(url, formData, { params: params });
  }

  chiudiValidazioneUfficio(idDichiarazioneSpesa: number) {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/chiudiValidazioneUfficio';
    let params = new HttpParams().set('idDichiarazioneSpesa', idDichiarazioneSpesa.toString());
    return this.http.post<any>(url, { body: null }, { params: params });
  }


  initIntegrazione(idDichiarazioneSpesa: string): Observable<any> {
    let url = this.configService.getApiURL() + "/restfacade/validazioneRendicontazione/initIntegrazione";
    let params = new HttpParams()
      .set("idDichiarazioneSpesa", idDichiarazioneSpesa);
    return this.http.get<any>(url, { params: params });
  }

  getSumImportoErogProgettiPercettori(progrBandoLineaIntervento: number, idDichiarazioneDiSpesa: number) {
    let url = this.configService.getApiURL() + '/restfacade/validazioneRendicontazione/getSumImportoErogProgettiPercettori';
    let params = new HttpParams()
      .set('progrBandoLineaIntervento', progrBandoLineaIntervento.toString())
      .set('idDichiarazioneDiSpesa', idDichiarazioneDiSpesa.toString());
    return this.http.get<number>(url, { params: params });
  }

  //GESTIONE DELLE PROROGHE

  getProrogaIntegrazione(idDichiarazioneDiSpesa: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneProroghe/getProrogaIntegrazione';
    let params = new HttpParams()
      .set('idDichiarazioneSpesa', idDichiarazioneDiSpesa.toString());
    return this.http.get<Array<any>>(url, { params: params });
  }
  getStepProroga(idDichiarazioneDiSpesa: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneProroghe/getStepProroga';
    let params = new HttpParams()
      .set('idDichiarazioneSpesa', idDichiarazioneDiSpesa.toString());
    return this.http.get<Array<any>>(url, { params: params });
  }
  getStoricoProrogheIntegrazione(idDichiarazioneDiSpesa: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneProroghe/getStoricoProrogheIntegrazione';
    let params = new HttpParams().set('idDichiarazioneSpesa', idDichiarazioneDiSpesa.toString());
    return this.http.get<Array<any>>(url, { params: params });
  }

  approvaProroga(idProroga: number, numGiorni: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneProroghe/approvaProroga';
    let params = new HttpParams()
      .set('idProroga', idProroga.toString())
      .set('numGiorni', numGiorni.toString());
    return this.http.put<boolean>(url, { body: null }, { params: params });
  }

  respingiProroga(idProroga) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneProroghe/respingiProroga';
    let params = new HttpParams().set('idProroga', idProroga.toString());
    return this.http.put<boolean>(url, { body: null }, { params: params });
  }
}
