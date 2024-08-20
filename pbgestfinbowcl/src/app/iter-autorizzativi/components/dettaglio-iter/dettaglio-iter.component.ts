/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { DettaglioIterVO } from '../../commons/dettaglio-iter-vo';
import { IterAutorizzativiService } from '../../services/iter-autorizzativi-service';

@Component({
  selector: 'app-dettaglio-iter',
  templateUrl: './dettaglio-iter.component.html',
  styleUrls: ['./dettaglio-iter.component.scss']
})
export class DettaglioIterComponent implements OnInit {

  isSubmitted :boolean = false;
  motivazioni: Array<Object> = [];
  errore:boolean = null;
  message :string;

  dettaglioIter : DettaglioIterVO[] = [];
  constructor(private iterAutorizzativiService : IterAutorizzativiService ,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public datepipe: DatePipe,) 
    
  {
   
  }

  ngOnInit(): void {
    this.getDettaglioIter();
  }


  getDettaglioIter() {
      this.iterAutorizzativiService.getDettaglioIter(this.data.dataKey.idWorkFlow).subscribe(data =>  {
        this.dettaglioIter = data;
      }, err => {


      });

    }


}
