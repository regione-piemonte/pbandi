/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Location } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-torna-indietro',
  template: `
    <div class="backContainer">
      <button (click)="goBack()" mat-button>
       <mat-icon class="blue-icon">keyboard_backspace</mat-icon>
       <span class="backText"> {{ backText }} </span>
      </button>
    </div>
  `
})
export class TornaIndietroComponent implements OnInit {

  @Input() backText: string;

  constructor(private location: Location) { }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

}
