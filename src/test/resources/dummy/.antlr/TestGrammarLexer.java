// Generated from /opt/git/repositories/grammars-v4/sql/mysql-java/src/main/antlr4/io/TestGrammarLexer.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TestGrammarLexer extends io.BaseLexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EQUAL_SIGN=1, ADDDATE_SYMBOL=2, BIT_AND_SYMBOL=3, BIT_OR_SYMBOL=4, BIT_XOR_SYMBOL=5, 
		OPEN_PAREN=6, CLOSE_PAREN=7, WHITESPACE=8, IDENTIFIER=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"EQUAL_SIGN", "ADDDATE_SYMBOL", "BIT_AND_SYMBOL", "BIT_OR_SYMBOL", "BIT_XOR_SYMBOL", 
		"OPEN_PAREN", "CLOSE_PAREN", "WHITESPACE", "IDENTIFIER", "A", "B", "C", 
		"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", 
		"R", "S", "T", "U", "V", "W", "X", "Y", "Z", "DIGIT", "DIGITS", "HEXDIGIT", 
		"LETTER_WHEN_UNQUOTED", "LETTER_WHEN_UNQUOTED_NO_DIGIT", "LETTER_WITHOUT_FLOAT_PART"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", null, null, null, null, "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "EQUAL_SIGN", "ADDDATE_SYMBOL", "BIT_AND_SYMBOL", "BIT_OR_SYMBOL", 
		"BIT_XOR_SYMBOL", "OPEN_PAREN", "CLOSE_PAREN", "WHITESPACE", "IDENTIFIER"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public TestGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "TestGrammarLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 1:
			ADDDATE_SYMBOL_action((RuleContext)_localctx, actionIndex);
			break;
		case 2:
			BIT_AND_SYMBOL_action((RuleContext)_localctx, actionIndex);
			break;
		case 3:
			BIT_OR_SYMBOL_action((RuleContext)_localctx, actionIndex);
			break;
		case 4:
			BIT_XOR_SYMBOL_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ADDDATE_SYMBOL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:

				 setType(determineFunction(ADDDATE_SYMBOL)); 
				
			break;
		}
	}
	private void BIT_AND_SYMBOL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
				setType(determineFunction(BIT_AND_SYMBOL)); 
			break;
		}
	}
	private void BIT_OR_SYMBOL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 setType(determineFunction(BIT_OR_SYMBOL)); 
			break;
		}
	}
	private void BIT_XOR_SYMBOL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 setType(determineFunction(BIT_XOR_SYMBOL)); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13\u00eb\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\6\n\u0084\n\n\r\n\16\n\u0085"+
		"\3\n\3\n\3\n\7\n\u008b\n\n\f\n\16\n\u008e\13\n\5\n\u0090\n\n\3\n\6\n\u0093"+
		"\n\n\r\n\16\n\u0094\3\n\3\n\7\n\u0099\n\n\f\n\16\n\u009c\13\n\3\n\3\n"+
		"\7\n\u00a0\n\n\f\n\16\n\u00a3\13\n\5\n\u00a5\n\n\3\13\3\13\3\f\3\f\3\r"+
		"\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3"+
		"#\3$\3$\3%\3%\3&\6&\u00de\n&\r&\16&\u00df\3\'\3\'\3(\3(\5(\u00e6\n(\3"+
		")\3)\3*\3*\2\2+\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\2\27\2\31"+
		"\2\33\2\35\2\37\2!\2#\2%\2\'\2)\2+\2-\2/\2\61\2\63\2\65\2\67\29\2;\2="+
		"\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2\3\2!\5\2\13\f\16\17\"\"\4\2GGgg\4"+
		"\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLl"+
		"l\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2"+
		"UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\3\2\62"+
		";\5\2\62;CHch\7\2&&C\\aac|\u0082\1\t\2&&CFH\\aacfh|\u0082\1\2\u00d4\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\3U\3\2\2\2\5W\3\2\2\2\7`\3\2"+
		"\2\2\ti\3\2\2\2\13q\3\2\2\2\rz\3\2\2\2\17|\3\2\2\2\21~\3\2\2\2\23\u00a4"+
		"\3\2\2\2\25\u00a6\3\2\2\2\27\u00a8\3\2\2\2\31\u00aa\3\2\2\2\33\u00ac\3"+
		"\2\2\2\35\u00ae\3\2\2\2\37\u00b0\3\2\2\2!\u00b2\3\2\2\2#\u00b4\3\2\2\2"+
		"%\u00b6\3\2\2\2\'\u00b8\3\2\2\2)\u00ba\3\2\2\2+\u00bc\3\2\2\2-\u00be\3"+
		"\2\2\2/\u00c0\3\2\2\2\61\u00c2\3\2\2\2\63\u00c4\3\2\2\2\65\u00c6\3\2\2"+
		"\2\67\u00c8\3\2\2\29\u00ca\3\2\2\2;\u00cc\3\2\2\2=\u00ce\3\2\2\2?\u00d0"+
		"\3\2\2\2A\u00d2\3\2\2\2C\u00d4\3\2\2\2E\u00d6\3\2\2\2G\u00d8\3\2\2\2I"+
		"\u00da\3\2\2\2K\u00dd\3\2\2\2M\u00e1\3\2\2\2O\u00e5\3\2\2\2Q\u00e7\3\2"+
		"\2\2S\u00e9\3\2\2\2UV\7?\2\2V\4\3\2\2\2WX\5\25\13\2XY\5\33\16\2YZ\5\33"+
		"\16\2Z[\5\33\16\2[\\\5\25\13\2\\]\5;\36\2]^\5\35\17\2^_\b\3\2\2_\6\3\2"+
		"\2\2`a\5\27\f\2ab\5%\23\2bc\5;\36\2cd\7a\2\2de\5\25\13\2ef\5/\30\2fg\5"+
		"\33\16\2gh\b\4\3\2h\b\3\2\2\2ij\5\27\f\2jk\5%\23\2kl\5;\36\2lm\7a\2\2"+
		"mn\5\61\31\2no\5\67\34\2op\b\5\4\2p\n\3\2\2\2qr\5\27\f\2rs\5%\23\2st\5"+
		";\36\2tu\7a\2\2uv\5C\"\2vw\5\61\31\2wx\5\67\34\2xy\b\6\5\2y\f\3\2\2\2"+
		"z{\7*\2\2{\16\3\2\2\2|}\7+\2\2}\20\3\2\2\2~\177\t\2\2\2\177\u0080\3\2"+
		"\2\2\u0080\u0081\b\t\6\2\u0081\22\3\2\2\2\u0082\u0084\5K&\2\u0083\u0082"+
		"\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0087\u008f\t\3\2\2\u0088\u008c\5Q)\2\u0089\u008b\5O(\2"+
		"\u008a\u0089\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d"+
		"\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0088\3\2\2\2\u008f"+
		"\u0090\3\2\2\2\u0090\u00a5\3\2\2\2\u0091\u0093\5K&\2\u0092\u0091\3\2\2"+
		"\2\u0093\u0094\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096"+
		"\3\2\2\2\u0096\u009a\5S*\2\u0097\u0099\5O(\2\u0098\u0097\3\2\2\2\u0099"+
		"\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u00a5\3\2"+
		"\2\2\u009c\u009a\3\2\2\2\u009d\u00a1\5Q)\2\u009e\u00a0\5O(\2\u009f\u009e"+
		"\3\2\2\2\u00a0\u00a3\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4\u0083\3\2\2\2\u00a4\u0092\3\2"+
		"\2\2\u00a4\u009d\3\2\2\2\u00a5\24\3\2\2\2\u00a6\u00a7\t\4\2\2\u00a7\26"+
		"\3\2\2\2\u00a8\u00a9\t\5\2\2\u00a9\30\3\2\2\2\u00aa\u00ab\t\6\2\2\u00ab"+
		"\32\3\2\2\2\u00ac\u00ad\t\7\2\2\u00ad\34\3\2\2\2\u00ae\u00af\t\3\2\2\u00af"+
		"\36\3\2\2\2\u00b0\u00b1\t\b\2\2\u00b1 \3\2\2\2\u00b2\u00b3\t\t\2\2\u00b3"+
		"\"\3\2\2\2\u00b4\u00b5\t\n\2\2\u00b5$\3\2\2\2\u00b6\u00b7\t\13\2\2\u00b7"+
		"&\3\2\2\2\u00b8\u00b9\t\f\2\2\u00b9(\3\2\2\2\u00ba\u00bb\t\r\2\2\u00bb"+
		"*\3\2\2\2\u00bc\u00bd\t\16\2\2\u00bd,\3\2\2\2\u00be\u00bf\t\17\2\2\u00bf"+
		".\3\2\2\2\u00c0\u00c1\t\20\2\2\u00c1\60\3\2\2\2\u00c2\u00c3\t\21\2\2\u00c3"+
		"\62\3\2\2\2\u00c4\u00c5\t\22\2\2\u00c5\64\3\2\2\2\u00c6\u00c7\t\23\2\2"+
		"\u00c7\66\3\2\2\2\u00c8\u00c9\t\24\2\2\u00c98\3\2\2\2\u00ca\u00cb\t\25"+
		"\2\2\u00cb:\3\2\2\2\u00cc\u00cd\t\26\2\2\u00cd<\3\2\2\2\u00ce\u00cf\t"+
		"\27\2\2\u00cf>\3\2\2\2\u00d0\u00d1\t\30\2\2\u00d1@\3\2\2\2\u00d2\u00d3"+
		"\t\31\2\2\u00d3B\3\2\2\2\u00d4\u00d5\t\32\2\2\u00d5D\3\2\2\2\u00d6\u00d7"+
		"\t\33\2\2\u00d7F\3\2\2\2\u00d8\u00d9\t\34\2\2\u00d9H\3\2\2\2\u00da\u00db"+
		"\t\35\2\2\u00dbJ\3\2\2\2\u00dc\u00de\5I%\2\u00dd\u00dc\3\2\2\2\u00de\u00df"+
		"\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0L\3\2\2\2\u00e1"+
		"\u00e2\t\36\2\2\u00e2N\3\2\2\2\u00e3\u00e6\5I%\2\u00e4\u00e6\5Q)\2\u00e5"+
		"\u00e3\3\2\2\2\u00e5\u00e4\3\2\2\2\u00e6P\3\2\2\2\u00e7\u00e8\t\37\2\2"+
		"\u00e8R\3\2\2\2\u00e9\u00ea\t \2\2\u00eaT\3\2\2\2\f\2\u0085\u008c\u008f"+
		"\u0094\u009a\u00a1\u00a4\u00df\u00e5\7\3\3\2\3\4\3\3\5\4\3\6\5\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}