/**
* Author: przewnic
*/
package tictactoe

fun main() {
    // Init empty game
    val gameTable = "_".repeat(9).toCharArray()
    displayGameState(gameTable)
    playGame(gameTable)
}
/**
 * Updates gameTable and checks for end of the game
 */
fun playGame(gameTable: CharArray) {
    var lastMove = 'O'
    while (true) {
        val coordinates = getMove(gameTable)
        lastMove = if (lastMove == 'O') 'X' else 'O'
        gameTable[3*(coordinates[0]-1) + (coordinates[1]-1)] = lastMove
        displayGameState(gameTable)
        if (
                checkStatus(gameTable) in
                    mutableListOf("X wins", "O wins", "Draw", "Impossible")
        ) break
    }
    print(checkStatus(gameTable))

}

/**
 * Gets coordinates from the user of the next move
 * Displays error messages if necessary
 */
fun getMove(gameTable: CharArray): MutableList<Int> {
    val coordinates = MutableList(2){ 0 }
    while (true){
        print("Enter coordinates: ")
        val move = readLine()!!
        val read = move.split(" ").toMutableList()
        try {
            coordinates[0] = read[0].toInt()
            coordinates[1] = read[1].toInt()
        } catch (e: NumberFormatException) {
            print("You should enter numbers!")
            continue
        }
        if (coordinates[0] < 1 || coordinates[0] > 3) continue
        if (coordinates[1] < 1 || coordinates[1] > 3) continue

        if (gameTable[3*(coordinates[0]-1) + (coordinates[1]-1)] != '_') continue
        break
    }
    return coordinates
}

/**
 * Display current board
 */
fun displayGameState(gameTable: CharArray){
    val sep = "---------"
    println(sep)
    for (i in 0..2) {
        println("| ${gameTable[3*i + 0]} ${gameTable[3*i + 1]} ${gameTable[3*i + 2]} |")
    }
    println(sep)
}

/**
 * Check status of the game
 */
fun checkStatus(gameTable: CharArray): String {
    // check not finished
    val results = mutableListOf<Char>()
    // Check horizontal
    for (i in 0..2) {
        if (gameTable[3 * i] == gameTable[3 * i + 1] && gameTable[3 * i] == gameTable[3 * i + 2] && gameTable[3 * i] != '_') {
            results.add(gameTable[3 * i])
        }
    }
    // Check vertical
    for (i in 0..2) {
        if (gameTable[i] == gameTable[3 + i] && gameTable[i] == gameTable[6 + i] && gameTable[i] != '_') {
            results.add(gameTable[i])
        }
    }
    // Check diagonal
    if (gameTable[0] == gameTable[4] && gameTable[0] == gameTable[8] && gameTable[0] != '_') {
        results.add(gameTable[0])
    }
    if (gameTable[2] == gameTable[4] && gameTable[2] == gameTable[6] && gameTable[2] != '_') {
        results.add(gameTable[2])
    }

    // Check for impossible scenarios
    if (results.size > 1) return "Impossible"
    if (gameTable.count { it == 'X' } > gameTable.count {it == 'O'} + 1) return "Impossible"
    if (gameTable.count { it == 'O' } > gameTable.count {it == 'X'} + 1) return "Impossible"

    if ( '_' in gameTable) {
        // Check if game not finished
        if (results.isEmpty()) return "Game not finished"
    }
    else {
        // Check if ended is Draw
        if (results.isEmpty()) return "Draw"
    }
    // In other case return winner
    return results.first().toString() + " wins"
}


