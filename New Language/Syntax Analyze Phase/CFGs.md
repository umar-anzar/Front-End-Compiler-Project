## Syntax Analyze Phase

Writing context free grammar

r: right
w: wrong




### Single/Multi Statement
```xml
<SST>   -> <IF_ELSE>    | <SHIFT>   | <INC_DEC_ST> ;    | <DEC>     |  
           <OBJ_DEC>    | <LOOP>    | <DO_WHILE> ;      | <BREAK> ; | 
           <CONTINUE> ; | <ASSIGN> ;| <TRY_CATCH_ST>    | <FN_CALL> | 
           <RET_ST> ;

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
<OPERAND>   -> <CONST> | <INDFRS> | <INC_DEC> <INDFRS> | <INDFRS> <OP1> | 
               <OBJ_AC_PROP>

<OP1>       -> <INC_DEC> | null
```
<hr>





### Identifer, Function call, array subscript (INDFRS)
```xml
<INDFRS>    -> id <AF>
<AF>        -> <SUBSCRIPT> | <FUNC_CALL> | null
<SUBSCRIPT> -> [ <EXPR> <SLICE> ]
<FUNC_CALL> -> ( <FC> )
<SLICE>     -> : <EXPR> | null
<FC>        -> <EXPR> <PAR_LIST> | null
<PAR_LIST>  -> , <EXPR> <PAR_LIST> | null
```
```
Example:
ID              | var_1 
Function call   | func(par1,par2) | helo_q()
array subscript | arr[2]          | arr[2:3]
```
<hr>




### Dot Separated Identifers
Access Part can end with ID, array subscript, and function call
```xml
<ACCESS_ID>     -> <INDFRS> <AP_DOT_LIST>
<AP_DOT_LIST>   -> dot <INDFRS> <AP_DOT_LIST> | null
```

Assignment Part end only with ID, array subscript
```xml
<ASSIGN_ID>     -> id <IS_ARR_FUNC> 
<IS_ARR_FUNC>   -> <IS_DOT> | <SUBSCRIPT> <IS_DOT> | <FUNC_CALL> dot <ASP_DOT_LIST>
<IS_DOT>        -> dot <ASP_DOT_LIST> | null
<ASP_DOT_LIST>  -> <INDFRS> dot <ASP_DOT_LIST> | <LAST_ID_ARR>
<LAST_ID_ARR>   -> id <ARRAY_NULL>
<ARRAY_NULL>    -> <SUBSCRIPT> | null
```
<hr>




### Declaration and Initialization

In Main Function
There is no access modifer nor static

```xml
<DEC>       -> <PROP> id <INIT> <LIST>
<PROP>      -> const dt | dt
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
<ASSIGN>        -> <ASSIGN_ID> <ASS_OP> <ASSIGN_LIST>
<ASSIGN1>       -> <ASS_OP> <ASSIGN_LIST> | null 
<ASSIGN_LIST>   -> <ASSIGN_ID> <ASSIGN1> | <EXPR> 
<ASS_OP>        -> = | cma
```

```
Example:
x += 2 + 3 * a          r
x = y -= a + 5;         r
x += y *= int <- a + 5; r
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

<BRACKETS>  -> ( <EXPR> )

<F>         -> <BRACKETS>
<G>         -> <BRACKETS>
<H>         -> <BRACKETS>
<I>         -> <BRACKETS>
<J>         -> <BRACKETS>
<K>         -> <BRACKETS>

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
<L>         -> <OPERANDS>|<BRACKETS>

<UNARY>     -> dt typeCast <UNARY> | not <UNARY>| null
```
<hr>




### Conditional Statement

if-else CFG
```xml
<IF_ELSE>   -> if(<EXPR>) <body> <OELSE>
<OELSE>     -> else <body> | null
```

switch-case statement:
```xml
<SHIFT> -> shift ( <EXPR> ) { <STATE> }
<STATE> -> state <EXPR> : <MST> <STATE> | null
```
















