import kotlin.random.Random
import kotlin.random.nextInt

class Player (x: Int, y: Int, maze: Maze) {
    var px = x
    var py = y
    val maze = maze

    private enum class Direction(private val directionCoordinate: Coordinate) {
        North(Coordinate(0, -2)),
        East(Coordinate(2, 0)),
        South(Coordinate(0, 2)),
        West(Coordinate(-2, 0));
        fun getCoordinate() = directionCoordinate
    }

    fun build() {
        val dir = mutableListOf<Direction>().apply {
            if (px > 1) add(Direction.West)
            if (px < maze.width - 1) add(Direction.East)
            if (py > 1) add(Direction.North)
            if (py < maze.height - 1) add(Direction.South)
        }
        val index = Random.nextInt(0 until dir.size)
        val (dx, dy) = dir[index].getCoordinate()
        px += dx
        py += dy
        if (maze.grid[py][px] === maze.wall) {
            maze.grid[py][px] = maze.empty
            maze.grid[py - dy / 2][px - dx / 2] = maze.empty
        }
    }
}

data class Coordinate(val x: Int, val y: Int)