package tutorial

import spray.json._
import DefaultJsonProtocol._

object Main {

  abstract class Tree
  case class Sum(l: Tree, r: Tree) extends Tree
  case class Mul(l: Tree, r: Tree) extends Tree
  case class Var(n: String) extends Tree
  case class Const(v: Int) extends Tree

  def main(args: Array[String]): Unit = {

  case class ope(operation: String,expre: String)

  object MyJsonProtocol extends DefaultJsonProtocol {
    implicit def opFormat = jsonFormat2(ope.apply)
   // implicit val oper = jsonFormat4(Color)
  }

import MyJsonProtocol._

  val source = """{"operation": "sum","expre":"Sum(Sum(Const(2),Const(2)),Mul(Const(7),Const(2)))"}"""
  val jsonAst = source.parseJson

  //val json = Color("CadetBlue", 95, 158, 160).toJson
  val jak = jsonAst.convertTo[ope]
    
   println(jak.expre)
   //val exp: Tree =color.expre



  }

}

