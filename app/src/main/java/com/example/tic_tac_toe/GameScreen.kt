package com.example.tic_tac_toe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(modifier: Modifier, viewModel: GameViewModel = viewModel ()){
	val gameBoard by viewModel.gameBoard.collectAsState()
	
	
	Column (modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
		Text(viewModel.gameStatus.value,
			modifier = Modifier.padding(8.dp))
		Box (modifier = Modifier.size(width = 180.dp, 180.dp)){
			LazyVerticalGrid(
				columns = GridCells.Fixed(3), verticalArrangement = Arrangement.Center,
				horizontalArrangement = Arrangement.Center,
			) {
				items(count = gameBoard.size) {
					Box(
						modifier = Modifier
							.size(width = 60.dp, height = 60.dp)
							.clickable(
								enabled = gameBoard[it].isEmpty() && viewModel.gameStatus.value.contains("turn"),
								onClick = {
									viewModel.setBoardValue(it)
								})
							.border(border = BorderStroke(width = 0.4.dp, color = Color.Gray)),
						contentAlignment = Alignment.Center,
					) {
						Text(gameBoard[it])
					}
				}
			}
		}
		Box(modifier= Modifier.size(12.dp))
		if(viewModel.shouldReset.value)
		ResetButton (reset = {viewModel.resetGame()})
	}
}

@Composable
fun ResetButton(reset : () -> Unit){
	Button(onClick = {
		reset()
	}) {
		Text("Reset")
	}
}