package silveira.felipe.workUnit;

import org.springframework.http.ResponseEntity;

/**
 * This interface provides the methods for WorkerManager implementation.
 */
public interface WorkerManager {

    /**
     * This method creates a work request for a specific number of workers.
     *
     * @param workers the numbers of workers requested.
     * @return true if the work request was acknowledged or false if it was rejected.
     */
    ResponseEntity<Boolean> requestWorkers(final int workers);

}
