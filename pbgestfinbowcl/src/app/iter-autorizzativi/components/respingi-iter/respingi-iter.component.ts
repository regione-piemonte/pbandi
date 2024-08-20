/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { IterAutorizzativiService } from '../../services/iter-autorizzativi-service';

@Component({
  selector: 'app-respingi-iter',
  templateUrl: './respingi-iter.component.html',
  styleUrls: ['./respingi-iter.component.scss']
})
export class RespingiIterComponent implements OnInit {
  idWorkFlowList = new Array;
  isSubmitted :boolean = false;
  myForm: FormGroup;
  motivazioni: Array<Object> = [];
  @Input()count: any;
  @ViewChild('myaccordion') myPanels: MatAccordion;
  errore :boolean = null;
  message : string;
  
  constructor(private iterAutorizzativiService : IterAutorizzativiService ,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public fb: FormBuilder,
              public datepipe: DatePipe,
              public dialogRef: MatDialogRef<RespingiIterComponent>,) 
    
  {
    this.myForm = this.fb.group({
      idWorkFlow: this.data.dataKey.idWorkFlow,
      motivazione: ['', [Validators.required]],
      
    });
  }

  ngOnInit(): void {
    this.idWorkFlowList = this.data.dataKey.map(p =>p.idWorkFlow);
    
  }


  get myForms() {
    return this.myForm.value;
  }

  
   onSubmit() {
    this.isSubmitted = true;
    if (!this.myForm.valid) {
      return false;
    } else {
      let datiBackEnd  = this.myForm.value;
      let dataBack =  {
        idWorkFlowList :this.idWorkFlowList,
        motivazione:this.myForm.get('motivazione').value,
       }
       this.iterAutorizzativiService.respingiIter(dataBack).subscribe((data) => { 
          if (data == true) {
            this.message = "Operazione completata con successo";
            this.errore = false;
            this.ngOnInit();
          }
          else if (data == false) {
            this.errore = true;
            this.message  = "Non è stato possibile completare l'operazione";
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

  closeDialog() {
    this.dialogRef.close('chiudi');
  }

}
