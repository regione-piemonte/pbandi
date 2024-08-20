/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ArchivioFileService, Constants, HandleExceptionService } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { AllegatoVO } from 'src/app/gestione-crediti/commons/dto/allegatoVO';
import { DocumentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/documento-revoca-vo';
import { ProcedimentiRevocaResponseService } from 'src/app/revoche/services/procedimenti-revoca-response.service';
import { saveAs } from 'file-saver-es';
@Component({
  selector: 'app-avvia-procedimento-rev-dialog',
  templateUrl: './avvia-procedimento-rev-dialog.component.html',
  styleUrls: ['./avvia-procedimento-rev-dialog.component.scss']
})
export class AvviaProcedimentoRevDialogComponent implements OnInit {
  @ViewChild('uploadLetteraAccompagnatoria') myFileLettera: ElementRef;
  @ViewChild('uploadFile') myFile: ElementRef;

  myForm: FormGroup;
  isLoading: boolean = false;

  //Gestione FILE
  letteraAccompagnatoria: DocumentoRevocaVO = null;
  altriAllegati: DocumentoRevocaVO[] = null;
  newAltriAllegati: Array<File> = new Array<File>();
  newLetteraAccompagnatoria: File = null;
  erroreAggiuntaAllegati: boolean;    //se si presenta un qualsiasi errore durante il caricamento dei documenti, questa variabile diventa TRUE e non viene eseguito avviaProcedimentoRevoca

  //gestione messaggi errore/successo
  error: boolean = false;
  success: boolean = false;
  messageError: string;

  constructor(
    private fb: FormBuilder,
    private procedimentiRevocaResponseService: ProcedimentiRevocaResponseService,
    private handleExceptionService: HandleExceptionService,
    private router: Router,
    public dialogRef: MatDialogRef<AvviaProcedimentoRevDialogComponent>,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    @Inject(MAT_DIALOG_DATA) public data: any
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
      this.letteraAccompagnatoria = data.find(element => element.idTipoDocumento == '44');
      this.altriAllegati = data.filter(x => x.idTipoDocumento == '50');
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

  tornaAllaRicerca() {
    this.dialogRef.close();

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROCEDIMENTI + "/procedimentiRevoca"], {});
  }

  salvaAltriAllegati() {
    if (this.newAltriAllegati.length > 0) {
      for (let i = 0; i < this.newAltriAllegati.length; i++) {
        this.procedimentiRevocaResponseService.aggiungiAllegatoProcedimento(this.newAltriAllegati[i], "altro", "altro", "altro", this.data.idProcedimentoRevoca).subscribe(data => {
    
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
  
  avviaProcedimentoCall() {
    if (this.erroreAggiuntaAllegati == false) {
      this.procedimentiRevocaResponseService.avviaProcedimentoRevoca(this.data?.numeroProcedimentoRevoca, this.myForm.get("giorniScadenza").value.toString()).subscribe(data => {

        if (data.code == 'OK') {
          this.tornaAllaRicerca();

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
      this.procedimentiRevocaResponseService.aggiungiAllegatoProcedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "altro", "altro", this.data.idProcedimentoRevoca).subscribe(data => {

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

  avviaProcedimento() {
    //SE NEL DB NON ERA ANCORA PRESENTE LA LETTERA ACCOMPAGNATORIA (e quindi è appena stata aggiunta)
    if (this.myForm.valid && this.newLetteraAccompagnatoria) {
      //salvo lettera accompagnatoria
      this.procedimentiRevocaResponseService.aggiungiAllegatoProcedimento(this.newLetteraAccompagnatoria, "letteraAccompagnatoria", "altro", "altro", this.data.idProcedimentoRevoca).subscribe(data => {

        if (data.code == 'OK') {
          this.erroreAggiuntaAllegati = false;
          this.salvaAltriAllegati();
          this.avviaProcedimentoCall();

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
      this.avviaProcedimentoCall();
    } else {
      this.showMessageError("È necessario specificare i giorni scadenza e allegare la lettera accompagnatoria");
      this.handleExceptionService.handleNotBlockingError("È necessario specificare i giorni scadenza e allegare la lettera accompagnatoria");
    }
  }

  downloadAllegato(files) {
    this.archivioFileService.leggiFile(this.configService.getApiURL(), files.idDocumento.toString()).subscribe(res => {

      if (res) {
        saveAs(res, files.nomeFile);
      }

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
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  resetMessage() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }

}
