# Componente di Prodotto
PBSERVIZIT

## Versione
1.0.9

## Descrizione della componente
Componente trasversale contentente funzionalità comuni alle componenti Java.

## Configurazioni iniziali
Questa componente deve essere installata come libreria trasversale delle componenti java. 

## Prerequisiti di sistema
* [Application Server: WildFly - 17.0](https://www.wildfly.org/)

## Software
* [Java Development Kit (jdk): 8](https://www.oracle.com/java)
* [Maven: 3.2.5](https://maven.apache.org)

## Installazione
Sostituire in tutto il codice, se presenti, i seguenti placeholders con gli opportuni VH dedicati:
* <VH_ESPOSIZIONE_SERVIZI>
* <VH_API_MANAGER>
* <VH_SECURE>
* <VH_DB>
* <VH_DOMICILIARITA_RESIDENZIALITA>
* <VH_OAUTH>
* <VH>

mvn clean package -P prod-rp-01 -Dpostfix="" -Dmaven.test.skip=true

## Deployment
Essendo una libreria comune a tutte le componenti java, pubblicare il jar prodotto sul repository comune.

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”