package frontend.lexer;

import java.util.ArrayList;

public class TokenList {
    private ArrayList<Token> tokens;

    public TokenList() {
        this.tokens = new ArrayList<>();
    }

    public void addToken(Token token) {
        this.tokens.add(token);
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(token.syntaxOutput());
            //sb.append(token.getType() + " " + token.getContent() + "\n");
        }
        return sb.toString();
    }
}
