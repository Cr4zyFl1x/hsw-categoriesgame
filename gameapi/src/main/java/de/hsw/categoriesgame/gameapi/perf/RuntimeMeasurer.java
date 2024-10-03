package de.hsw.categoriesgame.gameapi.perf;

/**
 * Measurer that can be used to get runtimes between method calls
 *
 * @author Florian J. Kleine-Vorholt
 */
public final class RuntimeMeasurer {

    /**
     * Start system time in milliseconds from UTC.
     * Is {@code null} if not started.
     */
    private Long startTime;

    /**
     * The system time when {@link RuntimeMeasurer#stop()} was called.
     * Is {@code null} if not stopped.
     */
    private Long endTime;


    /**
     * Creates a new instance of the {@code RuntimeMeasurer}
     */
    public RuntimeMeasurer() {}


    /**
     * Starts the measurer
     *
     * @throws IllegalStateException If its already running or has been run and was not resetted.
     */
    public RuntimeMeasurer start() throws IllegalStateException
    {
        if (isRunning()) {
            throw new IllegalStateException("Measurer is already running!");
        }
        if (hasRun()) {
            throw new IllegalStateException("Measurer has already run! Reset it before starting it again.");
        }

        startTime = System.currentTimeMillis();
        return this;
    }


    /**
     * Stops the measurer and returns the running time.
     *
     * @return the time the measurer has run
     *
     * @throws IllegalStateException if measurer is not running or has been run and is not resetted.
     */
    public long stop() throws IllegalStateException
    {
        if (!isRunning()) {
            throw new IllegalStateException("Measurer is not running!");
        }
        endTime = System.currentTimeMillis();
        return endTime - startTime;
    }


    /**
     * Gets the milliseconds the measurer is running until moment of call or the time it has been run in total if stopped.
     *
     * @return  actual running time (until call or until stopped)
     *
     * @throws IllegalStateException if was not started or is not running
     */
    public long getMillis() throws IllegalStateException
    {
        if (!isRunning() && !hasRun()) {
            throw new IllegalStateException("Measurer is not running or was not started before.");
        }
        return ((endTime != null) ? endTime : System.currentTimeMillis()) - startTime;
    }


    /**
     * Resets the measurer state
     */
    public void reset()
    {
        startTime = null;
        endTime = null;
    }


    /**
     * Returns true if the measurer is running#
     *
     * @return  true if running
     */
    public boolean isRunning()
    {
        return startTime != null && endTime == null;
    }


    /**
     * Returns true if the measurer has run and has finished
     *
     * @return true if the measurer has run and has finished
     */
    public boolean hasRun()
    {
        return startTime != null && endTime != null;
    }
}