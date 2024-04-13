package com.haredev.library.configuration.time

import groovy.transform.CompileStatic

import java.time.Clock
import java.time.Duration
import java.time.ZoneId
import java.time.temporal.ChronoUnit

import static pl.amazingcode.timeflow.TestTime.testInstance
import static pl.amazingcode.timeflow.Time.instance

@CompileStatic
trait TestTimeProvider {
    static void resetClock() {
        testInstance().resetClock();
    }

    static Clock setClock() {
        final ZoneId ZONE_ID = TimeZone.getTimeZone("Europe/Warsaw").toZoneId();
        return Clock.fixed(instance().now(), ZONE_ID);
    }

    static final void jumpInDaysForward(final Integer days) {
        final Duration duration = Duration.of(days, ChronoUnit.DAYS)
        testInstance().fastForward(duration)
    }
}