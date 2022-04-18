package com.fxingsign.profitrackr.util

import kotlinx.coroutines.flow.Flow

fun <ResultType, RequestType> networkBoundResource(
    query: () -> Flow<ResultType>,
    fetch: suspend () -> RequestType
) {
}