// Generated from /opt/git/repositories/grammars-v4/sql/mysql-java/src/main/antlr4/io/TestGrammarParser.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TestGrammarParser extends io.BaseParser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EQUAL_SIGN=1, ADDDATE_SYMBOL=2, BIT_AND_SYMBOL=3, BIT_OR_SYMBOL=4, BIT_XOR_SYMBOL=5, 
		OPEN_PAREN=6, CLOSE_PAREN=7, WHITESPACE=8, IDENTIFIER=9;
	public static final int
		RULE_attrib_list = 0, RULE_attrib = 1, RULE_variable_name = 2, RULE_function = 3;
	public static final String[] ruleNames = {
		"attrib_list", "attrib", "variable_name", "function"
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

	@Override
	public String getGrammarFileName() { return "TestGrammarParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TestGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Attrib_listContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(TestGrammarParser.EOF, 0); }
		public List<AttribContext> attrib() {
			return getRuleContexts(AttribContext.class);
		}
		public AttribContext attrib(int i) {
			return getRuleContext(AttribContext.class,i);
		}
		public Attrib_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrib_list; }
	}

	public final Attrib_listContext attrib_list() throws RecognitionException {
		Attrib_listContext _localctx = new Attrib_listContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_attrib_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(9); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(8);
				attrib();
				}
				}
				setState(11); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENTIFIER );
			setState(13);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttribContext extends ParserRuleContext {
		public Variable_nameContext variable_name() {
			return getRuleContext(Variable_nameContext.class,0);
		}
		public TerminalNode EQUAL_SIGN() { return getToken(TestGrammarParser.EQUAL_SIGN, 0); }
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public TerminalNode OPEN_PAREN() { return getToken(TestGrammarParser.OPEN_PAREN, 0); }
		public TerminalNode CLOSE_PAREN() { return getToken(TestGrammarParser.CLOSE_PAREN, 0); }
		public AttribContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrib; }
	}

	public final AttribContext attrib() throws RecognitionException {
		AttribContext _localctx = new AttribContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_attrib);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			variable_name();
			setState(16);
			match(EQUAL_SIGN);
			setState(17);
			function();
			setState(18);
			match(OPEN_PAREN);
			setState(19);
			match(CLOSE_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Variable_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(TestGrammarParser.IDENTIFIER, 0); }
		public Variable_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_name; }
	}

	public final Variable_nameContext variable_name() throws RecognitionException {
		Variable_nameContext _localctx = new Variable_nameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variable_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode ADDDATE_SYMBOL() { return getToken(TestGrammarParser.ADDDATE_SYMBOL, 0); }
		public TerminalNode BIT_AND_SYMBOL() { return getToken(TestGrammarParser.BIT_AND_SYMBOL, 0); }
		public TerminalNode BIT_OR_SYMBOL() { return getToken(TestGrammarParser.BIT_OR_SYMBOL, 0); }
		public TerminalNode BIT_XOR_SYMBOL() { return getToken(TestGrammarParser.BIT_XOR_SYMBOL, 0); }
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADDDATE_SYMBOL) | (1L << BIT_AND_SYMBOL) | (1L << BIT_OR_SYMBOL) | (1L << BIT_XOR_SYMBOL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13\34\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\3\2\6\2\f\n\2\r\2\16\2\r\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\5\2\2\6\2\4\6\b\2\3\3\2\4\7\2\30\2\13\3\2\2"+
		"\2\4\21\3\2\2\2\6\27\3\2\2\2\b\31\3\2\2\2\n\f\5\4\3\2\13\n\3\2\2\2\f\r"+
		"\3\2\2\2\r\13\3\2\2\2\r\16\3\2\2\2\16\17\3\2\2\2\17\20\7\2\2\3\20\3\3"+
		"\2\2\2\21\22\5\6\4\2\22\23\7\3\2\2\23\24\5\b\5\2\24\25\7\b\2\2\25\26\7"+
		"\t\2\2\26\5\3\2\2\2\27\30\7\13\2\2\30\7\3\2\2\2\31\32\t\2\2\2\32\t\3\2"+
		"\2\2\3\r";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}