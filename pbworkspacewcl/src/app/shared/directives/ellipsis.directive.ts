/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewInit, Directive, HostListener } from '@angular/core';

@Directive({
  selector: '[appEllipsis]'
})
export class EllipsisDirective implements AfterViewInit {

  constructor() { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.showHide();
    }, 1000);
  }

  @HostListener('click', ['$event']) onClick($event) {
    setTimeout(() => {
      this.showHide();
    }, 10);
  }

  showHide() {
    const leggiTuttoElement = document.getElementById('leggiTutto');
    let ellipsisElement = document.getElementById('ellipsisP');
    if (ellipsisElement) {
      if (ellipsisElement.offsetHeight < ellipsisElement.scrollHeight) {
        if (leggiTuttoElement) {
          leggiTuttoElement.hidden = false;
        }
      } else {
        if (leggiTuttoElement) {
          leggiTuttoElement.hidden = true;
        }
      }
    }
  }

}
