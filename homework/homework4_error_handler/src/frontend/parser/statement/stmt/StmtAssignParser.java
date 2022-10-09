package frontend.parser.statement.stmt;

import frontend.lexer.Token;
import frontend.lexer.TokenListIterator;
import frontend.lexer.TokenType;
import frontend.parser.expression.Exp;
import frontend.parser.expression.ExpParser;
import frontend.parser.expression.primaryexp.LVal;
import frontend.parser.expression.primaryexp.LValParser;
import middle.symbol.SymbolTable;

public class StmtAssignParser {
    private TokenListIterator iterator;
    /* StmtAssign Attributes */
    private LVal lval = null;
    private Token eq; // '='
    private Exp exp;
    private Token semicn; // ';'
    private SymbolTable curSymbolTable;

    public StmtAssignParser(TokenListIterator iterator) {
        this.iterator = iterator;
    }

    public StmtAssignParser(TokenListIterator iterator, SymbolTable curSymbolTable) {
        this.iterator = iterator;
        this.curSymbolTable = curSymbolTable;
    }

    public StmtAssign parseStmtAssign() {
        // LValParser lvalParser = new LValParser(this.iterator);
        LValParser lvalParser = new LValParser(this.iterator, this.curSymbolTable);
        this.lval = lvalParser.parseLVal();
        this.eq = this.iterator.readNextToken();
        if (!this.eq.getType().equals(TokenType.ASSIGN)) {
            System.out.println("EXPECT = HERE");
        }
        // ExpParser expParser = new ExpParser(this.iterator);
        ExpParser expParser = new ExpParser(this.iterator, this.curSymbolTable);
        this.exp = expParser.parseExp();
        this.semicn = this.iterator.readNextToken();
        StmtAssign stmtAssign = new StmtAssign(this.lval, this.eq, this.exp, this.semicn);
        return stmtAssign;
    }
}
