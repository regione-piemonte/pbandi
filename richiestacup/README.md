# Componente di Prodotto
PROCESSO

## Versione
2.0.1

## Descrizione della componente
Procedura PLSQL di richiesta CUP.

## Configurazioni iniziali
Per procedere all'installazione dei packages contenuti nella componente, bisogna collegarsi al DB Oracle ed assicurarsi che le unità di installazione contenute nelle componenti [PBANDIDB], [PBANDIRO] e [PBANDIRW] siano già state installate.

## Prerequisiti di sistema
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)

## Software
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)

## Installazione
Sostituire in tutto il codice, se presenti, i seguenti placeholders con gli opportuni VH dedicati:
* <VH_ESPOSIZIONE_SERVIZI>
* <VH_API_MANAGER>
* <VH_SECURE>
* <VH_DB>
* <VH_DOMICILIARITA_RESIDENZIALITA>
* <VH_OAUTH>
* <VH_APACHE>

Esecuzione dei file che si trovano sotto la cartella src\plsql\package nel seguente ordine:
* CREATE_GRANT_RICHIESTA.sql
* PCK_PBANDI_RICHIESTA_CUP.pkb
* PCK_PBANDI_RICHIESTA_CUP.pks

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”