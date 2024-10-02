package de.hsw.categoriesgame.gameapi.perf;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class RuntimeMeasurer {

    private long startTime;
    private long endTime;

    public RuntimeMeasurer() {}

    public synchronized RuntimeMeasurer start()
    {
        startTime = System.currentTimeMillis();
        return this;
    }

    public synchronized long stop()
    {
        endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public long getMillis()
    {
        return ((endTime != 0) ? endTime : System.currentTimeMillis()) - startTime;
    }
}