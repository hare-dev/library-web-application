package com.haredev.library

import groovy.transform.CompileStatic

import java.time.Clock
import java.time.ZoneId

import static pl.amazingcode.timeflow.TestTime.testInstance
import static pl.amazingcode.timeflow.Time.instance

@CompileStatic
class TimeManager {

    static void resetClock() {
        testInstance().resetClock();
    }

    static Clock setClock() {
        final ZoneId ZONE_ID = TimeZone.getTimeZone("Europe/Warsaw").toZoneId();
        return Clock.fixed(instance().now(), ZONE_ID);
    }
}
