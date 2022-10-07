import json

string = """
<FN_DEC>    -> def <RET_TYPE> id <FN_ST> <THROWS> { <MST> }
<FN_ST>     -> ( <PAR> )
<PAR>       -> <DT_ID> id <PAR_LIST>   | null
<PAR_LIST>  -> , <DT_ID> id <PAR_LIST> | null

<DT_ID>         -> <TYPE> <ARR_TYPE>
<RET_TYPE>      -> <DT_ID> | null
<TYPE>          -> id | dt | str 
<ARR_TYPE>      -> [ ] <ARR_TYPE> | null
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
    