/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { PassaggioPerditaVO } from '../../commons/dto/passaggio-perdita-VO';
import { StoricoRevocaDTO } from '../../commons/dto/storico-revoca-dto';
import { PassaggioPerditaService } from '../../services/passaggio-perdita-services';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';

@Component({
  selector: 'app-passaggio-perdita',
  templateUrl: './passaggio-perdita.component.html',
  styleUrls: ['./passaggio-perdita.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class PassaggioPerditaComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  today = new Date(); 
  allegati: any;
  subscribers: any = {};
  storico: Array<StoricoRevocaDTO>;
  user: UserInfoSec
  idUtente: number;
  idProgetto: number;
  passaggioPerditaVO: PassaggioPerditaVO = new PassaggioPerditaVO;
  idPassaggioPer: number;
  dataPassaggioPerdita: Date;
  impPerditaComplessiva: number;
  impPerditaCapitale: number;
  impPerditaInterressi: number;
  impPerditaAgevolaz: number;
  impPerditaMora: number;
  note: string;
  isConferma: boolean;
  isPP: boolean;
  isSalvato: boolean;
  isCampiControl: boolean;

  loadedPP: boolean;
  loadedUser: boolean;
  isCampiVuoti: boolean;
  messageSuccess: string = "Salvato";
  isSaveSuccess: boolean;
  loadedSave: boolean = true;
  disableStorico: boolean;
  isModifica: boolean;
  impPerditaCapitaleFormatted: string;
  impPerditaInterressiFormatted: string;
  impPerditaAgevolazFormatted: string;
  impPerditaMoraFormatted: string;
  impPerditaComplessivaFormatted: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private passaggioPerditaService: PassaggioPerditaService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public sharedService: SharedService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.isModifica = false;
    this.loadedUser = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.loadedUser = true;
        this.idProgetto = this.item;
       
        this.getPassaggioPer();
        this.onStorico();
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  /* recuperaFile(newItem) {
    this.allegati = newItem;
    
  } */
  getPassaggioPer() {
    this.loadedPP = false;
    this.subscribers.getPassaggioPer = this.passaggioPerditaService.getPassaggioPerdita(this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data) {

        this.passaggioPerditaVO = data;
        this.dataPassaggioPerdita = this.passaggioPerditaVO.dataPassaggioPerdita;
        this.idPassaggioPer = this.passaggioPerditaVO.idPassaggioPerdita;
        this.impPerditaAgevolaz = this.passaggioPerditaVO.impPerditaAgevolaz;
        if (this.impPerditaAgevolaz != null) {
          this.impPerditaAgevolazFormatted = this.sharedService.formatValue(this.impPerditaAgevolaz.toString());
        }
        this.impPerditaCapitale = this.passaggioPerditaVO.impPerditaCapitale;
        if (this.impPerditaCapitale) {
          this.impPerditaCapitaleFormatted = this.sharedService.formatValue(this.impPerditaCapitale.toString());
        }
        this.impPerditaComplessiva = this.passaggioPerditaVO.impPerditaComplessiva;
        if (this.impPerditaComplessiva) {
          this.impPerditaComplessivaFormatted = this.sharedService.formatValue(this.impPerditaComplessiva.toString());
        }
        this.impPerditaInterressi = this.passaggioPerditaVO.impPerditaInterressi;
        if (this.impPerditaInterressi) {
          this.impPerditaInterressiFormatted = this.sharedService.formatValue(this.impPerditaInterressi.toString());
        }
        this.impPerditaMora = this.passaggioPerditaVO.impPerditaMora;
        if (this.impPerditaMora) {
          this.impPerditaMoraFormatted = this.sharedService.formatValue(this.impPerditaMora.toString());
        }
        this.note = this.passaggioPerditaVO.note;
        this.loadedPP = true;

        if (this.passaggioPerditaVO.idPassaggioPerdita != null) {
          this.isPP = true;
          this.isConferma = false;

        } else {
          this.isConferma = true;
          this.isPP = false;
        }

      }
    });

  }
  setDataNull(){
    this.dataPassaggioPerdita = null; 
  }
  salva() {

    if (this.passaggioPerditaVO.dataPassaggioPerdita == this.dataPassaggioPerdita && this.passaggioPerditaVO.impPerditaCapitale == this.impPerditaCapitale
      && this.passaggioPerditaVO.impPerditaInterressi == this.impPerditaInterressi && this.passaggioPerditaVO.impPerditaAgevolaz ==
      this.impPerditaAgevolaz && this.passaggioPerditaVO.impPerditaMora == this.impPerditaMora && this.impPerditaComplessiva ==
      this.passaggioPerditaVO.impPerditaComplessiva && this.note == this.passaggioPerditaVO.note) {
      this.isModifica = true;
    } else {
      this.isModifica = false;
      this.loadedSave = false;
      this.isCampiVuoti = false;
      this.isCampiControl = false;

      this.passaggioPerditaVO.idProgetto = this.item;
      this.passaggioPerditaVO.idUtenteIns = this.idUtente;
      this.passaggioPerditaVO.dataPassaggioPerdita = this.dataPassaggioPerdita;
      this.passaggioPerditaVO.impPerditaComplessiva = this.impPerditaComplessiva;
      this.passaggioPerditaVO.impPerditaCapitale = this.impPerditaCapitale;
      this.passaggioPerditaVO.impPerditaInterressi = this.impPerditaInterressi;
      this.passaggioPerditaVO.impPerditaAgevolaz = this.impPerditaAgevolaz;
      this.passaggioPerditaVO.impPerditaMora = this.impPerditaMora;
      this.passaggioPerditaVO.note = this.note;
//this.subscribers.salva = this.passaggioPerditaService.salvaPassaggioPerdita(this.passaggioPerditaVO,this.allegati,this.idModalitaAgevolazione).subscribe(data => {
      this.subscribers.salva = this.passaggioPerditaService.salvaPassaggioPerdita(this.passaggioPerditaVO, this.idModalitaAgevolazione).subscribe(data => {
        if (data == true) {
          this.isSalvato = data;
          this.isPP = true;
          this.isConferma = false;
          this.isSaveSuccess = true;
          this.loadedSave = true;
          this.disableStorico = false;
          this.onStorico();
        }
      });
    }

  }
  controllaCampi() {
    var regex = /^\d+(?:\.\d{1,2})?$/;

    if (this.dataPassaggioPerdita == null && this.impPerditaCapitale == null && this.impPerditaInterressi == null
      && this.impPerditaAgevolaz == null && this.impPerditaMora == null && this.impPerditaComplessiva == null && this.note == null) {
      this.isCampiVuoti = true;
    } else {
      if (

        ((this.impPerditaComplessiva != null && this.impPerditaComplessiva.toString().length > 13)
          || (this.impPerditaComplessiva != null && !this.impPerditaComplessiva.toString().match(regex)))

        || ((this.impPerditaCapitale != null && this.impPerditaCapitale.toString().length > 13)
          || (this.impPerditaCapitale != null && !this.impPerditaCapitale.toString().match(regex)))

        || ((this.impPerditaInterressi != null && this.impPerditaInterressi.toString().length > 13)
          || (this.impPerditaInterressi != null && !this.impPerditaInterressi.toString().match(regex)))

        || ((this.impPerditaAgevolaz != null && this.impPerditaAgevolaz.toString().length > 13)
          || (this.impPerditaAgevolaz != null && !this.impPerditaAgevolaz.toString().match(regex)))

        || ((this.impPerditaMora != null && this.impPerditaMora.toString().length > 13)
          || (this.impPerditaMora != null && !this.impPerditaMora.toString().match(regex)))
      ) {


        console.log("controlla campi !");
        this.isCampiControl = true;
      } else {

        console.log("Salvato!");
        this.isCampiControl = false;
        this.salva();
      }
    }


  }
  onModifica() {
    this.isModifica = false;
    this.isCampiVuoti = false;
    this.isSaveSuccess = false;
    this.isCampiControl = false;
    this.isPP = false;
    this.isConferma = true;
  }

  onAnnulla() {
    this.isModifica = false;
    this.isCampiVuoti = false;
    this.isCampiControl = false;
    this.isPP = true;
    this.isConferma = false;
    this.isSaveSuccess = false;
    this.getPassaggioPer();
  }



  onStorico() {
    this.subscribers.storico = this.passaggioPerditaService.getStorico(this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data.length > 0) {
        this.storico = data;
      } else {
        this.disableStorico = true;
      }
    });
  }

  openStorico() {
    this.isModifica = false;
    this.isSaveSuccess = false;
    this.isCampiControl = false;
    this.isCampiVuoti = false;
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '600px',
      data: {
        id: 5,
        storico: this.storico

      }
    });
  }

  setImporto(campoNumber: number) {
    switch (campoNumber) {
      case 1:
        this.impPerditaCapitale = this.sharedService.getNumberFromFormattedString(this.impPerditaCapitaleFormatted);
        if (this.impPerditaCapitale !== null) {
          this.impPerditaCapitaleFormatted = this.sharedService.formatValue(this.impPerditaCapitale.toString());
        }
        break;
      case 2:
        this.impPerditaInterressi = this.sharedService.getNumberFromFormattedString(this.impPerditaInterressiFormatted);
        if (this.impPerditaInterressi !== null) {
          this.impPerditaInterressiFormatted = this.sharedService.formatValue(this.impPerditaInterressi.toString());
        }
        break
      case 3:
        this.impPerditaAgevolaz = this.sharedService.getNumberFromFormattedString(this.impPerditaAgevolazFormatted);
        if (this.impPerditaAgevolaz !== null) {
          this.impPerditaAgevolazFormatted = this.sharedService.formatValue(this.impPerditaAgevolaz.toString());
        }
        break;
      case 4:
        this.impPerditaMora = this.sharedService.getNumberFromFormattedString(this.impPerditaMoraFormatted);
        if (this.impPerditaMora !== null) {
          this.impPerditaMoraFormatted = this.sharedService.formatValue(this.impPerditaMora.toString());
        }
        break;
      case 5:
        this.impPerditaComplessiva = this.sharedService.getNumberFromFormattedString(this.impPerditaComplessivaFormatted);
        if (this.impPerditaComplessiva !== null) {
          this.impPerditaComplessivaFormatted = this.sharedService.formatValue(this.impPerditaComplessiva.toString());
        }
        break;
      default:
        break;
    }
  }
  isLoading() {
    if (!this.loadedPP || !this.loadedUser || !this.loadedSave) {
      return true;
    }
    return false;
  }

}
