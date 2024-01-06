import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
fun App() {

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
    var test by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf("") }

    if (test) {
        text = "Hello World!"
    } else {
        text = "Hello Desktop!"
    }

    MaterialTheme {
        Button(onClick = {
            navController.navigate("AddNames")
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    val navController by rememberNavController("AddNames")
    Window(onCloseRequest = ::exitApplication) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddNames(navController)
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
    }
}