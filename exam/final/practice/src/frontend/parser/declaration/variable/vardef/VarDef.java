package frontend.parser.declaration.variable.vardef;

import frontend.parser.SyntaxNode;
import frontend.parser.terminal.Ident;

public class VarDef implements SyntaxNode {
    private final String name = "<VarDef>";
    private VarDefEle varDefEle;

    public VarDef(VarDefEle varDefEle) {
        this.varDefEle = varDefEle;
    }

    @Override
    public String syntaxOutput() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.varDefEle.syntaxOutput());
        sb.append(this.name + "\n");
        return sb.toString();
    }

    public Ident getIdent() {
        return this.varDefEle.getIdent();
    }

    public VarDefEle getVarDefEle() {
        return varDefEle;
    }
}
