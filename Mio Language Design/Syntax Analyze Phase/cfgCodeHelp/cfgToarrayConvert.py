import json

string = """
<PACKAGE>   -> package id <IMP_DOT> ;
<IMPORTS>   -> import id <IMP_DOT> 
<IMP_DOT>   -> dot <ID_STAR> | ;
<ID_STAR>   -> id <IMP_DOT> | power ; | ;
"""

for strx in string.split('\n'):
    try:
        #strx = "<B> -> [=] | += | *="
        y = strx.split('->')


        x = []
        for i in y[1].split('|'):
            x.append(i)

        for i,j in enumerate(x):
            x[i] = j.split()
        x = json.dumps(x)

        Z = ""
        condoff = 0
        for i in x:
            if i == '\"':
                condoff += 1
                if condoff == 2:
                    condoff = 0
            if condoff != 1:
                if i == '[':
                    Z+= '{ '
                    continue
                elif i == ']':
                    Z+= ' }'
                    continue
            Z+=i


        print('cfg.put(\"'+str(y[0]).strip()+'\",',end="")
        print(' new String[][]',end=" ")
        print(Z,end=" );")
        print()
        #("<X>", new String[][] { {"<S>",";"} } );

    except IndexError:
        print()
    