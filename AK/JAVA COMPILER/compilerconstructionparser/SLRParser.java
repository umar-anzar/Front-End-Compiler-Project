import java.util.Stack;
import java.util.StringTokenizer;

public class SLRParser {
    String inputString;
    
    public SLRParser() {
    }

    public void errorMessage() {
        System.out.println("Syntax Error");
    }

    public int getIndex(String token){
        int indexA = -1;
        switch (token) {
            case "id":
                indexA = 0;
                break;
            case "+":
                indexA = 1;
                break;
            case "*":
                indexA = 2;
                break;
            case "(":
                indexA = 3;
                break;
            case ")":
                indexA = 4;
                break;
            case "$":
                indexA = 5;
                break;
            case "E":
                indexA = 6;
                break;
            case "T":
                indexA = 7;
                break;
            case "F":
                indexA = 8;
                break;
            default:
                errorMessage();
        }
        return indexA;
    }

    public String[][] ParseTable() {
        String[][] parseTable = new String[12][9];
        //id
        parseTable[0][0] = "s5";
        parseTable[4][0] = "s5";
        parseTable[6][0] = "s5";
        parseTable[7][0] = "s5";

        //+
        parseTable[1][1] = "s6";
        parseTable[2][1] = "r2";
        parseTable[3][1] = "r4";
        parseTable[5][1] = "r6";
        parseTable[8][1] = "s6";
        parseTable[9][1] = "r1";
        parseTable[10][1] = "r3";
        parseTable[11][1] = "r5";

        //*
        parseTable[2][2] = "s7";
        parseTable[3][2] = "r4";
        parseTable[5][2] = "r6";
        parseTable[9][2] = "s7";
        parseTable[10][2] = "r3";
        parseTable[11][2] = "r5";

        //(
        parseTable[0][3] = "s4";
        parseTable[4][3] = "s4";
        parseTable[6][3] = "s4";
        parseTable[7][3] = "s4";

        //)
        parseTable[2][4] = "r2";
        parseTable[3][4] = "r4";
        parseTable[5][4] = "r6";
        parseTable[8][4] = "s11";
        parseTable[9][4] = "r1";
        parseTable[10][4] = "r3";
        parseTable[11][4] = "r5";

        //$
        parseTable[1][5] = "acc";
        parseTable[2][5] = "r2";
        parseTable[3][5] = "r4";
        parseTable[5][5] = "r6";
        parseTable[9][5] = "r1";
        parseTable[10][5] = "r3";
        parseTable[11][5] = "r5";

        //E
        parseTable[0][6] = "1";
        parseTable[4][6] = "8";

        //T
        parseTable[0][7] = "2";
        parseTable[4][7] = "2";
        parseTable[6][7] = "9";

        //F
        parseTable[0][8] = "3";
        parseTable[4][8] = "3";
        parseTable[6][8] = "3";
        parseTable[7][8] = "10";

        return parseTable;
    }

    public void Parse(String inputString){
        String[][] parseTable = ParseTable();
        String InputString = inputString;
        StringTokenizer st = new StringTokenizer(InputString);
        Stack s = new Stack();
        s.push("0");
        String str = st.nextToken();
        do {
            int index = getIndex(str);
            int peekValue = Integer.parseInt((String) s.peek());
            String action = parseTable[peekValue][index];
            if (action != null){
            switch (action.charAt(0)) {
                case 's':
                    String number = parseTable[peekValue][index].substring(1);
                    s.push(str);
                    s.push(number);
                    str = st.nextToken();
                    break;

                case 'r':
                    number = parseTable[peekValue][index].substring(1);
                    int popTimes = 6;
                    if (Integer.parseInt(number) % 2 == 0) {
                        popTimes = 2;
                    }
                    for (int i = 0; i < popTimes; i++) {
                        s.pop();
                    }

                    String ls = "";
                    switch (Integer.parseInt(number)) {
                        case 1:
                            ls = "E";
                            break;
                        case 2:
                            ls = "E";
                            break;
                        case 3:
                            ls = "T";
                            break;
                        case 4:
                            ls = "T";
                            break;
                        case 5:
                            ls = "F";
                            break;
                        case 6:
                            ls = "F";
                            break;
                    }

                    int indexA = -1;
                    switch (ls.charAt(0)) {
                        case 'E':
                            indexA = 6;
                            break;
                        case 'T':
                            indexA = 7;
                            break;
                        case 'F':
                            indexA = 8;
                            break;
                    }
                    int newPeekVal = Integer.parseInt((String) s.peek());
                    int num = Integer.parseInt(parseTable[newPeekVal][indexA]);
                    s.push(ls + "");
                    s.push(num + "");

                    break;
                case 'a':
                    System.out.printf("[STATUS]: SUCCESFULLY COMPILED\n");
                    return;
            }
            }
            else{
                System.out.println("[COMPILATION-ERROR]: THIS LINE IS NOT ACCEPTED BY THE GIVEN CFG\n");
                return;
            }
        } while (true);
    }
}
