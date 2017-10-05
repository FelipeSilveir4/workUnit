package silveira.felipe.workunit;

/**
 * This interface provides the methods for WorkerManager implementation.
 */
public interface WorkerManager {

    /**
     * This method creates a work request for a specific number of workers.
     *
     * @return true if the work request was acknowledged or false if it was rejected.
     */
    Boolean requestWorkers(int workersNumber);

}
