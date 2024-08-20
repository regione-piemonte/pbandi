/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { not } from '@angular/compiler/src/output/output_ast';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from '@pbandi/common-lib';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AttivitaDTO } from '../../../commons/dto/attivita-dto';
import { MessaMoraVO } from '../../../commons/dto/messa-moraVO';
import { MessaMoraService } from '../../../services/messa-mora-services';

@Component({
  selector: 'app-dialog-edit-messa-mora',
  templateUrl: './dialog-edit-messa-mora.component.html',
  styleUrls: ['./dialog-edit-messa-mora.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogEditMessaMoraComponent implements OnInit {
  today = new Date()
  idModalitaAgevolazione = this.data.idModalitaAgevolazione;
  nomeBox = this.data.nomeBox;
  idProgetto = this.data.idProgetto;
  allegati: any;
  idMessaMora: number;
  idAttivitaMora: number; // tipo messa in mora
  dataMessaInMora: Date;
  importoMessaMoraComplessiva: number;
  importoQuotaCapitaleRevoca: number;
  importoAgevolazRevoca: number;
  importoCreditoResiduo: number;
  importoInteressiMora: number;
  dataNotificaMessaMora: Date;
  idAttivitaRecuperoMora: number;
  dataPagamento: Date;
  note: string;
  idUtente: number;
  subscribers: any = {};
  user: UserInfoSec;
  listaAttivitaRecupero: Array<AttivitaDTO>;
  listaAttivitaMessaMora: Array<AttivitaDTO>;
  messaMoraVO: MessaMoraVO = new MessaMoraVO;
  isControllaCampi: boolean;
  isSalvato: boolean;
  attivitaDTO: AttivitaDTO;
  attivitaDTO1: AttivitaDTO;
  isNew: boolean = true;
  isCampiVuoti: boolean;
  isModifica: boolean;
  importoMessaMoraComplessivaFormatted: string;
  importoQuotaCapitaleRevocaFormatted: string;
  importoAgevolazRevocaFormatted: string;
  importoCreditoResiduoFormatted: string;
  importoInteressiMoraFormatted: string;
  numeroProtocollo: string; // Aggiunto da richiesta da FP



  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private messaMoraService: MessaMoraService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DialogEditMessaMoraComponent>,
    public sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {

    this.idMessaMora = this.data.idMessaMora;
      this.idProgetto = this.data.idProgetto;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
      this.subscribers.listaAttivitaRecupero = this.messaMoraService.getListaAttivitaRecupero().subscribe(data => {
        if (data)
          this.listaAttivitaRecupero = data;
      })

      this.subscribers.listaAttivitaMora = this.messaMoraService.getListaAttivitaMessaMora().subscribe(data => {
        if (data)
          this.listaAttivitaMessaMora = data;
      })
    });  // fine usersubscribe

    if (this.data.idMessaMora != null) {
      this.isNew = false;

      this.loadMessaMora();
    } else {
      this.messaMoraVO = new MessaMoraVO;
    }

  }

  /* recuperaFile(newItem) {
    this.allegati = newItem;
  } */

  loadMessaMora() {

    this.idMessaMora = this.data.idMessaMora;

    this.subscribers.getMessaMora = this.messaMoraService.getMessaMora(this.idMessaMora,this.idModalitaAgevolazione).subscribe(data => {
      if (data) {

        this.messaMoraVO = data;
        this.dataMessaInMora = this.messaMoraVO.dataMessaMora;
        this.idAttivitaMora = this.messaMoraVO.idAttivitaMora;
        this.importoMessaMoraComplessiva = this.messaMoraVO.impMessaMoraComplessiva;
        this.importoQuotaCapitaleRevoca = this.messaMoraVO.impQuotaCapitaleRevoca;
        this.importoCreditoResiduo = this.messaMoraVO.impCreditoResiduo;
        this.importoAgevolazRevoca = this.messaMoraVO.impAgevolazRevoca;
        this.importoInteressiMora = this.messaMoraVO.impInteressiMora;
        this.dataNotificaMessaMora = this.messaMoraVO.dataNotifica;
        this.idAttivitaRecuperoMora = this.messaMoraVO.idAttivitaRecuperoMora;
        this.dataPagamento = this.messaMoraVO.dataPagamento;
        this.note = this.messaMoraVO.note;
        this.numeroProtocollo = this.messaMoraVO.numeroProtocollo; // New
        if (this.importoAgevolazRevoca) {
          this.importoAgevolazRevocaFormatted = this.sharedService.formatValue(this.importoAgevolazRevoca.toString());
        }
        if (this.importoCreditoResiduo) {
          this.importoCreditoResiduoFormatted = this.sharedService.formatValue(this.importoCreditoResiduo.toString());
        }
        if (this.importoInteressiMora) {
          this.importoInteressiMoraFormatted = this.sharedService.formatValue(this.importoInteressiMora.toString());
        }
        if (this.importoMessaMoraComplessiva) {
          this.importoMessaMoraComplessivaFormatted = this.sharedService.formatValue(this.importoMessaMoraComplessiva.toString());
        }
        if (this.importoQuotaCapitaleRevoca) {
          this.importoQuotaCapitaleRevocaFormatted = this.sharedService.formatValue(this.importoQuotaCapitaleRevoca.toString());
        }
      }
    })
  }
  setDataNull(idData: number) {
    switch (idData) {
      case 1:
        this.dataMessaInMora = null;
        break;
      case 2:
        this.dataPagamento = null;
        break;
      case 3:
        this.dataNotificaMessaMora = null;
        break;
   

      default:
        break;
    }
  }
  controllaCampi() {

    var regex = /^\d+(?:\.\d{1,2})?$/;

    if (this.dataMessaInMora == null && this.idAttivitaMora == null && this.importoMessaMoraComplessiva == null && this.importoQuotaCapitaleRevoca == null
      && this.importoAgevolazRevoca == null && this.importoCreditoResiduo == null && this.importoInteressiMora == null && this.dataNotificaMessaMora == null
      && this.dataPagamento == null && this.numeroProtocollo == null && this.note == null) {
      this.isCampiVuoti = true;
    } else {
      if (
        ((this.importoAgevolazRevoca != null && this.importoAgevolazRevoca.toString().length > 13)
          || (this.importoAgevolazRevoca != null && !this.importoAgevolazRevoca.toString().match(regex)))

        || ((this.importoCreditoResiduo != null && this.importoCreditoResiduo.toString().length > 13)
          || (this.importoCreditoResiduo != null && !this.importoCreditoResiduo.toString().match(regex)))

        || ((this.importoInteressiMora != null && this.importoInteressiMora.toString().length > 13)
          || (this.importoInteressiMora != null && !this.importoInteressiMora.toString().match(regex)))

        || ((this.importoMessaMoraComplessiva != null && this.importoMessaMoraComplessiva.toString().length > 13)
          || (this.importoMessaMoraComplessiva != null && !this.importoMessaMoraComplessiva.toString().match(regex)))

        || ((this.importoQuotaCapitaleRevoca != null && this.importoQuotaCapitaleRevoca.toString().length > 13)
          || (this.importoQuotaCapitaleRevoca != null && !this.importoQuotaCapitaleRevoca.toString().match(regex)))
      ) {
        this.isControllaCampi = true;
      } else {
        this.isControllaCampi = false;
        this.salvaMessaMora();
        this.messaMoraVO.note = this.note;


      }
    }


  }
  salvaMessaMora() {

    if (this.dataMessaInMora == this.messaMoraVO.dataMessaMora && this.idAttivitaMora == this.messaMoraVO.idAttivitaMora &&
      this.importoMessaMoraComplessiva == this.messaMoraVO.impMessaMoraComplessiva && this.note == this.messaMoraVO.note &&
      this.importoAgevolazRevoca == this.messaMoraVO.impAgevolazRevoca && this.importoCreditoResiduo == this.messaMoraVO.impCreditoResiduo
      && this.importoInteressiMora == this.messaMoraVO.impInteressiMora && this.idAttivitaRecuperoMora == this.messaMoraVO.idAttivitaRecuperoMora
      && this.dataPagamento == this.messaMoraVO.dataPagamento && this.dataNotificaMessaMora == this.messaMoraVO.dataNotifica && this.numeroProtocollo == this.messaMoraVO.numeroProtocollo) {
      this.isModifica = true;
    } else {
      this.isModifica = false;
      this.messaMoraVO.note = this.note;
      this.messaMoraVO.idAttivitaMora = this.idAttivitaMora;
      this.messaMoraVO.dataMessaMora = this.dataMessaInMora;
      this.messaMoraVO.impMessaMoraComplessiva = this.importoMessaMoraComplessiva;
      this.messaMoraVO.impQuotaCapitaleRevoca = this.importoQuotaCapitaleRevoca;
      this.messaMoraVO.impAgevolazRevoca = this.importoAgevolazRevoca;
      this.messaMoraVO.impCreditoResiduo = this.importoCreditoResiduo;
      this.messaMoraVO.impInteressiMora = this.importoInteressiMora;
      this.messaMoraVO.dataNotifica = this.dataNotificaMessaMora;
      this.messaMoraVO.idAttivitaRecuperoMora = this.idAttivitaRecuperoMora;
      this.messaMoraVO.dataPagamento = this.dataPagamento;
      this.messaMoraVO.idProgetto = this.idProgetto;
      this.messaMoraVO.idUtenteIns = this.idUtente;
      this.messaMoraVO.numeroProtocollo = this.numeroProtocollo; // Aggiunto da richiesta di FP

      console.log(this.messaMoraVO);
      if (this.data.idMessaMora != null) {
      //this.subscribers.salvaMessaMora = this.messaMoraService.modificaMessaMora(this.messaMoraVO, this.idMessaMora).subscribe(data1 => {
        this.subscribers.salvaMessaMora = this.messaMoraService.modificaMessaMora(this.messaMoraVO, this.idMessaMora, this.idModalitaAgevolazione).subscribe(data1 => {
          if (data1)
            this.isSalvato = data1;
          this.dialogRef.close(this.isSalvato)
        });
      } else {
      //this.subscribers.salvaMessaMora = this.messaMoraService.insertMessaMora(this.messaMoraVO).subscribe(data1 => {
        this.subscribers.salvaMessaMora = this.messaMoraService.insertMessaMora(this.messaMoraVO, this.idModalitaAgevolazione).subscribe(data1 => {
          if (data1)
            this.isSalvato = data1;
          this.dialogRef.close(this.isSalvato)
        });
      }
    }
  }
  setImporto(campoNumber: number) {
    switch (campoNumber) {
      case 1:
        this.importoMessaMoraComplessiva = this.sharedService.getNumberFromFormattedString(this.importoMessaMoraComplessivaFormatted);
        if (this.importoMessaMoraComplessiva !== null) {
          this.importoMessaMoraComplessivaFormatted = this.sharedService.formatValue(this.importoMessaMoraComplessiva.toString());
        }
        break;
      case 2:
        this.importoQuotaCapitaleRevoca = this.sharedService.getNumberFromFormattedString(this.importoQuotaCapitaleRevocaFormatted);
        if (this.importoQuotaCapitaleRevoca !== null) {
          this.importoQuotaCapitaleRevocaFormatted = this.sharedService.formatValue(this.importoQuotaCapitaleRevoca.toString());
        }
        break
      case 3:
        this.importoAgevolazRevoca = this.sharedService.getNumberFromFormattedString(this.importoAgevolazRevocaFormatted);
        if (this.importoAgevolazRevoca !== null) {
          this.importoAgevolazRevocaFormatted = this.sharedService.formatValue(this.importoAgevolazRevoca.toString());
        }
        break;
      case 4:
        this.importoCreditoResiduo = this.sharedService.getNumberFromFormattedString(this.importoCreditoResiduoFormatted);
        if (this.importoCreditoResiduo !== null) {
          this.importoCreditoResiduoFormatted = this.sharedService.formatValue(this.importoCreditoResiduo.toString());
        }
        break;
      case 5:
        this.importoInteressiMora = this.sharedService.getNumberFromFormattedString(this.importoInteressiMoraFormatted);
        if (this.importoInteressiMora !== null) {
          this.importoInteressiMoraFormatted = this.sharedService.formatValue(this.importoInteressiMora.toString());
        }
        break;
      default:
        break;
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }


}
