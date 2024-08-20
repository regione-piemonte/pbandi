/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AttivitaDTO } from '../../commons/dto/attivita-dto';
import { StoricoRevocaDTO } from '../../commons/dto/storico-revoca-dto';
import { TransazioneVO } from '../../commons/dto/transazioneVO';
import { PassaggioPerditaService } from '../../services/passaggio-perdita-services';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';

@Component({
  selector: 'app-transazione',
  templateUrl: './transazione.component.html',
  styleUrls: ['./transazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class TransazioneComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  allegati:any;
  subscribers: any = {};
  storico: Array<StoricoRevocaDTO>;
  user: UserInfoSec
  idUtente: number;
  idProgetto: number;
  transazioneVO: TransazioneVO = new TransazioneVO;
  listaBanche: Array<AttivitaDTO>;
  idTransazione: number;
  impRiconciliato: number;
  impTransato: number;
  idBanca: number;
  percTransazione: number;
  impPagato: number;
  note: string;
  isConferma: boolean;
  isTT: boolean;
  isSalvato: boolean;
  isStorico: boolean;
  isCampiControl: boolean;
  bancaSelected: FormControl = new FormControl();
  bancaGroup: FormGroup = new FormGroup({ bancaControl: new FormControl() });
  filteredOptions: Observable<AttivitaDTO[]>;
  messageSuccess: string = "Salvato";
  messageError: string = "errore controlla i campi";
  messageBanca: string = "campo 'nominativo banca ' necessario";
  isSaveSuccess: boolean;
  issaveError: boolean;



  displayedColumns: string[] = ['dataInserimento', 'istruttore'];
  loadedTrans: boolean;
  loadedUser: boolean;
  listaBanche2: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  suggesttionnull: AttivitaDTO;
  nomeBanca: string;
  bancaDTO: AttivitaDTO = new AttivitaDTO;
  isIdBancaObbligatorio: boolean;
  disableStorico: boolean;
  isModifica: boolean;
  impRiconciliatoFormatted: string;
  impTransatoFormatted: string;
  impPagatoFormatted: string;


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
    this.loadedUser = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.loadedUser = true;
        this.route.queryParams.subscribe(params => {
          this.idProgetto = params.progetto;
        });
        this.getTransazione();
        this.onStorico();
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });



  }

  /* recuperaFile(newItem) {
    this.allegati = newItem;
  } */
  
  getTransazione() {
    this.isModifica = false;
    this.loadedTrans = false;
    this.subscribers.getPassaggioPer = this.passaggioPerditaService.getTransazione(this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data) {

        this.transazioneVO = data;
        this.note = this.transazioneVO.note;
        this.impPagato = this.transazioneVO.impPagato;
        this.impRiconciliato = this.transazioneVO.impRiconciliato;
        this.impTransato = this.transazioneVO.impTransato;
        this.percTransazione = this.transazioneVO.percTransazione;
        this.idBanca = this.transazioneVO.idBanca;
        this.nomeBanca = this.transazioneVO.descBanca;

        if (this.impPagato) {
          this.impPagatoFormatted = this.sharedService.formatValue(this.impPagato.toString());
        }
        if (this.impRiconciliato) {
          this.impRiconciliatoFormatted = this.sharedService.formatValue(this.impRiconciliato.toString());
        }
        if (this.impTransato) {
          this.impTransatoFormatted = this.sharedService.formatValue(this.impTransato.toString());
        }

        this.loadedTrans = true;


        if (this.transazioneVO.idTransazione != null) {
          this.isTT = true;
          this.isConferma = false;

        } else {
          this.isConferma = true;
          this.isTT = false;
        }
      }
    });

  }

  salva() {
    for (const banca of this.listaBanche2) {
      if (banca.descAttivita == this.nomeBanca)
        this.idBanca = banca.idAttivita;
    }

    if (this.impRiconciliato == this.transazioneVO.impRiconciliato && this.impTransato == this.transazioneVO.impTransato
      && this.percTransazione == this.transazioneVO.percTransazione && this.impPagato == this.transazioneVO.impPagato
      && this.idBanca == this.transazioneVO.idBanca && this.note == this.transazioneVO.note) {
      this.isModifica = true;
    } else {
      this.isModifica = false;
      this.bancaDTO.descAttivita = this.nomeBanca;
      this.bancaDTO.idAttivita = this.idBanca;

      this.transazioneVO.idProgetto = this.item;
      this.transazioneVO.idUtenteIns = this.idUtente;
      this.transazioneVO.impPagato = this.impPagato;
      this.transazioneVO.idBanca = this.bancaDTO.idAttivita;
      this.transazioneVO.impRiconciliato = this.impRiconciliato;
      this.transazioneVO.impTransato = this.impTransato;
      this.transazioneVO.percTransazione = this.percTransazione;
      this.transazioneVO.note = this.note;

//this.subscribers.salva = this.passaggioPerditaService.salvaTransazione(this.transazioneVO,this.allegati,this.idModalitaAgevolazione).subscribe(data => {
      this.subscribers.salva = this.passaggioPerditaService.salvaTransazione(this.transazioneVO, this.idModalitaAgevolazione).subscribe(data => {
        if (data == true) {
          this.isSalvato = data;
          this.isTT = true;
          this.isConferma = false;
          this.isSaveSuccess = true;
          this.onStorico();
          this.disableStorico = false;
        } else {
          this.issaveError = true;
        }
      });
    }

  }
  controllaCampi() {

    this.issaveError = false;
    this.isSaveSuccess = false;
    this.isCampiControl = false;
    this.isIdBancaObbligatorio = false;
    var regex = /^\d+(?:\.\d{1,2})?$/;

    if (this.nomeBanca == null) {
      this.isIdBancaObbligatorio = true;
    } else {
      if (

        ((this.impRiconciliato != null && this.impRiconciliato.toString().length > 13)
          || (this.impRiconciliato != null && !this.impRiconciliato.toString().match(regex)))

        || ((this.impTransato != null && this.impTransato.toString().length > 13)
          || (this.impTransato != null && !this.impTransato.toString().match(regex)))

        || ((this.percTransazione != null && this.percTransazione.toString().length > 5)
          || (this.percTransazione != null && !this.percTransazione.toString().match(regex)))

        || ((this.impPagato != null && this.impPagato.toString().length > 13)
          || (this.impPagato != null && !this.impPagato.toString().match(regex)))
      ) {

        this.isCampiControl = true;
      } else {
        this.isCampiControl = false;
        this.salva();
      }
    }




  }
  onModifica() {
    this.isModifica = false;
    this.issaveError = false;
    this.isSaveSuccess = false;
    this.isCampiControl = false;
    this.isIdBancaObbligatorio = false;

    this.isTT = false;
    this.isConferma = true;
  }

  onAnnulla() {
    this.isModifica = false;
    this.issaveError = false;
    this.isSaveSuccess = false;
    this.isCampiControl = false;
    this.isIdBancaObbligatorio = false;
    this.isTT = true;
    this.isConferma = false;
    this.getTransazione();
  }

  disattiva() {
    this.isModifica = false;
    this.issaveError = false;
    this.isSaveSuccess = false;
    this.isCampiControl = false;
    this.isIdBancaObbligatorio = false;
    this.isConferma = false;
    this.isTT = true;
  }

  onStorico() {
    this.subscribers.storico = this.passaggioPerditaService.getStoricoTransazioni(this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data.length > 0) {
        this.storico = data;
      } else {
        this.disableStorico = true;
      }
    });
  }
  displayFn(banca: AttivitaDTO): string {
    return banca && banca.descAttivita ? banca.descAttivita : ''
  }

  private _filter(descAttivita: string): AttivitaDTO[] {
    const filterValue = descAttivita.toLowerCase();
    return this.listaBanche.filter(option => option.descAttivita.toLowerCase().includes(filterValue));
  }

  check() {
    setTimeout(() => {
      if (!this.bancaSelected || (this.bancaGroup.controls['bancaControl'] && this.bancaSelected !== this.bancaGroup.controls['bancaControl'].value)) {
        this.bancaGroup.controls['bancaControl'].setValue(null);
        this.bancaSelected = new FormControl();
      }
    }, 200);
  }

  click(event: any) {
    this.bancaSelected.setValue(event.option.value);
  }

  isLoading() {
    if (!this.loadedTrans || !this.loadedUser) {
      return true;
    }
    return false;
  }

  suggest(value: string) {

    this.suggesttionnull = new AttivitaDTO;
    if (value.length >= 3) {
      this.listaBanche2 = new Array<AttivitaDTO>();
      this.suggesttionnull.descAttivita = 'caricamento...';
      this.listaBanche2.push(this.suggesttionnull);
      this.subscribers.listaBanche2 = this.passaggioPerditaService.getListaBanche2(value).subscribe(data => {
        if (data.length > 0) {
          this.listaBanche2 = data;
        } else {
          this.listaBanche2 = new Array<AttivitaDTO>();
          this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
          this.listaBanche2.push(this.suggesttionnull);
        }
      });
    }
  }
  getNumberFromFormattedString(importoFormatted: string) {
    if (!importoFormatted) return null;
    importoFormatted = importoFormatted.replace(/[.]/g, '');
    importoFormatted = importoFormatted.replace(',', '.');
    return +importoFormatted;
  }


  openStorico() {
    this.isModifica = false;
    this.isSaveSuccess = false;
    this.isIdBancaObbligatorio = false;
    this.isCampiControl = false;
    this.issaveError = false;
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '600px',
      data: {
        id: 8,
        storico: this.storico
      }
    });
  }
  setImporto(campoNumber: number) {
    switch (campoNumber) {
      case 1:
        this.impRiconciliato = this.sharedService.getNumberFromFormattedString(this.impRiconciliatoFormatted);
        if (this.impRiconciliato !== null) {
          this.impRiconciliatoFormatted = this.sharedService.formatValue(this.impRiconciliato.toString());
        }
        break;
      case 2:
        this.impTransato = this.sharedService.getNumberFromFormattedString(this.impTransatoFormatted);
        if (this.impTransato !== null) {
          this.impTransatoFormatted = this.sharedService.formatValue(this.impTransato.toString());
        }
        break
      case 3:
        this.impPagato = this.sharedService.getNumberFromFormattedString(this.impPagatoFormatted);
        if (this.impPagato !== null) {
          this.impPagatoFormatted = this.sharedService.formatValue(this.impPagato.toString());
        }
        break;
      default:
        break;
    }
  }
}
