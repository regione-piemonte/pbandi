/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { RevocaBancariaDTO } from 'src/app/gestione-crediti/commons/dto/revoca-bancaria-dto';
import { StoricoRevocaDTO } from 'src/app/gestione-crediti/commons/dto/storico-revoca-dto';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-dialog-modifica-box-revoca-bancaria',
  templateUrl: './dialog-modifica-box-revoca-bancaria.component.html',
  styleUrls: ['./dialog-modifica-box-revoca-bancaria.component.scss']
})
export class DialogModificaBoxRevocaBancariaComponent implements OnInit {

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
    public dialogRef: MatDialogRef<DialogModificaBoxRevocaBancariaComponent>,
    private userService: UserService,
    private sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }



  setDataNull(idData: number){
    if(idData==1){
      this.dataRicezComunicazioneRevoca = null; 
    } 
    if(idData ==2) {
      this.dataRevoca = null
    }
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

  salva(){

  }

  closeDialog() { this.dialogRef.close() }

  downloadStorico(){

  }

}
