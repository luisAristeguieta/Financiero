package com.krakedev.financiero.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;

public class TestCuenta {

	public static void main(String[] args) {
		
		Cuenta cuenta1 = new Cuenta("22078222", 3000, "C");
		cuenta1.imprimir();
		
		Cliente cliente1 = new Cliente("175612822","Luis", "Aristeguieta");
		cliente1.imprimir();

	}

}
