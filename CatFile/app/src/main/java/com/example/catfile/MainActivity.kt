package com.example.catfile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catfile.ui.theme.CatFileTheme
import com.example.database.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, this, "app.db")
        database = AppDatabase(driver)

        enableEdgeToEdge()

        database.transaction {

            val bdQueries = database.bdQueries

        }

        setContent {
            CatFileTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main_catfile"
                ) {
                    composable("main_catfile") { CatFile(database = database, navController) }
                    composable("home_catfile/{nombreUsuario}") {
                        backStackEntry -> val nombreUsuario = backStackEntry.arguments?.getString("nombreUsuario") ?: ""
                        HomeActivity(database = database, navController, nombreUsuario)
                    }
                    composable("registro_catfile") { RegistroActivity(database = database, navController) }
                }
            }
        }
    }
}

@Composable
fun CatFile(database: AppDatabase? = null, navController : NavController) {

    var errorNombre by remember { mutableStateOf(false) }
    var errorPasswd by remember { mutableStateOf(false) }
    var nombreUsuario by remember{mutableStateOf("")}
    var passwd by remember{mutableStateOf("")}

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(Modifier.padding(20.dp)) {
            Text(
                "CatFile", textAlign = TextAlign.Center,
                color = Color.hsv(220f, 0.45f, 0.14f),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                lineHeight = 50.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.gatoprincipal),
            contentDescription = "Gato",
            modifier = Modifier.size(150.dp)
        )

        OutlinedTextField(
            value = nombreUsuario,
            onValueChange = {
                nombreUsuario = it
                errorNombre = nombreUsuario.isEmpty()
            },
            label = {Text("Nombre de usuario")},
            isError = errorNombre,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = passwd,
            onValueChange = {
                passwd = it
                errorPasswd = passwd.isEmpty()
            },
            label = {Text("Contraseña")},
            isError = errorPasswd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        if(errorNombre) Text(text = "El nombre no puede estar vacío", color = MaterialTheme.colorScheme.error)
        if(errorPasswd) Text(text = "La contraseña no puede estar vacía", color = MaterialTheme.colorScheme.error)

        Button(onClick = { navController.navigate("home_catfile") }) {
            Text("Iniciar Sesión")
        }
        Button(onClick = { navController.navigate("registro_catfile") }) {
            Text("Ir a Registro")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatFilePreview() {
    CatFileTheme {
        val navController = rememberNavController()
        CatFile(null, navController)
    }
}