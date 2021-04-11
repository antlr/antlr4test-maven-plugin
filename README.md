![CI](https://github.com/antlr/antlr4test-maven-plugin/workflows/CI/badge.svg)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/47b1ccdfcf5b4af4abdef44328f8cb26)](https://www.codacy.com/app/teverett/antlr4test-maven-plugin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=teverett/antlr4test-maven-plugin&amp;utm_campaign=Badge_Grade)
[![DepShield Badge](https://depshield.sonatype.org/badges/teverett/antlr4test-maven-plugin/depshield.svg)](https://depshield.github.io)

# antlr4test-maven-plugin

Maven Mojo for testing [Antlr4](http://www.antlr.org/) Grammars

## Maven Coordinates

```xml
<groupId>com.khubla.antlr</groupId>
<artifactId>antlr4test-maven-plugin</artifactId>
<version>1.18</version>
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
		<grammarInitializer>com.my.package.MyGrammarInitializer</grammarInitializer>
	</configuration>
</plugin>
```

## Parameters

### grammarName

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
### binary

Optionally treat file as binary

```xml
<binary>true</binary>
```

### enabled

Optional boolean enable-disable flag

```xml
<enabled>true</enabled>
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

Optional file encoding. The default value is UTF-8.

```xml
<fileEncoding>Shift_JIS</fileEncoding>
```

### grammarInitializer

Optional full qualified name of a Java class that implements com.khubla.antlr.antlr4test.GrammarInitializer interface.

```xml
<grammarInitializer>com.my.package.MyGrammarInitializer</grammarInitializer>
```

When used, allows the Lexer and/or Parser to be initialized **before** grammar test starts.
This option is typically used when your grammar uses **superClass** antlr4 option to override/extend default Lexer and/or Parser behavior and need to be initialized somehow before use.

One instance will be created with default constructor for each file being parsed and initialize method will be called with the Lexer and Parser instances that will be used in the tests.
The class that implements GrammarInitializer should implement the following signature.

```java
public void initialize(Lexer lexer, Parser parser)
```

## Multiple Test Scenarios

A new optional configuration style was created to allow multiple test scenarios for the grammar.
A test scenario is a set of configuration parameters used to test a particular scenario.
The same configuration parameters used in traditional style can be used within test scenarios.

Its particularly useful for grammars that allows multiple configuration options through the use of **superClass** antlr4 option.
Test scenarios will probably be used in conjunction with grammarInitializer parameter, allowing tests for distinct sets of grammar options.
All configuration parameters are allowed within a test scenario. Here is an example of this new configuration style.

```xml
<plugin>
	<groupId>org.antlr</groupId>
	<artifactId>antlr4test-maven-plugin</artifactId>
	<configuration>
		<scenarios>
			<scenario>
				<scenarioName>Package-Without-Initialization</scenarioName>
				<verbose>false</verbose>
				<showTree>true</showTree>
				<entryPoint>attrib_list</entryPoint>
				<grammarName>TestGrammar</grammarName>
				<packageName>dummy</packageName>
				<testFileExtension>.txt</testFileExtension>
				<exampleFiles>src/test/resources/noInitializationScenario/</exampleFiles>
			</scenario>
			<scenario>
				<scenarioName>Package-Initialize-IgnoreSpaces</scenarioName>
				<verbose>true</verbose>
				<showTree>true</showTree>
				<entryPoint>attrib_list</entryPoint>
				<grammarName>TestGrammar</grammarName>
				<packageName>dummy</packageName>
				<testFileExtension>.txt</testFileExtension>
				<exampleFiles>src/test/resources/initializeIgnoreSpacesScenario/</exampleFiles>
				<grammarInitializer>dummy.TestGrammarInitializer</grammarInitializer>
			</scenario>
		</scenarios>
	</configuration>
</plugin>
```

In the example we can see a first test scenario where no initialization is made and another test scenario where a "Ignore Space" grammar option is initialized before tests takes place.
You can point to distinct example files directory in each test scenario. You can even use distinct grammars in each scenario.
One should not assume any particular order of execution for each scenario.

### Mixing Configuration Styles

You can mix traditional configuration style with test scenario style.
In this case, the set of parameters created within traditional configuration style scope will be created within a "Default Scenario" scenario.

The following two configurations are completely equivalent.

- Traditional Configuration Style

```xml
<plugin>
	<groupId>org.antlr</groupId>
	<artifactId>antlr4test-maven-plugin</artifactId>
	<configuration>
		<verbose>true</verbose>
		<showTree>true</showTree>
		<entryPoint>equation</entryPoint>
		<grammarName>tnt</grammarName>
		<testFileExtension>.txt</testFileExtension>
		<exampleFiles>src/test/resources/examples</exampleFiles>
		<grammarInitializer>com.my.package.MyGrammarInitializer</grammarInitializer>
	</configuration>
</plugin>
```

- Test Scenario Configuration Style

```xml
<plugin>
	<groupId>org.antlr</groupId>
	<artifactId>antlr4test-maven-plugin</artifactId>
	<configuration>
		<scenarios>
			<scenario>
				<scenarioName>Default Scenario</scenarioName>
				<verbose>true</verbose>
				<showTree>true</showTree>
				<entryPoint>equation</entryPoint>
				<grammarName>tnt</grammarName>
				<testFileExtension>.txt</testFileExtension>
				<exampleFiles>src/test/resources/examples</exampleFiles>
				<grammarInitializer>com.my.package.MyGrammarInitializer</grammarInitializer>
			</scenario>
		</scenarios>
	</configuration>
</plugin>
```

## Checking parsed tree

Sometimes you want to assure that a given input file generates an specific parsed tree. To do so, you can create sibling files to the parsed examples provided adding the .tree extension to the full name of the parsed file to ask the plugin to check if the parsed tree obtained from the file matchs an expected tree. 

For example, if you have an 'examples/fileToBeParsed.txt' file, you can create a 'examples/fileToBeParsed.txt.tree' file containing the LISP style tree expected to be parsed from you file.

The checking is done by taking the parsed tree and transforming it to LISP style tree with toStringTree(parserRuleContext, parser) of class org.antlr.v4.runtime.tree.Trees. If they match, everything is fine. If don't, a Diff is generated to show where differences was found.

Here is the code snippet that checks the parsed tree against the expected one:

```java
		final File treeFile = new File(grammarFile.getAbsolutePath() + GrammarTestMojo.TREE_SUFFIX);
		if (treeFile.exists()) {
			final String lispTree = Trees.toStringTree(parserRuleContext, parser);
			if (null != lispTree) {
				final String treeFileData = FileUtils.fileRead(treeFile, scenario.getFileEncoding());
				if (null != treeFileData) {
					if (0 != treeFileData.compareTo(lispTree)) {
						StringBuilder sb = new StringBuilder(
								"Parse tree does not match '" + treeFile.getName() + "'. Differences: ");
						for (DiffMatchPatch.Diff diff : new DiffMatchPatch().diffMain(treeFileData, lispTree)) {
							sb.append(diff.toString());
							sb.append(", ");
						}
						throw new Exception(sb.toString());
					} else {
						log.info("Parse tree for '" + grammarFile.getName() + "' matches '" + treeFile.getName() + "'");
					}
				}
			}
		}
```

A tip to use this feature is to configure the plugin to shows the parsed tree using **<showTree>true</showTree>** option. Then you can check the log output manually to see if the generated tree is the expected one. If it is ok, you can copy/paste the parsed tree (only the parsed tree with no other message accessories) to the .tree file.

## Checking expected errors

Sometimes you want to check if specific input files generate parsing errors. This can be done with the plugin by adding a new file sibling to the parsed one with .errors extension. Each line is interpreted as an expected parsing error, and the message in the file is compared to the parsed results. If they match, everything is ok, otherwise a test error is raised.

For example, if you have an 'examples/fileToBeParsed.txt' file, you can create a 'examples/fileToBeParsed.txt.errors' file containing the expected parse error messages.

A tip to use this feature is to configure the plugin to shows the errors using **<verbose>true</verbose>** option. Then you can check the log output manually to see exact error message generated and copy/paste the error messages (only the parsed tree with no other message accessories) to the .errors file.

