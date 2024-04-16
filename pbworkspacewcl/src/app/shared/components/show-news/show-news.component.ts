/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AvvisoUtenteDTO } from 'src/app/core/commons/vo/avviso-utente-dto';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from '../../decorator/auto-unsubscribe';
import { VisualizzaTestoDialogComponent } from '../visualizza-testo-dialog/visualizza-testo-dialog.component';

@Component({
  selector: 'app-show-news',
  templateUrl: './show-news.component.html',
  styleUrls: ['./show-news.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ShowNewsComponent implements OnInit {

  news: Array<AvvisoUtenteDTO>;
  currentNews: AvvisoUtenteDTO;
  currentNewsIndex: number;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedNews: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadedNews = false;
    this.subscribers.news = this.userService.avvisi().subscribe(data => {
      if (data) {
        this.news = data;
        this.currentNews = this.news[0];
        this.currentNewsIndex = 0;
      }
      this.loadedNews = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero degli avvisi.");
      this.loadedNews = true;
    });
  }

  leggiTutto() {
    this.dialog.open(VisualizzaTestoDialogComponent, {
      width: "50%",
      data: {
        text: this.currentNews.descrizione
      }
    });
  }

  get isSingleNews() {
    return this.news?.length === 1;
  }

  newsPrecedente() {
    if (this.currentNewsIndex > 0) {
      this.currentNewsIndex--;
    } else {
      this.currentNewsIndex = this.news.length - 1;
    }
    this.currentNews = this.news[this.currentNewsIndex];
  }

  newsSuccessiva() {
    if (this.currentNewsIndex < this.news.length - 1) {
      this.currentNewsIndex++;
    } else {
      this.currentNewsIndex = 0;
    }
    this.currentNews = this.news[this.currentNewsIndex];
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  isLoading() {
    if (!this.loadedNews) {
      return true;
    }
    return false;
  }

}
