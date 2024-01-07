import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun App() {
    val navController by rememberNavController("AddNames")
    CustomNavHost(navController)
}

@Preview
@Composable
fun AddNames(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "INGRESEN LOS NOMBRES DE LOS JUGADORES", color = Color.Blue)
        Spacer(modifier = Modifier.padding(top = 20.dp))
        MaterialTheme {
            Button(onClick = {
                navController.navigate("Game")
            }) {
                Text("Cambiar")
            }
        }
    }
}

@Composable
fun Game(navController: NavController){

    MaterialTheme {
        Button(onClick = {
            navController.navigate("AddNames")
        }) {
            Text("Navegar")
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            App()
        }
    }
}

@Composable
fun CustomNavHost(
    navController: NavController
){
    NavHost(navController){
        composable("AddNames"){AddNames(navController)}
        composable("Game"){Game(navController)}
    }.build()
}