package com.example.chatroom

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.ui.theme.ChatRoomTheme
import java.util.Base64

object GlobalLoginInput {
    var loginInput: LoginInput? = null
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatRoomTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "input_form") {
        composable("input_form") {
            InputForm(navController)
        }
        composable("chat_screen") {
            ChatScreen()
        }
    }
}
@Composable
fun InputForm(navController: NavHostController) {
    val (ip, setIp) = remember { mutableStateOf("") }
    val (port, setPort) = remember { mutableStateOf("") }
    val (login, setLogin) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (errorMessage, setErrorMessage) = remember { mutableStateOf<String?>(null) } // Dodajemy obsługę błędów

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = ip,
            onValueChange = { setIp(it) },
            label = { Text("IP Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = port,
            onValueChange = { setPort(it) },
            label = { Text("Port") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = login,
            onValueChange = { setLogin(it) },
            label = { Text("Login") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { setPassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                try {
                    val portInt = port.toIntOrNull() ?: throw IllegalArgumentException("Invalid port")

                    val loginInput = LoginInput(ip, portInt, login, password)

                    GlobalLoginInput.loginInput = loginInput

                    navController.navigate("chat_screen")
                } catch (e: Exception) {
                    setErrorMessage(e.message)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = androidx.compose.ui.graphics.Color.Red)
        }
    }
}

@Composable
fun ChatScreen() {
    val loginInput = GlobalLoginInput.loginInput

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (loginInput != null) {
            Text("IP Address: ${loginInput.ipAddr}")
            Text("Port: ${loginInput.port}")
            Text("Login: ${loginInput.login}")
        } else {
            Text("No login data available!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputFormPreview() {
    ChatRoomTheme {
        val navController = rememberNavController()
        InputForm(navController)
    }
}
