/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
	selector: 'app-blocchi',
	templateUrl: './blocchi.component.html',
	styleUrls: ['./blocchi.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class BlocchiComponent implements OnInit {

	@Input("idProgetto") idProgetto: number;
	@Input("idBando") idBando: number;

	@Output("messageError") messageError = new EventEmitter<string>();
	@Output("messageSuccess") messageSuccess = new EventEmitter<string>();
	@Output("messageWarning") messageWarning = new EventEmitter<string>();

	user: UserInfoSec;

	subscribers: any = {};

	constructor(
		private userService: UserService
	) { }

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
			}
		});
	}

	loadData(isInit?: boolean) {
		console.log("TODO: LOAD BLOCCHI");
	}

	showMessageError(msg: string) {
		this.messageError.emit(msg);
	}

	resetMessageError() {
		this.messageError.emit(null);
	}

	showMessageSuccess(msg: string) {
		this.messageSuccess.emit(msg);
	}

	resetMessageSuccess() {
		this.messageSuccess.emit(null);
	}

	showMessageWarning(msg: string) {
		this.messageWarning.emit(msg);
	}

	isLoading() {
		// if (!this.loadedFinpiemonte) {
		//   return true;
		// }
		return false;
	}
}
