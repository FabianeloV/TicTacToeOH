import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jdk.jfr.Enabled

@Composable
fun app() {
    val navController by rememberNavController("AddNames")
    customNavHost(navController)
}

@Composable
fun addNames(navController: NavController, nameOne: MutableState<String>, nameTwo: MutableState<String>) {
    val empty = remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "INGRESE LOS NOMBRES DE LOS JUGADORES",
            color = Color.Blue,
            fontFamily = FontFamily.Monospace,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))

        Column(modifier = Modifier.border(width = 2.dp, color = Color.Cyan, shape = CircleShape)) {
            OutlinedTextField(
                modifier = Modifier.padding(start = 100.dp, end = 100.dp, top = 30.dp),
                value = nameOne.value,
                onValueChange = { nameOne.value = it },
                shape = CircleShape,
                label = { Text("Nombre 1") },
                leadingIcon = { Icon(Icons.Outlined.Face, null) })

            Spacer(modifier = Modifier.padding(top = 20.dp))

            OutlinedTextField(
                modifier = Modifier.padding(start = 100.dp, end = 100.dp, bottom = 30.dp),
                value = nameTwo.value,
                onValueChange = { nameTwo.value = it },
                shape = CircleShape,
                label = { Text("Nombre 2") },
                leadingIcon = { Icon(Icons.Outlined.Face, null) }
            )
        }

        empty.value = nameOne.value.isNotEmpty() && nameTwo.value.isNotEmpty()

        MaterialTheme {
            Button(onClick = {
                navController.navigate("Game")
            }, enabled = empty.value, modifier = Modifier.padding(15.dp)) {
                Text("JUGAR")
            }
        }
    }
}

@Composable
fun game(navController: NavController, matrix: Array<Array<MutableState<String>>>, nameOne: String, nameTwo: String) {
    val turns = remember { mutableStateOf(0) }
    val player = remember { mutableStateOf(true) }
    val name = remember { mutableStateOf("") }
    val list = remember {
        listOf(
            mutableStateOf(true),
            mutableStateOf(true),
            mutableStateOf(true),
            mutableStateOf(true),
            mutableStateOf(true),
            mutableStateOf(true),
            mutableStateOf(true),
            mutableStateOf(true),
            mutableStateOf(true)
        )
    }

    if (player.value) {
        name.value = nameOne
    } else {
        name.value = nameTwo
    }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Turno de ${name.value}", fontSize = 24.sp, modifier = Modifier.padding(20.dp))

        Row() {
            Text(matrix[0][0].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[0][0], turns, list[0])
            })

            Text(" | ", fontSize = 38.sp)

            Text(matrix[0][1].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[0][1], turns, list[1])
            })

            Text(" | ", fontSize = 38.sp)

            Text(matrix[0][2].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[0][2], turns, list[2])
            })

        }
        Text("---------------", fontSize = 38.sp)
        Row() {
            Text(matrix[1][0].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[1][0], turns, list[3])
            })

            Text(" | ", fontSize = 38.sp)

            Text(matrix[1][1].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[1][1], turns, list[4])
            })

            Text(" | ", fontSize = 38.sp)

            Text(matrix[1][2].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[1][2], turns, list[5])
            })
        }
        Text("---------------", fontSize = 38.sp)
        Row() {
            Text(matrix[2][0].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[2][0], turns, list[6])
            })

            Text(" | ", fontSize = 38.sp)

            Text(matrix[2][1].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[2][1], turns, list[7])
            })

            Text(" | ", fontSize = 38.sp)

            Text(matrix[2][2].value, fontSize = 38.sp, modifier = Modifier.clickable {
                changeValue(player, matrix[2][2], turns, list[8])
            })
        }
    }
}

fun changeValue(
    player: MutableState<Boolean>,
    casilla: MutableState<String>,
    turns: MutableState<Int>,
    enabled: MutableState<Boolean>
) {

    if (enabled.value) {
        if (player.value) {
            casilla.value = " X "
        } else {
            casilla.value = " O "
        }
        player.value = !player.value
        turns.value++
        enabled.value = false
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            app()
        }
    }
}

@Composable
fun customNavHost(
    navController: NavController
) {
    val nameOne = remember { mutableStateOf("") }
    val nameTwo = remember { mutableStateOf("") }
    val matrix = remember {
        arrayOf(
            arrayOf(mutableStateOf("   "), mutableStateOf("   "), mutableStateOf("   ")),
            arrayOf(mutableStateOf("   "), mutableStateOf("   "), mutableStateOf("   ")),
            arrayOf(mutableStateOf("   "), mutableStateOf("   "), mutableStateOf("   "))
        )
    }

    NavHost(navController) {
        composable("AddNames") { addNames(navController, nameOne, nameTwo) }
        composable("Game") { game(navController, matrix, nameOne.value, nameTwo.value) }
    }.build()
}

