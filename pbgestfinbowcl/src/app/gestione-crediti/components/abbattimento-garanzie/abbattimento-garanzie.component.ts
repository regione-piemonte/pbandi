/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from '@pbandi/common-lib';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AbbattimentoGaranzieVO } from '../../commons/dto/abbattimento-garanzieVO';
import { AttivitaDTO } from '../../commons/dto/attivita-dto';
import { StoricoAbbattimentoGaranziaDTO } from '../../commons/dto/storico-abbattimento-garanzia-dto';
import { AbbattimentoGaranzieService } from '../../services/abbattimento-garanzie.service';
import { DialogEditAbbattimentoGaranzieComponent } from '../dialog-edit-abbattimento-garanzie/dialog-edit-abbattimento-garanzie.component';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';

@Component({
  selector: 'app-abbattimento-garanzie',
  templateUrl: './abbattimento-garanzie.component.html',
  styleUrls: ['./abbattimento-garanzie.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AbbattimentoGaranzieComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  user: UserInfoSec;
  idUtente: number;
  abbattimentoGaranzieVO: AbbattimentoGaranzieVO;
  isStorico: boolean = false;
  isStorico2: boolean = false;
  isAttiva: boolean = true;
  idProgetto: number;
  tipoGaranzia: string;
  storicoAbbattimentoGaranzia: Array<StoricoAbbattimentoGaranziaDTO>;
  storicoAbbattimenti: Array<StoricoAbbattimentoGaranziaDTO>;
  subscribers: any = {};
  displayedColumns: string[] = ['tipoGaranzia', 'dataAbbattimento', 'utente', 'actions'];
  loadedUser: boolean;
  loadedabbattimenti: boolean;
  isSaveSuccess: boolean;
  dialogSaving: boolean = true;
  isSaveError: boolean;
  disableStorico: boolean;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private abbattimentoGaranzieService: AbbattimentoGaranzieService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
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

        this.loadAbbattimenti();
        this.storico();
      }
    })
  }

  storico() {

    this.subscribers.storicoAbbatimenti = this.abbattimentoGaranzieService.getStoricoAbbattimenti(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(
      data => {
        if (data.length>0) {
          this.storicoAbbattimenti = data;
        } else {
          this.disableStorico = true; 
        }

      })

  }

  openDialog() {
    this.isSaveSuccess = false; 
    this.dialogSaving = false;
    let dialogRef = this.dialog.open(DialogEditAbbattimentoGaranzieComponent, {
      width: '30%',
      data: {
        idAbbattimentoGaranzia: null,
        nomeBox : this.nomeBox,
        idProgetto : this.idProgetto,
        idModalitaAgevolazione : this.idModalitaAgevolazione,
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        this.isSaveSuccess= data; 
        this.loadAbbattimenti();
        this.storico();
        this.dialogSaving = true
        this.disableStorico = false;
      } else{
        this.dialogSaving = true;
        this.isSaveError = true;
      }
    })

  }

  modifica(idAbbattimentoGaranzia: number) {
    this.isSaveSuccess= false;
    this.dialogSaving = false;
      let dialogRef = this.dialog.open(DialogEditAbbattimentoGaranzieComponent, {
      width: '30%',
      data: {
        idAbbattimentoGaranzia: idAbbattimentoGaranzia,
        nomeBox : this.nomeBox,
        idProgetto : this.idProgetto,
        idModalitaAgevolazione : this.idModalitaAgevolazione,
      }
    })

    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        this.isSaveSuccess = data;
        this.loadAbbattimenti();
        this.storico();
        this.dialogSaving = true;
      } else{
        this.dialogSaving = true; 
        this.isSaveError = true; 
      }
    }); 
    
  }

  loadAbbattimenti() {

    this.loadedabbattimenti = false;

    this.subscribers.storico = this.abbattimentoGaranzieService.getStoricoAbbattimentoGaranzia(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data) {
        this.storicoAbbattimentoGaranzia = data;
        if (this.storicoAbbattimentoGaranzia.length > 0) {
          this.isStorico = true;
        }
        this.loadedabbattimenti = true;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    })

  }

  openStorico() {
    this.isSaveSuccess = null; 
    this.isSaveError = false; 
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '600px',
      data: {
        id: 3,
        storico: this.storicoAbbattimenti

      }
    });
  }

  isLoading() {
    if (!this.loadedabbattimenti || !this.loadedUser) {
      return true;
    }
    return false;
  }


}
