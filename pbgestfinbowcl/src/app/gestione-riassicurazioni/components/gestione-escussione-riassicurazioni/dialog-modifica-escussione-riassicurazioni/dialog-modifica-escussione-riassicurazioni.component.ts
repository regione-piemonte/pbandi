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
import { SchedaClienteMain } from "src/app/gestione-crediti/commons/dto/scheda-cliente-main";
import { ModGarResponseService } from "src/app/gestione-crediti/services/modgar-response-service.service";
import { EditDialogComponent } from "src/app/gestione-crediti/components/dialog-edit-modben/dialog-edit.component";
import { initGestioneEscussioneRiassicurazioniVO } from "src/app/gestione-riassicurazioni/commons/dto/init-gestione-escussione-riassicurazioni-VO";
import { GestioneRiassicurazioniService } from "src/app/gestione-riassicurazioni/services/gestione-riassicurazioni.service";
import { modificaEscussioneRiassicurazioniDTO } from "src/app/gestione-riassicurazioni/commons/dto/modifica-escussione-riassicurazioni-DTO";


@Component({
    selector: 'dialog-modifica-escussione-riassicurazioni',
    templateUrl: './dialog-modifica-escussione-riassicurazioni.component.html',
    styleUrls: ['./dialog-modifica-escussione-riassicurazioni.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogModificaEscussioneRiassicurazioni implements OnInit {

  // Per chiamate
  subscribers: any = {};

  // Dati
  objEscussione: initGestioneEscussioneRiassicurazioniVO = new initGestioneEscussioneRiassicurazioniVO();
  listaStatiEscussione: Array<string> = [];
  listaTipiEscussione: Array<string> = []

  // Controllo stato
  isEscussioneParziale: boolean = false;
  isNuovaEscussione: boolean = false;
  mostraSezioneBanca: boolean = false;
  mostraSezioneNotifica: boolean = false;
  importoDebitoResuduo: number = -1;

  // Messaggi
  showError: boolean = false;
  errorMsg: string = "";
  message: string;
  errore: boolean;

  // Per data max
  today = new Date();


  schedaCliente: SchedaClienteMain = new SchedaClienteMain;
  
  
  isLoading = false;
  masterSpinnerIsSpinning: boolean = false;

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

  

  isTooMuchImporto: boolean = false;
  impRichiestoError: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private userService: UserService,
    private escussioniService: ModGarResponseService,
    private riassicurazioniService: GestioneRiassicurazioniService,
    @Inject(MAT_DIALOG_DATA) public data: {
      dati: initGestioneEscussioneRiassicurazioniVO,
      isEscussioneParziale: boolean,
      isNuovaEscussione: boolean,
      importoDebitoResuduo: number
    },
  ) {
    this.objEscussione = this.data.dati;
    if(data.isNuovaEscussione) {
      this.listaStatiEscussione = this.objEscussione.statiNuovaEscussione;
    } else {
      this.listaStatiEscussione = this.objEscussione.statiEscussione;
    }
    this.listaTipiEscussione = this.objEscussione.tipiEscussione;

    if(data.isEscussioneParziale) {
      this.isEscussioneParziale = true;
      if(data.isNuovaEscussione) { // Se l'escussione è parziale e voglio crearne una nuova, lo setto pragmaticamente
        this.objEscussione.escussione_idStatoEscussione = null;
        this.objEscussione.escussione_statoEscussione = null;
      }
    }


    if(data.importoDebitoResuduo && data.importoDebitoResuduo > 0) { // Se il debito residuo è valorizzato correttamente, lo uso per un controllo [vedi salva()]
      this.importoDebitoResuduo = data.importoDebitoResuduo;
    }

    /*if(this.objEscussione.escussione_idStatoEscussione == null) {
      //console.log("1")
      this.stepEscussione = 1
    } else {
      //console.log("2")
      this.stepEscussione = this.objEscussione.escussione_idStatoEscussione;
    }*/
    
  }


  ngOnInit(): void {

    

    /*this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

        if (data) {
            this.user = data;
            this.idUtente = this.user.idUtente;
            this.codUtente = this.user.codFisc;

            this.route.queryParams.subscribe(params => {
                this.idProgetto = params.idProgetto;
            });

        }
    });*/

    /*this.schedaCliente = this.data.schedaCliente;
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
    this.inizializzaStep();*/

    this.initEscussione()
  }

    /*(){
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
    }*/

    getBancaSuggestion(value: string) {
      //this.formEscussione.get('descBanca').setValue(null);
      //this.formEscussione.get('idBanca').setValue(null);
      if (value.length >= 3) {
        this.listaBanche = ["Caricamento..."];
        this.escussioniService.getBancaSuggestion(value).subscribe(data => { if (data.length > 0) { this.listaBanche = data } else { this.listaBanche = [{ iban: "Nessuna corrispondenza" }] } })
      } else { this.listaBanche = [] }

    }

    getIbanSuggestion(value: string) {
      if (value.length >= 3) {
        this.listaIban = ["Caricamento..."];
        this.escussioniService.getIbanSuggestion(value).subscribe(data => { if (data.length > 0) { this.listaIban = data } else { this.listaIban = ["Nessuna corrispondenza"] } })
      } else { this.listaIban = [] }

    }


  initEscussione() {
    console.log("im in:", this.objEscussione.escussione_idStatoEscussione)
    
    this.titolo = "Modifica escussione";

    switch(this.objEscussione.escussione_idStatoEscussione) {

      case 1: // Ricezione escussione
        this.formEscussione = this.fb.group({
          dtRichEscussione: new FormControl({value: this.objEscussione.escussione_dataRicevimentoRichEscuss, disabled: true}), // valore precedente
          numProtocolloRich: new FormControl({value: this.objEscussione.escussione_numeroProtocolloRichiesta, disabled: true}), // valore precedente
          descTipoEscussione: new FormControl({value: this.objEscussione.escussione_tipoEscussione, disabled: true}), // valore precedente
          descStatoEscussione: new FormControl('', Validators.required),
          dtInizioValidita: new FormControl('', Validators.required),
          dtNotifica: new FormControl({value: '', disabled: true}),
          numProtocolloNotif: new FormControl({value: '', disabled: true}),
          impRichiesto: new FormControl({value: this.objEscussione.escussione_importoRichiesto, disabled: true}), // valore precedente
          impApprovato: new FormControl({value: '', disabled: true}),
          causaleBonifico: new FormControl({value: '', disabled: true}),
          ibanBonifico: new FormControl({value: '', disabled: true}),
          descBanca: new FormControl({value: '', disabled: true}),
          note: new FormControl(''),
        });
        break;

      case 2: // In istruttoria
        this.formEscussione = this.fb.group({
          dtRichEscussione: new FormControl({value: this.objEscussione.escussione_dataRicevimentoRichEscuss, disabled: true}), // valore precedente
          numProtocolloRich: new FormControl({value: this.objEscussione.escussione_numeroProtocolloRichiesta, disabled: true}), // valore precedente
          descTipoEscussione: new FormControl({value: this.objEscussione.escussione_tipoEscussione, disabled: true}), // valore precedente
          descStatoEscussione: new FormControl('', Validators.required),
          dtInizioValidita: new FormControl('', Validators.required),
          dtNotifica: new FormControl({value: '', disabled: true}),
          numProtocolloNotif: new FormControl({value: '', disabled: true}),
          impRichiesto: new FormControl({value: this.objEscussione.escussione_importoRichiesto, disabled: true}), // valore precedente
          impApprovato: new FormControl({value: '', disabled: true}),
          causaleBonifico: new FormControl({value: '', disabled: true}),
          ibanBonifico: new FormControl({value: '', disabled: true}),
          descBanca: new FormControl({value: '', disabled: true}),
          note: new FormControl(''),
        });
        break;

      case 3: // Richiesta integrazione
        this.formEscussione = this.fb.group({
          dtRichEscussione: new FormControl({value: this.objEscussione.escussione_dataRicevimentoRichEscuss, disabled: true}), // valore precedente
          numProtocolloRich: new FormControl({value: this.objEscussione.escussione_numeroProtocolloRichiesta, disabled: true}), // valore precedente
          descTipoEscussione: new FormControl({value: this.objEscussione.escussione_tipoEscussione, disabled: true}), // valore precedente
          descStatoEscussione: new FormControl('', Validators.required),
          dtInizioValidita: new FormControl('', Validators.required),
          dtNotifica: new FormControl(''), // inseribile e non obbligatorio
          numProtocolloNotif: new FormControl(''), // inseribile e non obbligatorio
          impRichiesto: new FormControl({value: this.objEscussione.escussione_importoRichiesto, disabled: true}), // valore precedente
          impApprovato: new FormControl({value: '', disabled: true}),
          causaleBonifico: new FormControl({value: '', disabled: true}),
          ibanBonifico: new FormControl({value: '', disabled: true}),
          descBanca: new FormControl({value: '', disabled: true}),
          note: new FormControl(''),
        });
        this.mostraSezioneNotifica = true;
        break;

      case 4: // Ricezione integrazione
        this.formEscussione = this.fb.group({
          dtRichEscussione: new FormControl({value: this.objEscussione.escussione_dataRicevimentoRichEscuss, disabled: true}), // valore precedente
          numProtocolloRich: new FormControl({value: this.objEscussione.escussione_numeroProtocolloRichiesta, disabled: true}), // valore precedente
          descTipoEscussione: new FormControl({value: this.objEscussione.escussione_tipoEscussione, disabled: true}), // valore precedente
          descStatoEscussione: new FormControl('', Validators.required),
          dtInizioValidita: new FormControl('', Validators.required),
          dtNotifica: new FormControl({value: '', disabled: true}), // Non riporta il valore precedente e non compilabile
          numProtocolloNotif: new FormControl({value: '', disabled: true}), // Non riporta il valore precedente e non compilabile
          impRichiesto: new FormControl({value: this.objEscussione.escussione_importoRichiesto, disabled: true}), // valore precedente
          impApprovato: new FormControl({value: '', disabled: true}),
          causaleBonifico: new FormControl({value: '', disabled: true}),
          ibanBonifico: new FormControl({value: '', disabled: true}),
          descBanca: new FormControl({value: '', disabled: true}),
          note: new FormControl(''),
        });
        break;

      case 5: // Esito positivo
        this.formEscussione = this.fb.group({
          dtRichEscussione: new FormControl({value: this.objEscussione.escussione_dataRicevimentoRichEscuss, disabled: true}), // valore precedente
          numProtocolloRich: new FormControl({value: this.objEscussione.escussione_numeroProtocolloRichiesta, disabled: true}), // valore precedente
          descTipoEscussione: new FormControl({value: this.objEscussione.escussione_tipoEscussione, disabled: true}), // valore precedente
          descStatoEscussione: new FormControl({value: this.objEscussione.escussione_statoEscussione}, Validators.required), // valore precedente
          dtInizioValidita: new FormControl({value: this.objEscussione.escussione_dataStatoEscussione, disabled: true}), // valore precedente e non modificabile
          dtNotifica: new FormControl(''), // inseribile ma non obbligatorio
          numProtocolloNotif: new FormControl(''), // inseribile ma non obbligatorio
          impRichiesto: new FormControl({value: this.objEscussione.escussione_importoRichiesto, disabled: true}), // valore precedente
          impApprovato: new FormControl('', Validators.required),
          causaleBonifico: new FormControl('', Validators.required),
          ibanBonifico: new FormControl('', Validators.required),
          descBanca: new FormControl('', Validators.required),
          note: new FormControl(''),
        });
        if(this.objEscussione.escussione_importoApprovato && this.objEscussione.escussione_importoApprovato != 0) {
          this.formEscussione.get('impApprovato')?.setValue(this.objEscussione.escussione_importoApprovato);
          this.formEscussione.get('impApprovato').disable();
        }
        this.formEscussione.get('descStatoEscussione')?.setValue(this.objEscussione.escussione_statoEscussione);
        this.mostraSezioneNotifica = true;
        this.mostraSezioneBanca = true;
        break;

      case 6: // Esito negativo
        this.formEscussione = this.fb.group({
          dtRichEscussione: new FormControl({value: this.objEscussione.escussione_dataRicevimentoRichEscuss, disabled: true}), // valore precedente
          numProtocolloRich: new FormControl({value: this.objEscussione.escussione_numeroProtocolloRichiesta, disabled: true}), // valore precedente
          descTipoEscussione: new FormControl({value: this.objEscussione.escussione_tipoEscussione, disabled: true}), // valore precedente
          descStatoEscussione: new FormControl({value: this.objEscussione.escussione_statoEscussione}, Validators.required), // valore precedente
          dtInizioValidita: new FormControl({value: this.objEscussione.escussione_dataStatoEscussione, disabled: true}), // valore precedente e non modificabile
          dtNotifica: new FormControl(''), // inseribile ma non obbligatorio
          numProtocolloNotif: new FormControl(''), // inseribile ma non obbligatorio
          impRichiesto: new FormControl({value: this.objEscussione.escussione_importoRichiesto, disabled: true}), // valore precedente
          impApprovato: new FormControl(''),
          causaleBonifico: new FormControl(''),
          ibanBonifico: new FormControl(''),
          descBanca: new FormControl(''),
          note: new FormControl(''),
        });
        if(this.objEscussione.escussione_importoApprovato && this.objEscussione.escussione_importoApprovato != 0) {
          this.formEscussione.get('impApprovato')?.setValue(this.objEscussione.escussione_importoApprovato);
          this.formEscussione.get('impApprovato').disable();
        }
        this.formEscussione.get('descStatoEscussione')?.setValue(this.objEscussione.escussione_statoEscussione);
        this.mostraSezioneNotifica = true;
        this.mostraSezioneBanca = true;
        break;

      default: // Nessuna
        this.formEscussione = this.fb.group({
          dtRichEscussione: new FormControl('', Validators.required),
          numProtocolloRich: new FormControl('', Validators.required),
          descTipoEscussione: new FormControl('', Validators.required),
          descStatoEscussione: new FormControl('', Validators.required),
          dtInizioValidita: new FormControl('', Validators.required),
          dtNotifica: new FormControl({value: '', disabled: true}),
          numProtocolloNotif: new FormControl({value: '', disabled: true}),
          impRichiesto: new FormControl('', Validators.required),
          impApprovato: new FormControl({value: '', disabled: true}),
          causaleBonifico: new FormControl({value: '', disabled: true}),
          ibanBonifico: new FormControl({value: '', disabled: true}),
          descBanca: new FormControl({value: '', disabled: true}),
          note: new FormControl(''),
        });
        this.isNuovaEscussione = true;
        break;
    }

    

    // Casomai servissero
    //this.formEscussione.get('descStatoEscussione')?.setValue(this.objEscussione.escussione_statoEscussione);
    //this.formEscussione.get('numProtocolloRich').setValidators(Validators.required);
    //this.formEscussione.get('numProtocolloRich').disable();
  }

    
  salva() {

    this.resetMessages();

    if(!this.formEscussione.valid) {
      this.showMessageError("Compilare prima i campi obbligatori.");
      return;
    }
    
    let datiForm = this.formEscussione.getRawValue();
    console.log("datiForm: ", datiForm);

    if(this.formEscussione.get('impRichiesto').enabled) { // Effettuo il controllo sull'importo richiesto solo se posso inserirlo
      // Controllo di non aver inserito un importo richiesto maggiore del debito residuo
      if(this.importoDebitoResuduo != -1 && datiForm.impRichiesto != null && datiForm.impRichiesto != "" && datiForm.impRichiesto > this.importoDebitoResuduo) {
        this.showMessageError("L'importo richiesto non può essere maggiore del Debito Residuo (" + this.importoDebitoResuduo + ").");
        return;
      }
    }

    if(this.formEscussione.get('impApprovato').enabled) { // Effettuo il controllo sull'importo approvato solo se posso inserirlo
      if(datiForm.impApprovato != null && datiForm.impApprovato != "" && datiForm.impApprovato > datiForm.impRichiesto) {
        this.showMessageError("L'importo approvato inserito supera l'importo richiesto.");
        return;
      }
    }


    
    let nuovaEscussione: modificaEscussioneRiassicurazioniDTO = new modificaEscussioneRiassicurazioniDTO();

    nuovaEscussione.idProgetto = this.objEscussione.idProgetto;
    nuovaEscussione.idEscussioneCorrente = this.objEscussione.escussione_idEscussioneCorrente;
    nuovaEscussione.tipoEscussione = datiForm.descTipoEscussione;
    nuovaEscussione.statoEscussione = datiForm.descStatoEscussione;
    nuovaEscussione.dataRicevimentoRichEscuss = datiForm.dtRichEscussione;
    nuovaEscussione.dataStatoEscussione = datiForm.dtInizioValidita;
    nuovaEscussione.dataNotificaEscussione = datiForm.dtNotifica;
    nuovaEscussione.numeroProtocolloRichiesta = datiForm.numProtocolloRich;
    nuovaEscussione.numeroProtocolloNotifica = datiForm.numProtocolloNotif;
    nuovaEscussione.importoRichiesto = datiForm.impRichiesto;
    nuovaEscussione.importoApprovato = datiForm.impApprovato;
    nuovaEscussione.causaleBonifico = datiForm.causaleBonifico;
    nuovaEscussione.ibanBanca = datiForm.ibanBonifico;
    nuovaEscussione.denomBanca = datiForm.descBanca;
    nuovaEscussione.note = datiForm.note;
    nuovaEscussione.allegatiInseriti = this.objEscussione.allegatiInseriti;

    if(this.data.isNuovaEscussione) {
      this.isNuovaEscussione = true;
    }

    this.masterSpinnerIsSpinning = true;
    this.riassicurazioniService.modificaEscussioneRiassicurazioni(nuovaEscussione, this.isNuovaEscussione).subscribe((data) => {

      console.log(data);
      if (data.esito) {

        this.masterSpinnerIsSpinning = false;
        this.dialogRef.close(true);

      } else {
        this.showMessageError("Si è verificato un errore.");
        this.masterSpinnerIsSpinning = false;
      }
    }, (err) => {
      if (err.ok == false) {
        this.showMessageError("Si è verificato un errore.");
        this.masterSpinnerIsSpinning = false;
        this.handleExceptionService.handleNotBlockingError(err);
      }
    });


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

  showMessageError(msg: string) {
    this.resetMessages();
    this.errorMsg = msg;
    this.showError = true;
    //const element = document.querySelector('#scrollId');
    //element.scrollIntoView();
  }

  resetMessages() {
    this.errorMsg = "";
    this.showError = false;
    // this.success = false;
  }

}