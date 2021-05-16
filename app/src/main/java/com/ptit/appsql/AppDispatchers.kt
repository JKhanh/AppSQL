package com.ptit.appsql

import kotlinx.coroutines.CoroutineDispatcher

class AppDispatchers(val main: CoroutineDispatcher, val io: CoroutineDispatcher, val default: CoroutineDispatcher)