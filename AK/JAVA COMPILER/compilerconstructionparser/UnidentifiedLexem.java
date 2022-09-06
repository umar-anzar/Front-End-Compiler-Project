public class UnidentifiedLexem {
    String lineNo, tokenName, errMsg;
    
    UnidentifiedLexem(String lineNo, String tokenName, String errMsg) {
        this.lineNo = lineNo;
        this.tokenName = tokenName;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        String LineNo = this.lineNo;
        String Token = this.tokenName;
        String ErrMsg = this.errMsg;

        return "\nError on Line: " + LineNo + "\nUnrecognized Token: " + Token + "\n[ERROR]: " + ErrMsg;
    }
}
