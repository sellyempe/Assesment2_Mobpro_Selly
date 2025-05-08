package com.selly0024.Assesment2.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FormBaru : Screen("form_baru")
    object FormUbah : Screen("form_ubah/{$KEY_WATER_ID}") {
        fun createRoute(id: Long) = "form_ubah/$id"
    }
}
