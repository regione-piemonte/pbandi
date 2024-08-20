/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { Injectable } from '@angular/core'; 
import { BehaviorSubject, Observable } from 'rxjs';
import { AttivitàIstruttoreAreaCreditiService } from '../../services/attivita-istruttore-area-crediti.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { state } from '@angular/animations';
import { Router } from '@angular/router';
import { Constants } from '../../../core/commons/util/constants';


@Component({
  selector: 'app-attivita-istruttore-area-crediti',
  templateUrl:'./attivita-istruttore-area-crediti.component.html',
  styleUrls: ['./attivita-istruttore-area-crediti.component.scss']
})
export class AttivitaIstruttoreAreaCreditiComponent implements OnInit {
  @Input() item ;
  @Input() idModalitaAgevolazione ;
  boxTrovate: any[];
  constructor(
    private attivitaIstruttoreAreaCreditiService: AttivitàIstruttoreAreaCreditiService,
    private router: Router, 
    ) { }

  spinner: boolean;
  ngOnInit(): void {
    this.getBoxList();
  }

  getBoxList() {
    this.attivitaIstruttoreAreaCreditiService.getBoxList(this.idModalitaAgevolazione,1).subscribe(data => { 
        console.log(data);
        this.boxTrovate = [data];
        },err => {
       });
  }

  

  goBack() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI + "/monitoraggioCrediti"]);
  }
  

  
}
