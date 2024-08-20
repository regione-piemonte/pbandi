/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { ControlliPreErogazioneResponseService } from '../../services/controlli-pre-erogazione-response.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DistintaInterventiVO } from '../../commons/distinta-interventi-vo';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-crea-distinta-interventi-dialog',
  templateUrl: './crea-distinta-interventi-dialog.component.html',
  styleUrls: ['./crea-distinta-interventi-dialog.component.scss']
})
export class CreaDistintaInterventiDialogComponent implements OnInit {
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
  
  //form
  myForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private controlliPreErogazioneService: ControlliPreErogazioneResponseService,
    public dialogRef: MatDialogRef<CreaDistintaInterventiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    console.log(this.data);
    this.myForm = this.fb.group({
      descrizione: new FormControl("", Validators.required),
    })
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

    //Creo l'oggetto da inviare
    let infoDistinta: DistintaInterventiVO = new DistintaInterventiVO();
    infoDistinta.idProposta = this.data.idProposta;
    infoDistinta.listaInterventi = this.data.listaInterventi;
    infoDistinta.descrizione = this.myForm.get("descrizione").value,

    //Faccio la chiamata
    this.controlliPreErogazioneService.creaDistintaInterventi(
      infoDistinta,
      this.letteraAccompagnatoria,
      this.visibilitaLettera,
      this.reportValidazioneSpesa,
      this.visibilitaReport,
      this.checklistInterna,
      this.visibilitaChecklist
    ).subscribe(data => {

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
    return this.letteraAccompagnatoria && this.checklistInterna && this.myForm.valid;
  }

}
