/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { ProposteRevocaResponseService } from '../../../services/proposte-revoca-response.service';
import { Location } from '@angular/common';
import { DenominazioneSuggestVO } from 'src/app/revoche/commons/denominazione-suggest-vo';
import { UserService } from 'src/app/core/services/user.service';
import { SuggestResponseService } from 'src/app/revoche/services/suggest-response.service';
import { CausaleBloccoSuggestVO } from 'src/app/revoche/commons/proposte-revoca-dto/causale-blocco-suggest-vo';
import { AutoritaControllanteVO } from 'src/app/revoche/commons/proposte-revoca-dto/autorita-controllante-vo';

@Component({
  selector: 'app-nuova-proposta-revoca',
  templateUrl: './nuova-proposta-revoca.component.html',
  styleUrls: ['./nuova-proposta-revoca.component.scss']
})

export class NuovaPropostaRevocaComponent implements OnInit {

  messageError: string;
  messageSuccess: string;
  error: boolean = false;
  success: boolean = false;



  //variabili
  user: UserInfoSec;
  today: Date = new Date();
  myForm: FormGroup;
  spinner: boolean = false;
  numeroPropostaRevoca: number;

  altriControlli: DenominazioneSuggestVO;


  //Suggest
  suggBeneficiario: DenominazioneSuggestVO[] = [];
  suggBando: DenominazioneSuggestVO[] = [];     //BandoLineaInterventoVO
  suggProgetto: DenominazioneSuggestVO[] = [];    //ProgettoSuggestVO
  suggCausaPropRevoca: DenominazioneSuggestVO[] = [];
  suggAutoritaControllante: DenominazioneSuggestVO[] = [];


  constructor(
    private location: Location,
    public fb: FormBuilder,
    private handleExceptionService: HandleExceptionService,
    private ProposteRevocaResponseService: ProposteRevocaResponseService,
    private resService: ProposteRevocaResponseService,
    private userService : UserService,
    private suggestService: SuggestResponseService,
  ) { }

  ngOnInit(): void {

    this.resetMessageError()

    //recupero dati utente
    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
    });

    //creo form
    this.myForm = this.fb.group({
      beneficiario: new FormControl(""),
      bando: new FormControl(""),
      progetto: new FormControl("", [Validators.required]),
      causaPropRevoca: new FormControl("", [Validators.required]),
      autoritaControllante: new FormControl({disabled: true, value: ''}),
    });

    //recupero numero proposta di revoca
    this.resService.getNumeroPropostaRevoche().subscribe((data) => {
      this.numeroPropostaRevoca = data;
    }, (err) => {console.log(err)});

    //recupero la lista delle cause proposta revoca
    this.resService.getCauseProposteRevoca().subscribe((data) => {
        if(data.length > 0) {

          this.suggCausaPropRevoca = data;

          this.altriControlli = data.find(element => element.desc == "Altri controlli");

        } else {

          this.suggCausaPropRevoca = [
            {
              desc: "Nessuna corrispondenza",
              id: "",
              altro: "",
            }
          ]

        }


      }, (err) => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
    });

  }


  suggerimentoBeneficiario(value: string) {
    this.resetMessageError()

    if (value.length >= 3) {
      this.suggBeneficiario = [
        {
          desc: "Caricamento...",
          id: "",
          altro: ""
        }
      ]
      this.suggestService.suggestBeneficiario(value, "-1").subscribe(data => {
        if (data.length > 0) {
          this.suggBeneficiario = data;
          this.suggBeneficiario.sort((a, b) => a.desc.localeCompare(b.desc));
        } else {
          this.suggBeneficiario = [
            {
              desc: "Nessuna corrispondenza",
              id: "",
              altro: "",
            }
          ]

        }
      }, err => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });

    } else {
      this.suggBeneficiario = []
    }
  }

  suggerimentoBando(value: string) {

    let formControls = this.myForm.getRawValue();
    this.suggBando = [];

    if (
      (formControls.beneficiario == "undefined" || formControls.beneficiario == null || formControls.beneficiario == "")
      ) {

        this.showMessageError("Per inserire il bando è necessario aver indicato prima il beneficiario.")

    } else {

      this.suggBando = [
        {
          desc: "Caricamento...",
          id: "",
          altro: ""
        }
      ]

      this.suggestService.suggestBando(value == undefined ? "" : value, formControls.beneficiario.id).subscribe(data => {

        if (data.length > 0) {

          this.suggBando = data;
          this.suggBando.sort((a, b) => a.desc.localeCompare(b.desc));

        } else {

          this.suggBando = [
            {
              desc: "Nessuna corrispondenza",
              id: "",
              altro: ""
            }
          ]

        }
      })

    }
  }

  suggerimentoProgetto(value: string) {
    this.resetMessageError()

    //La ricerca si attiva SOLO nel caso in cui sia stato selezionato prima un beneficiario e un bando
    let formControls = this.myForm.getRawValue();

    this.suggProgetto = []

    if (
      (formControls.beneficiario == undefined || formControls.beneficiario == null || formControls.beneficiario == "") ||
      (formControls.bando == undefined || formControls.bando == null || formControls.bando == "")
      ) {

        this.showMessageError("Per inserire il progetto è necessario aver indicato prima il beneficiario e il bando.")

    } else {

      this.suggProgetto =  [
        {
          desc: "",
          id: "",
          altro: "Caricamento..."
        }
      ];

      this.suggestService.suggestProgetto(value == undefined ? "" : value, formControls.beneficiario?.id, formControls.bando?.id).subscribe(data => {
        if (data.length > 0) {

          this.suggProgetto = data;
          this.suggProgetto.sort((a, b) => a.desc.localeCompare(b.desc));

        } else {

          this.suggProgetto =  [
            {
              desc: "",
              id: "",
              altro: "Nessuna corrispondenza"
            }
          ];

        }
      })
    }
  }

  causaPropRevocaSelezionata(event: any) {

    this.resetMessageError()

    //Se viene selezionato "Altri controlli"
    if(event.value.id == this.altriControlli.id) {

      //recupero la lista dei valori Origine proposta di revoca
      this.resService.getAutoritaControllante().subscribe((data) => {
        if(data.length > 0) {

          this.suggAutoritaControllante = data;

        } else {

          this.suggAutoritaControllante = [
            {
              desc: "Nessuna corrispondenza",
              id: "",
              altro: "",
            }
          ]

        }


      }, (err) => {
        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);
      });

      //allore viene viene abilitato Autorità controllante
      this.myForm.get("autoritaControllante").enable();
      this.myForm.get("autoritaControllante").setValidators(Validators.required);
      this.myForm.get("autoritaControllante").updateValueAndValidity();

    } else {
      this.myForm.get("autoritaControllante").disable();
      this.myForm.get("autoritaControllante").setValue(null);
      this.myForm.get("autoritaControllante").clearValidators();
      this.myForm.get("autoritaControllante").updateValueAndValidity();
    }

  }


  onSubmit() {
    this.resetMessageError();

    if(this.myForm.valid) {

      let formData = new FormData();
      formData.set("numeroRevoca", this.numeroPropostaRevoca.toString());
      formData.set("idProgetto", this.myForm.get("progetto").value?.id.toString());
      formData.set("idCausaleBlocco", this.myForm.get("causaPropRevoca").value?.id.toString());
      formData.set("idAutoritaControllante", this.myForm.get("autoritaControllante").value?.id == undefined ? null : this.myForm.get("autoritaControllante").value?.id.toString());
      formData.set("dataPropostaRevoca", this.today.toString());

      let parametro = {
        "numeroRevoca" : this.numeroPropostaRevoca,
        "idProgetto": this.myForm.get("progetto").value?.id,
        "idCausaleBlocco": this.myForm.get("causaPropRevoca").value?.id,
        "idAutoritaControllante": this.myForm.get("autoritaControllante").value?.id == undefined ? null : this.myForm.get("autoritaControllante").value?.id,
        "dataPropostaRevoca": this.today
      }

      this.ProposteRevocaResponseService.nuovaPropostaRevoca(parametro).subscribe(
        (data) => {
          if (data.code == 'OK') {
            this.success = true;

            //su indicazione di Luca Rodolfi (30/01/2023)
            //quando si clicca su salva, si torna alla pagina di ricerca
            this.goBack();

          }
          else if (data.code == 'KO') {
            this.showMessageError(data.message);
          }
        },
        (err) => {
          if(err.ok == false){
            this.showMessageError(err);
          }
        }
      );
    } else {

     console.log("FORM NON VALIDO");
     this.showMessageError("Errore durante la compilazione del form!");

    }
  }

  goBack() {
    this.resetMessageError();
    this.location.back();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
    //const element = document.querySelector('#scrollId');
    //element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }

  displayBeneficiario(element: DenominazioneSuggestVO): string {
    return element && element.desc ? element.desc + ' - ' + element.altro : '';
  }
  displayBando(element: DenominazioneSuggestVO): string {
    return element && element.desc ? element.desc : '';
  }
  displayProgetto(element: DenominazioneSuggestVO): string {
    return element && element.altro ? element.desc + ' - ' + element.altro : '';
  }
  displayCausaProp(element: CausaleBloccoSuggestVO): string {
    return element && element.descCausaleBlocco ? element.descCausaleBlocco : '';
  }
  displayAutoritaControllante(element: AutoritaControllanteVO): string {
    return element && element.descAutoritaControllante ? element.descAutoritaControllante : '';
  }
}
