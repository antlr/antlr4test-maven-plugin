parser grammar TestGrammarParser;

options {
    tokenVocab = TestGrammarLexer;
}

attrib_list
: attrib+ EOF
;

attrib
:
	variable_name EQUAL function OPEN_PAREN variable_name? CLOSE_PAREN
;

variable_name
:
	ID
;

function
: FA
| FB
| FC
| FD
;