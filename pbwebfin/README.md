# Componente di Prodotto
PBWEBFIN

## Versione
1.8.0

## Descrizione della componente
Questa componente si occupa della certificazione dei finanziamenti pubblici erogati.

## Configurazioni iniziali
Questa componente deve essere installata dopo la componente PBWORKSPACE.

## Prerequisiti di sistema
* [Web Server: Apache WebServer - 2.4.53](https://www.apache.org)
* [Application Server: WildFly - 17.0](https://www.wildfly.org/)
* [RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.com/java)
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
* <VH_APACHE>

I parametri di configurazione per l'accesso al DB da parametrizzare in base agli ambienti, sono contenuti nel file pbwebfin\pbwebfin-tar\src\pbwebfin-ds.xml:
*	Line  5: 		<connection-url>@@CONNECTION_URL@@</connection-url>
*	Line  9: 		<user-name>@@DB_USER@@</user-name>
*	Line 10: 		<password>@@DB_PWD@@</password>

Per generare i pacchetti effettuare i seguenti step:

1. cd pbworkspace
2. generare il file pbwebfin-web\src\main\webapp\dist_wcl_prod_rp_01.zip come indicato nel README.md della componete [PBWEBFINWCL](../pbwebfinwcl)
2. unzip -o pbwebfin-web\src\main\webapp\dist_wcl_prod_rp_01.zip -d pbwebfin-web\src\main\webapp\
3. del pbwebfin-web\src\main\webapp\dist_wcl_prod_rp_01.zip
4. mvn clean package -P prod-rp-01 -Dpostfix="" -Dmaven.test.skip=true

## Deployment
Inserire il file ear generato durante l'installazione sotto la cartella deployments del Wildfly

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”