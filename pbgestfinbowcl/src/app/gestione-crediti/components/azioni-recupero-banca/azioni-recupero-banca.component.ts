/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { observable } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from '../../commons/dto/attivita-dto';
import { AzioneRecuperoBancaVO } from '../../commons/dto/azione-recupero-bancaVO';
import { StoricoAzioneRecuperoBancaDTO } from '../../commons/dto/storico-azione-recupero-banca-dto';
import { AzioniRecuperoBancaService } from '../../services/azioni-recupero-banca-service';
import { DialogEditAzioneRecuperoBancaComponent } from '../dialog-edit-azione-recupero-banca/dialog-edit-azione-recupero-banca.component';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';

@Component({
  selector: 'app-azioni-recupero-banca',
  templateUrl: './azioni-recupero-banca.component.html',
  styleUrls: ['./azioni-recupero-banca.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AzioniRecuperoBancaComponent implements OnInit {
  @Input() item ;
  @Input() ambito ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  azioneRecuperoBancaVO: AzioneRecuperoBancaVO; 
  subscribers:  any = {} ;
  user: UserInfoSec;
  isStorico : boolean = false;
  isStorico2 : boolean = false;
  isAttiva: boolean = true; 

  idUtente: number; 
  storicoAzioniRecupBanca: Array<StoricoAzioneRecuperoBancaDTO>;
  storicoAzioni: Array<StoricoAzioneRecuperoBancaDTO>;
  idProgetto: number;
  dataAzione: Date;
  numProtocollo: number;
  note: string;
  idAttivitaAzione: number;
  descAttivitaAzione: string;
  attivitaAzione: AttivitaDTO; 
  loadedUser: boolean; 
  loadedAzioni: boolean;
  isSaveSuccess:boolean; 

  displayedColumns: string[] = ['azione', 'dataAzione', 'utente', 'actions'];
  displayedColumns2: string[] = ['dataInserimento', 'istruttore', 'Azione'];
  isStoricoLength: boolean;
  loadedStorico: boolean = true;
  disableStorico:  boolean; 

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private azioneRecuperoBancaService: AzioniRecuperoBancaService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialog: MatDialog
  
  ) {}



  ngOnInit(): void {

  //console.log(this.item);
  
  this.route.queryParams.subscribe(params => {
    this.item = params.idProgetto;
  });

    this.loadedUser = false
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        console.log(this.user.codiceRuolo);
        this.idUtente = this.user.idUtente;
        this.loadedUser =  true; 
        this.idProgetto = this.item
        this.loadAzioni();
        this.storico(); 
      
      }
    },  err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });

    
  }

 
  openDialog() {
    this.isSaveSuccess = false;
    let dialogRef = this.dialog.open(DialogEditAzioneRecuperoBancaComponent,{
      width: '30%',
      data: {
        idAzioneRecuperoBanca: null,
        nomeBox : this.nomeBox,
        ambito : this.ambito,
        idModalitaAgevolazione : this.idModalitaAgevolazione,
        idProgetto : this.item,
      }
    });
    dialogRef.afterClosed().subscribe(data=>{
      if(data == true){
        this.loadAzioni();
        this.storico(); 
        this.isSaveSuccess= data;
        this.disableStorico = false;
      }
    });

  }

  modifica(idAzioneRecuperoBanca: number){
    this.isSaveSuccess = false;
      let dialogRef = this.dialog.open(DialogEditAzioneRecuperoBancaComponent,{
        width: '30%',
        data: {
          idAzioneRecuperoBanca: idAzioneRecuperoBanca,
          nomeBox : this.nomeBox,
          ambito : this.ambito,
          idModalitaAgevolazione : this.idModalitaAgevolazione,
          idProgetto : this.item,
        }
      });
      dialogRef.afterClosed().subscribe(data=>{
        if(data == true){
          this.loadAzioni();
          this.storico(); 
          this.isSaveSuccess= data;
        }
      });
  }

  storico(){ 
    this.subscribers.storicoAzioni = this.azioneRecuperoBancaService.getStoricoAzioni(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(
      data=>{
        if(data.length>0){
          this.storicoAzioni = data;
          this.isStoricoLength=true;   
        } else{
          this.disableStorico = true; 
        }
        this.loadedStorico= true; 
      });     
  }

  loadAzioni(){
    this.loadedAzioni =  false; 
    this.subscribers.storico = this.azioneRecuperoBancaService.getStoricoAzioneRecupBanca(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(data=>{
      if(data){
        this.storicoAzioniRecupBanca= data; 
        if(this.storicoAzioniRecupBanca.length>0){
          this.isStorico = true; 
        }    
      }
      this.loadedAzioni =  true; 

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    }); 

  }

  openStorico() {
    this.isSaveSuccess = false;
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '600px',
      data: {
        id: 2,
        storico: this.storicoAzioni
      }
    });
  }



  isLoading(){
    if (!this.loadedAzioni || !this.loadedUser || !this.loadedStorico) {
      return true;
    }
    return false;
  }
}

