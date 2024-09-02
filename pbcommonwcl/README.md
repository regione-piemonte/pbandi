# Componente di Prodotto
PBCOMMONWCL

## Versione
14.5.1

## Descrizione della componente
Questa componente contiene funzionalità angular comuni per BPM.

## Configurazioni iniziali
Questa deve essere resa disponibile prima dell'installazione come libreria prima delle altre componenti angular.

## Prerequisiti di sistema

## Software
[Angular CLI: 10.2.0](https://angular.io)
[Node: 12.20.1](https://nodejs.org)
[Maven: 3.2.5](https://maven.apache.org)

## Installazione
Sostituire in tutto il codice, se presenti, i seguenti placeholders con gli opportuni VH dedicati:
* <VH_ESPOSIZIONE_SERVIZI>
* <VH_API_MANAGER>
* <VH_SECURE>
* <VH_DB>
* <VH_DOMICILIARITA_RESIDENZIALITA>
* <VH_OAUTH>
* <VH_APACHE>

0. Assicurarsi di avere installato il modulo angular-devkit (npm i @angular-devkit/build-angular)
1. cd pbcommonwcl
2. ng build @pbandi/common-lib --prod
3. cd dist/pbandi/common-lib
4. npm pack
5. pubblicare su un repository il file pbandi-common-lib-0.0.1.tgz generato 

## Deployment
Essendo una componente comune ad altre componenti, dovrà essere resa disponibile sul repository comune e scaricata per ogni componente angular.

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”