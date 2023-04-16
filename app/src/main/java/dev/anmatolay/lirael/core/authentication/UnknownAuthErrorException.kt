package dev.anmatolay.lirael.core.authentication

class UnknownAuthErrorException(isTaskSuccessful: Boolean, isCurrentUserNull: Boolean) :
    RuntimeException(
        "Unknown error occurred during authentication. " +
            "Auth Task successful: $isTaskSuccessful. Is user null: $isCurrentUserNull.",
    )
