antlr4test-maven-plugin
===============

Maven Mojo for testing [Antlr4](http://www.antlr.org/) Grammars

Example usage
---------

```xml
<plugin>
  <groupId>com.khubla.antlr</groupId>
  <artifactId>grammartest-maven-plugin</artifactId>
  <configuration>
    <verbose>true</verbose>
    <entryPoint>equation</entryPoint>
    <exampleFiles>src/test/resources/examples/</exampleFiles>
    <grammarFiles>src/test/resources/grammars/</grammarFiles>
  </configuration>
</plugin>
```

Travis Status
---------

<a href="https://travis-ci.org/teverett/antlr4test-maven-plugin"><img src="https://api.travis-ci.org/teverett/antlr4test-maven-plugin.png"></a>
