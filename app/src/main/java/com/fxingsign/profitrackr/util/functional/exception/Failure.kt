package com.fxingsign.profitrackr.util.functional.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure : Throwable() {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object ValidationError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
