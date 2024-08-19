/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Directive, HostListener, OnInit, Renderer2, ElementRef, AfterViewInit, Input } from '@angular/core';
import { MatSelect } from '@angular/material/select';

@Directive({
  selector: 'mat-select'
})
export class CustomOptionalMatSelectDirective {

  @Input() matOptionFirstChildUndefined;
  @Input() matOptionFirstChildNull;
  @Input() matOptionFirstChildEmpty;

  enabledToAll: boolean = false;

  constructor(
    private matSelect: MatSelect,
    //private elementRef: ElementRef,
    private renderer: Renderer2
  ) { }

  @HostListener('openedChange', ['$event'])
  onOpenedChange( isOpened : boolean)
  {
    if (isOpened && (this.enabledToAll || this.matOptionFirstChildUndefined !== undefined || this.matOptionFirstChildNull !== undefined || this.matOptionFirstChildEmpty !== undefined))  // || this.matSelect.required
    {
      const matOption = this.renderer.createElement('mat-option');
      this.renderer.setAttribute(matOption, 'id', 'mat-option-first-child');
      this.renderer.addClass(matOption, 'mat-option');
      this.renderer.listen(matOption,'click', () =>
      {
        let ret: any;
        if (this.matOptionFirstChildUndefined !== undefined) {
          ret = undefined;
        } else if (this.matOptionFirstChildNull !== undefined) {
          ret = null;
        } else if (this.matOptionFirstChildEmpty !== undefined) {
          ret = '';
        } else {
          ret = undefined;
        }
        this.matSelect.value = ret;
        this.matSelect.ngControl.viewToModelUpdate(ret);
        this.matSelect.close();
      });
  
      const panel = document.querySelector('.mat-select-panel');
      if (!panel)
      {
        throw new Error('Cannot find mat select panel');
      }
      this.renderer.insertBefore(panel, matOption, panel.firstChild);
    }
  }

}