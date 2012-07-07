package com.euler.problem3.main

import scala.annotation.tailrec
import scala.math.BigInt

import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._
import akka.dispatch.Await
import akka.pattern.ask
import akka.util.Timeout

sealed trait Message
case object Calculate extends Message
case class WorkerResult(number: BigInt, start: BigInt, stop: BigInt, value: List[BigInt]) extends Message
case class MasterResult(number: BigInt, primeFactors: List[BigInt]) extends Message
case class Work(number: BigInt, start: BigInt, stop: BigInt) extends Message

class Worker extends Actor {
    def receive = {
        case Work(number, start, stop) =>
            println("worker got message. number: %s, start: %s, stop: %s".format(number, start, stop))
            val result = calculatePrimeFactors(number, start, stop)
            println("worker sends result. numer: %s, start: %s, stop: %s, result: %s".format(number, start, stop, result))
            sender ! WorkerResult(number, start, stop, result)
    } // def receive

    val IS_PROBABLE_PRIME_CERTAINTY = 10
    val ONE = BigInt(1)
    val ZERO = BigInt(0)

    def calculatePrimeFactors(number: BigInt,
                              start: BigInt,
                              stop: BigInt): List[BigInt] = {
        calculatePrimeFactors(number, start, stop, start, List[BigInt]())
    }

    @tailrec
    final def calculatePrimeFactors(number: BigInt,
                                    start: BigInt,
                                    stop: BigInt,
                                    current: BigInt,
                                    acc: List[BigInt]): List[BigInt] = {
        if (current >= stop)
            return acc
        if ((number % current == ZERO) && (isPrime(current)))
            calculatePrimeFactors(number, start, stop, current + ONE, current :: acc)
        else
            calculatePrimeFactors(number, start, stop, current + ONE, acc)
    } // calculatePrimeFactors

    def isPrime(integer: BigInt): Boolean = {
        integer.isProbablePrime(IS_PROBABLE_PRIME_CERTAINTY)
    }

} // class Worker

class Master(numberOfWorkers: Int,
             integer: BigInt,
             listener: ActorRef) extends Actor {

    var numberOfResults: Int = _
    var main_sender: ActorRef = _
    val divisor = integer / numberOfWorkers
    val remainder = integer % numberOfWorkers
    var workSchedule: List[(BigInt, BigInt, BigInt)] = List()
    for (x <- 0 until numberOfWorkers) {
        var start = divisor * x
        var stop = start + divisor
        if (x == (numberOfWorkers - 1)) {
            stop += remainder
        }
        workSchedule = (integer, start, stop) :: workSchedule
    }
    println("workSchedule: %s".format(workSchedule))
    var results: List[BigInt] = List()

    val workerRouter = context.actorOf(
        Props[Worker].withRouter(RoundRobinRouter(numberOfWorkers)),
        name = "workerRouter")
    
    def receive = {
        case Calculate =>
            println("master starting workers")
            main_sender = sender
            workSchedule foreach { case (integer, start, stop) =>
                workerRouter ! Work(integer, start, stop)
            }
        case WorkerResult(number, start, stop, value) =>
            println("master gets result. number: %s, start: %s, stop: %s, value: %s".format(number, start, stop, value))
            numberOfResults += 1
            results = value ::: results
            if (numberOfResults == numberOfWorkers) {
                listener ! MasterResult(integer, results)
                main_sender ! results
                context.stop(self)
            }
    }
}

class Listener extends Actor {
    def receive = {
        case MasterResult(integer, results) =>
            println("prime factors: %s".format(results))
            context.system.shutdown()
    } // def receive
}

class Problem3 {

} // class Problem3

object Problem3 {
    def main(args: Array[String]) = {
        val number = BigInt("600851475143")
        val answer = Problem3.primeFactors2(number)
        println("Problem 3 answer: %s".format(answer))
    }

    def primeFactors2(input: BigInt): BigInt = {
        @tailrec
        def loop(factor: BigInt, number: BigInt): BigInt = {
            if (number == factor)     factor else
            if (number % factor == 0) loop(factor, number / factor)
            else                      loop(factor + 1, number)
        }
        loop(BigInt(2), input)
    }

    def primeFactors(input: BigInt): List[BigInt] = {
        val system = ActorSystem("PrimeFactorSystem")
        val listener = system.actorOf(Props[Listener], name="listener")
        val master = system.actorOf(Props(new Master(
            numberOfWorkers = 4, integer = input, listener = listener)),
            name = "master")
        println("primeFactors starting with input: %s".format(input))
        implicit val timeout = Timeout(60 seconds)
        val future = master ? Calculate
        val result = Await.result(future, timeout.duration).asInstanceOf[List[BigInt]]
        println("result: %s".format(result))
        result
    }
} // object Problem3

// vim: set ts=4 sw=4 et:
