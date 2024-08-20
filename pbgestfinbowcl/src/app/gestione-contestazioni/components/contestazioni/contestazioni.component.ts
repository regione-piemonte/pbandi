/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficiarioSec, Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ContestazioniService } from '../../services/contestazioni.service';
import { AllegatiContestazioneComponent } from '../allegati-contestazione/allegati-contestazione.component';
import { ConfermaInvioContestazioneComponent } from '../conferma-invio-contestazione/conferma-invio-contestazione.component';
import { DettaglioAllegatiContestazioniComponent } from '../dettaglio-allegati-contestazioni/dettaglio-allegati-contestazioni.component';
import { EliminaContestazioneComponent } from '../elimina-contestazione/elimina-contestazione.component';
import { DialogAddControdeduzione } from 'src/app/gestione-controdeduzioni/components/dialog-add-controdeduzione/dialog-add-controdeduzione.component';
import { InserisciContestazioneComponent } from '../inserisci-contestazione/inserisci-contestazione.component';
import { OkDialogComponent } from 'src/app/iter-autorizzativi/components/iter-autorizzativi/ok-dialog/ok-dialog.component';

@Component({
  selector: 'app-contestazioni',
  templateUrl: './contestazioni.component.html',
  styleUrls: ['./contestazioni.component.scss']
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class ContestazioniComponent implements OnInit {
  idOperazione: number;
  idUtente: number;
  idProgetto: number;
  idBandoLinea: number;
  subscribers: any = {};
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  isMessageErrorVisible: boolean;
  messageError: string;
  listTab;
  inserimentoAbilitato = null;
  dataResult: Array<any> = [];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  user: UserInfoSec;
  showSucc: boolean = false;
  showError: boolean = false;
  columns: string[] = ['numeroProvvedimentoRevoca','dataNotifica','causaRevoca', 'numeroProtocollo','numeroContestazione','descStatoContestazione','dtStatoContestazione','dataScadenzaContestazione','buttons'];
  loadedChiudiAttivita: boolean = true;
  codiceProgetto;
  message: string;
  errore: boolean;

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private route: ActivatedRoute,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private configService: ConfigService,
    public contestazioniService : ContestazioniService) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.idUtente = data.idUtente;
          this.getCodicePorgetto();
          this.getContestazioni();
       //   this.getRecupProvRevoca();
        }
      })
    });

    
  }
  
  getContestazioni(){
    this.contestazioniService.getContestazioni(this.idProgetto).subscribe(data => {
      if (data) {
        this.dataResult = data;
        this.dataSource = new MatTableDataSource<any>(data);
      }
    }, err => {
      this.showMessageError("Errore in fase di ricerca.")
      this.handleExceptionService.handleNotBlockingError(err);
    })
  }

  getCodicePorgetto(){
    this.contestazioniService.getCodiceProgetto(this.idProgetto).subscribe(data => {
        this.codiceProgetto = data;
      
    },)
  }

 /*  inserisciContestazione(){
    this.router.navigate(["/drawer/contestazioni/inserisci-contestazione"], { queryParams: {idProgetto : this.idProgetto,idBandoLinea:this.idBandoLinea,codiceProgetto:this.codiceProgetto} });
  } */

  // INSERISCI CONTRODEDUZIONE
  inserisciContestazione(): void {
    let dialogRef = this.dialog.open(InserisciContestazioneComponent, {
      width: '50em',
      data: {
        idProgetto: this.idProgetto,
        idBandoLinea: this.idBandoLinea
      }
    });

    dialogRef.afterClosed().subscribe(result => {
        this.ngOnInit();
    })
  }

  nuovaContestazione(idGestioneRevoca) {
    const dialogRef = this.dialog.open(OkDialogComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        title: 'Inserimento Contestazione',
        message: 'Sei sicuro di voler inserire una nuova Contestazione?',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if(result === true){
      this.contestazioniService.inserisciContestazione(idGestioneRevoca).subscribe((data) => {
        if (data == true) {
          this.message = "Contestazione inserita con successo";
          this.errore = false;
          setTimeout(() => {
            this.errore = null;
            this.getContestazioni();
          }, 2000); 
        }
        else if (data == false) {
          this.errore = true;
          this.message =  "Errore non è stato possibile inserire la Contestazione";
          setTimeout(() => {
            this.errore = null;
            this.getContestazioni();
          }, 2000); 
        }
      },
        (err) => {
            this.errore = true;
            this.message = err.statusText;
        }
      );
      }else return;
      
    });
  }

  /* getRecupProvRevoca(){
    this.contestazioniService.getRecupProvRevoca(this.idProgetto).subscribe(data => {
      if (data.length>0) {
        this.inserimentoAbilitato = true ;
        
      }else {
        this.inserimentoAbilitato = false ;
      }
      
    }, err => {
      this.showMessageError("Errore in fase di ricerca.")
      this.handleExceptionService.handleNotBlockingError(err);
    })
  }
 */
 
 
  allegatiContestazione(element){
    const dialogRef = this.dialog.open(AllegatiContestazioneComponent, {
      height: 'auto',
      width: '50rem',
      panelClass: 'custom-modalbox',
      data: {
        dataKey: element, 
        dataKey2:this.user.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getContestazioni();
    }); 
  }
  
  dettaglioAllegatiContestazione(element){
    const dialogRef = this.dialog.open(DettaglioAllegatiContestazioniComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element, 
       // dataKey2:this.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getContestazioni();
    }); 
  }

  inviaContestazione(element){
    const dialogRef = this.dialog.open(ConfermaInvioContestazioneComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        idContestazione: element.idContestazione, 
        numeroRevoca: element.numeroRevoca,
        numeroProtocollo: element.numeroProtocollo,
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getContestazioni();
    }); ;
  }

  eliminaContestazione(idContestazione){
    const dialogRef = this.dialog.open(EliminaContestazioneComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: idContestazione, 
       // dataKey2:this.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getContestazioni();
    }); 
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedChiudiAttivita) {
      return true;
    }
    return false;
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_CONTESTAZIONI).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });

  }
}
