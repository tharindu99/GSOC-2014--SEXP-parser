object Manipulator  {

abstract class Tree
case class ADD(l: Tree, r: Tree) extends Tree
case class MUL(l: Tree, r: Tree) extends Tree
case class SUB(l: Tree, r: Tree) extends Tree
case class DIV(l: Tree, r: Tree) extends Tree
case class Var(n: String) extends Tree
case class Const(v: Int) extends Tree

type Environment = String => Double

def eval(t: Tree, env: Environment): Double = t match {
  case ADD(l, r) => eval(l, env) + eval(r, env)
  case MUL(l, r) => eval(l, env) * eval(r, env)
  case SUB(l, r) => eval(l, env) - eval(r, env)
  case DIV(l, r) => eval(l, env) / eval(r, env)
  case Var(n)    => env(n)
  case Const(v)  => v
}

def main(args: Array[String]) {
  val exp: Tree = SUB(DIV(ADD(Var("x"),Const(2)),MUL(Var("y"),Const(2))),Const(3))
  val env: Environment = { case "x" => 5.2 case "y" => 7 }
  
  println()

  println("Expression: " + exp)

  println("Evaluation with x=5, y=7: " + eval(exp, env))
}
}
