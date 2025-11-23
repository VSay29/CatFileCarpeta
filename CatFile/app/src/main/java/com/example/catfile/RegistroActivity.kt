package com.example.catfile

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.catfile.ui.theme.CatFileTheme
import com.example.database.AppDatabase

@Composable
fun RegistroActivity(database: AppDatabase? = null, navController : NavHostController) {

    var errorNombre by remember { mutableStateOf(false) }
    var errorPasswd by remember { mutableStateOf(false) }
    var errorCorreo by remember { mutableStateOf(false) }
    var nombreUsuario by remember{mutableStateOf("")}
    var passwd by remember{mutableStateOf("")}
    var correoElectronico by remember{mutableStateOf("")}


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
            value = correoElectronico,
            onValueChange = {
                correoElectronico = it
                errorCorreo = correoElectronico.isEmpty()
            },
            label = {Text("Correo Electrónico")},
            isError = errorCorreo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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

        if(errorCorreo) Text(text = "El correo electrónico no puede estar vacío", color = MaterialTheme.colorScheme.error)
        if(errorNombre) Text(text = "El nombre no puede estar vacío", color = MaterialTheme.colorScheme.error)
        if(errorPasswd) Text(text = "La contraseña no puede estar vacía", color = MaterialTheme.colorScheme.error)

        Button(onClick = {
            if(!errorCorreo && !errorNombre && !errorPasswd) {
                database?.bdQueries?.registrarUsuario(correoElectronico, nombreUsuario, passwd)
                navController.navigate("home_catfile/$nombreUsuario")
            }
        }) {
            Text("Registrarse")
        }
        Button(onClick = { navController.navigate("main_catfile") }) {
            Text("Ir a Iniciar Sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    CatFileTheme {
        val navController = rememberNavController()
        RegistroActivity(null, navController)
    }
}