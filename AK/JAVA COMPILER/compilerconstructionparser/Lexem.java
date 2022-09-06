public class Lexem {
    String name, tokenName, value;
    
    Lexem(String name, String tokenName, String value) {
        this.name = name;
        this.tokenName = tokenName;
        this.value = value;
    }

    @Override
    public String toString() {
        String Name = this.name;
        String Token = this.tokenName;
        String Value = this.value;
        

        String space1 = "";
        String space2 = "";

        for(int i = 0; i < (30 - Name.length()); i++){
            space1 = space1 + " ";
        }
        for(int i = 0; i < (30 - Token.length()); i++){
            space2 = space2 + " ";
        }

        return Name + space1 + Token + space2 + Value;
    }
}
