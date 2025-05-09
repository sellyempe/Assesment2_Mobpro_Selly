const val KEY_WATER_ID = "waterId"

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FormBaru : Screen("formBaru")
    object FormUbah : Screen("formUbah/{$KEY_WATER_ID}") {
        fun createRoute(id: Long) = "formUbah/$id"
    }
    object TrashScreen : Screen("recycleBin")
}
