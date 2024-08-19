/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizione } from 'src/app/disimpegni/commons/models/codice-descrizione';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { EsitoSalvaIrregolaritaDTO } from '../../commons/dto/esito-salva-irregolarita-dto';
import { Irregolarita } from '../../commons/models/irregolarita';
import { RequestModificaIrregolarita } from '../../commons/models/request-modifica-irregolarita';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';

@Component({
  selector: 'app-modifica-regolari',
  templateUrl: './modifica-regolari.component.html',
  styleUrls: ['./modifica-regolari.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ModificaRegolariComponent implements OnInit {

  idProgetto: string;
  beneficiario: string;
  codiceProgetto: string;
  user: UserInfoSec;
  noteRegolare: string;

  idEsitoControllo: string;

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

  saved: boolean;

  @ViewChild(NgForm) esitoForm: NgForm;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  loadedGetPeriodi: boolean;
  loadedGetCategAnagrafica: boolean;
  loadedGetDettaglioEsitoRegolare: boolean;
  loadedGetDataCampione: boolean;
  loadedModificaEsitoRegolare: boolean;

  constructor(
    private userService: UserService,
    private registroControlliService: RegistroControlliService,
    public dialogRef: MatDialogRef<ModificaRegolariComponent>,
    private handleExceptionService: HandleExceptionService,
    private registroControlliService2: RegistroControlliService2,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idEsitoControllo = this.data.idEsitoControllo;
    this.beneficiario = this.data.beneficiario;
    this.codiceProgetto = this.data.codiceProgetto;
    this.idProgetto = this.data.idProgetto;

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.loadedGetPeriodi = true;
        this.subscribers.getPeriodi = this.registroControlliService.getPeriodi().subscribe((res1: Array<CodiceDescrizione>) => {
          this.anniContabile = res1;
          this.loadedGetPeriodi = false;

          this.loadedGetCategAnagrafica = true;
          this.subscribers.getCategAnagrafica = this.registroControlliService.getCategAnagrafica().subscribe((res2: Array<CodiceDescrizione>) => {
            this.autoritasControllante = res2;
            this.loadedGetCategAnagrafica = false;

            this.loadedGetDettaglioEsitoRegolare = true;
            this.subscribers.getDettaglioEsitoRegolare = this.registroControlliService.getDettaglioEsitoRegolare(Number(this.idEsitoControllo)).subscribe((res3: Irregolarita) => {
              this.loadedGetDettaglioEsitoRegolare = false;

              this.dataInserimentoEsito = new Date(res3.dtComunicazione).toLocaleDateString();
              this.tipoControlliRadio = res3.tipoControlli == "D" ? "1" : "2";
              this.annoContabileSelected = this.anniContabile.find(d => Number(d.codice) == res3.idPeriodo);
              this.autoritaControllanteSelected = this.autoritasControllante.find(d => Number(d.codice) == res3.idCategAnagrafica);

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

                if (res3.dataCampione == null) {
                  this.dataCampioneSelected = this.dateCampione.find(d => d.descrizione == "Nessuno");
                } else {
                  this.dataCampioneSelected = this.dateCampione.find(d => d.descrizione == (new Date(res3.dataCampione).toLocaleDateString("en-GB", { // you can use undefined as first argument
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

              this.dataInizioControlli.setValue(new Date(res3.dataInizioControlli));
              this.dataFineControlli.setValue(new Date(res3.dataFineControlli));
              this.noteRegolare = res3.note;
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di ottenimento del dettaglio della regolarità");
              this.loadedGetDettaglioEsitoRegolare = false;
            });
          }, err => {
            this.loadedGetCategAnagrafica = false;
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di ottenimento delle categ anagrafiche");
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
      let request: RegistroControlliService.GetDataCampioneParams = {
        tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
        idProgetto: Number(this.idProgetto),
        idPeriodo: Number(this.annoContabileSelected.codice),
        idCategAnagrafica: Number(this.autoritaControllanteSelected.codice)
      };

      this.loadedGetDataCampione = true;
      this.subscribers.getDataCampione = this.registroControlliService.getDataCampione(request).subscribe((res: Array<CodiceDescrizione>) => {
        this.loadedGetDataCampione = false;

        if (res.length == 0) {

          let comodo: CodiceDescrizione = {
            codice: '-1',
            descrizione: 'Nessuno'
          }

          this.dateCampione = new Array<CodiceDescrizione>();
          this.dateCampione.push(comodo)
        } else {
          this.dateCampione = res;
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ottenimento delle date campione");
        this.loadedGetDataCampione = false;
      });
    } else {
      this.dataCampioneSelected = undefined;
      this.dateCampione = new Array<CodiceDescrizione>();
    }
  }

  changeAutControl() {
    if (!(this.annoContabileSelected == undefined) && !(this.autoritaControllanteSelected == undefined)) {
      let request: RegistroControlliService.GetDataCampioneParams = {
        tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
        idProgetto: Number(this.idProgetto),
        idPeriodo: Number(this.annoContabileSelected.codice),
        idCategAnagrafica: Number(this.autoritaControllanteSelected.codice)
      };

      this.loadedGetDataCampione = true;
      this.subscribers.getDataCampione = this.registroControlliService.getDataCampione(request).subscribe((res: Array<CodiceDescrizione>) => {
        this.loadedGetDataCampione = false;

        if (res.length == 0) {

          let comodo: CodiceDescrizione = {
            codice: '-1',
            descrizione: 'Nessuno'
          }

          this.dateCampione = new Array<CodiceDescrizione>();
          this.dateCampione.push(comodo)
        } else {
          this.dateCampione = res;
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ottenimento delle date campione");
        this.loadedGetDataCampione = false;
      });
    } else {
      this.dataCampioneSelected = undefined;
      this.dateCampione = new Array<CodiceDescrizione>();
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

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.registroControlliService2.validate(this.esitoForm, "1"/*REGOLARE*/, this.dataInizioControlli, this.dataFineControlli, null, null, null)) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
      return;
    }
    let comodoIrregolarita: Irregolarita = {
      idEsitoControllo: Number(this.idEsitoControllo),
      esitoControllo: 'REGOLARE',
      idPeriodo: Number(this.annoContabileSelected.codice),
      idCategAnagrafica: Number(this.autoritaControllanteSelected.codice),
      tipoControlli: this.tipoControlliRadio == '1' ? 'D' : 'L',
      dtComunicazioneProvv: this.dataInserimentoEsito,
      dataInizioControlli: this.dataInizioControlli && this.dataInizioControlli.value ? new Date(this.dataInizioControlli.value).toLocaleDateString() : null,
      dataFineControlli: this.dataFineControlli && this.dataFineControlli.value ? new Date(this.dataFineControlli.value).toLocaleDateString() : null,
      note: this.noteRegolare,
      dataCampione: this.dataCampioneSelected == undefined ? null : this.dataCampioneSelected.descrizione,
    };

    let request: RequestModificaIrregolarita = {
      idProgetto: Number(this.idProgetto),
      irregolarita: comodoIrregolarita,
      idU: this.user.idUtente,
      idBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
      codiceFiscale: this.user.beneficiarioSelezionato.codiceFiscale
    };

    this.loadedModificaEsitoRegolare = true;
    this.subscribers.modificaEsitoRegolare = this.registroControlliService.modificaEsitoRegolare(request).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
      this.loadedModificaEsitoRegolare = false;

      if (res.esito == true) {
        this.showMessageSuccess("Salvataggio avvenuto con successo.");
        this.saved = true;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di modifica regolarità");
      this.loadedModificaEsitoRegolare = false;
    });
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

  isLoading() {
    if (this.loadedGetPeriodi || this.loadedGetCategAnagrafica || this.loadedGetDettaglioEsitoRegolare || this.loadedGetDataCampione || this.loadedModificaEsitoRegolare) {
      return true;
    } else {
      return false;
    }
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

  close() {
    this.dialogRef.close(this.saved);
  }
}
