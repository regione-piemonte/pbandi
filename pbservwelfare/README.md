# Componente di Prodotto
PBSERVWELFARE

## Versione
1.2.0

## Descrizione della componente
Componente di esposizione servizi per i bandi welfare.

## Configurazioni iniziali
Questa componente deve essere installata come libreria trasversale delle componenti java. 

## Prerequisiti di sistema
* [Web Server: Apache WebServer - 2.4.53](https://www.apache.org)
* [Application Server: WildFly - 17.0](https://www.wildfly.org/)
## Software
* [Java Development Kit (jdk): 8](https://www.oracle.com/java)
* [Maven: 3.2.5](https://maven.apache.org)

## Installazione
mvn clean package -P prod-rp-01 -Dpostfix="" -Dmaven.test.skip=true

## Deployment
Inserire il file ear generato durante l'installazione sotto la cartella deployments del Wildfly

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”