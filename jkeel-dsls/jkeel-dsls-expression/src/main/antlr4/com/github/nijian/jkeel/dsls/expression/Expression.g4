grammar Expression;

inject: expression;

expression:
		expression POW expression
		| expression (times | div) expression
		| expression (plus | minus) expression
		| LPAREN expression RPAREN
		| atom; 

	atom: scientific | variable;

	scientific: SCIENTIFIC_NUMBER;

	variable: VARIABLE;

	relop: EQ | GT | LT;

	VARIABLE: VALID_ID_START VALID_ID_CHAR*;

	fragment VALID_ID_START: ('a' .. 'z') | ('A' .. 'Z') | '_';

	fragment VALID_ID_CHAR: VALID_ID_START | ('0' .. '9');

	//The NUMBER part gets its potential sign from "(PLUS | MINUS)* atom" in the expression rule
	SCIENTIFIC_NUMBER: NUMBER (E SIGN? UNSIGNED_INTEGER)?;

	fragment NUMBER: ('0' .. '9')+ ('.' ('0' .. '9')+)?;

	fragment UNSIGNED_INTEGER: ('0' .. '9')+;

	fragment E: 'E' | 'e';

	fragment SIGN: ('+' | '-');

	LPAREN: '(';

	RPAREN: ')';

	plus: '+';

	minus: '-';

	times: '*';

	div: '/';

	GT: '>';

	LT: '<';

	EQ: '=';

	POINT: '.';

	POW: '^';

	WS: [ \r\n\t]+ -> skip;