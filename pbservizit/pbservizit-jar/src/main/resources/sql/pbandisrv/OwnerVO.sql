/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) owner from dual