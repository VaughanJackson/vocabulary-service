# Vocabulary Service

* `Spring Boot Initializr` generated REST service that serves up [Jun Da's Modern Chinese 汉字 vocabulary](http://lingua.mtsu.edu/chinese-computing/statistics/) from a `MongoDB` 
  database.  
  
## Usage

* So far pretty limited!
* Assuming you have a local `MongoDB` database daemon running with little security, load up the vocabulary like this:

```text
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ ./bootstrap.sh 
2018-04-15T14:00:32.278+0100    connected to: localhost
2018-04-15T14:00:32.396+0100    imported 9933 documents
vaughanjackson@Vaughans-MacBook-Pro vocabulary-service (master) $ 
```  

* Use a tool such as `Robo 3T` to inspect the resulting vocabulary.