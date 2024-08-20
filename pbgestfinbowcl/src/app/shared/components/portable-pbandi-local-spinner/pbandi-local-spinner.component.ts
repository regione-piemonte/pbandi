/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Input } from '@angular/core';

/**
 * Un altro nuovo approccio al classico spinner di Pbandi ma meno radicale.
 * Può essere usato all'interno di un componente il cui caricamento non necessiti
 * l'interruzione dell'attività dell'utente, come il caricamento di uno storico.
 * 
 * E' possibile settare la dimensione ed un sottotesto da far visualizzare all'utente durante il caricamento.
 * 
 * Per la versione classica che oscura l'intera pagina e blocca tutte le azioni, riferirsi a PbandiMasterSpinner
*/

@Component({
  selector: 'pbandi-local-spinner',
  templateUrl: './pbandi-local-spinner.component.html',
  styleUrls: ['./pbandi-local-spinner.component.scss']
})

export class PbandiLocalSpinner implements OnInit {

  @Input() diameter: number;
  @Input() subText: string;

  size: number;
  defaultSize: number = 160
  text: string = "";

  constructor() {}

  ngOnInit() {

    if(this.diameter) {
      this.size = this.diameter;
    } else {
      this.size = this.defaultSize;
    }

    if(this.subText) {
      this.text = this.subText;
    }

  }

}