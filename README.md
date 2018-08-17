# Vocabulary Service

* `Spring Boot Initializr` generated REST service that serves up [Jun Da's Modern Chinese 汉字 vocabulary](http://lingua.mtsu.edu/chinese-computing/statistics/) from a `MongoDB` 
  database. 
* You can build, test and use the service [locally on your host machine](#build), or you can use [Docker containers](#using-docker) to do so. 
  
## Build

* `mvn clean package`

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ mvn clean package
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building vocabulary-service 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.0.0:clean (default-clean) @ vocabulary-service ---
[INFO] Deleting /Users/vaughanjackson/Dropbox/Projects/VocabularyDashboard/vocabulary-service/target
...
[INFO] Results:
[INFO] 
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-jar-plugin:3.0.2:jar (default-jar) @ vocabulary-service ---
[INFO] Building jar: /Users/vaughanjackson/Dropbox/Projects/VocabularyDashboard/vocabulary-service/target/vocabulary-service-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.0.1.RELEASE:repackage (default) @ vocabulary-service ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 8.456 s
[INFO] Finished at: 2018-05-20T13:31:26+01:00
[INFO] Final Memory: 41M/532M
[INFO] ------------------------------------------------------------------------
```   
 
## Execution

* `mvn spring-boot:run` 

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ mvn spring-boot:run
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building vocabulary-service 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] >>> spring-boot-maven-plugin:2.0.1.RELEASE:run (default-cli) > test-compile @ vocabulary-service >>>
...
[INFO] --- spring-boot-maven-plugin:2.0.1.RELEASE:run (default-cli) @ vocabulary-service ---

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.1.RELEASE)

2018-04-28 15:31:29.789  INFO 34539 --- [           main] c.l.v.VocabularyServiceApplication       : Starting VocabularyServiceApplication on Vaughans-MacBook-Pro.local with PID 34539 (/Users/vaughanjackson/Dropbox/Projects/VocabularyDashboard/vocabulary-service/target/classes started by vaughanjackson in /Users/vaughanjackson/Dropbox/Projects/VocabularyDashboard/vocabulary-service)
...
2018-04-28 15:31:33.812  INFO 34539 --- [           main] s.b.a.e.w.s.WebMvcEndpointHandlerMapping : Mapped "{[/actuator],methods=[GET],produces=[application/vnd.spring-boot.actuator.v2+json || application/json]}" onto protected java.util.Map<java.lang.String, java.util.Map<java.lang.String, org.springframework.boot.actuate.endpoint.web.Link>> org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping.links(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2018-04-28 15:31:33.864  INFO 34539 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-04-28 15:31:33.918  INFO 34539 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2018-04-28 15:31:33.923  INFO 34539 --- [           main] c.l.v.VocabularyServiceApplication       : Started VocabularyServiceApplication in 4.489 seconds (JVM running for 6.882)
```
  
## Usage

* Assuming you have a local `MongoDB` database daemon running with little security, load up the vocabulary like this:

```text
vaughanjackson@Vaughans-MBP vocabulary-service (master) $ ./bootstrap.sh 
2018-08-17T16:13:27.434+0100    connected to: localhost
2018-08-17T16:13:27.563+0100    imported 9933 documents
MongoDB shell version v4.0.1
connecting to: mongodb://127.0.0.1:27017/vocabulary
MongoDB server version: 4.0.1
vaughanjackson@Vaughans-MBP vocabulary-service (master) $ 
```  

* Use a tool such as `Robo 3T` to inspect the resulting vocabulary.
* Typically connect `Robo 3T` to `localhost:27017` to inspect the database if you have set it up on your host machine.
* Alternatively, if you are working with the dockerised version, connect `Robo 3T` to `localhost:27020`.

### Query Modes

* Thus app configures Spring Data MongoDB to support two query modes of interest. 

#### Paged access to all characters (whole vocabulary)

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ curl "http://localhost:8080/characters?page=0&size=3&sort=frequencyRank"
{
  "_embedded" : {
    "characters" : [ {
      "character" : "的",
      "frequencyRank" : 1,
      "frequency" : 7922684,
      "cumulativePercentage" : 4.09,
      "pinyin" : "de/di2/di4",
      "englishTranslation" : "(possessive particle)/of, really and truly, aim/clear",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d09320f"
        },
        "character" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d09320f"
        }
      }
    }, {
      "character" : "一",
      "frequencyRank" : 2,
      "frequency" : 3050722,
      "cumulativePercentage" : 5.67,
      "pinyin" : "yi1",
      "englishTranslation" : "one/1/single/a(n)",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d09320e"
        },
        "character" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d09320e"
        }
      }
    }, {
      "character" : "是",
      "frequencyRank" : 3,
      "frequency" : 2615490,
      "cumulativePercentage" : 7.02,
      "pinyin" : "shi4",
      "englishTranslation" : "is/are/am/yes/to be",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d09320d"
        },
        "character" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d09320d"
        }
      }
    } ]
  },
  "_links" : {
    "first" : {
      "href" : "http://localhost:8080/characters?page=0&size=3&sort=frequencyRank,asc"
    },
    "self" : {
      "href" : "http://localhost:8080/characters"
    },
    "next" : {
      "href" : "http://localhost:8080/characters?page=1&size=3&sort=frequencyRank,asc"
    },
    "last" : {
      "href" : "http://localhost:8080/characters?page=3310&size=3&sort=frequencyRank,asc"
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/characters"
    },
    "search" : {
      "href" : "http://localhost:8080/characters/search"
    }
  },
  "page" : {
    "size" : 3,
    "totalElements" : 9933,
    "totalPages" : 3311,
    "number" : 0
  }
}vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ 
```

#### Individual character by character (汉字)

* Use the URL below in a browser instead if you prefer. 
* The URL in the example below actually appears as `http://localhost:8080/characters/search/findByCharacter?汉字=中` in the browser address bar before being copied and pasted into a terminal session for use with `curl`):

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ curl http://localhost:8080/characters/search/findByCharacter?%E6%B1%89%E5%AD%97=%E4%B8%AD
{
  "_embedded" : {
    "characters" : [ {
      "character" : "中",
      "frequencyRank" : 14,
      "frequency" : 1104541,
      "cumulativePercentage" : 16.5,
      "pinyin" : "zhong1/zhong4",
      "englishTranslation" : "within/among/in/middle/center/while (doing sth)/during/China/Chinese, hit (the mark)",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d093218"
        },
        "character" : {
          "href" : "http://localhost:8080/characters/5ad34cf0bb426a874d093218"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/characters/search/findByCharacter?%E6%B1%89%E5%AD%97=%E4%B8%AD"
    }
  }
}
```

## Automated Integration Tests

These have been implemented in the `VocabularyApplicationIntegrationTest` class and fall into two categories:

1. Tests that assert correct behaviour of the supported query modes
2. Tests that assert that the CRUD operations, supported by default when using a Spring Data REST Repository such as
`CharacterRepository`, have been effectively disabled here. This service is intended to provide read access only.  

## Using Docker

* If you have [Docker](https://www.docker.com/) set up on your host, you can take advantage of its automation to stand 
up an instance of the service quickly and conveniently.

### Docker Build

* Use this command line: `docker-compose build`

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ docker-compose build
mongodb uses an image, skipping
Building mongo-seed
Step 1/3 : FROM mongo
 ---> 14c497d5c758
Step 2/3 : COPY CharFreq-Modern.csv /CharFreq-Modern.csv
 ---> Using cache
 ---> 60a4c79469a8
Step 3/3 : CMD mongoimport      --host mongodb     --db vocabulary     --collection vocabulary     --drop     --type csv     --headerline     --file /CharFreq-Modern.csv
 ---> Using cache
 ---> b5f8670ca7bd
Successfully built b5f8670ca7bd
Successfully tagged vocabularyservice_mongo-seed:latest
Building vocabulary_service
Step 1/6 : FROM openjdk:8-jdk-alpine
 ---> 224765a6bdbe
Step 2/6 : VOLUME /tmp
 ---> Using cache
 ---> a5262424a483
Step 3/6 : ADD target/vocabulary-service-0.0.1-SNAPSHOT.jar app.jar
 ---> 28714ebf1638
Step 4/6 : EXPOSE 8080
 ---> Running in fe4f843f9750
Removing intermediate container fe4f843f9750
 ---> 167f78bbb68a
Step 5/6 : ENV JAVA_OPTS="-Dspring.profiles.active=docker"
 ---> Running in 7c82a0f2150c
Removing intermediate container 7c82a0f2150c
 ---> b2ef72c80470
Step 6/6 : ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
 ---> Running in b2af32794a74
Removing intermediate container b2af32794a74
 ---> 6fb59e6b4917
Successfully built 6fb59e6b4917
Successfully tagged vocabularyservice_vocabulary_service:latest
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ 
```

### Docker Execution  

* Use this command line: `docker-compose up`

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ docker-compose up
Creating network "vocabularyservice_default" with the default driver
Creating vocabdb ... done
Creating vocabularyservice_vocabulary_service_1 ... done
Creating vocabularyservice_mongo-seed_1         ... done
Attaching to vocabdb, vocabularyservice_vocabulary_service_1, vocabularyservice_mongo-seed_1
vocabdb               | 2018-05-20T12:42:49.869+0000 I CONTROL  [initandlisten] MongoDB starting : pid=1 port=27017 dbpath=/data/db 64-bit host=eb3e9e019db2
...
vocabulary_service_1  | 2018-05-20 12:42:57.546  INFO 7 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
vocabulary_service_1  | 2018-05-20 12:42:57.600  INFO 7 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
vocabulary_service_1  | 2018-05-20 12:42:57.605  INFO 7 --- [           main] c.l.v.VocabularyServiceApplication       : Started VocabularyServiceApplication in 6.326 seconds (JVM running for 7.006)
```

### Stopping Docker Execution

* Use this command line: `docker-compose down`

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ docker-compose down
Stopping vocabularyservice_vocabulary_service_1 ... done
Stopping vocabdb                                ... done
Removing vocabularyservice_mongo-seed_1         ... done
Removing vocabularyservice_vocabulary_service_1 ... done
Removing vocabdb                                ... done
Removing network vocabularyservice_default
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ 
```

## Allowing Cross-Origin Requests

You may find that your client gets a `403` (Forbidden) response from the service with this payload:

```text
Invalid CORS request
```

If your client is hosted at `xyz.com` for example, then you can avoid this problem by setting the `corsAllowedOrigins`
property in `application.properties`:

```text
# corsAllowedOrigins - set to a comma separated list of origins to restrict cross-origin requests to those from
#                      the origins listed, or comment out to open up access to requests from anywhere (not advisable
#                      in production).
corsAllowedOrigins=http://xyz.com
```

Or, for *testing in development only*, you could open up the service to requests from any origin by commenting out the 
property altogether like this:

```text
# corsAllowedOrigins - set to a comma separated list of origins to restrict cross-origin requests to those from
#                      the origins listed, or comment out to open up access to requests from anywhere (not advisable
#                      in production).
# corsAllowedOrigins=http://xyz.com
``` 