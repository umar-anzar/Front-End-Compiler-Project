## Syntax Analyze Phase

Writing context free grammar

r: right
w: wrong






### Body

```xml
<BODY>  -> ; | <SST> | { <MST> }
```


### Single and Multi Statements

```xml
<SST>   -> <IF_ELSE>        | <SWITCH>          | <INC_DEC_ST> ;    | <DEC> ;       |  
 ...       <LOOP>           | <DO_WHILE> ;      | <BREAK> ;         | <RET_ST> ;    |
 ...       <CONTINUE> ;     | <THROW> ;         | <ASSIGN> ;        | <TRY_CATCH>   |
           

<MST>   -> <SST> <MST> | null 
```
<hr>

<!--------------------------------------------------------------------------------------->




### Function

- Function call is in `<DEC>` CFG.
```
Example:
function_id ()
arr[3].function_id (p1)
x.y.functio().function_id (p1,p2,p3)
```

- Function Statement in function

```xml
<FN_DEC>    -> def <RET_TYPE> id <FN_ST> <THROWS> { <MST> }
<FN_ST>     -> ( <PAR> )
<PAR>       -> <DT_ID> id <PAR_LIST>   | null
<PAR_LIST>  -> , <DT_ID> id <PAR_LIST> | null
```
```xml
<DT_ID>         -> <TYPE> <ARR_TYPE>
<RET_TYPE>      -> <DT_ID> | null <!--Here null is void-->
<TYPE>          -> id | dt | str 
<ARR_TYPE>      -> [ ] <ARR_TYPE> | null
```

```
Example:
def function_id () { }
def int function_id (p1)  { }
def object function_id (p1,p2,p3) {}
```

- Function Statement in class

```xml
<FN_CLASS_DEC>  -> def <RET_TYPE> <IS_ABSTRACT> 
<IS_ABSTRACT>   -> Abstract <ACCESSMOD> id <FN_ST> <THROWS> ; | 
...                <FINAL> <ACCESSMOD> id <FN_ST> <THROWS> { <MST> }
<FINAL>         -> const | null
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

In Main Function
There is no access modifer nor static

This Cfg take transistion to declaration of **primitive** and **object** type **variable** and **array**, and also towards variable 
**assignment** and **function call**.

```xml
<DEC>           -> const <VAR_OBJ>
<DEC>           -> dt <VAR_ARR> | id <ASSIGN_OBJ>
<ASSIGN_OBJ>    -> <ASSIGN> | <OBJ_DEC>             <!--DEC TO ASSIGNMENT-->
<VAR_OBJ>       -> <OBJ_DEC> | dt <VAR_ARR>
<VAR_ARR>       -> <ARR_DEC> | id = <INIT> <LIST>
<INIT>          -> id <ASSIGN_EXPR> | <EXPR>
<ASSIGN_EXPR>   -> <LIST_EXPR> | <SUBSCRIPT> <LIST_EXPR> | <FN_BRACKETS> <DOT_EXPR>
<LIST_EXPR>     -> <ASSIGN_OP> <INIT> | <INC_DEC> <Y> | <DOT_EXPR>
<Y>             -> <ID_TO_EXPR> | null
<DOT_EXPR>      -> dot id <ASSIGN_EXPR> | <ID_TO_EXPR> | null
<ID_TO_EXPR>    -> <J1> <I1> <H1> <G1> <F1> <EXPR>
<LIST>          -> , id = <INIT> <LIST> | null
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

### Assignment

This Cfg is called by `<DEC>` and it handles assignment and function call.

```xml
<ASSIGN>        -> <DOT_ID3> <TWO_ASSIGN> | <SUBSCRIPT> <DOT_ID3> <TWO_ASSIGN> | 
...                <FN_BRACKETS> <DOT_ID3>
<DOT_ID3>       -> dot id <ASSIGN> | null
<TWO_ASSIGN>    -> <INC_DEC> | <ASSIGN_OP> <OBJ_PRIMITIVE> 
<OBJ_PRIMITIVE> -> <NEW_OBJ> | <INIT>
```

```
Example:
x += 2 + 3 * a          r
x = y -= a + 5;         r
x += y *= int <- a + 5; r
x = new Q(x,y);         r
x = int <- y = a + 5;   w
x = y = a + 5, t = 3;   w
```
<hr>



<!--------------------------------------------------------------------------------------->

### Access Modifier, Static and Abstract

```xml
<ACCESSMOD>     -> protected | private | null  <!--Here null is public-->
<ABS_FINAL>     -> Abstract | const | null
<STATIC>        -> Static | null
```
<hr>

<!--------------------------------------------------------------------------------------->

### Class Declaration

```xml
<OUTER_CLASS_DEC>   -> <ABS_FINAL> <CLASS_DEC>
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


### Attribute Declaration in class

This CFG take transition to primitive and object type variable and array declaration and also inner class declarartion

```xml
<ATTR_CLASS_DEC>    -> <STATIC> <ABS_FINAL>
<ABS_FINAL>         -> Abstract <CLASS_DEC> | const <CLASS_OBJ_PRIM>  | <CLASS_OBJ_PRIM> 
<CLASS_OBJ_PRIM>    -> <CLASS_DEC> | dt <VAR_ARR2> | id <OBJ_CLASS_DEC>

<VAR_ARR2>          -> <ARR_CLASS_DEC> | id = <INIT> <LIST_C> 
<LIST_C>            -> , <ACCESSMOD> id = <INIT> <LIST_C> | null <!--Using DEC init but now list has access modifier-->


```

<hr>

<!--------------------------------------------------------------------------------------->



### Object Declaration

- Not in Class
```xml
<OBJ_DEC>       -> id <IS_ARR>      <!--2nd rule is in string declaration-->
<IS_ARR>        -> <ARR_DEC> | id = <REF_NEWOBJ>
<REF_NEWOBJ>    -> id <POSOBJ> | <NEW_OBJ> 
<POSOBJ>        -> <DOT_OBJ> <MORE_REF> | <SUBSCRIPT> <DOT_OBJ> <MORE_REF> | 
...                <FN_BRACKETS> <DOT_OBJ> 
<DOT_OBJ>       -> dot id <POSOBJ> | null
<MORE_REF>      -> = <REF_NEWOBJ>  | null
<NEW_OBJ>       -> new <TYPE> <CONSTR_ARR> 
<CONSTR_ARR>    -> <FN_BRACKETS> | [ <DIM_PASS>
```

- In Class
```xml
<OBJ_CLASS_DEC> -> id <IS_ARR_CLASS>   <!--2nd rule is in string declaration-->
<IS_ARR_CLASS>  -> <ARR_CLASS_DEC> | <ACCESSMOD> id = <REF_NEWOBJ>
```

<hr>


<!--------------------------------------------------------------------------------------->

### String Declaration
- Not in Class
```xml
<OBJ_DEC>       -> str <ARR_STR>
<ARR_STR>       -> <ARR_DEC> | id = <REF_NEWSTR> 
<REF_NEWSTR>    -> id <POSSTR> | <NEW_STR_CONST>
<POSSTR>        -> <DOT_STR> <MORE_REF_STR> | <SUBSCRIPT> <DOT_STR> <MORE_REF_STR> | 
...                <FN_BRACKETS> <DOT_STR> 
<DOT_STR>       -> dot id <POSSTR> | null
<MORE_REF_STR>  -> = <REF_NEWSTR>  | null

<NEW_STR_CONST> -> new str <FN_BRACKETS> | strConst
```

- In Class
```xml
<OBJ_CLASS_DEC> -> str <ARR_STR_CLASS> 
<ARR_STR_CLASS> -> <ARR_CLASS_DEC> | <ACCESSMOD> id = <REF_NEWSTR> 
```
<hr>

<!--------------------------------------------------------------------------------------->

### Array Declaration
- Not in Class
```xml
<ARR_DEC>       -> [ ] <ARR_TYPE> id = <CHOICE>

<CHOICE>        -> <REF_NEWARR> | <NEW_ARR_CONST>
<NEW_ARR_CONST> -> new <TYPE> [ <DIM_PASS>

<REF_NEWARR>    -> id <POSARR> | <NEW_ARR_CONST>
<POSARR>        -> <DOT_ARR> <MORE_REF_STR> | <SUBSCRIPT> <DOT_ARR> <MORE_REF_ARR> | 
...                <FN_BRACKETS> <DOT_ARR> 
<DOT_ARR>       -> dot id <POSARR> | null
<MORE_REF_STR>  -> = <REF_NEWARR>  | null 

<DIM_PASS>      -> <EXPR> ] <MUL_ARR_DEC> | ] <EMP_ARR_DEC> <ARR_CONST>

<MUL_ARR_DEC>   -> [ <LEN_OF_ARR> ] <MUL_ARR_DEC> | null
<EMP_ARR_DEC>   -> [ ] <EMP_ARR_DEC> | null
<LEN_OF_ARR>    -> <EXPR> | null

<ARR_CONST>     -> { <ARR_ELEMT> }
<ARR_ELEMT>     -> <EXPR> <EXPR_LIST>  | <ARR_CONST> <EXPR_LIST> | null 
<EXPR_LIST>     -> , <ARR_ELEMT> <EXPR_LIST> | null
```
- In Class

```xml
<ARR_CLASS_DEC> -> [ ] <ARR_TYPE> <ACCESSMOD> id = <CHOICE>
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
<K>         -> <OPERANDS>
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
<K>         -> <OPERANDS>
```
<hr>

<!--------------------------------------------------------------------------------------->

### Operands

```xml
<OPERAND>   -> id <POS2>            | <INC_DEC> id <POS>    | ( <EXPR> ) | 
               <UNARY> <OPERAND>    | <CONST>

<UNARY>     -> typeCast ( dt ) | not
```
<hr>


<!--------------------------------------------------------------------------------------->


### Conditional Statements

if-else CFG
```xml
<IF_ELSE>   -> if ( <EXPR> ) <body> <OELSE>
<OELSE>     -> else <body> | null
```

switch-case statement:
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
<FOR_ST>    -> thru ( dt id in <F1> ) <BODY>
<F1>        -> id | ( <EXPR> , <EXPR> , <EXPR> )
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
<TRY_CATCH>     -> test { <MST> } except <ERROR_TYPE> { <MST> }
<ERROR_TYPE>    -> ( id id )
<THROWS>        -> raises id | null
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



  
<br>
<hr><hr><hr>

<!--
*
*
*
-->

```xml
<TYPE>          -> id | dt | str 
<FINAL>         -> const | null
<ACCESSMOD>     -> protected | private | null
<ABS_FINAL>     -> Abstract | const | null
<STATIC>        -> Static | null
<FN_ST>         -> ( <PAR> ) <!--used in function declaration-->
<FN_BRACKETS>   -> ( <ARG> ) <!--used in function calling-->
<NEW_OBJ>       -> new <TYPE> <CONSTR_ARR>  <!--after = or cma(+= etc) also in array const-->
<STR_ID>        -> str | id 
<ASSIGN_OP>     -> = | cma
```
