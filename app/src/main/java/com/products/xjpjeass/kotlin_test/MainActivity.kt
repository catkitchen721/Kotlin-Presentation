package com.products.xjpjeass.kotlin_test

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.products.xjpjeass.kotlin_test.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var isWin = false
    var isCPUmode = true
    var winner = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bldr = AlertDialog.Builder(this@MainActivity)
        bldr.setTitle("Game Start")
        bldr.setMessage("1p or 2p?")
        bldr.setPositiveButton("1p") {
            dialog, which ->
            isCPUmode = true
        }
        bldr.setNegativeButton("2p"){
            dialog,which ->
            isCPUmode = false
        }

        val dilg: AlertDialog = bldr.create()
        dilg.setCancelable(false)
        dilg.show()
    }

    fun Button.init() {
        this.text = ""
        this.setBackgroundColor(Color.parseColor("#ffffff"))
        this.isEnabled = true
    }

    fun buClick(view: View){
        val buSelected = view as Button
        var cellID = 0
        when(buSelected.id){
            R.id.bu1 -> cellID = 1
            R.id.bu2 -> cellID = 2
            R.id.bu3 -> cellID = 3
            R.id.bu4 -> cellID = 4
            R.id.bu5 -> cellID = 5
            R.id.bu6 -> cellID = 6
            R.id.bu7 -> cellID = 7
            R.id.bu8 -> cellID = 8
            R.id.bu9 -> cellID = 9

        }
        playGame(cellID,buSelected)
        if(isWin){
            //Thread.sleep(1000)
            val bldr = AlertDialog.Builder(this@MainActivity)
            bldr.setTitle("Congratulations! ${if(winner == 1) "Player 1" else if(winner == 2) "Player 2" else "NoBody"} is win!")
            bldr.setMessage("Replay?")
            bldr.setPositiveButton("Sure") {
                dialog, which ->
                refresh()
            }
            val dilg: AlertDialog = bldr.create()
            dilg.setCancelable(false)
            dilg.show()
            //refresh()


            isWin = false
        }
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    var activePlayer = 1

    private fun playGame(cellID: Int, buSelected: Button) {
        if(activePlayer==1){
            buSelected.text = "X"
            buSelected.setBackgroundColor(Color.parseColor("#009193"))
            player1.add(cellID)
            activePlayer = 2
            if(isCPUmode) AutoPlay()
        }else{
            buSelected.text = "o"
            buSelected.setBackgroundColor(Color.parseColor("#FF9300"))
            player2.add(cellID)
            activePlayer = 1
        }
        buSelected.isEnabled = false;
        checkWinner()
    }

    private fun refresh() {
        val bldr12 = AlertDialog.Builder(this@MainActivity)
        bldr12.setTitle("Game Start")
        bldr12.setMessage("1p or 2p?")
        bldr12.setPositiveButton("1p") {
            dialog, which ->
            isCPUmode = true
        }
        bldr12.setNegativeButton("2p"){
            dialog,which ->
            isCPUmode = false
        }
        val dilg12: AlertDialog = bldr12.create()
        dilg12.setCancelable(false)
        dilg12.show()

        activePlayer = 1
        player1.clear()
        player2.clear()
        bu1.init()
        bu2.init()
        bu3.init()
        bu4.init()
        bu5.init()
        bu6.init()
        bu7.init()
        bu8.init()
        bu9.init()
    }

    private fun checkWinner() {
        winner = -1

        if(player1.size + player2.size == 9){
            winner = 3
        }

        if(player1.contains(1) && player1.contains(2) && player1.contains(3)){
            winner=1
        }
        if(player2.contains(1) && player2.contains(2) && player2.contains(3)){
            winner=2
        }


        // row 2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6)){
            winner=1
        }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6)){
            winner=2
        }




        // row 3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9)){
            winner=1
        }
        if(player2.contains(7) && player2.contains(8) && player2.contains(9)){
            winner=2
        }



        // col 1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7)){
            winner=1
        }
        if(player2.contains(1) && player2.contains(4) && player2.contains(7)){
            winner=2
        }



        // col 2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8)){
            winner=1
        }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8)){
            winner=2
        }


        // col 3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9)){
            winner=1
        }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9)){
            winner=2
        }

        //diagonal
        if(player1.contains(1) && player1.contains(5) && player1.contains(9)){
            winner=1
        }
        if(player2.contains(1) && player2.contains(5) && player2.contains(9)){
            winner=2
        }

        if(player1.contains(3) && player1.contains(5) && player1.contains(7)){
            winner=1
        }
        if(player2.contains(3) && player2.contains(5) && player2.contains(7)){
            winner=2
        }

        if( winner != -1){
            if(winner == 3)
            {
                Toast.makeText(this," Game ended in a tie", Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(this,"${if (winner==1) "Player1" else "Player2" } is winner!", Toast.LENGTH_LONG).show()
            }

            isWin = true
        }

    }

    private fun AutoPlay() {
        val emptyCells=ArrayList<Int>()
        for (cellID in 1..9){
            if(!(player1.contains(cellID) || player2.contains(cellID))){
                emptyCells.add(cellID)
            }
        }

        val r = Random()
        if( emptyCells.size != 0 ) {
            val randIndex = r.nextInt(emptyCells.size - 0) + 0
            val cellID = emptyCells[randIndex]

            var buSelected: Button
            when (cellID) {
                1 -> buSelected = bu1
                2 -> buSelected = bu2
                3 -> buSelected = bu3
                4 -> buSelected = bu4
                5 -> buSelected = bu5
                6 -> buSelected = bu6
                7 -> buSelected = bu7
                8 -> buSelected = bu8
                9 -> buSelected = bu9
                else -> buSelected = bu1

            }
            playGame(cellID, buSelected)
        }
        /*else{
            Toast.makeText(this," Game ended in a tie", Toast.LENGTH_LONG).show()
            refresh()
        }*/
    }
}
