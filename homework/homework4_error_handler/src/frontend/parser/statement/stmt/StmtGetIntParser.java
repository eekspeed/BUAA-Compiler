package frontend.parser.statement.stmt;

import frontend.lexer.Token;
import frontend.lexer.TokenListIterator;
import frontend.parser.expression.primaryexp.LVal;
import frontend.parser.expression.primaryexp.LValParser;
import middle.symbol.SymbolTable;

public class StmtGetIntParser {
    private TokenListIterator iterator;
    /* StmtGetint Attributes */
    private LVal lval = null;
    private Token eq = null; // '='
    private Token getint = null; // 'getint'
    private Token leftParent = null; // '('
    private Token rightParent = null; // ')'
    private Token semicn = null; // ';'
    private SymbolTable curSymbolTable;

    public StmtGetIntParser(TokenListIterator iterator) {
        this.iterator = iterator;
    }

    public StmtGetIntParser(TokenListIterator iterator, SymbolTable curSymbolTable) {
        this.iterator = iterator;
        this.curSymbolTable = curSymbolTable;
    }

    public StmtGetint parseStmtGetInt() {
        // LValParser lvalParser = new LValParser(this.iterator);
        LValParser lvalParser = new LValParser(this.iterator, this.curSymbolTable);
        this.lval = lvalParser.parseLVal();
        this.eq = this.iterator.readNextToken();
        this.getint = this.iterator.readNextToken();
        this.leftParent = this.iterator.readNextToken();
        this.rightParent = this.iterator.readNextToken();
        this.semicn = this.iterator.readNextToken();
        StmtGetint stmtGetint = new StmtGetint(this.lval, this.eq, this.getint,
                this.leftParent, this.rightParent, this.semicn);
        return stmtGetint;
    }
}
