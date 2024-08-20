/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { StoricoSegnalazioneCorteContiDTO } from '../../commons/dto/storico-segnalazione-corte-conti-dto';
import { SegnalazioneCorteContiService } from '../../services/segnalazione-corte-conti-service';
import { DialogEditSegnalazioneCorteContiComponent } from '../dialog-edit-segnalazione-corte-conti/dialog-edit-segnalazione-corte-conti.component';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';


@Component({
  selector: 'app-segnalazione-corte-conti',
  templateUrl: './segnalazione-corte-conti.component.html',
  styleUrls: ['./segnalazione-corte-conti.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class SegnalazioneCorteContiComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  allegati:any;
  subscribers: any = {}; 
  user :  UserInfoSec; 
  idUtente: number; 
  idProgetto: number; 
  listaSegnalazioniCC: Array<StoricoSegnalazioneCorteContiDTO>; 
  storicoSegnalazioni: Array<StoricoSegnalazioneCorteContiDTO>; 
  isListaSegnalazioni: boolean; 
  isStorico :  boolean; 
  isConferma: boolean = true;

  displayedColumns: string[] = ['dataSegn',  'numProtocollo' ,'dataArchiviaz', 'utente','actions'];
  loadedSegnalazioni: boolean;
  loadedUser: boolean;
  isStoricoLength: boolean;
  loadedStorico: boolean = true;
  isSaveSuccess: boolean; 
  isSaveError: boolean;
  disableStorico: boolean; 

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private segnalazioneCorteContiService: SegnalazioneCorteContiService, 
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadedUser = false; 
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.loadedUser = true;
          this.idProgetto = this.item;
          this.caricaSegnalazioni();
          this.getStorico(); 
      }
    });

  }
  caricaSegnalazioni(){
    this.loadedSegnalazioni = false; 

      this.subscribers.listaSegnalazioniCC =  this.segnalazioneCorteContiService.getStoricoSegnalazioniCorteConti(this.item,this.idModalitaAgevolazione)
      .subscribe(data=>{
        if(data)
        this.listaSegnalazioniCC = data; 
        if(this.listaSegnalazioniCC.length>0)
        this.isListaSegnalazioni =  true;

        this.loadedSegnalazioni = true; 
      }); 
   }

   getStorico(){
     this.subscribers.storico = this.segnalazioneCorteContiService.getStorico(this.item,this.idModalitaAgevolazione).subscribe(data=>{
       if(data.length>0){
         this.storicoSegnalazioni = data;
         this.loadedStorico = true;  
       }else{
         this.disableStorico = true;
       }
     }); 

   }

   
  openDialog() {
    
    this.isSaveSuccess = false; 
    this.isSaveError = false; 
    let dialogRef = this.dialog.open(DialogEditSegnalazioneCorteContiComponent,{
      width: '45%',
      data: {
        idSegnalazioneCC: null,
        nomeBox : this.nomeBox,
        idProgetto : this.idProgetto,
        idModalitaAgevolazione : this.idModalitaAgevolazione,
      }
    });
    dialogRef.afterClosed().subscribe(data=>{
      if(data == true){
        this.caricaSegnalazioni();
        this.getStorico();
        this.isSaveSuccess= data; 
        this.disableStorico = false;
      } else if(data==false){
        this.isSaveError = true; 
      }
    });
  }

  modifica(idSegnalazioneCC: number){
    this.isSaveSuccess = false; 
    this.isSaveError = false; 
    let dialogRef = this.dialog.open(DialogEditSegnalazioneCorteContiComponent,{
      width: '45%',
      data: {
        idSegnalazioneCC: idSegnalazioneCC,
        nomeBox : this.nomeBox,
        idProgetto : this.idProgetto,
        idModalitaAgevolazione : this.idModalitaAgevolazione,
      }
    });
    dialogRef.afterClosed().subscribe(data=>{
      if(data == true){
        this.caricaSegnalazioni();
        this.getStorico();
          this.isSaveSuccess = data; 
        } else if(data==false){
          this.isSaveError = true; 
        }
    });
  }

  openStorico() {

    this.isSaveSuccess = false;
    this.isSaveError = false;
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '55%',
      data: {
        id: 7,
        storico: this.storicoSegnalazioni

      }
    });
  }
  

  isLoading(){
    if (!this.loadedSegnalazioni || !this.loadedUser || !this.loadedStorico) {
      return true;
    }
    return false;
  }


}
