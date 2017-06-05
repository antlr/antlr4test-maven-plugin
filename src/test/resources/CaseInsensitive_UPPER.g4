grammar CaseInsensitive_UPPER;

rootRule
    : Token+ EOF
    ;

Token: 'AAAA';
Whitespace: [ \t\r\n]+ -> skip;