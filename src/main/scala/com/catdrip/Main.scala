package com.catdrip

import java.sql.Date

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.javadsl.SlickSession
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.Sink
import org.slf4j.LoggerFactory
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContextExecutor

case class Hospital(
                     uniqueKey: Long,
                     createdAt: Date,
                     updatedAt: Option[Date],
                     state: String,
                     city: String,
                     address: String,
                     name: String,
                     phoneNumber: String,
                     id: Option[Long] = Option.empty
                   )

class HospitalRepository(val driver: JdbcProfile) {

  import driver.api._

  val pkType = implicitly[BaseTypedType[Long]]
  val tableQuery = TableQuery[Hospitals]
  type TableType = Hospitals

  def findAll = tableQuery.result

  class Hospitals(tag: Tag) extends Table[Hospital](tag, "hospital") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def uniqueKey = column[Long]("unique_key")

    def createdAt = column[Date]("created_at")

    def updatedAt = column[Option[Date]]("updated_at")

    def state = column[String]("state")

    def city = column[String]("city")

    def address = column[String]("address")

    def name = column[String]("name")

    def phoneNumber = column[String]("phone_number")

    def * = (uniqueKey, createdAt, updatedAt, state, city, address, name, phoneNumber, id.?) <> ((Hospital.apply _).tupled, Hospital.unapply)
  }

}


object Main extends App {
  val logger = LoggerFactory.getLogger(Main.getClass)
  println(s"Current time = ${System.currentTimeMillis}")

  implicit val system: ActorSystem = ActorSystem("catdrip")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  implicit val session: SlickSession = SlickSession.forConfig("slick-postgres")
  system.registerOnTermination(() => session.close())

  val hospitalRepository = new HospitalRepository(session.profile)
  val tableQuery = hospitalRepository.tableQuery
  Slick.source(hospitalRepository.findAll)
    .log("hospital")
    .runForeach {
      println(_)
    }

}

