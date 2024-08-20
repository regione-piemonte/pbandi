/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { ActivatedRoute } from "@angular/router";
import { HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { UserService } from "src/app/core/services/user.service";
import { SchedaClienteMain } from "../../commons/dto/scheda-cliente-main";
import { ModGarResponseService } from "../../services/modgar-response-service.service";
import { EditDialogComponent } from "../dialog-edit-modben/dialog-edit.component";
import { SaveEscussioneGaranzia } from "../../commons/dto/save-escussione-garanzia.all";

@Component({
    selector: 'dialog-insert-escussione',
    templateUrl: './dialog-insert-escussione.component.html',
    styleUrls: ['./dialog-insert-escussione.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogInsertEscussione implements OnInit {
    
    @ViewChild(MatPaginator) paginator: MatPaginator;
    schedaCliente: SchedaClienteMain = new SchedaClienteMain;
    subscribers: any = {};
    showError: boolean = false;
    errorMsg: string ;
    isLoading = false;
    public formEscussione: FormGroup;
    idCurrentRecord: string;
    idComponentToShow: number;
    idSoggetto: string;
    idUtente: any;
    user: UserInfoSec;
    idProgetto: any;
    codUtente: string;

    stepEscussione: any;
    azione: number;
    titolo;
    message: string;
    errore: boolean;
    infoStatiEscussione: any;
    infoTipiEscussione: any;
    idTipoEscussione: any;
    esito: any;
    infoEscussione: any;
    selected: string;
    selezionato: any;
    esitoInviato: any;
    listaBanche;
    listaIban: Array<string> = [];
    idSoggProgBancaBen: any;
    idBanca: any;
    progrSoggettoProgetto: any;
  banca: any;

  listaAllegatiPresenti: Array<number> = new Array<number>();

  today = new Date();

  isTooMuchImporto: boolean = false;
  impRichiestoError: boolean = false;

    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private handleExceptionService: HandleExceptionService,
        public dialogRef: MatDialogRef<EditDialogComponent>,
        private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private escussioniService: ModGarResponseService,
    ) { 
    }

    ngOnInit(): void {

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.codUtente = this.user.codFisc;

                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.idProgetto;
                });

            }
        });

        this.schedaCliente = this.data.schedaCliente;
        this.stepEscussione = this.data.stepEscussione;
        if(this.stepEscussione == null){
            this.stepEscussione = 0;
        }
        this.idTipoEscussione = this.data.idTipoEscussione;
        this.infoEscussione = this.data.infoEscussione;
        this.esitoInviato = this.data.esitoInviato;
        this.selezionato = this.infoEscussione.statoEscussione;
        this.idSoggProgBancaBen = this.infoEscussione.idSoggProgBancaBen;
        this.idBanca = this.infoEscussione.idBanca;
        this.progrSoggettoProgetto = this.infoEscussione.progrSoggettoProgetto;

        this.listaAllegatiPresenti = this.data.listaAllegatiPresenti
        
        this.initDialogEscussione()
        this.inizializzaStep();
    }

    initDialogEscussione(){
        this.escussioniService.initDialogEscussione(this.stepEscussione).subscribe(
            (data) => {
                this.infoStatiEscussione = data.statiEscussione;
                this.infoTipiEscussione = data.tipiEscussione;
                console.log(this.infoStatiEscussione);
                console.log(this.infoTipiEscussione);
                  },
                  (err) => {
                    }
       );
    }

    getBancaSuggestion(value: string) {
      this.formEscussione.get('descBanca').setValue(null);
      this.formEscussione.get('idBanca').setValue(null);
      if (value.length >= 3) {
        this.listaBanche = ["Caricamento..."];
        this.escussioniService.getBancaSuggestion(value).subscribe(data => { if (data.length > 0) { this.listaBanche = data } else { this.listaBanche = [{iban : "Nessuna corrispondenza"}] } })
      } else { this.listaBanche = [] }
  
    }

    getIbanSuggestion(value: string) {
      if (value.length >= 3) {
        this.listaIban = ["Caricamento..."];
        this.escussioniService.getIbanSuggestion(value).subscribe(data => { if (data.length > 0) { this.listaIban = data } else { this.listaIban = ["Nessuna corrispondenza"] } })
      } else { this.listaIban = [] }
  
    }

    

    inizializzaStep(){
        if(this.idTipoEscussione == 3 && this.stepEscussione == 5 && this.esitoInviato == true ){
            this.titolo = "Inserisci nuova escussione";
            console.log("I'm in A")
            this.formEscussione = this.fb.group({
                dtRichEscussione: new FormControl([Validators.required]),
                numProtocolloRich: new FormControl(),
                numProtocolloNotif: new FormControl(),
                descTipoEscussione: new FormControl([Validators.required]),
                descStatoEscussione: new FormControl([Validators.required]),
                dtInizioValidita: new FormControl([Validators.required]),
                dtNotifica: new FormControl(),
                impRichiesto: new FormControl('', [Validators.required]),
                impApprovato: new FormControl(),
                causaleBonifico: new FormControl(),
                ibanBonifico: new FormControl(),
                descBanca: new FormControl(),
                note: new FormControl(),
                idProgetto : this.idProgetto,
                //idEscussione :this.infoEscussione.idEscussione,
                progrSoggettoProgetto: this.progrSoggettoProgetto,
                idBanca: this.idBanca,
                idSoggProgBancaBen: this.idSoggProgBancaBen,
                //listaAllegatiPresenti: this.listaAllegatiPresenti
            });
        }

        else if(this.idTipoEscussione == 3 && this.stepEscussione == 5 && this.esitoInviato == false ){
          this.titolo = "Modifica escussione";
          this.formEscussione = this.fb.group({
              dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione,[Validators.required]),
              numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
              numProtocolloNotif: new FormControl(''),//this.infoEscussione.numProtoNotifica
              descTipoEscussione: new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
              descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
              dtInizioValidita: new FormControl('', [Validators.required]), // this.infoEscussione.dataStato
              dtNotifica: new FormControl(''), // this.infoEscussione.dataNotifica
              impRichiesto: new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
              impApprovato: new FormControl(''),// this.infoEscussione.importoApprovato
              causaleBonifico: new FormControl(''), // this.infoEscussione.causaleBonifico
              ibanBonifico: new FormControl(''),//this.infoEscussione.ibanBancaBenef
              descBanca: new FormControl(''), // this.infoEscussione.denominazioneBanca
              note: new FormControl(''), // this.infoEscussione.note
              idProgetto : this.idProgetto,
              idEscussione :this.infoEscussione.idEscussione,
              progrSoggettoProgetto: this.progrSoggettoProgetto,
              idBanca: this.idBanca,
              idSoggProgBancaBen: this.idSoggProgBancaBen,
              //listaAllegatiPresenti: this.listaAllegatiPresenti
          });
        }
        else {
        switch (this.stepEscussione) {
            case  0:
              console.log("I'm in B")
                this.titolo = "Inserisci nuova escussione";
                this.formEscussione = this.fb.group({
                    dtRichEscussione: new FormControl('',[Validators.required]), // this.infoEscussione.dataRicevimentoEscussione
                    numProtocolloRich: new FormControl(''),// this.infoEscussione.numProtoRichiesta
                    numProtocolloNotif: new FormControl(''),// this.infoEscussione.numProtoNotifica
                    descTipoEscussione: new FormControl('', [Validators.required]),// this.infoEscussione.tipoEscussione
                    descStatoEscussione: new FormControl('', [Validators.required]),// this.infoEscussione.statoEscussione
                    dtInizioValidita: new FormControl('', [Validators.required]),// this.infoEscussione.dataStato
                    dtNotifica: new FormControl(''),// this.infoEscussione.dataNotifica
                    impRichiesto: new FormControl('', [Validators.required]), // this.infoEscussione.importoRichiesto
                    impApprovato: new FormControl(''), // this.infoEscussione.importoApprovato
                    causaleBonifico: new FormControl(''), // this.infoEscussione.causaleBonifico
                    ibanBonifico: new FormControl(''), // this.infoEscussione.ibanBancaBenef
                    descBanca: new FormControl(''), // this.infoEscussione.denominazioneBanca
                    note: new FormControl(''), // this.infoEscussione.note
                    idProgetto : this.idProgetto,
                    idEscussione :this.infoEscussione.idEscussione,
                    progrSoggettoProgetto: this.progrSoggettoProgetto,
                    idBanca: this.idBanca,
                    idSoggProgBancaBen: this.idSoggProgBancaBen,
                    //listaAllegatiPresenti: this.listaAllegatiPresenti
                });

                //non compilabili data notifica importo approvato causale bonifico iban denominazione banca 
              break;
            case  1:
                this.titolo = "Modifica escussione";
                this.formEscussione = this.fb.group({
                    dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione,[Validators.required]),
                    numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
                    numProtocolloNotif: new FormControl(''),//this.infoEscussione.numProtoNotifica
                    descTipoEscussione: new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
                    descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                    dtInizioValidita: new FormControl('', [Validators.required]), // this.infoEscussione.dataStato
                    dtNotifica: new FormControl(''), // this.infoEscussione.dataNotifica
                    impRichiesto: new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
                    impApprovato: new FormControl(''), // this.infoEscussione.importoApprovato
                    causaleBonifico: new FormControl(''),//this.infoEscussione.causaleBonifico 
                    ibanBonifico: new FormControl(''), // this.infoEscussione.ibanBancaBenef
                    descBanca: new FormControl(''), // this.infoEscussione.denominazioneBanca
                    note: new FormControl(''),// this.infoEscussione.note
                    idProgetto : this.idProgetto,
                    idEscussione :this.infoEscussione.idEscussione,
                    progrSoggettoProgetto: this.progrSoggettoProgetto,
                    idBanca: this.idBanca,
                    idSoggProgBancaBen: this.idSoggProgBancaBen,
                    //listaAllegatiPresenti: this.listaAllegatiPresenti
            });

                //non compilabili data notifica importo approvato causale bonifico iban denominazione banca 
              break;

            case  2:
                this.titolo = "Modifica escussione";
                this.formEscussione = this.fb.group({
                    dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione,[Validators.required]),
                    numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
                    numProtocolloNotif: new FormControl(''), // this.infoEscussione.numProtoNotifica
                    descTipoEscussione: new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
                    descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                    dtInizioValidita: new FormControl('', [Validators.required]), // this.infoEscussione.dataStato
                    dtNotifica: new FormControl(''), // this.infoEscussione.dataNotifica
                    impRichiesto: new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
                    impApprovato: new FormControl(''), // this.infoEscussione.importoApprovato
                    causaleBonifico: new FormControl(''), // this.infoEscussione.causaleBonifico
                    ibanBonifico: new FormControl(''), // this.infoEscussione.ibanBancaBenef
                    descBanca: new FormControl(''), // this.infoEscussione.denominazioneBanca
                    note: new FormControl(''), // this.infoEscussione.note
                    idProgetto : this.idProgetto,
                    idEscussione :this.infoEscussione.idEscussione,
                    progrSoggettoProgetto: this.progrSoggettoProgetto,
                    idBanca: this.idBanca,
                    idSoggProgBancaBen: this.idSoggProgBancaBen,
                    //listaAllegatiPresenti: this.listaAllegatiPresenti
                });
                //non compilabili data notifica importo approvato causale bonifico iban denominazione banca 
              break;

            case  3:
                this.titolo = "Modifica escussione";
                this.formEscussione = this.fb.group({
                    dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione,[Validators.required]),
                    numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
                    numProtocolloNotif: new FormControl(''),// this.infoEscussione.numProtoNotifica
                    descTipoEscussione: new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
                    descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                    dtInizioValidita: new FormControl('', [Validators.required]), // this.infoEscussione.dataStato
                    dtNotifica: new FormControl(''), // this.infoEscussione.dataNotifica
                    impRichiesto: new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
                    impApprovato: new FormControl(''), // this.infoEscussione.importoApprovato
                    causaleBonifico: new FormControl(''), // this.infoEscussione.causaleBonifico
                    ibanBonifico: new FormControl(''),//this.infoEscussione.ibanBancaBenef
                    descBanca: new FormControl(''),//this.infoEscussione.denominazioneBanca 
                    note: new FormControl(''), //this.infoEscussione.note 
                    idProgetto : this.idProgetto,
                    idEscussione :this.infoEscussione.idEscussione,
                    progrSoggettoProgetto: this.progrSoggettoProgetto,
                    idBanca: this.idBanca,
                    idSoggProgBancaBen: this.idSoggProgBancaBen,
                    //listaAllegatiPresenti: this.listaAllegatiPresenti
                });
                //non compilabili importo approvato causale bonifico iban denominazione banca 
              break;

            case  4:
                this.titolo = "Modifica escussione";
                this.formEscussione = this.fb.group({
                    dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione,[Validators.required]),
                    numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
                    numProtocolloNotif: new FormControl(''),// this.infoEscussione.numProtoNotifica
                    descTipoEscussione: new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
                    descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                    dtInizioValidita: new FormControl('', [Validators.required]), // this.infoEscussione.dataStato
                    dtNotifica: new FormControl(''), // this.infoEscussione.dataNotifica
                    impRichiesto: new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
                    impApprovato: new FormControl(''), // this.infoEscussione.importoApprovato
                    causaleBonifico: new FormControl(''), // this.infoEscussione.causaleBonifico
                    ibanBonifico: new FormControl(''),// this.infoEscussione.ibanBancaBenef
                    descBanca: new FormControl(''), // this.infoEscussione.denominazioneBanca
                    note: new FormControl(''), // this.infoEscussione.note
                    idProgetto : this.idProgetto,
                    idEscussione :this.infoEscussione.idEscussione,
                    progrSoggettoProgetto: this.progrSoggettoProgetto,
                    idBanca: this.idBanca,
                    idSoggProgBancaBen: this.idSoggProgBancaBen,
                    //listaAllegatiPresenti: this.listaAllegatiPresenti
                });
                //non compilabili data notifica importo approvato causale bonifico iban denominazione banca
              break;

            case  5:
                this.titolo = "Modifica escussione";
                this.formEscussione = this.fb.group({
                    dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione,[Validators.required]),
                    numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
                    numProtocolloNotif: new FormControl(''), // this.infoEscussione.numProtoNotifica
                    descTipoEscussione: new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
                    descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                    dtInizioValidita: new FormControl('', [Validators.required]),// this.infoEscussione.dataStato
                    dtNotifica: new FormControl(''), // this.infoEscussione.dataNotifica
                    impRichiesto: new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
                    impApprovato: new FormControl(''), // this.infoEscussione.importoApprovato
                    causaleBonifico: new FormControl(''), // this.infoEscussione.causaleBonifico
                    ibanBonifico: new FormControl(''),//this.infoEscussione.ibanBancaBenef 
                    descBanca: new FormControl(''), // this.infoEscussione.denominazioneBanca
                    note: new FormControl(''), // this.infoEscussione.note
                    idProgetto : this.idProgetto,
                    idEscussione :this.infoEscussione.idEscussione,
                    progrSoggettoProgetto: this.progrSoggettoProgetto,
                    idBanca: this.idBanca,
                    idSoggProgBancaBen: this.idSoggProgBancaBen,
                    //listaAllegatiPresenti: this.listaAllegatiPresenti
                });
                //tutto compilabile
              break;

            case  6:
                this.titolo = "Modifica escussione";
                this.formEscussione = this.fb.group({
                    dtRichEscussione: new FormControl(this.infoEscussione.dataRicevimentoEscussione, [Validators.required]),
                    numProtocolloRich: new FormControl(this.infoEscussione.numProtoRichiesta),
                    numProtocolloNotif: new FormControl(''), // this.infoEscussione.numProtoNotifica
                    descTipoEscussione: new FormControl(this.infoEscussione.tipoEscussione, [Validators.required]),
                    descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                    dtInizioValidita: new FormControl('', [Validators.required]),// this.infoEscussione.dataStato
                    dtNotifica: new FormControl(''), // this.infoEscussione.dataNotifica
                    impRichiesto: new FormControl(this.infoEscussione.importoRichiesto, [Validators.required]),
                    impApprovato: new FormControl(''), // this.infoEscussione.importoApprovato
                    causaleBonifico: new FormControl(''), // this.infoEscussione.causaleBonifico
                    ibanBonifico: new FormControl(''), // this.infoEscussione.ibanBancaBenef
                    descBanca: new FormControl(''), // this.infoEscussione.denominazioneBanca
                    note: new FormControl(''), // this.infoEscussione.note
                    idProgetto : this.idProgetto,
                    idEscussione :this.infoEscussione.idEscussione,
                    progrSoggettoProgetto: this.progrSoggettoProgetto,
                    idBanca: this.idBanca,
                    idSoggProgBancaBen: this.idSoggProgBancaBen,
                    //listaAllegatiPresenti: this.listaAllegatiPresenti
                });
                //non compilabili importo approvato causale bonifico iban denominazione banca
              break;

           

            default:
              break;
          } 

        }

      /*if (this.titolo != "Inserisci nuova escussione") {
        this.fb.control('impRichiesto').disable();
      }*/
    }

    onConfirmClick(): void {
    this.isTooMuchImporto = false;
    this.impRichiestoError = false;
    if(this.formEscussione.valid) {

      if (this.data.new) {
        sessionStorage.setItem('impApprovato', JSON.stringify(0))
      }
      
      console.log(this.formEscussione.value);
      let datiBackend = this.formEscussione.value;

      // Controllo importo approvato
      if(datiBackend.impRichiesto != null && datiBackend.impRichiesto > 10000) {
        this.impRichiestoError = true;
        return;
      }
      
      

      if(datiBackend.impApprovato != null && datiBackend.impApprovato != undefined && datiBackend.impApprovato != 0) {

        let importoPrev: number = Number(sessionStorage.getItem('impApprovato'));
        console.log("importoPrev", importoPrev);
        let newImporto: number = 0;

        if(importoPrev != null && !Number.isNaN(importoPrev)) {
          console.log("FORMimpApprovato", datiBackend.impApprovato);

          if((+importoPrev + +datiBackend.impApprovato) > 10000) {
            this.isTooMuchImporto = true;
            return
          } else {
            newImporto = +importoPrev + +datiBackend.impApprovato;
            console.log("newImporto", newImporto);
            sessionStorage.setItem('impApprovato', JSON.stringify(newImporto));
          }

          
        } else {
          sessionStorage.setItem('impApprovato', JSON.stringify(datiBackend.impApprovato));
        }

        
      }

      if (this.data.new) {
        this.escussioniService.insertEscussione(datiBackend).subscribe(
          (data) => {
            console.log(data);
            if (data.esito == true) {
              this.message = data.messaggio;
              this.errore = false;

              setTimeout(() => {
                this.errore = null;
                this.dialogRef.close({
                  idEscussione: data.id,
                });
              }, 2000);
            }
            else if (data.esito === false) {
              this.errore = true;
              this.message = "Non è stato possibile inserire l'escussione";
            }
          },
          (err) => {
            if (err.ok == false) {
              this.errore = true;
              this.message = err.statusText;
            }
          }
        );
        return
      }

            
            if(this.stepEscussione == 0 || this.idTipoEscussione == 3 && this.esito == true){ // this.idTipoEscussione == 3 && this.stepEscussione == 5
                this.escussioniService.insertEscussione(datiBackend).subscribe(
                    (data) => {
                        console.log(data);
                        if (data.esito  == true) {
                          this.message = data.messaggio;
                          this.errore = false;
                          
                          setTimeout(() => {
                            this.errore = null;
                            this.dialogRef.close({
                                idEscussione: data.id,
                            });
                          }, 2000); 
                        }
                        else if (data.esito === false) {
                          this.errore = true;
                          this.message = "Non è stato possibile inserire l'escussione";
                        }
                          },
                          (err) => {
                            if(err.ok == false){
                              this.errore = true;
                              this.message = err.statusText;
                            }
                            }
                          );
            }
            else{
                this.escussioniService.updateEscussione(datiBackend, this.listaAllegatiPresenti).subscribe(
                    (data) => {
                        console.log(data);
                        if (data.esito  == true) {
                          this.message = data.messaggio;
                          this.errore = false;
                          
                          setTimeout(() => {
                            this.errore = null;
                            this.dialogRef.close({
                                idEscussione: data.id,
                            });
                          }, 2000); 
                        }
                        else if (data.esito === false) {
                          this.errore = true;
                          this.message = "Non è stato possibile modificare l'escussione";
                        }
                          },
                          (err) => {
                            if(err.ok == false){
                              this.errore = true;
                              this.message = err.statusText;
                            }
                            }
                          );
            }
             
        } else {
            this.showError = true;
            this.errorMsg = "Compila tutti i campi obbligatori!";
        }  

    }

    closeDialog() {
        this.dialogRef.close();
    }

    getBanca(banca,idBanca) {
      if(this.formEscussione.get('ibanBonifico').value == "Nessuna corrispondenza"){
        this.formEscussione.get('ibanBonifico').setValue(null);
      }
      this.formEscussione.get('descBanca').setValue(banca);
      this.formEscussione.get('idBanca').setValue(idBanca);
    }

}