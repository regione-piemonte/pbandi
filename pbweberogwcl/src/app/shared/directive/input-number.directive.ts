/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/**
 * *.component.ts
 * inputNumberType: string = 'float';
 * fieldImportoErogazioneEffettivo: number;
 * 
 * *.component.html
 * <input #importoEE [(inputNumber)]="inputNumberType" [(ngModel)]="fieldImportoErogazioneEffettivo" />
 * <!-- (ngModelChange)="elaborateNgModelValue($event)" (change)="elaborateInputValue($event)" -->
 * <br>{{ fieldImportoErogazioneEffettivo | json }}
 * <br>{{ importoEE?.value | json }}
 * <br>{{ inputNumberType | json }}
 */
import { Directive, OnInit, AfterViewInit, HostListener, Input, Output, forwardRef, ElementRef, EventEmitter } from '@angular/core';
//import { NgModel } from '@angular/common';
//import { NgControl } from '@angular/forms';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { SharedService } from 'src/app/shared/services/shared.service';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputNumberDirective),
  multi: true
};

@Directive({
  selector: '[inputNumber]',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
  // host: {,
  //   '(keydown)': 'onKeyDown($event)',
  //   '(blur)': 'onInputChange($event)',
  //   '(input)': 'onInputChange($event)'
  // }
})
export class InputNumberDirective implements OnInit, AfterViewInit, ControlValueAccessor {

  private debug: boolean = false;
  private navigationKeys = [
    'Backspace',
    'Delete',
    'Tab',
    'Escape',
    'Enter',
    'Home',
    'End',
    'ArrowLeft',
    'ArrowRight',
    'Clear',
    'Copy',
    'Paste'
  ];
  el: HTMLInputElement;
  private innerValue: any = 0;
  // Placeholders for the callbacks which are later providesd
  // by the Control Value Accessor
  private onTouchedCallback: () => void = () => {};
  private onChangeCallback: (a: any) => void = () => {};

  constructor(
    private elRef: ElementRef,
    //public model: NgModel
    //private ngControl: NgControl,
    private sharedService: SharedService,
  ) {
    if (this.debug) this.debugLog('constructor');
    this.el = elRef.nativeElement;
  }

  @Input("inputNumber") inputNumberType: string;  // selector: '[inputNumber]' 
  @Output("inputNumberChange") inputNumberTypeChange: EventEmitter<string> = new EventEmitter();  // selector: '[InputNumber]'
  //@Input() inputNumberType: string;
  //@Output() inputNumberTypeEvent:EventEmitter<string> = new EventEmitter();
  //@Input() inputNumberFormatted: string;
  //@Output() inputNumberFormattedChange:EventEmitter<string> = new EventEmitter();
  //@Output() ngModelChange: EventEmitter<any> = new EventEmitter(false);
  //this.ngModelChange.emit(this._el.nativeElement.value));

  // set getter
  get value(): any {
    if (this.debug) this.debugLog('get');
    if (this.debug) console.log('input - get value - this.innerValue', this.innerValue);
    if (this.debug) console.log('input - get value - this.el.value', this.el.value);
    return this.innerValue;
  }

  // set accessor including call the onchange callback
  set value(v: any) {
    if (this.debug) this.debugLog('set');
    if (this.debug) console.log('input - set value - value', v);
    if (this.debug) console.log('input - set value - this.el.value', this.el.value);
    if (v !== this.innerValue) {
      this.innerValue = v;
      this.onChangeCallback(v);
    }
  }

  // From ControlValueAccessor interface
  writeValue(value: any) {
    if (this.debug) this.debugLog('writeValue');
    if (value !== this.innerValue) {
      if (this.debug) console.log('input - writeValue - value', value, typeof(value));
      if (this.debug) console.log('input - writeValue - this.innerValue', this.innerValue);
      if (this.debug) console.log('input - writeValue - this.el.value', this.el.value);
      //this.el.value = this.currencyMaskService.transform(value);
      this.el.value = this.sharedService.formatValue(value);
      //let elValue = this.sharedService.formatValue(value);
      //value = this.sharedService.getNumberFromFormattedString(elValue);
      this.onChangeCallback(this.sharedService.getNumberFromFormattedString(this.el.value));
      this.innerValue = value;
    }
  }

  // From ControlValueAccessor interface
  registerOnChange(fn: any) {
    if (this.debug) this.debugLog('registerOnChange');
    this.onChangeCallback = fn;
  }

  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    if (this.debug) this.debugLog('registerOnTouched');
    this.onTouchedCallback = fn;
  }

  ngAfterViewInit() {
    if (this.debug) this.debugLog('ngAfterViewInit');
    //this.el.style.textAlign = 'right';
    //this.el.classList.add('text-right');
  }

  // On Focus remove all non-digit or decimal separator values
  @HostListener('focus', ['$event.target.value'])
  onfocus(value) {
    if (this.debug) this.debugLog('onfocus');
    if (this.debug) console.log('input - onfocus - value', value);
    if (this.debug) console.log('input - onfocus - this.el.value', this.el.value);
    //this.el.value = this.currencyMaskService.parse(value);
    value = this.sharedService.getNumberFromFormattedString(value);
    this.el.value = this.sharedService.formatValue(value);
    //this.el.value = value;
  }

  // On Blue remove all symbols except last . and set to currency format
  @HostListener('blur', ['$event.target.value'])
  onBlur(value) {
    if (this.debug) this.debugLog('onBlur');
    if (this.debug) console.log('input - onBlur - value', value);
    if (this.debug) console.log('input - onBlur - this.el.value', this.el.value);
    this.onTouchedCallback();
    //this.el.value = this.currencyMaskService.transform(value);
    //this.onChangeCallback(this.currencyMaskService.parse(this.el.value));
    value = this.sharedService.getNumberFromFormattedString(value);
    this.el.value = this.sharedService.formatValue(value);
    this.onChangeCallback(this.sharedService.getNumberFromFormattedString(this.el.value));
  }

  // On Change remove all symbols except last . and set to currency format
  @HostListener('change', ['$event.target.value'])
  onChange(value) {
    if (this.debug) this.debugLog('onChange');
    if (this.debug) console.log('input - onChange - value', value);
    if (this.debug) console.log('input - onChange - this.el.value', this.el.value);
    //this.el.value = this.currencyMaskService.transform(value);
    //this.onChangeCallback(this.currencyMaskService.parse(this.sharedService.getNumberFromFormattedString(this.el.value)));
    value = this.sharedService.getNumberFromFormattedString(value);
    this.el.value = this.sharedService.formatValue(value);
    this.onChangeCallback(this.sharedService.getNumberFromFormattedString(this.el.value));
  }

  // Prevent user to enter anything but digits and decimal separator
  @HostListener('keypress', ['$event'])
  onKeyPress(event) {
    if (this.debug) this.debugLog('onKeyPress');
    let value = event.target.value;
    if (this.debug) console.log('input - onKeyPress - value', value);
    if (this.debug) console.log('input - onKeyPress - this.el.value', this.el.value);
    const key = event.which || event.keyCode || 0;
    if ((this.inputNumberType == 'float' && key !== 44) && key !== 46 && key > 31 && (key < 48 || key > 57)) { // 44: ','  // 46: '.'
      event.preventDefault();
    }
  }

  // -----------------------------------------------------------

  ngOnInit() {
    //const ctrl = this.ngControl.control;
    //ctrl.valueChanges
      //.pipe(map(v => (v || '').slice(0, this.limit)))
    //  .subscribe(v => ctrl.setValue(v, { emitEvent: false }));
  }

  @HostListener('input', ['$event'])
  onInputChange(event) {
    if (this.debug) this.debugLog('onInputChange');
    let value = event.target.value;
    if (this.debug) console.log('input - onInputChange - value', value);
    if (this.debug) console.log('input - onInputChange - this.el.value', this.el.value);
    let newValue = event.target.value;
    //this.model.valueAccessor.writeValue(newValue);
  }

  // -----------------------------------------------------------

  @HostListener('keydown', ['$event'])
  onKeyDown(e: any) {  // KeyboardEvent
    if (this.debug) this.debugLog('onKeyDown');
    //let e = <KeyboardEvent>event;
    //let key = e.key || String.fromCharCode(e.keyCode);
    let value = e.target.value;
    if (this.debug) console.log('input - onKeyDown - value', value);
    if (this.debug) console.log('input - onKeyDown - this.el.value', this.el.value);
    if (this.debug) {
      if (e.key === 'i') { this.inputNumberType = ''; }
      if (e.key === 'f') { this.inputNumberType = 'float'; }
      console.log('input - inputNumberType - emit', this.inputNumberType);
      this.inputNumberTypeChange.emit(this.inputNumberType);
    }
    if (
      this.navigationKeys.indexOf(e.key) > -1 || // Allow: navigation keys: backspace, delete, arrows etc.
      (e.key === 'a' && e.ctrlKey === true) || // Allow: Ctrl+A
      (e.key === 'c' && e.ctrlKey === true) || // Allow: Ctrl+C
      (e.key === 'v' && e.ctrlKey === true) || // Allow: Ctrl+V
      (e.key === 'x' && e.ctrlKey === true) || // Allow: Ctrl+X
      (e.key === 'a' && e.metaKey === true) || // Allow: Cmd+A (Mac)
      (e.key === 'c' && e.metaKey === true) || // Allow: Cmd+C (Mac)
      (e.key === 'v' && e.metaKey === true) || // Allow: Cmd+V (Mac)
      (e.key === 'x' && e.metaKey === true) // Allow: Cmd+X (Mac)
    ) {
      // let it happen, don't do anything
      return;
    }
    if (
      this.inputNumberType == 'float' || 
      (e.key === ',') || 
      (e.key === '.')
    ) {
        return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
      e.preventDefault();
    }
  }

  @HostListener('paste', ['$event'])
  onPaste(event: ClipboardEvent) {
    if (this.debug) this.debugLog('onPaste');
    event.preventDefault();
    let regex: RegExp = ((this.inputNumberType == 'float')?/\\B(?=(?:\\d{3})+(?!\\d))/g:/\D/g)
    const pastedInput: string = event.clipboardData
      .getData('text/plain')
      .replace(regex, ''); // get a digit-only string
    document.execCommand('insertText', false, pastedInput);
  }

  @HostListener('drop', ['$event'])
  onDrop(event: DragEvent) {
    if (this.debug) this.debugLog('onDrop');
    event.preventDefault();
    const textData = event.dataTransfer.getData('text').replace(/\D/g, '');
    this.el.focus();
    document.execCommand('insertText', false, textData);
  }

  // -----------------------------------------------------------

  debugLog(fname) {
    console.log('input - debug - ' + fname, ((this.el)?this.el.tagName:undefined), ((this.el)?this.el.getAttribute('name'):undefined), ((this.el && !this.el.getAttribute('name'))?this.el.attributes:undefined));
  }
}