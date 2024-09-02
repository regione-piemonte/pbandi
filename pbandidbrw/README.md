# Componente di Prodotto
PBANDIDBRW

## Versione
3.30.0

## Descrizione della componente
Componente schema pbandi_rw per RDBMS Oracle 11.

## Configurazioni iniziali
Definire l'utente "pbandi" su una istanza DBMS Oracle 11 proprietario dello schema, un utente "pbandi_rw" per accedere ai dati da applicativo (questo utente non ha la possibilità di modificare lo schema dati) ed un utente "pbandi_ro" per accedere ai dati dell'applicativo in sola lettura.

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

0. Esecuzione del file che si trova sotto la cartella src\sql\init:

* INIT-001-create_sinonimi.sql

1. Esecuzione dei file che si trovano sotto la cartella src\sql\incr nell'ordine indicato dal progressivo (xxx) contenuto nel nome del file:

* INCR-xxx-create_db.sql

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”