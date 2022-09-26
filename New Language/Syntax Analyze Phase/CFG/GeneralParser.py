"""
{ dt id = id } ;
{ dt id { dt id } } ;

0X -> S ;
1S -> { <A> }
2A -> dt id <D> 
3D -> <B> <C> | <S> | null
4B -> = | += | *=
5C -> intConst | floatConst
"""

class CFG:
    def __init__(self) -> None:
        self.index = 0
        self.word = []
        self.cfg = [
            [ ["? 1 S",";"] ],
            [ ["{","? 2 A","}"] ],
            [ ["dt","id","? 3 D"] ],
            [ ["? 4 B","? 5 C"], ["? 1 S"],["null"] ],
            [ ["="],["+="],["*="] ],
            [ ["intConst"], ["floatConst"] ]
        ]
        self.isOr = True

    def validate(self,word):
        self.word = word
        result = False
        if self.parser(0):
            if len(word)-1 == self.index:
                result = True
        self.word = ""
        self.index = 0
        return result,word[:-1]

    def parser(self,start):
        F = False
        rule = self.cfg[start]
        for OR in rule:
            for terminal in OR:
                F = False
                if terminal[0] == '?':#if non terminal
                    F = self.parser(int(terminal.split(" ")[1]))
                elif terminal == self.word[self.index]:
                    self.index += 1
                    F = True
                else:
                    if terminal == 'null':
                        F = True
                        return True
                    break
            
            if F:
                return True
            if len(self.word) - 1 <= self.index:
                return True

        return False

A = CFG()
x= "{ dt id { dt id { dt id } } } ;"

print(A.validate(x.split(" ")+["$"]))
