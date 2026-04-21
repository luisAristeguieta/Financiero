package com.krakedev.financiero.entidades;

public class Cuenta {
	private String id;
	private double saldoActual;
	private String tipo;
	private Cliente propietario;
	
	public Cuenta(String id, double saldoActual, String tipo) {
		this.id = id;
		this.saldoActual = saldoActual; // Igual se iniciaria en cero por default solo que tipo double.
		this.tipo = "A";
		propietario = new Cliente(); // Agrega en el contrustor para crear un nuevo objeto vacio tipo cliente.
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(double saldoActual) {
		this.saldoActual = saldoActual;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Cliente getPropietario() {
		return propietario;
	}

	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
	}
	
	public void imprimir() {
		String mensaje;
		mensaje = "Id: "+ id + "" +  " |Saldo Actual: " + saldoActual + " |Tipo: " + tipo;
		System.out.println(mensaje);
		propietario.imprimir();
	}
}
