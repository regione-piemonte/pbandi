# Componente di Prodotto
PBANDIDB

## Versione
3.34.0

## Descrizione della componente
Componente schema pbandi per RDBMS Oracle 11.

## Configurazioni iniziali
Definire l'utente "pbandi" su una istanza DBMS Oracle 11 proprietario dello schema, un utente "pbandi_rw" per accedere ai dati da applicativo (questo utente non ha la possibilità di modificare lo schema dati) ed un utente "pbandi_ro" per accedere ai dati dell'applicativo in sola lettura.

## Prerequisiti di sistema
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)

## Software
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)

## Installazione
* Esecuzione dei file che si trovano sotto la cartella src\sql\init nel corretto ordine (creazione_db, creazione_sinonimi ....);
* Esecuzione dei seguenti file contenuti nela cartella src\sql\incr nell'ordine indicato:

1. create_database_area_crediti.sql
2. create_grant.sql

* Esecuzione dei file che si trovano sotto la cartella src\sql\incr nell'ordine indicato dal progressivo (xxx) contenuto nel nome del file:

1. INCR-xxx-create_db.sql

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”