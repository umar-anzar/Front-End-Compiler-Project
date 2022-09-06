# Mio Programming Language
> - By 
> - - **Muhammad Umar Anzar**
> - - **Izhan Nadeem**
> - - **Abdul Muqsit**
> - Seat No. **B19102104**
> - University Of Karachi 
> - UBIT department of computer science
> - Subject Computer Graphics

Front End Compiler Project

Project of Compiler Construction

## Language Keywords
| Java/C#  | Mio        | Java/C#           | Mio                    |
|----------|------------|-------------------|------------------------|
| if       | if         | int               | int                    |
| else     | else       | double            | point                  |
| while    | loop till  | char              | char                   |
| do       | do till    | byte              | excluded               |
| for      | excluded   | float             | excluded               |
| forEach  | loop thru  | string            | str                    |
| switch   | shift      | boolean           | bool                   |
| case     | state      | long              | excluded               |
| default  | default    | dynamic /var      | val                    |
| continue | cont       | comment           | @comment               |
| break    | stop       | comment           | @@multi-line comment@@ |
| return   | ret        | line terminator;  | ;                      |
| void     | implicit   |                   |                        |
| main     | begin/end  |                   |                        |

| Java/C#    | Mio                         | Java/C# | Mio     |
|------------|-----------------------------|---------|---------|
| class      | Class                       | try     | test    |
| interface  | excluded                    | catch   | except  |
| abstract   | Abstract                    | finally | finally |
| extends    | Class name(inherited class) | throw   | raise   |
| final      | const                       | throws  | raises  |
| implements | no interface                |         | in      |
| this       | Self                        |         |         |
| super      | Parent                      |         |         |
| public     | implicit                    |         |         |
| private    | $$                          |         |         |
| protected  | $                           |         |         |
| static     | static                      |         |         |
| new        | new                         |         |         |

## Classification

### Operator

Same: 
- Precedence
- Associativity
- Syntactically replaceable

| Relation   Operator | inc-dec | not | pm | mdm | power |
|---------------------|---------|-----|----|-----|-------|
| <                   | ++      | !   | +  | *   | ^     |
| >                   | --      |     | -  | /   |       |
| <=                  |         |     |    | %   |       |
| >=                  |         |     |    |     |       |
| !=                  |         |     |    |     |       |
| ==                  |         |     |    |     |       |

| simple assignment | compound assignment  | and | or   |
|-------------------|----------------------|-----|------|
| =                 | +=                   | &&  | \|\| |
|                   | *=                   |     |      |
|                   | -=                   |     |      |
|                   | %=                   |     |      |

### Keyword

Same:
- Syntactically replaceable

| DT    | String |
|-------|--------|
| int   | str    |
| point |        |
| char  |        |
| val   |        |
| bool  |        |

| if | else | while | do | for | in |
|----|------|-------|----|-----|----|
| if | else | while | do | for | in |


## Syntax

### Comment
```
Syntax:

@ Single Line Comment

@@ Multi
Line
comment @@
```

### Main Function
```
Begin {
    block of code
}
```

### Variable Definition and Initialization
```
Syntax:

declaration: 
dataType IdentifierName;

assignment:
IdentifierName = Constant;

declaration and assignment:
dataType IdentifierName = Constant;
```

### Conditional Statement
IF AND ELSE
```
Syntax:

if (condition) {
    block of code
}


if (condition) {
    block of code
} else {
    block of code
}

if (condition) {
    block of code
} else if (condition) {
    block of code
}
```
SWITCH CASE
```
Syntax:

shift(expression) {
    state x:
        code block
        stop;
    state y:
        code block
        stop;
    default:
        code block
}
```

### Loop

While Loop
```
Syntax:

loop till (condition) {
     code block
}

int i = 0;
loop till (i < 5) {
  block of code
  i++;
}
```

For Loop (iterator)
```
loop  thru ( DT i in (initial, final, incremental) ) {
    block of code
}
loop  thru ( int i in (0,5,1) ) {
    block of code
}
```


