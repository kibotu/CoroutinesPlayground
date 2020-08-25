package net.kibotu.coroutines.internal

import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextDouble

/**
 * Created by [Jan Rabe](https://http://kibotu.net).
 */

fun createRandomImageUrl(): String {

    val landscape = nextBoolean()

    val width = random(300, 400)
    val height = random(200, 300)

    return (if (nextBoolean())
        "https://lorempixel.com/%d/%d/"
    else
        "https://picsum.photos/%d/%d/")
        .format(
            if (landscape) width else height, if (landscape) height else width
        )
}

/**
 * Returns a random number between start (inclusive) and end (inclusive).
 */
fun random(start: Int, end: Int): Int = (start + (nextDouble() * (end - start))).toInt()