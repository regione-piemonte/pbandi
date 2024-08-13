/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Constants } from 'src/app/core/commons/util/constants';

@Component({
  selector: 'app-dati-progetto-attivita-pregresse',
  templateUrl: './dati-progetto-attivita-pregresse.component.html',
  styleUrls: ['./dati-progetto-attivita-pregresse.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DatiProgettoAttivitaPregresseComponent implements OnInit {

  idDrawer: number;
  idProgetto: number;
  idBandoLinea: number;

  // dati generali
  bando: string;
  titoloProgetto: string;
  acronimo: string;
  dataDomanda: string;
  bandoLinea: string;
  codiceProgetto: string;
  cup: string;
  dataConcessione: string;

  // dati finanziari
  ammesso: string;
  residuoAmmesso: string;
  cofinanziamentoPubblico: string;
  contributo: string;
  impegno: string;
  rendicontato: string;
  quietanziato: string;
  validato: string;
  contributoErogato: string;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idDrawer = +params['id'];
    });

    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
    });

    // dati generali
    this.bando = "SISTEMA FLUVIALE DEL PO - DIFESA DEL SUOLO";
    this.titoloProgetto = "ADEGUAMENTO IDRAULICO PONTE DI TRINO, SCOGLIERA A VALLE E STABILIZZAZIONE PENDIO A MONTE - PROGETTAZIONE DEFINITIVA"
    this.acronimo = " ";
    this.dataDomanda = "01/01/2007";
    this.bandoLinea = "PAR-FSC ASSE II.2.2211 DIFESA DEL SUOLO";
    this.codiceProgetto = "DB0800_PAR-FAS_2012_PONTE_TRINO";
    this.cup = "D59J13000130003";
    this.dataConcessione = "29/11/2012";

    // dati finanziari
    this.ammesso = "700.000,00";
    this.residuoAmmesso = "120.971,88";
    this.cofinanziamentoPubblico = "700.000,00";
    this.contributo = "700.000,00";
    this.impegno = "700.000,00";
    this.rendicontato = "579.028,12";
    this.quietanziato = "579.028,12";
    this.validato = "579.028,12";
    this.contributoErogato = "537.360,68";
  }

  tornaAlleRendicontazioni() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE + "/rendicontazione/" +
      (this.activatedRoute.snapshot.paramMap.get('integrativa') ? "integrativa/" : "") + this.idProgetto + "/" + this.idBandoLinea]);
  }

  aggiorna() {
    alert("Click su aggiorna");
  }

  scarica() {
    alert('Click su scarica');
  }
}
