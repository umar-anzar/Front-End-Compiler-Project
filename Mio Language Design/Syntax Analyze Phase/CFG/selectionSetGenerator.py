import json

string = """

id? dot ? ( ?inc_dec ? = ?cma 




"""

arr = []
subarr = []
for i in string.split("\n"):
    
    if i == "":
        if len(subarr) != 0:
            arr.append(subarr)
            subarr = []
    else:
        subarr.append(i)

for i in range(len(arr)):
    arr[i] = [i.strip() for i in arr[i][0].split("?")]

for x in arr:
    y = json.dumps(x)
    Z = ""
    condoff = 0
    for i in y:
        if i == '\"':
            condoff += 1
            if condoff == 2:
                condoff = 0
        if condoff != 1:
            if i == '[':
                Z+= '{'
                continue
            elif i == ']':
                Z+= '}'
                continue
        Z+=i

    print("sSet.put(\"\", new String[][] { "+Z+", {} });")


