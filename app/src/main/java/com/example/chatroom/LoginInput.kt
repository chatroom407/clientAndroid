package com.example.chatroom

class LoginInput(
    val ipAddr: String,
    val port: Int,
    val login: String,
    val passwd: String
) {
    init {
        require(ipAddr.isNotEmpty()) { "IP address cannot be empty" }
        require(port in 1..65535) { "Port must be in range 1-65535" }
        require(login.isNotEmpty()) { "Login cannot be empty" }
        require(passwd.isNotEmpty()) { "Password cannot be empty" }
    }

    override fun toString(): String {
        return "LoginInput(ipAddr='$ipAddr', port=$port, login='$login')"
    }
}