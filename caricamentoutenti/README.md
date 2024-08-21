# Componente di Prodotto
CAMPIONAMENTO

## Versione
2.0.0

## Descrizione della componente
Procedura ORACLE/PSQL per caricamento utenti da csv proveniente da frontend applicativo.

## Configurazioni iniziali
Per procedere all'installazione dei packages contenuti nella componente, bisogna collegarsi al DB Oracle ed assicurarsi che le unità di installazione contenute nelle componenti [PBANDIDB], [PBANDIRO] e [PBANDIRW] siano già state installate.

## Prerequisiti di sistema
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.org)

## Software
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.org)

## Installazione
Esecuzione dei file che si trovano sotto la cartella src\plsql\package nel seguente ordine:
* CREATE_GRANT_CARICAMENTO_UTENTI.sql
* PCK_PBANDI_CARICAMENTO_UTENTI.pks
* PCK_PBANDI_CARICAMENTO_UTENTI.pkb

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”