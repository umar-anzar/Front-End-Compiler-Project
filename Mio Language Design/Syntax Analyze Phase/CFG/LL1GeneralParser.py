class CFG:

    def __init__(self,start,cfg,selectionSet) -> None:

        self.index = 0
        self.word = []
        self.start = start
        self.cfg = cfg
        self.ss = selectionSet
        self.theEnd = False

    def reset(self):

        self.index = 0
        self.word = []
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

        selectionSet = self.ss[start]
        if self.word[self.index] not in selectionSet:
            print(self.word[self.index],self.word[self.index] in selectionSet)
            return False

        for OR in rule:

            for k,terminal in enumerate(OR):

                accept = False
                if terminal[0] == '<': # if word is non-terminal

                    accept = self.parser(terminal) # Recursion starts

                    if not(accept): # Non Terminal failed to parse hence *break* to find other rule of same non terminal

                        if k>0: 
                            self.theEnd = True
                            #First Non Terminal return true this production rule is selected then second 
                            # or further Non Terminal returns false so no backtrack, Failed to parse

                        break

                elif terminal == self.word[self.index]: # If word match with Terminal in a rule

                    self.index += 1
                    accept = True

                else: # If word doesn't match with Terminal in a rule

                    # Word can be neglected if the terminal is null else break to find other rule of same Non terminal
                    if terminal == 'null': #return True instead break because it is end condition hence just returns True
                        if self.word[self.index] in selectionSet:
                            
                            return True
                        else:
                            
                            self.theEnd = True
                            return False

                    #First set of rule is true for word then second or further word false so no backtrack, Failed to parse
                    if k>0: 
                        self.theEnd = True
                    
                    break
            
            # This condition is on mean word wont parse so straightly Return False
            if self.theEnd:
                return False
                
            # this return True in order to skip searching in Next OR (production rule of same Non Terminal)
            # and it is also the here to give True if word parse correctly
            if accept:
                return True

            
        return False


"""
{ dt id = id } ;
{ dt id { dt id } } ;

<X> -> S ;
<S> -> { <A> }
<A> -> dt id <D> 
<D> -> <B> <C> | <S> | null
<B> -> = | += | *=
<C> -> intConst | floatConst
"""
selectionSet = {
            "<X>" : ['{','$'],

            "<S>" : ['{'],

            "<A>" : ['dt'],

            "<D>" : ["=", "+=", "*=",'{',"}"],

            "<B>" : ["=", "+=", "*="],

            "<C>" : ["intConst", "floatConst"]
}
A = CFG("<X>",
        {
            "<X>" : [   ["<S>",";"] ],

            "<S>" : [   ["{","<A>","}"] ],

            "<A>" : [   ["dt","id","<D>"] ],

            "<D>" : [   ["<B>","<C>"], ["<S>"], ["null"] ],

            "<B>" : [   ["="], ["+="], ["*="] ],

            "<C>" : [   ["intConst"], ["floatConst"] ]
        },
        selectionSet
    )
x= "{ dt id { dt id { dt id { dt id { dt id  } } }  } } ;"

print(A.validate(x.split()+["$"]))

x= "{ dt id  { dt id = intConst } } ;"

print(A.validate(x.split()+["$"]))



# A = CFG("<X>",
#         {
#             "<X>" : [ ["a","b"], ['c','d'] ]
#         }
#     )

# x= "c d"
# print(A.validate(x.split()+["$"]))