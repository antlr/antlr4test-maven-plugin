[![Travis](https://api.travis-ci.org/antlr/antlr4test-maven-plugin.png)](https://travis-ci.org/antlr/antlr4test-maven-plugin)
[![Coverity Scan](https://scan.coverity.com/projects/13302/badge.svg)](https://scan.coverity.com/projects/teverett-antlr4test-maven-plugin)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/47b1ccdfcf5b4af4abdef44328f8cb26)](https://www.codacy.com/app/teverett/antlr4test-maven-plugin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=teverett/antlr4test-maven-plugin&amp;utm_campaign=Badge_Grade)
[![DepShield Badge](https://depshield.sonatype.org/badges/teverett/antlr4test-maven-plugin/depshield.svg)](https://depshield.github.io)

# antlr4test-maven-plugin

Maven Mojo for testing [Antlr4](http://www.antlr.org/) Grammars

## Maven Coordinates

```xml
<groupId>com.khubla.antlr</groupId>
<artifactId>antlr4test-maven-plugin</artifactId>
<version>1.6</version>
<packaging>jar</packaging>
```

## Example usage

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

## Parameters

### GrammarName

Required name of the grammar.  This should match the name of the grammar defined in the grammar ".g4" file.

```xml
<grammarName>PHP</grammarName>
```

### caseInsensitiveType

An optional enum parameter used to enable a caseInsensitive lexer for case-insensitive languages such as PHP, Pascal, T-SQL, etc.

Available values:
* `None` - does not activate a case insensitive mode (by default).
* `lower` - all token values should be written in lower-case: `TOKEN: 'asdf'`.
* `UPPER` - all token values should be written in UPPER-case: `TOKEN: 'ASDF'`.

```xml
<caseInsensitiveType>UPPER</caseInsensitiveType>
```

### entryPoint

Required name of the grammar rule to use as the test entry point

```xml
<entryPoint>htmlDocument</entryPoint>
```

### enabled

Optional boolean enable-disable flag

```xml
<enabled>true<\enabled>
```

### verbose

Optionally produce verbose output

```xml
<verbose>true</verbose>
```

### showTree

Optionally show the LISP grammar tree

```xml
<showTree>false</showTree>
```

### exampleFiles

Required relative path to the example files

```xml
<exampleFiles>src/test/resources/examples/</exampleFiles>
```

### packageName

Optional package name to find the Lexer and Parser classes in

```xml
<packageName></packageName>
```

### testFileExtension 

Optional file extension of test files

```xml
<testFileExtension>.php</testFileExtension>
```

### fileEncoding

Optional file encoding. The default value if UTF-8.

```xml
<fileEncoding>Shift_JIS</fileEncoding>
```
