package com.ef;

import com.ef.cli.delegate.LogProcessorFacade;
import com.ef.module.ParserModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class of the parser cli.
 *
 * <pre>
 *     Usage: com.ef.Parser --accesslog=&lt;accessLog&gt; --duration=&lt;duration&gt;
 *                     --startDate=&lt;startDate&gt; --threshold=&lt;threshold&gt;
 *       --accesslog=&lt;accessLog&gt;
 *          Log file location
 *       --duration=&lt;duration&gt;
 *          'hourly' or 'daily'
 *       --startDate=&lt;startDate&gt;
 *          Start date in format of 'yyyy-MM-dd.HH:mm:ss'
 *       --threshold=&lt;threshold&gt;
 *          Any integer value
 * </pre>
 *
 * @author veysiertekin
 */
public class Parser {
    /**
     * Initializes log parsing operation.
     *
     * @param args log parsing options
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ParserModule());
        LogProcessorFacade logProcessorFacade = injector.getInstance(LogProcessorFacade.class);
        logProcessorFacade.process(args);
    }
}
