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
	
	@Test
	public void shouldRetirarWhenMontoIsValidAndSaldoIsEnough() {
		Banco banco = new Banco();
		Cliente cliente = new Cliente("1757126528", "Luis", "Mena");
		Cuenta cuenta = banco.crearCuenta(cliente);
		banco.depositar(200.0, cuenta);

		boolean resultado = banco.retirar(50.0, cuenta);

		assertTrue(resultado);
		assertEquals(150.0, cuenta.getSaldoActual());
	}

	@Test
	public void shouldNotRetirarWhenMontoIsZero() {
		Banco banco = new Banco();
		Cliente cliente = new Cliente("1757126528", "Luis", "Mena");
		Cuenta cuenta = banco.crearCuenta(cliente);
		banco.depositar(200.0, cuenta);

		boolean resultado = banco.retirar(0.0, cuenta);

		assertFalse(resultado);
		assertEquals(200.0, cuenta.getSaldoActual());
	}

	@Test
	public void shouldNotRetirarWhenMontoIsNegative() {
		Banco banco = new Banco();
		Cliente cliente = new Cliente("1757126528", "Luis", "Mena");
		Cuenta cuenta = banco.crearCuenta(cliente);
		banco.depositar(200.0, cuenta);

		boolean resultado = banco.retirar(-10.0, cuenta);

		assertFalse(resultado);
		assertEquals(200.0, cuenta.getSaldoActual());
	}

	@Test
	public void shouldNotRetirarWhenMontoIsGreaterThanSaldo() {
		Banco banco = new Banco();
		Cliente cliente = new Cliente("1757126528", "Luis", "Mena");
		Cuenta cuenta = banco.crearCuenta(cliente);
		banco.depositar(200.0, cuenta);

		boolean resultado = banco.retirar(300.0, cuenta);

		assertFalse(resultado);
		assertEquals(200.0, cuenta.getSaldoActual());
	}

	@Test
	public void shouldRetirarWhenMontoIsEqualToSaldo() {
		Banco banco = new Banco();
		Cliente cliente = new Cliente("1757126528", "Luis", "Mena");
		Cuenta cuenta = banco.crearCuenta(cliente);
		banco.depositar(200.0, cuenta);
		
		System.out.println("Antes del retiro");
		cuenta.imprimir();
		
		boolean resultado = banco.retirar(200.0, cuenta);

		assertTrue(resultado);
		assertEquals(0.0, cuenta.getSaldoActual());
		System.out.println("Despues del retiro");
		cuenta.imprimir();
		
	}
	
	@Test
	public void shouldTransferAllSaldoFromOrigenToDestino() {
		Banco banco = new Banco();
		Cliente clienteOrigen = new Cliente("1757126528", "Luis", "Mena");
		Cliente clienteDestino = new Cliente("1723456789", "Maria", "Lopez");

		Cuenta origen = banco.crearCuenta(clienteOrigen);
		Cuenta destino = banco.crearCuenta(clienteDestino);

		banco.depositar(300.0, origen);

		System.out.println("=== BEFORE TRANSFER ===");
		System.out.println("Origen saldo: " + origen.getSaldoActual());
		System.out.println("Destino saldo: " + destino.getSaldoActual());

		boolean resultado = banco.transferir(origen, destino);

		System.out.println("=== AFTER TRANSFER ===");
		System.out.println("Origen saldo: " + origen.getSaldoActual());
		System.out.println("Destino saldo: " + destino.getSaldoActual());

		assertTrue(resultado);
		assertEquals(0.0, origen.getSaldoActual());
		assertEquals(300.0, destino.getSaldoActual());
	}

	@Test
	public void shouldNotTransferWhenOrigenHasZeroSaldo() {
		Banco banco = new Banco();
		Cliente clienteOrigen = new Cliente("1757126528", "Luis", "Mena");
		Cliente clienteDestino = new Cliente("1723456789", "Maria", "Lopez");

		Cuenta origen = banco.crearCuenta(clienteOrigen);
		Cuenta destino = banco.crearCuenta(clienteDestino);

		System.out.println("=== BEFORE FAILED TRANSFER ===");
		System.out.println("Origen saldo: " + origen.getSaldoActual());
		System.out.println("Destino saldo: " + destino.getSaldoActual());

		boolean resultado = banco.transferir(origen, destino);

		System.out.println("=== AFTER FAILED TRANSFER ===");
		System.out.println("Origen saldo: " + origen.getSaldoActual());
		System.out.println("Destino saldo: " + destino.getSaldoActual());

		assertFalse(resultado);
		assertEquals(0.0, origen.getSaldoActual());
		assertEquals(0.0, destino.getSaldoActual());
	}

	@Test
	public void shouldAccumulateSaldoInDestinoAfterTransfer() {
		Banco banco = new Banco();
		Cliente clienteOrigen = new Cliente("1757126528", "Luis", "Mena");
		Cliente clienteDestino = new Cliente("1723456789", "Maria", "Lopez");

		Cuenta origen = banco.crearCuenta(clienteOrigen);
		Cuenta destino = banco.crearCuenta(clienteDestino);

		banco.depositar(150.0, origen);
		banco.depositar(50.0, destino);

		System.out.println("=== BEFORE ACCUMULATED TRANSFER ===");
		System.out.println("Origen saldo: " + origen.getSaldoActual());
		System.out.println("Destino saldo: " + destino.getSaldoActual());

		boolean resultado = banco.transferir(origen, destino);

		System.out.println("=== AFTER ACCUMULATED TRANSFER ===");
		System.out.println("Origen saldo: " + origen.getSaldoActual());
		System.out.println("Destino saldo: " + destino.getSaldoActual());

		assertTrue(resultado);
		assertEquals(0.0, origen.getSaldoActual());
		assertEquals(200.0, destino.getSaldoActual());
	}
	
	
}
