/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'mat-spinner-pbandi',
  templateUrl: './mat-spinner-pbandi.component.html',
  styleUrls: ['./mat-spinner-pbandi.component.scss']
})
export class MatSpinnerPbandi implements OnInit {

  @Input() diameter: number;
  @Input() position: string
  size: number;
  left: string;
  right: string;

  constructor() {
    this.size = 200;
    this.left = "auto";
    this.right = "auto";
  }


  ngOnInit() {
    if (this.diameter)
      this.size = this.diameter;

    if (this.position === "right") {
      this.right = "0px";
      this.left = "auto";
    }
    if (this.position === "left") {
      this.right = "auto";
      this.left = "0px";
    }

  }

}