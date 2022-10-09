package frontend.parser.statement.stmt;

import frontend.lexer.Token;
import frontend.lexer.TokenListIterator;
import frontend.lexer.TokenType;
import frontend.parser.expression.Exp;
import frontend.parser.expression.ExpParser;
import frontend.parser.terminal.FormatString;
import middle.error.Error;
import middle.error.ErrorTable;
import middle.error.ErrorType;
import middle.symbol.SymbolTable;

import java.util.ArrayList;

public class StmtPrintParser {
    private TokenListIterator iterator;
    /* StmtPrint Attributes */
    private Token printf; // 'printf'
    private Token leftParent; // '('
    private FormatString formatString;
    private ArrayList<Token> commmas = new ArrayList<>(); // ','
    private ArrayList<Exp> exps = new ArrayList<>();
    private Token rightParent; // ')'
    private Token semicn; // ';'
    private SymbolTable curSymbolTable;

    public StmtPrintParser(TokenListIterator iterator) {
        this.iterator = iterator;
    }

    public StmtPrintParser(TokenListIterator iterator, SymbolTable curSymbolTable) {
        this.iterator = iterator;
        this.curSymbolTable = curSymbolTable;
    }

    public StmtPrint parseStmtPrint() {
        this.commmas = new ArrayList<>();
        this.exps = new ArrayList<>();
        this.printf = this.iterator.readNextToken();
        if (!this.printf.getType().equals(TokenType.PRINTFTK)) {
            System.out.println("EXPEXT PRINTF IN STMTPRINTFPARSER");
        }
        this.leftParent = this.iterator.readNextToken();
        if (!this.leftParent.getType().equals(TokenType.LPARENT)) {
            System.out.println("EXPECT LPARENT IN STMTPRINTFPARSER");
        }
        this.formatString = new FormatString(this.iterator.readNextToken());
        Token token = this.iterator.readNextToken();
        while (token.getType().equals(TokenType.COMMA)) {
            this.commmas.add(token);
            ExpParser expParser = new ExpParser(this.iterator);
            this.exps.add(expParser.parseExp());
            token = this.iterator.readNextToken();
        }
        this.rightParent = token;
        /* 处理j类异常：缺少 ) */
        handleJError(this.rightParent);
        this.semicn = this.iterator.readNextToken();
        /* 处理i类异常：缺少 ; */
        handleIError(this.semicn);
        /* 处理l类异常：格式字符与表达式个数不匹配 */
        handleLError(this.formatString);
        StmtPrint stmtPrint = new StmtPrint(this.printf, this.leftParent,
                this.formatString, this.commmas, this.exps, this.rightParent, this.semicn);
        return stmtPrint;
    }

    private void handleIError(Token token) {
        if (token.getType().equals(TokenType.SEMICN)) {
            this.iterator.unReadToken(2); // 后退两格以方便确定分号上一个非终结符位置
            Error error = new Error(this.iterator.readNextToken().getLineNum(),
                    ErrorType.MISSING_SEMICN);
            ErrorTable.addError(error);
        }
    }

    private void handleJError(Token token) {
        if (!token.getType().equals(TokenType.RPARENT)) {
            this.iterator.unReadToken(2);
            Error error = new Error(this.iterator.readNextToken().getLineNum(),
                    ErrorType.MISSING_R_PARENT);
            ErrorTable.addError(error);
        }
    }

    private void handleLError(FormatString formatString) {
        String target = "%d";
        String formatContent = formatString.getContent();
        /* formatString 中格式字符的个数 */
        int cnt1 = (formatContent.length() - formatContent.replace(target, "").length())
                / (target.length());
        /* 表达式个数 */
        int cnt2 = this.exps.size();
        if (cnt1 != cnt2) {
            Error error = new Error(this.printf.getLineNum(), ErrorType.MISMATCCH_PRINTF);
            ErrorTable.addError(error);
        }
    }
}
