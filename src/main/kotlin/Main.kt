fun main() {
    Game.play()
}

object Game {
    fun play() {
        val (width, height) = readMazeInput()
        val maze = Maze(width, height).also {
            it.createMaze()
        }
        val player = Player(1,1, maze)
        maze.run {
            while (!(completeMaze())) player.build()
            visualMap ()
        }
    }

    private fun readMazeInput(): Pair<Int, Int> {
        var input: String?
        var width: Int
        var height: Int

        while (true) {
            print("Enter width and height of the maze (separated by space): ")
            input = readLine()

            val parts = input?.split(" ")
            if (parts != null && parts.size == 2) {
                val isWidthValid = parts[0].toIntOrNull()
                val isHeightValid = parts[1].toIntOrNull()

                if (isWidthValid != null && isHeightValid != null) {
                    width = isWidthValid
                    height = isHeightValid
                    break
                }
            }
            println("Invalid input. Please enter two valid numbers separated by space.")
        }

        return Pair(width, height)
    }
}
