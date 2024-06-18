package com.example.rpsgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpsgame.ui.theme.RPSGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RPSGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RockPaperScissorsGame()
                }
            }
        }
    }
}

@Composable
fun RockPaperScissorsGame() {
    var playerChoice by remember { mutableStateOf<Choice?>(null) }
    var appChoice by remember { mutableStateOf<Choice?>(null) }
    var result by remember { mutableStateOf("") }

    fun play(choice: Choice) {
        playerChoice = choice
        appChoice = Choice.entries.random()
        result = getResult(playerChoice!!, appChoice!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Choice.entries.forEach { choice ->
                Image(
                    painter = painterResource(id = choice.drawable),
                    contentDescription = choice.name,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { play(choice) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (playerChoice != null && appChoice != null) {
            Text(text = "You chose: ${playerChoice!!.name}", fontSize = 24.sp)
            Text(text = "App chose: ${appChoice!!.name}", fontSize = 24.sp)
            Text(text = result, fontSize = 28.sp, color = Color.Red, modifier = Modifier.padding(top = 16.dp))

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Play Again",
                fontSize = 20.sp,
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(16.dp)
                    .clickable {
                        playerChoice = null
                        appChoice = null
                        result = ""
                    }
            )
        }
    }
}

enum class Choice(val drawable: Int) {
    ROCK(R.drawable.rock),
    PAPER(R.drawable.paper),
    SCISSORS(R.drawable.scissors)
}

fun getResult(player: Choice, app: Choice): String {
    return if (player == app) {
        "It's a draw!"
    } else if (
        (player == Choice.ROCK && app == Choice.SCISSORS) ||
        (player == Choice.PAPER && app == Choice.ROCK) ||
        (player == Choice.SCISSORS && app == Choice.PAPER)
    ) {
        "You win!"
    } else {
        "You lose!"
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RPSGameTheme {
        RockPaperScissorsGame()
    }
}
