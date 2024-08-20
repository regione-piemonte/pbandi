/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { MatTableDataSource } from '@angular/material/table';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProposteRevocaResponseService } from '../../../services/proposte-revoca-response.service';
import { ArchiviaRevocaComponent } from '../archivia-revoca/archivia-revoca.component';
import { MatDialog } from '@angular/material/dialog';
import { Constants } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { ControlliService } from 'src/app/gestione-controlli/services/controlli.service';
import { InfoRevocaVO } from 'src/app/revoche/commons/proposte-revoca-dto/info-revoca-vo';
import { ImportiRevocaVO } from 'src/app/revoche/commons/proposte-revoca-dto/importi-revoca-vo';

@Component({
  selector: 'app-visualizza-modifica-revoca',
  templateUrl: './visualizza-modifica-revoca.component.html',
  styleUrls: ['./visualizza-modifica-revoca.component.scss']
})
export class VisualizzaModificaRevocaComponent implements OnInit {
  idGestioneRevoca;
  idDomanda;
  idSoggetto;
  informazioniBackend: InfoRevocaVO;
  archiviaRevocaDati;
  notaSolaLettura = true;
  dataSource = new MatTableDataSource<ImportiRevocaVO>();
  dataProva;
  displayedColumns: string[] = ["a", "ab", "b", "c", "d", "e", "f", "g","h"];
  noteBackend;
  statoProposta;
  erroreNota;
  isLoading:boolean = false;
  userloaded;
  subscribers;
  user;
  idUtente;

  //gestione errori
  error: boolean = false;
  success: boolean = false;
  messageError: string = "";

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private ProposteRevocaResponseService : ProposteRevocaResponseService,
    private router: Router,
    public dialog: MatDialog,
    private userService : UserService,)
    { }

  ngOnInit(): void {
    this.resetMessageError();
    this.isLoading = true;

    this.route.queryParams.subscribe(params => {
      this.idGestioneRevoca = params['idGestioneRevoca'],
      this.idDomanda = params['idDomanda'],
      this.idSoggetto = params['idSoggetto']
    });
   this.getInfoRevoca();
   this.getImportiRevoche();
   this.getNotaRevoche();

   this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
    });
  }

  getInfoRevoca(){
       this.ProposteRevocaResponseService.getInfoRevoca(this.idGestioneRevoca, this.idSoggetto, this.idDomanda)
      .subscribe((data) => {
        this.informazioniBackend = data;
        console.log("INFORMAZIONI BACKEND", this.informazioniBackend)
        this.isLoading = false;
      }, (err) => { });
  }

  getImportiRevoche(){
     this.ProposteRevocaResponseService.getImportiRevoche(this.idGestioneRevoca)
    .subscribe((data) => {
      this.dataSource = new MatTableDataSource(data);
    }, (err) => { });
  }

  getNotaRevoche(){
     this.ProposteRevocaResponseService.getNotaRevoche(this.idGestioneRevoca)
    .subscribe((data) => {
      this.noteBackend = data.nota;
    }, (err) => { });
  }



  archivia(element) {
    this.resetMessageError();
    console.log(element);
    const dialogRef = this.dialog.open(ArchiviaRevocaComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element,
        dataKey2:this.idUtente
      },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.ngOnInit();
      this.goBack();
    });
  }


  creaBozzaProcedimento(element){
    this.resetMessageError();
console.log(element.idGestioneRevoca,this.idUtente);

    this.ProposteRevocaResponseService.creaBozzaProcedimentoRevoca(element.idGestioneRevoca,this.idUtente)
    .subscribe((data) => {

      this.noteBackend = data.nota;
      this.isLoading = false;

      if (data.code == 'OK') {
        setTimeout(()=>{
          //this.errore = null;
          this.ngOnInit();
         }, 2000);

         this.goBack();
      }
      else if (data.code == 'KO') {
        this.showMessageError(data.message);
      }
    }, (err) => { });
  }

  creaBozzaProvvedimento(element){
    this.resetMessageError();
    console.log(element.idGestioneRevoca,this.idUtente);
    this.ProposteRevocaResponseService.creaBozzaProvvedimentoRevoca(element.idGestioneRevoca,this.idUtente)
    .subscribe((data) => {
      this.noteBackend = data.nota;
      this.isLoading = false;

      if (data.code == 'OK') {
        setTimeout(()=>{
          //this.errore = null;
          this.ngOnInit();
         }, 2000);
         this.goBack();
      }
      else if (data.code == 'KO') {
        this.showMessageError(data.message);
      }
    }, (err) => { });
  }

  salva(note, element) {
    this.resetMessageError();
    if(!note && this.informazioniBackend?.descStatoRevoca == 'Archiviato'){
      this.erroreNota = true;
        return
    } else {
      this.isLoading = true;
      this.erroreNota = false;
      let id_gestione_revoca = element.idGestioneRevoca.toString();
      let nota = note;

      this.ProposteRevocaResponseService.updateNotaRevoca(nota,id_gestione_revoca)
      .subscribe((data) => {
        // this.isLoading = false;

        if (data.code == 'OK') {
          setTimeout(()=>{
            //this.errore = null;
            this.ngOnInit();
          }, 2000);
        }
        else if (data.code == 'KO') {
          this.showMessageError(data.message);
        }
      }, (err) => { });

    }
  }

  goBack() {
    this.location.back();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }
}
