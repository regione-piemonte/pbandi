# Componente di Prodotto
PBWEBBO

## Versione
1.8.0

## Descrizione della componente
Questa componente si occupa della gestione della gestione back-office.

## Configurazioni iniziali
Questa componente deve essere installata dopo la componente PBANDIWORKSPACE.

## Prerequisiti di sistema
* [Web Server: Apache WebServer - 2.4.53](https://www.apache.org)
* [Application Server: WildFly - 17.0](https://www.wildfly.org/)
* [RDBMS: Oracle Database - 11.2.0.4.](https://https://www.oracle.com/java)
## Software
* [Java Development Kit (jdk): 8](https://https://www.oracle.com/java)
* [Maven: 3.2.5](https://maven.apache.org)

## Installazione
I parametri di configurazione per l'accesso al DB da parametrizzare in base agli ambienti, sono contenuti nel file pbwebbo\pbwebbo-tar\src\pbwebbo-ds.xml:
	Line  5: 		<connection-url>@@CONNECTION_URL@@</connection-url>
	Line  9: 		<user-name>@@DB_USER@@</user-name>
	Line 10: 		<password>@@DB_PWD@@</password>

Per generare i pacchetti effettuare i seguenti step:

1. cd pbworkspace
2. generare il file pbwebbo-web\src\main\webapp\dist_wcl_prod_rp_01.zip come indicato nel README.md della componete [PBWEBBOWCL](../pbwebbowcl)
3. unzip -o pbwebbo-web\src\main\webapp\dist_wcl_prod_rp_01.zip -d pbwebbo-web\src\main\webapp\
4. del pbwebbo-web\src\main\webapp\dist_wcl_prod_rp_01.zip
5. mvn clean package -P prod-rp-01 -Dpostfix="" -Dmaven.test.skip=true

## Deployment
Inserire il file ear generato durante l'installazione sotto la cartella deployments del Wildfly

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”