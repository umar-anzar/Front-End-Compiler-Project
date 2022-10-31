# Mio Programming Language
> - By 
>   - **B19102104 Muhammad Umar Anzar**
>   - **B19102048 Izhan Nadeem**
>   - **B19102004 Abdul Muqsit**
> - University Of Karachi 
> - UBIT department of computer science
> - Subject Compiler Construction

## Description
Project: Front End Compiler Project


## Language New Keywords
| Java/C#  | Mio       | Java/C#          | Mio                    |
|----------|-----------|------------------|------------------------|
| if       | if        | int              | int                    |
| else     | else      | double           | point                  |
| while    | loop till | char             | char                   |
| do       | do till   | byte             | excluded               |
| for      | excluded  | float            | excluded               |
| forEach  | loop thru | string           | str                    |
| switch   | shift     | boolean          | bool                   |
| case     | state     | long             | excluded               |
| default  | default   | dynamic /var     | val                    |
| continue | cont      | comment          | @comment               |
| break    | stop      | comment          | @@multi-line comment@@ |
| return   | ret       | line terminator; | ;                      |
| void     | implicit  |                  | def                    |
| main     | begin     | true/false       | true/false             |

| Java/C#    | Mio                         | Java/C# | Mio     |
|------------|-----------------------------|---------|---------|
| class      | Class                       | try     | test    |
| interface  | excluded                    | catch   | except  |
| abstract   | Abstract                    | finally | finally |
| extends    | Class name(inherited class) | throw   | raise   |
| final      | const                       | throws  | raises  |
| implements | no interface                |         | in      |
| this       | Self                        | import  | import  |
| super      | Parent                      | package | package |
| public     | implicit                    | null    | NaN     |
| private    | $$identiferName             |         |         |
| protected  | $identiferName              |         |         |
| static     | Static                      |         |         |
| new        | new                         |         |         |

## Classification of Lexemes

### Operator

Same: 
- Precedence
- Associativity
- Syntactically replaceable

| Relational | inc_dec | not | pm | mdm | power | simple assignment | compound assignment | and | or   |
|------------|---------|-----|----|-----|-------|-------------------|---------------------|-----|------|
| <          | ++      | !   | +  | *   | ^     | =                 | +=                  | &&  | \|\| |
| >          | --      |     | -  | /   |       |                   | *=                  |     |      |
| <=         |         |     |    | %   |       |                   | -=                  |     |      |
| >=         |         |     |    |     |       |                   | %=                  |     |      |
| !=         |         |     |    |     |       |                   |                     |     |      |
| ==         |         |     |    |     |       |                   |                     |     |      |



### Keyword

Same:
- Syntactically replaceable

| DT    | String | var |
|-------|--------|-----|
| int   | str    | var |
| point |        |     |
| char  |        |     |
| val   |        |     |
| bool  |        |     |

| if | else | loop | till | thru | do | class | boolConst  | null |
|----|------|------|------|------|----|-------|------------|------|
| if | else | loop | till | thru | do | Class | true/false | NaN  |

| switch | case | break | continue | return | static |
|--------|------|-------|----------|--------|--------|
| switch | case | stop  | cont     | ret    | Static |

| final | main  | in | default | new | abstract | super  | this |
|-------|-------|----|---------|-----|----------|--------|------|
| const | begin | in | default | new | abstract | Parent | Self |

| Three ps  | try  | catch  | finally | throw | throws | package | import |
|-----------|------|--------|---------|-------|--------|---------|--------|
| public    | test | except | finally | raise | raises | package | import |
| private   |      |        |         |       |        |         |        |
| protected |      |        |         |       |        |         |        |


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
Syntax:

Begin {
    block of code
}
```

### Variable Definition and Initialization
```
Syntax:

declaration: 
dataType variableName;

assignment:
variableName = Constant;

declaration and assignment:
dataType variableName = Constant;
```

### Type Casting
```
Syntax:
convt(DT) value

int x = 2 * convt(int)3.9        @ Here 3.9 is converted into 3 then * 2

int x = convt(int) 2.9 * 32      @ Here 2.9 * 32 then converted into integer
```

### Conditional Statement
- IF AND ELSE

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
- SWITCH CASE

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

- While Loop

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

- For Loop (iterator)

```
Syntax:

loop  thru ( DT i in (initial, final, incremental) ) {
    block of code
}
loop  thru ( int i in (0,5,1) ) {
    block of code
}
```

### Function

- Procedure

```
Syntax:

def functionName(parameters,comma,separated) {
	block of code
}

def foo(int a, point b) {

}
```

- Function
    - returnType is dataType

```
def returnType Identifer(parameters,comma,separated) {
	block of code
	ret value;
}

def str foo(str x) {
	block of code
	ret x;
}
```

### Class

- Class Structure

```
Syntax:

Class className(){

}
Class className(inheritedClass){

}
Class className(inheritedClass1,nheritedClass2){

}
Class className <Parameter> (inheritedClass1,nheritedClass2){

}
```


- Abstract class and function

```
abstract Class Shape{  
    abstract draw();  
}  
```

- Example code

```
Syntax:

Class Car(){
    
    dataType attribute;         @public
    static dataType attribute;  @static
    dataType $attribute;        @private
    dataType $$attribute;       @protected
	
    @This function is public and void
	def foo(dataType variable1,dataType variable2) {
		@block of code
	}
    
    @This function is private and returnType is there
    def dataType $foo1(dataType variable1,dataType variable2) {
        @block of code
        ret variable1+variable2;
    }

    @This function is protected and returnType is there
    def dataType $$foo2(dataType variable1,dataType variable2) {
        @block of code
        ret variable1+variable2;
    }
}
```

- Parametric class

```
Syntax:

begin 
{
    Polygone<A> x = new Box<A>();
    Polygone<str> x_alpha = new Box<str>();
    
    x.add(new A(10));
    x_alpha.add(new str("Hello World"));
}

Class Polygone<T>(){
   T $t; @private variable

   add(T t) {
      this.t = t;
   }

   T get() {
      return t;
   }   
}
```

- Example code of multiple inheritances

```
Syntax:

Class JDM(){
	static dataType attribute;
	dataType $$attribute;
}

Class Supra(Car, JDM){	//Car and JDM classes are inherited
	dataType attribute;
}

Class UnitedBravo(Car){	//Car classe are inherited
	dataType attribute;
}
```


- Example of code

```
package project.src;

import Properties.Registration;
import Random.^;

Abstract Class $$Vehicle () {
    str $type;
    str model;
    str name;
    Registration $register = NaN;
    int wheelers = 2;   @ default bike
    
    def Vehicle(str type, str city, str licenseNo, int wheels) {
        this.type = type;
        register = new Registration(city, licenseNo);
    }

    def Vehicle(str type, str city_name, str license_number, int wheels) {
        this.type = type;
        register = new Registration(city_name, license_number);
        wheelers = wheels;
    }

    def str getType() {
        ret this.type;
    }
    def Abstract int getWheelers();

    def str getModel() {
        ret this.model;
    }
    def str getName() {
        ret this.name;
    }
    def str getRegisteration() {
        ret this.register;
    }

}


Class Car (Vehicle,Vehicle) {
    point engine_size;

    def Car(str type, str city_name, str license_number,point engineSize) {
        Parent(type,new Registration(city_name, license_number),4);
        engine_size = engineSize;
    }
    
    def int getWheelers() {
        ret Parent.wheelers;
    }

    def str[] information () {
        ret new str[] {Parent.getType(),Parent.getModel(),
        Parent.getName(),Parent.getRegistration(),
        convt(str) getWheelers()};
    }
}

def str printInformation(str [] info) raises Exception  {
    str information = new str();
    loop thru (int i in info) {
        information += info[i];
    }
    return information;
}

def Car bmw(str model,str city) {
    ret Car("BMW", city, "ABC-" + convt(str) (random()*1000), 10.4);
}

begin {
    
    Car car1 = bmw("2018","Karachi");
    Car car2 = Car("2017", "Islamabad", "MFUBIT-2018",95);

    test {
        str info = printInformation(car2.information());
    } except (Mio.lang.Exception e) {
        raise new Exception();
    }
}


```