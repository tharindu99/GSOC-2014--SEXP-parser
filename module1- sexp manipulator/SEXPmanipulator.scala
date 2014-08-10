object Manipulator  {

/* Manipulator is analyze the given Expression and evaluate the result.
*  SEXP Parser it may use this to resolve the given sexp expression. 
*  Future enhancements – To improve and create new mathematical operations 
*  for the raster data manipulations , this interface
*  can be develop to do that job as a middle layer of 
*  gotrills and respective client application
*
* Code written by Tharindu Madushanka Peiris 
* for SEXP parser module 1st stage
*/

abstract class Tree

/* absrtact tree class include the structure of the expression 
* it's a trees structure and manipulation may done bottom to up
* then no any mathamatical operation presidences problem with that 
* because the tree is identical for the expression and no any mabiguous
*/

case class ADD(l: Tree, r: Tree) extends Tree
case class MUL(l: Tree, r: Tree) extends Tree
case class SUB(l: Tree, r: Tree) extends Tree
case class DIV(l: Tree, r: Tree) extends Tree

case class Var(n: String) extends Tree
case class Const(v: Int) extends Tree

/*case class is one of special facility in scala language,
* we given our expression structurs in differnet forms by 
* extending the tree class 
* ADD,MUL,SUB,DIV are the basic mathamatical operations and 
* Var can be use to assign variable and Const can be use to 
  assign constant value to the expression.
*/

type Environment = String => Double

/* Environment use to assign variables in to the expression
*  then it may allow to dymnamic manipulations throw 
*  given expression. I use String to Double convention 
*  for that, but for the raster data manipulation it can 
*  use as String to String or String to Int convention.
*/

def eval(t: Tree, env: Environment): Double = t match {
  case ADD(l, r) => eval(l, env) + eval(r, env)
  case MUL(l, r) => eval(l, env) * eval(r, env)
  case SUB(l, r) => eval(l, env) - eval(r, env)
  case DIV(l, r) => eval(l, env) / eval(r, env)
  
  case Var(n)    => env(n)
  case Const(v)  => v
}

/*evaluate the operation by using recurtion manner is done
* by here.Because expression can be have some multilevel operations, 
* then have to go for recurtion method to achive that. 
*/

  def main(args: Array[String]) {
    
    val exp: Tree = SUB(DIV(ADD(Var("x"),Const(2)),MUL(Var("y"),Const(2))),Const(3))
    /* parse the expression to the tree class here 
    * expression should be in fallowing format to evaluate
    * example : val exp: Tree = SUB(DIV(ADD(Var("x"),Const(2)),MUL(Var("y"),Const(2))),Const(3))
    *
    * if you use Var to the expression, have to initialise the corresponding
    * variable with value in side the Environment as bellow,
    * example : val env: Environment = { case "x" => 5.2 case "y" => 7 }
    */
    
    val env: Environment = { case "x" => 5.2 case "y" => 7 }
    
    println()
  
    //print the output of the evaluation as an result of final
  
    println("Expression: " + exp)
  
    println("Evaluation with x=5, y=7: " + eval(exp, env))
    
  }
}
/* code written by Tharindu Maduahanka */
