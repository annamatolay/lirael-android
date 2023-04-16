package dev.anmatolay.lirael.core.authentication

sealed class UserProvider {
    data class FirebaseUser(val user: com.google.firebase.auth.FirebaseUser) : UserProvider()
    data class FakeUser(val userId: String) : UserProvider()
}
