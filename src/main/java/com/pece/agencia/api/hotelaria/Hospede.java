package com.pece.agencia.api.hotelaria;

import java.time.LocalDate;

public record Hospede(String nome, String email, LocalDate dataNascimento) {
}
