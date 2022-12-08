package testkit

import me.khazaddum.events.{ Event, Item }
import org.scalacheck.Gen

/**
 * Reference
 * https://github.com/typelevel/scalacheck/blob/main/doc/UserGuide.md
 */
object Generator {

  val GenName: Gen[String] = Gen.oneOf( "Americano", "Cappuccino",
    "Espresso", "Latte", "Macciato", "Mocha" )

  val GenItem: Gen[Item] = for {
    no <- Gen.choose[Long]( 1, 9999 )
    name <- GenName
    isActive <- Gen.oneOf( true, false )
  } yield Item( no, name, isActive )

  val GenCreated: Gen[Event[Item]] = GenItem.map( Event.created )
  val GenUpdated: Gen[Event[Item]] = GenItem.map( Event.updated )
  val GenDeleted: Gen[Event[Item]] = GenItem.map( Event.deleted )

  val GenEvent: Gen[Event[Item]] = Gen.frequency(
    ( 1, GenCreated ),
    ( 1, GenUpdated ),
    ( 1, GenDeleted )
  )

}