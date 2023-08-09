import kotlin.random.Random
import kotlin.random.nextInt
class Player (var px: Int, var py: Int, val maze: Maze) {
    private val way = Array(maze.height) { arrayOfNulls<Int>(maze.width) }
    private val path = "\u001B[31m * \u001B[0m"

    init {
        for (y in 0..<maze.height) {
            for (x in 0..<maze.width) {
                way[y][x] = 0
            }
        }
        way[py][px] = 1
    }

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
//    fun visualWay() {
//        for (y in way.indices) {
//            for (x in way[y].indices) print("${way[y][x]}")
//            println()
//        }
//    }
    fun drawRoute() {
        var x: Int = (maze.width-1)
        var y: Int = (maze.height-1)
        maze.grid[1][0] = path
        maze.grid[y][x+1] = path
        var k:Int = way[y][x]?:0
        maze.grid[y][x] = path
        while (k > 1) {
            if (y > 0 && way[y - 1][x] == k - 1) {
                y -= 1
                maze.grid[y][x] = path
                k -= 1
            } else if (x > 0 && way[y][x - 1] == k - 1) {
                x -= 1
                maze.grid[y][x] = path
                k -= 1
            } else if (y < way.size && way[y + 1][x] == k - 1) {
                y += 1
                maze.grid[y][x] = path
                k -= 1
            } else if (x < way[y].size && way[y][x + 1] == k - 1) {
                x += 1
                maze.grid[y][x] = path
                k -= 1
            }
        }
    }
    fun findRoute() {
        var k = 1
        while (way[maze.height-1][maze.width-1] == 0) {
            makeStep(k++)
        }
    }
    private fun makeStep(k: Int) {
        for (y in 0..<maze.height) {
            for (x in 0..<maze.width) {
                if (way[y][x] == k) {
                    if (y > 0 && way[y - 1][x] == 0 && maze.grid[y - 1][x] == maze.empty) {
                        way[y - 1][x] = k + 1
                    }
                    if (x > 0 && way[y][x - 1] == 0 && maze.grid[y][x - 1] == maze.empty) {
                        way[y][x - 1] = k + 1
                    }
                    if (y < way.size - 1 && way[y + 1][x] == 0 && maze.grid[y + 1][x] == maze.empty) {
                        way[y + 1][x] = k + 1
                    }
                    if (x < way[y].size - 1 && way[y][x + 1] == 0 && maze.grid[y][x + 1] == maze.empty) {
                        way[y][x + 1] = k + 1
                    }
                }
            }
        }
    }
}
data class Coordinate(val x: Int, val y: Int)