package be.julien.squarehole

import com.badlogic.gdx.backends.lwjgl._

object Main extends App {
    val cfg = new LwjglApplicationConfiguration
    cfg.title = "SquareHole"
    cfg.height = 800
    cfg.width = 1280
    cfg.forceExit = false
    new LwjglApplication(new Squarehole, cfg)
}
