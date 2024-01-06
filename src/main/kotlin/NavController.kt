import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

class NavController(
    private val start: String,
    private var backStack: MutableSet<String> = mutableSetOf()
) {
    var currentState: MutableState<String> = mutableStateOf(start)

    fun navigate(route: String) {
        if (backStack.contains(currentState.component1()) && currentState.value != start) {
            backStack.remove(currentState.value)
        }
        if (route == start) {
            backStack = mutableSetOf()
        } else {
            backStack.add(currentState.value)
        }

        currentState.value = route
    }

    fun navigateBack() {
        if (backStack.isNotEmpty()) {
            currentState.value = backStack.last()
            backStack.remove(currentState.value)
        }
    }
}

@Composable
fun rememberNavController(
    start: String,
    backStack: MutableSet<String> = mutableSetOf()
): MutableState<NavController> = rememberSaveable {
    mutableStateOf(NavController(start, backStack))
}

class NavHost(
    val navController: NavController,
    val contents: @Composable NavigationGraphBuilder.() -> Unit
) {
    @Composable
    fun build(){
        NavigationGraphBuilder().renderContent()
    }

    inner class NavigationGraphBuilder(
        val navController: NavController = this@NavHost.navController
    ) {
        @Composable
        fun renderContent(){
            this@NavHost.contents(this)
        }
    }
}

@Composable
fun NavHost.NavigationGraphBuilder.composable(
    route: String,
    content: @Composable () -> Unit
) {
    if (navController.currentState.value == route){
        content()
    }
}