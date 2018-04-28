# Vocabulary Service

* `Spring Boot Initializr` generated REST service that serves up [Jun Da's Modern Chinese 汉字 vocabulary](http://lingua.mtsu.edu/chinese-computing/statistics/) from a `MongoDB` 
  database. 
  
## Build

* `mvn install`

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ mvn install
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building vocabulary-service 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:3.0.1:resources (default-resources) @ vocabulary-service ---
...
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ vocabulary-service ---
[INFO] Installing /Users/vaughanjackson/Dropbox/Projects/VocabularyDashboard/vocabulary-service/target/vocabulary-service-0.0.1-SNAPSHOT.jar to /Users/vaughanjackson/.m2/repository/com/learner/vocabulary-service/0.0.1-SNAPSHOT/vocabulary-service-0.0.1-SNAPSHOT.jar
[INFO] Installing /Users/vaughanjackson/Dropbox/Projects/VocabularyDashboard/vocabulary-service/pom.xml to /Users/vaughanjackson/.m2/repository/com/learner/vocabulary-service/0.0.1-SNAPSHOT/vocabulary-service-0.0.1-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 10.776 s
[INFO] Finished at: 2018-04-28T15:23:40+01:00
[INFO] Final Memory: 41M/597M
[INFO] ------------------------------------------------------------------------
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ 

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
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ ./bootstrap.sh 
2018-04-15T14:00:32.278+0100    connected to: localhost
2018-04-15T14:00:32.396+0100    imported 9933 documents
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ 
```  

* Use a tool such as `Robo 3T` to inspect the resulting vocabulary.

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

* The url in the example below actually appears as `http://localhost:8080/characters/search/findByCharacter?汉字=中` in the browser address bar before being copied and pasted into a terminal session for use with `curl`):

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