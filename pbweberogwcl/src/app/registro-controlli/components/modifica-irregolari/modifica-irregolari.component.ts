/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizione } from 'src/app/disimpegni/commons/models/codice-descrizione';
import { EsitoSalvaIrregolaritaDTO } from '../../commons/dto/esito-salva-irregolarita-dto';
import { Irregolarita } from '../../commons/models/irregolarita';
import { RequestInserisciIrreg } from '../../commons/models/request-inserisci-irreg';
import { RequestModificaIrreg } from '../../commons/models/request-modifica-irreg';
import { RequestModificaIrregolarita } from '../../commons/models/request-modifica-irregolarita';
import { RequestSalvaIrregolarita } from '../../commons/models/request-salva-irregolarita';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-modifica-irregolari',
  templateUrl: './modifica-irregolari.component.html',
  styleUrls: ['./modifica-irregolari.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ModificaIrregolariComponent implements OnInit {

  idProgetto: string;
  beneficiario: string;
  codiceProgetto: string;
  user: UserInfoSec;
  noteRegolare: string;
  idIrregolarita: number;
  idDocumentoIndexOlaf: string;
  idDocumentoIndexDatiAggiuntivi: string;
  irregolari: Array<Irregolarita>;

  dataInserimentoEsito: string;
  tipoControlliRadio: string = '1';

  anniContabile: Array<CodiceDescrizione>;
  annoContabileSelected: CodiceDescrizione;
  autoritasControllante: Array<CodiceDescrizione>;
  autoritaControllanteSelected: CodiceDescrizione;

  dateCampione: Array<CodiceDescrizione>;
  dataCampioneSelected: CodiceDescrizione;

  dataInizioControlli: FormControl = new FormControl();
  dataFineControlli: FormControl = new FormControl();

  importoSpesaIrregolareIrregProvv: string;
  importoSpesaIrregolareIrregDef: string;
  importoAgevIrrIrregProvv: string;
  importoAgevIrrIrregDef: string;
  importoIrregCertIrregProvv: string;
  importoIrregCertIrregDef: string;

  praticaUsataIrregIrregDef: string;
  soggContatChiarimIrregDef: string;

  allegDatiAccessIrreg: string;
  allegatiVariIntegIrreg: string;

  noteIrregDef: string;

  motivoIrregIrregProvvSelected: CodiceDescrizione;
  motiviIrregIrregProvv: Array<CodiceDescrizione>;
  motivoIrregIrregDefSelected: CodiceDescrizione;;
  motiviIrregIrregDef: Array<CodiceDescrizione>;

  @ViewChild('uploader') uploader;
  @ViewChild('uploaderDatiAgg') uploaderDatiAgg;

  provvedimentoRadio: string = '1';
  trasformaInRadio: string;
  noteIrregProvv: string;

  fileOLAF: File;
  fileDatiAgg: File;

  //SUBSCRIBERS
  subscribers: any = {};

  trasformata: boolean;
  tipologia: string;
  showTrasformaRadio: boolean;

  flagIrregolaritaAnnullataProvv: string;
  dtFineProvvisoriaProvv: string;
  dtFineValiditaProvv: string;

  saved: boolean;

  @ViewChild(NgForm) esitoForm: NgForm;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  loadedGetPeriodi: boolean;
  loadedGetCategAnagrafica: boolean;
  loadedGetMotiviIrregolarita: boolean;
  loadedGetDettaglioIrregolarita: boolean;
  loadedGetDataCampione: boolean;
  loadedGetDettaglioIrregolaritaProvvisoria: boolean;
  loadedInserisciIrregolaritaDefinitiva: boolean;
  loadedInserisciIrregolaritaRegolare: boolean;
  loadedModificaIrregolarita: boolean;
  loadedModificaIrregolaritaProvvisoria: boolean;
  loadedGetDocumento: boolean;

  constructor(
    private userService: UserService,
    private registroControlliService: RegistroControlliService,
    private registroControlliService2: RegistroControlliService2,
    public dialogRef: MatDialogRef<ModificaIrregolariComponent>,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idIrregolarita = this.data.idIrregolarita;
    this.idProgetto = this.data.idProgetto;
    this.beneficiario = this.data.beneficiario;
    this.codiceProgetto = this.data.codiceProgetto;
    this.irregolari = this.data.irregolari;

    if (this.data.irregDefinitiva) {
      this.trasformata = false;
      this.tipologia = "definitiva";
      this.showTrasformaRadio = false;
    } else {
      this.trasformata = false;
      this.tipologia = "provvisoria";
      this.showTrasformaRadio = true;
    }
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.loadedGetPeriodi = true;
        this.subscribers.getPeriodi = this.registroControlliService.getPeriodi().subscribe((res: Array<CodiceDescrizione>) => {
          this.anniContabile = res;
          this.loadedGetPeriodi = false;

          this.loadedGetCategAnagrafica = true;
          this.subscribers.getCategAnagrafica = this.registroControlliService.getCategAnagrafica().subscribe((res: Array<CodiceDescrizione>) => {
            this.autoritasControllante = res;
            this.loadedGetCategAnagrafica = false;

            this.loadedGetMotiviIrregolarita = true;
            this.subscribers.getMotiviIrregolarita = this.registroControlliService.getMotiviIrregolarita().subscribe((res: Array<CodiceDescrizione>) => {
              this.motiviIrregIrregDef = res;
              this.motiviIrregIrregProvv = res;
              this.loadedGetMotiviIrregolarita = false;

              if (this.tipologia == "definitiva") {

                this.loadedGetDettaglioIrregolarita = true;
                this.subscribers.getDettaglioIrregolarita = this.registroControlliService.getDettaglioIrregolarita(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
                  this.loadedGetDettaglioIrregolarita = false;


                  // download documento olaf per passarlo in fase di modifica in caso di nessun caricamento file ulteriore
                  this.loadedGetDocumento = true;
                  this.subscribers.getDocumento = this.registroControlliService2.getDocumento(res.schedaOLAF.idDocumentoIndex).subscribe(data => {
                    this.fileOLAF = new File([data], res.schedaOLAF.nomeFile);
                    this.allegDatiAccessIrreg = res.schedaOLAF.nomeFile;

                    this.loadedGetDocumento = false;
                  }, err => {
                    this.fileOLAF = undefined;
                    this.allegDatiAccessIrreg = undefined;
                    this.loadedGetDocumento = false;
                  })

                  // download documento dati aggiuntivi per passarlo in fase di modifica in caso di nessun caricamento file ulteriore
                  this.allegatiVariIntegIrreg = res.datiAggiuntivi == undefined ? undefined : res.datiAggiuntivi.nomeFile;
                  if (res.datiAggiuntivi == undefined) {
                    this.fileDatiAgg = undefined;
                    this.allegatiVariIntegIrreg = undefined;
                  } else {
                    this.loadedGetDocumento = true;
                    this.subscribers.getDocumento2 = this.registroControlliService2.getDocumento(res.datiAggiuntivi.idDocumentoIndex).subscribe(data => {
                      this.fileDatiAgg = new File([data], res.datiAggiuntivi.nomeFile);
                      this.allegatiVariIntegIrreg = res.datiAggiuntivi.nomeFile;

                      this.loadedGetDocumento = false;
                    }, err => {
                      this.fileDatiAgg = undefined;
                      this.allegatiVariIntegIrreg = undefined;
                      this.loadedGetDocumento = false;
                    })
                  }

                  this.flagIrregolaritaAnnullataProvv = res.flagIrregolaritaAnnullataProvv;
                  this.dtFineProvvisoriaProvv = res.dtFineProvvisoriaProvv;
                  this.dtFineValiditaProvv = res.dtFineValiditaProvv;
                  this.dataInserimentoEsito = new Date(res.dtComunicazione).toLocaleDateString();
                  this.tipoControlliRadio = res.tipoControlli == 'L' ? '2' : '1';
                  this.annoContabileSelected = this.anniContabile.find(d => Number(d.codice) == res.idPeriodo);
                  this.autoritaControllanteSelected = this.autoritasControllante.find(d => Number(d.codice) == res.idCategAnagrafica);
                  this.idDocumentoIndexOlaf = res.schedaOLAF == null ? null : res.schedaOLAF.idDocumentoIndex.toString();
                  this.idDocumentoIndexDatiAggiuntivi = res.datiAggiuntivi == null ? null : res.datiAggiuntivi.idDocumentoIndex.toString();

                  // Popolamento drop data campione
                  let request: RegistroControlliService.GetDataCampioneParams = {
                    tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
                    idProgetto: Number(this.idProgetto),
                    idPeriodo: Number(this.annoContabileSelected.codice),
                    idCategAnagrafica: Number(this.autoritaControllanteSelected.codice)
                  };

                  this.loadedGetDataCampione = true;
                  this.subscribers.getDataCampione = this.registroControlliService.getDataCampione(request).subscribe((res4: Array<CodiceDescrizione>) => {
                    this.loadedGetDataCampione = false;

                    if (res4.length == 0) {

                      let comodo: CodiceDescrizione = {
                        codice: '-1',
                        descrizione: 'Nessuno'
                      }

                      this.dateCampione = new Array<CodiceDescrizione>();
                      this.dateCampione.push(comodo)
                    } else {
                      this.dateCampione = res4;

                      let comodo: CodiceDescrizione = {
                        codice: '-1',
                        descrizione: 'Nessuno'
                      }

                      this.dateCampione.push(comodo)
                    }

                    if (res.dataCampione == null) {
                      this.dataCampioneSelected = this.dateCampione.find(d => d.descrizione == "Nessuno");
                    } else {
                      this.dataCampioneSelected = this.dateCampione.find(d => d.descrizione == (new Date(Date.parse(res.dataCampione)).toLocaleDateString("en-GB", { // you can use undefined as first argument
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit",
                      })).toString());
                    }
                  }, err => {
                    this.handleExceptionService.handleNotBlockingError(err);
                    this.showMessageError("Errore in fase di ottenimento delle date campione");
                    this.loadedGetDataCampione = false;
                  });

                  this.dataInizioControlli.setValue(new Date(res.dataInizioControlli));
                  this.dataFineControlli.setValue(new Date(res.dataFineControlli));
                  this.importoSpesaIrregolareIrregDef = res.importoIrregolarita == null ? null : this.sharedService.formatValue(res.importoIrregolarita.toString());
                  this.importoAgevIrrIrregDef = res.importoAgevolazioneIrreg == null ? null : this.sharedService.formatValue(res.importoAgevolazioneIrreg.toString());
                  this.importoIrregCertIrregDef = res.quotaImpIrregCertificato == null ? null : this.sharedService.formatValue(res.quotaImpIrregCertificato.toString());

                  this.provvedimentoRadio = res.flagProvvedimento == 'N' ? '1' : (res.flagProvvedimento == 'S' ? '3' : '2');
                  this.motivoIrregIrregDefSelected = this.motiviIrregIrregDef.find(d => Number(d.codice) == res.idMotivoRevoca);
                  this.praticaUsataIrregIrregDef = res.notePraticaUsata ? res.notePraticaUsata : "";
                  this.soggContatChiarimIrregDef = res.soggettoResponsabile;

                  this.noteIrregDef = res.note ? res.note : "";
                }, err => {
                  this.handleExceptionService.handleNotBlockingError(err);
                  this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità");
                  this.loadedGetDettaglioIrregolarita = false;
                });
              } else {

                this.loadedGetDettaglioIrregolaritaProvvisoria = true;
                this.subscribers.getDettaglioIrregolaritaProvvisoria = this.registroControlliService.getDettaglioIrregolaritaProvvisoria(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
                  this.loadedGetDettaglioIrregolaritaProvvisoria = false;

                  this.dataInserimentoEsito = new Date(res.dtComunicazioneProvv).toLocaleDateString();
                  this.tipoControlliRadio = res.tipoControlliProvv == 'L' ? '2' : '1';
                  this.annoContabileSelected = this.anniContabile.find(d => Number(d.codice) == res.idPeriodoProvv);
                  this.autoritaControllanteSelected = this.autoritasControllante.find(d => Number(d.codice) == res.idCategAnagraficaProvv);

                  // Popolamento drop data campione
                  let request: RegistroControlliService.GetDataCampioneParams = {
                    tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
                    idProgetto: Number(this.idProgetto),
                    idPeriodo: Number(this.annoContabileSelected.codice),
                    idCategAnagrafica: Number(this.autoritaControllanteSelected.codice)
                  };

                  this.loadedGetDataCampione = true;
                  this.subscribers.getDataCampione = this.registroControlliService.getDataCampione(request).subscribe((res4: Array<CodiceDescrizione>) => {
                    this.loadedGetDataCampione = false;

                    if (res4.length == 0) {

                      let comodo: CodiceDescrizione = {
                        codice: '-1',
                        descrizione: 'Nessuno'
                      }

                      this.dateCampione = new Array<CodiceDescrizione>();
                      this.dateCampione.push(comodo)
                    } else {
                      this.dateCampione = res4;

                      let comodo: CodiceDescrizione = {
                        codice: '-1',
                        descrizione: 'Nessuno'
                      }

                      this.dateCampione.push(comodo)
                    }

                    if (res.dataCampione == null) {
                      this.dataCampioneSelected = this.dateCampione.find(d => d.descrizione == "Nessuno");
                    } else {
                      this.dataCampioneSelected = this.dateCampione.find(d => d.descrizione == (new Date(res.dataCampione).toLocaleDateString("en-GB", { // you can use undefined as first argument
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit",
                      })).toString());
                    }
                  }, err => {
                    this.handleExceptionService.handleNotBlockingError(err);
                    this.showMessageError("Errore in fase di ottenimento della data campione");
                    this.loadedGetDataCampione = false;
                  });

                  this.dataInizioControlli.setValue(new Date(res.dataInizioControlliProvv));
                  if (res.dataFineControlliProvv) {
                    this.dataFineControlli.setValue(new Date(res.dataFineControlliProvv));
                  }
                  this.importoSpesaIrregolareIrregProvv = res.importoIrregolaritaProvv == null ? null : this.sharedService.formatValue(res.importoIrregolaritaProvv.toString());
                  this.importoAgevIrrIrregProvv = res.importoAgevolazioneIrregProvv == null ? null : this.sharedService.formatValue(res.importoAgevolazioneIrregProvv.toString());
                  this.importoIrregCertIrregProvv = res.importoIrregolareCertificatoProvv == null ? null : this.sharedService.formatValue(res.importoIrregolareCertificatoProvv.toString());
                  this.motivoIrregIrregProvvSelected = this.motiviIrregIrregProvv.find(d => Number(d.codice) == res.idMotivoRevocaProvv);
                  this.noteIrregProvv = res.noteProvv ? res.noteProvv : "";
                }, err => {
                  this.handleExceptionService.handleNotBlockingError(err);
                  this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità provvisoria'");
                  this.loadedGetDettaglioIrregolaritaProvvisoria = false;
                });
              }
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di ottenimento dei motivi dell'irregolarità");
              this.loadedGetMotiviIrregolarita = false;
            });
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di ottenimento delle categ anagrafiche");
            this.loadedGetCategAnagrafica = false;
          });
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento dei periodi");
          this.loadedGetPeriodi = false;
        });
      }
    });
  }

  changeAnnoContabile() {
    if (!(this.annoContabileSelected == undefined) && !(this.autoritaControllanteSelected == undefined)) {
      this.loadDataCampione();
    } else {
      this.dataCampioneSelected = undefined;
      this.dateCampione = new Array<CodiceDescrizione>();
    }
  }

  loadDataCampione() {
    let request: RegistroControlliService.GetDataCampioneParams = {
      tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
      idProgetto: Number(this.idProgetto),
      idPeriodo: Number(this.annoContabileSelected.codice),
      idCategAnagrafica: Number(this.autoritaControllanteSelected.codice)
    };

    this.loadedGetDataCampione = true;
    this.subscribers.getDataCampione = this.registroControlliService.getDataCampione(request).subscribe((res: Array<CodiceDescrizione>) => {
      this.loadedGetDataCampione = false;
      let comodo: CodiceDescrizione = {
        codice: '-1',
        descrizione: 'Nessuno'
      }
      if (res.length == 0) {
        this.dateCampione = new Array<CodiceDescrizione>();
        this.dateCampione.push(comodo)
      } else {
        this.dateCampione = res;
        this.dateCampione.push(comodo)
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento della data campione");
      this.loadedGetDataCampione = false;
    });
  }

  changeAutControl() {
    if (!(this.annoContabileSelected == undefined) && !(this.autoritaControllanteSelected == undefined)) {
      this.loadDataCampione();
    } else {
      this.dataCampioneSelected = undefined;
      this.dateCampione = new Array<CodiceDescrizione>();
    }
  }

  getDescTipoByCode(code: string) {
    if (code == 'N') {
      return 'Nessuno';
    }
    if (code == 'R') {
      return 'Revoca';
    }
    if (code == 'S') {
      return 'Soppressione';
    }
    if (code == 'D') {
      return 'Documentali';
    }
    if (code == 'L') {
      return 'In loco';
    }
  }

  changeDataFineControlli() {
    if (this.dataInizioControlli && this.dataInizioControlli.errors && this.dataInizioControlli.errors.error === 'notValid' &&
      (!this.dataFineControlli || !this.dataFineControlli.value
        || (this.dataFineControlli && this.dataFineControlli.value && this.dataInizioControlli && this.dataInizioControlli.value
          && this.dataInizioControlli.value <= this.dataFineControlli.value))) {
      this.dataInizioControlli.setErrors(null);
    }
  }

  setImportoSpesaIrregolareIrregProvv() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregProvv);
    if (imp !== null) {
      this.importoSpesaIrregolareIrregProvv = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoAgevIrrIrregProvv() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregProvv);
    if (imp !== null) {
      this.importoAgevIrrIrregProvv = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoIrregCertIrregProvv() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregProvv);
    if (imp !== null) {
      this.importoIrregCertIrregProvv = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoSpesaIrregolareIrregDef() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregDef);
    if (imp !== null) {
      this.importoSpesaIrregolareIrregDef = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoAgevIrrIrregDef() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregDef);
    if (imp !== null) {
      this.importoAgevIrrIrregDef = this.sharedService.formatValue(imp.toString());
    }
  }

  setImportoIrregCertIrregDef() {
    let imp: number = this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregDef);
    if (imp !== null) {
      this.importoIrregCertIrregDef = this.sharedService.formatValue(imp.toString());
    }
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.trasformata) {
      if (this.tipologia == "definitiva") {
        // TRASFORMAZIONE IN DEFINITIVA
        if (this.registroControlliService2.validate(this.esitoForm, "3" /*DEFINITIVA*/, this.dataInizioControlli, this.dataFineControlli, this.importoSpesaIrregolareIrregDef,
          this.importoAgevIrrIrregDef, this.importoIrregCertIrregDef)) {
          this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
          return;
        }
        // Creazione irregolarità definitiva
        let comodoRequestIrreg: RequestModificaIrreg = {
          idU: this.user.idUtente,
          idIride: this.user.idIride,
          modificaDatiAggiuntivi: this.fileDatiAgg == undefined ? "false" : "true",
          idProgetto: +this.idProgetto,
          idMotivoRevoca: +this.motivoIrregIrregDefSelected.codice,
          importoIrregolarita: this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregDef.toString()),
          importoAgevolazioneIrreg: this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregDef.toString()),
          quotaImpIrregCertificato: this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregDef.toString()),
          idPeriodo: +this.annoContabileSelected.codice,
          idCategAnagrafica: +this.autoritaControllanteSelected.codice,
          tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
          note: this.noteIrregDef ? this.noteIrregDef : "",
          dataCampione: this.dataCampioneSelected ? this.dataCampioneSelected.codice : null,
          dataInizioControlli: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
          dataFineControlli: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
          flagProvvedimento: this.provvedimentoRadio == '1' ? 'N' : (this.provvedimentoRadio == '2' ? 'D' : 'S'),
          notePraticaUsata: this.praticaUsataIrregIrregDef ? this.praticaUsataIrregIrregDef : "",
          soggettoResponsabile: this.soggContatChiarimIrregDef,
          idIrregolaritaProvv: this.idIrregolarita,
          fileDatiAggiuntivi: this.fileDatiAgg,
          fileOlaf: this.fileOLAF,
          idIrregolarita: null
        };

        this.loadedInserisciIrregolaritaDefinitiva = true;
        this.subscribers.inserisciIrregolaritaDefinitiva = this.registroControlliService2.inserisciIrregolaritaDefinitiva(comodoRequestIrreg).subscribe(data => {
          this.loadedInserisciIrregolaritaDefinitiva = false;

          if (data.esito == true) {
            this.dialogRef.close({ esito: true, idIrregolaritaDef: data.idIrregolarita });
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di inserimento dell'irregolarità definitiva");
          this.loadedInserisciIrregolaritaDefinitiva = false;
        });
      } else {
        // TRASFORMAZIONE IN REGOLARE
        if (this.registroControlliService2.validate(this.esitoForm, "1" /*REGOLARE*/, this.dataInizioControlli, this.dataFineControlli, null, null, null)) {
          this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
          return;
        }
        // Creazione esito regolare
        let irregolarita: Irregolarita = {
          esitoControllo: "REGOLARE",
          idPeriodo: Number(this.annoContabileSelected.codice),
          idCategAnagrafica: Number(this.autoritaControllanteSelected.codice),
          tipoControlli: this.tipoControlliRadio == "1" ? "D" : "L",
          dtComunicazioneIrregolarita: this.dataInserimentoEsito,
          dataInizioControlli: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
          dataFineControlli: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
          note: this.noteRegolare ? this.noteRegolare : "",
          dataCampione: this.dataCampioneSelected == undefined ? null : this.dataCampioneSelected.descrizione,
          idIrregolaritaCollegata: Number(this.idIrregolarita),
          idIrregolaritaProvv: Number(this.idIrregolarita)
        }

        let request: RequestSalvaIrregolarita = {
          idProgetto: Number(this.idProgetto),
          idU: this.user.idUtente,
          idBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
          codiceFiscale: this.user.beneficiarioSelezionato.codiceFiscale,
          irregolarita: irregolarita
        };

        this.loadedInserisciIrregolaritaRegolare = true;
        this.subscribers.inserisciIrregolaritaRegolare = this.registroControlliService.inserisciIrregolaritaRegolare(request).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
          this.loadedInserisciIrregolaritaRegolare = false;

          if (res.esito == true) {
            this.dialogRef.close({ esito: true, provvToReg: true });
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di inserimento dell'irregolarità regolare");
          this.loadedInserisciIrregolaritaRegolare = false;
        });
      }
    } else {
      if (this.tipologia == "definitiva") {
        if (this.registroControlliService2.validate(this.esitoForm, "3" /*DEFINITIVA*/, this.dataInizioControlli, this.dataFineControlli, this.importoSpesaIrregolareIrregDef,
          this.importoAgevIrrIrregDef, this.importoIrregCertIrregDef)) {
          this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
          return;
        }
        // MODIFICA IRREGOLARITA DEFINITIVA
        let comodoRequestIrreg: RequestModificaIrreg = {
          idU: this.user.idUtente,
          idIride: this.user.idIride,
          modificaDatiAggiuntivi: this.fileDatiAgg == undefined ? "false" : "true",
          idProgetto: +this.idProgetto,
          idMotivoRevoca: +this.motivoIrregIrregDefSelected.codice,
          importoIrregolarita: this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregDef.toString()),
          importoAgevolazioneIrreg: this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregDef.toString()),
          quotaImpIrregCertificato: this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregDef.toString()),
          idPeriodo: +this.annoContabileSelected.codice,
          idCategAnagrafica: +this.autoritaControllanteSelected.codice,
          tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
          idIrregolarita: this.idIrregolarita,
          note: this.noteIrregDef ? this.noteIrregDef : "",
          dataCampione: this.dataCampioneSelected ? this.dataCampioneSelected.codice : null,
          dataInizioControlli: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
          dataFineControlli: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
          flagProvvedimento: this.provvedimentoRadio == '1' ? 'N' : (this.provvedimentoRadio == '2' ? 'D' : 'S'),
          notePraticaUsata: this.praticaUsataIrregIrregDef ? this.praticaUsataIrregIrregDef : "",
          soggettoResponsabile: this.soggContatChiarimIrregDef,
          idIrregolaritaProvv: null,
          fileDatiAggiuntivi: this.fileDatiAgg,
          fileOlaf: this.fileOLAF,
          idDocumentoIndexOlaf: this.idDocumentoIndexOlaf,
          idDocumentoIndexDatiAggiuntivi: this.idDocumentoIndexDatiAggiuntivi
        };

        this.loadedModificaIrregolarita = true;
        this.subscribers.modificaIrregolarita = this.registroControlliService2.modificaIrregolarita(comodoRequestIrreg).subscribe(data => {
          this.loadedModificaIrregolarita = false;
          if (data.esito == true) {
            this.dialogRef.close({ esito: true });
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di modifica dell'irregolarità");
          this.loadedModificaIrregolarita = false;
        });
      } else {
        if (this.registroControlliService2.validate(this.esitoForm, "2" /*PROVVISORIA*/, this.dataInizioControlli, this.dataFineControlli, null, null, null)) {
          this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
          return;
        }
        // MODIFICA IRREGOLARITA' PROVVISORIA
        let comodoIrreg: Irregolarita = {
          dtComunicazioneProvv: this.dataInserimentoEsito,
          tipoControlliProvv: this.tipoControlliRadio == '1' ? 'D' : 'L',
          idPeriodoProvv: Number(this.annoContabileSelected.codice),
          idCategAnagraficaProvv: Number(this.autoritaControllanteSelected.codice),
          dataCampioneProvv: this.dataCampioneSelected == undefined ? null : this.dataCampioneSelected.descrizione,
          dataInizioControlliProvv: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
          dataFineControlliProvv: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
          importoIrregolaritaProvv: this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregProvv),
          importoAgevolazioneIrregProvv: this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregProvv),
          importoIrregolareCertificatoProvv: this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregProvv),
          idMotivoRevocaProvv: Number(this.motivoIrregIrregProvvSelected.codice),
          noteProvv: this.noteIrregProvv ? this.noteIrregProvv : "",
          idIrregolaritaProvv: Number(this.idIrregolarita),
          //esitoControllo: 'REGOLARE',
        };

        let request: RequestModificaIrregolarita = {
          idProgetto: Number(this.idProgetto),
          irregolarita: comodoIrreg,
          idU: this.user.idUtente,
          idBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
          codiceFiscale: this.user.beneficiarioSelezionato.codiceFiscale
        };

        this.loadedModificaIrregolaritaProvvisoria = true;
        this.subscribers.modificaIrregolaritaProvvisoria = this.registroControlliService.modificaIrregolaritaProvvisoria(request).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
          this.loadedModificaIrregolaritaProvvisoria = false;

          if (res.esito == true) {
            this.dialogRef.close({ esito: true });
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di modifica dell'irregolarità provvisoria");
          this.loadedModificaIrregolaritaProvvisoria = false;
        });
      }
    }
  }

  trasformaInChange() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.registroControlliService2.validate(this.esitoForm, "2" /*PROVVISORIA*/, this.dataInizioControlli, this.dataFineControlli, null, null, null)) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
      this.trasformaInRadio = '0';
      return;
    }


    let comodoIrreg: Irregolarita = {
      dataFineControlliProvv: new Date(Date.parse(this.dataFineControlli.value)).toLocaleDateString(),
      importoIrregolareCertificatoProvv: this.sharedService.getNumberFromFormattedString(this.importoIrregCertIrregProvv),
      idPeriodoProvv: Number(this.annoContabileSelected.codice),
      idEsitoControllo: null,
      importoIrregolaritaProvv: this.sharedService.getNumberFromFormattedString(this.importoSpesaIrregolareIrregProvv),
      dtComunicazioneProvv: this.dataInserimentoEsito,
      idIrregolaritaProvv: Number(this.idIrregolarita),
      flagIrregolaritaAnnullataProvv: this.flagIrregolaritaAnnullataProvv,
      tipoControlliProvv: this.tipoControlliRadio == '1' ? 'D' : 'L',
      dataInizioControlliProvv: new Date(Date.parse(this.dataInizioControlli.value)).toLocaleDateString(),
      idCategAnagraficaProvv: Number(this.autoritaControllanteSelected.codice),
      noteProvv: this.noteIrregProvv ? this.noteIrregProvv : "",
      dataCampioneProvv: this.dataCampioneSelected == undefined ? null : this.dataCampioneSelected.descrizione,
      idProgettoProvv: Number(this.idProgetto),
      importoAgevolazioneIrregProvv: this.sharedService.getNumberFromFormattedString(this.importoAgevIrrIrregProvv),
      idMotivoRevocaProvv: Number(this.motivoIrregIrregProvvSelected.codice),
      dtFineProvvisoriaProvv: this.dtFineProvvisoriaProvv,
      dtFineValiditaProvv: this.dtFineValiditaProvv
    };

    if (this.irregolari.find(irr => irr.idIrregolaritaProvv === this.idIrregolarita && irr.idIrregolarita !== irr.idIrregolaritaProvv)) {
      this.showMessageError("Attenzione! Irregolarità definitiva già esistente!");
      setTimeout(() => { this.trasformaInRadio = '0'; }, 10);
      return;
    }

    let request: RequestModificaIrregolarita = {
      idProgetto: Number(this.idProgetto),
      irregolarita: comodoIrreg,
      idU: this.user.idUtente,
      idBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
      codiceFiscale: this.user.beneficiarioSelezionato.codiceFiscale
    };

    this.loadedModificaIrregolaritaProvvisoria = true;
    this.subscribers.modificaIrregolaritaProvvisoria = this.registroControlliService.modificaIrregolaritaProvvisoria(request).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
      if (res && res.esito) {
        this.trasformata = true;
        this.showTrasformaRadio = false;

        if (this.trasformaInRadio == '1') {
          this.tipologia = "definitiva";
          this.importoSpesaIrregolareIrregDef = this.importoSpesaIrregolareIrregProvv;
          this.importoAgevIrrIrregDef = this.importoAgevIrrIrregProvv;
          this.importoIrregCertIrregDef = this.importoIrregCertIrregProvv;
          this.motiviIrregIrregDef = this.motiviIrregIrregProvv;
          this.motivoIrregIrregDefSelected = this.motivoIrregIrregProvvSelected;
          this.noteIrregDef = this.noteIrregProvv ? this.noteIrregProvv : "";
        } else {
          this.tipologia = "regolare";
          this.noteRegolare = this.noteIrregProvv ? this.noteIrregProvv : "";
        }
      } else {
        this.showMessageError("Errore in fase di modifica dell'irregolarità provvisoria");
      }
      this.loadedModificaIrregolaritaProvvisoria = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di modifica dell'irregolarità provvisoria");
      this.loadedModificaIrregolaritaProvvisoria = false;
    });
  }

  clearFileOlaf() {
    this.uploader.nativeElement.value = '';
  }

  clearFileDatiAgg() {
    this.uploaderDatiAgg.nativeElement.value = '';
  }

  uploadFileOlaf($event: any) {
    this.fileOLAF = $event.target.files[0];
    this.allegDatiAccessIrreg = this.fileOLAF.name;
  }

  annullaDatiAccessIrreg() {
    this.fileOLAF = undefined;
    this.allegDatiAccessIrreg = undefined;
  }

  uploadFileDatiAgg($event: any) {
    this.fileDatiAgg = $event.target.files[0];
    this.allegatiVariIntegIrreg = this.fileDatiAgg.name;
  }

  annullaAllegVariIntegIrreg() {
    this.fileDatiAgg = undefined;
    this.allegatiVariIntegIrreg = undefined;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (this.loadedGetPeriodi || this.loadedGetCategAnagrafica || this.loadedGetMotiviIrregolarita || this.loadedGetDettaglioIrregolarita
      || this.loadedGetDataCampione || this.loadedGetDettaglioIrregolaritaProvvisoria || this.loadedInserisciIrregolaritaDefinitiva
      || this.loadedInserisciIrregolaritaRegolare || this.loadedModificaIrregolarita || this.loadedModificaIrregolaritaProvvisoria || this.loadedGetDocumento) {
      return true;
    } else {
      return false;
    }
  }

  close() {
    this.dialogRef.close();
  }

}
