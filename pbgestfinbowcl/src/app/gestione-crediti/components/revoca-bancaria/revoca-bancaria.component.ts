/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RevocaBancariaDTO } from '../../commons/dto/revoca-bancaria-dto';
import { RevocaBancariaService } from '../../services/revoca-bancaria.service';
import { MatCard } from '@angular/material/card';
import { MatTab } from '@angular/material/tabs';
import { MatDivider } from '@angular/material/divider';
import { Subscriber } from 'rxjs';
import { UserService } from 'src/app/core/services/user.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { StoricoRevocaDTO } from '../../commons/dto/storico-revoca-dto';
import { escapeRegExp } from '@angular/compiler/src/util';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';
import { MatDialog } from '@angular/material/dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-revoca-bancaria',
  templateUrl: './revoca-bancaria.component.html',
  styleUrls: ['./revoca-bancaria.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RevocaBancariaComponent implements OnInit {
  @Input() item ;
  @Input() ambito ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  allegati: any;
  myForm: FormGroup;
  revocaBancariaDTO: RevocaBancariaDTO = new RevocaBancariaDTO();
  isConferma: boolean = false;
  isRevoca: boolean;
  isMandato: boolean;
  isDisp: boolean;
  isCampiControl: boolean;
  idProgetto: number;
  dataRicezComunicazioneRevoca: Date
  dataRevoca: Date
  impDebitoResiduoBanca: number
  impDebitoResiduoFinpiemonte: number
  perCofinanziamentoFinpiemonte: number;
  numeroProtocollo: string;
  note: string
  idUtente: number;
  totaleFinepiemonte: number;
  totaleErogato: number;
  subscribers: any = {};
  storico: Array<StoricoRevocaDTO>;
  user: UserInfoSec
  todayDate = new Date();
  displayedColumns: string[] = ['dataInserimento', 'istruttore'];

  idRevoca: number;
  loadedRB: boolean;
  loadedUser: boolean;
  messageSuccess: string = "Salvato";
  isSaveSuccess: boolean;
  isCampiVuoti: boolean;
  disableStorico: boolean;
  isModifica: boolean;
  importoBanca: number;
  importoFormatted: string;
  impDebitoResiduoBancaFormatted: string;
  impDebitoResiduoFinpiemonteFormatted: string;
  


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private revocaBancariaService: RevocaBancariaService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    public dialog: MatDialog

  ) { }

  

  ngOnInit(): void {
    console.log(this.nomeBox);
   // Totale erogato	Totale erogato Finpiemonte
    this.loadedUser = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.loadedUser = true;
        this.route.queryParams.subscribe(params => {
          this.item = params.idProgetto;
          this.totaleErogato = params.totEro;
          this.totaleFinepiemonte = params.totFin;
          

        });
        //this.idProgetto = this.item;
        let factor = Math.pow(10, 2);
        let div = (this.totaleErogato / this.totaleFinepiemonte) * 100;
        this.perCofinanziamentoFinpiemonte = Math.round(div * factor) / factor;
        this.getRevocaBancaria();
        this.onStorico();

      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });

  }

  /* recuperaFile(newItem) {
    this.allegati = newItem;
    
  } */

  getRevocaBancaria() {
    this.loadedRB = false;
    //console.log("idProg: ", this.idProgetto);
    //console.log("item: ", this.item);
    
    this.subscribers.revoca = this.revocaBancariaService.getRevoca(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(data => {
      //this.subscribers.revoca = this.revocaBancariaService.getRevoca(this.idUtente, 19217, this.idModalitaAgevolazione).subscribe(data => {
      if (data.idRevoca != null) {
        this.revocaBancariaDTO = data;
        this.dataRevoca = this.revocaBancariaDTO.dataRevoca;
        this.dataRicezComunicazioneRevoca = this.revocaBancariaDTO.dataRicezComunicazioneRevoca;
        this.impDebitoResiduoBanca = this.revocaBancariaDTO.impDebitoResiduoBanca;
        if(this.impDebitoResiduoBanca!=null){
          this.impDebitoResiduoBancaFormatted = this.sharedService.formatValue(this.impDebitoResiduoBanca.toString());
        }
        this.impDebitoResiduoFinpiemonte = this.revocaBancariaDTO.impDebitoResiduoFinpiemonte;
        if(this.impDebitoResiduoFinpiemonte!=null){
          this.impDebitoResiduoFinpiemonteFormatted = this.sharedService.formatValue(this.impDebitoResiduoFinpiemonte.toString());
        }
        this.note = this.revocaBancariaDTO.note;

        //console.log(this.ambito)
        if(this.ambito == "escussioni") {
          this.perCofinanziamentoFinpiemonte = 0;
        } else {
          this.perCofinanziamentoFinpiemonte = this.revocaBancariaDTO.perCofinanziamentoFinpiemonte;
        }

        
        this.numeroProtocollo = this.revocaBancariaDTO.numeroProtocollo;
        this.idRevoca = this.revocaBancariaDTO.idRevoca;
        this.isRevoca = true;
        this.isConferma = false
        //console.log(this.revocaBancariaDTO);
      } else {
        this.isRevoca = false
        this.isConferma = true;
      }
      this.loadedRB = true;
    });


  }

  setDataNull(idData: number){
    if(idData==1){
      this.dataRicezComunicazioneRevoca = null; 
    } 
    if(idData ==2) {
      this.dataRevoca = null
    }
  }
  
  salva() {
    this.isCampiVuoti = false;

    if (this.dataRicezComunicazioneRevoca == this.revocaBancariaDTO.dataRicezComunicazioneRevoca &&
      this.dataRevoca == this.revocaBancariaDTO.dataRevoca &&
      this.impDebitoResiduoBanca == this.revocaBancariaDTO.impDebitoResiduoBanca &&
      this.impDebitoResiduoFinpiemonte == this.revocaBancariaDTO.impDebitoResiduoFinpiemonte &&
      this.numeroProtocollo == this.revocaBancariaDTO.numeroProtocollo &&
      this.note == this.revocaBancariaDTO.note) {
      this.isModifica = true;
    } else {
      this.isModifica = false;
      this.revocaBancariaDTO.dataRicezComunicazioneRevoca = this.dataRicezComunicazioneRevoca;
      this.revocaBancariaDTO.dataRevoca = this.dataRevoca;
      this.revocaBancariaDTO.impDebitoResiduoBanca = this.impDebitoResiduoBanca;
      this.revocaBancariaDTO.impDebitoResiduoFinpiemonte = this.impDebitoResiduoFinpiemonte;
      this.revocaBancariaDTO.perCofinanziamentoFinpiemonte = this.perCofinanziamentoFinpiemonte;
      this.revocaBancariaDTO.numeroProtocollo = this.numeroProtocollo;
      this.revocaBancariaDTO.note = this.note;
      this.revocaBancariaDTO.idUtenteIns = this.idUtente;
      this.revocaBancariaDTO.idProgetto = this.item;
      this.isModifica = false;
      this.revocaBancariaDTO.allegati = this.allegati;
      /* this.subscribers.resultRevoca = this.revocaBancariaService.salvaRevoca(this.revocaBancariaDTO , this.allegati , this.item , this.idModalitaAgevolazione ).subscribe(data => { */
      this.subscribers.resultRevoca = this.revocaBancariaService.salvaRevoca(this.revocaBancariaDTO ,this.idUtente , this.item , this.idModalitaAgevolazione ).subscribe(data => {
        if (data) {
          this.isMandato = Boolean(data);
          this.isSaveSuccess = true;
          this.isRevoca = true;
          this.isConferma = false;
          this.onStorico();
          this.disableStorico = false;
        }
      });


    }
  }

  onModifica() {
    this.isModifica = false;
    this.isCampiVuoti = false;
    this.isSaveSuccess = false;
    this.isCampiControl = false;
    this.isRevoca = false;
    this.isConferma = true;

  }


  onStorico() {
    this.subscribers.home = this.revocaBancariaService.getStorico(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data.length > 0) {
        this.storico = data;
      } else {
        this.disableStorico = true;
      }
    }, err => {
      console.log("errore");
    });

  }

  onAnnulla() {
    this.isModifica = false;
    this.isCampiControl = false;
    this.isSaveSuccess = false;
    this.getRevocaBancaria();
    this.isRevoca = true;
    this.isConferma = false;
  }

  controllaCampi() {
    this.isCampiControl = false;
    var regex = /^\d+(?:\.\d{1,2})?$/;
    var regex2 = /[\w\[\].-\^-]*/;
    

    if (this.dataRevoca == null && this.dataRicezComunicazioneRevoca == null && this.impDebitoResiduoBanca == null
      && this.impDebitoResiduoFinpiemonte == null && this.numeroProtocollo == null && this.note == null) {
      this.isCampiVuoti = true;
    } else {
      if (
        ((this.impDebitoResiduoBanca != null && this.impDebitoResiduoBanca.toString().length > 13)
          || (this.impDebitoResiduoBanca != null && !this.impDebitoResiduoBanca.toString().match(regex)))

        || ((this.impDebitoResiduoFinpiemonte != null && this.impDebitoResiduoFinpiemonte.toString().length > 13)
          || (this.impDebitoResiduoFinpiemonte != null && !this.impDebitoResiduoFinpiemonte.toString().match(regex)))

      ) {
        console.log("i campi non vanno bene");
        this.isCampiControl = true;
      } else {
          this.salva();
        console.log("salviamo ok !!")
      }
    }

  }

  openStorico() {
    this.isModifica = false;
    this.isSaveSuccess = false;
    this.isCampiVuoti = false;
    this.isCampiControl = false;
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '600px',
      data: {
        id: 1,
        storico: this.storico
      }
    });
  }

  setImpDebitoResiduoBanca(){
    this.impDebitoResiduoBanca = this.sharedService.getNumberFromFormattedString(this.impDebitoResiduoBancaFormatted);
    if (this.impDebitoResiduoBanca !== null) {
      this.impDebitoResiduoBancaFormatted = this.sharedService.formatValue(this.impDebitoResiduoBanca.toString());
    }
  }
  setImpDebitoResiduoFinpiemonte(){
    this.impDebitoResiduoFinpiemonte = this.sharedService.getNumberFromFormattedString(this.impDebitoResiduoFinpiemonteFormatted);
    if (this.impDebitoResiduoFinpiemonte !== null) {
      this.impDebitoResiduoFinpiemonteFormatted = this.sharedService.formatValue(this.impDebitoResiduoFinpiemonte.toString());
    }
  }

  isLoading() {
    if (!this.loadedRB || !this.loadedUser) {
      return true;
    }
    return false;
  }



}
