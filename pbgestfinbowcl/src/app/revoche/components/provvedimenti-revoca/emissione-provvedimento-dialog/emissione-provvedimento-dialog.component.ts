/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ArchivioFileService, Constants, HandleExceptionService } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { DocumentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/documento-revoca-vo';
import { ProvvedimentiRevocaResponseService } from 'src/app/revoche/services/provvedimenti-revoca-response.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-emissione-provvedimento-dialog',
  templateUrl: './emissione-provvedimento-dialog.component.html',
  styleUrls: ['./emissione-provvedimento-dialog.component.scss']
})
export class EmissioneProvvedimentoDialogComponent implements OnInit {
  @ViewChild('uploadLetteraAccompagnatoria') myFileLettera: ElementRef;
  @ViewChild('uploadFile') myFile: ElementRef;

  myForm: FormGroup;

  //gestione messaggi errore/successo
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;

  //Gestione FILE
  letteraAccompagnatoria: DocumentoRevocaVO = null;
  altriAllegati: DocumentoRevocaVO[] = null;
  newAltriAllegati: Array<File> = new Array<File>();
  newLetteraAccompagnatoria: File = null;
  erroreAggiuntaAllegati: boolean;


  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<EmissioneProvvedimentoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private provvedimentiRevocaResponseService: ProvvedimentiRevocaResponseService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    //creo form
    this.myForm = this.fb.group({
      giorniScadenza: new FormControl("", [Validators.required]),
    });

    //recupero i documenti
    this.getDocumenti();
  }

  getDocumenti() {
    this.provvedimentiRevocaResponseService.getDocumentiProvvedimentoRevoca(this.data.numeroGestioneRevoca == undefined ? "" : this.data.numeroGestioneRevoca).subscribe(data => {
      this.letteraAccompagnatoria = data.find(element => element.idTipoDocumento == '47');
      this.altriAllegati = data.filter(x => x.idTipoDocumento == '53');
      this.newLetteraAccompagnatoria = null;
      this.newAltriAllegati = new Array<File>();
    }, err => {
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
        this.provvedimentiRevocaResponseService.aggiungiAllegatoProvvedimento(this.newAltriAllegati[i], "altro", "emissione", this.data.numeroGestioneRevoca).subscribe(data => {
    
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
  
  emettiProvvedimentoCall() {
    if (this.erroreAggiuntaAllegati == false) {
      this.provvedimentiRevocaResponseService.emettiProvvedimentoRevoca(this.data?.numeroGestioneRevoca, this.myForm.get("giorniScadenza").value.toString()).subscribe(data => {

        if (data.code == 'OK') {
          this.showMessageSuccess(data.message);
          setTimeout(() => {
            this.dialogRef.close();
          }, 1000); 

        } else if (data.code == 'KO') {
          this.showMessageError(data.message);
          this.handleExceptionService.handleNotBlockingError(data.message);
        }
      }, err => {
        this.showMessageError(err);
        this.handleExceptionService.handleNotBlockingError(err);
      })
    }
  }

  salvaDocumenti() {
    //SE NEL DB NON ERA ANCORA PRESENTE LA LETTERA ACCOMPAGNATORIA (e quindi è appena stata aggiunta)
    if(this.myForm.valid && this.newLetteraAccompagnatoria) {
      //salvo lettera accompagnatoria
      this.provvedimentiRevocaResponseService.aggiungiAllegatoProvvedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "emissione", this.data.numeroGestioneRevoca).subscribe(data => {

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

  emettiProvvedimento() {
    //SE NEL DB NON ERA ANCORA PRESENTE LA LETTERA ACCOMPAGNATORIA (e quindi è appena stata aggiunta)
    if (this.myForm.valid && this.newLetteraAccompagnatoria) {
      //salvo lettera accompagnatoria
      this.provvedimentiRevocaResponseService.aggiungiAllegatoProvvedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "emissione", this.data.numeroGestioneRevoca).subscribe(data => {

        if (data.code == 'OK') {
          this.erroreAggiuntaAllegati = false;
          this.salvaAltriAllegati();
          this.emettiProvvedimentoCall();

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
      this.emettiProvvedimentoCall();
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
    this.provvedimentiRevocaResponseService.deleteAllegato(idDocumentoIndex).subscribe(data => {
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

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.success = true;
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  resetMessage() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }
  
}
