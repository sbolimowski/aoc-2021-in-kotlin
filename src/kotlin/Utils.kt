import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInputToLines(name: String) = File("src/inputs", "$name.txt").readLines()
fun readInputToLine(name: String) = File("src/inputs", "$name.txt").readLines().first()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
