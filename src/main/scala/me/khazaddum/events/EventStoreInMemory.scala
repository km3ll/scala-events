package me.khazaddum.events

import scala.collection.concurrent.TrieMap
import cats.syntax.either._

/**
 * Reference
 * Stack Overflow
 * https://stackoverflow.com/questions/29499381/what-is-a-triemap-and-what-is-its-advantages-disadvantages-compared-to-a-hashmap
 * Concurrent Tries with Efficient Non-Blocking Snapshots
 * https://www.researchgate.net/publication/221643801_Concurrent_Tries_with_Efficient_Non-Blocking_Snapshots
 */

object EventStoreInMemory {

  def apply[K]: EventStore[K] = new EventStore[K] {

    private val log = TrieMap[K, List[Event[_]]]()

    def get( key: K ): List[Event[_]] = {
      log.getOrElse( key, List.empty[Event[_]] )
    }

    def put( key: K, event: Event[_] ): Either[String, Event[_]] = {
      val currentLog = log.getOrElse( key, List.empty[Event[_]] )
      try {
        log += ( key -> ( event :: currentLog ) )
        event.asRight
      } catch {
        case ex: Exception => s"Error putting key $key: ${ex.getMessage}".asLeft
      }
    }

    def events( key: K ): Either[String, List[Event[_]]] = {
      val currentLog = log.getOrElse( key, List.empty[Event[_]] )
      if ( currentLog.isEmpty ) s"Event with key $key does not exist".asLeft
      else currentLog.asRight
    }

    def allEvents(): Either[String, List[Event[_]]] = {
      try {
        log.values.toList.flatten.asRight
      } catch {
        case ex: Exception => s"Error getting all events: ${ex.getMessage}".asLeft
      }
    }

  }

}