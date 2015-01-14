package pl.setblack.paint.util

import java.util.function.Supplier

import pl.setblack.airomem.core.{Command, Query, VoidCommand}


object JavaIntegration {

  implicit def query[Q,R]( f: (Q) => R):Query[Q,R] = new Query[Q,R] {
    def evaluate(q: Q) : R = {
      return f(q);
    }
  }

  implicit def cmd[S]( f: (S) => Unit):VoidCommand[S] = new VoidCommand[S] {
    def executeVoid(s: S)  = {
      f(s);
    }
  }
  implicit def cmdQuery[S,R]( f: (S) => R):Command[S,R] = new Command[S,R] {
    def execute(s: S):R  = {
      f(s);
    }
  }

  implicit def supplier[S](f: () => S):Supplier[S] = new Supplier[S] {
      def get(): S = {
      println("supplier called")
      return f()
    }
  }

}
