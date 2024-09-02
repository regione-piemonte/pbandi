# Componente di Prodotto
CAMPIONAMENTO

## Versione
1.3.0

## Descrizione della componente
Procedura ORACLE/PSQL di campionamento che estrae un campione delle operazioni (progetti certificati) da controllare per FSE, FESR e FSC in modo automatico e di tenerne traccia.

## Configurazioni iniziali
Per procedere all'installazione dei packages contenuti nella componente, bisogna collegarsi al DB Oracle ed assicurarsi che le unità di installazione contenute nelle componenti [PBANDIDB], [PBANDIRO] e [PBANDIRW] siano già state installate.

## Prerequisiti di sistema
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)

## Software
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)

## Installazione
0. Sostituire in tutto il codice, se presenti, i seguenti placeholders con gli opportuni VH dedicati:
* <VH_ESPOSIZIONE_SERVIZI>
* <VH_API_MANAGER>
* <VH_SECURE>
* <VH_DB>
* <VH_DOMICILIARITA_RESIDENZIALITA>
* <VH_OAUTH>
* <VH>
1. Esecuzione dei file che si trovano sotto la cartella src\plsql\package nel seguente ordine:
* CREATE_GRANT_CAMPIONAMENTO.sql
* PCK_PBANDI_CAMPIONAMENTO.pks
* PCK_PBANDI_CAMPIONAMENTO.pkb

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”