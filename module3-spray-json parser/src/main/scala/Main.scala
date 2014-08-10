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















   /*

    val source = """{ "operation": "www", "ni": "fff"}"""
    val jsonAst = source.parseJson

 case class Person(
      operation: String,
      ni:String
      );
  implicit val formats = jsonFormat4(Person)
    //val json = jsonAst.prettyPrint

    //val json1 = Color("CadetBlue", 95, 158, 160).toJson
    //val color = json1.convertTo[Color]
    
   val myObject = jsonAst.convertTo[Person]

    //val ()
    println(myObject.operation)
   

    //val person = source.asJson.convertTo[Person]
    //println(person)

   // println(jsonAst);
   */



  }

}

