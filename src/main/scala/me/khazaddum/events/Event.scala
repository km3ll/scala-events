package me.khazaddum.events

import java.util.{ Calendar, Date }

/**
 * Reference
 * https://github.com/debasishg/frdomain/blob/master/src/main/scala/frdomain/ch8/cqrs/memrepo/AccountService.scala
 */
trait Event[A] {
  def at: Date
}

case class Created( at: Date, item: Item ) extends Event[Item]
case class Updated( at: Date, item: Item ) extends Event[Item]
case class Deleted( at: Date, item: Item ) extends Event[Item]

object Event {

  private val today: Date = Calendar.getInstance.getTime

  def created( item: Item ): Created = Created( today, item )
  def updated( item: Item ): Updated = Updated( today, item )
  def deleted( item: Item ): Deleted = Deleted( today, item )

}