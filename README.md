# Lazy Reflect Calculator

This is a calculator with lazy evaluation which can perform different operations on a set of registers.
As an exercise I've tried utilizing reflection to simplify extending the calculator 
with additional operations, for example, division. So all that's needed is to create a class implementing
operation in the operations package, and it will be available for use in the calculator.

It's not the best solution for finding the available classes and there are several libraries to simplyfy this,
but I wanted to try to avoid external dependencies.

## Assumptions

- When a cycle is detected, for example if A add B add C add A. In this case A will not be evaluated again but 
calculation will otherwise continue as usual.
- Values are in floating point and not integer in order to support more operations, such as division.
- New Registers will have a value of zero, so a one line of 'Print A' will output 0.0

## Source code 

The source code is available in the src folder

## Running

A prebuilt version built using IntelliJ and openJDK17 is available in either out/production/lazy-reflect-calculator or lazy-reflect-calculator-prebuilt
There's two ways to run, either interactive or file mode.

To run in interactive mode use
```
java lazyCalculator
```
and you will be presented with instructions on how to use the calculator.
To run in file mode use
<pre>
java lazyCalculator <i>filename</i>
</pre>
where *filename* is a text file containing different commands, the 
textfile does not need to contain *quit* in order to exit and will just run its course