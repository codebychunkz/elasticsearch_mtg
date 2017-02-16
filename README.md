# elasticsearch_mtg
Playing around with MTG cards and a dockerised elasticsearch for fuzzy queries.

## Prerequisites
- Gradle 3.x
- Java 8
- Elasticsearch 5.x with BASIC auth/no auth

## How to run

**elasticurl:**The url to elasticsearch

**basicauth_username:** username for BASIC auth

**basicauth_password:** password for BASIC auth

**attribute:** The MTG card attribute to query(name, text etc)

**value:** The query value

You can try put the following docker image of elasticsearch is to much of a hazzle to install.

docker run -d -p 9200:9200 -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" --name elastic docker.elastic.co/elasticsearch/elasticsearch:5.1.1

###First time
Add the init param the first time to initialize elasticsearch with all cards data

gradle execute -Pappargs="-init -elasticurl **elasticurl** -username **basicauth_username** -password **basicauth_password** -attr **attribute** -value **value**"

###Later runs
On later runs the init param can be excluded

gradle execute -Pappargs="-elasticurl **elasticurl** -username **basicauth_username** -password **basicauth_password** -attr **attribute** -value **value**"

For example:

gradle execute -Pappargs="-elasticurl http://127.0.0.1:9200 -username elastic -password changeme -attr name -value rage"
