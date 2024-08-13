/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { DatiModificaTemplate } from '../../commons/vo/dati-modifica-template-DTO';
import { SezioneVO } from '../../commons/vo/sezione-DTO';
import { ModelliDocumentoService } from '../../services/modelli-documento.service';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

export class MicroSezioneNewArray {
  constructor(
    public microSezione: SezioneVO,
    public isChecked: boolean
  ) { }
}

export class SezioneNewArray {
  constructor(
    public descMacroSezione: string,
    public numOrdinamentoMacroSezione: number,
    public microSezioni: Array<MicroSezioneNewArray>,
    // public microSezioniAggiungiCampo: Array<MicroSezioneNewArray>,
  ) { }
}

export class SezioneArraySalvataggio {
  constructor(
    public idMacroSezioneModulo: string,
    public idMicroSezioneModulo: string,
    public contenutoMicroSezione: string,
    public descMicroSezione: string,
    public progrBlTipoDocSezMod: string,
    public progrBandoLineaIntervento: string,
    public idTipoDocumentoIndex: string
  ) { }
}

@Component({
  selector: 'app-modifica-modello-documento-dialog',
  templateUrl: './modifica-modello-documento-dialog.component.html',
  styleUrls: ['./modifica-modello-documento-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ModificaModelloDocumentoDialogComponent implements OnInit {

  // Dichiarazione variabili
  tipoDocumento: string;
  codiceTipo: string;
  isCheckedTitolo = true;
  isCheckedDestinatario = true;
  isCheckedIntestazione = true;
  isCheckedCorpo = true;
  isCheckedFirma = true;
  user: UserInfoSec;
  idBandoLinea: string;
  id: number;
  showModelli: boolean;
  dataTemplateNew = new Array<SezioneNewArray>();
  dataTemplatOld: DatiModificaTemplate

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedModificaTipoDocumento = false;
  loadedSalvaModificheDocumento = false;

  showDraggableSection = true;

  subscribers: any = {};

  constructor(@Inject(MAT_DIALOG_DATA) public data: Array<any>,
    private modelliDocumentoService: ModelliDocumentoService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<ModificaModelloDocumentoDialogComponent>) { }

  ngOnInit(): void {

    this.user = this.data[0];
    this.idBandoLinea = this.data[1];
    this.id = this.data[2];

    this.loadedModificaTipoDocumento = true;
    this.subscribers.modifica = this.modelliDocumentoService.modificaTipoDocumento(this.user, this.idBandoLinea, this.id.toString()).subscribe(data => {
      this.loadedModificaTipoDocumento = false;
      if (data.message == undefined) {
        this.dataTemplatOld = data;
        this.showModelli = true;
        this.dataTemplateNew = this.createNewArraySezioni(this.dataTemplatOld.sezioni);
      } else {
        this.showModelli = false;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento dati del modello di documento associato.");
      this.loadedModificaTipoDocumento = false;
    })
  }

  inizioCDK() {
    this.showDraggableSection = false;
  }

  fineCDK() {
    this.showDraggableSection = false;
  }

  drop2(macroSezione: SezioneNewArray, event: CdkDragDrop<string[]>) {
    moveItemInArray(macroSezione.microSezioni, event.previousIndex, event.currentIndex);
  }
  /* Fine prova cdk */


  createNewArraySezioni(oldArray: Array<SezioneVO>) {
    var newArray = new Array<SezioneNewArray>();
    var arrayTemporaneo: SezioneNewArray;
    for (let i = 0; i < oldArray.length; i++) {
      if (i == oldArray.length - 1) {
        if (oldArray[i].numOrdinamentoMicroSezione == 1) {
          newArray.push(arrayTemporaneo);
          var comodoSezioni = new Array<MicroSezioneNewArray>();
          //  var comodoSezioniAggiungiCampo = new Array<MicroSezioneNewArray>();
          comodoSezioni.push(new MicroSezioneNewArray(oldArray[i], true));
          arrayTemporaneo = new SezioneNewArray(oldArray[i].descMacroSezione, oldArray[i].numOrdinamentoMacroSezione, comodoSezioni/*, comodoSezioniAggiungiCampo*/);
          newArray.push(arrayTemporaneo);
        } else {
          arrayTemporaneo.microSezioni.push(new MicroSezioneNewArray(oldArray[i], true));
          newArray.push(arrayTemporaneo);
        }
      } else {
        if (oldArray[i].numOrdinamentoMicroSezione == 1) {
          if (i == 0) {
            var comodoSezioni = new Array<MicroSezioneNewArray>();
            //   var comodoSezioniAggiungiCampo = new Array<MicroSezioneNewArray>();
            comodoSezioni.push(new MicroSezioneNewArray(oldArray[i], true));
            arrayTemporaneo = new SezioneNewArray(oldArray[i].descMacroSezione, oldArray[i].numOrdinamentoMacroSezione, comodoSezioni/*, comodoSezioniAggiungiCampo*/);
          } else {
            newArray.push(arrayTemporaneo);
            var comodoSezioni = new Array<MicroSezioneNewArray>();
            //   var comodoSezioniAggiungiCampo = new Array<MicroSezioneNewArray>();
            comodoSezioni.push(new MicroSezioneNewArray(oldArray[i], true));
            arrayTemporaneo = new SezioneNewArray(oldArray[i].descMacroSezione, oldArray[i].numOrdinamentoMacroSezione, comodoSezioni/*, comodoSezioniAggiungiCampo*/);
          }
        } else {
          arrayTemporaneo.microSezioni.push(new MicroSezioneNewArray(oldArray[i], true));
        }
      }
    }
    return newArray;
  }

  costruisciArraySalvataggio() {
    var newArray = new Array<SezioneArraySalvataggio>();
    for (let a of this.dataTemplateNew) {
      for (let b of a.microSezioni) {
        if (b.isChecked) {
          if (!(b.microSezione.contenutoMicroSezione == "")) {
            newArray.push(new SezioneArraySalvataggio(b.microSezione.idMacroSezioneModulo.toString(), b.microSezione.idMicroSezioneModulo.toString(), b.microSezione.contenutoMicroSezione, b.microSezione.descMicroSezione, b.microSezione.progrBlTipoDocSezMod.toString(), b.microSezione.progrBandoLineaIntervento.toString(), b.microSezione.idTipoDocumentoIndex.toString()));
          }
        }
      }
      /* for (let c of a.microSezioniAggiungiCampo) {
         if (c.isChecked) {
           if (!(c.microSezione.contenutoMicroSezione == "")) {
             newArray.push(new SezioneArraySalvataggio(c.microSezione.idMacroSezioneModulo.toString(), c.microSezione.idMicroSezioneModulo.toString(), c.microSezione.contenutoMicroSezione, c.microSezione.descMicroSezione, c.microSezione.progrBlTipoDocSezMod.toString(), c.microSezione.progrBandoLineaIntervento.toString(), c.microSezione.idTipoDocumentoIndex.toString()));
           }
         }
       }*/
    }
    return JSON.stringify(newArray);
  }

  drop(event: CdkDragDrop<string[]>, macroSezione: SezioneNewArray, microSezione: MicroSezioneNewArray) {
    if (!(event.previousContainer === event.container)) {
      var vecchioContenutoMicroSezione = this.dataTemplateNew.find(a => a == macroSezione).microSezioni.find(b => b == microSezione).microSezione.contenutoMicroSezione;
      this.dataTemplateNew.find(a => a == macroSezione).microSezioni.find(b => b == microSezione).microSezione.contenutoMicroSezione = event.previousContainer.data[event.previousIndex] + vecchioContenutoMicroSezione;
    }
  }

  entrareInDiv() {
    this.showDraggableSection = true;
  }

  /*dropAggiungiCampo(event: CdkDragDrop<string[]>, macroSezione: SezioneNewArray, microSezioneAggiungiCampo: MicroSezioneNewArray) {
    if (!(event.previousContainer === event.container)) {
      var vecchioContenutoMicroSezione = this.dataTemplateNew.find(a => a == macroSezione).microSezioniAggiungiCampo.find(b => b == microSezioneAggiungiCampo).microSezione.contenutoMicroSezione;
      this.dataTemplateNew.find(a => a == macroSezione).microSezioniAggiungiCampo.find(b => b == microSezioneAggiungiCampo).microSezione.contenutoMicroSezione = event.previousContainer.data[event.previousIndex] + vecchioContenutoMicroSezione;
    }
  }*/

  aggiungiCampo(comodo: SezioneNewArray) {
    for (let item of this.dataTemplateNew) {
      if (comodo.descMacroSezione == item.descMacroSezione) {
        /*if (item.microSezioniAggiungiCampo.length == 0) {
          var lastMicroSezione: MicroSezioneNewArray;
          for (var i = 0; i < item.microSezioni.length; i++) {
            if ((i + 1) == (item.microSezioni.length)) {
              lastMicroSezione = JSON.parse(JSON.stringify(item.microSezioni[i]))
            }
          }

          lastMicroSezione.isChecked = true;
          lastMicroSezione.microSezione.numOrdinamentoMicroSezione = lastMicroSezione.microSezione.numOrdinamentoMicroSezione + 1;
          lastMicroSezione.microSezione.contenutoMicroSezione = "";

          item.microSezioniAggiungiCampo.push(lastMicroSezione);
        } else {
          var lastMicroSezioneAggiungiCampo: MicroSezioneNewArray;
          for (var i = 0; i < item.microSezioniAggiungiCampo.length; i++) {
            if ((i + 1) == (item.microSezioniAggiungiCampo.length)) {
              lastMicroSezioneAggiungiCampo = JSON.parse(JSON.stringify(item.microSezioniAggiungiCampo[i]))
            }
          }

          lastMicroSezioneAggiungiCampo.isChecked = true;
          lastMicroSezioneAggiungiCampo.microSezione.numOrdinamentoMicroSezione = lastMicroSezioneAggiungiCampo.microSezione.numOrdinamentoMicroSezione + 1;
          lastMicroSezioneAggiungiCampo.microSezione.contenutoMicroSezione = "";
          item.microSezioniAggiungiCampo.push(lastMicroSezioneAggiungiCampo);
          
        }*/
        var lastMicroSezione: MicroSezioneNewArray;
        for (var i = 0; i < item.microSezioni.length; i++) {
          if ((i + 1) == (item.microSezioni.length)) {
            lastMicroSezione = JSON.parse(JSON.stringify(item.microSezioni[i]))
          }
        }
        lastMicroSezione.isChecked = true;
        lastMicroSezione.microSezione.numOrdinamentoMicroSezione = lastMicroSezione.microSezione.numOrdinamentoMicroSezione + 1;
        lastMicroSezione.microSezione.contenutoMicroSezione = "";

        item.microSezioni.push(lastMicroSezione);
        break;
      }
    }
  }

  /* -------------------- inizio generale --------------------*/
  isLoading() {
    if (this.loadedSalvaModificheDocumento || this.loadedModificaTipoDocumento) {
      return true;
    } else {
      return false;
    }
  }
  /* -------------------- fine generale --------------------*/

  /* -------------------- Inizio gestione pulsanti --------------------*/
  salva() {
    var paramSezioni = this.costruisciArraySalvataggio();
    this.loadedSalvaModificheDocumento = true;
    this.subscribers.salvaMoficheoDocumento = this.modelliDocumentoService.salvaMoficheoDocumento(this.user, paramSezioni).subscribe(data => {
      this.loadedSalvaModificheDocumento = false;
      this.dialogRef.close();
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento dati del modello di documento associato");
      this.loadedSalvaModificheDocumento = false;
    })
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  annulla() {
    this.dialogRef.close();
  }
  /* -------------------- Fine gestione pulsanti --------------------*/
}
