package com.example.tic_tac_toe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel(){
	 private var _board = MutableStateFlow(
		MutableList(9){""}
	)
	
	var isPlayerX = mutableStateOf(true)
	
	val gameBoard : StateFlow<List<String>> =_board
	
	var gameStatus = mutableStateOf("Vez do Jogador: X")
	var shouldReset = mutableStateOf(false)
	
	val winPatterns = listOf(
		listOf(0, 1, 2),
		listOf(3, 4, 5),
		listOf(6, 7, 8),
		listOf(0, 3, 6),
		listOf(1, 4, 7),
		listOf(2, 5, 8),
		listOf(0, 4, 8),
		listOf(2, 4, 6,)
	)
	
	fun setBoardValue(index:Int){
		shouldReset.value = true
		_board.update {
			list-> list.toMutableList().apply {
				if(this[index].isEmpty()){
					val value = if (isPlayerX.value){
						"X"
					}else{
						"O"
					}
					this[index] =  value
				}
			}
		}
		isPlayerX.value = !isPlayerX.value
		val player = if(isPlayerX.value) "X" else "O"
		gameStatus.value = "Vez do Jogador: $player"
		defineWinner()
	}
	
	fun defineWinner(){
		for( pattern in winPatterns){
			val a = gameBoard.value[pattern[0]]
			val b = gameBoard.value[pattern[1]]
			val c = gameBoard.value[pattern[2]]
			if(a.isNotEmpty() && a == b && b == c){
				gameStatus.value = "Jogador $a venceu"
				return
			}
		}
		if(!gameBoard.value.contains("")){
			gameStatus.value = "Empate"
			return
		}
	}
	
	fun resetGame(){
		gameStatus.value = "Vez do Jogador: X"
		shouldReset.value = false
		isPlayerX.value = true
		_board.value =MutableList(9) { "" }
	}
	
	fun showSideBorder(index:Int): Boolean{
		return !(index == 2 || index == 5 || index == 8)
	}
}