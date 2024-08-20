/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { ControlliPreErogazioneResponseService } from '../../services/controlli-pre-erogazione-response.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-avvia-iter-dialog',
  templateUrl: './avvia-iter-dialog.component.html',
  styleUrls: ['./avvia-iter-dialog.component.scss']
})
export class AvviaIterDialogComponent implements OnInit {

  //PARAMETRI
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;

  //ALLEGATI
  letteraAccompagnatoria: File;
  visibilitaLettera: boolean = true;
  reportValidazioneSpesa: File;
  visibilitaReport: boolean = true;
  checklistInterna: File;
  visibilitaChecklist: boolean = false;

  constructor(
    private controlliPreErogazioneService: ControlliPreErogazioneResponseService,
    public dialogRef: MatDialogRef<AvviaIterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    console.log(this.data);
  }

  handleFileLetteraAccompagnatoria(files: Array<File>) {
    this.letteraAccompagnatoria = files[0];
    console.log("Lettera accompagnatoria caricata", this.letteraAccompagnatoria)
  }
  eliminaLetteraAccompagnatoria(uploadFile: any) {
    this.letteraAccompagnatoria = null;
    uploadFile.value = '';
  }
  handleFileReportValidazione(files: Array<File>) {
    this.reportValidazioneSpesa = files[0];
    console.log("Report validazione caricata", this.reportValidazioneSpesa)
  }
  eliminaReportValidazione(uploadFile: any) {
    this.reportValidazioneSpesa = null;
    uploadFile.value = '';
  }
  handleFileChecklistInterna(files: Array<File>) {
    this.checklistInterna = files[0];
    console.log("Checklist interna caricata", this.checklistInterna)
  }
  eliminaChecklistInterna(uploadFile: any) {
    this.checklistInterna = null;
    uploadFile.value = '';
  }

  toggleVisibilita(ambito: number) {
    switch(ambito) {
      case 1: {
        this.visibilitaLettera = !this.visibilitaLettera;
        break;
      }
      case 2: {
        this.visibilitaReport = !this.visibilitaReport;
        break;
      }
      case 3: {
        this.visibilitaChecklist = !this.visibilitaChecklist;
        break;
      }
    }
  }

  conferma() {
    console.log(this.data)

    if (this.data.idTipoIter == 18) {

      this.controlliPreErogazioneService.avviaIterEsitoValidazione(
        this.data.idProposta,
        this.letteraAccompagnatoria,
        this.visibilitaLettera,
        this.reportValidazioneSpesa,
        this.visibilitaReport,
        this.checklistInterna,
        this.visibilitaChecklist).subscribe(data => {

          if (data.code == "OK") {
            this.showMessageSuccess(data.message);
            setTimeout(() => {
              this.dialogRef.close();
            }, 1000);
          } else {
            this.showMessageError(data.message)
          }

        }, err => {
          this.showMessageError(err);
        });

    } else if (this.data.idTipoIter == 19) {

      this.controlliPreErogazioneService.avviaIterCommunicazioneIntervento(
        this.data.idProposta,
        this.letteraAccompagnatoria,
        this.visibilitaLettera).subscribe(data => {

          if (data.code == "OK") {
            this.showMessageSuccess(data.message);
            setTimeout(() => {
              this.dialogRef.close();
            }, 1000);
          } else {
            this.showMessageError(data.message)
          }

        }, err => {
          this.showMessageError(err);
        });

    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
  }
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.success = true;
  }
  resetMessage() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }

  validateForm() {
    if (this.data.idTipoIter == 18) {
      return this.letteraAccompagnatoria && this.checklistInterna;
    } else if (this.data.idTipoIter == 19) {
      return this.letteraAccompagnatoria;
    }
  }

}
