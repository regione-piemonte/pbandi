# Componente di Prodotto
FILESTORAGE

## Versione
1.0.0

## Descrizione del prodotto
Componente trasversale (jar) per la gestione degli storage su fileSystem.

## Configurazioni iniziali
Questa componente deve essere installata come libreria trasversale delle componenti che accedono al FileSystem.

## Prerequisiti di sistema

## Software
[Java Development Kit (jdk): 8](https://www.oracle.org)
[Maven: 3.2.5](https://maven.apache.org)

## Installazione
mvn clean package -P prod-rp-01 -Dpostfix="" -Dmaven.test.skip=true

## Deployment
Inserire il jar tra le librerie utilizzate della componenti

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”