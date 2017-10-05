package silveira.felipe.workunit.work.union;

import javax.annotation.Nonnull;

/**
 * Union Interface.
 */
public interface Union {

    /**
     * Request a number of workers.
     *
     * @param workersNumber the number of workers requested.
     */
    Boolean requestWorkers(@Nonnull int workersNumber);

}
