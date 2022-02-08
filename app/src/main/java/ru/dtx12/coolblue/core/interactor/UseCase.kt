package ru.dtx12.coolblue.core.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<out Type, in Params> {

    protected abstract suspend fun run(params: Params): Type

    suspend fun execute(params: Params): Type {
        return withContext(Dispatchers.Main.immediate) {
            withContext(Dispatchers.IO) {
                run(params)
            }
        }
    }

    class None
}