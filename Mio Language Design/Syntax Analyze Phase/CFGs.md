## Syntax Analyze Phase

| Heading no | Name                                | CFG no | Name                           |
|------------|-------------------------------------|--------|--------------------------------|
| 1          | Start Structure                     | 14     | Object Declaration             |
| 2          | Body                                | 15     | Array Declaration              |
| 3          | Single and Multi Statements         | 16     | Global Variable Declaration    |
| 4          | Begin the Main Function             | 17     | Attribute Declaration in class |
| 5          | Package and Import                  | 18     | Expression                     |
| 6          | Reusable CFG                        | 19     | Operands                       |
| 7          | Access Modifier                     | 20     | Increment Decrement            |
| 8          | Function Statement                  | 21     | Constant                       |
| 9          | Class Statement                     | 22     | Conditional Statements         |
| 10         | Class Body                          | 23     | Loop Statements                |
| 11         | Dot Separated Id, FC, AR subscripts | 24     | Jump Statements                |
| 12         | Declaration and Initialization      | 25     | Exception Handler              |
| 13         | Assignment                          |        |                                |


Writing context free grammar

r: right
w: wrong

<!--COUNT:4-->
### Start Structure

```xml
<START>     -> <PACKAGE> <ST1> | null
<ST1>       -> <IMPORTS> <ST1> | <ST_BODY>
<ST_BODY>   -> <MAIN> <ST_BODY2> | <FN_DEC> <ST_BODY> | <GLOBAL_CLASS> <ST_BODY> | 
...            <GLOBAL_DEC> <ST_BODY> | null
<ST_BODY2>  -> <FN_DEC> <ST_BODY2> | <GLOBAL_CLASS> <ST_BODY2> | 
...            <GLOBAL_DEC> <ST_BODY2> | null
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:1-->
### Body

```xml
<BODY>  -> ; | <SST> | { <MST> }
```
<!--COUNT:2-->
### Single and Multi Statements

```xml
<SST>   -> <IF_ELSE>    | <SWITCH>      | <DEC>     | <TRY_CATCH>   |
...        <LOOP>       | <DO_WHILE>    | <BREAK>   | <RET_ST>      |
...        <CONTINUE>   | <THROW>
           

<MST>   -> <SST> <MST> | null 
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:1-->
### Begin the Main Function
```xml
<MAIN>  -> begin { <MST> }
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:4-->
### Package and Import
```xml
<PACKAGE>   -> package id <IMP_DOT>
<IMPORTS>   -> import id <IMP_DOT> 
<IMP_DOT>   -> dot <ID_STAR> | ;
<ID_STAR>   -> id <IMP_DOT> | power ; | ;
```
```
Example:
import id.^;
import id.id.id.^;
import id.id;
```
<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:5-->
### Reusable CFG
```xml
<TYPE>          -> id | dt | str 
<DT_STR>        -> dt | str
<ARR_TYPE>      -> [ ] <ARR_TYPE_LIST>
<ARR_TYPE_LIST> -> [ ] <ARR_TYPE_LIST> | null
<ACCESS_METH>   -> Parent dot | Self dot
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:1-->
### Access Modifier

```xml
<ACCESSMOD>     -> protected | private | null  <!--Here null is public-->
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:14-->
### Function Statement

- Function Statement in Start

```xml
<FN_DEC>    -> def <RET_TYPE> <FN_ST> <THROWS> { <MST> }

<FN_ST>     -> ( <PAR>
<PAR>       -> <DT_ID> id <PAR_LIST>   | )
<PAR_LIST>  -> , <DT_ID> id <PAR_LIST> | )
<DT_ID>     -> <TYPE> <ARR_TYPE_LIST>
```
```xml
<RET_TYPE>  -> <DT_STR> <ARR_TYPE_LIST> id | id <RT_OBJ>
<RT_OBJ>    -> <ARR_TYPE> id | id | null
```


```
Example:
def function_id () { }
def int function_id (int p1)  { }
def str function_id (str abc)  { }
def object function_id (float p1, int [] p2, ABC p3) {}
```

- Function Statement in class

```xml
<FN_CLASS_DEC>  -> def <IS_ABSTRACT>
<IS_ABSTRACT>   -> Abstract <WITH_STATIC>  ; | 
...                const <WITH_STATIC>  { <MST> } |
                   <WITH_STATIC>  { <MST> }
<RET_TO_THROW>  -> <RET_TYPE_C> <FN_ST> <THROWS>
<WITH_STATIC>   -> Static <RET_TO_THROW> | <RET_TO_THROW>
```

```xml
<RET_TYPE_C>    -> <DT_STR> <ARR_TYPE_LIST> <ACCESSMOD> id | 
                   id <RET_OBJ_C> | <ACCESSMOD_C> id <!--void and no access modifer--> |

<RET_OBJ_C>     -> <ARR_TYPE> <ACCESSMOD> id | <ACCESSMOD_C> id | 
                   id <!--return type but no access modifer--> | 
                   null <!--void no access modifier-->

<ACCESSMOD_C>   -> private | protected
```

```
Example:
def function_id () { }
def Abstract $function_id ();
def int[] $$function_id (p1)  { }
def const object $function_id (p1,p2,p3) {}
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:7-->
### Class Statement

```xml
<GLOBAL_CLASS>  -> <CLASS_DEC> | Abstract <CLASS_DEC> | const <CLASS_GLOBAL>
<CLASS_GLOBAL>  -> <CLASS_DEC> | <VAR_OBJ_G>
<CLASS_DEC>     -> Class <NO_PRIVATE> id <CLASS_PAR> ( <INHERIT>
<NO_PRIVATE>    -> protected | null
<CLASS_PAR>     -> < id > | null
<INHERIT>       -> id <MULTI_INHERIT>   | )  { <CLASS_BODY> 
<MULTI_INHERIT> -> , id <MULTI_INHERIT> | )  { <CLASS_BODY> 
```
<!--COUNT:2-->
### Class Body 

```xml
<CLASS_BODY>    -> <ATTR_FUNC> <CLASS_BODY> | }
<ATTR_FUNC>     -> <FN_CLASS_DEC> | <ATTR_CLASS_DEC>
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:11-->
### Dot Separated Identifers, Function calls, array subscripts

```
Example:
ID              | var_1 
Function call   | func(par1,par2) | helo_q()
array subscript | arr[2]          | arr[2:3]
```

- End only with ID, array subscript.
```xml
<POS>               -> <DOT_ID> | <SUBSCRIPT> <DOT_ID> | <FN_BRACKETS> dot id <POS> | null
<SUBSCRIPT>         -> [ <EXPR> ] <SUBSCRIPT_LIST>
<SUBSCRIPT_LIST>    -> [ <EXPR> ] <SUBSCRIPT_LIST> | null
<FN_BRACKETS>       -> ( <ARG> 
<ARG>               -> <EXPR_OBJ> <ARG_LIST> | )
<ARG_LIST>          -> , <EXPR_OBJ> <ARG_LIST> | )
<EXPR_OBJ>          -> <EXPR> | <NEW_OBJ>
<DOT_ID>            -> dot id <POS>
```

```
Example:
Equal sign not included in this cfg, its only their to explain 
which word this cfg going to parse
a =                     r
a.b.c =                 r
func().b[7].c.a[2] =    r
a[2].b.c.func() =       w
func() =                w
```

- End with ID and array subscript with posibility of pos increment decrement in the end, 
and function call ends with null.

```xml
<POS2>          -> <INC_DEC_DOT> | <SUBSCRIPT> <INC_DEC_DOT> | <FN_BRACKETS> <DOT_ID2> | null
<INC_DEC_DOT>   -> <INC_DEC> | <DOT_ID2> 
<DOT_ID2>       -> dot id <POS2>
```
```
Example:
Equal sign not included in this cfg, its only their to explain 
which word this cfg going to parse
= a                     r
= a[2] ++               r
= func()                r
= a.b.func()            r
= func().b[7].c.a[2]    r
= a[2].b.c.func()--     w
= func().b[7].c++.a[2]  w
= func().b[7].c.a[2]    r
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:14-->
### Declaration and Initialization

- Not in Class
There is no access modifer nor static

This CFG take cares of declaration of **primitive/object** type **variable** and takes transistion to **array declaration** `<ARR_DEC>`, and also towards **assignment** `<ASSIGN>`.

```xml
<DEC>           -> const <TYPE> <VAR_ARR> | dt <VAR_ARR> | id <ASSIGN_OBJ> | <ACCESS_METH> id <ASSIGN> 
<ASSIGN_OBJ>    -> [ <ARR_SUBSCRIPT> | id = <INIT> <LIST> | <ASSIGN> <!--DEC TO ASSIGNMENT-->
<ARR_SUBSCRIPT> -> ] <ARR_TYPE_LIST> <ARR_INIT> | <EXPR> ] <DOT_ID3>

<VAR_ARR>       -> <ARR_DEC> | id <IS_INIT>
<IS_INIT>       -> = <INIT> <LIST> | ;
<INIT>          -> <IS_ACMETH> id <ASSIGN_EXPR> | <NEW_OBJ> | <OPER_TO_EXPR> | <FLAG> <OPERANDS>
<IS_ACMETH>     -> <ACCESS_METH> | null

<OPER_TO_EXPR>  -> <INC_DEC> id <POS> <ID_TO_EXPR>    | ( <EXPR> ) <ID_TO_EXPR> | 
                   <UNARY> <OPERANDS> <ID_TO_EXPR>     | <CONST> <ID_TO_EXPR>   | 

<ASSIGN_EXPR>   -> <DOT_EXPR> | <SUBSCRIPT> <DOT_EXPR> | <FN_BRACKETS> <DOT_EXPR2>
<DOT_EXPR>      -> dot id <ASSIGN_EXPR> | <ASSIGN_OP> <INIT> | <INC_DEC> <ID_TO_EXPR> | <NEW_OBJ> | <ID_TO_EXPR> 
<DOT_EXPR2>     -> dot id <ASSIGN_EXPR> | <ID_TO_EXPR>
<ID_TO_EXPR>    -> <J1> <I1> <H1> <G1> <F1> <EXPR1>
<LIST>          -> , id = <INIT> <LIST> | ;
<ASSIGN_OP>     -> = | cma
```

```
Example:
const int x = y.b = a + 5, t = 3;           r
const str y = "str";                        r
int x = y = convt(int) a + 5, t = q = 2;    r
int x = convt(int) y = a + 5, t = q = 2;    w
x = y = a + 5, t = 3;                       w
```
<!--COUNT:4-->
### Assignment

- This CFG is called by `<DEC>` and it handles **assignment** and **function call**.

```xml
<ASSIGN>        -> <DOT_ID3> | <SUBSCRIPT> <DOT_ID3> | <FN_BRACKETS> <DOT_ID4>
<DOT_ID3>       -> dot id <ASSIGN> | <TWO_ASSIGN>
<DOT_ID4>       -> dot id <ASSIGN> | ;              <!--function call-->
<TWO_ASSIGN>    -> <INC_DEC> ; | <ASSIGN_OP> <INIT> ;
```

```
Example:
x += 2 + 3 * a          r
x = y -= a + 5;         r
x += y *= int <- a + 5; r
x = new Q(x,y);         r
x = y = z = new Q(x,y); r
x = z = new Q(x,y); = y w
x = int <- y = a + 5;   w
x = y = a + 5, t = 3;   w
```

- Function call is in `<ASSIGN>` CFG.
```
Example:
function_id ()
arr[3].function_id (p1)
x.y.functio().function_id (p1,p2,p3)
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:2-->
### Object Declaration
```xml
<NEW_OBJ>       -> new <TYPE> <CONSTR_ARR> | NaN
<CONSTR_ARR>    -> <FN_BRACKETS> | [ <DIM_PASS>
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:21-->
### Array Declaration

```xml
<ARR_DEC>       -> <ARR_TYPE> <ARR_INIT> 
<ARR_CLASS_DEC> -> <ARR_TYPE> <ACCESSMOD> <ARR_INIT>

<ARR_INIT>      -> id <IS_ARR_INIT>
<IS_ARR_INIT>   -> = <CHOICE> | ;
<CHOICE>        -> id <POSARR> | <NEW_ARR_CONST>
<NEW_ARR_CONST> -> new <TYPE> [ <DIM_PASS>

<REF_NEWARR>    -> id <POSARR> <MORE_REF_STR> | <NEW_ARR_CONST>
<POSARR>        -> <DOT_ARR> | <SUBSCRIPT> <DOT_ARR> | <FN_BRACKETS> <DOT_ARR_TRMIN> | null
<DOT_ARR>       -> dot id <POSARR>
<DOT_ARR_TRMIN> -> <DOT_ARR> | ;
<MORE_REF_STR>  -> = <REF_NEWARR> | ; 

<DIM_PASS>      -> <EXPR> ] <MUL_ARR_DEC> | ] <EMP_ARR_DEC> 

<MUL_ARR_DEC>   -> [ <LEN_OF_ARR> | ;
<LEN_OF_ARR>    -> <EXPR> ] <MUL_ARR_DEC2> | ] <EMP_ARR_DEC>
<EMP_ARR_DEC>   -> [ ] <EMP_ARR_DEC> | <ARR_CONST>
<MUL_ARR_DEC2>  -> [ <LEN_OF_ARR2> | ;
<LEN_OF_ARR2>   -> <EXPR> ] <MUL_ARR_DEC2> | ] <EMP_ARR_DEC2>
<EMP_ARR_DEC2>  -> [ ] <EMP_ARR_DEC2> | ;

<ARR_CONST>     -> { <ARR_ELEMT>
<ARR_ELEMT>     -> <EXPR> <EXPR_LIST>  | <ARR_CONST> <EXPR_LIST> | } ;
<EXPR_LIST>     -> , <ARR_ELEMT> <EXPR_LIST> | } ;
```

- Not in Class
```
Example:
int [][] var = new int [][] {{1},{2,4}}     r
int [][] var = new int [][] {{1},{2,4}}     r
int [][] var = new int [2][]                r
int [][][] var = new int [2][3][]           r
int [][][] var = new int [2][][1]           w
int [][] var = new int [2][]  {}            w
```
- In Class
```
Example:
int [][] $var = new int [][] {{1},{2,4}}    r
int [][] $$var = new int [][] {{1},{2,4}}   r
int [][] var = new int [2][]                r
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:6-->
### Global Variable Declaration
```xml
<GLOBAL_DEC>    -> <IS_OBJ_G>
<IS_OBJ_G>      -> dt <VAR_ARR_G> | id <VAR_ARR_G>   
<VAR_OBJ_G>     -> <TYPE> <VAR_ARR_G>
<VAR_ARR_G>     -> <ARR_CLASS_DEC> | id <IS_INIT_G>
<IS_INIT_G>     -> = <INIT> <LIST_G> | ;
<LIST_G>        -> , id = <INIT> <LIST_G> | ;
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:6-->
### Attribute Declaration in class

This CFG take care of primitive and object type variable and array declaration.
<!--null in list and is_init is ';' but it is used in either class body or SST-->
```xml
<ATTR_CLASS_DEC>    -> Static <IS_FINAL> | <IS_FINAL>
<IS_FINAL>          -> const <VAR_OBJ_C> | dt <VAR_ARR_C> | id <VAR_ARR_C>   
<VAR_OBJ_C>         -> <TYPE> <VAR_ARR_C>
<VAR_ARR_C>         -> <ARR_CLASS_DEC> | <ACCESSMOD> id <IS_INIT_C>
<IS_INIT_C>         -> = <INIT> <LIST_C> | ;
<LIST_C>            -> , <ACCESSMOD> id = <INIT> <LIST_C> | ; 
                    <!--Using DEC init but now list has access modifier-->
```


<hr>
<!--------------------------------------------------------------------------------------->

<!--COUNT:14-->
### Expression

Precedance of Operators Low to High
```
Binary OP
or      '||'
and     '&&'
rop     '> < >= <= == !='
pm      '+ -' 
mdm     '* \ %' 
power   '^'

Unary OP
Unary   'convt(dt) !'
```

- Right Recursive 
```xml
<EXPR>      -> <F> <EXPR1>
<EXPR1>     -> or <F> <EXPR1> | null
<F>         -> <G> <F1>
<F1>        -> and <G> <F1>   | null
<G>         -> <H> <G1>
<G1>        -> rop <H> <G1>   | null
<H>         -> <I> <H1>  
<H1>        -> pm <I> <H1>    | null  
<I>         -> <J> <I1> 
<I1>        -> mdm <J> <I1>   | null
<J>         -> <K> <J1> 
<J1>        -> power <K> <J1> | null
<K>         -> <IS_FLAG>
<IS_FLAG>   -> <FLAG> <OPERANDS> | <OPERANDS>
```


<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:3-->
### Operands

```xml
<OPERANDS>  -> <IS_ACMETH> id <POS2> | <INC_DEC> id <POS> | ( <EXPR> ) | <UNARY> <OPERANDS> | <CONST>

<UNARY>     -> typeCast ( dt ) | not
<FLAG>      -> pm 
```

<hr>

<!--------------------------------------------------------------------------------------->

<!--COUNT:1-->
###  Increment Decrement

```xml
<INC_DEC> -> inc_dec
```

<!--COUNT:1-->
### Constant
```xml
<CONST> -> intConst | floatConst | charConst | boolConst | strConst
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:6-->
### Conditional Statements

if-else
```xml
<IF_ELSE>   -> if ( <EXPR> ) <BODY> <OELSE>
<OELSE>     -> else <BODY> | null
```

switch-case
```xml
<SWITCH>        -> shift ( <EXPR> ) { <STATE> 
<STATE>         -> state <EXPR> : <SWITCH_BODY> | <DEFAULT> | }
<DEFAULT>       -> default : <MST> }
<SWITCH_BODY>   -> { <MST> } <STATE> | ; <STATE> | <MST> <STATE> 
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:8-->
### Loop Statements

Loop
```xml
<LOOP>      -> loop <LT>
<LT>        -> <WHILE_ST> | <FOR_ST>
```

While/do-while loop
```xml
<WHILE_ST>  -> till ( <EXPR> ) <BODY>
<DO_WHILE>  -> do <BODY> till ( <EXPR> ) ;
```

For-loop
```xml
<FOR_ST>    -> thru ( dt id in <FOR_ARG> ) <BODY>
<FOR_ARG>   -> id <POS3> | ( <EXPR> , <EXPR> , <EXPR> )

<!--POS3 end with anything subscript id and function-->
<POS3>      -> <DOT_ID5> | <SUBSCRIPT> <DOT_ID5> | <FN_BRACKETS> <DOT_ID5> | null
<DOT_ID5>   -> dot id <POS3>
```

<hr>

<!--------------------------------------------------------------------------------------->
<!--COUNT:5-->
### Jump Statements

Break-continue
```xml
<BREAK>     -> stop <L>
<CONTINUE>  -> cont <L>
<L>         -> id : | ;
```

Return-statement
```xml
<RET_ST>    -> ret <EXPR> ;
```

Throw
```xml
<THROW>     -> raise <NEW_OBJ> ;
```

<hr>

<!--------------------------------------------------------------------------------------->

<!--COUNT:6-->
### Exception Handler

```xml
<TRY_CATCH>         -> test { <MST> } except <ERROR_TYPE> { <MST> } <EXCEPT_FINALLY>
<EXCEPT_FINALLY>    -> except <ERROR_TYPE> { <MST> } <EXCEPT> | <FINALLY> | null
<ERROR_TYPE>        -> ( id <ERR_DOT> )
<ERR_DOT>           -> dot id | id
<THROWS>            -> raises id | null
<FINALLY>           -> finally { <MST> }
```

<hr>

<!--------------------------------------------------------------------------------------->