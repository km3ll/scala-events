package me.khazaddum.events

import org.scalatest.{ FlatSpec, Matchers }
import testkit.Generator._
import java.util.UUID

class EventStoreTest extends FlatSpec with Matchers {

  "EventStoreInMemory" should "put an event" in {

    // Given
    val store = EventStoreInMemory[String]
    val key = UUID.randomUUID().toString
    val event = GenEvent.sample.get

    // When
    val result = store.put( key, event )

    // Then
    result.isRight shouldBe true
    result.right.get == event shouldBe true

  }

  it should "get an event" in {

    // Given
    val store = EventStoreInMemory[String]
    val key = UUID.randomUUID().toString
    val event = GenEvent.sample.get
    store.put( key, event )

    // When
    val events = store.get( key )

    // Then
    events.size shouldEqual 1
    events.headOption.contains( event ) shouldBe true

  }

  it should "get multiple events" in {

    // Given
    val store = EventStoreInMemory[String]
    val key1 = UUID.randomUUID().toString
    val key2 = UUID.randomUUID().toString
    val event1 = GenEvent.sample.get
    val event2 = GenEvent.sample.get
    val event3 = GenEvent.sample.get
    store.put( key1, event1 )
    store.put( key1, event2 )
    store.put( key2, event3 )

    // When
    val events = store.get( key1 )

    // Then
    events.size shouldEqual 2
    events.contains( event1 ) shouldBe true
    events.contains( event2 ) shouldBe true
    events.contains( event3 ) shouldBe false

  }

  it should "get all events" in {

    // Given
    val store = EventStoreInMemory[String]
    val key1 = UUID.randomUUID().toString
    val key2 = UUID.randomUUID().toString
    val event1 = GenEvent.sample.get
    val event2 = GenEvent.sample.get
    store.put( key1, event1 )
    store.put( key2, event2 )

    // When
    val events = store.allEvents()

    // Then
    events.isRight shouldBe true
    events.right.get.size shouldEqual 2
    events.right.get.contains( event1 ) shouldBe true
    events.right.get.contains( event2 ) shouldBe true

  }

  it should "not fail when getting from empty" in {

    // Given
    val store = EventStoreInMemory[String]
    val key = UUID.randomUUID().toString

    // When
    val result = store.get( key )

    // Then
    result.isEmpty shouldBe true

  }

  it should "not fail when key not found" in {

    // Given
    val store = EventStoreInMemory[String]
    val key = UUID.randomUUID().toString
    val event = GenEvent.sample.get
    store.put( key, event )

    // When
    val result = store.get( UUID.randomUUID().toString )

    // Then
    result.isEmpty shouldBe true

  }

}