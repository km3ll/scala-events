package me.khazaddum.events

/**
 * Reference
 * https://github.com/debasishg/frdomain/blob/master/src/main/scala/frdomain/ch8/cqrs/lib/EventStore.scala
 */
trait EventStore[K] {

  def get( key: K ): List[Event[_]]

  def put( key: K, event: Event[_] ): Either[String, Event[_]]

  def events( key: K ): Either[String, List[Event[_]]]

  def allEvents(): Either[String, List[Event[_]]]

}