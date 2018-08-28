package com.ef;

import com.ef.builder.ParserArgsBuilder;
import com.ef.factory.ParserArgsFactory;
import extensions.CaptureSystemOutput;
import org.junit.jupiter.api.Test;

import static extensions.CaptureSystemOutput.OutputCapture;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;

@CaptureSystemOutput
class ParserTest {
    private static final String USAGE_MESSAGE = "Usage: <main class> -duration=<duration> -startDate=<startDate>\n" +
            "                    -threshold=<threshold>\n" +
            "      -duration, --duration=<duration>\n" +
            "         'hourly' or 'daily'\n" +
            "      -startDate, --startDate=<startDate>\n" +
            "         Start date in format of 'yyyy-MM-dd.HH:mm:ss'\n" +
            "      -threshold, --threshold=<threshold>\n" +
            "         Any integer value";

    private void assertThatUsagePrinted(OutputCapture outputCapture) {
        outputCapture.expect(containsString(USAGE_MESSAGE));
    }

    private void assertThatUsageNotPrinted(OutputCapture outputCapture) {
        outputCapture.expect(not(containsString(USAGE_MESSAGE)));
    }

    @Test
    void should_fail_with_empty_parameters(OutputCapture outputCapture) {
        Parser.main(new ParserArgsBuilder().build());

        assertThatUsagePrinted(outputCapture);
        outputCapture.expect(containsString("Missing required options [--startDate=<startDate>, --duration=<duration>, --threshold=<threshold>]"));
    }

    @Test
    void should_fail_with_invalid_duration(OutputCapture outputCapture) {
        Parser.main(ParserArgsFactory.argsWithInvalidDuration());

        assertThatUsagePrinted(outputCapture);
        outputCapture.expect(containsString("Missing required option '--duration=<duration>'"));
    }

    @Test
    void should_fail_with_invalid_threshold(OutputCapture outputCapture) {
        Parser.main(ParserArgsFactory.argsWithInvalidThreshold());

        assertThatUsagePrinted(outputCapture);
        outputCapture.expect(containsString("Missing required option '--threshold=<threshold>'"));
    }

    @Test
    void should_fail_with_invalid_startDate(OutputCapture outputCapture) {
        Parser.main(ParserArgsFactory.argsWithInvalidStartDate());

        assertThatUsagePrinted(outputCapture);
        outputCapture.expect(containsString("Missing required option '--startDate=<startDate>'"));
    }

    @Test
    void should_succeed_with_valid_data(OutputCapture outputCapture) {
        Parser.main(ParserArgsFactory.validArgs());
        assertThatUsageNotPrinted(outputCapture);
    }
}