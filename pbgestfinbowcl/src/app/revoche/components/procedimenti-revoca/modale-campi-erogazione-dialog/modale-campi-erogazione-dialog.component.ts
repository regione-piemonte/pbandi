/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Inject, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Constants, HandleExceptionService } from '@pbandi/common-lib';
import { DocumentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/documento-revoca-vo';
import { ProcedimentiRevocaResponseService } from 'src/app/revoche/services/procedimenti-revoca-response.service';

@Component({
  selector: 'app-modale-campi-erogazione-dialog',
  templateUrl: './modale-campi-erogazione-dialog.component.html',
  styleUrls: ['./modale-campi-erogazione-dialog.component.scss']
})
export class ModaleCampiErogazioneDialogComponent implements OnInit {
  @ViewChild('uploadFile') myFile: ElementRef;

  //PARAMETRI
  isLoading: boolean = false;
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;
  myForm: FormGroup;

  erroreAggiuntaAllegati: boolean = false;    //se si presenta un qualsiasi errore durante il caricamento dei documenti, questa variabile diventa TRUE e non viene eseguito avviaProcedimentoRevoca


  //documenti
  altriAllegati: DocumentoRevocaVO[] = null;
  newAltriAllegati: Array<File> = new Array<File>();

  constructor(
    public dialogRef: MatDialogRef<ModaleCampiErogazioneDialogComponent>,
    private procedimentiRevocaResponseService: ProcedimentiRevocaResponseService,
    private handleExceptionService: HandleExceptionService,
    private fb: FormBuilder,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.isLoading = true;

    //creo form
    this.myForm = this.fb.group({
      numeroDichiarazioneSpesa: new FormControl({ disabled: true, value: this.data?.numeroDichiarazioneSpesa }, [Validators.required]),
      impDaErogareContributo: new FormControl({ disabled: true, value: this.data?.impDaErogareContributo }, [Validators.required]),
      causaleErogazioneContributo: new FormControl({ disabled: true, value: this.data?.causaleErogazioneContributo }, [Validators.required]),
      impDaErogareFinanziamento: new FormControl({ disabled: true, value: this.data?.impDaErogareFinanziamento }, [Validators.required]),
      causaleErogazioneFinanziamento: new FormControl({ disabled: true, value: this.data?.causaleErogazioneFinanziamento }, [Validators.required]),
      ires: new FormControl({ disabled: true, value: this.data?.impIres }, [Validators.required]),
      giorniScadenza: new FormControl("", [Validators.required]),
    });

    //recupero i documenti
    this.getDocumenti();
  }

  getDocumenti() {
    this.procedimentiRevocaResponseService.getDocumenti(this.data.numeroProcedimentoRevoca == undefined ? "" : this.data.numeroProcedimentoRevoca).subscribe(data => {
      this.altriAllegati = data.filter(x => x.idTipoDocumento == '50');
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

  inviaIncaricoAdErogazione() {
    if(this.myForm.get("giorniScadenza").value && this.myForm.get("numeroDichiarazioneSpesa").value) {
      this.salvaAltriAllegati();
      //se l'aggiunta degli allegati è andata a buon fine effettuo la chiamata
      if (this.erroreAggiuntaAllegati == false) {
        this.procedimentiRevocaResponseService.inviaIncaricoAErogazione(
          this.data?.numeroProcedimentoRevoca,
          this.myForm.get("numeroDichiarazioneSpesa").value?.toString(),
          this.myForm.get("impDaErogareContributo").value?.toString(),
          this.myForm.get("impDaErogareFinanziamento").value?.toString(),
          this.myForm.get("ires").value?.toString(),
          this.myForm.get("giorniScadenza").value?.toString()
        ).subscribe(data => {
          console.log(data);
          if (data.code == "OK") {
  
            console.log(data.message);
  
            //se l'avvia procedimento ha successo, si ritorna alla pagina di ricerca
            this.tornaAllaRicerca();
          } else if (data.code == "KO") {
            this.showMessageError(data.message);
            this.handleExceptionService.handleNotBlockingError(data.message);
          }
        }, err => {
          this.showMessageError(err);
          this.handleExceptionService.handleNotBlockingError(err);
        })
      }
    }else {
      if(this.myForm.get("giorniScadenza").value) {
        this.showMessageError("Numero della dichiarazione di spesa mancante!");
      }else {
        this.myForm.get("giorniScadenza").markAsTouched();
      }
    }
  }

  salvaDocumenti() {
    this.salvaAltriAllegati();
    this.close();
  }

  /*************************
   ***** GESTIONE FILE *****
   *************************/

   /*CR173
  handleFileInputLetteraAccompagnatoria(files: Array<File>) {
    //viene considerato SOLO IL PRIMO FILE selezionato
    this.lettAccompagnatoria = files[0];

    console.log("LETTERA ACCOMPAGNATORIA CARICATA", this.lettAccompagnatoria);

  }
  eliminaLetteraAccompagnatoria() {
    this.lettAccompagnatoria = null;
  }
  */

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
