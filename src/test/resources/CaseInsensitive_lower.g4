grammar CaseInsensitive_lower;

rootRule
    : Token+ EOF
    ;

Token: 'aaaa';
Whitespace: [ \t\r\n]+ -> skip;