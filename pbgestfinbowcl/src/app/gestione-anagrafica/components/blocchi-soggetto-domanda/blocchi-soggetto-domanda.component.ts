/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/shared/services/shared.service';
import { BloccoVO } from '../../commons/dto/bloccoVO';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { EditBloccoComponent } from '../edit-blocco/edit-blocco.component';
import { StoricoBlocchiDomandaDialogComponent } from '../storico-blocchi-domanda-dialog/storico-blocchi-domanda-dialog.component';
@Component({
  selector: 'app-blocchi-soggetto-domanda',
  templateUrl: './blocchi-soggetto-domanda.component.html',
  styleUrls: ['./blocchi-soggetto-domanda.component.scss']
})
export class BlocchiSoggettoDomandaComponent implements OnInit {

  subscribers: any = {};
  listaBlocchi: Array<BloccoVO> = new Array<BloccoVO>();
  displayedColumnsBlocchi: string[] = ['dataBlocco', 'causale', 'azioni'];
  idEnteGiuridico: string;
  idVersione: string;
  numeroDomanda: string;
  idSoggetto: string;
  verificaSoggetto: string;
  isBlocchi: boolean;
  isSaveBlocco: boolean;
  veroIdDomanda: any;
  isProgetto: boolean;

  constructor(private anagBeneService: AnagraficaBeneficiarioService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private datiDomandaService: DatiDomandaService,
    private router: Router,
    private sharedService: SharedService,) { }

  ngOnInit(): void {
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get('numeroDomanda');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.verificaSoggetto = this.route.snapshot.queryParamMap.get('isEnteGiuridico');
    this.veroIdDomanda = this.route.snapshot.queryParamMap.get('idDomanda');

    this.getElencoBlocchiDomanda();
  }
  getElencoBlocchiDomanda() {
    this.subscribers.getElencoBlocchi = this.anagBeneService.getElencoBlocchiSoggettoDomanda(this.idSoggetto, this.numeroDomanda).subscribe(data => {
      if (data.length > 0) {
        this.listaBlocchi = data;
        this.isBlocchi = true;
      } else {
        this.listaBlocchi = data;
        this.isBlocchi = false;
      }
    }, err => {
      //this.bloccaSpinner();
      /// faccio qualcosa dopo
    });

    // controllo se la domanda ha un progetto per sapere se si possono inserire i blocchi oppure no
    this.subscribers.checkProgetto= this.datiDomandaService.checkProgetto(this.numeroDomanda).subscribe(data=>{
      if(data){
        this.isProgetto = data;
      } else {
        this.isProgetto =false;
      }
    })
  }

  openStoricoBlocchi() {
    this.isSaveBlocco = false;
    this.dialog.open(StoricoBlocchiDomandaDialogComponent, {
      width: '60%',
    });
  }

  

  inserisciBlocco() {
    this.isSaveBlocco = false;
    let dialogRef = this.dialog.open(EditBloccoComponent, {
      width: '50%',
      data: {
        domanda: 1
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        this.isSaveBlocco = true
        this.getElencoBlocchiDomanda()
      }
    })
  }
  modificaBlocco(bloccoVO: BloccoVO) {
    this.isSaveBlocco= false; 
    let dialogRef = this.dialog.open(EditBloccoComponent, {
      width: '40%',
      data: {
        domanda:2,
        bloccoVO: bloccoVO
      }
    });

    dialogRef.afterClosed().subscribe(data => {

      if(data ==true){
        this.isSaveBlocco =true
        this.getElencoBlocchiDomanda()
      }

    })
  }
}
