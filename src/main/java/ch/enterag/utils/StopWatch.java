/*== StopWatch.java ====================================================
StopWatch implements a utility for performance testing.
Version     : $Id: StopWatch.java 1212 2010-09-02 12:00:26Z hartwig $
Application : Utilities
Description : StopWatch implements a utility for performance testing.
------------------------------------------------------------------------
Copyright  : Enter AG, Zurich, Switzerland, 2010
Created    : 30.08.2010, Hartwig Thomas
======================================================================*/
package ch.enterag.utils;

import lombok.Getter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * StopWatch implements time measuring for performance assessment.
 *
 * @author Hartwig Thomas
 */
public class StopWatch {
    private static final DecimalFormat dfLong = new DecimalFormat("#,##0");
    private static final DecimalFormat dfDouble = new DecimalFormat("#,##0.00");
    private static DecimalFormatSymbols dfsEurope = new DecimalFormatSymbols();

    private long msStart = 0;
    @Getter
    private long msAccumulated = 0;

    public StopWatch() {
        dfsEurope.setGroupingSeparator('\'');
        dfsEurope.setDecimalSeparator('.');
        dfLong.setDecimalFormatSymbols(dfsEurope);
        dfDouble.setDecimalFormatSymbols(dfsEurope);
    }

    /**
     * @return StopWatch instance.
     * @deprecated Use {@link #StopWatch()} to create instances.
     */
    @Deprecated
    public static StopWatch getInstance() {
        return new StopWatch();
    }

    /**
     * Helper function to format a long.
     *
     * @param longValue long to be formatted.
     * @return String for long with a thousand's separator.
     */
    public static String formatLong(long longValue) {
        return dfLong.format(longValue);
    }

    /**
     * Notes the starting time in milliseconds.
     */
    public void start() {
        msStart = System.currentTimeMillis();
    }

    /**
     * Returns the time since start after having added it to the
     * accumulated time.
     *
     * @return Time since start in milliseconds.
     */
    public long stop() {
        long interval = System.currentTimeMillis() - msStart;
        msAccumulated += interval;
        return interval;
    }

    /**
     * Helper function to format accumulated milliseconds.
     *
     * @return String for long with a thousand's separator.
     */
    public String formatMs() {
        return formatLong(msAccumulated);
    }

    /**
     * Helper function to compute and format a rate (units/ms).
     *
     * @param units Units for which a rate (presumably per millisecond) is to be computed.
     * @param duration Duration (presumably per millisecond) for units.
     * @return String for rate with two decimals and a thousand's separator.
     */
    public String formatRate(long units, long duration) {
        double rate = units;
        if (duration > 0) rate = rate / duration;
        else rate = 0.0;
        return dfDouble.format(rate);
    }

    /**
     * Helper function to compute and format a rate (units/ms).
     *
     * @param units Units for which a rate (presumably per millisecond) is to be computed.
     * @return String for rate with two decimals and a thousand's separator.
     */
    public String formatRate(long units) {
        return formatRate(units, msAccumulated);
    }

}
