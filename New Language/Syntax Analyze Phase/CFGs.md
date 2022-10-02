## Syntax Analyze Phase

| CFG no | Name                                | CFG no | Name                                |
|--------|-------------------------------------|--------|-------------------------------------|
| 1      | Start Structure                     | 14     | Object Declaration                  |
| 2      | Body                                | 15     | Array Declaration                   |
| 3      | Single and Multi Statements         | 16     | Global Variable Declaration         |
| 4      | Begin the Main Function             | 17     | Attribute Declaration in class      |
| 5      | Package and Import                  | 18     | Expression                          |
| 6      | Reusable CFG                        | 19     | Operands                            |
| 7      | Access Mod, Static and Abstract     | 20     | Increment Decrement                 |
| 8      | Function Declaration                | 21     | Constant                            |
| 9      | Class Declaration                   | 22     | Conditional Statements              |
| 10     | Class Body                          | 23     | Loop Statements                     |
| 11     | Dot Separated Id, FC, AR subscripts | 24     | Jump Statements                     |
| 12     | Declaration and Initialization      | 25     | Exception Handler                   |
| 13     | Assignment                          |







Writing context free grammar

r: right
w: wrong

### Start Structure
```xml
<START>     -> <PACKAGE> <ST1> <ST_BODY>
<ST1>       -> <IMPORTS> <ST1> | null
<ST_BODY>   -> <MAIN> <ST_BODY2> | <FN_DEC> <ST_BODY> | <OUTER_CLASS_DEC> <ST_BODY> | 
...            <GLOBAL_DEC> <ST_BODY> | null
<ST_BODY2>  -> <FN_DEC> <ST_BODY2> | <OUTER_CLASS_DEC> <ST_BODY2> | 
...            <GLOBAL_DEC> <ST_BODY2> | null
```

<hr>

<!--------------------------------------------------------------------------------------->

### Body

```xml
<BODY>  -> ; | <SST> | { <MST> }
```

### Single and Multi Statements

```xml
<SST>   -> <IF_ELSE>        | <SWITCH>          | <DEC> ;       | <TRY_CATCH>   |
 ...       <LOOP>           | <DO_WHILE> ;      | <BREAK> ;     | <RET_ST> ;    |
 ...       <CONTINUE> ;     | <THROW> ;
           

<MST>   -> <SST> <MST> | null 
```

<hr>

<!--------------------------------------------------------------------------------------->

### Begin the Main Function
```xml
<MAIN>  -> Begin { <MST> }
```

<hr>

<!--------------------------------------------------------------------------------------->

### Package and Import
```xml
<PACKAGE>   -> package id <IMP_DOT> ;
<IMPORTS>   -> import id <IMP_DOT> 
<IMP_DOT>   -> dot id | ;
```

<hr>

<!--------------------------------------------------------------------------------------->

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

### Access Modifier, Static and Abstract

```xml
<ACCESSMOD>     -> protected | private | null  <!--Here null is public-->
<STATIC>        -> Static
<FINAL>         -> const | null
```

<hr>

<!--------------------------------------------------------------------------------------->

### Function Statement

- Function Statement in Start

```xml
<FN_DEC>    -> def <RET_TYPE> <FN_ST> <THROWS> { <MST> }

<FN_ST>     -> ( <PAR> )
<PAR>       -> <DT_ID> id <PAR_LIST>   | null
<PAR_LIST>  -> , <DT_ID> id <PAR_LIST> | null
<DT_ID>     -> <TYPE> <ARR_TYPE_LIST>
```
```xml
<RET_TYPE>  -> <DT_STR> <ARR_TYPE_LIST> id | id <RT_OBJ>
<RT_OBJ>    -> <ARR_TYPE> id | id | null
```


```
Example:
def function_id () { }
def int function_id (p1)  { }
def object function_id (p1,p2,p3) {}
```

- Function Statement in class

```xml
<FN_CLASS_DEC>  -> def <IS_ABSTRACT>
<IS_ABSTRACT>   -> Abstract <RET_TYPE_C> <FN_ST> <THROWS> ; | 
...                <FINAL> <RET_TYPE_C> <FN_ST> <THROWS> { <MST> }
```

```xml
<RET_TYPE_C>    -> <DT_STR> <ARR_TYPE_LIST> <ACCESSMOD> id | 
                   id <RT_OBJ_C> | <ACCESSMOD_C> id

<RT_OBJ_C>      -> <ARR_TYPE> <ACCESSMOD> id | <ACCESSMOD_C> id | 
                   id <!--void and no access modifer--> | 
                   null <!--no return no access modifier-->

<ACCESSMOD_C>   -> private | protected
```

```
Example:
def function_id () { }
def Abstract $function_id ();
def int[] $$function_id (p1)  { }
def object const $function_id (p1,p2,p3) {}
```

<hr>

<!--------------------------------------------------------------------------------------->

### Class Statement

```xml
<OUTER_CLASS_DEC>   -> <CLASS_DEC> | Abstract <CLASS_DEC> | const <CLASS_GLOBAL>
<CLASS_GLOBAL>      -> <CLASS_DEC> | <VAR_OBJ_G>
<CLASS_DEC>         -> Class <ACCESSMOD> id <CLASS_PAR> ( <INHERIT> ) { <CLASS_BODY> }
<CLASS_PAR>         -> < id > | null
<INHERIT>           -> id <MULTI_INHERIT>   | null
<MULTI_INHERIT>     -> , id <MULTI_INHERIT> | null
```

### Class Body 

```xml
<CLASS_BODY>    -> <ATTR_FUNC> <CLASS_BODY> | null
<ATTR_FUNC>     -> <FN_CLASS_DEC> | <ATTR_CLASS_DEC> ;
```

<hr>

<!--------------------------------------------------------------------------------------->

### Dot Separated Identifers, Function calls, array subscripts

```
Example:
ID              | var_1 
Function call   | func(par1,par2) | helo_q()
array subscript | arr[2]          | arr[2:3]
```

- End only with ID, array subscript.
```xml
<POS>           -> <DOT_ID> | <SUBSCRIPT> <DOT_ID> | <FN_BRACKETS> dot id <POS>
<SUBSCRIPT>     -> [ <EXPR> ]
<FN_BRACKETS>   -> ( <ARG> )
<ARG>           -> <EXPR> <ARG_LIST> | <NEW_OBJ> | null
<ARG_LIST>      -> , <EXPR> <ARG_LIST> | null
<DOT_ID>        -> dot id <POS> | null
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
<POS2>          -> <INC_DEC_DOT> | <SUBSCRIPT> <INC_DEC_DOT> | <FN_BRACKETS> <DOT_ID2>
<INC_DEC_DOT>   -> <INC_DEC> | <DOT_ID2> 
<DOT_ID2>       -> dot id <POS2> | null
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

### Declaration and Initialization

- Not in Class
There is no access modifer nor static

This CFG take cares of declaration of **primitive/object** type **variable** and takes transistion to **array declaration** `<ARR_DEC>`, and also towards **assignment** `<ASSIGN>`.

```xml
<DEC>           -> const <TYPE> <VAR_ARR> | dt <VAR_ARR> | id <ASSIGN_OBJ> | <ACCESS_METH> id <ASSIGN> 
<ASSIGN_OBJ>    -> [ <ARR_SUBSCRIPT> | id = <INIT> <LIST> | <ASSIGN> <!--DEC TO ASSIGNMENT-->
<ARR_SUBSCRIPT> -> ] <ARR_TYPE_LIST> <ARR_INIT> | <EXPR> ] <DOT_ID3>

<VAR_ARR>       -> <ARR_DEC> | id = <INIT> <LIST>
<INIT>          -> <IS_ACMETH> id <ASSIGN_EXPR> | <NEW_OBJ> | <OPER_TO_EXPR>
<IS_ACMETH>     -> <ACCESS_METH> | null

<OPER_TO_EXPR>  -> <FLAG> <INC_DEC> id <POS> <Y>    | <FLAG> ( <EXPR> ) <Y> | 
                   <FLAG> <UNARY> <OPERAND> <Y>     | <FLAG> <CONST> <Y>    | 
                   pm <IS_ACMETH> id <POS2> <Y>     <!--compulsory flag pm-->

<ASSIGN_EXPR>   -> <LIST_EXPR> | <SUBSCRIPT> <LIST_EXPR> | <FN_BRACKETS> <DOT_EXPR>
<LIST_EXPR>     -> <DOT_EXPR>  | <ASSIGN_OP> <INIT> | <INC_DEC> <Y> | <NEW_OBJ>
<Y>             -> <ID_TO_EXPR> | null
<DOT_EXPR>      -> dot id <ASSIGN_EXPR> | <ID_TO_EXPR> | null
<ID_TO_EXPR>    -> <J1> <I1> <H1> <G1> <F1> <EXPR>
<LIST>          -> , id = <INIT> <LIST> | null
<ASSIGN_OP>     -> = | cma

<DEC_CLASS>     -> const <VAR_OBJ_C> | dt <VAR_ARR_C> | id <VAR_ARR_C>        
<VAR_OBJ_C>     -> <TYPE> <VAR_ARR_C>
<VAR_ARR_C>     -> <ARR_CLASS_DEC> | <ACCESSMOD> id = <INIT> <LIST_C>
<LIST_C>        -> , id = <INIT> <LIST_C> | null
```

```
Example:
const int x = y.b = a + 5, t = 3;           r
const str y = "str";                        r
int x = y = convt(int) a + 5, t = q = 2;    r
int x = convt(int) y = a + 5, t = q = 2;    w
x = y = a + 5, t = 3;                       w
```

### Assignment

- This CFG is called by `<DEC>` and it handles **assignment** and **function call**.

```xml
<ASSIGN>        -> <DOT_ID3> | <SUBSCRIPT> <DOT_ID3> | 
...                <FN_BRACKETS> <DOT_ID4>
<DOT_ID3>       -> dot id <ASSIGN> | <TWO_ASSIGN>
<DOT_ID4>       -> dot id <ASSIGN> | null
<TWO_ASSIGN>    -> <INC_DEC> | <ASSIGN_OP> <INIT>
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

### Object Declaration
```xml
<NEW_OBJ>       -> new <TYPE> <CONSTR_ARR> 
<CONSTR_ARR>    -> <FN_BRACKETS> | [ <DIM_PASS>
```

<hr>

<!--------------------------------------------------------------------------------------->

### Array Declaration

```xml
<ARR_DEC>       -> <ARR_TYPE> <ARR_INIT> 
<ARR_CLASS_DEC> -> <ARR_TYPE> <ACCESSMOD> <ARR_INIT>

<ARR_INIT>      -> id = <CHOICE>
<CHOICE>        -> <REF_NEWARR> | <NEW_ARR_CONST>
<NEW_ARR_CONST> -> new <TYPE> [ <DIM_PASS>

<REF_NEWARR>    -> id <POSARR> | <NEW_ARR_CONST>
<POSARR>        -> <DOT_ARR> <MORE_REF_STR> | <SUBSCRIPT> <DOT_ARR> <MORE_REF_ARR> | 
...                <FN_BRACKETS> <DOT_ARR> 
<DOT_ARR>       -> dot id <POSARR> | null
<MORE_REF_STR>  -> = <REF_NEWARR>  | null 

<DIM_PASS>      -> <EXPR> ] <MUL_ARR_DEC> | ] <EMP_ARR_DEC> <ARR_CONST>

<MUL_ARR_DEC>   -> [ <LEN_OF_ARR> | null
<EMP_ARR_DEC>   -> [ ] <EMP_ARR_DEC> | null
<LEN_OF_ARR>    -> <EXPR> ] <MUL_ARR_DEC> | ] <EMP_ARR_DEC>

<ARR_CONST>     -> { <ARR_ELEMT> }
<ARR_ELEMT>     -> <EXPR> <EXPR_LIST>  | <ARR_CONST> <EXPR_LIST> | null 
<EXPR_LIST>     -> , <ARR_ELEMT> <EXPR_LIST> | null
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

### Global Variable Declaration
```xml
<GLOBAL_DEC>    -> <STATIC> <IS_FINAL_G>
<IS_FINAL_G>    -> const <VAR_OBJ_G> | dt <VAR_ARR_G> | id <VAR_ARR_G>   
<VAR_OBJ_G>     -> <TYPE> <VAR_ARR_G>
<VAR_ARR_G>     -> <ARR_CLASS_DEC> | id = <INIT> <LIST_G>
<LIST_G>        -> , id = <INIT> <LIST_G> | ;
```

<hr>

<!--------------------------------------------------------------------------------------->

### Attribute Declaration in class

This CFG take care of primitive and object type variable and array declaration.

```xml
<ATTR_CLASS_DEC>    -> <STATIC> <IS_FINAL>
<IS_FINAL>          -> const <VAR_OBJ_C> | dt <VAR_ARR_C> | id <VAR_ARR_C>   
<VAR_OBJ_C>         -> <TYPE> <VAR_ARR_C>
<VAR_ARR_C>         -> <ARR_CLASS_DEC> | <ACCESSMOD> id = <INIT> <LIST_C>
<LIST_C>            -> , <ACCESSMOD> id = <INIT> <LIST_C> | null <!--Using DEC init but now list has access modifier-->
```


<hr>
<!--------------------------------------------------------------------------------------->

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

- Left Recursive 
```xml
<EXPR>      -> <EXPR> or <F>
<EXPR>      -> <F>
<F>         -> <F> and <G>
<F>         -> <G>
<G>         -> <G> rop <H>
<G>         -> <H>
<H>         -> <H> pm <I>
<H>         -> <I>
<I>         -> <I> mdm <J>
<I>         -> <J>
<J>         -> <J> power <K>
<J>         -> <K>
<K>         -> <FLAG> <OPERANDS>
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
<K>         -> <FLAG> <OPERANDS>
```


<hr>

<!--------------------------------------------------------------------------------------->

### Operands

```xml
<OPERAND>   -> <IS_ACMETH> id <POS2> | <INC_DEC> id <POS> | ( <EXPR> ) | 
               <UNARY> <OPERAND> | <CONST>

<UNARY>         -> typeCast ( dt ) | not
<FLAG>          -> pm | null
```

<hr>

<!--------------------------------------------------------------------------------------->


###  Increment Decrement

```xml
<INC_DEC> -> ++ | --
```


### Constant
```xml
<CONST> -> intConst | floatConst | charConst | boolConst | 
           strConst
```

<hr>

<!--------------------------------------------------------------------------------------->

### Conditional Statements

if-else
```xml
<IF_ELSE>   -> if ( <EXPR> ) <body> <OELSE>
<OELSE>     -> else <body> | null
```

switch-case
```xml
<SWITCH>    -> shift ( <EXPR> ) { <STATE> }
<STATE>     -> state <EXPR> : <TWO_MST> <STATE> | <DEFAULT> 
<DEFAULT>   -> default : <TWO_MST> | null 
<TWO_MST>   -> <MST> | { <MST> }
```

<hr>

<!--------------------------------------------------------------------------------------->

### Loop Statements

Loop
```xml
<LOOP>      -> loop <LT>
<LT>        -> <WHILE_ST> | <FOR_ST>
```

While/do-while loop
```xml
<WHILE_ST>  -> till ( <EXPR> ) <body>
<DO_WHILE>  -> do <BODY> till ( <EXPR> )
```

For-loop
```xml
<FOR_ST>    -> thru ( dt id in <FOR_ARG> ) <BODY>
<FOR_ARG>   -> id <POS3> | ( <EXPR> , <EXPR> , <EXPR> )

<POS3>      -> <DOT_ID5> | <SUBSCRIPT> <DOT_ID5> | <FN_BRACKETS> <DOT_ID5>
<DOT_ID5>   -> dot id <POS3> | null
```

<hr>

<!--------------------------------------------------------------------------------------->

### Jump Statements

Break-continue
```xml
<BREAK>     -> stop <L>
<CONTINUE>  -> cont <L>
<L>         -> id : | null
```

Return-statement
```xml
<RET_ST>    -> ret <EXPR>
```

Throw
```xml
<THROW>     -> raise <NEW_OBJ>
```

<hr>

<!--------------------------------------------------------------------------------------->


### Exception Handler

```xml
<TRY_CATCH>     -> test { <MST> } except <ERROR_TYPE> { <MST> } <FINALLY>
<ERROR_TYPE>    -> ( id id )
<THROWS>        -> raises id | null
<FINALLY>       -> finally { <MST> } | null
```

<hr>

<!--------------------------------------------------------------------------------------->