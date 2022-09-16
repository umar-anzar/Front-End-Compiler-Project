## Syntax Analyze Phase

Writing context free grammar

r: right
w: wrong

### Declaration

In Main Function
There is no access modifer nor static

```md
<DEC>       -> <PROP> ID <INIT> <LIST>
<PROP>      -> const DT | DT
<INIT>      -> = ID <INIT> | = <expression> | null
<LIST>      -> , ID <INIT> <LIST> | ;
```

Example:
const int x = y = a + 5, t = 3;         r
int x = y = int <- a + 5, t = q = 2;    r
int x = int <- y = a + 5, t = q = 2;    w
x = y = a + 5, t = 3;                   w






TYPECAST  -> DT <- | null

'+ -'
'* \ %'
```md
<E>     -> <E> PM <T>
<E>     -> <T>
<T>     -> <T> MDM <F>
<T>     -> <F>
<F>     -> <operands>
```

||
&&
ROP
PM
MDM
Power

Left Recursive
```md
<C>     -> <C> || <E>
<C>     -> <E>
<E>     -> <E> && <F>
<E>     -> <F>
<F>     -> <F> ROP <G>
<F>     -> <G>
<G>     -> <G> PM <H>
<G>     -> <H>
<H>     -> <H> ROP <I>
<H>     -> <I>
<I>     -> <I> MDM <J>
<I>     -> <J>
<J>     -> <J> Power <K>
<J>     -> <K>
<K>     -> <operands>
```





