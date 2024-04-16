/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { WidgetSettingsDialogComponent } from '../widget-settings-dialog/widget-settings-dialog.component';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { DashboardService } from '../../services/dashboard.service';
import { WidgetDTO } from '../../commons/dto/widget-dto';
import { BandoWidgetDTO } from '../../commons/dto/bando-widget-dto';

@Component({
  selector: 'app-dashboard-settings',
  templateUrl: './dashboard-settings.component.html',
  styleUrls: ['./dashboard-settings.component.scss']
})
export class DashboardSettingsComponent implements OnInit {

  user: UserInfoSec;
  widgets: Array<WidgetDTO>;
  bandiDaAssociare: Array<BandoWidgetDTO>;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedWidgets: boolean;
  loadedBandiDaAssociare: boolean;
  loadedChangeFlagAttivo: boolean = true;

  //SUSBCRIBERS
  subscribers: any = {};
  constructor(
    private userService: UserService,
    private dashboardService: DashboardService,
    private handleExceptionService: HandleExceptionService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.loadedWidgets = false;
        this.dashboardService.getWidgets().subscribe(res => {
          this.widgets = res;
          this.loadedWidgets = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica delle impostazione salvate.");
          this.loadedWidgets = true;
        });
        this.loadedBandiDaAssociare = false;
        this.dashboardService.getBandiDaAssociare().subscribe(res => {
          this.bandiDaAssociare = res;
          this.loadedBandiDaAssociare = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica dei bandi da associare.");
          this.loadedBandiDaAssociare = true;
        });
      }
    });
  }

  changeToggleWidget(widget: WidgetDTO, event: MatSlideToggleChange) {
    this.loadedChangeFlagAttivo = false;
    this.dashboardService.changeWidgetAttivo(widget.idWidget, event.checked).subscribe(data => {
      if (data?.esito) {
        widget.flagWidgetAttivo = event.checked;
        if (widget.flagWidgetAttivo && widget.flagModifica) {
          this.impostazioniWidget(widget);
        }
      } else {
        this.showMessageError("Errore in fase di salvataggio della modifica.");
      }
      this.loadedChangeFlagAttivo = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio della modifica.");
      this.loadedChangeFlagAttivo = true;
    });

  }

  impostazioniWidget(widget: WidgetDTO) {
    this.dialog.open(WidgetSettingsDialogComponent, {
      width: '60%',
      data: {
        widget: widget,
        bandiDaAssociare: this.bandiDaAssociare,
      }
    });
  }

  goToHome() {
    this.router.navigate([`/drawer/${Constants.ID_OPERAZIONE_HOME}/dashboardHome`])
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedWidgets || !this.loadedBandiDaAssociare || !this.loadedChangeFlagAttivo) {
      return true;
    }
    return false;
  }
}
