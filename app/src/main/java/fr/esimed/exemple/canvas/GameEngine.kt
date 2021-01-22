package fr.esimed.exemple.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.concurrent.ThreadLocalRandom

class GameEngine(ctx: Context) {
    private val paintBg: Paint = Paint()
    private val paintflocon: Paint = Paint()
    private val listSnowflake = mutableListOf<Snowflake>()


    private val background = Paint()
    private val colorSnowflake = Paint()
    init {
        paintBg.color=Color.BLACK
        paintflocon.color=Color.WHITE
    }

    fun addSnowflake(width: Int, height: Int)
    {
        val g : Int = listSnowflake.size+1


        var i: Int =listSnowflake.size
        if (i <=1000){
            while(i <g)
            {val random = ThreadLocalRandom.current()
                val randomX = random.nextDouble(0.0, 1.0)
                val randomVitesseChute = random.nextDouble(1.0, 4.0)
                val coordonneesX = width * randomX

                val snowflake = Snowflake(X= coordonneesX.toFloat(), Y = 0f,Vitesse = randomVitesseChute.toInt())
                listSnowflake.add(snowflake)
                i=listSnowflake.size


            }

        } else {}

    }

    init
    {
        background.color = Color.BLACK
        colorSnowflake.color = Color.WHITE
    }



    @Synchronized
    fun draw(canvas: Canvas)
    {
        var i: Int = 0
        val number = listSnowflake.size

        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), background)
        while (i < number)
        {
            val list = listSnowflake.get(i)


            canvas.drawCircle(list.X.toFloat(), list.Y.toFloat(), 4f, colorSnowflake)
            i++
        }
    }

    @Synchronized
    fun update(canvas: Canvas, width: Int, height: Int)
    {
        var i: Int = 0

        addSnowflake(canvas.width, 0)
        val number = listSnowflake.size
        while (i < number)
        {
            val list = listSnowflake.get(i)
            var y = list.Y.toFloat()

            if (y <= height)
            {
                val vitesse = 1*list.Vitesse.toFloat()
                y = vitesse + y
                list.Y = y
            }
            else
            {

                listSnowflake.removeAt(i)
                addSnowflake(canvas.width, 0)


            }

            i++

        }
    }
}