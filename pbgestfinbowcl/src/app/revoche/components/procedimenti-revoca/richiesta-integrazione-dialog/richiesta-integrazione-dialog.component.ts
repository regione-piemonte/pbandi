/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ArchivioFileService, HandleExceptionService } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { DocumentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/documento-revoca-vo';
import { ProcedimentiRevocaResponseService } from 'src/app/revoche/services/procedimenti-revoca-response.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-richiesta-integrazione-dialog',
  templateUrl: './richiesta-integrazione-dialog.component.html',
  styleUrls: ['./richiesta-integrazione-dialog.component.scss']
})
export class RichiestaIntegrazioneDialogComponent implements OnInit {
  @ViewChild('uploadLetteraAccompagnatoria') myFileLettera: ElementRef;
  @ViewChild('uploadFile') myFile: ElementRef;

  //PARAMETRI
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;
  isLoading: boolean = false;
  myForm: FormGroup;

  //variabili documenti
  letteraAccompagnatoria: DocumentoRevocaVO = null;
  altriAllegati: DocumentoRevocaVO[] = null;
  newAltriAllegati: Array<File> = new Array<File>();
  newLetteraAccompagnatoria: File = null;
  erroreAggiuntaAllegati: boolean;    //se si presenta un qualsiasi errore durante il caricamento dei documenti, questa variabile diventa TRUE e non viene eseguito avviaProcedimentoRevoca

  constructor(
    private fb: FormBuilder,
    private procedimentiRevocaResponseService: ProcedimentiRevocaResponseService,
    public dialogRef: MatDialogRef<RichiestaIntegrazioneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.isLoading = true;

    //creo form
    this.myForm = this.fb.group({
      giorniScadenza: new FormControl(20, [Validators.required]),
    });

    //recupero i documenti
    this.getDocumenti();
  }

  getDocumenti() {
    this.procedimentiRevocaResponseService.getDocumenti(this.data.numeroProcedimentoRevoca == undefined ? "" : this.data.numeroProcedimentoRevoca).subscribe(data => {
      this.letteraAccompagnatoria = data.find(element => element.idTipoDocumento == '45' && element.bozza);
      this.altriAllegati = data.filter(x => x.idTipoDocumento == '51' && x.bozza);
      this.newLetteraAccompagnatoria = null;
      this.newAltriAllegati = new Array<File>();

      this.isLoading = false;
    }, err => {
      this.isLoading = false;
      this.showMessageError(err);
      console.log(err);
    });
  }

  close() {
    this.dialogRef.close();
  }

  salvaAltriAllegati() {
    if (this.newAltriAllegati.length > 0) {
      for (let i = 0; i < this.newAltriAllegati.length; i++) {
        this.procedimentiRevocaResponseService.aggiungiAllegatoProcedimento(this.newAltriAllegati[i], "altro", "allegatoIntegrazione", "altro", this.data.idProcedimentoRevoca).subscribe(data => {
    
          if (data.code == "OK") {
            console.log("File " + this.newAltriAllegati[i].name + " aggiunto correttamente");
            this.erroreAggiuntaAllegati = false;
          } else if (data.code == "KO") {
            this.erroreAggiuntaAllegati = true;
            console.log("Errore nell'aggiunta del file " + this.newAltriAllegati[i].name);
            this.handleExceptionService.handleNotBlockingError(data.message);
            this.showMessageError(data.message);
          }
    
        }, err => {
          this.erroreAggiuntaAllegati = true;
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError(err);
        });   
      }
    }
  }

  creaIntegrazioneCall() {
    this.procedimentiRevocaResponseService.creaRichiestaIntegrazione(this.data.idProcedimentoRevoca, this.myForm.get("giorniScadenza").value).subscribe(data => {

      console.log("DATA PROCEDIMENTO REVOCA", data)
      if (data.code == "OK") {
        this.showMessageSuccess(data.message);
        setTimeout(() => {
          this.dialogRef.close();
        }, 1000); 
      } else {
        this.showMessageError(data.message)
      }

    }, err => {
      this.showMessageError(err);
    });
  }

  salvaDocumenti() {
    //SE NEL DB NON ERA ANCORA PRESENTE LA LETTERA ACCOMPAGNATORIA (e quindi è appena stata aggiunta)
    if(this.myForm.valid && this.newLetteraAccompagnatoria) {
      //salvo lettera accompagnatoria
      this.procedimentiRevocaResponseService.aggiungiAllegatoProcedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "allegatoIntegrazione", "altro", this.data.idProcedimentoRevoca).subscribe(data => {

        if (data.code == 'OK') {
          this.erroreAggiuntaAllegati = false;
          this.salvaAltriAllegati();
          this.close();

        } else if (data.code == 'KO') {
          this.erroreAggiuntaAllegati = true;
          console.log("Errore durante l'aggiunta della lettera accompagnatoria")
          this.handleExceptionService.handleNotBlockingError(data.message);
          this.showMessageError(data.message);
        }

      }, err => {
        this.erroreAggiuntaAllegati = true;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err);
      });

    }
  }

  creaIntegrazione() {
    //SE NEL DB NON ERA ANCORA PRESENTE LA LETTERA ACCOMPAGNATORIA (e quindi è appena stata aggiunta)
    if (this.myForm.valid && this.newLetteraAccompagnatoria) {
      //salvo lettera accompagnatoria
      this.procedimentiRevocaResponseService.aggiungiAllegatoProcedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "allegatoIntegrazione", "altro", this.data.idProcedimentoRevoca).subscribe(data => {

        if (data.code == 'OK') {
          this.erroreAggiuntaAllegati = false;
          this.salvaAltriAllegati();
          this.creaIntegrazioneCall();

        } else if (data.code == 'KO') {
          this.erroreAggiuntaAllegati = true;
          console.log("Errore durante l'aggiunta della lettera accompagnatoria")
          this.handleExceptionService.handleNotBlockingError(data.message);
          this.showMessageError(data.message);
        }

      }, err => {
        this.erroreAggiuntaAllegati = true;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err);
      })

    } else if (this.myForm.valid && this.letteraAccompagnatoria) {
      this.erroreAggiuntaAllegati = false;
      this.salvaAltriAllegati();
      this.creaIntegrazioneCall();
    } else {
      this.showMessageError("È necessario specificare i giorni scadenza e allegare la lettera accompagnatoria");
      this.handleExceptionService.handleNotBlockingError("È necessario specificare i giorni scadenza e allegare la lettera accompagnatoria");
    }
  }

  loadedDownload: boolean;
  downloadAllegato(idDocumentoIndex: string) {
    this.resetMessage();
    this.loadedDownload = false;
    this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        let nome = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nome);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  /*************************
   ***** GESTIONE FILE *****
   *************************/

  //LETTERA ACCOMPAGNATORIA

  handleFileInputLetteraAccompagnatoria(files: Array<File>) {
    //viene considerato SOLO IL PRIMO FILE selezionato
    this.newLetteraAccompagnatoria = files[0];
    console.log("ALLEGATI", this.newLetteraAccompagnatoria);
  }
  
  eliminaLetteraAccompagnatoriaLocale() {
    this.newLetteraAccompagnatoria = null;
    this.myFileLettera.nativeElement.value = '';
  }

  //ALTRI ALLEGATI

  handleFileInput(files: Array<File>) {
    this.newAltriAllegati.push(...files);
    console.log("ALLEGATI", this.newAltriAllegati);
  }

  eliminaFileLocale(index: number) {
    this.newAltriAllegati.splice(index, 1);
    this.myFile.nativeElement.value = '';
  }

  //UNIVERSALE

  deleteAllegato(idDocumentoIndex) {
    this.procedimentiRevocaResponseService.deleteAllegato(idDocumentoIndex).subscribe(data => {
      if (data.code == 'OK') {
        this.getDocumenti()
      } else {
        /* this.showError = true;
        this.showSucc = false; */
        this.showMessageError("non è stato possibile eliminare l'allegato'.");
      }

    })
  }

  /*************************
   **** MESSAGGI ERRORE ****
   *************************/

  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
  }
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.success = true;
  }
  resetMessage() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }

}
