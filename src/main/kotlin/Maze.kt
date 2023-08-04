class Maze (val width: Int, val height: Int) {
    val wall = "\u2593" + "\u2593" + "\u2593"
    val empty = " * "
    val grid = Array(height + 2) { arrayOfNulls<String>(width + 2) }
    fun createMaze(){
        for (y in 0..<height + 1) {
            for (x in 0..<width + 1) {
                grid[y][x] = wall
            }
        }
        grid[1][0] = empty
        grid[height - 1][width] = empty
    }
    fun visualMap() {
        for (row in grid) println(row.joinToString("") { it ?: "" })
    }
    fun completeMaze(): Boolean {
        return grid.indices.step(2).all { y ->
            grid[y].indices.step(2).all { x ->
                grid[y + 1][x + 1] != wall
            }
        }
    }
}