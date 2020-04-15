lexer grammar TestGrammarLexer ;

options {
    superClass = dummy.BaseLexer;
}

fragment DIGIT:    [0-9];
fragment LETTER: [a-zA-Z];

EQUAL
	: '=' ;

FA
	: [fF] [aA] { setType(checkTypeID(TestGrammarLexer.FA)); }
	;

FB
	: [fF] [bB] {	setType(checkTypeID(TestGrammarLexer.FB)); }
	;
//

FC
	: [fF] [cC] { setType(checkTypeID(TestGrammarLexer.FC)); }
	;
//

FD
	: [fF] [dD] { setType(checkTypeID(TestGrammarLexer.FD)); }
	;
// 

OPEN_PAREN: '(' ;

CLOSE_PAREN: ')' ;

WHITESPACE: [ \t\f\r\n] -> channel(HIDDEN); // Ignore whitespaces.

ID	: (DIGIT|LETTER)+
	;
