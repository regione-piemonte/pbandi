/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from '@pbandi/common-lib';
import { ControdeduzioniService } from '../../services/controdeduzioni.service';

@Component({
    selector: 'app-edit-proroga',
    templateUrl: 'dialog-add-proroga.component.html',
    styleUrls: ['dialog-add-proroga.component.scss']
})


export class DialogAddProroga implements OnInit {
  isSubmitted = false;
  myForm: FormGroup;
  motivazioni: Array<Object> = [];
  date: Date;
  errore = null;
  message;
  isLoading = false;
  stepProroga;
  inviataDataSource;
  inviataDisplayedColumns : string[] =  ['dataRichiestaProroga','numGiorniRichiesti','motivazioneProroga','numGiorniApprovati','descStatoProroga'];
  inviataDataSource2: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  ambito : string ;
 // @ViewChild(MatSort) sort: MatSort;
  

  constructor(
    public fb: FormBuilder,
    public datepipe: DatePipe,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private controdeduzioniService: ControdeduzioniService,
    private handleExceptionService: HandleExceptionService,
    public dialog: MatDialogRef<DialogAddProroga>) 
  
  {
    this.myForm = this.fb.group({
        numGiorniRichiesti: ['',[Validators.required],],
        motivazioneProroga: ['',[Validators.required],],
    });
   }

  ngOnInit(): void {
    this.getRichiestaProroga();
    this.stepProroga = this.data.numero;
  }


  

  getRichiestaProroga() {
    this.controdeduzioniService.getRichiestaProroga(this.data.element.idControdeduzione).subscribe(data => {
      this.inviataDataSource = data;
     //   this.inviataDataSource.sort = this.sort;
      
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    }); 
  } 

  get myForms() {
    return this.myForm.value;
  }

  onSubmit() {
    this.isSubmitted = true;
    if (!this.myForm.valid) {
      return false;
    } 
    else {
    let datiBackend = this.myForm.value;

      this.controdeduzioniService.richiediProroga(datiBackend,this.data.element.idControdeduzione).subscribe(
        (data) => {
           this.isSubmitted = false;
            if (data  == true) {
              this.message = "Proroga inserita con successo";
              this.errore = false;
              setTimeout(() => {
                this.errore = null;
                this.closeDialog();
              }, 2000); 
            }
            else if (data === false) {
              this.errore = true;
              this.message = "Non è stato possibile inserire la proroga";
            }
              },
              (err) => {
                if(err.ok == false){
                  this.errore = true;
                  this.message = err.statusText;
                }
                }
              );  
        }
        
    }
  



  controllaForm() {
    this.isSubmitted = true;
  }

  closeDialog() {
    this.dialog.close();
}

}
