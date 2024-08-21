/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FlatTreeControl } from '@angular/cdk/tree';
import { Component, Inject, OnInit, AfterViewInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * Food data with nested structure.
 * Each node has a name and an optional list of children.
 */
interface FoodNode {
  name: string;
  children?: FoodNode[];
}

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

@Component({
  selector: 'app-sposta-dialog',
  templateUrl: './sposta-dialog.component.html',
  styleUrls: ['./sposta-dialog.component.scss']
})
export class SpostaDialogComponent {

  private _transformer = (node: FoodNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  }

  cartellaSelezionata = "";

  treeControl = new FlatTreeControl<ExampleFlatNode>(
    node => node.level, node => node.expandable);

  treeFlattener = new MatTreeFlattener(
    this._transformer, node => node.level, node => node.expandable, node => node.children);

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor(
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<SpostaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  ottieniNomeFolderDaStringa(testo: string) {
    return testo.split(".*.")[0];
  }

  ottieniIdFolderDaStringa(testo: string) {
    return Number(testo.split(".*.")[1]);
  }

  ngOnInit() {
    this.dataSource.data = this.data;
  }

  sposta() {
    if (this.radioSelected == 0) {
      this.openSnackBar("Selezionere la cartella di destinazione")
    } else {
      this.dialogRef.close({ data: this.radioSelected });
    }
  }

  annulla() {
    this.dialogRef.close({ data: 0 });
  }

  stateRadio = false;
  radioSelected = 0;

  public clickRadio(event: Event, testo: string) {
    event.preventDefault();
    var idFolder = this.ottieniIdFolderDaStringa(testo);
    this.radioSelected = idFolder;
    this.stateRadio = true;
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }
}
