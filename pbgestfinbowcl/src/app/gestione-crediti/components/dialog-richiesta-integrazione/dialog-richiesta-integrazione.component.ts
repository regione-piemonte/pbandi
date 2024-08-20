/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, Inject, OnInit, ViewChild} from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Constants, UserInfoSec } from "@pbandi/common-lib";
import { ModGarResponseService } from "../../services/modgar-response-service.service";
import { ActivatedRoute } from "@angular/router";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { UserService } from "src/app/core/services/user.service";

@Component({
    selector: 'dialog-richiesta-integrazione',
    templateUrl: 'dialog-richiesta-integrazione.component.html',
    styleUrls: ['dialog-richiesta-integrazione.component.scss'],
})


export class DialogRichiestaIntegrazione implements OnInit {
    @ViewChild('uploadFileEscussione')myFileEscussione: ElementRef;

    subscribers: any = {};
    idOp = Constants.ID_OPERAZIONE_GESTIONE_GARANZIE;
    showError: boolean = false;
    showSucc: boolean = false;
    errorMsg: string;
    isLoading;
    
    idSoggetto: string;
    idUtente: any;
    user: UserInfoSec;
    idProgetto: any;
    codUtente: string;
    idTarget: number;
    idEscussione: number;
    nomeFile: string;
    file: File;
    allegati = [];
    public myForm: FormGroup;
    messageAllegato: string;
    erroreAllegato: boolean;
    messageError: string;
    error: boolean;
    allegatiInseriti: boolean = false;

    constructor(
       
        private escussioneService: ModGarResponseService,
        private fb: FormBuilder,
        private route: ActivatedRoute,
        public dialogRef: MatDialogRef<DialogRichiestaIntegrazione>,
        private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: any,
    ) { 
        this.myForm = this.fb.group({
         //   motivazione: new FormControl('', [Validators.required]),
            giorniScadenza: new FormControl('', [Validators.required]),
        });
    }

    ngOnInit(): void {

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.codUtente = this.user.codFisc;
                this.idEscussione = this.data.idEscussione,
                console.log(this.idEscussione);
                
                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.idProgetto;
                });

            }

        });


    }

    closeDialog() {
        this.dialogRef.close();
    }


    

   
    /* downloadAllegato(files: AllegatoVO) {

        this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), files.idDocIndex.toString()).subscribe(res => {

            if (res) {
                saveAs(res, files.nomeFile);
            }

        });

    } */

    


    handleFileEscussione(files: Array<File>) {
        console.log(files[0]);
        this.resetMessage();
        if (!files[0].name.endsWith(".pdf") && !files[0].name.endsWith(".PDF") && !files[0].name.endsWith(".DOC") && !files[0].name.endsWith(".doc") && !files[0].name.endsWith(".zip") && !files[0].name.endsWith(".ZIP")) {
          this.showMessageError("Il file deve avere estensione .pdf oppure .doc oppure .zip");
        } 
        else {
         // this.allegati.push(files[0]);
         this.allegati.push(files[0]);
          this.allegatiInseriti = true;
        }
       
    }

    
    /* eliminaFileIntegrazione(i) {
        this.myFileEscussione.nativeElement.value = '';
        this.allegati = null;
        this.allegatiInseriti = false;
    } */

    eliminaFileIntegrazioneLocale(i) {
      //  this.allegati.splice(i, 1);
        this.allegati = [];
        this.allegatiInseriti = false;
        this.clearInputElement();
    }

    clearInputElement() {
        this.myFileEscussione.nativeElement.value = ''
      }


    inviaIntegrazione() {
        this.showError = false;
        /* if(this.myForm.invalid){
            this.errorMsg = "è necessario inserire una motivazione prima di procedere"
            this.showError=true;
            return;

        }else  */if(this.allegati.length<=0)
        {
            this.errorMsg = "è necessario inserire una lettera accompagnatoria prima di procedere"
            this.showError = true;
            return;

        }
        
        else{

        this.showError=false;
    //    let motivazione = this.myForm.get('motivazione').value;
        let giorniScadenza = this.myForm.get('giorniScadenza').value;
            this.escussioneService.inserisciAllegatoIntegrazione(this.allegati,this.idProgetto,giorniScadenza, this.idEscussione).subscribe(data => {

                if (data.esito  == true) {
                    this.messageAllegato = "Integrazione inviata correttamente";
                    this.erroreAllegato = false;
                    setTimeout(() => {
                      this.erroreAllegato = null;
                      this.allegati = null;
                      this.closeDialog();
                    }, 2000); 
                  }
                  else if (data.esito === false) {
                    this.erroreAllegato = true;
                    this.messageAllegato = "Non è stato possibile inviare la richiesta d'integrazione";
                  }
            }) 
        }
    }

    

    showMessageError(errorMsg: string) {
        this.resetMessage();
        this.messageError = errorMsg;
        this.error = true;
    }

    resetMessage() {
        this.messageError = null;
        this.error = false;
        // this.success = false;
    }

}