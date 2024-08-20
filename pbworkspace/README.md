# Componente di Prodotto
PBWORKSPACEWCL

## Versione
1.8.0

## Descrizione del prodotto
Si tratta della parte di front-end della componente [PBWORKSPACE](../pbworkspace). Questa componente equivale ad un Desktop da cui vengono richiamate tutte le altre componenti web (moduli) che compongono il prodotto PBANDI.

## Configurazioni iniziali
Questa deve essere la prima componente ad essere installata in quanto funge da Desktop del prodotto PBANDI.

## Prerequisiti di sistema
[Web Server: Apache WebServer - 2.4.53](https://www.apache.org)
[Application Server: WildFly - 17.0](https://www.wildfly.org/)
[RDBMS: Oracle Database - 11.2.0.4.](https://www.oracle.org)
## Software
[Java Development Kit (jdk): 8](https://www.oracle.org)
[Maven: 3.2.5](https://maven.apache.org)

## Installazione
I parametri di configurazione per l'accesso al DB da parametrizzare in base agli ambienti, sono contenuti nel file pbworkspace\pbworkspace-tar\src\pbworkspace-ds.xml:
	Line  5: 		<connection-url>@@CONNECTION_URL@@</connection-url>
	Line  9: 		<user-name>@@DB_USER@@</user-name>
	Line 10: 		<password>@@DB_PWD@@</password>

Per generare i pacchetti effettuare i seguenti step:

1- cd pbworkspace
2- unzip -o pbworkspace-web\src\main\webapp\dist_wcl_prod_rp_01.zip -d pbworkspace-web\src\main\webapp\
   (per generare il file pbworkspace-web\src\main\webapp\dist_wcl_prod_rp_01.zip, seguire il README.md della componete [PBWORKSPACEWCL](../pbworkspacewcl) )
3- del pbworkspace-web\src\main\webapp\dist_wcl_prod_rp_01.zip
4- mvn clean package -P prod-rp-01 -Dpostfix="" -Dmaven.test.skip=true

## Deployment
Inserire il file ear generato durante l'installazione sotto la cartella deployments del Wildfly

## Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors
* [Marco Pochettino](mailto:marco.pochettino@csi.it)
* [Claudio Zamboni](mailto:claudio.zamboni@csi.it)

## Copyrights
“© Copyright Regione Piemonte – 2024”