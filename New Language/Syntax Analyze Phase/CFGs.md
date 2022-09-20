## Syntax Analyze Phase

Writing context free grammar

r: right
w: wrong


## Functional Part

### Single and Multi Statements

```xml
<SST>   -> <IF_ELSE>    | <SWITCH>          | <INC_DEC_ST> ;    | <DEC>      |  
           <OBJ_DEC>  ; | <LOOP>            | <DO_WHILE> ;      | <BREAK> ;  | 
           <CONTINUE> ; | <RET_ST> ;        | <THROW> ;         | <ASSIGN> ; | 
           <TRY_CATCH>  | <FN_ID_CALL> ;
           

<MST>   -> <SST> <MST> | null 
```
<hr>



### Body

```xml
<BODY>  -> ; | <SST> | { <MST> }
```
<hr>




### Operands

```xml
<OPERAND>   -> <CONST> | <INC_DEC> <INDFRS> | <INDFRS> <OP1> | 
               <OBJ_AC_PROP> | <NEW_OBJ> <!--new String()-->

<OP1>       -> <INC_DEC> | null
```
<hr>

###  Increment Decrement and Constant

```xml
<INC_DEC> -> ++ | --
```

```xml
<CONST>         -> intConst | floatConst | charConst | boolConst | 
                   strConst | <ARR_CONST>
<ARR_CONST>     -> { <EXPR> <EXPR_LIST> }
<EXPR_LIST>     -> , <EXPR>| null
```
<hr>



### Function Call

```xml
<FN_ID_CALL>    -> id <FN_CALL>
```

```
Example:
function_id ()
function_id (p1)
function_id (p1,p2,p3)
```
<hr>



### Identifer, Function call, array subscript (INDFRS)

```xml
<INDFRS>    -> id <AF>
<AF>        -> <SUBSCRIPT> | <FN_CALL> | null
<SUBSCRIPT> -> [ <EXPR> <SLICE> ]
<FN_CALL>   -> ( <ARG> )
<SLICE>     -> : <EXPR> | null
<ARG>       -> <EXPR> <ARG_LIST> | null
<ARG_LIST>  -> , <EXPR> <ARG_LIST> | null
```
```
Example:
ID              | var_1 
Function call   | func(par1,par2) | helo_q()
array subscript | arr[2]          | arr[2:3]
```
<hr>




### Dot Separated Identifers

- Access Part can end with ID, array subscript, and function call
These are all used after equal sign
```xml
<ACCESS_ID>     -> <INDFRS> <AP_DOT_LIST>
<AP_DOT_LIST>   -> dot <INDFRS> <AP_DOT_LIST> | null
```

```
Example:
Equal sign not included in this cfg, its only their to explain 
which word this cfg going to parse
= a                     r
= a[2]                  r
= func()                r
= a.b.func()            r
= a[2].b.c.func()       r
= func().b[7].c.a[2]    r
```



- Assignment Part end only with ID, array subscript
These are all used before equal sign
```xml
<ASSIGN_ID>     -> id <IS_ARR_FUNC> 
<IS_ARR_FUNC>   -> <IS_DOT> | <SUBSCRIPT> <IS_DOT> | <FN_CALL> dot <ASP_DOT_LIST>
<IS_DOT>        -> dot <ASP_DOT_LIST> | null
<ASP_DOT_LIST>  -> <INDFRS> dot <ASP_DOT_LIST> | <LAST_ID_ARR>
<LAST_ID_ARR>   -> id <ARRAY_NULL>
<ARRAY_NULL>    -> <SUBSCRIPT> | null
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

<hr>




### Declaration and Initialization

In Main Function
There is no access modifer nor static

```xml
<DEC>       -> <FINAL> dt id <VAR_ARR> 
<VAR_ARR>   -> <IS_ARR> | <INIT> <LIST>
<FINAL>     -> const | null
<INIT>      -> = <INIT2> | null
<INIT2>     -> <ASSIGN_ID> <INIT> | <EXPR>
<LIST>      -> , id <INIT> <LIST> | ;
```

```
Example:
const int x = y = a + 5, t = 3;         r
int x = y = int <- a + 5, t = q = 2;    r
int x = int <- y = a + 5, t = q = 2;    w
x = y = a + 5, t = 3;                   w
```
<hr>




### Assignment
```xml
<ASSIGN>        -> <ASSIGN_ID> <ASS_OP> <OBJ_PRIMITIVE> 
<ASSIGN1>       -> <ASS_OP> <ASSIGN_LIST> | null 
<ASSIGN_LIST>   -> <ASSIGN_ID> <ASSIGN1> | <EXPR> 
<ASS_OP>        -> = | cma
<OBJ_PRIMITIVE> -> <NEW_OBJ> | <ASSIGN_LIST>
```

```
Example:
x += 2 + 3 * a          r
x = y -= a + 5;         r
x += y *= int <- a + 5; r
x = new Q(x,y)          r
x = int <- y = a + 5;   w
x = y = a + 5, t = 3;   w
```
<hr>




### Expression

Precedance of Operators Low to High
```
or      '||'
and     '&&'
rop     '> < >= <= == !='
pm      '+ -' 
mdm     '* \ %' 
power   '^'
```

Left Recursive 
With Brackets
```xml
<EXPR>      -> <E> or <F>
<E>         -> <BRACKETS>|<EXPR>
<EXPR>      -> <F>

<BRACKETS>  -> <UNARY> ( <EXPR> )

<F>         -> <BRACKETS>
<G>         -> <BRACKETS>
<H>         -> <BRACKETS>
<I>         -> <BRACKETS>
<J>         -> <BRACKETS>
<K>         -> <BRACKETS>
<L>         -> <BRACKETS>

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
<K>         -> <UNARY> <L>
<L>         -> <OPERANDS>

<UNARY>     -> typeCast ( dt ) <UNARY> | not <UNARY>| null
```
<hr>




### Conditional Statements

if-else CFG
```xml
<IF_ELSE>   -> if(<EXPR>) <body> <OELSE>
<OELSE>     -> else <body> | null
```

switch-case statement:
```xml
<SWITCH>    -> shift ( <EXPR> ) { <STATE> }
<STATE>     -> state <EXPR> : <BODY> <STATE> | <DEFAULT> 
<DEFAULT>   -> default : <BODY> | null 
```
<hr>



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


### Exception Handler

```xml
<TRY_CATCH>     -> test { <MST> } except <ERROR_TYPE> { <MST> }
<ERROR_TYPE>    -> ( id id )
```
<hr>



### Function Statement not in class

```xml
<FN_DEC>    -> def <RET_TYPE> id <FN_ST> { <MST> }
<RET_TYPE>  -> id | dt | null   <!--Here null is void-->
<FN_ST>     -> ( <PAR> )
<PAR>       -> <DT_ID> id <PAR_LIST>   | null
<PAR_LIST>  -> , <DT_ID> id <PAR_LIST> | null
```
<hr>


## OOP PART

### Access Modifier, Static and Abstract

```xml
<ACCESSMOD>     -> protected | private | null  <!--Here null is public-->
<ABS_FINAL>     -> Abstract | const | null
<STATIC>        -> static | null
```
<hr>


### Class

```xml
<CLASS_DEC>     -> <ABS_FINAL> Class <ACCESSMOD> id <CLASS_PAR> ( <INHERIT> ) { <CLASS_BODY> }
<CLASS_PAR>     -> < id > | null
<INHERIT>       -> id <MULTI_INHERIT>   | null
<MULTI_INHERIT> -> , id <MULTI_INHERIT> | null
```
<hr>



### Class Body 

```xml
<CLASS_BODY>    -> <ATTR_FUNC> <CLASS_BODY> | null
<ATTR_FUNC>     -> <FN_CLASS_DEC> | <ATTR_CLASS_DEC>
```
<hr>



### Attribute Declaration in class

```xml
<ATTR_CLASS_DEC>    -> <STATIC>  dt  <ACCESSMOD>  id 
```

```xml
<DEC>       -> <STATIC> <FINAL> dt <ACCESSMOD> id <INIT> <LIST>
<FINAL>     -> const | null
<INIT>      -> = <INIT2> | null
<INIT2>     -> <ASSIGN_ID> <INIT> | <EXPR>
<LIST>      -> , <ACCESSMOD> id <INIT> <LIST> | ;
```
<hr>



### Function Statement in class

```xml
<FN_CLASS_DEC>  -> def <RET_TYPE> <ACCESSMOD> <IS_ABSTRACT>
<IS_ABSTRACT>   -> Abstract id <FN_ST> ; | <FINAL> id <FN_ST> { <MST> }
```
<hr>


### Object Declaration

```xml
<OBJ_DEC>   -> id <IS_ARR>
<IS_ARR>    -> <ARR_DEC> | id = <NEW_OBJ> 
<NEW_OBJ>   -> new id <FN_CALL>
```
<hr>

### String Declaration

```xml
<OBJ_DEC>   -> str id = new str <FN_CALL>
```
<hr>

### Array Declaration

```xml
<ARR_DEC>       -> [ ] <MUL_ARR_LIST> id = new <IS_OBJ_DT> [ <EXPR> ] <MUL_ARR_DEC> 
<MUL_ARR_LIST>  -> [ ] <MUL_ARR_LIST> | null
<IS_OBJ_DT>     -> id | dt
<MUL_ARR_DEC>   -> [ <EXPR> ] <MUL_ARR_DEC> | null
```

<br>
<hr><hr><hr>

<!--
*
*
*
-->

```xml
<FINAL>     -> const | null
<ACCESSMOD> -> protected | private | null
<ABS_FINAL> -> Abstract | const | null
<STATIC>    -> static | null
<FN_ST>     -> ( <PAR> ) <!--used in function declaration-->
<FN_CALL>   -> ( <ARG> ) <!--used in function calling-->
<NEW_OBJ>   -> new id <FN_CALL> <!--after = or cma(+= etc) also in array const-->
```
