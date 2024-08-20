/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { throwToolbarMixedModesError } from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { HandleExceptionService } from '@pbandi/common-lib';
import { Constants } from 'src/app/core/commons/util/constants';
import { AgevolazioneSuggestVO } from '../../commons/agevolazione-suggest-vo';
import { BandoSuggestVO } from '../../commons/bando-suggest-vo';
import { DistintaVO } from '../../commons/distinta-vo';
import { DistinteResponseService } from '../../services/distinte-response.service';
import { AnimationMetadataType } from '@angular/animations';

@Component({
  selector: 'app-caricamento-distinte',
  templateUrl: './caricamento-distinte.component.html',
  styleUrls: ['./caricamento-distinte.component.scss']
})
export class CaricamentoDistinteComponent implements OnInit {

  spinner: boolean = false;
  error: boolean = false;
  success: boolean = false;
  messageError: string;

  myForm: FormGroup;

  suggBando: BandoSuggestVO[] = [];
  suggAgevolazione: AgevolazioneSuggestVO[] = [];
  suggDistinta: DistintaVO[] = [];

  bandoInserito: boolean = false;


  constructor(
    private fb: FormBuilder,
    private resService: DistinteResponseService,
    private handleExceptionService: HandleExceptionService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      bando: new FormControl(""),
      agevolazione: new FormControl(""),    //disable
      //this.myForm.controls['agevolazione'].enable()
      distinta: new FormControl(""),
      flagEscussione: new FormControl(false)
    });
  }

  //Metodo che serve per l'autocomplete
  displayBando(element: BandoSuggestVO): string {
    return element && element.titoloBando ? element.titoloBando : '';
  }

  displayAgevolazione(element: AgevolazioneSuggestVO): string {
    return element && element.descModalitaAgevolazione ? element.descModalitaAgevolazione : '';
  }

  displayDistinta(element: DistintaVO): string {
    return element && element.idDistinta ? element.idDistinta : '';
  }

  changeCheckboxFinistr(name: string, event: any) {

    console.log("evento: "+ event.checked);
    console.log("naae: "+ name);
  }

  sugg(id: number, value: string) {

    this.removeMessage();

    if (id == 1) {

      if (value.length >= 3) {

        this.suggBando = [];

        this.suggBando[0] = {
          idBando: "",
          titoloBando: "Caricamento..."
        };

        this.resService.suggestBando(value).subscribe(data => {
          if (data.length > 0) {

            console.table(data);
            this.suggBando = data;

          } else {

            this.suggBando[0] =
            {
              idBando: "",
              titoloBando: "Nessuna corrispondenza"
            }

          }
        })
      } else {
        this.suggBando = []
      }

    } else if (id == 2) {

      let formControls = this.myForm.getRawValue();

      /*
      if (formControls.bando.titoloBando == undefined || formControls.bando.titoloBando == "Nessuna corrispondenza" || formControls.bando.titoloBando.length < 3) {

        this.showMessageError("Per poter inserire un'agevolazione è necessario selezionare prima un bando");

      }
      */

      // if (value.length >= 3) {

      this.suggAgevolazione = [];

      this.suggAgevolazione[0] = {
        idModalitaAgevolazione: "",
        descModalitaAgevolazione: "Caricamento...",
        descBreveModalitaAgevolazione: "Caricamento..."
      };

      this.resService.suggestAgevolazione(value).subscribe(data => {
        if (data.length > 0) {
          this.suggAgevolazione = data
        } else {

          this.suggAgevolazione[0] = {
            idModalitaAgevolazione: "",
            descModalitaAgevolazione: "Nessuna corrispondenza",
            descBreveModalitaAgevolazione: "Nessuna corrispondenza"
          };
        }
      });

      // } else { this.suggAgevolazione = [] }

    } else if (id == 3) {

      // if (value.length >= 1) {

      this.suggDistinta = [];

      this.suggDistinta[0] = {
        idDistinta: "Caricamento...",
        descrizioneDistinta: "Caricamento...",
        descrizioneModalitaAgevolazione: "Caricamento..."
      };

      this.resService.suggestDistintaRespinta(value == "" ? "-1" : value).subscribe(data => {

        if (data.length > 0) {

          this.suggDistinta = data

        } else {

          this.suggDistinta[0] = {
            idDistinta: "Nessuna corrispondenza",
            descrizioneDistinta: "Nessuna corrispondenza",
            descrizioneModalitaAgevolazione: "Nessuna corrispondenza"
          }

        }
      })
      // } else {
      //   this.suggDistinta = []
      // }
    }
  }

  creaDistinta() {

    this.removeMessage();

    let formControls = this.myForm.getRawValue();

    if (formControls.bando.titoloBando == undefined || formControls.agevolazione.descBreveModalitaAgevolazione == undefined) {

      this.showMessageError("Per avviare la ricerca è necessario selezionare entrambi i campi");

    } else if ((formControls.bando.titoloBando.length < 3) && (formControls.agevolazione.descBreveModalitaAgevolazione.length < 3)) {

      this.showMessageError("Inserire almeno 3 caratteri per avviare una ricerca");

    } else if ((formControls.bando.titoloBando == "Nessuna corrispondenza") || (formControls.agevolazione.descBreveModalitaAgevolazione == "Nessuna corrispondenza")) {

      this.showMessageError("Errore durante l'inserimento dei campi");

    } else {

      this.spinner = true;

      this.resService.creaDistinta(formControls.bando.idBando, formControls.agevolazione.idModalitaAgevolazione).subscribe(data => {

        console.table(data);
        this.spinner = false;

        //apro component crea-distinta
        if (data) {

          this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_CARICAMENTO_DISTINTE + "/creaDistinta"], {
            queryParams: {
              idBando: formControls.bando.idBando,
              idModalitaAgevolazione: formControls.agevolazione.idModalitaAgevolazione, 
              flagEscussione: this.myForm.get("flagEscussione").value
            }
          });

        } else {

          this.showMessageError("Errore in fase di ricerca: la ricerca nel database non ha prodotto risultati validi.");

        }

      }, err => {

        this.spinner = false;
        this.showMessageError("Errore in fase di ricerca.");
        this.handleExceptionService.handleNotBlockingError(err);

      })

    }
  }

  copiaDistinta() {

    this.removeMessage();

    //momentaneo
    // this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_CARICAMENTO_DISTINTE + "/copiaDistinta"], {})

    let formControls = this.myForm.getRawValue();

    if (formControls.distinta?.idDistinta.length < 1) {

      this.showMessageError("Inserire almeno 1 carattere per avviare una ricerca");

    } else {

      this.spinner = true;

      this.resService.copiaDistinta(formControls.distinta.idDistinta).subscribe(data => {

        console.log("data COPIA DISTINTA", data);
        this.spinner = false;

        if (data) {

          this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_CARICAMENTO_DISTINTE + "/copiaDistinta"], {
            queryParams: {
              idDistinta: formControls.distinta.idDistinta
            }
          })

        } else {

          this.showMessageError("Errore in fase di ricerca: la ricerca nel database non ha prodotto risultati validi.");

        }

      }, err => {

        this.spinner = false;
        this.showMessageError("Errore in fase di ricerca.");
        this.handleExceptionService.handleNotBlockingError(err);

      })
    }
  }

  removeMessage() {
    this.error = false;
  }

  showMessageError(mex: string) {
    this.error = true;
    this.messageError = mex;
  }

}
