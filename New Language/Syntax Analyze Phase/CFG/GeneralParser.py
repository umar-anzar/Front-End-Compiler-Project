class CFG:
    def __init__(self,start,cfg) -> None:
        self.index = 0
        self.word = []
        self.start = start
        self.cfg = cfg
        self.isOr = True
        self.theEnd = False

    def reset(self):
        self.index = 0
        self.word = []
        self.isOr = True
        self.theEnd = False

    def validate(self,word):
        self.word = word
        result = False
        if self.parser("<X>"):
            if len(word)-1 == self.index:
                result = True
        self.reset() #Reset parameters to parse more
        return result,word[:-1]

    def parser(self,start):
        accept = False
        rule = self.cfg[start]
        for OR in rule:
            for k,terminal in enumerate(OR):
                #print(rule)
                accept = False
                if terminal[0] == '<':#if non terminal
                    accept = self.parser(terminal)
                    if not(accept):
                        if k>0: 
                            self.theEnd = True
                            #We have enter in a production rule and there is no BACKTRACK then no BACK
                            #Because it failed to parse the other terminal
                        break
                elif terminal == self.word[self.index]:
                    self.index += 1
                    accept = True
                else:
                    if terminal == 'null':
                        accept = True
                        return True
                    break
            
            if self.theEnd:
                return False
                
            if accept:
                return True

            
        return False


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
A = CFG("<X>",
        {
            "<X>" : [ ["<S>",";"] ],
            "<S>" : [ ["{","<A>","}"] ],
            "<A>" : [ ["dt","id","<D>"] ],
            "<D>" : [ ["<B>","<C>"], ["<S>"],["null"] ],
            "<B>" : [ ["="],["+="],["*="] ],
            "<C>" : [ ["intConst"], ["floatConst"] ]
        }
    )
x= "{ dt id { dt id { dt id { dt id { dt id *= floatConst } } }  } } ;"

print(A.validate(x.split()+["$"]))

x= "{ dt id += { dt id = intConst } ;"

print(A.validate(x.split()+["$"]))
