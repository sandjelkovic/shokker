# Shokker

## Services

- Twitter Producer
- Tweet Mapper
- Post Persister
- Backing services
   - Kafka
   - Zookeper
   - Cassandra

### Twitter producer
Simple Kotlin application. Run with arguments.

Example config for IntelliJ's run configuration:

```
CONSUMER_KEY=TWITTER_CONSUMER_KEY CONSUMER_SECRET=TWITTER_CONSUMER_SECRET ACCESS_TOKEN=TWITTER_ACCESS_TOKEN TOKEN_SECRET=TWITTER_TOKEN_SECRET HASHTAGS=#bigdata,#cassandra,#kafka
```

Breakdown of required arguments:
Twitter API secrets:
```
CONSUMER_KEY
CONSUMER_SECRET
ACCESS_TOKEN
TOKEN_SECRET
```
List of hashtags to listen to. Comma-separated values, with `#` included
``` 
HASHTAGS
```

### Twitter Producer
Spring boot application. Can be run from IDE or terminal with `./gradlew bootRun`

### Post Persister
Spring boot application. Can be run from IDE or terminal with `./gradlew bootRun`

### Backing services
All of these services can be easily run via `docker-compose`.

Start with `docker-compose up -d` and stop with `docker-compose stop`
