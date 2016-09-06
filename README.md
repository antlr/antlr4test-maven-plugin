antlr4test-maven-plugin
===============

Travis Status
---------

<a href="https://travis-ci.org/teverett/antlr4test-maven-plugin"><img src="https://api.travis-ci.org/teverett/antlr4test-maven-plugin.png"></a>

Maven Mojo for testing [Antlr4](http://www.antlr.org/) Grammars

Maven Coordinates
-------------

```
<groupId>com.khubla.antlr</groupId>
<artifactId>antlr4test-maven-plugin</artifactId>
<version>1.6</version>
<packaging>jar</packaging>
```

Example usage
---------

```xml
<plugin>
	<groupId>org.antlr</groupId>
	<artifactId>antlr4test-maven-plugin</artifactId>
	<configuration>
		<verbose>true</verbose>
		<showTree>true</showTree>
		<entryPoint>equation</entryPoint>
		<grammarName>tnt</grammarName>
		<packageName></packageName>
		<testFileExtension>.txt</testFileExtension>
		<exampleFiles>src/test/resources/examples</exampleFiles>
	</configuration>
</plugin>
```

Parameters
---------

###GrammarName

Required name of the grammar.  This should match the name of the grammar defined in the grammar ".g4" file.

```
<grammarName>PHP</grammarName>
```

###caseInsensitive

An optional boolean parameter used to enable a caseInsensitive lexer for case-insensitive languages such as PHP

```
<caseInsensitive>true</caseInsensitive>
```

###entryPoint

Required name of the grammar rule to use as the test entry point

```
<entryPoint>htmlDocument</entryPoint>
```

###enabled

Optional boolean enable-disable flag

```
<enabled>true<\enabled>
```

###verbose

Optionally produce verbose output

```
<verbose>true</verbose>
```

###showTree

Optionally show the LISP grammar tree

```
<showTree>false</showTree>
```

###exampleFiles

Required relative path to the example files

```
<exampleFiles>src/test/resources/examples/</exampleFiles>
```

###packageName

Optional package name to find the Lexer and Parser classes in

```
<packageName></packageName>
```

###testFileExtension 

Optional file extension of test files

```
<testFileExtension>.php</testFileExtension>
```


