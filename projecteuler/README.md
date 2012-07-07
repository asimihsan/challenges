#Test Driven Euler

## Introduction

This repository is me learning test-driven development in Scala by solving Project Euler problems.

## Methodology

-	Write a test for the problem's hint, which is typically a trivial subset of the problem. Use [ScalaTest FunSuite](http://www.scalatest.org/getting_started_with_fun_suite). Put the test in `src/test/scala/ProblemXXXSuite.scala`.
-	Write a trivial problem solution that, in a hard-coded fashion, returns the expected trivial answer.
-	Run the test by executing `sbt test`. It should pass.
-	Remove the hard-coded answer. Run the test. It should fail.
-	Solve the problem. Call the solution from the main method. Put the code in `src/main/scala/ProblemXXX.scala`.
-    Run both the test and solution by executing: `sbt test run`.
-	??
-	Profit.

## TODO

-	Annoying, but I can't figure out how to tell `sbt` to only test/run a particular main function. It's forcing me to run all tests, pick a main function, run a particular main function.
	-	I don't want to make masses of sub-projects; I want one project with masses of main functions. If I have masses of sub-projects I think I need to explicitly state the existence of all sub-projects, which sucks.

