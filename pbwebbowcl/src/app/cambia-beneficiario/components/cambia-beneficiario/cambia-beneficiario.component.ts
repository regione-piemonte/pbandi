/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Progetto } from './../../commons/dto/progetto';
import { Component, OnInit } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Beneficiario } from 'src/app/cambia-beneficiario/commons/dto/beneficiario';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { CambiaBeneficiarioService } from '../../services/cambia-beneficiario.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { BeneficiarioDTO } from '../../commons/dto/beneficiario-dto';
import { DatiBeneficiarioProgettoDTO } from '../../commons/dto/dati-beneficiario-progetto-dto';
import { ResponseCodeMessage } from 'src/app/shared/commons/dto/response-code-message-dto';
import { MatDialog } from '@angular/material/dialog';
import { ModificaDatiBeneficiarioComponent } from '../modifica-dati-beneficiario/modifica-dati-beneficiario.component';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { ConfirmCambiaBeneficiarioDialogComponent } from '../confirm-cambia-beneficiario-dialog/confirm-cambia-beneficiario-dialog.component';


@Component({
  selector: 'app-cambia-beneficiario',
  templateUrl: './cambia-beneficiario.component.html',
  styleUrls: ['./cambia-beneficiario.component.scss']
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class CambiaBeneficiarioComponent implements OnInit {

  //##### VARIABILI ############################################

  user: UserInfoSec;  //variabile utente

  //COMMENTATO TUTTO CIO' CHE RIGUARDA LA COMBO DEI BENEFICIARI PER VELOCIZZARE LA PAGINA
  /* beneficiari: Array<Beneficiario>;
   beneficiario: Beneficiario;
   beneficiarioSelected: FormControl = new FormControl();
   beneficiarioGroup: FormGroup = new FormGroup({ beneficiarioControl: new FormControl() });
   filteredOptionsBen: Observable<Beneficiario[]>;*/

  progetti: Array<Progetto>;
  progetto: Progetto;
  progettoSelected: FormControl = new FormControl();
  progettoGroup: FormGroup = new FormGroup({ progettoControl: new FormControl() });
  filteredOptionsProg: Observable<Progetto[]>;

  //isBeneficiarioSelezionato: boolean = false;
  isProgettoSelezionato: boolean = false;
  beneficiarioDTO: BeneficiarioDTO  // oggetto beneficiario dopo la ricerca
  cfBeneficiarioSubentrante: string // codice fiscale del beneficiario subentrante
  datiBeneficiarioProgettoDTO: DatiBeneficiarioProgettoDTO // beneficiario subentrante
  responseCodeMessage: ResponseCodeMessage //messagio di risposta
  isSearchinProgress = false;

  //Per test Da rimuovere
  beneficiariRisdotta: Array<Beneficiario> = []
  progettiListRidotta: Array<Progetto> = [];


  // Variabili di errore 
  messageError: string;
  isMessageErrorVisible: boolean;
  isResultVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  //Errori form
  cfBeneficiarioSubentranteValidator: boolean = true
  cfValidations: FormGroup;

  //Loaded
  loadedOnInit: boolean = true;
  loadedRicercaBeneficiari: boolean = true;
  loadedRicercaProgetti: boolean = true;
  loadedCerca: boolean = true;
  loadedCambiaBeneficiario: boolean = true;
  loadedSalvaBeneficiario: boolean = true;
  loadedLuogo: boolean = true;

  //Object subscriber
  subscribers: any = {};


  //##### COSTRUTTORE ################################################
  constructor(
    private cambiaBeneficiarioService: CambiaBeneficiarioService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private modificaBeneficiarioDialog: MatDialog,
    private formBuilder: FormBuilder
  ) { }


  //##### FUNZIONI ##################################################

  // ON-INIT
  ngOnInit(): void {

    this.loadedOnInit = false;
    //Pre caricamento inizile dei dati
    this.cfValidations = this.formBuilder.group({
      cf: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(16)]],
      select: ['', [Validators.required]]
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      console.log(this.user)
      if (data) {

        //COMMENTI TEST
        console.log("[cambia beneficiario -- onIninit] \nRichiesta utente conclusa con successo\n" +
          "[User: \n" +
          "idU = " + this.user.idUtente.toString() + " // idSoggetto =" + this.user.idSoggetto.toString() + " // ruolo = " + this.user.codiceRuolo)
        //this.cercaBeneficiari();
        this.cercaProgetti();

      }
      this.loadedOnInit = true;
    });


  }

  // Restetta gli errori
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  // resetta gli errori e motra il messaggio di errore 
  showMessageError(msg: string) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageError = msg;
    }
    else {
      this.messageError = "Success";
    }
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  // resetta gli errori e motra il messaggio di successo
  showMessageSucces(msg: string) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageSuccess = msg;
    }
    else {
      this.messageSuccess = "Error";
    }
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  // resetta gli errori
  resetMessage() {
    this.messageError = null;
    this.messageSuccess = null;
    this.isMessageErrorVisible = false;
    this.isMessageSuccessVisible = false;
  }

  // Funzione che fa apparire e scomparire la grafica del caricamento
  isLoading() {
    if (!this.loadedOnInit || !this.loadedRicercaBeneficiari || !this.loadedRicercaProgetti || !this.loadedCerca
      || !this.loadedSalvaBeneficiario || !this.loadedCambiaBeneficiario) {
      return true;
    }
    return false;
  }

  // Funzione che chiama il servizio spirng che torna i beneficiari
  /* cercaBeneficiari() {
 
     this.resetMessageError();
     this.loadedRicercaBeneficiari = false;
 
     //Funzione cerca i beneficiari disponibili per un certo utente
     this.subscribers.ricerca = this.cambiaBeneficiarioService.cercaBeneficiari(this.user, this.progetto).subscribe(data => {
       if (data) {
         var beneficiario: Beneficiario = this.beneficiario
         this.beneficiario = undefined
         this.beneficiari = data;
         this.filteredOptionsBen = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
           .pipe(
             startWith(''),
             map(value => typeof value === 'string' || value == null ? value : value.descrizione),
             map(name => name ? this._filterBen(name) : this.beneficiari.slice())
           );
         if (this.isBeneficiarioSelezionato) {
           var i = 0
           var find = false
           while (!find && i < this.beneficiari.length) {
             if (this.beneficiari[i].id == beneficiario.id) {
               this.beneficiario = this.beneficiari[i]
               find = true
             }
             i++
           }
           if (find) {
             this.isBeneficiarioSelezionato = true;
           }
           else {
             this.isBeneficiarioSelezionato = false;
           }
         }
 
 
       }
       this.loadedRicercaBeneficiari = true;
     }, err => {
       this.handleExceptionService.handleNotBlockingError(err);
       this.showMessageError("Errore in fase di ricerca dei beneficiari.");
       this.isResultVisible = false;
       this.loadedRicercaBeneficiari = true;
     });
 
   }*/

  // funzione che chiama il servizio spirng che torna i progetti
  cercaProgetti() {

    this.resetMessageError();
    this.loadedRicercaProgetti = false;

    //Funzione cerca i beneficiari disponibili per un certo utente
    this.subscribers.ricerca = this.cambiaBeneficiarioService.cercaProgetti(this.user, null /*this.beneficiario*/).subscribe(data => {
      if (data) {

        var progetto: Progetto = this.progetto
        this.progetto = undefined
        this.progetti = data;
        this.filteredOptionsProg = this.progettoGroup.controls['progettoControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descrizione),
            map(name => name ? this._filterProg(name) : (this.progetti ? this.progetti.slice() : null))
          );
        //if(progetto != undefined){
        if (this.isProgettoSelezionato) {
          var i = 0
          var find = false
          while (!find && i < this.progetti.length) {
            if (this.progetti[i].idProgetto == progetto.idProgetto) {
              this.progetto = this.progetti[i]
              find = true
            }
            i++
          }
          if (find) {
            this.isProgettoSelezionato = true;
          }
          else {
            this.isProgettoSelezionato = false;
          }
        }


      }
      this.loadedRicercaProgetti = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca dei progetti.");
      this.isResultVisible = false;
      this.loadedRicercaProgetti = true;
    });


  }


  check(type: string) {
    setTimeout(() => {
      if (type === 'B') {
        /* if (!this.beneficiarioSelected || (this.beneficiarioGroup.controls['beneficiarioControl'] && this.beneficiarioSelected !== this.beneficiarioGroup.controls['beneficiarioControl'].value)) {
           this.beneficiarioGroup.controls['beneficiarioControl'].setValue(null);
           this.beneficiarioSelected = new FormControl();
         }*/
      } else if (type === 'P') {
        if (!this.progettoSelected || (this.progettoGroup.controls['progettoControl'] && this.progettoSelected !== this.progettoGroup.controls['progettoControl'].value)) {
          this.progettoGroup.controls['progettoControl'].setValue(null);
          this.progettoSelected = new FormControl();
        }
      }
    }, 200);
  }

  click(event: any, type: string) {
    if (type === 'B') {
      /* this.beneficiarioSelected = event.option.value;
        this.beneficiario = this.beneficiarioGroup.controls['beneficiarioControl'].value;
        this.onSelectedBeneficiario();*/
    } else if (type === 'P') {
      // this.progettoSelected.setValue(event.option.value);
      this.progettoSelected = event.option.value;
      this.progetto = this.progettoGroup.controls['progettoControl'].value;
      this.onSelectedProgetto();
    }
  }

  /*private _filterBen(descrizione: string): Beneficiario[] {
    const filterValue = descrizione.toLowerCase();
    return this.beneficiari.filter(option => option.descrizione.toLowerCase().includes(filterValue) || option.codiceFiscale.toLowerCase().includes(filterValue));

  }*/

  private _filterProg(codiceVisualizzatoProgetto: string): Progetto[] {
    const filterValue = codiceVisualizzatoProgetto.toLowerCase();
    return this.progetti.filter(option => option.codiceVisualizzatoProgetto.toLowerCase().includes(filterValue));
  }

  /*displayFnBen(beneficiario: Beneficiario): string {
    return (beneficiario && beneficiario.descrizione ? beneficiario.descrizione : '')
      + (beneficiario && beneficiario.descrizione && beneficiario.codiceFiscale ? ' - ' : '')
      + (beneficiario && beneficiario.codiceFiscale ? beneficiario.codiceFiscale : '');
  }*/

  displayFnProg(element: Progetto): string {
    return element && element.codiceVisualizzatoProgetto ? element.codiceVisualizzatoProgetto : '';
  }

  // Funzione che viene chimata quando viene scelto un beneficiario
  // Va qundi a prendere i progetti del beneficiario scelto
  /* onSelectedBeneficiario() {
        this.isSearchinProgress = false
     if (this.beneficiario != undefined) {
       this.isBeneficiarioSelezionato = true;
     }
     else {
       this.isBeneficiarioSelezionato = false;
     }
     this.cercaProgetti();
   }*/

  // Funzione che viene chimata quando viene scelto un progetto
  // Va qundi a prendere il beneficiario collegato al progetto scelto
  onSelectedProgetto() {
    this.isSearchinProgress = false
    if (this.progetto != undefined) {
      this.isProgettoSelezionato = true;
    }
    else {
      this.isProgettoSelezionato = false;
    }
    //this.cercaBeneficiari();
  }

  // Pulsante cerca invia i dati inseriti e torna i dati del beneficiario attuale
  cerca() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- cerca] \n" +
      "Avviata la ricerca:\n");

    this.resetMessageError();
    this.loadedCerca = false;
    this.isSearchinProgress = true

    //Funzione cerca i beneficiari disponibili per un certo utente
    this.subscribers.ricerca = this.cambiaBeneficiarioService.cercabenprog(this.user, null /*this.beneficiario*/, this.progetto).subscribe(data => {
      if (data) {

        this.beneficiarioDTO = data;

        //COMMENTI TEST
        console.log("[cambia beneficiario -- cerca] \nRichiesta della funzione cerca avvenuta con successo. \n")
        console.log("Tipo di ritorno è beneficiario = " + typeof BeneficiarioDTO)
        console.log("id soggetto = " + this.beneficiarioDTO.idSoggetto +
          "\nid progetto = " + this.beneficiarioDTO.idProgetto)


      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca");
      this.isResultVisible = false;
      this.loadedCerca = true;
    });

  }

  // Pulsante che ricerca il beneficiario subentrante tramite il suo cofice fiscale
  cercaBeneficiarioSubentrante() {

    /*

    if( this.cfBeneficiarioSubentrante.length != 16 &&  this.cfBeneficiarioSubentrante.length != 11 ){
      //COMMENTI TEST
      console.log("[cambia beneficiario -- cercaBeneficiarioSubentrante]")
      console.log("Errore nel campo codice fiscale beneficiario")      
      this.cfBeneficiarioSubentranteValidator = false;
      console.log("Value di cfBeneficiarioSubentranteValidator = " + this.cfBeneficiarioSubentranteValidator)
    }
    
    else{*/

    //COMMENTI TEST
    console.log("[cambia beneficiario -- cercaBeneficiarioSubentrante]")
    console.log("Ricerca avviata")

    this.cfBeneficiarioSubentranteValidator = true;
    this.resetMessageError();
    this.loadedCerca = false;

    //Funzione cerca il beneficiario utilizzando il codice fiscale
    this.subscribers.ricerca = this.cambiaBeneficiarioService.cercabensub(this.user, this.cfBeneficiarioSubentrante, this.beneficiarioDTO).subscribe(data => {
      if (data) {

        this.datiBeneficiarioProgettoDTO = data;

        //COMMENTI TEST
        console.log("[cambia beneficiario -- cerca] \nRichiesta della funzione cerca avvenuta con successo. \n")
        console.log("id soggetto = " + this.datiBeneficiarioProgettoDTO.idSoggetto)
        console.log("id progetto = " + this.datiBeneficiarioProgettoDTO.idProgetto)
        console.log("denominazione = " + this.datiBeneficiarioProgettoDTO.denominazione)

        //this.resetMessage

        if (this.datiBeneficiarioProgettoDTO == undefined || this.datiBeneficiarioProgettoDTO.idSoggetto == undefined) {

          if (this.datiBeneficiarioProgettoDTO == undefined) {
            this.showMessageError("Errore nei dati")
          }
          else {
            if (this.datiBeneficiarioProgettoDTO.message) {
              this.showMessageSucces(decodeURIComponent(JSON.parse('"' + this.datiBeneficiarioProgettoDTO.message.replace(/\\{4}/g, '\\') + '"')));
            }
          }

        }
        else {
          if (this.datiBeneficiarioProgettoDTO.message) {
            this.showMessageSucces(decodeURIComponent(JSON.parse('"' + this.datiBeneficiarioProgettoDTO.message.replace(/\\{4}/g, '\\') + '"')));
          }
        }

      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca");
      this.isResultVisible = false;
      this.loadedCerca = true;
    });
    // }

  }

  // Pulsante apre un dilaog che permette la modifica dei dati del beneficiario
  modificabeneficiarioDialog() {

    const dialogRef = this.modificaBeneficiarioDialog.open(ModificaDatiBeneficiarioComponent,
      {
        width: '600px', data: {
          dati: this.datiBeneficiarioProgettoDTO, user: this.user
        }
      });

    dialogRef.afterClosed().subscribe(result => {

      //COMMENTI TEST
      console.log("[cambia beneficiario -- modificabeneficiarioDialog]\n")
      console.log("Dialog chiuso. il risultato è: ")
      console.log(result)

      if (result != undefined) {
        var newDatiBeneficiarioProgettoDTO: DatiBeneficiarioProgettoDTO = result;
        this.datiBeneficiarioProgettoDTO = result;
        this.salvaNuovoBeneficiario();

      }


    });

  }

  // Pulsante che permettedi slavare le modifiche fatte al beneficiario
  salvaNuovoBeneficiario() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- slavaNuovoBeneficiario]\n")
    console.log("Avvio procvedura di slavataggio: ")

    this.resetMessageError();
    this.loadedSalvaBeneficiario = false;


    //Funzione cerca i beneficiari disponibili per un certo utente
    this.subscribers.ricerca = this.cambiaBeneficiarioService.salvaNuovoBeneficiario(this.user.idUtente.toString(), this.datiBeneficiarioProgettoDTO).subscribe(data => {
      if (data) {

        this.datiBeneficiarioProgettoDTO = data;

        //COMMENTI TEST
        console.log("[cambia beneficiario -- salvaNuovoBeneficiario] \nRichiesta della funzione cerca avvenuta con successo. \n")
        console.log("messaggio = " + this.datiBeneficiarioProgettoDTO.message)

      }
      this.loadedSalvaBeneficiario = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di slavataggio ");
      this.isResultVisible = false;
      this.loadedSalvaBeneficiario = true;
    });


  }

  //Pulsante che permette il cambio beneficiario
  cambiabeneficiario() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- cambiabeneficiario]")
    console.log("Richiesta cd cam modifica beneficiario avviata")

    this.resetMessageError();
    if (!this.datiBeneficiarioProgettoDTO.idSoggetto) {
      this.datiBeneficiarioProgettoDTO.codiceFiscale = this.cfBeneficiarioSubentrante;
    }
    const dialogRef = this.modificaBeneficiarioDialog.open(ConfirmCambiaBeneficiarioDialogComponent,
      {
        width: '600px', data: {
          beneficiario: this.beneficiarioDTO, beneficiarioSubentrante: this.datiBeneficiarioProgettoDTO
        }
      });

    dialogRef.afterClosed().subscribe(result => {

      //COMMENTI TEST
      console.log("[cambia beneficiario -- ConfirmCambiaBeneficiarioDialogComponent]\n")
      console.log("Dialog chiuso. il risultato è: ")
      console.log(result)

      if (result != undefined && result) {
        this.loadedCambiaBeneficiario = false;
        // copio l'idProgetto del beneficiario vecchio dentro quello nuovo, perchè il servizio lo legge da lì
        this.datiBeneficiarioProgettoDTO.idProgetto = this.beneficiarioDTO.idProgetto;
        this.subscribers.ricerca = this.cambiaBeneficiarioService.cambiabeneficiario(this.user.idUtente.toString(), this.datiBeneficiarioProgettoDTO, this.beneficiarioDTO.anagraficaAggiornabile).subscribe(data => {
          if (data) {

            this.responseCodeMessage = data;

            //COMMENTI TEST
            console.log("[cambia beneficiario -- cambiabeneficiario] \nRichiesta della funzione cerca avvenuta con successo. \n")
            console.log("messaggio = " + this.responseCodeMessage.message)
            console.log("code messange = " + this.responseCodeMessage.code)

            if (this.responseCodeMessage == undefined || this.responseCodeMessage == null || this.responseCodeMessage.code == undefined) {
              this.showMessageError("Error")
            }
            else if (this.responseCodeMessage.code == "OK" && this.responseCodeMessage.message) {
              this.showMessageSucces(decodeURIComponent(JSON.parse('"' + this.responseCodeMessage.message.replace(/\\{4}/g, '\\') + '"')));
            }
            else if (this.responseCodeMessage.code == "KO" && this.responseCodeMessage.message) {
              this.showMessageError(decodeURIComponent(JSON.parse('"' + this.responseCodeMessage.message.replace(/\\{4}/g, '\\') + '"')))
            }

          }
          this.cleanPage()
          this.loadedCambiaBeneficiario = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di cambiamento di beneficiario");
          this.isResultVisible = false;
          this.loadedCambiaBeneficiario = true;
        });


      }


    });




  }

  cfValidator(form: FormGroup) {
    const condition = form.get('cf')
  }

  //questa funziona riazzera la pagina chiudendo le operazioni aperte 
  cleanPage() {
    this.cleanCardBeneficiario()
    /*  this.beneficiario = undefined
      this.onSelectedBeneficiario()*/
  }

  //questa funziona riazzera la le card di ricerca aperte
  cleanCardBeneficiario() {
    this.cfBeneficiarioSubentrante = undefined
    this.datiBeneficiarioProgettoDTO = undefined
    this.isSearchinProgress = false
  }

}








