/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { ArchivioRevocaVO } from 'src/app/revoche/commons/proposte-revoca-dto/archivio-revoca-vo';
import { ProposteRevocaResponseService } from '../../../services/proposte-revoca-response.service';

@Component({
  selector: 'app-archivia-revoca',
  templateUrl: './archivia-revoca.component.html',
  styleUrls: ['./archivia-revoca.component.scss']
})
export class ArchiviaRevocaComponent implements OnInit {

  isSubmitted = false;
  myForm: FormGroup;
  motivazioni: Array<Object> = [];
  @Input()
  count: any;
  @ViewChild('myaccordion') myPanels: MatAccordion;
  public tipo_elaborazione = this.data.dataKey.tipo_elaborazione;
  idFunzionario: number;
  errore = null;
  message;
  constructor(private ProposteRevocaResponseService :ProposteRevocaResponseService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public fb: FormBuilder,
              public datepipe: DatePipe,)

  {
    this.myForm = this.fb.group({
      idGestioneRevoca: this.data.dataKey.idGestioneRevoca,
      noteRevoca: ['', [Validators.required]],

    });
  }

  ngOnInit(): void {
    console.log(this.data.dataKey2);


  }
  get myForms() {
    return this.myForm.value;
  }

  // Submit Registration Form
   onSubmit() {
    this.isSubmitted = true;
    if (!this.myForm.valid) {
      return false;
    } else {
      let datiBackEnd : ArchivioRevocaVO = this.myForm.value
      this.ProposteRevocaResponseService.archiviaRevoca(datiBackEnd,this.data.dataKey2)
        .subscribe((data) => {
          if (data.code == 'OK') {
            this.message = data.message;
            this.errore = false;
          }
          else if (data.code == 'KO') {
            this.errore = true;
            this.message = data.message;
          }
        },
          (err) => {


              this.errore = true;
              this.message = err.statusText;

          }
        );
    }
  }



  controllaForm() {
    this.isSubmitted = true;
  }
}
