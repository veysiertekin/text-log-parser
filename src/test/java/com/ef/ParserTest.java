package com.ef;

import com.ef.builder.ParserArgsBuilder;
import com.ef.factory.ParserArgsFactory;
import extensions.CaptureSystemOutput;
import org.junit.jupiter.api.Test;

import static extensions.CaptureSystemOutput.OutputCapture;
import static org.hamcrest.core.StringContains.containsString;

@CaptureSystemOutput
class ParserTest {
    private static final String USAGE_MESSAGE = "Usage: <main class> --accesslog=<accessLog> --duration=<duration>\n" +
            "                    --startDate=<startDate> --threshold=<threshold>\n" +
            "      --accesslog=<accessLog>\n" +
            "         Log file location\n" +
            "      --duration=<duration>\n" +
            "         'hourly' or 'daily'\n" +
            "      --startDate=<startDate>\n" +
            "         Start date in format of 'yyyy-MM-dd.HH:mm:ss'\n" +
            "      --threshold=<threshold>\n" +
            "         Any integer value";

    private void assertThatUsagePrinted(OutputCapture outputCapture) {
        outputCapture.expect(containsString(USAGE_MESSAGE));
    }

    @Test
    void should_fail_with_empty_parameters(OutputCapture outputCapture) {
        Parser.main(new ParserArgsBuilder().build());

        assertThatUsagePrinted(outputCapture);
        outputCapture.expect(containsString("Missing required options [--accesslog=<accessLog>, --startDate=<startDate>, --duration=<duration>, --threshold=<threshold>]"));
    }

    @Test
    void should_fail_with_invalid_file(OutputCapture outputCapture) {
        Parser.main(ParserArgsFactory.argsWithInvalidFileName());
        outputCapture.expect(containsString("Validation exception: File not exists!"));
    }

    @Test
    void should_fail_with_invalid_access_log(OutputCapture outputCapture) {
        Parser.main(ParserArgsFactory.argsWithInvalidAccessLog());

        assertThatUsagePrinted(outputCapture);
        outputCapture.expect(containsString("Missing required option '--accesslog=<accessLog>'"));
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
}