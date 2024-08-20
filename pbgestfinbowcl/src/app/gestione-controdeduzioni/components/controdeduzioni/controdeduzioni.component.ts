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
import { DocumentoAllegatoDTO } from '@pbandi/common-lib';
import { BeneficiarioSec } from 'src/app/core/commons/dto/beneficiario';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AccessoAgliAttiComponent } from 'src/app/gestione-controdeduzioni/components/accesso-agli-atti/accesso-agli-atti.component';
import { AllegatiControdeduzioneComponent } from 'src/app/gestione-controdeduzioni/components/allegati-controdeduzioni/allegati-controdeduzioni.component';
import { EliminaControdeduzioneComponent } from 'src/app/gestione-controdeduzioni/components/elimina-controdeduzione/elimina-controdeduzione.component';
import { InviaControdeduzione } from 'src/app/gestione-controdeduzioni/components/invia-controdeduzione/invia-controdeduzione.component';
import { ControdeduzioneVO } from 'src/app/gestione-crediti/commons/dto/controdeduzioneVO';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ControdeduzioniService } from '../../services/controdeduzioni.service';
import { DialogAddControdeduzione } from '../dialog-add-controdeduzione/dialog-add-controdeduzione.component';
import { DialogAddProroga } from '../dialog-add-proroga/dialog-add-proroga.component';
import { DialogEditAllegati } from '../dialog-edit-allegati/dialog-edit-allegati.component';
import { OkDialogComponent } from 'src/app/iter-autorizzativi/components/iter-autorizzativi/ok-dialog/ok-dialog.component';

@Component({
  selector: 'app-controdeduzioni',
  templateUrl: './controdeduzioni.component.html',
  styleUrls: ['./controdeduzioni.component.scss']
})



export class ControdeduzioniComponent implements OnInit {
  idOperazione: number;
  idUtente: string;
  idProgetto: number;
  idBandoLinea: number;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  isMessageErrorVisible: boolean;
  messageError: string;
  showResults: boolean = false;
  beneficiario: BeneficiarioSec;
  con: Array<ControdeduzioneVO> = [];
  user: UserInfoSec;
  showSucc: boolean = false;
  showError: boolean = false;
  idControdeduz: number;
  numeroControdeduz: number;
  pro: Array<ControdeduzioneVO> = [];
  thereIsProroga: boolean = false;
  isApproved: number = 0;
  subscribers: any = {};
  dataSource: MatTableDataSource<ControdeduzioneVO> = new MatTableDataSource<ControdeduzioneVO>([]);
  columns: string[] = ['numeroRevoca','dataNotifica','causaRevoca','numeroProtocollo','numeroControdeduzione', 'descStatoControdeduzione','dtStatoControdeduzione',
  'descAttivitaControdeduzione','dtAttivitaControdeduzione','dtScadenzaControdeduzione', 'buttons'];


  loadedChiudiAttivita: boolean = true;
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  allegati: Array<DocumentoAllegatoDTO>;
  theRevoche: Array<ControdeduzioneVO> = [];
  ilCodice: Array<ControdeduzioneVO> = [];
  

/////
  codiceProgetto;
  dataResult: Array<any> = [];
  message: string;
  errore: boolean;

  constructor(
    private controdeduzioniService: ControdeduzioniService,
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private route: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private configService: ConfigService,
  ) { }

  ngOnInit(): void {

    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.getIntestazioneControdeduzioni();
          this.getControdeduzioni();
        }

      })

    });

  }

  refresh(){
    this.getIntestazioneControdeduzioni();
    this.getControdeduzioni();
  }

  

//ELIMINA

  eliminaControdeduzione(element){
    const dialogRef = this.dialog.open(EliminaControdeduzioneComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element, 
       // dataKey2:this.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.ngOnInit();
    }); 
 }

// ACCESSO ATTI
  accessoAgliAtti(element): void {
  this.showSucc = false;
  this.showError = false;

  let dialogRef = this.dialog.open(AccessoAgliAttiComponent, {
    height: 'auto',
    width: '50rem',

    data: {
      dataKey:element,
    }

  });

  dialogRef.afterClosed().subscribe(result => {
   this.refresh();

  })

 }

// ROBA SOPRA
  getIntestazioneControdeduzioni(){
    this.controdeduzioniService.getIntestazioneControdeduzioni(this.idProgetto).subscribe(data => {
      if (data) {
        this.codiceProgetto = data[0].codiceVisualizzatoProgetto;
      }

    })
  }

// GET CONTRODEDUZIONI
  getControdeduzioni(){
    this.controdeduzioniService.getControdeduzioni(this.idProgetto).subscribe(data => {
      if (data) {
        this.dataResult = data;
        this.dataSource = new MatTableDataSource<any>(data);
      }
     
    }, err => {
      this.showMessageError("Errore in fase di ricerca.")
      this.handleExceptionService.handleNotBlockingError(err);
    })
  } 

// INSERISCI CONTRODEDUZIONE
  /* nuovaControdeduzione(): void {
    let dialogRef = this.dialog.open(DialogAddControdeduzione, {
      width: '50em',
      data: {
        idProgetto: this.idProgetto,
        idBandoLinea: this.idBandoLinea
      }
    });

    dialogRef.afterClosed().subscribe(result => {
        this.ngOnInit();
    })
  } */

  nuovaControdeduzione(idGestioneRevoca) {
    const dialogRef = this.dialog.open(OkDialogComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        title: 'Inserimento Controdeduzione',
        message: 'Sei sicuro di voler inserire una nuova controdeduzione?',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if(result === true){
      this.controdeduzioniService.insertControdeduzione(idGestioneRevoca).subscribe((data) => {
        if (data == true) {
          this.message = "controdeduzione inserita con successo";
          this.errore = false;
          setTimeout(() => {
            this.errore = null;
            this.getControdeduzioni();
          }, 2000); 
        }
        else if (data == false) {
          this.errore = true;
          this.message =  "Errore non è stato possibile inserire la controdeduzione";
          setTimeout(() => {
            this.errore = null;
            this.getControdeduzioni();
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


// INVIA CONTRODEDUZIONE

  inviaControdeduzione(element){
    const dialogRef = this.dialog.open(InviaControdeduzione, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element, 
        dataKey2:this.idUtente,
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getControdeduzioni();
    }); ;
  }
// ALLEGATI GESTIONE
  allegatiControdeduzione(element){
    const dialogRef = this.dialog.open(AllegatiControdeduzioneComponent, {
      height: 'auto',
      width: '50rem',
      panelClass: 'custom-modalbox',
      data: {
        dataKey: element, 
        dataKey2:this.user.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getControdeduzioni();
    }); 
  }

  // ALLEGATO DETTAGLIO
  
  dettaglioAllegatiControdeduzione(element){
    const dialogRef = this.dialog.open(DialogEditAllegati, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element, 
       // dataKey2:this.idUtente
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getControdeduzioni();
    }); 
  }

  richiediProroga(element,numero){
    const dialogRef = this.dialog.open(DialogAddProroga, {
      height: 'auto',
      width: '50%',
      data: {
        element: element, 
        numero : numero,
        idProgetto : this.idProgetto
       },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getControdeduzioni();
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

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;

    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_CONTRODEDUZIONI).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });

  }

  isLoading() {

    if (!this.loadedChiudiAttivita) {
      return true;
    }

    return false;
  }
  

}