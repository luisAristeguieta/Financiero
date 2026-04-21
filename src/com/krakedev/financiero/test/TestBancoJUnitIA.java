package com.krakedev.financiero.test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestBancoJUnitIA {
	@Test
	public void shouldCreateBancoWithDefaultUltimoCodigo() {
		Banco banco = new Banco();

		assertEquals(1000, banco.getUltimoCodigo());
	}

	@Test
	public void shouldSetAndGetUltimoCodigo() {
		Banco banco = new Banco();

		banco.setUltimoCodigo(2000);

		assertEquals(2000, banco.getUltimoCodigo());
	}

	@Test
	public void shouldCreateCuentaWithCurrentCodeAndCliente() {
		Banco banco = new Banco();
		Cliente cliente = new Cliente("1757126528", "Luis", "Mena");

		Cuenta cuenta = banco.crearCuenta(cliente);
		boolean resultado = banco.depositar(100.0, cuenta);

		assertEquals("1000", cuenta.getId());
		assertEquals("A", cuenta.getTipo());
		assertSame(cliente, cuenta.getPropietario());
		assertEquals(1001, banco.getUltimoCodigo());
		assertTrue(resultado);
		assertEquals(100.0, cuenta.getSaldoActual());
		
	}

	@Test
	public void shouldCreateConsecutiveAccounts() {
		Banco banco = new Banco();
		Cliente cliente1 = new Cliente("111", "Ana", "Lopez");
		Cliente cliente2 = new Cliente("222", "Pedro", "Mena");

		Cuenta cuenta1 = banco.crearCuenta(cliente1);
		Cuenta cuenta2 = banco.crearCuenta(cliente2);

		assertEquals("1000", cuenta1.getId());
		assertEquals("1001", cuenta2.getId());
		assertSame(cliente1, cuenta1.getPropietario());
		assertSame(cliente2, cuenta2.getPropietario());
		assertEquals(1002, banco.getUltimoCodigo());
	}
	
	@Test
	public void shouldNotDepositWhenMontoIsZero() {
		Banco banco = new Banco();
		Cliente cliente = new Cliente("1757126528", "Luis", "Mena");
		Cuenta cuenta = banco.crearCuenta(cliente);

		boolean resultado = banco.depositar(0.0, cuenta);

		assertFalse(resultado);
		assertEquals(0.0, cuenta.getSaldoActual());
	}
	
}
