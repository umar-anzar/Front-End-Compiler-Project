## Syntax Analyze Phase

Writing context free grammar

### Declaration

In Main Function
There is no access modifer nor static

```
<DEC>       -> <PROP> ID <INIT> <LIST>
<DEC>       -> ID =
<PROP>      -> const DT | DT
<INIT>      -> = <TYPECAST> ID <INIT> | = <expression> | null
<TYPECAST>  -> DT <- | null
<LIST>      -> , ID <INIT> <LIST> | ;
```

