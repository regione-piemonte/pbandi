/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
//import * as EventEmitter from 'events';
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";

@Component({
  selector: 'header-labels-buttons',
  templateUrl: './header-labels-buttons.component.html',
  styleUrls: ['./header-labels-buttons.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderLabelsButtonsComponent implements OnInit {

  @Input() labels: CodiceDescrizione[] = [];
  @Input() buttons: CodiceDescrizione[] = [];
  @Input() class: string;
  @Output() onBtnClick = new EventEmitter<CodiceDescrizione>();

  constructor() {
  }

  ngOnInit() {
  }

  onButtonsBtnClick(btn: CodiceDescrizione) {
    this.onBtnClick.emit(btn);
  }

}