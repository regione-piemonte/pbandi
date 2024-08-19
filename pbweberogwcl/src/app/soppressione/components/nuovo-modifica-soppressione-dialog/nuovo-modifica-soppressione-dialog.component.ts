/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from '@pbandi/common-lib';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Soppressione } from '../../commons/requests/soppressione';
import { SoppressioneService } from '../../services/soppressione.service';

@Component({
  selector: 'app-nuovo-modifica-soppressione-dialog',
  templateUrl: './nuovo-modifica-soppressione-dialog.component.html',
  styleUrls: ['./nuovo-modifica-soppressione-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoModificaSoppressioneDialogComponent implements OnInit {

  isNuovo: boolean;
  idProgetto: number;
  importoCertificabileLordo: number;
  importoTotaleDisimpegni: number;
  soppressione: Soppressione;
  dataSoppr: FormControl = new FormControl();
  importo: number;
  importoFormatted: string;
  estremi: string;
  note: string;
  flagMonitoraggio: boolean;

  @ViewChild(NgForm) sopprForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedSalva: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private sharedService: SharedService,
    private soppressioneService: SoppressioneService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<NuovoModificaSoppressioneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.isNuovo = this.data.isNuovo;
    this.idProgetto = this.data.idProgetto;
    this.importoCertificabileLordo = this.data.importoCertificabileLordo;
    this.importoTotaleDisimpegni = this.data.importoTotaleDisimpegni;
    if (!this.isNuovo) {
      this.soppressione = this.data.soppressione;
      if (this.soppressione.dataSoppressione) {
        this.dataSoppr.setValue(new Date(this.soppressione.dataSoppressione));
      }
      this.importo = this.soppressione.importoSoppressione;
      if (this.importo != null) {
        this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
      }
      this.estremi = this.soppressione.estremiDetermina;
      this.note = this.soppressione.note;
      this.flagMonitoraggio = this.soppressione.flagMonit === "S" ? true : false;
    }
  }

  setImporto() {
    this.importo = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
    if (this.importo != null) {
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }
  }

  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    let hasError: boolean;
    if (this.importo === null || this.importo == undefined) {
      this.sopprForm.form.get('importo').setErrors({ error: 'required' });
      hasError = true;
    } else {
      let from: number = 0;
      let to: number = this.importoCertificabileLordo;
      if (to !== null && to !== undefined && from > to) {
        let temp: number = from;
        from = to;
        to = temp;
      }
      let failed: boolean =
        (from !== null && from !== undefined && this.importo < from) ||
        (to !== null && to !== undefined && this.importo > to)
        ;
      if (failed) {
        hasError = true;
        this.sopprForm.form.get('importo').setErrors({ error: 'notValid' });
      }
    }
    if (!this.dataSoppr || !this.dataSoppr.value) {
      this.dataSoppr.setErrors({ error: 'required' });
      this.dataSoppr.markAllAsTouched();
      hasError = true;
    }
    this.sopprForm.form.markAllAsTouched();
    if (hasError) {
      this.showMessageError("Inserire tutti i campi obbligatori.")
      return;
    }

    if (!this.soppressione) {
      this.soppressione = new Soppressione();
    }
    this.soppressione.importoSoppressione = this.importo;
    this.soppressione.dataSoppressione = this.dataSoppr && this.dataSoppr.value ? this.dataSoppr.value : null;
    this.soppressione.idProgetto = this.idProgetto;
    this.soppressione.estremiDetermina = this.estremi;
    this.soppressione.note = this.note;
    this.soppressione.flagMonit = this.flagMonitoraggio ? "S" : "N";
    this.loadedSalva = false;
    this.subscribers.salva = this.soppressioneService.salvaSoppressione(this.soppressione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.dialogRef.close(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  close() {
    this.dialogRef.close();
  }

  isLoading() {
    if (!this.loadedSalva) {
      return true;
    }
    return false;
  }

}
