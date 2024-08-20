/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Inject, ElementRef, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ArchivioFileService, HandleExceptionService } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { DocumentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/documento-revoca-vo';
import { ProvvedimentiRevocaResponseService } from 'src/app/revoche/services/provvedimenti-revoca-response.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-conferma-provvedimento-dialog',
  templateUrl: './conferma-provvedimento-dialog.component.html',
  styleUrls: ['./conferma-provvedimento-dialog.component.scss']
})
export class ConfermaProvvedimentoDialogComponent implements OnInit {
  @ViewChild('uploadLetteraAccompagnatoria') myFileLettera: ElementRef;
  @ViewChild('uploadFile') myFile: ElementRef;

  //PARAMETRI
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;

  //variabili documenti
  letteraAccompagnatoria: DocumentoRevocaVO = null;
  altriAllegati: DocumentoRevocaVO[] = null;
  newAltriAllegati: Array<File> = new Array<File>();
  newLetteraAccompagnatoria: File = null;
  erroreAggiuntaAllegati: boolean;


  constructor(
    public dialogRef: MatDialogRef<ConfermaProvvedimentoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private provvedimentiRevocaResponseService: ProvvedimentiRevocaResponseService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService

  ) { }

  ngOnInit(): void {

    //recupero i documenti
    this.getDocumenti();
  }

  getDocumenti() {
    this.provvedimentiRevocaResponseService.getDocumentiProvvedimentoRevoca(this.data.numeroGestioneRevoca == undefined ? "" : this.data.numeroGestioneRevoca).subscribe(data => {
      this.letteraAccompagnatoria = data.find(element => element.idTipoDocumento == '49');
      this.altriAllegati = data.filter(x => x.idTipoDocumento == '55');
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
        this.provvedimentiRevocaResponseService.aggiungiAllegatoProvvedimento(this.newAltriAllegati[i], "altro", "conferma", this.data.numeroGestioneRevoca).subscribe(data => {
    
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
  
  confermaProvvedimentoCall() {
    if (this.erroreAggiuntaAllegati == false) {
      this.provvedimentiRevocaResponseService.confermaProvvedimentoRevoca(this.data?.numeroGestioneRevoca).subscribe(data => {

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
    if(this.newLetteraAccompagnatoria) {
      //salvo lettera accompagnatoria
      this.provvedimentiRevocaResponseService.aggiungiAllegatoProvvedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "conferma", this.data.numeroGestioneRevoca).subscribe(data => {

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

  confermaProvvedimento() {
    //SE NEL DB NON ERA ANCORA PRESENTE LA LETTERA ACCOMPAGNATORIA (e quindi è appena stata aggiunta)
    if (this.newLetteraAccompagnatoria) {
      //salvo lettera accompagnatoria
      this.provvedimentiRevocaResponseService.aggiungiAllegatoProvvedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "conferma", this.data.numeroGestioneRevoca).subscribe(data => {

        if (data.code == 'OK') {
          this.erroreAggiuntaAllegati = false;
          this.salvaAltriAllegati();
          this.confermaProvvedimentoCall();

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

    } else if (this.letteraAccompagnatoria) {
      this.erroreAggiuntaAllegati = false;
      this.salvaAltriAllegati();
      this.confermaProvvedimentoCall();
    } else {
      this.showMessageError("È necessario allegare la lettera accompagnatoria");
      this.handleExceptionService.handleNotBlockingError("È necessario allegare la lettera accompagnatoria");
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
