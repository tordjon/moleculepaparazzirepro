package dev.paparazzi.repro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

class Paparazzimoleculereprotest {

    @Composable
    fun Counter(): Int {
        var count by remember { mutableStateOf(1) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(1_000)
                count++
            }
        }

        return count
    }
    @Test
    fun repro() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            Counter()
        }.test {
            assertEquals(awaitItem(), 1)
            assertEquals(awaitItem(), 2)
            assertEquals(awaitItem(), 3)
        }
    }
}