/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Input } from '@angular/core';

/**
 * Un nuovo approccio al classico spinner di Pbandi presente da tempi immemori.
 * 
 * Oscura l'intera pagina e non permette all'utente di eseguire azioni dirante un processo importante (come un salvataggio).
 * E' possibile settare la dimensione ed un sottotesto da far visualizzare all'utente durante il caricamento.
 * 
 * Per la versione da usare all'interno dei componenti (e che non blocchi l'azioni dell'utente), fare riferimento a PbandiLocalSpinner
*/

@Component({
  selector: 'pbandi-master-spinner',
  templateUrl: './pbandi-master-spinner.component.html',
  styleUrls: ['./pbandi-master-spinner.component.scss']
})

export class PbandiMasterSpinner implements OnInit {

  @Input() diameter: number;
  @Input() subText: string;

  size: number;
  defaultSize: number = 200
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