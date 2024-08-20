/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { animate, state, style, transition, trigger} from '@angular/animations';
import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from '@pbandi/common-lib';
import { Subscriber } from 'rxjs';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { StoricoMessaMoraDTO } from '../../commons/dto/storico-messa-mora-dto';
import { MessaMoraService } from '../../services/messa-mora-services';
import { DialogEditMessaMoraComponent } from './dialog-edit-messa-mora/dialog-edit-messa-mora.component';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';

@Component({
  selector: 'app-messa-mora',
  templateUrl: './messa-mora.component.html',
  styleUrls: ['./messa-mora.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class MessaMoraComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: number;
  idProgetto: number;
  storicoMessaMora: Array<StoricoMessaMoraDTO>;
  storico: Array<StoricoMessaMoraDTO>;
  isStoricoMesse: boolean;
  isStorico: boolean;
  isConferma: boolean = true;

  displayedColumns: string[] = [ 'dataEsito', 'numeroProtocollo', 'utente', 'actions'];
  loadedMesse: boolean;
  loadedUser: boolean;
  isSaveSuccess: boolean;
  isSaveError: boolean;
  disableStorico: boolean;



  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private messaMoraService: MessaMoraService,
    private userService: UserService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadedUser = false; 
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.loadedUser = true; 
        this.idProgetto = this.item;
        this.caricaMesse();
        this.getStorico();
      }
    });

  }

  caricaMesse() {

    this.loadedMesse = false; 
    this.subscribers.messe = this.messaMoraService.getStoricoMessaMora(this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data) {
        this.storicoMessaMora = data;
        if (this.storicoMessaMora.length > 0) {
          this.isStoricoMesse = true;
        }

        this.loadedMesse = true; 
      }
    });
  }

  getStorico() {   
    this.subscribers.storico = this.messaMoraService.getStorico(this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data.length>0){
        this.storico = data;
      } else{
        this.disableStorico = true; 
      }

    });
  }


  openDialog() {
    this.isSaveSuccess = false;
    let dialogRef = this.dialog.open(DialogEditMessaMoraComponent,{
      width: '40%',
      data: {
        idMessaMora: null,
        nomeBox : this.nomeBox,
        idProgetto : this.idProgetto,
        idModalitaAgevolazione : this.idModalitaAgevolazione,
      }
    });
    dialogRef.afterClosed().subscribe(data=>{
      if(data == true){
        this.caricaMesse();
        this.getStorico();
        this.isSaveSuccess = data; 
        this.disableStorico = false;
      }else if(data== false){
        this.isSaveError= false;
      }
    });
  }

  modifica(idMessaMora: number){
    this.isSaveSuccess = false;
    let dialogRef = this.dialog.open(DialogEditMessaMoraComponent,{
      width: '40%',
      data: {
        idMessaMora: idMessaMora,
        nomeBox : this.nomeBox,
        idProgetto : this.idProgetto,
        idModalitaAgevolazione : this.idModalitaAgevolazione,
      }
    });
    dialogRef.afterClosed().subscribe(data=>{
      if(data == true){
        this.caricaMesse();
        this.getStorico(); 
        this.isSaveSuccess=data;  
      } else if(data== false){
        this.isSaveError= false;
      }
    });
  }
  openStorico() {
    this.isSaveSuccess = false; 
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '55%',
      data: {
        id: 6,
        storico: this.storico
      }
    });
  }
  
  isLoading(){
    if (!this.loadedMesse || !this.loadedUser) {
      return true;
    }
    return false;
  }

}

