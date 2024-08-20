/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AttivitaDTO } from '../../commons/dto/attivita-dto';
import { AzioneRecuperoBancaVO } from '../../commons/dto/azione-recupero-bancaVO';
import { StoricoAzioneRecuperoBancaDTO } from '../../commons/dto/storico-azione-recupero-banca-dto';

import { AzioniRecuperoBancaService } from '../../services/azioni-recupero-banca-service';

@Component({
  selector: 'app-dialog-edit-azione-recupero-banca',
  templateUrl: './dialog-edit-azione-recupero-banca.component.html',
  styleUrls: ['./dialog-edit-azione-recupero-banca.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogEditAzioneRecuperoBancaComponent implements OnInit {
  today = new Date(); 
  idProgetto = this.data.idProgetto ;
  ambito = this.data.ambito; 
  nomeBox = this.data.nomeBox;
  idModalitaAgevolazione = this.data.idModalitaAgevolazione;
  user: UserInfoSec;
  idUtente: number;
  azioneRecuperoBancaVO: AzioneRecuperoBancaVO = new AzioneRecuperoBancaVO();
  
  dataAzione: any;
  numProtocollo: string;
  note: string;
  idAttivitaAzione: number;
  descAttivitaAzione: string;
  attivitaAzione: AttivitaDTO;
  subscribers: any = {};
  storicoAzioniRecupBanca: Array<StoricoAzioneRecuperoBancaDTO>;
  listaAttivitaAzioneBanca: Array<AttivitaDTO>;
  isSalvato: boolean;
  attivitaAzioneDTO: AttivitaDTO;
  public myForm: FormGroup;

  attivitaAzioneControl = new FormControl('', Validators.required);
  selectFormControl = new FormControl('', Validators.required);
  dataAzioneControl = new FormControl('', Validators.required);
  idAzioneRecupero: number;
  isConferma: boolean = true;
  numProtocolloControl: boolean;
  isNew: boolean = true;
  messageError: string = " ATTENZIONE! È necessario indicare i campi obbligatori: data azione e motivazione azione di recupero";
  isSaveError: boolean;
  isModifica: boolean;
  allegati: any;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private azioneRecuperoBancaService: AzioniRecuperoBancaService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DialogEditAzioneRecuperoBancaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {

    this.idAzioneRecupero = this.data.idAzioneRecuperoBanca;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
    

    this.subscribers.listaAttivita = this.azioneRecuperoBancaService.getAttivitaAzioneRecuperoBanca().subscribe(data => {
      if (data) {
        this.listaAttivitaAzioneBanca = data;
      }
    })
    if (this.idAzioneRecupero != null) {
      this.isNew = false;
      this.subscribers.azioneRecup = this.azioneRecuperoBancaService.getAzioneRecupBanca(this.idUtente, this.idProgetto, this.idAzioneRecupero, this.idModalitaAgevolazione)
        .subscribe(data => {
          if (data) {
            this.azioneRecuperoBancaVO = data;
            this.dataAzione = this.azioneRecuperoBancaVO.dataAzione;
            this.numProtocollo = this.azioneRecuperoBancaVO.numProtocollo;
            this.note = this.azioneRecuperoBancaVO.note;
            this.idAttivitaAzione = this.azioneRecuperoBancaVO.idAttivitaAzione;

          }

        })
    }



  }

 /*  recuperaFile(newItem) {
    this.allegati = newItem;
    
  } */
  
  setDataNull(){
    this.dataAzione = null; 
  }
  getDescAttivita(idAttivita: number): string {

    var descrizioneAttivita: string;

    for (let attivita1 of this.listaAttivitaAzioneBanca) {
      if (attivita1.idAttivita == idAttivita) {
        descrizioneAttivita = attivita1.descAttivita;
      }
    }

    return descrizioneAttivita;
  }


  salvaAzione() {


    if (this.azioneRecuperoBancaVO.dataAzione != this.dataAzione || this.azioneRecuperoBancaVO.idAttivitaAzione != this.idAttivitaAzione
      || this.azioneRecuperoBancaVO.numProtocollo != this.numProtocollo || this.note != this.azioneRecuperoBancaVO.note) {

      this.azioneRecuperoBancaVO.dataAzione = this.dataAzione;
      this.azioneRecuperoBancaVO.numProtocollo = this.numProtocollo;
      this.azioneRecuperoBancaVO.idAttivitaAzione = this.idAttivitaAzione;
      this.azioneRecuperoBancaVO.idProgetto = this.idProgetto;
      this.azioneRecuperoBancaVO.note = this.note;
      this.azioneRecuperoBancaVO.idUtenteIns = this.idUtente;

      if (this.data.idAzioneRecuperoBanca != null) {
 //this.subscribers.modifica = this.azioneRecuperoBancaService.modificaAzioneRecupBanca(this.azioneRecuperoBancaVO, this.idAzioneRecupero, this.allegati, this.idProgetto,this.idModalitaAgevolazione).subscribe(data => {
        this.subscribers.modifica = this.azioneRecuperoBancaService.modificaAzioneRecupBanca(this.azioneRecuperoBancaVO,this.idUtente,this.idProgetto,this.idAzioneRecupero, this.idModalitaAgevolazione).subscribe(data => {
            if (data) {
              this.isSalvato = data;
              this.dialogRef.close(this.isSalvato);
            }
          })
      } else if (this.data.idAzioneRecuperoBanca == null) {
//this.subscribers.salva = this.azioneRecuperoBancaService.insertAzioneRecupBanca(this.azioneRecuperoBancaVO, this.allegati,this.idProgetto,this.idModalitaAgevolazione).subscribe(data => {
        this.subscribers.salva = this.azioneRecuperoBancaService.insertAzioneRecupBanca(this.azioneRecuperoBancaVO,this.idUtente,this.idProgetto, this.idModalitaAgevolazione).subscribe(data => {
            if (data) {
              this.isSalvato = data;
              this.dialogRef.close(this.isSalvato);
            }
          })
      }
    } else {
      this.isModifica = true;
    }


  }

  closeDialog() {
    this.dialogRef.close();
  }

  controllaCampi() {

    if (this.dataAzione == null || this.idAttivitaAzione == null) {
      this.isSaveError = true;
    }
    else {
      this.salvaAzione();
    }

  }


}


