## Syntax Analyze Phase

Writing context free grammar

r: right
w: wrong

### Declaration

In Main Function
There is no access modifer nor static

```md
<DEC>       -> <PROP> id <INIT> <LIST>
<PROP>      -> const dt | dt
<INIT>      -> = <INIT2> | null
<INIT2>     -> id <INIT> | <expression>
<LIST>      -> , id <INIT> <LIST> | ;
```
```
Example:
const int x = y = a + 5, t = 3;         r
int x = y = int <- a + 5, t = q = 2;    r
int x = int <- y = a + 5, t = q = 2;    w
x = y = a + 5, t = 3;                   w
```





### Expression

Precedance of Operators L to H
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
```md
<E>         -> <Brackets>|<E> or <F>
<E>         -> <F>

<Brackets>  -> ( <E> )

<F>         -> <Brackets>
<G>         -> <Brackets>
<H>         -> <Brackets>
<I>         -> <Brackets>
<J>         -> <Brackets>
<K>         -> <Brackets>

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
<L>         ->  <OPERANDS>|<BRACKETS>

<UNARY>     -> DT typeCast <UNARY> | not <UNARY>| null

```



















