This directory contains the modern Java functional programming
examples from my online courses at Vanderbilt.  Many examples use
features found in later Java releases, so I recommend installing Java
18 (at least) to avoid compatibility problems.

Here's an overview of what's currently included in these examples:

. ex0 - This example shows two zap*() method implementations that
        remove a string from a list of strings.  One method uses
        traditional Java 7 features and the other uses basic modern
        Java features.

. ex1 - This example use the Java streams framework to generate and
        check sMAX_COUNT positive odd random numbers and print which
        are prime and which are not.

. ex2 - This program implements four ways of computing factorials for
        BigIntegers to demonstrate the correctness and performance of
        different parallel and sequential algorithms that use
        immutable or mutable Java objects.  It also shows (1) the
        dangers of sharing unsynchronized mutable state between
        threads and (2) the overhead of excessive synchronization of
        shared mutable state.  Java sequential and parallel streams
        are used in this program to compose both "pure" functions and
        functions with side effects.

. ex3 - This example demonstrates benefits of combining
        object-oriented and functional programming in modern Java.  It
        uses the Java parallel streams framework to compose a pipeline
        of functions that check sMAX_COUNT positive odd random numbers
        in parallel to determine which are prime and which are not.

. ex4 - This example shows various ways to create a Java Thread and
        pass a Runnable lambda expression to it.

. ex5 - This example shows how to sort elements in a collection using
        a Java anonymous inner class, lambda expression, and method
        reference.  It also shows how to use the modern Java forEach()
        method for Streams and collections.

. ex6 - This example shows how to use modern Java lambda expressions
        to create a closure, which is a persistent scope that holds on
        to local variables even after the code execution has moved out
        of that block.


