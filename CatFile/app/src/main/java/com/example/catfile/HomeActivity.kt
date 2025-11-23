package com.example.catfile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catfile.ui.theme.CatFileTheme
import com.example.database.AppDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeActivity(database: AppDatabase? = null, navController: NavHostController, nombreUsuario: String) {

    var expanded1 by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row {
                TopAppBar(
                    title = { Text("¡Hola $nombreUsuario!") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.hsv(220f, 0.45f, 0.14f),
                        titleContentColor = Color.White
                    ),
                    actions = {
                        Box(modifier = Modifier) {

                            Button(onClick = { expanded1 = !expanded1 },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.hsv(220f,0.45f,0.14f))
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.menupuntos),
                                    contentDescription = "Menu",
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                            // MENÚ DESPLEGABLE

                            DropdownMenu(expanded = expanded1,
                                onDismissRequest = { expanded1 = false }) {

                                // Opción para subir archivo
                                DropdownMenuItem(onClick = {
                                    expanded1 = false
                                    coroutineScope.launch {

                                    }
                                },
                                    text = { Text("Subir archivo") })

                                // Opción para entrar a ajustes
                                DropdownMenuItem(onClick = {
                                    expanded1 = false
                                    coroutineScope.launch {
                                        navController.navigate("ajustes_catfile")
                                    }
                                },
                                    text = { Text("Ajustes") })

                                // Opción para cerrar sesión
                                DropdownMenuItem(onClick = {
                                    expanded1 = false
                                    coroutineScope.launch {
                                        navController.popBackStack()
                                    }
                                },
                                    text = { Text("Cerrar sesión") })
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Archivos")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    CatFileTheme {
        val navController = rememberNavController()
        HomeActivity(null, navController, "Usuario")
    }
}