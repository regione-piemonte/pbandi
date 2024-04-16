/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { DashboardService } from '../../services/dashboard.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { WidgetDTO } from '../../commons/dto/widget-dto';

@Component({
  selector: 'app-dashboard-home',
  templateUrl: './dashboard-home.component.html',
  styleUrls: ['./dashboard-home.component.scss']
})
export class DashboardHomeComponent implements OnInit {

  user: UserInfoSec;
  impostazioniSaved: boolean;
  widgetsAttivi: Array<WidgetDTO>;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedIsWidgetConfigured: boolean;
  loadedWidgets: boolean = true;

  //SUSBCRIBERS
  subscribers: any = {};
  constructor(
    private userService: UserService,
    private dashboardService: DashboardService,
    private handleExceptionService: HandleExceptionService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.loadedIsWidgetConfigured = false;
        this.dashboardService.areWidgetsConfigured().subscribe(res => {
          this.impostazioniSaved = res;
          if (this.impostazioniSaved) {
            this.loadedWidgets = false;
            this.dashboardService.getWidgets().subscribe(res => {
              this.widgetsAttivi = res ? res.filter(w => w.flagWidgetAttivo) : [];
              this.loadedWidgets = true;
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di verifica delle impostazione salvate.");
              this.loadedWidgets = true;
            });
          }
          this.loadedIsWidgetConfigured = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica delle impostazione salvate.");
          this.loadedIsWidgetConfigured = true;
        });
      }
    });
  }

  goToSettings() {
    this.router.navigate([`/drawer/${Constants.ID_OPERAZIONE_HOME}/dashboardSettings`])
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
    if (!this.loadedIsWidgetConfigured || !this.loadedWidgets) {
      return true;
    }
    return false;
  }

}
